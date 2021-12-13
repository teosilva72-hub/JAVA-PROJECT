package br.com.tracevia.webapp.controller.global;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.cfg.RoadConcessionairesEnum;
import br.com.tracevia.webapp.dao.global.RoadConcessionaireDAO;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.util.SessionUtil;
import br.com.tracevia.webapp.model.global.Estrada;

@ManagedBean(name="roadView")
@ViewScoped
public class EstradaObjectController {
	
	public List<Plaque> plaque;
	public List<int[]> roadLine;
	public List<String[]> cars;
	
	private Estrada road_01;
	private Estrada road_02;
	private Estrada road_03;
	private Estrada road_04;
	private Estrada road_05;
	private Estrada road_06;
	private Estrada road_07;
	private Estrada road_08;
	private Estrada road_09;
	private Estrada road_10;
	
	private boolean en_road01;
	private boolean en_road02;
	private boolean en_road03;
	private boolean en_road04;
	private boolean en_road05;
	private boolean en_road06;
	private boolean en_road07;
	private boolean en_road08;
	private boolean en_road09;
	private boolean en_road10;
	
	public Estrada getRoad_01() {
		return road_01;
	}
	public Estrada getRoad_02() {
		return road_02;
	}
	public Estrada getRoad_03() {
		return road_03;
	}
	public Estrada getRoad_04() {
		return road_04;
	}
	public Estrada getRoad_05() {
		return road_05;
	}
	public Estrada getRoad_06() {
		return road_06;
	}
	public Estrada getRoad_07() {
		return road_07;
	}
	public Estrada getRoad_08() {
		return road_08;
	}
	public Estrada getRoad_09() {
		return road_09;
	}
	public Estrada getRoad_10() {
		return road_10;
	}
	public boolean isEn_road01() {
		return en_road01;
	}
	public boolean isEn_road02() {
		return en_road02;
	}
	public boolean isEn_road03() {
		return en_road03;
	}
	public boolean isEn_road04() {
		return en_road04;
	}
	public boolean isEn_road05() {
		return en_road05;
	}
	public boolean isEn_road06() {
		return en_road06;
	}
	public boolean isEn_road07() {
		return en_road07;
	}
	public boolean isEn_road08() {
		return en_road08;
	}
	public boolean isEn_road09() {
		return en_road09;
	}
	public boolean isEn_road10() {
		return en_road10;
	}

	@PostConstruct
	public void initialize() {
		
		RoadConcessionaireDAO roadDAO = new RoadConcessionaireDAO();
		try {
			plaque = roadDAO.getPlaque();
			roadLine = roadDAO.getRoadLine();
			cars = roadDAO.getCarsList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/* Inst�ncia de Estradas sem valores aparentes.
		Dependendo da concess�o o n�mero de objetos Estradas pode variar.
		Valores podem ser atribuidos posteriormente */
		
		road_01 = new Estrada();
		road_02 = new Estrada();
		road_03 = new Estrada();
		road_04 = new Estrada();
		road_05 = new Estrada();
		road_06 = new Estrada();
		road_07 = new Estrada();
		road_08 = new Estrada();
		road_09 = new Estrada();
		road_10 = new Estrada();
		
		//Habilitar visualiza��o no Real Time
		
		en_road01 = false;
		en_road02 = false;
		en_road03 = false;
		en_road04 = false;
		en_road05 = false;
		en_road06 = false;
		en_road07 = false;
		en_road08 = false;
		en_road09 = false;
		en_road10 = false;
		
		buildHighwaySystem(RoadConcessionaire.roadConcessionaire);
		
	}
	
	public List<Plaque> getPlaque() {
		return plaque;
	}
	
	public List<int[]> getRoadLine() {
		return this.roadLine;
	}
	
	public List<String[]> getCars() {
		return this.cars;
	}

	public void setRoadLine(List<int[]> roadLine) {
		this.roadLine = roadLine;
	}

	public EstradaObjectController() {}
	
	
	public void buildCardelPozaRica() {
				
		//Instanciar Estradas
		buildHighwaySystem(RoadConcessionaire.roadConcessionaire);			
	}
	
	public void buildHighwaySystem(String name) {
		
		if(name.equals(RoadConcessionairesEnum.Tracevia.getConcessionaire())) {
			buildCardelPozaRica(name);
		}
		
		
	}
	
	public void buildTracevia(String concessionaire) {
		
		 en_road01 = true;
		 en_road02 = true;
		
		 road_01 = new Estrada("trcv01", 0, 50, 1550, 70, "TRCV001", "horizontal", concessionaire);
		 road_02 = new Estrada("trcv02", 0, 50, 1550, 70, "TRCV002", "horizontal", concessionaire);			
		
	}

	public void buildCardelPozaRica(String concessionaire) {
				
		 en_road01 = true;
		 en_road02 = true;
		 en_road03 = true;
		 en_road04 = true;
		 en_road05 = false;
		 en_road06 = false;
				
		 road_01 = new Estrada("Cardel01", 0, 50, 1000, 70, "CPR001", "horizontal", concessionaire);				
		 road_02 = new Estrada("Cardel02", 0, 50, 500, 70, "CPR002", "vertical", concessionaire);				
		 //road_03 = new Estrada("Cardel03", 0, 50, 550, 70, "CPR003", "vertical", concessionaire);				
		 //road_04 = new Estrada("Cardel04", 0, 50, 750, 70, "CPR004", "vertical", concessionaire);				
		// road_05 = new Estrada("Cardel05", 0, 50, 750, 70, "CPR005", "vertical", concessionaire);				
		// road_06 = new Estrada("Cardel06", 0, 50, 750, 70, "CPR006", "vertical", concessionaire);				
				
	}

	public class Plaque {
		int km;
		double longitude;
		double latitude;
		int mapY;
		int linear_posX;
		int linear_posY;

		public Plaque() {}

		public int getKm() {
			return this.km;
		}

		public void setKm(int km) {
			this.km = km;
		}

		public double getLongitude() {
			return this.longitude;
		}

		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}

		public double getLatitude() {
			return this.latitude;
		}

		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}

		public int getMapY() {
			return this.mapY;
		}

		public void setMapY(int mapY) {
			this.mapY = mapY;
		}

		public int getLinear_posX() {
			return this.linear_posX;
		}

		public void setLinear_posX(int linear_posX) {
			this.linear_posX = linear_posX;
		}

		public int getLinear_posY() {
			return this.linear_posY;
		}

		public void setLinear_posY(int linear_posY) {
			this.linear_posY = linear_posY;
		}
	}

	public void saveCarIMG() throws IOException {
		Map<String, String> map = SessionUtil.getRequestParameterMap();
		RoadConcessionaireDAO roadDAO = new RoadConcessionaireDAO();

		String id = map.get("id_car");
		String type = map.get("type_car");

		roadDAO.saveCarIMG(id, type);
		cars = roadDAO.getCarsList();
	}
}
