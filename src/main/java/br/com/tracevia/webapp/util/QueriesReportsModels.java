package br.com.tracevia.webapp.util;

import br.com.tracevia.webapp.model.global.RoadConcessionaire;

public class QueriesReportsModels {

	/**
	 * @author Wellington 29/08/2020
	 */	

	///////////////////
	//CONST
	/////////////////

	//CRIAÇÃO DE PROCEDURES
	private static final String CALL_PROCEDURE_05_MIN = "CALL TemporaryDateTime05( ?, ?)";
	private static final String CALL_PROCEDURE_06_MIN = "CALL TemporaryDateTime06( ?, ?)";
	private static final String CALL_PROCEDURE_10_MIN = "CALL TemporaryDateTime10( ?, ?)";
	private static final String CALL_PROCEDURE_15_MIN = "CALL TemporaryDateTime15( ?, ?)";
	private static final String CALL_PROCEDURE_30_MIN = "CALL TemporaryDateTime30( ?, ?)";
	private static final String CALL_PROCEDURE_01_HOUR = "CALL TemporaryDateTimeHour( ?, ?)";
	private static final String CALL_PROCEDURE_06_HOURS = "CALL TemporaryDateTimeSixHours( ?, ?)";	
	private static final String CALL_PROCEDURE_DAYS = "CALL TemporaryDates( ?, ?)";

 //CABEÇALHO QUERIES
  private static final String QUERY_HEADER_05_MIN = "SELECT DATE_FORMAT(data, '%Y-%m-%d') AS data, " + 
  "CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(data) - TIME_TO_SEC(data)%(05*60))),'%H:%i'), ' - '), " +
  "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(data) + 240 ) - TIME_TO_SEC(data)%(05*60))),'%H:%i')) AS intervals, ";

  private static final String QUERY_HEADER_06_MIN = "SELECT DATE_FORMAT(data, '%Y-%m-%d') AS data, " + 
  "CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(data) - TIME_TO_SEC(data)%(06*60))),'%H:%i'), ' - '), " +
  "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(data) + 300 ) - TIME_TO_SEC(data)%(06*60))),'%H:%i')) AS intervals, ";

  private static final String QUERY_HEADER_10_MIN = "SELECT DATE_FORMAT(data, '%Y-%m-%d') AS data, " + 
  "CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(data) - TIME_TO_SEC(data)%(10*60))),'%H:%i'), ' - '), " +
  "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(data) + 540 ) - TIME_TO_SEC(data)%(10*60))),'%H:%i')) AS intervals, ";

  private static final String QUERY_HEADER_15_MIN = "SELECT DATE_FORMAT(data, '%Y-%m-%d') AS data, " + 
  "CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(data) - TIME_TO_SEC(data)%(15*60))),'%H:%i'), ' - '), " +
  "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(data) + 840 ) - TIME_TO_SEC(data)%(15*60))),'%H:%i')) AS intervals, ";

  private static final String QUERY_HEADER_30_MIN = "SELECT DATE_FORMAT(data, '%Y-%m-%d') AS data, " + 
  "CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(data) - TIME_TO_SEC(data)%(30*60))),'%H:%i'), ' - '), " +
  "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(data) + 1740 ) - TIME_TO_SEC(data)%(30*60))),'%H:%i')) AS intervals, ";

  private static final String QUERY_HEADER_01_HOUR = "SELECT DATE_FORMAT(data, '%Y-%m-%d') AS data, " + 
  "CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(data) - TIME_TO_SEC(data)%(60*60))),'%H:%i'), ' - '), " +
  "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(data) + 3599 ) - TIME_TO_SEC(data)%(60*60))),'%H:%i')) AS intervals, ";

  private static final String QUERY_HEADER_06_HOURS = "SELECT DATE_FORMAT(data, '%Y-%m-%d') AS data, " + 
  "CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(data) - TIME_TO_SEC(data)%(360*60))),'%H:%i'), ' - '), " +
  "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(data) + 21599 ) - TIME_TO_SEC(data)%(360*60))),'%H:%i')) AS intervals, ";

  private static final String QUERY_HEADER_DATE = "SELECT DATE_FORMAT(data, '%Y-%m-%d') AS data, CONCAT('', ' ----- ') AS intervals, ";
 
  private static final String QUERY_HEADER_DAYS = "SELECT DATE_FORMAT(data, '%d') AS days, ";

  private static final String QUERY_HEADER_MONTHS = "SELECT DATE_FORMAT(data, '%M') AS month, ";
  
  /////////////////////// DATETIME ////////////////////////////////////////
  //CABEÇALHO QUERIES
  private static final String QUERY_HEADER_05_MIN_DATETIME  = "SELECT DATE_FORMAT(datetime_, '%Y-%m-%d') AS data, " + 
  "CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(datetime_) - TIME_TO_SEC(datetime_)%(05*60))),'%H:%i'), ' - '), " +
  "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(datetime_) + 240 ) - TIME_TO_SEC(datetime_)%(05*60))),'%H:%i')) AS intervals, ";

  private static final String QUERY_HEADER_06_MIN_DATETIME  = "SELECT DATE_FORMAT(datetime_, '%Y-%m-%d') AS data, " + 
  "CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(datetime_) - TIME_TO_SEC(datetime_)%(06*60))),'%H:%i'), ' - '), " +
  "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(datetime_) + 300 ) - TIME_TO_SEC(datetime_)%(06*60))),'%H:%i')) AS intervals, ";

  private static final String QUERY_HEADER_10_MIN_DATETIME  = "SELECT DATE_FORMAT(datetime_, '%Y-%m-%d') AS data, " + 
  "CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(datetime_) - TIME_TO_SEC(datetime_)%(10*60))),'%H:%i'), ' - '), " +
  "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(datetime_) + 540 ) - TIME_TO_SEC(datetime_)%(10*60))),'%H:%i')) AS intervals, ";

  private static final String QUERY_HEADER_15_MIN_DATETIME  = "SELECT DATE_FORMAT(datetime_, '%Y-%m-%d') AS data, " + 
  "CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(datetime_) - TIME_TO_SEC(datetime_)%(15*60))),'%H:%i'), ' - '), " +
  "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(datetime_) + 840 ) - TIME_TO_SEC(datetime_)%(15*60))),'%H:%i')) AS intervals, ";

