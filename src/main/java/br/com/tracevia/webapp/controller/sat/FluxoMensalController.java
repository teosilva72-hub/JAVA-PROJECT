package br.com.tracevia.webapp.controller.sat;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
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
import br.com.tracevia.webapp.dao.sat.SatQueriesModels;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.ExcelModels;
import br.com.tracevia.webapp.model.global.ColumnModel;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.sat.SAT;
import br.com.tracevia.webapp.model.sat.SatReports;
import br.com.tracevia.webapp.model.sat.SatReports.Builder;
import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.QueriesReportsModels;

@ManagedBean(name="fluxoMensalBean")
@RequestScoped
public class FluxoMensalController {

	private SatReports satReport;
	private List<SelectItem> equipments;  
	private List<SelectItem> months;  
	private List<SelectItem> years;	
	private List<Builder> resultList;
	private List<String> header;  
	private List<ColumnModel> columns; 
	
	List<? extends Equipments> listSats;  

	//Locale Docs
	LocaleUtil localeLabel, localeCalendar, localeDir, localeSat;  

	int periodRange, daysInMonth, daysCount; 

	String jsTableId, jsTableScrollHeight;

	// Varivel que recebe o nmero de registros esperados para uma consula SQL (de acordo com perodos)
	private static int numRegisters;	

	// Varivel que recebe o nmero de campos de uma consulta SQL
	private static int fieldsNumber;	

	String[] fields, jsonFields, fieldObjectValues, fieldsAux, fieldObjAux; //Nome dos campos // Valores de cada campo -> Atribuidos a variavis do modelo  

	String[][] resultQuery, jsonArray; 

	String query, queryCount, module, fileName, currentDate, direction1, direction2, directionLabel1, directionLabel2, 
	directionValue1, directionValue2, equipId; 
	
	int[] motoACM1, autoACM1, comACM1, motoACM2, autoACM2, comACM2, speedACM1, speedACM2,
	totalACM1, totalACM2, fluxo1, fluxo2, fluxoACM1, fluxoACM2, total, total1, total2 ;


	//Dates
	String start, end;

	BufferedWriter writer;  
	ByteArrayOutputStream byteWriter; 

	EquipmentsDAO equipDAO;
	ExcelModels model;

	String displayEquipInfo, displayDirection1, displayDirection2;

	private boolean clearBool, excelBool, chartBool;

	public SatReports getSatReport() {
		return satReport;
	}

	public void setSatReport(SatReports satReport) {
		this.satReport = satReport;
	}

	public List<SelectItem> getEquipments() {
		return equipments;
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
		FluxoMensalController.numRegisters = numRegisters;
	}

	public static int getFieldsNumber() {
		return fieldsNumber;
	}

