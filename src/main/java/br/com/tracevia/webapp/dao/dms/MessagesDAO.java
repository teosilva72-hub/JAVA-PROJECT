
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
	private PreparedStatement ps;
	private ResultSet rs;

	// LanguageMB lang;
	Locale locale;
	ResourceBundle resourceBundle;

	public List<Messages> mensagensDisponiveis(String driver) throws Exception {

		List<Messages> lista = new ArrayList<Messages>();

		try {
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			switch (driver) {
			case "driver1":
				ps = conn.prepareStatement("SELECT id_message, type, name, "
						+ "page1_image, page1_1, page1_2, page1_3, timer1, "
						+ "page2_image, page2_1, page2_2, page2_3, timer2, "
						+ "page3_image, page3_1, page3_2, page3_3, timer3, "
						+ "page4_image, page4_1, page4_2, page4_3, timer4, "
						+ "page5_image, page5_1, page5_2, page5_3, timer5 from pmv_messages_available WHERE avaliable <> 0 "
						+ "AND driver = 1 ORDER BY id_message ASC");
				rs = ps.executeQuery();

				if (rs.isBeforeFirst()) {
					while (rs.next()) {
						Messages mensagens = new Messages();
						mensagens.setId_message(rs.getInt("id_message"));
						mensagens.setTipo(rs.getString("type"));
						mensagens.setNome(rs.getString("name"));
						mensagens.setDriver(1);

						for (int i = 0; i < 5; i++) {
							int idx = i + 1;
							if (rs.getFloat("timer" + idx) != .0) {
								mensagens.setPages(rs.getString("page" + idx + "_1"), rs.getString("page" + idx + "_2"),
										rs.getString("page" + idx + "_3"), rs.getInt("page" + idx + "_image"),
										getImageFromMessageAvailable(rs.getInt("page" + idx + "_image")),
										rs.getFloat("timer" + idx), idx);
							} else {
								mensagens.setPages(i);
							}
						}
						lista.add(mensagens);
					}
				}
				break;

			case "driver2":
				ps = conn.prepareStatement("SELECT id_message, type, name, "
						+ "page1_image, page1_1, page1_2, timer1, page2_image, page2_1, page2_2, timer2, "
						+ "page3_image, page3_1, page3_2, timer3, page4_image, page4_1, page4_2, timer4, "
						+ "page5_image, page5_1, page5_2, timer5 from pmv_messages_available WHERE avaliable <> 0 "
						+ "AND driver = 2 ORDER BY id_message ASC");
				rs = ps.executeQuery();

				if (rs.isBeforeFirst()) {
					while (rs.next()) {
						Messages mensagens = new Messages();
						mensagens.setId_message(rs.getInt("id_message"));
						mensagens.setTipo(rs.getString("type"));
						mensagens.setNome(rs.getString("name"));
						mensagens.setDriver(2);

						for (int i = 0; i < 5; i++) {
							int idx = i + 1;
							if (rs.getFloat("timer" + idx) != .0) {
								mensagens.setPages(rs.getString("page" + idx + "_1"), rs.getString("page" + idx + "_2"),
										rs.getInt("page" + idx + "_image"),
										getImageFromMessageAvailable(rs.getInt("page" + idx + "_image")),
										rs.getFloat("timer" + idx), idx);
							} else {
								mensagens.setPages(i);
							}
						}
						lista.add(mensagens);
					}
				}

				break;

			case "driver3":
				ps = conn.prepareStatement("SELECT id_message, type, name, "
						+ "page1_image, page1_image2, page1_1, page1_2, page1_3, timer1, "
						+ "page2_image, page2_image2, page2_1, page2_2, page2_3, timer2, "
						+ "page3_image, page3_image2, page3_1, page3_2, page3_3, timer3, "
						+ "page4_image, page4_image2, page4_1, page4_2, page4_3, timer4, "
						+ "page5_image, page5_image2, page5_1, page5_2, page5_3, timer5 from pmv_messages_available WHERE avaliable <> 0 "
						+ "AND driver = 3 ORDER BY id_message ASC");
				rs = ps.executeQuery();

				if (rs.isBeforeFirst()) {
					while (rs.next()) {
						Messages mensagens = new Messages();
						mensagens.setId_message(rs.getInt("id_message"));
						mensagens.setTipo(rs.getString("type"));
						mensagens.setNome(rs.getString("name"));
						mensagens.setDriver(3);

						for (int i = 0; i < 5; i++) {
							int idx = i + 1;
							if (rs.getFloat("timer" + idx) != .0) {
								mensagens.setPages(rs.getString("page" + idx + "_1"), rs.getString("page" + idx + "_2"),
										rs.getString("page" + idx + "_3"), rs.getInt("page" + idx + "_image"),
										getImageFromMessageAvailable(rs.getInt("page" + idx + "_image")),
										rs.getInt("page" + idx + "_image2"),
										getImageFromMessageAvailable(rs.getInt("page" + idx + "_image2")),
										rs.getFloat("timer" + idx), idx);
							} else {
								mensagens.setPages(i);
							}
						}
						lista.add(mensagens);
					}
				}

				break;

			default:
				break;
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

	// Create new messages and pages
	// @Param ListaPages
	// image
	// image_id
	// timer
	// line1
	// line2
	// line3
	// @Param msgID
	// @Param user
	public boolean createMessage(Map<String, String> msg, String user, List<Map<String, String>> ListaPages)
			throws Exception {
		boolean success;

		DateTimeApplication dt = new DateTimeApplication();
		String dt_creation = dt.currentStringDate(DateTimeApplication.DATE_TIME_FORMAT_STANDARD_DATABASE);

		try {
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			String sql = "INSERT INTO pmv_messages_available "
					+ "(id_message, creation_date, creation_username, update_date, update_username, type, name, driver, "
					+ "page1_image, page1_image2, page1_1, page1_2, page1_3, timer1, "
					+ "page2_image, page2_image2, page2_1, page2_2, page2_3, timer2, "
					+ "page3_image, page3_image2, page3_1, page3_2, page3_3, timer3, "
					+ "page4_image, page4_image2, page4_1, page4_2, page4_3, timer4, "
					+ "page5_image, page5_image2, page5_1, page5_2, page5_3, timer5, avaliable) "
					+ "VALUES ( null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, true );";

			ps = conn.prepareStatement(sql);

			ps.setString(1, dt_creation);
			ps.setString(2, user);
			ps.setString(3, dt_creation);
			ps.setString(4, user);
			ps.setString(5, msg.get("type"));
			ps.setString(6, msg.get("name"));
			ps.setInt(7, Integer.parseInt(msg.get("type_page")));
			for (int i = 0; i < 5; i++) {
				if (i < ListaPages.size()) {
					ps.setInt(8 + i * 6, Integer.parseInt(ListaPages.get(i).get("image_id")));
					ps.setInt(9 + i * 6, Integer.parseInt(ListaPages.get(i).get("image_id2")));
					ps.setString(10 + i * 6, ListaPages.get(i).get("line1"));
					ps.setString(11 + i * 6, ListaPages.get(i).get("line2"));
					ps.setString(12 + i * 6, ListaPages.get(i).get("line3"));
					ps.setFloat(13 + i * 6, Float.parseFloat(ListaPages.get(i).get("timer")));
				} else {
					ps.setInt(8 + i * 6, 0);
					ps.setInt(9 + i * 6, 0);
					ps.setString(10 + i * 6, "");
					ps.setString(11 + i * 6, "");
					ps.setString(12 + i * 6, "");
					ps.setInt(13 + i * 6, 0);
				}
			}

			ps.executeUpdate();

			success = true;
		} catch (SQLException e) {
			e.printStackTrace();

			success = false;
		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}

		return success;
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
	public boolean editMessage(Map<String, String> msg, String user, List<Map<String, String>> ListaPages)
			throws Exception {
		boolean success;

		DateTimeApplication dt = new DateTimeApplication();
		String dt_creation = dt.currentStringDate(DateTimeApplication.DATE_TIME_FORMAT_STANDARD_DATABASE);

		try {
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			String sql = "UPDATE pmv_messages_available SET update_date = ?, update_username = ?, type = ?, name = ?, "
					+ "page1_image = ?, page1_image2 = ?, page1_1 = ?, page1_2 = ?, page1_3 = ?, timer1 = ?, "
					+ "page2_image = ?, page2_image2 = ?, page2_1 = ?, page2_2 = ?, page2_3 = ?, timer2 = ?, "
					+ "page3_image = ?, page3_image2 = ?, page3_1 = ?, page3_2 = ?, page3_3 = ?, timer3 = ?, "
					+ "page4_image = ?, page4_image2 = ?, page4_1 = ?, page4_2 = ?, page4_3 = ?, timer4 = ?, "
					+ "page5_image = ?, page5_image2 = ?, page5_1 = ?, page5_2 = ?, page5_3 = ?, timer5 = ? WHERE (id_message = ? AND driver = ?);";

			ps = conn.prepareStatement(sql);

			ps.setString(1, dt_creation);
			ps.setString(2, user);
			ps.setString(3, msg.get("type"));
			ps.setString(4, msg.get("name"));
			for (int i = 0; i < 5; i++) {
				if (i < ListaPages.size()) {
					ps.setInt(5 + i * 6, Integer.parseInt(ListaPages.get(i).get("image_id")));
					ps.setInt(6 + i * 6, Integer.parseInt(ListaPages.get(i).get("image_id2")));
					ps.setString(7 + i * 6, ListaPages.get(i).get("line1"));
					ps.setString(8 + i * 6, ListaPages.get(i).get("line2"));
					ps.setString(9 + i * 6, ListaPages.get(i).get("line3"));
					ps.setFloat(10 + i * 6, Float.parseFloat(ListaPages.get(i).get("timer")));
				} else {
					ps.setInt(5 + i * 6, 0);
					ps.setInt(6 + i * 6, 0);
					ps.setString(7 + i * 6, "");
					ps.setString(8 + i * 6, "");
					ps.setString(9 + i * 6, "");
					ps.setInt(10 + i * 6, 0);
				}
			}
			ps.setInt(35, Integer.parseInt(msg.get("id")));
			ps.setInt(36, Integer.parseInt(msg.get("type_page")));

			ps.executeUpdate();

			success = true;
		} catch (SQLException e) {
			e.printStackTrace();

			success = false;
		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}

		return success;
	}

	public boolean removeMessage(int msgID, String user) throws Exception {
		boolean success;
		try {
			DateTimeApplication dt = new DateTimeApplication();
			String dt_creation = dt.currentStringDate(DateTimeApplication.DATE_TIME_FORMAT_STANDARD_DATABASE);

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(
					"UPDATE pmv_messages_available SET update_date = ?, update_username = ?, avaliable = 0 WHERE (id_message = ?);");
			ps.setString(1, dt_creation);
			ps.setString(2, user);
			ps.setInt(3, msgID);

			ps.executeUpdate();

			success = true;
		} catch (

		SQLException e) {
			e.printStackTrace();
			success = false;
		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}

		return success;
	}

	public Messages mensagensDisponivelById(int driver, int id) throws Exception {

		Messages mensagem = new Messages();

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			switch (driver) {
			case 1:
				ps = conn.prepareStatement("SELECT id_message, type, name, "
						+ "page1_image, page1_1, page1_2, page1_3, timer1, "
						+ "page2_image, page2_1, page2_2, page2_3, timer2, "
						+ "page3_image, page3_1, page3_2, page3_3, timer3, "
						+ "page4_image, page4_1, page4_2, page4_3, timer4, "
						+ "page5_image, page5_1, page5_2, page5_3, timer5 from pmv_messages_available WHERE avaliable <> 0 "
						+ "AND driver = 1 AND id_message = " + id + " ORDER BY id_message ASC");
				rs = ps.executeQuery();

				if (rs.isBeforeFirst()) {
					while (rs.next()) {
						mensagem.setId_message(rs.getInt("id_message"));
						mensagem.setTipo(rs.getString("type"));
						mensagem.setNome(rs.getString("name"));

						for (int i = 0; i < 5; i++) {
							int idx = i + 1;
							if (rs.getFloat("timer" + idx) != .0) {
								mensagem.setPages(rs.getString("page" + idx + "_1"), rs.getString("page" + idx + "_2"),
										rs.getString("page" + idx + "_3"), rs.getInt("page" + idx + "_image"),
										getImageFromMessageAvailable(rs.getInt("page" + idx + "_image")),
										rs.getFloat("timer" + idx), idx);
							} else {
								mensagem.setPages(i);
							}
						}
					}
				}
				break;

			case 2:
				ps = conn.prepareStatement("SELECT id_message, type, name, "
						+ "page1_image, page1_1, page1_2, timer1, page2_image, page2_1, page2_2, timer2, "
						+ "page3_image, page3_1, page3_2, timer3, page4_image, page4_1, page4_2, timer4, "
						+ "page5_image, page5_1, page5_2, timer5 from pmv_messages_available WHERE avaliable <> 0 "
						+ "AND driver = 2 AND id_message = " + id + " ORDER BY id_message ASC");
				rs = ps.executeQuery();

				if (rs.isBeforeFirst()) {
					while (rs.next()) {
						mensagem.setId_message(rs.getInt("id_message"));
						mensagem.setTipo(rs.getString("type"));
						mensagem.setNome(rs.getString("name"));

						for (int i = 0; i < 5; i++) {
							int idx = i + 1;
							if (rs.getFloat("timer" + idx) != .0) {
								mensagem.setPages(rs.getString("page" + idx + "_1"), rs.getString("page" + idx + "_2"),
										rs.getInt("page" + idx + "_image"),
										getImageFromMessageAvailable(rs.getInt("page" + idx + "_image")),
										rs.getFloat("timer" + idx), idx);
							} else {
								mensagem.setPages(i);
							}
						}
					}
				}

				break;

			case 3:
				ps = conn.prepareStatement("SELECT id_message, type, name, "
						+ "page1_image, page1_image2, page1_1, page1_2, page1_3, timer1, "
						+ "page2_image, page2_image2, page2_1, page2_2, page2_3, timer2, "
						+ "page3_image, page3_image2, page3_1, page3_2, page3_3, timer3, "
						+ "page4_image, page4_image2, page4_1, page4_2, page4_3, timer4, "
						+ "page5_image, page5_image2, page5_1, page5_2, page5_3, timer5 from pmv_messages_available WHERE avaliable <> 0 "
						+ "AND driver = 3 AND id_message = " + id + " ORDER BY id_message ASC");
				rs = ps.executeQuery();

				if (rs.isBeforeFirst()) {
					while (rs.next()) {
						mensagem.setId_message(rs.getInt("id_message"));
						mensagem.setTipo(rs.getString("type"));
						mensagem.setNome(rs.getString("name"));

						for (int i = 0; i < 5; i++) {
							int idx = i + 1;
							if (rs.getFloat("timer" + idx) != .0) {
								mensagem.setPages(rs.getString("page" + idx + "_1"), rs.getString("page" + idx + "_2"),
										rs.getString("page" + idx + "_3"), rs.getInt("page" + idx + "_image"),
										getImageFromMessageAvailable(rs.getInt("page" + idx + "_image")),
										rs.getInt("page" + idx + "_image2"),
										getImageFromMessageAvailable(rs.getInt("page" + idx + "_image2")),
										rs.getFloat("timer" + idx), idx);
							} else {
								mensagem.setPages(i);
							}
						}
					}
				}

				break;

			default:
				mensagem.setPages(0);

				break;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		mensagem.setDriver(driver);
		
		if (!mensagem.revision())
			mensagem.setPages(0);
		
		return mensagem;
	}

	public List<Messages> availableMessagesByType(String type) throws Exception {

		List<Messages> lista = new ArrayList<Messages>();

		try {

			String query = "";

			if (!type.equals("All"))
				query = "SELECT id_message, type, name, id_image, text1, text2, text3 FROM pmv_messages_available "
						+ "WHERE id_message <> 1 and enabled <> 0 and type = '" + type + "' ";

			else
				query = "SELECT id_message, type, name, id_image, text1, text2, text3 FROM pmv_messages_available "
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
					"SELECT id_message, id_image, text1, text2, text3 FROM pmv_messages_available WHERE id_message = (SELECT id_message FROM pmv_messages_active WHERE id_equip = ?)");
			ps.setInt(1, equip);

			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					mensagem.setId_message(rs.getInt("id_message"));

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

			String sql = "SELECT d.id_image, d.text1, d.text2, d.text3, a.id_modify FROM pmv_messages_available d "
					+ "INNER JOIN pmv_messages_active a "
					+ "ON a.id_modify = d.id_message WHERE id_equip = ? ";

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(sql);
			ps.setInt(1, equip);

			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {

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

		try { // Todo: Atualmente incompativel

			String sql = "SELECT d.id_image, d.text1, d.text2, d.text3, a.id_equip, a.id_modify, a.active_status "
					+ "FROM pmv_messages_available d " + "INNER JOIN pmv_messages_active a "
					+ "ON a.id_message = d.id_message ";

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					Messages message = new Messages();

					message.setEquip(rs.getInt("a.id_equip"));
					message.setId_modify(rs.getInt("a.id_modify"));
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

			String sql = "UPDATE pmv_messages_active SET id_modify = ?, active_status = ? WHERE id_equip = ?";

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

			String query = "SELECT p.name, m.active_status  FROM pmv_messages_active m "
					+ "INNER JOIN pmv_equipment p ON p.equip_id = m.id_equip " + "WHERE m.id_equip IN(";

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

			String sql = "UPDATE pmv_messages_available SET enabled = 0 WHERE id_message = ?";

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

			String sql = "SELECT COUNT(ac.active_status) AS status FROM pmv_messages_available av "
					+ "INNER JOIN pmv_messages_active ac ON ac.id_message = av.id_message "
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
	 * "DELETE FROM pmv_messages_available WHERE id_message = ?";
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

				sql = "UPDATE pmv_messages_active SET id_modify = '" + msg[i]
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

				sql = "UPDATE pmv_messages_active SET id_message = '" + msg + "' WHERE id_equip = '"
						+ equip[i] + "' ";

				// sql = "UPDATE pmv_messages_active SET id_modify = '"+msg+"',
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
