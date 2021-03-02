package br.com.tracevia.webapp.dao.global;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import br.com.tracevia.webapp.cfg.servers.Servers;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class RoadConcessionaireDAO {

	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;

	public String IdentifyRoadConcessionarie(String serverAddress) throws Exception {

		String concessionarieName = "";

		//if (serverAddress.equals("192.168.0.32") || serverAddress.equals("192.168.0.40"))
		 //if(serverAddress.equals("192.168.3.142"))
		 if(serverAddress.equals(Servers.ServersViaSul.getServer()))		
			conn = ConnectionFactory.connectToCCR();

		else if (serverAddress.equals(Servers.ServerViaPaulista.getServer()))
			conn = ConnectionFactory.connectToViaPaulista();

		else
			conn = ConnectionFactory.connectToTraceviaApp();

		try {

			String query = "SELECT road_concessionaire FROM server_config WHERE server_address = ? ";

			ps = conn.prepareStatement(query);
			ps.setString(1, serverAddress);
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					concessionarieName = rs.getString("road_concessionaire");

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}

		return concessionarieName;

	}

	public ArrayList<SelectItem> cityDefinitions() throws Exception {

		ArrayList<SelectItem> city = new ArrayList<SelectItem>();

		String query = "SELECT city_id, city_name FROM concessionaire_cities";

		try {

			conn = ConnectionFactory.connectToTraceviaApp();

			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					SelectItem item = new SelectItem();
					item.setValue(rs.getInt(1));
					item.setLabel(rs.getString(2));

					city.add(item);
				}
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			ConnectionFactory.closeConnection(conn, ps, rs);

		}

		return city;
	}

	public ArrayList<SelectItem> roadDefinitions() throws Exception {

		ArrayList<SelectItem> roads = new ArrayList<SelectItem>();

		String query = "SELECT road_id, road_name FROM concessionaire_roads";

		try {
			conn = ConnectionFactory.connectToTraceviaApp();

			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					SelectItem item = new SelectItem();
					item.setValue(rs.getInt(1));
					item.setLabel(rs.getString(2));

					roads.add(item);
				}
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			ConnectionFactory.closeConnection(conn, ps, rs);

		}

		return roads;
	}

	public List<SelectItem> moduleDefinitions() throws Exception {

		ArrayList<SelectItem> modules = new ArrayList<SelectItem>();

		String query = null;

		try {

			query = "SELECT module_id, module FROM tracevia_core.modules WHERE enabled = 1 "
					+ "AND module <> 'OCC' AND MODULE <> 'VIDEOWALL' ";

			conn = ConnectionFactory.connectToTraceviaCore();

			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					SelectItem item = new SelectItem();
					item.setValue(rs.getInt(1));
					item.setLabel(rs.getString(2));

					modules.add(item);
				}
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			ConnectionFactory.closeConnection(conn, ps, rs);

		}

		return modules;

	}

}
