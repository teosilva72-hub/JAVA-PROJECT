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
			String dt1 = formatImageDate(ocr1.getDataHour());						
			String subFolder1 = dt1.substring(0, 8);
			String nameVeh1 = data.getCam().replaceAll(" ", "_");	
			
			File Ocr1Vei = new File(path+nameVeh1+"\\"+subFolder1+"\\"+nameVeh1+"_"+dt1+"_"+ocr1.getPlaca()+".jpg");
			File ocr1Plate = new File(path+nameVeh1+"\\"+subFolder1+"\\Plate"+nameVeh1+"_"+dt1+"_"+ocr1.getPlaca()+".jpg");
			///////////////////////////////
			String dt2 = formatImageDate(ocr2.getDataHour());						
			String subFolder2 = dt2.substring(0, 8);
			String nameVeh2 = data.getCam().replaceAll(" ", "_");	
			
			File Ocr2Vei = new File(path+nameVeh2+"\\"+subFolder2+"\\"+nameVeh2+"_"+dt2+"_"+ocr2.getPlaca()+".jpg");
			File ocr2Plate = new File(path+nameVeh2+"\\"+subFolder2+"\\Plate"+nameVeh2+"_"+dt2+"_"+ocr2.getPlaca()+".jpg");
			///////////////////////////////
			String dt3 = formatImageDate(ocr3.getDataHour());						
			String subFolder3 = dt3.substring(0, 8);
			String nameVeh3 = data.getCam().replaceAll(" ", "_");	
			
			File Ocr3Vei = new File(path+nameVeh3+"\\"+subFolder3+"\\"+nameVeh3+"_"+dt3+"_"+ocr3.getPlaca()+".jpg");
			File ocr3Plate = new File(path+nameVeh3+"\\"+subFolder3+"\\Plate"+nameVeh3+"_"+dt3+"_"+ocr3.getPlaca()+".jpg");
			///////////////////////////////
			String dt4 = formatImageDate(ocr4.getDataHour());						
			String subFolder4 = dt4.substring(0, 8);
			String nameVeh4 = data.getCam().replaceAll(" ", "_");	
			
			File Ocr4Vei = new File(path+nameVeh4+"\\"+subFolder4+"\\"+nameVeh4+"_"+dt4+"_"+ocr4.getPlaca()+".jpg");
			File ocr4Plate = new File(path+nameVeh4+"\\"+subFolder4+"\\Plate"+nameVeh4+"_"+dt4+"_"+ocr4.getPlaca()+".jpg");
			///////////////////////////////
			String dt5 = formatImageDate(ocr5.getDataHour());						
			String subFolder5 = dt5.substring(0, 8);
			String nameVeh5 = data.getCam().replaceAll(" ", "_");	
			
			File Ocr5Vei = new File(path+nameVeh5+"\\"+subFolder5+"\\"+nameVeh5+"_"+dt5+"_"+ocr5.getPlaca()+".jpg");
			File ocr5Plate = new File(path+nameVeh5+"\\"+subFolder5+"\\Plate"+nameVeh5+"_"+dt5+"_"+ocr5.getPlaca()+".jpg");
			///////////////////////////////
			String dt6 = formatImageDate(ocr6.getDataHour());						
			String subFolder6 = dt6.substring(0, 8);
			String nameVeh6 = data.getCam().replaceAll(" ", "_");	
			
			File Ocr6Vei = new File(path+nameVeh6+"\\"+subFolder6+"\\"+nameVeh6+"_"+dt6+"_"+ocr6.getPlaca()+".jpg");
			File ocr6Plate = new File(path+nameVeh6+"\\"+subFolder6+"\\Plate"+nameVeh6+"_"+dt6+"_"+ocr6.getPlaca()+".jpg");
			///////////////////////////////
			String dt7 = formatImageDate(ocr7.getDataHour());						
			String subFolder7 = dt7.substring(0, 8);
			String nameVeh7 = data.getCam().replaceAll(" ", "_");	
			
			File Ocr7Vei = new File(path+nameVeh7+"\\"+subFolder7+"\\"+nameVeh7+"_"+dt7+"_"+ocr7.getPlaca()+".jpg");
			File ocr7Plate = new File(path+nameVeh7+"\\"+subFolder7+"\\Plate"+nameVeh7+"_"+dt7+"_"+ocr7.getPlaca()+".jpg");
			///////////////////////////////
			String dt8 = formatImageDate(ocr8.getDataHour());						
			String subFolder8 = dt4.substring(0, 8);
			String nameVeh8 = data.getCam().replaceAll(" ", "_");	
			
			File Ocr8Vei = new File(path+nameVeh8+"\\"+subFolder8+"\\"+nameVeh8+"_"+dt8+"_"+ocr8.getPlaca()+".jpg");
			File ocr8Plate = new File(path+nameVeh8+"\\"+subFolder8+"\\Plate"+nameVeh8+"_"+dt8+"_"+ocr8.getPlaca()+".jpg");
			///////////////////////////////
			String dt9 = formatImageDate(ocr9.getDataHour());						
			String subFolder9 = dt9.substring(0, 8);
			String nameVeh9 = data.getCam().replaceAll(" ", "_");	
			
			File Ocr9Vei = new File(path+nameVeh9+"\\"+subFolder9+"\\"+nameVeh9+"_"+dt9+"_"+ocr9.getPlaca()+".jpg");
			File ocr9Plate = new File(path+nameVeh9+"\\"+subFolder9+"\\Plate"+nameVeh9+"_"+dt9+"_"+ocr9.getPlaca()+".jpg");
			///////////////////////////////
			///////////////////////////////
			String dt10 = formatImageDate(ocr10.getDataHour());						
			String subFolder10 = dt10.substring(0, 8);
			String nameVeh10 = data.getCam().replaceAll(" ", "_");	
			
			File Ocr10Vei = new File(path+nameVeh10+"\\"+subFolder10+"\\"+nameVeh10+"_"+dt10+"_"+ocr10.getPlaca()+".jpg");
			File ocr10Plate = new File(path+nameVeh10+"\\"+subFolder10+"\\Plate"+nameVeh10+"_"+dt10+"_"+ocr10.getPlaca()+".jpg");
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
	
	public String formatImageDate(String dt){
		
		dt = dt.replaceAll("\\.","");
		dt = dt.replaceAll("-", "");
		dt = dt.replaceAll(":", "");			
		dt = dt.replaceAll(" ", "");
		
		return dt;
		
		}
}