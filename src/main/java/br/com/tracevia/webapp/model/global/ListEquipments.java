package br.com.tracevia.webapp.model.global;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.controller.global.LoginAccountBean;
import br.com.tracevia.webapp.model.cftv.CFTV;
import br.com.tracevia.webapp.model.colas.Colas;
import br.com.tracevia.webapp.model.comms.COMMS;
import br.com.tracevia.webapp.model.dai.DAI;
import br.com.tracevia.webapp.model.dms.DMS;
import br.com.tracevia.webapp.model.ocr.OCR;
import br.com.tracevia.webapp.model.meteo.mto.MTO;
import br.com.tracevia.webapp.model.meteo.sv.SV;
import br.com.tracevia.webapp.model.sat.SAT;
import br.com.tracevia.webapp.model.sos.SOS;
import br.com.tracevia.webapp.model.speed.Speed;
import br.com.tracevia.webapp.model.wim.WIM;

@ManagedBean(name="listEquips")
@ViewScoped
public class ListEquipments {
	
	List<listEquips> equips;
	
	List<? extends Equipments> cftvList; 
	List<? extends Equipments> colasList; 
	List<? extends Equipments> commsList; 
	List<? extends Equipments> dmsList; 
	List<? extends Equipments> daiList; 
	List<? extends Equipments> mtoList; 
	List<? extends Equipments> ocrList; 
	List<? extends Equipments> satList; 
	List<? extends Equipments> speedList; 
	List<? extends Equipments> sosList; 
	List<? extends Equipments> svList; 
	List<? extends Equipments> wimList; 
	
	@ManagedProperty("#{loginAccount}")
	private LoginAccountBean login;
	
	public List<listEquips> getEquips() {
		return equips;
	}
	
	public void setEquips(List<listEquips> equips) {
		this.equips = equips;
	}
				
	public LoginAccountBean getLogin() {
		return login;
	}

	public void setLogin(LoginAccountBean login) {
		this.login = login;
	}
	

	public List<? extends Equipments> getCftvList() {
		return cftvList;
	}

	public List<? extends Equipments> getColasList() {
		return colasList;
	}

	public List<? extends Equipments> getCommsList() {
		return commsList;
	}

	public List<? extends Equipments> getDmsList() {
		return dmsList;
	}

	public List<? extends Equipments> getDaiList() {
		return daiList;
	}

	public List<? extends Equipments> getMtoList() {
		return mtoList;
	}

	public List<? extends Equipments> getOcrList() {
		return ocrList;
	}

	public List<? extends Equipments> getSatList() {
		return satList;
	}

	public List<? extends Equipments> getSpeedList() {
		return speedList;
	}

	public List<? extends Equipments> getSosList() {
		return sosList;
	}

	public List<? extends Equipments> getSvList() {
		return svList;
	}

	public List<? extends Equipments> getWimList() {
		return wimList;
	}
	
	@PostConstruct
	public void initalize() {
		
		BuildEquipments();
		
	}
	
	public class listEquips {
				
		private boolean value;
		private boolean mainMenu;
		private String module;
		private List<? extends Equipments> list;
		private double voltage;
		
		listEquips(String module, boolean value, List<? extends Equipments> list, double voltage) {
			this.value = value;	
			this.module = module;		
			this.list = list;
			this.voltage = voltage;
		}
		
		
		listEquips(String module, boolean value, boolean mainMenu, List<? extends Equipments> list, double voltage) {
			this.value = value;	
			this.module = module;
			this.mainMenu = mainMenu;
			this.list = list;
			this.voltage = voltage;
		}

		public String getModule() {
			return module;
		}
		
		public void setModule(String module) {
			this.module = module;
		}

		public boolean getValue() {
			return value;
		}
							
		public boolean isMainMenu() {
			return mainMenu;
		}


		public void setMainMenu(boolean mainMenu) {
			this.mainMenu = mainMenu;
		}


		public void setValue(boolean value) {
			this.value = value;
		}


