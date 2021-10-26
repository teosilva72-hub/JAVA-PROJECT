package br.com.tracevia.webapp.util;

import java.text.ParseException;

import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.global.ReportBuild;
import br.com.tracevia.webapp.model.global.ReportSelection;

/**
 * Classe para criar métodos para o processamento de dados
 * @author Wellington 18/10/2021
 * @version 1.0
 * @since 1.0
 *
 */
public class ReportUtil {
		
	
	// ----------------------------------------------------------------------------------------------------------------
		
	 public static void initializeDataInterval(String[][] resultQuery, String period, int periodRange, String startDate) {
		 
		 DateTimeApplication dta = new DateTimeApplication(); // DateTimeApplication class
		 
		 // DATAS
		 dta.preencherDataPorPeriodo(resultQuery, 0, ReportBuild.numRegisters,  periodRange, startDate);
		 
		 switch(period) {
		 
		 case "05 minutes": dta.intervalo05Minutos(resultQuery, 1, ReportBuild.numRegisters); break;		 
		 case "06 minutes": dta.intervalo06Minutos(resultQuery, 1, ReportBuild.numRegisters); break;
		 case "10 minutes": dta.intervalo10Minutos(resultQuery, 1, ReportBuild.numRegisters); break;
		 case "15 minutes": dta.intervalo15Minutos(resultQuery, 1, ReportBuild.numRegisters); break;
		 case "30 minutes": dta.intervalo30Min(resultQuery, 1, ReportBuild.numRegisters); break;
		 case "01 hour": dta.preencherHora(resultQuery, 1, ReportBuild.numRegisters);break;
		 case "06 hours": dta.intervalo06Horas(resultQuery, 1, ReportBuild.numRegisters); break;
		 case "24 hours": dta.intervalo24Horas(resultQuery, 1, ReportBuild.numRegisters); break;
		 		 
		 }		 
	 }
			
	// ----------------------------------------------------------------------------------------------------------------
	 
	 // PERIODS
	 public static Integer definePosition(DateTimeApplication dta, String[][] array, String period, String startDate, String data_anterior, int pos, int lin, int periodRange) {
		 
		            // Restrio caso no haja dados nos primeiros registros
					if ((startDate != null) && (!array[lin][0].equals(startDate))) {   // Executa uma unica vez
										
						int iterator = 0;
						
						try {
							
							iterator = dta.daysDifference(startDate, array[lin][0], periodRange);	
							
						} catch (ParseException e) {					
							e.printStackTrace();
						}
									
						pos+= iterator;
						
					
						startDate = null;
						
					} else if (!array[lin][0].equals(data_anterior)) {							
															
						int iterator = 0;
						
						try {
							
							iterator = dta.daysDifference(data_anterior, array[lin][0], periodRange);	
							
						} catch (ParseException e) {					
							e.printStackTrace();
						}
						   								
						pos+= iterator;							
					} 	
					
					return pos;		 
		 		 
	            }	 
	 
	 // ----------------------------------------------------------------------------------------------------------------
	 
	 // 24 HOURS
	 public static Integer definePosition(DateTimeApplication dta, String[][] array, String period, String startDate, String data_anterior, int pos, int lin) {
		 
			// Restrio caso no haja dados nos primeiros registros
			if ((startDate != null) && (!array[lin][0].equals(startDate))) {   // Executa uma unica vez
								
				int iterator = 0;
							
				try {
					
					iterator = (int) dta.daysDifference(startDate, array[lin][0]);
					
				} catch (ParseException e) {					
					e.printStackTrace();
				}
							
				pos+= iterator;
				startDate = null;

			} else if (!array[lin][0].equals(data_anterior)) {							
													
				int iterator = 0;
				
				try {
					
					iterator = (int) dta.daysDifference(data_anterior, array[lin][0]);					
					pos+= iterator;	
					
				} catch (ParseException e) {					
					e.printStackTrace();
				}									
			} 	
			
			return pos;		 
		 
	 }
	 
