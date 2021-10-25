package br.com.tracevia.webapp.controller.sos;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import br.com.tracevia.webapp.dao.sos.SOSMainQueries;
import br.com.tracevia.webapp.dao.sos.SOSQueryModels;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.ExcelModels;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.ColumnModel;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.sos.SOS;
import br.com.tracevia.webapp.model.sos.SOSReports;
import br.com.tracevia.webapp.model.sos.SOSReports.Builder;
import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.QueriesReportsModels;

@ManagedBean(name="sosMultiStatistic")
@RequestScoped
public class SOSMultiStatisticReport {

	private SOSReports sosReport;
	private List<SelectItem> equipments;  
	private List<SelectItem> vehicles;  
	private List<SelectItem> vehiclesCCR;  
	private List<SelectItem> periods;  
	private List<SelectItem> months;  
	private List<SelectItem> years;	
	private List<SelectItem> classes;  
	private List<SelectItem> axles;  

	private List<Builder> resultList;
	private List<String> header;  
	private List<ColumnModel> columns;  
	List<? extends Equipments> listSOS;  

	//Locale Docs
	LocaleUtil localeLabel, localePeriods, localeMessage;  

	int periodRange, dateRange, daysInMonth, daysCount; 
	
	String jsTableId, jsTableScrollHeight;
 
	// Varivel que recebe o nmero de registros esperados para uma consula SQL (de acordo com perodos)
	private static int numRegisters;	

	// Varivel que recebe o nmero de campos de uma consulta SQL
	private static int fieldsNumber;	
	
	// Varivel que recebe o nmero de campos de uma consulta SQL
	private static int fieldsIndex;

	String[] fields, jsonFields, fieldObjectValues, fieldsAux, fieldObjAux, equipName; //Nome dos campos // Valores de cada campo -> Atribuidos a variavis do modelo  

	String[][] resultQuery, jsonArray; 
		
	String procedure, query, queryCount, module, fileName, currentDate, direction1, direction2, directionLabel1, directionLabel2, 
	directionValue1, directionValue2, equipId, equip_anterior; 
	
	//Dates
	String start, end;

	BufferedWriter writer;  
	ByteArrayOutputStream byteWriter; 

	EquipmentsDAO equipDAO;
	ExcelModels model;
		
	String displayEquipInfo, displayDirection1, displayDirection2;

	private boolean clearBool, excelBool;

	public SOSReports getSosReport() {
		return sosReport;
	}

	public void setSosReport(SOSReports sosReport) {
		this.sosReport = sosReport;
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
		SOSMultiStatisticReport.numRegisters = numRegisters;
	}

	public static int getFieldsNumber() {
		return fieldsNumber;
	}

