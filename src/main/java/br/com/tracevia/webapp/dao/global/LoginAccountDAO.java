package br.com.tracevia.webapp.dao.global;

import java.io.PrintWriter;
import java.io.StringWriter;

import br.com.tracevia.webapp.model.global.SQL_Tracevia;
import br.com.tracevia.webapp.model.global.UserAccount;
import br.com.tracevia.webapp.model.global.ColumnsSql.RowResult;
import br.com.tracevia.webapp.model.global.ResultSql.MapResult;
import br.com.tracevia.webapp.util.LogUtils;

public class LoginAccountDAO {

	SQL_Tracevia conn = new SQL_Tracevia();
	
	private static final String EMAIL_PATTERN = "[\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]";

	private static final String USER_PATTERN = "^([a-zA-Z]+[a-zA-Z0-9\\._]*[a-zA-Z0-9]+)$";
	
	// --------------------------------------------------------------------------------------------
	
	// CLASS PATH
		
	private static String classLocation = LoginAccountDAO.class.getCanonicalName();
		
	// --------------------------------------------------------------------------------------------
		
	// CLASS LOG FOLDER
		
	private static String classErrorPath = LogUtils.ERROR.concat("login\\");
		
	// --------------------------------------------------------------------------------------------
		
	// EXCEPTION FILENAMES
		
	private static String sqlUserValidateExceptionLog = classErrorPath.concat("sql_user_validate_");
	private static String sqlEmailValidateExceptionLog = classErrorPath.concat("sql_email_validate_");	
	private static String sqlLoginValidateExceptionLog = classErrorPath.concat("sql_login_validate_");
	private static String sqlChangePasswordExceptionLog = classErrorPath.concat("sql_change_password_validate_");	
		
	// --------------------------------------------------------------------------------------------		

	// CONSTRUTOR
	
	public LoginAccountDAO(){
		LogUtils.createLogFolder(classErrorPath);
		
	}
	
	// --------------------------------------------------------------------------------------------		
	
	/**
	 * Método para validar login do usuário
	 * @author Wellington 10/06/2021
	 * @version 1.0
	 * @since 1.0
	 * @param userParam - parametro de usuário (email ou username)
	 * @return verdadeiro caso seja validado	
	 * @throws Exception 
	 */

