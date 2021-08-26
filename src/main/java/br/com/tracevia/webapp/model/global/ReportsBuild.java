package br.com.tracevia.webapp.model.global;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import br.com.tracevia.webapp.model.sat.SatReports.Builder;
import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.QueriesReportsModels;

public class ReportsBuild {

	// Lists
	private List<SelectItem> equipments;  
	private List<SelectItem> months;  
	private List<SelectItem> years;	
	private List<SelectItem> periods;  
	private List<ColumnModel> columns;  
	private List<Builder> resultList;		
	private List<String> header;  

	// Date Format 
	private final String dateFormat = "dd/MM/yyyy";
	private final String datetimeFormat = "dd/MM/yyyy HH:mm";
			
	// Registers of an array
	private static int numRegisters;	

	// Fields number of an array
	private static int fieldsNumber;	
		
	// Interval
    private int breakTime;
    
    // Interval Index to increment
    private int breakTimeIndex;
    
    // Days Count Interval
    private int daysCount;
    
    // For Reports Columns
    private String[] fields;   
    private String[] fieldObjectValues;
    private String[] fieldsAux;
    private String[] fieldObjAux; 
    
    // JSON FIELDS
    public String[][] jsonArray;
    public String[] jsonFields;  
    
    // RESULT FOR QUERY
    public String[][] resultQuery;
    		
    // GSON to use JSON Google API
 	public Gson gson;
    
	// JSON Attributes
	private String jsColumn;
	private String jsData;
	private String jsChartTitle;
	private String jsImageName;
	private String fileName;
	private String currentDate;
	
	// Enable button (Front End)
	private boolean clearBool;
	private boolean excelBool;
	private boolean chartBool;
	
	// String Query
	private String query;
	
	// JSON for table 
	private String jsTableId;
	private String jsTableScrollHeight;
	
	// Locale
	public LocaleUtil localeLabel; 
	LocaleUtil localeLabelPeriods;
	LocaleUtil localeLabelCharts;
	LocaleUtil localeLabelExcel; 
	LocaleUtil localeLabelReports; 
	LocaleUtil localeMessage; 
	
	// Equipment DAO Class
	EquipmentsDAO equipDAO;
	
	// TESTE
	ExcelModels model;
	
	// Builder
	public ReportsBuild() {}
	
	public List<SelectItem> getEquipments() {
		return equipments;
	}
	
	public List<SelectItem> getMonths() {
		return months;
	}
	
	public List<SelectItem> getYears() {
		return years;
	}
	
	public List<SelectItem> getPeriods() {
		return periods;
	}
	
	public List<ColumnModel> getColumns() {
		return columns;
	}
	
	public List<Builder> getResultList() {
		return resultList;
	}
	
	public List<String> getHeader() {
		return header;
	}
	
	public static int getNumRegisters() {
		return numRegisters;
	}
	public static void setNumRegisters(int numRegisters) {
		ReportsBuild.numRegisters = numRegisters;
	}
	public static int getFieldsNumber() {
		return fieldsNumber;
	}
	public static void setFieldsNumber(int fieldsNumber) {
		ReportsBuild.fieldsNumber = fieldsNumber;
	}
	public int getBreakTime() {
		return breakTime;
	}
	public void setBreakTime(int breakTime) {
		this.breakTime = breakTime;
	}
	public int getBreakTimeIndex() {
		return breakTimeIndex;
	}
	public void setBreakTimeIndex(int breakTimeIndex) {
		this.breakTimeIndex = breakTimeIndex;
	}
	
	public String[] getFields() {
		return fields;
	}
	
	public void setFields(String[] fields) {
		this.fields = fields;
	}

