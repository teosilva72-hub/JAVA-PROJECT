package br.com.tracevia.webapp.controller.cftv;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import br.com.tracevia.webapp.dao.cftv.CFTVDAO;
import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.global.ModulesDAO;
import br.com.tracevia.webapp.model.cftv.CFTV;
import br.com.tracevia.webapp.model.global.Equipments;
@ManagedBean(name="CftvCam")
@ViewScoped
public class CftvCam {
	Equipments equip;
	private int id, idTotal, sumId;
	//GLOBAL VARIABLES
	private String[] credentials;
	private String cam, MoveUp, MoveDown, MoveLeft, km, presetCall="", presetSet, presetDetails, MoveRight, command, ZoomIn, ZoomOut, camCftv, imgControle = "";
	String callsArray[];
	private List<SelectItem> presetList;
	private CFTVDAO cftv;
	//getters and setters
	
	public String getCam() {
		return cam;
	}
	public int getSumId() {
		return sumId;
	}
	public void setSumId(int sumId) {
		this.sumId = sumId;
	}
	public int getIdTotal() {
		return idTotal;
	}
	public void setIdTotal(int idTotal) {
		this.idTotal = idTotal;
	}
	public String getPresetDetails() {
		return presetDetails;
	}
	public void setPresetDetails(String presetDetails) {
		this.presetDetails = presetDetails;
	}
	public String getPresetSet() {
		return presetSet;
	}
	public void setPresetSet(String presetSet) {
		this.presetSet = presetSet;
	}
	public List<SelectItem> getPresetList() {
		return presetList;
	}
	public void setPresetList(List<SelectItem> presetList) {
		this.presetList = presetList;
	}
	public String getPresetCall() {
		return presetCall;
	}
	public void setPresetCall(String presetCall) {
		this.presetCall = presetCall;
	}
	public String getKm() {
		return km;
	}
	public void setKm(String km) {
		this.km = km;
	}
	public String getImgControle() {
		try {
			Path path = Paths.get(imgControle);
			byte[] file = Files.readAllBytes(path);
			return Base64.getEncoder().encodeToString(file);
		} catch (IOException e) {
			return "";
		}
	}
	public void setImgControle(String imgControle) {
		this.imgControle = imgControle;
	}
	public String getCamCftv() {
		return camCftv;
	}
	public void setCamCftv(String camCftv) {
		this.camCftv = camCftv;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setCam(String cam) {
		this.cam = cam;
	}
	public String getMoveUp() {
		return MoveUp;
	}
	public void setMoveUp(String moveUp) {
		MoveUp = moveUp;
	}
	public String getMoveDown() {
		return MoveDown;
	}
	public void setMoveDown(String moveDown) {
		MoveDown = moveDown;
	}
	public String getMoveLeft() {
		return MoveLeft;
	}
	public void setMoveLeft(String moveLeft) {
		MoveLeft = moveLeft;
	}
	public String getMoveRight() {
		return MoveRight;
	}
	public void setMoveRight(String moveRight) {
		MoveRight = moveRight;
	}
	//Methods
	@PostConstruct
	public void initialize() {
		CFTVDAO ptz = new CFTVDAO();
		totalId();
		try {
			credentials();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String[] credentials() throws Exception {
			ModulesDAO mod = new ModulesDAO();
			credentials = mod.getCred("digifort");
		return credentials;
	}
	public int totalId() {
		CFTVDAO ptz = new CFTVDAO();
		try {
			equip = ptz.getTotalId();
			idTotal = equip.getEquip_id();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return idTotal;
	}
	public int searchCftv() {
		EquipmentsDAO search = new EquipmentsDAO();
		equip = new Equipments();
		presetCall = "";
		try {
			getCam(Integer.toString(id));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		presetList();
		try {
			equip = search.cftvCam(id);
			setKm(equip.getKm());
			imgControle = "C:\\Tracevia\\Software\\External\\Cftv\\controller\\controller.png";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	public void getCam(String ptz) throws IOException {
		String ext ="";
		if(id > 0 && id != 0) {
			if(id < 10)ext="0";
			camCftv = "https://"+credentials[3]+":"+credentials[4]+"/Interface/Cameras/GetJPEGStream?Camera=PTZ%20"+ext+ptz+"&Width=480&Height=320&Quality=20&FPS=30&ResponseFormat=Text&AuthUser="+credentials[1];
			URL url = new URL(camCftv);
			HttpURLConnection http = (HttpURLConnection)url.openConnection();
			http.disconnect();
		}
	}
	public void presetList() {
		presetList = new  ArrayList<SelectItem>();
		for(int m = 0; m <= 300; m++){				 
			presetList.add(new SelectItem(String.valueOf(m), String.valueOf(m)));
		}
	}
	public int callPreset() {
		try {
			presetCall(Integer.toString(id));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	public String presetCall(String id) throws IOException {
		if(presetCall == "") {
			RequestContext.getCurrentInstance().execute("validatePresetCall()");
			RequestContext.getCurrentInstance().execute("presetCftv()");
			//return presetCall = "teste";
		}
		else {
			int z = 0, o = 0, t = 0;
			String zr = "", on = "", tw= "";
			callsArray = presetCall.split("");
			if(callsArray.length == 1) {
				if(!callsArray[0].equals("0")) {
					z = Integer.parseInt(callsArray[0]) - 1;
					presetCall = String.valueOf(z);
				}
			}else if(callsArray.length == 2) {
				if(!callsArray[1].equals("0")) {
					o = Integer.parseInt(callsArray[1]) - 1;
					presetCall = callsArray[0]+ String.valueOf(o);
				}
			}else if(callsArray.length == 3) {
				if(!callsArray[2].equals("0")) {
					t = Integer.parseInt(callsArray[2]) - 1;
					presetCall = callsArray[0]+ callsArray[1]+ String.valueOf(t);
				}
			}else {
				RequestContext.getCurrentInstance().execute("msgError()");
				return presetCall = "";
			}
			RequestContext.getCurrentInstance().execute("validatePresetCall()");
			RequestContext.getCurrentInstance().execute("presetCftv()");
			String ext ="";
			if(Integer.parseInt(id) >= 0) {
				if(Integer.parseInt(id) < 10)ext="0";
				String call = "https://"+credentials[3]+":"+credentials[4]+"/Interface/Cameras/PTZ/CallPreset?Camera=PTZ%20"+ext+id+"&Value="+presetCall+"&ResponseFormat=XML&AuthUser="+credentials[1];
				URL url = new URL(call);
				HttpURLConnection http = (HttpURLConnection)url.openConnection();
				http.disconnect();
				//presetCall = "";
			}
		}
		return presetCall;
	}
	public int setPreset() {
		try {
			presetSet(Integer.toString(id));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	public void presetSet(String id) throws IOException {
		if(presetSet == "" || presetDetails == "")RequestContext.getCurrentInstance().execute("validatePresetSet()");
		else{
			RequestContext.getCurrentInstance().execute("validatePresetSet()");
			String ext ="";
			if(Integer.parseInt(id) > 0) {
				if(Integer.parseInt(id) < 10)ext="0";
				String call = "https://10.14.110.83:8602/Interface/Cameras/PTZ/SetPreset?Camera=PTZ%20"+ext+id+"&Value="+presetSet+"&Description="+presetDetails+"&ResponseFormat=XML&AuthUser=admin";
				URL url = new URL(call);
				HttpURLConnection http = (HttpURLConnection)url.openConnection();
				http.disconnect();
			}
		}
	}
	public void UpMove() {
		try {
			moveUp(Integer.toString(id));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void moveUp(String cam) throws IOException {
		String zero = "";
		if(Integer.parseInt(cam) < 10)zero = "0";
		MoveUp = "https://"+credentials[3]+":"+credentials[4]+"/Interface/Cameras/PTZ/Simple?Camera=PTZ%20"+zero+cam+"&Operation=MoveUp&ResponseFormat=XML&AuthUser="+credentials[1];
		URL url = new URL(MoveUp);
		HttpURLConnection http = (HttpURLConnection)url.openConnection();
		http.disconnect();
	}
	public void downMove() {
		try {
			moveDown(Integer.toString(id));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void moveDown(String cam) throws IOException {
		String zero = "";
		if(Integer.parseInt(cam) < 10)zero = "0";
		MoveDown = "https://"+credentials[3]+":"+credentials[4]+"/Interface/Cameras/PTZ/Simple?Camera=PTZ%20"+zero+cam+"&Operation=MoveDown&ResponseFormat=XML&AuthUser="+credentials[1];
		URL url = new URL(MoveDown);
		HttpURLConnection http = (HttpURLConnection)url.openConnection();
		http.disconnect();
	}
	public void leftMove() {
		try {
			moveLeft(Integer.toString(id));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void moveLeft(String cam) throws IOException {
		String zero = "";
		if(Integer.parseInt(cam) < 10)zero = "0";
		MoveLeft = "https://"+credentials[3]+":"+credentials[4]+"/Interface/Cameras/PTZ/Simple?Camera=PTZ%20"+zero+cam+"&Operation=MoveLeft&ResponseFormat=XML&AuthUser="+credentials[1];
		URL url = new URL(MoveLeft);
		HttpURLConnection http = (HttpURLConnection)url.openConnection();
		http.disconnect();
	}
	public void rightMove() {
		try {
			moveRight(Integer.toString(id));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void moveRight(String cam) throws IOException {
		String zero = "";
		if(Integer.parseInt(cam) < 10)zero = "0";
		MoveRight = "https://"+credentials[3]+":"+credentials[4]+"/Interface/Cameras/PTZ/Simple?Camera=PTZ%20"+zero+cam+"&Operation=MoveRight&ResponseFormat=XML&AuthUser="+credentials[1];
		URL url = new URL(MoveRight);
		HttpURLConnection http = (HttpURLConnection)url.openConnection();
		http.disconnect();
	}
	public void zoomInMove() {
		try {
			MoveZoomIn(Integer.toString(id));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void MoveZoomIn(String cam) throws IOException {
		String zero = "";
		if(Integer.parseInt(cam) < 10)zero = "0";
		ZoomIn = "https://"+credentials[3]+":"+credentials[4]+"/Interface/Cameras/PTZ/Simple?Camera=PTZ%20"+zero+cam+"&Operation=ZoomTele&ResponseFormat=XML&AuthUser="+credentials[1];
		URL url = new URL(ZoomIn);
		HttpURLConnection http = (HttpURLConnection)url.openConnection();
		http.disconnect();
	}
	public void zoomOutMove() {
		try {
			MoveZoomOut(Integer.toString(id));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void MoveZoomOut(String cam) throws IOException {
		String zero = "";
		if(Integer.parseInt(cam) < 10)zero = "0";
		ZoomOut = "https://"+credentials[3]+":"+credentials[4]+"/Interface/Cameras/PTZ/Simple?Camera=PTZ%20"+zero+cam+"&Operation=ZoomWide&ResponseFormat=XML&AuthUser="+credentials[1];
		URL url = new URL(ZoomOut);
		HttpURLConnection http = (HttpURLConnection)url.openConnection();
		http.disconnect();
	}
}