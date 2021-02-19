package br.com.tracevia.webapp.controller.colas;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.model.colas.Colas;
import br.com.tracevia.webapp.model.global.Equipments;

@ManagedBean(name="colasMapsView")
@ViewScoped
public class ColasBuildMaps {
	
	List<? extends Equipments> colasList;

	public List<? extends Equipments> getColasList() {
		return colasList;
	}

	public void setColasList(List<? extends Equipments> colasList) {
		this.colasList = colasList;
	} 
	
	@PostConstruct
	public void initalize() {
		
		CreateMapEquipment();
		
	}
	
	public void CreateMapEquipment() {
						
		try {	
		
		try {
			
			colasList = new ArrayList<Colas>();
			
			Colas colas = new Colas();						
			colasList = colas.listEquipments("colas");			
				
            }catch(IndexOutOfBoundsException ex) {}
		
		}catch(Exception ex) {}		
						
	}

}