  private static final String QUERY_HEADER_30_MIN_DATETIME = "SELECT DATE_FORMAT(datetime_, '%Y-%m-%d') AS data, " + 
  "CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(datetime_) - TIME_TO_SEC(datetime_)%(30*60))),'%H:%i'), ' - '), " +
  "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(datetime_) + 1740 ) - TIME_TO_SEC(datetime_)%(30*60))),'%H:%i')) AS intervals, ";

  private static final String QUERY_HEADER_01_HOUR_DATETIME  = "SELECT DATE_FORMAT(datetime_, '%Y-%m-%d') AS data, " + 
  "CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(datetime_) - TIME_TO_SEC(datetime_)%(60*60))),'%H:%i'), ' - '), " +
  "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(datetime_) + 3599 ) - TIME_TO_SEC(datetime_)%(60*60))),'%H:%i')) AS intervals, ";

  private static final String QUERY_HEADER_06_HOURS_DATETIME  = "SELECT DATE_FORMAT(datetime_, '%Y-%m-%d') AS data, " + 
  "CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(datetime_) - TIME_TO_SEC(datetime_)%(360*60))),'%H:%i'), ' - '), " +
  "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(datetime_) + 21599 ) - TIME_TO_SEC(datetime_)%(360*60))),'%H:%i')) AS intervals, ";

  private static final String QUERY_HEADER_DATE_DATETIME  = "SELECT DATE_FORMAT(datetime_, '%Y-%m-%d') AS data, CONCAT('', ' ----- ') AS intervals, ";
 
  private static final String QUERY_HEADER_DAYS_DATETIME  = "SELECT DATE_FORMAT(datetime_, '%d') AS days, ";

  private static final String QUERY_HEADER_MONTHS_DATETIME  = "SELECT DATE_FORMAT(datetime_, '%m') AS month, ";
  

	//FROM TABLES REFERENCE		
	private static final String FROM_TABLE_05_MIN = " FROM temporarydatetime05 as tmp ";
	private static final String FROM_TABLE_06_MIN = " FROM temporarydatetime06 as tmp ";
	private static final String FROM_TABLE_10_MIN = " FROM temporarydatetime10 as tmp ";
	private static final String FROM_TABLE_15_MIN = " FROM temporarydatetime15 as tmp ";
	private static final String FROM_TABLE_30_MIN = " FROM temporarydatetime30 as tmp ";
	private static final String FROM_TABLE_01_HOUR = " FROM temporarydatetimehour as tmp ";
	private static final String FROM_TABLE_06_HOURS = " FROM temporarydatetimesixhours as tmp ";
	private static final String FROM_TABLE_DATE = " FROM temporarydates as tmp " ;
	
   //FROM TABLES REFERENCE		
	private static final String FROM_TABLE_05_MIN_LL = " FROM tb_vbv_ll st ";
	private static final String FROM_TABLE_06_MIN_LL = " FROM tb_vbv_ll st ";
	private static final String FROM_TABLE_10_MIN_LL = " FROM tb_vbv_ll st ";
	private static final String FROM_TABLE_15_MIN_LL = " FROM tb_vbv_ll st ";
	private static final String FROM_TABLE_30_MIN_LL = " FROM tb_vbv_ll st ";
	private static final String FROM_TABLE_01_HOUR_LL = " FROM tb_vbv_ll st ";
	private static final String FROM_TABLE_06_HOURS_LL = " FROM tb_vbv_ll st ";
	private static final String FROM_TABLE_DATE_LL = " FROM tb_vbv_ll st " ;
	
    //FROM TABLES REFERENCE		
	private static final String FROM_TABLE_05_MIN_VBV = " FROM tb_vbv st ";
	private static final String FROM_TABLE_06_MIN_VBV = " FROM tb_vbv st ";
	private static final String FROM_TABLE_10_MIN_VBV = " FROM tb_vbv st ";
	private static final String FROM_TABLE_15_MIN_VBV = " FROM tb_vbv st ";
	private static final String FROM_TABLE_30_MIN_VBV = " FROM tb_vbv st ";
	private static final String FROM_TABLE_01_HOUR_VBV = " FROM tb_vbv st ";
	private static final String FROM_TABLE_06_HOURS_VBV = " FROM tb_vbv st ";
	private static final String FROM_TABLE_DATE_VBV = " FROM tb_vbv st ";
	
	//FROM TABLES REFERENCE		
	private static final String FROM_TABLE_05_MIN_CCR = " FROM tb_vbv_ccr st ";
	private static final String FROM_TABLE_06_MIN_CCR = " FROM tb_vbv_ccr st ";
	private static final String FROM_TABLE_10_MIN_CCR = " FROM tb_vbv_ccr st ";
	private static final String FROM_TABLE_15_MIN_CCR = " FROM tb_vbv_ccr st ";
	private static final String FROM_TABLE_30_MIN_CCR = " FROM tb_vbv_ccr st ";
	private static final String FROM_TABLE_01_HOUR_CCR = " FROM tb_vbv_ccr st  ";
	private static final String FROM_TABLE_06_HOURS_CCR = " FROM tb_vbv_ccr st ";
	private static final String FROM_TABLE_DATE_CCR = " FROM tb_vbv_ccr st " ;
	
    //FROM TABLES REFERENCE		
	private static final String FROM_TABLE_05_MIN_SAT_VBV = " FROM sat_vbv st ";
	private static final String FROM_TABLE_06_MIN_SAT_VBV  = " FROM sat_vbv st ";
	private static final String FROM_TABLE_10_MIN_SAT_VBV = " FROM sat_vbv st ";
	private static final String FROM_TABLE_15_MIN_SAT_VBV = " FROM sat_vbv st ";
	private static final String FROM_TABLE_30_MIN_SAT_VBV = " FROM sat_vbv st ";
	private static final String FROM_TABLE_01_HOUR_SAT_VBV = " FROM sat_vbv st ";
	private static final String FROM_TABLE_06_HOURS_SAT_VBV = " FROM sat_vbv st ";
	private static final String FROM_TABLE_DATE_SAT_VBV = " FROM sat_vbv st " ;
	
