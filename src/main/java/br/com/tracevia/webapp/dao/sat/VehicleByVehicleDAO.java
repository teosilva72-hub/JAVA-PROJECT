package br.com.tracevia.webapp.dao.sat;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.log.SystemLog;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.global.VehicleByVehicle;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class VehicleByVehicleDAO {
	
	private Connection conn;	
	private PreparedStatement ps;
	private ResultSet rs;
	
	// --------------------------------------------------------------------------------------------------------------
		
	// LOGS FOLDER
	
	private final String errorFolder = SystemLog.ERROR.concat("vbv-dao\\");
	
	// --------------------------------------------------------------------------------------------------------------
	
	public VehicleByVehicleDAO() {
		
		SystemLog.createLogFolder(errorFolder);
		
	}

	// --------------------------------------------------------------------------------------------------------------
	
    /**
     * Método para obter informações de veículos para montar um arquivo de texto
     * 
     * @param startDate data de inicio
     * @param endDate data de fim
     * @param equipId id do equipamento
     * @return lista com veículos 
     * @throws Exception
     */
	public List<VehicleByVehicle> getVehicles(String startDate, String endDate, String equipId) {

		List<VehicleByVehicle> list = new ArrayList<VehicleByVehicle>();
				
		String query = " ";
		
		query = "SELECT siteID, seqG, seqN, data, classe, axlNumber, axl1W, axl2W, axl3W, axl4W, "
				+ "axl5W, axl6W, axl7W, axl8W, axl9W, axl2D, axl3D, axl4D, axl5D, "
				+ "axl6D, axl7D, axl8D, axl9D, gross, temperature, speed, lane FROM tb_vbv " //INDEX(data_siteID) "
				+ "WHERE DATE(data) BETWEEN ? AND ? AND siteID = ? "
				+ "ORDER BY data ASC";
		
		try {						
			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(query);			
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			ps.setString(3, equipId);
							
			rs = ps.executeQuery();
									
			if (rs.isBeforeFirst()) {
				while (rs.next()) {

					VehicleByVehicle vbv = new VehicleByVehicle(); // instânciar objeto

					vbv.setSiteID(rs.getInt(1));
					
					vbv.setSeqG(rs.getInt(2));
					vbv.setSeqN(rs.getInt(3));
					
					vbv.setDateTime(rs.getString(4));	
					
					// ------------------------------------

					String classe = rs.getString(5); // CLASS
					
					if (classe == null)
						vbv.setClassVBV("UC");						
					else
						vbv.setClassVBV(classe);
					
					// ------------------------------------

					int axle = rs.getInt(6); // AXLE NUMBER
					
					if (axle == 0 || axle == 1)
						vbv.setAxlNumber(2); // Caso 0 ou 1 recebe 2
					else
						vbv.setAxlNumber(axle);	
					
					// ------------------------------------
					
					 vbv.setAxl1W(rs.getInt(7));
					 vbv.setAxl2W(rs.getInt(8));
					 vbv.setAxl3W(rs.getInt(9));
					 vbv.setAxl4W(rs.getInt(10));
					 vbv.setAxl5W(rs.getInt(11));
					 vbv.setAxl6W(rs.getInt(12));
					 vbv.setAxl7W(rs.getInt(13));
					 vbv.setAxl8W(rs.getInt(14));
					 vbv.setAxl9W(rs.getInt(15));					 
					 vbv.setAxl2D(rs.getInt(16));
					 vbv.setAxl3D(rs.getInt(17));
					 vbv.setAxl4D(rs.getInt(18));
					 vbv.setAxl5D(rs.getInt(19));
					 vbv.setAxl6D(rs.getInt(20));
					 vbv.setAxl7D(rs.getInt(21));
					 vbv.setAxl8D(rs.getInt(22));
					 vbv.setAxl9D(rs.getInt(23));					 											
					 vbv.setGross(rs.getInt(24));
					 vbv.setTemperature(rs.getInt(25));
					 vbv.setSpeed(rs.getInt(26));
					 vbv.setLane(rs.getInt(25));

					 list.add(vbv); // adiciona na Lista
				}
			}
		
			return list;

		} catch (SQLException sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));
			
			SystemLog.logErrorSQL(errorFolder.concat("error_build_meteo"), EquipmentsDAO.class.getCanonicalName(), sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
						
		} finally {
			
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		return list;
	}
	
	// --------------------------------------------------------------------------------------------------------------

}