	public boolean UserValidation(String userParam) throws Exception {

		boolean validation = false, isEmail = false, isUserName = false;

		String query = "", query1 = "";

		if (userParam.matches(EMAIL_PATTERN)) {
			query = "SELECT r.email FROM users_permission_user pu "
					+ "INNER JOIN users_register r ON r.user_id = pu.user_id "
					+ "INNER JOIN users_permission p ON p.permission_id = pu.permission_id WHERE r.email = ? ";

			isEmail = true;

		}

		if (userParam.matches(USER_PATTERN)) {
			query1 = "SELECT r.username FROM users_permission_user pu "
					+ "INNER JOIN users_register r ON r.user_id = pu.user_id "
					+ "INNER JOIN users_permission p ON p.permission_id = pu.permission_id WHERE r.username = ? ";

			isUserName = true;

		}

		try {
			conn.start(1);
			
			//Verifica condições
			if(isEmail || isUserName) {

			if (isEmail)
				conn.prepare(query);

			if (isUserName)
				conn.prepare(query1);

			conn.setString(1, userParam);
			MapResult result = conn.executeQuery();
		
			if (result.len() > 0)
				validation = true; // Existe				
				
			}				

		} catch (Exception sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));	

			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(sqlUserValidateExceptionLog), classLocation, sqle.hashCode(), sqle.toString(), sqle.getMessage(), errors.toString());
					
		} finally {
			conn.close();
		}

		return validation;
	}

	
	// --------------------------------------------------------------------------------------------		
	
	/**
	 * Método para validar email de usuário
	 * @author Wellington 10/06/2021
	 * @version 1.0
	 * @since 1.0
	 * @param email - endereço de email
	 * @param roadConcessionaire - nome da concessionária
	 * @return um objeto do tipo UserAccount	
	*/
	
	public UserAccount emailValidation(String email, String roadConcessionaire) {

		UserAccount user = new UserAccount();

		String query = "";

		query = "SELECT r.user_id, r.username, r.name FROM users_permission_user pu "
				+ "INNER JOIN users_register r ON r.user_id = pu.user_id "
				+ "INNER JOIN users_permission p ON p.permission_id = pu.permission_id " + "WHERE r.email = ? ";

		try {
			conn.start(1);

			conn.prepare(query);
			conn.setString(1, email);
			MapResult result = conn.executeQuery();

			if (result != null) {
				for (RowResult rs : result) {

					user.setUser_id(rs.getInt("user_id"));
					user.setUsername(rs.getString("username"));
					user.setName(rs.getString("name"));

				}
			}

		} catch (Exception sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));	

			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(sqlEmailValidateExceptionLog), classLocation, sqle.hashCode(), sqle.toString(), sqle.getMessage(), errors.toString());
				
		} finally {
			conn.close();
		}

		return user;
	}

	// --------------------------------------------------------------------------------------------		
	
	/**
     * Método para validar login de usuário
	 * @author Wellington 10/06/2021
	 * @version 1.0
     * @since 1.0
	 * @param username - nome de usuário
	 * @param password - senha do usuário
	 * @return um objeto do tipo UserAccount	
	*/	
	public UserAccount loginValidation(String username, String password) {

		try {
			conn.start(1);

			String query = "SELECT r.username, p.permission_id, pu.status FROM users_permission_user pu "
					+ "INNER JOIN users_register r ON r.user_id = pu.user_id "
					+ "INNER JOIN users_permission p ON p.permission_id = pu.permission_id ";

			if (username.matches(EMAIL_PATTERN))
				query += "WHERE r.email = ? AND r.password = ? ";
			
			else query += "WHERE r.username = ? AND r.password = ? ";

			conn.prepare(query);
			conn.setString(1, username);
			conn.setString(2, password);

			MapResult result = conn.executeQuery();
			if (result != null) {
				for (RowResult rs : result) {

					UserAccount login = new UserAccount();
					login.setUsername(rs.getString("username"));
					login.setPermission_id(rs.getInt("permission_id"));
					login.setActiveStatus(rs.getBoolean("status"));

					return login;

				}

			}

		} catch (Exception sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));	

			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(sqlLoginValidateExceptionLog), classLocation, sqle.hashCode(), sqle.toString(), sqle.getMessage(), errors.toString());
		
			
		} finally {
			conn.close();
		}

		return null;
	}
	
	// --------------------------------------------------------------------------------------------		
	
		/**
	     * Método para alterar a senha de usuário
		 * @author Wellington 10/06/2021
		 * @version 1.0
	     * @since 1.0
		 * @param usuario - nome de usuário
		 * @param senha - senha do usuário
		 * @param roadConcessionaire - nome da concessionária
		 * @return um objeto do tipo UserAccount	
		*/	

	public boolean changePassword(String usuario, String senha, int id, String roadConcessionaire) {

		boolean status = false;
	

		try {
			conn.start(1);

			String sql = "UPDATE users_register SET  password = ? WHERE username = ? and user_id = ?";

			conn.prepare(sql);

			conn.setString(1, senha);
			conn.setString(2, usuario);
			conn.setInt(3, id);

			conn.executeUpdate();

			status = true;

		} catch (Exception sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));	

			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(sqlChangePasswordExceptionLog), classLocation, sqle.hashCode(), sqle.toString(), sqle.getMessage(), errors.toString());
					
		} finally {
			conn.close();
		}

		return status;
	}

	public double[] getCoord(String name) {
		double[] coord = new double[3];

		try {
			conn.start(1);

			// CHECK
			String select = "SELECT latitude, longitude, y_pos FROM map_coordinate WHERE name = ?";

			conn.prepare(select);

			conn.setString(1, name);
			MapResult result = conn.executeQuery();

			if(result != null) {
				if (result.len() > 0) {
					RowResult row = result.first();
					coord[0] = row.getDouble("longitude");
					coord[1] = row.getDouble("latitude");
					coord[2] = row.getDouble("y_pos");
				}
		    }

		} catch (Exception sqle) {
			System.out.println("Erro ao buscar dados " + sqle);        		    
		} finally {
			conn.close();
		}
		
		return coord;
	}
	
	// --------------------------------------------------------------------------------------------	

}