	public static void setFieldsNumber(int fieldsNumber) {
		SOSMultiStatisticReport.fieldsNumber = fieldsNumber;
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
	 	
	public static int getFieldsIndex() {
		return fieldsIndex;
	}

	public static void setFieldsIndex(int fieldsIndex) {
		SOSMultiStatisticReport.fieldsIndex = fieldsIndex;
	}
	
	
	///////////////////////////////////
    //CONSTRUCTOR
   ///////////////////////////////// 

	@PostConstruct
	public void initialize() {

		localeLabel = new LocaleUtil();	
		localeLabel.getResourceBundle(LocaleUtil.LABELS_SOS);
		
		localePeriods = new LocaleUtil();	
		localePeriods.getResourceBundle(LocaleUtil.LABELS_PERIODS);
	
		/* EQUIPMENTS SELECTION */	
		sosReport = new SOSReports();	
		equipments = new ArrayList<SelectItem>();		

		listSOS = new ArrayList<SOS>();  

		try {

			EquipmentsDAO dao = new EquipmentsDAO();		 
			listSOS = dao.EquipmentSelectOptions("sos");

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
	
		//module
		module = "sos";		

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

	public void CreateFields() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
		/**** ESTATISTICA CHAMADAS ****/
	
		//Table Fields
		 fields = new String[]{localeLabel.getStringKey("sos_report_column_date"), localeLabel.getStringKey("sos_report_column_datetime"),
				localeLabel.getStringKey("sos_report_column_equipment"), localeLabel.getStringKey("sos_report_column_call_received"), 
				localeLabel.getStringKey("sos_report_column_call_answered"), localeLabel.getStringKey("sos_report_column_call_ended"),
				localeLabel.getStringKey("sos_report_column_call_missed"), localeLabel.getStringKey("sos_report_column_call_error"), 
				localeLabel.getStringKey("sos_report_column_call_duration_avg")};
		
		// Table Objects
		 fieldObjectValues = new String[] {"date", "time", "equipment", "callReceivedAmount", "callAnsweredAmount", "callEndedAmount", "callMissedAmount", "callErrorAmount", "callDuration"};
			
	   // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
					
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
	public void GetReport() throws Exception{
		
		//RESET ON RESTART
	    resetFormValues();

		// Get external application contents
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		SOSQueryModels models = new SOSQueryModels(); //QuerieReportsModel class
		SOSMainQueries sosModels = new SOSMainQueries(); // SatReportsModel class	    
		DateTimeApplication dta = new DateTimeApplication(); //DateTimeApsplication class

		GlobalReportsDAO dao = new GlobalReportsDAO();	//GlobalReportsDAO
				
		String startDate = null, endDate = null, data_anterior = null, data_inicial = "", data_final = "";
										
		/*** Obter parmetros que vem no submit de cada pesquisa ***/

		//Get request values for multiple fields selection
		Map<String, String[]> parameterMap = (Map<String, String[]>) externalContext.getRequestParameterValuesMap();

		//Get Request single values
		Map<String, String> parameterMap2 = (Map<String, String>) externalContext.getRequestParameterMap();

		/**** Multiple Params ***/
		
		//param for equipments array
		sosReport.equipments = parameterMap.get("equips");

		/**** Multiple Params ***/

		/**** Single Params ***/

		//param for equipment
		sosReport.setEquipment(parameterMap2.get("equip"));

		//param for dateStart
		sosReport.setStartDate(parameterMap2.get("dateStart"));

		//param for dateEnd
		sosReport.setEndDate(parameterMap2.get("dateEnd"));

		//param for periods
		sosReport.setPeriod(parameterMap2.get("periods"));

		//param for month
		sosReport.setMonth(parameterMap2.get("month"));

		//param for year
		sosReport.setYear(parameterMap2.get("year"));
		
		//Table id
		jsTableId = parameterMap2.get("jsTable");
		
		//Table Scroll Height 
		 jsTableScrollHeight = parameterMap2.get("jsTableScrollHeight");

		/**** Single Params ***/

		//Initialize ResultList
		//Builder class -> for construct objects
		resultList = new ArrayList<Builder>();	
		
		// ------------------------------------------------------------------------------------------------------------------
	
		//RETORNA NMERO DE REGISTROS POR PERIODO E DATAS SELECIONADAS	
		setNumRegisters(dta.RegistersNumbers(sosReport.getStartDate(), sosReport.getEndDate(), sosReport.getPeriod())); 
		
		// NÚMERO DE REGISTROS
					
	     setNumRegisters(numRegisters * sosReport.getEquipments().length);
		// setFieldsIndex(sosReport.getEquipments().length / numRegisters);
		    
		// System.out.println("REG: "+getNumRegisters()); 
							
		// ------------------------------------------------------------------------------------------------------------------	
						
		//CONTAGEM DOS DIAS
		if(sosReport.getStartDate() != null && sosReport.getEndDate() != null)
		daysCount = ((int) dta.diferencaDias(sosReport.getStartDate(), sosReport.getEndDate()) + 1);

		//INTERVALO POR PERIODO
		periodRange = dta.periodsRange(sosReport.getPeriod());	
		dateRange = sosReport.getEquipments().length;
			
		// ------------------------------------------------------------------------------------------------------------------	
		
		startDate = dta.StringDBDateFormat(sosReport.getStartDate());
		endDate = dta.StringDBDateFormat(sosReport.getEndDate());
	
		data_inicial = startDate;
		data_final = endDate;		
						
		start = dta.DateTimeToStringIni(startDate); 
		end = dta.DateTimeToStringFim(endDate); 
		
		//STARTS EQUIP WITH FIRST SELECTION
		equip_anterior = sosReport.getEquipments()[0];		
		
		// STARTS EQUIPS SHEET NAME
		equipName = new String[sosReport.getEquipments().length];

		/** TODO RELATORIO PASSA POR AQUI!!! **/

		//NUMERO DE CAMPOS PARA A SADA DE DADOS
		//LEVA EM CONSIDERAO NMERO DE CAMPOS DA QUERY
		setFieldsNumber(fieldsNumber());

		//SELECIONA UMA PROCEDURE DE ACORDO COM PERODO SELECIONADO
		//procedure = models.SelectProcedureByPeriod(sosReport.getPeriod());	
		
		resultQuery = new String[getNumRegisters()][getFieldsNumber()];	
		
	    //// NEW METHOD
		
		int minuto = 0;
		int iterator = 0;
		int pos = 0;
		int hr = 0;
 				
		int lin = 0;
		int col = 0;
		int p = 0;
		int empty = 0;
		int inc = 0;
		int index = 0;
		
		// Counter to RUN ARRAY
		int counter = daysCount * periodRange;
							
		//DATAS
	    dta.fillEquipName(listSOS, resultQuery, sosReport.getEquipments(), equipName, 0, 2, getNumRegisters(), periodRange, daysCount, startDate);
						
		//PERIODOS	
		if(sosReport.getPeriod().equals("05 minutes")) 		
			 dta.intervalo05Minutos(resultQuery, 1, getNumRegisters());	
										
		if(sosReport.getPeriod().equals("06 minutes"))	
		     dta.intervalo06Minutos(resultQuery, 1, getNumRegisters());
		   					
		if(sosReport.getPeriod().equals("10 minutes"))		
			dta.intervalo10Minutos(resultQuery, 1, getNumRegisters());
				   			
		if(sosReport.getPeriod().equals("15 minutes"))
		    dta.intervalo15Minutos(resultQuery, 1, getNumRegisters());	
			
		if(sosReport.getPeriod().equals("30 minutes"))	
			dta.intervalo30Min(resultQuery, 1, getNumRegisters());	
				   		        			
		if(sosReport.getPeriod().equals("01 hour")) 	
			dta.preencherHora(resultQuery, 1, getNumRegisters());
				
		if(sosReport.getPeriod().equals("06 hours")) 	
		    dta.intervalo06Horas(resultQuery, 1, getNumRegisters());
				
		 if(sosReport.getPeriod().equals("24 hours"))
		    dta.intervalo24Horas(resultQuery, 1, getNumRegisters());
		 	
		// HERE		
		for(int k = 0; k < sosReport.getEquipments().length; k++) {
		
		//SELECIONA UMA QUERY DE ACORDO COM TIPO SELECIONADO
		query = ExcuteQuery(models, sosModels, k);
				
	    //System.out.println(query); //debug

		//EXECUO DA QUERY
		String[][] auxResult = dao.ExecuteQuery(query, getNumRegisters(), getFieldsNumber());		
													
		//CASO EXISTA REGISTROS ENTRA AQUI
		if(auxResult.length > 0) {
									
		lin = counter;
		col = auxResult[0].length;
																	
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
					
		      // ------------------------------------------------------------------------------------ 
		      // TODA VEZ QUE FOR REALIZADA A LEITURA DE UM REGISTRO DO BANCO DE DADOS
		      // VERIFICA SE O REGISTRO É DIFERENTE DA DATA ANTERIOR
		
		     if(data_inicial != null) {
		    	 
		    	  iterator = dta.daysDifference(data_inicial, auxResult[j][0], periodRange);	
		    	 		     
		          pos += iterator;
		          
		          data_anterior = auxResult[j][0];			      	
		          data_inicial = null;		       		
			
		     } else if (!auxResult[j][0].equals(data_anterior) && auxResult[j][2].equals(equip_anterior)) {				
		   											
			    // CASO SEJA DIFERENTE PEGA-SE A DIFERENÇA DE DIAS
			    // DATA ANTERIOR, DATA QUE VEM DA BASE DE DADOS E INTERVALO DO PERIODO
			    iterator = dta.daysDifference(data_anterior, auxResult[j][0], periodRange);	
									    
			    pos += iterator;	   				
						    			  										
				// ADICIONA-SE A DATA ANTERIOR A DATA DO REGISTRO ATUAL
				data_anterior = auxResult[j][0];
																																				
			 }		     
			 
			// ------------------------------------------------------------------------------------
			 					 
		     if(!auxResult[j][2].equals(equip_anterior)) {
		    	 		    					
				 // INCREMENT RANGE
				 inc += periodRange; //ERROR
				 
				 iterator = dta.daysDifference(data_anterior, data_final, periodRange);	
				 
				 pos+= iterator;
				 							 
				 data_anterior = startDate;
				 equip_anterior = auxResult[j][2];
				 			    
			}
										 			 						
			// ------------------------------------------------------------------------------------ 
			
			 if(sosReport.getPeriod().equals("05 minutes"))	{
				 p = dta.index05Minutes(hr, minuto);
				 index = p + pos + inc;
			 }
			 else if(sosReport.getPeriod().equals("06 minutes")) {	
					 p = dta.index06Minutes(hr, minuto);
					 index = p + pos + inc;
			 }
			 else if(sosReport.getPeriod().equals("10 minutes")) {
			    	 p = dta.index10Minutes(hr, minuto);
			    	 index = p + pos + inc;
			 }
			 else if(sosReport.getPeriod().equals("15 minutes")) {	
					 p = dta.index15Minutes(hr, minuto);
			         index = p + pos + inc;
							
			 }
			 else if(sosReport.getPeriod().equals("30 minutes")) {	
					 p = dta.index30Minutes(hr, minuto);
					 index = p + pos + inc;
			 }
			 
			 else if(sosReport.getPeriod().equals("01 hour"))				
				index = pos + hr + inc;
						
			else if(sosReport.getPeriod().equals("06 hours")) {
				
				p = dta.index06Hours(hr);				
				index = pos + p + inc;
				
			}
			
			else if(sosReport.getPeriod().equals("24 hours"))
				     index = pos + inc;
			 					 			 								 
			if(i > 2 ) 
			    resultQuery[index][i] = auxResult[j][i];	
			
		    }		
		 }
		   
		   index++; // Index Increment
	   }	
		
		// CASO CONTRARIO ENTRA AQUI
			
		} else {
						
			pos += lin;
			empty++;
			
		}			
				  			     
		} // THE END LOOP
		
		if(empty < sosReport.getEquipments().length) {
						
			//SADA PARA A TABELA
			OutPutResult();
						
			//SAIDA DO EXCEL
			ExcelOutPut(model);

			//BOTO DE LIMPAR 
			setClearBool(false);

			//LINK DE DOWNLOAD DO EXCEL
			setExcelBool(false);	
																																		
			//UPDATE RESET BUTTON
			RequestContext.getCurrentInstance().update("form-btns:#btn-tab-reset");

			//UPDATE BUTTON GENERATE EXCEL
			RequestContext.getCurrentInstance().update("form-excel:#excel-act");	
						
			//UPDATE TABLE JQUERY ON RELOAD PAGE
			RequestContext.getCurrentInstance().execute("drawTable('#"+jsTableId+"', '"+jsTableScrollHeight+"');");	
			
		}
		
		else {			
		    
	          //EXECUTE JS
			  RequestContext.getCurrentInstance().execute("hideMessage();");
			  
			  //UPDATE TABLE JQUERY ON RELOAD PAGE
			  RequestContext.getCurrentInstance().execute("drawTable('#"+jsTableId+"', '"+jsTableScrollHeight+"'); showMessage();");	
						
		}
			
	}
	
