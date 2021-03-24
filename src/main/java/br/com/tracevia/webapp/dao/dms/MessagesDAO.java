
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
}
