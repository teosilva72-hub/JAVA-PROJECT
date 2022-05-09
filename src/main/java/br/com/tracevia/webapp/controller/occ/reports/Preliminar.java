package br.com.tracevia.webapp.controller.occ.reports;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import org.primefaces.context.RequestContext;

import br.com.tracevia.webapp.controller.occ.uploadBean;
import br.com.tracevia.webapp.dao.occ.TuxpanDAO;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.occ.TuxpanOccModel;

@ManagedBean(name="OccPreliminar")
public class Preliminar {
	@PostConstruct
	public void init() {
		initializeData();
		dao = new TuxpanDAO();
		listar = new ArrayList<TuxpanOccModel>();
		RequestContext.getCurrentInstance().execute("scann()");
		try {
			listar = dao.listPreliminar();

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
			String val = "2";
			check = dao.update(data, Integer.parseInt(idTable), val);
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
		RequestContext.getCurrentInstance().execute("editInfos();deleteFile()");
		try {

			data = dao.selectPreliminar(idTable);
			String filter = "";
			
			
			if(data.getSiniestro().equals("") || data.getFolio_sec().equals("")) {
				siniestro = "-"; folio_sec = "-";
				filter = filter(data.getIdPasta(), siniestro, folio_sec);
			}else {
				filter = filter(data.getReporte(), data.getSiniestro(), data.getFolio_sec());
			}
			
			mainPath = createFolder(filter);
			listarAqr = listarFiles(mainPath);
			if(data.getType_report().equals("2")) {
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
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			String idPasta = dateFormat.format(date);
			if(siniestro.equals("") || folio_sec.equals("")) {
				siniestro = "-"; folio_sec = "-";
				filter(idPasta, siniestro, folio_sec);
			}else {
				filter(reporte, siniestro, folio_sec);
			}
			
			check = dao.registerOcc(data, typeReport, idPasta);

			if(check == false) {
				//message error
			}else {
				//message success
				//if(reporte.equals(null) || reporte.equals(reporte)) reporte = "-";
				
				listTable();
				listarAqr = null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return check;
	}	

	public String filter(String reporte, String siniestro, String folio) {
		String pattern = "[\\s,-/\\\\\\*\\|\\?:<>]";
		reporte = reporte.replaceAll(pattern, ""); siniestro = siniestro.replaceAll(pattern, ""); folio = folio.replaceAll(pattern, "");
		String result = reporte+siniestro+folio;
		createFolder(result);
		return result;
	}
	public String createFolder(String date){
		DateTimeApplication dta = new DateTimeApplication();
		LocalDate local = dta.localeDate();
		mainPath = "C:\\Cameras\\Report_ocurrencias\\"+date+"\\";
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

			totalFile = i;
		}
		return listarAqr;
	}
	public void deleteRegister() {

		nivelAcesso = (int) facesContext.getExternalContext().getSessionMap().get("nivel");
		dao = new TuxpanDAO();
		String type = "2";
		boolean check = false;
		try {
			if(nivelAcesso == 1 || nivelAcesso == 6) {
				check = dao.deleteRegister(idTable, type);
			}
			listTable();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void deleteFile() {
		RequestContext.getCurrentInstance().execute("$('.divForm').addClass('hidden');$('.divFile').removeClass('hidden');");
		String filter = filter(reporte, siniestro, folio_sec);
		mainPath = createFolder(filter);
		File path = new File(mainPath, fileName);
		path.delete();
	}
	public void uploadFile() throws Exception {
		uploadBean up = new uploadBean();	
		String space = "";
		data = dao.selectPreliminar(idTable);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String idPasta = dateFormat.format(date);
		if(siniestro.equals("") || folio_sec.equals("")) {
			if(data.getIdPasta().equals(data.getIdPasta()) != data.getIdPasta().equals("")) {
				siniestro = "-"; folio_sec = "-";
				
				String filter = filter(data.getIdPasta(), siniestro, folio_sec);
				mainPath = createFolder(filter);
				up.upload(file, mainPath, space);
			}else {
				siniestro = "-"; folio_sec = "-";
				String filter = filter(idPasta, siniestro, folio_sec);
				mainPath = createFolder(filter);
				up.upload(file, mainPath, space);
			}
			
		}else {
			String filter = filter(reporte, siniestro, folio_sec);
			mainPath = createFolder(filter);
			up.upload(file, mainPath, space);
		}
		
		//passando variavel para uploadFile
			
		RequestContext.getCurrentInstance().execute("listSin(); setFile()");
	}

	public boolean listTable() {
		boolean check = false;
		try {
			listar = dao.listPreliminar();
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
	private String typeReport, idTable, reportType, folio_sec, reporte, siniestro, mainPath = "", fileName;
	private int totalFile, nivelAcesso;
	private FacesContext facesContext = FacesContext.getCurrentInstance();
	private String[] listarAqr = null;
	private Part file = null, dst = null;
	private TuxpanDAO dao;
	private TuxpanOccModel data;
	private List<TuxpanOccModel> listar;

	//setter and getter

	public List<TuxpanOccModel> getListar() {
		return listar;
	}

	public int getTotalFile() {
		return totalFile;
	}
	public void setTotalFile(int totalFile) {
		this.totalFile = totalFile;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
