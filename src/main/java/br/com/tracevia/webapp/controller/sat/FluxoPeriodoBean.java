package br.com.tracevia.webapp.controller.sat;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import br.com.tracevia.webapp.controller.global.ListEquipments;
import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.sat.FluxoPeriodoDAO;
import br.com.tracevia.webapp.log.SystemLog;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.ReportBuild;
import br.com.tracevia.webapp.model.sat.FluxoPeriodo.Builder;
import br.com.tracevia.webapp.model.sat.SAT;
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
	private List<Builder> resultList;	
						
	private ReportBuild build;
			
	LocaleUtil localeSat, localeDir, localeReports, localeSheet, localeCalendar;
		
	List<SAT> satList;
				
	String[] equips;

	String period, month, year;
	
	String[][] resultQuery;
		
	// --------------------------------------------------------------------------------------------------------------

	private int daysInMonth;	
	private int mes;
	private int ano;
	private int tamanho;
	private int tam;
	private int step;	
	private int equip_anterior;	
	private String startDate; 
	private String endDate;
	private String data_anterior;	
	private String yearABR;
	private String  monthABR;
	private String displayMessage;			

	
	private Workbook workbook;		
	private Sheet sheet;
			
	DateTimeApplication dta;
	TranslationMethods tm;

	FluxoPeriodoDAO dao;	
	EquipmentsDAO equipDao;

	String[] sentido1, sentido2, nomeSats, days;

	CellStyle subheader, dayStyle, espacoStyle,	espacoStyle2, backgroundColorBodyHeader, backgroundColorBodyHeader2,
	blueColorBackground, redColorBackground, numbersTitle, reportTime, subHeader, intervaloStyle, style1, blankStyle;
	

	Font fontDataHeader, fontEspaco2, fontEspaco, fontBodyHeader2, fontBodyHeader, fontBackgroundColor, fontNumbersTitle,
	fontSubHeader, fontBody, fontIntervalo;
	
	int indice, interResp, last_index, fieldsLenght; 
	String dtInicio, fileName;

	String[] intervalo, interInicio, interFim, separador, data;
	
	int minuto, iterator, pos, hr, lin, col, p, empty, inc, index;
		
	@ManagedProperty("#{listEquipsBean}")
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
			
	public List<Builder> getResultList() {
		return resultList;
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
	

	public String getData_anterior() {
		return data_anterior;
	}

	public void setData_anterior(String data_anterior) {
		this.data_anterior = data_anterior;
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
	
	public Workbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public String getDisplayMessage() {
		return displayMessage;
	}

	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
	}
	
	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}		
		
	public int getEquip_anterior() {
		return equip_anterior;
	}

	public void setEquip_anterior(int equip_anterior) {
		this.equip_anterior = equip_anterior;
	}
	
	// ---------------------------------------------------------------------------------------------------
		
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
	
	// -------------------------------------------------------------------------------------------------------------
	// PROCESSAMENTO DE DADOS
	// -------------------------------------------------------------------------------------------------------------
		 
	  public void instaciarProcessaDados(DateTimeApplication dta) throws Exception {
	     	 
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
													
			tam = dta.periodsRange(period);
												
			tamanho = ((daysInMonth * tam) * equips.length);	
								
			dtInicio = startDate;			
			data_anterior = startDate;			
			equip_anterior = Integer.parseInt(equips[0]);		
			pos = 0;					
			hr = 0; 				
			interResp = 0; 
			last_index = 0;	
			index = 0;	
			fieldsLenght = 58;				
			minuto = 0;
			iterator = 0;					 		
			lin = 0;
			col = 0;
			p = 0;
			empty = 0;			
			index = 0;
						
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
			
			//System.out.println(tamanho);
					
     }
	  
	// ----------------------------------------------------------------------------------------------------------------------------------------------------- 
			
	  public void processaDados(int sheetIndex, String equip, DateTimeApplication dta, List<SAT> satList) throws Exception {	
			
			try {						
			
				// System.out.println("-----------------");
				 // System.out.println("Entrando no Loop ...");
													
					if(Integer.parseInt(equip) != equip_anterior) {
						 dtInicio = startDate;					    
					     pos = (sheetIndex * ((daysInMonth * tam)));	
					}					
																
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
							if ((dtInicio != null) && (!auxResult[j][0].equals(dtInicio))) {   // Executa uma unica vez
								
								if(period.equals("24 hours"))
									iterator = (int) dta.daysDifference(dtInicio, auxResult[j][0]);

								else iterator = dta.daysDifference(dtInicio, auxResult[j][0], tam);	
								
								pos+= iterator;
								dtInicio = null;

							} else if (!auxResult[j][0].equals(data_anterior)) {								
															
								if(period.equals("24 hours"))
									iterator = (int) dta.daysDifference(data_anterior, auxResult[j][0]);
								   
								else iterator = dta.daysDifference(data_anterior, auxResult[j][0], tam);	
								
								pos+= iterator;							
							} 			
							
							 data_anterior = auxResult[j][0];
																				
							 if(period.equals("15 minutes")) {	
								 p = dta.index15Minutes(hr, minuto);
						         index = p + pos;										
							 }					
							 
							 else if(period.equals("01 hour"))				
								 index = pos + hr;
							
							
							else if(period.equals("24 hours"))
								  index = pos;
																	 
							if(i > 1 )
							    resultQuery[index][i] = auxResult[j][i];
																									
						   } // CASO NO EXISTA VALOR >>>>>>> PASSA
						 }
					   }
					
					 equip_anterior = Integer.parseInt(equip);
					
					}											
								   
			}catch(Exception ex) {
				ex.printStackTrace();
			}			
		
			 // System.out.println("Saindo do Loop ...");
			 // System.out.println("-----------------");
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
					  		
		 workbook = new SXSSFWorkbook();
				 
		 fileName = localeSat.getStringKey("via_paulista_flow_per_period_file_name")+"_"+tm.periodName(period)+"_"+tm.MonthAbbreviation(month)+"_"+tm.yearAbbreviation(year);
		 		 
	     satList = new ArrayList<SAT>();	
		 satList = equipDao.ListSATinfoHeader(equips);	
		 
		 // INSTANCE DATA		 
		 instaciarProcessaDados(dta);
		
		  //--- INITIALIZE --- //	
		
		  step = 1; //
			  message(step);  // STARTING PROCESS MESSAGE				 				 		 								   		       		
                   		 		 			 		 
		 for(indice=0; indice < equips.length; indice++) {				 
																																		 
				 step = 2;
				 	message(step);	// DATA PROCESS MESSAGE		
				 	
				 step = 0;	
				 	message(step);	// NO MESSAGE TO DISPLAY	
				 				 											
				 processaDados(indice, equips[indice], dta, satList);
				 			 			 			
				 if(indice == (equips.length-1)) {				 
					     step = 3;
					     	message(step);	// PROCESS DATA ENDED						     	
				  }			
		   	}
		 
		 	step = 4;
	     		message(step);	// PROCESS EXCEL FILE MESSAGE	
	     		
	     	step = 0;
			    message(step);	// NO MESSAGE TO DISPLAY 
		 
		 	generateExcelPeriodFlow(equips); // GENERATING EXCEL MESSAGE
		 	
		    step = 5;
		    	message(step);	// COMPLETED MESSAGE
		   								
			// ACTIVATE EXCEL BUTTON
			SessionUtil.executeScript("$('#activate-excel-act').prop('disabled', false);"); // ENABLE DOWNLOAD BUTTON
												        
	    }	  
	  
	// -------------------------------------------------------------------------------------------------------------
	// EXCEL SPREAD SHEET
	// -------------------------------------------------------------------------------------------------------------
	   
		public void createStyle() {			

			// FONTES
							
			fontBody = utilSheet.createFont(workbook, FONT_ARIAL, 10, false, false, IndexedColors.BLACK); // FONTE DE USO PADRÃO		
			fontSubHeader = utilSheet.createFont(workbook, FONT_ARIAL, 10, true, false, IndexedColors.DARK_BLUE);			
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
		
		}
		
		// ----------------------------------------------------------------------------------------- 

		public void columnsWidth(Sheet sheet) {

			// COLUMNS WIDTH
			utilSheet.columnWidth(sheet, 0, 1000);
			utilSheet.columnWidth(sheet, 1, 1600);
			utilSheet.columnWidth(sheet, 2, 2500);
			utilSheet.columnWidth(sheet, 3, 1800);
			utilSheet.columnWidth(sheet, 4, 800);
			utilSheet.columnWidth(sheet, 5, 1800);
			utilSheet.columnWidth(sheet, 9, 4500);
			utilSheet.columnWidth(sheet, 13, 4500);
			utilSheet.columnWidth(sheet, 18, 4500);
			utilSheet.columnWidth(sheet, 22, 4500);
			utilSheet.columnWidth(sheet, 27, 4500);
			utilSheet.columnWidth(sheet, 31, 4500);
			utilSheet.columnWidth(sheet, 36, 4500);
			utilSheet.columnWidth(sheet, 40, 4500);
			utilSheet.columnWidth(sheet, 45, 4500);
			utilSheet.columnWidth(sheet, 49, 4500);
			utilSheet.columnWidth(sheet, 54, 4500);
			utilSheet.columnWidth(sheet, 58, 4500);
			utilSheet.columnWidth(sheet, 63, 4500);
			utilSheet.columnWidth(sheet, 67, 4500);

			// WHITE SPACE
			utilSheet.columnWidth(sheet, 14, 1300);
			utilSheet.columnWidth(sheet, 23, 1300);
			utilSheet.columnWidth(sheet, 32, 1300);
			utilSheet.columnWidth(sheet, 41, 1300);
			utilSheet.columnWidth(sheet, 50, 1300);
			utilSheet.columnWidth(sheet, 59, 1300);	

		}
		
		// ----------------------------------------------------------------------------------------- 

		public void mergeCells(Sheet sheet) {			

			utilSheet.mergeCells(sheet, "A1:L1");
			utilSheet.mergeCells(sheet, "A2:A36");
			utilSheet.mergeCells(sheet, "C3:F3");
			utilSheet.mergeCells(sheet, "G3:J3");
			utilSheet.mergeCells(sheet, "K3:N3");
			utilSheet.mergeCells(sheet, "P3:S3");
			utilSheet.mergeCells(sheet, "T3:W3");
			utilSheet.mergeCells(sheet, "Y3:AB3");
			utilSheet.mergeCells(sheet, "AC3:AF3");
			utilSheet.mergeCells(sheet, "AH3:AK3");
			utilSheet.mergeCells(sheet, "AL3:AO3");
			utilSheet.mergeCells(sheet, "AQ3:AT3");							
			utilSheet.mergeCells(sheet, "AU3:AX3");
			utilSheet.mergeCells(sheet, "AZ3:BC3");
			utilSheet.mergeCells(sheet, "BD3:BG3");
			utilSheet.mergeCells(sheet, "BI3:BL3");
			utilSheet.mergeCells(sheet, "BM3:BP3");	
			utilSheet.mergeCells(sheet, "C4:C5");
			utilSheet.mergeCells(sheet, "D4:F5");						
			utilSheet.mergeCells(sheet, "G4:J4");
			utilSheet.mergeCells(sheet, "K4:N4");
			utilSheet.mergeCells(sheet, "P4:S4");
			utilSheet.mergeCells(sheet, "T4:W4");
			utilSheet.mergeCells(sheet, "Y4:AB4");
			utilSheet.mergeCells(sheet, "AC4:AF4");
			utilSheet.mergeCells(sheet, "AH4:AK4");
			utilSheet.mergeCells(sheet, "AL4:AO4");
			utilSheet.mergeCells(sheet, "AQ4:AT4");							
			utilSheet.mergeCells(sheet, "AU4:AX4");
			utilSheet.mergeCells(sheet, "AZ4:BC4");
			utilSheet.mergeCells(sheet, "BD4:BG4");
			utilSheet.mergeCells(sheet,"BI4:BL4");
			utilSheet.mergeCells(sheet, "BM4:BP4");

		}	
		
		// ----------------------------------------------------------------------------------------- 
	  	  
		/* Métodos para criar o Excel */			
		public void createExcelHeader(Sheet sheet, int sheetIndex, List<SAT> satList) {
			
			sheet =  workbook.createSheet(satList.get(sheetIndex).getNome());
			
			createStyle();
			columnsWidth(sheet);
			mergeCells(sheet);			
					
			// FIRST ROW 
			
			Row firstRow = null;		
			
			utilSheet.createRow(sheet, firstRow, 0);
			utilSheet.createCellWithValueAndStyle(sheet, firstRow, espacoStyle, 0, 0, localeSheet.getStringKey("$label_excel_sheet_period_flow_header"));
					
			// -----------------------------------------------------------------------
			
			// SECOND ROW
			
			Row secondRow = null;
			
			utilSheet.createRow(sheet, secondRow, 1);		
			utilSheet.createCellWithValueAndStyle(sheet, secondRow, espacoStyle2, 1, 0, localeSheet.getStringKey("$label_excel_sheet_period_flow_header"));
			utilSheet.createCells(sheet, secondRow, 1, 67, 1, 1);
			utilSheet.setCellsStyle(sheet, secondRow, numbersTitle, 1, 67, 1, 1);
			
			for(int i=1; i < 68; i++)
				utilSheet.setCellValue(sheet, secondRow, 1, i, i);
							
			// ----------------------------------------------------------------------

		    // THIRD ROW
			
			Row thirdRow = null;
			
			utilSheet.createRow(sheet, thirdRow, 2);		
			utilSheet.createCells(sheet, thirdRow, 1, 67, 2, 2);
			
			utilSheet.setCellValue(sheet, thirdRow, 2, 1, 2);
			utilSheet.setCellValue(sheet, thirdRow, 2, 2, tm.periodName(period));
		
			utilSheet.setCellValue(sheet, thirdRow, 2, 6, satList.get(sheetIndex).getSentido1());
			utilSheet.setCellValue(sheet, thirdRow, 2, 10, satList.get(sheetIndex).getSentido2());
			
			utilSheet.setCellValue(sheet, thirdRow, 2, 15, satList.get(sheetIndex).getSentido1());
			utilSheet.setCellValue(sheet, thirdRow, 2, 19, satList.get(sheetIndex).getSentido2());
			
			utilSheet.setCellValue(sheet, thirdRow, 2, 24, satList.get(sheetIndex).getSentido1());
			utilSheet.setCellValue(sheet, thirdRow, 2, 28, satList.get(sheetIndex).getSentido2());
			
			utilSheet.setCellValue(sheet, thirdRow, 2, 33, satList.get(sheetIndex).getSentido1());
			utilSheet.setCellValue(sheet, thirdRow, 2, 37, satList.get(sheetIndex).getSentido2());
			
			utilSheet.setCellValue(sheet, thirdRow, 2, 42, satList.get(sheetIndex).getSentido1());
			utilSheet.setCellValue(sheet, thirdRow, 2, 46, satList.get(sheetIndex).getSentido2());
			
			utilSheet.setCellValue(sheet, thirdRow, 2, 51, satList.get(sheetIndex).getSentido1());
			utilSheet.setCellValue(sheet, thirdRow, 2, 55, satList.get(sheetIndex).getSentido2());
			
			utilSheet.setCellValue(sheet, thirdRow, 2, 60, satList.get(sheetIndex).getSentido1());
			utilSheet.setCellValue(sheet, thirdRow, 2, 64, satList.get(sheetIndex).getSentido2());
			
			// STYLES 
			
			utilSheet.setCellStyle(sheet, thirdRow, numbersTitle, 2, 1);
			utilSheet.setCellsStyle(sheet, thirdRow, subHeader, 2, 5, 2, 2);
			
			// SUBHEADER
				
			utilSheet.setCellsStyle(sheet, thirdRow, subHeader, 6, 9, 2, 2);
			utilSheet.setCellsStyle(sheet, thirdRow, subHeader, 10, 13, 2, 2);
			utilSheet.setCellsStyle(sheet, thirdRow, subHeader, 15, 18, 2, 2);
			utilSheet.setCellsStyle(sheet, thirdRow, subHeader, 19, 22, 2, 2);
			utilSheet.setCellsStyle(sheet, thirdRow, subHeader, 24, 27, 2, 2);
			utilSheet.setCellsStyle(sheet, thirdRow, subHeader, 28, 31, 2, 2);
			utilSheet.setCellsStyle(sheet, thirdRow, subHeader, 33, 36, 2, 2);
			utilSheet.setCellsStyle(sheet, thirdRow, subHeader, 37, 40, 2, 2);
			utilSheet.setCellsStyle(sheet, thirdRow, subHeader, 42, 45, 2, 2);
			utilSheet.setCellsStyle(sheet, thirdRow, subHeader, 46, 49, 2, 2);
			utilSheet.setCellsStyle(sheet, thirdRow, subHeader, 51, 54, 2, 2);
			utilSheet.setCellsStyle(sheet, thirdRow, subHeader, 55, 58, 2, 2);
			utilSheet.setCellsStyle(sheet, thirdRow, subHeader, 60, 63, 2, 2);
			utilSheet.setCellsStyle(sheet, thirdRow, subHeader, 64, 67, 2, 2);
			
			// BLANK STYLE
			
			utilSheet.setCellStyle(sheet, thirdRow, blankStyle, 2, 14);
			utilSheet.setCellStyle(sheet, thirdRow, blankStyle, 2, 23);
			utilSheet.setCellStyle(sheet, thirdRow, blankStyle, 2, 32);
			utilSheet.setCellStyle(sheet, thirdRow, blankStyle, 2, 41);
			utilSheet.setCellStyle(sheet, thirdRow, blankStyle, 2, 50);
			utilSheet.setCellStyle(sheet, thirdRow, blankStyle, 2, 59);
						
			// ----------------------------------------------------------------------
			
			// FOURTH ROW
			
			Row fourthRow = null;
			
			utilSheet.createRow(sheet, fourthRow, 3);		
			utilSheet.createCells(sheet, fourthRow, 1, 69, 3, 3);
			
			utilSheet.setCellValue(sheet, fourthRow, 3, 1, 3);
			
			utilSheet.setCellValue(sheet, fourthRow, 3, 2, localeSheet.getStringKey("$label_excel_sheet_period_flow_day"));
			utilSheet.setCellValue(sheet, fourthRow, 3, 3, localeSheet.getStringKey("$label_excel_sheet_period_flow_hour"));
				
			utilSheet.setCellValue(sheet, fourthRow, 3, 6, localeSheet.getStringKey("$label_excel_sheet_period_flow_flow"));
			utilSheet.setCellValue(sheet, fourthRow, 3, 10, localeSheet.getStringKey("$label_excel_sheet_period_flow_flow"));
			utilSheet.setCellValue(sheet, fourthRow, 3, 15, localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_avg"));
			utilSheet.setCellValue(sheet, fourthRow, 3, 19, localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_avg"));
			utilSheet.setCellValue(sheet, fourthRow, 3, 24, localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_avg_50"));
			utilSheet.setCellValue(sheet, fourthRow, 3, 28, localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_avg_50"));
			utilSheet.setCellValue(sheet, fourthRow, 3, 33, localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_avg_85"));
			utilSheet.setCellValue(sheet, fourthRow, 3, 37, localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_avg_85"));
			utilSheet.setCellValue(sheet, fourthRow, 3, 42, localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_max"));
			utilSheet.setCellValue(sheet, fourthRow, 3, 46, localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_max"));
			utilSheet.setCellValue(sheet, fourthRow, 3, 51, localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_min"));
			utilSheet.setCellValue(sheet, fourthRow, 3, 55, localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_min"));
			utilSheet.setCellValue(sheet, fourthRow, 3, 60, localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_std"));
			utilSheet.setCellValue(sheet, fourthRow, 3, 64, localeSheet.getStringKey("$label_excel_sheet_period_flow_speed_std"));
					
			// STYLES 
			
			utilSheet.setCellStyle(sheet, fourthRow, numbersTitle, 3, 1);
			utilSheet.setCellsStyle(sheet, fourthRow, subHeader, 2, 3, 3, 3);
		
			// SUBHEADER
			
			utilSheet.setCellsStyle(sheet, fourthRow, blueColorBackground, 6, 9, 3, 3);
			utilSheet.setCellsStyle(sheet, fourthRow, blueColorBackground, 10, 13, 3, 3);
			utilSheet.setCellsStyle(sheet, fourthRow, redColorBackground, 15, 18, 3, 3);
			utilSheet.setCellsStyle(sheet, fourthRow, redColorBackground, 19, 22, 3, 3);
			utilSheet.setCellsStyle(sheet, fourthRow, blueColorBackground, 24, 27, 3, 3);
			utilSheet.setCellsStyle(sheet, fourthRow, blueColorBackground, 28, 31, 3, 3);
			utilSheet.setCellsStyle(sheet, fourthRow, redColorBackground, 33, 36, 3, 3);
			utilSheet.setCellsStyle(sheet, fourthRow, redColorBackground, 37, 40, 3, 3);
			utilSheet.setCellsStyle(sheet, fourthRow, blueColorBackground, 42, 45, 3, 3);
			utilSheet.setCellsStyle(sheet, fourthRow, blueColorBackground, 46, 49, 3, 3);
			utilSheet.setCellsStyle(sheet, fourthRow, redColorBackground, 51, 54, 3, 3);
			utilSheet.setCellsStyle(sheet, fourthRow, redColorBackground, 55, 58, 3, 3);
			utilSheet.setCellsStyle(sheet, fourthRow, blueColorBackground, 60, 63, 3, 3);
			utilSheet.setCellsStyle(sheet, fourthRow, blueColorBackground, 64, 67, 3, 3);
							
			// BLANK STYLE
			
			utilSheet.setCellStyle(sheet, fourthRow, blankStyle, 3, 14);
			utilSheet.setCellStyle(sheet, fourthRow, blankStyle, 3, 23);
			utilSheet.setCellStyle(sheet, fourthRow, blankStyle, 3, 32);
			utilSheet.setCellStyle(sheet, fourthRow, blankStyle, 3, 41);
			utilSheet.setCellStyle(sheet, fourthRow, blankStyle, 3, 50);
			utilSheet.setCellStyle(sheet, fourthRow, blankStyle, 3, 59);
						
			// ----------------------------------------------------------------------
			
			// FIFTH ROW
					
			Row fifthRow = null;
			
			utilSheet.createRow(sheet, fifthRow, 4);		
			utilSheet.createCells(sheet, fifthRow, 1, 67, 4, 4);
			
			utilSheet.setCellValue(sheet, fifthRow, 4, 1, 4);						
					
			for(int i = 6; i < 61; i+=9)			
				  utilSheet.setCellValue(sheet, fifthRow, 4, i, localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
			
			for(int i = 7; i < 62; i+=9)
				  utilSheet.setCellValue(sheet, fifthRow, 4, i, localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
			
			for(int i = 8; i < 63; i+=9)
				  utilSheet.setCellValue(sheet, fifthRow, 4, i, localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
			
			for(int i = 9; i < 64; i+=9)
				  utilSheet.setCellValue(sheet, fifthRow, 4, i, localeSheet.getStringKey("$label_excel_sheet_period_flow_all_directions"));
			
			for(int i = 10; i < 65; i+=9)			
				  utilSheet.setCellValue(sheet, fifthRow, 4, i, localeSheet.getStringKey("$label_excel_sheet_period_flow_auto"));
			
			for(int i = 11; i < 66; i+=9)
				  utilSheet.setCellValue(sheet, fifthRow, 4, i, localeSheet.getStringKey("$label_excel_sheet_period_flow_com"));
			
			for(int i = 12; i < 67; i+=9)
				  utilSheet.setCellValue(sheet, fifthRow, 4, i, localeSheet.getStringKey("$label_excel_sheet_period_flow_moto"));
			
			for(int i = 13; i < 68; i+=9)
				  utilSheet.setCellValue(sheet, fifthRow, 4, i, localeSheet.getStringKey("$label_excel_sheet_period_flow_all_directions"));
							
			/* STYLES */	
		
			utilSheet.setCellStyle(sheet, fifthRow, numbersTitle, 4, 1);
			utilSheet.setCellsStyle(sheet, fourthRow, subHeader, 2, 5, 4, 4);

			for(int i=6; i < 14; i++)
				utilSheet.setCellStyle(sheet, fifthRow, subHeader, 4, i);
			
			for(int i=15; i < 23; i++)
				utilSheet.setCellStyle(sheet, fifthRow, subHeader, 4, i);
		
			for(int i=24; i < 32; i++)
				utilSheet.setCellStyle(sheet, fifthRow, subHeader, 4, i);

			for(int i=33; i < 41; i++)
				utilSheet.setCellStyle(sheet, fifthRow, subHeader, 4, i);

			for(int i=42; i < 50; i++)
				utilSheet.setCellStyle(sheet, fifthRow, subHeader, 4, i);

			for(int i=51; i < 59; i++)
				utilSheet.setCellStyle(sheet, fifthRow, subHeader, 4, i);

			for(int i=60; i < 68; i++)
				utilSheet.setCellStyle(sheet, fifthRow, subHeader, 4, i);						 						

			    //System.out.println("Folha Criada com Sucesso!");
			   
		}
		
		// -------------------------------------------------------------------------------------------------------------
			  
	public void preencherDadosExcel(Workbook workbook, Sheet sheet, List<SAT> satList, int tam, int indice, String[][] dados) throws IOException {    	 
						
		int i = 0, idx = 0, rowIndex = 0; // Variáveis para criar o excel	
		int rowMax = ((daysInMonth * tam) + 5); // trabalhar aqui  					        
		int maxLimit = (daysInMonth * tam);	
		
		int increment = (indice * maxLimit); // INCREMENT INDEX

		sheet = workbook.getSheet(satList.get(indice).getNome()); 
		Row row = null;
													
		for (rowIndex = 5, i= 0; rowIndex <= rowMax && i < (rowMax - 5); rowIndex++, i++) { 
			
			utilSheet.createRow(sheet, row, rowIndex); // CRIAR AS LINHAS
		
			idx = i + increment;		
			
			utilSheet.createCellWithValueAndStyle(sheet, row, numbersTitle, rowIndex, 1, (rowIndex + 1)); // NUMBERS
				
			if(days[i] != null) 
				utilSheet.createCellWithValueAndStyle(sheet, row, dayStyle, rowIndex, 2, Integer.parseInt(days[i])); // DAYS
								
			else utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 2, ""); // DAYS
		
			utilSheet.createCellWithValueAndStyle(sheet, row, intervaloStyle, rowIndex, 3, interInicio[i]); // START			
			utilSheet.createCellWithValueAndStyle(sheet, row, intervaloStyle, rowIndex, 4, separador[i]); // SEPARATOR
			utilSheet.createCellWithValueAndStyle(sheet, row, intervaloStyle, rowIndex, 5, interFim[i]); // END
			    
			// FLOW		    

		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 6, dados[idx][2] == null ? 0 : Integer.parseInt(dados[idx][2])); // LIGHT S1
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 7, dados[idx][3] == null ? 0 : Integer.parseInt(dados[idx][3])); // COM S1
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 8, dados[idx][4] == null ? 0 : Integer.parseInt(dados[idx][4])); // MOTO S1
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 9, dados[idx][5] == null ? 0 : Integer.parseInt(dados[idx][5])); // TOTAL S1
		    
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 10, dados[idx][6] == null ? 0 : Integer.parseInt(dados[idx][6])); // LIGHT S2
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 11, dados[idx][7] == null ? 0 : Integer.parseInt(dados[idx][7])); // COM S2
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 12, dados[idx][8] == null ? 0 : Integer.parseInt(dados[idx][8])); // MOTO S2
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 13, dados[idx][9] == null ? 0 : Integer.parseInt(dados[idx][9])); // TOTAL S2
	
			// SPEED AVERAGE
		    
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 15, dados[idx][10] == null ? 0 : Integer.parseInt(dados[idx][10])); // LIGHT S1
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 16, dados[idx][11] == null ? 0 : Integer.parseInt(dados[idx][11])); // COM S1
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 17, dados[idx][12] == null ? 0 : Integer.parseInt(dados[idx][12])); // MOTO S1
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 18, dados[idx][13] == null ? 0 : Integer.parseInt(dados[idx][13])); // TOTAL S1
		    
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 19, dados[idx][14] == null ? 0 : Integer.parseInt(dados[idx][14])); // LIGHT S2
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 20, dados[idx][15] == null ? 0 : Integer.parseInt(dados[idx][15])); // COM S2
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 21, dados[idx][16] == null ? 0 : Integer.parseInt(dados[idx][16])); // MOTO S2
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 22, dados[idx][17] == null ? 0 : Integer.parseInt(dados[idx][17])); // TOTAL S2
		
			// MEDIAN SPEED 50%		
		    
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 24, dados[idx][18] == null ? 0 : Integer.parseInt(dados[idx][18])); // LIGHT S1
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 25, dados[idx][19] == null ? 0 : Integer.parseInt(dados[idx][19])); // COM S1
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 26, dados[idx][20] == null ? 0 : Integer.parseInt(dados[idx][20])); // MOTO S1
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 27, dados[idx][21] == null ? 0 : Integer.parseInt(dados[idx][21])); // TOTAL S1
		    
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 28, dados[idx][22] == null ? 0 : Integer.parseInt(dados[idx][22])); // LIGHT S2
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 29, dados[idx][23] == null ? 0 : Integer.parseInt(dados[idx][23])); // COM S2
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 30, dados[idx][24] == null ? 0 : Integer.parseInt(dados[idx][24])); // MOTO S2
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 31, dados[idx][25] == null ? 0 : Integer.parseInt(dados[idx][25])); // TOTAL S2
		
		    // MEDIAN SPEED 85%		
		    
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 33, dados[idx][26] == null ? 0 : Integer.parseInt(dados[idx][26])); // LIGHT S1
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 34, dados[idx][27] == null ? 0 : Integer.parseInt(dados[idx][27])); // COM S1
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 35, dados[idx][28] == null ? 0 : Integer.parseInt(dados[idx][28])); // MOTO S1
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 36, dados[idx][29] == null ? 0 : Integer.parseInt(dados[idx][29])); // TOTAL S1
		  
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 37, dados[idx][30] == null ? 0 : Integer.parseInt(dados[idx][30])); // LIGHT S2
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 38, dados[idx][31] == null ? 0 : Integer.parseInt(dados[idx][31])); // COM S2
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 39, dados[idx][32] == null ? 0 : Integer.parseInt(dados[idx][32])); // MOTO S2
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 40, dados[idx][33] == null ? 0 : Integer.parseInt(dados[idx][33])); // TOTAL S2

			// MAX SPEED
		    		    
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 42, dados[idx][34] == null ? 0 : Integer.parseInt(dados[idx][34])); // LIGHT S1
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 43, dados[idx][35] == null ? 0 : Integer.parseInt(dados[idx][35])); // COM S1
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 44, dados[idx][36] == null ? 0 : Integer.parseInt(dados[idx][36])); // MOTO S1
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 45, dados[idx][37] == null ? 0 : Integer.parseInt(dados[idx][37])); // TOTAL S1
		  
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 46, dados[idx][38] == null ? 0 : Integer.parseInt(dados[idx][38])); // LIGHT S2
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 47, dados[idx][39] == null ? 0 : Integer.parseInt(dados[idx][39])); // COM S2
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 48, dados[idx][40] == null ? 0 : Integer.parseInt(dados[idx][40])); // MOTO S2
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 49, dados[idx][41] == null ? 0 : Integer.parseInt(dados[idx][41])); // TOTAL S2
		
		    // MIN SPEED
		    		 		    
			utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 51, dados[idx][42] == null ? 0 : Integer.parseInt(dados[idx][42])); // LIGHT S1
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 52, dados[idx][43] == null ? 0 : Integer.parseInt(dados[idx][43])); // COM S1
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 53, dados[idx][44] == null ? 0 : Integer.parseInt(dados[idx][44])); // MOTO S1
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 51, dados[idx][45] == null ? 0 : Integer.parseInt(dados[idx][45])); // TOTAL S1
		 		   
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 55, dados[idx][46] == null ? 0 : Integer.parseInt(dados[idx][46])); // LIGHT S2
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 56, dados[idx][47] == null ? 0 : Integer.parseInt(dados[idx][47])); // COM S2
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 57, dados[idx][48] == null ? 0 : Integer.parseInt(dados[idx][48])); // MOTO S2
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 58, dados[idx][49] == null ? 0 : Integer.parseInt(dados[idx][49])); // TOTAL S2
									
			// STANDARD DEVIATION
		    		    
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 60, dados[idx][50] == null ? 0 : Integer.parseInt(dados[idx][50])); // LIGHT S1
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 61, dados[idx][51] == null ? 0 : Integer.parseInt(dados[idx][51])); // COM S1
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 62, dados[idx][52] == null ? 0 : Integer.parseInt(dados[idx][52])); // MOTO S1
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 63, dados[idx][53] == null ? 0 : Integer.parseInt(dados[idx][53])); // TOTAL S1
		
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 64, dados[idx][54] == null ? 0 : Integer.parseInt(dados[idx][54])); // LIGHT S2
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 65, dados[idx][55] == null ? 0 : Integer.parseInt(dados[idx][55])); // COM S2
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 66, dados[idx][56] == null ? 0 : Integer.parseInt(dados[idx][56])); // MOTO S2
		    utilSheet.createCellWithValueAndStyle(sheet, row, style1, rowIndex, 67, dados[idx][57] == null ? 0 : Integer.parseInt(dados[idx][57])); // TOTAL S2
		
		}
	
		// Controle manual de como as linhas são liberadas para o disco
		// if(rowIndex % rowMax == 0) 
		//	((SXSSFSheet)sheet).flushRows(rowMax); //  guardas as ultimas linhas escritas e libera outras

		// System.out.println("Preenchimento de dados finalizado!");

		}		
				
	    // -------------------------------------------------------------------------------------------------------------
		// METODOS AUXILIARES
		// -------------------------------------------------------------------------------------------------------------
		
	/** Método para limpar listas e variáveis de caixas de seleção
	 * @throws Exception
	 * @return void */

	public void resetForm() throws Exception {								
				
		workbook = null;
			
		setDaysInMonth(0);		
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

	/* Métodos para criar o Excel - END */	
		
	public void updateForm() {		
		
		SessionUtil.getFacesContext().getPartialViewContext().getRenderIds().add("form-info:display");
	    
	}	
	
	// ---------------------------------------------------------------------------------
	
	public void message(int step) { 
		
	    // System.out.println("step by step");
				 
	        if(step == 0) {
	        	displayMessage += "";
	        	updateForm();        	
	        }
						
			if(step == 1) {
				 displayMessage = localeSat.getStringKey("$label_period_flow_message_start"); // START MESSAGE
				 updateForm(); // UPDATE MODAL FORM VIEW
				   											    
				}
        			
		   if(step == 2) {		   
			    displayMessage += "\n"+localeSat.getStringKey("$label_period_flow_message_processing_data")+" "+satList.get(indice).getNome()+" ..."; // PROCESS INFORMATION
			    updateForm(); // UPDATE MODAL FORM VIEW			    
		   	}
							
	        if(step == 3) {        	     	
	        	displayMessage += "\n"+localeSat.getStringKey("$label_period_flow_message_ended_process"); // PROCESS ENDED       		        		        	
	        	updateForm(); // UPDATE MODAL FORM VIEW	        	 	
	        }
					   
	        if(step == 4) {        	
	   			displayMessage += "\n"+localeSat.getStringKey("$label_period_flow_message_generate_excel_file"); // GENERATE EXCEL FILE	   			                  				
	   			updateForm(); // UPDATE MODAL FORM VIEW   				
	        }
   		        
	        if(step == 5) {        	
        		displayMessage += "\n"+localeSat.getStringKey("$label_period_flow_message_end");  // COMPLETED
        		updateForm(); // UPDATE MODAL FORM VIEW       	  	        	    	
	         }       
	        
	        if(step != 5)
	        	SessionUtil.executeScript("scrollOnBottom();");  // SCROLL ON BOTTOM DURING PROCESS
		 }
	
		// -----------------------------------------------------------------------------------------   	   	     	     	     
			     	     
	     public void generateExcelPeriodFlow(String[] equips) throws IOException {
	    	 
	    	 for(int i = 0; i < equips.length; i++) {	// EXECUTA O FOR PARA CADA EQUIPAMENTO SELECIONADO
	    		 
	    		 	createExcelHeader(sheet, i, satList); // CRIA A FOLHA DO EXCEL	    	 
	    		 	preencherDadosExcel(workbook, sheet, satList, tam, i, resultQuery); // PREENCHE DADOS DO EXCEL APOS CRIAR A FOLHA
	    		 	
	    	 }	    	    	 
	     }	       
	     
	  // ----------------------------------------------------------------------------------------- 
	     
  }
