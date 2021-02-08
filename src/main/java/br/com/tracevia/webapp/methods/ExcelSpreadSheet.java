package br.com.tracevia.webapp.methods;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.BorderExtent;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
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
import org.apache.poi.ss.util.PropertyTemplate;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelSpreadSheet {
	
	Cell[] cells;
	CellStyle[] styles;
	Row[] rows;
		
	public void criarEstruturaExcel(XSSFWorkbook workbook, XSSFSheet sheet, Row[] rows, String infoHeader, int[] cellPosition, String[] tituloCol, CellStyle[] styles) {
		
		for(int r=0; r < rows.length; r++) {
			
		   rows[r] = sheet.createRow((short) r);		
		   rows[r].createCell(cellPosition[r]).setCellValue(tituloCol[r]);			   
		   rows[r].getCell(cellPosition[r]).setCellStyle(styles[r]);	
		   
		}			
	}
			
	    //Convert Fiels to Integer
		public void fillDataRange(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow, int day, int periodRange) {
			 
			int rowLenght = startRow + endRow ;
			int index = 0, auxCol = 0;
				    	  
		    	  for(int col = colStart; col < maxCol; col++) {
					   for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
						   
						   index = lin + (day * periodRange);
							
					            row = sheet.getRow((short) rowIndex);
					            cells[col][index] = row.createCell((short) col);	
					            
					            try {
					            				            	
					            	if(col == 0 || col == 1)
					            	cells[col][index].setCellValue(values[auxCol][index]); 
					            	
					            	else cells[col][index].setCellValue(Integer.parseInt(values[auxCol][index]));
					            			          			            
					            }catch(NullPointerException ex) {
					            	ex.printStackTrace();
					           }		            		
					        }
					   
					        auxCol++; //Coluna começa do indice 0 para percorrer o array
					     }		    	  
		              }			           
			
		 //Convert Fiels to Integer
		public void fillDataSingle(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow) {
			 
			int rowLenght = startRow + endRow ;
			int auxCol = colStart;
									
			for(int col = colStart; col < maxCol; col++) {
			   for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
				   				 					   
			            row = sheet.getRow((short) rowIndex);
			          
			            cells[col][lin] = row.createCell((short) col);	
			            
			            try {
			            				            	
			            	if(col == 0 || col == 1)
			            	cells[col][lin].setCellValue(values[auxCol][lin]); 
			            	
			            	else cells[col][lin].setCellValue(Integer.parseInt(values[auxCol][lin]));
			            				            			            			          			            
			            }catch(NullPointerException ex) {
			            	ex.printStackTrace();
			           }		            		
			        }
			   
			   
			   
			        auxCol++; //Coluna começa do indice 0 para percorrer o array
			     }     	   
		       }
		
		    //Convert Fiels to Integer
				public void fillDataSingleMonthReport(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow) {
					 
					int rowLenght = startRow + endRow ;
					int auxCol = 0;
											
					for(int col = colStart; col < maxCol; col++) {
					   for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
						   				 					   
					            row = sheet.getRow((short) rowIndex);
					            cells[col][lin] = row.createCell((short) col);	
					            
					            try {
					            				            	
					            	cells[col][lin].setCellValue(Integer.parseInt(values[auxCol][lin]));
					            			          			            
					            }catch(NullPointerException ex) {
					            	ex.printStackTrace();
					           }		            		
					        }
					   
					        auxCol++; //Coluna começa do indice 0 para percorrer o array
					     }     	   
				       }
				
				
				 //Convert Fiels to Integer
				public void fillDataSingleFlow(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow) {
					 
					int rowLenght = startRow + endRow ;
					int auxCol = 0;
											
					for(int col = colStart; col < maxCol; col++) {
					   for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
						   				 					   
					            row = sheet.getRow((short) rowIndex);
					            cells[col][lin] = row.createCell((short) col);	
					            
					            try {
					            				            	
					            	if(col == 0 || col == 1)
					            	cells[col][lin].setCellValue(values[auxCol][lin]); 
					            	
					            	else cells[col][lin].setCellValue(Integer.parseInt(values[auxCol][lin]));
					            			          			            
					            }catch(NullPointerException ex) {
					            	ex.printStackTrace();
					           }		            		
					        }
					   
					        auxCol++; //Coluna começa do indice 0 para percorrer o array
					     }     	   
				       }
				
				
				 //Convert Fiels to Integer
				//SOBRECARGA DE METODO
				public void fillDataSingle(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, int startAux, int colStart, int maxCol, int startRow, int endRow) {
					 
					int rowLenght = startRow + endRow ;
					int auxCol = startAux;
											
					for(int col = colStart; col < maxCol; col++) {
					   for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
						   				 					   
					            row = sheet.getRow((short) rowIndex);
					            cells[col][lin] = row.createCell((short) col);	
					            
					            try {
					            				            	
					            	if(col == 1 || col == 2)
					            	cells[col][lin].setCellValue(values[auxCol][lin]); 
					            	
					            	else cells[col][lin].setCellValue(Integer.parseInt(values[auxCol][lin]));
					            						            			          			            
					            }catch(NullPointerException ex) {
					            	ex.printStackTrace();
					           }		            		
					        }
					   
					        auxCol++; //Coluna começa do indice 0 para percorrer o array
					     }     	   
				       }
				
				public void fillDataSinglePeriodFlow(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow) {
					 
					int rowLenght = startRow + endRow ;
					int auxCol = colStart;
					
					///System.out.println(maxCol);
									
					for(int col = colStart; col < maxCol; col++) {
					   for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
						   				 					   
					            row = sheet.getRow((short) rowIndex);
					            cells[col][lin] = row.createCell((short) col);	
					            
					            try {	
					            				            	
					            	if(col == 3 || col == 4 || col == 5 || col == 14 || col == 23 || col == 32 ||
					            			col == 41 || col == 50 || col == 59)
					            	cells[col][lin].setCellValue(values[auxCol][lin]); 
					            	
					            	else if( col == 60 || col == 61 || col == 62 || col == 62 ||  
					            			col == 63 ||  col == 64 ||  col == 65 ||  col == 66 || col == 67 || col == 68)					            	
					            	    
					            		cells[col][lin].setCellValue((int) Double.parseDouble((values[auxCol][lin])));
					            	
					            	else cells[col][lin].setCellValue(Integer.parseInt(values[auxCol][lin]));
					            			          			            
					            }catch(NullPointerException ex) {
					            	ex.printStackTrace();
					           }		            		
					        }
					   
					     if(col == 5)
					    	 auxCol+=2;
					    	 
					    else auxCol++; 
					     
					     }     	   
				       }
	
				
				
				
		//Convert Fiels to Integer
				public void fillDataByValue(XSSFSheet sheet, XSSFRow row, Cell[][] cells, int[] value, int colStart, int maxCol, int startRow, int endRow) {
					 
					int rowLenght = startRow + endRow ;					
						
					for(int col = colStart; col <= maxCol; col++) {
					   for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
							
					            row = sheet.getRow((short) rowIndex);
					            cells[col][lin] = row.createCell((short) col);	
					            
					            try {
					            	
					            	cells[col][lin].setCellValue(value[lin]); 
					            						          			            
					            }catch(NullPointerException ex) {
					            
					           }		            		
					        }				       
					     }		
				      }		
				
				///////////////////////////////////////
				/// 24 Hours
				/////////////////////////////////////
				
				//SINGLE FOR DIRECTIONS REPORTS 
				
				 //Convert Fiels to Integer
				public void fillDataSingleDirections(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow, int inc) {
					 
					int rowLenght = startRow + endRow ;
					int auxCol = 0;
									
					for(int col = colStart; col < maxCol; col++) {
					   for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
						   				 					   
					            row = sheet.getRow((short) rowIndex);
					            cells[col][lin] = row.createCell((short) col);	
					            
					            try {
					            				            	
					            	if(col == 0 || col == 1)
					            	cells[col][lin].setCellValue(values[auxCol][lin]); 
					            	
					            	else cells[col][lin].setCellValue(Integer.parseInt(values[auxCol][lin]));
					            			          			            
					            }catch(NullPointerException ex) {
					            	ex.printStackTrace();
					           }		            		
					        }
					   					   					     
					      auxCol++;
										     
					     }     	   
				       }
				
				//SINGLE FOR DIRECTIONS REPORTS DIR 1
				public void fillDataSingleDirectionsDir1(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow, int iniCol1) {
					 
					int rowLenght = startRow + endRow ;
					int auxCol = 0;
									
					for(int col = colStart; col < maxCol; col++) {
					   for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
						   				 					   
					            row = sheet.getRow((short) rowIndex);
					            cells[col][lin] = row.createCell((short) col);	
					            
					            try {
					            				            	
					            	if(col == 0 || col == 1)
					            	cells[col][lin].setCellValue(values[auxCol][lin]); 
					            	
					            	else cells[col][lin].setCellValue(Integer.parseInt(values[auxCol][lin]));
					            			          			            
					            }catch(NullPointerException ex) {
					            	ex.printStackTrace();
					           }		            		
					        }
					   
					     if(col == 1)
					    	 auxCol+=iniCol1;
					     
					     else auxCol++;
					    	 
					    // else auxCol=+3; //Coluna começa do indice 0 para percorrer o array
					     
					     }     	   
				       }
				
				//SINGLE FOR DIRECTIONS REPORTS DIR 1
				public void fillDataSingleDirectionsDir2(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow, int iniCol1) {
					 
					int rowLenght = startRow + endRow ;
					int auxCol = 0;
									
					for(int col = colStart; col < maxCol; col++) {
					   for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
						   				 					   
					            row = sheet.getRow((short) rowIndex);
					            cells[col][lin] = row.createCell((short) col);	
					            
					            try {
					            				            	
					            	if(col == 0 || col == 1)
					            	cells[col][lin].setCellValue(values[auxCol][lin]); 
					            	
					            	else cells[col][lin].setCellValue(Integer.parseInt(values[auxCol][lin]));
					            			          			            
					            }catch(NullPointerException ex) {
					            	ex.printStackTrace();
					           }		            		
					        }
					   
					     if(col == 1)
					    	 auxCol+=iniCol1;
					     
					     else auxCol++;
					    						   					     
					     }     	   
				       }
				
				/////////////////////////////////////////
				/// PERIODs
				////////////////////////////////////////
				
				 //Convert Fiels to Integer
				public void fillDataRangeDirections(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow, int day, int periodRange, int inc) {
					 
					int rowLenght = startRow + endRow ;
					int index = 0, auxCol = 0;
						    	  
				    	  for(int col = colStart; col < maxCol; col++) {
							   for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
								   
								   index = lin + (day * periodRange);
									
							            row = sheet.getRow((short) rowIndex);
							            cells[col][index] = row.createCell((short) col);	
							            
							            try {
							            				            	
							            	if(col == 0 || col == 1)
							            		 cells[col][index].setCellValue(values[auxCol][index]);			          			            
							            	
							            	else cells[col][index].setCellValue(Integer.parseInt(values[auxCol][index]));
							            	
							            }catch(NullPointerException ex) {
							            	ex.printStackTrace();
							           }		            		
							        }
							   
							   auxCol++;
														     
							     }		    	  
				              }	
				
				 //Convert Fiels to Integer
				public void fillDataRangeDirectionsDir1(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow, int day, int periodRange, int iniDir1) {
					 
					int rowLenght = startRow + endRow ;
					int index = 0, auxCol = 0;
						    	  
				    	  for(int col = colStart; col < maxCol; col++) {
							   for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
								   
								   index = lin + (day * periodRange);
									
							            row = sheet.getRow((short) rowIndex);
							            cells[col][index] = row.createCell((short) col);	
							            
							            try {
							            				            	
							            	if(col == 0 || col == 1)
							            		 cells[col][index].setCellValue(values[auxCol][index]);			          			            
							            	
							            	else cells[col][index].setCellValue(Integer.parseInt(values[auxCol][index]));
							            	
							            }catch(NullPointerException ex) {
							            	ex.printStackTrace();
							           }		            		
							        }
							   
							   if(col == 1)
								   auxCol += iniDir1;
							   
							   else auxCol++;
														     
							     }		    	  
				              }		
				
				 //Convert Fiels to Integer
				public void fillDataRangeDirectionsDir2(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[][] values, String period, int colStart, int maxCol, int startRow, int endRow, int day, int periodRange, int iniDir2) {
					 
					int rowLenght = startRow + endRow ;
					int index = 0, auxCol = 0;
						    	  
				    	  for(int col = colStart; col < maxCol; col++) {
							   for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
								   
								   index = lin + (day * periodRange);
									
							            row = sheet.getRow((short) rowIndex);
							            cells[col][index] = row.createCell((short) col);	
							            
							            try {
							            				            	
							            	if(col == 0 || col == 1)
							            		 cells[col][index].setCellValue(values[auxCol][index]);			          			            
							            	
							            	else cells[col][index].setCellValue(Integer.parseInt(values[auxCol][index]));
							            	
							            }catch(NullPointerException ex) {
							            	ex.printStackTrace();
							           }		            		
							        }
							   
							   if(col == 1)
								   auxCol += iniDir2;
							   
							   else  auxCol++;
														     
							     }		    	  
				              }		
					
									
				 //Convert Fiels to Integer
				public void fillStringValues(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[] values, int colIndex, int maxCol, int startRow, int endRow) {
					 
					int rowLenght = startRow + endRow ;
						
					for(int col = colIndex; col <= maxCol; col++) {
					   for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
							
						   try {
							   
					            row = sheet.getRow((short) rowIndex);
					            cells[col][lin] = row.createCell((short) col);				           
					            	
					            	cells[col][lin].setCellValue(values[lin]); 
					            	
					            }catch(NullPointerException ex) {
					            	
					           }		            		
					        }		   
					     }		
				      }
				
				 //Convert Fiels to Integer
				public void fillStringDataValues(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[] values, int colIndex, int maxCol, int startRow, int endRow, int dia, int tam) {
					 
					int rowLenght = startRow + endRow ;
					int index = 0;
						
					for(int col = colIndex; col <= maxCol; col++) {
					   for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
						   
						   index = lin + (dia * tam);
							
						   try {
							   
					            row = sheet.getRow((short) rowIndex);
					            cells[col][lin] = row.createCell((short) col);				           
					            	
					            	cells[col][lin].setCellValue(values[index]); 
					            	
					            }catch(NullPointerException ex) {
					            	
					           }		            		
					        }		   
					     }		
				      }
				
				
				 //Convert Fiels to Integer
				public void fillStringInterval(XSSFSheet sheet, XSSFRow row, Cell[][] cells, String[] values, int colStart, int maxCol, int startRow, int endRow, int dia) {
					 
					int rowLenght = startRow + endRow;
					int index = 0;
						
					for(int col = colStart; col <= maxCol; col++) {						
					   for (int rowIndex = startRow, lin = 0; rowIndex <= rowLenght && lin < endRow ; rowIndex++, lin++) {
							
						   index = lin + (dia * 25);
						   
						   try {
							   
					            row = sheet.getRow((short) rowIndex);
					            cells[col][index] = row.createCell((short) col);				           
					            	
					            	cells[col][index].setCellValue(values[index]); 
					            	
					            }catch(NullPointerException ex) {
					            	
					           }		            		
					        }				         
					     }		
				      }
					
		       //Convert Fiels to Integer
				public void fillDataIntervals(XSSFSheet sheet, XSSFRow row, Cell[][] cells, int[][] values, int colStart, int maxCol, int startRow, int endRow, int dia) {
					 
					int rowLenght = startRow + endRow;
					int index = 0, auxCol = 0;
					
					for(int col = colStart; col <= maxCol; col++) {												
					     for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < 24 ; rowIndex++, lin++) {
							 
						        index = lin + (dia * 25);
						   
					            row = sheet.getRow((short) rowIndex);
					            cells[col][index] = row.createCell((short) col);	
					            
					            try {
					            	
					            	cells[col][index].setCellValue(values[auxCol][index]); 					            
					               	            
					            }catch(NullPointerException ex) {
					            	
					           }		            		
					       }					   
					        auxCol++;
					     }		
				     }
				
				
				//Convert Fiels to Integer
				public void fillDataHourInterval(XSSFSheet sheet, XSSFRow row, Cell[][] cells, int[][] values, int colStart, int maxCol, int startRow, int endRow, int dia, int tam) {
					 
					int rowLenght = startRow + endRow;
					int index = 0, auxCol = 0;
					
					for(int col = colStart; col <= maxCol; col++) {												
					     for (int rowIndex = startRow, lin = 0; rowIndex < rowLenght && lin < endRow ; rowIndex++, lin++) {
							 
						        index = lin + (dia * tam);
						   
					            row = sheet.getRow((short) rowIndex);
					            cells[col][index] = row.createCell((short) col);	
					            
					            try {
					            	
					            	cells[col][index].setCellValue(values[auxCol][index]); 					            
					               	            
					            }catch(NullPointerException ex) {
					            	
					           }		            		
					       }					   
					        auxCol++;
					     }		
				     }
			
	
	
   public void createRows(XSSFSheet sheet, XSSFRow row, int initialValue, int endValue) {
			
		   for (int rowIndex = initialValue; rowIndex <= endValue;  rowIndex++) { 				
		            row = sheet.createRow((short) rowIndex);		       		  
		   }
	 }
	
	
	public void createRow(Sheet sheet, Row row, int idx) {		   
			   row = sheet.createRow((short) idx);			  	   
		   } 
	
   
   public void createHeaderCells(Sheet sheet, Row row, int rowNumber, Cell[] cells, String[] value ) {	 
	   
	   for(int i = 0; i < cells.length; i++) {
		   
	    row = sheet.getRow(rowNumber);		
		cells[i] = row.createCell(i);
		cells[i].setCellValue(value[i]);
	   
	   }
   }
   
   public void createCells(Sheet sheet, Row row, int col1, int col2, int lin1, int lin2) {		
		
	   for(int c=col1; c <= col2; c++) {		
	      for (int lin = lin1; lin <= lin2; lin++) { 
		      row = sheet.getRow(lin);		
		        row.createCell(c);
		        
		       // System.out.println(lin);
	    }
	  }
   }
   
   public void styleCells(Sheet sheet, Row row, CellStyle style, int col1, int col2, int lin1, int lin2) {		
		
	   for(int c=col1; c <= col2; c++) {		
	      for (int lin = lin1; lin <= lin2; lin++) {  
		      row = sheet.getRow(lin);		 
		        row.getCell(c).setCellStyle(style);		 	
	     }
	   }
   }
   
	public void createCell(Sheet sheet, Row row, int rowNumber, int cellNumber, String value) {		
		
		row = sheet.getRow(rowNumber);		
		row.createCell(cellNumber);
		row.getCell(cellNumber).setCellValue(value);		
	}
	
	public void createCell(Sheet sheet, Row row, int rowNumber, int cellNumber, int value) {		
		
		row = sheet.getRow(rowNumber);		
		row.createCell(cellNumber);
		row.getCell(cellNumber).setCellValue(value);			
	}

