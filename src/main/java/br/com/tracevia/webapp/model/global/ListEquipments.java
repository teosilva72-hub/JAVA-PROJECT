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

				UserAccount actual_login = login.getLogin();
				LoadStartupModules load = login.getLoad();
				int permission_id = actual_login.getPermission_id();
											
				System.out.println(permission_id);
				    if(load.isEn_cftv())	{	
				    	
				    					  					
					cftvList = cftv.listEquipments("cftv", permission_id); 
					equips.add(new listEquips("cftv", load.isEn_cftv(), cftvList, load.getVoltage_cftv()));
					
				    }
					
					if(load.isEn_colas()) {
						
					colasList = colas.listEquipments("colas", permission_id); 
					equips.add(new listEquips("colas", load.isEn_colas(), colasList, load.getVoltage_colas()));
					
					}
					
					if(load.isEn_comms()) {
						
					commsList = comms.listEquipments("comms", permission_id);	
					equips.add(new listEquips("comms", load.isEn_comms(), commsList, load.getVoltage_comms()));
					
					}
					
					if(load.isEn_dai()) {
						
					daiList = dai.listEquipments("dai", permission_id);	
					equips.add(new listEquips("dai", load.isEn_dai(), daiList, load.getVoltage_dai()));
					
					}
					
					if(load.isEn_pmv()) {
						
					dmsList = dms.listDMSEquipments(permission_id);	
					equips.add(new listEquips("dms", load.isEn_pmv(), dmsList, load.getVoltage_pmv()));
					
					}
					
					if(load.isEn_ocr()) {
						
					ocrList = ocr.listEquipments("ocr", permission_id);	
					equips.add(new listEquips("ocr", load.isEn_ocr(), ocrList, load.getVoltage_ocr()));
					
					}
					
					if(load.isEn_mto()) {
						
					mtoList = mto.listEquipments("mto", permission_id);	
					equips.add(new listEquips("mto", load.isEn_mto(), load.isEn_meteo(), mtoList, load.getVoltage_mto()));
								
					}
					
					if(load.isEn_sv()) {
						
					svList = sv.listEquipments("sv", permission_id);	
					equips.add(new listEquips("sv", load.isEn_sv(), load.isEn_meteo(), svList, load.getVoltage_sv()));
											
					}
					
					if(load.isEn_sat()) {
						
					satList = sat.listSatEquipments(permission_id);	
					equips.add(new listEquips("sat", load.isEn_sat(), satList, load.getVoltage_sat()));
					
					}
					
					if(load.isEn_sos()) {
						
					sosList = sos.listEquipments("sos", permission_id);
					equips.add(new listEquips("sos", load.isEn_sos(), sosList, load.getVoltage_sos()));
					
					}
					
					if(load.isEn_speed()) {
						
					speedList = speed.listEquipments("speed", permission_id);	
					equips.add(new listEquips("speed", load.isEn_speed(), speedList, load.getVoltage_speed()));
									
					}
					
					if(load.isEn_wim()) {
						
					wimList = wim.listEquipments("wim", permission_id)	;
					equips.add(new listEquips("wim", load.isEn_wim(), wimList, load.getVoltage_wim()));
				
					}
					
            }catch(IndexOutOfBoundsException ex) {}
		
		}catch(Exception ex) {}	
		
	}
	  
}
