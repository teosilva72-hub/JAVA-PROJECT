package br.com.tracevia.webapp.dao.global;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import br.com.tracevia.webapp.model.global.SQL_Tracevia;
import br.com.tracevia.webapp.model.global.ColumnsSql.RowResult;
import br.com.tracevia.webapp.model.global.ResultSql.MapResult;
import br.com.tracevia.webapp.controller.global.EstradaObjectController;
import br.com.tracevia.webapp.controller.global.EstradaObjectController.Plaque;
import br.com.tracevia.webapp.util.LogUtils;

/**
 * Classe para obter dados para classe RoadConcessionaire via base de dados
 * 
 * @author Wellington - 08/09/2020
 * @version 1.0
 * @since 1.0
 *
 */

public class RoadConcessionaireDAO {

	SQL_Tracevia conn = new SQL_Tracevia();

	// --------------------------------------------------------------------------------------------

	// CLASS PATH

	private static String classLocation = RoadConcessionaireDAO.class.getCanonicalName();

	// --------------------------------------------------------------------------------------------

	// CLASS LOG FOLDER

	private static String classErrorPath = LogUtils.ERROR.concat("road_concessionaire\\");

	// --------------------------------------------------------------------------------------------

	// EXCEPTION FILENAMES

	private static String serverExceptionLog = classErrorPath.concat("server_exception_");
	private static String citiesExceptionLog = classErrorPath.concat("cities_exception_");
	private static String citiesDirectionExceptionLog = classErrorPath.concat("cities_direction_exception_");
	private static String roadsExceptionLog = classErrorPath.concat("roads_exception_");
	private static String modulesExceptionLog = classErrorPath.concat("modules_exception_");
	private static String plaquesExceptionLog = classErrorPath.concat("plaques_exception_");

	// --------------------------------------------------------------------------------------------

	// CONSTRUTOR

	public RoadConcessionaireDAO() {

		LogUtils.createLogFolder(classErrorPath);

	}

	// --------------------------------------------------------------------------------------------

