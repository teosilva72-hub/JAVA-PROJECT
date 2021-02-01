package br.com.tracevia.webapp.controller.videowall;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.videowall.VIDEOWALL;

@ManagedBean(name="videoWallMapView")
@ViewScoped
public class VIDEOWALLBuildMap {
	
	List<? extends Equipments> videoWallList;

	public List<? extends Equipments> getVideoWallList() {
		return videoWallList;
	}

	public void setVideoWallList(List<? extends Equipments> videoWallList) {
		this.videoWallList = videoWallList;
	}
	
	@PostConstruct
	public void initalize() {
		
		CreateMapEquipment();
		
	}
	
	public void CreateMapEquipment() {
						
		try {	
		
		try {
			
			videoWallList = new ArrayList<VIDEOWALL>();
			
			VIDEOWALL video = new VIDEOWALL();						
			videoWallList = video.ListMapEquipments("videowall");			
				
            }catch(IndexOutOfBoundsException ex) {}
		
		}catch(Exception ex) {}		
						
	}

	
}
