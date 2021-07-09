package br.com.tracevia.webapp.dao.colas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.tracevia.webapp.model.colas.Colas;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class ColasDAO {
	
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	
	   public List<Colas> Status() throws Exception {
			
			
			List<Colas> list = new ArrayList<Colas>();		
							
			String select = "SELECT equip_id, equip_name, equip_status FROM connection_monitor WHERE equip_type = 'COLAS' ";
										
		  try {
				
			   conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
				
				ps = conn.prepareStatement(select);			
										
				rs = ps.executeQuery();
				
				if (rs != null) {
					while (rs.next()) {
						
						Colas colas = new Colas();

				         colas.setEquip_id(rs.getInt("equip_id"));
				         colas.setNome(rs.getString("equip_name"));
				         colas.setStatus(rs.getInt("equip_status"));
				        															
						list.add(colas);
					}				
				 }			

			} catch (SQLException e) {
				e.printStackTrace();
			}finally {ConnectionFactory.closeConnection(conn, ps, rs);}
	 				
			return list;
			
		}

}