	//// BUILD REPORTS
			
	/**********************************************************************************************************/

	///////////////////////////////////
	//QUERY FOR METHODS
	///////////////////////////////// 
	
	/////// BUILD QUERY MODEL 
	
	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
			/**
			 * @author Wellington 20/08/2021
			 * Método para criar montar uma query a ser executada 
			 * @param models - Objeto do tipo QuerieReportsModels
			 * @param mainQuery - Query principal a ser adicionada
			 * @param index - Index a ser usada na query
			 * @return
			 */							
				public String ExcuteQuery(SOSQueryModels models, SOSMainQueries sosModel , int index) throws Exception {  

				String query = null;

				query = models.BuildSOSQueryBase(models.SOSCallsHeader(sosReport.getPeriod()), sosModel.Statitictics(), models.SOSCallsFromTable(), models.UseIndex(QueriesReportsModels.USE_INDEX_IDX_SOS),
						models.WhereClauseSOSCalls(start, end, sosReport.getEquipments()[index]),  models.SOSCallsQueryGroupAndOrderBy(sosReport.getPeriod()));
				
				return query;
				
			}		
	

	// -------------------------------------------------------------------------------------------------------------------------------------


	///////////////////////////////////
	//SAIDA DE DADOS
	/////////////////////////////////  
	
	
    /////// SAIDA DE DADOS -/- FRONT-END

