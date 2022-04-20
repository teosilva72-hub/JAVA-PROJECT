package br.com.tracevia.webapp.controller.colas;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.controller.global.ListEquipments;
import br.com.tracevia.webapp.model.colas.Colas;

@ManagedBean(name="colasMapsView")
@ViewScoped
public class ColasBuildMaps {
	
	@ManagedProperty("#{listEquips}")
	private ListEquipments equips;
	
	List<Colas> colasStatus;

	
	public ListEquipments getEquips() {
		return equips;
	}

	public void setEquips(ListEquipments equips) {
		this.equips = equips;
	}
	
	public List<Colas> getColasStatus() {
		return colasStatus;
	}
	
	@PostConstruct
	public void initalize() {
		
		BuildCOLAS();
		
	}
	
	public void BuildCOLAS() {
						
		try {	
		
		try {
			
			//ColasDAO colasDAO = new ColasDAO();
						
							
            }catch(IndexOutOfBoundsException ex) {}
		
		}catch(Exception ex) {}		
						
	}

}
