package br.com.tracevia.webapp.controller.colas;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;

import org.primefaces.context.RequestContext;

import br.com.tracevia.webapp.dao.colas.ColasDAO;
import br.com.tracevia.webapp.model.colas.ColasData;

@ManagedBean(name="ColasBean")
@ViewScoped
public class ColasBean{
	private List<SelectItem> minute, hour;
	private String dateInitial, dateFinal, hourInitial,
			hourFinal, minuteInitial, minuteFinal, camera;
	
	public String getCamera() {
		return camera;
	}

	public void setCamera(String camera) {
		this.camera = camera;
	}

	public String getDateInitial() {
		return dateInitial;
	}

	public void setDateInitial(String dateInitial) {
		this.dateInitial = dateInitial;
	}

	public String getDateFinal() {
		return dateFinal;
	}

	public void setDateFinal(String dateFinal) {
		this.dateFinal = dateFinal;
	}

	public String getHourInitial() {
		return hourInitial;
	}

	public void setHourInitial(String hourInitial) {
		this.hourInitial = hourInitial;
	}

	public String getHourFinal() {
		return hourFinal;
	}

	public void setHourFinal(String hourFinal) {
		this.hourFinal = hourFinal;
	}

	public String getMinuteInitial() {
		return minuteInitial;
	}

	public void setMinuteInitial(String minuteInitial) {
		this.minuteInitial = minuteInitial;
	}

	public String getMinuteFinal() {
		return minuteFinal;
	}

	public void setMinuteFinal(String minuteFinal) {
		this.minuteFinal = minuteFinal;
	}

	public List<SelectItem> getMinute() {
		return minute;
	}

	public void setMinute(List<SelectItem> minute) {
		this.minute = minute;
	}

	public List<SelectItem> getHour() {
		return hour;
	}

	public void setHour(List<SelectItem> hour) {
		this.hour = hour;
	}
	List<ColasData> listCam;
	
	public List<ColasData> getListCam() {
		return listCam;
	}

	public void setListCam(List<ColasData> listCam) {
		this.listCam = listCam;
	}
	List<ColasData> listData;
	
	public List<ColasData> getListData() {
		return listData;
	}

	public void setListData(List<ColasData> listData) {
		this.listData = listData;
	}
	private ColasDAO dao;
	@PostConstruct
	public void initialize() {
		minHour();//minuteHour
		camera();//filter cam
	}
	public void search() {
		
		RequestContext.getCurrentInstance().execute("getTr()");
		dao = new ColasDAO();
		//método de pesquisa
		if(camera == null) {
			//se o filtro da camera for igual a null acesso essa condição
		}else {
			//senão acesso essa condição aqui
		}
	}
	public void idGet(String id) {
		//metodo para pegar id da tabela
	}
	public void pdf() {
		//método pdf
	}
	public void camera() {
		
		dao = new ColasDAO();
		listCam = new ArrayList<ColasData>();
		try {
			listCam = dao.cameraGet();
			listData = dao.cameraGet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void minHour() {
		minute = new ArrayList<SelectItem>();
		hour = new ArrayList<SelectItem>();
		for(int x = 0; x < 24; x++) {
			if (x < 10)
				hour.add(new SelectItem("0"+String.valueOf(x), "0"+String.valueOf(x)));
			else 
				hour.add(new SelectItem(String.valueOf(x), String.valueOf(x)));
		}for(int m = 0; m < 60; m++){				
			if (m < 10)
				minute.add(new SelectItem("0"+String.valueOf(m), "0"+String.valueOf(m)));
			else 
				minute.add(new SelectItem(String.valueOf(m), String.valueOf(m)));
		}
	}
}