	/**
	 * @author Wellington 10/09/2020
	 * Mtodo para gerenciar construtores a fim de criar saidas de dados dinamicamente.
	 * @param type - Tipo do sosReport (Define qual  a sua finalidade)
	 */

	//PREENCHER SAIDA DE DADOS 
	// POR TIPO DE RELATRIO
	public void OutPutResult() {  

		//ACESSAR DADOS DO RELATRIOF
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		//FIELDS EXTERNOS ARMAZENADOS NA REQUISIO
		fields = (String[]) externalContext.getSessionMap().get("fields");
		fieldObjectValues =  (String[]) externalContext.getSessionMap().get("fieldsObject");
			
		   /**** SPEED MULT ****/			  		 

			//SPEED CONSTRUCTOR
		    SOSStatisticBuilder();
		 		  

	  } 

	/**********************************************************************************************************/
	
	///////////////////////////////////
	//SAIDA DE DADOS
	/////////////////////////////////      

	/////// FIELDS NUMBER - SADA DE DADO

	public Integer fieldsNumber() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		int length = (int) externalContext.getSessionMap().get("fieldsLength");

		int fields = length;   
				    	
		return fields;    	 
	}

     /////// FIELDS NUMBER - SADA DE DADO 

	/**********************************************************************************************************/

   /////// SAIDA DO EXCEL

	public void ExcelOutPut(ExcelModels model) throws IOException, Exception {

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
		
		// GET EQUIPMENT INFORMATIONS TO EXCEL FILE
        EquipmentsDAO dao = new EquipmentsDAO();
        List<SOS> sosInfo = new ArrayList<SOS>();
        
        // INFORMATION LIST
        sosInfo = dao.listSOStoXLS(sosReport.getEquipments());			
		
		//Define fileName
		fileName = localeLabel.getStringKey("sos_excel_file_name_statistic")+tm.periodName(sosReport.getPeriod()); // File Name
		String title = localeLabel.getStringKey("sos_excel_file_title_statistic"); // File Title
		String sheetName = localeLabel.getStringKey("sos_excel_file_sheet_name_statistic"); // File sheetName

		countMergeHeader = new String[] {"A1:B4", "C1:H4", "I1:J4"}; // Merge cells

		col = new int[] {3500, 3500, 4250, 3500, 3500, 3500, 3500, 3500, 4500, 3500}; // Col size 

		colStartDate = 8; colEndDate = 9; // Col ini & end date
															
		model.StandardFonts(); // Fonts
		model.StandardStyles(); // Styles
		model.StandardBorders(); // Borders
		
		//Standard Excel Model
		model.StandardEquipSOSExcelModel(fields, getNumRegisters(), equipName, periodRange, daysCount, sosReport.getPeriod(), dta.currentTime(), module,  				  
			RoadConcessionaire.externalImagePath, title, sheetName, sosInfo, sosReport.getStartDate(), sosReport.getEndDate(), countMergeHeader, 
			col, colStartDate, colEndDate, resultQuery);
				
		// Define Values in session map !important
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

     /////// RESET FORM VALUES
	
	//Form Reset
	public void resetFormValues() {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();	
		ExternalContext externalContext = facesContext.getExternalContext();

		//Reset object => call on click reset button
		sosReport = new SOSReports();

		jsonFields = new String[1];
		jsonArray = new String[1][1];
			
		externalContext.getSessionMap().remove("xlsModel");
		externalContext.getSessionMap().remove("current");
		externalContext.getSessionMap().remove("fileName");		
		externalContext.getSessionMap().remove("fields");
		externalContext.getSessionMap().remove("jsonFields");
		externalContext.getSessionMap().remove("fieldsObject");

		// Fields again
		CreateFields();
		
	}

    /////// RESET FORM VALUES

	/**********************************************************************************************************/
	
	///////////////////////////////////
	//CONSTRUCTORS FOR RESULTLIST
	/////////////////////////////////  
   
	  // SOS STATISTIC Builder
	
			public void SOSStatisticBuilder() {

				for(int k = 0; k < getNumRegisters(); k++) {      

					resultList.add(new SOSReports.Builder().date(resultQuery[k][0]) 
							.time(resultQuery[k][1])	
							.equipment(resultQuery[k][2])	
							.callReceivedAmount(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))					
							.callAnsweredAmount(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))
							.callEndedAmount(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
							.callMissedAmount(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6]))	
							.callErrorAmount(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))	
							.callDuration(resultQuery[k][8] == null ? "00:00:00" : resultQuery[k][8]));						 			 
				   } 
			  }
			
	// -------------------------------------------------------------------------------------------------------------------------
	
	


}
