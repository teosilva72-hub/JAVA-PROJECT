
package br.com.tracevia.webapp.dao.dms;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.dms.Messages;
import br.com.tracevia.webapp.model.global.SQL_Tracevia;
import br.com.tracevia.webapp.model.global.ColumnsSql.RowResult;
import br.com.tracevia.webapp.model.global.ResultSql.MapResult;

public class MessagesDAO {

	SQL_Tracevia conn = new SQL_Tracevia();

	// LanguageMB lang;
	Locale locale;
	ResourceBundle resourceBundle;

	public List<Messages> mensagensDisponiveis(String driver) throws Exception {

		List<Messages> lista = new ArrayList<Messages>();
		MapResult result;

		try {
			conn.start(1);

			switch (driver) {
			case "driver1":
				conn.prepare("SELECT id_message, type, name, "
						+ "page1_image, page1_1, page1_2, page1_3, timer1, "
						+ "page2_image, page2_1, page2_2, page2_3, timer2, "
						+ "page3_image, page3_1, page3_2, page3_3, timer3, "
						+ "page4_image, page4_1, page4_2, page4_3, timer4, "
						+ "page5_image, page5_1, page5_2, page5_3, timer5 from dms_messages_available WHERE avaliable <> 0 "
						+ "AND driver = 1 ORDER BY id_message ASC");
				result = conn.executeQuery();

				if (result.hasNext()) {
					for (RowResult rs : result) {
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
				conn.prepare("SELECT id_message, type, name, "
						+ "page1_image, page1_1, page1_2, timer1, page2_image, page2_1, page2_2, timer2, "
						+ "page3_image, page3_1, page3_2, timer3, page4_image, page4_1, page4_2, timer4, "
						+ "page5_image, page5_1, page5_2, timer5 from dms_messages_available WHERE avaliable <> 0 "
						+ "AND driver = 2 ORDER BY id_message ASC");
				result = conn.executeQuery();

				if (result.hasNext()) {
					for (RowResult rs : result) {
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
				conn.prepare("SELECT id_message, type, name, "
						+ "page1_image, page1_image2, page1_1, page1_2, page1_3, timer1, "
						+ "page2_image, page2_image2, page2_1, page2_2, page2_3, timer2, "
						+ "page3_image, page3_image2, page3_1, page3_2, page3_3, timer3, "
						+ "page4_image, page4_image2, page4_1, page4_2, page4_3, timer4, "
						+ "page5_image, page5_image2, page5_1, page5_2, page5_3, timer5 from dms_messages_available WHERE avaliable <> 0 "
						+ "AND driver = 3 ORDER BY id_message ASC");
				result = conn.executeQuery();

				if (result.hasNext()) {
					for (RowResult rs : result) {
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

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
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
			conn.start(1);

			String sql = "INSERT INTO dms_messages_available "
					+ "(id_message, creation_date, creation_username, update_date, update_username, type, name, driver, "
					+ "page1_image, page1_image2, page1_1, page1_2, page1_3, timer1, "
					+ "page2_image, page2_image2, page2_1, page2_2, page2_3, timer2, "
					+ "page3_image, page3_image2, page3_1, page3_2, page3_3, timer3, "
					+ "page4_image, page4_image2, page4_1, page4_2, page4_3, timer4, "
					+ "page5_image, page5_image2, page5_1, page5_2, page5_3, timer5, avaliable) "
					+ "VALUES ( null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, true );";

			conn.prepare(sql);

			conn.setString(1, dt_creation);
			conn.setString(2, user);
			conn.setString(3, dt_creation);
			conn.setString(4, user);
			conn.setString(5, msg.get("type"));
			conn.setString(6, msg.get("name"));
			conn.setInt(7, Integer.parseInt(msg.get("type_page")));
			for (int i = 0; i < 5; i++) {
				if (i < ListaPages.size()) {
					conn.setInt(8 + i * 6, Integer.parseInt(ListaPages.get(i).get("image_id")));
					conn.setInt(9 + i * 6, Integer.parseInt(ListaPages.get(i).get("image_id2")));
					conn.setString(10 + i * 6, ListaPages.get(i).get("line1"));
					conn.setString(11 + i * 6, ListaPages.get(i).get("line2"));
					conn.setString(12 + i * 6, ListaPages.get(i).get("line3"));
					conn.setFloat(13 + i * 6, Float.parseFloat(ListaPages.get(i).get("timer")));
				} else {
					conn.setInt(8 + i * 6, 0);
					conn.setInt(9 + i * 6, 0);
					conn.setString(10 + i * 6, "");
					conn.setString(11 + i * 6, "");
					conn.setString(12 + i * 6, "");
					conn.setInt(13 + i * 6, 0);
				}
			}

			conn.executeUpdate();

			success = true;
		} catch (Exception e) {
			e.printStackTrace();

			success = false;
		} finally {
			conn.close();
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
		int id = Integer.parseInt(msg.get("id"));

		DMSDAO dms_dao = new DMSDAO();
		DateTimeApplication dt = new DateTimeApplication();
		String dt_creation = dt.currentStringDate(DateTimeApplication.DATE_TIME_FORMAT_STANDARD_DATABASE);

		try {
			conn.start(1);

			String sql = "UPDATE dms_messages_available SET update_date = ?, update_username = ?, type = ?, name = ?, "
					+ "page1_image = ?, page1_image2 = ?, page1_1 = ?, page1_2 = ?, page1_3 = ?, timer1 = ?, "
					+ "page2_image = ?, page2_image2 = ?, page2_1 = ?, page2_2 = ?, page2_3 = ?, timer2 = ?, "
					+ "page3_image = ?, page3_image2 = ?, page3_1 = ?, page3_2 = ?, page3_3 = ?, timer3 = ?, "
					+ "page4_image = ?, page4_image2 = ?, page4_1 = ?, page4_2 = ?, page4_3 = ?, timer4 = ?, "
					+ "page5_image = ?, page5_image2 = ?, page5_1 = ?, page5_2 = ?, page5_3 = ?, timer5 = ? WHERE (id_message = ? AND driver = ?);";

			conn.prepare(sql);

			conn.setString(1, dt_creation);
			conn.setString(2, user);
			conn.setString(3, msg.get("type"));
			conn.setString(4, msg.get("name"));
			for (int i = 0; i < 5; i++) {
				if (i < ListaPages.size()) {
					conn.setInt(5 + i * 6, Integer.parseInt(ListaPages.get(i).get("image_id")));
					conn.setInt(6 + i * 6, Integer.parseInt(ListaPages.get(i).get("image_id2")));
					conn.setString(7 + i * 6, ListaPages.get(i).get("line1"));
					conn.setString(8 + i * 6, ListaPages.get(i).get("line2"));
					conn.setString(9 + i * 6, ListaPages.get(i).get("line3"));
					conn.setFloat(10 + i * 6, Float.parseFloat(ListaPages.get(i).get("timer")));
				} else {
					conn.setInt(5 + i * 6, 0);
					conn.setInt(6 + i * 6, 0);
					conn.setString(7 + i * 6, "");
					conn.setString(8 + i * 6, "");
					conn.setString(9 + i * 6, "");
					conn.setInt(10 + i * 6, 0);
				}
			}
			conn.setInt(35, id);
			conn.setInt(36, Integer.parseInt(msg.get("type_page")));

			conn.executeUpdate();
			
			dms_dao.reloadActivateMessageWith(id);

			success = true;
		} catch (Exception e) {
			e.printStackTrace();

			success = false;
		} finally {
			conn.close();
		}

		return success;
	}

	public boolean removeMessage(int msgID, String user) throws Exception {
		boolean success;
		try {
			DateTimeApplication dt = new DateTimeApplication();
			String dt_creation = dt.currentStringDate(DateTimeApplication.DATE_TIME_FORMAT_STANDARD_DATABASE);

			conn.start(1);

			conn.prepare(
					"UPDATE dms_messages_available SET update_date = ?, update_username = ?, avaliable = 0 WHERE (id_message = ?);");
			conn.setString(1, dt_creation);
			conn.setString(2, user);
			conn.setInt(3, msgID);

			conn.executeUpdate();

			success = true;
		} catch (

		Exception e) {
			e.printStackTrace();
			success = false;
		} finally {
			conn.close();
		}

		return success;
	}

	public Messages mensagensDisponivelById(int driver, int id) throws Exception {

		Messages mensagem = new Messages();
		MapResult result;

		try {

			conn.start(1);

			switch (driver) {
			case 1:
				conn.prepare("SELECT id_message, type, name, "
						+ "page1_image, page1_1, page1_2, page1_3, timer1, "
						+ "page2_image, page2_1, page2_2, page2_3, timer2, "
						+ "page3_image, page3_1, page3_2, page3_3, timer3, "
						+ "page4_image, page4_1, page4_2, page4_3, timer4, "
						+ "page5_image, page5_1, page5_2, page5_3, timer5 from dms_messages_available WHERE avaliable <> 0 "
						+ "AND driver = 1 AND id_message = " + id + " ORDER BY id_message ASC");
				result = conn.executeQuery();

				if (result.hasNext()) {
					for (RowResult rs : result) {
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
				conn.prepare("SELECT id_message, type, name, "
						+ "page1_image, page1_1, page1_2, timer1, page2_image, page2_1, page2_2, timer2, "
						+ "page3_image, page3_1, page3_2, timer3, page4_image, page4_1, page4_2, timer4, "
						+ "page5_image, page5_1, page5_2, timer5 from dms_messages_available WHERE avaliable <> 0 "
						+ "AND driver = 2 AND id_message = " + id + " ORDER BY id_message ASC");
				result = conn.executeQuery();

				if (result.hasNext()) {
					for (RowResult rs : result) {
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
				conn.prepare("SELECT id_message, type, name, "
						+ "page1_image, page1_image2, page1_1, page1_2, page1_3, timer1, "
						+ "page2_image, page2_image2, page2_1, page2_2, page2_3, timer2, "
						+ "page3_image, page3_image2, page3_1, page3_2, page3_3, timer3, "
						+ "page4_image, page4_image2, page4_1, page4_2, page4_3, timer4, "
						+ "page5_image, page5_image2, page5_1, page5_2, page5_3, timer5 from dms_messages_available WHERE avaliable <> 0 "
						+ "AND driver = 3 AND id_message = " + id + " ORDER BY id_message ASC");
				result = conn.executeQuery();

				if (result.hasNext()) {
					for (RowResult rs : result) {
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

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
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