	 // ----------------------------------------------------------------------------------------------------------------	 
	 // MULTI
	 // ----------------------------------------------------------------------------------------------------------------
	 public static Integer defineMultiPosition(DateTimeApplication dta, String[][] array, String period, String startDate, String endDate, String data_anterior, String equip_anterior, int iterator, int inc, int pos, int lin, int periodRange) {
		 
		            // Restrio caso no haja dados nos primeiros registros
					if ((startDate != null) && (!array[lin][0].equals(startDate))) {   // Executa uma unica vez
														
						try {
							
							iterator = dta.daysDifference(startDate, array[lin][0], periodRange);	
														
							pos += iterator;
							
							data_anterior = array[lin][0];	
							startDate = null;							
							
						} catch (ParseException e) {					
							e.printStackTrace();
						}
									
						pos+= iterator;
						startDate = null;

					} else if (!array[lin][0].equals(data_anterior) && array[lin][2].equals(equip_anterior)) {							
											
						try {
							
							iterator = dta.daysDifference(data_anterior, array[lin][0], periodRange);	
							
							pos += iterator;	   				
								
						  // ADICIONA-SE A DATA ANTERIOR A DATA DO REGISTRO ATUAL
							data_anterior = array[lin][0];
							
						} catch (ParseException e) {					
							e.printStackTrace();
						}										
					} 	
					
					else if(!array[lin][2].equals(equip_anterior)) {
	    					
							 // INCREMENT RANGE
							 inc += periodRange; //ERROR
							 
							 try {
								 
								iterator = dta.daysDifference(data_anterior, endDate, periodRange);
								
							} catch (ParseException e) {								
								e.printStackTrace();
							}	
							 
							 pos+= iterator;
							 							 
							 data_anterior = startDate;
							 equip_anterior = array[lin][2];
							 			    
						}
					
					return pos;		 
		 		 
	            }	 
	 	 
	 // ----------------------------------------------------------------------------------------------------------------
	
	 public static Integer getHour(String[][] array, int lin) {
		 
		  return Integer.parseInt(array[lin][1].substring(0, 2));
	 }
	 
	 // ----------------------------------------------------------------------------------------------------------------
	 
	 public static Integer getMinute(String[][] array, int lin) {
		 
		  return Integer.parseInt(array[lin][1].substring(3, 5));
	 }
	 
	 // ----------------------------------------------------------------------------------------------------------------
	 
	 public static Integer position05Minutes(DateTimeApplication dta, int pos, int hour, int minute)
	 { 
		 int p = dta.index05Minutes(hour, minute);
		 p += pos;
	             
		 return p; 	             
	             
	 }	 
	 
	 // ----------------------------------------------------------------------------------------------------------------
	 
	 public static Integer position06Minutes(DateTimeApplication dta, int pos, int hour, int minute)
	 { 
		 int p = dta.index06Minutes(hour, minute);
		 p += pos;
	             
		 return p; 	             
	             
	 }	 
	 
	 // ----------------------------------------------------------------------------------------------------------------
	
	 public static Integer position10Minutes(DateTimeApplication dta, int pos, int hour, int minute)
	 { 
		 int p = dta.index10Minutes(hour, minute);
		 p += pos;
	             
		 return p; 	             
	             
	 }	  
 
    // ----------------------------------------------------------------------------------------------------------------
		
	 public static Integer position15Minutes(DateTimeApplication dta, int pos, int hour, int minute)
	 { 
		 int p = dta.index15Minutes(hour, minute);
		 p += pos;
	             
		 return p; 	             
	             
	 }	  
 
    // ----------------------------------------------------------------------------------------------------------------
	 
	 public static Integer position30Minutes(DateTimeApplication dta, int pos, int hour, int minute)
	 { 
		 int p = dta.index30Minutes(hour, minute);
		 p += pos;
	             
		 return p; 	             
	             
	 }	  
 
    // ----------------------------------------------------------------------------------------------------------------
	 
	 public static Integer position01Hour(DateTimeApplication dta, int pos, int hour) { return pos + hour; }	             
	 
    // ----------------------------------------------------------------------------------------------------------------

	 public static Integer position06Hours(DateTimeApplication dta, int pos, int hour)
	 { 
		 int p = dta.index06Hours(hour);
		 p += pos;
	             
		 return p; 	             
	             
	 }	  
	 
