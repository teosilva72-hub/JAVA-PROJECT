package br.com.tracevia.webapp.controller.meteo.rs;

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
import br.com.tracevia.webapp.model.meteo.rs.RS;
import br.com.tracevia.webapp.model.meteo.rs.RsPanel;
	import br.com.tracevia.webapp.util.LocaleUtil;
	import br.com.tracevia.webapp.util.MessagesUtil;

	@ManagedBean(name="rsPanelBean")
	@RequestScoped
	public class RsPanelController {
		
		private RsPanel panel;
		private List<SelectItem> equipments;
		
		LocaleUtil localeLabel, localeCalendar, localeRs;
		
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

		public RsPanel getPanel() {
			return panel;
		}
		
		public void setPanel(RsPanel panel) {
			this.panel = panel;
		}

		public List<SelectItem> getEquipments() {
			return equipments;
		}

		@PostConstruct
		public void initialize() {
			
			localeLabel = new LocaleUtil();	
			localeLabel.getResourceBundle(LocaleUtil.LABELS_RS);
			
			localeRs = new LocaleUtil();	
			localeRs.getResourceBundle(LocaleUtil.MESSAGES_RS);
			
			/* EQUIPMENTS SELECTION */
			panel = new RsPanel();
			equipments = new ArrayList<SelectItem>();	
							
			List<? extends Equipments> listRs = new ArrayList<RS>();  
						
			try {
				
				 dao = new EquipmentsDAO();		 
				 listRs = dao.EquipmentSelectOptions("rs");
				 			
			} catch (Exception e1) {			
				e1.printStackTrace();
			}
			
			if(!listRs.isEmpty()) {				
					
			for (Equipments e : listRs) {
				SelectItem s = new SelectItem();
				s.setValue(e.getEquip_id());
				s.setLabel(e.getNome());
				equipments.add(s);		
			}
						
			//Initialize with first register
			station = String.valueOf(listRs.get(0).getEquip_id());
			
			station_name = listRs.get(0).getNome();
			
			InitializePanelValues(); //Initialize Panel Values
			
			}
			
			else initPanelZero(panel);
								
		}
		
		//Inicializar Painel
		public void InitializePanelValues(){
			
			try {					
				
				message = new MessagesUtil();
				mtoDao = new MeteoDAO();
				panel = new RsPanel();
						
				panel = mtoDao.RsPanelInformation(station);
															
				if(panel == null) {
					
				   panel = new RsPanel(); // Inst�ncia o objeto novamente.
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
				panel = new RsPanel();				
				panel = mtoDao.RsPanelInformation(station);	
							
				//Nome do Equipamento
				for(SelectItem s : equipments) {							
					if(station.equals(String.valueOf(s.getValue())))			
						station_name = s.getLabel();				
				}
							
				if(panel == null) {
					
					panel = new RsPanel();	// Inst�ncia o objeto novamente.
					initPanelZero(panel);				
					message.InfoMessage(localeRs.getStringKey("mto_message_records_not_found_title"),localeRs.getStringKey("mto_message_equipment_no_data"));
				}
				
				RequestContext.getCurrentInstance().execute("checkMtoStatus();");			
			  								
				}
				
			} catch (Exception e) {			
				e.printStackTrace();
			}		
		}	
		
		//PREENCHER CASO N�O HAJA VALORES
		public void initPanelZero(RsPanel panel) {
				
			station_name = "Default";
			panel.setRoad_temperature(0);		
			panel.setStatus(0);
			panel.setBattery(0);
			panel.setLine_volts(0);	
			
		}

	}

