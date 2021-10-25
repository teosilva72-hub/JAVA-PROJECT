package br.com.tracevia.webapp.controller.global;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.global.ReportDAO;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.ReportBuild;
import br.com.tracevia.webapp.model.global.ReportSelection;
import br.com.tracevia.webapp.model.speed.SpeedReport.Builder;
import br.com.tracevia.webapp.util.SessionUtil;

@ManagedBean(name="testerBean")
@RequestScoped
public class TesterBean {

	public String table; 
	public List<String> columnsName; 
	public List<String> searchParameters; 

	
	// ----------------------------------------------------------------------------------------------------------------
	
	private static String DATE_COLUMN = "save_date";
	private static String TABLE = "speed_registry";
	private static String MODULE = "speed";
	private static String INDEX_NAME = "idx_date_speed_id";
	private static String JOIN_COLUMN = "equip_id";
	private static String EQUIP_COLUMN = "id_speed";
		
	// ----------------------------------------------------------------------------------------------------------------
	
	private ReportSelection select;
	private ReportBuild build;
	
	private ReportDAO report;
	public List<Builder> resultList;	

	List<? extends Equipments> listSpeed;  
			
	public String[] equipNames;
		
	public ReportSelection getSelect() {
		return select;
	}
		
	public void setSelect(ReportSelection select) {
		this.select = select;
	}
	
	public ReportBuild getBuild() {
		return build;
	}

	public void setBuild(ReportBuild build) {
		this.build = build;
	}

	public List<Builder> getResultList() {
		return resultList;
    }
	
	public void setColumnsName(String columns) {
		List<String> columnsName = Arrays.asList(columns.split(","));

		this.columnsName = columnsName;
	}

	public void setSearchParameters(String parameter) {
		List<String> searchParameters = Arrays.asList(parameter.split(","));
		
		this.searchParameters = searchParameters;
	}
	
	public void setTable(String table) {
		this.table = table;
	}
	
	
	public ReportDAO getReport() {
		return report;
	}

	public void setReport(ReportDAO report) {
		this.report = report;
	}
	
				
	// ----------------------------------------------------------------------------------------------------------------
		
		// CLASS PATH

		// private static String classLocation = SpeedReportsBean.class.getCanonicalName();
		
		// --------------------------------------------------------------------------------------------
		
		// CLASS LOG FOLDER
		
		// private static String classErrorPath = LogUtils.ERROR.concat("reports\\speed\\");
		
		// --------------------------------------------------------------------------------------------
		
		// EXCEPTION FILENAMES
		
		// private static String ioExceptionLog = classErrorPath.concat("io_exception_");
		
		// --------------------------------------------------------------------------------------------		
		
		// CONSTRUTOR 
	


	@PostConstruct
	public void initialize() {
		
		// Instantiate Objects - build and select class

		select = new ReportSelection();
				
		// ---------------------------------------------------------------------------
				   		
		// SELECT ITEMS LISTS		
        // build.equipments = new ArrayList<SelectItem>();	  
        // build.periods = new ArrayList<SelectItem>();
        
        // listSpeed = new ArrayList<Equipments>();
                
        // COLUMNS LIST
        // build.columns = new ArrayList<ColumnModel>();
        
        //DEFINE LOCALE
        // build.localeLabels = LocaleUtil.setLocale(LocaleUtil.LABELS_SPEED);
        
        try {
        		        	
        	EquipmentsDAO dao = new EquipmentsDAO();		 
			listSpeed = dao.EquipmentSelectOptions("speed");
        	
        	// EQUIPMENTS
        	//  build.equipments = build.selectEquips(listSpeed);
        	//  select.equipments = new String[build.equipments.size()];  
        	 
        	//  build.periods = build.selectPeriods();
        	        	                	 			
		} catch (Exception e) {			
			e.printStackTrace();
		}
                 	
    	// ----------------------------------------------------------------------------
        	
    	 // Disabled Buttons
    	//  build.clearBool = true;
    	//  build.excelBool = true;
    	//  build.chartBool = true;						
	 
	 }	
	
    // ----------------------------------------------------------------------------------------------------------------
	public void createReport() throws Exception {
		// Table Fields
		 report = new ReportDAO(columnsName);
	}

	// CAMPOS
		 		
	public void createFields() throws Exception {
						
		String query = "Select ";
		for (String parameter : searchParameters) {
			query += String.format("%s, ", parameter);
		}
		query = String.format("%s FROM %s", query.substring(0, query.length() - 2), table);
		
		// Table Fields
		 report.getReport(query);
		 		
       // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  		          	
		     // DESENHAR TABLE
		    //  build.drawTable(build.columns, build.fields, build.fieldObjectValues);
		 
			 // GUARDAR VALORES NA SESS�O		     
		     SessionUtil.setParam("fieldsLength", columnsName.size()); //Length of Fields
		    //  SessionUtil.setParam("fields", build.fields);	//Fields
		     // SessionUtil.setParam("jsonFields", build.jsonFields);	//Fields
		     //  SessionUtil.setParam("fieldsObject", build.fieldObjectValues); //Objects
		     
		     System.out.println(""+query+"  "+ columnsName);
		     
		     SessionUtil.executeScript("drawTable('#speed-records-table', '50.3vh');");
		     
		     
		 
		     
		  
		     
		    		
	  }
}