	 // ----------------------------------------------------------------------------------------------------------------
	
	 /**
	  * Método para resetar valores de formulários nos relatórios
	  * @author Wellington 16/10/2021
	  * @version 1.1
	  * @since version 1.0
	  * @param select - objeto do tipo ReportSelect para receber dados de formulário
	  * @param length - número de campos que possui um relatório
	  * @param isMulti - verificar se é um relatório de multipla seleção de equipamentos		 
	 */
	 public static Integer totalFieldsNumber(ReportSelection select, int length, boolean isMulti) {
		 			 
			int fieldsLength = 0;
			
			if(isMulti)											
				fieldsLength =  ((length - 1) + select.equipments.length);	// REMOVE O CAMPO DE EQUIPAMENTO DEFAULT E ADICIONA EQUIPAMENTOS SELECIONADOS
			
			// else if(isDirection)
			// fieldsLength =  length + ((length - 2) * 3); // REMOVE O CAMPO DATA E INTERVALO E MULTIPLA POR 3 (DIREÇÕES TODOS, SENTIDO 1 E SENTIDO 2)
							
			else fieldsLength = length; // 					
																
			return fieldsLength;	
			
	 }
	 
	 // ----------------------------------------------------------------------------------------------------------------
	 	 
	 public static void executeProcessData(ReportBuild build, DateTimeApplication dta, String[][] array, String[][] resultQuery, 
			 String period, String startDate, int periodRange) {
		 
         switch(period) {
		 
		 case "05 minutes": resultQuery = process05Minutes(build, dta, array, period, startDate, periodRange); break;		 
		 case "06 minutes": resultQuery = process06Minutes(build, dta, array, period, startDate, periodRange);; break;
		 case "10 minutes": resultQuery = process10Minutes(build, dta, array, period, startDate, periodRange);; break;
		 case "15 minutes": resultQuery = process15Minutes(build, dta, array, period, startDate, periodRange);; break;
		 case "30 minutes": resultQuery = process30Minutes(build, dta, array, period, startDate, periodRange);; break;
		 case "01 hour": resultQuery = process01Hour(build, dta, array, period, startDate, periodRange);;break;
		 case "06 hours": resultQuery = process06Hours(build, dta, array, period, startDate, periodRange); break;
		 case "24 hours": resultQuery = process24Hours(build, dta, array, period, startDate); break;
		 		 
		 }	  	 
		 
	 } 
	 
	 // ----------------------------------------------------------------------------------------------------------------
 	 
	 public static void executeMultiProcessData(ReportBuild build, DateTimeApplication dta, String[][] array, String[][] resultQuery, 
			 String period, String startDate, String endDate, String lastDate, String lastEquip, int periodRange, int iterator, int counter, int pos, int inc) {
		 
         switch(period) {
		 
		 case "05 minutes": resultQuery = processMulti05Minutes(build, dta, array, resultQuery, period, startDate, endDate, lastDate, lastEquip, periodRange, iterator, counter, pos, inc); break;		 
		 case "06 minutes": resultQuery = processMulti06Minutes(build, dta, array, resultQuery, period, startDate, endDate, lastDate, lastEquip, periodRange, iterator,counter, pos, inc);; break;
		 case "10 minutes": resultQuery = processMulti10Minutes(build, dta, array, resultQuery, period, startDate, endDate, lastDate, lastEquip, periodRange, iterator,counter, pos, inc);; break;
		 case "15 minutes": resultQuery = processMulti15Minutes(build, dta, array, resultQuery, period, startDate, endDate, lastDate, lastEquip, periodRange, iterator, counter, pos, inc);; break;
		 case "30 minutes": resultQuery = processMulti30Minutes(build, dta, array, resultQuery, period, startDate, endDate, lastDate, lastEquip, periodRange, iterator, counter, pos, inc);; break;
		 case "01 hour": resultQuery = processMulti01Hour(build, dta, array, resultQuery, period, startDate, endDate, lastDate, lastEquip, periodRange, iterator, counter, pos, inc);;break;
		 case "06 hours": resultQuery = processMulti06Hours(build, dta, array, resultQuery, period, startDate, endDate, lastDate, lastEquip, periodRange, iterator, counter, pos, inc); break;
		 case "24 hours": resultQuery = processMulti24Hours(build, dta, array,  resultQuery, period, startDate, endDate, lastDate, lastEquip, periodRange, iterator, counter, pos, inc); break;
		 		 
		 }	  	 
		 
	 } 
	 