public void createCell(Sheet sheet, Row row, Cell cellName, int rowNumber, int cellNumber, double value) {		
	
	row = sheet.getRow(rowNumber);		
	cellName = row.createCell(cellNumber);
	cellName.setCellValue(value);		
}

public void createCell(Sheet sheet, Row row, Cell cellName, int rowNumber, int cellNumber, boolean value) {		
	
	row = sheet.getRow(rowNumber);		
	cellName = row.createCell(cellNumber);
	cellName.setCellValue(value);		
}

public void createCell(Sheet sheet, Row row, Cell cellName, int rowNumber, int cellNumber, Date value) {		
	
	row = sheet.getRow(rowNumber);		
	cellName = row.createCell(cellNumber);
	cellName.setCellValue(value);		
}

public void createCellWithFormula(Sheet sheet, Row row, int rowNumber, int cellNumber, String formula) {		
	
	row = sheet.getRow(rowNumber);		
	row.createCell(cellNumber);
	row.getCell(cellNumber).setCellFormula(formula);	
}

public void getCell(Sheet sheet, Row row, Cell cellName, int rowNumber, int cellNumber, String value) {		
	
		row = sheet.getRow(rowNumber);		
		cellName = row.getCell(cellNumber);
		cellName.setCellValue(value);		
	}

