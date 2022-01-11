package br.com.tracevia.webapp.controller.sat;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PropertyTemplate;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.sat.FluxoPeriodoDAO;
import br.com.tracevia.webapp.log.SystemLog;
import br.com.tracevia.webapp.methods.DateMethods;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.ExcelModels;
import br.com.tracevia.webapp.model.global.ColumnModel;
import br.com.tracevia.webapp.model.global.ListEquipments;
import br.com.tracevia.webapp.model.global.ReportBuild;
import br.com.tracevia.webapp.model.sat.FluxoPeriodo;
import br.com.tracevia.webapp.model.sat.FluxoPeriodo.Builder;
import br.com.tracevia.webapp.model.sat.SAT;
import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.SessionUtil;

@ManagedBean(name="fluxoPeriodo")
@RequestScoped
public class FluxoPeriodoBean {
	
	    // --------------------------------------------------------------------------------------------------------------
	
		private final String errorFolder = SystemLog.ERROR.concat("period-flow-bean\\");
				
		// --------------------------------------------------------------------------------------------------------------
			
		private List<SelectItem> equipments;
		private List<SelectItem> periods;
		private List<SelectItem> months;
		private List<SelectItem> years;
		private List<ColumnModel> columns;  			
		private List<Builder> resultList;	
		private List<FluxoPeriodo> lista;
		
		private SXSSFWorkbook workbook;	
		private SXSSFSheet sheet;
		private SXSSFRow rowDados;
		private PropertyTemplate propertyTemplate;
		
		private ReportBuild build;
		
		LocaleUtil localeSat, localeDir, localeReports, localeSheet, localeCalendar;
			
		int minLen, daysInMonth;
		
		SAT sat;
		
		String[] equips;

		String period, month, year, displayMessage = "FOOL";
		
		// --------------------------------------------------------------------------------------------------------------
		
		private Date date;		
		private int sixHoursRows;
		private String[] days;
		private int mes;
		private int ano;
		private int tamanho;
		private int tam;
		private int hora_anterior;		
		private String startDate; 
		private String endDate;
		private String data_anterior;	
		private String nomeSat;
		private String estradaSat;  
		private String kmSat;
		private String yearABR;
		private String  monthABR;
		private String displayImage;
		private String displayMessage2;
		private String displayImage2;
		private String imagem, imagem2;
		private String mensagem, mensagem2;
		private int rows;
		private int flag;
		private boolean stop;
		private String satName;
		private String road; 
		private String kilometer;
		private String faixa1;
		private int numFaixas;
		
		FluxoPeriodo per;
		FluxoPeriodoDAO dao;		
		DateMethods dtm;
		
		EquipmentsDAO equipDao;

		String[] dataHora, siteNames, dataIntervalo, data_excel, hora_excel, data_cabecalho,
		diasx, excel_hora, satNames, sentido1, sentido2, sentidoExcel1, sentidoExcel2, nomeSats;

		int[] equip1, equip2, equip3, equip4, equip5, equip6, equip7, equip8, equip9, equip10, equip11, equip12, equip13,
		equip14, equip15, equip16, equip17, equip18, equip19, equip20, equip21, equip22, equip23, equip24, equip25,
		total;

		int[] auto1, com1, moto1, total1, autoVM1, comVM1, motoVM1, totalVM1,
		autoV501, comV501, motoV501, totalV501, autoV851, comV851, motoV851, totalV851, autoMAX1, 
		comMAX1, motoMAX1, totalMAX1, autoMIN1, comMIN1, motoMIN1, totalMIN1, autoVDSP1, comVDSP1,
		motoVDSP1, totalVDSP1, speedAVG1;

		int[] auto2, com2, moto2, total2, autoVM2, comVM2, motoVM2, totalVM2,
		autoV502, comV502, motoV502, totalV502,autoV852, comV852, motoV852, totalV852, autoMAX2, 
		comMAX2, motoMAX2, totalMAX2, autoMIN2, comMIN2, motoMIN2, totalMIN2, autoVDSP2, comVDSP2,  
		motoVDSP2, totalVDSP2, speedAVG2;

		Cell numbers, dates, horaInicio, horaSeparator, horaFim, autoS1, comS1,	motoS1,	totalS1, autoS2, comS2,	motoS2,	totalS2, autoVMS1,
		comVMS1, motoVMS1, totalVMS1, autoVMS2,	comVMS2, motoVMS2, totalVMS2, autoV50S1, comV50S1, motoV50S1,
		totalV50S1, autoV50S2, comV50S2, motoV50S2, totalV50S2, autoV85S1,	comV85S1, motoV85S1, totalV85S1,
		autoV85S2, comV85S2, motoV85S2,	totalV85S2,	autoVMAXS1,	comVMAXS1,	motoVMAXS1,	totalVMAXS1, autoVMAXS2,
		comVMAXS2, motoVMAXS2, totalVMAXS2, autoVMINS1, comVMINS1, motoVMINS1, totalVMINS1,	autoVMINS2,	comVMINS2,
		motoVMINS2,	totalVMINS2, autoVDSPS1, comVDSPS1,	motoVDSPS1,	totalVDSPS1, autoVDSPS2, comVDSPS2,	motoVDSPS2,
		totalVDSPS2;

		CellStyle subheader, dayStyle, espacoStyle,	espacoStyle2, backgroundColorBodyHeader, backgroundColorBodyHeader2,
		blueColorBackground, redColorBackground, numbersTitle, reportTime, subHeader, intervaloStyle, style1, blankStyle,
		blankStyle2; 

		Font fontDataHeader, fontEspaco2, fontEspaco, fontBodyHeader2, fontBodyHeader, fontBackgroundColor, fontNumbersTitle,
		fontReportTime, fontSubHeader, fontBody, fontIntervalo;

		int veiculos;
		int dia_anterior;
		int daysInt;
		int maxDay;
		int maxInterval;
		int maxHour;
		int length;
		int indice;
		int periodRange;
		int index; 
		int hr, minuto, interResp, ultimo_equipamento;		
		int rangeHour, rangeInterval, pos, hrPos, creation, fill, increment, incEquip; 
		String dtInicio;

		String[] intervalo,interInicio, interFim, separador, data, horaIntervalo, hora, sheetName;

		long start, end, elapsed;

		boolean pause;
		
		@ManagedProperty("#{listEquips}")
		private ListEquipments listEquips;
					
		public ListEquipments getListEquips() {
			return listEquips;
		}
		
		public void setListEquips(ListEquipments listEquips) {
			this.listEquips = listEquips;
		}
			
		public ReportBuild getBuild() {
			return build;
		}

		public void setBuild(ReportBuild build) {
			this.build = build;
		}
		
		public List<SelectItem> getEquipments() {
			return equipments;
		}
		
		public List<SelectItem> getMonths() {
			return months;
		}
			
		public List<SelectItem> getPeriods() {
			return periods;
		}

		public List<SelectItem> getYears() {
			return years;
		}
		
		public List<ColumnModel> getColumns() {
			return columns;
		}
		
		public List<Builder> getResultList() {
			return resultList;
		}	
						
		public List<FluxoPeriodo> getLista() {
			return lista;
		}

		public String getDisplayMessage() {
			return displayMessage;
		}

		public void setDisplayMessage(String displayMessage) {
			this.displayMessage = displayMessage;
		}
		
		// ----------------------------------------------------------------------------------------------------------------------------------------------------- 

		@PostConstruct
		public void init() {
			
			localeDir = new LocaleUtil();
			localeDir.getResourceBundle(LocaleUtil.LABELS_DIRECTIONS);
			
			localeCalendar = new LocaleUtil();
			localeCalendar.getResourceBundle(LocaleUtil.LABELS_CALENDAR);
			
			localeSat = new LocaleUtil();
			localeSat.getResourceBundle(LocaleUtil.LABELS_SAT);
			
			localeReports = new LocaleUtil();
			localeReports.getResourceBundle(LocaleUtil.LABELS_REPORTS);
			
			localeSheet = new LocaleUtil();		
			localeSheet.getResourceBundle(LocaleUtil.LABELS_EXCELSHEET);
							
		    build = new ReportBuild();
			
			equipments = new ArrayList<SelectItem>();
			months = new ArrayList<SelectItem>();
			years = new ArrayList<SelectItem>();
			
			build.clearBool = true;
			build.excelBool = true;
			build.closeBool = true;
			
			try {
				
				    // SELECT FIELD VALUES
			
					equipments = build.selectEquips(listEquips.getSatList());
					periods = build.selectBasicPeriods();
					months = build.selectMonth();
					years = build.selectYears();
		
				} catch (Exception ex) {
			
					StringWriter errors = new StringWriter();
					ex.printStackTrace(new PrintWriter(errors));
				
					SystemLog.logError(errorFolder.concat("error_select_item"), EquipmentsDAO.class.getCanonicalName(), ex.getMessage(), errors.toString());
				
				}	      
		   }
		
		// ----------------------------------------------------------------------------------------------------------------------------------------------------- 
		
		public void createFields() {
			
			build.fields = new String[] {localeSat.getStringKey("global_equipment_column"), localeSat.getStringKey("global_date_column"), localeSat.getStringKey("global_interval_column"), 
					localeSat.getStringKey("$labels_period_flow_direction"), localeSat.getStringKey("$labels_period_flow_light_vehicles"), localeSat.getStringKey("$labels_period_flow_commercial_vehicles"), 
					localeSat.getStringKey("$labels_period_flow_motorcycle"),  localeSat.getStringKey("global_total_column"), localeSat.getStringKey("$labels_period_flow_speed"),	localeSat.getStringKey("$labels_period_flow_direction"), 
					localeSat.getStringKey("$labels_period_flow_light_vehicles"), localeSat.getStringKey("$labels_period_flow_commercial_vehicles"), localeSat.getStringKey("$labels_period_flow_motorcycle"),
					localeSat.getStringKey("global_total_column"), localeSat.getStringKey("$labels_period_flow_speed")};

			// Table Object 
			build.fieldObjectValues = new String[] {"equip", "date", "time", "direction1", "light1", "comm1", "moto1", "total1", "speed1", "direction2", "light2", "comm2", "moto2", "total2", "speed2"};
							
			//DESENHAR A TABELA
			columns = build.drawTable(build.fields, build.fieldObjectValues);						

			// GUARDAR VALORES NA SESSION
			SessionUtil.setParam("fields", build.fields);	//Fields
			SessionUtil.setParam("fieldsObject", build.fieldObjectValues); //Objects
						
		}
		
		// ----------------------------------------------------------------------------------------------------------------------------------------------------- 
	
