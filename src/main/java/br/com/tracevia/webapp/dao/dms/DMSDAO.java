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

public class DMSDAO {

	private Connection conn;
	protected ConnectionFactory connection = new ConnectionFactory();
	private PreparedStatement ps;
	private ResultSet rs;

	/* Quantidade de PMVs reigstrados na base de dados */

	public Integer amountDMS() throws Exception {

		int count = 0;

		String sql = "SELECT COUNT(*) AS dmsCount FROM tracevia_app.pmv_equipment";

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

	/* IDS dos PMVs reigstrados na base de dados */

	public ArrayList<DMS> idsDMS() throws Exception {

		ArrayList<DMS> lista = new ArrayList<DMS>();

		String sql = "SELECT equip_id, ip_equip, name, km, linear_width, linear_posX, linear_posY, map_width, map_posX, map_posY, id_message, id_modify, driver, active FROM pmv_equipment pmv INNER JOIN pmv_messages_active act WHERE act.id_equip = pmv.equip_id ORDER BY pmv.equip_id ASC";

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
					dms.setDms_ip(rs.getString("ip_equip"));
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

}