    //FROM TABLES REFERENCE		
	private static final String FROM_TABLE_05_MIN_SAT_VBV_LL = " FROM sat_vbv_ll st ";
	private static final String FROM_TABLE_06_MIN_SAT_VBV_LL = " FROM sat_vbv_ll st ";
	private static final String FROM_TABLE_10_MIN_SAT_VBV_LL = " FROM sat_vbv_ll st ";
	private static final String FROM_TABLE_15_MIN_SAT_VBV_LL = " FROM sat_vbv_ll st ";
	private static final String FROM_TABLE_30_MIN_SAT_VBV_LL = " FROM sat_vbv_ll st ";
	private static final String FROM_TABLE_01_HOUR_SAT_VBV_LL = " FROM sat_vbv_ll st ";
	private static final String FROM_TABLE_06_HOURS_SAT_VBV_LL = " FROM sat_vbv_ll st ";
	private static final String FROM_TABLE_DATE_SAT_VBV_LL = " FROM sat_vbv_ll st " ;
	
	//FROM TABLES REFERENCE		
	private static final String FROM_TABLE_05_MIN_SAT_VBV_CCR = " FROM sat_vbv_ccr st ";
	private static final String FROM_TABLE_06_MIN_SAT_VBV_CCR = " FROM sat_vbv_ccr st ";
	private static final String FROM_TABLE_10_MIN_SAT_VBV_CCR = " FROM sat_vbv_ccr st ";
	private static final String FROM_TABLE_15_MIN_SAT_VBV_CCR = " FROM sat_vbv_ccr st ";
	private static final String FROM_TABLE_30_MIN_SAT_VBV_CCR = " FROM sat_vbv_ccr st ";
	private static final String FROM_TABLE_01_HOUR_SAT_VBV_CCR = " FROM sat_vbv_ccr st ";
	private static final String FROM_TABLE_06_HOURS_SAT_VBV_CCR = " FROM sat_vbv_ccr st ";
	private static final String FROM_TABLE_DATE_SAT_VBV_CCR = " FROM sat_vbv_ccr st " ;

    //FROM TABLES REFERENCE		
	private static final String FROM_TABLE_MTO = " FROM mto_data st ";	
	private static final String FROM_TABLE_SV = " FROM sv_data st ";

	//LEFT JOIN TABLE REFERENCE
	private static final String LEFT_JOIN_START_SAT = "LEFT JOIN sat_vbv AS st ";
	private static final String LEFT_JOIN_START_TABLE_SAT = "LEFT JOIN tb_vbv AS st ";
	private static final String LEFT_JOIN_START_SAT_TABLE_LL = "LEFT JOIN tb_vbv_ll AS st ";
	private static final String LEFT_JOIN_START_SAT_TABLE_CCR = "LEFT JOIN tb_vbv_ccr AS st ";
	private static final String LEFT_JOIN_START_MTO = "LEFT JOIN mto_data AS st ";

	//LEFT JOIN SAT EQUIPMENT TABLE REFERENCE
	private static final String LEFT_JOIN_END_SAT_EQUIP = "LEFT JOIN sat_equipment as eq ON eq.equip_id = st.siteID ";
	private static final String LEFT_JOIN_END_MTO_EQUIP = "LEFT JOIN mto_equipment as eq ON eq.equip_id = st.station_id ";
	private static final String INNER_JOIN_END_SAT_EQUIP = "INNER JOIN sat_equipment as eq ON eq.equip_id = st.siteID ";
	private static final String INNER_JOIN_END_MTO_EQUIP = "INNER JOIN mto_equipment as eq ON eq.equip_id = st.station_id ";
	private static final String INNER_JOIN_END_SV_EQUIP = "INNER JOIN sv_equipment as eq ON eq.equip_id = st.station_id ";
	
	//LEFT JOIN PERIOD 05 MINUTES
		private static final String LEFT_JOIN_CONDITION_05_MIN = "ON DATE(st.data) = DATE(tmp.datetime_05) AND HOUR(st.data) = HOUR(tmp.datetime_05) AND " +
				"((MINUTE(st.data) >= 0 AND MINUTE(st.data) <= 4) = (MINUTE(tmp.datetime_05) = 0) AND " +
				"(MINUTE(st.data) >= 5 AND MINUTE(st.data) <= 9) = (MINUTE(tmp.datetime_05) = 5) AND " +
				"(MINUTE(st.data) >= 10 AND MINUTE(st.data) <= 14) = (MINUTE(tmp.datetime_05) = 10) AND " +
				"(MINUTE(st.data) >= 15 AND MINUTE(st.data) <= 19) = (MINUTE(tmp.datetime_05) = 15) AND " +
				"(MINUTE(st.data) >= 20 AND MINUTE(st.data) <= 24) = (MINUTE(tmp.datetime_05) = 20) AND " +
				"(MINUTE(st.data) >= 25 AND MINUTE(st.data) <= 29) = (MINUTE(tmp.datetime_05) = 25) AND " +
				"(MINUTE(st.data) >= 30 AND MINUTE(st.data) <= 34) = (MINUTE(tmp.datetime_05) = 30) AND " +
				"(MINUTE(st.data) >= 35 AND MINUTE(st.data) <= 39) = (MINUTE(tmp.datetime_05) = 35) AND " +
				"(MINUTE(st.data) >= 40 AND MINUTE(st.data) <= 44) = (MINUTE(tmp.datetime_05) = 40) AND " +
				"(MINUTE(st.data) >= 45 AND MINUTE(st.data) <= 49) = (MINUTE(tmp.datetime_05) = 45) AND " +
				"(MINUTE(st.data) >= 50 AND MINUTE(st.data) <= 54) = (MINUTE(tmp.datetime_05) = 50) AND " +
				"(MINUTE(st.data) >= 55 AND MINUTE(st.data) <= 59) = (MINUTE(tmp.datetime_05) = 55)) ";