	 // ----------------------------------------------------------------------------------------------------------------	 
	 // ----------------------------------------------------------------------------------------------------------------
	 // PROCESS DATA MODELS
	 // ----------------------------------------------------------------------------------------------------------------
	 
	 public static String[][] process05Minutes(ReportBuild build, DateTimeApplication dta, String[][] array, 
			 String period, String startDate, int periodRange) {
 					
			String last_date = startDate;
			
			String[][] result = new String[ReportBuild.numRegisters][ReportBuild.fieldsNumber];
						
			int pos = 0; 	
			int p = 0;
			int hr = 0;
			int m = 0;
			int lin = 0;
			int col = 0;
							
			lin = array.length;
			col = array[0].length;
			
			for(int j = 0; j < lin; j++) {
			   for(int i = 0; i < col; i++) {		
				  
				    hr = getHour(array, j);
				   
				    m = getMinute(array, j);
								
					p = definePosition(dta, array, period, startDate, last_date, pos, j, periodRange);
					
					p = position05Minutes(dta, p, hr, m);
															
					if(i > 1)
					    result[p][i] = array[j][i];				
					
					 }	
			      }
			
			    return result;
			}	    
	     
	 // ----------------------------------------------------------------------------------------------------------------
	 
	 public static String[][] process06Minutes(ReportBuild build, DateTimeApplication dta, String[][] array,
			 String period, String startDate, int periodRange) {
 					
			String last_date = startDate;
			
			String[][] result = new String[ReportBuild.numRegisters][ReportBuild.fieldsNumber];
			
			int pos = 0; 	
			int p = 0;
			int hr = 0;
			int m = 0;
			int lin = 0;
			int col = 0;
											
			lin = array.length;
			col = array[0].length;
			
			for(int j = 0; j < lin; j++) {
			   for(int i = 0; i < col; i++) {		
				  
				    hr = getHour(array, j);
				   
				    m = getMinute(array, j);
								
					p = definePosition(dta, array, period, startDate, last_date, pos, j, periodRange);
					
					p = position06Minutes(dta, p, hr, m);
															
					if(i > 1)
					    result[p][i] = array[j][i];				
					
					 }	
			      }
			
			    return result;
			}	    
	     
	 // ----------------------------------------------------------------------------------------------------------------
	 
	 public static String[][] process10Minutes(ReportBuild build, DateTimeApplication dta, String[][] array,
			 String period, String startDate, int periodRange) {
 					
			String last_date = startDate;
			
			String[][] result = new String[ReportBuild.numRegisters][ReportBuild.fieldsNumber];
			
			int pos = 0; 	
			int p = 0;
			int hr = 0;
			int m = 0;
			int lin = 0;
			int col = 0;
										
			lin = array.length;
			col = array[0].length;
			
			for(int j = 0; j < lin; j++) {
			   for(int i = 0; i < col; i++) {		
				  
				    hr = getHour(array, j);
				   
				    m = getMinute(array, j);
								
					p = definePosition(dta, array, period, startDate, last_date, pos, j, periodRange);
					
					p = position10Minutes(dta, p, hr, m);
															
					if(i > 1)
					    result[p][i] = array[j][i];				
					
					 }	
			      }
			
			    return result;
			}	    
	     
	 // ----------------------------------------------------------------------------------------------------------------
	 
	 public static String[][] process15Minutes(ReportBuild build, DateTimeApplication dta, String[][] array, 
			 String period, String startDate, int periodRange) {
 					
			String last_date = startDate;
			
			String[][] result = new String[ReportBuild.numRegisters][ReportBuild.fieldsNumber];			
			
			int pos = 0; 	
			int p = 0;
			int hr = 0;
			int m = 0;
			int lin = 0;
			int col = 0;
										
			lin = array.length;
			col = array[0].length;
			
			for(int j = 0; j < lin; j++) {
			   for(int i = 0; i < col; i++) {		
				  
				    hr = getHour(array, j);
				   
				    m = getMinute(array, j);
								
					p = definePosition(dta, array, period, startDate, last_date, pos, j, periodRange);
					
					p = position15Minutes(dta, p, hr, m);
										
					if(i > 1)
					    result[p][i] = array[j][i];				
					
					 }	
			      }
			
			    return result;
			}	    
	     
