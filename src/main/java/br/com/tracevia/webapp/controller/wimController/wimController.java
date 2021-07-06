package br.com.tracevia.webapp.controller.wimController;
import java.io.File;
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
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.wim.WimData;

@ManagedBean(name="wimController")
@ViewScoped
public class wimController {
	
	private int axl1W, axl2W, axl3W, axl4W, axl5W,
	axl6W, axl7W, axl8W, axl9W, axl1D, axl2D, axl3D,
	axl4D, axl5D,
	axl6D, axl7D, axl8D, axl9D;
	
	String noImage, noImageFolder, silFolder, vehiclesFolder;	
	
	private String rateTxt;
				
	private int serialNumber;
	
	int pbtTotal;
	
	private int rate;
	
	private int[] axes;
	
	private String[] listarFile;
	
	private WIMDAO dao = new WIMDAO();
	private boolean color;

	
	public int[] getAxes() {
		return axes;
	}
	public void setAxes(int[] axes) {
		this.axes = axes;
	}
	private int[] weight, details, type;

	public int[] getType() {
		return type;
	}
	public void setType(int[] type) {
		this.type = type;
	}

	public int[] getDetails() {
		return details;
	}
	public void setDetails(int[] details) {
		this.details = details;
	}
	public int[] getWeight() {
		return weight;
	}
	public void setWeight(int[] weight) {
		this.weight = weight;
	}
	private int[] dstAxes;

	public int[] getDstAxes() {
		return dstAxes;
	}
	public void setDstAxes(int[] dstAxes) {
		this.dstAxes = dstAxes;
	}

	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getRateTxt() {
		return rateTxt;
	}
	public void setRateTxt(String rateTxt) {
		this.rateTxt = rateTxt;
	}
	
	public String[] getListarFile() {
		return listarFile;
	}
	public void setListarFile(String[] listarFile) {
		this.listarFile = listarFile;
	}
	
	public int getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	private String dataHour, classe, eixo, speed, size;

