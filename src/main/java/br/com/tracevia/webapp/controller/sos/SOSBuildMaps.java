package br.com.tracevia.webapp.controller.sos;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.sos.SOS;

@ManagedBean(name="sosLinearView")
@ViewScoped
public class SOSBuildMaps {
	
	List<? extends Equipments> sosList; 
		
	public List<? extends Equipments> getSosList() {
		return sosList;
	}

	public void setSosList(List<? extends Equipments> sosList) {
		this.sosList = sosList;
	}

	@PostConstruct
	public void initalize() {
		
		CreateLinearEquipment();
		
	}
	
	public void CreateLinearEquipment() {
						
		try {	
		
		try {
			
			sosList = new ArrayList<SOS>();
			
			SOS sos = new SOS();					
			sosList = sos.listEquipments("sos");		
				
            }catch(IndexOutOfBoundsException ex) {}
		
		}catch(Exception ex) {}		
						
	}

}
