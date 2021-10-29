package br.com.tracevia.webapp.dao.global;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.taskdefs.AbstractCvsTask.Module;

import br.com.tracevia.webapp.cfg.ModulesEnum;
import br.com.tracevia.webapp.dao.dms.MessagesDAO;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.cftv.CFTV;
import br.com.tracevia.webapp.model.colas.Colas;
import br.com.tracevia.webapp.model.comms.COMMS;
import br.com.tracevia.webapp.model.dai.DAI;
import br.com.tracevia.webapp.model.dms.DMS;
import br.com.tracevia.webapp.model.dms.Messages;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.ocr.OCR;
import br.com.tracevia.webapp.model.meteo.mto.MTO;
import br.com.tracevia.webapp.model.meteo.sv.SV;
import br.com.tracevia.webapp.model.sat.SAT;
import br.com.tracevia.webapp.model.sos.SOS;
import br.com.tracevia.webapp.model.speed.Speed;
import br.com.tracevia.webapp.model.wim.WIM;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class EquipmentsDAO {

	private Connection conn;
	protected ConnectionFactory connection = new ConnectionFactory();
	private PreparedStatement ps;
	private ResultSet rs;	

	// --------------------------------------------------------------------------------------------------------------

	/**
	 * Atualizar tamanho do equipamento
	 * @author 
	 * @version 1.0
	 * @since Release 1.0
	 * @param mod - Módulo
	 * @param view - Mapa gis ou linear
	 * @param width - Comprimento a ser alterado
	 * @throws Exception
	 * 
	 */	

	public void setWidthMap(String modulo, String view, int width) throws Exception {

		String sql = "UPDATE " + modulo + "_equipment SET " + view + "_width = ? WHERE equip_id > 0;";

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(sql);

			ps.setInt(1, width);

			ps.executeUpdate();

		} catch (SQLException sqle) {
			throw new Exception("Erro ao atualizar dados " + sqle);        		    

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
	 * @param mod - Módulo
	 * @return ArrayList - Lista de equipamentos
	 * @throws Exception
	 */

	public ArrayList<Equipments> buildEquipmentsInterface(String modulo, int permission) throws Exception {

		ArrayList<Equipments> lista = new ArrayList<Equipments>();

		String query = "";

		String sql = "SELECT equip_id, name, c.city_name, r.road_name, km, linear_width, " +
				"linear_posX, linear_posY, map_width, map_posX, map_posY, direction FROM "+modulo+"_equipment eq " +
				"INNER JOIN concessionaire_cities c ON c.city_id = eq.city " +
				"INNER JOIN concessionaire_roads r ON r.road_id = eq.road " +
				"WHERE visible = 1 ";

		String sqlVW = "SELECT equip_id, name, c.city_name, r.road_name, km, vw_linear_width, " +
				"vw_linear_posX, vw_linear_posY, vw_map_width, vw_map_posX, vw_map_posY, direction FROM "+modulo+"_equipment eq " +
				"INNER JOIN concessionaire_cities c ON c.city_id = eq.city " +
				"INNER JOIN concessionaire_roads r ON r.road_id = eq.road " +
				"WHERE visible = 1 ";

		try {

			if(permission != 9)
				query = sql;

			else query = sqlVW;

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			//	System.out.println(query);

			ps = conn.prepareStatement(query);					
			rs = ps.executeQuery();

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
					equip.setDirection(rs.getString(12));

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
	 * Método para listar equipamentos por módulo
	 * @author Guilherme
	 * @version 1.0
	 * @since Release 1.0
	 * @param mod - Módulo
	 * @return ArrayList - Lista de equipamentos
	 * @throws Exception
	 */

	public ArrayList<SOS> buildSosEquipmentsInterface(int permission) throws Exception {

		ArrayList<SOS> lista = new ArrayList<>();

		String query = "";

		String sql = "SELECT equip_id, name, port, c.city_name, r.road_name, km, linear_width, " +
				"linear_posX, linear_posY, map_width, map_posX, map_posY, model, master_sip, direction FROM sos_equipment eq " +
				"INNER JOIN concessionaire_cities c ON c.city_id = eq.city " +
				"INNER JOIN concessionaire_roads r ON r.road_id = eq.road " +
				"WHERE visible = 1 ";

		String sqlVW = "SELECT equip_id, name, port, c.city_name, r.road_name, km, vw_linear_width, " +
				"vw_linear_posX, vw_linear_posY, vw_map_width, vw_map_posX, vw_map_posY, model, master_sip, direction FROM sos_equipment eq " +
				"INNER JOIN concessionaire_cities c ON c.city_id = eq.city " +
				"INNER JOIN concessionaire_roads r ON r.road_id = eq.road " +
				"WHERE visible = 1 ";

		try {

			if(permission != 9)
				query = sql;

			else query = sqlVW;

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
					sos.setDirection(rs.getString(15));

					lista.add(sos);
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
	 * Método para listar equipamentos do tipo SAT
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0	
	 * @return ArrayList - Lista de equipamentos
	 * @throws Exception
	 * 
	 */

	public ArrayList<SAT> buildSatEquipmentsInterface(int permission) throws Exception {

		ArrayList<SAT> lista = new ArrayList<SAT>();
		TranslationMethods translator = new TranslationMethods();

		String dir1 = " ", dir2 = " ", dir3 = " ", dir4 = " ", dir5 = " ", dir6 = " ", dir7= " ", dir8 = " ";
		String sentido1 = "", sentido2 = "";

		String query = "";

		String sql = "SELECT equip_id, name, c.city_name, r.road_name, km, number_lanes, dir_lane1, dir_lane2, dir_lane3, dir_lane4, " +
				"dir_lane5, dir_lane6, dir_lane7, dir_lane8, linear_width, linear_posX, linear_posY, map_width, map_posX, map_posY, " +
				"service_position, direction FROM sat_equipment eq " +
				"INNER JOIN concessionaire_cities c ON c.city_id = eq.city " +
				"INNER JOIN concessionaire_roads r ON r.road_id = eq.road " +
				"WHERE visible = 1 ";

		String sqlVW = "SELECT equip_id, name, c.city_name, r.road_name, km, number_lanes, dir_lane1, dir_lane2, dir_lane3, dir_lane4, " +
				"dir_lane5, dir_lane6, dir_lane7, dir_lane8, vw_linear_width, vw_linear_posX, vw_linear_posY, vw_map_width, vw_map_posX, vw_map_posY, " +
				"service_position, direction FROM sat_equipment eq " +
				"INNER JOIN concessionaire_cities c ON c.city_id = eq.city " +
				"INNER JOIN concessionaire_roads r ON r.road_id = eq.road " +
				"WHERE visible = 1 ";


		try {

			if(permission != 9)
				query = sql;

			else query = sqlVW;

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
					sat.setDirection(rs.getString(22));

					//equip.setLinearHeight((int) (equip.getLinearWidth()*0.232)); //

					/*if(dms.getPosicao().equals("horizontal")) {
						dms.setHorizontal(true);
					}else {
						dms.setHorizontal(false);
					}						
					 */

					lista.add(sat);
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
	 * Método para listar equipamentos do tipo PMV
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0	
	 * @return ArrayList - Lista de equipamentos
	 * @throws Exception
	 * 
	 */

	public ArrayList<DMS> buildDMSEquipmentsInterface(int permission) throws Exception {

		ArrayList<DMS> lista = new ArrayList<DMS>();

		String query = "";

		String sql = "SELECT equip_id, ip_equip, driver, name, c.city_name, r.road_name, km, "
				+ "linear_width, linear_posX, linear_posY, map_width, map_posX, map_posY, id_message, id_modify, active, direction "
				+ "FROM pmv_equipment eq " 
				+ "INNER JOIN pmv_messages_active act ON act.id_equip = eq.equip_id " 
				+ "INNER JOIN concessionaire_cities c ON c.city_id = eq.city " 
				+ "INNER JOIN concessionaire_roads r ON r.road_id = eq.road "								 
				+ "WHERE visible = 1 "
				+ "ORDER BY eq.equip_id ASC"; 

		String sqlVW = "SELECT equip_id, ip_equip, driver, name, c.city_name, r.road_name, km, "
				+ "vw_linear_width, vw_linear_posX, vw_linear_posY, vw_map_width, vw_map_posX, vw_map_posY, id_message, id_modify, active, direction"
				+ "FROM pmv_equipment eq " 
				+ "INNER JOIN pmv_messages_active act ON act.id_equip = eq.equip_id " 
				+ "INNER JOIN concessionaire_cities c ON c.city_id = eq.city " 
				+ "INNER JOIN concessionaire_roads r ON r.road_id = eq.road "								 
				+ "WHERE visible = 1 "
				+ "ORDER BY eq.equip_id ASC"; 

		try {

			if(permission != 9)
				query = sql;

			else query = sqlVW;

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(query);				
			rs = ps.executeQuery();

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
					dms.setEquip_type(ModulesEnum.PMV.getModule());
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
					dms.setDirection(rs.getString(16));

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

	// --------------------------------------------------------------------------------------------------------------

	/**
	 * Método para listar informações de equipamentos para seleção
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param modulo - Módulo
	 * @return ArrayList - Lista de equipamentos
	 * @throws Exception
	 */

	public ArrayList<Equipments> EquipmentSelectOptions(String modulo) throws Exception {

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
	 * Método para obter o tipo de um equipamento MTO
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param equip - Equipamento
	 * @return String - Tipo de equipamento
	 * @throws Exception
	 */

	public String EquipmentSelectMTOType(String equip) throws Exception {

		String type = "";

		String sql = "SELECT type FROM mto_equipment WHERE equip_id = ? ";

		try {

			//GET CONNECTION			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(sql);
			ps.setString(1, equip);

			rs = ps.executeQuery();

			if (rs != null) {

				while (rs.next()) {

					type = rs.getString("type");

				}				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return type;
	}

	// --------------------------------------------------------------------------------------------------------------

	/**
	 * Método para obter número de faixas de um equipamento SAT
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param modulo - Módulo
	 * @param equipID - Equipamento ID
	 * @return ArrayList - Lista de equipamentos
	 * @throws Exception
	 */

	public Integer EquipmentSelectLanesNumber(String modulo, String equipID) throws Exception {

		int lanesNumber = 0;

		String sql = "SELECT number_lanes FROM "+modulo+"_equipment WHERE equip_id = ? AND visible = 1";

		try {

			//GET CONNECTION			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(sql);
			ps.setString( 1, equipID);
			rs = ps.executeQuery();

			if (rs != null) {

				while (rs.next()) {

					lanesNumber = rs.getInt(1);											

				}				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return lanesNumber;
	}

	// --------------------------------------------------------------------------------------------------------------


	/**
	 * Método para obter número de faixas (n) equipamentos SAT
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param mod - Módulo
	 * @param equips - Array de equipamentos
	 * @return int[] - Array com numero de faixas por equipamentos
	 * @throws Exception
	 */

	public int[] EquipmentSelectLanesNumber(String modulo, String[] equips) throws Exception {

		int[] lanesNumber = new int[equips.length];

		int lane = 0;

		String sql = "";

		for(int i = 0; i < equips.length; i++)
			sql = "SELECT number_lanes FROM "+modulo+"_equipment WHERE equip_id = '"+equips[i]+"' AND visible = 1";

		try {

			//GET CONNECTION			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			ps = conn.prepareStatement(sql);			
			rs = ps.executeQuery();

			if (rs != null) {

				while (rs.next()) {

					lanesNumber[lane] = rs.getInt(1);

					lane++; //increment

				}				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return lanesNumber;
	}

	// --------------------------------------------------------------------------------------------------------------

	/**
	 * Método para obter o nome de um equipamento
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param modulo - Módulo
	 * @param equipId - Equipamento ID
	 * @return String - Nome do equipamento
	 * @throws Exception
	 */

	public String EquipmentName(String modulo, String equipId) throws Exception {

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
	 * Método para obter informações para relatórios do tipo SAT
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param equip_id - Equipamento ID	
	 * @return SAT - Informações da classe SAT
	 * @throws Exception
	 */

	public SAT SATreportInfo(String equip_id) throws Exception {

		SAT eq = new SAT();

		try {

			//GET CONNECTION			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			String sql = "SELECT st.name, c.city_name, r.road_name, st.km, st.number_lanes, st.dir_lane1 FROM sat_equipment st "
					+ "INNER JOIN concessionaire_cities c ON c.city_id = st.city "
					+ "INNER JOIN concessionaire_roads r ON r.road_id = st.road "
					+ "WHERE st.equip_id = '"+ equip_id + "' AND st.visible = 1";

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					eq.setNome(rs.getString(1));
					eq.setCidade(rs.getString(2));
					eq.setEstrada(rs.getString(3));
					eq.setKm(rs.getString(4));
					eq.setNumFaixas(rs.getInt(5));		
					eq.setFaixa1(rs.getString(6));

				}
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps);}

		return eq;
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
	 * @throws Exception
	 */

	public List<Equipments> EquipReportInfo(String equip_id, String module) throws Exception {

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

		} catch (SQLException sqle) {

			sqle.printStackTrace();

		}finally {ConnectionFactory.closeConnection(conn, ps);}

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
	 * @throws Exception
	 */

	public Equipments EquipReportsInfo(String equip_id, String module) throws Exception {

		Equipments eq = new Equipments();
		
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
					
					

					eq.setNome(rs.getString(1));
					eq.setCidade(rs.getString(2));
					eq.setEstrada(rs.getString(3));
					eq.setKm(rs.getString(4));
					
				}
			}

		} catch (SQLException sqle) {

			sqle.printStackTrace();

		}finally {ConnectionFactory.closeConnection(conn, ps);}

		return eq;
	}	

	// --------------------------------------------------------------------------------------------------------------
	
	/**
	 * Método para obter informação de um equipamentos específico
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0 
	 * @return ArrayList - Lista do tipo PMV
	 * @throws Exception
	 */	

	public ArrayList<Equipments> listPMVSites() throws Exception{

		ArrayList<Equipments> lista = new ArrayList<Equipments>();         

		String sql = "";

		try {			

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);			

			sql = "SELECT equip_id, name FROM pmv_equipment WHERE visible = 1 ORDER BY name ASC";		

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

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps);}

		return lista;

	}	

	// --------------------------------------------------------------------------------------------------------------

	//Lista todos os Sites independente do status
	public ArrayList<Integer> listPMVSitesById() throws Exception{

		ArrayList<Integer> lista = new ArrayList<Integer>();         

		String sql = "";

		try {			

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);			

			sql = "SELECT equip_id FROM pmv_equipment WHERE visible = 1 ORDER BY equip_id ASC";		

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {			

					int id = rs.getInt("equip_id");							    

					lista.add(id);
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps);}

		return lista;

	}					

	// --------------------------------------------------------------------------------------------------------------

	/**
	 * Método para obter o primeiro sentido de um equipamento SAT
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param equip - Equipamento	  
	 * @return String - Primeira direção de um equipamento SAT
	 * @throws Exception
	 */

	public String firstDirection(String equip) throws Exception{

		String dir = "";        

		String sql = "";

		try {			

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);			

			sql = "SELECT dir_lane1 FROM sat_equipment WHERE equip_id = ? AND visible = 1";		

			ps = conn.prepareStatement(sql);
			ps.setString(1, equip);
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {			

					dir = rs.getNString(1);
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps);}

		return dir;

	}		

	// --------------------------------------------------------------------------------------------------------------

	/**
	 * Método para salvar um equipamento do tipo SAT 
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param equip - Objeto do tipo SAT
	 * @param table - Table id	 
	 * @return boolean - Verdairo ou falso após operação
	 * @throws Exception
	 */

	public boolean EquipSATRegisterMap(SAT equip, String table) throws Exception {

		boolean status = false; 

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			// SAT EQUIPMENT TABLE INSERT QUERY
			String query = "INSERT INTO "+table+"_equipment (equip_id, creation_date, creation_username, number_lanes, equip_ip, name, city, road, km, "
					+ "dir_lane1, dir_lane2, dir_lane3, dir_lane4, dir_lane5, dir_lane6, dir_lane7, dir_lane8, "
					+ "linear_width, linear_posX, linear_posY, vw_linear_width, vw_linear_posX, vw_linear_posY, "
					+ "map_width, map_posX, map_posY, vw_map_width, vw_map_posX, vw_map_posY, visible) "
					+ "values  ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			// NOTIFICATION STATUS TABLE INSERT QUERY
			String queryNotification = "INSERT INTO notifications_status (notifications_id, equip_id, equip_type, equip_ip, equip_name, equip_km) "        							
					+ "VALUES (null, ?, ?, ?, ?, ?)"; 


			// SAT ADD	
			ps = conn.prepareStatement(query);

			ps.setInt(1, equip.getEquip_id());
			ps.setString(2, equip.getCreation_date());
			ps.setString(3, equip.getCreation_username());
			ps.setInt(4, equip.getNumFaixas());
			ps.setString(5, equip.getEquip_ip());
			ps.setString(6, equip.getNome());
			ps.setString(7, equip.getCidade());
			ps.setString(8, equip.getEstrada());
			ps.setString(9, equip.getKm());
			ps.setString(10, equip.getFaixa1());	
			ps.setString(11, equip.getFaixa2());	
			ps.setString(12, equip.getFaixa3());	
			ps.setString(13, equip.getFaixa4());	
			ps.setString(14, equip.getFaixa5());	
			ps.setString(15, equip.getFaixa6());	
			ps.setString(16, equip.getFaixa7());	
			ps.setString(17, equip.getFaixa8());	          			    			
			ps.setInt(18, 100); // Linear Width
			ps.setInt(19, 37); // Linear posX
			ps.setInt(20, 107); // Linear posY
			ps.setInt(21, 100); // VIDEO WALL Linear Width
			ps.setInt(22, 37); // VIDEO WALL Linear posX
			ps.setInt(23, 107); // VIDEO WALL Linear posY
			ps.setInt(24, 75); // Map Width
			ps.setInt(25, 50); // Map posX
			ps.setInt(26, 50); // Map posY
			ps.setInt(27, 75); // VIDEO WALL Map Width
			ps.setInt(28, 50); // VIDEO WALL Map posX
			ps.setInt(29, 50); // VIDEO WALL Map posY
			ps.setBoolean(30, true);

			int success = ps.executeUpdate();

			if(success > 0) {    

				// NOTIFICATION ADD		
				ps = conn.prepareStatement(queryNotification);

				ps.setInt(1, equip.getEquip_id()); 			
				ps.setString(2, equip.getEquip_type());
				ps.setString(3, equip.getEquip_ip());
				ps.setString(4, equip.getNome());
				ps.setString(5, equip.getKm());

				int successNotif = ps.executeUpdate();

				if(successNotif > 0)            			
					status = true;	   				
			}             			

		} catch (SQLException sqle) {
			throw new Exception("Erro ao inserir dados " + sqle);        		    

		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}

		return status;	
	}

	// --------------------------------------------------------------------------------------------------------------

	/**
	 * Método para salvar um equipamento do tipo PMV
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param equip - Objeto do tipo PMV
	 * @param table - Table id	 
	 * @return boolean - Verdairo ou falso após operação
	 * @throws Exception
	 */

	public boolean EquipDMSRegisterMap(DMS equip, String table) throws Exception {

		boolean status = false; 

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			// DMS EQUIPMENT TABLE INSERT QUERY
			String query = "INSERT INTO "+table+"_equipment (equip_id, creation_date, creation_username, ip_equip, name, city, road, km, "
					+ "linear_width, linear_posX, linear_posY, vw_linear_width, vw_linear_posX, vw_linear_posY, "
					+ "map_width, map_posX, map_posY, vw_map_width, vw_map_posX, vw_map_posY, driver, visible) "
					+ "values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			// DMS ACTIVE MESSAGES TABLE INSERT QUERY
			String queryActive = "INSERT INTO "+table+"_messages_active (id_equip, id_message, activation_username, date_time_message, id_modify, active) "
					+ "values (?,?,?,?,?,?)";

			// NOTIFICATION STATUS TABLE INSERT QUERY
			String queryNotification = "INSERT INTO notifications_status (notifications_id, equip_id, equip_type, equip_ip, equip_name, equip_km) "        							
					+ "VALUES (null, ?, ?, ?, ?, ?)"; 


			//DMS ADD		
			ps = conn.prepareStatement(query);

			ps.setInt(1, equip.getEquip_id());
			ps.setString(2, equip.getCreation_date());
			ps.setString(3, equip.getCreation_username());      
			ps.setString(4, equip.getDms_ip());   
			ps.setString(5, equip.getNome());
			ps.setString(6, equip.getCidade());
			ps.setString(7, equip.getEstrada());
			ps.setString(8, equip.getKm());       			
			ps.setInt(9,  150); // Linear Width
			ps.setInt(10, 100); // Linear posX
			ps.setInt(11, 100); // Linear posY
			ps.setInt(12,  150); // VIDEO WALL Linear Width
			ps.setInt(13, 100); // VIDEO WALL Linear posX
			ps.setInt(14, 100); // VIDEO WALL Linear posY
			ps.setInt(15, 15); // Map Width
			ps.setInt(16, 50); // Map posX
			ps.setInt(17, 50); // Map posY
			ps.setInt(18, 15); // VIDEO WALL Map Width
			ps.setInt(19, 50); // VIDEO WALL Map posX
			ps.setInt(20, 50); // VIDEO WALL Map posY
			ps.setInt(21,  equip.getDms_type()); //driver
			ps.setBoolean(22, true);

			int success = ps.executeUpdate();

			if(success > 0) {

				//ACTIVE MESSAGES ADD
				ps = conn.prepareStatement(queryActive);

				ps.setInt(1, equip.getEquip_id());
				ps.setInt(2, 0);            			
				ps.setString(3, equip.getCreation_username());    
				ps.setString(4, equip.getCreation_date());
				ps.setInt(5, 0);   
				ps.setInt(6, 1);

				int success2 = ps.executeUpdate();

				if(success2 > 0) {    

					//NOTIFICATION ADD		
					ps = conn.prepareStatement(queryNotification);

					ps.setInt(1, equip.getEquip_id()); 			
					ps.setString(2, equip.getEquip_type());
					ps.setString(3, equip.getDms_ip());
					ps.setString(4, equip.getNome());
					ps.setString(5, equip.getKm());

					int successNotif = ps.executeUpdate();

					if(successNotif > 0)		
						status = true;	                				         

				}     				 	
			}            				  	


		} catch (SQLException sqle) {
			throw new Exception("Erro ao inserir dados " + sqle);        		    

		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}

		return status;	
	}


	// --------------------------------------------------------------------------------------------------------------

	/**
	 * Método para salvar um equipamento genérico
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param equip - Objeto do tipo genérico
	 * @param table - Table id	 
	 * @return boolean - Verdairo ou falso
	 * @throws Exception
	 */

	public boolean EquipSOSMap(SOS equip, String table) throws Exception {

		boolean status = false;     

		// GENERIC TABLE INSERT QUERY
		String query = "INSERT INTO "+table+"_equipment (equip_id, creation_date, creation_username, equip_ip, port, name, city, road, km, "
				+ "linear_width, linear_posX, linear_posY, vw_linear_width, vw_linear_posX, vw_linear_posY, map_width, map_posX, map_posY, "
				+ "vw_map_width, vw_map_posX, vw_map_posY, model, master_sip, visible)"
				+ " values  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		// NOTIFICATION STATUS TABLE INSERT QUERY
		String queryNotification = "INSERT INTO notifications_status (notifications_id, equip_id, equip_type, equip_ip, equip_name, equip_km) "        							
				+ "VALUES (null, ?, ?, ?, ?, ?)"; 

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			// GENERIC ADD			
			ps = conn.prepareStatement(query);

			ps.setInt(1, equip.getEquip_id());
			ps.setString(2, equip.getCreation_date());
			ps.setString(3, equip.getCreation_username());
			ps.setString(4, equip.getEquip_ip());
			ps.setInt(5, equip.getPort());
			ps.setString(6, equip.getNome());
			ps.setString(7, equip.getCidade());
			ps.setString(8, equip.getEstrada());
			ps.setString(9, equip.getKm());	
			ps.setInt(10, 30); // Linear Width
			ps.setInt(11, 30); // Linear posX
			ps.setInt(12, 500); // Linear posY
			ps.setInt(13, 30); // VIDEO WALL Linear Width
			ps.setInt(14, 30); // VIDEO WALL Linear posX
			ps.setInt(15, 500); // VIDEO WALL Linear posY
			ps.setInt(16, 20); // Map Width
			ps.setInt(17, 50); // Map posX
			ps.setInt(18, 50); // Map posY
			ps.setInt(19, 20); // VIDEO WALL Map Width
			ps.setInt(20, 50); // VIDEO WALL Map posX
			ps.setInt(21, 50); // VIDEO WALL Map posY
			ps.setInt(22, equip.getModel());
			ps.setString(23, equip.getSip());
			ps.setBoolean(24, true);

			int success = ps.executeUpdate();

			if(success > 0) {    

				//NOTIFICATION ADD		
				ps = conn.prepareStatement(queryNotification);

				ps.setInt(1, equip.getEquip_id()); 			
				ps.setString(2, equip.getEquip_type());
				ps.setString(3, equip.getEquip_ip());
				ps.setString(4, equip.getNome());
				ps.setString(5, equip.getKm());

				int successNotif = ps.executeUpdate();

				if(successNotif > 0)    			
					status = true;	                				         

			}  	

		} catch (SQLException sqle) {
			throw new Exception("Erro ao inserir dados " + sqle);

		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}

		return status;	
	}

	// --------------------------------------------------------------------------------------------------------------

	/**
	 * Método para salvar um equipamento genérico
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param equip - Objeto do tipo genérico
	 * @param table - Table id	 
	 * @return boolean - Verdairo ou falso
	 * @throws Exception
	 */

	public boolean EquipRegisterSpeedMap(Speed equip, String table) throws Exception {

		System.out.println("HERE");

		boolean status = false;     

		// GENERIC TABLE INSERT QUERY
		String query = "INSERT INTO speed_equipment (equip_id, creation_date, creation_username, equip_ip_indicator, equip_ip_radar, name, city, road, km, "
				+ "linear_width, linear_posX, linear_posY, vw_linear_width, vw_linear_posX, vw_linear_posY, map_width, map_posX, map_posY, "
				+ "vw_map_width, vw_map_posX, vw_map_posY, visible)"
				+ " values  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		// NOTIFICATION STATUS TABLE INSERT QUERY
		String queryNotification = "INSERT INTO notifications_status (notifications_id, equip_id, equip_type, equip_ip, equip_name, equip_km) "        							
				+ "VALUES (null, ?, ?, ?, ?, ?)"; 

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			// GENERIC ADD			
			ps = conn.prepareStatement(query);

			ps.setInt(1, equip.getEquip_id());
			ps.setString(2, equip.getCreation_date());
			ps.setString(3, equip.getCreation_username());
			ps.setString(4, equip.getEquip_ip_indicator());
			ps.setString(5, equip.getEquip_ip_radar());			
			ps.setString(6, equip.getNome());
			ps.setString(7, equip.getCidade());
			ps.setString(8, equip.getEstrada());
			ps.setString(9, equip.getKm());	
			ps.setInt(10, 30); // Linear Width
			ps.setInt(11, 30); // Linear posX
			ps.setInt(12, 500); // Linear posY
			ps.setInt(13, 30); // VIDEO WALL Linear Width
			ps.setInt(14, 30); // VIDEO WALL Linear posX
			ps.setInt(15, 500); // VIDEO WALL Linear posY
			ps.setInt(16, 20); // Map Width
			ps.setInt(17, 50); // Map posX
			ps.setInt(18, 50); // Map posY
			ps.setInt(19, 20); // VIDEO WALL Map Width
			ps.setInt(20, 50); // VIDEO WALL Map posX
			ps.setInt(21, 50); // VIDEO WALL Map posY			
			ps.setBoolean(22, true);

			int success = ps.executeUpdate();

			if(success > 0) {    

				// NOTIFICATION ADD		
				ps = conn.prepareStatement(queryNotification);

				ps.setInt(1, equip.getEquip_id()); 		
				ps.setString(2, equip.getEquip_type()+" I");
				ps.setString(3, equip.getEquip_ip_indicator());
				ps.setString(4, "I "+equip.getNome());
				ps.setString(5, equip.getKm());

				int successNotif = ps.executeUpdate();

				if(successNotif > 0) { 

					// NOTIFICATION ADD		
					ps = conn.prepareStatement(queryNotification);

					ps.setInt(1, equip.getEquip_id()); 		
					ps.setString(2, equip.getEquip_type()+" I");
					ps.setString(3, equip.getEquip_ip_indicator());
					ps.setString(4, "I "+equip.getNome());
					ps.setString(5, equip.getKm());

					int successNotif1 = ps.executeUpdate();

					if(successNotif1 > 0)
						status = true;  								 						

				}

			}  	

		} catch (SQLException sqle) {
			throw new Exception("Erro ao inserir dados " + sqle);

		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}

		return status;	
	}


	// --------------------------------------------------------------------------------------------------------------

	public boolean EquipRegisterMap(Equipments equip, String table) throws Exception {

		boolean status = false;     

		// GENERIC TABLE INSERT QUERY
		String query = "INSERT INTO "+table+"_equipment (equip_id, creation_date, creation_username, equip_ip, name, city, road, km, "
				+ "linear_width, linear_posX, linear_posY, vw_linear_width, vw_linear_posX, vw_linear_posY, map_width, map_posX, map_posY, "
				+ "vw_map_width, vw_map_posX, vw_map_posY, visible)"
				+ " values  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		// NOTIFICATION STATUS TABLE INSERT QUERY
		String queryNotification = "INSERT INTO notifications_status (notifications_id, equip_id, equip_type, equip_ip, equip_name, equip_km) "        							
				+ "VALUES (null, ?, ?, ?, ?, ?)"; 

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			// GENERIC ADD			
			ps = conn.prepareStatement(query);

			ps.setInt(1, equip.getEquip_id());
			ps.setString(2, equip.getCreation_date());
			ps.setString(3, equip.getCreation_username());
			ps.setString(4, equip.getEquip_ip());
			ps.setString(5, equip.getNome());
			ps.setString(6, equip.getCidade());
			ps.setString(7, equip.getEstrada());
			ps.setString(8, equip.getKm());	
			ps.setInt(9, 30); // Linear Width
			ps.setInt(10, 30); // Linear posX
			ps.setInt(11, 500); // Linear posY
			ps.setInt(12, 30); // VIDEO WALL Linear Width
			ps.setInt(13, 30); // VIDEO WALL Linear posX
			ps.setInt(14, 500); // VIDEO WALL Linear posY
			ps.setInt(15, 20); // Map Width
			ps.setInt(16, 50); // Map posX
			ps.setInt(17, 50); // Map posY
			ps.setInt(18, 20); // VIDEO WALL Map Width
			ps.setInt(19, 50); // VIDEO WALL Map posX
			ps.setInt(20, 50); // VIDEO WALL Map posY
			ps.setBoolean(21, true);

			int success = ps.executeUpdate();

			if(success > 0) {    

				//NOTIFICATION ADD		
				ps = conn.prepareStatement(queryNotification);

				ps.setInt(1, equip.getEquip_id()); 		
				ps.setString(2, equip.getEquip_type());
				ps.setString(3, equip.getEquip_ip());
				ps.setString(4, equip.getNome());
				ps.setString(5, equip.getKm());

				int successNotif = ps.executeUpdate();

				if(successNotif > 0)            			
					status = true;	                				         

			}  	

		} catch (SQLException sqle) {
			throw new Exception("Erro ao inserir dados " + sqle);

		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}

		return status;	
	}

	// --------------------------------------------------------------------------------------------------------------

	/**
	 * Método para atualizar um equipamento genérico
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param equip - Objeto do tipo genérico
	 * @param table - Table id	
	 * @param view - Mapa a ser atualizado 
	 * @return boolean - Verdairo ou falso
	 * @throws Exception
	 */

	public boolean EquipUpdateMap(Equipments equip, String table, String updateView, int permission) throws Exception {    

		boolean updated = false;
		int id = 0;

		// STATUS -----------------------------------------------------------------------------------------------------------------------------------------

		String notificationSafety = "SELECT notifications_id FROM notifications_status WHERE equip_id = ? and equip_type = ?";

		String notifications = "UPDATE notifications_status SET equip_ip = ?, equip_name = ?,  equip_km = ? WHERE notifications_id = ? ";

		// MONITOR -----------------------------------------------------------------------------------------------------------------------------------------

		try { //GET SLQException     

			if(table.equals("cftv")) {  //CFTV 

				String queryLinear = "";
				String queryMap = "";

				String queryCftvLinear = "UPDATE cftv_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, linear_width = ? WHERE equip_id = ? ";

				String queryCftvMap = "UPDATE cftv_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ? ";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWqueryCftvLinear = "UPDATE cftv_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, vw_linear_width = ? WHERE equip_id = ? ";

				String VWqueryCftvMap = "UPDATE cftv_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, vw_map_width = ? WHERE equip_id = ? ";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				if(permission != 9) {

					queryLinear = queryCftvLinear;
					queryMap = queryCftvMap;			

				}else { 

					queryLinear = VWqueryCftvLinear;
					queryMap = VWqueryCftvMap;		

				}				

				if(updateView.equals("linear"))
					ps = conn.prepareStatement(queryLinear);

				else  ps = conn.prepareStatement(queryMap);

				ps.setString(1,  equip.getNome());
				ps.setString(2,  equip.getEquip_ip());
				ps.setString(3,  equip.getCidade());
				ps.setString(4,  equip.getEstrada());
				ps.setString(5,  equip.getKm());
				ps.setInt(6,     equip.getMapWidth());            
				ps.setInt(7,  equip.getEquip_id());

				int res = ps.executeUpdate();

				if (res > 0) {

					ps = conn.prepareStatement(notificationSafety);

					ps.setInt(1, equip.getEquip_id());	
					ps.setString(2, ModulesEnum.CFTV.getModule());

					rs = ps.executeQuery();

					if(rs.isBeforeFirst()) {
						while(rs.next()) {

							id = rs.getInt(1);	

						}
					}

					ps = conn.prepareStatement(notifications);

					ps.setString(1, equip.getEquip_ip());
					ps.setString(2, equip.getNome()); 
					ps.setString(3, equip.getKm());						
					ps.setInt(4, id);	

					int res2 = ps.executeUpdate();

					if(res2 > 0)
						updated = true;						 


				}
			} 

			// --------------------------------------------------------------------------------------------------------------

			if(table.equals("colas")) { // COLAS 

				String queryLinear = "";
				String queryMap = "";

				String queryColasLinear = "UPDATE colas_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, linear_width = ? WHERE equip_id = ? ";

				String queryColasMap = "UPDATE colas_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ? ";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWqueryColasLinear = "UPDATE colas_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, vw_linear_width = ? WHERE equip_id = ? ";

				String VWqueryColasMap = "UPDATE colas_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, vw_map_width = ? WHERE equip_id = ? ";


				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				if(permission != 9) {

					queryLinear = queryColasLinear;
					queryMap = queryColasMap;			

				}else { 

					queryLinear = VWqueryColasLinear;
					queryMap = VWqueryColasMap;		

				}				

				if(updateView.equals("linear"))
					ps = conn.prepareStatement(queryLinear);

				else  ps = conn.prepareStatement(queryMap);

				ps.setString(1,  equip.getNome());
				ps.setString(2,  equip.getEquip_ip());
				ps.setString(3,  equip.getCidade());
				ps.setString(4,  equip.getEstrada());
				ps.setString(5,  equip.getKm());
				ps.setInt(6,     equip.getMapWidth());            
				ps.setInt(7,  equip.getEquip_id());

				int res = ps.executeUpdate();

				if (res > 0) {

					ps = conn.prepareStatement(notificationSafety);

					ps.setInt(1, equip.getEquip_id());	
					ps.setString(2, ModulesEnum.COLAS.getModule());

					rs = ps.executeQuery();

					if(rs.isBeforeFirst()) {
						while(rs.next()) {

							id = rs.getInt(1);	

						}
					}

					ps = conn.prepareStatement(notifications);

					ps.setString(1, equip.getEquip_ip());
					ps.setString(2, equip.getNome()); 
					ps.setString(3, equip.getKm());						
					ps.setInt(4, id);								

					int res2 = ps.executeUpdate();

					if(res2 > 0) 
						updated = true;						 


				}
			} 

			// --------------------------------------------------------------------------------------------------------------

			if(table.equals("comms")) { // COMMS 

				String queryLinear = "";
				String queryMap = "";

				String queryCOMMSLinear = "UPDATE comms_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, linear_width = ? WHERE equip_id = ? ";

				String queryCOMMSMap = "UPDATE comms_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ? ";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWqueryCOMMSLinear = "UPDATE comms_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, vw_linear_width = ? WHERE equip_id = ? ";

				String VWqueryCOMMSMap = "UPDATE comms_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, vw_map_width = ? WHERE equip_id = ? ";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//VIDEO WALL SWITCH
				if(permission != 9) {

					queryLinear = queryCOMMSLinear;
					queryMap = queryCOMMSMap;			

				}else { 

					queryLinear = VWqueryCOMMSLinear;
					queryMap = VWqueryCOMMSMap;		

				}				

				if(updateView.equals("linear"))
					ps = conn.prepareStatement(queryLinear);

				else  ps = conn.prepareStatement(queryMap);

				ps.setString(1,  equip.getNome());
				ps.setString(2,  equip.getEquip_ip());
				ps.setString(3,  equip.getCidade());
				ps.setString(4,  equip.getEstrada());
				ps.setString(5,  equip.getKm());
				ps.setInt(6,     equip.getMapWidth());            
				ps.setInt(7,  equip.getEquip_id());

				int res = ps.executeUpdate();

				if (res > 0) {

					ps = conn.prepareStatement(notificationSafety);

					ps.setInt(1, equip.getEquip_id());	
					ps.setString(2, ModulesEnum.COMMS.getModule());

					rs = ps.executeQuery();

					if(rs.isBeforeFirst()) {
						while(rs.next()) {

							id = rs.getInt(1);	

						}
					}

					ps = conn.prepareStatement(notifications);

					ps.setString(1, equip.getEquip_ip());
					ps.setString(2, equip.getNome()); 
					ps.setString(3, equip.getKm());						
					ps.setInt(4, id);										

					int res2 = ps.executeUpdate();

					if(res2 > 0) 
						updated = true;						 

				} 
			}  

			// --------------------------------------------------------------------------------------------------------------

			if(table.equals("dai")) { // DAI

				String queryLinear = "";
				String queryMap = "";

				String queryDAILinear = "UPDATE dai_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, linear_width = ? WHERE equip_id = ?";

				String queryDAIMap = "UPDATE dai_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ?";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWqueryDAILinear = "UPDATE dai_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, vw_linear_width = ? WHERE equip_id = ?";

				String VWqueryDAIMap = "UPDATE dai_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, vw_map_width = ? WHERE equip_id = ?";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//VIDEO WALL SWITCH
				if(permission != 9) {

					queryLinear = queryDAILinear;
					queryMap = queryDAIMap;			

				}else { 

					queryLinear = VWqueryDAILinear;
					queryMap = VWqueryDAIMap;		

				}				

				if(updateView.equals("linear"))
					ps = conn.prepareStatement(queryLinear);

				else  ps = conn.prepareStatement(queryMap);

				ps.setString(1,  equip.getNome());
				ps.setString(2,  equip.getEquip_ip());
				ps.setString(3,  equip.getCidade());
				ps.setString(4,  equip.getEstrada());
				ps.setString(5,  equip.getKm());
				ps.setInt(6,     equip.getMapWidth());            
				ps.setInt(7,  equip.getEquip_id());         			            			  

				int res = ps.executeUpdate();

				if (res > 0) {

					ps = conn.prepareStatement(notificationSafety);

					ps.setInt(1, equip.getEquip_id());	
					ps.setString(2, ModulesEnum.DAI.getModule());

					rs = ps.executeQuery();

					if(rs.isBeforeFirst()) {
						while(rs.next()) {

							id = rs.getInt(1);	

						}
					}

					ps = conn.prepareStatement(notifications);

					ps.setString(1, equip.getEquip_ip());
					ps.setString(2, equip.getNome()); 
					ps.setString(3, equip.getKm());						
					ps.setInt(4, id);	


					int res2 = ps.executeUpdate();

					if(res2 > 0) 
						updated = true;						 

				}		                   
			} 

			// --------------------------------------------------------------------------------------------------------------


			if(table.equals("ocr")) { // OCR

				String queryLinear = "";
				String queryMap = "";

				String queryOCRLinear = "UPDATE ocr_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, linear_width = ? WHERE equip_id = ?";

				String queryOCRMap = "UPDATE ocr_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ?";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWqueryOCRLinear = "UPDATE ocr_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, vw_linear_width = ? WHERE equip_id = ?";

				String VWqueryOCRMap = "UPDATE ocr_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, vw_map_width = ? WHERE equip_id = ?";


				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//VIDEO WALL SWITCH
				if(permission != 9) {

					queryLinear = queryOCRLinear;
					queryMap = queryOCRMap;			

				}else { 

					queryLinear = VWqueryOCRLinear;
					queryMap = VWqueryOCRMap;		

				}				

				if(updateView.equals("linear"))
					ps = conn.prepareStatement(queryLinear);

				else  ps = conn.prepareStatement(queryMap);

				ps.setString(1,  equip.getNome());
				ps.setString(2,  equip.getEquip_ip());
				ps.setString(3,  equip.getCidade());
				ps.setString(4,  equip.getEstrada());
				ps.setString(5,  equip.getKm());
				ps.setInt(6,     equip.getMapWidth());            
				ps.setInt(7,  equip.getEquip_id());

				int res = ps.executeUpdate();

				if (res > 0) {

					ps = conn.prepareStatement(notificationSafety);

					ps.setInt(1, equip.getEquip_id());	
					ps.setString(2, ModulesEnum.OCR.getModule());

					rs = ps.executeQuery();

					if(rs.isBeforeFirst()) {
						while(rs.next()) {

							id = rs.getInt(1);	

						}
					}

					ps = conn.prepareStatement(notifications);

					ps.setString(1, equip.getEquip_ip());
					ps.setString(2, equip.getNome()); 
					ps.setString(3, equip.getKm());						
					ps.setInt(4, id);									

					int res2 = ps.executeUpdate();

					if(res2 > 0) 					
						updated = true;						 


				}

			}  

			// --------------------------------------------------------------------------------------------------------------

			if(table.equals("mto")) { // MTO 

				String queryLinear = "";
				String queryMap = "";

				String queryMTOLinear = "UPDATE mto_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, linear_width = ? WHERE equip_id = ? ";

				String queryMTOMap = "UPDATE mto_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ? ";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWqueryMTOLinear = "UPDATE mto_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, vw_linear_width = ? WHERE equip_id = ? ";

				String VWqueryMTOMap = "UPDATE mto_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, vw_map_width = ? WHERE equip_id = ? ";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//VIDEO WALL SWITCH
				if(permission != 9) {

					queryLinear = queryMTOLinear;
					queryMap = queryMTOMap;			

				}else { 

					queryLinear = VWqueryMTOLinear;
					queryMap = VWqueryMTOMap;		

				}				

				if(updateView.equals("linear"))
					ps = conn.prepareStatement(queryLinear);

				else  ps = conn.prepareStatement(queryMap);

				ps.setString(1,  equip.getNome());
				ps.setString(2,  equip.getEquip_ip());
				ps.setString(3,  equip.getCidade());
				ps.setString(4,  equip.getEstrada());
				ps.setString(5,  equip.getKm());
				ps.setInt(6,     equip.getMapWidth());            
				ps.setInt(7,  equip.getEquip_id());    			            			  

				int res = ps.executeUpdate();

				if (res > 0) {

					ps = conn.prepareStatement(notificationSafety);

					ps.setInt(1, equip.getEquip_id());	
					ps.setString(2, ModulesEnum.MTO.getModule());

					rs = ps.executeQuery();

					if(rs.isBeforeFirst()) {
						while(rs.next()) {

							id = rs.getInt(1);	

						}
					}

					ps = conn.prepareStatement(notifications);

					ps.setString(1, equip.getEquip_ip());
					ps.setString(2, equip.getNome()); 
					ps.setString(3, equip.getKm());						
					ps.setInt(4, id);	

					int res2 = ps.executeUpdate();

					if(res2 > 0)
						updated = true;						 

				}
			}   


			// --------------------------------------------------------------------------------------------------------------

			if(table.equals("sv")) { // SV 

				String queryLinear = "";
				String queryMap = "";

				String querySVLinear = "UPDATE sv_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, linear_width = ? WHERE equip_id = ? ";

				String querySVMap = "UPDATE sv_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ? ";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWquerySVLinear = "UPDATE sv_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, vw_linear_width = ? WHERE equip_id = ? ";

				String VWquerySVMap = "UPDATE sv_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, vw_map_width = ? WHERE equip_id = ? ";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//VIDEO WALL SWITCH
				if(permission != 9) {

					queryLinear = querySVLinear;
					queryMap = querySVMap;			

				}else { 

					queryLinear = VWquerySVLinear;
					queryMap = VWquerySVMap;		

				}				

				if(updateView.equals("linear"))
					ps = conn.prepareStatement(queryLinear);

				else  ps = conn.prepareStatement(queryMap);

				ps.setString(1,  equip.getNome());
				ps.setString(2,  equip.getEquip_ip());
				ps.setString(3,  equip.getCidade());
				ps.setString(4,  equip.getEstrada());
				ps.setString(5,  equip.getKm());
				ps.setInt(6,     equip.getMapWidth());            
				ps.setInt(7,  equip.getEquip_id());     			            			  

				int res = ps.executeUpdate();

				if (res > 0) {

					ps = conn.prepareStatement(notificationSafety);

					ps.setInt(1, equip.getEquip_id());	
					ps.setString(2, ModulesEnum.SV.getModule());

					rs = ps.executeQuery();

					if(rs.isBeforeFirst()) {
						while(rs.next()) {

							id = rs.getInt(1);	

						}
					}

					ps = conn.prepareStatement(notifications);

					ps.setString(1, equip.getEquip_ip());
					ps.setString(2, equip.getNome()); 
					ps.setString(3, equip.getKm());						
					ps.setInt(4, id);							

					int res2 = ps.executeUpdate();

					if(res2 > 0)
						updated = true;						 

				}          	  

			} 

			// --------------------------------------------------------------------------------------------------------------

			if(table.equals("wim")) { // WIM

				String queryLinear = "";
				String queryMap = "";

				String queryWIMLinear = "UPDATE wim_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, linear_width = ? WHERE equip_id = ? ";

				String queryWIMMap= "UPDATE wim_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ? ";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWqueryWIMLinear = "UPDATE wim_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, vw_linear_width = ? WHERE equip_id = ? ";

				String VWqueryWIMMap = "UPDATE wim_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, vw_map_width = ? WHERE equip_id = ? ";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//VIDEO WALL SWITCH
				if(permission != 9) {

					queryLinear = queryWIMLinear;
					queryMap = queryWIMMap;			

				}else { 

					queryLinear = VWqueryWIMLinear;
					queryMap = VWqueryWIMMap;		

				}				

				if(updateView.equals("linear"))
					ps = conn.prepareStatement(queryLinear);

				else  ps = conn.prepareStatement(queryMap);

				ps.setString(1,  equip.getNome());
				ps.setString(2,  equip.getEquip_ip());
				ps.setString(3,  equip.getCidade());
				ps.setString(4,  equip.getEstrada());
				ps.setString(5,  equip.getKm());
				ps.setInt(6,     equip.getMapWidth());            
				ps.setInt(7,  equip.getEquip_id());    			            			  

				int res = ps.executeUpdate();

				if (res > 0) {

					ps = conn.prepareStatement(notificationSafety);

					ps.setInt(1, equip.getEquip_id());	
					ps.setString(2, ModulesEnum.WIM.getModule());

					rs = ps.executeQuery();

					if(rs.isBeforeFirst()) {
						while(rs.next()) {

							id = rs.getInt(1);	

						}
					}

					ps = conn.prepareStatement(notifications);

					ps.setString(1, equip.getEquip_ip());
					ps.setString(2, equip.getNome()); 
					ps.setString(3, equip.getKm());						
					ps.setInt(4, id);								

					int res2 = ps.executeUpdate();

					if(res2 > 0)
						updated = true;						 

				}          	  
			}         	  


		}catch(SQLException sqle) {

			sqle.printStackTrace();
		}
		finally { // Close Connection

			ConnectionFactory.closeConnection(conn, ps);
		}

		return updated;
	} 

	// --------------------------------------------------------------------------------------------------------------

	/**
	 * Método para atualizar um equipamento do tipo SAT
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param sat - Objeto do tipo SAT
	 * @param table - Table id	
	 * @param view - Mapa a ser atualizado 
	 * @return boolean - Verdairo ou falso
	 * @throws Exception
	 */

	public boolean EquipSATUpdateMap(SAT sat, String table, String updateView, int permission) throws Exception {    

		boolean updated = false;
		int id = 0;

		String queryLinear = "";
		String queryMap = "";

		String notificationSafety = "SELECT notifications_id FROM notifications_status WHERE equip_id = ? and equip_type = ?";

		String notifications = "UPDATE notifications_status SET equip_ip = ?, equip_name = ?,  equip_km = ? WHERE notifications_id = ? ";

		String querySATLinear = "UPDATE sat_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, linear_width = ?, number_lanes = ?, " 
				+ "dir_lane1 = ?, dir_lane2 = ?, dir_lane3 = ?, dir_lane4 = ?, dir_lane5 = ?, dir_lane6 = ?, dir_lane7 = ?, dir_lane8 = ? " 
				+ " WHERE equip_id = ? ";

		String querySATMap = "UPDATE sat_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, map_width = ?, number_lanes = ?, " 
				+ "dir_lane1 = ?, dir_lane2 = ?, dir_lane3 = ?, dir_lane4 = ?, dir_lane5 = ?, dir_lane6 = ?, dir_lane7 = ?, dir_lane8 = ? " 
				+ " WHERE equip_id = ? ";

		// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

		String VWquerySATLinear = "UPDATE sat_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, vw_linear_width = ?, number_lanes = ?, " 
				+ "dir_lane1 = ?, dir_lane2 = ?, dir_lane3 = ?, dir_lane4 = ?, dir_lane5 = ?, dir_lane6 = ?, dir_lane7 = ?, dir_lane8 = ? " 
				+ " WHERE equip_id = ? ";

		String VWquerySATMap = "UPDATE sat_equipment SET name = ?, equip_ip = ?, city = ?, road = ?, km = ?, vw_map_width = ?, number_lanes = ?, " 
				+ "dir_lane1 = ?, dir_lane2 = ?, dir_lane3 = ?, dir_lane4 = ?, dir_lane5 = ?, dir_lane6 = ?, dir_lane7 = ?, dir_lane8 = ? " 
				+ " WHERE equip_id = ? ";

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			//VIDEO WALL SWITCH
			if(permission != 9) {

				queryLinear = querySATLinear;
				queryMap = querySATMap;			

			}else { 

				queryLinear = VWquerySATLinear;
				queryMap = VWquerySATMap;		

			}				

			if(updateView.equals("linear"))
				ps = conn.prepareStatement(queryLinear);

			else ps = conn.prepareStatement(queryMap);

			ps.setString(1, sat.getNome()); 
			ps.setString(2, sat.getEquip_ip());
			ps.setString(3, sat.getCidade());
			ps.setString(4, sat.getEstrada());
			ps.setString(5, sat.getKm());			
			ps.setInt(6, sat.getMapWidth());				 
			ps.setInt(7, sat.getNumFaixas());
			ps.setString(8, sat.getFaixa1());  
			ps.setString(9, sat.getFaixa2()); 		  
			ps.setString(10, sat.getFaixa3()); 
			ps.setString(11, sat.getFaixa4()); 
			ps.setString(12, sat.getFaixa5()); 
			ps.setString(13, sat.getFaixa6()); 
			ps.setString(14, sat.getFaixa7()); 
			ps.setString(15, sat.getFaixa8()); 	
			ps.setInt(16, sat.getEquip_id());

			int res = ps.executeUpdate();

			if (res > 0) {

				ps = conn.prepareStatement(notificationSafety);

				ps.setInt(1, sat.getEquip_id());	
				ps.setString(2, ModulesEnum.SAT.getModule());

				rs = ps.executeQuery();

				if(rs.isBeforeFirst()) {
					while(rs.next()) {

						id = rs.getInt(1);	

					}
				}

				ps = conn.prepareStatement(notifications);

				ps.setString(1, sat.getEquip_ip());
				ps.setString(2, sat.getNome()); 
				ps.setString(3, sat.getKm());						
				ps.setInt(4, id);	

				int res2 = ps.executeUpdate();

				if(res2 > 0)
					updated = true;						 

			}          	  


		}catch(SQLException sqle) {

			sqle.printStackTrace();
		}
		finally { //Close Connection

			ConnectionFactory.closeConnection(conn, ps);

		}

		return updated;			    

	}

	// --------------------------------------------------------------------------------------------------------------


	/**
	 * Método para atualizar um equipamento do tipo PMV
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param dms - Objeto do tipo PMV
	 * @param table - Table id	
	 * @param view - Mapa a ser atualizado 
	 * @return boolean - Verdairo ou falso
	 * @throws Exception
	 */

	public boolean EquipDMSUpdateMap(DMS dms, String table, String updateView, int permission) throws Exception {    

		boolean updated = false;
		int id = 0;

		String queryLinear = "";
		String queryMap = "";

		String notifications = "UPDATE notifications_status SET equip_ip = ?, equip_name = ?,  equip_km = ? WHERE notifications_id = ? ";

		String notificationSafety = "SELECT notifications_id FROM notifications_status WHERE equip_id = ? and equip_type = ?";

		String queryDMSLinear = "UPDATE pmv_equipment SET ip_equip = ?, driver = ?, name = ?, city = ?, road = ?, km = ?, linear_width = ? " 
				+ " WHERE equip_id = ? ";

		String queryDMSMap = "UPDATE pmv_equipment SET ip_equip = ?, driver = ?, name = ?, city = ?, road = ?, km = ?, map_width = ? " 
				+ " WHERE equip_id = ? ";

		// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

		String VWqueryDMSLinear = "UPDATE pmv_equipment SET ip_equip = ?, driver = ?, name = ?, city = ?, road = ?, km = ?, vw_linear_width = ? " 
				+ " WHERE equip_id = ? ";

		String VWqueryDMSMap = "UPDATE pmv_equipment SET ip_equip = ?, driver = ?, name = ?, city = ?, road = ?, km = ?, vw_map_width = ? " 
				+ " WHERE equip_id = ? ";

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			//VIDEO WALL SWITCH
			if(permission != 9) {

				queryLinear = queryDMSLinear;
				queryMap = queryDMSMap;			

			}else { 

				queryLinear = VWqueryDMSLinear;
				queryMap = VWqueryDMSMap;		

			}			

			if(updateView.equals("linear"))
				ps = conn.prepareStatement(queryLinear);

			else ps = conn.prepareStatement(queryMap);

			ps.setString(1, dms.getDms_ip());
			ps.setInt(2, dms.getDms_type());
			ps.setString(3, dms.getNome());
			ps.setString(4, dms.getCidade());
			ps.setString(5, dms.getEstrada());
			ps.setString(6, dms.getKm());									 
			ps.setInt(7, dms.getMapWidth());	 
			ps.setInt(8, dms.getEquip_id());

			int res = ps.executeUpdate();

			if (res > 0) {

				ps = conn.prepareStatement(notificationSafety);

				ps.setInt(1, dms.getEquip_id());	
				ps.setString(2, ModulesEnum.PMV.getModule());

				rs = ps.executeQuery();

				if(rs.isBeforeFirst()) {
					while(rs.next()) {

						id = rs.getInt(1);	

					}
				}

				ps = conn.prepareStatement(notifications);

				ps.setString(1, dms.getEquip_ip());
				ps.setString(2, dms.getNome()); 
				ps.setString(3, dms.getKm());						
				ps.setInt(4, id);	

				int res2 = ps.executeUpdate();

				if(res2 > 0)
					updated = true;						 

			}          	     


		}catch(SQLException sqle) {

			sqle.printStackTrace();
		}
		finally { //Close Connection

			ConnectionFactory.closeConnection(conn, ps);

		}			    

		return updated;
	}

	// --------------------------------------------------------------------------------------------------------------

	/**
	 * Método para localizar dados de um equipamento genérico
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param id - Equipamento ID
	 * @param table - Table id	
	 * @param interfacesview - Mapa a ser atualizado 
	 * @return Equipments - Objeto com informações genéricos
	 * @throws Exception
	 */

	public boolean EquipSOSUpdateMap(SOS sos, String table, String updateView, int permission) throws Exception {    

		boolean updated = false;

		int id = 0;

		String queryLinear = "";
		String queryMap = "";

		String notificationSafety = "SELECT notifications_id FROM notifications_status WHERE equip_id = ? and equip_type = ?";

		String notifications = "UPDATE notifications_status SET equip_ip = ?, equip_name = ?,  equip_km = ? WHERE notifications_id = ? ";

		String querySOSLinear = "UPDATE sos_equipment SET ip_equip = ?, port = ?, name = ?, city = ?, road = ?, km = ?, linear_width = ?, model = ?, master_sip = ? " 
				+ " WHERE equip_id = ? ";

		String querySOSMap = "UPDATE pmv_equipment SET ip_equip = ?, port = ?, name = ?, city = ?, road = ?, km = ?, map_width = ?, model = ?, master_sip = ? " 
				+ " WHERE equip_id = ? ";

		// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

		String VWquerySOSLinear = "UPDATE pmv_equipment SET ip_equip = ?, port = ?, name = ?, city = ?, road = ?, km = ?, vw_linear_width = ?, model = ?, master_sip = ? " 
				+ " WHERE equip_id = ? ";

		String VWquerySOSMap = "UPDATE pmv_equipment SET ip_equip = ?, port = ?, name = ?, city = ?, road = ?, km = ?, vw_map_width = ?, model = ?, master_sip = ? " 
				+ " WHERE equip_id = ? ";

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			//VIDEO WALL SWITCH
			if(permission != 9) {

				queryLinear = querySOSLinear;
				queryMap = querySOSMap;			

			}else { 

				queryLinear = VWquerySOSLinear;
				queryMap = VWquerySOSMap;		

			}			

			if(updateView.equals("linear"))
				ps = conn.prepareStatement(queryLinear);

			else ps = conn.prepareStatement(queryMap);

			ps.setString(1, sos.getEquip_ip());
			ps.setInt(2, sos.getPort());
			ps.setString(3, sos.getNome());
			ps.setString(4, sos.getCidade());
			ps.setString(5, sos.getEstrada());
			ps.setString(6, sos.getKm());									 
			ps.setInt(7, sos.getMapWidth());
			ps.setInt(8, sos.getModel());
			ps.setString(9, sos.getSip());
			ps.setInt(10, sos.getEquip_id());

			int res = ps.executeUpdate();

			if (res > 0) {

				ps = conn.prepareStatement(notificationSafety);

				ps.setInt(1, sos.getEquip_id());	
				ps.setString(2, ModulesEnum.SOS.getModule());

				rs = ps.executeQuery();

				if(rs.isBeforeFirst()) {
					while(rs.next()) {

						id = rs.getInt(1);	

					}
				}

				ps = conn.prepareStatement(notifications);

				ps.setString(1, sos.getEquip_ip());
				ps.setString(2, sos.getNome()); 
				ps.setString(3, sos.getKm());						
				ps.setInt(4, id);								

				int res2 = ps.executeUpdate();

				if(res2 > 0)
					updated = true;						 

			}          	    


		}catch(SQLException sqle) {

			sqle.printStackTrace();
		}
		finally { //Close Connection

			ConnectionFactory.closeConnection(conn, ps);

		}			    

		return updated;
	}

	// --------------------------------------------------------------------------------------------------------------

	/**
	 * Método para atualizar um equipamento do tipo SAT
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param sat - Objeto do tipo SAT
	 * @param table - Table id	
	 * @param view - Mapa a ser atualizado 
	 * @return boolean - Verdairo ou falso
	 * @throws Exception
	 */

	public boolean EquipSpeedUpdateMap(Speed speed, String table, String updateView, int permission) throws Exception {    

		boolean updated = false;
		int id = 0;

		String queryLinear = "";
		String queryMap = "";

		String notifications = "UPDATE notifications_status SET equip_ip = ?, equip_name = ?,  equip_km = ? WHERE notifications_id = ? ";


		String notificationSafety = "SELECT notifications_id FROM notifications_status WHERE equip_id = ? and equip_type = ?";


		String querySpeedLinear = "UPDATE speed_equipment SET name = ?, equip_ip_indicator = ?, equip_ip_radar = ?, city = ?, road = ?, km = ?, linear_width = ? " 
				+ " WHERE equip_id = ? ";

		String querySpeedMap = "UPDATE speed_equipment SET name = ?, equip_ip_indicator = ?, equip_ip_radar = ?, city = ?, road = ?, km = ?, map_width = ? " 
				+ " WHERE equip_id = ? ";

		// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

		String VWquerySpeedLinear = "UPDATE speed_equipment SET name = ?, equip_ip_indicator = ?, equip_ip_radar = ?, city = ?, road = ?, km = ?, vw_linear_width = ? " 
				+ " WHERE equip_id = ? ";

		String VWquerySpeedMap = "UPDATE speed_equipment SET name = ?, equip_ip_indicator = ?, equip_ip_radar = ?, city = ?, road = ?, km = ?, vw_map_width = ?  " 
				+ " WHERE equip_id = ? ";

		// ----------------------------------------------------------------------------------------------------------------------------------------------

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			//VIDEO WALL SWITCH
			if(permission != 9) {

				queryLinear = querySpeedLinear;
				queryMap = querySpeedMap;			

			}else { 

				queryLinear = VWquerySpeedLinear;
				queryMap = VWquerySpeedMap;		

			}				

			if(updateView.equals("linear"))
				ps = conn.prepareStatement(queryLinear);

			else ps = conn.prepareStatement(queryMap);

			ps.setString(1, speed.getNome()); 
			ps.setString(2, speed.getEquip_ip_indicator());
			ps.setString(3, speed.getEquip_ip_radar());
			ps.setString(4, speed.getCidade());
			ps.setString(5, speed.getEstrada());
			ps.setString(6, speed.getKm());			
			ps.setInt(7, speed.getMapWidth());			
			ps.setInt(8, speed.getEquip_id());

			int res = ps.executeUpdate();

			if (res > 0) {

				ps = conn.prepareStatement(notificationSafety);

				ps.setInt(1, speed.getEquip_id());	
				ps.setString(2, ModulesEnum.SPEED.getModule()+" I");

				rs = ps.executeQuery();

				if(rs.isBeforeFirst()) {
					while(rs.next()) {

						id = rs.getInt(1);	

					}
				}

				ps = conn.prepareStatement(notifications);

				ps.setString(1, speed.getEquip_ip_indicator());
				ps.setString(2, "I "+speed.getNome()); 
				ps.setString(3, speed.getKm());						
				ps.setInt(4, id);						

				int res2 = ps.executeUpdate();

				if(res2 > 0) {

					ps = conn.prepareStatement(notificationSafety);

					ps.setInt(1, speed.getEquip_id());	
					ps.setString(2, ModulesEnum.SPEED.getModule()+" R");

					rs = ps.executeQuery();

					if(rs.isBeforeFirst()) {
						while(rs.next()) {

							id = rs.getInt(1);	

						}
					}

					ps = conn.prepareStatement(notifications);

					ps.setString(1, speed.getEquip_ip_indicator());
					ps.setString(2, "I "+speed.getNome()); 
					ps.setString(3, speed.getKm());						
					ps.setInt(4, id);	

					int res3 = ps.executeUpdate();

					if(res3 > 0)
						updated = true; 										
				}				
			}

		}catch(SQLException sqle) {

			sqle.printStackTrace();
		}
		finally { //Close Connection

			ConnectionFactory.closeConnection(conn, ps, rs);

		}

		return updated;			    

	}

	// --------------------------------------------------------------------------------------------------------------

	public Equipments cftvCam(int id) throws Exception {
		String script = "SELECT name, equip_ip, km FROM cftv_equipment WHERE equip_id= "+id+"";
		Equipments data = new Equipments();
		//System.out.println(script);
		//DateTimeApplication dtm = new DateTimeApplication();

		try {
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(script);
			rs = ps.executeQuery();

			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					data.setNome(rs.getString(1));
					data.setEquip_ip(rs.getString(2));
					data.setKm(rs.getString(3));
				}				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}
	public Equipments EquipSearchMap(int id, String table, String interfacesView, int permission) throws Exception {    

		try {

			if(table.equals("cftv")) { // CFTV 

				String queryLinear = "";
				String queryMap = "";

				CFTV cftv = new CFTV();

				String queryCftvLinear = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, linear_width FROM cftv_equipment WHERE equip_id = ? ";

				String queryCftvMap = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, map_width FROM cftv_equipment WHERE equip_id = ? ";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWqueryCftvLinear = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, vw_linear_width FROM cftv_equipment WHERE equip_id = ? ";

				String VWqueryCftvMap = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, vw_map_width FROM cftv_equipment WHERE equip_id = ? ";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//VIDEO WALL SWITCH
				if(permission != 9) {

					queryLinear = queryCftvLinear;
					queryMap = queryCftvMap;			

				}else { 

					queryLinear = VWqueryCftvLinear;
					queryMap = VWqueryCftvMap;		

				}			

				if(interfacesView.equals("linear"))
					ps = conn.prepareStatement(queryLinear);

				else ps = conn.prepareStatement(queryMap);

				ps.setInt(1,  id);
				rs = ps.executeQuery();

				if(rs != null) {
					while(rs.next()){

						cftv.setEquip_id(rs.getInt(1));  			 			  
						cftv.setEquip_ip(rs.getString(2));  			  
						cftv.setNome(rs.getString(3));
						cftv.setCidade(rs.getString(4));
						cftv.setEstrada(rs.getString(5));
						cftv.setKm(rs.getString(6));
						cftv.setMapWidth(rs.getInt(7));

					}
				}

				return cftv;            	  

			}  

			// --------------------------------------------------------------------------------------------------------------

			if(table.equals("colas")) { // COLAS

				String queryLinear = "";
				String queryMap = "";

				Colas colas = new Colas();

				String queryColasLinear = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, linear_width FROM colas_equipment WHERE equip_id = ? ";

				String queryColasMap = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, map_width FROM colas_equipment WHERE equip_id = ? ";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWqueryColasLinear = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, vw_linear_width FROM colas_equipment WHERE equip_id = ? ";

				String VWqueryColasMap = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, vw_map_width FROM colas_equipment WHERE equip_id = ? ";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//VIDEO WALL SWITCH
				if(permission != 9) {

					queryLinear = queryColasLinear;
					queryMap = queryColasMap;			

				}else { 

					queryLinear = VWqueryColasLinear;
					queryMap = VWqueryColasMap;		

				}			

				if(interfacesView.equals("linear"))
					ps = conn.prepareStatement(queryLinear);

				else   ps = conn.prepareStatement(queryMap);

				ps.setInt(1,  id);
				rs = ps.executeQuery();

				if(rs != null) {
					while(rs.next()){

						colas.setEquip_id(rs.getInt(1));
						colas.setEquip_ip(rs.getString(2));  	
						colas.setNome(rs.getString(3));
						colas.setCidade(rs.getString(4));
						colas.setEstrada(rs.getString(5));
						colas.setKm(rs.getString(6));
						colas.setMapWidth(rs.getInt(7));

					}
				}

				return colas;            	  

			} 

			// --------------------------------------------------------------------------------------------------------------

			if(table.equals("comms")) { // COMMS

				String queryLinear = "";
				String queryMap = "";

				COMMS comms = new COMMS();

				String queryCOMMSLinear = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, linear_width FROM comms_equipment WHERE equip_id = ? ";

				String queryCOMMSMap = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, map_width FROM comms_equipment WHERE equip_id = ? ";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWqueryCOMMSLinear = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, vw_linear_width FROM comms_equipment WHERE equip_id = ? ";

				String VWqueryCOMMSMap = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, vw_map_width FROM comms_equipment WHERE equip_id = ? ";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//VIDEO WALL SWITCH
				if(permission != 9) {

					queryLinear = queryCOMMSLinear;
					queryMap = queryCOMMSMap;			

				}else { 

					queryLinear = VWqueryCOMMSLinear;
					queryMap = VWqueryCOMMSMap;		

				}			

				if(interfacesView.equals("linear"))
					ps = conn.prepareStatement(queryLinear);

				else  ps = conn.prepareStatement(queryMap);

				ps.setInt(1,  id);
				rs = ps.executeQuery();

				if(rs != null) {
					while(rs.next()){

						comms.setEquip_id(rs.getInt(1));
						comms.setEquip_ip(rs.getString(2));  	
						comms.setNome(rs.getString(3));
						comms.setCidade(rs.getString(4));
						comms.setEstrada(rs.getString(5));
						comms.setKm(rs.getString(6));
						comms.setMapWidth(rs.getInt(7));  			         			            			  

					}
				}

				return comms;            	  

			} 

			// --------------------------------------------------------------------------------------------------------------

			if(table.equals("dai")) { // DAI

				String queryLinear = "";
				String queryMap = "";

				DAI dai = new DAI();

				String queryDAILinear = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, linear_width FROM dai_equipment WHERE equip_id = ? ";

				String queryDAIMap = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, map_width FROM dai_equipment WHERE equip_id = ? ";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWqueryDAILinear = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, vw_linear_width FROM dai_equipment WHERE equip_id = ? ";

				String VWqueryDAIMap = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, vw_map_width FROM dai_equipment WHERE equip_id = ? ";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//VIDEO WALL SWITCH
				if(permission != 9) {

					queryLinear = queryDAILinear;
					queryMap = queryDAIMap;			

				}else { 

					queryLinear = VWqueryDAILinear;
					queryMap = VWqueryDAIMap;		

				}			

				if(interfacesView.equals("linear"))
					ps = conn.prepareStatement(queryLinear);

				else ps = conn.prepareStatement(queryMap);

				ps.setInt(1,  id);
				rs = ps.executeQuery();

				if(rs != null) {
					while(rs.next()){

						dai.setEquip_id(rs.getInt(1));
						dai.setEquip_ip(rs.getString(2));  	
						dai.setNome(rs.getString(3));
						dai.setCidade(rs.getString(4));
						dai.setEstrada(rs.getString(5));
						dai.setKm(rs.getString(6));
						dai.setMapWidth(rs.getInt(7));

					}
				}

				return dai;            	  

			}  

			// --------------------------------------------------------------------------------------------------------------

			if(table.equals("ocr")) { // OCR

				String queryLinear = "";
				String queryMap = "";

				OCR ocr = new OCR();

				String queryOCRLinear = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, linear_width FROM ocr_equipment WHERE equip_id = ? ";

				String queryOCRMap = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, map_width FROM ocr_equipment WHERE equip_id = ? ";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWqueryOCRLinear = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, vw_linear_width FROM ocr_equipment WHERE equip_id = ? ";

				String VWqueryOCRMap = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, vw_map_width FROM ocr_equipment WHERE equip_id = ? ";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//VIDEO WALL SWITCH
				if(permission != 9) {

					queryLinear = queryOCRLinear;
					queryMap = queryOCRMap;			

				}else { 

					queryLinear = VWqueryOCRLinear;
					queryMap = VWqueryOCRMap;		

				}			

				if(interfacesView.equals("linear"))
					ps = conn.prepareStatement(queryLinear);

				else ps = conn.prepareStatement(queryMap);

				ps.setInt(1,  id);
				rs = ps.executeQuery();

				if(rs != null) {
					while(rs.next()){

						ocr.setEquip_id(rs.getInt(1));
						ocr.setEquip_ip(rs.getString(2));  	
						ocr.setNome(rs.getString(3));
						ocr.setCidade(rs.getString(4));
						ocr.setEstrada(rs.getString(5));
						ocr.setKm(rs.getString(6));
						ocr.setMapWidth(rs.getInt(7)); 			        			            			  

					}
				}

				return ocr;            	  

			}  

			// --------------------------------------------------------------------------------------------------------------

			if(table.equals("mto")) { // MTO

				String queryLinear = "";
				String queryMap = "";

				MTO mto = new MTO();

				String queryMTOLinear = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, linear_width FROM mto_equipment WHERE equip_id = ? ";

				String queryMTOMap = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, map_width FROM mto_equipment WHERE equip_id = ? ";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWqueryMTOLinear = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, vw_linear_width FROM mto_equipment WHERE equip_id = ? ";

				String VWqueryMTOMap = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, vw_map_width FROM mto_equipment WHERE equip_id = ? ";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//VIDEO WALL SWITCH
				if(permission != 9) {

					queryLinear = queryMTOLinear;
					queryMap = queryMTOMap;			

				}else { 

					queryLinear = VWqueryMTOLinear;
					queryMap = VWqueryMTOMap;		

				}			

				if(interfacesView.equals("linear"))
					ps = conn.prepareStatement(queryLinear);

				else ps = conn.prepareStatement(queryMap);

				ps.setInt(1,  id);
				rs = ps.executeQuery();

				if(rs != null) {
					while(rs.next()){

						mto.setEquip_id(rs.getInt(1));
						mto.setEquip_ip(rs.getString(2));  					
						mto.setNome(rs.getString(3));
						mto.setCidade(rs.getString(4));
						mto.setEstrada(rs.getString(5));
						mto.setKm(rs.getString(6));
						mto.setMapWidth(rs.getInt(7));  		      			            			  

					}
				}

				return mto;            	  

			} 

			// --------------------------------------------------------------------------------------------------------------

			if(table.equals("sos")) { // SOS

				String queryLinear = "";
				String queryMap = "";

				SOS sos = new SOS();

				String querySOSLinear = "SELECT equip_id, IFNULL(equip_ip, ''), port, name, city, road, km, linear_width, model, master_sip FROM sos_equipment WHERE equip_id = ? ";

				String querySOSMap = "SELECT equip_id, IFNULL(equip_ip, ''), port, name, city, road, km, map_width, model, master_sip FROM sos_equipment WHERE equip_id = ? ";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWquerySOSLinear = "SELECT equip_id, IFNULL(equip_ip, ''), port, name, city, road, km, vw_linear_width, model, master_sip FROM sos_equipment WHERE equip_id = ? ";

				String VWquerySOSMap = "SELECT equip_id, IFNULL(equip_ip, ''), port, name, city, road, km, vw_map_width, model, master_sip FROM sos_equipment WHERE equip_id = ? ";


				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//VIDEO WALL SWITCH
				if(permission != 9) {

					queryLinear = querySOSLinear;
					queryMap = querySOSMap;			

				}else { 

					queryLinear = VWquerySOSLinear;
					queryMap = VWquerySOSMap;		

				}			

				if(interfacesView.equals("linear"))
					ps = conn.prepareStatement(queryLinear);

				else ps = conn.prepareStatement(queryMap);

				ps.setInt(1,  id);
				rs = ps.executeQuery();

				if(rs != null) {
					while(rs.next()){

						sos.setEquip_id(rs.getInt(1));
						sos.setEquip_ip(rs.getString(2));
						sos.setPort(rs.getInt(3));
						sos.setNome(rs.getString(4));
						sos.setCidade(rs.getString(5));
						sos.setEstrada(rs.getString(6));
						sos.setKm(rs.getString(7));
						sos.setMapWidth(rs.getInt(8));
						sos.setModel(rs.getInt(9));
						sos.setSip(rs.getString(10)); 

					}
				}

				return sos;            	  

			} 

			// --------------------------------------------------------------------------------------------------------------

			if(table.equals("sv")) { // SV

				String queryLinear = "";
				String queryMap = "";

				SV sv = new SV();

				String querySVLinear = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, linear_width FROM sv_equipment WHERE equip_id = ? ";

				String querySVMap = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, map_width FROM sv_equipment WHERE equip_id = ? ";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWquerySVLinear = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, vw_linear_width FROM sv_equipment WHERE equip_id = ? ";

				String VWquerySVMap = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, vw_map_width FROM sv_equipment WHERE equip_id = ? ";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//VIDEO WALL SWITCH
				if(permission != 9) {

					queryLinear = querySVLinear;
					queryMap = querySVMap;			

				}else { 

					queryLinear = VWquerySVLinear;
					queryMap = VWquerySVMap;		

				}			

				if(interfacesView.equals("linear"))
					ps = conn.prepareStatement(queryLinear);

				else ps = conn.prepareStatement(queryMap);

				ps.setInt(1,  id);
				rs = ps.executeQuery();

				if(rs != null) {
					while(rs.next()){

						sv.setEquip_id(rs.getInt(1));
						sv.setEquip_ip(rs.getString(2));  	
						sv.setNome(rs.getString(3));
						sv.setCidade(rs.getString(4));
						sv.setEstrada(rs.getString(5));
						sv.setKm(rs.getString(6));
						sv.setMapWidth(rs.getInt(7));  		      			            			  

					}
				}

				return sv;            	  

			}  			
			// --------------------------------------------------------------------------------------------------------------

			if(table.equals("wim")) { // WIM

				String queryLinear = "";
				String queryMap = "";

				WIM wim = new WIM();

				String queryWIMLinear = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, linear_width FROM wim_equipment WHERE equip_id = ? ";

				String queryWIMMap = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, map_width FROM wim_equipment WHERE equip_id = ? ";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWqueryWIMLinear = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, vw_linear_width FROM wim_equipment WHERE equip_id = ? ";

				String VWqueryWIMMap = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, vw_map_width FROM wim_equipment WHERE equip_id = ? ";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//VIDEO WALL SWITCH
				if(permission != 9) {

					queryLinear = queryWIMLinear;
					queryMap = queryWIMMap;			

				}else { 

					queryLinear = VWqueryWIMLinear;
					queryMap = VWqueryWIMMap;		

				}			

				if(interfacesView.equals("linear"))
					ps = conn.prepareStatement(queryLinear);

				else ps = conn.prepareStatement(queryMap);

				ps.setInt(1,  id);
				rs = ps.executeQuery();

				if(rs != null) {
					while(rs.next()){

						wim.setEquip_id(rs.getInt(1));
						wim.setEquip_ip(rs.getString(2));  	
						wim.setNome(rs.getString(3));
						wim.setCidade(rs.getString(4));
						wim.setEstrada(rs.getString(5));
						wim.setKm(rs.getString(6));
						wim.setMapWidth(rs.getInt(7));  			             			            			  

					}
				}

				return wim;            	  

			}  

		}catch(SQLException sqle) {

			sqle.printStackTrace();
		}
		finally { //Close Connection

			ConnectionFactory.closeConnection(conn, ps, rs);

		}

		return null;

	}

	// --------------------------------------------------------------------------------------------------------------

	/**
	 * Método para localizar dados de um equipamento SAT
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param id - Equipamento ID
	 * @param table - Table id	
	 * @param interfacesview - Mapa a ser atualizado 
	 * @return SAT - Objeto do com informações de um SAT
	 * @throws Exception
	 */

	public SAT EquipSatSearchMap(int id, String table, String interfaceView, int permission) throws Exception { 

		String queryLinear = "";
		String queryMap = "";


		SAT sat = new SAT();

		String querySATLinear = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, linear_width, number_lanes, " 
				+ "dir_lane1, dir_lane2, dir_lane3, dir_lane4, dir_lane5, dir_lane6, dir_lane7, dir_lane8 " 
				+ " FROM sat_equipment WHERE equip_id = ? ";

		String querySATMap = "SELECT equip_id, IFNULL(equip_ip, ''),  name, city, road, km, map_width, number_lanes, " 
				+ "dir_lane1, dir_lane2, dir_lane3, dir_lane4, dir_lane5, dir_lane6, dir_lane7, dir_lane8 " 
				+ " FROM sat_equipment WHERE equip_id = ? ";

		// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

		String VWquerySATLinear = "SELECT equip_id, IFNULL(equip_ip, ''), name, city, road, km, vw_linear_width, number_lanes, " 
				+ "dir_lane1, dir_lane2, dir_lane3, dir_lane4, dir_lane5, dir_lane6, dir_lane7, dir_lane8 " 
				+ " FROM sat_equipment WHERE equip_id = ? ";

		String VWquerySATMap = "SELECT equip_id, IFNULL(equip_ip, ''),  name, city, road, km, vw_map_width, number_lanes, " 
				+ "dir_lane1, dir_lane2, dir_lane3, dir_lane4, dir_lane5, dir_lane6, dir_lane7, dir_lane8 " 
				+ " FROM sat_equipment WHERE equip_id = ? ";

		conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

		//VIDEO WALL SWITCH
		if(permission != 9) {

			queryLinear = querySATLinear;
			queryMap = querySATMap;			

		}else { 

			queryLinear = VWquerySATLinear;
			queryMap = VWquerySATMap;		

		}			

		if(interfaceView.equals("linear"))
			ps = conn.prepareStatement(queryLinear);

		else  ps = conn.prepareStatement(queryMap);

		ps.setInt(1,  id);
		rs = ps.executeQuery();

		if(rs != null) {
			while(rs.next()){

				sat.setEquip_id(rs.getInt(1));
				sat.setEquip_ip(rs.getString(2));  
				sat.setNome(rs.getString(3));
				sat.setCidade(rs.getString(4));
				sat.setEstrada(rs.getString(5));
				sat.setKm(rs.getString(6));
				sat.setMapWidth(rs.getInt(7));
				sat.setNumFaixas(rs.getInt(8));

				defineDirectionNumber(sat, 1, rs.getString(9));
				defineDirectionNumber(sat, 2, rs.getString(10));
				defineDirectionNumber(sat, 3, rs.getString(11));
				defineDirectionNumber(sat, 4, rs.getString(12));
				defineDirectionNumber(sat, 5, rs.getString(13));
				defineDirectionNumber(sat, 6, rs.getString(14));
				defineDirectionNumber(sat, 7, rs.getString(15));
				defineDirectionNumber(sat, 8, rs.getString(16));	  			  


			}
		}

		return sat;            	  
	}

	// --------------------------------------------------------------------------------------------------------------

	/**
	 * Método para localizar dados de um equipamento PMV
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param id - Equipamento ID
	 * @param table - Table id	
	 * @param interfacesview - Mapa a ser atualizado 
	 * @return DMS - Objeto com informações de um PMV
	 * @throws Exception
	 */

	public DMS EquipDMSSearchMap(int id, String table, String interfaceView, int permission) throws Exception { 

		String queryLinear = "";
		String queryMap = "";

		DMS dms = new DMS();

		String queryDMSLinear = "SELECT equip_id, IFNULL(ip_equip, ''), name, city, road, km, linear_width, driver FROM pmv_equipment WHERE equip_id = ? ";

		String queryDMSMap = "SELECT equip_id, IFNULL(ip_equip, ''), name, city, road, km, map_width, driver FROM pmv_equipment WHERE equip_id = ? ";	

		// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

		String VWqueryDMSLinear = "SELECT equip_id, IFNULL(ip_equip, ''), name, city, road, km, vw_linear_width, driver FROM pmv_equipment WHERE equip_id = ? ";

		String VWqueryDMSMap = "SELECT equip_id, IFNULL(ip_equip, ''), name, city, road, km, vw_map_width, driver FROM pmv_equipment WHERE equip_id = ? ";	

		conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

		//VIDEO WALL SWITCH
		if(permission != 9) {

			queryLinear = queryDMSLinear;
			queryMap = queryDMSMap;			

		}else { 

			queryLinear = VWqueryDMSLinear;
			queryMap = VWqueryDMSMap;		

		}			

		if(interfaceView.equals("linear"))
			ps = conn.prepareStatement(queryLinear);

		else  ps = conn.prepareStatement(queryMap);

		ps.setInt(1,  id);
		rs = ps.executeQuery();

		if(rs != null) {
			while(rs.next()){

				dms.setEquip_id(rs.getInt(1));
				dms.setDms_ip(rs.getString(2));
				dms.setNome(rs.getString(3));	  			  
				dms.setCidade(rs.getString(4));
				dms.setEstrada(rs.getString(5));
				dms.setKm(rs.getString(6));
				dms.setMapWidth(rs.getInt(7));
				dms.setDms_type(rs.getInt(8));    

			}
		}

		return dms;            	  

	}

	public SOS EquipSOSSearchMap(int id, String table, String interfaceView, int permission) throws Exception { 

		String queryLinear = "";
		String queryMap = "";

		SOS sos = new SOS();

		String querySOSLinear = "SELECT equip_id, IFNULL(equip_ip, ''), port, name, city, road, km, linear_width, model, master_sip  FROM sos_equipment WHERE equip_id = ? ";

		String querySOSMap = "SELECT equip_id, IFNULL(equip_ip, ''), port, name, city, road, km, map_width, model, master_sip  FROM sos_equipment WHERE equip_id = ? ";	

		// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

		String VWquerySOSLinear = "SELECT equip_id, IFNULL(ip_equip, ''), port, name, city, road, km, vw_linear_width, model, master_sip FROM sos_equipment WHERE equip_id = ? ";

		String VWquerySOSMap = "SELECT equip_id, IFNULL(ip_equip, ''), port, name, city, road, km, vw_map_width, model, master_sip FROM sos_equipment WHERE equip_id = ? ";	

		conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

		//VIDEO WALL SWITCH
		if(permission != 9) {

			queryLinear = querySOSLinear;
			queryMap = querySOSMap;			

		}else { 

			queryLinear = VWquerySOSLinear;
			queryMap = VWquerySOSMap;		

		}			

		if(interfaceView.equals("linear"))
			ps = conn.prepareStatement(queryLinear);

		else  ps = conn.prepareStatement(queryMap);

		ps.setInt(1,  id);
		rs = ps.executeQuery();

		if(rs != null) {
			while(rs.next()){

				sos.setEquip_id(rs.getInt(1));
				sos.setEquip_ip(rs.getString(2));
				sos.setPort(rs.getInt(3));
				sos.setNome(rs.getString(4));	  			  
				sos.setCidade(rs.getString(5));
				sos.setEstrada(rs.getString(6));
				sos.setKm(rs.getString(7));
				sos.setMapWidth(rs.getInt(8)); 
				sos.setModel(rs.getInt(9));
				sos.setSip(rs.getString(10));   

			}
		}

		return sos;            	  

	}

	// --------------------------------------------------------------------------------------------------------------

	public Speed EquipSpeedSearchMap(int id, String table, String interfaceView, int permission) throws Exception { 

		String queryLinear = "";
		String queryMap = "";

		Speed speed = new Speed();

		String querySpeedLinear = "SELECT equip_id, IFNULL(equip_ip_indicator, ''), IFNULL(equip_ip_radar, ''), name, city, road, km, linear_width FROM speed_equipment WHERE equip_id = ? ";

		String querySpeedMap = "SELECT equip_id, IFNULL(equip_ip_indicator, ''), IFNULL(equip_ip_radar, ''), name, city, road, km, map_width FROM speed_equipment WHERE equip_id = ? ";	

		// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

		String VWquerySpeedLinear = "SELECT equip_id, IFNULL(equip_ip_indicator, ''), IFNULL(equip_ip_radar, ''), name, city, road, km, vw_linear_width FROM speed_equipment WHERE equip_id = ? ";

		String VWquerySpeedMap = "SELECT equip_id, IFNULL(equip_ip_indicator, ''), IFNULL(equip_ip_radar, ''), name, city, road, km, vw_map_width FROM speed_equipment WHERE equip_id = ? ";	

		conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

		//VIDEO WALL SWITCH
		if(permission != 9) {

			queryLinear = querySpeedLinear;
			queryMap = querySpeedMap;			

		}else { 

			queryLinear = VWquerySpeedLinear;
			queryMap = VWquerySpeedMap;		

		}			

		if(interfaceView.equals("linear"))
			ps = conn.prepareStatement(queryLinear);

		else  ps = conn.prepareStatement(queryMap);

		ps.setInt(1,  id);
		rs = ps.executeQuery();

		if(rs != null) {
			while(rs.next()){

				speed.setEquip_id(rs.getInt(1));
				speed.setEquip_ip_indicator(rs.getString(2));
				speed.setEquip_ip_radar(rs.getString(3));		
				speed.setNome(rs.getString(4));	  			  
				speed.setCidade(rs.getString(5));
				speed.setEstrada(rs.getString(6));
				speed.setKm(rs.getString(7));
				speed.setMapWidth(rs.getInt(8)); 


			}
		}

		return speed;            	  

	}

	// --------------------------------------------------------------------------------------------------------------



	/**
	 * Método para deleção de um equipamento
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param id - Equipamento ID
	 * @param table - Table id		
	 * @return boolean- Verdadeiro ou falso
	 * @throws Exception
	 */

	public boolean EquipDeleteMap(int id, String table) throws Exception {    

		boolean deleted = false;

		try { 

			if(table.equals("cftv")) { // CFTV

				String queryCftv = "DELETE FROM cftv_equipment WHERE equip_id = ? ";

				String queryNotification = "DELETE FROM notifications_status WHERE equip_id = ? AND equip_type = ? ";			

				//DELETE TABLE EQUIP
				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
				ps = conn.prepareStatement(queryCftv);  
				ps.setInt(1,  id);

				int rs =  ps.executeUpdate();

				if(rs > 0) {

					//DELETE NOTIFICATION
					ps = conn.prepareStatement(queryNotification);
					ps.setInt(1,  id);
					ps.setString(2, ModulesEnum.CFTV.getModule());

					int rs2 =  ps.executeUpdate();

					if(rs2 > 0) 
						deleted = true;


				}	  

			}  

			// --------------------------------------------------------------------------------------------------------------

			if(table.equals("colas")) { // COLAS

				String queryColas= "DELETE FROM colas_equipment WHERE equip_id = ?";

				String queryNotification = "DELETE FROM notifications_status WHERE equip_id = ? AND equip_type = ? ";

				//DELETE TABLE EQUIP
				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
				ps = conn.prepareStatement(queryColas);
				ps.setInt(1,  id);
				int rs =  ps.executeUpdate();

				if(rs > 0) {

					ps = conn.prepareStatement(queryNotification);
					ps.setInt(1,  id);
					ps.setString(2, ModulesEnum.COLAS.getModule());

					int rs2 =  ps.executeUpdate();

					if(rs2 > 0) 
						deleted = true;						

				}
			} 

			// --------------------------------------------------------------------------------------------------------------

			if(table.equals("comms")) { // COMMS

				String queryCOMMS= "DELETE FROM comms_equipment WHERE equip_id = ?";

				String queryNotification = "DELETE FROM notifications_status WHERE equip_id = ? AND equip_type = ? ";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//DELETE TABLE EQUIP
				ps = conn.prepareStatement(queryCOMMS);
				ps.setInt(1,  id);
				int rs =  ps.executeUpdate();

				if(rs > 0) {

					//DELETE NOTIFICATION
					ps = conn.prepareStatement(queryNotification);
					ps.setInt(1,  id);
					ps.setString(2, ModulesEnum.COMMS.getModule());

					int rs2 =  ps.executeUpdate();

					if(rs2 > 0) 
						deleted = true;
				}		

			}            	  

			// --------------------------------------------------------------------------------------------------------------

			if(table.equals("dai")) { // DAI

				String queryDAI = "DELETE FROM dai_equipment WHERE equip_id = ?";

				String queryNotification = "DELETE FROM notifications_status WHERE equip_id = ? AND equip_type = ? ";

				//DELETE TABLE EQUIP
				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
				ps = conn.prepareStatement(queryDAI);;
				ps.setInt(1,  id);
				int rs =  ps.executeUpdate();

				if(rs > 0) {

					//DELETE NOTIFICATION
					ps = conn.prepareStatement(queryNotification);
					ps.setInt(1,  id);
					ps.setString(2, ModulesEnum.DAI.getModule());

					int rs2 =  ps.executeUpdate();

					if(rs2 > 0) 
						deleted = true;
				}		

			}         

			// --------------------------------------------------------------------------------------------------------------

			if(table.equals("ocr")) { // OCR

				String queryOCR = "DELETE FROM ocr_equipment WHERE equip_id = ?";

				String queryNotification = "DELETE FROM notifications_status WHERE equip_id = ? AND equip_type = ? ";

				//DELETE TABLE EQUIP
				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
				ps = conn.prepareStatement(queryOCR);
				ps.setInt(1,  id);
				int rs =  ps.executeUpdate();

				if(rs > 0) {

					//DELETE NOTIFICATION
					ps = conn.prepareStatement(queryNotification);
					ps.setInt(1,  id);
					ps.setString(2, ModulesEnum.OCR.getModule());

					int rs2 =  ps.executeUpdate();

					if(rs2 > 0)
						deleted = true;
				}		

			}           	  

			// --------------------------------------------------------------------------------------------------------------

			if(table.equals("mto")) { // MTO

				String queryMTO = "DELETE FROM mto_equipment WHERE equip_id = ?";
				String queryNotification = "DELETE FROM notifications_status WHERE equip_id = ? AND equip_type = ? ";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//DELETE TABLE EQUIP
				ps = conn.prepareStatement(queryMTO);
				ps.setInt(1,  id);
				int rs =  ps.executeUpdate();

				if(rs > 0) {

					//DELETE NOTIFICATION
					ps = conn.prepareStatement(queryNotification);
					ps.setInt(1,  id);
					ps.setString(2, ModulesEnum.MTO.getModule());

					int rs2 =  ps.executeUpdate();

					if(rs2 > 0) 
						deleted = true;
				}		
			}           	  

			// --------------------------------------------------------------------------------------------------------------

			if(table.equals("dms")) { //

				String queryDMS= "DELETE FROM pmv_equipment WHERE equip_id = ?";
				String queryDMSActive = "DELETE FROM pmv_messages_active WHERE id_equip = ?";  
				String queryNotification = "DELETE FROM notifications_status WHERE equip_id = ? AND equip_type = ? ";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//DELETE TABLE EQUIP
				ps = conn.prepareStatement(queryDMS);
				ps.setInt(1,  id);
				int rs =  ps.executeUpdate();

				if(rs > 0) {

					//DELETE MESSAGE ACTIVE
					ps = conn.prepareStatement(queryDMSActive);
					ps.setInt(1,  id);

					int rs2 =  ps.executeUpdate();

					if(rs2 > 0) {
						//DELETE NOTIFICATION
						ps = conn.prepareStatement(queryNotification);
						ps.setInt(1,  id);
						ps.setString(2, ModulesEnum.PMV.getModule());

						int rs3 =  ps.executeUpdate();

						if(rs3 > 0)
							deleted = true;
					}
				}

			}	          	            	  


			// --------------------------------------------------------------------------------------------------------------

			if(table.equals("sat")) { // SAT

				String querySAT= "DELETE FROM sat_equipment WHERE equip_id = ?";
				String queryNotification = "DELETE FROM notifications_status WHERE equip_id = ? AND equip_type = ? ";

				//DELETE TABLE EQUIP
				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
				ps = conn.prepareStatement(querySAT);
				ps.setInt(1,  id);
				int rs =  ps.executeUpdate();

				if(rs > 0) {

					//DELETE NOTIFICATION
					ps = conn.prepareStatement(queryNotification);
					ps.setInt(1,  id);
					ps.setString(2, ModulesEnum.SAT.getModule());

					int rs2 =  ps.executeUpdate();

					if(rs2 > 0)
						deleted = true;				
				}

			}

			// --------------------------------------------------------------------------------------------------------------

			if(table.equals("sos")) { // SOS

				String querySOS= "DELETE FROM sos_equipment WHERE equip_id = ?";
				String queryNotification = "DELETE FROM notifications_status WHERE equip_id = ? AND equip_type = ? ";

				//DELETE TABLE EQUIP
				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
				ps = conn.prepareStatement(querySOS);
				ps.setInt(1,  id);
				int rs =  ps.executeUpdate();

				if(rs > 0) {

					//DELETE NOTIFICATION
					ps = conn.prepareStatement(queryNotification);
					ps.setInt(1,  id);
					ps.setString(2, ModulesEnum.SOS.getModule());

					int rs2 =  ps.executeUpdate();

					if(rs2 > 0) 
						deleted = true;				

				}           	  

			} 

			// --------------------------------------------------------------------------------------------------------------

			if(table.equals("speed")) { // SPEED

				String querySpeed= "DELETE FROM speed_equipment WHERE equip_id = ?";
				String queryNotification = "DELETE FROM notifications_status WHERE equip_id = ? AND equip_type = ? ";

				//DELETE TABLE EQUIP
				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
				ps = conn.prepareStatement(querySpeed);
				ps.setInt(1,  id);
				int rs =  ps.executeUpdate();

				if(rs > 0) {

					//DELETE NOTIFICATION
					ps = conn.prepareStatement(queryNotification);
					ps.setInt(1,  id);
					ps.setString(2, ModulesEnum.SPEED.getModule()+" R");

					int rs2 =  ps.executeUpdate();

					if(rs2 > 0) {

						//DELETE CONNECTION MONITOR
						ps = conn.prepareStatement(queryNotification);
						ps.setInt(1,  id);
						ps.setString(2, ModulesEnum.SPEED.getModule()+" I");

						int rs3 =  ps.executeUpdate();

						if(rs3 > 0) 
							deleted = true;							

					}		

				}        	  

			} 

			// --------------------------------------------------------------------------------------------------------------

			if(table.equals("sv")) { // SV

				String querySV = "DELETE FROM sv_equipment WHERE equip_id = ?";
				String queryNotification = "DELETE FROM notifications_status WHERE equip_id = ? AND equip_type = ? ";

				//DELETE TABLE EQUIP
				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
				ps = conn.prepareStatement(querySV);
				ps.setInt(1,  id);

				int rs =  ps.executeUpdate();

				if(rs > 0) {

					//DELETE NOTIFICATION
					ps = conn.prepareStatement(queryNotification);
					ps.setInt(1,  id);
					ps.setString(2, ModulesEnum.SV.getModule());

					int rs2 =  ps.executeUpdate();

					if(rs2 > 0)
						deleted = true;

				}

			}  

			// --------------------------------------------------------------------------------------------------------------

			if(table.equals("wim")) { // WIM

				String queryWIM= "DELETE FROM wim_equipment WHERE equip_id = ?";
				String queryNotification = "DELETE FROM notifications_status WHERE equip_id = ? AND equip_type = ? ";

				//DELETE TABLE EQUIP
				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
				ps = conn.prepareStatement(queryWIM);
				ps.setInt(1,  id);
				int rs =  ps.executeUpdate();

				if(rs > 0) {

					//DELETE NOTIFICATION
					ps = conn.prepareStatement(queryNotification);
					ps.setInt(1,  id);
					ps.setString(2, ModulesEnum.WIM.getModule());

					int rs2 =  ps.executeUpdate();

					if(rs2 > 0)
						deleted = true;

				}  

			} 

		}catch(SQLException sqle) {

			sqle.printStackTrace();
		}
		finally { //Close Connection

			ConnectionFactory.closeConnection(conn, ps);

		}

		return deleted;
	}

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * Método para posicionar equipamentos
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param id - Equipamento ID
	 * @param table - Table id	
	 * @param posX - Posição eixo X
	 * @param posY - Posiçaõ eixo Y
	 * @param positionView - Interface a posicinar
	 * @return boolean- Verdadeiro ou falso
	 * @throws Exception
	 */

	public boolean EquipPositionMap(int id, String table, int posX, int posY, String positionView, int permission) throws Exception {    

		boolean positioned = false;

		try {

			if(table.equals("cftv")) { // CFTV

				String queryLinear = "";
				String queryMap = "";

				String queryCftvLinear = "UPDATE cftv_equipment SET linear_posX = ?, linear_posY = ? WHERE equip_id = ? ";

				String queryCftvMap = "UPDATE cftv_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ? ";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWqueryCftvLinear = "UPDATE cftv_equipment SET vw_linear_posX = ?, vw_linear_posY = ? WHERE equip_id = ? ";

				String VWqueryCftvMap = "UPDATE cftv_equipment SET vw_map_posX = ?, vw_map_posY = ? WHERE equip_id = ? ";


				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//VIDEO WALL SWITCH
				if(permission != 9) {

					queryLinear = queryCftvLinear;
					queryMap = queryCftvMap;			

				}else { 

					queryLinear = VWqueryCftvLinear;
					queryMap = VWqueryCftvMap;		

				}	

				if(positionView.equals("linear"))
					ps = conn.prepareStatement(queryLinear);

				else ps = conn.prepareStatement(queryMap);

				ps.setInt(1,  posX);
				ps.setInt(2,  posY);
				ps.setInt(3,  id);
				int rs =  ps.executeUpdate();

				if(rs > 0)
					positioned = true;

			}   

			//--------------------------------------------------------------------------------------------------------------

			if(table.equals("colas")) { // COLAS

				String queryLinear = "";
				String queryMap = "";

				String queryColasLinear = "UPDATE colas_equipment SET linear_posX = ?, linear_posY = ? WHERE equip_id = ?";

				String queryColasMap = "UPDATE colas_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWqueryColasLinear = "UPDATE colas_equipment SET vw_linear_posX = ?, vw_linear_posY = ? WHERE equip_id = ?";

				String VWqueryColasMap = "UPDATE colas_equipment SET vw_map_posX = ?, vw_map_posY = ? WHERE equip_id = ?";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//VIDEO WALL SWITCH
				if(permission != 9) {

					queryLinear = queryColasLinear;
					queryMap = queryColasMap;			

				}else { 

					queryLinear = VWqueryColasLinear;
					queryMap = VWqueryColasMap;		

				}	

				if(positionView.equals("linear"))
					ps = conn.prepareStatement(queryLinear);

				else ps = conn.prepareStatement(queryMap);

				ps.setInt(1,  posX);
				ps.setInt(2,  posY);
				ps.setInt(3,  id);
				int rs =  ps.executeUpdate();

				if(rs > 0)
					positioned = true;            	  

			} 

			//--------------------------------------------------------------------------------------------------------------

			if(table.equals("comms")) { // COMMS 

				String queryLinear = "";
				String queryMap = "";

				String queryCOMMSLinear = "UPDATE comms_equipment SET linear_posX = ?, linear_posY = ? WHERE equip_id = ?";

				String queryCOMMSMap = "UPDATE comms_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWqueryCOMMSLinear = "UPDATE comms_equipment SET vw_linear_posX = ?, vw_linear_posY = ? WHERE equip_id = ?";

				String VWqueryCOMMSMap = "UPDATE comms_equipment SET vw_map_posX = ?, vw_map_posY = ? WHERE equip_id = ?";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//VIDEO WALL SWITCH
				if(permission != 9) {

					queryLinear = queryCOMMSLinear;
					queryMap = queryCOMMSMap;			

				}else { 

					queryLinear = VWqueryCOMMSLinear;
					queryMap = VWqueryCOMMSMap;		

				}	

				if(positionView.equals("linear"))
					ps = conn.prepareStatement(queryLinear);

				else ps = conn.prepareStatement(queryMap);

				ps.setInt(1,  posX);
				ps.setInt(2,  posY);
				ps.setInt(3,  id);
				int rs =  ps.executeUpdate();

				if(rs > 0)
					positioned = true;              	  

			} 

			//--------------------------------------------------------------------------------------------------------------

			if(table.equals("dai")) { // DAI

				String queryLinear = "";
				String queryMap = "";

				String queryDAILinear = "UPDATE dai_equipment SET linear_posX = ?, linear_posY = ? WHERE equip_id = ?";

				String queryDAIMap = "UPDATE dai_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWqueryDAILinear = "UPDATE dai_equipment SET vw_linear_posX = ?, vw_linear_posY = ? WHERE equip_id = ?";

				String VWqueryDAIMap = "UPDATE dai_equipment SET vw_map_posX = ?, vw_map_posY = ? WHERE equip_id = ?";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//VIDEO WALL SWITCH
				if(permission != 9) {

					queryLinear = queryDAILinear;
					queryMap = queryDAIMap;			

				}else { 

					queryLinear = VWqueryDAILinear;
					queryMap = VWqueryDAIMap;		

				}	

				if(positionView.equals("linear"))
					ps = conn.prepareStatement(queryLinear);

				else ps = conn.prepareStatement(queryMap);

				ps.setInt(1,  posX);
				ps.setInt(2,  posY);
				ps.setInt(3,  id);
				int rs =  ps.executeUpdate();

				if(rs > 0)
					positioned = true;           	  

			} // DAI   

			//--------------------------------------------------------------------------------------------------------------

			if(table.equals("ocr")) { // OCR

				String queryLinear = "";
				String queryMap = "";

				String queryOCRLinear = "UPDATE ocr_equipment SET linear_posX = ?, linear_posY = ? WHERE equip_id = ?";

				String queryOCRMap = "UPDATE ocr_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWqueryOCRLinear = "UPDATE ocr_equipment SET vw_linear_posX = ?, vw_linear_posY = ? WHERE equip_id = ?";

				String VWqueryOCRMap = "UPDATE ocr_equipment SET vw_map_posX = ?, vw_map_posY = ? WHERE equip_id = ?";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//VIDEO WALL SWITCH
				if(permission != 9) {

					queryLinear = queryOCRLinear;
					queryMap = queryOCRMap;			

				}else { 

					queryLinear = VWqueryOCRLinear;
					queryMap = VWqueryOCRMap;		

				}	

				if(positionView.equals("linear"))
					ps = conn.prepareStatement(queryLinear);

				else ps = conn.prepareStatement(queryMap);

				ps.setInt(1,  posX);
				ps.setInt(2,  posY);
				ps.setInt(3,  id);
				int rs =  ps.executeUpdate();

				if(rs > 0)
					positioned = true;             	  

			} 

			//--------------------------------------------------------------------------------------------------------------

			if(table.equals("mto")) { // MTO 

				String queryLinear = "";
				String queryMap = "";

				String queryMTOLinear = "UPDATE mto_equipment SET linear_posX = ?, linear_posY = ? WHERE equip_id = ?";

				String queryMTOMap = "UPDATE mto_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWqueryMTOLinear = "UPDATE mto_equipment SET vw_linear_posX = ?, vw_linear_posY = ? WHERE equip_id = ?";

				String VWqueryMTOMap = "UPDATE mto_equipment SET vw_map_posX = ?, vw_map_posY = ? WHERE equip_id = ?";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//VIDEO WALL SWITCH
				if(permission != 9) {

					queryLinear = queryMTOLinear;
					queryMap = queryMTOMap;			

				}else { 

					queryLinear = VWqueryMTOLinear;
					queryMap = VWqueryMTOMap;		

				}	

				if(positionView.equals("linear"))
					ps = conn.prepareStatement(queryLinear);

				else ps = conn.prepareStatement(queryMap);

				ps.setInt(1,  posX);
				ps.setInt(2,  posY);
				ps.setInt(3,  id);
				int rs =  ps.executeUpdate();

				if(rs > 0)
					positioned = true;


			}

			//--------------------------------------------------------------------------------------------------------------

			if(table.equals("dms")) { // PMV

				String queryLinear = "";
				String queryMap = "";

				String queryDMSLinear = "UPDATE pmv_equipment SET linear_posX = ?, linear_posY = ? WHERE equip_id = ?";

				String queryDMSMap = "UPDATE pmv_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWqueryDMSLinear = "UPDATE pmv_equipment SET vw_linear_posX = ?, vw_linear_posY = ? WHERE equip_id = ?";

				String VWqueryDMSMap = "UPDATE pmv_equipment SET vw_map_posX = ?, vw_map_posY = ? WHERE equip_id = ?";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//VIDEO WALL SWITCH
				if(permission != 9) {

					queryLinear = queryDMSLinear;
					queryMap = queryDMSMap;			

				}else { 

					queryLinear = VWqueryDMSLinear;
					queryMap = VWqueryDMSMap;		

				}	

				if(positionView.equals("linear")) 
					ps = conn.prepareStatement(queryLinear);

				else ps = conn.prepareStatement(queryMap);

				ps.setInt(1,  posX);
				ps.setInt(2,  posY);
				ps.setInt(3,  id);
				int rs =  ps.executeUpdate();

				if(rs > 0)
					positioned = true;    

			} 

			//--------------------------------------------------------------------------------------------------------------

			if(table.equals("sat")) { // SAT

				String queryLinear = "";
				String queryMap = "";

				String querySATLinear = "UPDATE sat_equipment SET linear_posX = ?, linear_posY = ? WHERE equip_id = ?";

				String querySATMap = "UPDATE sat_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWquerySATLinear = "UPDATE sat_equipment SET vw_linear_posX = ?, vw_linear_posY = ? WHERE equip_id = ?";

				String VWquerySATMap = "UPDATE sat_equipment SET vw_map_posX = ?, vw_map_posY = ? WHERE equip_id = ?";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//VIDEO WALL SWITCH
				if(permission != 9) {

					queryLinear = querySATLinear;
					queryMap = querySATMap;			

				}else { 

					queryLinear = VWquerySATLinear;
					queryMap = VWquerySATMap;		

				}	

				if(positionView.equals("linear"))
					ps = conn.prepareStatement(queryLinear);

				else ps = conn.prepareStatement(queryMap);

				ps.setInt(1,  posX);
				ps.setInt(2,  posY);
				ps.setInt(3,  id);
				int rs =  ps.executeUpdate();

				if(rs > 0)
					positioned = true; 

			} 

			//--------------------------------------------------------------------------------------------------------------

			if(table.equals("sos")) { // SOS

				String queryLinear = "";
				String queryMap = "";

				String querySOSLinear = "UPDATE sos_equipment SET linear_posX = ?, linear_posY = ? WHERE equip_id = ?";

				String querySOSMap = "UPDATE sos_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWquerySOSLinear = "UPDATE sos_equipment SET vw_linear_posX = ?, vw_linear_posY = ? WHERE equip_id = ?";

				String VWquerySOSMap = "UPDATE sos_equipment SET vw_map_posX = ?, vw_map_posY = ? WHERE equip_id = ?";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//VIDEO WALL SWITCH
				if(permission != 9) {

					queryLinear = querySOSLinear;
					queryMap = querySOSMap;			

				}else { 

					queryLinear = VWquerySOSLinear;
					queryMap = VWquerySOSMap;		

				}	

				if(positionView.equals("linear")) 
					ps = conn.prepareStatement(queryLinear);

				else ps = conn.prepareStatement(queryMap);

				ps.setInt(1,  posX);
				ps.setInt(2,  posY);
				ps.setInt(3,  id);
				int rs =  ps.executeUpdate();

				if(rs > 0)
					positioned = true;             	  

			}

			//--------------------------------------------------------------------------------------------------------------

			if(table.equals("speed")) { // SPEED Definitions

				String queryLinear = "";
				String queryMap = "";

				String querySpeedLinear = "UPDATE speed_equipment SET linear_posX = ?, linear_posY = ? WHERE equip_id = ?";

				String querySpeedMap = "UPDATE speed_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWquerySpeedLinear = "UPDATE speed_equipment SET vw_linear_posX = ?, vw_linear_posY = ? WHERE equip_id = ?";

				String VWquerySpeedMap = "UPDATE speed_equipment SET vw_map_posX = ?, vw_map_posY = ? WHERE equip_id = ?";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//VIDEO WALL SWITCH
				if(permission != 9) {

					queryLinear = querySpeedLinear;
					queryMap = querySpeedMap;			

				}else { 

					queryLinear = VWquerySpeedLinear;
					queryMap = VWquerySpeedMap;		

				}	

				if(positionView.equals("linear"))
					ps = conn.prepareStatement(queryLinear);

				else ps = conn.prepareStatement(queryMap);

				ps.setInt(1,  posX);
				ps.setInt(2,  posY);
				ps.setInt(3,  id);
				int rs =  ps.executeUpdate();

				if(rs > 0)
					positioned = true;            	  

			}  // SPEED

			//--------------------------------------------------------------------------------------------------------------

			if(table.equals("sv")) { // SV Definitions

				String queryLinear = "";
				String queryMap = "";

				String querySVLinear = "UPDATE sv_equipment SET linear_posX = ?, linear_posY = ? WHERE equip_id = ?";

				String querySVMap = "UPDATE sv_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWquerySVLinear = "UPDATE sv_equipment SET vw_linear_posX = ?, vw_linear_posY = ? WHERE equip_id = ?";

				String VWquerySVMap = "UPDATE sv_equipment SET vw_map_posX = ?, vw_map_posY = ? WHERE equip_id = ?";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//VIDEO WALL SWITCH
				if(permission != 9) {

					queryLinear = querySVLinear;
					queryMap = querySVMap;			

				}else { 

					queryLinear = VWquerySVLinear;
					queryMap = VWquerySVMap;		

				}	

				if(positionView.equals("linear"))
					ps = conn.prepareStatement(queryLinear);

				else ps = conn.prepareStatement(queryMap);

				ps.setInt(1,  posX);
				ps.setInt(2,  posY);
				ps.setInt(3,  id);
				int rs =  ps.executeUpdate();

				if(rs > 0)
					positioned = true;


			} 

			//--------------------------------------------------------------------------------------------------------------

			if(table.equals("wim")) { // WIM 

				String queryLinear = "";
				String queryMap = "";

				String queryWIMLinear = "UPDATE wim_equipment SET linear_posX = ?, linear_posY = ? WHERE equip_id = ?";

				String queryWIMMap = "UPDATE wim_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

				// VIDEO WALL CFG -----------------------------------------------------------------------------------------------------------------------------------------

				String VWqueryWIMLinear = "UPDATE wim_equipment SET vw_linear_posX = ?, vw_linear_posY = ? WHERE equip_id = ?";

				String VWqueryWIMMap = "UPDATE wim_equipment SET vw_map_posX = ?, vw_map_posY = ? WHERE equip_id = ?";

				conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				//VIDEO WALL SWITCH
				if(permission != 9) {

					queryLinear = queryWIMLinear;
					queryMap = queryWIMMap;			

				}else { 

					queryLinear = VWqueryWIMLinear;
					queryMap = VWqueryWIMMap;		

				}	

				if(positionView.equals("linear"))
					ps = conn.prepareStatement(queryLinear);

				else ps = conn.prepareStatement(queryMap);

				ps.setInt(1,  posX);
				ps.setInt(2,  posY);
				ps.setInt(3,  id);
				int rs =  ps.executeUpdate();

				if(rs > 0)
					positioned = true;            	  


			}  

		}catch(SQLException sqle) {

			sqle.printStackTrace();
		}
		finally { //Close Connection

			ConnectionFactory.closeConnection(conn, ps);

		}

		return positioned;
	}

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * Método para verificar se um equipamento existe
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0	
	 * @param id - Equipamento ID
	 * @param table - Table id	
	 * @return boolean - Verdadeiro ou falso
	 */

	public boolean checkExists(int id, String table) throws Exception {


		boolean checked = false; 

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			//CHECK
			String select = "SELECT equip_id FROM "+table+"_equipment WHERE equip_id = ?";

			ps = conn.prepareStatement(select);
			ps.setInt(1, id);

			rs = ps.executeQuery();

			if(rs.isBeforeFirst())
				checked = true;

		} 

		catch (SQLException sqle) {
			throw new Exception("Erro ao inserir dados " + sqle);        		    

		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}

		return checked;	
	}


	//--------------------------------------------------------------------------------------------------------------

	/**
	 * Método para obter o nome de um equipamento
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0	
	 * @param id - Equipamento ID
	 * @param table - Table id	
	 * @return String - Retorna o nome do equipamento
	 */

	public String equipmentName(int id, String table) throws Exception {


		String name = ""; 

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			//CHECK
			String select = "SELECT name FROM "+table+"_equipment WHERE equip_id = ?";

			ps = conn.prepareStatement(select);
			ps.setInt(1, id);

			rs = ps.executeQuery();

			if(rs.isBeforeFirst())
				while(rs.next()) {

					name = rs.getString(1);              											
				} 
		}

		catch (SQLException sqle) {
			throw new Exception("Erro ao inserir dados " + sqle);        		    

		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}

		return name;	
	}

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * Método para obter o nome de um equipamento
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0	
	 * @param id - Equipamento ID
	 * @param table - Table id	
	 * @return String - Retorna o nome do equipamento
	 */

	public List<SAT> listSATtoXLS(String[] equips) throws Exception {

		List<SAT> lista = new ArrayList<SAT>();

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			//CHECK
			String select = "SELECT s.name, c.city_name, r.road_name, s.km, s.number_lanes FROM sat_equipment s " +
					"INNER JOIN concessionaire_cities c ON c.city_id = s.city " +
					"INNER JOIN concessionaire_roads r ON r.road_id = s.road " +
					" WHERE equip_id IN(";

			String aux = "";

			for(int i = 0; i < equips.length; i++) {
				aux += equips[i];

				if(equips[i] != equips[equips.length - 1])
					aux +=", ";

			}

			aux += ")"; // CLOSE STATEMENT

			select += aux; // JOIN AUX VAR

			ps = conn.prepareStatement(select);

			rs = ps.executeQuery();

			if(rs.isBeforeFirst())
				while(rs.next()) {

					SAT sat = new SAT();

					sat.setNome(rs.getString(1));
					sat.setCidade(rs.getString(2)); 
					sat.setEstrada(rs.getString(3)); 
					sat.setKm(rs.getString(4)); 
					sat.setNumFaixas(rs.getInt(5)); 

					lista.add(sat);

				} 
		}

		catch (SQLException sqle) {
			throw new Exception("Erro ao inserir dados " + sqle);        		    

		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}

		return lista;	
	}

	//--------------------------------------------------------------------------------------------------------------

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * Método para obter o nome de um equipamento
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0	
	 * @param id - Equipamento ID
	 * @param table - Table id	
	 * @return String - Retorna o nome do equipamento
	 */

	public List<SOS> listSOStoXLS(String[] equips) throws Exception {

		List<SOS> lista = new ArrayList<SOS>();

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			//CHECK
			String select = "SELECT s.name, c.city_name, r.road_name, s.km FROM sos_equipment s " +
					"INNER JOIN concessionaire_cities c ON c.city_id = s.city " +
					"INNER JOIN concessionaire_roads r ON r.road_id = s.road " +
					" WHERE equip_id IN(";

			String aux = "";

			for(int i = 0; i < equips.length; i++) {
				aux += equips[i];

				if(equips[i] != equips[equips.length - 1])
					aux +=", ";

			}

			aux += ")"; // CLOSE STATEMENT

			select += aux; // JOIN AUX VAR

			ps = conn.prepareStatement(select);

			rs = ps.executeQuery();

			if(rs.isBeforeFirst())
				while(rs.next()) {

					SOS sos = new SOS();

					sos.setNome(rs.getString(1));
					sos.setCidade(rs.getString(2)); 
					sos.setEstrada(rs.getString(3)); 
					sos.setKm(rs.getString(4)); 							

					lista.add(sos);

				} 
		}

		catch (SQLException sqle) {
			throw new Exception("Erro ao inserir dados " + sqle);        		    

		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}

		return lista;	
	}

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * Método para definir tipo de faixas em um determinado equipamento SAT
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0	
	 * @param sat - Obejto do tipo SAT
	 * @param numberLanes - Número de faixas
	 * @param dir - Direção
	 * @return void
	 */

	public void defineDirectionNumber(SAT sat, int numberLane, String dir){

		try {

			switch(dir) {

			case "":  

				switch (numberLane) { 

				case 1: sat.setFaixa1(""); break;
				case 2: sat.setFaixa2(""); break;
				case 3: sat.setFaixa3(""); break;
				case 4: sat.setFaixa4(""); break;
				case 5: sat.setFaixa5(""); break;
				case 6: sat.setFaixa6(""); break;
				case 7: sat.setFaixa7(""); break;
				case 8: sat.setFaixa8(""); break;

				}; break;

			case "N": 

				switch (numberLane) { 

				case 1: sat.setFaixa1("1"); break;
				case 2: sat.setFaixa2("1"); break;
				case 3: sat.setFaixa3("1"); break;
				case 4: sat.setFaixa4("1"); break;
				case 5: sat.setFaixa5("1"); break;
				case 6: sat.setFaixa6("1"); break;
				case 7: sat.setFaixa7("1"); break;
				case 8: sat.setFaixa8("1"); break;

				}; break;

			case "S": 

				switch (numberLane) { 

				case 1: sat.setFaixa1("2"); break;
				case 2: sat.setFaixa2("2"); break;
				case 3: sat.setFaixa3("2"); break;
				case 4: sat.setFaixa4("2"); break;
				case 5: sat.setFaixa5("2"); break;
				case 6: sat.setFaixa6("2"); break;
				case 7: sat.setFaixa7("2"); break;
				case 8: sat.setFaixa8("2"); break;

				}; break;	

			case "L": 	

				switch (numberLane) { 

				case 1: sat.setFaixa1("3"); break;
				case 2: sat.setFaixa2("3"); break;
				case 3: sat.setFaixa3("3"); break;
				case 4: sat.setFaixa4("3"); break;
				case 5: sat.setFaixa5("3"); break;
				case 6: sat.setFaixa6("3"); break;
				case 7: sat.setFaixa7("3"); break;
				case 8: sat.setFaixa8("3"); break;

				}; break;


			case "O": 

				switch (numberLane) { 

				case 1: sat.setFaixa1("4"); break;
				case 2: sat.setFaixa2("4"); break;
				case 3: sat.setFaixa3("4"); break;
				case 4: sat.setFaixa4("4"); break;
				case 5: sat.setFaixa5("4"); break;
				case 6: sat.setFaixa6("4"); break;
				case 7: sat.setFaixa7("4"); break;
				case 8: sat.setFaixa8("4"); break;

				}; break;

			} //SWITCH END

		}catch (NullPointerException e) {
			// TODO: handle exception
		}

	} 

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * Método para obter um módulo pelo tipo
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0	
	 * @param type - Tipo de módulo
	 * @return String - Retorna o nome do módulo
	 */

	public String getModule(String type) { 

		String module = null;

		switch(type) {

		case "cftv": module = ModulesEnum.CFTV.getModule() ; break;
		case "colas": module = ModulesEnum.COLAS.getModule(); ; break;
		case"comms": module = ModulesEnum.COMMS.getModule(); ; break;
		case "dai": module = ModulesEnum.DAI.getModule(); ; break;
		case "ocr": module = ModulesEnum.OCR.getModule(); ; break;
		case "mto": module = ModulesEnum.MTO.getModule(); ; break;
		case "dms": module = ModulesEnum.PMV.getModule(); ; break;
		case "sat": module = ModulesEnum.SAT.getModule(); ; break;
		case "sos": module = ModulesEnum.SOS.getModule(); ; break;
		case "speed": module = ModulesEnum.SPEED.getModule(); ; break;
		case "sv": module = ModulesEnum.SV.getModule(); ; break;
		case "wim": module = ModulesEnum.WIM.getModule(); ; break;

		}

		return module;
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * Método para obter informações para relatórios do tipo SAT
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param equip_id - Equipamento ID	
	 * @return SAT - Informações da classe SAT
	 * @throws Exception
	 */

	public Equipments GenericInfo(String type, String equip_id) throws Exception {

		Equipments eq = new Equipments();

		try {

			//GET CONNECTION			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			String sql = "SELECT st.name, c.city_name, r.road_name, st.km FROM "+type+"_equipment st "
					+ "INNER JOIN concessionaire_cities c ON c.city_id = st.city "
					+ "INNER JOIN concessionaire_roads r ON r.road_id = st.road "
					+ "WHERE st.equip_id = '"+ equip_id + "' AND st.visible = 1";

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					eq.setNome(rs.getString(1));
					eq.setCidade(rs.getString(2));
					eq.setEstrada(rs.getString(3));
					eq.setKm(rs.getString(4));
				
				}
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps);}

		return eq;
	}	

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * Método para obter informações de um equipamento GENERIC
	 * @author Wellington 20/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param module - módulo do equipamento	
	 * @param equip_id - ID	do equipamento
	 * @return matriz com informações dos equipamentos
	 * @throws Exception 
	 */
	public String[] genericInfo(String module, String equip_id) throws Exception {

		String[] info = new String[4];

		try {

			//GET CONNECTION			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			String sql = "SELECT eq.name, c.city_name, r.road_name, eq.km FROM "+module+"_equipment eq "
					+ "INNER JOIN concessionaire_cities c ON c.city_id = eq.city "
					+ "INNER JOIN concessionaire_roads r ON r.road_id = eq.road "
					+ "WHERE eq.equip_id = '"+ equip_id + "' AND eq.visible = 1";

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if (rs != null) {
				while (rs.next()) {

					info[0] = rs.getString(1);
					info[1] = rs.getString(2);
					info[2] = rs.getString(3);
					info[3] = rs.getString(4);
					
				}
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

		return info;
	}	

	// --------------------------------------------------------------------------------------------------------------

	/**
	 * Método para obter informações de um equipamento SAT
	 * @author Wellington 20/10/2021
	 * @version 1.0
	 * @since 1.0	 
	 * @param equip_id - ID	do equipamento
	 * @return matriz com informações dos equipamentos
	 * @throws Exception 
	 */
	public String[] satInfo(String equip_id) throws Exception {

		String[] info = new String[5];

		try {

			//GET CONNECTION			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			String query = "SELECT eq.name, c.city_name, r.road_name, eq.km, eq.number_lanes FROM sat_equipment eq "
					+ "INNER JOIN concessionaire_cities c ON c.city_id = eq.city "
					+ "INNER JOIN concessionaire_roads r ON r.road_id = eq.road "
					+ "WHERE eq.equip_id = '"+ equip_id + "' AND eq.visible = 1";

			ps = conn.prepareStatement(query);			
			rs = ps.executeQuery();
			

			if (rs != null) {
				while (rs.next()) {

					info[0] = rs.getString(1);
					info[1] = rs.getString(2);
					info[2] = rs.getString(3);
					info[3] = rs.getString(4);
					info[4] = rs.getString(5);
					//info[5] = rs.getString(6);
				
					
				}
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

		return info;
	}	

	// --------------------------------------------------------------------------------------------------------------

	/**
	 * Método para obter informações de um equipamento SOS
	 * @author Wellington 20/10/2021
	 * @version 1.0
	 * @since 1.0	
	 * @param equip_id - ID	do equipamento
	 * @return matriz com informações dos equipamentos
	 * @throws Exception 
	 */
	public String[] sosInfo(String equip_id) throws Exception {

		String[] info = new String[4];

		try {

			//GET CONNECTION			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			String query = "SELECT eq.name, c.city_name, r.road_name, eq.km FROM sos_equipment eq "
					+ "INNER JOIN concessionaire_cities c ON c.city_id = eq.city "
					+ "INNER JOIN concessionaire_roads r ON r.road_id = eq.road "
					+ "WHERE eq.equip_id = '"+ equip_id + "' AND eq.visible = 1";

			ps = conn.prepareStatement(query);			
			rs = ps.executeQuery();
			
			if (rs != null) {
				while (rs.next()) {
				
					info[0] = rs.getString(1);
					info[1] = rs.getString(2);
					info[2] = rs.getString(3);
					info[3] = rs.getString(4);
					// info[4] = rs.getString(5);
					// info[5] = rs.getString(6);
					// info[6] = rs.getString(7);
					
				}
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

		return info;
	}	


	// --------------------------------------------------------------------------------------------------------------

	/**
	 * Método para obter informações de um equipamento DMS
	 * @author Wellington 20/10/2021
	 * @version 1.0
	 * @since 1.0	
	 * @param equip_id - ID	do equipamento
	 * @return matriz com informações dos equipamentos
	 * @throws Exception 
	 */
	public String[] dmsInfo(String equip_id) throws Exception {

		String[] info = new String[4];

		try {

			// GET CONNECTION			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			String query = "SELECT eq.name, c.city_name, r.road_name, eq.km FROM pmv_equipment eq "
					+ "INNER JOIN concessionaire_cities c ON c.city_id = eq.city "
					+ "INNER JOIN concessionaire_roads r ON r.road_id = eq.road "
					+ "WHERE eq.equip_id = '"+ equip_id + "' AND eq.visible = 1";

			ps = conn.prepareStatement(query);			
			rs = ps.executeQuery();
		
			if (rs != null) {
				while (rs.next()) {

					info[0] = rs.getString(1);
					info[1] = rs.getString(2);
					info[2] = rs.getString(3);
					info[3] = rs.getString(4);
					// info[4] = rs.getString(5);
					// info[5] = rs.getString(6);
					// info[6] = rs.getString(7);
					
				}
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

		return info;
	}	

	// --------------------------------------------------------------------------------------------------------------
	
	/**
	 * Método para obter o informações de diversos equipamentos do tipo GENERIC
	 * @author Wellington 20/10/2020
	 * @version 1.0
	 * @since 1.0	
	 * @param equips - matriz com o ID dos equipamentos
	 * @param module - módulo do equipamento genérico
	 * @return matriz com informações dos equipamentos
	 */
	public String[][] multiGenericInfo(String[] equips, String module) throws Exception {

		// INFO TWO-DIMENSIONAL ARRAY 
		String[][] info = new String[equips.length][4];
		
		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			// CHECK
			String select = "SELECT eq.name, c.city_name, r.road_name, eq.km, eq.number_lanes FROM "+module+"_equipment eq " +
					"INNER JOIN concessionaire_cities c ON c.city_id = eq.city " +
					"INNER JOIN concessionaire_roads r ON r.road_id = eq.road " +
					" WHERE eq.equip_id IN(";

			String aux = "";

			for(int i = 0; i < equips.length; i++) {
				aux += equips[i];

				if(equips[i] != equips[equips.length - 1])
					aux +=", ";

			}

			aux += ") AND eq.visible = 1 "; // CLOSE STATEMENT

			select += aux; // JOIN AUX VAR

			ps = conn.prepareStatement(select);

			rs = ps.executeQuery();
			
			int eqp = 0;

			if(rs.isBeforeFirst()) {
				 while(rs.next()) {
				
					info[eqp][0] = rs.getString(1);
					info[eqp][1] = rs.getString(2);
					info[eqp][2] = rs.getString(3);
					info[eqp][3] = rs.getString(4);
					
					eqp++;
					
				}
		    }

	     } catch (SQLException sqle) {
			   throw new Exception("Erro ao buscar dados " + sqle);     		    

		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return info;	
	}

	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * Método para obter o informações de diversos equipamentos do tipo SAT
	 * @author Wellington 20/10/2020
	 * @version 1.0
	 * @since 1.0	
	 * @param equips - matriz com o ID dos equipamentos
	 * @return matriz com informações dos equipamentos
	 */
	public String[][] multiSatInfo(String[] equips) throws Exception {

		// INFO TWO-DIMENSIONAL ARRAY 
		String[][] info = new String[equips.length][5];

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			//CHECK
			String select = "SELECT eq.name, c.city_name, r.road_name, eq.km, eq.number_lanes FROM sat_equipment eq " +
					"INNER JOIN concessionaire_cities c ON c.city_id = eq.city " +
					"INNER JOIN concessionaire_roads r ON r.road_id = eq.road " +
					" WHERE eq.equip_id IN(";

			String aux = "";

			for(int i = 0; i < equips.length; i++) {
				aux += equips[i];

				if(equips[i] != equips[equips.length - 1])
					aux +=", ";

			}

			aux += ") AND eq.visible = 1 "; // CLOSE STATEMENT

			select += aux; // JOIN AUX VAR
				
			ps = conn.prepareStatement(select);

			rs = ps.executeQuery();
			
			int eqp = 0;
			
			if(rs.isBeforeFirst()) {
				while(rs.next()) {

					info[eqp][0] = rs.getString(1);
					info[eqp][1] = rs.getString(2);
					info[eqp][2] = rs.getString(3);
					info[eqp][3] = rs.getString(4);
					info[eqp][4] = rs.getString(5);
					
					eqp++;

				} 
		   }

	   } catch (SQLException sqle) {
			throw new Exception("Erro ao buscar dados " + sqle);     		    

		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return info;	
	}

	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * Método para obter o informações de diversos equipamentos do tipo SOS
	 * @author Wellington 20/10/2020
	 * @version 1.0
	 * @since 1.0	
	 * @param equips - matriz com o ID dos equipamentos
	 * @return matriz com informações dos equipamentos
	 */
	public String[][] multiSosInfo(String[] equips) throws Exception {

		// INFO TWO-DIMENSIONAL ARRAY 
		String[][] info = new String[equips.length][6];

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			//CHECK
			String select = "SELECT eq.name, c.city_name, r.road_name, eq.km, eq.number_lanes FROM sos_equipment eq " +
					"INNER JOIN concessionaire_cities c ON c.city_id = eq.city " +
					"INNER JOIN concessionaire_roads r ON r.road_id = eq.road " +
					" WHERE eq.equip_id IN(";

			String aux = "";

			for(int i = 0; i < equips.length; i++) {
				aux += equips[i];

				if(equips[i] != equips[equips.length - 1])
					aux +=", ";

			}

			aux += ") AND eq.visible = 1 "; // CLOSE STATEMENT

			select += aux; // JOIN AUX VAR

			ps = conn.prepareStatement(select);

			rs = ps.executeQuery();
			
			int eqp = 0;

			if(rs.isBeforeFirst()) {
				while(rs.next()) {

					info[eqp][0] = rs.getString(1);
					info[eqp][1] = rs.getString(2);
					info[eqp][2] = rs.getString(3);
					info[eqp][3] = rs.getString(4);
					//info[eqp][4] = rs.getString(5);
					//info[eqp][5] = rs.getString(6);
					//info[eqp][6] = rs.getString(7);
					
					eqp++;

				} 
		  }

		}catch (SQLException sqle) {
			throw new Exception("Erro ao buscar dados " + sqle);     		    

		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return info;	
	}

	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * Método para obter o informações de diversos equipamentos do tipo DMS
	 * @author Wellington 20/10/2020
	 * @version 1.0
	 * @since 1.0	
	 * @param equips - matriz com o ID dos equipamentos
	 * @return matriz com informações dos equipamentos
	 */
	public String[][] multiDmsInfo(String[] equips) throws Exception {

		// INFO TWO-DIMENSIONAL ARRAY 
		String[][] info = new String[equips.length][6];		

		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			//CHECK
			String select = "SELECT eq.name, c.city_name, r.road_name, eq.km, eq.number_lanes FROM pmv_equipment eq " +
					"INNER JOIN concessionaire_cities c ON c.city_id = eq.city " +
					"INNER JOIN concessionaire_roads r ON r.road_id = eq.road " +
					" WHERE eq.equip_id IN(";

			String aux = "";

			for(int i = 0; i < equips.length; i++) {
				aux += equips[i];

				if(equips[i] != equips[equips.length - 1])
					aux +=", ";

			}

			aux += ") AND eq.visible = 1 "; // CLOSE STATEMENT

			select += aux; // JOIN AUX VAR

			ps = conn.prepareStatement(select);

			rs = ps.executeQuery();
			
			int eqp = 0;

			if(rs.isBeforeFirst()) {
				while(rs.next()) {

					info[eqp][0] = rs.getString(1);
					info[eqp][1] = rs.getString(2);
					info[eqp][2] = rs.getString(3);
					info[eqp][3] = rs.getString(4);
					//info[eqp][4] = rs.getString(5);
					//info[eqp][5] = rs.getString(6);
					//info[eqp][6] = rs.getString(7);
					
					eqp++;
				} 
		}

		}catch (SQLException sqle) {
			throw new Exception("Erro ao buscar dados " + sqle);        		    

		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return info;	
	}

	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * Método para obter o informações de diversos equipamentos do tipo DMS
	 * @author Wellington 20/10/2020
	 * @version 1.0
	 * @since 1.0	
	 * @param equips - matriz com o ID dos equipamentos
	 * @return matriz com informações dos equipamentos
	 */
	public Integer numberOfEquips(String module) throws Exception {
		
		int count = 0;
		
		try {

			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

			// CHECK
			String select = "SELECT COUNT(*) FROM "+module+"_equipment WHERE visible = 1";

			ps = conn.prepareStatement(select);

			rs = ps.executeQuery();

			if(rs.isBeforeFirst()) {
				while(rs.next()) {

				count = rs.getInt(1);

				} 
		    }

		}catch (SQLException sqle) {
			throw new Exception("Erro ao buscar dados " + sqle);        		    

		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return count;	
	}

	//--------------------------------------------------------------------------------------------------------------
		

	//	public Equipments FollowEquip(String type, String equip_id) throws Exception {

	//		Equipments eq = new Equipments();

	//		try {

	//			//GET CONNECTION			
	//			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

	//			String sql = "SELECT st.direction FROM "+type+"_equipment st "
	//			+ "WHERE st.equip_id = '"+ equip_id +"' AND st.visible = 1";


	//			ps = conn.prepareStatement(sql);
	//			rs = ps.executeQuery();

	//			if (rs != null) {
	//				while (rs.next()) {

	//					eq.setDestino(rs.getString(1));

	//				}
	//			}

	//		} catch (SQLException sqle) {
	//			sqle.printStackTrace();
	//		}finally {ConnectionFactory.closeConnection(conn, ps);}

	//		return eq;
	//	}	


} 