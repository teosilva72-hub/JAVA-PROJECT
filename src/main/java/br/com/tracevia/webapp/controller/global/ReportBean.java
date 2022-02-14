package br.com.tracevia.webapp.controller.global;

import java.io.IOException;
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
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.google.gson.Gson;
import com.mysql.cj.conf.ConnectionUrlParser.Pair;

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.global.ReportDAO;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.ReportBuild;
import br.com.tracevia.webapp.model.global.ReportSelection;
import br.com.tracevia.webapp.model.sat.SatTableHeader;
import br.com.tracevia.webapp.model.speed.SpeedReport.Builder;
import br.com.tracevia.webapp.util.ExcelTemplate;
import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.SessionUtil;

@ManagedBean(name="reportBean")
@RequestScoped
public class ReportBean {

	public String table;
	public String idTable;
	public List<String> columnsName = new ArrayList<>(); 
	public List<String> searchParameters,
						searchParametersMS = new ArrayList<>();
		
	private final String dateFormat = "dd/MM/yyyy";
	private final String datetimeFormat = "dd/MM/yyyy HH:mm";
	
	LocaleUtil locale;
	
	// ----------------------------------------------------------------------------------------------------------------

	public String 	fileName, fileTitle, usePeriod, sheetName = "Report";	
	
	public String 	specialName, classSubHeader = "default";	
	
	public String 	jsTable, jsTableScroll, chartTitle, imageName, vAxis;	
	
	public boolean 	isSat = false, haveTotal, multiSheet = true, equipSheetName = false, isChart = false, special = false, headerInfo = false, classHead = false, caseSensitive = false,
			groupId = false;
	
	public String totalType = "standard";
	public String 	module = "default";
	
	// ----------------------------------------------------------------------------------------------------------------

	private String periodColumn;
	private List<String[]> period = new ArrayList<>();
	private String extraGroup = "";
	private List<String> extraSelect;
	private String columnDate;
	private String innerJoin;
	private String useIndex;
	private String orderDate;
	private String[] division;

	private ExcelTemplate model;
	private List<String> columnsInUse = new ArrayList<>(); 
	private List<String> columnsHeader = new ArrayList<>(); 
	private HashMap<String, List<String>> listArgs = new HashMap<>(); 
	private List<String[]> dateSearch = new ArrayList<>();
	private List<Pair<String[], List<String[]>>> filterSearch = new ArrayList<>();
	
	private ReportSelection select;
	private ReportBuild build;
	private SatTableHeader satTab;
	private TranslationMethods tm;
		
	private ReportDAO report;
	public List<Builder> resultList;	

	List<? extends Equipments> listEquips;  
	
	@ManagedProperty("#{language}")
	private LanguageBean language;
				
	// Get	
	
	public LanguageBean getLanguage() {
		return language;
	}

	public void setLanguage(LanguageBean language) {
		this.language = language;
	}
		
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
				