		//LEFT JOIN PERIOD 06 MINUTES		
		private static final String LEFT_JOIN_CONDITION_06_MIN = "ON DATE(st.data) = DATE(tmp.datetime_06) AND HOUR(st.data) = HOUR(tmp.datetime_06) AND " + 
				"((MINUTE(st.data) >= 0 AND MINUTE(st.data) <= 5) = (MINUTE(tmp.datetime_06) = 0) AND " +
				"(MINUTE(st.data) >= 6 AND MINUTE(st.data) <= 11) = (MINUTE(tmp.datetime_06) = 6) AND " +
				"(MINUTE(st.data) >= 12 AND MINUTE(st.data) <= 17) = (MINUTE(tmp.datetime_06) = 12) AND " +
				"(MINUTE(st.data) >= 18 AND MINUTE(st.data) <= 23) = (MINUTE(tmp.datetime_06) = 18) AND " +
				"(MINUTE(st.data) >= 24 AND MINUTE(st.data) <= 29) = (MINUTE(tmp.datetime_06) = 24) AND " +
				"(MINUTE(st.data) >= 30 AND MINUTE(st.data) <= 35) = (MINUTE(tmp.datetime_06) = 30) AND " +
				"(MINUTE(st.data) >= 36 AND MINUTE(st.data) <= 41) = (MINUTE(tmp.datetime_06) = 36) AND " +
				"(MINUTE(st.data) >= 42 AND MINUTE(st.data) <= 47) = (MINUTE(tmp.datetime_06) = 42) AND " + 
				"(MINUTE(st.data) >= 48 AND MINUTE(st.data) <= 53) = (MINUTE(tmp.datetime_06) = 48) AND " +
				" (MINUTE(st.data) >= 54 AND MINUTE(st.data) <= 59) = (MINUTE(tmp.datetime_06) = 54)) ";

		//LEFT JOIN PERIOD 10 MINUTES
		private static final String LEFT_JOIN_CONDITION_10_MIN = "ON DATE(st.data) = DATE(tmp.datetime_10) AND HOUR(st.data) = HOUR(tmp.datetime_10) AND " + 
				"((MINUTE(st.data) >= 0 AND MINUTE(st.data) <= 9) = (MINUTE(tmp.datetime_10) = 0) AND "+
				"(MINUTE(st.data) >= 10 AND MINUTE(st.data) <= 19) = (MINUTE(tmp.datetime_10) = 10) AND " +
				"(MINUTE(st.data) >= 20 AND MINUTE(st.data) <= 29) = (MINUTE(tmp.datetime_10) = 20) AND " +
				"(MINUTE(st.data) >= 30 AND MINUTE(st.data) <= 39) = (MINUTE(tmp.datetime_10) = 30) AND " +
				"(MINUTE(st.data) >= 40 AND MINUTE(st.data) <= 49) = (MINUTE(tmp.datetime_10) = 40) AND " +
				"(MINUTE(st.data) >= 50 AND MINUTE(st.data) <= 59) = (MINUTE(tmp.datetime_10) = 50)) ";

		//LEFT JOIN PERIOD 15 MINUTES
		private static final String LEFT_JOIN_CONDITION_15_MIN = "ON DATE(st.data) = DATE(tmp.datetime_15) AND HOUR(st.data) = HOUR(tmp.datetime_15) AND " +
				"((MINUTE(st.data) >= 0 AND MINUTE(st.data) <= 14) = (MINUTE(tmp.datetime_15) = 0) AND " +
				"(MINUTE(st.data) >= 15 AND MINUTE(st.data) <= 29) = (MINUTE(tmp.datetime_15) = 15) AND " +
				"(MINUTE(st.data) >= 30 AND MINUTE(st.data) <= 44) = (MINUTE(tmp.datetime_15) = 30) AND " +
				"(MINUTE(st.data) >= 45 AND MINUTE(st.data) <= 59) = (MINUTE(tmp.datetime_15) = 45)) ";

		//LEFT JOIN PERIOD 30 MINUTES
		private static final String LEFT_JOIN_CONDITION_30_MIN = "ON DATE(st.data) = DATE(tmp.datetime_30) AND HOUR(st.data) = HOUR(tmp.datetime_30) AND " +
				"((MINUTE(st.data) >= 0 AND MINUTE(st.data) <= 29) = (MINUTE(tmp.datetime_30) = 0) AND " +
				"(MINUTE(st.data) >= 30 AND MINUTE(st.data) <= 59) = (MINUTE(tmp.datetime_30) = 30)) ";

		//LEFT JOIN PERIOD 01 HOUR
		private static final String LEFT_JOIN_CONDITION_01_HOUR = "ON DATE(st.data) = DATE(tmp.datetime_hour) AND HOUR(st.data) = HOUR(tmp.datetime_hour)";

		//LEFT JOIN PERIOD 06 HOUR
		private static final String LEFT_JOIN_CONDITION_06_HOURS = "ON DATE(st.data) = DATE(tmp.datetime_six_hours) AND " +
				"((HOUR(st.data) >= 0 AND HOUR(st.data) <= 5) = ( HOUR(tmp.datetime_six_hours) = 0) AND " +
				"(HOUR(st.data) >= 6 AND HOUR(st.data) <= 11) = ( HOUR(tmp.datetime_six_hours) = 6) AND " +
				"(HOUR(st.data) >= 12 AND HOUR(st.data) <= 17) = ( HOUR(tmp.datetime_six_hours) = 12) AND " +
				"(HOUR(st.data) >= 18 AND HOUR(st.data) <= 23) = ( HOUR(tmp.datetime_six_hours) = 18)) ";	

		//LEFT JOIN PERIOD DAYS
		private static final String LEFT_JOIN_CONDITION_DAYS = "ON DAY(st.data) = DAY(tmp.date_) ";

		//LEFT JOIN PERIOD DATES
		private static final String LEFT_JOIN_CONDITION_DATE = "ON DATE(st.data) = DATE(tmp.date_) ";

		//LEFT JOIN PERIOD MONTHS
		private static final String LEFT_JOIN_CONDITION_MONTHS = "ON MONTH(st.data) = MONTH(tmp.date_) ";	

		//QUERIES GROUP AND ORDER BY PERIODS		
		private static final String GROUP_AND_ORDER_TABLE_05_MIN = "GROUP BY tmp.datetime_05, sec_to_time(time_to_sec(tmp.datetime_05)- time_to_sec(tmp.datetime_05)%(05*60)) " +
				"ORDER BY tmp.datetime_05 ASC ";

