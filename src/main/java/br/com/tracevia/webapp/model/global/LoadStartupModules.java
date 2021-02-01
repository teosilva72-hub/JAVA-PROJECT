package br.com.tracevia.webapp.model.global;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import br.com.tracevia.webapp.cfg.ModulesEnum;
import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.global.ModulesDAO;

public class LoadStartupModules {  
	
	private List<Modules> modules;
	private Equipments equips;
	private List<Equipments> cftv, colas, comms, dai, lpr, mto, pmv, sat, sos, speed, videowall, wim;
	private boolean en_cftv, en_colas, en_comms, en_dai, en_lpr, en_mto, en_occ, en_pmv, en_sat, en_sos, en_speed, en_videowall, en_wim;
	private boolean rt_cftv, rt_colas, rt_comms, rt_dai, rt_lpr, rt_mto, rt_pmv, rt_sat, rt_sos, rt_speed, rt_videowall, rt_wim;
	private List<listEquips> allEquips;
	

	public List<Modules> getModules() {
		return modules;
	}
				
	public Equipments getEquips() {
		return equips;
	}
	
	public void setEquips(Equipments equips) {
		this.equips = equips;
	}
		
	public List<Equipments> getCftv() {
		return cftv;
	}
	
	public List<Equipments> getColas() {
		return colas;
	}

	public List<Equipments> getComms() {
		return comms;
	}

	public List<Equipments> getDai() {
		return dai;
	}

	public List<Equipments> getLpr() {
		return lpr;
	}

	public List<Equipments> getMto() {
		return mto;
	}

	public List<Equipments> getPmv() {
		return pmv;
	}

	public List<Equipments> getSat() {
		return sat;
	}

	public List<Equipments> getSos() {
		return sos;
	}

	public List<Equipments> getSpeed() {
		return speed;
	}

	public List<Equipments> getVideowall() {
		return videowall;
	}

	public List<Equipments> getWim() {
		return wim;
	}
	
	public boolean isEn_cftv() {
		return en_cftv;
	}

	public void setEn_cftv(boolean en_cftv) {
		this.en_cftv = en_cftv;
	}
	
	public boolean isEn_colas() {
		return en_colas;
	}

	public void setEn_colas(boolean en_colas) {
		this.en_colas = en_colas;
	}

	public boolean isEn_comms() {
		return en_comms;
	}

	public void setEn_comms(boolean en_comms) {
		this.en_comms = en_comms;
	}

	public boolean isEn_dai() {
		return en_dai;
	}

	public void setEn_dai(boolean en_dai) {
		this.en_dai = en_dai;
	}

	public boolean isEn_lpr() {
		return en_lpr;
	}

	public void setEn_lpr(boolean en_lpr) {
		this.en_lpr = en_lpr;
	}

	public boolean isEn_mto() {
		return en_mto;
	}

	public void setEn_mto(boolean en_mto) {
		this.en_mto = en_mto;
	}

	public boolean isEn_pmv() {
		return en_pmv;
	}
	
	public boolean isEn_occ() {
		return en_occ;
	}

	public void setEn_occ(boolean en_occ) {
		this.en_occ = en_occ;
	}

	public void setEn_pmv(boolean en_pmv) {
		this.en_pmv = en_pmv;
	}

	public boolean isEn_sat() {
		return en_sat;
	}

	public void setEn_sat(boolean en_sat) {
		this.en_sat = en_sat;
	}

	public boolean isEn_sos() {
		return en_sos;
	}

	public void setEn_sos(boolean en_sos) {
		this.en_sos = en_sos;
	}

	public boolean isEn_speed() {
		return en_speed;
	}

	public void setEn_speed(boolean en_speed) {
		this.en_speed = en_speed;
	}

	public boolean isEn_videowall() {
		return en_videowall;
	}

	public void setEn_videowall(boolean en_videowall) {
		this.en_videowall = en_videowall;
	}

	public boolean isEn_wim() {
		return en_wim;
	}

	public void setEn_wim(boolean en_wim) {
		this.en_wim = en_wim;
	}
		
	public boolean isRt_cftv() {
		return rt_cftv;
	}

	public void setRt_cftv(boolean rt_cftv) {
		this.rt_cftv = rt_cftv;
	}
	
	public boolean isRt_colas() {
		return rt_colas;
	}

	public void setRt_colas(boolean rt_colas) {
		this.rt_colas = rt_colas;
	}

	public boolean isRt_comms() {
		return rt_comms;
	}

