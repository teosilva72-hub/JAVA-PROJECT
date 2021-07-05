package br.com.tracevia.webapp.dao.global;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.tracevia.webapp.cfg.ModulesEnum;
import br.com.tracevia.webapp.dao.dms.MessagesDAO;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.cftv.CFTV;
import br.com.tracevia.webapp.model.colas.Colas;
import br.com.tracevia.webapp.model.comms.COMMS;
import br.com.tracevia.webapp.model.dai.DAI;
import br.com.tracevia.webapp.model.dms.DMS;
import br.com.tracevia.webapp.model.dms.Messages;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.Modules;
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

	/**
	 * M�todo para criado para obter uma lista dos equipamentos metereol�gicos no sistema.	 
	 * @param mod - parametro da classe Modules
	 * @return uma lista com metereol�gicos 
	 * @throws Exception
	 *  @see list
	 */
	
	public ArrayList<Equipments> listEquipmentsAvailables(Modules mod) throws Exception{
		
		ArrayList<Equipments> equips = new ArrayList<Equipments>();
		
		String query = null;
		
		if(mod.getModule().equals(ModulesEnum.CFTV.getModule()))
			query = "SELECT equip_id, name, km, map_width, map_posX, map_posY, linear_width, linear_posX, linear_posY FROM cftv_equipment WHERE visible = 1";	
		
		else if(mod.getModule().equals(ModulesEnum.COLAS.getModule()))
			query = "SELECT equip_id, name, km, map_width, map_posX, map_posY, linear_width, linear_posX, linear_posY FROM colas_equipment WHERE visible = 1";	
		
		else if(mod.getModule().equals(ModulesEnum.COMMS.getModule()))
			query = "SELECT equip_id, name, km, map_width, map_posX, map_posY, linear_width, linear_posX, linear_posY FROM comms_equipment WHERE visible = 1";	
		
		else if(mod.getModule().equals(ModulesEnum.DAI.getModule()))
			query = "SELECT equip_id, name, km, map_width, map_posX, map_posY, linear_width, linear_posX, linear_posY FROM dai_equipment WHERE visible = 1";	
		
		else if(mod.getModule().equals(ModulesEnum.OCR.getModule()))
			query = "SELECT equip_id, name, km, map_width, map_posX, map_posY, linear_width, linear_posX, linear_posY FROM ocr_equipment WHERE visible = 1";	
		
		else if(mod.getModule().equals(ModulesEnum.MTO.getModule()))
			query = "SELECT equip_id, name, km, map_width, map_posX, map_posY, linear_width, linear_posX, linear_posY FROM mto_equipment WHERE visible = 1";	
			
		else if(mod.getModule().equals(ModulesEnum.PMV.getModule()))
			query = "SELECT equip_id, name, km, map_width, map_posX, map_posY, linear_width, linear_posX, linear_posY FROM pmv_equipment WHERE visible = 1";	
					
		else if(mod.getModule().equals(ModulesEnum.SAT.getModule()))
			query = "SELECT equip_id, number_lanes, name, km, dir_lane1, dir_lane2, dir_lane3, dir_lane4, dir_lane5, dir_lane6, dir_lane7, dir_lane8, map_width, map_posX, map_posY, linear_width, linear_posX, linear_posY, service_level FROM sat_equipment WHERE visible = 1";	
		
		else if(mod.getModule().equals(ModulesEnum.SOS.getModule()))
			query = "SELECT equip_id, name, km, map_width, map_posX, map_posY, linear_width, linear_posX, linear_posY FROM sos_equipment WHERE visible = 1";	
		
		else if(mod.getModule().equals(ModulesEnum.SPEED.getModule()))
			query = "SELECT equip_id, name, km, map_width, map_posX, map_posY, linear_width, linear_posX, linear_posY FROM speed_equipment WHERE visible = 1";	
		
		else if(mod.getModule().equals(ModulesEnum.SV.getModule()))
			query = "SELECT equip_id, name, km, map_width, map_posX, map_posY, linear_width, linear_posX, linear_posY FROM sv_equipment WHERE visible = 1";	
				
		else if(mod.getModule().equals(ModulesEnum.VIDEOWALL.getModule()))
			query = "SELECT equip_id, name, km, map_width, map_posX, map_posY, linear_width, linear_posX, linear_posY FROM videowall_equipment WHERE visible = 1";	
		
		else if(mod.getModule().equals(ModulesEnum.WIM.getModule()))
			query = "SELECT equip_id, name, km, map_width, map_posX, map_posY, linear_width, linear_posX, linear_posY FROM wim_equipment WHERE visible = 1";	
		
		
		try {			
			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			//System.out.println("TESTE "+query);

			if (rs != null) {
				while (rs.next()) {			
					
				  // FOR GENERICS	
				  Equipments eq = new Equipments();
				  
				  //FOR SAT
				  SAT sat = new SAT();
								  
				  if(mod.getModule().equals(ModulesEnum.SAT.getModule())) {
				 
				  sat.setEquip_id(rs.getInt("equip_id"));
				  sat.setTable_id(mod.getModule().toLowerCase());
				  sat.setNumFaixas(rs.getInt("number_lanes"));
				  sat.setNome(rs.getString("name"));		
				  sat.setKm(rs.getString("km"));
				  sat.setFaixa1(rs.getString("dir_lane1"));
				  sat.setFaixa2(rs.getString("dir_lane2"));
				  sat.setFaixa3(rs.getString("dir_lane3"));
				  sat.setFaixa4(rs.getString("dir_lane4"));
				  sat.setFaixa5(rs.getString("dir_lane5"));
				  sat.setFaixa6(rs.getString("dir_lane6"));
				  sat.setFaixa7(rs.getString("dir_lane7"));
				  sat.setFaixa8(rs.getString("dir_lane8"));
				  sat.setMapWidth(rs.getInt("map_width"));															
				  sat.setMapPosX(rs.getInt("map_posX"));
				  sat.setMapPosY(rs.getInt("map_posY"));
				  sat.setLinearWidth(rs.getInt("linear_width"));
				  sat.setLinearPosX(rs.getInt("linear_posX"));
				  sat.setLinearPosY(rs.getInt("linear_posY"));		
				  sat.setPosicao_nivel_servico(rs.getString("service_level"));	
				  
				  equips.add(sat);
  
				  } else {
				  
				  eq.setEquip_id(rs.getInt("equip_id"));
				  eq.setTable_id(mod.getModule().toLowerCase()); 
				  eq.setNome(rs.getString("name"));		
				  eq.setKm(rs.getString("km"));	
				  eq.setMapWidth(rs.getInt("map_width"));															
				  eq.setMapPosX(rs.getInt("map_posX"));
				  eq.setMapPosY(rs.getInt("map_posY"));
				  eq.setLinearWidth(rs.getInt("linear_width"));
				  eq.setLinearPosX(rs.getInt("linear_posX"));
				  eq.setLinearPosY(rs.getInt("linear_posY"));							
				  				  
				  equips.add(eq);
				    
				}
			 }
		  }
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}
			
		  return equips;
	}
	
	 // --------------------------- //
	// --- LISTAR EQUIPAMENTOS --- //
   // --------------------------- //
	
	// ---- LINEAR INTERFACE EQUIPMENTS ---- //
	
	public ArrayList<Equipments> buildLinearEquipemnts(String modulo) throws Exception {

		ArrayList<Equipments> lista = new ArrayList<Equipments>();

		String sql = "SELECT equip_id, name, c.city_name, r.road_name, km, linear_width, " +
				   "linear_posX, linear_posY FROM "+modulo+"_equipment eq " +
				   "INNER JOIN concessionaire_cities c ON c.city_id = eq.city " +
				   "INNER JOIN concessionaire_roads r ON r.road_id = eq.road " +
				   "WHERE visible = 1 ";
		try {
			
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
					equip.setCidade(rs.getString(3));
					equip.setEstrada(rs.getString(4));
					equip.setKm(rs.getString(5));
					equip.setLinearWidth(rs.getInt(6));															
					equip.setLinearPosX(rs.getInt(7));
					equip.setLinearPosY(rs.getInt(8));	
										
					/*if(dms.getPosicao().equals("horizontal")) {
						dms.setHorizontal(true);
					}else {
						dms.setHorizontal(false);
					}						
					*/
					
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

	public void setWidthMap(String modulo, String map, int width) throws Exception {

		String sql = "UPDATE " + modulo + "_equipment SET " + map + "_width = ? WHERE equip_id > 0;";

		try {

		   conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(sql);

			ps.setInt(1, width);

			ps.executeUpdate();

		} catch (SQLException sqle) {
			throw new Exception("Erro ao inserir dados " + sqle);        		    
				
		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}


	}
	
	// ---- MAP INTERFACE EQUIPMENTS ---- //
	
	public ArrayList<Equipments> buildMapEquipments(String modulo) throws Exception {

		ArrayList<Equipments> lista = new ArrayList<Equipments>();

		String sql = "SELECT equip_id, name, c.city_name, r.road_name, km, map_width, " +
		   "map_posX, map_posY, linear_posX, linear_posY FROM "+modulo+"_equipment eq " +
		   "INNER JOIN concessionaire_cities c ON c.city_id = eq.city " +
		   "INNER JOIN concessionaire_roads r ON r.road_id = eq.city " +
		   "WHERE visible = 1 ";
				
		try {
			
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
					equip.setCidade(rs.getString(3));
					equip.setEstrada(rs.getString(4));
					equip.setKm(rs.getString(5));
					equip.setMapWidth(rs.getInt(6));		
					equip.setMapPosX(rs.getInt(7));
					equip.setMapPosY(rs.getInt(8));
					equip.setLinearPosX(rs.getInt(9));				
					equip.setLinearPosY(rs.getInt(10));					
					
					/*if(dms.getPosicao().equals("horizontal")) {
						dms.setHorizontal(true);
					}else {
						dms.setHorizontal(false);
					}						
					*/
					
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
	
	
	
	public ArrayList<Equipments> buildEquipmentsInterface(String modulo) throws Exception {

		ArrayList<Equipments> lista = new ArrayList<Equipments>();

		String sql = "SELECT equip_id, name, c.city_name, r.road_name, km, linear_width, " +
				   "linear_posX, linear_posY, map_width, map_posX, map_posY FROM "+modulo+"_equipment eq " +
				   "INNER JOIN concessionaire_cities c ON c.city_id = eq.city " +
				   "INNER JOIN concessionaire_roads r ON r.road_id = eq.road " +
				   "WHERE visible = 1 ";
				
		try {
			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(sql);
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
					
					//equip.setLinearHeight((int) (equip.getLinearWidth()*0.232)); //
					
					/*if(dms.getPosicao().equals("horizontal")) {
						dms.setHorizontal(true);
					}else {
						dms.setHorizontal(false);
					}						
					*/
					
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
	
	// ---- SAT INTERFACE EQUIPMENTS ---- //
	
	public ArrayList<SAT> buildSatEquipmentsInterface() throws Exception {

		ArrayList<SAT> lista = new ArrayList<SAT>();
		TranslationMethods translator = new TranslationMethods();
		
		String dir1 = " ", dir2 = " ", dir3 = " ", dir4 = " ", dir5 = " ", dir6 = " ", dir7= " ", dir8 = " ";
		String sentido1 = "", sentido2 = "";

		String sql = "SELECT equip_id, name, c.city_name, r.road_name, km, number_lanes, dir_lane1, dir_lane2, dir_lane3, dir_lane4, " +
				   "dir_lane5, dir_lane6, dir_lane7, dir_lane8, linear_width, linear_posX, linear_posY, map_width, map_posX, map_posY, " +
				   "service_position FROM sat_equipment eq " +
				   "INNER JOIN concessionaire_cities c ON c.city_id = eq.city " +
				   "INNER JOIN concessionaire_roads r ON r.road_id = eq.road " +
				   "WHERE visible = 1 ";
				
		try {
			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(sql);
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
	
	// ---- SAT INTERFACE EQUIPMENTS ---- //
	
	
	// ---- DMS INTERFACE EQUIPMENTS ---- //
	
	public ArrayList<DMS> buildDMSEquipmentsInterface() throws Exception {

		ArrayList<DMS> lista = new ArrayList<DMS>();
	
		String sql = "SELECT equip_id, ip_equip, driver, name, c.city_name, r.road_name, km, "
				+ "linear_width, linear_posX, linear_posY, map_width, map_posX, map_posY, id_message, id_modify, active "
				+ "FROM pmv_equipment eq " 
				+ "INNER JOIN pmv_messages_active act ON act.id_equip = eq.equip_id " 
				+ "INNER JOIN concessionaire_cities c ON c.city_id = eq.city " 
				+ "INNER JOIN concessionaire_roads r ON r.road_id = eq.road "								 
				+ "WHERE visible = 1 "
				+ "ORDER BY eq.equip_id ASC"; 
		       				
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
	
	// ---- DMS INTERFACE EQUIPMENTS ---- //
	
	
    //Equipments Options for Selection	
	public ArrayList<Equipments> EquipmentSelectOptions(String modulo) throws Exception {

		ArrayList<Equipments> lista = new ArrayList<Equipments>();

		String sql = "SELECT equip_id, name FROM "+modulo+"_equipment WHERE visible = 1";
				
		try {
							
			 //GET CONNECTION			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
						
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
	
	 // --------------------------- //
	// --- LISTAR EQUIPAMENTOS --- //
   // --------------------------- //
	
	
	   //Equipments Options for Selection	
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
	
	
	
	 //Equipments Options for Selection	
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
		
		 //Equipments Options for Selection	
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
	
		
		 //Equipments Options for Selection	
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
			
		
		/**
		 * M�todo para retornar informa��es de um determinado Equipamento
		 * @param equip_id
		 * @return object - retorna um objeto do tipo equipamento
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
	
	
	/**
	 * M�todo para retornar informa��es de um determinado Equipamento
	 * @param equip_id
	 * @return object - retorna um objeto do tipo equipamento
	 * @throws Exception
	 */
    public Equipments EquipReportInfo(String equip_id, String module) throws Exception {

	Equipments eq = new Equipments();

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
    
  
	//Lista todos os Sites independente do status
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
			
		  	// --------------------------------------------------- //
  			// ---------- SELECT FIRST LANE FROM EQUIPMENT ------- //
  			// --------------------------------------------------- //
			
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
	
          	// --------------------------------------------------- //
  			// ---------- SELECT FIRST LANE FROM EQUIPMENT ------- //
  			// --------------------------------------------------- //
              
              
              ////LOUIS

             // --------------------------------------------------- //
    	    // ------- CREATE EQUIPMENT SAT FOR MAP / REALTIME ------- //
    	   // --------------------------------------------------- //
              
              public boolean EquipSATRegisterMap(SAT equip, String table) throws Exception {
            		          		
          		boolean status = false; 
          		          		
          		try {
          		
          		conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
          		
       		    //INSERT
        		String query = "INSERT INTO "+table+"_equipment (equip_id, creation_date, creation_username, number_lanes, name, city, road, km, "
                + "dir_lane1, dir_lane2, dir_lane3, dir_lane4, dir_lane5, dir_lane6, dir_lane7, dir_lane8, "
                + "linear_width, linear_posX, linear_posY, map_width, map_posX, map_posY, visible) "
                + "values  ( ?,?,?,?,?,?,?,?,?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        		
        		
        		String queryNotification = "INSERT INTO notifications_status (notifications_id, equip_id, equip_name, equip_type, equip_km) "        							
				        + "VALUES (null, ?, ?, ?, ?)";       	       
           			           		         			          		          			          			
                    //Execute Register			
          			ps = conn.prepareStatement(query);
          			
          			ps.setInt(1, equip.getEquip_id());
          			ps.setString(2, equip.getCreation_date());
          			ps.setString(3, equip.getCreation_username());
          			ps.setInt(4, equip.getNumFaixas());
          			ps.setString(5, equip.getNome());
          			ps.setString(6, equip.getCidade());
          			ps.setString(7, equip.getEstrada());
          			ps.setString(8, equip.getKm());
          			ps.setString(9, equip.getFaixa1());	
          			ps.setString(10, equip.getFaixa2());	
          			ps.setString(11, equip.getFaixa3());	
          			ps.setString(12, equip.getFaixa4());	
          			ps.setString(13, equip.getFaixa5());	
          			ps.setString(14, equip.getFaixa6());	
          			ps.setString(15, equip.getFaixa7());	
          			ps.setString(16, equip.getFaixa8());	          			    			
          			ps.setInt(17, 100); // Linear Width
          			ps.setInt(18, 37); // Linear posX
          			ps.setInt(19, 107); // Linear posY
          			ps.setInt(20, 75); // Map Width
          			ps.setInt(21, 50); // Map posX
          			ps.setInt(22, 50); // Map posY
          			ps.setBoolean(23, true);
          			          			
          			int success = ps.executeUpdate();
          			          			
          			if(success > 0) {    
        				
        				//Execute Register			
            			ps = conn.prepareStatement(queryNotification);
        			
        			    ps.setInt(1, equip.getEquip_id());             		
            			ps.setString(2, equip.getNome());
            			ps.setString(3, equip.getEquip_type());
            			ps.setString(4, equip.getKm());
            		      			            			                    			
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
              
              
            // --------------------------------------------------- //
      	   // ------- CREATE EQUIPMENT SAT FOR MAP / REALTIME ------- //
      	  // --------------------------------------------------- //
              
              
             // --------------------------------------------------- //
      	    // ------- CREATE DMS FOR MAP / REALTIME ------- //
      	   // --------------------------------------------------- //
                
             public boolean EquipDMSRegisterMap(DMS equip, String table) throws Exception {
              		            		
            		boolean status = false; 
            		          		
            		try {
            		
            		conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
            		
         		    //INSERT
          		String query = "INSERT INTO "+table+"_equipment (equip_id, creation_date, creation_username, ip_equip, name, city, road, km, "
                  + "linear_width, linear_posX, linear_posY, map_width, map_posX, map_posY, driver, visible) "
                  + "values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
          		
          		String queryActive = "INSERT INTO "+table+"_messages_active (id_equip, id_message, activation_username, date_time_message, id_modify, active) "
                        + "values ( ?,?,?,?,?,?)";
          		
          		          		
          		String queryNotification = "INSERT INTO notifications_status (notifications_id, equip_id, equip_name, equip_type, equip_km) "        							
				        + "VALUES (null, ?, ?, ?, ?)";       	    	                       		       
             			           		         			          		          			          			
                      //Execute Register			
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
              			ps.setInt(12, 15); // Map Width
              			ps.setInt(13, 50); // Map posX
              			ps.setInt(14, 50); // Map posY
            			ps.setInt(15,  equip.getDms_type()); //driver
            			ps.setBoolean(16, true);
            			          			
            			int success = ps.executeUpdate();
            			          			
            			if(success > 0) {
            				
            				ps = conn.prepareStatement(queryActive);
            				
            				ps.setInt(1, equip.getEquip_id());
                			ps.setInt(2, 0);            			
                			ps.setString(3, equip.getCreation_username());    
                			ps.setString(4, equip.getCreation_date());
                			ps.setInt(5, 0);   
                			ps.setInt(6, 1);
                			            			          			
                			int success2 = ps.executeUpdate();
            				
                			if(success2 > 0) {    
                				
                				//Execute Register			
                    			ps = conn.prepareStatement(queryNotification);
                			
                			    ps.setInt(1, equip.getEquip_id());             		
                    			ps.setString(2, equip.getNome());
                    			ps.setString(3, equip.getEquip_type());
                    			ps.setString(4, equip.getKm());
                    			                    			                    			
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
                
                
              // --------------------------------------------------- //
        	   // ------- CREATE DMS FOR MAP / REALTIME ------- //
        	  // --------------------------------------------------- //
              
              
              // --------------------------------------------------- //
      	    // ------- CREATE EQUIPMENT FOR MAP / REALTIME ------- //
      	   // --------------------------------------------------- //
                
                public boolean EquipRegisterMap(Equipments equip, String table) throws Exception {
              		            		
            		boolean status = false;     
            		
            		
        			String query = "INSERT INTO "+table+"_equipment (equip_id, creation_date, creation_username, name, city, road, km, "
        					+ "linear_width, linear_posX, linear_posY, map_width, map_posX, map_posY, visible)"
        					+ " values  (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        			
        			String queryMTO = "INSERT INTO "+table+"_equipment (equip_id, creation_date, creation_username, type, name, city, road, km, "
        					+ "linear_width, linear_posX, linear_posY, map_width, map_posX, map_posY, visible)"
        					+ " values  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        			
        			
        			String queryNotification = "INSERT INTO notifications_status (notifications_id, equip_id, equip_name, equip_type, equip_km) "        							
    				        + "VALUES (null, ?, ?, ?, ?)";       	
        			       	                       		            		
            		try {
            	
            			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
            			
            			if(!table.equals("mto")) {
            			
                        //Execute Register			
            			ps = conn.prepareStatement(query);
            			
            			ps.setInt(1, equip.getEquip_id());
            			ps.setString(2, equip.getCreation_date());
            			ps.setString(3, equip.getCreation_username());
            			ps.setString(4, equip.getNome());
            			ps.setString(5, equip.getCidade());
            			ps.setString(6, equip.getEstrada());
            			ps.setString(7, equip.getKm());	
            			ps.setInt(8, 30); // Linear Width
              			ps.setInt(9, 30); // Linear posX
              			ps.setInt(10, 500); // Linear posY
              			ps.setInt(11, 20); // Map Width
              			ps.setInt(12, 50); // Map posX
              			ps.setInt(13, 50); // Map posY
            			ps.setBoolean(14, true);
            			
            			int success = ps.executeUpdate();
            			
            			if(success > 0) {    
            				
            				//Execute Register			
                			ps = conn.prepareStatement(queryNotification);
            			
            			    ps.setInt(1, equip.getEquip_id());             		
                			ps.setString(2, equip.getNome());
                			ps.setString(3, equip.getEquip_type()); 
                			ps.setString(4, equip.getKm());
                			                    			
                			int successNotif = ps.executeUpdate();
                			
                			if(successNotif > 0)                				
            				     status = true;	            				  
            			    }     				 		
            			
            			} else {
            				
            				   //Execute Register			
                			ps = conn.prepareStatement(queryMTO);
                			
                			ps.setInt(1, equip.getEquip_id());
                			ps.setString(2, equip.getCreation_date());
                			ps.setString(3, equip.getCreation_username());
                			ps.setString(4, equip.getEquip_type());
                			ps.setString(5, equip.getNome());
                			ps.setString(6, equip.getCidade());
                			ps.setString(7, equip.getEstrada());
                			ps.setString(8, equip.getKm());	
                			ps.setInt(9, 30); // Linear Width
                  			ps.setInt(10, 30); // Linear posX
                  			ps.setInt(11, 500); // Linear posY
                  			ps.setInt(12, 20); // Map Width
                  			ps.setInt(13, 50); // Map posX
                  			ps.setInt(14, 50); // Map posY
                			ps.setBoolean(15, true);
                			
                			int success = ps.executeUpdate();
                			
                			if(success > 0) {    
                				
                				//Execute Register			
                    			ps = conn.prepareStatement(queryNotification);
                			
                			    ps.setInt(1, equip.getEquip_id());             		
                    			ps.setString(2, equip.getNome());
                    			ps.setString(3, ModulesEnum.MTO.getModule());
                    			ps.setString(4, equip.getKm());
                    		                    			                    			
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
                
                
              // --------------------------------------------------- //
        	   // ------- CREATE EQUIPMENT FOR MAP / REALTIME ------- //
        	  // --------------------------------------------------- //
           
           // --------------------------------------------------- //
          // ------- UPDATE EQUIPMENT FOR MAP / REALTIME ------- //
         // --------------------------------------------------- //
              
              
         public boolean EquipUpdateMap(Equipments equip, String table, String updateView) throws Exception {    
                       	 
             boolean updated = false;
        	 
        	 try { //GET SLQException           
            	 
            	 
              if(table.equals("cftv")) { // CFTV Definitions            	  
            	  
            	  String queryCftvLinear = "UPDATE cftv_equipment SET name = ?, city = ?, road = ?, km = ?, linear_width = ? WHERE equip_id = ? ";
            	  
            	  String queryCftvMap = "UPDATE cftv_equipment SET name = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ? ";
            	              	  
            	  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
            	  
            	  if(updateView.equals("linear"))
            	  ps = conn.prepareStatement(queryCftvLinear);
            	  
            	  else  ps = conn.prepareStatement(queryCftvMap);
            	 
            	  ps.setString(1,  equip.getNome());
            	  ps.setString(2,  equip.getCidade());
            	  ps.setString(3,  equip.getEstrada());
            	  ps.setString(4,  equip.getKm());
            	  ps.setInt(5,     equip.getMapWidth());            
            	  ps.setInt(6,  equip.getEquip_id());
            	  
            	  int rs = ps.executeUpdate();
            	  
            	  if (rs > 0) 
            		  updated = true;            	  
            	  
              }  // CFTV Definitions END    
              
              if(table.equals("colas")) { // COLAS Definitions

            	  String queryColasLinear = "UPDATE colas_equipment SET name = ?, city = ?, road = ?, km = ?, linear_width = ? WHERE equip_id = ? ";
            	  
            	  String queryColasMap = "UPDATE colas_equipment SET name = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ? ";

            	  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
            	  
            	  if(updateView.equals("linear"))
                	  ps = conn.prepareStatement(queryColasLinear);
                	  
                	  else  ps = conn.prepareStatement(queryColasMap);
            	  
            	  ps.setString(1,  equip.getNome());
            	  ps.setString(2,  equip.getCidade());
            	  ps.setString(3,  equip.getEstrada());
            	  ps.setString(4,  equip.getKm());
            	  ps.setInt(5,     equip.getMapWidth());
            	  ps.setInt(6,  equip.getEquip_id());

            	  int rs = ps.executeUpdate();

            	  if (rs > 0) 
            		  updated = true;

              }  // COLAS Definitions END    
              
              if(table.equals("comms")) { // COMMS Definitions

            	  String queryCOMMSLinear = "UPDATE comms_equipment SET name = ?, city = ?, road = ?, km = ?, linear_width = ? WHERE equip_id = ? ";

            	  String queryCOMMSMap = "UPDATE comms_equipment SET name = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ? ";
            	  
            	  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
            	  
            	  if(updateView.equals("linear"))
                	  ps = conn.prepareStatement(queryCOMMSLinear);
                	  
                	  else  ps = conn.prepareStatement(queryCOMMSMap);

            	  ps.setString(1,  equip.getNome());
            	  ps.setString(2,  equip.getCidade());
            	  ps.setString(3,  equip.getEstrada());
            	  ps.setString(4,  equip.getKm());
            	  ps.setInt(5,     equip.getMapWidth());
            	  ps.setInt(6,  equip.getEquip_id());
            	  
            	  int rs = ps.executeUpdate();

            	  if (rs > 0) 
            		  updated = true;           	  

              }  // COMMS Definitions END    
              
              if(table.equals("dai")) { // DAI Definitions

            	  String queryDAILinear = "UPDATE dai_equipment SET name = ?, city = ?, road = ?, km = ?, linear_width = ? WHERE equip_id = ?";
            	  
            	  String queryDAIMap = "UPDATE dai_equipment SET name = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ?";
            	  
            	  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
            	  
            	  if(updateView.equals("linear"))
                	  ps = conn.prepareStatement(queryDAILinear);
                	  
                	  else  ps = conn.prepareStatement(queryDAIMap);

            	  ps.setString(1,  equip.getNome());
            	  ps.setString(2,  equip.getCidade());
            	  ps.setString(3,  equip.getEstrada());
            	  ps.setString(4,  equip.getKm());
            	  ps.setInt(5,     equip.getMapWidth());
            	  ps.setInt(6,  equip.getEquip_id());          			            			  

            	  int rs = ps.executeUpdate();

            	  if (rs > 0) 
            		  updated = true;              	  

              }  // DAI Definitions END    
              
              if(table.equals("ocr")) { // LPR Definitions

            	  String queryLPRLinear = "UPDATE ocr_equipment SET name = ?, city = ?, road = ?, km = ?, linear_width = ? WHERE equip_id = ?";
            	  
            	  String queryLPRMap = "UPDATE ocr_equipment SET name = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ?";
            	 
            	  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
            	  
            	  if(updateView.equals("linear"))
                	  ps = conn.prepareStatement(queryLPRLinear);
                	  
                	  else  ps = conn.prepareStatement(queryLPRMap);
            	  
            	  ps.setString(1,  equip.getNome());
            	  ps.setString(2,  equip.getCidade());
            	  ps.setString(3,  equip.getEstrada());
            	  ps.setString(4,  equip.getKm());
            	  ps.setInt(5,     equip.getMapWidth());
            	  ps.setInt(6,  equip.getEquip_id());

            	  int rs = ps.executeUpdate();

            	  if (rs > 0) 
            		  updated = true;
         	  

              }  // LPR Definitions END    
              
              if(table.equals("mto")) { // MTO Definitions

            	  String queryMTOLinear = "UPDATE mto_equipment SET name = ?, city = ?, road = ?, km = ?, linear_width = ? WHERE equip_id = ? ";
            	  
            	  String queryMTOMap = "UPDATE mto_equipment SET name = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ? ";

            	  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
            	  
            	  if(updateView.equals("linear"))
                	  ps = conn.prepareStatement(queryMTOLinear);
                	  
                  else  ps = conn.prepareStatement(queryMTOMap);
            	             	  
            	  ps.setString(1,  equip.getNome());
            	  ps.setString(2,  equip.getCidade());
            	  ps.setString(3,  equip.getEstrada());
            	  ps.setString(4,  equip.getKm());
            	  ps.setInt(5,     equip.getMapWidth());
            	  ps.setInt(6,  equip.getEquip_id());        			            			  

            	  int rs = ps.executeUpdate();
            	 
            	  if (rs > 0) 
            		  updated = true;          	  

              }  // MTO Definitions END                                
                            
              if(table.equals("sos")) { // SOS Definitions

            	  String querySOSLinear = "UPDATE sos_equipment SET name = ?, city = ?, road = ?, km = ?, linear_width = ? WHERE equip_id = ? ";
            	  
            	  String querySOSMap = "UPDATE sos_equipment SET name = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ? ";

            	  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
            	  
            	  if(updateView.equals("linear"))
                	  ps = conn.prepareStatement(querySOSLinear);
                	  
                	  else  ps = conn.prepareStatement(querySOSMap);
            	  
            	  ps.setString(1,  equip.getNome());
            	  ps.setString(2,  equip.getCidade());
            	  ps.setString(3,  equip.getEstrada());
            	  ps.setString(4,  equip.getKm());
            	  ps.setInt(5,     equip.getMapWidth());
            	  ps.setInt(6,  equip.getEquip_id());          			            			  

            	  int rs = ps.executeUpdate();

            	  if (rs > 0) 
            		  updated = true;              	  

              }  // SOS Definitions END    
              
              if(table.equals("speed")) { // SPEED Definitions

            	  String querySpeedLinear = "UPDATE speed_equipment SET name = ?, city = ?, road = ?, km = ?, linear_width = ? WHERE equip_id = ? ";
            	  
            	  String querySpeedMap = "UPDATE speed_equipment SET name = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ? ";

            	  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
            	  
            	  if(updateView.equals("linear"))
                	  ps = conn.prepareStatement(querySpeedLinear);
                	  
                	  else  ps = conn.prepareStatement(querySpeedMap);
            	  
            	  ps.setString(1,  equip.getNome());
            	  ps.setString(2,  equip.getCidade());
            	  ps.setString(3,  equip.getEstrada());
            	  ps.setString(4,  equip.getKm());
            	  ps.setInt(5,     equip.getMapWidth());
            	  ps.setInt(6,  equip.getEquip_id());          			            			  

            	  int rs = ps.executeUpdate();

            	  if (rs > 0) 
            		  updated = true;             	  

              }  // SPEED Definitions END    
              
              if(table.equals("sv")) { // SV Definitions

            	  String querySVLinear = "UPDATE sv_equipment SET name = ?, city = ?, road = ?, km = ?, linear_width = ? WHERE equip_id = ? ";
            	  
            	  String querySVMap = "UPDATE sv_equipment SET name = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ? ";

            	  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
            	  
            	  if(updateView.equals("linear"))
                	  ps = conn.prepareStatement(querySVLinear);
                	  
                  else  ps = conn.prepareStatement(querySVMap);
            	             	  
            	  ps.setString(1,  equip.getNome());
            	  ps.setString(2,  equip.getCidade());
            	  ps.setString(3,  equip.getEstrada());
            	  ps.setString(4,  equip.getKm());
            	  ps.setInt(5,   equip.getMapWidth());
            	  ps.setInt(6, equip.getEquip_id());        			            			  

            	  int rs = ps.executeUpdate();
            	 
            	  if (rs > 0) 
            		  updated = true;          	  

              }  // SV Definitions END               
              
              if(table.equals("wim")) { // WIM Definitions

            	  String queryWIMLinear = "UPDATE wim_equipment SET name = ?, city = ?, road = ?, km = ?, linear_width = ? WHERE equip_id = ? ";
            	  
            	  String queryWIMMap= "UPDATE wim_equipment SET name = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ? ";

            	  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
            	  
            	  if(updateView.equals("linear"))
                	  ps = conn.prepareStatement(queryWIMLinear);
                	  
                	  else  ps = conn.prepareStatement(queryWIMMap);
            	  
            	  ps.setString(1,  equip.getNome());
            	  ps.setString(2,  equip.getCidade());
            	  ps.setString(3,  equip.getEstrada());
            	  ps.setString(4,  equip.getKm());
            	  ps.setInt(5,     equip.getMapWidth());
            	  ps.setInt(6,  equip.getEquip_id());       			            			  

            	  int rs = ps.executeUpdate();

            	  if (rs > 0) 
            		  updated = true; ;            	  

              }  // WIM Definitions END    
              
              
             }catch(SQLException sqle) {
            	 
            	 sqle.printStackTrace();
             }
             finally { //Close Connection
            	 
            	 ConnectionFactory.closeConnection(conn, ps);
				
			}
            
              return updated;
         }                 
         
		 public boolean EquipSATUpdateMap(SAT sat, String table, String updateView) throws Exception {    
			    			 
			 boolean updated = false;

			 String querySATLinear = "UPDATE sat_equipment SET name = ?, city = ?, road = ?, km = ?, linear_width = ?, number_lanes = ?, " 
					 + "dir_lane1 = ?, dir_lane2 = ?, dir_lane3 = ?, dir_lane4 = ?, dir_lane5 = ?, dir_lane6 = ?, dir_lane7 = ?, dir_lane8 = ? " 
					 + " WHERE equip_id = ? ";
			 
			 String querySATMap = "UPDATE sat_equipment SET name = ?, city = ?, road = ?, km = ?, map_width = ?, number_lanes = ?, " 
					 + "dir_lane1 = ?, dir_lane2 = ?, dir_lane3 = ?, dir_lane4 = ?, dir_lane5 = ?, dir_lane6 = ?, dir_lane7 = ?, dir_lane8 = ? " 
					 + " WHERE equip_id = ? ";

			 try {

				 conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

				 if(updateView.equals("linear"))
				 ps = conn.prepareStatement(querySATLinear);
				 
				 else ps = conn.prepareStatement(querySATMap);
				 
				 ps.setString(1, sat.getNome());
				 ps.setString(2, sat.getCidade());
				 ps.setString(3, sat.getEstrada());
				 ps.setString(4, sat.getKm());			
				 ps.setInt(5, sat.getMapWidth());				 
				 ps.setInt(6, sat.getNumFaixas());
				 ps.setString(7, sat.getFaixa1());  
				 ps.setString(8, sat.getFaixa2()); 		  
				 ps.setString(9, sat.getFaixa3()); 
				 ps.setString(10, sat.getFaixa4()); 
				 ps.setString(11, sat.getFaixa5()); 
				 ps.setString(12, sat.getFaixa6()); 
				 ps.setString(13, sat.getFaixa7()); 
				 ps.setString(14, sat.getFaixa8()); 	
				 ps.setInt(15, sat.getEquip_id());

				 int rs = ps.executeUpdate();

				 if (rs > 0) 
					 updated = true;   


			 }catch(SQLException sqle) {

				 sqle.printStackTrace();
			 }
			 finally { //Close Connection

				ConnectionFactory.closeConnection(conn, ps);

			 }
			    
			    return updated;			    
			    
			}
		
			public boolean EquipDMSUpdateMap(DMS dms, String table, String updateView) throws Exception {    
			    				 
			    boolean updated = false;
			    
			    String queryDMSLinear = "UPDATE pmv_equipment SET ip_equip = ?, driver = ?, name = ?, city = ?, road = ?, km = ?, linear_width = ? " 
						 + " WHERE equip_id = ? ";
			    
			    String queryDMSMap = "UPDATE pmv_equipment SET ip_equip = ?, driver = ?, name = ?, city = ?, road = ?, km = ?, map_width = ? " 
						 + " WHERE equip_id = ? ";

				 try {

					 conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

					 if(updateView.equals("linear"))
					 ps = conn.prepareStatement(queryDMSLinear);
					 
					 else ps = conn.prepareStatement(queryDMSMap);
					
					 ps.setString(1, dms.getDms_ip());
					 ps.setInt(2, dms.getDms_type());
					 ps.setString(3, dms.getNome());
					 ps.setString(4, dms.getCidade());
					 ps.setString(5, dms.getEstrada());
					 ps.setString(6, dms.getKm());									 
					 ps.setInt(7, dms.getMapWidth());	 
					 ps.setInt(8, dms.getEquip_id());
					            
					 int rs = ps.executeUpdate();

					 if (rs > 0) 
						 updated = true;   


				 }catch(SQLException sqle) {

					 sqle.printStackTrace();
				 }
				 finally { //Close Connection

					 ConnectionFactory.closeConnection(conn, ps);

				 }			    
			    
			    return updated;
			}
         
  
//--------------------------------------------------- //
// ------- UPDATE EQUIPMENT FOR MAP / REALTIME ------- //
// --------------------------------------------------- //
         
//---------------------------------------------------  //
// ------- SEARCH EQUIPMENT FOR MAP / REALTIME ------- //
// --------------------------------------------------- //
    
    
public Equipments EquipSearchMap(int id, String table, String interfacesView) throws Exception {    
    
//System.out.println(table);
	
   try { //GET SQLException
  	 
    
    if(table.equals("cftv")) { // CFTV Definitions
  	  
  	  CFTV cftv = new CFTV();
  	  
  	  String queryCftvLinear = "SELECT equip_id, name, city, road, km, linear_width FROM cftv_equipment WHERE equip_id = ? ";
  	  
  	  String queryCftvMap = "SELECT equip_id, name, city, road, km, map_width FROM cftv_equipment WHERE equip_id = ? ";
  	              	  
  	  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
  	
  	  if(interfacesView.equals("linear"))
  	  ps = conn.prepareStatement(queryCftvLinear);
  	  
  	  else ps = conn.prepareStatement(queryCftvMap);
  		    		  
  	  ps.setInt(1,  id);
  	  rs = ps.executeQuery();
  	  
  	  if(rs != null) {
  		  while(rs.next()){
  			  
  			  cftv.setEquip_id(rs.getInt(1));
  			  cftv.setNome(rs.getString(2));
  			  cftv.setCidade(rs.getString(3));
  			  cftv.setEstrada(rs.getString(4));
  			  cftv.setKm(rs.getString(5));
  			  cftv.setMapWidth(rs.getInt(6));
  			  
  		  }
  	  }
  	  
  	  return cftv;            	  
  	  
    }  // CFTV Definitions END    
    
    if(table.equals("colas")) { // COLAS Definitions

  	  Colas colas = new Colas();

  	 String queryColasLinear = "SELECT equip_id, name, city, road, km, linear_width FROM colas_equipment WHERE equip_id = ? ";
  	  
  	 String queryColasMap = "SELECT equip_id, name, city, road, km, map_width FROM colas_equipment WHERE equip_id = ? ";

  	 conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
  	
  	  if(interfacesView.equals("linear"))
  	  ps = conn.prepareStatement(queryColasLinear);
  	  
  	  else   ps = conn.prepareStatement(queryColasMap);
  	  
  	  ps.setInt(1,  id);
  	  rs = ps.executeQuery();

  	  if(rs != null) {
  		  while(rs.next()){

  			  colas.setEquip_id(rs.getInt(1));
  			  colas.setNome(rs.getString(2));
  			  colas.setCidade(rs.getString(3));
  			  colas.setEstrada(rs.getString(4));
  			  colas.setKm(rs.getString(5));
  			  colas.setMapWidth(rs.getInt(6));
  			   
  		  }
  	  }

  	  return colas;            	  

    }  // COLAS Definitions END    
    
    if(table.equals("comms")) { // COMMS Definitions

  	  COMMS comms = new COMMS();

  	  String queryCOMMSLinear = "SELECT equip_id, name, city, road, km, linear_width FROM comms_equipment WHERE equip_id = ? ";
  	  
	  String queryCOMMSMap = "SELECT equip_id, name, city, road, km, map_width FROM comms_equipment WHERE equip_id = ? ";

  	  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
  	
  	  if(interfacesView.equals("linear"))
  	  ps = conn.prepareStatement(queryCOMMSLinear);
  	  
   	  else  ps = conn.prepareStatement(queryCOMMSMap);
  	 
  	  ps.setInt(1,  id);
  	  rs = ps.executeQuery();

  	  if(rs != null) {
  		  while(rs.next()){

  			  comms.setEquip_id(rs.getInt(1));
  			  comms.setNome(rs.getString(2));
  			  comms.setCidade(rs.getString(3));
  			  comms.setEstrada(rs.getString(4));
  			  comms.setKm(rs.getString(5));
  			  comms.setMapWidth(rs.getInt(6));  			         			            			  

  		  }
  	  }

  	  return comms;            	  

    }  // COMMS Definitions END    
    
    if(table.equals("dai")) { // DAI Definitions

  	  DAI dai = new DAI();

  	  String queryDAILinear = "SELECT equip_id, name, city, road, km, linear_width FROM dai_equipment WHERE equip_id = ? ";
  	  
  	 String queryDAIMap = "SELECT equip_id, name, city, road, km, map_width FROM dai_equipment WHERE equip_id = ? ";

  	  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
  	
  	 if(interfacesView.equals("linear"))
  	  ps = conn.prepareStatement(queryDAILinear);
  	 
  	 else ps = conn.prepareStatement(queryDAIMap);
  	 
  	  ps.setInt(1,  id);
  	  rs = ps.executeQuery();

  	  if(rs != null) {
  		  while(rs.next()){

  			  dai.setEquip_id(rs.getInt(1));
  			  dai.setNome(rs.getString(2));
  			  dai.setCidade(rs.getString(3));
  			  dai.setEstrada(rs.getString(4));
  			  dai.setKm(rs.getString(5));
  			  dai.setMapWidth(rs.getInt(6));
  			  
  		  }
  	  }

  	  return dai;            	  

    }  // DAI Definitions END    
    
    if(table.equals("ocr")) { // LPR Definitions

  	  OCR ocr = new OCR();

  	  String queryLPRLinear = "SELECT equip_id, name, city, road, km, linear_width FROM ocr_equipment WHERE equip_id = ? ";
  	  
  	  String queryLPRMap = "SELECT equip_id, name, city, road, km, map_width FROM ocr_equipment WHERE equip_id = ? ";

  	  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
  	  
  	
   	 if(interfacesView.equals("linear"))
   	  ps = conn.prepareStatement(queryLPRLinear);
   	 
   	 else ps = conn.prepareStatement(queryLPRMap);
 
  	  ps.setInt(1,  id);
  	  rs = ps.executeQuery();

  	  if(rs != null) {
  		  while(rs.next()){

  			  ocr.setEquip_id(rs.getInt(1));
  			  ocr.setNome(rs.getString(2));
  			  ocr.setCidade(rs.getString(3));
  			  ocr.setEstrada(rs.getString(4));
  			  ocr.setKm(rs.getString(5));
  			  ocr.setMapWidth(rs.getInt(6)); 			        			            			  

  		  }
  	  }

  	  return ocr;            	  

    }  // LPR Definitions END    
    
    if(table.equals("mto")) { // MTO Definitions

  	  MTO mto = new MTO();

  	  String queryMTOLinear = "SELECT equip_id, type, name, city, road, km, linear_width FROM mto_equipment WHERE equip_id = ? ";
  	  
  	  String queryMTOMap = "SELECT equip_id, type, name, city, road, km, map_width FROM mto_equipment WHERE equip_id = ? ";

  	  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
  	  
  	  if(interfacesView.equals("linear"))
  	  ps = conn.prepareStatement(queryMTOLinear);
  	  
  	  else ps = conn.prepareStatement(queryMTOMap);
  	  
  	  ps.setInt(1,  id);
  	  rs = ps.executeQuery();

  	  if(rs != null) {
  		  while(rs.next()){

  			  mto.setEquip_id(rs.getInt(1));
  			  mto.setEquip_type(rs.getString(2));
  			  mto.setNome(rs.getString(3));
  			  mto.setCidade(rs.getString(4));
  			  mto.setEstrada(rs.getString(5));
  			  mto.setKm(rs.getString(6));
  			  mto.setMapWidth(rs.getInt(7));  		      			            			  

  		  }
  	  }

  	  return mto;            	  

    }  // MTO Definitions END  
    
          
    if(table.equals("sos")) { // SOS Definitions

  	  SOS sos = new SOS();

  	  String querySOSLinear = "SELECT equip_id, name, city, road, km, linear_width FROM sos_equipment WHERE equip_id = ? ";
  	  
  	  String querySOSMap = "SELECT equip_id, name, city, road, km, map_width FROM sos_equipment WHERE equip_id = ? ";
  	  
  	  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
  	  
  	  if(interfacesView.equals("linear"))
  	  ps = conn.prepareStatement(querySOSLinear);
  	  
  	  else ps = conn.prepareStatement(querySOSMap);
  	  
  	  ps.setInt(1,  id);
  	  rs = ps.executeQuery();

  	  if(rs != null) {
  		  while(rs.next()){

  			  sos.setEquip_id(rs.getInt(1));
  			  sos.setNome(rs.getString(2));
  			  sos.setCidade(rs.getString(3));
  			  sos.setEstrada(rs.getString(4));
  			  sos.setKm(rs.getString(5));
  			  sos.setMapWidth(rs.getInt(6));  			        			            			  

  		  }
  	  }

  	  return sos;            	  

    }  // SOS Definitions END    
    
    if(table.equals("speed")) { // SPEED Definitions

  	  Speed speed = new Speed();

  	  String querySpeedLinear = "SELECT equip_id, name, city, road, km, linear_width FROM speed_equipment WHERE equip_id = ? ";
  	  
  	  String querySpeedMap = "SELECT equip_id, name, city, road, km, map_width FROM speed_equipment WHERE equip_id = ? ";

  	  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
  	 
  	  if(interfacesView.equals("linear"))
  	  ps = conn.prepareStatement(querySpeedLinear);
  	  
  	  else ps = conn.prepareStatement(querySpeedMap);
  	  
  	  ps.setInt(1,  id);
  	  rs = ps.executeQuery();

  	  if(rs != null) {
  		  while(rs.next()){

  			  speed.setEquip_id(rs.getInt(1));
  			  speed.setNome(rs.getString(2));
  			  speed.setCidade(rs.getString(3));
  			  speed.setEstrada(rs.getString(4));
  			  speed.setKm(rs.getString(5));
  			  speed.setMapWidth(rs.getInt(6));  			       			            			  

  		  }
  	  }

  	  return speed;            	  

    }  // SPEED Definitions END    
    
    if(table.equals("sv")) { // SV Definitions

  	  SV sv = new SV();

  	  String querySVLinear = "SELECT equip_id, name, city, road, km, linear_width FROM sv_equipment WHERE equip_id = ? ";
  	  
  	  String querySVMap = "SELECT equip_id, name, city, road, km, map_width FROM sv_equipment WHERE equip_id = ? ";

  	  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
  	  
  	  if(interfacesView.equals("linear"))
  	  ps = conn.prepareStatement(querySVLinear);
  	  
  	  else ps = conn.prepareStatement(querySVMap);
  	  
  	  ps.setInt(1,  id);
  	  rs = ps.executeQuery();

  	  if(rs != null) {
  		  while(rs.next()){

  			  sv.setEquip_id(rs.getInt(1));
  			  sv.setNome(rs.getString(2));
  			  sv.setCidade(rs.getString(3));
  			  sv.setEstrada(rs.getString(4));
  			  sv.setKm(rs.getString(5));
  			  sv.setMapWidth(rs.getInt(6));  		      			            			  

  		  }
  	  }

  	  return sv;            	  

    }  // SV Definitions END  
    
    if(table.equals("wim")) { // WIM Definitions

  	  WIM wim = new WIM();

  	  String queryWIMLinear = "SELECT equip_id, name, city, road, km, linear_width FROM wim_equipment WHERE equip_id = ? ";
  	  
  	  String queryWIMMap = "SELECT equip_id, name, city, road, km, map_width FROM wim_equipment WHERE equip_id = ? ";
  	 
  	  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
  	  
  	  if(interfacesView.equals("linear"))
  	  ps = conn.prepareStatement(queryWIMLinear);
  	  
  	  else ps = conn.prepareStatement(queryWIMMap);
  	  
  	  ps.setInt(1,  id);
  	  rs = ps.executeQuery();

  	  if(rs != null) {
  		  while(rs.next()){

  			  wim.setEquip_id(rs.getInt(1));
  			  wim.setNome(rs.getString(2));
  			  wim.setCidade(rs.getString(3));
  			  wim.setEstrada(rs.getString(4));
  			  wim.setKm(rs.getString(5));
  			  wim.setMapWidth(rs.getInt(6));  			             			            			  

  		  }
  	  }

  	  return wim;            	  

    }  // WIM Definitions END    
    
    
   }catch(SQLException sqle) {
  	 
  	 sqle.printStackTrace();
   }
   finally { //Close Connection
  	 
  	 ConnectionFactory.closeConnection(conn, ps, rs);
		
	}
  
    return null;
}


     public SAT EquipSatSearchMap(int id, String table, String interfaceView) throws Exception { 
	
	  	  SAT sat = new SAT();

	  	  String querySATLinear = "SELECT equip_id, name, city, road, km, linear_width, number_lanes, " 
	  	  		+ "dir_lane1, dir_lane2, dir_lane3, dir_lane4, dir_lane5, dir_lane6, dir_lane7, dir_lane8 " 
	  	  		+ " FROM sat_equipment WHERE equip_id = ? ";
	  	  
	  	  String querySATMap = "SELECT equip_id, name, city, road, km, map_width, number_lanes, " 
		  	  		+ "dir_lane1, dir_lane2, dir_lane3, dir_lane4, dir_lane5, dir_lane6, dir_lane7, dir_lane8 " 
		  	  		+ " FROM sat_equipment WHERE equip_id = ? ";


	  	  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
	  	  
	  	 if(interfaceView.equals("linear"))
	  	  ps = conn.prepareStatement(querySATLinear);
	  	 
	  	 else  ps = conn.prepareStatement(querySATMap);
	  		 
	  	  ps.setInt(1,  id);
	  	  rs = ps.executeQuery();

	  	  if(rs != null) {
	  		  while(rs.next()){

	  			  sat.setEquip_id(rs.getInt(1));
	  			  sat.setNome(rs.getString(2));
	  			  sat.setCidade(rs.getString(3));
	  			  sat.setEstrada(rs.getString(4));
	  			  sat.setKm(rs.getString(5));
	  			  sat.setMapWidth(rs.getInt(6));
	  			  sat.setNumFaixas(rs.getInt(7));
	  			  	  			 	  			  
	  			  defineDirectionNumber(sat, 1, rs.getString(8));
	  			  defineDirectionNumber(sat, 2, rs.getString(9));
	  			  defineDirectionNumber(sat, 3, rs.getString(10));
	  			  defineDirectionNumber(sat, 4, rs.getString(11));
	  			  defineDirectionNumber(sat, 5, rs.getString(12));
	  			  defineDirectionNumber(sat, 6, rs.getString(13));
	  			  defineDirectionNumber(sat, 7, rs.getString(14));
	  			  defineDirectionNumber(sat, 8, rs.getString(15));	  			  
	  			  	  			         			            			  

	  		  }
	  	  }

	  	  return sat;            	  
	}

    public DMS EquipDMSSearchMap(int id, String table, String interfaceView) throws Exception { 
		
	  	  DMS dms = new DMS();

	  	  String queryDMSLinear = "SELECT equip_id, ip_equip, name, city, road, km, linear_width, driver FROM pmv_equipment WHERE equip_id = ? ";
	  	  
	  	  String queryDMSMap = "SELECT equip_id, ip_equip, name, city, road, km, map_width, driver FROM pmv_equipment WHERE equip_id = ? ";	  	

	  	  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
	  	  
	  	  if(interfaceView.equals("linear"))
	  	  ps = conn.prepareStatement(queryDMSLinear);
	  	  
	  	  else  ps = conn.prepareStatement(queryDMSMap);
	  	  
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


//--------------------------------------------------- //
// ------- CREATE EQUIPMENT FOR MAP / REALTIME ------- //
// --------------------------------------------------- //
  
//--------------------------------------------------- //
// ------- DELETE EQUIPMENT FOR MAP / REALTIME ------- //
// --------------------------------------------------- //


public boolean EquipDeleteMap(int id, String table) throws Exception {    
	
	boolean deleted = false;

try { //GET SLQException
 

if(table.equals("cftv")) { // CFTV Definitions
    
  String queryCftv = "DELETE FROM cftv_equipment WHERE equip_id = ? ";
  
  String queryNotification = "DELETE FROM notifications_status WHERE equip_id = ? AND equip_type = ? ";
              	  
  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
  ps = conn.prepareStatement(queryCftv);
  ps.setInt(1,  id);
  int rs =  ps.executeUpdate();
  
  if(rs > 0) {
	  
	  ps = conn.prepareStatement(queryNotification);
	  ps.setInt(1,  id);
	  ps.setString(2, ModulesEnum.CFTV.getModule());
	  
	  int rs2 =  ps.executeUpdate();
	  
	  if(rs2 > 0)
		  deleted = true;
	  
  }	  
    
}  // CFTV Definitions END    

if(table.equals("colas")) { // COLAS Definitions

  String queryColas= "DELETE FROM colas_equipment WHERE equip_id = ?";
  
  String queryNotification = "DELETE FROM notifications_status WHERE equip_id = ? AND equip_type = ? ";
  
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
}  // COLAS Definitions END    

if(table.equals("comms")) { // COMMS Definitions

  String queryCOMMS= "DELETE FROM comms_equipment WHERE equip_id = ?";
  
  String queryNotification = "DELETE FROM notifications_status WHERE equip_id = ? AND equip_type = ? ";
  
  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
  ps = conn.prepareStatement(queryCOMMS);
  ps.setInt(1,  id);
  int rs =  ps.executeUpdate();
  
  if(rs > 0) {
	  
	  ps = conn.prepareStatement(queryNotification);
	  ps.setInt(1,  id);
	  ps.setString(2, ModulesEnum.COMMS.getModule());
	  
	  int rs2 =  ps.executeUpdate();
	  
	  if(rs2 > 0)
		  deleted = true;
	  
   }             	  
 
}  // COMMS Definitions END    

if(table.equals("dai")) { // DAI Definitions

  String queryDAI = "DELETE FROM dai_equipment WHERE equip_id = ?";
  
  String queryNotification = "DELETE FROM notifications_status WHERE equip_id = ? AND equip_type = ? ";

  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
  ps = conn.prepareStatement(queryDAI);;
  ps.setInt(1,  id);
  int rs =  ps.executeUpdate();
  
 if(rs > 0) {
	  
	  ps = conn.prepareStatement(queryNotification);
	  ps.setInt(1,  id);
	  ps.setString(2, ModulesEnum.DAI.getModule());
	  
	  int rs2 =  ps.executeUpdate();
	  
	  if(rs2 > 0)
		  deleted = true;
	  
  }        	  

} // DAI Definitions END    

if(table.equals("ocr")) { // LPR Definitions

  String queryLPR= "DELETE FROM ocr_equipment WHERE equip_id = ?";
  
  String queryNotification = "DELETE FROM notifications_status WHERE equip_id = ? AND equip_type = ? ";
  
  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
  ps = conn.prepareStatement(queryLPR);
  ps.setInt(1,  id);
  int rs =  ps.executeUpdate();
  
 if(rs > 0) {
	  
	  ps = conn.prepareStatement(queryNotification);
	  ps.setInt(1,  id);
	  ps.setString(2, ModulesEnum.OCR.getModule());
	  
	  int rs2 =  ps.executeUpdate();
	  
	  if(rs2 > 0)
		  deleted = true;
	  
  }           	  

}  // LPR Definitions END    

if(table.equals("mto")) { // MTO Definitions

  String queryMTO = "DELETE FROM mto_equipment WHERE equip_id = ?";
  String queryNotification = "DELETE FROM notifications_status WHERE equip_id = ? AND equip_type = ? ";

  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
   
  ps = conn.prepareStatement(queryMTO);
  ps.setInt(1,  id);
  int rs =  ps.executeUpdate();
  
 if(rs > 0) {
	  
	  ps = conn.prepareStatement(queryNotification);
	  ps.setInt(1,  id);
	  ps.setString(2, ModulesEnum.MTO.getModule());
	  
	  int rs2 =  ps.executeUpdate();
	  
	  if(rs2 > 0)
		  deleted = true;
	  
  }           	  
            	  

}  // MTO Definitions END    


if(table.equals("dms")) { // PMV Definitions

  String queryDMS= "DELETE FROM pmv_equipment WHERE equip_id = ?";
  String queryDMSActive = "DELETE FROM pmv_messages_active WHERE id_equip = ?";  
  String queryNotification = "DELETE FROM notifications_status WHERE equip_id = ? AND equip_type = ? ";
  
  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
 
  ps = conn.prepareStatement(queryDMS);
  ps.setInt(1,  id);
  int rs =  ps.executeUpdate();
  
  if(rs > 0) {
	  
	  ps = conn.prepareStatement(queryDMSActive);
	  ps.setInt(1,  id);
	  
	  int rs2 =  ps.executeUpdate();
	  
	  if(rs2 > 0) {
		  	  
		  ps = conn.prepareStatement(queryNotification);
		  ps.setInt(1,  id);
		  ps.setString(2, ModulesEnum.PMV.getModule());
		  
		  int rs3 =  ps.executeUpdate();
		  
		  if(rs3 > 0)
			  deleted = true;
		  
	  }
  
  }	          	            	  

}  // PMV Definitions END    

if(table.equals("sat")) { // SAT Definitions

  String querySAT= "DELETE FROM sat_equipment WHERE equip_id = ?";
  String queryNotification = "DELETE FROM notifications_status WHERE equip_id = ? AND equip_type = ? ";
  
  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
  ps = conn.prepareStatement(querySAT);
  ps.setInt(1,  id);
  int rs =  ps.executeUpdate();
  
 if(rs > 0) {
	  
	  ps = conn.prepareStatement(queryNotification);
	  ps.setInt(1,  id);
	  ps.setString(2, ModulesEnum.SAT.getModule());
	  
	  int rs2 =  ps.executeUpdate();
	  
	  if(rs2 > 0)
		  deleted = true;
	  
  }
  
}  // SAT Definitions END    

if(table.equals("sos")) { // SOS Definitions

  String querySOS= "DELETE FROM sos_equipment WHERE equip_id = ?";
  String queryNotification = "DELETE FROM notifications_status WHERE equip_id = ? AND equip_type = ? ";
   
  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
  ps = conn.prepareStatement(querySOS);
  ps.setInt(1,  id);
  int rs =  ps.executeUpdate();
  
  //System.out.println(queryNotification);
  
 if(rs > 0) {
	  
	  ps = conn.prepareStatement(queryNotification);
	  ps.setInt(1,  id);
	  ps.setString(2, ModulesEnum.SOS.getModule());
		  
	  int rs2 =  ps.executeUpdate();
	  
	  if(rs2 > 0)
		  deleted = true;
	  
  }           	  

}  // SOS Definitions END    

if(table.equals("speed")) { // SPEED Definitions

  String querySpeed= "DELETE FROM speed_equipment WHERE equip_id = ?";
  String queryNotification = "DELETE FROM notifications_status WHERE equip_id = ? AND equip_type = ? ";
  
  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
  ps = conn.prepareStatement(querySpeed);
  ps.setInt(1,  id);
  int rs =  ps.executeUpdate();
  
  if(rs > 0) {
	  
	  ps = conn.prepareStatement(queryNotification);
	  ps.setInt(1,  id);
	  ps.setString(2, ModulesEnum.SPEED.getModule());
	  
	  int rs2 =  ps.executeUpdate();
	  
	  if(rs2 > 0)
		  deleted = true;
	  
  }        	  

}  // SPEED Definitions END   

if(table.equals("sv")) { // SV Definitions

	  String querySV = "DELETE FROM sv_equipment WHERE equip_id = ?";
	  String queryNotification = "DELETE FROM notifications_status WHERE equip_id = ? AND equip_type = ? ";
	  

	  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
	  ps = conn.prepareStatement(querySV);
	  ps.setInt(1,  id);
	 
	  int rs =  ps.executeUpdate();
	  
	  if(rs > 0) {
		  
		  ps = conn.prepareStatement(queryNotification);
		  ps.setInt(1,  id);
		  ps.setString(2, ModulesEnum.SV.getModule());
		  
		  int rs2 =  ps.executeUpdate();
		  
		  if(rs2 > 0)
			  deleted = true;
		  
	  }

	}  // SV Definitions END   

if(table.equals("wim")) { // WIM Definitions

  String queryWIM= "DELETE FROM wim_equipment WHERE equip_id = ?";
  String queryNotification = "DELETE FROM notifications_status WHERE equip_id = ? AND equip_type = ? ";
  

  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
  ps = conn.prepareStatement(queryWIM);
  ps.setInt(1,  id);
  int rs =  ps.executeUpdate();
  
  if(rs > 0) {
	  
	  ps = conn.prepareStatement(queryNotification);
	  ps.setInt(1,  id);
	  ps.setString(2, ModulesEnum.WIM.getModule());
	  
	  int rs2 =  ps.executeUpdate();
	  
	  if(rs2 > 0)
		  deleted = true;
	  
  }           	  
            	  

}  // WIM Definitions END    


}catch(SQLException sqle) {
 
 sqle.printStackTrace();
}
finally { //Close Connection
 
 ConnectionFactory.closeConnection(conn, ps);
	
}

return deleted;
}

//--------------------------------------------------- //
//------- DELETE EQUIPMENT FOR MAP / REALTIME ------- //
//--------------------------------------------------- //

//---------------------------------------------------------- //
//------- POSITION FOR EQUIPMENT FOR MAP / REALTIME ------- //
//-------------------------------------------------------- //


public boolean EquipPositionMap(int id, String table, int posX, int posY, String positionView) throws Exception {    
	
	boolean positioned = false;

try { //GET SLQException
	
if(table.equals("cftv")) { // CFTV Definitions
 
String queryCftvLinear = "UPDATE cftv_equipment SET linear_posX = ?, linear_posY = ? WHERE equip_id = ? ";

String queryCftvMap = "UPDATE cftv_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ? ";
           	  
conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

if(positionView.equals("linear"))
ps = conn.prepareStatement(queryCftvLinear);

else ps = conn.prepareStatement(queryCftvMap);

ps.setInt(1,  posX);
ps.setInt(2,  posY);
ps.setInt(3,  id);
int rs =  ps.executeUpdate();

if(rs > 0)
	positioned = true;
 
}  // CFTV Definitions END    

if(table.equals("colas")) { // COLAS Definitions

String queryColasLinear = "UPDATE colas_equipment SET linear_posX = ?, linear_posY = ? WHERE equip_id = ?";

String queryColasMap = "UPDATE colas_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

if(positionView.equals("linear"))
ps = conn.prepareStatement(queryColasLinear);

else ps = conn.prepareStatement(queryColasMap);

ps.setInt(1,  posX);
ps.setInt(2,  posY);
ps.setInt(3,  id);
int rs =  ps.executeUpdate();

if(rs > 0)
	positioned = true;            	  

}  // COLAS Definitions END    

if(table.equals("comms")) { // COMMS Definitions

String queryCOMMSLinear = "UPDATE comms_equipment SET linear_posX = ?, linear_posY = ? WHERE equip_id = ?";

String queryCOMMSMap = "UPDATE comms_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

if(positionView.equals("linear"))
ps = conn.prepareStatement(queryCOMMSLinear);

else ps = conn.prepareStatement(queryCOMMSMap);

ps.setInt(1,  posX);
ps.setInt(2,  posY);
ps.setInt(3,  id);
int rs =  ps.executeUpdate();

if(rs > 0)
	positioned = true;              	  

}  // COMMS Definitions END    

if(table.equals("dai")) { // DAI Definitions

String queryDAILinear = "UPDATE dai_equipment SET linear_posX = ?, linear_posY = ? WHERE equip_id = ?";

String queryDAIMap = "UPDATE dai_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

if(positionView.equals("linear"))
ps = conn.prepareStatement(queryDAILinear);

else ps = conn.prepareStatement(queryDAIMap);

ps.setInt(1,  posX);
ps.setInt(2,  posY);
ps.setInt(3,  id);
int rs =  ps.executeUpdate();

if(rs > 0)
	positioned = true;           	  

} // DAI Definitions END    

if(table.equals("ocr")) { // LPR Definitions

String queryLPRLinear = "UPDATE ocr_equipment SET linear_posX = ?, linear_posY = ? WHERE equip_id = ?";

String queryLPRMap = "UPDATE ocr_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

if(positionView.equals("linear"))
ps = conn.prepareStatement(queryLPRLinear);

else ps = conn.prepareStatement(queryLPRMap);

ps.setInt(1,  posX);
ps.setInt(2,  posY);
ps.setInt(3,  id);
int rs =  ps.executeUpdate();

if(rs > 0)
	positioned = true;             	  

}  // LPR Definitions END    

if(table.equals("mto")) { // MTO Definitions

String queryMTOLinear = "UPDATE mto_equipment SET linear_posX = ?, linear_posY = ? WHERE equip_id = ?";

String queryMTOMap = "UPDATE mto_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

if(positionView.equals("linear"))
ps = conn.prepareStatement(queryMTOLinear);

else ps = conn.prepareStatement(queryMTOMap);

ps.setInt(1,  posX);
ps.setInt(2,  posY);
ps.setInt(3,  id);
int rs =  ps.executeUpdate();

if(rs > 0)
	positioned = true;
         	  

}  // SV Definitions END    

if(table.equals("dms")) { // PMV Definitions

String queryDMSLinear = "UPDATE pmv_equipment SET linear_posX = ?, linear_posY = ? WHERE equip_id = ?";

String queryDMSMap = "UPDATE pmv_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

if(positionView.equals("linear")) 
	ps = conn.prepareStatement(queryDMSLinear);

else ps = conn.prepareStatement(queryDMSMap);

ps.setInt(1,  posX);
ps.setInt(2,  posY);
ps.setInt(3,  id);
int rs =  ps.executeUpdate();

if(rs > 0)
	positioned = true;    

}  // PMV Definitions END    

if(table.equals("sat")) { // SAT Definitions

String querySATLinear = "UPDATE sat_equipment SET linear_posX = ?, linear_posY = ? WHERE equip_id = ?";

String querySATMap = "UPDATE sat_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);


if(positionView.equals("linear"))
ps = conn.prepareStatement(querySATLinear);

else ps = conn.prepareStatement(querySATMap);

ps.setInt(1,  posX);
ps.setInt(2,  posY);
ps.setInt(3,  id);
int rs =  ps.executeUpdate();

if(rs > 0)
	positioned = true; 

}  // SAT Definitions END    

if(table.equals("sos")) { // SOS Definitions

String querySOSLinear = "UPDATE sos_equipment SET linear_posX = ?, linear_posY = ? WHERE equip_id = ?";

String querySOSMap = "UPDATE sos_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

if(positionView.equals("linear")) 
ps = conn.prepareStatement(querySOSLinear);

else ps = conn.prepareStatement(querySOSMap);

ps.setInt(1,  posX);
ps.setInt(2,  posY);
ps.setInt(3,  id);
int rs =  ps.executeUpdate();

if(rs > 0)
	positioned = true;             	  

}  // SOS Definitions END    

if(table.equals("speed")) { // SPEED Definitions

String querySpeedLinear = "UPDATE speed_equipment SET linear_posX = ?, linear_posY = ? WHERE equip_id = ?";

String querySpeedMap = "UPDATE speed_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

if(positionView.equals("linear"))
ps = conn.prepareStatement(querySpeedLinear);

else ps = conn.prepareStatement(querySpeedMap);

ps.setInt(1,  posX);
ps.setInt(2,  posY);
ps.setInt(3,  id);
int rs =  ps.executeUpdate();

if(rs > 0)
	positioned = true;            	  

}  // SPEED Definitions END  

if(table.equals("sv")) { // SV Definitions

String querySVLinear = "UPDATE sv_equipment SET linear_posX = ?, linear_posY = ? WHERE equip_id = ?";

String querySVMap = "UPDATE sv_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

if(positionView.equals("linear"))
ps = conn.prepareStatement(querySVLinear);

else ps = conn.prepareStatement(querySVMap);

ps.setInt(1,  posX);
ps.setInt(2,  posY);
ps.setInt(3,  id);
int rs =  ps.executeUpdate();

if(rs > 0)
	positioned = true;
         	  

}  // SV Definitions END    

if(table.equals("wim")) { // WIM Definitions

String queryWIMLinear = "UPDATE wim_equipment SET linear_posX = ?, linear_posY = ? WHERE equip_id = ?";

String queryWIMMap = "UPDATE wim_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);

if(positionView.equals("linear"))
ps = conn.prepareStatement(queryWIMLinear);

else ps = conn.prepareStatement(queryWIMMap);

ps.setInt(1,  posX);
ps.setInt(2,  posY);
ps.setInt(3,  id);
int rs =  ps.executeUpdate();

if(rs > 0)
	positioned = true;            	  
         	  

}  // WIM Definitions END    


}catch(SQLException sqle) {

sqle.printStackTrace();
}
finally { //Close Connection

ConnectionFactory.closeConnection(conn, ps);
	
}

return positioned;
}

//-------------------------------------------------------------- //
// ----------------- CHECK IF ID SAT EXISTS ------------------- //
//------------------------------------------------------------ //

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
  
  
//------------------------------------------------------------- //
//----------------------- EQUIP NAME  ------------------------ //
//----------------------------------------------------------- //

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


//DEFINE DIRECTIONS VALUES
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
  
}