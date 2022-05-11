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

import org.primefaces.context.RequestContext;

import br.com.tracevia.webapp.cfg.ModulesEnum;
import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.global.RoadConcessionaireDAO;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.dms.DMS;
import br.com.tracevia.webapp.model.global.EquipmentDataSource;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.sat.SAT;
import br.com.tracevia.webapp.model.speed.Speed;
import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.SessionUtil;

@ManagedBean(name="equipsBean")
@RequestScoped
public class EquipmentsBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<SelectItem> cities, roads, module, lanes, dir, serviceLevel, dmsType, meteoType, directionTo;
	
	private String imgControle = "";

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
	
	String interfaceView;

	public int getid() {
		return id;
	}

	public void setid(int id) {
		this.id = id;
	}

	@ManagedProperty("#{loginAccount}")
	private LoginAccountBean login;

	public LoginAccountBean getLogin() {
		return login;
	}

	public void setLogin(LoginAccountBean login) {
		this.login = login;
	}

	public List<SelectItem> getCities() {
		return cities;
	}

	public List<SelectItem> getRoads() {
		return roads;
	}
	
	public List<SelectItem> getModule() {
		return module;
	}

	public List<SelectItem> getLanes() {
		return lanes;
	}

	public List<SelectItem> getDir() {
		return dir;
	}

	public List<SelectItem> getDmsType() {
		return dmsType;
	}

	public List<SelectItem> getMeteoType() {
		return meteoType;
	}
		
	public List<SelectItem> getServiceLevel() {
		return serviceLevel;
	}
	
	public List<SelectItem> getDirectionTo() {
		return directionTo;
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
	
	
	public void defineInterfaceView(String interfaceView) {
		this.interfaceView = interfaceView;
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
		serviceLevel = new ArrayList<SelectItem>();
		directionTo = new ArrayList<SelectItem>();
		
		try {

			concessionaireDao = new RoadConcessionaireDAO();

			cities = concessionaireDao.cityDefinitions();
			roads = concessionaireDao.roadDefinitions();
			module = concessionaireDao.moduleDefinitions();
			directionTo = concessionaireDao.cityDirectionDefinitions();

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
			
			serviceLevel.add(new SelectItem("horizontal", "Horizontal")); 
			serviceLevel.add(new SelectItem("vertical", "Vertical")); 

		}catch(Exception ex){
			ex.printStackTrace();
		}

	}

	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * MÃ©todo para salvar novos equipamentos na base de dados
	 * de acordo com seu respectivo mÃ³dulo
	 * @author Wellington 25/12/2021
	 * @version 1.0
	 * @since 1.0
	 * @throws Exception
	 */
	
	public void saveEquipment() throws Exception {
				
		DateTimeApplication dta = new DateTimeApplication(); // DATETIME APPLICATION OBJ			
		EquipmentsDAO equipDAO = new EquipmentsDAO(); // EQUIPMENT DAO
		
		checked = false; // VARIÃ�VEL PARA VERFICAR OPERAÃ‡Ã•ES AO SALVAR NOVO EQUIPAMENTO	
		
		EquipmentDataSource dataSource = new EquipmentDataSource();
				
		// ------------------------------------------------------------------------------------------------------

		Map<String, String> parameterMap = SessionUtil.getRequestParameterMap(); // OBTER PARÃ‚METROS EXTERNOS		 			
		
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
		
		if(dataSource.getTable().equals("colas") || dataSource.getTable().equals("dai"))
			dataSource.setDirectionTo(parameterMap.get("directionTo") == "" ? "1" : parameterMap.get("directionTo")); // DIRECTION	
		
		dataSource.setLatitude(parameterMap.get("lat") == "" ? 0 : Double.parseDouble(parameterMap.get("lat"))); // LATITUDE

		dataSource.setLongitude(parameterMap.get("long") == "" ? 0 : Double.parseDouble(parameterMap.get("long"))); // LONGITUDE
		
		// ----------------------------------------------------------------------------------------------------------------------
				
		// DMS
		
		if(dataSource.getTable().equals("dms"))
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
		
		// Meteo
		
		if(dataSource.getTable().equals("meteo")) {		
			dataSource.setPort(parameterMap.get("equipPort") == "" ? 0 : Integer.parseInt(parameterMap.get("equipPort"))); 	// PORT 
			dataSource.setConfigId(parameterMap.get("configId") == "" ? 0 : Integer.parseInt(parameterMap.get("configId"))); // Meteo CONFIG ID
			dataSource.setEquipType(parameterMap.get("meteoType") == "" ? "MTO" : parameterMap.get("meteoType")); // Meteo TYPE			
		}
		 							
		// ----------------------------------------------------------------------------------------------------------------------
		
		// SAT 
		
		if(dataSource.getTable().equals("sat")) {	
			
			dataSource.setServiceFlow(parameterMap.get("service") == "" ? "horizontal" : parameterMap.get("service")); // SERVICE FLOW
			
			dataSource.setNumLanes(parameterMap.get("lanes") == "" ? 2 : Integer.parseInt(parameterMap.get("lanes"))); // NUMBER LANES
		
			// LANE VALUE
		
			dataSource.setLane1(parameterMap.get("direction1") == "" ? "N" : parameterMap.get("direction1"));
			dataSource.setLane2(parameterMap.get("direction2") == "" ? "S" : parameterMap.get("direction2"));
			dataSource.setLane3(parameterMap.get("direction3") == "" ? null : parameterMap.get("direction3"));
			dataSource.setLane4(parameterMap.get("direction4") == "" ? null : parameterMap.get("direction4"));
			dataSource.setLane5(parameterMap.get("direction5") == "" ? null : parameterMap.get("direction5"));
			dataSource.setLane6(parameterMap.get("direction6") == "" ? null : parameterMap.get("direction6"));
			dataSource.setLane7(parameterMap.get("direction7") == "" ? null : parameterMap.get("direction7"));
			dataSource.setLane8(parameterMap.get("direction8") == "" ? null : parameterMap.get("direction8"));

		}
		
		// -----------------------------------------------------------------------------------------------------------------
				
		checked =  equipDAO.checkExists(dataSource.getEquipId(), dataSource.getTable()); // CHECK IF ID ALREADY EXISTS

			if(checked)
				SessionUtil.executeScript("alertOptions('#warn', '"+localeMap.getStringKey("$message_map_alert_error_saving_equipment_exists")+"');");

			else {

				checked = equipDAO.saveEquipment(dataSource, interfaceView);
					
				if(checked) {
					SessionUtil.executeScript("alertOptions('#success', '"+localeMap.getStringKey("$message_map_alert_saved_equipment")+"');");
					SessionUtil.executeScript("updated = '" + dataSource.getTable() + parameterMap.get("equipId") + "';");
				}

				else SessionUtil.executeScript("alertOptions('#error', '"+localeMap.getStringKey("$message_map_alert_error_saving_equipment")+"');");

			}		
	}
		
   //--------------------------------------------------------------------------------------------------------------

	/**
	 * MÃ©todo para pesquisar equipamentos
	 * @author Wellington 25/12/2021
	 * @version 1.0
	 * @since Release 1.0		
	 * @throws Exception
	 */

	public void searchEquipment() throws Exception {
			
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
					SessionUtil.executeScript("$('#meteoType-edit').val('"+dataSource.getEquipType()+"');");
					SessionUtil.executeScript("$('#equipPort-edit').val('"+dataSource.getPort()+"');");		
					
				    SessionUtil.getExternalContext().getSessionMap().put("meteoType", dataSource.getEquipType()); 
			 }
			 
			 else if(equipTable.equals("dms")) 
				 SessionUtil.executeScript("$('#dmsType-edit').val('"+dataSource.getDmsDriver()+"');");
			
			 else if(equipTable.equals("sat")) {
				 
				 SessionUtil.executeScript("$('#service-edit').val('"+dataSource.getServiceFlow()+"');"); // SERVICE FLOW
				
				 SessionUtil.executeScript("$('#lanes-edit').val('"+dataSource.getNumLanes()+"');");
				 				
					if(dataSource.getNumLanes() >= 2) {			 
						SessionUtil.executeScript("$('#direction1-edit').show(); $('#direction1-edit').val('"+dataSource.getLane1()+"');");									 
						SessionUtil.executeScript("$('#direction2-edit').show(); $('#direction2-edit').val('"+dataSource.getLane2()+"');");						
					}
					
					if(dataSource.getNumLanes() >= 3)			 
						SessionUtil.executeScript("$('#direction3-edit').show(); $('#direction3-edit').val('"+dataSource.getLane3()+"');");	

					if(dataSource.getNumLanes() >= 4)			 
						SessionUtil.executeScript("$('#direction4-edit').show(); $('#direction4-edit').val('"+dataSource.getLane4()+"');");	

					if(dataSource.getNumLanes() >= 5)			 
						SessionUtil.executeScript("$('#direction5-edit').show(); $('#direction5-edit').val('"+dataSource.getLane5() == null ? "" : dataSource.getLane5()+"');");	

					if(dataSource.getNumLanes() >= 6)			 
						SessionUtil.executeScript("$('#direction6-edit').show(); $('#direction6-edit').val('"+dataSource.getLane6() == null ? "" : dataSource.getLane6()+"');");	

					if(dataSource.getNumLanes() >= 7)			 
						SessionUtil.executeScript("$('#direction7-edit').show(); $('#direction7-edit').val('"+dataSource.getLane7() == null ? "" : dataSource.getLane7()+"');");	

					if(dataSource.getNumLanes() == 8)			 
						SessionUtil.executeScript("$('#direction8-edit').show(); $('#direction8-edit').val('"+dataSource.getLane8() == null ? "" : dataSource.getLane8()+"');");		
																		 
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
				
			 else if(equipTable.equals("colas") || equipTable.equals("dai"))			 	
				  SessionUtil.executeScript("$('#directionTo-edit').val('"+dataSource.getDirectionTo()+"');");		
	    
	}

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * MÃ©todo para executar deleÃ§Ã£o de equipamentos
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
			equipTable = "dms";

		else equipTable = getEquipTable();
		//CHANGE

		String equipName = dao.equipmentName(equipId, equipTable);

		SessionUtil.executeScript("$('#del-equip-name').html('"+equipName+"');");				 	 
		SessionUtil.executeScript("clearLines();");				 	 

	}

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * MÃ©todo para atualizar equipamentos
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0	
	 * @return void
	 * @throws Exception
	 */
	public void updateEquipment() throws Exception {
		
		DateTimeApplication dta = new DateTimeApplication(); // DATETIME APPLICATION OBJ			
		EquipmentsDAO dao = new EquipmentsDAO(); // EQUIPMENT DAO
		
		checked = false; // VARIÃ�VEL PARA VERFICAR OPERAÃ‡Ã•ES AO SALVAR NOVO EQUIPAMENTO	
		
		int equipId = getEquipId();		 
		String equipTable = getEquipTable();
			
		EquipmentDataSource dataSource = new EquipmentDataSource();
				
		// ------------------------------------------------------------------------------------------------------
		
        Map<String, String> parameterMap = SessionUtil.getRequestParameterMap(); // OBTER PARÃ‚METROS EXTERNOS		 	
               		
		dataSource.setModuleID(getModuleByName(equipTable)); // GET MODULE ID
	
		dataSource.setEquipId(equipId); // GET EQUIP ID
				
		dataSource.setTable(defineTableById(dataSource.getModuleID())); // GET TABLE MODULE BY MODULE ID
				
		dataSource.setDatetime(dta.currentTimeDBformat()); // CREATION DATE
		
		dataSource.setUsername((String) SessionUtil.getParam("user"));  // CREATION USERNAME	
			
		dataSource.setEquipName(parameterMap.get("equipNameEdit")); // NAME
	
		dataSource.setIpAddress(parameterMap.get("equipIp-edit")); 	// IP Address
						
		dataSource.setCity(parameterMap.get("citiesEdit")); 	// CITY
		
		dataSource.setRoad(parameterMap.get("roadsEdit")); // ROAD
		
		if(dataSource.getTable().equals("colas") || dataSource.getTable().equals("dai"))
			dataSource.setDirectionTo(parameterMap.get("directionTo-edit") == "" ? "1" : parameterMap.get("directionTo-edit")); // DIRECTION	
			
		if(!dataSource.getTable().equals("meteo"))
			dataSource.setEquipType(defineEquipType(dataSource.getTable())); // TYPE
										
		dataSource.setKm(parameterMap.get("kmEdit")); // KM
		
		if(parameterMap.get("width-edit") == "0")
			dataSource.setWidth(1);	
		
		else dataSource.setWidth(parameterMap.get("width-edit") == "" ? 1 : Integer.parseInt(parameterMap.get("width-edit")));
				
		dataSource.setDirection(parameterMap.get("direction-edit")); // DIRECTION	
		
		dataSource.setLatitude(parameterMap.get("latEdit") == "" ? 0 : Double.parseDouble(parameterMap.get("latEdit"))); // LATITUDE

		dataSource.setLongitude(parameterMap.get("longEdit") == "" ? 0 : Double.parseDouble(parameterMap.get("longEdit"))); // LONGITUDE
		
		// ----------------------------------------------------------------------------------------------------------------------
							
		// SOS 
		
		if(dataSource.getTable().equals("sos")) {
			dataSource.setPort(parameterMap.get("equipPort-edit") == "" ? 0 : Integer.parseInt(parameterMap.get("equipPort-edit"))); // PORT 
			dataSource.setModel(parameterMap.get("modelEdit") == "" ? 0 : Integer.parseInt(parameterMap.get("modelEdit"))); // SOS MODEL
			dataSource.setSip(parameterMap.get("sipEdit")); // SOS SIP			
		}
	
		// ----------------------------------------------------------------------------------------------------------------------
		
		// SPEED 
		
		if(dataSource.getTable().equals("speed")) {			
			dataSource.setIpAddressIndicator(parameterMap.get("indicator-equipIp-edit")); // IP INDICATOR			
			dataSource.setIpAddressRadar(parameterMap.get("radar-equipIp-edit")); 	// IP RADAR 		
		}
		
		// Meteo
		
		if(dataSource.getTable().equals("meteo")) {		
			dataSource.setPort(parameterMap.get("equipPort-edit") == "" ? 0 : Integer.parseInt(parameterMap.get("equipPort-edit"))); 	// PORT 		
			dataSource.setEquipType((String) SessionUtil.getExternalContext().getSessionMap().get("meteoType"));	
					
		}
		 							
		// ----------------------------------------------------------------------------------------------------------------------
		
		// SAT 
		
		if(dataSource.getTable().equals("sat")) {	
									
			dataSource.setServiceFlow(parameterMap.get("service-edit") == "" ? "horizontal" : parameterMap.get("service-edit")); // SERVICE FLOW
			
			dataSource.setNumLanes(parameterMap.get("lanes-edit") == "" ? 2 : Integer.parseInt(parameterMap.get("lanes-edit"))); // NUMBER LANES
		
			// LANE VALUE
			
			dataSource.setLane1(parameterMap.get("direction1-edit") == "" ? "N" : parameterMap.get("direction1-edit"));
			dataSource.setLane2(parameterMap.get("direction2-edit") == "" ? "S" : parameterMap.get("direction2-edit"));
			dataSource.setLane3(parameterMap.get("direction3-edit") == "" ? null : parameterMap.get("direction3-edit"));
			dataSource.setLane4(parameterMap.get("direction4-edit") == "" ? null : parameterMap.get("direction4-edit"));
			dataSource.setLane5(parameterMap.get("direction5-edit") == "" ? null : parameterMap.get("direction5-edit"));
			dataSource.setLane6(parameterMap.get("direction6-edit") == "" ? null : parameterMap.get("direction6-edit"));
			dataSource.setLane7(parameterMap.get("direction7-edit") == "" ? null : parameterMap.get("direction7-edit"));
			dataSource.setLane8(parameterMap.get("direction8-edit") == "" ? null : parameterMap.get("direction8-edit"));

		}
		
		// -----------------------------------------------------------------------------------------------------------------
				
		  checked = dao.updateEquipment(dataSource, interfaceView, login.getLogin().getPermission_id());
		
		if(checked) {
			RequestContext.getCurrentInstance().execute(String.format("editBtnDisabled('%b')", checked));
			SessionUtil.executeScript("alertOptions('#success', '"+localeMap.getStringKey("$message_map_alert_updated_equipment")+"');");
			SessionUtil.executeScript("updated = '" + equipTable + equipId + "';");			
			SessionUtil.remove("meteoType");
			
		} else {
			RequestContext.getCurrentInstance().execute(String.format("editBtnDisabled('%b')", checked));
			SessionUtil.executeScript("alertOptions('#error', '"+localeMap.getStringKey("$message_map_alert_error_updating_equipment")+"');");
		}
		
	}
	
	//--------------------------------------------------------------------------------------------------------------	

	/**
	 * MÃ©todo para deleÃ§Ã£o de equipamentos
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0	
	 * @return void
	 * @throws Exception
	 */
	
	public void deleteEquipment() throws Exception {

		boolean delete = false;

		int equipId = getEquipId();		 
		String equipTable = getEquipTable();	
		String equipType = defineEquipType(equipTable);
	
		EquipmentsDAO dao = new EquipmentsDAO();		

		delete = dao.deleteEquipment(equipId, equipType, equipTable);

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
	 * MÃ©todo para definir posiÃ§Ã£o de equipamentos
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0	
	 * @return void
	 * @throws Exception
	 */	

	public void definePosition() throws Exception {

		boolean position = false;
		
		int equipId = getEquipId();		
		int posX = getPositionX(); // MAP / LINEAR
		int posY = getPositionY(); // MAP / LINEAR 
		String equipTable = getEquipTable();
				
		EquipmentsDAO dao = new EquipmentsDAO();		

		position = dao.positionEquipment(equipId, equipTable, posX, posY, interfaceView, login.getLogin().getPermission_id());
						
		if(position) {
			
			SessionUtil.executeScript("alertOptions('#success', '"+localeMap.getStringKey("$message_map_alert_position_success")+"');");
			SessionUtil.executeScript("updated = '" + equipTable + equipId + "';");
		
		} else {
			
			SessionUtil.executeScript("alertOptions('#error', '"+localeMap.getStringKey("$message_map_alert_error_positioning")+"');");
		}

	}

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * MÃ©todo para definir tamanho de um grupo de equipamentos
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

		dao.setWidthMap(module, map, width);

		SessionUtil.executeScript("alertToast('"+localeMap.getStringKey("$message_map_option_all_equipment_width_updated")+"');");
	}

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * MÃ©todo para obter o tipo da tabela pelo id
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
		case 8: table="dms"    ; break;	
		case 9: table="sat"    ; break;
		case 10: table="sos"    ; break;
		case 11: table="speed"  ; break;
		case 12: table="wim"    ; break;
		case 16: table="hit"; break;
		
		}

		return table;
	}

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * MÃ©todo para obter a tabela pelo tipo do mÃ³dulo
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
		case "dms": table="dms"      ; break;	
		case "sat": table="sat"      ; break;
		case "sos": table="sos"       ; break;
		case "speed": table="speed"   ; break;
		case "sv": table="sv"         ; break;
		case "wim": table="wim"       ; break;
		case "hit": table="hit"; break;
		}

		return table;
	}

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * MÃ©todo para obter o ID do mÃ³dulo pelo tipo
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0
	 * @param type - Tipo do mÃ³dulo
	 * @return int - MÃ³dulo ID
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
		case "hit" : moduleId = 16 ; break;
		}

		return moduleId;
	}

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * MÃ©todo para obter tipo do equipamento
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
				case "dms": type = ModulesEnum.DMS.getModule(); break;
				case "sat": type = ModulesEnum.SAT.getModule(); break;
				case "sos": type = ModulesEnum.SOS.getModule(); break;
				case "speed": type = ModulesEnum.SPEED.getModule(); break;
				case "wim": type = ModulesEnum.WIM.getModule(); break;
				case "hit": type = ModulesEnum.HIT.getModule(); break;
	
			}

		return type;
	}

	//--------------------------------------------------------------------------------------------------------------
	       
	/**
	 * MÃ©todo para obter informaÃ§Ãµes de um cabeÃ§alho para o SAT
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0   	
	 * @param id ID do equipamento   	
	 * @return objeto do SAT com informaÃ§Ãµes para o cabeÃ§alho
	 */	
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
	   	 
}
