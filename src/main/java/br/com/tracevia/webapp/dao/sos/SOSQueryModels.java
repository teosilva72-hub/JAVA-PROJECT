package br.com.tracevia.webapp.dao.sos;

public class SOSQueryModels {
	
	// -------------------------------------------------------------------------------------------------------------------------------------------
	// MODELS
	// -------------------------------------------------------------------------------------------------------------------------------------------
			
	 // HEADERS QUERIES
	
	  private static final String SOS_CALLS_QUERY_HEADER_05_MIN = "SELECT DATE_FORMAT(cl.start_date, '%Y-%m-%d') AS start_date, " + 
		"CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(cl.start_date) - TIME_TO_SEC(cl.start_date)%(05*60))),'%H:%i'), ' - '), " +
		"DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(cl.start_date) + 240 ) - TIME_TO_SEC(cl.start_date)%(05*60))),'%H:%i')) AS intervals, ";
	
	  private static final String SOS_CALLS_QUERY_HEADER_06_MIN = "SELECT DATE_FORMAT(cl.start_date, '%Y-%m-%d') AS start_date, " + 
		"CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(cl.start_date) - TIME_TO_SEC(cl.start_date)%(06*60))),'%H:%i'), ' - '), " +
	    "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(cl.start_date) + 300 ) - TIME_TO_SEC(cl.start_date)%(06*60))),'%H:%i')) AS intervals, ";

	  private static final String SOS_CALLS_QUERY_HEADER_10_MIN = "SELECT DATE_FORMAT(cl.start_date, '%Y-%m-%d') AS start_date, " + 
	    "CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(cl.start_date) - TIME_TO_SEC(cl.start_date)%(10*60))),'%H:%i'), ' - '), " +
	    "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(cl.start_date) + 540 ) - TIME_TO_SEC(cl.start_date)%(10*60))),'%H:%i')) AS intervals, ";

	  private static final String SOS_CALLS_QUERY_HEADER_15_MIN = "SELECT DATE_FORMAT(cl.start_date, '%Y-%m-%d') AS start_date, " + 
	    "CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(cl.start_date) - TIME_TO_SEC(cl.start_date)%(15*60))),'%H:%i'), ' - '), " +
		"DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(cl.start_date) + 840 ) - TIME_TO_SEC(cl.start_date)%(15*60))),'%H:%i')) AS intervals, ";

	  private static final String SOS_CALLS_QUERY_HEADER_30_MIN = "SELECT DATE_FORMAT(cl.start_date, '%Y-%m-%d') AS start_date, " + 
		"CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(cl.start_date) - TIME_TO_SEC(cl.start_date)%(30*60))),'%H:%i'), ' - '), " +
		"DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(cl.start_date) + 1740 ) - TIME_TO_SEC(cl.start_date)%(30*60))),'%H:%i')) AS intervals, ";

	  private static final String SOS_CALLS_QUERY_HEADER_01_HOUR = "SELECT DATE_FORMAT(cl.start_date, '%Y-%m-%d') AS start_date, " + 
		"CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(cl.start_date) - TIME_TO_SEC(cl.start_date)%(60*60))),'%H:%i'), ' - '), " +
		"DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(cl.start_date) + 3599 ) - TIME_TO_SEC(cl.start_date)%(60*60))),'%H:%i')) AS intervals, ";

	  private static final String SOS_CALLS_QUERY_HEADER_06_HOURS = "SELECT DATE_FORMAT(cl.start_date, '%Y-%m-%d') AS start_date, " + 
		"CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(cl.start_date) - TIME_TO_SEC(cl.start_date)%(360*60))),'%H:%i'), ' - '), " +
		"DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(cl.start_date) + 21599 ) - TIME_TO_SEC(cl.start_date)%(360*60))),'%H:%i')) AS intervals, ";

	  private static final String SOS_CALLS_QUERY_HEADER_DATE = "SELECT DATE_FORMAT(cl.start_date, '%Y-%m-%d') AS start_date, CONCAT('', ' ----- ') AS intervals, ";

	 // ---------------------------------------------------------------------------------------------------------------------------------------------------
				 
		// SINGLE QUERIES
			  
	  private static final String SOS_CALL_QUERY_HEADER_SINGLE_DATE = "SELECT DATE_FORMAT(cl.start_date, '%d/%m/%Y') AS start_date, ";
			  
	  private static final String SOS_ALARM_QUERY_HEADER_SINGLE_DATE = "SELECT DATE_FORMAT(al.start, '%d/%m/%Y') AS start, ";
	      		 	
	 // ---------------------------------------------------------------------------------------------------------------------------------------------------
		 	  
	    // INDEX
	  
