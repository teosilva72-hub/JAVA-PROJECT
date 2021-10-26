package br.com.tracevia.webapp.methods;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.tracevia.webapp.model.global.Equipments;

public class DateTimeApplication {

	public final static String DATE_TIME_FORMAT_STANDARD_DATABASE = "yyyy-MM-dd HH:mm:ss";
	public final static String DATE_TIME_FORMAT_STANDARD_DATABASE_MILLIS = "yyyy-MM-dd HH:mm:ss.SSS";
	public final static String DATE_TIME_FORMAT_HOUR_MINUTE_DATABASE = "yyyy-MM-dd HH:mm";
	public final static String DATE_TIME_FORMAT_DATE_DATABASE = "yyyy-MM-dd";
	public final static String DATE_TIME_FORMAT_DATE_FILE = "yyyy.MM.dd";

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
	 * M�todo para obter a data e hora atual do sistema	 
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

	/*Calcular Diferen�a entre dias */
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
	
	// ------------------------------------------------------------------------------------
	
	
	//FORMAT A STRING DATE
	public String StringDBDateFormatInitial(String data) throws ParseException {	

			formatter = new SimpleDateFormat(DATE_TIME_FORMAT_DATE_VIEW);	
			Date date = formatter.parse(data);
			formatter.applyPattern(DATE_TIME_FORMAT_DATE_VIEW_START);
			String dataFormatada = formatter.format(date);

			return dataFormatada;	
		}
	
	// -------------------------------------------------------------------------------------
	
	//FORMAT A STRING DATE
		public String StringDBDateFormatEnd(String data) throws ParseException {	

				formatter = new SimpleDateFormat(DATE_TIME_FORMAT_DATE_VIEW);	
				Date date = formatter.parse(data);
				formatter.applyPattern(DATE_TIME_FORMAT_DATE_VIEW_END);
				String dataFormatada = formatter.format(date);

				return dataFormatada;	
	}
		
	// -------------------------------------------------------------------------------------

