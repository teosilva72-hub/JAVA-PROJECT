package br.com.tracevia.webapp.dao.system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.speed.Speed;
import br.com.tracevia.webapp.model.system.Support;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class SupportDAO {
	
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	
	
	
	public boolean save (Support support) {
		
		boolean save = false; 
				
				String query = "INSERT INTO Support (id, username, road_concessionaire, email, category, others, subject, message) values (null, ?,?,?,?,?,?,?) ";
		
		
				try {
					conn = ConnectionFactory.connectToTraceviaApp();
					ps = conn.prepareStatement(query);

					ps.setString(1, support.getUsername());
					ps.setString(2, support.getRoad_concessionaire());
					ps.setString(3, support.getEmail());
					ps.setString(4, support.getCategory());
					ps.setString(5, support.getOthers());
					ps.setString(6, support.getSubject());
					ps.setString(7, support.getMessage());
					
					int rs = ps.executeUpdate(); //
				
					if(rs > 0) 
						save = true;
				}catch(SQLException sqle) {
					
					sqle.printStackTrace(); // Imprime o rastreamento do erro da SQL Exception (Console)
				
				}catch(Exception ex) {
				
					ex.printStackTrace(); // Imprime o rastreamento do erro da Exception (Console)
				
				}finally{
				
					ConnectionFactory.closeConnection(conn, ps); // Encerrar conexão ao finalizar		
				
				}
			
			return save;
			
			
	}
			
			public ArrayList<Support>listarSupport() throws Exception {
				
			String query = "SELECT id, username, road_concessionaire, category FROM support";
			
			ArrayList<Support> listar = new ArrayList<Support>();
			
			try {

				conn = ConnectionFactory.connectToTraceviaApp();
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
				
				if(rs != null) {
					while(rs.next()) {
						Support spt = new Support();
					
			spt.setData_number(String.valueOf(rs.getInt(1)));
			spt.setUsername(rs.getString(2));
			spt.setRoad_concessionaire(rs.getString(3));
			spt.setCategory(rs.getString(4));
			
			 listar.add(spt);
					}
				}
			}finally {
				ConnectionFactory.closeConnection(conn, ps, rs);
			}
			return listar;
			
			}
			}
		


	
