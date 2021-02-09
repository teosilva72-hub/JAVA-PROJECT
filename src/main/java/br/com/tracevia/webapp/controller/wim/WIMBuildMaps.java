package br.com.tracevia.webapp.controller.wim;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.wim.WIM;

@ManagedBean(name="wimLinearView")
@ViewScoped
public class WIMBuildMaps {
	
	List<? extends Equipments> wimList;
	
	public List<? extends Equipments> getWimList() {
		return wimList;
	}

	public void setWimList(List<? extends Equipments> wimList) {
		this.wimList = wimList;
	}

	@PostConstruct
	public void initalize() {
		
		CreateLinearEquipment();
		
	}
	
	public void CreateLinearEquipment() {
						
		try {	
		
		try {
			
			wimList = new ArrayList<WIM>();
			
			WIM wim =  new WIM();						
			wimList = wim.listEquipments("wim");			
				
            }catch(IndexOutOfBoundsException ex) {}
		
		}catch(Exception ex) {}		
						
	}

}
