package br.com.tracevia.webapp.controller.ocr;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.model.ocr.OCR;
import br.com.tracevia.webapp.cfg.NotificationsAlarmsEnum;
import br.com.tracevia.webapp.cfg.NotificationsTypeEnum;
import br.com.tracevia.webapp.controller.global.NotificationsBean;
import br.com.tracevia.webapp.dao.ocr.OCRDAO;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.ListEquipments;

@ManagedBean(name="ocrMapsView")
@ViewScoped
public class OCRBuildMaps {
	
	@ManagedProperty("#{listEquips}")
	private ListEquipments equips;
	
	List<OCR> ocrStatus;	
	
	public List<OCR> getOcrStatus() {
		return ocrStatus;
	}	
	
	public ListEquipments getEquips() {
		return equips;
	}

	public void setEquips(ListEquipments equips) {
		this.equips = equips;
	}

	@PostConstruct
	public void initalize() {
		
		BuildOCR();
		
	}
	
	public void BuildOCR() {
						
		try {	
		
			try {
			
			OCRDAO ocrDAO = new OCRDAO();  							
		    
			ocrStatus = new ArrayList<OCR>();			
			ocrStatus = ocrDAO.Status();	
				
			}catch (IndexOutOfBoundsException ex) {			
				ex.printStackTrace();
			}
		
		}catch(Exception ex) {	
		   ex.printStackTrace();
		}
	}

}
