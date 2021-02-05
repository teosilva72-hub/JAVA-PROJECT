package br.com.tracevia.webapp.dao.dms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.tracevia.webapp.model.dms.DMS;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class DMSDAO {
	
	private Connection conn;
	protected ConnectionFactory connection = new ConnectionFactory();
	private PreparedStatement ps;
	private ResultSet rs;
		
	/*Quantidade de PMVs reigstrados na base de dados*/
	
	public Integer amountDMS() throws Exception {

		int count = 0;

		String sql = "SELECT COUNT(*) AS dmsCount FROM tracevia_app.pmv_equipment";
				
		try {
			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
						
			if (rs != null) {

				while (rs.next()) {					
					
					 count = rs.getInt(1);					
				}				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return count;
	}
	
	
/*IDS dos PMVs reigstrados na base de dados*/
	
	public ArrayList<DMS> idsDMS() throws Exception {

		ArrayList<DMS> lista = new ArrayList<DMS>();

		String sql = "SELECT equip_id, name, km FROM tracevia_app.pmv_equipment";
				
		try {
			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
						
			if (rs != null) {

				while (rs.next()) {		
					
					DMS dms = new DMS();
					
					dms.setEquip_id(rs.getInt(1));				
					dms.setNome(rs.getString(2));
					dms.setKm(rs.getString(3));
					
					lista.add(dms);				
				}				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return lista;
	}
	
	

}