	public String getDataHour() {
		return dataHour;
	}
	public void setDataHour(String dataHour) {
		this.dataHour = dataHour;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public String getEixo() {
		return eixo;
	}
	public void setEixo(String eixo) {
		this.eixo = eixo;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	
	public int getPbtTotal() {
		return pbtTotal;
	}
	public void setPbtTotal(int pbtTotal) {
		this.pbtTotal = pbtTotal;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}

	public int getAxl1W() {
		return axl1W;
	}
	public void setAxl1W(int axl1w) {
		axl1W = axl1w;
	}
	public int getAxl2W() {
		return axl2W;
	}
	public void setAxl2W(int axl2w) {
		axl2W = axl2w;
	}
	public int getAxl3W() {
		return axl3W;
	}
	public void setAxl3W(int axl3w) {
		axl3W = axl3w;
	}
	public int getAxl4W() {
		return axl4W;
	}
	public void setAxl4W(int axl4w) {
		axl4W = axl4w;
	}
	public int getAxl5W() {
		return axl5W;
	}
	public void setAxl5W(int axl5w) {
		axl5W = axl5w;
	}
	public int getAxl6W() {
		return axl6W;
	}
	public void setAxl6W(int axl6w) {
		axl6W = axl6w;
	}
	public int getAxl7W() {
		return axl7W;
	}
	public void setAxl7W(int axl7w) {
		axl7W = axl7w;
	}
	public int getAxl8W() {
		return axl8W;
	}
	public void setAxl8W(int axl8w) {
		axl8W = axl8w;
	}
	public int getAxl9W() {
		return axl9W;
	}
	public void setAxl9W(int axl9w) {
		axl9W = axl9w;
	}
	public int getAxl1D() {
		return axl1D;
	}
	public void setAxl1D(int axl1d) {
		axl1D = axl1d;
	}
	public int getAxl2D() {
		return axl2D;
	}
	public void setAxl2D(int axl2d) {
		axl2D = axl2d;
	}
	public int getAxl3D() {
		return axl3D;
	}
	public void setAxl3D(int axl3d) {
		axl3D = axl3d;
	}
	public int getAxl4D() {
		return axl4D;
	}
	public void setAxl4D(int axl4d) {
		axl4D = axl4d;
	}
	public int getAxl5D() {
		return axl5D;
	}
	public void setAxl5D(int axl5d) {
		axl5D = axl5d;
	}
	public int getAxl6D() {
		return axl6D;
	}
	public void setAxl6D(int axl6d) {
		axl6D = axl6d;
	}
	public int getAxl7D() {
		return axl7D;
	}
	public void setAxl7D(int axl7d) {
		axl7D = axl7d;
	}
	public int getAxl8D() {
		return axl8D;
	}
	public void setAxl8D(int axl8d) {
		axl8D = axl8d;
	}
	public int getAxl9D() {
		return axl9D;
	}
	public void setAxl9D(int axl9d) {
		axl9D = axl9d;
	}	

	public boolean isColor() {
		return color;
	}
	public void setColor(boolean color) {
		this.color = color;
	}
	WimData data = new WimData();
	private String silueta;

	public String getSilueta() {
		return silueta;
	}
	public void setSilueta(String silueta) {
		this.silueta = silueta;
	}
	private String[] pathImg;

	public String[] getPathImg() {
		return pathImg;
	}
	public void setPathImg(String[] pathImg) {
		this.pathImg = pathImg;
	}
	private String img1 = "", img2 = "";
	
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
	private List<SelectItem> minutos, horas, classes;
	
	public List<SelectItem> getMinutos() {
		return minutos;
	}
	public List<SelectItem> getHoras() {
		return horas;
	}
	public void setHoras(List<SelectItem> horas) {
		this.horas = horas;
	}
	public void setMinutos(List<SelectItem> minutos) {
		this.minutos = minutos;
	}
	
	public List<SelectItem> getClasses() {
		return classes;
	}
	public void setClasses(List<SelectItem> classes) {
		this.classes = classes;
	}
	
	@PostConstruct
	public void initalize(){
		
		// initalize wim realtime
		colorInitial();
		rate();
		updateView();
		
		try {
			
			dados();
							
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		silFolder = "C:\\Tracevia\\Software\\External\\Wim\\Silhuetas\\";
		
		vehiclesFolder = "C:\\Tracevia\\Software\\External\\Wim\\Veiculos\\";
		
		noImageFolder = "C:\\Tracevia\\Software\\External\\Unknown\\";
			
	    noImage = noImageFolder + "no-image.jpg";
		
		//img1 = getImagePath(noImage);
		//img2 = getImagePath(noImage);
		//silueta = getImagePath(noImage);
				
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
			
			dados();
			rate();
			siluetaRealtime();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestContext request = RequestContext.getCurrentInstance();
		request.execute("btnUpdateView();");
	}
	//mudar cor dos inputs
	public void colorInitial() {
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
	}
	public boolean colorInput() throws Exception {
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
	}
	//rate view wim.xhtml
	public void rate() {
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
	}
	//dados
	public void dados() {
		try {
			//Viewing vehicle information
			serialNumber = dao.serialNumber();
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
		type [2] = "";*/
		try {
			siluetaRealtime();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
		  
		  setSerialNumber(1);
		  setDataHour("2021-01-01 00:00:00");
		  setClasse("1");
		  setEixo("2");
		  setSpeed("60");
		  setPbtTotal(1000);
		  setSize("500");
		  
		  weight = new int[10];
		  weight [1] = 0;
		  weight [2] = 0;
		  weight [3] = 0;
		  weight [4] = 0;
		  weight [5] = 0;
		  weight [6] = 0;
		  weight [7] = 0;
		  weight [8] = 0;
		  weight [9] = 0;
		  
		 //distancia entre os eixos
		  dstAxes = new int[10];
		  dstAxes [2] = 0;
		  dstAxes [3] = 0;
		  dstAxes [4] = 0;
		  dstAxes [5] = 0;
		  dstAxes [6] = 0;
		  dstAxes [7] = 0;
		  dstAxes [8] = 0;
	      dstAxes [9] = 0;
		  
		  
	  }

}
