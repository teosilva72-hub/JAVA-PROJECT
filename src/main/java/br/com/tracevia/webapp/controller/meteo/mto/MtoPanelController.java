package br.com.tracevia.webapp.controller.meteo.mto;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.meteo.MeteoDAO;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.meteo.mto.MTO;
import br.com.tracevia.webapp.model.meteo.mto.MtoPanel;
import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.MessagesUtil;

@ManagedBean(name="mtoPanelBean")
@RequestScoped
public class MtoPanelController {
	
	private MtoPanel panel;
	private List<SelectItem> equipments;
	
	LocaleUtil localeLabel, localeCalendar, localeMto;
	
	MessagesUtil message;
	
	EquipmentsDAO dao;
	MeteoDAO mtoDao;	
	
	private String station, station_name, type;
			
	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}
	
	public String getStation_name() {
		return station_name;
	}

	public void setStation_name(String station_name) {
		this.station_name = station_name;
	}

	public MtoPanel getPanel() {
		return panel;
	}
	
	public void setPanel(MtoPanel panel) {
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
		
		localeLabel = new LocaleUtil();	
		localeLabel.getResourceBundle(LocaleUtil.LABELS_MTO);
		
		localeMto = new LocaleUtil();	
		localeMto.getResourceBundle(LocaleUtil.MESSAGES_MTO);
		
		/* EQUIPMENTS SELECTION */
		panel = new MtoPanel();
		equipments = new ArrayList<SelectItem>();		
				
		List<? extends Equipments> listMto = new ArrayList<MTO>();  
					
		try {
			
			 dao = new EquipmentsDAO();		 
			 listMto = dao.EquipmentSelectOptions("mto");
			 			
		} catch (Exception e1) {			
			e1.printStackTrace();
		}
				
		if(!listMto.isEmpty()) {
		
		for (Equipments e : listMto) {
			SelectItem s = new SelectItem();
			s.setValue(e.getEquip_id());
			s.setLabel(e.getNome());
			equipments.add(s);		
		}
					
		//Initialize with first register
		station = String.valueOf(listMto.get(0).getEquip_id());
		
		station_name = listMto.get(0).getNome();		
	 
		InitializePanelValues();  //Initialize Panel Values
		
	}  else { station_name = "Default"; initPanelZero(panel); }
		
	}
	
	//Inicializar Painel
	public void InitializePanelValues(){
		
		try {					
			
			message = new MessagesUtil();
			mtoDao = new MeteoDAO();
			panel = new MtoPanel();
			
			type = mtoDao.MtoPanelType(station);
					
			if(!type.equals("RS"))
			  RequestContext.getCurrentInstance().execute("$('#card-road-temp').css('display', 'none')");
			
			else  RequestContext.getCurrentInstance().execute("$('#card-road-temp').css('display', 'block')");
					
			panel = mtoDao.MtoPanelInformation(station);
														
			if(panel == null) {
				
			   panel = new MtoPanel(); // Inst�ncia o objeto novamente.
			   initPanelZero(panel);
			  		  
			 }		
			
			RequestContext.getCurrentInstance().execute("checkMtoStatus();");	
			
		} catch (Exception e) {			
			e.printStackTrace();
		}		
	}	
	
	//Information to Update
	public void GetPanelInformation(){
		
		try {
											
			if(station != null) {
										
			mtoDao = new MeteoDAO();	
			message = new MessagesUtil();
			panel = new MtoPanel();		
						
			type = mtoDao.MtoPanelType(station);
			
			if(!type.equals("RS"))
			  RequestContext.getCurrentInstance().execute("$('#card-road-temp').css('display', 'none')");
			
			else  RequestContext.getCurrentInstance().execute("$('#card-road-temp').css('display', 'block')");
			
			panel = mtoDao.MtoPanelInformation(station);	
						
			//Nome do Equipamento
			for(SelectItem s : equipments) {							
				if(station.equals(String.valueOf(s.getValue())))			
					station_name = s.getLabel();				
			}
						
			if(panel == null) {
				
				panel = new MtoPanel();	// Inst�ncia o objeto novamente.
				initPanelZero(panel);				
				message.InfoMessage(localeMto.getStringKey("mto_message_records_not_found_title"),localeMto.getStringKey("mto_message_equipment_no_data"));
			}
			
			RequestContext.getCurrentInstance().execute("checkMtoStatus();");			
		  								
			}
			
		} catch (Exception e) {			
			e.printStackTrace();
		}		
	}	
	
	//PREENCHER CASO N�O HAJA VALORES
	public void initPanelZero(MtoPanel panel) {
				
		panel.setAtmPressure(0);
		panel.setRelative_humidity(0);
		panel.setAbsolute_preciptation(0);
		panel.setWind_speed(0);
		panel.setWind_direction(0);  
		panel.setTemperature(0);
		panel.setVisibility(0);
		panel.setStatus(0);
		panel.setBattery(0);
		panel.setLine_volts(0);		
		panel.setRoad_temperature(0);
		
	}

}
