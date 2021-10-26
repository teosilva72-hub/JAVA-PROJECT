package br.com.tracevia.webapp.model.global;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.google.gson.Gson;

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.methods.ExcelModels;
import br.com.tracevia.webapp.methods.TranslationMethods;

import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.SessionUtil;

public class ReportBuild {

	// Lists
	public List<SelectItem> equipments;  	
	public List<SelectItem> years;	
	public List<SelectItem> periods;  
	public List<SelectItem> classes;  
	public List<SelectItem> axles;  
	public List<SelectItem> vehiclesCCR;  
	public List<SelectItem> vehicles;  
	public List<Columns> columns;  

	// Date Format 
	public final String dateFormat = "dd/MM/yyyy";
	public final String datetimeFormat = "dd/MM/yyyy HH:mm";

	// Registers of an array
	public static int numRegisters;	

	// Fields number of an array
	public static int fieldsNumber;	

	// Interval
	public int breakTime;

	// Interval Index to increment
	public int breakTimeIndex;

	// Days Count Interval
	public int daysCount;

	// For Reports Columns
	public String[] fields;   
	public String[] fieldObjectValues;
	public String[] fieldsAux;
	public String[] fieldObjAux; 

	// JSON FIELDS
	public String[][] jsonArray;
	public String[] jsonFields;  

	// RESULT FOR QUERY
	public String[][] resultQuery;

	// GSON to use JSON Google API
	public Gson gson;

	// JSON Attributes
	public String jsColumn;
	public String jsData;
	public String jsChartTitle;
	public String jsImageName;
	public String fileName;
	public String currentDate;

	// Variáveis para habilitar botões
	public boolean clearBool;
	public boolean excelBool;
	public boolean chartBool;

	// Query
	public String query;

	// JSON for table 
	public String jsTableId;
	public  String jsTableScrollHeight;

	// Locale
	public LocaleUtil localeLabels; 
	public LocaleUtil localeMessages; 
	public LocaleUtil localeLabelSelectItems;
	public LocaleUtil localeLabelCharts;	
	public LocaleUtil localeLabelExcel; 
	public LocaleUtil localeLabelReports; 

	// Equipment DAO
	public EquipmentsDAO equipDAO;

	// Excel Method
	public ExcelModels model;

	// Construtor
	public ReportBuild() {

		// DEFINE LOCALE
		localeLabelSelectItems = LocaleUtil.setLocale(LocaleUtil.LABELS_SELECTION_ITEM);

	}

	// ----------------------------------------------------------------------------------------------------------------------

	/**
	 * Método para desenhar as colunas de uma tabela
	 * @author Wellington
	 * @version 1.0
	 * @since version 1.0
	 * @param field - Array contendo os nomes de cada campo
	 * @return - vazio
	 */

	public void drawTable( List<Columns> columns, String[] field) {

		for(int i = 0; i < field.length; i++)
			columns.add(new Columns(field[i]));	
	}

	// ----------------------------------------------------------------------------------------------------------------------


	/**
	 * Método para realizar o download de um arquivo formato .xls
	 * @author Wellington
	 * @version 1.0 
	 * @since version 1.0
	 * @return - vazio
	 */
	public void download() {

		model = (ExcelModels) SessionUtil.getParam("xlsModel");
		currentDate = (String) SessionUtil.getParam("current");
		fileName = (String) SessionUtil.getParam("fileName");

		String name = fileName+"_"+currentDate;

		try {

			model.download(ExcelModels.workbook, name);

		} catch (IOException e) {			
			e.printStackTrace();
		}

	}   

	// ----------------------------------------------------------------------------------------------------------------------

