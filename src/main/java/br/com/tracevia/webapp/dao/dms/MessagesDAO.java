
package br.com.tracevia.webapp.dao.dms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.dms.Messages;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class MessagesDAO {

	private Connection conn;
	protected ConnectionFactory connection = new ConnectionFactory();
	private PreparedStatement ps, ps1;
	private ResultSet rs, rs1;

	// LanguageMB lang;
	Locale locale;
	ResourceBundle resourceBundle;

	public List<Messages> mensagensDisponiveis() throws Exception {

		List<Messages> lista = new ArrayList<Messages>();

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement("SELECT ma.id_message, m.id_message as page, page1, "
					+ "timer1, page2, timer2, page3, timer3, page4, timer4, page5, timer5, "
					+ "type, name, id_image, text1, text2, text3 "
					+ "FROM tracevia_app.pmv_messages_available ma INNER JOIN tracevia_app.pmv_messages m "
					+ "ON ma.page1 = m.id_message OR ma.page2 = m.id_message "
					+ "OR ma.page3 = m.id_message OR ma.page4 = m.id_message "
					+ "OR ma.page5 = m.id_message OR m.id_message = 1 "
					+ "WHERE enabled <> 0 and avaliable <> 0 ORDER BY ma.id_message ASC, page ASC");
			rs = ps.executeQuery();

			if (rs.isBeforeFirst()) {
				Messages mensagens = new Messages();
				boolean[] pages = new boolean[] { true, true, true, true, true };
				rs.next();
				while (true) {
					int page = rs.getInt("page");
					if (page == 1) {
						mensagens = new Messages();
						mensagens.setId_message(rs.getInt("id_message"));
					} else
						for (int i = 0; i <= pages.length - 1; i++) {
							int idx = i + 1;
							if (rs.getInt("page" + idx) == page) {
								int index = mensagens.getPages().size();
								if (i <= index)
									index = i;
								mensagens.setPages(rs.getString("text1"), rs.getString("text2"), rs.getString("text3"),
										rs.getInt("id_image"), rs.getString("type"), rs.getString("name"),
										getImageFromMessageAvailable(rs.getInt("id_image")), rs.getFloat("timer" + idx),
										idx, index);
								pages[i] = false;
							}
						}
					if (rs.next()) {
						if (rs.getInt("id_message") != mensagens.getId_message()) {
							for (int i = 0; i < pages.length; i++) {
								if (pages[i]) {
									pages[i] = false;
									mensagens.setPages(i);
								}
							}
							pages = new boolean[] { true, true, true, true, true };
							lista.add(mensagens);
						}
					} else {
						for (int i = 0; i < pages.length; i++) {
							if (pages[i]) {
								pages[i] = false;
								mensagens.setPages(i);
							}
						}
						lista.add(mensagens);
						break;
					}
				}
			}

		} catch (

		SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		for (Messages messages : lista) {
			messages.revision();
		}

		return lista;
	}

	public List<Messages> mensagensOnly() throws Exception {

		List<Messages> lista = new ArrayList<Messages>();

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement("SELECT id_message, id_image, type, name, text1, text2, text3 "
					+ "FROM tracevia_app.pmv_messages WHERE enabled <> 0 AND id_message <> 1 ORDER BY id_message ASC");
			rs = ps.executeQuery();

			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					Messages mensagens = new Messages();

					mensagens.setId_message(rs.getInt("id_message") - 1);
					mensagens.setId_image(rs.getInt("id_image"));
					mensagens.setImage(getImageFromMessageAvailable(rs.getInt("id_image")));
					mensagens.setTipo(rs.getString("type"));
					mensagens.setNome(rs.getString("name"));
					mensagens.setMessage1(rs.getString("text1"));
					mensagens.setMessage2(rs.getString("text2"));
					mensagens.setMessage3(rs.getString("text3"));

					lista.add(mensagens);
				}
			}
			return lista;

		} catch (

		SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return lista;
	}

	// Create new messages and pages
	// @Param ListaPages
	// type
	// name
	// image
	// image_id
	// timer
	// line1
	// line2
	// line3
	// @Param msgID
	// @Param user
	public void createMessage(int msgID, String user, List<Map<String, String>> ListaPages) throws Exception {

		DateTimeApplication dt = new DateTimeApplication();
		String dt_creation = dt.currentStringDate(DateTimeApplication.DATE_TIME_FORMAT_STANDARD_DATABASE);

		try {
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			boolean[] pages = new boolean[] { false, false, false, false, false };
			List<Integer> pageId = new ArrayList<Integer>();
			List<Float> pageTimer = new ArrayList<Float>();
			int count = 0;
			for (Map<String, String> page : ListaPages) {
				int id;

				ps1 = conn.prepareStatement("SELECT Max(id_message) as user FROM tracevia_app.pmv_messages;");
				rs1 = ps1.executeQuery();

				if (rs1.isBeforeFirst()) {
					rs1.next();
					id = rs1.getInt("user") + 1;
				} else
					id = 1;

				String sql = "INSERT INTO tracevia_app.pmv_messages "
						+ "(id_message, creation_date,  creation_username, type, name, id_image, text1, text2, text3, enabled) "
						+ "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, true ); ";

				ps = conn.prepareStatement(sql);
				ps.setInt(1, id);
				ps.setString(2, dt_creation);
				ps.setString(3, user);
				ps.setString(4, page.get("type"));
				ps.setString(5, page.get("name"));
				ps.setInt(6, Integer.parseInt(page.get("image_id")));
				ps.setString(7, page.get("line1"));
				ps.setString(8, page.get("line2"));
				ps.setString(9, page.get("line3"));

				ps.executeUpdate();

				pageId.add(id);
				pageTimer.add(Float.parseFloat(page.get("timer")));

				pages[count] = true;
				count++;
			}

			String sql = "INSERT INTO tracevia_app.pmv_messages_available "
					+ "(id_message, creation_date, creation_username, update_date, update_username, "
					+ "page1, timer1, page2, timer2, page3, timer3, page4, timer4, page5, timer5, avaliable) "
					+ "VALUES ( null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, true );";

			ps = conn.prepareStatement(sql);

			ps.setString(1, dt_creation);
			ps.setString(2, user);
			ps.setString(3, dt_creation);
			ps.setString(4, user);
			for (int i = 0; i < pages.length; i++) {
				if (pages[i]) {
					ps.setInt(5 + i * 2, pageId.get(i));
					ps.setFloat(6 + i * 2, pageTimer.get(i));
				} else {
					ps.setInt(5 + i * 2, 0);
					ps.setInt(6 + i * 2, 0);
				}
			}

			ps.executeUpdate();

		} catch (

		SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}
	}

	// Edit messages and pages
	// @Param ListaPages
	// type
	// name
	// image
	// image_id
	// timer
	// line1
	// line2
	// line3
	// @Param msgID
	// @Param user
	public void editMessage(int msgID, String user, List<Map<String, String>> ListaPages) throws Exception {

		DateTimeApplication dt = new DateTimeApplication();
		String dt_creation = dt.currentStringDate(DateTimeApplication.DATE_TIME_FORMAT_STANDARD_DATABASE);

		try {
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			// boolean[] pages = new boolean[] { false, false, false, false, false };
			// List<Integer> pageId = new ArrayList<Integer>();
			List<Float> pageTimer = new ArrayList<Float>();
			// int count = 0;
			int[] idPage;

			ps1 = conn.prepareStatement(
					"SELECT page1, page2, page3, page4, page5 FROM tracevia_app.pmv_messages_available WHERE id_message = ?;");
			ps1.setInt(1, msgID);

			rs1 = ps1.executeQuery();

			if (rs1.isBeforeFirst()) {
				rs1.next();
				idPage = new int[] { rs1.getInt("page1"), rs1.getInt("page2"), rs1.getInt("page3"), rs1.getInt("page4"),
						rs1.getInt("page5") };
			} else {
				createMessage(msgID, user, ListaPages);
				return;
			}

			for (int i = 0; i < ListaPages.size(); i++) {
				Map<String, String> page = ListaPages.get(i);

				if (idPage[i] != 0) {
					String sql = "UPDATE tracevia_app.pmv_messages SET id_image = ?, type = ?, name = ?, text1 = ?, text2 = ?, text3 =? WHERE (id_message = ?);";

					ps = conn.prepareStatement(sql);
					ps.setInt(1, Integer.parseInt(page.get("image_id")));
					ps.setString(2, page.get("type"));
					ps.setString(3, page.get("name"));
					ps.setString(4, page.get("line1"));
					ps.setString(5, page.get("line2"));
					ps.setString(6, page.get("line3"));
					ps.setInt(7, idPage[i]);

					ps.executeUpdate();
				} else {
					int id;

					ps1 = conn.prepareStatement("SELECT Max(id_message) as user FROM tracevia_app.pmv_messages;");
					rs1 = ps1.executeQuery();

					if (rs1.isBeforeFirst()) {
						rs1.next();
						id = rs1.getInt("user") + 1;
					} else
						id = 1;

					String sql = "INSERT INTO tracevia_app.pmv_messages "
							+ "(id_message, creation_date,  creation_username, type, name, id_image, text1, text2, text3, enabled) "
							+ "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, true ); ";

					ps = conn.prepareStatement(sql);
					ps.setInt(1, id);
					ps.setString(2, dt_creation);
					ps.setString(3, user);
					ps.setString(4, page.get("type"));
					ps.setString(5, page.get("name"));
					ps.setInt(6, Integer.parseInt(page.get("image_id")));
					ps.setString(7, page.get("line1"));
					ps.setString(8, page.get("line2"));
					ps.setString(9, page.get("line3"));

					ps.executeUpdate();

					idPage[i] = id;
				}

				// pageId.add(id);
				pageTimer.add(Float.parseFloat(page.get("timer")));

				// pages[count] = true;
				// count++;
			}

			String sql = "UPDATE tracevia_app.pmv_messages_available SET update_date = ?, update_username = ?, "
					+ "page1 = ?, timer1 = ?, page2 = ?, timer2 = ?, page3 = ?, timer3 = ?, "
					+ "page4 = ?, timer4 = ?, page5 = ?, timer5 = ? WHERE (id_message = ?);";

			ps = conn.prepareStatement(sql);

			ps.setString(1, dt_creation);
			ps.setString(2, user);
			for (int i = 0; i < idPage.length; i++) {
				if (idPage[i] != 0) {
					ps.setInt(3 + i * 2, idPage[i]);
					if (pageTimer.size() > i)
						ps.setFloat(4 + i * 2, pageTimer.get(i));
					else
						ps.setFloat(4 + i * 2, 0);

				} else {
					ps.setInt(3 + i * 2, 0);
					ps.setFloat(4 + i * 2, 0);
				}
			}

			ps.executeUpdate();

		} catch (

		SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}
	}

	public List<Messages> availableMessagesByType(String type) throws Exception {

		List<Messages> lista = new ArrayList<Messages>();

		try {

			String query = "";

			if (!type.equals("All"))
				query = "SELECT id_message, type, name, id_image, text1, text2, text3 FROM tracevia_app.pmv_messages_available "
						+ "WHERE id_message <> 1 and enabled <> 0 and type = '" + type + "' ";

			else
				query = "SELECT id_message, type, name, id_image, text1, text2, text3 FROM tracevia_app.pmv_messages_available "
						+ "WHERE id_message <> 1 and enabled <> 0 ";

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(query);

			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					Messages mensagens = new Messages();

					mensagens.setId_message(rs.getInt("id_message"));
					mensagens.setTipo(rs.getString("type"));
					mensagens.setNome(rs.getString("name"));
					mensagens.setId_image(rs.getInt("id_image"));
					mensagens.setImage(getImageFromMessageAvailable(rs.getInt("id_image")));

					// mensagens.setPages("", "", "", 0); // TODO: Corrigir query e setpages

					lista.add(mensagens);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return lista;
	}

	public boolean insertMessage(int imageID, String user, String tipo, String nome, String texto1, String texto2,
			String texto3) throws Exception {

		DateTimeApplication dt = new DateTimeApplication();
		boolean status = false;

		if (texto1 == null && texto2 == null && texto3 == null)
			throw new Exception("O valor passado nao pode ser nulo");

		try {

			String sql = "INSERT INTO tracevia_app.pmv_messages_available (id_message, creation_date,  creation_username, type, name, id_image, "
					+ "text1, text2, text3, enabled) " + "VALUES( null, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

			// Retorna data atual (data do sistema como par�metro)
			String dt_creation = dt.currentStringDate(DateTimeApplication.DATE_TIME_FORMAT_STANDARD_DATABASE);

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(sql);
			ps.setString(1, dt_creation);
			ps.setString(2, user);
			ps.setString(3, tipo);
			ps.setString(4, nome);
			ps.setInt(5, imageID);
			ps.setString(6, texto1);
			ps.setString(7, texto2);
			ps.setString(8, texto3);
			ps.setBoolean(9, true);

			ps.executeUpdate();
			status = true;

		} catch (SQLException sqle) {
			throw new Exception("Erro ao inserir dados " + sqle);

		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}

		return status;

	}

	public boolean updateMessage(int imageID, String user, String tipo, String nome, String texto1, String texto2,
			String texto3, String id) throws Exception {

		boolean status = false;
		DateTimeApplication dt = new DateTimeApplication();

		try {

			String sql = "UPDATE tracevia_app.pmv_messages_available SET id_image = ?, update_date = ?, update_username = ?, type = ?, name = ?, text1 = ?, text2 = ?, text3 = ? WHERE id_message = ?";

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			String dt_update = dt.currentStringDate(DateTimeApplication.DATE_TIME_FORMAT_STANDARD_DATABASE);

			ps = conn.prepareStatement(sql);
			ps.setInt(1, imageID);
			ps.setString(2, dt_update);
			ps.setString(3, user);
			ps.setString(4, tipo);
			ps.setString(5, nome);
			ps.setString(6, texto1);
			ps.setString(7, texto2);
			ps.setString(8, texto3);
			ps.setInt(9, Integer.parseInt(id));

			ps.executeUpdate();
			status = true;

		} catch (SQLException sqle) {

			throw new Exception("Erro ao atualizar dados " + sqle);

		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}

		return status;

	}

	public Messages mensagensDisponivelById(int id) throws Exception {

		Messages mensagem = new Messages();

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(
					"SELECT id_message, id_image,type, name, text1, text2, text3 FROM tracevia_app.pmv_messages_available WHERE id_message = ?");
			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					mensagem.setId_message(rs.getInt("id_message"));
					mensagem.setId_image(rs.getInt("id_image"));
					mensagem.setImage(getImageFromMessageAvailable(rs.getInt("id_image")));
					mensagem.setTipo(rs.getString("type"));
					mensagem.setNome(rs.getString("name"));

					// mensagem.setPages("", "", "", 0); // TODO: Corrigir query e setpages
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return mensagem;
	}

	public String getImageFromMessageAvailable(int id) {

		String image = null;

		if (id < 10)
			image = "00" + id + "_6464.bmp";

		else if (id >= 10 && id < 100)
			image = "0" + id + "_6464.bmp";

		else if (id > 100 && id <= 105)
			image = id + "_6464.bmp";

		else if (id == 106)
			image = "logo ATT.bmp";

		else if (id == 107)
			image = "logo ATT2.bmp";

		return image;
	}

	public Messages getDMSmessages(int equip) throws Exception {

		Messages mensagem = new Messages();

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(
					"SELECT id_message, id_image, text1, text2, text3 FROM tracevia_app.pmv_messages_available WHERE id_message = (SELECT id_message FROM tracevia_app.pmv_messages_active WHERE id_equip = ?)");
			ps.setInt(1, equip);

			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					mensagem.setId_message(rs.getInt("id_message"));
					mensagem.setId_image(rs.getInt("id_image"));
					mensagem.setImage(getImageFromMessageAvailable(rs.getInt("id_image")));

					// mensagem.setPages("", "", "", 0); // TODO: Corrigir query e setpages
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return mensagem;

	}

	public Messages selectMessageToUpdate(int equip) throws Exception {

		Messages mensagem = new Messages();

		try {

			String sql = "SELECT d.id_image, d.text1, d.text2, d.text3, a.id_modify FROM tracevia_app.pmv_messages_available d "
					+ "INNER JOIN tracevia_app.pmv_messages_active a "
					+ "ON a.id_modify = d.id_message WHERE id_equip = ? ";

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(sql);
			ps.setInt(1, equip);

			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					mensagem.setId_image(rs.getInt("d.id_image"));
					mensagem.setImage(getImageFromMessageAvailable(rs.getInt("d.id_image")));
					mensagem.setId_modify(rs.getInt("a.id_modify"));

					// mensagem.setPages("", "", "", 0); // TODO: Corrigir query e setpages
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return mensagem;
	}

	// Messages Actives Bean
	public List<Messages> selectActivesMessages() throws Exception {

		List<Messages> lista = new ArrayList<Messages>();

		try {

			String sql = "SELECT d.id_image, d.text1, d.text2, d.text3, a.id_equip, a.id_modify, a.active_status "
					+ "FROM tracevia_app.pmv_messages_available d " + "INNER JOIN tracevia_app.pmv_messages_active a "
					+ "ON a.id_message = d.id_message ";

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					Messages message = new Messages();

					message.setEquip(rs.getInt("a.id_equip"));
					message.setId_modify(rs.getInt("a.id_modify"));
					message.setId_image(rs.getInt("d.id_image"));
					message.setImage(getImageFromMessageAvailable(rs.getInt("d.id_image")));
					message.setActiveMessage(rs.getBoolean("a.active_status"));

					// message.setPages("", "", "", 0); // TODO: Corrigir query e setpages

					lista.add(message);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return lista;
	}

	public boolean updateModifyMessage(int messageID, int equip) throws Exception {

		boolean status = false;

		try {

			/*
			 * DateFormat dfm = new SimpleDateFormat("yyy-MM-dd HH:mm:ss"); Date date = new
			 * Date(); String strDate = dfm.format(date); s
			 */

			String sql = "UPDATE tracevia_app.pmv_messages_active SET id_modify = ?, active_status = ? WHERE id_equip = ?";

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(sql);
			ps.setInt(1, messageID);
			ps.setBoolean(2, false);
			ps.setInt(3, equip);

			ps.executeUpdate();
			status = true;

		} catch (SQLException sqle) {

			throw new Exception("Erro ao atualizar dados " + sqle);

		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}

		return status;

	}

	public List<Messages> checkModifyMessageState(int[] equip) throws Exception {

		List<Messages> list = new ArrayList<Messages>();

		int count = 0;

		for (int i = 0; i < equip.length; i++) {
			if (equip[i] != 0)
				count++;
		}

		try {

			String query = "SELECT p.name, m.active_status  FROM tracevia_app.pmv_messages_active m "
					+ "INNER JOIN tracevia_app.pmv_equipment p ON p.equip_id = m.id_equip " + "WHERE m.id_equip IN(";

			for (int i = 0; i < count; i++) {
				query += "'" + equip[i] + "'";

				if (equip[i] != equip[count - 1]) // Caso seja maior que 1 => equip , ent�o add ','
					query += " , ";

				else
					query += ") ";

			}

			// System.out.println("COUNT: "+count);

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(query);

			// System.out.println(query);

			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					Messages msg = new Messages();

					msg.setNome(rs.getString(1));
					msg.setActiveMessage(rs.getBoolean(2));

					list.add(msg);

				}
			}

		} catch (SQLException sqle) {

			throw new Exception("Erro ao recuperar dados " + sqle);

		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}

		return list;

	}

	public boolean removeRegister(int id) throws Exception {

		boolean response = false;

		if (id == 0)
			throw new Exception("O valor passado nao pode ser nulo");

		try {

			String sql = "UPDATE tracevia_app.pmv_messages_available SET enabled = 0 WHERE id_message = ?";

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(sql);

			ps.setInt(1, id);

			ps.executeUpdate();

			response = true;

		} catch (SQLException sqle) {
			throw new Exception("Erro ao atualizar dado " + sqle);
		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}

		return response;
	}

	public Integer verifyRegisterIsActive(int id) throws Exception {

		int response = 0;

		if (id == 0)
			throw new Exception("O valor passado nao pode ser nulo");

		try {

			String sql = "SELECT COUNT(ac.active_status) AS status FROM tracevia_app.pmv_messages_available av "
					+ "INNER JOIN tracevia_app.pmv_messages_active ac ON ac.id_message = av.id_message "
					+ "WHERE av.id_message = ? AND ac.active_status IN(0,1) ";

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					response = rs.getInt("status");

				}
			}

		} catch (SQLException sqle) {
			throw new Exception("Erro ao atualizar dado " + sqle);
		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}

		return response;
	}

	/*
	 * 
	 * public boolean deletarRegistro(int id) throws Exception {
	 * 
	 * boolean response = false;
	 * 
	 * if (id == 0) throw new Exception("O valor passado nao pode ser nulo");
	 * 
	 * try {
	 * 
	 * String sql =
	 * "DELETE FROM tracevia_app.pmv_messages_available WHERE id_message = ?";
	 * 
	 * conn =
	 * ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
	 * 
	 * ps = conn.prepareStatement(sql);
	 * 
	 * ps.setInt(1, id);
	 * 
	 * ps.executeUpdate();
	 * 
	 * response = true;
	 * 
	 * } catch (SQLException sqle) { throw new Exception("Erro ao deletar dados " +
	 * sqle); } finally { ConnectionFactory.closeConnection(conn, ps); }
	 * 
	 * return response; }
	 */

	public boolean updateActivesMessages(int[] msg, int[] equip) throws Exception {

		boolean status = false;

		try {

			int count = 0;

			for (int i = 0; i < equip.length; i++) {
				if (equip[i] != 0)
					count++;

				// System.out.println(equip[i]);
			}

			/*
			 * DateFormat dfm = new SimpleDateFormat("yyy-MM-dd HH:mm:ss"); Date date = new
			 * Date(); String strDate = dfm.format(date); s
			 */

			String sql = "";

			for (int i = 0; i < count; i++) {

				// sql = "UPDATE tb_mensagens_ativas SET id_mensagem = '"+msg[i]+"' WHERE
				// id_equip = '"+equip[i]+"' ";

				sql = "UPDATE tracevia_app.pmv_messages_active SET id_modify = '" + msg[i]
						+ "', active_status = false WHERE id_equip = '" + equip[i] + "' ";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
				ps = conn.prepareStatement(sql);

				ps.executeUpdate();

			}

			status = true;

		} catch (SQLException sqle) {

			throw new Exception("Erro ao atualizar dados " + sqle);

		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}

		return status;

	}

	public boolean updateCleanMessages(int msg, int[] equip) throws Exception {

		boolean status = false;

		try {

			int count = 0;

			for (int i = 0; i < equip.length; i++) {
				if (equip[i] != 0)
					count++;
			}

			/*
			 * DateFormat dfm = new SimpleDateFormat("yyy-MM-dd HH:mm:ss"); Date date = new
			 * Date(); String strDate = dfm.format(date); s
			 */

			String sql = "";

			for (int i = 0; i < count; i++) {

				sql = "UPDATE tracevia_app.pmv_messages_active SET id_message = '" + msg + "' WHERE id_equip = '"
						+ equip[i] + "' ";

				// sql = "UPDATE tracevia_app.pmv_messages_active SET id_modify = '"+msg+"',
				// active_status = false WHERE id_equip = '"+equip[i]+"' ";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				ps = conn.prepareStatement(sql);

				ps.executeUpdate();

			}

			status = true;

		} catch (SQLException sqle) {

			throw new Exception("Erro ao atualizar dados " + sqle);

		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}

		return status;

	}

}
