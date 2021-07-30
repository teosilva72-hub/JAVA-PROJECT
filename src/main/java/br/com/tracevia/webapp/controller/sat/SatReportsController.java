package br.com.tracevia.webapp.controller.sat;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import com.google.gson.Gson;

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.global.GlobalReportsDAO;
import br.com.tracevia.webapp.dao.sat.SatQueriesModels;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.ExcelModels;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.ColumnModel;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.global.VBV;
import br.com.tracevia.webapp.model.sat.SAT;
import br.com.tracevia.webapp.model.sat.SatReports;
import br.com.tracevia.webapp.model.sat.SatReports.Builder;
import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.QueriesReportsModels;

@ManagedBean(name="satReportsBean")
@RequestScoped
public class SatReportsController {

	private SatReports satReport;
	private List<SelectItem> equipments;  
	private List<SelectItem> vehicles;  
	private List<SelectItem> vehiclesCCR;  
	private List<SelectItem> periods;  
	private List<SelectItem> months;  
	private List<SelectItem> years;	
	private List<SelectItem> classes;  
	private List<SelectItem> axles;  

	private List<Builder> resultList;	
	private List<VBV> resultVBV;
	private List<String> header;  
	private List<ColumnModel> columns;  
	List<? extends Equipments> listSats;  
	
	private final String dateFormat = "dd/MM/yyyy";
	private final String datetimeFormat = "dd/MM/yyyy HH:mm";

	//Locale Docs
	LocaleUtil localeLabel, localeCalendar, localeDir, localeSat;  

	int periodRange, range, daysInMonth, daysCount; 
	
	String jsTableId, jsTableScrollHeight;
 
	// Varivel que recebe o nmero de registros esperados para uma consula SQL (de acordo com perodos)
	private static int numRegisters;	

	// Varivel que recebe o nmero de campos de uma consulta SQL
	private static int fieldsNumber;	
	
	// Varivel que recebe o nmero de campos de uma consulta SQL
	private static int fieldsIndex;

	String[] fields, jsonFields, fieldObjectValues, fieldsAux, fieldObjAux; //Nome dos campos // Valores de cada campo -> Atribuidos a variavis do modelo  

	String[][] resultQuery, jsonArray; 
	
	private String jsColumn, jsData, chartTitle, imageName;
	
	Gson gson;

	String procedure, query, queryCount, module, fileName, currentDate, direction1, direction2, directionLabel1, directionLabel2, 
	directionValue1, directionValue2, equipId; 
	
	//Dates
	String start, end;

	BufferedWriter writer;  
	ByteArrayOutputStream byteWriter; 

	EquipmentsDAO equipDAO;
	ExcelModels model;
		
	String displayEquipInfo, displayDirection1, displayDirection2;

	private boolean clearBool, excelBool, chartBool;

	public SatReports getSatReport() {
		return satReport;
	}

	public void setSatReport(SatReports satReport) {
		this.satReport = satReport;
	}

	public List<SelectItem> getEquipments() {
		return equipments;
	}

	public List<SelectItem> getVehicles() {
		return vehicles;
	}

	public List<SelectItem> getVehiclesCCR() {
		return vehiclesCCR;
	}

	public List<SelectItem> getPeriods() {
		return periods;
	}	

	public List<Builder> getResultList() {
		return resultList;
	}

	public List<String> getHeader() {
		return header;
	}

	public List<ColumnModel> getColumns() {
		return columns;
	}

	public List<SelectItem> getMonths() {
		return months;
	}

	public List<SelectItem> getYears() {
		return years;
	}

	public List<SelectItem> getPeriodsFlow() {
		return periods;
	}	

	public List<SelectItem> getClasses() {
		return classes;
	}

	public List<SelectItem> getAxles() {
		return axles;
	}

	public List<VBV> getResultVBV() {
		return resultVBV;
	}

	public String getDirectionLabel1() {
		return directionLabel1;
	}

	public void setDirectionLabel1(String directionLabel1) {
		this.directionLabel1 = directionLabel1;
	}

	public String getDirectionLabel2() {
		return directionLabel2;
	}

	public void setDirectionLabel2(String directionLabel2) {
		this.directionLabel2 = directionLabel2;
	}

	public String getDirectionValue1() {
		return directionValue1;
	}

	public void setDirectionValue1(String directionValue1) {
		this.directionValue1 = directionValue1;
	}

	public String getDirectionValue2() {
		return directionValue2;
	}

	public void setDirectionValue2(String directionValue2) {
		this.directionValue2 = directionValue2;
	}

