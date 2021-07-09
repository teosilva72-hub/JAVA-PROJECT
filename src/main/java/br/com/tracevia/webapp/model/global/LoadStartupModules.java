package br.com.tracevia.webapp.model.global;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import br.com.tracevia.webapp.cfg.ModulesEnum;
import br.com.tracevia.webapp.dao.global.ModulesDAO;

public class LoadStartupModules {  
	
	private List<Modules> modules;
	private boolean en_cftv, en_colas, en_comms, en_dai, en_ocr, en_meteo, en_mto, en_occ, en_pmv, en_sat, en_sos, en_speed, en_videowall, en_sv, en_wim;
	private double voltage_cftv, voltage_colas, voltage_comms, voltage_dai, voltage_ocr, voltage_mto, voltage_pmv, voltage_sat, voltage_sos, voltage_speed, voltage_videowall, 
	voltage_sv, voltage_wim;

	public List<Modules> getModules() {
		return modules;
	}

	public void setModules(List<Modules> modules) {
		this.modules = modules;
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

	public boolean isEn_ocr() {
		return en_ocr;
	}

	public void setEn_ocr(boolean en_lpr) {
		this.en_ocr = en_lpr;
	}

	public boolean isEn_meteo() {
		return en_meteo;
	}

	public void setEn_meteo(boolean en_meteo) {
		this.en_meteo = en_meteo;
	}

	public boolean isEn_mto() {
		return en_mto;
	}

	public void setEn_mto(boolean en_mto) {
		this.en_mto = en_mto;
	}

	public boolean isEn_occ() {
		return en_occ;
	}

	public void setEn_occ(boolean en_occ) {
		this.en_occ = en_occ;
	}

	public boolean isEn_pmv() {
		return en_pmv;
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
	
	public boolean isEn_sv() {
		return en_sv;
	}

	public void setEn_sv(boolean en_sv) {
		this.en_sv = en_sv;
	}

	public boolean isEn_wim() {
		return en_wim;
	}

	public void setEn_wim(boolean en_wim) {
		this.en_wim = en_wim;
	}

	public double getVoltage_cftv() {
		return voltage_cftv;
	}

	public void setVoltage_cftv(double voltage_cftv) {
		this.voltage_cftv = voltage_cftv;
	}

	public double getVoltage_colas() {
		return voltage_colas;
	}

	public void setVoltage_colas(double voltage_colas) {
		this.voltage_colas = voltage_colas;
	}

	public double getVoltage_comms() {
		return voltage_comms;
	}

	public void setVoltage_comms(double voltage_comms) {
		this.voltage_comms = voltage_comms;
	}

	public double getVoltage_dai() {
		return voltage_dai;
	}

	public void setVoltage_dai(double voltage_dai) {
		this.voltage_dai = voltage_dai;
	}

	public double getVoltage_ocr() {
		return voltage_ocr;
	}

	public void setVoltage_ocr1(double voltage_ocr) {
		this.voltage_ocr = voltage_ocr;
	}

	public double getVoltage_mto() {
		return voltage_mto;
	}

	public void setVoltage_mto(double voltage_mto) {
		this.voltage_mto = voltage_mto;
	}

	public double getVoltage_pmv() {
		return voltage_pmv;
	}

	public void setVoltage_pmv(double voltage_pmv) {
		this.voltage_pmv = voltage_pmv;
	}

	public double getVoltage_sat() {
		return voltage_sat;
	}

	public void setVoltage_sat(double voltage_sat) {
		this.voltage_sat = voltage_sat;
	}

	public double getVoltage_sos() {
		return voltage_sos;
	}

	public void setVoltage_sos(double voltage_sos) {
		this.voltage_sos = voltage_sos;
	}

	public double getVoltage_speed() {
		return voltage_speed;
	}

	public void setVoltage_speed(double voltage_speed) {
		this.voltage_speed = voltage_speed;
	}

	public double getVoltage_videowall() {
		return voltage_videowall;
	}

	public void setVoltage_videowall(double voltage_videowall) {
		this.voltage_videowall = voltage_videowall;
	}
	
	public double getVoltage_sv() {
		return voltage_sv;
	}

	public void setVoltage_sv(double voltage_sv) {
		this.voltage_sv = voltage_sv;
	}

	public double getVoltage_wim() {
		return voltage_wim;
	}

	public void setVoltage_wim(double voltage_wim) {
		this.voltage_wim = voltage_wim;
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
			en_ocr = false;
			en_mto = false;
			en_occ = false;
			en_pmv = false;
			en_meteo = false;
			en_sv = false;
			en_sat = false;
			en_sos = false;
			en_speed = false;			
			en_videowall = false;
			en_wim = false;	
									
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
											
		for(Modules mod: modules) {
							
			if(mod.getModule().equals(ModulesEnum.CFTV.getModule()) && mod.isEnabled()) {			
				en_cftv = mod.isEnabled();	
				voltage_cftv = mod.getBattery_voltage();
			
			}else if(mod.getModule().equals(ModulesEnum.COLAS.getModule()) && mod.isEnabled()) { 		
				en_colas = mod.isEnabled();				
				voltage_colas = mod.getBattery_voltage();
				
			}else if(mod.getModule().equals(ModulesEnum.COMMS.getModule()) && mod.isEnabled()) {			
				en_comms = mod.isEnabled();
				voltage_comms = mod.getBattery_voltage();
				   
		    }else if(mod.getModule().equals(ModulesEnum.DAI.getModule()) && mod.isEnabled()) {				
				en_dai = mod.isEnabled();
				voltage_dai = mod.getBattery_voltage();
						
		    }else if(mod.getModule().equals(ModulesEnum.OCR.getModule()) && mod.isEnabled()) {				
				en_ocr = mod.isEnabled();
			    voltage_ocr = mod.getBattery_voltage();
			
			}else if(mod.getModule().equals(ModulesEnum.MTO.getModule()) && mod.isEnabled())	{			
				en_mto = mod.isEnabled();
			    voltage_mto = mod.getBattery_voltage();
				en_meteo = true;
		
		    }else if(mod.getModule().equals(ModulesEnum.OCC.getModule()) && mod.isEnabled())				
			    en_occ = mod.isEnabled();	   
								
		    else if(mod.getModule().equals(ModulesEnum.PMV.getModule()) && mod.isEnabled()) {				
				en_pmv = mod.isEnabled();
			    voltage_pmv = mod.getBattery_voltage();
			    
		   }else if(mod.getModule().equals(ModulesEnum.SV.getModule()) && mod.isEnabled())	{		
				en_sv = mod.isEnabled();
				voltage_sv = mod.getBattery_voltage();
				en_meteo = true;
				
		   }else if(mod.getModule().equals(ModulesEnum.SAT.getModule()) && mod.isEnabled())	{		
				en_sat = mod.isEnabled();
				voltage_sat = mod.getBattery_voltage();
				
		   } else if(mod.getModule().equals(ModulesEnum.SOS.getModule()) && mod.isEnabled())	{		  
			    en_sos = mod.isEnabled();
				voltage_sos = mod.getBattery_voltage();
						
		   }else if(mod.getModule().equals(ModulesEnum.SPEED.getModule()) && mod.isEnabled()) {			
				en_speed = mod.isEnabled();
				voltage_speed = mod.getBattery_voltage();
						
		   }else if(mod.getModule().equals(ModulesEnum.VIDEOWALL.getModule()) && mod.isEnabled()) {				
				en_videowall = mod.isEnabled();
				voltage_videowall = mod.getBattery_voltage();
								  				
		   }else if(mod.getModule().equals(ModulesEnum.WIM.getModule()) && mod.isEnabled())	{			
				en_wim = mod.isEnabled();
				voltage_wim = mod.getBattery_voltage();
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

}
