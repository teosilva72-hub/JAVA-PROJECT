package br.com.tracevia.webapp.controller.meteo.mto;

import java.io.IOException;
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
import br.com.tracevia.webapp.dao.meteo.MeteoQueriesModels;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.ExcelModels;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.ColumnModel;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.meteo.mto.MtoReports;
import br.com.tracevia.webapp.model.meteo.mto.MtoReports.Builder;
import br.com.tracevia.webapp.model.meteo.mto.MTO;
import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.QueriesReportsModels;

@ManagedBean(name="mtoReportsBean")
@RequestScoped
public class MtoReportsController {
	
	private MtoReports mtoReport;
	private List<SelectItem> equipments;
	private List<SelectItem> months;
	private List<SelectItem> years;
	private List<SelectItem> periods;
		
	private List<Builder> resultList;	
	private List<ColumnModel> columns;
	
	List<? extends Equipments> listMto;
	
	private final String dateFormat = "dd/MM/yyyy";
	private final String datetimeFormat = "dd/MM/yyyy HH:mm";
	private final String monthFormat = "dd";
	private final String yearFormat = "MMM";
	
	String jsTableId;
		
	LocaleUtil localeLabel, localeCalendar;
			
	int periodRange, daysInMonth, daysCount, start_month, end_month;  
		
	String procedure, query, queryCount, module, fileName, currentDate, direction1, direction2, table; 
	
	// Vari�vel que recebe o n�mero de registros esperados para uma consula SQL (de acordo com per�odos)
     private static int numRegisters;	

	// Vari�vel que recebe o n�mero de campos de uma consulta SQL
	private static int fieldsNumber;	
	
	private boolean clearBool, excelBool, chartBool;
		
	//Dates
	String start, end, mtoType;
		
     String[] field, jsonFields, fieldObjectValues;
	
 	String[][] resultQuery, jsonArray; 
	
 	private String jsColumn, jsData, chartTitle, imageName;
 	
 	Gson gson;
	
	ExcelModels model;

	public MtoReports getMtoReport() {
		return mtoReport;
	}

	public void setMtoReport(MtoReports mtoReport) {
		this.mtoReport = mtoReport;
	}
	
	public List<SelectItem> getEquipments() {
		return equipments;
	}

	public List<SelectItem> getPeriods() {
		return periods;
	}	
		
