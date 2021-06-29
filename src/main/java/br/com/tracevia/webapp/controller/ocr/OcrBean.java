package br.com.tracevia.webapp.controller.ocr;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import br.com.tracevia.webapp.dao.ocr.OCRDAO;
import br.com.tracevia.webapp.model.ocr.OCR;

@ManagedBean(name="OcrRealtime")
@ViewScoped
public class OcrBean{
	
	private List<SelectItem> cam;
	private String filterCam;
	private OCR data;
	private OCRDAO dao;	
	
	public List<SelectItem> getCam() {
		return cam;
	}
	public void setCam(List<SelectItem> cam) {
		this.cam = cam;
	}
	public String getFilterCam() {
		return filterCam;
	}
	public void setFilterCam(String filterCam) {
		this.filterCam = filterCam;
	}
	
	public List<OCR> list;

	public List<OCR> getList() {
		return list;
	}
	public void setList(List<OCR> list) {
		this.list = list;
	}
		
	public OCR getData() {
		return data;
	}
		
	public void setData(OCR data) {
		this.data = data;
	}
	
	@PostConstruct
	public void initialize() {
		
		RequestContext.getCurrentInstance().execute("filtro()");

		System.out.println("Inicialização");
		updateView();
			
		dao = new OCRDAO();

		//filtro câmera
		cam = new ArrayList<SelectItem>();
		cam.add(new SelectItem("Todos"));
		cam.add(new SelectItem("ocr_1"));
		cam.add(new SelectItem("ocr_2"));
		cam.add(new SelectItem("ocr_3"));
		cam.add(new SelectItem("ocr_4"));
		cam.add(new SelectItem("ocr_5"));
		cam.add(new SelectItem("ocr_6"));
		
		//final filtro câmera
		try {
			data = dao.lastRegister();			
			data.getId();
			data.getDataHour();
			data.getCam();
			data.getPlaca();
			data.getPlateImage();
			data.getVehicleImage();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void updateView() {

		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestContext.getCurrentInstance().execute("updateView()");
		System.out.println("atualizando");
		System.out.println(getFilterCam());
		
		try {

			String cam = getFilterCam();
			
			if(cam == null || cam.equals("Todos")) {
				
				data = dao.lastRegister();
							
				data.getId();
				data.getDataHour();
				data.getCam();
				data.getPlaca();
				data.getPlateImage();
				data.getVehicleImage();
				
				System.out.println(data.getPlateImage());
				
				
			}else {
				
				data = dao.searchCam(cam);	
				
				data.getId();
				data.getDataHour();
				data.getCam();
				data.getPlaca();
				data.getPlateImage();
				data.getVehicleImage();
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void setFilter() {
		
		String x = getFilterCam();
		System.out.println("aquie");
		
		try {
			
			dao.searchCam(x);		
			data.getId();
			data.getDataHour();
			data.getCam();
			data.getPlaca();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}