		  public void processInformations() throws Exception {
			  
			//  SessionUtil.executeScript("$('#modalInfo').modal('show');");
			  
			  DateTimeApplication dta = new DateTimeApplication();
			  
			  equipDao = new EquipmentsDAO();
			  
			  //FIELDS EXTERNOS ARMAZENADOS NA REQUISIO
			  build.fields = (String[]) SessionUtil.getParam("fields");
			  build.fieldObjectValues =  (String[]) SessionUtil.getParam("fieldsObject");
			  
			  Map<String, String[]> multiParameterMap = SessionUtil.getRequestParameterValuesMap();
			  Map<String, String> parameterMap = SessionUtil.getRequestParameterMap();
			   
			  equips = multiParameterMap.get("equips"); // EQUIP	 
			  period = parameterMap.get("periods"); // PERIOD
			  month = parameterMap.get("month"); // MONTH	
			  year = parameterMap.get("year"); // YEAR
									  
			  String fileName = localeSat.getStringKey("via_paulista_flow_per_period_file_name")+"_"+periodName(period)+"_"+monthABR+""+yearABR;
			  SessionUtil.setParam("current", dta.currentTime());
			  SessionUtil.setParam("fileName", fileName);   
			  
			 /* FacesContext context = FacesContext.getCurrentInstance();
			    boolean validationFailed = context.isValidationFailed();
			    if(!validationFailed) {
			    	RequestContext.getCurrentInstance().execute("PF('statusDialog').hide()");
			    	RequestContext.getCurrentInstance().execute("PF('poll').start()");
			    }*/
			  
		    			  
			// System.out.println("--- Initializing ---");
			 //System.out.println("Hello World");	 
					 
			 workbook = new SXSSFWorkbook(-1); //Criar Pasta do Excel	
			 propertyTemplate = new PropertyTemplate();
			 sheetName = new String[equips.length];		 
			 
			//Define Values in session map !important	
			 SessionUtil.setParam("workbook", workbook);
			 			
			 flag=0;
			 mess(flag);		
			 //System.out.println("Flag "+flag);
			 		
			 flag = 1;
			 mess(flag);	
			// System.out.println("Flag "+flag);
		     	     
		     flag=6;
			 mess(flag);		
			 //System.out.println("Flag "+flag);
			 
			 //System.out.println("Qtde Equipamentos: "+equips.length);	 
		     
			 for(indice=0; indice < equips.length; indice++) 	 
				   createSheets(indice, Integer.parseInt(equips[indice])); 	
							   		       		
	         flag = 2;
		     mess(flag);	   
		     //System.out.println("Flag "+flag);
		     
		     flag=6;
			 mess(flag);		
			 //System.out.println("Flag "+flag); 
			 
			 DateMethods dtm = new DateMethods();
			 
			 instaciarProcessaDados(dtm);	 
			 
			 for(indice=0; indice < equips.length; indice++) {				 
										
					sat = new SAT();		
					sat = equipDao.headerInfoSAT(equips[indice]);	
																							 
				 flag=3;
				 mess(flag);			 		 
				 //System.out.println("Flag "+flag);				
				// pausa(3000);
				 
				 flag=6;
				 mess(flag);			 
				// System.out.println("Flag "+flag);
					
				 processaDados(indice, Integer.parseInt(equips[indice]), dtm, sat);
				 			
				 if(indice == (equips.length-1)) {
				 
			     flag = 4;
				 mess(flag);
				// System.out.println("Flag "+flag);
				 
				 flag=6;
				 mess(flag);			 
				// System.out.println("Flag "+flag);
				 
				 }
				
			 }
			 
			    flag = 5;
			    mess(flag);				  
				//System.out.println("Flag "+flag);
			 		   		   
			    populateTable(); // POP DATATABLE
			    
			    flag=6;		   
			    mess(flag);		    
				//System.out.println("Flag "+flag);
				
				flag=7;
				mess(flag);
				//System.out.println("Flag "+flag);		
								
				build.closeBool = false; // ALLOW CLOSE MODAL
				build.excelBool = false;
				
				updateCloseButton(); 
								
				// REDRAW TABLE
				columns = build.drawTable(build.fields, build.fieldObjectValues);
				
				//UPDATE TABLE JQUERY ON RELOAD PAGE
				SessionUtil.executeScript("drawTable()");		
																        
		    }
		  
		  
		// -------------------------------------------------------------------------------------------------------------
		
		
		 public void getReport() throws Exception {
	 		 
				DateTimeApplication dta = new DateTimeApplication();
					
				List<FluxoPeriodo> lista = new ArrayList<FluxoPeriodo>();
				resultList = new ArrayList<Builder>();
			
				FluxoPeriodoDAO dao = new FluxoPeriodoDAO();
				EquipmentsDAO eqDAO = new EquipmentsDAO();
				
				//FIELDS EXTERNOS ARMAZENADOS NA REQUISIO
				build.fields = (String[]) SessionUtil.getParam("fields");
				build.fieldObjectValues =  (String[]) SessionUtil.getParam("fieldsObject");
						
			    Map<String, String> parameterMap = SessionUtil.getRequestParameterMap();
			    Map<String, String[]> multiParameterMap = SessionUtil.getRequestParameterValuesMap();
				
				equips = multiParameterMap.get("equips"); // EQUIP	 
				period = parameterMap.get("periods"); // PERIOD
				month = parameterMap.get("month"); // MONTH				
				year = parameterMap.get("year"); // YEAR
				 
				int yr = Integer.parseInt(year);
				int mth = Integer.parseInt(month);
				 
				YearMonth yearMonthObject = YearMonth.of(yr, mth);
				daysInMonth = yearMonthObject.lengthOfMonth();		
				 
				int diaInicial = 1;
				int equip = 0;
					 					 
				periodRange = dta.periodsRange(period);
						
				length = ((daysInMonth * periodRange) * equips.length); 
											 
				initVariables(length); // LENGHT				 	 
					 
				String startDate = dta.createDate(diaInicial, mth, yr);
				String endDate = dta.createDate(daysInMonth, mth, yr);
				String startDateAux = startDate;
			   
				hora_anterior = -1;				
				rangeInterval = tam;
				pos = 0;
				hrPos = 0;
				flag = 0;

				int hr = 0, minuto = 0, interResp = 0, ultimo_equipamento = 0;
				
				try {					
					
					workbook = new SXSSFWorkbook(-1); //Criar Pasta do Excel	
					propertyTemplate = new PropertyTemplate();
					sheetName = new String[equips.length]; 
				
					for(creation = 0; creation < equips.length; creation++) {

						equip = Integer.parseInt(equips[creation]);	
						
						sat = new SAT();		
						sat = eqDAO.headerInfoSAT(equips[fill]);		
											
						sheetName[creation] = sat.getNome();					
						sheet = workbook.createSheet(sheetName[creation]); //Criar WorkBook													 

						//System.out.println("----------------- ");
						//System.out.println("Criando Folha: "+sheetName[creation]);						

						//createExcelSheet(sheet, propertyTemplate);	
					}		
					
				
					//System.out.println("-----------------");
					//System.out.println("Folha(s) criada(s) ...");
					
					flag = 1;
					
					//mess(flag);
					//updateForm();

				 for(fill = 0; fill < equips.length; fill++) {	

						System.out.println("-----------------");
					    System.out.println("Entrando no Loop ...");

						if(ultimo_equipamento != fill) // Na troca de equipamento necessita incrementar mais uma posição
							pos += ((daysInMonth - 1) * periodRange);	

						equip = Integer.parseInt(equips[fill]);
				
						String date, interval;
						int autoSumS1, comSumS1, motoSumS1, totalSumS1,autoSumS2, comSumS2, motoSumS2,
						totalSumS2, speedAutoS1, speedComS1, speedMotoS1, speedTotalS1, speedAutoS2, speedComS2,
						speedMotoS2, speedTotalS2, speed50thAutoS1, speed50thComS1, speed50thMotoS1, speed50thTotalS1, speed50thAutoS2, speed50thComS2, 
						speed50thMotoS2,speed50thTotalS2, speed85thAutoS1, speed85thComS1, speed85thMotoS1, speed85thTotalS1, speed85thAutoS2, speed85thComS2, speed85thMotoS2,
						speed85thTotalS2, speedMaxAutoS1, speedMaxComS1, speedMaxMotoS1, speedMaxTotalS1, speedMaxAutoS2, speedMaxComS2, speedMaxMotoS2, speedMaxTotalS2,										
						speedMinAutoS1, speedMinComS1, speedMinMotoS1, speedMinTotalS1, speedMinAutoS2, speedMinComS2, speedMinMotoS2, speedMinTotalS2, speedStdAutoS1,						
						speedStdComS1, speedStdMotoS1, speedStdTotalS1, speedStdAutoS2, speedStdComS2, speedStdMotoS2, speedStdTotalS2;	

						//data = dtm.preencherDataFluxoPeriodo(startDate, endDate, tamanho);
						intervalo = dtm.intervaloHora(tamanho);	
						interInicio = dtm.intervalo15Inicio(tamanho);	
						interFim = dtm.intervalo15Fim(tamanho);	
						separador = dtm.intervaloSeparador(tamanho);	
						//days = dtm.preencherDias(tamanho);	

						System.out.println("----------------- ");
						System.out.println("Processando dados do "+sheetName[fill]+" ...");
						
						flag = 3;
						//mess(flag);
						
						//lista = dao.getVehicles(startDate, endDate, equips[fill], period, sat);	

						if(!lista.isEmpty()) {

							for(FluxoPeriodo pe : lista) {

								date = pe.getDate();
								interval = pe.getInterval();	
								
								autoSumS1 = pe.getSpeedAutoS1();
								comSumS1 = pe.getSpeedComS1();
								motoSumS1 = pe.getSpeedMotoS1();
								totalSumS1 = pe.getSpeedTotalS1();
								autoSumS2 = pe.getSpeedAutoS2();
								comSumS2 = pe.getSpeedComS2();
								motoSumS2 = pe.getSpeedMotoS2();
								totalSumS2 = pe.getSpeedTotalS2();

								speedAutoS1 = pe.getSpeedAutoS1();
								speedComS1 = pe.getSpeedComS1();
								speedMotoS1 = pe.getSpeedMotoS1();
								speedTotalS1 = pe.getSpeedTotalS1();						
								speedAutoS2 = pe.getSpeedAutoS2();
								speedComS2 = pe.getSpeedComS2();
								speedMotoS2 = pe.getSpeedMotoS2();
								speedTotalS2 = pe.getSpeedTotalS2();

								speed50thAutoS1 = pe.getSpeed50thAutoS1();
								speed50thComS1 = pe.getSpeed50thComS1();
								speed50thMotoS1 = pe.getSpeed50thMotoS1();
								speed50thTotalS1 = pe.getSpeed50thTotalS1();						
								speed50thAutoS2 = pe.getSpeed50thAutoS2();
								speed50thComS2 = pe.getSpeed50thComS2();
								speed50thMotoS2 = pe.getSpeed50thMotoS2();
								speed50thTotalS2 = pe.getSpeed50thTotalS2();

								speed85thAutoS1 = pe.getSpeed85thAutoS1();
								speed85thComS1 = pe.getSpeed85thComS1();
								speed85thMotoS1 = pe.getSpeed85thMotoS1();
								speed85thTotalS1 = pe.getSpeed85thTotalS1();						
								speed85thAutoS2 = pe.getSpeed85thAutoS2();
								speed85thComS2 = pe.getSpeed85thComS2();
								speed85thMotoS2 = pe.getSpeed85thMotoS2();
								speed85thTotalS2 = pe.getSpeed85thTotalS2();

								speedMaxAutoS1 = pe.getSpeedMaxAutoS1();
								speedMaxComS1 = pe.getSpeedMaxComS1();
								speedMaxMotoS1 = pe.getSpeedMaxMotoS1();
								speedMaxTotalS1 = pe.getSpeedMaxTotalS1();
								speedMaxAutoS2 = pe.getSpeedMaxAutoS2();
								speedMaxComS2 = pe.getSpeedMaxComS2();
								speedMaxMotoS2 = pe.getSpeedMaxMotoS2();
								speedMaxTotalS2 = pe.getSpeedMaxTotalS2();
								
								speedMinAutoS1 = pe.getSpeedMinAutoS1();
								speedMinComS1 = pe.getSpeedMinComS1();
								speedMinMotoS1 = pe.getSpeedMinMotoS1();
								speedMinTotalS1 = pe.getSpeedMinTotalS1();
								speedMinAutoS2 = pe.getSpeedMinAutoS2();
								speedMinComS2 = pe.getSpeedMinComS2();
								speedMinMotoS2 = pe.getSpeedMinMotoS2();
								speedMinTotalS2 = pe.getSpeedMinTotalS2();

								speedStdAutoS1 = pe.getSpeedStdAutoS1();						
								speedStdComS1 = pe.getSpeedStdComS1();
								speedStdMotoS1 = pe.getSpeedStdMotoS1();
								speedStdTotalS1 = pe.getSpeedStdTotalS1();
								speedStdAutoS2 = pe.getSpeedStdAutoS2();
								speedStdComS2 = pe.getSpeedStdComS2();
								speedStdMotoS2 = pe.getSpeedStdMotoS2();
								speedStdTotalS2= pe.getSpeedStdTotalS2();						

								hr = Integer.parseInt(interval.substring(0, 2));
								minuto =  Integer.parseInt(interval.substring(3, 5));

								// Restrição caso não haja dados nos primeiros registros
								if ((startDate != null) && (!date.equals(startDate))) {   // Executa uma unica vez

									interResp = daysDifference(dtInicio, date, rangeInterval);	
									pos+= interResp;	

									dtInicio = null;

								} else if (!date.equals(data_anterior)) {								

									interResp = daysDifference(data_anterior, date, rangeInterval);	
									pos+= interResp;							
								} 

								preencherDados15Min(pos, hr, minuto, autoSumS1, comSumS1, motoSumS1, totalSumS1, autoSumS2, comSumS2, motoSumS2, totalSumS2,
										speedAutoS1, speedComS1, speedMotoS1, speedTotalS1 ,speedAutoS2, speedComS2, speedMotoS2, speedTotalS2, 
										speed50thAutoS1, speed50thComS1, speed50thMotoS1, speed50thTotalS1, speed50thAutoS2, speed50thComS2, speed50thMotoS2, speed50thTotalS2,
										speed85thAutoS1, speed85thComS1, speed85thMotoS1, speed85thTotalS1, speed85thAutoS2, speed85thComS2, speed85thMotoS2, speed85thTotalS2,
										speedMaxAutoS1, speedMaxComS1, speedMaxMotoS1, speedMaxTotalS1, speedMaxAutoS2, speedMaxComS2, speedMaxMotoS2, speedMaxTotalS2,
										speedMinAutoS1, speedMinComS1, speedMinMotoS1, speedMinTotalS1, speedMinAutoS2, speedMinComS2, speedMinMotoS2, speedMinTotalS2,								
										speedStdAutoS1, speedStdComS1, speedStdMotoS1, speedStdTotalS1, speedStdAutoS2, speedStdComS2, speedStdMotoS2, speedStdTotalS2); 

								data_anterior = date; // Atualiza o dia						
							}	

							ultimo_equipamento = fill;

							//System.out.println("Encerrando processamento ...");
							//System.out.println("-----------------");
							//System.out.println("Saindo do Loop ...");
							//System.out.println("-----------------");

						} else  pos += 96;

						// System.out.println("Preenchendo dados na Folha "+sheetName[fill]+" ...");

						preencherDadosExcel(workbook,  propertyTemplate, fill, sheetName[fill], tam);

						flag = 5;				
						//mess(flag);
					}				

					//Fora do Laço!!!
					/*for(int i=0; i < tamanho; i++) {

						totalVeiculos.add(new FluxoPeriodo(data[i], intervalo[i], auto1[i], com1[i], moto1[i], total1[i], auto2[i], com2[i], 
								moto2[i], total2[i], totalVM1[i], totalVM2[i]));													
					}*/

					//System.out.println("------------------");
					//System.out.println("Processando Arquivo ...");
					//System.out.println("------------------");

					/* FileOutputStream out = new FileOutputStream("C:/teste/temp_files/teste.xlsx");
							  workbook.write(out);
							  out.close();	
							  workbook.dispose();*/

					//	System.out.println("------------------");
					//	System.out.println("Arquivo do Excel Criado com Sucesso!");
					//	System.out.println("------------------");		
		   
				 
		 	} catch (Exception ex) {
				ex.printStackTrace();
				
			}	
									 
					 
		 }
		 
	  // ----------------------------------------------------------------------------------------------------------------------------------------------------- 
							 
