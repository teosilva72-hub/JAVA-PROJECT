package br.com.tracevia.webapp.controller.mto;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import com.google.protobuf.Value;

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.mto.MtoDAO;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.mto.MTO;
import br.com.tracevia.webapp.model.mto.MtoPanel;
import br.com.tracevia.webapp.util.LocaleUtil;

@ManagedBean(name="mtoPanelBean")
@RequestScoped
public class MtoPanelController {
	
	private MtoPanel panel;
	private List<SelectItem> equipments;
	
	LocaleUtil localeLabel, localeCalendar;
	
	EquipmentsDAO dao;
	MtoDAO mtoDao;	
	
	private String station;
			
	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
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
		
	    //Initialize Panel Values
		InitializePanel();
		
	}
	
	//Inicializar Painel
	public void InitializePanel(){
		
		try {					
			
			mtoDao = new MtoDAO();
			panel = new MtoPanel();
			panel = mtoDao.WeatherPanelInformation(station);
						
			
		} catch (Exception e) {			
			e.printStackTrace();
		}		
	}	
	
	//Information to Update
	public void GetPanelInformation(){
		try {
								
			/*if(station != null) {
										
			mtoDao = new MtoDAO();				
			panel = new MtoPanel();				
			panel = mtoDao.WeatherPanelInformation(station);			
		  								
			}*/
			
		} catch (Exception e) {			
			e.printStackTrace();
		}		
	}	
	

}
