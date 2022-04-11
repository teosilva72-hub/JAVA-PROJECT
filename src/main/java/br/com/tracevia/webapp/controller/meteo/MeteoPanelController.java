package br.com.tracevia.webapp.controller.meteo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.meteo.MeteoDAO;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.ListEquipments;
import br.com.tracevia.webapp.model.meteo.MeteoPanel;
import br.com.tracevia.webapp.util.SessionUtil;

@ManagedBean(name="meteoPanelBean")
@RequestScoped
public class MeteoPanelController {
	
	private MeteoPanel panel;
	private List<SelectItem> equipments;
			
	EquipmentsDAO dao;
	MeteoDAO meteoDAO;	
	
	private String station, stationName, type;
		
	@ManagedProperty("#{listEquips}")
	private ListEquipments listEquips;
	
	public ListEquipments getListEquips() {
		return listEquips;
	}

	public void setListEquips(ListEquipments listEquips) {
		this.listEquips = listEquips;
	}

	public EquipmentsDAO getDao() {
		return dao;
	}

	public void setDao(EquipmentsDAO dao) {
		this.dao = dao;
	}

	public MeteoDAO getMeteoDAO() {
		return meteoDAO;
	}

	public void setMeteoDAO(MeteoDAO meteoDAO) {
		this.meteoDAO = meteoDAO;
	}
		
	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}
	
	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public MeteoPanel getPanel() {
		return panel;
	}

	public void setPanel(MeteoPanel panel) {
		this.panel = panel;
	}

	public List<SelectItem> getEquipments() {
		return equipments;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
		
	@PostConstruct
	public void initialize() {
								
		/* EQUIPMENTS SELECTION */
		
		panel = new MeteoPanel();
		equipments = new ArrayList<SelectItem>();		
					
		try {
					 			
		} catch (Exception e1) {			
			e1.printStackTrace();
		}
				
		if(!listEquips.getMeteoList().isEmpty()) {
		
			for (Equipments e : listEquips.getMeteoList()) {
					SelectItem s = new SelectItem();
						s.setValue(String.format("%s%s", e.getTable_id(), e.getEquip_id()));
						s.setLabel(e.getNome());
					
						equipments.add(s);		
			}
						
				// INITIALIZE FIRT VALUE
				
				station = String.valueOf(listEquips.getMeteoList().get(0).getEquip_id());				
				stationName = listEquips.getMeteoList().get(0).getNome();					
				type = listEquips.getMeteoList().get(0).getEquip_type();
				
			 
				InitializePanelValues();  //Initialize Panel Values
		
	}  else { stationName = "Default"; initPanelZero(panel); }
		
	}
	
	// -----------------------------------------------------
	
	//Inicializar Painel
	public void InitializePanelValues(){
		
		try {			
					
			meteoDAO = new MeteoDAO();
			panel = new MeteoPanel();
							
			panel = meteoDAO.MeteoPanel(station, type);
														
			if(panel == null) {
				
			   panel = new MeteoPanel(); // Inst�ncia o objeto novamente.
			   initPanelZero(panel);
			  		  
			 }		
			
			//SessionUtil.executeScript("checkMtoStatus();");	
			
		} catch (Exception e) {			
			e.printStackTrace();
		}		
	}	
	
	// -----------------------------------------------------
	
	//Information to Update
	public void GetPanelInformation(){
		
		try {
											
			if(station != null) {
										
			meteoDAO = new MeteoDAO();	
		
			panel = new MeteoPanel();		
					
			if(!type.equals("MTO")) {
				SessionUtil.executeScript("$('#card-road-temp').css('display', 'none')");
				SessionUtil.executeScript("$('#visibility').css('display', 'none')");			  
			}
			
			else {
				
				SessionUtil.executeScript("$('#card-road-temp').css('display', 'block')");
				SessionUtil.executeScript("$('#visibility').css('display', 'block')");
			}
			
			panel = meteoDAO. MeteoPanel(station, type);	
						
			// EQUIP NAME
			for(SelectItem s : equipments) {							
				if(station.equals(String.valueOf(s.getValue())))			
					stationName = s.getLabel();				
			}
						
			if(panel == null) {
				
				panel = new MeteoPanel();	// Inst�ncia o objeto novamente.
				initPanelZero(panel);				
				
				SessionUtil.executeScript("showInfoMessage();");
				SessionUtil.executeScript("hideInfoMessage();");
	
			}
			
			//SessionUtil.executeScript("checkMtoStatus();");			
		  								
			}
			
		} catch (Exception e) {			
			e.printStackTrace();
		}		
	}	
	
	// -----------------------------------------------------
	
	// PREENCHER CASO NÃO HAJA VALORES
	public void initPanelZero(MeteoPanel panel) {
				
		panel.setAtmosphericPressure(0);
		panel.setRelativeHumidity(0);
		panel.setAbsolutePreciptation(0);
		panel.setWindSpeed(0);
		panel.setWindDirection(0);  
		panel.setTemperature(0);
		panel.setVisibility(0);
		panel.setStatus(0);
		panel.setBattery(0);
		panel.setLineVolts(0);		
		panel.setRoadTemperature(0);
		
	}
	
	// -----------------------------------------------------

}
