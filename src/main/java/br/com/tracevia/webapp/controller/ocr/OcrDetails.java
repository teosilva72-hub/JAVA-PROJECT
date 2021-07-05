package br.com.tracevia.webapp.controller.ocr;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import br.com.tracevia.webapp.dao.ocr.OCRDAO;
import br.com.tracevia.webapp.model.ocr.OCR;

@ViewScoped
@ManagedBean(name="OcrDetails")
public class OcrDetails{
	private String[] id, dateHour, cam, plate, imageVei, imagePlate;
	private OCR data = new OCR();
	private OCRDAO dao = new OCRDAO();

	public String[] getId() {
		return id;
	}
	public void setId(String[] id) {
		this.id = id;
	}
	public String[] getDateHour() {
		return dateHour;
	}
	public void setDateHour(String[] dateHour) {
		this.dateHour = dateHour;
	}
	public String[] getCam() {
		return cam;
	}
	public void setCam(String[] cam) {
		this.cam = cam;
	}
	public String[] getPlate() {
		return plate;
	}
	public void setPlate(String[] plate) {
		this.plate = plate;
	}
	public String[] getImageVei() {
		return imageVei;
	}
	public void setImageVei(String[] imageVei) {
		this.imageVei = imageVei;
	}
	public String[] getImagePlate() {
		return imagePlate;
	}
	public void setImagePlate(String[] imagePlate) {
		this.imagePlate = imagePlate;
	}
	@PostConstruct
	public void initialize() {
		System.out.println("Inicializou");
		updateView();
		RequestContext.getCurrentInstance().execute("updateDetails()");

	}
	public void infoGet(){
		System.out.println("aquie");
		//classes

		//method dao
		try {
			OCR ocr1 = new OCR(); OCR ocr5 = new OCR(); OCR ocr9 = new OCR();
			OCR ocr2 = new OCR(); OCR ocr6 = new OCR(); OCR ocr10 = new OCR();
			OCR ocr3 = new OCR(); OCR ocr7 = new OCR(); 
			OCR ocr4 = new OCR(); OCR ocr8 = new OCR();
			//var array
			id = new String [20];
			dateHour = new String[20];
			cam = new String[20];
			plate = new String[20];
			imageVei = new String[20];
			imagePlate = new String[20];
			/////////////////////////////////////////////
			ocr1 = dao.OCR1(); ocr2 = dao.OCR2();
			ocr3 = dao.OCR3(); ocr4 = dao.OCR4();
			ocr5 = dao.OCR5(); ocr6 = dao.OCR6();
			ocr7 = dao.OCR7(); ocr8 = dao.OCR8();
			ocr9 = dao.OCR9(); ocr10 = dao.OCR10();
			/////////////////////////////////////////////
			/////ID/////////////////////////////////////
			if(ocr1.getId() != null)id[1] = ocr1.getId();
			else id[1] = "Vazio";	
			if(ocr2.getId() != null)id[2] = ocr2.getId();
			else id[2] = "Vazio";
			if(ocr3.getId() != null)id[3] = ocr4.getId();
			else id[3] = "Vazio";
			if(ocr4.getId() != null)id[4] = ocr4.getId();
			else id[4] = "Vazio";
			if(ocr5.getId() != null)id[5] = ocr5.getId();
			else id[5] = "Vazio";
			if(ocr6.getId() != null)id[6] = ocr6.getId();
			else id[6] = "Vazio";
			if(ocr7.getId() != null)id[7] = ocr7.getId();
			else id[7] = "Vazio";
			if(ocr8.getId() != null)id[8] = ocr8.getId();
			else id[8] = "Vazio";
			if(ocr9.getId() != null)id[9] = ocr9.getId();
			else id[9] = "Vazio";
			if(ocr10.getId() != null)id[10] = ocr10.getId();
			else id[10] = "Vazio";
			////////////////////////////////////////////////
			////DATA|HOUR//////////////////////////////////
			if(ocr1.getDataHour() != null)dateHour[1] = ocr1.getDataHour();
			else dateHour[1] = "Vazio";
			if(ocr2.getDataHour() != null)dateHour[2] = ocr2.getDataHour();
			else dateHour[2] = "Vazio";
			if(ocr3.getDataHour() != null)dateHour[3] = ocr3.getDataHour();
			else dateHour[3] = "Vazio";
			if(ocr4.getDataHour() != null)dateHour[4] = ocr4.getDataHour();
			else dateHour[4] = "Vazio";
			if(ocr5.getDataHour() != null)dateHour[5] = ocr5.getDataHour();
			else dateHour[5] = "Vazio";
			if(ocr6.getDataHour() != null)dateHour[6] = ocr6.getDataHour();
			else dateHour[6] = "Vazio";
			if(ocr7.getDataHour() != null)dateHour[7] = ocr7.getDataHour();
			else dateHour[7] = "Vazio";
			if(ocr8.getDataHour() != null)dateHour[8] = ocr8.getDataHour();
			else dateHour[8] = "Vazio";
			if(ocr9.getDataHour() != null)dateHour[9] = ocr9.getDataHour();
			else dateHour[9] = "Vazio";
			if(ocr10.getDataHour() != null)dateHour[10] = ocr10.getDataHour();
			else dateHour[10] = "Vazio";
			//////////////////////////////////////////////////////////////////
			////CAM//////////////////////////////////////////////////////////
			if(ocr1.getCam() != null) {
				String dt = ocr1.getCam();
				dt = dt.replaceAll("_", "");
				cam[1] = dt;
			}
			else cam[1] = "OCR1";
			if(ocr2.getCam() != null){
				String dt = ocr2.getCam();
				dt = dt.replaceAll("_", "");
				cam[2] = dt;
			}else cam[2] = "OCR2";
			if(ocr3.getCam() != null){
				String dt = ocr3.getCam();
				dt = dt.replaceAll("_", "");
				cam[3] = dt;
			}else cam[3] = "OCR3";
			if(ocr4.getCam() != null){
				String dt = ocr4.getCam();
				dt = dt.replaceAll("_", "");
				cam[4] = dt;
			}else cam[4] = "OCR4";
			if(ocr5.getCam() != null){
				String dt = ocr5.getCam();
				dt = dt.replaceAll("_", "");
				cam[5] = dt;
			}else cam[5] = "OCR5";
			if(ocr6.getCam() != null){
				String dt = ocr6.getCam();
				dt = dt.replaceAll("_", "");
				cam[6] = dt;
			}else cam[6] = "OCR6";
			if(ocr7.getCam() != null){
				String dt = ocr7.getCam();
				dt = dt.replaceAll("_", "");
				cam[7] = dt;
			}else cam[7] = "OCR7";
			if(ocr8.getCam() != null){
				String dt = ocr8.getCam();
				dt = dt.replaceAll("_", "");
				cam[8] = dt;
			}else cam[8] = "OCR8";
			if(ocr9.getCam() != null){
				String dt = ocr9.getCam();
				dt = dt.replaceAll("_", "");
				cam[9] = dt;
			}else cam[9] = "OCR9";
			if(ocr10.getCam() != null){
				String dt = ocr10.getCam();
				dt = dt.replaceAll("_", "");
				cam[10] = dt;
			}else cam[10] = "OCR10";
			////////////////////////////////////////////////////
			////PLATE//////////////////////////////////////////
			if(ocr1.getPlaca() != null)plate[1] = ocr1.getPlaca();
			else plate[1] = "Vazio";
			if(ocr2.getPlaca() != null)plate[2] = ocr2.getPlaca();
			else plate[2] = "Vazio";
			if(ocr3.getPlaca() != null)plate[3] = ocr3.getPlaca();
			else plate[3] = "Vazio";
			if(ocr4.getPlaca() != null)plate[4] = ocr4.getPlaca();
			else plate[4] = "Vazio";
			if(ocr5.getPlaca() != null)plate[5] = ocr5.getPlaca();
			else plate[5] = "Vazio";
			if(ocr6.getPlaca() != null)plate[6] = ocr6.getPlaca();
			else plate[6] = "Vazio";
			if(ocr7.getPlaca() != null)plate[7] = ocr7.getPlaca();
			else plate[7] = "Vazio";
			if(ocr8.getPlaca() != null)plate[8] = ocr8.getPlaca();
			else plate[8] = "Vazio";
			if(ocr9.getPlaca() != null)plate[9] = ocr9.getPlaca();
			else plate[9] = "Vazio";
			if(ocr10.getPlaca() != null)plate[10] = ocr10.getPlaca();
			else plate[10] = "Vazio";
			imageVei[1] = "";
			imageVei[2] = "";
			imageVei[3] = "";
			imageVei[4] = "";
			imageVei[5] = "";
			imageVei[6] = "";
			imageVei[7] = "";
			imageVei[8] = "";
			imageVei[9] = "";
			imageVei[10] = "";
			imagePlate[1] = "";
			imagePlate[2] = "";
			imagePlate[3] = "";
			imagePlate[4] = "";
			imagePlate[5] = "";
			imagePlate[6] = "";
			imagePlate[7] = "";
			imagePlate[8] = "";
			imagePlate[9] = "";
			imagePlate[10] = "";
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
		infoGet();
		System.out.println("atualizando");
		RequestContext.getCurrentInstance().execute("updateDetails()");
	}
}