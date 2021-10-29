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
import br.com.tracevia.webapp.methods.DateTimeApplication;
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
	public String idTable;
	public List<String> columnsName; 
	public List<String> searchParameters;
	
	// ----------------------------------------------------------------------------------------------------------------

	public String 	fileName,
					title,
					sheet = "Report";
	public String 	module,
					total;
	public boolean 	sat,
					caseSensitive = false;

	// ----------------------------------------------------------------------------------------------------------------

	private String period;

	private ExcelTemplate model;
	private List<String> columnsInUse = new ArrayList<>(); 
	private List<String[]> dateSearch = new ArrayList<>();
	private List<Pair<String[], List<String[]>>> filterSearch = new ArrayList<>();
	
	private ReportSelection select;
	private ReportBuild build;
	
	private ReportDAO report;
	public List<Builder> resultList;	

	List<? extends Equipments> listEquips;  
			
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
	
	public List<? extends Equipments> getListEquips() {
		return listEquips;
	}
	
	public void setColumnsInUse(String[] columns) {
		List<String> cols = new ArrayList<>();
		for (String col : columns) {
			cols.add(columnsName.get(Integer.parseInt(col)));
		}
		columnsInUse = cols;
	}

	public void setSearchParameters(String parameter) {
		List<String> searchParameters = Arrays.asList(parameter.split(";"));
		
		this.searchParameters = searchParameters;
	}

	public List<String[]> getDateSearch() {
		return dateSearch;
	}

	public void setDateSearch(String dateSearch, String nameColumn) {
		setDateSearch(dateSearch, nameColumn, false);
	}

	public void setDateSearch(String dateSearch, String nameColumn, boolean mandatory) {
		this.dateSearch.add(new String[]{dateSearch, nameColumn, mandatory ? "required" : ""});
	}

	public List<Pair<String[], List<String[]>>> getFilterSearch() {
		return filterSearch;
	}

	public void setFilterSearch(String filterSearch, String nameColumn) {
		setFilterSearch(filterSearch, nameColumn, String.format("%s.%s", table, filterSearch));
	}
	
	public void setFilterSearch(String filterSearch, String nameColumn, boolean multiple) {
		setFilterSearch(filterSearch, nameColumn, String.format("%s.%s", table, filterSearch), multiple, false);
	}
	
	public void setFilterSearch(String filterSearch, String nameColumn, boolean multiple, boolean mandatory) {
		setFilterSearch(filterSearch, nameColumn, String.format("%s.%s", table, filterSearch), multiple, mandatory);
	}
	
	public void setFilterSearch(String filterSearch, String nameColumn, String tableWithName) {
		setFilterSearch(filterSearch, nameColumn, tableWithName, false, false);
	}
	
	public void setFilterSearch(String filterSearch, String nameColumn, String tableWithName, boolean multiple) {
		setFilterSearch(filterSearch, nameColumn, tableWithName, multiple, false);
	}

	public void setFilterSearch(String filterSearch, String nameColumn, String tableWithName, boolean multiple, boolean mandatory) { // Esse metodo pode ser mais rapido do que o metodo acima, pois, te permite escolher uma tabela menor sómente com os campos necessarios
		String[] tableName = tableWithName.split("\\.");

		String extra1 = multiple ? "multiple" : "";
		String extra2 = mandatory ? "required" : "";
		
		if (report != null)
			try {
				if (tableName[1].contains("|"))
					this.filterSearch.add(new Pair<String[], List<String[]>>(new String[]{filterSearch, nameColumn, extra1, extra2}, report.getOtherElementTable(tableName[0], tableName[1].split("\\|"))));
				else
					this.filterSearch.add(new Pair<String[], List<String[]>>(new String[]{filterSearch, nameColumn, extra1, extra2}, report.getOtherElementTable(tableName[0], tableName[1])));
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public void setTable(String table) {
		this.table = table;
	}
	
	public void setIdTable(String idTable) {
		this.idTable = idTable;
	}
	
	public void defineFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public void defineTitle(String title) {
		this.title = title;
	}
	
	public void defineSheet(String sheet) {
		this.sheet = sheet;
	}
	
	public void defineModule(String module) {
		this.module = module;
	}
	
	public void defineTotal(String total) {
		this.total = total;
	}
	
	public void defineSat() {
		this.sat = true;
	}
	
	public void defineCaseSensitive() {
		this.caseSensitive = true;
	}
	
	public boolean isTotal() {
		return total == null ? false : true;
	}
		
	public ReportDAO getReport() {
		return report;
	}

	public void setReport(ReportDAO report) {
		this.report = report;
	}

	public void periodMinutes(int minute, String date) {
		this.period = String.format("DATE_ADD(STR_TO_DATE(CONCAT(DATE(%1$s), ' ', HOUR(%1$s), ':', FLOOR(MINUTE(%1$s) / %2$d) * %2$d, ':00'), '%%Y-%%m-%%d %%H:%%i:%%s'), INTERVAL %2$d MINUTE) as dat, ", date, minute);
	}

	public void periodHours(int hour, String date) {
		this.period = String.format("DATE_ADD(STR_TO_DATE(CONCAT(DATE(%1$s), ' ', FLOOR(HOUR(%1$s) / %2$d) * %2$d, ':00:00'), '%%Y-%%m-%%d %%H:%%i:%%s'), INTERVAL %2$d HOUR) as dat, ", date, hour);
	}
	
	public void periodDay(String date) {
		this.period = String.format("DATE(%s) as dat, ", date);
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
		boolean setPeriod = false;
		Map<String, String> map = SessionUtil.getRequestParameterMap();
		Map<String, String[]> mapArray = SessionUtil.getRequestParameterValuesMap();
		String[] columns = mapArray.get("allColumns");
		setColumnsInUse(columns);
						
		model = new ExcelTemplate(); // HERE
		
		String dateStart = "", dateEnd = "";
				
		resetForm();
		
		String query = "SELECT ";
		for (String col : columns) {
			if (searchParameters.get(Integer.parseInt(col)).trim().equals("$period") && period != null) {
				query += period;
				setPeriod = true;
			} else
				query += String.format("%s, ", searchParameters.get(Integer.parseInt(col)));
		}
		if (!setPeriod && period != null)
			query += period;

		query = String.format("%s FROM %s", query.substring(0, query.length() - 2), table);
					

		if (!dateSearch.isEmpty())
			for (String[] search : dateSearch) {
				dateStart = map.get(String.format("%s-start", search[0]));
				dateEnd = map.get(String.format("%s-end", search[0]));

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
			for (Pair<String[], List<String[]>> search : filterSearch) {
				String filter = "";
				if (search.left[2].equals("multiple")) {
					String[] filterArray = mapArray.get(String.format("%s-filter", search.left[0]));
					String newFilter = "";

					if (filterArray != null) {						
						for (String f : filterArray) {
							newFilter = String.format("%s,%s '%s'", newFilter, caseSensitive ? " BINARY" : "", f);
						}
						filter = newFilter.substring(2);
					}
				}
				else {
					String f = map.get(String.format("%s-filter", search.left[0]));
					if (!f.isEmpty())
						filter = String.format("%s'%s'", caseSensitive ? "BINARY " : "", f);
				}

				if (count == 0 && !filter.isEmpty())
					query += " WHERE";

				if (!filter.isEmpty()) {
					query += String.format("%s %s IN (%s)", count > 0 ? " AND" : "", search.left[0], filter);
					count++;
				}
			}
			if (setPeriod && period != null)
				query += " GROUP BY dat ORDER BY dat DESC";

		   // Table Fields
		    report.getReport(query, idTable);

			System.out.println(report.IDs);
		          	
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
				     
		     model.generateExcelFile(columnsInUse, report.lines,"sos", report.IDs, dateStart, dateEnd, "", "TRACEVIA", "Teste", false, false);
		     
		 	 SessionUtil.getExternalContext().getSessionMap().put("xlsModel", model); 
		     
			 build.clearBool = false; // BOTÃO DE LIMPAR	 
	      	 build.excelBool = false; // LINK DE DOWNLOAD DO EXCEL
	      	 	      		  		    		
	    }
	
		
	   // -------------------------------------------------------------------------------------------------------------------------------------------------
	
	   public void download() {
		   
		   DateTimeApplication dta = new DateTimeApplication();
			
		// MANTER VALORES NA SESSÃO
		//String fileDate = (String) SessionUtil.getExternalContext().getSessionMap().get("datetime");
		//String fileName = (String) SessionUtil.getExternalContext().getSessionMap().get("fileName");
		 
		model = (ExcelTemplate) SessionUtil.getExternalContext().getSessionMap().get("xlsModel");
	
		String file = "teste_file_"+dta.currentDateToExcelFile();
					
		try {
			
			model.download(file);
			
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
	  	

			/**
			 * Método para carregar equipamentos disponíveis para seleção
			 * @author Wellington 26/10/2021
			 * @version 1.0
			 * @since 1.0
			 * @param module - modulo que os equipamentosa devem ser carregados
			 */
			public void defineEquipmentsOption(String module) {

				try {

					EquipmentsDAO dao = new EquipmentsDAO();		 
					listEquips = dao.EquipmentSelectOptions(module);

					build.equipments = build.selectEquips(listEquips);

					// EQUIPMENTS
					// build.equipments = build.selectEquips(listSpeed);
					// select.equipments = new String[build.equipments.size()];  

				} catch (Exception e) {			
					e.printStackTrace();
				}
			}

			// --------------------------------------------------------------------------------------------		

			/**
			 * Método para carregar períodos disponíveis para seleção
			 * @author Wellington 26/10/2021
			 * @version 1.0
			 * @since 1.0	
			 */
			public void definePeriodsOption() {

				try {

					build.periods = build.selectPeriods();

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			// --------------------------------------------------------------------------------------------	

}
