package br.com.tracevia.webapp.util;

public class QueriesReportsModels {

	/**
	 * @author Wellington 29/08/2020
	 */	

	///////////////////
	//CONST
	/////////////////

	//CRIAÇÃO DE PROCEDURES
	private static final String CALL_PROCEDURE_05_MIN = "CALL tracevia_app.TemporaryDateTime05( ?, ?)";
	private static final String CALL_PROCEDURE_06_MIN = "CALL tracevia_app.TemporaryDateTime06( ?, ?)";
	private static final String CALL_PROCEDURE_10_MIN = "CALL tracevia_app.TemporaryDateTime10( ?, ?)";
	private static final String CALL_PROCEDURE_15_MIN = "CALL tracevia_app.TemporaryDateTime15( ?, ?)";
	private static final String CALL_PROCEDURE_30_MIN = "CALL tracevia_app.TemporaryDateTime30( ?, ?)";
	private static final String CALL_PROCEDURE_01_HOUR = "CALL tracevia_app.TemporaryDateTimeHour( ?, ?)";
	private static final String CALL_PROCEDURE_06_HOURS = "CALL tracevia_app.TemporaryDateTimeSixHours( ?, ?)";	
	private static final String CALL_PROCEDURE_DAYS = "CALL tracevia_app.TemporaryDates( ?, ?)";


	//CABEÇALHO QUERIES
	private static final String QUERY_HEADER_05_MIN = "SELECT DATE_FORMAT(tmp.datetime_05, '%d/%m/%Y') AS data, " + 
			"CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(tmp.datetime_05) - TIME_TO_SEC(tmp.datetime_05)%(05*60))),'%H:%i'), ' - '), " +
			"DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(tmp.datetime_05) + 240 ) - TIME_TO_SEC(tmp.datetime_05)%(05*60))),'%H:%i')) AS intervals, ";

	private static final String QUERY_HEADER_06_MIN = "SELECT DATE_FORMAT(tmp.datetime_06, '%d/%m/%Y') AS data, " + 
			"CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(tmp.datetime_06) - TIME_TO_SEC(tmp.datetime_06)%(06*60))),'%H:%i'), ' - '), " +
			"DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(tmp.datetime_06) + 300 ) - TIME_TO_SEC(tmp.datetime_06)%(06*60))),'%H:%i')) AS intervals, ";

	private static final String QUERY_HEADER_10_MIN = "SELECT DATE_FORMAT(tmp.datetime_10, '%d/%m/%Y') AS data, " + 
			"CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(tmp.datetime_10) - TIME_TO_SEC(tmp.datetime_10)%(10*60))),'%H:%i'), ' - '), " +
			"DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(tmp.datetime_10) + 540 ) - TIME_TO_SEC(tmp.datetime_10)%(10*60))),'%H:%i')) AS intervals, ";

	private static final String QUERY_HEADER_15_MIN = "SELECT DATE_FORMAT(tmp.datetime_15, '%d/%m/%Y') AS data, " + 
			"CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(tmp.datetime_15) - TIME_TO_SEC(tmp.datetime_15)%(15*60))),'%H:%i'), ' - '), " +
			"DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(tmp.datetime_15) + 840 ) - TIME_TO_SEC(tmp.datetime_15)%(15*60))),'%H:%i')) AS intervals, ";

	private static final String QUERY_HEADER_30_MIN = "SELECT DATE_FORMAT(tmp.datetime_30, '%d/%m/%Y') AS data, " + 
			"CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(tmp.datetime_30) - TIME_TO_SEC(tmp.datetime_30)%(30*60))),'%H:%i'), ' - '), " +
			"DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(tmp.datetime_30) + 1740 ) - TIME_TO_SEC(tmp.datetime_30)%(30*60))),'%H:%i')) AS intervals, ";

	private static final String QUERY_HEADER_01_HOUR = "SELECT DATE_FORMAT(tmp.datetime_hour, '%d/%m/%Y') AS data, " + 
			"CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(tmp.datetime_hour) - TIME_TO_SEC(tmp.datetime_hour)%(60*60))),'%H:%i'), ' - '), " +
			"DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(tmp.datetime_hour) + 3599 ) - TIME_TO_SEC(tmp.datetime_hour)%(60*60))),'%H:%i')) AS intervals, ";

