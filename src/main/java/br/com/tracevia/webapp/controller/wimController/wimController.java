package br.com.tracevia.webapp.controller.wimController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import br.com.tracevia.webapp.dao.wim.WIMDAO;
import br.com.tracevia.webapp.model.wim.WimData;

@ManagedBean(name="wimController")
@ViewScoped
public class wimController {
		
	private int rate;
			
	private WIMDAO dao;
	private WimData data;
	private boolean color;
			
	private String silueta, img1, img2;	

	private List<SelectItem> minutos, horas, classes;
	
	String noImage;	
	
	String silFolder = "C:\\Tracevia\\Software\\External\\Wim\\Silhuetas\\";
	
	String vehiclesFolder = "C:\\Tracevia\\Software\\External\\Wim\\Veiculos\\";
	
	String noImageFolder = "C:\\Tracevia\\Software\\External\\Unknown\\";
		
	public WimData getData() {
		return data;
	}

	public void setData(WimData data) {
		this.data = data;
	}

	public boolean isColor() {
		return color;
	}

	public void setColor(boolean color) {
		this.color = color;
	}

	public String getSilueta() {
		return silueta;
	}

	public void setSilueta(String silueta) {
		this.silueta = silueta;
	}

	public String getImg1() {
		return img1;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}

	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

	public List<SelectItem> getMinutos() {
		return minutos;
	}

	public List<SelectItem> getHoras() {
		return horas;
	}

	public List<SelectItem> getClasses() {
		return classes;
	}

	@PostConstruct
	public void initalize(){
				
		data = new WimData();
		dao = new WIMDAO();
		
		// initalize wim realtime
		//colorInitial();
		//rate();
		updateView();
		
		try {
			
			//dados();
			initializeVeh();
							
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}						
	}
	
