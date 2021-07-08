package br.com.tracevia.webapp.controller.ocr;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.model.ocr.OCR;
import br.com.tracevia.webapp.controller.global.NotificationsBean;
import br.com.tracevia.webapp.dao.ocr.OCRDAO;
import br.com.tracevia.webapp.model.global.Equipments;

@ManagedBean(name="ocrMapsView")
@ViewScoped
public class OCRBuildMaps {
	
	List<? extends Equipments> ocrList;
	
	List<OCR> ocrStatus;

	public List<? extends Equipments> getOcrList() {
		return ocrList;
	}
	
	public List<OCR> getOcrStatus() {
		return ocrStatus;
	}

	@PostConstruct
	public void initalize() {
		
		CreateLinearEquipment();
		
	}
	
	public void CreateLinearEquipment() {
						
		try {	
		
			try {
			
			OCRDAO ocrDAO = new OCRDAO();  
			NotificationsBean notif = new NotificationsBean();
				
			ocrList = new ArrayList<OCR>();
			
			OCR ocr =  new OCR();						
			ocrList = ocr.listEquipments("ocr");			
							
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
