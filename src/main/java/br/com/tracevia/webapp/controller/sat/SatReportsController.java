package br.com.tracevia.webapp.controller.sat;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.time.YearMonth;
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

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.global.GlobalReportsDAO;
import br.com.tracevia.webapp.dao.sat.SatQueriesModels;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.ExcelModels;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.ColumnModel;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.global.Equipments;
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

	//Locale Docs
	LocaleUtil localeLabel, localeCalendar, localeDir;  

	int periodRange, daysInMonth, daysCount;  

	// Variável que recebe o número de registros esperados para uma consula SQL (de acordo com períodos)
	private static int numRegisters;	

	// Variável que recebe o número de campos de uma consulta SQL
	private static int fieldsNumber;	

	String[] fields, fieldObjectValues, fieldsAux, fieldObjAux; //Nome dos campos // Valores de cada campo -> Atribuidos a variavéis do modelo  

	String[][] resultQuery; 

	String procedure, query, queryCount, module, fileName, currentDate, direction1, direction2, directionLabel1, directionLabel2, 
	directionValue1, directionValue2, equipId; 

	BufferedWriter writer;  
	ByteArrayOutputStream byteWriter; 

	EquipmentsDAO equipDAO;
	ExcelModels model;

	private boolean clearBool, excelBool;

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
		vehicles.add(new SelectItem(4, localeLabel.getStringKey("sat_reports_select_vehicles_long_heavy")));  

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
	}

	///////////////////////////////////
	//CREATE REPORTS
	/////////////////////////////////  

	/**********************************************************************************************************/
	
	//////DESENHAR TABLES 	

	/**
	 * Método para criar os headers
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

		/**** CONTAGEM VEÍCULOS ****/			
		if(type.equals("1")) {

			fields = new String[] {localeLabel.getStringKey("sat_reports_general_date"), localeLabel.getStringKey("sat_reports_general_datetime"), listSats.get(0).getNome(), localeLabel.getStringKey("sat_reports_general_total")};
			fieldObjectValues = new String[] { "date", "dateTime", "eqp1", "total"};

		}

		/**** CONTAGEM DE VEICULOS POR PERÍODO ****/
		if(type.equals("2")) {

			fields = new String[] {localeLabel.getStringKey("sat_reports_general_date"), localeLabel.getStringKey("sat_reports_general_datetime"), 
					localeLabel.getStringKey("sat_reports_count_flow_lightVehicles"), localeLabel.getStringKey("sat_reports_count_flow_heavyVehicles"),
					localeLabel.getStringKey("sat_reports_count_flow_motorcycleVehicles"), localeLabel.getStringKey("sat_reports_count_flow_lightVehicles"), 
					localeLabel.getStringKey("sat_reports_count_flow_heavyVehicles"), localeLabel.getStringKey("sat_reports_count_flow_motorcycleVehicles"), localeLabel.getStringKey("sat_reports_general_total")};

			fieldObjectValues = new String[] { "date", "dateTime", "lightDir1", "heavyDir1",  "motoDir1",
					"lightDir2", "heavyDir2", "motoDir2", "total"};

		}

		/**** FLUXO MENSAL  ****/
		if(type.equals("3")) {

			fields = new String[] {localeLabel.getStringKey("sat_reports_general_date"), localeLabel.getStringKey("sat_reports_general_datetime"), 
					localeLabel.getStringKey("sat_reports_monthly_flow_lightVehicles"), localeLabel.getStringKey("sat_reports_monthly_flow_commercials"), localeLabel.getStringKey("sat_reports_monthly_flow_motoVehicles"),
					localeLabel.getStringKey("sat_reports_monthly_flow_lightVehicles"), localeLabel.getStringKey("sat_reports_monthly_flow_motoVehicles"),
					localeLabel.getStringKey("sat_reports_monthly_flow_commercials"), localeLabel.getStringKey("sat_reports_monthly_flow_dir1"), localeLabel.getStringKey("sat_reports_monthly_flow_dir2")};

			fieldObjectValues = new String[] { "date", "dateTime", "lightDir1", "heavyDir1", "motoDir1", "lightDir2", "heavyDir2", "motoDir2", "speedValue1", "speedValue2"};

		}

		/**** FLUXO PERÍODO  ****/
		if(type.equals("4")) {

			fields = new String[] {localeLabel.getStringKey("sat_reports_general_date"), localeLabel.getStringKey("sat_reports_general_datetime"), localeLabel.getStringKey("sat_reports_general_equipment"),
					localeLabel.getStringKey("sat_reports_period_flow_lightVehicles"), 	   
					localeLabel.getStringKey("sat_reports_period_flow_commVehicles"), localeLabel.getStringKey("sat_reports_period_flow_motoVehicles"), 
					localeLabel.getStringKey("sat_reports_general_total"), localeLabel.getStringKey("sat_reports_period_flow_speed_abbr"),
					localeLabel.getStringKey("sat_reports_period_flow_lightVehicles"), 	   
					localeLabel.getStringKey("sat_reports_period_flow_commVehicles"), localeLabel.getStringKey("sat_reports_period_flow_motoVehicles"), 
					localeLabel.getStringKey("sat_reports_general_total"), localeLabel.getStringKey("sat_reports_period_flow_speed_abbr")};

			fieldObjectValues = new String[] {"date", "dateTime", "equipment", "motoDir1", "lightDir1", "heavyDir1", "total1", "speedValue1", "motoDir2", "lightDir2", "heavyDir2", "total2", "speedValue2"};

		}

		/**** PESAGEM ****/
		if(type.equals("5")) {

			fields = new String[] {localeLabel.getStringKey("sat_reports_general_date"), localeLabel.getStringKey("sat_reports_general_datetime"),
					localeLabel.getStringKey("sat_reports_class_light"), localeLabel.getStringKey("sat_reports_class_motorcycle"), 	   
					localeLabel.getStringKey("sat_reports_class_trailer"), localeLabel.getStringKey("sat_reports_select_class_semi_trailer"), 
					localeLabel.getStringKey("sat_reports_class_heavy_2_axles"), localeLabel.getStringKey("sat_reports_class_heavy_3_axles"),
					localeLabel.getStringKey("sat_reports_class_heavy_4_axles"), localeLabel.getStringKey("sat_reports_class_heavy_5_axles"), 	   
					localeLabel.getStringKey("sat_reports_class_heavy_6_axles"), localeLabel.getStringKey("sat_reports_class_heavy_7_axles"), 
					localeLabel.getStringKey("sat_reports_class_heavy_8_axles"), localeLabel.getStringKey("sat_reports_class_heavy_9_axles"),
					localeLabel.getStringKey("sat_reports_class_heavy_10_axles")};

			fieldObjectValues = new String[] {"date", "dateTime", "class1", "class2", "class3", "class4", "class5", "class6", "class7", "class8", "class9", "class10", "class11", "class12", "class13"};   

		} 

		/**** CLASS TYPE  ****/
		if(type.equals("6")) {

			fields = new String[] {localeLabel.getStringKey("sat_reports_general_date"), localeLabel.getStringKey("sat_reports_general_datetime"),
					localeLabel.getStringKey("sat_reports_class_light"), localeLabel.getStringKey("sat_reports_class_motorcycle"), 	   
					localeLabel.getStringKey("sat_reports_class_trailer"), localeLabel.getStringKey("sat_reports_class_semi_trailer"), 
					localeLabel.getStringKey("sat_reports_class_heavy_2_axles"), localeLabel.getStringKey("sat_reports_class_heavy_3_axles"),
					localeLabel.getStringKey("sat_reports_class_heavy_4_axles"), localeLabel.getStringKey("sat_reports_class_heavy_5_axles"), 	   
					localeLabel.getStringKey("sat_reports_class_heavy_6_axles"), localeLabel.getStringKey("sat_reports_class_heavy_7_axles"), 
					localeLabel.getStringKey("sat_reports_class_heavy_8_axles"), localeLabel.getStringKey("sat_reports_class_heavy_9_axles"),
					localeLabel.getStringKey("sat_reports_class_heavy_10_axles"), localeLabel.getStringKey("sat_reports_general_total") };

			fieldObjectValues = new String[] {"date", "dateTime", "class1", "class2", "class3", "class4", "class5", "class6", "class7", "class8", "class9", "class10", "class11", "class12", "class13", "total"};

		}

		/**** AXLE TYPE ****/
		if(type.equals("7")) {

			fields = new String[] {localeLabel.getStringKey("sat_reports_general_date"), localeLabel.getStringKey("sat_reports_general_datetime"),
					localeLabel.getStringKey("sat_reports_axles_2"), localeLabel.getStringKey("sat_reports_axles_3"),
					localeLabel.getStringKey("sat_reports_axles_4"), localeLabel.getStringKey("sat_reports_axles_5"), 	   
					localeLabel.getStringKey("sat_reports_axles_6"), localeLabel.getStringKey("sat_reports_axles_7"), 
					localeLabel.getStringKey("sat_reports_axles_8"), localeLabel.getStringKey("sat_reports_axles_9"),
					localeLabel.getStringKey("sat_reports_axles_10"), localeLabel.getStringKey("sat_reports_general_total") };

			fieldObjectValues = new String[] {"date", "dateTime", "axles_1", "axles_2", "axles_3", "axles_4", "axles_5", "axles_6", "axles_7", "axles_8", "axles_9", "total"};

		}

		/**** SPEED ****/
		if(type.equals("8")) {

			fields = new String[] {localeLabel.getStringKey("sat_reports_general_date"), localeLabel.getStringKey("sat_reports_general_datetime"),
					localeLabel.getStringKey("sat_reports_speed_50km"), localeLabel.getStringKey("sat_reports_speed_70km"),
					localeLabel.getStringKey("sat_reports_speed_90km"), localeLabel.getStringKey("sat_reports_speed_120km"), 	   
					localeLabel.getStringKey("sat_reports_speed_150km"), localeLabel.getStringKey("sat_reports_speed_150km_bigger"), 
					localeLabel.getStringKey("sat_reports_general_total") };

			fieldObjectValues = new String[] {"date", "dateTime", "speed50km", "speed70km", "speed90km", "speed120km", "speed150km", "speed150km_bigger", "total"};

		}

		/** CCR REPORTS **/

		/**** CCR CLASSES TYPE  ****/
		if(type.equals("9")) {

			fields = new String[] {localeLabel.getStringKey("sat_reports_general_date"), localeLabel.getStringKey("sat_reports_general_datetime"),
					localeLabel.getStringKey("sat_reports_class_motorcycle"), localeLabel.getStringKey("sat_reports_class_light"), 
					localeLabel.getStringKey("sat_reports_class_small"), localeLabel.getStringKey("sat_reports_class_long"), 
					localeLabel.getStringKey("sat_reports_class_bus"), localeLabel.getStringKey("sat_reports_general_total")};		   

			fieldObjectValues = new String[] {"date", "dateTime", "class1", "class2", "class3", "class4", "class5", "total"};
		}

		/**** CCR AXLE TYPE  ****/
		if(type.equals("10")) {

			fields = new String[] {localeLabel.getStringKey("sat_reports_general_date"), localeLabel.getStringKey("sat_reports_general_datetime"),
					localeLabel.getStringKey("sat_reports_class_light"), localeLabel.getStringKey("sat_reports_class_commercials"), 
					localeLabel.getStringKey("sat_reports_general_total")};	

			fieldObjectValues = new String[] {"date", "dateTime", "class1", "class2", "total"};

		}

		/**** CCR SPEED ****/
		if(type.equals("11")) {

			fields = new String[] {localeLabel.getStringKey("sat_reports_general_date"), localeLabel.getStringKey("sat_reports_general_datetime"),
					localeLabel.getStringKey("sat_reports_speed_50km"), localeLabel.getStringKey("sat_reports_speed_70km"),
					localeLabel.getStringKey("sat_reports_speed_90km"), localeLabel.getStringKey("sat_reports_speed_120km"), 	   
					localeLabel.getStringKey("sat_reports_speed_150km"), localeLabel.getStringKey("sat_reports_speed_150km_bigger"), 
					localeLabel.getStringKey("sat_reports_general_total") };

			fieldObjectValues = new String[] {"date", "dateTime", "speed50km", "speed70km", "speed90km", "speed120km", "speed150km", "speed150km_bigger", "total"};

		}

		/**** ALL CLASS TYPE  ****/
		if(type.equals("12")) {

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

			fieldObjectValues = new String[] {"date", "dateTime", "class1", "class2", "class3", "class4", "class5",  "class6",
					"class7",  "class8",  "class9",  "class10",  "class11",  "class12",  "class13",  "class14",  "class15",  "class16",
					"class17",  "class18",  "total"};
		}


		/** CCR REPORTS **/

		//DESENHAR A TABELA
		drawTable(fields, fieldObjectValues);	

		//GUARDAR VALORES NA SESSION
		facesContext.getExternalContext().getSessionMap().put("fieldsLength", fields.length); //Length of Fields
		facesContext.getExternalContext().getSessionMap().put("fields", fields);	//Fields
		facesContext.getExternalContext().getSessionMap().put("fieldsObject", fieldObjectValues); //Objects

	}

    ////CRIAR TABLE HEADERS 

	/**********************************************************************************************************/

    ////BUILD REPORTS

	// REPORTS MODELS
	// Recebe uma String que define qual o relátorio vai ser chamado

	/**
	 * Método par criar um relatório de acordo com tipo
	 * @param type tipo do relatório 
	 * @throws Exception
	 */	
	public void GetReports(String type) throws Exception{

		//Get external application contents
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		QueriesReportsModels models = new QueriesReportsModels(); //QuerieReportsModel class
		SatQueriesModels satModels = new SatQueriesModels(); // SatReportsModel class	    
		DateTimeApplication dta = new DateTimeApplication(); //DateTimeApsplication class

		GlobalReportsDAO dao = new GlobalReportsDAO();	//GlobalReportsDAO

		/*** Obter parâmetros que vem no submit de cada pesquisa ***/

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

		/**** Single Params ***/

		//Initialize ResultList
		//Builder class -> for construct objects
		resultList = new ArrayList<Builder>();	


		/**** FLUXO MENSAL  ****/
		if(type.equals("3")) {

			// QUANTOS DIAS POSSUI O RESPECTIVO MÊS
			YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(satReport.getYear()), Integer.parseInt(satReport.getMonth()));					
			daysInMonth = yearMonthObject.lengthOfMonth();

			//INSERIR VALORES DATAS DE INICIO E FIM
			satReport.setStartDate("01/"+satReport.getMonth()+"/"+satReport.getYear());
			satReport.setEndDate(daysInMonth+"/"+satReport.getMonth()+"/"+satReport.getYear());

			//INTERVALO POR PERIODO
			periodRange = dta.periodsRange(satReport.getPeriod());

			//NÚMERO DE REGISTROS PARA A SAÍDA DE DADOS			
			setNumRegisters((daysInMonth * periodRange)); 				

		}

		/**** FLUXO POR PERÍODO ****/

		else if(type.equals("4")) {

			// QUANTOS DIAS POSSUI O RESPECTIVO MÊS
			YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(satReport.getYear()), Integer.parseInt(satReport.getMonth()));
			int daysInMonth = yearMonthObject.lengthOfMonth();

			//INSERIR VALORES DATAS DE INICIO E FIM
			satReport.setStartDate("01/"+satReport.getMonth()+"/"+satReport.getYear());
			satReport.setEndDate(daysInMonth+"/"+satReport.getMonth()+"/"+satReport.getYear());

			//INTERVALO POR PERIODO
			periodRange = dta.periodsRange(satReport.getPeriod());

			//NÚMERO DE REGISTROS PARA A SAÍDA DE DADOS					
			setNumRegisters(((daysInMonth * periodRange) * satReport.equipments.length)); // Número de registros

		}

		/** CASO CONTRARIO -- OUTROS TIPOS **/

		else {

			//RETORNA NÚMERO DE REGISTROS POR PERIODO E DATAS SELECIONADAS	
			setNumRegisters(dta.RegistersNumbers(satReport.getStartDate(), satReport.getEndDate(), satReport.getPeriod())); 

			//CONTAGEM DOS DIAS
			daysCount = ((int) dta.diferencaDias(satReport.getStartDate(), satReport.getEndDate()) + 1);

			//INTERVALO POR PERIODO
			periodRange = dta.periodsRange(satReport.getPeriod());

		}

		/** TODO RELATÓRIO PASSA POR AQUI!!! **/

		//NÚMERO DE CAMPOS PARA A SAÍDA DE DADOS
		//LEVA EM CONSIDERAÇÃO NÚMERO DE CAMPOS DA QUERY
		setFieldsNumber(fieldsNumber(type));

		//SELECIONA UMA PROCEDURE DE ACORDO COM PERÍODO SELECIONADO
		procedure = models.SelectProcedureByPeriod(satReport.getPeriod());	

		System.out.println(procedure); //debug

		//SELECIONA UMA QUERY DE ACORDO COM TIPO SELECIONADO
		query = SelectQueryType(type, models, satModels);

		System.out.println(query); //debug

		//EXECUÇÃO DA QUERY
		resultQuery = dao.ExecuteQuery(procedure, query, satReport.getStartDate(), satReport.getEndDate());

		//CASO EXISTIR VALORES
		if(resultQuery.length > 0) {

			//SAÍDA PARA A TABELA
			OutPutResult(type);
			
			//SAÍDA DO EXCEL
			ExcelOutPut(type, model);

			//BOTÃO DE LIMPAR 
			setClearBool(false);

			//LINK DE DOWNLOAD DO EXCEL
			setExcelBool(false);

			//UPDATE RESET BUTTON
			RequestContext.getCurrentInstance().update("form-btns:#btn-tab-reset");

			//UPDATE BUTTON GENERATE EXCEL
			RequestContext.getCurrentInstance().update("form-excel:#excel-act");	

		}

	}
	
	//// BUILD REPORTS

	/**********************************************************************************************************/

	///////////////////////////////////
	//CREATE REPORTS
	///////////////////////////////// 
	
	///// VBVs Txt  
	
	// REPORTS VBV MODEL
	public void GetVBVReport() throws Exception{				

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		SatQueriesModels satModels = new SatQueriesModels();	    
		DateTimeApplication dta = new DateTimeApplication();

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

		// Quantos dias possui o respectivo mês
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

	/**
	 * @author Wellington 10/09/2020
	 * Método para criar uma Query a ser executada  
	 * @param models - Objeto do tipo QuerieReportsModels
	 * @param mainQuery - Query principal a ser adicionada
	 * @return
	 */

	/*** SAT_VBV TABLE ***/
	public String BuildMainQuery(QueriesReportsModels models, String mainQuery) {    	 

		String query = null;

		query = models.BuildQuery(models.QueryHeader(satReport.getPeriod()), mainQuery, models.QueryFromTable(satReport.getPeriod()), models.LeftJoinStart(RoadConcessionaire.tableVBV),
				models.LeftJoinCondition(satReport.getPeriod()), models.LeftJoinEnd("sat"), models.QueryGroupAndOrder(satReport.getPeriod()));

		return query;
	}

	/*** SAT_VBV_LL TABLE ***/
	public String BuildMainQueryLL(QueriesReportsModels models, String mainQuery) {    	 

		String query = null;

		query = models.BuildQuery(models.QueryHeader(satReport.getPeriod()), mainQuery, models.QueryFromTable(satReport.getPeriod()), models.LeftJoinStart(RoadConcessionaire.tableLL),
				models.LeftJoinCondition(satReport.getPeriod()), models.LeftJoinEnd("sat"), models.QueryGroupAndOrder(satReport.getPeriod()));

		return query;
	}

	/*** SAT_VBV_CCR TABLE ***/
	public String BuildMainQueryCCR(QueriesReportsModels models, String mainQuery) {    	 

		String query = null;

		query = models.BuildQuery(models.QueryHeader(satReport.getPeriod()), mainQuery, models.QueryFromTable(satReport.getPeriod()), models.LeftJoinStart(RoadConcessionaire.tableCCR),
				models.LeftJoinCondition(satReport.getPeriod()), models.LeftJoinEnd("sat"), models.QueryGroupAndOrder(satReport.getPeriod()));

		return query;
	}

     /////// BUILD QUERY MODEL

	/**********************************************************************************************************/

    /////// CONSTRUÇÃO DA QUERY

	/**
	 * Método que retorna um query específica de acordo com tipo
	 * @param type - Tipo de específico do relatório
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

		case "1": query = BuildMainQuery(models, satModels.CountVehiclesMainQuery(satReport.equipments, satReport.vehicles)); break;
		case "2": equipDAO = new EquipmentsDAO(); lanes = equipDAO.EquipmentSelectLanesNumber("sat", satReport.getEquipment()); query = BuildMainQuery(models, satModels.CountVehiclesDirectionMainQuery(satReport.getEquipment(), satReport.getPeriod(), lanes)); break;
		case "3": equipDAO = new EquipmentsDAO(); lanes = equipDAO.EquipmentSelectLanesNumber("sat", satReport.getEquipment()); query = BuildMainQuery(models, satModels.MonthlyFlowMainQuery(satReport.getEquipment(), lanes)); break;
		case "4": equipDAO = new EquipmentsDAO(); lanesEquips = equipDAO.EquipmentSelectLanesNumber("sat", satReport.equipments); query = BuildMainQuery(models, satModels.PeriodFlowMainQuery(satReport.equipments, satReport.getPeriod(), lanesEquips)); break;
		case "5": query = BuildMainQuery(models, satModels.WeighingMainQuery(satReport.getEquipment(), satReport.classes)); ; break;
		case "6": equipDAO = new EquipmentsDAO(); lanes = equipDAO.EquipmentSelectLanesNumber("sat", satReport.getEquipment()); query = BuildMainQuery(models, satModels.ClassTypeMainQuery(satReport.getEquipment(), satReport.classes, lanes)); ; break;
		case "7": equipDAO = new EquipmentsDAO(); lanes = equipDAO.EquipmentSelectLanesNumber("sat", satReport.getEquipment()); query = BuildMainQuery(models, satModels.AxleTypeMainQuery(satReport.getEquipment(), satReport.axles, lanes)); ; break;
		case "8": query = BuildMainQuery(models, satModels.SpeedMainQuery(satReport.getEquipment(), satReport.vehicles));  ; break; 
		case "9": query = BuildMainQueryLL(models, satModels.CCRClasses(satReport.getEquipment()));  ; break;  
		case "10": query = BuildMainQueryLL(models, satModels.CCRTipos(satReport.getEquipment()));  ; break;  	
		case "11": query = BuildMainQueryLL(models, satModels.CCRVelocidade(satReport.getEquipment()));  ; break;  
		case "12": query = BuildMainQueryCCR(models, satModels.CCRAllClasses(satReport.getEquipment()));  ; break;  

		default: query = null; break;

		}  	 
		
		return query;

	}

     /////// CONSTRUÇÃO DA QUERY  

	///////////////////////////////////
	//QUERY FOR METHODS
	/////////////////////////////////
	
	/**********************************************************************************************************/
	/**********************************************************************************************************/

	///////////////////////////////////
	//SAIDA DE DADOS
	/////////////////////////////////  
	
	
    /////// SAIDA DE DADOS -/- FRONT-END

	/**
	 * @author Wellington 10/09/2020
	 * Método para gerenciar construtores a fim de criar saidas de dados dinamicamente.
	 * @param type - Tipo do satReport (Define qual é a sua finalidade)
	 */

	//PREENCHER SAIDA DE DADOS 
	// POR TIPO DE RELATÓRIO
	public void OutPutResult(String type) {  

		//ACESSAR DADOS DO RELATÓRIOF
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		//FIELDS EXTERNOS ARMAZENADOS NA REQUISIÇÃO
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
			weighingBuilder(satReport.classes.length);
			
			//REORDERNAR HEADERS DE ACORDO COM SELEÇÃO => CHECKBOXES DE CLASSES
			ReorderTableHeaderForWeighing(satReport.classes);  

			// DRAW TABLE -- BUILD HEADER
			drawTable(fields, fieldObjectValues);	

		}  

		/**** CLASS TYPE ****/
		if(type.equals("6")) { 

			//CLASS TYPE CONSTRUCTOR 
			classesBuilder(satReport.classes.length);    
			
			//REORDERNAR HEADERS DE ACORDO COM SELEÇÃO => CHECKBOXES DE CLASSES
			ReorderTableHeaderForClasses(satReport.classes);

			// DRAW TABLE -- BUILD HEADER
			drawTable(fields, fieldObjectValues);	

		}    		 

		/**** AXLE TYPE ****/
		if(type.equals("7")) {     		 

			//AXLE TYPE CONSTRUCTOR
			axlesBuilder(satReport.axles.length);
			
			//REORDERNAR HEADERS DE ACORDO COM SELEÇÃO => CHECKBOXES DE AXLES
			ReorderTableHeaderForAxles(satReport.axles);

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
	
	//REORDENAR TABLE FRONT END - CONTAGEM VEÍCULOS
	public void ReorderTableHeaderForCountVehicles(String[] equipments) {
		
			fields = new String[(3 + equipments.length)];
			fieldObjectValues = new String[(3 + equipments.length)];

			fields[0] = localeLabel.getStringKey("sat_reports_general_date");
			fields[1] = localeLabel.getStringKey("sat_reports_general_datetime");
			fieldObjectValues[0] = "date";
			fieldObjectValues[1] = "dateTime";

			for(int i = 0; i < equipments.length; i++) {

				fields[i+2] =  listSats.get(i).getNome();
				fieldObjectValues[i+2] = "eqp"+(i+1);
					

			fields[equipments.length + 2] = localeLabel.getStringKey("sat_reports_general_total");
			fieldObjectValues[equipments.length + 2] = "total";  

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

	//CARREGAR DIREÇÕES DB
	public void loadDirections() throws Exception{			

		equipDAO  = new EquipmentsDAO();

		String dir1 = equipDAO.firstDirection(equipId);  

		popDirections(dir1);			

		//UpdateDirs
		RequestContext.getCurrentInstance().execute("updateDirections('#{satLabels.sat_reports_select_directions}');");

	}

	//POPULAR DIREÇÕES -> Internacionalização
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

	/////// FIELDS NUMBER - SAÍDA DE DADO

	public Integer fieldsNumber(String type) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		int length = (int) externalContext.getSessionMap().get("fieldsLength");

		int fields = 0;

		/**** CONTAGEM VEÍCULOS ****/		
		if(type.equals("1")) {    		
				fields =  (3 + satReport.equipments.length);		

		}

		/**** CONTAGEM VEÍCULOS ****/		
		if(type.equals("2")) {    
			fields = length;   
			
			System.out.println("LEN: "+length);
		}

		/**** FLUXO MENSAL  ****/
		if(type.equals("3")) {    		
			fields = (length + 2);    		 
		}

		/**** FLUXO PERÍODO  ****/
		if(type.equals("4")) {    		
			fields = 69;    		 
		}

		/**** PESAGEM  ****/
		if(type.equals("5")) {
			
			 fields = ( 2 + satReport.classes.length);

		}

		/**** CLASS TYPE  ****/
		if(type.equals("6")) {		
			 fields = ( 3 + (satReport.classes.length * 3));

		}

		/**** AXLE TYPE ****/
		if(type.equals("7")) {
			
			 fields = ( 3 + (satReport.axles.length * 3));

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
			 fields = length;
		}

		/**** CCR SPEED ****/
		if(type.equals("11")) {
			fields = length;
		}

		/**** CCR ALL CLASSES ****/
		if(type.equals("12")) {
			fields = (3 + 54);

		}

		return fields;    	 
	}

     /////// FIELDS NUMBER - SAÍDA DE DADO 

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

			ReorderTableHeaderForCountVehicles(satReport.equipments);    	//Reorder Table Header	

			model.StandardFonts();  //Set Font
			model.StandardStyles(); //Set Style
			model.StandardBorders(); // Set Borders
			
			System.out.println("RG: "+periodRange);

			//Chamada ao Método padrão do Excel
			model.StandardExcelModel(fields, getNumRegisters(), periodRange, daysCount, satReport.getPeriod(), dta.currentTime(), type, module,  				  
					RoadConcessionaire.externalImagePath, excel_title, equip, city, road, km, lanes, satReport.getStartDate(), satReport.getEndDate(), countMergeHeader, 
					col, colStartDate, colEndDate, resultQuery);

		}

		/**** COUNT VEHICLES PERIOD ****/
		if(type.equals("2")) {

			fileName = localeLabel.getStringKey("excel_report_vehicles_count_flow_file");
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
			direction1 = tm.CheckDirection1(lane1);
			direction2 = tm.CheckDirection2(lane1);

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
			direction1 = tm.CheckDirection1(lane1);
			direction2 = tm.CheckDirection2(lane1);

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
			direction1 = tm.CheckDirection1(lane1);
			direction2 = tm.CheckDirection2(lane1);

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
			ReorderTableHeaderForWeighing(satReport.classes);    		

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
			direction1 = tm.CheckDirection1(lane1);
			direction2 = tm.CheckDirection2(lane1);

			//Reorder Tables
			ReorderTableHeaderForClasses(satReport.classes);    		

			model.StandardFonts(); //Set Fonts
			model.StandardStyles(); // Set Styles
			model.StandardBorders(); //Set Borders

			//Excel Model with Directions
			model.ExcelModelDirections(fields, getNumRegisters(), periodRange, daysCount, satReport.getPeriod(), dta.currentTime(), type, module,  				  
					RoadConcessionaire.externalImagePath, excel_title, equip, city, road, km, lanes, direction1, direction2, satReport.getStartDate(), satReport.getEndDate(), countMergeHeader, 
					col, colStartDate, colEndDate, resultQuery);

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
			direction1 = tm.CheckDirection1(lane1);
			direction2 = tm.CheckDirection2(lane1);

			//Reorder header
			ReorderTableHeaderForAxles(satReport.axles);    		

			model.StandardFonts(); //Set Font
			model.StandardStyles(); // Set Styles
			model.StandardBorders(); //Set Borders

			//Excel Model Directions
			model.ExcelModelDirections(fields, getNumRegisters(), periodRange, daysCount, satReport.getPeriod(), dta.currentTime(), type, module,  				  
					RoadConcessionaire.externalImagePath, excel_title, equip, city, road, km, lanes, direction1, direction2, satReport.getStartDate(), satReport.getEndDate(), countMergeHeader, 
					col, colStartDate, colEndDate, resultQuery);		  
		} 	 

		/**** SPEED ****/
		if(type.equals("8")) {

			//Define name
			fileName = localeLabel.getStringKey("excel_report_speed_file")+tm.periodName(satReport.getPeriod());
			excel_title = localeLabel.getStringKey("excel_report_speed_title"); // Excel title

			countMergeHeader = new String[] {"A1:B4", "C1:H4", "I1:J4"}; // Merge cells

			col = new int[] {3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500}; // Col size 

			colStartDate = 8; colEndDate = 9; // Col ini & end date
			
			// get equipment values from DB 
			if(!satReport.getEquipment().equals(""))
			info = dao.SATreportInfo(satReport.getEquipment()); // fill equipemnt DB values

			//Equipment Info
			equip = info.getNome(); road = info.getEstrada(); km = info.getKm(); 
			lanes = String.valueOf(info.getNumFaixas()); city = info.getCidade();

			model.StandardFonts(); // Fonts
			model.StandardStyles(); // Styles
			model.StandardBorders(); // Borders

			//Standard Excel Model
			model.StandardExcelModel(fields, getNumRegisters(), periodRange, daysCount, satReport.getPeriod(), dta.currentTime(), type, module,  				  
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

			col = new int[] {3500, 3500, 3500, 3500, 3500, 3500, 3500, 350}; //Col size

			colStartDate = 12; colEndDate = 14; //Col start & end date
			
			// get equipment values from DB 
			if(!satReport.getEquipment().equals(""))
			info = dao.SATreportInfo(satReport.getEquipment()); // fill equipemnt DB values

			//Equipments Info
			equip = info.getNome(); road = info.getEstrada(); km = info.getKm(); 
			lanes = String.valueOf(info.getNumFaixas()); city = info.getCidade();
			lane1 = info.getFaixa1();

			//Directions 
			direction1 = tm.CheckDirection1(lane1);
			direction2 = tm.CheckDirection2(lane1);

			//Reorder Tables
			//ReorderTableHeaderForClasses(satReport.classes);    		

			model.StandardFonts(); //Set Fonts
			model.StandardStyles(); // Set Styles
			model.StandardBorders(); //Set Borders

			//Excel Model with Directions
			model.ExcelModelDirections(fields, getNumRegisters(), periodRange, daysCount, satReport.getPeriod(), dta.currentTime(), type, module,  				  
					RoadConcessionaire.externalImagePath, excel_title, equip, city, road, km, lanes, direction1, direction2, satReport.getStartDate(), satReport.getEndDate(), countMergeHeader, 
					col, colStartDate, colEndDate, resultQuery);

		}		
      			
         /*** CCR TIPOS ***/
		
		if(type.equals("10")) {

			//File name
			fileName = localeLabel.getStringKey("ccr_reports_type_excel_title")+tm.periodName(satReport.getPeriod());
			excel_title = localeLabel.getStringKey("ccr_reports_type_title"); // File Title

			countMergeHeader = new String[] {"A1:B4", "C1:L4", "M1:O4"}; // Merge Cells

			col = new int[] {3500, 3500, 3500, 3500, 3500, 3500, 3500}; //Col size

			colStartDate = 12; colEndDate = 14; //Col start & end date
			
			// get equipment values from DB 
			if(!satReport.getEquipment().equals(""))
			info = dao.SATreportInfo(satReport.getEquipment()); // fill equipemnt DB values

			//Equipments Info
			equip = info.getNome(); road = info.getEstrada(); km = info.getKm(); 
			lanes = String.valueOf(info.getNumFaixas()); city = info.getCidade();
			lane1 = info.getFaixa1();

			//Directions 
			direction1 = tm.CheckDirection1(lane1);
			direction2 = tm.CheckDirection2(lane1);

			//Reorder Tables
			//ReorderTableHeaderForClasses(satReport.classes);    		

			model.StandardFonts(); //Set Fonts
			model.StandardStyles(); // Set Styles
			model.StandardBorders(); //Set Borders

			//Excel Model with Directions
			model.ExcelModelDirections(fields, getNumRegisters(), periodRange, daysCount, satReport.getPeriod(), dta.currentTime(), type, module,  				  
					RoadConcessionaire.externalImagePath, excel_title, equip, city, road, km, lanes, direction1, direction2, satReport.getStartDate(), satReport.getEndDate(), countMergeHeader, 
					col, colStartDate, colEndDate, resultQuery);

		}
		
		         /*** CCR SPEED ***/
		
				if(type.equals("11")) {

					//File name
					fileName = localeLabel.getStringKey("ccr_reports_speed_excel_title")+tm.periodName(satReport.getPeriod());
					excel_title = localeLabel.getStringKey("ccr_reports_speed_title"); // File Title

					countMergeHeader = new String[] {"A1:B4", "C1:L4", "M1:O4"}; // Merge Cells

					col = new int[] {3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500}; // Col size 

					colStartDate = 12; colEndDate = 14; //Col start & end date
					
					// get equipment values from DB 
					if(!satReport.getEquipment().equals(""))
					info = dao.SATreportInfo(satReport.getEquipment()); // fill equipemnt DB values

					//Equipments Info
					equip = info.getNome(); road = info.getEstrada(); km = info.getKm(); 
					lanes = String.valueOf(info.getNumFaixas()); city = info.getCidade();
					lane1 = info.getFaixa1();

					//Directions 
					direction1 = tm.CheckDirection1(lane1);
					direction2 = tm.CheckDirection2(lane1);

					//Reorder Tables
					//ReorderTableHeaderForClasses(satReport.classes);    		

					model.StandardFonts(); //Set Fonts
					model.StandardStyles(); // Set Styles
					model.StandardBorders(); //Set Borders

					//Excel Model with Directions
					model.ExcelModelDirections(fields, getNumRegisters(), periodRange, daysCount, satReport.getPeriod(), dta.currentTime(), type, module,  				  
							RoadConcessionaire.externalImagePath, excel_title, equip, city, road, km, lanes, direction1, direction2, satReport.getStartDate(), satReport.getEndDate(), countMergeHeader, 
							col, colStartDate, colEndDate, resultQuery);

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
					direction1 = tm.CheckDirection1(lane1);
					direction2 = tm.CheckDirection2(lane1);

					//Reorder Tables
					//ReorderTableHeaderForClasses(satReport.classes);    		

					model.StandardFonts(); //Set Fonts
					model.StandardStyles(); // Set Styles
					model.StandardBorders(); //Set Borders

					//Excel Model with Directions
					model.ExcelModelDirectionsSubHeader(fields, getNumRegisters(), periodRange, daysCount, satReport.getPeriod(), dta.currentTime(), type, module,  				  
							RoadConcessionaire.externalImagePath, excel_title, equip, city, road, km, lanes, direction1, direction2, satReport.getStartDate(), satReport.getEndDate(), countMergeHeader, 
							col, colStartDate, colEndDate, resultQuery);

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

		System.out.println("reset");
		
		externalContext.getSessionMap().remove("xlsModel");
		externalContext.getSessionMap().remove("current");
		externalContext.getSessionMap().remove("fileName");		
		externalContext.getSessionMap().remove("fields");
		externalContext.getSessionMap().remove("fieldsObject");

		// Fields again
		CreateFields(type);

	}

    /////// RESET FORM VALUES

	/**********************************************************************************************************/
	
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

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))    		 				               
						.total(Integer.parseInt(resultQuery[3][k])));  

			}; break;	

		case 2:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k])) 				               	              			               				               	 
						.total(Integer.parseInt(resultQuery[4][k]))); 

			}; break;	
		case 3:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k])) 				              				              			               				               	 
						.total(Integer.parseInt(resultQuery[5][k]))); 

			}; break;	
		case 4:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k])) 				               			              			               				               	 
						.total(Integer.parseInt(resultQuery[6][k])));   

			}; break;	
		case 5:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k])) 				             			              			               				               	 
						.total(Integer.parseInt(resultQuery[7][k]))); 

			}; break;	
		case 6:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k])) 				               				              			               				               	 
						.total(Integer.parseInt(resultQuery[8][k])));   

			}; break;	
		case 7:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k])) 				                				              			               				               	 
						.total(Integer.parseInt(resultQuery[9][k])));  

			}; break;	
		case 8:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k])) 				               			              			               				               	 
						.total(Integer.parseInt(resultQuery[10][k]))); 

			}; break;	
		case 9:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k])) 				              				              			               				               	 
						.total(Integer.parseInt(resultQuery[11][k])));   

			}; break;	
		case 10:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k])) 				                 				              			               				               	 
						.total(Integer.parseInt(resultQuery[12][k])));  

			}; break;	
		case 11:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k])) 				              			              			               				               	 
						.total(Integer.parseInt(resultQuery[13][k]))); 

			}; break;	
		case 12:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k])) 				              				              			               				               	 
						.total(Integer.parseInt(resultQuery[14][k])));   

			}; break;	
		case 13:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k])) 				               			              			               				               	 
						.total(Integer.parseInt(resultQuery[15][k])));   

			}; break;	
		case 14:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k])) 				               				              			               				               	 
						.total(Integer.parseInt(resultQuery[16][k])));   

			}; break;	
		case 15:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k])) 				              			               				               	 
						.total(Integer.parseInt(resultQuery[17][k])));     

			}; break;	
		case 16:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k])) 				                			               				               	 
						.total(Integer.parseInt(resultQuery[18][k])));    

			}; break;	
		case 17:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k])) 				               				               	 
						.total(Integer.parseInt(resultQuery[19][k])));    

			}; break;	
		case 18:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k])) 				            			               	 
						.total(Integer.parseInt(resultQuery[20][k])));   

			}; break;	
		case 19:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k])) 				              			               	 
						.total(Integer.parseInt(resultQuery[21][k])));  

			}; break;	
		case 20:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k])) 				             			               	 
						.total(Integer.parseInt(resultQuery[22][k])));   

			}; break;	
		case 21:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k])) 				              			               	 
						.total(Integer.parseInt(resultQuery[23][k])));   

			}; break;	
		case 22:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k])) 				            				               	 
						.total(Integer.parseInt(resultQuery[24][k])));    

			}; break;	
		case 23:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k])) 				              				               	 
						.total(Integer.parseInt(resultQuery[25][k])));    

			}; break;	
		case 24:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k]))
						.equip24(Integer.parseInt(resultQuery[25][k])) 				             				               	 
						.total(Integer.parseInt(resultQuery[26][k])));  

			}; break;	
		case 25:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k]))
						.equip24(Integer.parseInt(resultQuery[25][k]))
						.equip25(Integer.parseInt(resultQuery[26][k])) 				            		               	 
						.total(Integer.parseInt(resultQuery[27][k])));   

			}; break;	
		case 26:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k]))
						.equip24(Integer.parseInt(resultQuery[25][k]))
						.equip25(Integer.parseInt(resultQuery[26][k]))
						.equip26(Integer.parseInt(resultQuery[27][k])) 				            				               	 
						.total(Integer.parseInt(resultQuery[28][k])));  

			}; break;	
		case 27:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k]))
						.equip24(Integer.parseInt(resultQuery[25][k]))
						.equip25(Integer.parseInt(resultQuery[26][k]))
						.equip26(Integer.parseInt(resultQuery[27][k]))
						.equip27(Integer.parseInt(resultQuery[28][k])) 				              			                			               	 
						.total(Integer.parseInt(resultQuery[29][k])));   

			}; break;	
		case 28:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k]))
						.equip24(Integer.parseInt(resultQuery[25][k]))
						.equip25(Integer.parseInt(resultQuery[26][k]))
						.equip26(Integer.parseInt(resultQuery[27][k]))
						.equip27(Integer.parseInt(resultQuery[28][k]))		 
						.equip28(Integer.parseInt(resultQuery[29][k])) 				            			               	 
						.total(Integer.parseInt(resultQuery[30][k])));  

			}; break;	
		case 29:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k]))
						.equip24(Integer.parseInt(resultQuery[25][k]))
						.equip25(Integer.parseInt(resultQuery[26][k]))
						.equip26(Integer.parseInt(resultQuery[27][k]))
						.equip27(Integer.parseInt(resultQuery[28][k]))		 
						.equip28(Integer.parseInt(resultQuery[29][k]))		 
						.equip29(Integer.parseInt(resultQuery[30][k]))				               			            			               	 
						.total(Integer.parseInt(resultQuery[31][k])));   

			}; break;	
		case 30:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k]))
						.equip24(Integer.parseInt(resultQuery[25][k]))
						.equip25(Integer.parseInt(resultQuery[26][k]))
						.equip26(Integer.parseInt(resultQuery[27][k]))
						.equip27(Integer.parseInt(resultQuery[28][k]))		 
						.equip28(Integer.parseInt(resultQuery[29][k]))		 
						.equip29(Integer.parseInt(resultQuery[30][k]))		 
						.equip30(Integer.parseInt(resultQuery[31][k])) 				             				               	 
						.total(Integer.parseInt(resultQuery[32][k])));    

			}; break;	
		case 31:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k]))
						.equip24(Integer.parseInt(resultQuery[25][k]))
						.equip25(Integer.parseInt(resultQuery[26][k]))
						.equip26(Integer.parseInt(resultQuery[27][k]))
						.equip27(Integer.parseInt(resultQuery[28][k]))		 
						.equip28(Integer.parseInt(resultQuery[29][k]))		 
						.equip29(Integer.parseInt(resultQuery[30][k]))		 
						.equip30(Integer.parseInt(resultQuery[31][k]))		 
						.equip31(Integer.parseInt(resultQuery[32][k]))				               			               	 
						.total(Integer.parseInt(resultQuery[33][k])));    

			}; break;	
		case 32:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k]))
						.equip24(Integer.parseInt(resultQuery[25][k]))
						.equip25(Integer.parseInt(resultQuery[26][k]))
						.equip26(Integer.parseInt(resultQuery[27][k]))
						.equip27(Integer.parseInt(resultQuery[28][k]))		 
						.equip28(Integer.parseInt(resultQuery[29][k]))		 
						.equip29(Integer.parseInt(resultQuery[30][k]))		 
						.equip30(Integer.parseInt(resultQuery[31][k]))		 
						.equip31(Integer.parseInt(resultQuery[32][k]))		 
						.equip32(Integer.parseInt(resultQuery[33][k])) 				               			               	 
						.total(Integer.parseInt(resultQuery[34][k])));    

			}; break;	
		case 33:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k]))
						.equip24(Integer.parseInt(resultQuery[25][k]))
						.equip25(Integer.parseInt(resultQuery[26][k]))
						.equip26(Integer.parseInt(resultQuery[27][k]))
						.equip27(Integer.parseInt(resultQuery[28][k]))		 
						.equip28(Integer.parseInt(resultQuery[29][k]))		 
						.equip29(Integer.parseInt(resultQuery[30][k]))		 
						.equip30(Integer.parseInt(resultQuery[31][k]))		 
						.equip31(Integer.parseInt(resultQuery[32][k]))		 
						.equip32(Integer.parseInt(resultQuery[33][k]))		 
						.equip33(Integer.parseInt(resultQuery[34][k])) 				               			               	 
						.total(Integer.parseInt(resultQuery[35][k])));   

			}; break;	
		case 34:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k]))
						.equip24(Integer.parseInt(resultQuery[25][k]))
						.equip25(Integer.parseInt(resultQuery[26][k]))
						.equip26(Integer.parseInt(resultQuery[27][k]))
						.equip27(Integer.parseInt(resultQuery[28][k]))		 
						.equip28(Integer.parseInt(resultQuery[29][k]))		 
						.equip29(Integer.parseInt(resultQuery[30][k]))		 
						.equip30(Integer.parseInt(resultQuery[31][k]))		 
						.equip31(Integer.parseInt(resultQuery[32][k]))		 
						.equip32(Integer.parseInt(resultQuery[33][k]))		 
						.equip33(Integer.parseInt(resultQuery[34][k]))		 
						.equip34(Integer.parseInt(resultQuery[35][k]))				         			               	 
						.total(Integer.parseInt(resultQuery[36][k])));  

			}; break;	
		case 35:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k]))
						.equip24(Integer.parseInt(resultQuery[25][k]))
						.equip25(Integer.parseInt(resultQuery[26][k]))
						.equip26(Integer.parseInt(resultQuery[27][k]))
						.equip27(Integer.parseInt(resultQuery[28][k]))		 
						.equip28(Integer.parseInt(resultQuery[29][k]))		 
						.equip29(Integer.parseInt(resultQuery[30][k]))		 
						.equip30(Integer.parseInt(resultQuery[31][k]))		 
						.equip31(Integer.parseInt(resultQuery[32][k]))		 
						.equip32(Integer.parseInt(resultQuery[33][k]))		 
						.equip33(Integer.parseInt(resultQuery[34][k]))		 
						.equip34(Integer.parseInt(resultQuery[35][k]))		 
						.equip35(Integer.parseInt(resultQuery[36][k])) 				                			               	 
						.total(Integer.parseInt(resultQuery[37][k])));   

			}; break;	
		case 36:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k]))
						.equip24(Integer.parseInt(resultQuery[25][k]))
						.equip25(Integer.parseInt(resultQuery[26][k]))
						.equip26(Integer.parseInt(resultQuery[27][k]))
						.equip27(Integer.parseInt(resultQuery[28][k]))		 
						.equip28(Integer.parseInt(resultQuery[29][k]))		 
						.equip29(Integer.parseInt(resultQuery[30][k]))		 
						.equip30(Integer.parseInt(resultQuery[31][k]))		 
						.equip31(Integer.parseInt(resultQuery[32][k]))		 
						.equip32(Integer.parseInt(resultQuery[33][k]))		 
						.equip33(Integer.parseInt(resultQuery[34][k]))		 
						.equip34(Integer.parseInt(resultQuery[35][k]))		 
						.equip35(Integer.parseInt(resultQuery[36][k]))		 
						.equip36(Integer.parseInt(resultQuery[37][k])) 				                				               	 
						.total(Integer.parseInt(resultQuery[38][k])));   

			}; break;	
		case 37:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k]))
						.equip24(Integer.parseInt(resultQuery[25][k]))
						.equip25(Integer.parseInt(resultQuery[26][k]))
						.equip26(Integer.parseInt(resultQuery[27][k]))
						.equip27(Integer.parseInt(resultQuery[28][k]))		 
						.equip28(Integer.parseInt(resultQuery[29][k]))		 
						.equip29(Integer.parseInt(resultQuery[30][k]))		 
						.equip30(Integer.parseInt(resultQuery[31][k]))		 
						.equip31(Integer.parseInt(resultQuery[32][k]))		 
						.equip32(Integer.parseInt(resultQuery[33][k]))		 
						.equip33(Integer.parseInt(resultQuery[34][k]))		 
						.equip34(Integer.parseInt(resultQuery[35][k]))		 
						.equip35(Integer.parseInt(resultQuery[36][k]))		 
						.equip36(Integer.parseInt(resultQuery[37][k]))
						.equip37(Integer.parseInt(resultQuery[38][k]))				             				               	 
						.total(Integer.parseInt(resultQuery[39][k])));  

			}; break;	
		case 38:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k]))
						.equip24(Integer.parseInt(resultQuery[25][k]))
						.equip25(Integer.parseInt(resultQuery[26][k]))
						.equip26(Integer.parseInt(resultQuery[27][k]))
						.equip27(Integer.parseInt(resultQuery[28][k]))		 
						.equip28(Integer.parseInt(resultQuery[29][k]))		 
						.equip29(Integer.parseInt(resultQuery[30][k]))		 
						.equip30(Integer.parseInt(resultQuery[31][k]))		 
						.equip31(Integer.parseInt(resultQuery[32][k]))		 
						.equip32(Integer.parseInt(resultQuery[33][k]))		 
						.equip33(Integer.parseInt(resultQuery[34][k]))		 
						.equip34(Integer.parseInt(resultQuery[35][k]))		 
						.equip35(Integer.parseInt(resultQuery[36][k]))		 
						.equip36(Integer.parseInt(resultQuery[37][k]))
						.equip37(Integer.parseInt(resultQuery[38][k]))		 
						.equip38(Integer.parseInt(resultQuery[39][k]))				             			               	 
						.total(Integer.parseInt(resultQuery[40][k])));  

			}; break;	
		case 39:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k]))
						.equip24(Integer.parseInt(resultQuery[25][k]))
						.equip25(Integer.parseInt(resultQuery[26][k]))
						.equip26(Integer.parseInt(resultQuery[27][k]))
						.equip27(Integer.parseInt(resultQuery[28][k]))		 
						.equip28(Integer.parseInt(resultQuery[29][k]))		 
						.equip29(Integer.parseInt(resultQuery[30][k]))		 
						.equip30(Integer.parseInt(resultQuery[31][k]))		 
						.equip31(Integer.parseInt(resultQuery[32][k]))		 
						.equip32(Integer.parseInt(resultQuery[33][k]))		 
						.equip33(Integer.parseInt(resultQuery[34][k]))		 
						.equip34(Integer.parseInt(resultQuery[35][k]))		 
						.equip35(Integer.parseInt(resultQuery[36][k]))		 
						.equip36(Integer.parseInt(resultQuery[37][k]))
						.equip37(Integer.parseInt(resultQuery[38][k]))		 
						.equip38(Integer.parseInt(resultQuery[39][k]))		 
						.equip39(Integer.parseInt(resultQuery[40][k]))					             			               	 
						.total(Integer.parseInt(resultQuery[41][k])));    

			}; break;	
		case 40:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k]))
						.equip24(Integer.parseInt(resultQuery[25][k]))
						.equip25(Integer.parseInt(resultQuery[26][k]))
						.equip26(Integer.parseInt(resultQuery[27][k]))
						.equip27(Integer.parseInt(resultQuery[28][k]))		 
						.equip28(Integer.parseInt(resultQuery[29][k]))		 
						.equip29(Integer.parseInt(resultQuery[30][k]))		 
						.equip30(Integer.parseInt(resultQuery[31][k]))		 
						.equip31(Integer.parseInt(resultQuery[32][k]))		 
						.equip32(Integer.parseInt(resultQuery[33][k]))		 
						.equip33(Integer.parseInt(resultQuery[34][k]))		 
						.equip34(Integer.parseInt(resultQuery[35][k]))		 
						.equip35(Integer.parseInt(resultQuery[36][k]))		 
						.equip36(Integer.parseInt(resultQuery[37][k]))
						.equip37(Integer.parseInt(resultQuery[38][k]))		 
						.equip38(Integer.parseInt(resultQuery[39][k]))		 
						.equip39(Integer.parseInt(resultQuery[40][k]))		 
						.equip40(Integer.parseInt(resultQuery[41][k]))				                			               	 
						.total(Integer.parseInt(resultQuery[42][k])));   

			}; break;	
		case 41:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k]))
						.equip24(Integer.parseInt(resultQuery[25][k]))
						.equip25(Integer.parseInt(resultQuery[26][k]))
						.equip26(Integer.parseInt(resultQuery[27][k]))
						.equip27(Integer.parseInt(resultQuery[28][k]))		 
						.equip28(Integer.parseInt(resultQuery[29][k]))		 
						.equip29(Integer.parseInt(resultQuery[30][k]))		 
						.equip30(Integer.parseInt(resultQuery[31][k]))		 
						.equip31(Integer.parseInt(resultQuery[32][k]))		 
						.equip32(Integer.parseInt(resultQuery[33][k]))		 
						.equip33(Integer.parseInt(resultQuery[34][k]))		 
						.equip34(Integer.parseInt(resultQuery[35][k]))		 
						.equip35(Integer.parseInt(resultQuery[36][k]))		 
						.equip36(Integer.parseInt(resultQuery[37][k]))
						.equip37(Integer.parseInt(resultQuery[38][k]))		 
						.equip38(Integer.parseInt(resultQuery[39][k]))		 
						.equip39(Integer.parseInt(resultQuery[40][k]))		 
						.equip40(Integer.parseInt(resultQuery[41][k]))		 
						.equip41(Integer.parseInt(resultQuery[42][k]))				               			               			               	 
						.total(Integer.parseInt(resultQuery[43][k])));  

			}; break;	
		case 42:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k]))
						.equip24(Integer.parseInt(resultQuery[25][k]))
						.equip25(Integer.parseInt(resultQuery[26][k]))
						.equip26(Integer.parseInt(resultQuery[27][k]))
						.equip27(Integer.parseInt(resultQuery[28][k]))		 
						.equip28(Integer.parseInt(resultQuery[29][k]))		 
						.equip29(Integer.parseInt(resultQuery[30][k]))		 
						.equip30(Integer.parseInt(resultQuery[31][k]))		 
						.equip31(Integer.parseInt(resultQuery[32][k]))		 
						.equip32(Integer.parseInt(resultQuery[33][k]))		 
						.equip33(Integer.parseInt(resultQuery[34][k]))		 
						.equip34(Integer.parseInt(resultQuery[35][k]))		 
						.equip35(Integer.parseInt(resultQuery[36][k]))		 
						.equip36(Integer.parseInt(resultQuery[37][k]))
						.equip37(Integer.parseInt(resultQuery[38][k]))		 
						.equip38(Integer.parseInt(resultQuery[39][k]))		 
						.equip39(Integer.parseInt(resultQuery[40][k]))		 
						.equip40(Integer.parseInt(resultQuery[41][k]))		 
						.equip41(Integer.parseInt(resultQuery[42][k]))		 
						.equip42(Integer.parseInt(resultQuery[43][k])) 				              			               	 
						.total(Integer.parseInt(resultQuery[44][k])));    

			}; break;	
		case 43:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k]))
						.equip24(Integer.parseInt(resultQuery[25][k]))
						.equip25(Integer.parseInt(resultQuery[26][k]))
						.equip26(Integer.parseInt(resultQuery[27][k]))
						.equip27(Integer.parseInt(resultQuery[28][k]))		 
						.equip28(Integer.parseInt(resultQuery[29][k]))		 
						.equip29(Integer.parseInt(resultQuery[30][k]))		 
						.equip30(Integer.parseInt(resultQuery[31][k]))		 
						.equip31(Integer.parseInt(resultQuery[32][k]))		 
						.equip32(Integer.parseInt(resultQuery[33][k]))		 
						.equip33(Integer.parseInt(resultQuery[34][k]))		 
						.equip34(Integer.parseInt(resultQuery[35][k]))		 
						.equip35(Integer.parseInt(resultQuery[36][k]))		 
						.equip36(Integer.parseInt(resultQuery[37][k]))
						.equip37(Integer.parseInt(resultQuery[38][k]))		 
						.equip38(Integer.parseInt(resultQuery[39][k]))		 
						.equip39(Integer.parseInt(resultQuery[40][k]))		 
						.equip40(Integer.parseInt(resultQuery[41][k]))		 
						.equip41(Integer.parseInt(resultQuery[42][k]))		 
						.equip42(Integer.parseInt(resultQuery[43][k]))		 
						.equip43(Integer.parseInt(resultQuery[44][k])) 				             			               	 
						.total(Integer.parseInt(resultQuery[45][k])));    

			}; break;	
		case 44:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k]))
						.equip24(Integer.parseInt(resultQuery[25][k]))
						.equip25(Integer.parseInt(resultQuery[26][k]))
						.equip26(Integer.parseInt(resultQuery[27][k]))
						.equip27(Integer.parseInt(resultQuery[28][k]))		 
						.equip28(Integer.parseInt(resultQuery[29][k]))		 
						.equip29(Integer.parseInt(resultQuery[30][k]))		 
						.equip30(Integer.parseInt(resultQuery[31][k]))		 
						.equip31(Integer.parseInt(resultQuery[32][k]))		 
						.equip32(Integer.parseInt(resultQuery[33][k]))		 
						.equip33(Integer.parseInt(resultQuery[34][k]))		 
						.equip34(Integer.parseInt(resultQuery[35][k]))		 
						.equip35(Integer.parseInt(resultQuery[36][k]))		 
						.equip36(Integer.parseInt(resultQuery[37][k]))
						.equip37(Integer.parseInt(resultQuery[38][k]))		 
						.equip38(Integer.parseInt(resultQuery[39][k]))		 
						.equip39(Integer.parseInt(resultQuery[40][k]))		 
						.equip40(Integer.parseInt(resultQuery[41][k]))		 
						.equip41(Integer.parseInt(resultQuery[42][k]))		 
						.equip42(Integer.parseInt(resultQuery[43][k]))		 
						.equip43(Integer.parseInt(resultQuery[44][k]))		 
						.equip44(Integer.parseInt(resultQuery[45][k]))	 				          	 				               			               	 
						.total(Integer.parseInt(resultQuery[46][k])));   

			}; break;	
		case 45:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   				            
						.dateTime(resultQuery[1][k])
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k]))
						.equip24(Integer.parseInt(resultQuery[25][k]))
						.equip25(Integer.parseInt(resultQuery[26][k]))
						.equip26(Integer.parseInt(resultQuery[27][k]))
						.equip27(Integer.parseInt(resultQuery[28][k]))		 
						.equip28(Integer.parseInt(resultQuery[29][k]))		 
						.equip29(Integer.parseInt(resultQuery[30][k]))		 
						.equip30(Integer.parseInt(resultQuery[31][k]))		 
						.equip31(Integer.parseInt(resultQuery[32][k]))		 
						.equip32(Integer.parseInt(resultQuery[33][k]))		 
						.equip33(Integer.parseInt(resultQuery[34][k]))		 
						.equip34(Integer.parseInt(resultQuery[35][k]))		 
						.equip35(Integer.parseInt(resultQuery[36][k]))		 
						.equip36(Integer.parseInt(resultQuery[37][k]))
						.equip37(Integer.parseInt(resultQuery[38][k]))		 
						.equip38(Integer.parseInt(resultQuery[39][k]))		 
						.equip39(Integer.parseInt(resultQuery[40][k]))		 
						.equip40(Integer.parseInt(resultQuery[41][k]))		 
						.equip41(Integer.parseInt(resultQuery[42][k]))		 
						.equip42(Integer.parseInt(resultQuery[43][k]))		 
						.equip43(Integer.parseInt(resultQuery[44][k]))		 
						.equip44(Integer.parseInt(resultQuery[45][k]))		 
						.equip45(Integer.parseInt(resultQuery[46][k])) 				              			               	 
						.total(Integer.parseInt(resultQuery[47][k])));    

			}; break;	
		case 46:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])							 
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k]))
						.equip24(Integer.parseInt(resultQuery[25][k]))
						.equip25(Integer.parseInt(resultQuery[26][k]))
						.equip26(Integer.parseInt(resultQuery[27][k]))
						.equip27(Integer.parseInt(resultQuery[28][k]))		 
						.equip28(Integer.parseInt(resultQuery[29][k]))		 
						.equip29(Integer.parseInt(resultQuery[30][k]))		 
						.equip30(Integer.parseInt(resultQuery[31][k]))		 
						.equip31(Integer.parseInt(resultQuery[32][k]))		 
						.equip32(Integer.parseInt(resultQuery[33][k]))		 
						.equip33(Integer.parseInt(resultQuery[34][k]))		 
						.equip34(Integer.parseInt(resultQuery[35][k]))		 
						.equip35(Integer.parseInt(resultQuery[36][k]))		 
						.equip36(Integer.parseInt(resultQuery[37][k]))
						.equip37(Integer.parseInt(resultQuery[38][k]))		 
						.equip38(Integer.parseInt(resultQuery[39][k]))		 
						.equip39(Integer.parseInt(resultQuery[40][k]))		 
						.equip40(Integer.parseInt(resultQuery[41][k]))		 
						.equip41(Integer.parseInt(resultQuery[42][k]))		 
						.equip42(Integer.parseInt(resultQuery[43][k]))		 
						.equip43(Integer.parseInt(resultQuery[44][k]))		 
						.equip44(Integer.parseInt(resultQuery[45][k]))		 
						.equip45(Integer.parseInt(resultQuery[46][k]))		 
						.equip46(Integer.parseInt(resultQuery[47][k]))				               	 
						.total(Integer.parseInt(resultQuery[48][k])));  

			}; break;	
		case 47:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])
						.dateTime(resultQuery[1][k])							 
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k]))
						.equip24(Integer.parseInt(resultQuery[25][k]))
						.equip25(Integer.parseInt(resultQuery[26][k]))
						.equip26(Integer.parseInt(resultQuery[27][k]))
						.equip27(Integer.parseInt(resultQuery[28][k]))		 
						.equip28(Integer.parseInt(resultQuery[29][k]))		 
						.equip29(Integer.parseInt(resultQuery[30][k]))		 
						.equip30(Integer.parseInt(resultQuery[31][k]))		 
						.equip31(Integer.parseInt(resultQuery[32][k]))		 
						.equip32(Integer.parseInt(resultQuery[33][k]))		 
						.equip33(Integer.parseInt(resultQuery[34][k]))		 
						.equip34(Integer.parseInt(resultQuery[35][k]))		 
						.equip35(Integer.parseInt(resultQuery[36][k]))		 
						.equip36(Integer.parseInt(resultQuery[37][k]))
						.equip37(Integer.parseInt(resultQuery[38][k]))		 
						.equip38(Integer.parseInt(resultQuery[39][k]))		 
						.equip39(Integer.parseInt(resultQuery[40][k]))		 
						.equip40(Integer.parseInt(resultQuery[41][k]))		 
						.equip41(Integer.parseInt(resultQuery[42][k]))		 
						.equip42(Integer.parseInt(resultQuery[43][k]))		 
						.equip43(Integer.parseInt(resultQuery[44][k]))		 
						.equip44(Integer.parseInt(resultQuery[45][k]))		 
						.equip45(Integer.parseInt(resultQuery[46][k]))		 
						.equip46(Integer.parseInt(resultQuery[47][k]))		 
						.equip47(Integer.parseInt(resultQuery[48][k])) 				               	 
						.total(Integer.parseInt(resultQuery[49][k])));    

			}; break;	
		case 48:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])  
						.dateTime(resultQuery[1][k])							 
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k]))
						.equip24(Integer.parseInt(resultQuery[25][k]))
						.equip25(Integer.parseInt(resultQuery[26][k]))
						.equip26(Integer.parseInt(resultQuery[27][k]))
						.equip27(Integer.parseInt(resultQuery[28][k]))		 
						.equip28(Integer.parseInt(resultQuery[29][k]))		 
						.equip29(Integer.parseInt(resultQuery[30][k]))		 
						.equip30(Integer.parseInt(resultQuery[31][k]))		 
						.equip31(Integer.parseInt(resultQuery[32][k]))		 
						.equip32(Integer.parseInt(resultQuery[33][k]))		 
						.equip33(Integer.parseInt(resultQuery[34][k]))		 
						.equip34(Integer.parseInt(resultQuery[35][k]))		 
						.equip35(Integer.parseInt(resultQuery[36][k]))		 
						.equip36(Integer.parseInt(resultQuery[37][k]))
						.equip37(Integer.parseInt(resultQuery[38][k]))		 
						.equip38(Integer.parseInt(resultQuery[39][k]))		 
						.equip39(Integer.parseInt(resultQuery[40][k]))		 
						.equip40(Integer.parseInt(resultQuery[41][k]))		 
						.equip41(Integer.parseInt(resultQuery[42][k]))		 
						.equip42(Integer.parseInt(resultQuery[43][k]))		 
						.equip43(Integer.parseInt(resultQuery[44][k]))		 
						.equip44(Integer.parseInt(resultQuery[45][k]))		 
						.equip45(Integer.parseInt(resultQuery[46][k]))		 
						.equip46(Integer.parseInt(resultQuery[47][k]))		 
						.equip47(Integer.parseInt(resultQuery[48][k]))		 
						.equip48(Integer.parseInt(resultQuery[49][k])) 				               		 
						.total(Integer.parseInt(resultQuery[50][k])));  

			}; break;	
		case 49:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])
						.dateTime(resultQuery[1][k])							 
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k]))
						.equip24(Integer.parseInt(resultQuery[25][k]))
						.equip25(Integer.parseInt(resultQuery[26][k]))
						.equip26(Integer.parseInt(resultQuery[27][k]))
						.equip27(Integer.parseInt(resultQuery[28][k]))		 
						.equip28(Integer.parseInt(resultQuery[29][k]))		 
						.equip29(Integer.parseInt(resultQuery[30][k]))		 
						.equip30(Integer.parseInt(resultQuery[31][k]))		 
						.equip31(Integer.parseInt(resultQuery[32][k]))		 
						.equip32(Integer.parseInt(resultQuery[33][k]))		 
						.equip33(Integer.parseInt(resultQuery[34][k]))		 
						.equip34(Integer.parseInt(resultQuery[35][k]))		 
						.equip35(Integer.parseInt(resultQuery[36][k]))		 
						.equip36(Integer.parseInt(resultQuery[37][k]))
						.equip37(Integer.parseInt(resultQuery[38][k]))		 
						.equip38(Integer.parseInt(resultQuery[39][k]))		 
						.equip39(Integer.parseInt(resultQuery[40][k]))		 
						.equip40(Integer.parseInt(resultQuery[41][k]))		 
						.equip41(Integer.parseInt(resultQuery[42][k]))		 
						.equip42(Integer.parseInt(resultQuery[43][k]))		 
						.equip43(Integer.parseInt(resultQuery[44][k]))		 
						.equip44(Integer.parseInt(resultQuery[45][k]))		 
						.equip45(Integer.parseInt(resultQuery[46][k]))		 
						.equip46(Integer.parseInt(resultQuery[47][k]))		 
						.equip47(Integer.parseInt(resultQuery[48][k]))		 
						.equip48(Integer.parseInt(resultQuery[49][k]))
						.equip49(Integer.parseInt(resultQuery[50][k]))			              		 
						.total(Integer.parseInt(resultQuery[51][k])));  

			}; break;	
		case 50:          			 
			for(int k = 0; k < getNumRegisters(); k++) {

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])						 
						.equip1(Integer.parseInt(resultQuery[2][k]))
						.equip2(Integer.parseInt(resultQuery[3][k]))
						.equip3(Integer.parseInt(resultQuery[4][k]))
						.equip4(Integer.parseInt(resultQuery[5][k]))
						.equip5(Integer.parseInt(resultQuery[6][k]))
						.equip6(Integer.parseInt(resultQuery[7][k]))
						.equip7(Integer.parseInt(resultQuery[8][k]))
						.equip8(Integer.parseInt(resultQuery[9][k]))
						.equip9(Integer.parseInt(resultQuery[10][k]))
						.equip10(Integer.parseInt(resultQuery[11][k]))
						.equip11(Integer.parseInt(resultQuery[12][k]))
						.equip12(Integer.parseInt(resultQuery[13][k]))
						.equip13(Integer.parseInt(resultQuery[14][k]))
						.equip14(Integer.parseInt(resultQuery[15][k]))
						.equip15(Integer.parseInt(resultQuery[16][k]))
						.equip16(Integer.parseInt(resultQuery[17][k]))
						.equip17(Integer.parseInt(resultQuery[18][k]))
						.equip18(Integer.parseInt(resultQuery[19][k]))
						.equip19(Integer.parseInt(resultQuery[20][k]))
						.equip20(Integer.parseInt(resultQuery[21][k]))
						.equip21(Integer.parseInt(resultQuery[22][k]))
						.equip22(Integer.parseInt(resultQuery[23][k]))
						.equip23(Integer.parseInt(resultQuery[24][k]))
						.equip24(Integer.parseInt(resultQuery[25][k]))
						.equip25(Integer.parseInt(resultQuery[26][k]))
						.equip26(Integer.parseInt(resultQuery[27][k]))
						.equip27(Integer.parseInt(resultQuery[28][k]))		 
						.equip28(Integer.parseInt(resultQuery[29][k]))		 
						.equip29(Integer.parseInt(resultQuery[30][k]))		 
						.equip30(Integer.parseInt(resultQuery[31][k]))		 
						.equip31(Integer.parseInt(resultQuery[32][k]))		 
						.equip32(Integer.parseInt(resultQuery[33][k]))		 
						.equip33(Integer.parseInt(resultQuery[34][k]))		 
						.equip34(Integer.parseInt(resultQuery[35][k]))		 
						.equip35(Integer.parseInt(resultQuery[36][k]))		 
						.equip36(Integer.parseInt(resultQuery[37][k]))
						.equip37(Integer.parseInt(resultQuery[38][k]))		 
						.equip38(Integer.parseInt(resultQuery[39][k]))		 
						.equip39(Integer.parseInt(resultQuery[40][k]))		 
						.equip40(Integer.parseInt(resultQuery[41][k]))		 
						.equip41(Integer.parseInt(resultQuery[42][k]))		 
						.equip42(Integer.parseInt(resultQuery[43][k]))		 
						.equip43(Integer.parseInt(resultQuery[44][k]))		 
						.equip44(Integer.parseInt(resultQuery[45][k]))		 
						.equip45(Integer.parseInt(resultQuery[46][k]))		 
						.equip46(Integer.parseInt(resultQuery[47][k]))		 
						.equip47(Integer.parseInt(resultQuery[48][k]))		 
						.equip48(Integer.parseInt(resultQuery[49][k]))
						.equip49(Integer.parseInt(resultQuery[50][k]))		 
						.equip50(Integer.parseInt(resultQuery[51][k]))		 
						.total(Integer.parseInt(resultQuery[52][k])));  

			}; break;	
		}     			 
	}  

	/* COUNT VEHICLES PERIOD -- TYPE = 2 */

	public void countVehiclesPeriodBuilder() {


		for(int k = 0; k < getNumRegisters(); k++) {      

			resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
					.dateTime(resultQuery[1][k])   
					.lightDir1(Integer.parseInt(resultQuery[2][k]))
					.heavyDir1(Integer.parseInt(resultQuery[3][k]))
					.motosDir1(Integer.parseInt(resultQuery[4][k]))		 				                	
					.lightDir2(Integer.parseInt(resultQuery[5][k]))
					.heavyDir2(Integer.parseInt(resultQuery[6][k]))
					.motosDir2(Integer.parseInt(resultQuery[7][k]))
					.total(Integer.parseInt(resultQuery[8][k]))); 		 			 
		}    			 

	}

	/* MONTH FLOW -- TYPE = 3 */      

	public void monthFlowBuilder() {

		for(int k = 0; k < getNumRegisters(); k++) {      

			resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
					.dateTime(resultQuery[1][k])		 					            
					.lightDir1(Integer.parseInt(resultQuery[2][k]))
					.heavyDir1(Integer.parseInt(resultQuery[3][k]))
					.motosDir1(Integer.parseInt(resultQuery[4][k]))	 		 				                
					.lightDir2(Integer.parseInt(resultQuery[5][k]))
					.heavyDir2(Integer.parseInt(resultQuery[6][k])) 
					.motosDir2(Integer.parseInt(resultQuery[7][k]))
					.speed1(Integer.parseInt(resultQuery[8][k]))
					.speed2(Integer.parseInt(resultQuery[9][k])));	
		}             

	}

	/* PERIOD FLOW -- TYPE = 4 */   

	public void periodFlowBuilder() {


		for(int k = 0; k < getNumRegisters(); k++) {      

			resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
					.dateTime(resultQuery[1][k]) 
					.equipment(resultQuery[6][k])		 					            
					.lightDir1(Integer.parseInt(resultQuery[7][k]))
					.heavyDir1(Integer.parseInt(resultQuery[8][k]))
					.motosDir1(Integer.parseInt(resultQuery[9][k]))	
					.total1(Integer.parseInt(resultQuery[10][k]))	
					.speed1(Integer.parseInt(resultQuery[18][k]))		 				               
					.lightDir2(Integer.parseInt(resultQuery[11][k]))
					.heavyDir2(Integer.parseInt(resultQuery[12][k]))
					.motosDir2(Integer.parseInt(resultQuery[13][k]))		 				            
					.total2(Integer.parseInt(resultQuery[14][k])) 
					.speed2(Integer.parseInt(resultQuery[22][k])));
		}    	
	}      

	/* WEIGHING -- TYPE = 5 */  

	public void weighingBuilder(int classLength) {


		if(classLength == 1) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])  
						.class1(Integer.parseInt(resultQuery[2][k])));  				                            	 				                 

			}

		} else if(classLength == 2) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])  
						.class1(Integer.parseInt(resultQuery[2][k])) 
						.class2(Integer.parseInt(resultQuery[3][k])));				                            	 				                 

			}

		} else if(classLength == 3) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])  
						.class1(Integer.parseInt(resultQuery[2][k])) 
						.class2(Integer.parseInt(resultQuery[3][k])) 
						.class3(Integer.parseInt(resultQuery[4][k])));  	 				                           		 				                 

			}

		} else if(classLength == 4) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])  
						.class1(Integer.parseInt(resultQuery[2][k])) 
						.class2(Integer.parseInt(resultQuery[3][k])) 
						.class3(Integer.parseInt(resultQuery[4][k]))
						.class4(Integer.parseInt(resultQuery[5][k]))); 		 				                			 				                          		 				                 

			}

		}else if(classLength == 5) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])  
						.class1(Integer.parseInt(resultQuery[2][k])) 
						.class2(Integer.parseInt(resultQuery[3][k])) 
						.class3(Integer.parseInt(resultQuery[4][k]))
						.class4(Integer.parseInt(resultQuery[5][k]))
						.class5(Integer.parseInt(resultQuery[6][k])));                       				                              		 				                 

			}

		} else if(classLength == 6) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])  
						.class1(Integer.parseInt(resultQuery[2][k])) 
						.class2(Integer.parseInt(resultQuery[3][k])) 
						.class3(Integer.parseInt(resultQuery[4][k]))
						.class4(Integer.parseInt(resultQuery[5][k]))
						.class5(Integer.parseInt(resultQuery[6][k]))
						.class6(Integer.parseInt(resultQuery[7][k])));   

			}

		} else if(classLength == 7) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])  
						.class1(Integer.parseInt(resultQuery[2][k])) 
						.class2(Integer.parseInt(resultQuery[3][k])) 
						.class3(Integer.parseInt(resultQuery[4][k]))
						.class4(Integer.parseInt(resultQuery[5][k]))
						.class5(Integer.parseInt(resultQuery[6][k]))
						.class6(Integer.parseInt(resultQuery[7][k]))
						.class7(Integer.parseInt(resultQuery[8][k]))); 		 				                           			 	 	  				                              		 				                 

			}

		} else if(classLength == 8) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])  
						.class1(Integer.parseInt(resultQuery[2][k])) 
						.class2(Integer.parseInt(resultQuery[3][k])) 
						.class3(Integer.parseInt(resultQuery[4][k]))
						.class4(Integer.parseInt(resultQuery[5][k]))
						.class5(Integer.parseInt(resultQuery[6][k]))
						.class6(Integer.parseInt(resultQuery[7][k]))
						.class7(Integer.parseInt(resultQuery[8][k]))
						.class8(Integer.parseInt(resultQuery[9][k])));   		 				          

			}

		} else if(classLength == 9) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])  
						.class1(Integer.parseInt(resultQuery[2][k])) 
						.class2(Integer.parseInt(resultQuery[3][k])) 
						.class3(Integer.parseInt(resultQuery[4][k]))
						.class4(Integer.parseInt(resultQuery[5][k]))
						.class5(Integer.parseInt(resultQuery[6][k]))
						.class6(Integer.parseInt(resultQuery[7][k]))
						.class7(Integer.parseInt(resultQuery[8][k]))
						.class8(Integer.parseInt(resultQuery[9][k]))    		 				          
						.class9(Integer.parseInt(resultQuery[10][k])));

			}

		} else if(classLength == 10) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])  
						.class1(Integer.parseInt(resultQuery[2][k])) 
						.class2(Integer.parseInt(resultQuery[3][k])) 
						.class3(Integer.parseInt(resultQuery[4][k]))
						.class4(Integer.parseInt(resultQuery[5][k]))
						.class5(Integer.parseInt(resultQuery[6][k]))
						.class6(Integer.parseInt(resultQuery[7][k]))
						.class7(Integer.parseInt(resultQuery[8][k]))
						.class8(Integer.parseInt(resultQuery[9][k]))    		 				          
						.class9(Integer.parseInt(resultQuery[10][k]))
						.class10(Integer.parseInt(resultQuery[11][k])));		 	 	  				                            		 				                 

			}

		} else if(classLength == 11) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])  
						.class1(Integer.parseInt(resultQuery[2][k])) 
						.class2(Integer.parseInt(resultQuery[3][k])) 
						.class3(Integer.parseInt(resultQuery[4][k]))
						.class4(Integer.parseInt(resultQuery[5][k]))
						.class5(Integer.parseInt(resultQuery[6][k]))
						.class6(Integer.parseInt(resultQuery[7][k]))
						.class7(Integer.parseInt(resultQuery[8][k]))
						.class8(Integer.parseInt(resultQuery[9][k]))    		 				          
						.class9(Integer.parseInt(resultQuery[10][k]))
						.class10(Integer.parseInt(resultQuery[11][k]))
						.class11(Integer.parseInt(resultQuery[12][k])));		 	 	  				                               		 				                 

			}

		} else if(classLength == 12) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])  
						.class1(Integer.parseInt(resultQuery[2][k])) 
						.class2(Integer.parseInt(resultQuery[3][k])) 
						.class3(Integer.parseInt(resultQuery[4][k]))
						.class4(Integer.parseInt(resultQuery[5][k]))
						.class5(Integer.parseInt(resultQuery[6][k]))
						.class6(Integer.parseInt(resultQuery[7][k]))
						.class7(Integer.parseInt(resultQuery[8][k]))
						.class8(Integer.parseInt(resultQuery[9][k]))    		 				          
						.class9(Integer.parseInt(resultQuery[10][k]))
						.class10(Integer.parseInt(resultQuery[11][k]))
						.class11(Integer.parseInt(resultQuery[12][k]))
						.class12(Integer.parseInt(resultQuery[13][k])));		 	 	  				                           		 				                 

			}

		} else if(classLength == 13) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])  
						.class1(Integer.parseInt(resultQuery[2][k])) 
						.class2(Integer.parseInt(resultQuery[3][k])) 
						.class3(Integer.parseInt(resultQuery[4][k]))
						.class4(Integer.parseInt(resultQuery[5][k]))
						.class5(Integer.parseInt(resultQuery[6][k]))
						.class6(Integer.parseInt(resultQuery[7][k]))
						.class7(Integer.parseInt(resultQuery[8][k]))
						.class8(Integer.parseInt(resultQuery[9][k]))    		 				          
						.class9(Integer.parseInt(resultQuery[10][k]))
						.class10(Integer.parseInt(resultQuery[11][k]))
						.class11(Integer.parseInt(resultQuery[12][k]))
						.class12(Integer.parseInt(resultQuery[13][k]))
						.class13(Integer.parseInt(resultQuery[14][k])));	 	 				                 

			}

		}    		                  
	}

	/* CLASS -- TYPE = 6 */ 

	public void classesBuilder(int classLength) {


		if(classLength == 1) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])  
						.class1(Integer.parseInt(resultQuery[2][k]))  				                            	 				                 
						.total(Integer.parseInt(resultQuery[5][k])));    				    				 
			}

		} else if(classLength == 2) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])  
						.class1(Integer.parseInt(resultQuery[2][k])) 
						.class2(Integer.parseInt(resultQuery[5][k]))  	 				                            		 				                 
						.total(Integer.parseInt(resultQuery[8][k])));    				    				 
			}

		} else if(classLength == 3) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])  
						.class1(Integer.parseInt(resultQuery[2][k])) 
						.class2(Integer.parseInt(resultQuery[5][k])) 
						.class3(Integer.parseInt(resultQuery[8][k])) 	 	 				                           		 				                 
						.total(Integer.parseInt(resultQuery[11][k])));    				    				 
			}

		} else if(classLength == 4) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])  
						.class1(Integer.parseInt(resultQuery[2][k])) 
						.class2(Integer.parseInt(resultQuery[5][k])) 
						.class3(Integer.parseInt(resultQuery[8][k]))
						.class4(Integer.parseInt(resultQuery[11][k])) 	 	 	 				                          		 				                 
						.total(Integer.parseInt(resultQuery[14][k])));    				    				 
			}

		}else if(classLength == 5) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])  
						.class1(Integer.parseInt(resultQuery[2][k])) 
						.class2(Integer.parseInt(resultQuery[5][k])) 
						.class3(Integer.parseInt(resultQuery[8][k]))
						.class4(Integer.parseInt(resultQuery[11][k]))
						.class5(Integer.parseInt(resultQuery[14][k])) 	 	 	  				                              		 				                 
						.total(Integer.parseInt(resultQuery[17][k])));    				    				 
			}

		} else if(classLength == 6) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])  
						.class1(Integer.parseInt(resultQuery[2][k])) 
						.class2(Integer.parseInt(resultQuery[5][k])) 
						.class3(Integer.parseInt(resultQuery[8][k]))
						.class4(Integer.parseInt(resultQuery[11][k]))
						.class5(Integer.parseInt(resultQuery[14][k]))
						.class6(Integer.parseInt(resultQuery[17][k])) 	 	 	  				                             		 				                 
						.total(Integer.parseInt(resultQuery[20][k])));    				    				 
			}

		} else if(classLength == 7) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])  
						.class1(Integer.parseInt(resultQuery[2][k])) 
						.class2(Integer.parseInt(resultQuery[5][k])) 
						.class3(Integer.parseInt(resultQuery[8][k]))
						.class4(Integer.parseInt(resultQuery[11][k]))
						.class5(Integer.parseInt(resultQuery[14][k]))
						.class6(Integer.parseInt(resultQuery[17][k]))
						.class7(Integer.parseInt(resultQuery[20][k])) 	 	 	  				                              		 				                 
						.total(Integer.parseInt(resultQuery[23][k])));    				    				 
			}

		} else if(classLength == 8) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])  
						.class1(Integer.parseInt(resultQuery[2][k])) 
						.class2(Integer.parseInt(resultQuery[5][k])) 
						.class3(Integer.parseInt(resultQuery[8][k]))
						.class4(Integer.parseInt(resultQuery[11][k]))
						.class5(Integer.parseInt(resultQuery[14][k]))
						.class6(Integer.parseInt(resultQuery[17][k]))
						.class7(Integer.parseInt(resultQuery[20][k]))
						.class8(Integer.parseInt(resultQuery[23][k]))  
						.total(Integer.parseInt(resultQuery[26][k])));    				    				 
			}

		} else if(classLength == 9) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])  
						.class1(Integer.parseInt(resultQuery[2][k])) 
						.class2(Integer.parseInt(resultQuery[5][k])) 
						.class3(Integer.parseInt(resultQuery[8][k]))
						.class4(Integer.parseInt(resultQuery[11][k]))
						.class5(Integer.parseInt(resultQuery[14][k]))
						.class6(Integer.parseInt(resultQuery[17][k]))
						.class7(Integer.parseInt(resultQuery[20][k]))
						.class8(Integer.parseInt(resultQuery[23][k]))    		 				          
						.class9(Integer.parseInt(resultQuery[26][k])) 	 	 	  				                             		 				                 
						.total(Integer.parseInt(resultQuery[29][k])));    				    				 
			}

		} else if(classLength == 10) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])  
						.class1(Integer.parseInt(resultQuery[2][k])) 
						.class2(Integer.parseInt(resultQuery[5][k])) 
						.class3(Integer.parseInt(resultQuery[8][k]))
						.class4(Integer.parseInt(resultQuery[11][k]))
						.class5(Integer.parseInt(resultQuery[14][k]))
						.class6(Integer.parseInt(resultQuery[17][k]))
						.class7(Integer.parseInt(resultQuery[20][k]))
						.class8(Integer.parseInt(resultQuery[23][k]))    		 				          
						.class9(Integer.parseInt(resultQuery[26][k]))
						.class10(Integer.parseInt(resultQuery[29][k])) 	 	 	  				                            		 				                 
						.total(Integer.parseInt(resultQuery[32][k])));    				    				 
			}

		} else if(classLength == 11) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])  
						.class1(Integer.parseInt(resultQuery[2][k])) 
						.class2(Integer.parseInt(resultQuery[5][k])) 
						.class3(Integer.parseInt(resultQuery[8][k]))
						.class4(Integer.parseInt(resultQuery[11][k]))
						.class5(Integer.parseInt(resultQuery[14][k]))
						.class6(Integer.parseInt(resultQuery[17][k]))
						.class7(Integer.parseInt(resultQuery[20][k]))
						.class8(Integer.parseInt(resultQuery[23][k]))    		 				          
						.class9(Integer.parseInt(resultQuery[26][k]))
						.class10(Integer.parseInt(resultQuery[29][k]))
						.class11(Integer.parseInt(resultQuery[32][k])) 	 	 	  				                               		 				                 
						.total(Integer.parseInt(resultQuery[35][k])));    				    				 
			}

		} else if(classLength == 12) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])  
						.class1(Integer.parseInt(resultQuery[2][k])) 
						.class2(Integer.parseInt(resultQuery[5][k])) 
						.class3(Integer.parseInt(resultQuery[8][k]))
						.class4(Integer.parseInt(resultQuery[11][k]))
						.class5(Integer.parseInt(resultQuery[14][k]))
						.class6(Integer.parseInt(resultQuery[17][k]))
						.class7(Integer.parseInt(resultQuery[20][k]))
						.class8(Integer.parseInt(resultQuery[23][k]))    		 				          
						.class9(Integer.parseInt(resultQuery[26][k]))
						.class10(Integer.parseInt(resultQuery[29][k]))
						.class11(Integer.parseInt(resultQuery[32][k]))
						.class12(Integer.parseInt(resultQuery[35][k])) 	 	 	  				                           		 				                 
						.total(Integer.parseInt(resultQuery[38][k])));    				    				 
			}

		} else if(classLength == 13) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])  
						.class1(Integer.parseInt(resultQuery[2][k])) 
						.class2(Integer.parseInt(resultQuery[5][k])) 
						.class3(Integer.parseInt(resultQuery[8][k]))
						.class4(Integer.parseInt(resultQuery[11][k]))
						.class5(Integer.parseInt(resultQuery[14][k]))
						.class6(Integer.parseInt(resultQuery[17][k]))
						.class7(Integer.parseInt(resultQuery[20][k]))
						.class8(Integer.parseInt(resultQuery[23][k]))    		 				          
						.class9(Integer.parseInt(resultQuery[26][k]))
						.class10(Integer.parseInt(resultQuery[29][k]))
						.class11(Integer.parseInt(resultQuery[32][k]))
						.class12(Integer.parseInt(resultQuery[35][k]))
						.class13(Integer.parseInt(resultQuery[38][k]))    		 				                 
						.total(Integer.parseInt(resultQuery[41][k])));    				    				 
			}

		}    		                  
	}

	/* AXLES -- TYPE = 7 */ 

	public void axlesBuilder(int axleLength) {

		if(axleLength == 1) {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   
						.dateTime(resultQuery[1][k])
						.axles1(Integer.parseInt(resultQuery[2][k])) 				                
						.total(Integer.parseInt(resultQuery[5][k])));    				    				 
			}

		} else if(axleLength == 2) {

			for(int k = 0; k < getNumRegisters(); k++) {   

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])
						.axles1(Integer.parseInt(resultQuery[2][k])) 
						.axles2(Integer.parseInt(resultQuery[5][k]))
						.total(Integer.parseInt(resultQuery[8][k])));
			}

		} else  if(axleLength == 3) {

			for(int k = 0; k < getNumRegisters(); k++) {   

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])
						.axles1(Integer.parseInt(resultQuery[2][k])) 
						.axles2(Integer.parseInt(resultQuery[5][k]))
						.axles3(Integer.parseInt(resultQuery[8][k]))
						.total(Integer.parseInt(resultQuery[11][k])));
			}

		}else if(axleLength == 4) {

			for(int k = 0; k < getNumRegisters(); k++) {   

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])   
						.dateTime(resultQuery[1][k])
						.axles1(Integer.parseInt(resultQuery[2][k])) 
						.axles2(Integer.parseInt(resultQuery[5][k]))
						.axles3(Integer.parseInt(resultQuery[8][k]))
						.axles4(Integer.parseInt(resultQuery[11][k]))
						.total(Integer.parseInt(resultQuery[14][k])));
			}

		}else if(axleLength == 5) {

			for(int k = 0; k < getNumRegisters(); k++) {   

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])  
						.dateTime(resultQuery[1][k])
						.axles1(Integer.parseInt(resultQuery[2][k])) 
						.axles2(Integer.parseInt(resultQuery[5][k]))
						.axles3(Integer.parseInt(resultQuery[8][k]))
						.axles4(Integer.parseInt(resultQuery[11][k]))
						.axles5(Integer.parseInt(resultQuery[14][k]))
						.total(Integer.parseInt(resultQuery[17][k])));

			}

		}else if(axleLength == 6) {

			for(int k = 0; k < getNumRegisters(); k++) {   

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])    
						.dateTime(resultQuery[1][k])
						.axles1(Integer.parseInt(resultQuery[2][k])) 
						.axles2(Integer.parseInt(resultQuery[5][k]))
						.axles3(Integer.parseInt(resultQuery[8][k]))
						.axles4(Integer.parseInt(resultQuery[11][k]))
						.axles5(Integer.parseInt(resultQuery[14][k]))
						.axles6(Integer.parseInt(resultQuery[17][k]))			               
						.total(Integer.parseInt(resultQuery[20][k])));
			}

		}else if(axleLength == 7) {

			for(int k = 0; k < getNumRegisters(); k++) {   

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])
						.dateTime(resultQuery[1][k])
						.axles1(Integer.parseInt(resultQuery[2][k])) 
						.axles2(Integer.parseInt(resultQuery[5][k]))
						.axles3(Integer.parseInt(resultQuery[8][k]))
						.axles4(Integer.parseInt(resultQuery[11][k]))
						.axles5(Integer.parseInt(resultQuery[14][k]))
						.axles6(Integer.parseInt(resultQuery[17][k]))
						.axles7(Integer.parseInt(resultQuery[20][k]))
						.total(Integer.parseInt(resultQuery[23][k])));			              
			}

		}else if(axleLength == 8) {

			for(int k = 0; k < getNumRegisters(); k++) {   

				resultList.add(new SatReports.Builder().date(resultQuery[0][k]) 
						.dateTime(resultQuery[1][k])
						.axles1(Integer.parseInt(resultQuery[2][k])) 
						.axles2(Integer.parseInt(resultQuery[5][k]))
						.axles3(Integer.parseInt(resultQuery[8][k]))
						.axles4(Integer.parseInt(resultQuery[11][k]))
						.axles5(Integer.parseInt(resultQuery[14][k]))
						.axles6(Integer.parseInt(resultQuery[17][k]))
						.axles7(Integer.parseInt(resultQuery[20][k]))
						.axles8(Integer.parseInt(resultQuery[23][k]))
						.total(Integer.parseInt(resultQuery[26][k])));			               
			}

		} else if(axleLength == 9) {

			for(int k = 0; k < getNumRegisters(); k++) {   

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])
						.dateTime(resultQuery[1][k])
						.axles1(Integer.parseInt(resultQuery[2][k])) 
						.axles2(Integer.parseInt(resultQuery[5][k]))
						.axles3(Integer.parseInt(resultQuery[8][k]))
						.axles4(Integer.parseInt(resultQuery[11][k]))
						.axles5(Integer.parseInt(resultQuery[14][k]))
						.axles6(Integer.parseInt(resultQuery[17][k]))
						.axles7(Integer.parseInt(resultQuery[20][k]))
						.axles8(Integer.parseInt(resultQuery[23][k]))
						.axles9(Integer.parseInt(resultQuery[26][k]))
						.total(Integer.parseInt(resultQuery[29][k])));
			}    		

		} else if(axleLength == 10) {

			for(int k = 0; k < getNumRegisters(); k++) {   

				resultList.add(new SatReports.Builder().date(resultQuery[0][k])
						.dateTime(resultQuery[1][k])
						.axles1(Integer.parseInt(resultQuery[2][k])) 
						.axles2(Integer.parseInt(resultQuery[5][k]))
						.axles3(Integer.parseInt(resultQuery[8][k]))
						.axles4(Integer.parseInt(resultQuery[11][k]))
						.axles5(Integer.parseInt(resultQuery[14][k]))
						.axles6(Integer.parseInt(resultQuery[17][k]))
						.axles7(Integer.parseInt(resultQuery[20][k]))
						.axles8(Integer.parseInt(resultQuery[23][k]))
						.axles9(Integer.parseInt(resultQuery[26][k]))
						.axles10(Integer.parseInt(resultQuery[29][k]))
						.total(Integer.parseInt(resultQuery[32][k])));
			}    		
		}  
	}

	/* SPEED -- TYPE = 8 */ 

	public void speedBuilder() {

		for(int k = 0; k < getNumRegisters(); k++) {         			

			resultList.add(new SatReports.Builder().date(resultQuery[0][k])
					.dateTime(resultQuery[1][k])
					.speed50km(Integer.parseInt(resultQuery[2][k]))
					.speed70km(Integer.parseInt(resultQuery[3][k]))
					.speed90km(Integer.parseInt(resultQuery[4][k]))
					.speed120km(Integer.parseInt(resultQuery[5][k]))
					.speed150km(Integer.parseInt(resultQuery[6][k]))
					.speed150Bigger(Integer.parseInt(resultQuery[7][k]))
					.total(Integer.parseInt(resultQuery[8][k])));    				    				 
		} 	 
	}

	 /////// COMMON REPORTS

	 /////// CCR REPORTS

	/* CCR CLASSES -- TYPE = 9 */

	public void classesCCRBuilder() {

		for(int k = 0; k < getNumRegisters(); k++) {         			

			resultList.add(new SatReports.Builder().date(resultQuery[0][k])
					.dateTime(resultQuery[1][k])
					.class1(Integer.parseInt(resultQuery[2][k]))
					.class2(Integer.parseInt(resultQuery[3][k]))   
					.class3(Integer.parseInt(resultQuery[4][k]))
					.class4(Integer.parseInt(resultQuery[5][k]))
					.class5(Integer.parseInt(resultQuery[6][k]))			                  				               
					.total(Integer.parseInt(resultQuery[7][k])));    				    				 
		} 
	}

	/* CCR TYPE -- TYPE = 10 */

	public void tiposCCRBuilder() {

		for(int k = 0; k < getNumRegisters(); k++) {         			

			resultList.add(new SatReports.Builder().date(resultQuery[0][k])
					.dateTime(resultQuery[1][k])
					.class1(Integer.parseInt(resultQuery[2][k]))
					.class2(Integer.parseInt(resultQuery[3][k]))    				                  				               
					.total(Integer.parseInt(resultQuery[4][k])));    				    				 
		} 

	}

	/* CCR SPEED -- TYPE = 11 */

	public void speedCCRBuilder() {

		for(int k = 0; k < getNumRegisters(); k++) {         			

			resultList.add(new SatReports.Builder().date(resultQuery[0][k])
					.dateTime(resultQuery[1][k])
					.speed50km(Integer.parseInt(resultQuery[2][k]))
					.speed70km(Integer.parseInt(resultQuery[3][k]))
					.speed90km(Integer.parseInt(resultQuery[4][k]))
					.speed120km(Integer.parseInt(resultQuery[5][k]))
					.speed150km(Integer.parseInt(resultQuery[6][k]))
					.speed150Bigger(Integer.parseInt(resultQuery[7][k]))
					.total(Integer.parseInt(resultQuery[8][k])));    				    				 
		} 	 
	}

	/* CCR ALL CLASSES -- TYPE = 12 */

	public void ccrAllClassesBuilder() {

		for(int k = 0; k < getNumRegisters(); k++) {       			

			resultList.add(new SatReports.Builder().date(resultQuery[0][k])
					.dateTime(resultQuery[1][k])
					.class2(Integer.parseInt(resultQuery[2][k]))    
					.class3(Integer.parseInt(resultQuery[3][k]))
					.class4(Integer.parseInt(resultQuery[4][k]))
					.class5(Integer.parseInt(resultQuery[5][k]))
					.class6(Integer.parseInt(resultQuery[6][k]))
					.class7(Integer.parseInt(resultQuery[7][k]))
					.class8(Integer.parseInt(resultQuery[8][k]))
					.class9(Integer.parseInt(resultQuery[9][k]))
					.class10(Integer.parseInt(resultQuery[10][k]))
					.class11(Integer.parseInt(resultQuery[11][k]))
					.class12(Integer.parseInt(resultQuery[12][k]))
					.class13(Integer.parseInt(resultQuery[13][k]))
					.class14(Integer.parseInt(resultQuery[14][k]))
					.class15(Integer.parseInt(resultQuery[15][k]))
					.class16(Integer.parseInt(resultQuery[16][k]))
					.class17(Integer.parseInt(resultQuery[17][k]))
					.class18(Integer.parseInt(resultQuery[18][k]))		                  				               
					.total(Integer.parseInt(resultQuery[19][k])));    				    				 

		}

	}

    /////// CCR REPORTS

	///////////////////////////////////
	//CONSTRUCTORS FOR RESULTLIST
	/////////////////////////////////  
	
	/**********************************************************************************************************/
	/**********************************************************************************************************/

}
