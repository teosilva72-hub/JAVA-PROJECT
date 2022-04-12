package br.com.tracevia.webapp.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.com.tracevia.webapp.model.sat.laneFilter;

/**
 * Classe que possui métodos para construir planilhas EXCEL
 * @author Wellington 13/10/2021
 * @version 1.0
 * @since 1.0
 * 
 */
public class ExcelUtil {	

	private static String FONT_ARIAL = "Arial";
	
	private static String NUMBER_REGEX = "\\d+";
	private static String DOUBLE_REGEX = "\\d*\\.\\d+$";
	private static String NUMBER_DECIMAL = "^[0-9]\\d*(\\.\\d+)?$";
	private static String DATETIME_REGEX = "^(2[0-3]|[01]?[0-9]):([0-5]?[0-9]):([0-5]?[0-9])$";
	
	public static String ALL_BORDERS = "ALL";
	public static String TOP_BORDER = "TOP";
	public static String BOTTOM_BORDER = "BOTTOM";
	public static String LEFT_BORDER = "LEFT";
	public static String RIGHT_BORDER = "RIGHT";

	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// CREATE ROWS
	// ----------------------------------------------------------------------------------------------------------------			

	/**
	 * Método para criar multiplas linhas em uma planilha Excel
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha
	 * @param initRow - indice inicial
	 * @param endRow -  indice final
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFSheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFRow.html
	 */
	public void createRows(XSSFSheet sheet, XSSFRow row, int initRow, int endRow) {

		for (int rowIndex = initRow; rowIndex <= endRow;  rowIndex++) { 				
			row = sheet.createRow(rowIndex);

		}
	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * Método para criar multiplas linhas em uma planilha Excel
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha
	 * @param initRow - indice inicial
	 * @param endRow -  indice final
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Sheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Row.html
	 */
	public void createRows(Sheet sheet, Row row, int initRow, int endRow) {

		for (int rowIndex = initRow; rowIndex <= endRow;  rowIndex++) { 				
			row = sheet.createRow((short) rowIndex);

		}
	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * Método para definir altura de uma linha em pontos
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha
	 * @param rowNumber - objeto de representação de alto nível de estilos em uma planilha 
	 * @param points - altura da linha medida em tamanho de pontos
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Sheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Row.html 
	 */
	public void setPointsheight(Sheet sheet, Row row, int rowNumber, int points) {	
		row = sheet.getRow(rowNumber);	
		row.setHeightInPoints((points * sheet.getDefaultRowHeightInPoints()));
	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * Método para definir altura de uma linha
	 * @author Wellington 15/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha	
	 * @param height - altura da linha 
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Sheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Row.html 
	 */
	public void setHeight(Sheet sheet, int row, int height) {	
		sheet.getRow(row).setHeight((short) height); 	  	  
	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * Método para criar uma única linha em uma planilha Excel
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha
	 * @param index - indice da linha a ser criado      
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Sheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Row.html
	 */
	public void createRow(Sheet sheet, Row row, int index) {		   
		row = sheet.createRow((short) index);			  	   
	} 

	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// CREATE CELLS
	// ----------------------------------------------------------------------------------------------------------------			

	/**
	 * Método para criar multiplas células em uma planilha Excel
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha
	 * @param col1 - indice da coluna inicial
	 * @param col2 - indice da coluna final
	 * @param lin1 - indice da linha inicial
	 * @param lin2 - indice da coluna final  
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Sheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Row.html 
	 */
	public void createCells(Sheet sheet, Row row, int col1, int col2, int lin1, int lin2) {		

		for (int lin = lin1; lin <= lin2; lin++) { 
			for(int c=col1; c <= col2; c++) {		

				row = sheet.getRow(lin);		
				row.createCell(c);
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * Método para criar uma única célula em uma planilha Excel
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha
	 * @param rowNumber - número da linha
	 * @param cellNumber - número da célula    
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Sheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Row.html 
	 */
	public void createCell(Sheet sheet, Row row, int rowNumber, int cellNumber) {		

		row = sheet.getRow(rowNumber);		
		row.createCell(cellNumber);	

	}

	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// SET CELL VALUES
	// ----------------------------------------------------------------------------------------------------------------		
	
	/**
	 * Método para inserir valores no cabeçalho da tabela de dados
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha
	 * @param rowNumber - indice da linha	 
	 * @param columns - lista com as colunas a serem inseridas no arquivo
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Sheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Row.html 
	 */
	public void setHeaderCellsValue(Sheet sheet, Row row, int rowNumber, List<String> columns) {		

		 row = sheet.getRow(rowNumber);	
		
		for(int col = 0; col < columns.size(); col++)		     		
		     row.getCell(col).setCellValue(columns.get(col));	
		
	  }	

	// ----------------------------------------------------------------------------------------------------------------


	/**
	 * Método para inserir valor em uma única célula de uma planilha Excel
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha
	 * @param rowNumber - indice da linha
	 * @param cellNumber - indice da coluna 
	 * @param value - valor a ser inserido (String)
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Sheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Row.html 
	 */
	public void setCellValue(Sheet sheet, Row row, int rowNumber, int cellNumber, String value) {		
		
		row = sheet.getRow(rowNumber);			
		row.getCell(cellNumber)	
		.setCellValue(value);		

	}	

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * Método para inserir valor em uma única célula de uma planilha Excel
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha
	 * @param rowNumber - indice da linha
	 * @param cellNumber - indice da coluna 
	 * @param value - valor a ser inserido (Integer)
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Sheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Row.html 
	 */
	public void setCellValue(Sheet sheet, Row row, int rowNumber, int cellNumber, int value) {		

		row = sheet.getRow(rowNumber);			
		row.getCell(cellNumber)
		.setCellValue(value);		

	}	

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * Método para inserir valor em uma única célula de uma planilha Excel
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha
	 * @param rowNumber - indice da linha
	 * @param cellNumber - indice da coluna 
	 * @param value - valor a ser inserido (Double)
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Sheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Row.html 
	 */
	public void setCellValue(Sheet sheet, Row row, int rowNumber, int cellNumber, double value) {		

		row = sheet.getRow(rowNumber);			
		row.getCell(cellNumber)
		.setCellValue(value);		

	}	

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * Método para inserir valor em uma única célula de uma planilha Excel
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha
	 * @param rowNumber - indice da linha
	 * @param cellNumber - indice da coluna 
	 * @param value - valor a ser inserido (boolean)
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Sheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Row.html 
	 */
	public void setCellValue(Sheet sheet, Row row, int rowNumber, int cellNumber, boolean value) {		

		row = sheet.getRow(rowNumber);			
		row.getCell(cellNumber)
		.setCellValue(value);		

	}	

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * Método para inserir valor em uma única célula de uma planilha Excel
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha
	 * @param rowNumber - indice da linha
	 * @param cellNumber - indice da coluna 
	 * @param value - valor a ser inserido (Date)
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Sheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Row.html 
	 */
	public void setCellValue(Sheet sheet, Row row, int rowNumber, int cellNumber, Date value) {		

		row = sheet.getRow(rowNumber);			
		row.getCell(cellNumber)
		.setCellValue(value);		

	}	

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * Método para inserir multiplos valores nas células de uma linha
	 * @author Wellington 15/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha
	 * @param rowNumber - indice da linha 
	 * @param values - colunas do cabeçalho 
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Sheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Row.html 
	 */
	public void setCellsValues(Sheet sheet, Row row, int rowNumber, String[] values) {	 

		for(int i = 0; i < values.length; i++) {

			row = sheet.getRow(rowNumber);		
			row.getCell(i)
			.setCellValue(values[i]);		

		}
	}

	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// SET CELL FORMULA
	// ----------------------------------------------------------------------------------------------------------------			

	/**
	 * Método para inserir uma fórmula em uma única célula de uma planilha Excel
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha
	 * @param rowNumber - indice da linha
	 * @param cellNumber - indice da coluna 
	 * @param formula - fórmula a ser inserida
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Sheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Row.html 
	 */
	public void setFormula(Sheet sheet, Row row, int rowNumber, int cellNumber, String formula) {	
		
	//	System.out.println(cellNumber+" "+formula);

		row = sheet.getRow(rowNumber);		
		row.getCell(cellNumber)	
		.setCellFormula(formula);	
	}

	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// MERGE CELL VALUES
	// ----------------------------------------------------------------------------------------------------------------				

	/**
	 * Método para mesclar células de uma planilha Excel
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param sheet - objeto de representação de alto nível de uma planilha    
	 * @param rowNumber - indice da linha
	 * @param cellRange - indices para realizar a operação 
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Sheet.html
	 */
	public void mergeCells(Sheet sheet, String cellRange) {	
		sheet.addMergedRegion(CellRangeAddress.valueOf(cellRange));		
	}

	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// COLUMN WIDTH
	// ----------------------------------------------------------------------------------------------------------------				

	/**
	 * Método para aplicar a largura de uma célula em uma planilha Excel (Single)
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param sheet - objeto de representação de alto nível de uma planilha  
	 * @param colNumber - indice da coluna
	 * @param width - largura da coluna     
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Sheet.html  
	 */
	public void columnWidth(Sheet sheet, int colNumber, int width) {		
		sheet.setColumnWidth(colNumber, width);		     
	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * Método para aplicar uma largura em várias células de uma planilha Excel
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param sheet - objeto de representação de alto nível de uma planilha  
	 * @param initCol - indice da coluna inicial
	 * @param endCol - indice da coluna final
	 * @param width - largura a ser atribuida nas colunas     
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Sheet.html  
	 */
	public void columnsWidth(Sheet sheet, int initCol, int endCol, int width) {		

		for(int c = initCol; c <= endCol; c++)
			sheet.setColumnWidth(c, width);		     
	}
	
	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * Método para aplicar uma largura em várias células de uma planilha Excel
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param sheet - objeto de representação de alto nível de uma planilha  
	 * @param length - tamanho das colunas	
	 * @param width - largura a ser atribuida nas colunas     
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Sheet.html  
	 */
	public void columnsWidthAuto(Sheet sheet, int length) {		

		for(int c = 0; c < length; c++)
			 sheet.autoSizeColumn(c);	     
	}

	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// CREATE FONT 
	// ----------------------------------------------------------------------------------------------------------------				

	/**
	 * Método para criar uma fonte
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param workbook - objeto de representação de alto nível de uma pasta de trabalho para uma planilha
	 * @param fontName - nome da fonte
	 * @param fontHeight - tamanho da fonte    
	 * @param bold - definir fonte em negrito
	 * @param italic - definir fonte em itálico  
	 * @param fontColor - objeto de indexação de cores que ainda é necessário para alguns registros (obsoleto)     
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFWorkbook.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Font.html
	 * @return uma fonte pré configurada
	 */
	public Font createFont(Workbook workbook, String fontName, int fontHeight, boolean bold, boolean italic, IndexedColors color) {

		Font font = workbook.createFont();			
		font.setFontName(fontName); 
		font.setFontHeightInPoints((short) fontHeight);	
		font.setBold(bold);	
		font.setItalic(italic);
		font.setColor(color.getIndex());

		return font;
	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * Método para aplicar cores em fontes 
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0   
	 * @param font - objeto do tipo Font
	 * @param color - objeto de indexação de cores que ainda é necessário para alguns registros (obsoleto)     
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Font.html
	 * @see https://poi.apache.org/apidocs/4.0/org/apache/poi/ss/usermodel/IndexedColors.html
	 */
	public void setFontColor(Font font, IndexedColors color) {
		font.setColor(color.getIndex());
	}

	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// CREATE CELL STYLE
	// ----------------------------------------------------------------------------------------------------------------		

	/**
	 * Método para criar um estilo para uma planilha do Excel
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param workbook - objeto de representação de alto nível de uma pasta de trabalho do Excel
	 * @param style - objeto de representação de alto nível de estilos em uma planilha 
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Workbook.html
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html
	 * @return estilo para aplicar em células
	 */
	public CellStyle createCellStyle(Workbook workbook) {

		CellStyle style = workbook.createCellStyle();

		return style;

	}	

	// ---------------------------------------------------------------------------------------------------------------	

	/**
	 * Método para aplicar estilos de forma multipla em células de uma planilha Excel
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha
	 * @param style - objeto de representação de alto nível de estilos em uma planilha
	 * @param col1 - indice da coluna inicial
	 * @param col2 - indice da coluna final
	 * @param lin1 - indice da linha inicial
	 * @param lin2 - indice da coluna final  
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Sheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Row.html 
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html
	 */
	public void setCellsStyle(Sheet sheet, Row row, CellStyle style, int col1, int col2, int lin1, int lin2) {		

		for (int lin = lin1; lin <= lin2; lin++) {  
			for(int c=col1; c <= col2; c++) {
				row = sheet.getRow(lin);		 
				row.getCell(c).setCellStyle(style);		 	
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * Método para aplicar estilo em uma única célula de uma planilha Excel
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha
	 * @param style - objeto de representação de alto nível de estilos em uma planilha
	 * @param rowNumber - indice da linha
	 * @param cellNumber - indice da coluna
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Sheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Row.html 
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html
	 */
	public void setCellStyle(Sheet sheet, Row row, CellStyle style, int rowNumber, int cellNumber) {		

		row = sheet.getRow(rowNumber);		 
		row.getCell(cellNumber).setCellStyle(style);			

	}

	// ----------------------------------------------------------------------------------------------------------------


	/**
	 * Método para aplicar alinhamento horizontal
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param style - objeto de representação de alto nível de estilos em uma planilha 
	 * @param position - valor de enumeração que indica o alinhamento horizontal de uma célula
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/HorizontalAlignment.html
	 */
	public void setStyleHorizontalAlignment(CellStyle style, HorizontalAlignment position) {				
		style.setAlignment(position);			
	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * Método para aplicar alinhamento vertical
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param style - objeto de representação de alto nível de estilos em uma planilha  
	 * @param position - valor de enumeração que indica o alinhamento vertical de uma célula  
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/VerticalAlignment.html
	 */
	public void setStyleVerticalAlignment(CellStyle style, VerticalAlignment position) 
	{						
		style.setVerticalAlignment(position); 			
	}	

	// ----------------------------------------------------------------------------------------------------------------	

	/**
	 * Método para definir uma quebra de linha
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param style - objeto de representação de alto nível de estilos em uma planilha 
	 * @param wrap - define o uso de uma quebra de linha
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html
	 */
	public void setWrapText(CellStyle style, boolean wrap) {				
		style.setWrapText(wrap);		
	}

	// ----------------------------------------------------------------------------------------------------------------	

	/**
	 * Método para definir o estilo da cor para células
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param style - objeto de representação de alto nível de estilos em uma planilha 
	 * @param color - objeto de indexação de cores que ainda é necessário para alguns registros (obsoleto) 
	 * @param type - O valor de enumeração que indica o estilo do padrão de preenchimento
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html
	 * @see https://poi.apache.org/apidocs/4.0/org/apache/poi/ss/usermodel/IndexedColors.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/FillPatternType.html
	 */
	public void setCellBackgroundColor(CellStyle style, IndexedColors color, FillPatternType type) {				

		style.setFillForegroundColor(color.getIndex());
		style.setFillPattern(type);

	}

	// ----------------------------------------------------------------------------------------------------------------	

	/**
	 * Método para definir uma fonte a ser usada
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param style - objeto de representação de alto nível de estilos em uma planilha 
	 * @param font - fonte a ser aplicada
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Font.html
	 */
	public void setFont(CellStyle style, Font font) {				
		style.setFont(font);	
	}

	// ----------------------------------------------------------------------------------------------------------------	

	/**
	 * Método para definir a rotação para um fonte
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param style - objeto de representação de alto nível de estilos em uma planilha 
	 * @param rotation - grau de rotação
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html  
	 */

	public void addRotationStyle(CellStyle style, int rotation) {
		style.setRotation((short) rotation);	
	}

	// ----------------------------------------------------------------------------------------------------------------	

	/**
	 * Método para aplicar todas as bordas
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param style - objeto de representação de alto nível de estilos em uma planilha
	 * @param type - valor de enumeração que indica o estilo de linha de uma borda
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/BorderStyle.html
	 */
	public void setBorders(CellStyle style, BorderStyle type) {

		style.setBorderTop(type);
		style.setBorderRight(type);
		style.setBorderLeft(type);
		style.setBorderBottom(type);
	}

	// ----------------------------------------------------------------------------------------------------------------	

	/**
	 * Método para aplicar borda ao topo
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param style - objeto de representação de alto nível de estilos em uma planilha 
	 * @param type - valor de enumeração que indica o estilo de linha de uma borda
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html  
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/BorderStyle.html
	 */
	public void setBorderTop(CellStyle style, BorderStyle type) {		
		style.setBorderTop(type);		
	}

	// ----------------------------------------------------------------------------------------------------------------	

	/**
	 * Método para aplicar borda na direita
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param style - objeto de representação de alto nível de estilos em uma planilha 
	 * @param type - valor de enumeração que indica o estilo de linha de uma borda
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html  
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/BorderStyle.html
	 */
	public void setBorderRight(CellStyle style, BorderStyle type) {		
		style.setBorderRight(type);	
	}

	// ----------------------------------------------------------------------------------------------------------------	

	/**
	 * Método para aplicar borda na esquerda
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param style - objeto de representação de alto nível de estilos em uma planilha 
	 * @param type - valor de enumeração que indica o estilo de linha de uma borda
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html  
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/BorderStyle.html
	 */
	public void setBorderLeft(CellStyle style, BorderStyle type) {		
		style.setBorderLeft(type);		
	}

	// ----------------------------------------------------------------------------------------------------------------	

	/**
	 * Método para aplicar borda ao fundo
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param style - objeto de representação de alto nível de estilos em uma planilha 
	 * @param type - valor de enumeração que indica o estilo de linha de uma borda
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html  
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/BorderStyle.html
	 */
	public void setBorderBottom(CellStyle style, BorderStyle type) {
		style.setBorderBottom(type);
	}

	// ----------------------------------------------------------------------------------------------------------------	

	/**
	 * Método para definir um estilo em apenas uma célula
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha
	 * @param style - objeto de representação de alto nível de estilos em uma planilha 
	 * @param rowNumber - número da linha
	 * @param cellNumber - número da célula
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Sheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Row.html 
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html  
	 */
	public void setStyle(Sheet sheet, Row row, CellStyle style, int rowNumber, int cellNumber) {		

		row = sheet.getRow(rowNumber);		
		row.getCell(cellNumber).setCellStyle(style);

	}

	// ----------------------------------------------------------------------------------------------------------------	

	/**
	 * Método para definir um estilo para multiplas células
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha
	 * @param style - objeto de representação de alto nível de estilos em uma planilha 
	 * @param initRow - indice da linha inicial
	 * @param endRow - indice da linha final
	 * @param initCol - indice da coluna inicial
	 * @param endCol - indice da coluna final
	 * @param cellNumber - número da célula
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Sheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Row.html 
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html  
	 */
	public void setCellStyle(Sheet sheet, Row row, CellStyle style, int initRow, int endRow, int initCol, int endCol) {		

		for(int r = initRow; r <= endRow; r++) {
			for(int c = initCol; c <= endCol; c++)	{	

				row = sheet.getRow((short) r);		
				row.getCell(c).setCellStyle(style);

			}
		}
	}

	// ----------------------------------------------------------------------------------------------------------------		
	// ----------------------------------------------------------------------------------------------------------------
	// CREATE IMAGE
	// ----------------------------------------------------------------------------------------------------------------		

	/**
	 * Método para criar uma imagem na planilha do Excel
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0 
	 * @param workbook - objeto de representação de alto nível de uma pasta de trabalho do Excel
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param logo - representa o caminho de uma imagem
	 * @param col1 - primeira coluna
	 * @param col2 - segunda coluna
	 * @param row1 - primeira linha	
	 * @param row2 - terceira linha 
	 * @param dx1 - distância eixo X1 
	 * @param dy1 - distância eixo Y1 
	 * @param dx2 - distância eixo X2 
	 * @param dy2 - distância eixo Y2 
	 * @param resize - indice de redimensionamento da imagem (proporcional)
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Workbook.html
	 * @see https://poi.apache.org/apidocs/4.1/org/apache/poi/xssf/usermodel/XSSFDrawing.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFClientAnchor.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFPicture.html
	 */	
	public void createImage(Workbook workbook, Sheet sheet, String logo, int col1, int col2,  int row1, int row2, 
			int dx1, int dy1, int dx2, int dy2, int resize) {
		
		try {
			
			String path = ImageUtil.getInternalImagePath("images", "files", logo);
				
			InputStream my_banner_image = new FileInputStream(path);

			byte[] bytes = IOUtils.toByteArray(my_banner_image);	

			int my_picture_id = workbook.addPicture(bytes, XSSFWorkbook.PICTURE_TYPE_PNG);	

			my_banner_image.close();	

			XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();		
			XSSFClientAnchor my_anchor = new XSSFClientAnchor();

			my_anchor.setCol1(col1);
			my_anchor.setRow1(row1);
			my_anchor.setCol2(col2);
			my_anchor.setRow2(row2);

			my_anchor.setDx1(dx1);
			my_anchor.setDy1(dy1);
			my_anchor.setDx2(dx2);
			my_anchor.setDy2(dy2);
			
			my_anchor.setCol1(col1);
			my_anchor.setRow1(row1);
			my_anchor.setCol2(col2);
			my_anchor.setRow2(row2);

			my_anchor.setDx1(dx1);
			my_anchor.setDy1(dy1);
			my_anchor.setDx2(dx2);
			my_anchor.setDy2(dy2);

			XSSFPicture my_picture = drawing.createPicture(my_anchor, my_picture_id);

			my_picture.resize(resize);

		}catch(IOException ex) {

			ex.printStackTrace();

		}

	}

	// ----------------------------------------------------------------------------------------------------------------	
	// ----------------------------------------------------------------------------------------------------------------
	// DOWNLOAD FILE
	// ----------------------------------------------------------------------------------------------------------------
	
	public InputStream getOutput(Workbook workbook) throws IOException {
		InputStream input;
		ByteArrayOutputStream output = new ByteArrayOutputStream();  
		try {
			workbook.write(output);
			input = new ByteArrayInputStream(output.toByteArray());
		} finally {
			output.close();			
		}
		
		return input;
		
	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * Método para realizar o download de um arquivo Excel
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0 
	 * @param workbook - objeto de representação de alto nível de uma pasta de trabalho do Excel	
	 * @param fileName - nome do arquivo
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Workbook.html
	 */
	public void donwloadExcelFile(Workbook workbook, String fileName) throws IOException {

		SessionUtil.getExternalContext().setResponseContentType("application/vnd.ms-excel");
		SessionUtil.getExternalContext().setResponseHeader("Content-Disposition","attachment; filename=\""+fileName+".xlsx\"");

		OutputStream responseOutputStream = SessionUtil.getExternalContext().getResponseOutputStream();     

		workbook.write(responseOutputStream);
		SessionUtil.getFacesContext().responseComplete();     

	}
	
	public void donwloadPDFFile(ByteArrayOutputStream file, String fileName) throws IOException {
		
		SessionUtil.getExternalContext().setResponseContentType("application/pdf");
		SessionUtil.getExternalContext().setResponseHeader("Content-Disposition","attachment; filename=\""+fileName+".pdf\"");
		
		OutputStream responseOutputStream = SessionUtil.getExternalContext().getResponseOutputStream();     
		
		file.writeTo(responseOutputStream);
		SessionUtil.getFacesContext().responseComplete();     
		
	}

	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// INITIALIZE METHODS
	// ----------------------------------------------------------------------------------------------------------------		

	// STYLES

	/**		 
	 * Método para criar uma novo estilo com quebra de linha, bordas e alinhamento horizontal e vertical
	 * @author Wellington 14/10/2021
	 * @version 1.0
	 * @since 1.0 
	 * @param workbook - objeto de representação de alto nível de uma pasta de trabalho do Excel
	 * @param style - objeto de representação de alto nível de estilos em uma planilha 
	 * @param font - objeto do tipo Font    	
	 * @param horizontal - valor de enumeração que indica o alinhamento horizontal de uma célula
	 * @param vertical - valor de enumeração que indica o alinhamento vertical de uma célula
	 * @param wrapText - define o uso de uma quebra de linha  
	 * @param backgroundColor - objeto de indexação de cores que ainda é necessário para alguns registros (obsoleto)
	 * @param pattern - O valor de enumeração que indica o estilo do padrão de preenchimento
	 * @param borderTemplate - template a ser utilizado (ALL, TOP, BOTTOM, LEFT, RIGHT)
	 * @param borderStyle - valor de enumeração que indica o estilo de linha de uma borda
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/HorizontalAlignment.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/VerticalAlignment.html
	 * @see https://poi.apache.org/apidocs/4.0/org/apache/poi/ss/usermodel/IndexedColors.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/FillPatternType.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/BorderStyle.html
	 */
	public CellStyle createCellStyle(Workbook workbook, Font font, HorizontalAlignment horizontal, VerticalAlignment vertical,
			boolean wrapText, IndexedColors backgroundColor, FillPatternType pattern, String borderTemplate, BorderStyle borderStyle) {

		CellStyle style = createCellStyle(workbook);
		setStyleHorizontalAlignment(style, horizontal);
		setStyleVerticalAlignment(style, vertical);
		setWrapText(style, wrapText);
		setFont(style, font);
		setCellBackgroundColor(style, backgroundColor, pattern);
		borderTemplate(borderTemplate, style, borderStyle);
				
		return style;
	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * Método para criar uma novo estilo com bordas e alinhamento horizontal e vertical
	 * @author Wellington 14/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param workbook - objeto de representação de alto nível de uma pasta de trabalho do Excel
	 * @param style - objeto de representação de alto nível de estilos em uma planilha
	 * @param font - objeto do tipo Font
	 * @param horizontal - valor de enumeração que indica o alinhamento horizontal de uma célula
	 * @param vertical - valor de enumeração que indica o alinhamento vertical de uma célula
	 * @param backgroundColor - objeto de indexação de cores que ainda é necessário para alguns registros (obsoleto)
	 * @param pattern - O valor de enumeração que indica o estilo do padrão de preenchimento
	 * @param borderTemplate - template a ser utilizado (ALL, TOP, BOTTOM, LEFT, RIGHT)
	 * @param borderStyle - valor de enumeração que indica o estilo de linha de uma borda
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/HorizontalAlignment.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/VerticalAlignment.html
	 * @see https://poi.apache.org/apidocs/4.0/org/apache/poi/ss/usermodel/IndexedColors.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/FillPatternType.html 
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/BorderStyle.html
	 */
	public CellStyle createCellStyle(Workbook workbook, Font font, HorizontalAlignment horizontal, VerticalAlignment vertical, IndexedColors backgroundColor, FillPatternType pattern, String borderTemplate, BorderStyle borderStyle) {

		CellStyle style = createCellStyle(workbook);
		setStyleHorizontalAlignment(style, horizontal);    
		setStyleVerticalAlignment(style, vertical);    
		setFont(style, font);
		setCellBackgroundColor(style, backgroundColor, pattern);   
		borderTemplate(borderTemplate, style, borderStyle);
		
		return style;
	}

	// ----------------------------------------------------------------------------------------------------------------
	
	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * Método para criar uma novo estilo com bordas e alinhamento horizontal e vertical
	 * @author Wellington 14/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param workbook - objeto de representação de alto nível de uma pasta de trabalho do Excel
	 * @param style - objeto de representação de alto nível de estilos em uma planilha
	 * @param font - objeto do tipo Font
	 * @param horizontal - valor de enumeração que indica o alinhamento horizontal de uma célula
	 * @param vertical - valor de enumeração que indica o alinhamento vertical de uma célula
	 * @param backgroundColor - objeto de indexação de cores que ainda é necessário para alguns registros (obsoleto)
	 * @param pattern - O valor de enumeração que indica o estilo do padrão de preenchimento
	 * @param borderTemplate - template a ser utilizado (ALL, TOP, BOTTOM, LEFT, RIGHT)
	 * @param borderStyle - valor de enumeração que indica o estilo de linha de uma borda
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/HorizontalAlignment.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/VerticalAlignment.html
	 * @see https://poi.apache.org/apidocs/4.0/org/apache/poi/ss/usermodel/IndexedColors.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/FillPatternType.html 
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/BorderStyle.html
	 */
	public CellStyle createCellStyle(Workbook workbook, Font font, VerticalAlignment vertical, IndexedColors backgroundColor, FillPatternType pattern, String borderTemplate, BorderStyle borderStyle) {

		CellStyle style = createCellStyle(workbook);		   
		setStyleVerticalAlignment(style, vertical);    
		setFont(style, font);
		setCellBackgroundColor(style, backgroundColor, pattern);   
		borderTemplate(borderTemplate, style, borderStyle);
		
		return style;
	}

	/**		 
	 * Método para criar uma novo estilo com bordas e alinhamento horizontal
	 * @author Wellington 14/10/2021
	 * @version 1.0
	 * @since 1.0 
	 * @param workbook - objeto de representação de alto nível de uma pasta de trabalho do Excel
	 * @param style - objeto de representação de alto nível de estilos em uma planilha 
	 * @param font - objeto do tipo Font    	
	 * @param horizontal - valor de enumeração que indica o alinhamento horizontal de uma célula		
	 * @param backgroundColor - objeto de indexação de cores que ainda é necessário para alguns registros (obsoleto)
	 * @param pattern - O valor de enumeração que indica o estilo do padrão de preenchimento
	 * @param borderTemplate - template a ser utilizado (ALL, TOP, BOTTOM, LEFT, RIGHT)
	 * @param borderStyle - valor de enumeração que indica o estilo de linha de uma borda 	
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html  
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/HorizontalAlignment.html 
	 * @see https://poi.apache.org/apidocs/4.0/org/apache/poi/ss/usermodel/IndexedColors.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/FillPatternType.html  
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/BorderStyle.html
	 */
	public CellStyle createCellStyle(Workbook workbook, Font font, HorizontalAlignment horizontal, IndexedColors backgroundColor, FillPatternType pattern, String borderTemplate, BorderStyle borderStyle) {

		CellStyle style = createCellStyle(workbook);
		setStyleHorizontalAlignment(style, horizontal);
		setFont(style, font);
		setCellBackgroundColor(style, backgroundColor, pattern);
		borderTemplate(borderTemplate, style, borderStyle);
		
		return style;

	}

	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// TOTAL
	// ----------------------------------------------------------------------------------------------------------------		

	/**
	 * Método para criar colunas com total para SOMA
	 * @author Wellington 15/10/2021
	 * @version 1.0
	 * @since 1.0  
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha
	 * @param style - objeto de representação de alto nível de estilos em uma planilha 
	 * @param lines - lista com dados
	 * @param rowTotal - última linha a ser apresentado o total		
	 * @param columnsLength - número de colunas 
	 * @param rowIni - linha inicial
	 * @param rowEnd - linha final 		
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Sheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Row.html  
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html
	 * 
	 */
	public void totalExcelSum(XSSFWorkbook wb, Sheet sheet, Row row, CellStyle standard, List<String[]> values, String totalType, int columnsLength, int startColumn, int rowIni, int rowEnd) {
				
		String columnLetter = "";				
				
		int sum = rowIni - rowEnd;
		
			if(sum == 1)
				rowEnd -= 1;
		
	    rowIni += 1;	    
	    
	    setCellsStyle(sheet, row, standard, startColumn, columnsLength, rowEnd, rowEnd); // TOTAL STYLE	
	    
	    // ----------------------------------------------------------------------------------
	    
		// CREATE STYLE
		/*CellStyle cellStyle = createCellStyle(wb);
	    	    
	    if(totalType.equals("timeSum")) {
	    	
	    	// CREATE FONT	
			Font font = createFont(wb, FONT_ARIAL, 10, false, false, IndexedColors.BLACK);
	
			// SET STYLE
			cellStyle = createCellStyle(wb, font, HorizontalAlignment.CENTER, IndexedColors.WHITE, FillPatternType.SOLID_FOREGROUND, ALL_BORDERS, BorderStyle.THIN);
		
			// HELPER							
			CreationHelper createHelper = wb.getCreationHelper();

			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("hh:mm:ss"));
	    		    	
	    }*/
	    
	    // ----------------------------------------------------------------------------------
				    	    
		for(int col = startColumn; col <= columnsLength; col++) {
			
			columnLetter = CellReference.convertNumToColString(col); // COLUMN LETTER				
									
			//if(values.get(0)[col].matches(NUMBER_DECIMAL))
			    setFormula(sheet, row, rowEnd, col, "SUM("+columnLetter+""+ (rowIni) + ":"+columnLetter+"" + (rowEnd) + ")");	// DEFINE FORMULA										
            			
			/*else if(values.get(0)[col].matches(DATETIME_REGEX)) {
				
				columnLetter = CellReference.convertNumToColString(col);	// COLUMN LETTER	

				// FORMULA				
				String formula = "(";
				String aux= "";

				for(int i = rowIni; i <= rowEnd; i++) {
					aux += ""+columnLetter+""+i+"";

					if(i < rowEnd)
						aux += "+";
				}

				formula += aux + ")";
				
				setFormula(sheet, row, rowEnd, col, formula);	// DEFINE FORMULA	

				setCellStyle(sheet, row, cellStyle, rowEnd, rowEnd, col, col); // TOTAL STYLE		
				
			}*/		
		}	
	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * Método para criar colunas com total para MÉDIA
	 * @author Wellington 15/10/2021
	 * @version 1.0
	 * @since 1.0  
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha
	 * @param style - objeto de representação de alto nível de estilos em uma planilha
	 * @param lines - lista com dados 
	 * @param rowTotal - última linha a ser apresentado o total	
	 * @param columnsLength - número de colunas  			 
	 * @param rowIni - linha inicial
	 * @param rowEnd - linha final 			
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Sheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Row.html  
	 * @see http://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html
	 * 
	 */
	public void totalExcelAverage(Sheet sheet, Row row, CellStyle standard, List<String[]> lines, int columnsLength,  int startColumn, int rowIni, int rowEnd) {
		
		int sum = rowIni - rowEnd;
		
			if(sum == 1)
				rowEnd -= 1;
			
		rowIni += 1;
							
		// LOOP
		for(int col = startColumn; col <= columnsLength; col++) {

			String columnLetter = CellReference.convertNumToColString(col); // COLUMN LETTER

			setFormula(sheet, row, rowEnd, col, "ROUND(AVERAGEIF("+columnLetter+""+ (rowIni) + ":"+columnLetter+"" + (rowEnd) + ", \">0\"), 2)");	// DEFINE FORMULA										

		}

		setCellsStyle(sheet, row, standard, startColumn, columnsLength, rowEnd, rowEnd); // TOTAL STYLE		

	}

	// ----------------------------------------------------------------------------------------------------------------

	// SPECIAL METHOD

	/**
	 * Método para criar um coluna com total em tempo (hh:mm:ss)
	 * @author Wellington 15/10/2021
	 * @version 1.0
	 * @since 1.0  		            
	 * @param wb - objeto de representação de alto nível de uma pasta de trabalho do Excel
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha 
	 * @param style - objeto de representação de alto nível de estilos em uma planilha 
	 * @param lines - lista com dados  
	 * @param rowTotal - linha total
	 * @param columnsLength - número de colunas
	 * @param colNumber - coluna a ser feita soma dos dados
	 * @param rowIni - linha inicial
	 * @param rowEnd - linha final
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFWorkbook.html	
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Sheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/Row.html 
	 * @see https://poi.apache.org/apidocs/4.0/org/apache/poi/ss/usermodel/CreationHelper.html 
	 */
	public void totalExcelDate(XSSFWorkbook wb, Sheet sheet, Row row, CellStyle standard, int colNumber, int rowTotal, int rowIni, int rowEnd) {

		// CREATE FONT	
		Font font = createFont(wb, FONT_ARIAL, 10, false, false, IndexedColors.BLACK);

		// CREATE STYLE
		CellStyle cellStyle = createCellStyle(wb);

		// SET STYLE
		cellStyle = createCellStyle(wb, font, HorizontalAlignment.CENTER, IndexedColors.WHITE, FillPatternType.SOLID_FOREGROUND, ALL_BORDERS, BorderStyle.THIN);
	
		// HELPER							
		CreationHelper createHelper = wb.getCreationHelper();

		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("hh:mm:ss"));
		
		//System.out.println(colNumber);

		String columnLetter = CellReference.convertNumToColString(colNumber);	// COLUMN LETTER	

		// FORMULA				
		String formula = "(";
		String aux= "";

		for(int i = rowIni; i <= rowEnd; i++) {
			aux += ""+columnLetter+""+i+"";

			if(i < rowEnd)
				aux += "+";

		}

		formula += aux + ")";
		
		setFormula(sheet, row, rowTotal, colNumber, formula);	// DEFINE FORMULA	

		setCellStyle(sheet, row, standard, rowTotal, rowTotal, colNumber, colNumber); // TOTAL STYLE

	}	

	// ----------------------------------------------------------------------------------------------------------------

	/*	public void totalExcelIntervals(Sheet sheet, Row row, int[] selectedEquipamento, int startColumn, int length, int ini, int rowIni, int rowMax, int dia) {

			int col = selectedEquipamento.length;
			int totalCol = col + 1;

			String initialColumnLetter = CellReference.convertNumToColString(startColumn);
			String maxColumnLetter = CellReference.convertNumToColString((col));
			String totalColumnLetter = CellReference.convertNumToColString(totalCol);

			for(int i = startColumn; i < length; i++) {
				for(int r = ini; r < rowMax; r++) { 

					String columnLetter = CellReference.convertNumToColString(i);

					setFormula(sheet, row, r, totalCol, "SUM("+initialColumnLetter+""+ (r+1) + ":"+maxColumnLetter+"" + (r+1) + ")");

					setFormula(sheet, row, rowMax, i, "SUM("+columnLetter+""+ (rowIni) + ":"+columnLetter+"" + (rowMax) + ")");	

					setFormula(sheet, row, rowMax, totalCol, "SUM("+totalColumnLetter+""+ (rowIni) + ":"+totalColumnLetter+"" + (rowMax) + ")");

				}				
			}

		}*/


	// ----------------------------------------------------------------------------------------------------------------		
	// ----------------------------------------------------------------------------------------------------------------
	// PROCESS DATA
	// ----------------------------------------------------------------------------------------------------------------	

	/**
	 * Método para criar um modelo para um relátorio com multiplas planilhas
	 * @author Wellington 15/10/2021
	 * @version 1.0
	 * @since 1.0 
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha		
	 * @param values - matriz com os valores obtidos pelo banco
	 * @param startCol - coluna inicial
	 * @param endCol - coluna final
	 * @param startRow - linha inicial
	 * @param endRow - linha final
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFSheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFRow.html			 
	 */

	public void fileBodySingle(XSSFSheet sheet, XSSFRow row, String[][] values, int startCol, int endCol, int startRow, int endRow) {

		int rowLenght = startRow + endRow ;

		for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
			for(int col = startCol; col < endCol; col++) {

				row = sheet.getRow((short) rowIndex);												   				 				   

				try {
													
					if(values[lin][col].matches(NUMBER_REGEX))
						row.getCell(col).setCellValue(Integer.parseInt(values[lin][col]));
						
					else if(values[lin][col].matches(DOUBLE_REGEX))
							row.getCell(col).setCellValue(Double.parseDouble(values[lin][col]));
						
						else row.getCell(col).setCellValue(values[lin][col]);	
					

				}catch(NullPointerException ex) {
					ex.printStackTrace();
				}		

			}		       
		}     	   
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Método para criar um modelo para um relátorio com multiplas planilhas
	 * @author Wellington 15/10/2021
	 * @version 1.0
	 * @since 1.0 
	 * @param sheet - objeto de representação de alto nível de uma planilha
	 * @param row - objeto de representação de alto nível de uma linha de uma planilha		
	 * @param values - matriz com os valores obtidos pelo banco
	 * @param startCol - coluna inicial
	 * @param endCol - coluna final
	 * @param startRow - linha inicial
	 * @param endRow - linha final
	 * @param day - índice do dia
	 * @param interval - índice do intervalo
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFSheet.html
	 * @see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFRow.html			 
	 */
	public void fileBodyMulti(XSSFSheet sheet, XSSFRow row, String[][] values, int startCol, int endCol, int startRow, int endRow, int day, int interval) {

		int rowLenght = startRow + endRow ;
		int index = 0;

		for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
			for(int col = startCol; col < endCol; col++) {

				index = lin + (day * interval);

				row = sheet.getRow((short) rowIndex);

				try {
					
					if(values[index][col].matches(NUMBER_REGEX))
						row.getCell(col).setCellValue(Integer.parseInt(values[index][col]));
						
					else if(values[index][col].matches(DOUBLE_REGEX))
							row.getCell(col).setCellValue(Double.parseDouble(values[index][col]));
						
						else row.getCell(col).setCellValue(values[index][col]);	

				}catch(NullPointerException ex) {
					ex.printStackTrace();
				}				         
			}				        
		}		    	  
	}	

	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------


	public void fileBodySingle(XSSFSheet sheet, XSSFRow row, List<String> columnName, List<String[]> values, int startCol, int endCol, int startRow) {

		int rowLenght = startRow + values.size() ;
					  			
		  for (int col = startCol; col < columnName.size(); col++) {
			  
			  for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < values.size(); rowIndex++, lin++) {
		 		
				row = sheet.getRow((short) rowIndex);	
								
				try {
																							
				if(values.get(lin)[col].matches(NUMBER_REGEX))
				     row.getCell(col).setCellValue(Integer.parseInt(values.get(lin)[col]));
						
				else if(values.get(lin)[col].matches(DOUBLE_REGEX))
				        row.getCell(col).setCellValue(Double.parseDouble(values.get(lin)[col]));
						
				else row.getCell(col).setCellValue(values.get(lin)[col].toString());	
					
				}catch(NullPointerException ex) {
					ex.printStackTrace();
				}		

			}		       
		}     	   
	}
	
	// ------------------------------------------------------------------------------------------------------------

	
	// USE FOR SOS

	/*public void fillDataSingleSOSAmount(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow) {

		int rowLenght = startRow + endRow ;

		for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
			for(int col = colStart; col < maxCol; col++) {

				row = sheet.getRow((short) rowIndex);

				cells[lin][col] = row.createCell((short) col);	

				try {

					if(values[lin][col] != null && (values[lin][col].contains("/") || values[lin][col].contains(":") || values[lin][col].contains("-")))			            		
						cells[lin][col].setCellValue(values[lin][col] == null ? "00:00:00" : values[lin][col]);	

					else if(values[lin][col] == null && col == (maxCol - 1))			            		
						cells[lin][col].setCellValue("00:00:00");

					else cells[lin][col].setCellValue(values[lin][col] == null ? 0 : Integer.parseInt(values[lin][col]));

				}catch(NullPointerException ex) {
					ex.printStackTrace();
				}		

			}		       
		}     	   
	}


	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	// USE FOR SOS

	// CALL COUNT

	/*	public void fillDataEquipSingleSOSAmount(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow) {

				int rowLenght = startRow + endRow ;

				for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
					for(int col = colStart; col < maxCol; col++) {

						row = sheet.getRow((short) rowIndex);

						cells[lin][col] = row.createCell((short) col);	

						try {

							if(col == 0 || col == 1 || col == 2 || (values[lin][col] != null && values[lin][col].contains(":")))		            		
								cells[lin][col].setCellValue(values[lin][col] == null ? "00:00:00" : values[lin][col]);	

							else if(values[lin][col] == null && col == (maxCol - 1))			            		
								cells[lin][col].setCellValue("00:00:00");

							else cells[lin][col].setCellValue(values[lin][col] == null ? 0 : Integer.parseInt(values[lin][col]));

						}catch(NullPointerException ex) {
							ex.printStackTrace();
						}		

					}		       
				}     	   
			}

			/* *********************************************************************************************************************************************************************************** */


	//SOS AMOUNT USE

	/*		public void fillDataRangeSOSAmount(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow, int day, int periodRange) {

				int rowLenght = startRow + endRow ;
				int index = 0;

				for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
					for(int col = colStart; col < maxCol; col++) {

						index = lin + (day * periodRange);

						row = sheet.getRow((short) rowIndex);
						cells[index][col] = row.createCell((short) col);	

						try {

							if(values[index][col] != null && (values[index][col].contains("/") || values[index][col].contains(":") || values[index][col].contains("-")))			            		
								cells[index][col].setCellValue(values[index][col]);	

							else if(values[index][col] == null && col == (maxCol - 1))			            		
								cells[index][col].setCellValue("00:00:00");	


							else cells[index][col].setCellValue(values[index][col] == null? 0 : Integer.parseInt(values[index][col]));

						}catch(NullPointerException ex) {
							ex.printStackTrace();
						}				         
					}				        
				}		    	  
			}	

			/* *********************************************************************************************************************************************************************************** */

	/*	public void fillDataEquipRangeSOSAmount(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow, int day, int periodRange) {

				int rowLenght = startRow + endRow ;
				int index = 0;

				for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
					for(int col = colStart; col < maxCol; col++) {

						index = lin + (day * periodRange);

						row = sheet.getRow((short) rowIndex);
						cells[index][col] = row.createCell((short) col);	

						try {

							if(col == 0 || col == 1 || col == 2 || (values[index][col] != null && values[index][col].contains(":")))			            		
								cells[index][col].setCellValue(values[index][col]);		

							else if(values[index][col] == null && col == (maxCol - 1))			            		
								cells[index][col].setCellValue("00:00:00");	


							else cells[index][col].setCellValue(values[index][col] == null? 0 : Integer.parseInt(values[index][col]));

						}catch(NullPointerException ex) {
							ex.printStackTrace();
						}				         
					}				        
				}		    	  
			}	

			/* *********************************************************************************************************************************************************************************** */

	/* public void excelStringBasic(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, int startRow) {

				int rowLenght = startRow + values.length;

				for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < values.length; rowIndex++, lin++) {    			
					for(int col = 0; col < values[0].length; col++) {

						row = sheet.getRow((short) rowIndex);

						// System.out.println(rowIndex);

						cells[lin][col] = row.createCell((short) col);	

						try {    			            				            	

							cells[lin][col].setCellValue(values[lin][col]);	


						}catch(NullPointerException ex) {
							ex.printStackTrace();
						}		

					}		       
				}     	   
			}

			/* *********************************************************************************************************************************************************************************** */

	//OK -> 03/06/2021
	//STANDARD EXCEL MODEL USED
	//DAYS / MONTH / YEAR
	/*public void fillDataEquipSOSSingle(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow) {

				int rowLenght = startRow + endRow ;

				for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
					for(int col = colStart; col < maxCol; col++) {

						row = sheet.getRow((short) rowIndex);

						cells[lin][col] = row.createCell((short) col);	

						try {

							if(col == 0 || col == 1 || col == 2)
								cells[lin][col].setCellValue(values[lin][col]);			            			            

							else cells[lin][col].setCellValue(values[lin][col] == null ? 0 : Integer.parseInt(values[lin][col]));

						}catch(NullPointerException ex) {
							ex.printStackTrace();
						}		

					}		       
				}     	   
			}

			/* *********************************************************************************************************************************************************************************** */
	/* *********************************************************************************************************************************************************************************** */

	//Convert Fiels to Integer
	//STANDARD EXCEL MODEL DATA BY RANGE
	/*		public void fillDataRange(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow, int day, int periodRange) {

				int rowLenght = startRow + endRow ;
				int index = 0;

				for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
					for(int col = colStart; col < maxCol; col++) {

						index = lin + (day * periodRange);

						row = sheet.getRow((short) rowIndex);
						cells[index][col] = row.createCell((short) col);	

						try {

							if(col == 0 || col == 1)
								cells[index][col].setCellValue(values[index][col]); 

							else if(values[index][col] != null && values[index][col].contains("."))
								cells[index][col].setCellValue(values[index][col] == null ? 0 : Double.parseDouble(values[index][col]));

							else cells[index][col].setCellValue(values[index][col] == null? 0 : Integer.parseInt(values[index][col]));

						}catch(NullPointerException ex) {
							ex.printStackTrace();
						}				         
					}				        
				}		    	  
			}	

			/* *********************************************************************************************************************************************************************************** */

	//OK -> 03/06/2021
	//STANDARD EXCEL MODEL USED
	//DAYS / MONTH / YEAR
	/*		public void fillDataSingle(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow) {

				int rowLenght = startRow + endRow ;

				for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
					for(int col = colStart; col < maxCol; col++) {

						row = sheet.getRow((short) rowIndex);

						cells[lin][col] = row.createCell((short) col);	

						try {

							if(col == 0 || col == 1)
								cells[lin][col].setCellValue(values[lin][col]);	

							else if(values[lin][col] != null && values[lin][col].contains("."))
								cells[lin][col].setCellValue(values[lin][col] == null ? 0 : Double.parseDouble(values[lin][col]));

							else cells[lin][col].setCellValue(values[lin][col] == null ? 0 : Integer.parseInt(values[lin][col]));

						}catch(NullPointerException ex) {
							ex.printStackTrace();
						}		

					}		       
				}     	   
			}

			/* *********************************************************************************************************************************************************************************** */

	//Convert Fiels to Integer
	/*		public void fillDataSingleMonthReport(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow) {

				int rowLenght = startRow + endRow ;

				for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
					for(int col = colStart; col < maxCol; col++) {

						row = sheet.getRow((short) rowIndex);
						cells[lin][col] = row.createCell((short) col);	

						try {

							if(values[lin][col] != null && values[lin][col].contains("."))
								cells[lin][col].setCellValue(values[lin][col] == null? 0 : Double.parseDouble(values[lin][col]));

							else cells[lin][col].setCellValue(values[lin][col] == null? 0 : Integer.parseInt(values[lin][col]));

						}catch(NullPointerException ex) {
							ex.printStackTrace();
						}				          
					}					       
				}     	   
			}

			/* *********************************************************************************************************************************************************************************** */

	//Convert Fiels to Integer
	/*	public void fillDataSingleYearReport(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow) {

				int rowLenght = startRow + endRow ;

				for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
					for(int col = colStart; col < maxCol; col++) {

						row = sheet.getRow((short) rowIndex);
						cells[lin][col] = row.createCell((short) col);	

						try {

							if(col == 0)
								cells[lin][col].setCellValue(values[lin][col]); 

							else if(values[lin][col] != null && values[lin][col].contains("."))
								cells[lin][col].setCellValue(values[lin][col] == null? 0 : Double.parseDouble(values[lin][col]));

							else cells[lin][col].setCellValue(values[lin][col] == null? 0 : Integer.parseInt(values[lin][col]));

						}catch(NullPointerException ex) {
							ex.printStackTrace();
						}					            
					}					       
				}     	   
			}	

			/* *********************************************************************************************************************************************************************************** */

	//Convert Fiels to Integer
	//COUNT FLOW
	/*	public void fillDataSingleFlow(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow) {

				int rowLenght = startRow + endRow ;
				int auxCol = 0;								

				for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {

					auxCol = 0;

					for(int col = colStart; col < maxCol; col++) {

						row = sheet.getRow((short) rowIndex);
						cells[lin][col] = row.createCell((short) col);	

						try {

							if(col == 0 || col == 1)
								cells[lin][col].setCellValue(values[lin][auxCol]); 

							else cells[lin][col].setCellValue(values[lin][auxCol] == null? 0 : Integer.parseInt(values[lin][auxCol]));

						}catch(NullPointerException ex) {
							ex.printStackTrace();
						}	

						auxCol++; //Coluna come�a do indice 0 para percorrer o array
					}					       
				}     	   
			}

			/* *********************************************************************************************************************************************************************************** */			

	// Convert Fiels to Integer
	// SOBRECARGA DE METODO
	// MONTH FLOW
	/*	public void fillDataSingle(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, int startAux, int colStart, int maxCol, int startRow, int endRow) {

				int rowLenght = startRow + endRow ;
				int auxCol = startAux;

				for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
					for(int col = colStart; col < maxCol; col++) {

						row = sheet.getRow((short) rowIndex);
						cells[lin][col] = row.createCell((short) col);	

						try {

							if(col == 1 || col == 2)
								cells[lin][col].setCellValue(values[lin][auxCol]); 

							else cells[lin][col].setCellValue(values[lin][auxCol] == null? 0 : Integer.parseInt(values[lin][auxCol]));

						}catch(NullPointerException ex) {
							ex.printStackTrace();
						}

						auxCol++; //Coluna come�a do indice 0 para percorrer o array
					}					       
				}     	   
			}

			/* *********************************************************************************************************************************************************************************** */

	// PERIOD FLOW
	/*	public void fillDataSinglePeriodFlow(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow) {

				int rowLenght = startRow + endRow ;
				int auxCol = colStart;							

				for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
					for(int col = colStart; col < maxCol; col++) {

						row = sheet.getRow((short) rowIndex);
						cells[lin][col] = row.createCell((short) col);	

						try {	

							if(col == 3 || col == 4 || col == 5 || col == 14 || col == 23 || col == 32 ||
									col == 41 || col == 50 || col == 59)
								cells[lin][col].setCellValue(values[lin][auxCol]); 

							else if( col == 60 || col == 61 || col == 62 || col == 62 ||  
									col == 63 ||  col == 64 ||  col == 65 ||  col == 66 || col == 67 || col == 68)					            	

								cells[lin][col].setCellValue((int) Double.parseDouble((values[lin][auxCol])));

							else cells[lin][col].setCellValue(values[lin][auxCol] == null? 0 : Integer.parseInt(values[lin][auxCol]));

						}catch(NullPointerException ex) {
							ex.printStackTrace();
						}

						if(col == 5)
							auxCol+=2;

						else auxCol++; 
					}				     
				}     	   
			}

			/* *********************************************************************************************************************************************************************************** */				

	//SINGLE FOR DIRECTIONS REPORTS 

	//Convert Fiels to Integer
	/*	public void fillDataSingleDirections(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow, int inc) {

				int rowLenght = startRow + endRow ;

				for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
					for(int col = colStart; col < maxCol; col++) {

						row = sheet.getRow((short) rowIndex);
						cells[lin][col] = row.createCell((short) col);	

						try {

							if(col == 0 || col == 1)
								cells[lin][col].setCellValue(values[lin][col]); 

							else cells[lin][col].setCellValue(values[lin][col] == null? 0 : Integer.parseInt(values[lin][col]));

						}catch(NullPointerException ex) {
							ex.printStackTrace();
						}					            
					}						     
				}     	   
			}

			/* *********************************************************************************************************************************************************************************** */

	//SINGLE FOR DIRECTIONS REPORTS DIR 1
	/*		public void fillDataSingleDirectionsDir1(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow, int iniCol1) {

				int rowLenght = startRow + endRow ;
				int auxCol;

				for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {

					auxCol = iniCol1;

					for(int col = colStart; col < maxCol; col++) {

						row = sheet.getRow((short) rowIndex);
						cells[lin][col] = row.createCell((short) col);	

						try {

							if(col == 0 || col == 1)
								cells[lin][col].setCellValue(values[lin][col]); 

							else {

								cells[lin][col].setCellValue(values[lin][auxCol] == null? 0 : Integer.parseInt(values[lin][auxCol]));

								auxCol++;
							}



						}catch(NullPointerException ex) {
							ex.printStackTrace();
						}					            				           						     
					}				    
				}     	   
			}

			/* *********************************************************************************************************************************************************************************** */

	//SINGLE FOR DIRECTIONS REPORTS DIR 1
	/*		public void fillDataSingleDirectionsDir2(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow, int iniCol2) {

				int rowLenght = startRow + endRow ;
				int auxCol;

				for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {

					auxCol = iniCol2;

					for(int col = colStart; col < maxCol; col++) {

						row = sheet.getRow((short) rowIndex);
						cells[lin][col] = row.createCell((short) col);	

						try {

							if(col == 0 || col == 1)
								cells[lin][col].setCellValue(values[lin][col]); 

							else {

								cells[lin][col].setCellValue(values[lin][auxCol] == null? 0 : Integer.parseInt(values[lin][auxCol]));

								auxCol++;
							}

						}catch(NullPointerException ex) {
							ex.printStackTrace();
						}					            						     
					}					    						   					     
				}     	   
			}

			/* *********************************************************************************************************************************************************************************** */


	/* *********************************************************************************************************************************************************************************** */				

	//SINGLE FOR DIRECTIONS REPORTS 

	//Convert Fiels to Integer
	/*	public void fillDataEquipSingleDirections(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow, int inc) {

				int rowLenght = startRow + endRow ;

				for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
					for(int col = colStart; col < maxCol; col++) {

						row = sheet.getRow((short) rowIndex);
						cells[lin][col] = row.createCell((short) col);	

						try {

							if(col == 0 || col == 1 || col == 2)
								cells[lin][col].setCellValue(values[lin][col]); 

							else cells[lin][col].setCellValue(values[lin][col] == null? 0 : Integer.parseInt(values[lin][col]));

						}catch(NullPointerException ex) {
							ex.printStackTrace();
						}					            
					}						     
				}     	   
			}

			/* *********************************************************************************************************************************************************************************** */

	//SINGLE FOR DIRECTIONS REPORTS DIR 1
	/*		public void fillDataEquipSingleDirectionsDir1(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow, int iniCol1) {

				int rowLenght = startRow + endRow ;
				int auxCol;

				for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {

					auxCol = iniCol1;

					for(int col = colStart; col < maxCol; col++) {

						row = sheet.getRow((short) rowIndex);
						cells[lin][col] = row.createCell((short) col);	

						try {

							if(col == 0 || col == 1 || col == 2)
								cells[lin][col].setCellValue(values[lin][col]); 

							else {

								cells[lin][col].setCellValue(values[lin][auxCol] == null? 0 : Integer.parseInt(values[lin][auxCol]));

								auxCol++;
							}



						}catch(NullPointerException ex) {
							ex.printStackTrace();
						}					            				           						     
					}				    
				}     	   
			}

			/* *********************************************************************************************************************************************************************************** */

	//SINGLE FOR DIRECTIONS REPORTS DIR 1
	/*		public void fillDataEquipSingleDirectionsDir2(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow, int iniCol2) {

				int rowLenght = startRow + endRow ;
				int auxCol;

				for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {

					auxCol = iniCol2;

					for(int col = colStart; col < maxCol; col++) {

						row = sheet.getRow((short) rowIndex);
						cells[lin][col] = row.createCell((short) col);	

						try {

							if(col == 0 || col == 1 || col == 2)
								cells[lin][col].setCellValue(values[lin][col]); 

							else {

								cells[lin][col].setCellValue(values[lin][auxCol] == null? 0 : Integer.parseInt(values[lin][auxCol]));

								auxCol++;
							}

						}catch(NullPointerException ex) {
							ex.printStackTrace();
						}					            						     
					}					    						   					     
				}     	   
			}

			/* *********************************************************************************************************************************************************************************** */

	/////////////////////////////////////////
	/// PERIODs
	////////////////////////////////////////

	//Convert Fiels to Integer
	/*		public void fillDataRangeDirections(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow, int day, int periodRange, int inc) {

				int rowLenght = startRow + endRow ;
				int index = 0;					    	  

				for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
					for(int col = colStart; col < maxCol; col++) {

						index = lin + (day * periodRange);

						row = sheet.getRow((short) rowIndex);
						cells[index][col] = row.createCell((short) col);	

						try {

							if(col == 0 || col == 1)
								cells[index][col].setCellValue(values[index][col]);			          			            

							else cells[index][col].setCellValue(values[index][col] == null? 0 : Integer.parseInt(values[index][col]));

						}catch(NullPointerException ex) {
							ex.printStackTrace();
						}	     
					}										     
				}		    	  
			}	

			/* *********************************************************************************************************************************************************************************** */

	//Convert Fiels to Integer
	/*		public void fillDataRangeDirectionsDir1(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow, int day, int periodRange, int iniDir1) {

				int rowLenght = startRow + endRow ;
				int index = 0;
				int auxCol;

				for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {

					auxCol = iniDir1;

					for(int col = colStart; col < maxCol; col++) {

						index = lin + (day * periodRange);

						row = sheet.getRow((short) rowIndex);
						cells[index][col] = row.createCell((short) col);	

						try {

							if(col == 0 || col == 1)
								cells[lin][col].setCellValue(values[index][col]); 


							else {

								cells[lin][col].setCellValue(values[lin][auxCol] == null? 0 : Integer.parseInt(values[lin][auxCol]));							            		
								auxCol++;
							}



						}catch(NullPointerException ex) {
							ex.printStackTrace();
						}					  				        						   
					}														     
				}		    	  
			}		

			/* *********************************************************************************************************************************************************************************** */

	//Convert Fiels to Integer
	/*		public void fillDataRangeDirectionsDir2(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow, int day, int periodRange, int iniDir2) {

				int rowLenght = startRow + endRow ;
				int index = 0;
				int auxCol;

				for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {

					auxCol = iniDir2;

					for(int col = colStart; col < maxCol; col++) {

						index = lin + (day * periodRange);

						row = sheet.getRow((short) rowIndex);
						cells[index][col] = row.createCell((short) col);	

						try {

							if(col == 0 || col == 1)
								cells[lin][col].setCellValue(values[index][col]); 

							else {

								cells[lin][col].setCellValue(values[lin][auxCol] == null? 0 : Integer.parseInt(values[lin][auxCol]));							            						            	
								auxCol++;

							}

						}catch(NullPointerException ex) {
							ex.printStackTrace();
						}				            		
					}
				}
			}	

			/* *********************************************************************************************************************************************************************************** */

	/////////////////////////////////////////
	/// PERIODs
	////////////////////////////////////////

	//Convert Fiels to Integer
	/*	public void fillDataEquipRangeDirections(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow, int day, int periodRange, int inc) {

				int rowLenght = startRow + endRow ;
				int index = 0;					    	  

				for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
					for(int col = colStart; col < maxCol; col++) {

						index = lin + (day * periodRange);

						row = sheet.getRow((short) rowIndex);
						cells[index][col] = row.createCell((short) col);	

						try {

							if(col == 0 || col == 1 || col == 2)
								cells[index][col].setCellValue(values[index][col]);			          			            

							else cells[index][col].setCellValue(values[index][col] == null? 0 : Integer.parseInt(values[index][col]));

						}catch(NullPointerException ex) {
							ex.printStackTrace();
						}	     
					}										     
				}		    	  
			}	

			/* *********************************************************************************************************************************************************************************** */

	//Convert Fiels to Integer
	/*		public void fillDataEquipRangeDirectionsDir1(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow, int day, int periodRange, int iniDir1) {

				int rowLenght = startRow + endRow ;
				int index = 0;
				int auxCol;

				for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {

					auxCol = iniDir1;

					for(int col = colStart; col < maxCol; col++) {

						index = lin + (day * periodRange);

						row = sheet.getRow((short) rowIndex);
						cells[index][col] = row.createCell((short) col);	

						try {

							if(col == 0 || col == 1 || col == 2)
								cells[lin][col].setCellValue(values[index][col]); 


							else {

								cells[lin][col].setCellValue(values[lin][auxCol] == null? 0 : Integer.parseInt(values[lin][auxCol]));							            		
								auxCol++;
							}



						}catch(NullPointerException ex) {
							ex.printStackTrace();
						}					  				        						   
					}														     
				}		    	  
			}		

			/* *********************************************************************************************************************************************************************************** */

	// Convert Fiels to Integer
	/*		public void fillDataEquipRangeDirectionsDir2(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow, int day, int periodRange, int iniDir2) {

				int rowLenght = startRow + endRow ;
				int index = 0;
				int auxCol;

				for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {

					auxCol = iniDir2;

					for(int col = colStart; col < maxCol; col++) {

						index = lin + (day * periodRange);

						row = sheet.getRow((short) rowIndex);
						cells[index][col] = row.createCell((short) col);	

						try {

							if(col == 0 || col == 1 || col == 2)
								cells[lin][col].setCellValue(values[index][col]); 

							else {

								cells[lin][col].setCellValue(values[lin][auxCol] == null? 0 : Integer.parseInt(values[lin][auxCol]));							            						            	
								auxCol++;

							}

						}catch(NullPointerException ex) {
							ex.printStackTrace();
						}				            		
					}
				}
			}	

			/* *********************************************************************************************************************************************************************************** */

	// Convert Fiels to Integer
	// STANDARD EXCEL MODEL DATA BY RANGE
	/*	public void fillDataEquipRange(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow, int day, int periodRange) {

				int rowLenght = startRow + endRow ;
				int index = 0;

				for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
					for(int col = colStart; col < maxCol; col++) {

						index = lin + (day * periodRange);

						row = sheet.getRow((short) rowIndex);
						cells[index][col] = row.createCell((short) col);	

						try {

							if(col == 0 || col == 1 || col == 2)
								cells[index][col].setCellValue(values[index][col]); 

							else if(values[index][col] != null && values[index][col].contains("."))
								cells[index][col].setCellValue(values[index][col] == null ? 0 : Double.parseDouble(values[index][col]));

							else cells[index][col].setCellValue(values[index][col] == null? 0 : Integer.parseInt(values[index][col]));

						}catch(NullPointerException ex) {
							ex.printStackTrace();
						}				         
					}				        
				}		    	  
			}	

			/* *********************************************************************************************************************************************************************************** */

	//OK -> 03/06/2021
	//STANDARD EXCEL MODEL USED
	//DAYS / MONTH / YEAR
	/*	public void fillDataEquipSingle(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow) {

				int rowLenght = startRow + endRow ;

				for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
					for(int col = colStart; col < maxCol; col++) {

						row = sheet.getRow((short) rowIndex);

						cells[lin][col] = row.createCell((short) col);	

						try {

							if(col == 0 || col == 1 || col == 2)
								cells[lin][col].setCellValue(values[lin][col]);			            			            

							else cells[lin][col].setCellValue(values[lin][col] == null ? 0 : Integer.parseInt(values[lin][col]));

						}catch(NullPointerException ex) {
							ex.printStackTrace();
						}		

					}		       
				}     	   
			}

			/* *********************************************************************************************************************************************************************************** */

	
	public void mergeBetweenColumns(XSSFSheet sheet, int startCol, int endCol, int startRow, int endRow) {
				
			String columnStartLetter = CellReference.convertNumToColString(startCol); // COLUMN LETTER
			String columnEndLetter = CellReference.convertNumToColString(endCol); // COLUMN LETTER
			
			mergeCells(sheet, columnStartLetter.concat(""+startRow).concat(":").concat(columnEndLetter).concat(""+endRow));

	}
	
	// -----------------------------------------------------------------------------------------------------------------------------------------------
	
	  public void borderTemplate(String template, CellStyle style, BorderStyle borderStyle) {
				  
		  switch(template) {
		  
		  case "TOP": setBorderTop(style, borderStyle); break;
		  case "BOTTOM": setBorderBottom(style, borderStyle); break;
		  case "LEFT": setBorderLeft(style, borderStyle); break;
		  case "RIGHT": setBorderRight(style, borderStyle); break;
		  case "ALL": setBorders(style, borderStyle); break;
				  		  		  		  
		  }	  
	  }
	  
	// -----------------------------------------------------------------------------------------------------------------------------------------------
		
		public void fileBodySimple(XSSFSheet sheet, XSSFRow row, List<String> columnName, List<String[]> values, int startCol, int endCol, int startRow) {

			int rowLenght = startRow + values.size();
						  			
			  for (int col = startCol; col < columnName.size(); col++) {
				  
				  for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < values.size(); rowIndex++, lin++) {
			 		
					row = sheet.getRow((short) rowIndex);
					
					try {
																																				
					if(values.get(lin)[col].matches(NUMBER_REGEX))
					     row.getCell(col).setCellValue(Integer.parseInt(values.get(lin)[col]));
							
					else if(values.get(lin)[col].matches(DOUBLE_REGEX))
					     row.getCell(col).setCellValue(Double.parseDouble(values.get(lin)[col]));
							
					else row.getCell(col).setCellValue(values.get(lin)[col].toString());	
									
					}catch(NullPointerException ex) {}		

				}		       
			}     	   
		}
		
		// -----------------------------------------------------------------------------------------------------------------------------------------------
			
		public void fileBodySimpleDirection(XSSFSheet sheet, XSSFRow row, List<String> columnName, String left, List<String[]> values, String[] directions, List<String> equips, List<laneFilter> lanes, int startCol, int endCol, int dirCol, int startRow, boolean all) {

			int rowLenght = startRow + values.size();
				
			  String direction = "";
									  			
			  for (int col = startCol; col < columnName.size(); col++) {
				  
				  for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < values.size(); rowIndex++, lin++) {
					  					 			 		
					row = sheet.getRow((short) rowIndex);
					
					try {
																																										
					if(values.get(lin)[col].matches(NUMBER_REGEX))
					     row.getCell(col).setCellValue(Integer.parseInt(values.get(lin)[col]));
							
					else if(values.get(lin)[col].matches(DOUBLE_REGEX))
					     	row.getCell(col).setCellValue(Double.parseDouble(values.get(lin)[col]));
							
					else row.getCell(col).setCellValue(values.get(lin)[col].toString());	
					
					 // -------------------------------------------------------------------------------------------
					
					  // COLUMNS DIRECTIONS
																		
				      if(col == columnName.size() - 1) {
				    					    	  
				    	if(equips.size() > 1) {
				    		
				    		 for(int l = 0; l < lanes.size(); l++) {
									
									if(all) {
										if(lanes.get(l).getLane() == 1 && (Integer.parseInt(values.get(lin)[2]) == lanes.get(l).getEquipId())) {
											direction =  getLane(lanes.get(l).getDirection(), directions);
											row.getCell(dirCol).setCellValue(direction);
										}
									
									} else row.getCell(dirCol).setCellValue(left);										    
								      
						        }
				    
				    } else {				    				    					    	
				    	
				    	 for(int l = 0; l < lanes.size(); l++) {
								
								if(all) {
									if(lanes.get(l).getLane() == 1 && (Integer.parseInt(equips.get(0)) == lanes.get(l).getEquipId())) {
										direction =  getLane(lanes.get(l).getDirection(), directions);
										row.getCell(dirCol).setCellValue(direction);
									}
								
								} else row.getCell(dirCol).setCellValue(left);		
									    																												
							    }					    	  
						     }				    	  
				         }
					
					}catch(NullPointerException ex) {}		
				 }				  
			  }     	   
		  }
		
		// -----------------------------------------------------------------------------------------------------------------------------------------------
				
		public void fileBodySimpleTabEquipDirs(XSSFSheet sheet, XSSFRow row, List<String> columnName, String left, List<String[]> values, String[] directions, List<String> equips, List<laneFilter> lanes, int equipIndex, int days, int startCol, int endCol, int dirCol, int startRow, int endRow, boolean all) {

			int rowLenght = startRow + endRow;
			
			int index = 0;
														
			  String direction = "";
									  			
			  for (int col = startCol; col < columnName.size(); col++) {
				  
				  for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < days; rowIndex++, lin++) {
					  										  					 			 		
					row = sheet.getRow((short) rowIndex);
										
					index = lin + (days * equipIndex);
					
					try {
																																										
						if(values.get(index)[col].matches(NUMBER_REGEX))
						     row.getCell(col).setCellValue(Integer.parseInt(values.get(index)[col]));
								
						else if(values.get(index)[col].matches(DOUBLE_REGEX))
						     	row.getCell(col).setCellValue(Double.parseDouble(values.get(index)[col]));
								
						else row.getCell(col).setCellValue(values.get(index)[col].toString());	
					
					 // -------------------------------------------------------------------------------------------
					
					  // COLUMNS DIRECTIONS
																		
				      if(col == columnName.size() - 1) {
				    					    	  
				    	if(equips.size() > 1) {
				    	  
				    		 for(int l = 0; l < lanes.size(); l++) {
									
									if(all) {
										if(lanes.get(l).getLane() == 1 && (Integer.parseInt(values.get(index)[2]) == lanes.get(l).getEquipId())) {
											direction =  getLane(lanes.get(l).getDirection(), directions);
											row.getCell(dirCol).setCellValue(direction);
										}
									
									} else row.getCell(dirCol).setCellValue(left);										    
								      
						        }
				    
				    } else {
				    	
				    	 for(int l = 0; l < lanes.size(); l++) {
								
								if(all) {
									if(lanes.get(l).getLane() == 1 && (Integer.parseInt(equips.get(0)) == lanes.get(l).getEquipId())) {
										direction =  getLane(lanes.get(l).getDirection(), directions);
										row.getCell(dirCol).setCellValue(direction);
									}
								
								} else row.getCell(dirCol).setCellValue(left);		
									    																												
							    }				    	  
						     }				    	  
				         }
					
					}catch(NullPointerException ex) {}		
				 }				  
			  }     	   
		  }
		
		// -----------------------------------------------------------------------------------------------------------------------------------------------
		
		public void fileBodySimpleDirs(XSSFSheet sheet, XSSFRow row, List<String> columnName, String left, List<String[]> values, String[] directions, List<String> equips, List<laneFilter> lanes, int equipIndex, int days, int startCol, int endCol, int dirCol, int startRow, int endRow, boolean all , boolean isDirectionsOnSheet, 
				boolean isSingleDirectionFilter) {

			int rowLenght = startRow + endRow;
			
			int index = 0;
														
			  String direction = "";
									  			
			  for (int col = startCol; col < columnName.size(); col++) {
				  
				  for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < days; rowIndex++, lin++) {
					  										  					 			 		
					row = sheet.getRow((short) rowIndex);
															
					index = lin + (days * equipIndex);
																				
					try {
																																										
						if(values.get(index)[col].matches(NUMBER_REGEX))
						     row.getCell(col).setCellValue(Integer.parseInt(values.get(index)[col]));
								
						else if(values.get(index)[col].matches(DOUBLE_REGEX))
						     	row.getCell(col).setCellValue(Double.parseDouble(values.get(index)[col]));
								
						else row.getCell(col).setCellValue(values.get(index)[col].toString());	
					
					 // -------------------------------------------------------------------------------------------
					
					  // COLUMNS DIRECTIONS
						
					if(isDirectionsOnSheet) {
						
						if(isSingleDirectionFilter) {
							
							if(col == columnName.size() - 1) 
								  row.getCell(dirCol).setCellValue(directions[0]);
							
						} else {							
																		
					      if(col == columnName.size() - 1) {
					    					    	  
					    	if(equips.size() > 1) {
					    	  
					    	  for(int l = 0; l < lanes.size(); l++) {
																								
								if(all) {
									if(lanes.get(l).getLane() == 1 && (Integer.parseInt(values.get(index)[2]) == lanes.get(l).getEquipId())) {
										direction =  getLane(lanes.get(l).getDirection(), directions);
										row.getCell(dirCol).setCellValue(direction);
									}
								
								} else row.getCell(dirCol).setCellValue(left);										    
							      
					        }
					    
					    } else {
					    	
					    	  for(int l = 0; l < lanes.size(); l++) {
																		
										if(all) {
											if(lanes.get(l).getLane() == 1 && (Integer.parseInt(equips.get(0)) == lanes.get(l).getEquipId())) {
												direction =  getLane(lanes.get(l).getDirection(), directions);
												row.getCell(dirCol).setCellValue(direction);
											}
										
										} else row.getCell(dirCol).setCellValue(left);		
											    																												
									    }			    	
					    	  	     }				    	  
							     }	 
							  } // DEAD CODE
					         				      
					  } // DIR ENABLE
					
					}catch(NullPointerException ex) {}		
				 }				  
			  }     	   
		  }
		
		// -----------------------------------------------------------------------------------------------------------------------------------------------
						
	public void fileBodySimpleDirs2(XSSFSheet sheet, XSSFRow row, List<String> columnName, String left, List<String[]> values, String[] directions, List<String> equips, List<laneFilter> lanes, int equipIndex, int days, int startCol, int endCol, int dirCol, int startRow, int endRow, boolean all, boolean isDirectionsOnSheet) {

		  int rowLenght = startRow + endRow;
		
		  int index = 0;
													
		  String direction = "";
								  			
		  for (int col = startCol; col < columnName.size(); col++) {
			  
			  for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < days; rowIndex++, lin++) {
				  										  					 			 		
				row = sheet.getRow((short) rowIndex);
										
				index = lin + (days * equipIndex);
							
				try {
																																									
				if(values.get(index)[col].matches(NUMBER_REGEX))
				     row.getCell(col).setCellValue(Integer.parseInt(values.get(index)[col]));
						
				else if(values.get(index)[col].matches(DOUBLE_REGEX))
				     	row.getCell(col).setCellValue(Double.parseDouble(values.get(index)[col]));
						
				else row.getCell(col).setCellValue(values.get(index)[col].toString());	
				
				 // -------------------------------------------------------------------------------------------
				
				  // COLUMNS DIRECTIONS
				
				if(isDirectionsOnSheet) {
																	
			      if(col == columnName.size() - 1) {
			    					    	  
			    	if(equips.size() > 1) {
			    	  
			    		 for(int l = 0; l < lanes.size(); l++) {
								
								if(all) {
									if(lanes.get(l).getLane() == 1 && (Integer.parseInt(values.get(index)[2]) == lanes.get(l).getEquipId())) {
										direction =  getLane(lanes.get(l).getDirection(), directions);
										row.getCell(dirCol).setCellValue(direction);
									}
								
								} else row.getCell(dirCol).setCellValue(left);										    
							      
					        }
			    
			    } else {
			    	
			    	 for(int l = 0; l < lanes.size(); l++) {
							
							if(all) {
								if(lanes.get(l).getLane() == 1 && (Integer.parseInt(equips.get(0)) == lanes.get(l).getEquipId())) {
									direction =  getLane(lanes.get(l).getDirection(), directions);
									row.getCell(dirCol).setCellValue(direction);
								}
							
							} else row.getCell(dirCol).setCellValue(left);		
								    																												
						    }				    	  
					     }				    	  
			         }
			      
				} // DIR EMABLE
				
				}catch(NullPointerException ex) {}		
			 }				  
		  }     	   
	  }
	
	// -----------------------------------------------------------------------------------------------------------------------------------------------
		
			
		 public void fileBodyMulti(XSSFSheet sheet, XSSFRow row, List<String> columnName, List<String[]> values, int startCol, int endCol, int startRow, int day, int periodRange) {

			int rowLenght = startRow + values.size() ;
			int index = 0;
						  			
			  for (int col = startCol; col < columnName.size(); col++) {
				  
				  for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < periodRange; rowIndex++, lin++) {
					  
				    index = lin + (day * periodRange);
			 		
					row = sheet.getRow((short) rowIndex);	

					try {
																																				
					 if(values.get(index)[col].matches(NUMBER_REGEX))
					    row.getCell(col).setCellValue(Integer.parseInt(values.get(index)[col]));
							
					 else if(values.get(index)[col].matches(DOUBLE_REGEX))
					    row.getCell(col).setCellValue(Double.parseDouble(values.get(index)[col]));
							
					 else row.getCell(col).setCellValue(values.get(index)[col].toString());	
									
					}catch(NullPointerException ex) {}		

				}		       
			}     	   
		}
		
	  // -----------------------------------------------------------------------------------------------------------------------------------------------
	
		 public void fileBodyMultiDirection(XSSFSheet sheet, XSSFRow row, List<String> columnName, String left, List<String[]> values, String[] directions, List<String> equips, List<laneFilter> lanes, int startCol, int endCol, int dirCol, int startRow, boolean all, int day, int periodRange) {

				int rowLenght = startRow + values.size() ;
				int index = 0;
				
				  String direction = "";
							  			
				  for (int col = startCol; col < columnName.size(); col++) {
					  
					  for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < periodRange; rowIndex++, lin++) {
						  
					    index = lin + (day * periodRange);
				 		
						row = sheet.getRow((short) rowIndex);	

						try {
																																					
						 if(values.get(index)[col].matches(NUMBER_REGEX))
						    row.getCell(col).setCellValue(Integer.parseInt(values.get(index)[col]));
								
						 else if(values.get(index)[col].matches(DOUBLE_REGEX))
						    row.getCell(col).setCellValue(Double.parseDouble(values.get(index)[col]));
								
						 else row.getCell(col).setCellValue(values.get(index)[col].toString());
						 
						 // -------------------------------------------------------------------------------------------
						 
						// COLUMNS DIRECTIONS
						 
					      if(col == columnName.size() - 1) {
					    	  
						    	if(equips.size() > 1) {
						    		
						    		 for(int l = 0; l < lanes.size(); l++) {
											
											if(all) {
												if(lanes.get(l).getLane() == 1 && (Integer.parseInt(values.get(index)[2]) == lanes.get(l).getEquipId())) {
													direction =  getLane(lanes.get(l).getDirection(), directions);
													row.getCell(dirCol).setCellValue(direction);
												}
											
											} else row.getCell(dirCol).setCellValue(left);										      
								        }
						    
						    } else {
						    	
						    	 for(int l = 0; l < lanes.size(); l++) {
										
										if(all) {
											if(lanes.get(l).getLane() == 1 && (Integer.parseInt(equips.get(0)) == lanes.get(l).getEquipId())) {
												direction =  getLane(lanes.get(l).getDirection(), directions);
												row.getCell(dirCol).setCellValue(direction);
											}
										
										} else row.getCell(dirCol).setCellValue(left);		
											    																												
									    }					    	  
								   }						    	  
						       }
					      
					      // -------------------------------------------------------------------------------------------
										
						} catch(NullPointerException ex) {}
					}		       
				}     	   
			}
			
		// -----------------------------------------------------------------------------------------------------------------------------------------------
		 
		 public void fileBodyMultiDirs(XSSFSheet sheet, XSSFRow row, List<String> columnName, String left, List<String[]> values, String[] directions, List<String> equips, List<laneFilter> lanes, 
				 int equipIndex, int days, int interval, int startCol, int endCol, int dirCol, int startRow, int endRow, boolean all, boolean isDirectionsOnSheet, boolean isSingleDirectionFilter) {

				int rowLenght = startRow + endRow;
				
				int index = 0;
																				
				  String direction = "";
										  			
				  for (int col = startCol; col < columnName.size(); col++) {
					  
					  for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < interval; rowIndex++, lin++) {
						  					 			 		
						row = sheet.getRow((short) rowIndex);
						
						index = lin + (equipIndex * interval);
						
						try {
																																											
							if(values.get(index)[col].matches(NUMBER_REGEX))
							     row.getCell(col).setCellValue(Integer.parseInt(values.get(index)[col]));
									
							else if(values.get(index)[col].matches(DOUBLE_REGEX))
							     	row.getCell(col).setCellValue(Double.parseDouble(values.get(index)[col]));
									
							else row.getCell(col).setCellValue(values.get(index)[col].toString());	
						
						 // -------------------------------------------------------------------------------------------
						
						  // COLUMNS DIRECTIONS
							
						if(isDirectionsOnSheet) {
							
							if(isSingleDirectionFilter) {
								
								if(col == columnName.size() - 1) 
									  row.getCell(dirCol).setCellValue(directions[0]);
								
							} else {								
																			
								if(col == columnName.size() - 1) {				    	
					    					    	  
					    	if(equips.size() > 1) {
					    	  
					    		 for(int l = 0; l < lanes.size(); l++) {
										
										if(all) {
											if(lanes.get(l).getLane() == 1 && (Integer.parseInt(values.get(index)[2]) == lanes.get(l).getEquipId())) {
												direction =  getLane(lanes.get(l).getDirection(), directions);
												row.getCell(dirCol).setCellValue(direction);
											}
										
										} else row.getCell(dirCol).setCellValue(left);								      
							        }					    
					    } else {
					    	
					    	 for(int l = 0; l < lanes.size(); l++) {
									
									if(all) {
										if(lanes.get(l).getLane() == 1 && (Integer.parseInt(equips.get(0)) == lanes.get(l).getEquipId())) {
											direction =  getLane(lanes.get(l).getDirection(), directions);
											row.getCell(dirCol).setCellValue(direction);
										}
									
									} else row.getCell(dirCol).setCellValue(left);		
										    																												
								    }					    	  
							     }	
							   }
					        }
					      
						} // DIR ENABLE
						
						}catch(NullPointerException ex) {}		
					 }				  
				  }     	   
			  }
			
			// -----------------------------------------------------------------------------------------------------------------------------------------------
						 					
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
		
	}
