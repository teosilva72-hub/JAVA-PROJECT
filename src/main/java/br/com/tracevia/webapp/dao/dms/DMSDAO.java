package br.com.tracevia.webapp.dao.dms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.tracevia.webapp.model.dms.DMS;
import br.com.tracevia.webapp.model.dms.Messages;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.util.ConnectionFactory;
import br.com.tracevia.webapp.util.SessionUtil;

public class DMSDAO {

	private Connection conn;
	protected ConnectionFactory connection = new ConnectionFactory();
	private PreparedStatement ps;
	private ResultSet rs;

	/* Quantidade de dmss reigstrados na base de dados */

	public Integer amountDMS() throws Exception {

		int count = 0;

		String sql = "SELECT COUNT(*) AS dmsCount FROM dms_equipment";

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs != null) {

				while (rs.next()) {

					count = rs.getInt(1);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return count;
	}

	/* IDS dos dmss reigstrados na base de dados */

	public ArrayList<DMS> idsDMS() throws Exception {

		ArrayList<DMS> lista = new ArrayList<DMS>();

		String sql = "SELECT dms.equip_id, equip_ip, name, km, linear_width, linear_posX, linear_posY, map_width, map_posX, map_posY, id_message, id_modify, driver, active FROM dms_equipment dms INNER JOIN dms_messages_active act WHERE act.equip_id = dms.equip_id AND visible = 1 ORDER BY dms.equip_id ASC";

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs != null) {

				while (rs.next()) {

					DMS dms = new DMS();
					MessagesDAO msg = new MessagesDAO();

					boolean active = rs.getBoolean("active");
					Messages message = msg.mensagensDisponivelById(rs.getInt("driver"), rs.getInt("id_message"));

					dms.setTable_id("dms");
					dms.setEquip_id(rs.getInt("equip_id"));
					dms.setDms_ip(rs.getString("equip_ip"));
					dms.setNome(rs.getString("name"));
					dms.setKm(rs.getString("km"));
					dms.setLinearWidth(rs.getInt("linear_width"));
					dms.setLinearPosX(rs.getInt("linear_posX"));
					dms.setLinearPosY(rs.getInt("linear_posY"));
					dms.setMapWidth(rs.getInt("map_width"));
					dms.setMapPosX(rs.getInt("map_posX"));
					dms.setMapPosY(rs.getInt("map_posY"));
					dms.setMessage(message);
					dms.setMsg_status(active);
					if (active)
						dms.setMessageChange(message);
					else
						dms.setMessageChange(msg.mensagensDisponivelById(rs.getInt("driver"), rs.getInt("id_modify")));

					lista.add(dms);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return lista;
	}

	public boolean changeActivateMessage(int idDMS, int idMSG) throws Exception {
		boolean success = false;

		String sql1 = "SELECT id_message FROM dms_messages_active WHERE equip_id = ?;";
		String sql2 = "UPDATE dms_messages_active SET id_modify = ?, active = ? WHERE (equip_id = ?);";

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(sql1);

			ps.setInt(1, idDMS);

			rs = ps.executeQuery();

			if (rs.isBeforeFirst()) {
				rs.next();

				ps = conn.prepareStatement(sql2);

				if (rs.getInt("id_message") == idMSG) {
					ps.setInt(1, 0);
					ps.setInt(2, 1);
				} else {
					ps.setInt(1, idMSG);
					ps.setInt(2, 0);
				}

				ps.setInt(3, idDMS);

				ps.executeUpdate();

				save_history(idDMS, idMSG);

				success = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			success = false;
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return success;
	}

	private void save_history(int idDMS, int idMSG) {
		String sql_update = "UPDATE dms_history SET until_date = now(), changed_by = ? WHERE equip_id = ? AND changed_by is null order by equip_id desc limit 1;";
		String sql_select = "SELECT CONCAT(page1_1, ' ', page1_2, ' ', page1_3) as page_one, CONCAT(page2_1, ' ', page2_2, ' ', page2_3) as page_two, CONCAT(page3_1, ' ', page3_2, ' ', page3_3) as page_three, CONCAT(page4_1, ' ', page4_2, ' ', page4_3) as page_four, CONCAT(page5_1, ' ', page5_2, ' ', page5_3) as page_five FROM dms_messages_available where id_message = ?;";
		String sql_insert = "INSERT INTO dms_history (equip_id, id_message, activation_username, define_date, page_one, page_two, page_three, page_four, page_five) VALUES (?, ?, ?, now(), ?, ?, ?, ?, ?);";

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(sql_update);

			ps.setString(1, (String) SessionUtil.getParam("user"));
			ps.setInt(2, idDMS);

			ps.executeUpdate();

			ps = conn.prepareStatement(sql_select);

			ps.setInt(1, idMSG);

			rs = ps.executeQuery();

			if (rs.isBeforeFirst()) {
				rs.next();

				String page1 = rs.getString("page_one");
				String page2 = rs.getString("page_two");
				String page3 = rs.getString("page_three");
				String page4 = rs.getString("page_four");
				String page5 = rs.getString("page_five");

				ps = conn.prepareStatement(sql_insert);

				ps.setInt(1, idDMS);
				ps.setInt(2, idMSG);
				ps.setString(3, (String) SessionUtil.getParam("user"));
				ps.setString(4, page1);
				ps.setString(5, page2);
				ps.setString(6, page3);
				ps.setString(7, page4);
				ps.setString(8, page5);
				
				ps.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
	}

	public boolean reloadActivateMessage(int idDMS) throws Exception {
		boolean success = false;

		String sql1 = "SELECT id_message FROM dms_messages_active WHERE equip_id = ?;";
		String sql2 = "UPDATE dms_messages_active SET id_modify = ?, active = ? WHERE (equip_id = ?);";

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(sql1);

			ps.setInt(1, idDMS);

			rs = ps.executeQuery();

			if (rs.isBeforeFirst()) {
				rs.next();

				int id = rs.getInt("id_message");

				ps = conn.prepareStatement(sql2);
				
				ps.setInt(1, id);
				ps.setInt(2, 0);

				ps.setInt(3, idDMS);

				ps.executeUpdate();

				save_history(idDMS, id);

				success = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			success = false;
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return success;
	}
	
	public boolean reloadActivateMessageWith(int id) throws Exception {
		boolean success = false;
		
		String sql = "UPDATE dms_messages_active SET id_modify = ?, active = ? WHERE (id_message = ?);";
		String sql_select = "SELECT equip_id FROM dms_messages_active WHERE id_message = ?;";
		
		try {
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, id);
			ps.setInt(2, 0);
			ps.setInt(3, id);
			
			rs = ps.executeQuery();

			ps = conn.prepareStatement(sql_select); 

			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.isBeforeFirst())
				while (rs.next())
					save_history(rs.getInt(1), id);
			
		} catch (SQLException e) {
			e.printStackTrace();
			success = false;
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		
		return success;
	}
}
