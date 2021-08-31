package br.com.tracevia.webapp.controller.sos;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
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
import br.com.tracevia.webapp.dao.global.QueryModelsBattery;
import br.com.tracevia.webapp.dao.sos.SOSMainQueries;
import br.com.tracevia.webapp.dao.sos.SOSQueryModels;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.ExcelModels;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.ColumnModel;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.global.VBV;
import br.com.tracevia.webapp.model.sos.SOS;
import br.com.tracevia.webapp.model.sos.SOSReports;
import br.com.tracevia.webapp.model.sos.SOSReports.Builder;
import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.QueriesReportsModels;

@ManagedBean(name="sosReportsBean")
@RequestScoped
public class SOSReportsBean {
	
	private SOSReports sosReport;
	private List<SelectItem> equipments;  
	private List<SelectItem> periods;  
	private List<SelectItem> months;  
	private List<SelectItem> years;	

	private List<Builder> resultList;	
	private List<VBV> resultVBV;
	private List<String> header;  
	private List<ColumnModel> columns;  
	List<? extends Equipments> listSOS;  
	
	private final String dateFormat = "dd/MM/yyyy";
	private final String datetimeFormat = "dd/MM/yyyy HH:mm";

	//Locale Docs
	LocaleUtil localeLabel, localeCalendar, localeDir, localePeriods;  

	int periodRange, daysInMonth, daysCount; 
	
	String jsTableId, jsTableScrollHeight;
	
	long seconds = 0;
 
	// Varivel que recebe o nmero de registros esperados para uma consula SQL (de acordo com perodos)
	private static int numRegisters;	

	// Varivel que recebe o nmero de campos de uma consulta SQL
	private static int fieldsNumber;	

	String[] fields, jsonFields, fieldObjectValues, fieldsAux, fieldObjAux; //Nome dos campos // Valores de cada campo -> Atribuidos a variavis do modelo  

	String[][] resultQuery, jsonArray; 
	
	private String jsColumn, jsData, jsDataFormat, chartTitle, imageName;
	
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
	
	// Equipments
	String[] equips;

	private boolean clearBool, excelBool, chartBool;

	public SOSReports getSosReport() {
		return sosReport;
	}

	public void setSosReport(SOSReports sosReport) {
		this.sosReport = sosReport;
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
		SOSReportsBean.numRegisters = numRegisters;
	}

	public static int getFieldsNumber() {
		return fieldsNumber;
	}

