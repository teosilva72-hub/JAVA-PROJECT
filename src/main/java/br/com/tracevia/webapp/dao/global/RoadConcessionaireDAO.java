package br.com.tracevia.webapp.dao.global;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.controller.global.EstradaObjectController;
import br.com.tracevia.webapp.controller.global.EstradaObjectController.Plaque;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class RoadConcessionaireDAO {

	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;

	public String IdentifyRoadConcessionarie(String serverAddress) throws Exception {

		String concessionarieName = "";
		
		  //if(serverAddress.equals(Servers.ServerViaSul.getServer()))
		    //if(serverAddress.equals("192.168.3.195"))
		      // conn = ConnectionFactory.connectToCCR();
		
		 //   else if(serverAddress.equals(Servers.ServerViaPaulista.getServer()))
		  //     conn = ConnectionFactory.connectToViaPaulista();
				
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

		String query = "SELECT city_id, city_name FROM concessionaire_cities WHERE city_id <> 0";

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

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

		String query = "SELECT road_id, road_name FROM concessionaire_roads WHERE road_id <> 0";

		try {
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

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

	public List<Plaque> getPlaque() throws Exception {

		ArrayList<Plaque> all_plaque = new ArrayList<>();

		String query = null;

		try {

			query = "SELECT km, map_pos_x, map_pos_y, linear_pos_x, linear_pos_y FROM plaque_km";

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			if (rs.isBeforeFirst()) {
				while (rs.next()) {

					Plaque plaque = new EstradaObjectController().new Plaque();
					plaque.setKm(rs.getInt(1));
					plaque.setMap_posX(rs.getInt(2));
					plaque.setMap_posY(rs.getInt(3));
					plaque.setLinear_posX(rs.getInt(4));
					plaque.setLinear_posY(rs.getInt(5));

					all_plaque.add(plaque);
				}
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			ConnectionFactory.closeConnection(conn, ps, rs);

		}

		return all_plaque;

	}

}
