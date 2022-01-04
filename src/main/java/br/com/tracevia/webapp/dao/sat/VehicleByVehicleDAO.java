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
		
		query = "SELECT eq.name, t.siteID, t.seqG, t.seqN, t.data, t.classe, t.axlNumber, t.axl1W, t.axl2W, t.axl3W, t.axl4W, "
				+ "t.axl5W, t.axl6W, t.axl7W, t.axl8W, t.axl9W, t.axl2D, t.axl3D, t.axl4D, t.axl5D, "
				+ "t.axl6D, t.axl7D, t.axl8D, t.axl9D, t.gross, t.temperature, t.speed, t.lane FROM tb_vbv t " //INDEX(data_siteID) "
				+ "INNER JOIN sat_equipment eq ON eq.equip_id = t.siteID "
				+ "WHERE DATE(t.data) BETWEEN ? AND ? AND t.siteID = ? "
				+ "ORDER BY t.data ASC";
		
		try {						
			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(query);			
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			ps.setString(3, equipId);
			
			System.out.println(startDate+"\n"+endDate+"\n"+equipId);
							
			rs = ps.executeQuery();
			
			System.out.println(query);
									
			if (rs.isBeforeFirst()) {
				while (rs.next()) {

					VehicleByVehicle vbv = new VehicleByVehicle(); // instânciar objeto

					vbv.setName(rs.getString(1));
					vbv.setSiteID(rs.getInt(2));
					
					vbv.setSeqG(rs.getInt(3));
					vbv.setSeqN(rs.getInt(4));
					
					vbv.setDateTime(rs.getString(5));	
					
					// ------------------------------------

					String classe = rs.getString(6); // CLASS
					
					if (classe == null)
						vbv.setClassVBV("UC");						
					else
						vbv.setClassVBV(classe);
					
					// ------------------------------------

					int axle = rs.getInt(7); // AXLE NUMBER
					
					if (axle == 0 || axle == 1)
						vbv.setAxlNumber(2); // Caso 0 ou 1 recebe 2
					else
						vbv.setAxlNumber(axle);	
					
					// ------------------------------------
					
					 vbv.setAxl1W(rs.getInt(8));
					 vbv.setAxl2W(rs.getInt(9));
					 vbv.setAxl3W(rs.getInt(10));
					 vbv.setAxl4W(rs.getInt(11));
					 vbv.setAxl5W(rs.getInt(12));
					 vbv.setAxl6W(rs.getInt(13));
					 vbv.setAxl7W(rs.getInt(14));
					 vbv.setAxl8W(rs.getInt(15));
					 vbv.setAxl9W(rs.getInt(16));					 
					 vbv.setAxl2D(rs.getInt(17));
					 vbv.setAxl3D(rs.getInt(18));
					 vbv.setAxl4D(rs.getInt(19));
					 vbv.setAxl5D(rs.getInt(20));
					 vbv.setAxl6D(rs.getInt(21));
					 vbv.setAxl7D(rs.getInt(22));
					 vbv.setAxl8D(rs.getInt(23));
					 vbv.setAxl9D(rs.getInt(24));					 											
					 vbv.setGross(rs.getInt(25));
					 vbv.setTemperature(rs.getInt(26));
					 vbv.setSpeed(rs.getInt(27));
					 vbv.setLane(rs.getInt(28));

					 list.add(vbv); // adiciona na Lista
				}
			}
		
			return list;

		} catch (SQLException sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));
			
			SystemLog.logErrorSQL(errorFolder.concat("error_get_vehicles"), EquipmentsDAO.class.getCanonicalName(), sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
						
		} finally {
			
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		return list;
	}
	
	// --------------------------------------------------------------------------------------------------------------

}
