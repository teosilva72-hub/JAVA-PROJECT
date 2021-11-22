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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import br.com.tracevia.webapp.cfg.ModulesEnum;
import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.global.RoadConcessionaireDAO;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.dms.DMS;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.sat.SAT;
import br.com.tracevia.webapp.model.sos.SOS;
import br.com.tracevia.webapp.model.speed.Speed;
import br.com.tracevia.webapp.util.LocaleUtil;

import org.primefaces.context.RequestContext;

@ManagedBean(name="equipsBean")
@RequestScoped
public class EquipmentsBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<SelectItem> cities, roads, module, lanes, dir, dmsType, mtoType;

	RoadConcessionaireDAO concessionaireDao;

	LocaleUtil localeDirection, localeMap;

	Equipments equip;
	SAT sat;
	DMS dms;
	Speed speed;

	private int equipId;
	private String equipTable, equipDel, cftvController;
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

	public List<SelectItem> getMtoType() {
		return mtoType;
	}

	public void setMtoType(List<SelectItem> mtoType) {
		this.mtoType = mtoType;
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
		mtoType = new ArrayList<SelectItem>();

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

			dir.add(new SelectItem(1, localeDirection.getStringKey("directions_north")));   
			dir.add(new SelectItem(2, localeDirection.getStringKey("directions_south")));   
			dir.add(new SelectItem(3, localeDirection.getStringKey("directions_east")));   
			dir.add(new SelectItem(4, localeDirection.getStringKey("directions_west")));   

			dmsType.add(new SelectItem(1, localeMap.getStringKey("map_dms_type_1")));   
			dmsType.add(new SelectItem(2, localeMap.getStringKey("map_dms_type_2")));   
			dmsType.add(new SelectItem(3, localeMap.getStringKey("map_dms_type_3"))); 

			mtoType.add(new SelectItem("WS", "WS")); 
			mtoType.add(new SelectItem("RS", "RS")); 

		}catch(Exception ex){
			ex.printStackTrace();
		}

	}

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * Método para criar equipamentos
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0	
	 * @return void
	 * @throws Exception
	 */

	public void createEquipment() throws Exception {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		RequestContext request = RequestContext.getCurrentInstance();

		DateTimeApplication dta = new DateTimeApplication();

		Map<String, String> parameterMap = (Map<String, String>) externalContext.getRequestParameterMap();

		checked = false;

		//FOR GENERICS
		Equipments equip = new Equipments(); 	 

		//EQUIPDAO
		EquipmentsDAO equipDAO = new EquipmentsDAO();

		//FOR SOS
		SOS sos = new SOS();	

		//FOR SAT
		SAT sat = new SAT();

		//FOR PMV
		DMS dms = new DMS();
		
		//FOR Speed
		Speed speed = new Speed();
			

		//CHECK MODULES	
		int moduleID = (parameterMap.get("equips") == "" ? 0 : Integer.parseInt(parameterMap.get("equips")));

		//EQUIP ID
		int equipId = (parameterMap.get("equipId") == "" ? 0 : Integer.parseInt(parameterMap.get("equipId")));
	
		///////////////////////////////////////////////////////////////////////////////////////////////////////////	 
		//DMS CHECKING
		//////////////////////////////////////////////////////////////////////////////////////////////////////////

		if((moduleID != 0 && moduleID == 8) && (equipId != 0)) {

			String table = defineTableById(moduleID);

			//For Equipment CreationDate
			dms.setCreation_date(dta.currentTimeDBformat());

			//For Equipment CreationUsername		
			dms.setCreation_username( (String) facesContext.getExternalContext().getSessionMap().get("user"));

			//DMS ID
			dms.setEquip_id(equipId);

			//For Equipment Name
			dms.setNome(parameterMap.get("equipName"));

			//For Equipment IP
			dms.setDms_ip(parameterMap.get("equipIp"));

			//For Equipment Type
			dms.setDms_type(parameterMap.get("dmsType") == "" ? 1 :Integer.parseInt(parameterMap.get("dmsType")));

			//For Equipment City
			dms.setCidade(parameterMap.get("cities"));

			//For Equipment Road
			dms.setEstrada(parameterMap.get("roads"));

			//For Equipment TYPE
			dms.setEquip_type(ModulesEnum.PMV.getModule());

			//For Equipment KM
			dms.setKm(parameterMap.get("km"));

			int type = (parameterMap.get("dmsType") == "" ? 1 : Integer.parseInt(parameterMap.get("dmsType")));

			//DMS TYPE
			defineDMStype(dms, type);

			checked =  equipDAO.checkExists(dms.getEquip_id(), table);

			if(checked)
				request.execute("alertOptions('#equip-save-error');");

			else {

				checked = equipDAO.EquipDMSRegisterMap(dms, table);

				if(checked) {
					request.execute("alertOptions('#equip-save');");
					request.execute("updated = '" + table + parameterMap.get("equipId") + "';");
				}

				else 
					request.execute("alertOptions('#equip-save-error');");

			}

			dms = new DMS(); // RESET
		}

		///////////////////////////////////////////////////////////////////////////////////////////////////////////	 
		//SOS CHECKING
		//////////////////////////////////////////////////////////////////////////////////////////////////////////

		if((moduleID != 0 && moduleID == 10) && (equipId != 0)) {

			String table = defineTableById(moduleID);

			//For Equipment CreationDate
			sos.setCreation_date(dta.currentTimeDBformat());

			//For Equipment CreationUsername		
			sos.setCreation_username( (String) facesContext.getExternalContext().getSessionMap().get("user"));

			//SOS ID
			sos.setEquip_id(equipId);

			//For Equipment Name
			sos.setNome(parameterMap.get("equipName"));

			//For Equipment IP
			sos.setEquip_ip(parameterMap.get("equipIp"));

			//For Equipment Port
			sos.setPort(Integer.parseInt(parameterMap.get("equipPort")));

			//For Equipment City
			sos.setCidade(parameterMap.get("cities"));

			//For Equipment Road
			sos.setEstrada(parameterMap.get("roads"));

			//For Equipment KM
			sos.setKm(parameterMap.get("km"));

			//For Equipment Model
			sos.setModel(Integer.parseInt(parameterMap.get("model")));

			//For Equipment SIP
			sos.setSip(parameterMap.get("sip"));

			checked =  equipDAO.checkExists(sos.getEquip_id(), table);

			if(checked)
				request.execute("alertOptions('#equip-save-error');");

			else {

				checked = equipDAO.EquipSOSMap(sos, table);

				if(checked) {
					request.execute("alertOptions('#equip-save');");
					request.execute("updated = '" + table + parameterMap.get("equipId") + "';");
				}

				else 
					request.execute("alertOptions('#equip-save-error');");

			}

			sos = new SOS(); // RESET
		}

		///////////////////////////////////////////////////////////////////////////////////////////////////////////	 
		//SAT CHECKING
		//////////////////////////////////////////////////////////////////////////////////////////////////////////

		if((moduleID != 0 && moduleID == 9) && (equipId != 0)) {

			String table = defineTableById(moduleID);

			//For Equipment CreationDate
			sat.setCreation_date(dta.currentTimeDBformat());

			//For Equipment CreationUsername		
			sat.setCreation_username( (String) facesContext.getExternalContext().getSessionMap().get("user"));

			//SAT ID
			sat.setEquip_id(equipId);

			//For Equipment Name
			sat.setNome(parameterMap.get("equipName"));

			//EQUIP IP
			sat.setEquip_ip(parameterMap.get("equipIp"));

			//For Equipment City
			sat.setCidade(parameterMap.get("cities"));

			//For Equipment Road
			sat.setEstrada(parameterMap.get("roads"));

			//For Equipment KM
			sat.setKm(parameterMap.get("km"));

			//For Equipment TYPE
			sat.setEquip_type(ModulesEnum.SAT.getModule());

			//For Number Lanes
			int numLanes = (parameterMap.get("lanes") == "" ? 0 : Integer.parseInt(parameterMap.get("lanes")));

			if(numLanes > 0)
				sat.setNumFaixas(numLanes);

			else sat.setNumFaixas(2);

			//SET LANES DEFINITION
			defineDirection(sat, 1, parameterMap.get("direction1") == "" ? 0 : Integer.parseInt(parameterMap.get("direction1")));
			defineDirection(sat, 2, parameterMap.get("direction2") == "" ? 0 : Integer.parseInt(parameterMap.get("direction2")));
			defineDirection(sat, 3, parameterMap.get("direction3") == "" ? 0 : Integer.parseInt(parameterMap.get("direction3")));
			defineDirection(sat, 4, parameterMap.get("direction4") == "" ? 0 : Integer.parseInt(parameterMap.get("direction4")));
			defineDirection(sat, 5, parameterMap.get("direction5") == "" ? 0 : Integer.parseInt(parameterMap.get("direction5")));
			defineDirection(sat, 6, parameterMap.get("direction6") == "" ? 0 : Integer.parseInt(parameterMap.get("direction6")));
			defineDirection(sat, 7, parameterMap.get("direction7") == "" ? 0 : Integer.parseInt(parameterMap.get("direction7")));
			defineDirection(sat, 8, parameterMap.get("direction8") == "" ? 0 : Integer.parseInt(parameterMap.get("direction8")));


			checked =  equipDAO.checkExists(sat.getEquip_id(), table);

			if(checked)
				request.execute("alertOptions('#equip-save-error');");

			else {

				checked = equipDAO.EquipSATRegisterMap(sat, table);

				if(checked) {
					request.execute("alertOptions('#equip-save');");
					request.execute("updated = '" + table + parameterMap.get("equipId") + "';");

				}

				else  request.execute("alertOptions('#equip-save-error');");

			}

			sat = new SAT(); // RESET
		}
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////	 
		//SPEED CHECKING
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		if((moduleID != 0 &&  moduleID == 11) && (equipId != 0)) {

			//EQUIP TABLE BY MODULE
			String table = defineTableById(moduleID);

			//For Equipment ID
			speed.setEquip_id(equipId);

			//For Equipment CreationDate
			speed.setCreation_date(dta.currentTimeDBformat());

			//For Equipment CreationUsername		
			speed.setCreation_username( (String) facesContext.getExternalContext().getSessionMap().get("user")); 

			//Equip Type
			speed.setEquip_type(defineEquipType(table));

			//For Equipment Name
			speed.setNome(parameterMap.get("equipName"));

			//EQUIP IP
			speed.setEquip_ip_indicator(parameterMap.get("indicator-equipIp"));
			
			//EQUIP IP
			speed.setEquip_ip_radar(parameterMap.get("radar-equipIp"));

			//For Equipment City
			speed.setCidade(parameterMap.get("cities"));

			//For Equipment Road
			speed.setEstrada(parameterMap.get("roads"));

			//For Equipment KM
			speed.setKm(parameterMap.get("km"));		 

			checked =  equipDAO.checkExists(speed.getEquip_id(), table);
			
			//System.out.println(checked);

			if(checked)
				request.execute("alertOptions('#equip-save-error');");

			else {

				checked = equipDAO.EquipRegisterSpeedMap(speed, table);

				if(checked) {
					request.execute("alertOptions('#equip-save');");
					request.execute("updated = '" + table + parameterMap.get("equipId") + "';");
				}

				else  request.execute("alertOptions('#equip-save-error');");

			}  //VALIDATION
			
			speed = new Speed(); // RESET

		} // END METHOD

				
		///////////////////////////////////////////////////////////////////////////////////////////////////////////	 
		//GENERIC CHECKING
		//////////////////////////////////////////////////////////////////////////////////////////////////////////

		else if((moduleID != 0 && (moduleID != 9 && moduleID != 8 && moduleID != 10 && moduleID != 11)) && (equipId != 0)) {

			//EQUIP TABLE BY MODULE
			String table = defineTableById(moduleID);

			//For Equipment ID
			equip.setEquip_id(equipId);

			//For Equipment CreationDate
			equip.setCreation_date(dta.currentTimeDBformat());

			//For Equipment CreationUsername		
			equip.setCreation_username( (String) facesContext.getExternalContext().getSessionMap().get("user")); 

			//Equip Type
			equip.setEquip_type(defineEquipType(table));

			//For Equipment Name
			equip.setNome(parameterMap.get("equipName"));

			//EQUIP IP
			equip.setEquip_ip(parameterMap.get("equipIp"));

			//For Equipment City
			equip.setCidade(parameterMap.get("cities"));

			//For Equipment Road
			equip.setEstrada(parameterMap.get("roads"));

			//For Equipment KM
			equip.setKm(parameterMap.get("km"));		 

			checked =  equipDAO.checkExists(equip.getEquip_id(), table);

			if(checked)
				request.execute("alertOptions('#equip-save-error');");

			else {

				checked = equipDAO.EquipRegisterMap(equip, table);

				if(checked) {
					request.execute("alertOptions('#equip-save');");
					request.execute("updated = '" + table + parameterMap.get("equipId") + "';");
				}

				else  request.execute("alertOptions('#equip-save-error');");

			}  //VALIDATION

		} // END METHOD

		equip = new Equipments(); // RESET

	}

	//--------------------------------------------------------------------------------------------------------------

	/**
	 * Método para pesquisar equipamentos
	 * @author Wellington
	 * @version 1.0
	 * @since Release 1.0	
	 * @return void
	 * @throws Exception
	 */

	public void SearchEquipment() throws Exception {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		Map<String, String> parameterMap = (Map<String, String>) externalContext.getRequestParameterMap();

		//INTERFACES
		String interfaceView = parameterMap.get("interface-search");		   		

		int equipId = getEquipId();		 
		String equipTable = getEquipTable();
		EquipmentsDAO dao = new EquipmentsDAO();
		equip = new Equipments();
		sat = new SAT();
		dms = new DMS();
		sos = new SOS();
		speed = new Speed();

		int moduleId = getModuleByName(equipTable);

		if(moduleId == 8) {

			dms = dao.EquipDMSSearchMap(equipId, equipTable, interfaceView, login.getLogin().getPermission_id());

			RequestContext.getCurrentInstance().execute("$('#equips-edit').val('"+moduleId+"');");
			RequestContext.getCurrentInstance().execute("$('#equipId-edit').val('"+dms.getEquip_id()+"');");	
			RequestContext.getCurrentInstance().execute("$('#equipNameEdit').val('"+dms.getNome()+"');");	
			RequestContext.getCurrentInstance().execute("$('#dmsType-edit').val('"+dms.getDms_type()+"');");
			RequestContext.getCurrentInstance().execute("$('#equipIp-edit').val('"+dms.getDms_ip()+"');");
			RequestContext.getCurrentInstance().execute("$('#citiesEdit').val('"+dms.getCidade()+"');");	
			RequestContext.getCurrentInstance().execute("$('#roadsEdit').val('"+dms.getEstrada()+"');");	
			RequestContext.getCurrentInstance().execute("$('#kmEdit').val('"+dms.getKm()+"');");	
			RequestContext.getCurrentInstance().execute("$('#width-edit').val('"+dms.getMapWidth()+"');");	


		} else if(moduleId == 9) {

			sat = dao.EquipSatSearchMap(equipId, equipTable, interfaceView, login.getLogin().getPermission_id());

			RequestContext.getCurrentInstance().execute("$('#equips-edit').val('"+moduleId+"');");
			RequestContext.getCurrentInstance().execute("$('#equipId-edit').val('"+sat.getEquip_id()+"');");	
			RequestContext.getCurrentInstance().execute("$('#equipNameEdit').val('"+sat.getNome()+"');");		
			RequestContext.getCurrentInstance().execute("$('#equipIp-edit').val('"+sat.getEquip_ip()+"');");
			RequestContext.getCurrentInstance().execute("$('#citiesEdit').val('"+sat.getCidade()+"');");	
			RequestContext.getCurrentInstance().execute("$('#roadsEdit').val('"+sat.getEstrada()+"');");	
			RequestContext.getCurrentInstance().execute("$('#kmEdit').val('"+sat.getKm()+"');");	
			RequestContext.getCurrentInstance().execute("$('#width-edit').val('"+sat.getMapWidth()+"');");	
			RequestContext.getCurrentInstance().execute("$('#lanes-edit').val('"+sat.getNumFaixas()+"');");

			if(sat.getFaixa1() != null)			 
				RequestContext.getCurrentInstance().execute("$('#direction1-edit').show(); $('#direction1-edit').val('"+sat.getFaixa1()+"');");	

			if(sat.getFaixa2() != null)			 
				RequestContext.getCurrentInstance().execute("$('#direction2-edit').show(); $('#direction2-edit').val('"+sat.getFaixa2()+"');");	

			if(sat.getFaixa3() != null)			 
				RequestContext.getCurrentInstance().execute("$('#direction3-edit').show(); $('#direction3-edit').val('"+sat.getFaixa3()+"');");	

			if(sat.getFaixa4() != null)			 
				RequestContext.getCurrentInstance().execute("$('#direction4-edit').show(); $('#direction4-edit').val('"+sat.getFaixa4()+"');");	

			if(sat.getFaixa5() != null)			 
				RequestContext.getCurrentInstance().execute("$('#direction5-edit').show(); $('#direction5-edit').val('"+sat.getFaixa5()+"');");	

			if(sat.getFaixa6() != null)			 
				RequestContext.getCurrentInstance().execute("$('#direction6-edit').show(); $('#direction6-edit').val('"+sat.getFaixa6()+"');");	

			if(sat.getFaixa7() != null)			 
				RequestContext.getCurrentInstance().execute("$('#direction7-edit').show(); $('#direction7-edit').val('"+sat.getFaixa7()+"');");	

			if(sat.getFaixa8() != null)			 
				RequestContext.getCurrentInstance().execute("$('#direction8-edit').show(); $('#direction8-edit').val('"+sat.getFaixa8()+"');");				

		} else if (moduleId == 10) {

			sos = dao.EquipSOSSearchMap(equipId, equipTable, interfaceView, login.getLogin().getPermission_id());

			RequestContext.getCurrentInstance().execute("$('#equips-edit').val('"+moduleId+"');");
			RequestContext.getCurrentInstance().execute("$('#equipId-edit').val('"+sos.getEquip_id()+"');");			
			RequestContext.getCurrentInstance().execute("$('#equipNameEdit').val('"+sos.getNome()+"');");	
			RequestContext.getCurrentInstance().execute("$('#equipIp-edit').val('"+sos.getEquip_ip()+"');");
			RequestContext.getCurrentInstance().execute("$('#equipPort-edit').val('"+sos.getPort()+"');");
			RequestContext.getCurrentInstance().execute("$('#citiesEdit').val('"+sos.getCidade()+"');");	
			RequestContext.getCurrentInstance().execute("$('#roadsEdit').val('"+sos.getEstrada()+"');");	
			RequestContext.getCurrentInstance().execute("$('#kmEdit').val('"+sos.getKm()+"');");	
			RequestContext.getCurrentInstance().execute("$('#width-edit').val('"+sos.getMapWidth()+"');");
			RequestContext.getCurrentInstance().execute("$('#modelEdit').val('"+sos.getModel()+"');");
			RequestContext.getCurrentInstance().execute("$('#sipEdit').val('"+sos.getSip()+"');");	
		
		} else if (moduleId == 11) {

			speed = dao.EquipSpeedSearchMap(equipId, equipTable, interfaceView, login.getLogin().getPermission_id());

			RequestContext.getCurrentInstance().execute("$('#equips-edit').val('"+moduleId+"');");
			RequestContext.getCurrentInstance().execute("$('#equipId-edit').val('"+speed.getEquip_id()+"');");			
			RequestContext.getCurrentInstance().execute("$('#equipNameEdit').val('"+speed.getNome()+"');");	
			RequestContext.getCurrentInstance().execute("$('#indicator-equipIp-edit').val('"+speed.getEquip_ip_indicator()+"');");	
			RequestContext.getCurrentInstance().execute("$('#radar-equipIp-edit').val('"+speed.getEquip_ip_radar()+"');");		
			RequestContext.getCurrentInstance().execute("$('#citiesEdit').val('"+speed.getCidade()+"');");	
			RequestContext.getCurrentInstance().execute("$('#roadsEdit').val('"+speed.getEstrada()+"');");	
			RequestContext.getCurrentInstance().execute("$('#kmEdit').val('"+speed.getKm()+"');");	
			RequestContext.getCurrentInstance().execute("$('#width-edit').val('"+speed.getMapWidth()+"');");
				
		}else {		

			equip = dao.EquipSearchMap(equipId, equipTable, interfaceView, login.getLogin().getPermission_id()); 

			RequestContext.getCurrentInstance().execute("$('#equips-edit').val('"+getModuleByName(equipTable)+"');");				 
			RequestContext.getCurrentInstance().execute("$('#equipId-edit').val('"+equip.getEquip_id()+"');");	
			RequestContext.getCurrentInstance().execute("$('#equipNameEdit').val('"+equip.getNome()+"');");	
			RequestContext.getCurrentInstance().execute("$('#equipIp-edit').val('"+equip.getEquip_ip()+"');");
			RequestContext.getCurrentInstance().execute("$('#citiesEdit').val('"+equip.getCidade()+"');");	
			RequestContext.getCurrentInstance().execute("$('#roadsEdit').val('"+equip.getEstrada()+"');");	
			RequestContext.getCurrentInstance().execute("$('#kmEdit').val('"+equip.getKm()+"');");	
			RequestContext.getCurrentInstance().execute("$('#width-edit').val('"+equip.getMapWidth()+"');");	
		
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

		RequestContext.getCurrentInstance().execute("$('#del-equip-name').html('"+equipName+"');");				 	 

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

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		RequestContext request = RequestContext.getCurrentInstance();

		DateTimeApplication dta = new DateTimeApplication();

		Map<String, String> parameterMap = (Map<String, String>) externalContext.getRequestParameterMap();

		boolean update = false;

		EquipmentsDAO dao = new EquipmentsDAO();

		DMS dms = new DMS();
		SAT sat = new SAT();
		SOS sos = new SOS();
		Speed speed = new Speed();
		Equipments equip = new Equipments();

		int equipId = getEquipId();		 
		String equipTable = getEquipTable();

		//INTERFACES
		String interfaceView = parameterMap.get("interface");		   		

		//CHECK MODULES	
		int moduleId = getModuleByName(equipTable);

		if(moduleId != 0 && moduleId == 8) {		

			//Table definition
			String table = defineTableById(moduleId);

			//For Equipment Update Date
			dms.setUpdate_date(dta.currentTimeDBformat());

			//For Equipment Update Username		
			dms.setUpdate_username( (String) facesContext.getExternalContext().getSessionMap().get("user"));			

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
				request.execute("alertOptions('#equip-update');");
				request.execute("updated = '" + equipTable + equipId + "';");
			} else {
				request.execute("alertOptions('#equip-update-error');");
			}

		} else if(moduleId != 0 && moduleId == 10) {		

			//Table definition
			String table = defineTableById(moduleId);

			//For Equipment Update Date
			sos.setUpdate_date(dta.currentTimeDBformat());

			//For Equipment Update Username		
			sos.setUpdate_username( (String) facesContext.getExternalContext().getSessionMap().get("user"));			

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

			//For Equipment Map Width / Linear Width			    			    
			if(parameterMap.get("width-edit") == "0")
				sos.setMapWidth(1);			    

			else sos.setMapWidth(parameterMap.get("width-edit") == "" ? 1 : Integer.parseInt(parameterMap.get("width-edit")));

			update = dao.EquipSOSUpdateMap(sos, table, interfaceView, login.getLogin().getPermission_id());

			if(update) {
				request.execute("alertOptions('#equip-update');");
				request.execute("updated = '" + equipTable + equipId + "';");
			} else {
				request.execute("alertOptions('#equip-update-error');");
			}    


		} else if(moduleId != 0 && moduleId == 9) {

			// Table definition
			String table = defineTableById(moduleId);

			//For Equipment Update Date
			sat.setUpdate_date(dta.currentTimeDBformat());

			//For Equipment Update Username		
			sat.setUpdate_username( (String) facesContext.getExternalContext().getSessionMap().get("user"));		

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
				request.execute("alertOptions('#equip-update');");
				request.execute("updated = '" + equipTable + equipId + "';");
			} else {
				request.execute("alertOptions('#equip-update-error');");
			}


		} else if(moduleId != 0 && moduleId == 11) {		

			//Table definition
			String table = defineTableById(moduleId);

			//For Equipment Update Date
			speed.setUpdate_date(dta.currentTimeDBformat());

			//For Equipment Update Username		
			speed.setUpdate_username( (String) facesContext.getExternalContext().getSessionMap().get("user"));			

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
		
			//For Equipment Map Width / Linear Width			    			    
			if(parameterMap.get("width-edit") == "0")
				speed.setMapWidth(1);			    

			else speed.setMapWidth(parameterMap.get("width-edit") == "" ? 1 : Integer.parseInt(parameterMap.get("width-edit")));

			update = dao.EquipSpeedUpdateMap(speed, table, interfaceView, login.getLogin().getPermission_id());

			if(update) {
				request.execute("alertOptions('#equip-update');");
				request.execute("updated = '" + equipTable + equipId + "';");
			} else {
				request.execute("alertOptions('#equip-update-error');");
			}    


		}else if((moduleId != 0 && (moduleId != 8 && moduleId != 9 && moduleId != 10 && moduleId != 11))) {

			// Table definition
			String table = defineTableById(moduleId);

			//For Equipment Update Date
			dms.setUpdate_date(dta.currentTimeDBformat());

			//For Equipment Update Username		
			dms.setUpdate_username( (String) facesContext.getExternalContext().getSessionMap().get("user"));		

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

			//For Equipment Map Width / Linear Width			    			    
			if(parameterMap.get("width-edit") == "0")
				equip.setMapWidth(1);			    

			else equip.setMapWidth(parameterMap.get("width-edit") == "" ? 1 : Integer.parseInt(parameterMap.get("width-edit")));

			//MENSAGEM UPDATED
			update = dao.EquipUpdateMap(equip, table, interfaceView, login.getLogin().getPermission_id());

			if(update) {
				request.execute("alertOptions('#equip-update');");
				request.execute("updated = '" + equipTable + equipId + "';");
			} else {
				request.execute("alertOptions('#equip-update-error');");
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
		RequestContext request = RequestContext.getCurrentInstance();

		EquipmentsDAO dao = new EquipmentsDAO();		

		delete = dao.EquipDeleteMap(equipId, equipTable);

		request.execute("init();");

		if(delete) {
			request.execute("alertOptions('#equip-delete');");
			request.execute("updated = '" + equipTable + equipId + "';");
		} else {
			request.execute("alertOptions('#equip-delete-error');");
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

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		Map<String, String> parameterMap = (Map<String, String>) externalContext.getRequestParameterMap();

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

		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();

		EquipmentsDAO dao = new EquipmentsDAO();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();

		String w = params.get("width-edit");

		String module = getEquipTable();
		int width = w == "" ? 100 : Integer.parseInt(w);

		dao.setWidthMap(module.equals("dms") ? "pmv" : module , map, width);

		request.execute("alertToast('all equipment updated!');");
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
		case 6: table="mto"    ; break;
		case 8: table="pmv"    ; break;	
		case 9: table="sat"    ; break;
		case 10: table="sos"    ; break;
		case 11: table="speed"  ; break;
		case 12: table="sv"    ; break;
		case 14: table="wim"    ; break;
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
		case "mto": moduleId = 6 ; break;
		case "dms": moduleId = 8 ; break;  	
		case "sat": moduleId = 9 ; break;
		case "sos": moduleId = 10  ; break;
		case "speed": moduleId = 11 ; break;
		case "sv": moduleId = 12 ; break;
		case "wim" : moduleId = 14 ; break;
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

		case "cftv": type = ModulesEnum.CFTV.getModule() ; break;
		case "colas": type = ModulesEnum.COLAS.getModule(); ; break;
		case"comms": type = ModulesEnum.COMMS.getModule(); ; break;
		case "dai": type = ModulesEnum.DAI.getModule(); ; break;
		case "mto": type = ModulesEnum.MTO.getModule(); ; break;
		case "ocr": type = ModulesEnum.OCR.getModule(); ; break;
		case "sos": type = ModulesEnum.SOS.getModule(); ; break;
		case "speed": type = ModulesEnum.SPEED.getModule(); ; break;
		case "sv": type = ModulesEnum.SV.getModule(); ; break;
		case "wim": type = ModulesEnum.WIM.getModule(); ; break;

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
	   
	// --------------------------------------------------------------------------------------------		
	   
       
}