	private static final String QUERY_HEADER_06_HOURS = "SELECT DATE_FORMAT(tmp.datetime_six_hours, '%d/%m/%Y') AS data, " + 
			"CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(tmp.datetime_six_hours) - TIME_TO_SEC(tmp.datetime_six_hours)%(360*60))),'%H:%i'), ' - '), " +
			"DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(tmp.datetime_six_hours) + 21599 ) - TIME_TO_SEC(tmp.datetime_six_hours)%(360*60))),'%H:%i')) AS intervals, ";

	private static final String QUERY_HEADER_DATE = "SELECT DATE_FORMAT(tmp.date_, '%d/%m/%Y') AS data, CONCAT('', ' ----- ') AS intervals, ";

	private static final String QUERY_HEADER_DAYS = "SELECT DATE_FORMAT(tmp.date_, '%d') AS days, ";

	private static final String QUERY_HEADER_MONTHS = "SELECT DATE_FORMAT(tmp.date_, '%M') AS month, ";

	//FROM TABLES REFERENCE		
	private static final String FROM_TABLE_05_MIN = " FROM temporarydatetime05 as tmp ";
	private static final String FROM_TABLE_06_MIN = " FROM temporarydatetime06 as tmp ";
	private static final String FROM_TABLE_10_MIN = " FROM temporarydatetime10 as tmp ";
	private static final String FROM_TABLE_15_MIN = " FROM temporarydatetime15 as tmp ";
	private static final String FROM_TABLE_30_MIN = " FROM temporarydatetime30 as tmp ";
	private static final String FROM_TABLE_01_HOUR = " FROM temporarydatetimehour as tmp ";
	private static final String FROM_TABLE_06_HOURS = " FROM temporarydatetimesixhours as tmp ";
	private static final String FROM_TABLE_DATE = " FROM temporarydates as tmp " ;

	//LEFT JOIN TABLE REFERENCE
	private static final String LEFT_JOIN_START_SAT = "LEFT JOIN sat_vbv AS st ";
	private static final String LEFT_JOIN_START_SAT_LL = "LEFT JOIN sat_vbv_ll AS st ";
	private static final String LEFT_JOIN_START_SAT_CCR = "LEFT JOIN sat_vbv_ccr AS st ";
	private static final String LEFT_JOIN_START_MTO = "LEFT JOIN weather_station AS st ";

	//LEFT JOIN SAT EQUIPMENT TABLE REFERENCE
	private static final String LEFT_JOIN_END_SAT_EQUIP = "LEFT JOIN sat_equipment as eq ON eq.equip_id = st.siteID ";
	private static final String LEFT_JOIN_END_MTO_EQUIP = "LEFT JOIN mto_equipment as eq ON eq.equip_id = st.station_id ";

	//LEFT JOIN PERIOD 05 MINUTES
	private static final String LEFT_JOIN_CONDITION_05_MIN = "ON DATE(st.datetime_) = DATE(tmp.datetime_05) AND HOUR(st.datetime_) = HOUR(tmp.datetime_05) AND " +
			"((MINUTE(st.datetime_) >= 0 AND MINUTE(st.datetime_) <= 4) = (MINUTE(tmp.datetime_05) = 0) AND " +
			"(MINUTE(st.datetime_) >= 5 AND MINUTE(st.datetime_) <= 9) = (MINUTE(tmp.datetime_05) = 5) AND " +
			"(MINUTE(st.datetime_) >= 10 AND MINUTE(st.datetime_) <= 14) = (MINUTE(tmp.datetime_05) = 10) AND " +
			"(MINUTE(st.datetime_) >= 15 AND MINUTE(st.datetime_) <= 19) = (MINUTE(tmp.datetime_05) = 15) AND " +
			"(MINUTE(st.datetime_) >= 20 AND MINUTE(st.datetime_) <= 24) = (MINUTE(tmp.datetime_05) = 20) AND " +
			"(MINUTE(st.datetime_) >= 25 AND MINUTE(st.datetime_) <= 29) = (MINUTE(tmp.datetime_05) = 25) AND " +
			"(MINUTE(st.datetime_) >= 30 AND MINUTE(st.datetime_) <= 34) = (MINUTE(tmp.datetime_05) = 30) AND " +
			"(MINUTE(st.datetime_) >= 35 AND MINUTE(st.datetime_) <= 39) = (MINUTE(tmp.datetime_05) = 35) AND " +
			"(MINUTE(st.datetime_) >= 40 AND MINUTE(st.datetime_) <= 44) = (MINUTE(tmp.datetime_05) = 40) AND " +
			"(MINUTE(st.datetime_) >= 45 AND MINUTE(st.datetime_) <= 49) = (MINUTE(tmp.datetime_05) = 45) AND " +
			"(MINUTE(st.datetime_) >= 50 AND MINUTE(st.datetime_) <= 54) = (MINUTE(tmp.datetime_05) = 50) AND " +
			"(MINUTE(st.datetime_) >= 55 AND MINUTE(st.datetime_) <= 59) = (MINUTE(tmp.datetime_05) = 55)) ";

