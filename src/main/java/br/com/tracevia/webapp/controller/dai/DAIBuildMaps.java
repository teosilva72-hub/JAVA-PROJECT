package br.com.tracevia.webapp.controller.dai;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.controller.global.ListEquipments;
import br.com.tracevia.webapp.model.dai.DAI;

@ManagedBean(name="daiMapsView")
@ViewScoped
public class DAIBuildMaps implements Serializable {
	
	/**
	 * SERIAL ID
	 */
	private static final long serialVersionUID = 1L;

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
