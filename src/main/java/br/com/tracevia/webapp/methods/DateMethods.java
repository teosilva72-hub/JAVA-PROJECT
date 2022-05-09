package br.com.tracevia.webapp.methods;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import br.com.tracevia.webapp.controller.global.LanguageBean;
import br.com.tracevia.webapp.controller.global.MessagesBean;

public class DateMethods {
	
	private static DateFormat df = new SimpleDateFormat ("dd/MM/yyyy");
	
	private String[] data;
	private String[] dias;
	private String[] hora;
	private String[] data_excel;
	private String[] data_hora;
	private String[] data_cabecalho; 
	private String[] intervalos;
	private String[] intervaloInicio;
	private String[] intervaloFim;
	private String[] intervaloSeparador;
	private String[] intervaloSeparadorDias;
	int maxDay, maxHour;
	
	MessagesBean msg;
	private ResourceBundle resourceBundle;
	private LanguageBean lang;
	
	public String[] getData() {
		return data;
	}

	public void setData(String[] data) {
		this.data = data;
	}
		
	public String[] getDias() {
		return dias;
	}

	public void setDias(String[] dias) {
		this.dias = dias;
	}

	public String[] getHora() {
		return hora;
	}

	public void setHora(String[] hora) {
		this.hora = hora;
	}

	public String[] getData_excel() {
		return data_excel;
	}

	public void setData_excel(String[] data_excel) {
		this.data_excel = data_excel;
	}

	public String[] getData_cabecalho() {
		return data_cabecalho;
	}

	public void setData_cabecalho(String[] data_cabecalho) {
		this.data_cabecalho = data_cabecalho;
	}

	public String[] getData_hora() {
		return data_hora;
	}

	public void setData_hora(String[] data_hora) {
		this.data_hora = data_hora;
	}
		
	public String[] getIntervalos() {
		return intervalos;
	}

	public void setIntervalos(String[] intervalos) {
		this.intervalos = intervalos;
	}
	
	public String[] getIntervaloInicio() {
		return intervaloInicio;
	}

	public void setIntervaloInicio(String[] intervaloInicio) {
		this.intervaloInicio = intervaloInicio;
	}

	public String[] getIntervaloFim() {
		return intervaloFim;
	}

	public void setIntervaloFim(String[] intervaloFim) {
		this.intervaloFim = intervaloFim;
	}

	public String[] getIntervaloSeparador() {
		return intervaloSeparador;
	}

	public void setIntervaloSeparador(String[] intervaloSeparador) {
		this.intervaloSeparador = intervaloSeparador;
	}
	
	public String[] getIntervaloSeparadorDias() {
		return intervaloSeparadorDias;
	}

	public void setIntervaloSeparadorDias(String[] intervaloSeparadorDias) {
		this.intervaloSeparadorDias = intervaloSeparadorDias;
	}
	
	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public void setResourceBundle(ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
	}

	public LanguageBean getLang() {
		return lang;
	}

	public void setLang(LanguageBean lang) {
		this.lang = lang;
	}
	
	//UPDATED

	public String DateTimeToStringIni(Date data) {
		
		DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String date = dtf.format(data);

		return date;
	}

	public String DateTimeToStringFim(Date data) {
		
		DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String date = dtf.format(data);

		return date;
	}	
	
	public String DateTimeToStringFormat(Date data) {
		
		DateFormat dtf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String date = dtf.format(data);

		return date;
	}
	
	public String formatDateTime(Date data) {
		
		DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
		String date = dtf.format(data);

		return date;
	}
	

public String StringToStringDateFormat(String data) throws ParseException {	
	
	SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
	Date date = formato.parse(data);
	formato.applyPattern("dd/MM/yyyy");
	String dataFormatada = formato.format(date);
	
	return dataFormatada;	
	}


public String stringDateOrder(String data) throws ParseException {	
	
	SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
	Date date = formato.parse(data);
	formato.applyPattern("dd-MM-yyyy");
	String dataFormatada = formato.format(date);
	
	return dataFormatada;	
	}

public String StringToStringDateTimeFormat(String data) throws ParseException {	
	
	SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date date = formato.parse(data);
	formato.applyPattern("dd/MM/yyyy HH:mm:ss");
	String dataFormatada = formato.format(date);
	
	return dataFormatada;	
	}
 
//Format DateTime to date
public String StringToDataBaseDateFormat(String data) throws ParseException {	
	
	SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
	Date date = formato.parse(data);
	formato.applyPattern("yyyy-MM-dd");
	String dataFormatada = formato.format(date);
	
	return dataFormatada;	
	}

public String createData(int dia, int mes, int ano) throws ParseException {
	   
	   String data = ano+"-"+mes+"-"+dia;
	   
	   SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Date date = formato.parse(data);	   
		String dataCriada = formato.format(date);
		
		return dataCriada;		   
}	

   public String createDataInicial(int dia, int mes, int ano) throws ParseException {
	   
	   String data = ano+"-"+mes+"-"+dia+" 00:00:00";
	   
	   SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = formato.parse(data);	   
		String dataCriada = formato.format(date);
		
		return dataCriada;		   
   }	
   
    public String createDataFinal(int dia, int mes, int ano) throws ParseException {
	   
	   String data = ano+"-"+mes+"-"+dia+" 23:59:59";
	   
	   SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = formato.parse(data);   
		String dataCriada = formato.format(date);
		
		return dataCriada;		   
   }	

	// DateTime Converter
	public DateTime StringToDateTime(String data) {

		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		DateTime date = formatter.parseDateTime(data);

		return date;
	}
	
	
	// Joda DateTime to String converter
		public String dateTimeToString(DateTime data) {

			DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
			String date = formatter.print(data);

			return date;
		}
		
		// Joda DateTime to String converter
				public String StringFormToMySQLstring(String date) throws ParseException {

					SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
					Date date_ = formato.parse(date);
					formato.applyPattern("yyyy-MM-dd");
					String dataFormatada = formato.format(date_);
					
					return dataFormatada;	
				}
		
		
	   // DateTime Converter
		public String StringDateFormat(String data) throws ParseException {

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = formatter.parse(data);
			formatter.applyPattern("dd/MM/yyyy HH:mm:ss");
			String dataFormatada = formatter.format(date);
			
			return dataFormatada;
		}
	
	//Calcular Data
	public long diferencaDias(String inicio, String fim) throws ParseException {	 	
		
        Date dtInicial = df.parse (inicio);
        Date dtFinal = df.parse (fim);
        
        return (dtFinal.getTime() - dtInicial.getTime() + 3600000L) / 86400000L;
    }
	
	public String getData(String ano, String mes, String dia) {

		String data = ano + "-" + mes + "-" + dia;
		return data;
	}