		 @SuppressWarnings("deprecation")
			public void preencherDadosExcel(SXSSFWorkbook workbook, PropertyTemplate propertyTemplate, int indice, String sheetName, int tam) throws IOException {    	 

				SXSSFSheet sheet = null;
				
				int i, idx, rowIndex; // Variáveis para criar o excel	
				int rowMax = ((daysInMonth * tam) + 5); // trabalhar aqui  					        
				int maxLimit = (daysInMonth * tam);	

				sheet = workbook.getSheet(sheetName);   
				
				//createRows(workbook, sheet, propertyTemplate); //
										
				for (rowIndex = 5, i= 0; rowIndex <= rowMax && i < (rowMax - 5); rowIndex++, i++) { 

					SXSSFRow row = sheet.createRow((short) 0 + rowIndex);	        	

					idx = i + (indice * maxLimit);				

					numbers = row.createCell((short) 1);
					numbers.setCellStyle(numbersTitle);
					numbers.setCellValue((rowIndex+1));

					dates = row.createCell((short) 2);
					dates.setCellStyle(dayStyle);
					dates.setCellValue(days[i]); 

					if(dates.getCellType() == Cell.CELL_TYPE_BLANK) //deixa mais lento
					dates.setCellStyle(style1);		
								
					horaInicio = row.createCell((short) 3);
					horaInicio.setCellStyle(intervaloStyle);
					horaInicio.setCellValue(interInicio[i]);
					
					//System.out.println("Inicio["+i+"]: "+interInicio[i]);
					
					horaSeparator = row.createCell((short) 4);
					horaSeparator.setCellStyle(intervaloStyle);
					horaSeparator.setCellValue(separador[i]);

					horaFim = row.createCell((short) 5);
					horaFim.setCellStyle(intervaloStyle);
				    horaFim.setCellValue(interFim[i]);		
										
					//Fluxo	

					autoS1 = row.createCell((short) 6);
					autoS1.setCellStyle(style1);
					autoS1.setCellValue(auto1[idx]);

					comS1 = row.createCell((short) 7);	
					comS1.setCellStyle(style1);	
					comS1.setCellValue(com1[idx]);	

					motoS1 = row.createCell((short) 8);	
					motoS1.setCellStyle(style1);
					motoS1.setCellValue(moto1[idx]);

					totalS1 = row.createCell((short) 9);	
					totalS1.setCellStyle(style1);	
					totalS1.setCellValue(total1[idx]);

					autoS2 = row.createCell((short) 10);	
					autoS2.setCellStyle(style1);
					autoS2.setCellValue(auto2[idx]);

					comS2 = row.createCell((short) 11);
					comS2.setCellStyle(style1);
					comS2.setCellValue(com2[idx]);										

					motoS2 = row.createCell((short) 12);
					motoS2.setCellStyle(style1);
					motoS2.setCellValue(moto2[idx]);

					totalS2 = row.createCell((short) 13);	
					totalS2.setCellStyle(style1);
					totalS2.setCellValue(total2[idx]);			

					//Velocidade Média

					autoVMS1 = row.createCell((short) 15);	 
					autoVMS1.setCellStyle(style1);
					autoVMS1.setCellValue(autoVM1[idx]);

					comVMS1 = row.createCell((short) 16);	
					comVMS1.setCellStyle(style1);	
					comVMS1.setCellValue(comVM1[idx]);

					motoVMS1 = row.createCell((short) 17);	
					motoVMS1.setCellStyle(style1);
					motoVMS1.setCellValue(motoVM1[idx]);

					totalVMS1 = row.createCell((short) 18);
					totalVMS1.setCellStyle(style1);	
					totalVMS1.setCellValue(totalVM1[idx]);

					autoVMS2 = row.createCell((short) 19);	
					autoVMS2.setCellStyle(style1);
					autoVMS2.setCellValue(autoVM2[idx]);

					comVMS2 = row.createCell((short) 20);	
					comVMS2.setCellStyle(style1);
					comVMS2.setCellValue(comVM2[idx]);	

					motoVMS2 = row.createCell((short) 21);	
					motoVMS2.setCellStyle(style1);	
					motoVMS2.setCellValue(motoVM2[idx]);

					totalVMS2 = row.createCell((short) 22);
					totalVMS2.setCellStyle(style1);
					totalVMS2.setCellValue(totalVM2[idx]);	
				
					//Velocidade Mediana 50%			

					autoV50S1 = row.createCell((short) 24);
					autoV50S1.setCellStyle(style1);
					autoV50S1.setCellValue(autoV501[idx]);

					comV50S1 = row.createCell((short) 25);
					comV50S1.setCellStyle(style1);	
					comV50S1.setCellValue(comV501[idx]);

					motoV50S1 = row.createCell((short) 26);	
					motoV50S1.setCellStyle(style1);
					motoV50S1.setCellValue(motoV501[idx]);

					totalV50S1= row.createCell((short) 27);	
					totalV50S1.setCellStyle(style1);
					totalV50S1.setCellValue(totalV501[idx]);	

					autoV50S2 = row.createCell((short) 28);	
					autoV50S2.setCellStyle(style1);
					autoV50S2.setCellValue(autoV502[idx]);

					comV50S2 = row.createCell((short) 29);
					comV50S2.setCellStyle(style1);
					comV50S2.setCellValue(comV502[idx]);	

					motoV50S2 = row.createCell((short) 30);
					motoV50S2.setCellStyle(style1);
					motoV50S2.setCellValue(motoV502[idx]);

					totalV50S2 = row.createCell((short) 31);	
					totalV50S2.setCellStyle(style1);
					totalV50S2.setCellValue(totalV502[idx]);	
				
					// Velocidade V85			

					autoV85S1 = row.createCell((short) 33);
					autoV85S1.setCellStyle(style1);	
					autoV85S1.setCellValue(autoV851[idx]);	

					comV85S1 = row.createCell((short) 34);	
					comV85S1.setCellStyle(style1);
					comV85S1.setCellValue(comV851[idx]);	

					motoV85S1 = row.createCell((short) 35);
					motoV85S1.setCellStyle(style1);	
					motoV85S1.setCellValue(motoV851[idx]);		

					totalV85S1 = row.createCell((short) 36);	
					totalV85S1.setCellStyle(style1);
					totalV85S1.setCellValue(totalV851[idx]);

					autoV85S2 = row.createCell((short) 37);	
					autoV85S2.setCellStyle(style1);
					autoV85S2.setCellValue(autoV852[idx]);	

					comV85S2 = row.createCell((short) 38);
					comV85S2.setCellStyle(style1);
					comV85S2.setCellValue(comV852[idx]);

					motoV85S2 = row.createCell((short) 39);
					motoV85S2.setCellStyle(style1);	
					motoV85S2.setCellValue(motoV852[idx]);

					totalV85S2 = row.createCell((short) 40);	
					totalV85S2.setCellStyle(style1);
					totalV85S2.setCellValue(totalV852[idx]);
				
					//Velocidade Máxima						

					autoVMAXS1 = row.createCell((short) 42);
					autoVMAXS1.setCellStyle(style1);
					autoVMAXS1.setCellValue(autoMAX1[idx]);

					comVMAXS1 = row.createCell((short) 43);	
					comVMAXS1.setCellStyle(style1);
					comVMAXS1.setCellValue(comMAX1[idx]);

					motoVMAXS1 = row.createCell((short) 44);
					motoVMAXS1.setCellStyle(style1);
					motoVMAXS1.setCellValue(motoMAX1[idx]);	

					totalVMAXS1 = row.createCell((short) 45);	
					totalVMAXS1.setCellStyle(style1);
					totalVMAXS1.setCellValue(totalMAX1[idx]);

					autoVMAXS2 = row.createCell((short) 46);	
					autoVMAXS2.setCellStyle(style1);
					autoVMAXS2.setCellValue(autoMAX2[idx]);

					comVMAXS2 = row.createCell((short) 47);
					comVMAXS2.setCellStyle(style1);
					comVMAXS2.setCellValue(comMAX2[idx]);

					motoVMAXS2 = row.createCell((short) 48);
					motoVMAXS2.setCellStyle(style1);
					motoVMAXS2.setCellValue(motoMAX2[idx]);	

					totalVMAXS2 = row.createCell((short) 49);
					totalVMAXS2.setCellStyle(style1);
					totalVMAXS2.setCellValue(totalMAX2[idx]);
					
					//Velocidade Mínima						

					autoVMINS1 = row.createCell((short) 51);
					autoVMINS1.setCellStyle(style1);
					autoVMINS1.setCellValue(autoMIN1[idx]);		

					comVMINS1 = row.createCell((short) 52);
					comVMINS1.setCellStyle(style1);
					comVMINS1.setCellValue(comMIN1[idx]);	

					motoVMINS1 = row.createCell((short) 53);	
					motoVMINS1.setCellStyle(style1);
					motoVMINS1.setCellValue(motoMIN1[idx]);		

					totalVMINS1 = row.createCell((short) 54);
					totalVMINS1.setCellStyle(style1);
					totalVMINS1.setCellValue(totalMIN1[idx]);

					autoVMINS2 = row.createCell((short) 55);	
					autoVMINS2.setCellStyle(style1);
					autoVMINS2.setCellValue(autoMIN2[idx]);	

					comVMINS2 = row.createCell((short) 56);
					comVMINS2.setCellStyle(style1);
					comVMINS2.setCellValue(comMIN2[idx]);

					motoVMINS2 = row.createCell((short) 57);	
					motoVMINS2.setCellStyle(style1);
					motoVMINS2.setCellValue(motoMIN2[idx]);		

					totalVMINS2 = row.createCell((short) 58);
					totalVMINS2.setCellStyle(style1);
					totalVMINS2.setCellValue(totalMIN2[idx]);	
								
					//Velocidade Desvio Padrão

					autoVDSPS1 = row.createCell((short) 60);	
					autoVDSPS1.setCellStyle(style1);
					autoVDSPS1.setCellValue(autoVDSP1[idx]);

					comVDSPS1 = row.createCell((short) 61);
					comVDSPS1.setCellStyle(style1);
					comVDSPS1.setCellValue(comVDSP1[idx]);	

					motoVDSPS1 = row.createCell((short) 62);
					motoVDSPS1.setCellStyle(style1);
					motoVDSPS1.setCellValue(motoVDSP1[idx]);

					totalVDSPS1 = row.createCell((short) 63);	
					totalVDSPS1.setCellStyle(style1);
					totalVDSPS1.setCellValue(totalVDSP1[idx]);

					autoVDSPS2 = row.createCell((short) 64);
					autoVDSPS2.setCellStyle(style1);
					autoVDSPS2.setCellValue(autoVDSP2[idx]);

					comVDSPS2 = row.createCell((short) 65);	
					comVDSPS2.setCellStyle(style1);
					comVDSPS2.setCellValue(comVDSP2[idx]);

					motoVDSPS2 = row.createCell((short) 66);	
					motoVDSPS2.setCellStyle(style1);
					motoVDSPS2.setCellValue(motoVDSP2[idx]);

					totalVDSPS2 = row.createCell((short) 67);
					totalVDSPS2.setCellStyle(style1);
					totalVDSPS2.setCellValue(totalVDSP2[idx]);

				}
			
				// Controle manual de como as linhas são liberadas para o disco
				if(rowIndex % rowMax == 0) 
					((SXSSFSheet)sheet).flushRows(rowMax); //  guardas as ultimas linhas escritas e libera outras

				// System.out.println("Preenchimento de dados finalizado!");

			}	


			private void initVariables(int tamanho) {

				nomeSats = new String[tamanho];
				data = new String[tamanho];
				days = new String[tamanho];  	   
				intervalo = new String[tamanho];
				sentido1 = new String[tamanho];
				sentido2 = new String[tamanho];		
				auto1 = new int[tamanho];				
				com1 = new int[tamanho];  
				moto1 = new int[tamanho];
				total1 = new int[tamanho];
				autoVM1 = new int[tamanho];
				comVM1 = new int[tamanho];
				motoVM1 = new int[tamanho]; 
				totalVM1 = new int[tamanho];		
				autoV501 = new int[tamanho];
				comV501 = new int[tamanho]; 
				motoV501 = new int[tamanho]; 
				totalV501 = new int[tamanho];
				autoV851 = new int[tamanho];
				comV851 = new int[tamanho]; 
				motoV851 = new int[tamanho]; 
				totalV851 = new int[tamanho];
				autoMAX1 = new int[tamanho];
				comMAX1 = new int[tamanho];
				motoMAX1 = new int[tamanho];
				totalMAX1 = new int[tamanho]; 
				autoMIN1= new int[tamanho];
				comMIN1 = new int[tamanho]; 
				motoMIN1 = new int[tamanho];
				totalMIN1 = new int[tamanho];
				autoVDSP1 = new int[tamanho]; 
				comVDSP1 = new int[tamanho]; 
				motoVDSP1 = new int[tamanho];
				totalVDSP1 = new int[tamanho];
				speedAVG1 = new int[tamanho];

				auto2 = new int[tamanho];				
				com2 = new int[tamanho];  
				moto2 = new int[tamanho];
				total2 = new int[tamanho];
				autoVM2 = new int[tamanho];
				comVM2 = new int[tamanho];
				motoVM2 = new int[tamanho]; 
				totalVM2 = new int[tamanho];
				autoV502 = new int[tamanho];
				comV502 = new int[tamanho];
				motoV502 = new int[tamanho]; 
				totalV502 = new int[tamanho];
				autoV502 = new int[tamanho];
				comV502 = new int[tamanho]; 
				motoV502 = new int[tamanho]; 
				totalV502 = new int[tamanho];
				autoV852 = new int[tamanho];
				comV852 = new int[tamanho]; 
				motoV852 = new int[tamanho]; 
				totalV852 = new int[tamanho];
				autoMAX2 = new int[tamanho];
				comMAX2 = new int[tamanho];
				motoMAX2 = new int[tamanho];
				totalMAX2 = new int[tamanho]; 
				autoMIN2= new int[tamanho];
				comMIN2 = new int[tamanho]; 
				motoMIN2 = new int[tamanho];
				totalMIN2 = new int[tamanho];
				autoVDSP2 = new int[tamanho]; 
				comVDSP2 = new int[tamanho]; 
				motoVDSP2 = new int[tamanho];
				totalVDSP2 = new int[tamanho];
				speedAVG2 = new int[tamanho];	
			}
			
			public void initializeSentidoExcel(int equipamentos) {
				
				int tamanho = equipamentos;	
				
				sentidoExcel1 = new String[tamanho];
				sentidoExcel2 = new String[tamanho];
			}

			public void preencherDados05Min(int pos, int hora, int minuto, int autoSumS1, int comSumS1, int motoSumS1, int totalSumS1, 
					int autoSumS2, int comSumS2, int motoSumS2, int totalSumS2, int speedAutoS1, int speedComS1, int speedMotoS1, int speedTotalS1,	
					int speedAutoS2, int speedComS2, int speedMotoS2, int speedTotalS2,
					int speed50thAutoS1, int speed50thComS1, int speed50thMotoS1, int speed50thTotalS1,	int speed50thAutoS2, int speed50thComS2, int speed50thMotoS2, int speed50thTotalS2,	
					int speed85thAutoS1, int speed85thComS1, int speed85thMotoS1, int speed85thTotalS1,	int speed85thAutoS2, int speed85thComS2, int speed85thMotoS2, int speed85thTotalS2,	
					int speedMaxAutoS1, int speedMaxComS1, int speedMaxMotoS1, int speedMaxTotalS1, int speedMaxAutoS2, int speedMaxComS2, int speedMaxMotoS2, int speedMaxTotalS2, 
					int speedMinAutoS1, int speedMinComS1, int speedMinMotoS1, int speedMinTotalS1,	int speedMinAutoS2, int speedMinComS2, int speedMinMotoS2, int speedMinTotalS2,		
					int speedStdAutoS1, int speedStdComS1, int speedStdMotoS1, int speedStdTotalS1, int speedStdAutoS2, int speedStdComS2, int speedStdMotoS2, int speedStdTotalS2) {		

				  DateMethods dtm = new DateMethods();

				int idx = dtm.horaIndex05Min(hora); // aponta um índice para preencher o array

			 int i=0; int p=0;					

				if (minuto == 0 ) 
					 i = 0;
				   if (minuto == 5 ) 
					    i = 1;
				      if (minuto == 10 ) 
					       i = 2;
				         if (minuto == 15 ) 
					          i = 3;
				             if (minuto == 20 ) 
					             i = 4;
				                if (minuto == 25 ) 
					               i = 5;			
				                   if (minuto == 30) 
					                   i = 6;			
				                      if (minuto == 35 ) 
					                      i = 7;			
				                         if (minuto == 40 ) 
					                          i = 8;	                         
				                            if (minuto == 45 ) 
						                         i = 9;	
				                              if (minuto == 50 ) 
						                           i = 10;		                            
				                                if (minuto == 55 ) 
						                             i = 11;	

					p = pos + idx + i; // Apontar o índice
								
					auto1[p] += autoSumS1;	com1[p] +=comSumS1; moto1[p] += motoSumS1; total1[p] += totalSumS1;
					auto2[p] += autoSumS2;	com2[p] +=comSumS2; moto2[p] += motoSumS2; total2[p] += totalSumS2;

					autoVM1[p] += speedAutoS1; comVM1[p] += speedComS1;	motoVM1[p] += speedMotoS1; totalVM1[p] += speedTotalS1;
					autoVM2[p] += speedAutoS2; comVM2[p] += speedComS2;	motoVM2[p] += speedMotoS2; totalVM2[p] += speedTotalS2;

					autoV501[p] += speed50thAutoS1; comV501[p] += speed50thComS1; motoV501[p] += speed50thMotoS1; totalV501[p] += speed50thTotalS1;
					autoV502[p] += speed50thAutoS2; comV502[p] += speed50thComS2; motoV502[p] += speed50thMotoS2; totalV502[p] += speed50thTotalS2;

					autoV851[p] += speed85thAutoS1; comV851[p] += speed85thComS1; motoV851[p] += speed85thMotoS1; totalV851[p] += speed85thTotalS1;
					autoV852[p] += speed85thAutoS2; comV852[p] += speed85thComS2; motoV852[p] += speed85thMotoS2; totalV852[p] += speed85thTotalS2;

					autoMAX1[p] += speedMaxAutoS1; comMAX1[p] += speedMaxComS1; motoMAX1[p] += speedMaxMotoS1; totalMAX1[p] += speedMaxTotalS1; 
					autoMAX2[p] += speedMaxAutoS2; comMAX2[p] += speedMaxComS2; motoMAX2[p] += speedMaxMotoS2; totalMAX2[p] += speedMaxTotalS2; 

					autoMIN1[p] += speedMinAutoS1; comMIN1[p] += speedMinComS1; motoMIN1[p] += speedMinMotoS1; totalMIN1[p] += speedMinTotalS1;
					autoMIN2[p] += speedMinAutoS2; comMIN2[p] += speedMinComS2; motoMIN2[p] += speedMinMotoS2; totalMIN2[p] += speedMinTotalS2;

					autoVDSP1[p] += speedStdAutoS1;	comVDSP1[p] += speedStdComS1; motoVDSP1[p] += speedStdMotoS1; totalVDSP1[p] += speedStdTotalS1;
					autoVDSP2[p] += speedStdAutoS2;	comVDSP2[p] += speedStdComS2; motoVDSP2[p] += speedStdMotoS2; totalVDSP2[p] += speedStdTotalS2;
				
			   }	
			
