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
		initializeData();
		dao = new TuxpanDAO();
		listar = new ArrayList<TuxpanOccModel>();

		try {
			listar = dao.listarOcorrencias();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void initializeData() {
		data = new TuxpanOccModel();
	}
	public void pdf() {
		dao = new TuxpanDAO();
		PDF pdf = new PDF();
		try {
			data = dao.select(idTable);
			pdf.selectPdf(idTable, data);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public boolean update() {
		boolean check = false;

		dao = new TuxpanDAO();
		try {
			check = dao.update(data, Integer.parseInt(idTable));
			if(check == false) {
				//error
				listTable();
				scriptsSin();
			}else {
				//sucess
				listTable();
				scriptsSin();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return check;
	}
	public boolean select() {
		boolean check = false;
		dao = new TuxpanDAO();

		try {

			data = dao.select(idTable);

			if(data.getType_report().equals("1")) {
				//message type report one
				scriptsOcc();
				RequestContext.getCurrentInstance().execute("$('#modalOcc').modal('show')");
				//arguments
				veh_inv(data.getTipo_veh_inv(), data.getNum_eje_veh_inv(), data.getNum_tp_veh(),
						data.getMarca_tp_veh(), data.getTipo_tp_veh(), data.getModel_tp_veh(),
						data.getColor(), data.getPlaca_estado(), data.getTel(), data.getId_person(),
						data.getNombre(), data.getEdad(), data.getCondiciones());

			}else if(data.getType_report().equals("2")) {
				//message type report two
				scriptsSin();
				RequestContext.getCurrentInstance().execute("$('#modalSin').modal('show')");
				//
				ocup_veh_sin(data.getOcupantes_sin(), data.getVeh_sin(), data.getObs_sin());
			}else {
				//error type report
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return check;
	}
	public boolean ocup_veh_sin(String ocup, String veh, String Obs) {
		boolean check = false;
		RequestContext.getCurrentInstance().execute(String.format("plusInputSin('%s', '%s', '%s')", ocup, veh, Obs));
		return check;
	}
	public boolean veh_inv(String tipo, String eje, String num_veh, String marca,
			String tipo_veh, String modelo, String cor, String placa, String tel,
			String id_person, String nombre, String edad, String cond) {
		boolean check = false;

		RequestContext.getCurrentInstance().execute(String.format("plusInputVehInv('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')",
				tipo, eje, num_veh, marca, tipo_veh, modelo, cor, placa, tel, id_person, nombre, edad, cond));
		//RequestContext.getCurrentInstance().execute("saveSin();");
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
				if(typeReport.equals("1")) {
					//type occ
					scriptsOcc();
				}else {
					//type sin
				}
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
			if(listar.size() > 0) {
				RequestContext.getCurrentInstance().execute("table()");
				check = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return check;
	}
	public void scriptsOcc() {
		RequestContext.getCurrentInstance().execute("appendOcc()");
		RequestContext.getCurrentInstance().execute("hiddenBts()");

	}
	public void scriptsSin() {
		RequestContext.getCurrentInstance().execute("clickUpdate();");
		RequestContext.getCurrentInstance().execute("blockVirgula();");
		RequestContext.getCurrentInstance().execute("vehOcupSin();");
		RequestContext.getCurrentInstance().execute("hiddenBtnSin();");
	}
	//variables
	private String typeReport, idTable, reportType;
	private TuxpanDAO dao;
	private TuxpanOccModel data;
	private List<TuxpanOccModel> listar;

	//setter and getter
	public List<TuxpanOccModel> getListar() {
		return listar;
	}

	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
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