	//FORMATTER FOR DATETIME - DATABASE TO VIEW
	public String formatterDateTime(String datetime) {

		DateTimeFormatter databasefmt = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_STANDARD_DATABASE);			 
		DateTimeFormatter viewfmt = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_STANDARD_VIEW);

		LocalDateTime myDateObj = LocalDateTime.parse(datetime, databasefmt);			  

		String formattedDate = myDateObj.format(viewfmt);

		return  formattedDate;
	}

	//FORMATTER FOR DATETIME - DATABASE TO VIEW


	//FORMATTER FOR DATETIME - DATABASE TO VIEW
	public String currentDateTimeMillis() {

		DateTimeFormatter databasefmt = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_STANDARD_DATABASE_MILLIS);			 

		String currentDate = LocalDateTime.now().format(databasefmt);

		return  currentDate;
	}
	
	//FORMATTER FOR DATETIME - DATABASE TO VIEW
	public String currentDateTime() {

		DateTimeFormatter databasefmt = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_STANDARD_DATABASE);			 

		String currentDate = LocalDateTime.now().format(databasefmt);

		return  currentDate;
	}


	//FORMATTER FOR DATE - DATABASE FORMAT
	public String currentDateToFile() {

		DateTimeFormatter databasefmt = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_DATE_FILE);			 

		String currentDate = LocalDateTime.now().format(databasefmt);

		return  currentDate;
	}

	//FORMATTER FOR DATETIME - DATABASE TO VIEW

	//FORMATTER FOR DATETIME - DATABASE TO VIEW
	public String currentViewDateTime() {

		DateTimeFormatter databasefmt = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_STANDARD_VIEW);			 

		String currentDate = LocalDateTime.now().format(databasefmt);

		return  currentDate;
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
	 * @param currentDate - Variav�l para ser preenchida com data atual
	 * @param currentDateSub - Variav�l para ser preenchida com data atual com um minuto e um segundo a menos
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


	/**
	 * 
	 * @param calendar - Instancia Calendar
	 * @param minute - minuto atual
	 * @param currentDate - Variav�l para ser preenchida com data atual
	 * @param currentDateSub - Variav�l para ser preenchida com data atual com um minuto e um segundo a menos
	 */
	public String getCurrentDateDados15CCR(Calendar calendar, int minute) {

		dtFormatter = new SimpleDateFormat(DATE_TIME_FORMAT_STANDARD_DATABASE);

		String currentDate = null;

		//formatar os minutos para que nao ocorra inconsistencias
		if(minute >= 0 && minute < 15) {

			calendar.set(Calendar.MINUTE, 45);
			calendar.add(Calendar.HOUR_OF_DAY, -1);

		}else if(minute >= 15 && minute < 30)		
			calendar.set(Calendar.MINUTE, 0);	   

		else if(minute >= 30 && minute < 45)
			calendar.set(Calendar.MINUTE, 15);

		else if(minute >= 45 && minute <= 59)			
			calendar.set(Calendar.MINUTE, 30);

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


	/**
	 * 
	 * @param calendar - Instancia Calendar
	 * @param minute - minuto atual
	 * @param currentDate - Variav�l para ser preenchida com data atual
	 * @param currentDateSub - Variav�l para ser preenchida com data atual com um minuto e um segundo a menos
	 */
	public String getCurrentDateDados30(Calendar calendar, int minute) {

		dtFormatter = new SimpleDateFormat(DATE_TIME_FORMAT_STANDARD_DATABASE);

		String currentDate = null;

		//formatar os minutos para que nao ocorra inconsistencias
		if(minute >= 0 && minute < 15)
			calendar.set(Calendar.MINUTE, 45);

		else if(minute >= 15 && minute < 30)
			calendar.set(Calendar.MINUTE, 0);

		else if(minute >= 30 && minute < 45)
			calendar.set(Calendar.MINUTE, 15);

		else calendar.set(Calendar.MINUTE, 30);

		calendar.set(Calendar.SECOND, 0);

		//Data Atual
		currentDate = dtFormatter.format(calendar.getTime());

		return currentDate;

	}

	public String getCurrentDateSubDados30(Calendar calendar, int minute) {

		dtFormatter = new SimpleDateFormat(DATE_TIME_FORMAT_STANDARD_DATABASE);

		String currentDateSub = null;

		//formatar os minutos para que nao ocorra inconsistencias
		if(minute >= 0 && minute < 15)
			calendar.set(Calendar.MINUTE, 45);

		else if(minute >= 15 && minute < 30)
			calendar.set(Calendar.MINUTE, 0);

		else if(minute >= 30 && minute < 45)
			calendar.set(Calendar.MINUTE, 15);

		else calendar.set(Calendar.MINUTE, 30);

		calendar.add(Calendar.MINUTE, -1);				
		calendar.add(Calendar.SECOND, 59);	

		//Data Atual com subtracao de minuto e segundo
		currentDateSub = dtFormatter.format(calendar.getTime());	

		return currentDateSub;

	}

	public String getCurrentDateSubDados45(Calendar calendar, int minute) {

		dtFormatter = new SimpleDateFormat(DATE_TIME_FORMAT_STANDARD_DATABASE);

		String currentDateSub = null;
		int hour = calendar.get(Calendar.HOUR);

		//formatar os minutos para que nao ocorra inconsistencias
		if(minute >= 0 && minute < 15) {
			calendar.set(Calendar.MINUTE, 30);
			calendar.set(Calendar.HOUR, (hour - 1));
		}

		else if(minute >= 15 && minute < 30) {
			calendar.set(Calendar.MINUTE, 45);	   
			calendar.set(Calendar.HOUR, (hour - 1));
		}
		else if(minute >= 30 && minute < 45)
			calendar.set(Calendar.MINUTE, 0);

		else calendar.set(Calendar.MINUTE, 15);

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
			// System.out.println (fmt.format (cal.getTime()));
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
			//System.out.println (formatter.format (cal.getTime()));
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


	//DAYS DIFF ///////////////

	public int daysDifference(String startDate, String endDate, int index) throws ParseException {

		String startDt= "";
		String endDt = "";

		int days=0, difference=0;

		startDt = StringDataBaseDateToBRFormat(startDate);
		endDt = StringDataBaseDateToBRFormat(endDate);

		days = (int) diferencaDias(startDt, endDt);
		difference = (days * index);		

		return difference;
	}	

	//Sobrecarga 24 HRS		
	public int daysDifference(String startDate, String endDate) throws ParseException {

		String startDt = "";
		String endDt = "";

		int days = 0;

		startDt = StringDataBaseDateToBRFormat(startDate);
		endDt = StringDataBaseDateToBRFormat(endDate);

		days = (int) diferencaDias(startDt, endDt); 

		return days;
	}


	//DATE FORMAT		
	public String StringDataBaseDateToBRFormat(String data) throws ParseException {	

		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Date date = formato.parse(data);
		formato.applyPattern("dd/MM/yyyy");
		String dataFormatada = formato.format(date);

		return dataFormatada;	
	}

	public String DateTimeToStringIni(String data) throws ParseException {

		SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dtf.parse(data);
		dtf.applyPattern("yyyy-MM-dd 00:00:00");
		String dataFormatada = dtf.format(date);

		return dataFormatada;
	}

	public String DateTimeToStringFim(String data) throws ParseException {

		SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dtf.parse(data);
		dtf.applyPattern("yyyy-MM-dd 23:59:59");
		String dataFormatada = dtf.format(date);

		return dataFormatada;
	}	

	/////////////////////////////////
	///// PREENCHER DATA POR FLUXO
	////////////////////////////////

	// Preencher dias - Proporcional ao intervalo de tempo
	public void preencherDataPorPeriodo(String matriz[][], int col, int lin,  int range, String dtInicio) {

		String da, mth; // Formatar apresenta��o

		int inc = 0;
		int day = 1;

		// dia, m�s e ano da dataInicial

		String anoIni = dtInicio.substring(0, 4);
		String mesIni = dtInicio.substring(5, 7);
		String diaIni = dtInicio.substring(8, 10);

		// dia inicial - convers�o para inteiro
		int dayIni = Integer.parseInt(diaIni);

		// mes inicial - convers�o para inteiro
		int mthIni = Integer.parseInt(mesIni);

		// ano inicial - convers�o para inteiro
		int yearIni = Integer.parseInt(anoIni);

		int dia = dayIni; // inicializar vari�vel do dia
		int mes = mthIni; // inicializar vari�vel do m�s
		int ano = yearIni; // inicializar vari�vel do ano

		// Quantos dias possui o respectivo m�s
		YearMonth yearMonthObject = YearMonth.of(ano, mes);
		int daysInMonth = yearMonthObject.lengthOfMonth();

		// Novo Objeto - auxiliar
		//YearMonth yearMonthNew;

		// Preencher o n�mero de posi��es proporcional ao intervalo de 15 minutos (4
		// intervalos por hora x 24 horas)

		for (int i = 0; i < lin; i++) {

			// Caso o dia seja maior que total de dias no m�s
			if (dia > daysInMonth) {
				dia = 1; 
				mes++;
			}

			// Formata apresenta��o da String
			if (dia <= 9)
				da = "0";
			else
				da = "";
			if (mes < 10)
				mth = "0";
			else
				mth = "";

			matriz[i][col] = da + dia + "/" + mth + mes + "/" + ano; // Preenche os dados

			if(i == (day * (range - 1) + inc)) {					
				dia++;	
				day++;		
				inc++;
			}

			//else if (day > 1 && i == (day * range - inc) + inc) {
			//	      dia++;
			//	      day++;
			//	      inc++;					 
			//   
			// }	//System.out.println("P: "+p);				
		}

		//dia++; // Incrementar o dia at� limite do intervalo entre dias	

	}


	/////////////////////////////////
	///// PREENCHER DATA POR DIA
	////////////////////////////////

	// Preencher dias - Proporcional ao intervalo de tempo
	public void preencherDias(String matriz[][], int col, String dtInicio, int daysInMonth) {

		String da, mth; // Formatar apresenta��o

		// dia, m�s e ano da dataInicial

		String mesIni = dtInicio.substring(5, 7);
		String diaIni = dtInicio.substring(8, 10);

		// dia inicial - convers�o para inteiro
		int dayIni = Integer.parseInt(diaIni);

		// mes inicial - convers�o para inteiro
		int mthIni = Integer.parseInt(mesIni);

		int dia = dayIni; // inicializar vari�vel do dia
		int mes = mthIni; // inicializar vari�vel do m�s

		// Novo Objeto - auxiliar
		//YearMonth yearMonthNew;

		// Preencher o n�mero de posi��es proporcional ao intervalo de 15 minutos (4
		// intervalos por hora x 24 horas)

		for (int i = 0; i < daysInMonth; i++) {

			// Caso o dia seja maior que total de dias no m�s
			if (dia > daysInMonth)
				dia = 1; 

			// Formata apresenta��o da String
			if (dia <= 9)
				da = "0";
			else
				da = "";
			if (mes < 10)
				mth = "0";
			else
				mth = "";

			matriz[i][col] = da + dia; // Preenche os dados
			dia++;	


		}			

	}

	/////////////////////////////////
	///// PREENCHER DATA POR FLUXO
	////////////////////////////////

	// Preencher dias - Proporcional ao intervalo de tempo
	public void preencherDataMes(String matriz[][], int col, String mesInicio, String mesFim) {

		TranslationMethods translate = new TranslationMethods();

		int startMonth = Integer.parseInt(mesInicio);
		int endMonth = Integer.parseInt(mesFim);

		int monthsLength = (endMonth - startMonth) + 1;

		for (int i = 0; i < monthsLength; i++) {
			matriz[i][col] = translate.monthComparison(i+startMonth);

		}
	}


	///////////////////////////
	///// INTERVALOS
	/////////////////////////

	//Criar o intervalo de 24 horas
	public void intervalo24Horas(String[][] matriz, int col, int lin) { 

		for (int i = 0; i < lin; i++) 						
			matriz[i][col] = " ----- ";			
	}	

	// -------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Método para preencher nome dos equipamentos na apresentação do relatório
	 * @param equip - Lista de equipamentos
	 * @param matriz - Matriz com dados a serem preenchidos
	 * @param eqp - Array com equipamentos selecionados
	 * @param col - Coluna a ser aplicada
	 * @param lin - Número de registros 
	 * @param range - Intervalo por período
	 * @param days - Número de dias
	 */
	public void fillEquipName(List<? extends Equipments> equip, String[][] matriz, String[] eqp, String[] siteName, int colDate, int colEquip, int lin, int range, int days, String dtInicio) { 

		String da, mth; // Formatar apresenta��o

		// dia, m�s e ano da dataInicial

		String anoIni = dtInicio.substring(0, 4);
		String mesIni = dtInicio.substring(5, 7);
		String diaIni = dtInicio.substring(8, 10);

		// dia inicial - convers�o para inteiro
		int dayIni = Integer.parseInt(diaIni);

		// mes inicial - convers�o para inteiro
		int mthIni = Integer.parseInt(mesIni);

		// ano inicial - convers�o para inteiro
		int yearIni = Integer.parseInt(anoIni);

		int dia = dayIni; // inicializar vari�vel do dia
		int mes = mthIni; // inicializar vari�vel do m�s
		int ano = yearIni; // inicializar vari�vel do ano

		// Quantos dias possui o respectivo m�s
		YearMonth yearMonthObject = YearMonth.of(ano, mes);
		int daysInMonth = yearMonthObject.lengthOfMonth();		

		int idx = 0; // INDEX
		int inc = range; // INCRENENTO

		for (int j = 0; j < equip.size(); j++) { // LISTA DE EQUIPAMENTOS

			dia = dayIni; // EVER INITIZILE THE DAY, AFTER PASS BY ONE EQUIP
			mes = mthIni;  // EVER INITIZILE THE MONTH, AFTER PASS BY ONE EQUIP
			ano = yearIni; // EVER INITIZILE THE YEAR, AFTER PASS BY ONE EQUIP

			for (int i = 0; i < eqp.length; i++) { // EQUIPAMENTOS DA CAIXA DE SELECAO

				if (equip.get(j).getEquip_id() == Integer.parseInt(eqp[i])) { // COMPARAR EQUIPAMENTOS PARA OBTER O NOME

					siteName[i] = equip.get(j).getNome(); // FILL EQUIP NAME

					for (int d = 0; d < days; d++) { // NUMEROS DE DIAS

						for (int k = idx; k < inc; k++) { // PERCORRER AS LINHAS DA TABELA

							// Caso o dia seja maior que total de dias no m�s
							if (dia > daysInMonth) {
								dia = 1; 
								mes++;
							}

							// Formata apresenta��o da String
							if (dia <= 9)
								da = "0";
							else
								da = "";
							if (mes < 10)
								mth = "0";
							else
								mth = "";

							matriz[idx][colDate] = da + dia + "/" + mth + mes + "/" + ano; 		

							matriz[idx][colEquip] = equip.get(j).getNome();		
							idx++;

						}

						if(inc < lin) { // COMPARAR INCREMENTO COM NUMERO DE LINHAS -> CASO SEJA MENOR
							inc+= range;				           
						}

						dia++;

					}	
				}
			}			

		} 		

	}	// END


	// -------------------------------------------------------------------------------------------------------------------------------------

	//Criar o intervalo de 5 minutos
	public void intervalo05Minutos(String[][] matriz, int col, int lin) {

		int i, ini, fim, hora;

		ini = 0;
		fim = 5;
		hora = 0;

		for (i = 0; i < lin; i++) {

			if (fim == 60)
				fim = 59;

			if (ini > 55 && fim > 60) {
				ini = 0;
				fim = 5;
				hora++;
			}

			if (hora == 24)
				hora = 0;

			if ((ini == 0) && (fim == 5) && (hora < 10))
				matriz[i][col] = "0" + hora + ":0" + ini + " - 0" + hora + ":0"+ fim;	

			else if ((ini == 0) && (fim == 5) && (hora > 9))
				matriz[i][col] = "" + hora + ":0" + ini + " - " + hora + ":0"+ fim;	

			else if ((ini == 5) && (hora < 10))
				matriz[i][col] = "0" + hora + ":0" + ini + " - 0" + hora + ":"+ fim;

			else if ((ini == 5) && (hora > 9))
				matriz[i][col] = "" + hora + ":0" + ini + " - " + hora + ":"+ fim;

			else if ((ini != 0 && ini !=5) && (hora > 9))
				matriz[i][col] = "" + hora + ":" + ini + " - " + hora + ":"+ fim;

			else if((ini != 0 && ini !=5) && (hora < 10))
				matriz[i][col]= "0" + hora + ":" + ini + " - 0" + hora + ":"+ fim;	

			ini += 5;
			fim += 5;

		}

	}

	//Criar o intervalo de 6 minutos
	public void intervalo06Minutos(String[][] matriz, int col, int lin) {

		int i, ini, fim, hora;

		ini = 0;
		fim = 6;
		hora = 0;

		for (i = 0; i < lin; i++) {

			if (fim == 60)
				fim = 59;

			if (ini > 54 && fim > 60) {
				ini = 0;
				fim = 6;
				hora++;
			}

			if (hora == 24)
				hora = 0;

			if ((ini == 0) && (fim == 6) && (hora < 10))
				matriz[i][col] = "0" + hora + ":0" + ini + " - 0" + hora + ":0"+ fim;	

			else if ((ini == 0) && (fim == 6) && (hora > 9))
				matriz[i][col] = "" + hora + ":0" + ini + " - " + hora + ":0"+ fim;	

			else if ((ini == 6) && (hora < 10))
				matriz[i][col] = "0" + hora + ":0" + ini + " - 0" + hora + ":"+ fim;

			else if ((ini == 6) && (hora > 9))
				matriz[i][col] = "" + hora + ":0" + ini + " - " + hora + ":"+ fim;

			else if ((ini != 0 && ini !=6) && (hora > 9))
				matriz[i][col] = "" + hora + ":" + ini + " - " + hora + ":"+ fim;

			else if((ini != 0 && ini !=6) && (hora < 10))
				matriz[i][col] = "0" + hora + ":" + ini + " - 0" + hora + ":"+ fim;	

			ini += 6;
			fim += 6;

		}

	}

	//Criar o intervalo de 10 minutos
	public void intervalo10Minutos(String[][] matriz, int col, int lin) {

		int i, ini, fim, hora;

		ini = 0;
		fim = 10;
		hora = 0;

		for (i = 0; i < lin; i++) {

			if (fim == 60)
				fim = 59;

			if (ini > 50 && fim > 60) {
				ini = 0;
				fim = 10;
				hora++;
			}

			if (hora == 24)
				hora = 0;

			if ((ini == 0) && (hora < 10))
				matriz[i][col] = "0" + hora + ":0" + ini + " - 0" + hora + ":" + fim;

			else if ((ini != 0) && (hora < 10))
				matriz[i][col] = "0" + hora + ":" + ini + " - 0" + hora + ":" + fim;

			else if (ini == 0)
				matriz[i][col] = "" + hora + ":0" + ini + " - " + hora + ":" + fim;

			else
				matriz[i][col] = "" + hora + ":" + ini + " - " + hora + ":" + fim;

			ini += 10;
			fim += 10;

		}

	}	

	//15 MINUTOS
	public void intervalo15Minutos(String[][] matriz, int col, int lin) {

		int i, ini, fim, hora;

		ini = 0;
		fim = 15;
		hora = 0;

		for (i = 0; i < lin; i++) {

			if (fim == 60)
				fim = 59;

			if (ini > 45 && fim > 60) {
				ini = 0;
				fim = 15;
				hora++;
			}

			if (hora == 24)
				hora = 0;

			if ((ini == 0) && (hora < 10))
				matriz[i][col] = "0" + hora + ":0" + ini + " - 0" + hora + ":" + fim;

			else if ((ini != 0) && (hora < 10))
				matriz[i][col] = "0" + hora + ":" + ini + " - 0" + hora + ":" + fim;

			else if (ini == 0)
				matriz[i][col] = "" + hora + ":0" + ini + " - " + hora + ":" + fim;

			else
				matriz[i][col] = "" + hora + ":" + ini + " - " + hora + ":" + fim;

			ini += 15;
			fim += 15;

		}

	}	



	//30 MINUTOS
	public void intervalo30Min(String[][] matriz, int col, int lin) {

		int i, ini, fim, hora;

		ini = 0;
		fim = 30;
		hora = 0;

		for (i = 0; i < lin; i++) {

			if (fim == 60)
				fim = 59;

			if (ini > 30 && fim > 60) {
				ini = 0;
				fim = 30;
				hora++;
			}

			if (hora == 24)
				hora = 0;

			if ((ini == 0) && (hora < 10))
				matriz[i][col] = "0" + hora + ":0" + ini + " - 0" + hora + ":" + fim;

			else if ((ini != 0) && (hora < 10))
				matriz[i][col] = "0" + hora + ":" + ini + " - 0" + hora + ":" + fim;

			else if (ini == 0)
				matriz[i][col] = "" + hora + ":0" + ini + " - " + hora + ":" + fim;

			else
				matriz[i][col] = "" + hora + ":" + ini + " - " + hora + ":" + fim;

			ini += 30;
			fim += 30;

		}

	}			

	// HORA
	public void preencherHora(String[][] matriz, int col, int lin) {		

		int k = 0;
		String horaInicio = " ";
		String horaFim = " ";

		for (int i = 0; i < lin; i++) {		

			if (k == 24) k = 0;

			if (k < 10)
				horaInicio = "0" + String.valueOf(k);
			else
				horaInicio = String.valueOf(k);
			horaInicio += ":00";

			if (k < 10)
				horaFim = "0" + String.valueOf(k);
			else
				horaFim = String.valueOf(k);
			horaFim += ":59";

			matriz[i][col] = horaInicio + " - " + horaFim;
			k++;	   

		}				
	}

	//06 HORAS
	public void intervalo06Horas(String[][] matriz, int col, int lin) {

		int k = 0, j=5;
		String horaInicio = " ";
		String horaFim = " ";

		for (int i = 0; i < lin; i++) {		

			if (k == 24) k = 0;
			if (j == 29) j = 5;

			if (k < 10)
				horaInicio = "0" + String.valueOf(k);
			else
				horaInicio = String.valueOf(k);
			horaInicio += ":00";

			if (j < 10)
				horaFim = "0" + String.valueOf(j);
			else
				horaFim = String.valueOf(j);
			horaFim += ":59";

			matriz[i][col] = horaInicio + " - " + horaFim;
			k+=6;
			j+=6;

		}

	}

	///////////////////////////
	///// INTERVALOS
	/////////////////////////

	///////////////////////////
	///// INTERVALOS FLUXO
	/////////////////////////



	///////////////////////////
	///// INTERVALOS FLUXO
	/////////////////////////

	/////////////////////////
	////////// HOUR INDEX
	////////////////////////

	public int horaIndex30Min(int hora) { 

		int index = 0;

		if (hora == 0)
			index = 0;
		if (hora == 1)
			index = 2;
		if (hora == 2)
			index = 4;
		if (hora == 3)
			index = 6;
		if (hora == 4)
			index = 8;
		if (hora == 5)
			index = 10;
		if (hora == 6)
			index = 12;
		if (hora == 7)
			index = 14;
		if (hora == 8)
			index = 16;
		if (hora == 9)
			index = 18;
		if (hora == 10)
			index = 20;
		if (hora == 11)
			index = 22;
		if (hora == 12)
			index = 24;
		if (hora == 13)
			index = 26;
		if (hora == 14)
			index = 28;
		if (hora == 15)
			index = 30;
		if (hora == 16)
			index = 32;
		if (hora == 17)
			index = 34;
		if (hora == 18)
			index = 36;
		if (hora == 19)
			index = 38;
		if (hora == 20)
			index = 40;
		if (hora == 21)
			index = 42;
		if (hora == 22)
			index = 44;
		if (hora == 23)
			index = 46;

		return index;
	}

	public int horaIndex15Min(int hora) { 

		int index = 0;

		if (hora == 0)
			index = 0;
		if (hora == 1)
			index = 4;
		if (hora == 2)
			index = 8;
		if (hora == 3)
			index = 12;
		if (hora == 4)
			index = 16;
		if (hora == 5)
			index = 20;
		if (hora == 6)
			index = 24;
		if (hora == 7)
			index = 28;
		if (hora == 8)
			index = 32;
		if (hora == 9)
			index = 36;
		if (hora == 10)
			index = 40;
		if (hora == 11)
			index = 44;
		if (hora == 12)
			index = 48;
		if (hora == 13)
			index = 52;
		if (hora == 14)
			index = 56;
		if (hora == 15)
			index = 60;
		if (hora == 16)
			index = 64;
		if (hora == 17)
			index = 68;
		if (hora == 18)
			index = 72;
		if (hora == 19)
			index = 76;
		if (hora == 20)
			index = 80;
		if (hora == 21)
			index = 84;
		if (hora == 22)
			index = 88;
		if (hora == 23)
			index = 92;

		return index;
	}

	public int horaIndex05Min(int hora) {

		int index = 0;

		if (hora == 0)
			index = 0;
		if (hora == 1)
			index = 12;
		if (hora == 2)
			index = 24;
		if (hora == 3)
			index = 36;
		if (hora == 4)
			index = 48;
		if (hora == 5)
			index = 60;
		if (hora == 6)
			index = 72;
		if (hora == 7)
			index = 84;
		if (hora == 8)
			index = 96;
		if (hora == 9)
			index = 108;
		if (hora == 10)
			index = 120;
		if (hora == 11)
			index = 132;
		if (hora == 12)
			index = 144;
		if (hora == 13)
			index = 156;
		if (hora == 14)
			index = 168;
		if (hora == 15)
			index = 180;
		if (hora == 16)
			index = 192;
		if (hora == 17)
			index = 204;
		if (hora == 18)
			index = 216;
		if (hora == 19)
			index = 228;
		if (hora == 20)
			index = 240;
		if (hora == 21)
			index = 252;
		if (hora == 22)
			index = 264;
		if (hora == 23)
			index = 276;

		return index;
	}

	public int horaIndex06Min(int hora) { 

		int index = 0;

		if (hora == 0)
			index = 0;
		if (hora == 1)
			index = 10;
		if (hora == 2)
			index = 20;
		if (hora == 3)
			index = 30;
		if (hora == 4)
			index = 40;
		if (hora == 5)
			index = 50;
		if (hora == 6)
			index = 60;
		if (hora == 7)
			index = 70;
		if (hora == 8)
			index = 80;
		if (hora == 9)
			index = 90;
		if (hora == 10)
			index = 100;
		if (hora == 11)
			index = 110;
		if (hora == 12)
			index = 120;
		if (hora == 13)
			index = 130;
		if (hora == 14)
			index = 140;
		if (hora == 15)
			index = 150;
		if (hora == 16)
			index = 160;
		if (hora == 17)
			index = 170;
		if (hora == 18)
			index = 180;
		if (hora == 19)
			index = 190;
		if (hora == 20)
			index = 200;
		if (hora == 21)
			index = 210;
		if (hora == 22)
			index = 220;
		if (hora == 23)
			index = 230;

		return index;
	}

	public int horaIndex10Min(int hora) { 

		int index = 0;

		if (hora == 0)
			index = 0;
		if (hora == 1)
			index = 6;
		if (hora == 2)
			index = 12;
		if (hora == 3)
			index = 18;
		if (hora == 4)
			index = 24;
		if (hora == 5)
			index = 30;
		if (hora == 6)
			index = 36;
		if (hora == 7)
			index = 42;
		if (hora == 8)
			index = 48;
		if (hora == 9)
			index = 54;
		if (hora == 10)
			index = 60;
		if (hora == 11)
			index = 66;
		if (hora == 12)
			index = 72;
		if (hora == 13)
			index = 78;
		if (hora == 14)
			index = 84;
		if (hora == 15)
			index = 90;
		if (hora == 16)
			index = 96;
		if (hora == 17)
			index = 102;
		if (hora == 18)
			index = 108;
		if (hora == 19)
			index = 114;
		if (hora == 20)
			index = 120;
		if (hora == 21)
			index = 126;
		if (hora == 22)
			index = 132;
		if (hora == 23)
			index = 138;

		return index;
	}

	///////////////////////////////
	//// INDEX
	////////////////////////////

	public Integer index05Minutes(int hora, int minuto) {

		int idx = horaIndex05Min(hora);

		int i = 0;

		if (minuto == 0 ) 
			i = 0;
		if (minuto == 5 ) 
			i = 1;
		if (minuto == 10 ) 
			i = 2;
		if (minuto == 15 ) 
			i = 3;
		if (minuto == 20 ) 
			i = 4;
		if (minuto == 25 ) 
			i = 5;			
		if (minuto == 30) 
			i = 6;			
		if (minuto == 35 ) 
			i = 7;			
		if (minuto == 40 ) 
			i = 8;	                         
		if (minuto == 45 ) 
			i = 9;	
		if (minuto == 50 ) 
			i = 10;		                            
		if (minuto == 55 ) 
			i = 11;	

		return idx + i;
	}	

	public Integer index06Minutes(int hora, int minuto) {

		int idx = horaIndex06Min(hora);

		int i = 0;

		if (minuto == 0 ) 
			i = 0;
		if (minuto == 6 ) 
			i = 1;
		if (minuto == 12 ) 
			i = 2;
		if (minuto == 18 ) 
			i = 3;
		if (minuto == 24 ) 
			i = 4;
		if (minuto == 30 ) 
			i = 5;			
		if (minuto == 36 ) 
			i = 6;			
		if (minuto == 42 ) 
			i = 7;			
		if (minuto == 48 ) 
			i = 8;	                         
		if (minuto == 54 ) 
			i = 9;	

		return idx + i;
	}	


	public Integer index10Minutes(int hora, int minuto) {

		int idx = horaIndex10Min(hora);

		int i = 0;

		if(minuto == 0) 
			i=0;			
		if(minuto == 10)
			i=1;
		if(minuto == 20)
			i=2;
		if(minuto == 30)
			i=3;

		if(minuto == 40)
			i=4;

		if(minuto == 50)
			i=5;

		return idx + i;
	}	


	public Integer index15Minutes(int hora, int minuto) {

		int idx = horaIndex15Min(hora);

		int i = 0;

		if(minuto == 0) 
			i=0;			
		if(minuto == 15)
			i=1;
		if(minuto == 30)
			i=2;
		if(minuto == 45)
			i=3;

		return idx + i;
	}	


	public Integer index30Minutes(int hora, int minuto) {

		int idx = horaIndex30Min(hora);

		int i = 0;

		if(minuto == 0) 
			i=0;			
		if(minuto == 30)
			i=1;

		return idx + i;
	}	

	public Integer index06Hours(int hora) {

		int i = 0;

		if(hora == 0) 
			i=0;			
		if(hora == 6)
			i=1;
		if(hora == 12)
			i=2;
		if(hora == 18)
			i=3;	

		return i;
	}	

	///////////////////////////////////////////////////

	public void intervaloCountVehiclesName(String[][] matriz, List<? extends Equipments> listSats , String[] selectedEquips, int col, int lin, int period) { 

		int colName = col + 1; //Coluna do nome do equipamento

		for(int k = 0; k < listSats.size(); k++) {



			for(int i = 0; i < selectedEquips.length; i++) {

				if(listSats.get(k).getEquip_id() == Integer.parseInt(selectedEquips[i])) {

					//System.out.println(listSats.get(i).getEquip_id());

					for (int j = 0; j < lin; j+= period)			       
						matriz[colName][j] = listSats.get(i).getNome();	      

				}

			}

		}
	}

	///////////////////////////////////////////////////

	/* ********************************************* JSON ******************************************************* */

	// Preencher dias - Proporcional ao intervalo de tempo
	public void preencherJSONDataMes(String matriz[][], int col, String mesInicio, String mesFim, String ano) {

		int startMonth = Integer.parseInt(mesInicio);
		int endMonth = Integer.parseInt(mesFim);

		int monthsLength = (endMonth - startMonth) + 1;

		for (int i = 0; i < monthsLength; i++) {
			matriz[i][col] = "new Date("+ano+"," + i + ", 1)";

		}

	}

	/* ********************************************* DIAS ******************************************************* */

	// Preencher dias - Proporcional ao intervalo de tempo
	public void preencherJSONDias(String matriz[][], int col, String dtInicio, int daysInMonth) {

		String da, mth; // Formatar apresenta��o

		// dia, m�s e ano da dataInicial

		String anoIni = dtInicio.substring(0, 4);
		String mesIni = dtInicio.substring(5, 7);
		String diaIni = dtInicio.substring(8, 10);

		// dia inicial - convers�o para inteiro
		int dayIni = Integer.parseInt(diaIni);

		// mes inicial - convers�o para inteiro
		int mthIni = Integer.parseInt(mesIni);

		// ano inicial - convers�o para inteiro
		int yearIni = Integer.parseInt(anoIni);

		int dia = dayIni; // inicializar vari�vel do dia
		int mes = mthIni; // inicializar vari�vel do m�s
		int ano = yearIni; // inicializar vari�vel do ano

		// Novo Objeto - auxiliar
		//YearMonth yearMonthNew;

		// Preencher o n�mero de posi��es proporcional ao intervalo de 15 minutos (4
		// intervalos por hora x 24 horas)

		for (int i = 0; i < daysInMonth; i++) {

			// Caso o dia seja maior que total de dias no m�s
			if (dia > daysInMonth)
				dia = 1; 

			// Formata apresenta��o da String
			if (dia <= 9)
				da = "0";
			else
				da = "";
			if (mes < 10)
				mth = "0";
			else
				mth = "";

			matriz[i][col] = "new Date("+ ano + "," + (mes-1) + "," + dia+")"; // Preenche os dados
			dia++;	


		}			

	}

	/* ********************************************* PERIODOS ******************************************************* */

	// Preencher dias - Proporcional ao intervalo de tempo
	public void preencherJSONDataPorPeriodo(String matriz[][], int col, int lin,  int range, String dtInicio) {

		String da, mth; // Formatar apresenta��o

		int inc = 0;
		int day = 1;

		// dia, m�s e ano da dataInicial

		String anoIni = dtInicio.substring(0, 4);
		String mesIni = dtInicio.substring(5, 7);
		String diaIni = dtInicio.substring(8, 10);

		// dia inicial - convers�o para inteiro
		int dayIni = Integer.parseInt(diaIni);

		// mes inicial - convers�o para inteiro
		int mthIni = Integer.parseInt(mesIni);

		// ano inicial - convers�o para inteiro
		int yearIni = Integer.parseInt(anoIni);

		int dia = dayIni; // inicializar vari�vel do dia
		int mes = mthIni; // inicializar vari�vel do m�s
		int ano = yearIni; // inicializar vari�vel do ano

		// Quantos dias possui o respectivo m�s
		YearMonth yearMonthObject = YearMonth.of(ano, mes);
		int daysInMonth = yearMonthObject.lengthOfMonth();

		// Novo Objeto - auxiliar
		//YearMonth yearMonthNew;

		// Preencher o n�mero de posi��es proporcional ao intervalo de 15 minutos (4
		// intervalos por hora x 24 horas)

		for (int i = 0; i < lin; i++) {

			// Caso o dia seja maior que total de dias no m�s
			if (dia > daysInMonth) {
				dia = 1; 
				mes++;
			}

			// Formata apresenta��o da String
			if (dia <= 9)
				da = "0";
			else
				da = "";
			if (mes < 10)
				mth = "0";
			else
				mth = "";		

			if(range == 1)
				matriz[i][col] = "new Date("+ ano + "," + (mes-1) + "," + dia+")"; // Preenche os dados

			else matriz[i][col] = "new Date("+ ano + "," + (mes-1) + "," + dia+""; // Preenche os dados


			if(i == (day * (range - 1) + inc)) {					
				dia++;	
				day++;		
				inc++;
			}

			//else if (day > 1 && i == (day * range - inc) + inc) {
			//	      dia++;
			//	      day++;
			//	      inc++;					 
			//   
			// }	//System.out.println("P: "+p);				
		}

		//dia++; // Incrementar o dia at� limite do intervalo entre dias	

	}

	/* ********************************************* 05 MIN ******************************************************* */

	//Criar o intervalo de 5 minutos
	public void intervaloJSON05Minutos(String[][] matriz, int col, int lin) {

		int i, ini, fim, hora;

		ini = 0;
		fim = 5;
		hora = 0;

		for (i = 0; i < lin; i++) {

			if (fim == 60)
				fim = 59;

			if (ini > 55 && fim > 60) {
				ini = 0;
				fim = 5;
				hora++;
			}

			if (hora == 24)
				hora = 0;

			matriz[i][col] += "," + hora + "," + ini + ")";

			ini += 5;
			fim += 5;

		}

	}

	/* ********************************************* 06 MIN ******************************************************* */

	//Criar o intervalo de 6 minutos
	public void intervaloJSON06Minutos(String[][] matriz, int col, int lin) {

		int i, ini, fim, hora;

		ini = 0;
		fim = 6;
		hora = 0;

		for (i = 0; i < lin; i++) {

			if (fim == 60)
				fim = 59;

			if (ini > 54 && fim > 60) {
				ini = 0;
				fim = 6;
				hora++;
			}

			if (hora == 24)
				hora = 0;

			matriz[i][col] += "," + hora + "," + ini + ")";

			ini += 6;
			fim += 6;

		}

	}

	/* ********************************************* 10 MIN ******************************************************* */

	//Criar o intervalo de 10 minutos
	public void intervaloJSON10Minutos(String[][] matriz, int col, int lin) {

		int i, ini, fim, hora;

		ini = 0;
		fim = 10;
		hora = 0;

		for (i = 0; i < lin; i++) {

			if (fim == 60)
				fim = 59;

			if (ini > 50 && fim > 60) {
				ini = 0;
				fim = 10;
				hora++;
			}

			if (hora == 24)
				hora = 0;

			matriz[i][col] += "," + hora + "," + ini + ")";

			ini += 10;
			fim += 10;

		}

	}	

	/* ********************************************* 15 MIN ******************************************************* */

	//JSON 15 MINUTOS
	public void intervaloJSON15Minutos(String[][] matriz, int col, int lin) {

		int i, ini, fim, hora;

		ini = 0;
		fim = 15;
		hora = 0;

		for (i = 0; i < lin; i++) {

			if (fim == 60)
				fim = 59;

			if (ini > 45 && fim > 60) {
				ini = 0;
				fim = 15;
				hora++;
			}

			if (hora == 24)
				hora = 0;


			matriz[i][col] += "," + hora + "," + ini + ")";

			ini += 15;
			fim += 15;

		}

	}	

	/* ********************************************* 30 MIN ******************************************************* */

	//30 MINUTOS
	public void intervaloJSON30Minutos(String[][] matriz, int col, int lin) {

		int i, ini, fim, hora;

		ini = 0;
		fim = 30;
		hora = 0;

		for (i = 0; i < lin; i++) {

			if (fim == 60)
				fim = 59;

			if (ini > 30 && fim > 60) {
				ini = 0;
				fim = 30;
				hora++;
			}

			if (hora == 24)
				hora = 0;

			matriz[i][col] += "," + hora + "," + ini + ")";

			ini += 30;
			fim += 30;

		}

	}			

	/* ********************************************* 01 HORA ******************************************************* */

	// HORA
	public void intervaloJSON01Hora(String[][] matriz, int col, int lin) {		

		int k = 0;

		for (int i = 0; i < lin; i++) {		

			if (k == 24) k = 0;

			matriz[i][col] += "," + k + ", 0)";

			k++;	   

		}				
	}

	/* ********************************************* 06 HORAS ******************************************************* */

	//06 HORAS
	public void intervaloJSON06Horas(String[][] matriz, int col, int lin) {

		int k = 0;

		for (int i = 0; i < lin; i++) {		

			if (k == 24) k = 0;					

			matriz[i][col] += "," + k + ", 0)";

			k+=6;

		}

	}


	/* ************************************************************************************************************* */

	// ---------------------------------------------------------------------------------------------------------------


	// NUEVO	


	public boolean isDateAfter(String startDate, String endDate) throws ParseException {

		boolean isAfter = false;

		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

		Date date1 = sdformat.parse(startDate);
		Date date2 = sdformat.parse(endDate);

		if (date1.after(date2)) {
			isAfter = true;
			System.out.println("DATE  1 is AFTER DATE 2");

		}

		System.out.println(isAfter);

		return isAfter;

	}

	// ------------------------------------------------------------------------------------------------------

	public String getCallTime(String time) {

		DecimalFormat df = new DecimalFormat("####.##");
		df.format(1234.36); 

		String[] timeDivided = time.split(":");

		int hour = Integer.parseInt(timeDivided[0]);
		int minute = Integer.parseInt(timeDivided[1]);
		int second = Integer.parseInt(timeDivided[2]);

		String seconds = df.format(Double.parseDouble(String.valueOf(((second + (60 * minute) + (3600 * hour) % 3600) / 60))));

		return seconds;
	}

	// ------------------------------------------------------------------------------------------------------

}