			public void preencherDados06Min(int pos, int hora, int minuto, int autoSumS1, int comSumS1, int motoSumS1, int totalSumS1, 
					int autoSumS2, int comSumS2, int motoSumS2, int totalSumS2, int speedAutoS1, int speedComS1, int speedMotoS1, int speedTotalS1,	
					int speedAutoS2, int speedComS2, int speedMotoS2, int speedTotalS2,
					int speed50thAutoS1, int speed50thComS1, int speed50thMotoS1, int speed50thTotalS1,	int speed50thAutoS2, int speed50thComS2, int speed50thMotoS2, int speed50thTotalS2,	
					int speed85thAutoS1, int speed85thComS1, int speed85thMotoS1, int speed85thTotalS1,	int speed85thAutoS2, int speed85thComS2, int speed85thMotoS2, int speed85thTotalS2,	
					int speedMaxAutoS1, int speedMaxComS1, int speedMaxMotoS1, int speedMaxTotalS1, int speedMaxAutoS2, int speedMaxComS2, int speedMaxMotoS2, int speedMaxTotalS2, 
					int speedMinAutoS1, int speedMinComS1, int speedMinMotoS1, int speedMinTotalS1,	int speedMinAutoS2, int speedMinComS2, int speedMinMotoS2, int speedMinTotalS2,		
					int speedStdAutoS1, int speedStdComS1, int speedStdMotoS1, int speedStdTotalS1, int speedStdAutoS2, int speedStdComS2, int speedStdMotoS2, int speedStdTotalS2) {		

				  DateMethods dtm = new DateMethods();

				int idx = dtm.horaIndex06Min(hora); // aponta um índice para preencher o array

				int i=0; int p=0;					

				if (minuto == 0 ) 
					 i = 0;
				   if (minuto == 6 ) 
					    i = 1;
				      if (minuto == 12 ) 
					       i = 2;
				         if (minuto == 18 ) 
					          i = 3;
				             if (minuto == 24 ) 
					             i = 4;
				                if (minuto == 30 ) 
					               i = 5;			
				                   if (minuto == 36 ) 
					                   i = 6;			
				                      if (minuto == 42 ) 
					                      i = 7;			
				                         if (minuto == 48 ) 
					                          i = 8;	                         
				                            if (minuto == 54 ) 
						                         i = 9;	

					p = pos + idx + i; // Apontar o índice
								
					auto1[p] += autoSumS1;	com1[p] +=comSumS1; moto1[p] += motoSumS1; total1[p] += totalSumS1;
					auto2[p] += autoSumS2;	com2[p] +=comSumS2; moto2[p] += motoSumS2; total2[p] += totalSumS2;

					autoVM1[p] += speedAutoS1; comVM1[p] += speedComS1;	motoVM1[p] += speedMotoS1; totalVM1[p] += speedTotalS1;
					autoVM2[p] += speedAutoS2; comVM2[p] += speedComS2;	motoVM2[p] += speedMotoS2; totalVM2[p] += speedTotalS2;

					autoV501[p] += speed50thAutoS1; comV501[p] += speed50thComS1; motoV501[p] += speed50thMotoS1; totalV501[p] += speed50thTotalS1;
					autoV502[p] += speed50thAutoS2; comV502[p] += speed50thComS2; motoV502[p] += speed50thMotoS2; totalV502[p] += speed50thTotalS2;

					autoV851[p] += speed85thAutoS1; comV851[p] += speed85thComS1; motoV851[p] += speed85thMotoS1; totalV851[p] += speed85thTotalS1;
					autoV852[p] += speed85thAutoS2; comV852[p] += speed85thComS2; motoV852[p] += speed85thMotoS2; totalV852[p] += speed85thTotalS2;

					autoMAX1[p] += speedMaxAutoS1; comMAX1[p] += speedMaxComS1; motoMAX1[p] += speedMaxMotoS1; totalMAX1[p] += speedMaxTotalS1; 
					autoMAX2[p] += speedMaxAutoS2; comMAX2[p] += speedMaxComS2; motoMAX2[p] += speedMaxMotoS2; totalMAX2[p] += speedMaxTotalS2; 

					autoMIN1[p] += speedMinAutoS1; comMIN1[p] += speedMinComS1; motoMIN1[p] += speedMinMotoS1; totalMIN1[p] += speedMinTotalS1;
					autoMIN2[p] += speedMinAutoS2; comMIN2[p] += speedMinComS2; motoMIN2[p] += speedMinMotoS2; totalMIN2[p] += speedMinTotalS2;

					autoVDSP1[p] += speedStdAutoS1;	comVDSP1[p] += speedStdComS1; motoVDSP1[p] += speedStdMotoS1; totalVDSP1[p] += speedStdTotalS1;
					autoVDSP2[p] += speedStdAutoS2;	comVDSP2[p] += speedStdComS2; motoVDSP2[p] += speedStdMotoS2; totalVDSP2[p] += speedStdTotalS2;
				
			   }	 
			 
			public void preencherDados15Min(int pos, int hora, int minuto, int autoSumS1, int comSumS1, int motoSumS1, int totalSumS1, 
					int autoSumS2, int comSumS2, int motoSumS2, int totalSumS2, int speedAutoS1, int speedComS1, int speedMotoS1, int speedTotalS1,	
					int speedAutoS2, int speedComS2, int speedMotoS2, int speedTotalS2,
					int speed50thAutoS1, int speed50thComS1, int speed50thMotoS1, int speed50thTotalS1,	int speed50thAutoS2, int speed50thComS2, int speed50thMotoS2, int speed50thTotalS2,	
					int speed85thAutoS1, int speed85thComS1, int speed85thMotoS1, int speed85thTotalS1,	int speed85thAutoS2, int speed85thComS2, int speed85thMotoS2, int speed85thTotalS2,	
					int speedMaxAutoS1, int speedMaxComS1, int speedMaxMotoS1, int speedMaxTotalS1, int speedMaxAutoS2, int speedMaxComS2, int speedMaxMotoS2, int speedMaxTotalS2, 
					int speedMinAutoS1, int speedMinComS1, int speedMinMotoS1, int speedMinTotalS1,	int speedMinAutoS2, int speedMinComS2, int speedMinMotoS2, int speedMinTotalS2,		
					int speedStdAutoS1, int speedStdComS1, int speedStdMotoS1, int speedStdTotalS1, int speedStdAutoS2, int speedStdComS2, int speedStdMotoS2, int speedStdTotalS2) {		

				DateMethods dtm = new DateMethods();
				
				DateTimeApplication dta = new DateTimeApplication();

				int idx = dta.horaIndex15Min(hora); // aponta um índice para preencher o array

				int i = 0; int p = 0;				

				if(minuto == 0 ) 
		             i=0;			
				   if(minuto == 15)
				       i=1;
				     if(minuto == 30)
				         i=2;
				        if(minuto == 45)
				           i=3;	   
				   
				    p = pos + idx + i; // Apontar o índice

					auto1[p] += autoSumS1;	com1[p] +=comSumS1; moto1[p] += motoSumS1; total1[p] += totalSumS1;
					auto2[p] += autoSumS2;	com2[p] +=comSumS2; moto2[p] += motoSumS2; total2[p] += totalSumS2;

					autoVM1[p] += speedAutoS1; comVM1[p] += speedComS1;	motoVM1[p] += speedMotoS1; totalVM1[p] += speedTotalS1;
					autoVM2[p] += speedAutoS2; comVM2[p] += speedComS2;	motoVM2[p] += speedMotoS2; totalVM2[p] += speedTotalS2;

					autoV501[p] += speed50thAutoS1; comV501[p] += speed50thComS1; motoV501[p] += speed50thMotoS1; totalV501[p] += speed50thTotalS1;
					autoV502[p] += speed50thAutoS2; comV502[p] += speed50thComS2; motoV502[p] += speed50thMotoS2; totalV502[p] += speed50thTotalS2;

					autoV851[p] += speed85thAutoS1; comV851[p] += speed85thComS1; motoV851[p] += speed85thMotoS1; totalV851[p] += speed85thTotalS1;
					autoV852[p] += speed85thAutoS2; comV852[p] += speed85thComS2; motoV852[p] += speed85thMotoS2; totalV852[p] += speed85thTotalS2;

					autoMAX1[p] += speedMaxAutoS1; comMAX1[p] += speedMaxComS1; motoMAX1[p] += speedMaxMotoS1; totalMAX1[p] += speedMaxTotalS1; 
					autoMAX2[p] += speedMaxAutoS2; comMAX2[p] += speedMaxComS2; motoMAX2[p] += speedMaxMotoS2; totalMAX2[p] += speedMaxTotalS2; 

					autoMIN1[p] += speedMinAutoS1; comMIN1[p] += speedMinComS1; motoMIN1[p] += speedMinMotoS1; totalMIN1[p] += speedMinTotalS1;
					autoMIN2[p] += speedMinAutoS2; comMIN2[p] += speedMinComS2; motoMIN2[p] += speedMinMotoS2; totalMIN2[p] += speedMinTotalS2;

					autoVDSP1[p] += speedStdAutoS1;	comVDSP1[p] += speedStdComS1; motoVDSP1[p] += speedStdMotoS1; totalVDSP1[p] += speedStdTotalS1;
					autoVDSP2[p] += speedStdAutoS2;	comVDSP2[p] += speedStdComS2; motoVDSP2[p] += speedStdMotoS2; totalVDSP2[p] += speedStdTotalS2;
				
			     }		
			
			 
			public void preencherDados01Hora(int pos, int hora, int autoSumS1, int comSumS1, int motoSumS1, int totalSumS1, 
					int autoSumS2, int comSumS2, int motoSumS2, int totalSumS2, int speedAutoS1, int speedComS1, int speedMotoS1, int speedTotalS1,	
					int speedAutoS2, int speedComS2, int speedMotoS2, int speedTotalS2,
					int speed50thAutoS1, int speed50thComS1, int speed50thMotoS1, int speed50thTotalS1,	int speed50thAutoS2, int speed50thComS2, int speed50thMotoS2, int speed50thTotalS2,	
					int speed85thAutoS1, int speed85thComS1, int speed85thMotoS1, int speed85thTotalS1,	int speed85thAutoS2, int speed85thComS2, int speed85thMotoS2, int speed85thTotalS2,	
					int speedMaxAutoS1, int speedMaxComS1, int speedMaxMotoS1, int speedMaxTotalS1, int speedMaxAutoS2, int speedMaxComS2, int speedMaxMotoS2, int speedMaxTotalS2, 
					int speedMinAutoS1, int speedMinComS1, int speedMinMotoS1, int speedMinTotalS1,	int speedMinAutoS2, int speedMinComS2, int speedMinMotoS2, int speedMinTotalS2,		
					int speedStdAutoS1, int speedStdComS1, int speedStdMotoS1, int speedStdTotalS1, int speedStdAutoS2, int speedStdComS2, int speedStdMotoS2, int speedStdTotalS2) {		
				  
				    int idx = hora + pos;
				    
				    auto1[idx] += autoSumS1;	com1[idx] +=comSumS1; moto1[idx] += motoSumS1; total1[idx] += totalSumS1;
					auto2[idx] += autoSumS2;	com2[idx] +=comSumS2; moto2[idx] += motoSumS2; total2[idx] += totalSumS2;

					autoVM1[idx] += speedAutoS1; comVM1[idx] += speedComS1;	motoVM1[idx] += speedMotoS1; totalVM1[idx] += speedTotalS1;
					autoVM2[idx] += speedAutoS2; comVM2[idx] += speedComS2;	motoVM2[idx] += speedMotoS2; totalVM2[idx] += speedTotalS2;

					autoV501[idx] += speed50thAutoS1; comV501[idx] += speed50thComS1; motoV501[idx] += speed50thMotoS1; totalV501[idx] += speed50thTotalS1;
					autoV502[idx] += speed50thAutoS2; comV502[idx] += speed50thComS2; motoV502[idx] += speed50thMotoS2; totalV502[idx] += speed50thTotalS2;

					autoV851[idx] += speed85thAutoS1; comV851[idx] += speed85thComS1; motoV851[idx] += speed85thMotoS1; totalV851[idx] += speed85thTotalS1;
					autoV852[idx] += speed85thAutoS2; comV852[idx] += speed85thComS2; motoV852[idx] += speed85thMotoS2; totalV852[idx] += speed85thTotalS2;

					autoMAX1[idx] += speedMaxAutoS1; comMAX1[idx] += speedMaxComS1; motoMAX1[idx] += speedMaxMotoS1; totalMAX1[idx] += speedMaxTotalS1; 
					autoMAX2[idx] += speedMaxAutoS2; comMAX2[idx] += speedMaxComS2; motoMAX2[idx] += speedMaxMotoS2; totalMAX2[idx] += speedMaxTotalS2; 

					autoMIN1[idx] += speedMinAutoS1; comMIN1[idx] += speedMinComS1; motoMIN1[idx] += speedMinMotoS1; totalMIN1[idx] += speedMinTotalS1;
					autoMIN2[idx] += speedMinAutoS2; comMIN2[idx] += speedMinComS2; motoMIN2[idx] += speedMinMotoS2; totalMIN2[idx] += speedMinTotalS2;

					autoVDSP1[idx] += speedStdAutoS1;	comVDSP1[idx] += speedStdComS1; motoVDSP1[idx] += speedStdMotoS1; totalVDSP1[idx] += speedStdTotalS1;
					autoVDSP2[idx] += speedStdAutoS2;	comVDSP2[idx] += speedStdComS2; motoVDSP2[idx] += speedStdMotoS2; totalVDSP2[idx] += speedStdTotalS2;
							
			   }
			
