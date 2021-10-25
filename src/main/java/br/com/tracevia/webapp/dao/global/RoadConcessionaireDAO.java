package br.com.tracevia.webapp.dao.global;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.cfg.servers.Servers;
import br.com.tracevia.webapp.controller.global.EstradaObjectController;
import br.com.tracevia.webapp.controller.global.EstradaObjectController.Plaque;
import br.com.tracevia.webapp.util.ConnectionFactory;
import br.com.tracevia.webapp.util.LogUtils;

/**
 * Classe para obter dados para classe RoadConcessionaire via base de dados
 * @author Wellington - 08/09/2020
 * @version 1.0
 * @since 1.0
 *
 */

public class RoadConcessionaireDAO {

	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	
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
	private static String roadsExceptionLog = classErrorPath.concat("roads_exception_");
	private static String modulesExceptionLog = classErrorPath.concat("modules_exception_");
	private static String plaquesExceptionLog = classErrorPath.concat("plaques_exception_");
		
	// --------------------------------------------------------------------------------------------	
	
	// CONSTRUTOR
	
	public RoadConcessionaireDAO() {
		
		LogUtils.createLogsFolder(classErrorPath);
		
	}
	
	// --------------------------------------------------------------------------------------------			

	/**
	 * Método para identificar a concessionária através do endereço de ip
	 * @author Wellington 10/03/2020
	 * @version 1.0
	 * @since 1.0
	 * @param serverAddress - endereço de ip do servidor a ser verificado
	 * @return o nome da concessionária
	 */
	public String IdentifyRoadConcessionarie(String serverAddress) {

		String concessionarieName = "";
		
		if(serverAddress.equals(Servers.ServerViaSul.getServer()))
		  //  if(serverAddress.equals("192.168.3.195"))
		    conn = ConnectionFactory.connectToCCR();
		
		else if(serverAddress.equals(Servers.ServerViaPaulista.getServer()))
			conn = ConnectionFactory.connectToViaPaulista();
			
	    else conn = ConnectionFactory.connectToTraceviaApp();
		    
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
						
		} catch (SQLException sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));				
										 						
			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(serverExceptionLog), classLocation, sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
					
		} finally {
			
			ConnectionFactory.closeConnection(conn, ps);
		}

		return concessionarieName;

	}
	
	// --------------------------------------------------------------------------------------------		
	
	/**
	 * Método para obter uma lista com as cidades disponíveis
	 * @author Wellington 10/03/2020
	 * @version 1.0
	 * @since 1.0
	 * @return lista contendo o id e o nome das cidades disponíveis
	 */

	public ArrayList<SelectItem> cityDefinitions() {

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

		} catch (SQLException sqle) {

			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));				
										 						
			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(citiesExceptionLog), classLocation, sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
						

		} finally {

			ConnectionFactory.closeConnection(conn, ps, rs);

		}

		return city;
	}
	
	// --------------------------------------------------------------------------------------------

	/**
	 * Método para obter uma lista com as estradas disponíveis
	 * @author Wellington 10/03/2020
	 * @version 1.0
	 * @since 1.0
	 * @return lista contendo o id e o nome das estradas disponíveis
	 */
	public ArrayList<SelectItem> roadDefinitions() {

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

		} catch (SQLException sqle) {

			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));				
										 						
			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(roadsExceptionLog), classLocation, sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
			

		} finally {

			ConnectionFactory.closeConnection(conn, ps, rs);

		}

		return roads;
	}
	
	// --------------------------------------------------------------------------------------------

	/**
	 * Método para obter uma lista com os módulos disponíveis
	 * @author Wellington 10/03/2020
	 * @version 1.0
	 * @since 1.0
	 * @return lista contendo o id e o nome dos módulos disponíveis
	 */
	public List<SelectItem> moduleDefinitions() {

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

		} catch (SQLException sqle) {

			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));				
										 						
			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(modulesExceptionLog), classLocation, sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
			

		} finally {

			ConnectionFactory.closeConnection(conn, ps, rs);

		}

		return modules;

	}
	
	// --------------------------------------------------------------------------------------------

	/**
	 * Método para obter uma lista com as placas disponíveis
	 * @author Guilherme 10/07/2021
	 * @version 1.0
	 * @since 1.0
	 * @return lista atributos das placas disponíveis
	 */
	public List<Plaque> getPlaque() {

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

		} catch (SQLException sqle) {

			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));				
										 						
			LogUtils.logErrorSQL(LogUtils.fileDateTimeFormatter(plaquesExceptionLog), classLocation, sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
			

		} finally {

			ConnectionFactory.closeConnection(conn, ps, rs);

		}

		return all_plaque;

	}
	
	// --------------------------------------------------------------------------------------------

}