	 // ----------------------------------------------------------------------------------------------------------------
	 
	 public static String[][] process30Minutes(ReportBuild build, DateTimeApplication dta, String[][] array,
			 String period, String startDate, int periodRange) {
 					
			String last_date = startDate;
			
			String[][] result = new String[ReportBuild.numRegisters][ReportBuild.fieldsNumber];
			
			int pos = 0; 	
			int p = 0;
			int hr = 0;
			int m = 0;
			int lin = 0;
			int col = 0;
											
			lin = array.length;
			col = array[0].length;
			
			for(int j = 0; j < lin; j++) {
			   for(int i = 0; i < col; i++) {		
				  
				    hr = getHour(array, j);
				   
				    m = getMinute(array, j);
								
					p = definePosition(dta, array, period, startDate, last_date, pos, j, periodRange);
					
					p = position05Minutes(dta, p, hr, m);
															
					if(i > 1)
					    result[p][i] = array[j][i];				
					
					 }	
			      }
			
			    return result;
			}	    
	     
	 // ----------------------------------------------------------------------------------------------------------------
	 
	 public static String[][] process01Hour(ReportBuild build, DateTimeApplication dta, String[][] array,
			 String period, String startDate, int periodRange) {
 					
			String last_date = startDate;
			
			String[][] result = new String[ReportBuild.numRegisters][ReportBuild.fieldsNumber];
			
			int pos = 0; 	
			int p = 0;
			int hr = 0;		
			int lin = 0;
			int col = 0;
								
			lin = array.length;
			col = array[0].length;
			
			for(int j = 0; j < lin; j++) {
			   for(int i = 0; i < col; i++) {		
				  
				    hr = getHour(array, j);
				   								
					p = definePosition(dta, array, period, startDate, last_date, pos, j, periodRange);
					
					p = position01Hour(dta, p, hr);
															
					if(i > 1)
					    result[p][i] = array[j][i];				
					
					 }	
			      }
			
			    return result;
			}	    
	     
	 // ----------------------------------------------------------------------------------------------------------------
	 
	 public static String[][] process06Hours(ReportBuild build, DateTimeApplication dta, String[][] array,
			 String period, String startDate, int periodRange) {
 					
			String last_date = startDate;
			
			String[][] result = new String[ReportBuild.numRegisters][ReportBuild.fieldsNumber];
			
			int pos = 0; 	
			int p = 0;
			int hr = 0;		
			int lin = 0;
			int col = 0;
											
			lin = array.length;
			col = array[0].length;
			
			for(int j = 0; j < lin; j++) {
			   for(int i = 0; i < col; i++) {		
				  
				    hr = getHour(array, j);
				   								
					p = definePosition(dta, array, period, startDate, last_date, pos, j, periodRange);
					
					p = position06Hours(dta, p, hr);
															
					if(i > 1)
					    result[p][i] = array[j][i];				
					
					 }	
			      }
			
			    return result;
			}	    
	     
	 // ----------------------------------------------------------------------------------------------------------------
	 
	 public static String[][] process24Hours(ReportBuild build, DateTimeApplication dta, String[][] array, 
			 String period, String startDate) {
 					
			String last_date = startDate;
			
			String[][] result = new String[ReportBuild.numRegisters][ReportBuild.fieldsNumber];
			
			int pos = 0; 		
			int lin = 0;
			int col = 0;
			int p = 0;
													
			lin = array.length;
			col = array[0].length;
			
			for(int j = 0; j < lin; j++) {
			  for(int i = 0; i < col; i++) {					   			
								
					p = definePosition(dta, array, period, startDate, last_date, pos, j);
															
					if(i > 1)
					    result[p][i] = array[j][i];				
					
					 }	
			      }
			
			     return result;
			}	
	 