			public void preencherDados06Horas(int pos, int hora, int autoSumS1, int comSumS1, int motoSumS1, int totalSumS1, 
					int autoSumS2, int comSumS2, int motoSumS2, int totalSumS2, int speedAutoS1, int speedComS1, int speedMotoS1, int speedTotalS1,	
					int speedAutoS2, int speedComS2, int speedMotoS2, int speedTotalS2,
					int speed50thAutoS1, int speed50thComS1, int speed50thMotoS1, int speed50thTotalS1,	int speed50thAutoS2, int speed50thComS2, int speed50thMotoS2, int speed50thTotalS2,	
					int speed85thAutoS1, int speed85thComS1, int speed85thMotoS1, int speed85thTotalS1,	int speed85thAutoS2, int speed85thComS2, int speed85thMotoS2, int speed85thTotalS2,	
					int speedMaxAutoS1, int speedMaxComS1, int speedMaxMotoS1, int speedMaxTotalS1, int speedMaxAutoS2, int speedMaxComS2, int speedMaxMotoS2, int speedMaxTotalS2, 
					int speedMinAutoS1, int speedMinComS1, int speedMinMotoS1, int speedMinTotalS1,	int speedMinAutoS2, int speedMinComS2, int speedMinMotoS2, int speedMinTotalS2,		
					int speedStdAutoS1, int speedStdComS1, int speedStdMotoS1, int speedStdTotalS1, int speedStdAutoS2, int speedStdComS2, int speedStdMotoS2, int speedStdTotalS2) {		
				  		    
				//DateMethods dtm = new DateMethods();
				//int idx = dtm.getHoraIndex(hora); // aponta um índice para preencher o array
				
				int i = 0, p = 0;
				    
					if(hora == 0) 
			             i=0;			
					   if(hora == 6)
					       i=1;
					     if(hora == 12)
					         i=2;
					        if(hora == 18)
					           i=3;		
					        
					p = pos + i;
				    
					auto1[p] += autoSumS1;	com1[p] +=comSumS1; moto1[p] += motoSumS1; total1[p] += totalSumS1;
					auto2[p] += autoSumS2;	com2[p] +=comSumS2; moto2[p] += motoSumS2; total2[p] += totalSumS2;

					autoVM1[p] += speedAutoS1; comVM1[p] += speedComS1;	motoVM1[p] += speedMotoS1; totalVM1[p] += speedTotalS1;
					autoVM2[p] += speedAutoS2; comVM2[p] += speedComS2;	motoVM2[p] += speedMotoS2; totalVM2[p] += speedTotalS2;

					autoV501[p] += speed50thAutoS1; comV501[p] += speed50thComS1; motoV501[p] += speed50thMotoS1; totalV501[p] += speed50thTotalS1;
					autoV502[p] += speed50thAutoS2; comV502[p] += speed50thComS2; motoV502[p] += speed50thMotoS2; totalV502[p] += speed50thTotalS2;

					autoV851[p] += speed85thAutoS1; comV851[p] += speed85thComS1; motoV851[p] += speed85thMotoS1; totalV851[p] += speed85thTotalS1;
					autoV852[p] += speed85thAutoS2; comV852[p] += speed85thComS2; motoV852[p] += speed85thMotoS2; totalV852[p] += speed85thTotalS2;

					autoMAX1[p] += speedMaxAutoS1; comMAX1[p] += speedMaxComS1; motoMAX1[p] += speedMaxMotoS1; totalMAX1[p] += speedMaxTotalS1; 
					autoMAX2[p] += speedMaxAutoS2; comMAX2[p] += speedMaxComS2; motoMAX2[p] += speedMaxMotoS2; totalMAX2[p] += speedMaxTotalS2; 

					autoMIN1[p] += speedMinAutoS1; comMIN1[p] += speedMinComS1; motoMIN1[p] += speedMinMotoS1; totalMIN1[p] += speedMinTotalS1;
					autoMIN2[p] += speedMinAutoS2; comMIN2[p] += speedMinComS2; motoMIN2[p] += speedMinMotoS2; totalMIN2[p] += speedMinTotalS2;

					autoVDSP1[p] += speedStdAutoS1;	comVDSP1[p] += speedStdComS1; motoVDSP1[p] += speedStdMotoS1; totalVDSP1[p] += speedStdTotalS1;
					autoVDSP2[p] += speedStdAutoS2;	comVDSP2[p] += speedStdComS2; motoVDSP2[p] += speedStdMotoS2; totalVDSP2[p] += speedStdTotalS2;
				
			   }
			
			 
			public void preencherDados24Horas(int pos, int autoSumS1, int comSumS1, int motoSumS1, int totalSumS1, 
					int autoSumS2, int comSumS2, int motoSumS2, int totalSumS2, int speedAutoS1, int speedComS1, int speedMotoS1, int speedTotalS1,	
					int speedAutoS2, int speedComS2, int speedMotoS2, int speedTotalS2,
					int speed50thAutoS1, int speed50thComS1, int speed50thMotoS1, int speed50thTotalS1,	int speed50thAutoS2, int speed50thComS2, int speed50thMotoS2, int speed50thTotalS2,	
					int speed85thAutoS1, int speed85thComS1, int speed85thMotoS1, int speed85thTotalS1,	int speed85thAutoS2, int speed85thComS2, int speed85thMotoS2, int speed85thTotalS2,	
					int speedMaxAutoS1, int speedMaxComS1, int speedMaxMotoS1, int speedMaxTotalS1, int speedMaxAutoS2, int speedMaxComS2, int speedMaxMotoS2, int speedMaxTotalS2, 
					int speedMinAutoS1, int speedMinComS1, int speedMinMotoS1, int speedMinTotalS1,	int speedMinAutoS2, int speedMinComS2, int speedMinMotoS2, int speedMinTotalS2,		
					int speedStdAutoS1, int speedStdComS1, int speedStdMotoS1, int speedStdTotalS1, int speedStdAutoS2, int speedStdComS2, int speedStdMotoS2, int speedStdTotalS2) {		

				    auto1[pos] += autoSumS1;	com1[pos] +=comSumS1; moto1[pos] += motoSumS1; total1[pos] += totalSumS1;
				    auto2[pos] += autoSumS2;	com2[pos] +=comSumS2; moto2[pos] += motoSumS2; total2[pos] += totalSumS2;

				    autoVM1[pos] += speedAutoS1; comVM1[pos] += speedComS1;	motoVM1[pos] += speedMotoS1; totalVM1[pos] += speedTotalS1;
				    autoVM2[pos] += speedAutoS2; comVM2[pos] += speedComS2;	motoVM2[pos] += speedMotoS2; totalVM2[pos] += speedTotalS2;

				    autoV501[pos] += speed50thAutoS1; comV501[pos] += speed50thComS1; motoV501[pos] += speed50thMotoS1; totalV501[pos] += speed50thTotalS1;
				    autoV502[pos] += speed50thAutoS2; comV502[pos] += speed50thComS2; motoV502[pos] += speed50thMotoS2; totalV502[pos] += speed50thTotalS2;

				    autoV851[pos] += speed85thAutoS1; comV851[pos] += speed85thComS1; motoV851[pos] += speed85thMotoS1; totalV851[pos] += speed85thTotalS1;
				    autoV852[pos] += speed85thAutoS2; comV852[pos] += speed85thComS2; motoV852[pos] += speed85thMotoS2; totalV852[pos] += speed85thTotalS2;

				    autoMAX1[pos] += speedMaxAutoS1; comMAX1[pos] += speedMaxComS1; motoMAX1[pos] += speedMaxMotoS1; totalMAX1[pos] += speedMaxTotalS1; 
				    autoMAX2[pos] += speedMaxAutoS2; comMAX2[pos] += speedMaxComS2; motoMAX2[pos] += speedMaxMotoS2; totalMAX2[pos] += speedMaxTotalS2; 

				    autoMIN1[pos] += speedMinAutoS1; comMIN1[pos] += speedMinComS1; motoMIN1[pos] += speedMinMotoS1; totalMIN1[pos] += speedMinTotalS1;
				    autoMIN2[pos] += speedMinAutoS2; comMIN2[pos] += speedMinComS2; motoMIN2[pos] += speedMinMotoS2; totalMIN2[pos] += speedMinTotalS2;

				    autoVDSP1[pos] += speedStdAutoS1;	comVDSP1[pos] += speedStdComS1; motoVDSP1[pos] += speedStdMotoS1; totalVDSP1[pos] += speedStdTotalS1;
				    autoVDSP2[pos] += speedStdAutoS2;	comVDSP2[pos] += speedStdComS2; motoVDSP2[pos] += speedStdMotoS2; totalVDSP2[pos] += speedStdTotalS2;
				    
				    System.out.println(auto1[pos]);
			
					
			   }						

			public int daysDifference(String dtInit, String dtEnd, int indice) throws ParseException {

				DateMethods dtm = new DateMethods();

				String dataIni = "";
				String dataEnd = "";

				int days=0, difference=0;

				dataIni = dtm.StringToStringDateFormat(dtInit);
				dataEnd = dtm.StringToStringDateFormat(dtEnd);

				days = (int) dtm.diferencaDias(dataIni, dataEnd);
				difference = (days * indice);		

				return difference;
			}		
			
		
			/** Método para limpar listas e variáveis de caixas de seleção
			 * @throws Exception
			 * @return void */

			public void resetForm() throws Exception {
											
			// ---------------------------------------------------------------------------------
				
				//FIELDS EXTERNOS ARMAZENADOS NA REQUISIO
				build.fields = (String[]) SessionUtil.getParam("fields");
				build.fieldObjectValues =  (String[]) SessionUtil.getParam("fieldsObject");
						
				// AFTER REDRAW TABLE
				columns = build.drawTable(build.fields, build.fieldObjectValues);
						
			// ---------------------------------------------------------------------------------
							
				sat = new SAT();
				
				build.excelBool = true;
				build.clearBool = true;
				
				// REMOVE FIELDS FROM SESSION
				
				SessionUtil.remove("workbook");
				SessionUtil.remove("current");
				SessionUtil.remove("fileName");		
						
			}
			
			// ---------------------------------------------------------------------------------
				
								

			/* Métodos para criar o Excel */			
			public void createExcelSheet(SXSSFSheet sheet, PropertyTemplate propertyTemplate,  int sheetIndex) {

				dtm = new DateMethods();
				
				createStyle();
				columnsWidth(sheet);
				mergeCells(sheet);
				
				Row rowDescription = sheet.createRow((short) 0);							

				rowDescription.createCell(0).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_header"));							
				rowDescription.getCell(0).setCellStyle(espacoStyle);							

				Row rowDescription1 = sheet.createRow((short) 1);

				rowDescription1.createCell(0);

				rowDescription1.createCell(0).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_header"));
				rowDescription1.getCell(0).setCellStyle(espacoStyle2);	

				for(int i=1; i < 68; i++) {							
					rowDescription1.createCell(i).setCellValue(i);								
					rowDescription1.getCell(i).setCellStyle(numbersTitle);
				}			

				Row rowDescription2 = sheet.createRow((short) 2);

				rowDescription2.createCell(1).setCellValue("2");
				rowDescription2.createCell(2).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_model")+" - "+dtm.translateMinutes(period));

				rowDescription2.createCell(6).setCellValue(sentidoExcel1[sheetIndex]);
				rowDescription2.createCell(10).setCellValue(sentidoExcel2[sheetIndex]);

				rowDescription2.createCell(15).setCellValue(sentidoExcel1[sheetIndex]);
				rowDescription2.createCell(19).setCellValue(sentidoExcel2[sheetIndex]);

				rowDescription2.createCell(24).setCellValue(sentidoExcel1[sheetIndex]);
				rowDescription2.createCell(28).setCellValue(sentidoExcel2[sheetIndex]);

				rowDescription2.createCell(33).setCellValue(sentidoExcel1[sheetIndex]);
				rowDescription2.createCell(37).setCellValue(sentidoExcel2[sheetIndex]);

				rowDescription2.createCell(42).setCellValue(sentidoExcel1[sheetIndex]);
				rowDescription2.createCell(46).setCellValue(sentidoExcel2[sheetIndex]);

				rowDescription2.createCell(51).setCellValue(sentidoExcel1[sheetIndex]);							
				rowDescription2.createCell(55).setCellValue(sentidoExcel2[sheetIndex]);

				rowDescription2.createCell(60).setCellValue(sentidoExcel1[sheetIndex]);							
				rowDescription2.createCell(64).setCellValue(sentidoExcel2[sheetIndex]);	

				rowDescription2.createCell(14);
				rowDescription2.createCell(23);
				rowDescription2.createCell(32);
				rowDescription2.createCell(41);
				rowDescription2.createCell(50);
				rowDescription2.createCell(59);
				rowDescription2.createCell(68);

				/* Style */	
				rowDescription2.getCell(1).setCellStyle(numbersTitle);
				rowDescription2.getCell(2).setCellStyle(reportTime);							
				rowDescription2.getCell(6).setCellStyle(subHeader);
				rowDescription2.getCell(10).setCellStyle(subHeader);						
				rowDescription2.getCell(15).setCellStyle(subHeader);
				rowDescription2.getCell(19).setCellStyle(subHeader);							
				rowDescription2.getCell(24).setCellStyle(subHeader);
				rowDescription2.getCell(28).setCellStyle(subHeader);							
				rowDescription2.getCell(33).setCellStyle(subHeader);
				rowDescription2.getCell(37).setCellStyle(subHeader);							
				rowDescription2.getCell(42).setCellStyle(subHeader);
				rowDescription2.getCell(46).setCellStyle(subHeader);							
				rowDescription2.getCell(51).setCellStyle(subHeader);
				rowDescription2.getCell(55).setCellStyle(subHeader);							
				rowDescription2.getCell(60).setCellStyle(subHeader);
				rowDescription2.getCell(64).setCellStyle(subHeader);

				//BlankCell
				rowDescription2.getCell(14).setCellStyle(blankStyle);
				rowDescription2.getCell(23).setCellStyle(blankStyle);
				rowDescription2.getCell(32).setCellStyle(blankStyle);
				rowDescription2.getCell(41).setCellStyle(blankStyle);
				rowDescription2.getCell(50).setCellStyle(blankStyle);
				rowDescription2.getCell(59).setCellStyle(blankStyle);
				rowDescription2.getCell(68).setCellStyle(blankStyle2);

				Row rowDescription3 = sheet.createRow((short) 3);				
				rowDescription3.createCell(1).setCellValue("3");
				
				rowDescription3.createCell(2).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_day"));
				rowDescription3.createCell(3).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_hour"));

				rowDescription3.createCell(6).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_flow"));
				rowDescription3.createCell(10).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_flow"));

				rowDescription3.createCell(15).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_avg"));
				rowDescription3.createCell(19).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_avg"));

				rowDescription3.createCell(24).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_avg_50"));
				rowDescription3.createCell(28).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_avg_50"));

				rowDescription3.createCell(33).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_avg_85"));
				rowDescription3.createCell(37).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_avg_85"));

				rowDescription3.createCell(42).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_max"));
				rowDescription3.createCell(46).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_max"));

				rowDescription3.createCell(51).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_min"));							
				rowDescription3.createCell(55).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_min"));

				rowDescription3.createCell(60).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_std"));							
				rowDescription3.createCell(64).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_std"));

				//Blank Space
				rowDescription3.createCell(14);
				rowDescription3.createCell(23);
				rowDescription3.createCell(32);
				rowDescription3.createCell(41);
				rowDescription3.createCell(50);
				rowDescription3.createCell(59);
				rowDescription3.createCell(68);

				/* Style */	
				rowDescription3.getCell(1).setCellStyle(numbersTitle);
				rowDescription3.getCell(2).setCellStyle(subHeader);		
				rowDescription3.getCell(3).setCellStyle(subHeader);	
				rowDescription3.getCell(6).setCellStyle(subHeader);
				rowDescription3.getCell(10).setCellStyle(subHeader);							
				rowDescription3.getCell(15).setCellStyle(subHeader);
				rowDescription3.getCell(19).setCellStyle(subHeader);							
				rowDescription3.getCell(24).setCellStyle(subHeader);
				rowDescription3.getCell(28).setCellStyle(subHeader);							
				rowDescription3.getCell(33).setCellStyle(subHeader);
				rowDescription3.getCell(37).setCellStyle(subHeader);							
				rowDescription3.getCell(42).setCellStyle(subHeader);
				rowDescription3.getCell(46).setCellStyle(subHeader);							
				rowDescription3.getCell(51).setCellStyle(subHeader);
				rowDescription3.getCell(55).setCellStyle(subHeader);							
				rowDescription3.getCell(60).setCellStyle(subHeader);
				rowDescription3.getCell(64).setCellStyle(subHeader);

				rowDescription3.getCell(15).setCellStyle(redColorBackground);
				rowDescription3.getCell(19).setCellStyle(redColorBackground);							
				rowDescription3.getCell(24).setCellStyle(blueColorBackground);
				rowDescription3.getCell(28).setCellStyle(blueColorBackground);							
				rowDescription3.getCell(33).setCellStyle(redColorBackground);
				rowDescription3.getCell(37).setCellStyle(redColorBackground);							
				rowDescription3.getCell(42).setCellStyle(blueColorBackground);
				rowDescription3.getCell(46).setCellStyle(blueColorBackground);							
				rowDescription3.getCell(51).setCellStyle(redColorBackground);
				rowDescription3.getCell(55).setCellStyle(redColorBackground);							
				rowDescription3.getCell(60).setCellStyle(blueColorBackground);
				rowDescription3.getCell(64).setCellStyle(blueColorBackground);

				//BlankCell
				rowDescription3.getCell(14).setCellStyle(blankStyle);
				rowDescription3.getCell(23).setCellStyle(blankStyle);
				rowDescription3.getCell(32).setCellStyle(blankStyle);
				rowDescription3.getCell(41).setCellStyle(blankStyle);
				rowDescription3.getCell(50).setCellStyle(blankStyle);
				rowDescription3.getCell(59).setCellStyle(blankStyle);
				rowDescription3.getCell(68).setCellStyle(blankStyle2);

				Row rowDescription4 = sheet.createRow((short) 4);

				rowDescription4.createCell(1).setCellValue("4");
				rowDescription4.createCell(6).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
				rowDescription4.createCell(7).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
				rowDescription4.createCell(8).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
				rowDescription4.createCell(9).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_total_tab"));
				rowDescription4.createCell(10).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
				rowDescription4.createCell(11).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
				rowDescription4.createCell(12).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
				rowDescription4.createCell(13).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_total_tab"));

				rowDescription4.createCell(15).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
				rowDescription4.createCell(16).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
				rowDescription4.createCell(17).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
				rowDescription4.createCell(18).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_all_directions"));							
				rowDescription4.createCell(19).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
				rowDescription4.createCell(20).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
				rowDescription4.createCell(21).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
				rowDescription4.createCell(22).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_all_directions"));
				
				rowDescription4.createCell(24).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
				rowDescription4.createCell(25).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
				rowDescription4.createCell(26).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
				rowDescription4.createCell(27).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_all_directions"));							
				rowDescription4.createCell(28).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
				rowDescription4.createCell(29).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
				rowDescription4.createCell(30).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
				rowDescription4.createCell(31).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_all_directions"));

				rowDescription4.createCell(33).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
				rowDescription4.createCell(34).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
				rowDescription4.createCell(35).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
				rowDescription4.createCell(36).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_all_directions"));							
				rowDescription4.createCell(37).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
				rowDescription4.createCell(38).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
				rowDescription4.createCell(39).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
				rowDescription4.createCell(40).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_all_directions"));
				
				rowDescription4.createCell(42).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
				rowDescription4.createCell(43).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
				rowDescription4.createCell(44).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
				rowDescription4.createCell(45).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_all_directions"));							
				rowDescription4.createCell(46).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
				rowDescription4.createCell(47).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
				rowDescription4.createCell(48).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
				rowDescription4.createCell(49).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_all_directions"));
				
				rowDescription4.createCell(51).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
				rowDescription4.createCell(52).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
				rowDescription4.createCell(53).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
				rowDescription4.createCell(54).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_all_directions"));							
				rowDescription4.createCell(55).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
				rowDescription4.createCell(56).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
				rowDescription4.createCell(57).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
				rowDescription4.createCell(58).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_all_directions"));
				
				rowDescription4.createCell(60).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
				rowDescription4.createCell(61).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
				rowDescription4.createCell(62).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
				rowDescription4.createCell(63).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_all_directions"));							
				rowDescription4.createCell(64).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
				rowDescription4.createCell(65).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
				rowDescription4.createCell(66).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
				rowDescription4.createCell(67).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_all_directions"));
				
				/* Style */	
				rowDescription4.getCell(1).setCellStyle(numbersTitle);	

				for(int i=6; i < 14; i++)
					rowDescription4.getCell(i).setCellStyle(subHeader);

				for(int i=15; i < 23; i++)
					rowDescription4.getCell(i).setCellStyle(subHeader);

				for(int i=24; i < 32; i++)
					rowDescription4.getCell(i).setCellStyle(subHeader);

				for(int i=33; i < 41; i++)
					rowDescription4.getCell(i).setCellStyle(subHeader);

				for(int i=42; i < 50; i++)
					rowDescription4.getCell(i).setCellStyle(subHeader);

				for(int i=51; i < 59; i++)
					rowDescription4.getCell(i).setCellStyle(subHeader);

				for(int i=60; i < 68; i++)
					rowDescription4.getCell(i).setCellStyle(subHeader);							 						

				   // System.out.println("Folha Criada com Sucesso!");					

			}

