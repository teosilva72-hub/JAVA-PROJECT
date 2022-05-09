package br.com.tracevia.webapp.controller.occ;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.servlet.ServletException;
import javax.servlet.http.Part;

import org.primefaces.context.RequestContext;

import com.groupdocs.conversion.internal.c.a.s.internal.nx.ma;

import br.com.tracevia.webapp.dao.occ.TuxpanDAO;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.occ.TuxpanOccModel;

@ManagedBean(name="OccController")
public class TuxpanOcc implements Serializable{
	
	/**
	 * SERIAL ID
	 */
	private static final long serialVersionUID = -1758316549584598394L;

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
		listarAqr = null;
	}

	public boolean update() {
		boolean check = false;

		dao = new TuxpanDAO();
		try {
			check = dao.update(data, Integer.parseInt(idTable));
			String filter = filter(data.getReporte(), data.getSiniestro(), data.getFolio_sec());
			createFolder(filter);
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
		//RequestContext.getCurrentInstance().execute("setFile()");
		try {

			data = dao.select(idTable);
			String filter = filter(data.getReporte(), data.getSiniestro(), data.getFolio_sec());
			mainPath = createFolder(filter);
			listarAqr = listarFiles(mainPath);
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
		//RequestContext.getCurrentInstance().execute("setFile();");
		//copy(File filter, File filer);
		try {
			check = dao.registerOcc(data, typeReport);

			if(check == false) {
				//message error
			}else {

				//createFolder();
				//message success
				if(typeReport.equals("1")) {
					//type occ
					scriptsOcc();
				}else if(typeReport.equals("2")){
					if(reporte.equals(null) || reporte.equals(reporte)) reporte = "-";
					//type sin
				}
				filter(reporte, siniestro, folio_sec);
				listarAqr = null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return check;
	}	
	
	public String filter(String reporte, String siniestro, String folio) {
		reporte = reporte.replace(" ", ""); siniestro = siniestro.replace(" ", ""); folio = folio.replace(" ", "");
		reporte = reporte.replace(",", ""); siniestro = siniestro.replace(",", ""); folio = folio.replace(",", "");
		reporte = reporte.replace("-", "");	siniestro = siniestro.replace("-", ""); folio = folio.replace("-", "");
		reporte = reporte.replace("/", ""); siniestro = siniestro.replace("/", ""); folio = folio.replace("/", "");
		reporte = reporte.replace("\\", ""); siniestro = siniestro.replace("\\", ""); folio = folio.replace("\\", "");
		reporte = reporte.replace("*", ""); siniestro = siniestro.replace("*", ""); folio = folio.replace("*", "");
		reporte = reporte.replace("|", "");	siniestro = siniestro.replace("|", ""); folio = folio.replace("|", "");
		reporte = reporte.replace("?", ""); siniestro = siniestro.replace("?", ""); folio = folio.replace("?", "");
		String result = reporte+siniestro+folio;
		createFolder(result);
		return result;
	}
	public String createFolder(String date){
		DateTimeApplication dta = new DateTimeApplication();
		LocalDate local = dta.localeDate();
		mainPath = "C:\\Report_ocurrencias\\"+date+"\\";
		File directory = new File(mainPath);
		if (!directory.exists())
			directory.mkdirs();			
		return mainPath;
	}
	public String[] listarFiles(String date) {
		
		File dir = new File(date);
		File[] arq = dir.listFiles();
		listarAqr = new String[arq.length];
		for(int i = 0; i< arq.length; i++) {
			listarAqr[i] = arq[i].getName();
		}
		return listarAqr;
	}
	public void uploadFile() throws Exception {
		uploadBean up = new uploadBean();	
		String space = "";
		String filter = filter(reporte, siniestro, folio_sec);
		mainPath = createFolder(filter);
		//passando variavel para uploadFile
		up.upload(file, mainPath, space);	
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
		//RequestContext.getCurrentInstance().execute("clickUpdate();");
		RequestContext.getCurrentInstance().execute("blockVirgula();");
		RequestContext.getCurrentInstance().execute("vehOcupSin();");
		RequestContext.getCurrentInstance().execute("hiddenBtnSin();");
	}
	//variables
	private String typeReport, idTable, reportType, folio_sec, reporte, siniestro, mainPath = "";
	private String[] listarAqr = null;
	private Part file = null, dst = null;
	private TuxpanDAO dao;
	private TuxpanOccModel data;
	private List<TuxpanOccModel> listar;

	//setter and getter

	public List<TuxpanOccModel> getListar() {
		return listar;
	}

	public String[] getListarAqr() {
		return listarAqr;
	}
	public void setListarAqr(String[] listarAqr) {
		this.listarAqr = listarAqr;
	}
	public Part getFile() {
		return file;
	}
	public void setFile(Part file) {
		this.file = file;
	}
	public void setListar(List<TuxpanOccModel> listar) {
		this.listar = listar;
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

	public TuxpanOccModel getData() {
		return data;
	}
	public void setData(TuxpanOccModel data) {
		this.data = data;
	}
	public String getFolio_sec() {
		return folio_sec;
	}
	public void setFolio_sec(String folio_sec) {
		this.folio_sec = folio_sec;
	}
	public String getReporte() {
		return reporte;
	}
	public void setReporte(String reporte) {
		this.reporte = reporte;
	}
	public String getSiniestro() {
		return siniestro;
	}
	public void setSiniestro(String siniestro) {
		this.siniestro = siniestro;
	}

}