		public static final String USE_INDEX_IDX_SOS = "USE INDEX(idx_equip_start_end_date) ";
		
	  		
	// ---------------------------------------------------------------------------------------------------------------------------------------------------
	 
		 // FROM TABLE - SOS CALLS	
	  
		private static final String SOS_FROM_TABLE_CALLS = " FROM sos_calls cl ";
		
     // ---------------------------------------------------------------------------------------------------------------------------------------------------
		
		// FROM TABLE - SOS ALARMS	
		  
	    private static final String SOS_FROM_TABLE_ALARMS = " FROM sos_alarms al ";
	    
	 // ---------------------------------------------------------------------------------------------------------------------------------------------------
			    
		// QUERIES GROUP AND ORDER BY SOS CALLS (PERIODS)
	    
		private static final String SOS_CALLS_GROUP_BY_ORDER_BY_05_MIN = "GROUP BY DATE(cl.start_date), sec_to_time(time_to_sec(cl.start_date) - time_to_sec(cl.start_date)%(05*60)) " +
				"ORDER BY DATE(cl.start_date) ASC ";

		private static final String SOS_CALLS_GROUP_BY_ORDER_BY_06_MIN = "GROUP BY DATE(cl.start_date), sec_to_time(time_to_sec(cl.start_date) - time_to_sec(cl.start_date)%(06*60)) " +
				"ORDER BY DATE(cl.start_date) ASC ";

		private static final String SOS_CALLS_GROUP_BY_ORDER_BY_10_MIN = "GROUP BY DATE(cl.start_date), sec_to_time(time_to_sec(cl.start_date) - time_to_sec(cl.start_date)%(10*60)) " +
				"ORDER BY DATE(cl.start_date) ASC ";

		private static final String SOS_CALLS_GROUP_BY_ORDER_BY_15_MIN = "GROUP BY DATE(cl.start_date), sec_to_time(time_to_sec(cl.start_date) - time_to_sec(cl.start_date)%(15*60)) " +
				"ORDER BY DATE(cl.start_date) ASC ";

		private static final String SOS_CALLS_GROUP_BY_ORDER_BY_30_MIN = "GROUP BY DATE(cl.start_date), sec_to_time(time_to_sec(cl.start_date) - time_to_sec(cl.start_date)%(30*60)) " +
				"ORDER BY DATE(cl.start_date) ASC ";

		private static final String SOS_CALLS_GROUP_BY_ORDER_BY_01_HOUR = "GROUP BY DATE(cl.start_date), sec_to_time(time_to_sec(cl.start_date) - time_to_sec(cl.start_date)%(60*60)) " +
				"ORDER BY DATE(cl.start_date) ASC "; 

		private static final String SOS_CALLS_GROUP_BY_ORDER_BY_06_HOURS = "GROUP BY DATE(cl.start_date), sec_to_time(time_to_sec(cl.start_date) - time_to_sec(cl.start_date)%(360*60)) " +
				"ORDER BY DATE(cl.start_date) ASC "; 

		private static final String SOS_CALLS_GROUP_BY_ORDER_BY_DATE = "GROUP BY DATE(cl.start_date) ORDER BY DATE(cl.start_date) ASC";
		
		
		// ---------------------------------------------------------------------------------------------------------------------------------------------------
		
	    // SOS CALLS SINGLE
		
		private static final String SOS_CALLS_GROUP_BY_ORDER_BY_SINGLE_DATE = "GROUP BY cl.start_date ORDER BY cl.start_date ASC";
		
        // ---------------------------------------------------------------------------------------------------------------------------------------------------
		
	    // SOS ALARMS SINGLE
		
		private static final String SOS_ALARMS_GROUP_BY_ORDER_BY_SINGLE_DATE = "GROUP BY al.start ORDER BY al.start ASC";
		
		
		// -------------------------------------------------------------------------------------------------------------------------------------------
		// METHODS
		// -------------------------------------------------------------------------------------------------------------------------------------------
	  		
		// METODO PARA SELECIONAR O HEADER DA QUERY
		
		public String SOSCallsHeader(String period) {

			if(period.equals("05 minutes"))
				return SOS_CALLS_QUERY_HEADER_05_MIN;

			if(period.equals("06 minutes"))
				return SOS_CALLS_QUERY_HEADER_06_MIN;

			if(period.equals("10 minutes"))
				return SOS_CALLS_QUERY_HEADER_10_MIN;

			if(period.equals("15 minutes"))
				return SOS_CALLS_QUERY_HEADER_15_MIN;

			if(period.equals("30 minutes"))
				return SOS_CALLS_QUERY_HEADER_30_MIN;

			if(period.equals("01 hour"))
				return SOS_CALLS_QUERY_HEADER_01_HOUR;

			if(period.equals("06 hours"))
				return SOS_CALLS_QUERY_HEADER_06_HOURS;

			if(period.equals("24 hours"))
				return SOS_CALLS_QUERY_HEADER_DATE;	
							 	
			return null;
		}
		
