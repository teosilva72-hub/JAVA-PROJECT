package br.com.tracevia.webapp.dao.speed;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.tracevia.webapp.model.speed.Speed;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class SpeedDAO {
	
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;

	   public List<Speed> Status() throws Exception {
						
			List<Speed> list = new ArrayList<Speed>();		
							
			String select = "SELECT equip_id, equip_name, equip_status FROM connection_monitor WHERE equip_type = 'SPEED' ";
										
		  try {
				
			   conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
				
				ps = conn.prepareStatement(select);			
										
				rs = ps.executeQuery();
				
				if (rs != null) {
					while (rs.next()) {
						
						Speed speed = new Speed();

				         speed.setEquip_id(rs.getInt("equip_id"));
				         speed.setNome(rs.getString("equip_name"));
				         speed.setStatus(rs.getInt("equip_status"));
				        															
						list.add(speed);
					}				
				 }			

			} catch (SQLException e) {
				e.printStackTrace();
			}finally {ConnectionFactory.closeConnection(conn, ps, rs);}
	 				
			return list;
			
		}	
	

}
