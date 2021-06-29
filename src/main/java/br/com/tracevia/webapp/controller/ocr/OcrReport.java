package br.com.tracevia.webapp.controller.ocr;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import br.com.tracevia.webapp.dao.ocr.OcrDao;
import br.com.tracevia.webapp.model.ocr.OcrData;

@ViewScoped
@ManagedBean(name="OcrReport")
public class OcrReport{
	private OcrData data = new OcrData();
	private OcrData date;
	private String dtStart, hrStart, minStart, dtFinal, hrFinal, minFinal, camera; 
	private List<SelectItem> minutos, horas, classes;
	private List<OcrData> list;
	private int rowkey;
	private boolean selectedRow;
	private OcrDao dao = new OcrDao();
	
	public OcrData getData() {
		return data;
	}

	
	public String getCamera() {
		return camera;
	}


	public void setCamera(String camera) {
		this.camera = camera;
	}


	public String getDtStart() {
		return dtStart;
	}


	public void setDtStart(String dtStart) {
		this.dtStart = dtStart;
	}


	public String getHrStart() {
		return hrStart;
	}


	public void setHrStart(String hrStart) {
		this.hrStart = hrStart;
	}


	public String getMinStart() {
		return minStart;
	}


	public void setMinStart(String minStart) {
		this.minStart = minStart;
	}


	public String getDtFinal() {
		return dtFinal;
	}


	public void setDtFinal(String dtFinal) {
		this.dtFinal = dtFinal;
	}


	public String getHrFinal() {
		return hrFinal;
	}


	public void setHrFinal(String hrFinal) {
		this.hrFinal = hrFinal;
	}


	public String getMinFinal() {
		return minFinal;
	}


	public void setMinFinal(String minFinal) {
		this.minFinal = minFinal;
	}


	public void setData(OcrData data) {
		this.data = data;
	}


	public OcrData getDate() {
		return date;
	}


	public void setDate(OcrData date) {
		this.date = date;
	}


	public List<SelectItem> getMinutos() {
		return minutos;
	}


	public void setMinutos(List<SelectItem> minutos) {
		this.minutos = minutos;
	}


	public List<SelectItem> getHoras() {
		return horas;
	}


	public void setHoras(List<SelectItem> horas) {
		this.horas = horas;
	}


	public List<SelectItem> getClasses() {
		return classes;
	}


	public void setClasses(List<SelectItem> classes) {
		this.classes = classes;
	}


	public List<OcrData> getList() {
		return list;
	}


	public void setList(List<OcrData> list) {
		this.list = list;
	}


	public int getRowkey() {
		return rowkey;
	}


	public void setRowkey(int rowkey) {
		this.rowkey = rowkey;
	}


	public boolean isSelectedRow() {
		return selectedRow;
	}


	public void setSelectedRow(boolean selectedRow) {
		this.selectedRow = selectedRow;
	}


	@PostConstruct
	public void initialize() {
		RequestContext.getCurrentInstance().execute("getTr()");
		horas = new ArrayList<SelectItem>();
		minutos = new ArrayList<SelectItem>();
		classes = new ArrayList<SelectItem>();
		classes.add(new SelectItem("Todos"));
		classes.add(new SelectItem("ocr_1"));
		classes.add(new SelectItem("ocr_2"));
		classes.add(new SelectItem("ocr_3"));
		classes.add(new SelectItem("ocr_4"));
		classes.add(new SelectItem("ocr_5"));
		classes.add(new SelectItem("ocr_6"));
		for(int x = 0; x < 24; x++) {
			if (x < 10)
				horas.add(new SelectItem("0"+String.valueOf(x), "0"+String.valueOf(x)));
			else 
				horas.add(new SelectItem(String.valueOf(x), String.valueOf(x)));
		}for(int m = 0; m < 60; m++){				
			if (m < 10)
				minutos.add(new SelectItem("0"+String.valueOf(m), "0"+String.valueOf(m)));
			else 
				minutos.add(new SelectItem(String.valueOf(m), String.valueOf(m)));
		}
	}
	public void search() {
		
		String start = dtStart+" "+ hrStart+":"+minStart;
		String end = dtFinal+" "+ hrFinal+":"+minFinal;
		String cam = camera;
		System.out.println(start);
		System.out.println(end);
		if(start != null && end != null) {
			
			try {
				list = dao.searchTable(start, end, cam);
				RequestContext.getCurrentInstance().execute("getTr()");
				RequestContext.getCurrentInstance().execute("dataPicker()");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}