package br.com.tracevia.webapp.dao.global;

public class QueryModelsBattery {
	
	// -------------------------------------------------------------------------------------------------------------------------------------------
	// MODELS
	// -------------------------------------------------------------------------------------------------------------------------------------------
				
	 // HEADERS QUERIES
	
	  private static final String BATTERY_QUERY_HEADER_05_MIN = "SELECT DATE_FORMAT(bt.bt.save_date, '%Y-%m-%d') AS save_date, " + 
	  "CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(bt.save_date) - TIME_TO_SEC(bt.save_date)%(05*60))),'%H:%i'), ' - '), " +
	  "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(bt.save_date) + 240 ) - TIME_TO_SEC(bt.save_date)%(05*60))),'%H:%i')) AS intervals, ";

	  private static final String BATTERY_QUERY_HEADER_06_MIN = "SELECT DATE_FORMAT(bt.save_date, '%Y-%m-%d') AS save_date, " + 
	  "CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(bt.save_date) - TIME_TO_SEC(bt.save_date)%(06*60))),'%H:%i'), ' - '), " +
	  "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(bt.save_date) + 300 ) - TIME_TO_SEC(bt.save_date)%(06*60))),'%H:%i')) AS intervals, ";

	  private static final String BATTERY_QUERY_HEADER_10_MIN = "SELECT DATE_FORMAT(bt.save_date, '%Y-%m-%d') AS save_date, " + 
	  "CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(bt.save_date) - TIME_TO_SEC(bt.save_date)%(10*60))),'%H:%i'), ' - '), " +
	  "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(bt.save_date) + 540 ) - TIME_TO_SEC(bt.save_date)%(10*60))),'%H:%i')) AS intervals, ";

	  private static final String BATTERY_QUERY_HEADER_15_MIN = "SELECT DATE_FORMAT(bt.save_date, '%Y-%m-%d') AS save_date, " + 
	  "CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(bt.save_date) - TIME_TO_SEC(bt.save_date)%(15*60))),'%H:%i'), ' - '), " +
	  "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(bt.save_date) + 840 ) - TIME_TO_SEC(bt.save_date)%(15*60))),'%H:%i')) AS intervals, ";

	  private static final String BATTERY_QUERY_HEADER_30_MIN = "SELECT DATE_FORMAT(bt.save_date, '%Y-%m-%d') AS save_date, " + 
	  "CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(bt.save_date) - TIME_TO_SEC(bt.save_date)%(30*60))),'%H:%i'), ' - '), " +
	  "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(bt.save_date) + 1740 ) - TIME_TO_SEC(bt.save_date)%(30*60))),'%H:%i')) AS intervals, ";

	  private static final String BATTERY_QUERY_HEADER_01_HOUR = "SELECT DATE_FORMAT(bt.save_date, '%Y-%m-%d') AS save_date, " + 
	  "CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(bt.save_date) - TIME_TO_SEC(bt.save_date)%(60*60))),'%H:%i'), ' - '), " +
	  "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(bt.save_date) + 3599 ) - TIME_TO_SEC(bt.save_date)%(60*60))),'%H:%i')) AS intervals, ";

	  private static final String BATTERY_QUERY_HEADER_06_HOURS = "SELECT DATE_FORMAT(bt.save_date, '%Y-%m-%d') AS save_date, " + 
	  "CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(bt.save_date) - TIME_TO_SEC(bt.save_date)%(360*60))),'%H:%i'), ' - '), " +
	  "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(bt.save_date) + 21599 ) - TIME_TO_SEC(bt.save_date)%(360*60))),'%H:%i')) AS intervals, ";

	  private static final String BATTERY_QUERY_HEADER_DATE = "SELECT DATE_FORMAT(bt.save_date, '%Y-%m-%d') AS save_date, CONCAT('', ' ----- ') AS intervals, ";
	  
	  // ---------------------------------------------------------------------------------------------------------------------------------------------------
		  	
	    // INDEX
	  
		public static final String USE_INDEX_IDX_BATTERY = "USE INDEX(idx_battery) ";
		
     // ---------------------------------------------------------------------------------------------------------------------------------------------------
		 
	   // FROM TABLE - BATTERY	
	  
		private static final String BATTERY_FROM_TABLE= " FROM battery_values bt ";
		
    // ---------------------------------------------------------------------------------------------------------------------------------------------------
				    
		// QUERIES GROUP AND ORDER BY BATTERY (PERIODS)
	    
		private static final String BATTERY_GROUP_BY_ORDER_BY_05_MIN = "GROUP BY DATE(bt.save_date), sec_to_time(time_to_sec(bt.save_date) - time_to_sec(bt.save_date)%(05*60)) " +
				"ORDER BY DATE(bt.save_date) ASC ";

		private static final String BATTERY_GROUP_BY_ORDER_BY_06_MIN = "GROUP BY DATE(bt.save_date), sec_to_time(time_to_sec(bt.save_date) - time_to_sec(bt.save_date)%(06*60)) " +
				"ORDER BY DATE(bt.save_date) ASC ";

		private static final String BATTERY_GROUP_BY_ORDER_BY_10_MIN = "GROUP BY DATE(bt.save_date), sec_to_time(time_to_sec(bt.save_date) - time_to_sec(bt.save_date)%(10*60)) " +
				"ORDER BY DATE(bt.save_date) ASC ";

