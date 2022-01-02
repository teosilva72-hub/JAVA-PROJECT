package br.com.tracevia.webapp.dao.global;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.tracevia.webapp.cfg.ModulesEnum;
import br.com.tracevia.webapp.dao.dms.MessagesDAO;
import br.com.tracevia.webapp.log.SystemLog;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.dms.DMS;
import br.com.tracevia.webapp.model.dms.Messages;
import br.com.tracevia.webapp.model.global.EquipmentDataSource;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.meteo.Meteo;
import br.com.tracevia.webapp.model.sat.SAT;
import br.com.tracevia.webapp.model.sos.SOS;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class EquipmentsDAO {

	private Connection conn;
	protected ConnectionFactory connection = new ConnectionFactory();
	private PreparedStatement ps;
	private ResultSet rs;	
	
	// --------------------------------------------------------------------------------------------------------------
	
	// LOGS FOLDER
	
	private final String errorFolder = SystemLog.ERROR.concat("equipments-dao\\");
	
	// --------------------------------------------------------------------------------------------------------------
	
	public EquipmentsDAO() {
		
		SystemLog.createLogFolder(errorFolder);
		
	}

	// --------------------------------------------------------------------------------------------------------------

	/**
	 * Método para atualizar tamanho do equipamento
	 * @author 
	 * @version 1.0
	 * @since Release 1.0
	 * @param mod - Módulo
	 * @param view - Mapa gis ou linear
	 * @param width - Comprimento a ser alterado	
	 * 
	 */	

	public void setWidthMap(String modulo, String view, int width) {

		String sql = "UPDATE " + modulo + "_equipment SET " + view + "_width = ? WHERE equip_id > 0;";

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(sql);

			ps.setInt(1, width);

			ps.executeUpdate();

		}catch (SQLException sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));
			
			SystemLog.logErrorSQL(errorFolder.concat("error_set_width"), EquipmentsDAO.class.getCanonicalName(), sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
			
		} finally {
			
			ConnectionFactory.closeConnection(conn, ps);
		}
	}

	
	// --------------------------------------------------------------------------------------------------------------

	/**
	 * Método para listar equipamentos por módulo
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param mod - M�dulo
	 * @return ArrayList - Lista de equipamentos	
	 */

	public ArrayList<Equipments> buildEquipmentsInterface(String modulo, int permission) {

		ArrayList<Equipments> lista = new ArrayList<Equipments>();

		String query = "";

		String sql = "SELECT equip_id, name, c.city_name, r.road_name, km, linear_width, " +
				"linear_posX, linear_posY, map_width, map_posX, map_posY, longitude, latitude, direction FROM "+modulo+"_equipment eq " +
				"INNER JOIN concessionaire_cities c ON c.city_id = eq.city " +
				"INNER JOIN concessionaire_roads r ON r.road_id = eq.road " +
				"WHERE visible = 1 ";

		String sqlVW = "SELECT equip_id, name, c.city_name, r.road_name, km, vw_linear_width, " +
				"vw_linear_posX, vw_linear_posY, vw_map_width, vw_map_posX, vw_map_posY, longitude, latitude, direction FROM "+modulo+"_equipment eq " +
				"INNER JOIN concessionaire_cities c ON c.city_id = eq.city " +
				"INNER JOIN concessionaire_roads r ON r.road_id = eq.road " +
				"WHERE visible = 1 ";

			if(permission != 9)
				query = sql;

			else query = sqlVW;

			try {
				
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(query);					
			rs = ps.executeQuery();
			
			//System.out.println(query);

			if (rs != null) {

				while (rs.next()) {

					Equipments equip = new Equipments();

					equip.setEquip_id(rs.getInt(1));
					equip.setTable_id(modulo);
					equip.setNome(rs.getString(2));
					equip.setEquip_type(getModule(modulo));
					equip.setCidade(rs.getString(3));
					equip.setEstrada(rs.getString(4));
					equip.setKm(rs.getString(5));
					equip.setLinearWidth(rs.getInt(6));						
					equip.setLinearPosX(rs.getInt(7));
					equip.setLinearPosY(rs.getInt(8));
					equip.setMapWidth(rs.getInt(9));						
					equip.setMapPosX(rs.getInt(10));					
					equip.setMapPosY(rs.getInt(11));
					equip.setLongitude(rs.getDouble(12));
					equip.setLatitude(rs.getDouble(13));
					equip.setDirection(rs.getString(14));

					lista.add(equip);
				}				
			}

		}catch (SQLException sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));
			
			SystemLog.logErrorSQL(errorFolder.concat("error_build_generic"), EquipmentsDAO.class.getCanonicalName(), sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
			
		} finally {
			
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return lista;
	}

	// --------------------------------------------------------------------------------------------------------------
	/**
	 * M�todo para listar equipamentos por módulo
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param permission nível de permissão
	 * @return ArrayList - Lista de equipamentos do tipo METEO
	 */

	public ArrayList<Meteo> buildMeteoEquipmentsInterface(int permission) {

		ArrayList<Meteo> lista = new ArrayList<>();

		String query = "";

		String sql = "SELECT equip_id, name, equip_type, c.city_name, r.road_name, km, linear_width, " +
				"linear_posX, linear_posY, map_width, map_posX, map_posY, longitude, latitude, direction FROM meteo_equipment eq " +
				"INNER JOIN concessionaire_cities c ON c.city_id = eq.city " +
				"INNER JOIN concessionaire_roads r ON r.road_id = eq.road " +
				"WHERE visible = 1 ";

		String sqlVW = "SELECT equip_id, name, equip_type, c.city_name, r.road_name, km, vw_linear_width, " +
				"vw_linear_posX, vw_linear_posY, vw_map_width, vw_map_posX, vw_map_posY, longitude, latitude, direction FROM meteo_equipment eq " +
				"INNER JOIN concessionaire_cities c ON c.city_id = eq.city " +
				"INNER JOIN concessionaire_roads r ON r.road_id = eq.road " +
				"WHERE visible = 1 ";


			if(permission != 9)
				query = sql;

			else query = sqlVW;

			try {
				
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(query);					
			rs = ps.executeQuery();
		
			if (rs != null) {

				while (rs.next()) {

					Meteo meteo = new Meteo();

					meteo.setEquip_id(rs.getInt(1));
					meteo.setTable_id("meteo");
					meteo.setNome(rs.getString(2));
					meteo.setEquip_type(rs.getString(3));		
					meteo.setCidade(rs.getString(4));
					meteo.setEstrada(rs.getString(5));
					meteo.setKm(rs.getString(6));
					meteo.setLinearWidth(rs.getInt(7));						
					meteo.setLinearPosX(rs.getInt(8));
					meteo.setLinearPosY(rs.getInt(9));
					meteo.setMapWidth(rs.getInt(10));						
					meteo.setMapPosX(rs.getInt(11));					
					meteo.setMapPosY(rs.getInt(12));			
					meteo.setLongitude(rs.getDouble(13));
					meteo.setLatitude(rs.getDouble(14));
					meteo.setDirection(rs.getString(15));

					lista.add(meteo);
				}				
			}

		}catch (SQLException sqle) {
				
				StringWriter errors = new StringWriter();
				sqle.printStackTrace(new PrintWriter(errors));
				
				SystemLog.logErrorSQL(errorFolder.concat("error_build_meteo"), EquipmentsDAO.class.getCanonicalName(), sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
				
		} finally {
				
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
			
		return lista;
	}

	// --------------------------------------------------------------------------------------------------------------

	/**
	 * M�todo para listar equipamentos por m�dulo
	 * @author Guilherme
	 * @version 1.0
	 * @since Release 1.0
	 * @param mod - Módulo
	 * @return ArrayList - Lista de equipamentos
	 */

	public ArrayList<SOS> buildSosEquipmentsInterface(int permission) {

		ArrayList<SOS> lista = new ArrayList<>();

		String query = "";

		String sql = "SELECT equip_id, name, port, c.city_name, r.road_name, km, linear_width, " +
				"linear_posX, linear_posY, map_width, map_posX, map_posY, model, master_sip, longitude, latitude, direction FROM sos_equipment eq " +
				"INNER JOIN concessionaire_cities c ON c.city_id = eq.city " +
				"INNER JOIN concessionaire_roads r ON r.road_id = eq.road " +
				"WHERE visible = 1 ";

		String sqlVW = "SELECT equip_id, name, port, c.city_name, r.road_name, km, vw_linear_width, " +
				"vw_linear_posX, vw_linear_posY, vw_map_width, vw_map_posX, vw_map_posY, model, master_sip, longitude, latitude, direction FROM sos_equipment eq " +
				"INNER JOIN concessionaire_cities c ON c.city_id = eq.city " +
				"INNER JOIN concessionaire_roads r ON r.road_id = eq.road " +
				"WHERE visible = 1 ";

			if(permission != 9)
				query = sql;

			else query = sqlVW;

			try {
				
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(query);					
			rs = ps.executeQuery();

			if (rs != null) {

				while (rs.next()) {

					SOS sos = new SOS();

					sos.setEquip_id(rs.getInt(1));
					sos.setTable_id("sos");
					sos.setNome(rs.getString(2));
					sos.setEquip_type(getModule("sos"));
					sos.setPort(rs.getInt(3));
					sos.setCidade(rs.getString(4));
					sos.setEstrada(rs.getString(5));
					sos.setKm(rs.getString(6));
					sos.setLinearWidth(rs.getInt(7));						
					sos.setLinearPosX(rs.getInt(8));
					sos.setLinearPosY(rs.getInt(9));
					sos.setMapWidth(rs.getInt(10));						
					sos.setMapPosX(rs.getInt(11));					
					sos.setMapPosY(rs.getInt(12));
					sos.setModel(rs.getInt(13));
					sos.setSip(rs.getString(14));
					sos.setLongitude(rs.getDouble(15));
					sos.setLatitude(rs.getDouble(16));
					sos.setDirection(rs.getString(17));

					lista.add(sos);
				}				
			}

		}catch (SQLException sqle) {
				
				StringWriter errors = new StringWriter();
				sqle.printStackTrace(new PrintWriter(errors));
				
				SystemLog.logErrorSQL(errorFolder.concat("error_build_sos"), EquipmentsDAO.class.getCanonicalName(), sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
				
		} finally {
				
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
			
		return lista;
	}

	// --------------------------------------------------------------------------------------------------------------

	/**
	 * M�todo para listar equipamentos do tipo SAT
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0	
	 * @return ArrayList - Lista de equipamentos
	 * 
	 */
	public ArrayList<SAT> buildSatEquipmentsInterface(int permission) {

		ArrayList<SAT> lista = new ArrayList<SAT>();
		TranslationMethods translator = new TranslationMethods();

		String dir1 = " ", dir2 = " ", dir3 = " ", dir4 = " ", dir5 = " ", dir6 = " ", dir7= " ", dir8 = " ";
		String sentido1 = "", sentido2 = "";

		String query = "";

		String sql = "SELECT equip_id, name, c.city_name, r.road_name, km, number_lanes, dir_lane1, dir_lane2, dir_lane3, dir_lane4, " +
				"dir_lane5, dir_lane6, dir_lane7, dir_lane8, linear_width, linear_posX, linear_posY, map_width, map_posX, map_posY, " +
				"service_position, longitude, latitude, direction FROM sat_equipment eq " +
				"INNER JOIN concessionaire_cities c ON c.city_id = eq.city " +
				"INNER JOIN concessionaire_roads r ON r.road_id = eq.road " +
				"WHERE visible = 1 ";

		String sqlVW = "SELECT equip_id, name, c.city_name, r.road_name, km, number_lanes, dir_lane1, dir_lane2, dir_lane3, dir_lane4, " +
				"dir_lane5, dir_lane6, dir_lane7, dir_lane8, vw_linear_width, vw_linear_posX, vw_linear_posY, vw_map_width, vw_map_posX, vw_map_posY, " +
				"service_position, longitude, latitude, direction FROM sat_equipment eq " +
				"INNER JOIN concessionaire_cities c ON c.city_id = eq.city " +
				"INNER JOIN concessionaire_roads r ON r.road_id = eq.road " +
				"WHERE visible = 1 ";

			if(permission != 9)
				query = sql;

			else query = sqlVW;

			try {
				
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(query);									
			rs = ps.executeQuery();

			if (rs != null) {

				while (rs.next()) {

					SAT sat = new SAT();

					sentido1 = translator.CheckDirection(rs.getString(7));
					sentido2 = translator.Check2ndDirection(rs.getString(7));

					dir1 = translator.CheckDirection(rs.getString(7));
					dir2 = translator.CheckDirection(rs.getString(8));
					dir3 = translator.CheckDirection(rs.getString(9));
					dir4 = translator.CheckDirection(rs.getString(10));
					dir5 = translator.CheckDirection(rs.getString(11));
					dir6 = translator.CheckDirection(rs.getString(12));
					dir7 = translator.CheckDirection(rs.getString(13));
					dir8 = translator.CheckDirection(rs.getString(14));

					sat.setEquip_id(rs.getInt(1));
					sat.setTable_id("sat");
					sat.setNome(rs.getString(2));
					sat.setCidade(rs.getString(3));
					sat.setEstrada(rs.getString(4));
					sat.setKm(rs.getString(5));
					sat.setNumFaixas(rs.getInt(6));
					sat.setEquip_type(ModulesEnum.SAT.getModule());
					sat.setFaixa1(dir1);		
					sat.setFaixa2(dir2);
					sat.setFaixa3(dir3);		
					sat.setFaixa4(dir4);
					sat.setFaixa5(dir5);		
					sat.setFaixa6(dir6);
					sat.setFaixa7(dir7);		
					sat.setFaixa8(dir8);
					sat.setSentido1(sentido1);
					sat.setSentido2(sentido2);
					sat.setLinearWidth(rs.getInt(15));						
					sat.setLinearPosX(rs.getInt(16));
					sat.setLinearPosY(rs.getInt(17));
					sat.setMapWidth(rs.getInt(18));						
					sat.setMapPosX(rs.getInt(19));					
					sat.setMapPosY(rs.getInt(20));	
					sat.setPosicao_nivel_servico(rs.getString(21));
					sat.setLongitude(rs.getDouble(22));
					sat.setLatitude(rs.getDouble(23));
					sat.setDirection(rs.getString(24));					

					lista.add(sat);
				}				
			}

		}catch (SQLException sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));
			
			SystemLog.logErrorSQL(errorFolder.concat("error_build_sat"), EquipmentsDAO.class.getCanonicalName(), sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
						
		} catch (Exception ex) {
		
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			
			SystemLog.logError(errorFolder.concat("error_parsing_direction"), EquipmentsDAO.class.getCanonicalName(), ex.getMessage(), errors.toString());
			
		} finally {
			
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return lista;
	}

	// --------------------------------------------------------------------------------------------------------------

	/**
	 * M�todo para listar equipamentos do tipo DMS
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0	
	 * @return ArrayList - Lista de equipamentos
	 * 
	 */

	public ArrayList<DMS> buildDMSEquipmentsInterface(int permission) {

		ArrayList<DMS> lista = new ArrayList<DMS>();

		String query = "";

		String sql = "SELECT eq.equip_id, equip_ip, driver, name, c.city_name, r.road_name, km, "
				+ "linear_width, linear_posX, linear_posY, map_width, map_posX, map_posY, id_message, id_modify, active, longitude, latitude, direction "
				+ "FROM dms_equipment eq " 
				+ "INNER JOIN dms_messages_active act ON act.equip_id = eq.equip_id " 
				+ "INNER JOIN concessionaire_cities c ON c.city_id = eq.city " 
				+ "INNER JOIN concessionaire_roads r ON r.road_id = eq.road "								 
				+ "WHERE visible = 1 "
				+ "ORDER BY eq.equip_id ASC"; 

		String sqlVW = "SELECT eq.equip_id, equip_ip, driver, name, c.city_name, r.road_name, km, "
				+ "vw_linear_width, vw_linear_posX, vw_linear_posY, vw_map_width, vw_map_posX, vw_map_posY, id_message, id_modify, active, longitude, latitude, direction"
				+ "FROM dms_equipment eq " 
				+ "INNER JOIN dms_messages_active act ON act.equip_id = eq.equip_id " 
				+ "INNER JOIN concessionaire_cities c ON c.city_id = eq.city " 
				+ "INNER JOIN concessionaire_roads r ON r.road_id = eq.road "								 
				+ "WHERE visible = 1 "
				+ "ORDER BY eq.equip_id ASC"; 


			if(permission != 9)
				query = sql;

			else query = sqlVW;

			try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(query);				
			rs = ps.executeQuery();
			
			//System.out.println(query);

			if (rs != null) {

				while (rs.next()) {

					DMS dms = new DMS();
					MessagesDAO msg = new MessagesDAO();

					boolean active = rs.getBoolean("active");
					Messages message = msg.mensagensDisponivelById(rs.getInt("driver"), rs.getInt("id_message"));

					dms.setEquip_id(rs.getInt(1));
					dms.setTable_id("dms");
					dms.setDms_ip(rs.getString(2));
					dms.setDms_type(rs.getInt(3));
					dms.setEquip_type(ModulesEnum.DMS.getModule());
					dms.setNome(rs.getString(4));
					dms.setCidade(rs.getString(5));
					dms.setEstrada(rs.getString(6));
					dms.setKm(rs.getString(7));				
					dms.setLinearWidth(rs.getInt(8));						
					dms.setLinearPosX(rs.getInt(9));
					dms.setLinearPosY(rs.getInt(10));
					dms.setMapWidth(rs.getInt(11));						
					dms.setMapPosX(rs.getInt(12));					
					dms.setMapPosY(rs.getInt(13));	
					dms.setMessage(message);
					dms.setMsg_status(active);
					dms.setLongitude(rs.getDouble(17));
					dms.setLatitude(rs.getDouble(18));
					dms.setDirection(rs.getString(19));

					if (active)
						dms.setMessageChange(message);
					else
						dms.setMessageChange(msg.mensagensDisponivelById(rs.getInt("driver"), rs.getInt("id_modify")));

					lista.add(dms);
				}				
			}

			}catch (SQLException sqle) {
				
				StringWriter errors = new StringWriter();
				sqle.printStackTrace(new PrintWriter(errors));
				
				SystemLog.logErrorSQL(errorFolder.concat("error_build_dms"), EquipmentsDAO.class.getCanonicalName(), sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
				
			}catch (Exception ex) {
				
				StringWriter errors = new StringWriter();
				ex.printStackTrace(new PrintWriter(errors));
				
				SystemLog.logError(errorFolder.concat("error_define_message"), EquipmentsDAO.class.getCanonicalName(), ex.getMessage(), errors.toString());
				
			} finally {
				
				ConnectionFactory.closeConnection(conn, ps, rs);
			}

		return lista;
	}

	// --------------------------------------------------------------------------------------------------------------

	/**
	 * Método para listar informa��es de equipamentos para sele��o
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param modulo - M�dulo
	 * @return ArrayList - Lista de equipamentos	 
	 */

	public ArrayList<Equipments> equipmentSelectOptions(String modulo){

		ArrayList<Equipments> lista = new ArrayList<Equipments>();

		String sql = "SELECT equip_id, name FROM "+modulo+"_equipment WHERE visible = 1";

		try {

			// GET CONNECTION			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			//System.out.println(sql);

			if (rs != null) {

				while (rs.next()) {

					Equipments equip = new Equipments();

					equip.setEquip_id(rs.getInt(1));		
					equip.setTable_id(modulo);
					equip.setNome(rs.getString(2));

					lista.add(equip);
				}				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return lista;
	}

	// --------------------------------------------------------------------------------------------------------------
	
	/**
	 * Método para obter o nome de um equipamento
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param modulo - M�dulo
	 * @param equipId - Equipamento ID
	 * @return String - Nome do equipamento	
	 */

	public String equipmentName(String modulo, String equipId){

		String name = "";

		String sql = "SELECT name FROM "+modulo+"_equipment WHERE equip_id = '"+equipId+"' AND visible = 1 ";

		try {

			//GET CONNECTION			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs != null) {

				while (rs.next()) {

					name = rs.getString(1);

				}				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return name;
	}

	// --------------------------------------------------------------------------------------------------------------

	/**
	 * M�todo para obter informa��o de um equipamento espec�fico
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param equip_id - Equipamento ID
	 * @param module - M�dulo	 
	 * @return Equipments - Objeto com informa��es do tipo Equipments	 
	 */

	public List<Equipments> equipReportInfo(String equip_id, String module) {

		List<Equipments> list = new ArrayList<>();

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			String sql = "SELECT st.name, c.city_name, r.road_name, st.km "
					+ "FROM "+module+"_equipment st "
					+ "INNER JOIN concessionaire_cities c ON c.city_id = st.city "
					+ "INNER JOIN concessionaire_roads r ON r.road_id = st.road "
					+ " WHERE st.equip_id = '"+ equip_id + "' AND st.visible = 1";

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
						
			if (rs != null) {
				while (rs.next()) {
					
					Equipments eq = new Equipments();

					eq.setNome(rs.getString(1));
					eq.setCidade(rs.getString(2));
					eq.setEstrada(rs.getString(3));
					eq.setKm(rs.getString(4));
					
					list.add(eq);
				}
			}

		}catch (SQLException sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));
			
			SystemLog.logErrorSQL(errorFolder.concat("error_equip_report_info"), EquipmentsDAO.class.getCanonicalName(), sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
			
		} finally {
			
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return list;
	}	

	// --------------------------------------------------------------------------------------------------------------
	
	/**
	 * Método para obter informação de um equipamento específico
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param equip_id - Equipamento ID
	 * @param module - Módulo	 
	 * @return Equipments - Objeto com informações do tipo Equipments
	 */

	public List<SAT> SATReportInfo(String equip_id) {

		List<SAT> list = new ArrayList<>();

		String sql = "SELECT st.name, c.city_name, r.road_name, st.km, st.number_lanes, "
				+"CASE "
				+"WHEN dir_lane1 = 'N' THEN 'NORTH / SOUTH' "
				+"WHEN dir_lane1 = 'S' THEN 'SOUTH / NORTH' "
				+"WHEN dir_lane1 = 'L' THEN 'EAST / WEST' "
				+"WHEN dir_lane1 = 'O' THEN 'WEST / EAST' " 			 
				+"ELSE ' --- ' "
				+"END 'directions' "
				+"FROM sat_equipment st "
				+"INNER JOIN concessionaire_cities c ON c.city_id = st.city "
				+"INNER JOIN concessionaire_roads r ON r.road_id = st.road "
				+"WHERE st.equip_id = '"+ equip_id + "' AND st.visible = 1";
		
		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			// System.out.println(sql);
						
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					
					SAT sat = new SAT();

					sat.setNome(rs.getString(1));
				    sat.setCidade(rs.getString(2));
					sat.setEstrada(rs.getString(3));
					sat.setKm(rs.getString(4));
					sat.setQtdeFaixas(rs.getString(5));
					sat.setSentidos(rs.getString(6));
					
					list.add(sat);
				}
			}

		}catch (SQLException sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));
			
			SystemLog.logErrorSQL(errorFolder.concat("error_sat_report_info"), EquipmentsDAO.class.getCanonicalName(), sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
			
		} finally {
			
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		return list;
	}	

	// --------------------------------------------------------------------------------------------------------------
	
	/**
	 * Método para obter informação de um equipamento específico
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param equip_id - Equipamento ID
	 * @param module - Módulo	 
	 * @return Equipments - Objeto com informações do tipo Equipments
	 */

	public SAT headerInfoSAT(String equip_id) {

		TranslationMethods tm = new TranslationMethods();
		
		SAT sat = new SAT();
		
		String sql = "SELECT st.name, st.km, r.road_name, dir_lane1 "					
				+"FROM sat_equipment st "						
				+"INNER JOIN concessionaire_roads r ON r.road_id = st.road "
				+"WHERE st.equip_id = '"+ equip_id + "' AND st.visible = 1";
		
		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			//System.out.println(sql);
		
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
										
					sat.setNome(rs.getString(1));	
					sat.setKm(rs.getString(2));
					sat.setEstrada(rs.getString(3));
				    sat.setSentido1(tm.directionTab(rs.getString(4)));
				    sat.setSentido2(tm.oppositeDirectionTab(rs.getString(4)));
				    sat.setSentido1Abbr(tm.directionAbbreviation(rs.getString(4)));
				    sat.setSentido2Abbr(tm.oppositeDirectionAbbreviation(rs.getString(4)));
					
				}
			}

		}catch (SQLException sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));
			
			SystemLog.logErrorSQL(errorFolder.concat("error_sat_header"), EquipmentsDAO.class.getCanonicalName(), sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
			
		} finally {
			
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		return sat;
	}	

	// --------------------------------------------------------------------------------------------------------------

	/**
	 * Método para obter informa��o de um equipamentos especifico
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0 
	 * @return ArrayList - Lista do tipo DMS	
	 */	

	public ArrayList<Equipments> listDMSSites(){

		ArrayList<Equipments> lista = new ArrayList<Equipments>();         

		String sql = "SELECT equip_id, name FROM dms_equipment WHERE visible = 1 ORDER BY name ASC";	

		try {			

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);			

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {			

					Equipments eq = new Equipments();

					eq.setEquip_id(rs.getInt("equip_id"));
					eq.setNome(rs.getString("name"));

					lista.add(eq);
				}
			}

		}catch (SQLException sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));
			
			SystemLog.logErrorSQL(errorFolder.concat("error_list_dms"), EquipmentsDAO.class.getCanonicalName(), sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
			
		} finally {
			
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		return lista;

	}	

	// --------------------------------------------------------------------------------------------------------------
	
	public Equipments cftvCam(int id) {
		
		String script = "SELECT name, equip_ip, km FROM cftv_equipment WHERE equip_id= "+id+"";
		
		Equipments data = new Equipments();
		
		try {
			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			ps = conn.prepareStatement(script);
			rs = ps.executeQuery();

			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					data.setNome(rs.getString(1));
					data.setEquip_ip(rs.getString(2));
					data.setKm(rs.getString(3));
				}				
			}
		
		}catch (SQLException sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));
			
			SystemLog.logErrorSQL(errorFolder.concat("error_cftv_cam"), EquipmentsDAO.class.getCanonicalName(), sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
			
		} finally {
			
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return data;
	}
	
	
	// --------------------------------------------------------------------------------------------------------------

	/**
	 * Método para verificar se um equipamento existe
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0	
	 * @param id - Equipamento ID
	 * @param table - Table id	
	 * @return boolean - Verdadeiro ou falso
	 */

	public boolean checkExists(int id, String table) {

		boolean checked = false; 
		
		// QUERY
		String select = "SELECT equip_id FROM "+table+"_equipment WHERE equip_id = ?";

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(select);
			ps.setInt(1, id);

			rs = ps.executeQuery();

			if(rs.isBeforeFirst())
				checked = true;

		}catch (SQLException sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));
			
			SystemLog.logErrorSQL(errorFolder.concat("error_checking_id"), EquipmentsDAO.class.getCanonicalName(), sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
			
		} finally {
			
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return checked;	
	}


	//--------------------------------------------------------------------------------------------------------------

	/**
	 * M�todo para obter o nome de um equipamento
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0	
	 * @param id - Equipamento ID
	 * @param table - Table id	
	 * @return String - Retorna o nome do equipamento
	 */

	public String equipmentName(int id, String table) {

		String name = ""; 
		
		// QUERY
		String select = "SELECT name FROM "+table+"_equipment WHERE equip_id = ?";

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(select);
			ps.setInt(1, id);

			rs = ps.executeQuery();

			if(rs.isBeforeFirst())
				while(rs.next()) {

					name = rs.getString(1);              											
				} 
			
		}catch (SQLException sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));
			
			SystemLog.logErrorSQL(errorFolder.concat("error_get_equip_name"), EquipmentsDAO.class.getCanonicalName(), sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
			
		} finally {
			
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return name;	
	}

	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * M�todo para obter um m�dulo pelo tipo
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0	
	 * @param type - Tipo de m�dulo
	 * @return String - Retorna o nome do m�dulo
	 */

	public String getModule(String type) { 

		String module = null;

		switch(type) {

		case "cftv": module = ModulesEnum.CFTV.getModule() ; break;
		case "colas": module = ModulesEnum.COLAS.getModule(); ; break;
		case"comms": module = ModulesEnum.COMMS.getModule(); ; break;
		case "dai": module = ModulesEnum.DAI.getModule(); ; break;
		case "ocr": module = ModulesEnum.OCR.getModule(); ; break;
		case "mto": module = ModulesEnum.METEO.getModule(); ; break;
		case "dms": module = ModulesEnum.DMS.getModule(); ; break;
		case "sat": module = ModulesEnum.SAT.getModule(); ; break;
		case "sos": module = ModulesEnum.SOS.getModule(); ; break;
		case "speed": module = ModulesEnum.SPEED.getModule(); ; break;		
		case "wim": module = ModulesEnum.WIM.getModule(); ; break;

		}

		return module;
	}
	
	//--------------------------------------------------------------------------------------------------------------
			
	/**
	 * Método para salvar um novo equipamento na base de dados
	 * @author Wellington 25/12/2021
	 * @version 1.0
	 * @since 1.0
	 * @param dataSource objeto para manipular dados dos equipamentos
	 * @return verdadeiro caso tenha salvo dados do equipamento	
	 */
	public boolean saveEquipment(EquipmentDataSource dataSource, String interfaceView){

		boolean status = false;  


			// DMS INSERT QUERY
			String insertDMS = "INSERT INTO dms_equipment (equip_id, creation_date, creation_username, name, city, road, km, "
					+ "linear_width, linear_posX, linear_posY, vw_linear_width, vw_linear_posX, vw_linear_posY, "
					+ "map_width, map_posX, map_posY, vw_map_width, vw_map_posX, vw_map_posY, latitude, longitude, direction, visible, equip_ip, driver) "
					+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			// SAT INSERT QUERY
			String insertSAT = "INSERT INTO sat_equipment (equip_id, creation_date, creation_username, name, city, road, km, "					
					+ "linear_width, linear_posX, linear_posY, vw_linear_width, vw_linear_posX, vw_linear_posY, "
					+ "map_width, map_posX, map_posY, vw_map_width, vw_map_posX, vw_map_posY, latitude, longitude, direction, visible, equip_ip, "
					+ "service_position, number_lanes, dir_lane1, dir_lane2, dir_lane3, dir_lane4, dir_lane5, dir_lane6, dir_lane7, dir_lane8) "
					+ "values  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			// SOS INSERT QUERY
			String insertSOS = "INSERT INTO sos_equipment (equip_id, creation_date, creation_username, name, city, road, km, "
					+ "linear_width, linear_posX, linear_posY, vw_linear_width, vw_linear_posX, vw_linear_posY, map_width, map_posX, map_posY, "
					+ "vw_map_width, vw_map_posX, vw_map_posY,  latitude, longitude, direction, visible, equip_ip, port, model, master_sip) "
					+ " values  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			// SPEED INSERT QUERY
			String insertSpeed = "INSERT INTO speed_equipment (equip_id, creation_date, creation_username, name, city, road, km, "
					+ "linear_width, linear_posX, linear_posY, vw_linear_width, vw_linear_posX, vw_linear_posY, map_width, map_posX, map_posY, "
					+ "vw_map_width, vw_map_posX, vw_map_posY, latitude, longitude, direction, visible, equip_ip_indicator, equip_ip_radar) "
					+ " values  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			// Meteo INSERT QUERY
			String insertMeteo = "INSERT INTO meteo_equipment (equip_id, creation_date, creation_username, name, city, road, km, "
					+ "linear_width, linear_posX, linear_posY, vw_linear_width, vw_linear_posX, vw_linear_posY, map_width, map_posX, map_posY, "
					+ "vw_map_width, vw_map_posX, vw_map_posY, latitude, longitude, direction, visible, equip_ip, config_id, equip_type, port) "
					+ " values  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			// GENERIC INSERT QUERY		
			String insertDevices = "INSERT INTO "+dataSource.getTable()+"_equipment (equip_id, creation_date, creation_username, name, city, road, km, "
					+ "linear_width, linear_posX, linear_posY, vw_linear_width, vw_linear_posX, vw_linear_posY, map_width, map_posX, map_posY, "
					+ "vw_map_width, vw_map_posX, vw_map_posY, latitude, longitude, direction, visible, equip_ip) "
					+ " values  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			
			// NOTIFICATION STATUS TABLE INSERT QUERY
			String queryNotification = "INSERT INTO notifications_status (notifications_id, equip_id, equip_type, equip_ip, equip_name, equip_km) "        							
					+ "VALUES (null, ?, ?, ?, ?, ?)"; 
						
			// DMS ACTIVE MESSAGES TABLE INSERT QUERY
			String insertActiveMessage = "INSERT INTO dms_messages_active (equip_id, id_message, activation_username, date_time_message, id_modify, active) "
					+ "values (?,?,?,?,?,?)";
			
			// SAT EQUIPMENT TABLE INSERT QUERY
			String insertFilterDirections = "INSERT INTO filter_directions(id, equip_id, lane, direction) VALUES (null,?,?,?)";

			// -------------------------------------------------------------------------------------------------------------------------------------------------------
					
			String insert = "";
			
			int linearWidth = 0, linearPosx = 0, linearPosY = 0;
			int mapWidth = 0, mapPosX = 0, mapPosY = 0;
			
			// -------------------------------------------------------------------------------------------------------------------------------------------------------
			
			    switch (dataSource.getTable()) {
				
				case "meteo": insert = insertMeteo; break;
				case "dms": insert = insertDMS; break;
				case "sat": insert = insertSAT; break;
				case "sos": insert = insertSOS; break;
				case "speed": insert = insertSpeed; break;				

				default: insert = insertDevices; break;
				
				}
						
			// -------------------------------------------------------------------------------------------------------------------------------------------------------
			
			try {
				
			if(interfaceView.equals("linear")) {  // LINEAR INTERFACE
								
				if(dataSource.getTable().equals("sat")) {
					
					linearWidth = 90; 
					linearPosx = 36;
					linearPosY = 486;
				}
				
				else if(dataSource.getTable().equals("dms")) {
					
					linearWidth = 90;
					linearPosx = 40;
					linearPosY = 421;
					
				}else if(dataSource.getTable().equals("speed")) {
					
					linearWidth = 30;
					linearPosx = 35;
					linearPosY = 485;
					
				}else {
					
					linearWidth = 45;
					linearPosx = 35;
					linearPosY = 485;			
				}
			
			}else {   // MAP INTERFACE
																				
				if(dataSource.getTable().equals("dms") || dataSource.getTable().equals("sat"))
						mapWidth = 60;
									
				else if(dataSource.getTable().equals("speed") || dataSource.getTable().equals("meteo")) 
						mapWidth = 20;					
					
				else 	mapWidth = 15;
				
				
			}
			
			// -------------------------------------------------------------------------------------------------------------------------------------------------------
					
			 conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			 ps = conn.prepareStatement(insert);

				ps.setInt(1, dataSource.getEquipId());
				ps.setString(2, dataSource.getDatetime());
				ps.setString(3, dataSource.getUsername()); 		
				ps.setString(4, dataSource.getEquipName());
				ps.setString(5, dataSource.getCity());
				ps.setString(6, dataSource.getRoad());
				ps.setString(7, dataSource.getKm());       			
				ps.setInt(8,  linearWidth); // Linear Width
				ps.setInt(9,  linearPosx); // Linear posX
				ps.setInt(10, linearPosY); // Linear posY				
				ps.setInt(11,  linearWidth); // VIDEO WALL Linear Width				
				ps.setInt(12, linearPosx); // VIDEO WALL Linear posX
				ps.setInt(13, linearPosY); // VIDEO WALL Linear posY
				ps.setInt(14, mapWidth); // Map Width
				ps.setInt(15, mapPosX); // Map posX
				ps.setInt(16, mapPosY); // Map posY
				ps.setInt(17, mapWidth); // VIDEO WALL Map Width
				ps.setInt(18, mapPosX); // VIDEO WALL Map posX
				ps.setInt(19, mapPosY); // VIDEO WALL Map posY			
				ps.setDouble(20, dataSource.getLatitude()); // LATITUDE
				ps.setDouble(21, dataSource.getLongitude()); // LONGITUDE
				ps.setString(22, dataSource.getDirection()); // DIREÇÂO	
				ps.setBoolean(23, true);
				
				if(!dataSource.getTable().equals("speed"))
					ps.setString(24, dataSource.getIpAddress());   
				
				if(dataSource.getTable().equals("dms"))
					ps.setInt(25, dataSource.getDmsDriver());
				
				else if(dataSource.getTable().equals("sat")) {
					ps.setString(25, dataSource.getServiceFlow());
					ps.setInt(26, dataSource.getNumLanes()); 
					ps.setString(27, dataSource.getLane1());
					ps.setString(28, dataSource.getLane2());
					ps.setString(29, dataSource.getLane3());
					ps.setString(30, dataSource.getLane4());
					ps.setString(31, dataSource.getLane5());
					ps.setString(32, dataSource.getLane6());
					ps.setString(33, dataSource.getLane7());
					ps.setString(34, dataSource.getLane8());					
				}
				
				else if(dataSource.getTable().equals("sos")) {
					
					ps.setInt(25, dataSource.getPort());
					ps.setInt(26, dataSource.getModel());
					ps.setString(27, dataSource.getSip());
				}
				
				else if(dataSource.getTable().equals("speed")) {					
					ps.setString(24, dataSource.getIpAddressIndicator());
					ps.setString(25, dataSource.getIpAddressRadar());					
				}
				
				else if(dataSource.getTable().equals("meteo")) {
					
					ps.setInt(25, dataSource.getConfigId());
					ps.setString(26, dataSource.getEquipType());
					ps.setInt(27, dataSource.getPort());			
					
				}

			int success = ps.executeUpdate();

				if(success > 0) {
					
					int successNotif = 0;
					
					// NOTIFICATION ADD		
					ps = conn.prepareStatement(queryNotification);
					
					if(!dataSource.getTable().equals("speed")) {
					
						ps.setInt(1, dataSource.getEquipId());					
					    ps.setString(2, dataSource.getEquipType());
						ps.setString(3, dataSource.getIpAddress());
						ps.setString(4, dataSource.getEquipName());
						ps.setString(5, dataSource.getKm());
						
						successNotif = ps.executeUpdate();
					
					}
					
					else {
						
						ps.setInt(1, dataSource.getEquipId()); 		
						ps.setString(2, dataSource.getEquipType()+" I");
						ps.setString(3, dataSource.getIpAddressIndicator());
						ps.setString(4, dataSource.getEquipName());
						ps.setString(5, dataSource.getKm());

						successNotif = ps.executeUpdate();
						
						if(successNotif > 0) {
							
							ps.setInt(1, dataSource.getEquipId()); 		
							ps.setString(2, dataSource.getEquipType()+" R");
							ps.setString(3, dataSource.getIpAddressRadar());
							ps.setString(4, dataSource.getEquipName());
							ps.setString(5, dataSource.getKm());

						    successNotif = ps.executeUpdate();
						}						
					}	
											
					if(successNotif > 0) {
						
					   if(!dataSource.getTable().equals("dms") && !dataSource.getTable().equals("sat"))
						     status = true;
					   
					   else {						   
		
						if(dataSource.getTable().equals("dms")) {
							   
							ps = conn.prepareStatement(insertActiveMessage);
			
								ps.setInt(1, dataSource.getEquipId());
								ps.setInt(2, 0);            			
								ps.setString(3, dataSource.getUsername());    
								ps.setString(4, dataSource.getDatetime());
								ps.setInt(5, 0);   
								ps.setInt(6, 1);								
							   
						}
						
						else if(dataSource.getTable().equals("sat")) {
												
							for(int ln = 1; ln <= dataSource.getNumLanes(); ln++) {								
									
							ps = conn.prepareStatement(insertFilterDirections);
						
								ps.setInt(1, dataSource.getEquipId()); 			
								ps.setInt(2, ln);
												
								switch(ln) {
								
								case 1: ps.setString(3, dataSource.getLane1()); break;
								case 2: ps.setString(3, dataSource.getLane2()); break;
								case 3: ps.setString(3, dataSource.getLane3()); break;
								case 4: ps.setString(3, dataSource.getLane4()); break;
								case 5: ps.setString(3, dataSource.getLane5()); break;
								case 6: ps.setString(3, dataSource.getLane6()); break;
								case 7: ps.setString(3, dataSource.getLane7()); break;
								case 8: ps.setString(3, dataSource.getLane8()); break;
								
								}										
							}							  														
						}
						
						 int success2 = ps.executeUpdate();
						    
						    if(success2 > 0)
						    	status = true;						
					}					   
				}
			}         		  	

		}catch (SQLException sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));
			
			SystemLog.logErrorSQL(errorFolder.concat("error_saving"), EquipmentsDAO.class.getCanonicalName(), sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
			
		} finally {
			
			ConnectionFactory.closeConnection(conn, ps);
		}

		return status;	
	}
	
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * Método para obter dados de um equipamento da base de dados
	 * @author Wellington 25/12/2021
	 * @version 1.0
	 * @since 1.0
	 * @param id id do equipamento
	 * @param table módulo do equipamento
	 * @param interfaceView intercade utilizada
	 * @param permission permissão do usuário (verificar se possui acesso ao video wall)
	 * @return um objeto do tipo dataSource com informações do equipamento
	 */
	public EquipmentDataSource searchEquipament(int id, String table, String interfaceView, int permission) { 

		String widthOption = "";
		String select = "";
				
		String linearWidth = "linear_width";
		String mapWidth = "map_width";
		String videoWallLinearWidth = "vw_linear_width";
		String videoWallMapWidth = "vw_map_width";
				
		EquipmentDataSource dataSource = new EquipmentDataSource();
		
		// ---------------------------------------------------------------------------------------------------------------------
		
		// SWITCH CONDITION
		
		if(permission != 9) {
			
			if(interfaceView.equals("linear"))
				widthOption = linearWidth;
			
			else widthOption = mapWidth;		
			
		}else { 
			
			if(interfaceView.equals("linear"))
				widthOption = videoWallLinearWidth;
			
			else widthOption = videoWallMapWidth;
			
		}	
		
		// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
				
		// QUERIES
				
		String selectCftv = "SELECT equip_id, name, city, road, km, "+widthOption+", latitude, longitude, direction, equip_ip FROM cftv_equipment WHERE equip_id = ? ";	
		String selectColas = "SELECT equip_id, name, city, road, km, "+widthOption+", latitude, longitude, direction, equip_ip FROM colas_equipment WHERE equip_id = ? ";
		String selectDai = "SELECT equip_id, name, city, road, km, "+widthOption+", latitude, longitude, direction, equip_ip FROM dai_equipment WHERE equip_id = ? ";
		String selectMeteo = "SELECT equip_id, name, city, road, km, "+widthOption+", latitude, longitude, direction, equip_ip, config_id, equip_type, port FROM meteo_equipment WHERE equip_id = ? ";		
		String selectOcr = "SELECT equip_id, name, city, road, km, "+widthOption+", latitude, longitude, direction, equip_ip FROM ocr_equipment WHERE equip_id = ? ";
		String selectdms = "SELECT equip_id, name, city, road, km, "+widthOption+", latitude, longitude, direction, equip_ip, driver FROM dms_equipment WHERE equip_id = ? ";
		String selectSat = "SELECT equip_id, name, city, road, km, "+widthOption+", " +		
		"latitude, longitude, direction, equip_ip, service_position, number_lanes, dir_lane1, dir_lane2, dir_lane3, dir_lane4, dir_lane5, dir_lane6, dir_lane7, dir_lane8 FROM sat_equipment WHERE equip_id = ? ";		
		String selectSos = "SELECT equip_id, name, city, road, km, "+widthOption+", latitude, longitude, direction, equip_ip, port, model, master_sip FROM sos_equipment WHERE equip_id = ? ";
		String selectSpeed = "SELECT equip_id, name, city, road, km, "+widthOption+", latitude, longitude, direction, equip_ip_indicator, equip_ip_radar FROM speed_equipment WHERE equip_id = ? ";
		String selectWim = "SELECT equip_id, name, city, road, km, "+widthOption+", latitude, longitude, direction, equip_ip FROM wim_equipment WHERE equip_id = ? "; 
				
		// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
				
		switch (table) {
		
			case "cftv": select = selectCftv; break;
			case "colas": select = selectColas; break;			
			case "dai": select = selectDai; break;
			case "meteo": select = selectMeteo; break;
			case "ocr": select = selectOcr; break;			
			case "dms": select = selectdms; break;  	
			case "sat": select = selectSat; break;
			case "sos": select = selectSos; break;
			case "speed": select = selectSpeed; break;	
			case "wim" : select = selectWim; break;
			
		}
		
		// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
		
		try {
			
		conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
		ps = conn.prepareStatement(select);

		ps.setInt(1,  id);
		
		rs = ps.executeQuery();
		
		//System.out.println("SEL: "+select);
	
		if(rs.isBeforeFirst()) {
			while(rs.next()){

				dataSource.setEquipId(rs.getInt(1));				
				dataSource.setEquipName(rs.getString(2));
				dataSource.setCity(rs.getString(3));
				dataSource.setRoad(rs.getString(4));
				dataSource.setKm(rs.getString(5));
				dataSource.setWidth(rs.getInt(6)); 
				dataSource.setLatitude(rs.getDouble(7));
				dataSource.setLongitude(rs.getDouble(8));
				dataSource.setDirection(rs.getString(9));
											
			 if(table.equals("meteo")) {
				 dataSource.setIpAddress(rs.getString(10));	
				 dataSource.setConfigId(rs.getInt(11));
				 dataSource.setEquipType(rs.getString(12));
				 dataSource.setPort(rs.getInt(13));
			   
			 }
			 			 
			 else if(table.equals("dms")) { 
				 dataSource.setIpAddress(rs.getString(10));	
				 dataSource.setDmsDriver(rs.getInt(11));
			 }
			
			 else if(table.equals("sat")) {		
				 dataSource.setIpAddress(rs.getString(10));
				 dataSource.setServiceFlow(rs.getString(11));
				 dataSource.setNumLanes(rs.getInt(12));
				 dataSource.setLane1(rs.getString(13));
				 dataSource.setLane2(rs.getString(14));
				 dataSource.setLane3(rs.getString(15)  == null ? "" : rs.getString(15));
				 dataSource.setLane4(rs.getString(16)  == null ? "" : rs.getString(16));
				 dataSource.setLane5(rs.getString(17)  == null ? "" : rs.getString(17));
				 dataSource.setLane6(rs.getString(18)  == null ? "" : rs.getString(18));
				 dataSource.setLane7(rs.getString(19)  == null ? "" : rs.getString(19));
				 dataSource.setLane8(rs.getString(20)  == null ? "" : rs.getString(20));
				 									 
			 }
			 
			 else if(table.equals("sos")) {
				 dataSource.setIpAddress(rs.getString(10));
				 dataSource.setPort(rs.getInt(11));
				 dataSource.setModel(rs.getInt(12));
				 dataSource.setSip(rs.getString(13));
			 }
			 
			 else if(table.equals("speed")) {
				 dataSource.setIpAddressIndicator(rs.getString(10));
				 dataSource.setIpAddressRadar(rs.getString(11));
			 }
		  }
	   }
		
		}catch (SQLException sqle) {
		
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));
		
			SystemLog.logErrorSQL(errorFolder.concat("search_error"), EquipmentsDAO.class.getCanonicalName(), sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
		
		} finally {
		
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return dataSource;            	  

	}

	// --------------------------------------------------------------------------------------------------------------
	
	/**
	 * Método para posicionar equipamentos
	 * @author Wellington 26/12/2021
	 * @version 1.0
	 * @since Release 1.0
	 * @param id - Equipamento ID
	 * @param table - Table id	
	 * @param posX - Posição eixo X
	 * @param posY - Posição eixo Y
	 * @param positionView - Interface a posicinar
	 * @return boolean- Verdadeiro ou falso	
	 */	
	public boolean positionEquipment(int id, String table, int posX, int posY, String positionView, int permission) { 
			
		boolean positioned = false;
		
		String posXOption = "";
		String posYOption = "";
		String update = "";
				
		String linearPosX = "linear_posX";
		String linearPosY = "linear_posY";
		String mapPosX = "map_posX";
		String mapPosY = "map_posY";
		String videoWallMapPosX = "vw_map_posX";
		String videoWallMapPosY = "vw_map_posY";
		String videoWallLinearPosX = "vw_linear_posX";
		String videoWallLinearPosY = "vw_linear_posY";
				
		// ---------------------------------------------------------------------------------------------------------------------
		
		// SWITCH CONDITION
		
		if(permission != 9) {
			
			if(positionView.equals("linear")) {
				posXOption = linearPosX;
				posYOption = linearPosY;
			
			}else {
				
				posXOption = mapPosX;	
				posYOption = mapPosY;			
			}
			
		}else { 
			
			if(positionView.equals("linear")) {
				posXOption = videoWallLinearPosX;
				posYOption = videoWallLinearPosY;
			
			}else {
				
				posXOption = videoWallMapPosX;
				posYOption = videoWallMapPosY;		
			}
			
		}	
		
		// ---------------------------------------------------------------------------------------------------------------------
				
		// QUERIES
		
		String updateCftv = "UPDATE cftv_equipment SET "+posXOption+" = ?, "+posYOption+" = ? WHERE equip_id = ? ";
		String updateColas = "UPDATE colas_equipment SET "+posXOption+" = ?, "+posYOption+" = ? WHERE equip_id = ? ";
		String updateDai = "UPDATE dai_equipment SET "+posXOption+" = ?, "+posYOption+" = ? WHERE equip_id = ? ";
		String updateDms = "UPDATE dms_equipment SET "+posXOption+" = ?, "+posYOption+" = ? WHERE equip_id = ? ";
		String updateMeteo = "UPDATE meteo_equipment SET "+posXOption+" = ?, "+posYOption+" = ? WHERE equip_id = ? ";
		String updateOcr = "UPDATE ocr_equipment SET "+posXOption+" = ?, "+posYOption+" = ? WHERE equip_id = ? ";	
		String updateSat = "UPDATE sat_equipment SET "+posXOption+" = ?, "+posYOption+" = ? WHERE equip_id = ? ";
		String updateSos = "UPDATE sos_equipment SET "+posXOption+" = ?, "+posYOption+" = ? WHERE equip_id = ? ";
		String updateSpeed = "UPDATE speed_equipment SET "+posXOption+" = ?, "+posYOption+" = ? WHERE equip_id = ? ";
		String updateWim = "UPDATE wim_equipment SET "+posXOption+" = ?, "+posYOption+" = ? WHERE equip_id = ? ";
		
		// ---------------------------------------------------------------------------------------------------------------------
		
		switch (table) {
		
			case "cftv": update = updateCftv; break;
			case "colas": update = updateColas; break;			
			case "dai": update = updateDai; break;
			case "meteo": update = updateMeteo; break;
			case "ocr": update = updateOcr; break;			
			case "dms": update = updateDms; break;  	
			case "sat": update = updateSat; break;
			case "sos": update = updateSos; break;
			case "speed": update = updateSpeed; break;	
			case "wim" : update = updateWim; break;
		
	  }
		
	  // ---------------------------------------------------------------------------------------------------------------------
		
		try {
					
		conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
		ps = conn.prepareStatement(update);
		
		ps.setInt(1,  posX);
		ps.setInt(2,  posY);
		ps.setInt(3,  id);
		
		int rs =  ps.executeUpdate();

		if(rs > 0)
			positioned = true;
		
		}catch (SQLException sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));
			
			SystemLog.logErrorSQL(errorFolder.concat("error_positioning"), EquipmentsDAO.class.getCanonicalName(), sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
			
		} finally {
			
			ConnectionFactory.closeConnection(conn, ps);
		}
				
		return positioned;
				
	}

	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * Método para atualizar equipamentos
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param dataSource objeto com a origem dos dados para atualização
	 * @param permission nível de permissão do usuário
	 * @param view - interface a ser atualizada
	 * @return boolean - Verdairo ou falso
	 */	
	public boolean updateEquipment(EquipmentDataSource dataSource, String updateView, int permission){    

		boolean updated = false;
		int id = 0;
		
		String widthOption = "";
		String update = "";
				
		String linearWidth = "linear_width";
		String mapWidth = "map_width";
		String videoWallLinearWidth = "vw_linear_width";
		String videoWallMapWidth = "vw_map_width";

		// -----------------------------------------------------------------------------------------------------------------------------------------
		
		// SWITCH CONDITION
	
			if(permission != 9) {
				
				if(updateView.equals("linear"))
					widthOption = linearWidth;
				
				else widthOption = mapWidth;		
				
			}else { 
				
				if(updateView.equals("linear"))
					widthOption = videoWallLinearWidth;
				
				else widthOption = videoWallMapWidth;
				
			}	
		
		// -----------------------------------------------------------------------------------------------------------------------------------------
		
		// NOTIFICATIONS QUERY

		String notificationId = "SELECT notifications_id FROM notifications_status WHERE equip_id = ? and equip_type = ?";
		String updateNotifications = "UPDATE notifications_status SET equip_ip = ?, equip_name = ?,  equip_km = ? WHERE notifications_id = ? ";

		// -----------------------------------------------------------------------------------------------------------------------------------------
		
		// LINEAR QUERIES
		
		String updateCftvLinear = "UPDATE cftv_equipment SET name = ?, city = ?, direction = ?, road = ?, km = ?, "+widthOption+" = ?, equip_ip = ? WHERE equip_id = ? ";
		String updateColasLinear = "UPDATE colas_equipment SET name = ?, city = ?, direction = ?, road = ?, km = ?, "+widthOption+" = ?, equip_ip = ? WHERE equip_id = ? ";
		String updateDaiLinear = "UPDATE dai_equipment SET name = ?, city = ?, direction = ?, road = ?, km = ?, "+widthOption+" = ?, equip_ip = ? WHERE equip_id = ? ";
		String updateDmsLinear = "UPDATE dms_equipment SET name = ?, city = ?, direction = ?, road = ?, km = ?, "+widthOption+" = ?, equip_ip = ? WHERE equip_id = ? ";
		String updateMeteoLinear = "UPDATE meteo_equipment SET name = ?, city = ?, direction = ?, road = ?, km = ?, "+widthOption+" = ?, equip_ip = ?, port = ? WHERE equip_id = ? ";
		String updateOcrLinear = "UPDATE ocr_equipment SET name = ?, city = ?, direction = ?, road = ?, km = ?, "+widthOption+" = ?, equip_ip = ? WHERE equip_id = ? ";		
		String updateSatLinear = "UPDATE sat_equipment SET name = ?, city = ?, direction = ?, road = ?, km = ?, "+widthOption+" = ?, equip_ip = ?, " +
		"service_position = ?, number_lanes = ?, dir_lane1 = ?, dir_lane2 = ?, dir_lane3 = ?, dir_lane4 = ?, dir_lane5 = ?, dir_lane6 = ?, dir_lane7 = ?, dir_lane8 = ? WHERE equip_id = ? ";	
		String updateSosLinear = "UPDATE sos_equipment SET name = ?, city = ?, direction = ?, road = ?, km = ?, "+widthOption+" = ?, equip_ip = ?, port = ?, model = ?, master_sip = ? WHERE equip_id = ? ";
		String updateSpeedLinear = "UPDATE speed_equipment SET name = ?, city = ?, direction = ?, road = ?, km = ?, "+widthOption+" = ?, equip_ip_indicator = ?, equip_ip_radar = ? WHERE equip_id = ? ";
		String updateWimLinear = "UPDATE wim_equipment SET name = ?, city = ?, direction = ?, road = ?, km = ?, "+widthOption+" = ?, equip_ip = ? WHERE equip_id = ? ";
				
		// -----------------------------------------------------------------------------------------------------------------------------------------
		
		// MAP QUERIES
		
		String updateCftvMap = "UPDATE cftv_equipment SET name = ?, city = ?, direction = ?, road = ?, km = ?, "+widthOption+" = ?, latitude = ?, longitude = ?, equip_ip = ? WHERE equip_id = ? ";	
		String updateColasMap = "UPDATE colas_equipment SET name = ?, city = ?, direction = ?, road = ?, km = ?, "+widthOption+" = ?, latitude = ?, longitude = ?, equip_ip = ? WHERE equip_id = ? ";	
		String updateDaiMap = "UPDATE dai_equipment SET name = ?, city = ?, direction = ?, road = ?, km = ?, "+widthOption+" = ?, latitude = ?, longitude = ?, equip_ip = ? WHERE equip_id = ? ";	
		String updateDmsMap = "UPDATE dms_equipment SET name = ?, city = ?, direction = ?, road = ?, km = ?, "+widthOption+" = ?, latitude = ?, longitude = ?, equip_ip = ? WHERE equip_id = ? ";	
		String updateMeteoMap = "UPDATE meteo_equipment SET name = ?, city = ?, direction = ?, road = ?, km = ?, "+widthOption+" = ?, latitude = ?, longitude = ?, equip_ip = ?, port = ? WHERE equip_id = ? ";	
		String updateOcrMap = "UPDATE ocr_equipment SET name = ?, city = ?, direction = ?, road = ?, km = ?, "+widthOption+" = ?, latitude = ?, longitude = ?, equip_ip = ? WHERE equip_id = ? ";		
		String updateSatMap = "UPDATE sat_equipment SET name = ?, city = ?, direction = ?, road = ?, km = ?, "+widthOption+" = ?, latitude = ?, longitude = ?, equip_ip = ?, " +
		"number_lanes = ?, dir_lane1 = ?, dir_lane2 = ?, dir_lane3 = ?, dir_lane4 = ?, dir_lane5 = ?, dir_lane6 = ?, dir_lane7 = ?, dir_lane8 = ? WHERE equip_id = ? ";	
		String updateSosMap = "UPDATE sos_equipment SET name = ?, city = ?, direction = ?, road = ?, km = ?, "+widthOption+" = ?, latitude = ?, longitude = ?, equip_ip = ?, port = ?, model = ?, master_sip = ?  WHERE equip_id = ? ";	
		String updateSpeedMap = "UPDATE speed_equipment SET name = ?, city = ?, direction = ?, road = ?, km = ?, "+widthOption+" = ?, latitude = ?, longitude = ?, equip_ip_indicator = ?, equip_ip_radar = ? WHERE equip_id = ? ";	
		String updateWimMap = "UPDATE wim_equipment SET name = ?, city = ?, direction = ?, road = ?, km = ?, "+widthOption+" = ?, latitude = ?, longitude = ?, equip_ip = ? WHERE equip_id = ? ";	
		

		// -----------------------------------------------------------------------------------------------------------------------------------------
		
			switch (dataSource.getTable()) {
			
				case "cftv": 
					
					if(updateView.equals("linear"))
					    update = updateCftvLinear;
					
					else update = updateCftvMap; break;
					
				case "colas": 
					
					if(updateView.equals("linear"))
					    update = updateColasLinear;
					
					else update = updateColasMap; break;
				
				case "dai": 
					
					if(updateView.equals("linear"))
					    update = updateDaiLinear;
					
					else update = updateDaiMap; break;
					
				case "meteo": 
					
					if(updateView.equals("linear"))
					    update = updateMeteoLinear;
					
					else update = updateMeteoMap; break;
					
				case "ocr": 
	
					if(updateView.equals("linear"))
					    update = updateOcrLinear;
					
					else update = updateOcrMap; break;
					
				case "dms": 
	
					if(updateView.equals("linear"))
					    update = updateDmsLinear;
					
					else update = updateDmsMap; break;
	
				case "sat": 
	
					if(updateView.equals("linear"))
					    update = updateSatLinear;
					
					else update = updateSatMap; break;
					
				case "sos": 
	
					if(updateView.equals("linear"))
					    update = updateSosLinear;
					
					else update = updateSosMap; break;
	
				case "speed": 
	
					if(updateView.equals("linear"))
					    update = updateSpeedLinear;
					
					else update = updateSpeedMap; break;
	
				case "wim": 
	
					if(updateView.equals("linear"))
					    update = updateWimLinear;
					
					else update = updateWimMap; break;
							
		   }		
		
		// -----------------------------------------------------------------------------------------------------------------------------------------
							
		try {
			
		conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
		ps = conn.prepareStatement(update);
										
				ps.setString(1, dataSource.getEquipName());		
				ps.setString(2, dataSource.getCity());
				ps.setString(3,  dataSource.getDirection());
				ps.setString(4, dataSource.getRoad());
				ps.setString(5, dataSource.getKm());
				ps.setInt(6, dataSource.getWidth());
									
			if(updateView.equals("linear"))	{									
												
					if(dataSource.getTable().equals("meteo")) {
						ps.setString(7, dataSource.getIpAddress());	
						ps.setInt(8, dataSource.getPort());							
						ps.setInt(9,  dataSource.getEquipId());
					}
				
					else if(dataSource.getTable().equals("speed")) {
						ps.setString(7, dataSource.getIpAddressIndicator());				
					    ps.setString(8, dataSource.getIpAddressRadar());
					    ps.setInt(9, dataSource.getEquipId());
					}
				
					else if(dataSource.getTable().equals("sos")){
						ps.setString(7, dataSource.getIpAddress());	
						ps.setInt(8, dataSource.getPort());	
						ps.setInt(9, dataSource.getModel());	
						ps.setString(10, dataSource.getSip());
						ps.setInt(11,  dataSource.getEquipId());			
						
					}
					
					else if(dataSource.getTable().equals("sat")) {
						ps.setString(7, dataSource.getIpAddress());
						ps.setString(8, dataSource.getServiceFlow());
						ps.setInt(9, dataSource.getNumLanes());	
						ps.setString(10, dataSource.getLane1());
						ps.setString(11, dataSource.getLane2());
						ps.setString(12, dataSource.getLane3());
						ps.setString(13, dataSource.getLane4());
						ps.setString(14, dataSource.getLane5());
						ps.setString(15, dataSource.getLane6());
						ps.setString(16, dataSource.getLane7());
						ps.setString(17, dataSource.getLane8());
						ps.setInt(18,  dataSource.getEquipId());		
					}
					
					else {
						
						ps.setString(7, dataSource.getIpAddress());						       
						ps.setInt(8,  dataSource.getEquipId());
						
					}
			
			}else {
				
						ps.setDouble(7,  dataSource.getLatitude());
						ps.setDouble(8,  dataSource.getLongitude());
																		
					if(dataSource.getTable().equals("meteo")) {
						ps.setString(9, dataSource.getIpAddress());	
						ps.setInt(10, dataSource.getPort());							
						ps.setInt(11,  dataSource.getEquipId());
					}
				
					else if(dataSource.getTable().equals("speed")) {
						ps.setString(9, dataSource.getIpAddressIndicator());				
					    ps.setString(10, dataSource.getIpAddressRadar());
					    ps.setInt(11, dataSource.getEquipId());
					}
				
					else if(dataSource.getTable().equals("sos")){
						ps.setString(9, dataSource.getIpAddress());	
						ps.setInt(10, dataSource.getPort());	
						ps.setInt(11, dataSource.getModel());	
						ps.setString(12, dataSource.getSip());
						ps.setInt(13,  dataSource.getEquipId());			
						
					}
					
					else if(dataSource.getTable().equals("sat")) {
						ps.setString(9, dataSource.getIpAddress());	
						ps.setInt(10, dataSource.getNumLanes());	
						ps.setString(11, dataSource.getLane1());
						ps.setString(12, dataSource.getLane2());
						ps.setString(13, dataSource.getLane3());
						ps.setString(14, dataSource.getLane4());
						ps.setString(15, dataSource.getLane5());
						ps.setString(16, dataSource.getLane6());
						ps.setString(17, dataSource.getLane7());
						ps.setString(18, dataSource.getLane8());
						ps.setInt(19, dataSource.getEquipId());		
					}	
					
					else {									
							  
						ps.setString(9, dataSource.getIpAddress());						       
						ps.setInt(10,  dataSource.getEquipId());					    
						       
					}					
			   }
			
		int res = ps.executeUpdate();

		if (res > 0) {
			
			if(!dataSource.getTable().equals("speed")) {

				ps = conn.prepareStatement(notificationId);
	
				ps.setInt(1, dataSource.getEquipId());	
				ps.setString(2, dataSource.getEquipType());
	
				rs = ps.executeQuery();
	
					if(rs.isBeforeFirst()) {
						while(rs.next()) {
		
							id = rs.getInt(1);	
		
						}
					}
	
				ps = conn.prepareStatement(updateNotifications);
	
				ps.setString(1, dataSource.getIpAddress());
				ps.setString(2, dataSource.getEquipName()); 
				ps.setString(3, dataSource.getKm());						
				ps.setInt(4, id);	
	
				int res2 = ps.executeUpdate();
	
				if(res2 > 0)
					updated = true;	
				
			}else {
				
				// UPDATE INDICATOR REGISTER
				
				ps = conn.prepareStatement(notificationId);
				
				ps.setInt(1, dataSource.getEquipId());	
				ps.setString(2, dataSource.getEquipType()+" I");
	
					rs = ps.executeQuery();
		
						if(rs.isBeforeFirst()) {
							while(rs.next()) {
			
								id = rs.getInt(1);				
							}
						}
						
				ps = conn.prepareStatement(updateNotifications);
				
				ps.setString(1, dataSource.getIpAddressIndicator());
				ps.setString(2, dataSource.getEquipName()); 
				ps.setString(3, dataSource.getKm());						
				ps.setInt(4, id);	
	
				int res2 = ps.executeUpdate();
	
				if(res2 > 0) {
					
					// UPDATE RADAR REGISTER
					
					ps = conn.prepareStatement(notificationId);
					
					ps.setInt(1, dataSource.getEquipId());	
					ps.setString(2, dataSource.getEquipType()+" R");
		
						rs = ps.executeQuery();
			
							if(rs.isBeforeFirst()) {
								while(rs.next()) {
				
									id = rs.getInt(1);				
								}
							}
					
					ps = conn.prepareStatement(updateNotifications);
					
					ps.setString(1, dataSource.getIpAddressRadar());
					ps.setString(2, dataSource.getEquipName()); 
					ps.setString(3, dataSource.getKm());						
					ps.setInt(4, id);	
					
					int res3 = ps.executeUpdate();
					
					if(res3 > 0)
						updated = true;						
				}		        
			}
		}
		
		}catch (SQLException sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));
			
			SystemLog.logErrorSQL(errorFolder.concat("error_updating"), EquipmentsDAO.class.getCanonicalName(), sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
			
		} finally {
			
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		
		return updated;
		
	}
	
	// --------------------------------------------------------------------------------------------------------------
	
	/**
	 * Metodo para deletar de um equipamento
	 * @author Wellington 26/12/2021
	 * @version 1.0
	 * @since Release 1.0
	 * @param id - Equipamento ID
	 * @param table - Table id		
	 * @return boolean- Verdadeiro ou falso
	 */
	
	public boolean deleteEquipment(int id, String equipType, String table) { 
		
		boolean deleted = false;
		
		String delete = "";

		// -----------------------------------------------------------------------------------------------------------------------------------------
		
		// NOTIFICATIONS QUERY

		String deleteNotification = "DELETE FROM notifications_status WHERE notifications_id = ? ";
		String notificationId = "SELECT notifications_id FROM notifications_status WHERE equip_id = ? and equip_type = ?";

		// -----------------------------------------------------------------------------------------------------------------------------------------
		
		// Meteo TYPE ONLY
		
		String selectMeteoType = "SELECT equip_type FROM meteo_equipment WHERE equip_id = ?";
		
		// -----------------------------------------------------------------------------------------------------------------------------------------
		
		// QUERIES
		
		String deleteCftv = "DELETE FROM cftv_equipment WHERE equip_id = ? ";
		String deleteColas = "DELETE FROM colas_equipment WHERE equip_id = ? ";
		String deleteDai = "DELETE FROM dai_equipment WHERE equip_id = ? ";
		String deleteDms = "DELETE FROM dms_equipment WHERE equip_id = ? ";
		String deleteMeteo = "DELETE FROM meteo_equipment WHERE equip_id = ? ";
		String deleteOcr = "DELETE FROM ocr_equipment WHERE equip_id = ? ";		
		String deleteSat = "DELETE FROM sat_equipment WHERE equip_id = ? ";
		String deleteSos = "DELETE FROM sos_equipment WHERE equip_id = ? ";
		String deleteSpeed = "DELETE FROM speed_equipment WHERE equip_id = ? ";
		String deleteWim = "DELETE FROM wim_equipment WHERE equip_id = ? ";
		
		// -----------------------------------------------------------------------------------------------------------------------------------------
		
		switch (table) {
		
			case "cftv": delete = deleteCftv; break;
			case "colas": delete = deleteColas; break;			
			case "dai": delete = deleteDai; break;
			case "meteo": delete = deleteMeteo; break;
			case "ocr": delete = deleteOcr; break;			
			case "dms": delete = deleteDms; break;  	
			case "sat": delete = deleteSat; break;
			case "sos": delete = deleteSos; break;
			case "speed": delete = deleteSpeed; break;	
			case "wim" : delete = deleteWim; break;
			
		}
		
		// -----------------------------------------------------------------------------------------------------------------------------------------
	
		try {
		
		conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
		
		if(table.equals("meteo")) { // GET Meteo EQUIP TYPE
			
			// SELECT Meteo TYPE
			ps = conn.prepareStatement(selectMeteoType);
			ps.setInt(1,  id);
		
			rs = ps.executeQuery();
						
				if(rs.isBeforeFirst()) {
					while(rs.next()) {

						equipType = rs.getString(1);	
					}
				}			
		    }	
		
		 // ------------------------------------------------
		
			ps = conn.prepareStatement(delete);  
			ps.setInt(1,  id);

			int rsp =  ps.executeUpdate();

			if(rsp > 0) {	
				
				if(!table.equals("speed")) {
						
					// DELETE NOTIFICATION
					ps = conn.prepareStatement(notificationId);
					ps.setInt(1,  id);
					ps.setString(2, equipType);

					rs = ps.executeQuery();
					
						if(rs.isBeforeFirst()) {
							while(rs.next()) {

								id = rs.getInt(1);	
							}
						}

					//DELETE NOTIFICATION
					ps = conn.prepareStatement(deleteNotification);
					ps.setInt(1,  id);
							
					int rs2 =  ps.executeUpdate();
		
					if(rs2 > 0) 
						deleted = true;		
					
				} else {
										
					// DELETE INDICATOR REGISTER
					
					int idAux = 0;
					
					ps = conn.prepareStatement(notificationId);
					
					ps.setInt(1, id);	
					ps.setString(2, equipType+" I");
		
						rs = ps.executeQuery();
			
							if(rs.isBeforeFirst()) {
								while(rs.next()) {
				
									idAux = rs.getInt(1);				
								}
							}
							
					// DELETE NOTIFICATION
					ps = conn.prepareStatement(deleteNotification);
					ps.setInt(1,  idAux);
							
					int res2 = ps.executeUpdate();	
					
					if(res2 > 0) {
						
						// DELETE RADAR REGISTER
						
						ps = conn.prepareStatement(notificationId);
						
						ps.setInt(1, id);	
						ps.setString(2, equipType+" R");
			
							rs = ps.executeQuery();
				
								if(rs.isBeforeFirst()) {
									while(rs.next()) {
					
										idAux = rs.getInt(1);				
									}
								}
								
						// DELETE NOTIFICATION
						ps = conn.prepareStatement(deleteNotification);
						ps.setInt(1,  idAux);
												
						int res3 =  ps.executeUpdate();
						 
						if(res3 > 0) 
							deleted = true;							 
					}								
				}			
			}
			
		}catch (SQLException sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));
			
			SystemLog.logErrorSQL(errorFolder.concat("error_deleting"), EquipmentsDAO.class.getCanonicalName(), sqle.getErrorCode(), sqle.getSQLState(), sqle.getMessage(), errors.toString());
			
		} finally {
			
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
				
		return deleted;
		
	}
	
	
	// --------------------------------------------------------------------------------------------------------------

} 