package br.com.tracevia.webapp.controller.comms;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.model.comms.COMMS;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.ListEquipments;
import br.com.tracevia.webapp.model.wim.WIM;

@ManagedBean(name="commsMapsView")
@ViewScoped
public class COMMSBuildMaps {
	
	 @ManagedProperty("#{listEquips}")
	private ListEquipments equips;
			
	public ListEquipments getEquips() {
		return equips;
	}

	public void setEquips(ListEquipments equips) {
		this.equips = equips;
	}
		
	@PostConstruct
	public void initalize() {
		
		//BuildComms();
		
	}
	
	public void BuildComms() {
						
		try {	
		
		try {
			
	
				
            }catch(IndexOutOfBoundsException ex) {}
		
		}catch(Exception ex) {}		
						
	}

}
