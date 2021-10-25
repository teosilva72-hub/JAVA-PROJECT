package br.com.tracevia.webapp.dao.global;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.global.UserAccount;
import br.com.tracevia.webapp.util.ConnectionFactory;
import br.com.tracevia.webapp.util.LogUtils;

public class LoginAccountDAO {

	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	
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
		
		LogUtils.createLogsFolder(classErrorPath);
		
	}
	
	// --------------------------------------------------------------------------------------------		
	
	/**
	 * Método para validar login do usuário
	 * @author Wellington 10/06/2021
	 * @version 1.0
	 * @since 1.0
	 * @param userParam - parametro de usuário (email ou username)
	 * @return verdadeiro caso seja validado	
	 */

	public boolean UserValidation(String userParam) {

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
			
			//Verifica condições
			if(isEmail || isUserName) {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			if (isEmail)
				ps = conn.prepareStatement(query);

			if (isUserName)
				ps = conn.prepareStatement(query1);

				ps.setString(1, userParam);
				rs = ps.executeQuery();
			
				if (rs.next() != false)
					 validation = true; // Existe				
				
			}				

		} catch (SQLException sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));	

			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(sqlUserValidateExceptionLog), classLocation, sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
					
		} finally {
			
			ConnectionFactory.closeConnection(conn, ps, rs);
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

			conn = ConnectionFactory.useConnection(roadConcessionaire);

			ps = conn.prepareStatement(query);
			ps.setString(1, email);
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					user.setUser_id(rs.getInt("user_id"));
					user.setUsername(rs.getString("username"));
					user.setName(rs.getString("name"));

				}
			}

		} catch (SQLException sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));	

			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(sqlEmailValidateExceptionLog), classLocation, sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
				
		} finally {
			
			ConnectionFactory.closeConnection(conn, ps, rs);
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

			String query = "SELECT r.username, p.permission_id, pu.status FROM users_permission_user pu "
					+ "INNER JOIN users_register r ON r.user_id = pu.user_id "
					+ "INNER JOIN users_permission p ON p.permission_id = pu.permission_id ";

			if (username.matches(EMAIL_PATTERN))
				query += "WHERE r.email = ? AND r.password = ? ";
			
			else query += "WHERE r.username = ? AND r.password = ? ";
			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, password);

			rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {

					UserAccount login = new UserAccount();
					login.setUsername(rs.getString("username"));
					login.setPermission_id(rs.getInt("permission_id"));
					login.setActiveStatus(rs.getBoolean("status"));

					return login;

				}

			}

		} catch (SQLException sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));	

			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(sqlLoginValidateExceptionLog), classLocation, sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
		
			
		} finally {
			
			ConnectionFactory.closeConnection(conn, ps, rs);
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

			String sql = "UPDATE users_register SET  password = ? WHERE username = ? and user_id = ?";

			conn = ConnectionFactory.useConnection(roadConcessionaire);

			ps = conn.prepareStatement(sql);

			ps.setString(1, senha);
			ps.setString(2, usuario);
			ps.setInt(3, id);

			ps.executeUpdate();

			status = true;

		} catch (SQLException sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));	

			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(sqlChangePasswordExceptionLog), classLocation, sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
					
		} finally {
			
			ConnectionFactory.closeConnection(conn, ps);
		}

		return status;
	}
	
	// --------------------------------------------------------------------------------------------	

}
