package br.com.tracevia.webapp.controller.global;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import br.com.tracevia.webapp.controller.sos.SOSBuildMaps;
import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.global.RoadConcessionaireDAO;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.sos.SOS;


@ManagedBean(name="equipsBean")
@RequestScoped
public class EquipmentsBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<SelectItem> cities, roads, module;

	RoadConcessionaireDAO concessionaireDao;
	
	Equipments equip;
	
	private int equipId;
	private String equipTable;
	private int positionX, positionY;
					
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
	
	public Equipments getEquip() {
		return equip;
	}

	public void setEquip(Equipments equip) {
		this.equip = equip;
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

	@PostConstruct
	public void initialize() {
					
	  cities = new  ArrayList<SelectItem>();
      roads = new  ArrayList<SelectItem>();
      module = new  ArrayList<SelectItem>();
      
      equip = new Equipments();
      
      try {
			
    	 concessionaireDao = new RoadConcessionaireDAO();
 		 
 		 cities = concessionaireDao.cityDefinitions();
 		 roads = concessionaireDao.roadDefinitions();
 		 module = concessionaireDao.moduleDefinitions();
 		 
      }catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	public void createEquipment() throws Exception {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
	    ExternalContext externalContext = facesContext.getExternalContext();
	    
	    DateTimeApplication dta = new DateTimeApplication();
	    
	    Map<String, String> parameterMap = (Map<String, String>) externalContext.getRequestParameterMap();
		
	   int moduleID = Integer.parseInt(parameterMap.get("equips"));
	    
		//For Equipment ID
		equip.setEquip_id(Integer.parseInt(parameterMap.get("equipId")));
		
		//For Equipment CreationDate
	    equip.setCreation_date(dta.currentTimeDBformat());
				    
	    //For Equipment CreationUsername		
		 equip.setCreation_username( (String) facesContext.getExternalContext().getSessionMap().get("user"));
		
		//For Equipment Name
	    equip.setNome(parameterMap.get("equipName"));
	    
	    //For Equipment City
	    equip.setCidade(parameterMap.get("cities"));
	    
	    //For Equipment Road
	    equip.setEstrada(parameterMap.get("roads"));
	    
	    //For Equipment KM
	    equip.setKm(parameterMap.get("km"));
	    
	    //For Equipment Width
	    equip.setMapWidth(Integer.parseInt(parameterMap.get("width")));
	    	    
	    EquipmentsDAO equipDAO = new EquipmentsDAO();
	    
	     String table = defineTableById(moduleID);
	    
	   boolean equipr = equipDAO.EquipRegisterMap(equip, table);
	   			
	   		//if (equipr)
	   			//System.out.println("true");
	   			
	}
	
	public void SearchEquipment() throws Exception {
		
		 int equipId = getEquipId();		 
		 String equipTable = getEquipTable();
		 
		 EquipmentsDAO dao = new EquipmentsDAO();
		 equip = new Equipments();
		 
		 equip = dao.EquipSearchMap(equipId, equipTable);
				    
		 System.out.println(equip.getCidade());
	    //For Equipment ID
	 
	    
	}
	
	public void UpdateEquipment() throws Exception {
			
		boolean update = false;
		
		 int equipId = getEquipId();		 
		 String equipTable = getEquipTable();
		 
		 System.out.println(equipTable);
		 
		 EquipmentsDAO dao = new EquipmentsDAO();
		 		 
		 update = dao.EquipUpdateMap(equip, equipTable);
		
		    
		 System.out.println("UP"+update);
	    //For Equipment ID
		
	}
	
	public void DeleteEquipment() throws Exception {
		
		boolean delete = false;
		
		 int equipId = getEquipId();		 
		 String equipTable = getEquipTable();
		 
		 System.out.println(equipTable);
		 
		 EquipmentsDAO dao = new EquipmentsDAO();
		 		 
		 delete = dao.EquipDeleteMap(equipId, equipTable);
		
		    
		 System.out.println("Deleted: "+delete);
	   
	
	}
	
	public void definePosition() throws Exception {
		
		 boolean position = false;
		
		 int equipId = getEquipId();		
		 int posX = getPositionX();
		 int posY = getPositionY();
		 String equipTable = getEquipTable();
		 
		 System.out.println("EQUIP: "+equipId);
		 System.out.println("TABLE: "+equipTable);
		 System.out.println("X: "+posX);
		 System.out.println("Y: "+posY);
		 
		 EquipmentsDAO dao = new EquipmentsDAO();		
		
		 position = dao.EquipPositionMap(equipId, equipTable, posX, posY);
		 
		 System.out.println("Positioned: "+position);
		 
		
	}
	
	public String defineTableById(int id) { 
		
		String table = null;
		
		switch(id) {
		
		case 1: table="cftv"   ; break;
		case 2: table="colas"  ; break;
		case 3: table="comms"  ; break;
		case 4: table="dai"    ; break;
		case 5: table="lpr"    ; break;
		case 6: table="mto"    ; break;
		case 8: table="pmv"    ; break;
		case 9: table="sat"    ; break;
		case 10:table="sos"    ; break;
		case 11:table="speed"  ; break;
		case 13:table="wim"    ; break;
		}
		
		return table;
	}
	
    public String defineTableByName(String type) { 
		
		String table = null;
		
		switch(type) {
		
		case "cftv": table="cftv"    ; break;
		case "colas": table="colas"  ; break;
		case"comms": table="comms"   ; break;
		case "dai": table="dai"      ; break;
		case "lpr": table="lpr"      ; break;
		case "mto": table="mto"      ; break;
		case "pmv": table="pmv"      ; break;
		case "sat": table="sat"      ; break;
		case "sos":table="sos"       ; break;
		case "speed":table="speed"   ; break;
		case "wim":table="wim"       ; break;
		}
		
		return table;
	}
	
    
    

}