	public void setRt_comms(boolean rt_comms) {
		this.rt_comms = rt_comms;
	}

	public boolean isRt_dai() {
		return rt_dai;
	}

	public void setRt_dai(boolean rt_dai) {
		this.rt_dai = rt_dai;
	}

	public boolean isRt_lpr() {
		return rt_lpr;
	}

	public void setRt_lpr(boolean rt_lpr) {
		this.rt_lpr = rt_lpr;
	}

	public boolean isRt_mto() {
		return rt_mto;
	}

	public void setRt_mto(boolean rt_mto) {
		this.rt_mto = rt_mto;
	}
	
	public boolean isRt_pmv() {
		return rt_pmv;
	}

	public void setRt_pmv(boolean rt_pmv) {
		this.rt_pmv = rt_pmv;
	}

	public boolean isRt_sat() {
		return rt_sat;
	}

	public void setRt_sat(boolean rt_sat) {
		this.rt_sat = rt_sat;
	}

	public boolean isRt_sos() {
		return rt_sos;
	}

	public void setRt_sos(boolean rt_sos) {
		this.rt_sos = rt_sos;
	}

	public boolean isRt_speed() {
		return rt_speed;
	}

	public void setRt_speed(boolean rt_speed) {
		this.rt_speed = rt_speed;
	}

	public boolean isRt_videowall() {
		return rt_videowall;
	}

	public void setRt_videowall(boolean rt_videowall) {
		this.rt_videowall = rt_videowall;
	}

	public boolean isRt_wim() {
		return rt_wim;
	}

	public void setRt_wim(boolean rt_wim) {
		this.rt_wim = rt_wim;
	}

	public void setModules(List<Modules> modules) {
		this.modules = modules;
	}

	public void setCftv(List<Equipments> cftv) {
		this.cftv = cftv;
	}
	
	public void setColas(List<Equipments> colas) {
		this.colas = colas;
	}

	public void setComms(List<Equipments> comms) {
		this.comms = comms;
	}

	public void setDai(List<Equipments> dai) {
		this.dai = dai;
	}

	public void setLpr(List<Equipments> lpr) {
		this.lpr = lpr;
	}

	public void setMto(List<Equipments> mto) {
		this.mto = mto;
	}

	public void setPmv(List<Equipments> pmv) {
		this.pmv = pmv;
	}

	public void setSat(List<Equipments> sat) {
		this.sat = sat;
	}

	public void setSos(List<Equipments> sos) {
		this.sos = sos;
	}

	public void setSpeed(List<Equipments> speed) {
		this.speed = speed;
	}

	public void setVideowall(List<Equipments> videowall) {
		this.videowall = videowall;
	}

	public void setWim(List<Equipments> wim) {
		this.wim = wim;
	}
		
