package br.com.tracevia.webapp.controller.sat;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
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
import javax.faces.bean.ViewScoped;
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

import br.com.tracevia.webapp.controller.global.LanguageBean;
import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.sat.FluxoPeriodoDAO;
import br.com.tracevia.webapp.log.SystemLog;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.ColumnModel;
import br.com.tracevia.webapp.model.global.ListEquipments;
import br.com.tracevia.webapp.model.global.ReportBuild;
import br.com.tracevia.webapp.model.sat.FluxoPeriodo;
import br.com.tracevia.webapp.model.sat.FluxoPeriodo.Builder;
import br.com.tracevia.webapp.model.sat.SAT;
import br.com.tracevia.webapp.model.sat.SatReports;
import br.com.tracevia.webapp.util.ExcelUtil;
import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.SessionUtil;

@ManagedBean(name = "fluxoPeriodo")
@ViewScoped
public class FluxoPeriodoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static String FONT_ARIAL = "Arial";
	
	ExcelUtil utilSheet;
	
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
						
			private ReportBuild build;
					
			LocaleUtil localeSat, localeDir, localeReports, localeSheet, localeCalendar;
				
			List<SAT> satList;
					
			SAT sat;
			
			String[] equips;

			String period, month, year;
			
			String[][] resultQuery;
			
			// --------------------------------------------------------------------------------------------------------------

	private Date date;	
	private int daysInMonth;
	private int sixHoursRows;
	private String[] days;
	private int mes;
	private int ano;
	private int tamanho;
	private int tam;
	private int hora_anterior;
	private int[] selectedEquipamento;
	private String selectedAno;
	private String selectedMes;	
	private String selectedPeriodo;
	private String startDate; 
	private String endDate;
	private String data_anterior;	
	private int equip_anterior;	
	private String nomeSat;
	private String estradaSat;  
	private String kmSat;
	private String yearABR;
	private String  monthABR;
	private String displayMessage;
	private String displayImage;
	private String displayMessage2;
	private String displayImage2;
	private String imagem, imagem2;
	private String mensagem, mensagem2;
	private int rows;
	private int step;
	private boolean stop;
	private String satName;
	private String road; 
	private String kilometer;
	private String faixa1;
	private int numFaixas;
	
	private SXSSFWorkbook workbook;		
	private SXSSFSheet sheet;
	private SXSSFRow rowDados;
	private PropertyTemplate propertyTemplate;
			
	DateTimeApplication dta;
	TranslationMethods tm;

	FluxoPeriodoDAO dao;	
	EquipmentsDAO equipDao;
	LanguageBean lang;

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
	int interResp, last_index;		
	int rangeHour, rangeInterval, hrPos, creation, fill, increment, incEquip, fieldsLenght; 
	String dtInicio, fileName, data_inicial, data_final;

	String[] intervalo,interInicio, interFim, separador, data, horaIntervalo, hora, sheetName;

	long start, end, elapsed;
	
	int minuto, iterator, pos, hr, lin, col, p, empty, inc, index, indexInc;
		
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
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getDaysInMonth() {
		return daysInMonth;
	}

	public void setDaysInMonth(int daysInMonth) {
		this.daysInMonth = daysInMonth;
	}

	public String[] getDays() {
		return days;
	}

	public void setDays(String[] days) {
		this.days = days;
	}

	public int getNumFaixas() {
		return numFaixas;
	}

	public void setNumFaixas(int numFaixas) {
		this.numFaixas = numFaixas;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

	public int getTam() {
		return tam;
	}

	public void setTam(int tam) {
		this.tam = tam;
	}

	public int getHora_anterior() {
		return hora_anterior;
	}

	public void setHora_anterior(int hora_anterior) {
		this.hora_anterior = hora_anterior;
	}

	public String getSelectedAno() {
		return selectedAno;
	}

	public void setSelectedAno(String selectedAno) {
		this.selectedAno = selectedAno;
	}

	public String getSelectedMes() {
		return selectedMes;
	}

	public void setSelectedMes(String selectedMes) {
		this.selectedMes = selectedMes;
	}

	public int[] getSelectedEquipamento() {
		return selectedEquipamento;
	}

	public void setSelectedEquipamento(int[] selectedEquipamento) {
		this.selectedEquipamento = selectedEquipamento;
	}

	public String getSelectedPeriodo() {
		return selectedPeriodo;
	}

	public void setSelectedPeriodo(String selectedPeriodo) {
		this.selectedPeriodo = selectedPeriodo;
	}

	public String getData_anterior() {
		return data_anterior;
	}

	public void setData_anterior(String data_anterior) {
		this.data_anterior = data_anterior;
	}

	public String getNomeSat() {
		return nomeSat;
	}

	public void setNomeSat(String nomeSat) {
		this.nomeSat = nomeSat;
	}

	public String getEstradaSat() {
		return estradaSat;
	}

	public void setEstradaSat(String estradaSat) {
		this.estradaSat = estradaSat;
	}

	public String getKmSat() {
		return kmSat;
	}

	public void setKmSat(String kmSat) {
		this.kmSat = kmSat;
	}

	public String getYearABR() {
		return yearABR;
	}

	public void setYearABR(String yearABR) {
		this.yearABR = yearABR;
	}

	public String getMonthABR() {
		return monthABR;
	}

	public void setMonthABR(String monthABR) {
		this.monthABR = monthABR;
	}

	public SXSSFWorkbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(SXSSFWorkbook workbook) {
		this.workbook = workbook;
	}

	public SXSSFSheet getSheet() {
		return sheet;
	}

	public void setSheet(SXSSFSheet sheet) {
		this.sheet = sheet;
	}

	public SXSSFRow getRowDados() {
		return rowDados;
	}

	public void setRowDados(SXSSFRow rowDados) {
		this.rowDados = rowDados;
	}

	public PropertyTemplate getPropertyTemplate() {
		return propertyTemplate;
	}

	public void setPropertyTemplate(PropertyTemplate propertyTemplate) {
		this.propertyTemplate = propertyTemplate;
	}
	
	public String getDisplayMessage() {
		return displayMessage;
	}

	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
	}

	public String getDisplayImage() {
		return displayImage;
	}

	public void setDisplayImage(String displayImage) {
		this.displayImage = displayImage;
	}

	public String getDisplayMessage2() {
		return displayMessage2;
	}

	public void setDisplayMessage2(String displayMessage2) {
		this.displayMessage2 = displayMessage2;
	}

	public String getDisplayImage2() {
		return displayImage2;
	}

	public void setDisplayImage2(String displayImage2) {
		this.displayImage2 = displayImage2;
	}	
	
	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getImagem2() {
		return imagem2;
	}

	public void setImagem2(String imagem2) {
		this.imagem2 = imagem2;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMensagem2() {
		return mensagem2;
	}

	public void setMensagem2(String mensagem2) {
		this.mensagem2 = mensagem2;
	}	

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}
		
	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}
		
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public String getSatName() {
		return satName;
	}

	public void setSatName(String satName) {
		this.satName = satName;
	}

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public String getKilometer() {
		return kilometer;
	}

	public void setKilometer(String kilometer) {
		this.kilometer = kilometer;
	}

	public String getFaixa1() {
		return faixa1;
	}

	public void setFaixa1(String faixa1) {
		this.faixa1 = faixa1;
	}
	
	public int getSixHoursRows() {
		return sixHoursRows;
	}

	public void setSixHoursRows(int sixHoursRows) {
		this.sixHoursRows = sixHoursRows;
	}
	
	public int getEquip_anterior() {
		return equip_anterior;
	}

	public void setEquip_anterior(int equip_anterior) {
		this.equip_anterior = equip_anterior;
	}
		
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
		
		utilSheet = new ExcelUtil();
						
	    build = new ReportBuild();
		
		equipments = new ArrayList<SelectItem>();
		months = new ArrayList<SelectItem>();
		years = new ArrayList<SelectItem>();
			
		displayMessage = "";
				
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
	
	  public void processInformations() throws Exception {		  
					  
	   dta = new DateTimeApplication();	   
	   tm = new TranslationMethods();
	  
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
					  		
		 workbook = new SXSSFWorkbook(-1); //Criar Pasta do Excel	
		 propertyTemplate = new PropertyTemplate();
		 sheetName = new String[equips.length];
		 
		 fileName = localeSat.getStringKey("via_paulista_flow_per_period_file_name")+"_"+tm.periodName(period)+"_"+tm.MonthAbbreviation(month)+"_"+tm.yearAbbreviation(year);
		 		 
	    satList = new ArrayList<SAT>();	
		satList = equipDao.ListSATinfoHeader(equips);	
		
		  //--- Initializing --- //	
		
		  step = 1; //
			  message(step);	// CREATE SHEETS MESSAGE
					 				 		 								   		       		
         step = 2;
         	message(step);	 // CREATE SHEETS ENDED MESSAGE
                		 
		 instaciarProcessaDados(dta);	
		 		 
		 for(indice=0; indice < equips.length; indice++) {				 
																																		 
				 step = 3;
				 	message(step);	// DATA PROCESS MESSAGE		
				 	
				 step = 7;	
				 	message(step);		
				 				 											
				 processaDados(indice, equips[indice], dta, satList);
				 			 			 			
				 if(indice == (equips.length-1)) {
				 
					     step = 4;
					     	message(step);	// PROCESS DATA ENDED						     	
				  }			
		   	}
		 
		    step = 5;
		    	message(step);	// ENDING PROCESS MESSAGE
		    	
		    	 step = 7;	
				 	message(step);
					 		   		   		    		    
		    step = 6;		   
		    	message(step);	// PROCESS ENDED MESSAGE	
		    	
		    	 step = 7;	
				 	message(step);
				 								    	 									
		    //populateTable(); // POP DATATABLE
					
			// ACTIVATE EXCEL BUTTON
			SessionUtil.executeScript("$('#activate-excel-act').prop('disabled', false);");
																
			// REDRAW TABLE
			//columns = build.drawTable(build.fields, build.fieldObjectValues);								        
	    }	  
	  
	// -------------------------------------------------------------------------------------------------------------
	  		
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

	
	public void initializeSentidoExcel(int equipamentos) {
		
		int tamanho = equipamentos;	
		
		sentidoExcel1 = new String[tamanho];
		sentidoExcel2 = new String[tamanho];
	}
		

	public int daysDifference(String dtInit, String dtEnd, int indice) throws ParseException {
		
		String dataIni = "";
		String dataEnd = "";

		int days=0, difference=0;

		dataIni = dta.StringDataBaseDateToBRFormat(dtInit);
		dataEnd = dta.StringDataBaseDateToBRFormat(dtEnd);

		days = (int) dta.diferencaDias(dataIni, dataEnd);
		difference = (days * indice);		

		return difference;
	}	
	
	// ---------------------------------------------------------------------------------

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
				
		workbook = null;
			
		setDaysInMonth(0);
		setRows(0);
		setTam(0);			
		setDisplayMessage("");	
		
		step = 0;
		
		updateForm();		
		
	}
	
	// ---------------------------------------------------------------------------------
	
	/** Método para limpar listas e variáveis de caixas de seleção
	 * @throws Exception
	 * @return void */

	public void resetStepView() throws Exception {
				
		setDisplayMessage("");	// RESET MESSAGE
					
		// RESET TEXTAREA INFO
		SessionUtil.executeScript("$('#display').val('');");
						
		//RESET FORM VALUES AND MULTISELECT
		SessionUtil.executeScript("$('#form').trigger('reset'); $(setTimeout(() => $('[multiple]').multiselect('selectAll', false).multiselect('updateButtonText'), 100));");
		
		// AFTER SUBMIT CLEAN FIELDS ICON
		SessionUtil.executeScript("$('#equips').val(''); $('span[for=equips]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');");
		SessionUtil.executeScript("$('#periods').val(''); $('span[for=periods]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');");
		SessionUtil.executeScript("$('#month').val(''); $('span[for=month]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');");
		SessionUtil.executeScript("$('#year').val(''); $('span[for=year]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');");
			
		
		
	}
	
	// ---------------------------------------------------------------------------------
		
	public void downloadFile() throws IOException {
			
		//System.out.println("Gerando arquivo para Donwload ... ");
				
		SessionUtil.getExternalContext().setResponseContentType("application/vnd.ms-excel");
		SessionUtil.getExternalContext().setResponseHeader("Content-Disposition",
				"attachment; filename=\""+fileName+".xlsx\"");
	
		OutputStream responseOutputStream = SessionUtil.getExternalContext().getResponseOutputStream(); 								
		workbook.write(responseOutputStream);
								
		SessionUtil.getFacesContext().responseComplete(); 
		responseOutputStream.close();
										
	}
		
	// ---------------------------------------------------------------------------------

	/* Métodos para criar o Excel */			
	public void createExcelSheet(SXSSFSheet sheet, PropertyTemplate propertyTemplate,  int sheetIndex) {

		dta = new DateTimeApplication();
		
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
				
		for(int i=1; i < 69; i++)
			rowDescription2.createCell(i);		

		rowDescription2.getCell(1).setCellValue("2");
		rowDescription2.getCell(2).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_model")+" - "+tm.periodName(period));

		rowDescription2.getCell(6).setCellValue(sentidoExcel1[sheetIndex]);
		rowDescription2.getCell(10).setCellValue(sentidoExcel2[sheetIndex]);

		rowDescription2.getCell(15).setCellValue(sentidoExcel1[sheetIndex]);
		rowDescription2.getCell(19).setCellValue(sentidoExcel2[sheetIndex]);

		rowDescription2.getCell(24).setCellValue(sentidoExcel1[sheetIndex]);
		rowDescription2.getCell(28).setCellValue(sentidoExcel2[sheetIndex]);

		rowDescription2.getCell(33).setCellValue(sentidoExcel1[sheetIndex]);
		rowDescription2.getCell(37).setCellValue(sentidoExcel2[sheetIndex]);

		rowDescription2.getCell(42).setCellValue(sentidoExcel1[sheetIndex]);
		rowDescription2.getCell(46).setCellValue(sentidoExcel2[sheetIndex]);

		rowDescription2.getCell(51).setCellValue(sentidoExcel1[sheetIndex]);							
		rowDescription2.getCell(55).setCellValue(sentidoExcel2[sheetIndex]);

		rowDescription2.getCell(60).setCellValue(sentidoExcel1[sheetIndex]);							
		rowDescription2.getCell(64).setCellValue(sentidoExcel2[sheetIndex]);	

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
				
		for(int i=1; i < 69; i++)
			rowDescription3.createCell(i);	
		
		rowDescription3.getCell(1).setCellValue("3");
		
		rowDescription3.getCell(2).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_day"));
		rowDescription3.getCell(3).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_hour"));

		rowDescription3.getCell(6).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_flow"));
		rowDescription3.getCell(10).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_flow"));

		rowDescription3.getCell(15).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_avg"));
		rowDescription3.getCell(19).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_avg"));

		rowDescription3.getCell(24).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_avg_50"));
		rowDescription3.getCell(28).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_avg_50"));

		rowDescription3.getCell(33).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_avg_85"));
		rowDescription3.getCell(37).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_avg_85"));

		rowDescription3.getCell(42).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_max"));
		rowDescription3.getCell(46).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_max"));

		rowDescription3.getCell(51).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_min"));							
		rowDescription3.getCell(55).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_min"));

		rowDescription3.getCell(60).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_std"));							
		rowDescription3.getCell(64).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_std"));

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
				
		for(int i=1; i < 69; i++)
			rowDescription4.createCell(i);	

		rowDescription4.getCell(1).setCellValue("4");
		rowDescription4.getCell(6).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
		rowDescription4.getCell(7).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
		rowDescription4.getCell(8).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
		rowDescription4.getCell(9).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_total_tab"));
		rowDescription4.getCell(10).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
		rowDescription4.getCell(11).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
		rowDescription4.getCell(12).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
		rowDescription4.getCell(13).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_total_tab"));

		rowDescription4.getCell(15).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
		rowDescription4.getCell(16).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
		rowDescription4.getCell(17).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
		rowDescription4.getCell(18).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_all_directions"));							
		rowDescription4.getCell(19).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
		rowDescription4.getCell(20).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
		rowDescription4.getCell(21).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
		rowDescription4.getCell(22).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_all_directions"));
		
		rowDescription4.getCell(24).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
		rowDescription4.getCell(25).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
		rowDescription4.getCell(26).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
		rowDescription4.getCell(27).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_all_directions"));							
		rowDescription4.getCell(28).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
		rowDescription4.getCell(29).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
		rowDescription4.getCell(30).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
		rowDescription4.getCell(31).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_all_directions"));

		rowDescription4.getCell(33).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
		rowDescription4.getCell(34).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
		rowDescription4.getCell(35).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
		rowDescription4.getCell(36).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_all_directions"));							
		rowDescription4.getCell(37).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
		rowDescription4.getCell(38).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
		rowDescription4.getCell(39).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
		rowDescription4.getCell(40).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_all_directions"));
		
		rowDescription4.getCell(42).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
		rowDescription4.getCell(43).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
		rowDescription4.getCell(44).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
		rowDescription4.getCell(45).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_all_directions"));							
		rowDescription4.getCell(46).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
		rowDescription4.getCell(47).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
		rowDescription4.getCell(48).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
		rowDescription4.getCell(49).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_all_directions"));
		
		rowDescription4.getCell(51).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
		rowDescription4.getCell(52).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
		rowDescription4.getCell(53).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
		rowDescription4.getCell(54).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_all_directions"));							
		rowDescription4.getCell(55).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
		rowDescription4.getCell(56).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
		rowDescription4.getCell(57).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
		rowDescription4.getCell(58).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_all_directions"));
		
		rowDescription4.getCell(60).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
		rowDescription4.getCell(61).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
		rowDescription4.getCell(62).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
		rowDescription4.getCell(63).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_all_directions"));							
		rowDescription4.getCell(64).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
		rowDescription4.getCell(65).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
		rowDescription4.getCell(66).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
		rowDescription4.getCell(67).setCellValue(localeSheet.getStringKey("$label_excel_sheet_period_flow_all_directions"));
		
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

		
	public void createStyle() {			

		// FONTES
						
		fontBody = utilSheet.createFont(workbook, FONT_ARIAL, 10, false, false, IndexedColors.BLACK); // FONTE DE USO PADRÃO		
		fontSubHeader = utilSheet.createFont(workbook, FONT_ARIAL, 10, true, false, IndexedColors.DARK_BLUE);	
		fontReportTime = utilSheet.createFont(workbook, FONT_ARIAL, 10, true, false, IndexedColors.WHITE);		
		fontNumbersTitle = utilSheet.createFont(workbook, FONT_ARIAL, 11, true, false, IndexedColors.WHITE); 
		fontBackgroundColor = utilSheet.createFont(workbook, FONT_ARIAL, 10, true, false, IndexedColors.WHITE);		
		fontBodyHeader = utilSheet.createFont(workbook, FONT_ARIAL, 10, false, false, IndexedColors.GREEN);	
		fontBodyHeader2 = utilSheet.createFont(workbook, FONT_ARIAL, 10, false, false, IndexedColors.ORANGE);		
		fontEspaco = utilSheet.createFont(workbook, FONT_ARIAL, 9, true, false, IndexedColors.WHITE);
		fontEspaco2 = utilSheet.createFont(workbook, FONT_ARIAL, 11, true, false, IndexedColors.WHITE);
		fontDataHeader = utilSheet.createFont(workbook, FONT_ARIAL, 11, true, false, IndexedColors.WHITE);
		fontIntervalo = utilSheet.createFont(workbook, FONT_ARIAL, 10, true, false, IndexedColors.DARK_BLUE);

		// ---------------------------------------------------------------------------------------------------------------		

		// STYLES

		style1 = utilSheet.createCellStyle(workbook, fontBody, HorizontalAlignment.CENTER, IndexedColors.WHITE, FillPatternType.NO_FILL, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
		subHeader = utilSheet.createCellStyle(workbook, fontSubHeader, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, IndexedColors.WHITE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	    reportTime = utilSheet.createCellStyle(workbook, fontReportTime, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, true, IndexedColors.ROYAL_BLUE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);						
	    numbersTitle = utilSheet.createCellStyle(workbook, fontNumbersTitle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, true, IndexedColors.DARK_BLUE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.NONE);											
		blueColorBackground = utilSheet.createCellStyle(workbook, fontBackgroundColor, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, true, IndexedColors.LIGHT_BLUE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);											
		redColorBackground = utilSheet.createCellStyle(workbook, fontBackgroundColor, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, true, IndexedColors.CORAL, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);	 	 				
		backgroundColorBodyHeader = utilSheet.createCellStyle(workbook, fontBodyHeader, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, IndexedColors.LIGHT_CORNFLOWER_BLUE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.NONE);	 	 				
		backgroundColorBodyHeader2 = utilSheet.createCellStyle(workbook, fontBodyHeader2, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, IndexedColors.LIGHT_YELLOW, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.NONE);	 	 				
		espacoStyle = utilSheet.createCellStyle(workbook, fontEspaco, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, IndexedColors.ORANGE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.NONE);	 	 				
		espacoStyle2 = utilSheet.createCellStyle(workbook, fontEspaco, VerticalAlignment.CENTER, IndexedColors.ORANGE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.NONE);	 	 				
		espacoStyle2.setRotation((short) 90);	
		dayStyle = utilSheet.createCellStyle(workbook, fontDataHeader, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, IndexedColors.ROYAL_BLUE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);	 	 				
		intervaloStyle = utilSheet.createCellStyle(workbook, fontIntervalo, HorizontalAlignment.CENTER, IndexedColors.WHITE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);	 	 				
		
		blankStyle = workbook.createCellStyle();
		utilSheet.setBorderRight(blankStyle, BorderStyle.THIN);
		utilSheet.setBorderLeft(blankStyle, BorderStyle.THIN);
	
		blankStyle2 = workbook.createCellStyle();
		utilSheet.setBorderLeft(blankStyle2, BorderStyle.THIN);
		
		// -------------------------------------------------------------------------------------

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

	public void createSheets(int sheetIndex, int equip, List<SAT> satList) throws Exception {
		
		initializeSentidoExcel(equip); 				
													
		sheetName[sheetIndex] = satList.get(sheetIndex).getNome();								
		sheet = workbook.createSheet(sheetName[sheetIndex]); // Criar nova folha para o arquivo	
		rowDados = new SXSSFRow(sheet);
				
		sentidoExcelHeader(sheetIndex, satList.get(sheetIndex).getFaixa1());		
		createExcelSheet(sheet, propertyTemplate, sheetIndex);
		
	}
	
// -------------------------------------------------------------------------------------------------------------

/* Métodos para criar o Excel - END */	
		
	public void updateForm() {		
		
		SessionUtil.getFacesContext().getPartialViewContext().getRenderIds().add("form-info:display");
	    
	}	
	
	public void message(int step) { 
		
	    // System.out.println("step by step");
						
			if(step == 1) {
				
				if(equips.length > 1)    
						displayMessage = localeSat.getStringKey("$label_period_flow_message_begin")
						      + "\n"+localeSat.getStringKey("$label_period_flow_message_create_sheets");
				
						else displayMessage += localeSat.getStringKey("$label_period_flow_message_begin")
								+ "\n"+localeSat.getStringKey("$label_period_flow_message_create_sheet");
					
					updateForm(); // UPDATE MODAL FORM VIEW
				   											    
				}
        				
			if(step == 2) {
				if(equips.length > 1) 
						displayMessage +="\n"+localeSat.getStringKey("$label_period_flow_message_created_sheets");		
					
					else displayMessage +="\n"+localeSat.getStringKey("$label_period_flow_message_created_sheet");
							
						updateForm(); // UPDATE MODAL FORM VIEW	   
				}
		
		   if(step == 3) {		   
			    displayMessage += "\n"+localeSat.getStringKey("$label_period_flow_message_process_sheets")+" "+satList.get(indice).getNome()+" ...";
			    	updateForm(); // UPDATE MODAL FORM VIEW				    	
			    
		   	}
							
	        if(step == 4) {
	        	
	        	if(equips.length > 1)        	
	        	     displayMessage += "\n"+localeSat.getStringKey("$label_period_flow_message_end_process_sheets");        	
	        		        		        	
	        		  updateForm(); // UPDATE MODAL FORM VIEW
	        	 	
	        	}
					   
	        if(step == 5) {        	
	   				displayMessage += "\n"+localeSat.getStringKey("$label_period_flow_message_ending_process_sheet");	   			                  				
	   				  updateForm(); // UPDATE MODAL FORM VIEW   				
	        	}
   		        
	        if(step == 6) {
	        		displayMessage += "\n"+localeSat.getStringKey("$label_period_flow_message_ended_process_sheet"); 
	        		updateForm(); // UPDATE MODAL FORM VIEW  
	        	    SessionUtil.executeScript("PF('poll').stop();");   
	        	    // System.out.println("Stopped!");	        	    	
	         } 	
	        
	        if(step == 7) {
	        	displayMessage += "";
	        	updateForm(); 
	        	
	        }
	        
	        if(step != 6)
	        	SessionUtil.executeScript("scrollOnBottom();");  
		 }
   
	public void processaDados(int sheetIndex, String equip, DateTimeApplication dta, List<SAT> satList) throws Exception {	
													
		try {						
		
			// System.out.println("-----------------");
			 // System.out.println("Entrando no Loop ...");

				if(last_index != sheetIndex) // Enquanto não é o ultimo index incrementa essa formula
					pos += ((daysInMonth - 1) * tam);	
				
				if(Integer.parseInt(equip) != equip_anterior)
						dtInicio = startDate;						
																		 
				index = increment * sheetIndex;					
						
				equipamentoHeader(index, incEquip, tamanho, satList.get(sheetIndex).getNome(), satList.get(sheetIndex).getKm(), satList.get(sheetIndex).getEstrada(), satList.get(sheetIndex).getFaixa1()); //Preenche nome do equipamento na tabela 
			
				sentidoHeader(index, tamanho, satList.get(sheetIndex).getFaixa1()); // Preencher sentido na DataTable	
				
				SAT sat = new SAT();
				
				sat = satList.get(sheetIndex);
								
				String[][] auxResult = dao.getVehicles(startDate, endDate, equip, period, sat, fieldsLenght, tamanho);
				
				//CASO EXISTA REGISTROS ENTRA AQUI
				if(auxResult.length > 0) {		
					
				lin = auxResult.length;
				col = auxResult[0].length;
														
				for(int j = 0; j < lin; j++) {
					   for(int i = 0; i < col; i++) {
					
					// CASO NO EXISTA VALOR >>>>>>> PASSA	   
					if(auxResult[j][0] != null)	 {  
										
					if(period.equals("01 hour"))
						   hr = Integer.parseInt(auxResult[j][1].substring(0, 2));
						
					else if(!period.equals("24 hours") && !period.equals("01 hour")) {
						    hr = Integer.parseInt(auxResult[j][1].substring(0, 2));
						    minuto =  Integer.parseInt(auxResult[j][1].substring(3, 5));	
						    			 
						}
					
						// Restrio caso no haja dados nos primeiros registros
						if ((startDate != null) && (!auxResult[j][0].equals(startDate))) {   // Executa uma unica vez
							
							if(period.equals("24 hours"))
								iterator = (int) dta.daysDifference(startDate, auxResult[j][0]);

							else iterator = dta.daysDifference(startDate, auxResult[j][0], periodRange);	
							
							pos+= iterator;
							startDate = null;

						} else if (!auxResult[j][0].equals(data_anterior)) {								
														
							if(period.equals("24 hours"))
								iterator = (int) dta.daysDifference(data_anterior, auxResult[j][0]);
							   
							else iterator = dta.daysDifference(data_anterior, auxResult[j][0], periodRange);	
							
							pos+= iterator;							
						} 			
						
						 data_anterior = auxResult[j][0];
						 equip_anterior = sat.getEquip_id();
												
						 if(period.equals("15 minutes")) {	
							 p = dta.index15Minutes(hr, minuto);
					         indexInc = p + pos + inc;										
						 }					
						 
						 else if(period.equals("01 hour"))				
							 indexInc = pos + hr + inc;
						
						
						else if(period.equals("24 hours"))
							  indexInc = pos + inc;
																 
						if(i > 1 )
						    resultQuery[indexInc][i] = auxResult[j][i];
										
					   } // CASO NO EXISTA VALOR >>>>>>> PASSA
					 }
				   }
				
					last_index = sheetIndex;
				
				} else  pos += tam;
												
				//preencherDadosExcel(workbook, propertyTemplate, sheetIndex, sheetName[sheetIndex], tam);
			   
		}catch(Exception ex) {
			ex.printStackTrace();
		}			
	
		 // System.out.println("Saindo do Loop ...");
		 // System.out.println("-----------------");
	  }
		
	     public void instaciarProcessaDados(DateTimeApplication dta) throws Exception {
	    	 
	    	 	lista = new ArrayList<FluxoPeriodo>();		    	 
	    	 	dao = new FluxoPeriodoDAO();
				
				//mes = dta.selectedMonth(month);
				int mth = Integer.parseInt(month);
				int yr = Integer.parseInt(year);
				int endHour = 23, endMin = 59, endSec = 59;
								
				monthABR = tm.MonthAbbreviation(month); 
				yearABR = tm.yearAbbreviation(year); 

				YearMonth yearMonthObject = YearMonth.of(yr, mth);
				daysInMonth = yearMonthObject.lengthOfMonth();			

				int diaInicial = 1;
				
				startDate = dta.createData(diaInicial, mth, yr);
				endDate = dta.createDateTime(daysInMonth, mth, yr, endHour, endMin, endSec);
							
				data_inicial = startDate;
				data_final = dta.createData(diaInicial, mth, yr);	
											
				tam = dta.periodsRange(period);
				
				increment = 0; incEquip = 0;				
										
				if(tam == 1) {increment = daysInMonth; incEquip = daysInMonth;}
				else if(tam == 4) {increment = daysInMonth * tam; incEquip = daysInMonth * tam;}
				else {increment = (tam * daysInMonth); incEquip = tam;}
						
				tamanho = ((daysInMonth * tam) * equips.length);	
												
				initializeSentidoExcel(equips.length);
				
				dtInicio = startDate;			
				data_anterior = startDate;			
				equip_anterior = Integer.parseInt(equips[0]);
				hora_anterior = -1;				
				rangeInterval = tam;
				pos = 0;
				hrPos = 0;		
				hr = 0; 				
				interResp = 0; 
				last_index = 0;	
				index = 0;	
				fieldsLenght = 58;				
				minuto = 0;
				iterator = 0;
				pos = 0;
				hr = 0;		 		
				lin = 0;
				col = 0;
				p = 0;
				empty = 0;
				inc = 0;
				index = 0;
				indexInc = 0;
				
				data = dta.preencherDataFluxoPeriodo(startDate, endDate, tamanho, tam);								
				days = dta.preencherDias(tamanho, tam);	
																		
				if(period.equals("15 minutes")) {		
				   intervalo = dta.intervalo15Minutos(tamanho);
				   interInicio = dta.intervalo15Inicio(tamanho);	
				   interFim = dta.intervalo15Fim(tamanho);	
				   separador = dta.intervaloSeparador(tamanho);
		        }
				
				if(period.equals("01 hour")) {	
					intervalo = dta.preencherHora(tamanho);
					interInicio = dta.intervaloFluxoHoraInicio(tamanho);	
					interFim = dta.intervaloFluxoHoraFim(tamanho);	
					separador = dta.intervaloSeparador(tamanho);
				}
								
				if(period.equals("24 hours")) {	
			       intervalo = dta.intervalo24Horas(tamanho);
				   interInicio = dta.intervaloSeparador24Horas(tamanho);	
				   interFim = dta.intervaloSeparador24Horas(tamanho);	
				   separador = dta.intervaloSeparador(tamanho);		          
				}
				
				resultQuery = new String[tamanho][fieldsLenght];
				
				System.out.println(tamanho);
						
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
