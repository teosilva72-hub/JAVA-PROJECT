package br.com.tracevia.webapp.controller.speed;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.dao.speed.SpeedDAO;
import br.com.tracevia.webapp.model.global.ListEquipments;
import br.com.tracevia.webapp.model.speed.Speed;

@ManagedBean(name="speedMapsView")
@ViewScoped
public class SpeedBuildSpeed {
	
	@ManagedProperty("#{listEquips}")
	private ListEquipments equips;
	
	List<Speed> speedStatus;
	List<Speed> listSpeed;
		
	public ListEquipments getEquips() {
		return equips;
	}

	public void setEquips(ListEquipments equips) {
		this.equips = equips;
	}
	
	public List<Speed> getSpeedStatus() {
		return speedStatus;
	}
	
	public List<Speed> getListSpeed() {
		return listSpeed;
	}

	public void setSpeedStatus(List<Speed> speedStatus) {
		this.speedStatus = speedStatus;
	}
	
	@PostConstruct
	public void initalize() {
		
		BuildSpeed();
		
	}
	
	public void BuildSpeed() {
						
		try {	
		
		try {
			
			SpeedDAO dao = new SpeedDAO();
			
			speedStatus = new ArrayList<Speed>();			
			speedStatus = dao.Status();			
			listSpeed = dao.getSpeeds();			
				
            }catch(IndexOutOfBoundsException ex) {}
		
		}catch(Exception ex) {}		
						
	}

}
