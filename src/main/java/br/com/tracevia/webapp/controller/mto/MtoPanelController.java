package br.com.tracevia.webapp.controller.mto;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import org.apache.poi.hssf.util.PaneInformation;
import org.primefaces.context.RequestContext;

import com.google.protobuf.Value;

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.mto.MtoDAO;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.mto.MTO;
import br.com.tracevia.webapp.model.mto.MtoPanel;
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
	MtoDAO mtoDao;	
	
	private String station, station_name;
			
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
				
		for (Equipments e : listMto) {
			SelectItem s = new SelectItem();
			s.setValue(e.getEquip_id());
			s.setLabel(e.getNome());
			equipments.add(s);		
		}
					
		//Initialize with first register
		station = String.valueOf(listMto.get(0).getEquip_id());
		
		station_name = listMto.get(0).getNome();
		
	    //Initialize Panel Values
		InitializePanel();
		
	}
	
	//Inicializar Painel
	public void InitializePanel(){
		
		try {					
			
			message = new MessagesUtil();
			mtoDao = new MtoDAO();
			panel = new MtoPanel();
					
			panel = mtoDao.WeatherPanelInformation(station);
														
			if(panel == null) {
				
			   panel = new MtoPanel(); // Instância o objeto novamente.
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
										
			mtoDao = new MtoDAO();	
			message = new MessagesUtil();
			panel = new MtoPanel();				
			panel = mtoDao.WeatherPanelInformation(station);	
						
			//Nome do Equipamento
			for(SelectItem s : equipments) {							
				if(station.equals(String.valueOf(s.getValue())))			
					station_name = s.getLabel();				
				
			}
						
			if(panel == null) {
				
				panel = new MtoPanel();	// Instância o objeto novamente.
				initPanelZero(panel);				
				message.InfoMessage(localeMto.getStringKey("mto_message_records_not_found_title"),localeMto.getStringKey("mto_message_equipment_no_data"));
			}
			
			RequestContext.getCurrentInstance().execute("checkMtoStatus();");			
		  								
			}
			
		} catch (Exception e) {			
			e.printStackTrace();
		}		
	}	
	
	//PREENCHER CASO NÃO HAJA VALORES
	public void initPanelZero(MtoPanel panel) {
		
		panel.setAtmPressure(0);
		panel.setRelative_humidity(0);
		panel.setPreciptation_rate(0);
		panel.setPreciptation_rate_hour(0);
		panel.setWind_speed(0);
		panel.setWind_direction(0);  
		panel.setTemperature(0);
		panel.setVisibility(0);
		panel.setStatus(0);
		panel.setBattery(0);
		panel.setLine_volts(0);	
		
	}

}