	/**
	 * Método para realizar o download de um arquivo formato .txt
	 * @author Wellington
	 * @version 1.0
	 * @since version 1.0
	 * @return - vazio
	 */
	public void downloadTextFile() throws Exception {

		TranslationMethods tm = new TranslationMethods();

		//Get Site information
		String siteID = (String) SessionUtil.getParam("selectedEquip");
		String month = (String) String.valueOf(SessionUtil.getParam("selectedMonth"));
		String year = (String) String.valueOf(SessionUtil.getParam("selectedYear"));    

		byte[] bytes = (byte[]) SessionUtil.getParam("bytes");

		EquipmentsDAO dao = new EquipmentsDAO();

		String siteName = dao.EquipmentName("sat", siteID);

		SessionUtil.getExternalContext().setResponseContentType("text/plain");
		SessionUtil.getExternalContext().setResponseHeader("Content-Disposition",
				"attachment; filename=\"VBV_"+siteName+"_"+tm.MonthAbbreviation(month)+"_"+tm.YearAbbreviation(year)+".txt\"");

		OutputStream responseOutputStream = SessionUtil.getExternalContext().getResponseOutputStream();

		responseOutputStream.write(bytes);
		responseOutputStream.flush();
		responseOutputStream.close();

		SessionUtil.getFacesContext().responseComplete(); 	

	}

	// ----------------------------------------------------------------------------------------------------------------------

	/**
	 * Método para limpar valores da sessão do relatório
	 * @author Wellington
	 * @version 1.0
	 * @since version 1.0
	 * @return - vazio
	 */
	public void resetReportValues() {

		SessionUtil.remove("xlsModel");
		SessionUtil.remove("current");
		SessionUtil.remove("fileName");		
		SessionUtil.remove("fields");
		SessionUtil.remove("jsonFields");
		SessionUtil.remove("fieldsObject");

	}				

	// ----------------------------------------------------------------------------------------------------------------------

	/**
	 * Método para limpar valores da sessão dos relatório
	 * @author Wellington
	 * @version 1.0 
	 * @since version 1.0
	 * @return - vazio
	 */
	public void resetReportTextValues() {

		SessionUtil.remove("selectedEquip");
		SessionUtil.remove("selectedMonth");
		SessionUtil.remove("selectedYear");
		SessionUtil.remove("bytes");

	}				

	// ----------------------------------------------------------------------------------------------------------------------


	/**
	 * Método para popular uma lista com informações dos equipamentos
	 * @author Wellington
	 * @version 1.0
	 * @since version 1.0
	 * @param module - modulo para buscar equipamentos disponiveis
	 * @return - Uma lista com id e nome dos equipamentos
	 * @throws Exception
	 */
	public List<SelectItem> selectEquips(List<? extends Equipments> list) throws Exception{

		List<SelectItem> items = new ArrayList<SelectItem>(); 

		for (Equipments e : list) {

			SelectItem s = new SelectItem();

			s.setValue(e.getEquip_id());
			s.setLabel(e.getNome());

			items.add(s);				
		}

		return items;

	}

	// ----------------------------------------------------------------------------------------------------------------------

	/**
	 * Método para popular uma lista com períodos para seleção
	 * @author Wellington
	 * @version 1.0 
	 * @since version 1.0 		 
	 * @return - Uma lista com as opções dos períodos
	 * @throws Exception
	 */
	public List<SelectItem> selectPeriods() throws Exception{

		List<SelectItem> items = new ArrayList<SelectItem>(); 

		items.add(new SelectItem("05 minutes", localeLabelSelectItems.getStringKey("select_item_periods_five_minutes")));
		items.add(new SelectItem("06 minutes", localeLabelSelectItems.getStringKey("select_item_periods_six_minutes")));
		items.add(new SelectItem("10 minutes", localeLabelSelectItems.getStringKey("select_item_periods_teen_minutes")));
		items.add(new SelectItem("15 minutes", localeLabelSelectItems.getStringKey("select_item_periods_fifteen_minutes")));
		items.add(new SelectItem("30 minutes", localeLabelSelectItems.getStringKey("select_item_periods_thirty_minutes")));  
		items.add(new SelectItem("01 hour", localeLabelSelectItems.getStringKey("select_item_periods_one_hour")));
		items.add(new SelectItem("06 hours", localeLabelSelectItems.getStringKey("select_item_periods_six_hours")));
		items.add(new SelectItem("24 hours", localeLabelSelectItems.getStringKey("select_item_periods_twenty_four_hours")));

		return items;
	}