public void getCell(Sheet sheet, Row row, int rowNumber, int cellNumber, String value) {		
		
		row = sheet.getRow(rowNumber);			
		row.getCell(cellNumber).setCellValue(value);		
	}
	
	public void mergeCells(Sheet sheet, String range) {	
		sheet.addMergedRegion(CellRangeAddress.valueOf(range));		
	}
	
	public void columnsWidth(Sheet sheet, int colIndex, int value) {		
		     sheet.setColumnWidth(colIndex, value);		     
	}
	
	public void applyManyColumnsWidth(Sheet sheet, int colMinIndex, int colMaxIndex, int value) {		
	    
		for(int i = colMinIndex; i <= colMaxIndex; i++)
		       sheet.setColumnWidth(i, value);		     
    }
	
	public Font createFont(XSSFWorkbook workbook, int fontHeight, boolean bold, boolean italic) {
		
		Font font = workbook.createFont();		
		font.setFontName(HSSFFont.FONT_ARIAL); 
		font.setFontHeightInPoints((short) fontHeight);	
		font.setBold(bold);	
		font.setItalic(italic);
				
		return font;
	}
	
	public void addFontColor(Font font, IndexedColors color) {		
		font.setColor(color.getIndex());
	}
	
	public void addStyleVerticalHorizontalAlignment(Workbook workbook, CellStyle style, HorizontalAlignment posX, VerticalAlignment posY) 
	{				
		style.setAlignment(posX);	
		style.setVerticalAlignment(posY);						
	}
	
    public void addStyleHorizontalAlignment(Workbook workbook, CellStyle style, HorizontalAlignment posX) {				
		style.setAlignment(posX);			
	}