	 // ----------------------------------------------------------------------------------------------------------------
	 // PROCESS MULTI DATA MODELS
	 // ----------------------------------------------------------------------------------------------------------------
	 
	 public static String[][] processMulti05Minutes(ReportBuild build, DateTimeApplication dta, String[][] array, String[][] result,
			 String period, String startDate, String endDate, String lastDate, String lastEquip, int iterator, int periodRange, int counter, int pos, int inc) {
 						
			int p = 0;
			int hr = 0;
			int m = 0;
			int lin = 0;
			int col = 0;
							
			lin = counter;
			col = array[0].length;
			
			for(int j = 0; j < lin; j++) {
			   for(int i = 0; i < col; i++) {		
				  
				    hr = getHour(array, j);
				   
				    m = getMinute(array, j);
								
				    p = defineMultiPosition(dta, array, period, startDate, endDate, lastDate, lastEquip, iterator, inc, pos, j, periodRange);
					
					p = position05Minutes(dta, p, hr, m);
					
					p += pos + inc;
										
					if(i > 1)
					    result[p][i] = array[j][i];					
					 }	
			      }
			
			    return result;
			}	    
	     
	 // ----------------------------------------------------------------------------------------------------------------
	 
	 public static String[][] processMulti06Minutes(ReportBuild build, DateTimeApplication dta, String[][] array, String[][] result,
			 String period, String startDate, String endDate, String lastDate, String lastEquip, int iterator, int periodRange, int counter, int pos, int inc) {
 					
			String last_date = startDate;
										
			int p = 0;
			int hr = 0;
			int m = 0;
			int lin = 0;
			int col = 0;
											
			lin = counter;
			col = array[0].length;
			
			for(int j = 0; j < lin; j++) {
			   for(int i = 0; i < col; i++) {		
				  
				    hr = getHour(array, j);
				   
				    m = getMinute(array, j);
								
				    p = defineMultiPosition(dta, array, period, startDate, endDate, lastDate, lastEquip, iterator, inc, pos, j, periodRange);
					
					p = position06Minutes(dta, p, hr, m);
					
					p += pos + inc;
										
					if(i > 1)
					    result[p][i] = array[j][i];				
					
					 }	
			      }		   
			
			    return result;
			}	    
	     
	 // ----------------------------------------------------------------------------------------------------------------
	 
	 public static String[][] processMulti10Minutes(ReportBuild build, DateTimeApplication dta, String[][] array, String[][] result,
			 String period, String startDate, String endDate, String lastDate, String lastEquip, int iterator, int periodRange, int counter, int pos, int inc) {
 													
			int p = 0;
			int hr = 0;
			int m = 0;
			int lin = 0;
			int col = 0;
										
			lin = counter;
			col = array[0].length;
			
			for(int j = 0; j < lin; j++) {
			   for(int i = 0; i < col; i++) {		
				  
				    hr = getHour(array, j);
				   
				    m = getMinute(array, j);
								
				    p = defineMultiPosition(dta, array, period, startDate, endDate, lastDate, lastEquip, iterator, inc, pos, j, periodRange);
					
					p = position10Minutes(dta, p, hr, m);
					
					p += pos + inc;
										
					if(i > 1)
					    result[p][i] = array[j][i];				
					
					 }	
			      }
			
			    return result;
			}	    
	     
	 // ----------------------------------------------------------------------------------------------------------------
	 
	 public static String[][] processMulti15Minutes(ReportBuild build, DateTimeApplication dta, String[][] array, String[][] result,
			 String period, String startDate, String endDate, String lastDate, String lastEquip, int iterator, int periodRange, int counter, int pos, int inc) {
 											
			int p = 0;
			int hr = 0;
			int m = 0;
			int lin = 0;
			int col = 0;
										
			lin = counter;
			col = array[0].length;
			
			for(int j = 0; j < lin; j++) {
			   for(int i = 0; i < col; i++) {		
				  
				    hr = getHour(array, j);
				   
				    m = getMinute(array, j);
								
				    p = defineMultiPosition(dta, array, period, startDate, endDate, lastDate, lastEquip, iterator, inc, pos, j, periodRange);
					
					p = position15Minutes(dta, p, hr, m);
					
					p += pos + inc;
										
					if(i > 1)
					    result[p][i] = array[j][i];				
					
					 }	
			      }
			
			    return result;
			}	    
	     