		public List<? extends Equipments> getList() {
			return list;
		}
		
		public double getVoltagem() {
			return voltage;		

	   }
	}
	
	public void BuildEquipments() {
		
		equips = new ArrayList<listEquips>();			
						
		try {	
			
			try {
						
				CFTV cftv =  new CFTV();
				Colas colas = new Colas();
				COMMS comms = new COMMS();
				DAI dai = new DAI();
				DMS dms = new DMS();
				OCR ocr =  new OCR();
				MTO mto =  new MTO();
				SV sv = new SV();
				SAT sat = new SAT();
				SOS sos = new SOS();
				Speed speed =  new Speed();			
				WIM wim =  new WIM();
				
				    if(login.getLoad().isEn_cftv())	{	
				    					  					
					cftvList = cftv.listEquipments("cftv"); 
					equips.add(new listEquips("cftv", login.getLoad().isEn_cftv(), cftvList, login.getLoad().getVoltage_cftv()));
					
				    }
					
					if(login.getLoad().isEn_colas()) {
						
					colasList = colas.listEquipments("colas"); 
					equips.add(new listEquips("colas", login.getLoad().isEn_colas(), colasList, login.getLoad().getVoltage_colas()));
					
					}
					
					if(login.getLoad().isEn_comms()) {
						
					commsList = comms.listEquipments("comms");	
					equips.add(new listEquips("comms", login.getLoad().isEn_comms(), commsList, login.getLoad().getVoltage_comms()));
					
					}
					
					if(login.getLoad().isEn_dai()) {
						
					daiList = dai.listEquipments("dai");	
					equips.add(new listEquips("dai", login.getLoad().isEn_dai(), daiList, login.getLoad().getVoltage_dai()));
					
					}
					
					if(login.getLoad().isEn_pmv()) {
						
					dmsList = dms.listDMSEquipments();	
					equips.add(new listEquips("dms", login.getLoad().isEn_pmv(), dmsList, login.getLoad().getVoltage_pmv()));
					
					}
					
					if(login.getLoad().isEn_ocr()) {
						
					ocrList = ocr.listEquipments("ocr");	
					equips.add(new listEquips("ocr", login.getLoad().isEn_ocr(), ocrList, login.getLoad().getVoltage_ocr()));
					
					}
					
					if(login.getLoad().isEn_mto()) {
						
					mtoList = mto.listEquipments("mto");	
					equips.add(new listEquips("mto", login.getLoad().isEn_mto(), login.getLoad().isEn_meteo(), mtoList, login.getLoad().getVoltage_mto()));
								
					}
					
					if(login.getLoad().isEn_sv()) {
						
					svList = sv.listEquipments("sv");	
					equips.add(new listEquips("sv", login.getLoad().isEn_sv(), login.getLoad().isEn_meteo(), svList, login.getLoad().getVoltage_sv()));
											
					}
					
					if(login.getLoad().isEn_sat()) {
						
					satList = sat.listSatEquipments();	
					equips.add(new listEquips("sat", login.getLoad().isEn_sat(), satList, login.getLoad().getVoltage_sat()));
					
					}
					
					if(login.getLoad().isEn_sos()) {
						
					sosList = sos.listEquipments("sos");
					equips.add(new listEquips("sos", login.getLoad().isEn_sos(), sosList, login.getLoad().getVoltage_sos()));
					
					}
					
					if(login.getLoad().isEn_speed()) {
						
					speedList = speed.listEquipments("speed");	
					equips.add(new listEquips("speed", login.getLoad().isEn_speed(), speedList, login.getLoad().getVoltage_speed()));
									
					}
					
					if(login.getLoad().isEn_wim()) {
						
					wimList = wim.listEquipments("wim")	;
					equips.add(new listEquips("wim", login.getLoad().isEn_wim(), wimList, login.getLoad().getVoltage_wim()));
				
					}
					
            }catch(IndexOutOfBoundsException ex) {}
		
		}catch(Exception ex) {}	
		
	}
	  
}