	/**
	  * M�todo inicializar componentes de visualiza��o
	 * @throws Exception
	 */
	public void startupComponents() {
		
		try {
			
			//Estados de habilita��o de m�dulos
			//Inicia-se todos inativos		
			//Default False (Disabled)
			en_cftv = false;
			en_colas = false;
			en_comms = false;
			en_dai = false;
			en_lpr = false;
			en_mto = false;
			en_occ = false;
			en_pmv = false;
			en_sat = false;
			en_sos = false;
			en_speed = false;
			en_videowall = false;
			en_wim = false;	
						
			//Real Time Show/Hide equipments Boolean
			//Default True (Enabled)
			rt_cftv = true;
			rt_colas = true;
			rt_comms = true;
			rt_dai = true;
			rt_lpr = true;
			rt_mto = true;			
			rt_pmv = true;
			rt_sat = true;
			rt_sos = true;
			rt_speed = true;
			rt_videowall = true;
			rt_wim = true;
			
			//Lista com m�dulos ativos
			listViewModules();
			
			//Lista de equipamentos ativos
			listViewEquipments();
																
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	  }
	
	/**
	 * M�todo para retornar lista de m�dulos ativos 
	 * @throws Exception
	 */
	public void listViewModules() throws Exception {
							
		modules = new ArrayList<Modules>();				
		ModulesDAO modDAO = new ModulesDAO();
		
		modules = modDAO.listModules();				
	}
	
	/**
	 * M�todo para retornar lista de equipmentos para cada modulo
	 * @throws Exception
	 */		

	public void listViewEquipments() throws Exception  {
		
		cftv = new ArrayList<Equipments>();
		colas = new ArrayList<Equipments>();
		comms = new ArrayList<Equipments>();
		dai = new ArrayList<Equipments>();
		lpr = new ArrayList<Equipments>();
		mto = new ArrayList<Equipments>();			
		pmv = new ArrayList<Equipments>();
		sat = new ArrayList<Equipments>();
		sos = new ArrayList<Equipments>();
		speed = new ArrayList<Equipments>();		
		videowall = new ArrayList<Equipments>();
		wim = new ArrayList<Equipments>();
		
		allEquips = new ArrayList<listEquips>();
		
		EquipmentsDAO equipDAO = new EquipmentsDAO();
							
		for(Modules mod: modules) {
			
			if(mod.getModule().equals(ModulesEnum.CFTV.getModule())) {
				cftv.addAll(equipDAO.listEquipmentsAvailables(mod));
				en_cftv = mod.isState();	
				allEquips.add(new listEquips(en_cftv, rt_cftv, cftv));
			}
			
			if(mod.getModule().equals(ModulesEnum.COLAS.getModule())) {
				colas.addAll(equipDAO.listEquipmentsAvailables(mod));
				en_colas = mod.isState();
				allEquips.add(new listEquips(en_colas, rt_colas, colas));
			}

			if(mod.getModule().equals(ModulesEnum.COMMS.getModule())) {
				comms.addAll(equipDAO.listEquipmentsAvailables(mod));
				en_comms = mod.isState();
				allEquips.add(new listEquips(en_comms, rt_comms, comms));
			}
			
			if(mod.getModule().equals(ModulesEnum.DAI.getModule())) {
				dai.addAll(equipDAO.listEquipmentsAvailables(mod));
				en_dai = mod.isState();
				allEquips.add(new listEquips(en_dai, rt_dai, dai));
			}
			
			if(mod.getModule().equals(ModulesEnum.LPR.getModule())) {
				lpr.addAll(equipDAO.listEquipmentsAvailables(mod));
				en_lpr = mod.isState();
				allEquips.add(new listEquips(en_lpr, rt_lpr, lpr));
			}
							
			if(mod.getModule().equals(ModulesEnum.MTO.getModule())) {
				mto.addAll(equipDAO.listEquipmentsAvailables(mod));
				en_mto = mod.isState();
				allEquips.add(new listEquips(en_mto, rt_mto, mto));
			}
			
			if(mod.getModule().equals(ModulesEnum.OCC.getModule())) {				
				en_occ = mod.isState();
			}
					
			if(mod.getModule().equals(ModulesEnum.PMV.getModule())) {
				pmv.addAll(equipDAO.listEquipmentsAvailables(mod));
				en_pmv = mod.isState();
				allEquips.add(new listEquips(en_pmv, rt_pmv, pmv));
			}
			
			if(mod.getModule().equals(ModulesEnum.SAT.getModule())) {
				sat.addAll(equipDAO.listEquipmentsAvailables(mod));
				en_sat = mod.isState();
				allEquips.add(new listEquips(en_sat, rt_sat, sat));
			}
			
			if(mod.getModule().equals(ModulesEnum.SOS.getModule())) {
			    sos.addAll(equipDAO.listEquipmentsAvailables(mod));	
			    en_sos = mod.isState();
			    allEquips.add(new listEquips(en_sos, rt_sos, sos));
			}
			
			if(mod.getModule().equals(ModulesEnum.SPEED.getModule())) {
				speed.addAll(equipDAO.listEquipmentsAvailables(mod));
				en_speed = mod.isState();
				allEquips.add(new listEquips(en_speed, rt_speed, speed));
			}
			
			if(mod.getModule().equals(ModulesEnum.VIDEOWALL.getModule())) {
				videowall.addAll(equipDAO.listEquipmentsAvailables(mod));
				en_videowall = mod.isState();
			}
			
			if(mod.getModule().equals(ModulesEnum.WIM.getModule())) {
				wim.addAll(equipDAO.listEquipmentsAvailables(mod));
				en_wim = mod.isState();
				allEquips.add(new listEquips(en_wim, rt_wim, wim));
			}						
		}	
	}
	
	/**
	 * M�todo para obter a mudan�a de estados
	 * @throws Exception
	 */
	public void valueChangeListener(ValueChangeEvent valueChangeEvent) {
		 		
		//System.out.println("ID: "+valueChangeEvent.getComponent().getId());			
		
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

	public List<listEquips> getAllEquips() {
		return allEquips;
	}
}