	public static void setFieldsNumber(int fieldsNumber) {
		FluxoMensalController.fieldsNumber = fieldsNumber;
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

	public void CreateField() {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		// Table fields
		fields = new String[] {localeLabel.getStringKey("sat_reports_general_date"), localeLabel.getStringKey("sat_reports_general_datetime"), 
				localeLabel.getStringKey("sat_reports_monthly_flow_motoVehicles"), localeLabel.getStringKey("sat_reports_monthly_flow_lightVehicles"), 
				localeLabel.getStringKey("sat_reports_monthly_flow_commercials"), localeLabel.getStringKey("sat_reports_monthly_flow_motoVehicles"),
				localeLabel.getStringKey("sat_reports_monthly_flow_lightVehicles"), localeLabel.getStringKey("sat_reports_monthly_flow_commercials"),
				localeLabel.getStringKey("sat_reports_monthly_flow_dir1"), localeLabel.getStringKey("sat_reports_monthly_flow_dir2")};

		// Table Objects
		fieldObjectValues = new String[] { "date", "dateTime", "motoDir1", "heavyDir1", "lightDir1", "motoDir2", "heavyDir2", "lightDir2", "speedValue1", "speedValue2" };

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
		// resetFormValues(type);

		//Get external application contents
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		QueriesReportsModels models = new QueriesReportsModels(); //QuerieReportsModel class
		SatQueriesModels satModels = new SatQueriesModels(); // SatReportsModel class	    
		DateTimeApplication dta = new DateTimeApplication(); //DateTimeApsplication class

		GlobalReportsDAO dao = new GlobalReportsDAO();	//GlobalReportsDAO

		String startDate = null, endDate = null, data_anterior = null;


		/*** Obter parmetros que vem no submit de cada pesquisa ***/
	
		//Get Request single values
		Map<String, String> parameterMap2 = (Map<String, String>) externalContext.getRequestParameterMap();


		/**** Single Params ***/

		//param for equipment
		satReport.setEquipment(parameterMap2.get("equip"));

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

		
			// QUANTOS DIAS POSSUI O RESPECTIVO MS
			YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(satReport.getYear()), Integer.parseInt(satReport.getMonth()));					
			daysInMonth = yearMonthObject.lengthOfMonth();

			//INSERIR VALORES DATAS DE INICIO E FIM
			satReport.setStartDate("01/"+satReport.getMonth()+"/"+satReport.getYear());
			satReport.setEndDate(daysInMonth+"/"+satReport.getMonth()+"/"+satReport.getYear());

			//INTERVALO POR PERIODO
			periodRange = dta.periodsRange(satReport.getPeriod());

			//NMERO DE REGISTROS PARA A SADA DE DADOS			
			setNumRegisters((daysInMonth * periodRange)); 				


		startDate = dta.StringDBDateFormat(satReport.getStartDate());
		endDate = dta.StringDBDateFormat(satReport.getEndDate());
		data_anterior = startDate;

		start = dta.DateTimeToStringIni(startDate); 
		end = dta.DateTimeToStringFim(endDate); 

		/** TODO RELATRIO PASSA POR AQUI!!! **/

		//NUMERO DE CAMPOS PARA A SADA DE DADOS
		//LEVA EM CONSIDERAO NMERO DE CAMPOS DA QUERY
		setFieldsNumber(fieldsNumber());

		//SELECIONA UMA PROCEDURE DE ACORDO COM PERODO SELECIONADO
		//procedure = models.SelectProcedureByPeriod(satReport.getPeriod());	

		resultQuery = new String[getNumRegisters()][getFieldsNumber() + 16];	

		//SELECIONA UMA QUERY DE ACORDO COM TIPO SELECIONADO
		query = buildQuery(models, satModels);

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
	
			lin = auxResult.length;
			col = auxResult[0].length;

			//DATAS
			dta.preencherDataPorPeriodo(resultQuery, 0, getNumRegisters(),  periodRange, startDate); 			
			dta.intervalo15Minutos(resultQuery, 1, getNumRegisters());	
						

			for(int j = 0; j < lin; j++) {
				for(int i = 0; i < col; i++) {

					// CASO NO EXISTA VALOR >>>>>>> PASSA	   
					if(auxResult[j][0] != null)	 {  

							hr = Integer.parseInt(auxResult[j][1].substring(0, 2));
							minuto =  Integer.parseInt(auxResult[j][1].substring(3, 5));	

						// Restrio caso no haja dados nos primeiros registros
						if ((startDate != null) && (!auxResult[j][0].equals(startDate))) {   // Executa uma unica vez

							if(satReport.getPeriod().equals("24 hours"))
								iterator = (int) dta.daysDifference(startDate, auxResult[j][0]);

							else iterator = dta.daysDifference(startDate, auxResult[j][0], periodRange);	

							pos+= iterator;
							startDate = null;

						} else if (!auxResult[j][0].equals(data_anterior)) {								

							if(satReport.getPeriod().equals("24 hours"))
								iterator = (int) dta.daysDifference(data_anterior, auxResult[j][0]);

							else iterator = dta.daysDifference(data_anterior, auxResult[j][0], periodRange);	

							pos+= iterator;							
						} 			

						  data_anterior = auxResult[j][0];
						
							p = dta.index15Minutes(hr, minuto);
							p = p + pos;
													
						if(i > 1 )
							resultQuery[p][i] = auxResult[j][i];


					} // CASO NO EXISTA VALOR >>>>>>> PASSA
				}
			}

			//// NEW METHOD

			//SADA PARA A TABELA
			OutPutResult();

			//SADA DO EXCEL
	

			//BOTO DE LIMPAR 
			setClearBool(false);

			//LINK DE DOWNLOAD DO EXCEL
			setExcelBool(false);	

			//LINK PARA ACESSAR O GRÁFICO
			setChartBool(false);

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

	/////// FIELDS NUMBER - SADA DE DADO

	public Integer fieldsNumber() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		int length = (int) externalContext.getSessionMap().get("fieldsLength");

		int fields = (length);   
	
		return fields;    	 
	}
	
