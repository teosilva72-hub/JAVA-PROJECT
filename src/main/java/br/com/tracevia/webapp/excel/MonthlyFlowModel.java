package br.com.tracevia.webapp.excel;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.util.ExcelUtil;
import br.com.tracevia.webapp.util.LocaleUtil;

public class MonthlyFlowModel {
	
	private static String FONT_ARIAL = "Arial";
	
	private XSSFWorkbook workbook;
	private static XSSFSheet sheet;
	
	ExcelUtil utilSheet;
	
	LocaleUtil localeSheet;
	
	CellStyle baseStyle, standardStyle, dateTimeStyle, bgColorGreyStyle, bgColorWhiteStyle;  
	
	CellStyle daysBaseStyle, daysStyleData, daysHeaderStyle1, daysHeaderStyle2, baseStyleDaysHour, boldDataCellStyle;   
	 
	Font baseFont, standardFont, bgWhiteFont, boldFont;
		
	String equivNumber = "3,00", fph_value = "0,92" , fssi_value = "100", fphn_value = "1,84", flw_value = "1" ,
			width_value = "3,50", flc_value = "1", obst_value = "2,40", fm_value = "0",
			categoriaA = "A", categoriaB = "B", categoriaC = "C", categoriaD = "D", categoriaE = "E", categoriaF = "F",
			var_value = "> 25,0", fa_value = "0";
	
	public MonthlyFlowModel() {
		
		localeSheet = new LocaleUtil();		
		localeSheet.getResourceBundle(LocaleUtil.LABELS_EXCELSHEET);
		
		// ----------------------------------------------------------------------------------------------------------------

		// DEFAULT FONTS
		
		standardFont = utilSheet.createFont(workbook, FONT_ARIAL, 10, false, false, IndexedColors.BLACK); // STANDARD FONT				
	    baseFont = utilSheet.createFont(workbook,  FONT_ARIAL, 10, true, false, IndexedColors.DARK_BLUE); // SPECIAL FONT
	    bgWhiteFont = utilSheet.createFont(workbook, FONT_ARIAL, 10, true, false, IndexedColors.WHITE); // SPECIAL FONT 
	    boldFont = utilSheet.createFont(workbook,  FONT_ARIAL, 10, true, false, IndexedColors.BLACK); // FONTE EM NEGRITO
	    
		// ----------------------------------------------------------------------------------------------------------------

		// BASE STYLES	
			    
	    standardStyle = utilSheet.createCellStyle(workbook, standardFont, HorizontalAlignment.CENTER, IndexedColors.WHITE, FillPatternType.NO_FILL, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
		baseStyle =  utilSheet.createCellStyle(workbook, baseFont, HorizontalAlignment.CENTER, IndexedColors.WHITE, FillPatternType.NO_FILL, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	    dateTimeStyle = utilSheet.createCellStyle(workbook, baseFont, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false, IndexedColors.WHITE, FillPatternType.NO_FILL, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);   
	    bgColorGreyStyle = utilSheet.createCellStyle(workbook, standardFont, HorizontalAlignment.CENTER, IndexedColors.GREY_25_PERCENT, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	    bgColorWhiteStyle = utilSheet.createCellStyle(workbook, bgWhiteFont, HorizontalAlignment.CENTER, IndexedColors.DARK_BLUE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	  	 		
	    // ----------------------------------------------------------------------------------------------------------------
	    
	    // DAYS STYLES
		    
	    daysBaseStyle =  utilSheet.createCellStyle(workbook, boldFont, HorizontalAlignment.LEFT, IndexedColors.WHITE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	    daysStyleData =  utilSheet.createCellStyle(workbook, boldFont, HorizontalAlignment.CENTER, IndexedColors.WHITE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	    daysHeaderStyle1 = utilSheet.createCellStyle(workbook, standardFont, HorizontalAlignment.CENTER, IndexedColors.WHITE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	    daysHeaderStyle2 =  utilSheet.createCellStyle(workbook, baseFont, HorizontalAlignment.CENTER, IndexedColors.WHITE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
		baseStyleDaysHour = utilSheet.createCellStyle(workbook, baseFont, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, false, IndexedColors.WHITE, FillPatternType.NO_FILL, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);   
		boldDataCellStyle = utilSheet.createCellStyle(workbook, baseFont, HorizontalAlignment.CENTER, IndexedColors.WHITE, FillPatternType.SOLID_FOREGROUND, ExcelUtil.ALL_BORDERS, BorderStyle.THIN);
	  	    	    
	    // ----------------------------------------------------------------------------------------------------------------
	}

			    	 	


}
