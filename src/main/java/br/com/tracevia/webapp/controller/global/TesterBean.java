package br.com.tracevia.webapp.controller.global;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

	private List<String[]> dateSearch = new ArrayList<>();
	
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

	public List<String[]> getDateSearch() {
		return dateSearch;
	}

	public void setDateSearch(String dateSearch, String nameColumn) {
		this.dateSearch.add(new String[]{dateSearch, nameColumn});
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
		int count = 0;
		Map<String, String> map = SessionUtil.getRequestParameterMap();
						
		String query = "Select ";
		for (String parameter : searchParameters) {
			query += String.format("%s, ", parameter);
		}
		query = String.format("%s FROM %s", query.substring(0, query.length() - 2), table);

		if (!dateSearch.isEmpty())
			for (String[] search : dateSearch) {
				String dateStart = map.get(String.format("%s-start", search[0]));
				String dateEnd = map.get(String.format("%s-end", search[0]));

				if (count == 0 && (!dateStart.isEmpty() || !dateEnd.isEmpty()))
					query += " WHERE";

				if (!dateStart.isEmpty()) {
					query += String.format("%s STR_TO_DATE('%s', '%%d/%%m/%%Y') <= %s", count > 0 ? " AND" : "", dateStart, search[0]);
					count++;
				}
				if (!dateEnd.isEmpty()) {
					query += String.format("%s STR_TO_DATE('%s', '%%d/%%m/%%Y') >= %s", count > 0 ? " AND" : "", dateEnd, search[0]);
					count++;
				}
			}
		
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
		     
		     SessionUtil.executeScript("drawTable('#generic-report-table', '50.3vh');");
		     
		     
		 
		     
		  
		     
		    		
	  }
}
