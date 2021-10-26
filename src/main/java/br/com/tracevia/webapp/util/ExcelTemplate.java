package br.com.tracevia.webapp.util;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.ReportBuild;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;

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

	private static String FONT_ARIAL = "Arial";

	LocaleUtil localeExcel;

	// DEFAULT FONTS

	// standardFont - fonte padrão para os relátorios
	// boldFont - fonte em negrito
	// titleFont - fonte para títulos dos relatórios
	// tableHeaderFont - fonte para cabeçalho das tabelas

	Font standardFont, boldFont, titleFont, tableHeadFont;

	// COUNT FLOW FONTS	

	// countFlowFont - fonte para uso no header e subheaders
	// countFlowFontBody1 - fonte para a primeira parte do corpo do relatório
	// countFlowFontBody2 - fonte para a segunda parte do corpo do relatório

	Font countFlowFont, countFlowFontBody1, countFlowFontBody2;

	// ---------------------------------------------------------------------------------------------------------------

	// DEFAULT STYLES 

	// titleStyle - estilo para o título principal do relatório
	// standardStyle - estilo padrão para uso no corpo do relatório
	// tableHeaderStyle - estilo para uso no cabeçalho da tabela
	// leftAlignStandardStyle - estilo para alinhamento a esquerda (padrão)
	// rightAlignBoldStyle - estilo em negrito com alinhamento a direita
	// dateTitleStyle - estilo para datas ao lado do título principal
	// dateTimeStyle - estilo para data e hora no corpo do relatório
	// centerBoldStyle - estilo em negrito para centralizar
	// centerAlignStandardStyle - estilo padrão para centralizar	

	CellStyle titleStyle, standardStyle, tableHeadStyle, leftAlignStandardStyle, rightAlignBoldStyle, dateTitleStyle,   
	dateTimeStyle, centerBoldStyle, centerAlignStandardStyle;   

	// COUNT FLOW STYLES 

	// bgColorHeaderStyle - estilo do cabeçalho principal
	// bgColorSubHeaderStyle1 - estilo do primeiro subheader
	// bgColorSubHeaderStyle2 - estilo do segundo subheader
	// bgColorBodyStyle1 - estilo da primeira parte do corpo do relatório
	// bgColorBodyStyle2 - estilo da segunda parte do corpo do relatório

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
	    titleFont = utilSheet.createFont(workbook,  FONT_ARIAL, 16, true, false, IndexedColors.BLACK); // FONTE PARA O TÍTULO
	    tableHeadFont = utilSheet.createFont(workbook, FONT_ARIAL, 10, true, false, IndexedColors.WHITE); // FONT PARA CABEÇALHO DA TABELA
	    
		// COUNT FLOW FONTS
	    
	    countFlowFont = utilSheet.createFont(workbook, FONT_ARIAL, 11, true, false, IndexedColors.WHITE); // FONTE PADRÃO COUNT FLOW
	    countFlowFontBody1 = utilSheet.createFont(workbook, FONT_ARIAL, 10, false, false, IndexedColors.ORANGE); // FONTE PARA APRESENTAÇÃO DE DADOS 1
	    countFlowFontBody2 = utilSheet.createFont(workbook, FONT_ARIAL, 10, false, false, IndexedColors.GREEN); // FONTE PARA APRESENTAÇÃO DE DADOS 2	       

		// ----------------------------------------------------------------------------------------------------------------

		// DEFAULT STYLES	
	    
	    // ESTILO PADRÃO
	    standardStyle = utilSheet.createCellStyle(workbook, standardFont, HorizontalAlignment.CENTER, IndexedColors.WHITE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	    // ESTILO PARA TÍTULO
	    titleStyle = utilSheet.createCellStyle(workbook, titleFont, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, true, IndexedColors.WHITE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	    // ESTILO PARA CABEÇALHO DAS TABELAS
	    tableHeadStyle = utilSheet.createCellStyle(workbook, tableHeadFont, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, true, IndexedColors.LIGHT_BLUE, FillPatternType.SOLID_FOREGROUND,ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
		// ESTILO PARA ALINHAMENTO A ESQUERDA FONTE PADRÃO
	    leftAlignStandardStyle = utilSheet.createCellStyle(workbook, standardFont, HorizontalAlignment.LEFT, IndexedColors.WHITE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	    // ESTILO PARA CABEÇALHO ENTRE DATAS 
	    dateTitleStyle = utilSheet.createCellStyle(workbook, standardFont, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, true, IndexedColors.WHITE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	    // ESTILO PARA CAMPO DE DATAS EM NEGRITO
	    dateTimeStyle = utilSheet.createCellStyle(workbook, boldFont, HorizontalAlignment.CENTER, IndexedColors.WHITE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
        // ESTILO NEGRITO CENTRALIZADO   
	    centerBoldStyle = utilSheet.createCellStyle(workbook, boldFont, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, IndexedColors.WHITE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	    // ESTILO PADRÃO DE ALINHAMENTO CENTRAL	    
	    centerAlignStandardStyle = utilSheet.createCellStyle(workbook, standardFont, HorizontalAlignment.CENTER, IndexedColors.WHITE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);

		// COUNT FLOW STYLES

	    // ESTILO HEADER
	    bgColorHeaderStyle = utilSheet.createCellStyle(workbook, countFlowFont, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, true, IndexedColors.BLUE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	    // ESTILO SUB HEADER 1
	    bgColorSubHeaderStyle1 = utilSheet.createCellStyle(workbook, countFlowFont, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, true, IndexedColors.LIGHT_BLUE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	    // ESTILO SUB HEADER 2
	    bgColorSubHeaderStyle2 = utilSheet.createCellStyle(workbook, countFlowFont, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, true, IndexedColors.LIGHT_ORANGE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
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

	public void spreadSheetHeader(XSSFWorkbook workbook, XSSFSheet sheet, XSSFRow row, String pathLogo, int columns, String fileTitle, 
			String startDate, String endDate, String period, String[] equipInfo, boolean isSat) {

		DateTimeApplication dta = new DateTimeApplication();
		TranslationMethods tm = new TranslationMethods();

		// INDEX DA COLUNA DE ÍNICIO DA DATA
		int columnStartDateIndex = columns - 2;
		int columnEndDateIndex = columns;
		int columnTitleEndIndex = columnStartDateIndex - 1;

		// ----------------------------------------------------------------------------------------------------------------

		// HEADER

		// CRIAR COLUNAS E LINHAS DO HEADER
		utilSheet.createRows(sheet, row, 0, 4);
		utilSheet.createCells(sheet, row, 0, columns, 0, 3);

		// DEFINIR IMAGEM
		utilSheet.createImage(workbook, sheet, pathLogo, 0, 0, 2, 4, 1, 1, 1, 1, 1); 

		// DEFINIR O TÍTULO	DO HEADER					
		utilSheet.setCellValue(sheet, row, 0, 2, fileTitle);
		utilSheet. setCellsStyle(sheet, row, titleStyle, 0, 3, 0, columnTitleEndIndex); // ESTILO TITULO

		// HEADER DATE TEMPLATE
		String headerDates = localeExcel.getStringKey("excel_sheet_header_date_from")+": " + startDate+ "\n"+localeExcel.getStringKey("excel_sheet_header_date_to")+": " + endDate;

		// INSERIR O TEMPLATE
		utilSheet.setCellValue(sheet, row, 0, columnStartDateIndex, headerDates);	
		//utilSheet.setCellsStyle(sheet, row, dateTitleStyle, 0, 3, columnStartDateIndex, columnEndDateIndex); // ESTILO DATAS
		
		// HEADER COLUMNS DINAMIC MERGE
		utilSheet.mergeBetweenColumns(sheet, 0, 1, 1, 4);
		utilSheet.mergeBetweenColumns(sheet, 2, columns, 1, 4);
		utilSheet.mergeBetweenColumns(sheet, columns + 1, columns + 3, 1, 4);
		
		// ----------------------------------------------------------------------------------------------------------------	    
		// SUBHEADER 
		// ----------------------------------------------------------------------------------------------------------------

		// MERGE CELLS
		utilSheet.mergeCells(sheet, "A6:B6");	
		utilSheet.mergeCells(sheet, "F6:H6");
		utilSheet.mergeCells(sheet, "I6:J6");
		
		// ----------------------------------------------------------------------------------------------------------------				
				
		// CRIAR LINHA 1 - SUBHEADER
		utilSheet.createRow(sheet, row, 5);

		// EQUIPAMENTO LABEL
		utilSheet.createCell(sheet, row, 5, 0);
		utilSheet.setCellValue(sheet, row, 5, 0, localeExcel.getStringKey("excel_sheet_header_equipment"));
		utilSheet.setCellStyle(sheet, row, rightAlignBoldStyle, 5, 0); 

		// NOME DO EQUIPAMENTO
		utilSheet.createCell(sheet, row, 5, 2);
		utilSheet.setCellValue(sheet, row, 5, 2, equipInfo[0]);
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
		utilSheet.setCellValue(sheet, row, 6, 2, equipInfo[1]);
		utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 6, 2);

		// SENTIDO LABEL
		utilSheet.createCell(sheet, row, 6, 7);
		utilSheet.setCellValue(sheet, row, 6, 7, localeExcel.getStringKey("excel_sheet_header_direction"));
		utilSheet.setCellStyle(sheet, row, centerBoldStyle, 6, 7);

		// SENTIDO
		utilSheet.createCell(sheet, row, 6, 8);
		utilSheet.setCellValue(sheet, row, 6, 8, localeExcel.getStringKey("excel_sheet_header_all_directions"));
		utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 6, 8);

		// ----------------------------------------------------------------------------------------------------------------

		// CRIAR LINHA 3 - SUBHEADER
		utilSheet.createRow(sheet, row, 7);

		// RODOVIA / ESTRADA LABEL
		utilSheet.createCell(sheet, row, 7, 1);
		utilSheet.setCellValue(sheet, row, 7, 1, localeExcel.getStringKey("excel_sheet_header_highway"));
		utilSheet.setCellStyle(sheet, row, centerBoldStyle, 7, 1);

		// NOME DA RODOVIA / ESTRADA
		utilSheet.createCell(sheet, row, 7, 2);
		utilSheet.setCellValue(sheet, row, 7, 2, equipInfo[2]);
		utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 7, 2);
		
		if(!period.equals("")) {

		// PERÍODO LABEL
		utilSheet.createCell(sheet, row, 7, 7);
		utilSheet.setCellValue(sheet, row, 7, 7, localeExcel.getStringKey("excel_sheet_header_period"));
		utilSheet.setCellStyle(sheet, row, centerBoldStyle, 7, 7);

		// PERIODO
		utilSheet.createCell(sheet, row, 7, 8);
		utilSheet.setCellValue(sheet, row, 7, 8, tm.periodName(period));
		utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 7, 8);
		
		}

		// ----------------------------------------------------------------------------------------------------------------

		// CRIAR LINHA 4 - SUBHEADER
		utilSheet.createRow(sheet, row, 8);

		// KM LABEL
		utilSheet.createCell(sheet, row, 8, 1);
		utilSheet.setCellValue(sheet, row, 8, 1, localeExcel.getStringKey("excel_sheet_header_km"));
		utilSheet.setCellStyle(sheet, row, centerBoldStyle, 8, 1);

		// KM
		utilSheet.createCell(sheet, row, 8, 2);
		utilSheet.setCellValue(sheet, row, 8, 2, equipInfo[3]);
		utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 8, 2);

		// ----------------------------------------------------------------------------------------------------------------

		// CASO O NÚMERO DE LINHAS FOR MAIOR QUE 1 
		// ENTRA NESSA CONDIÇÃO
		// UTILIZADA ESPECIFICAMENTE PARA O SAT
		if(isSat) {

			// CRIAR LINHA 5 - SUBHEADER
			utilSheet.createRow(sheet, row, 9);

			// LANE LABELS
			utilSheet.createCell(sheet, row, 9, 1);
			utilSheet.setCellValue(sheet, row, 9, 1, localeExcel.getStringKey("excel_sheet_header_lanes"));
			utilSheet.setCellStyle(sheet, row, centerBoldStyle, 9, 1); 

			// NÚMERO DE LINHAS
			utilSheet.createCell(sheet, row, 9, 2);
			utilSheet.setCellValue(sheet, row, 9, 2, Integer.parseInt(equipInfo[4]));
			utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 9, 2);

		}
		
		// ----------------------------------------------------------------------------------------------------------------
		
		// TABLE COLUMNS LENGTH
		utilSheet.columnsWidthAuto(sheet, columns);
	}
	
	// ----------------------------------------------------------------------------------------------------------------
	
	public void spreadSheetHeader(XSSFWorkbook workbook, XSSFSheet sheet, XSSFRow row, String pathLogo, String[] columns, String fileTitle, 
			String startDate, String endDate, String period, String[][] equipInfo, boolean isSat, int posArray) {

		DateTimeApplication dta = new DateTimeApplication();
		TranslationMethods tm = new TranslationMethods();

		// INDEX DA COLUNA DE ÍNICIO DA DATA
		int columnStartDateIndex = columns.length - 2;
		int columnEndDateIndex = columns.length;
		int columnTitleEndIndex = columnStartDateIndex - 1;

		// ----------------------------------------------------------------------------------------------------------------

		// HEADER

		// CRIAR COLUNAS E LINHAS DO HEADER
		utilSheet.createRows(sheet, row, 0, 4);
		utilSheet.createCells(sheet, row, 0, columns.length, 0, 3);

		// DEFINIR IMAGEM
		utilSheet.createImage(workbook, sheet, pathLogo, 0, 0, 2, 4, 1, 1, 1, 1, 1); 

		// DEFINIR O TÍTULO	DO HEADER					
		utilSheet.setCellValue(sheet, row, 0, 2, fileTitle);
		utilSheet. setCellsStyle(sheet, row, titleStyle, 0, 3, 0, columnTitleEndIndex); // ESTILO TITULO

		// HEADER DATE TEMPLATE
		String headerDates = localeExcel.getStringKey("excel_sheet_header_date_from")+": " + startDate+ "\n"+localeExcel.getStringKey("excel_sheet_header_date_to")+": " + endDate;

		// INSERIR O TEMPLATE
		utilSheet.setCellValue(sheet, row, 0, columnStartDateIndex, headerDates);	
		utilSheet.setCellsStyle(sheet, row, dateTitleStyle, 0, 3,columnStartDateIndex, columnEndDateIndex); // ESTILO DATAS
		
		// HEADER COLUMNS DINAMIC MERGE
		utilSheet.mergeBetweenColumns(sheet, 0, 1, 1, 4);
		utilSheet.mergeBetweenColumns(sheet, 2, columns.length, 1, 4);
		utilSheet.mergeBetweenColumns(sheet, columns.length + 1, columns.length + 3, 1, 4);
		
		// ----------------------------------------------------------------------------------------------------------------	    
		// SUBHEADER 
		// ----------------------------------------------------------------------------------------------------------------

		// MERGE CELLS
		utilSheet.mergeCells(sheet, "A6:B6");	
		utilSheet.mergeCells(sheet, "F6:H6");
		utilSheet.mergeCells(sheet, "I6:J6");
		
		// ----------------------------------------------------------------------------------------------------------------				
				
		// CRIAR LINHA 1 - SUBHEADER
		utilSheet.createRow(sheet, row, 5);

		// EQUIPAMENTO LABEL
		utilSheet.createCell(sheet, row, 5, 0);
		utilSheet.setCellValue(sheet, row, 5, 0, localeExcel.getStringKey("excel_sheet_header_equipment"));
		utilSheet.setCellStyle(sheet, row, rightAlignBoldStyle, 5, 0); 

		// NOME DO EQUIPAMENTO
		utilSheet.createCell(sheet, row, 5, 2);
		utilSheet.setCellValue(sheet, row, 5, 2, equipInfo[posArray][0]);
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
		utilSheet.setCellValue(sheet, row, 6, 2, equipInfo[posArray][1]);
		utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 6, 2);

		// SENTIDO LABEL
		utilSheet.createCell(sheet, row, 6, 7);
		utilSheet.setCellValue(sheet, row, 6, 7, localeExcel.getStringKey("excel_sheet_header_direction"));
		utilSheet.setCellStyle(sheet, row, centerBoldStyle, 6, 7);

		// SENTIDO
		utilSheet.createCell(sheet, row, 6, 8);
		utilSheet.setCellValue(sheet, row, 6, 8, localeExcel.getStringKey("excel_sheet_header_all_directions"));
		utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 6, 8);

		// ----------------------------------------------------------------------------------------------------------------

		// CRIAR LINHA 3 - SUBHEADER
		utilSheet.createRow(sheet, row, 7);

		// RODOVIA / ESTRADA LABEL
		utilSheet.createCell(sheet, row, 7, 1);
		utilSheet.setCellValue(sheet, row, 7, 1, localeExcel.getStringKey("excel_sheet_header_highway"));
		utilSheet.setCellStyle(sheet, row, centerBoldStyle, 7, 1);

		// NOME DA RODOVIA / ESTRADA
		utilSheet.createCell(sheet, row, 7, 2);
		utilSheet.setCellValue(sheet, row, 7, 2, equipInfo[posArray][2]);
		utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 7, 2);
		
		if(!period.equals("")) {

		// PERÍODO LABEL
		utilSheet.createCell(sheet, row, 7, 7);
		utilSheet.setCellValue(sheet, row, 7, 7, localeExcel.getStringKey("excel_sheet_header_period"));
		utilSheet.setCellStyle(sheet, row, centerBoldStyle, 7, 7);

		// PERIODO
		utilSheet.createCell(sheet, row, 7, 8);
		utilSheet.setCellValue(sheet, row, 7, 8, tm.periodName(period));
		utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 7, 8);
		
		}

		// ----------------------------------------------------------------------------------------------------------------

		// CRIAR LINHA 4 - SUBHEADER
		utilSheet.createRow(sheet, row, 8);

		// KM LABEL
		utilSheet.createCell(sheet, row, 8, 1);
		utilSheet.setCellValue(sheet, row, 8, 1, localeExcel.getStringKey("excel_sheet_header_km"));
		utilSheet.setCellStyle(sheet, row, centerBoldStyle, 8, 1);

		// KM
		utilSheet.createCell(sheet, row, 8, 2);
		utilSheet.setCellValue(sheet, row, 8, 2, equipInfo[posArray][3]);
		utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 8, 2);

		// ----------------------------------------------------------------------------------------------------------------

		// CASO O NÚMERO DE LINHAS FOR MAIOR QUE 1 
		// ENTRA NESSA CONDIÇÃO
		// UTILIZADA ESPECIFICAMENTE PARA O SAT
		if(isSat) {

			// CRIAR LINHA 5 - SUBHEADER
			utilSheet.createRow(sheet, row, 9);

			// LANE LABELS
			utilSheet.createCell(sheet, row, 9, 1);
			utilSheet.setCellValue(sheet, row, 9, 1, localeExcel.getStringKey("excel_sheet_header_lanes"));
			utilSheet.setCellStyle(sheet, row, centerBoldStyle, 9, 1); 

			// NÚMERO DE LINHAS
			utilSheet.createCell(sheet, row, 9, 2);
			utilSheet.setCellValue(sheet, row, 9, 2, Integer.parseInt(equipInfo[posArray][4]));
			utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 9, 2);

		}
		
		// ----------------------------------------------------------------------------------------------------------------
		
		// TABLE COLUMNS LENGTH
		utilSheet.columnsWidthAuto(sheet, columns.length);
	}
	

	// ----------------------------------------------------------------------------------------------------------------
	// TOTAL
	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * Método para criar colunas com total para SOMA
	 * @author Wellington 15/10/2021
	 * @version 1.0
	 * @since 1.0  
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha
	 * @param standard - estilo padrão para colunas atribuidas a fórmula
	 * @param tableHeader - estilo apenas para a string "TOTAL"
	 * @param rowTotal - última linha a ser apresentado o total	
	 * @param multi - define se o template do total é de múltiplos equipamentos ou não
	 * @param columnsLength - número de colunas  
	 * @param rowIni - linha inicial		
	 * @param rowEnd - linha final	
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFSheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFRow.html
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html
	 * 
	 */
	public void totalSum(XSSFSheet sheet, XSSFRow row, CellStyle standard, CellStyle tableHeader, boolean isMulti, int columnsLength, int rowTotal, int rowIni, int rowEnd) {

		// CREATE ROW TOTAL
		utilSheet.createRow(sheet, row, rowTotal);		 

		utilSheet.createCells(sheet, row, 0, columnsLength, rowTotal, rowTotal);   // CREATE CELLS			
		utilSheet.setCellValue(sheet, row, rowTotal, 0, localeExcel.getStringKey("excel_sheet_table_total")); // SET FIRST CELL VALUE						
		utilSheet.setCellStyle(sheet, row, tableHeader, rowTotal, 0); // SET FIRST CELL STYLE	

		utilSheet.totalExcelSum(sheet, row, standard, rowTotal, isMulti, columnsLength, rowIni, rowEnd); // SET CELL FORMULA			

	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * Método para criar colunas com total MÉDIA
	 * @author Wellington 15/10/2021
	 * @version 1.0
	 * @since 1.0  
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha
	 * @param standard - estilo padrão para colunas atribuidas a fórmula
	 * @param tableHeader - estilo apenas para a string "TOTAL"
	 * @param rowTotal - última linha a ser apresentado o total	
	 * @param multi - define se o template do total é de múltiplos equipamentos ou não
	 * @param columnsLength - número de colunas  
	 * @param rowIni - linha inicial		
	 * @param rowEnd - linha final	
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFSheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFRow.html
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html
	 * 
	 */
	public void totalAverage(XSSFSheet sheet, XSSFRow row, CellStyle standard, CellStyle tableHeader, boolean isMulti, int columnsLength, int rowTotal, int rowIni, int rowEnd) {

		// CREATE ROW TOTAL
		utilSheet.createRow(sheet, row, rowTotal);		 

		utilSheet.createCells(sheet, row, 0, columnsLength, rowTotal, rowTotal);   // CREATE CELLS			
		utilSheet.setCellValue(sheet, row, rowTotal, 0, localeExcel.getStringKey("excel_sheet_table_total")); // SET FIRST CELL VALUE						
		utilSheet.setCellStyle(sheet, row, tableHeader, rowTotal, 0); // SET FIRST CELL STYLE	

		utilSheet.totalExcelAverage(sheet, row, standard, rowTotal, isMulti, columnsLength, rowIni, rowEnd); // SET CELL FORMULA			

	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * Método para criar colunas com total para TEMPO
	 * @author Wellington 15/10/2021
	 * @version 1.0
	 * @since 1.0  
	 * @param workbook - objeto de representação de alto nível de uma pasta de trabalho para uma planilha
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha
	 * @param standard - estilo padrão para colunas atribuidas a fórmula
	 * @param tableHeader - estilo apenas para a string "TOTAL"
	 * @param rowTotal - última linha a ser apresentado o total	
	 * @param multi - define se o template do total é de múltiplos equipamentos ou não
	 * @param columnsLength - número de colunas  
	 * @param rowIni - linha inicial		
	 * @param rowEnd - linha final		
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFWorkbook.html	
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFSheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFRow.html
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html
	 * 
	 */
	public void totalTime(XSSFWorkbook workbook, XSSFSheet sheet, XSSFRow row, CellStyle standard, CellStyle tableHeader, boolean isMulti, 
			int columnsLength, int columnNumber, int rowTotal, int rowIni, int rowEnd) {

		// CREATE ROW TOTAL
		utilSheet.createRow(sheet, row, rowTotal);		 

		utilSheet.createCells(sheet, row, 0, columnsLength, rowTotal, rowTotal);   // CREATE CELLS			
		utilSheet.setCellValue(sheet, row, rowTotal, 0, localeExcel.getStringKey("excel_sheet_table_total")); // SET FIRST CELL VALUE						
		utilSheet.setCellStyle(sheet, row, tableHeader, rowTotal, 0); // SET FIRST CELL STYLE	

		utilSheet.totalExcelDate(workbook, sheet, row, standard, isMulti, rowTotal, columnsLength, columnNumber, rowIni, rowEnd); // SET CELL FORMULA					

	}
	
	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// DOWNLOAD
    // ----------------------------------------------------------------------------------------------------------------

	public void download(String fileName) throws IOException {

		utilSheet.donwloadExcelFile(workbook, fileName);

	}

	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// MAIN TEMPLATES
	// ----------------------------------------------------------------------------------------------------------------
	
	/**  
	 * Método para criar um modelo para um relátorio para uma única planilha
	 * @author Wellington 15/10/2021
	 * @version 1.0
	 * @since 1.0 
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha		
	 * @param multi - define o total para múltiplos equipamentos ou não
	 * @param numLanes - número de linhas caso equipmemnto possua
	 * @param columnsHeader - matriz com os valores das colunas
	 * @param values - matriz com os valores obtidos pelo banco
	 * @param rowTotal - linha dos valores a serem somados no total
	 * @param iniRow - linha inicial
	 * @param endRow - linha final	
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFSheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFRow.html	
	 */
	public void singleTempateWithSum(XSSFSheet sheet, XSSFRow row, boolean multi, boolean isSat, int firstRow,String[] columnsHeader, String[][] values, int rowTotal, int iniRow, int endRow) {

		int columns = columnsHeader.length; // TOTAL DE COLUNAS		
		int startColumn = 0; // COLUNA INICIAL		
		int maxCol = (columns - 1); // INDEX MAX DAS COLUNAS
		int minCol = 2; // INDEX INICIAL DAS COLUNAS

		// CRIAR PRIMEIRA LINHA DO CABEÇALHO
		utilSheet.createRow(sheet, row, firstRow);	

		utilSheet.createCells(sheet, row, startColumn, columns, firstRow, firstRow); // CRIAR A CÉLULAS DO CABEÇALHO		 
		utilSheet.setCellsValues(sheet, row, firstRow, columnsHeader); // INSERIR VALORES		 
		utilSheet.setCellsStyle(sheet, row, tableHeadStyle, startColumn, columns, firstRow, firstRow); // DEFINIR ESTILO

		// CRIAR LINHAS PARA APRESENTAÇÃO DOS DADOS
		utilSheet.createRows(sheet, row, iniRow, endRow);
		utilSheet.createCells(sheet, row, startColumn, columns, iniRow, endRow);

		utilSheet.fileBodySingle(sheet, row, values, startColumn, columns, iniRow, endRow); // PREENCHER DADOS

		utilSheet.setCellsStyle(sheet, row, dateTimeStyle, 0, 1, iniRow, endRow); // ESTILOS
		utilSheet.setCellsStyle(sheet, row, standardStyle, minCol, maxCol, iniRow, endRow); // ESTILOS

		//totalSum(sheet, row, standardStyle, tableHeaderStyle, multi, columns, rowTotal, iniRow, endRow); // TOTAL


	}

	// ----------------------------------------------------------------------------------------------------------------

	/**  
	 * Método para criar um modelo para um relátorio para multiplas planilhas
	 * @author Wellington 15/10/2021
	 * @version 1.0
	 * @since 1.0 
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha		
	 * @param multi - define o total para múltiplos equipamentos ou não
	 * @param numLanes - número de linhas caso equipmemnto possua
	 * @param columnsHeader - matriz com os valores das colunas
	 * @param values - matriz com os valores obtidos pelo banco
	 * @param rowTotal - linha dos valores a serem somados no total
	 * @param iniRow - linha inicial
	 * @param endRow - linha final	
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFSheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFRow.html	
	 */
	public void multipleTemplate(XSSFSheet sheet, XSSFRow row, boolean multi, boolean isSat, int firstRow, String[] columnsHeader, String[][] values, int iniRow, int endRow, int day, int interval) {
	
		int columns = columnsHeader.length; // TOTAL DE COLUNAS		
		int startColumn = 0; // COLUNA INICIAL		
		int maxCol = (columns - 1); // INDEX MAX DAS COLUNAS
		int minCol = 2; // INDEX INICIAL DAS COLUNAS

		// CRIAR PRIMEIRA LINHA DO CABEÇALHO
		utilSheet.createRow(sheet, row, firstRow);	

		utilSheet.createCells(sheet, row, startColumn, columns, firstRow, firstRow); // CRIAR A CÉLULAS DO CABEÇALHO		 
		utilSheet.setCellsValues(sheet, row, firstRow, columnsHeader); // INSERIR VALORES		 
		utilSheet.setCellsStyle(sheet, row, tableHeadStyle, startColumn, columns, firstRow, firstRow); // DEFINIR ESTILO

		// CRIAR LINHAS PARA APRESENTAÇÃO DOS DADOS
		utilSheet.createRows(sheet, row, iniRow, endRow);
		utilSheet.createCells(sheet, row, startColumn, columns, iniRow, endRow);

		utilSheet.fileBodyMulti(sheet, row, values, startColumn, columns, iniRow, endRow, day, interval); // PREENCHER DADOS

		utilSheet.setCellsStyle(sheet, row, dateTimeStyle, 0, 1, iniRow, endRow); // ESTILOS
		utilSheet.setCellsStyle(sheet, row, standardStyle, minCol, maxCol, iniRow, endRow); // ESTILOS

		//totalSum(sheet, row, standardStyle, tableHeaderStyle, multi, columns, rowTotal, iniRow, endRow); // TOTAL

	}

	// ----------------------------------------------------------------------------------------------------------------
	// TEMPLATE MODEL
	// ----------------------------------------------------------------------------------------------------------------
	
	public void generateMultiExcelFile(XSSFWorkbook workbook, XSSFSheet sheet, XSSFRow row, String pathLogo, String[] equipNames, String[] columns, String fileTitle,
				String startDate, String endDate, String period, String[][] equipInfo, boolean isSat, String[][] values, int interval) {
		
		int firstRow = 0; // PRIMEIRAS LINHA DO HEADER
		
		// CASO EXISTA FAIXAS NO EQUIPAMENTO
		if(isSat)
			firstRow = 11;

		// CASO NÃO EXISTA FAIXAS NO EQUIPAMENTO
		else firstRow = 10;
				
		// ----------------------------------------------------------------------------------------------------------------
		
		int iniRow = firstRow + 2;
		int endRow = iniRow + ReportBuild.numRegisters;
		
		// ----------------------------------------------------------------------------------------------------------------
		
		for(int d = 0; d < equipNames.length; d++) {
		
		    //spreadSheetHeader(workbook, sheet, row, pathLogo, equipNames, columns, fileTitle, startDate, endDate, period, equipInfo, isSat, d);		    
		    multipleTemplate(sheet, row, true, isSat, firstRow, columns, values, iniRow, endRow, d, interval);
		    	    		
		}

	}

	// ----------------------------------------------------------------------------------------------------------------
	
	public void generateExcelFile(List<String> columns, List<String[]> rows, String startDate, String endDate, String period, String fileTitle, String[] equipInfo) {
		
		sheet = null;		
		row = null;
				
		int startRow = 2;
		int endRow = startRow + rows.size();
		int startCol = 0;
		int endCol = columns.size() - 1;
		
		sheet = workbook.createSheet("TRACEVIA");
		
		spreadSheetHeader(workbook, sheet, row, RoadConcessionaire.logo, columns.size(), fileTitle, 
				startDate, endDate, period, equipInfo, false) ;
		
									
		// CRIAR LINHAS PARA APRESENTAÇÃO DOS DADOS
		utilSheet.createRows(sheet, row, startRow, endRow);
		utilSheet.createCells(sheet, row, startCol, endCol, startRow, endRow);
								 		
		utilSheet.fileBodySimple(sheet, row, columns, rows, startCol, endCol, startRow);
		
		utilSheet.setCellsStyle(sheet, row, standardStyle, startCol, endCol, startRow, endRow);
		
		// ----------------------------------------------------------------------------------------------------------------
					
	}
}