    // -------------------------------------------------------------------------------------------------------------------------------------------
	  	
		// FROM  TABLE CALLS
		
		public String SOSCallsFromTable() {
			
				return SOS_FROM_TABLE_CALLS;			
		}
		
    // -------------------------------------------------------------------------------------------------------------------------------------------
	  	
		// FROM  TABLE ALARM
		public String SOSAlarmsFromTable() {
			
				return SOS_FROM_TABLE_ALARMS;			
		}
		
    // -------------------------------------------------------------------------------------------------------------------------------------------	 
			
		// GROUP BY AND ORDER BY - CALLS TABLE
		public String SOSCallsQueryGroupAndOrderBy(String period) {

			if(period.equals("05 minutes"))
				return SOS_CALLS_GROUP_BY_ORDER_BY_05_MIN;

			if(period.equals("06 minutes"))
				return SOS_CALLS_GROUP_BY_ORDER_BY_06_MIN;

			if(period.equals("10 minutes"))
				return SOS_CALLS_GROUP_BY_ORDER_BY_10_MIN;

			if(period.equals("15 minutes"))
				return SOS_CALLS_GROUP_BY_ORDER_BY_15_MIN;

			if(period.equals("30 minutes"))
				return SOS_CALLS_GROUP_BY_ORDER_BY_30_MIN;

			if(period.equals("01 hour"))
				return SOS_CALLS_GROUP_BY_ORDER_BY_01_HOUR;

			if(period.equals("06 hours"))
				return SOS_CALLS_GROUP_BY_ORDER_BY_06_HOURS;

			if(period.equals("24 hours"))
				return SOS_CALLS_GROUP_BY_ORDER_BY_DATE;
		
			return null;
		}
				
	 // -------------------------------------------------------------------------------------------------------------------------------------------

		// QUERY HEADER - SINGLE (CALLS TABLE)
		public String SOSSingleCallsQueryHeader() {
			
			return SOS_CALL_QUERY_HEADER_SINGLE_DATE;
		}		
		
     // -------------------------------------------------------------------------------------------------------------------------------------------

		// QUERY HEADER - SINGLE (CALLS TABLE)
		public String SOSSingleAlarmsQueryHeader() {
			
			return SOS_ALARM_QUERY_HEADER_SINGLE_DATE;
		}		
		
     // -------------------------------------------------------------------------------------------------------------------------------------------

		
		// GROUP BY AND ORDER BY - SINGLE (CALLS TABLE)
		public String SOSSingleCallsQueryGroupAndOrderBy() {
			
			return SOS_CALLS_GROUP_BY_ORDER_BY_SINGLE_DATE;
		}
		
    // -------------------------------------------------------------------------------------------------------------------------------------------
		
		// GROUP BY AND ORDER BY - SINGLE (ALARMS TABLE)		
        public String SOSSingleAlarmsQueryGroupAndOrderBy() {
			
			return SOS_ALARMS_GROUP_BY_ORDER_BY_SINGLE_DATE;
		}
        
    // -------------------------------------------------------------------------------------------------------------------------------------------
    	
        // USER INDEX
        public String UseIndex(String index) {		 
   		 return index;   		
   	 }
        
   // -------------------------------------------------------------------------------------------------------------------------------------------
        
        // MAIN QUERY STRUCTURE
        public String BuildSOSQueryBase(String queryHeader, String queryMain, String queryFromTable, String queryIndex, String whereClause, String queryGroupOrder) { 	   
    	return queryHeader.concat(queryMain).concat(queryFromTable).concat(queryIndex).concat(whereClause).concat(queryGroupOrder);      
        }   
        
   // -------------------------------------------------------------------------------------------------------------------------------------------
     
        //SOS CALLS WHERE CLAUSE
        public String WhereClauseSOSCalls(String startDate, String endDate, String equip_id) {
       	   
       	   return " WHERE start_date >= '"+startDate+"' AND  end_date <= '"+endDate+"' AND equip_id = "+equip_id+" ";       	              	 
             	 
        }
      
   // -------------------------------------------------------------------------------------------------------------------------------------------
            
        // SOS ALARMS WHERE CLAUSE
        public String WhereClauseSOSAlarms(String startDate, String endDate, String equip_id) {
       	   
       	   return " WHERE start >= '"+startDate+"' AND  end <= '"+endDate+"' AND equip_id = "+equip_id+" ";
            	 
     }
        
  // -------------------------------------------------------------------------------------------------------------------------------------------       
        
}