	public static void setFieldsNumber(int fieldsNumber) {
		SOSReportsBean.fieldsNumber = fieldsNumber;
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
	
	 ///////////////////////////////////
    //CONSTRUCTOR
    ///////////////////////////////// 

	@PostConstruct
	public void initialize() {

		localeLabel = new LocaleUtil();	
		localeLabel.getResourceBundle(LocaleUtil.LABELS_SOS);

		localeCalendar = new LocaleUtil();	
		localeCalendar.getResourceBundle(LocaleUtil.LABELS_CALENDAR);
		
		localePeriods = new LocaleUtil();	
		localePeriods.getResourceBundle(LocaleUtil.LABELS_PERIODS);
		
		// MODULE
		module = "sos";		
			
		/* EQUIPMENTS SELECTION */	
		
		sosReport = new SOSReports();	
		equipments = new ArrayList<SelectItem>();		
		
		listSOS = new ArrayList<SOS>();

		try {

			EquipmentsDAO dao = new EquipmentsDAO();	
			
			listSOS = dao.EquipmentSelectOptions(module);

			sosReport.equipments = new String[listSOS.size()];
								
		} catch (Exception e1) {			
			e1.printStackTrace();
		}

		for (Equipments e : listSOS) {
			
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

		/* PERIODS */		
		periods = new ArrayList<SelectItem>();			
		periods.add(new SelectItem("05 minutes", localePeriods.getStringKey("periods_five_minutes")));
		periods.add(new SelectItem("06 minutes", localePeriods.getStringKey("periods_six_minutes")));
		periods.add(new SelectItem("10 minutes", localePeriods.getStringKey("periods_teen_minutes")));
		periods.add(new SelectItem("15 minutes", localePeriods.getStringKey("periods_fifteen_minutes")));
		periods.add(new SelectItem("30 minutes", localePeriods.getStringKey("periods_thirty_minutes")));  
		periods.add(new SelectItem("01 hour", localePeriods.getStringKey("periods_one_hour")));
		periods.add(new SelectItem("06 hours", localePeriods.getStringKey("periods_six_hours")));
		periods.add(new SelectItem("24 hours", localePeriods.getStringKey("periods_twenty_four_hours")));
	
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

	// ----------------------------------------------------------------------------------------------------------------------------
	
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

	
	// ----------------------------------------------------------------------------------------------------------------------------
	
	//////DESENHAR TABLES 	
	
	public void CreateFields(String type) {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		// -----------------------------------------------------------------------------
		
			     // SOS ESTATÍSTICA REPORT
			
					if(type.equals("1")) {
						
						//Table Fields
						 fields = new String[]{localeLabel.getStringKey("sos_report_column_date"), localeLabel.getStringKey("sos_report_column_datetime"),
								localeLabel.getStringKey("sos_report_column_call_received"), localeLabel.getStringKey("sos_report_column_call_answered"), localeLabel.getStringKey("sos_report_column_call_ended"),
								localeLabel.getStringKey("sos_report_column_call_missed"), localeLabel.getStringKey("sos_report_column_call_error"), localeLabel.getStringKey("sos_report_column_call_duration_avg")};
						
						// Table Objects
						 fieldObjectValues = new String[] {"date", "time", "callReceivedAmount", "callAnsweredAmount", "callEndedAmount", "callMissedAmount", "callErrorAmount", "callDuration"};
												
							//JSON chart fields
							jsonFields = new String[] {localeLabel.getStringKey("sos_reports_chart_hAxis"),
									localeLabel.getStringKey("sos_report_column_call_received"), localeLabel.getStringKey("sos_report_column_call_answered"), localeLabel.getStringKey("sos_report_column_call_ended"),
									localeLabel.getStringKey("sos_report_column_call_missed"), localeLabel.getStringKey("sos_report_column_call_error")};
													
							//JSON chart title and subtitle
							chartTitle = localeLabel.getStringKey("sos_chart_title_statistic"); 		
							imageName = localeLabel.getStringKey("sos_chart_file_name_statistic");	
					
					
					
					}
					
				// -----------------------------------------------------------------------------
					
					// SOS BATTERY REPORT
					
					if(type.equals("2")) {
						
						// Table Fields
						fields = new String[]{localeLabel.getStringKey("sos_report_column_date"), localeLabel.getStringKey("sos_report_column_datetime"),
								localeLabel.getStringKey("sos_report_column_volts"), localeLabel.getStringKey("sos_report_column_ampere") };
						
						// Table Objects
						fieldObjectValues = new String[] {"date", "time", "batteryVolts", "batteryAmp"};
						
						//JSON chart fields
						jsonFields = new String[] {localeLabel.getStringKey("sos_reports_chart_hAxis"),
								localeLabel.getStringKey("sos_report_column_volts"), localeLabel.getStringKey("sos_report_column_ampere") };
						
						//JSON chart title and subtitle
						chartTitle = localeLabel.getStringKey("sos_chart_title_battery"); 		
						imageName = localeLabel.getStringKey("sos_chart_file_name_battery");	
						
						
					}
					
				// -----------------------------------------------------------------------------
					
					//DESENHAR A TABELA
					drawTable(fields, fieldObjectValues);	

					//GUARDAR VALORES NA SESSION
					facesContext.getExternalContext().getSessionMap().put("fieldsLength", fields.length); //Length of Fields
					facesContext.getExternalContext().getSessionMap().put("fields", fields);	//Fields
					facesContext.getExternalContext().getSessionMap().put("jsonFields", jsonFields);	//Fields
					facesContext.getExternalContext().getSessionMap().put("fieldsObject", fieldObjectValues); //Objects
				
	        }
	
	         // ----------------------------------------------------------------------------------------------------------------------

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
		
			SOSMainQueries sosModels = new SOSMainQueries(); // SOS MODEL QUERIE	    
			QueryModelsBattery batteryModels = new QueryModelsBattery();   // SOS MODEL QUERIE	    
			DateTimeApplication dta = new DateTimeApplication(); //DateTimeApsplication class

			GlobalReportsDAO dao = new GlobalReportsDAO();	//GlobalReportsDAO
					
			String startDate = null, endDate = null, data_anterior = null;
						
			/*** Obter parmetros que vem no submit de cada pesquisa ***/

			//Get request values for multiple fields selection
			Map<String, String[]> parameterMap = (Map<String, String[]>) externalContext.getRequestParameterValuesMap();

			//Get Request single values
			Map<String, String> parameterMap2 = (Map<String, String>) externalContext.getRequestParameterMap();

			/**** Multiple Params ***/
			
			//param for equipments array
			equips = parameterMap.get("equips");

			// ---------------------------------------------------------------------------------------------------------------------------

			/**** Single Params ***/

			//param for equipment
			sosReport.setEquipment(parameterMap2.get("equip"));
			
			//param for periods
			sosReport.setPeriod(parameterMap2.get("periods"));

			//param for dateStart
			sosReport.setStartDate(parameterMap2.get("dateStart"));

			//param for dateEnd
			sosReport.setEndDate(parameterMap2.get("dateEnd"));
						
			//Table id
			jsTableId = parameterMap2.get("jsTable");
			
			//Table Scroll Height 
			 jsTableScrollHeight = parameterMap2.get("jsTableScrollHeight");
			
			/**** Single Params ***/

			//Initialize ResultList
			//Builder class -> for construct objects
			resultList = new ArrayList<Builder>();	

           // -------------------------------------------------------------------------------------------------------------------------

				//RETORNA NMERO DE REGISTROS POR PERIODO E DATAS SELECIONADAS	
				setNumRegisters(dta.RegistersNumbers(sosReport.getStartDate(), sosReport.getEndDate(), sosReport.getPeriod())); 
							
				//CONTAGEM DOS DIAS
				if(sosReport.getStartDate() != null && sosReport.getEndDate() != null)
				daysCount = ((int) dta.diferencaDias(sosReport.getStartDate(), sosReport.getEndDate()) + 1);

				//INTERVALO POR PERIODO
				periodRange = dta.periodsRange(sosReport.getPeriod());
				
				// System.out.println(getNumRegisters());
							
		   // ---------------------------------------------------------------------------------------------------------------------------
			
			startDate = dta.StringDBDateFormat(sosReport.getStartDate());
			endDate = dta.StringDBDateFormat(sosReport.getEndDate());
			data_anterior = startDate;
					
			start = dta.DateTimeToStringIni(startDate); 
			end = dta.DateTimeToStringFim(endDate); 

			/** TODO RELATRIO PASSA POR AQUI!!! **/

			//NUMERO DE CAMPOS PARA A SADA DE DADOS
			//LEVA EM CONSIDERAO NMERO DE CAMPOS DA QUERY
			setFieldsNumber(fieldsNumber(type));

			//SELECIONA UMA PROCEDURE DE ACORDO COM PERODO SELECIONADO
			//procedure = models.SelectProcedureByPeriod(sosReport.getPeriod());	
			
			resultQuery = new String[getNumRegisters()][getFieldsNumber()];	
			
			//JSON ARRAY DATA RANGE						
			jsonArray = new String[getNumRegisters()][jsonFields.length];	
			
			//SELECIONA UMA QUERY DE ACORDO COM TIPO SELECIONADO		
			query = BatteryQuery(type, batteryModels, sosModels, module);
							
			//System.out.println(query);
		  
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
					
			lin = auxResult.length;
			col = auxResult[0].length;
				
			//DATAS
			dta.preencherDataPorPeriodo(resultQuery, 0, getNumRegisters(),  periodRange, startDate); 
			
			//JSON DATA
			dta.preencherJSONDataPorPeriodo(jsonArray, 0, getNumRegisters(),  periodRange, startDate); 
			
			//PERIODOS
			//NEW
			if(sosReport.getPeriod().equals("05 minutes")) {			
				 dta.intervalo05Minutos(resultQuery, 1, getNumRegisters());	
				 dta.intervaloJSON05Minutos(jsonArray, 0, getNumRegisters());				 
			}
						
			if(sosReport.getPeriod().equals("06 minutes"))	{		
			     dta.intervalo06Minutos(resultQuery, 1, getNumRegisters());
			     dta.intervaloJSON06Minutos(jsonArray, 0, getNumRegisters());			
			}
			
			if(sosReport.getPeriod().equals("10 minutes")) {		
				dta.intervalo10Minutos(resultQuery, 1, getNumRegisters());
				dta.intervaloJSON10Minutos(jsonArray, 0, getNumRegisters());	
			}
			   			
			if(sosReport.getPeriod().equals("15 minutes"))	{	
			    dta.intervalo15Minutos(resultQuery, 1, getNumRegisters());	
			    dta.intervaloJSON15Minutos(jsonArray, 0, getNumRegisters());	
			}
			
			if(sosReport.getPeriod().equals("30 minutes"))	{	
				dta.intervalo30Min(resultQuery, 1, getNumRegisters());	
			    dta.intervaloJSON30Minutos(jsonArray, 0, getNumRegisters());	
			}
				   		        			
			if(sosReport.getPeriod().equals("01 hour")) { 	
				dta.preencherHora(resultQuery, 1, getNumRegisters());
				dta.intervaloJSON01Hora(jsonArray, 0, getNumRegisters());	
			}
			
			if(sosReport.getPeriod().equals("06 hours")) {	
			    dta.intervalo06Horas(resultQuery, 1, getNumRegisters());
			    dta.intervaloJSON06Horas(jsonArray, 0, getNumRegisters());	
			}
			
			 if(sosReport.getPeriod().equals("24 hours"))
			    dta.intervalo24Horas(resultQuery, 1, getNumRegisters());
													
			for(int j = 0; j < lin; j++) {
			   for(int i = 0; i < col; i++) {
			
			// CASO NO EXISTA VALOR >>>>>>> PASSA	   
			if(auxResult[j][0] != null)	 {  
			
			if(sosReport.getPeriod().equals("01 hour") || sosReport.getPeriod().equals("06 hours"))
				   hr = Integer.parseInt(auxResult[j][1].substring(0, 2));
				
			else if(!sosReport.getPeriod().equals("24 hours") && !sosReport.getPeriod().equals("01 hour") && !sosReport.getPeriod().equals("06 hours")) {
				    hr = Integer.parseInt(auxResult[j][1].substring(0, 2));
				    minuto =  Integer.parseInt(auxResult[j][1].substring(3, 5));	
				    			 
				}
			
				// Restrio caso no haja dados nos primeiros registros
				if ((startDate != null) && (!auxResult[j][0].equals(startDate))) {   // Executa uma unica vez
					
					if(sosReport.getPeriod().equals("24 hours"))
						iterator = (int) dta.daysDifference(startDate, auxResult[j][0]);

					else iterator = dta.daysDifference(startDate, auxResult[j][0], periodRange);	
					
					pos+= iterator;
					startDate = null;
					

				} else if (!auxResult[j][0].equals(data_anterior)) {								
												
					if(sosReport.getPeriod().equals("24 hours"))
						iterator = (int) dta.daysDifference(data_anterior, auxResult[j][0]);
					   
					else iterator = dta.daysDifference(data_anterior, auxResult[j][0], periodRange);	
					
					pos+= iterator;							
				} 	
								
				data_anterior = auxResult[j][0];
				
				 if(sosReport.getPeriod().equals("05 minutes"))	{
					 p = dta.index05Minutes(hr, minuto);
					 p = p + pos;
				 }
				 else if(sosReport.getPeriod().equals("06 minutes")) {	
						 p = dta.index06Minutes(hr, minuto);
						 p = p + pos;
				 }
				 else if(sosReport.getPeriod().equals("10 minutes")) {
				    	 p = dta.index10Minutes(hr, minuto);
				    	 p = p + pos;
				 }
				 else if(sosReport.getPeriod().equals("15 minutes")) {	
						 p = dta.index15Minutes(hr, minuto);
				         p = p + pos;
								
				 }
				 else if(sosReport.getPeriod().equals("30 minutes")) {	
						 p = dta.index30Minutes(hr, minuto);
						 p = p + pos;
				 }
				 else if(sosReport.getPeriod().equals("01 hour"))				
					p = pos + hr;
							
				else if(sosReport.getPeriod().equals("06 hours")) {
					
					p = dta.index06Hours(hr);				
					p = pos + p;
					
				}
				
				else if(sosReport.getPeriod().equals("24 hours"))
					     p = pos;
						
									 
				if(i > 1 ) {
				   
					resultQuery[p][i] = auxResult[j][i];
					
					if(i < jsonFields.length)
                       jsonArray[p][i-1] = auxResult[j][i];								
				}					  				
				
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
				
				JSONData(type, isChartBool(), sosReport.getPeriod(), sosReport.getEquipment());
										
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
		
		
		public void JSONData(String type, boolean chartBool, String period, String equipment) {
			
			TranslationMethods trm = new TranslationMethods();
					
			String vAxisTitle = localeLabel.getStringKey("sos_reports_chart_vAxis");
			
			LocalDateTime local =  LocalDateTime.now();
			
			DateTimeFormatter format = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm");  
		    String formatDateTime = local.format(format);  
		    
		    String equipName = ""; // EQUIP NAME VARIABLE
		    
		    if(equipment != null) { // IF IS A SINGLE EQUIPMENT
			    
			    //LIST OF EQUIPMENTS TO GET CURRENT NAME
			    for (Equipments eq : listSOS) {
			    	
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
		          //jsData = jsData.toString().replaceAll("\"", "\\'");
		          // jsData.toString().replaceAll("\\)", "\\'");
		          jsData = jsData.toString().replaceAll("null", "0");			  		          
		         		         		       		        
		        // DEBUG
		       // System.out.println("Header = " + jsColumn);
		       // System.out.println("Data = " + jsData);
		    	        		       		       	        
		        if(period.equals("24 hours"))
		           RequestContext.getCurrentInstance().execute("reDrawChart("+jsColumn+", "+jsData+", '"+chartTitle+"', '"+vAxisTitle+"', '"+ dateFormat +"', '"+imageName+"');");
		        
		        else RequestContext.getCurrentInstance().execute("reDrawChart("+jsColumn+", "+jsData+", '"+chartTitle+"','"+vAxisTitle+"', '"+ datetimeFormat +"', '"+imageName+"');");
		        
			}
	     }	

		///////////////////////////////////
		// SOS DATABASE METHODS
		///////////////////////////////// 
				
		  	
		// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

			public String SOSBattery(QueryModelsBattery models, String mainQuery, String index, String type) {    	 

				String query = null;
				
				query = models.BuildBatteryQueryBase(models.BatteryHeader(sosReport.getPeriod()), mainQuery, models.BatteryFromTable(), models.UseIndex(index),
							models.WhereClauseBattery(start, end, sosReport.getEquipment(), type),  models.BatteryQueryGroupAndOrderBy(sosReport.getPeriod()));
					
				return query;
					
		 }		
			  
		// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	  
		/**
		 * Mtodo que retorna um query especfica de acordo com tipo
		 * @param type - Tipo de especfico do relatrio
		 * @param models - Objeto do tipo QueriesReportsModels
		 * @param satModels - Objeto do tipo SatQueriesModels
		 * @return query
		 * @throws Exception 
		 */
			
		public String BatteryQuery(String type, QueryModelsBattery models, SOSMainQueries sosModel, String equip_type) throws Exception {   
			
			return SOSBattery(models, sosModel.BatteryStatus(sosReport.getEquipment()), QueriesReportsModels.USE_INDEX_IDX_BATTERY, equip_type);

		}	 		
		
		
		// -------------------------------------------------------------------------------------------------------------------------------------

		
		///////////////////////////////////
		//SAIDA DE DADOS
		/////////////////////////////////  
					  
		/**
		 * @author Wellington 10/09/2020
		 * Mtodo para gerenciar construtores a fim de criar saidas de dados dinamicamente.
		 * @param type - Tipo do sosReport (Define qual  a sua finalidade)
		 */

		// PREENCHER SAIDA DE DADOS 
		// POR TIPO DE RELATRIO
		public void OutPutResult(String type) {  

			//ACESSAR DADOS DO RELATRIOF
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();

			//FIELDS EXTERNOS ARMAZENADOS NA REQUISIO
			fields = (String[]) externalContext.getSessionMap().get("fields");
			fieldObjectValues =  (String[]) externalContext.getSessionMap().get("fieldsObject");

				// CHAMADA
				SOSBatteryBuilder();

				// DRAW TABLE -- BUILD HEADER
				drawTable(fields, fieldObjectValues); 
		
		} 

		// -------------------------------------------------------------------------------------------------------------------

		 ///////////////////////////////////
		 // SAIDA DE DADOS
		/////////////////////////////////      

		/////// FIELDS NUMBER - SAÍDA DE DADO

		public Integer fieldsNumber(String type) {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();

			int length = (int) externalContext.getSessionMap().get("fieldsLength");

			int fields = 0;
						
			fields = length;			 		 
						
			return fields;  
			
		}

	     /////// FIELDS NUMBER - SADA DE DADO 

		/**********************************************************************************************************/

	   /////// SAIDA DO EXCEL

		public void ExcelOutPut(String type, ExcelModels model) throws IOException, Exception {
				
				FacesContext facesContext = FacesContext.getCurrentInstance();		

				DateTimeApplication dta = new DateTimeApplication();
			
				//Initialize Excel Class Object
				//Excel Model Class
				model = new ExcelModels(); 
				
				EquipmentsDAO dao = new EquipmentsDAO();  // EquipmentsDAO Class
				Equipments info = new Equipments(); // SAT Class
				
				String equip = "", road = "", km = "", lanes = "", city = "";  //Equipment Info
				
				// Get equipment values from DB			
				info = dao.GenericInfo(module, sosReport.getEquipment());
				
				String[] mergedCells = new String[] {"A1:B4", "C1:H4", "I1:J4"}; // Merge cells
				
				int[] col = new int[] {3500, 4000, 4000, 4000, 4000, 4000, 4000, 4000}; //SET size to columns

				int dateStartCell = 8, dateEndCell = 9; //Col ini & end date

				//Equipment Info
				equip = info.getNome();
				road = info.getEstrada(); 
				km = info.getKm(); 
			    city = info.getCidade();
			    lanes = " ---- ";

				model.StandardFonts();  //Set Font
				model.StandardStyles(); //Set Style
				model.StandardBorders(); // Set Borders
								
		
				//Define fileName
				fileName = localeLabel.getStringKey("sos_excel_file_name_battery"); // File Name
				String title = localeLabel.getStringKey("sos_excel_file_title_battery"); // File Title
							
				model.StandardBatteryExcelModel(fields, getNumRegisters(), periodRange, daysCount, sosReport.getPeriod(), dta.currentTime(), type, module,  				  
						RoadConcessionaire.externalImagePath, title, equip, city, road, km, lanes, sosReport.getStartDate(), sosReport.getEndDate(), mergedCells, 
						col, dateStartCell, dateEndCell, resultQuery);
			
			//Define Values in session map !important
			facesContext.getExternalContext().getSessionMap().put("xlsModel", model); 
			facesContext.getExternalContext().getSessionMap().put("current", dta.currentTime());
			facesContext.getExternalContext().getSessionMap().put("fileName", fileName); 
			
		}

	      /////// SAIDA DO EXCEL
         
		// ------------------------------------------------------------------------------------------------------------------

	    /////// DOWNLOAD DO EXCEL
		
		public void download() {

			FacesContext facesContext = FacesContext.getCurrentInstance();	
			ExternalContext externalContext = facesContext.getExternalContext();

			model = (ExcelModels) externalContext.getSessionMap().get("xlsModel");
			currentDate = (String) externalContext.getSessionMap().get("current");
			fileName = (String) externalContext.getSessionMap().get("fileName");

			String name = fileName+""+currentDate;
				
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

		// ------------------------------------------------------------------------------------------------------------------
		
	     /////// RESET FORM VALUES
			
		public void resetFormValues(String type) {
			
			FacesContext facesContext = FacesContext.getCurrentInstance();	
			ExternalContext externalContext = facesContext.getExternalContext();

			//Reset object => call on click reset button
			sosReport = new SOSReports();

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

	    // -------------------------------------------------------------------------------------------------------------------------
		
		///////////////////////////////////
		// CONSTRUCTORS FOR RESULTLIST
		/////////////////////////////////  
				
		// SOS BATTERY Builder
		
		public void SOSBatteryBuilder() {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SOSReports.Builder().date(resultQuery[k][0]) 
						.time(resultQuery[k][1])						
						.batteryVolts(resultQuery[k][2] == null? "0" : resultQuery[k][2])					
						.batteryAmp(resultQuery[k][3] == null? "0" : resultQuery[k][3]));						 			 
			   } 
		  }
		
		// -------------------------------------------------------------------------------------------------------------------------
	

	}
