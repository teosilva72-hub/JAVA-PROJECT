package br.com.tracevia.webapp.controller.wimController;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import br.com.tracevia.webapp.dao.wim.WIMDAO;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.wim.WimData;
import br.com.tracevia.webapp.util.ImageUtil;

@ManagedBean(name="WimController")
@ViewScoped
public class WimController {
		
	private int rate;
			
	private WIMDAO dao;
	private WimData data;
	private boolean color;
			
	private String silueta, img1, img2, rateValue;	

	private List<SelectItem> minutos, horas, classes;
	
	DateTimeApplication dta;
	
	String noImage, vehFolder, silFolder;
			
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
		
	public String getRateValue() {
		return rateValue;
	}

	public void setRateValue(String rateValue) {
		this.rateValue = rateValue;
	}

	@PostConstruct
	public void initalize(){
				
		data = new WimData();
		dao = new WIMDAO();
		
		silFolder = "wim/sil";		
	    vehFolder = "wim/veh";		
		noImage = "no-image.jpg";
		
		// initalize wim realtime
		//colorInitial();
		//rate();
		//updateView();
			
		try {
			
			//dados();
			initializeVeh();
							
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}						
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
	
/*	public boolean colorInput(WimData data) throws Exception {
		boolean x = false;
		color = dao.searchColor();
		RequestContext request = RequestContext.getCurrentInstance();
		if(color == false) {
			request.execute("color();");
			dao.color(true);
			rate(data);
		}else {
			request.execute("colorReplacement();");
			dao.color(false);
			rate(data);
		}
		initalize();
		return x;
	}*/
	
	public void rate(WimData data) {
		
		try {
			
			String classe = data.getClasse();
					
			RequestContext request = RequestContext.getCurrentInstance();
			rate = Integer.parseInt(data.getPbtTotal());
			
			//classe onibus E2
			TranslationMethods trad = new TranslationMethods();
			
			if(classe.equals("1")) {
				if(rate < 6000) {
					rateValue = trad.wimLabels("indicator3")+" "+ rate;
					request.execute("sizeNormal();");
				}else if( rate > 6000 && rate < 6450){
					request.execute("sizeAtenttion();");
					rateValue= trad.wimLabels("indicator2")+" "+ rate;
				}else {
					request.execute("sizeAtenttion();");
					rateValue = trad.wimLabels("indicator1")+" "+ rate;
				}
			}else if(classe.equals("2")){
				if(rate < 10000) {
					request.execute("sizeNormal();");
					rateValue = trad.wimLabels("indicator3")+" "+ rate;
				}
			}else if(classe.equals("4")){
				if(rate < 17000) {
					request.execute("sizeNormal();");
					rateValue = trad.wimLabels("indicator3")+" "+ rate;
				}else if(rate > 17000 && rate <= 17850){
					request.execute("sizeAtenttion();");
					rateValue = trad.wimLabels("indicator2")+" "+ rate;
				}else{
					request.execute("sizeAcima();");
					rateValue = trad.wimLabels("indicator1")+" "+ rate;
				}
			}else if(classe.equals("5")) {
				//essa classe falta definir o peso
				if(rate < 17000) {
					request.execute("sizeNormal();");
					rateValue = trad.wimLabels("indicator3")+" "+ rate;
				}else if(rate > 17000 && rate <= 17850){
					request.execute("sizeAtenttion();");
					rateValue = trad.wimLabels("indicator2")+" "+ rate;
				}else{
					request.execute("sizeAcima();");
					rateValue = trad.wimLabels("indicator1")+" "+ rate;
				}
			}else if(classe.equals("6")||classe.equals("7")||classe.equals("8")){
				if(rate < 25500) {
					request.execute("sizeNormal();");
					rateValue = trad.wimLabels("indicator3")+" "+ rate;
				}else if(rate > 25500 && rate <= 26775){
					request.execute("sizeAtenttion();");
					rateValue = trad.wimLabels("indicator2")+" "+ rate;
				}else{
					request.execute("sizeAcima();");
					rateValue = trad.wimLabels("indicator1")+" "+ rate;
				}
			}else if(classe.equals("9")){
				if(rate < 6000) {
					request.execute("sizeNormal();");
					rateValue = trad.wimLabels("indicator3")+" "+ rate;
				}else if(rate > 6000 && rate <= 6450){
					request.execute("sizeAtenttion();");
					rateValue = trad.wimLabels("indicator2")+" "+ rate;
				}else {
					request.execute("sizeAcima();");
					rateValue = trad.wimLabels("indicator1")+" "+ rate;
				}
				//classe carro
			}else if(classe.equals("10")){
				if(rate < 51000) {
					request.execute("sizeNormal();");
					rateValue = trad.wimLabels("indicator3")+" "+ rate;
				}else if(rate > 51000 && rate <= 53500){
					request.execute("sizeAtenttion();");
					rateValue = trad.wimLabels("indicator2")+" "+ rate;
				}else{
					request.execute("sizeAcima();");
					rateValue = trad.wimLabels("indicator1")+" "+ rate;
				}
			}else if(classe.equals("11")||classe.equals("E9")){
				if(rate < 68000) {
					request.execute("sizeNormal();");
					rateValue = trad.wimLabels("indicator3")+" "+ rate;
				}else if(rate > 68000 && rate <= 71400){
					request.execute("sizeAtenttion();");
					rateValue = trad.wimLabels("indicator2")+" "+ rate;
				}else{
					request.execute("sizeAcima();");
					rateValue = trad.wimLabels("indicator1")+" "+ rate;
				}
			}else if(classe.equals("2A")){
				if(rate < 10000) {
					request.execute("sizeNormal();");
					rateValue = trad.wimLabels("indicator3")+" "+ rate;
				}else if(rate > 10000 && rate <= 10500) {
					request.execute("sizeAtenttion();");
					rateValue = trad.wimLabels("indicator2")+" "+ rate;
				}else {
					request.execute("sizeAcima();");
					rateValue = trad.wimLabels("indicator1")+" "+ rate;
				}
				//classe onibus E3
			}else if(classe.equals("4A")){
				if(rate < 13500) {
					request.execute("sizeNormal();");
					rateValue = trad.wimLabels("indicator3")+" "+ rate;
				}else if(rate > 13500 && rate <= 14175) {
					request.execute("sizeAtenttion();");
					rateValue = trad.wimLabels("indicator2")+" "+ rate;
				}else {
					request.execute("sizeAcima();");
					rateValue = trad.wimLabels("indicator1")+" "+ rate;
				}
			}else if(classe.equals("10N") || classe.equals("E9")){
				if(rate < 70001) {
					request.execute("sizeNormal();");
					rateValue = trad.wimLabels("indicator3")+" "+ rate;
				}else if(rate > 70001 && rate <= 73500){
					request.execute("sizeAtenttion();");
					rateValue = trad.wimLabels("indicator2")+" "+ rate;
				}else{
					request.execute("sizeAcima();");
					rateValue = trad.wimLabels("indicator1")+" "+ rate;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
			  
	  public void initializeVeh() {
		  
		  String unknownImage = ImageUtil.getImagePath("images", "unknown", noImage);
		  String encondedUnknownImage = ImageUtil.encodeToBase64(unknownImage);
		 		 
		  img1 = encondedUnknownImage;
		  img2 = encondedUnknownImage;
		  silueta = encondedUnknownImage;    
				  		  
		  data.setSerialNumber(" - ");
		  data.setDatetime(" - ");
		  data.setClasse(" - ");
		  data.setAxlNumber(" - ");
		  data.setSpeed(" - ");
		  data.setPbtTotal(" - ");
		  data.setSize(" - ");
		  		  
		  String[] weight = new String[9];
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
			String[] dstAxes = new String[9];
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
			String[] typeAxes = new String[9];
			typeAxes [0] = " - ";
			typeAxes [1] = " - ";
			typeAxes [2] = " - ";
			typeAxes [3] = " - ";
			typeAxes [4] = " - ";
			typeAxes [5] = " - ";
			typeAxes [6] = " - ";
			typeAxes [7] = " - ";
			typeAxes [8] = " - ";

			data.setAxlType(typeAxes);
		  
	  }
	  
	  //------------------------------------------------------------------------
	
	  public void generateCar() {
		  
	   DateTimeApplication dta = new DateTimeApplication();
	   						   
	      boolean saved = false;
  		  
		  img1 = ImageUtil.getImagePathAndEncodeToBase64("images", vehFolder, "WIM_car.jpg");
		  img2 = ImageUtil.getImagePathAndEncodeToBase64("images", vehFolder, "Plate_WIM_car.jpg");
		  silueta = ImageUtil.getImagePathAndEncodeToBase64("images", silFolder, "car.jpg");
		  					      			
		  data.setImage("WIM_car.jpg");
		  data.setImagePlate("Plate_WIM_car.jpg");
		  data.setImageSil("car.jpg");
		  data.setId(1000);		
		  data.setSerialNumber(String.valueOf((int)(Math.random() * 1000 ) + 1));
		  data.setDatetime(dta.currentDateTime());
		  data.setClasse("1");
		  data.setAxlNumber("2");
		  data.setSpeed("60");
		  data.setPbtTotal("1000");
		  data.setSize("700");
		  		  
		  String[] weight = new String[9];
			weight [0] = "400";
			weight [1] = "600";
			weight [2] = "0";
			weight [3] = "0";
			weight [4] = "0";
			weight [5] = "0";
			weight [6] = "0";
			weight [7] = "0";
			weight [8] = "0";
			
			data.setAxlWeight(weight);
			
			//distancia entre os eixos
			String[] dstAxes = new String[9];
			dstAxes [0] = "330";
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
			String[] typeAxes = new String[9];
			typeAxes [0] = " S ";
			typeAxes [1] = " S ";
			typeAxes [2] = " - ";
			typeAxes [3] = " - ";
			typeAxes [4] = " - ";
			typeAxes [5] = " - ";
			typeAxes [6] = " - ";
			typeAxes [7] = " - ";
			typeAxes [8] = " - ";

			data.setAxlType(typeAxes);					    
			
			dao = new WIMDAO();			
										
			try {

				if(!saved)				   					
				   dao.saveVehicle(data);
				   				   
				   rate(data); // COR INDICATOR
				   								
			} catch (Exception e) {			
							
				e.printStackTrace();
			}						
	  }
	  	  
	  //------------------------------------------------------------------------
		
	  public void generateBus2() {
		  
		    boolean saved = false;
		  
	      DateTimeApplication dta = new DateTimeApplication();
	    		  
		  img1 = ImageUtil.getImagePathAndEncodeToBase64("images", vehFolder, "WIM_bus2.jpg");
		  img2 = ImageUtil.getImagePathAndEncodeToBase64("images", vehFolder, "Plate_WIM_bus2.jpg");
		  silueta = ImageUtil.getImagePathAndEncodeToBase64("images", silFolder, "bus2.jpg");
					      		
		  data.setImage("WIM_bus2.jpg");
		  data.setImagePlate("Plate_WIM_bus2.jpg");
		  data.setImageSil("bus2.jpg");
		  data.setId(1000);	
		  data.setSerialNumber(String.valueOf((int)(Math.random() * 1000 ) + 1));
		  data.setDatetime(dta.currentDateTime());
		  data.setClasse("2A");
		  data.setAxlNumber("2");
		  data.setSpeed("75");
		  data.setPbtTotal("7000");
		  data.setSize("1000");
		  		  
		  String[] weight = new String[9];
			weight [0] = "2500";
			weight [1] = "4500";
			weight [2] = "0";
			weight [3] = "0";
			weight [4] = "0";
			weight [5] = "0";
			weight [6] = "0";
			weight [7] = "0";
			weight [8] = "0";
			
			data.setAxlWeight(weight);
			
			//distancia entre os eixos
			String[] dstAxes = new String[9];
			dstAxes [0] = "600";
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
			String[] typeAxes = new String[9];
			typeAxes [0] = " S ";
			typeAxes [1] = " S ";
			typeAxes [2] = " - ";
			typeAxes [3] = " - ";
			typeAxes [4] = " - ";
			typeAxes [5] = " - ";
			typeAxes [6] = " - ";
			typeAxes [7] = " - ";
			typeAxes [8] = " - ";

			data.setAxlType(typeAxes);
			
			try {

				if(!saved)
				   dao.saveVehicle(data);
				
				rate(data); // COR INDICATOR
				
			} catch (Exception e) {			
							
				e.printStackTrace();
			}		  
		  
	  }
	  
	  //------------------------------------------------------------------------
		
	  public void generateTruck2() {
		  
		  boolean saved = false;
		  
		  DateTimeApplication dta = new DateTimeApplication();
	   	   			  
		  img1 = ImageUtil.getImagePathAndEncodeToBase64("images", vehFolder, "WIM_trck2.jpg");
		  img2 = ImageUtil.getImagePathAndEncodeToBase64("images", vehFolder, "Plate_WIM_trck2.jpg");
		  silueta = ImageUtil.getImagePathAndEncodeToBase64("images", silFolder, "2.jpg");
					   
		  data.setImage("WIM_trck2.jpg");
		  data.setImagePlate("Plate_WIM_trck2.jpg");
		  data.setImageSil("2.jpg");
		  data.setId(1000);	
		  data.setSerialNumber(String.valueOf((int)(Math.random() * 1000 ) + 1));
		  data.setDatetime(dta.currentDateTime());
		  data.setClasse("2");
		  data.setAxlNumber("2");
		  data.setSpeed("90");
		  data.setPbtTotal("4000");
		  data.setSize("1200");
		  		  
		  String[] weight = new String[9];
			weight [0] = "1500";
			weight [1] = "2500";
			weight [2] = "0";
			weight [3] = "0";
			weight [4] = "0";
			weight [5] = "0";
			weight [6] = "0";
			weight [7] = "0";
			weight [8] = "0";
			
			data.setAxlWeight(weight);
			
			//distancia entre os eixos
			String[] dstAxes = new String[9];
			dstAxes [0] = "500";
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
			String[] typeAxes = new String[9];
			typeAxes [0] = " S ";
			typeAxes [1] = " S ";
			typeAxes [2] = " - ";
			typeAxes [3] = " - ";
			typeAxes [4] = " - ";
			typeAxes [5] = " - ";
			typeAxes [6] = " - ";
			typeAxes [7] = " - ";
			typeAxes [8] = " - ";

			data.setAxlType(typeAxes);
			
			try {

				if(!saved)
				   dao.saveVehicle(data);
				
				rate(data); // COR INDICATOR
				
			} catch (Exception e) {			
							
				e.printStackTrace();
			}		  
		  
	  }
	  
	  //------------------------------------------------------------------------
		
	  public void generateTruck3() {
		  
		  boolean saved = false;
		  
	   DateTimeApplication dta = new DateTimeApplication();
  		  			  
		  img1 = ImageUtil.getImagePathAndEncodeToBase64("images", vehFolder, "WIM_trck3.jpg");
		  img2 = ImageUtil.getImagePathAndEncodeToBase64("images", vehFolder, "Plate_WIM_trck3.jpg");
		  silueta = ImageUtil.getImagePathAndEncodeToBase64("images", silFolder, "3.jpg");
					   		   
	      data.setImage("WIM_trck3.jpg");
	      data.setImagePlate("Plate_WIM_trck3.jpg");
	      data.setImageSil("3.jpg");
	      data.setId(1000);	
	      data.setSerialNumber(String.valueOf((int)(Math.random() * 1000 ) + 1));
		  data.setDatetime(dta.currentDateTime());
		  data.setClasse("4");
		  data.setAxlNumber("3");
		  data.setSpeed("75");
		  data.setPbtTotal("7000");
		  data.setSize("1500");
		  		  
		  String[] weight = new String[9];
			weight [0] = "1500";
			weight [1] = "2500";
			weight [2] = "2500";
			weight [3] = "0";
			weight [4] = "0";
			weight [5] = "0";
			weight [6] = "0";
			weight [7] = "0";
			weight [8] = "0";
			
			data.setAxlWeight(weight);
			
			//distancia entre os eixos
			String[] dstAxes = new String[9];
			dstAxes [0] = "500";
			dstAxes [1] = "150";
			dstAxes [2] = "0";
			dstAxes [3] = "0";
			dstAxes [4] = "0";
			dstAxes [5] = "0";
			dstAxes [6] = "0";
			dstAxes [7] = "0";
			dstAxes [8] = "0";

			data.setAxlDist(dstAxes);
			
			//distancia entre os eixos
			String[] typeAxes = new String[9];
			typeAxes [0] = " S ";
			typeAxes [1] = " D ";
			typeAxes [2] = " D ";
			typeAxes [3] = " - ";
			typeAxes [4] = " - ";
			typeAxes [5] = " - ";
			typeAxes [6] = " - ";
			typeAxes [7] = " - ";
			typeAxes [8] = " - ";

			data.setAxlType(typeAxes);
			
			try {

				if(!saved)
				   dao.saveVehicle(data);
				
				rate(data); // COR INDICATOR
				
			} catch (Exception e) {			
							
				e.printStackTrace();
			}			  
	     }
	  
	  
	  //------------------------------------------------------------------------
		
	  public void generateTruck5() {
		  
		  boolean saved = false;
		  
	   DateTimeApplication dta = new DateTimeApplication();
  				  
		  img1 = ImageUtil.getImagePathAndEncodeToBase64("images", vehFolder, "WIM_trck5.jpg");
		  img2 = ImageUtil.getImagePathAndEncodeToBase64("images", vehFolder, "Plate_WIM_trck5.jpg");
		  silueta = ImageUtil.getImagePathAndEncodeToBase64("images", silFolder, "5.jpg");
					   					     
		  data.setImage("WIM_trck5.jpg");
	      data.setImagePlate("Plate_WIM_trck5.jpg");
	      data.setImageSil("5.jpg");
	      data.setSerialNumber(String.valueOf((int)(Math.random() * 1000 ) + 1));
		  data.setDatetime(dta.currentDateTime());
		  data.setClasse("5");
		  data.setAxlNumber("3");
		  data.setSpeed("75");
		  data.setPbtTotal("17250");
		  data.setSize("7000");
		  		  
		  String[] weight = new String[9];
			weight [0] = "6000";
			weight [1] = "1250";
			weight [2] = "3000";
			weight [3] = "3000";
			weight [4] = "3000";
			weight [5] = "0";
			weight [6] = "0";
			weight [7] = "0";
			weight [8] = "0";
			
			data.setAxlWeight(weight);
			
			//distancia entre os eixos
			String[] dstAxes = new String[9];
			dstAxes [0] = "240";
			dstAxes [1] = "150";
			dstAxes [2] = "240";
			dstAxes [3] = "150";
			dstAxes [4] = "0";
			dstAxes [5] = "0";
			dstAxes [6] = "0";
			dstAxes [7] = "0";
			dstAxes [8] = "0";

			data.setAxlDist(dstAxes);
			
			//distancia entre os eixos
			String[] typeAxes = new String[9];
			typeAxes [0] = " S ";
			typeAxes [1] = " D ";
			typeAxes [2] = " D ";
			typeAxes [3] = " D ";
			typeAxes [4] = " D ";
			typeAxes [5] = " - ";
			typeAxes [6] = " - ";
			typeAxes [7] = " - ";
			typeAxes [8] = " - ";

			data.setAxlType(typeAxes);
			
			try {

				if(!saved)
				   dao.saveVehicle(data);
				
				rate(data); // COR INDICATOR
				
			} catch (Exception e) {			
							
				e.printStackTrace();
			}	  
	     }
	  
	  
	  //------------------------------------------------------------------------
		
	  public void generateTruck6() {
		  
		  boolean saved = false;
		  
	   DateTimeApplication dta = new DateTimeApplication();
  		  		  
		  img1 = ImageUtil.getImagePathAndEncodeToBase64("images", vehFolder, "WIM_trck6.jpg");
		  img2 = ImageUtil.getImagePathAndEncodeToBase64("images", vehFolder, "Plate_WIM_trck6.jpg");
		  silueta = ImageUtil.getImagePathAndEncodeToBase64("images", silFolder, "6.jpg");
					   					      	
		  data.setImage("WIM_trck6.jpg");
	      data.setImagePlate("Plate_WIM_trck6.jpg");
	      data.setImageSil("6.jpg");
	      data.setSerialNumber(String.valueOf((int)(Math.random() * 1000 ) + 1));
		  data.setDatetime(dta.currentDateTime());
		  data.setClasse("8");
		  data.setAxlNumber("6");
		  data.setSpeed("75");
		  data.setPbtTotal("48000");
		  data.setSize("8000");
		  		  
		  String[] weight = new String[9];
			weight [0] = "6000";
			weight [1] = "8000";
			weight [2] = "8000";
			weight [3] = "6000";
			weight [4] = "10000";
			weight [5] = "10000";
			weight [6] = "0";
			weight [7] = "0";
			weight [8] = "0";
			
			data.setAxlWeight(weight);
			
			//distancia entre os eixos
			String[] dstAxes = new String[9];
			dstAxes [0] = "240";
			dstAxes [1] = "150";
			dstAxes [2] = "240";
			dstAxes [3] = "100";
			dstAxes [4] = "100";
			dstAxes [5] = "100";
			dstAxes [6] = "0";
			dstAxes [7] = "0";
			dstAxes [8] = "0";

			data.setAxlDist(dstAxes);
			
			//distancia entre os eixos
			String[] typeAxes = new String[9];
			typeAxes [0] = " S ";
			typeAxes [1] = " D ";
			typeAxes [2] = " D ";
			typeAxes [3] = " D ";
			typeAxes [4] = " D ";
			typeAxes [5] = " D ";
			typeAxes [6] = " - ";
			typeAxes [7] = " - ";
			typeAxes [8] = " - ";

			data.setAxlType(typeAxes);
			
			try {

				if(!saved)
				   dao.saveVehicle(data);
				
				rate(data); // COR INDICATOR
				
			} catch (Exception e) {			
							
				e.printStackTrace();
			}	  
	     }
	  
	  //------------------------------------------------------------------------
		
	  public void generateTruck7() {
		  
		  boolean saved = false;
		  
	   DateTimeApplication dta = new DateTimeApplication();
  			  
		  img1 = ImageUtil.getImagePathAndEncodeToBase64("images", vehFolder, "WIM_trck7.jpg");
		  img2 = ImageUtil.getImagePathAndEncodeToBase64("images", vehFolder, "Plate_WIM_trck7.jpg");
		  silueta = ImageUtil.getImagePathAndEncodeToBase64("images", silFolder, "7.jpg");				   
					      		
		  data.setImage("WIM_trck7.jpg");
	      data.setImagePlate("Plate_WIM_trck7.jpg");
	      data.setImageSil("7.jpg");
	      data.setSerialNumber(String.valueOf((int)(Math.random() * 1000 ) + 1));
		  data.setDatetime(dta.currentDateTime());
		  data.setClasse("10");
		  data.setAxlNumber("7");
		  data.setSpeed("75");
		  data.setPbtTotal("56000");
		  data.setSize("10000");
		  		  
		  String[] weight = new String[9];
			weight [0] = "6000";
			weight [1] = "8000";
			weight [2] = "7000";
			weight [3] = "9000";
			weight [4] = "10000";
			weight [5] = "10000";
			weight [6] = "6000";
			weight [7] = "0";
			weight [8] = "0";
			
			data.setAxlWeight(weight);
			
			//distancia entre os eixos
			String[] dstAxes = new String[9];
			dstAxes [0] = "240";
			dstAxes [1] = "100";
			dstAxes [2] = "240";
			dstAxes [3] = "100";
			dstAxes [4] = "240";
			dstAxes [5] = "100";
			dstAxes [6] = "240";
			dstAxes [7] = "100";
			dstAxes [8] = "0";

			data.setAxlDist(dstAxes);
			
			//distancia entre os eixos
			String[] typeAxes = new String[9];
			typeAxes [0] = " S ";
			typeAxes [1] = " D ";
			typeAxes [2] = " D ";
			typeAxes [3] = " D ";
			typeAxes [4] = " D ";
			typeAxes [5] = " D ";
			typeAxes [6] = " D ";
			typeAxes [7] = " - ";
			typeAxes [8] = " - ";

			data.setAxlType(typeAxes);
			
			try {

				if(!saved)
				   dao.saveVehicle(data);
				
				rate(data); // COR INDICATOR
				
			} catch (Exception e) {			
							
				e.printStackTrace();
			}		  		  
	    }
	  
	  //------------------------------------------------------------------------
		
	  public void generateTruck9() {
		  
		  boolean saved = false;
		  
	   DateTimeApplication dta = new DateTimeApplication();
  		  				  
		  img1 = ImageUtil.getImagePathAndEncodeToBase64("images", vehFolder, "WIM_trck9.jpg");
		  img2 = ImageUtil.getImagePathAndEncodeToBase64("images", vehFolder, "Plate_WIM_trck9.jpg");
		  silueta = ImageUtil.getImagePathAndEncodeToBase64("images", silFolder, "9.jpg");
					   		    	
		  data.setImage("WIM_trck9.jpg");
	      data.setImagePlate("Plate_WIM_trck9.jpg");
	      data.setImageSil("E9.jpg");
	      data.setSerialNumber(String.valueOf((int)(Math.random() * 1000 ) + 1));
		  data.setDatetime(dta.currentDateTime());
		  data.setClasse("E9");
		  data.setAxlNumber("7");
		  data.setSpeed("80");
		  data.setPbtTotal("73000");
		  data.setSize("9000");
		  		  
		  String[] weight = new String[9];
			weight [0] = "6000";
			weight [1] = "8000";
			weight [2] = "7000";
			weight [3] = "9000";
			weight [4] = "10000";
			weight [5] = "10000";
			weight [6] = "8000";
			weight [7] = "7000";
			weight [8] = "8000";
			
			data.setAxlWeight(weight);
			
			//distancia entre os eixos
			String[] dstAxes = new String[9];
			dstAxes [0] = "240";
			dstAxes [1] = "100";
			dstAxes [2] = "240";
			dstAxes [3] = "100";
			dstAxes [4] = "100";
			dstAxes [5] = "200";
			dstAxes [6] = "100";
			dstAxes [7] = "100";
			dstAxes [8] = "0";

			data.setAxlDist(dstAxes);
			
			//distancia entre os eixos
			String[] typeAxes = new String[9];
			typeAxes [0] = " S ";
			typeAxes [1] = " D ";
			typeAxes [2] = " D ";
			typeAxes [3] = " D ";
			typeAxes [4] = " D ";
			typeAxes [5] = " D ";
			typeAxes [6] = " D ";
			typeAxes [7] = " D ";
			typeAxes [8] = " D ";

			data.setAxlType(typeAxes);
			
			try {

				if(!saved)
				   dao.saveVehicle(data);
				
				rate(data); // COR INDICATOR
				
			} catch (Exception e) {			
							
				e.printStackTrace();
			}		  		  
	    }	  
	  
	  
	 

}