public void addStyleVerticalAlignment(Workbook workbook, CellStyle style, VerticalAlignment posY) 
{						
	style.setVerticalAlignment(VerticalAlignment.CENTER); 			
}	

	public CellStyle createStyleTitleHeader(Workbook workbook, Font font) {
	
    CellStyle style = workbook.createCellStyle();
	style.setAlignment(HorizontalAlignment.CENTER);
	style.setVerticalAlignment(VerticalAlignment.CENTER);
	style.setWrapText(true);
	style.setFillBackgroundColor(IndexedColors.WHITE.getIndex());	
	style.setFont(font);
	
	return style;
	
	}	
		
	public CellStyle createStyleHeaderTable(Workbook workbook, Font font) {
		
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setFillBackgroundColor(IndexedColors.WHITE.getIndex());		
		style.setFont(font);		
				
		return style;			
	}	
	
	public CellStyle createStyle(Workbook workbook, Font font, IndexedColors background) {
		
		CellStyle style = workbook.createCellStyle();				
		style.setFont(font);		
		style.setFillBackgroundColor(background.getIndex());
		
		return style;
	}
	
	public void wrapText(CellStyle style, boolean choice) {		
		style.setWrapText(choice);		
	}
	
	
	public void addStyleBackgroundColor(CellStyle style, IndexedColors color) {
		
		style.setFillForegroundColor(color.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	}
	
	public void addRotationStyle(CellStyle style, int rotation) {
		style.setRotation((short) rotation);	
	}
	
	public void applyBorderAllStyle(CellStyle style, BorderStyle type) {
						        		
		style.setBorderTop(type);
		style.setBorderRight(type);
		style.setBorderLeft(type);
		style.setBorderBottom(type);
	}
		
	public void applyBorderTopStyle(CellStyle style, BorderStyle type) {		
		style.setBorderTop(type);		
	}
	
	public void applyBorderRightStyle(CellStyle style, BorderStyle type) {		
		style.setBorderRight(type);	
	}
	
	public void applyBorderLeftStyle(CellStyle style, BorderStyle type) {		
		style.setBorderLeft(type);		
	}
	
	public void applyBorderBottomStyle(CellStyle style, BorderStyle type) {
 		style.setBorderBottom(type);
}
	
	public void InsertExcelImage(Workbook workbook, Sheet sheet, String logo, int col1, int row1, int col2, int row2, 
			int dx1, int dy1, int dx2, int dy2, int resize) throws IOException {
		
		InputStream my_banner_image = new FileInputStream(logo);		
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

		XSSFPicture my_picture = drawing.createPicture(my_anchor, my_picture_id);
		
		my_picture.resize(resize);
		
	}
	
	public void setPointsheight(Sheet sheet, Row row, int rowNumber, int points) {	
		row = sheet.getRow(rowNumber);	
		row.setHeightInPoints((points * sheet.getDefaultRowHeightInPoints()));
	}
	
	public void setStyle(Sheet sheet, Row row, int rowNumber, CellStyle style, int cellNumber) {		
		  
		row = sheet.getRow(rowNumber);		
		row.getCell(cellNumber).setCellStyle(style);
	}	
	
	
	public void setStyle(Sheet sheet, Row row, int rowIni, int rowMax, CellStyle style, int cellIni, int cellMax) {		
		  		
	for(int c = cellIni; c <= cellMax; c++) {
		for(int r= rowIni; r <= rowMax; r++) {		
	     	row = sheet.getRow(r);		
		    row.getCell(c).setCellStyle(style);
		  }
		}
	}	
	
	
	public void setCellStyle(Sheet sheet, Row row, CellStyle style, int startCol, int endCol, int initialValue, int endValue) {		
			
		for(int c = startCol; c <= endCol; c++)	{	
		   for(int r = initialValue; r <= endValue; r++) {
			
			row = sheet.getRow((short) r);		
		    row.getCell(c).setCellStyle(style);
		
		   }
		}
	}
	
	public void setStyleBody(Sheet sheet, Row row, Cell[][] cells, CellStyle style, int startCol, int endCol, int initialValue, int tamanho) {		
		  
		int rowLenght = initialValue + tamanho ;
		
		for(int c= startCol; c <= endCol; c++)	{	
		   for(int l=0, r = initialValue; l < tamanho && r < rowLenght; l++, r++) {
			
			row = sheet.getRow((short) r);		
		    cells[c][l] = row.getCell((short) c);		
		    cells[c][l].setCellStyle(style);
		
		   }
		}
	}
	
	public void setStyleHeaderBody(Sheet sheet, Row row, int rowNumber, Cell[] cells, int length,  CellStyle style) {		
		  
			 for(int i = 0; i < length; i++) {
			   
			    row = sheet.getRow(rowNumber);		
				cells[i] = row.getCell(i);
				cells[i].setCellStyle(style);
			   
			   }
	}	
	
	//Borda para cabeçalho --->>> Não usar para o corpo da tabela
	public void  createHeaderBorder(int row1, int row2, int col1, int col2, BorderStyle style, BorderExtent ext) {
		
		PropertyTemplate prop = new PropertyTemplate();		
		prop.drawBorders(new CellRangeAddress(row1, row2, col1, col2), style, ext);
				
	}
		
	
	public void donwloadExcelFile(XSSFWorkbook workbook, String fileName) throws IOException {
						
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		externalContext.setResponseContentType("application/vnd.ms-excel");
		externalContext.setResponseHeader("Content-Disposition","attachment; filename=\""+fileName+".xlsx\"");

		workbook.write(externalContext.getResponseOutputStream());
		facesContext.responseComplete();
		
		OutputStream responseOutputStream = externalContext.getResponseOutputStream();     
		
		workbook.write(responseOutputStream);
		facesContext.responseComplete();   
		//workbook.close();
	   
		
	}
	
	//Streaming
    public void donwloadExcelFile(SXSSFWorkbook workbook, String fileName) throws IOException {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		externalContext.setResponseContentType("application/vnd.ms-excel");
		externalContext.setResponseHeader("Content-Disposition","attachment; filename=\""+fileName+".xlsx\"");

		OutputStream responseOutputStream = externalContext.getResponseOutputStream();             

		workbook.write(responseOutputStream);
		facesContext.responseComplete();   
		//workbook.close();
	   // workbook.dispose();
		
	}
    
    public void donwloadExcelFile(Workbook workbook, String fileName) throws IOException {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		externalContext.setResponseContentType("application/vnd.ms-excel");
		externalContext.setResponseHeader("Content-Disposition","attachment; filename=\""+fileName+".xlsx\"");

		OutputStream responseOutputStream = externalContext.getResponseOutputStream();     
		
		workbook.write(responseOutputStream);
		facesContext.responseComplete();   
		//workbook.close();   
		
	}
    
    public void totalExcel(Sheet sheet, Row row, String period, int startColumn, int length, int rowIni, int rowMax) {
    	
    	int rowTotal = rowMax + 1;
		int totalStartRow = rowIni + 1;	
		length -= 1;
    	
    	    	    	    	    	
    	startColumn = 2; mergeCells(sheet, "A"+(rowTotal)+":B"+(rowTotal)); 
    	
    	//System.out.println("COL: "+startColumn);
    	//System.out.println("LEH: "+length);
				
		//System.out.println("ST: "+totalStartRow);
		//System.out.println("MX: "+rowMax);
		
		for(int col = startColumn; col <= length; col++) {
			
			String columnLetter = CellReference.convertNumToColString(col);
			
			//System.out.println(columnLetter);
			
			 // for(int r = totalStartRow; r <= rowMax; r++) { 
				
				
				
				//createCellWithFormula(sheet, row, r, totalCol, "SUM("+initialColumnLetter+""+ (r) + ":"+maxColumnLetter+"" + (r) + ")");
				
				createCellWithFormula(sheet, row, rowMax, col, "SUM("+columnLetter+""+ (totalStartRow) + ":"+columnLetter+"" + (rowMax) + ")");	
									
			   // createCellWithFormula(sheet, row, rowMax, totalCol, "SUM("+totalColumnLetter+""+ (totalStartRow) + ":"+totalColumnLetter+"" + (rowMax) + ")");
			
							
		}
    	
    }
    
    //SOBRECARGA DE MÉTODO
    public void totalExcel(Sheet sheet, Row row, int startColumn, int length, int rowIni, int rowMax) {
    	    
		int totalStartRow = rowIni + 1;	
			
		for(int col = startColumn; col <= length; col++) {
			
			String columnLetter = CellReference.convertNumToColString(col);			
						
		   createCellWithFormula(sheet, row, rowMax, col, "SUM("+columnLetter+""+ (totalStartRow) + ":"+columnLetter+"" + (rowMax) + ")");	
		
		}
    	
    }
    
  public void totalExcelIntervals(Sheet sheet, Row row, int[] selectedEquipamento, int startColumn, int length, int ini, int rowIni, int rowMax, int dia) {
    	
    	int col = selectedEquipamento.length;
		int totalCol = col + 1;
					
		String initialColumnLetter = CellReference.convertNumToColString(startColumn);
		String maxColumnLetter = CellReference.convertNumToColString((col));
		String totalColumnLetter = CellReference.convertNumToColString(totalCol);
		
		for(int i = startColumn; i < length; i++) {
			  for(int r = ini; r < rowMax; r++) { 
				 				
				String columnLetter = CellReference.convertNumToColString(i);
				
				createCellWithFormula(sheet, row, r, totalCol, "SUM("+initialColumnLetter+""+ (r+1) + ":"+maxColumnLetter+"" + (r+1) + ")");
				
				createCellWithFormula(sheet, row, rowMax, i, "SUM("+columnLetter+""+ (rowIni) + ":"+columnLetter+"" + (rowMax) + ")");	
									
				createCellWithFormula(sheet, row, rowMax, totalCol, "SUM("+totalColumnLetter+""+ (rowIni) + ":"+totalColumnLetter+"" + (rowMax) + ")");
				
			}				
		}
    	
    }
  
  public void setHeight(Sheet sheet, int row, int height) {	
	  sheet.getRow(row).setHeight((short) height); 	  	  
  }

  
/*
	public void fillDataIntervals(XSSFSheet sheet, XSSFRow row, Cell[][] cells, int[][] values, int colIndex, int maxCol, int startRow, int endRow, int dia) {
		 
		int rowLenght = endRow;
		int index = 0;
		
		for(int col = 0; col < maxCol; col++) {
		   for (int rowIndex = startRow, lin = 0; rowIndex <= rowLenght && lin < 24 ; rowIndex++, lin++) {
				 
			        index = lin + (dia * 25);
			   
		            row = sheet.getRow((short) rowIndex);
		            cells[(col+colIndex)][index] = row.createCell((short) (col+colIndex));	
		            
		            try {
		            	
		            	cells[(col+colIndex)][index].setCellValue(values[col][index]); 
		                 
		                System.out.println("values["+col+"]["+lin+"]: "+values[col][index]);
		          			            
		            }catch(NullPointerException ex) {
		            	
		           }		            		
		       }		   
		    }		
	     }		
  */
  
  
}
