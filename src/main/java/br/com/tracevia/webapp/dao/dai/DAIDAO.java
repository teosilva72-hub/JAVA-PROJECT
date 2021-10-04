package br.com.tracevia.webapp.dao.dai;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.tracevia.webapp.model.dai.DAI;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class DAIDAO {
	
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public List<DAI> Status() throws Exception {
		
		
		List<DAI> list = new ArrayList<DAI>();		
						
		String select = "SELECT equip_id, equip_name, equip_status FROM connection_monitor WHERE equip_type = 'DAI' ";
									
		try {
			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(select);			
									
			rs = ps.executeQuery();
			
			if (rs != null) {
				while (rs.next()) {
					
					DAI dai = new DAI();

						dai.setEquip_id(rs.getInt("equip_id"));
						dai.setNome(rs.getString("equip_name"));
						dai.setStatus(rs.getInt("equip_status"));
																				
					list.add(dai);
				}				
				}			

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}
				
		return list;
		
	}

	public DAI getEquipment(String name) {
		String select = "SELECT equip_id, name, km FROM connection_monitor WHERE name = ? ";

		try {
			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(select);
			ps.setString(0, name);
									
			rs = ps.executeQuery();
			
			if (rs.isBeforeFirst()) {
				rs.next()
					
				DAI dai = new DAI();

				dai.setEquip_id(rs.getInt("equip_id"));
				dai.setNome(rs.getString("name"));
				dai.setKm(rs.getString("km"));
			}			

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}
				
		return dai;
		
	}

}