			public void createRows(SXSSFWorkbook workbook, SXSSFSheet sheet, PropertyTemplate propertyTemplate) {    	 

				int rowIndex; // Variáveis para criar o excel	
				int rowMax = ((daysInMonth * periodRange) + 5); // trabalhar aqui  
					
				for(rowIndex = 5; rowIndex < rowMax; rowIndex++) {

					SXSSFRow row = sheet.createRow((short) 0 + rowIndex);

					numbers = row.createCell((short) 1); numbers.setCellStyle(numbersTitle);
					dates = row.createCell((short) 2);	dates.setCellStyle(dayStyle);														

					horaInicio = row.createCell((short) 3);	horaInicio.setCellStyle(intervaloStyle);			
					horaSeparator = row.createCell((short) 4); horaSeparator.setCellStyle(intervaloStyle);					
					horaFim = row.createCell((short) 5); horaFim.setCellStyle(intervaloStyle);

					//Fluxo					

					autoS1 = row.createCell((short) 6); autoS1.setCellStyle(style1);					
					comS1 = row.createCell((short) 7);	comS1.setCellStyle(style1);					
					motoS1 = row.createCell((short) 8); motoS1.setCellStyle(style1);					
					totalS1 = row.createCell((short) 9); totalS1.setCellStyle(style1);	

					autoS2 = row.createCell((short) 10); autoS2.setCellStyle(style1);					
					comS2 = row.createCell((short) 11); comS2.setCellStyle(style1);				
					motoS2 = row.createCell((short) 12); motoS2.setCellStyle(style1);				
					totalS2 = row.createCell((short) 13); totalS2.setCellStyle(style1);	

					//Velocidade Média

					autoVMS1 = row.createCell((short) 15); autoVMS1.setCellStyle(style1);					
					comVMS1 = row.createCell((short) 16); comVMS1.setCellStyle(style1);					
					motoVMS1 = row.createCell((short)17); motoVMS1.setCellStyle(style1);				
					totalVMS1 = row.createCell((short) 18); totalVMS1.setCellStyle(style1);							

					autoVMS2 = row.createCell((short) 19); autoVMS2.setCellStyle(style1);					
					comVMS2 = row.createCell((short) 20); comVMS2.setCellStyle(style1);				
					motoVMS2 = row.createCell((short) 21);	motoVMS2.setCellStyle(style1);				
					totalVMS2 = row.createCell((short) 22);totalVMS2.setCellStyle(style1);	

					//Velocidade 50%

					autoV50S1 = row.createCell((short) 24); autoV50S1.setCellStyle(style1);					
					comV50S1 = row.createCell((short) 25); comV50S1.setCellStyle(style1);				
					motoV50S1 = row.createCell((short) 26);motoV50S1.setCellStyle(style1);					
					totalV50S1 = row.createCell((short) 27);	totalV50S1.setCellStyle(style1);							

					autoV50S2 = row.createCell((short) 28); autoV50S2.setCellStyle(style1);					
					comV50S2 = row.createCell((short) 29); comV50S2.setCellStyle(style1);				
					motoV50S2 = row.createCell((short) 30); motoV50S2.setCellStyle(style1);				
					totalV50S2 = row.createCell((short) 31); totalV50S2.setCellStyle(style1);	

					//Velocidade 85%

					autoV85S1 = row.createCell((short) 33); autoV85S1.setCellStyle(style1);				
					comV85S1 = row.createCell((short) 34);	comV85S1.setCellStyle(style1);					
					motoV85S1 = row.createCell((short) 35); motoV85S1.setCellStyle(style1);			
					totalV85S1 = row.createCell((short) 36); totalV85S1.setCellStyle(style1);					

					autoV85S2 = row.createCell((short) 37); autoV85S2.setCellStyle(style1);			
					comV85S2 = row.createCell((short) 38);	comV85S2.setCellStyle(style1);				
					motoV85S2 = row.createCell((short) 39); motoV85S2.setCellStyle(style1);			
					totalV85S2 = row.createCell((short) 40); totalV85S2.setCellStyle(style1);

					//Velocidade Máxima					

					autoVMAXS1 = row.createCell((short) 42); autoVMAXS1.setCellStyle(style1);					
					comVMAXS1 = row.createCell((short) 43); comVMAXS1.setCellStyle(style1);					
					motoVMAXS1 = row.createCell((short) 44); motoVMAXS1.setCellStyle(style1);				
					totalVMAXS1 = row.createCell((short) 45); totalVMAXS1.setCellStyle(style1);							

					autoVMAXS2 = row.createCell((short) 46); autoVMAXS2.setCellStyle(style1);				
					comVMAXS2 = row.createCell((short) 47); comVMAXS2.setCellStyle(style1);		
					motoVMAXS2 = row.createCell((short) 48); motoVMAXS2.setCellStyle(style1);				
					totalVMAXS2 = row.createCell((short) 49); totalVMAXS2.setCellStyle(style1);

					//Velocidade Mínima

					autoVMINS1 = row.createCell((short) 51); autoVMINS1.setCellStyle(style1);					
					comVMINS1 = row.createCell((short) 52); comVMINS1.setCellStyle(style1);				
					motoVMINS1 = row.createCell((short) 53); motoVMINS1.setCellStyle(style1);
					totalVMINS1 = row.createCell((short) 54); totalVMINS1.setCellStyle(style1);							

					autoVMINS2 = row.createCell((short) 55); autoVMINS2.setCellStyle(style1);					
					comVMINS2 = row.createCell((short) 56); comVMINS2.setCellStyle(style1);			
					motoVMINS2 = row.createCell((short) 57); motoVMINS2.setCellStyle(style1);								
					totalVMINS2 = row.createCell((short) 58);totalVMINS2.setCellStyle(style1);

					//Velocidade Desvio Padrão

					autoVDSPS1 = row.createCell((short) 60); autoVDSPS1.setCellStyle(style1);					
					comVDSPS1 = row.createCell((short) 61); comVDSPS1.setCellStyle(style1);				
					motoVDSPS1 = row.createCell((short) 62); motoVDSPS1.setCellStyle(style1);				
					totalVDSPS1 = row.createCell((short) 63); totalVDSPS1.setCellStyle(style1);							

					autoVDSPS2 = row.createCell((short) 64); autoVDSPS2.setCellStyle(style1);					
					comVDSPS2 = row.createCell((short) 65); comVDSPS2.setCellStyle(style1);				
					motoVDSPS2 = row.createCell((short) 66); motoVDSPS2.setCellStyle(style1);					
					totalVDSPS2 = row.createCell((short) 67); totalVDSPS2.setCellStyle(style1);

				}		        
			}	

			
			 /**M�todo para realizar o download do aqruivo Excel
		     * @return void - retorno vazio
		     * @throws IOException 
		     */
		    
			public void download() {

				ExcelModels model = new ExcelModels();
				
				SXSSFWorkbook workbook = (SXSSFWorkbook) SessionUtil.getParam("workbook");
				String currentDate = (String) SessionUtil.getParam("current");
				String fileName = (String) SessionUtil.getParam("fileName");

				String name = fileName+"_"+currentDate;

				try {

					model.download(workbook, name);

				} catch (IOException e) {			
					e.printStackTrace();
				}

			}   	            

			public void createStyle() {			

				// Fonte

				fontBody = workbook.createFont();
				fontBody.setFontName(HSSFFont.FONT_ARIAL);
				fontBody.setFontHeightInPoints((short) 10);

				fontSubHeader = workbook.createFont();
				fontSubHeader.setColor(HSSFColor.HSSFColorPredefined.DARK_BLUE.getIndex());
				fontSubHeader.setBold(true);
				fontSubHeader.setFontName(HSSFFont.FONT_ARIAL);
				fontSubHeader.setFontHeightInPoints((short) 10);	

				fontReportTime = workbook.createFont();
				fontReportTime.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
				fontReportTime.setBold(true);
				fontReportTime.setFontName(HSSFFont.FONT_ARIAL);
				fontReportTime.setFontHeightInPoints((short) 10);	

				fontNumbersTitle = workbook.createFont(); 
				fontNumbersTitle.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
				fontNumbersTitle.setBold(true); 
				fontNumbersTitle.setFontName(HSSFFont.FONT_ARIAL); 
				fontNumbersTitle.setFontHeightInPoints((short) 11);	

				fontBackgroundColor = workbook.createFont();
				fontBackgroundColor.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
				fontBackgroundColor.setBold(true);
				fontBackgroundColor.setFontName(HSSFFont.FONT_ARIAL);
				fontBackgroundColor.setFontHeightInPoints((short) 10);

				fontBodyHeader = workbook.createFont();
				fontBodyHeader.setColor(HSSFColor.HSSFColorPredefined.GREEN.getIndex());
				fontBodyHeader.setBold(false);	

				fontBodyHeader2 = workbook.createFont();
				fontBodyHeader2.setColor(HSSFColor.HSSFColorPredefined.ORANGE.getIndex());
				fontBodyHeader2.setBold(false);	

				fontEspaco = workbook.createFont();
				fontEspaco.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
				fontEspaco.setFontName(HSSFFont.FONT_ARIAL); 
				fontEspaco.setFontHeightInPoints((short) 9);	
				fontEspaco.setBold(true);	

				fontEspaco2 = workbook.createFont();
				fontEspaco2.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
				fontEspaco2.setFontName(HSSFFont.FONT_ARIAL); 
				fontEspaco2.setFontHeightInPoints((short) 11);	
				fontEspaco2.setBold(true);	

				fontDataHeader = workbook.createFont();
				fontDataHeader.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
				fontDataHeader.setFontName(HSSFFont.FONT_ARIAL); 
				fontDataHeader.setFontHeightInPoints((short) 11);	
				fontDataHeader.setBold(true);

				fontIntervalo = workbook.createFont();
				fontIntervalo.setColor(HSSFColor.HSSFColorPredefined.DARK_BLUE.getIndex());
				fontIntervalo.setBold(true);
				fontIntervalo.setFontName(HSSFFont.FONT_ARIAL);
				fontIntervalo.setFontHeightInPoints((short) 10);

				/* Fonte End */			

				// Estilo 

				style1 = workbook.createCellStyle();				
				style1.setFont(fontBody);
				style1.setAlignment(HorizontalAlignment.CENTER);
				style1.setFillBackgroundColor(IndexedColors.WHITE.getIndex()); // Cor na seleção da célula

				subHeader = workbook.createCellStyle();									
				subHeader.setFont(fontSubHeader);				
				subHeader.setFillForegroundColor(IndexedColors.WHITE.getIndex()); 
				subHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				subHeader.setAlignment(HorizontalAlignment.CENTER);	
				subHeader.setVerticalAlignment(VerticalAlignment.CENTER); // Centralizar no vertical

				reportTime = workbook.createCellStyle();						
				reportTime.setWrapText(true);
				reportTime.setFont(fontReportTime);		 		
				reportTime.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
				reportTime.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				reportTime.setAlignment(HorizontalAlignment.CENTER);	
				reportTime.setVerticalAlignment(VerticalAlignment.CENTER); // Centralizar no vertical

				numbersTitle = workbook.createCellStyle();						
				numbersTitle.setWrapText(true);
				numbersTitle.setFont(fontNumbersTitle);				
				numbersTitle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
				numbersTitle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				numbersTitle.setAlignment(HorizontalAlignment.CENTER);	
				numbersTitle.setVerticalAlignment(VerticalAlignment.CENTER); // Centralizar no vertical

				blueColorBackground = workbook.createCellStyle();									
				blueColorBackground.setWrapText(true);
				blueColorBackground.setFont(fontBackgroundColor);				
				blueColorBackground.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_BLUE.getIndex());
				blueColorBackground.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				blueColorBackground.setAlignment(HorizontalAlignment.CENTER);	
				blueColorBackground.setVerticalAlignment(VerticalAlignment.CENTER); // Centralizar no vertical

