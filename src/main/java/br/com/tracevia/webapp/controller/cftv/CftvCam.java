package br.com.tracevia.webapp.controller.cftv;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.model.global.Equipments;
@ManagedBean(name="CftvCam")
@ViewScoped
public class CftvCam {
	Equipments equip;
	private int id;
	//VARIABLES LINK´S IMAGE CAM
	private String cftv01 = "http://10.14.110.83:8601/Interface/Cameras/GetJPEGStream?Camera=PTZ%2001&Width=480&Height=320&Quality=20&FPS=30&ResponseFormat=Text&AuthUser=admin";
	private String cftv02 = "http://10.14.110.83:8601/Interface/Cameras/GetJPEGStream?Camera=PTZ%2002&Width=480&Height=320&Quality=20&FPS=30&ResponseFormat=Text&AuthUser=admin"; 
	private String cftv03 = "http://10.14.110.83:8601/Interface/Cameras/GetJPEGStream?Camera=PTZ%2003&Width=480&Height=320&Quality=20&FPS=30&ResponseFormat=Text&AuthUser=admin";
	private String cftv04 = "http://10.14.110.83:8601/Interface/Cameras/GetJPEGStream?Camera=PTZ%2004&Width=480&Height=320&Quality=20&FPS=30&ResponseFormat=Text&AuthUser=admin";
	private String cftv05 = "http://10.14.110.83:8601/Interface/Cameras/GetJPEGStream?Camera=PTZ%2005&Width=480&Height=320&Quality=20&FPS=30&ResponseFormat=Text&AuthUser=admin";
	private String cftv06 = "http://10.14.110.83:8601/Interface/Cameras/GetJPEGStream?Camera=PTZ%2006&Width=480&Height=320&Quality=20&FPS=30&ResponseFormat=Text&AuthUser=admin";
	private String cftv07 = "http://10.14.110.83:8601/Interface/Cameras/GetJPEGStream?Camera=PTZ%2007&Width=480&Height=320&Quality=20&FPS=30&ResponseFormat=Text&AuthUser=admin";
	private String cftv08 = "http://10.14.110.83:8601/Interface/Cameras/GetJPEGStream?Camera=PTZ%2008&Width=480&Height=320&Quality=20&FPS=30&ResponseFormat=Text&AuthUser=admin";
	private String cftv09 = "http://10.14.110.83:8601/Interface/Cameras/GetJPEGStream?Camera=PTZ%2009&Width=480&Height=320&Quality=20&FPS=30&ResponseFormat=Text&AuthUser=admin";
	private String cftv10 = "http://10.14.110.83:8601/Interface/Cameras/GetJPEGStream?Camera=PTZ%2010&Width=480&Height=320&Quality=20&FPS=30&ResponseFormat=Text&AuthUser=admin";
	private String cftv11 = "http://10.14.110.83:8601/Interface/Cameras/GetJPEGStream?Camera=PTZ%2011&Width=480&Height=320&Quality=20&FPS=30&ResponseFormat=Text&AuthUser=admin";
	private String cftv12 = "http://10.14.110.83:8601/Interface/Cameras/GetJPEGStream?Camera=PTZ%2012&Width=480&Height=320&Quality=20&FPS=30&ResponseFormat=Text&AuthUser=admin";
	private String cftv13 = "http://10.14.110.83:8601/Interface/Cameras/GetJPEGStream?Camera=PTZ%2013&Width=480&Height=320&Quality=20&FPS=30&ResponseFormat=Text&AuthUser=admin";
	private String cftv14 = "http://10.14.110.83:8601/Interface/Cameras/GetJPEGStream?Camera=PTZ%2014&Width=480&Height=320&Quality=20&FPS=30&ResponseFormat=Text&AuthUser=admin";
	private String cftv15 = "http://10.14.110.83:8601/Interface/Cameras/GetJPEGStream?Camera=PTZ%2015&Width=480&Height=320&Quality=20&FPS=30&ResponseFormat=Text&AuthUser=admin";
	private String cftv16 = "http://10.14.110.83:8601/Interface/Cameras/GetJPEGStream?Camera=PTZ%2016&Width=480&Height=320&Quality=20&FPS=30&ResponseFormat=Text&AuthUser=admin";
	//GLOBAL VARIABLES
	private String cam, MoveUp, MoveDown, MoveLeft, km,
	MoveRight, command, ZoomIn, ZoomOut, camCftv, imgControle = "";
	//getters and setters
	
