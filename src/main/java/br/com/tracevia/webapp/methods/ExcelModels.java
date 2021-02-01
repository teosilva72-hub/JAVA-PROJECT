package br.com.tracevia.webapp.methods;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Chart;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.charts.AxisCrosses;
import org.apache.poi.ss.usermodel.charts.AxisPosition;
import org.apache.poi.ss.usermodel.charts.ChartAxis;
import org.apache.poi.ss.usermodel.charts.ChartDataSource;
import org.apache.poi.ss.usermodel.charts.ChartLegend;
import org.apache.poi.ss.usermodel.charts.DataSources;
import org.apache.poi.ss.usermodel.charts.LegendPosition;
import org.apache.poi.ss.usermodel.charts.LineChartData;
import org.apache.poi.ss.usermodel.charts.LineChartSeries;
import org.apache.poi.ss.usermodel.charts.ValueAxis;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.com.tracevia.webapp.util.LocaleUtil;

public class ExcelModels {

	DateTimeApplication dta;
	TranslationMethods tm;
	ExcelSpreadSheet spreadSheet;
	
	XSSFSheet sheet;
	XSSFRow row;
	
	Cell[] headerCells;
	Cell[][] cellData;
	
	LocaleUtil localeDirections, localeExcel;
	
	public static XSSFWorkbook workbook;
	
	//Standard Model
	CellStyle headerStyle, leftAlignStyle, boldRightAlignStyle, standardStyle, datePeriodStyle, tableHeaderStyle, dateHourStyle, boldCenterStyle, centerAlignStyle,
	backgroundColorHeader, backgroundColorSubHeader, backgroundColorSubHeader2, backgroundColorBody, backgroundColorBody2;
	
	Font standardFont, boldFont, titleFont, headerFont, fontCountFlowBackground, fontCountFlowBody, fontCountFlowBody2;
		
	//Period Flow
	CellStyle subHeaderStyle, dayStyle, espaceStyle, espaceStyle2, backgroundColorBodyHeaderStyle, backgroundColorBodyHeaderStyle2,  
	blueColorBackgroundStyle, redColorBackgroundStyle, numbersTitleStyle, reportTimeStyle, intervalStyle, standardStyle1, blankStyle, 
	blankStyle2; 
  
	Font fontDateHeader, fontEspace, fontEspace2, fontBodyHeader2, fontBodyHeader, fontBackgroundColor, fontNumbersTitle,
	fontReportTime, fontSubHeader, fontBody, fontInterval; 
	
	//Montlhy Flow	
	CellStyle indigoBackgroundStyle, monthBodybackgroundStyle, grayBackgroundStyle, monthDateTimeStyle, monthBoldLeftStyle, monthBoldCenterStyle,  monthBoldBody,
	monthLowDescriptStyle;
	
	Font fontMonthHeader, fontMonthBody, fontMonthDateTime, fontLowDescript;
	
	public ExcelModels() {
		
		spreadSheet = new ExcelSpreadSheet();
		workbook = new XSSFWorkbook();
		
		localeExcel = new LocaleUtil();		
		localeExcel.getResourceBundle(LocaleUtil.LABELS_EXCEL);
		
		localeDirections = new LocaleUtil();		
		localeDirections.getResourceBundle(LocaleUtil.LABELS_DIRECTIONS);
		
	}
	
	public void StandardFonts() {
		
		standardFont = spreadSheet.createFont(workbook, 10, false, false);
		spreadSheet.addFontColor(standardFont, IndexedColors.BLACK);

		boldFont = spreadSheet.createFont(workbook, 10, true, false);
		spreadSheet.addFontColor(boldFont, IndexedColors.BLACK);
		
		headerFont = spreadSheet.createFont(workbook, 10, true, false);
		spreadSheet.addFontColor(headerFont, IndexedColors.WHITE);

		titleFont = spreadSheet.createFont(workbook, 16, true, false);
		spreadSheet.addFontColor(titleFont, IndexedColors.BLACK);
	}
		
	//FONT COUNT FLOW	
	public void countFlowFonts() {
				
		standardFont = spreadSheet.createFont(workbook, 10, false, false);
		spreadSheet.addFontColor(standardFont, IndexedColors.BLACK);

		boldFont = spreadSheet.createFont(workbook, 10, true, false);
		spreadSheet.addFontColor(boldFont, IndexedColors.BLACK);
		
		headerFont = spreadSheet.createFont(workbook, 11, true, false);
		spreadSheet.addFontColor(headerFont, IndexedColors.WHITE);

		titleFont = spreadSheet.createFont(workbook, 16, true, false);
		spreadSheet.addFontColor(titleFont, IndexedColors.BLACK);
		
		fontCountFlowBackground = spreadSheet.createFont(workbook, 11, true, false);
		spreadSheet.addFontColor(fontCountFlowBackground, IndexedColors.WHITE);
		
		fontCountFlowBody = spreadSheet.createFont(workbook, 10, false, false);
		spreadSheet.addFontColor(fontCountFlowBody, IndexedColors.GREEN);
		
		fontCountFlowBody2 = spreadSheet.createFont(workbook, 10, false, false);
		spreadSheet.addFontColor(fontCountFlowBody2, IndexedColors.ORANGE);
	}
	
	
	public void periodFlowFonts() {
		
			fontBody = workbook.createFont();
			fontBody.setFontName(HSSFFont.FONT_ARIAL);
			fontBody.setFontHeightInPoints((short) 10);

			fontSubHeader = workbook.createFont();
			fontSubHeader.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
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

			fontEspace = workbook.createFont();
			fontEspace.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
			fontEspace.setFontName(HSSFFont.FONT_ARIAL); 
			fontEspace.setFontHeightInPoints((short) 9);	
			fontEspace.setBold(true);	

			fontEspace2 = workbook.createFont();
			fontEspace2.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
			fontEspace2.setFontName(HSSFFont.FONT_ARIAL); 
			fontEspace2.setFontHeightInPoints((short) 11);	
			fontEspace2.setBold(true);	

			fontDateHeader = workbook.createFont();
			fontDateHeader.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
			fontDateHeader.setFontName(HSSFFont.FONT_ARIAL); 
			fontDateHeader.setFontHeightInPoints((short) 11);	
			fontDateHeader.setBold(true);

			fontInterval = workbook.createFont();
			fontInterval.setColor(HSSFColor.HSSFColorPredefined.DARK_BLUE.getIndex());
			fontInterval.setBold(true);
			fontInterval.setFontName(HSSFFont.FONT_ARIAL);
			fontInterval.setFontHeightInPoints((short) 10);

	       }
	
	      public void monthlyFlowFonts() {
		
		  fontMonthHeader = workbook.createFont();
		  fontMonthHeader.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
		  fontMonthHeader.setBold(true);	
			
		  fontMonthBody = workbook.createFont();
		  fontMonthBody.setFontName(HSSFFont.FONT_ARIAL);
		  fontMonthBody.setFontHeightInPoints((short) 10);
		  
		  fontMonthDateTime =  workbook.createFont();
		  fontMonthDateTime.setColor(HSSFColor.HSSFColorPredefined.DARK_BLUE.getIndex());
	      fontMonthDateTime.setBold(true);
		  fontMonthDateTime.setFontName(HSSFFont.FONT_ARIAL);
		  fontMonthDateTime.setFontHeightInPoints((short) 10);	
		  
		  boldFont = spreadSheet.createFont(workbook, 10, true, false);
		  spreadSheet.addFontColor(boldFont, IndexedColors.BLACK);
		  
		  fontLowDescript = spreadSheet.createFont(workbook, 8, false, false);
		  spreadSheet.addFontColor(fontLowDescript, IndexedColors.BLACK);
		  	
	    }
	
