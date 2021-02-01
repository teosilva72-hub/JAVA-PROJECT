package br.com.tracevia.webapp.controller.videowall;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.videowall.VIDEOWALL;

@ManagedBean(name="videoWallLinearView")
@ViewScoped
public class VIDEOWALLBuildLinear {
	
	List<? extends Equipments> videoWallList;

	public List<? extends Equipments> getVideoWallList() {
		return videoWallList;
	}

	public void setVideoWallList(List<? extends Equipments> videoWallList) {
		this.videoWallList = videoWallList;
	}
	
	@PostConstruct
	public void initalize() {
		
		CreateLinearEquipment();
		
	}
	
	public void CreateLinearEquipment() {
						
		try {	
		
		try {
			
			videoWallList = new ArrayList<VIDEOWALL>();
			
			VIDEOWALL video = new VIDEOWALL();						
			videoWallList = video.ListLinearEquipments("videowall");			
				
            }catch(IndexOutOfBoundsException ex) {}
		
		}catch(Exception ex) {}		
						
	}

	
}
