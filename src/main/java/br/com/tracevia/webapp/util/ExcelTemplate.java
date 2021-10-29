package br.com.tracevia.webapp.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.ReportBuild;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.sat.SAT;

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

	LocaleUtil localeExcel;

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
	dateTimeStyle, centerBoldStyle, centerAlignStandardStyle;   

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
		
		standardFont = utilSheet.createFont(workbook, FONT_ARIAL, 10, false, false, IndexedColors.BLACK); // FONTE DE USO PADRÃƒO 			 
	    boldFont = utilSheet.createFont(workbook,  FONT_ARIAL, 10, true, false, IndexedColors.BLACK); // FONTE EM NEGRITO
	    titleFont = utilSheet.createFont(workbook,  FONT_ARIAL, 16, true, false, IndexedColors.BLACK); // FONTE PARA O TÃ�TULO
	    tableHeadFont = utilSheet.createFont(workbook, FONT_ARIAL, 10, true, false, IndexedColors.WHITE); // FONT PARA CABEÃ‡ALHO DA TABELA
	    
		// COUNT FLOW FONTS
	    
	    countFlowFont = utilSheet.createFont(workbook, FONT_ARIAL, 11, true, false, IndexedColors.WHITE); // FONTE PADRÃƒO COUNT FLOW
	    countFlowFontBody1 = utilSheet.createFont(workbook, FONT_ARIAL, 10, false, false, IndexedColors.ORANGE); // FONTE PARA APRESENTAÃ‡ÃƒO DE DADOS 1
	    countFlowFontBody2 = utilSheet.createFont(workbook, FONT_ARIAL, 10, false, false, IndexedColors.GREEN); // FONTE PARA APRESENTAÃ‡ÃƒO DE DADOS 2	       

		// ----------------------------------------------------------------------------------------------------------------

		// DEFAULT STYLES	
	    
	    // ESTILO PADRÃƒO
	    standardStyle = utilSheet.createCellStyle(workbook, standardFont, HorizontalAlignment.CENTER, IndexedColors.WHITE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	   	// ESTILO PARA TÃ�TULO
	    titleStyle = utilSheet.createCellStyle(workbook, titleFont, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, true, IndexedColors.WHITE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	    // ESTILO PARA CABEÃ‡ALHO DAS TABELAS
	    tableHeadStyle = utilSheet.createCellStyle(workbook, tableHeadFont, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, IndexedColors.LIGHT_BLUE, FillPatternType.SOLID_FOREGROUND,ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	    // ESTILO PARA CABEÃ‡ALHO ENTRE DATAS 
	    dateTitleStyle = utilSheet.createCellStyle(workbook, standardFont, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, true, IndexedColors.WHITE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	    // ESTILO PARA CAMPO DE DATAS EM NEGRITO
	    dateTimeStyle = utilSheet.createCellStyle(workbook, boldFont, HorizontalAlignment.CENTER, IndexedColors.WHITE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
        // ESTILO NEGRITO CENTRALIZADO (SEM BORDAS)  
	    centerBoldStyle = utilSheet.createCellStyle(workbook, boldFont, HorizontalAlignment.CENTER, IndexedColors.WHITE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.NONE);
	    // ESTILO PADRÃƒO DE ALINHAMENTO CENTRAL	(SEM BORDAS)  
	    centerAlignStandardStyle = utilSheet.createCellStyle(workbook, standardFont, HorizontalAlignment.CENTER, IndexedColors.WHITE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.NONE);
		// ESTILO PARA ALINHAMENTO A ESQUERDA FONTE PADRÃƒO (SEM BORDAS)
	    leftAlignStandardStyle = utilSheet.createCellStyle(workbook, standardFont, HorizontalAlignment.LEFT, IndexedColors.WHITE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.NONE);
		// ESTILO PARA ALINHAMENTO A DIREITA EM NEGRITO (SEM BORDAS)
	    rightAlignBoldStyle = utilSheet.createCellStyle(workbook, boldFont, HorizontalAlignment.RIGHT, IndexedColors.WHITE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.NONE);
		
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

	public void excelSingleFileHeader(XSSFWorkbook workbook, XSSFSheet sheet, XSSFRow row, String pathLogo, String module, int columns, String fileTitle, 
			String startDate, String endDate, String period, List<String> equipId) {

		DateTimeApplication dta = new DateTimeApplication();
		TranslationMethods tm = new TranslationMethods();
	
		// INDEX DA COLUNA DE INICIO DA DATA
		
		int columnsIndex = 0;
		int columnStartDate = 0;
		int columnEndDate = 0;
			
		// MINIMO 5 COLUNAS PADRÃO
		if(columns < 5)
			columnsIndex = 5;
		
		else columnsIndex = columns;
		
		columnStartDate = columns + 1;
		columnEndDate = columns + 3;	
	
		// ----------------------------------------------------------------------------------------------------------------
					
		if(!module.equals("sat")) {
		    if(equipId.size() == 1)		
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
		
		// DEFINIR O TÃ�TULO	DO HEADER					
		utilSheet.setCellValue(sheet, row, 0, 2, fileTitle);
		utilSheet. setCellsStyle(sheet, row, titleStyle, 2, columnsIndex, 0, 3); // ESTILO TITULO

		// HEADER DATE TEMPLATE
		String headerDates = localeExcel.getStringKey("excel_sheet_header_date_from")+": " + startDate+ "\n"+localeExcel.getStringKey("excel_sheet_header_date_to")+": " + endDate;

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
		utilSheet.mergeCells(sheet, "I6:J6");
		
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
		utilSheet.setCellValue(sheet, row, 7, 2, module.equals("sat")? satInfo.get(0).getEstrada() : equipsInfo.get(0).getEstrada());
		utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 7, 2);
		
		if(!period.equals("")) {

		// PERÃ�ODO LABEL
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
		utilSheet.setCellValue(sheet, row, 8, 2, module.equals("sat")? satInfo.get(0).getKm() : equipsInfo.get(0).getKm());
		utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 8, 2);

		// ----------------------------------------------------------------------------------------------------------------

		// CASO O NÃšMERO DE LINHAS FOR MAIOR QUE 1 
		// ENTRA NESSA CONDIÃ‡ÃƒO
		// UTILIZADA ESPECIFICAMENTE PARA O SAT
		if(module.equals("sat")) {

			// CRIAR LINHA 5 - SUBHEADER
			utilSheet.createRow(sheet, row, 9);

			// LANE LABELS
			utilSheet.createCell(sheet, row, 9, 1);
			utilSheet.setCellValue(sheet, row, 9, 1, localeExcel.getStringKey("excel_sheet_header_lanes"));
			utilSheet.setCellStyle(sheet, row, centerBoldStyle, 9, 1); 

			// NÃšMERO DE LINHAS
			utilSheet.createCell(sheet, row, 9, 2);
			
			if(satInfo.get(0).getQtdeFaixas().matches(NUMBER_REGEX))
				utilSheet.setCellValue(sheet, row, 9, 2, Integer.parseInt(satInfo.get(0).getQtdeFaixas()));
			
			else utilSheet.setCellValue(sheet, row, 9, 2, satInfo.get(0).getQtdeFaixas()); 
			
			utilSheet.setCellStyle(sheet, row, centerAlignStandardStyle, 9, 2);

		}
		
		// ----------------------------------------------------------------------------------------------------------------
				
	}
	
	// ----------------------------------------------------------------------------------------------------------------
	
	public void spreadSheetHeader(XSSFWorkbook workbook, XSSFSheet sheet, XSSFRow row, String pathLogo, String[] columns, String fileTitle, 
			String startDate, String endDate, String period, String[][] equipInfo, boolean isSat, int posArray) {

		DateTimeApplication dta = new DateTimeApplication();
		TranslationMethods tm = new TranslationMethods();

		// INDEX DA COLUNA DE Ã�NICIO DA DATA
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

		// DEFINIR O TÃ�TULO	DO HEADER					
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

		// PERÃ�ODO LABEL
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

		// CASO O NÃšMERO DE LINHAS FOR MAIOR QUE 1 
		// ENTRA NESSA CONDIÃ‡ÃƒO
		// UTILIZADA ESPECIFICAMENTE PARA O SAT
		if(isSat) {

			// CRIAR LINHA 5 - SUBHEADER
			utilSheet.createRow(sheet, row, 9);

			// LANE LABELS
			utilSheet.createCell(sheet, row, 9, 1);
			utilSheet.setCellValue(sheet, row, 9, 1, localeExcel.getStringKey("excel_sheet_header_lanes"));
			utilSheet.setCellStyle(sheet, row, centerBoldStyle, 9, 1); 

			// NÃšMERO DE LINHAS
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
	public void totalSum(XSSFSheet sheet, XSSFRow row, CellStyle standard, CellStyle tableHeader, int columnsLength, int rowTotal, int rowIni, int rowEnd) {

		// CREATE ROW TOTAL
		utilSheet.createRow(sheet, row, rowTotal);		 

		utilSheet.createCells(sheet, row, 0, columnsLength, rowTotal, rowTotal);   // CREATE CELLS			
		utilSheet.setCellValue(sheet, row, rowTotal, 0, localeExcel.getStringKey("excel_sheet_table_total")); // SET FIRST CELL VALUE						
		utilSheet.setCellStyle(sheet, row, tableHeader, rowTotal, 0); // SET FIRST CELL STYLE	

		utilSheet.totalExcelSum(sheet, row, standard, rowTotal, columnsLength, rowIni, rowEnd); // SET CELL FORMULA			

	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * MÃ©todo para criar colunas com total MÃ‰DIA
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
	 * MÃ©todo para criar colunas com total para TEMPO
	 * @author Wellington 15/10/2021
	 * @version 1.0
	 * @since 1.0  
	 * @param workbook - objeto de representaÃ§Ã£o de alto nÃ­vel de uma pasta de trabalho para uma planilha
	 * @param sheet - objeto de representaÃ§Ã£o de alto nÃ­vel de uma planilha
	 * @param row - objeto de representaÃ§Ã£o de alto nÃ­vel de uma linha de uma planilha
	 * @param standard - estilo padrÃ£o para colunas atribuidas a fÃ³rmula
	 * @param tableHeader - estilo apenas para a string "TOTAL"
	 * @param rowTotal - Ãºltima linha a ser apresentado o total	
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
	 * MÃ©todo para criar um modelo para um relÃ¡torio para uma Ãºnica planilha
	 * @author Wellington 15/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param sheet - objeto de representaÃ§Ã£o de alto nÃ­vel de uma planilha
	 * @param row - objeto de representaÃ§Ã£o de alto nÃ­vel de uma linha de uma planilha	
	 * @param multi - define o total para mÃºltiplos equipamentos ou nÃ£o
	 * @param numLanes - nÃºmero de linhas caso equipmemnto possua
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

		// CRIAR PRIMEIRA LINHA DO CABEÃ‡ALHO
		utilSheet.createRow(sheet, row, firstRow);	

		utilSheet.createCells(sheet, row, startColumn, columns, firstRow, firstRow); // CRIAR A CÃ‰LULAS DO CABEÃ‡ALHO		 
		utilSheet.setCellsValues(sheet, row, firstRow, columnsHeader); // INSERIR VALORES		 
		utilSheet.setCellsStyle(sheet, row, tableHeadStyle, startColumn, columns, firstRow, firstRow); // DEFINIR ESTILO

		// CRIAR LINHAS PARA APRESENTAÃ‡ÃƒO DOS DADOS
		utilSheet.createRows(sheet, row, iniRow, endRow);
		utilSheet.createCells(sheet, row, startColumn, columns, iniRow, endRow);

		utilSheet.fileBodySingle(sheet, row, values, startColumn, columns, iniRow, endRow); // PREENCHER DADOS

		utilSheet.setCellsStyle(sheet, row, dateTimeStyle, 0, 1, iniRow, endRow); // ESTILOS
		utilSheet.setCellsStyle(sheet, row, standardStyle, minCol, maxCol, iniRow, endRow); // ESTILOS

		//totalSum(sheet, row, standardStyle, tableHeaderStyle, multi, columns, rowTotal, iniRow, endRow); // TOTAL

	}

	// ----------------------------------------------------------------------------------------------------------------

	/**  
	 * MÃ©todo para criar um modelo para um relÃ¡torio para multiplas planilhas
	 * @author Wellington 15/10/2021
	 * @version 1.0
	 * @since 1.0 
	 * @param sheet - objeto de representaÃ§Ã£o de alto nÃ­vel de uma planilha
	 * @param row - objeto de representaÃ§Ã£o de alto nÃ­vel de uma linha de uma planilha		
	 * @param multi - define o total para mÃºltiplos equipamentos ou nÃ£o
	 * @param numLanes - nÃºmero de linhas caso equipmemnto possua
	 * @param columnsHeader - matriz com os valores das colunas
	 * @param values - matriz com os valores obtidos pelo banco
	 * @param rowTotal - linha dos valores a serem somados no total
	 * @param iniRow - linha inicial
	 * @param endRow - linha final	
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFSheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFRow.html	
	 */
	public void multipleTemplate(XSSFSheet sheet, XSSFRow row, boolean multi, String module, int firstRow, String[] columnsHeader, String[][] values, int iniRow, int endRow, int day, int interval) {
	
		int columns = columnsHeader.length; // TOTAL DE COLUNAS		
		int startColumn = 0; // COLUNA INICIAL		
		int maxCol = (columns - 1); // INDEX MAX DAS COLUNAS
		int minCol = 2; // INDEX INICIAL DAS COLUNAS

		// CRIAR PRIMEIRA LINHA DO CABEÃ‡ALHO
		utilSheet.createRow(sheet, row, firstRow);	

		utilSheet.createCells(sheet, row, startColumn, columns, firstRow, firstRow); // CRIAR A CÃ‰LULAS DO CABEÃ‡ALHO		 
		utilSheet.setCellsValues(sheet, row, firstRow, columnsHeader); // INSERIR VALORES		 
		utilSheet.setCellsStyle(sheet, row, tableHeadStyle, startColumn, columns, firstRow, firstRow); // DEFINIR ESTILO

		// CRIAR LINHAS PARA APRESENTAÃ‡ÃƒO DOS DADOS
		utilSheet.createRows(sheet, row, iniRow, endRow);
		utilSheet.createCells(sheet, row, startColumn, columns, iniRow, endRow);

		utilSheet.fileBodyMulti(sheet, row, values, startColumn, columns, iniRow, endRow, day, interval); // PREENCHER DADOS

		utilSheet.setCellsStyle(sheet, row, dateTimeStyle, 0, 1, iniRow, endRow); // ESTILOS
		utilSheet.setCellsStyle(sheet, row, standardStyle, minCol, maxCol, iniRow, endRow); // ESTILOS

		// totalSum(sheet, row, standardStyle, tableHeaderStyle, multi, columns, rowTotal, iniRow, endRow); // TOTAL

	}

	// ----------------------------------------------------------------------------------------------------------------
	// TEMPLATE MODEL
	// ----------------------------------------------------------------------------------------------------------------
	
	public void generateMultiExcelFile(XSSFWorkbook workbook, XSSFSheet sheet, XSSFRow row, String pathLogo, String[] equipNames, String[] columns, String fileTitle,
				String startDate, String endDate, String period, String[][] equipInfo, String module, String[][] values, int interval) {
		
		int firstRow = 0; // PRIMEIRAS LINHA DO HEADER
		
		// CASO EXISTA FAIXAS NO EQUIPAMENTO
		if(module.equals("sat"))
			firstRow = 11;

		// CASO NÃƒO EXISTA FAIXAS NO EQUIPAMENTO
		else firstRow = 10;
				
		// ----------------------------------------------------------------------------------------------------------------
		
		int iniRow = firstRow + 2;
		int endRow = iniRow + ReportBuild.numRegisters;
		
		// ----------------------------------------------------------------------------------------------------------------
		
		for(int d = 0; d < equipNames.length; d++) {
		
		    //spreadSheetHeader(workbook, sheet, row, pathLogo, equipNames, columns, fileTitle, startDate, endDate, period, equipInfo, isSat, d);		    
		    multipleTemplate(sheet, row, true, module, firstRow, columns, values, iniRow, endRow, d, interval);
		    	    		
		}

	}

	// ----------------------------------------------------------------------------------------------------------------
	
	public void generateExcelFile(List<String> columns, List<String[]> rows, String module, List<String> equips, String startDate, String endDate, String period, String sheetName, String fileTitle, boolean isSat, boolean isTotal) {
		
		sheet = null;		
		row = null;
				
		int tableStartRow = 0;
		int dataStartRow = 0;
		int dataEndRow = 0;
		int startCol = 0;
		int endCol = columns.size() - 1;
		int dataTotalRow = columns.size();
		
		sheet = workbook.createSheet(sheetName);
										
		excelSingleFileHeader(workbook, sheet, row, RoadConcessionaire.externalImagePath, module, columns.size(), fileTitle,  
				startDate, endDate, period, equips);
									    		
		// CASO EXISTA FAIXAS NO EQUIPAMENTO
		if(module.equals("sat")) {
			tableStartRow = 11;
			dataStartRow = 12;

		// CASO NÃƒO EXISTA FAIXAS NO EQUIPAMENTO 
	    }else {  tableStartRow = 10; dataStartRow = 11; }
		
		dataEndRow = dataStartRow + rows.size() - 1;
						
		utilSheet.createRow(sheet, row, tableStartRow);
		utilSheet.createCells(sheet, row, startCol, endCol, tableStartRow, tableStartRow);
		utilSheet.setHeaderCellsValue(sheet, row, tableStartRow, columns);
		
		utilSheet.setCellsStyle(sheet, row, tableHeadStyle, startCol, endCol, tableStartRow, tableStartRow);
													
		// CRIAR LINHAS PARA APRESENTAÃ‡ÃƒO DOS DADOS
		utilSheet.createRows(sheet, row, dataStartRow, dataEndRow);
		utilSheet.createCells(sheet, row, startCol, endCol, dataStartRow, dataEndRow);
								 		
		utilSheet.fileBodySimple(sheet, row, columns, rows, startCol, endCol, dataStartRow);
		
		utilSheet.setCellsStyle(sheet, row, standardStyle, startCol, endCol, dataStartRow, dataEndRow);
		
		// TABLE COLUMNS AUTO SIZE 
		utilSheet.columnsWidthAuto(sheet, columns.size());
		
		if(isTotal)
			totalSum(sheet, row, standardStyle, tableHeadStyle, columns.size(), dataTotalRow, dataStartRow, dataEndRow); // TOTAL
			
		
	// ----------------------------------------------------------------------------------------------------------------
					
	}	
	
	// ----------------------------------------------------------------------------------------------------------------
	
	public List<Equipments> genericInfo(List<String> equipId, String module) { 
		
		List<Equipments> info = new ArrayList<Equipments>();
		 dao = new EquipmentsDAO();
		
		try {
						 			 
			 for(int s = 0; s < equipId.size(); s++)	        	
			    info = dao.EquipReportInfo(equipId.get(s), module);
			 								
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
		
		lista.add(sat);
		
		return lista;
		
	}
	
	 // ----------------------------------------------------------------------------------------------------------------

}