	// ----------------------------------------------------------------------------------------------------------------------   

	/**
	 * Método para popular uma lista com meses para seleção	
	 * @author Wellington
	 * @version 1.0
	 * @since version 1.0
	 * @return - Uma lista com as opções dos meses
	 * @throws Exception
	 */
	public List<SelectItem> selectMonth() throws Exception{

		List<SelectItem> items = new ArrayList<SelectItem>(); 

		items.add(new SelectItem(1, localeLabelSelectItems.getStringKey("select_item_january")));  
		items.add(new SelectItem(2, localeLabelSelectItems.getStringKey("select_item_february")));  
		items.add(new SelectItem(3, localeLabelSelectItems.getStringKey("select_item_march")));  
		items.add(new SelectItem(4, localeLabelSelectItems.getStringKey("select_item_april")));  
		items.add(new SelectItem(5, localeLabelSelectItems.getStringKey("select_item_may")));
		items.add(new SelectItem(6, localeLabelSelectItems.getStringKey("select_item_june")));
		items.add(new SelectItem(7, localeLabelSelectItems.getStringKey("select_item_july")));
		items.add(new SelectItem(8, localeLabelSelectItems.getStringKey("select_item_august")));
		items.add(new SelectItem(9, localeLabelSelectItems.getStringKey("select_item_september")));
		items.add(new SelectItem(10, localeLabelSelectItems.getStringKey("select_item_october")));
		items.add(new SelectItem(11, localeLabelSelectItems.getStringKey("select_item_november")));
		items.add(new SelectItem(12, localeLabelSelectItems.getStringKey("select_item_december")));

		return items;

	}

	// ----------------------------------------------------------------------------------------------------------------------   

	/**
	 * Método para popular uma lista com classes para seleção (padrão)	 
	 * @author Wellington
	 * @version 1.0
	 * @since version 1.0
	 * @return - Uma lista com as opções das classes
	 * @throws Exception
	 */
	public List<SelectItem> selectClasses() throws Exception{

		List<SelectItem> items = new ArrayList<SelectItem>(); 

		items.add(new SelectItem("1", localeLabelSelectItems.getStringKey("select_item_class_light")));
		items.add(new SelectItem("9", localeLabelSelectItems.getStringKey("select_item_class_motorcycle")));
		items.add(new SelectItem("5", localeLabelSelectItems.getStringKey("select_item_class_trailer")));
		items.add(new SelectItem("3", localeLabelSelectItems.getStringKey("select_item_class_semi_trailer")));
		items.add(new SelectItem("2A", localeLabelSelectItems.getStringKey("select_item_class_bus_2_axles")));
		items.add(new SelectItem("4A", localeLabelSelectItems.getStringKey("select_item_class_bus_3_axles")));
		items.add(new SelectItem("2", localeLabelSelectItems.getStringKey("select_item_class_truck_2_axles")));
		items.add(new SelectItem("4", localeLabelSelectItems.getStringKey("select_item_class_truck_3_axles")));
		items.add(new SelectItem("6", localeLabelSelectItems.getStringKey("select_item_class_truck_4_axles")));
		items.add(new SelectItem("7", localeLabelSelectItems.getStringKey("select_item_class_truck_5_axles")));
		items.add(new SelectItem("8", localeLabelSelectItems.getStringKey("select_item_class_truck_6_axles")));
		items.add(new SelectItem("10", localeLabelSelectItems.getStringKey("select_item_class_truck_7_axles")));
		items.add(new SelectItem("11", localeLabelSelectItems.getStringKey("select_item_class_truck_8_axles")));
		items.add(new SelectItem("E9", localeLabelSelectItems.getStringKey("select_item_class_truck_9_axles")));
		items.add(new SelectItem("10N", localeLabelSelectItems.getStringKey("select_item_class_truck_10_axles")));

		return items;

	}

	// ----------------------------------------------------------------------------------------------------------------------   

