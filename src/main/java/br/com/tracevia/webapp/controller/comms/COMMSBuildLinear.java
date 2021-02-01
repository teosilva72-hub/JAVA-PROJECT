package br.com.tracevia.webapp.controller.comms;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.model.comms.COMMS;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.wim.WIM;


@ManagedBean(name="commsLinearView")
@ViewScoped
public class COMMSBuildLinear {
	
	List<? extends Equipments> commsList;
		
	public List<? extends Equipments> getCommsList() {
		return commsList;
	}

	public void setCommsList(List<? extends Equipments> commsList) {
		this.commsList = commsList;
	}

	@PostConstruct
	public void initalize() {
		
		CreateLinearEquipment();
		
	}
	
	public void CreateLinearEquipment() {
						
		try {	
		
		try {
			
			commsList = new ArrayList<WIM>();
			
			COMMS comms = new COMMS();						
			commsList = comms.ListLinearEquipments("comms");			
				
            }catch(IndexOutOfBoundsException ex) {}
		
		}catch(Exception ex) {}		
						
	}

}