		private static final String GROUP_AND_ORDER_TABLE_06_MIN = "GROUP BY tmp.datetime_06, sec_to_time(time_to_sec(tmp.datetime_06)- time_to_sec(tmp.datetime_06)%(06*60)) " +
				"ORDER BY tmp.datetime_06 ASC ";

		private static final String GROUP_AND_ORDER_TABLE_10_MIN = "GROUP BY tmp.datetime_10, sec_to_time(time_to_sec(tmp.datetime_10)- time_to_sec(tmp.datetime_10)%(10*60)) " +
				"ORDER BY tmp.datetime_10 ASC ";

		private static final String GROUP_AND_ORDER_TABLE_15_MIN = "GROUP BY tmp.datetime_15, sec_to_time(time_to_sec(tmp.datetime_15)- time_to_sec(tmp.datetime_15)%(15*60)) " +
				"ORDER BY tmp.datetime_15 ASC ";

		private static final String GROUP_AND_ORDER_TABLE_30_MIN = "GROUP BY tmp.datetime_30, sec_to_time(time_to_sec(tmp.datetime_30)- time_to_sec(tmp.datetime_30)%(30*60)) " +
				"ORDER BY tmp.datetime_30 ASC ";

		private static final String GROUP_AND_ORDER_TABLE_01_HOUR = "GROUP BY tmp.datetime_hour, sec_to_time(time_to_sec(tmp.datetime_hour)- time_to_sec(tmp.datetime_hour)%(60*60)) " +
				"ORDER BY tmp.datetime_hour ASC "; 

		private static final String GROUP_AND_ORDER_TABLE_06_HOURS = "GROUP BY tmp.datetime_six_hours, sec_to_time(time_to_sec(tmp.datetime_six_hours)- time_to_sec(tmp.datetime_six_hours)%(360*60)) " +
				"ORDER BY tmp.datetime_six_hours ASC "; 

		private static final String GROUP_AND_ORDER_TABLE_DAYS = "GROUP BY tmp.date_ " +
				"ORDER BY DAY(tmp.date_) ASC";

		private static final String GROUP_AND_ORDER_TABLE_DATE = "GROUP BY tmp.date_ " +
				"ORDER BY DATE(tmp.date_) ASC";

		private static final String GROUP_AND_ORDER_TABLE_MONTHS = "GROUP BY DAY(tmp.date_) " +
				"ORDER BY DAY(tmp.date_) ASC";

		private static final String GROUP_AND_ORDER_TABLE_MONTH_YEAR = "GROUP BY MONTH(tmp.date_) " +
				"ORDER BY MONTH(tmp.date_) ASC";
		
		
		//QUERIES GROUP AND ORDER BY PERIODSNEW SATS		
		private static final String GROUP_AND_ORDER_TABLE_05_MIN_SAT = "GROUP BY DATE(data), sec_to_time(time_to_sec(data)- time_to_sec(data)%(05*60)) " +
				"ORDER BY DATE(data) ASC ";

		private static final String GROUP_AND_ORDER_TABLE_06_MIN_SAT = "GROUP BY DATE(data), sec_to_time(time_to_sec(data)- time_to_sec(data)%(06*60)) " +
				"ORDER BY DATE(data) ASC ";

		private static final String GROUP_AND_ORDER_TABLE_10_MIN_SAT= "GROUP BY DATE(data), sec_to_time(time_to_sec(data)- time_to_sec(data)%(10*60)) " +
				"ORDER BY DATE(data) ASC ";

		private static final String GROUP_AND_ORDER_TABLE_15_MIN_SAT = "GROUP BY DATE(data), sec_to_time(time_to_sec(data)- time_to_sec(data)%(15*60)) " +
				"ORDER BY DATE(data) ASC ";

		private static final String GROUP_AND_ORDER_TABLE_30_MIN_SAT = "GROUP BY DATE(data), sec_to_time(time_to_sec(data)- time_to_sec(data)%(30*60)) " +
				"ORDER BY DATE(data) ASC ";

		private static final String GROUP_AND_ORDER_TABLE_01_HOUR_SAT = "GROUP BY DATE(data), sec_to_time(time_to_sec(data)- time_to_sec(data)%(60*60)) " +
				"ORDER BY DATE(data) ASC "; 

		private static final String GROUP_AND_ORDER_TABLE_06_HOURS_SAT = "GROUP BY DATE(data), sec_to_time(time_to_sec(data)- time_to_sec(data)%(360*60)) " +
				"ORDER BY DATE(data) ASC "; 

		private static final String GROUP_AND_ORDER_TABLE_DAYS_SAT = "GROUP BY DAY(data) " +
				"ORDER BY DAY(data) ASC";

		private static final String GROUP_AND_ORDER_TABLE_DATE_SAT = "GROUP BY DATE(data) " +
				"ORDER BY DATE(data) ASC";

		private static final String GROUP_AND_ORDER_TABLE_MONTHS_SAT = "GROUP BY DAY(data) " +
				"ORDER BY DAY(data) ASC";

		private static final String GROUP_AND_ORDER_TABLE_MONTH_YEAR_SAT = "GROUP BY MONTH(data) " +
		       "ORDER BY MONTH(data) ASC";
		
		//QUERIES GROUP AND ORDER BY PERIODSNEW MTO		
				private static final String GROUP_AND_ORDER_TABLE_05_MIN_METEO = "GROUP BY DATE(datetime_), intervals " +
						"ORDER BY DATE(datetime_) ASC ";

				private static final String GROUP_AND_ORDER_TABLE_06_MIN_METEO = "GROUP BY DATE(datetime_), intervals " +
						"ORDER BY DATE(datetime_) ASC ";

				private static final String GROUP_AND_ORDER_TABLE_10_MIN_METEO = "GROUP BY DATE(datetime_), intervals " +
						"ORDER BY DATE(datetime_) ASC ";

				private static final String GROUP_AND_ORDER_TABLE_15_MIN_METEO = "GROUP BY DATE(datetime_), intervals " +
						"ORDER BY DATE(datetime_) ASC ";

				private static final String GROUP_AND_ORDER_TABLE_30_MIN_METEO = "GROUP BY DATE(datetime_), intervals " +
						"ORDER BY DATE(datetime_) ASC ";

				private static final String GROUP_AND_ORDER_TABLE_01_HOUR_METEO = "GROUP BY DATE(datetime_), intervals " +
						"ORDER BY DATE(datetime_) ASC "; 