	//LEFT JOIN PERIOD 06 MINUTES		
	private static final String LEFT_JOIN_CONDITION_06_MIN = "ON DATE(st.datetime_) = DATE(tmp.datetime_06) AND HOUR(st.datetime_) = HOUR(tmp.datetime_06) AND " + 
			"((MINUTE(st.datetime_) >= 0 AND MINUTE(st.datetime_) <= 5) = (MINUTE(tmp.datetime_06) = 0) AND " +
			"(MINUTE(st.datetime_) >= 6 AND MINUTE(st.datetime_) <= 11) = (MINUTE(tmp.datetime_06) = 6) AND " +
			"(MINUTE(st.datetime_) >= 12 AND MINUTE(st.datetime_) <= 17) = (MINUTE(tmp.datetime_06) = 12) AND " +
			"(MINUTE(st.datetime_) >= 18 AND MINUTE(st.datetime_) <= 23) = (MINUTE(tmp.datetime_06) = 18) AND " +
			"(MINUTE(st.datetime_) >= 24 AND MINUTE(st.datetime_) <= 29) = (MINUTE(tmp.datetime_06) = 24) AND " +
			"(MINUTE(st.datetime_) >= 30 AND MINUTE(st.datetime_) <= 35) = (MINUTE(tmp.datetime_06) = 30) AND " +
			"(MINUTE(st.datetime_) >= 36 AND MINUTE(st.datetime_) <= 41) = (MINUTE(tmp.datetime_06) = 36) AND " +
			"(MINUTE(st.datetime_) >= 42 AND MINUTE(st.datetime_) <= 47) = (MINUTE(tmp.datetime_06) = 42) AND " + 
			"(MINUTE(st.datetime_) >= 48 AND MINUTE(st.datetime_) <= 53) = (MINUTE(tmp.datetime_06) = 48) AND " +
			" (MINUTE(st.datetime_) >= 54 AND MINUTE(st.datetime_) <= 59) = (MINUTE(tmp.datetime_06) = 54)) ";

	//LEFT JOIN PERIOD 10 MINUTES
	private static final String LEFT_JOIN_CONDITION_10_MIN = "ON DATE(st.datetime_) = DATE(tmp.datetime_10) AND HOUR(st.datetime_) = HOUR(tmp.datetime_10) AND " + 
			"((MINUTE(st.datetime_) >= 0 AND MINUTE(st.datetime_) <= 9) = (MINUTE(tmp.datetime_10) = 0) AND "+
			"(MINUTE(st.datetime_) >= 10 AND MINUTE(st.datetime_) <= 19) = (MINUTE(tmp.datetime_10) = 10) AND " +
			"(MINUTE(st.datetime_) >= 20 AND MINUTE(st.datetime_) <= 29) = (MINUTE(tmp.datetime_10) = 20) AND " +
			"(MINUTE(st.datetime_) >= 30 AND MINUTE(st.datetime_) <= 39) = (MINUTE(tmp.datetime_10) = 30) AND " +
			"(MINUTE(st.datetime_) >= 40 AND MINUTE(st.datetime_) <= 49) = (MINUTE(tmp.datetime_10) = 40) AND " +
			"(MINUTE(st.datetime_) >= 50 AND MINUTE(st.datetime_) <= 59) = (MINUTE(tmp.datetime_10) = 50)) ";

