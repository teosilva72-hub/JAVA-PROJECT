package br.com.tracevia.webapp.dao.cftv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.tracevia.webapp.model.cftv.CFTV;
import br.com.tracevia.webapp.model.dms.DMS;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class CFTVDAO {

	private Connection conn;
	protected ConnectionFactory connection = new ConnectionFactory();
	private PreparedStatement ps;
	private ResultSet rs;

	
	
	/*public ArrayList<CFTV> buildCFTVInterface() throws Exception {

		ArrayList<CFTV> lista = new ArrayList<CFTV>();

		String sql = "SELECT pmv_id, table_id, name, city, road, km, width, posX, posY, "
				+ "position FROM tracevia_app.pmv_equipment";
				
		try {
			
			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
						
			if (rs != null) {

				while (rs.next()) {
					
					CFTV cftv = new CFTV();
					
					dms.setEquip_id(rs.getInt(1));
					dms.setTable_id(rs.getInt(2));						
					dms.setNome(rs.getString(3));
					dms.setCidade(rs.getString(4));
					dms.setEstrada(rs.getString(5));
					dms.setKm(rs.getString(6));
					dms.setWidth(rs.getInt(7));
					dms.setHeight((int) (dms.getWidth()*0.232));										
					dms.setPosX(rs.getInt(8));
					dms.setPosY(rs.getInt(9));					
					dms.setPosicao(rs.getString(10));
					
					/*if(dms.getPosicao().equals("horizontal")) {
						dms.setHorizontal(true);
					}else {
						dms.setHorizontal(false);
					}						
					
					
					lista.add(cftv);
				}				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return lista;
	}*/
	
	
	
	
	
}
