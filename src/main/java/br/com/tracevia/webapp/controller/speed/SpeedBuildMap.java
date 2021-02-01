package br.com.tracevia.webapp.controller.speed;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.speed.Speed;

@ManagedBean(name="speedMapView")
@ViewScoped
public class SpeedBuildMap {
	
	List<? extends Equipments> speedList;

	public List<? extends Equipments> getSpeedList() {
		return speedList;
	}

	public void setSpeedList(List<? extends Equipments> speedList) {
		this.speedList = speedList;
	}
	
	@PostConstruct
	public void initalize() {
		
		CreateMapEquipment();
		
	}
	
	public void CreateMapEquipment() {
						
		try {	
		
		try {
			
			speedList = new ArrayList<Speed>();
			
			Speed speed =  new Speed();						
			speedList = speed.ListMapEquipments("speed");			
				
            }catch(IndexOutOfBoundsException ex) {}
		
		}catch(Exception ex) {}		
						
	}

}
