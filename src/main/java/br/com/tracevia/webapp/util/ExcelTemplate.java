package br.com.tracevia.webapp.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.groupdocs.conversion.Converter;
import com.groupdocs.conversion.options.convert.PdfConvertOptions;
import com.mysql.cj.conf.ConnectionUrlParser.Pair;

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.sat.SAT;
import br.com.tracevia.webapp.model.sat.SatTableHeader;
import br.com.tracevia.webapp.model.sat.laneFilter;

/**
 * Classe com modelos de planilhas EXCEL
 * @author Wellington 13/10/2021
 * @version 1.0
 * @since 1.0
 * 
 */
public class ExcelTemplate {

	ExcelUtil utilSheet;

	private static XSSFWorkbook workbook;	
	private static XSSFSheet sheet;
	private static XSSFRow row;
	
	private EquipmentsDAO dao;
	private List<Equipments> equipsInfo;
	private List<SAT> satInfo;

	private static String FONT_ARIAL = "Arial";
	
	private static String NUMBER_REGEX = "\\d+";
	private static String NUMBER_DECIMAL = "^[0-9]\\d*(\\.\\d+)?$";
	
	LocaleUtil localeExcel, localeSAT;

	// DEFAULT FONTS

	// standardFont - fonte padrÃ£o para os relÃ¡torios
	// boldFont - fonte em negrito
	// titleFont - fonte para tÃ­tulos dos relatÃ³rios
	// tableHeaderFont - fonte para cabeÃ§alho das tabelas

	Font standardFont, boldFont, titleFont, tableHeadFont;

	// COUNT FLOW FONTS	

	// countFlowFont - fonte para uso no header e subheaders
	// countFlowFontBody1 - fonte para a primeira parte do corpo do relatÃ³rio
	// countFlowFontBody2 - fonte para a segunda parte do corpo do relatÃ³rio

	Font countFlowFont, countFlowFontBody1, countFlowFontBody2;

	// ---------------------------------------------------------------------------------------------------------------

	// DEFAULT STYLES 

	// titleStyle - estilo para o tÃ­tulo principal do relatÃ³rio
	// standardStyle - estilo padrÃ£o para uso no corpo do relatÃ³rio
	// tableHeaderStyle - estilo para uso no cabeÃ§alho da tabela
	// leftAlignStandardStyle - estilo para alinhamento a esquerda (padrÃ£o)
	// rightAlignBoldStyle - estilo em negrito com alinhamento a direita
	// dateTitleStyle - estilo para datas ao lado do tÃ­tulo principal
	// dateTimeStyle - estilo para data e hora no corpo do relatÃ³rio
	// centerBoldStyle - estilo em negrito para centralizar
	// centerAlignStandardStyle - estilo padrÃ£o para centralizar	

	CellStyle titleStyle, standardStyle, tableHeadStyle, leftAlignStandardStyle, rightAlignBoldStyle, dateTitleStyle,   
	dateTimeStyle, centerBoldStyle, centerAlignStandardStyle, subHeaderClassStyle, centerStyle;   

	// COUNT FLOW STYLES 

	// bgColorHeaderStyle - estilo do cabeÃ§alho principal
	// bgColorSubHeaderStyle1 - estilo do primeiro subheader
	// bgColorSubHeaderStyle2 - estilo do segundo subheader
	// bgColorBodyStyle1 - estilo da primeira parte do corpo do relatÃ³rio
	// bgColorBodyStyle2 - estilo da segunda parte do corpo do relatÃ³rio

	CellStyle bgColorHeaderStyle, bgColorSubHeaderStyle1, bgColorSubHeaderStyle2, bgColorBodyStyle1, bgColorBodyStyle2;

	// ---------------------------------------------------------------------------------------------------------------

