package br.com.tracevia.webapp.dao.global;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.tracevia.webapp.cfg.RoadConcessionairesEnum;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.global.UserAccount;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class UserAccountDAO {

	private Connection conn;		
	protected ConnectionFactory connection = new ConnectionFactory();

	private PreparedStatement ps, ps1;
	private ResultSet rs, rs1;
	private int id;
	
	public UserAccountDAO() throws Exception {	

		try {
			
			this.conn = ConnectionFactory.connectToTraceviaApp();	
			
		} catch (Exception e) {
			
			throw new Exception("erro: \n" + e.getMessage());
		}
	  }

	@SuppressWarnings("static-access")
	public boolean cadastroUsuario(UserAccount user) throws Exception {


		boolean status = false;

		if (user == null)
			throw new Exception("O valor passado nao pode ser nulo");

		try {
			String sql = "INSERT INTO users_register (user_id, date_register, name, job_position, email, username, password)"
					+ " values  ( ?, ?, ?, ?, ?, ?, ?)";

			String sql2 = "INSERT INTO users_permission_user (user_id, permission_id, status)"
					+ " values  ( ?, ?, ?)";


			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			//Criar Datetime

			Date date = new Date();
			DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dt_register = dtf.format(date);

			//Execute Register			
			ps = conn.prepareStatement(sql);

			ps.setInt(1, Integer.parseInt(user.getUserID()));
			
			ps1 = conn.prepareStatement("SELECT Max(user_id) as user FROM users_register;");
			rs1 = ps1.executeQuery();
			
			if (rs1 != null) {
			    while (rs1.next()) {
			    	
			    	System.out.println(rs1);
					System.out.println(rs1.getInt("user"));
					id = rs1.getInt("user") + 1;
			    
			    }
			  }
			
			
			
			
            //Execute Register			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, id);

			ps.setString(2, dt_register);
			ps.setString(3, user.getName());
			ps.setString(4, user.getJob_position());
			ps.setString(5, user.getEmail());			
			ps.setString(6, user.getUsername());
			ps.setString(7, user.getPassword());

			//System.out.println(sql);

			int success = ps.executeUpdate();

			if(success > 0) {

				ps = conn.prepareStatement(sql2);			
				ps.setInt(1, id);
				ps.setInt(2, user.getPermission_id());
				ps.setBoolean(3, user.isActiveStatus());

				int sucess2 = ps.executeUpdate();				

				if(sucess2 > 0)
					status = true;							
			}

			//Caso retorno for de sucesso			

		} catch (SQLException sqle) {
			throw new Exception("Erro ao inserir dados " + sqle);

		} finally {
			connection.closeConnection(conn, ps);
		}

		return status;	
	}

	@SuppressWarnings("static-access")
	public boolean atualizar(UserAccount usuario) throws Exception {

		boolean status = false;

		if (usuario == null)
			throw new Exception("O valor passado nao pode ser nulo");

		try {

			String sql = "UPDATE tracevia_app.users_register SET name = ?, job_position = ?, email = ?, username = ? " + 		      
					"WHERE user_id = ? ";

			String sql2 = "UPDATE users_permission_user SET permission_id = ?, status = ?  " + 
					"WHERE user_id = ? ";

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(sql);

			ps.setString(1, usuario.getName());
			ps.setString(2, usuario.getJob_position());
			ps.setString(3, usuario.getEmail());				
			ps.setString(4, usuario.getUsername());		  	 
			ps.setInt(5, Integer.parseInt(usuario.getUserID())); // Here

			int success = ps.executeUpdate();		

			if(success > 0) {

				ps = conn.prepareStatement(sql2);

				ps.setInt(1, usuario.getPermission_id());
				ps.setBoolean(2, usuario.isActiveStatus());
				ps.setInt(3, Integer.parseInt(usuario.getUserID()));

				int sucess2 = ps.executeUpdate();

				if(sucess2 > 0)
					status = true; 
			}


		} catch (SQLException sqle) {
			throw new Exception("Erro ao alterar dados " + sqle);
		} finally {
			connection.closeConnection(conn, ps);
		}

		return status;
	}
	//Criar Metodos daqui
	@SuppressWarnings("static-access")
	public ArrayList<UserAccount> BuscarUsuarios(String parametro) throws Exception
	{	
		TranslationMethods trad = new TranslationMethods();

		boolean isNumber = false, isName = false, isUserName = false;	
		String regexNumber = "^[0-9]+$", regexName = "^[^a-zA-Z\\u00C0-\\u00FF ]$", 
				regexNick = "^[^0-9][^._]+$", sql = "";

		ArrayList<UserAccount> lista = new ArrayList<UserAccount>();

		if(parametro.equals("Todos"))
			sql ="SELECT r.user_id, r.date_register, r.name, r.job_position, r.email, r.username, p.permission_role, pu.status " +
					"FROM tracevia_app.users_permission_user pu " + 
					"INNER JOIN users_register r ON r.user_id = pu.user_id " + 
					"INNER JOIN users_permission p ON p.permission_id = pu.permission_id "; 
		else {    

			isNumber = parametro.matches(regexNumber);
			isName = parametro.matches(regexName);	
			isUserName = parametro.matches(regexNick);	

			sql = "SELECT r.user_id, r.date_register, r.name, r.job_position, r.email, r.username, p.permission_role, pu.status " +
					"FROM tracevia_app.users_permission_user pu " + 
					"INNER JOIN users_register r ON r.user_id = pu.user_id " + 				
					"INNER JOIN users_permission p ON p.permission_id = pu.permission_id "; 


			if(isNumber) 	
				sql +="WHERE pu.user_id = ? ";

			else if(isName) 
				sql +="WHERE r.name LIKE ? "; 		

			else if(isUserName)	
				sql +="WHERE r.username LIKE ? ";			

		}

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);		

			ps = conn.prepareStatement(sql);

			if(isNumber)
				ps.setInt(1, Integer.parseInt(parametro));

			else if(isName || isUserName)
				ps.setString(1, "%"+parametro+"%");	

			rs = ps.executeQuery();	

			if (rs != null) {
				while (rs.next()) {

					UserAccount user = new UserAccount();

					user.setUser_id(rs.getInt("user_id"));
					user.setDate_register(rs.getString("date_register"));
					user.setName(rs.getString("name"));
					user.setJob_position(rs.getString("job_position"));
					user.setEmail(rs.getString("email"));
					user.setUsername(rs.getString("username"));
					user.setPermission_role(trad.convertPermission(rs.getString("permission_role")));
					user.setCheckActive(trad.isActive(rs.getBoolean("status")));

					lista.add(user);				  							    
				}
			} 

		} catch (SQLException sqle) {
			throw new Exception(sqle);
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return lista;
	}


	@SuppressWarnings("static-access")
	public UserAccount procurarUsuarioByID(String parametro) throws Exception {	

		UserAccount user = new UserAccount();

		try {

			String sql = "SELECT r.user_id, r.name, r.job_position, r.email, r.username, p.permission_id, pu.status " + 
					"FROM tracevia_app.users_permission_user pu " + 
					"INNER JOIN users_register r ON r.user_id = pu.user_id " + 
					"INNER JOIN users_permission p ON p.permission_id = pu.permission_id " +
					"WHERE pu.user_id = ? ";

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);	

			ps = conn.prepareStatement(sql);			
			ps.setString(1, parametro);		

			rs = ps.executeQuery();	

			if (rs != null) {
				while (rs.next()) {				   

					user.setUserID(rs.getString("user_id"));				    
					user.setName(rs.getString("name"));
					user.setJob_position(rs.getString("job_position"));
					user.setEmail(rs.getString("email"));				    
					user.setUsername(rs.getString("username"));				
					user.setPermission_id(rs.getInt("permission_id"));						   
					user.setActiveStatus(rs.getBoolean("status"));

				}
			} 

		} catch (SQLException sqle) {
			throw new Exception(sqle);
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return user;
	}
	@SuppressWarnings("static-access")
	public boolean deletarRegistro(String parametro) throws Exception {

		boolean response = false;

		int success = 0, success2 = 0;

		if (parametro == null)
			throw new Exception("O valor passado nao pode ser nulo");

		try {

			String sql = "DELETE FROM users_register WHERE user_id = ?";

			String sql2 = "DELETE FROM users_permission_user WHERE user_id = ?";

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);		

			ps = conn.prepareStatement(sql);

			ps.setInt(1, Integer.parseInt(parametro));

			success = ps.executeUpdate();

			if(success > 0) {

				ps = conn.prepareStatement(sql2);

				ps.setInt(1, Integer.parseInt(parametro));

				success2 = ps.executeUpdate();

				if(success2 > 0)
					response = true;			

			}		

		} catch (SQLException sqle) {
			throw new Exception("Erro ao deletar dados " + sqle);
		} finally {
			connection.closeConnection(conn, ps);
		}

		return response;
	}
	@SuppressWarnings("static-access")
	public boolean validaUsuario(String usuario) throws Exception {

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement("SELECT username FROM users_register WHERE username = ?");
			ps.setString(1, usuario);				
			rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) 
					return true;								
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

		return false;
	}

	@SuppressWarnings("static-access")
	public UserAccount validaLogin(String usuario, String senha) throws Exception {

		UserAccount user = new UserAccount();

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement("SELECT username, job_position, status, password FROM users_register WHERE username = ? and password = ?");
			ps.setString(1, usuario);		
			ps.setString(2, senha);	
			rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {

					user.setUsername(rs.getString("username"));	
					user.setJob_position(rs.getString("job_position"));	
					user.setPassword(rs.getString("password"));
					user.setActiveStatus(rs.getBoolean("status"));
				}				
			}			

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

		return user;		
	}


	public boolean changePassword(String usuario, String senha) throws Exception {

		boolean status = false;

		if (usuario == null)
			throw new Exception("O valor passado nao pode ser nulo");

		try {

			String sql = "UPDATE users_register SET  password = ? WHERE username = ? ";

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);	

			ps = conn.prepareStatement(sql);

			ps.setString(1, senha);	
			ps.setString(2, usuario);	

			ps.executeUpdate();

			status = true;

		} catch (SQLException sqle) {
			throw new Exception("Erro ao alterar dados " + sqle);
		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}

		return status;
	}

}
