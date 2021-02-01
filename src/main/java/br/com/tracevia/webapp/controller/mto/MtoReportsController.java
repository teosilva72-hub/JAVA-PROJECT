package br.com.tracevia.webapp.controller.mto;

import java.io.IOException;
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

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.global.GlobalReportsDAO;
import br.com.tracevia.webapp.dao.mto.MtoQueriesModels;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.ExcelModels;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.ColumnModel;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.mto.MtoReports;
import br.com.tracevia.webapp.model.mto.MtoReports.Builder;
import br.com.tracevia.webapp.model.sat.SAT;
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
		
	LocaleUtil localeLabel, localeCalendar;
			
	int fieldsNumber, numRegisters, periodRange, daysInMonth, daysCount, start_month, end_month;  
		
	String procedure, query, queryCount, module, fileName, currentDate, direction1, direction2; 
	
     String[] field, fieldObjectValues;
	
	String[][] resultQuery;
	
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
	
	
	@PostConstruct
	public void initialize() {
		
		localeLabel = new LocaleUtil();	
		localeLabel.getResourceBundle(LocaleUtil.LABELS_MTO);
		
		localeCalendar = new LocaleUtil();	
		localeCalendar.getResourceBundle(LocaleUtil.LABELS_CALENDAR);
				
		/* EQUIPMENTS SELECTION */
		mtoReport = new MtoReports();
		equipments = new ArrayList<SelectItem>();		
				
		List<? extends Equipments> listSats = new ArrayList<SAT>();  
		
		try {
			
			 EquipmentsDAO dao = new EquipmentsDAO();		 
			 listSats = dao.EquipmentSelectOptions("mto");
						 
		} catch (Exception e1) {			
			e1.printStackTrace();
		}
				
		for (Equipments e : listSats) {
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
				
	}
		
	/**
	 * Método para criar os campos nas tabelas dos relatórios	
	 * @param field - Campos (Headers)
	 * @param objectValue - Valores de cada campos expressos em objetos
	 */
	public void drawTable(String[] field, String[] objectValue) {
				
		columns = new ArrayList<ColumnModel>();
		
		for(int i = 0; i < field.length; i++)
		   columns.add(new ColumnModel(field[i], objectValue[i]));		
		
	}
	
	//GETREPORTS
			
	// REPORTS MODELS
	public void GetReports(String type) throws Exception{
		
		   FacesContext facesContext = FacesContext.getCurrentInstance();
	       ExternalContext externalContext = facesContext.getExternalContext();
	    
		    QueriesReportsModels models = new QueriesReportsModels();
		    MtoQueriesModels mtoModels = new MtoQueriesModels();	    
		    DateTimeApplication dta = new DateTimeApplication();
		    
			GlobalReportsDAO dao = new GlobalReportsDAO();	
			
		    end_month = 0;
		    start_month = 0;
			
			Map<String, String> parameterMap = (Map<String, String>) externalContext.getRequestParameterMap();
			
			//Single Selection
			mtoReport.setEquipment(parameterMap.get("equip"));
			
			mtoReport.setMonth(parameterMap.get("month"));
			
			mtoReport.setMonth(parameterMap.get("month"));
			
			mtoReport.setStartMonth(parameterMap.get("start-month"));
						
			mtoReport.setEndMonth(parameterMap.get("end-month"));
								
			mtoReport.setYear(parameterMap.get("year"));
			
			mtoReport.setPeriod(parameterMap.get("period"));
						
			int length = (int) externalContext.getSessionMap().get("fields");
											
			 //Initialize ResultList
		     resultList = new ArrayList<Builder>();	
		 		    	 
		    	 if(type.equals("1")) {
		    		    			    	
		    	// Quantos dias possui o respectivo mês
				YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(mtoReport.getYear()), Integer.parseInt(mtoReport.getEndMonth()));
				int daysInEndMonth = yearMonthObject.lengthOfMonth();
				
				System.out.println(daysInEndMonth);
								
				mtoReport.setStartDate("01/"+mtoReport.getStartMonth()+"/"+mtoReport.getYear());
				mtoReport.setEndDate(daysInEndMonth+"/"+mtoReport.getEndMonth()+"/"+mtoReport.getYear());
				
				end_month = Integer.parseInt(mtoReport.getEndMonth());
				start_month = Integer.parseInt(mtoReport.getStartMonth());
				
				numRegisters = ((end_month - start_month) + 1);
								 
				 fieldsNumber = length;
				 
		    	 }
		    	 
		    	 else if(type.equals("2")) {
		  		    	 
				// Quantos dias possui o respectivo mês
				YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(mtoReport.getYear()), Integer.parseInt(mtoReport.getMonth()));
				int daysInMonth = yearMonthObject.lengthOfMonth();
				
				mtoReport.setStartDate("01/"+mtoReport.getMonth()+"/"+mtoReport.getYear());
				mtoReport.setEndDate(daysInMonth+"/"+mtoReport.getMonth()+"/"+mtoReport.getYear());
				
				numRegisters = daysInMonth;				
				fieldsNumber = length;
		    	
		    	 }
		    	 
		    	 else if(type.equals("3")) {
		    		 
		    		 mtoReport.setStartDate(parameterMap.get("dateStart"));
		    		 mtoReport.setEndDate(parameterMap.get("dateEnd"));
		    		 
		    		 daysCount = ((int) dta.diferencaDias(mtoReport.getStartDate(), mtoReport.getEndDate()) + 1);
		    		 
		    		//Registros de acordo com seleção - importante
		 			numRegisters = dta.RegistersNumbers(mtoReport.getStartDate(), mtoReport.getEndDate(), mtoReport.getPeriod());
		 		   
		 			if(mtoReport.getPeriod().equals("24 hours"))
		 				fieldsNumber = length - 1;
		 			
		 			else fieldsNumber = length;
		 			
		 			periodRange = dta.periodsRange(mtoReport.getPeriod());
		 		    	    	    	   
		    	   }
		    		   				    
			//Chamar Procedure de acordo com período selecionado
			procedure = models.SelectProcedureByPeriod(mtoReport.getPeriod());		
			
			//Select specific query by type
			query = SelectQueryType(type, models, mtoModels); 
													
			//Execution of Query
		   // resultQuery = dao.ExecuteQuery(procedure, query, mtoReport.getStartDate(), mtoReport.getEndDate(), numRegisters, fieldsNumber);
					
		    //Excel OutPut
		    ExcelOutPut(type, model);
		    
		    //Output to datatable
		    OutputResult(type);		
	     
	}
	
  //Create Fields
    
   public void CreateFields(String type) {
	   
	   FacesContext facesContext = FacesContext.getCurrentInstance();
	   
	   if(type.equals("1")) {
		   
			field = new String[] {localeLabel.getStringKey("mto_reports_year_month"), localeLabel.getStringKey("mto_reports_general_atmPressure"),
					localeLabel.getStringKey("mto_reports_general_relative_humidity"), localeLabel.getStringKey("mto_reports_general_temperature"),
					localeLabel.getStringKey("mto_reports_general_wind_direction"), localeLabel.getStringKey("mto_reports_general_wind_speed"),
					localeLabel.getStringKey("mto_reports_general_preciptation_rate"), localeLabel.getStringKey("mto_reports_general_preciptation_rate_hour"),
					localeLabel.getStringKey("mto_reports_general_visibility")};
						
			fieldObjectValues = new String[] { "month", "atmPressure", "relative_humidity", "temperature", "wind_direction", "wind_speed",
					"preciptation_rate", "preciptation_rate_hour", "visibility"};
	   }
	   
	   if(type.equals("2")) {
		   
		   field = new String[] {localeLabel.getStringKey("mto_reports_general_day_month"), localeLabel.getStringKey("mto_reports_general_atmPressure"),
					localeLabel.getStringKey("mto_reports_general_relative_humidity"), localeLabel.getStringKey("mto_reports_general_temperature"),
					localeLabel.getStringKey("mto_reports_general_wind_direction"), localeLabel.getStringKey("mto_reports_general_wind_speed"),
					localeLabel.getStringKey("mto_reports_general_preciptation_rate"), localeLabel.getStringKey("mto_reports_general_preciptation_rate_hour"),
					localeLabel.getStringKey("mto_reports_general_visibility")};
			
		  fieldObjectValues = new String[] { "dayOfTheMonth", "atmPressure", "relative_humidity", "temperature", "wind_direction", "wind_speed",
					"preciptation_rate", "preciptation_rate_hour", "visibility"};
			
		   }
	   
	   if(type.equals("3")) {
			   
		   field = new String[] {localeLabel.getStringKey("mto_reports_general_date"), localeLabel.getStringKey("mto_reports_general_interval"), localeLabel.getStringKey("mto_reports_general_atmPressure"),
					localeLabel.getStringKey("mto_reports_general_relative_humidity"), localeLabel.getStringKey("mto_reports_general_temperature"),
					localeLabel.getStringKey("mto_reports_general_wind_direction"), localeLabel.getStringKey("mto_reports_general_wind_speed"),
					localeLabel.getStringKey("mto_reports_general_preciptation_rate"), localeLabel.getStringKey("mto_reports_general_preciptation_rate_hour"),
					localeLabel.getStringKey("mto_reports_general_visibility")};
			
			
			fieldObjectValues = new String[] { "date", "dateTime", "atmPressure", "relative_humidity", "temperature", "wind_direction", "wind_speed",
					"preciptation_rate", "preciptation_rate_hour", "visibility"}; 
			
	      }
	   
	      facesContext.getExternalContext().getSessionMap().put("fields", field.length); 
	   
	        //Finally Draw Table
	        drawTable(field, fieldObjectValues);			   
    
      }
      
   
   /* *********** */
   /* *** AUX *** */
   /* *********** */

   /**
    * @author Wellington 10/09/2020
    * Método para criar uma Query a ser executada  
    * @param models - Objeto do tipo QuerieReportsModels
    * @param mainQuery - Query principal a ser adicionada
    * @return
    */
   public String BuildMainQuery(QueriesReportsModels models, String mainQuery) {    	 

	   String query = null;

	   query = models.BuildQuery(models.QueryHeader(mtoReport.getPeriod()), mainQuery, models.QueryFromTable(mtoReport.getPeriod()), models.LeftJoinStart("mto"),
			   models.LeftJoinCondition(mtoReport.getPeriod()), models.LeftJoinEnd("mto"), models.QueryGroupAndOrder(mtoReport.getPeriod()));

	   return query;
   }
  
   /**
    * Método que retorna um query específica de acordo com tipo
    * @param type - Tipo de específico do relatório
    * @param models - Objeto do tipo QueriesReportsModels
    * @param satModels - Objeto do tipo SatQueriesModels
    * @return
   * @throws Exception 
    */
   public String SelectQueryType(String type, QueriesReportsModels models, MtoQueriesModels mtoModels) throws Exception {   
  	
	    String query = null;
		
	     switch(type) {
	     
	     case "1": query = BuildMainQuery(models, mtoModels.WeatherMainQuery(mtoReport.getEquipment())); break;
	     case "2": query = BuildMainQuery(models, mtoModels.WeatherMainQuery(mtoReport.getEquipment())); break;
	     case "3": query = BuildMainQuery(models, mtoModels.WeatherMainQuery(mtoReport.getEquipment())); break;
	     case "4": ; break;	   
	     default: query = null; break;
	       	    	     
	     }  	 
	   		  		
		return query;
		
    }


   
   
   public void OutputResult(String type) {  
	   
	   if(type.equals("1")) { 
  		   		       			 
  		    for(int k = 0; k < numRegisters; k++) {      
  		     			     		     					 
  		      resultList.add(new MtoReports.Builder().month(resultQuery[0][k]) 
  		    		                    .atmPressure(Integer.parseInt(resultQuery[1][k]))  
  		    		                    .relative_humidity(Integer.parseInt(resultQuery[2][k]))
  		 				                .temperature(Integer.parseInt(resultQuery[3][k]))
  		 				                .windDir(Integer.parseInt(resultQuery[4][k]))
  		    		                    .windSpeed(Integer.parseInt(resultQuery[5][k]))
  		    		                    .preciptationRate(Integer.parseInt(resultQuery[5][k]))
  		    		                    .preciptationRateHour(Integer.parseInt(resultQuery[7][k]))
  		    		                    .visibility(Integer.parseInt(resultQuery[8][k])));  		    		                
  		    		    			    				 
  		 }
	   }
	   
	   if(type.equals("2")) { 
      			 
 		    for(int k = 0; k < numRegisters; k++) {      
 		     			     		     					 
 		      resultList.add(new MtoReports.Builder().dayOfMonth(Integer.parseInt(resultQuery[0][k])) 
 		    		                    .atmPressure(Integer.parseInt(resultQuery[1][k]))  
 		    		                    .relative_humidity(Integer.parseInt(resultQuery[2][k]))
 		 				                .temperature(Integer.parseInt(resultQuery[3][k]))
 		 				                .windDir(Integer.parseInt(resultQuery[4][k]))
 		    		                    .windSpeed(Integer.parseInt(resultQuery[5][k]))
 		    		                    .preciptationRate(Integer.parseInt(resultQuery[5][k]))
 		    		                    .preciptationRateHour(Integer.parseInt(resultQuery[7][k]))
 		    		                    .visibility(Integer.parseInt(resultQuery[8][k])));
 		    		    			    				 
 		 }
	   }
	   
	   if(type.equals("3")) { 
		   
		   if(mtoReport.getPeriod().equals("24 hours")) {
      			 
 		    for(int k = 0; k < numRegisters; k++) {      
 		     			     		     					 
 		      resultList.add(new MtoReports.Builder().date(resultQuery[0][k]) 
 		    		                    .atmPressure(Integer.parseInt(resultQuery[1][k]))  
 		    		                    .relative_humidity(Integer.parseInt(resultQuery[2][k]))
 		 				                .temperature(Integer.parseInt(resultQuery[3][k]))
 		 				                .windDir(Integer.parseInt(resultQuery[4][k]))
 		    		                    .windSpeed(Integer.parseInt(resultQuery[5][k]))
 		    		                    .preciptationRate(Integer.parseInt(resultQuery[5][k]))
 		    		                    .preciptationRateHour(Integer.parseInt(resultQuery[7][k]))
 		    		                    .visibility(Integer.parseInt(resultQuery[8][k])));
 		    		    			    				 
 		 }
 		    
		 }else {
 		    	
			  for(int k = 0; k < numRegisters; k++) {      
 					 
	 		      resultList.add(new MtoReports.Builder().date(resultQuery[0][k]) 
	 		    		                    .dateTime(resultQuery[1][k]) 
	 		    		                    .atmPressure(Integer.parseInt(resultQuery[2][k]))  
	 		    		                    .relative_humidity(Integer.parseInt(resultQuery[3][k]))
	 		 				                .temperature(Integer.parseInt(resultQuery[4][k]))
	 		 				                .windDir(Integer.parseInt(resultQuery[5][k]))
	 		    		                    .windSpeed(Integer.parseInt(resultQuery[6][k]))
	 		    		                    .preciptationRate(Integer.parseInt(resultQuery[7][k]))
	 		    		                    .preciptationRateHour(Integer.parseInt(resultQuery[8][k]))
	 		    		                    .visibility(Integer.parseInt(resultQuery[9][k])));
	 		    		    			    				 
	 		 }
 		    	
 		 }
		   
		   //Reorder DataTable
		 //  ReorderTableHeaderPeriod();
 	      
 	      //Finally Draw Table
 		   drawTable(field, fieldObjectValues);	
	   
	   }
	   
   }
   
   
   public void ReorderTableHeaderPeriod() {
   	 
  	 if(mtoReport.getPeriod().equals("24 hours")) {
  		 
  		 field = new String[] {localeLabel.getStringKey("mto_reports_general_date"), localeLabel.getStringKey("mto_reports_general_atmPressure"),
			localeLabel.getStringKey("mto_reports_general_relative_humidity"), localeLabel.getStringKey("mto_reports_general_temperature"),
			localeLabel.getStringKey("mto_reports_general_wind_direction"), localeLabel.getStringKey("mto_reports_general_wind_speed"),
			localeLabel.getStringKey("mto_reports_general_preciptation_rate"), localeLabel.getStringKey("mto_reports_general_preciptation_rate_hour"),
			localeLabel.getStringKey("mto_reports_general_visibility")};
			
			
			fieldObjectValues = new String[] { "date", "atmPressure", "relative_humidity", "temperature", "wind_direction", "wind_speed",
					"preciptation_rate", "preciptation_rate_hour", "visibility"}; 
			  		 
  		 
  	 } else {
  		 
  		 field = new String[] {localeLabel.getStringKey("mto_reports_general_date"), localeLabel.getStringKey("mto_reports_general_interval"), localeLabel.getStringKey("mto_reports_general_atmPressure"),
					localeLabel.getStringKey("mto_reports_general_relative_humidity"), localeLabel.getStringKey("mto_reports_general_temperature"),
					localeLabel.getStringKey("mto_reports_general_wind_direction"), localeLabel.getStringKey("mto_reports_general_wind_speed"),
					localeLabel.getStringKey("mto_reports_general_preciptation_rate"), localeLabel.getStringKey("mto_reports_general_preciptation_rate_hour"),
					localeLabel.getStringKey("mto_reports_general_visibility")};
			
			
			fieldObjectValues = new String[] { "date", "dateTime", "atmPressure", "relative_humidity", "temperature", "wind_direction", "wind_speed",
					"preciptation_rate", "preciptation_rate_hour", "visibility"}; 

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
 	 
 	  String equip = "", road = "", km = "", lanes = "", city = "", lane1 = " --- ";
 	  
 	  
 	 if(type.equals("1")) {
		  
		  EquipmentsDAO dao = new EquipmentsDAO();
		  Equipments info = new Equipments();
		  
		  info = dao.EquipReportInfo(mtoReport.getEquipment(), module);    		 
		   		 
		  fileName = localeLabel.getStringKey("excel_report_weather_file")+tm.periodName(mtoReport.getPeriod());
		  excel_title = localeLabel.getStringKey("excel_report_weather_title");
		  
		  countMergeHeader = new String[] {"A1:B4", "C1:H4", "I1:J4"};
		  		     		  
		   col = new int[] {3500, 4000, 4000, 4000, 4000, 4000, 4000, 4000,4000}; 		   
		   colStartDate = 8; colEndDate = 9;
		 		  
		  equip = info.getNome(); road = info.getEstrada();  km = info.getKm(); city = info.getCidade(); lanes = " --- ";
		      		      		  
		  CreateFields("1");		
	
		  model.StandardFonts();
		  model.StandardStyles();
		  model.StandardBorders();
		      		      		    		    		    		  
		  model.StandardExcelModelWithoutTotal(field, numRegisters, periodRange, daysCount, mtoReport.getPeriod(), dta.currentTime(), type, module,  				  
				  RoadConcessionaire.externalImagePath, excel_title, equip, city, road, km, lanes, mtoReport.getStartDate(), mtoReport.getEndDate(), countMergeHeader, 
				  col, colStartDate, colEndDate, resultQuery);
		  
	    }
 	  
 	      	     	   	  
 	  if(type.equals("2")) {
 		  
 		  EquipmentsDAO dao = new EquipmentsDAO();
		  Equipments info = new Equipments();
		  
		  info = dao.EquipReportInfo(mtoReport.getEquipment(), module);    		 
 		   		 
 		  fileName = localeLabel.getStringKey("excel_report_weather_file")+tm.periodName(mtoReport.getPeriod());
 		  excel_title = localeLabel.getStringKey("excel_report_weather_title");
 		  
 		  countMergeHeader = new String[] {"A1:B4", "C1:H4", "I1:J4"};
 		  		     		  
 		   col = new int[] {3500, 4000, 4000, 4000, 4000, 4000, 4000, 4000,4000}; 		   
 		   colStartDate = 8; colEndDate = 9;
 		 		  
 		  equip = info.getNome(); road = info.getEstrada();  km = info.getKm(); city = info.getCidade(); lanes = " --- ";
 		      		      		  
 		  CreateFields("2");		
 	
 		  model.StandardFonts();
 		  model.StandardStyles();
 		  model.StandardBorders();
 		      		      		    		    		    		  
 		  model.StandardExcelModelWithoutTotal(field, numRegisters, periodRange, daysCount, mtoReport.getPeriod(), dta.currentTime(), type, module,  				  
 				  RoadConcessionaire.externalImagePath, excel_title, equip, city, road, km, lanes, mtoReport.getStartDate(), mtoReport.getEndDate(), countMergeHeader, 
 				  col, colStartDate, colEndDate, resultQuery);
 		  
 	    }
 	  
 	 if(type.equals("3")) {
		  
		  EquipmentsDAO dao = new EquipmentsDAO();
		  Equipments info = new Equipments();
		  
		  info = dao.EquipReportInfo(mtoReport.getEquipment(), module);    		 
		   		 
		  fileName = localeLabel.getStringKey("excel_report_weather_file")+tm.periodName(mtoReport.getPeriod());
		  excel_title = localeLabel.getStringKey("excel_report_weather_title");
		  
		  countMergeHeader = new String[] {"A1:B4", "C1:I4", "J1:L4"};
		  		     		  
		   col = new int[] {3500, 4000, 4000, 4000, 4000, 4000, 4000, 4000, 4000, 4000}; 		   
		   colStartDate = 9; colEndDate = 11;
		 		  
		  equip = info.getNome(); road = info.getEstrada();  km = info.getKm(); city = info.getCidade(); lanes = " --- ";
		      		      		  
		  ReorderTableHeaderPeriod();	
	
		  model.StandardFonts();
		  model.StandardStyles();
		  model.StandardBorders();
		      		      		    		    		    		  
		  model.StandardExcelModelWithoutTotal(field, numRegisters, periodRange, daysCount, mtoReport.getPeriod(), dta.currentTime(), type, module,  				  
				  RoadConcessionaire.externalImagePath, excel_title, equip, city, road, km, lanes, mtoReport.getStartDate(), mtoReport.getEndDate(), countMergeHeader, 
				  col, colStartDate, colEndDate, resultQuery);
		  
	    }
 	   	  
 	  facesContext.getExternalContext().getSessionMap().put("xlsModel", model); 
	  facesContext.getExternalContext().getSessionMap().put("current", dta.currentTime());
	  facesContext.getExternalContext().getSessionMap().put("fileName", fileName); 
    
      }
      
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
 		  externalContext.getSessionMap().remove("xlsModel");
 		  externalContext.getSessionMap().remove("current");
 		  externalContext.getSessionMap().remove("fileName");
		}
 	  
    }
   
   
   
   }
