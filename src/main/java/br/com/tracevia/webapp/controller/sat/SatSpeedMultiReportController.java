package br.com.tracevia.webapp.controller.sat;

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
import br.com.tracevia.webapp.dao.sat.SatQueriesModels;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.ExcelModels;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.ColumnModel;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.sat.SAT;
import br.com.tracevia.webapp.model.sat.SatReports;
import br.com.tracevia.webapp.model.sat.SatReports.Builder;
import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.QueriesReportsModels;

@ManagedBean(name="satSpeedMultiBean")
@RequestScoped
public class SatSpeedMultiReportController {

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
	private List<String> header;  
	private List<ColumnModel> columns;  
	List<? extends Equipments> listSats;  

	//Locale Docs
	LocaleUtil localeLabel, localeCalendar, localeDir, localeSat;  

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
		SatSpeedMultiReportController.numRegisters = numRegisters;
	}

	public static int getFieldsNumber() {
		return fieldsNumber;
	}

	public static void setFieldsNumber(int fieldsNumber) {
		SatSpeedMultiReportController.fieldsNumber = fieldsNumber;
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
		SatSpeedMultiReportController.fieldsIndex = fieldsIndex;
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

		/* EQUIPMENTS SELECTION */	
		satReport = new SatReports();	
		equipments = new ArrayList<SelectItem>();		

		listSats = new ArrayList<SAT>();  

		try {

			EquipmentsDAO dao = new EquipmentsDAO();		 
			listSats = dao.equipmentSelectOptions("sat");

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
	
		/**** VELOCIDADE MULTIPLA ****/
	
			// Table fields
			fields = new String[] {localeLabel.getStringKey("sat_reports_general_date"), localeLabel.getStringKey("sat_reports_general_datetime"),
					localeLabel.getStringKey("sat_reports_general_equipment"), localeLabel.getStringKey("sat_reports_speed_50km"), localeLabel.getStringKey("sat_reports_speed_70km"),
					localeLabel.getStringKey("sat_reports_speed_90km"), localeLabel.getStringKey("sat_reports_speed_120km"), 	   
					localeLabel.getStringKey("sat_reports_speed_150km"), localeLabel.getStringKey("sat_reports_speed_150km_bigger"), 
					localeLabel.getStringKey("sat_reports_general_total") };
			
			// Table Objects
			fieldObjectValues = new String[] {"date", "dateTime", "equipment", "speed50km", "speed70km", "speed90km", "speed120km", "speed150km", "speed150km_bigger", "total"};
		
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

		QueriesReportsModels models = new QueriesReportsModels(); //QuerieReportsModel class
		SatQueriesModels satModels = new SatQueriesModels(); // SatReportsModel class	    
		DateTimeApplication dta = new DateTimeApplication(); //DateTimeApsplication class

		GlobalReportsDAO dao = new GlobalReportsDAO();	//GlobalReportsDAO
				
		String startDate = null, endDate = null, data_last_equip = null, data_anterior = null, data_inicial = "", data_final = "";
				
		String type = "13";
					
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
		
		// ------------------------------------------------------------------------------------------------------------------
	
		//RETORNA NMERO DE REGISTROS POR PERIODO E DATAS SELECIONADAS	
		setNumRegisters(dta.RegistersNumbers(satReport.getStartDate(), satReport.getEndDate(), satReport.getPeriod())); 
		
		// NÚMERO DE REGISTROS
					
	     setNumRegisters(numRegisters * satReport.getEquipments().length);
		// setFieldsIndex(satReport.getEquipments().length / numRegisters);
		    
		// System.out.println(getNumRegisters()); 
							
		// ------------------------------------------------------------------------------------------------------------------	
						
		//CONTAGEM DOS DIAS
		if(satReport.getStartDate() != null && satReport.getEndDate() != null)
		daysCount = ((int) dta.diferencaDias(satReport.getStartDate(), satReport.getEndDate()) + 1);

		//INTERVALO POR PERIODO
		periodRange = dta.periodsRange(satReport.getPeriod());	
		dateRange = satReport.getEquipments().length;
			
		// ------------------------------------------------------------------------------------------------------------------	
		
		startDate = dta.StringDBDateFormat(satReport.getStartDate());
		endDate = dta.StringDBDateFormat(satReport.getEndDate());
	
		data_inicial = startDate;
		data_final = endDate;		
						
		start = dta.DateTimeToStringIni(startDate); 
		end = dta.DateTimeToStringFim(endDate); 
		
		//STARTS EQUIP WITH FIRST SELECTION
		equip_anterior = satReport.getEquipments()[0];		
		
		// STARTS EQUIPS SHEET NAME
		equipName = new String[satReport.getEquipments().length];

		/** TODO RELATORIO PASSA POR AQUI!!! **/

		//NUMERO DE CAMPOS PARA A SADA DE DADOS
		//LEVA EM CONSIDERAO NMERO DE CAMPOS DA QUERY
		setFieldsNumber(fieldsNumber());

		//SELECIONA UMA PROCEDURE DE ACORDO COM PERODO SELECIONADO
		//procedure = models.SelectProcedureByPeriod(satReport.getPeriod());	
		
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
	    dta.fillEquipName(listSats, resultQuery, satReport.getEquipments(), equipName, 0, 2, getNumRegisters(), periodRange, daysCount, startDate);
						
		//PERIODOS	
		if(satReport.getPeriod().equals("05 minutes")) 		
			 dta.intervalo05Minutos(resultQuery, 1, getNumRegisters());	
										
		if(satReport.getPeriod().equals("06 minutes"))	
		     dta.intervalo06Minutos(resultQuery, 1, getNumRegisters());
		   					
		if(satReport.getPeriod().equals("10 minutes"))		
			dta.intervalo10Minutos(resultQuery, 1, getNumRegisters());
				   			
		if(satReport.getPeriod().equals("15 minutes"))
		    dta.intervalo15Minutos(resultQuery, 1, getNumRegisters());	
			
		if(satReport.getPeriod().equals("30 minutes"))	
			dta.intervalo30Min(resultQuery, 1, getNumRegisters());	
				   		        			
		if(satReport.getPeriod().equals("01 hour")) 	
			dta.preencherHora(resultQuery, 1, getNumRegisters());
				
		if(satReport.getPeriod().equals("06 hours")) 	
		    dta.intervalo06Horas(resultQuery, 1, getNumRegisters());
				
		 if(satReport.getPeriod().equals("24 hours"))
		    dta.intervalo24Horas(resultQuery, 1, getNumRegisters());
		 	
		//HERE		
		for(int k = 0; k < satReport.getEquipments().length; k++) {
		
		//SELECIONA UMA QUERY DE ACORDO COM TIPO SELECIONADO
		query = ExcuteQuery(models, satModels, k);
		
	    System.out.println(query); //debug

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
		
		if(satReport.getPeriod().equals("01 hour") || satReport.getPeriod().equals("06 hours"))
			   hr = Integer.parseInt(auxResult[j][1].substring(0, 2));
			
		else if(!satReport.getPeriod().equals("24 hours") && !satReport.getPeriod().equals("01 hour") && !satReport.getPeriod().equals("06 hours")) {
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
			
			 if(satReport.getPeriod().equals("05 minutes"))	{
				 p = dta.index05Minutes(hr, minuto);
				 index = p + pos + inc;
			 }
			 else if(satReport.getPeriod().equals("06 minutes")) {	
					 p = dta.index06Minutes(hr, minuto);
					 index = p + pos + inc;
			 }
			 else if(satReport.getPeriod().equals("10 minutes")) {
			    	 p = dta.index10Minutes(hr, minuto);
			    	 index = p + pos + inc;
			 }
			 else if(satReport.getPeriod().equals("15 minutes")) {	
					 p = dta.index15Minutes(hr, minuto);
			         index = p + pos + inc;
							
			 }
			 else if(satReport.getPeriod().equals("30 minutes")) {	
					 p = dta.index30Minutes(hr, minuto);
					 index = p + pos + inc;
			 }
			 
			 else if(satReport.getPeriod().equals("01 hour"))				
				index = pos + hr + inc;
						
			else if(satReport.getPeriod().equals("06 hours")) {
				
				p = dta.index06Hours(hr);				
				index = pos + p + inc;
				
			}
			
			else if(satReport.getPeriod().equals("24 hours"))
				     index = pos + inc;
			 					 			 								 
			if(i > 2 ) 
			    resultQuery[index][i] = auxResult[j][i];	
			
		    }			   
		 }		
		   
		   index++; // Index Increment
	   }		 								    					
			//CASO CONTRARIO ENTRA AQUI
			
		} else {
						
			pos += lin;
			empty++;
			
		}		
				
				  			     
		} // THE END LOOP
		
		if(empty < satReport.getEquipments().length) {
						
			//SADA PARA A TABELA
			OutPutResult();
						
			//SAIDA DO EXCEL
			ExcelOutPut(type, model);

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
	
	
	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
	/*** SPEED MULTIPLE ***/
	public String BuildMainQuerySpeed(QueriesReportsModels models, String mainQuery, String queryIndex, int index) {    	 

		String query = null;

		query = models.BuildQueryIndex(models.QueryHeader(satReport.getPeriod()), mainQuery, models.QueryFromSatTable(satReport.getPeriod(), RoadConcessionaire.tableCCR), models.useIndex(queryIndex), 
				models.whereClauseForSpeed(satReport.getEquipments()[index], start, end), models.vehicleSelectionWhereClause(satReport.vehicles), models.QuerySatGroupAndOrderMultiple(satReport.getPeriod()));
		
		return query;
	}
	
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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
	public String ExcuteQuery(QueriesReportsModels models, SatQueriesModels satModels, int index) throws Exception {  
		
		String query = BuildMainQuerySpeed(models, satModels.SpeedMultiMainQuery(satReport.getEquipments()[index]), QueriesReportsModels.USE_INDEX_IDX_SITEID_DATA, index); 
						
	    return query ;
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
	public void OutPutResult() {  

		//ACESSAR DADOS DO RELATRIOF
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		//FIELDS EXTERNOS ARMAZENADOS NA REQUISIO
		fields = (String[]) externalContext.getSessionMap().get("fields");
		fieldObjectValues =  (String[]) externalContext.getSessionMap().get("fieldsObject");
			
		   /**** SPEED MULT ****/			  		 

			//SPEED CONSTRUCTOR
			speedMultiBuilder();

			  

	  } 

	/**********************************************************************************************************/

	///////////////////////////////////
	//DIRECTIONS METHODS
	/////////////////////////////////  

	//CARREGAR DIREES DB
	public void loadDirections() throws Exception{			

		equipDAO  = new EquipmentsDAO();

		//String dir1 = equipDAO.firstDirection(equipId);  

		//popDirections(dir1);			

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
		
		// GET EQUIPMENT INFORMATIONS TO EXCEL FILE
        EquipmentsDAO dao = new EquipmentsDAO();
        List<SAT> satInfo = new ArrayList<SAT>();
        
        // INFORMATION LIST
       // satInfo = dao.listSATtoXLS(satReport.getEquipments());
				
		//Define name
		fileName = localeLabel.getStringKey("excel_report_speed_file")+tm.periodName(satReport.getPeriod());
		excel_title = localeLabel.getStringKey("excel_report_speed_title"); // Excel title

		countMergeHeader = new String[] {"A1:B4", "C1:H4", "I1:J4"}; // Merge cells

		col = new int[] {3500, 3500, 4250, 3500, 3500, 3500, 3500, 3500, 4500, 3500}; // Col size 

		colStartDate = 8; colEndDate = 9; // Col ini & end date
															
		model.StandardFonts(); // Fonts
		model.StandardStyles(); // Styles
		model.StandardBorders(); // Borders
		
		//Standard Excel Model
		model.StandardEquipExcelModel(fields, getNumRegisters(), equipName, periodRange, daysCount, satReport.getPeriod(), dta.currentTime(), type, module,  				  
			RoadConcessionaire.externalImagePath, excel_title, satInfo, satReport.getStartDate(), satReport.getEndDate(), countMergeHeader, 
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
		satReport = new SatReports();

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
   
	public void speedMultiBuilder() {

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

	///////////////////////////////////
	//CONSTRUCTORS FOR RESULTLIST
	/////////////////////////////////  
	
	/**********************************************************************************************************/
	/**********************************************************************************************************/
	


}