	 // ----------------------------------------------------------------------------------------------------------------
	 
	 public static String[][] processMulti30Minutes(ReportBuild build, DateTimeApplication dta, String[][] array, String[][] result,
			 String period, String startDate, String endDate, String lastDate, String lastEquip, int iterator, int periodRange, int counter, int pos, int inc) {
 												
			int p = 0;
			int hr = 0;
			int m = 0;
			int lin = 0;
			int col = 0;
											
			lin = counter;
			col = array[0].length;
			
			for(int j = 0; j < lin; j++) {
			   for(int i = 0; i < col; i++) {		
				  
				    hr = getHour(array, j);
				   
				    m = getMinute(array, j);
								
				    p = defineMultiPosition(dta, array, period, startDate, endDate, lastDate, lastEquip, iterator, inc, pos, j, periodRange);
					
					p = position05Minutes(dta, p, hr, m);
					
					p += pos + inc;
										
					if(i > 1)
					    result[p][i] = array[j][i];				
					
					 }	
			      }
			
			    return result;
			}	    
	     
	 // ----------------------------------------------------------------------------------------------------------------
	 
	 public static String[][] processMulti01Hour(ReportBuild build, DateTimeApplication dta, String[][] array, String[][] result,
			 String period, String startDate, String endDate, String lastDate, String lastEquip, int iterator, int periodRange, int counter, int pos, int inc) {
 								
			int p = 0;
			int hr = 0;		
			int lin = 0;
			int col = 0;
								
			lin = counter;
			col = array[0].length;
			
			for(int j = 0; j < lin; j++) {
			   for(int i = 0; i < col; i++) {		
				  
				    hr = getHour(array, j);
				   								
				    p = defineMultiPosition(dta, array, period, startDate, endDate, lastDate, lastEquip, iterator, inc, pos, j, periodRange);
					
					p = position01Hour(dta, p, hr);
					
					p += pos + inc;
										
					if(i > 1)
					    result[p][i] = array[j][i];				
					
					 }	
			      }
			
			    return result;
			}	    
	     
	 // ----------------------------------------------------------------------------------------------------------------
	 
	 public static String[][] processMulti06Hours(ReportBuild build, DateTimeApplication dta, String[][] array, String[][] result,
			 String period, String startDate, String endDate, String lastDate, String lastEquip, int iterator, int periodRange, int counter, int pos, int inc) {
 											
			int p = 0;
			int hr = 0;		
			int lin = 0;
			int col = 0;
											
			lin = counter;
			col = array[0].length;
			
			for(int j = 0; j < lin; j++) {
			   for(int i = 0; i < col; i++) {		
				  
				    hr = getHour(array, j);
				   								
					p = defineMultiPosition(dta, array, period, startDate, endDate, lastDate, lastEquip, iterator, inc, pos, j, periodRange);
					
					p = position06Hours(dta, p, hr);
					
					p += pos + inc;
										
					if(i > 1)
					    result[p][i] = array[j][i];				
					
					 }	
			   
			        p++;
			        
			      }
			
			    return result;
			}	    
	     
	 // ----------------------------------------------------------------------------------------------------------------
	 
	 public static String[][] processMulti24Hours(ReportBuild build, DateTimeApplication dta, String[][] array, String[][] result,
			 String period, String startDate, String endDate, String lastDate, String lastEquip, int periodRange, int iterator, int counter, int pos, int inc) {
 					
			int lin = 0;
			int col = 0;
			int p = 0;
													
			lin = counter;
			col = array[0].length;
			
			for(int j = 0; j < lin; j++) {
			  for(int i = 0; i < col; i++) {					   			
								
				  p = defineMultiPosition(dta, array, period, startDate, endDate, lastDate, lastEquip, iterator, inc, pos, j, periodRange);
					
					p += pos + inc;
					
					if(i > 1)
					    result[p][i] = array[j][i];				
					
					 }
			      }
			
			     return result;
			}
	     
	     
	 // ----------------------------------------------------------------------------------------------------------------
	 
}
