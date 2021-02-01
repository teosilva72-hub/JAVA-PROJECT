package br.com.tracevia.webapp.dao.global;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.tracevia.webapp.cfg.ModulesEnum;
import br.com.tracevia.webapp.model.cftv.CFTV;
import br.com.tracevia.webapp.model.colas.Colas;
import br.com.tracevia.webapp.model.comms.COMMS;
import br.com.tracevia.webapp.model.dai.DAI;
import br.com.tracevia.webapp.model.dms.DMS;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.Modules;
import br.com.tracevia.webapp.model.global.UserAccount;
import br.com.tracevia.webapp.model.lpr.LPR;
import br.com.tracevia.webapp.model.mto.MTO;
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

	public EquipmentsDAO() throws Exception {

		try {
			
			conn = ConnectionFactory.connectToTraceviaApp();	
			
		} catch (Exception e) {
			
			throw new Exception("erro: \n" + e.getMessage());
		}
	}	
		
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
			query = "SELECT equip_id, name, km, map_width, map_posX, map_posY, position FROM tracevia_app.cftv_equipment WHERE visible = 1";	
		
		else if(mod.getModule().equals(ModulesEnum.COLAS.getModule()))
			query = "SELECT equip_id, name, km, map_width, map_posX, map_posY, position FROM tracevia_app.colas_equipment WHERE visible = 1";	
		
		else if(mod.getModule().equals(ModulesEnum.COMMS.getModule()))
			query = "SELECT equip_id, name, km, map_width, map_posX, map_posY, position FROM tracevia_app.comms_equipment WHERE visible = 1";	
		
		else if(mod.getModule().equals(ModulesEnum.DAI.getModule()))
			query = "SELECT equip_id, name, km, map_width, map_posX, map_posY, position FROM tracevia_app.dai_equipment WHERE visible = 1";	
		
		else if(mod.getModule().equals(ModulesEnum.LPR.getModule()))
			query = "SELECT equip_id, name, km, map_width, map_posX, map_posY, position FROM tracevia_app.lpr_equipment WHERE visible = 1";	
		
		else if(mod.getModule().equals(ModulesEnum.MTO.getModule()))
			query = "SELECT equip_id, name, km, map_width, map_posX, map_posY, position FROM tracevia_app.mto_equipment WHERE visible = 1";	
			
		else if(mod.getModule().equals(ModulesEnum.PMV.getModule()))
			query = "SELECT equip_id, name, km, map_width, map_posX, map_posY, position FROM tracevia_app.pmv_equipment WHERE visible = 1";	
		
		else if(mod.getModule().equals(ModulesEnum.SAT.getModule()))
			query = "SELECT equip_id, name, km, map_width, map_posX, map_posY, position FROM tracevia_app.sat_equipment WHERE visible = 1";	
		
		else if(mod.getModule().equals(ModulesEnum.SOS.getModule()))
			query = "SELECT equip_id, name, km, map_width, map_posX, map_posY, position FROM tracevia_app.sos_equipment WHERE visible = 1";	
		
		else if(mod.getModule().equals(ModulesEnum.SPEED.getModule()))
			query = "SELECT equip_id, name, km, map_width, map_posX, map_posY, position FROM tracevia_app.speed_equipment WHERE visible = 1";	
		
		else if(mod.getModule().equals(ModulesEnum.VIDEOWALL.getModule()))
			query = "SELECT equip_id, name, km, map_width, map_posX, map_posY, position FROM tracevia_app.videowall_equipment WHERE visible = 1";	
		
		else if(mod.getModule().equals(ModulesEnum.WIM.getModule()))
			query = "SELECT equip_id, name, km, map_width, map_posX, map_posY, position FROM tracevia_app.wim_equipment WHERE visible = 1";	
		
		
		try {			
			
			conn = ConnectionFactory.connectToTraceviaApp();
						
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			//System.out.println("TESTE "+query);

			if (rs != null) {
				while (rs.next()) {			

				  Equipments eq = new Equipments();
				  
				  eq.setEquip_id(rs.getInt("equip_id"));
				  eq.setTable_id(mod.getModule().toLowerCase());
				  eq.setWidth(rs.getInt("map_width"));															
				  eq.setMapPosX(rs.getInt("map_posX"));
				  eq.setMapPosY(rs.getInt("map_posY"));		
				  eq.setPosicao(rs.getString("position"));
					
				  eq.setNome(rs.getString("name"));		
				  eq.setKm(rs.getString("km"));	
				  equips.add(eq);
				    
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
				   "linear_posX, linear_posY, position FROM tracevia_app."+modulo+"_equipment eq " +
				   "INNER JOIN concessionaire_cities c ON c.city_id = eq.city " +
				   "INNER JOIN concessionaire_roads r ON r.road_id = eq.city " +
				   "WHERE visible = 1 ";
		try {
			
			conn = ConnectionFactory.connectToTraceviaApp();
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
					equip.setWidth(rs.getInt(6));															
					equip.setLinearPosX(rs.getInt(7));
					equip.setLinearPosY(rs.getInt(8));		
					equip.setPosicao(rs.getString(9));
					
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
	
	// ---- MAP INTERFACE EQUIPMENTS ---- //
	
	public ArrayList<Equipments> buildMapEquipments(String modulo) throws Exception {

		ArrayList<Equipments> lista = new ArrayList<Equipments>();

		String sql = "SELECT equip_id, name, c.city_name, r.road_name, km, map_width, " +
		   "map_posX, map_posY, position FROM "+modulo+"_equipment eq " +
		   "INNER JOIN concessionaire_cities c ON c.city_id = eq.city " +
		   "INNER JOIN concessionaire_roads r ON r.road_id = eq.city " +
		   "WHERE visible = 1 ";
				
		try {
			
			conn = ConnectionFactory.connectToTraceviaApp();
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
					equip.setWidth(rs.getInt(6));		
					equip.setMapPosX(rs.getInt(7));
					equip.setMapPosY(rs.getInt(8));
					equip.setPosicao(rs.getString(9));
					
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
	
	// ---- DMS LINEAR INTERFACE EQUIPMENTS ---- //
	
	public ArrayList<Equipments> buildLinearDMSEquipments(String modulo) throws Exception {

		ArrayList<Equipments> lista = new ArrayList<Equipments>();

		String sql = "SELECT equip_id, name, c.city_name, r.road_name, km, linear_width, " +
				   "linear_posX, linear_posY, position FROM tracevia_app."+modulo+"_equipment eq " +
				   "INNER JOIN concessionaire_cities c ON c.city_id = eq.city " +
				   "INNER JOIN concessionaire_roads r ON r.road_id = eq.city " +
				   "WHERE visible = 1 ";
				
		try {
			
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
						
			if (rs != null) {

				while (rs.next()) {
					
					Equipments equip = new Equipments();
					
					equip.setEquip_id(rs.getInt(1));
					equip.setTable_id(modulo);
					equip.setNome(rs.getString(2));
					equip.setCidade(rs.getString(3));
					equip.setEstrada(rs.getString(4));
					equip.setKm(rs.getString(5));
					equip.setWidth(rs.getInt(6));	
					equip.setHeight((int) (equip.getWidth()*0.232));
					equip.setLinearPosX(rs.getInt(7));
					equip.setLinearPosY(rs.getInt(8));									
					equip.setPosicao(rs.getString(9));
					
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
	
	// --- DMS MAP INTERFACE EQUIPMENTS --- //
	
	public ArrayList<Equipments> buildMapDMSEquipments(String modulo) throws Exception {

		ArrayList<Equipments> lista = new ArrayList<Equipments>();

		String sql = "SELECT equip_id, name, c.city_name, r.road_name, km, map_width, " +
				   "map_posX, map_posY, position FROM tracevia_app."+modulo+"_equipment eq " +
				   "INNER JOIN concessionaire_cities c ON c.city_id = eq.city " +
				   "INNER JOIN concessionaire_roads r ON r.road_id = eq.city " +
				   "WHERE visible = 1 ";
		try {
			
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
						
			if (rs != null) {

				while (rs.next()) {
					
					Equipments equip = new Equipments();
					
					equip.setEquip_id(rs.getInt(1));	
					equip.setTable_id(modulo);
					equip.setNome(rs.getString(2));
					equip.setCidade(rs.getString(3));
					equip.setEstrada(rs.getString(4));
					equip.setKm(rs.getString(5));
					equip.setWidth(rs.getInt(6));	
					equip.setHeight((int) (equip.getWidth()*0.232));	
					equip.setMapPosX(rs.getInt(7));
					equip.setMapPosY(rs.getInt(8));					
					equip.setPosicao(rs.getString(9));
					
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
	
	
    //Equipments Options for Selection	
	public ArrayList<Equipments> EquipmentSelectOptions(String modulo) throws Exception {

		ArrayList<Equipments> lista = new ArrayList<Equipments>();

		String sql = "SELECT equip_id, name FROM tracevia_app."+modulo+"_equipment WHERE visible = 1";
				
		try {
			
			conn = ConnectionFactory.connectToTraceviaApp();
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
		public Integer EquipmentSelectLanesNumber(String modulo, String equipID) throws Exception {

			int lanesNumber = 0;

			String sql = "SELECT number_lanes FROM tracevia_app."+modulo+"_equipment WHERE equip_id = ? AND visible = 1";
					
			try {
				
				conn = ConnectionFactory.connectToTraceviaApp();
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
			    sql = "SELECT number_lanes FROM tracevia_app."+modulo+"_equipment WHERE equip_id = '"+equips[i]+"' AND visible = 1";
					
			try {
				
				conn = ConnectionFactory.connectToTraceviaApp();
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

			String sql = "SELECT name FROM tracevia_app."+modulo+"_equipment WHERE equip_id = '"+equipId+"' AND visible = 1 ";
					
			try {
				
				conn = ConnectionFactory.connectToTraceviaApp();
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

			conn = ConnectionFactory.connectToTraceviaApp();

			String sql = "SELECT name, city, road, km, number_lanes, dir_lane1 FROM sat_equipment WHERE equip_id = '"+ equip_id + "' AND visible = 1";

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					eq.setNome(rs.getString("name"));
					eq.setCidade(rs.getString("city"));
					eq.setEstrada(rs.getString("road"));
					eq.setKm(rs.getString("km"));
					eq.setNumFaixas(rs.getInt("number_lanes"));		
					eq.setFaixa1(rs.getString("dir_lane1"));
					
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

		conn = ConnectionFactory.connectToTraceviaApp();

		String sql = "SELECT name, city, road, km FROM "+module+"_equipment WHERE equip_id = '"+ equip_id + "' AND visible = 1";

		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();

		if (rs != null) {
			while (rs.next()) {

				eq.setNome(rs.getString("name"));
				eq.setCidade(rs.getString("city"));
				eq.setEstrada(rs.getString("road"));
				eq.setKm(rs.getString("km"));			
			}
		}

	} catch (SQLException sqle) {
		sqle.printStackTrace();
	}finally {ConnectionFactory.closeConnection(conn, ps);}

	return eq;
}	
    
    //DMS
    
    public ArrayList<Equipments> pmvInterface() throws Exception {

		ArrayList<Equipments> lista = new ArrayList<Equipments>();

		String sql = "SELECT equip_id, table_id, name, city, road, km, map_width, map_posX, map_posY FROM pmv_equipment AND visible = 1";
				
		try {
			
			conn = ConnectionFactory.connectToTraceviaApp();	
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			NumberFormat formatter = new DecimalFormat("#0.000"); 
			
			if(rs != null) {

				while (rs.next()) {
					
					DMS dados = new DMS();
					
					dados.setEquip_id(rs.getInt(1));				
					dados.setNome(rs.getString(3));
					dados.setCidade(rs.getString(4));
					dados.setEstrada(rs.getString(6));	
					
					String aux = rs.getString(5).replace("+", ".");
					double auxD = Double.parseDouble(aux);
					dados.setKm(formatter.format(auxD));
					
					dados.setWidth(rs.getInt(7));
					dados.setHeight((int) (dados.getWidth()*0.232));										
					dados.setMapPosX(rs.getInt(8));
					dados.setMapPosY(rs.getInt(9));			
											
					lista.add(dados);
				}
				
				return lista;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return null;
	}

	//Lista todos os Sites independente do status
	public ArrayList<Equipments> listPMVSites() throws Exception{
		
        ArrayList<Equipments> lista = new ArrayList<Equipments>();         
		
		String sql = "";

		try {			
		
			conn = ConnectionFactory.connectToTraceviaApp();				
			
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
				
					conn = ConnectionFactory.connectToTraceviaApp();				
					
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
				
					conn = ConnectionFactory.connectToTraceviaApp();				
					
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
              
              

             // --------------------------------------------------- //
    	    // ------- CREATE EQUIPMENT FOR MAP / REALTIME ------- //
    	   // --------------------------------------------------- //
              
              public boolean EquipRegisterMap(Equipments equip, String table) throws Exception {
            		
          		
          		boolean status = false;          		
          		
          		
          		try {
          			
          			String query = "INSERT INTO "+table+"_equipment (equip_id, creation_date, creation_username, name, city, road, km, map_width, map_posX, map_posY, visible)"
          					+ " values  ( ?,?,?,?,?,?,?,?,?,?,?)";
          	           

          			conn = ConnectionFactory.connectToTraceviaApp();
          			
                      //Execute Register			
          			ps = conn.prepareStatement(query);
          			
          			ps.setInt(1, equip.getEquip_id());
          			ps.setString(2, equip.getCreation_date());
          			ps.setString(3, equip.getCreation_username());
          			ps.setString(4, equip.getNome());
          			ps.setString(5, equip.getCidade());
          			ps.setString(6, equip.getEstrada());
          			ps.setString(7, equip.getKm());			
          			ps.setInt(8, equip.getWidth());
          			ps.setInt(9, 0); //posX
          			ps.setInt(10, 0); //posY
          			ps.setBoolean(11, true);
          			          			
          			//System.out.println(sql);
          			
          			int success = ps.executeUpdate();
          			
          			if(success > 0)         				
          				  status = true;							
          	              											
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
              
              
         public boolean EquipUpdateMap(Equipments equip, String table) throws Exception {    
              
         	 
             boolean updated = false;
        	 
        	 try { //GET SLQException           
            	 
            	 
              if(table.equals("cftv")) { // CFTV Definitions            	  
            	  
            	  String queryCftv = "UPDATE cftv_equipment SET name = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ? ";
            	              	  
            	  conn = ConnectionFactory.connectToTraceviaApp();
            	  ps = conn.prepareStatement(queryCftv);
            	 
            	  ps.setString(1,  equip.getNome());
            	  ps.setString(2,  equip.getCidade());
            	  ps.setString(3,  equip.getEstrada());
            	  ps.setString(4,  equip.getKm());
            	  ps.setInt(5,     equip.getWidth());            
            	  ps.setInt(6,  equip.getEquip_id());
            	  
            	  int rs = ps.executeUpdate();
            	  
            	  if (rs > 0) 
            		  updated = true;            	  
            	  
              }  // CFTV Definitions END    
              
              if(table.equals("colas")) { // COLAS Definitions

            	  String queryColas= "UPDATE colas_equipment SET name = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ? ";

            	  conn = ConnectionFactory.connectToTraceviaApp();
            	  ps = conn.prepareStatement(queryColas);
            	  
            	  ps.setString(1,  equip.getNome());
            	  ps.setString(2,  equip.getCidade());
            	  ps.setString(3,  equip.getEstrada());
            	  ps.setString(4,  equip.getKm());
            	  ps.setInt(5,     equip.getWidth());
            	  ps.setInt(6,  equip.getEquip_id());

            	  int rs = ps.executeUpdate();

            	  if (rs > 0) 
            		  updated = true;

              }  // COLAS Definitions END    
              
              if(table.equals("comms")) { // COMMS Definitions

            	  String queryCOMMS= "UPDATE comms_equipment SET name = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ? ";

            	  conn = ConnectionFactory.connectToTraceviaApp();
            	  ps = conn.prepareStatement(queryCOMMS);

            	  ps.setString(1,  equip.getNome());
            	  ps.setString(2,  equip.getCidade());
            	  ps.setString(3,  equip.getEstrada());
            	  ps.setString(4,  equip.getKm());
            	  ps.setInt(5,     equip.getWidth());
            	  ps.setInt(6,  equip.getEquip_id());
            	  
            	  int rs = ps.executeUpdate();

            	  if (rs > 0) 
            		  updated = true;           	  

              }  // COMMS Definitions END    
              
              if(table.equals("dai")) { // DAI Definitions

            	  String queryDAI= "UPDATE dai_equipment SET name = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ?";

            	  conn = ConnectionFactory.connectToTraceviaApp();
            	  ps = conn.prepareStatement(queryDAI);

            	  ps.setString(1,  equip.getNome());
            	  ps.setString(2,  equip.getCidade());
            	  ps.setString(3,  equip.getEstrada());
            	  ps.setString(4,  equip.getKm());
            	  ps.setInt(5,     equip.getWidth());
            	  ps.setInt(6,  equip.getEquip_id());          			            			  

            	  int rs = ps.executeUpdate();

            	  if (rs > 0) 
            		  updated = true;              	  

              }  // DAI Definitions END    
              
              if(table.equals("lpr")) { // LPR Definitions

            	  String queryLPR= "UPDATE lpr_equipment SET name = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ?";

            	  conn = ConnectionFactory.connectToTraceviaApp();
            	  ps = conn.prepareStatement(queryLPR);

            	  ps.setString(1,  equip.getNome());
            	  ps.setString(2,  equip.getCidade());
            	  ps.setString(3,  equip.getEstrada());
            	  ps.setString(4,  equip.getKm());
            	  ps.setInt(5,     equip.getWidth());
            	  ps.setInt(6,  equip.getEquip_id());

            	  int rs = ps.executeUpdate();

            	  if (rs > 0) 
            		  updated = true;
         	  

              }  // LPR Definitions END    
              
              if(table.equals("mto")) { // MTO Definitions

            	  String queryMTO= "UPDATE mto_equipment SET name = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ? ";

            	  conn = ConnectionFactory.connectToTraceviaApp();
            	  ps = conn.prepareStatement(queryMTO);
            	  
            	  ps.setString(1,  equip.getNome());
            	  ps.setString(2,  equip.getCidade());
            	  ps.setString(3,  equip.getEstrada());
            	  ps.setString(4,  equip.getKm());
            	  ps.setInt(5,     equip.getWidth());
            	  ps.setInt(6,  equip.getEquip_id());        			            			  

            	  int rs = ps.executeUpdate();

            	  if (rs > 0) 
            		  updated = true;          	  

              }  // MTO Definitions END    
              
              if(table.equals("pmv")) { // PMV Definitions

            	  String queryDMS= "UPDATE pmv_equipment SET name = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ? ";

            	  conn = ConnectionFactory.connectToTraceviaApp();
            	  ps = conn.prepareStatement(queryDMS);
            	  
            	  ps.setString(1,  equip.getNome());
            	  ps.setString(2,  equip.getCidade());
            	  ps.setString(3,  equip.getEstrada());
            	  ps.setString(4,  equip.getKm());
            	  ps.setInt(5,     equip.getWidth());
            	  ps.setInt(6,  equip.getEquip_id());       			            			  

            	  int rs = ps.executeUpdate();

            	  if (rs > 0) 
            		  updated = true;           	  

              }  // PMV Definitions END    
              
              if(table.equals("sat")) { // SAT Definitions

            	  String querySAT= "UPDATE sat_equipment SET name = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ? ";

            	  conn = ConnectionFactory.connectToTraceviaApp();
            	  ps = conn.prepareStatement(querySAT);
            	  
            	  ps.setString(1,  equip.getNome());
            	  ps.setString(2,  equip.getCidade());
            	  ps.setString(3,  equip.getEstrada());
            	  ps.setString(4,  equip.getKm());
            	  ps.setInt(5,     equip.getWidth());
            	  ps.setInt(6,  equip.getEquip_id());        			            			  

            	  int rs = ps.executeUpdate();

            	  if (rs > 0) 
            		  updated = true;               	  

              }  // SAT Definitions END    
              
              if(table.equals("sos")) { // SOS Definitions

            	  String querySOS= "UPDATE sos_equipment SET name = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ? ";

            	  conn = ConnectionFactory.connectToTraceviaApp();
            	  ps = conn.prepareStatement(querySOS);
            	  
            	  ps.setString(1,  equip.getNome());
            	  ps.setString(2,  equip.getCidade());
            	  ps.setString(3,  equip.getEstrada());
            	  ps.setString(4,  equip.getKm());
            	  ps.setInt(5,     equip.getWidth());
            	  ps.setInt(6,  equip.getEquip_id());          			            			  

            	  int rs = ps.executeUpdate();

            	  if (rs > 0) 
            		  updated = true;              	  

              }  // SOS Definitions END    
              
              if(table.equals("speed")) { // SPEED Definitions

            	  String querySpeed= "UPDATE speed_equipment SET name = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ? ";

            	  conn = ConnectionFactory.connectToTraceviaApp();
            	  ps = conn.prepareStatement(querySpeed);
            	  
            	  ps.setString(1,  equip.getNome());
            	  ps.setString(2,  equip.getCidade());
            	  ps.setString(3,  equip.getEstrada());
            	  ps.setString(4,  equip.getKm());
            	  ps.setInt(5,     equip.getWidth());
            	  ps.setInt(6,  equip.getEquip_id());          			            			  

            	  int rs = ps.executeUpdate();

            	  if (rs > 0) 
            		  updated = true;             	  

              }  // SPEED Definitions END    
              
              if(table.equals("wim")) { // WIM Definitions

            	  String queryWIM= "UPDATE wim_equipment SET name = ?, city = ?, road = ?, km = ?, map_width = ? WHERE equip_id = ? ";

            	  conn = ConnectionFactory.connectToTraceviaApp();
            	  ps = conn.prepareStatement(queryWIM);
            	  
            	  ps.setString(1,  equip.getNome());
            	  ps.setString(2,  equip.getCidade());
            	  ps.setString(3,  equip.getEstrada());
            	  ps.setString(4,  equip.getKm());
            	  ps.setInt(5,     equip.getWidth());
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
         
  
//--------------------------------------------------- //
// ------- UPDATE EQUIPMENT FOR MAP / REALTIME ------- //
// --------------------------------------------------- //
         
       //--------------------------------------------------- //
      // ------- SEARCH EQUIPMENT FOR MAP / REALTIME ------- //
      // --------------------------------------------------- //
    
    
public Equipments EquipSearchMap(int id, String table) throws Exception {    
    
System.out.println(table);
	
   try { //GET SLQException
  	 
    
    if(table.equals("cftv")) { // CFTV Definitions
  	  
  	  CFTV cftv = new CFTV();
  	  
  	  String queryCftv = "SELECT equip_id, name, city, road, km, map_width, visible FROM cftv_equipment WHERE equip_id = ? ";
  	              	  
  	  conn = ConnectionFactory.connectToTraceviaApp();
  	  ps = conn.prepareStatement(queryCftv);
  	  ps.setInt(1,  id);
  	  rs = ps.executeQuery();
  	  
  	  if(rs != null) {
  		  while(rs.next()){
  			  
  			  cftv.setEquip_id(rs.getInt(1));
  			  cftv.setNome(rs.getString(2));
  			  cftv.setCidade(rs.getString(3));
  			  cftv.setEstrada(rs.getString(4));
  			  cftv.setKm(rs.getString(5));
  			  cftv.setWidth(rs.getInt(6));
  			  cftv.setVisible(rs.getBoolean(7));            			            			  
  			  
  		  }
  	  }
  	  
  	  return cftv;            	  
  	  
    }  // CFTV Definitions END    
    
    if(table.equals("colas")) { // COLAS Definitions

  	  Colas colas = new Colas();

  	  String queryColas= "SELECT equip_id, name, city, road, km, map_width, visible FROM colas_equipment WHERE equip_id = ? ";

  	  conn = ConnectionFactory.connectToTraceviaApp();
  	  ps = conn.prepareStatement(queryColas);
  	  ps.setInt(1,  id);
  	  rs = ps.executeQuery();

  	  if(rs != null) {
  		  while(rs.next()){

  			  colas.setEquip_id(rs.getInt(1));
  			  colas.setNome(rs.getString(2));
  			  colas.setCidade(rs.getString(3));
  			  colas.setEstrada(rs.getString(4));
  			  colas.setKm(rs.getString(5));
  			  colas.setWidth(rs.getInt(6));
  			  colas.setVisible(rs.getBoolean(7));            			            			  

  		  }
  	  }

  	  return colas;            	  

    }  // COLAS Definitions END    
    
    if(table.equals("comms")) { // COMMS Definitions

  	  COMMS comms = new COMMS();

  	  String queryCOMMS= "SELECT equip_id, name, city, road, km, map_width, visible FROM comms_equipment WHERE equip_id = ? ";

  	  conn = ConnectionFactory.connectToTraceviaApp();
  	  ps = conn.prepareStatement(queryCOMMS);
  	  ps.setInt(1,  id);
  	  rs = ps.executeQuery();

  	  if(rs != null) {
  		  while(rs.next()){

  			  comms.setEquip_id(rs.getInt(1));
  			  comms.setNome(rs.getString(2));
  			  comms.setCidade(rs.getString(3));
  			  comms.setEstrada(rs.getString(4));
  			  comms.setKm(rs.getString(5));
  			  comms.setWidth(rs.getInt(6));
  			  comms.setVisible(rs.getBoolean(7));            			            			  

  		  }
  	  }

  	  return comms;            	  

    }  // COMMS Definitions END    
    
    if(table.equals("dai")) { // DAI Definitions

  	  DAI dai = new DAI();

  	  String queryDAI= "SELECT equip_id, name, city, road, km, map_width, visible FROM dai_equipment WHERE equip_id = ? ";

  	  conn = ConnectionFactory.connectToTraceviaApp();
  	  ps = conn.prepareStatement(queryDAI);
  	  ps.setInt(1,  id);
  	  rs = ps.executeQuery();

  	  if(rs != null) {
  		  while(rs.next()){

  			  dai.setEquip_id(rs.getInt(1));
  			  dai.setNome(rs.getString(2));
  			  dai.setCidade(rs.getString(3));
  			  dai.setEstrada(rs.getString(4));
  			  dai.setKm(rs.getString(5));
  			  dai.setWidth(rs.getInt(6));
  			  dai.setVisible(rs.getBoolean(7));            			            			  

  		  }
  	  }

  	  return dai;            	  

    }  // DAI Definitions END    
    
    if(table.equals("lpr")) { // LPR Definitions

  	  LPR lpr = new LPR();

  	  String queryLPR= "SELECT equip_id, name, city, road, km, map_width, visible FROM lpr_equipment WHERE equip_id = ? ";

  	  conn = ConnectionFactory.connectToTraceviaApp();
  	  ps = conn.prepareStatement(queryLPR);
  	  ps.setInt(1,  id);
  	  rs = ps.executeQuery();

  	  if(rs != null) {
  		  while(rs.next()){

  			  lpr.setEquip_id(rs.getInt(1));
  			  lpr.setNome(rs.getString(2));
  			  lpr.setCidade(rs.getString(3));
  			  lpr.setEstrada(rs.getString(4));
  			  lpr.setKm(rs.getString(5));
  			  lpr.setWidth(rs.getInt(6));
  			  lpr.setVisible(rs.getBoolean(7));            			            			  

  		  }
  	  }

  	  return lpr;            	  

    }  // LPR Definitions END    
    
    if(table.equals("mto")) { // MTO Definitions

  	  MTO mto = new MTO();

  	  String queryMTO= "SELECT equip_id, name, city, road, km, map_width, visible FROM mto_equipment WHERE equip_id = ? ";

  	  conn = ConnectionFactory.connectToTraceviaApp();
  	  ps = conn.prepareStatement(queryMTO);
  	  ps.setInt(1,  id);
  	  rs = ps.executeQuery();

  	  if(rs != null) {
  		  while(rs.next()){

  			  mto.setEquip_id(rs.getInt(1));
  			  mto.setNome(rs.getString(2));
  			  mto.setCidade(rs.getString(3));
  			  mto.setEstrada(rs.getString(4));
  			  mto.setKm(rs.getString(5));
  			  mto.setWidth(rs.getInt(6));
  			  mto.setVisible(rs.getBoolean(7));            			            			  

  		  }
  	  }

  	  return mto;            	  

    }  // MTO Definitions END    
    
    if(table.equals("pmv")) { // PMV Definitions

  	  DMS dms = new DMS();

  	  String queryDMS= "SELECT equip_id, name, city, road, km, map_width, visible FROM pmv_equipment WHERE equip_id = ? ";

  	  conn = ConnectionFactory.connectToTraceviaApp();
  	  ps = conn.prepareStatement(queryDMS);
  	  ps.setInt(1,  id);
  	  rs = ps.executeQuery();

  	  if(rs != null) {
  		  while(rs.next()){

  			  dms.setEquip_id(rs.getInt(1));
  			  dms.setNome(rs.getString(2));
  			  dms.setCidade(rs.getString(3));
  			  dms.setEstrada(rs.getString(4));
  			  dms.setKm(rs.getString(5));
  			  dms.setWidth(rs.getInt(6));
  			  dms.setVisible(rs.getBoolean(7));            			            			  

  		  }
  	  }

  	  return dms;            	  

    }  // PMV Definitions END    
    
    if(table.equals("sat")) { // SAT Definitions

  	  SAT sat = new SAT();

  	  String querySAT= "SELECT equip_id, name, city, road, km, map_width, visible FROM sat_equipment WHERE equip_id = ? ";

  	  conn = ConnectionFactory.connectToTraceviaApp();
  	  ps = conn.prepareStatement(querySAT);
  	  ps.setInt(1,  id);
  	  rs = ps.executeQuery();

  	  if(rs != null) {
  		  while(rs.next()){

  			  sat.setEquip_id(rs.getInt(1));
  			  sat.setNome(rs.getString(2));
  			  sat.setCidade(rs.getString(3));
  			  sat.setEstrada(rs.getString(4));
  			  sat.setKm(rs.getString(5));
  			  sat.setWidth(rs.getInt(6));
  			  sat.setVisible(rs.getBoolean(7));            			            			  

  		  }
  	  }

  	  return sat;            	  

    }  // SAT Definitions END    
    
    if(table.equals("sos")) { // SOS Definitions

  	  SOS sos = new SOS();

  	  String querySOS= "SELECT equip_id, name, city, road, km, map_width, visible FROM sos_equipment WHERE equip_id = ? ";

  	  conn = ConnectionFactory.connectToTraceviaApp();
  	  ps = conn.prepareStatement(querySOS);
  	  ps.setInt(1,  id);
  	  rs = ps.executeQuery();

  	  if(rs != null) {
  		  while(rs.next()){

  			  sos.setEquip_id(rs.getInt(1));
  			  sos.setNome(rs.getString(2));
  			  sos.setCidade(rs.getString(3));
  			  sos.setEstrada(rs.getString(4));
  			  sos.setKm(rs.getString(5));
  			  sos.setWidth(rs.getInt(6));
  			  sos.setVisible(rs.getBoolean(7));            			            			  

  		  }
  	  }

  	  return sos;            	  

    }  // SOS Definitions END    
    
    if(table.equals("speed")) { // SPEED Definitions

  	  Speed speed = new Speed();

  	  String querySpeed= "SELECT equip_id, name, city, road, km, map_width, visible FROM speed_equipment WHERE equip_id = ? ";

  	  conn = ConnectionFactory.connectToTraceviaApp();
  	  ps = conn.prepareStatement(querySpeed);
  	  ps.setInt(1,  id);
  	  rs = ps.executeQuery();

  	  if(rs != null) {
  		  while(rs.next()){

  			  speed.setEquip_id(rs.getInt(1));
  			  speed.setNome(rs.getString(2));
  			  speed.setCidade(rs.getString(3));
  			  speed.setEstrada(rs.getString(4));
  			  speed.setKm(rs.getString(5));
  			  speed.setWidth(rs.getInt(6));
  			  speed.setVisible(rs.getBoolean(7));            			            			  

  		  }
  	  }

  	  return speed;            	  

    }  // SPEED Definitions END    
    
    if(table.equals("wim")) { // WIM Definitions

  	  WIM wim = new WIM();

  	  String queryWIM= "SELECT equip_id, name, city, road, km, map_width, visible FROM wim_equipment WHERE equip_id = ? ";

  	  conn = ConnectionFactory.connectToTraceviaApp();
  	  ps = conn.prepareStatement(queryWIM);
  	  ps.setInt(1,  id);
  	  rs = ps.executeQuery();

  	  if(rs != null) {
  		  while(rs.next()){

  			  wim.setEquip_id(rs.getInt(1));
  			  wim.setNome(rs.getString(2));
  			  wim.setCidade(rs.getString(3));
  			  wim.setEstrada(rs.getString(4));
  			  wim.setKm(rs.getString(5));
  			  wim.setWidth(rs.getInt(6));
  			  wim.setVisible(rs.getBoolean(7));            			            			  

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
              	  
  conn = ConnectionFactory.connectToTraceviaApp();
  ps = conn.prepareStatement(queryCftv);
  ps.setInt(1,  id);
  int rs =  ps.executeUpdate();
  
  if(rs > 0)
	  deleted = true;
    
}  // CFTV Definitions END    

if(table.equals("colas")) { // COLAS Definitions

  String queryColas= "DELETE FROM colas_equipment WHERE equip_id = ?";

  conn = ConnectionFactory.connectToTraceviaApp();
  ps = conn.prepareStatement(queryColas);
  ps.setInt(1,  id);
  int rs =  ps.executeUpdate();
  
  if(rs > 0)
	  deleted = true;            	  

}  // COLAS Definitions END    

if(table.equals("comms")) { // COMMS Definitions

  String queryCOMMS= "DELETE FROM comms_equipment WHERE equip_id = ?";

  conn = ConnectionFactory.connectToTraceviaApp();
  ps = conn.prepareStatement(queryCOMMS);
  ps.setInt(1,  id);
  int rs =  ps.executeUpdate();
  
  if(rs > 0)
	  deleted = true;              	  

}  // COMMS Definitions END    

if(table.equals("dai")) { // DAI Definitions

  String queryDAI= "DELETE FROM dai_equipment WHERE equip_id = ?";

  conn = ConnectionFactory.connectToTraceviaApp();
  ps = conn.prepareStatement(queryDAI);;
  ps.setInt(1,  id);
  int rs =  ps.executeUpdate();
  
  if(rs > 0)
	  deleted = true;           	  

} // DAI Definitions END    

if(table.equals("lpr")) { // LPR Definitions

  String queryLPR= "DELETE FROM lpr_equipment WHERE equip_id = ?";

  conn = ConnectionFactory.connectToTraceviaApp();
  ps = conn.prepareStatement(queryLPR);
  ps.setInt(1,  id);
  int rs =  ps.executeUpdate();
  
  if(rs > 0)
	  deleted = true;             	  

}  // LPR Definitions END    

if(table.equals("mto")) { // MTO Definitions

  String queryMTO= "DELETE FROM mto_equipment WHERE equip_id = ?";

  conn = ConnectionFactory.connectToTraceviaApp();
  ps = conn.prepareStatement(queryMTO);
  ps.setInt(1,  id);
  int rs =  ps.executeUpdate();
  
  if(rs > 0)
	  deleted = true;
            	  

}  // MTO Definitions END    

if(table.equals("pmv")) { // PMV Definitions

  String queryDMS= "DELETE FROM pmv_equipment WHERE equip_id = ?";

  conn = ConnectionFactory.connectToTraceviaApp();
  ps = conn.prepareStatement(queryDMS);
  ps.setInt(1,  id);
  int rs =  ps.executeUpdate();
  
  if(rs > 0)
	  deleted = true;          	            	  

}  // PMV Definitions END    

if(table.equals("sat")) { // SAT Definitions

  String querySAT= "DELETE FROM sat_equipment WHERE equip_id = ?";

  conn = ConnectionFactory.connectToTraceviaApp();
  ps = conn.prepareStatement(querySAT);
  ps.setInt(1,  id);
  int rs =  ps.executeUpdate();
  
  if(rs > 0)
	  deleted = true; 
  
}  // SAT Definitions END    

if(table.equals("sos")) { // SOS Definitions

  String querySOS= "DELETE FROM sos_equipment WHERE equip_id = ?";

  conn = ConnectionFactory.connectToTraceviaApp();
  ps = conn.prepareStatement(querySOS);
  ps.setInt(1,  id);
  int rs =  ps.executeUpdate();
  
  if(rs > 0)
	  deleted = true;             	  

}  // SOS Definitions END    

if(table.equals("speed")) { // SPEED Definitions

  String querySpeed= "DELETE FROM speed_equipment WHERE equip_id = ?";

  conn = ConnectionFactory.connectToTraceviaApp();
  ps = conn.prepareStatement(querySpeed);
  ps.setInt(1,  id);
  int rs =  ps.executeUpdate();
  
  if(rs > 0)
	  deleted = true;            	  

}  // SPEED Definitions END    

if(table.equals("wim")) { // WIM Definitions

  String queryWIM= "DELETE FROM wim_equipment WHERE equip_id = ?";

  conn = ConnectionFactory.connectToTraceviaApp();
  ps = conn.prepareStatement(queryWIM);
  ps.setInt(1,  id);
  int rs =  ps.executeUpdate();
  
  if(rs > 0)
	  deleted = true;            	  
            	  

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


public boolean EquipPositionMap(int id, String table, int posX, int posY) throws Exception {    
	
	boolean positioned = false;

try { //GET SLQException


if(table.equals("cftv")) { // CFTV Definitions
 
String queryCftv = "UPDATE cftv_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ? ";
           	  
conn = ConnectionFactory.connectToTraceviaApp();
ps = conn.prepareStatement(queryCftv);
ps.setInt(1,  posX);
ps.setInt(2,  posY);
ps.setInt(3,  id);
int rs =  ps.executeUpdate();

if(rs > 0)
	positioned = true;
 
}  // CFTV Definitions END    

if(table.equals("colas")) { // COLAS Definitions

String queryColas= "UPDATE colas_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

conn = ConnectionFactory.connectToTraceviaApp();
ps = conn.prepareStatement(queryColas);
ps.setInt(1,  posX);
ps.setInt(2,  posY);
ps.setInt(3,  id);
int rs =  ps.executeUpdate();

if(rs > 0)
	positioned = true;            	  

}  // COLAS Definitions END    

if(table.equals("comms")) { // COMMS Definitions

String queryCOMMS= "UPDATE comms_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

conn = ConnectionFactory.connectToTraceviaApp();
ps = conn.prepareStatement(queryCOMMS);
ps.setInt(1,  posX);
ps.setInt(2,  posY);
ps.setInt(3,  id);
int rs =  ps.executeUpdate();

if(rs > 0)
	positioned = true;              	  

}  // COMMS Definitions END    

if(table.equals("dai")) { // DAI Definitions

String queryDAI= "UPDATE dai_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

conn = ConnectionFactory.connectToTraceviaApp();
ps = conn.prepareStatement(queryDAI);;
ps.setInt(1,  posX);
ps.setInt(2,  posY);
ps.setInt(3,  id);
int rs =  ps.executeUpdate();

if(rs > 0)
	positioned = true;           	  

} // DAI Definitions END    

if(table.equals("lpr")) { // LPR Definitions

String queryLPR= "UPDATE lpr_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

conn = ConnectionFactory.connectToTraceviaApp();
ps = conn.prepareStatement(queryLPR);
ps.setInt(1,  posX);
ps.setInt(2,  posY);
ps.setInt(3,  id);
int rs =  ps.executeUpdate();

if(rs > 0)
	positioned = true;             	  

}  // LPR Definitions END    

if(table.equals("mto")) { // MTO Definitions

String queryMTO= "UPDATE mto_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

conn = ConnectionFactory.connectToTraceviaApp();
ps = conn.prepareStatement(queryMTO);
ps.setInt(1,  posX);
ps.setInt(2,  posY);
ps.setInt(3,  id);
int rs =  ps.executeUpdate();

if(rs > 0)
	positioned = true;
         	  

}  // MTO Definitions END    

if(table.equals("pmv")) { // PMV Definitions

String queryDMS= "UPDATE pmv_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

conn = ConnectionFactory.connectToTraceviaApp();
ps = conn.prepareStatement(queryDMS);
ps.setInt(1,  posX);
ps.setInt(2,  posY);
ps.setInt(3,  id);
int rs =  ps.executeUpdate();

if(rs > 0)
	positioned = true;          	            	  

}  // PMV Definitions END    

if(table.equals("sat")) { // SAT Definitions

String querySAT= "UPDATE sat_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

conn = ConnectionFactory.connectToTraceviaApp();
ps = conn.prepareStatement(querySAT);
ps.setInt(1,  posX);
ps.setInt(2,  posY);
ps.setInt(3,  id);
int rs =  ps.executeUpdate();

if(rs > 0)
	positioned = true; 

}  // SAT Definitions END    

if(table.equals("sos")) { // SOS Definitions

String querySOS= "UPDATE sos_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

conn = ConnectionFactory.connectToTraceviaApp();
ps = conn.prepareStatement(querySOS);
ps.setInt(1,  posX);
ps.setInt(2,  posY);
ps.setInt(3,  id);
int rs =  ps.executeUpdate();

if(rs > 0)
	positioned = true;             	  

}  // SOS Definitions END    

if(table.equals("speed")) { // SPEED Definitions

String querySpeed= "UPDATE speed_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

conn = ConnectionFactory.connectToTraceviaApp();
ps = conn.prepareStatement(querySpeed);
ps.setInt(1,  posX);
ps.setInt(2,  posY);
ps.setInt(3,  id);
int rs =  ps.executeUpdate();

if(rs > 0)
	positioned = true;            	  

}  // SPEED Definitions END    

if(table.equals("wim")) { // WIM Definitions

String queryWIM= "UPDATE wim_equipment SET map_posX = ?, map_posY = ? WHERE equip_id = ?";

conn = ConnectionFactory.connectToTraceviaApp();
ps = conn.prepareStatement(queryWIM);
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

//---------------------------------------------------------- //
//------- POSITION FOR EQUIPMENT FOR MAP / REALTIME ------- //
//-------------------------------------------------------- //


}