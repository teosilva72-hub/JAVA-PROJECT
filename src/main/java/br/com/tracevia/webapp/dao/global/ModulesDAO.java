package br.com.tracevia.webapp.dao.global;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.tracevia.webapp.cfg.RoadConcessionairesEnum;
import br.com.tracevia.webapp.model.global.Modules;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class ModulesDAO {
	
	private Connection conn;
	protected ConnectionFactory connection = new ConnectionFactory();
	private PreparedStatement ps;
	private ResultSet rs;

	public ModulesDAO() throws Exception {

		try {
			
		   conn = ConnectionFactory.connectToTraceviaCore();
			
		} catch (Exception e) {
			
			throw new Exception("erro: \n" + e.getMessage());
		}
	}	
	
	
	/**
	 * M�todo para criado para obter uma lista com m�dulos ativos no sistema.	  
	 * @return uma lista com m�dulos ativos
	 * @throws Exception
	 * @see list
	 **/
	
	public ArrayList<Modules> listModules() throws Exception{
		
		ArrayList<Modules> modules = new ArrayList<Modules>();
		
		String query = null;
		
		try {			
					
			query = "SELECT module, battery_voltage, enabled FROM tracevia_core.modules WHERE enabled = 1";		
			
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			//System.out.println(query);

			if (rs != null) {
				while (rs.next()) {			

				  Modules mod = new Modules();
					
				  mod.setModule(rs.getString("module"));	
				  mod.setBattery_voltage(rs.getDouble("battery_voltage"));
				  mod.setEnabled(rs.getBoolean("enabled"));		
				  modules.add(mod);
				    
				}

			}

		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}
			
		  return modules;
	}

	// Pull Credentials
	public String[] getCred(String name) throws Exception {
		String[] credentials = new String[6];
		String query;

		try {			
					
			query = "SELECT name, user, pass, address, port, ws FROM credentials WHERE name = ?";		
			
			ps = conn.prepareStatement(query);
			ps.setString(1, name);

			rs = ps.executeQuery();
			
			//System.out.println(query);

			if (rs.isBeforeFirst()) {
				rs.next();

				credentials[0] = rs.getString("name");
				credentials[1] = rs.getString("user");
				credentials[2] = rs.getString("pass");
				credentials[3] = rs.getString("address");
				credentials[4] = rs.getString("port");
				credentials[5] = rs.getString("ws");
			}

		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}
			
		return credentials;
	}
}