	public ExcelTemplate() {

		utilSheet = new ExcelUtil();
		workbook = new XSSFWorkbook();

		localeExcel = new LocaleUtil();		
		localeExcel.getResourceBundle(LocaleUtil.LABELS_EXCELSHEET);
				
		// ----------------------------------------------------------------------------------------------------------------

		// DEFAULT FONTS
		
		standardFont = utilSheet.createFont(workbook, FONT_ARIAL, 10, false, false, IndexedColors.BLACK); // FONTE DE USO PADRÃO 			 
	    boldFont = utilSheet.createFont(workbook,  FONT_ARIAL, 10, true, false, IndexedColors.BLACK); // FONTE EM NEGRITO
	    titleFont = utilSheet.createFont(workbook,  FONT_ARIAL, 16, true, false, IndexedColors.BLACK); // FONTE PARA O TÃ�TULO
	    tableHeadFont = utilSheet.createFont(workbook, FONT_ARIAL, 10, true, false, IndexedColors.WHITE); // FONT PARA CABEÃ‡ALHO DA TABELA
	    
		// COUNT FLOW FONTS
	    
	    countFlowFont = utilSheet.createFont(workbook, FONT_ARIAL, 11, true, false, IndexedColors.WHITE); // FONTE PADRÃO COUNT FLOW
	    countFlowFontBody1 = utilSheet.createFont(workbook, FONT_ARIAL, 10, false, false, IndexedColors.ORANGE); // FONTE PARA APRESENTAÃ‡ÃƒO DE DADOS 1
	    countFlowFontBody2 = utilSheet.createFont(workbook, FONT_ARIAL, 10, false, false, IndexedColors.GREEN); // FONTE PARA APRESENTAÃ‡ÃƒO DE DADOS 2	       

		// ----------------------------------------------------------------------------------------------------------------

		// DEFAULT STYLES	
	    
	    // ESTILO PADRÃƒO
	    standardStyle = utilSheet.createCellStyle(workbook, standardFont, HorizontalAlignment.CENTER, IndexedColors.WHITE, FillPatternType.NO_FILL, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	   	// ESTILO PARA TÃ�TULO
	    titleStyle = utilSheet.createCellStyle(workbook, titleFont, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, true, IndexedColors.WHITE, FillPatternType.NO_FILL, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	    // ESTILO PARA CABEÃ‡ALHO DAS TABELAS
	    tableHeadStyle = utilSheet.createCellStyle(workbook, tableHeadFont, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, IndexedColors.LIGHT_BLUE, FillPatternType.SOLID_FOREGROUND,ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	    // ESTILO PARA CABEÃ‡ALHO ENTRE DATAS 
	    dateTitleStyle = utilSheet.createCellStyle(workbook, standardFont, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, true, IndexedColors.WHITE, FillPatternType.NO_FILL, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	    // ESTILO PARA CAMPO DE DATAS EM NEGRITO
	    dateTimeStyle = utilSheet.createCellStyle(workbook, boldFont, HorizontalAlignment.CENTER, IndexedColors.WHITE, FillPatternType.NO_FILL, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
        // ESTILO NEGRITO CENTRALIZADO (SEM BORDAS)  
	    centerBoldStyle = utilSheet.createCellStyle(workbook, boldFont, HorizontalAlignment.CENTER, IndexedColors.WHITE, FillPatternType.NO_FILL, ExcelUtil.ALL_BORDERS, BorderStyle.NONE);
	    // ESTILO PADRÃƒO DE ALINHAMENTO CENTRAL	(SEM BORDAS)  
	    centerAlignStandardStyle = utilSheet.createCellStyle(workbook, standardFont, HorizontalAlignment.CENTER, IndexedColors.WHITE, FillPatternType.NO_FILL, ExcelUtil.ALL_BORDERS, BorderStyle.NONE);
		// ESTILO PARA ALINHAMENTO A ESQUERDA FONTE PADRÃƒO (SEM BORDAS)
	    leftAlignStandardStyle = utilSheet.createCellStyle(workbook, standardFont, HorizontalAlignment.LEFT, IndexedColors.WHITE, FillPatternType.NO_FILL, ExcelUtil.ALL_BORDERS, BorderStyle.NONE);
		// ESTILO PARA ALINHAMENTO A DIREITA EM NEGRITO (SEM BORDAS)
	    rightAlignBoldStyle = utilSheet.createCellStyle(workbook, boldFont, HorizontalAlignment.RIGHT, IndexedColors.WHITE, FillPatternType.NO_FILL, ExcelUtil.ALL_BORDERS, BorderStyle.NONE);
	    // ESTILO NEGRITO CENTRALIZADO (SEM BORDAS)  
	    subHeaderClassStyle = utilSheet.createCellStyle(workbook, boldFont, HorizontalAlignment.CENTER, IndexedColors.WHITE, FillPatternType.NO_FILL, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	    //JUST CENTER STYLE	    
	    centerStyle = utilSheet.createCellStyle(workbook, standardFont, HorizontalAlignment.CENTER, IndexedColors.WHITE, FillPatternType.NO_FILL, ExcelUtil.ALL_BORDERS, BorderStyle.NONE);
	    		
	    // COUNT FLOW STYLES

	    // ESTILO HEADER
	    bgColorHeaderStyle = utilSheet.createCellStyle(workbook, countFlowFont, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, true, IndexedColors.BLUE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	    // ESTILO SUB HEADER 1
	    bgColorSubHeaderStyle1 = utilSheet.createCellStyle(workbook, countFlowFont, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false, IndexedColors.LIGHT_BLUE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	    // ESTILO SUB HEADER 2
	    bgColorSubHeaderStyle2 = utilSheet.createCellStyle(workbook, countFlowFont, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false, IndexedColors.LIGHT_ORANGE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	    // ESTILO BODY 1
	    bgColorBodyStyle1 = utilSheet.createCellStyle(workbook, countFlowFontBody1, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, IndexedColors.LIGHT_CORNFLOWER_BLUE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	    //ESTILO BODY 2
	    bgColorBodyStyle2 = utilSheet.createCellStyle(workbook, countFlowFontBody2, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, IndexedColors.LIGHT_YELLOW, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);

		// ----------------------------------------------------------------------------------------------------------------
	
	}	
	  	 
	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// HEADER
	// ----------------------------------------------------------------------------------------------------------------

	public void excelFileHeader(XSSFSheet sheet, XSSFRow row, String pathLogo, String module, int columns, String fileTitle, 
			String[] dates, String[] period, List<String> equipId, int dayIndex, boolean isMultiSheet, boolean isDirectionsOnSheet) {

		DateTimeApplication dta = new DateTimeApplication();
		TranslationMethods tm = new TranslationMethods();
	
		// INDEX DA COLUNA DE INICIO DA DATA
		
		int columnsIndex = 0;
		int columnStartDate = 0;
		int columnEndDate = 0;
			
		String headerDates = "";
		
		// MINIMO 6 COLUNAS PADRÃO
		if(columns < 6) {
			columnsIndex = 6;
			columnStartDate = columnsIndex + 1;
			columnEndDate = columnsIndex + 3;	
			
		}		
		else { 
			
			columnsIndex = columns;
			columnStartDate = columnsIndex + 1;
			columnEndDate = columnsIndex + 3;	
		}
		
	
		// ----------------------------------------------------------------------------------------------------------------
					
		if(!module.equals("sat")) {
		     if(equipId.size() == 1 && !module.equals("default"))		
		        equipsInfo = genericInfo(equipId, module);
				
		    else equipsInfo = defaultGenericInfo();			    
		   		
		}else {
			
			 if(equipId.size() == 1)		
			     satInfo = SATInfo(equipId);
					
			 else satInfo = defaultSATInfo();			
		   
		     }
					
		// ----------------------------------------------------------------------------------------------------------------
				
		// HEADER

		// CRIAR COLUNAS E LINHAS DO HEADER
		utilSheet.createRows(sheet, row, 0, 4);
		utilSheet.createCells(sheet, row, 0, columnEndDate, 0, 3);

		// DEFINIR IMAGEM
		utilSheet.createImage(workbook, sheet, pathLogo, 0, 2, 0, 4, 1, 1, 1, 1, 1); // CRIAR IMAGEM
		utilSheet.setCellsStyle(sheet, row, standardStyle, 0, 1, 0, 3); // ESTILO CAMPOS IMAGEM
		
		// DEFINIR O TÍTULO	DO HEADER					
		utilSheet.setCellValue(sheet, row, 0, 2, fileTitle);
		utilSheet. setCellsStyle(sheet, row, titleStyle, 2, columnsIndex, 0, 3); // ESTILO TITULO
		
		// HEADER DATE TEMPLATE
		
		if(!isMultiSheet || period[1].toUpperCase().equals("DAY") || period[1].toUpperCase().equals("MONTH") || period[1].toUpperCase().equals("YEAR")) 	
		    headerDates = localeExcel.getStringKey("excel_sheet_header_date_from")+": " + dates[0]+ "\n"+localeExcel.getStringKey("excel_sheet_header_date_to")+": " + dates[1];

		else headerDates = localeExcel.getStringKey("excel_sheet_header_date_from")+": " + dates[dayIndex]+ "\n"+localeExcel.getStringKey("excel_sheet_header_date_to")+": " + dates[dayIndex];
		
		// INSERIR O TEMPLATE
		utilSheet.setCellValue(sheet, row, 0, columnStartDate, headerDates);	
		utilSheet.setCellsStyle(sheet, row, dateTitleStyle, columnStartDate, columnEndDate, 0, 3); // ESTILO DATAS
		
		// HEADER COLUMNS DINAMIC MERGE
		utilSheet.mergeBetweenColumns(sheet, 0, 1, 1, 4); // IMAGE
		utilSheet.mergeBetweenColumns(sheet, 2, columnsIndex, 1, 4); // TITLE
		utilSheet.mergeBetweenColumns(sheet, columnStartDate, columnEndDate, 1, 4); // DATE AND TIME
		
		//
		// ----------------------------------------------------------------------------------------------------------------	    
		// SUBHEADER 
		// ----------------------------------------------------------------------------------------------------------------

		// MERGE CELLS
		//utilSheet.mergeCells(sheet, "A6:B6");	
		utilSheet.mergeCells(sheet, "F6:H6");
		utilSheet.mergeCells(sheet, "I6:K6");
		
		// ----------------------------------------------------------------------------------------------------------------				
				
		// CRIAR LINHA 1 - SUBHEADER
		utilSheet.createRow(sheet, row, 5);

		// EQUIPAMENTO LABEL
		utilSheet.createCell(sheet, row, 5, 1);
		utilSheet.setCellValue(sheet, row, 5, 1, localeExcel.getStringKey("excel_sheet_header_equipment"));
		utilSheet.setCellStyle(sheet, row, centerBoldStyle, 5, 1); 	
       				
		// NOME DO EQUIPAMENTO
		utilSheet.createCell(sheet, row, 5, 2);		
		utilSheet.setCellValue(sheet, row, 5, 2, module.equals("sat")? satInfo.get(0).getNome() : equipsInfo.get(0).getNome()); // null? 0 :
		utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 5, 2); 
 
		// DATA DE CONSULTA LABEL
		utilSheet.createCell(sheet, row, 5, 5);
		utilSheet.setCellValue(sheet, row, 5, 5, localeExcel.getStringKey("excel_sheet_header_consultation_date"));
		utilSheet.setCellStyle(sheet, row, rightAlignBoldStyle, 5, 5);

		// DATA
		utilSheet.createCell(sheet, row, 5, 8);
		utilSheet.setCellValue(sheet, row, 5, 8, " " + dta.currentDateTime());	
		utilSheet.setCellStyle(sheet, row, leftAlignStandardStyle, 5, 8);

		// ----------------------------------------------------------------------------------------------------------------

		// CRIAR LINHA 2 - SUBHEADER
		utilSheet.createRow(sheet, row, 6);

		// CIDADE LABEL
		utilSheet.createCell(sheet, row, 6, 1);
		utilSheet.setCellValue(sheet, row, 6, 1, localeExcel.getStringKey("excel_sheet_header_city"));
		utilSheet.setCellStyle(sheet, row, centerBoldStyle, 6, 1);

		// CIDADE
		utilSheet.createCell(sheet, row, 6, 2);
		utilSheet.setCellValue(sheet, row, 6, 2, module.equals("sat")? satInfo.get(0).getCidade() : equipsInfo.get(0).getCidade());
		utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 6, 2);

		// PARA REPORTS QUE NÃO USAM PERIODO
		try {
		
		if(!period.equals("")) {

		// PERÍODO LABEL
		utilSheet.createCell(sheet, row, 6, 7);
		utilSheet.setCellValue(sheet, row, 6, 7, localeExcel.getStringKey("excel_sheet_header_period"));
		utilSheet.setCellStyle(sheet, row, centerBoldStyle, 6, 7);

		// PERIODO
		utilSheet.createCell(sheet, row, 6, 8);
		utilSheet.setCellValue(sheet, row, 6, 8, period[2]);
		utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 6, 8);
		
		}
		
		}catch(NullPointerException ex) {}

		// ----------------------------------------------------------------------------------------------------------------

		// CRIAR LINHA 3 - SUBHEADER
		utilSheet.createRow(sheet, row, 7);

		// RODOVIA / ESTRADA LABEL
		utilSheet.createCell(sheet, row, 7, 1);
		utilSheet.setCellValue(sheet, row, 7, 1, localeExcel.getStringKey("excel_sheet_header_highway"));
		utilSheet.setCellStyle(sheet, row, centerBoldStyle, 7, 1);

		// NOME DA RODOVIA / ESTRADA
		utilSheet.createCell(sheet, row, 7, 2);
		utilSheet.setCellValue(sheet, row, 7, 2, module.equals("sat")? satInfo.get(0).getEstrada() : equipsInfo.get(0).getEstrada());
		utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 7, 2);
					    
		// ----------------------------------------------------------------------------------------------------------------

		// CRIAR LINHA 4 - SUBHEADER
		utilSheet.createRow(sheet, row, 8);

		// KM LABEL
		utilSheet.createCell(sheet, row, 8, 1);
		utilSheet.setCellValue(sheet, row, 8, 1, localeExcel.getStringKey("excel_sheet_header_km"));
		utilSheet.setCellStyle(sheet, row, centerBoldStyle, 8, 1);

		// KM
		utilSheet.createCell(sheet, row, 8, 2);
		utilSheet.setCellValue(sheet, row, 8, 2, module.equals("sat")? satInfo.get(0).getKm() : equipsInfo.get(0).getKm());
		utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 8, 2);

		// ----------------------------------------------------------------------------------------------------------------

		// CASO O NÚMERO DE LINHAS FOR MAIOR QUE 1 
		// ENTRA NESSA CONDIÇÃO
		// UTILIZADA ESPECIFICAMENTE PARA O SAT
		if(module.equals("sat")) {

			// CRIAR LINHA 5 - SUBHEADER
			utilSheet.createRow(sheet, row, 9);
			utilSheet.createRow(sheet, row, 10);

			// LANE LABELS
			utilSheet.createCell(sheet, row, 9, 1);
			utilSheet.setCellValue(sheet, row, 9, 1, localeExcel.getStringKey("excel_sheet_header_lanes"));
			utilSheet.setCellStyle(sheet, row, centerBoldStyle, 9, 1); 

			// NÚMERO DE LINHAS
			utilSheet.createCell(sheet, row, 9, 2);
			
			if(satInfo.get(0).getQtdeFaixas().matches(NUMBER_REGEX))
				utilSheet.setCellValue(sheet, row, 9, 2, Integer.parseInt(satInfo.get(0).getQtdeFaixas()));
			
			else utilSheet.setCellValue(sheet, row, 9, 2, satInfo.get(0).getQtdeFaixas()); 
			
			utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 9, 2);
			
			// -------------------------------------------------------------------------------------------------------
			
			if(!isDirectionsOnSheet) {
							
			// SENTIDO LABEL
			 utilSheet.createCell(sheet, row, 10, columns - 2);
			 utilSheet.setCellValue(sheet, row, 10, columns-2, localeExcel.getStringKey("excel_sheet_header_direction"));
			 utilSheet.setCellStyle(sheet, row, centerBoldStyle, 10, columns - 2);

			 // SENTIDO
			 utilSheet.createCell(sheet, row, 10, columns - 1);
			 
			 utilSheet.setCellValue(sheet, row, 10, columns - 1, tm.directions(satInfo.get(0).getSentidos()));
						
			 utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 10, columns - 1);
								
			}
		}
		
		// ----------------------------------------------------------------------------------------------------------------
				
	}
	
	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// HEADER
	// ----------------------------------------------------------------------------------------------------------------

	public void excelFileHeader(XSSFSheet sheet, XSSFRow row, String pathLogo, String module, int columns, String fileTitle, 
			String[] dates, String[] period, String equipId, int dayIndex, boolean isDirectionsOnSheet) {

		DateTimeApplication dta = new DateTimeApplication();
		TranslationMethods tm = new TranslationMethods();
			
		// INDEX DA COLUNA DE INICIO DA DATA
		
		int columnsIndex = 0;
		int columnStartDate = 0;
		int columnEndDate = 0;
			
		String headerDates = "";
		
		// MINIMO 6 COLUNAS PADRÃO
		if(columns < 6) {
			columnsIndex = 6;
			columnStartDate = columnsIndex + 1;
			columnEndDate = columnsIndex + 3;	
			
		}		
		else { 
			
			columnsIndex = columns;
			columnStartDate = columnsIndex + 1;
			columnEndDate = columnsIndex + 3;	
		}
			
	
		// ----------------------------------------------------------------------------------------------------------------
					
		if(module.equals("sat")) {					
			 satInfo = SATInfo(equipId);			
		}
		   
		else equipsInfo = genericInfo(equipId, module);				
		  					
		// ----------------------------------------------------------------------------------------------------------------
				
		// HEADER

		// CRIAR COLUNAS E LINHAS DO HEADER
		utilSheet.createRows(sheet, row, 0, 4);
		utilSheet.createCells(sheet, row, 0, columnEndDate, 0, 3);

		// DEFINIR IMAGEM
		utilSheet.createImage(workbook, sheet, pathLogo, 0, 2, 0, 4, 1, 1, 1, 1, 1); // CRIAR IMAGEM
		utilSheet.setCellsStyle(sheet, row, standardStyle, 0, 1, 0, 3); // ESTILO CAMPOS IMAGEM
		
		// DEFINIR O TÃ�TULO	DO HEADER					
		utilSheet.setCellValue(sheet, row, 0, 2, fileTitle);
		utilSheet. setCellsStyle(sheet, row, titleStyle, 2, columnsIndex, 0, 3); // ESTILO TITULO
		
		// HEADER DATE TEMPLATE		
	    headerDates = localeExcel.getStringKey("excel_sheet_header_date_from")+": " + dates[0]+ "\n"+localeExcel.getStringKey("excel_sheet_header_date_to")+": " + dates[1];
		
		// INSERIR O TEMPLATE
		utilSheet.setCellValue(sheet, row, 0, columnStartDate, headerDates);	
		utilSheet.setCellsStyle(sheet, row, dateTitleStyle, columnStartDate, columnEndDate, 0, 3); // ESTILO DATAS
		
		// HEADER COLUMNS DINAMIC MERGE
		utilSheet.mergeBetweenColumns(sheet, 0, 1, 1, 4); // IMAGE
		utilSheet.mergeBetweenColumns(sheet, 2, columnsIndex, 1, 4); // TITLE
		utilSheet.mergeBetweenColumns(sheet, columnStartDate, columnEndDate, 1, 4); // DATE AND TIME
		//
		// ----------------------------------------------------------------------------------------------------------------	    
		// SUBHEADER 
		// ----------------------------------------------------------------------------------------------------------------

		// MERGE CELLS
		//utilSheet.mergeCells(sheet, "A6:B6");	
		utilSheet.mergeCells(sheet, "F6:H6");
		utilSheet.mergeCells(sheet, "I6:K6");
		
		// ----------------------------------------------------------------------------------------------------------------				
				
		// CRIAR LINHA 1 - SUBHEADER
		utilSheet.createRow(sheet, row, 5);

		// EQUIPAMENTO LABEL
		utilSheet.createCell(sheet, row, 5, 1);
		utilSheet.setCellValue(sheet, row, 5, 1, localeExcel.getStringKey("excel_sheet_header_equipment"));
		utilSheet.setCellStyle(sheet, row, centerBoldStyle, 5, 1); 	
       				
		// NOME DO EQUIPAMENTO
		utilSheet.createCell(sheet, row, 5, 2);		
		utilSheet.setCellValue(sheet, row, 5, 2, module.equals("sat") ? satInfo.get(0).getNome() : equipsInfo.get(0).getNome()); // null? 0 :
		utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 5, 2); 
 
		// DATA DE CONSULTA LABEL
		utilSheet.createCell(sheet, row, 5, 5);
		utilSheet.setCellValue(sheet, row, 5, 5, localeExcel.getStringKey("excel_sheet_header_consultation_date"));
		utilSheet.setCellStyle(sheet, row, rightAlignBoldStyle, 5, 5);

		// DATA
		utilSheet.createCell(sheet, row, 5, 8);
		utilSheet.setCellValue(sheet, row, 5, 8, " " + dta.currentDateTime());	
		utilSheet.setCellStyle(sheet, row, leftAlignStandardStyle, 5, 8);

		// ----------------------------------------------------------------------------------------------------------------

		// CRIAR LINHA 2 - SUBHEADER
		utilSheet.createRow(sheet, row, 6);

		// CIDADE LABEL
		utilSheet.createCell(sheet, row, 6, 1);
		utilSheet.setCellValue(sheet, row, 6, 1, localeExcel.getStringKey("excel_sheet_header_city"));
		utilSheet.setCellStyle(sheet, row, centerBoldStyle, 6, 1);

		// CIDADE
		utilSheet.createCell(sheet, row, 6, 2);
		utilSheet.setCellValue(sheet, row, 6, 2, module.equals("sat")? satInfo.get(0).getCidade() : equipsInfo.get(0).getCidade());
		utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 6, 2);

		// PARA REPORTS QUE NÃO USAM PERIODO
		try {
		
		if(!period.equals("")) {

			// PERÍODO LABEL
			utilSheet.createCell(sheet, row, 6, 7);
			utilSheet.setCellValue(sheet, row, 6, 7, localeExcel.getStringKey("excel_sheet_header_period"));
			utilSheet.setCellStyle(sheet, row, centerBoldStyle, 6, 7);
	
			// PERIODO
			utilSheet.createCell(sheet, row, 6, 8);
			utilSheet.setCellValue(sheet, row, 6, 8, period[2]);
			utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 6, 8);
		
		}
		
		}catch(NullPointerException ex) {}

		// ----------------------------------------------------------------------------------------------------------------

		// CRIAR LINHA 3 - SUBHEADER
		utilSheet.createRow(sheet, row, 7);

		// RODOVIA / ESTRADA LABEL
		utilSheet.createCell(sheet, row, 7, 1);
		utilSheet.setCellValue(sheet, row, 7, 1, localeExcel.getStringKey("excel_sheet_header_highway"));
		utilSheet.setCellStyle(sheet, row, centerBoldStyle, 7, 1);

		// NOME DA RODOVIA / ESTRADA
		utilSheet.createCell(sheet, row, 7, 2);
		utilSheet.setCellValue(sheet, row, 7, 2, module.equals("sat")? satInfo.get(0).getEstrada() : equipsInfo.get(0).getEstrada());
		utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 7, 2);
					    