	//LEFT JOIN PERIOD 15 MINUTES
	private static final String LEFT_JOIN_CONDITION_15_MIN = "ON DATE(st.datetime_) = DATE(tmp.datetime_15) AND HOUR(st.datetime_) = HOUR(tmp.datetime_15) AND " +
			"((MINUTE(st.datetime_) >= 0 AND MINUTE(st.datetime_) <= 14) = (MINUTE(tmp.datetime_15) = 0) AND " +
			"(MINUTE(st.datetime_) >= 15 AND MINUTE(st.datetime_) <= 29) = (MINUTE(tmp.datetime_15) = 15) AND " +
			"(MINUTE(st.datetime_) >= 30 AND MINUTE(st.datetime_) <= 44) = (MINUTE(tmp.datetime_15) = 30) AND " +
			"(MINUTE(st.datetime_) >= 45 AND MINUTE(st.datetime_) <= 59) = (MINUTE(tmp.datetime_15) = 45)) ";

	//LEFT JOIN PERIOD 30 MINUTES
	private static final String LEFT_JOIN_CONDITION_30_MIN = "ON DATE(st.datetime_) = DATE(tmp.datetime_30) AND HOUR(st.datetime_) = HOUR(tmp.datetime_30) AND " +
			"((MINUTE(st.datetime_) >= 0 AND MINUTE(st.datetime_) <= 29) = (MINUTE(tmp.datetime_30) = 0) AND " +
			"(MINUTE(st.datetime_) >= 30 AND MINUTE(st.datetime_) <= 59) = (MINUTE(tmp.datetime_30) = 30)) ";

	//LEFT JOIN PERIOD 01 HOUR
	private static final String LEFT_JOIN_CONDITION_01_HOUR = "ON DATE(st.datetime_) = DATE(tmp.datetime_hour) AND HOUR(st.datetime_) = HOUR(tmp.datetime_hour)";

	//LEFT JOIN PERIOD 06 HOUR
	private static final String LEFT_JOIN_CONDITION_06_HOURS = "ON DATE(st.datetime_) = DATE(tmp.datetime_six_hours) AND " +
			"((HOUR(st.datetime_) >= 0 AND HOUR(st.datetime_) <= 5) = ( HOUR(tmp.datetime_six_hours) = 0) AND " +
			"(HOUR(st.datetime_) >= 6 AND HOUR(st.datetime_) <= 11) = ( HOUR(tmp.datetime_six_hours) = 6) AND " +
			"(HOUR(st.datetime_) >= 12 AND HOUR(st.datetime_) <= 17) = ( HOUR(tmp.datetime_six_hours) = 12) AND " +
			"(HOUR(st.datetime_) >= 18 AND HOUR(st.datetime_) <= 23) = ( HOUR(tmp.datetime_six_hours) = 18)) ";	

	//LEFT JOIN PERIOD DAYS
	private static final String LEFT_JOIN_CONDITION_DAYS = "ON DAY(st.datetime_) = DAY(tmp.date_) ";

	//LEFT JOIN PERIOD DATES
	private static final String LEFT_JOIN_CONDITION_DATE = "ON DATE(st.datetime_) = DATE(tmp.date_) ";

	//LEFT JOIN PERIOD MONTHS
	private static final String LEFT_JOIN_CONDITION_MONTHS = "ON MONTH(st.datetime_) = MONTH(tmp.date_) ";	

	//QUERIES GROUP AND ORDER BY PERIODS		
	private static final String GROUP_AND_ORDER_TABLE_05_MIN = "GROUP BY tmp.datetime_05, sec_to_time(time_to_sec(st.datetime_)- time_to_sec(st.datetime_)%(05*60)) " +
			"ORDER BY tmp.datetime_05 ASC ";

	private static final String GROUP_AND_ORDER_TABLE_06_MIN = "GROUP BY tmp.datetime_06, sec_to_time(time_to_sec(st.datetime_)- time_to_sec(st.datetime_)%(06*60)) " +
			"ORDER BY tmp.datetime_06 ASC ";

	private static final String GROUP_AND_ORDER_TABLE_10_MIN = "GROUP BY tmp.datetime_10, sec_to_time(time_to_sec(st.datetime_)- time_to_sec(st.datetime_)%(10*60)) " +
			"ORDER BY tmp.datetime_10 ASC ";

	private static final String GROUP_AND_ORDER_TABLE_15_MIN = "GROUP BY tmp.datetime_15, sec_to_time(time_to_sec(st.datetime_)- time_to_sec(st.datetime_)%(15*60)) " +
			"ORDER BY tmp.datetime_15 ASC ";

	private static final String GROUP_AND_ORDER_TABLE_30_MIN = "GROUP BY tmp.datetime_30, sec_to_time(time_to_sec(st.datetime_)- time_to_sec(st.datetime_)%(30*60)) " +
			"ORDER BY tmp.datetime_30 ASC ";