				redColorBackground = workbook.createCellStyle();	 	 				
				redColorBackground.setWrapText(true);
				redColorBackground.setFont(fontBackgroundColor);				
				redColorBackground.setFillForegroundColor(HSSFColor.HSSFColorPredefined.CORAL.getIndex());
				redColorBackground.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				redColorBackground.setAlignment(HorizontalAlignment.CENTER);	
				redColorBackground.setVerticalAlignment(VerticalAlignment.CENTER); // Centralizar no vertical

				backgroundColorBodyHeader = workbook.createCellStyle();			
				backgroundColorBodyHeader.setFont(fontBodyHeader);				
				backgroundColorBodyHeader.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_CORNFLOWER_BLUE.getIndex());
				backgroundColorBodyHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				backgroundColorBodyHeader.setAlignment(HorizontalAlignment.CENTER);	
				backgroundColorBodyHeader.setVerticalAlignment(VerticalAlignment.CENTER); // Centralizar no vertical	

				backgroundColorBodyHeader2 = workbook.createCellStyle();									
				backgroundColorBodyHeader2.setFont(fontBodyHeader2);				
				backgroundColorBodyHeader2.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex());
				backgroundColorBodyHeader2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				backgroundColorBodyHeader2.setAlignment(HorizontalAlignment.CENTER);	
				backgroundColorBodyHeader2.setVerticalAlignment(VerticalAlignment.CENTER); // Centralizar no vertical		

				espacoStyle = workbook.createCellStyle();					
				espacoStyle.setFont(fontEspaco);
				espacoStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.ORANGE.getIndex());
				espacoStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				espacoStyle.setAlignment(HorizontalAlignment.CENTER);	
				espacoStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Centralizar no vertical

				espacoStyle2 = workbook.createCellStyle();						
				espacoStyle2.setFont(fontEspaco);
				espacoStyle2.setFillForegroundColor(HSSFColor.HSSFColorPredefined.ORANGE.getIndex());
				espacoStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				espacoStyle2.setRotation((short) 90);							
				espacoStyle2.setVerticalAlignment(VerticalAlignment.CENTER); // Centralizar no vertical							

				dayStyle = workbook.createCellStyle();			    	
				dayStyle.setFont(fontDataHeader);
				dayStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.ROYAL_BLUE.getIndex());
				dayStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				dayStyle.setAlignment(HorizontalAlignment.CENTER);	
				dayStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Centralizar no vertical				

				intervaloStyle = workbook.createCellStyle();
				intervaloStyle.setAlignment(HorizontalAlignment.CENTER);
				intervaloStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());				
				intervaloStyle.setFont(fontIntervalo);

				blankStyle = workbook.createCellStyle();
				blankStyle.setBorderRight(BorderStyle.THIN);
				blankStyle.setBorderLeft(BorderStyle.THIN);

				blankStyle2 = workbook.createCellStyle();				
				blankStyle2.setBorderLeft(BorderStyle.THIN);

				//Fill Border
				cellBorderStyle(subHeader); 
				cellBorderStyle(reportTime);
				cellBorderStyle(dayStyle);
				cellBorderStyle(intervaloStyle);
				cellBorderStyle(style1); 
				cellBorderStyle(redColorBackground);							
				cellBorderStyle(blueColorBackground);

				/* Estilo End */
			}

			public void borderLines(SXSSFWorkbook workbook, SXSSFSheet sheet, PropertyTemplate propertyTemplate) {

				sheet.getSheetName();
				propertyTemplate.applyBorders(sheet);			

			}

			public void columnsWidth(SXSSFSheet sheet) {

				// Column Width
				sheet.setColumnWidth(0, 1000);
				sheet.setColumnWidth(1, 1600);
				sheet.setColumnWidth(2, 2500);
				sheet.setColumnWidth(3, 1800);
				sheet.setColumnWidth(4, 800);
				sheet.setColumnWidth(5, 1800);
				sheet.setColumnWidth(18, 4500);
				sheet.setColumnWidth(22, 4500);
				sheet.setColumnWidth(27, 4500);
				sheet.setColumnWidth(31, 4500);
				sheet.setColumnWidth(36, 4500);
				sheet.setColumnWidth(40, 4500);
				sheet.setColumnWidth(45, 4500);
				sheet.setColumnWidth(49, 4500);
				sheet.setColumnWidth(54, 4500);
				sheet.setColumnWidth(58, 4500);
				sheet.setColumnWidth(63, 4500);
				sheet.setColumnWidth(67, 4500);

				//Espaço em branco
				sheet.setColumnWidth(14, 1300);
				sheet.setColumnWidth(23, 1300);
				sheet.setColumnWidth(32, 1300);
				sheet.setColumnWidth(41, 1300);
				sheet.setColumnWidth(50, 1300);
				sheet.setColumnWidth(59, 1300);	

			}

			public void mergeCells(SXSSFSheet sheet) {			

				sheet.addMergedRegion(CellRangeAddress.valueOf("A1:L1"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("A2:A36"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("C3:F3"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("G3:J3"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("K3:N3"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("P3:S3"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("T3:W3"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("Y3:AB3"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("AC3:AF3"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("AH3:AK3"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("AL3:AO3"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("AQ3:AT3"));							
				sheet.addMergedRegion(CellRangeAddress.valueOf("AU3:AX3"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("AZ3:BC3"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("BD3:BG3"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("BI3:BL3"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("BM3:BP3"));	

				sheet.addMergedRegion(CellRangeAddress.valueOf("C4:C5"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("D4:F5"));						
				sheet.addMergedRegion(CellRangeAddress.valueOf("G4:J4"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("K4:N4"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("P4:S4"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("T4:W4"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("Y4:AB4"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("AC4:AF4"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("AH4:AK4"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("AL4:AO4"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("AQ4:AT4"));							
				sheet.addMergedRegion(CellRangeAddress.valueOf("AU4:AX4"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("AZ4:BC4"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("BD4:BG4"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("BI4:BL4"));
				sheet.addMergedRegion(CellRangeAddress.valueOf("BM4:BP4"));

			}		

			public void cellBorderStyle(CellStyle cell) {

				cell.setBorderTop(BorderStyle.THIN);
				cell.setBorderRight(BorderStyle.THIN);
				cell.setBorderLeft(BorderStyle.THIN);
				cell.setBorderBottom(BorderStyle.THIN);

			}

			public void createSheets(int sheetIndex, int equip) throws Exception {
				
				initializeSentidoExcel(equip);  
								
				sat = new SAT();		
				sat = equipDao.headerInfoSAT(equips[sheetIndex]);	
															
				sheetName[sheetIndex] = sat.getNome();								
				sheet = workbook.createSheet(sheetName[sheetIndex]); // Criar nova folha para o arquivo	
				rowDados = new SXSSFRow(sheet);
						
				sentidoExcelHeader(sheetIndex, sat.getFaixa1());		
				createExcelSheet(sheet, propertyTemplate, sheetIndex);
				
			}
			
	// -------------------------------------------------------------------------------------------------------------

	    /* Métodos para criar o Excel - END */	
				
			public void updateForm() {
				
				/*RequestContext.getCurrentInstance().update("idformdialog:displayImage1");
				RequestContext.getCurrentInstance().update("idformdialog:displayMessage1");			    
				RequestContext.getCurrentInstance().update("idformdialog:displayImage2");
				RequestContext.getCurrentInstance().update("idformdialog:displayMessage2");
				RequestContext.getCurrentInstance().update("idformdialog:display");	*/
				
				SessionUtil.getFacesContext().getPartialViewContext().getRenderIds().add("form-info:display");
			    
			}
			
			public void updateCloseButton() {
				
				SessionUtil.getFacesContext().getPartialViewContext().getRenderIds().add("form-info:dismiss-modal");
				
			}
			
			public void updateExcelButton() {
				
				SessionUtil.getFacesContext().getPartialViewContext().getRenderIds().add("form-table:excel-act");
				
			}
			
			
		    public void updateDatable() {				
				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form-table:data-table");		
			}

			private int number;

			public int getNumber() {
				return number;
			}

			public void increment() {
				number++;
			}

			public void mess(int flag) {
				
				stop = false;
				
				if(flag == 0) 	
					pausa(3000);	  			
							
				if(flag == 1) {
					if(equips.length > 1)    
					displayMessage = localeSat.getStringKey("$label_period_flow_message_begin")
							      + "\n"+localeSat.getStringKey("$label_period_flow_message_create_sheets");
					
					else displayMessage += localeSat.getStringKey("$label_period_flow_message_begin")
						      + "\n"+localeSat.getStringKey("$label_period_flow_message_create_sheet");
					updateForm();
				    pausa(3000);
				}
		        				
				if(flag == 2) {
					if(equips.length > 1)  
					 displayMessage +="\n"+localeSat.getStringKey("$label_period_flow_message_created_sheets");		
					 else displayMessage +="\n"+localeSat.getStringKey("$label_period_flow_message_created_sheet");
					updateForm();
				   // pausa(3000);
				}
				
			   if(flag == 3) 		{		   
				    displayMessage += "\n"+localeSat.getStringKey("$label_period_flow_message_process_sheets")+" "+sheetName[indice]+" ...";
				    updateForm();
				    
			   }
									
		        if(flag == 4) {
		        	
		        	if(equips.length > 1)        	
		        	    displayMessage += "\n"+localeSat.getStringKey("$label_period_flow_message_end_process_sheets");        	
		        	  else displayMessage += "\n"+localeSat.getStringKey("$label_period_flow_message_end_process_sheet");
		        	
		        	updateForm();         	
		        }
							   
		        if(flag == 5) {
		   			displayMessage += "\n"+localeSat.getStringKey("$label_period_flow_message_end_process_sheets") + 
		   			                  "\n"+localeSat.getStringKey("$label_period_flow_message_end_process_sheet");
		   			updateForm();
		        }
		   		        
		        if(flag == 6)     {  		
		  	      displayMessage += "";
		  	      updateForm();  	      
		        }
		        
		        if(flag == 7) stop = true;        
		        
		        //if(stop)      	
		        //	RequestContext.getCurrentInstance().execute("PF('poll').stop();");        	  
		        	        
		        //else System.out.println("step by step");
		        
		        updateDatable(); // Atualizar tabela com registros
		        
			}
		   
			public void processaDados(int sheetIndex, int equip, DateMethods dtm, SAT sat) throws Exception {	
															
				try {						
				
					 //System.out.println("-----------------");
					  //System.out.println("Entrando no Loop ...");

						if(ultimo_equipamento != sheetIndex) // Na troca de equipamento necessita incrementar mais uma posição
							pos += ((daysInMonth - 1) * tam);	

						equip = Integer.parseInt(equips[sheetIndex]);				
				
						String date, interval;
						int autoSumS1, comSumS1, motoSumS1, totalSumS1,autoSumS2, comSumS2, motoSumS2,
						totalSumS2, speedAutoS1, speedComS1, speedMotoS1, speedTotalS1, speedAutoS2, speedComS2,
						speedMotoS2, speedTotalS2, speed50thAutoS1, speed50thComS1, speed50thMotoS1, speed50thTotalS1, speed50thAutoS2, speed50thComS2, 
						speed50thMotoS2,speed50thTotalS2, speed85thAutoS1, speed85thComS1, speed85thMotoS1, speed85thTotalS1, speed85thAutoS2, speed85thComS2, speed85thMotoS2,
						speed85thTotalS2, speedMaxAutoS1, speedMaxComS1, speedMaxMotoS1, speedMaxTotalS1, speedMaxAutoS2, speedMaxComS2, speedMaxMotoS2, speedMaxTotalS2,										
						speedMinAutoS1, speedMinComS1, speedMinMotoS1, speedMinTotalS1, speedMinAutoS2, speedMinComS2, speedMinMotoS2, speedMinTotalS2, speedStdAutoS1,						
						speedStdComS1, speedStdMotoS1, speedStdTotalS1, speedStdAutoS2, speedStdComS2, speedStdMotoS2, speedStdTotalS2;	
									
						data = dtm.preencherDataFluxoPeriodo(startDate, endDate, tamanho, tam);								
						days = dtm.preencherDias(tamanho, tam);	
																	
						if(period.equals("15 minutes")) {		
						   intervalo = dtm.intervalo15Minutos(tamanho);
						   interInicio = dtm.intervalo15Inicio(tamanho);	
						   interFim = dtm.intervalo15Fim(tamanho);	
						   separador = dtm.intervaloSeparador(tamanho);
				        }
						
						if(period.equals("01 hour")) {	
							intervalo = dtm.preencherHoraContagem(tamanho);
							interInicio = dtm.intervaloFluxoHoraInicio(tamanho);	
							interFim = dtm.intervaloFluxoHoraFim(tamanho);	
							separador = dtm.intervaloSeparador(tamanho);
						}
						
						if(period.equals("24 hours")) {	
					       intervalo = dtm.intervalo24Horas(tamanho);
						   interInicio = dtm.intervaloSeparador24Horas(tamanho);	
						   interFim = dtm.intervaloSeparador24Horas(tamanho);	
						   separador = dtm.intervaloSeparador(tamanho);		          
						}			
															 
						index = increment * sheetIndex;					
								
						equipamentoHeader(index, incEquip, tamanho, sat.getNome(), sat.getKm(), sat.getEstrada(), sat.getFaixa1()); //Preenche nome do equipamento na tabela 
					
						sentidoHeader(index, tamanho, sat.getFaixa1()); // Preencher sentido na DataTable
																		
						lista = dao.getVehicles(startDate, endDate, equips[fill], period, sat);		
						
						if(!lista.isEmpty()) {

							for(FluxoPeriodo pe : lista) {
								
								System.out.println(pe.getDate());

								date = pe.getDate();
								interval = pe.getInterval();	
								
								autoSumS1 = pe.getAutoS1();
								comSumS1 = pe.getComS1();
								motoSumS1 = pe.getMotoS1();
								totalSumS1 = pe.getTotalS1();
								autoSumS2 = pe.getAutoS2();
								comSumS2 = pe.getComS2();
								motoSumS2 = pe.getMotoS2();
								totalSumS2 = pe.getTotalS2();

								speedAutoS1 = pe.getSpeedAutoS1();
								speedComS1 = pe.getSpeedComS1();
								speedMotoS1 = pe.getSpeedMotoS1();
								speedTotalS1 = pe.getSpeedTotalS1();						
								speedAutoS2 = pe.getSpeedAutoS2();
								speedComS2 = pe.getSpeedComS2();
								speedMotoS2 = pe.getSpeedMotoS2();
								speedTotalS2 = pe.getSpeedTotalS2();

								speed50thAutoS1 = pe.getSpeed50thAutoS1();
								speed50thComS1 = pe.getSpeed50thComS1();
								speed50thMotoS1 = pe.getSpeed50thMotoS1();
								speed50thTotalS1 = pe.getSpeed50thTotalS1();						
								speed50thAutoS2 = pe.getSpeed50thAutoS2();
								speed50thComS2 = pe.getSpeed50thComS2();
								speed50thMotoS2 = pe.getSpeed50thMotoS2();
								speed50thTotalS2 = pe.getSpeed50thTotalS2();

								speed85thAutoS1 = pe.getSpeed85thAutoS1();
								speed85thComS1 = pe.getSpeed85thComS1();
								speed85thMotoS1 = pe.getSpeed85thMotoS1();
								speed85thTotalS1 = pe.getSpeed85thTotalS1();						
								speed85thAutoS2 = pe.getSpeed85thAutoS2();
								speed85thComS2 = pe.getSpeed85thComS2();
								speed85thMotoS2 = pe.getSpeed85thMotoS2();
								speed85thTotalS2 = pe.getSpeed85thTotalS2();

								speedMaxAutoS1 = pe.getSpeedMaxAutoS1();
								speedMaxComS1 = pe.getSpeedMaxComS1();
								speedMaxMotoS1 = pe.getSpeedMaxMotoS1();
								speedMaxTotalS1 = pe.getSpeedMaxTotalS1();
								speedMaxAutoS2 = pe.getSpeedMaxAutoS2();
								speedMaxComS2 = pe.getSpeedMaxComS2();
								speedMaxMotoS2 = pe.getSpeedMaxMotoS2();
								speedMaxTotalS2 = pe.getSpeedMaxTotalS2();
								
								speedMinAutoS1 = pe.getSpeedMinAutoS1();
								speedMinComS1 = pe.getSpeedMinComS1();
								speedMinMotoS1 = pe.getSpeedMinMotoS1();
								speedMinTotalS1 = pe.getSpeedMinTotalS1();
								speedMinAutoS2 = pe.getSpeedMinAutoS2();
								speedMinComS2 = pe.getSpeedMinComS2();
								speedMinMotoS2 = pe.getSpeedMinMotoS2();
								speedMinTotalS2 = pe.getSpeedMinTotalS2();

								speedStdAutoS1 = pe.getSpeedStdAutoS1();						
								speedStdComS1 = pe.getSpeedStdComS1();
								speedStdMotoS1 = pe.getSpeedStdMotoS1();
								speedStdTotalS1 = pe.getSpeedStdTotalS1();
								speedStdAutoS2 = pe.getSpeedStdAutoS2();
								speedStdComS2 = pe.getSpeedStdComS2();
								speedStdMotoS2 = pe.getSpeedStdMotoS2();
								speedStdTotalS2= pe.getSpeedStdTotalS2();				
														
								if(period.equals("01 hour") || period.equals("06 hours"))
								   hr = Integer.parseInt(interval.substring(0, 2));
								
								if(!period.equals("24 hours") && !period.equals("01 hour") && !period.equals("06 hours")) {
								    hr = Integer.parseInt(interval.substring(0, 2));
								    minuto =  Integer.parseInt(interval.substring(3, 5));						
								}

								// Restrição caso não haja dados nos primeiros registros
								if ((dtInicio != null) && (!date.equals(dtInicio))) {   // Executa uma unica vez
									
									if(period.equals("24 hours"))
										interResp = (int) dtm.daysDifference(dtInicio, date);

									else interResp = daysDifference(dtInicio, date, rangeInterval);	
									
									pos+= interResp;
									dtInicio = null;

								} else if (!date.equals(data_anterior)) {								
																
									if(period.equals("24 hours"))
										interResp = (int) dtm.daysDifference(data_anterior, date);
									   
									else interResp = daysDifference(data_anterior, date, rangeInterval);	
									
									pos+= interResp;							
								} 								
																
								if(period.equals("15 minutes")) 
								        preencherDados15Min(pos, hr, minuto, autoSumS1, comSumS1, motoSumS1, totalSumS1, autoSumS2, comSumS2, motoSumS2, totalSumS2,
										speedAutoS1, speedComS1, speedMotoS1, speedTotalS1 ,speedAutoS2, speedComS2, speedMotoS2, speedTotalS2, 
										speed50thAutoS1, speed50thComS1, speed50thMotoS1, speed50thTotalS1, speed50thAutoS2, speed50thComS2, speed50thMotoS2, speed50thTotalS2,
										speed85thAutoS1, speed85thComS1, speed85thMotoS1, speed85thTotalS1, speed85thAutoS2, speed85thComS2, speed85thMotoS2, speed85thTotalS2,
										speedMaxAutoS1, speedMaxComS1, speedMaxMotoS1, speedMaxTotalS1, speedMaxAutoS2, speedMaxComS2, speedMaxMotoS2, speedMaxTotalS2,
										speedMinAutoS1, speedMinComS1, speedMinMotoS1, speedMinTotalS1, speedMinAutoS2, speedMinComS2, speedMinMotoS2, speedMinTotalS2,								
										speedStdAutoS1, speedStdComS1, speedStdMotoS1, speedStdTotalS1, speedStdAutoS2, speedStdComS2, speedStdMotoS2, speedStdTotalS2); 

								
								if(period.equals("01 hour")) 
									
							        preencherDados01Hora(pos, hr, autoSumS1, comSumS1, motoSumS1, totalSumS1, autoSumS2, comSumS2, motoSumS2, totalSumS2,
									speedAutoS1, speedComS1, speedMotoS1, speedTotalS1 ,speedAutoS2, speedComS2, speedMotoS2, speedTotalS2, 
									speed50thAutoS1, speed50thComS1, speed50thMotoS1, speed50thTotalS1, speed50thAutoS2, speed50thComS2, speed50thMotoS2, speed50thTotalS2,
									speed85thAutoS1, speed85thComS1, speed85thMotoS1, speed85thTotalS1, speed85thAutoS2, speed85thComS2, speed85thMotoS2, speed85thTotalS2,
									speedMaxAutoS1, speedMaxComS1, speedMaxMotoS1, speedMaxTotalS1, speedMaxAutoS2, speedMaxComS2, speedMaxMotoS2, speedMaxTotalS2,
									speedMinAutoS1, speedMinComS1, speedMinMotoS1, speedMinTotalS1, speedMinAutoS2, speedMinComS2, speedMinMotoS2, speedMinTotalS2,								
									speedStdAutoS1, speedStdComS1, speedStdMotoS1, speedStdTotalS1, speedStdAutoS2, speedStdComS2, speedStdMotoS2, speedStdTotalS2); 
								
								
		                           if(period.equals("24 hours")) 
									
							        preencherDados24Horas(pos, autoSumS1, comSumS1, motoSumS1, totalSumS1, autoSumS2, comSumS2, motoSumS2, totalSumS2,
									speedAutoS1, speedComS1, speedMotoS1, speedTotalS1 ,speedAutoS2, speedComS2, speedMotoS2, speedTotalS2, 
									speed50thAutoS1, speed50thComS1, speed50thMotoS1, speed50thTotalS1, speed50thAutoS2, speed50thComS2, speed50thMotoS2, speed50thTotalS2,
									speed85thAutoS1, speed85thComS1, speed85thMotoS1, speed85thTotalS1, speed85thAutoS2, speed85thComS2, speed85thMotoS2, speed85thTotalS2,
									speedMaxAutoS1, speedMaxComS1, speedMaxMotoS1, speedMaxTotalS1, speedMaxAutoS2, speedMaxComS2, speedMaxMotoS2, speedMaxTotalS2,
									speedMinAutoS1, speedMinComS1, speedMinMotoS1, speedMinTotalS1, speedMinAutoS2, speedMinComS2, speedMinMotoS2, speedMinTotalS2,								
									speedStdAutoS1, speedStdComS1, speedStdMotoS1, speedStdTotalS1, speedStdAutoS2, speedStdComS2, speedStdMotoS2, speedStdTotalS2); 
								
								   data_anterior = date; // Atualiza o dia						
							}	

							ultimo_equipamento = sheetIndex;

							//System.out.println("Encerrando processamento ...");
						   // System.out.println("-----------------");
							//System.out.println("Saindo do Loop ...");
							//System.out.println("-----------------");

						} else  pos += tam;
						
						//System.out.println("Preenchendo dados na Folha "+sheetName[indice]+" ...");
						preencherDadosExcel(workbook, propertyTemplate, sheetIndex, sheetName[sheetIndex], tam);
												  
					//System.out.println("------------------");
					//System.out.println("Processando Arquivo ...");
					//System.out.println("------------------");

					/* FileOutputStream out = new FileOutputStream("C:/teste/temp_files/teste.xlsx");
							  workbook.write(out);
							  out.close();	
							  workbook.dispose();*/

				     	//System.out.println("------------------");
						//System.out.println("Arquivo do Excel Criado com Sucesso!");
						//System.out.println("------------------");					
				   
				}catch(Exception ex) {
					ex.printStackTrace();
				}		
			  }
			
			public void populateTable() throws Exception {
				
				resultList = new ArrayList<Builder>();
																
				for(int i=0; i < tamanho; i++) {
					
					resultList.add(new FluxoPeriodo.Builder().equip(nomeSats[i]).date(data[i]).time(intervalo[i])
											.direction1(sentido1[i])
											.lightS1(auto1[i])
											.commS1(com1[i])
											.totalS1(total1[i])
											.speedS1(totalVM1[i])
											.direction2(sentido2[i])
											.lightS2(auto2[i])
											.commS2(com2[i])
											.totalS2(total2[i])
											.speedS2(totalVM2[i]));		
																																
				               }	
				
				//for(int i=0; i < tamanho; i++)
						//	System.out.println(auto1[i]+" "+sentido1[i]);
			       }
			
			
			 public void pausa(int milissegundos) {
			        try {	            
			            Thread.sleep(milissegundos);
			        } catch (InterruptedException e) {
			            
			        }
			    }
			 
			 public void updateTime() {
				 
				 if(pause) {			 
				  stop = true;	  
				 }
				 else stop = false;		 
			 }	 
			 
			 public int tamanhoPorPeriodo(String period) {
				 
				 int tam = 0;
				 
				 if(period.equals("05 minutes"))
				     tam = 288;
				    if(period.equals("06 minutes"))
				         tam = 240;
					    if(period.equals("15 minutes"))
							tam = 96;
							   if(period.equals("01 hour"))
								   tam = 24;
							   if(period.equals("06 hours"))
								     tam = 4;
								     if(period.equals("24 hours"))
									   tam = 1;
				 		 
				      return tam;
				 
			 }
			 
			     public String periodName(String period) {

				     String name = "";

				     if(period.equals("05 minutes"))
					     name = "05_min";
				     if(period.equals("06 minutes"))
					     name = "06_min";
				        if(period.equals("15 minutes"))
					       name = "15_min";
				          if(period.equals("01 hours"))
					          name = "01_hr";
				          if(period.equals("06 hours"))
					          name = "06_hrs";
				             if(period.equals("24 hourss"))
					            name = "24_hrs";

				        return name;
			        } 
			     
			     
			     public void instaciarProcessaDados(DateMethods dtm) throws Exception {
			    	 
			    	 lista = new ArrayList<FluxoPeriodo>();	

						per = new FluxoPeriodo();
						dao = new FluxoPeriodoDAO();				
					
						//mes = dtm.selectedMonth(month);
						int mth = Integer.parseInt(month);
						int yr = Integer.parseInt(year);
						
						//System.out.println(year);
						//System.out.println(month);
						
						monthABR = dtm.abrevMes(month); 
						yearABR = dtm.abrevAno(year); 

						YearMonth yearMonthObject = YearMonth.of(yr, mth);
						daysInMonth = yearMonthObject.lengthOfMonth();			

						int diaInicial = 1;
						
						startDate = dtm.createData(diaInicial, mth, yr);
						endDate = dtm.createData(daysInMonth, mth, yr);
									
						tam = tamanhoPorPeriodo(period);
						
						increment = 0; incEquip = 0;
								
						if(tam == 1) {increment = daysInMonth; incEquip = daysInMonth;}
						else if(tam == 4) {increment = daysInMonth * tam; incEquip = daysInMonth * tam;}
						else {increment = (tam * daysInMonth); incEquip = tam;}
								
						tamanho = ((daysInMonth * tam) * equips.length);	
														
						initVariables(tamanho); 
						initializeSentidoExcel(equips.length);

						dtInicio = startDate;			
						data_anterior = startDate;
						hora_anterior = -1;				
						rangeInterval = tam;
						pos = 0;
						hrPos = 0;		
						hr = 0; 
						minuto = 0; 
						interResp = 0; 
						ultimo_equipamento = 0;	
						index = 0;								
			       }
			     	     	     
			     public void equipamentoHeader(int indice, int increment, int tamanho, String sat, String km, String road, String faixa1) {
			    	 	    	 
			    	 for(int i=indice; i < tamanho; i+=increment)  		 
			    		 nomeSats[i] = sat+", KM"+km+", "+road;
			 	    }  			     		     
			     
			     public void sentidoHeader(int indice, int tamanho, String faixa1) {
		          
			     for(int i=indice; i < tamanho; i++) {	    		 
			    	    	 
			    	 if(faixa1.equals("N")) {
			    		 sentido1[i] = localeDir.getStringKey("directions_tab_north");
			    		 sentido2[i] = localeDir.getStringKey("directions_tab_south");	    		
			    	 }
			    	 
			    	 if(faixa1.equals("S")) {
			    		 sentido1[i] = localeDir.getStringKey("directions_tab_south");	
			    		 sentido2[i] = localeDir.getStringKey("directions_tab_north");		    		
			    	 }
			    	 
			    	 if(faixa1.equals("L")) {
			    		 sentido1[i] = localeDir.getStringKey("directions_tab_east");	
			    		 sentido2[i] = localeDir.getStringKey("directions_tab_west");	    		
			    	 }
			    	 
			    	 if(faixa1.equals("O")) {
			    		 sentido1[i] = localeDir.getStringKey("directions_tab_west");	
			    		 sentido2[i] = localeDir.getStringKey("directions_tab_east");	    		 
			    	 }	    	 
			       }
			     }	 
			     
			     public void sentidoExcelHeader(int sheetIndex, String faixa1) {
			    	 
			    	 if(faixa1.equals("N")) {
			    		 sentidoExcel1[sheetIndex] = localeDir.getStringKey("directions_tab_north")+"/"+localeDir.getStringKey("directions_tab_west");
			    		 sentidoExcel2[sheetIndex] = localeDir.getStringKey("directions_tab_north")+"/"+localeDir.getStringKey("directions_tab_east");	    		
			    	 }
			    	 
			    	 if(faixa1.equals("S")) {
			    		 sentidoExcel1[sheetIndex] = localeDir.getStringKey("directions_tab_north")+"/"+localeDir.getStringKey("directions_tab_east");
			    		 sentidoExcel2[sheetIndex] = localeDir.getStringKey("directions_tab_north")+"/"+localeDir.getStringKey("directions_tab_west");    		
			    	 }
			    	 
			    	 if(faixa1.equals("L")) {
			    		 sentidoExcel1[sheetIndex] = localeDir.getStringKey("directions_tab_east")+"/"+localeDir.getStringKey("directions_tab_south");
			    		 sentidoExcel2[sheetIndex] = localeDir.getStringKey("directions_tab_west")+"/"+localeDir.getStringKey("directions_tab_north");    		
			    	 }
			    	 
			    	 if(faixa1.equals("O")) {
			    		 sentidoExcel1[sheetIndex] = localeDir.getStringKey("directions_tab_west")+"/"+localeDir.getStringKey("directions_tab_north"); 
			    		 sentidoExcel2[sheetIndex] = localeDir.getStringKey("directions_tab_east")+"/"+localeDir.getStringKey("directions_tab_south");    		 
			    	 }	    	 
			      }  
		     	     


}