	/**
	 * Método para popular uma lista com classes para seleção (Opção 2 => usado em CCR LL) 
	 * @author Wellington
	 * @version 1.0
	 * @since version 1.0
	 * @return - Uma lista com as opções das classes
	 * @throws Exception
	 */
	public List<SelectItem> selectClassesOP2() throws Exception{

		List<SelectItem> items = new ArrayList<SelectItem>(); 

		items.add(new SelectItem("1", localeLabelSelectItems.getStringKey("select_item_class_light")));
		items.add(new SelectItem("10", localeLabelSelectItems.getStringKey("select_item_class_motorcycle")));
		items.add(new SelectItem("12", localeLabelSelectItems.getStringKey("select_item_class_trailer")));
		items.add(new SelectItem("11", localeLabelSelectItems.getStringKey("select_item_class_semi_trailer")));				
		items.add(new SelectItem("13", localeLabelSelectItems.getStringKey("select_item_class_bus_2_axles")));
		items.add(new SelectItem("14", localeLabelSelectItems.getStringKey("select_item_class_bus_3_axles")));
		items.add(new SelectItem("15", localeLabelSelectItems.getStringKey("select_item_class_bus_4_axles")));
		items.add(new SelectItem("16", localeLabelSelectItems.getStringKey("select_item_class_bus_5_axles")));
		items.add(new SelectItem("17", localeLabelSelectItems.getStringKey("select_item_class_bus_6_axles")));								
		items.add(new SelectItem("2", localeLabelSelectItems.getStringKey("select_item_class_truck_2_axles")));	
		items.add(new SelectItem("18", localeLabelSelectItems.getStringKey("select_item_class_truck_2_axles_class18")));	
		items.add(new SelectItem("19", localeLabelSelectItems.getStringKey("select_item_class_truck_2_axles_class19")));					
		items.add(new SelectItem("3", localeLabelSelectItems.getStringKey("select_item_class_truck_3_axles")));
		items.add(new SelectItem("4", localeLabelSelectItems.getStringKey("select_item_class_truck_4_axles")));
		items.add(new SelectItem("5", localeLabelSelectItems.getStringKey("select_item_class_truck_5_axles")));
		items.add(new SelectItem("6", localeLabelSelectItems.getStringKey("select_item_class_truck_6_axles")));
		items.add(new SelectItem("7", localeLabelSelectItems.getStringKey("select_item_class_truck_7_axles")));
		items.add(new SelectItem("8", localeLabelSelectItems.getStringKey("select_item_class_truck_8_axles")));
		items.add(new SelectItem("9", localeLabelSelectItems.getStringKey("select_item_class_truck_9_axles")));
		items.add(new SelectItem("20", localeLabelSelectItems.getStringKey("select_item_class_truck_10_axles")));				

		return items;

	}

	// ----------------------------------------------------------------------------------------------------------------------   

	/**
	 * Método para popular uma lista com eixos para seleção 
	 * @author Wellington
	 * @version 1.0
	 * @since version 1.0
	 * @return - Uma lista com as opções dos eixos
	 * @throws Exception
	 */
	public List<SelectItem> selectAxles() throws Exception{

		List<SelectItem> items = new ArrayList<SelectItem>(); 

		items.add(new SelectItem("2", localeLabelSelectItems.getStringKey("select_item_axles_2")));
		items.add(new SelectItem("3", localeLabelSelectItems.getStringKey("select_item_axles_3")));
		items.add(new SelectItem("4", localeLabelSelectItems.getStringKey("select_item_axles_4")));
		items.add(new SelectItem("5", localeLabelSelectItems.getStringKey("select_item_axles_5")));
		items.add(new SelectItem("6", localeLabelSelectItems.getStringKey("select_item_axles_6")));
		items.add(new SelectItem("7", localeLabelSelectItems.getStringKey("select_item_axles_7")));
		items.add(new SelectItem("8", localeLabelSelectItems.getStringKey("select_item_axles_8")));
		items.add(new SelectItem("9", localeLabelSelectItems.getStringKey("select_item_axles_9")));
		items.add(new SelectItem("10", localeLabelSelectItems.getStringKey("select_item_axles_10")));

		return items;

	}   

	// ----------------------------------------------------------------------------------------------------------------------