	   //STYLES	  
	    public void StandardStyles() {
			
		    headerStyle = spreadSheet.createStyle(workbook, titleFont, IndexedColors.WHITE);
			spreadSheet.addStyleVerticalHorizontalAlignment(workbook, headerStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
			spreadSheet.wrapText(headerStyle, true);
			
			leftAlignStyle = spreadSheet.createStyle(workbook, standardFont, IndexedColors.WHITE);
			spreadSheet.addStyleHorizontalAlignment(workbook, leftAlignStyle, HorizontalAlignment.LEFT);

			boldRightAlignStyle = spreadSheet.createStyle(workbook, boldFont, IndexedColors.WHITE);
			spreadSheet.addStyleHorizontalAlignment(workbook, boldRightAlignStyle, HorizontalAlignment.RIGHT);

			standardStyle = spreadSheet.createStyle(workbook, standardFont, IndexedColors.WHITE);
			spreadSheet.addStyleHorizontalAlignment(workbook, standardStyle, HorizontalAlignment.CENTER);

			datePeriodStyle = spreadSheet.createStyle(workbook, standardFont, IndexedColors.WHITE);
			spreadSheet.addStyleVerticalHorizontalAlignment(workbook, datePeriodStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
			spreadSheet.wrapText(datePeriodStyle, true);

			tableHeaderStyle = spreadSheet.createStyle(workbook, headerFont, IndexedColors.WHITE);
			spreadSheet.addStyleBackgroundColor(tableHeaderStyle, IndexedColors.LIGHT_BLUE);
			spreadSheet.addStyleVerticalHorizontalAlignment(workbook, tableHeaderStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
			spreadSheet.addFontColor(headerFont, IndexedColors.WHITE);
			spreadSheet.wrapText(tableHeaderStyle, true);
			
			dateHourStyle = spreadSheet.createStyle(workbook, boldFont, IndexedColors.WHITE);
			spreadSheet.addStyleHorizontalAlignment(workbook, dateHourStyle, HorizontalAlignment.CENTER);
			
			boldCenterStyle = spreadSheet.createStyle(workbook, boldFont, IndexedColors.WHITE);
			spreadSheet.addStyleVerticalHorizontalAlignment(workbook, boldCenterStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
			
			centerAlignStyle = spreadSheet.createStyle(workbook, standardFont, IndexedColors.WHITE);
			spreadSheet.addStyleHorizontalAlignment(workbook, centerAlignStyle, HorizontalAlignment.CENTER);
			
		}
	  
	//STYLES COUNT FLOW	
	public void countFlowStyles() {
		
		headerStyle = spreadSheet.createStyle(workbook, titleFont, IndexedColors.WHITE);
		spreadSheet.addStyleVerticalHorizontalAlignment(workbook, headerStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
		spreadSheet.wrapText(headerStyle, true);
		
		leftAlignStyle = spreadSheet.createStyle(workbook, standardFont, IndexedColors.WHITE);
		spreadSheet.addStyleHorizontalAlignment(workbook, leftAlignStyle, HorizontalAlignment.LEFT);

		boldRightAlignStyle = spreadSheet.createStyle(workbook, boldFont, IndexedColors.WHITE);
		spreadSheet.addStyleHorizontalAlignment(workbook, boldRightAlignStyle, HorizontalAlignment.RIGHT);

		standardStyle = spreadSheet.createStyle(workbook, standardFont, IndexedColors.WHITE);
		spreadSheet.addStyleHorizontalAlignment(workbook, standardStyle, HorizontalAlignment.CENTER);

		datePeriodStyle = spreadSheet.createStyle(workbook, standardFont, IndexedColors.WHITE);
		spreadSheet.addStyleVerticalHorizontalAlignment(workbook, datePeriodStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
		spreadSheet.wrapText(datePeriodStyle, true);		
		
		dateHourStyle = spreadSheet.createStyle(workbook, boldFont, IndexedColors.WHITE);
		spreadSheet.addStyleHorizontalAlignment(workbook, dateHourStyle, HorizontalAlignment.CENTER);

		tableHeaderStyle= spreadSheet.createStyle(workbook, headerFont, IndexedColors.WHITE);
		spreadSheet.addStyleBackgroundColor(tableHeaderStyle, IndexedColors.LIGHT_BLUE);
		spreadSheet.addStyleHorizontalAlignment(workbook, tableHeaderStyle, HorizontalAlignment.CENTER);
		spreadSheet.addFontColor(headerFont, IndexedColors.WHITE);
			
		backgroundColorHeader = spreadSheet.createStyle(workbook, fontCountFlowBackground, IndexedColors.WHITE);
		spreadSheet.addStyleBackgroundColor(backgroundColorHeader, IndexedColors.BLUE);
		spreadSheet.addStyleVerticalHorizontalAlignment(workbook, backgroundColorHeader, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
		spreadSheet.wrapText(backgroundColorHeader, true);
	    		
		backgroundColorSubHeader = spreadSheet.createStyle(workbook, fontCountFlowBackground, IndexedColors.WHITE);	
		spreadSheet.addStyleBackgroundColor(backgroundColorSubHeader, IndexedColors.LIGHT_BLUE);
		spreadSheet.addStyleVerticalHorizontalAlignment(workbook, backgroundColorSubHeader, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
				
		backgroundColorSubHeader2 = spreadSheet.createStyle(workbook, fontCountFlowBackground, IndexedColors.WHITE);						
		spreadSheet.addStyleBackgroundColor(backgroundColorSubHeader2, IndexedColors.LIGHT_ORANGE);
		spreadSheet.addStyleVerticalHorizontalAlignment(workbook, backgroundColorSubHeader2, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
		spreadSheet.wrapText(backgroundColorSubHeader2, true);
				
		backgroundColorBody = spreadSheet.createStyle(workbook, fontCountFlowBody, IndexedColors.WHITE);
		spreadSheet.addStyleBackgroundColor(backgroundColorBody, IndexedColors.LIGHT_CORNFLOWER_BLUE);
		spreadSheet.addStyleVerticalHorizontalAlignment(workbook, backgroundColorBody, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
				
		backgroundColorBody2 = spreadSheet.createStyle(workbook, fontCountFlowBody2, IndexedColors.WHITE);
		spreadSheet.addStyleBackgroundColor(backgroundColorBody2, IndexedColors.LIGHT_YELLOW);
		spreadSheet.addStyleVerticalHorizontalAlignment(workbook, backgroundColorBody2, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
						
	}
	
	  public void periodFlowStyles() {
		  
			// Estilo 

			standardStyle1 = workbook.createCellStyle();				
			standardStyle1.setFont(fontBody);
			standardStyle1.setAlignment(HorizontalAlignment.CENTER);
			standardStyle1.setFillBackgroundColor(IndexedColors.WHITE.getIndex()); // Cor na seleção da célula

			subHeaderStyle = workbook.createCellStyle();									
			subHeaderStyle.setFont(fontSubHeader);				
			subHeaderStyle.setFillForegroundColor(IndexedColors.INDIGO.getIndex()); 
			subHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			subHeaderStyle.setAlignment(HorizontalAlignment.CENTER);	
			subHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Centralizar no vertical

			reportTimeStyle = workbook.createCellStyle();						
			reportTimeStyle.setWrapText(true);
			reportTimeStyle.setFont(fontReportTime);		 		
			reportTimeStyle.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
			reportTimeStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			reportTimeStyle.setAlignment(HorizontalAlignment.CENTER);	
			reportTimeStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Centralizar no vertical

			numbersTitleStyle = workbook.createCellStyle();						
			numbersTitleStyle.setWrapText(true);
			numbersTitleStyle.setFont(fontNumbersTitle);				
			numbersTitleStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
			numbersTitleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			numbersTitleStyle.setAlignment(HorizontalAlignment.CENTER);	
			numbersTitleStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Centralizar no vertical

			blueColorBackgroundStyle = workbook.createCellStyle();									
			blueColorBackgroundStyle.setWrapText(true);
			blueColorBackgroundStyle.setFont(fontBackgroundColor);				
			blueColorBackgroundStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_BLUE.getIndex());
			blueColorBackgroundStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			blueColorBackgroundStyle.setAlignment(HorizontalAlignment.CENTER);	
			blueColorBackgroundStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Centralizar no vertical

			redColorBackgroundStyle = workbook.createCellStyle();	 	 				
			redColorBackgroundStyle.setWrapText(true);
			redColorBackgroundStyle.setFont(fontBackgroundColor);				
			redColorBackgroundStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.CORAL.getIndex());
			redColorBackgroundStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			redColorBackgroundStyle.setAlignment(HorizontalAlignment.CENTER);	
			redColorBackgroundStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Centralizar no vertical

			backgroundColorBodyHeaderStyle = workbook.createCellStyle();			
			backgroundColorBodyHeaderStyle.setFont(fontBodyHeader);				
			backgroundColorBodyHeaderStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_CORNFLOWER_BLUE.getIndex());
			backgroundColorBodyHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			backgroundColorBodyHeaderStyle.setAlignment(HorizontalAlignment.CENTER);	
			backgroundColorBodyHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Centralizar no vertical	

			backgroundColorBodyHeaderStyle2 = workbook.createCellStyle();									
			backgroundColorBodyHeaderStyle2.setFont(fontBodyHeader2);				
			backgroundColorBodyHeaderStyle2.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex());
			backgroundColorBodyHeaderStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			backgroundColorBodyHeaderStyle2.setAlignment(HorizontalAlignment.CENTER);	
			backgroundColorBodyHeaderStyle2.setVerticalAlignment(VerticalAlignment.CENTER); // Centralizar no vertical		

			espaceStyle = workbook.createCellStyle();					
			espaceStyle.setFont(fontEspace);
			espaceStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.ORANGE.getIndex());
			espaceStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			espaceStyle.setAlignment(HorizontalAlignment.CENTER);	
			espaceStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Centralizar no vertical

			espaceStyle2 = workbook.createCellStyle();						
			espaceStyle2.setFont(fontEspace);
			espaceStyle2.setFillForegroundColor(HSSFColor.HSSFColorPredefined.ORANGE.getIndex());
			espaceStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			espaceStyle2.setRotation((short) 90);							
			espaceStyle2.setVerticalAlignment(VerticalAlignment.CENTER); // Centralizar no vertical							

			dayStyle = workbook.createCellStyle();			    	
			dayStyle.setFont(fontDateHeader);
			dayStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.ROYAL_BLUE.getIndex());
			dayStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			dayStyle.setAlignment(HorizontalAlignment.CENTER);	
			dayStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Centralizar no vertical				

			intervalStyle = workbook.createCellStyle();
			intervalStyle.setAlignment(HorizontalAlignment.CENTER);
			intervalStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());				
			intervalStyle.setFont(fontInterval);

			blankStyle = workbook.createCellStyle();
			blankStyle.setBorderRight(BorderStyle.THIN);
			blankStyle.setBorderLeft(BorderStyle.THIN);

			blankStyle2 = workbook.createCellStyle();				
			blankStyle2.setBorderLeft(BorderStyle.THIN);
		  		  
	  }
	  
	  public void mothlyFlowStyles() {
		  
		//Style Background color
	    monthBodybackgroundStyle = workbook.createCellStyle();
		monthBodybackgroundStyle.setFont(fontMonthBody);
		monthBodybackgroundStyle.setAlignment(HorizontalAlignment.CENTER);
		monthBodybackgroundStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
			
		indigoBackgroundStyle = workbook.createCellStyle();	 	 				
		indigoBackgroundStyle.setWrapText(true);
		indigoBackgroundStyle.setFont(fontMonthHeader);				
		indigoBackgroundStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.INDIGO.getIndex());
		indigoBackgroundStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		indigoBackgroundStyle.setAlignment(HorizontalAlignment.CENTER);	
		indigoBackgroundStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Centralizar no vertical
		indigoBackgroundStyle.setWrapText(true);
		
		monthDateTimeStyle = workbook.createCellStyle();
		monthDateTimeStyle.setFont(fontMonthDateTime);
		monthDateTimeStyle.setAlignment(HorizontalAlignment.CENTER);
		monthDateTimeStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
		
		monthBoldLeftStyle = workbook.createCellStyle();
		monthBoldLeftStyle.setFont(boldFont);
		monthBoldLeftStyle.setAlignment(HorizontalAlignment.LEFT);
		monthBoldLeftStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
		
		monthBoldCenterStyle = workbook.createCellStyle();
		monthBoldCenterStyle.setFont(boldFont);
		monthBoldCenterStyle.setAlignment(HorizontalAlignment.CENTER);
		monthBoldCenterStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
		
		monthLowDescriptStyle = spreadSheet.createStyle(workbook, fontLowDescript, IndexedColors.WHITE);
				
	  }
	
	//BORDERS
		
	public void StandardBorders() {
		
		spreadSheet.applyBorderAllStyle(headerStyle, BorderStyle.THIN);
		spreadSheet.applyBorderAllStyle(tableHeaderStyle, BorderStyle.THIN);
		spreadSheet.applyBorderAllStyle(datePeriodStyle, BorderStyle.THIN);		
		spreadSheet.applyBorderAllStyle(dateHourStyle, BorderStyle.THIN);
		spreadSheet.applyBorderAllStyle(standardStyle, BorderStyle.THIN);
		
	}		
	
	public void countFlowBorders() {
		
		//APLICAÇÃO BORDAS			
		spreadSheet.applyBorderAllStyle(headerStyle, BorderStyle.THIN);
		spreadSheet.applyBorderAllStyle(tableHeaderStyle, BorderStyle.THIN);
		spreadSheet.applyBorderAllStyle(datePeriodStyle, BorderStyle.THIN);	
		spreadSheet.applyBorderAllStyle(dateHourStyle, BorderStyle.THIN);
		spreadSheet.applyBorderAllStyle(standardStyle, BorderStyle.THIN);
				
		spreadSheet.applyBorderAllStyle(backgroundColorHeader, BorderStyle.THIN);
		spreadSheet.applyBorderAllStyle(backgroundColorSubHeader, BorderStyle.THIN);
		spreadSheet.applyBorderAllStyle(backgroundColorSubHeader2, BorderStyle.THIN);
		spreadSheet.applyBorderAllStyle(backgroundColorBody, BorderStyle.THIN);
		spreadSheet.applyBorderAllStyle(backgroundColorBody2, BorderStyle.THIN);	
		
	}
	
	  public void periodFlowBorders() {
		
		  spreadSheet.applyBorderAllStyle(subHeaderStyle, BorderStyle.THIN); 
		  spreadSheet.applyBorderAllStyle(reportTimeStyle, BorderStyle.THIN);
		  spreadSheet.applyBorderAllStyle(dayStyle, BorderStyle.THIN);
		  spreadSheet.applyBorderAllStyle(intervalStyle, BorderStyle.THIN);
		  spreadSheet.applyBorderAllStyle(standardStyle1, BorderStyle.THIN); 
		  spreadSheet.applyBorderAllStyle(redColorBackgroundStyle, BorderStyle.THIN);							
		  spreadSheet.applyBorderAllStyle(blueColorBackgroundStyle, BorderStyle.THIN);		  
		  
	  }
	  
	  public void monthlyFlowBorders() {
			
		  spreadSheet.applyBorderAllStyle(monthBodybackgroundStyle, BorderStyle.THIN); 	
		  spreadSheet.applyBorderAllStyle(monthDateTimeStyle, BorderStyle.THIN); 
		  
	  }
	
	public void StandardExcelModel(String[] columnsHeader, int registers, int range, int daysCount, String period, String currentDate, String type, String module, String logo, 
			String fileTitle, String equip, String city, String road, String km, String numLanes, String startDate, String endDate, String[] mergeCells, int[] columnsWidth, int colStartDate, int colEndDate, String[][] resultQuery ) throws Exception {
	
		dta = new DateTimeApplication(); // Métodos Date and Time	
		tm = new TranslationMethods();
		sheet = null;		
		row = null;
		
		int rowMax = 0;
		int total = 0;
		int registerLength = 0;
		
		int ini = 12;
		int startColumn = 0;
		int totalStartColumn = 2;
		int rowIni = ini ;
		int length = columnsHeader.length;
		int cellMaxCol = (length - 1);
		int cellMinCol = 0;
		int colHeaderEnd = colStartDate - 1;
						
		String intervalDate = "";
		 
		//Excel Cells - header 
		headerCells = new Cell[length];
		cellData = new Cell[length][registers];
				
		if(period.equals("24 hours")) {
		  
		   rowMax = (ini + registers); 
		   total = (rowMax + 1);			 
		   registerLength = registers;
		   cellMinCol = 1;
		   
		   System.out.println("RG"+range);
		   
		   intervalDate = localeExcel.getStringKey("excel_report_from")+": " + startDate+ "\n"+localeExcel.getStringKey("excel_report_to")+": " + endDate;
		  		
		   String sheetName = sheetNameSingle(type, module);
		   
		   sheet = workbook.createSheet(sheetName);	
		   
		 //Imagem
			spreadSheet.InsertExcelImage(workbook, sheet, logo, 0, 0, 2, 4, 1, 1, 1, 1, 1); // criar Imagem
			
			// Criar Linhas
			spreadSheet.createRows(sheet, row, 0, 4);
			
			//Criar Células
			spreadSheet.createCells(sheet, row, 0, length, 0, 3);
			
			//Mesclar Células		
			for(int i = 0; i < mergeCells.length; i++)
			   spreadSheet.mergeCells(sheet, mergeCells[i]);
			
			//Largura das Colunas		
			for(int i = 0; i < columnsWidth.length; i++)
				spreadSheet.columnsWidth(sheet, i, columnsWidth[i]);
			
			//HEADER	
			
			// Criar Linhas
			spreadSheet.createRows(sheet, row, 0, 3);
			spreadSheet.createCells(sheet, row, 0, colEndDate, 0, 3);

			spreadSheet.getCell(sheet, row, 0, 2,  fileTitle); // Título
			
			spreadSheet.getCell(sheet, row, 0, colStartDate, intervalDate); // Período
								
			spreadSheet.setStyle(sheet, row, 0, 3, headerStyle, 0, colHeaderEnd); //Aplicar Borda - Header
			spreadSheet.setStyle(sheet, row, 0, 3, datePeriodStyle, colStartDate, colEndDate); //Aplicar Borda - Header
			
			//Merge Cells
			spreadSheet.mergeCells(sheet, "A6:B6");			
			spreadSheet.mergeCells(sheet, "F6:H6");
			spreadSheet.mergeCells(sheet, "I6:J6");
																
			spreadSheet.createRow(sheet, row, 5);
			spreadSheet.createCell(sheet, row, 5, 0, localeExcel.getStringKey("excel_report_consultation_equipment"));
			spreadSheet.createCell(sheet, row, 5, 2, equip);
			spreadSheet.createCell(sheet, row, 5, 5, localeExcel.getStringKey("excel_report_consultation_date"));
			spreadSheet.createCell(sheet, row, 5, 8, " " + currentDate);

			spreadSheet.setStyle(sheet, row, 5, boldRightAlignStyle, 0);
			spreadSheet.setStyle(sheet, row, 5, boldRightAlignStyle, 5);
			spreadSheet.setStyle(sheet, row, 5, centerAlignStyle, 2);
			spreadSheet.setStyle(sheet, row, 5, leftAlignStyle, 8);

			spreadSheet.createRow(sheet, row, 6);	
			spreadSheet.createCell(sheet, row, 6, 1, localeExcel.getStringKey("excel_report_consultation_highway"));
			spreadSheet.createCell(sheet, row, 6, 2, road );
			spreadSheet.createCell(sheet, row, 6, 7, localeExcel.getStringKey("excel_report_consultation_direction"));
			spreadSheet.createCell(sheet, row, 6, 8, localeDirections.getStringKey("directions_all") );

			spreadSheet.createRow(sheet, row, 6);	
			spreadSheet.createCell(sheet, row, 6, 1, localeExcel.getStringKey("excel_report_consultation_city"));
			spreadSheet.createCell(sheet, row, 6, 2, city );
			spreadSheet.createCell(sheet, row, 6, 7, localeExcel.getStringKey("excel_report_consultation_direction"));
			spreadSheet.createCell(sheet, row, 6, 8, localeDirections.getStringKey("directions_all") );

			spreadSheet.setStyle(sheet, row, 6,  boldCenterStyle, 1);
			spreadSheet.setStyle(sheet, row, 6,  boldCenterStyle, 7);
			spreadSheet.setStyle(sheet, row, 6, centerAlignStyle, 2);
			spreadSheet.setStyle(sheet, row, 6, centerAlignStyle, 8);
							
			spreadSheet.createRow(sheet, row, 7);	
			spreadSheet.createCell(sheet, row, 7, 1, localeExcel.getStringKey("excel_report_consultation_highway"));
			spreadSheet.createCell(sheet, row, 7, 2, road );
			spreadSheet.createCell(sheet, row, 7, 7, localeExcel.getStringKey("excel_report_consultation_period"));
			spreadSheet.createCell(sheet, row, 7, 8, tm.periodName(period));
			
			spreadSheet.setStyle(sheet, row, 7,  boldCenterStyle, 1);
			spreadSheet.setStyle(sheet, row, 7,  boldCenterStyle, 7);
			spreadSheet.setStyle(sheet, row, 7, centerAlignStyle, 2);
			spreadSheet.setStyle(sheet, row, 7, centerAlignStyle, 8);
			
			spreadSheet.createRow(sheet, row, 8);	
			spreadSheet.createCell(sheet, row, 8, 1, localeExcel.getStringKey("excel_report_consultation_km"));
			spreadSheet.createCell(sheet, row, 8, 2, km );
				
			spreadSheet.setStyle(sheet, row, 8,  boldCenterStyle, 1);
			spreadSheet.setStyle(sheet, row, 8, centerAlignStyle, 2);
		
			spreadSheet.createRow(sheet, row, 9);	
			spreadSheet.createCell(sheet, row, 9, 1, localeExcel.getStringKey("excel_report_consultation_lanes"));
			spreadSheet.createCell(sheet, row, 9, 2, numLanes);
			
			spreadSheet.setStyle(sheet, row, 9,  boldCenterStyle, 1);
			spreadSheet.setStyle(sheet, row, 9, centerAlignStyle, 2);
			
			//HEADER
			
			// BODY
			spreadSheet.createRow(sheet, row, 11);
			spreadSheet.createHeaderCells(sheet, row, 11, headerCells, columnsHeader);
			spreadSheet.setStyleHeaderBody(sheet, row, 11, headerCells, length, tableHeaderStyle);

			spreadSheet.createRows(sheet, row, ini, total); // Criar o número de linhas		
					
			spreadSheet.fillDataSingle(sheet, row, cellData, resultQuery, period, startColumn, length, ini, registerLength); // Preencher a colunas
			
			spreadSheet.createCells(sheet, row, 0, length, rowMax, rowMax);			 
			spreadSheet.getCell(sheet, row, rowMax, 0, localeExcel.getStringKey("excel_report_excel_total"));
					
		    spreadSheet.totalExcel(sheet, row, period, totalStartColumn, length, rowIni, rowMax);	   
		    		    
		    spreadSheet.setStyle(sheet, row, ini, rowMax, dateHourStyle, 0, 0); 
		    spreadSheet.setStyle(sheet, row, rowMax, rowMax, tableHeaderStyle, 0, 0);
		    spreadSheet.setStyle(sheet, row, ini, rowMax, standardStyle, cellMinCol, cellMaxCol);	    
		    
		    // BODY
		   		   
		}else {
			
			  rowMax = (ini + range); 
			  total = (rowMax + 1);			  
			  registerLength = range;
			  cellMinCol = 2;
			  
			  String[] dateRange = new String[daysCount];
			  String[] sheetName = new String[daysCount];
						  
			  dateRange = dta.dateRangeForHeader(startDate, endDate, daysCount);		
			  sheetName = dta.dateRangeForSheetName(startDate, endDate, daysCount);
		  							
		for(int d = 0; d < daysCount; d++) {
		
			//SheetName
		String sheetName_ = String.valueOf(sheetName[d]); // Criação das tabs
		sheet = workbook.createSheet(sheetName_);	
						
		//Imagem
		spreadSheet.InsertExcelImage(workbook, sheet, logo, 0, 0, 2, 4, 1, 1, 1, 1, 1); // criar Imagem
		
		// Criar Linhas
		spreadSheet.createRows(sheet, row, 0, 4);
		
		//Criar Células
		spreadSheet.createCells(sheet, row, 0, length, 0, 3);
		
		//Mesclar Células		
		for(int i = 0; i < mergeCells.length; i++)
		   spreadSheet.mergeCells(sheet, mergeCells[i]);
		
		//Largura das Colunas		
		for(int i = 0; i < columnsWidth.length; i++)
			spreadSheet.columnsWidth(sheet, i, columnsWidth[i]);
		
		//HEADER	
		
		// Criar Linhas
		spreadSheet.createRows(sheet, row, 0, 3);
		spreadSheet.createCells(sheet, row, 0, colEndDate, 0, 3);

		spreadSheet.getCell(sheet, row, 0, 2,  fileTitle); // Título
		
		spreadSheet.getCell(sheet, row, 0, colStartDate, localeExcel.getStringKey("excel_report_from")+": " + dateRange[d]+ DateTimeApplication.HOUR_TIME_FORMAT_START_DATE +
		"\n"+localeExcel.getStringKey("excel_report_to")+": " + dateRange[d] + DateTimeApplication.HOUR_TIME_FORMAT_END_DATE); // Período
					
		spreadSheet.setStyle(sheet, row, 0, 3, headerStyle, 0, colHeaderEnd); //Aplicar Borda - Header
		spreadSheet.setStyle(sheet, row, 0, 3, datePeriodStyle, colStartDate, colEndDate); //Aplicar Borda - Header
		
		//Merge Cells
		spreadSheet.mergeCells(sheet, "A6:B6");	
		spreadSheet.mergeCells(sheet, "F6:H6");
		spreadSheet.mergeCells(sheet, "I6:J6");
															
		spreadSheet.createRow(sheet, row, 5);
		spreadSheet.createCell(sheet, row, 5, 0, localeExcel.getStringKey("excel_report_consultation_equipment"));
		spreadSheet.createCell(sheet, row, 5, 2, equip);
		spreadSheet.createCell(sheet, row, 5, 5, localeExcel.getStringKey("excel_report_consultation_date"));
		spreadSheet.createCell(sheet, row, 5, 8, " " + currentDate);

		spreadSheet.setStyle(sheet, row, 5, boldRightAlignStyle, 0);
		spreadSheet.setStyle(sheet, row, 5, boldRightAlignStyle, 5);
		spreadSheet.setStyle(sheet, row, 5, centerAlignStyle, 2);
		spreadSheet.setStyle(sheet, row, 5, leftAlignStyle, 8);

		spreadSheet.createRow(sheet, row, 6);	
		spreadSheet.createCell(sheet, row, 6, 1, localeExcel.getStringKey("excel_report_consultation_city"));
		spreadSheet.createCell(sheet, row, 6, 2, city );
		spreadSheet.createCell(sheet, row, 6, 7, localeExcel.getStringKey("excel_report_consultation_direction"));
		spreadSheet.createCell(sheet, row, 6, 8, localeDirections.getStringKey("directions_all") );

		spreadSheet.setStyle(sheet, row, 6,  boldCenterStyle, 1);
		spreadSheet.setStyle(sheet, row, 6,  boldCenterStyle, 7);
		spreadSheet.setStyle(sheet, row, 6, centerAlignStyle, 2);
		spreadSheet.setStyle(sheet, row, 6, centerAlignStyle, 8);
						
		spreadSheet.createRow(sheet, row, 7);	
		spreadSheet.createCell(sheet, row, 7, 1, localeExcel.getStringKey("excel_report_consultation_highway"));
		spreadSheet.createCell(sheet, row, 7, 2, road );
		spreadSheet.createCell(sheet, row, 7, 7, localeExcel.getStringKey("excel_report_consultation_period"));
		spreadSheet.createCell(sheet, row, 7, 8, tm.periodName(period));
		
		spreadSheet.setStyle(sheet, row, 7,  boldCenterStyle, 1);
		spreadSheet.setStyle(sheet, row, 7,  boldCenterStyle, 7);
		spreadSheet.setStyle(sheet, row, 7, centerAlignStyle, 2);
		spreadSheet.setStyle(sheet, row, 7, centerAlignStyle, 8);
		
		spreadSheet.createRow(sheet, row, 8);	
		spreadSheet.createCell(sheet, row, 8, 1, localeExcel.getStringKey("excel_report_consultation_km"));
		spreadSheet.createCell(sheet, row, 8, 2, km );
			
		spreadSheet.setStyle(sheet, row, 8,  boldCenterStyle, 1);
		spreadSheet.setStyle(sheet, row, 8, centerAlignStyle, 2);
	
		spreadSheet.createRow(sheet, row, 9);	
		spreadSheet.createCell(sheet, row, 9, 1, localeExcel.getStringKey("excel_report_consultation_lanes"));
		spreadSheet.createCell(sheet, row, 9, 2, numLanes);
		
		spreadSheet.setStyle(sheet, row, 9,  boldCenterStyle, 1);
		spreadSheet.setStyle(sheet, row, 9, centerAlignStyle, 2);
		
		//HEADER
		
		// BODY
		spreadSheet.createRow(sheet, row, 11);
		spreadSheet.createHeaderCells(sheet, row, 11, headerCells, columnsHeader);
		spreadSheet.setStyleHeaderBody(sheet, row, 11, headerCells, length, tableHeaderStyle);

		spreadSheet.createRows(sheet, row, ini, total); // Criar o número de linhas		
				
		spreadSheet.fillDataRange(sheet, row, cellData, resultQuery, period, startColumn, length, ini, registerLength, d, range); // Preencher a colunas
		
		spreadSheet.createCells(sheet, row, 0, length, rowMax, rowMax);			 
		spreadSheet.getCell(sheet, row, rowMax, 0, localeExcel.getStringKey("excel_report_excel_total"));
				
	    spreadSheet.totalExcel(sheet, row, period, totalStartColumn, length, rowIni, rowMax);	   
	    		    
	    spreadSheet.setStyle(sheet, row, ini, rowMax, dateHourStyle, 0, 1); 
	    spreadSheet.setStyle(sheet, row, rowMax, rowMax, tableHeaderStyle, 0, 0);
	    spreadSheet.setStyle(sheet, row, ini, rowMax, standardStyle, cellMinCol, cellMaxCol);	    
	    
	    // BODY
	    
		}
	  }				
    }
	
	
	
	public void StandardExcelModelWithoutTotal(String[] columnsHeader, int registers, int range, int daysCount, String period, String currentDate, String type, String module, String logo, 
			String fileTitle, String equip, String city, String road, String km, String numLanes, String startDate, String endDate, String[] mergeCells, int[] columnsWidth, int colStartDate, int colEndDate, String[][] resultQuery ) throws Exception {
	
		dta = new DateTimeApplication(); // Métodos Date and Time	
		tm = new TranslationMethods();
		sheet = null;		
		row = null;
		
		int rowMax = 0;		
		int registerLength = 0;
		
		int ini = 12;
		int startColumn = 0;			
		int length = columnsHeader.length;
		int cellMaxCol = (length - 1);
		int cellMinCol = 0;
		int colHeaderEnd = colStartDate - 1;
						
		String intervalDate = "";
		 
		//Excel Cells - header 
		headerCells = new Cell[length];
		cellData = new Cell[length][registers];
		
		
		if(period.equals("24 hours") || period.equals("month") || period.equals("year")) {
		  
		   rowMax = ((ini + registers) - 1); 		   	 
		   registerLength = registers;
		   cellMinCol = 1;
		   
		     intervalDate = localeExcel.getStringKey("excel_report_from")+": " + startDate+ "\n"+localeExcel.getStringKey("excel_report_to")+": " + endDate;
		  		
		     String sheetName = sheetNameSingle(type, module);
		   
		     sheet = workbook.createSheet(sheetName);	
		   
		    //Imagem
			spreadSheet.InsertExcelImage(workbook, sheet, logo, 0, 0, 2, 4, 1, 1, 1, 1, 1); // criar Imagem
			
			// Criar Linhas
			spreadSheet.createRows(sheet, row, 0, 4);
			
			//Criar Células
			spreadSheet.createCells(sheet, row, 0, length, 0, 3);
			
			//Mesclar Células		
			for(int i = 0; i < mergeCells.length; i++)
			   spreadSheet.mergeCells(sheet, mergeCells[i]);
			
			//Largura das Colunas		
			for(int i = 0; i < columnsWidth.length; i++)
				spreadSheet.columnsWidth(sheet, i, columnsWidth[i]);
			
			//HEADER	
			
			// Criar Linhas
			spreadSheet.createRows(sheet, row, 0, 3);
			spreadSheet.createCells(sheet, row, 0, colEndDate, 0, 3);

			spreadSheet.getCell(sheet, row, 0, 2,  fileTitle); // Título
			
			spreadSheet.getCell(sheet, row, 0, colStartDate, intervalDate); // Período
								
			spreadSheet.setStyle(sheet, row, 0, 3, headerStyle, 0, colHeaderEnd); //Aplicar Borda - Header
			spreadSheet.setStyle(sheet, row, 0, 3, datePeriodStyle, colStartDate, colEndDate); //Aplicar Borda - Header
			
			//Merge Cells
			spreadSheet.mergeCells(sheet, "A6:B6");			
			spreadSheet.mergeCells(sheet, "F6:H6");
			spreadSheet.mergeCells(sheet, "I6:J6");
																			
			spreadSheet.createRow(sheet, row, 5);
			spreadSheet.createCell(sheet, row, 5, 0, localeExcel.getStringKey("excel_report_consultation_equipment"));
			spreadSheet.createCell(sheet, row, 5, 2, equip);
			spreadSheet.createCell(sheet, row, 5, 5, localeExcel.getStringKey("excel_report_consultation_date"));
			spreadSheet.createCell(sheet, row, 5, 8, " " + currentDate);

			spreadSheet.setStyle(sheet, row, 5, boldRightAlignStyle, 0);
			spreadSheet.setStyle(sheet, row, 5, boldRightAlignStyle, 5);
			spreadSheet.setStyle(sheet, row, 5, centerAlignStyle, 2);
			spreadSheet.setStyle(sheet, row, 5, leftAlignStyle, 8);

			spreadSheet.createRow(sheet, row, 6);	
			spreadSheet.createCell(sheet, row, 6, 1, localeExcel.getStringKey("excel_report_consultation_city"));
			spreadSheet.createCell(sheet, row, 6, 2, city );
			spreadSheet.createCell(sheet, row, 6, 7, localeExcel.getStringKey("excel_report_consultation_direction"));
			spreadSheet.createCell(sheet, row, 6, 8, localeDirections.getStringKey("directions_all") );

			spreadSheet.setStyle(sheet, row, 6,  boldCenterStyle, 1);
			spreadSheet.setStyle(sheet, row, 6,  boldCenterStyle, 7);
			spreadSheet.setStyle(sheet, row, 6, centerAlignStyle, 2);
			spreadSheet.setStyle(sheet, row, 6, centerAlignStyle, 8);
							
			spreadSheet.createRow(sheet, row, 7);	
			spreadSheet.createCell(sheet, row, 7, 1, localeExcel.getStringKey("excel_report_consultation_highway"));
			spreadSheet.createCell(sheet, row, 7, 2, road );
			spreadSheet.createCell(sheet, row, 7, 7, localeExcel.getStringKey("excel_report_consultation_period"));
			spreadSheet.createCell(sheet, row, 7, 8, tm.periodName(period));
			
			spreadSheet.setStyle(sheet, row, 7,  boldCenterStyle, 1);
			spreadSheet.setStyle(sheet, row, 7,  boldCenterStyle, 7);
			spreadSheet.setStyle(sheet, row, 7, centerAlignStyle, 2);
			spreadSheet.setStyle(sheet, row, 7, centerAlignStyle, 8);
			
			spreadSheet.createRow(sheet, row, 8);	
			spreadSheet.createCell(sheet, row, 8, 1, localeExcel.getStringKey("excel_report_consultation_km"));
			spreadSheet.createCell(sheet, row, 8, 2, km );
				
			spreadSheet.setStyle(sheet, row, 8,  boldCenterStyle, 1);
			spreadSheet.setStyle(sheet, row, 8, centerAlignStyle, 2);
		
			spreadSheet.createRow(sheet, row, 9);	
			spreadSheet.createCell(sheet, row, 9, 1, localeExcel.getStringKey("excel_report_consultation_lanes"));
			spreadSheet.createCell(sheet, row, 9, 2, numLanes);
			
			spreadSheet.setStyle(sheet, row, 9,  boldCenterStyle, 1);
			spreadSheet.setStyle(sheet, row, 9, centerAlignStyle, 2);
			
			//HEADER
			
			// BODY
			spreadSheet.createRow(sheet, row, 11);
			spreadSheet.createHeaderCells(sheet, row, 11, headerCells, columnsHeader);
			spreadSheet.setStyleHeaderBody(sheet, row, 11, headerCells, length, tableHeaderStyle);

			spreadSheet.createRows(sheet, row, ini, rowMax); // Criar o número de linhas
			
			if(period.equals("month"))										
			spreadSheet.fillDataSingleMonthReport(sheet, row, cellData, resultQuery, period, startColumn, length, ini, registerLength); // Preencher a colunas
						
			else spreadSheet.fillDataSingle(sheet, row, cellData, resultQuery, period, startColumn, length, ini, registerLength); // Preencher a colunas
						
		    spreadSheet.setStyle(sheet, row, ini, rowMax, dateHourStyle, 0, 0); 		
		    spreadSheet.setStyle(sheet, row, ini, rowMax, standardStyle, cellMinCol, cellMaxCol);	    
		    
		    // BODY
		   
		   
		}else {
			
			  rowMax = ((ini + range) - 1); 			 	  
			  registerLength = range;
			  cellMinCol = 2;
			  
			  String[] dateRange = new String[daysCount];
			  String[] sheetName = new String[daysCount];
						  
			  dateRange = dta.dateRangeForHeader(startDate, endDate, daysCount);		
			  sheetName = dta.dateRangeForSheetName(startDate, endDate, daysCount);
		  							
		for(int d = 0; d < daysCount; d++) {
		
			//SheetName
		String sheetName_ = String.valueOf(sheetName[d]); // Criação das tabs
		sheet = workbook.createSheet(sheetName_);	
						
		//Imagem
		spreadSheet.InsertExcelImage(workbook, sheet, logo, 0, 0, 2, 4, 1, 1, 1, 1, 1); // criar Imagem
		
		// Criar Linhas
		spreadSheet.createRows(sheet, row, 0, 4);
		
		//Criar Células
		spreadSheet.createCells(sheet, row, 0, length, 0, 3);
		
		//Mesclar Células		
		for(int i = 0; i < mergeCells.length; i++)
		   spreadSheet.mergeCells(sheet, mergeCells[i]);
		
		//Largura das Colunas		
		for(int i = 0; i < columnsWidth.length; i++)
			spreadSheet.columnsWidth(sheet, i, columnsWidth[i]);
		
		//HEADER	
		
		// Criar Linhas
		spreadSheet.createRows(sheet, row, 0, 3);
		spreadSheet.createCells(sheet, row, 0, colEndDate, 0, 3);

		spreadSheet.getCell(sheet, row, 0, 2,  fileTitle); // Título
		
		spreadSheet.getCell(sheet, row, 0, colStartDate, localeExcel.getStringKey("excel_report_from")+": " + dateRange[d]+ DateTimeApplication.HOUR_TIME_FORMAT_START_DATE +
		"\n"+localeExcel.getStringKey("excel_report_to")+": " + dateRange[d] + DateTimeApplication.HOUR_TIME_FORMAT_END_DATE); // Período
					
		spreadSheet.setStyle(sheet, row, 0, 3, headerStyle, 0, colHeaderEnd); //Aplicar Borda - Header
		spreadSheet.setStyle(sheet, row, 0, 3, datePeriodStyle, colStartDate, colEndDate); //Aplicar Borda - Header
		
		//Merge Cells
		spreadSheet.mergeCells(sheet, "A6:B6");	
		spreadSheet.mergeCells(sheet, "F6:H6");
		spreadSheet.mergeCells(sheet, "I6:J6");
															
		spreadSheet.createRow(sheet, row, 5);
		spreadSheet.createCell(sheet, row, 5, 0, localeExcel.getStringKey("excel_report_consultation_equipment"));
		spreadSheet.createCell(sheet, row, 5, 2, equip);
		spreadSheet.createCell(sheet, row, 5, 5, localeExcel.getStringKey("excel_report_consultation_date"));
		spreadSheet.createCell(sheet, row, 5, 8, " " + currentDate);

		spreadSheet.setStyle(sheet, row, 5, boldRightAlignStyle, 0);
		spreadSheet.setStyle(sheet, row, 5, boldRightAlignStyle, 5);
		spreadSheet.setStyle(sheet, row, 5, centerAlignStyle, 2);
		spreadSheet.setStyle(sheet, row, 5, leftAlignStyle, 8);

		spreadSheet.createRow(sheet, row, 6);	
		spreadSheet.createCell(sheet, row, 6, 1, localeExcel.getStringKey("excel_report_consultation_city"));
		spreadSheet.createCell(sheet, row, 6, 2, city );
		spreadSheet.createCell(sheet, row, 6, 7, localeExcel.getStringKey("excel_report_consultation_direction"));
		spreadSheet.createCell(sheet, row, 6, 8, localeDirections.getStringKey("directions_all") );

		spreadSheet.setStyle(sheet, row, 6,  boldCenterStyle, 1);
		spreadSheet.setStyle(sheet, row, 6,  boldCenterStyle, 7);
		spreadSheet.setStyle(sheet, row, 6, centerAlignStyle, 2);
		spreadSheet.setStyle(sheet, row, 6, centerAlignStyle, 8);
						
		spreadSheet.createRow(sheet, row, 7);	
		spreadSheet.createCell(sheet, row, 7, 1, localeExcel.getStringKey("excel_report_consultation_highway"));
		spreadSheet.createCell(sheet, row, 7, 2, road );
		spreadSheet.createCell(sheet, row, 7, 7, localeExcel.getStringKey("excel_report_consultation_period"));
		spreadSheet.createCell(sheet, row, 7, 8, tm.periodName(period));
		
		spreadSheet.setStyle(sheet, row, 7,  boldCenterStyle, 1);
		spreadSheet.setStyle(sheet, row, 7,  boldCenterStyle, 7);
		spreadSheet.setStyle(sheet, row, 7, centerAlignStyle, 2);
		spreadSheet.setStyle(sheet, row, 7, centerAlignStyle, 8);
		
		spreadSheet.createRow(sheet, row, 8);	
		spreadSheet.createCell(sheet, row, 8, 1, localeExcel.getStringKey("excel_report_consultation_km"));
		spreadSheet.createCell(sheet, row, 8, 2, km );
			
		spreadSheet.setStyle(sheet, row, 8,  boldCenterStyle, 1);
		spreadSheet.setStyle(sheet, row, 8, centerAlignStyle, 2);
	
		spreadSheet.createRow(sheet, row, 9);	
		spreadSheet.createCell(sheet, row, 9, 1, localeExcel.getStringKey("excel_report_consultation_lanes"));
		spreadSheet.createCell(sheet, row, 9, 2, numLanes);
		
		spreadSheet.setStyle(sheet, row, 9,  boldCenterStyle, 1);
		spreadSheet.setStyle(sheet, row, 9, centerAlignStyle, 2);
		
		//HEADER
		
		// BODY
		spreadSheet.createRow(sheet, row, 11);
		spreadSheet.createHeaderCells(sheet, row, 11, headerCells, columnsHeader);
		spreadSheet.setStyleHeaderBody(sheet, row, 11, headerCells, length, tableHeaderStyle);

		spreadSheet.createRows(sheet, row, ini, rowMax); // Criar o número de linhas		
				
		spreadSheet.fillDataRange(sheet, row, cellData, resultQuery, period, startColumn, length, ini, registerLength, d, range); // Preencher a colunas
			    		    
	    spreadSheet.setStyle(sheet, row, ini, rowMax, dateHourStyle, 0, 1); 	   
	    spreadSheet.setStyle(sheet, row, ini, rowMax, standardStyle, cellMinCol, cellMaxCol);	    
	    
	    // BODY
	    
		}
	  }				
    }
	
	
	public void ExcelModelDirections(String[] columnsHeader, int registers, int range, int daysCount, String period, String currentDate, String type, String module, String logo, 
			String fileTitle, String equip, String city, String road, String km, String numLanes, String direction1, String direction2, String startDate, String endDate, String[] mergeCells, int[] columnsWidth, int colStartDate, int colEndDate, String[][] resultQuery ) throws Exception {
	
		dta = new DateTimeApplication(); // Métodos Date and Time	
		tm = new TranslationMethods();
		sheet = null;		
		row = null;
		
		int rowMax = 0;		
		int registerLength = 0;
		
		int ini = 12;
		int startColumn = 0;			
		int length = columnsHeader.length;
		int cellMaxCol = (length - 1);
		int cellMinCol = 0;
		int colHeaderEnd = colStartDate - 1;
		int dir1Pos = 0;
		int dir2Pos = 0;
		int total = 0;	
			
		int rowHeaderDir1 = 0; 
		int rowDataDir1 = 0;
		int rowMaxDir1 = 0;
		int totalDir1 = 0;
		
		int rowMaxDir2 = 0;
		int rowDataDir2 = 0;
		int rowHeaderDir2 = 0;		
		int totalDir2 = 0;
						
		String intervalDate = "";
		 
		//Excel Cells - header 
		headerCells = new Cell[length];
		cellData = new Cell[length][registers];
				
		if(period.equals("24 hours")) {
		  
		   rowMax = (ini + registers); 		   	 
		   registerLength = registers;
		   total = (rowMax + 1);
		   cellMinCol = 1;
		   
		   dir1Pos = (total + 2);		   
		   rowHeaderDir1 = (dir1Pos + 2);
		   rowDataDir1 = (rowHeaderDir1 + 1);
		   rowMaxDir1 =  (rowDataDir1 + registers); 
		   totalDir1 = (rowMaxDir1 + 1);
		   
		   dir2Pos = (totalDir1 + 2);
		   rowHeaderDir2 = (dir2Pos + 2);
		   rowDataDir2 = (rowHeaderDir2 + 1);
		   rowMaxDir2 = (rowDataDir2 + registers); 
		   totalDir2 = (rowMaxDir2 + 1);
		   		   
		   intervalDate = localeExcel.getStringKey("excel_report_from")+": " + startDate+ "\n"+localeExcel.getStringKey("excel_report_to")+": " + endDate;
		  		
		   String sheetName = sheetNameSingle(type, module);
		   
		   sheet = workbook.createSheet(sheetName);	
		   
		    //Imagem
			spreadSheet.InsertExcelImage(workbook, sheet, logo, 0, 0, 2, 4, 1, 1, 1, 1, 1); // criar Imagem
			
			// Criar Linhas
			spreadSheet.createRows(sheet, row, 0, 4);
			
			//Criar Células
			spreadSheet.createCells(sheet, row, 0, length, 0, 3);
			
			//Mesclar Células		
			for(int i = 0; i < mergeCells.length; i++)
			   spreadSheet.mergeCells(sheet, mergeCells[i]);
			
			//Largura das Colunas		
			for(int i = 0; i < columnsWidth.length; i++)
				spreadSheet.columnsWidth(sheet, i, columnsWidth[i]);
			
			//HEADER	
			
			// Criar Linhas
			spreadSheet.createRows(sheet, row, 0, 3);
			spreadSheet.createCells(sheet, row, 0, colEndDate, 0, 3);

			spreadSheet.getCell(sheet, row, 0, 2,  fileTitle); // Título
			
			spreadSheet.getCell(sheet, row, 0, colStartDate, intervalDate); // Período
								
			spreadSheet.setStyle(sheet, row, 0, 3, headerStyle, 0, colHeaderEnd); //Aplicar Borda - Header
			spreadSheet.setStyle(sheet, row, 0, 3, datePeriodStyle, colStartDate, colEndDate); //Aplicar Borda - Header
			
			//Merge Cells
			spreadSheet.mergeCells(sheet, "A6:B6");			
			spreadSheet.mergeCells(sheet, "F6:H6");
			spreadSheet.mergeCells(sheet, "I6:J6");
			spreadSheet.mergeCells(sheet, "C7:D7");
			spreadSheet.mergeCells(sheet, "C8:D8");
																
			spreadSheet.createRow(sheet, row, 5);
			spreadSheet.createCell(sheet, row, 5, 0, localeExcel.getStringKey("excel_report_consultation_equipment"));
			spreadSheet.createCell(sheet, row, 5, 2, equip);
			spreadSheet.createCell(sheet, row, 5, 5, localeExcel.getStringKey("excel_report_consultation_date"));
			spreadSheet.createCell(sheet, row, 5, 8, " " + currentDate);

			spreadSheet.setStyle(sheet, row, 5, boldRightAlignStyle, 0);
			spreadSheet.setStyle(sheet, row, 5, boldRightAlignStyle, 5);
			spreadSheet.setStyle(sheet, row, 5, centerAlignStyle, 2);
			spreadSheet.setStyle(sheet, row, 5, leftAlignStyle, 8);

			spreadSheet.createRow(sheet, row, 6);	
			spreadSheet.createCell(sheet, row, 6, 1, localeExcel.getStringKey("excel_report_consultation_city"));
			spreadSheet.createCell(sheet, row, 6, 2, city );
			spreadSheet.createCell(sheet, row, 6, 7, localeExcel.getStringKey("excel_report_consultation_direction"));
			spreadSheet.createCell(sheet, row, 6, 8, localeDirections.getStringKey("directions_all"));

			spreadSheet.setStyle(sheet, row, 6,  boldCenterStyle, 1);
			spreadSheet.setStyle(sheet, row, 6,  boldCenterStyle, 7);
			spreadSheet.setStyle(sheet, row, 6, centerAlignStyle, 2);
			spreadSheet.setStyle(sheet, row, 6, centerAlignStyle, 8);
							
			spreadSheet.createRow(sheet, row, 7);	
			spreadSheet.createCell(sheet, row, 7, 1, localeExcel.getStringKey("excel_report_consultation_highway"));
			spreadSheet.createCell(sheet, row, 7, 2, road );
			spreadSheet.createCell(sheet, row, 7, 7, localeExcel.getStringKey("excel_report_consultation_period"));
			spreadSheet.createCell(sheet, row, 7, 8, tm.periodName(period));
			
			spreadSheet.setStyle(sheet, row, 7,  boldCenterStyle, 1);
			spreadSheet.setStyle(sheet, row, 7,  boldCenterStyle, 7);
			spreadSheet.setStyle(sheet, row, 7, centerAlignStyle, 2);
			spreadSheet.setStyle(sheet, row, 7, centerAlignStyle, 8);
			
			spreadSheet.createRow(sheet, row, 8);	
			spreadSheet.createCell(sheet, row, 8, 1, localeExcel.getStringKey("excel_report_consultation_km"));
			spreadSheet.createCell(sheet, row, 8, 2, km );
				
			spreadSheet.setStyle(sheet, row, 8,  boldCenterStyle, 1);
			spreadSheet.setStyle(sheet, row, 8, centerAlignStyle, 2);
		
			spreadSheet.createRow(sheet, row, 9);	
			spreadSheet.createCell(sheet, row, 9, 1, localeExcel.getStringKey("excel_report_consultation_lanes"));
			spreadSheet.createCell(sheet, row, 9, 2, numLanes);
			
			spreadSheet.setStyle(sheet, row, 9,  boldCenterStyle, 1);
			spreadSheet.setStyle(sheet, row, 9, centerAlignStyle, 2);
			
			//HEADER
			
			// BODY
		   spreadSheet.createRow(sheet, row, 11);
		   spreadSheet.createHeaderCells(sheet, row, 11, headerCells, columnsHeader);
		   spreadSheet.setStyleHeaderBody(sheet, row, 11, headerCells, length, tableHeaderStyle);
		   
		   /* FIRST GROUP DATA */

		   spreadSheet.createRows(sheet, row, ini, total); // Criar o número de linhas	
			
		   spreadSheet.fillDataSingleDirections(sheet, row, cellData, resultQuery, period, startColumn, length, ini, registerLength, 1); // Preencher a colunas
		   						
		   spreadSheet.createCells(sheet, row, 0, length, rowMax, rowMax);			 
		   spreadSheet.getCell(sheet, row, rowMax, 0, localeExcel.getStringKey("excel_report_excel_total"));
					
		   spreadSheet.totalExcel(sheet, row, period, startColumn, length, ini, rowMax);	   
		    		 
		   spreadSheet.setStyle(sheet, row, ini, rowMax, dateHourStyle, 0, 0);	
		   spreadSheet.setStyle(sheet, row, rowMax, rowMax, tableHeaderStyle, 0, 0);
		   spreadSheet.setStyle(sheet, row, ini, rowMax, standardStyle, cellMinCol, cellMaxCol);
		   
		   /* SECOND GROUP DATA */
				   	   
		   spreadSheet.createRow(sheet, row, dir1Pos);	
		   spreadSheet.createCell(sheet, row, dir1Pos, 7, localeExcel.getStringKey("excel_report_consultation_direction"));
		   spreadSheet.createCell(sheet, row, dir1Pos, 8, direction1 );
		
		   spreadSheet.setStyle(sheet, row, dir1Pos,  boldCenterStyle, 7);		
		   spreadSheet.setStyle(sheet, row, dir1Pos, centerAlignStyle, 8);
							
			spreadSheet.createRow(sheet, row, rowHeaderDir1);
			spreadSheet.createHeaderCells(sheet, row, rowHeaderDir1, headerCells, columnsHeader);
			spreadSheet.setStyleHeaderBody(sheet, row, rowHeaderDir1, headerCells, length, tableHeaderStyle);
			
			spreadSheet.createRows(sheet, row,  rowDataDir1, totalDir1); // Criar o número de linhas	
			
			spreadSheet.fillDataSingleDirections(sheet, row, cellData, resultQuery, period, startColumn, length, rowDataDir1, registerLength, 2); // Preencher a colunas
		   				
			spreadSheet.createCells(sheet, row, 0, length, rowMaxDir1, rowMaxDir1);			 
			spreadSheet.getCell(sheet, row, rowMaxDir1, 0, localeExcel.getStringKey("excel_report_excel_total"));
						
			spreadSheet.totalExcel(sheet, row, period, startColumn, length, rowDataDir1, rowMaxDir1);	   
			    		 
			spreadSheet.setStyle(sheet, row, rowDataDir1, rowMaxDir1, dateHourStyle, 0, 0);	
			spreadSheet.setStyle(sheet, row, rowMaxDir1, rowMaxDir1, tableHeaderStyle, 0, 0);
			spreadSheet.setStyle(sheet, row, rowDataDir1, rowMaxDir1, standardStyle, cellMinCol, cellMaxCol);
			
			 /* THIRD GROUP DATA */
						
			 spreadSheet.createRow(sheet, row, dir2Pos);	
			 spreadSheet.createCell(sheet, row, dir2Pos, 7, localeExcel.getStringKey("excel_report_consultation_direction"));
			 spreadSheet.createCell(sheet, row, dir2Pos, 8, direction2);
			
			 spreadSheet.setStyle(sheet, row, dir2Pos,  boldCenterStyle, 7);		
			 spreadSheet.setStyle(sheet, row, dir2Pos, centerAlignStyle, 8);
								
		     spreadSheet.createRow(sheet, row, rowHeaderDir2);
		     spreadSheet.createHeaderCells(sheet, row, rowHeaderDir2, headerCells, columnsHeader);
			 spreadSheet.setStyleHeaderBody(sheet, row, rowHeaderDir2, headerCells, length, tableHeaderStyle);
				
			 spreadSheet.createRows(sheet, row,  rowDataDir2, totalDir2); // Criar o número de linhas	
				
			 spreadSheet.fillDataSingleDirections(sheet, row, cellData, resultQuery, period, startColumn, length, rowDataDir2, registerLength, 3); // Preencher a colunas
			   				
			 spreadSheet.createCells(sheet, row, 0, length, rowMaxDir2, rowMaxDir2);			 
			 spreadSheet.getCell(sheet, row, rowMaxDir2, 0, localeExcel.getStringKey("excel_report_excel_total"));
							
			 spreadSheet.totalExcel(sheet, row, period, startColumn, length, rowDataDir2, rowMaxDir2);	   
				    		 
			 spreadSheet.setStyle(sheet, row, rowDataDir2, rowMaxDir2, dateHourStyle, 0, 0);	
			 spreadSheet.setStyle(sheet, row, rowMaxDir2, rowMaxDir2, tableHeaderStyle, 0, 0);
			 spreadSheet.setStyle(sheet, row, rowDataDir2, rowMaxDir2, standardStyle, cellMinCol, cellMaxCol);
							
		} else {
					  
		  rowMax = (ini + range); 
		  total = (rowMax + 1);			  
		  registerLength = range;
		  cellMinCol = 2;
				   
		   dir1Pos = (total + 2);		   
		   rowHeaderDir1 = (dir1Pos + 2);
		   rowDataDir1 = (rowHeaderDir1 + 1);
		   rowMaxDir1 =  (rowDataDir1 + range); 
		   totalDir1 = (rowMaxDir1 + 1);
		   
		   dir2Pos = (totalDir1 + 2);
		   rowHeaderDir2 = (dir2Pos + 2);
		   rowDataDir2 = (rowHeaderDir2 + 1);
		   rowMaxDir2 = (rowDataDir2 + range); 
		   totalDir2 = (rowMaxDir2 + 1);
		  
		  String[] dateRange = new String[daysCount];
		  String[] sheetName = new String[daysCount];
					  
		  dateRange = dta.dateRangeForHeader(startDate, endDate, daysCount);		
		  sheetName = dta.dateRangeForSheetName(startDate, endDate, daysCount);
	  							
	      for(int d = 0; d < daysCount; d++) {
	
		  //SheetName
	      String sheetName_ = String.valueOf(sheetName[d]); // Criação das tabs
	       sheet = workbook.createSheet(sheetName_);	
					
	    //Imagem
	   spreadSheet.InsertExcelImage(workbook, sheet, logo, 0, 0, 2, 4, 1, 1, 1, 1, 1); // criar Imagem
	
	    // Criar Linhas
	   spreadSheet.createRows(sheet, row, 0, 4);
	
	   //Criar Células
	   spreadSheet.createCells(sheet, row, 0, length, 0, 3);
	
	   //Mesclar Células		
	   for(int i = 0; i < mergeCells.length; i++)
	   spreadSheet.mergeCells(sheet, mergeCells[i]);
	
	   //Largura das Colunas		
	   for(int i = 0; i < columnsWidth.length; i++)
	   spreadSheet.columnsWidth(sheet, i, columnsWidth[i]);
	
	  //HEADER	
	
	  // Criar Linhas
	  spreadSheet.createRows(sheet, row, 0, 3);
	  spreadSheet.createCells(sheet, row, 0, colEndDate, 0, 3);

	  spreadSheet.getCell(sheet, row, 0, 2,  fileTitle); // Título
	
	  spreadSheet.getCell(sheet, row, 0, colStartDate, localeExcel.getStringKey("excel_report_from")+": " + dateRange[d]+ DateTimeApplication.HOUR_TIME_FORMAT_START_DATE +
	  "\n"+localeExcel.getStringKey("excel_report_to")+": " + dateRange[d] + DateTimeApplication.HOUR_TIME_FORMAT_END_DATE); // Período
				
	  spreadSheet.setStyle(sheet, row, 0, 3, headerStyle, 0, colHeaderEnd); //Aplicar Borda - Header
	  spreadSheet.setStyle(sheet, row, 0, 3, datePeriodStyle, colStartDate, colEndDate); //Aplicar Borda - Header
	
	  //Merge Cells
	  spreadSheet.mergeCells(sheet, "A6:B6");	
	  spreadSheet.mergeCells(sheet, "F6:H6");
	  spreadSheet.mergeCells(sheet, "I6:J6");
	  spreadSheet.mergeCells(sheet, "C7:D7");
	  spreadSheet.mergeCells(sheet, "C8:D8");
														
	  spreadSheet.createRow(sheet, row, 5);
	  spreadSheet.createCell(sheet, row, 5, 0, localeExcel.getStringKey("excel_report_consultation_equipment"));
	  spreadSheet.createCell(sheet, row, 5, 2, equip);
	  spreadSheet.createCell(sheet, row, 5, 5, localeExcel.getStringKey("excel_report_consultation_date"));
	  spreadSheet.createCell(sheet, row, 5, 8, " " + currentDate);

	  spreadSheet.setStyle(sheet, row, 5, boldRightAlignStyle, 0);
	  spreadSheet.setStyle(sheet, row, 5, boldRightAlignStyle, 5);
	  spreadSheet.setStyle(sheet, row, 5, centerAlignStyle, 2);
	  spreadSheet.setStyle(sheet, row, 5, leftAlignStyle, 8);

	  spreadSheet.createRow(sheet, row, 6);	
	  spreadSheet.createCell(sheet, row, 6, 1, localeExcel.getStringKey("excel_report_consultation_city"));
	  spreadSheet.createCell(sheet, row, 6, 2, city );
	  spreadSheet.createCell(sheet, row, 6, 7, localeExcel.getStringKey("excel_report_consultation_direction"));
	  spreadSheet.createCell(sheet, row, 6, 8, localeDirections.getStringKey("directions_all") );

	  spreadSheet.setStyle(sheet, row, 6,  boldCenterStyle, 1);
	  spreadSheet.setStyle(sheet, row, 6,  boldCenterStyle, 7);
	  spreadSheet.setStyle(sheet, row, 6, centerAlignStyle, 2);
	  spreadSheet.setStyle(sheet, row, 6, centerAlignStyle, 8);
					
	  spreadSheet.createRow(sheet, row, 7);	
	  spreadSheet.createCell(sheet, row, 7, 1, localeExcel.getStringKey("excel_report_consultation_highway"));
	  spreadSheet.createCell(sheet, row, 7, 2, road );
	  spreadSheet.createCell(sheet, row, 7, 7, localeExcel.getStringKey("excel_report_consultation_period"));
	  spreadSheet.createCell(sheet, row, 7, 8, tm.periodName(period));
	
	  spreadSheet.setStyle(sheet, row, 7,  boldCenterStyle, 1);
	  spreadSheet.setStyle(sheet, row, 7,  boldCenterStyle, 7);
	  spreadSheet.setStyle(sheet, row, 7, centerAlignStyle, 2);
	  spreadSheet.setStyle(sheet, row, 7, centerAlignStyle, 8);
	
	  spreadSheet.createRow(sheet, row, 8);	
	  spreadSheet.createCell(sheet, row, 8, 1, localeExcel.getStringKey("excel_report_consultation_km"));
	  spreadSheet.createCell(sheet, row, 8, 2, km );
		
	  spreadSheet.setStyle(sheet, row, 8,  boldCenterStyle, 1);
	  spreadSheet.setStyle(sheet, row, 8, centerAlignStyle, 2);

	  spreadSheet.createRow(sheet, row, 9);	
	  spreadSheet.createCell(sheet, row, 9, 1, localeExcel.getStringKey("excel_report_consultation_lanes"));
	  spreadSheet.createCell(sheet, row, 9, 2, numLanes);
	
	  spreadSheet.setStyle(sheet, row, 9,  boldCenterStyle, 1);
	  spreadSheet.setStyle(sheet, row, 9, centerAlignStyle, 2);
	
	   //HEADER
	
	   // BODY
	  
	   spreadSheet.createRow(sheet, row, 11);
	   spreadSheet.createHeaderCells(sheet, row, 11, headerCells, columnsHeader);
	   spreadSheet.setStyleHeaderBody(sheet, row, 11, headerCells, length, tableHeaderStyle);
	   
	   /* FIRST GROUP DATA */

	   spreadSheet.createRows(sheet, row, ini, total); // Criar o número de linhas	
		
	   spreadSheet.fillDataRangeDirections(sheet, row, cellData, resultQuery, period, startColumn, length, ini, registerLength, d, range, 1); // Preencher a colunas
	   						
	   spreadSheet.createCells(sheet, row, 0, length, rowMax, rowMax);			 
	   spreadSheet.getCell(sheet, row, rowMax, 0, localeExcel.getStringKey("excel_report_excel_total"));
				
	   spreadSheet.totalExcel(sheet, row, period, startColumn, length, ini, rowMax);	   
	    		 
	   spreadSheet.setStyle(sheet, row, ini, rowMax, dateHourStyle, 0, 1);	
	   spreadSheet.setStyle(sheet, row, rowMax, rowMax, tableHeaderStyle, 0, 1);
	   spreadSheet.setStyle(sheet, row, ini, rowMax, standardStyle, cellMinCol, cellMaxCol);
	   
	   /* SECOND GROUP DATA */
			   	   
	   spreadSheet.createRow(sheet, row, dir1Pos);	
	   spreadSheet.createCell(sheet, row, dir1Pos, 7, localeExcel.getStringKey("excel_report_consultation_direction"));
	   spreadSheet.createCell(sheet, row, dir1Pos, 8, direction1);
	
	   spreadSheet.setStyle(sheet, row, dir1Pos,  boldCenterStyle, 7);		
	   spreadSheet.setStyle(sheet, row, dir1Pos, centerAlignStyle, 8);
						
	   spreadSheet.createRow(sheet, row, rowHeaderDir1);
	   spreadSheet.createHeaderCells(sheet, row, rowHeaderDir1, headerCells, columnsHeader);
	   spreadSheet.setStyleHeaderBody(sheet, row, rowHeaderDir1, headerCells, length, tableHeaderStyle);
		
	   spreadSheet.createRows(sheet, row,  rowDataDir1, totalDir1); // Criar o número de linhas	
		
	   spreadSheet.fillDataRangeDirections(sheet, row, cellData, resultQuery, period, startColumn, length, rowDataDir1, registerLength, d, range, 2); // Preencher a colunas
	   			
	   spreadSheet.createCells(sheet, row, 0, length, rowMaxDir1, rowMaxDir1);			 
	   spreadSheet.getCell(sheet, row, rowMaxDir1, 0, localeExcel.getStringKey("excel_report_excel_total"));
					
	   spreadSheet.totalExcel(sheet, row, period, startColumn, length, rowDataDir1, rowMaxDir1);	   
		    		 
	   spreadSheet.setStyle(sheet, row, rowDataDir1, rowMaxDir1, dateHourStyle, 0, 1);	
	   spreadSheet.setStyle(sheet, row, rowMaxDir1, rowMaxDir1, tableHeaderStyle, 0, 1);
	   spreadSheet.setStyle(sheet, row, rowDataDir1, rowMaxDir1, standardStyle, cellMinCol, cellMaxCol);
		
	    /* THIRD GROUP DATA */
					
	   spreadSheet.createRow(sheet, row, dir2Pos);	
	   spreadSheet.createCell(sheet, row, dir2Pos, 7, localeExcel.getStringKey("excel_report_consultation_direction"));
	   spreadSheet.createCell(sheet, row, dir2Pos, 8, direction2);
		
	   spreadSheet.setStyle(sheet, row, dir2Pos,  boldCenterStyle, 7);		
	   spreadSheet.setStyle(sheet, row, dir2Pos, centerAlignStyle, 8);
							
	   spreadSheet.createRow(sheet, row, rowHeaderDir2);
	   spreadSheet.createHeaderCells(sheet, row, rowHeaderDir2, headerCells, columnsHeader);
	   spreadSheet.setStyleHeaderBody(sheet, row, rowHeaderDir2, headerCells, length, tableHeaderStyle);
			
	   spreadSheet.createRows(sheet, row,  rowDataDir2, totalDir2); // Criar o número de linhas	
				
	   spreadSheet.fillDataRangeDirections(sheet, row, cellData, resultQuery, period, startColumn, length, rowDataDir2, registerLength, d, range, 3); // Preencher a colunas
	   
	   spreadSheet.createCells(sheet, row, 0, length, rowMaxDir2, rowMaxDir2);			 
	   spreadSheet.getCell(sheet, row, rowMaxDir2, 0, localeExcel.getStringKey("excel_report_excel_total"));
						
	   spreadSheet.totalExcel(sheet, row, period, startColumn, length, rowDataDir2, rowMaxDir2);	   
			    		 
	   spreadSheet.setStyle(sheet, row, rowDataDir2, rowMaxDir2, dateHourStyle, 0, 1);	
	   spreadSheet.setStyle(sheet, row, rowMaxDir2, rowMaxDir2, tableHeaderStyle, 0, 1);
	   spreadSheet.setStyle(sheet, row, rowDataDir2, rowMaxDir2, standardStyle, cellMinCol, cellMaxCol); 
  
	   }
     }				
   }
	
	public void ExcelModelDirectionsSubHeader(String[] columnsHeader, int registers, int range, int daysCount, String period, String currentDate, String type, String module, String logo, 
			String fileTitle, String equip, String city, String road, String km, String numLanes, String direction1, String direction2, String startDate, String endDate, String[] mergeCells, int[] columnsWidth, int colStartDate, int colEndDate, String[][] resultQuery ) throws Exception {
	
		dta = new DateTimeApplication(); // Métodos Date and Time	
		tm = new TranslationMethods();
		sheet = null;		
		row = null;
		
		int rowMax = 0;		
		int registerLength = 0;
		
		int ini = 13;
		int startColumn = 0;			
		int length = columnsHeader.length;
		int cellMaxCol = (length - 1);
		int cellMinCol = 0;
		int colHeaderEnd = colStartDate - 1;
		int dir1Pos = 0;
		int dir2Pos = 0;
		int total = 0;	
			
		int rowHeaderDir1 = 0; 
		int rowDataDir1 = 0;
		int rowMaxDir1 = 0;
		int totalDir1 = 0;
		
		int rowMaxDir2 = 0;
		int rowDataDir2 = 0;
		int rowHeaderDir2 = 0;		
		int totalDir2 = 0;
						
		String intervalDate = "";
		 
		//Excel Cells - header 
		headerCells = new Cell[length];
		cellData = new Cell[length][registers];
				
		if(period.equals("24 hours")) {
		  
		   rowMax = (ini + registers); 		   	 
		   registerLength = registers;
		   total = (rowMax + 1);
		   cellMinCol = 1;
		   
		   dir1Pos = (total + 2);		   
		   rowHeaderDir1 = (dir1Pos + 2);
		   rowDataDir1 = (rowHeaderDir1 + 1);
		   rowMaxDir1 =  (rowDataDir1 + registers); 
		   totalDir1 = (rowMaxDir1 + 1);
		   
		   dir2Pos = (totalDir1 + 2);
		   rowHeaderDir2 = (dir2Pos + 2);
		   rowDataDir2 = (rowHeaderDir2 + 1);
		   rowMaxDir2 = (rowDataDir2 + registers); 
		   totalDir2 = (rowMaxDir2 + 1);
		   		   
		   intervalDate = localeExcel.getStringKey("excel_report_from")+": " + startDate+ "\n"+localeExcel.getStringKey("excel_report_to")+": " + endDate;
		  		
		   String sheetName = sheetNameSingle(type, module);
		   
		   sheet = workbook.createSheet(sheetName);	
		   
		    //Imagem
			spreadSheet.InsertExcelImage(workbook, sheet, logo, 0, 0, 2, 4, 1, 1, 1, 1, 1); // criar Imagem
			
			// Criar Linhas
			spreadSheet.createRows(sheet, row, 0, 4);
			
			//Criar Células
			spreadSheet.createCells(sheet, row, 0, length, 0, 3);
			
			//Mesclar Células		
			for(int i = 0; i < mergeCells.length; i++)
			   spreadSheet.mergeCells(sheet, mergeCells[i]);
			
			//Largura das Colunas		
			for(int i = 0; i < columnsWidth.length; i++)
				spreadSheet.columnsWidth(sheet, i, columnsWidth[i]);
			
			//HEADER	
			
			// Criar Linhas
			spreadSheet.createRows(sheet, row, 0, 3);
			spreadSheet.createCells(sheet, row, 0, colEndDate, 0, 3);

			spreadSheet.getCell(sheet, row, 0, 2,  fileTitle); // Título
			
			spreadSheet.getCell(sheet, row, 0, colStartDate, intervalDate); // Período
								
			spreadSheet.setStyle(sheet, row, 0, 3, headerStyle, 0, colHeaderEnd); //Aplicar Borda - Header
			spreadSheet.setStyle(sheet, row, 0, 3, datePeriodStyle, colStartDate, colEndDate); //Aplicar Borda - Header
			
			//Merge Cells
			spreadSheet.mergeCells(sheet, "A6:B6");			
			spreadSheet.mergeCells(sheet, "F6:H6");
			spreadSheet.mergeCells(sheet, "I6:J6");
			spreadSheet.mergeCells(sheet, "C7:D7");
			spreadSheet.mergeCells(sheet, "C8:D8");
																
			spreadSheet.createRow(sheet, row, 5);
			spreadSheet.createCell(sheet, row, 5, 0, localeExcel.getStringKey("excel_report_consultation_equipment"));
			spreadSheet.createCell(sheet, row, 5, 2, equip);
			spreadSheet.createCell(sheet, row, 5, 5, localeExcel.getStringKey("excel_report_consultation_date"));
			spreadSheet.createCell(sheet, row, 5, 8, " " + currentDate);

			spreadSheet.setStyle(sheet, row, 5, boldRightAlignStyle, 0);
			spreadSheet.setStyle(sheet, row, 5, boldRightAlignStyle, 5);
			spreadSheet.setStyle(sheet, row, 5, centerAlignStyle, 2);
			spreadSheet.setStyle(sheet, row, 5, leftAlignStyle, 8);

			spreadSheet.createRow(sheet, row, 6);	
			spreadSheet.createCell(sheet, row, 6, 1, localeExcel.getStringKey("excel_report_consultation_city"));
			spreadSheet.createCell(sheet, row, 6, 2, city );
			spreadSheet.createCell(sheet, row, 6, 7, localeExcel.getStringKey("excel_report_consultation_direction"));
			spreadSheet.createCell(sheet, row, 6, 8, localeDirections.getStringKey("directions_all"));

			spreadSheet.setStyle(sheet, row, 6,  boldCenterStyle, 1);
			spreadSheet.setStyle(sheet, row, 6,  boldCenterStyle, 7);
			spreadSheet.setStyle(sheet, row, 6, centerAlignStyle, 2);
			spreadSheet.setStyle(sheet, row, 6, centerAlignStyle, 8);
							
			spreadSheet.createRow(sheet, row, 7);	
			spreadSheet.createCell(sheet, row, 7, 1, localeExcel.getStringKey("excel_report_consultation_highway"));
			spreadSheet.createCell(sheet, row, 7, 2, road );
			spreadSheet.createCell(sheet, row, 7, 7, localeExcel.getStringKey("excel_report_consultation_period"));
			spreadSheet.createCell(sheet, row, 7, 8, tm.periodName(period));
			
			spreadSheet.setStyle(sheet, row, 7,  boldCenterStyle, 1);
			spreadSheet.setStyle(sheet, row, 7,  boldCenterStyle, 7);
			spreadSheet.setStyle(sheet, row, 7, centerAlignStyle, 2);
			spreadSheet.setStyle(sheet, row, 7, centerAlignStyle, 8);
			
			spreadSheet.createRow(sheet, row, 8);	
			spreadSheet.createCell(sheet, row, 8, 1, localeExcel.getStringKey("excel_report_consultation_km"));
			spreadSheet.createCell(sheet, row, 8, 2, km );
				
			spreadSheet.setStyle(sheet, row, 8,  boldCenterStyle, 1);
			spreadSheet.setStyle(sheet, row, 8, centerAlignStyle, 2);
		
			spreadSheet.createRow(sheet, row, 9);	
			spreadSheet.createCell(sheet, row, 9, 1, localeExcel.getStringKey("excel_report_consultation_lanes"));
			spreadSheet.createCell(sheet, row, 9, 2, numLanes);
			
			spreadSheet.setStyle(sheet, row, 9,  boldCenterStyle, 1);
			spreadSheet.setStyle(sheet, row, 9, centerAlignStyle, 2);
			
			//HEADER
			
			
			//SUB HEADER
			
			spreadSheet.createRow(sheet, row, 12);
			
			spreadSheet.createCell(sheet, row, 12, 2, "1");
			spreadSheet.createCell(sheet, row, 12, 6, "2");
			spreadSheet.createCell(sheet, row, 12, 15, "3");
			
			spreadSheet.setStyle(sheet, row, 12, standardStyle, 2);			
			spreadSheet.setStyle(sheet, row, 12, standardStyle, 6);
			spreadSheet.setStyle(sheet, row, 12, standardStyle, 15);
											
			sheet.addMergedRegion(CellRangeAddress.valueOf("C12:F12"));
			sheet.addMergedRegion(CellRangeAddress.valueOf("G12:O12"));	
			sheet.addMergedRegion(CellRangeAddress.valueOf("P12:T12"));			
						
			// BODY
		   spreadSheet.createRow(sheet, row, 12);
		   spreadSheet.createHeaderCells(sheet, row, 12, headerCells, columnsHeader);
		   spreadSheet.setStyleHeaderBody(sheet, row, 12, headerCells, length, tableHeaderStyle);
		   
		   /* FIRST GROUP DATA */

		   spreadSheet.createRows(sheet, row, ini, total); // Criar o número de linhas	
			
		   spreadSheet.fillDataSingleDirections(sheet, row, cellData, resultQuery, period, startColumn, length, ini, registerLength, 1); // Preencher a colunas
		   						
		   spreadSheet.createCells(sheet, row, 0, length, rowMax, rowMax);			 
		   spreadSheet.getCell(sheet, row, rowMax, 0, localeExcel.getStringKey("excel_report_excel_total"));
					
		   spreadSheet.totalExcel(sheet, row, period, startColumn, length, ini, rowMax);	   
		    		 
		   spreadSheet.setStyle(sheet, row, ini, rowMax, dateHourStyle, 0, 0);	
		   spreadSheet.setStyle(sheet, row, rowMax, rowMax, tableHeaderStyle, 0, 0);
		   spreadSheet.setStyle(sheet, row, ini, rowMax, standardStyle, cellMinCol, cellMaxCol);
		   
		   /* SECOND GROUP DATA */
				   	   
		   spreadSheet.createRow(sheet, row, dir1Pos);	
		   spreadSheet.createCell(sheet, row, dir1Pos, 7, localeExcel.getStringKey("excel_report_consultation_direction"));
		   spreadSheet.createCell(sheet, row, dir1Pos, 8, direction1 );
		
		   spreadSheet.setStyle(sheet, row, dir1Pos,  boldCenterStyle, 7);		
		   spreadSheet.setStyle(sheet, row, dir1Pos, centerAlignStyle, 8);
							
			spreadSheet.createRow(sheet, row, rowHeaderDir1);
			spreadSheet.createHeaderCells(sheet, row, rowHeaderDir1, headerCells, columnsHeader);
			spreadSheet.setStyleHeaderBody(sheet, row, rowHeaderDir1, headerCells, length, tableHeaderStyle);
			
			spreadSheet.createRows(sheet, row,  rowDataDir1, totalDir1); // Criar o número de linhas	
			
			spreadSheet.fillDataSingleDirections(sheet, row, cellData, resultQuery, period, startColumn, length, rowDataDir1, registerLength, 2); // Preencher a colunas
		   				
			spreadSheet.createCells(sheet, row, 0, length, rowMaxDir1, rowMaxDir1);			 
			spreadSheet.getCell(sheet, row, rowMaxDir1, 0, localeExcel.getStringKey("excel_report_excel_total"));
						
			spreadSheet.totalExcel(sheet, row, period, startColumn, length, rowDataDir1, rowMaxDir1);	   
			    		 
			spreadSheet.setStyle(sheet, row, rowDataDir1, rowMaxDir1, dateHourStyle, 0, 0);	
			spreadSheet.setStyle(sheet, row, rowMaxDir1, rowMaxDir1, tableHeaderStyle, 0, 0);
			spreadSheet.setStyle(sheet, row, rowDataDir1, rowMaxDir1, standardStyle, cellMinCol, cellMaxCol);
			
			 /* THIRD GROUP DATA */
						
			 spreadSheet.createRow(sheet, row, dir2Pos);	
			 spreadSheet.createCell(sheet, row, dir2Pos, 7, localeExcel.getStringKey("excel_report_consultation_direction"));
			 spreadSheet.createCell(sheet, row, dir2Pos, 8, direction2);
			
			 spreadSheet.setStyle(sheet, row, dir2Pos,  boldCenterStyle, 7);		
			 spreadSheet.setStyle(sheet, row, dir2Pos, centerAlignStyle, 8);
								
		     spreadSheet.createRow(sheet, row, rowHeaderDir2);
		     spreadSheet.createHeaderCells(sheet, row, rowHeaderDir2, headerCells, columnsHeader);
			 spreadSheet.setStyleHeaderBody(sheet, row, rowHeaderDir2, headerCells, length, tableHeaderStyle);
				
			 spreadSheet.createRows(sheet, row,  rowDataDir2, totalDir2); // Criar o número de linhas	
				
			 spreadSheet.fillDataSingleDirections(sheet, row, cellData, resultQuery, period, startColumn, length, rowDataDir2, registerLength, 3); // Preencher a colunas
			   				
			 spreadSheet.createCells(sheet, row, 0, length, rowMaxDir2, rowMaxDir2);			 
			 spreadSheet.getCell(sheet, row, rowMaxDir2, 0, localeExcel.getStringKey("excel_report_excel_total"));
							
			 spreadSheet.totalExcel(sheet, row, period, startColumn, length, rowDataDir2, rowMaxDir2);	   
				    		 
			 spreadSheet.setStyle(sheet, row, rowDataDir2, rowMaxDir2, dateHourStyle, 0, 0);	
			 spreadSheet.setStyle(sheet, row, rowMaxDir2, rowMaxDir2, tableHeaderStyle, 0, 0);
			 spreadSheet.setStyle(sheet, row, rowDataDir2, rowMaxDir2, standardStyle, cellMinCol, cellMaxCol);
							
		} else {
					  
		  rowMax = (ini + range); 
		  total = (rowMax + 1);			  
		  registerLength = range;
		  cellMinCol = 2;
				   
		   dir1Pos = (total + 2);		   
		   rowHeaderDir1 = (dir1Pos + 2);
		   rowDataDir1 = (rowHeaderDir1 + 1);
		   rowMaxDir1 =  (rowDataDir1 + range); 
		   totalDir1 = (rowMaxDir1 + 1);
		   
		   dir2Pos = (totalDir1 + 2);
		   rowHeaderDir2 = (dir2Pos + 2);
		   rowDataDir2 = (rowHeaderDir2 + 1);
		   rowMaxDir2 = (rowDataDir2 + range); 
		   totalDir2 = (rowMaxDir2 + 1);
		  
		  String[] dateRange = new String[daysCount];
		  String[] sheetName = new String[daysCount];
					  
		  dateRange = dta.dateRangeForHeader(startDate, endDate, daysCount);		
		  sheetName = dta.dateRangeForSheetName(startDate, endDate, daysCount);
	  							
	      for(int d = 0; d < daysCount; d++) {
	
		  //SheetName
	      String sheetName_ = String.valueOf(sheetName[d]); // Criação das tabs
	       sheet = workbook.createSheet(sheetName_);	
					
	    //Imagem
	   spreadSheet.InsertExcelImage(workbook, sheet, logo, 0, 0, 2, 4, 1, 1, 1, 1, 1); // criar Imagem
	
	    // Criar Linhas
	   spreadSheet.createRows(sheet, row, 0, 4);
	
	   //Criar Células
	   spreadSheet.createCells(sheet, row, 0, length, 0, 3);
	
	   //Mesclar Células		
	   for(int i = 0; i < mergeCells.length; i++)
	   spreadSheet.mergeCells(sheet, mergeCells[i]);
	
	   //Largura das Colunas		
	   for(int i = 0; i < columnsWidth.length; i++)
	   spreadSheet.columnsWidth(sheet, i, columnsWidth[i]);
	
	  //HEADER	
	
	  // Criar Linhas
	  spreadSheet.createRows(sheet, row, 0, 3);
	  spreadSheet.createCells(sheet, row, 0, colEndDate, 0, 3);

	  spreadSheet.getCell(sheet, row, 0, 2,  fileTitle); // Título
	
	  spreadSheet.getCell(sheet, row, 0, colStartDate, localeExcel.getStringKey("excel_report_from")+": " + dateRange[d]+ DateTimeApplication.HOUR_TIME_FORMAT_START_DATE +
	  "\n"+localeExcel.getStringKey("excel_report_to")+": " + dateRange[d] + DateTimeApplication.HOUR_TIME_FORMAT_END_DATE); // Período
				
	  spreadSheet.setStyle(sheet, row, 0, 3, headerStyle, 0, colHeaderEnd); //Aplicar Borda - Header
	  spreadSheet.setStyle(sheet, row, 0, 3, datePeriodStyle, colStartDate, colEndDate); //Aplicar Borda - Header
	
	  //Merge Cells
	  spreadSheet.mergeCells(sheet, "A6:B6");	
	  spreadSheet.mergeCells(sheet, "F6:H6");
	  spreadSheet.mergeCells(sheet, "I6:J6");
	  spreadSheet.mergeCells(sheet, "C7:D7");
	  spreadSheet.mergeCells(sheet, "C8:D8");
														
	  spreadSheet.createRow(sheet, row, 5);
	  spreadSheet.createCell(sheet, row, 5, 0, localeExcel.getStringKey("excel_report_consultation_equipment"));
	  spreadSheet.createCell(sheet, row, 5, 2, equip);
	  spreadSheet.createCell(sheet, row, 5, 5, localeExcel.getStringKey("excel_report_consultation_date"));
	  spreadSheet.createCell(sheet, row, 5, 8, " " + currentDate);

	  spreadSheet.setStyle(sheet, row, 5, boldRightAlignStyle, 0);
	  spreadSheet.setStyle(sheet, row, 5, boldRightAlignStyle, 5);
	  spreadSheet.setStyle(sheet, row, 5, centerAlignStyle, 2);
	  spreadSheet.setStyle(sheet, row, 5, leftAlignStyle, 8);

	  spreadSheet.createRow(sheet, row, 6);	
	  spreadSheet.createCell(sheet, row, 6, 1, localeExcel.getStringKey("excel_report_consultation_city"));
	  spreadSheet.createCell(sheet, row, 6, 2, city );
	  spreadSheet.createCell(sheet, row, 6, 7, localeExcel.getStringKey("excel_report_consultation_direction"));
	  spreadSheet.createCell(sheet, row, 6, 8, localeDirections.getStringKey("directions_all") );

	  spreadSheet.setStyle(sheet, row, 6,  boldCenterStyle, 1);
	  spreadSheet.setStyle(sheet, row, 6,  boldCenterStyle, 7);
	  spreadSheet.setStyle(sheet, row, 6, centerAlignStyle, 2);
	  spreadSheet.setStyle(sheet, row, 6, centerAlignStyle, 8);
					
	  spreadSheet.createRow(sheet, row, 7);	
	  spreadSheet.createCell(sheet, row, 7, 1, localeExcel.getStringKey("excel_report_consultation_highway"));
	  spreadSheet.createCell(sheet, row, 7, 2, road );
	  spreadSheet.createCell(sheet, row, 7, 7, localeExcel.getStringKey("excel_report_consultation_period"));
	  spreadSheet.createCell(sheet, row, 7, 8, tm.periodName(period));
	
	  spreadSheet.setStyle(sheet, row, 7,  boldCenterStyle, 1);
	  spreadSheet.setStyle(sheet, row, 7,  boldCenterStyle, 7);
	  spreadSheet.setStyle(sheet, row, 7, centerAlignStyle, 2);
	  spreadSheet.setStyle(sheet, row, 7, centerAlignStyle, 8);
	
	  spreadSheet.createRow(sheet, row, 8);	
	  spreadSheet.createCell(sheet, row, 8, 1, localeExcel.getStringKey("excel_report_consultation_km"));
	  spreadSheet.createCell(sheet, row, 8, 2, km );
		
	  spreadSheet.setStyle(sheet, row, 8,  boldCenterStyle, 1);
	  spreadSheet.setStyle(sheet, row, 8, centerAlignStyle, 2);

	  spreadSheet.createRow(sheet, row, 9);	
	  spreadSheet.createCell(sheet, row, 9, 1, localeExcel.getStringKey("excel_report_consultation_lanes"));
	  spreadSheet.createCell(sheet, row, 9, 2, numLanes);
	
	  spreadSheet.setStyle(sheet, row, 9,  boldCenterStyle, 1);
	  spreadSheet.setStyle(sheet, row, 9, centerAlignStyle, 2);
	
	   //HEADER
	
	   // BODY
	  
	   spreadSheet.createRow(sheet, row, 11);
	   spreadSheet.createHeaderCells(sheet, row, 11, headerCells, columnsHeader);
	   spreadSheet.setStyleHeaderBody(sheet, row, 11, headerCells, length, tableHeaderStyle);
	   
	   /* FIRST GROUP DATA */

	   spreadSheet.createRows(sheet, row, ini, total); // Criar o número de linhas	
		
	   spreadSheet.fillDataRangeDirections(sheet, row, cellData, resultQuery, period, startColumn, length, ini, registerLength, d, range, 1); // Preencher a colunas
	   						
	   spreadSheet.createCells(sheet, row, 0, length, rowMax, rowMax);			 
	   spreadSheet.getCell(sheet, row, rowMax, 0, localeExcel.getStringKey("excel_report_excel_total"));
				
	   spreadSheet.totalExcel(sheet, row, period, startColumn, length, ini, rowMax);	   
	    		 
	   spreadSheet.setStyle(sheet, row, ini, rowMax, dateHourStyle, 0, 1);	
	   spreadSheet.setStyle(sheet, row, rowMax, rowMax, tableHeaderStyle, 0, 1);
	   spreadSheet.setStyle(sheet, row, ini, rowMax, standardStyle, cellMinCol, cellMaxCol);
	   
	   /* SECOND GROUP DATA */
			   	   
	   spreadSheet.createRow(sheet, row, dir1Pos);	
	   spreadSheet.createCell(sheet, row, dir1Pos, 7, localeExcel.getStringKey("excel_report_consultation_direction"));
	   spreadSheet.createCell(sheet, row, dir1Pos, 8, direction1);
	
	   spreadSheet.setStyle(sheet, row, dir1Pos,  boldCenterStyle, 7);		
	   spreadSheet.setStyle(sheet, row, dir1Pos, centerAlignStyle, 8);
						
	   spreadSheet.createRow(sheet, row, rowHeaderDir1);
	   spreadSheet.createHeaderCells(sheet, row, rowHeaderDir1, headerCells, columnsHeader);
	   spreadSheet.setStyleHeaderBody(sheet, row, rowHeaderDir1, headerCells, length, tableHeaderStyle);
		
	   spreadSheet.createRows(sheet, row,  rowDataDir1, totalDir1); // Criar o número de linhas	
		
	   spreadSheet.fillDataRangeDirections(sheet, row, cellData, resultQuery, period, startColumn, length, rowDataDir1, registerLength, d, range, 2); // Preencher a colunas
	   			
	   spreadSheet.createCells(sheet, row, 0, length, rowMaxDir1, rowMaxDir1);			 
	   spreadSheet.getCell(sheet, row, rowMaxDir1, 0, localeExcel.getStringKey("excel_report_excel_total"));
					
	   spreadSheet.totalExcel(sheet, row, period, startColumn, length, rowDataDir1, rowMaxDir1);	   
		    		 
	   spreadSheet.setStyle(sheet, row, rowDataDir1, rowMaxDir1, dateHourStyle, 0, 1);	
	   spreadSheet.setStyle(sheet, row, rowMaxDir1, rowMaxDir1, tableHeaderStyle, 0, 1);
	   spreadSheet.setStyle(sheet, row, rowDataDir1, rowMaxDir1, standardStyle, cellMinCol, cellMaxCol);
		
	    /* THIRD GROUP DATA */
					
	   spreadSheet.createRow(sheet, row, dir2Pos);	
	   spreadSheet.createCell(sheet, row, dir2Pos, 7, localeExcel.getStringKey("excel_report_consultation_direction"));
	   spreadSheet.createCell(sheet, row, dir2Pos, 8, direction2);
		
	   spreadSheet.setStyle(sheet, row, dir2Pos,  boldCenterStyle, 7);		
	   spreadSheet.setStyle(sheet, row, dir2Pos, centerAlignStyle, 8);
							
	   spreadSheet.createRow(sheet, row, rowHeaderDir2);
	   spreadSheet.createHeaderCells(sheet, row, rowHeaderDir2, headerCells, columnsHeader);
	   spreadSheet.setStyleHeaderBody(sheet, row, rowHeaderDir2, headerCells, length, tableHeaderStyle);
			
	   spreadSheet.createRows(sheet, row,  rowDataDir2, totalDir2); // Criar o número de linhas	
				
	   spreadSheet.fillDataRangeDirections(sheet, row, cellData, resultQuery, period, startColumn, length, rowDataDir2, registerLength, d, range, 3); // Preencher a colunas
	   
	   spreadSheet.createCells(sheet, row, 0, length, rowMaxDir2, rowMaxDir2);			 
	   spreadSheet.getCell(sheet, row, rowMaxDir2, 0, localeExcel.getStringKey("excel_report_excel_total"));
						
	   spreadSheet.totalExcel(sheet, row, period, startColumn, length, rowDataDir2, rowMaxDir2);	   
			    		 
	   spreadSheet.setStyle(sheet, row, rowDataDir2, rowMaxDir2, dateHourStyle, 0, 1);	
	   spreadSheet.setStyle(sheet, row, rowMaxDir2, rowMaxDir2, tableHeaderStyle, 0, 1);
	   spreadSheet.setStyle(sheet, row, rowDataDir2, rowMaxDir2, standardStyle, cellMinCol, cellMaxCol); 
  
	   }
     }				
   }
		
	
	public void ExcelModelCountFlow(String[] columnsHeader, int registers, int range, int daysCount, String period, String currentDate, String type, String module, String logo, 
			String fileTitle, String equip, String city, String road, String km, String numLanes, String direction1, String direction2, String startDate, String endDate, String[] mergeCells, int[] columnsWidth, int colStartDate, int colEndDate, String[][] resultQuery ) throws Exception {
			
		dta = new DateTimeApplication(); // Métodos Date and Time	
		tm = new TranslationMethods();
		sheet = null;		
		row = null;
		
		int rowMax = 0;		
				
		int ini = 3;
		int startColumn = 0;			
		int length = columnsHeader.length;
							
		String sheetName = sheetNameSingle(type, module);
		sheet = workbook.createSheet(sheetName);	
		
		rowMax = ((ini + registers) - 1); 	
					  				 
		//Excel Cells - header 
		headerCells = new Cell[length];
		cellData = new Cell[length][registers];
		
		//Mesclar Células		
		for(int i = 0; i < mergeCells.length; i++)
		  spreadSheet.mergeCells(sheet, mergeCells[i]);
				
		//Largura das Colunas		
		for(int i = 0; i < columnsWidth.length; i++)
		  spreadSheet.columnsWidth(sheet, i, columnsWidth[i]);	
				
		spreadSheet.createRow(sheet, row, 0);
		spreadSheet.createCells(sheet, row, 0, 8, 0, 0);
		spreadSheet.getCell(sheet, row, 0, 0, equip+" "+localeExcel.getStringKey("excel_report_km")+km+"  "+road);
		spreadSheet.getCell(sheet, row, 0, 2, localeExcel.getStringKey("excel_report_count_flow_description"));
		spreadSheet.setHeight(sheet, 0, 700);
		
		spreadSheet.setStyle(sheet, row, 0, 0, backgroundColorHeader, 0, 8);	
		
		spreadSheet.createRow(sheet, row, 1);
		spreadSheet.createCells(sheet, row, 0, 8, 1, 1);
		spreadSheet.getCell(sheet, row, 1, 0, localeExcel.getStringKey("excel_report_date"));
		spreadSheet.getCell(sheet, row, 1, 1, localeExcel.getStringKey("excel_report_interval"));
		spreadSheet.getCell(sheet, row, 1, 2, ""+direction1);
		spreadSheet.getCell(sheet, row, 1, 5, ""+direction2);
		spreadSheet.getCell(sheet, row, 1, 8, localeExcel.getStringKey("excel_report_count_flow_total"));
		
		spreadSheet.setStyle(sheet, row, 1, 1, backgroundColorHeader, 0, 8);
					
		spreadSheet.createRow(sheet, row, 2);
		spreadSheet.createHeaderCells(sheet, row, 2, headerCells, columnsHeader);		
		
		spreadSheet.setStyle(sheet, row, 2, 2, backgroundColorHeader, 0, 1);		
		spreadSheet.setStyle(sheet, row, 2, 2, backgroundColorSubHeader, 2, 4);
		spreadSheet.setStyle(sheet, row, 2, 2, backgroundColorSubHeader2, 5, 7);			
	                
        spreadSheet.createRows(sheet, row, ini, rowMax); // Criar o número de linhas
        
        spreadSheet.fillDataSingleFlow(sheet, row, cellData, resultQuery, period, startColumn, length, ini, registers); // Preencher a colunas
				
        spreadSheet.setStyle(sheet, row, ini, rowMax, dateHourStyle, 1, 1);	
		spreadSheet.setStyle(sheet, row, ini, rowMax, standardStyle, 0, 0);	
		spreadSheet.setStyle(sheet, row, ini, rowMax, standardStyle, 8, 8);
		spreadSheet.setStyle(sheet, row, ini, rowMax, backgroundColorBody, 2, 4);
		spreadSheet.setStyle(sheet, row, ini, rowMax, backgroundColorBody2, 5, 7);			
		
	}
	
	
	public void ExcelModelPeriodFlow(int fieldsNumber, int registers, int range, int daysCount, String period, String currentDate, String type, String module, String logo, 
			String fileTitle, String equip, String city, String road, String km, String numLanes, String[] direction1, String[] direction2, String month, String year, String startDate, String endDate, String[] mergeCells, int[] columnsWidth, int colStartDate, int colEndDate, String[][] resultQuery ) throws Exception {

		dta = new DateTimeApplication(); // Métodos Date and Time	
		tm = new TranslationMethods();
		sheet = null;		
		row = null;
		
		int rowMax = 0;		
				
		int ini = 5;
		int startColumn = 2;			
		int length = (fieldsNumber - 1);
		int registerLength = registers;
		
		rowMax = ((ini + registers) - 1);
				
		//Excel Cells - header 
		headerCells = new Cell[length];
		cellData = new Cell[length][registers];
		
		String sheetName = "Teste";
		sheet = workbook.createSheet(sheetName);	
				
		//Columns
		spreadSheet.columnsWidth(sheet, 0, 1000);		
		spreadSheet.columnsWidth(sheet, 1, 1600);
		spreadSheet.columnsWidth(sheet, 2, 2500);
		spreadSheet.columnsWidth(sheet, 3, 1800);
		spreadSheet.columnsWidth(sheet, 4, 800);
		spreadSheet.columnsWidth(sheet, 5, 1800);
		spreadSheet.columnsWidth(sheet, 18, 4500);
		spreadSheet.columnsWidth(sheet, 22, 4500);
		spreadSheet.columnsWidth(sheet, 27, 4500);
		spreadSheet.columnsWidth(sheet, 31, 4500);
		spreadSheet.columnsWidth(sheet, 36, 4500);
		spreadSheet.columnsWidth(sheet, 40, 4500);
		spreadSheet.columnsWidth(sheet, 45, 4500);
		spreadSheet.columnsWidth(sheet, 49, 4500);
		spreadSheet.columnsWidth(sheet, 54, 4500);
		spreadSheet.columnsWidth(sheet, 58, 4500);
		spreadSheet.columnsWidth(sheet, 63, 4500);
		spreadSheet.columnsWidth(sheet, 67, 4500);

		//Espaço em branco
		spreadSheet.columnsWidth(sheet, 14, 1300);
		spreadSheet.columnsWidth(sheet, 23, 1300);
		spreadSheet.columnsWidth(sheet, 32, 1300);
		spreadSheet.columnsWidth(sheet, 41, 1300);
		spreadSheet.columnsWidth(sheet, 50, 1300);
		spreadSheet.columnsWidth(sheet, 59, 1300); 
		
		//Columns
		
		//Merge
		for(int i = 0; i < mergeCells.length; i++)
		   spreadSheet.mergeCells(sheet, mergeCells[i]);
		
		//ROW				
		spreadSheet.createRows(sheet, row, 0, 4); // Criar o número de linhas
		
		//ROW 0		
		spreadSheet.createCell(sheet, row, 0, 0, localeExcel.getStringKey("excel_report_period_flow_header"));
		spreadSheet.setStyle(sheet, row, 0, espaceStyle, 0);
		
		//ROW 1		
		spreadSheet.createCell(sheet, row, 1, 0, localeExcel.getStringKey("excel_report_period_flow_header"));
		spreadSheet.setStyle(sheet, row, 1, espaceStyle2, 0);
							
		//CELL NUMBERS	
		for(int c = 1; c < 68; c++) {
			spreadSheet.createCell(sheet, row, 1, c, c);
		    spreadSheet.setStyle(sheet, row, 1,  numbersTitleStyle, c);
		}
		
		// ROW 2		
		spreadSheet.createCell(sheet, row, 2, 1, 2);		
		spreadSheet.createCell(sheet, row, 2, 2,  tm.MonthAbbreviation(month)+" / "+year+" - "+tm.periodName(period)); 
		
		//MERGED CELLS
		spreadSheet.createCells(sheet, row, 3, 5, 2, 2);
		spreadSheet.createCells(sheet, row, 7, 9, 2, 2);
		spreadSheet.createCells(sheet, row, 7, 9, 2, 2);
		spreadSheet.createCells(sheet, row, 11, 13, 2, 2);
		spreadSheet.createCells(sheet, row, 16, 18, 2, 2);
		spreadSheet.createCells(sheet, row, 20, 22, 2, 2);
		spreadSheet.createCells(sheet, row, 25, 27, 2, 2);
		spreadSheet.createCells(sheet, row, 29, 31, 2, 2);
		spreadSheet.createCells(sheet, row, 34, 36, 2, 2);
		spreadSheet.createCells(sheet, row, 38, 40, 2, 2);
		spreadSheet.createCells(sheet, row, 43, 45, 2, 2);
		spreadSheet.createCells(sheet, row, 47, 49, 2, 2);
		spreadSheet.createCells(sheet, row, 52, 54, 2, 2);
		spreadSheet.createCells(sheet, row, 56, 58, 2, 2);
		spreadSheet.createCells(sheet, row, 61, 63, 2, 2);
		spreadSheet.createCells(sheet, row, 65, 67, 2, 2);
		
		
		//NORMAL CELLS
		spreadSheet.createCell(sheet, row, 2, 6, direction1[0]);
		spreadSheet.createCell(sheet, row, 2, 10, direction2[0]);
		
		spreadSheet.createCell(sheet, row, 2, 15, direction1[0]);
		spreadSheet.createCell(sheet, row, 2, 19, direction2[0]);
		
		spreadSheet.createCell(sheet, row, 2, 24, direction1[0]);
		spreadSheet.createCell(sheet, row, 2, 28, direction2[0]);
		
		spreadSheet.createCell(sheet, row, 2, 33, direction1[0]);
		spreadSheet.createCell(sheet, row, 2, 37, direction2[0]);
		
		spreadSheet.createCell(sheet, row, 2, 42, direction1[0]);
		spreadSheet.createCell(sheet, row, 2, 46, direction2[0]);
		
		spreadSheet.createCell(sheet, row, 2, 51, direction1[0]);
		spreadSheet.createCell(sheet, row, 2, 55, direction2[0]);
		
		spreadSheet.createCell(sheet, row, 2, 60, direction1[0]);
		spreadSheet.createCell(sheet, row, 2, 64, direction2[0]);
		
		spreadSheet.createCell(sheet, row, 2, 14 , "");
		spreadSheet.createCell(sheet, row, 2, 23 , "");
		spreadSheet.createCell(sheet, row, 2, 32 , "");
		spreadSheet.createCell(sheet, row, 2, 41 , "");
		spreadSheet.createCell(sheet, row, 2, 50 , "");
		spreadSheet.createCell(sheet, row, 2, 59 , "");
		spreadSheet.createCell(sheet, row, 2, 68 , "");		
		
		/* STYLE */	
		spreadSheet.setStyle(sheet, row, 2, numbersTitleStyle, 1);	
		spreadSheet.setStyle(sheet, row, 2, reportTimeStyle, 2);			
		spreadSheet.setStyle(sheet, row, 2, reportTimeStyle, 6);
		spreadSheet.setStyle(sheet, row, 2, reportTimeStyle, 10);
		spreadSheet.setStyle(sheet, row, 2, reportTimeStyle, 15);
		spreadSheet.setStyle(sheet, row, 2, reportTimeStyle, 19);
		spreadSheet.setStyle(sheet, row, 2, reportTimeStyle, 24);
		spreadSheet.setStyle(sheet, row, 2, reportTimeStyle, 28);
		spreadSheet.setStyle(sheet, row, 2, reportTimeStyle, 33);
		spreadSheet.setStyle(sheet, row, 2, reportTimeStyle, 37);
		spreadSheet.setStyle(sheet, row, 2, reportTimeStyle, 42);
		spreadSheet.setStyle(sheet, row, 2, reportTimeStyle, 46);
		spreadSheet.setStyle(sheet, row, 2, reportTimeStyle, 51);
		spreadSheet.setStyle(sheet, row, 2, reportTimeStyle, 55);
		spreadSheet.setStyle(sheet, row, 2, reportTimeStyle, 60);
		spreadSheet.setStyle(sheet, row, 2, reportTimeStyle, 64);
		
		// BLANKCELL
		spreadSheet.setStyle(sheet, row, 2, blankStyle, 14);
		spreadSheet.setStyle(sheet, row, 2, blankStyle, 23);
		spreadSheet.setStyle(sheet, row, 2, blankStyle, 32);
		spreadSheet.setStyle(sheet, row, 2, blankStyle, 41);
		spreadSheet.setStyle(sheet, row, 2, blankStyle, 50);
		spreadSheet.setStyle(sheet, row, 2, blankStyle, 59);
		spreadSheet.setStyle(sheet, row, 2, blankStyle2, 68);
		
		//CELL MERGE STYLE
		spreadSheet.setCellStyle(sheet, row, reportTimeStyle, 3, 5, 2, 2);
		spreadSheet.setCellStyle(sheet, row, reportTimeStyle, 7, 9, 2, 2);
		spreadSheet.setCellStyle(sheet, row, reportTimeStyle, 11, 13, 2, 2);
		spreadSheet.setCellStyle(sheet, row, reportTimeStyle, 16, 18, 2, 2);
		spreadSheet.setCellStyle(sheet, row, reportTimeStyle, 20, 22, 2, 2);
		spreadSheet.setCellStyle(sheet, row, reportTimeStyle, 25, 27, 2, 2);
		spreadSheet.setCellStyle(sheet, row, reportTimeStyle, 29, 31, 2, 2);
		spreadSheet.setCellStyle(sheet, row, reportTimeStyle, 34, 36, 2, 2);
		spreadSheet.setCellStyle(sheet, row, reportTimeStyle, 38, 40, 2, 2);
		spreadSheet.setCellStyle(sheet, row, reportTimeStyle, 43, 45, 2, 2);
		spreadSheet.setCellStyle(sheet, row, reportTimeStyle, 47, 49, 2, 2);
		spreadSheet.setCellStyle(sheet, row, reportTimeStyle, 52, 54, 2, 2);
		spreadSheet.setCellStyle(sheet, row, reportTimeStyle, 56, 58, 2, 2);
		spreadSheet.setCellStyle(sheet, row, reportTimeStyle, 61, 63, 2, 2);
		spreadSheet.setCellStyle(sheet, row, reportTimeStyle, 65, 67, 2, 2);
			
		// ROW 3
		spreadSheet.createCell(sheet, row, 3, 1, 3);
		spreadSheet.createCell(sheet, row, 3, 2, localeExcel.getStringKey("excel_report_period_flow_day"));
		spreadSheet.createCell(sheet, row, 3, 3, localeExcel.getStringKey("excel_report_period_flow_interval"));
		
		spreadSheet.createCell(sheet, row, 3, 6, localeExcel.getStringKey("excel_report_period_flow_count"));
		spreadSheet.createCell(sheet, row, 3, 10, localeExcel.getStringKey("excel_report_period_flow_count"));
		
		spreadSheet.createCell(sheet, row, 3, 15, localeExcel.getStringKey("excel_report_period_flow_speed_avg"));
		spreadSheet.createCell(sheet, row, 3, 19, localeExcel.getStringKey("excel_report_period_flow_speed_avg"));
		
		spreadSheet.createCell(sheet, row, 3, 24, localeExcel.getStringKey("excel_report_period_flow_speed_avg_50"));
		spreadSheet.createCell(sheet, row, 3, 28, localeExcel.getStringKey("excel_report_period_flow_speed_avg_50"));
		
		spreadSheet.createCell(sheet, row, 3, 33, localeExcel.getStringKey("excel_report_period_flow_speed_avg_85"));
		spreadSheet.createCell(sheet, row, 3, 37, localeExcel.getStringKey("excel_report_period_flow_speed_avg_85"));
		
		spreadSheet.createCell(sheet, row, 3, 42, localeExcel.getStringKey("excel_report_period_flow_speed_max"));
		spreadSheet.createCell(sheet, row, 3, 46, localeExcel.getStringKey("excel_report_period_flow_speed_max"));
		
		spreadSheet.createCell(sheet, row, 3, 51, localeExcel.getStringKey("excel_report_period_flow_speed_min"));
		spreadSheet.createCell(sheet, row, 3, 55, localeExcel.getStringKey("excel_report_period_flow_speed_min"));
		
		spreadSheet.createCell(sheet, row, 3, 60, localeExcel.getStringKey("excel_report_period_flow_speed_std"));
		spreadSheet.createCell(sheet, row, 3, 64, localeExcel.getStringKey("excel_report_period_flow_speed_std"));
		
		spreadSheet.createCell(sheet, row, 3, 14 , "");
		spreadSheet.createCell(sheet, row, 3, 23 , "");
		spreadSheet.createCell(sheet, row, 3, 32 , "");
		spreadSheet.createCell(sheet, row, 3, 41 , "");
		spreadSheet.createCell(sheet, row, 3, 50 , "");
		spreadSheet.createCell(sheet, row, 3, 59 , "");
		spreadSheet.createCell(sheet, row, 3, 68 , "");		
		
		/* STYLE */	
		spreadSheet.setStyle(sheet, row, 3, numbersTitleStyle, 1);	
		spreadSheet.setStyle(sheet, row, 3, subHeaderStyle, 2);
		spreadSheet.setStyle(sheet, row, 3, subHeaderStyle, 3);
		spreadSheet.setStyle(sheet, row, 3, blueColorBackgroundStyle, 6);
		spreadSheet.setStyle(sheet, row, 3, blueColorBackgroundStyle, 10);
		spreadSheet.setStyle(sheet, row, 3, redColorBackgroundStyle, 15);
		spreadSheet.setStyle(sheet, row, 3, redColorBackgroundStyle, 19);
		spreadSheet.setStyle(sheet, row, 3, blueColorBackgroundStyle, 24);
		spreadSheet.setStyle(sheet, row, 3, blueColorBackgroundStyle, 28);
		spreadSheet.setStyle(sheet, row, 3, redColorBackgroundStyle, 33);
		spreadSheet.setStyle(sheet, row, 3, redColorBackgroundStyle, 37);
		spreadSheet.setStyle(sheet, row, 3, blueColorBackgroundStyle, 42);
		spreadSheet.setStyle(sheet, row, 3, blueColorBackgroundStyle, 46);
		spreadSheet.setStyle(sheet, row, 3, redColorBackgroundStyle, 51);
		spreadSheet.setStyle(sheet, row, 3, redColorBackgroundStyle, 55);
		spreadSheet.setStyle(sheet, row, 3, blueColorBackgroundStyle, 60);
		spreadSheet.setStyle(sheet, row, 3, blueColorBackgroundStyle, 64);
		
		// BLANK CELL
		spreadSheet.setStyle(sheet, row, 3, blankStyle, 14);
		spreadSheet.setStyle(sheet, row, 3, blankStyle, 23);
		spreadSheet.setStyle(sheet, row, 3, blankStyle, 32);
		spreadSheet.setStyle(sheet, row, 3, blankStyle, 41);
		spreadSheet.setStyle(sheet, row, 3, blankStyle, 50);
		spreadSheet.setStyle(sheet, row, 3, blankStyle, 59);
		spreadSheet.setStyle(sheet, row, 3, blankStyle2, 68);
	
		//ROW 4		
		spreadSheet.createCell(sheet, row, 4, 1, 4);
		spreadSheet.createCells(sheet, row, 2, 5, 4, 4);
		spreadSheet.createCell(sheet, row, 4, 6, localeExcel.getStringKey("excel_report_period_flow_auto"));
		spreadSheet.createCell(sheet, row, 4, 7, localeExcel.getStringKey("excel_report_period_flow_com"));
		spreadSheet.createCell(sheet, row, 4, 8, localeExcel.getStringKey("excel_report_period_flow_moto"));
		spreadSheet.createCell(sheet, row, 4, 9, localeExcel.getStringKey("excel_report_period_flow_total"));
		spreadSheet.createCell(sheet, row, 4, 10, localeExcel.getStringKey("excel_report_period_flow_auto"));
		spreadSheet.createCell(sheet, row, 4, 11, localeExcel.getStringKey("excel_report_period_flow_com"));
		spreadSheet.createCell(sheet, row, 4, 12, localeExcel.getStringKey("excel_report_period_flow_moto"));
		spreadSheet.createCell(sheet, row, 4, 13, localeExcel.getStringKey("excel_report_period_flow_total"));
		
		spreadSheet.createCell(sheet, row, 4, 15, localeExcel.getStringKey("excel_report_period_flow_auto"));
		spreadSheet.createCell(sheet, row, 4, 16, localeExcel.getStringKey("excel_report_period_flow_com"));
		spreadSheet.createCell(sheet, row, 4, 17, localeExcel.getStringKey("excel_report_period_flow_moto"));
		spreadSheet.createCell(sheet, row, 4, 18, localeExcel.getStringKey("excel_report_period_flow_direction1"));
		spreadSheet.createCell(sheet, row, 4, 19, localeExcel.getStringKey("excel_report_period_flow_auto"));
		spreadSheet.createCell(sheet, row, 4, 20, localeExcel.getStringKey("excel_report_period_flow_com"));
		spreadSheet.createCell(sheet, row, 4, 21, localeExcel.getStringKey("excel_report_period_flow_moto"));
		spreadSheet.createCell(sheet, row, 4, 22, localeExcel.getStringKey("excel_report_period_flow_direction2"));
		
		spreadSheet.createCell(sheet, row, 4, 24, localeExcel.getStringKey("excel_report_period_flow_auto"));
		spreadSheet.createCell(sheet, row, 4, 25, localeExcel.getStringKey("excel_report_period_flow_com"));
		spreadSheet.createCell(sheet, row, 4, 26, localeExcel.getStringKey("excel_report_period_flow_moto"));
		spreadSheet.createCell(sheet, row, 4, 27, localeExcel.getStringKey("excel_report_period_flow_direction1"));
		spreadSheet.createCell(sheet, row, 4, 28, localeExcel.getStringKey("excel_report_period_flow_auto"));
		spreadSheet.createCell(sheet, row, 4, 29, localeExcel.getStringKey("excel_report_period_flow_com"));
		spreadSheet.createCell(sheet, row, 4, 30, localeExcel.getStringKey("excel_report_period_flow_moto"));
		spreadSheet.createCell(sheet, row, 4, 31, localeExcel.getStringKey("excel_report_period_flow_direction2"));
		
		spreadSheet.createCell(sheet, row, 4, 33, localeExcel.getStringKey("excel_report_period_flow_auto"));
		spreadSheet.createCell(sheet, row, 4, 34, localeExcel.getStringKey("excel_report_period_flow_com"));
		spreadSheet.createCell(sheet, row, 4, 35, localeExcel.getStringKey("excel_report_period_flow_moto"));
		spreadSheet.createCell(sheet, row, 4, 36, localeExcel.getStringKey("excel_report_period_flow_direction1"));
		spreadSheet.createCell(sheet, row, 4, 37, localeExcel.getStringKey("excel_report_period_flow_auto"));
		spreadSheet.createCell(sheet, row, 4, 38, localeExcel.getStringKey("excel_report_period_flow_com"));
		spreadSheet.createCell(sheet, row, 4, 39, localeExcel.getStringKey("excel_report_period_flow_moto"));
		spreadSheet.createCell(sheet, row, 4, 40, localeExcel.getStringKey("excel_report_period_flow_direction2"));
		
		spreadSheet.createCell(sheet, row, 4, 42, localeExcel.getStringKey("excel_report_period_flow_auto"));
		spreadSheet.createCell(sheet, row, 4, 43, localeExcel.getStringKey("excel_report_period_flow_com"));
		spreadSheet.createCell(sheet, row, 4, 44, localeExcel.getStringKey("excel_report_period_flow_moto"));
		spreadSheet.createCell(sheet, row, 4, 45, localeExcel.getStringKey("excel_report_period_flow_direction1"));
		spreadSheet.createCell(sheet, row, 4, 46, localeExcel.getStringKey("excel_report_period_flow_auto"));
		spreadSheet.createCell(sheet, row, 4, 47, localeExcel.getStringKey("excel_report_period_flow_com"));
		spreadSheet.createCell(sheet, row, 4, 48, localeExcel.getStringKey("excel_report_period_flow_moto"));
		spreadSheet.createCell(sheet, row, 4, 49, localeExcel.getStringKey("excel_report_period_flow_direction2"));
		
		spreadSheet.createCell(sheet, row, 4, 51, localeExcel.getStringKey("excel_report_period_flow_auto"));
		spreadSheet.createCell(sheet, row, 4, 52, localeExcel.getStringKey("excel_report_period_flow_com"));
		spreadSheet.createCell(sheet, row, 4, 53, localeExcel.getStringKey("excel_report_period_flow_moto"));
		spreadSheet.createCell(sheet, row, 4, 54, localeExcel.getStringKey("excel_report_period_flow_direction1"));
		spreadSheet.createCell(sheet, row, 4, 55, localeExcel.getStringKey("excel_report_period_flow_auto"));
		spreadSheet.createCell(sheet, row, 4, 56, localeExcel.getStringKey("excel_report_period_flow_com"));
		spreadSheet.createCell(sheet, row, 4, 57, localeExcel.getStringKey("excel_report_period_flow_moto"));
		spreadSheet.createCell(sheet, row, 4, 58, localeExcel.getStringKey("excel_report_period_flow_direction2"));
		
		spreadSheet.createCell(sheet, row, 4, 60, localeExcel.getStringKey("excel_report_period_flow_auto"));
		spreadSheet.createCell(sheet, row, 4, 61, localeExcel.getStringKey("excel_report_period_flow_com"));
		spreadSheet.createCell(sheet, row, 4, 62, localeExcel.getStringKey("excel_report_period_flow_moto"));
		spreadSheet.createCell(sheet, row, 4, 63, localeExcel.getStringKey("excel_report_period_flow_direction1"));
		spreadSheet.createCell(sheet, row, 4, 64, localeExcel.getStringKey("excel_report_period_flow_auto"));
		spreadSheet.createCell(sheet, row, 4, 65, localeExcel.getStringKey("excel_report_period_flow_com"));
		spreadSheet.createCell(sheet, row, 4, 66, localeExcel.getStringKey("excel_report_period_flow_moto"));
		spreadSheet.createCell(sheet, row, 4, 67, localeExcel.getStringKey("excel_report_period_flow_direction2"));
				
		/* STYLE */			
		spreadSheet.setStyle(sheet, row, 4, numbersTitleStyle, 1);
		spreadSheet.setStyle(sheet, row, 4, subHeaderStyle, 2);
		spreadSheet.setCellStyle(sheet, row, subHeaderStyle, 2, 5, 4, 4);

		for(int i=6; i < 14; i++)
			spreadSheet.setStyle(sheet, row, 4, subHeaderStyle, i);

		for(int i=15; i < 23; i++)
			spreadSheet.setStyle(sheet, row, 4, subHeaderStyle, i);

		for(int i=24; i < 32; i++)
			spreadSheet.setStyle(sheet, row, 4, subHeaderStyle, i);

		for(int i=33; i < 41; i++)
			spreadSheet.setStyle(sheet, row, 4, subHeaderStyle, i);

		for(int i=42; i < 50; i++)
			spreadSheet.setStyle(sheet, row, 4, subHeaderStyle, i);

		for(int i=51; i < 59; i++)
			spreadSheet.setStyle(sheet, row, 4, subHeaderStyle, i);

		for(int i=60; i < 68; i++)
			spreadSheet.setStyle(sheet, row, 4, subHeaderStyle, i);	
		
		//ROW DATA
		spreadSheet.createRows(sheet, row, ini, rowMax); // Criar o número de linhas 
		
		//Numbers
		for(int r = ini; r <= rowMax; r++)		
		  spreadSheet.createCell(sheet, row, r, 1, r);
				
		// fields
	    spreadSheet.fillDataSinglePeriodFlow(sheet, row, cellData, resultQuery, period, startColumn, length, ini, registerLength ); // Preencher a colunas
	  				
        spreadSheet.setStyle(sheet, row, ini, rowMax, numbersTitleStyle, 1, 1);	
		spreadSheet.setStyle(sheet, row, ini, rowMax, dayStyle, 2, 2);	
		spreadSheet.setStyle(sheet, row, ini, rowMax, intervalStyle, 3, 5);	
		spreadSheet.setStyle(sheet, row, ini, rowMax, standardStyle1, 6, 13);
		spreadSheet.setStyle(sheet, row, ini, rowMax, standardStyle1, 15, 22);
		spreadSheet.setStyle(sheet, row, ini, rowMax, standardStyle1, 24, 31);
		spreadSheet.setStyle(sheet, row, ini, rowMax, standardStyle1, 33, 40);
		spreadSheet.setStyle(sheet, row, ini, rowMax, standardStyle1, 42, 49);
		spreadSheet.setStyle(sheet, row, ini, rowMax, standardStyle1, 51, 58);
		spreadSheet.setStyle(sheet, row, ini, rowMax, standardStyle1, 60, 67);
		    
	}
	
	public void download(XSSFWorkbook workbook, String fileName) throws IOException {
		
		 spreadSheet.donwloadExcelFile(workbook, fileName); //Download Method
		
	}
		
	public void ExcelModelMonthlyFlow(int fieldsNumber, int registers, int daysInMonth, int range, int daysCount, String period, String currentDate, String type, String module, String logo, 
			String fileTitle, String equip, String city, String road, String km, String numLanes, String direction1, String direction2, String abbrDir1, String abbrDir2,
			String month, String year, String startDate, String endDate, String[] mergeCells, int[] columnsWidth, int colStartDate, int colEndDate, String[][] resultQuery ) throws Exception {

		dta = new DateTimeApplication(); // Métodos Date and Time	
		tm = new TranslationMethods();
		sheet = null;		
		row = null;
		
		int rowMax = 0;		
				
		int ini = 3;				
		int length = fieldsNumber;
		int registerLength = registers;
		
		rowMax = (ini + registers);
				
		//Excel Cells - header 
		headerCells = new Cell[length];
		cellData = new Cell[length][registers];
		
		String sheetName =  localeExcel.getStringKey("excel_single_sat_sheet_base_name");
		sheet = workbook.createSheet(sheetName);
					
		spreadSheet.createRows(sheet, row, 1, 2); // Criar o número de linhas
		
		spreadSheet.createCell(sheet, row, 1, 0, "");
		spreadSheet.createCell(sheet, row, 1, 1, localeExcel.getStringKey("excel_report_monthly_flow_date"));
		spreadSheet.createCell(sheet, row, 1, 2, localeExcel.getStringKey("excel_report_monthly_flow_interval"));
		spreadSheet.createCell(sheet, row, 1, 3, direction1);
		spreadSheet.createCell(sheet, row, 1, 6, direction2);
		spreadSheet.createCell(sheet, row, 1, 10, localeExcel.getStringKey("excel_report_monthly_flow_speed_operations"));
		
		spreadSheet.createCell(sheet, row, 2, 3, localeExcel.getStringKey("excel_report_monthly_flow_moto"));
		spreadSheet.createCell(sheet, row, 2, 4, localeExcel.getStringKey("excel_report_monthly_flow_auto"));
		spreadSheet.createCell(sheet, row, 2, 5, localeExcel.getStringKey("excel_report_monthly_flow_com"));
		spreadSheet.createCell(sheet, row, 2, 6, localeExcel.getStringKey("excel_report_monthly_flow_moto"));
		spreadSheet.createCell(sheet, row, 2, 7, localeExcel.getStringKey("excel_report_monthly_flow_auto"));
		spreadSheet.createCell(sheet, row, 2, 8, localeExcel.getStringKey("excel_report_monthly_flow_com"));
		spreadSheet.createCell(sheet, row, 2, 10, "DIR1");
		spreadSheet.createCell(sheet, row, 2, 11, "DIR2");
				
		//ROW DATA CREATION
		spreadSheet.createRows(sheet, row, ini, rowMax);		
		spreadSheet.fillDataSingle(sheet, row, cellData, resultQuery, 0, 1 , 9, ini, registerLength);
		
		spreadSheet.fillDataSingle(sheet, row, cellData, resultQuery, 8, 10 , 12, ini, registerLength);
		       
		//TOTAL
        spreadSheet.createCells(sheet, row, 2, 2, rowMax, rowMax);			 
 	    spreadSheet.getCell(sheet, row, rowMax, 2, localeExcel.getStringKey("excel_report_excel_total"));
 	    
 	    spreadSheet.totalExcel(sheet, row, 3, 8, ini, rowMax);
 	    
 	    //BASE STYLES 	    
 	    spreadSheet.setStyle(sheet, row, 1, 1, indigoBackgroundStyle, 1, 3);
 	    spreadSheet.setStyle(sheet, row, 1, indigoBackgroundStyle, 6);
 	    spreadSheet.setStyle(sheet, row, 1, indigoBackgroundStyle, 10);
 	    spreadSheet.setStyle(sheet, row, 2, 2, indigoBackgroundStyle, 3, 8);	   
 	    spreadSheet.setStyle(sheet, row, 2, 2, indigoBackgroundStyle, 10, 11);	
 	    
 	    spreadSheet.setStyle(sheet, row, ini, (rowMax-1), monthDateTimeStyle, 1, 2); 	   
 	    spreadSheet.setStyle(sheet, row, ini, rowMax, monthBodybackgroundStyle, 3, 8);
	    spreadSheet.setStyle(sheet, row, ini, (rowMax-1), monthBodybackgroundStyle, 10, 11);
	    spreadSheet.setStyle(sheet, row, rowMax, rowMax, indigoBackgroundStyle, 2, 2); 	
	    
	    //BASE MERGE CELLS 	     	     	    	    
	    spreadSheet.mergeCells(sheet, "B2:B3");
	    spreadSheet.mergeCells(sheet, "C2:C3");
	    spreadSheet.mergeCells(sheet, "D2:F2");
	    spreadSheet.mergeCells(sheet, "G2:I2");
	    spreadSheet.mergeCells(sheet, "K2:L2");	    
	 
	    //BASE COLUMNS
	    spreadSheet.columnsWidth(sheet, 0, 700);
	    spreadSheet.columnsWidth(sheet, 1, 3500);
	    spreadSheet.columnsWidth(sheet, 2, 3500);
	    spreadSheet.columnsWidth(sheet, 10, 3650);
	    spreadSheet.columnsWidth(sheet, 11, 3650);
 	    
 	    //SHEET DAYS OF MONTH 	    
 	     XSSFSheet sheetDays = null;
 	   
 	     for (int d = 1; d <= daysInMonth; d++) {
 	    	  		  
 		 String sheetDaysName = String.valueOf(d);
 		 
 		 sheetDays = workbook.createSheet(sheetDaysName);
 		
 		//FIRST ROWS
 		spreadSheet.createRows(sheetDays, row, 1, 9);
 		 
 		spreadSheet.createCell(sheetDays, row, 1, 1, localeExcel.getStringKey("excel_report_monthly_flow_strech_title"));
 		spreadSheet.createCell(sheetDays, row, 1, 7, localeExcel.getStringKey("excel_report_monthly_flow_low_date"));
 		spreadSheet.createCell(sheetDays, row, 1, 8, "dia");
 		
 		spreadSheet.createCells(sheetDays, row, 2, 6, 1, 3);
 		
 		spreadSheet.createCell(sheetDays, row, 2, 1, localeExcel.getStringKey("excel_report_monthly_flow_company"));
 		spreadSheet.createCell(sheetDays, row, 2, 7, localeExcel.getStringKey("excel_report_monthly_flow_day_week")); 
 		spreadSheet.createCell(sheetDays, row, 2, 8, "week day");
 		
 		spreadSheet.createCell(sheetDays, row, 3, 1, localeExcel.getStringKey("excel_report_monthly_flow_lot")+": year");
 		  
 		spreadSheet.createCell(sheetDays, row, 4, 1, localeExcel.getStringKey("excel_report_monthly_flow_low_equipment"));
 		spreadSheet.createCell(sheetDays, row, 4, 2, "EQUIP");
 		
 		spreadSheet.createCell(sheetDays, row, 5, 1, localeExcel.getStringKey("excel_report_monthly_flow_road"));
 		spreadSheet.createCell(sheetDays, row, 5, 2, "road");
 		spreadSheet.createCell(sheetDays, row, 5, 3, localeExcel.getStringKey("excel_report_monthly_flow_strech"));
 		spreadSheet.createCell(sheetDays, row, 5, 4, "km");
 		spreadSheet.createCell(sheetDays, row, 5, 5, localeExcel.getStringKey("excel_report_monthly_flow_complement"));
 		spreadSheet.createCell(sheetDays, row, 5, 6, "km");
 		spreadSheet.createCell(sheetDays, row, 5, 7, localeExcel.getStringKey("excel_report_monthly_flow_equiv"));
 		spreadSheet.createCell(sheetDays, row, 5, 8, "eqvNmb");
 		
 		spreadSheet.createCell(sheetDays, row, 5, 11, localeExcel.getStringKey("excel_report_monthly_flow_road"));
 		spreadSheet.createCell(sheetDays, row, 5, 12, "road");
 		spreadSheet.createCell(sheetDays, row, 5, 13, localeExcel.getStringKey("excel_report_monthly_flow_strech"));
 		spreadSheet.createCell(sheetDays, row, 5, 14, "km");
 		spreadSheet.createCell(sheetDays, row, 5, 15, localeExcel.getStringKey("excel_report_monthly_flow_complement"));
 		spreadSheet.createCell(sheetDays, row, 5, 16, "km");
 		spreadSheet.createCell(sheetDays, row, 5, 17, localeExcel.getStringKey("excel_report_monthly_flow_equiv"));
 		spreadSheet.createCell(sheetDays, row, 5, 18, "eqvNmb");
 		
 		spreadSheet.createCell(sheetDays, row, 8, 1, localeExcel.getStringKey("excel_report_monthly_flow_low_hour"));
 		spreadSheet.createCell(sheetDays, row, 8, 2, "sentido1");
 		spreadSheet.createCell(sheetDays, row, 8, 6, localeExcel.getStringKey("excel_report_monthly_flow_low_total"));
 		spreadSheet.createCell(sheetDays, row, 8, 7, localeExcel.getStringKey("excel_report_monthly_flow_rate"));
 		spreadSheet.createCell(sheetDays, row, 8, 8, localeExcel.getStringKey("excel_report_monthly_flow_speed_op"));
 		
 		spreadSheet.createCell(sheetDays, row, 8, 11, localeExcel.getStringKey("excel_report_monthly_flow_low_hour"));
 		spreadSheet.createCell(sheetDays, row, 8, 12, "sentido2");
 		spreadSheet.createCell(sheetDays, row, 8, 16, localeExcel.getStringKey("excel_report_monthly_flow_low_total"));
 		spreadSheet.createCell(sheetDays, row, 8, 17, localeExcel.getStringKey("excel_report_monthly_flow_rate"));
 		spreadSheet.createCell(sheetDays, row, 8, 18, localeExcel.getStringKey("excel_report_monthly_flow_speed_op"));
 		
 		spreadSheet.createCell(sheetDays, row, 8, 21, localeExcel.getStringKey("excel_report_monthly_flow_low_hour"));
 		spreadSheet.createCell(sheetDays, row, 8, 22, "sentido1"); 	
 		spreadSheet.createCell(sheetDays, row, 8, 25, localeExcel.getStringKey("excel_report_monthly_flow_rate"));
 		spreadSheet.createCell(sheetDays, row, 8, 26, localeExcel.getStringKey("excel_report_monthly_flow_speed"));
 		
 		spreadSheet.createCell(sheetDays, row, 8, 28, localeExcel.getStringKey("excel_report_monthly_flow_low_hour"));
 		spreadSheet.createCell(sheetDays, row, 8, 29, "sentido2"); 	
 		spreadSheet.createCell(sheetDays, row, 8, 32, localeExcel.getStringKey("excel_report_monthly_flow_rate"));
 		spreadSheet.createCell(sheetDays, row, 8, 33, localeExcel.getStringKey("excel_report_monthly_flow_speed"));
 		
 		spreadSheet.createCell(sheetDays, row, 9, 2, localeExcel.getStringKey("excel_report_monthly_flow_motorcycle"));
 		spreadSheet.createCell(sheetDays, row, 9, 3, localeExcel.getStringKey("excel_report_monthly_flow_light"));
 		spreadSheet.createCell(sheetDays, row, 9, 4, localeExcel.getStringKey("excel_report_monthly_flow_comm"));
 		spreadSheet.createCell(sheetDays, row, 9, 5, localeExcel.getStringKey("excel_report_monthly_flow_low_total"));
 		spreadSheet.createCell(sheetDays, row, 9, 6, localeExcel.getStringKey("excel_report_monthly_flow_accumulated"));
 		spreadSheet.createCell(sheetDays, row, 9, 7, localeExcel.getStringKey("excel_report_monthly_flow_cph"));
 		spreadSheet.createCell(sheetDays, row, 9, 8, localeExcel.getStringKey("excel_report_monthly_flow_fifteen_min"));
 		spreadSheet.createCell(sheetDays, row, 9, 9, localeExcel.getStringKey("excel_report_monthly_flow_sixty_min"));
 		
 		spreadSheet.createCell(sheetDays, row, 9, 12, localeExcel.getStringKey("excel_report_monthly_flow_motorcycle"));
 		spreadSheet.createCell(sheetDays, row, 9, 13, localeExcel.getStringKey("excel_report_monthly_flow_light"));
 		spreadSheet.createCell(sheetDays, row, 9, 14, localeExcel.getStringKey("excel_report_monthly_flow_comm"));
 		spreadSheet.createCell(sheetDays, row, 9, 15, localeExcel.getStringKey("excel_report_monthly_flow_low_total"));
 		spreadSheet.createCell(sheetDays, row, 9, 16, localeExcel.getStringKey("excel_report_monthly_flow_accumulated"));
 		spreadSheet.createCell(sheetDays, row, 9, 17, localeExcel.getStringKey("excel_report_monthly_flow_cph"));
 		spreadSheet.createCell(sheetDays, row, 9, 18, localeExcel.getStringKey("excel_report_monthly_flow_fifteen_min"));
 		spreadSheet.createCell(sheetDays, row, 9, 19, localeExcel.getStringKey("excel_report_monthly_flow_sixty_min"));
 		 		 		
 		spreadSheet.createCell(sheetDays, row, 9, 22, localeExcel.getStringKey("excel_report_monthly_flow_motorcycle"));
 		spreadSheet.createCell(sheetDays, row, 9, 23, localeExcel.getStringKey("excel_report_monthly_flow_light"));
 		spreadSheet.createCell(sheetDays, row, 9, 24, localeExcel.getStringKey("excel_report_monthly_flow_comm")); 
 		spreadSheet.createCell(sheetDays, row, 9, 25, localeExcel.getStringKey("excel_report_monthly_flow_cph"));
 		spreadSheet.createCell(sheetDays, row, 9, 26, localeExcel.getStringKey("excel_report_monthly_flow_kmh"));
 		 		
 		spreadSheet.createCell(sheetDays, row, 9, 29, localeExcel.getStringKey("excel_report_monthly_flow_motorcycle"));
 		spreadSheet.createCell(sheetDays, row, 9, 30, localeExcel.getStringKey("excel_report_monthly_flow_light"));
 		spreadSheet.createCell(sheetDays, row, 9, 31, localeExcel.getStringKey("excel_report_monthly_flow_comm")); 
 		spreadSheet.createCell(sheetDays, row, 9, 32, localeExcel.getStringKey("excel_report_monthly_flow_cph"));
 		spreadSheet.createCell(sheetDays, row, 9, 33, localeExcel.getStringKey("excel_report_monthly_flow_kmh"));
 		
 		//SHEET DAYS STYLES 	    
 		spreadSheet.setStyle(sheetDays, row, 1, monthBoldLeftStyle, 1);   spreadSheet.setStyle(sheetDays, row, 1, monthBoldLeftStyle, 7);
 	    spreadSheet.setStyle(sheetDays, row, 2, monthBoldLeftStyle, 1);   spreadSheet.setStyle(sheetDays, row, 2, monthBoldLeftStyle, 7);  
 	    spreadSheet.setStyle(sheetDays, row, 3, monthBoldLeftStyle, 1);   spreadSheet.setStyle(sheetDays, row, 4, monthBoldLeftStyle, 1);   
 	    spreadSheet.setStyle(sheetDays, row, 5, monthBoldLeftStyle, 1);   spreadSheet.setStyle(sheetDays, row, 5, monthBoldLeftStyle, 3);  
 	    spreadSheet.setStyle(sheetDays, row, 5, monthBoldLeftStyle, 7);   spreadSheet.setStyle(sheetDays, row, 5, monthBoldLeftStyle, 11);  
 	    spreadSheet.setStyle(sheetDays, row, 5, monthBoldLeftStyle, 13);  spreadSheet.setStyle(sheetDays, row, 5, monthBoldLeftStyle, 17);  
 	    
 	    //DATA HEADER
 	    
 	    //ROW 9
 	    spreadSheet.setStyle(sheetDays, row, 8, 8, indigoBackgroundStyle, 1, 2);
 	    spreadSheet.setStyle(sheetDays, row, 8, 8, indigoBackgroundStyle, 6, 8);
	    spreadSheet.setStyle(sheetDays, row, 8, 8, indigoBackgroundStyle, 11, 12);
	    spreadSheet.setStyle(sheetDays, row, 8, 8, indigoBackgroundStyle, 16, 18);
	    spreadSheet.setStyle(sheetDays, row, 8, 8, indigoBackgroundStyle, 21, 22);
	    spreadSheet.setStyle(sheetDays, row, 8, 8, indigoBackgroundStyle, 25, 26);
	    spreadSheet.setStyle(sheetDays, row, 8, 8, indigoBackgroundStyle, 28, 29);
	    spreadSheet.setStyle(sheetDays, row, 8, 8, indigoBackgroundStyle, 32, 33);
	    
	    //ROW 10
	    spreadSheet.setStyle(sheetDays, row, 9, 9, indigoBackgroundStyle, 2, 9);
	    spreadSheet.setStyle(sheetDays, row, 9, 9, indigoBackgroundStyle, 6, 8);	    
 	    spreadSheet.setStyle(sheetDays, row, 9, 9, indigoBackgroundStyle, 12, 19);
 	    spreadSheet.setStyle(sheetDays, row, 9, 9, indigoBackgroundStyle, 22, 26);
 	    spreadSheet.setStyle(sheetDays, row, 9, 9, indigoBackgroundStyle, 29, 33);
 	    
 	   //DATA HEADER
 	     	 	    
	    //SHEET DAYS MERGE CELLS 	     	     	    	    
	    spreadSheet.mergeCells(sheetDays, "B2:E2");
	    spreadSheet.mergeCells(sheetDays, "B3:G3");
	    spreadSheet.mergeCells(sheetDays, "B4:C4");
	    
	    spreadSheet.mergeCells(sheetDays, "B9:B10");
	    spreadSheet.mergeCells(sheetDays, "C9:F9");	
	    spreadSheet.mergeCells(sheetDays, "I9:J9");
	    
	    spreadSheet.mergeCells(sheetDays, "L9:L10");
	    spreadSheet.mergeCells(sheetDays, "M9:P9");
	    spreadSheet.mergeCells(sheetDays, "S9:T9");
	    
	    spreadSheet.mergeCells(sheetDays, "W9:Y9");
	    spreadSheet.mergeCells(sheetDays, "V9:V10");
	    
	    spreadSheet.mergeCells(sheetDays, "AD9:AF9");
	    spreadSheet.mergeCells(sheetDays, "AC9:AC10");
	    
	    //SHEET DAYS COLUMNS
		spreadSheet.columnsWidth(sheetDays, 0, 700);
		spreadSheet.columnsWidth(sheetDays, 1, 3500);		
		spreadSheet.columnsWidth(sheetDays, 11, 3500);
	  	    
	    for(int i=2; i < 10; i++)					
			sheetDays.autoSizeColumn(i);

		for(int i=12; i< 19; i++)					
			sheetDays.autoSizeColumn(i);

		for(int i=22; i< 27; i++)					
			sheetDays.autoSizeColumn(i);

		for(int i=29; i< 34; i++)					
			sheetDays.autoSizeColumn(i);		 	
 		
 	     }
 	  
 	   //VDM SHEET START
 	  
 	    XSSFSheet sheetVDM = workbook.createSheet(localeExcel.getStringKey("excel_single_sat_sheet_vdm_name"));
 	  
 		spreadSheet.createRows(sheetVDM, row, 1, 9);
 		spreadSheet.createCell(sheetVDM, row, 1, 1, localeExcel.getStringKey("excel_report_monthly_flow_strech_title"));
 		
 		spreadSheet.createCell(sheetVDM, row, 2, 1, localeExcel.getStringKey("excel_report_monthly_flow_company"));
 		
 		spreadSheet.createCell(sheetVDM, row, 3, 1, localeExcel.getStringKey("excel_report_monthly_flow_lot"));
 	 		 		
 		spreadSheet.createCell(sheetVDM, row, 4, 1, localeExcel.getStringKey("excel_report_monthly_flow_low_equipment"));
 		spreadSheet.createCell(sheetVDM, row, 4, 2, "EQUIP");
 		 		
 		spreadSheet.createCell(sheetVDM, row, 5, 1, localeExcel.getStringKey("excel_report_monthly_flow_company"));
 		
 		spreadSheet.createCell(sheetVDM, row, 5, 1, localeExcel.getStringKey("excel_report_monthly_flow_road"));
 		spreadSheet.createCell(sheetVDM, row, 5, 2, "road");
 		spreadSheet.createCell(sheetVDM, row, 5, 3, localeExcel.getStringKey("excel_report_monthly_flow_strech"));
 		spreadSheet.createCell(sheetVDM, row, 5, 4, "km");
 		spreadSheet.createCell(sheetVDM, row, 5, 5, localeExcel.getStringKey("excel_report_monthly_flow_complement"));
 		spreadSheet.createCell(sheetVDM, row, 5, 6, "km");
 		spreadSheet.createCell(sheetVDM, row, 5, 7, localeExcel.getStringKey("excel_report_monthly_flow_low_month"));
 		spreadSheet.createCell(sheetVDM, row, 5, 8, "dateMonth");
 		
 		spreadSheet.createCell(sheetVDM, row, 8, 1, localeExcel.getStringKey("excel_report_monthly_flow_low_hour"));
 		spreadSheet.createCell(sheetVDM, row, 8, 2, "DIR1");
 		spreadSheet.createCell(sheetVDM, row, 8, 5, localeExcel.getStringKey("excel_report_monthly_flow_rate"));
 		
 		spreadSheet.createCell(sheetVDM, row, 8, 1, localeExcel.getStringKey("excel_report_monthly_flow_low_hour"));
 		spreadSheet.createCell(sheetVDM, row, 8, 2, "DIR1");
 		spreadSheet.createCell(sheetVDM, row, 8, 5, localeExcel.getStringKey("excel_report_monthly_flow_rate"));
 		
 		spreadSheet.createCell(sheetVDM, row, 8, 7, localeExcel.getStringKey("excel_report_monthly_flow_low_hour"));
 		spreadSheet.createCell(sheetVDM, row, 8, 8, "DIR2");
 		spreadSheet.createCell(sheetVDM, row, 8, 11, localeExcel.getStringKey("excel_report_monthly_flow_rate"));
 	
 		spreadSheet.createCell(sheetVDM, row, 9, 2, localeExcel.getStringKey("excel_report_monthly_flow_motorcycle"));
 		spreadSheet.createCell(sheetVDM, row, 9, 3, localeExcel.getStringKey("excel_report_monthly_flow_light"));
 		spreadSheet.createCell(sheetVDM, row, 9, 4, localeExcel.getStringKey("excel_report_monthly_flow_comm")); 
 		spreadSheet.createCell(sheetVDM, row, 9, 5, localeExcel.getStringKey("excel_report_monthly_flow_cph"));
 		
		spreadSheet.createCell(sheetVDM, row, 9, 8, localeExcel.getStringKey("excel_report_monthly_flow_motorcycle"));
 		spreadSheet.createCell(sheetVDM, row, 9, 9, localeExcel.getStringKey("excel_report_monthly_flow_light"));
 		spreadSheet.createCell(sheetVDM, row, 9, 10, localeExcel.getStringKey("excel_report_monthly_flow_comm")); 
 		spreadSheet.createCell(sheetVDM, row, 9, 11, localeExcel.getStringKey("excel_report_monthly_flow_cph"));
 		
 		//SHEET VDM STYLES 	  
 		
 		spreadSheet.setStyle(sheetVDM, row, 1, monthBoldLeftStyle, 1);   spreadSheet.setStyle(sheetVDM, row, 2, monthBoldLeftStyle, 1);   
 	    spreadSheet.setStyle(sheetVDM, row, 3, monthBoldLeftStyle, 1);   spreadSheet.setStyle(sheetVDM, row, 4, monthBoldLeftStyle, 1);   
 	    spreadSheet.setStyle(sheetVDM, row, 5, monthBoldLeftStyle, 1);   spreadSheet.setStyle(sheetVDM, row, 5, monthBoldLeftStyle, 3);  
 	    spreadSheet.setStyle(sheetVDM, row, 5, monthBoldLeftStyle, 7);   
 	    
 	    //DATA HEADER
 	    
 	    //ROW 9
 	    spreadSheet.setStyle(sheetVDM, row, 8, 8, indigoBackgroundStyle, 1, 2);
 	    spreadSheet.setStyle(sheetVDM, row, 8, 8, indigoBackgroundStyle, 5, 5);
	    spreadSheet.setStyle(sheetVDM, row, 8, 8, indigoBackgroundStyle, 7, 8);
	    spreadSheet.setStyle(sheetVDM, row, 8, 8, indigoBackgroundStyle, 11, 11);
	  	    
	    //ROW 10
	    spreadSheet.setStyle(sheetVDM, row, 9, 9, indigoBackgroundStyle, 2, 5);
	    spreadSheet.setStyle(sheetVDM, row, 9, 9, indigoBackgroundStyle, 8, 11);	   
 	    
 	    //DATA HEADER
 	     	 	    
	    //SHEET VDM MERGE CELLS 	     	     	    	    
	    spreadSheet.mergeCells(sheetVDM, "B2:E2");
	    spreadSheet.mergeCells(sheetVDM, "B3:G3");
	    spreadSheet.mergeCells(sheetVDM, "B4:C4");
	    spreadSheet.mergeCells(sheetVDM, "I6:K6");
	    	    
	    spreadSheet.mergeCells(sheetVDM, "B9:B10");
	    spreadSheet.mergeCells(sheetVDM, "C9:E9");	
	    spreadSheet.mergeCells(sheetVDM, "H9:H10");
	    spreadSheet.mergeCells(sheetVDM, "I9:K9");
	   	    
	    //SHEET VDM COLUMNS
		spreadSheet.columnsWidth(sheetVDM, 0, 700);
		spreadSheet.columnsWidth(sheetVDM, 1, 3500);		
		spreadSheet.columnsWidth(sheetVDM, 11, 3500);
	  	    
	    for(int i=2; i < 6; i++)					
			sheetVDM.autoSizeColumn(i);

		for(int i=8; i< 12; i++)					
			sheetVDM.autoSizeColumn(i);

 			
 	    // VDM SHEET END
 		 
 		// SHEET CHARTS
 		 
 		XSSFSheet sheetDadosGraficoFluxo = workbook.createSheet(localeExcel.getStringKey("excel_single_sat_sheet_flow_chart_name"));
		XSSFSheet sheetDadosGraficoDensidade = workbook.createSheet(localeExcel.getStringKey("excel_single_sat_sheet_density_chart_name"));
		XSSFSheet sheetAnalisePD1 = workbook.createSheet(localeExcel.getStringKey("excel_single_sat_sheet_double_lane_highway_name")+direction1);
		XSSFSheet sheetAnalisePD2 = workbook.createSheet(localeExcel.getStringKey("excel_single_sat_sheet_double_lane_highway_name")+direction2);
 		
 		//FLOW SHEET
		
		spreadSheet.createRows(sheetDadosGraficoFluxo, row, 1, 2);
		
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 1, 1, localeExcel.getStringKey("excel_report_monthly_flow_low_day"));
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 1, 2, localeExcel.getStringKey("excel_report_monthly_flow_low_hour"));
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 1, 3, "sentido1");
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 1, 6, localeExcel.getStringKey("excel_report_monthly_flow_rate"));
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 1, 7, localeExcel.getStringKey("excel_report_monthly_flow_vp"));
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 1, 8, "sentido2");
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 1, 11, localeExcel.getStringKey("excel_report_monthly_flow_rate"));
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 1, 12, localeExcel.getStringKey("excel_report_monthly_flow_vp"));	
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 1, 13, localeExcel.getStringKey("excel_report_monthly_flow_mvs_road")+"DIR1");
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 1, 18, localeExcel.getStringKey("excel_report_monthly_flow_mvs_road")+"DIR2");
		
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 2, 3, localeExcel.getStringKey("excel_report_monthly_flow_motorcycle"));
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 2, 4, localeExcel.getStringKey("excel_report_monthly_flow_light"));
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 2, 5, localeExcel.getStringKey("excel_report_monthly_flow_comm"));
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 2, 6, localeExcel.getStringKey("excel_report_monthly_flow_cph"));
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 2, 7, localeExcel.getStringKey("excel_report_monthly_flow_cph"));
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 2, 8, localeExcel.getStringKey("excel_report_monthly_flow_motorcycle"));
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 2, 9, localeExcel.getStringKey("excel_report_monthly_flow_light"));
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 2, 10, localeExcel.getStringKey("excel_report_monthly_flow_comm"));
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 2, 11, localeExcel.getStringKey("excel_report_monthly_flow_cph"));
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 2, 12, localeExcel.getStringKey("excel_report_monthly_flow_cph"));
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 2, 13, localeExcel.getStringKey("excel_report_monthly_flow_nsa"));
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 2, 14, localeExcel.getStringKey("excel_report_monthly_flow_nsb"));
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 2, 15, localeExcel.getStringKey("excel_report_monthly_flow_nsc"));
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 2, 16, localeExcel.getStringKey("excel_report_monthly_flow_nsd"));
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 2, 17, localeExcel.getStringKey("excel_report_monthly_flow_nse"));
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 2, 18, localeExcel.getStringKey("excel_report_monthly_flow_nsa"));
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 2, 19, localeExcel.getStringKey("excel_report_monthly_flow_nsb"));
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 2, 20, localeExcel.getStringKey("excel_report_monthly_flow_nsc"));
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 2, 21, localeExcel.getStringKey("excel_report_monthly_flow_nsd"));
		spreadSheet.createCell(sheetDadosGraficoFluxo, row, 2, 22, localeExcel.getStringKey("excel_report_monthly_flow_nse"));
		
        //FLOW SHEET STYLES 	  
 		
		//DATA HEADER
		
		//ROW 2
		spreadSheet.setStyle(sheetDadosGraficoFluxo, row, 1, 1, indigoBackgroundStyle, 1, 3);
		spreadSheet.setStyle(sheetDadosGraficoFluxo, row, 1, 1, indigoBackgroundStyle, 6, 8);
		spreadSheet.setStyle(sheetDadosGraficoFluxo, row, 1, 1, indigoBackgroundStyle, 11, 13);
		spreadSheet.setStyle(sheetDadosGraficoFluxo, row, 1, indigoBackgroundStyle, 18);
		
		//ROW 3
 		spreadSheet.setStyle(sheetDadosGraficoFluxo, row, 2, 2, indigoBackgroundStyle, 3,22);
 		 	    
 	    //DATA HEADER
 	     	 	    
	    //FLOW SHEET MERGE CELLS 	     	     	    	    
	    spreadSheet.mergeCells(sheetDadosGraficoFluxo, "B2:B3");
	    spreadSheet.mergeCells(sheetDadosGraficoFluxo, "C2:C3");
	    spreadSheet.mergeCells(sheetDadosGraficoFluxo, "D2:F2");
	    spreadSheet.mergeCells(sheetDadosGraficoFluxo, "I2:K2");	    	    
	    spreadSheet.mergeCells(sheetDadosGraficoFluxo, "N2:R2");
	    spreadSheet.mergeCells(sheetDadosGraficoFluxo, "S2:W2");	
	   	   	    
	    //FLOW SHEET COLUMNS
		spreadSheet.columnsWidth(sheetDadosGraficoFluxo, 0, 700);
		  	    
	    for(int i=3; i < 21; i++)					
	    	sheetDadosGraficoFluxo.autoSizeColumn(i);
		
		//DENSITY SHEET  
		
		spreadSheet.createRows(sheetDadosGraficoDensidade, row, 1, 2);
		
		spreadSheet.createCell(sheetDadosGraficoDensidade, row, 1, 1, localeExcel.getStringKey("excel_report_monthly_flow_low_day"));
		spreadSheet.createCell(sheetDadosGraficoDensidade, row, 1, 2, localeExcel.getStringKey("excel_report_monthly_flow_low_hour"));
		spreadSheet.createCell(sheetDadosGraficoDensidade, row, 1, 3, direction1);
		spreadSheet.createCell(sheetDadosGraficoDensidade, row, 1, 4, direction2);
		spreadSheet.createCell(sheetDadosGraficoDensidade, row, 1, 5, localeExcel.getStringKey("excel_report_monthly_flow_max_density_road")+"DIR1");
		spreadSheet.createCell(sheetDadosGraficoDensidade, row, 1, 10, localeExcel.getStringKey("excel_report_monthly_flow_max_density_road")+"DIR2");
		
		spreadSheet.createCell(sheetDadosGraficoDensidade, row, 2, 3, localeExcel.getStringKey("excel_report_monthly_flow_density"));
		spreadSheet.createCell(sheetDadosGraficoDensidade, row, 2, 4, localeExcel.getStringKey("excel_report_monthly_flow_density"));
		spreadSheet.createCell(sheetDadosGraficoDensidade, row, 2, 5, localeExcel.getStringKey("excel_report_monthly_flow_nsa"));
		spreadSheet.createCell(sheetDadosGraficoDensidade, row, 2, 6, localeExcel.getStringKey("excel_report_monthly_flow_nsb"));
		spreadSheet.createCell(sheetDadosGraficoDensidade, row, 2, 7, localeExcel.getStringKey("excel_report_monthly_flow_nsc"));
		spreadSheet.createCell(sheetDadosGraficoDensidade, row, 2, 8, localeExcel.getStringKey("excel_report_monthly_flow_nsd"));
		spreadSheet.createCell(sheetDadosGraficoDensidade, row, 2, 9, localeExcel.getStringKey("excel_report_monthly_flow_nse"));
		spreadSheet.createCell(sheetDadosGraficoDensidade, row, 2, 10, localeExcel.getStringKey("excel_report_monthly_flow_nsa"));
		spreadSheet.createCell(sheetDadosGraficoDensidade, row, 2, 11, localeExcel.getStringKey("excel_report_monthly_flow_nsb"));
		spreadSheet.createCell(sheetDadosGraficoDensidade, row, 2, 12, localeExcel.getStringKey("excel_report_monthly_flow_nsc"));
		spreadSheet.createCell(sheetDadosGraficoDensidade, row, 2, 13, localeExcel.getStringKey("excel_report_monthly_flow_nsd"));
		spreadSheet.createCell(sheetDadosGraficoDensidade, row, 2, 14, localeExcel.getStringKey("excel_report_monthly_flow_nse"));
		
		//DENSITY SHEET STYLES 	  
 		
		//DATA HEADER
				
		//ROW 2
		spreadSheet.setStyle(sheetDadosGraficoDensidade, row, 1, 1, indigoBackgroundStyle, 1, 3);
		spreadSheet.setStyle(sheetDadosGraficoDensidade, row, 1, 1, indigoBackgroundStyle, 4, 5);
		spreadSheet.setStyle(sheetDadosGraficoDensidade, row, 1, indigoBackgroundStyle, 10);
					
		//ROW 3
		spreadSheet.setStyle(sheetDadosGraficoDensidade, row, 2, 2, indigoBackgroundStyle, 3, 14);
		 		 	    
	   //DATA HEADER
		 	     	 	    
	  //DENSITY SHEET MERGE CELLS 	     	     	    	    
	   spreadSheet.mergeCells(sheetDadosGraficoDensidade, "B2:B3");
	   spreadSheet.mergeCells(sheetDadosGraficoDensidade, "C2:C3");
	   spreadSheet.mergeCells(sheetDadosGraficoDensidade, "F2:J2");
	   spreadSheet.mergeCells(sheetDadosGraficoDensidade, "K2:O2");	    	    
	
	  //DENSITY SHEET COLUMNS
	   spreadSheet.columnsWidth(sheetDadosGraficoDensidade, 0, 700);
				  	    
	    for(int i=3; i < 15; i++)					
		sheetDadosGraficoDensidade.autoSizeColumn(i);
						
		// Double Track Analysis DIR 1
		
		spreadSheet.createRows(sheetAnalisePD1, row, 1, 17);
		
		spreadSheet.createCell(sheetAnalisePD1, row, 1, 1, localeExcel.getStringKey("excel_report_monthly_flow_company"));
		
		spreadSheet.createCell(sheetAnalisePD1, row, 2, 1, localeExcel.getStringKey("excel_report_monthly_flow_multitrack_track_segment_header"));
		
		spreadSheet.createCell(sheetAnalisePD1, row, 4, 1, localeExcel.getStringKey("excel_report_monthly_flow_road_header"));
		spreadSheet.createCell(sheetAnalisePD1, row, 4, 2, ""+direction1);
		spreadSheet.createCell(sheetAnalisePD1, row, 4, 3, localeExcel.getStringKey("excel_report_monthly_flow_ground_header"));
		spreadSheet.createCell(sheetAnalisePD1, row, 4, 4, localeExcel.getStringKey("excel_report_monthly_flow_ground"));
		spreadSheet.createCell(sheetAnalisePD1, row, 4, 5, localeExcel.getStringKey("excel_report_monthly_flow_service_level_header"));
		spreadSheet.createCell(sheetAnalisePD1, row, 4, 6, localeExcel.getStringKey("excel_report_monthly_flow_service_level"));
		spreadSheet.createCell(sheetAnalisePD1, row, 4, 7, localeExcel.getStringKey("excel_report_monthly_flow_service_level_bw"));
		spreadSheet.createCell(sheetAnalisePD1, row, 4, 8, localeExcel.getStringKey("excel_report_monthly_flow_service_level_max"));
		
		spreadSheet.createCell(sheetAnalisePD1, row, 5, 1, localeExcel.getStringKey("excel_report_monthly_flow_road_header"));
		spreadSheet.createCell(sheetAnalisePD1, row, 5, 2, localeExcel.getStringKey("excel_report_monthly_flow_th1_road_header")+" ROAD");
		spreadSheet.createCell(sheetAnalisePD1, row, 5, 3, localeExcel.getStringKey("excel_report_monthly_flow_et_road_header"));
		spreadSheet.createCell(sheetAnalisePD1, row, 5, 4, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_number"));
		spreadSheet.createCell(sheetAnalisePD1, row, 5, 5, "A");
		spreadSheet.createCellWithFormula(sheetAnalisePD1, row, 5, 6, "IF($E$7>=100,700,IF($E$7>=90,630,IF($E$7>=80,560,IF($E$7>=70,490,490))))");
		spreadSheet.createCellWithFormula(sheetAnalisePD1, row, 5, 7, "IF($E$7>=100,100,IF($E$7>=90,90,IF($E$7>=80,80,IF($E$7>=70,70,70))))");
		spreadSheet.createCellWithFormula(sheetAnalisePD1, row, 5, 8, "ROUND(+G6/H6, 1)");
		
		spreadSheet.createCell(sheetAnalisePD1, row, 6, 1, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_lanes_header"));
		spreadSheet.createCell(sheetAnalisePD1, row, 6, 2, "faixas");
		spreadSheet.createCell(sheetAnalisePD1, row, 6, 3, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_ffs_header"));
		spreadSheet.createCellWithFormula(sheetAnalisePD1, row, 6, 4, "E8-E9-E10-E11-C12");
		spreadSheet.createCell(sheetAnalisePD1, row, 6, 5, "B");
		spreadSheet.createCellWithFormula(sheetAnalisePD1, row, 6, 6, "IF($E$7>=100,1100,IF($E$7>=90,990,IF($E$7>=80,880,IF($E$7>=70,770,770))))");
		spreadSheet.createCellWithFormula(sheetAnalisePD1, row, 6, 7, "IF($E$7>=100,100,IF($E$7>=90,90,IF($E$7>=80,80,IF($E$7>=70,70,70))))");
		spreadSheet.createCellWithFormula(sheetAnalisePD1, row, 6, 8, "ROUND(+G7/H7,1)");
		
		spreadSheet.createCell(sheetAnalisePD1, row, 7, 1, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_fph_header"));
		spreadSheet.createCell(sheetAnalisePD1, row, 7, 2, "fph_value");
		spreadSheet.createCell(sheetAnalisePD1, row, 7, 3, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_ffsi_header"));
		spreadSheet.createCell(sheetAnalisePD1, row, 7, 4, "fssi_value");
		spreadSheet.createCell(sheetAnalisePD1, row, 7, 5, "C");
		spreadSheet.createCellWithFormula(sheetAnalisePD1, row, 7, 6, "IF($E$7>=100,1575,IF($E$7>=90,1435,IF($E$7>=80,1280,IF($E$7>=70,1120,1120))))");
		spreadSheet.createCellWithFormula(sheetAnalisePD1, row, 7, 7, "IF($E$7>=100,98.4,IF($E$7>=90,89.8,IF($E$7>=80,80,IF($E$7>=70,70,70))))");
		spreadSheet.createCellWithFormula(sheetAnalisePD1, row, 7, 8, "ROUND(+G8/H8,1)");
				
		spreadSheet.createCell(sheetAnalisePD1, row, 8, 1, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_fphn_header"));
		spreadSheet.createCell(sheetAnalisePD1, row, 8, 2, "fphn_value");
		spreadSheet.createCell(sheetAnalisePD1, row, 8, 3, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_flw_header"));
		spreadSheet.createCell(sheetAnalisePD1, row, 8, 4, "flw_value");
		spreadSheet.createCell(sheetAnalisePD1, row, 8, 5, "D");
		spreadSheet.createCellWithFormula(sheetAnalisePD1, row, 8, 6, "IF($E$7>=100,2015,IF($E$7>=90,1860,IF($E$7>=80,1705,IF($E$7>=70,1530,1530))))");
		spreadSheet.createCellWithFormula(sheetAnalisePD1, row, 8, 7, "IF($E$7>=100,91.5,IF($E$7>=90,84.7,IF($E$7>=80,77.6,IF($E$7>=70,69.6,69.6))))");
		spreadSheet.createCellWithFormula(sheetAnalisePD1, row, 8, 8, "ROUND(+G9/H9,1)");
		
		spreadSheet.createCell(sheetAnalisePD1, row, 9, 1, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_width_header"));
		spreadSheet.createCell(sheetAnalisePD1, row, 9, 2, "width_value");
		spreadSheet.createCell(sheetAnalisePD1, row, 9, 3, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_flc_header"));
		spreadSheet.createCell(sheetAnalisePD1, row, 9, 4, "flc_value");
		spreadSheet.createCell(sheetAnalisePD1, row, 9, 5, "E");
		spreadSheet.createCellWithFormula(sheetAnalisePD1, row, 9, 6, "IF($E$7>=100,2200,IF($E$7>=90,2100,IF($E$7>=80,2000,IF($E$7>=70,1900,1900))))");
		spreadSheet.createCellWithFormula(sheetAnalisePD1, row, 9, 7, "IF($E$7>=100,88,IF($E$7>=90,80.8,IF($E$7>=80,74.1,IF($E$7>=70,67.9,67.9))))");
		spreadSheet.createCellWithFormula(sheetAnalisePD1, row, 9, 8, "ROUND(+G10/H10,1)");
		
		spreadSheet.createCell(sheetAnalisePD1, row, 10, 1, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_obst_header"));
		spreadSheet.createCell(sheetAnalisePD1, row, 10, 2, "obst_value");
		spreadSheet.createCell(sheetAnalisePD1, row, 10, 3, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_fm_header"));
		spreadSheet.createCell(sheetAnalisePD1, row, 10, 4, "fm_value");
		spreadSheet.createCell(sheetAnalisePD1, row, 10, 5, "F");
		spreadSheet.createCell(sheetAnalisePD1, row, 10, 6, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_var_header"));
		spreadSheet.createCell(sheetAnalisePD1, row, 10, 7, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_var_header"));
		spreadSheet.createCell(sheetAnalisePD1, row, 10, 8, "var_value");
		
		spreadSheet.createCell(sheetAnalisePD1, row, 11, 1, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_fa_header"));
		spreadSheet.createCell(sheetAnalisePD1, row, 11, 2, "fa_value");
		
		spreadSheet.createCell(sheetAnalisePD1, row, 12, 1, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_description1"));
	
		spreadSheet.createCell(sheetAnalisePD1, row, 13, 1, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_description2"));
		
		spreadSheet.createCell(sheetAnalisePD1, row, 14, 1, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_description3"));
		
		spreadSheet.createCell(sheetAnalisePD1, row, 16, 1, localeExcel.getStringKey("excel_report_monthly_flow_low_hour"));	
		spreadSheet.createCell(sheetAnalisePD1, row, 16, 2, localeExcel.getStringKey("excel_report_monthly_flow_low_date"));	
		spreadSheet.createCell(sheetAnalisePD1, row, 16, 3, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_volume_rsd"));	
		spreadSheet.createCell(sheetAnalisePD1, row, 16, 6, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_rate_of")+"\nFlow");	
		spreadSheet.createCell(sheetAnalisePD1, row, 16, 7, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_vp"));	
		spreadSheet.createCell(sheetAnalisePD1, row, 16, 8, localeExcel.getStringKey("excel_report_monthly_flow_speed")+" raised");	
		spreadSheet.createCell(sheetAnalisePD1, row, 16, 9, "D");	
		spreadSheet.createCell(sheetAnalisePD1, row, 16, 10, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_ns_calculated"));	
		spreadSheet.createCell(sheetAnalisePD1, row, 16, 12, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_hour_of_lvl"));	
		spreadSheet.createCell(sheetAnalisePD1, row, 16, 16, "Month / year");	
		
		spreadSheet.createCell(sheetAnalisePD1, row, 17, 3, localeExcel.getStringKey("excel_report_monthly_flow_motorcycle"));	
		spreadSheet.createCell(sheetAnalisePD1, row, 17, 4, localeExcel.getStringKey("excel_report_monthly_flow_light"));	
		spreadSheet.createCell(sheetAnalisePD1, row, 17, 5, localeExcel.getStringKey("excel_report_monthly_flow_comm"));	
		spreadSheet.createCell(sheetAnalisePD1, row, 17, 12, "A");	
		spreadSheet.createCell(sheetAnalisePD1, row, 17, 13, "B");	
		spreadSheet.createCell(sheetAnalisePD1, row, 17, 14, "C");	
		spreadSheet.createCell(sheetAnalisePD1, row, 17, 15, "D");	
		spreadSheet.createCell(sheetAnalisePD1, row, 17, 16, "E");	
		spreadSheet.createCell(sheetAnalisePD1, row, 17, 17, "F");	
		
		//Double Track Analysis DIR 1 STYLES 	  
 		
	   //DATA HEADER
						
	  //ROW 2 AND 3
	  spreadSheet.setStyle(sheetAnalisePD1, row, 1, 2, monthBoldCenterStyle, 1, 1 );
	  
	  //ROW 4
	  spreadSheet.setStyle(sheetAnalisePD1, row, 4, 4, indigoBackgroundStyle, 1, 8);
	  
	  //ROW 5 TO ROW 11
	  spreadSheet.setStyle(sheetAnalisePD1, row, 5, 10, monthBodybackgroundStyle, 1, 8);
	  spreadSheet.setStyle(sheetAnalisePD1, row, 11, 11, monthBodybackgroundStyle, 1, 2);
	  
	  //ROW 12 TO ROW 14
	  spreadSheet.setStyle(sheetAnalisePD1, row, 12, monthLowDescriptStyle, 1);
	  spreadSheet.setStyle(sheetAnalisePD1, row, 13, monthLowDescriptStyle, 1);
	  spreadSheet.setStyle(sheetAnalisePD1, row, 14, monthLowDescriptStyle, 1);
							
	 //ROW 17
	 spreadSheet.setStyle(sheetAnalisePD1, row, 16, 16, indigoBackgroundStyle, 1, 3);
	 spreadSheet.setStyle(sheetAnalisePD1, row, 16, 16, indigoBackgroundStyle, 6, 10);
	 spreadSheet.setStyle(sheetAnalisePD1, row, 16, indigoBackgroundStyle, 12);
	 spreadSheet.setStyle(sheetAnalisePD1, row, 16, indigoBackgroundStyle, 16);
	 
	 spreadSheet.setStyle(sheetAnalisePD1, row, 17, 17, indigoBackgroundStyle, 3, 5);
	 spreadSheet.setStyle(sheetAnalisePD1, row, 17, 17, indigoBackgroundStyle, 12, 17);
	 			 		 	    
	 //DATA HEADER
				 	     	 	    
	//Double Track Analysis DIR 1 MERGE CELLS 	     	     	    	    
	spreadSheet.mergeCells(sheetAnalisePD1, "B2:J2");
	spreadSheet.mergeCells(sheetAnalisePD1, "B3:J3");
    spreadSheet.mergeCells(sheetAnalisePD1, "B13:D13");
	spreadSheet.mergeCells(sheetAnalisePD1, "B14:D14");
	spreadSheet.mergeCells(sheetAnalisePD1, "B15:D15");	
	
	spreadSheet.mergeCells(sheetAnalisePD1, "B17:B18");	
	spreadSheet.mergeCells(sheetAnalisePD1, "C17:C18");	
	spreadSheet.mergeCells(sheetAnalisePD1, "D17:F17");	
	spreadSheet.mergeCells(sheetAnalisePD1, "G17:G18");	
	spreadSheet.mergeCells(sheetAnalisePD1, "H17:H18");	
	spreadSheet.mergeCells(sheetAnalisePD1, "I17:I18");	
	spreadSheet.mergeCells(sheetAnalisePD1, "J17:J18");	
	spreadSheet.mergeCells(sheetAnalisePD1, "K17:K18");	
	spreadSheet.mergeCells(sheetAnalisePD1, "M17:P17");		
	spreadSheet.mergeCells(sheetAnalisePD1, "Q17:R17");	
	
	//Double Track Analysis DIR 1 COLUMNS
	spreadSheet.columnsWidth(sheetAnalisePD1, 0, 700);
	spreadSheet.columnsWidth(sheetAnalisePD1,1, 3800);
	spreadSheet.columnsWidth(sheetAnalisePD1,2, 3800);
	spreadSheet.columnsWidth(sheetAnalisePD1,3, 4300);
	spreadSheet.columnsWidth(sheetAnalisePD1,4, 4300);
	spreadSheet.columnsWidth(sheetAnalisePD1,5, 5000);
	spreadSheet.columnsWidth(sheetAnalisePD1,6, 4300);
	spreadSheet.columnsWidth(sheetAnalisePD1,7, 3800);
	spreadSheet.columnsWidth(sheetAnalisePD1,8, 4300);
	spreadSheet.columnsWidth(sheetAnalisePD1,9, 4300);
	spreadSheet.columnsWidth(sheetAnalisePD1,10, 3500);
	spreadSheet.columnsWidth(sheetAnalisePD1,11, 3000);
	spreadSheet.columnsWidth(sheetAnalisePD1,12, 3000);
	spreadSheet.columnsWidth(sheetAnalisePD1,13, 3000);
	spreadSheet.columnsWidth(sheetAnalisePD1,14, 3000);
	spreadSheet.columnsWidth(sheetAnalisePD1,15, 3000);
	spreadSheet.columnsWidth(sheetAnalisePD1,16, 3000);
	
			
		// Double Track Analysis DIR 2
		
		spreadSheet.createRows(sheetAnalisePD2, row, 1, 17);
		
        spreadSheet.createCell(sheetAnalisePD2, row, 1, 1, localeExcel.getStringKey("excel_report_monthly_flow_company"));
		
		spreadSheet.createCell(sheetAnalisePD2, row, 2, 1, localeExcel.getStringKey("excel_report_monthly_flow_multitrack_track_segment_header"));
		
		spreadSheet.createCell(sheetAnalisePD2, row, 4, 1, localeExcel.getStringKey("excel_report_monthly_flow_road_header"));
		spreadSheet.createCell(sheetAnalisePD2, row, 4, 2, ""+direction1);
		spreadSheet.createCell(sheetAnalisePD2, row, 4, 3, localeExcel.getStringKey("excel_report_monthly_flow_ground_header"));
		spreadSheet.createCell(sheetAnalisePD2, row, 4, 4, localeExcel.getStringKey("excel_report_monthly_flow_ground"));
		spreadSheet.createCell(sheetAnalisePD2, row, 4, 5, localeExcel.getStringKey("excel_report_monthly_flow_service_level_header"));
		spreadSheet.createCell(sheetAnalisePD2, row, 4, 6, localeExcel.getStringKey("excel_report_monthly_flow_service_level"));
		spreadSheet.createCell(sheetAnalisePD2, row, 4, 7, localeExcel.getStringKey("excel_report_monthly_flow_service_level_bw"));
		spreadSheet.createCell(sheetAnalisePD2, row, 4, 8, localeExcel.getStringKey("excel_report_monthly_flow_service_level_max"));
		
		spreadSheet.createCell(sheetAnalisePD2, row, 5, 1, localeExcel.getStringKey("excel_report_monthly_flow_road_header"));
		spreadSheet.createCell(sheetAnalisePD2, row, 5, 2, localeExcel.getStringKey("excel_report_monthly_flow_th1_road_header")+" ROAD");
		spreadSheet.createCell(sheetAnalisePD2, row, 5, 3, localeExcel.getStringKey("excel_report_monthly_flow_et_road_header"));
		spreadSheet.createCell(sheetAnalisePD2, row, 5, 4, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_number"));
		spreadSheet.createCell(sheetAnalisePD2, row, 5, 5, "A");
		spreadSheet.createCellWithFormula(sheetAnalisePD2, row, 5, 6, "IF($E$7>=100,700,IF($E$7>=90,630,IF($E$7>=80,560,IF($E$7>=70,490,490))))");
		spreadSheet.createCellWithFormula(sheetAnalisePD2, row, 5, 7, "IF($E$7>=100,100,IF($E$7>=90,90,IF($E$7>=80,80,IF($E$7>=70,70,70))))");
		spreadSheet.createCellWithFormula(sheetAnalisePD2, row, 5, 8, "ROUND(+G6/H6, 1)");
		
		spreadSheet.createCell(sheetAnalisePD2, row, 6, 1, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_lanes_header"));
		spreadSheet.createCell(sheetAnalisePD2, row, 6, 2, "faixas");
		spreadSheet.createCell(sheetAnalisePD2, row, 6, 3, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_ffs_header"));
		spreadSheet.createCellWithFormula(sheetAnalisePD2, row, 6, 4, "E8-E9-E10-E11-C12");
		spreadSheet.createCell(sheetAnalisePD2, row, 6, 5, "B");
		spreadSheet.createCellWithFormula(sheetAnalisePD2, row, 6, 6, "IF($E$7>=100,1100,IF($E$7>=90,990,IF($E$7>=80,880,IF($E$7>=70,770,770))))");
		spreadSheet.createCellWithFormula(sheetAnalisePD2, row, 6, 7, "IF($E$7>=100,100,IF($E$7>=90,90,IF($E$7>=80,80,IF($E$7>=70,70,70))))");
		spreadSheet.createCellWithFormula(sheetAnalisePD2, row, 6, 8, "ROUND(+G7/H7,1)");
		
		spreadSheet.createCell(sheetAnalisePD2, row, 7, 1, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_fph_header"));
		spreadSheet.createCell(sheetAnalisePD2, row, 7, 2, "fph_value");
		spreadSheet.createCell(sheetAnalisePD2, row, 7, 3, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_ffsi_header"));
		spreadSheet.createCell(sheetAnalisePD2, row, 7, 4, "fssi_value");
		spreadSheet.createCell(sheetAnalisePD2, row, 7, 5, "C");
		spreadSheet.createCellWithFormula(sheetAnalisePD2, row, 7, 6, "IF($E$7>=100,1575,IF($E$7>=90,1435,IF($E$7>=80,1280,IF($E$7>=70,1120,1120))))");
		spreadSheet.createCellWithFormula(sheetAnalisePD2, row, 7, 7, "IF($E$7>=100,98.4,IF($E$7>=90,89.8,IF($E$7>=80,80,IF($E$7>=70,70,70))))");
		spreadSheet.createCellWithFormula(sheetAnalisePD2, row, 7, 8, "ROUND(+G8/H8,1)");
				
		spreadSheet.createCell(sheetAnalisePD2, row, 8, 1, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_fphn_header"));
		spreadSheet.createCell(sheetAnalisePD2, row, 8, 2, "fphn_value");
		spreadSheet.createCell(sheetAnalisePD2, row, 8, 3, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_flw_header"));
		spreadSheet.createCell(sheetAnalisePD2, row, 8, 4, "flw_value");
		spreadSheet.createCell(sheetAnalisePD2, row, 8, 5, "D");
		spreadSheet.createCellWithFormula(sheetAnalisePD2, row, 8, 6, "IF($E$7>=100,2015,IF($E$7>=90,1860,IF($E$7>=80,1705,IF($E$7>=70,1530,1530))))");
		spreadSheet.createCellWithFormula(sheetAnalisePD2, row, 8, 7, "IF($E$7>=100,91.5,IF($E$7>=90,84.7,IF($E$7>=80,77.6,IF($E$7>=70,69.6,69.6))))");
		spreadSheet.createCellWithFormula(sheetAnalisePD2, row, 8, 8, "ROUND(+G9/H9,1)");
		
		spreadSheet.createCell(sheetAnalisePD2, row, 9, 1, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_width_header"));
		spreadSheet.createCell(sheetAnalisePD2, row, 9, 2, "width_value");
		spreadSheet.createCell(sheetAnalisePD2, row, 9, 3, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_flc_header"));
		spreadSheet.createCell(sheetAnalisePD2, row, 9, 4, "flc_value");
		spreadSheet.createCell(sheetAnalisePD2, row, 9, 5, "E");
		spreadSheet.createCellWithFormula(sheetAnalisePD2, row, 9, 6, "IF($E$7>=100,2200,IF($E$7>=90,2100,IF($E$7>=80,2000,IF($E$7>=70,1900,1900))))");
		spreadSheet.createCellWithFormula(sheetAnalisePD2, row, 9, 7, "IF($E$7>=100,88,IF($E$7>=90,80.8,IF($E$7>=80,74.1,IF($E$7>=70,67.9,67.9))))");
		spreadSheet.createCellWithFormula(sheetAnalisePD2, row, 9, 8, "ROUND(+G10/H10,1)");
		
		spreadSheet.createCell(sheetAnalisePD2, row, 10, 1, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_obst_header"));
		spreadSheet.createCell(sheetAnalisePD2, row, 10, 2, "obst_value");
		spreadSheet.createCell(sheetAnalisePD2, row, 10, 3, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_fm_header"));
		spreadSheet.createCell(sheetAnalisePD2, row, 10, 4, "fm_value");
		spreadSheet.createCell(sheetAnalisePD2, row, 10, 5, "F");
		spreadSheet.createCell(sheetAnalisePD2, row, 10, 6, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_var_header"));
		spreadSheet.createCell(sheetAnalisePD2, row, 10, 7, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_var_header"));
		spreadSheet.createCell(sheetAnalisePD2, row, 10, 8, "var_value");
		
		spreadSheet.createCell(sheetAnalisePD2, row, 11, 1, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_fa_header"));
		spreadSheet.createCell(sheetAnalisePD2, row, 11, 2, "fa_value");
		
		spreadSheet.createCell(sheetAnalisePD2, row, 12, 1, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_description1"));
	
		spreadSheet.createCell(sheetAnalisePD2, row, 13, 1, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_description2"));
		
		spreadSheet.createCell(sheetAnalisePD2, row, 14, 1, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_description3"));
		
		spreadSheet.createCell(sheetAnalisePD2, row, 16, 1, localeExcel.getStringKey("excel_report_monthly_flow_low_hour"));	
		spreadSheet.createCell(sheetAnalisePD2, row, 16, 2, localeExcel.getStringKey("excel_report_monthly_flow_low_date"));	
		spreadSheet.createCell(sheetAnalisePD2, row, 16, 3, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_volume_rsd"));	
		spreadSheet.createCell(sheetAnalisePD2, row, 16, 6, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_rate_of")+"\nFlow");	
		spreadSheet.createCell(sheetAnalisePD2, row, 16, 7, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_vp"));	
		spreadSheet.createCell(sheetAnalisePD2, row, 16, 8, localeExcel.getStringKey("excel_report_monthly_flow_speed")+" raised");	
		spreadSheet.createCell(sheetAnalisePD2, row, 16, 9, "D");	
		spreadSheet.createCell(sheetAnalisePD2, row, 16, 10, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_ns_calculated"));	
		spreadSheet.createCell(sheetAnalisePD2, row, 16, 12, localeExcel.getStringKey("excel_report_monthly_flow_analyze_dbl_hour_of_lvl"));	
		spreadSheet.createCell(sheetAnalisePD2, row, 16, 16, "Month / year");	
		
		spreadSheet.createCell(sheetAnalisePD2, row, 17, 3, localeExcel.getStringKey("excel_report_monthly_flow_motorcycle"));	
		spreadSheet.createCell(sheetAnalisePD2, row, 17, 4, localeExcel.getStringKey("excel_report_monthly_flow_light"));	
		spreadSheet.createCell(sheetAnalisePD2, row, 17, 5, localeExcel.getStringKey("excel_report_monthly_flow_comm"));	
		spreadSheet.createCell(sheetAnalisePD2, row, 17, 12, "A");	
		spreadSheet.createCell(sheetAnalisePD2, row, 17, 13, "B");	
		spreadSheet.createCell(sheetAnalisePD2, row, 17, 14, "C");	
		spreadSheet.createCell(sheetAnalisePD2, row, 17, 15, "D");	
		spreadSheet.createCell(sheetAnalisePD2, row, 17, 16, "E");	
		spreadSheet.createCell(sheetAnalisePD2, row, 17, 17, "F");	
		
		//Double Track Analysis DIR 1 STYLES 	  
 		
		   //DATA HEADER
							
		  //ROW 2 AND 3
		  spreadSheet.setStyle(sheetAnalisePD2, row, 1, 2, monthBoldCenterStyle, 1, 1 );
		  
		  //ROW 4
		  spreadSheet.setStyle(sheetAnalisePD2, row, 4, 4, indigoBackgroundStyle, 1, 8);
		  
		  //ROW 5 TO ROW 11
		  spreadSheet.setStyle(sheetAnalisePD2, row, 5, 10, monthBodybackgroundStyle, 1, 8);
		  spreadSheet.setStyle(sheetAnalisePD2, row, 11, 11, monthBodybackgroundStyle, 1, 2);
		  
		  //ROW 12 TO ROW 14
		  spreadSheet.setStyle(sheetAnalisePD2, row, 12, monthLowDescriptStyle, 1);
		  spreadSheet.setStyle(sheetAnalisePD2, row, 13, monthLowDescriptStyle, 1);
		  spreadSheet.setStyle(sheetAnalisePD2, row, 14, monthLowDescriptStyle, 1);
								
		 //ROW 17
		 spreadSheet.setStyle(sheetAnalisePD2, row, 16, 16, indigoBackgroundStyle, 1, 3);
		 spreadSheet.setStyle(sheetAnalisePD2, row, 16, 16, indigoBackgroundStyle, 6, 10);
		 spreadSheet.setStyle(sheetAnalisePD2, row, 16, indigoBackgroundStyle, 12);
		 spreadSheet.setStyle(sheetAnalisePD2, row, 16, indigoBackgroundStyle, 16);
		 
		 spreadSheet.setStyle(sheetAnalisePD2, row, 17, 17, indigoBackgroundStyle, 3, 5);
		 spreadSheet.setStyle(sheetAnalisePD2, row, 17, 17, indigoBackgroundStyle, 12, 17);
		 			 		 	    
		 //DATA HEADER
					 	     	 	    
		//Double Track Analysis DIR 1 MERGE CELLS 	     	     	    	    
		spreadSheet.mergeCells(sheetAnalisePD2, "B2:J2");
		spreadSheet.mergeCells(sheetAnalisePD2, "B3:J3");
	    spreadSheet.mergeCells(sheetAnalisePD2, "B13:D13");
		spreadSheet.mergeCells(sheetAnalisePD2, "B14:D14");
		spreadSheet.mergeCells(sheetAnalisePD2, "B15:D15");	
		
		spreadSheet.mergeCells(sheetAnalisePD2, "B17:B18");	
		spreadSheet.mergeCells(sheetAnalisePD2, "C17:C18");	
		spreadSheet.mergeCells(sheetAnalisePD2, "D17:F17");	
		spreadSheet.mergeCells(sheetAnalisePD2, "G17:G18");	
		spreadSheet.mergeCells(sheetAnalisePD2, "H17:H18");	
		spreadSheet.mergeCells(sheetAnalisePD2, "I17:I18");	
		spreadSheet.mergeCells(sheetAnalisePD2, "J17:J18");	
		spreadSheet.mergeCells(sheetAnalisePD2, "K17:K18");	
		spreadSheet.mergeCells(sheetAnalisePD2, "M17:P17");		
		spreadSheet.mergeCells(sheetAnalisePD2, "Q17:R17");	
		
		//Double Track Analysis DIR 1 COLUMNS
		spreadSheet.columnsWidth(sheetAnalisePD2, 0, 700);
		spreadSheet.columnsWidth(sheetAnalisePD2,1, 3800);
		spreadSheet.columnsWidth(sheetAnalisePD2,2, 3800);
		spreadSheet.columnsWidth(sheetAnalisePD2,3, 4300);
		spreadSheet.columnsWidth(sheetAnalisePD2,4, 4300);
		spreadSheet.columnsWidth(sheetAnalisePD2,5, 5000);
		spreadSheet.columnsWidth(sheetAnalisePD2,6, 4300);
		spreadSheet.columnsWidth(sheetAnalisePD2,7, 3800);
		spreadSheet.columnsWidth(sheetAnalisePD2,8, 4300);
		spreadSheet.columnsWidth(sheetAnalisePD2,9, 4300);
		spreadSheet.columnsWidth(sheetAnalisePD2,10, 3500);
		spreadSheet.columnsWidth(sheetAnalisePD2,11, 3000);
		spreadSheet.columnsWidth(sheetAnalisePD2,12, 3000);
		spreadSheet.columnsWidth(sheetAnalisePD2,13, 3000);
		spreadSheet.columnsWidth(sheetAnalisePD2,14, 3000);
		spreadSheet.columnsWidth(sheetAnalisePD2,15, 3000);
		spreadSheet.columnsWidth(sheetAnalisePD2,16, 3000);
		
		//CHARTS DEFINITIONS
		
		XSSFSheet sheetGraficoFluxo = null;
		XSSFSheet sheetGraficoDensidade = null;
		
		int gfIni = 3;
		int gfFim = 122;
			
		//CHART FLOW
		
		for (int g = 1; g <= 6; g++) { 	

			sheetGraficoFluxo = workbook.createSheet(localeExcel.getStringKey("excel_single_sat_chart_title_flow")+ g + "");
			
			spreadSheet.createRows(sheetGraficoFluxo, row, 0, 33);
			
			spreadSheet.createCell(sheetGraficoFluxo, row, 0, 1, localeExcel.getStringKey("excel_report_monthly_flow_company"));
			
			spreadSheet.createCell(sheetGraficoFluxo, row, 1, 1, localeExcel.getStringKey("excel_report_monthly_flow_lot"));
			spreadSheet.createCell(sheetGraficoFluxo, row, 2, 1, localeExcel.getStringKey("excel_report_monthly_flow_chart_rate_of_flow_lane"));
			spreadSheet.createCell(sheetGraficoFluxo, row, 3, 1, localeExcel.getStringKey("excel_report_monthly_flow_chart_strech_uniform")
			+"km"+localeExcel.getStringKey("excel_report_monthly_flow_chart_strech_uniform") +"km"+ localeExcel.getStringKey("excel_report_monthly_flow_chart_track")+"DIR1");
			spreadSheet.createCell(sheetGraficoFluxo, row, 10, 0, localeExcel.getStringKey("excel_report_monthly_flow_nse"));
			spreadSheet.createCell(sheetGraficoFluxo, row, 14, 0, localeExcel.getStringKey("excel_report_monthly_flow_nsd"));
			spreadSheet.createCell(sheetGraficoFluxo, row, 19, 0, localeExcel.getStringKey("excel_report_monthly_flow_nsc"));
			spreadSheet.createCell(sheetGraficoFluxo, row, 25, 0, localeExcel.getStringKey("excel_report_monthly_flow_nsb"));
			spreadSheet.createCell(sheetGraficoFluxo, row, 33, 0, localeExcel.getStringKey("excel_report_monthly_flow_nsa"));
			
			// CHART FLOW DIR1

			@SuppressWarnings("rawtypes")
			Drawing drawing1 = sheetGraficoFluxo.createDrawingPatriarch();
			ClientAnchor anchor1 = null; 
			anchor1 = drawing1.createAnchor(0, 0, 0, 0, 1, 6, 16, 35);

			Chart chart1 = drawing1.createChart(anchor1);  
			ChartLegend legend1 = chart1.getOrCreateLegend(); 
			legend1.setPosition(LegendPosition.RIGHT);

			LineChartData dados = chart1.getChartDataFactory().createLineChartData();
 
			ChartAxis bottomAxis1 = chart1.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
			ValueAxis leftAxis1 = chart1.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
			leftAxis1.setCrosses(AxisCrosses.AUTO_ZERO); 

			// Testar outros dias
			if (g == 6 && daysInMonth == 28)
				gfFim -= 48;
			if (g == 6 && daysInMonth == 29)
				gfFim -= 24;
			if (g == 6 && daysInMonth == 30)
				gfFim += 0;
			if (g == 6 && daysInMonth == 31)
				gfFim += 24;

			ChartDataSource<Number> xs = DataSources.fromNumericCellRange(sheetGraficoFluxo,
					new CellRangeAddress(gfIni, gfFim, 2, 2));
			ChartDataSource<Number> ys = DataSources.fromNumericCellRange(sheetGraficoFluxo,
					new CellRangeAddress(gfIni, gfFim, 6, 6));
			ChartDataSource<Number> ys1 = DataSources.fromNumericCellRange(sheetGraficoFluxo,
					new CellRangeAddress(gfIni, gfFim, 11, 11));
			ChartDataSource<Number> ys2 = DataSources.fromNumericCellRange(sheetGraficoFluxo,
					new CellRangeAddress(gfIni, gfFim, 12, 12));
			ChartDataSource<Number> ys3 = DataSources.fromNumericCellRange(sheetGraficoFluxo,
					new CellRangeAddress(gfIni, gfFim, 13, 13));
			ChartDataSource<Number> ys4 = DataSources.fromNumericCellRange(sheetGraficoFluxo,
					new CellRangeAddress(gfIni, gfFim, 14, 14));
			ChartDataSource<Number> ys5 = DataSources.fromNumericCellRange(sheetGraficoFluxo,
					new CellRangeAddress(gfIni, gfFim, 15, 15));

			LineChartSeries seriesDir1 = dados.addSeries(xs, ys); 
			seriesDir1.setTitle("DIR01");
			LineChartSeries seriesNSA01 = dados.addSeries(xs, ys1); 
			seriesNSA01.setTitle(localeExcel.getStringKey("excel_report_monthly_flow_nsa"));
			LineChartSeries seriesNSB02 = dados.addSeries(xs, ys2);
			seriesNSB02.setTitle(localeExcel.getStringKey("excel_report_monthly_flow_nsb")); 
			LineChartSeries seriesNSC03 = dados.addSeries(xs, ys3); 
			seriesNSC03.setTitle(localeExcel.getStringKey("excel_report_monthly_flow_nsc"));
			LineChartSeries seriesNSD04 = dados.addSeries(xs, ys4); 
			seriesNSD04.setTitle(localeExcel.getStringKey("excel_report_monthly_flow_nsd")); 
			LineChartSeries seriesNSE05 = dados.addSeries(xs, ys5);
			seriesNSE05.setTitle(localeExcel.getStringKey("excel_report_monthly_flow_nse"));

			chart1.plot(dados, bottomAxis1, leftAxis1);
			
			spreadSheet.createRow(sheetGraficoFluxo, row, 37);
			spreadSheet.createRows(sheetGraficoFluxo, row, 43, 76);

			spreadSheet.createCell(sheetGraficoFluxo, row, 43, 1, localeExcel.getStringKey("excel_report_monthly_flow_company"));
			
			spreadSheet.createCell(sheetGraficoFluxo, row, 44, 1, localeExcel.getStringKey("excel_report_monthly_flow_lot"));
			spreadSheet.createCell(sheetGraficoFluxo, row, 45, 1, localeExcel.getStringKey("excel_report_monthly_flow_chart_rate_of_flow_lane"));
			spreadSheet.createCell(sheetGraficoFluxo, row, 46, 1, localeExcel.getStringKey("excel_report_monthly_flow_chart_strech_uniform")
			+"km"+localeExcel.getStringKey("excel_report_monthly_flow_chart_strech_uniform") +"km"+ localeExcel.getStringKey("excel_report_monthly_flow_chart_track")+"DIR1");
			spreadSheet.createCell(sheetGraficoFluxo, row, 53, 0, localeExcel.getStringKey("excel_report_monthly_flow_nse"));
			spreadSheet.createCell(sheetGraficoFluxo, row, 57, 0, localeExcel.getStringKey("excel_report_monthly_flow_nsd"));
			spreadSheet.createCell(sheetGraficoFluxo, row, 62, 0, localeExcel.getStringKey("excel_report_monthly_flow_nsc"));
			spreadSheet.createCell(sheetGraficoFluxo, row, 68, 0, localeExcel.getStringKey("excel_report_monthly_flow_nsb"));
			spreadSheet.createCell(sheetGraficoFluxo, row, 76, 0, localeExcel.getStringKey("excel_report_monthly_flow_nsa"));
			
			// CHART FLOW DIR2

			@SuppressWarnings("rawtypes")
			Drawing drawing2 = sheetGraficoFluxo.createDrawingPatriarch();
			ClientAnchor anchor2 = drawing2.createAnchor(0, 0, 0, 0, 1, 49, 16, 79);

			Chart chartDir2 = drawing2.createChart(anchor2);
			ChartLegend legend2 = chartDir2.getOrCreateLegend();
			legend2.setPosition(LegendPosition.RIGHT);

			LineChartData dados2 = chartDir2.getChartDataFactory().createLineChartData();

			ChartAxis bottomAxis2 = chartDir2.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
			ValueAxis leftAxis2 = chartDir2.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
			leftAxis2.setCrosses(AxisCrosses.AUTO_ZERO);

			ChartDataSource<Number> xso = DataSources.fromNumericCellRange(sheetGraficoFluxo,
					new CellRangeAddress(gfIni, gfFim, 2, 2));
			ChartDataSource<Number> yso = DataSources.fromNumericCellRange(sheetGraficoFluxo,
					new CellRangeAddress(gfIni, gfFim, 10, 10));
			ChartDataSource<Number> ys1o = DataSources.fromNumericCellRange(sheetGraficoFluxo,
					new CellRangeAddress(gfIni, gfFim, 16, 16));
			ChartDataSource<Number> ys2o = DataSources.fromNumericCellRange(sheetGraficoFluxo,
					new CellRangeAddress(gfIni, gfFim, 17, 17));
			ChartDataSource<Number> ys3o = DataSources.fromNumericCellRange(sheetGraficoFluxo,
					new CellRangeAddress(gfIni, gfFim, 18, 18));
			ChartDataSource<Number> ys4o = DataSources.fromNumericCellRange(sheetGraficoFluxo,
					new CellRangeAddress(gfIni, gfFim, 19, 19));
			ChartDataSource<Number> ys5o = DataSources.fromNumericCellRange(sheetGraficoFluxo,
					new CellRangeAddress(gfIni, gfFim, 20, 20));

			LineChartSeries seriesDir2 = dados2.addSeries(xso, yso); 
			seriesDir2.setTitle("DIR02");			
			LineChartSeries seriesNSA02 = dados2.addSeries(xso, ys1o);
			seriesNSA02.setTitle(localeExcel.getStringKey("excel_report_monthly_flow_nsa"));
			LineChartSeries seriesNSB03 = dados2.addSeries(xso, ys2o);
			seriesNSB03.setTitle(localeExcel.getStringKey("excel_report_monthly_flow_nsb")); 
			LineChartSeries seriesNSC04 = dados2.addSeries(xso, ys3o); 
			seriesNSC04.setTitle(localeExcel.getStringKey("excel_report_monthly_flow_nsc")); 
			LineChartSeries seriesNSD05 = dados2.addSeries(xso, ys4o);
			seriesNSD05.setTitle(localeExcel.getStringKey("excel_report_monthly_flow_nsd"));
			LineChartSeries seriesNSE06 = dados2.addSeries(xso, ys5o);
			seriesNSE06.setTitle(localeExcel.getStringKey("excel_report_monthly_flow_nse"));

			chartDir2.plot(dados2, bottomAxis2, leftAxis2);

			spreadSheet.createRow(sheetGraficoFluxo, row, 80);
								
		}
		
		// CHART DENSITY
		
		for (int g = 1; g <= 6; g++) {
			
	sheetGraficoDensidade = workbook.createSheet(localeExcel.getStringKey("excel_single_sat_chart_title_density")+ g + "");
			
			spreadSheet.createRows(sheetGraficoDensidade, row, 0, 33);
			
			spreadSheet.createCell(sheetGraficoDensidade, row, 0, 1, localeExcel.getStringKey("excel_report_monthly_flow_company"));
			
			spreadSheet.createCell(sheetGraficoDensidade, row, 1, 1, localeExcel.getStringKey("excel_report_monthly_flow_lot"));
			spreadSheet.createCell(sheetGraficoDensidade, row, 2, 1, localeExcel.getStringKey("excel_report_monthly_flow_chart_rate_of_flow_lane"));
			spreadSheet.createCell(sheetGraficoDensidade, row, 3, 1, localeExcel.getStringKey("excel_report_monthly_flow_chart_strech_uniform")
			+"km"+localeExcel.getStringKey("excel_report_monthly_flow_chart_strech_uniform") +"km"+ localeExcel.getStringKey("excel_report_monthly_flow_chart_track")+"DIR1");
			spreadSheet.createCell(sheetGraficoDensidade, row, 10, 0, localeExcel.getStringKey("excel_report_monthly_flow_nse"));
			spreadSheet.createCell(sheetGraficoDensidade, row, 14, 0, localeExcel.getStringKey("excel_report_monthly_flow_nsd"));
			spreadSheet.createCell(sheetGraficoDensidade, row, 19, 0, localeExcel.getStringKey("excel_report_monthly_flow_nsc"));
			spreadSheet.createCell(sheetGraficoDensidade, row, 25, 0, localeExcel.getStringKey("excel_report_monthly_flow_nsb"));
			spreadSheet.createCell(sheetGraficoDensidade, row, 33, 0, localeExcel.getStringKey("excel_report_monthly_flow_nsa"));
			
			// CHART DENSITY DIR1

			@SuppressWarnings("rawtypes")
			Drawing drawing1 = sheetGraficoDensidade.createDrawingPatriarch();
			ClientAnchor anchor1 = null; 
			anchor1 = drawing1.createAnchor(0, 0, 0, 0, 1, 6, 16, 35);

			Chart chart1 = drawing1.createChart(anchor1);  
			ChartLegend legend1 = chart1.getOrCreateLegend(); 
			legend1.setPosition(LegendPosition.RIGHT);

			LineChartData dados = chart1.getChartDataFactory().createLineChartData();
 
			ChartAxis bottomAxis1 = chart1.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
			ValueAxis leftAxis1 = chart1.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
			leftAxis1.setCrosses(AxisCrosses.AUTO_ZERO); 

			// Testar outros dias
			if (g == 6 && daysInMonth == 28)
				gfFim -= 48;
			if (g == 6 && daysInMonth == 29)
				gfFim -= 24;
			if (g == 6 && daysInMonth == 30)
				gfFim += 0;
			if (g == 6 && daysInMonth == 31)
				gfFim += 24;

			ChartDataSource<Number> xs = DataSources.fromNumericCellRange(sheetGraficoDensidade,
					new CellRangeAddress(gfIni, gfFim, 2, 2));
			ChartDataSource<Number> ys = DataSources.fromNumericCellRange(sheetGraficoDensidade,
					new CellRangeAddress(gfIni, gfFim, 6, 6));
			ChartDataSource<Number> ys1 = DataSources.fromNumericCellRange(sheetGraficoDensidade,
					new CellRangeAddress(gfIni, gfFim, 11, 11));
			ChartDataSource<Number> ys2 = DataSources.fromNumericCellRange(sheetGraficoDensidade,
					new CellRangeAddress(gfIni, gfFim, 12, 12));
			ChartDataSource<Number> ys3 = DataSources.fromNumericCellRange(sheetGraficoDensidade,
					new CellRangeAddress(gfIni, gfFim, 13, 13));
			ChartDataSource<Number> ys4 = DataSources.fromNumericCellRange(sheetGraficoDensidade,
					new CellRangeAddress(gfIni, gfFim, 14, 14));
			ChartDataSource<Number> ys5 = DataSources.fromNumericCellRange(sheetGraficoDensidade,
					new CellRangeAddress(gfIni, gfFim, 15, 15));

			LineChartSeries seriesDir1 = dados.addSeries(xs, ys); 
			seriesDir1.setTitle("DIR01");
			LineChartSeries seriesNSA01 = dados.addSeries(xs, ys1); 
			seriesNSA01.setTitle(localeExcel.getStringKey("excel_report_monthly_flow_nsa"));
			LineChartSeries seriesNSB02 = dados.addSeries(xs, ys2);
			seriesNSB02.setTitle(localeExcel.getStringKey("excel_report_monthly_flow_nsb")); 
			LineChartSeries seriesNSC03 = dados.addSeries(xs, ys3); 
			seriesNSC03.setTitle(localeExcel.getStringKey("excel_report_monthly_flow_nsc"));
			LineChartSeries seriesNSD04 = dados.addSeries(xs, ys4); 
			seriesNSD04.setTitle(localeExcel.getStringKey("excel_report_monthly_flow_nsd")); 
			LineChartSeries seriesNSE05 = dados.addSeries(xs, ys5);
			seriesNSE05.setTitle(localeExcel.getStringKey("excel_report_monthly_flow_nse"));

			chart1.plot(dados, bottomAxis1, leftAxis1);
			
			spreadSheet.createRow(sheetGraficoDensidade, row, 37);			
			spreadSheet.createRows(sheetGraficoDensidade, row, 43, 76);

			spreadSheet.createCell(sheetGraficoDensidade, row, 43, 1, localeExcel.getStringKey("excel_report_monthly_flow_company"));
			
			spreadSheet.createCell(sheetGraficoDensidade, row, 44, 1, localeExcel.getStringKey("excel_report_monthly_flow_lot"));
			spreadSheet.createCell(sheetGraficoDensidade, row, 45, 1, localeExcel.getStringKey("excel_report_monthly_flow_chart_rate_of_flow_lane"));
			spreadSheet.createCell(sheetGraficoDensidade, row, 46, 1, localeExcel.getStringKey("excel_report_monthly_flow_chart_strech_uniform")
			+"km"+localeExcel.getStringKey("excel_report_monthly_flow_chart_strech_uniform") +"km"+ localeExcel.getStringKey("excel_report_monthly_flow_chart_track")+"DIR1");
			spreadSheet.createCell(sheetGraficoDensidade, row, 53, 0, localeExcel.getStringKey("excel_report_monthly_flow_nse"));
			spreadSheet.createCell(sheetGraficoDensidade, row, 57, 0, localeExcel.getStringKey("excel_report_monthly_flow_nsd"));
			spreadSheet.createCell(sheetGraficoDensidade, row, 62, 0, localeExcel.getStringKey("excel_report_monthly_flow_nsc"));
			spreadSheet.createCell(sheetGraficoDensidade, row, 68, 0, localeExcel.getStringKey("excel_report_monthly_flow_nsb"));
			spreadSheet.createCell(sheetGraficoDensidade, row, 76, 0, localeExcel.getStringKey("excel_report_monthly_flow_nsa"));
			
			// CHART DENSITY DIR2

			@SuppressWarnings("rawtypes")
			Drawing drawing2 = sheetGraficoDensidade.createDrawingPatriarch();
			ClientAnchor anchor2 = drawing2.createAnchor(0, 0, 0, 0, 1, 49, 16, 79);

			Chart chartDir2 = drawing2.createChart(anchor2);
			ChartLegend legend2 = chartDir2.getOrCreateLegend();
			legend2.setPosition(LegendPosition.RIGHT);

			LineChartData dados2 = chartDir2.getChartDataFactory().createLineChartData();

			ChartAxis bottomAxis2 = chartDir2.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
			ValueAxis leftAxis2 = chartDir2.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
			leftAxis2.setCrosses(AxisCrosses.AUTO_ZERO);

			ChartDataSource<Number> xso = DataSources.fromNumericCellRange(sheetGraficoDensidade,
					new CellRangeAddress(gfIni, gfFim, 2, 2));
			ChartDataSource<Number> yso = DataSources.fromNumericCellRange(sheetGraficoDensidade,
					new CellRangeAddress(gfIni, gfFim, 10, 10));
			ChartDataSource<Number> ys1o = DataSources.fromNumericCellRange(sheetGraficoDensidade,
					new CellRangeAddress(gfIni, gfFim, 16, 16));
			ChartDataSource<Number> ys2o = DataSources.fromNumericCellRange(sheetGraficoDensidade,
					new CellRangeAddress(gfIni, gfFim, 17, 17));
			ChartDataSource<Number> ys3o = DataSources.fromNumericCellRange(sheetGraficoDensidade,
					new CellRangeAddress(gfIni, gfFim, 18, 18));
			ChartDataSource<Number> ys4o = DataSources.fromNumericCellRange(sheetGraficoDensidade,
					new CellRangeAddress(gfIni, gfFim, 19, 19));
			ChartDataSource<Number> ys5o = DataSources.fromNumericCellRange(sheetGraficoDensidade,
					new CellRangeAddress(gfIni, gfFim, 20, 20));

			LineChartSeries seriesDir2 = dados2.addSeries(xso, yso); 
			seriesDir2.setTitle("DIR02");			
			LineChartSeries seriesNSA02 = dados2.addSeries(xso, ys1o);
			seriesNSA02.setTitle(localeExcel.getStringKey("excel_report_monthly_flow_nsa"));
			LineChartSeries seriesNSB03 = dados2.addSeries(xso, ys2o);
			seriesNSB03.setTitle(localeExcel.getStringKey("excel_report_monthly_flow_nsb")); 
			LineChartSeries seriesNSC04 = dados2.addSeries(xso, ys3o); 
			seriesNSC04.setTitle(localeExcel.getStringKey("excel_report_monthly_flow_nsc")); 
			LineChartSeries seriesNSD05 = dados2.addSeries(xso, ys4o);
			seriesNSD05.setTitle(localeExcel.getStringKey("excel_report_monthly_flow_nsd"));
			LineChartSeries seriesNSE06 = dados2.addSeries(xso, ys5o);
			seriesNSE06.setTitle(localeExcel.getStringKey("excel_report_monthly_flow_nse"));

			chartDir2.plot(dados2, bottomAxis2, leftAxis2);

			spreadSheet.createRow(sheetGraficoDensidade, row, 80);
						
		}		
		
		//CHART DENSITY		
		
 	   // SHEET CHARTS			
 		
	}	
	
	public String sheetNameSingle(String type, String module) {
		
		String sheetName = "";
		
		if(module.equals("sat")) {
		
		if(type.equals("1"))
		  sheetName = localeExcel.getStringKey("excel_single_sat_vehicles_count_sheet_name");
		
		if(type.equals("2"))
			  sheetName = localeExcel.getStringKey("excel_single_sat_vehicles_count_flow_sheet_name");
		
		if(type.equals("5"))
			  sheetName = localeExcel.getStringKey("excel_single_sat_weighing_sheet_name");
				
		if(type.equals("6"))
			  sheetName = localeExcel.getStringKey("excel_single_sat_class_sheet_name");
		
		if(type.equals("7"))
			  sheetName = localeExcel.getStringKey("excel_single_sat_axle_sheet_name");
		
		if(type.equals("8"))
			  sheetName = localeExcel.getStringKey("excel_single_sat_speed_sheet_name");
		
		if(type.equals("9"))
			  sheetName = localeExcel.getStringKey("excel_single_sat_ccr_classes_sheet_name");
		
		if(type.equals("10"))
			  sheetName = localeExcel.getStringKey("excel_single_sat_ccr_type_sheet_name");
				
		if(type.equals("11"))
			  sheetName = localeExcel.getStringKey("excel_single_sat_ccr_speed_sheet_name");
					
		if(type.equals("12"))
			  sheetName = localeExcel.getStringKey("excel_single_sat_ccr_all_classes_sheet_name");
		
				}
		
		if(module.equals("mto")) {
			
			if(type.equals("1"))
				  sheetName = localeExcel.getStringKey("excel_single_mto_year_sheet_name");
			
			if(type.equals("2"))
				  sheetName = localeExcel.getStringKey("excel_single_mto_month_sheet_name");
			
			if(type.equals("3"))
				  sheetName = localeExcel.getStringKey("excel_single_mto_period_sheet_name");
			
		}
		
		return sheetName;
	}
	
		

}
