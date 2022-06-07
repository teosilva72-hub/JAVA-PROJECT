package br.com.tracevia.webapp.controller.cftv;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import br.com.tracevia.webapp.controller.global.LoginAccountBean;
import br.com.tracevia.webapp.dao.cftv.CFTVDAO;
import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.global.ModulesDAO;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.util.ImageUtil;

@ManagedBean(name="CftvCam")
@ViewScoped
public class CftvCam {	
	
	Equipments equip;	
	private int id, idTotal, sumId;
	//GLOBAL VARIABLES
	private String cam, MoveUp, MoveDown, MoveLeft, km, presetCall="", presetSet, presetDetails, MoveRight, /*command,*/ camCftv, imgControle;
	String callsArray[];
	private List<SelectItem> presetList;
	//private CFTVDAO cftv;
	private String[] credentials, patrolPreset;
	//getters and setters
	
	String cftvControlImage = "controller.png";
	
	@ManagedProperty("#{loginAccount}")
	private LoginAccountBean login;

	public LoginAccountBean getLogin() {
		return login;
	}

	public void setLogin(LoginAccountBean login) {
		this.login = login;
	}
	
	public String getCam() {
		return cam;
	}	
	public String[] getPatrolPreset() {
		return patrolPreset;
	}	
	public void setPatrolPreset(String[] patrolPreset) {
		this.patrolPreset = patrolPreset;
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
		return imgControle;
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
	
	
	@PostConstruct
	public void initialize() {
		
		if(login.getLoad().isEn_cftv()) {
				
			totalId();
			
				try {
					credentials();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
		}
	}
	
	public String[] presetPatrol() {
		//System.out.println("Estamos aqui");
		RequestContext.getCurrentInstance().execute("btnPatrol()");
		//System.out.println(patrolPreset.length);	
		
		return patrolPreset;
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
		presetList();
		try {
			equip = search.cftvCam(id);
			setKm(equip.getKm());
			getCam(Integer.toString(id), equip.getNome());
			String control = ImageUtil.getInternalImagePath("images", "cftv", cftvControlImage);
			setImgControle(ImageUtil.encodeToBase64(control));		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	public String[] credentials() throws Exception {
		
		ModulesDAO mod = new ModulesDAO();
		credentials = mod.getCred("digifort");
	
		return credentials;
	}
	
	public void getCam(String ptz, String name) throws IOException {
		String ext ="";
		name = name.replaceAll(" ","%20");
		System.out.println(name);
		
		if(id > 0 && id != 0) {
			if(id < 10)ext="0";
			camCftv = credentials[5]+"://"+credentials[3]+":"+credentials[4]+"/Interface/Cameras/GetJPEGStream?Camera="+name+"&Width=480&Height=320&Quality=20&FPS=30&ResponseFormat=Text&AuthUser="+credentials[1];
			URL url = new URL(camCftv);
			HttpURLConnection http = (HttpURLConnection)url.openConnection();
			http.disconnect();
			//System.out.println(camCftv);
			//System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
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
			presetCall(Integer.toString(id), data(id).getNome());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	
	public String presetCall(String id, String name) throws IOException {
		name = name.replaceAll(" ", "%20");
		if(presetCall == "") {
			RequestContext.getCurrentInstance().execute("validatePresetCall()");
			RequestContext.getCurrentInstance().execute("presetCftv()");
			//return presetCall = "teste";
		}
		
		else {
			int z = 0, o = 0, t = 0;
			//String zr = "", on = "", tw= "";
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
				String call = credentials[5]+"://"+credentials[3]+":"+credentials[4]+"/Interface/Cameras/PTZ/CallPreset?Camera="+name+"&Value="+presetCall+"&ResponseFormat=XML&AuthUser=admin";
				URL url = new URL(call);
				HttpURLConnection http = (HttpURLConnection)url.openConnection();
				http.disconnect();
				System.out.println(call);
				//System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
			}
		}
		return presetCall;
	}
	public Equipments data(int id) {
		EquipmentsDAO search = new EquipmentsDAO();
		equip = search.cftvCam(id);
		return equip;
	}
	public int setPreset() {
		try {
			
			presetSet(Integer.toString(id),data(id).getNome());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	
	public void presetSet(String id, String name) throws IOException {
		name = name.replaceAll(" ", "%20");
		if(presetSet == "" || presetDetails == "")RequestContext.getCurrentInstance().execute("validatePresetSet()");
		else{
			RequestContext.getCurrentInstance().execute("validatePresetSet()");
			String ext ="";
			if(Integer.parseInt(id) > 0) {
				if(Integer.parseInt(id) < 10)ext="0";
				String call = credentials[5]+"://"+credentials[3]+":"+credentials[4]+"/Interface/Cameras/PTZ/SetPreset?Camera="+name+"&Value="+presetSet+"&Description="+presetDetails+"&ResponseFormat=XML&AuthUser=admin";
				URL url = new URL(call);
				HttpURLConnection http = (HttpURLConnection)url.openConnection();
				http.disconnect();
				System.out.println(call);
				//System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
			}
		}
	}
	
	public void UpMove() {
		try {
			Command(Integer.toString(id), data(id).getNome(), "MoveUp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void downMove() {
		try {
			Command(Integer.toString(id), data(id).getNome(), "MoveDown");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void leftMove() {
		try {
			Command(Integer.toString(id), data(id).getNome(), "MoveLeft");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void rightMove() {
		try {
			Command(Integer.toString(id), data(id).getNome(), "MoveRight");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void zoomInMove() {
		try {
			Command(Integer.toString(id), data(id).getNome(), "ZoomTele");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void zoomOutMove() {
		try {
			Command(Integer.toString(id), data(id).getNome(), "ZoomWide");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Command(String cam, String name, String c) throws MalformedURLException {
		//String zero = "";
		name = name.replaceAll(" ", "%20");
		//if(Integer.parseInt(cam) < 10)zero = "0";
		String command = credentials[5]+"://"+credentials[3]+":"+credentials[4]+"/Interface/Cameras/PTZ/Simple?Camera="+name+"&Operation="+c+"&ResponseFormat=XML&AuthUser=admin";
		URL url = new URL(command);
		HttpURLConnection http = null;
		try {
			http = (HttpURLConnection)url.openConnection();
			http.connect();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (http != null)
				http.disconnect();			
		}
		System.out.println(command);
		//System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
	}
}