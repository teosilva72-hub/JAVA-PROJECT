package br.com.tracevia.webapp.controller.global;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.global.RoadConcessionaireDAO;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.dms.DMS;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.sat.SAT;
import br.com.tracevia.webapp.util.LocaleUtil;

import org.primefaces.context.RequestContext;

@ManagedBean(name="equipsBean")
@RequestScoped
public class EquipmentsBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<SelectItem> cities, roads, module, lanes, dir;

	RoadConcessionaireDAO concessionaireDao;
	
	LocaleUtil localeDirection;
	
	Equipments equip;
	SAT sat;
		
	private int equipId;
	private String equipTable, equipBord, equipDel;
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
	
	public String getEquipBord() {
		return equipBord;
	}

	public void setEquipBord(String equipBord) {
		this.equipBord = equipBord;
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

	@PostConstruct
	public void initialize() {
		
	  localeDirection = new LocaleUtil();
		
	  localeDirection.getResourceBundle(LocaleUtil.LABELS_DIRECTIONS);
	  
	  equip = new Equipments();
					
	  cities = new  ArrayList<SelectItem>();
      roads = new  ArrayList<SelectItem>();
      module = new  ArrayList<SelectItem>();
      lanes = new  ArrayList<SelectItem>();
      dir = new ArrayList<SelectItem>();
               
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
	   
	   boolean equipr = false;
	   
	   //FOR GENERICS
	   Equipments equip = new Equipments();
	   
	   //FOR SAT
	   SAT sat = new SAT();
	   
	   //FOR PMV
	   DMS dms = new DMS();
	   
	   if(moduleID == 9) {		   
	   
		//For Equipment ID
		sat.setEquip_id(Integer.parseInt(parameterMap.get("equipId")));
		 
		//For Equipment CreationDate
	    sat.setCreation_date(dta.currentTimeDBformat());
				    
	    //For Equipment CreationUsername		
		sat.setCreation_username( (String) facesContext.getExternalContext().getSessionMap().get("user"));
		
		//For Equipment Name
	    sat.setNome(parameterMap.get("equipName"));
	    
	    //For Equipment City
	    sat.setCidade(parameterMap.get("cities"));
	    
	    //For Equipment Road
	    sat.setEstrada(parameterMap.get("roads"));
	    
	    //For Equipment KM
	    sat.setKm(parameterMap.get("km"));
	    
	    //For Direction Lane 1
	    sat.setFaixa1(parameterMap.get("faixa1"));
	    
	    //For Direction Lane 2
	    sat.setFaixa2(parameterMap.get("faixa2"));
	    
	    //For Direction Lane 3
	    sat.setFaixa3(parameterMap.get("faixa3"));
	    
	    //For Direction Lane 4
	    sat.setFaixa4(parameterMap.get("faixa4"));
	    
	    //For Direction Lane 5
	    sat.setFaixa5(parameterMap.get("faixa5"));
	    
	    //For Direction Lane 6
	    sat.setFaixa6(parameterMap.get("faixa6"));
	    
	    //For Direction Lane 7
	    sat.setFaixa7(parameterMap.get("faixa7"));
	    
	    //For Direction Lane 8
	    sat.setFaixa8(parameterMap.get("faixa8"));
	    
	    //For Equipment Width
	    sat.setMapWidth(Integer.parseInt(parameterMap.get("width")));
	    
	    //For Number Lanes
	    sat.setNumFaixas(Integer.parseInt(parameterMap.get("lanes")));
	    
	    //For Direction 1
	    sat.setSentido1(parameterMap.get("direction1"));
	    
	    //For Direction 2
	    sat.setSentido2(parameterMap.get("direction2"));
	    
	    
	    defineDirections(sat, sat.getNumFaixas(), Integer.parseInt(sat.getSentido1()));
	    
	    
	   }else {
		   		   
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
		   
	   }
	
	    
	    String table = defineTableById(moduleID);
	    //execute js
	    RequestContext.getCurrentInstance().execute("history.go(0);");
	    RequestContext.getCurrentInstance().execute("location.href=location.protocol + '//' + location.host + location.pathname+'?newId="+table+Integer.parseInt(parameterMap.get("equipId"))+"'");
	    EquipmentsDAO equipDAO = new EquipmentsDAO();
	    
	     
	     equipBord = table+parameterMap.get("equipId");
	     System.out.println(equipBord+ "< id do equip bord");
	    
	     if(moduleID == 8)
	    	 equipr = equipDAO.EquipRegisterMap(dms, table);
	     
	     else if(moduleID == 9)  
	     equipr = equipDAO.EquipSATRegisterMap(sat, table);
	   
	     else equipr = equipDAO.EquipRegisterMap(equip, table);
	  
	   			
	   	
	   			
	}
	
	public void SearchEquipment() throws Exception {
		
		 int equipId = getEquipId();		 
		 String equipTable = getEquipTable();
		 EquipmentsDAO dao = new EquipmentsDAO();
		 equip = new Equipments();
		 sat = new SAT();
		 
		 equip = dao.EquipSearchMap(equipId, equipTable); 
		 
		 //System.out.println(equip.getNome());
		 System.out.println(equipId);
		 
         RequestContext.getCurrentInstance().update("delete-equip-form:delete-equipName");
	    // For Equipment ID
		 
	    
	}
	
	public void runDel() throws Exception {
		 
        RequestContext.getCurrentInstance().execute("sendToBeanDel();");
	    // For Equipment ID
		 
	    
	}
	
	public void UpdateEquipment() throws Exception {
			
		boolean update = false;
		
		 int equipId = getEquipId();		 
		 String equipTable = getEquipTable();
		 
		// System.out.println(equipTable);
		 RequestContext.getCurrentInstance().execute("history.go(0)");
		 
		 EquipmentsDAO dao = new EquipmentsDAO();
		 		 
		 update = dao.EquipUpdateMap(equip, equipTable);
		    
		 //System.out.println("UP"+update);
	    //For Equipment ID
		
	}
	
	public void DeleteEquipment() throws Exception {
		
				
		boolean delete = false;
		
		 int equipId = getEquipId();		 
		 String equipTable = getEquipTable();
		 
		 //System.out.println(equipTable);
		 RequestContext.getCurrentInstance().execute("history.go(0)");
		 
		 //RequestContext.getCurrentInstance().update("delete-equip-form:delete-equipName");
				 
		
		 
		 EquipmentsDAO dao = new EquipmentsDAO();
		 		 
		 delete = dao.EquipDeleteMap(equipId, equipTable);
		
		    
		// System.out.println("Deleted: "+delete);
	   
	
	}
	
	public void definePosition() throws Exception {
		
		 boolean position = false;
		
		 int equipId = getEquipId();		
		 int posX = getPositionX();
		 int posY = getPositionY();
		 String equipTable = getEquipTable();
		 
		// System.out.println("EQUIP: "+equipId);
		 ////System.out.println("TABLE: "+equipTable);
		/// System.out.println("X: "+posX);
		// System.out.println("Y: "+posY);
		 
		 EquipmentsDAO dao = new EquipmentsDAO();		
		
		 position = dao.EquipPositionMap(equipId, equipTable, posX, posY);
		 
		 //System.out.println("Positioned: "+position);
		 
		
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
	
    
    //DEFINIÇÃO PARA SALVAR SATS
    public void defineDirections(SAT sat, int numberLanes, int dir1){
    	
    	
    	switch(dir1) {
    	
    	case 1: 
    		
    		switch (numberLanes) {    		
			case 2: sat.setFaixa1("N"); sat.setFaixa2("S"); break;
			case 3: sat.setFaixa1("N"); sat.setFaixa2("S"); sat.setFaixa3("S"); break;
			case 4: sat.setFaixa1("N"); sat.setFaixa2("N"); sat.setFaixa3("S"); sat.setFaixa4("S"); break;
			case 5:  sat.setFaixa1("N"); sat.setFaixa2("N"); sat.setFaixa3("N"); sat.setFaixa4("S"); sat.setFaixa5("S"); break;
			case 6: sat.setFaixa1("N"); sat.setFaixa2("N"); sat.setFaixa3("N"); sat.setFaixa4("S"); sat.setFaixa5("S"); sat.setFaixa6("S"); break;
			case 7: sat.setFaixa1("N"); sat.setFaixa2("N"); sat.setFaixa3("N"); sat.setFaixa4("N"); sat.setFaixa5("S"); sat.setFaixa6("S"); sat.setFaixa7("S"); break;
			case 8: sat.setFaixa1("N"); sat.setFaixa2("N"); sat.setFaixa3("N"); sat.setFaixa4("N"); sat.setFaixa5("S"); sat.setFaixa6("S"); sat.setFaixa7("S"); sat.setFaixa8("S");break;
					
			}; break;
    	
    	case 2: 
    		
    		switch (numberLanes) {    		
			case 2: sat.setFaixa1("S"); sat.setFaixa2("N"); break;
			case 3: sat.setFaixa1("S"); sat.setFaixa2("N"); sat.setFaixa3("N"); break;
			case 4: sat.setFaixa1("S"); sat.setFaixa2("S"); sat.setFaixa3("N"); sat.setFaixa4("N"); break;
			case 5:  sat.setFaixa1("S"); sat.setFaixa2("S"); sat.setFaixa3("S"); sat.setFaixa4("N"); sat.setFaixa5("N"); break;
			case 6: sat.setFaixa1("S"); sat.setFaixa2("S"); sat.setFaixa3("S"); sat.setFaixa4("N"); sat.setFaixa5("N"); sat.setFaixa6("N"); break;
			case 7: sat.setFaixa1("S"); sat.setFaixa2("S"); sat.setFaixa3("S"); sat.setFaixa4("S"); sat.setFaixa5("N"); sat.setFaixa6("N"); sat.setFaixa7("N"); break;
			case 8: sat.setFaixa1("S"); sat.setFaixa2("S"); sat.setFaixa3("S"); sat.setFaixa4("S"); sat.setFaixa5("N"); sat.setFaixa6("N"); sat.setFaixa7("N"); sat.setFaixa8("N");break;
					
			}; break;	
    		    		    	
    	case 3: 	
    		
    		switch (numberLanes) {    		
			case 2: sat.setFaixa1("L"); sat.setFaixa2("O"); break;
			case 3: sat.setFaixa1("L"); sat.setFaixa2("O"); sat.setFaixa3("O"); break;
			case 4: sat.setFaixa1("L"); sat.setFaixa2("L"); sat.setFaixa3("O"); sat.setFaixa4("O"); break;
			case 5:  sat.setFaixa1("L"); sat.setFaixa2("L"); sat.setFaixa3("L"); sat.setFaixa4("O"); sat.setFaixa5("O"); break;
			case 6: sat.setFaixa1("L"); sat.setFaixa2("L"); sat.setFaixa3("L"); sat.setFaixa4("O"); sat.setFaixa5("O"); sat.setFaixa6("O"); break;
			case 7: sat.setFaixa1("L"); sat.setFaixa2("L"); sat.setFaixa3("L"); sat.setFaixa4("L"); sat.setFaixa5("O"); sat.setFaixa6("O"); sat.setFaixa7("O"); break;
			case 8: sat.setFaixa1("L"); sat.setFaixa2("L"); sat.setFaixa3("L"); sat.setFaixa4("L"); sat.setFaixa5("O"); sat.setFaixa6("O"); sat.setFaixa7("O"); sat.setFaixa8("O");break;
					
			}; break;
      
		
    	case 4: 
    		switch (numberLanes) {    		
			case 2: sat.setFaixa1("O"); sat.setFaixa2("L"); break;
			case 3: sat.setFaixa1("O"); sat.setFaixa2("L"); sat.setFaixa3("L"); break;
			case 4: sat.setFaixa1("O"); sat.setFaixa2("O"); sat.setFaixa3("L"); sat.setFaixa4("L"); break;
			case 5:  sat.setFaixa1("O"); sat.setFaixa2("O"); sat.setFaixa3("O"); sat.setFaixa4("L"); sat.setFaixa5("L"); break;
			case 6: sat.setFaixa1("O"); sat.setFaixa2("O"); sat.setFaixa3("O"); sat.setFaixa4("L"); sat.setFaixa5("L"); sat.setFaixa6("L"); break;
			case 7: sat.setFaixa1("O"); sat.setFaixa2("O"); sat.setFaixa3("O"); sat.setFaixa4("O"); sat.setFaixa5("L"); sat.setFaixa6("L"); sat.setFaixa7("L"); break;
			case 8: sat.setFaixa1("O"); sat.setFaixa2("O"); sat.setFaixa3("O"); sat.setFaixa4("O"); sat.setFaixa5("L"); sat.setFaixa6("L"); sat.setFaixa7("L"); sat.setFaixa8("L");break;
					
			}; break;   		
    	
    	}
    	
    	
    	
    	
    }
    

}
