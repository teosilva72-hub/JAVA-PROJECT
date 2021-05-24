package br.com.tracevia.webapp.controller.wimController;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import br.com.tracevia.webapp.controller.occ.OccurrencesBean;
import br.com.tracevia.webapp.dao.wim.WIMDAO;
import br.com.tracevia.webapp.model.occ.OccurrencesData;
import br.com.tracevia.webapp.model.wim.WimData;

@ManagedBean(name="wimController")
@ViewScoped
public class wimController {
	private int[] axes;

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
	private int rate;

	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	private String rateTxt;

	public String getRateTxt() {
		return rateTxt;
	}
	public void setRateTxt(String rateTxt) {
		this.rateTxt = rateTxt;
	}
	private String[] listarFile;

	public String[] getListarFile() {
		return listarFile;
	}
	public void setListarFile(String[] listarFile) {
		this.listarFile = listarFile;
	}
	private int serialNumber;


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
	int pbtTotal;
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
	private int axl1W, axl2W, axl3W, axl4W, axl5W,
	axl6W, axl7W, axl8W, axl9W, axl1D, axl2D, axl3D,
	axl4D, axl5D,
	axl6D, axl7D, axl8D, axl9D;

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
	private WIMDAO dao = new WIMDAO();
	private boolean color;

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
	@PostConstruct
	public void initalize(){
		colorInitial();
		updateView();
		rate();
		updateView();
		try {
			dados();
			siluetaRealtime();
			listFiles();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public String[] listFiles() throws Exception{
		int content = 0;
		File arquivos[];
		//local do armazenamento do arquivo
		File directory = new File("C:\\teste\\cars\\");
		//listar arquivos
		arquivos = directory.listFiles();
		listarFile = new String[arquivos.length];
		//lopping para pegar arquivos dentro do diretorio
		while (content != arquivos.length){
			//pega arquivo pelo nome
			listarFile[content] = arquivos[content].getName();
			content++;
		} 
		String imagePath = "http://localhost:8081/teste/cars/";
		pathImg = new String [2];
		pathImg[0] = imagePath+listarFile[0];
		pathImg[1] = imagePath+listarFile[1];
		//passando valor do método para a variável
		return listarFile;
	}
	public String siluetaRealtime() throws Exception{
		String classe = dao.classe();
		if(classe != "") {
			if(classe.equals("2")) {
				String img = "10.png";
				silueta = "http://localhost:8081/teste/sil/" + img;
			}else if(classe.equals("E5")) {
				String img = "1.png";
				silueta = "http://localhost:8081/teste/sil/" + img;
			}else if(classe.equals("E2")) {
				String img = "2.png";
				silueta = "http://localhost:8081/teste/sil/" + img;
			}else if(classe.equals("E3")) {
				String img = "3.png";
				silueta = "http://localhost:8081/teste/sil/" + img;
			}else if(classe.equals("E4")) {
				String img = "4.png";
				silueta = "http://localhost:8081/teste/sil/" + img;
			}else if(classe.equals("E5")) {
				String img = "5.png";
				silueta = "http://localhost:8081/teste/sil/" + img;
			}else if(classe.equals("E6")) {
				String img = "6.png";
				silueta = "http://localhost:8081/teste/sil/" + img;
			}else if(classe.equals("E9")) {
				String img = "9.png";
				silueta = "http://localhost:8081/teste/sil/" + img;
			}
		}
		return silueta;
	}
	//update View wim_Realtime
	public void updateView() {
		try {
			
			TimeUnit.SECONDS.sleep(1);
			
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			serialNumber = dao.serialNumber();
			dados();
			rate();
			listFiles();
			siluetaRealtime();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestContext request = RequestContext.getCurrentInstance();
		request.execute("btnUpdateView();");
		System.out.println("atualizando");
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
			pbtTotal = Integer.parseInt(dao.pbtTotal());
			RequestContext request = RequestContext.getCurrentInstance();
			rate = pbtTotal; /**/
			if(rate <= 1500) {
				request.execute("sizeNormal();");
				rateTxt = "Normal weight " + rate;
			}else if(rate > 1500 && rate <= 2500) {
				request.execute("sizeAtenttion();");
				rateTxt = "In tolerance " + rate;
			}else {
				request.execute("sizeAcima();");
				rateTxt = "overweight " + rate;
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
	}

}
