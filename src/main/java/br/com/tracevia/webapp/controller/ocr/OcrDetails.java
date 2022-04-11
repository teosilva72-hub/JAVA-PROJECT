package br.com.tracevia.webapp.controller.ocr;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import br.com.tracevia.webapp.dao.ocr.OCRDAO;
import br.com.tracevia.webapp.model.ocr.OCR;
import br.com.tracevia.webapp.util.ImageUtil;
import br.com.tracevia.webapp.util.LocaleUtil;

@ViewScoped
@ManagedBean(name="OcrDetails")
public class OcrDetails{
	private String[] id, dateHour, cam, plate, imageVei, imagePlate;
	private String imagePath = "";
	private OCRDAO dao = new OCRDAO();
	
	private static String CAM01 = "OCR 01";
	private static String CAM02 = "OCR 02";
	private static String CAM03 = "OCR 03";
	private static String CAM04 = "OCR 04";
	private static String CAM05 = "OCR 05";
	private static String CAM06 = "OCR 06";
	private static String CAM07 = "OCR 07";
	private static String CAM08 = "OCR 08";
	private static String CAM09 = "OCR 09";
	private static String CAM10 = "OCR 10";
	
	LocaleUtil localeOCR;
	
	String imageVeh, imagePlt, noImage, ftpFolder;		
		
	public String getImagePath() {
		try {

			Path path = Paths.get(imagePath);
			byte[] file = Files.readAllBytes(path);
			return Base64.getEncoder().encodeToString(file);
		} catch (IOException e) {
			return "";
		}
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

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
		
		localeOCR = new LocaleUtil();	
		localeOCR.getResourceBundle(LocaleUtil.LABELS_OCR);
		
		ftpFolder = "C:\\Cameras\\OCR_Types\\"; 
		
		noImage = "no-image.jpg";
		
		String unknownImage = ImageUtil.getInternalImagePath("images", "unknown", noImage);
							
		imageVeh = unknownImage;
		imagePlt = unknownImage;
		
		// System.out.println("Inicializou");
		updateView();
		RequestContext.getCurrentInstance().execute("updateDetails()");

	}
	
	public void infoGet(){
		
		//var array
		id = new String [10];
		dateHour = new String[10];
		cam = new String[10];
		plate = new String[10];
		imageVei = new String[10];
		imagePlate = new String[10];
		
		//CLASSES
		
		try {
			
			//------------------------------------------------
			//INTANCE OBJECTS
			
			OCR ocr1 = new OCR(); OCR ocr2 = new OCR(); OCR ocr3 = new OCR();
			OCR ocr4 = new OCR(); OCR ocr5 = new OCR(); OCR ocr6 = new OCR();
			OCR ocr7 = new OCR(); OCR ocr8 = new OCR(); OCR ocr9 = new OCR();		
			OCR ocr10 = new OCR();	
						
			//------------------------------------------------
			// SEARCH DATA
			
			ocr1 = dao.GetOCRdetail(CAM01);
			ocr2 = dao.GetOCRdetail(CAM02);
			ocr3 = dao.GetOCRdetail(CAM03);
			ocr4 = dao.GetOCRdetail(CAM04);
			ocr5 = dao.GetOCRdetail(CAM05);
			ocr6 = dao.GetOCRdetail(CAM06);
			ocr7 = dao.GetOCRdetail(CAM07);
			ocr8 = dao.GetOCRdetail(CAM08);
			ocr9 = dao.GetOCRdetail(CAM09);
			ocr10 = dao.GetOCRdetail(CAM10);
						
			//---------------------------------------------------
			//FILL DATA
						
			if(ocr1.getCam() != null)fillData(ocr1, 0);	else initEmpty(0, CAM01);				
			if(ocr2.getCam() != null) fillData(ocr2, 1); else initEmpty(1, CAM02);
			if(ocr3.getCam() != null) fillData(ocr3, 2); else initEmpty(2, CAM03);
			if(ocr4.getCam() != null) fillData(ocr4, 3); else initEmpty(3, CAM04);
			if(ocr5.getCam() != null) fillData(ocr5, 4); else initEmpty(4, CAM05);
			if(ocr6.getCam() != null) fillData(ocr6, 5); else initEmpty(5, CAM06);
			if(ocr7.getCam() != null) fillData(ocr7, 6); else initEmpty(6, CAM07);
			if(ocr8.getCam() != null) fillData(ocr8, 7); else initEmpty(7, CAM08);
			if(ocr9.getCam() != null) fillData(ocr9, 8); else initEmpty(8, CAM09);
			if(ocr10.getCam() != null) fillData(ocr10, 9); else initEmpty(9, CAM10);
							

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
		
		//System.out.println("atualizando");
		RequestContext.getCurrentInstance().execute("updateDetails()");
	}
	
	public String formatImageDate(String dt){
		
		dt = dt.replaceAll("\\.","");
		dt = dt.replaceAll("-", "");
		dt = dt.replaceAll(":", "");			
		dt = dt.replaceAll(" ", "");
		
		return dt;
		
		}
	
	public void initEmpty(int index, String camName) {
									
		id[index] = localeOCR.getStringKey("ocr_no_id_label");
		dateHour[index] = localeOCR.getStringKey("ocr_no_date_label");
		cam[index] = camName.replaceAll(" ", "");
		plate[index] = localeOCR.getStringKey("ocr_no_plate_label");
		imageVei[index] = ImageUtil.encodeToBase64(imageVeh);
		imagePlate[index] = ImageUtil.encodeToBase64(imagePlt);		
							
	}
		
     public void fillData(OCR data, int index) {			
				
		id[index] = data.getId().replaceAll(" ", "");;
		dateHour[index] = data.getDataHour();
		cam[index] = data.getCam().replaceAll(" ", "");
		plate[index] = data.getPlaca();
		imageVei[index] = ImageUtil.encodeToBase64(data.getVehicleImage());
		imagePlate[index] = ImageUtil.encodeToBase64(data.getPlateImage());
						
	}  
    
 }