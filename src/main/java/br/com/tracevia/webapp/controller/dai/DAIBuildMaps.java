package br.com.tracevia.webapp.controller.dai;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.dao.dai.DAIDAO;
import br.com.tracevia.webapp.model.dai.DAI;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.ListEquipments;

@ManagedBean(name="daiMapsView")
@ViewScoped
public class DAIBuildMaps {
	
	@ManagedProperty("#{listEquips}")
	private ListEquipments equips;
			
	List<DAI> daiStatus;
	
	public ListEquipments getEquips() {
		return equips;
	}

	public void setEquips(ListEquipments equips) {
		this.equips = equips;
	}
	
	public List<DAI> getDaiStatus() {
		return daiStatus;
	}

	@PostConstruct
	public void initalize() {
		
		BuildDAI();
		
	}
	
	public void BuildDAI() {
						
		try {	
		
		try {
			
						
	    			
            }catch(IndexOutOfBoundsException ex) {}
		
		}catch(Exception ex) {}		
						
	}

}