		private static final String BATTERY_GROUP_BY_ORDER_BY_15_MIN = "GROUP BY DATE(bt.save_date), sec_to_time(time_to_sec(bt.save_date) - time_to_sec(bt.save_date)%(15*60)) " +
				"ORDER BY DATE(bt.save_date) ASC ";

		private static final String BATTERY_GROUP_BY_ORDER_BY_30_MIN = "GROUP BY DATE(bt.save_date), sec_to_time(time_to_sec(bt.save_date) - time_to_sec(bt.save_date)%(30*60)) " +
				"ORDER BY DATE(bt.save_date) ASC ";

		private static final String BATTERY_GROUP_BY_ORDER_BY_01_HOUR = "GROUP BY DATE(bt.save_date), sec_to_time(time_to_sec(bt.save_date) - time_to_sec(bt.save_date)%(60*60)) " +
				"ORDER BY DATE(bt.save_date) ASC "; 

		private static final String BATTERY_GROUP_BY_ORDER_BY_06_HOURS = "GROUP BY DATE(bt.save_date), sec_to_time(time_to_sec(bt.save_date) - time_to_sec(bt.save_date)%(360*60)) " +
				"ORDER BY DATE(bt.save_date) ASC "; 

		private static final String BATTERY_GROUP_BY_ORDER_BY_DATE = "GROUP BY DATE(bt.save_date) ORDER BY DATE(bt.save_date) ASC";
	
	// -------------------------------------------------------------------------------------------------------------------------------------------
	// METHODS
	// -------------------------------------------------------------------------------------------------------------------------------------------
  			
	// METODO PARA SELECIONAR O HEADER DA QUERY
	
	// METODO PARA SELECIONAR O HEADER DA QUERY
	
	public String BatteryHeader(String period) {

		if(period.equals("05 minutes"))
			return BATTERY_QUERY_HEADER_05_MIN;

		if(period.equals("06 minutes"))
			return BATTERY_QUERY_HEADER_06_MIN;

		if(period.equals("10 minutes"))
			return BATTERY_QUERY_HEADER_10_MIN;

		if(period.equals("15 minutes"))
			return BATTERY_QUERY_HEADER_15_MIN;

		if(period.equals("30 minutes"))
			return BATTERY_QUERY_HEADER_30_MIN;

		if(period.equals("01 hour"))
			return BATTERY_QUERY_HEADER_01_HOUR;

		if(period.equals("06 hours"))
			return BATTERY_QUERY_HEADER_06_HOURS;

		if(period.equals("24 hours"))
			return BATTERY_QUERY_HEADER_DATE;	
						 	
		return null;
	}
		
		
  // -------------------------------------------------------------------------------------------------------------------------------------------
  	
	// FROM  TABLE CALLS
	
	public String BatteryFromTable() {
		
			return BATTERY_FROM_TABLE;			
	}
	
// -------------------------------------------------------------------------------------------------------------------------------------------
  		
	// GROUP BY AND ORDER BY - CALLS TABLE
	public String BatteryQueryGroupAndOrderBy(String period) {

		if(period.equals("05 minutes"))
			return BATTERY_GROUP_BY_ORDER_BY_05_MIN;

		if(period.equals("06 minutes"))
			return BATTERY_GROUP_BY_ORDER_BY_06_MIN;

		if(period.equals("10 minutes"))
			return BATTERY_GROUP_BY_ORDER_BY_10_MIN;

		if(period.equals("15 minutes"))
			return BATTERY_GROUP_BY_ORDER_BY_15_MIN;

		if(period.equals("30 minutes"))
			return BATTERY_GROUP_BY_ORDER_BY_30_MIN;

		if(period.equals("01 hour"))
			return BATTERY_GROUP_BY_ORDER_BY_01_HOUR;

		if(period.equals("06 hours"))
			return BATTERY_GROUP_BY_ORDER_BY_06_HOURS;

		if(period.equals("24 hours"))
			return BATTERY_GROUP_BY_ORDER_BY_DATE;
	
		return null;
	}
	
  // -------------------------------------------------------------------------------------------------------------------------------------------

    // USER INDEX
    public String UseIndex(String index) {		 
		 return index;   		
	 }
    
  // -------------------------------------------------------------------------------------------------------------------------------------------
    
    // MAIN QUERY STRUCTURE
    public String BuildBatteryQueryBase(String queryHeader, String queryMain, String queryFromTable, String queryIndex, String whereClause, String queryGroupOrder) { 	   
	   return queryHeader.concat(queryMain).concat(queryFromTable).concat(queryIndex).concat(whereClause).concat(queryGroupOrder);      
    }   
    
  // -------------------------------------------------------------------------------------------------------------------------------------------
 
    // BATTERY WHERE CLAUSE
    public String WhereClauseBattery(String startDate, String endDate, String equip_id, String type) {
   	   
     	return " WHERE bt.save_date between '"+startDate+"' AND  '"+endDate+"' AND bt.equip_id = "+equip_id+" AND bt.equip_type = '"+type+"' ";	
     	 
      }
  
  // -------------------------------------------------------------------------------------------------------------------------------------------
        

}