	public void onibus() {
		
		try {
			
			String data = "2021-01-15 15:01:51", classe = "2A";
			int axlNumber = 2, axl1W = 250, axl2W = 300, axl3W= 0,
					axl4W= 0, axl5W= 0, axl6W= 0, axl7W= 0, axl8W= 0,
					axl9W= 0, axl2D= 220, axl3D= 0, axl4D= 0, axl5D= 0,
					axl6D= 0, axl7D= 0, axl8D= 0, axl9D= 0, gross= 7900, speed= 30;
			
			dao.updateFilePath(data, classe, axlNumber, axl1W, axl2W, axl3W, axl4W, axl5W, axl6W, axl7W, axl8W, axl9W,
					axl2D, axl3D, axl4D, axl5D, axl6D, axl7D, axl8D, axl9D, gross, speed);
			
			img1 = getImagePath(vehiclesFolder+"onibus1.jpg");
			img2 = getImagePath(vehiclesFolder+"onibus2.jpg");
			silueta = getImagePath(silFolder+"onibusE2.jpg");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initalize();
	}
	public void onibusE3() {
		try {
			String data = "2021-05-05 11:25:01", classe = "4A";
			int axlNumber = 3, axl1W = 300, axl2W = 325, axl3W= 360,
					axl4W= 0, axl5W= 0, axl6W= 0, axl7W= 0, axl8W= 0,
					axl9W= 0, axl2D= 310, axl3D= 0, axl4D= 0, axl5D= 0,
					axl6D= 0, axl7D= 0, axl8D= 0, axl9D= 0, gross= 3500, speed= 30;
			dao.updateFilePath(data, classe, axlNumber, axl1W, axl2W, axl3W, axl4W, axl5W, axl6W, axl7W, axl8W, axl9W,
					axl2D, axl3D, axl4D, axl5D, axl6D, axl7D, axl8D, axl9D, gross, speed);
			
			img1 = getImagePath(vehiclesFolder+"onibus1-2.jpg");
			img2 = getImagePath(vehiclesFolder+"onibus2-1.jpg");
			silueta = getImagePath(silFolder+"onibusE3.jpg");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initalize();
	}
	public void car() {
		try {
			String data = "2021-05-05 11:25:01", classe = "1";
			int axlNumber = 2, axl1W = 180, axl2W = 200, axl3W= 0,
					axl4W= 0, axl5W= 0, axl6W= 0, axl7W= 0, axl8W= 0,
					axl9W= 0, axl2D= 180, axl3D= 0, axl4D= 0, axl5D= 0,
					axl6D= 0, axl7D= 0, axl8D= 0, axl9D= 0, gross= 3500, speed= 30;
			
			dao.updateFilePath(data, classe, axlNumber, axl1W, axl2W, axl3W, axl4W, axl5W, axl6W, axl7W, axl8W, axl9W,
					axl2D, axl3D, axl4D, axl5D, axl6D, axl7D, axl8D, axl9D, gross, speed);
			
			img1 = getImagePath(vehiclesFolder+"carro1.jpg");
			img2 = getImagePath(vehiclesFolder+"carro2.jpg");
			silueta = getImagePath(silFolder+"10.jpg");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initalize();
	}
	public void e2() {
		try {
			String data = "2011-05-15 01:58:44", classe = "2";
			int axlNumber = 2, axl1W = 280, axl2W = 300, axl3W= 0,
					axl4W= 0, axl5W= 0, axl6W= 0, axl7W= 0, axl8W= 0,
					axl9W= 0, axl2D= 298, axl3D= 0, axl4D= 0, axl5D= 0,
					axl6D= 0, axl7D= 0, axl8D= 0, axl9D= 0, gross= 7350, speed= 45;
			
			dao.updateFilePath(data, classe, axlNumber, axl1W, axl2W, axl3W, axl4W, axl5W, axl6W, axl7W, axl8W, axl9W,
					axl2D, axl3D, axl4D, axl5D, axl6D, axl7D, axl8D, axl9D, gross, speed);
			
			img1 = getImagePath(vehiclesFolder+"e2-1.jpg");
			img2 = getImagePath(vehiclesFolder+"e2-2.jpg");
			silueta = getImagePath(silFolder+"2.jpg");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initalize();
	}
	public void e3() {
		try {
			String data = "2021-06-25 16:26:16", classe = "4";
			int axlNumber = 3, axl1W = 210, axl2W = 225, axl3W= 245,
					axl4W= 0, axl5W= 0, axl6W= 0, axl7W= 0, axl8W= 0,
					axl9W= 0, axl2D= 214, axl3D= 228, axl4D= 0, axl5D= 0,
					axl6D= 0, axl7D= 0, axl8D= 0, axl9D= 0, gross= 16000, speed= 47;
			
			dao.updateFilePath(data, classe, axlNumber, axl1W, axl2W, axl3W, axl4W, axl5W, axl6W, axl7W, axl8W, axl9W,
					axl2D, axl3D, axl4D, axl5D, axl6D, axl7D, axl8D, axl9D, gross, speed);
			
			img1 = getImagePath(vehiclesFolder+"eixo3-1.jpg");
			img2 = getImagePath(vehiclesFolder+"eixo3-2.jpg");
			silueta = getImagePath(silFolder+"3.jpg");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initalize();
	}
	public void e4() {
		try {
			String data = "2021-05-25 15:20:01", classe = "6";
			int axlNumber = 4, axl1W = 211, axl2W = 215, axl3W= 222,
					axl4W= 228, axl5W= 0, axl6W= 0, axl7W= 0, axl8W= 0,
					axl9W= 0, axl2D= 202, axl3D= 205, axl4D= 218, axl5D= 0,
					axl6D= 0, axl7D= 0, axl8D= 0, axl9D= 0, gross= 21000, speed= 28;
			
			dao.updateFilePath(data, classe, axlNumber, axl1W, axl2W, axl3W, axl4W, axl5W, axl6W, axl7W, axl8W, axl9W,
					axl2D, axl3D, axl4D, axl5D, axl6D, axl7D, axl8D, axl9D, gross, speed);
			
			img1 = getImagePath(vehiclesFolder+"caminhao4-1.jpg");
			img2 = getImagePath(vehiclesFolder+"caminhao4-2.jpg");
			silueta = getImagePath(silFolder+"4.jpg");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initalize();
	}
	public void e5() {
		try {
			String data = "2021-07-28 15:20:01", classe = "7";
			int axlNumber = 5, axl1W = 180, axl2W = 200, axl3W= 205,
					axl4W= 218, axl5W= 238, axl6W= 0, axl7W= 0, axl8W= 0,
					axl9W= 0, axl2D= 202, axl3D= 208, axl4D= 220, axl5D= 243,
					axl6D= 0, axl7D= 0, axl8D= 0, axl9D= 0, gross= 31500, speed= 31;
			
			dao.updateFilePath(data, classe, axlNumber, axl1W, axl2W, axl3W, axl4W, axl5W, axl6W, axl7W, axl8W, axl9W,
					axl2D, axl3D, axl4D, axl5D, axl6D, axl7D, axl8D, axl9D, gross, speed);
			
			img1 = getImagePath(vehiclesFolder+"caminhao5-1.jpg");
			img2 = getImagePath(vehiclesFolder+"caminhao5-1.jpg");
			silueta = getImagePath(silFolder+"5.jpg");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initalize();
	}
	public void e6() {
		try {
			String data = "2021-03-23 18:10:21", classe = "8";
			int axlNumber = 6, axl1W = 180, axl2W = 200, axl3W= 214,
					axl4W= 228, axl5W= 241, axl6W= 271, axl7W= 0, axl8W= 0,
					axl9W= 0, axl2D= 168, axl3D= 197, axl4D= 219, axl5D= 236,
					axl6D= 266, axl7D= 0, axl8D= 0, axl9D= 0, gross= 38900, speed= 20;
			
			dao.updateFilePath(data, classe, axlNumber, axl1W, axl2W, axl3W, axl4W, axl5W, axl6W, axl7W, axl8W, axl9W,
					axl2D, axl3D, axl4D, axl5D, axl6D, axl7D, axl8D, axl9D, gross, speed);
			
			img1 = getImagePath(vehiclesFolder+"caminhao5-1.jpg");
			img2 = getImagePath(vehiclesFolder+"caminhao5-2.jpg");
			silueta = getImagePath(silFolder+"e6.jpg");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initalize();
	}
	public void e7() {
		try {
			String data = "2021-02-15 18:22:01", classe = "11";
			int axlNumber = 9, axl1W = 174, axl2W = 220, axl3W= 250,
					axl4W= 278, axl5W= 280, axl6W= 291, axl7W= 300, axl8W= 311,
					axl9W= 0, axl2D= 200, axl3D= 240, axl4D= 262, axl5D= 271,
					axl6D= 282, axl7D= 290, axl8D= 301, axl9D= 0, gross= 51200, speed= 35;
			
			dao.updateFilePath(data, classe, axlNumber, axl1W, axl2W, axl3W, axl4W, axl5W, axl6W, axl7W, axl8W, axl9W,
					axl2D, axl3D, axl4D, axl5D, axl6D, axl7D, axl8D, axl9D, gross, speed);
			
			img1 = getImagePath(vehiclesFolder+"caminhao7-1.jpg");
			img2 = getImagePath(vehiclesFolder+"caminhao7-1.jpg");
			silueta = getImagePath(silFolder+"e7.jpg");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initalize();
	}
	public void e9() {
		try {
			String data = "2021-12-15 13:30:00", classe = "E9";
			int axlNumber = 9, axl1W = 174, axl2W = 220, axl3W= 250,
					axl4W= 278, axl5W= 280, axl6W= 291, axl7W= 300, axl8W= 311,
					axl9W= 345, axl2D= 200, axl3D= 240, axl4D= 262, axl5D= 271,
					axl6D= 282, axl7D= 290, axl8D= 301, axl9D= 338, gross= 74400, speed= 20;
			dao.updateFilePath(data, classe, axlNumber, axl1W, axl2W, axl3W, axl4W, axl5W, axl6W, axl7W, axl8W, axl9W,
					axl2D, axl3D, axl4D, axl5D, axl6D, axl7D, axl8D, axl9D, gross, speed);
			
			img1 = getImagePath(vehiclesFolder+"caminhao9-1.jpg");
			img2 = getImagePath(vehiclesFolder+"caminhao9-2.jpg");
			silueta = getImagePath(vehiclesFolder+"9.jpg");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initalize();
	}

	public String siluetaRealtime() throws Exception{
		String classe = dao.classe();
		if(classe != "") {
			if(classe.equals("1")) {
				String img = "car.jpg";
				silueta = getImagePath(silFolder + img);
			}else if(classe.equals("2")) {
				String img = "2.jpg";
				silueta = getImagePath(silFolder + img);
			}else if(classe.equals("4")) {
				String img = "3.jpg";
				silueta = getImagePath(silFolder + img);
			}else if(classe.equals("6")) {
				String img = "4.jpg";
				silueta = getImagePath(silFolder + img);
			}else if(classe.equals("7")) {
				String img = "5.jpg";
				silueta = getImagePath(silFolder + img);
			}else if(classe.equals("8")) {
				String img = "6.png";
				silueta = getImagePath(silFolder + img);
			}else if(classe.equals("11")) {
				String img = "8.jpg";
				silueta = getImagePath(silFolder + img);
			}else if(classe.equals("E9")) {
				String img = "9.jpg";
				silueta = getImagePath(silFolder + img);
			}else if(classe.equals("10N")) {
				String img = "9.jpg";
				silueta = getImagePath(silFolder + img);
			}else if(classe.equals("2A")) {
				String img = "onibusE2.jpg";
				silueta = getImagePath(silFolder + img);
			}else if(classe.equals("4A")) {
				String img = "onibusE3.jpg";
				silueta = getImagePath(silFolder + img);
			}else if(classe.equals("9")) {
				String img = "9.jpg";
				silueta = getImagePath(silFolder + img);
			}else if(classe.equals("3")) {
				String img = "9.jpg";
				silueta = getImagePath(silFolder + img);
			}else if(classe.equals("5")) {
				String img = "9.jpg";
				silueta = getImagePath(silueta = silFolder + img);
			}
		}
		
		return silueta;
	}
	
	//update View wim_Realtime
	public void updateView() {
		try {

			TimeUnit.SECONDS.sleep(4);

		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			
			//initializeVeh();
			//rate();
			//siluetaRealtime();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestContext request = RequestContext.getCurrentInstance();
		request.execute("btnUpdateView();");
	}
	//mudar cor dos inputs
	/*public void colorInitial() {
		try {
			color = dao.searchColor();
			RequestContext request = RequestContext.getCurrentInstance();
			if(color == false) {
				request.execute("color();");
				rate();
			}else {
				request.execute("colorReplacement();");
				rate();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
/*	public boolean colorInput() throws Exception {
		boolean x = false;
		color = dao.searchColor();
		RequestContext request = RequestContext.getCurrentInstance();
		if(color == false) {
			request.execute("color();");
			dao.color(true);
			rate();
		}else {
			request.execute("colorReplacement();");
			dao.color(false);
			rate();
		}
		initalize();
		return x;
	}*/
	//rate view wim.xhtml
	/*public void rate() {
		try {
			String classe = dao.classe();
			pbtTotal = Integer.parseInt(dao.pbtTotal());
			RequestContext request = RequestContext.getCurrentInstance();
			rate = pbtTotal;
			//classe onibus E2
			TranslationMethods trad = new TranslationMethods();
			if(classe.equals("1")) {
				if(rate < 6000) {
					rateTxt = trad.wimLabels("indicator3")+" "+ rate;
					request.execute("sizeNormal();");
				}else if( rate > 6000 && rate < 6450){
					request.execute("sizeAtenttion();");
					rateTxt = trad.wimLabels("indicator2")+" "+ rate;
				}else {
					request.execute("sizeAtenttion();");
					rateTxt = trad.wimLabels("indicator1")+" "+ rate;
				}
			}else if(classe.equals("2")){
				if(rate < 10000) {
					request.execute("sizeNormal();");
					rateTxt = trad.wimLabels("indicator3")+" "+ rate;
				}
			}else if(classe.equals("4")){
				if(rate < 17000) {
					request.execute("sizeNormal();");
					rateTxt = trad.wimLabels("indicator3")+" "+ rate;
				}else if(rate > 17000 && rate <= 17850){
					request.execute("sizeAtenttion();");
					rateTxt = trad.wimLabels("indicator2")+" "+ rate;
				}else{
					request.execute("sizeAcima();");
					rateTxt = trad.wimLabels("indicator1")+" "+ rate;
				}
			}else if(classe.equals("5")) {
				//essa classe falta definir o peso
				if(rate < 17000) {
					request.execute("sizeNormal();");
					rateTxt = trad.wimLabels("indicator3")+" "+ rate;
				}else if(rate > 17000 && rate <= 17850){
					request.execute("sizeAtenttion();");
					rateTxt = trad.wimLabels("indicator2")+" "+ rate;
				}else{
					request.execute("sizeAcima();");
					rateTxt = trad.wimLabels("indicator1")+" "+ rate;
				}
			}else if(classe.equals("6")||classe.equals("7")||classe.equals("8")){
				if(rate < 25500) {
					request.execute("sizeNormal();");
					rateTxt = trad.wimLabels("indicator3")+" "+ rate;
				}else if(rate > 25500 && rate <= 26775){
					request.execute("sizeAtenttion();");
					rateTxt = trad.wimLabels("indicator2")+" "+ rate;
				}else{
					request.execute("sizeAcima();");
					rateTxt = trad.wimLabels("indicator1")+" "+ rate;
				}
			}else if(classe.equals("9")){
				if(rate < 6000) {
					request.execute("sizeNormal();");
					rateTxt = trad.wimLabels("indicator3")+" "+ rate;
				}else if(rate > 6000 && rate <= 6450){
					request.execute("sizeAtenttion();");
					rateTxt = trad.wimLabels("indicator2")+" "+ rate;
				}else {
					request.execute("sizeAcima();");
					rateTxt = trad.wimLabels("indicator1")+" "+ rate;
				}
				//classe carro
			}else if(classe.equals("10")){
				if(rate < 51000) {
					request.execute("sizeNormal();");
					rateTxt = trad.wimLabels("indicator3")+" "+ rate;
				}else if(rate > 51000 && rate <= 53500){
					request.execute("sizeAtenttion();");
					rateTxt = trad.wimLabels("indicator2")+" "+ rate;
				}else{
					request.execute("sizeAcima();");
					rateTxt = trad.wimLabels("indicator1")+" "+ rate;
				}
			}else if(classe.equals("11")||classe.equals("E9")){
				if(rate < 68000) {
					request.execute("sizeNormal();");
					rateTxt = trad.wimLabels("indicator3")+" "+ rate;
				}else if(rate > 68000 && rate <= 71400){
					request.execute("sizeAtenttion();");
					rateTxt = trad.wimLabels("indicator2")+" "+ rate;
				}else{
					request.execute("sizeAcima();");
					rateTxt = trad.wimLabels("indicator1")+" "+ rate;
				}
			}else if(classe.equals("2A")){
				if(rate < 10000) {
					request.execute("sizeNormal();");
					rateTxt = trad.wimLabels("indicator3")+" "+ rate;
				}else if(rate > 10000 && rate <= 10500) {
					request.execute("sizeAtenttion();");
					rateTxt = trad.wimLabels("indicator2")+" "+ rate;
				}else {
					request.execute("sizeAcima();");
					rateTxt = trad.wimLabels("indicator1")+" "+ rate;
				}
				//classe onibus E3
			}else if(classe.equals("4A")){
				if(rate < 13500) {
					request.execute("sizeNormal();");
					rateTxt = trad.wimLabels("indicator3")+" "+ rate;
				}else if(rate > 13500 && rate <= 14175) {
					request.execute("sizeAtenttion();");
					rateTxt = trad.wimLabels("indicator2")+" "+ rate;
				}else {
					request.execute("sizeAcima();");
					rateTxt = trad.wimLabels("indicator1")+" "+ rate;
				}
			}else if(classe.equals("10N")){
				if(rate < 70001) {
					request.execute("sizeNormal();");
					rateTxt = trad.wimLabels("indicator3")+" "+ rate;
				}else if(rate > 70001 && rate <= 73500){
					request.execute("sizeAtenttion();");
					rateTxt = trad.wimLabels("indicator2")+" "+ rate;
				}else{
					request.execute("sizeAcima();");
					rateTxt = trad.wimLabels("indicator1")+" "+ rate;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	//dados
	/*public void dados() {
		try {
			
			
			img1 = getImagePath(noImage);
			img2 = getImagePath(noImage);
		    silueta = getImagePath(noImage);
			
			//Viewing vehicle information
			/*serialNumber = dao.serialNumber();
			dataHour = dao.dateHour();
			classe = dao.classe();
			eixo = dao.eixo();
			speed = dao.speed();
			pbtTotal = Integer.parseInt(dao.pbtTotal());
			//passando valor para Weight and Distance
			axl1W = dao.axl1W();
			axl2W = dao.axl2W();
			axl3W = dao.axl3W();
			axl4W = dao.axl4W();
			axl5W = dao.axl5W();
			axl6W = dao.axl6W();
			axl7W = dao.axl7W();
			axl8W = dao.axl8W();
			axl9W = dao.axl9W();
			axl2D = dao.axl2D();
			axl3D = dao.axl3D();
			axl4D = dao.axl4D();
			axl5D = dao.axl5D();
			axl6D = dao.axl6D();
			axl7D = dao.axl7D();
			axl8D = dao.axl8D();
			axl9D = dao.axl9D();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//array table  Weight and Distance
		//peso
		weight = new int[10];
		weight [1] = axl1W;
		weight [2] = axl2W;
		weight [3] = axl3W;
		weight [4] = axl4W;
		weight [5] = axl5W;
		weight [6] = axl6W;
		weight [7] = axl7W;
		weight [8] = axl8W;
		weight [9] = axl9W;
		//distancia entre os eixos
		dstAxes = new int[10];
		dstAxes [2] = axl2D;
		dstAxes [3] = axl3D;
		dstAxes [4] = axl4D;
		dstAxes [5] = axl5D;
		dstAxes [6] = axl6D;
		dstAxes [7] = axl7D;
		dstAxes [8] = axl8D;
		dstAxes [9] = axl9D;
		/*type = new int[8];
		type [1] = "";
		type [2] = "";
		try {
			siluetaRealtime();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	  public String getImagePath(String image) {
			
			try {

				Path path = Paths.get(image);								
				  byte[] file = Files.readAllBytes(path);
				  return Base64.getEncoder().encodeToString(file);
				  
			} catch (IOException e) {											
				return "";
			}

		}
	  
	  public void initializeVeh() {
		  		  
		  noImage = noImageFolder + "no-image.jpg";
			
		  img1 = getImagePath(noImage);
		  img2 = getImagePath(noImage);
		  silueta = getImagePath(noImage);	      
					  
		  data.setSerialNumber(" - ");
		  data.setDatetime(" - ");
		  data.setClasse(" - ");
		  data.setAxlNumber(" - ");
		  data.setSpeed(" - ");
		  data.setPbtTotal(" - ");
		  data.setSize(" - ");
		  		  
		  String[] weight = new String[10];
			weight [0] = "0";
			weight [1] = "0";
			weight [2] = "0";
			weight [3] = "0";
			weight [4] = "0";
			weight [5] = "0";
			weight [6] = "0";
			weight [7] = "0";
			weight [8] = "0";
			
			data.setAxlWeight(weight);
			
			//distancia entre os eixos
			String[] dstAxes = new String[10];
			dstAxes [0] = "0";
			dstAxes [1] = "0";
			dstAxes [2] = "0";
			dstAxes [3] = "0";
			dstAxes [4] = "0";
			dstAxes [5] = "0";
			dstAxes [6] = "0";
			dstAxes [7] = "0";
			dstAxes [8] = "0";

			data.setAxlDist(dstAxes);
			
			//distancia entre os eixos
			String[] typeAxes = new String[10];
			typeAxes [0] = " - ";
			typeAxes [1] = " - ";
			typeAxes [2] = " - ";
			typeAxes [3] = " - ";
			typeAxes [4] = " - ";
			typeAxes [5] = " - ";
			typeAxes [6] = " - ";
			typeAxes [7] = " - ";
			typeAxes [8] = " - ";

			data.setAxlType(dstAxes);
		  
	  }
	  
	  
	  public void randVehicle() {
		  
		  int classe = (int)(Math.random() * 9) + 1;
		  
	  }

}
