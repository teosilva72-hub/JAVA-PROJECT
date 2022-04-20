package br.com.tracevia.webapp.controller.meteo;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.controller.global.ListEquipments;

@ManagedBean(name="mtoMapsView")
@ViewScoped
public class MeteoBuildMaps {
	
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
		
		//CreateLinearEquipment();
		
	}
	
	public void CreateLinearEquipment() {
						
		try {	
		
		try {
			
	
								
            }catch(IndexOutOfBoundsException ex) {}
		
		}catch(Exception ex) {}		
						
	}
}
