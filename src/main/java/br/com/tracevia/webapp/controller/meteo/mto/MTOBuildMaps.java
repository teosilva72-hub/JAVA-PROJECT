package br.com.tracevia.webapp.controller.meteo.mto;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.meteo.mto.MTO;

@ManagedBean(name="mtoMapsView")
@ViewScoped
public class MTOBuildMaps {

List<? extends Equipments> mtoList; 
	
	public List<? extends Equipments> getMtoList() {
		return mtoList;
	}

	public void setMtoList(List<? extends Equipments> mtoList) {
		this.mtoList = mtoList;
	}

	@PostConstruct
	public void initalize() {
		
		CreateLinearEquipment();
		
	}
	
	public void CreateLinearEquipment() {
						
		try {	
		
		try {
			
			mtoList = new ArrayList<MTO>();
			
			MTO mto =  new MTO();						
			mtoList = mto.listEquipments("mto");			
				
            }catch(IndexOutOfBoundsException ex) {}
		
		}catch(Exception ex) {}		
						
	}
}
