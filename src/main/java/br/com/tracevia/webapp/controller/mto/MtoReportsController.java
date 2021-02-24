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

import org.primefaces.context.RequestContext;

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
import br.com.tracevia.webapp.util.MessagesUtil;
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
		
	LocaleUtil localeLabel, localeCalendar, localeMto;
			
	int periodRange, daysInMonth, daysCount, start_month, end_month;  
		
	String procedure, query, queryCount, module, fileName, currentDate, direction1, direction2, table; 
	
	// Variável que recebe o número de registros esperados para uma consula SQL (de acordo com períodos)
     private static int numRegisters;	

	// Variável que recebe o número de campos de uma consulta SQL
	private static int fieldsNumber;	
	
	private boolean clearBool, excelBool;
	
	//Dates
	String start, end;
		
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

	@PostConstruct
	public void initialize() {
		
		localeLabel = new LocaleUtil();	
		localeLabel.getResourceBundle(LocaleUtil.LABELS_MTO);
		
		localeCalendar = new LocaleUtil();	
		localeCalendar.getResourceBundle(LocaleUtil.LABELS_CALENDAR);
		
		localeMto = new LocaleUtil();
		localeMto.getResourceBundle(LocaleUtil.MESSAGES_MTO);
				
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
		
		table = "weather_stations";
				
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

////CRIAR TABLE HEADERS

// CRIAR CAMPOS PARA OS HEADERS
// CRIAR CAMPOS PARA TABELA FRONT-END
// FIELDS -> HEADERS
// FIELDSOBJECTVALUES -> VALUES		

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

	public void GetReports(String type) throws Exception{
		
		   FacesContext facesContext = FacesContext.getCurrentInstance();
	       ExternalContext externalContext = facesContext.getExternalContext();
	    
		    QueriesReportsModels models = new QueriesReportsModels();
		    MtoQueriesModels mtoModels = new MtoQueriesModels();	    
		    DateTimeApplication dta = new DateTimeApplication();
		    
			GlobalReportsDAO dao = new GlobalReportsDAO();	
			
			MessagesUtil message = new MessagesUtil(); //Display messages
			
			String startDate = null, endDate = null, data_anterior = null, mes_anterior = null, mes_inicial = null;
			
		    end_month = 0;
		    start_month = 0;
			
			Map<String, String> parameterMap = (Map<String, String>) externalContext.getRequestParameterMap();
			
			//Single Selection
			mtoReport.setEquipment(parameterMap.get("equip"));
			
			mtoReport.setMonth(parameterMap.get("month"));
			
			mtoReport.setMonth(parameterMap.get("month"));
			
			mtoReport.setStartMonth(parameterMap.get("start_month"));
						
			mtoReport.setEndMonth(parameterMap.get("end_month"));
								
			mtoReport.setYear(parameterMap.get("year"));
			
			mtoReport.setPeriod(parameterMap.get("period"));
													
			 //Initialize ResultList
		     resultList = new ArrayList<Builder>();	
		 		    	 
		    	 if(type.equals("1")) {
		    		    			    	
		    	// Quantos dias possui o respectivo mês
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
		  		    	 
				// Quantos dias possui o respectivo mês
				YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(mtoReport.getYear()), Integer.parseInt(mtoReport.getMonth()));
				int daysInMonth = yearMonthObject.lengthOfMonth();
				
				mtoReport.setStartDate("01/"+mtoReport.getMonth()+"/"+mtoReport.getYear());
				mtoReport.setEndDate(daysInMonth+"/"+mtoReport.getMonth()+"/"+mtoReport.getYear());
				
				setNumRegisters(daysInMonth);				
				    	
		    	 }
		    	 
		    	 else if(type.equals("3")) {
		    		 
		    		 mtoReport.setStartDate(parameterMap.get("dateStart"));
		    		 mtoReport.setEndDate(parameterMap.get("dateEnd"));
		    		 
		    		 daysCount = ((int) dta.diferencaDias(mtoReport.getStartDate(), mtoReport.getEndDate()) + 1);
		    		 
		    		//Registros de acordo com seleção - importante
		 			setNumRegisters(dta.RegistersNumbers(mtoReport.getStartDate(), mtoReport.getEndDate(), mtoReport.getPeriod()));
		 		 	 			
		 			periodRange = dta.periodsRange(mtoReport.getPeriod());
		 		    	    	    	   
		    	   }
		    		   				    
			//Chamar Procedure de acordo com período selecionado
			//procedure = models.SelectProcedureByPeriod(mtoReport.getPeriod());	
		    	 
    		startDate = dta.StringDBDateFormat(mtoReport.getStartDate());
			endDate = dta.StringDBDateFormat(mtoReport.getEndDate());
			data_anterior = startDate;
			mes_inicial = mtoReport.getStartMonth();	
			
			start = dta.DateTimeToStringIni(startDate); 
			end = dta.DateTimeToStringFim(endDate); 
			
			//NÚMERO DE CAMPOS PARA A SAÍDA DE DADOS
			//LEVA EM CONSIDERAÇÃO NÚMERO DE CAMPOS DA QUERY
			setFieldsNumber(fieldsNumber(type));
			
			resultQuery = new String[getFieldsNumber()][getNumRegisters()];
			
			//Select specific query by type
			query = SelectQueryType(type, models, mtoModels); 
																			
			System.out.println(query); //debug

			//EXECUÇÃO DA QUERY
			String[][] auxResult = dao.ExecuteQuery(query);
			
			//// NEW METHOD
							
			if(auxResult.length != 0) {
			
			int minuto = 0;
			int iterator= 0;
			int pos = 0;
			int hr = 0;
	 		
			int lin = 0;
			int col = 0;
			int p = 0;
			
			lin = auxResult[0].length;
			col = auxResult.length;
			
			//DATAS
			//dta.preencherDataPorPeriodo(resultQuery, 0, getNumRegisters(),  periodRange, startDate); 
			
			//PERIODOS
			//NEW
			if(mtoReport.getPeriod().equals("05 minutes"))			
				 dta.intervalo05Minutos(resultQuery, 1, getNumRegisters());	
						
			if(mtoReport.getPeriod().equals("06 minutes"))			
			     dta.intervalo06Minutos(resultQuery, 1, getNumRegisters());
			
			if(mtoReport.getPeriod().equals("10 minutes"))		
				dta.intervalo10Minutos(resultQuery, 1, getNumRegisters());
			   			
			if(mtoReport.getPeriod().equals("15 minutes"))		
			    dta.intervalo15Minutos(resultQuery, 1, getNumRegisters());	
			
			if(mtoReport.getPeriod().equals("30 minutes"))		
				dta.intervalo30Min(resultQuery, 1, getNumRegisters());	
				   		        			
			if(mtoReport.getPeriod().equals("01 hour")) 	
				dta.preencherHora(resultQuery, 1, getNumRegisters());
			
			if(mtoReport.getPeriod().equals("06 hours"))	
			    dta.intervalo06Horas(resultQuery, 1, getNumRegisters());
			
			 if(mtoReport.getPeriod().equals("24 hours"))
			    dta.intervalo24Horas(resultQuery, 1, getNumRegisters());
			 													
			for(int j = 0; j < lin; j++) {
			   for(int i = 0; i < col; i++) {
				   
		    if(auxResult[0][j] != null)	 { 
						
			if(mtoReport.getPeriod().equals("01 hour") || mtoReport.getPeriod().equals("06 hours"))
				   hr = Integer.parseInt(auxResult[1][j].substring(0, 2));
				
			else if(!mtoReport.getPeriod().equals("24 hours") && !mtoReport.getPeriod().equals("01 hour") && !mtoReport.getPeriod().equals("06 hours")
					&& !mtoReport.getPeriod().equals("year")) {
				    hr = Integer.parseInt(auxResult[1][j].substring(0, 2));
				    minuto =  Integer.parseInt(auxResult[1][j].substring(3, 5));	
				    
				}
					  
			//System.out.println(satReport.getStartDate());
			
			  if(!mtoReport.getPeriod().equals("year")) {

				// Restrição caso não haja dados nos primeiros registros
				if ((startDate != null) && (!auxResult[0][j].equals(startDate))) {   // Executa uma unica vez
					
					if(mtoReport.getPeriod().equals("24 hours"))
						iterator = (int) dta.daysDifference(startDate, auxResult[0][j]);

					else iterator = dta.daysDifference(startDate, auxResult[0][j], periodRange);	
					
					pos+= iterator;
					startDate = null;

				} else if (!auxResult[0][j].equals(data_anterior)) {								
												
					if(mtoReport.getPeriod().equals("24 hours"))
						iterator = (int) dta.daysDifference(data_anterior, auxResult[0][j]);
					   
					else iterator = dta.daysDifference(data_anterior, auxResult[0][j], periodRange);	
					
					pos+= iterator;							
				} 	
							
				data_anterior = auxResult[0][j];
				
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
				 
					if(i > 1 )
					    resultQuery[i][p] = auxResult[i][j];
				 
			  }else {	
				  				  
				      if((mes_inicial != null) && Integer.parseInt(mes_inicial) != Integer.parseInt(auxResult[0][j])) {
							
							p = Integer.parseInt(auxResult[0][j]) - Integer.parseInt(mes_inicial);
							mes_inicial = null;
							
						}else if(Integer.parseInt(mes_anterior) != Integer.parseInt(auxResult[0][j])) {							
							 p++;							
						}
												
					     mes_anterior = auxResult[0][j];
					}
			 				
				   if(i > 0) 
					 resultQuery[i][p] = auxResult[i][j];				    
			  
				   }
			     }
			   }		  	
					
			  //// NEW METHOD

			//CASO EXISTIR VALORES
			if(resultQuery.length > 0) {

				//SAÍDA PARA A TABELA
				OutputResult(type);
				
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
			
	   } else {
		   message.InfoMessage(localeMto.getStringKey("mto_message_records_not_found_title"), localeMto.getStringKey("mto_message_records_not_found"));
		   CreateFields(type);
		 
		   
	   }
	     
	}
			
			
/**********************************************************************************************************/

///////////////////////////////////
//SAIDA DE DADOS
/////////////////////////////////      

/////// FIELDS NUMBER - SAÍDA DE DADO

public Integer fieldsNumber(String type) {

	FacesContext facesContext = FacesContext.getCurrentInstance();
	ExternalContext externalContext = facesContext.getExternalContext();

	int length = (int) externalContext.getSessionMap().get("fields");

int fields = 0;

/**** CONTAGEM VEÍCULOS ****/		
if(type.equals("1")) {    		
	fields = length;		

}

/**** CONTAGEM VEÍCULOS ****/		
if(type.equals("2")) {    
	fields = length;   
	
}

/**** FLUXO MENSAL  ****/
if(type.equals("3")) {    		
		fields = length;    		 
	}
	
	return fields;    	 
}

 /////// FIELDS NUMBER - SAÍDA DE DADO 

/**********************************************************************************************************/
   
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
   public String BuildMainQuery(QueriesReportsModels models, String mainQuery, String index) {    	 

	   String query = null;
	   query = models.BuildQueryIndexType2(models.QueryDateTimeHeader(mtoReport.getPeriod()), mainQuery, models.QueryFromMtoTable(mtoReport.getPeriod(), table), models.useIndex(index),
				models.innerJoinMto(), models.whereClauseWeatherEquipDate(mtoReport.getEquipment(), start, end), models.QueryWeatherGroupAndOrder(mtoReport.getPeriod()));

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
	     
	     case "1": query = BuildMainQuery(models, mtoModels.WeatherMainQuery(mtoReport.getEquipment()), QueriesReportsModels.USE_INDEX_IDX_DATETIME_STATION); break;
	     case "2": query = BuildMainQuery(models, mtoModels.WeatherMainQuery(mtoReport.getEquipment()), QueriesReportsModels.USE_INDEX_IDX_DATETIME_STATION); break;
	     case "3": query = BuildMainQuery(models, mtoModels.WeatherMainQuery(mtoReport.getEquipment()), QueriesReportsModels.USE_INDEX_IDX_DATETIME_STATION); break;
	     case "4": ; break;	   
	     default: query = null; break;
	       	    	     
	     }  	 
	   		  		
		return query;
		
    }

   
   public void OutputResult(String type) {  
	   
	   if(type.equals("1")) { 
  		   		       			 
  		    for(int k = 0; k < getNumRegisters(); k++) {      
  		     			     		     					 
  		      resultList.add(new MtoReports.Builder().month(resultQuery[0][k]) 
  		    		                    .atmPressure(resultQuery[1][k] == null? 0 : Integer.parseInt(resultQuery[1][k]))  
  		    		                    .relative_humidity(resultQuery[2][k] == null? 0 : Integer.parseInt(resultQuery[2][k]))
  		 				                .temperature(resultQuery[3][k] == null? 0 : Integer.parseInt(resultQuery[3][k]))
  		 				                .windDir(resultQuery[4][k] == null? 0 : Integer.parseInt(resultQuery[4][k]))
  		    		                    .windSpeed(resultQuery[5][k] == null? 0 : Integer.parseInt(resultQuery[5][k]))
  		    		                    .preciptationRate(resultQuery[6][k] == null? 0 : Integer.parseInt(resultQuery[6][k]))
  		    		                    .preciptationRateHour(resultQuery[7][k] == null? 0 : Integer.parseInt(resultQuery[7][k]))
  		    		                    .visibility(resultQuery[8][k] == null? 0 : Integer.parseInt(resultQuery[8][k])));  		    		                
  		    		    			    				 
  		 }
	   }
	   
	   if(type.equals("2")) { 
      			 
 		    for(int k = 0; k < getNumRegisters(); k++) {      
 		     			     		     					 
 		      resultList.add(new MtoReports.Builder().dayOfMonth(Integer.parseInt(resultQuery[0][k])) 
 		    		                    .atmPressure(resultQuery[1][k] == null? 0 : Integer.parseInt(resultQuery[1][k]))  
 		    		                    .relative_humidity(resultQuery[2][k] == null? 0 : Integer.parseInt(resultQuery[2][k]))
 		 				                .temperature(resultQuery[3][k] == null? 0 : Integer.parseInt(resultQuery[3][k]))
 		 				                .windDir(resultQuery[4][k] == null? 0 : Integer.parseInt(resultQuery[4][k]))
 		    		                    .windSpeed(resultQuery[5][k] == null? 0 : Integer.parseInt(resultQuery[5][k]))
 		    		                    .preciptationRate(resultQuery[6][k] == null? 0 : Integer.parseInt(resultQuery[6][k]))
 		    		                    .preciptationRateHour(resultQuery[7][k] == null? 0 : Integer.parseInt(resultQuery[7][k]))
 		    		                    .visibility(resultQuery[8][k] == null? 0 : Integer.parseInt(resultQuery[8][k])));
 		    		    			    				 
 		 }
	   }
	   if(type.equals("3")) { 
	   
			  for(int k = 0; k < getNumRegisters(); k++) {      
 					 
	 		      resultList.add(new MtoReports.Builder().date(resultQuery[0][k]) 
	 		    		                    .dateTime(resultQuery[1][k]) 
	 		    		                    .atmPressure(resultQuery[2][k] == null? 0 : Integer.parseInt(resultQuery[2][k]))  
	 		    		                    .relative_humidity(resultQuery[3][k] == null? 0 : Integer.parseInt(resultQuery[3][k]))
	 		 				                .temperature(resultQuery[4][k] == null? 0 : Integer.parseInt(resultQuery[4][k]))
	 		 				                .windDir(resultQuery[5][k] == null? 0 : Integer.parseInt(resultQuery[5][k]))
	 		    		                    .windSpeed(resultQuery[6][k] == null? 0 : Integer.parseInt(resultQuery[6][k]))
	 		    		                    .preciptationRate(resultQuery[7][k] == null? 0 : Integer.parseInt(resultQuery[7][k]))
	 		    		                    .preciptationRateHour(resultQuery[8][k] == null? 0 : Integer.parseInt(resultQuery[8][k]))
	 		    		                    .visibility(resultQuery[9][k] == null? 0 : Integer.parseInt(resultQuery[9][k])));
	 		    		    			    				 
	 		 }
 		    	
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
  	
  //Abreviação do Mês
  		public String monthComparison(String selectedMes) {

  			String selectMonth = "";				
  									
  			if (selectedMes.equals("1"))
  				selectMonth = "January";
  			if (selectedMes.equals("2"))
  				selectMonth = "February";
  			if (selectedMes.equals("3"))
  				selectMonth = "March";
  			if (selectedMes.equals("4"))
  				selectMonth = "April";
  			if (selectedMes.equals("5"))
  				selectMonth = "May";
  			if (selectedMes.equals("6"))
  				selectMonth = "June";
  			if (selectedMes.equals("7"))
  				selectMonth = "July";
  			if (selectedMes.equals("8"))
  				selectMonth = "August";
  			if (selectedMes.equals("9"))
  				selectMonth = "September";
  			if (selectedMes.equals("10"))
  				selectMonth = "October";
  			if (selectedMes.equals("11"))
  				selectMonth = "November";
  			if (selectedMes.equals("12"))
  				selectMonth = "December";						
  			
  			return selectMonth;
  		}
   
   
   
   }
