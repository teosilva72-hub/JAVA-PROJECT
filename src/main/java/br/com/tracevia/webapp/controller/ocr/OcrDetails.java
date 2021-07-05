package br.com.tracevia.webapp.controller.ocr;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
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
	private String imagePath = "";
	private OCR data = new OCR();
	private OCRDAO dao = new OCRDAO();

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
	public List<String> getImageVei() {
		List<String> list = new ArrayList<>();
		for(String image : imageVei) {
			try {
				if(image == null) {
					image = "";
				}
				Path path = Paths.get(image);
				byte[] file = Files.readAllBytes(path);
				list.add(Base64.getEncoder().encodeToString(file));

			} catch (IOException e) {
				list.add("");
			}
		}
		return list;
	}
	public void setImageVei(String[] imageVei) {
		this.imageVei = imageVei;
	}
	public List<String> getImagePlate() {
		List<String> list = new ArrayList<>();
		for(String image : imagePlate) {
			try {
				if(image == null) {
					image = "";
				}
				Path path = Paths.get(image);
				byte[] file = Files.readAllBytes(path);
				list.add(Base64.getEncoder().encodeToString(file));

			} catch (IOException e) {
				list.add("");
			}
		}
		return list;
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
			if(ocr3.getId() != null)id[3] = ocr3.getId();
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
			//////////////////////////////////////////////////////////
			////PLATE////////////////////////////////////////////////
			String path = "C:\\teste\\";
			imageVei[0] = "";
			///////////////////////////////
			String dt1 = ocr1.getDataHour();
			dt1 = dt1.replaceAll("\\.","");
			dt1 = dt1.replaceAll("-", "");
			dt1 = dt1.replaceAll(":", "");			
			dt1 = dt1.replaceAll(" ", "");
			File Ocr1Vei = new File(path+ocr1.getCam()+"\\"+ocr1.getCam()+"_"+dt1+"_"+ocr1.getPlaca()+".jpg");
			File ocr1Plate = new File(path+ocr1.getCam()+"\\Plate"+ocr1.getCam()+"_"+dt1+"_"+ocr1.getPlaca()+".jpg");
			///////////////////////////////
			String dt2 = ocr2.getDataHour();
			dt2 = dt2.replaceAll("\\.","");
			dt2 = dt2.replaceAll("-", "");
			dt2 = dt2.replaceAll(":", "");			
			dt2 = dt2.replaceAll(" ", "");
			File Ocr2Vei = new File(path+ocr2.getCam()+"\\"+ocr2.getCam()+"_"+dt2+"_"+ocr2.getPlaca()+".jpg");
			File ocr2Plate = new File(path+ocr2.getCam()+"\\Plate"+ocr2.getCam()+"_"+dt2+"_"+ocr2.getPlaca()+".jpg");
			///////////////////////////////
			String dt3 = ocr3.getDataHour();
			dt3 = dt3.replaceAll("\\.","");
			dt3 = dt3.replaceAll("-", "");
			dt3 = dt3.replaceAll(":", "");			
			dt3 = dt3.replaceAll(" ", "");
			File Ocr3Vei = new File(path+ocr3.getCam()+"\\"+ocr3.getCam()+"_"+dt3+"_"+ocr3.getPlaca()+".jpg");
			File ocr3Plate = new File(path+ocr3.getCam()+"\\Plate"+ocr3.getCam()+"_"+dt3+"_"+ocr3.getPlaca()+".jpg");
			///////////////////////////////
			String dt4 = ocr4.getDataHour();
			dt4 = dt4.replaceAll("\\.","");
			dt4 = dt4.replaceAll("-", "");
			dt4 = dt4.replaceAll(":", "");			
			dt4 = dt4.replaceAll(" ", "");
			File Ocr4Vei = new File(path+ocr4.getCam()+"\\"+ocr4.getCam()+"_"+dt4+"_"+ocr4.getPlaca()+".jpg");
			File ocr4Plate = new File(path+ocr4.getCam()+"\\Plate"+ocr4.getCam()+"_"+dt4+"_"+ocr4.getPlaca()+".jpg");
			///////////////////////////////
			String dt5 = ocr5.getDataHour();
			dt5 = dt5.replaceAll("\\.","");
			dt5 = dt5.replaceAll("-", "");
			dt5 = dt5.replaceAll(":", "");			
			dt5 = dt5.replaceAll(" ", "");
			File Ocr5Vei = new File(path+ocr4.getCam()+"\\"+ocr5.getCam()+"_"+dt5+"_"+ocr5.getPlaca()+".jpg");
			File ocr5Plate = new File(path+ocr4.getCam()+"\\Plate"+ocr5.getCam()+"_"+dt5+"_"+ocr5.getPlaca()+".jpg");
			///////////////////////////////
			String dt6 = ocr6.getDataHour();
			dt6 = dt6.replaceAll("\\.","");
			dt6 = dt6.replaceAll("-", "");
			dt6 = dt6.replaceAll(":", "");			
			dt6 = dt6.replaceAll(" ", "");
			File Ocr6Vei = new File(path+ocr6.getCam()+"\\"+ocr6.getCam()+"_"+dt6+"_"+ocr6.getPlaca()+".jpg");
			File ocr6Plate = new File(path+ocr6.getCam()+"\\Plate"+ocr1.getCam()+"_"+dt6+"_"+ocr6.getPlaca()+".jpg");
			///////////////////////////////
			String dt7 = ocr7.getDataHour();
			dt7 = dt7.replaceAll("\\.","");
			dt7 = dt7.replaceAll("-", "");
			dt7 = dt7.replaceAll(":", "");			
			dt7 = dt7.replaceAll(" ", "");
			File Ocr7Vei = new File(path+ocr7.getCam()+"\\"+ocr7.getCam()+"_"+dt7+"_"+ocr7.getPlaca()+".jpg");
			File ocr7Plate = new File(path+ocr7.getCam()+"\\Plate"+ocr7.getCam()+"_"+dt7+"_"+ocr7.getPlaca()+".jpg");
			///////////////////////////////
			String dt8 = ocr8.getDataHour();
			dt8 = dt8.replaceAll("\\.","");
			dt8 = dt8.replaceAll("-", "");
			dt6 = dt6.replaceAll(":", "");			
			dt6 = dt6.replaceAll(" ", "");
			File Ocr8Vei = new File(path+ocr6.getCam()+"\\"+ocr8.getCam()+"_"+dt8+"_"+ocr8.getPlaca()+".jpg");
			File ocr8Plate = new File(path+ocr6.getCam()+"\\Plate"+ocr8.getCam()+"_"+dt8+"_"+ocr8.getPlaca()+".jpg");
			///////////////////////////////
			String dt9 = ocr9.getDataHour();
			dt8 = dt8.replaceAll("\\.","");
			dt8 = dt8.replaceAll("-", "");
			dt6 = dt6.replaceAll(":", "");			
			dt6 = dt6.replaceAll(" ", "");
			File Ocr9Vei = new File(path+ocr9.getCam()+"\\"+ocr9.getCam()+"_"+dt9+"_"+ocr9.getPlaca()+".jpg");
			File ocr9Plate = new File(path+ocr9.getCam()+"\\Plate"+ocr9.getCam()+"_"+dt9+"_"+ocr9.getPlaca()+".jpg");
			///////////////////////////////
			///////////////////////////////
			String dt10 = ocr10.getDataHour();
			dt10 = dt10.replaceAll("\\.","");
			dt10 = dt10.replaceAll("-", "");
			dt10 = dt10.replaceAll(":", "");			
			dt10 = dt10.replaceAll(" ", "");
			File Ocr10Vei = new File(path+ocr10.getCam()+"\\"+ocr10.getCam()+"_"+dt10+"_"+ocr10.getPlaca()+".jpg");
			File ocr10Plate = new File(path+ocr10.getCam()+"\\Plate"+ocr10.getCam()+"_"+dt10+"_"+ocr10.getPlaca()+".jpg");
			///////////////////////////////
			///////////////////////////////
			if(Ocr1Vei.exists() && ocr1Plate.exists()) {
				System.out.println("Entramos");
				imageVei[1] = Ocr1Vei.getPath();
				imagePlate[1] = Ocr1Vei.getPath();;
			}else {
				System.out.println("não existe");
				imageVei[1] = path+"no-image.png";
				imagePlate[1] = path+"no-image.png";
			}
			if(Ocr2Vei.exists() && ocr2Plate.exists()) {
				System.out.println("Entramos");
				imageVei[2] = Ocr2Vei.getPath();
				imagePlate[2] = Ocr2Vei.getPath();;
			}else {
				System.out.println("não existe");
				imageVei[2] = path+"no-image.png";
				imagePlate[2] = path+"no-image.png";
			}
			if(Ocr3Vei.exists() && ocr3Plate.exists()) {
				System.out.println("Entramos");
				imageVei[3] = Ocr3Vei.getPath();
				imagePlate[3] = Ocr3Vei.getPath();;
			}else {
				System.out.println("não existe");
				imageVei[3] = path+"no-image.png";
				imagePlate[3] = path+"no-image.png";
			}
			if(Ocr4Vei.exists() && ocr4Plate.exists()) {
				System.out.println("Entramos");
				imageVei[4] = Ocr4Vei.getPath();
				imagePlate[4] = Ocr4Vei.getPath();;
			}else {
				System.out.println("não existe");
				imageVei[4] = path+"no-image.png";
				imagePlate[4] = path+"no-image.png";
			}
			if(Ocr5Vei.exists() && ocr5Plate.exists()) {
				System.out.println("Entramos");
				imageVei[5] = Ocr5Vei.getPath();
				imagePlate[5] = Ocr5Vei.getPath();;
			}else {
				System.out.println("não existe");
				imageVei[5] = path+"no-image.png";
				imagePlate[5] = path+"no-image.png";
			}
			if(Ocr6Vei.exists() && ocr6Plate.exists()) {
				System.out.println("Entramos");
				imageVei[6] = Ocr6Vei.getPath();
				imagePlate[6] = Ocr6Vei.getPath();;
			}else {
				System.out.println("não existe");
				imageVei[6] = path+"no-image.png";
				imagePlate[6] = path+"no-image.png";
			}
			if(Ocr7Vei.exists() && ocr7Plate.exists()) {
				System.out.println("Entramos");
				imageVei[7] = Ocr7Vei.getPath();
				imagePlate[7] = Ocr7Vei.getPath();;
			}else {
				System.out.println("não existe");
				imageVei[7] = path+"no-image.png";
				imagePlate[7] = path+"no-image.png";
			}
			if(Ocr8Vei.exists() && ocr8Plate.exists()) {
				System.out.println("Entramos");
				imageVei[8] = Ocr8Vei.getPath();
				imagePlate[8] = Ocr8Vei.getPath();;
			}else {
				System.out.println("não existe");
				imageVei[8] = path+"no-image.png";
				imagePlate[8] = path+"no-image.png";
			}
			if(Ocr9Vei.exists() && ocr9Plate.exists()) {
				System.out.println("Entramos");
				imageVei[9] = Ocr8Vei.getPath();
				imagePlate[9] = Ocr8Vei.getPath();;
			}else {
				System.out.println("não existe");
				imageVei[9] = path+"no-image.png";
				imagePlate[9] = path+"no-image.png";
			}
			if(Ocr10Vei.exists() && ocr10Plate.exists()) {
				System.out.println("Entramos");
				imageVei[10] = Ocr8Vei.getPath();
				imagePlate[10] = Ocr8Vei.getPath();;
			}else {
				System.out.println("não existe");
				imageVei[10] = path+"no-image.png";
				imagePlate[10] = path+"no-image.png";
			}


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