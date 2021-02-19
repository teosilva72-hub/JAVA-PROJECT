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
	 * Método para criado para obter uma lista com módulos ativos no sistema.	  
	 * @return uma lista com módulos ativos
	 * @throws Exception
	 * @see list
	 **/
	
	public ArrayList<Modules> listModules() throws Exception{
		
		ArrayList<Modules> modules = new ArrayList<Modules>();
		
		String query = null;
		
		try {			
					
			query = "SELECT module, enabled FROM tracevia_core.modules WHERE enabled = 1";		
			
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			//System.out.println(query);

			if (rs != null) {
				while (rs.next()) {			

				  Modules mod = new Modules();
					
				  mod.setModule(rs.getString("module"));		
				  mod.setEnabled(rs.getBoolean("enabled"));		
				  modules.add(mod);
				    
				}

			}

		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}
			
		  return modules;
	}

}