	private static final String GROUP_AND_ORDER_TABLE_01_HOUR = "GROUP BY tmp.datetime_hour, sec_to_time(time_to_sec(st.datetime_)- time_to_sec(st.datetime_)%(60*60)) " +
			"ORDER BY tmp.datetime_hour ASC "; 

	private static final String GROUP_AND_ORDER_TABLE_06_HOURS = "GROUP BY tmp.datetime_six_hours, sec_to_time(time_to_sec(st.datetime_)- time_to_sec(st.datetime_)%(360*60)) " +
			"ORDER BY tmp.datetime_six_hours ASC "; 

	private static final String GROUP_AND_ORDER_TABLE_DAYS = "GROUP BY tmp.date_ " +
			"ORDER BY DAY(tmp.date_) ASC";

	private static final String GROUP_AND_ORDER_TABLE_DATE = "GROUP BY tmp.date_ " +
			"ORDER BY DATE(tmp.date_) ASC";

	private static final String GROUP_AND_ORDER_TABLE_MONTHS = "GROUP BY DAY(tmp.date_) " +
			"ORDER BY DAY(tmp.date_) ASC";

	private static final String GROUP_AND_ORDER_TABLE_MONTH_YEAR = "GROUP BY MONTH(tmp.date_) " +
			"ORDER BY MONTH(tmp.date_) ASC";


	///////////////////
	//CONST
	/////////////////
	
	/////////////////
	//BUILD METHODS
	////////////////

	/**********************************************************************************************************/

	//METODO PARA SELECIONAR PROCEDURES
	public String SelectProcedureByPeriod(String period) {

		if(period.equals("05 minutes"))
			return CALL_PROCEDURE_05_MIN;

		if(period.equals("06 minutes"))
			return CALL_PROCEDURE_06_MIN;

		if(period.equals("10 minutes"))
			return CALL_PROCEDURE_10_MIN;

		if(period.equals("15 minutes"))
			return CALL_PROCEDURE_15_MIN;

		if(period.equals("30 minutes"))
			return CALL_PROCEDURE_30_MIN;

		if(period.equals("01 hour"))
			return CALL_PROCEDURE_01_HOUR;

		if(period.equals("06 hours"))
			return CALL_PROCEDURE_06_HOURS;

		if(period.equals("24 hours") || period.equals("year") || period.equals("month"))
			return CALL_PROCEDURE_DAYS;



		return null;

	}

	/**********************************************************************************************************/

	//METODO PARA SELECIONAR O HEADER DA QUERY
	public String QueryHeader(String period) {

		if(period.equals("05 minutes"))
			return QUERY_HEADER_05_MIN;

		if(period.equals("06 minutes"))
			return QUERY_HEADER_06_MIN;

		if(period.equals("10 minutes"))
			return QUERY_HEADER_10_MIN;

		if(period.equals("15 minutes"))
			return QUERY_HEADER_15_MIN;

		if(period.equals("30 minutes"))
			return QUERY_HEADER_30_MIN;

		if(period.equals("01 hour"))
			return QUERY_HEADER_01_HOUR;

		if(period.equals("06 hours"))
			return QUERY_HEADER_06_HOURS;

		if(period.equals("24 hours"))
			return QUERY_HEADER_DATE;	

		if(period.equals("month"))
			return QUERY_HEADER_DAYS;	

		if(period.equals("year"))
			return QUERY_HEADER_MONTHS;									 	

		return null;
	}

	/**********************************************************************************************************/

	//TABLE FROM  METHOD
	public String QueryFromTable(String period) {

		if(period.equals("05 minutes"))
			return FROM_TABLE_05_MIN;

		if(period.equals("06 minutes"))
			return FROM_TABLE_06_MIN;

		if(period.equals("10 minutes"))
			return FROM_TABLE_10_MIN;

		if(period.equals("15 minutes"))
			return FROM_TABLE_15_MIN;

		if(period.equals("30 minutes"))
			return FROM_TABLE_30_MIN;

		if(period.equals("01 hour"))
			return FROM_TABLE_01_HOUR;

		if(period.equals("06 hours"))
			return FROM_TABLE_06_HOURS;

		if(period.equals("24 hours") || period.equals("year") || period.equals("month"))
			return FROM_TABLE_DATE;


		return null;
	}

