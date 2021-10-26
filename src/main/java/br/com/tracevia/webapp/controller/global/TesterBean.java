package br.com.tracevia.webapp.controller.global;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.global.ReportDAO;
import br.com.tracevia.webapp.methods.ExcelModels;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.ReportBuild;
import br.com.tracevia.webapp.model.global.ReportSelection;
import br.com.tracevia.webapp.model.speed.SpeedReport.Builder;
import br.com.tracevia.webapp.util.ExcelTemplate;
import br.com.tracevia.webapp.util.SessionUtil;

@ManagedBean(name="testerBean")
@RequestScoped
public class TesterBean {

	public String table; 
	public List<String> columnsName,
						searchParameters; 
	
	// ----------------------------------------------------------------------------------------------------------------

	private ExcelModels model;
	private List<String> columnsInUse = new ArrayList<>(); 
	private List<String[]> dateSearch = new ArrayList<>();
	private List<Pair<String[], List<String>>> filterSearch = new ArrayList<>();
	
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
		this.columnsInUse = columnsName;
	}
	
	public List<String> getColumnsInUse() {
		return columnsInUse;
	}
	
	public void setColumnsInUse(String[] columns) {
		List<String> cols = new ArrayList<>();
		for (String col : columns) {
			cols.add(columnsName.get(Integer.parseInt(col)));
		}
		columnsInUse = cols;
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

	public List<Pair<String[], List<String>>> getFilterSearch() {
		return filterSearch;
	}

	public void setFilterSearch(String filterSearch, String nameColumn) {
		if (report != null)
			try {
				this.filterSearch.add(new Pair<String[], List<String>>(new String[]{filterSearch, nameColumn}, report.getOtherElementTable(table, filterSearch)));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public void setFilterSearchByTable(String filterSearch, String nameColumn, String tableWithName) {
		String[] tableName = tableWithName.split("\\.");
		System.out.println(tableName.toString());
		
		if (report != null)
			try {
				this.filterSearch.add(new Pair<String[], List<String>>(new String[]{filterSearch, nameColumn}, report.getOtherElementTable(tableName[0], tableName[1])));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
	public String[] getLeft(Pair<String[], List<String>> pair) {
		return pair.left;
	}
	
	public List<String> getRight(Pair<String[], List<String>> pair) {
		return pair.right;
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
		build = new ReportBuild();
	
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
    	  build.clearBool = true;
    	  build.excelBool = true;
    	 // build.chartBool = true;						
	 
	 }	
	
	   // -------------------------------------------------------------------------------------------------------------------------------------------------

	public void createReport() throws Exception {
				
		// Table Fields
		 report = new ReportDAO(columnsName);
		 
	}

	// CAMPOS
		 		
	public void createFields() throws Exception {
		int count = 0;
		Map<String, String> map = SessionUtil.getRequestParameterMap();
		String[] columns = SessionUtil.getRequestParameterValuesMap().get("allColumns");
		setColumnsInUse(columns);
						
		 model = new ExcelModels(); // HERE
		 
		 resetForm();
		 
		String query = "Select ";
		for (String col : columns) {
			query += String.format("%s, ", searchParameters.get(Integer.parseInt(col)));
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
		if (!filterSearch.isEmpty())
			for (Pair<String[], List<String>> search : filterSearch) {
				String filter = map.get(String.format("%s-filter", search.left[0]));

				if (count == 0 && !filter.isEmpty())
					query += " WHERE";

				if (!filter.isEmpty()) {
					query += String.format("%s '%s' = %s", count > 0 ? " AND" : "", filter, search.left[0]);
					count++;
				}
			}
		
		// Table Fields
		 report.getReport(query);
		          	
		     // DESENHAR TABLE
		    //  build.drawTable(build.columns, build.fields, build.fieldObjectValues);
		 
			 // GUARDAR VALORES NA SESS�O		     
		     //SessionUtil.setParam("fieldsLength", columnsName.size()); //Length of Fields
		     // SessionUtil.setParam("fields", build.fields);	//Fields
		     // SessionUtil.setParam("jsonFields", build.jsonFields);	//Fields
		     // SessionUtil.setParam("fieldsObject", build.fieldObjectValues); //Objects
		      		     
		     SessionUtil.executeScript("drawTable('#speed-records-table', '50.3vh');");
		   
	        // GENERATE EXCEL
		     SessionUtil.executeScript("drawTable('#generic-report-table', '50.3vh');");
		     
		     model.StandardFonts();  //Set Font
			 model.StandardStyles(); //Set Style
			 model.StandardBorders(); // Set Borders
		     
		     model.generateExcelFile(columnsInUse, report.lines);
		     
		 	 SessionUtil.getExternalContext().getSessionMap().put("xlsModel", model); 
		     
			 build.clearBool = false; // BOTÃO DE LIMPAR		    	 
	      	 build.excelBool = false; // LINK DE DOWNLOAD DO EXCEL
	      	 
	      		  		    		
	  }
	
		
	   // -------------------------------------------------------------------------------------------------------------------------------------------------
	
	   public void download() {
			
		// MANTER VALORES NA SESSÃO
		//String fileDate = (String) SessionUtil.getExternalContext().getSessionMap().get("datetime");
		//String fileName = (String) SessionUtil.getExternalContext().getSessionMap().get("fileName");
		 
		model = (ExcelModels) SessionUtil.getExternalContext().getSessionMap().get("xlsModel");
	
		String file = "teste_file";
					
		try {
			
			model.download(ExcelModels.workbook, file);
			
		} catch (IOException e) {	
			
			e.printStackTrace();
		}
		
	}
	   
		
	   // -------------------------------------------------------------------------------------------------------------------------------------------------	
		 public void resetForm() {
				
				// Limpa valores da sessão
				build.resetReportValues();
				
				// Reinicializa valores armazenados nas variáveis abaixo
				build = new ReportBuild();
				select = new ReportSelection();
							
				build.excelBool = true;
				build.clearBool = true;
											
			}	
	
	 		
	   // -------------------------------------------------------------------------------------------------------------------------------------------------
	  	
}
