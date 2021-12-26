package br.com.tracevia.webapp.controller.global;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import br.com.tracevia.webapp.cfg.ModulesEnum;
import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.global.RoadConcessionaireDAO;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.dms.DMS;
import br.com.tracevia.webapp.model.global.EquipmentDataSource;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.meteo_.METEO;
import br.com.tracevia.webapp.model.sat.SAT;
import br.com.tracevia.webapp.model.sos.SOS;
import br.com.tracevia.webapp.model.speed.Speed;
import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.SessionUtil;

@ManagedBean(name="equipsBean")
@RequestScoped
public class EquipmentsBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<SelectItem> cities, roads, module, lanes, dir, dmsType, meteoType;

	RoadConcessionaireDAO concessionaireDao;

	LocaleUtil localeDirection, localeMap;

	Equipments equip;
	SAT sat;
	DMS dms;
	Speed speed;

	private int equipId;
	private String equipTable, equipDel;
	private int positionX, positionY, id;
	private boolean checked;	

	public int getid() {
		return id;
	}

	public void setid(int id) {
		this.id = id;
	}

	@ManagedProperty("#{loginAccount}")
	private LoginAccountBean login;

	private SOS sos;

	public LoginAccountBean getLogin() {
		return login;
	}

	public void setLogin(LoginAccountBean login) {
		this.login = login;
	}

	public List<SelectItem> getCities() {
		return cities;
	}

	public void setCities(List<SelectItem> cities) {
		this.cities = cities;
	}

	public List<SelectItem> getRoads() {
		return roads;
	}

	public void setRoads(List<SelectItem> roads) {
		this.roads = roads;
	}

	public List<SelectItem> getModule() {
		return module;
	}

	public void setModule(List<SelectItem> module) {
		this.module = module;
	}

	public List<SelectItem> getLanes() {
		return lanes;
	}

	public void setLanes(List<SelectItem> lanes) {
		this.lanes = lanes;
	}

	public List<SelectItem> getDir() {
		return dir;
	}

	public void setDir(List<SelectItem> dir) {
		this.dir = dir;
	}

	public List<SelectItem> getDmsType() {
		return dmsType;
	}

	public void setDmsType(List<SelectItem> dmsType) {
		this.dmsType = dmsType;
	}

	public List<SelectItem> getMeteoType() {
		return meteoType;
	}

	public void setMeteoType(List<SelectItem> meteoType) {
		this.meteoType = meteoType;
	}

	public int getEquipId() {
		return equipId;
	}

	public void setEquipId(int equipId) {
		this.equipId = equipId;
	}

	public String getEquipTable() {
		return equipTable;
	}

	public void setEquipTable(String equipTable) {
		this.equipTable = equipTable;
	}

	public String getEquipDel() {
		return equipDel;
	}

	public void setEquipDel(String equipDel) {
		this.equipDel = equipDel;
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public Equipments getEquip() {
		return equip;
	}

	public void setEquip(Equipments equip) {
		this.equip = equip;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	private String imgControle = "";

	public String getImgControle() {
		try {

			Path path = Paths.get(imgControle);
			byte[] file = Files.readAllBytes(path);
			return Base64.getEncoder().encodeToString(file);
		} catch (IOException e) {
			return "";
		}
	}
	@PostConstruct
	public void initialize() {

		localeDirection = new LocaleUtil();
		localeMap = new LocaleUtil();

		localeDirection.getResourceBundle(LocaleUtil.LABELS_DIRECTIONS);	
		localeMap.getResourceBundle(LocaleUtil.LABELS_MAPS);

		equip = new Equipments();

		cities = new  ArrayList<SelectItem>();
		roads = new  ArrayList<SelectItem>();
		module = new  ArrayList<SelectItem>();
		lanes = new  ArrayList<SelectItem>();
		dir = new ArrayList<SelectItem>();
		dmsType = new ArrayList<SelectItem>();
		meteoType = new ArrayList<SelectItem>();

		try {

			concessionaireDao = new RoadConcessionaireDAO();

			cities = concessionaireDao.cityDefinitions();
			roads = concessionaireDao.roadDefinitions();
			module = concessionaireDao.moduleDefinitions();

			for (int f = 2; f <= 8; f++) {

				SelectItem s = new SelectItem();

				s.setValue(f);
				s.setLabel(String.valueOf(f)+" "+localeDirection.getStringKey("direction_lane_label"));
				lanes.add(s);				
			}

			dir.add(new SelectItem("N", localeDirection.getStringKey("directions_north")));   
			dir.add(new SelectItem("S", localeDirection.getStringKey("directions_south")));   
			dir.add(new SelectItem("L", localeDirection.getStringKey("directions_east")));   
			dir.add(new SelectItem("O", localeDirection.getStringKey("directions_west")));   

			dmsType.add(new SelectItem(1, localeMap.getStringKey("$label_map_dms_type_1")));   
			dmsType.add(new SelectItem(2, localeMap.getStringKey("$label_map_dms_type_2")));   
			dmsType.add(new SelectItem(3, localeMap.getStringKey("$label_map_dms_type_3"))); 

			meteoType.add(new SelectItem("MTO", localeMap.getStringKey("$label_map_meteo_weather_station"))); 
			meteoType.add(new SelectItem("SV", localeMap.getStringKey("$label_map_meteo_various_sensors"))); 

		}catch(Exception ex){
			ex.printStackTrace();
		}

	}

	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * Método para salvar novos equipamentos na base de dados
	 * de acordo com seu respectivo módulo
	 * @author Wellington 25/12/2021
	 * @version 1.0
	 * @since 1.0
	 * @throws Exception
	 */
	
	public void saveEquipment() throws Exception {
				
		DateTimeApplication dta = new DateTimeApplication(); // DATETIME APPLICATION OBJ			
		EquipmentsDAO equipDAO = new EquipmentsDAO(); // EQUIPMENT DAO
		
		checked = false; // VARIÁVEL PARA VERFICAR OPERAÇÕES AO SALVAR NOVO EQUIPAMENTO	
		
		EquipmentDataSource dataSource = new EquipmentDataSource();
				
		// ------------------------------------------------------------------------------------------------------

		Map<String, String> parameterMap = SessionUtil.getRequestParameterMap(); // OBTER PARAMETRÔS EXTERNOS		 			
		
		dataSource.setModuleID(parameterMap.get("equips") == "" ? 0 : Integer.parseInt(parameterMap.get("equips"))); // GET MODULE ID
	
		dataSource.setEquipId(parameterMap.get("equipId") == null ? 0 : Integer.parseInt(parameterMap.get("equipId"))); 	// GET EQUIP ID
				
		dataSource.setTable(defineTableById(dataSource.getModuleID())); // GET TABLE MODULE BY MODULE ID
				
		dataSource.setDatetime(dta.currentTimeDBformat()); // CREATION DATE
		
		dataSource.setUsername((String) SessionUtil.getParam("user"));  // CREATION USERNAME	
			
		dataSource.setEquipName(parameterMap.get("equipName")); // NAME
	
		dataSource.setIpAddress(parameterMap.get("equipIp")); 	// IP Address
						
		dataSource.setCity(parameterMap.get("cities")); 	// CITY
		
		dataSource.setRoad(parameterMap.get("roads")); // ROAD
		
		if(!dataSource.getTable().equals("meteo"))
			dataSource.setEquipType(defineEquipType(dataSource.getTable())); // TYPE
										
		dataSource.setKm(parameterMap.get("km")); // KM
				
		dataSource.setDirection(parameterMap.get("direction")); // DIRECTION	
		
		dataSource.setLatitude(parameterMap.get("lat") == "" ? 0 : Double.parseDouble(parameterMap.get("lat"))); // LATITUDE

		dataSource.setLongitude(parameterMap.get("long") == "" ? 0 : Double.parseDouble(parameterMap.get("long"))); // LONGITUDE
		
		// ----------------------------------------------------------------------------------------------------------------------
				
		// DMS
		
		if(dataSource.getTable().equals("pmv"))
			dataSource.setDmsDriver(parameterMap.get("dmsType") == "" ? 1 : Integer.parseInt(parameterMap.get("dmsType"))); // DMS DRIVER
				
		// ----------------------------------------------------------------------------------------------------------------------
		
		// SOS 
		
		if(dataSource.getTable().equals("sos")) {
			dataSource.setPort(parameterMap.get("equipPort") == "" ? 0 : Integer.parseInt(parameterMap.get("equipPort"))); // PORT 
			dataSource.setModel(parameterMap.get("model") == "" ? 0 : Integer.parseInt(parameterMap.get("model"))); // SOS MODEL
			dataSource.setSip(parameterMap.get("sip")); // SOS SIP			
		}
	
		// ----------------------------------------------------------------------------------------------------------------------
		
		// SPEED 
		
		if(dataSource.getTable().equals("speed")) {			
			dataSource.setIpAddressIndicator(parameterMap.get("indicator-equipIp")); // IP INDICATOR			
			dataSource.setIpAddressRadar(parameterMap.get("radar-equipIp")); 	// IP RADAR 		
		}
		
		// METEO
		
		if(dataSource.getTable().equals("meteo")) {		
			dataSource.setPort(parameterMap.get("equipPort") == "" ? 0 : Integer.parseInt(parameterMap.get("equipPort"))); 	// PORT 
			dataSource.setConfigId(parameterMap.get("configId") == "" ? 0 : Integer.parseInt(parameterMap.get("configId"))); // METEO CONFIG ID
			dataSource.setEquipType(parameterMap.get("meteoType") == "" ? "MTO" : parameterMap.get("meteoType")); // METEO TYPE			
		}
		 							
		// ----------------------------------------------------------------------------------------------------------------------
		
		// SAT 
		
		if(dataSource.getTable().equals("sat")) {	
			
			dataSource.setNumLanes(parameterMap.get("lanes") == "" ? 2 : Integer.parseInt(parameterMap.get("lanes"))); // NUMBER LANES
		
			// LANE VALUE
		
			dataSource.setLane1(satDirection(parameterMap.get("direction1") == "" ? 0 : Integer.parseInt(parameterMap.get("direction1")), 1));
			dataSource.setLane2(satDirection(parameterMap.get("direction2") == "" ? 0 : Integer.parseInt(parameterMap.get("direction2")), 2));
			dataSource.setLane3(satDirection(parameterMap.get("direction3") == "" ? 0 : Integer.parseInt(parameterMap.get("direction3")), 3));
			dataSource.setLane4(satDirection(parameterMap.get("direction4") == "" ? 0 : Integer.parseInt(parameterMap.get("direction4")), 4));
			dataSource.setLane5(satDirection(parameterMap.get("direction5") == "" ? 0 : Integer.parseInt(parameterMap.get("direction5")), 5));
			dataSource.setLane6(satDirection(parameterMap.get("direction6") == "" ? 0 : Integer.parseInt(parameterMap.get("direction6")), 6));
			dataSource.setLane7(satDirection(parameterMap.get("direction7") == "" ? 0 : Integer.parseInt(parameterMap.get("direction7")), 7));
			dataSource.setLane8(satDirection(parameterMap.get("direction8") == "" ? 0 : Integer.parseInt(parameterMap.get("direction8")), 8));

		}
		
		// -----------------------------------------------------------------------------------------------------------------
				
		checked =  equipDAO.checkExists(dataSource.getEquipId(), dataSource.getTable()); // CHECK IF ID ALREADY EXISTS

			if(checked)
				SessionUtil.executeScript("alertOptions('#warn', '"+localeMap.getStringKey("$message_map_alert_error_saving_equipment_exists")+"');");

			else {

				checked = equipDAO.saveEquipment(dataSource);
					
				if(checked) {
					SessionUtil.executeScript("alertOptions('#success', '"+localeMap.getStringKey("$message_map_alert_saved_equipment")+"');");
					SessionUtil.executeScript("updated = '" + dataSource.getTable() + parameterMap.get("equipId") + "';");
				}

				else SessionUtil.executeScript("alertOptions('#error', '"+localeMap.getStringKey("$message_map_alert_error_saving_equipment")+"');");

			}		
	}
		
   //--------------------------------------------------------------------------------------------------------------

	/**
	 * Método para pesquisar equipamentos
	 * @author Wellington 25/12/2021
	 * @version 1.0
	 * @since Release 1.0		
	 * @throws Exception
	 */

	public void searchEquipment() throws Exception {

		Map<String, String> parameterMap = SessionUtil.getRequestParameterMap();
		
		String interfaceView = parameterMap.get("interface-search"); // MAP INTERFACES		   		

		int equipId = getEquipId();		 
		String equipTable = getEquipTable();
		
		EquipmentsDAO dao = new EquipmentsDAO();
	    EquipmentDataSource dataSource = new EquipmentDataSource();
			
		dataSource = dao.searchEquipament(equipId, equipTable, interfaceView, login.getLogin().getPermission_id()); 

				SessionUtil.executeScript("$('#equips-edit').val('"+getModuleByName(equipTable)+"');");				
				SessionUtil.executeScript("$('#equipId-edit').val('"+dataSource.getEquipId()+"');");
				SessionUtil.executeScript("$('#equipIp-edit').val('"+dataSource.getIpAddress()+"');");
				SessionUtil.executeScript("$('#equipNameEdit').val('"+dataSource.getEquipName()+"');");	
				SessionUtil.executeScript("$('#citiesEdit').val('"+dataSource.getCity()+"');");
				SessionUtil.executeScript("$('#roadsEdit').val('"+dataSource.getRoad()+"');");
				SessionUtil.executeScript("$('#kmEdit').val('"+dataSource.getKm()+"');");	
				SessionUtil.executeScript("$('#width-edit').val('"+dataSource.getWidth()+"');");			
				SessionUtil.executeScript("$('#latEdit').val('"+dataSource.getLatitude()+"');");	
				SessionUtil.executeScript("$('#longEdit').val('"+dataSource.getLongitude()+"');");
				SessionUtil.executeScript("$('#direction-edit').val('"+dataSource.getDirection()+"');");
				
			 if(equipTable.equals("meteo")) {
				 
				 SessionUtil.executeScript("$('#configId-edit').val('"+dataSource.getConfigId()+"');");
				 SessionUtil.executeScript("$('#meteoType-edit').val('"+ dataSource.getEquipType()+"');");
				 SessionUtil.executeScript("$('#equipPort-edit').val('"+dataSource.getPort()+"');");							   
			 }
			 
			 else if(equipTable.equals("pmv")) 
				 SessionUtil.executeScript("$('#dmsType-edit').val('"+dataSource.getDmsDriver()+"');");
			
			 else if(equipTable.equals("sat")) {
				
				 SessionUtil.executeScript("$('#lanes-edit').val('"+dataSource.getNumLanes()+"');");
				
					if(dataSource.getLane1() != null)			 
						SessionUtil.executeScript("$('#direction1-edit').show(); $('#direction1-edit').val('"+dataSource.getLane1()+"');");	

					if(dataSource.getLane2() != null)			 
						SessionUtil.executeScript("$('#direction2-edit').show(); $('#direction2-edit').val('"+dataSource.getLane2()+"');");	

					if(dataSource.getLane3()!= null)			 
						SessionUtil.executeScript("$('#direction3-edit').show(); $('#direction3-edit').val('"+dataSource.getLane3()+"');");	

					if(dataSource.getLane4() != null)			 
						SessionUtil.executeScript("$('#direction4-edit').show(); $('#direction4-edit').val('"+dataSource.getLane4()+"');");	

					if(dataSource.getLane5() != null)			 
						SessionUtil.executeScript("$('#direction5-edit').show(); $('#direction5-edit').val('"+dataSource.getLane5()+"');");	

					if(dataSource.getLane6() != null)			 
						SessionUtil.executeScript("$('#direction6-edit').show(); $('#direction6-edit').val('"+dataSource.getLane6()+"');");	

					if(dataSource.getLane7() != null)			 
						SessionUtil.executeScript("$('#direction7-edit').show(); $('#direction7-edit').val('"+dataSource.getLane7()+"');");	

					if(dataSource.getLane8() != null)			 
						SessionUtil.executeScript("$('#direction8-edit').show(); $('#direction8-edit').val('"+dataSource.getLane8()+"');");		
																		 
			 }
			 
			 else if(equipTable.equals("sos")) {
				      SessionUtil.executeScript("$('#equipPort-edit').val('"+dataSource.getPort()+"');");	
					  SessionUtil.executeScript("$('#modelEdit').val('"+dataSource.getModel()+"');");
					  SessionUtil.executeScript("$('#sipEdit').val('"+dataSource.getSip()+"');");
			
			 }
			 
			 else if(equipTable.equals("speed")) {
				 	  SessionUtil.executeScript("$('#indicator-equipIp-edit').val('"+ dataSource.getIpAddressIndicator()+"');");	
					  SessionUtil.executeScript("$('#radar-equipIp-edit').val('"+dataSource.getIpAddressRadar()+"');");		
			
			 }		
	    }

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * Método para executar deleção de equipamentos
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0	
	 * @return void
	 * @throws Exception
	 */

	public void runDel() throws Exception {

		EquipmentsDAO dao = new  EquipmentsDAO();

		int equipId = getEquipId();
		String equipTable = "";

		//CHANGE
		if(getEquipTable().equals("dms"))
			equipTable = "pmv";

		else equipTable = getEquipTable();
		//CHANGE

		String equipName = dao.equipmentName(equipId, equipTable);

		SessionUtil.executeScript("$('#del-equip-name').html('"+equipName+"');");				 	 

	}

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * Método para atualizar equipamentos
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0	
	 * @return void
	 * @throws Exception
	 */

	public void UpdateEquipment() throws Exception {
		
		DateTimeApplication dta = new DateTimeApplication();

		Map<String, String> parameterMap = SessionUtil.getRequestParameterMap();

		boolean update = false;

		EquipmentsDAO dao = new EquipmentsDAO();

		DMS dms = new DMS();
		SAT sat = new SAT();
		SOS sos = new SOS();
		Speed speed = new Speed();
		METEO meteo = new METEO();
		Equipments equip = new Equipments();

		int equipId = getEquipId();		 
		String equipTable = getEquipTable();

		//INTERFACES
		String interfaceView = parameterMap.get("interface");		   		

		//CHECK MODULES	
		int moduleId = getModuleByName(equipTable);
				
		if(moduleId != 0 && moduleId == 6) {
						
			//EQUIP TABLE BY MODULE
			String table = defineTableById(moduleId);
			
			//For Equipment ID
			meteo.setEquip_id(equipId);			
						
			//For Equipment CreationDate
			meteo.setCreation_date(dta.currentTimeDBformat());
			
			//For Equipment CreationUsername		
			meteo.setCreation_username((String) SessionUtil.getParam("user")); 
					
			meteo.setEquip_type(parameterMap.get("meteoType-edit"));
			
			//For Equipment Name
			meteo.setNome(parameterMap.get("equipNameEdit"));
			
			//EQUIP IP
			meteo.setEquip_ip(parameterMap.get("equipIp-edit"));
			
			//For Equipment City
			meteo.setCidade(parameterMap.get("citiesEdit"));
			
			//For Equipment Road
			meteo.setEstrada(parameterMap.get("roadsEdit"));
			
			//For Equipment KM
			meteo.setKm(parameterMap.get("kmEdit"));
			
			//For Equipment Direction
			meteo.setDirection(parameterMap.get("direction-edit"));
					
			//For Equipment latitude
			meteo.setLatitude(Double.parseDouble(parameterMap.get("latEdit")));
			
			//For Equipment KM
			meteo.setLongitude(Double.parseDouble(parameterMap.get("longEdit")));	
			
			//For Equipment Port
			meteo.setPort(Integer.parseInt(parameterMap.get("equipPort-edit")));
			
			//For Equipment Map Width / Linear Width			    			    
			if(parameterMap.get("width-edit") == "0")
				meteo.setMapWidth(1);			    

			else meteo.setMapWidth(parameterMap.get("width-edit") == "" ? 1 : Integer.parseInt(parameterMap.get("width-edit")));
			
			update = dao.EquipMETEOUpdateMap(meteo, table, interfaceView, login.getLogin().getPermission_id());
		
			if(update) {
				SessionUtil.executeScript("alertOptions('#success', '"+localeMap.getStringKey("$message_map_alert_updated_equipment")+"');");
				SessionUtil.executeScript("updated = '" + equipTable + equipId + "';");
			} else {
				SessionUtil.executeScript("alertOptions('#error', '"+localeMap.getStringKey("$message_map_alert_error_updating_equipment")+"');");
			}
			
		} else if(moduleId != 0 && moduleId == 8) {		

			//Table definition
			String table = defineTableById(moduleId);

			//For Equipment Update Date
			dms.setUpdate_date(dta.currentTimeDBformat());

			//For Equipment Update Username		
			dms.setUpdate_username((String) SessionUtil.getParam("user"));			

			//DMS ID
			dms.setEquip_id(equipId);

			//For Equipment Name
			dms.setNome(parameterMap.get("equipNameEdit"));

			//For Equipment IP
			dms.setDms_ip(parameterMap.get("equipIp-edit"));

			//For Equipment City
			dms.setCidade(parameterMap.get("citiesEdit"));

			//For Equipment Road
			dms.setEstrada(parameterMap.get("roadsEdit"));

			//For Equipment KM
			dms.setKm(parameterMap.get("kmEdit"));

			//For Equipment Latitude
			dms.setLatitude(Double.parseDouble(parameterMap.get("latEdit")));

			//For Equipment longitude
			dms.setLongitude(Double.parseDouble(parameterMap.get("longEdit")));

			//For Equipment Map Width / Linear Width			    			    
			if(parameterMap.get("width-edit") == "0")
				dms.setMapWidth(1);			    

			else dms.setMapWidth(parameterMap.get("width-edit") == "" ? 1 : Integer.parseInt(parameterMap.get("width-edit")));

			//DMS Type
			int type = (parameterMap.get("dmsType-edit") == null ? 1 : Integer.parseInt(parameterMap.get("dmsType-edit")));

			//DMS TYPE
			defineDMStype(dms, type);

			update = dao.EquipDMSUpdateMap(dms, table, interfaceView, login.getLogin().getPermission_id());

			if(update) {
				SessionUtil.executeScript("alertOptions('#success', '"+localeMap.getStringKey("$message_map_alert_updated_equipment")+"');");
				SessionUtil.executeScript("updated = '" + equipTable + equipId + "';");
			} else {
				SessionUtil.executeScript("alertOptions('#error', '"+localeMap.getStringKey("$message_map_alert_error_updating_equipment")+"');");
			}

		} else if(moduleId != 0 && moduleId == 10) {		

			//Table definition
			String table = defineTableById(moduleId);

			//For Equipment Update Date
			sos.setUpdate_date(dta.currentTimeDBformat());

			//For Equipment Update Username		
			sos.setUpdate_username((String) SessionUtil.getParam("user"));			

			//SOS ID
			sos.setEquip_id(equipId);

			//For Equipment Name
			sos.setNome(parameterMap.get("equipNameEdit"));

			//For Equipment IP
			sos.setEquip_ip(parameterMap.get("equipIp-edit"));

			//For Equipment Port
			sos.setPort(Integer.parseInt(parameterMap.get("equipPort-edit")));

			//For Equipment City
			sos.setCidade(parameterMap.get("citiesEdit"));

			//For Equipment Road
			sos.setEstrada(parameterMap.get("roadsEdit"));

			//For Equipment KM
			sos.setKm(parameterMap.get("kmEdit"));

			//For Equipment Model
			sos.setModel(Integer.parseInt(parameterMap.get("modelEdit")));

			//For Equipment SIP
			sos.setSip(parameterMap.get("sipEdit"));

			//For Equipment latitude
			sos.setLatitude(Double.parseDouble(parameterMap.get("latEdit")));

			//For Equipment longitude
			sos.setLongitude(Double.parseDouble(parameterMap.get("longEdit")));

			//For Equipment Map Width / Linear Width			    			    
			if(parameterMap.get("width-edit") == "0")
				sos.setMapWidth(1);			    

			else sos.setMapWidth(parameterMap.get("width-edit") == "" ? 1 : Integer.parseInt(parameterMap.get("width-edit")));

			update = dao.EquipSOSUpdateMap(sos, table, interfaceView, login.getLogin().getPermission_id());
			
			if(update) {
				SessionUtil.executeScript("alertOptions('#success', '"+localeMap.getStringKey("$message_map_alert_updated_equipment")+"');");
				SessionUtil.executeScript("updated = '" + equipTable + equipId + "';");
			} else {
				SessionUtil.executeScript("alertOptions('#error', '"+localeMap.getStringKey("$message_map_alert_error_updating_equipment")+"');");
			}


		} else if(moduleId != 0 && moduleId == 9) {

			// Table definition
			String table = defineTableById(moduleId);

			//For Equipment Update Date
			sat.setUpdate_date(dta.currentTimeDBformat());

			//For Equipment Update Username		
			sat.setUpdate_username((String) SessionUtil.getParam("user"));		

			//SAT ID
			sat.setEquip_id(equipId);

			//For Equipment Name
			sat.setNome(parameterMap.get("equipNameEdit"));

			//For Equipment IP
			sat.setEquip_ip(parameterMap.get("equipIp-edit"));

			//For Equipment City
			sat.setCidade(parameterMap.get("citiesEdit"));

			//For Equipment Road
			sat.setEstrada(parameterMap.get("roadsEdit"));

			//For Equipment KM
			sat.setKm(parameterMap.get("kmEdit"));

			//For Equipment latitude
			sat.setLatitude(Double.parseDouble(parameterMap.get("latEdit")));

			//For Equipment longitude
			sat.setLongitude(Double.parseDouble(parameterMap.get("longEdit")));

			//For Equipment Map Width / Linear Width			    			    
			if(parameterMap.get("width-edit") == "0")
				sat.setMapWidth(1);	    

			else sat.setMapWidth(parameterMap.get("width-edit") == "" ? 1 : Integer.parseInt(parameterMap.get("width-edit")));

			//For Number Lanes
			int numLanes = (parameterMap.get("lanes-edit") == "" ? 0 : Integer.parseInt(parameterMap.get("lanes-edit")));

			if(numLanes > 0)
				sat.setNumFaixas(numLanes);

			else sat.setNumFaixas(2);

			// SET LANES DEFINITION
			defineDirection(sat, 1, parameterMap.get("direction1-edit") == "" ? 0 : Integer.parseInt(parameterMap.get("direction1-edit")));
			defineDirection(sat, 2, parameterMap.get("direction2-edit") == "" ? 0 : Integer.parseInt(parameterMap.get("direction2-edit")));
			defineDirection(sat, 3, parameterMap.get("direction3-edit") == "" ? 0 : Integer.parseInt(parameterMap.get("direction3-edit")));
			defineDirection(sat, 4, parameterMap.get("direction4-edit") == "" ? 0 : Integer.parseInt(parameterMap.get("direction4-edit")));
			defineDirection(sat, 5, parameterMap.get("direction5-edit") == "" ? 0 : Integer.parseInt(parameterMap.get("direction5-edit")));
			defineDirection(sat, 6, parameterMap.get("direction6-edit") == "" ? 0 : Integer.parseInt(parameterMap.get("direction6-edit")));
			defineDirection(sat, 7, parameterMap.get("direction7-edit") == "" ? 0 : Integer.parseInt(parameterMap.get("direction7-edit")));
			defineDirection(sat, 8, parameterMap.get("direction8-edit") == "" ? 0 : Integer.parseInt(parameterMap.get("direction8-edit")));

			update = dao.EquipSATUpdateMap(sat, table, interfaceView, login.getLogin().getPermission_id());

			if(update) {
				SessionUtil.executeScript("alertOptions('#success', '"+localeMap.getStringKey("$message_map_alert_updated_equipment")+"');");
				SessionUtil.executeScript("updated = '" + equipTable + equipId + "';");
			} else {
				SessionUtil.executeScript("alertOptions('#error', '"+localeMap.getStringKey("$message_map_alert_error_updating_equipment")+"');");
			}


		} else if(moduleId != 0 && moduleId == 11) {		

			//Table definition
			String table = defineTableById(moduleId);

			//For Equipment Update Date
			speed.setUpdate_date(dta.currentTimeDBformat());

			//For Equipment Update Username		
			speed.setUpdate_username((String) SessionUtil.getParam("user"));			

			//Speed ID
			speed.setEquip_id(equipId);

			//For Equipment Name
			speed.setNome(parameterMap.get("equipNameEdit"));

			//For Equipment IP
			speed.setEquip_ip_indicator(parameterMap.get("indicator-equipIp-edit"));
			
			//For Equipment IP
			speed.setEquip_ip_radar(parameterMap.get("radar-equipIp-edit"));
			
			//For Equipment City
			speed.setCidade(parameterMap.get("citiesEdit"));

			//For Equipment Road
			speed.setEstrada(parameterMap.get("roadsEdit"));

			//For Equipment KM
			speed.setKm(parameterMap.get("kmEdit"));
			
			//For Equipment latitude
			speed.setLatitude(Double.parseDouble(parameterMap.get("latEdit")));

			//For Equipment longitude
			speed.setLongitude(Double.parseDouble(parameterMap.get("longEdit")));
		
			//For Equipment Map Width / Linear Width			    			    
			if(parameterMap.get("width-edit") == "0")
				speed.setMapWidth(1);			    

			else speed.setMapWidth(parameterMap.get("width-edit") == "" ? 1 : Integer.parseInt(parameterMap.get("width-edit")));

			//update = dao.EquipSpeedUpdateMap(speed, table, interfaceView, login.getLogin().getPermission_id());

			if(update) {
				SessionUtil.executeScript("alertOptions('#success', '"+localeMap.getStringKey("$message_map_alert_updated_equipment")+"');");
				SessionUtil.executeScript("updated = '" + equipTable + equipId + "';");
			} else {
				SessionUtil.executeScript("alertOptions('#error', '"+localeMap.getStringKey("$message_map_alert_error_updating_equipment")+"');");
			}

		}else if((moduleId != 0 && (moduleId != 8 && moduleId != 9 && moduleId != 10 && moduleId != 11))) {

			// Table definition
			String table = defineTableById(moduleId);

			//For Equipment Update Date
			equip.setUpdate_date(dta.currentTimeDBformat());

			//For Equipment Update Username		
			equip.setUpdate_username((String) SessionUtil.getParam("user"));		

			//For Equipment ID
			equip.setEquip_id(equipId);

			//For Equipment Name
			equip.setNome(parameterMap.get("equipNameEdit"));

			//For Equipment IP
			equip.setEquip_ip(parameterMap.get("equipIp-edit"));

			//For Equipment City
			equip.setCidade(parameterMap.get("citiesEdit"));

			//For Equipment Road
			equip.setEstrada(parameterMap.get("roadsEdit"));

			//For Equipment KM
			equip.setKm(parameterMap.get("kmEdit"));	
			
			//For Equipment latitude
			equip.setLatitude(Double.parseDouble(parameterMap.get("latEdit")));

			//For Equipment longitude
			equip.setLongitude(Double.parseDouble(parameterMap.get("longEdit")));

			//For Equipment Map Width / Linear Width			    			    
			if(parameterMap.get("width-edit") == "0")
				equip.setMapWidth(1);			    

			else equip.setMapWidth(parameterMap.get("width-edit") == "" ? 1 : Integer.parseInt(parameterMap.get("width-edit")));

			//MENSAGEM UPDATED
			update = dao.EquipUpdateMap(equip, table, interfaceView, login.getLogin().getPermission_id());

			if(update) {
				SessionUtil.executeScript("alertOptions('#success', '"+localeMap.getStringKey("$message_map_alert_updated_equipment")+"');");
				SessionUtil.executeScript("updated = '" + equipTable + equipId + "';");
			} else {
				SessionUtil.executeScript("alertOptions('#error', '"+localeMap.getStringKey("$message_map_alert_error_updating_equipment")+"');");
			}

		}				
	}

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * Método para deleção de equipamentos
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0	
	 * @return void
	 * @throws Exception
	 */
	
	public void DeleteEquipment() throws Exception {

		boolean delete = false;

		int equipId = getEquipId();		 
		String equipTable = getEquipTable();	
	
		EquipmentsDAO dao = new EquipmentsDAO();		

		delete = dao.EquipDeleteMap(equipId, equipTable);

		SessionUtil.executeScript("init();");

		if(delete) {
			SessionUtil.executeScript("alertOptions('#success', '"+localeMap.getStringKey("$message_map_alert_deleted_equipment")+"');");
			SessionUtil.executeScript("updated = '" + equipTable + equipId + "';");
		} else {
			SessionUtil.executeScript("alertOptions('#error', '"+localeMap.getStringKey("$message_map_alert_error_deleting_equipment")+"');");
		}

	}

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * Método para definir posição de equipamentos
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0	
	 * @return void
	 * @throws Exception
	 */	

	public void definePosition() throws Exception {

		boolean position = false;

		Map<String, String> parameterMap = SessionUtil.getRequestParameterMap();

		//INTERFACES
		String interfaceView = parameterMap.get("positionView");		

		int equipId = getEquipId();		
		int posX = getPositionX(); // MAP / LINEAR
		int posY = getPositionY(); // MAP / LINEAR 
		String equipTable = getEquipTable();

		// System.out.println("EQUIP: "+equipId);
		////System.out.println("TABLE: "+equipTable);
		/// System.out.println("X: "+posX);
		// System.out.println("Y: "+posY);

		EquipmentsDAO dao = new EquipmentsDAO();		

		position = dao.EquipPositionMap(equipId, equipTable, posX, posY, interfaceView, login.getLogin().getPermission_id());
		
		if(position) {
			SessionUtil.executeScript("alertOptions('#success', '"+localeMap.getStringKey("$message_map_alert_position_success")+"');");
			SessionUtil.executeScript("updated = '" + equipTable + equipId + "';");
		} else {
			SessionUtil.executeScript("alertOptions('#error', '"+localeMap.getStringKey("$message_map_alert_position_cancel")+"');");
		}

	}

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * Método para definir tamanho de um grupo de equipamentos
	 * @author Guilherme
	 * @version 1.0
	 * @since Release 1.0	
	 * @param map - Interface a ser atualizada
	 * @return void
	 * @throws Exception
	 */	

	public void setAll(String map) throws Exception {

		EquipmentsDAO dao = new EquipmentsDAO();
		Map<String, String> params = SessionUtil.getRequestParameterMap();

		String w = params.get("width-edit");

		String module = getEquipTable();
		int width = w == "" ? 100 : Integer.parseInt(w);

		dao.setWidthMap(module.equals("dms") ? "pmv" : module , map, width);

		SessionUtil.executeScript("alertToast('"+localeMap.getStringKey("$message_map_option_all_equipment_width_updated")+"');");
	}

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * Método para obter o tipo da tabela pelo id
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param id - ID da tabela	
	 * @return String - Tabela de acordo com id
	 * @throws Exception
	 */

	public String defineTableById(int id) { 

		String table = null;

		switch(id) {

		case 1: table="cftv"   ; break;
		case 2: table="colas"  ; break;
		case 3: table="comms"  ; break;
		case 4: table="dai"    ; break;
		case 5: table="ocr"    ; break;
		case 6: table="meteo"    ; break;
		case 8: table="pmv"    ; break;	
		case 9: table="sat"    ; break;
		case 10: table="sos"    ; break;
		case 11: table="speed"  ; break;
		case 12: table="wim"    ; break;
		
		}

		return table;
	}

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * Método para obter a tabela pelo tipo do módulo
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param type- Tipo da tabela	
	 * @return String - Tabela definida
	 * @throws Exception
	 */

	public String defineTableByName(String type) { 

		String table = null;

		switch(type) {

		case "cftv": table="cftv"    ; break;
		case "colas": table="colas"  ; break;
		case"comms": table="comms"   ; break;
		case "dai": table="dai"      ; break;
		case "ocr": table="ocr"      ; break;
		case "mto": table="mto"      ; break;
		case "dms": table="pmv"      ; break;	
		case "sat": table="sat"      ; break;
		case "sos": table="sos"       ; break;
		case "speed": table="speed"   ; break;
		case "sv": table="sv"         ; break;
		case "wim": table="wim"       ; break;
		}

		return table;
	}

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * Método para obter o ID do módulo pelo tipo
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param type - Tipo do módulo
	 * @return int - Módulo ID
	 * @throws Exception
	 */

	public int getModuleByName(String type) { 

		int moduleId = 0;

		switch(type) {

		case "cftv": moduleId = 1 ; break;
		case "colas": moduleId = 2 ; break;
		case "comms": moduleId = 3 ; break;
		case "dai": moduleId = 4 ; break;
		case "ocr": moduleId = 5 ; break;
		case "meteo": moduleId = 6 ; break;
		case "dms": moduleId = 8 ; break;  	
		case "sat": moduleId = 9 ; break;
		case "sos": moduleId = 10  ; break;
		case "speed": moduleId = 11 ; break;	
		case "wim" : moduleId = 12 ; break;
		}

		return moduleId;
	}

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * Método para definir direções
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param sat - Objeto do tipo SAT
	 * @param numberLane - Número de linhas
	 * @param dir - Direção
	 * @return void   	
	 */

	public void defineDirection(SAT sat, int numberLane, int dir){

		switch(dir) {

		case 0: 

			switch (numberLane) { 

			case 1: sat.setFaixa1("N"); break;
			case 2: sat.setFaixa2("S"); break;
			case 3: sat.setFaixa3(null); break;
			case 4: sat.setFaixa4(null); break;
			case 5: sat.setFaixa5(null); break;
			case 6: sat.setFaixa6(null); break;
			case 7: sat.setFaixa7(null); break;
			case 8: sat.setFaixa8(null); break;

			}; break;

		case 1: 

			switch (numberLane) { 

			case 1: sat.setFaixa1("N"); break;
			case 2: sat.setFaixa2("N"); break;
			case 3: sat.setFaixa3("N"); break;
			case 4: sat.setFaixa4("N"); break;
			case 5: sat.setFaixa5("N"); break;
			case 6: sat.setFaixa6("N"); break;
			case 7: sat.setFaixa7("N"); break;
			case 8: sat.setFaixa8("N"); break;

			}; break;

		case 2: 

			switch (numberLane) { 

			case 1: sat.setFaixa1("S"); break;
			case 2: sat.setFaixa2("S"); break;
			case 3: sat.setFaixa3("S"); break;
			case 4: sat.setFaixa4("S"); break;
			case 5: sat.setFaixa5("S"); break;
			case 6: sat.setFaixa6("S"); break;
			case 7: sat.setFaixa7("S"); break;
			case 8: sat.setFaixa8("S"); break;

			}; break;	

		case 3: 	

			switch (numberLane) { 

			case 1: sat.setFaixa1("L"); break;
			case 2: sat.setFaixa2("L"); break;
			case 3: sat.setFaixa3("L"); break;
			case 4: sat.setFaixa4("L"); break;
			case 5: sat.setFaixa5("L"); break;
			case 6: sat.setFaixa6("L"); break;
			case 7: sat.setFaixa7("L"); break;
			case 8: sat.setFaixa8("L"); break;

			}; break;


		case 4: 

			switch (numberLane) { 

			case 1: sat.setFaixa1("O"); break;
			case 2: sat.setFaixa2("O"); break;
			case 3: sat.setFaixa3("O"); break;
			case 4: sat.setFaixa4("O"); break;
			case 5: sat.setFaixa5("O"); break;
			case 6: sat.setFaixa6("O"); break;
			case 7: sat.setFaixa7("O"); break;
			case 8: sat.setFaixa8("O"); break;

			}; break;				 	
		}
	}

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * Método para obter o tipo do PMV
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param dms - Objeto do tipo DMS
	 * @param type - Tipo do PMV
	 * @return void   
	 */

	public void defineDMStype(DMS dms, int type){

		switch (type) {     		

		case 1: dms.setDms_type(1); break;
		case 2: dms.setDms_type(2); break;
		case 3: dms.setDms_type(3); break;					

		}
	}

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * Método para obter tipo do equipamento
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0   	
	 * @param table - Tabela   	
	 * @return String - Retorna o tipo de equipamento 	
	 */

	public String defineEquipType(String table) { 

		String type = null;

			switch(table) {
	
				case "cftv": type = ModulesEnum.CFTV.getModule(); break;
				case "colas": type = ModulesEnum.COLAS.getModule(); break;
				case"comms": type = ModulesEnum.COMMS.getModule(); break;
				case "dai": type = ModulesEnum.DAI.getModule(); break;				
				case "ocr": type = ModulesEnum.OCR.getModule(); break;
				case "dms": type = ModulesEnum.PMV.getModule(); break;
				case "sat": type = ModulesEnum.SAT.getModule(); break;
				case "sos": type = ModulesEnum.SOS.getModule(); break;
				case "speed": type = ModulesEnum.SPEED.getModule(); break;
				case "wim": type = ModulesEnum.WIM.getModule(); break;
	
			}

		return type;
	}

	//--------------------------------------------------------------------------------------------------------------
	// GENERIC
	//--------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * Método para obter informações de um equipamento do tipo GENÉRICO
	 * @author Wellington 20/10/2021
	 * @version 1.0
	 * @since 1.0   	
	 * @param module - módulo do equipamento
	 * @param id - ID do equipamento
	 * @return matriz com informações dos equipamentos
	 */
	public String[] genericInfo(String module, String id) {
		
		EquipmentsDAO dao = new EquipmentsDAO();
	
		String[] info = new String[4];
		
		try {
			info = dao.genericInfo(module, id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return info;
				
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * Método para obter informações de vários equipamentos do tipo GENERIC
	 * @author Wellington 20/10/2021
	 * @version 1.0
	 * @since 1.0   	
	 * @param module - módulo do equipamento
	 * @param id - matriz com ID dos equipamentos
	 * @return matriz com informações dos equipamentos
	 */
    public String[][] multiGenericInfo(String module, String[] id) {
		
		EquipmentsDAO dao = new EquipmentsDAO();
		
		// INFO TWO-DIMENSIONAL ARRAY 
		String[][] info = new String[id.length][4];	
		
		try {
			
			info = dao.multiGenericInfo(id, module);
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
				
		return info;
				
	}
	
	//--------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------
	// SAT
	//--------------------------------------------------------------------------------------------------------------
    /**
	 * Método para obter informações de um equipamento do tipo SAT
	 * @author Wellington 20/10/2021
	 * @version 1.0
	 * @since 1.0 
	 * @param id - ID do equipamento
	 * @return matriz com informações dos equipamentos
	 */
     public String[] satInfo(String id) {
		
		EquipmentsDAO dao = new EquipmentsDAO();
		
		String[] info = new String[5];
		
		try {
			info = dao.satInfo(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return info;
				
	}
	
	//--------------------------------------------------------------------------------------------------------------
     
     /**
 	 * Método para obter informações de vários equipamentos do tipo SAT
 	 * @author Wellington 20/10/2021
 	 * @version 1.0
 	 * @since 1.0	
 	 * @param id - matriz com ID dos equipamentos
 	 * @return matriz com informações dos equipamentos
 	 */
     public String[][] multiSatInfo(String[] id) {
 		
 		EquipmentsDAO dao = new EquipmentsDAO();
 		
 		// INFO TWO-DIMENSIONAL ARRAY 
		String[][] info = new String[id.length][4];		
 		
 		try {
 			
 			info = dao.multiSatInfo(id);
 			
 		} catch (Exception e) {			
 			e.printStackTrace();
 		}
 				
 		return info;
 				
 	}
 	
 	//--------------------------------------------------------------------------------------------------------------
 	//--------------------------------------------------------------------------------------------------------------
 	// SOS
 	//--------------------------------------------------------------------------------------------------------------
     
     /**
 	 * Método para obter informações de um equipamento do tipo SOS
 	 * @author Wellington 20/10/2021
 	 * @version 1.0
 	 * @since 1.0
 	 * @param id - ID do equipamento
 	 * @return matriz com informações dos equipamentos
 	 */
     public String[] sosInfo(String id) {
 		
		EquipmentsDAO dao = new EquipmentsDAO();
		
		String[] info = new String[4];
		
		try {
			info = dao.sosInfo(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return info;
				
	}
	
	//--------------------------------------------------------------------------------------------------------------
     
     /**
  	 * Método para obter informações de vários equipamentos do tipo SOS
  	 * @author Wellington 20/10/2021
  	 * @version 1.0
  	 * @since 1.0	
  	 * @param id - matriz com ID dos equipamentos
  	 * @return matriz com informações dos equipamentos
  	 */
     public String[][] multiSosInfo(String[] id) {
 		
 		EquipmentsDAO dao = new EquipmentsDAO();
 	  
 		// INFO TWO-DIMENSIONAL ARRAY 
 		String[][] info = new String[id.length][3];		
 		
 		try {
 			
 			info = dao.multiSosInfo(id);
 			
 		} catch (Exception e) {			
 			e.printStackTrace();
 		}
 				
 		return info;
 				
 	}
 	
 	//--------------------------------------------------------------------------------------------------------------
 	//--------------------------------------------------------------------------------------------------------------
 	// DMS
 	//--------------------------------------------------------------------------------------------------------------
         
     /**
 	 * Método para obter informações de um equipamento do tipo DMS
 	 * @author Wellington 20/10/2021
 	 * @version 1.0
 	 * @since 1.0
 	 * @param id - ID do equipamento
 	 * @return matriz com informações dos equipamentos
 	 */
     public String[] dmsInfo(String id) {
    	 
    	String[] info = new String[4];
 		
		EquipmentsDAO dao = new EquipmentsDAO();
		
		try {
			info = dao.dmsInfo(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return info;
				
	}
	
	//--------------------------------------------------------------------------------------------------------------
     
     /**
  	 * Método para obter informações de vários equipamentos do tipo DMS
  	 * @author Wellington 20/10/2021
  	 * @version 1.0
  	 * @since 1.0	
  	 * @param id - matriz com ID dos equipamentos
  	 * @return matriz com informações dos equipamentos
  	 */
     public String[][] multiDmsInfo(String[] id) {
  		
  		EquipmentsDAO dao = new EquipmentsDAO();
  		
  	    // INFO TWO-DIMENSIONAL ARRAY 
  	 	String[][] info = new String[id.length][3];	
  		
  		try {
  			
  			info = dao.multiDmsInfo(id);
  			
  		} catch (Exception e) {			
  			e.printStackTrace();
  		}
  				
  		return info;
  				
  	}
  	
  	//--------------------------------------------------------------------------------------------------------------
     		
	   public SAT satHeaderInformation(String id) {
		   
		   SAT sat = new SAT();
		   EquipmentsDAO dao = new EquipmentsDAO();
		   
		   try {
			   
			sat = dao.headerInfoSAT(id);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   		   
		   return sat;
		   		   
	   }
	   	   
		//--------------------------------------------------------------------------------------------------------------

		/**
		 * Método para definir direções
		 * @author Wellington
		 * @version 1.0
		 * @since Release 1.0
		 * @param sat - Objeto do tipo SAT
		 * @param numberLane - Número de linhas
		 * @param dir - Direção
		 * @return void   	
		 */

		public String satDirection(int dir, int numberLane){
			
			String lane = "";

			switch(dir) {

			case 0: 

				switch (numberLane) { 

					case 1: lane = "N"; break;
					case 2: lane = "S"; break;
					case 3: lane = null; break;
					case 4: lane = null; break;
					case 5: lane = null; break;
					case 6: lane = null; break;
					case 7: lane = null; break;
					case 8: lane = null; break;

				}; break;

			case 1: 

				switch (numberLane) { 

					case 1: lane = "N"; break;
					case 2: lane = "N"; break;
					case 3: lane = "N"; break;
					case 4: lane = "N"; break;
					case 5: lane = "N"; break;
					case 6: lane = "N"; break;
					case 7: lane = "N"; break;
					case 8: lane = "N"; break;

				}; break;

			case 2: 

				switch (numberLane) { 
	
					case 1: lane = "S"; break;
					case 2: lane = "S"; break;
					case 3: lane = "S"; break;
					case 4: lane = "S"; break;
					case 5: lane = "S"; break;
					case 6: lane = "S"; break;
					case 7: lane = "S"; break;
					case 8: lane = "S"; break;
				
				}; break;	

			case 3: 	

				switch (numberLane) { 
	
					case 1: lane = "L"; break;
					case 2: lane = "L"; break;
					case 3: lane = "L"; break;
					case 4: lane = "L"; break;
					case 5: lane = "L"; break;
					case 6: lane = "L"; break;
					case 7: lane = "L"; break;
					case 8: lane = "L"; break;
				
				}; break;


			case 4: 

				switch (numberLane) { 

					case 1: lane = "O"; break;
					case 2: lane = "O"; break;
					case 3: lane = "O"; break;
					case 4: lane = "O"; break;
					case 5: lane = "O"; break;
					case 6: lane = "O"; break;
					case 7: lane = "O"; break;
					case 8: lane = "O"; break;
				
				}; break;				 	
			}
			
			return lane;
		}

	//--------------------------------------------------------------------------------------------------------------
	   
       
}