	  //Preencher os Dias
		public String[] dateForDays(String dtInicio, String dtFim, int tam) {

			String da, mth; // Formatar apresenta��o
			
			data = new String[tam];

			// m�s e ano da dataInicial
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
			YearMonth yearMonthNew;

			// Preencher o n�mero de posi��es proporcional ao intervalo de dias
			for (int i = 0; i < tam; i++) {

				// Caso o dia seja maior que total de dias no m�s
				if (dia > daysInMonth) {
					dia = 1; // reseta o dia
					mes++; // incrementa o m�s

					if (mes > 12) {
						mes = 1;
						ano++;
					} // caso for verdade - reseta o m�s e incrementa o ano

					// Quantos dias o m�s possui - atualiza os dados
					yearMonthNew = YearMonth.of(ano, mes);
					daysInMonth = yearMonthNew.lengthOfMonth();
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

				maxDay = i; // ultimo valor
				data[i] = da + dia + "/" + mth + mes + "/" + ano; // Preenche os dados				
				dia++; // Incrementar o dia at� limite do intervalo entre dias
								
			}	
			
			data[maxDay] = "TOTAL"; // Preencher ultimo dia
				
            return data;
		}
		
		public String[] preencherHora(int tamanho) {

			int dia=1, inc=1, k = 0;
			String horaInicio = " ";
			String horaFim = " ";
				
			hora = new String[tamanho];

			for (int i = 0; i < tamanho; i++) {					

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
	     	
		     	if (i == 24) {
					hora[i] = "Total";
					dia++;
					k = 0;
				}
			    else if (dia > 1 && i == ((dia * 24) + inc))
				
					{
						hora[i] = "Total";
						dia++;
						k = 0;
						inc++;
					}

					else {
						hora[i] = horaInicio + " - " + horaFim;
						k++;
					}			   
			}
								
			return hora;
		}
		
		public String[] preencherHoraContagem(int tamanho) {

			int k = 0;
			String horaInicio = " ";
			String horaFim = " ";
				
			hora = new String[tamanho];

			for (int i = 0; i < tamanho; i++) {		
				
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

		     	hora[i] = horaInicio + " - " + horaFim;
			    k++;	   
			   
			}
								
			return hora;
		}
						
		public String[] preencherDataHora(String dtInicio, String dtFim, int tamanho) {

			String da, mth; // Formatar apresenta��o
			
			data = new String[tamanho];

			// dia, m�s e ano da dataInicial

			String anoIni = dtInicio.substring(0, 4);
			String mesIni = dtInicio.substring(5, 7);
			String diaIni = dtInicio.substring(8, 10);

			// String anoFim = dtFim.substring(0,4);
			// String mesFim = dtFim.substring(5,7);
			// String diaFim = dtFim.substring(8,10);

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
			YearMonth yearMonthNew;

			// Preencher o n�mero de posi��es proporcional ao intervalo de 15 minutos (4
			// intervalos por hora x 24 horas)
			for (int i = 0; i < tamanho; i += 25) {

				// Caso o dia seja maior que total de dias no m�s
				if (dia > daysInMonth) {
					dia = 1; // reseta o dia
					mes++; // incrementa o m�s

					if (mes > 12) {
						mes = 1;
						ano++;
					} // caso for verdade - reseta o m�s e incrementa o ano

					// Quantos dias o m�s possui - atualiza os dados
					yearMonthNew = YearMonth.of(ano, mes);
					daysInMonth = yearMonthNew.lengthOfMonth();
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

				data[i] = da + dia + "/" + mth + mes + "/" + ano; // Preenche os dados
				dia++; // Incrementar o dia at� limite do intervalo entre dias
			}
			
			return data;
		}
		
		public String[] preencherDataHora2(String dtInicio, String dtFim, int tamanho) {

			String da, mth; // Formatar apresenta��o
			
			data = new String[tamanho];

			// dia, m�s e ano da dataInicial

			String anoIni = dtInicio.substring(0, 4);
			String mesIni = dtInicio.substring(5, 7);
			String diaIni = dtInicio.substring(8, 10);

			// String anoFim = dtFim.substring(0,4);
			// String mesFim = dtFim.substring(5,7);
			// String diaFim = dtFim.substring(8,10);

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
			YearMonth yearMonthNew;

			// Preencher o n�mero de posi��es proporcional ao intervalo de 15 minutos (4
			// intervalos por hora x 24 horas)
			for (int i = 0; i < tamanho; i += 24) {

				// Caso o dia seja maior que total de dias no m�s
				if (dia > daysInMonth) {
					dia = 1; // reseta o dia
					mes++; // incrementa o m�s

					if (mes > 12) {
						mes = 1;
						ano++;
					} // caso for verdade - reseta o m�s e incrementa o ano

					// Quantos dias o m�s possui - atualiza os dados
					yearMonthNew = YearMonth.of(ano, mes);
					daysInMonth = yearMonthNew.lengthOfMonth();
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

				data[i] = da + dia + "/" + mth + mes + "/" + ano; // Preenche os dados
				dia++; // Incrementar o dia at� limite do intervalo entre dias
			}
			
			return data;
		}
		
		// Preencher dias - Proporcional ao intervalo de tempo
		public String[] preencherDataIntervalo(String dtInicio, String dtFim, int tamanho) {

			data = new String[tamanho];
			
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

			// Novo Objeto - auxiliar
			YearMonth yearMonthNew;

			// Preencher o n�mero de posi��es proporcional ao intervalo de 15 minutos (4
			// intervalos por hora x 24 horas)
			for (int i = 0; i < tamanho; i += 97) {

				// Caso o dia seja maior que total de dias no m�s
				if (dia > daysInMonth) {
					dia = 1; // reseta o dia
					mes++; // incrementa o m�s

					if (mes > 12) {
						mes = 1;
						ano++;
					} // caso for verdade - reseta o m�s e incrementa o ano

					// Quantos dias o m�s possui - atualiza os dados
					yearMonthNew = YearMonth.of(ano, mes);
					daysInMonth = yearMonthNew.lengthOfMonth();
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

				data[i] = da + dia + "/" + mth + mes + "/" + ano; // Preenche os dados
				dia++; // Incrementar o dia at� limite do intervalo entre dias

			}
			
			return data;
		}
		
		
		
		public boolean verificaDatas(DateTime inicio, DateTime fim){
			
			boolean data;
			
			if (inicio.isBefore(fim)){
				data = true;
			}
			else if (inicio.isAfter(fim))
				data = false;
			
			else if(fim.isBefore(inicio))
				    data = false;
				 
			else data = false;
			
			return data;
		}
		
		
		// Preencher dias - Proporcional ao intervalo de tempo
		public void FillExcelDataByHora(String dtInicio, String dtFim, int tam) {

			String da, mth; // Formatar apresenta��o
			data_excel = new String[tam];
			data_cabecalho = new String[tam];			
			
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
			YearMonth yearMonthNew;

			// Preencher o n�mero de posi��es proporcional ao intervalo de 15 minutos (4
			// intervalos por hora x 24 horas)
			for (int i = 0; i < tam; i++) {

				// Caso o dia seja maior que total de dias no m�s
				if (dia > daysInMonth) {
					dia = 1; // reseta o dia
					mes++; // incrementa o m�s

					if (mes > 12) {
						mes = 1;
						ano++;
					} // caso for verdade - reseta o m�s e incrementa o ano

					// Quantos dias o m�s possui - atualiza os dados
					yearMonthNew = YearMonth.of(ano, mes);
					daysInMonth = yearMonthNew.lengthOfMonth();
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

				data_excel[i] = da + dia + "-" + mth + mes + "-" + ano; // Preenche os dados
				data_cabecalho[i] = da + dia + "/" + mth + mes + "/" + ano; // Preenche os dados	
				dia++;
			}
		}
		
		
		// NOVO RELATORIO CONTAGEM 
				public void preencherDataExcel(String dtInicio, String dtFim, int tam) {

					String da, mth; // Formatar apresenta��o
					
					int length = tam * 24;
					int pos = 0;
					
					data_hora = new String[length];							
					
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
					YearMonth yearMonthNew;

					// Preencher o n�mero de posi��es proporcional ao intervalo de 15 minutos (4
					// intervalos por hora x 24 horas)
					for (int i = 1; i <=tam; i++) {

						// Caso o dia seja maior que total de dias no m�s
						if (dia > daysInMonth) {
							dia = 1; // reseta o dia
							mes++; // incrementa o m�s

							if (mes > 12) {
								mes = 1;
								ano++;
							} // caso for verdade - reseta o m�s e incrementa o ano

							// Quantos dias o m�s possui - atualiza os dados
							yearMonthNew = YearMonth.of(ano, mes);
							daysInMonth = yearMonthNew.lengthOfMonth();
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
						
						for(int d=pos; d < (i*24); d++ ) // pos se inicia com zero --- (i (dias) * 24) >>> �ndice m�ximo por dia
						data_hora[d] = da + dia + "/" + mth + mes + "/" + ano; // Preenche os dados	
						
						if(i != tam) {	pos += 24; //a cada passagem incrementa 24
						                dia++; // incrementa um dia
						             }
						     }			     
				          }
				
						
				public int getHoraIndex(int hora) {

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
				
				
				public int selectedMonth(String selectedMes) {
					
					int selectMonth = 0;
										
					if (selectedMes.equals("January"))
						selectMonth = 1;
					if (selectedMes.equals("February"))
						selectMonth = 2;
					if (selectedMes.equals("March"))
						selectMonth = 3;
					if (selectedMes.equals("April"))
						selectMonth = 4;
					if (selectedMes.equals("May"))
						selectMonth = 5;
					if (selectedMes.equals("June"))
						selectMonth = 6;
					if (selectedMes.equals("July"))
						selectMonth = 7;
					if (selectedMes.equals("August"))
						selectMonth = 8;
					if (selectedMes.equals("September"))
						selectMonth = 9;
					if (selectedMes.equals("October"))
						selectMonth = 10;
					if (selectedMes.equals("November"))
						selectMonth = 11;
					if (selectedMes.equals("December"))
						selectMonth = 12;

					return selectMonth;
				}
				
				//Abrevia��o do M�s
					public String abrevMes(String selectedMes) {

						String selectMonth = "";				
												
						if (selectedMes.equals("January"))
							selectMonth = "jan";
						if (selectedMes.equals("February"))
							selectMonth = "feb";
						if (selectedMes.equals("March"))
							selectMonth = "mar";
						if (selectedMes.equals("April"))
							selectMonth = "apr";
						if (selectedMes.equals("May"))
							selectMonth = "may";
						if (selectedMes.equals("June"))
							selectMonth = "jun";
						if (selectedMes.equals("July"))
							selectMonth = "jul";
						if (selectedMes.equals("August"))
							selectMonth = "aug";
						if (selectedMes.equals("September"))
							selectMonth = "sep";
						if (selectedMes.equals("October"))
							selectMonth = "oct";
						if (selectedMes.equals("November"))
							selectMonth = "nov";
						if (selectedMes.equals("December"))
							selectMonth = "dec";						
						
						return selectMonth;
					}
					
					//Abrevia��o do Ano 
					public String abrevAno(String ano) {		
						return ano.substring(2,4);	
					}
					
					// Criar o intervalo em minutos entre as horas
					public String[] intervaloHora(int tamanho) {

						int i, ini, fim, hora;
												
						intervalos = new String[tamanho];	

						ini = 0;
						fim = 15;
						hora = 0;

						for (i = 0; i < tamanho; i++) {

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
								intervalos[i] = "0" + hora + ":0" + ini + " - 0" + hora + ":" + fim;

							else if ((ini != 0) && (hora < 10))
								intervalos[i] = "0" + hora + ":" + ini + " - 0" + hora + ":" + fim;

							else if (ini == 0)
								intervalos[i] = "" + hora + ":0" + ini + " - " + hora + ":" + fim;

							else
								intervalos[i] = "" + hora + ":" + ini + " - " + hora + ":" + fim;

							ini += 15;
							fim += 15;

						}
						
						return intervalos;
					}				
					
					
					// Criar o intervalo em minutos entre as horas
					public String[] intervalo15Inicio(int tamanho) { 

						int i, ini, hora;
												
						intervaloInicio = new String[tamanho];	

						ini = 0;						
						hora = 0;

						for (i = 0; i < tamanho; i++) {

							if (ini > 45) {
								ini = 0;							
								hora++;
							}

							if (hora == 24)
								hora = 0;

							if ((ini == 0) && (hora < 10))
								intervaloInicio[i] = "0" + hora + ":0" + ini;

							else if ((ini != 0) && (hora < 10))
								intervaloInicio[i] = "0" + hora + ":" + ini;

							else if (ini == 0)
								intervaloInicio[i] = "" + hora + ":0" + ini;

							else
								intervaloInicio[i] = "" + hora + ":" + ini;
							
							ini += 15;							
						}
						
						return intervaloInicio;
					}	
					
					// Criar o intervalo em minutos entre as horas
					public String[] intervalo15Fim(int tamanho) {

						int i, fim, hora;
												
						intervaloFim = new String[tamanho];	

						fim = 15;
						hora = 0;

						for (i = 0; i < tamanho; i++) {

							if (fim == 60)
								fim = 59;

							if (fim > 60) {								
								fim = 15;
								hora++;
							}

							if (hora == 24)
								hora = 0;

							if ((fim == 0) && (hora < 10))
								intervaloFim[i] = "0" + hora +":"+fim;

							else if ((fim != 0) && (hora < 10))
								intervaloFim[i] = "0" + hora + ":" + fim;

							else if (fim == 0)
								intervaloFim[i] = "" + hora +":"+fim;

							else
								intervaloFim[i] = "" + hora + ":" + fim; 

							fim += 15;

						}
						
						return intervaloFim;
					}
					
					// Criar o intervalo em minutos entre as horas
					public String[] intervaloFluxo05Inicio(int tamanho) {

						int i, ini, hora;
												
						intervaloInicio = new String[tamanho];	

						ini = 0;						
						hora = 0;

						for (i = 0; i < tamanho; i++) {

							if (ini > 59) {
								ini = 0;	
								hora++;
							}
							
							if (hora == 24)
								hora = 0;
							
							if ((ini == 0) && (hora < 10))
								intervaloInicio[i] = "0" + hora + ":0" + ini;	
							
							else if ((ini == 0) && (hora > 9))
								intervaloInicio[i] = "" + hora + ":0" + ini;
							
							else if ((ini == 5) && (hora < 10))
								intervaloInicio[i] = "0" + hora + ":0" + ini;
							
							else if ((ini == 5) && (hora > 9))
								intervaloInicio[i] = "" + hora + ":0" + ini;
													
							else if ((ini != 0 && ini !=5) && (hora > 9))
								intervaloInicio[i] = "" + hora + ":" + ini;
							
							else if((ini != 0 && ini !=5) && (hora < 10))
								intervaloInicio[i] = "0" + hora + ":" + ini;							
																			
							ini += 5;													
						}
						
						return intervaloInicio;
					}	
					
					// Criar o intervalo em minutos entre as horas
					public String[] intervaloFluxo05Fim(int tamanho) {

						int i, fim, hora;
												
						intervaloFim = new String[tamanho];	

						fim = 5;
						hora = 0;

						for (i = 0; i < tamanho; i++) {
							
							if(fim == 60) fim = 59;
																					
							else if (fim > 60) {
								fim = 5;								
								hora++;
							}
							
							if (hora == 24)
								hora = 0;

							if ((fim == 5) && (hora < 10))
								intervaloFim[i] = "0" + hora + ":0" + fim;							

							else if ((fim == 5) && (hora > 9))
								intervaloFim[i] = "" + hora + ":0" + fim;						
						
							else if ((fim !=5) && (hora > 9))
								intervaloFim[i] = "" + hora + ":" + fim;
							
							else if((fim !=5) && (hora < 10))
								intervaloFim[i] = "0" + hora + ":" + fim;
							
							else if ((fim != 0 && fim !=5) && (hora > 9))
								intervaloFim[i] = "" + hora + ":" + fim;
							
							else if((fim != 0 && fim !=5) && (hora < 10))
								intervaloFim[i] = "0" + hora + ":" + fim;
							
							fim += 5;							
						}
						
						return intervaloFim;
					}
					
					// Criar o intervalo em minutos entre as horas
					public String[] intervaloFluxo06Inicio(int tamanho) {

						int i, ini, hora;
												
						intervaloInicio = new String[tamanho];	

						ini = 0;						
						hora = 0;

						for (i = 0; i < tamanho; i++) {

							if (ini > 59) {
								ini = 0;	
								hora++;
							}
							
							if (hora == 24)
								hora = 0;
							
							if ((ini == 0) && (hora < 10))
								intervaloInicio[i] = "0" + hora + ":0" + ini;	
							
							else if ((ini == 0) && (hora > 9))
								intervaloInicio[i] = "" + hora + ":0" + ini;
							
							else if ((ini == 6) && (hora < 10))
								intervaloInicio[i] = "0" + hora + ":0" + ini;
							
							else if ((ini == 6) && (hora > 9))
								intervaloInicio[i] = "" + hora + ":0" + ini;
													
							else if ((ini != 0 && ini !=6) && (hora > 9))
								intervaloInicio[i] = "" + hora + ":" + ini;
							
							else if((ini != 0 && ini !=6) && (hora < 10))
								intervaloInicio[i] = "0" + hora + ":" + ini;							
																			
							ini += 6;													
						}
						
						return intervaloInicio;
					}	
					
					// Criar o intervalo em minutos entre as horas
					public String[] intervaloFluxo06Fim(int tamanho) {

						int i, fim, hora;
												
						intervaloFim = new String[tamanho];	

						fim = 6;
						hora = 0;

						for (i = 0; i < tamanho; i++) {
							
							if(fim == 60) fim = 59;
																					
							else if (fim > 60) {
								fim = 6;								
								hora++;
							}
													
							if (hora == 24)
								hora = 0;

							if ((fim == 0) && (hora < 10))
								intervaloFim[i] = "0" + hora + ":0" + fim;							

							else if ((fim == 0) && (hora > 9))
								intervaloFim[i] = "" + hora + ":0" + fim;
							
							else if ((fim == 6) && (hora < 10))
								intervaloFim[i] = "0" + hora + ":0" + fim;
							
							else if ((fim == 6) && (hora > 9))
								intervaloFim[i] = "" + hora + ":0" + fim;
													
							else if ((fim != 0 && fim !=6) && (hora > 9))
								intervaloFim[i] = "" + hora + ":" + fim;
							
							else if((fim != 0 && fim !=6) && (hora < 10))
								intervaloFim[i] = "0" + hora + ":" + fim;
							
							fim += 6;							
						}
						
						return intervaloFim;
					}
					
					// Criar o intervalo em minutos entre as horas
					public String[] intervaloFluxoHoraInicio(int tamanho) {

						int i, ini, fim, hora;
												
						intervaloInicio = new String[tamanho];	

						ini = 0;
						fim = 0;
						hora = 0;

						for (i = 0; i < tamanho; i++) {

							if (fim == 60)
								fim = 59;
							
							if (hora == 24)
								hora = 0;

							if ((ini == 0) && (hora < 10))
								intervaloInicio[i] = "0" + hora + ":0" + ini;

							else if ((ini != 0) && (hora < 10))
								intervaloInicio[i] = "0" + hora + ":" + ini;

							else if (ini == 0)
								intervaloInicio[i] = "" + hora + ":0" + ini;

							else
								intervaloInicio[i] = "" + hora + ":" + ini;
													
							hora++;
						}
						
						return intervaloInicio;
					}	
					
					// Criar o intervalo em minutos entre as horas
					public String[] intervaloFluxoHoraFim(int tamanho) {

						int i, fim, hora;
												
						intervaloFim = new String[tamanho];	

						fim = 59;
						hora = 0;

						for (i = 0; i < tamanho; i++) {															
							
							if (hora == 24)
								hora = 0;

							if ((hora == 0) && (hora < 10))
								intervaloFim[i] = "0" + hora + ":" + fim;

							else if ((hora != 0) && (hora < 10))
								intervaloFim[i] = "0" + hora + ":" + fim;

							else if (hora == 0)
								intervaloFim[i] = "" + hora + ":" + fim;

							else
								intervaloFim[i] = "" + hora + ":" + fim;
																				
							hora++;
						}
						
						return intervaloFim;
					}	
					
					
					
					
					public String[] intervalo06Horas(int tamanho) {

						int k = 0, j=5;
						String horaInicio = " ";
						String horaFim = " ";
							
						hora = new String[tamanho];

						for (int i = 0; i < tamanho; i++) {		
							
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

					     	hora[i] = horaInicio + " - " + horaFim;
						    k+=6;
						    j+=6;
						   
						}
											
						return hora;
					}
			
					// Criar o intervalo em minutos entre as horas
					public String[] intervalo06HoraInicio(int tamanho) {

						int i, ini, fim, hora;
												
						intervaloInicio = new String[tamanho];	

						ini = 0;
						fim = 0;
						hora = 0;

						for (i = 0; i < tamanho; i++) {

							if (fim == 60)
								fim = 59;
							
							if (hora == 24)
								hora = 0;

							if ((ini == 0) && (hora < 10))
								intervaloInicio[i] = "0" + hora + ":0" + ini;

							else if ((ini != 0) && (hora < 10))
								intervaloInicio[i] = "0" + hora + ":" + ini;

							else if (ini == 0)
								intervaloInicio[i] = "" + hora + ":0" + ini;

							else
								intervaloInicio[i] = "" + hora + ":" + ini;
													
							hora+=6;
						}
						
						return intervaloInicio;
					}	
					
					// Criar o intervalo em minutos entre as horas
					public String[] intervalo06HoraFim(int tamanho) {

						int i, fim, hora;
												
						intervaloFim = new String[tamanho];	

						fim = 59;
						hora = 5;

						for (i = 0; i < tamanho; i++) {															
							
							if (hora == 29)
								hora = 5;

							if ((hora == 5) && (hora < 10))
								intervaloFim[i] = "0" + hora + ":" + fim;

							else if ((hora != 5) && (hora < 10))
								intervaloFim[i] = "0" + hora + ":" + fim;

							else if (hora == 5)
								intervaloFim[i] = "" + hora + ":" + fim;

							else
								intervaloFim[i] = "" + hora + ":" + fim;
																				
							hora+=6;
						}
						
						return intervaloFim;
					}
					
									
					
					// Criar o intervalo em minutos entre as horas
					public String[] intervaloSeparador(int tamanho) {
																	
						intervaloSeparador = new String[tamanho];				

						for (int i = 0; i < tamanho; i++) 						
							 intervaloSeparador[i] = "-";						
						
						return intervaloSeparador;
					}
					
					// Criar o intervalo em minutos entre as horas
					public String[] intervaloSeparador24Horas(int tamanho) { 
																	
						intervaloSeparadorDias = new String[tamanho];				

						for (int i = 0; i < tamanho; i++) 						
							 intervaloSeparadorDias[i] = " ---- ";						
						
						return intervaloSeparadorDias;
					}
					
					
					// Preencher dias - Proporcional ao intervalo de tempo
					public String[] preencherDataFluxo(String dtInicio, String dtFim, int tamanho) {

						String da, mth; // Formatar apresenta��o
						
						data = new String[tamanho];

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
						YearMonth yearMonthNew;

						// Preencher o n�mero de posi��es proporcional ao intervalo de 15 minutos (4
						// intervalos por hora x 24 horas)
						for (int i = 0; i < tamanho; i += 96) {

							// Caso o dia seja maior que total de dias no m�s
							if (dia > daysInMonth) {
								dia = 1; // reseta o dia
								mes++; // incrementa o m�s

								if (mes > 12) {
									mes = 1;
									ano++;
								} // caso for verdade - reseta o m�s e incrementa o ano

								// Quantos dias o m�s possui - atualiza os dados
								yearMonthNew = YearMonth.of(ano, mes);
								daysInMonth = yearMonthNew.lengthOfMonth();
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

							data[i] = da + dia + "/" + mth + mes + "/" + ano; // Preenche os dados
							dia++; // Incrementar o dia at� limite do intervalo entre dias
						}
						
						return data;
					}
					
					// Preencher dias - Proporcional ao intervalo de tempo
					public String[] preencherDataFluxoPeriodo(String dtInicio, String dtFim, int tamanho, int periodo) {

						String da, mth; // Formatar apresenta��o
						
						data = new String[tamanho];
						
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
						for (int i = 0; i < tamanho; i += periodo) {

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

							data[i] = da + dia + "/" + mth + mes + "/" + ano; // Preenche os dados
							dia++; // Incrementar o dia at� limite do intervalo entre dias							
						}
						
						return data;
					}
					
															
					// Preencher dias - Proporcional ao intervalo de tempo
					public String[] preencherDias(int tamanho, int periodo) {
											
						dias = new String[tamanho];

						int days = 1;
						
						for (int i = 0; i < tamanho; i += periodo) {				
							dias[i] = String.valueOf(days);
							days++; 							
						}
						
						return dias;
					}
					
					/** M�todo para calcular o n�mero de dias entre duas datas (di�rio)	
					 * @param dtInit - data inicial
					 * @param dtEnd - data final	 
					 * @throws ParseException 
					 * @return int - retorna diferen�a entre duas datas */
					
					public int daysDifference(String dtInit, String dtEnd) throws ParseException {
												 
						int days=0;
						
						dtInit = StringToStringDateFormat(dtInit);
						dtEnd = StringToStringDateFormat(dtEnd);

						days = (int) diferencaDias(dtInit, dtEnd); 
											
						return days;
					}
					
					/** M�todo para calcular o n�mero de dias entre duas datas (Hor�rio)	
					 * @param dtInit - data inicial
					 * @param dtEnd - data final	 
					 * @throws ParseException 
					 * @return int - retorna diferen�a entre duas datas */
					
					public int daysDifferenceHour(String dtInit, String dtEnd) throws ParseException {
												 
						int days=0, difference=0;
						
						dtInit = StringToStringDateFormat(dtInit);
						dtEnd = StringToStringDateFormat(dtEnd);

						days = (int) diferencaDias(dtInit, dtEnd);
						difference = (days * 25);		
						
						return difference;
					}
					
					/** M�todo para calcular o n�mero de dias entre duas datas (Intervalo)
					 * @param dtInit - data inicial
					 * @param dtEnd - data final	 
					 * @throws ParseException 
					 * @return int - retorna diferen�a entre duas datas */
					
					public int daysDifferenceInterval(String dtInit, String dtEnd) throws ParseException {
												 
						int days=0, difference=0;
						
						dtInit = StringToStringDateFormat(dtInit);
						dtEnd = StringToStringDateFormat(dtEnd);

						days = (int) diferencaDias(dtInit, dtEnd);
						difference = (days * 97);		
						
						return difference;
					}
					
					//NOVO 10/12/2019
					/** M�todo para calcular o n�mero de dias entre duas datas (Hor�rio)	
					 * @param dtInit - data inicial
					 * @param dtEnd - data final	 
					 * @throws ParseException 
					 * @return int - retorna diferen�a entre duas datas */
					
					public int daysDifferenceHours(String dtInit, String dtEnd) throws ParseException {
												 
						int days=0, difference=0;
					
						days = (int) diferencaDias(dtInit, dtEnd);
						difference = (days * 24);		
						
						return difference;
					}
					
					//NOVO 
					
					
					/** M�todo para comparar se quantidade de dias selecionados, 
					 * est� conforme com limite pr�-estabelecido de dias para consulta.					 
					 * @param dtInit - data de �nicio
					 * @param dtEnd - data de fim
					 * @return void - retorno vazio
					 * @throws ParseException
					 */
					
					public boolean limitSearch(String dtInit, String dtEnd) throws ParseException {
						
						
						msg = new MessagesBean();
						int days = 0;
						boolean response = false;
											
						days = (int) diferencaDias(dtInit, dtEnd);
						
						if(days > 45)							
							response = true;						
						
						return response;
						
					}
					
					
					
					
					
					
					
					
					
					
					public String translateMonth(int month, Locale lang){
						
						LanguageBean language = new LanguageBean();	
						Locale locale = language.getLocale(); 
						
						String rsMonth = "";
						
						if(month == 1) {
							if(locale.toString().equals("pt_BR"))
							   rsMonth = "Janeiro";
						
							if(locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX"))
							   rsMonth = "Enero";
					
							if(locale.toString().equals("en_US"))
							   rsMonth = "January";
						}
						
						if(month == 2) {
							if(locale.toString().equals("pt_BR"))
							   rsMonth = "Fevereiro";
						
							if(locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX"))
							   rsMonth = "Febrero";
					
							if(locale.toString().equals("en_US"))
							   rsMonth = "February";
						}
						
						if(month == 3) {
							if(locale.toString().equals("pt_BR"))
							   rsMonth = "Mar�o";
						
							if(locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX"))
							   rsMonth = "Marzo";
					
							if(locale.toString().equals("en_US"))
							   rsMonth = "March";
						}
						
						if(month == 4) {
							if(locale.toString().equals("pt_BR"))
							   rsMonth = "Abril";
						
							if(locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX"))
							   rsMonth = "Abril";
					
							if(locale.toString().equals("en_US"))
							   rsMonth = "April";
						}
						
						if(month == 5) {
							if(locale.toString().equals("pt_BR"))
							   rsMonth = "Maio";
						
							if(locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX"))
							   rsMonth = "Mayo";
					
							if(locale.toString().equals("en_US"))
							   rsMonth = "May";
						}
						
						if(month == 6) {
							if(locale.toString().equals("pt_BR"))
							   rsMonth = "Junho";
						
							if(locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX"))
							   rsMonth = "Junio";
					
							if(locale.toString().equals("en_US"))
							   rsMonth = "June";
						}
						
						
						if(month == 7) {
							if(locale.toString().equals("pt_BR"))
							   rsMonth = "Julho";
						
							if(locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX"))
							   rsMonth = "Julio";
					
							if(locale.toString().equals("en_US"))
							   rsMonth = "July";
						}
						
						if(month == 8) {
							if(locale.toString().equals("pt_BR"))
							   rsMonth = "Agosto";
						
							if(locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX"))
							   rsMonth = "Agosto";
					
							if(locale.toString().equals("en_US"))
							   rsMonth = "August";
						}
						
						if(month == 9) {
							if(locale.toString().equals("pt_BR"))
							   rsMonth = "Setembro";
						
							if(locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX"))
							   rsMonth = "Septiembre";
					
							if(locale.toString().equals("en_US"))
							   rsMonth = "September";
						}
						
						if(month == 10) {
							if(locale.toString().equals("pt_BR"))
							   rsMonth = "Outubro";
						
							if(locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX"))
							   rsMonth = "Octubre";
					
							if(locale.toString().equals("en_US"))
							   rsMonth = "October";
						}
						
						if(month == 11) {
							if(locale.toString().equals("pt_BR"))
							   rsMonth = "Novembro";
						
							if(locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX"))
							   rsMonth = "Noviembre";
					
							if(locale.toString().equals("en_US"))
							   rsMonth = "November";
						}
						
						if(month == 12) {
							if(locale.toString().equals("pt_BR"))
							   rsMonth = "Dezembro";
						
							if(locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX"))
							   rsMonth = "Diciembre";
					
							if(locale.toString().equals("en_US"))
							   rsMonth = "December";
						}
						
					
						
						return rsMonth;							
						
					}
					
					//Abrevia��o do M�s
					public String abrevMes(int month) {

						LanguageBean language = new LanguageBean();	
						Locale locale = language.getLocale(); 
						
						String rsMonth = "";
						
						if(month == 1) {
							if(locale.toString().equals("pt_BR") || locale.toString().equals("en_US"))
							   rsMonth = "jan";
						
							if(locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX"))
							   rsMonth = "ene";							
						}
						
						if(month == 2) {
							if(locale.toString().equals("pt_BR"))
							   rsMonth = "fev";
						
							if(locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX") || locale.toString().equals("en_US"))							 
							   rsMonth = "feb";
						}
						
						if(month == 3) {
							if(locale.toString().equals("pt_BR") || locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX") || locale.toString().equals("en_US"))
							   rsMonth = "mar";
						}
						
						if(month == 4) {
							if(locale.toString().equals("pt_BR") || locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX"))
							   rsMonth = "abr";
											
							if(locale.toString().equals("en_US"))
							   rsMonth = "apr";
						}
						
						if(month == 5) {
							if(locale.toString().equals("pt_BR"))
							   rsMonth = "mai";
						
							if(locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX") || locale.toString().equals("en_US"))
							   rsMonth = "may";					
						}
						
						if(month == 6) {
							if(locale.toString().equals("pt_BR") || locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX") || locale.toString().equals("en_US"))
							            rsMonth = "jun";
						}						
						
						if(month == 7) {
							if(locale.toString().equals("pt_BR") || locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX") || locale.toString().equals("en_US"))
							          rsMonth = "jul";			
						}
						
						if(month == 8) {
							if(locale.toString().equals("pt_BR") || locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX"))
							   rsMonth = "ago";
					
							if(locale.toString().equals("en_US"))
							   rsMonth = "aug";
						}
						
						if(month == 9) {
							if(locale.toString().equals("pt_BR"))
							   rsMonth = "set";
						
							if(locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX") || locale.toString().equals("en_US"))
							   rsMonth = "sep";
					
						}
						
						if(month == 10) {
							if(locale.toString().equals("pt_BR"))
							   rsMonth = "out";
						
							if(locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX") || locale.toString().equals("en_US"))
							          rsMonth = "oct";					
						}
						
						if(month == 11) {
							if(locale.toString().equals("pt_BR") || locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX") || locale.toString().equals("en_US"))
							            rsMonth = "nov";
						}
						
						if(month == 12) {
							if(locale.toString().equals("pt_BR"))
							   rsMonth = "dez";
						
							if(locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX"))
							   rsMonth = "dic";
					
							if(locale.toString().equals("en_US"))
							   rsMonth = "dec";
						}					
						
						return rsMonth;			
					}
					
					
					//Abrevia��o do M�s
					public String translateMinutes(String minutes) {
						
						System.out.println(minutes);

						LanguageBean language = new LanguageBean();	
						Locale locale = language.getLocale(); 
						
						String rsMinutes = "";
						
						if(minutes.equals("05 minutes")) {
							if(locale.toString().equals("pt_BR") || locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX"))
							   rsMinutes = "05 minutos";
						
							if(locale.toString().equals("en_US"))
								rsMinutes = "05 minutes";						
						}
						
						if(minutes.equals("06 minutes")) {
							if(locale.toString().equals("pt_BR") || locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX"))
							   rsMinutes = "06 minutos";
						
							if(locale.toString().equals("en_US"))
								 rsMinutes = "06 minutes";						
						}
						
						if(minutes.equals("15 minutes")) {
							if(locale.toString().equals("pt_BR") || locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX"))
							   rsMinutes = "15 minutos";
						
							if(locale.toString().equals("en_US"))
								 rsMinutes = "15 minutes";						
						}
						
						if(minutes.equals("01 hour")) {
							if(locale.toString().equals("pt_BR") || locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX"))
							   rsMinutes = "01 hora";
						
							if(locale.toString().equals("en_US"))
								 rsMinutes = "01 hour";						
						}
						
						if(minutes.equals("06 hours")) {
							if(locale.toString().equals("pt_BR") || locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX"))
							   rsMinutes = "06 horas";
						
							if(locale.toString().equals("en_US"))
								 rsMinutes = "06 hours";						
						}
						
						if(minutes.equals("24 hours")) {
							if(locale.toString().equals("pt_BR") || locale.toString().equals("es_ES") || locale.toString().equals("es_AR") ||
									locale.toString().equals("es_MX"))
							   rsMinutes = "24 horas";
						
							if(locale.toString().equals("en_US"))
								 rsMinutes = "24 hours";						
						}					
						
						return rsMinutes;			
					}
					
					
					
					public int daysDifference(String dtInit, String dtEnd, int indice) throws ParseException {

						String dataIni = "";
						String dataEnd = "";

						int days=0, difference=0;

						dataIni = StringToStringDateFormat(dtInit);
						dataEnd = StringToStringDateFormat(dtEnd);

						days = (int) diferencaDias(dataIni, dataEnd);
						difference = (days * indice);		

						return difference;
					}	
					
					
					//Retorna DateTime atual do Sistema
					public String currentTime() {
						
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						return sdf.format(new Date(System.currentTimeMillis()));
						
					}
					
					
					//NOVO 13/11/2019
					
					public int lengthByPeriod(String periodo) {
						 
						 int tam = 0;
						 
						 if(periodo.equals("05 minutes"))
						     tam = 288;
						    if(periodo.equals("06 minutes"))
						         tam = 240;
							    if(periodo.equals("15 minutes"))
									tam = 96;
									   if(periodo.equals("01 hour"))
										   tam = 24;
									   if(periodo.equals("06 hours"))
										     tam = 4;
										     if(periodo.equals("24 hours"))
											   tam = 1;
						 		 
						      return tam;
						 
					 }
										
					public String[] contagemHorario(int tamanho, int range) {

						int k = 0, inc = 0, dia = 1;
						String horaInicio = " ";
						String horaFim = " ";
							
						hora = new String[tamanho+1];
						int i = 0;
							
						//System.out.println("tam: "+tamanho);
						
						while( i <= tamanho) {
							
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
	    
						    if( i == ((dia * range) + inc)) {
						    	 hora[i] = "TOTAL"; 						      
						         i++;
						    	 dia++;						         
						         inc++;						         
						         hora[i] = horaInicio + " - " + horaFim;						         						         
						    }
						    
						    else hora[i] = horaInicio + " - " + horaFim;
						         
						    k++; i++;							    			   
						}
						    
						return hora;
					}
					
					
					
				// ***** NOVO CONTAGEM BEAN ****** //
					
					public String[] fillDateHourlyCount(String dtInicio, String dtFim, int tamanho, int periodo) {

						String da, mth; // Formatar apresenta��o
						
						data = new String[tamanho];
						
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
					
						for (int i = 0; i < tamanho; i += periodo) {

							// Caso o dia seja maior que total de dias no m�s
							if (dia > daysInMonth) {
								dia = 1;  
							    mes++; // incrementa o m�s
							}
							
							if (mes > 12) {
								mes = 1;
								ano++;
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

							data[i] = da + dia + "/" + mth + mes + "/" + ano; // Preenche os dados
							dia++; // Incrementar o dia at� limite do intervalo entre dias	
													
						}
						
						return data;
					}
					
					public String[] fillDateDailyCount(String dtInicio, String dtFim, int tamanho, int periodo) {

						String da, mth; // Formatar apresenta��o
						
						data = new String[tamanho];
						
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
						YearMonth yearMonthNew;

						// Preencher o n�mero de posi��es proporcional ao intervalo de 15 minutos (4
						// intervalos por hora x 24 horas)
						for (int i = 0; i < tamanho; i += periodo) {

							// Caso o dia seja maior que total de dias no m�s
							if (dia > daysInMonth) {
								dia = 1;  
							    mes++; // incrementa o m�s
							}
							
							if (mes > 12) {
								mes = 1;
								ano++;
							} 
							
							// Quantos dias o m�s possui - atualiza os dados
							yearMonthNew = YearMonth.of(ano, mes);
							daysInMonth = yearMonthNew.lengthOfMonth();
						
							// Formata apresenta��o da String
							if (dia <= 9)
								da = "0";
							else
								da = "";
							if (mes < 10)
								mth = "0";
							else
								mth = "";

							data[i] = da + dia + "/" + mth + mes + "/" + ano; // Preenche os dados
							maxDay = i;													
							dia++; // Incrementar o dia at� limite do intervalo entre dias							
						}
						
						data[maxDay] = "TOTAL";	
						
						return data;
					}
										
					// Excel SpreadSheet Cabe�alho e Folhas
					public void fillExcelSheetName(String dtInicio, String dtFim, String[] data_excel, int tam) {
								
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

						// Novo Objeto - auxiliar
						YearMonth yearMonthNew;

						// Preencher o n�mero de posi��es proporcional ao intervalo de 15 minutos (4
						// intervalos por hora x 24 horas)
						for (int i = 0; i < tam; i++) {

							// Caso o dia seja maior que total de dias no m�s
							if (dia > daysInMonth) {
								dia = 1; // reseta o dia
								mes++; // incrementa o m�s

								if (mes > 12) {
									mes = 1;
									ano++;
								} // caso for verdade - reseta o m�s e incrementa o ano

								// Quantos dias o m�s possui - atualiza os dados
								yearMonthNew = YearMonth.of(ano, mes);
								daysInMonth = yearMonthNew.lengthOfMonth();
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

							data_excel[i] = da + dia + "-" + mth + mes + "-" + ano; // Preenche os dados						
							dia++; // Incrementar o dia at� limite do intervalo entre dias
						}
					}
									
					
					// Excel SpreadSheet Cabe�alho e Folhas
					public void fillExcelDateHeader(String dtInicio, String dtFim, String[] data_cabecalho, int tam) {
								
						String da, mth; // Formatar apresenta��o

						// dia, m�s e ano da dataInicial

						String anoIni = dtInicio.substring(0, 4);
						String mesIni = dtInicio.substring(5, 7);
						String diaIni = dtInicio.substring(8, 10);
											
						int dia = Integer.parseInt(diaIni); // inicializar vari�vel do dia
						int mes = Integer.parseInt(mesIni); // inicializar vari�vel do m�s
						int ano = Integer.parseInt(anoIni); // inicializar vari�vel do ano
												// Quantos dias possui o respectivo m�s
						YearMonth yearMonthObject = YearMonth.of(ano, mes);
						int daysInMonth = yearMonthObject.lengthOfMonth();

						// Novo Objeto - auxiliar
						YearMonth yearMonthNew;

						// Preencher o n�mero de posi��es proporcional ao intervalo de 15 minutos (4
						// intervalos por hora x 24 horas)
						for (int i = 0; i < tam; i++) {

							// Caso o dia seja maior que total de dias no m�s
							if (dia > daysInMonth) {
								dia = 1; // reseta o dia
								mes++; // incrementa o m�s

								if (mes > 12) {
									mes = 1;
									ano++;
								} // caso for verdade - reseta o m�s e incrementa o ano

								// Quantos dias o m�s possui - atualiza os dados
								yearMonthNew = YearMonth.of(ano, mes);
								daysInMonth = yearMonthNew.lengthOfMonth();
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
					
							data_cabecalho[i] = da + dia + "/" + mth + mes + "/" + ano; // Preenche os dados
							dia++; // Incrementar o dia at� limite do intervalo entre dias							
						}
					}
					
					public DateTime dateToDateTime(Date date) {
						
						DateTime dateTime = new DateTime(date);						
						return dateTime;						
					}
					
					public LocalDate covertToLocalDate(Date date) {
				       
						if(date == null) return null;
				        return new LocalDate(date);
				    }
										
										 
					 public List<String> dateRange(DateTime start, DateTime end) {
	                     
						 DateTimeFormatter frm = DateTimeFormat.forPattern("dd/MM/yyyy");
  						 
					        List<String> lista = new ArrayList<String>();					        
					        DateTime auxDate = start;
					        				        
					        while(auxDate.isBefore(end) || auxDate.equals(end)) { 				        	
					        						        	
					        	lista.add(frm.print(auxDate));
					            auxDate = auxDate.plusDays(1);
					        }
					        
					        return lista;
					    }
					 
			//NOVO METODO 12/12/2019		 
		     public String[] dateRangeArray(LocalDate start, LocalDate end, int days) {
	                     
				    DateTimeFormatter frm = DateTimeFormat.forPattern("dd/MM/yyyy");
  						 
					String[] dateAux = new String[days];
					
					LocalDate auxDate = start;
								
					int i = 0;
														 
					   while(auxDate.isBefore(end) || auxDate.equals(end)) { 				        	
					        						        	
					        dateAux[i] = frm.print(auxDate);					    
					        auxDate = auxDate.plusDays(1);					       						       					        
					        i++;				       
					   }				   
					        
					    return dateAux;
					}
		     
		           //NOVO M�TODO 12/12/2019
		     
		     //NOVO 13/12/2019
		     
		 	/** M�todo para calcular o n�mero de dias entre duas datas	
		 	 * @param dtInit - data inicial
		 	 * @param dtEnd - data final	 
		 	 * @throws ParseException 
		 	 * @return int - retorna diferen�a entre duas datas */
		 	
		 	public int daysHourDifference(String dtInit, String dtEnd) throws ParseException {
		 		
		 		DateMethods dtm = new DateMethods();
		 		 
		 		int days=0, difference=0;
		 		
		 		dtInit = dtm.StringToStringDateFormat(dtInit);
		 		dtEnd = dtm.StringToStringDateFormat(dtEnd);

		 		days = (int) dtm.diferencaDias(dtInit, dtEnd);
		 		difference = (days * 24);		
		 		
		 		return difference;
		 	}
		 	
		 	  //NOVO
					
				 // ***** NOVO CONTAGEM BEAN ****** //	
		 	
		 	  public String fillExcelPeriodos(String selectedPeriodo) {
				   
				   lang = new LanguageBean();
				   Locale locale = lang.getLocale(); 
				   resourceBundle = ResourceBundle.getBundle("br.com.estacao.sat.messages_"+locale.toString());
					
					String periodo = "";
					
					if(selectedPeriodo.equals("05 minutes"))
						periodo = resourceBundle.getString("minute5");
					
					if(selectedPeriodo.equals("06 minutes"))
						periodo =  resourceBundle.getString("minute6");
					
					if(selectedPeriodo.equals("15 minutes"))
						periodo = resourceBundle.getString("minute15");
					
					if(selectedPeriodo.equals("30 minutes"))
						periodo = resourceBundle.getString("minute30");	
					
					if(selectedPeriodo.equals("01 hour"))
						periodo = resourceBundle.getString("hour1");
					
					if(selectedPeriodo.equals("06 hours"))
						periodo = resourceBundle.getString("hour6");
					
					if(selectedPeriodo.equals("24 hours"))
						periodo = resourceBundle.getString("hour24");			
											
					
					return periodo;					
				}
						
		 	
 }