	/**********************************************************************************************************/

	//METHOD GROUP BY  AND ORDER BY
	public String QueryGroupAndOrder(String period) {

		if(period.equals("05 minutes"))
			return GROUP_AND_ORDER_TABLE_05_MIN;

		if(period.equals("06 minutes"))
			return GROUP_AND_ORDER_TABLE_06_MIN;

		if(period.equals("10 minutes"))
			return GROUP_AND_ORDER_TABLE_10_MIN;

		if(period.equals("15 minutes"))
			return GROUP_AND_ORDER_TABLE_15_MIN;

		if(period.equals("30 minutes"))
			return GROUP_AND_ORDER_TABLE_30_MIN;

		if(period.equals("01 hour"))
			return GROUP_AND_ORDER_TABLE_01_HOUR;

		if(period.equals("06 hours"))
			return GROUP_AND_ORDER_TABLE_06_HOURS;

		if(period.equals("24 hours"))
			return GROUP_AND_ORDER_TABLE_DATE;

		if(period.equals("month"))
			return GROUP_AND_ORDER_TABLE_MONTHS;

		if(period.equals("year"))
			return GROUP_AND_ORDER_TABLE_MONTH_YEAR;		

		return null;
	}

	/**********************************************************************************************************/

	//METHOD LEFT JOIN TABLE
	public String LeftJoinStart(String module) {

		if(module.equals("sat"))
			return LEFT_JOIN_START_SAT;

		else if(module.equals("mto"))
			return LEFT_JOIN_START_MTO;

		if(module.equals("sat_ll"))
			return LEFT_JOIN_START_SAT_LL;

		if(module.equals("sat_ccr"))
			return LEFT_JOIN_START_SAT_CCR;

		return null;
	}

	/**********************************************************************************************************/

	//METHOD LEFT JOIN EQUIPMENT TABLE
	public String LeftJoinEnd(String module) {

		if(module.equals("sat"))
			return LEFT_JOIN_END_SAT_EQUIP;

		else if(module.equals("mto"))
			return LEFT_JOIN_END_MTO_EQUIP;

		else if(module.equals("sat_ll"))
			return LEFT_JOIN_END_MTO_EQUIP;
		return null;
	}

	/**********************************************************************************************************/

	//METHOD LEFT JOIN PERIOD
	public String LeftJoinCondition(String period) {

		if(period.equals("05 minutes"))
			return LEFT_JOIN_CONDITION_05_MIN;

		if(period.equals("06 minutes"))
			return LEFT_JOIN_CONDITION_06_MIN;

		if(period.equals("10 minutes"))
			return LEFT_JOIN_CONDITION_10_MIN;

		if(period.equals("15 minutes"))
			return LEFT_JOIN_CONDITION_15_MIN;

		if(period.equals("30 minutes"))
			return LEFT_JOIN_CONDITION_30_MIN;

		if(period.equals("01 hour"))
			return LEFT_JOIN_CONDITION_01_HOUR;

		if(period.equals("06 hours"))
			return LEFT_JOIN_CONDITION_06_HOURS;

		if(period.equals("24 hours"))
			return LEFT_JOIN_CONDITION_DATE;

		if(period.equals("month"))
			return LEFT_JOIN_CONDITION_DAYS;

		if(period.equals("year"))
			return LEFT_JOIN_CONDITION_MONTHS;  		

		return null;

	}

	/**********************************************************************************************************/                      

	/////////////////
	//BUILD METHODS
	////////////////
	
	///////////////////
	//BUILD QUERY
	////////////////////

	/* *** Sobrecarga de métodos *** */

	/**
	 * Método para gerar criar uma query modulada
	 * @param queryHeader - Cabeçalho da query
	 * @param queryMain - Conteúdo principal da query
	 * @param queryFromTable - Especificando a tabela
	 * @param queryGroupOrder - Ordenação
	 * @return query
	 */
	public String BuildQuery(String queryHeader, String queryMain, String queryFromTable, String leftJoinStart, String leftJoinCondition, String leftJoinEnd, String queryGroupOrder) { 	   
		return queryHeader.concat(queryMain).concat(queryFromTable).concat(leftJoinStart).concat(leftJoinCondition).concat(leftJoinEnd).concat(queryGroupOrder); 	       
	}    

	///////////////////
	//BUILD QUERY
	////////////////////

}
