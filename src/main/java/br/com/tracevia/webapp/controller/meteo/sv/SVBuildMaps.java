package br.com.tracevia.webapp.controller.meteo.sv;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.ListEquipments;
import br.com.tracevia.webapp.model.meteo.sv.SV;


@ManagedBean(name="svMapsView")
@ViewScoped
public class SVBuildMaps {
	
	List<? extends Equipments> svList;
			
	public List<? extends Equipments> getSvList() {
		return svList;
	}

	public void setSvList(List<? extends Equipments> svList) {
		this.svList = svList;
	}
	

	@PostConstruct
	public void initalize() {
		
		CreateEquipment();
		
	}
	
	public void CreateEquipment() {
						
		try {	
			
			try {
				
				svList = new ArrayList<SV>();
				
				SV sv =  new SV();						
				svList = sv.listEquipments("sv");
								
	            }catch(IndexOutOfBoundsException ex) {}
			
			}catch(Exception ex) {}		
							
		
	}
	
}