				private static final String GROUP_AND_ORDER_TABLE_06_HOURS_METEO = "GROUP BY DATE(datetime_), intervals " +
						"ORDER BY DATE(datetime_) ASC "; 

				private static final String GROUP_AND_ORDER_TABLE_DAYS_MTO = "GROUP BY DAY(datetime_) " +
						"ORDER BY DAY(datetime_) ASC";

				private static final String GROUP_AND_ORDER_TABLE_DATE_METEO = "GROUP BY DATE(datetime_) " +
						"ORDER BY DATE(datetime_) ASC";

				private static final String GROUP_AND_ORDER_TABLE_MONTHS_METEO = "GROUP BY DAY(datetime_) " +
						"ORDER BY DAY(datetime_) ASC";

				private static final String GROUP_AND_ORDER_TABLE_MONTH_YEAR_METEO = "GROUP BY MONTH(datetime_) " +
				       "ORDER BY MONTH(datetime_) ASC";
		
		//INDEX
		public static final String USE_INDEX_IDX_DATE_SITEID = "USE INDEX(idx_date_siteID) ";
		
		public static final String USE_INDEX_SITE_ID = "USE INDEX(siteID_data) ";
		
		public static final String USE_INDEX_IDX_SITEID_DATA = "USE INDEX(idx_siteID_data) ";
		
		public static final String USE_INDEX_IDX_DATETIME_STATION = "USE INDEX(idx_datetime_station) ";
				
		
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