		// ----------------------------------------------------------------------------------------------------------------

		// CRIAR LINHA 4 - SUBHEADER
		utilSheet.createRow(sheet, row, 8);

		// KM LABEL
		utilSheet.createCell(sheet, row, 8, 1);
		utilSheet.setCellValue(sheet, row, 8, 1, localeExcel.getStringKey("excel_sheet_header_km"));
		utilSheet.setCellStyle(sheet, row, centerBoldStyle, 8, 1);

		// KM
		utilSheet.createCell(sheet, row, 8, 2);
		utilSheet.setCellValue(sheet, row, 8, 2, module.equals("sat")? satInfo.get(0).getKm() : equipsInfo.get(0).getKm());
		utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 8, 2);

		// ----------------------------------------------------------------------------------------------------------------

		// CASO O NÃšMERO DE LINHAS FOR MAIOR QUE 1 
		// ENTRA NESSA CONDIÃ‡ÃƒO
		// UTILIZADA ESPECIFICAMENTE PARA O SAT
		if(module.equals("sat")) {

			// CRIAR LINHA 5 - SUBHEADER
			utilSheet.createRow(sheet, row, 9);
			utilSheet.createRow(sheet, row, 10);

			// LANE LABELS
			utilSheet.createCell(sheet, row, 9, 1);
			utilSheet.setCellValue(sheet, row, 9, 1, localeExcel.getStringKey("excel_sheet_header_lanes"));
			utilSheet.setCellStyle(sheet, row, centerBoldStyle, 9, 1); 

			// NÚMERO DE LINHAS
			utilSheet.createCell(sheet, row, 9, 2);
			
			if(satInfo.get(0).getQtdeFaixas().matches(NUMBER_REGEX))
				utilSheet.setCellValue(sheet, row, 9, 2, Integer.parseInt(satInfo.get(0).getQtdeFaixas()));
			
			else utilSheet.setCellValue(sheet, row, 9, 2, satInfo.get(0).getQtdeFaixas()); 
			
			utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 9, 2);
			
			// -------------------------------------------------------------------------------------------------------
									
			if(!isDirectionsOnSheet) {
							
			// SENTIDO LABEL
			 utilSheet.createCell(sheet, row, 10, columns - 2);
			 utilSheet.setCellValue(sheet, row, 10, columns-2, localeExcel.getStringKey("excel_sheet_header_direction"));
			 utilSheet.setCellStyle(sheet, row, centerBoldStyle, 10, columns - 2);

			 // SENTIDO
			 utilSheet.createCell(sheet, row, 10, columns - 1);
			
		     utilSheet.setCellValue(sheet, row, 10, columns - 1, tm.directions(satInfo.get(0).getSentidos()));
				
		     utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 10, columns - 1);
		     
			}
								
		}
		
		// ----------------------------------------------------------------------------------------------------------------
				
	}

		// ----------------------------------------------------------------------------------------------------------------
		// TOTAL
		// ----------------------------------------------------------------------------------------------------------------
		// ----------------------------------------------------------------------------------------------------------------
				
		public Integer createTotalRow(XSSFSheet sheet, XSSFRow row, CellStyle tableHeader, int rowTotal, int columnsLength, List<String[]> lines, boolean isEquipNameSheet) {
					       		
			        // CREATE ROW TOTAL
					utilSheet.createRow(sheet, row, rowTotal);		 

					utilSheet.createCells(sheet, row, 0, columnsLength, rowTotal, rowTotal);   // CREATE CELLS			
					utilSheet.setCellValue(sheet, row, rowTotal, 0, localeExcel.getStringKey("excel_sheet_table_total")); // SET FIRST CELL VALUE
									
					int startColumn = 0;
					int total = rowTotal + 1; // SOMA -SE + 1 NESSE CASO (REGRA DO MERGE) 
					
					String startColumnLetter = "A";
					String endColumnLetter = "";

					// ----------------------------------------------------------------------------------------------------------------

					 // VERFICA SE AS # PRIMEIRAS COLUNAS SÃO STRINGS	
										
						for(int c = 0; c < 3; c++) {
							 
							if(!lines.get(0)[c].matches(NUMBER_DECIMAL)) {
								startColumn++;																
							    endColumnLetter = CellReference.convertNumToColString((startColumn - 1)); // END COLUMN LETTER					 
							}							
						}
						
						if(isEquipNameSheet) { // CASO ENTRE NESSA CONDICAO
							startColumn++;
						 	endColumnLetter = CellReference.convertNumToColString((startColumn - 1)); // END COLUMN LETTER			 	    	
						}
						
						// MERGE START CELLS ON INIT TOTAL
						utilSheet.mergeCells(sheet, startColumnLetter+""+(total)+":"+endColumnLetter+""+(total));			
						utilSheet.setCellsStyle(sheet, row, tableHeader, 0, startColumn-1, rowTotal, rowTotal); // SET FIRST CELL STYLE	
					
					// ----------------------------------------------------------------------------------------------------------------		
						
						return startColumn;
					
		          }
		
		// ----------------------------------------------------------------------------------------------------------------

	/**
	 * MÃ©todo para criar colunas com total para SOMA
	 * @author Wellington 15/10/2021
	 * @version 1.0
	 * @since 1.0  
	 * @param sheet - objeto de representaÃ§Ã£o de alto nÃ­vel de uma planilha
	 * @param row - objeto de representaÃ§Ã£o de alto nÃ­vel de uma linha de uma planilha
	 * @param standard - estilo padrÃ£o para colunas atribuidas a fÃ³rmula
	 * @param tableHeader - estilo apenas para a string "TOTAL"
	 * @param rowTotal - Ãºltima linha a ser apresentado o total	
	 * @param multi - define se o template do total Ã© de mÃºltiplos equipamentos ou nÃ£o
	 * @param columnsLength - nÃºmero de colunas  
	 * @param rowIni - linha inicial		
	 * @param rowEnd - linha final	
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFSheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFRow.html
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html
	 * 
	 */
	public void totalSum(XSSFWorkbook wb, XSSFSheet sheet, XSSFRow row, CellStyle standard, List<String[]> lines, String totalType, int columnsLength, int startColumn, int rowIni, int rowEnd) {
				
		utilSheet.totalExcelSum(wb, sheet, row, standard, lines, totalType, columnsLength, startColumn, rowIni, rowEnd); // SET CELL FORMULA			

	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * Método para criar colunas com total MÉDIA
	 * @author Wellington 15/10/2021
	 * @version 1.0
	 * @since 1.0  
	 * @param sheet - objeto de representaÃ§Ã£o de alto nÃ­vel de uma planilha
	 * @param row - objeto de representaÃ§Ã£o de alto nÃ­vel de uma linha de uma planilha
	 * @param standard - estilo padrÃ£o para colunas atribuidas a fÃ³rmula
	 * @param tableHeader - estilo apenas para a string "TOTAL"
	 * @param rowTotal - Ãºltima linha a ser apresentado o total	
	 * @param multi - define se o template do total Ã© de mÃºltiplos equipamentos ou nÃ£o
	 * @param columnsLength - nÃºmero de colunas  
	 * @param rowIni - linha inicial		
	 * @param rowEnd - linha final	
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFSheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFRow.html
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html
	 * 
	 */
	public void totalAverage(XSSFSheet sheet, XSSFRow row, CellStyle standard, List<String[]> lines, int columnsLength, int startColumn, int rowIni, int rowEnd) {

		utilSheet.totalExcelAverage(sheet, row, standard, lines, columnsLength, startColumn, rowIni, rowEnd); // SET CELL FORMULA			
    
	}
    
	// ----------------------------------------------------------------------------------------------------------------
    
	/**
	 * MÃ©todo para criar colunas com total para TEMPO
	 * @author Wellington 15/10/2021
	 * @version 1.0
	 * @since 1.0  
	 * @param workbook - objeto de representaÃ§Ã£o de alto nÃ­vel de uma pasta de trabalho para uma planilha
	 * @param sheet - objeto de representaÃ§Ã£o de alto nÃ­vel de uma planilha
	 * @param row - objeto de representaÃ§Ã£o de alto nÃ­vel de uma linha de uma planilha
	 * @param standard - estilo padrÃ£o para colunas atribuidas a fÃ³rmula
	 * @param tableHeader - estilo apenas para a string "TOTAL"
	 * @param rowTotal - Última linha a ser apresentado o total	
	 * @param multi - define se o template do total Ã© de mÃºltiplos equipamentos ou nÃ£o
	 * @param columnsLength - nÃºmero de colunas  
	 * @param rowIni - linha inicial		
	 * @param rowEnd - linha final		
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFWorkbook.html	
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFSheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFRow.html
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html
	 * 
	 */
	public void totalTime(XSSFWorkbook workbook, XSSFSheet sheet, XSSFRow row, CellStyle standard,  int columnNumber, int rowTotal, int rowIni, int rowEnd) {
		
		utilSheet.totalExcelDate(workbook, sheet, row, standard,  columnNumber, rowTotal, rowIni, rowEnd); // SET CELL FORMULA					

	}
	
	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// DOWNLOAD
    // ----------------------------------------------------------------------------------------------------------------

	public void download(String fileName) throws IOException {

		utilSheet.donwloadExcelFile(workbook, fileName);

	}
	
	public ByteArrayOutputStream ToPDF() throws IOException {
		InputStream input = utilSheet.getOutput(workbook);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			Converter converter = new Converter(input);
			converter.convert(output, new PdfConvertOptions());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			output.close();
			input.close();			
		}
		return output;
	}
	
	public void downloadToPDF(String fileName) throws IOException {
		ByteArrayOutputStream output = ToPDF();
		downloadToPDF(output, fileName);
	}
	
	public void downloadToPDF(ByteArrayOutputStream output, String fileName) throws IOException {
		utilSheet.donwloadPDFFile(output, fileName);
	}

	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// TEMPLATE MODEL
	// ----------------------------------------------------------------------------------------------------------------
	
	public void generateExcelFile(List<String> columns, List<String[]> lines, List<Pair<String, List<String[]>>> secondRows, String module, String filterLane, List<String> directions, List<String> equips, 
			String startDate, String endDate, String[] period, String sheetName, String fileTitle, String totalType, boolean isSat, boolean isTotal, boolean isMultiSheet, boolean isEquipNameSheet, boolean isDirectionsOnSheet, boolean division, String classSubHeader) {
		
		sheet = null;	
		row = null;
		
		TranslationMethods tm = new TranslationMethods();
		DateTimeApplication dta = new DateTimeApplication();
		List<laneFilter> lanesLista = new ArrayList<laneFilter>();
		EquipmentsDAO dao = new EquipmentsDAO();
		
		int selectOp = 0;
		int daysCount = 0;
		int subHeaderRow = 0;
		int startLightCol = 0;
		int startHeavyCol = 0;
		int startTruckCol = 0;
		int startBusCol = 0;
		int tableStartRow = 0;
		int dataStartRow = 0;
		int dataEndRow = 0;
		int startCol = 0;
		int endCol = columns.size() - 1;
		boolean singleDirectionFilter = false;
		
		// --------------------------------------------------------------------------------------
		
		if(module.equals("sat")) {
									
		if(!classSubHeader.equals("light-heavy") && !classSubHeader.equals("light-heavy-bus")) {
			    
				tableStartRow = 12;
			    dataStartRow = 13;
			    
			} else {
				
				 subHeaderRow = 12;
				 tableStartRow = 13;
				 dataStartRow = 14;				 
			}							   			   		
		
	    }else { tableStartRow = 11; dataStartRow = 12; }
		
		// --------------------------------------------------------------------------------------
				
		if(!isMultiSheet || (isMultiSheet && period[1].toUpperCase().equals("DAY") || period[1].toUpperCase().equals("MONTH") || period[1].toUpperCase().equals("YEAR"))
				|| (isEquipNameSheet && period[1].toUpperCase().equals("DAY") || period[1].toUpperCase().equals("MONTH") || period[1].toUpperCase().equals("YEAR"))) {
			
			String[] dates = new String[2];
			String[] sheetNames = null;
			
			dates[0] = startDate;
			dates[1] = endDate;
											
			if(isEquipNameSheet) {
			    
				try {
					
					daysCount = (int) dta.diferencaDias(startDate, endDate) + 1;
				
				} catch (ParseException e) {				
					e.printStackTrace();
				} 
										    
				dates = new String[2];
				sheetNames = new String[equips.size()];
				
				dates[0] = startDate;
				dates[1] = endDate;
								
				sheetNames = dao.equipmentsName(module, equips);				
				selectOp = equips.size();
							
			} else {
				
				sheetNames = new String[1];
				sheetNames[0] = sheetName;
				selectOp = 1;				
				
			}
			
			// -----------------------------------------------------
			
			if(isEquipNameSheet) {
								
				if(isTotal)
					dataEndRow = dataStartRow + daysCount;
				
				else dataEndRow = dataStartRow + (daysCount - 1);
				
				
			} else {
			
				if(isTotal)
				dataEndRow = dataStartRow + lines.size();
			
				else dataEndRow = dataStartRow + lines.size() - 1;
			
			}
			
			// -----------------------------------------------------
											
		  for(int op = 0; op < selectOp; op++) {
			  					  
			  if(isEquipNameSheet && division) {

				if(module.equals("sat")) {
											
					if(!classSubHeader.equals("light-heavy") && !classSubHeader.equals("light-heavy-bus")) {
						    
							tableStartRow = 12;
						    dataStartRow = 13;
						    
						} else {
							
							 subHeaderRow = 12;
							 tableStartRow = 13;
							 dataStartRow = 14;				 
						}							   			   		
					
				    }else { tableStartRow = 11; dataStartRow = 12; }
					
					// --------------------------------------------------
					
					if(isTotal)
						dataEndRow = dataStartRow + daysCount;
					
					else dataEndRow = dataStartRow + (daysCount - 1);									
		    	
			  }
			  
			  // --------------------------------------------------------------------------------------------			  
			  
		    	// SheetName
				sheet = workbook.createSheet(sheetNames[op]); // CREATE SHEET NAMES								
			
				if(isEquipNameSheet) 
					excelFileHeader(sheet, row, RoadConcessionaire.externalImagePath, module, columns.size(), fileTitle,  
							dates, period, equips.get(op), op, isDirectionsOnSheet);
								
					else excelFileHeader(sheet, row, RoadConcessionaire.externalImagePath, module, columns.size(), fileTitle,  
							dates, period, equips, op, isMultiSheet, isDirectionsOnSheet);
								
		if(module.contentEquals("sat")) {
										
			if(classSubHeader.equals("light-heavy")) {
				
				utilSheet.createRow(sheet, row, subHeaderRow);
				utilSheet.createCells(sheet, row, startCol, endCol, subHeaderRow, subHeaderRow);
										
				utilSheet.createCell(sheet, row,subHeaderRow, 2);
				utilSheet.setCellValue(sheet, row, subHeaderRow, 2, localeExcel.getStringKey("excel_sheet_light_vehicles_column"));
				utilSheet.setCellsStyle(sheet, row, subHeaderClassStyle, 2, 5, subHeaderRow, subHeaderRow);
				
				utilSheet.createCell(sheet, row, subHeaderRow, 6);
				utilSheet.setCellValue(sheet, row, subHeaderRow, 6, localeExcel.getStringKey("excel_sheet_heavy_vehicles_column"));
				utilSheet.setCellsStyle(sheet, row, subHeaderClassStyle, 6, endCol-1, subHeaderRow, subHeaderRow);
				
				utilSheet.mergeCells(sheet, "C13:F13");
				utilSheet.mergeCells(sheet, "G13:O13");
				
		  }
			
		    // -----------------------------------------------------
			
			else if(classSubHeader.equals("light-heavy-bus")) {
				
				utilSheet.createRow(sheet, row, subHeaderRow);
				utilSheet.createCells(sheet, row, startCol, endCol, subHeaderRow, subHeaderRow);
				
				utilSheet.createCell(sheet, row,subHeaderRow, 2);
				utilSheet.setCellValue(sheet, row, subHeaderRow, 2, localeExcel.getStringKey("excel_sheet_light_vehicles_column"));
				utilSheet.setCellsStyle(sheet, row, subHeaderClassStyle, 2, 5, subHeaderRow, subHeaderRow);
				
				utilSheet.createCell(sheet, row, subHeaderRow, 6);
				utilSheet.setCellValue(sheet, row, subHeaderRow, 6, localeExcel.getStringKey("excel_sheet_truck_vehicles_column"));
				utilSheet.setCellsStyle(sheet, row, subHeaderClassStyle, 6, endCol-1, subHeaderRow, subHeaderRow);
				
				utilSheet.createCell(sheet, row, subHeaderRow, 15);
				utilSheet.setCellValue(sheet, row, subHeaderRow, 15, localeExcel.getStringKey("excel_sheet_bus_column"));
				utilSheet.setCellsStyle(sheet, row, subHeaderClassStyle, 15, endCol-1, subHeaderRow, subHeaderRow);
								
				utilSheet.mergeCells(sheet, "C13:F13");
				utilSheet.mergeCells(sheet, "G13:O13");
				utilSheet.mergeCells(sheet, "P13:T13");
				
			}
		
	      	// -----------------------------------------------------
     	}			
					
		utilSheet.createRow(sheet, row, tableStartRow);			
		utilSheet.createCells(sheet, row, startCol, endCol, tableStartRow, tableStartRow);
		utilSheet.setHeaderCellsValue(sheet, row, tableStartRow, columns);
		utilSheet.setCellsStyle(sheet, row, tableHeadStyle, startCol, endCol, tableStartRow, tableStartRow);									
		
		// CRIAR LINHAS PARA APRESENTACAO DOS DADOS
		utilSheet.createRows(sheet, row, dataStartRow, dataEndRow);
		utilSheet.createCells(sheet, row, startCol, endCol, dataStartRow, dataEndRow);
						
		if(isEquipNameSheet && isDirectionsOnSheet || isEquipNameSheet) {
			
			String[] dirs = null;	
			int dirCol = 0;
			String lane = "", laneValue = "";	
						
			if(isDirectionsOnSheet) {
			
				lanesLista = dao.listarFaixas();
				
				// --------------------------------
				
				if(filterLane.equals("")) {
					
					lane = dao.firstLane(equips.get(op));
					dirs = getFiltersDirection(directions);						
					laneValue = getLane(lane, dirs);
					
				}
				
				else {
					
					  laneValue = dao.getLaneDir(equips.get(op), filterLane);
				      dirs = new String[1];
				      dirs[0] = laneValue;
				      singleDirectionFilter = true;
				
					 }
						
				// ----------------------------------------------------------
			
				dirCol = endCol + 1;
				utilSheet.createCells(sheet, row, dirCol, dirCol, dataStartRow-1, dataEndRow);								
								
				utilSheet.setCellValue(sheet, row, dataStartRow-1, dirCol, laneValue);	
				
				utilSheet.setCellsStyle(sheet, row, centerStyle, dirCol, dirCol, dataStartRow-1, dataEndRow); // DIR COL STYLE
				
			}
				
			utilSheet.fileBodySimpleDirs(sheet, row, columns, null, lines, dirs, equips, lanesLista, op, daysCount, startCol, endCol, dirCol, dataStartRow, dataEndRow, true, isDirectionsOnSheet, singleDirectionFilter);						
												
		} else if(!isEquipNameSheet && isDirectionsOnSheet) {
			
			String[] dirs = null;	
			int dirCol = 0;
			String lane = "", laneValue = "";	
						
			lanesLista = dao.listarFaixas();
			
			// --------------------------------
			
			if(filterLane.equals("")) {
				
				lane = dao.firstLane(equips.get(op));
				dirs = getFiltersDirection(directions);						
				laneValue = getLane(lane, dirs);
				
			}
			
			else {
				
				  laneValue = dao.getLaneDir(equips.get(op), filterLane);
			      dirs = new String[1];
			      dirs[0] = laneValue;
			      singleDirectionFilter = true;
			
				 }
					
			// ----------------------------------------------------------
						
			dirCol = endCol + 1;
			utilSheet.createCells(sheet, row, dirCol, dirCol, dataStartRow-1, dataEndRow);
					
			utilSheet.setCellValue(sheet, row, dataStartRow-1, dirCol, laneValue);									
				
			utilSheet.fileBodySimpleDirection(sheet, row, columns, null, lines, dirs, equips, lanesLista, startCol, endCol, dirCol, dataStartRow, true);			
									
			utilSheet.setCellsStyle(sheet, row, centerStyle, dirCol, dirCol, dataStartRow-1, dataEndRow); // DIR COL STYLE
						
		}else utilSheet.fileBodySimple(sheet, row, columns, lines, startCol, endCol, dataStartRow);
		
		utilSheet.setCellsStyle(sheet, row, standardStyle, startCol, endCol, dataStartRow, dataEndRow);
		
		if(isTotal) {
			
			int startColumn = createTotalRow(sheet, row, tableHeadStyle, dataEndRow, endCol, lines, isEquipNameSheet);
						
			switch (totalType) {
										
			case "average": totalAverage(sheet, row, standardStyle, lines, endCol, startColumn, dataStartRow, dataEndRow); break;
					
			default:  totalSum(workbook, sheet, row, standardStyle, lines, totalType, endCol, startColumn, dataStartRow, dataEndRow); 
				break;
			}		    
		}
		
		// ---------------------------------------------------------------------------------------------------
				
		if(secondRows != null) {
			
			for(Pair<String, List<String[]>> p : secondRows) {
																									
				dataEndRow = dataEndRow + 3;
				
				// CREATE ROW
				utilSheet.createRow(sheet, row, dataEndRow);
				
				if(module.equals("sat")) { 
					
					if(!isDirectionsOnSheet) {
				
						// SENTIDO LABEL
						utilSheet.createCell(sheet, row, dataEndRow, columns.size() - 2);
						utilSheet.setCellValue(sheet, row, dataEndRow, columns.size() - 2, localeExcel.getStringKey("excel_sheet_header_direction"));
						utilSheet.setCellStyle(sheet, row, centerBoldStyle, dataEndRow, columns.size() - 2);
		
						// SENTIDO
						utilSheet.createCell(sheet, row, dataEndRow, columns.size() - 1);
						utilSheet.setCellValue(sheet, row, dataEndRow, columns.size() - 1, tm.direction(p.left));
						utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, dataEndRow, columns.size() - 1);
				
					}
				
				}
				
				// TABLE HEADER
				if(classSubHeader.equals("light-heavy") || classSubHeader.equals("light-heavy-bus")) {
				    tableStartRow = dataEndRow + 3;	
				    subHeaderRow = tableStartRow - 1;
			
				}else  tableStartRow = dataEndRow + 2;	
										
				   dataStartRow = tableStartRow + 1;	
				   
					// -----------------------------------------------------
					
					if(isEquipNameSheet) {
										
						if(isTotal)
							dataEndRow = dataStartRow + daysCount;
						
						else dataEndRow = dataStartRow + (daysCount - 1);
						
						
					} else {
					
						if(isTotal)
							dataEndRow = dataStartRow + p.right.size(); 
							
						else dataEndRow = dataStartRow + p.right.size() - 1;
					
					}
					
					// -----------------------------------------------------				
															
				if(module.equals("sat")) {
													
					if(classSubHeader.equals("light-heavy")) {	
						
					utilSheet.createRow(sheet, row, subHeaderRow);
					utilSheet.createCells(sheet, row, startCol, endCol, subHeaderRow, subHeaderRow);
											
					utilSheet.setCellValue(sheet, row, subHeaderRow, 2, localeExcel.getStringKey("excel_sheet_light_vehicles_column"));
					utilSheet.setCellsStyle(sheet, row, subHeaderClassStyle, 2, 5, subHeaderRow, subHeaderRow);
														
					utilSheet.setCellValue(sheet, row, subHeaderRow, 6, localeExcel.getStringKey("excel_sheet_heavy_vehicles_column"));
					utilSheet.setCellsStyle(sheet, row, subHeaderClassStyle, 6, endCol - 1, subHeaderRow, subHeaderRow);
								
					utilSheet.mergeCells(sheet, "C".concat(""+(subHeaderRow + 1)).concat(":").concat("F").concat(""+(subHeaderRow + 1)));	
					utilSheet.mergeCells(sheet, "G".concat(""+(subHeaderRow + 1)).concat(":").concat("O").concat(""+(subHeaderRow + 1)));
									
				  }
					
					// -----------------------------------------------------
					
					else if(classSubHeader.equals("light-heavy-bus")) {
						
						utilSheet.createRow(sheet, row, subHeaderRow);
						utilSheet.createCells(sheet, row, startCol, endCol, subHeaderRow, subHeaderRow);
						
						utilSheet.createCell(sheet, row,subHeaderRow, 2);
						utilSheet.setCellValue(sheet, row, subHeaderRow, 2, localeExcel.getStringKey("excel_sheet_light_vehicles_column"));
						utilSheet.setCellsStyle(sheet, row, subHeaderClassStyle, 2, 5, subHeaderRow, subHeaderRow);
						
						utilSheet.createCell(sheet, row, subHeaderRow, 6);
						utilSheet.setCellValue(sheet, row, subHeaderRow, 6, localeExcel.getStringKey("excel_sheet_truck_vehicles_column"));
						utilSheet.setCellsStyle(sheet, row, subHeaderClassStyle, 6, endCol-1, subHeaderRow, subHeaderRow);
						
						utilSheet.createCell(sheet, row, subHeaderRow, 15);
						utilSheet.setCellValue(sheet, row, subHeaderRow, 15, localeExcel.getStringKey("excel_sheet_bus_column"));
						utilSheet.setCellsStyle(sheet, row, subHeaderClassStyle, 15, endCol-1, subHeaderRow, subHeaderRow);
										
						utilSheet.mergeCells(sheet, "C".concat(""+(subHeaderRow + 1)).concat(":").concat("F").concat(""+(subHeaderRow + 1)));
						utilSheet.mergeCells(sheet, "G".concat(""+(subHeaderRow + 1)).concat(":").concat("O").concat(""+(subHeaderRow + 1)));		
						utilSheet.mergeCells(sheet, "P".concat(""+(subHeaderRow + 1)).concat(":").concat("T").concat(""+(subHeaderRow + 1)));												
																
					}
				
			      	// -----------------------------------------------------
				
		     	}
							   	
				// CABEÇALHO DA TABELA		
				utilSheet.createRow(sheet, row, tableStartRow);
				utilSheet.createCells(sheet, row, startCol, endCol, tableStartRow, tableStartRow);
				utilSheet.setHeaderCellsValue(sheet, row, tableStartRow, columns);		
				utilSheet.setCellsStyle(sheet, row, tableHeadStyle, startCol, endCol, tableStartRow, tableStartRow);
				
				// CRIAR LINHAS PARA APRESENTAÇÃO DOS DADOS
				utilSheet.createRows(sheet, row, dataStartRow, dataEndRow);
				utilSheet.createCells(sheet, row, startCol, endCol, dataStartRow, dataEndRow);
				
				if(isEquipNameSheet && isDirectionsOnSheet || isEquipNameSheet){
					
					String[] dirs = null;	
					int dirCol = 0;
											
				if(isDirectionsOnSheet) { 
					
					dirCol = endCol + 1;			
					utilSheet.createCells(sheet, row, dirCol, dirCol, dataStartRow-1, dataEndRow);
					
					// -----------------------------------------------------
					
					String laneValue = "";
							
					if(p.left.equals("N"))
						laneValue = "N";
					
					else if(p.left.equals("S"))
						laneValue = "S";
						
					else if(p.left.equals("L"))
						laneValue = "L";
						
					else if(p.left.equals("O"))
						laneValue = "O";				
																
					utilSheet.setCellValue(sheet, row, dataStartRow-1, dirCol, laneValue);
					
					utilSheet.setCellsStyle(sheet, row, centerStyle, dirCol, dirCol, dataStartRow-1, dataEndRow); // DIR COL STYLE
					
				}
							
					utilSheet.fileBodySimpleDirs(sheet, row, columns, p.left, p.right, dirs, equips, lanesLista, op, daysCount, startCol, endCol, dirCol, dataStartRow, dataEndRow, false, isDirectionsOnSheet, singleDirectionFilter);													
															
				} else if(!isEquipNameSheet && isDirectionsOnSheet) {
					
					String[] dirs = null;	
					int dirCol = endCol + 1;			
					utilSheet.createCells(sheet, row, dirCol, dirCol, dataStartRow-1, dataEndRow);
					
					// -----------------------------------------------------
					
					String laneValue = "";
							
					if(p.left.equals("N"))
						laneValue = "N";
					
					else if(p.left.equals("S"))
						laneValue = "S";
						
					else if(p.left.equals("L"))
						laneValue = "L";
						
					else if(p.left.equals("O"))
						laneValue = "O";		
					
					// -----------------------------------------------------
																
					utilSheet.setCellValue(sheet, row, dataStartRow-1, dirCol, laneValue);
					
					utilSheet.fileBodySimpleDirection(sheet, row, columns, p.left, p.right, dirs, equips, lanesLista, startCol, endCol, dirCol, dataStartRow, false);			
											
					utilSheet.setCellsStyle(sheet, row, centerStyle, dirCol, dirCol, dataStartRow-1, dataEndRow); // DIR COL STYLE
					
				
				} else utilSheet.fileBodySimple(sheet, row, columns, p.right, startCol, endCol, dataStartRow);
								
				 	utilSheet.setCellsStyle(sheet, row, standardStyle, startCol, endCol, dataStartRow, dataEndRow);
				
				if(isTotal) {
					
					int startColumn = createTotalRow(sheet, row, tableHeadStyle, dataEndRow, endCol, lines, isEquipNameSheet);
					
					switch (totalType) {
															
					case "average": totalAverage(sheet, row, standardStyle,  lines, endCol, startColumn, dataStartRow, dataEndRow); break;
							
					default:  totalSum(workbook, sheet, row, standardStyle, lines, totalType, endCol, startColumn, dataStartRow, dataEndRow); 
						break;
					}
					
				}				   				
		     }		
		 }
		
		// -----------------------------------------------------------------------------------------------------------------------------------------------------
						
		// TABLE COLUMNS AUTO SIZE
		utilSheet.columnsWidthAuto(sheet, columns.size());
				
		}
		
		// -----------------------------------------------------------------------------------------------------------------------------------------------------
		
	} else if(isMultiSheet && (period[1].toUpperCase().equals("MINUTE") || period[1].toUpperCase().equals("HOUR")) 
				|| isEquipNameSheet && (period[1].toUpperCase().equals("MINUTE") || period[1].toUpperCase().equals("HOUR"))){		
		
		DateTimeApplication dt = new DateTimeApplication();
				
		int interval = 0;		
		int selectOption = 0;
				
		try {
				
			daysCount = (int) dt.diferencaDias(startDate, endDate) + 1; // DIFERENÇA DIAS
			//interval = dt.defineInterval(period); // INTERVALO EM RELAÇÃO AOS PERIODO SELECIONADO
											
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		String[] dates = null;
		String[] sheetNames = null;
		
		try {
				
			if(isEquipNameSheet) {
				    
					daysCount = (int) dt.diferencaDias(startDate, endDate) + 1; // DIFERENÇA DIAS
					interval = dt.defineInterval(period) * daysCount; // INTERVALO EM RELAÇÃO AOS PERIODO SELECIONADO
				    
					dates = new String[2];
					sheetNames = new String[equips.size()];
					
					dates[0] = startDate;
					dates[1] = endDate;
					
					//EquipmentsDAO dao = new EquipmentsDAO();
					
					sheetNames = dao.equipmentsName(module, equips);				
					selectOption = equips.size();
								
				} else {
					
					daysCount = (int) dt.diferencaDias(startDate, endDate) + 1; // DIFERENÇA DIAS
					interval = dt.defineInterval(period); // INTERVALO EM RELAÇÃO AOS PERIODO SELECIONADO
				
					dates = new String[daysCount];
					sheetNames = new String[daysCount];
					
					dates = dt.dateRangeForHeader(startDate, endDate, daysCount);
					sheetNames = dt.dateRangeForSheetName(startDate, endDate, daysCount);
					
					selectOption = daysCount;
			
		  }				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
					
		// SHEETNAME BY DATE SELECTION										
				
	    for(int op = 0; op < selectOption; op++) {	    	
	 					  
				  if(isEquipNameSheet && division) {

					if(module.equals("sat")) {
												
						if(!classSubHeader.equals("light-heavy") && !classSubHeader.equals("light-heavy-bus")) {
							    
								tableStartRow = 12;
							    dataStartRow = 13;
							    
							} else {
								
								 subHeaderRow = 12;
								 tableStartRow = 13;
								 dataStartRow = 14;				 
							}							   			   		
						
					    }else { tableStartRow = 11; dataStartRow = 12; }
						
						// --------------------------------------------------																	
			    	
				  }
				  
	    	
	    	// SheetName
			sheet = workbook.createSheet(sheetNames[op]); // CREATE SHEET NAMES
		
			if(isEquipNameSheet) 
				excelFileHeader(sheet, row, RoadConcessionaire.externalImagePath, module, columns.size(), fileTitle,  
						dates, period, equips.get(op), op, isDirectionsOnSheet);
			
			
				else excelFileHeader(sheet, row, RoadConcessionaire.externalImagePath, module, columns.size(), fileTitle,  
						dates, period, equips, op, isMultiSheet, isDirectionsOnSheet);
										  	
		// -----------------------------------------------------
								
				if(isTotal)
					dataEndRow = dataStartRow + interval;
				
				else dataEndRow = dataStartRow + interval - 1;				
					
		
		// -----------------------------------------------------
		
		if(module.contentEquals("sat")) {
			
			if(classSubHeader.equals("light-heavy")) {	
				
				utilSheet.createRow(sheet, row, subHeaderRow);
				utilSheet.createCells(sheet, row, startCol, endCol, subHeaderRow, subHeaderRow);
										
				utilSheet.createCell(sheet, row,subHeaderRow, 2);
				utilSheet.setCellValue(sheet, row, subHeaderRow, 2, localeExcel.getStringKey("excel_sheet_light_vehicles_column"));
				utilSheet.setCellsStyle(sheet, row, subHeaderClassStyle, 2, 5, subHeaderRow, subHeaderRow);
				
				utilSheet.createCell(sheet, row, subHeaderRow, 6);
				utilSheet.setCellValue(sheet, row, subHeaderRow, 6, localeExcel.getStringKey("excel_sheet_heavy_vehicles_column"));
				utilSheet.setCellsStyle(sheet, row, subHeaderClassStyle, 6, endCol-1, subHeaderRow, subHeaderRow);
				
				utilSheet.mergeCells(sheet, "C13:F13");	
				utilSheet.mergeCells(sheet, "G13:O13");
				
		  }
			
		    // -----------------------------------------------------
			
			else if(classSubHeader.equals("light-heavy-bus")) {
				
				utilSheet.createRow(sheet, row, subHeaderRow);
				utilSheet.createCells(sheet, row, startCol, endCol, subHeaderRow, subHeaderRow);
				
				utilSheet.createCell(sheet, row,subHeaderRow, 2);
				utilSheet.setCellValue(sheet, row, subHeaderRow, 2, localeExcel.getStringKey("excel_sheet_light_vehicles_column"));
				utilSheet.setCellsStyle(sheet, row, subHeaderClassStyle, 2, 5, subHeaderRow, subHeaderRow);
				
				utilSheet.createCell(sheet, row, subHeaderRow, 6);
				utilSheet.setCellValue(sheet, row, subHeaderRow, 6, localeExcel.getStringKey("excel_sheet_truck_vehicles_column"));
				utilSheet.setCellsStyle(sheet, row, subHeaderClassStyle, 6, endCol-1, subHeaderRow, subHeaderRow);
				
				utilSheet.createCell(sheet, row, subHeaderRow, 15);
				utilSheet.setCellValue(sheet, row, subHeaderRow, 15, localeExcel.getStringKey("excel_sheet_bus_column"));
				utilSheet.setCellsStyle(sheet, row, subHeaderClassStyle, 15, endCol-1, subHeaderRow, subHeaderRow);
								
				utilSheet.mergeCells(sheet, "C13:F13");	
				utilSheet.mergeCells(sheet, "G13:O13");	
				utilSheet.mergeCells(sheet, "P13:T13");	
				
			}			      	
     	}
		
		// -----------------------------------------------------
		
		utilSheet.createRow(sheet, row, tableStartRow);
		utilSheet.createCells(sheet, row, startCol, endCol, tableStartRow, tableStartRow);
		utilSheet.setHeaderCellsValue(sheet, row, tableStartRow, columns);		
		utilSheet.setCellsStyle(sheet, row, tableHeadStyle, startCol, endCol, tableStartRow, tableStartRow);
													
		// CRIAR LINHAS PARA APRESENTAÇÃO DOS DADOS
		utilSheet.createRows(sheet, row, dataStartRow, dataEndRow);
		utilSheet.createCells(sheet, row, startCol, endCol, dataStartRow, dataEndRow);	
							
			if(isEquipNameSheet && isDirectionsOnSheet || isEquipNameSheet) {	
				
				String[] dirs = null;	
				int dirCol = 0;
				String lane = "", laneValue = "";
				
				if(isDirectionsOnSheet) {
				
					lanesLista = dao.listarFaixas();
					
					dirCol = endCol + 1;			
					utilSheet.createCells(sheet, row, dirCol, dirCol, dataStartRow-1, dataEndRow);
					
					// ----------------------------------------------------------
					
					if(filterLane.equals("")) {
						
						lane = dao.firstLane(equips.get(op));
						dirs = getFiltersDirection(directions);						
						laneValue = getLane(lane, dirs);
						
					}
					
					else {
						
						  laneValue = dao.getLaneDir(equips.get(op), filterLane);
					      dirs = new String[1];
					      dirs[0] = laneValue;
					      singleDirectionFilter = true;
					
						 }
										
					// ----------------------------------------------------------	
										
					utilSheet.setCellValue(sheet, row, dataStartRow-1, dirCol, laneValue);
					
					utilSheet.setCellsStyle(sheet, row, centerStyle, dirCol, dirCol, dataStartRow-1, dataEndRow); // DIR COL STYLE
					
				}										
								
				utilSheet.fileBodyMultiDirs(sheet, row, columns, null, lines, dirs, equips, lanesLista, op, daysCount, interval, startCol, endCol, dirCol, dataStartRow, dataEndRow, true, isDirectionsOnSheet, singleDirectionFilter);
				
			} else if(!isEquipNameSheet && isDirectionsOnSheet) {
				
				String[] dirs = null;	
				int dirCol = 0;
				String lane = "", laneValue = "";
				
				lanesLista = dao.listarFaixas();
				
				// --------------------------------
				
				if(filterLane.equals("")) {
					
					lane = dao.firstLane(equips.get(op));
					dirs = getFiltersDirection(directions);						
					laneValue = getLane(lane, dirs);
					
				}
				
				else {
					
					  laneValue = dao.getLaneDir(equips.get(op), filterLane);
				      dirs = new String[1];
				      dirs[0] = laneValue;
				      singleDirectionFilter = true;
				
					 }
						
				// ----------------------------------------------------------
								
				dirCol = endCol + 1;			
				utilSheet.createCells(sheet, row, dirCol, dirCol, dataStartRow-1, dataEndRow);
							
				utilSheet.setCellValue(sheet, row, dataStartRow-1, dirCol, laneValue);
				
				// -----------------------------------------------------	
				
				utilSheet.fileBodyMultiDirection(sheet, row, columns, null, lines, dirs, equips, lanesLista, startCol, endCol, dirCol, dataStartRow, true, op, interval);	
				
				utilSheet.setCellsStyle(sheet, row, centerStyle, dirCol, dirCol, dataStartRow-1, dataEndRow); // DIR COL STYLE							
				
			} else utilSheet.fileBodyMulti(sheet, row, columns, lines, startCol, endCol, dataStartRow, op, interval); 
				
				utilSheet.setCellsStyle(sheet, row, standardStyle, startCol, endCol, dataStartRow, dataEndRow);
		
		if(isTotal) {
			
			int startColumn = createTotalRow(sheet, row, tableHeadStyle, dataEndRow, endCol, lines, isEquipNameSheet);
			
            switch (totalType) {
						
			case "average": totalAverage(sheet, row, standardStyle, lines, endCol, startColumn, dataStartRow, dataEndRow); break;
					
			default:  totalSum(workbook, sheet, row, standardStyle, lines, totalType, endCol, startColumn, dataStartRow, dataEndRow); 
				break;
			}						
		}		   		
		
		// ---------------------------------------------------------------------------------------------------
						
	if(secondRows != null) {
		
		for(Pair<String, List<String[]>> p : secondRows) {
						
			dataEndRow = dataEndRow + 3;
			
			// CREATE ROW
			utilSheet.createRow(sheet, row, dataEndRow);
			
			if(module.equals("sat")) {
				
				if(!isDirectionsOnSheet) {
					
					// SENTIDO LABEL
					utilSheet.createCell(sheet, row, dataEndRow, columns.size() - 2);
					utilSheet.setCellValue(sheet, row, dataEndRow, columns.size() - 2, localeExcel.getStringKey("excel_sheet_header_direction"));
					utilSheet.setCellStyle(sheet, row, centerBoldStyle, dataEndRow, columns.size() - 2);
		
					// SENTIDO
					utilSheet.createCell(sheet, row, dataEndRow, columns.size() - 1);
					utilSheet.setCellValue(sheet, row, dataEndRow, columns.size() - 1, tm.direction(p.left));
					utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, dataEndRow, columns.size() - 1);
			
				}
				
			}
			
			// TABLE HEADER
			if(classSubHeader.equals("light-heavy") || classSubHeader.equals("light-heavy-bus")) {
			    tableStartRow = dataEndRow + 3;	
			    subHeaderRow = tableStartRow - 1;
		
			}else  tableStartRow = dataEndRow + 2;	
									
			   dataStartRow = tableStartRow + 1;	
			
			// -----------------------------------------------------
								
			if(isEquipNameSheet) {
								
				if(isTotal)
					dataEndRow = dataStartRow + interval;
				
				else dataEndRow = dataStartRow + interval - 1;		
				
				
			} else {
			
				if(isTotal)
					dataEndRow = dataStartRow + p.right.size(); 
					
				else dataEndRow = dataStartRow + p.right.size() - 1;
			
			}
			
			// -----------------------------------------------------				
													
							
			if(module.equals("sat")) {
												
				if(classSubHeader.equals("light-heavy")) {	
					
				utilSheet.createRow(sheet, row, subHeaderRow);
				utilSheet.createCells(sheet, row, startCol, endCol, subHeaderRow, subHeaderRow);
										
				utilSheet.setCellValue(sheet, row, subHeaderRow, 2, localeExcel.getStringKey("excel_sheet_light_vehicles_column"));
				utilSheet.setCellsStyle(sheet, row, subHeaderClassStyle, 2, 5, subHeaderRow, subHeaderRow);
													
				utilSheet.setCellValue(sheet, row, subHeaderRow, 6, localeExcel.getStringKey("excel_sheet_heavy_vehicles_column"));
				utilSheet.setCellsStyle(sheet, row, subHeaderClassStyle, 6, endCol - 1, subHeaderRow, subHeaderRow);
							
				utilSheet.mergeCells(sheet, "C".concat(""+(subHeaderRow + 1)).concat(":").concat("F").concat(""+(subHeaderRow + 1)));	
				utilSheet.mergeCells(sheet, "G".concat(""+(subHeaderRow + 1)).concat(":").concat("O").concat(""+(subHeaderRow + 1)));
								
			  }
				
				// -----------------------------------------------------
				
				else if(classSubHeader.equals("light-heavy-bus")) {
					
					utilSheet.createRow(sheet, row, subHeaderRow);
					utilSheet.createCells(sheet, row, startCol, endCol, subHeaderRow, subHeaderRow);
					
					utilSheet.createCell(sheet, row,subHeaderRow, 2);
					utilSheet.setCellValue(sheet, row, subHeaderRow, 2, localeExcel.getStringKey("excel_sheet_light_vehicles_column"));
					utilSheet.setCellsStyle(sheet, row, subHeaderClassStyle, 2, 5, subHeaderRow, subHeaderRow);
					
					utilSheet.createCell(sheet, row, subHeaderRow, 6);
					utilSheet.setCellValue(sheet, row, subHeaderRow, 6, localeExcel.getStringKey("excel_sheet_truck_vehicles_column"));
					utilSheet.setCellsStyle(sheet, row, subHeaderClassStyle, 6, endCol-1, subHeaderRow, subHeaderRow);
					
					utilSheet.createCell(sheet, row, subHeaderRow, 15);
					utilSheet.setCellValue(sheet, row, subHeaderRow, 15, localeExcel.getStringKey("excel_sheet_bus_column"));
					utilSheet.setCellsStyle(sheet, row, subHeaderClassStyle, 15, endCol-1, subHeaderRow, subHeaderRow);
									
					utilSheet.mergeCells(sheet, "C".concat(""+(subHeaderRow + 1)).concat(":").concat("F").concat(""+(subHeaderRow + 1)));
					utilSheet.mergeCells(sheet, "G".concat(""+(subHeaderRow + 1)).concat(":").concat("O").concat(""+(subHeaderRow + 1)));		
					utilSheet.mergeCells(sheet, "P".concat(""+(subHeaderRow + 1)).concat(":").concat("T").concat(""+(subHeaderRow + 1)));												
															
				}
			
		      	// -----------------------------------------------------
			
	     	}
			   	
				// CABEÇALHO DA TABELA		
				utilSheet.createRow(sheet, row, tableStartRow);
				utilSheet.createCells(sheet, row, startCol, endCol, tableStartRow, tableStartRow);
				utilSheet.setHeaderCellsValue(sheet, row, tableStartRow, columns);		
				utilSheet.setCellsStyle(sheet, row, tableHeadStyle, startCol, endCol, tableStartRow, tableStartRow);
				
				// CRIAR LINHAS PARA APRESENTAÇÃO DOS DADOS
				utilSheet.createRows(sheet, row, dataStartRow, dataEndRow);
				utilSheet.createCells(sheet, row, startCol, endCol, dataStartRow, dataEndRow);
				
				if(isEquipNameSheet && isDirectionsOnSheet || isEquipNameSheet){
					
						String[] dirs = null;	
						int dirCol = 0;
					
					if(isDirectionsOnSheet) { 
						
						dirCol = endCol + 1;			
						utilSheet.createCells(sheet, row, dirCol, dirCol, dataStartRow-1, dataEndRow);
						
						// -----------------------------------------------------
						
						String laneValue = "";
								
						if(p.left.equals("N"))
							laneValue = "N";
						
						else if(p.left.equals("S"))
							laneValue = "S";
							
						else if(p.left.equals("L"))
							laneValue = "L";
							
						else if(p.left.equals("O"))
							laneValue = "O";
						
						// -----------------------------------------------------
																	
						utilSheet.setCellValue(sheet, row, dataStartRow-1, dirCol, laneValue);
						
						utilSheet.setCellsStyle(sheet, row, centerStyle, dirCol, dirCol, dataStartRow-1, dataEndRow); // DIR COL STYLE
						
					}
							
					utilSheet.fileBodyMultiDirs(sheet, row, columns, p.left, p.right, dirs, equips, lanesLista, op, daysCount, interval, startCol, endCol, dirCol, dataStartRow, dataEndRow, false, isDirectionsOnSheet, singleDirectionFilter);						
					
				}else if(!isEquipNameSheet && isDirectionsOnSheet) {
					
					String[] dirs = null;
								
					int dirCol = endCol + 1;			
					utilSheet.createCells(sheet, row, dirCol, dirCol, dataStartRow-1, dataEndRow);
					
					// -----------------------------------------------------
					
					String laneValue = "";
							
					if(p.left.equals("N"))
						laneValue = "N";
					
					else if(p.left.equals("S"))
						laneValue = "S";
						
					else if(p.left.equals("L"))
						laneValue = "L";
						
					else if(p.left.equals("O"))
						laneValue = "O";
					
					// -----------------------------------------------------
					
					utilSheet.setCellValue(sheet, row, dataStartRow-1, dirCol, laneValue);
					
					utilSheet.fileBodyMultiDirection(sheet, row, columns, p.left, p.right, dirs, equips, lanesLista, startCol, endCol, dirCol, dataStartRow, false, op, interval);	
					
					utilSheet.setCellsStyle(sheet, row, centerStyle, dirCol, dirCol, dataStartRow-1, dataEndRow); // DIR COL STYLE		
					
				} else utilSheet.fileBodyMulti(sheet, row, columns, p.right, startCol, endCol, dataStartRow, op, interval);
				
				utilSheet.setCellsStyle(sheet, row, standardStyle, startCol, endCol, dataStartRow, dataEndRow);
				
				if(isTotal) {
					
					int startColumn = createTotalRow(sheet, row, tableHeadStyle, dataEndRow, endCol, lines, isEquipNameSheet);
					
					switch (totalType) {
											
					case "average": totalAverage(sheet, row, standardStyle, lines, endCol, startColumn, dataStartRow, dataEndRow); break;
							
					default:  totalSum(workbook, sheet, row, standardStyle, lines, totalType, endCol, startColumn, dataStartRow, dataEndRow); break;
					
					}
					
				 }				    				
		     }		
		 }
		
		// -----------------------------------------------------------------------------------------------------------------------------------------------------
						
		// TABLE COLUMNS AUTO SIZE 
		utilSheet.columnsWidthAuto(sheet, columns.size());
		
		}		
	}
					
						
	}	
	
	// ----------------------------------------------------------------------------------------------------------------		
		
	public void generateCountFlow(List<String> columns, List<String[]> lines, String sheetName, SatTableHeader info) throws Exception {

		sheet = null;		
		row = null;
		
		int dataStartRow = 3;
		int dataEndRow = 0;
		int startCol = 0;
		int endCol = columns.size() - 1;		
	
		sheet = workbook.createSheet(sheetName);	
				
		dataEndRow = ((dataStartRow + lines.size()) - 1);
		
		// ------------------------------------------------------------------------------------------------------------
		
		// MESCLAR CÉLULAS

		String[] mergeCells = new String[] {"A1:B1", "C1:I1", "C2:E2", "F2:H2", "A2:A3", "B2:B3", "I2:I3"}; // Define Merge columns
								
		for(int i = 0; i < mergeCells.length; i++)
			utilSheet.mergeCells(sheet, mergeCells[i]);
		
		// ------------------------------------------------------------------------------------------------------------
		
		    // SPECIFIC WIDTH COLUMNS 
		
			utilSheet.columnWidth(sheet, 0, 4000);
			utilSheet.columnWidth(sheet, 1, 4000);
			utilSheet.columnWidth(sheet, 8, 4000);				
		
		// ------------------------------------------------------------------------------------------------------------
				
		// FIRST LEVEL COLUMNS
		
		utilSheet.createRow(sheet, row, 0);
		utilSheet.createCells(sheet, row, 0, 8, 0, 0);
		utilSheet.setCellValue(sheet, row, 0, 0, info.getEquipDescription());
		utilSheet.setCellValue(sheet, row, 0, 2, localeExcel.getStringKey("excel_sheet_table_flow_counting"));
		utilSheet.setHeight(sheet, 0, 700);
		
		utilSheet.setCellsStyle(sheet, row, bgColorHeaderStyle, 0, 8, 0, 0);
						
		// ------------------------------------------------------------------------------------------------------------
		
		// SECOND LEVEL COLUMNS 1

		utilSheet.createRow(sheet, row, 1);
		utilSheet.createCells(sheet, row, 0, 8, 1, 1);
		utilSheet.setCellValue(sheet, row, 1, 0, columns.get(0));
		utilSheet.setCellValue(sheet, row, 1, 1, columns.get(1));
		utilSheet.setCellValue(sheet, row, 1, 2, info.getDirection1());
		utilSheet.setCellValue(sheet, row, 1, 5, info.getDirection2());
		utilSheet.setCellValue(sheet, row, 1, 8, columns.get(8));

		utilSheet.setCellsStyle(sheet, row, bgColorHeaderStyle, 0, 8, 1, 1);
		
		// ------------------------------------------------------------------------------------------------------------

		// SECOND LEVEL COLUMNS 2
		
		utilSheet.createRow(sheet, row, 2);
		utilSheet.createCells(sheet, row, 0, 8, 2, 2);
		utilSheet.setCellValue(sheet, row, 2, 2, columns.get(2));
		utilSheet.setCellValue(sheet, row, 2, 3, columns.get(3));
		utilSheet.setCellValue(sheet, row, 2, 4, columns.get(4));
		utilSheet.setCellValue(sheet, row, 2, 5, columns.get(5));
		utilSheet.setCellValue(sheet, row, 2, 6, columns.get(6));
		utilSheet.setCellValue(sheet, row, 2, 7, columns.get(7));
							
		utilSheet.setCellsStyle(sheet, row, bgColorHeaderStyle, 0, 8, 2, 2);
		utilSheet.setCellsStyle(sheet, row, bgColorSubHeaderStyle1, 2, 4, 2, 2);
		utilSheet.setCellsStyle(sheet, row, bgColorSubHeaderStyle2, 5, 7, 2, 2);	
		
		utilSheet.columnsWidthAuto(sheet, columns.size()); // COLUMNS SIZE AUTO
			
		// ------------------------------------------------------------------------------------------------------------

		utilSheet.createRows(sheet, row, dataStartRow, dataEndRow); // CRIAR LINHAS 
		
		utilSheet.createCells(sheet, row, startCol, endCol, dataStartRow, dataEndRow); // CRIAR CÉLULAS
		
		utilSheet.fileBodySimple(sheet, row, columns, lines, startCol, endCol, dataStartRow); // PREENCHER DADOS
		
		utilSheet.setCellsStyle(sheet, row, standardStyle, startCol, startCol, dataStartRow, dataEndRow); // ESTILO		
		utilSheet.setCellsStyle(sheet, row, standardStyle, endCol, endCol, dataStartRow, dataEndRow);
		utilSheet.setCellsStyle(sheet, row, dateTimeStyle, 1, 1, dataStartRow, dataEndRow);		
		utilSheet.setCellsStyle(sheet, row, bgColorBodyStyle1, 2, 4, dataStartRow, dataEndRow);
		utilSheet.setCellsStyle(sheet, row, bgColorBodyStyle2, 5, 7, dataStartRow, dataEndRow);	
		
	}

	public void generateVehicleCountEco101(List<String> columns, List<String[]> lines, String sheetName, SatTableHeader info, String[] date, String[] period) throws Exception {

		sheet = null;		
		row = null;
		
		@SuppressWarnings("unchecked")
		List<String>[] tempLine = new ArrayList[17];
		String[] cat = new String[] {
				"CAT1",
				"CAT2",
				"CAT2A",
				"CAT3",
				"CAT4",
				"CAT4A",
				"CAT5",
				"CAT6",
				"CAT6A",
				"CAT7",
				"CAT8",
				"CAT9",
				"CAT10",
				"CAT11",
				"CATE9",
				"CATE10",
				"CAT0"
		};
		for (int i = 0; i < tempLine.length; i++) {
			List<String> t = new ArrayList<>();
			t.add(cat[i]);
			tempLine[i] = t;
		};
		List<String[]> newLine = new ArrayList<>();
		
		int dataStartRow = 14;
		int dataEndRow = 0;
		int startCol = 0;
		int endCol = columns.size() - 1;
		int len = lines.size() + 1;
	
		sheet = workbook.createSheet(sheetName);	
				
		dataEndRow = ((dataStartRow + lines.size()) - 1);
		
		excelFileHeader(sheet, row, RoadConcessionaire.externalImagePath, "sat", columns.size(), "Contagem de Veículo por Categoria",  
				date, period, new ArrayList<>(), 0, false, true);
		
		// ------------------------------------------------------------------------------------------------------------
		
		// MESCLAR CÉLULAS

		String[] mergeCells = new String[] {"A13:A14", String.format("%1$s13:%1$s14", (char)(len + 65))}; // Define Merge columns
								
		for(int i = 0; i < mergeCells.length; i++)
			utilSheet.mergeCells(sheet, mergeCells[i]);
						
		// ------------------------------------------------------------------------------------------------------------
		
		// SECOND LEVEL COLUMNS 1
		
		utilSheet.createRow(sheet, row, 12);
		utilSheet.createRow(sheet, row, 13);
		utilSheet.createCells(sheet, row, 0, len, 12, 13);
		utilSheet.setCellsStyle(sheet, row, tableHeadStyle, 0, len, 12, 13);
		utilSheet.setCellValue(sheet, row, 12, 0, "Valores");

		for (int i = 1; i < len; i += 2) {
			utilSheet.mergeCells(sheet, String.format("%s13:%s13", (char)(i + 65), (char)(i + 66)));
			String[] line1 = lines.get(i - 1);
			String[] line2 = lines.get(i);
			utilSheet.setCellValue(sheet, row, 12, i, line1[0]);
			utilSheet.setCellValue(sheet, row, 13, i, line1[1]);
			utilSheet.setCellValue(sheet, row, 13, i + 1, line2[1]);
			
			for (int n = 0; n < tempLine.length; n++) {				
				tempLine[n].add(line1[n + 2]);
				tempLine[n].add(line2[n + 2]);
			}
		}
		for (List<String> temp : tempLine) {
			int sum = 0;
			for (int s = 1; s < temp.size(); s++)
				sum += Integer.parseInt(temp.get(s));
			temp.add(String.valueOf(sum));
			newLine.add(temp.toArray(String[]::new));
		}
		utilSheet.setCellValue(sheet, row, 12, len, "Total");
			
		// ------------------------------------------------------------------------------------------------------------

		utilSheet.createRows(sheet, row, dataStartRow, dataEndRow); // CRIAR LINHAS 
		
		utilSheet.createCells(sheet, row, startCol, endCol, dataStartRow, dataEndRow); // CRIAR CÉLULAS
		
		utilSheet.fileBodySimple(sheet, row, columns, newLine, startCol, endCol, dataStartRow); // PREENCHER DADOS
		
		utilSheet.setCellsStyle(sheet, row, standardStyle, startCol, endCol, dataStartRow, dataEndRow - 1);
		
	}
	
	public void generateVehicleCountCategoryEco101(List<String> columns, List<String[]> lines, String sheetName, SatTableHeader info, String[] date, String[] period) throws Exception {
		
		sheet = null;		
		row = null;
		
		@SuppressWarnings("unchecked")
		List<String>[] tempLine = new ArrayList[17];
		String[] cat = new String[] {
				"CAT1",
				"CAT2",
				"CAT2A",
				"CAT3",
				"CAT4",
				"CAT4A",
				"CAT5",
				"CAT6",
				"CAT6A",
				"CAT7",
				"CAT8",
				"CAT9",
				"CAT10",
				"CAT11",
				"CATE9",
				"CATE10",
				"CAT0"
		};
		for (int i = 0; i < tempLine.length; i++) {
			List<String> t = new ArrayList<>();
			t.add(cat[i]);
			tempLine[i] = t;
		};
		List<String[]> newLine = new ArrayList<>();
		
		int dataStartRow = 14;
		int dataEndRow = 0;
		int startCol = 0;
		int endCol = 2;
		int len = lines.size() + 1;
		
		sheet = workbook.createSheet(sheetName);	
		
		dataEndRow = ((dataStartRow + lines.size()) - 1);
		
		excelFileHeader(sheet, row, RoadConcessionaire.externalImagePath, "sat", columns.size(), "Contagem de Veículo por Categoria",  
				date, period, new ArrayList<>(), 0, false, true);
		
		// ------------------------------------------------------------------------------------------------------------
		
		// MESCLAR CÉLULAS
		
		String[] mergeCells = new String[] {"A13:A14", String.format("%1$s13:%1$s14", (char)(len + 65))}; // Define Merge columns
		
		for(int i = 0; i < mergeCells.length; i++)
			utilSheet.mergeCells(sheet, mergeCells[i]);
		
		// ------------------------------------------------------------------------------------------------------------
		
		// SECOND LEVEL COLUMNS 1
		
		utilSheet.createRow(sheet, row, 12);
		utilSheet.createRow(sheet, row, 13);
		utilSheet.createCells(sheet, row, 0, len, 12, 13);
		utilSheet.setCellsStyle(sheet, row, tableHeadStyle, 0, len, 12, 13);
		utilSheet.setCellValue(sheet, row, 12, 0, "Valores");
		
		for (int i = 1; i < len; i += 2) {
			utilSheet.mergeCells(sheet, String.format("%s13:%s13", (char)(i + 65), (char)(i + 66)));
			String[] line1 = lines.get(i - 1);
			String[] line2 = lines.get(i);
			utilSheet.setCellValue(sheet, row, 12, i, line1[0]);
			utilSheet.setCellValue(sheet, row, 13, i, line1[1]);
			utilSheet.setCellValue(sheet, row, 13, i + 1, line2[1]);
			
			endCol += 2;
			
			for (int n = 0; n < tempLine.length; n++) {
				tempLine[n].add(line1[n + 2]);
				tempLine[n].add(line2[n + 2]);
			}
		}
		for (List<String> temp : tempLine) {
			int sum = 0;
			for (int s = 1; s < temp.size(); s++)
				sum += Integer.parseInt(temp.get(s));
			temp.add(String.valueOf(sum));
			newLine.add(temp.toArray(new String[temp.size()]));
		}
		utilSheet.setCellValue(sheet, row, 12, len, "Total");
		
		// ------------------------------------------------------------------------------------------------------------
		
		utilSheet.createRows(sheet, row, dataStartRow, dataEndRow); // CRIAR LINHAS 
		
		utilSheet.createCells(sheet, row, startCol, endCol, dataStartRow, dataEndRow); // CRIAR CÉLULAS
		
		utilSheet.fileBodySimple(sheet, row, newLine, startCol, endCol, dataStartRow); // PREENCHER DADOS
		
		utilSheet.setCellsStyle(sheet, row, standardStyle, startCol, endCol - 1, dataStartRow, dataEndRow - 1);
		
	}
	
	// ----------------------------------------------------------------------------------------------------------------
	
	public List<Equipments> genericInfo(List<String> equipId, String module) { 
		
		List<Equipments> info = new ArrayList<Equipments>();
		 dao = new EquipmentsDAO();
		
		try {
						 			 
			 for(int s = 0; s < equipId.size(); s++)	        	
			    info = dao.equipReportInfo(equipId.get(s), module);
			 								
			} catch (Exception e) {			
				e.printStackTrace();
			}
		 
		 return info;
				
	  }
	
  // ----------------------------------------------------------------------------------------------------------------
		
	public List<Equipments> genericInfo(String equipId, String module) { 
		
		List<Equipments> info = new ArrayList<Equipments>();
		 dao = new EquipmentsDAO();
		
		try {	        	
			    info = dao.equipReportInfo(equipId, module);
			 								
			} catch (Exception e) {			
				e.printStackTrace();
			}
		 
		 return info;
				
	  }
	
  // ----------------------------------------------------------------------------------------------------------------
	
	public List<SAT> SATInfo(List<String> equipId) { 
		
		List<SAT> info = new ArrayList<SAT>();
		 dao = new EquipmentsDAO();
		
		try {
						 			 
			 for(int s = 0; s < equipId.size(); s++)	        	
			    info = dao.SATReportInfo(equipId.get(s));
			 								
			} catch (Exception e) {			
				e.printStackTrace();
			}
		 
		 return info;
				
	  }
	
  // ----------------------------------------------------------------------------------------------------------------

	public List<SAT> SATInfo(String equipId) { 
	
	List<SAT> info = new ArrayList<SAT>();
	 dao = new EquipmentsDAO();
	
	try {							 
	       	
		   info = dao.SATReportInfo(equipId);
		 								
		} catch (Exception e) {			
			e.printStackTrace();
		}
	 
	 return info;
			
  }

	// ----------------------------------------------------------------------------------------------------------------
	
	public List<Equipments> defaultGenericInfo(){
		
		List<Equipments> lista = new ArrayList<Equipments>();
		
		Equipments equip = new Equipments();
		
		equip.setNome(" --- ");		
		equip.setCidade(" --- ");
		equip.setEstrada(" --- ");
		equip.setKm(" --- ");
		
		lista.add(equip);
		
		return lista;
		
	}
	
	 // ----------------------------------------------------------------------------------------------------------------
	
	 public List<SAT> defaultSATInfo(){
		
		List<SAT> lista = new ArrayList<SAT>();
		
		SAT sat = new SAT();
		
		sat.setNome(" --- ");		
		sat.setCidade(" --- ");
		sat.setEstrada(" --- ");
		sat.setKm(" --- ");
		sat.setQtdeFaixas(" --- ");
		sat.setSentidos(" --- ");
				
		lista.add(sat);
		
		return lista;
		
	}
	
	 // ----------------------------------------------------------------------------------------------------------------
				
		public String getLane(String lane, String[] dirs) {
						
			String auxLane = "";
			
			boolean north = false, south = false, east = false, west = false;
																							
				for(int i = 0; i < dirs.length; i++) {																	
															
					 if(dirs[i] != null) {						 
							
							if(lane.equals("N")) {
								
								  if(dirs[i].equals("N")) {									
									  auxLane = "N";	
									  north = true;
								  }
								  
								  else if(north && dirs[i].equals("S"))
								 		auxLane = "N / S";
								  
								  else if(!north && dirs[i].equals("S"))
								 		auxLane = "S";								 
								 								  
								}
							
							// --------------------------------------------------
							
							if(lane.equals("S")) {
								
								  if(dirs[i].equals("N")) {									
									  auxLane = "N";	
									  south = true;
								  }
								  
								  else if(south && dirs[i].equals("S"))
								 		auxLane = "S / N";
								  
								  else if(!south && dirs[i].equals("S"))
								 		auxLane = "S";										
								 								  
								}
							
							// --------------------------------------------------
							
							if(lane.equals("L")) {
								
								  if(dirs[i].equals("L")) {									
									  auxLane = "L";	
									  east = true;
								  }
								  
								  else if(east && dirs[i].equals("O"))
								 		auxLane = "L / O";
								  
								  else if(!east && dirs[i].equals("O"))
								 		auxLane = "O";										
								 								  
								}
							
							// --------------------------------------------------
							
							if(lane.equals("O")) {
								
								  if(dirs[i].equals("L")) {									
									  auxLane = "L";	
									  west = true;
								  }
								  
								  else if(west && dirs[i].equals("O"))
								 		auxLane = "O / L";
								  
								  else if(!west && dirs[i].equals("O"))
								 		auxLane = "O";																						
								 								  
								}
							
							// --------------------------------------------------
										
							}										   
					    }							  
										
				// ---------------------																														
													
					return auxLane;
			}
				
	// ----------------------------------------------------------------------------------------------------------------	
	
	public String[] getFiltersDirection(List<String> dirs) {
		
		String[] dir = new String[4];
	
		for(int i = 0; i < dirs.size(); i++) {
			  
			  if(dirs.get(i).equals("N"))
				  dir[i] = "N";
		
			  else if(dirs.get(i).equals("S"))
				  dir[i] = "S";
			  
			  else if(dirs.get(i).equals("L"))
				  dir[i] = "L";
				  
			  else if(dirs.get(i).equals("O"))
				  dir[i] = "O";
		
	    	}
				
		return dir;
		
	}
	
	// ----------------------------------------------------------------------------------------------------------------
	
	
}
