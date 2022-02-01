package br.com.tracevia.webapp.dao.global;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.SQL_Tracevia;
import br.com.tracevia.webapp.model.global.UserAccount;
import br.com.tracevia.webapp.model.global.ColumnsSql.RowResult;
import br.com.tracevia.webapp.model.global.ResultSql.MapResult;
import br.com.tracevia.webapp.util.LogUtils;

public class UserAccountDAO {

	SQL_Tracevia conn = new SQL_Tracevia();
	private int id;
	
	// --------------------------------------------------------------------------------------------
	
	// CLASS PATH
		
	private static String classLocation = UserAccountDAO.class.getCanonicalName();
		
	// --------------------------------------------------------------------------------------------
		
	// CLASS LOG FOLDER
		
	private static String classErrorPath = LogUtils.ERROR.concat("users\\");
		
	// --------------------------------------------------------------------------------------------
		
	// EXCEPTION FILENAMES
		
	private static String sqlRegisterExceptionLog = classErrorPath.concat("sql_register_");
	private static String sqlUpdateExceptionLog = classErrorPath.concat("sql_update_");
	private static String sqlListExceptionLog = classErrorPath.concat("sql_list_");
	private static String sqlSearchExceptionLog = classErrorPath.concat("sql_search_");
	private static String sqlDeleteExceptionLog = classErrorPath.concat("sql_delete_");
	private static String sqlUserValidateExceptionLog = classErrorPath.concat("sql_user_validate_");
	private static String sqlEmailExceptionLog = classErrorPath.concat("sql_email_validate_");
	private static String sqlUsernameExceptionLog = classErrorPath.concat("sql_username_validate_");
	private static String sqlPasswordExceptionLog = classErrorPath.concat("sql_change_password_");
				
	// --------------------------------------------------------------------------------------------		
		
	// CONSTRUTOR		

	public UserAccountDAO() throws Exception {

		LogUtils.createLogFolder(classErrorPath);
		
	}
	
	// --------------------------------------------------------------------------------------------		
	