	public String getCam() {
		return cam;
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
	public void searchCftv() {

		EquipmentsDAO search = new EquipmentsDAO();
		equip = new Equipments();
		getCam(id);
		try {
			equip = search.cftvCam(id);
			setKm(equip.getKm());
			RequestContext.getCurrentInstance().execute("dblModalHidden()");
			imgControle = "C:\\Tracevia\\Software\\External\\Cftv\\controller\\controller.png";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return equip;
	}
	public void getCam(int id) {
		if(id > 0 && id != 0) {
			if(id == 1)camCftv = cftv01;
			else if(id == 2)camCftv = cftv02;
			else if(id == 3)camCftv = cftv03;
			else if(id == 4)camCftv = cftv04;
			else if(id == 5)camCftv = cftv05;
			else if(id == 6)camCftv = cftv06;
			else if(id == 7)camCftv = cftv07;
			else if(id == 8)camCftv = cftv08;
			else if(id == 9)camCftv = cftv09;
			else if(id == 10)camCftv = cftv10;
			else if(id == 11)camCftv = cftv11;
			else if(id == 12)camCftv = cftv12;
			else if(id == 13)camCftv = cftv13;
			else if(id == 14)camCftv = cftv14;
			else if(id == 15)camCftv = cftv15;
			else if(id == 16)camCftv = cftv16;
		}
		System.out.println(camCftv+ "testando aqui");
	}
	public void UpMove() {
		try {
			System.out.println("pra cima");
			moveUp(Integer.toString(id));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void moveUp(String cam) throws IOException {
		String zero = "";
		if(Integer.parseInt(cam) < 10)zero = "0";
		MoveUp = "http://10.14.110.83:8601/Interface/Cameras/PTZ/Simple?Camera=PTZ%20"+zero+cam+"&Operation=MoveUp&ResponseFormat=XML&AuthUser=admin";
		System.out.println(MoveUp);
		URL url = new URL(MoveUp);
		HttpURLConnection http = (HttpURLConnection)url.openConnection();
		System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
		http.disconnect();
	}
	public void downMove() {
		try {
			moveDown(Integer.toString(id));
			System.out.println("Pra baixo");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void moveDown(String cam) throws IOException {
		String zero = "";
		if(Integer.parseInt(cam) < 10)zero = "0";
		MoveDown = "http://10.14.110.83:8601/Interface/Cameras/PTZ/Simple?Camera=PTZ%20"+zero+cam+"&Operation=MoveDown&ResponseFormat=XML&AuthUser=admin";
		URL url = new URL(MoveDown);
		System.out.println(MoveDown);
		HttpURLConnection http = (HttpURLConnection)url.openConnection();
		System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
		http.disconnect();
	}
	public void leftMove() {
		try {
			System.out.println("Pra esquerda");
			moveLeft(Integer.toString(id));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void moveLeft(String cam) throws IOException {
		String zero = "";
		if(Integer.parseInt(cam) < 10)zero = "0";
		MoveLeft = "http://10.14.110.83:8601/Interface/Cameras/PTZ/Simple?Camera=PTZ%20"+zero+cam+"&Operation=MoveLeft&ResponseFormat=XML&AuthUser=admin";
		URL url = new URL(MoveLeft);
		System.out.println(MoveLeft);
		HttpURLConnection http = (HttpURLConnection)url.openConnection();
		System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
		http.disconnect();
	}
	public void rightMove() {
		try {
			System.out.println("Pra direita");
			moveRight(Integer.toString(id));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void moveRight(String cam) throws IOException {
		String zero = "";
		if(Integer.parseInt(cam) < 10)zero = "0";
		MoveRight = "http://10.14.110.83:8601/Interface/Cameras/PTZ/Simple?Camera=PTZ%20"+zero+cam+"&Operation=MoveRight&ResponseFormat=XML&AuthUser=admin";
		URL url = new URL(MoveRight);
		System.out.println(MoveRight);
		HttpURLConnection http = (HttpURLConnection)url.openConnection();
		System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
		http.disconnect();
	}
	public void zoomInMove() {
		try {
			System.out.println("zoom +");
			MoveZoomIn(Integer.toString(id));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void MoveZoomIn(String cam) throws IOException {
		String zero = "";
		if(Integer.parseInt(cam) < 10)zero = "0";
		ZoomIn = "http://10.14.110.83:8601/Interface/Cameras/PTZ/Simple?Camera=PTZ%20"+zero+cam+"&Operation=ZoomTele&ResponseFormat=XML&AuthUser=admin";
		URL url = new URL(ZoomIn);
		System.out.println(ZoomIn);
		HttpURLConnection http = (HttpURLConnection)url.openConnection();
		System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
		http.disconnect();
	}
	public void zoomOutMove() {
		try {
			System.out.println("zoom -");
			MoveZoomOut(Integer.toString(id));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void MoveZoomOut(String cam) throws IOException {
		String zero = "";
		if(Integer.parseInt(cam) < 10)zero = "0";
		ZoomOut = "http://10.14.110.83:8601/Interface/Cameras/PTZ/Simple?Camera=PTZ%20"+zero+cam+"&Operation=ZoomWide&ResponseFormat=XML&AuthUser=admin";
		URL url = new URL(ZoomOut);
		System.out.println(ZoomOut);
		HttpURLConnection http = (HttpURLConnection)url.openConnection();
		System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
		http.disconnect();
	}
	public void xxx() {
		//H264Decoder decoder = new H264Decoder();
	}
}
