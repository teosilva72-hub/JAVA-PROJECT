package br.com.tracevia.webapp.controller.meteo.mto;

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
import br.com.tracevia.webapp.dao.meteo.MeteoQueriesModels;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.ExcelModels;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.ColumnModel;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.meteo.mto.MtoReports;
import br.com.tracevia.webapp.model.meteo.mto.MtoReports.Builder;
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
	
	// Vari�vel que recebe o n�mero de registros esperados para uma consula SQL (de acordo com per�odos)
     private static int numRegisters;	

	// Vari�vel que recebe o n�mero de campos de uma consulta SQL
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
		
		table = "mto_data";
		
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
	       
	        //Finally Draw Table
	        drawTable(field, fieldObjectValues);	
	        
	      //GUARDAR VALORES NA SESSION
			facesContext.getExternalContext().getSessionMap().put("fieldsLength", field.length); //Length of Fields
			facesContext.getExternalContext().getSessionMap().put("fields", field);	//Fields
			facesContext.getExternalContext().getSessionMap().put("fieldsObject", fieldObjectValues); //Objects
 
   }

	public void GetReports(String type) throws Exception{
		
		    FacesContext facesContext = FacesContext.getCurrentInstance();
	        ExternalContext externalContext = facesContext.getExternalContext();
	    
		    QueriesReportsModels models = new QueriesReportsModels();
		    MeteoQueriesModels mtoModels = new MeteoQueriesModels();	    
		    DateTimeApplication dta = new DateTimeApplication();
		    
			GlobalReportsDAO dao = new GlobalReportsDAO();	
			
			MessagesUtil message = new MessagesUtil(); //Display messages
			
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
		    		   				    
			//Chamar Procedure de acordo com per�odo selecionado
			//procedure = models.SelectProcedureByPeriod(mtoReport.getPeriod());	
		    	 
    		startDate = dta.StringDBDateFormat(mtoReport.getStartDate());
			endDate = dta.StringDBDateFormat(mtoReport.getEndDate());
			data_anterior = startDate;
			mes_inicial = mtoReport.getStartMonth();	
			month_start_date = "01";
			
			start = dta.DateTimeToStringIni(startDate); 
			end = dta.DateTimeToStringFim(endDate); 
			
			//N�MERO DE CAMPOS PARA A SA�DA DE DADOS
			//LEVA EM CONSIDERA��O N�MERO DE CAMPOS DA QUERY
			setFieldsNumber(fieldsNumber(type));
			
			resultQuery = new String[getFieldsNumber()][getNumRegisters()];
			
			//Select specific query by type
			query = SelectQueryType(type, models, mtoModels); 
																			
			System.out.println(query); //debug

			//EXECU��O DA QUERY
			String[][] auxResult = dao.ExecuteQuery(query, getFieldsNumber(), getNumRegisters());
			
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
			
			lin = auxResult[0].length;
			col = auxResult.length;
						
			if(mtoReport.getPeriod().equals("month"))
		       dta.preencherDias(resultQuery, 0, startDate, daysInMonth);
			
			else if (mtoReport.getPeriod().equals("year"))
				dta.preencherDataMes(resultQuery, 0, mtoReport.getStartMonth(), mtoReport.getEndMonth());
			
			//DATAS
			else {
				
			     dta.preencherDataPorPeriodo(resultQuery, 0, getNumRegisters(),  periodRange, startDate); 
			
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
			 
			}
			 													
			for(int j = 0; j < lin; j++) {
			   for(int i = 0; i < col; i++) {
				 
		    // CASO N�O EXISTA VALOR >>>>>>> PASSA	   
		    if(auxResult[0][j] != null)	 { 
						
			if(mtoReport.getPeriod().equals("01 hour") || mtoReport.getPeriod().equals("06 hours"))
				   hr = Integer.parseInt(auxResult[1][j].substring(0, 2));
				
			else if(!mtoReport.getPeriod().equals("24 hours") && !mtoReport.getPeriod().equals("01 hour") && !mtoReport.getPeriod().equals("06 hours")
					&& !mtoReport.getPeriod().equals("year") && !mtoReport.getPeriod().equals("month") ) {
				    hr = Integer.parseInt(auxResult[1][j].substring(0, 2));
				    minuto =  Integer.parseInt(auxResult[1][j].substring(3, 5));	
				    
				}
						
			  if(!mtoReport.getPeriod().equals("year") &&  !mtoReport.getPeriod().equals("month")) {

				// Restri��o caso n�o haja dados nos primeiros registros
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
				 
			  }
			  //////////////////////////////////
			  /////////////// YEAR REPORT 
			  ////////////////////////////////
			  else if(mtoReport.getPeriod().equals("year")) {	
				  				  				  
				      if((mes_inicial != null) && Integer.parseInt(mes_inicial) != Integer.parseInt(auxResult[0][j])) {
							
							p = Integer.parseInt(auxResult[0][j]) - Integer.parseInt(mes_inicial);
							mes_inicial = null;
							
						}else if((mes_inicial != null) && Integer.parseInt(mes_inicial) == Integer.parseInt(auxResult[0][j])) {
							 p = 0;
							 mes_inicial = null;
						
						}      
				      else if(Integer.parseInt(mes_anterior) != Integer.parseInt(auxResult[0][j])) {							
							 p++;							
						}
												
					     mes_anterior = auxResult[0][j];
					     
					     if(i > 0) 
							 resultQuery[i][p] = auxResult[i][j];	
					   		
					}
			  //////////////////////////////////
			  /////////////// MONTH REPORT 
			  ////////////////////////////////
			  else if(mtoReport.getPeriod().equals("month")){				
				  
				    if((month_start_date != null) && Integer.parseInt(month_start_date) != Integer.parseInt(auxResult[0][j])) {
				    							
						p = Integer.parseInt(auxResult[0][j]) - Integer.parseInt(month_start_date);
						month_start_date = null;
						
					}else if(Integer.parseInt(data_anterior) != Integer.parseInt(auxResult[0][j])) {							
						 p++;							
					}
											
				     data_anterior = auxResult[0][j];
				     
				     if(i > 0) 
						 resultQuery[i][p] = auxResult[i][j];		
				  				  
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

				//UPDATE RESET BUTTON
				RequestContext.getCurrentInstance().update("form-btns:#btn-tab-reset");

				//UPDATE BUTTON GENERATE EXCEL
				RequestContext.getCurrentInstance().update("form-excel:#excel-act");	
				
			
			//CASO CONTRARIO ENTRA AQUI
		} else {
			      message.InfoMessage(localeMto.getStringKey("mto_message_records_not_found_title"), localeMto.getStringKey("mto_message_records_not_found"));
		          CreateFields(type);  
		          
		          //EXECUTE JS
				  RequestContext.getCurrentInstance().execute("hideMessage();");
				  
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
 	 
 	  String equip = "", road = "", km = "", lanes = "", city = "", lane1 = " --- ";
 	  
 	  
 	 if(type.equals("1")) {
		  
		  EquipmentsDAO dao = new EquipmentsDAO();
		  Equipments info = new Equipments();
		  
		  info = dao.EquipReportInfo(mtoReport.getEquipment(), module);    		 
		   		 
		  fileName = localeLabel.getStringKey("excel_report_weather_file")+tm.periodName(mtoReport.getPeriod());
		  excel_title = localeLabel.getStringKey("excel_report_weather_title_year"); 
		  
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
 		  excel_title = localeLabel.getStringKey("excel_report_weather_title_month");
 		  
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
		  excel_title = localeLabel.getStringKey("excel_report_weather_title_periods");
		  
		  countMergeHeader = new String[] {"A1:B4", "C1:I4", "J1:L4"};
		  		     		  
		   col = new int[] {3500, 4000, 4000, 4000, 4000, 4000, 4000, 4000, 4000, 4000}; 		   
		   colStartDate = 9; colEndDate = 11;
		 		  
		  equip = info.getNome(); road = info.getEstrada();  km = info.getKm(); city = info.getCidade(); lanes = " --- ";
		 	
		  model.StandardFonts();
		  model.StandardStyles();
		  model.StandardBorders();
		      		      		    		    		    		  
		  model.StandardExcelModelWithoutTotal(field, numRegisters, periodRange, daysCount, mtoReport.getPeriod(), dta.currentTime(), type, module,  				  
				  RoadConcessionaire.externalImagePath, excel_title, equip, city, road, km, lanes, mtoReport.getStartDate(), mtoReport.getEndDate(), countMergeHeader, 
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
					 
 		      resultList.add(new MtoReports.Builder().month(resultQuery[0][k]) 
                .atmPressure(resultQuery[1][k] == null? 0 : Integer.parseInt(resultQuery[1][k]))  
                .relative_humidity(resultQuery[2][k] == null? 0 : Integer.parseInt(resultQuery[2][k]))
                .temperature(resultQuery[3][k] == null? 0.0 : Double.parseDouble(resultQuery[3][k]))
                .windDir(resultQuery[4][k] == null? 0 : Integer.parseInt(resultQuery[4][k]))
                .windSpeed(resultQuery[5][k] == null? 0 : Integer.parseInt(resultQuery[5][k]))
                .preciptationRate(resultQuery[6][k] == null? 0 : Integer.parseInt(resultQuery[6][k]))
                .preciptationRateHour(resultQuery[7][k] == null? 0 : Integer.parseInt(resultQuery[7][k]))
                .visibility(resultQuery[8][k] == null? 0 : Integer.parseInt(resultQuery[8][k])));  		 
 		       		       		    	    			    				 
 		 }  		   		 
  		}
  		
  		public void monthBuilder() {
  			
  			 for(int k = 0; k < getNumRegisters(); k++) {      
					 
  	 		      resultList.add(new MtoReports.Builder().dayOfMonth(resultQuery[0][k] == null? 0 : Integer.parseInt(resultQuery[0][k])) 
                    .atmPressure(resultQuery[1][k] == null? 0 : Integer.parseInt(resultQuery[1][k]))  
                    .relative_humidity(resultQuery[2][k] == null? 0 : Integer.parseInt(resultQuery[2][k]))
                    .temperature(resultQuery[3][k] == null? 0.0 : Double.parseDouble(resultQuery[3][k]))
	                .windDir(resultQuery[4][k] == null? 0 : Integer.parseInt(resultQuery[4][k]))
                    .windSpeed(resultQuery[5][k] == null? 0 : Integer.parseInt(resultQuery[5][k]))
                    .preciptationRate(resultQuery[6][k] == null? 0 : Integer.parseInt(resultQuery[6][k]))
                    .preciptationRateHour(resultQuery[7][k] == null? 0 : Integer.parseInt(resultQuery[7][k]))
                    .visibility(resultQuery[8][k] == null? 0 : Integer.parseInt(resultQuery[8][k])));
  	 		    		    			    				 
  	 		 }
  			
  		}
  		
  		public void periodBuilder() {
  			
  		  for(int k = 0; k < getNumRegisters(); k++) { 
  							 
 		      resultList.add(new MtoReports.Builder().date(resultQuery[0][k]) 
                .dateTime(resultQuery[1][k]) 
                .atmPressure(resultQuery[2][k] == null? 0 : Integer.parseInt(resultQuery[2][k]))  
                .relative_humidity(resultQuery[3][k] == null? 0 : Integer.parseInt(resultQuery[3][k]))
                .temperature(resultQuery[4][k] == null? 0.0 : Double.parseDouble(resultQuery[4][k]))
                .windDir(resultQuery[5][k] == null? 0 : Integer.parseInt(resultQuery[5][k]))
                .windSpeed(resultQuery[6][k] == null? 0 : Integer.parseInt(resultQuery[6][k]))
                .preciptationRate(resultQuery[7][k] == null? 0 : Integer.parseInt(resultQuery[7][k]))
                .preciptationRateHour(resultQuery[8][k] == null? 0 : Integer.parseInt(resultQuery[8][k]))
                .visibility(resultQuery[9][k] == null? 0 : Integer.parseInt(resultQuery[9][k])));
 		    		    			    				 
 		 }
		    	
   }		   
   
   }