	/**
	 * Método para popular uma lista com direções para seleção (padrão)	 
	 * @author Wellington
	 * @version 1.0 
	 * @since version 1.0
	 * @return - Uma lista com as opções das direções
	 * @throws Exception
	 */
	public List<SelectItem> selectDirections() throws Exception{

		List<SelectItem> items = new ArrayList<SelectItem>(); 

		items.add(new SelectItem("1", localeLabelSelectItems.getStringKey("select_item_direction_label1")));
		items.add(new SelectItem("2", localeLabelSelectItems.getStringKey("select_item_direction_label2")));

		return items;

	}   

	// ----------------------------------------------------------------------------------------------------------------------

	/**
	 * Método para popular uma lista com veículos para seleção (padrão)	 
	 * @author Wellington
	 * @version 1.0 
	 * @since version 1.0
	 * @return - Uma lista com as opções dos veículos
	 * @throws Exception
	 */
	public List<SelectItem> selectVehicles() throws Exception{

		List<SelectItem> items = new ArrayList<SelectItem>(); 

		items.add(new SelectItem(1, localeLabelSelectItems.getStringKey("select_item_vehicles_light")));   
		items.add(new SelectItem(2, localeLabelSelectItems.getStringKey("select_item_vehicles_motorcycle")));  
		items.add(new SelectItem(3, localeLabelSelectItems.getStringKey("select_item_vehicles_heavy")));  

		return items;

	}   

	// ----------------------------------------------------------------------------------------------------------------------

	/**
	 * Método para popular uma lista com veículos para seleção (Opção 2 => usado em CCR LL)	 
	 * @author Wellington
	 * @version 1.0 
	 * @since version 1.0
	 * @return - Uma lista com as opções dos veículos
	 * @throws Exception
	 */
	public List<SelectItem> selectVehiclesOP2() throws Exception{

		List<SelectItem> items = new ArrayList<SelectItem>(); 

		items.add(new SelectItem(1, localeLabelSelectItems.getStringKey("select_item_vehicles_light")));   
		items.add(new SelectItem(2, localeLabelSelectItems.getStringKey("select_item_vehicles_motorcycle")));  
		items.add(new SelectItem(3, localeLabelSelectItems.getStringKey("select_item_vehicles_small_heavy")));  
		items.add(new SelectItem(4, localeLabelSelectItems.getStringKey("select_item_vehicles_long_heavy")));  
		items.add(new SelectItem(5, localeLabelSelectItems.getStringKey("select_item_vehicles_bus")));  

		return items;

	}  

	// ----------------------------------------------------------------------------------------------------------------------

	/**
	 * Método para popular uma lista com anos para seleção 
	 * @author Wellington
	 * @version 1.0 
	 * @since version 1.0
	 * @return - Uma lista com as opções dos anos
	 * @throws Exception
	 */            
	public List<SelectItem> selectYears() throws Exception{

		List<SelectItem> items = new ArrayList<SelectItem>(); 

		for(int year = 2016; year < 2027; year++)   
			items.add(new SelectItem(year, String.valueOf(year)));  

		return items;

	}

	// ----------------------------------------------------------------------------------------------------------------------

	// GETTERS TO INTERACT WITH FRONT-END

	// ------------ LISTS ----------------

	public List<SelectItem> getEquipments() {
		return equipments;
	}

	public List<SelectItem> getYears() {
		return years;
	}

	public List<SelectItem> getPeriods() {
		return periods;
	}

	public List<SelectItem> getClasses() {
		return classes;
	}

	public List<SelectItem> getAxles() {
		return axles;
	}

	public List<SelectItem> getVehiclesCCR() {
		return vehiclesCCR;
	}

	public List<SelectItem> getVehicles() {
		return vehicles;
	}

	public List<Columns> getColumns() {
		return columns;
	}

	// ------------ SHOW / HIDE ----------------

	public boolean isClearBool() {
		return clearBool;
	}

	public boolean isExcelBool() {
		return excelBool;
	}

	public boolean isChartBool() {
		return chartBool;
	}	


	// ----------------------------------------------------------------------------------------------------------------------	           


}
