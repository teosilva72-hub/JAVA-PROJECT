package br.com.tracevia.webapp.controller.dai;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.model.dai.DAI;
import br.com.tracevia.webapp.model.global.Equipments;

@ManagedBean(name="daiMapView")
@ViewScoped
public class DAIBuildMap {
	
	List<? extends Equipments> daiList;
	
	public List<? extends Equipments> getDaiList() {
		return daiList;
	}

	public void setDaiList(List<? extends Equipments> daiList) {
		this.daiList = daiList;
	}

	@PostConstruct
	public void initalize() {
		
		CreateMapEquipment();
		
	}
	
	public void CreateMapEquipment() {
						
		try {	
		
		try {
			
			daiList = new ArrayList<DAI>();
			
			DAI dai = new DAI();						
			daiList = dai.ListMapEquipments("dai");			
				
            }catch(IndexOutOfBoundsException ex) {}
		
		}catch(Exception ex) {}		
						
	}

}
