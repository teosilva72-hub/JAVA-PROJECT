package br.com.tracevia.webapp.model.global;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.controller.global.LoginAccountBean;

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
		LoginAccountBean auth = new LoginAccountBean();
		LoadStartupModules load = auth.getLoad();
		
		try {	
			
			try {
					
				CFTV cftv =  new CFTV();
				equips.add(new listEquips(load.isEn_cftv(), load.isRt_cftv(), cftv.ListMapEquipments("cftv")));
				Colas colas = new Colas();
				equips.add(new listEquips(load.isEn_colas(), load.isRt_colas(), colas.ListMapEquipments("colas")));
				COMMS comms = new COMMS();
				equips.add(new listEquips(load.isEn_comms(), load.isRt_comms(), comms.ListMapEquipments("comms")));
				DAI dai = new DAI();
				equips.add(new listEquips(load.isEn_dai(), load.isRt_dai(), dai.ListMapEquipments("dai")));
				DMS dms = new DMS();
				equips.add(new listEquips(load.isEn_pmv(), load.isRt_pmv(), dms.ListMapDMSEquipments("pmv")));
				LPR lpr =  new LPR();
				equips.add(new listEquips(load.isEn_lpr(), load.isRt_lpr(), lpr.ListMapEquipments("lpr")));
				MTO mto =  new MTO();
				equips.add(new listEquips(load.isEn_mto(), load.isRt_mto(), mto.ListMapEquipments("mto")));
				SAT sat = new SAT();
				equips.add(new listEquips(load.isEn_sat(), load.isRt_sat(), sat.ListMapEquipments("sat")));
				SOS sos = new SOS();
				equips.add(new listEquips(load.isEn_sos(), load.isRt_sos(), sos.ListMapEquipments("sos")));
				Speed speed =  new Speed();
				equips.add(new listEquips(load.isEn_speed(), load.isRt_speed(), speed.ListMapEquipments("speed")));
				WIM wim =  new WIM();
				equips.add(new listEquips(load.isEn_wim(), load.isRt_wim(), wim.ListMapEquipments("wim")));
					
            }catch(IndexOutOfBoundsException ex) {}
		
		}catch(Exception ex) {}	
		
	}
}