	public List<Builder> getResultList() {
		return resultList;
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

	public static int getNumRegisters() {
		return numRegisters;
	}

	public static void setNumRegisters(int numRegisters) {
		MtoReportsController.numRegisters = numRegisters;
	}

	public static int getFieldsNumber() {
		return fieldsNumber;
	}

	public static void setFieldsNumber(int fieldsNumber) {
		MtoReportsController.fieldsNumber = fieldsNumber;
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

	public String[][] getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(String[][] jsonArray) {
		this.jsonArray = jsonArray;
	}

	public String getChartTitle() {
		return chartTitle;
	}

	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}
		
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
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

	@PostConstruct
	public void initialize() {
		
		localeLabel = new LocaleUtil();	
		localeLabel.getResourceBundle(LocaleUtil.LABELS_MTO);
		
		localeCalendar = new LocaleUtil();	
		localeCalendar.getResourceBundle(LocaleUtil.LABELS_CALENDAR);
					
		/* EQUIPMENTS SELECTION */
		mtoReport = new MtoReports();
		equipments = new ArrayList<SelectItem>();		
				
		listMto = new ArrayList<MTO>();  
		
		try {
			
			 EquipmentsDAO dao = new EquipmentsDAO();		 
			 listMto = dao.EquipmentSelectOptions("mto");
						 
		} catch (Exception e1) {			
			e1.printStackTrace();
		}
				
		for (Equipments e : listMto) {
			SelectItem s = new SelectItem();
			s.setValue(e.getEquip_id());
			s.setLabel(e.getNome());
			equipments.add(s);		
		}
				
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
		
		/* PERIODS FLOW */
		
		periods = new ArrayList<SelectItem>();			
		periods.add(new SelectItem("05 minutes", localeLabel.getStringKey("mto_reports_select_periods_five_minutes")));
		periods.add(new SelectItem("06 minutes", localeLabel.getStringKey("mto_reports_select_periods_six_minutes")));
		periods.add(new SelectItem("10 minutes", localeLabel.getStringKey("mto_reports_select_periods_teen_minutes")));
		periods.add(new SelectItem("15 minutes", localeLabel.getStringKey("mto_reports_select_periods_fifteen_minutes")));
		periods.add(new SelectItem("30 minutes", localeLabel.getStringKey("mto_reports_select_periods_thirty_minutes")));  
		periods.add(new SelectItem("01 hour", localeLabel.getStringKey("mto_reports_select_periods_one_hour")));
		periods.add(new SelectItem("06 hours", localeLabel.getStringKey("mto_reports_select_periods_six_hours")));
		periods.add(new SelectItem("24 hours", localeLabel.getStringKey("mto_reports_select_periods_twenty_four_hours")));

		module = "mto";
		
		table = "mto_data";
		
		//Disabled
		clearBool = true;
		excelBool = true;
		chartBool = true;
		
		jsColumn = "";
		jsData = "";	
				
	}
///////////////////////////////////
//CREATE REPORTS
/////////////////////////////////  

/**********************************************************************************************************/

//////DESENHAR TABLES 	

/**
* M�todo para criar os headers
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

////CRIAR TABLE HEADERS

// CRIAR CAMPOS PARA OS HEADERS
// CRIAR CAMPOS PARA TABELA FRONT-END
// FIELDS -> HEADERS
// FIELDSOBJECTVALUES -> VALUES		

public void CreateFields(String type) {
	   
	   FacesContext facesContext = FacesContext.getCurrentInstance();
	   
	   if(type.equals("1")) {
			  
		     // Table fields
			field = new String[] {localeLabel.getStringKey("mto_reports_year_month"), localeLabel.getStringKey("mto_reports_general_atmPressure")+" (hPa)",
					localeLabel.getStringKey("mto_reports_general_relative_humidity")+" (%)", localeLabel.getStringKey("mto_reports_general_temperature")+" (°C)",
				    localeLabel.getStringKey("mto_reports_general_wind_speed")+" (km/h)", localeLabel.getStringKey("mto_reports_general_wind_direction")+" (°)",
					localeLabel.getStringKey("mto_reports_general_absolute_preciptation")+" (l/m²)", localeLabel.getStringKey("mto_reports_general_visibility")+" (m)",
					localeLabel.getStringKey("mto_reports_general_road_temperature")+" (°C)"};
					
			// Table Objects
			fieldObjectValues = new String[] { "month", "atmPressure", "relative_humidity", "temperature", "wind_direction", "wind_speed",
					"absolute_precipitation", "visibility", "road_temperature"};
			
			 //JSON FIELDS
			  jsonFields = new String[] {localeLabel.getStringKey("mto_reports_chart_haxis"), localeLabel.getStringKey("mto_reports_general_atmPressure")+" (hPa)",
	   					localeLabel.getStringKey("mto_reports_general_relative_humidity")+" (%)", localeLabel.getStringKey("mto_reports_general_temperature")+" (°C)",
	   				    localeLabel.getStringKey("mto_reports_general_wind_speed")+" (km/h)", localeLabel.getStringKey("mto_reports_general_wind_direction")+" (°)",
	   					localeLabel.getStringKey("mto_reports_general_absolute_preciptation")+" (l/m²)", localeLabel.getStringKey("mto_reports_general_visibility")+" (m)",
	   					localeLabel.getStringKey("mto_reports_general_road_temperature")+" (°C)"};
			
			
			//JSON chart title and subtitle
 			chartTitle = localeLabel.getStringKey("mto_reports_chart_title_year");
 			imageName = localeLabel.getStringKey("mto_reports_chart_file_name_year");	
								
		  }
		  
		  if(type.equals("2")) {
			   
			  // Table fields
			   field = new String[] {localeLabel.getStringKey("mto_reports_general_day_month"), localeLabel.getStringKey("mto_reports_general_atmPressure")+" (hPa)",
						localeLabel.getStringKey("mto_reports_general_relative_humidity")+" (%)", localeLabel.getStringKey("mto_reports_general_temperature")+" (°C)",
					    localeLabel.getStringKey("mto_reports_general_wind_speed")+" (km/h)", localeLabel.getStringKey("mto_reports_general_wind_direction")+" (°)",
						localeLabel.getStringKey("mto_reports_general_absolute_preciptation")+" (l/m²)", localeLabel.getStringKey("mto_reports_general_visibility")+" (m)",
						localeLabel.getStringKey("mto_reports_general_road_temperature")+" (°C)"};
				
			   // Table Objects
			  fieldObjectValues = new String[] { "dayOfTheMonth", "atmPressure", "relative_humidity", "temperature", "wind_direction", "wind_speed",
					  "absolute_precipitation", "visibility", "road_temperature" };
			  
 			  //JSON chart fields
 			 jsonFields = new String[] {localeLabel.getStringKey("mto_reports_chart_haxis"), localeLabel.getStringKey("mto_reports_general_atmPressure")+" (hPa)",
						localeLabel.getStringKey("mto_reports_general_relative_humidity")+" (%)", localeLabel.getStringKey("mto_reports_general_temperature")+" (°C)",
					    localeLabel.getStringKey("mto_reports_general_wind_speed")+" (km/h)", localeLabel.getStringKey("mto_reports_general_wind_direction")+" (°)",
						localeLabel.getStringKey("mto_reports_general_absolute_preciptation")+" (l/m²)", localeLabel.getStringKey("mto_reports_general_visibility")+" (m)",
						localeLabel.getStringKey("mto_reports_general_road_temperature")+" (°C)"};
			  			 							  
 			    //JSON chart title and subtitle
 				chartTitle = localeLabel.getStringKey("mto_reports_chart_title_month");
 				imageName = localeLabel.getStringKey("mto_reports_chart_file_name_month"); 				
 							  					  		
			   }
		   
		   if(type.equals("3")) {
				   
			   // Table fields
			   field = new String[] {localeLabel.getStringKey("mto_reports_general_date"), localeLabel.getStringKey("mto_reports_general_interval"), localeLabel.getStringKey("mto_reports_general_atmPressure")+" (hPa)",
						localeLabel.getStringKey("mto_reports_general_relative_humidity")+" (%)", localeLabel.getStringKey("mto_reports_general_temperature")+" (°C)",
						localeLabel.getStringKey("mto_reports_general_wind_speed")+" (km/h)", 	localeLabel.getStringKey("mto_reports_general_wind_direction")+" (°)",
						localeLabel.getStringKey("mto_reports_general_absolute_preciptation")+" (l/m²)", localeLabel.getStringKey("mto_reports_general_visibility")+" (m)",
						localeLabel.getStringKey("mto_reports_general_road_temperature")+" (°C)"};
				
			   // Table Objects				
				fieldObjectValues = new String[] { "date", "dateTime", "atmPressure", "relative_humidity", "temperature", "wind_direction", "wind_speed",
						"absolute_precipitation", "visibility", "road_temperature" }; 
				
				//JSON chart fields
				   jsonFields = new String[] {localeLabel.getStringKey("mto_reports_chart_haxis"), localeLabel.getStringKey("mto_reports_general_atmPressure")+" (hPa)",
							localeLabel.getStringKey("mto_reports_general_relative_humidity")+" (%)", localeLabel.getStringKey("mto_reports_general_temperature")+" (°C)",
							localeLabel.getStringKey("mto_reports_general_wind_speed")+" (km/h)", 	localeLabel.getStringKey("mto_reports_general_wind_direction")+" (°)",
							localeLabel.getStringKey("mto_reports_general_absolute_preciptation")+" (l/m²)", localeLabel.getStringKey("mto_reports_general_visibility")+" (m)",
							localeLabel.getStringKey("mto_reports_general_road_temperature")+" (°C)"};
				
				 //JSON chart title and subtitle
				chartTitle = localeLabel.getStringKey("mto_reports_chart_title_period"); 				
				imageName = localeLabel.getStringKey("mto_reports_chart_file_name_period");
															
		      }			 
	       
	        //Finally Draw Table
	        drawTable(field, fieldObjectValues);	
	        
	      //GUARDAR VALORES NA SESSION
			facesContext.getExternalContext().getSessionMap().put("fieldsLength", field.length); //Length of Fields
			facesContext.getExternalContext().getSessionMap().put("fields", field);	//Fields
			facesContext.getExternalContext().getSessionMap().put("fieldsObject", fieldObjectValues); //Objects
 
   }

	public void GetReports(String type) throws Exception{
		
		    //RESET ON RESTART
		    resetFormValues(type);
		
		    FacesContext facesContext = FacesContext.getCurrentInstance();
	        ExternalContext externalContext = facesContext.getExternalContext();
	    
		    QueriesReportsModels models = new QueriesReportsModels();
		    MeteoQueriesModels mtoModels = new MeteoQueriesModels();	    
		    DateTimeApplication dta = new DateTimeApplication();
		    EquipmentsDAO equipDao = new EquipmentsDAO();	
			    
			GlobalReportsDAO dao = new GlobalReportsDAO();	
								
			String startDate = null, endDate = null, data_anterior = null, mes_anterior = null, mes_inicial = null, month_start_date = null;
			
		    end_month = 0;
		    start_month = 0;
			
			Map<String, String> parameterMap = (Map<String, String>) externalContext.getRequestParameterMap();
			
			//Single Selection
			mtoReport.setEquipment(parameterMap.get("equip"));
			
			mtoReport.setStartDate(parameterMap.get("dateStart"));
			
			mtoReport.setEndDate(parameterMap.get("dateEnd"));
			
			mtoReport.setStartMonth(parameterMap.get("start_month"));
						
			mtoReport.setEndMonth(parameterMap.get("end_month"));
								
			mtoReport.setYear(parameterMap.get("year"));
			
			mtoReport.setMonth(parameterMap.get("month"));
			
			mtoReport.setPeriod(parameterMap.get("periods"));
			
			jsTableId = parameterMap.get("jsTable");
													
			 //Initialize ResultList
		     resultList = new ArrayList<Builder>();	
		 		    	 
		    	 if(type.equals("1")) {
		    		    			    	
		    	// Quantos dias possui o respectivo m�s
				YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(mtoReport.getYear()), Integer.parseInt(mtoReport.getEndMonth()));
				int daysInEndMonth = yearMonthObject.lengthOfMonth();
											
				mtoReport.setStartDate("01/"+mtoReport.getStartMonth()+"/"+mtoReport.getYear());
				mtoReport.setEndDate(daysInEndMonth+"/"+mtoReport.getEndMonth()+"/"+mtoReport.getYear());
				
				end_month = Integer.parseInt(mtoReport.getEndMonth());
				start_month = Integer.parseInt(mtoReport.getStartMonth());
				
				setNumRegisters((end_month - start_month) + 1);
				
				periodRange = daysInEndMonth;
								 
		    	 }
		    	 
		    	 else if(type.equals("2")) {
		  		    	 
				// Quantos dias possui o respectivo m�s
				YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(mtoReport.getYear()), Integer.parseInt(mtoReport.getMonth()));
				daysInMonth = yearMonthObject.lengthOfMonth();
				
				mtoReport.setStartDate("01/"+mtoReport.getMonth()+"/"+mtoReport.getYear());
				mtoReport.setEndDate(daysInMonth+"/"+mtoReport.getMonth()+"/"+mtoReport.getYear());
				
				setNumRegisters(daysInMonth);				
				    	
		    	 }
		    	 
		    	 else if(type.equals("3")) {
		    		 
		    		 mtoReport.setStartDate(parameterMap.get("dateStart"));
		    		 mtoReport.setEndDate(parameterMap.get("dateEnd"));
		    		 
		    		 daysCount = ((int) dta.diferencaDias(mtoReport.getStartDate(), mtoReport.getEndDate()) + 1);
		    		 
		    		//Registros de acordo com sele��o - importante
		 			setNumRegisters(dta.RegistersNumbers(mtoReport.getStartDate(), mtoReport.getEndDate(), mtoReport.getPeriod()));
		 		 	 			
		 			periodRange = dta.periodsRange(mtoReport.getPeriod());
		 		    	    	    	   
		    	   }
		    	 				      
		  	   //N�MERO DE CAMPOS PARA A SA�DA DE DADOS
			   //LEVA EM CONSIDERA��O N�MERO DE CAMPOS DA QUERY					    	 
		    		      
		     setFieldsNumber(fieldsNumber(type));
		    		   				    
			//Chamar Procedure de acordo com per�odo selecionado
			//procedure = models.SelectProcedureByPeriod(mtoReport.getPeriod());	
		    	 
    		startDate = dta.StringDBDateFormat(mtoReport.getStartDate());
			endDate = dta.StringDBDateFormat(mtoReport.getEndDate());
			data_anterior = startDate;
			mes_inicial = mtoReport.getStartMonth();	
			month_start_date = "01";
			
			start = dta.DateTimeToStringIni(startDate); 
			end = dta.DateTimeToStringFim(endDate); 			
														
			resultQuery = new String[getNumRegisters()][getFieldsNumber()];
			jsonArray = new String[getNumRegisters()][jsonFields.length];	
			
			//Select specific query by type
			query = SelectQueryType(type, models, mtoModels); 
																			
			System.out.println(query); //debug

			//EXECU��O DA QUERY
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
			
			lin = auxResult.length;
			col = auxResult[0].length;
												
			if(mtoReport.getPeriod().equals("month")) {
				
		       dta.preencherDias(resultQuery, 0, startDate, daysInMonth);
			   dta.preencherJSONDias(jsonArray, 0, startDate, daysInMonth);
			   
			}
			
			else if (mtoReport.getPeriod().equals("year")) {
				dta.preencherDataMes(resultQuery, 0, mtoReport.getStartMonth(), mtoReport.getEndMonth());
				dta.preencherJSONDataMes(jsonArray, 0, mtoReport.getStartMonth(), mtoReport.getEndMonth(), mtoReport.getYear());
				
			}
			
		    else {
		    	
				//DATAS
			     dta.preencherDataPorPeriodo(resultQuery, 0, getNumRegisters(),  periodRange, startDate); 
			    
			     //JSON DATA			     			     
			     dta.preencherJSONDataPorPeriodo(jsonArray, 0, getNumRegisters(),  periodRange, startDate);
						
			//PERIODOS
			//NEW
			if(mtoReport.getPeriod().equals("05 minutes"))	{		
				 dta.intervalo05Minutos(resultQuery, 1, getNumRegisters());	
				 dta.intervaloJSON05Minutos(jsonArray, 0, getNumRegisters());	
			}
						
			if(mtoReport.getPeriod().equals("06 minutes"))	{		
			     dta.intervalo06Minutos(resultQuery, 1, getNumRegisters());
			     dta.intervaloJSON06Minutos(jsonArray, 0, getNumRegisters());	
			}
			
			if(mtoReport.getPeriod().equals("10 minutes"))	{	
				dta.intervalo10Minutos(resultQuery, 1, getNumRegisters());
				 dta.intervaloJSON10Minutos(jsonArray, 0, getNumRegisters());	
			}
			   			
			if(mtoReport.getPeriod().equals("15 minutes"))	{	
			    dta.intervalo15Minutos(resultQuery, 1, getNumRegisters());
			    dta.intervaloJSON15Minutos(jsonArray, 0, getNumRegisters());			    
			}
			
			if(mtoReport.getPeriod().equals("30 minutes"))	{	
				dta.intervalo30Min(resultQuery, 1, getNumRegisters());
				dta.intervaloJSON30Minutos(jsonArray, 0, getNumRegisters());	
			}
				   		        			
			if(mtoReport.getPeriod().equals("01 hour")) {	
				dta.preencherHora(resultQuery, 1, getNumRegisters());
				dta.intervaloJSON01Hora(jsonArray, 0, getNumRegisters());	
			}
			
			if(mtoReport.getPeriod().equals("06 hours")) {	
			    dta.intervalo06Horas(resultQuery, 1, getNumRegisters());
			    dta.intervaloJSON06Horas(jsonArray, 0, getNumRegisters());	
			}
			
			 if(mtoReport.getPeriod().equals("24 hours"))
			    dta.intervalo24Horas(resultQuery, 1, getNumRegisters());
			 
			}
			 													
			for(int j = 0; j < lin; j++) {
			   for(int i = 0; i < col; i++) {
				 
		    // CASO N�O EXISTA VALOR >>>>>>> PASSA	   
		    if(auxResult[j][0] != null)	 { 
						
			if(mtoReport.getPeriod().equals("01 hour") || mtoReport.getPeriod().equals("06 hours"))
				   hr = Integer.parseInt(auxResult[j][1].substring(0, 2));
				
			else if(!mtoReport.getPeriod().equals("24 hours") && !mtoReport.getPeriod().equals("01 hour") && !mtoReport.getPeriod().equals("06 hours")
					&& !mtoReport.getPeriod().equals("year") && !mtoReport.getPeriod().equals("month") ) {
				    hr = Integer.parseInt(auxResult[j][1].substring(0, 2));
				    minuto =  Integer.parseInt(auxResult[j][1].substring(3, 5));	
				    
				}
						
			  if(!mtoReport.getPeriod().equals("year") &&  !mtoReport.getPeriod().equals("month")) {

				// Restri��o caso n�o haja dados nos primeiros registros
				if ((startDate != null) && (!auxResult[j][0].equals(startDate))) {   // Executa uma unica vez
					
					if(mtoReport.getPeriod().equals("24 hours"))
						iterator = (int) dta.daysDifference(startDate, auxResult[j][0]);

					else iterator = dta.daysDifference(startDate, auxResult[j][0], periodRange);	
					
					pos+= iterator;
					startDate = null;

				} else if (!auxResult[j][0].equals(data_anterior)) {								
												
					if(mtoReport.getPeriod().equals("24 hours"))
						iterator = (int) dta.daysDifference(data_anterior, auxResult[j][0]);
					   
					else iterator = dta.daysDifference(data_anterior, auxResult[j][0], periodRange);	
					
					pos+= iterator;							
				} 	
							
				data_anterior = auxResult[j][0];
				
				 if(mtoReport.getPeriod().equals("05 minutes"))	{
					 p = dta.index05Minutes(hr, minuto);
					 p = p + pos;
				 }
				 else if(mtoReport.getPeriod().equals("06 minutes")) {	
						 p = dta.index06Minutes(hr, minuto);
						 p = p + pos;
				 }
				 else if(mtoReport.getPeriod().equals("10 minutes")) {
				    	 p = dta.index10Minutes(hr, minuto);
				    	 p = p + pos;
				 }
				 else if(mtoReport.getPeriod().equals("15 minutes")) {	
						 p = dta.index15Minutes(hr, minuto);
				         p = p + pos;
								
				 }
				 else if(mtoReport.getPeriod().equals("30 minutes")) {	
						 p = dta.index30Minutes(hr, minuto);
						 p = p + pos;
				 }
				 else if(mtoReport.getPeriod().equals("01 hour"))				
					p = pos + hr;
							
				else if(mtoReport.getPeriod().equals("06 hours")) {
					
					p = dta.index06Hours(hr);				
					p = pos + p;
					
				}
				
				else if(mtoReport.getPeriod().equals("24 hours"))
					     p = pos;
				 
					if(i > 1 ) {
					    resultQuery[p][i] = auxResult[j][i];
					    jsonArray[p][i-1] = auxResult[j][i];
					}
				 
			  }
			  
			  //////////////////////////////////
			  /////////////// YEAR REPORT 
			  ////////////////////////////////
			  
			  else if(mtoReport.getPeriod().equals("year")) {	
				  				  				  
				  if((mes_inicial != null) && Integer.parseInt(mes_inicial) != Integer.parseInt(auxResult[j][0])) {
						
						p = Integer.parseInt(auxResult[j][0]) - Integer.parseInt(mes_inicial);
						mes_inicial = null;
						
					}else if((mes_inicial != null) && Integer.parseInt(mes_inicial) == Integer.parseInt(auxResult[j][0])) {
						 p = 0;
						 mes_inicial = null;
					
					}      
			      else if(Integer.parseInt(mes_anterior) != Integer.parseInt(auxResult[j][0])) {							
						 p++;							
					}
											
				     mes_anterior = auxResult[j][0];
				     
				     if(i > 0) { 
						 resultQuery[p][i] = auxResult[j][i];	
						 jsonArray[p][i] = auxResult[j][i];
				      
				       }
				     					   		
					}
			  
			  //////////////////////////////////
			  /////////////// MONTH REPORT 
			  ////////////////////////////////			  		  
			
			  else if(mtoReport.getPeriod().equals("month")){
				  				 
				    if((month_start_date != null) && Integer.parseInt(month_start_date) != Integer.parseInt(auxResult[j][0])) {
				    						
						p = Integer.parseInt(auxResult[j][0]) - 1;
						month_start_date = null;
												  
						
					 }else if((month_start_date != null) && Integer.parseInt(month_start_date) == Integer.parseInt(auxResult[j][0])) {
						 p = 0;
						 month_start_date = null;					
				    
					 }else if(!auxResult[j][0].equals(data_anterior)) {							
						 p =  Integer.parseInt(auxResult[j][0]) - 1;				
					 }
															
				     data_anterior = auxResult[j][0];
				   				     			     			     
				     if(i > 0) { 
						 resultQuery[p][i] = auxResult[j][i];	
				         jsonArray[p][i] = auxResult[j][i];
				     }
				  				  
			  }
			
			  //////////////////////////////////
			  /////////////// MONTH REPORT 
			  ////////////////////////////////			    
			  
				   } // CASO N�O EXISTA VALOR >>>>>>> PASSA	 
			     }
			   }	
						
				//SA�DA PARA A TABELA
				OutputResult(type);
				
				//SA�DA DO EXCEL
			    ExcelOutPut(type, model);

				//BOT�O DE LIMPAR 
				setClearBool(false);

				//LINK DE DOWNLOAD DO EXCEL
				setExcelBool(false);
				
				//LINK PARA ACESSAR O GRÁFICO
				setChartBool(false);
				
				JSONData(isChartBool(), mtoReport.getPeriod(), mtoReport.getEquipment());										

				//UPDATE RESET BUTTON
				RequestContext.getCurrentInstance().update("form-btns:#btn-tab-reset");

				//UPDATE BUTTON GENERATE EXCEL
				RequestContext.getCurrentInstance().update("form-excel:#excel-act");	
				
				//UPDATE TABLE JQUERY ON RELOAD PAGE
				RequestContext.getCurrentInstance().execute("drawTable('#"+jsTableId+"');");	
								
			//CASO CONTRARIO ENTRA AQUI
				
		} else {					      
				      			 		          
		          //EXECUTE JS
				  RequestContext.getCurrentInstance().execute("hideMessage();");
				  
				  //UPDATE TABLE JQUERY ON RELOAD PAGE
			      RequestContext.getCurrentInstance().execute("drawTable('#"+jsTableId+"'); showMessage(); ");	
							  
	           }	
								   
	      }
	
	
	public void JSONData(boolean chartBool, String period, String equipment) {
		
		TranslationMethods trm = new TranslationMethods();
				
	    String vAxisTitle = localeLabel.getStringKey("mto_reports_chart_vAxis"); // VERTICAL AXIS LABEL
		
		LocalDateTime local =  LocalDateTime.now(); // CURRENT DATE TIME FOR IMAGE
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm");  // DATETIME FORMATTER FOR FILE
	    String formatDateTime = local.format(format); 
	    
	    String equipName = ""; // EQUIP NAME VARIABLE
	    
	    //LIST OF EQUIPMENTS TO GET CURRENT NAME
	    for (Equipments eq : listMto) {
	    	
	    	if(Integer.parseInt(equipment) == eq.getEquip_id())
	    		equipName = eq.getNome();	    	
	    }
	    									    
	    if(!period.equals("month") && !period.equals("year"))
		   chartTitle+= " - " + trm.periodName(period) + " ("+equipName+")"; // NAME OF FILE WITH TIME FORMATTED
		
	    else chartTitle+= " " + "("+equipName+")";
	    
	    imageName += formatDateTime; // NAME OF FILE WITH TIME
		
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
	       	        	     
	        //System.out.println("Header = " + jsColumn);
	        // System.out.println("Data = " + jsData);
	       	
	       if(period.equals("month"))
	    	   RequestContext.getCurrentInstance().execute("reDrawChart("+jsColumn+", "+jsData+", '"+chartTitle+"', '"+vAxisTitle+"', '"+ monthFormat +"', '"+imageName+"' );");
	        	 
	       else if(period.equals("year"))
	    	   RequestContext.getCurrentInstance().execute("reDrawChart("+jsColumn+", "+jsData+", '"+chartTitle+"', '"+vAxisTitle+"', '"+ yearFormat +"', '"+imageName+"' );");
	       
	       else if(period.equals("24 hours"))
		           RequestContext.getCurrentInstance().execute("reDrawChart("+jsColumn+", "+jsData+", '"+chartTitle+"', '"+vAxisTitle+"', '"+ dateFormat +"', '"+imageName+"');");
		        
		   else RequestContext.getCurrentInstance().execute("reDrawChart("+jsColumn+", "+jsData+", '"+chartTitle+"', '"+vAxisTitle+"', '"+ datetimeFormat +"', '"+imageName+"');");
		         	     	        
		}
     }	

			
			
/**********************************************************************************************************/

///////////////////////////////////
//SAIDA DE DADOS
/////////////////////////////////      

/////// FIELDS NUMBER - SA�DA DE DADO

public Integer fieldsNumber(String type) {

	FacesContext facesContext = FacesContext.getCurrentInstance();
	ExternalContext externalContext = facesContext.getExternalContext();

	int length = (int) externalContext.getSessionMap().get("fieldsLength");

int fields = 0;

/**** CONTAGEM VE�CULOS ****/		
if(type.equals("1")) {    		
	fields = length; 	
}

/**** CONTAGEM VE�CULOS ****/		
if(type.equals("2")) {    
	fields = length;  
}

/**** FLUXO MENSAL  ****/
if(type.equals("3")) {    		
		fields = length;     		 
	}
	
	return fields;    	 
}

 /////// FIELDS NUMBER - SA�DA DE DADO 

/**********************************************************************************************************/
   
   /* *********** */
   /* *** AUX *** */
   /* *********** */

   /**
    * @author Wellington 10/09/2020
    * M�todo para criar uma Query a ser executada  
    * @param models - Objeto do tipo QuerieReportsModels
    * @param mainQuery - Query principal a ser adicionada
    * @return
    */
   public String BuildMainQuery(QueriesReportsModels models, String mainQuery, String index) {    	 

	   String query = null;
	   query = models.BuildQueryIndexType2(models.QueryDateTimeHeader(mtoReport.getPeriod()), mainQuery, models.QueryFromMeteoTable(mtoReport.getPeriod(), table), models.useIndex(index),
				models.innerJoinMto(), models.whereClauseWeatherEquipDate(mtoReport.getEquipment(), start, end), models.QueryWeatherGroupAndOrder(mtoReport.getPeriod()));

	   return query;
   }
  
   /**
    * M�todo que retorna um query espec�fica de acordo com tipo
    * @param type - Tipo de espec�fico do relat�rio
    * @param models - Objeto do tipo QueriesReportsModels
    * @param satModels - Objeto do tipo SatQueriesModels
    * @return
   * @throws Exception 
    */
   public String SelectQueryType(String type, QueriesReportsModels models, MeteoQueriesModels mtoModels) throws Exception {   
  	
	    String query = null;
		
	     switch(type) {
	     
	     case "1": query = BuildMainQuery(models, mtoModels.MtoMainQuery(mtoReport.getEquipment()), QueriesReportsModels.USE_INDEX_IDX_DATETIME_STATION); break;
	     case "2": query = BuildMainQuery(models, mtoModels.MtoMainQuery(mtoReport.getEquipment()), QueriesReportsModels.USE_INDEX_IDX_DATETIME_STATION); break;
	     case "3": query = BuildMainQuery(models, mtoModels.MtoMainQuery(mtoReport.getEquipment()), QueriesReportsModels.USE_INDEX_IDX_DATETIME_STATION); break;
	     case "4": ; break;	   
	     default: query = null; break;
	       	    	     
	     }  	 
	   		  		
		return query;
		
    }

   
  public void OutputResult(String type) {  
	  
	//ACESSAR DADOS DO RELAT�RIOF
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		//FIELDS EXTERNOS ARMAZENADOS NA REQUISI��O
		field = (String[]) externalContext.getSessionMap().get("fields");
		fieldObjectValues =  (String[]) externalContext.getSessionMap().get("fieldsObject");
   
	   if(type.equals("1")) { 
  		   		       			 
  		   yearBuilder();
  		     		  		  		   
  		// DRAW TABLE -- BUILD HEADER
  		drawTable(field, fieldObjectValues);
  		  		
	   }
	   
	   if(type.equals("2")) { 
      			
		   monthBuilder();
		   		  		   
		// DRAW TABLE -- BUILD HEADER
	  	 drawTable(field, fieldObjectValues);
 		   
	   }
	   if(type.equals("3")) {
		   			
		 periodBuilder();
		 				   
		// DRAW TABLE -- BUILD HEADER
	  	 drawTable(field, fieldObjectValues);
	   
	   }
			
    }	      
   
       
   public void ExcelOutPut(String type, ExcelModels model) throws IOException, Exception {
   	  
 	  FacesContext facesContext = FacesContext.getCurrentInstance();		
 	  
 	  DateTimeApplication dta = new DateTimeApplication();
 	  TranslationMethods tm = new TranslationMethods();
 	  
 	  //Initialize Excel Class Object
	  model = new ExcelModels();
 	  
 	  String[] countMergeHeader;
 	
 	  String excel_title = "", fileName = "";
 	  
 	  int colStartDate = 0,  colEndDate = 0;
 	     	     	  
 	  int[] col;
 	 
 	  String equip = "", road = "", km = "", city = "";
 	  
 	  
 	 if(type.equals("1")) {
		  
		  EquipmentsDAO dao = new EquipmentsDAO();
		  Equipments info = new Equipments();
		  
		  info = dao.EquipReportsInfo(mtoReport.getEquipment(), module);    		 
		   		 
		  fileName = localeLabel.getStringKey("excel_report_weather_file")+tm.periodName(mtoReport.getPeriod());
		  excel_title = localeLabel.getStringKey("excel_report_weather_title_year"); 
		  
		  countMergeHeader = new String[] {"A1:B4", "C1:H4", "I1:J4"};
		  		     		  
		   col = new int[] {3500, 4500, 4500, 4500, 4500, 4500, 4500, 4500, 4500, 4500}; 		   
		   colStartDate = 8; colEndDate = 9;
		 		  
		  equip = info.getNome(); road = info.getEstrada();  km = info.getKm(); city = info.getCidade();
		      		     		 	
		  model.StandardFonts();
		  model.StandardStyles();
		  model.StandardBorders();
		  		  			      		      		    		    		    		  
		  model.StandardExcelModelWithoutTotalMTO(field, numRegisters, periodRange, daysCount, mtoReport.getPeriod(), dta.currentTime(), type, module,  				  
				  RoadConcessionaire.externalImagePath, excel_title, equip, city, road, km, mtoType, mtoReport.getStartDate(), mtoReport.getEndDate(), countMergeHeader, 
				  col, colStartDate, colEndDate, resultQuery);		  
	     
 	     }
 	   	      	     	   	  
 	  if(type.equals("2")) {
 		  
 		  EquipmentsDAO dao = new EquipmentsDAO();
		  Equipments info = new Equipments();
		  
		  info = dao.EquipReportsInfo(mtoReport.getEquipment(), module);    		 
 		   		 
 		  fileName = localeLabel.getStringKey("excel_report_weather_file")+tm.periodName(mtoReport.getPeriod());
 		  excel_title = localeLabel.getStringKey("excel_report_weather_title_month");
 		  
 		  countMergeHeader = new String[] {"A1:B4", "C1:H4", "I1:J4"};
 		  		     		  
 		   col = new int[] {3500, 4500, 4500, 4500, 4500, 4500, 4500, 4500, 4500, 4500}; 		   
 		   colStartDate = 8; colEndDate = 9;
 		 		  
 		  equip = info.getNome(); road = info.getEstrada();  km = info.getKm(); city = info.getCidade();
 	 	
 		  model.StandardFonts();
 		  model.StandardStyles();
 		  model.StandardBorders();
 		      		      		    		    		    		  
 		  model.StandardExcelModelWithoutTotalMTO(field, numRegisters, periodRange, daysCount, mtoReport.getPeriod(), dta.currentTime(), type, module,  				  
 				  RoadConcessionaire.externalImagePath, excel_title, equip, city, road, km, mtoType, mtoReport.getStartDate(), mtoReport.getEndDate(), countMergeHeader, 
 				  col, colStartDate, colEndDate, resultQuery);
 		  
 	    }
 	  
 	 if(type.equals("3")) {
		  
		  EquipmentsDAO dao = new EquipmentsDAO();
		  Equipments info = new Equipments();
		  
		  info = dao.EquipReportsInfo(mtoReport.getEquipment(), module);    		 
		   		 
		  fileName = localeLabel.getStringKey("excel_report_weather_file")+tm.periodName(mtoReport.getPeriod());
		  excel_title = localeLabel.getStringKey("excel_report_weather_title_periods");
		  
		  countMergeHeader = new String[] {"A1:B4", "C1:I4", "J1:L4"};
		  		     		  
		   col = new int[] {3500, 4500, 4500, 4500, 4500, 4500, 4500, 4500, 4500, 4500, 4500};	   
		   colStartDate = 9; colEndDate = 11;
		 		  
		  equip = info.getNome(); road = info.getEstrada();  km = info.getKm(); city = info.getCidade();
		 	
		  model.StandardFonts();
		  model.StandardStyles();
		  model.StandardBorders();
		      		      		    		    		    		  
		  model.StandardExcelModelWithoutTotalMTO(field, numRegisters, periodRange, daysCount, mtoReport.getPeriod(), dta.currentTime(), type, module,  				  
				  RoadConcessionaire.externalImagePath, excel_title, equip, city, road, km, mtoType, mtoReport.getStartDate(), mtoReport.getEndDate(), countMergeHeader, 
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
        
    //Form Reset
  	public void resetFormValues(String type) {
  		
  		FacesContext facesContext = FacesContext.getCurrentInstance();	
  		ExternalContext externalContext = facesContext.getExternalContext();

  		//Reset object => call on click reset button
  		mtoReport = new MtoReports();

  		//System.out.println("reset");
  		
  		externalContext.getSessionMap().remove("xlsModel");
  		externalContext.getSessionMap().remove("current");
  		externalContext.getSessionMap().remove("fileName");		
  		externalContext.getSessionMap().remove("fields");
  		externalContext.getSessionMap().remove("fieldsObject");

  		// Fields again
  		CreateFields(type);

  	}  	
  	   
  		 //CONSTRUCTORS
  		public void yearBuilder() {
  			  	  		
  		 for(int k = 0; k < getNumRegisters(); k++) {      
					 
 		      resultList.add(new MtoReports.Builder().month(resultQuery[k][0]) 
                .atmPressure(resultQuery[k][1] == null? 0 : Double.parseDouble(resultQuery[k][1]))  
                .relative_humidity(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
                .temperature(resultQuery[k][3] == null? 0.0 : Double.parseDouble(resultQuery[k][3]))
                .windDir(resultQuery[k][4] == null? 0 : Double.parseDouble(resultQuery[k][4]))
                .windSpeed(resultQuery[k][5] == null? 0 : Double.parseDouble(resultQuery[k][5]))
                .absolutePreciptation(resultQuery[k][6] == null? 0 : Double.parseDouble(resultQuery[k][6]))               
                .visibility(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
                .roadTemperature(resultQuery[k][8] == null? 0.0 : Double.parseDouble(resultQuery[k][8])));
 		       		       		    	    			    				 
 		 }   		  		 
  		
  	}
  		
  		public void monthBuilder() {
  			  		  			
  			 for(int k = 0; k < getNumRegisters(); k++) {      
					 
  	 		      resultList.add(new MtoReports.Builder().dayOfMonth(resultQuery[k][0] == null? 0 : Integer.parseInt(resultQuery[k][0])) 
                    .atmPressure(resultQuery[k][1] == null? 0 : Double.parseDouble(resultQuery[k][1]))  
                    .relative_humidity(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
                    .temperature(resultQuery[k][3] == null? 0.0 : Double.parseDouble(resultQuery[k][3]))
	                .windDir(resultQuery[k][4] == null? 0 : Double.parseDouble(resultQuery[k][4]))
                    .windSpeed(resultQuery[k][5] == null? 0 : Double.parseDouble(resultQuery[k][5]))
                    .absolutePreciptation(resultQuery[k][6] == null? 0 : Double.parseDouble(resultQuery[k][6]))                      
                    .visibility(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
                    .roadTemperature(resultQuery[k][8] == null? 0.0 : Double.parseDouble(resultQuery[k][8])));
  	 		    		    			    				 
  	 		 }  			  			
  		}
  		
  	public void periodBuilder() {
  			  	       				  	    			
  		  for(int k = 0; k < getNumRegisters(); k++) { 
  							 
 		      resultList.add(new MtoReports.Builder().date(resultQuery[k][0]) 
 		    	.dateTime(resultQuery[k][1]) 
                .atmPressure(resultQuery[k][2] == null? 0 : Double.parseDouble(resultQuery[k][2]))  
                .relative_humidity(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
                .temperature(resultQuery[k][4] == null? 0.0 : Double.parseDouble(resultQuery[k][4]))
                .windDir(resultQuery[k][5] == null? 0 : Double.parseDouble(resultQuery[k][5]))
                .windSpeed(resultQuery[k][6] == null? 0 : Double.parseDouble(resultQuery[k][6]))
                .absolutePreciptation(resultQuery[k][7] == null? 0 : Double.parseDouble(resultQuery[k][7]))             
                .visibility(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
                .roadTemperature(resultQuery[k][9] == null? 0.0 : Double.parseDouble(resultQuery[k][9])));
 		    		    			    				 
 		   } 	 		    	
       }	  				  		
  	   
  }