	public String getEquipId() {
		return equipId;
	}

	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}

	public boolean isClearBool() {
		return clearBool;
	}

	public void setClearBool(boolean clearBool) {
		this.clearBool = clearBool;
	}

	public boolean isExcelBool() {
		return excelBool;
	}

	public void setExcelBool(boolean excelBool) {
		this.excelBool = excelBool;
	}
		
	public boolean isChartBool() {
		return chartBool;
	}

	public void setChartBool(boolean chartBool) {
		this.chartBool = chartBool;
	}

	public static int getNumRegisters() {
		return numRegisters;
	}

	public static void setNumRegisters(int numRegisters) {
		SatReportsController.numRegisters = numRegisters;
	}

	public static int getFieldsNumber() {
		return fieldsNumber;
	}

	public static void setFieldsNumber(int fieldsNumber) {
		SatReportsController.fieldsNumber = fieldsNumber;
	}	
	
	public String getDisplayEquipInfo() {
		return displayEquipInfo;
	}

	public void setDisplayEquipInfo(String displayEquipInfo) {
		this.displayEquipInfo = displayEquipInfo;
	}
	
	
	public String getDisplayDirection1() {
		return displayDirection1;
	}

	public void setDisplayDirection1(String displayDirection1) {
		this.displayDirection1 = displayDirection1;
	}

	public String getDisplayDirection2() {
		return displayDirection2;
	}

	public void setDisplayDirection2(String displayDirection2) {
		this.displayDirection2 = displayDirection2;
	}
	 
	public String getJsColumn() {
		return jsColumn;
	}

	public void setJsColumn(String jsColumn) {
		this.jsColumn = jsColumn;
	}

	public String getJsData() {
		return jsData;
	}

	public void setJsData(String jsData) {
		this.jsData = jsData;
	}
	
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
		
	public static int getFieldsIndex() {
		return fieldsIndex;
	}

	public static void setFieldsIndex(int fieldsIndex) {
		SatReportsController.fieldsIndex = fieldsIndex;
	}
	
	 ///////////////////////////////////
    //CONSTRUCTOR
    ///////////////////////////////// 


	@PostConstruct
	public void initialize() {

		localeLabel = new LocaleUtil();	
		localeLabel.getResourceBundle(LocaleUtil.LABELS_SAT);

		localeCalendar = new LocaleUtil();	
		localeCalendar.getResourceBundle(LocaleUtil.LABELS_CALENDAR);

		localeDir = new LocaleUtil();	
		localeDir.getResourceBundle(LocaleUtil.LABELS_DIRECTIONS);
		
		localeSat = new LocaleUtil();
		localeSat.getResourceBundle(LocaleUtil.MESSAGES_SAT);

		//ViewLabels	
		directionLabel1 = localeDir.getStringKey("directions_select_label1");
		directionLabel2 = localeDir.getStringKey("directions_select_label2");
		directionValue1 = localeDir.getStringKey("directions_select_value1");
		directionValue2 = localeDir.getStringKey("directions_select_value2");

		/* EQUIPMENTS SELECTION */	
		satReport = new SatReports();	
		equipments = new ArrayList<SelectItem>();		

		listSats = new ArrayList<SAT>();  

		try {

			EquipmentsDAO dao = new EquipmentsDAO();		 
			listSats = dao.EquipmentSelectOptions("sat");

			satReport.equipments = new String[listSats.size()];

		} catch (Exception e1) {			
			e1.printStackTrace();
		}

		for (Equipments e : listSats) {
			SelectItem s = new SelectItem();

			s.setValue(e.getEquip_id());
			s.setLabel(e.getNome());
			equipments.add(s);				
		}

		/* VEHICLES SELECTION */		
		vehicles = new ArrayList<SelectItem>();		
		vehicles.add(new SelectItem(1, localeLabel.getStringKey("sat_reports_select_vehicles_light")));   
		vehicles.add(new SelectItem(2, localeLabel.getStringKey("sat_reports_select_vehicles_motorcycle")));  
		vehicles.add(new SelectItem(3, localeLabel.getStringKey("sat_reports_select_vehicles_heavy")));  
		
		/* VEHICLES CCR SELECTION */		
		vehiclesCCR = new ArrayList<SelectItem>();	
		vehiclesCCR.add(new SelectItem(1, localeLabel.getStringKey("sat_reports_select_vehicles_light")));   
		vehiclesCCR.add(new SelectItem(2, localeLabel.getStringKey("sat_reports_select_vehicles_motorcycle")));  
		vehiclesCCR.add(new SelectItem(3, localeLabel.getStringKey("sat_reports_select_vehicles_small_heavy")));  
		vehiclesCCR.add(new SelectItem(4, localeLabel.getStringKey("sat_reports_select_vehicles_long_heavy")));  
		vehiclesCCR.add(new SelectItem(5, localeLabel.getStringKey("sat_reports_select_vehicles_bus")));  

		/* YEARS */
		years = new ArrayList<SelectItem>();			
		for(int year = 2015; year < 2023; year++)   
			years.add(new SelectItem(year, String.valueOf(year)));  

		/* MOTNHS */
		months = new ArrayList<SelectItem>();		
		months.add(new SelectItem(1, localeCalendar.getStringKey("january")));  
		months.add(new SelectItem(2, localeCalendar.getStringKey("february")));  
		months.add(new SelectItem(3, localeCalendar.getStringKey("march")));  
		months.add(new SelectItem(4, localeCalendar.getStringKey("april")));  
		months.add(new SelectItem(5, localeCalendar.getStringKey("may")));
		months.add(new SelectItem(6, localeCalendar.getStringKey("june")));
		months.add(new SelectItem(7, localeCalendar.getStringKey("july")));
		months.add(new SelectItem(8, localeCalendar.getStringKey("august")));
		months.add(new SelectItem(9, localeCalendar.getStringKey("september")));
		months.add(new SelectItem(10, localeCalendar.getStringKey("october")));
		months.add(new SelectItem(11, localeCalendar.getStringKey("november")));
		months.add(new SelectItem(12, localeCalendar.getStringKey("december")));

		/* PERIODS */		
		periods = new ArrayList<SelectItem>();			
		periods.add(new SelectItem("05 minutes", localeLabel.getStringKey("sat_reports_select_periods_five_minutes")));
		periods.add(new SelectItem("06 minutes", localeLabel.getStringKey("sat_reports_select_periods_six_minutes")));
		periods.add(new SelectItem("10 minutes", localeLabel.getStringKey("sat_reports_select_periods_teen_minutes")));
		periods.add(new SelectItem("15 minutes", localeLabel.getStringKey("sat_reports_select_periods_fifteen_minutes")));
		periods.add(new SelectItem("30 minutes", localeLabel.getStringKey("sat_reports_select_periods_thirty_minutes")));  
		periods.add(new SelectItem("01 hour", localeLabel.getStringKey("sat_reports_select_periods_one_hour")));
		periods.add(new SelectItem("06 hours", localeLabel.getStringKey("sat_reports_select_periods_six_hours")));
		periods.add(new SelectItem("24 hours", localeLabel.getStringKey("sat_reports_select_periods_twenty_four_hours")));

		/* CLASSES */		
		classes = new ArrayList<SelectItem>();	
		classes.add(new SelectItem("1", localeLabel.getStringKey("sat_reports_select_class_light")));
		classes.add(new SelectItem("9", localeLabel.getStringKey("sat_reports_select_class_motorcycle")));
		classes.add(new SelectItem("5", localeLabel.getStringKey("sat_reports_select_class_trailer")));
		classes.add(new SelectItem("3", localeLabel.getStringKey("sat_reports_select_class_semi_trailer")));
		classes.add(new SelectItem("2", localeLabel.getStringKey("sat_reports_select_class_heavy_2_axles")));
		classes.add(new SelectItem("4", localeLabel.getStringKey("sat_reports_select_class_heavy_3_axles")));
		classes.add(new SelectItem("6", localeLabel.getStringKey("sat_reports_select_class_heavy_4_axles")));
		classes.add(new SelectItem("7", localeLabel.getStringKey("sat_reports_select_class_heavy_5_axles")));
		classes.add(new SelectItem("8", localeLabel.getStringKey("sat_reports_select_class_heavy_6_axles")));
		classes.add(new SelectItem("10", localeLabel.getStringKey("sat_reports_select_class_heavy_7_axles")));
		classes.add(new SelectItem("11", localeLabel.getStringKey("sat_reports_select_class_heavy_8_axles")));
		classes.add(new SelectItem("E9", localeLabel.getStringKey("sat_reports_select_class_heavy_9_axles")));
		classes.add(new SelectItem("10N", localeLabel.getStringKey("sat_reports_select_class_heavy_10_axles")));


		/* AXLES */		
		axles = new ArrayList<SelectItem>();	
		axles.add(new SelectItem("2", localeLabel.getStringKey("sat_reports_select_axles_2")));
		axles.add(new SelectItem("3", localeLabel.getStringKey("sat_reports_select_axles_3")));
		axles.add(new SelectItem("4", localeLabel.getStringKey("sat_reports_select_axles_4")));
		axles.add(new SelectItem("5", localeLabel.getStringKey("sat_reports_select_axles_5")));
		axles.add(new SelectItem("6", localeLabel.getStringKey("sat_reports_select_axles_6")));
		axles.add(new SelectItem("7", localeLabel.getStringKey("sat_reports_select_axles_7")));
		axles.add(new SelectItem("8", localeLabel.getStringKey("sat_reports_select_axles_8")));
		axles.add(new SelectItem("9", localeLabel.getStringKey("sat_reports_select_axles_9")));
		axles.add(new SelectItem("10", localeLabel.getStringKey("sat_reports_select_axles_10")));

		//module
		module = "sat";		

		//Disabled
		clearBool = true;
		excelBool = true;
		chartBool = true;
		
		displayDirection1 = localeLabel.getStringKey("sat_reports_count_flow_sat_dir1");
		displayDirection2 = localeLabel.getStringKey("sat_reports_count_flow_sat_dir2");
		displayEquipInfo = localeLabel.getStringKey("sat_reports_count_flow_sat_desc");		
		
		jsColumn = "";
		jsData = "";	
						
	}
	
	///////////////////////////////////
	//CREATE REPORTS
	/////////////////////////////////  

	/**********************************************************************************************************/
	
	//////DESENHAR TABLES 	

	/**
	 * Mtodo para criar os headers
	 * @param field - headers
	 * @param objectValue - Valores de cada header estanciados em objetos
	 */
	public void drawTable(String[] field, String[] objectValue) {

		columns = new ArrayList<ColumnModel>();

		for(int i = 0; i < field.length; i++)
			columns.add(new ColumnModel(field[i], objectValue[i]));		

	}

	
    //////DESENHAR TABLES 	

	/**********************************************************************************************************/

	//// CRIAR TABLE HEADERS
	
	// CRIAR CAMPOS PARA OS HEADERS
	// CRIAR CAMPOS PARA TABELA FRONT-END
	// FIELDS -> HEADERS
	// FIELDSOBJECTVALUES -> VALUES		

	public void CreateFields(String type) {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		/**** CONTAGEM VECULOS ****/			
		if(type.equals("1")) {

			// Table fields
			fields = new String[] {localeLabel.getStringKey("sat_reports_general_date"), localeLabel.getStringKey("sat_reports_general_datetime"), listSats.get(0).getNome(), localeLabel.getStringKey("sat_reports_general_total")};
			
			// Table Objects
			fieldObjectValues = new String[] { "date", "dateTime", "eqp1", "total"};
			
			//JSON chart fields
			jsonFields = new String[] {localeLabel.getStringKey("sat_reports_chart_haxis"), localeLabel.getStringKey("sat_reports_general_datetime"), listSats.get(0).getNome(), localeLabel.getStringKey("sat_reports_general_total")};

			//JSON chart title and subtitle
			chartTitle = localeLabel.getStringKey("sat_reports_chart_title_count");	
			imageName = localeLabel.getStringKey("sat_reports_chart_file_name_count");	
			
		}

		/**** CONTAGEM DE VEICULOS POR PERODO ****/
		if(type.equals("2")) {

			// Table fields
			fields = new String[] {localeLabel.getStringKey("sat_reports_general_date"), localeLabel.getStringKey("sat_reports_general_datetime"), 
					localeLabel.getStringKey("sat_reports_count_flow_lightVehicles"), localeLabel.getStringKey("sat_reports_count_flow_motorcycleVehicles"), 
					localeLabel.getStringKey("sat_reports_count_flow_heavyVehicles"), localeLabel.getStringKey("sat_reports_count_flow_lightVehicles"),  
					localeLabel.getStringKey("sat_reports_count_flow_motorcycleVehicles"),	localeLabel.getStringKey("sat_reports_count_flow_heavyVehicles"), 
					localeLabel.getStringKey("sat_reports_general_total")};

			// Table Objects
			fieldObjectValues = new String[] { "date", "dateTime", "lightDir1", "motoDir1", "heavyDir1",
					"lightDir2", "motoDir2", "heavyDir2", "total"};
			
			//JSON chart fields
			jsonFields = new String[] {localeLabel.getStringKey("sat_reports_chart_haxis"), localeLabel.getStringKey("sat_reports_count_flow_lightVehicles"),
					localeLabel.getStringKey("sat_reports_count_flow_motorcycleVehicles"), localeLabel.getStringKey("sat_reports_count_flow_heavyVehicles"),
					localeLabel.getStringKey("sat_reports_count_flow_lightVehicles"), localeLabel.getStringKey("sat_reports_count_flow_motorcycleVehicles"),	
					localeLabel.getStringKey("sat_reports_count_flow_heavyVehicles"), localeLabel.getStringKey("sat_reports_general_total")};
						
			//JSON chart title and subtitle
			chartTitle = localeLabel.getStringKey("sat_reports_chart_title_count_period");	
			imageName = localeLabel.getStringKey("sat_reports_chart_file_name_name_count_period");	

		}

		/**** FLUXO MENSAL  ****/
		if(type.equals("3")) {

			// Table fields
			fields = new String[] {localeLabel.getStringKey("sat_reports_general_date"), localeLabel.getStringKey("sat_reports_general_datetime"), 
					localeLabel.getStringKey("sat_reports_monthly_flow_lightVehicles"), localeLabel.getStringKey("sat_reports_monthly_flow_commercials"), localeLabel.getStringKey("sat_reports_monthly_flow_motoVehicles"),
					localeLabel.getStringKey("sat_reports_monthly_flow_lightVehicles"), localeLabel.getStringKey("sat_reports_monthly_flow_motoVehicles"),
					localeLabel.getStringKey("sat_reports_monthly_flow_commercials"), localeLabel.getStringKey("sat_reports_monthly_flow_dir1"), localeLabel.getStringKey("sat_reports_monthly_flow_dir2")};

			// Table Objects
			fieldObjectValues = new String[] { "date", "dateTime", "lightDir1", "heavyDir1", "motoDir1", "lightDir2", "heavyDir2", "motoDir2", "speedValue1", "speedValue2"};
			
			//JSON chart fields
			jsonFields = new String[] {localeLabel.getStringKey("sat_reports_chart_haxis"), localeLabel.getStringKey("sat_reports_monthly_flow_lightVehicles"), 
					localeLabel.getStringKey("sat_reports_monthly_flow_commercials"), localeLabel.getStringKey("sat_reports_monthly_flow_motoVehicles"),
			        localeLabel.getStringKey("sat_reports_monthly_flow_lightVehicles"), localeLabel.getStringKey("sat_reports_monthly_flow_motoVehicles"),
				    localeLabel.getStringKey("sat_reports_monthly_flow_commercials"), localeLabel.getStringKey("sat_reports_monthly_flow_dir1"), localeLabel.getStringKey("sat_reports_monthly_flow_dir2")};
			
			//JSON chart title and subtitle
			chartTitle = localeLabel.getStringKey("sat_reports_chart_title_monthly_flow");
			imageName = localeLabel.getStringKey("sat_reports_chart_file_name_monthly_flow");	
		}

		/**** FLUXO PERODO  ****/
		if(type.equals("4")) {

			// Table fields
			fields = new String[] {localeLabel.getStringKey("sat_reports_general_date"), localeLabel.getStringKey("sat_reports_general_datetime"), localeLabel.getStringKey("sat_reports_general_equipment"),
					localeLabel.getStringKey("sat_reports_period_flow_lightVehicles"), 	   
					localeLabel.getStringKey("sat_reports_period_flow_commVehicles"), localeLabel.getStringKey("sat_reports_period_flow_motoVehicles"), 
					localeLabel.getStringKey("sat_reports_general_total"), localeLabel.getStringKey("sat_reports_period_flow_speed_abbr"),
					localeLabel.getStringKey("sat_reports_period_flow_lightVehicles"), 	   
					localeLabel.getStringKey("sat_reports_period_flow_commVehicles"), localeLabel.getStringKey("sat_reports_period_flow_motoVehicles"), 
					localeLabel.getStringKey("sat_reports_general_total"), localeLabel.getStringKey("sat_reports_period_flow_speed_abbr")};

			// Table Objects
			fieldObjectValues = new String[] {"date", "dateTime", "equipment", "motoDir1", "lightDir1", "heavyDir1", "total1", "speedValue1", "motoDir2", "lightDir2", "heavyDir2", "total2", "speedValue2"};
			
			//JSON chart fields
			jsonFields = new String[] {localeLabel.getStringKey("sat_reports_chart_haxis"), localeLabel.getStringKey("sat_reports_period_flow_lightVehicles"), 	   
					localeLabel.getStringKey("sat_reports_period_flow_commVehicles"), localeLabel.getStringKey("sat_reports_period_flow_motoVehicles"), 
					localeLabel.getStringKey("sat_reports_general_total"), localeLabel.getStringKey("sat_reports_period_flow_speed_abbr"),
					localeLabel.getStringKey("sat_reports_period_flow_lightVehicles"), 	   
					localeLabel.getStringKey("sat_reports_period_flow_commVehicles"), localeLabel.getStringKey("sat_reports_period_flow_motoVehicles"), 
					localeLabel.getStringKey("sat_reports_general_total"), localeLabel.getStringKey("sat_reports_period_flow_speed_abbr")};
			
			//JSON chart title and subtitle
			chartTitle = localeLabel.getStringKey("sat_reports_chart_title_period_flow");
			imageName = localeLabel.getStringKey("sat_reports_chart_file_name_period_flow");	
		}

		/**** PESAGEM ****/
		if(type.equals("5")) {

			// Table fields
			fields = new String[] {localeLabel.getStringKey("sat_reports_general_date"), localeLabel.getStringKey("sat_reports_general_datetime"),
					localeLabel.getStringKey("sat_reports_class_light"), localeLabel.getStringKey("sat_reports_class_motorcycle"), 	   
					localeLabel.getStringKey("sat_reports_class_trailer"), localeLabel.getStringKey("sat_reports_select_class_semi_trailer"), 
					localeLabel.getStringKey("sat_reports_class_heavy_2_axles"), localeLabel.getStringKey("sat_reports_class_heavy_3_axles"),
					localeLabel.getStringKey("sat_reports_class_heavy_4_axles"), localeLabel.getStringKey("sat_reports_class_heavy_5_axles"), 	   
					localeLabel.getStringKey("sat_reports_class_heavy_6_axles"), localeLabel.getStringKey("sat_reports_class_heavy_7_axles"), 
					localeLabel.getStringKey("sat_reports_class_heavy_8_axles"), localeLabel.getStringKey("sat_reports_class_heavy_9_axles"),
					localeLabel.getStringKey("sat_reports_class_heavy_10_axles")};

			// Table Objects
			fieldObjectValues = new String[] {"date", "dateTime", "class1", "class2", "class3", "class4", "class5", "class6", "class7", "class8", "class9", "class10", "class11", "class12", "class13"};   

			//JSON chart fields
			jsonFields = new String[] {localeLabel.getStringKey("sat_reports_chart_haxis"),
					localeLabel.getStringKey("sat_reports_class_light"), localeLabel.getStringKey("sat_reports_class_motorcycle"), 	   
					localeLabel.getStringKey("sat_reports_class_trailer"), localeLabel.getStringKey("sat_reports_select_class_semi_trailer"), 
					localeLabel.getStringKey("sat_reports_class_heavy_2_axles"), localeLabel.getStringKey("sat_reports_class_heavy_3_axles"),
					localeLabel.getStringKey("sat_reports_class_heavy_4_axles"), localeLabel.getStringKey("sat_reports_class_heavy_5_axles"), 	   
					localeLabel.getStringKey("sat_reports_class_heavy_6_axles"), localeLabel.getStringKey("sat_reports_class_heavy_7_axles"), 
					localeLabel.getStringKey("sat_reports_class_heavy_8_axles"), localeLabel.getStringKey("sat_reports_class_heavy_9_axles"),
					localeLabel.getStringKey("sat_reports_class_heavy_10_axles")};
						
			//JSON chart title and subtitle
			chartTitle = localeLabel.getStringKey("sat_reports_chart_title_weighing");	
			imageName = localeLabel.getStringKey("sat_reports_chart_file_name_weighing");	
		} 

		/**** CLASS TYPE  ****/
		if(type.equals("6")) {

			// Table fields
			fields = new String[] {localeLabel.getStringKey("sat_reports_general_date"), localeLabel.getStringKey("sat_reports_general_datetime"),
					localeLabel.getStringKey("sat_reports_class_light"), localeLabel.getStringKey("sat_reports_class_motorcycle"), 	   
					localeLabel.getStringKey("sat_reports_class_trailer"), localeLabel.getStringKey("sat_reports_class_semi_trailer"), 
					localeLabel.getStringKey("sat_reports_class_heavy_2_axles"), localeLabel.getStringKey("sat_reports_class_heavy_3_axles"),
					localeLabel.getStringKey("sat_reports_class_heavy_4_axles"), localeLabel.getStringKey("sat_reports_class_heavy_5_axles"), 	   
					localeLabel.getStringKey("sat_reports_class_heavy_6_axles"), localeLabel.getStringKey("sat_reports_class_heavy_7_axles"), 
					localeLabel.getStringKey("sat_reports_class_heavy_8_axles"), localeLabel.getStringKey("sat_reports_class_heavy_9_axles"),
					localeLabel.getStringKey("sat_reports_class_heavy_10_axles"), localeLabel.getStringKey("sat_reports_general_total") };

			// Table Objects
			fieldObjectValues = new String[] {"date", "dateTime", "class1", "class2", "class3", "class4", "class5", "class6", "class7", "class8", "class9", "class10", "class11", "class12", "class13", "total"};

			//JSON chart fields
			jsonFields = new String[] {localeLabel.getStringKey("sat_reports_chart_haxis"),
				localeLabel.getStringKey("sat_reports_class_light"), localeLabel.getStringKey("sat_reports_class_motorcycle"), 	   
				localeLabel.getStringKey("sat_reports_class_trailer"), localeLabel.getStringKey("sat_reports_class_semi_trailer"), 
				localeLabel.getStringKey("sat_reports_class_heavy_2_axles"), localeLabel.getStringKey("sat_reports_class_heavy_3_axles"),
				localeLabel.getStringKey("sat_reports_class_heavy_4_axles"), localeLabel.getStringKey("sat_reports_class_heavy_5_axles"), 	   
				localeLabel.getStringKey("sat_reports_class_heavy_6_axles"), localeLabel.getStringKey("sat_reports_class_heavy_7_axles"), 
				localeLabel.getStringKey("sat_reports_class_heavy_8_axles"), localeLabel.getStringKey("sat_reports_class_heavy_9_axles"),
				localeLabel.getStringKey("sat_reports_class_heavy_10_axles"), localeLabel.getStringKey("sat_reports_general_total") };
						
			//JSON chart title and subtitle
			chartTitle = localeLabel.getStringKey("sat_reports_chart_title_class_type");		
			imageName = localeLabel.getStringKey("sat_reports_chart_file_name_class_type");	
			
		}

		/**** AXLE TYPE ****/
		if(type.equals("7")) {

			// Table fields
			fields = new String[] {localeLabel.getStringKey("sat_reports_general_date"), localeLabel.getStringKey("sat_reports_general_datetime"),
					localeLabel.getStringKey("sat_reports_axles_2"), localeLabel.getStringKey("sat_reports_axles_3"),
					localeLabel.getStringKey("sat_reports_axles_4"), localeLabel.getStringKey("sat_reports_axles_5"), 	   
					localeLabel.getStringKey("sat_reports_axles_6"), localeLabel.getStringKey("sat_reports_axles_7"), 
					localeLabel.getStringKey("sat_reports_axles_8"), localeLabel.getStringKey("sat_reports_axles_9"),
					localeLabel.getStringKey("sat_reports_axles_10"), localeLabel.getStringKey("sat_reports_general_total") };

			// Table Objects
			fieldObjectValues = new String[] {"date", "dateTime", "axles_1", "axles_2", "axles_3", "axles_4", "axles_5", "axles_6", "axles_7", "axles_8", "axles_9", "total"};

			//JSON chart fields
			jsonFields = new String[] {localeLabel.getStringKey("sat_reports_chart_haxis"),
					localeLabel.getStringKey("sat_reports_axles_2"), localeLabel.getStringKey("sat_reports_axles_3"),
					localeLabel.getStringKey("sat_reports_axles_4"), localeLabel.getStringKey("sat_reports_axles_5"), 	   
					localeLabel.getStringKey("sat_reports_axles_6"), localeLabel.getStringKey("sat_reports_axles_7"), 
					localeLabel.getStringKey("sat_reports_axles_8"), localeLabel.getStringKey("sat_reports_axles_9"),
					localeLabel.getStringKey("sat_reports_axles_10"), localeLabel.getStringKey("sat_reports_general_total") };
						
			//JSON chart title and subtitle
			chartTitle = localeLabel.getStringKey("sat_reports_chart_title_axle_type");	
			imageName = localeLabel.getStringKey("sat_reports_chart_file_name_axle_type");	
			
		}

		/**** SPEED ****/
		if(type.equals("8")) {

			// Table fields
			fields = new String[] {localeLabel.getStringKey("sat_reports_general_date"), localeLabel.getStringKey("sat_reports_general_datetime"),
					localeLabel.getStringKey("sat_reports_general_equipment"), localeLabel.getStringKey("sat_reports_speed_50km"), 
					localeLabel.getStringKey("sat_reports_speed_70km"),	localeLabel.getStringKey("sat_reports_speed_90km"), 
					localeLabel.getStringKey("sat_reports_speed_120km"), localeLabel.getStringKey("sat_reports_speed_150km"), 
					localeLabel.getStringKey("sat_reports_speed_150km_bigger"), localeLabel.getStringKey("sat_reports_general_total") };
			
			// Table Objects
			fieldObjectValues = new String[] {"date", "dateTime", "equipment", "speed50km", "speed70km", "speed90km", "speed120km", "speed150km", "speed150km_bigger", "total"};
					
			//JSON chart fields
		/*	jsonFields = new String[] {localeLabel.getStringKey("sat_reports_chart_haxis"), localeLabel.getStringKey("sat_reports_general_equipment"),
					localeLabel.getStringKey("sat_reports_speed_50km"), localeLabel.getStringKey("sat_reports_speed_70km"),
					localeLabel.getStringKey("sat_reports_speed_90km"), localeLabel.getStringKey("sat_reports_speed_120km"), 	   
					localeLabel.getStringKey("sat_reports_speed_150km"), localeLabel.getStringKey("sat_reports_speed_150km_bigger"), 
					localeLabel.getStringKey("sat_reports_general_total") };
			
			//JSON chart title and subtitle
			chartTitle = localeLabel.getStringKey("sat_reports_chart_title_speed");	
			imageName = localeLabel.getStringKey("sat_reports_chart_file_name_speed");*/
			
		}

		/** CCR REPORTS **/

		/**** CCR CLASSES TYPE  ****/
		if(type.equals("9")) {

			// Table fields
			fields = new String[] {localeLabel.getStringKey("sat_reports_general_date"), localeLabel.getStringKey("sat_reports_general_datetime"),
					localeLabel.getStringKey("sat_reports_class_motorcycle"), localeLabel.getStringKey("sat_reports_class_light"), 
					localeLabel.getStringKey("sat_reports_class_small"), localeLabel.getStringKey("sat_reports_class_long"), 
					localeLabel.getStringKey("sat_reports_class_bus"), localeLabel.getStringKey("sat_reports_general_total")};		   

			// Table Objects
			fieldObjectValues = new String[] {"date", "dateTime", "class1", "class2", "class3", "class4", "class5", "total"};
			
			//JSON chart fields
			jsonFields = new String[] {localeLabel.getStringKey("sat_reports_chart_haxis"),
					localeLabel.getStringKey("sat_reports_class_motorcycle"), localeLabel.getStringKey("sat_reports_class_light"), 
					localeLabel.getStringKey("sat_reports_class_small"), localeLabel.getStringKey("sat_reports_class_long"), 
					localeLabel.getStringKey("sat_reports_class_bus"), localeLabel.getStringKey("sat_reports_general_total")};		   
			
			//JSON chart title and subtitle
			chartTitle = localeLabel.getStringKey("sat_reports_chart_title_ccr_classes");	 
			imageName = localeLabel.getStringKey("sat_reports_chart_file_name_ccr_classes");	
		}

		/**** CCR AXLE TYPE  ****/
		if(type.equals("10")) {

			// Table fields
			fields = new String[] {localeLabel.getStringKey("sat_reports_general_date"), localeLabel.getStringKey("sat_reports_general_datetime"),
				localeLabel.getStringKey("sat_reports_class_light"), localeLabel.getStringKey("sat_reports_class_commercials"), 
				localeLabel.getStringKey("sat_reports_general_total")};	

			// Table Objects
			fieldObjectValues = new String[] {"date", "dateTime", "class1", "class2", "total"};
			
			//JSON chart fields
			jsonFields = new String[] {localeLabel.getStringKey("sat_reports_chart_haxis"),
				localeLabel.getStringKey("sat_reports_class_light"), localeLabel.getStringKey("sat_reports_class_commercials"), 
				localeLabel.getStringKey("sat_reports_general_total")};	
			
			//JSON chart title and subtitle
			chartTitle = localeLabel.getStringKey("sat_reports_chart_title_ccr_axles");	
			imageName = localeLabel.getStringKey("sat_reports_chart_file_name_ccr_axles");	

		}

		/**** CCR SPEED ****/
		if(type.equals("11")) {

			// Table fields
			fields = new String[] {localeLabel.getStringKey("sat_reports_general_date"), localeLabel.getStringKey("sat_reports_general_datetime"),
					localeLabel.getStringKey("sat_reports_general_equipment"), localeLabel.getStringKey("sat_reports_speed_50km"), 
					localeLabel.getStringKey("sat_reports_speed_70km"),	localeLabel.getStringKey("sat_reports_speed_90km"), 
					localeLabel.getStringKey("sat_reports_speed_120km"), localeLabel.getStringKey("sat_reports_speed_150km"), 
					localeLabel.getStringKey("sat_reports_speed_150km_bigger"), localeLabel.getStringKey("sat_reports_general_total") };

			// Table Objects
			fieldObjectValues = new String[] {"date", "dateTime", "equipment", "speed50km", "speed70km", "speed90km", "speed120km", "speed150km", "speed150km_bigger", "total"};

			//JSON chart fields
			/*jsonFields = new String[] {localeLabel.getStringKey("sat_reports_chart_haxis"),
					localeLabel.getStringKey("sat_reports_speed_50km"), localeLabel.getStringKey("sat_reports_speed_70km"),
					localeLabel.getStringKey("sat_reports_speed_90km"), localeLabel.getStringKey("sat_reports_speed_120km"), 	   
					localeLabel.getStringKey("sat_reports_speed_150km"), localeLabel.getStringKey("sat_reports_speed_150km_bigger"), 
					localeLabel.getStringKey("sat_reports_general_total") };
			
			//JSON chart title and subtitle
			chartTitle = localeLabel.getStringKey("sat_reports_chart_title_ccr_speed"); 		
			imageName = localeLabel.getStringKey("sat_reports_chart_file_name_ccr_speed");	*/
			
		}

		/**** ALL CLASS TYPE  ****/
		if(type.equals("12")) {

			// Table fields
			fields = new String[] {localeLabel.getStringKey("sat_reports_general_date"), localeLabel.getStringKey("sat_reports_general_datetime"),
					localeLabel.getStringKey("sat_reports_class_motorcycle"), localeLabel.getStringKey("sat_reports_class_light"), 
					localeLabel.getStringKey("sat_reports_class_semi_trailer"), localeLabel.getStringKey("sat_reports_class_trailer"), 
					localeLabel.getStringKey("sat_reports_axles_2"), localeLabel.getStringKey("sat_reports_axles_3"), 
					localeLabel.getStringKey("sat_reports_axles_4"), localeLabel.getStringKey("sat_reports_axles_5"), 
					localeLabel.getStringKey("sat_reports_axles_6"), localeLabel.getStringKey("sat_reports_axles_7"), 
					localeLabel.getStringKey("sat_reports_axles_8"), localeLabel.getStringKey("sat_reports_axles_9"), 
					localeLabel.getStringKey("sat_reports_axles_10"), localeLabel.getStringKey("sat_reports_axles_2"), 
					localeLabel.getStringKey("sat_reports_axles_3"), localeLabel.getStringKey("sat_reports_axles_4"), 
					localeLabel.getStringKey("sat_reports_axles_5"), localeLabel.getStringKey("sat_reports_axles_6"), 
					localeLabel.getStringKey("sat_reports_general_total")};	

			// Table Objects
			fieldObjectValues = new String[] {"date", "dateTime", "class1", "class2", "class3", "class4", "class5",  "class6",
					"class7",  "class8",  "class9",  "class10",  "class11",  "class12",  "class13",  "class14",  "class15",  "class16",
					"class17",  "class18",  "total"};
			
			//JSON chart fields
			jsonFields = new String[] {localeLabel.getStringKey("sat_reports_chart_haxis"),
					localeLabel.getStringKey("sat_reports_class_motorcycle"), localeLabel.getStringKey("sat_reports_class_light"), 
					localeLabel.getStringKey("sat_reports_class_semi_trailer"), localeLabel.getStringKey("sat_reports_class_trailer"), 
					localeLabel.getStringKey("sat_reports_axles_2"), localeLabel.getStringKey("sat_reports_axles_3"), 
					localeLabel.getStringKey("sat_reports_axles_4"), localeLabel.getStringKey("sat_reports_axles_5"), 
					localeLabel.getStringKey("sat_reports_axles_6"), localeLabel.getStringKey("sat_reports_axles_7"), 
					localeLabel.getStringKey("sat_reports_axles_8"), localeLabel.getStringKey("sat_reports_axles_9"), 
					localeLabel.getStringKey("sat_reports_axles_10"), localeLabel.getStringKey("sat_reports_axles_2"), 
					localeLabel.getStringKey("sat_reports_axles_3"), localeLabel.getStringKey("sat_reports_axles_4"), 
					localeLabel.getStringKey("sat_reports_axles_5"), localeLabel.getStringKey("sat_reports_axles_6"), 
					localeLabel.getStringKey("sat_reports_general_total")};	
			
			//JSON chart title and subtitle
			chartTitle = localeLabel.getStringKey("sat_reports_chart_title_all_classes");
			imageName = localeLabel.getStringKey("sat_reports_chart_file_name_all_classes");	
						
		}

		/** CCR REPORTS **/

		//DESENHAR A TABELA
		drawTable(fields, fieldObjectValues);	

		//GUARDAR VALORES NA SESSION
		facesContext.getExternalContext().getSessionMap().put("fieldsLength", fields.length); //Length of Fields
		facesContext.getExternalContext().getSessionMap().put("fields", fields);	//Fields
		facesContext.getExternalContext().getSessionMap().put("jsonFields", jsonFields);	//Fields
		facesContext.getExternalContext().getSessionMap().put("fieldsObject", fieldObjectValues); //Objects

	}

    ////CRIAR TABLE HEADERS 

	/**********************************************************************************************************/

    ////BUILD REPORTS

	// REPORTS MODELS
	// Recebe uma String que define qual o reltorio vai ser chamado

	/**
	 * Mtodo par criar um relatrio de acordo com tipo
	 * @param type tipo do relatrio 
	 * @throws Exception
	 */	
	public void GetReports(String type) throws Exception{
		
		//RESET ON RESTART
	    resetFormValues(type);

		//Get external application contents
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		QueriesReportsModels models = new QueriesReportsModels(); //QuerieReportsModel class
		SatQueriesModels satModels = new SatQueriesModels(); // SatReportsModel class	    
		DateTimeApplication dta = new DateTimeApplication(); //DateTimeApsplication class

		GlobalReportsDAO dao = new GlobalReportsDAO();	//GlobalReportsDAO
				
		String startDate = null, endDate = null, data_anterior = null, equip_anterior = null;
					
		/*** Obter parmetros que vem no submit de cada pesquisa ***/

		//Get request values for multiple fields selection
		Map<String, String[]> parameterMap = (Map<String, String[]>) externalContext.getRequestParameterValuesMap();

		//Get Request single values
		Map<String, String> parameterMap2 = (Map<String, String>) externalContext.getRequestParameterMap();

		/**** Multiple Params ***/
		
		//param for equipments array
		satReport.equipments = parameterMap.get("equips");

		//param for vehicles array
		satReport.vehicles = parameterMap.get("vehicles");

		//param for axles array
		satReport.axles = parameterMap.get("axles");

		//param for classes array
		satReport.classes = parameterMap.get("classes");

		/**** Multiple Params ***/

		/**** Single Params ***/

		//param for equipment
		satReport.setEquipment(parameterMap2.get("equip"));

		//param for dateStart
		satReport.setStartDate(parameterMap2.get("dateStart"));

		//param for dateEnd
		satReport.setEndDate(parameterMap2.get("dateEnd"));

		//param for periods
		satReport.setPeriod(parameterMap2.get("periods"));

		//param for month
		satReport.setMonth(parameterMap2.get("month"));

		//param for year
		satReport.setYear(parameterMap2.get("year"));
		
		//Table id
		jsTableId = parameterMap2.get("jsTable");
		
		//Table Scroll Height 
		 jsTableScrollHeight = parameterMap2.get("jsTableScrollHeight");

		/**** Single Params ***/

		//Initialize ResultList
		//Builder class -> for construct objects
		resultList = new ArrayList<Builder>();	
						
		int numRegisters = 0;		
	
		/**** FLUXO MENSAL  ****/
		if(type.equals("3")) {

			// QUANTOS DIAS POSSUI O RESPECTIVO MS
			YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(satReport.getYear()), Integer.parseInt(satReport.getMonth()));					
			daysInMonth = yearMonthObject.lengthOfMonth();

			//INSERIR VALORES DATAS DE INICIO E FIM
			satReport.setStartDate("01/"+satReport.getMonth()+"/"+satReport.getYear());
			satReport.setEndDate(daysInMonth+"/"+satReport.getMonth()+"/"+satReport.getYear());

			//INTERVALO POR PERIODO
			periodRange = dta.periodsRange(satReport.getPeriod());

			//NMERO DE REGISTROS PARA A SADA DE DADOS			
			setNumRegisters((daysInMonth * periodRange)); 				

		}

		/**** FLUXO POR PERODO ****/

		else if(type.equals("4")) {

			// QUANTOS DIAS POSSUI O RESPECTIVO MS
			YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(satReport.getYear()), Integer.parseInt(satReport.getMonth()));
			int daysInMonth = yearMonthObject.lengthOfMonth();

			//INSERIR VALORES DATAS DE INICIO E FIM
			satReport.setStartDate("01/"+satReport.getMonth()+"/"+satReport.getYear());
			satReport.setEndDate(daysInMonth+"/"+satReport.getMonth()+"/"+satReport.getYear());

			//INTERVALO POR PERIODO
			periodRange = dta.periodsRange(satReport.getPeriod());

			//NMERO DE REGISTROS PARA A SADA DE DADOS					
			setNumRegisters(((daysInMonth * periodRange) * satReport.equipments.length)); // Nmero de registros

		}
		
		/** CASO CONTRARIO -- OUTROS TIPOS **/

		else {

			//RETORNA NMERO DE REGISTROS POR PERIODO E DATAS SELECIONADAS	
			numRegisters = dta.RegistersNumbers(satReport.getStartDate(), satReport.getEndDate(), satReport.getPeriod()); 
			
			// ------------------------------------------------------------------------------------------
			
			// NÚMERO DE REGISTROS
									
			if(type.equals("8") || type.equals("11")) {
				
				setNumRegisters(numRegisters * satReport.getEquipments().length);
			    setFieldsIndex(satReport.getEquipments().length / numRegisters);
			    
			    System.out.println("Registers: "+getNumRegisters());
						
			}else setNumRegisters(numRegisters);
			
			// ------------------------------------------------------------------------------------------
			
						
			//CONTAGEM DOS DIAS
			if(satReport.getStartDate() != null && satReport.getEndDate() != null)
			daysCount = ((int) dta.diferencaDias(satReport.getStartDate(), satReport.getEndDate()) + 1);

			//INTERVALO POR PERIODO
			periodRange = dta.periodsRange(satReport.getPeriod());
			range = periodRange; // Range to INC
			
			//SPEED CASE PERIOD RANGE
			if(type.equals("8") || type.equals("11"))
				periodRange *= satReport.getEquipments().length;
						
		}
		
		startDate = dta.StringDBDateFormat(satReport.getStartDate());
		endDate = dta.StringDBDateFormat(satReport.getEndDate());
		data_anterior = startDate;
		
		if(type.equals("8") || type.equals("11"))
			equip_anterior = satReport.getEquipments()[0];
							
		start = dta.DateTimeToStringIni(startDate); 
		end = dta.DateTimeToStringFim(endDate); 

		/** TODO RELATRIO PASSA POR AQUI!!! **/

		//NUMERO DE CAMPOS PARA A SADA DE DADOS
		//LEVA EM CONSIDERAO NMERO DE CAMPOS DA QUERY
		setFieldsNumber(fieldsNumber(type));

		//SELECIONA UMA PROCEDURE DE ACORDO COM PERODO SELECIONADO
		//procedure = models.SelectProcedureByPeriod(satReport.getPeriod());	
		
		resultQuery = new String[getNumRegisters()][getFieldsNumber()];	
		
		//JSON ARRAY DATA RANGE
		
		if(type.equals("1")) //VEHICLE COUNT
			jsonArray = new String[getNumRegisters()][getFieldsNumber()-1];	
		
		else jsonArray = new String[getNumRegisters()][jsonFields.length];	
		
		//SELECIONA UMA QUERY DE ACORDO COM TIPO SELECIONADO
		query = SelectQueryType(type, models, satModels);
		
		System.out.println(query); //debug

		//EXECUO DA QUERY
		String[][] auxResult = dao.ExecuteQuery(query, getNumRegisters(), getFieldsNumber());
		
		//CASO EXISTA REGISTROS ENTRA AQUI
		if(auxResult.length > 0) {
		
		//// NEW METHOD		
		int minuto = 0;
		int iterator= 0;
		int pos = 0;
		int hr = 0;
 		
		int lin = 0;
		int col = 0;
		int p = 0;	
		int inc = 0;
				
		lin = auxResult.length;
		col = auxResult[0].length;
				
		//DATAS
		dta.preencherDataPorPeriodo(resultQuery, 0, getNumRegisters(),  periodRange, startDate); 
		
		//JSON DATA
		if(!type.equals("8") && !type.equals("11"))
		   dta.preencherJSONDataPorPeriodo(jsonArray, 0, getNumRegisters(),  periodRange, startDate); 
		
		//PERIODOS
		//NEW
		if(satReport.getPeriod().equals("05 minutes")) {			
			 dta.intervalo05Minutos(resultQuery, 1, getNumRegisters());	
			 
			 if(!type.equals("8") && !type.equals("11"))
			 dta.intervaloJSON05Minutos(jsonArray, 0, getNumRegisters());				 
		}
					
		if(satReport.getPeriod().equals("06 minutes"))	{		
		     dta.intervalo06Minutos(resultQuery, 1, getNumRegisters());
		     
		     if(!type.equals("8") && !type.equals("11"))
		     dta.intervaloJSON06Minutos(jsonArray, 0, getNumRegisters());			
		}
		
		if(satReport.getPeriod().equals("10 minutes")) {		
			dta.intervalo10Minutos(resultQuery, 1, getNumRegisters());
			
			if(!type.equals("8") && !type.equals("11"))
			dta.intervaloJSON10Minutos(jsonArray, 0, getNumRegisters());	
		}
		   			
		if(satReport.getPeriod().equals("15 minutes"))	{	
		    dta.intervalo15Minutos(resultQuery, 1, getNumRegisters());	
		    
		    if(!type.equals("8") && !type.equals("11"))
		    dta.intervaloJSON15Minutos(jsonArray, 0, getNumRegisters());	
		}
		
		if(satReport.getPeriod().equals("30 minutes"))	{	
			dta.intervalo30Min(resultQuery, 1, getNumRegisters());	
			
			if(!type.equals("8") && !type.equals("11"))
		    dta.intervaloJSON30Minutos(jsonArray, 0, getNumRegisters());	
		}
			   		        			
		if(satReport.getPeriod().equals("01 hour")) { 	
			dta.preencherHora(resultQuery, 1, getNumRegisters());
			
			if(!type.equals("8") && !type.equals("11"))
			dta.intervaloJSON01Hora(jsonArray, 0, getNumRegisters());	
		}
		
		if(satReport.getPeriod().equals("06 hours")) {	
		    dta.intervalo06Horas(resultQuery, 1, getNumRegisters());
		    
		    if(!type.equals("8") && !type.equals("11"))
		    dta.intervaloJSON06Horas(jsonArray, 0, getNumRegisters());	
		}
		
		 if(satReport.getPeriod().equals("24 hours")) {
		    dta.intervalo24Horas(resultQuery, 1, getNumRegisters());	 
									
		 }
		 
		 //Fill Date Range
		 if(type.equals("8") || type.equals("11"))
		    dta.fillEquipName(listSats, resultQuery, satReport.getEquipments(), 2, getNumRegisters(), range, daysCount);
		   			 
		for(int j = 0; j < lin; j++) {
		   for(int i = 0; i < col; i++) {
		
		// CASO NO EXISTA VALOR >>>>>>> PASSA	   
		if(auxResult[j][0] != null)	 {  
					
		if(satReport.getPeriod().equals("01 hour") || satReport.getPeriod().equals("06 hours"))
			   hr = Integer.parseInt(auxResult[j][1].substring(0, 2));
					
		else if(!satReport.getPeriod().equals("24 hours") && !satReport.getPeriod().equals("01 hour") && !satReport.getPeriod().equals("06 hours")) {
			    hr = Integer.parseInt(auxResult[j][1].substring(0, 2));
			    minuto =  Integer.parseInt(auxResult[j][1].substring(3, 5));	
			    			 
			}	
		
		  if(type.equals("8") || type.equals("11")) {
		
		      // ------------------------------------------------------------------------------------ 
		      // TODA VEZ QUE FOR REALIZADA A LEITURA DE UM REGISTRO DO BANCO DE DADOS
		      // VERIFICA SE O REGISTRO É DIFERENTE DA DATA ANTERIOR		     
			
			 if (!auxResult[j][0].equals(data_anterior)) {								
											
			    // CASO SEJA DIFERENTE PEGA-SE A DIFERENÇA DE DIAS
			    // DATA ANTERIOR, DATA QUE VEM DA BASE DE DADOS E INTERVALO DO PERIODO
			    iterator = dta.daysDifference(data_anterior, auxResult[j][0], periodRange);	
			    				
			    // ADICIONA-SE O ITERATOR A POSICAO
				pos = iterator;	
				
				// INCREMENTO
				inc = 0;
				
				// ADICIONA-SE A DATA ANTERIOR A DATA DO REGISTRO ATUAL
				data_anterior = auxResult[j][0];
				
				// ADICIONA-SE O EQUIPAMENTO ANTERIOR
				equip_anterior =  auxResult[j][2];				
																						
			 }	
			 					 
			if(!auxResult[j][2].equals(equip_anterior)) {
				inc += range;			 
			    equip_anterior = auxResult[j][2];
			    
			}
				 				 			 						
			// ------------------------------------------------------------------------------------ 
			
			 if(satReport.getPeriod().equals("05 minutes"))	{
				 p = dta.index05Minutes(hr, minuto);
				 p = p + pos + inc;
			 }
			 else if(satReport.getPeriod().equals("06 minutes")) {	
					 p = dta.index06Minutes(hr, minuto);
					 p = p + pos + inc;
			 }
			 else if(satReport.getPeriod().equals("10 minutes")) {
			    	 p = dta.index10Minutes(hr, minuto);
			    	 p = p + pos + inc;
			 }
			 else if(satReport.getPeriod().equals("15 minutes")) {	
					 p = dta.index15Minutes(hr, minuto);
			         p = p + pos + inc;
							
			 }
			 else if(satReport.getPeriod().equals("30 minutes")) {	
					 p = dta.index30Minutes(hr, minuto);
					 p = p + pos + inc;
			 }
			 
			 else if(satReport.getPeriod().equals("01 hour"))				
				p = pos + hr + inc;
						
			else if(satReport.getPeriod().equals("06 hours")) {
				
				p = dta.index06Hours(hr);				
				p = pos + p + inc;
				
			}
			
			else if(satReport.getPeriod().equals("24 hours"))
				     p = pos + inc;
			 					 			 								 
			if(i > 2 ) 
			    resultQuery[p][i] = auxResult[j][i];	
			
		    }
		  
		  else {
			  
			  if (!auxResult[j][0].equals(data_anterior)) {								
					
				    // CASO SEJA DIFERENTE PEGA-SE A DIFERENÇA DE DIAS
				    // DATA ANTERIOR, DATA QUE VEM DA BASE DE DADOS E INTERVALO DO PERIODO
				    iterator = dta.daysDifference(data_anterior, auxResult[j][0], periodRange);	
				    				
				    // ADICIONA-SE O ITERATOR A POSICAO
					pos = iterator;	
									
					// ADICIONA-SE A DATA ANTERIOR A DATA DO REGISTRO ATUAL
					data_anterior = auxResult[j][0];
																											
				 }	
			  
				 if(satReport.getPeriod().equals("05 minutes"))	{
					 p = dta.index05Minutes(hr, minuto);
					 p = p + pos;
				 }
				 else if(satReport.getPeriod().equals("06 minutes")) {	
						 p = dta.index06Minutes(hr, minuto);
						 p = p + pos;
				 }
				 else if(satReport.getPeriod().equals("10 minutes")) {
				    	 p = dta.index10Minutes(hr, minuto);
				    	 p = p + pos;
				 }
				 else if(satReport.getPeriod().equals("15 minutes")) {	
						 p = dta.index15Minutes(hr, minuto);
				         p = p + pos;
								
				 }
				 else if(satReport.getPeriod().equals("30 minutes")) {	
						 p = dta.index30Minutes(hr, minuto);
						 p = p + pos;
				 }
				 else if(satReport.getPeriod().equals("01 hour"))				
					p = pos + hr;
							
				else if(satReport.getPeriod().equals("06 hours")) {
					
					p = dta.index06Hours(hr);				
					p = pos + p;
					
				}
				
				else if(satReport.getPeriod().equals("24 hours"))
					     p = pos;
						
									 
				if(i > 1 ) {
				    resultQuery[p][i] = auxResult[j][i];			  
			  
		 																	
			//JSON ARRAY 
			if(type.equals("6") &&  i  < 12) // CLASS
			       jsonArray[p][i-1] = auxResult[j][i];
			 
			else  if(type.equals("7") && i  < 12) //AXLE
			       jsonArray[p][i-1] = auxResult[j][i];
			
			else  if(type.equals("9") && i  < 8) //CLASS CCR
			       jsonArray[p][i-1] = auxResult[j][i];
			
			else  if(type.equals("10") && i  < 5) //TYPE CCR
			       jsonArray[p][i-1] = auxResult[j][i];
			
			else  if(type.equals("11") && i < 9) // SPEED CCR
			       jsonArray[p][i-1] = auxResult[j][i];
			    
			else if(type.equals("12") && i  < 20) //ALL CLASSES
			       jsonArray[p][i-1] = auxResult[j][i];
			  
			  else if(!type.equals("6") && !type.equals("7") && !type.equals("9") && !type.equals("10") && !type.equals("11") && !type.equals("12"))
			      jsonArray[p][i-1] = auxResult[j][i];
			}		
			
		   } //  JSON 
		 } // CASO NO EXISTA VALOR >>>>>>> PASSA
		
		}
		   
	   }
		    //// NEW METHOD

			//SADA PARA A TABELA
			OutPutResult(type);
			
			//SADA DO EXCEL
		    ExcelOutPut(type, model);

			//BOTO DE LIMPAR 
			setClearBool(false);

			//LINK DE DOWNLOAD DO EXCEL
			setExcelBool(false);	
			
			//LINK PARA ACESSAR O GRÁFICO
			setChartBool(false);
			
			if(!type.equals("8") && !type.equals("11"))
			   JSONData(isChartBool(), satReport.getPeriod(), satReport.getEquipment());
									
			//UPDATE RESET BUTTON
			RequestContext.getCurrentInstance().update("form-btns:#btn-tab-reset");

			//UPDATE BUTTON GENERATE EXCEL
			RequestContext.getCurrentInstance().update("form-excel:#excel-act");	
			
			//UPDATE TABLE JQUERY ON RELOAD PAGE
			RequestContext.getCurrentInstance().execute("drawTable('#"+jsTableId+"', '"+jsTableScrollHeight+"');");	
								    					
			//CASO CONTRARIO ENTRA AQUI
			
		} else {
					    
		          //EXECUTE JS
				  RequestContext.getCurrentInstance().execute("hideMessage();");
				  
				  //UPDATE TABLE JQUERY ON RELOAD PAGE
				  RequestContext.getCurrentInstance().execute("drawTable('#"+jsTableId+"', '"+jsTableScrollHeight+"'); showMessage();");	
				  			     											
	    }

	}
	
	//// BUILD REPORTS

	/**********************************************************************************************************/
	
	
	public void JSONData(boolean chartBool, String period, String equipment) {
		
		TranslationMethods trm = new TranslationMethods();
				
		String vAxisTitle = localeLabel.getStringKey("sat_reports_chart_vAxis");
		
		LocalDateTime local =  LocalDateTime.now();
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm");  
	    String formatDateTime = local.format(format);  
	    
	    String equipName = ""; // EQUIP NAME VARIABLE
	    
	    if(equipment != null) { // IF IS A SINGLE EQUIPMENT
		    
		    //LIST OF EQUIPMENTS TO GET CURRENT NAME
		    for (Equipments eq : listSats) {
		    	
		    	if(Integer.parseInt(equipment) == eq.getEquip_id())
		    		equipName = eq.getNome();	    	
		    }		    							   
		   
		    chartTitle+= " - " + trm.periodName(period) + " ("+equipName+")"; // NAME OF FILE WITH TIME FORMATTED			  
		    
	        }
	        
	        else chartTitle+= " - " + trm.periodName(period); // IF IS NOT A SINGLE EQUIPEMENT DO THIS
	      		
		
		imageName += formatDateTime; //NAME OF FILE WITH TIME
		
		if(!chartBool) {
					 		  	   		
	        // Create a new instance of Gson
	        gson = new Gson();
	    
	        // Converting multidimensional array into JSON	      
	        jsColumn = gson.toJson(jsonFields);	
	        
	        jsData = gson.toJson(jsonArray);	
	        	     	        
	        jsData = jsData.toString().replaceAll("\"", "");	        
	        // jsData = jsData.toString().replaceAll("\\(", "\\'");
	        // jsData.toString().replaceAll("\\)", "\\'");
	        jsData = jsData.toString().replaceAll("null", "0");	
	       	        	     
	        //DEBUG
	        //System.out.println("Header = " + jsColumn);
	        // System.out.println("Data = " + jsData);
	       	        
	        if(period.equals("24 hours"))
	           RequestContext.getCurrentInstance().execute("reDrawChart("+jsColumn+", "+jsData+", '"+chartTitle+"', '"+vAxisTitle+"', '"+ dateFormat +"', '"+imageName+"');");
	        
	        else RequestContext.getCurrentInstance().execute("reDrawChart("+jsColumn+", "+jsData+", '"+chartTitle+"','"+vAxisTitle+"', '"+ datetimeFormat +"', '"+imageName+"');");
	        	     	        
		}
     }	

	///////////////////////////////////
	//CREATE REPORTS
	///////////////////////////////// 
	
	///// VBVs Txt  
	
	// REPORTS VBV MODEL
	public void GetVBVReport() throws Exception{				

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		SatQueriesModels satModels = new SatQueriesModels();	    

		GlobalReportsDAO dao = new GlobalReportsDAO();	

		//Initialize List
		resultVBV = new ArrayList<VBV>();

		//Get values for fields selection						
		Map<String, String> parameterMap = (Map<String, String>) externalContext.getRequestParameterMap();

		//Single Selection
		satReport.setEquipment(parameterMap.get("equip"));

		satReport.setMonth(parameterMap.get("month"));

		satReport.setYear(parameterMap.get("year"));

		satReport.setPeriod(parameterMap.get("period"));

		// Quantos dias possui o respectivo ms
		YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(parameterMap.get("year")), Integer.parseInt(parameterMap.get("month")));
		int daysInMonth = yearMonthObject.lengthOfMonth();

		satReport.setStartDate("01/"+satReport.getMonth()+"/"+satReport.getYear());
		satReport.setEndDate(daysInMonth+"/"+satReport.getMonth()+"/"+satReport.getYear());

		//Select specific query by type
		queryCount = satModels.VBVCount();
		query = satModels.VBVs();

		//Execution of Query
		resultVBV = dao.ExecuteQueryVBVs(query, satReport.getEquipment(), satReport.getStartDate(), satReport.getEndDate());

		//Output to datatable
		OutputVBVResult();

		facesContext.getExternalContext().getSessionMap().put("selectedEquip", satReport.getEquipment()); 
		facesContext.getExternalContext().getSessionMap().put("selectedMonth", satReport.getMonth()); 
		facesContext.getExternalContext().getSessionMap().put("selectedYear", satReport.getYear()); 

	}	

	//////// VBVs txt
	
	
	/**********************************************************************************************************/
	/**********************************************************************************************************/

	///////////////////////////////////
	//QUERY FOR METHODS
	///////////////////////////////// 
	
	/////// BUILD QUERY MODEL 
	
     //////////////////////
     //// WITHOUT INDEX
     /////////////////////

	/**
	 * @author Wellington 10/09/2020
	 * Mtodo para criar uma Query a ser executada  
	 * @param models - Objeto do tipo QuerieReportsModels
	 * @param mainQuery - Query principal a ser adicionada
	 * @return
	 */

	/*** COUNT VEHICLES ***/
	public String BuildMainQuery(QueriesReportsModels models, String mainQuery, String index) {    	 

		String query = null;

		query = models.BuildQuery(models.QueryHeader(satReport.getPeriod()), mainQuery, models.QueryFromSatTable(satReport.getPeriod(), RoadConcessionaire.tableCCR),
				models.whereClauseDate(start, end), models.vehicleSelectionWhereClause(satReport.vehicles), models.QuerySatGroupAndOrder(satReport.getPeriod()));
		
		return query;
	}
	
	/*** TO COUNT FLOW VEHICLES ***/
	public String BuildMainQueryType2(QueriesReportsModels models, String mainQuery, String index) {    	 

		String query = null;

		query = models.BuildQueryIndexType2(models.QueryHeader(satReport.getPeriod()), mainQuery, models.QueryFromSatTable(satReport.getPeriod(), RoadConcessionaire.tableCCR), models.useIndex(index),
				models.innerJoinSat(), models.whereClauseEquipDate(satReport.getEquipment(), start, end), models.QuerySatGroupAndOrder(satReport.getPeriod()));

		return query;
	}
	
	/*** TO WEIGHING VEHICLES ***/
	public String BuildMainQueryType3(QueriesReportsModels models, String mainQuery, String index) {    	 

		String query = null;

		query = models.BuildQueryIndexType3(models.QueryHeader(satReport.getPeriod()), mainQuery, models.QueryFromSatTable(satReport.getPeriod(), RoadConcessionaire.tableCCR), models.useIndex(index),
			    models.whereClauseEquipDate(satReport.getEquipment(), start, end), models.QuerySatGroupAndOrder(satReport.getPeriod()));

		return query;
	}


	/*** SAT_VBV_LL TABLE ***/
	public String BuildMainQueryLLType2(QueriesReportsModels models, String mainQuery, String index) {    	 

		String query = null;
		
		query = models.BuildQueryIndexType2(models.QueryHeader(satReport.getPeriod()), mainQuery, models.QueryFromSatTable(satReport.getPeriod(), RoadConcessionaire.tableLL),
				models.useIndex(index), models.innerJoinSat(), models.whereClauseEquipDate(satReport.getEquipment(), start, end), models.QuerySatGroupAndOrder(satReport.getPeriod()));

		return query;
	}
	
	/*** SAT_VBV_CCR TABLE ***/
	public String BuildMainQueryCCRType2(QueriesReportsModels models, String mainQuery, String index) {    	 

		String query = null;

		query = models.BuildQueryIndexType2(models.QueryHeader(satReport.getPeriod()), mainQuery, models.QueryFromSatTable(satReport.getPeriod(), RoadConcessionaire.tableCCR), models.useIndex(index),
				models.innerJoinSat(), models.whereClauseEquipDate(satReport.getEquipment(), start, end), models.QuerySatGroupAndOrder(satReport.getPeriod()));

		return query;
	}
	
	// -------------------- NEW INDEX -----------------------------------------
	
	/*** SPEED VEHICLES CCR ***/
	public String BuildMainQuerySpeed(QueriesReportsModels models, String mainQuery, String index) {    	 

		String query = null;

		query = models.BuildQuery(models.QueryHeader(satReport.getPeriod()), mainQuery, models.QueryFromSatTable(satReport.getPeriod(), RoadConcessionaire.tableCCR),
				models.whereClauseForSpeed(satReport.getEquipments(), start, end), models.vehicleSelectionWhereClause(satReport.vehicles), models.QuerySatGroupAndOrderMultiple(satReport.getPeriod()));
		
		return query;
	}
	
	// -----------------------------------------------------------------------
	
	/*** SPEED VEHICLES LL ***/
	public String BuildMainQuerySpeedLL(QueriesReportsModels models, String mainQuery, String index) {    	 

		String query = null;

		query = models.BuildQueryLL(models.QueryHeader(satReport.getPeriod()), mainQuery, models.QueryFromSatTable(satReport.getPeriod(), RoadConcessionaire.tableLL),
				models.innerJoinSat(), models.whereClauseForSpeed(satReport.getEquipments(), start, end), models.vehicleSelectionWhereClause(satReport.vehicles), models.QuerySatGroupAndOrderMultiple(satReport.getPeriod()));
		
		return query;
	}
	
	
     //////////////////////
     //// WITHOUT INDEX
     /////////////////////
	

	/*** TO VBV USE WHERE CLAUSE WITH TWO PARAMETERS ***/
	public String BuildMainQuery(QueriesReportsModels models, String mainQuery) {    
				
		String query = null;

		query = models.BuildQuery(models.QueryHeader(satReport.getPeriod()), mainQuery, models.QueryFromSatTable(satReport.getPeriod(), RoadConcessionaire.tableCCR),
				models.whereClauseDate(start, end), models.vehicleSelectionWhereClause(satReport.vehicles), models.QuerySatGroupAndOrder(satReport.getPeriod()));

		return query;
	}
	
	/*** TO COUNT FLOW VEHICLES ***/
	public String BuildMainQueryType2(QueriesReportsModels models, String mainQuery) {    	 

		String query = null;

		query = models.BuildQueryType2(models.QueryHeader(satReport.getPeriod()), mainQuery, models.QueryFromSatTable(satReport.getPeriod(), RoadConcessionaire.tableCCR),
				models.innerJoinSat(), models.whereClauseEquipDate(satReport.getEquipment(), start, end), models.QuerySatGroupAndOrder(satReport.getPeriod()));

		return query;
	}
	
	/*** TO WEIGHING VEHICLES ***/
	public String BuildMainQueryType3(QueriesReportsModels models, String mainQuery) {    	 

		String query = null;

		query = models.BuildQueryType3(models.QueryHeader(satReport.getPeriod()), mainQuery, models.QueryFromSatTable(satReport.getPeriod(), RoadConcessionaire.tableCCR),
			    models.whereClauseEquipDate(satReport.getEquipment(), start, end), models.QuerySatGroupAndOrder(satReport.getPeriod()));

		return query;
	}

	/*** SAT_VBV_LL TABLE ***/
	public String BuildMainQueryLLType2(QueriesReportsModels models, String mainQuery) {    	 

		String query = null;

		query = models.BuildQueryType2(models.QueryHeader(satReport.getPeriod()), mainQuery, models.QueryFromSatTable(satReport.getPeriod(), RoadConcessionaire.tableLL),
				models.innerJoinSat(), models.whereClauseEquipDate(satReport.getEquipment(), start, end), models.QuerySatGroupAndOrder(satReport.getPeriod()));

		return query;
	}
	
	/*** SAT_VBV_CCR TABLE ***/
	public String BuildMainQueryCCRType2(QueriesReportsModels models, String mainQuery) {    	 

		String query = null;

		query = models.BuildQueryType2(models.QueryHeader(satReport.getPeriod()), mainQuery, models.QueryFromSatTable(satReport.getPeriod(), RoadConcessionaire.tableCCR),
				models.innerJoinSat(), models.whereClauseEquipDate(satReport.getEquipment(), start, end), models.QuerySatGroupAndOrder(satReport.getPeriod()));

		return query;
	}
	
	//////////////////////
	//// WITHOUT INDEX
	/////////////////////

     /////// BUILD QUERY MODEL

	/**********************************************************************************************************/

    /////// CONSTRUO DA QUERY

	/**
	 * Mtodo que retorna um query especfica de acordo com tipo
	 * @param type - Tipo de especfico do relatrio
	 * @param models - Objeto do tipo QueriesReportsModels
	 * @param satModels - Objeto do tipo SatQueriesModels
	 * @return query
	 * @throws Exception 
	 */
	public String SelectQueryType(String type, QueriesReportsModels models, SatQueriesModels satModels) throws Exception {   

		int lanes = 0;
		int[] lanesEquips;

		if(satReport.equipments != null)
			lanesEquips = new int[satReport.equipments.length];

		String query = null;

		switch(type) {

		case "1": query = BuildMainQuery(models, satModels.CountVehiclesMainQuery(satReport.equipments), QueriesReportsModels.USE_INDEX_IDX_SITEID_DATA); break;
		case "2": query = BuildMainQueryType2(models, satModels.CountVehiclesDirectionMainQuery(satReport.getEquipment()), QueriesReportsModels.USE_INDEX_IDX_SITEID_DATA); break;
		case "3": equipDAO = new EquipmentsDAO(); lanes = equipDAO.EquipmentSelectLanesNumber("sat", satReport.getEquipment()); query = BuildMainQuery(models, satModels.MonthlyFlowMainQuery(satReport.getEquipment(), lanes)); break;
		case "4": equipDAO = new EquipmentsDAO(); lanesEquips = equipDAO.EquipmentSelectLanesNumber("sat", satReport.equipments); query = BuildMainQuery(models, satModels.PeriodFlowMainQuery(satReport.equipments, satReport.getPeriod(), lanesEquips)); break;
		case "5": query = BuildMainQueryType3(models, satModels.WeighingMainQuery(satReport.getEquipment()), QueriesReportsModels.USE_INDEX_IDX_SITEID_DATA); ; break;
		case "6": query = BuildMainQueryType2(models, satModels.ClassTypeCCRMainQuery(satReport.getEquipment()), QueriesReportsModels.USE_INDEX_IDX_SITEID_DATA); ; break; //MUDANA CCR
		case "7": query = BuildMainQueryType2(models, satModels.AxleTypeMainQuery(satReport.getEquipment()), QueriesReportsModels.USE_INDEX_IDX_SITEID_DATA); ; break;
		case "8": query = BuildMainQuerySpeed(models, satModels.SpeedMainQuery(), QueriesReportsModels.USE_INDEX_IDX_SITEID_DATA);  ; break; 
		case "9": query = BuildMainQueryLLType2(models, satModels.CCRClasses(satReport.getEquipment()), QueriesReportsModels.USE_INDEX_IDX_SITEID_DATA);  ; break;  
		case "10": query = BuildMainQueryLLType2(models, satModels.CCRTipos(satReport.getEquipment()), QueriesReportsModels.USE_INDEX_IDX_SITEID_DATA);  ; break;  	
		case "11": query = BuildMainQuerySpeedLL(models, satModels.CCRVelocidade(), QueriesReportsModels.USE_INDEX_IDX_SITEID_DATA);  ; break;  
		case "12": query = BuildMainQueryCCRType2(models, satModels.CCRAllClasses(satReport.getEquipment()), QueriesReportsModels.USE_INDEX_IDX_SITEID_DATA);  ; break;  

		default: query = null; break;

		}  	 
		
		return query;

	}

     /////// CONSTRUO DA QUERY  

	///////////////////////////////////
	//QUERY FOR METHODS
	/////////////////////////////////
	
	/**********************************************************************************************************/

	///////////////////////////////////
	//SAIDA DE DADOS
	/////////////////////////////////  
	
	
    /////// SAIDA DE DADOS -/- FRONT-END

	/**
	 * @author Wellington 10/09/2020
	 * Mtodo para gerenciar construtores a fim de criar saidas de dados dinamicamente.
	 * @param type - Tipo do satReport (Define qual  a sua finalidade)
	 */

	//PREENCHER SAIDA DE DADOS 
	// POR TIPO DE RELATRIO
	public void OutPutResult(String type) {  

		//ACESSAR DADOS DO RELATRIOF
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		//FIELDS EXTERNOS ARMAZENADOS NA REQUISIO
		fields = (String[]) externalContext.getSessionMap().get("fields");
		fieldObjectValues =  (String[]) externalContext.getSessionMap().get("fieldsObject");

		/**** COUNT VEHICLES ****/
		if(type.equals("1")) {  

			// COUNT VEHICLES CONSTRUCTOR
			countVehiclesBuilder(satReport.equipments.length);
				
			ReorderTableHeaderForCountVehicles(satReport.equipments);
		 
			// DRAW TABLE -- BUILD HEADER
			drawTable(fields, fieldObjectValues);

		}

		/**** COUNT VEHICLES PERIOD ****/
		if(type.equals("2")) {

			// COUNT VEHICLES PERIOD CONSTRUCTOR
			countVehiclesPeriodBuilder();

			// DRAW TABLE -- BUILD HEADER
			drawTable(fields, fieldObjectValues);

		}   	 

		/**** FLUXO MENSAL ****/
		if(type.equals("3")) {

			// FLUXO MENSAL CONSTRUCTOR
			monthFlowBuilder();

			// DRAW TABLE -- BUILD HEADER
			drawTable(fields, fieldObjectValues);
		}

		/**** FLUXO POR PERIODO ****/
		if(type.equals("4")) {

			// FLUXO POR PERIODO CONSTRUCTOR
			periodFlowBuilder();

			// DRAW TABLE -- BUILD HEADER
			drawTable(fields, fieldObjectValues);
		}

		/**** PESAGEM ****/
		if(type.equals("5")) { 
			
			//FLUXO POR PERIODO CONSTRUCTOR		
			 weighingBuilder();
			 
			//REORDERNAR HEADERS DE ACORDO COM SELEO => CHECKBOXES DE CLASSES
			//if(satReport.classes.length < 13)
			//   ReorderTableHeaderForWeighing(satReport.classes);  
			
			// DRAW TABLE -- BUILD HEADER
			   drawTable(fields, fieldObjectValues);	

		}  

		/**** CLASS TYPE ****/
		if(type.equals("6")) { 

			//CLASS TYPE CONSTRUCTOR 
			classesBuilder();    
			
			//REORDERNAR HEADERS DE ACORDO COM SELEO => CHECKBOXES DE CLASSES
			//if(satReport.classes.length < 13)
			//ReorderTableHeaderForClasses(satReport.classes);

			// DRAW TABLE -- BUILD HEADER
			drawTable(fields, fieldObjectValues);	

		}    		 

		/**** AXLE TYPE ****/
		if(type.equals("7")) {     		 

			//AXLE TYPE CONSTRUCTOR
			axlesBuilder();
			
			//REORDERNAR HEADERS DE ACORDO COM SELEO => CHECKBOXES DE AXLES
			//if(satReport.axles.length < 9)
			//ReorderTableHeaderForAxles(satReport.axles);

			// DRAW TABLE -- BUILD HEADER
			drawTable(fields, fieldObjectValues);	

		}  	     

		/**** SPEED ****/
		if(type.equals("8")) {   		 

			//SPEED CONSTRUCTOR
			speedBuilder();

			// DRAW TABLE -- BUILD HEADER
			drawTable(fields, fieldObjectValues);	      

		}  

		/**** CCR CLASS  ****/
		if(type.equals("9")) {   		 

			//CCR CLASS CONSTRUCTOR
			classesCCRBuilder();
					
			// DRAW TABLE -- BUILD HEADER
			drawTable(fields, fieldObjectValues);	      

		}  

		/**** CCR TYPE  ****/
		if(type.equals("10")) {   		 

			//CCR TYPE CONSTRUCTOR 
			tiposCCRBuilder();

			// DRAW TABLE -- BUILD HEADER
			drawTable(fields, fieldObjectValues);	      

		}  

		/**** CCR SPEED  ****/
		if(type.equals("11")) {   		 

			//CCR SPEED CONSTRUCTOR
			speedBuilder();

			// DRAW TABLE -- BUILD HEADER
			drawTable(fields, fieldObjectValues);	      

		}  

		/**** CCR ALL CLASSES  ****/
		if(type.equals("12")) {   		 

			//CCR ALL CLASSES CONSTRUCTOR
			ccrAllClassesBuilder();

			// DRAW TABLE -- BUILD HEADER
			drawTable(fields, fieldObjectValues);	      

		}  

	} 

	 /////// SAIDA DE DADOS -/- FRONT-END

	//REORDENAR TABLE FRONT END - EIXOS
	public void ReorderTableHeaderForAxles(String[] axles) {
		
			fields = new String[(3 + axles.length)];
			fieldObjectValues = new String[(3 + axles.length)];

			fields[0] = localeLabel.getStringKey("sat_reports_general_date");
			fields[1] = localeLabel.getStringKey("sat_reports_general_datetime");
			fieldObjectValues[0] = "date";
			fieldObjectValues[1] = "dateTime";

			for(int i = 0; i < axles.length; i++) {

				if(axles[i].equals("2")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_axles_2");
					fieldObjectValues[i+2] = "axles_"+(i+1);
				}
				if(axles[i].equals("3")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_axles_3");
					fieldObjectValues[i+2] = "axles_"+(i+1);
				}
				if(axles[i].equals("4")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_axles_4");
					fieldObjectValues[i+2] = "axles_"+(i+1);
				}
				if(axles[i].equals("5")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_axles_5");
					fieldObjectValues[i+2] = "axles_"+(i+1);
				}
				if(axles[i].equals("6")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_axles_6");
					fieldObjectValues[i+2] = "axles_"+(i+1);
				}
				if(axles[i].equals("7")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_axles_7");
					fieldObjectValues[i+2] = "axles_"+(i+1);
				}
				if(axles[i].equals("8")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_axles_8");
					fieldObjectValues[i+2] = "axles_"+(i+1);
				}
				if(axles[i].equals("9")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_axles_9");
					fieldObjectValues[i+2] = "axles_"+(i+1);
				}
				if(axles[i].equals("10")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_axles_10");
					fieldObjectValues[i+2] = "axles_"+(i+1);
				}    		 
			 
			fields[axles.length + 2] = localeLabel.getStringKey("sat_reports_general_total");
			fieldObjectValues[axles.length + 2] = "total";

		}    	 

	}

	//REORDENAR TABLE FRONT END - CLASSES
	public void ReorderTableHeaderForClasses(String[] classes) {

			fields = new String[(3 + classes.length)];
			fieldObjectValues = new String[(3 + classes.length)];

			fields[0] = localeLabel.getStringKey("sat_reports_general_date");
			fields[1] = localeLabel.getStringKey("sat_reports_general_datetime");
			fieldObjectValues[0] = "date";
			fieldObjectValues[1] = "dateTime";

			for(int i = 0; i < classes.length; i++) {

				if(classes[i].equals("1")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_class_light");
					fieldObjectValues[i+2] = "class"+(i+1);
				}
				if(classes[i].equals("2")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_class_heavy_2_axles");
					fieldObjectValues[i+2] = "class"+(i+1);
				}
				if(classes[i].equals("3")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_class_semi_trailer");
					fieldObjectValues[i+2] = "class"+(i+1);
				}
				if(classes[i].equals("4")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_class_heavy_3_axles");
					fieldObjectValues[i+2] = "class"+(i+1);
				}
				if(classes[i].equals("5")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_class_trailer");
					fieldObjectValues[i+2] = "class"+(i+1);
				}
				if(classes[i].equals("6")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_class_heavy_4_axles");
					fieldObjectValues[i+2] = "class"+(i+1);
				}
				if(classes[i].equals("7")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_class_heavy_5_axles");
					fieldObjectValues[i+2] = "class"+(i+1);
				}
				if(classes[i].equals("8")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_class_heavy_6_axles");
					fieldObjectValues[i+2] = "class"+(i+1);
				}
				if(classes[i].equals("9")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_class_motorcycle");
					fieldObjectValues[i+2] = "class"+(i+1);
				} 
				if(classes[i].equals("10")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_class_heavy_7_axles");
					fieldObjectValues[i+2] = "class"+(i+1);
				} 
				if(classes[i].equals("11")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_class_heavy_8_axles");
					fieldObjectValues[i+2] = "class"+(i+1);
				}  

				if(classes[i].equals("E9")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_class_heavy_9_axles");
					fieldObjectValues[i+2] = "class"+(i+1);
				}  

				if(classes[i].equals("10N")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_class_heavy_10_axles");
					fieldObjectValues[i+2] = "class"+(i+1);
				}  
			}   

			fields[classes.length + 2] = localeLabel.getStringKey("sat_reports_general_total");
			fieldObjectValues[classes.length + 2] = "total";
		}

	//REORDENAR TABLE FRONT END - PESAGEM
	public void ReorderTableHeaderForWeighing(String[] classes) {

			fields = new String[(2 + classes.length)];
			fieldObjectValues = new String[(2 + classes.length)];

			fields[0] = localeLabel.getStringKey("sat_reports_general_date");
			fields[1] = localeLabel.getStringKey("sat_reports_general_datetime");
			fieldObjectValues[0] = "date";
			fieldObjectValues[1] = "dateTime";

			for(int i = 0; i < classes.length; i++) {

				if(classes[i].equals("1")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_class_light");
					fieldObjectValues[i+2] = "class"+(i+1);
				}
				if(classes[i].equals("2")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_class_heavy_2_axles");
					fieldObjectValues[i+2] = "class"+(i+1);
				}
				if(classes[i].equals("3")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_class_semi_trailer");
					fieldObjectValues[i+2] = "class"+(i+1);
				}
				if(classes[i].equals("4")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_class_heavy_3_axles");
					fieldObjectValues[i+2] = "class"+(i+1);
				}
				if(classes[i].equals("5")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_class_trailer");
					fieldObjectValues[i+2] = "class"+(i+1);
				}
				if(classes[i].equals("6")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_class_heavy_4_axles");
					fieldObjectValues[i+2] = "class"+(i+1);
				}
				if(classes[i].equals("7")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_class_heavy_5_axles");
					fieldObjectValues[i+2] = "class"+(i+1);
				}
				if(classes[i].equals("8")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_class_heavy_6_axles");
					fieldObjectValues[i+2] = "class"+(i+1);
				}
				if(classes[i].equals("9")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_class_motorcycle");
					fieldObjectValues[i+2] = "class"+(i+1);
				} 
				if(classes[i].equals("10")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_class_heavy_7_axles");
					fieldObjectValues[i+2] = "class"+(i+1);
				} 
				if(classes[i].equals("11")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_class_heavy_8_axles");
					fieldObjectValues[i+2] = "class"+(i+1);
				}  

				if(classes[i].equals("E9")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_class_heavy_9_axles");
					fieldObjectValues[i+2] = "class"+(i+1);
				}  

				if(classes[i].equals("10N")) {
					fields[i+2] =  localeLabel.getStringKey("sat_reports_class_heavy_10_axles");
					fieldObjectValues[i+2] = "class"+(i+1);
				}  
				
							
			}
		}
	
	//REORDENAR TABLE FRONT END - CONTAGEM VECULOS
	public void ReorderTableHeaderForCountVehicles(String[] equipments) {
		
			fields = new String[(3 + equipments.length)];
			fieldObjectValues = new String[(3 + equipments.length)];
			jsonFields = new String[equipments.length + 2];
			
			int equip = 0;
			String[] satNames = new String[equipments.length];
			
			fields[0] = localeLabel.getStringKey("sat_reports_general_date");
			fields[1] = localeLabel.getStringKey("sat_reports_general_datetime");
			fields[equipments.length + 2] = localeLabel.getStringKey("sat_reports_general_total");
			fieldObjectValues[0] = "date";
			fieldObjectValues[1] = "dateTime";
			fieldObjectValues[equipments.length + 2] = "total";  
			
			jsonFields[0] = localeLabel.getStringKey("sat_reports_general_date");
			jsonFields[equipments.length + 1] = localeLabel.getStringKey("sat_reports_general_total");

			//VERIFICAR NOME DOS SATS DA LISTA COM OS SELECIONADOS
			for(int i = 0; i < listSats.size(); i++) {
				
				for (int e = 0; e < equipments.length; e++) {
					
					equip = Integer.parseInt(equipments[e]);
					
					if (listSats.get(i).getEquip_id() == equip)
				       satNames[e] = listSats.get(i).getNome(); 							     
		         		     
				}   
			  }
					
			//PREENCHER VARIVEIS DOS SATS
             for(int i = 0; i < equipments.length; i++) {	

				fields[i+2] = satNames[i];
				fieldObjectValues[i+2] = "eqp"+(i+1);
				jsonFields[i+1] = satNames[i]; 
			}
			
			 
	    }	
		
	//FOR VBV TEXT OUTPUT FILE
	private void OutputVBVResult() throws IOException {

		FacesContext facesContext = FacesContext.getCurrentInstance();	

		byteWriter = new ByteArrayOutputStream();
		writer = new BufferedWriter(new OutputStreamWriter(byteWriter));

		for(int i = 0; i < resultVBV.size(); i++) {

			writer.write(resultVBV.get(i).getSiteID() + ";");
			writer.write(resultVBV.get(i).getSeqG() + ";");
			writer.write(resultVBV.get(i).getSeqN() + ";");
			writer.write(resultVBV.get(i).getDatetime() + ";");
			writer.write(resultVBV.get(i).getClassVBV()+ ";");
			writer.write(resultVBV.get(i).getAxlNumber() + ";");
			writer.write(resultVBV.get(i).getAxl1W() + ";");
			writer.write(resultVBV.get(i).getAxl2W() + ";");
			writer.write(resultVBV.get(i).getAxl3W() + ";");
			writer.write(resultVBV.get(i).getAxl4W() + ";");
			writer.write(resultVBV.get(i).getAxl5W() + ";");
			writer.write(resultVBV.get(i).getAxl6W() + ";");
			writer.write(resultVBV.get(i).getAxl7W() + ";");
			writer.write(resultVBV.get(i).getAxl8W() + ";");
			writer.write(resultVBV.get(i).getAxl9W() + ";");
			writer.write(resultVBV.get(i).getAxl2D() + ";");
			writer.write(resultVBV.get(i).getAxl3D() + ";");
			writer.write(resultVBV.get(i).getAxl4D() + ";");
			writer.write(resultVBV.get(i).getAxl5D() + ";");
			writer.write(resultVBV.get(i).getAxl6D() + ";");
			writer.write(resultVBV.get(i).getAxl7D() + ";");
			writer.write(resultVBV.get(i).getAxl8D() + ";");
			writer.write(resultVBV.get(i).getAxl9D() + ";");			
			writer.write(resultVBV.get(i).getGross() + ";");				
			writer.write(resultVBV.get(i).getTemperature() + ";");					
			writer.write(resultVBV.get(i).getSpeed() + ";");
			writer.write(resultVBV.get(i).getLane() + ";");					

			writer.newLine();
		}

		writer.flush();			
		writer.close(); 

		byte[] bytes = byteWriter.toByteArray();

		facesContext.getExternalContext().getSessionMap().put("bytes", bytes);

	}  

	/**********************************************************************************************************/

	///////////////////////////////////
	//DIRECTIONS METHODS
	/////////////////////////////////  

	//CARREGAR DIREES DB
	public void loadDirections() throws Exception{			

		equipDAO  = new EquipmentsDAO();

		String dir1 = equipDAO.firstDirection(equipId);  

		popDirections(dir1);			

		//UpdateDirs
		RequestContext.getCurrentInstance().execute("updateDirections('#{satLabels.sat_reports_select_directions}');");

	}

	//POPULAR DIREES -> Internacionalizao
	public void popDirections(String faixa1) {

		if (faixa1.equals("N")) {
			directionLabel1 = localeDir.getStringKey("directions_north");
			directionLabel2 = localeDir.getStringKey("directions_south");	
			directionValue1 = localeDir.getStringKey("directions_north_abbr");	
			directionValue2 = localeDir.getStringKey("directions_south_abbr");	
		}

		else if (faixa1.equals("S")) {
			directionLabel1 = localeDir.getStringKey("directions_south");
			directionLabel2 = localeDir.getStringKey("directions_north");	
			directionValue1 = localeDir.getStringKey("directions_south_abbr");	
			directionValue2 = localeDir.getStringKey("directions_north_abbr");	
		}

		else if (faixa1.equals("L")) {
			directionLabel1 = localeDir.getStringKey("directions_east");
			directionLabel2 = localeDir.getStringKey("directions_west");	
			directionValue1 = localeDir.getStringKey("directions_east_abbr");	
			directionValue2 = localeDir.getStringKey("directions_west_abbr");	
		}

		else if (faixa1.equals("O")) {
			directionLabel1 = localeDir.getStringKey("directions_west");
			directionLabel2 = localeDir.getStringKey("directions_east");	
			directionValue1 = localeDir.getStringKey("directions_west_abbr");	
			directionValue2 = localeDir.getStringKey("directions_east_abbr");	
		}	

		else {

			directionLabel1 = localeDir.getStringKey("directions_select_label1");
			directionLabel2 = localeDir.getStringKey("directions_select_label2");
			directionValue1 = localeDir.getStringKey("directions_select_value1");
			directionValue2 = localeDir.getStringKey("directions_select_value2");
		}
	}

	///////////////////////////////////
	//DIRECTIONS METHODS
	/////////////////////////////////  

	/**********************************************************************************************************/
	
	/**********************************************************************************************************/
	/**********************************************************************************************************/

	///////////////////////////////////
	//SAIDA DE DADOS
	/////////////////////////////////      

	/////// FIELDS NUMBER - SADA DE DADO

	public Integer fieldsNumber(String type) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		int length = (int) externalContext.getSessionMap().get("fieldsLength");

		int fields = 0;

		/**** CONTAGEM VECULOS ****/		
		if(type.equals("1")) {    		
				fields =  (3 + satReport.equipments.length);		

		}

		/**** CONTAGEM VECULOS ****/		
		if(type.equals("2")) {    
			fields = length;   
			
	    }

		/**** FLUXO MENSAL  ****/
		if(type.equals("3")) {    		
			fields = (length + 2);    		 
		}

		/**** FLUXO PERODO  ****/
		if(type.equals("4")) {    		
			fields = 69;    		 
		}

		/**** PESAGEM  ****/
		if(type.equals("5")) {
			
			 fields = length;

		}

		/**** CLASS TYPE  ****/
		if(type.equals("6")) {		
			 fields = length + 28;

		}

		/**** AXLE TYPE ****/
		if(type.equals("7")) {
			
			 fields = length + 20;

		}

		/**** SPEED ****/
		if(type.equals("8")) {			
			fields = length;

		}

		/**** CLASSES TYPE ****/
		if(type.equals("9")) {
			 fields = length +12;
		}

		/**** CCR TYPE ****/
		if(type.equals("10")) {
			 fields = length + 6;
		}

		/**** CCR SPEED ****/
		if(type.equals("11")) {
			fields = length + 14;
		}

		/**** CCR ALL CLASSES ****/
		if(type.equals("12")) {
			fields = length + 38;

		}

		return fields;    	 
	}

     /////// FIELDS NUMBER - SADA DE DADO 

	/**********************************************************************************************************/

   /////// SAIDA DO EXCEL

	public void ExcelOutPut(String type, ExcelModels model) throws IOException, Exception {

		FacesContext facesContext = FacesContext.getCurrentInstance();		

		DateTimeApplication dta = new DateTimeApplication();
		TranslationMethods tm = new TranslationMethods();

		//Initialize Excel Class Object
		//Excel Model Class
		model = new ExcelModels(); 

		String[] countMergeHeader; //Merge Cells

		String excel_title = "", fileName = ""; // Titulo e nome do arquivo

		int colStartDate = 0,  colEndDate = 0; //col ini & end

		int[] col; //cols array -> size

		String equip = "", road = "", km = "", lanes = "", city = "", lane1 = "";  //Equipment Info

		/** get equipment values from DB **/  
		
		EquipmentsDAO dao = new EquipmentsDAO();  // EquipmentsDAO Class
		SAT info = new SAT(); // SAT Class
        
		/** get equipment values from DB **/  

		/**** COUNT VEHICLES ****/
		if(type.equals("1")) {    		  

			//Define fileName
			fileName = localeLabel.getStringKey("excel_report_vehicles_count_file")+tm.periodName(satReport.getPeriod());
			excel_title = localeLabel.getStringKey("excel_report_vehicles_count_title"); //Excel title

			countMergeHeader = new String[] {"A1:B4", "C1:S4", "T1:W4"}; //Define Merge columns
			
			col = new int[] {3500, 4000}; //SET size to columns

			colStartDate = 19; colEndDate = 22; //Col ini & end date

			equip = " --- "; road = " --- "; km = " --- "; lanes = " --- "; city = " --- ";

			//ReorderTableHeaderForCountVehicles(satReport.equipments);    	//Reorder Table Header	

			model.StandardFonts();  //Set Font
			model.StandardStyles(); //Set Style
			model.StandardBorders(); // Set Borders
						
			//Chamada ao Mtodo padro do Excel
			model.StandardExcelModel(fields, getNumRegisters(), periodRange, daysCount, satReport.getPeriod(), dta.currentTime(), type, module,  				  
					RoadConcessionaire.externalImagePath, excel_title, equip, city, road, km, lanes, satReport.getStartDate(), satReport.getEndDate(), countMergeHeader, 
					col, colStartDate, colEndDate, resultQuery);

		}

		/**** COUNT VEHICLES PERIOD ****/
		if(type.equals("2")) {

			fileName = localeLabel.getStringKey("excel_report_vehicles_count_flow_file")+tm.periodName(satReport.getPeriod());
			excel_title = localeLabel.getStringKey("excel_report_vehicles_count_flow_title");

			countMergeHeader = new String[] {"A1:B1", "C1:I1", "C2:E2", "F2:H2", "A2:A3", "B2:B3", "I2:I3"}; // Define Merge columns

			col = new int[] {3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500}; //SET size to columns
			
			// get equipment values from DB 
			if(!satReport.getEquipment().equals(""))
				info = dao.SATreportInfo(satReport.getEquipment()); // fill equipemnt DB values    	        
			
			//Equipment Info
			equip = info.getNome(); road = info.getEstrada(); km = info.getKm();  
			lanes = String.valueOf(info.getNumFaixas()); city = info.getCidade();
			lane1 = info.getFaixa1();

			//Directions
			direction1 = tm.CheckDirection(lane1);
			direction2 = tm.Check2ndDirection(lane1);
			
			//DISPLAY INFO ON TABLE
			displayEquipInfo = equip+"  "+km+"  "+road;			
			displayDirection1 = direction1;
			displayDirection2 = direction2;

			//Create Fieldds Method
			CreateFields(type); 		

			model.countFlowFonts(); // Fonts
			model.countFlowStyles(); // Styles
			model.countFlowBorders(); // Borders

			//Excel Model For Count Flow
			model.ExcelModelCountFlow(fields, getNumRegisters(), periodRange, daysCount, satReport.getPeriod(), dta.currentTime(), type, module,  				  
					RoadConcessionaire.externalImagePath, excel_title, equip, city, road, km, lanes, direction1, direction2, satReport.getStartDate(), satReport.getEndDate(), countMergeHeader, 
					col, colStartDate, colEndDate, resultQuery);    		  
		}

		/**** FLUXO MENSAL ****/
		if(type.equals("3")) {

			//Define fileName
			fileName = localeLabel.getStringKey("excel_report_vehicles_count_flow_file"); 
			excel_title = localeLabel.getStringKey("excel_report_vehicles_count_flow_title"); //Excel Title

			countMergeHeader = new String[] {}; // Merge columns

			col = new int[] {}; // Cols size
			
			// get equipment values from DB 
			if(!satReport.getEquipment().equals(""))
			info = dao.SATreportInfo(satReport.getEquipment()); // fill equipemnt DB values

			//Equipment Info
			equip = info.getNome(); road = info.getEstrada(); km = info.getKm(); 
			lanes = String.valueOf(info.getNumFaixas()); city = info.getCidade();
			lane1 = info.getFaixa1();

			//Directions
			direction1 = tm.CheckDirection(lane1);
			direction2 = tm.Check2ndDirection(lane1);

			// Create Fields 
			CreateFields(type); 

			//Abreviation
			String abrDir1 = "", abrDir2 = "";

			model.monthlyFlowFonts(); //Set font
			model.mothlyFlowStyles(); // Set Style    		   
			model.monthlyFlowBorders(); // Set Borders

			//Excel Monthly Flow Model
			model.ExcelModelMonthlyFlow(getFieldsNumber(), getNumRegisters(), daysInMonth, periodRange, daysCount, satReport.getPeriod(), dta.currentTime(), type, module,   				  
					RoadConcessionaire.externalImagePath, excel_title, equip, city, road, km, lanes, direction1, direction2, abrDir1, abrDir2, satReport.getMonth(), satReport.getYear(), satReport.getStartDate(), satReport.getEndDate(), countMergeHeader, 
					col, colStartDate, colEndDate, resultQuery);

		}

		/**** FLUXO PERIODO ****/
		if(type.equals("4")) {

			//FileName
			fileName = localeLabel.getStringKey("excel_report_vehicles_count_flow_file");
			excel_title = localeLabel.getStringKey("excel_report_vehicles_count_flow_title"); // Excel title

			countMergeHeader = new String[] {"A1:L1", "A2:A36", "C3:F3", "G3:J3", "K3:N3", "P3:S3", "T3:W3", "Y3:AB3",
					"AC3:AF3", "AH3:AK3", "AL3:AO3", "AQ3:AT3", "AU3:AX3", "AZ3:BC3", "BD3:BG3", "BI3:BL3", "BM3:BP3", "C4:C5",
					"D4:F5", "G4:J4", "K4:N4", "P4:S4", "T4:W4", "Y4:AB4", "AC4:AF4", "AH4:AK4", "AL4:AO4", "AQ4:AT4", "AU4:AX4",
					"AZ4:BC4", "BD4:BG4", "BI4:BL4", "BM4:BP4"}; // Merge Cells

			//Equipment Info
			equip = info.getNome(); road = info.getEstrada(); km = info.getKm(); 
			lanes = String.valueOf(info.getNumFaixas()); city = info.getCidade();
			lane1 = info.getFaixa1();

			//Directions
			direction1 = tm.CheckDirection(lane1);
			direction2 = tm.Check2ndDirection(lane1);

			//Directions array
			String[] dir1 = new String[1];
			String[] dir2 = new String[1];

			//Direction header
			tm.sentidoExcelHeader(0, lane1, dir1, dir2);

			//Direction Labels
			directionLabel1 = tm.CheckHeaderDirection1(lane1);
			directionLabel2 = tm.CheckHeaderDirection2(lane1);

			col = new int[] {}; //Col Size

			//Create Fields
			CreateFields(type);    

			model.periodFlowFonts(); // Fonts
			model.periodFlowStyles(); // Styles
			model.periodFlowBorders(); // Borders

			//Excel PeriodFlow Method
			model.ExcelModelPeriodFlow(getFieldsNumber(), getNumRegisters(), periodRange, daysCount, satReport.getPeriod(), dta.currentTime(), type, module,   				  
					RoadConcessionaire.externalImagePath, excel_title, equip, city, road, km, lanes, dir1, dir2, satReport.getMonth(), satReport.getYear(), satReport.getStartDate(), satReport.getEndDate(), countMergeHeader, 
					col, colStartDate, colEndDate, resultQuery);

		}

		/**** PESAGEM ****/
		if(type.equals("5")) {    		  

			//FileName
			fileName = localeLabel.getStringKey("excel_report_weighing_file")+tm.periodName(satReport.getPeriod());
			excel_title = localeLabel.getStringKey("excel_report_weighing_title");

			countMergeHeader = new String[] {"A1:B4", "C1:K4", "L1:M4"}; // Merge Cells

	       col = new int[] {3500, 3500, 4550, 4550, 4550, 4550, 4550, 4550, 4550, 4550, 4550, 4550, 4550, 4550, 4550}; //Col size

			colStartDate = 11; colEndDate = 12; // Col ini & end
			
			// get equipment values from DB 
			if(!satReport.getEquipment().equals(""))
			info = dao.SATreportInfo(satReport.getEquipment()); // fill equipemnt DB values

			//Equipment info
			equip = info.getNome(); road = info.getEstrada(); km = info.getKm(); 
			lanes = String.valueOf(info.getNumFaixas()); city = info.getCidade();

			//Reorder Table
			//ReorderTableHeaderForWeighing(satReport.classes);    		

			model.StandardFonts(); //Set Font
			model.StandardStyles(); //Set Style
			model.StandardBorders(); //Set Borders

			//Standard Method Without total
			model.StandardExcelModelWithoutTotal(fields, getNumRegisters(), periodRange, daysCount, satReport.getPeriod(), dta.currentTime(), type, module,  				  
					RoadConcessionaire.externalImagePath, excel_title, equip, city, road, km, lanes, satReport.getStartDate(), satReport.getEndDate(), countMergeHeader, 
					col, colStartDate, colEndDate, resultQuery);

		}

		/**** CLASS TYPE ****/
		if(type.equals("6")) {

			//File name
			fileName = localeLabel.getStringKey("excel_report_class_file")+tm.periodName(satReport.getPeriod());
			excel_title = localeLabel.getStringKey("excel_report_class_title"); // File Title

			countMergeHeader = new String[] {"A1:B4", "C1:L4", "M1:O4"}; // Merge Cells

			col = new int[] {3500, 3500, 3500, 3500, 4250, 4250, 2700, 2700, 2700, 2700, 2700, 2700, 2700, 2700, 2700, 2700}; //Col size

			colStartDate = 12; colEndDate = 14; //Col start & end date
			
			// get equipment values from DB 
			if(!satReport.getEquipment().equals(""))
			info = dao.SATreportInfo(satReport.getEquipment()); // fill equipemnt DB values

			//Equipments Info
			equip = info.getNome(); road = info.getEstrada(); km = info.getKm(); 
			lanes = String.valueOf(info.getNumFaixas()); city = info.getCidade();
			lane1 = info.getFaixa1();

			//Directions 
			direction1 = tm.CheckDirection(lane1);
			direction2 = tm.Check2ndDirection(lane1);

			//Reorder Tables
			//ReorderTableHeaderForClasses(satReport.classes); 
			
			//Colunas que iniciam Sentido 1 e Sentido 2
			int iniDir1 = 16, iniDir2 = 30;

			model.StandardFonts(); //Set Fonts
			model.StandardStyles(); // Set Styles
			model.StandardBorders(); //Set Borders

			//Excel Model with Directions
			model.ExcelModelDirections(fields, getNumRegisters(), periodRange, daysCount, satReport.getPeriod(), dta.currentTime(), type, module,  				  
					RoadConcessionaire.externalImagePath, excel_title, equip, city, road, km, lanes, direction1, direction2, satReport.getStartDate(), satReport.getEndDate(), countMergeHeader, 
					col, colStartDate, colEndDate, resultQuery, iniDir1, iniDir2);

		}

		/**** AXLE TYPE ****/
		if(type.equals("7")) {

			//File Name
			fileName = localeLabel.getStringKey("excel_report_axle_file")+tm.periodName(satReport.getPeriod());
			excel_title = localeLabel.getStringKey("excel_report_axle_title"); //Excel title

			countMergeHeader = new String[] {"A1:B4", "C1:I4", "J1:L4"}; // Col Merge

			col = new int[] {3500, 3500}; // Col size

			colStartDate = 9; colEndDate = 11; // Col ini d& end date
			
			// get equipment values from DB 
			if(!satReport.getEquipment().equals(""))
			info = dao.SATreportInfo(satReport.getEquipment()); // fill equipemnt DB values

			//Equipments Info
			equip = info.getNome(); road = info.getEstrada(); km = info.getKm(); 
			lanes = String.valueOf(info.getNumFaixas()); city = info.getCidade();
			lane1 = info.getFaixa1();

			//Directions
			direction1 = tm.CheckDirection(lane1);
			direction2 = tm.Check2ndDirection(lane1);

			//Reorder header
			//ReorderTableHeaderForAxles(satReport.axles);  
			
			//Colunas que iniciam Sentido 1 e Sentido 2
			int iniDir1 = 12, iniDir2 = 22;

			model.StandardFonts(); //Set Font
			model.StandardStyles(); // Set Styles
			model.StandardBorders(); //Set Borders

			//Excel Model Directions
			model.ExcelModelDirections(fields, getNumRegisters(), periodRange, daysCount, satReport.getPeriod(), dta.currentTime(), type, module,  				  
					RoadConcessionaire.externalImagePath, excel_title, equip, city, road, km, lanes, direction1, direction2, satReport.getStartDate(), satReport.getEndDate(), countMergeHeader, 
					col, colStartDate, colEndDate, resultQuery, iniDir1, iniDir2);		  
		} 	 

		/**** SPEED ****/
		if(type.equals("8")) {

			//Define name
			fileName = localeLabel.getStringKey("excel_report_speed_file")+tm.periodName(satReport.getPeriod());
			excel_title = localeLabel.getStringKey("excel_report_speed_title"); // Excel title

			countMergeHeader = new String[] {"A1:B4", "C1:H4", "I1:J4"}; // Merge cells

			col = new int[] {3500, 3500, 4250, 3500, 3500, 3500, 3500, 3500, 4500, 3500}; // Col size 

			colStartDate = 8; colEndDate = 9; // Col ini & end date
								
			//Equipment Info
			equip = " --- "; road = " --- "; km = " --- "; 
			lanes = " --- "; city = " --- ";
									
			model.StandardFonts(); // Fonts
			model.StandardStyles(); // Styles
			model.StandardBorders(); // Borders

			//Standard Excel Model
			model.StandardEquipExcelModel(fields, getNumRegisters(), periodRange, daysCount, satReport.getPeriod(), dta.currentTime(), type, module,  				  
				RoadConcessionaire.externalImagePath, excel_title, equip, city, road, km, lanes, satReport.getStartDate(), satReport.getEndDate(), countMergeHeader, 
				col, colStartDate, colEndDate, resultQuery);

		}
		
		/* CCR REPORTS */
		
		/*** CLASSES ***/
		
		if(type.equals("9")) {

			//File name
			fileName = localeLabel.getStringKey("ccr_reports_class_excel_title")+tm.periodName(satReport.getPeriod());
			excel_title = localeLabel.getStringKey("ccr_reports_class_title"); // File Title

			countMergeHeader = new String[] {"A1:B4", "C1:L4", "M1:O4"}; // Merge Cells

			col = new int[] {3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500}; //Col size

			colStartDate = 12; colEndDate = 14; //Col start & end date
			
			//Colunas que iniciam Sentido 1 e Sentido 2
			int iniDir1 = 8, iniDir2 = 14;
			
			// get equipment values from DB 
			if(!satReport.getEquipment().equals(""))
			info = dao.SATreportInfo(satReport.getEquipment()); // fill equipemnt DB values

			//Equipments Info
			equip = info.getNome(); road = info.getEstrada(); km = info.getKm(); 
			lanes = String.valueOf(info.getNumFaixas()); city = info.getCidade();
			lane1 = info.getFaixa1();

			//Directions 
			direction1 = tm.CheckDirection(lane1);
			direction2 = tm.Check2ndDirection(lane1);

			//Reorder Tables
			//ReorderTableHeaderForClasses(satReport.classes);    		

			model.StandardFonts(); //Set Fonts
			model.StandardStyles(); // Set Styles
			model.StandardBorders(); //Set Borders

			//Excel Model with Directions
			model.ExcelModelDirections(fields, getNumRegisters(), periodRange, daysCount, satReport.getPeriod(), dta.currentTime(), type, module,  				  
					RoadConcessionaire.externalImagePath, excel_title, equip, city, road, km, lanes, direction1, direction2, satReport.getStartDate(), satReport.getEndDate(), countMergeHeader, 
					col, colStartDate, colEndDate, resultQuery, iniDir1, iniDir2 );

		}		
      			
         /*** CCR TIPOS ***/
		
		if(type.equals("10")) {

			//File name
			fileName = localeLabel.getStringKey("ccr_reports_type_excel_title")+tm.periodName(satReport.getPeriod());
			excel_title = localeLabel.getStringKey("ccr_reports_type_title"); // File Title

			countMergeHeader = new String[] {"A1:B4", "C1:L4", "M1:O4"}; // Merge Cells

			col = new int[] {3500, 3500, 3500, 3500, 3500, 3500, 3500}; //Col size

			colStartDate = 12; colEndDate = 14; //Col start & end date
			
			//Colunas que iniciam Sentido 1 e Sentido 2
			int iniDir1 = 5, iniDir2 = 8;
			
			// get equipment values from DB 
			if(!satReport.getEquipment().equals(""))
			info = dao.SATreportInfo(satReport.getEquipment()); // fill equipemnt DB values

			//Equipments Info
			equip = info.getNome(); road = info.getEstrada(); km = info.getKm(); 
			lanes = String.valueOf(info.getNumFaixas()); city = info.getCidade();
			lane1 = info.getFaixa1();

			//Directions 
			direction1 = tm.CheckDirection(lane1);
			direction2 = tm.Check2ndDirection(lane1);

			//Reorder Tables
			//ReorderTableHeaderForClasses(satReport.classes);    		

			model.StandardFonts(); //Set Fonts
			model.StandardStyles(); // Set Styles
			model.StandardBorders(); //Set Borders

			//Excel Model with Directions
			model.ExcelModelDirections(fields, getNumRegisters(), periodRange, daysCount, satReport.getPeriod(), dta.currentTime(), type, module,  				  
					RoadConcessionaire.externalImagePath, excel_title, equip, city, road, km, lanes, direction1, direction2, satReport.getStartDate(), satReport.getEndDate(), countMergeHeader, 
					col, colStartDate, colEndDate, resultQuery, iniDir1, iniDir2);

		}
		
		         /*** CCR SPEED ***/
		
				if(type.equals("11")) {

					//File name
					fileName = localeLabel.getStringKey("ccr_reports_speed_excel_title")+tm.periodName(satReport.getPeriod());
					excel_title = localeLabel.getStringKey("ccr_reports_speed_title"); // File Title

					countMergeHeader = new String[] {"A1:B4", "C1:L4", "M1:O4"}; // Merge Cells

					col = new int[] {3500, 3500, 4250, 3500, 3500, 3500, 3500, 3500, 4500, 3500}; // Col size 
					
					colStartDate = 12; colEndDate = 14; //Col start & end date
														
					//Equipment Info
					equip = " --- "; road = " --- "; km = " --- "; 
					lanes = " --- "; city = " --- ";

					//Directions 
					direction1 = " --- ";
					direction2 = " --- ";

					//Colunas que iniciam Sentido 1 e Sentido 2
					int iniDir1 = 10, iniDir2 = 17;		

					model.StandardFonts(); //Set Fonts
					model.StandardStyles(); // Set Styles
					model.StandardBorders(); //Set Borders

					//Excel Model with Directions
					model.ExcelModelEquipDirections(fields, getNumRegisters(), periodRange, daysCount, satReport.getPeriod(), dta.currentTime(), type, module,  				  
						RoadConcessionaire.externalImagePath, excel_title, equip, city, road, km, lanes, direction1, direction2, satReport.getStartDate(), satReport.getEndDate(), countMergeHeader, 
						col, colStartDate, colEndDate, resultQuery, iniDir1, iniDir2);

				}
				
				  /*** CCR TIPOS ***/
				
				if(type.equals("12")) {

					//File name
					fileName = localeLabel.getStringKey("ccr_reports_all_class_excel_title")+tm.periodName(satReport.getPeriod());
					excel_title = localeLabel.getStringKey("ccr_reports_all_class_title"); // File Title

					countMergeHeader = new String[] {"A1:B4", "C1:L4", "M1:O4"}; // Merge Cells

					col = new int[] {3500, 3500, 3500, 3500, 4500, 3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500}; //Col size

					colStartDate = 12; colEndDate = 14; //Col start & end date
					
					// get equipment values from DB 
					if(!satReport.getEquipment().equals(""))
					info = dao.SATreportInfo(satReport.getEquipment()); // fill equipemnt DB values

					//Equipments Info
					equip = info.getNome(); road = info.getEstrada(); km = info.getKm(); 
					lanes = String.valueOf(info.getNumFaixas()); city = info.getCidade();
					lane1 = info.getFaixa1();

					//Directions 
					direction1 = tm.CheckDirection(lane1);
					direction2 = tm.Check2ndDirection(lane1);

					//Colunas que iniciam Sentido 1 e Sentido 2
					int iniDir1 = 21, iniDir2 = 40;	  		

					model.StandardFonts(); //Set Fonts
					model.StandardStyles(); // Set Styles
					model.StandardBorders(); //Set Borders

					//Excel Model with Directions
				    model.ExcelModelDirectionsSubHeader(fields, getNumRegisters(), periodRange, daysCount, satReport.getPeriod(), dta.currentTime(), type, module,  				  
					RoadConcessionaire.externalImagePath, excel_title, equip, city, road, km, lanes, direction1, direction2, satReport.getStartDate(), satReport.getEndDate(), countMergeHeader, 
					col, colStartDate, colEndDate, resultQuery, iniDir1, iniDir2);

				}
				

		//Define Values in session map !important
		facesContext.getExternalContext().getSessionMap().put("xlsModel", model); 
		facesContext.getExternalContext().getSessionMap().put("current", dta.currentTime());
		facesContext.getExternalContext().getSessionMap().put("fileName", fileName);    	  
	}

      /////// SAIDA DO EXCEL

	/**********************************************************************************************************/

    /////// DOWNLOAD DO EXCEL

	//Download File Method
	public void download() {

		FacesContext facesContext = FacesContext.getCurrentInstance();	
		ExternalContext externalContext = facesContext.getExternalContext();

		model = (ExcelModels) externalContext.getSessionMap().get("xlsModel");
		currentDate = (String) externalContext.getSessionMap().get("current");
		fileName = (String) externalContext.getSessionMap().get("fileName");

		String name = fileName+"_"+currentDate;
			
		try {
			model.download(ExcelModels.workbook, name);
		} catch (IOException e) {			
			e.printStackTrace();
		}
		finally {
			//externalContext.getSessionMap().remove("xlsModel");
			//externalContext.getSessionMap().remove("current");
			//externalContext.getSessionMap().remove("fileName");					
		}

	}      

	 /////// DOWNLOAD DO EXCEL

	///////////////////////////////////
	//SAIDA DE DADOS
	/////////////////////////////////      
	
	/**********************************************************************************************************/
	/**********************************************************************************************************/

	 /////// TXT FILES

	//Download File Text
	public void downloadTxtFile() throws Exception {

		FacesContext facesContext = FacesContext.getCurrentInstance();	
		ExternalContext externalContext = facesContext.getExternalContext();

		TranslationMethods tm = new TranslationMethods();

		//Get Site information
		String siteID = (String) externalContext.getSessionMap().get("selectedEquip");
		String month = (String) String.valueOf(externalContext.getSessionMap().get("selectedMonth"));
		String year = (String) String.valueOf(externalContext.getSessionMap().get("selectedYear"));    
		
		byte[] bytes = (byte[]) externalContext.getSessionMap().get("bytes");

		try {

			EquipmentsDAO dao = new EquipmentsDAO();

			String siteName = dao.EquipmentName("sat", siteID);

			externalContext.setResponseContentType("text/plain");
			externalContext.setResponseHeader("Content-Disposition",
					"attachment; filename=\"VBV_"+siteName+"_"+tm.MonthAbbreviation(month)+"_"+tm.YearAbbreviation(year)+".txt\"");

			OutputStream responseOutputStream = externalContext.getResponseOutputStream();

			responseOutputStream.write(bytes);
			responseOutputStream.flush();
			responseOutputStream.close();

			facesContext.responseComplete(); 	

		} finally {

			//Set Session map values !important
			externalContext.getSessionMap().remove("selectedEquip");
			externalContext.getSessionMap().remove("selectedMonth");
			externalContext.getSessionMap().remove("selectedYear");
			externalContext.getSessionMap().remove("bytes");

		}

	}

    /////// TXT FILES   

	/**********************************************************************************************************/

     /////// RESET FORM VALUES
	
	//Form Reset
	public void resetFormValues(String type) {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();	
		ExternalContext externalContext = facesContext.getExternalContext();

		//Reset object => call on click reset button
		satReport = new SatReports();

		jsonFields = new String[1];
		jsonArray = new String[1][1];
		
		jsData = "";
		jsColumn = "";
		
		gson = new Gson();
		
		externalContext.getSessionMap().remove("xlsModel");
		externalContext.getSessionMap().remove("current");
		externalContext.getSessionMap().remove("fileName");		
		externalContext.getSessionMap().remove("fields");
		externalContext.getSessionMap().remove("jsonFields");
		externalContext.getSessionMap().remove("fieldsObject");

		// Fields again
		CreateFields(type);
		
	}

    /////// RESET FORM VALUES

	/**********************************************************************************************************/
	
	/**********************************************************************************************************/
	
	///////////////////////////////////
	//CONSTRUCTORS FOR RESULTLIST
	/////////////////////////////////  

    /////// COMMON REPORTS

	/* COUNT VEHICLES REPORT -- TYPE = 1 */

	public void countVehiclesBuilder(int equips) {

		switch(equips) {

		case 1:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))    		 				               
						.total(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3])));  

			}; break;	

		case 2:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))				               	              			               				               	 
						.total(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))); 

			}; break;	
		case 3:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4])) 				              				              			               				               	 
						.total(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))); 

			}; break;	
		case 4:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5])) 				               			              			               				               	 
						.total(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6])));   

			}; break;	
		case 5:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6])) 				             			              			               				               	 
						.total(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))); 

			}; break;	
		case 6:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7])) 				               				              			               				               	 
						.total(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8])));   

			}; break;	
		case 7:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8])) 				                				              			               				               	 
						.total(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9])));  

			}; break;	
		case 8:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9])) 				               			              			               				               	 
						.total(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))); 

			}; break;	
		case 9:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10])) 				              				              			               				               	 
						.total(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11])));   

			}; break;	
		case 10:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11])) 				                 				              			               				               	 
						.total(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12])));  

			}; break;	
		case 11:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12])) 				              			              			               				               	 
						.total(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))); 

			}; break;	
		case 12:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13])) 				              				              			               				               	 
						.total(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14])));   

			}; break;	
		case 13:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14])) 				               			              			               				               	 
						.total(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15])));   

			}; break;	
		case 14:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15])) 				               				              			               				               	 
						.total(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16])));   

			}; break;	
		case 15:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16])) 				              			               				               	 
						.total(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17])));     

			}; break;	
		case 16:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17])) 				                			               				               	 
						.total(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18])));    

			}; break;	
		case 17:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18])) 				               				               	 
						.total(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19])));    

			}; break;	
		case 18:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19])) 				            			               	 
						.total(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20])));   

			}; break;	
		case 19:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20])) 				              			               	 
						.total(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21])));  

			}; break;	
		case 20:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21])) 				             			               	 
						.total(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22])));   

			}; break;	
		case 21:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22])) 				              			               	 
						.total(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23])));   

			}; break;	
		case 22:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23])) 				            				               	 
						.total(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24])));    

			}; break;	
		case 23:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24])) 				              				               	 
						.total(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25])));    

			}; break;	
		case 24:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24]))
						.equip24(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25])) 				             				               	 
						.total(resultQuery[k][26] == null? 0 : Integer.parseInt(resultQuery[k][26])));  

			}; break;	
		case 25:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24]))
						.equip24(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25]))
						.equip25(resultQuery[k][26] == null? 0 : Integer.parseInt(resultQuery[k][26])) 				            		               	 
						.total(resultQuery[k][27] == null? 0 : Integer.parseInt(resultQuery[k][27])));   

			}; break;	
		case 26:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24]))
						.equip24(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25]))
						.equip25(resultQuery[k][26] == null? 0 : Integer.parseInt(resultQuery[k][26]))
						.equip26(resultQuery[k][27] == null? 0 : Integer.parseInt(resultQuery[k][27])) 				            				               	 
						.total(resultQuery[k][28] == null? 0 : Integer.parseInt(resultQuery[k][28])));  

			}; break;	
		case 27:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24]))
						.equip24(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25]))
						.equip25(resultQuery[k][26] == null? 0 : Integer.parseInt(resultQuery[k][26]))
						.equip26(resultQuery[k][27] == null? 0 : Integer.parseInt(resultQuery[k][27]))
						.equip27(resultQuery[k][28] == null? 0 : Integer.parseInt(resultQuery[k][28])) 				              			                			               	 
						.total(resultQuery[k][29] == null? 0 : Integer.parseInt(resultQuery[k][29])));   

			}; break;	
		case 28:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24]))
						.equip24(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25]))
						.equip25(resultQuery[k][26] == null? 0 : Integer.parseInt(resultQuery[k][26]))
						.equip26(resultQuery[k][27] == null? 0 : Integer.parseInt(resultQuery[k][27]))
						.equip27(resultQuery[k][28] == null? 0 : Integer.parseInt(resultQuery[k][28]))		 
						.equip28(resultQuery[k][29] == null? 0 : Integer.parseInt(resultQuery[k][29])) 				            			               	 
						.total(resultQuery[k][30] == null? 0 : Integer.parseInt(resultQuery[k][30])));  

			}; break;	
		case 29:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24]))
						.equip24(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25]))
						.equip25(resultQuery[k][26] == null? 0 : Integer.parseInt(resultQuery[k][26]))
						.equip26(resultQuery[k][27] == null? 0 : Integer.parseInt(resultQuery[k][27]))
						.equip27(resultQuery[k][28] == null? 0 : Integer.parseInt(resultQuery[k][28]))		 
						.equip28(resultQuery[k][29] == null? 0 : Integer.parseInt(resultQuery[k][29]))		 
						.equip29(resultQuery[k][30] == null? 0 : Integer.parseInt(resultQuery[k][30]))				               			            			               	 
						.total(resultQuery[k][31] == null? 0 : Integer.parseInt(resultQuery[k][31])));   

			}; break;	
		case 30:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24]))
						.equip24(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25]))
						.equip25(resultQuery[k][26] == null? 0 : Integer.parseInt(resultQuery[k][26]))
						.equip26(resultQuery[k][27] == null? 0 : Integer.parseInt(resultQuery[k][27]))
						.equip27(resultQuery[k][28] == null? 0 : Integer.parseInt(resultQuery[k][28]))		 
						.equip28(resultQuery[k][29] == null? 0 : Integer.parseInt(resultQuery[k][29]))		 
						.equip29(resultQuery[k][30] == null? 0 : Integer.parseInt(resultQuery[k][30]))		 
						.equip30(resultQuery[k][31] == null? 0 : Integer.parseInt(resultQuery[k][31])) 				             				               	 
						.total(resultQuery[k][32] == null? 0 : Integer.parseInt(resultQuery[k][32])));    

			}; break;	
		case 31:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24]))
						.equip24(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25]))
						.equip25(resultQuery[k][26] == null? 0 : Integer.parseInt(resultQuery[k][26]))
						.equip26(resultQuery[k][27] == null? 0 : Integer.parseInt(resultQuery[k][27]))
						.equip27(resultQuery[k][28] == null? 0 : Integer.parseInt(resultQuery[k][28]))		 
						.equip28(resultQuery[k][29] == null? 0 : Integer.parseInt(resultQuery[k][29]))		 
						.equip29(resultQuery[k][30] == null? 0 : Integer.parseInt(resultQuery[k][30]))		 
						.equip30(resultQuery[k][31] == null? 0 : Integer.parseInt(resultQuery[k][31]))		 
						.equip31(resultQuery[k][32] == null? 0 : Integer.parseInt(resultQuery[k][32]))				               			               	 
						.total(resultQuery[k][33] == null? 0 : Integer.parseInt(resultQuery[k][33])));    

			}; break;	
		case 32:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24]))
						.equip24(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25]))
						.equip25(resultQuery[k][26] == null? 0 : Integer.parseInt(resultQuery[k][26]))
						.equip26(resultQuery[k][27] == null? 0 : Integer.parseInt(resultQuery[k][27]))
						.equip27(resultQuery[k][28] == null? 0 : Integer.parseInt(resultQuery[k][28]))		 
						.equip28(resultQuery[k][29] == null? 0 : Integer.parseInt(resultQuery[k][29]))		 
						.equip29(resultQuery[k][30] == null? 0 : Integer.parseInt(resultQuery[k][30]))		 
						.equip30(resultQuery[k][31] == null? 0 : Integer.parseInt(resultQuery[k][31]))		 
						.equip31(resultQuery[k][32] == null? 0 : Integer.parseInt(resultQuery[k][32]))		 
						.equip32(resultQuery[k][33] == null? 0 : Integer.parseInt(resultQuery[k][33])) 				               			               	 
						.total(resultQuery[k][34] == null? 0 : Integer.parseInt(resultQuery[k][34])));    

			}; break;	
		case 33:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24]))
						.equip24(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25]))
						.equip25(resultQuery[k][26] == null? 0 : Integer.parseInt(resultQuery[k][26]))
						.equip26(resultQuery[k][27] == null? 0 : Integer.parseInt(resultQuery[k][27]))
						.equip27(resultQuery[k][28] == null? 0 : Integer.parseInt(resultQuery[k][28]))		 
						.equip28(resultQuery[k][29] == null? 0 : Integer.parseInt(resultQuery[k][29]))		 
						.equip29(resultQuery[k][30] == null? 0 : Integer.parseInt(resultQuery[k][30]))		 
						.equip30(resultQuery[k][31] == null? 0 : Integer.parseInt(resultQuery[k][31]))		 
						.equip31(resultQuery[k][32] == null? 0 : Integer.parseInt(resultQuery[k][32]))		 
						.equip32(resultQuery[k][33] == null? 0 : Integer.parseInt(resultQuery[k][33]))		 
						.equip33(resultQuery[k][34] == null? 0 : Integer.parseInt(resultQuery[k][34])) 				               			               	 
						.total(resultQuery[k][35] == null? 0 : Integer.parseInt(resultQuery[k][35])));   

			}; break;	
		case 34:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24]))
						.equip24(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25]))
						.equip25(resultQuery[k][26] == null? 0 : Integer.parseInt(resultQuery[k][26]))
						.equip26(resultQuery[k][27] == null? 0 : Integer.parseInt(resultQuery[k][27]))
						.equip27(resultQuery[k][28] == null? 0 : Integer.parseInt(resultQuery[k][28]))		 
						.equip28(resultQuery[k][29] == null? 0 : Integer.parseInt(resultQuery[k][29]))		 
						.equip29(resultQuery[k][30] == null? 0 : Integer.parseInt(resultQuery[k][30]))		 
						.equip30(resultQuery[k][31] == null? 0 : Integer.parseInt(resultQuery[k][31]))		 
						.equip31(resultQuery[k][32] == null? 0 : Integer.parseInt(resultQuery[k][32]))		 
						.equip32(resultQuery[k][33] == null? 0 : Integer.parseInt(resultQuery[k][33]))		 
						.equip33(resultQuery[k][34] == null? 0 : Integer.parseInt(resultQuery[k][34]))		 
						.equip34(resultQuery[k][35] == null? 0 : Integer.parseInt(resultQuery[k][35]))				         			               	 
						.total(resultQuery[k][36] == null? 0 : Integer.parseInt(resultQuery[k][36])));  

			}; break;	
		case 35:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24]))
						.equip24(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25]))
						.equip25(resultQuery[k][26] == null? 0 : Integer.parseInt(resultQuery[k][26]))
						.equip26(resultQuery[k][27] == null? 0 : Integer.parseInt(resultQuery[k][27]))
						.equip27(resultQuery[k][28] == null? 0 : Integer.parseInt(resultQuery[k][28]))		 
						.equip28(resultQuery[k][29] == null? 0 : Integer.parseInt(resultQuery[k][29]))		 
						.equip29(resultQuery[k][30] == null? 0 : Integer.parseInt(resultQuery[k][30]))		 
						.equip30(resultQuery[k][31] == null? 0 : Integer.parseInt(resultQuery[k][31]))		 
						.equip31(resultQuery[k][32] == null? 0 : Integer.parseInt(resultQuery[k][32]))		 
						.equip32(resultQuery[k][33] == null? 0 : Integer.parseInt(resultQuery[k][33]))		 
						.equip33(resultQuery[k][34] == null? 0 : Integer.parseInt(resultQuery[k][34]))		 
						.equip34(resultQuery[k][35] == null? 0 : Integer.parseInt(resultQuery[k][35]))		 
						.equip35(resultQuery[k][36] == null? 0 : Integer.parseInt(resultQuery[k][36])) 				                			               	 
						.total(resultQuery[k][37] == null? 0 : Integer.parseInt(resultQuery[k][37])));   

			}; break;	
		case 36:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24]))
						.equip24(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25]))
						.equip25(resultQuery[k][26] == null? 0 : Integer.parseInt(resultQuery[k][26]))
						.equip26(resultQuery[k][27] == null? 0 : Integer.parseInt(resultQuery[k][27]))
						.equip27(resultQuery[k][28] == null? 0 : Integer.parseInt(resultQuery[k][28]))		 
						.equip28(resultQuery[k][29] == null? 0 : Integer.parseInt(resultQuery[k][29]))		 
						.equip29(resultQuery[k][30] == null? 0 : Integer.parseInt(resultQuery[k][30]))		 
						.equip30(resultQuery[k][31] == null? 0 : Integer.parseInt(resultQuery[k][31]))		 
						.equip31(resultQuery[k][32] == null? 0 : Integer.parseInt(resultQuery[k][32]))		 
						.equip32(resultQuery[k][33] == null? 0 : Integer.parseInt(resultQuery[k][33]))		 
						.equip33(resultQuery[k][34] == null? 0 : Integer.parseInt(resultQuery[k][34]))		 
						.equip34(resultQuery[k][35] == null? 0 : Integer.parseInt(resultQuery[k][35]))		 
						.equip35(resultQuery[k][36] == null? 0 : Integer.parseInt(resultQuery[k][36]))		 
						.equip36(resultQuery[k][37] == null? 0 : Integer.parseInt(resultQuery[k][37])) 				                				               	 
						.total(resultQuery[k][38] == null? 0 : Integer.parseInt(resultQuery[k][38])));   

			}; break;	
		case 37:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24]))
						.equip24(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25]))
						.equip25(resultQuery[k][26] == null? 0 : Integer.parseInt(resultQuery[k][26]))
						.equip26(resultQuery[k][27] == null? 0 : Integer.parseInt(resultQuery[k][27]))
						.equip27(resultQuery[k][28] == null? 0 : Integer.parseInt(resultQuery[k][28]))		 
						.equip28(resultQuery[k][29] == null? 0 : Integer.parseInt(resultQuery[k][29]))		 
						.equip29(resultQuery[k][30] == null? 0 : Integer.parseInt(resultQuery[k][30]))		 
						.equip30(resultQuery[k][31] == null? 0 : Integer.parseInt(resultQuery[k][31]))		 
						.equip31(resultQuery[k][32] == null? 0 : Integer.parseInt(resultQuery[k][32]))		 
						.equip32(resultQuery[k][33] == null? 0 : Integer.parseInt(resultQuery[k][33]))		 
						.equip33(resultQuery[k][34] == null? 0 : Integer.parseInt(resultQuery[k][34]))		 
						.equip34(resultQuery[k][35] == null? 0 : Integer.parseInt(resultQuery[k][35]))		 
						.equip35(resultQuery[k][36] == null? 0 : Integer.parseInt(resultQuery[k][36]))		 
						.equip36(resultQuery[k][37] == null? 0 : Integer.parseInt(resultQuery[k][37]))
						.equip37(resultQuery[k][38] == null? 0 : Integer.parseInt(resultQuery[k][38]))				             				               	 
						.total(resultQuery[k][39] == null? 0 : Integer.parseInt(resultQuery[k][39])));  

			}; break;	
		case 38:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24]))
						.equip24(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25]))
						.equip25(resultQuery[k][26] == null? 0 : Integer.parseInt(resultQuery[k][26]))
						.equip26(resultQuery[k][27] == null? 0 : Integer.parseInt(resultQuery[k][27]))
						.equip27(resultQuery[k][28] == null? 0 : Integer.parseInt(resultQuery[k][28]))		 
						.equip28(resultQuery[k][29] == null? 0 : Integer.parseInt(resultQuery[k][29]))		 
						.equip29(resultQuery[k][30] == null? 0 : Integer.parseInt(resultQuery[k][30]))		 
						.equip30(resultQuery[k][31] == null? 0 : Integer.parseInt(resultQuery[k][31]))		 
						.equip31(resultQuery[k][32] == null? 0 : Integer.parseInt(resultQuery[k][32]))		 
						.equip32(resultQuery[k][33] == null? 0 : Integer.parseInt(resultQuery[k][33]))		 
						.equip33(resultQuery[k][34] == null? 0 : Integer.parseInt(resultQuery[k][34]))		 
						.equip34(resultQuery[k][35] == null? 0 : Integer.parseInt(resultQuery[k][35]))		 
						.equip35(resultQuery[k][36] == null? 0 : Integer.parseInt(resultQuery[k][36]))		 
						.equip36(resultQuery[k][37] == null? 0 : Integer.parseInt(resultQuery[k][37]))
						.equip37(resultQuery[k][38] == null? 0 : Integer.parseInt(resultQuery[k][38]))		 
						.equip38(resultQuery[k][39] == null? 0 : Integer.parseInt(resultQuery[k][39]))				             			               	 
						.total(resultQuery[k][40] == null? 0 : Integer.parseInt(resultQuery[k][40])));  

			}; break;	
		case 39:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24]))
						.equip24(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25]))
						.equip25(resultQuery[k][26] == null? 0 : Integer.parseInt(resultQuery[k][26]))
						.equip26(resultQuery[k][27] == null? 0 : Integer.parseInt(resultQuery[k][27]))
						.equip27(resultQuery[k][28] == null? 0 : Integer.parseInt(resultQuery[k][28]))		 
						.equip28(resultQuery[k][29] == null? 0 : Integer.parseInt(resultQuery[k][29]))		 
						.equip29(resultQuery[k][30] == null? 0 : Integer.parseInt(resultQuery[k][30]))		 
						.equip30(resultQuery[k][31] == null? 0 : Integer.parseInt(resultQuery[k][31]))		 
						.equip31(resultQuery[k][32] == null? 0 : Integer.parseInt(resultQuery[k][32]))		 
						.equip32(resultQuery[k][33] == null? 0 : Integer.parseInt(resultQuery[k][33]))		 
						.equip33(resultQuery[k][34] == null? 0 : Integer.parseInt(resultQuery[k][34]))		 
						.equip34(resultQuery[k][35] == null? 0 : Integer.parseInt(resultQuery[k][35]))		 
						.equip35(resultQuery[k][36] == null? 0 : Integer.parseInt(resultQuery[k][36]))		 
						.equip36(resultQuery[k][37] == null? 0 : Integer.parseInt(resultQuery[k][37]))
						.equip37(resultQuery[k][38] == null? 0 : Integer.parseInt(resultQuery[k][38]))		 
						.equip38(resultQuery[k][39] == null? 0 : Integer.parseInt(resultQuery[k][39]))		 
						.equip39(resultQuery[k][40] == null? 0 : Integer.parseInt(resultQuery[k][40]))					             			               	 
						.total(resultQuery[k][41] == null? 0 : Integer.parseInt(resultQuery[k][41])));    

			}; break;	
		case 40:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24]))
						.equip24(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25]))
						.equip25(resultQuery[k][26] == null? 0 : Integer.parseInt(resultQuery[k][26]))
						.equip26(resultQuery[k][27] == null? 0 : Integer.parseInt(resultQuery[k][27]))
						.equip27(resultQuery[k][28] == null? 0 : Integer.parseInt(resultQuery[k][28]))		 
						.equip28(resultQuery[k][29] == null? 0 : Integer.parseInt(resultQuery[k][29]))		 
						.equip29(resultQuery[k][30] == null? 0 : Integer.parseInt(resultQuery[k][30]))		 
						.equip30(resultQuery[k][31] == null? 0 : Integer.parseInt(resultQuery[k][31]))		 
						.equip31(resultQuery[k][32] == null? 0 : Integer.parseInt(resultQuery[k][32]))		 
						.equip32(resultQuery[k][33] == null? 0 : Integer.parseInt(resultQuery[k][33]))		 
						.equip33(resultQuery[k][34] == null? 0 : Integer.parseInt(resultQuery[k][34]))		 
						.equip34(resultQuery[k][35] == null? 0 : Integer.parseInt(resultQuery[k][35]))		 
						.equip35(resultQuery[k][36] == null? 0 : Integer.parseInt(resultQuery[k][36]))		 
						.equip36(resultQuery[k][37] == null? 0 : Integer.parseInt(resultQuery[k][37]))
						.equip37(resultQuery[k][38] == null? 0 : Integer.parseInt(resultQuery[k][38]))		 
						.equip38(resultQuery[k][39] == null? 0 : Integer.parseInt(resultQuery[k][39]))		 
						.equip39(resultQuery[k][40] == null? 0 : Integer.parseInt(resultQuery[k][40]))		 
						.equip40(resultQuery[k][41] == null? 0 : Integer.parseInt(resultQuery[k][41]))				                			               	 
						.total(resultQuery[k][42] == null? 0 : Integer.parseInt(resultQuery[k][42])));   

			}; break;	
		case 41:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24]))
						.equip24(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25]))
						.equip25(resultQuery[k][26] == null? 0 : Integer.parseInt(resultQuery[k][26]))
						.equip26(resultQuery[k][27] == null? 0 : Integer.parseInt(resultQuery[k][27]))
						.equip27(resultQuery[k][28] == null? 0 : Integer.parseInt(resultQuery[k][28]))		 
						.equip28(resultQuery[k][29] == null? 0 : Integer.parseInt(resultQuery[k][29]))		 
						.equip29(resultQuery[k][30] == null? 0 : Integer.parseInt(resultQuery[k][30]))		 
						.equip30(resultQuery[k][31] == null? 0 : Integer.parseInt(resultQuery[k][31]))		 
						.equip31(resultQuery[k][32] == null? 0 : Integer.parseInt(resultQuery[k][32]))		 
						.equip32(resultQuery[k][33] == null? 0 : Integer.parseInt(resultQuery[k][33]))		 
						.equip33(resultQuery[k][34] == null? 0 : Integer.parseInt(resultQuery[k][34]))		 
						.equip34(resultQuery[k][35] == null? 0 : Integer.parseInt(resultQuery[k][35]))		 
						.equip35(resultQuery[k][36] == null? 0 : Integer.parseInt(resultQuery[k][36]))		 
						.equip36(resultQuery[k][37] == null? 0 : Integer.parseInt(resultQuery[k][37]))
						.equip37(resultQuery[k][38] == null? 0 : Integer.parseInt(resultQuery[k][38]))		 
						.equip38(resultQuery[k][39] == null? 0 : Integer.parseInt(resultQuery[k][39]))		 
						.equip39(resultQuery[k][40] == null? 0 : Integer.parseInt(resultQuery[k][40]))		 
						.equip40(resultQuery[k][41] == null? 0 : Integer.parseInt(resultQuery[k][41]))		 
						.equip41(resultQuery[k][42] == null? 0 : Integer.parseInt(resultQuery[k][42]))				               			               			               	 
						.total(resultQuery[k][43] == null? 0 : Integer.parseInt(resultQuery[k][43])));  

			}; break;	
		case 42:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24]))
						.equip24(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25]))
						.equip25(resultQuery[k][26] == null? 0 : Integer.parseInt(resultQuery[k][26]))
						.equip26(resultQuery[k][27] == null? 0 : Integer.parseInt(resultQuery[k][27]))
						.equip27(resultQuery[k][28] == null? 0 : Integer.parseInt(resultQuery[k][28]))		 
						.equip28(resultQuery[k][29] == null? 0 : Integer.parseInt(resultQuery[k][29]))		 
						.equip29(resultQuery[k][30] == null? 0 : Integer.parseInt(resultQuery[k][30]))		 
						.equip30(resultQuery[k][31] == null? 0 : Integer.parseInt(resultQuery[k][31]))		 
						.equip31(resultQuery[k][32] == null? 0 : Integer.parseInt(resultQuery[k][32]))		 
						.equip32(resultQuery[k][33] == null? 0 : Integer.parseInt(resultQuery[k][33]))		 
						.equip33(resultQuery[k][34] == null? 0 : Integer.parseInt(resultQuery[k][34]))		 
						.equip34(resultQuery[k][35] == null? 0 : Integer.parseInt(resultQuery[k][35]))		 
						.equip35(resultQuery[k][36] == null? 0 : Integer.parseInt(resultQuery[k][36]))		 
						.equip36(resultQuery[k][37] == null? 0 : Integer.parseInt(resultQuery[k][37]))
						.equip37(resultQuery[k][38] == null? 0 : Integer.parseInt(resultQuery[k][38]))		 
						.equip38(resultQuery[k][39] == null? 0 : Integer.parseInt(resultQuery[k][39]))		 
						.equip39(resultQuery[k][40] == null? 0 : Integer.parseInt(resultQuery[k][40]))		 
						.equip40(resultQuery[k][41] == null? 0 : Integer.parseInt(resultQuery[k][41]))		 
						.equip41(resultQuery[k][42] == null? 0 : Integer.parseInt(resultQuery[k][42]))		 
						.equip42(resultQuery[k][43] == null? 0 : Integer.parseInt(resultQuery[k][43])) 				              			               	 
						.total(resultQuery[k][44] == null? 0 : Integer.parseInt(resultQuery[k][44])));    

			}; break;	
		case 43:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24]))
						.equip24(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25]))
						.equip25(resultQuery[k][26] == null? 0 : Integer.parseInt(resultQuery[k][26]))
						.equip26(resultQuery[k][27] == null? 0 : Integer.parseInt(resultQuery[k][27]))
						.equip27(resultQuery[k][28] == null? 0 : Integer.parseInt(resultQuery[k][28]))		 
						.equip28(resultQuery[k][29] == null? 0 : Integer.parseInt(resultQuery[k][29]))		 
						.equip29(resultQuery[k][30] == null? 0 : Integer.parseInt(resultQuery[k][30]))		 
						.equip30(resultQuery[k][31] == null? 0 : Integer.parseInt(resultQuery[k][31]))		 
						.equip31(resultQuery[k][32] == null? 0 : Integer.parseInt(resultQuery[k][32]))		 
						.equip32(resultQuery[k][33] == null? 0 : Integer.parseInt(resultQuery[k][33]))		 
						.equip33(resultQuery[k][34] == null? 0 : Integer.parseInt(resultQuery[k][34]))		 
						.equip34(resultQuery[k][35] == null? 0 : Integer.parseInt(resultQuery[k][35]))		 
						.equip35(resultQuery[k][36] == null? 0 : Integer.parseInt(resultQuery[k][36]))		 
						.equip36(resultQuery[k][37] == null? 0 : Integer.parseInt(resultQuery[k][37]))
						.equip37(resultQuery[k][38] == null? 0 : Integer.parseInt(resultQuery[k][38]))		 
						.equip38(resultQuery[k][39] == null? 0 : Integer.parseInt(resultQuery[k][39]))		 
						.equip39(resultQuery[k][40] == null? 0 : Integer.parseInt(resultQuery[k][40]))		 
						.equip40(resultQuery[k][41] == null? 0 : Integer.parseInt(resultQuery[k][41]))		 
						.equip41(resultQuery[k][42] == null? 0 : Integer.parseInt(resultQuery[k][42]))		 
						.equip42(resultQuery[k][43] == null? 0 : Integer.parseInt(resultQuery[k][43]))		 
						.equip43(resultQuery[k][44] == null? 0 : Integer.parseInt(resultQuery[k][44])) 				             			               	 
						.total(resultQuery[k][45] == null? 0 : Integer.parseInt(resultQuery[k][45])));    

			}; break;	
		case 44:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24]))
						.equip24(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25]))
						.equip25(resultQuery[k][26] == null? 0 : Integer.parseInt(resultQuery[k][26]))
						.equip26(resultQuery[k][27] == null? 0 : Integer.parseInt(resultQuery[k][27]))
						.equip27(resultQuery[k][28] == null? 0 : Integer.parseInt(resultQuery[k][28]))		 
						.equip28(resultQuery[k][29] == null? 0 : Integer.parseInt(resultQuery[k][29]))		 
						.equip29(resultQuery[k][30] == null? 0 : Integer.parseInt(resultQuery[k][30]))		 
						.equip30(resultQuery[k][31] == null? 0 : Integer.parseInt(resultQuery[k][31]))		 
						.equip31(resultQuery[k][32] == null? 0 : Integer.parseInt(resultQuery[k][32]))		 
						.equip32(resultQuery[k][33] == null? 0 : Integer.parseInt(resultQuery[k][33]))		 
						.equip33(resultQuery[k][34] == null? 0 : Integer.parseInt(resultQuery[k][34]))		 
						.equip34(resultQuery[k][35] == null? 0 : Integer.parseInt(resultQuery[k][35]))		 
						.equip35(resultQuery[k][36] == null? 0 : Integer.parseInt(resultQuery[k][36]))		 
						.equip36(resultQuery[k][37] == null? 0 : Integer.parseInt(resultQuery[k][37]))
						.equip37(resultQuery[k][38] == null? 0 : Integer.parseInt(resultQuery[k][38]))		 
						.equip38(resultQuery[k][39] == null? 0 : Integer.parseInt(resultQuery[k][39]))		 
						.equip39(resultQuery[k][40] == null? 0 : Integer.parseInt(resultQuery[k][40]))		 
						.equip40(resultQuery[k][41] == null? 0 : Integer.parseInt(resultQuery[k][41]))		 
						.equip41(resultQuery[k][42] == null? 0 : Integer.parseInt(resultQuery[k][42]))		 
						.equip42(resultQuery[k][43] == null? 0 : Integer.parseInt(resultQuery[k][43]))		 
						.equip43(resultQuery[k][44] == null? 0 : Integer.parseInt(resultQuery[k][44]))		 
						.equip44(resultQuery[k][45] == null? 0 : Integer.parseInt(resultQuery[k][45]))	 				          	 				               			               	 
						.total(resultQuery[k][46] == null? 0 : Integer.parseInt(resultQuery[k][46])));   

			}; break;	
		case 45:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   				            
						.dateTime(resultQuery[k][1])
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24]))
						.equip24(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25]))
						.equip25(resultQuery[k][26] == null? 0 : Integer.parseInt(resultQuery[k][26]))
						.equip26(resultQuery[k][27] == null? 0 : Integer.parseInt(resultQuery[k][27]))
						.equip27(resultQuery[k][28] == null? 0 : Integer.parseInt(resultQuery[k][28]))		 
						.equip28(resultQuery[k][29] == null? 0 : Integer.parseInt(resultQuery[k][29]))		 
						.equip29(resultQuery[k][30] == null? 0 : Integer.parseInt(resultQuery[k][30]))		 
						.equip30(resultQuery[k][31] == null? 0 : Integer.parseInt(resultQuery[k][31]))		 
						.equip31(resultQuery[k][32] == null? 0 : Integer.parseInt(resultQuery[k][32]))		 
						.equip32(resultQuery[k][33] == null? 0 : Integer.parseInt(resultQuery[k][33]))		 
						.equip33(resultQuery[k][34] == null? 0 : Integer.parseInt(resultQuery[k][34]))		 
						.equip34(resultQuery[k][35] == null? 0 : Integer.parseInt(resultQuery[k][35]))		 
						.equip35(resultQuery[k][36] == null? 0 : Integer.parseInt(resultQuery[k][36]))		 
						.equip36(resultQuery[k][37] == null? 0 : Integer.parseInt(resultQuery[k][37]))
						.equip37(resultQuery[k][38] == null? 0 : Integer.parseInt(resultQuery[k][38]))		 
						.equip38(resultQuery[k][39] == null? 0 : Integer.parseInt(resultQuery[k][39]))		 
						.equip39(resultQuery[k][40] == null? 0 : Integer.parseInt(resultQuery[k][40]))		 
						.equip40(resultQuery[k][41] == null? 0 : Integer.parseInt(resultQuery[k][41]))		 
						.equip41(resultQuery[k][42] == null? 0 : Integer.parseInt(resultQuery[k][42]))		 
						.equip42(resultQuery[k][43] == null? 0 : Integer.parseInt(resultQuery[k][43]))		 
						.equip43(resultQuery[k][44] == null? 0 : Integer.parseInt(resultQuery[k][44]))		 
						.equip44(resultQuery[k][45] == null? 0 : Integer.parseInt(resultQuery[k][45]))		 
						.equip45(resultQuery[k][46] == null? 0 : Integer.parseInt(resultQuery[k][46])) 				              			               	 
						.total(resultQuery[k][47] == null? 0 : Integer.parseInt(resultQuery[k][47])));    

			}; break;	
		case 46:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])							 
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24]))
						.equip24(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25]))
						.equip25(resultQuery[k][26] == null? 0 : Integer.parseInt(resultQuery[k][26]))
						.equip26(resultQuery[k][27] == null? 0 : Integer.parseInt(resultQuery[k][27]))
						.equip27(resultQuery[k][28] == null? 0 : Integer.parseInt(resultQuery[k][28]))		 
						.equip28(resultQuery[k][29] == null? 0 : Integer.parseInt(resultQuery[k][29]))		 
						.equip29(resultQuery[k][30] == null? 0 : Integer.parseInt(resultQuery[k][30]))		 
						.equip30(resultQuery[k][31] == null? 0 : Integer.parseInt(resultQuery[k][31]))		 
						.equip31(resultQuery[k][32] == null? 0 : Integer.parseInt(resultQuery[k][32]))		 
						.equip32(resultQuery[k][33] == null? 0 : Integer.parseInt(resultQuery[k][33]))		 
						.equip33(resultQuery[k][34] == null? 0 : Integer.parseInt(resultQuery[k][34]))		 
						.equip34(resultQuery[k][35] == null? 0 : Integer.parseInt(resultQuery[k][35]))		 
						.equip35(resultQuery[k][36] == null? 0 : Integer.parseInt(resultQuery[k][36]))		 
						.equip36(resultQuery[k][37] == null? 0 : Integer.parseInt(resultQuery[k][37]))
						.equip37(resultQuery[k][38] == null? 0 : Integer.parseInt(resultQuery[k][38]))		 
						.equip38(resultQuery[k][39] == null? 0 : Integer.parseInt(resultQuery[k][39]))		 
						.equip39(resultQuery[k][40] == null? 0 : Integer.parseInt(resultQuery[k][40]))		 
						.equip40(resultQuery[k][41] == null? 0 : Integer.parseInt(resultQuery[k][41]))		 
						.equip41(resultQuery[k][42] == null? 0 : Integer.parseInt(resultQuery[k][42]))		 
						.equip42(resultQuery[k][43] == null? 0 : Integer.parseInt(resultQuery[k][43]))		 
						.equip43(resultQuery[k][44] == null? 0 : Integer.parseInt(resultQuery[k][44]))		 
						.equip44(resultQuery[k][45] == null? 0 : Integer.parseInt(resultQuery[k][45]))		 
						.equip45(resultQuery[k][46] == null? 0 : Integer.parseInt(resultQuery[k][46]))		 
						.equip46(resultQuery[k][47] == null? 0 : Integer.parseInt(resultQuery[k][47]))				               	 
						.total(resultQuery[k][48] == null? 0 : Integer.parseInt(resultQuery[k][48])));  

			}; break;	
		case 47:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])
						.dateTime(resultQuery[k][1])							 
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24]))
						.equip24(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25]))
						.equip25(resultQuery[k][26] == null? 0 : Integer.parseInt(resultQuery[k][26]))
						.equip26(resultQuery[k][27] == null? 0 : Integer.parseInt(resultQuery[k][27]))
						.equip27(resultQuery[k][28] == null? 0 : Integer.parseInt(resultQuery[k][28]))		 
						.equip28(resultQuery[k][29] == null? 0 : Integer.parseInt(resultQuery[k][29]))		 
						.equip29(resultQuery[k][30] == null? 0 : Integer.parseInt(resultQuery[k][30]))		 
						.equip30(resultQuery[k][31] == null? 0 : Integer.parseInt(resultQuery[k][31]))		 
						.equip31(resultQuery[k][32] == null? 0 : Integer.parseInt(resultQuery[k][32]))		 
						.equip32(resultQuery[k][33] == null? 0 : Integer.parseInt(resultQuery[k][33]))		 
						.equip33(resultQuery[k][34] == null? 0 : Integer.parseInt(resultQuery[k][34]))		 
						.equip34(resultQuery[k][35] == null? 0 : Integer.parseInt(resultQuery[k][35]))		 
						.equip35(resultQuery[k][36] == null? 0 : Integer.parseInt(resultQuery[k][36]))		 
						.equip36(resultQuery[k][37] == null? 0 : Integer.parseInt(resultQuery[k][37]))
						.equip37(resultQuery[k][38] == null? 0 : Integer.parseInt(resultQuery[k][38]))		 
						.equip38(resultQuery[k][39] == null? 0 : Integer.parseInt(resultQuery[k][39]))		 
						.equip39(resultQuery[k][40] == null? 0 : Integer.parseInt(resultQuery[k][40]))		 
						.equip40(resultQuery[k][41] == null? 0 : Integer.parseInt(resultQuery[k][41]))		 
						.equip41(resultQuery[k][42] == null? 0 : Integer.parseInt(resultQuery[k][42]))		 
						.equip42(resultQuery[k][43] == null? 0 : Integer.parseInt(resultQuery[k][43]))		 
						.equip43(resultQuery[k][44] == null? 0 : Integer.parseInt(resultQuery[k][44]))		 
						.equip44(resultQuery[k][45] == null? 0 : Integer.parseInt(resultQuery[k][45]))		 
						.equip45(resultQuery[k][46] == null? 0 : Integer.parseInt(resultQuery[k][46]))		 
						.equip46(resultQuery[k][47] == null? 0 : Integer.parseInt(resultQuery[k][47]))		 
						.equip47(resultQuery[k][48] == null? 0 : Integer.parseInt(resultQuery[k][48])) 				               	 
						.total(resultQuery[k][49] == null? 0 : Integer.parseInt(resultQuery[k][49])));    

			}; break;	
		case 48:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])  
						.dateTime(resultQuery[k][1])							 
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24]))
						.equip24(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25]))
						.equip25(resultQuery[k][26] == null? 0 : Integer.parseInt(resultQuery[k][26]))
						.equip26(resultQuery[k][27] == null? 0 : Integer.parseInt(resultQuery[k][27]))
						.equip27(resultQuery[k][28] == null? 0 : Integer.parseInt(resultQuery[k][28]))		 
						.equip28(resultQuery[k][29] == null? 0 : Integer.parseInt(resultQuery[k][29]))		 
						.equip29(resultQuery[k][30] == null? 0 : Integer.parseInt(resultQuery[k][30]))		 
						.equip30(resultQuery[k][31] == null? 0 : Integer.parseInt(resultQuery[k][31]))		 
						.equip31(resultQuery[k][32] == null? 0 : Integer.parseInt(resultQuery[k][32]))		 
						.equip32(resultQuery[k][33] == null? 0 : Integer.parseInt(resultQuery[k][33]))		 
						.equip33(resultQuery[k][34] == null? 0 : Integer.parseInt(resultQuery[k][34]))		 
						.equip34(resultQuery[k][35] == null? 0 : Integer.parseInt(resultQuery[k][35]))		 
						.equip35(resultQuery[k][36] == null? 0 : Integer.parseInt(resultQuery[k][36]))		 
						.equip36(resultQuery[k][37] == null? 0 : Integer.parseInt(resultQuery[k][37]))
						.equip37(resultQuery[k][38] == null? 0 : Integer.parseInt(resultQuery[k][38]))		 
						.equip38(resultQuery[k][39] == null? 0 : Integer.parseInt(resultQuery[k][39]))		 
						.equip39(resultQuery[k][40] == null? 0 : Integer.parseInt(resultQuery[k][40]))		 
						.equip40(resultQuery[k][41] == null? 0 : Integer.parseInt(resultQuery[k][41]))		 
						.equip41(resultQuery[k][42] == null? 0 : Integer.parseInt(resultQuery[k][42]))		 
						.equip42(resultQuery[k][43] == null? 0 : Integer.parseInt(resultQuery[k][43]))		 
						.equip43(resultQuery[k][44] == null? 0 : Integer.parseInt(resultQuery[k][44]))		 
						.equip44(resultQuery[k][45] == null? 0 : Integer.parseInt(resultQuery[k][45]))		 
						.equip45(resultQuery[k][46] == null? 0 : Integer.parseInt(resultQuery[k][46]))		 
						.equip46(resultQuery[k][47] == null? 0 : Integer.parseInt(resultQuery[k][47]))		 
						.equip47(resultQuery[k][48] == null? 0 : Integer.parseInt(resultQuery[k][48]))		 
						.equip48(resultQuery[k][49] == null? 0 : Integer.parseInt(resultQuery[k][49])) 				               		 
						.total(resultQuery[k][50] == null? 0 : Integer.parseInt(resultQuery[k][50])));  

			}; break;	
		case 49:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])
						.dateTime(resultQuery[k][1])							 
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24]))
						.equip24(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25]))
						.equip25(resultQuery[k][26] == null? 0 : Integer.parseInt(resultQuery[k][26]))
						.equip26(resultQuery[k][27] == null? 0 : Integer.parseInt(resultQuery[k][27]))
						.equip27(resultQuery[k][28] == null? 0 : Integer.parseInt(resultQuery[k][28]))		 
						.equip28(resultQuery[k][29] == null? 0 : Integer.parseInt(resultQuery[k][29]))		 
						.equip29(resultQuery[k][30] == null? 0 : Integer.parseInt(resultQuery[k][30]))		 
						.equip30(resultQuery[k][31] == null? 0 : Integer.parseInt(resultQuery[k][31]))		 
						.equip31(resultQuery[k][32] == null? 0 : Integer.parseInt(resultQuery[k][32]))		 
						.equip32(resultQuery[k][33] == null? 0 : Integer.parseInt(resultQuery[k][33]))		 
						.equip33(resultQuery[k][34] == null? 0 : Integer.parseInt(resultQuery[k][34]))		 
						.equip34(resultQuery[k][35] == null? 0 : Integer.parseInt(resultQuery[k][35]))		 
						.equip35(resultQuery[k][36] == null? 0 : Integer.parseInt(resultQuery[k][36]))		 
						.equip36(resultQuery[k][37] == null? 0 : Integer.parseInt(resultQuery[k][37]))
						.equip37(resultQuery[k][38] == null? 0 : Integer.parseInt(resultQuery[k][38]))		 
						.equip38(resultQuery[k][39] == null? 0 : Integer.parseInt(resultQuery[k][39]))		 
						.equip39(resultQuery[k][40] == null? 0 : Integer.parseInt(resultQuery[k][40]))		 
						.equip40(resultQuery[k][41] == null? 0 : Integer.parseInt(resultQuery[k][41]))		 
						.equip41(resultQuery[k][42] == null? 0 : Integer.parseInt(resultQuery[k][42]))		 
						.equip42(resultQuery[k][43] == null? 0 : Integer.parseInt(resultQuery[k][43]))		 
						.equip43(resultQuery[k][44] == null? 0 : Integer.parseInt(resultQuery[k][44]))		 
						.equip44(resultQuery[k][45] == null? 0 : Integer.parseInt(resultQuery[k][45]))		 
						.equip45(resultQuery[k][46] == null? 0 : Integer.parseInt(resultQuery[k][46]))		 
						.equip46(resultQuery[k][47] == null? 0 : Integer.parseInt(resultQuery[k][47]))		 
						.equip47(resultQuery[k][48] == null? 0 : Integer.parseInt(resultQuery[k][48]))		 
						.equip48(resultQuery[k][49] == null? 0 : Integer.parseInt(resultQuery[k][49]))
						.equip49(resultQuery[k][50] == null? 0 : Integer.parseInt(resultQuery[k][50]))						 
						.total(resultQuery[k][51] == null? 0 : Integer.parseInt(resultQuery[k][51])));  

			}; break;	
		case 50:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])						 
						.equip1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.equip2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.equip3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.equip4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.equip5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.equip6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.equip7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.equip8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.equip9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.equip10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.equip11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.equip12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.equip13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
						.equip14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
						.equip15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
						.equip16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
						.equip17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
						.equip18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))
						.equip19(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20]))
						.equip20(resultQuery[k][21] == null? 0 : Integer.parseInt(resultQuery[k][21]))
						.equip21(resultQuery[k][22] == null? 0 : Integer.parseInt(resultQuery[k][22]))
						.equip22(resultQuery[k][23] == null? 0 : Integer.parseInt(resultQuery[k][23]))
						.equip23(resultQuery[k][24] == null? 0 : Integer.parseInt(resultQuery[k][24]))
						.equip24(resultQuery[k][25] == null? 0 : Integer.parseInt(resultQuery[k][25]))
						.equip25(resultQuery[k][26] == null? 0 : Integer.parseInt(resultQuery[k][26]))
						.equip26(resultQuery[k][27] == null? 0 : Integer.parseInt(resultQuery[k][27]))
						.equip27(resultQuery[k][28] == null? 0 : Integer.parseInt(resultQuery[k][28]))		 
						.equip28(resultQuery[k][29] == null? 0 : Integer.parseInt(resultQuery[k][29]))		 
						.equip29(resultQuery[k][30] == null? 0 : Integer.parseInt(resultQuery[k][30]))		 
						.equip30(resultQuery[k][31] == null? 0 : Integer.parseInt(resultQuery[k][31]))		 
						.equip31(resultQuery[k][32] == null? 0 : Integer.parseInt(resultQuery[k][32]))		 
						.equip32(resultQuery[k][33] == null? 0 : Integer.parseInt(resultQuery[k][33]))		 
						.equip33(resultQuery[k][34] == null? 0 : Integer.parseInt(resultQuery[k][34]))		 
						.equip34(resultQuery[k][35] == null? 0 : Integer.parseInt(resultQuery[k][35]))		 
						.equip35(resultQuery[k][36] == null? 0 : Integer.parseInt(resultQuery[k][36]))		 
						.equip36(resultQuery[k][37] == null? 0 : Integer.parseInt(resultQuery[k][37]))
						.equip37(resultQuery[k][38] == null? 0 : Integer.parseInt(resultQuery[k][38]))		 
						.equip38(resultQuery[k][39] == null? 0 : Integer.parseInt(resultQuery[k][39]))		 
						.equip39(resultQuery[k][40] == null? 0 : Integer.parseInt(resultQuery[k][40]))		 
						.equip40(resultQuery[k][41] == null? 0 : Integer.parseInt(resultQuery[k][41]))		 
						.equip41(resultQuery[k][42] == null? 0 : Integer.parseInt(resultQuery[k][42]))		 
						.equip42(resultQuery[k][43] == null? 0 : Integer.parseInt(resultQuery[k][43]))		 
						.equip43(resultQuery[k][44] == null? 0 : Integer.parseInt(resultQuery[k][44]))		 
						.equip44(resultQuery[k][45] == null? 0 : Integer.parseInt(resultQuery[k][45]))		 
						.equip45(resultQuery[k][46] == null? 0 : Integer.parseInt(resultQuery[k][46]))		 
						.equip46(resultQuery[k][47] == null? 0 : Integer.parseInt(resultQuery[k][47]))		 
						.equip47(resultQuery[k][48] == null? 0 : Integer.parseInt(resultQuery[k][48]))		 
						.equip48(resultQuery[k][49] == null? 0 : Integer.parseInt(resultQuery[k][49]))
						.equip49(resultQuery[k][50] == null? 0 : Integer.parseInt(resultQuery[k][50]))		 
						.equip50(resultQuery[k][51] == null? 0 : Integer.parseInt(resultQuery[k][51]))		 
						.total(resultQuery[k][52] == null? 0 : Integer.parseInt(resultQuery[k][52])));  

			}; break;	
		}     			 
	}  

	/* COUNT VEHICLES PERIOD -- TYPE = 2 */

	public void countVehiclesPeriodBuilder() {


		for(int k = 0; k < getNumRegisters(); k++) {      

			resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
					.dateTime(resultQuery[k][1])   
					.lightDir1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))					
					.motosDir1(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))	
					.heavyDir1(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
					.lightDir2(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))					
					.motosDir2(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
					.heavyDir2(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
					.total(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))); 		 			 
		}    			 

	}

	/* MONTH FLOW -- TYPE = 3 */      

	public void monthFlowBuilder() {

		for(int k = 0; k < getNumRegisters(); k++) {      

			resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
					.dateTime(resultQuery[k][1])		 					            
					.lightDir1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
					.heavyDir1(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
					.motosDir1(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))	 		 				                
					.lightDir2(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
					.heavyDir2(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6])) 
					.motosDir2(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
					.speed1(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
					.speed2(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9])));	
		}             

	}

	/* PERIOD FLOW -- TYPE = 4 */   

	public void periodFlowBuilder() {


		for(int k = 0; k < getNumRegisters(); k++) {      

			resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
					.dateTime(resultQuery[k][1]) 
					.equipment(resultQuery[k][6])		 					            
					.lightDir1(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
					.heavyDir1(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
					.motosDir1(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))	
					.total1(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))	
					.speed1(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))		 				               
					.lightDir2(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
					.heavyDir2(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
					.motosDir2(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))		 				            
					.total2(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14])) 
					.speed2(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15])));
		}    	
	}      

	/* WEIGHING -- TYPE = 5 */  

	public void weighingBuilder() {


		/*if(classLength == 1) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])  
						.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])));  				                            	 				                 

			}

		} else if(classLength == 2) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])  
						.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.class2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3])));				                            	 				                 

			}

		} else if(classLength == 3) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])  
						.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.class2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3])) 
						.class3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4])));  	 				                           		 				                 

			}

		} else if(classLength == 4) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])  
						.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.class2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3])) 
						.class3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.class4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))); 		 				                			 				                          		 				                 

			}

		}else if(classLength == 5) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])  
						.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.class2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3])) 
						.class3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.class4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.class5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6])));                       				                              		 				                 

			}

		} else if(classLength == 6) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])  
						.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.class2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3])) 
						.class3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.class4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.class5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.class6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7])));   

			}

		} else if(classLength == 7) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])  
						.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.class2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3])) 
						.class3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.class4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.class5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.class6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.class7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))); 		 				                           			 	 	  				                              		 				                 

			}

		} else if(classLength == 8) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])  
						.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.class2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3])) 
						.class3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.class4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.class5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.class6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.class7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.class8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9])));   		 				          

			}

		} else if(classLength == 9) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])  
						.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.class2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3])) 
						.class3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.class4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.class5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.class6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.class7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.class8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))    		 				          
						.class9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10])));

			}

		} else if(classLength == 10) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])  
						.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.class2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3])) 
						.class3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.class4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.class5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.class6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.class7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.class8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))    		 				          
						.class9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.class10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11])));		 	 	  				                            		 				                 

			}

		} else if(classLength == 11) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])  
						.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.class2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3])) 
						.class3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.class4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.class5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.class6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.class7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.class8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))    		 				          
						.class9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.class10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.class11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12])));		 	 	  				                               		 				                 

			}

		} else if(classLength == 12) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])  
						.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.class2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3])) 
						.class3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.class4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.class5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.class6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.class7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.class8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))    		 				          
						.class9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.class10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.class11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.class12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13])));		 	 	  				                           		 				                 

			}

		} else if(classLength == 13) { */

			for(int k = 0; k < getNumRegisters(); k++) {  

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])  
						.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.class2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3])) 
						.class3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.class4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.class5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.class6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.class7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.class8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))    		 				          
						.class9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.class10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.class11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.class12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.class13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14])));	 	 				                 

			}

		//}    		                  
	}

	/* CLASS -- TYPE = 6 */ 

	public void classesBuilder() {


		/*if(classLength == 1) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])  
						.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))  				                            	 				                 
						.total(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3])));    				    				 
			}

		} else if(classLength == 2) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])  
						.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.class2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))  	 				                            		 				                 
						.total(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4])));    				    				 
			}

		} else if(classLength == 3) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])  
						.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.class2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3])) 
						.class3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4])) 	 	 				                           		 				                 
						.total(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5])));    				    				 
			}

		} else if(classLength == 4) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])  
						.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.class2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3])) 
						.class3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.class4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5])) 	 	 	 				                          		 				                 
						.total(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6])));    				    				 
			}

		}else if(classLength == 5) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])  
						.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.class2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3])) 
						.class3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.class4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.class5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6])) 	 	 	  				                              		 				                 
						.total(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7])));    				    				 
			}

		} else if(classLength == 6) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])  
						.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.class2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3])) 
						.class3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.class4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.class5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.class6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7])) 	 	 	  				                             		 				                 
						.total(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8])));    				    				 
			}

		} else if(classLength == 7) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])  
						.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.class2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3])) 
						.class3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.class4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.class5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.class6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.class7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8])) 	 	 	  				                              		 				                 
						.total(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9])));    				    				 
			}

		} else if(classLength == 8) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])  
						.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.class2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3])) 
						.class3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.class4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.class5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.class6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.class7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.class8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))  
						.total(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10])));    				    				 
			}

		} else if(classLength == 9) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])  
						.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.class2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3])) 
						.class3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.class4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.class5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.class6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.class7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.class8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))    		 				          
						.class9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10])) 	 	 	  				                             		 				                 
						.total(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11])));    				    				 
			}

		} else if(classLength == 10) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])  
						.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.class2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3])) 
						.class3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.class4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.class5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.class6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.class7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.class8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))    		 				          
						.class9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.class10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11])) 	 	 	  				                            		 				                 
						.total(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12])));    				    				 
			}

		} else if(classLength == 11) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])  
						.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.class2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3])) 
						.class3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.class4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.class5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.class6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.class7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.class8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))    		 				          
						.class9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.class10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.class11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12])) 	 	 	  				                               		 				                 
						.total(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13])));    				    				 
			}

		} else if(classLength == 12) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])  
						.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.class2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3])) 
						.class3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.class4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.class5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.class6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.class7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.class8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))    		 				          
						.class9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.class10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.class11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.class12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13])) 	 	 	  				                           		 				                 
						.total(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14])));    				    				 
			}

		} else if(classLength == 13) {*/

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])  
						.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.class2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3])) 
						.class3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.class4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.class5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.class6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.class7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.class8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))    		 				          
						.class9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.class10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
						.class11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
						.class12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
						.class13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))    		 				                 
						.total(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15])));    				    				 
			//}

		}    		                  
	}

	/* AXLES -- TYPE = 7 */ 

	public void axlesBuilder() {

		/*if(axleLength == 1) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   
						.dateTime(resultQuery[k][1])
						.axles1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 				                
						.total(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3])));    				    				 
			}

		} else if(axleLength == 2) {

			for(int k = 0; k < getNumRegisters(); k++) {   

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])
						.axles1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.axles2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.total(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4])));
			}

		} else  if(axleLength == 3) {

			for(int k = 0; k < getNumRegisters(); k++) {   

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])
						.axles1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.axles2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.axles3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.total(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5])));
			}

		}else if(axleLength == 4) {

			for(int k = 0; k < getNumRegisters(); k++) {   

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])   
						.dateTime(resultQuery[k][1])
						.axles1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.axles2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.axles3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.axles4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.total(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6])));
			}

		}else if(axleLength == 5) {

			for(int k = 0; k < getNumRegisters(); k++) {   

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])  
						.dateTime(resultQuery[k][1])
						.axles1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.axles2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.axles3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.axles4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.axles5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.total(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7])));

			}

		}else if(axleLength == 6) {

			for(int k = 0; k < getNumRegisters(); k++) {   

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])    
						.dateTime(resultQuery[k][1])
						.axles1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.axles2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.axles3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.axles4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.axles5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.axles6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))			               
						.total(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8])));
			}

		}else if(axleLength == 7) {

			for(int k = 0; k < getNumRegisters(); k++) {   

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])
						.dateTime(resultQuery[k][1])
						.axles1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.axles2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.axles3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.axles4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.axles5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.axles6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.axles7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.total(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9])));			              
			}

		}else if(axleLength == 8) {

			for(int k = 0; k < getNumRegisters(); k++) {   

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])
						.axles1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.axles2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.axles3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.axles4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.axles5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.axles6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.axles7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.axles8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.total(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10])));			               
			}

		} else if(axleLength == 9) {

			for(int k = 0; k < getNumRegisters(); k++) {   

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])
						.dateTime(resultQuery[k][1])
						.axles1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.axles2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.axles3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.axles4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.axles5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.axles6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.axles7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.axles8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.axles9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
						.total(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11])));
			}    		

		} else if(axleLength == 10) {*/

			for(int k = 0; k < getNumRegisters(); k++) {   

				resultList.add(new SatReports.Builder().date(resultQuery[k][0])
						.dateTime(resultQuery[k][1])
						.axles1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2])) 
						.axles2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.axles3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
						.axles4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.axles5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
						.axles6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.axles7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.axles8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
						.axles9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))						
						.total(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11])));
			//}    		
		}  
	}

	/* SPEED -- TYPE = 8 */ 

	public void speedBuilder() {

		for(int k = 0; k < getNumRegisters(); k++) {         			

			resultList.add(new SatReports.Builder().date(resultQuery[k][0])
					.dateTime(resultQuery[k][1])
					.equipment(resultQuery[k][2])
					.speed50km(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
					.speed70km(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
					.speed90km(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
					.speed120km(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
					.speed150km(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
					.speed150Bigger(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
					.total(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9])));    				    				 
		} 	 
	}

	 /////// COMMON REPORTS

	 /////// CCR REPORTS

	/* CCR CLASSES -- TYPE = 9 */

	public void classesCCRBuilder() {

		for(int k = 0; k < getNumRegisters(); k++) {         			

			resultList.add(new SatReports.Builder().date(resultQuery[k][0])
					.dateTime(resultQuery[k][1])
					.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
					.class2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))   
					.class3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
					.class4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
					.class5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))			                  				               
					.total(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7])));    				    				 
		} 
	}

	/* CCR TYPE -- TYPE = 10 */

	public void tiposCCRBuilder() {

		for(int k = 0; k < getNumRegisters(); k++) {         			

			resultList.add(new SatReports.Builder().date(resultQuery[k][0])
					.dateTime(resultQuery[k][1])
					.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
					.class2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))    				                  				               
					.total(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4])));    				    				 
		} 

	}

	/* CCR SPEED -- TYPE = 11 */

	public void speedCCRBuilder() {

		for(int k = 0; k < getNumRegisters(); k++) {         			

			resultList.add(new SatReports.Builder().date(resultQuery[k][0])
					.dateTime(resultQuery[k][1])
					.speed50km(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
					.speed70km(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
					.speed90km(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
					.speed120km(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
					.speed150km(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
					.speed150Bigger(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
					.total(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8])));    				    				 
		} 	 
	}

	/* CCR ALL CLASSES -- TYPE = 12 */

	public void ccrAllClassesBuilder() {

		for(int k = 0; k < getNumRegisters(); k++) {       			

			resultList.add(new SatReports.Builder().date(resultQuery[k][0])
					.dateTime(resultQuery[k][1])
					.class1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))    
					.class2(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))    
					.class3(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
					.class4(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
					.class5(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))
					.class6(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
					.class7(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
					.class8(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9]))
					.class9(resultQuery[k][10] == null? 0 : Integer.parseInt(resultQuery[k][10]))
					.class10(resultQuery[k][11] == null? 0 : Integer.parseInt(resultQuery[k][11]))
					.class11(resultQuery[k][12] == null? 0 : Integer.parseInt(resultQuery[k][12]))
					.class12(resultQuery[k][13] == null? 0 : Integer.parseInt(resultQuery[k][13]))
					.class13(resultQuery[k][14] == null? 0 : Integer.parseInt(resultQuery[k][14]))
					.class14(resultQuery[k][15] == null? 0 : Integer.parseInt(resultQuery[k][15]))
					.class15(resultQuery[k][16] == null? 0 : Integer.parseInt(resultQuery[k][16]))
					.class16(resultQuery[k][17] == null? 0 : Integer.parseInt(resultQuery[k][17]))
					.class17(resultQuery[k][18] == null? 0 : Integer.parseInt(resultQuery[k][18]))
					.class18(resultQuery[k][19] == null? 0 : Integer.parseInt(resultQuery[k][19]))		                  				               
					.total(resultQuery[k][20] == null? 0 : Integer.parseInt(resultQuery[k][20])));    				    				 

		}

	}

    /////// CCR REPORTS

	///////////////////////////////////
	//CONSTRUCTORS FOR RESULTLIST
	/////////////////////////////////  
	
	/**********************************************************************************************************/
	
	public String equipName(List<? extends Equipments> list, String equip) {
		
		String eqp = "";
		
		for(int i = 0; i < list.size(); i++) {
			
			if(list.get(i).getEquip_id() == Integer.parseInt(equip)) {
				eqp = list.get(i).getNome();
				break;
			}					
	     }
	
	     return eqp;

    }
	
	
}
