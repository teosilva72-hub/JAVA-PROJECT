package br.com.tracevia.webapp.controller.ocr;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.model.ocr.OCR;
import br.com.tracevia.webapp.model.global.Equipments;

@ManagedBean(name="ocrMapsView")
@ViewScoped
public class OCRBuildMaps {
	
	List<? extends Equipments> ocrList;

	public List<? extends Equipments> getOcrList() {
		return ocrList;
	}

	public void setOCRList(List<? extends Equipments> ocrList) {
		this.ocrList = ocrList;
	} 
	
	@PostConstruct
	public void initalize() {
		
		CreateLinearEquipment();
		
	}
	
	public void CreateLinearEquipment() {
						
		try {	
		
		try {
			
			ocrList = new ArrayList<OCR>();
			
			OCR ocr =  new OCR();						
			ocrList = ocr.listEquipments("ocr");			
				
            }catch(IndexOutOfBoundsException ex) {}
		
		}catch(Exception ex) {}		
						
	}

}