	/**
	 * Método para registrar usuários no sistema
	 * @author Wellington 10/06/2021
	 * @version 1.0
	 * @since 1.0		
	 * @param user - objeto do tipo userAccount
	 * @return verdadeiro caso o cadastro foi efetuado com sucesso 	
	 */
	public boolean cadastroUsuario(UserAccount user) {

		boolean status = false;
	
		try {
			
			String sql = "INSERT INTO users_register (user_id, date_register, creation_username, name, job_position, email, username, password)"
					+ " values  ( (SELECT Max(user_id) as usr FROM users_register as maxId) + 1, ?, ?, ?, ?, ?, ?, ?)";

			String sql2 = "INSERT INTO users_permission_user (user_id, permission_id, status) values  ( ?, ?, ?)";

			conn.start(1);

			// Criar Datetime

			Date date = new Date();
			DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dt_register = dtf.format(date);

			// Execute Register
			conn.prepare(sql);

			conn.setString(1, dt_register);
			conn.setString(2, user.getCreatedBy());
			conn.setString(3, user.getName());
			conn.setString(4, user.getJob_position());
			conn.setString(5, user.getEmail());
			conn.setString(6, user.getUsername());
			conn.setString(7, user.getPassword());

			// System.out.println(sql);

			long success = conn.executeUpdate();

			if (success > 0) {

				conn.prepare(sql2);
				conn.setInt(1, id);
				conn.setInt(2, user.getPermission_id());
				conn.setBoolean(3, user.isActiveStatus());

				long sucess2 = conn.executeUpdate();

				if (sucess2 > 0)
					status = true;
			}

			// Caso retorno for de sucesso

		} catch (Exception sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));	

			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(sqlRegisterExceptionLog), classLocation, sqle.hashCode(), sqle.toString(), sqle.getMessage(), errors.toString());
			
		} finally {
			
			conn.close();		
		}

		return status;
	}


	// --------------------------------------------------------------------------------------------		
	
	/**
	 * Método para atualizar um usuário no sistema
	 * @author Wellington 10/06/2021
	 * @version 1.0
	 * @since 1.0		
	 * @param user - objeto do tipo userAccount
	 * @return verdadeiro caso o cadastro foi efetuado com sucesso 	
	*/
	public boolean atualizar(UserAccount usuario) {

		boolean status = false;

		try {

			String sql = "UPDATE users_register SET name = ?, job_position = ?, email = ?, username = ? "
					+ "WHERE user_id = ? ";

			String sql2 = "UPDATE users_permission_user SET permission_id = ?, status = ?  " + "WHERE user_id = ? ";

			conn.start(1);

			conn.prepare(sql);
					
			conn.setString(1, usuario.getName());
			conn.setString(2, usuario.getJob_position());
			conn.setString(3, usuario.getEmail());
			conn.setString(4, usuario.getUsername());
			conn.setInt(5, Integer.parseInt(usuario.getUserID())); // Here

			long success = conn.executeUpdate();

			if (success > 0) {

				conn.prepare(sql2);

				conn.setInt(1, usuario.getPermission_id());
				conn.setBoolean(2, usuario.isActiveStatus());
				conn.setInt(3, Integer.parseInt(usuario.getUserID()));

				long sucess2 = conn.executeUpdate();

				if (sucess2 > 0)
					status = true;
			}

		} catch (Exception sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));	

			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(sqlUpdateExceptionLog), classLocation, sqle.hashCode(), sqle.toString(), sqle.getMessage(), errors.toString());
			
			
		} finally {
			
			conn.close();
		}

		return status;
	}

	
	// --------------------------------------------------------------------------------------------		
	
	/**
	 * Método para listar usuários
	 * @author Wellington 10/06/2021
	 * @version 1.0
	 * @since 1.0			
	 * @return uma lista com objetos do tipo UserAccount
	*/
	public ArrayList<UserAccount> BuscarUsuarios() {
		
		TranslationMethods trad = new TranslationMethods();
	
		String sql = "";

		ArrayList<UserAccount> lista = new ArrayList<UserAccount>();

		
			sql = "SELECT r.user_id, r.date_register, r.name, r.job_position, r.email, r.username, p.permission_role, pu.status "
					+ "FROM users_permission_user pu "
					+ "INNER JOIN users_register r ON r.user_id = pu.user_id "
					+ "INNER JOIN users_permission p ON p.permission_id = pu.permission_id ";	

		try {

			conn.start(1);

			conn.prepare(sql);
			MapResult result = conn.executeQuery();
			
			//System.out.println("sql");

			if (result.hasNext()) {
				for (RowResult rs : result) {

					UserAccount user = new UserAccount();

					user.setUser_id(rs.getInt("user_id"));
					user.setDate_register(rs.getString("date_register"));
					user.setName(rs.getString("name"));
					user.setJob_position(rs.getString("job_position"));
					user.setEmail(rs.getString("email"));
					user.setUsername(rs.getString("username"));
					user.setPermission_role(trad.convertPermission(rs.getString("permission_role")));
					user.setActiveStatusName(trad.isActive(rs.getBoolean("status")));

					lista.add(user);
				}
			}

		} catch (Exception sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));	

			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(sqlListExceptionLog), classLocation, sqle.hashCode(), sqle.toString(), sqle.getMessage(), errors.toString());
			
			
		} finally {
			
			conn.close();
		}

		return lista;
	}
	
	// --------------------------------------------------------------------------------------------		

	/**
	 * Método para buscar um usuário específico pelo ID
	 * @author Wellington 10/06/2021
	 * @version 1.0
	 * @since 1.0		
	 * @param parametro - um parametro de busca do usuário
	 * @return um objeto do tipo UserAccount	
	 */
	public UserAccount procurarUsuarioByID(String parametro) {

		UserAccount user = new UserAccount();

		try {

			String sql = "SELECT r.user_id, r.name, r.job_position, r.email, r.username, p.permission_id, pu.status "
					+ "FROM users_permission_user pu "
					+ "INNER JOIN users_register r ON r.user_id = pu.user_id "
					+ "INNER JOIN users_permission p ON p.permission_id = pu.permission_id " + "WHERE pu.user_id = ? ";

			conn.start(1);

			conn.prepare(sql);
			conn.setString(1, parametro);

			MapResult result = conn.executeQuery();
					
			if (result.hasNext()) {
				for (RowResult rs : result) {

					user.setUserID(rs.getString("user_id"));
					user.setName(rs.getString("name"));
					user.setJob_position(rs.getString("job_position"));
					user.setEmail(rs.getString("email"));
					user.setUsername(rs.getString("username"));
					user.setPermission_id(rs.getInt("permission_id"));
					user.setActiveStatus(rs.getBoolean("status"));

				}
			}

		} catch (Exception sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));	

			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(sqlSearchExceptionLog), classLocation, sqle.hashCode(), sqle.toString(), sqle.getMessage(), errors.toString());
			
			
		} finally {
			
			conn.close();
		}

		return user;
	}
	
	// --------------------------------------------------------------------------------------------		

	/**
	 * Método para deletar um usuário
	 * @author Wellington 10/06/2021
	 * @version 1.0
	 * @since 1.0		
	 * @param parametro - um parametro de deleção do usuário
	 * @return verdadeiro caso seja ação realizada com sucesso
	 */
	public boolean deletarRegistro(String parametro) {

		boolean response = false;

		long success = 0, success2 = 0;
	
		try {

			String sql = "DELETE FROM users_register WHERE user_id = ?";

			String sql2 = "DELETE FROM users_permission_user WHERE user_id = ?";

			conn.start(1);

			conn.prepare(sql);

			conn.setInt(1, Integer.parseInt(parametro));

			success = conn.executeUpdate();

			if (success > 0) {

				conn.prepare(sql2);

				conn.setInt(1, Integer.parseInt(parametro));

				success2 = conn.executeUpdate();

				if (success2 > 0)
					response = true;

			}

		} catch (Exception sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));	

			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(sqlDeleteExceptionLog), classLocation, sqle.hashCode(), sqle.toString(), sqle.getMessage(), errors.toString());
			
			
		} finally {
			
			conn.close();
			
		}

		return response;
	}
	
	// --------------------------------------------------------------------------------------------		

	/**
	 * Método para validar um usuário
	 * @author Wellington 10/06/2021
	 * @version 1.0
	 * @since 1.0		
	 * @param usuario - um parametro de validação do usuário
	 * @return verdadeiro caso seja ação realizada com sucesso
	 */
	public boolean validaUsuario(String usuario) {
		boolean valid = false;

		try {

			conn.start(1);

			conn.prepare("SELECT username FROM users_register WHERE username = ?");
			conn.setString(1, usuario);
			MapResult result = conn.executeQuery();
			valid = result.hasNext();

		} catch (Exception sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));	

			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(sqlUserValidateExceptionLog), classLocation, sqle.hashCode(), sqle.toString(), sqle.getMessage(), errors.toString());
			
		} finally {
			conn.close();
		}

		return valid;
	}
	
	// --------------------------------------------------------------------------------------------		

	/**
	 * Método para alterar a senha de um usuário
	 * @author Wellington 10/06/2021
	 * @version 1.0
	 * @since 1.0		
	 * @param usuario - nome de usuário
	 * @param senha - senha de usuário
	 * @return verdadeiro caso seja ação realizada com sucesso
	 */
	public boolean changePassword(String usuario, String senha) {

		boolean status = false;

		try {

			String sql = "UPDATE users_register SET password = ? WHERE username = ? ";

			conn.start(1);

			conn.prepare(sql);

			conn.setString(1, senha);
			conn.setString(2, usuario);

			conn.executeUpdate();

			status = true;

		} catch (Exception sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));	

			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(sqlPasswordExceptionLog), classLocation, sqle.hashCode(), sqle.toString(), sqle.getMessage(), errors.toString());
				
		} finally {
			conn.close();
		}

		return status;
	}
	
	// --------------------------------------------------------------------------------------------		
	
	/**
	 * Método para verificar se um email existe
	 * @author Wellington 10/06/2021
	 * @version 1.0
	 * @since 1.0		
	 * @param email - endereço de email	
	 * @return verdadeiro caso seja ação realizada com sucesso
	 */	
	public boolean checkEmail(String email) {

		boolean response = false;

		try {
					
			String emailCheck = "SELECT email FROM users_register WHERE email = ? ";
			
			conn.start(1);
						
			conn.prepare(emailCheck); //CHECK EMAIL EXISTS
			conn.setString(1, email);
			
			MapResult result = conn.executeQuery();
			
			if (result.hasNext())
			     response = true;
			

		} catch (Exception sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));	

			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(sqlEmailExceptionLog), classLocation, sqle.hashCode(), sqle.toString(), sqle.getMessage(), errors.toString());
			
		} finally {
			conn.close();		
		}

		return response;
	}
	
	// --------------------------------------------------------------------------------------------		
	
	/**
	 * Método para verificar se um usuário existe
	 * @author Wellington 10/06/2021
	 * @version 1.0
	 * @since 1.0		
	 * @param username - nome de usuário
	 * @return verdadeiro caso seja ação realizada com sucesso
	 */	
	public boolean checkUsername(String username) {

		boolean response = false;

		try {
					
			String usernameCheck = "SELECT username FROM users_register WHERE username = ? ";
			
			conn.start(1);
						
			conn.prepare(usernameCheck); //CHECK EMAIL EXISTS
			conn.setString(1, username);
			
			MapResult result = conn.executeQuery();
			
			if (result.hasNext())
			     response = true;			
		

		} catch (Exception sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));	

			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(sqlUsernameExceptionLog), classLocation, sqle.hashCode(), sqle.toString(), sqle.getMessage(), errors.toString());
			
		} finally {
			
			conn.close();		
		}

		return response;
	}

	// --------------------------------------------------------------------------------------------		
}
