package br.com.tracevia.webapp.dao.global;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.global.UserAccount;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class LoginAccountDAO {

	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	private static final String EMAIL_PATTERN = "[\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]";

	private static final String USER_PATTERN = "^([a-zA-Z]+[a-zA-Z0-9\\._]*[a-zA-Z0-9]+)$";

	public LoginAccountDAO() throws Exception {

		try {

			this.conn = ConnectionFactory.connectToTraceviaApp();

		} catch (Exception e) {

			throw new Exception("erro: \n" + e.getMessage());
		}
	}

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

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			if (isEmail)
				ps = conn.prepareStatement(query);

			if (isUserName)
				ps = conn.prepareStatement(query1);

				ps.setString(1, userParam);
				rs = ps.executeQuery();
			

			if (rs != null) {
				while (rs.next()) {

					validation = true; // Existe

				}
			}

			else
				validation = false;

		} catch (SQLException sqle) {
			throw new Exception(sqle);
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return validation;

	}

	public UserAccount emailValidation(String email) throws Exception {

		UserAccount user = new UserAccount();

		String query = "";

		query = "SELECT r.user_id, r.username, r.name FROM users_permission_user pu "
				+ "INNER JOIN users_register r ON r.user_id = pu.user_id "
				+ "INNER JOIN users_permission p ON p.permission_id = pu.permission_id " + "WHERE r.email = ? ";

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

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
			throw new Exception(sqle);
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return user;
	}

	// Login Validation
	public UserAccount loginValidation(String username, String password) throws Exception {

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
			throw new Exception(sqle);
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return null;
	}

	@SuppressWarnings("static-access")
	public boolean changePassword(String usuario, String senha, int id) throws Exception {

		boolean status = false;

		if (usuario == null)
			throw new Exception("O valor passado nao pode ser nulo");

		try {

			String sql = "UPDATE users_register SET  password = ? WHERE username = ? and user_id = ?";

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(sql);

			ps.setString(1, senha);
			ps.setString(2, usuario);
			ps.setInt(3, id);

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