	public List<String> getColumnsHeader() {
		return columnsHeader;
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
						
	  //System.out.println(columns);
		
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
	
	public void setColumnsHeader(String columns) {
	
		for (String col : columns.split(","))
			this.columnsHeader.add(col);		    	   
	}
			
	public void setColumnsInUse(String[] columns) {
		if (columns == null) {
			columnsInUse = new ArrayList<>(columnsName);
			return;
		}
		
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

	public void setSearchParametersMS(String parameter) {
		List<String> searchParametersMS = Arrays.asList(parameter.replace("`", "'").split(";"));
		
		this.searchParametersMS = searchParametersMS;
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
		setFilterSearch(filterSearch, nameColumn, String.format("%s.%s", table, filterSearch), "", multiple, false);
	}
	
	public void setFilterSearch(String filterSearch, String nameColumn, boolean multiple, boolean mandatory) {
		setFilterSearch(filterSearch, nameColumn, String.format("%s.%s", table, filterSearch), "", multiple, mandatory);
	}
	
	public void setFilterSearch(String filterSearch, String nameColumn, String tableWithName) {
		setFilterSearch(filterSearch, nameColumn, tableWithName, "", false, false);
	}

	public void setFilterSearch(String filterSearch, String nameColumn, String tableWithName, String where) {
		setFilterSearch(filterSearch, nameColumn, tableWithName, where, false, false);
	}
	
	public void setFilterSearch(String filterSearch, String nameColumn, String tableWithName, boolean multiple) {
		setFilterSearch(filterSearch, nameColumn, tableWithName, "", multiple, false);
	}

	public void setFilterSearch(String filterSearch, String nameColumn, String tableWithName, boolean multiple, boolean mandatory) {
		setFilterSearch(filterSearch, nameColumn, tableWithName, "", multiple, mandatory);
	}

	public void setFilterSearch(String filterSearch, String nameColumn, String tableWithName, String where, boolean multiple) {
		setFilterSearch(filterSearch, nameColumn, tableWithName, where, multiple, false);
	}

	public void setFilterSearch(String filterSearch, String nameColumn, String tableWithName, String where, boolean multiple, boolean mandatory) { // Esse metodo pode ser mais rapido do que o metodo acima, pois, te permite escolher uma tabela menor sÃ³mente com os campos necessarios
		String[] tableName = tableWithName.split("\\.");
			
		String extra1 = multiple ? "multiple" : "";
		String extra2 = mandatory ? "required" : "";
		
		if (report != null)
			
			try {
				
				if(tableName[1].contains("|"))
					 this.filterSearch.add(new Pair<String[], List<String[]>>(new String[]{filterSearch, nameColumn, extra1, extra2}, report.getOtherElementTable(tableName[0], tableName[1].split("\\|"), where)));
				
				else					
					 this.filterSearch.add(new Pair<String[], List<String[]>>(new String[]{filterSearch, nameColumn, extra1, extra2}, report.getOtherElementTable(tableName[0], tableName[1], where)));
											
			} catch (Exception e) {
				e.printStackTrace();
			}		
	}
	
	public void setTable(String table) {
		this.table = table.trim();
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
	
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
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
	
	public void defineTotalType(String totalType) {
		this.totalType = totalType;
	}
	
	public void defineClassSublHeader(String classSubHeader) {
		this.classSubHeader = classSubHeader;
	}
		
	public void defineSpecialName(String specialName) {
		this.specialName = specialName;
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
	
	public void defineChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}
	
	public void defineImageName(String imageName) {
		this.imageName = imageName;
	}
	
	public void defineAxis(String vAxis) {
		this.vAxis = vAxis;
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
	
	public void isSpecial(boolean special) {
		this.special = special;
	}
	
	public void haveHeaderInfo(boolean headerInfo) {
		this.headerInfo = headerInfo;
	}
	
	public void hasClassHeader(boolean classHead) {
		this.classHead = classHead;
	}
	
	public void defineMultiSheet(boolean multiSheet) {
		this.multiSheet = multiSheet;
	}
	
	public void defineMultiEquipTab(boolean equipSheetName) {
		this.equipSheetName = equipSheetName;
	}
	
	public void groupByID(boolean groupId) {
		this.groupId = groupId;
	}
		
	public void defineCaseSensitive() {
		this.caseSensitive = true;
	}
	
	public void addExtraSelect(String extraSelect) {
		this.extraSelect = Arrays.asList(extraSelect.split(";"));
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
			
	public SatTableHeader getSatTab() {
		return satTab;
	}

	public void setSatTab(SatTableHeader satTab) {
		this.satTab = satTab;
	}
	
	public TranslationMethods getTm() {
		return tm;
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

	public String genPeriodMS(String[] time) {
		switch (time[1].toUpperCase()) {
			case "MINUTE":
				return String.format("CONVERT(DATETIME, CONCAT(FORMAT(%1$s, 'dd/MM/yyyy'), ' ', DATEPART(HOUR, %1$s), ':', FLOOR(DATEPART(MINUTE, %1$s) / %2$s) * %2$s, ':00'), 103)", periodColumn, time[0]);

			case "HOUR":
				return String.format("CONVERT(DATETIME, CONCAT(FORMAT(%1$s, 'dd/MM/yyyy'), ' ', FLOOR(DATEPART(HOUR, %1$s) / %2$s) * %2$s, ':00:00'), 103)", periodColumn, time[0]);
		
			default:
				return periodColumn;
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
		
		locale = new LocaleUtil();
		locale.getResourceBundle(LocaleUtil.LABELS_REPORTS);
			
		// ---------------------------------------------------------------------------
	        	
    	  // Disabled Buttons
    	  build.clearBool = true;
    	  build.excelBool = true;
    	  build.chartBool = true;
    	      	
    	  // UPDATE VIEW LOCALE INTO REPORTS
    	  language.updateViewLocale(Locale.getDefault()); 
    	      	
	 }		
	
	public void initiliazeSatHeader() {
				
		satTab = new SatTableHeader();		
		satTab.initialize();
				
	}
	
	// -------------------------------------------------------------------------------------------------------------------------------------------------
	
	public void createReport() throws Exception {
				
		// Table Fields
		report = new ReportDAO(columnsName);
		List<String> parameters = new ArrayList<>();
		List<String> parametersMS = new ArrayList<>();
		List<String> select = new ArrayList<>();

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
		
		for (String column : searchParametersMS) {
			if (column.contains("$custom")) {
				Pattern pattern = Pattern.compile("^\\w+");
				Matcher alias = pattern.matcher(column.split("@")[1]);
				if (alias.find())
					if (listArgs.containsKey(alias.group(0))) {
						List<String> values = listArgs.get(alias.group(0));
						for (String arg : values) {
							String columns_replace = column.replace(String.format("$custom@%s", alias.group(0)), arg);
	
							parametersMS.add(String.format("%s", columns_replace));
						}
					}
			} else
				parametersMS.add(column);
		}
		
		if (extraSelect != null) {
			for (String column : extraSelect) {
				if (column.contains("$custom")) {
					Pattern pattern = Pattern.compile("^\\w+");
					Matcher alias = pattern.matcher(column.split("@")[1]);
					if (alias.find())
						if (listArgs.containsKey(alias.group(0))) {
							List<String> values = listArgs.get(alias.group(0));
							for (String arg : values) {
								String columns_replace = column.replace(String.format("$custom@%s", alias.group(0)), arg);
		
								select.add(String.format("%s", columns_replace));
							}
						}
				} else
					select.add(column);
			}

			extraSelect = select;
		}

		 searchParameters = parameters;					
		 searchParametersMS = parametersMS;					
	  }

	 // CAMPOS
	
	  public void createFields() throws Exception {
									
		int count = 0;
		boolean setPeriod = false;
		
		Map<String, String> map = SessionUtil.getRequestParameterMap();
		Map<String, String[]> mapArray = SessionUtil.getRequestParameterValuesMap();
		
		String[] columns = mapArray.get("allColumns");
		List<String> columnsTemp = columns != null ? Arrays.asList(columns) : searchParameters;
		String selectedPeriod = (String) map.get("date-period");
		usePeriod = selectedPeriod;
				
		List<String> idSearch = new ArrayList<>();		
				
		String group = "$period",
			   groupMS = "$period";
		String order = "";
		String orderMS = "";
		Date[] dateProcess = null;
		String[] period = null;
		setColumnsInUse(columns);
								
		model = new ExcelTemplate(); // HERE
		
		String dateStart = "", dateEnd = "", equipFilter = "";
		
				
		String query = "SELECT ";
		String queryMS = null;
		
		List<String> equipIDs = new ArrayList<String>();
		
		if (!searchParametersMS.isEmpty())
			queryMS = "SELECT ";
		for (String col : columnsTemp) {
			String column = columns != null ? searchParameters.get(Integer.parseInt(col)) : col;
			String columnMS = null;
			if (queryMS != null)
				columnMS = columns != null ? searchParametersMS.get(Integer.parseInt(col)) : col;

			if (!setPeriod && hasPeriod() && column.contains("$period")) {
				period = selectedPeriod.split(",");
				column = column.replace("$period", genPeriod(period));
				if (columnMS != null)
					columnMS = columnMS.replace("$period", genPeriodMS(period));

				if (column.contains("@")) {
					String[] alias = column.split("@");
					String[] aliasMS = null;
					if (columnMS != null)
						aliasMS = columnMS.split("@");
					query += String.format("%s as %s, ", alias[0], group);
					
					System.out.println("ALIAS: "+alias[0]+" QUERY1: "+query);
										
					if (columnMS != null)
						queryMS += String.format("%s as %s, ", aliasMS[0], groupMS);
					
					if (period[1].toUpperCase().equals("DAY")) {
						group = alias[1];
						order = String.format("STR_TO_DATE(%s, '%%d/%%m/%%Y %%H:%%i:%%s')", alias[1]);
						if (columnMS != null) {
							groupMS = aliasMS[0];
							orderMS = periodColumn;
						}
						
					} else {
						
						order = String.format("STR_TO_DATE(CONCAT(%s, %s), '%%d/%%m/%%Y %%H:%%i:%%s')", alias[1], group);
						group = String.format("%s, %s", alias[1], group);
						
						System.out.println("QUERY2: "+query);
						
						if (columnMS != null) {
							if (columnDate.contains("@")) {
								String[] c = columnDate.split("@");
								String date = searchParametersMS.get(Integer.parseInt(c[0]));
								groupMS = String.format("%s, %s", date, aliasMS[0]);
								orderMS = String.format("%s, %s", date, aliasMS[0]);
							} else {
								String date = searchParametersMS.get(Integer.parseInt(columnDate));
								groupMS = String.format("%s, %s", date, aliasMS[0]);
								orderMS = String.format("%s, %s", date, aliasMS[0]);
							}
						}
					}
				} else {
					query += String.format("%s as %s, ", column, group);
					System.out.println("QUERY3: "+query);
					if (columnMS != null)
						queryMS += String.format("%s as %s, ", columnMS, groupMS);
				}
				selectedPeriod = period[2];
				setPeriod = true;
			} else {
				query += String.format("%s, ", column);
				if (queryMS != null)
					queryMS += String.format("%s, ", columnMS);
			}
		}

		query = String.format("%s FROM %s", query.substring(0, query.length() - 2), table);
		
		if (queryMS != null)
			queryMS = String.format("%s FROM %s", queryMS.substring(0, queryMS.length() - 2), table);

		if (withIndex()) {
			query += String.format(" USE INDEX(%s)", useIndex);
			// if (queryMS != null)
			// 	queryMS += String.format(" WITH INDEX(%s)", useIndex);
		}
		
		if (withInnerJoin()) {
			query += String.format(" INNER JOIN %s", innerJoin);
			if (queryMS != null)
				queryMS += String.format(" INNER JOIN %s", innerJoin);
		}

		if (!dateSearch.isEmpty())
			for (String[] search : dateSearch) {
				dateStart = map.get(String.format("%s-start", search[1]).replaceAll(" ", ""));
				dateEnd = map.get(String.format("%s-end", search[1]).replaceAll(" ", ""));

				if (search[3] != null && !dateStart.isEmpty() && !dateEnd.isEmpty()) {
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(format.parse(dateEnd));
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					Date start = format.parse(dateStart);
					Date end = calendar.getTime();
					dateProcess = new Date[] { start, end };
				}
				if (!dateStart.isEmpty() || !dateEnd.isEmpty()) {
					
					if (count == 0) {
						query += " WHERE";
						if (queryMS != null)
							queryMS += " WHERE";
					}

					query += String.format("%s DATE(%s) BETWEEN STR_TO_DATE('%s', '%%d/%%m/%%Y') AND STR_TO_DATE('%s', '%%d/%%m/%%Y')", count > 0 ? " AND" : "", search[0], dateStart, dateEnd);
					
					if (queryMS != null)
						queryMS += String.format("%s %s BETWEEN CONVERT(DATE, '%s', 103) AND DATEADD(DAY, 1, CONVERT(DATE, '%s', 103))", count > 0 ? " AND" : "", search[0], dateStart, dateEnd);
					count++;
				}
			}
					
		if (!filterSearch.isEmpty())			
			for (Pair<String[], List<String[]>> search : filterSearch) {
				String filter = "";
				if (search.left[2].equals("multiple")) {
					String[] filterArray = mapArray.get(String.format("%s-filter", search.left[1]).replaceAll(" ", ""));
					String newFilter = "";
															
					if (filterArray != null) {						
						for (String f : filterArray) { // HERE
							
							System.out.println(search.left[0]);
							
							if(search.left[0].equals("siteID"))
								equipFilter = search.left[0];
							
							if(search.left[1].equals("Equipamento"))							
							    equipIDs.add(f);
														
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
					String f = map.get(String.format("%s-filter", search.left[1]).replaceAll(" ", ""));
					
					//System.out.println(String.format("%s-filter", search.left[1]));
					if (!f.isEmpty())
						filter = String.format("%s'%s'", caseSensitive ? "BINARY " : "", f);
					if (search.left[0].equals(idTable))
						idSearch.add(f);
				}

				if (count == 0 && !filter.isEmpty()) {
					query += " WHERE";
					if (queryMS != null)
						queryMS += " WHERE";
				}

				if (!filter.isEmpty()) {
					String f = String.format("%s %s IN (%s)", count > 0 ? " AND" : "", search.left[0], filter);
					query += f;
					if (queryMS != null)
						queryMS += f;
					count++;
				}
			}
		
			if (isDivision()) {
				if (count == 0) {
					query += " WHERE";
					if (queryMS != null)
						queryMS += " WHERE";
				}
				
				String f = String.format("%s %s IN (@division)", count > 0 ? " AND" : "", division[1]);
				query += f;
				if (queryMS != null)
					queryMS += f;
			}

			if (setPeriod && hasPeriod()) {
								
				if(equipSheetName)
					group +=", "+equipFilter;
					
				System.out.println(group);
				
				query += String.format(" GROUP BY %s%s ORDER BY %s%s ASC", group, extraGroup, orderDate != null ? orderDate + ", " : "", order);
				System.out.println("QUERY5: "+query);
				if (queryMS != null)
					queryMS += String.format(" GROUP BY %s%s ORDER BY %s%s ASC", groupMS, extraGroup, orderDate != null ? orderDate + ", " : "", orderMS);
			} else if (orderDate != null) {
				String o = String.format(" ORDER BY %s ASC", orderDate);
				query += o;
				if (queryMS != null)
					queryMS += o;
			}

			if (extraSelect != null) {
				query = String.format("SELECT %s FROM (%s) extraselect GROUP BY %s", String.join(",", extraSelect), query, group);
				System.out.println("QUERY6: "+query);
				if (queryMS != null)
					queryMS = String.format("SELECT %s FROM (%s) extraselect GROUP BY %s", String.join(",", extraSelect), queryMS, groupMS);
			}
			
			System.out.println(query);
			  
		    // Table Fields
			report.getReport(query, queryMS, idTable, isDivision() ? division : null);
		    boolean hasValue = true;

			if (hasColumnDate() && dateProcess != null && hasPeriod() && setPeriod)
				hasValue = this.setIntervalDate(dateProcess, columnDate, period, "sat", equipSheetName, equipIDs);
		          										
			// -------------------------------------------------------------------------------------
					
			// CASO NÃO EXISTA VALOR			
			if (report.lines.isEmpty() || !hasValue) {
				SessionUtil.executeScript("alertOptions('#info', '"+locale.getStringKey("$message_reports_record_not_found")+"');");
				
				build.chartBool = true; // BOTÃO DO GRÃFICO	 
							
				if (report.lines.isEmpty()) {
					 SessionUtil.executeScript("drawTable()");					
					 return;
				}				
			}
		
			
			for(String s : equipIDs)
				System.out.println(s);
			
		     	if (report.IDs.isEmpty())
		     		report.IDs.addAll(idSearch);
		
			// -------------------------------------------------------------------------------------
		
			// TABLE DINAMIC HEADER
			
			if(headerInfo) {
				
				if(module.equals("sat")) {
					
					if(specialName.equals("counting-flow"))
						satTab.satHeaderInformation(module, report.IDs);
				}
			 }
			
			// -------------------------------------------------------------------------------------
						  
			SessionUtil.executeScript("drawTable()");
			
			// -------------------------------------------------------------------------------------	
				      		     						
			// if(!special)										
		    //	model.generateExcelFile(columnsInUse, report.lines, report.secondaryLines, module, equipIDs, dateStart, dateEnd, period, sheetName, fileTitle, totalType, isSat, haveTotal, multiSheet, equipSheetName, classSubHeader);
			
			// else generateSpecialFile(model, specialName);
		     
		     SessionUtil.getExternalContext().getSessionMap().put("xlsModel", model); 		        
		    
		    // ------------------------------------------------------------
		    			
		    build.excelBool = false; // LINK DE DOWNLOAD DO EXCEL
		    build.clearBool = false; // LINK DE DOWNLOAD DO EXCEL
		    
			if(isChart && !report.lines.isEmpty() && hasValue) {
					Pair<List<String>, List<String[]>> data = processChartData();
	      	 
		 	    	build.chartBool = false;
	      		    createChartData(build.chartBool, period, data.left, data.right, report.IDs);
	      	    } 
			
	        }
			
	   // -------------------------------------------------------------------------------------------------------------------------------------------------
	
	   public void download() {
		   
		DateTimeApplication dta = new DateTimeApplication();
			
		model = (ExcelTemplate) SessionUtil.getExternalContext().getSessionMap().get("xlsModel");
	
		String file = fileName+"_"+dta.currentDateToExcelFile();
					
		try {
			
			model.download(file);
			
		} catch (IOException e) {	
			
			e.printStackTrace();
		}
		
	}	   

	public boolean setIntervalDate(Date[] date, String column, String[] period, String modulo, boolean isEquipSheeName, List<String> equips) throws ParseException {
			
		Calendar calendar = Calendar.getInstance();
		List<Pair<String, List<String[]>>> secondaryLines = new ArrayList<>();
		List<String[]> newList = new ArrayList<>();
		calendar.setTime(date[0]);
		String[] model = new String[report.columnName.size()];
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		boolean sep = column.contains("@");
		boolean hasLine = !report.lines.isEmpty();
		int[] col = new int[2];
		
		String lastdate = "";
							
		Arrays.fill(model, "0"); // HERE
									
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
		
		String[] eqp = null;
		
		if(isEquipSheeName) {
		
			EquipmentsDAO dao = new EquipmentsDAO();
			eqp = dao.equipmentsName(modulo, equips);
		
		}
				
		do {
			if (count > 0)
				if (report.secondaryLines.size() >= count) {
					temp = report.secondaryLines.get(count - 1).right;
					calendar.setTime(date[0]);
					newList = new ArrayList<>();
					
				} else
					break;

			for (String[] lines : temp) { // HERE
				String d;
	
				if (sep) {
					d = String.format("%s %s", lines[col[0]], lines[col[1]]);
				} else {
					d = lines[col[0]];
				}
				
				Date dateReport;
				
				try {					
					dateReport = formatter.parse(d);
				} catch (ParseException e ) {
					continue;
				}
				
				step = calendar.getTime();
				
				int idx = 0;
											
				while (step.before(dateReport) && step.before(date[1])) {
					String f = formatter.format(step);
															
					if (sep) {
						String[] split = f.split(" ");	
						
						model[col[0]] = split[0];
						model[col[1]] = split[1];	
						
						//if(!lastdate.equals(model[col[0]]))
						//	idx++;
						
						//if(isEquipSheeName)
							//model[2] = eqp[idx];
						
					} else {
						
					//	if(!lastdate.equals(model[col[0]]))
					//		idx++;
						
						model[col[0]] = f;
						
						//if(isEquipSheeName)
						//	model[1] = eqp[idx];
						
					}										
	
					newList.add(model.clone());
					calendar.add(interval, Integer.parseInt(period[0]));
					step = calendar.getTime();
					
					lastdate = model[col[0]];
					
				}
			
				newList.add(lines);
				calendar.add(interval, Integer.parseInt(period[0]));
			}
						 	
			step = calendar.getTime();
			
			int idx = 0;
	
			while (step.before(date[1])) {
				String f = formatter.format(step);				
				if (sep) {
					String[] split = f.split(" ");
					model[col[0]] = split[0];
					model[col[1]] = split[1];
					
					//if(!lastdate.equals(model[col[0]]))
					//	idx++;
					
				//	if(isEquipSheeName)
					//	model[2] = eqp[idx];
					
				} else {
									
					model[col[0]] = f;
					
					//if(!lastdate.equals(model[col[0]]))
					//	idx++;
				
			//	if(isEquipSheeName)
					//model[1] = eqp[idx];
				
				}
	
				newList.add(model.clone());
				calendar.add(interval, Integer.parseInt(period[0]));
				step = calendar.getTime();
				
				lastdate = model[col[0]];
			}
	
			if (count > 0) {
				secondaryLines.add(new Pair<String, List<String[]>>(report.secondaryLines.get(count - 1).left, newList));
			} else
				report.lines = newList;

			count++;
		} while (report.secondaryLines != null);

		if (count > 0)
			report.secondaryLines = secondaryLines;
								
	   //   dta.fillEquipName(listEquips, model, equips, 2, temp.size(), Integer.parseInt(period[0]));
		
		return hasLine;
		
	}
		
	   // -------------------------------------------------------------------------------------------------------------------------------------------------	
		
	   public void resetForm() {
				
				// Limpa valores da sessÃƒÂ£o
				build.resetReportValues();
				
				// Reinicializa valores armazenados nas variÃƒÂ¡veis abaixo
				build = new ReportBuild();
				select = new ReportSelection();
										
				build.excelBool = true;
				build.clearBool = true;
				build.chartBool = true;		
				
				initiliazeSatHeader(); // ONLY HEADER 
				
				SessionUtil.executeScript("window.clear_table = true");
																		
			}	
	 		
	   // -------------------------------------------------------------------------------------------------------------------------------------------------
	  	
			/**
			 * MÃƒÂ©todo para carregar equipamentos disponÃƒÂ­veis para seleÃƒÂ§ÃƒÂ£o
			 * @author Wellington 26/10/2021
			 * @version 1.0
			 * @since 1.0
			 * @param module - modulo que os equipamentosa devem ser carregados
			 */
			public void defineEquipmentsOption(String module) {

				try {

					EquipmentsDAO dao = new EquipmentsDAO();		 
					listEquips = dao.equipmentSelectOptions(module);

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
			 * MÃƒÂ©todo para carregar perÃƒÂ­odos disponÃƒÂ­veis para seleÃƒÂ§ÃƒÂ£o
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
			
	    public void createChartData(boolean chartBool, String[] period, List<String> columns, List<String[]> lines, List<String> ids) {
					
	    TranslationMethods tm = new TranslationMethods();
	    	
		String vAxisTitle = tm.verticalAxisTranslate(vAxis);
				
		LocalDateTime local =  LocalDateTime.now();
		EquipmentsDAO dao = new EquipmentsDAO();
						
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm");  
	    String formatDateTime = local.format(format);  
	    
	    String equipName = "";
	  
	     String title = "";
	      		
		 imageName += "_"+formatDateTime; //NAME OF FILE WITH TIME
		 
		// -------------------------------------------------------------------
		 
		 if(ids.size() == 1) {
			 
			try {
				
				equipName =  dao.equipmentName(module, ids.get(0));				
				title =  chartTitle+ " - " + period[2] + " ("+equipName+")"; 
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		 } else  title =  chartTitle+ " - " + period[2];
		 
		 // -------------------------------------------------------------------
		
		if(!chartBool) {
					 		  	   		
	        // Create a new instance of Gson
	        Gson gson = new Gson();
	    
	        // Converting multidimensional array into JSON	      
	        String jsColumn = gson.toJson(columns);	
	        
	        //String jsData = hAxisTitle;
	        String jsData = "";
	        
	        jsData += gson.toJson(lines);
	        	     	        
	        jsData = jsData.toString().replaceAll("\"", "").replaceAll("null", "0").replaceAll("@aspas", "'");	     
	        
	        //System.out.println(jsColumn);
	        //System.out.println(jsData);
	        	   	       	        
	        if(period[1].toUpperCase().equals("DAY"))
	           SessionUtil.executeScript("reDrawChart("+jsColumn+", "+jsData+", '"+title+"', '"+vAxisTitle+"', '"+ dateFormat +"', '"+imageName+"');");
	        
	        else SessionUtil.executeScript("reDrawChart("+jsColumn+", "+jsData+", '"+title+"','"+vAxisTitle+"', '"+ datetimeFormat +"', '"+imageName+"');");
	       		
		}
     }	
		
	 // --------------------------------------------------------------------------------------------	
	    
	   public Pair<List<String>, List<String[]>> processChartData() throws ParseException{
		   
		    List<String[]> array = new ArrayList<>();
		    List<String> column = new ArrayList<>(columnsInUse);
	    	
		  	boolean sep = columnDate.contains("@");
	    	int[] col = new int[2];
	    	
	    	if (sep) {
				String[] c = columnDate.split("@");
				col[0] = Integer.parseInt(c[0]);
				col[1] = Integer.parseInt(c[1]);
			} else {
				col[0] = Integer.parseInt(columnDate);
			}
	    	
	    	if (sep) {
	    		int big = col[0] > col[1] ? 0 : 1;
	    		
	    		column.remove(col[big]);	
	    		column.remove(col[big ^ 1]);
	    			    		
									
			} else {
				column.remove(col[0]);
			}
	    	
	    	column.add(0, "DATE");
	    			    	
	    for(String[] sa : report.lines) {
	    	String date = "new Date(@aspas%s@aspas)";
	    	int c = 1;
	    	String[] newArray = new String[sa.length - (sep ? 1 : 0)];
	    	
	    	for(int i = 0; i < sa.length; i++) {
	    		if (sep) {		    			
	    			if (col[1] == i || col[0] == i) 
	    				continue;
	    		} else
	    			if (col[0] == i)
	    				continue;
	    		
	    		newArray[c] = sa[i];
	    		c++;
	    	}
	    	
	    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    	SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	Date d;
	    	
	    	if (sep)
	    		d = formatter.parse(String.format("%s %s", sa[col[0]], sa[col[1]]));
	    	else
    			d = formatter.parse(sa[col[0]]);
	    	date = String.format(date, formatter2.format(d));
	    	
	    	newArray[0] = date;
	    	array.add(newArray);
	    }
	    
	    return new Pair<>(column, array);
	    	
	  }
	    
	    
	// --------------------------------------------------------------------------------------------	
	   
	   public void generateSpecialFile(ExcelTemplate model, String name) throws Exception {
		   		   
		   switch(name) {
		   			   
		   	case "counting-flow":  model.generateCountFlow(columnsInUse, report.lines, sheetName, satTab);
		   
		   }
	   }
	   
	// --------------------------------------------------------------------------------------------	
	   
	   
		
}
