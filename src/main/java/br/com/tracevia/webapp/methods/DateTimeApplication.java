package br.com.tracevia.webapp.methods;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class DateTimeApplication {
	
	public final static String DATE_TIME_FORMAT_STANDARD_DATABASE = "yyyy-MM-dd HH:mm:ss";
	public final static String DATE_TIME_FORMAT_HOUR_MINUTE_DATABASE = "yyyy-MM-dd HH:mm";
	public final static String DATE_TIME_FORMAT_DATE_DATABASE = "yyyy-MM-dd";
			
	public final static String DATE_TIME_FORMAT_STANDARD_VIEW = "dd/MM/yyyy HH:mm:ss";
	public final static String DATE_TIME_FORMAT_HOUR_MINUTE_VIEW = "dd/MM/yyyy HH:mm";	
	public final static String DATE_TIME_FORMAT_DATE_VIEW = "dd/MM/yyyy";
	
	public final static String DATE_TIME_FORMAT_DATE_VIEW_START = "yyyy-MM-dd 00:00:00";	
	public final static String DATE_TIME_FORMAT_DATE_VIEW_END = "yyyy-MM-dd 23:59:59";
	
	public final static String HOUR_MINUTE_FORMAT = "HH:mm";
	
	public final static String HOUR_TIME_FORMAT_START_DATE = " 00:00:00";	
	public final static String HOUR_TIME_FORMAT_END_DATE = " 23:59:59";
	
	public final static String DATE_TIME_FORMAT_EXCEL_SHEET_NAME = "dd-MM-yyyy";
		
	private static SimpleDateFormat formatter;	
	private static DateFormat dtFormatter;
					
	/**
	 * Método para obter a data e hora atual do sistema	 
	 * @autor Wellington - 2020-07-21	 
	 * @param dateFormat - Formato para data - Ex.: (dd/MM/yyyy HH:mm:ss)
	 * @return data formatada
	 */
	public String currentStringDate(String dateFormat) {
		
		Date date = new Date();  //Obter data e hora atual 
		
		formatter = new SimpleDateFormat(dateFormat);
		String dtime = formatter.format(date);

		return dtime;
	}
	
	/*Limitar Dias em buscas*/
	public boolean limitSearch(String startDate, String endDate, int limitDays) throws ParseException {
		
		int days = 0;
		boolean response = false;
											
		days = (int) diferencaDias(startDate, endDate);
						
		    if(days > limitDays)							
			response = true;						
						
		return response;
						
	}
	
	/*Calcular Diferença entre dias */
	public long diferencaDias(String inicio, String fim) throws ParseException {
		
		dtFormatter =  new SimpleDateFormat (DATE_TIME_FORMAT_DATE_VIEW);			
		
        Date dtInicial = dtFormatter.parse (inicio);
        Date dtFinal = dtFormatter.parse (fim);
                
           return (dtFinal.getTime() - dtInicial.getTime() + 3600000L) / 86400000L;
    }
	
	//FORMAT A STRING DATE
	public String StringDBDateFormat(String data) throws ParseException {	
		
	formatter = new SimpleDateFormat(DATE_TIME_FORMAT_DATE_VIEW);	
	Date date = formatter.parse(data);
	formatter.applyPattern(DATE_TIME_FORMAT_DATE_DATABASE);
	String dataFormatada = formatter.format(date);
	
	return dataFormatada;	
	}
	
	//FORMAT A STRING DATE
		public String StringDateFormat(String data) throws ParseException {	
			
		formatter = new SimpleDateFormat(DATE_TIME_FORMAT_DATE_DATABASE);	
		Date date = formatter.parse(data);
		formatter.applyPattern(DATE_TIME_FORMAT_DATE_VIEW);
		String dataFormatada = formatter.format(date);
		
		return dataFormatada;	
		}
	
	

	//DATETIME TO STRING
	public String DateTimeToString(Date data, String format) {
		
		dtFormatter = new SimpleDateFormat(format);
		String date = dtFormatter.format(data);

		return date;
	}
	
	//Esperar x time para executar (Timer thread)	
	public void waitSecs(int time) {
		   
	    try {
	    	
	        Thread.sleep(time); //paraliza por x segundos a thread atual
	        
	       } catch (InterruptedException ex) {
	        
	     }
	  }   
	
	/**
	 * 
	 * @param calendar - Instancia Calendar
	 * @param minute - minuto atual
	 * @param currentDate - Variavél para ser preenchida com data atual
	 * @param currentDateSub - Variavél para ser preenchida com data atual com um minuto e um segundo a menos
	 */
	public String getCurrentDateDados15(Calendar calendar, int minute) {
	
	dtFormatter = new SimpleDateFormat(DATE_TIME_FORMAT_STANDARD_DATABASE);
	
	String currentDate = null;
		
     //formatar os minutos para que nao ocorra inconsistencias
	if(minute >= 0 && minute < 15)
		 calendar.set(Calendar.MINUTE, 0);
	
	else if(minute >= 15 && minute < 30)
		 calendar.set(Calendar.MINUTE, 15);
	
	else if(minute >= 30 && minute < 45)
		 calendar.set(Calendar.MINUTE, 30);
	
	else calendar.set(Calendar.MINUTE, 45);
	
	calendar.set(Calendar.SECOND, 0);
	     
	//Data Atual
	currentDate = dtFormatter.format(calendar.getTime());
   
   return currentDate;
   
	}
	
	public String getCurrentDateSubDados15(Calendar calendar, int minute) {
		
		dtFormatter = new SimpleDateFormat(DATE_TIME_FORMAT_STANDARD_DATABASE);
		
		String currentDateSub = null;
			
	     //formatar os minutos para que nao ocorra inconsistencias
		if(minute >= 0 && minute < 15)
			 calendar.set(Calendar.MINUTE, 0);
		
		else if(minute >= 15 && minute < 30)
			 calendar.set(Calendar.MINUTE, 15);
		
		else if(minute >= 30 && minute < 45)
			 calendar.set(Calendar.MINUTE, 30);
		
		else calendar.set(Calendar.MINUTE, 45);
						
		calendar.add(Calendar.MINUTE, -1);				
		calendar.add(Calendar.SECOND, 59);	

	   //Data Atual com subtracao de minuto e segundo
	   currentDateSub = dtFormatter.format(calendar.getTime());	
	   
	   return currentDateSub;
	   
		}
	
	
	public Integer periodsRange(String period) {
		
		int range = 0;
		
		if(period.equals("05 minutes"))
			range = 288;

		if(period.equals("06 minutes"))
			range = 240;

		if(period.equals("10 minutes"))
			range = 144;

		if(period.equals("15 minutes"))
			range = 96;

		if(period.equals("30 minutes"))
			range = 48;

		if(period.equals("01 hour"))
			range = 24;
		
		if(period.equals("06 hours"))
			range = 4;
		
		if(period.equals("24 hours"))
			range = 1;
				
		return range;			
		
	}
   
	
	 public int RegistersNumbers(String startDate, String endDate, String period) {
	    		    	
	    	int days = 0, range = 0;
	    	
	    	range = periodsRange(period);
	    	
	    	try {
	    		
				days = ((int) diferencaDias(startDate, endDate) + 1);			
				
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
	    	
	    	
	    	return (days * range);
	    }
	 
	 
	 public String CreateDate(int dia, int mes, int ano) throws ParseException {
		   
		   String data = ano+"-"+mes+"-"+dia;
		   
		   SimpleDateFormat formato = new SimpleDateFormat(DATE_TIME_FORMAT_DATE_DATABASE);
			Date date = formato.parse(data);	   
			String dataCriada = formato.format(date);
			
			return dataCriada;		   
	}	
	 
	 
	//Retorna DateTime atual do Sistema
		public String currentTime() {
			
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT_STANDARD_VIEW);
			return sdf.format(new Date(System.currentTimeMillis()));
			
		}
		
		//Retorna DateTime atual do Sistema
				public String currentTimeDBformat() {
					
					SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT_STANDARD_DATABASE);
					return sdf.format(new Date(System.currentTimeMillis()));
					
				}
		
		
		public String[] dateRangeForHeader(String startDate, String endDate, int days) throws Exception {
			
		  String[] dateInterval = new String[days];
		  int inc = 0;
			
		  SimpleDateFormat fmt = new SimpleDateFormat(DATE_TIME_FORMAT_DATE_VIEW); 		 	
		  Date dt1 = fmt.parse (startDate);
		  Date dt2 = fmt.parse (endDate);
		  
		  Calendar cal1 = Calendar.getInstance();
		        
		  cal1.setTime (dt1);
		  Calendar cal2 = Calendar.getInstance();
		  cal2.setTime (dt2);
		        
		  for (Calendar cal = cal1; cal.compareTo (cal2) <= 0; cal.add (Calendar.DATE, 1)) {
		          System.out.println (fmt.format (cal.getTime()));
		          dateInterval[inc] = fmt.format (cal.getTime());
		            
		            inc++;
		        }
		
		  return dateInterval;			
			
		}
		
		public String[] dateRangeForSheetName(String startDate, String endDate, int days) throws ParseException {
			
			  String[] dateInterval = new String[days];
			  int inc = 0;
						  
			  formatter = new SimpleDateFormat(DATE_TIME_FORMAT_DATE_VIEW);									 
			  Date dt1 = formatter.parse (startDate);
			  Date dt2 = formatter.parse (endDate);
			  			  
			  Calendar cal1 = Calendar.getInstance();
			        
			  cal1.setTime (dt1);
			  Calendar cal2 = Calendar.getInstance();
			  cal2.setTime (dt2);
			  
			  formatter.applyPattern(DATE_TIME_FORMAT_EXCEL_SHEET_NAME);
			        
			  for (Calendar cal = cal1; cal.compareTo (cal2) <= 0; cal.add (Calendar.DATE, 1)) {
			            System.out.println (formatter.format (cal.getTime()));
			            dateInterval[inc] = formatter.format (cal.getTime());
			            
			            inc++;
			        }
			
			  return dateInterval;			
				
			}
		
		  // -------- 2020-12-01 --------- //
		
		 //JAVA 8 
		//DATETIME SYSTEM		
		public LocalDate localeDate() {			
			return LocalDate.now();
		}
						
   }
