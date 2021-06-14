package br.com.tracevia.webapp.controller.meteo.vs;

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
import br.com.tracevia.webapp.model.meteo.sv.SV;
import br.com.tracevia.webapp.model.meteo.sv.SvPanel;
import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.MessagesUtil;

@ManagedBean(name="svPanelBean")
@RequestScoped
public class SvPanelController {
	
	private SvPanel panel;
	private List<SelectItem> equipments;
	
	LocaleUtil localeLabel, localeCalendar, localeSV;
	
	MessagesUtil message;
	
	EquipmentsDAO dao;
	MeteoDAO mtoDao;	
	
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

	public SvPanel getPanel() {
		return panel;
	}
	
	public void setPanel(SvPanel panel) {
		this.panel = panel;
	}

	public List<SelectItem> getEquipments() {
		return equipments;
	}

	@PostConstruct
	public void initialize() {
		
		localeLabel = new LocaleUtil();	
		localeLabel.getResourceBundle(LocaleUtil.LABELS_SV);
		
		localeSV = new LocaleUtil();	
		localeSV.getResourceBundle(LocaleUtil.MESSAGES_SV);
		
		/* EQUIPMENTS SELECTION */
		panel = new SvPanel();
		equipments = new ArrayList<SelectItem>();	
								
		List<? extends Equipments> listVS = new ArrayList<SV>();  
					
		try {
			
			 dao = new EquipmentsDAO();		 
			 listVS = dao.EquipmentSelectOptions("sv");
			 			
		} catch (Exception e1) {			
			e1.printStackTrace();
		}
			
		if(!listVS.isEmpty()) {
		
		for (Equipments e : listVS) {
			SelectItem s = new SelectItem();
			s.setValue(e.getEquip_id());
			s.setLabel(e.getNome());
			equipments.add(s);		
		}
					
		//Initialize with first register
		station = String.valueOf(listVS.get(0).getEquip_id());
		
		station_name = listVS.get(0).getNome();
		
	    //Initialize Panel Values
		InitializePanelValues();
		
	} else { station_name = "Default"; initPanelZero(panel); }
		
		
	}
	
	//Inicializar Painel
	public void InitializePanelValues(){
		
		try {					
			
			message = new MessagesUtil();
			mtoDao = new MeteoDAO();
			panel = new SvPanel();
					
			panel = mtoDao.SvPanelInformation(station);
														
			if(panel == null) {
				
			   panel = new SvPanel(); // Inst�ncia o objeto novamente.
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
			panel = new SvPanel();				
			panel = mtoDao.SvPanelInformation(station);	
						
			//Nome do Equipamento
			for(SelectItem s : equipments) {							
				if(station.equals(String.valueOf(s.getValue())))			
					station_name = s.getLabel();				
			}
						
			if(panel == null) {
				
				panel = new SvPanel();	// Inst�ncia o objeto novamente.
				initPanelZero(panel);				
				message.InfoMessage(localeSV.getStringKey("sv_message_records_not_found_title"),localeSV.getStringKey("sv_message_equipment_no_data"));
			}
			
			RequestContext.getCurrentInstance().execute("checkMtoStatus();");			
		  								
			}
			
		} catch (Exception e) {			
			e.printStackTrace();
		}		
	}	
	
	//PREENCHER CASO N�O HAJA VALORES
	public void initPanelZero(SvPanel panel) {
					
		panel.setAmbient_temperature(0);
		panel.setVisibility(0);
		panel.setStatus(0);
		panel.setBattery(0);
		panel.setLine_volts(0);	
		
	}

}
