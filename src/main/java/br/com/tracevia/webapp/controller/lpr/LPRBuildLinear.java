package br.com.tracevia.webapp.controller.lpr;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.model.lpr.LPR;
import br.com.tracevia.webapp.model.global.Equipments;

@ManagedBean(name="lprLinearView")
@ViewScoped
public class LPRBuildLinear {
	
	List<? extends Equipments> lprList;

	public List<? extends Equipments> getLprList() {
		return lprList;
	}

	public void setLprList(List<? extends Equipments> lprList) {
		this.lprList = lprList;
	} 
	
	@PostConstruct
	public void initalize() {
		
		CreateLinearEquipment();
		
	}
	
	public void CreateLinearEquipment() {
						
		try {	
		
		try {
			
			lprList = new ArrayList<LPR>();
			
			LPR lpr =  new LPR();						
			lprList = lpr.ListLinearEquipments("lpr");			
				
            }catch(IndexOutOfBoundsException ex) {}
		
		}catch(Exception ex) {}		
						
	}

}