	/**********************************************************************************************************/
	
	
	/*** TO VBV USE WHERE CLAUSE WITH TWO PARAMETERS ***/
	public String BuildMainQuery(QueriesReportsModels models, String mainQuery) {  
		
		String query = null;
	
		query = models.BuildQueryType2(models.QueryHeader(satReport.getPeriod()), mainQuery, models.QueryFromSatTable(satReport.getPeriod(), RoadConcessionaire.tableCCR),
				models.innerJoinSat(), models.whereClauseDate(start, end), models.QuerySatGroupAndOrder(satReport.getPeriod()));

		return query;
	}
	
	  /////// CONSTRUO DA QUERY

		/**
		 * Mtodo que retorna um query especfica de acordo com tipo
		 * @param type - Tipo de especfico do relatrio
		 * @param models - Objeto do tipo QueriesReportsModels
		 * @param satModels - Objeto do tipo SatQueriesModels
		 * @return query
		 * @throws Exception 
		 */
		public String buildQuery(QueriesReportsModels models, SatQueriesModels satModels) throws Exception {   

			int lanes = 0;
				
			 equipDAO = new EquipmentsDAO(); 
			 
			 lanes = equipDAO.EquipmentSelectLanesNumber("sat", satReport.getEquipment()); 
			 		
			return  BuildMainQuery(models, satModels.MonthlyFlowMainQuery(satReport.getEquipment(), lanes)); 

		}
		
		/**********************************************************************************************************/
		
		
		//PREENCHER SAIDA DE DADOS 
		// POR TIPO DE RELATRIO
		public void OutPutResult() {  

			//ACESSAR DADOS DO RELATRIOF
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();

			//FIELDS EXTERNOS ARMAZENADOS NA REQUISIO
			fields = (String[]) externalContext.getSessionMap().get("fields");
			fieldObjectValues =  (String[]) externalContext.getSessionMap().get("fieldsObject");
		
				// FLUXO MENSAL CONSTRUCTOR
				monthFlowBuilder();

				// DRAW TABLE -- BUILD HEADER
				drawTable(fields, fieldObjectValues);
		

		} 

		 /////// SAIDA DE DADOS -/- FRONT-END
		
		/**********************************************************************************************************/
		
		/* MONTH FLOW -- TYPE = 3 */      

		public void monthFlowBuilder() {

			for(int k = 0; k < getNumRegisters(); k++) {      

				resultList.add(new SatReports.Builder().date(resultQuery[k][0]) 
						.dateTime(resultQuery[k][1])		 					            
						.lightDir1(resultQuery[k][2] == null? 0 : Integer.parseInt(resultQuery[k][2]))
						.heavyDir1(resultQuery[k][3] == null? 0 : Integer.parseInt(resultQuery[k][3]))
						.motosDir1(resultQuery[k][4] == null? 0 : Integer.parseInt(resultQuery[k][4]))	 		 				                
						.lightDir2(resultQuery[k][5] == null? 0 : Integer.parseInt(resultQuery[k][5]))
						.heavyDir2(resultQuery[k][6] == null? 0 : Integer.parseInt(resultQuery[k][6])) 
						.motosDir2(resultQuery[k][7] == null? 0 : Integer.parseInt(resultQuery[k][7]))
						.speed1(resultQuery[k][8] == null? 0 : Integer.parseInt(resultQuery[k][8]))
						.speed2(resultQuery[k][9] == null? 0 : Integer.parseInt(resultQuery[k][9])));	
			}             

		}
		
		/**********************************************************************************************************/

		   /////// SAIDA DO EXCEL

			public void ExcelOutPut(String matriz[][], ExcelModels model) throws IOException, Exception {

			

		   }

	
}
