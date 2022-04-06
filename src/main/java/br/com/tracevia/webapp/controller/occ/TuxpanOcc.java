package br.com.tracevia.webapp.controller.occ;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.primefaces.context.RequestContext;

import br.com.tracevia.webapp.dao.occ.TuxpanDAO;
import br.com.tracevia.webapp.model.occ.TuxpanOccModel;

@ManagedBean(name="OccController")
public class TuxpanOcc{
	
	@PostConstruct
	public void init() {
		data = new TuxpanOccModel();
		dao = new TuxpanDAO();
		listar = new ArrayList<TuxpanOccModel>();
		try {
			listar = dao.listarOcorrencias();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean select() {
		
		boolean check = false;
		dao = new TuxpanDAO();
		
		try {
			data = dao.select(idTable);
			System.out.println("method select > "+idTable );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return check;
	}
	public boolean saveOcc() {
		boolean check = false;
		dao = new TuxpanDAO();
		try {
			check = dao.registerOcc(data, typeReport);
			if(check == false) {
				//message error
			}else {
				//message success
				listTable();
				//init();
				//RequestContext.getCurrentInstance().execute("listOcc()");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return check;
	}	
	public boolean listTable() {
		boolean check = false;
		try {
			listar = dao.listarOcorrencias();
			if(listar.size() != 0) {
				RequestContext.getCurrentInstance().execute("table()");
				check = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return check;
	}
	//variables
	private String typeReport, idTable;
	private TuxpanDAO dao;
	private TuxpanOccModel data;
	private List<TuxpanOccModel> listar;
	
	//setter and getter
	public List<TuxpanOccModel> getListar() {
		return listar;
	}
	
	public String getIdTable() {
		return idTable;
	}
	public void setIdTable(String idTable) {
		this.idTable = idTable;
	}
	public String getTypeReport() {
		return typeReport;
	}
	public void setTypeReport(String typeReport) {
		this.typeReport = typeReport;
	}
	public void setListar(List<TuxpanOccModel> listar) {
		this.listar = listar;
	}
	public TuxpanOccModel getData() {
		return data;
	}
	public void setData(TuxpanOccModel data) {
		this.data = data;
	}
}