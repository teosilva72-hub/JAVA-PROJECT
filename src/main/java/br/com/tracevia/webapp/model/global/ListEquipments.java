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
	
	@PostConstruct
	public void initalize() {
		
		CreateMapEquipment();
		
	}
	
	public class listEquips {
		private boolean val1;
		private boolean val2;
		private List<Equipments> list;
		
		listEquips(boolean val1, boolean val2, List<Equipments> list) {
			this.val1 = val1;
			this.val2 = val2;
			this.list = list;
		}
		
		public boolean getVal1() {
			return val1;
		}
		
		public boolean getVal2() {
			return val2;
		}

		public List<Equipments> getList() {
			return list;
		}

	}
	
	public void CreateMapEquipment() {
		equips = new ArrayList<listEquips>();
		LoadStartupModules load = new LoadStartupModules();
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
				equips.add(new listEquips(load.isEn_cftv(), load.isRt_cftv(), cftv.listEquipments("cftv")));
				
				if(load.isEn_colas())
				equips.add(new listEquips(load.isEn_colas(), load.isRt_colas(), colas.listEquipments("colas")));
				
				if(load.isEn_comms())
				equips.add(new listEquips(load.isEn_comms(), load.isRt_comms(), comms.listEquipments("comms")));
				
				if(load.isEn_dai())
				equips.add(new listEquips(load.isEn_dai(), load.isRt_dai(), dai.listEquipments("dai")));
				
				if(load.isEn_pmv())
				equips.add(new listEquips(load.isEn_pmv(), load.isRt_pmv(), dms.listEquipments("pmv")));
				
				if(load.isEn_lpr())
				equips.add(new listEquips(load.isEn_lpr(), load.isRt_lpr(), lpr.listEquipments("lpr")));
				
				if(load.isEn_mto())
				equips.add(new listEquips(load.isEn_mto(), load.isRt_mto(), mto.listEquipments("mto")));
				
				if(load.isEn_sat())
				equips.add(new listEquips(load.isEn_sat(), load.isRt_sat(), sat.listEquipments("sat")));
				
				if(load.isEn_sos())
				equips.add(new listEquips(load.isEn_sos(), load.isRt_sos(), sos.listEquipments("sos")));
				
				if(load.isEn_speed())
				equips.add(new listEquips(load.isEn_speed(), load.isRt_speed(), speed.listEquipments("speed")));
				
				if(load.isEn_wim())
				equips.add(new listEquips(load.isEn_wim(), load.isRt_wim(), wim.listEquipments("wim")));
				
					
            }catch(IndexOutOfBoundsException ex) {}
		
		}catch(Exception ex) {}	
		
	}
}
