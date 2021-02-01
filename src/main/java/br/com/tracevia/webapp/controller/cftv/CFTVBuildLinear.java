package br.com.tracevia.webapp.controller.cftv;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.model.cftv.CFTV;
import br.com.tracevia.webapp.model.global.Equipments;

@ManagedBean(name="cftvLinearView")
@ViewScoped
public class CFTVBuildLinear {
	
	List<? extends Equipments> cftvList; 
	
	public List<? extends Equipments> getCftvList() {
		return cftvList;
	}

	public void setCftvList(List<? extends Equipments> cftvList) {
		this.cftvList = cftvList;
	}

	@PostConstruct
	public void initalize() {
		
		CreateLinearEquipment();
		
	}
	
	public void CreateLinearEquipment() {
			
		try {	
		
		try {
			
			cftvList = new ArrayList<CFTV>();
			
			CFTV cftv =  new CFTV();						
			cftvList = cftv.ListLinearEquipments("cftv");			
				
            }catch(IndexOutOfBoundsException ex) {}
		
		}catch(Exception ex) {}		
						
	}

}