	//METODO PARA SELECIONAR O HEADER DA QUERY
	public String QueryDateTimeHeader(String period) {

		if(period.equals("05 minutes"))
			return QUERY_HEADER_05_MIN_DATETIME;

		if(period.equals("06 minutes"))
			return QUERY_HEADER_06_MIN_DATETIME;

		if(period.equals("10 minutes"))
			return QUERY_HEADER_10_MIN_DATETIME;

		if(period.equals("15 minutes"))
			return QUERY_HEADER_15_MIN_DATETIME;

		if(period.equals("30 minutes"))
			return QUERY_HEADER_30_MIN_DATETIME;

		if(period.equals("01 hour"))
			return QUERY_HEADER_01_HOUR_DATETIME;

		if(period.equals("06 hours"))
			return QUERY_HEADER_06_HOURS_DATETIME;

		if(period.equals("24 hours"))
			return QUERY_HEADER_DATE_DATETIME;

		if(period.equals("month"))
			return QUERY_HEADER_DAYS_DATETIME;	

		if(period.equals("year"))
			return QUERY_HEADER_MONTHS_DATETIME;							 	

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
	
	
	//TABLE FROM  METHOD SELECTION
		public String QueryFromSatTable(String period, String table) {

			if(period.equals("05 minutes")) {
								
				   if(table.equals("tb_vbv"))
					 return FROM_TABLE_05_MIN_VBV;
					
					else if(table.equals("tb_vbv_ll"))
						 return FROM_TABLE_05_MIN_LL;
						
						else if(table.equals("tb_vbv_ccr"))
							 return FROM_TABLE_05_MIN_CCR;
				
						 else if(table.equals("sat_vbv"))
							 return FROM_TABLE_05_MIN_SAT_VBV;
							
							else if(table.equals("sat_vbv_ll"))
								 return FROM_TABLE_05_MIN_SAT_VBV_LL;
								
								else if(table.equals("sat_vbv_ccr"))
									 return FROM_TABLE_05_MIN_SAT_VBV_CCR;
							 
			        }

			if(period.equals("06 minutes")) {
				
				 if(table.equals("tb_vbv"))
					 return FROM_TABLE_06_MIN_VBV;
					
					else if(table.equals("tb_vbv_ll"))
						 return FROM_TABLE_06_MIN_LL;
						
						else if(table.equals("tb_vbv_ccr"))
							 return FROM_TABLE_06_MIN_CCR;
				
						 else if(table.equals("sat_vbv"))
							 return FROM_TABLE_06_MIN_SAT_VBV;
							
							else if(table.equals("sat_vbv_ll"))
								 return FROM_TABLE_06_MIN_SAT_VBV_LL;
								
								else if(table.equals("sat_vbv_ccr"))
									 return FROM_TABLE_06_MIN_SAT_VBV_CCR;
			}
				

			if(period.equals("10 minutes")) {				
				
				 if(table.equals("tb_vbv"))
					 return FROM_TABLE_10_MIN_VBV;
					
					else if(table.equals("tb_vbv_ll"))
						 return FROM_TABLE_10_MIN_LL;
						
						else if(table.equals("tb_vbv_ccr"))
							 return FROM_TABLE_10_MIN_CCR;
				
						 else if(table.equals("sat_vbv"))
							 return FROM_TABLE_10_MIN_SAT_VBV;
							
							else if(table.equals("sat_vbv_ll"))
								 return FROM_TABLE_10_MIN_SAT_VBV_LL;
								
								else if(table.equals("sat_vbv_ccr"))
									 return FROM_TABLE_10_MIN_SAT_VBV_CCR;
			               }
				

			if(period.equals("15 minutes")) {
				 
				if(table.equals("tb_vbv"))
					 return FROM_TABLE_15_MIN_VBV;
					
					else if(table.equals("tb_vbv_ll"))
						 return FROM_TABLE_15_MIN_LL;
						
						else if(table.equals("tb_vbv_ccr"))
							 return FROM_TABLE_15_MIN_CCR;
				
						 else if(table.equals("sat_vbv"))
							 return FROM_TABLE_15_MIN_SAT_VBV;
							
							else if(table.equals("sat_vbv_ll"))
								 return FROM_TABLE_15_MIN_SAT_VBV_LL;
								
								else if(table.equals("sat_vbv_ccr"))
									 return FROM_TABLE_15_MIN_SAT_VBV_CCR;
			}

			if(period.equals("30 minutes")) {
				
				 if(table.equals("tb_vbv"))
					 return FROM_TABLE_30_MIN_VBV;
					
					else if(table.equals("tb_vbv_ll"))
						 return FROM_TABLE_30_MIN_LL;
						
						else if(table.equals("tb_vbv_ccr"))
							 return FROM_TABLE_30_MIN_CCR;
				
						 else if(table.equals("sat_vbv"))
							 return FROM_TABLE_30_MIN_SAT_VBV;
							
							else if(table.equals("sat_vbv_ll"))
								 return FROM_TABLE_30_MIN_SAT_VBV_LL;
								
								else if(table.equals("sat_vbv_ccr"))
									 return FROM_TABLE_30_MIN_SAT_VBV_CCR;
			}
			

			if(period.equals("01 hour")) {
				
				 if(table.equals("tb_vbv"))
					 return FROM_TABLE_01_HOUR_VBV;
					
					else if(table.equals("tb_vbv_ll"))
						 return FROM_TABLE_01_HOUR_LL;
						
						else if(table.equals("tb_vbv_ccr"))
							 return FROM_TABLE_01_HOUR_CCR;
				
						 else if(table.equals("sat_vbv"))
							 return FROM_TABLE_01_HOUR_SAT_VBV;
							
							else if(table.equals("sat_vbv_ll"))
								 return FROM_TABLE_01_HOUR_SAT_VBV_LL;
								
								else if(table.equals("sat_vbv_ccr"))
									 return FROM_TABLE_01_HOUR_SAT_VBV_CCR;
			}

			if(period.equals("06 hours")) {		
				
				 if(table.equals("tb_vbv"))
					 return FROM_TABLE_06_HOURS_VBV;
					
					else if(table.equals("tb_vbv_ll"))
						 return FROM_TABLE_06_HOURS_LL;
						
						else if(table.equals("tb_vbv_ccr"))
							 return FROM_TABLE_06_HOURS_CCR;
				
						 else if(table.equals("sat_vbv"))
							 return FROM_TABLE_06_HOURS_SAT_VBV;
							
							else if(table.equals("sat_vbv_ll"))
								 return FROM_TABLE_06_HOURS_SAT_VBV_LL;
								
								else if(table.equals("sat_vbv_ccr"))
									 return FROM_TABLE_06_HOURS_SAT_VBV_CCR;
				
			       }
				

			if(period.equals("24 hours") || period.equals("year") || period.equals("month")) {
				 
			
					   if(table.equals("tb_vbv"))
						 return FROM_TABLE_DATE_VBV;
						
						else if(table.equals("tb_vbv_ll"))
							 return FROM_TABLE_DATE_LL;
							
							else if(table.equals("tb_vbv_ccr"))
								 return FROM_TABLE_DATE_CCR;
					   
							else if(table.equals("sat_vbv"))
							   return FROM_TABLE_DATE_SAT_VBV;
					   
							else if(table.equals("sat_vbv_ll"))
								 return FROM_TABLE_DATE_SAT_VBV_LL;
								
								else if(table.equals("sat_vbv_ccr"))
									 return FROM_TABLE_DATE_SAT_VBV_CCR;
								
			     }
			
			return null;
		}
		
		//TABLE FROM  METHOD SELECTION
				public String QueryFromMeteoTable(String period, String table) {
					
					if(table.equals("mto_data")) 
						return FROM_TABLE_MTO;

					if(table.equals("sv_data"))
						return FROM_TABLE_SV;
					
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
	
	//METHOD GROUP BY  AND ORDER BY
	public String QuerySatGroupAndOrder(String period) {

		if(period.equals("05 minutes"))
			return GROUP_AND_ORDER_TABLE_05_MIN_SAT;

		if(period.equals("06 minutes"))
			return GROUP_AND_ORDER_TABLE_06_MIN_SAT;

		if(period.equals("10 minutes"))
			return GROUP_AND_ORDER_TABLE_10_MIN_SAT;

		if(period.equals("15 minutes"))
			return GROUP_AND_ORDER_TABLE_15_MIN_SAT;

		if(period.equals("30 minutes"))
			return GROUP_AND_ORDER_TABLE_30_MIN_SAT;

		if(period.equals("01 hour"))
			return GROUP_AND_ORDER_TABLE_01_HOUR_SAT;

		if(period.equals("06 hours"))
			return GROUP_AND_ORDER_TABLE_06_HOURS_SAT;

		if(period.equals("24 hours"))
			return GROUP_AND_ORDER_TABLE_DATE_SAT;

		if(period.equals("month"))
			return GROUP_AND_ORDER_TABLE_MONTHS_SAT;

		if(period.equals("year"))
			return GROUP_AND_ORDER_TABLE_MONTH_YEAR_SAT;		

		return null;
	}
	
	/**********************************************************************************************************/

	//METHOD GROUP BY  AND ORDER BY
		public String QueryWeatherGroupAndOrder(String period) {

			if(period.equals("05 minutes"))
				return GROUP_AND_ORDER_TABLE_05_MIN_METEO;

			if(period.equals("06 minutes"))
				return GROUP_AND_ORDER_TABLE_06_MIN_METEO;

			if(period.equals("10 minutes"))
				return GROUP_AND_ORDER_TABLE_10_MIN_METEO;

			if(period.equals("15 minutes"))
				return GROUP_AND_ORDER_TABLE_15_MIN_METEO;

			if(period.equals("30 minutes"))
				return GROUP_AND_ORDER_TABLE_30_MIN_METEO;

			if(period.equals("01 hour"))
				return GROUP_AND_ORDER_TABLE_01_HOUR_METEO;

			if(period.equals("06 hours"))
				return GROUP_AND_ORDER_TABLE_06_HOURS_METEO;

			if(period.equals("24 hours"))
				return GROUP_AND_ORDER_TABLE_DATE_METEO;

			if(period.equals("month"))
				return GROUP_AND_ORDER_TABLE_MONTHS_METEO;

			if(period.equals("year"))
				return GROUP_AND_ORDER_TABLE_MONTH_YEAR_METEO;		

			return null;
		}
		
	//METHOD LEFT JOIN TABLE
	public String LeftJoinStart(String module) {

		if(module.equals("sat_vbv"))
			return LEFT_JOIN_START_SAT;

		if(module.equals("mto"))
			return LEFT_JOIN_START_MTO;
		
		if(module.equals("tb_vbv"))
			return LEFT_JOIN_START_TABLE_SAT;

		if(module.equals("tb_vbv_ll"))
			return LEFT_JOIN_START_SAT_TABLE_LL;

		if(module.equals("tb_vbv_ccr"))
			return LEFT_JOIN_START_SAT_TABLE_CCR;
	
		return null;
	}

	/**********************************************************************************************************/

	//METHOD LEFT JOIN EQUIPMENT TABLE
	public String LeftJoinEnd(String module) {

		if(module.equals("sat"))
			return LEFT_JOIN_END_SAT_EQUIP;

		else if(module.equals("mto"))
			return LEFT_JOIN_END_MTO_EQUIP;

		return null;
	}
	
	public String innerJoinSat() {
		return INNER_JOIN_END_SAT_EQUIP;
		
	}
	
	public String innerJoinMto() {
		return INNER_JOIN_END_MTO_EQUIP;
		
	}
	
	public String innerJoinSv() {
		return INNER_JOIN_END_SV_EQUIP;
		
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
	
	public String useIndex(String index) {		 
		 return index;
		
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
	//public String BuildQuery(String queryHeader, String queryMain, String queryFromTable, String leftJoinStart, String leftJoinCondition, String leftJoinEnd, String queryGroupOrder) { 	   
	//	return queryHeader.concat(queryMain).concat(queryFromTable).concat(leftJoinStart).concat(leftJoinCondition).concat(leftJoinEnd).concat(queryGroupOrder); 	       
	//}    

	///////////////////
	//BUILD QUERY
	////////////////////
	
      ///////////////////
     // USE INDEX
    //////////////////
	
	///////// WHERE CLAUSE WITH TWO PARAMETERS	
	public String BuildQueryIndex(String queryHeader, String queryMain, String queryFromTable, String queryIndex, String whereClause, String whereVechiles, String queryGroupOrder) { 	   
		return queryHeader.concat(queryMain).concat(queryFromTable).concat(queryIndex).concat(whereClause).concat(whereVechiles).concat(queryGroupOrder); 	       
	} 
	
  ///////// INNER JOIN AND WHERE CLAUSE WITH ONE PARAMETER
   public String BuildQueryIndexType2(String queryHeader, String queryMain, String queryFromTable, String queryIndex, String innerJoin, String whereClause, String queryGroupOrder) { 	
	   	return queryHeader.concat(queryMain).concat(queryFromTable).concat(queryIndex).concat(innerJoin).concat(whereClause).concat(queryGroupOrder); 	       
  } 
   	
   ///////// WHERE CLAUSE WITH ONE PARAMETER
    public String BuildQueryIndexType3(String queryHeader, String queryMain, String queryFromTable, String queryIndex, String whereClause, String queryGroupOrder) { 	   
 	return queryHeader.concat(queryMain).concat(queryFromTable).concat(queryIndex).concat(whereClause).concat(queryGroupOrder); 	       
   } 
    
    ///////////////////
    // WITHOUT INDEX
    //////////////////
    

	///////// WHERE CLAUSE WITH TWO PARAMETERS	
	public String BuildQuery(String queryHeader, String queryMain, String queryFromTable, String whereClause, String whereVechiles, String queryGroupOrder) { 	   
		return queryHeader.concat(queryMain).concat(queryFromTable).concat(whereClause).concat(whereVechiles).concat(queryGroupOrder); 	       
	} 
	
  ///////// INNER JOIN AND WHERE CLAUSE WITH ONE PARAMETER
   public String BuildQueryType2(String queryHeader, String queryMain, String queryFromTable, String innerJoin, String whereClause, String queryGroupOrder) { 	   
	return queryHeader.concat(queryMain).concat(queryFromTable).concat(innerJoin).concat(whereClause).concat(queryGroupOrder); 	       
  } 
   	
   ///////// WHERE CLAUSE WITH ONE PARAMETER
    public String BuildQueryType3(String queryHeader, String queryMain, String queryFromTable, String whereClause, String queryGroupOrder) { 	   
 	return queryHeader.concat(queryMain).concat(queryFromTable).concat(whereClause).concat(queryGroupOrder); 	       
   } 
	
    
   ///////////////////
   //BUILD QUERY
   ////////////////////
	
	
	 public String whereClauseDate(String startDate, String endDate) {
  	   
  	   String query = "";
  	   
  	   query =" WHERE data between '"+startDate+"' AND '"+endDate+"' ";
  	   
  	   
  	   return query;
  	   
     }
	 
	  public String whereClauseWeatherEquipDate(String station, String startDate, String endDate) {
		  			  	   
	  	   String query = "";
	  	   
	  	   query =" WHERE station_id = '"+station+"' AND datetime_ between '"+startDate+"' AND '"+endDate+"' ";
	  	   
	  	   
	  	   return query;
	  	   
	     }
     
     
       public String whereClauseEquipDate(String siteID, String startDate, String endDate) {
  	   
  	   String query = "";
  	   
  	   query =" WHERE siteID = '"+siteID+"' AND data between '"+startDate+"' AND '"+endDate+"' ";
  	   
  	   
  	   return query;
  	   
     }
     
     
     
     
     public String vehicleSelectionWhereClause(String[] vehicles) {
  	   
  	   String query ="";
  	             	   
  	   if(vehicles.length == 1) {
  		   if(vehicles[0].equals("1"))
  				query += " AND classe = '"+RoadConcessionaire.classLight+"' ";
  		   
  		   if(vehicles[0].equals("2"))
 				query += " AND classe = '"+RoadConcessionaire.classMotorcycle+"' ";
  		   
  		   if(vehicles[0].equals("3"))
 				query += " AND classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' ";
  		         		   
  		   
  	   }
  		   
  	  if(vehicles.length == 2) {
  		  if(vehicles[0].equals("1") && vehicles[0].equals("2"))
				   query += " AND classe = '"+RoadConcessionaire.classLight+"' AND classe = '"+RoadConcessionaire.classMotorcycle+"' ";
		   
  		  if(vehicles[0].equals("1") && vehicles[0].equals("3"))
  			  query += " AND classe <> '"+RoadConcessionaire.classMotorcycle+"' ";
  		  
  		  if(vehicles[0].equals("2") && vehicles[0].equals("3"))
  			  query += " AND classe <> '"+RoadConcessionaire.classLight+"' ";
  	  }                			   
  			   
  	   
  	   return query;
     }
     

}
