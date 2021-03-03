package br.com.tracevia.webapp.model.global;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.model.cftv.CFTV;
import br.com.tracevia.webapp.model.colas.Colas;
import br.com.tracevia.webapp.model.comms.COMMS;
import br.com.tracevia.webapp.model.dai.DAI;
import br.com.tracevia.webapp.model.dms.DMS;
import br.com.tracevia.webapp.model.lpr.LPR;
import br.com.tracevia.webapp.model.mto.MTO;
import br.com.tracevia.webapp.model.sat.SAT;
import br.com.tracevia.webapp.model.sos.SOS;
import br.com.tracevia.webapp.model.speed.Speed;
import br.com.tracevia.webapp.model.wim.WIM;

@ManagedBean(name="listEquips")
@ViewScoped
public class ListEquipments {
	List<listEquips> equips;
	
	public List<listEquips> getEquips() {
		return equips;
	}
	
	public void setEquips(List<listEquips> equips) {
		this.equips = equips;
	}
	
	LoadStartupModules load;
	
	@PostConstruct
	public void initalize() {
		
		CreateMapEquipment();
		
	}
		
	public LoadStartupModules getLoad() {
		return load;
	}

	public void setLoad(LoadStartupModules load) {
		this.load = load;
	}

	public class listEquips {
		private boolean value;	
		private List<? extends Equipments> list;
		
		listEquips(boolean value, List<? extends Equipments> list) {
			this.value = value;			
			this.list = list;
		}
		
		public boolean getValue() {
			return value;
		}
			
		public List<? extends Equipments> getList() {
			return list;
		}

	}
	
	public void CreateMapEquipment() {
		equips = new ArrayList<listEquips>();
		
		load = new LoadStartupModules();
		load.startupComponents();
						
		try {	
			
			try {
						
				CFTV cftv =  new CFTV();
				Colas colas = new Colas();
				COMMS comms = new COMMS();
				DAI dai = new DAI();
				DMS dms = new DMS();
				LPR lpr =  new LPR();
				MTO mto =  new MTO();
				SAT sat = new SAT();
				SOS sos = new SOS();
				Speed speed =  new Speed();
				WIM wim =  new WIM();
			
				if(load.isEn_cftv())			
				equips.add(new listEquips(load.isEn_cftv(), cftv.listEquipments("cftv")));
				
				if(load.isEn_colas())
				equips.add(new listEquips(load.isEn_colas(), colas.listEquipments("colas")));
				
				if(load.isEn_comms())
				equips.add(new listEquips(load.isEn_comms(), comms.listEquipments("comms")));
				
				if(load.isEn_dai())
				equips.add(new listEquips(load.isEn_dai(), dai.listEquipments("dai")));
				
				if(load.isEn_pmv())
				equips.add(new listEquips(load.isEn_pmv(), dms.listEquipments("pmv")));
				
				if(load.isEn_lpr())
				equips.add(new listEquips(load.isEn_lpr(), lpr.listEquipments("lpr")));
				
				if(load.isEn_mto())
				equips.add(new listEquips(load.isEn_mto(), mto.listEquipments("mto")));
				
				if(load.isEn_sat())
				equips.add(new listEquips(load.isEn_sat(), sat.listSatEquipments()));
				
				if(load.isEn_sos())
				equips.add(new listEquips(load.isEn_sos(), sos.listEquipments("sos")));
				
				if(load.isEn_speed())
				equips.add(new listEquips(load.isEn_speed(), speed.listEquipments("speed")));
				
				if(load.isEn_wim())
				equips.add(new listEquips(load.isEn_wim(), wim.listEquipments("wim")));
				
					
            }catch(IndexOutOfBoundsException ex) {}
		
		}catch(Exception ex) {}	
		
	}
}