	public String[] getFieldObjectValues() {
		return fieldObjectValues;
	}
	public void setFieldObjectValues(String[] fieldObjectValues) {
		this.fieldObjectValues = fieldObjectValues;
	}
	public String[] getFieldsAux() {
		return fieldsAux;
	}
	public void setFieldsAux(String[] fieldsAux) {
		this.fieldsAux = fieldsAux;
	}
	public String[] getFieldObjAux() {
		return fieldObjAux;
	}
	public void setFieldObjAux(String[] fieldObjAux) {
		this.fieldObjAux = fieldObjAux;
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
	public String getJsChartTitle() {
		return jsChartTitle;
	}
	public void setJsChartTitle(String jsChartTitle) {
		this.jsChartTitle = jsChartTitle;
	}
	public String getJsImageName() {
		return jsImageName;
	}
	public void setJsImageName(String jsImageName) {
		this.jsImageName = jsImageName;
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
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getJsTableId() {
		return jsTableId;
	}
	public void setJsTableId(String jsTableId) {
		this.jsTableId = jsTableId;
	}
	public String getJsTableScrollHeight() {
		return jsTableScrollHeight;
	}
	public void setJsTableScrollHeight(String jsTableScrollHeight) {
		this.jsTableScrollHeight = jsTableScrollHeight;
	}
	public String getDateFormat() {
		return dateFormat;
	}
	public String getDatetimeFormat() {
		return datetimeFormat;
	}	
	
	public int getDaysCount() {
		return daysCount;
	}

	public void setDaysCount(int daysCount) {
		this.daysCount = daysCount;
	}
		
	// ----------------------------------------------------------------------------------------------------------------------
		
	/**
	 * Método para criar o cabeçalho dos relatórios
	 * @param field - Nome dos campos
	 * @param objectValue - Objeto de cada campo
	 */
	public void drawTable(String[] field, String[] objectValue) {

		columns = new ArrayList<ColumnModel>();

		for(int i = 0; i < field.length; i++)
			columns.add(new ColumnModel(field[i], objectValue[i]));		

	}
	
	// ----------------------------------------------------------------------------------------------------------------------
	
	   // Download File Method
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
		
	// ----------------------------------------------------------------------------------------------------------------------
		
		public void GetReports(String type, ReportsSelection selection) throws Exception{
						
			// RESET ON RESTART
		    // resetFormValues(type);
			
			// --------------------------------------------------------------------------------------------------------------

			// GET EXTERNAL CONTENT
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			SatQueriesModels satModels = new SatQueriesModels(); // SatReportsModel class	    
			QueriesReportsModels models = new QueriesReportsModels(); // QUERY MODEL CLASS		
			DateTimeApplication dta = new DateTimeApplication(); // DATETIME APPLICATION CLASS

			GlobalReportsDAO dao = new GlobalReportsDAO();	// GLOBAL REPORTS DAO					
					
			String startDate = null, endDate = null, data_anterior = null, equip_anterior = null; // STRING AUX VARS
						
			// --------------------------------------------------------------------------------------------------------------
			
			// MAP REQUEST VARS
			
			// GET REQUEST VALUES FOR MULTIPLE SELECTION FIELDS
			Map<String, String[]> parameterMap = (Map<String, String[]>) externalContext.getRequestParameterValuesMap();

			// GET REQUEST VALUE FOR SINGLE SELECTION FIELD
			Map<String, String> parameterMap2 = (Map<String, String>) externalContext.getRequestParameterMap();
			
			// ----------------------------------------------------------------------------------------------------------------			
			// PARAMS			
			// ----------------------------------------------------------------------------------------------------------------

			// MULTIPLE PARAMS
			
			// EQUIPMENTS
			selection.setEquipments(parameterMap.get("equips"));

			// VEHICLES
			selection.setVehicles(parameterMap.get("vehicles"));

			// AXLES
			selection.setAxles(parameterMap.get("axles"));

			// CLASSES
			selection.setClasses(parameterMap.get("classes"));

			// ----------------------------------------------------------------------------------------------------------------

			// SINGLE PARAMS

			// EQUIPMENT
			selection.setEquipment(parameterMap2.get("equip"));

			// DATE START
			selection.setStartDate(parameterMap2.get("dateStart"));

			// DATE END
			selection.setEndDate(parameterMap2.get("dateEnd"));

			// PERIODS
			selection.setPeriod(parameterMap2.get("periods"));

			// MONTH
			selection.setMonth(parameterMap2.get("month"));

			// YEAR
			selection.setYear(parameterMap2.get("year"));
			
			// TABLE ID
			jsTableId = parameterMap2.get("jsTable");
			
			// TABLE SCROLL HEIGHT
			jsTableScrollHeight = parameterMap2.get("jsTableScrollHeight");

			// ----------------------------------------------------------------------------------------------------------------
			// PARAMS			
			// ----------------------------------------------------------------------------------------------------------------
			// ----------------------------------------------------------------------------------------------------------------			
			// PROCESS DATA			
			// ----------------------------------------------------------------------------------------------------------------
			
			// LIST TO SHOW DATA
			resultList = new ArrayList<Builder>();	
							
			// INITIALIZE NUMBER OF REGISTERS TO FILL ARRAY TWO-DIMENSIONAL
			int numRegisters = 0;								
			
			// ----------------------------------------------------------------------------------------------------------------

			 // RETORNA NÚMERO DE REGISTROS CALCULADOS PELO PERIODO E AS DATAS SELECIONADAS	
			 numRegisters = dta.RegistersNumbers(selection.getStartDate(), selection.getEndDate(), selection.getPeriod()); 
				
			// ----------------------------------------------------------------------------------------------------------------
										
			// CONTAGEM DOS NÚMERO DE DIAS - SELECIONADO A PARTIR DA DATA DE INICIO COM A DATA DE FIM			 
			if(selection.getStartDate() != null && selection.getEndDate() != null)
				 daysCount = ((int) dta.diferencaDias(selection.getStartDate(), selection.getEndDate()) + 1);

				// INTERVALO POR PERIODO
				breakTimeIndex = dta.periodsRange(selection.getPeriod()); // INDICE 
				breakTime = breakTimeIndex; // INCREMENTO
								
			   // DATA DE INICIO - FORMATO DATABASE				
			   startDate = dta.StringDBDateFormat(selection.getStartDate());
			   // DATA DE FIM - FORMATO DATABASE		
			   endDate = dta.StringDBDateFormat(selection.getEndDate());			   
			   //DATA ANTERIOR
			   data_anterior = startDate;
														
			  //start = dta.DateTimeToStringIni(startDate); 
			   //end = dta.DateTimeToStringFim(endDate); 
					
			resultQuery = new String[getNumRegisters()][getFieldsNumber()];	
			
			//JSON ARRAY DATA RANGE					
			jsonArray = new String[getNumRegisters()][jsonFields.length];	
			
			//SELECIONA UMA QUERY DE ACORDO COM TIPO SELECIONADO
		//	query = SelectQueryType(type, models, satModels);
			
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
			dta.preencherDataPorPeriodo(resultQuery, 0, getNumRegisters(),  breakTimeIndex, startDate); 
			
			//JSON DATA
			if(!type.equals("8") && !type.equals("11"))
			   dta.preencherJSONDataPorPeriodo(jsonArray, 0, getNumRegisters(),  breakTimeIndex, startDate); 
			
			//PERIODOS
			//NEW
			if(selection.getPeriod().equals("05 minutes")) {			
				 dta.intervalo05Minutos(resultQuery, 1, getNumRegisters());	
				 
				 if(!type.equals("8") && !type.equals("11"))
				 dta.intervaloJSON05Minutos(jsonArray, 0, getNumRegisters());				 
			}
						
			if(selection.getPeriod().equals("06 minutes"))	{		
			     dta.intervalo06Minutos(resultQuery, 1, getNumRegisters());
			     
			     if(!type.equals("8") && !type.equals("11"))
			     dta.intervaloJSON06Minutos(jsonArray, 0, getNumRegisters());			
			}
			
			if(selection.getPeriod().equals("10 minutes")) {		
				dta.intervalo10Minutos(resultQuery, 1, getNumRegisters());
				
				if(!type.equals("8") && !type.equals("11"))
				dta.intervaloJSON10Minutos(jsonArray, 0, getNumRegisters());	
			}
			   			
			if(selection.getPeriod().equals("15 minutes"))	{	
			    dta.intervalo15Minutos(resultQuery, 1, getNumRegisters());	
			    
			    if(!type.equals("8") && !type.equals("11"))
			    dta.intervaloJSON15Minutos(jsonArray, 0, getNumRegisters());	
			}
			
			if(selection.getPeriod().equals("30 minutes"))	{	
				dta.intervalo30Min(resultQuery, 1, getNumRegisters());	
				
				if(!type.equals("8") && !type.equals("11"))
			    dta.intervaloJSON30Minutos(jsonArray, 0, getNumRegisters());	
			}
				   		        			
			if(selection.getPeriod().equals("01 hour")) { 	
				dta.preencherHora(resultQuery, 1, getNumRegisters());
				
				if(!type.equals("8") && !type.equals("11"))
				dta.intervaloJSON01Hora(jsonArray, 0, getNumRegisters());	
			}
			
			if(selection.getPeriod().equals("06 hours")) {	
			    dta.intervalo06Horas(resultQuery, 1, getNumRegisters());
			    
			    if(!type.equals("8") && !type.equals("11"))
			    dta.intervaloJSON06Horas(jsonArray, 0, getNumRegisters());	
			}
			
			 if(selection.getPeriod().equals("24 hours")) {
			    dta.intervalo24Horas(resultQuery, 1, getNumRegisters());	 
										
			 }
			 						   			 
			for(int j = 0; j < lin; j++) {
			   for(int i = 0; i < col; i++) {
			
			// CASO NO EXISTA VALOR >>>>>>> PASSA	   
			if(auxResult[j][0] != null)	 {  
						
			if(selection.getPeriod().equals("01 hour") || selection.getPeriod().equals("06 hours"))
				   hr = Integer.parseInt(auxResult[j][1].substring(0, 2));
						
			else if(!selection.getPeriod().equals("24 hours") && !selection.getPeriod().equals("01 hour") && !selection.getPeriod().equals("06 hours")) {
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
				    iterator = dta.daysDifference(data_anterior, auxResult[j][0], breakTimeIndex);	
				    				
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
					inc += breakTime;			 
				    equip_anterior = auxResult[j][2];
				    
				}
					 				 			 						
				// ------------------------------------------------------------------------------------ 
				
				 if(selection.getPeriod().equals("05 minutes"))	{
					 p = dta.index05Minutes(hr, minuto);
					 p = p + pos + inc;
				 }
				 else if(selection.getPeriod().equals("06 minutes")) {	
						 p = dta.index06Minutes(hr, minuto);
						 p = p + pos + inc;
				 }
				 else if(selection.getPeriod().equals("10 minutes")) {
				    	 p = dta.index10Minutes(hr, minuto);
				    	 p = p + pos + inc;
				 }
				 else if(selection.getPeriod().equals("15 minutes")) {	
						 p = dta.index15Minutes(hr, minuto);
				         p = p + pos + inc;
								
				 }
				 else if(selection.getPeriod().equals("30 minutes")) {	
						 p = dta.index30Minutes(hr, minuto);
						 p = p + pos + inc;
				 }
				 
				 else if(selection.getPeriod().equals("01 hour"))				
					p = pos + hr + inc;
							
				else if(selection.getPeriod().equals("06 hours")) {
					
					p = dta.index06Hours(hr);				
					p = pos + p + inc;
					
				}
				
				else if(selection.getPeriod().equals("24 hours"))
					     p = pos + inc;
				 					 			 								 
				if(i > 2 ) 
				    resultQuery[p][i] = auxResult[j][i];	
				
			    }
			  
			  else {
				  
				  if (!auxResult[j][0].equals(data_anterior)) {								
						
					    // CASO SEJA DIFERENTE PEGA-SE A DIFERENÇA DE DIAS
					    // DATA ANTERIOR, DATA QUE VEM DA BASE DE DADOS E INTERVALO DO PERIODO
					    iterator = dta.daysDifference(data_anterior, auxResult[j][0], breakTimeIndex);	
					    				
					    // ADICIONA-SE O ITERATOR A POSICAO
						pos = iterator;	
										
						// ADICIONA-SE A DATA ANTERIOR A DATA DO REGISTRO ATUAL
						data_anterior = auxResult[j][0];
																												
					 }	
				  
					 if(selection.getPeriod().equals("05 minutes"))	{
						 p = dta.index05Minutes(hr, minuto);
						 p = p + pos;
					 }
					 else if(selection.getPeriod().equals("06 minutes")) {	
							 p = dta.index06Minutes(hr, minuto);
							 p = p + pos;
					 }
					 else if(selection.getPeriod().equals("10 minutes")) {
					    	 p = dta.index10Minutes(hr, minuto);
					    	 p = p + pos;
					 }
					 else if(selection.getPeriod().equals("15 minutes")) {	
							 p = dta.index15Minutes(hr, minuto);
					         p = p + pos;
									
					 }
					 else if(selection.getPeriod().equals("30 minutes")) {	
							 p = dta.index30Minutes(hr, minuto);
							 p = p + pos;
					 }
					 else if(selection.getPeriod().equals("01 hour"))				
						p = pos + hr;
								
					else if(selection.getPeriod().equals("06 hours")) {
						
						p = dta.index06Hours(hr);				
						p = pos + p;
						
					}
					
					else if(selection.getPeriod().equals("24 hours"))
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
				//OutPutResult(type);
				
				//SADA DO EXCEL
			   // ExcelOutPut(type, model);

				//BOTO DE LIMPAR 
				setClearBool(false);

				//LINK DE DOWNLOAD DO EXCEL
				setExcelBool(false);	
				
				//LINK PARA ACESSAR O GRÁFICO
				setChartBool(false);
				
				//JSONData(isChartBool(), selection.getPeriod(), selection.getEquipment());
										
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
		
		// ----------------------------------------------------------------------------------------------------------------
		
	
		
	
}
