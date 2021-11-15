package br.com.tracevia.webapp.controller.global;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.context.RequestContext;

import com.google.gson.Gson;
import com.mysql.cj.conf.ConnectionUrlParser.Pair;

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.global.ReportDAO;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.TranslationMethods;
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
	public List<String> columnsName = new ArrayList<>(); 
	public List<String> searchParameters;
	
	private final String dateFormat = "dd/MM/yyyy";
	private final String datetimeFormat = "dd/MM/yyyy HH:mm";
	
	// ----------------------------------------------------------------------------------------------------------------

	public String 	fileName,
					fileTitle, usePeriod,
					sheetName = "Report";
	public String 	module;
	
	public String 	jsTable, jsTableScroll;
	public boolean 	isSat, haveTotal, multiSheet = true, isChart = false,
					caseSensitive = false;

	// ----------------------------------------------------------------------------------------------------------------

	private String periodColumn;
	private List<String[]> period = new ArrayList<>();
	private String extraGroup = "";
	private String columnDate;
	private String innerJoin;
	private String useIndex;
	private String[] division;

	private ExcelTemplate model;
	private List<String> columnsInUse = new ArrayList<>(); 
	private HashMap<String, List<String>> listArgs = new HashMap<>(); 
	private List<String[]> dateSearch = new ArrayList<>();
	private List<Pair<String[], List<String[]>>> filterSearch = new ArrayList<>();
	
	private ReportSelection select;
	private ReportBuild build;
	
	private ReportDAO report;
	public List<Builder> resultList;	

	List<? extends Equipments> listEquips;  
			
	public String[] equipNames;

	// Get
		
	public ReportSelection getSelect() {
		return select;
	}
	
	public ReportBuild getBuild() {
		return build;
	}

	public List<Builder> getResultList() {
		return resultList;
    }
	
	public List<String> getColumnsInUse() {
		return columnsInUse;
	}
	
	public List<? extends Equipments> getListEquips() {
		return listEquips;
	}

	public List<String[]> getDateSearch() {
		return dateSearch;
	}

	public List<Pair<String[], List<String[]>>> getFilterSearch() {
		return filterSearch;
	}
		
	public ReportDAO getReport() {
		return report;
	}

	// Set

	public void setBuild(ReportBuild build) {
		this.build = build;
	}
		
	public void setSelect(ReportSelection select) {
		this.select = select;
	}
	
	public void setColumnsName(String columns) {
		for (String col : columns.split(",")) {
			if (col.contains("$foreach")) {
				try {
					ReportDAO report = new ReportDAO(columnsName);
					List<String> args = new ArrayList<>();

					String[] table = col.split("=")[1].split("\\.");
					String[] alias = table[1].split("@"); //asd
					
					List<String[]> fields = report.getOtherElementTable(table[0], alias[0].split("\\|"));

					for (String[] field : fields) {
						setColumns(field[0]);
						args.add(field[1]);
					}
					listArgs.put(alias[1], args);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				setColumns(col);
			}
		}
	}
	
	public void setColumns(String col) {
		this.columnsName.add(col);
		this.columnsInUse.add(col);
	}
	
	public void setColumnsInUse(String[] columns) {
		List<String> cols = new ArrayList<>();
		for (String col : columns) {
			cols.add(columnsName.get(Integer.parseInt(col)));
		}
		columnsInUse = cols;
	}

	public void setSearchParameters(String parameter) {
		List<String> searchParameters = Arrays.asList(parameter.replace("`", "'").split(";"));
		
		this.searchParameters = searchParameters;
	}

	public void setDateSearch(String dateSearch, String nameColumn) {
		setDateSearch(dateSearch, nameColumn, false);
	}

	public void setDateSearch(String dateSearch, String nameColumn, boolean mandatory) {
		setDateSearch(dateSearch, nameColumn, mandatory, false);
	}

	public void setDateSearch(String dateSearch, String nameColumn, boolean mandatory, boolean forProcess) {
		this.dateSearch.add(new String[]{dateSearch, nameColumn, mandatory ? "required" : "", forProcess ? "" : null});
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
	
	public void setPeriodColumn(String column) {
		this.periodColumn = column;
	}
	
	public void setDivision(String division) {
		this.division = division.split(",");
	}
	
	public void setInnerJoin(String innerJoin) {
		this.innerJoin = innerJoin;
	}
	
	public void setUseIndex(String useIndex) {
		this.useIndex = useIndex;
	}
	
	public void setColumnDate(String columnDate) {
		this.columnDate = columnDate;
	}
	
	public void setPeriod(int time, String step, String name) {
		this.period.add(new String[]{String.valueOf(time), step, name});
	}
	
	public List<String[]> getPeriod() {
		return period;
	}
	
	public void setExtraGroup(String extraGroup) {
		this.extraGroup = ", " + extraGroup;
	}

	public void setReport(ReportDAO report) {
		this.report = report;
	}

	// Devines
	
	public void defineFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public void defineFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}
	
	public void defineSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	
	public void defineModule(String module) {
		this.module = module;
	}
	
	public void haveTotal(boolean total) {
		this.haveTotal = total;
	}
	
	public void haveChart(boolean chart) {
		this.isChart = chart;
	}
	
	public void isSat(boolean sat) {
		this.isSat = sat;
	}
	
	public void defineMultiSheet(boolean multiSheet) {
		this.multiSheet = multiSheet;
	}
	
	public void defineJsTable(String jsTable) {
		this.jsTable = jsTable;
		
	}
	
	public void defineJsTableScroll(String jsTableScroll) {
		this.jsTableScroll = jsTableScroll;
		
	}
	public void defineCaseSensitive() {
		this.caseSensitive = true;
	}

	// Check
		
	public boolean isDivision() {
		return division == null ? false : true;
	}

	public boolean withInnerJoin() {
		return innerJoin == null ? false : true;
	}	

	public boolean withIndex() {
		return useIndex == null ? false : true;
	}
	
	public boolean hasPeriod() {
		return periodColumn != null ? true : false;
	}
	
	public boolean hasColumnDate() {
		return columnDate != null ? true : false;
	}

	// Util

	public String genPeriod(String[] time) {
		switch (time[1].toUpperCase()) {
			case "MINUTE":
				return String.format("STR_TO_DATE(CONCAT(DATE(%1$s), ' ', HOUR(%1$s), ':', FLOOR(MINUTE(%1$s) / %2$s) * %2$s, ':00'), '%%Y-%%m-%%d %%H:%%i:%%s')", periodColumn, time[0]);

			case "HOUR":
				return String.format("STR_TO_DATE(CONCAT(DATE(%1$s), ' ', FLOOR(HOUR(%1$s) / %2$s) * %2$s, ':00:00'), '%%Y-%%m-%%d %%H:%%i:%%s')", periodColumn, time[0]);
		
			default:
				return String.format("DATE(%s)", periodColumn);
		}
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
    	  build.chartBool = true;						
	 
	 }	
	
	   // -------------------------------------------------------------------------------------------------------------------------------------------------
	
	public void createReport() throws Exception {
				
		 // Table Fields
		report = new ReportDAO(columnsName);
		List<String> parameters = new ArrayList<>();

		for (String column : searchParameters) {
			if (column.contains("$custom")) {
				Pattern pattern = Pattern.compile("^\\w+");
				Matcher alias = pattern.matcher(column.split("@")[1]);
				if (alias.find())
					if (listArgs.containsKey(alias.group(0))) {
						List<String> values = listArgs.get(alias.group(0));
						for (String arg : values) {
							String columns_replace = column.replace(String.format("$custom@%s", alias.group(0)), arg);
	
							parameters.add(String.format("%s", columns_replace));
						}
					}
			} else
				parameters.add(column);
		}

		searchParameters = parameters;
		 
	}

	// CAMPOS
	
	public void createFields() throws Exception {
		
		int count = 0;
		boolean setPeriod = false;
		
		Map<String, String> map = SessionUtil.getRequestParameterMap();
		Map<String, String[]> mapArray = SessionUtil.getRequestParameterValuesMap();
		
		String[] columns = mapArray.get("allColumns");
		String selectedPeriod = (String) map.get("date-period");
		usePeriod = selectedPeriod;
		
		List<String> idSearch = new ArrayList<>();
		String group = "$period";
		Date[] dateProcess = null;
		String[] period = null;
		setColumnsInUse(columns);
						
		model = new ExcelTemplate(); // HERE
		
		String dateStart = "", dateEnd = "";
				
		resetForm();
		
		String query = "SELECT ";
		for (String col : columns) {
			String column = searchParameters.get(Integer.parseInt(col));

			if (!setPeriod && hasPeriod() && column.contains("$period")) {
				period = selectedPeriod.split(",");
				column = column.replace("$period", genPeriod(period));

				if (column.contains("@")) {
					String[] alias = column.split("@");
					query += String.format("%s as %s, ", alias[0], group);
					group = String.format("%s, %s", alias[1], group);
				} else
					query += String.format("%s as %s, ", column, group);
					
				selectedPeriod = period[2];
				setPeriod = true;
			} else
				query += String.format("%s, ", column);
		}

		query = String.format("%s FROM %s", query.substring(0, query.length() - 2), table);

		if (withIndex())
			query += String.format(" USE INDEX(%s)", useIndex);
		if (withInnerJoin())
			query += String.format(" INNER JOIN %s", innerJoin);

		if (!dateSearch.isEmpty())
			for (String[] search : dateSearch) {
				dateStart = map.get(String.format("%s-start", search[0]));
				dateEnd = map.get(String.format("%s-end", search[0]));

				if (search[3] != null && !dateStart.isEmpty() && !dateEnd.isEmpty()) {
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(format.parse(dateEnd));
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					Date start = format.parse(dateStart);
					Date end = calendar.getTime();
					dateProcess = new Date[] { start, end };
				}
				if (count == 0 && (!dateStart.isEmpty() || !dateEnd.isEmpty()))
					query += " WHERE";

				if (!dateStart.isEmpty()) {
					query += String.format("%s STR_TO_DATE('%s', '%%d/%%m/%%Y') <= DATE(%s)", count > 0 ? " AND" : "", dateStart, search[0]);
					count++;
				}
				if (!dateEnd.isEmpty()) {
					query += String.format("%s STR_TO_DATE('%s', '%%d/%%m/%%Y') >= DATE(%s)", count > 0 ? " AND" : "", dateEnd, search[0]);
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
							if (f.contains(",")) {
								String[] splitF = f.split(",");

								for (String new_f : splitF) {
									new_f = new_f.trim();

									newFilter = String.format("%s,%s '%s'", newFilter, caseSensitive ? " BINARY" : "", new_f);
								}
							} else {
								newFilter = String.format("%s,%s '%s'", newFilter, caseSensitive ? " BINARY" : "", f);
							}
						}
						filter = newFilter.substring(2);
					}
				}
				else {
					String f = map.get(String.format("%s-filter", search.left[0]));
					if (!f.isEmpty())
						filter = String.format("%s'%s'", caseSensitive ? "BINARY " : "", f);
					if (search.left[0].equals(idTable))
						idSearch.add(f);
				}

				if (count == 0 && !filter.isEmpty())
					query += " WHERE";

				if (!filter.isEmpty()) {
					query += String.format("%s %s IN (%s)", count > 0 ? " AND" : "", search.left[0], filter);
					count++;
				}
			}
			if (isDivision()) {
				if (count == 0)
					query += " WHERE";
				
				query += String.format("%s %s IN (@division)", count > 0 ? " AND" : "", division[1]);
			}

			if (setPeriod && hasPeriod())
				query += String.format(" GROUP BY %1$s%2$s ORDER BY %1$s ASC", group, extraGroup);
			
			System.out.println(query);

		   // Table Fields
		    report.getReport(query, idTable, isDivision() ? division : null);

			if (hasColumnDate() && dateProcess != null && hasPeriod() && setPeriod)
				this.setIntervalDate(dateProcess, columnDate, period);

		          	
		     // DESENHAR TABLE
		    //  build.drawTable(build.columns, build.fields, build.fieldObjectValues);
		 
			 // GUARDAR VALORES NA SESS�O		     
		     // SessionUtil.setParam("fieldsLength", columnsName.size()); //Length of Fields
		     // SessionUtil.setParam("fields", build.fields);	//Fields
		     // SessionUtil.setParam("jsonFields", build.jsonFields);	//Fields
		     // SessionUtil.setParam("fieldsObject", build.fieldObjectValues); //Objects
		      		     
			SessionUtil.executeScript("drawTable('#"+jsTable+"', '"+jsTableScroll+"');");

			if (report.lines.isEmpty())
				 return;
			if (report.IDs.isEmpty())
				 report.IDs.addAll(idSearch);
				     
		     model.generateExcelFile(columnsInUse, report.lines, report.secondaryLines, module, report.IDs, dateStart, dateEnd, selectedPeriod, sheetName, fileTitle, isSat, haveTotal, multiSheet);
		     
		 	 SessionUtil.getExternalContext().getSessionMap().put("xlsModel", model); 
		     
			 build.clearBool = false; // BOTÃO DE LIMPAR	 
	      	 build.excelBool = false; // LINK DE DOWNLOAD DO EXCEL
	      	 
	      	 if(isChart) {
	      		 build.chartBool = false;
	      		 chartData(build.chartBool, usePeriod);
	      	 }
	      	 	      		  		    		
	    }
	
		
	   // -------------------------------------------------------------------------------------------------------------------------------------------------
	
	   public void download() {
		   
		   DateTimeApplication dta = new DateTimeApplication();
			
		// MANTER VALORES NA SESSÃO
		//String fileDate = (String) SessionUtil.getExternalContext().getSessionMap().get("datetime");
		//String fileName = (String) SessionUtil.getExternalContext().getSessionMap().get("fileName");
		 
		model = (ExcelTemplate) SessionUtil.getExternalContext().getSessionMap().get("xlsModel");
	
		String file = fileName+"_"+dta.currentDateToExcelFile();
					
		try {
			
			model.download(file);
			
		} catch (IOException e) {	
			
			e.printStackTrace();
		}
		
	}	   


	public void setIntervalDate(Date[] date, String column, String[] period) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		List<Pair<String, List<String[]>>> secondaryLines = new ArrayList<>();
		List<String[]> newList = new ArrayList<>();
		calendar.setTime(date[0]);
		String[] model = new String[report.columnName.size()];
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		boolean sep = column.contains("@");
		int[] col = new int[2];
		Arrays.fill(model, "0");
		int count = 0;
		List<String[]> temp;
		int interval;
		Date step;

		switch (period[1].toUpperCase()) {
			case "MINUTE":
				interval = Calendar.MINUTE;
				break;

			case "HOUR":
				interval = Calendar.HOUR;
				break;
				
			default:
				interval = Calendar.DAY_OF_MONTH;
		}

		if (sep) {
			String[] c = column.split("@");
			col[0] = Integer.parseInt(c[0]);
			col[1] = Integer.parseInt(c[1]);
		} else {
			col[0] = Integer.parseInt(column);
		}

		temp = report.lines;

		do {
			if (count > 0)
				if (report.secondaryLines.size() >= count) {
					temp = report.secondaryLines.get(count - 1).right;
					calendar.setTime(date[0]);
					newList = new ArrayList<>();
				} else
					break;

			for (String[] lines : temp) {
				String d;
	
				if (sep) {
					d = String.format("%s %s", lines[col[0]], lines[col[1]]);
				} else {
					d = lines[col[0]];
				}
	
				Date dateReport = formatter.parse(d);
				
				step = calendar.getTime();
				
				while (step.before(dateReport) && step.before(date[1])) {
					String f = formatter.format(step);
					if (sep) {
						String[] split = f.split(" ");
						model[col[0]] = split[0];
						model[col[1]] = split[1];
					} else
						model[col[0]] = f;
	
					newList.add(model.clone());
					calendar.add(interval, Integer.parseInt(period[0]));
					step = calendar.getTime();
				}
	
				newList.add(lines);
				calendar.add(interval, Integer.parseInt(period[0]));
			}
	
			step = calendar.getTime();
	
			while (step.before(date[1])) {
				String f = formatter.format(step);
				if (sep) {
					String[] split = f.split(" ");
					model[col[0]] = split[0];
					model[col[1]] = split[1];
				} else
					model[col[0]] = f;
	
				newList.add(model.clone());
				calendar.add(interval, Integer.parseInt(period[0]));
				step = calendar.getTime();
			}
	
			if (count > 0) {
				secondaryLines.add(new Pair<String, List<String[]>>(report.secondaryLines.get(count - 1).left, newList));
			} else
				report.lines = newList;

			count++;
		} while (report.secondaryLines != null);

		if (count > 0)
			report.secondaryLines = secondaryLines;
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
				build.chartBool = true;
											
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
			
	public void chartData(boolean chartBool, String period) {
		
		TranslationMethods trm = new TranslationMethods();
				
		String vAxisTitle = "veiculos";
		
		LocalDateTime local =  LocalDateTime.now();
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm");  
	    String formatDateTime = local.format(format);  
	  
	    String title = " - " + trm.periodTranslator(period); // IF IS NOT A SINGLE EQUIPEMENT DO THIS
	      		
		
		String imageName = formatDateTime; //NAME OF FILE WITH TIME
		
		if(!chartBool) {
					 		  	   		
	        // Create a new instance of Gson
	       Gson gson = new Gson();
	    
	        // Converting multidimensional array into JSON	      
	        String jsColumn = gson.toJson(columnsInUse);	
	        
	        String jsData = gson.toJson(report.lines);	
	        	     	        
	        jsData = jsData.toString().replaceAll("\"", "");	        
	        // jsData = jsData.toString().replaceAll("\\(", "\\'");
	        // jsData.toString().replaceAll("\\)", "\\'");
	        jsData = jsData.toString().replaceAll("null", "0");	
	       	        	     
	        //DEBUG
	        System.out.println("Header = " + jsColumn);
	        System.out.println("Data = " + jsData);
	        
	        System.out.println(usePeriod);
	       	        
	        if(period.equals("day"))
	        	SessionUtil.executeScript("reDrawChart("+jsColumn+", "+jsData+", '"+title+"', '"+vAxisTitle+"', '"+ dateFormat +"', '"+imageName+"');");
	        
	        else SessionUtil.executeScript("reDrawChart("+jsColumn+", "+jsData+", '"+title+"','"+vAxisTitle+"', '"+ datetimeFormat +"', '"+imageName+"');");
	        	     	        
		}
     }	
		
	 // --------------------------------------------------------------------------------------------	

}