	/**
	 * M�todo para identificar a concession�ria atrav�s do endere�o de ip
	 * 
	 * @author Wellington 10/03/2020
	 * @version 1.0
	 * @since 1.0
	 * @param serverAddress - endere�o de ip do servidor a ser verificado
	 * @return o nome da concession�ria
	 * @throws Exception
	 */
	public String IdentifyRoadConcessionarie(String serverAddress) throws Exception {

		String concessionarieName = "";

		try {
			conn.start(1);

			String query = "SELECT road_concessionaire FROM server_config WHERE server_address = ? ";

			conn.prepare(query);
			conn.setString(1, serverAddress);
			MapResult result = conn.executeQuery();
			
			//System.out.println(query+" "+serverAddress);

			if (result.hasNext()) {
				for (RowResult rs : result) {

					concessionarieName = rs.getString("road_concessionaire");

				}
			}

		} catch (Exception sqle) {

			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));

			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(serverExceptionLog), classLocation, sqle.hashCode(),
					sqle.toString(), sqle.getMessage(), errors.toString());

		} finally {

			conn.close();
		}

		return concessionarieName;

	}

	// --------------------------------------------------------------------------------------------

	/**
	 * M�todo para obter uma lista com as cidades dispon�veis
	 * 
	 * @author Wellington 10/03/2020
	 * @version 1.0
	 * @since 1.0
	 * @return lista contendo o id e o nome das cidades dispon�veis
	 * @throws Exception
	 */

	public ArrayList<SelectItem> cityDefinitions() throws Exception {

		ArrayList<SelectItem> city = new ArrayList<SelectItem>();

		String query = "SELECT city_id, city_name FROM concessionaire_cities WHERE city_id <> 0";

		try {

			conn.start(1);

			conn.prepare(query);
			MapResult result = conn.executeQuery();

			if (result.hasNext()) {
				for (RowResult rs : result) {

					SelectItem item = new SelectItem();
					item.setValue(rs.getInt(1));
					item.setLabel(rs.getString(2));

					city.add(item);
				}
			}

		} catch (Exception sqle) {

			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));

			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(citiesExceptionLog), classLocation, sqle.hashCode(),
					sqle.toString(), sqle.getMessage(), errors.toString());

		} finally {

			conn.close();

		}

		return city;
	}

	// --------------------------------------------------------------------------------------------

	/**
	 * M�todo para obter uma lista com as estradas dispon�veis
	 * 
	 * @author Wellington 10/03/2020
	 * @version 1.0
	 * @since 1.0
	 * @return lista contendo o id e o nome das estradas dispon�veis
	 * @throws Exception
	 */
	public ArrayList<SelectItem> roadDefinitions() throws Exception {

		ArrayList<SelectItem> roads = new ArrayList<SelectItem>();

		String query = "SELECT road_id, road_name FROM concessionaire_roads WHERE road_id <> 0";

		try {
			conn.start(1);

			conn.prepare(query);
			MapResult result = conn.executeQuery();

			if (result.hasNext()) {
				for (RowResult rs : result) {

					SelectItem item = new SelectItem();
					item.setValue(rs.getInt(1));
					item.setLabel(rs.getString(2));

					roads.add(item);
				}
			}

		} catch (Exception sqle) {

			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));

			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(roadsExceptionLog), classLocation, sqle.hashCode(),
					sqle.toString(), sqle.getMessage(), errors.toString());

		} finally {

			try {
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return roads;
	}

	// --------------------------------------------------------------------------------------------

	/**
	 * M�todo para obter uma lista com os m�dulos dispon�veis
	 * 
	 * @author Wellington 10/03/2020
	 * @version 1.0
	 * @since 1.0
	 * @return lista contendo o id e o nome dos m�dulos dispon�veis
	 * @throws Exception
	 */
	public List<SelectItem> moduleDefinitions() throws Exception {

		ArrayList<SelectItem> modules = new ArrayList<SelectItem>();

		String query = null;

		try {

			query = "SELECT module_id, module FROM tracevia_core.modules WHERE enabled = 1 "
					+ "AND module <> 'OCC' AND MODULE <> 'VIDEOWALL' ";

			conn.start(1);

			conn.prepare(query);
			MapResult result = conn.executeQuery();

			if (result.hasNext()) {
				for (RowResult rs : result) {

					SelectItem item = new SelectItem();
					item.setValue(rs.getInt(1));
					item.setLabel(rs.getString(2));

					modules.add(item);
				}
			}

		} catch (Exception sqle) {

			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));

			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(modulesExceptionLog), classLocation, sqle.hashCode(),
					sqle.toString(), sqle.getMessage(), errors.toString());

		} finally {

			conn.close();

		}

		return modules;

	}

	// --------------------------------------------------------------------------------------------
	
	/**
	 * M�todo para obter uma lista com as cidades dispon�veis
	 * 
	 * @author Wellington 10/03/2020
	 * @version 1.0
	 * @since 1.0
	 * @return lista contendo o id e o nome das cidades dispon�veis
	 * @throws Exception
	 */

	public ArrayList<SelectItem> cityDirectionDefinitions() throws Exception {

		ArrayList<SelectItem> city = new ArrayList<SelectItem>();

		String query = "SELECT city_id, city_name FROM city_direction";

		try {

			conn.start(1);

			conn.prepare(query);
			MapResult result = conn.executeQuery();

			if (result.hasNext()) {
				for (RowResult rs : result) {

					SelectItem item = new SelectItem();
					item.setValue(rs.getInt(1));
					item.setLabel(rs.getString(2));

					city.add(item);
				}
			}

		} catch (Exception sqle) {

			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));

			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(citiesDirectionExceptionLog), classLocation, sqle.hashCode(),
					sqle.toString(), sqle.getMessage(), errors.toString());

		} finally {

			conn.close();

		}

		return city;
	}

	// --------------------------------------------------------------------------------------------


	/**
	 * M�todo para obter uma lista com as placas dispon�veis
	 * 
	 * @author Guilherme 10/07/2021
	 * @version 1.0
	 * @since 1.0
	 * @return lista atributos das placas dispon�veis
	 * @throws Exception
	 */
	public List<Plaque> getPlaque() throws Exception {

		ArrayList<Plaque> all_plaque = new ArrayList<>();

		String query = null;

		try {

			query = "SELECT km, longitude, latitude, map_posY, linear_pos_x, linear_pos_y FROM plaque_km";

			conn.start(1);

			conn.prepare(query);
			MapResult result = conn.executeQuery();

			if (result.hasNext()) {
				for (RowResult rs : result) {

					Plaque plaque = new EstradaObjectController().new Plaque();
					plaque.setKm(rs.getInt(1));
					plaque.setLongitude(rs.getDouble(2));
					plaque.setLatitude(rs.getDouble(3));
					plaque.setMapY(rs.getInt(4));
					plaque.setLinear_posX(rs.getInt(5));
					plaque.setLinear_posY(rs.getInt(6));

					all_plaque.add(plaque);
				}
			}

		} catch (Exception sqle) {

			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));

			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(plaquesExceptionLog), classLocation, sqle.hashCode(),
					sqle.toString(), sqle.getMessage(), errors.toString());

		} finally {

			conn.close();

		}

		return all_plaque;

	}

	public List<int[]> getRoadLine() {
		ArrayList<int[]> line = new ArrayList<>();

		try {

			String query = "SELECT id, position_x, position_y FROM map_mapping";

			conn.start(1);

			conn.prepare(query);
			MapResult result = conn.executeQuery();

			if (result.hasNext())
				for (RowResult rs : result) {

					int[] point = new int[3];
					point[0] = rs.getInt(1);
					point[1] = rs.getInt(2);
					point[2] = rs.getInt(3);

					line.add(point);
				}

		} catch (Exception sqle) {

			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));

			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(plaquesExceptionLog), classLocation, sqle.hashCode(),
					sqle.toString(), sqle.getMessage(), errors.toString());

		} finally {

			conn.close();

		}

		return line;
	}

	// --------------------------------------------------------------------------------------------

	public List<String[]> getCarsList() {
		ArrayList<String[]> cars = new ArrayList<>();

		try {

			String query = "SELECT v.id, file, GROUP_CONCAT(CASE WHEN v.id = type THEN c.id ELSE NULL END) FROM gps_vehicle v INNER JOIN gps_custom c GROUP BY v.id";

			conn.start(1);

			conn.prepare(query);
			MapResult result = conn.executeQuery();

			if (result.hasNext())
				for (RowResult rs : result) {

					String[] car = new String[3];
					car[0] = rs.getString(1);
					car[1] = rs.getString(2);
					car[2] = rs.getString(3);

					cars.add(car);
				}

		} catch (Exception sqle) {

			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));

			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(plaquesExceptionLog), classLocation, sqle.hashCode(),
					sqle.toString(), sqle.getMessage(), errors.toString());

		} finally {

			conn.close();

		}

		return cars;
	}

	public void saveCarIMG(String id, String type) {
		try {

			String query = "INSERT INTO gps_custom (id, type) VALUES (?, ?)";

			conn.start(1);

			conn.prepare(query);

			conn.setString(1, id);
			conn.setString(2, type);

			conn.executeUpdate();

		} catch (Exception sqle) {
			try {
				String query = "UPDATE gps_custom SET type = ? WHERE (id = ?)";

				conn.start(1);

				conn.prepare(query);

				conn.setString(1, type);
				conn.setString(2, id);

				conn.executeUpdate();
			} catch (Exception sqle2) {

				StringWriter errors = new StringWriter();
				sqle.printStackTrace(new PrintWriter(errors));
				sqle2.printStackTrace(new PrintWriter(errors));

				LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(plaquesExceptionLog), classLocation,
						sqle.hashCode(), sqle.toString(), sqle.getMessage(), errors.toString());
				LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(plaquesExceptionLog), classLocation,
						sqle2.hashCode(), sqle2.toString(), sqle2.getMessage(), errors.toString());
			}

		} finally {

			conn.close();

		}
	}
}
