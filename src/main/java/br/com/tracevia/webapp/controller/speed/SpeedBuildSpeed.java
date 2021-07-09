package br.com.tracevia.webapp.controller.speed;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.model.global.ListEquipments;

@ManagedBean(name="speedMapsView")
@ViewScoped
public class SpeedBuildSpeed {
	
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
		
		//BuildSpeed();
		
	}
	
	public void BuildSpeed() {
						
		try {	
		
		try {
			
						
				
            }catch(IndexOutOfBoundsException ex) {}
		
		}catch(Exception ex) {}		
						
	}

}
