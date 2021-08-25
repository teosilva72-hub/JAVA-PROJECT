package br.com.tracevia.webapp.dao.sos;

public class SOSMainQueries {
			
	public String AlarmsStatus(String equip_id) {
		
		String query = "IFNULL(al.type_id, 0) 'TYPE', " +
				"CONCAT('OPEN') 'STATUS', " +
				"SEC_TO_TIME(TIMESTAMPDIFF(SECOND, al.start, al.end)) 'STATUS TIME' ";
					
		return query;
		
	}
	
	// -----------------------------------------------------------------
	
    public String Calls(String equip_id) {
		
		String query = "IFNULL(IF((cl.state_id = 3 OR cl.state_id = 5 OR cl.state_id = 6) AND cl.start_date <> '', DATE_FORMAT(cl.start_date, '%H:%i:%s'), NULL), ' --- ') 'RECEIVED', " + 
				"IFNULL(IF((cl.state_id = 3 OR cl.state_id = 6) AND cl.answered_date <> '', DATE_FORMAT(cl.answered_date, '%H:%i:%s'), NULL), ' --- ') 'ANSWERED', " + 
				"IFNULL(IF(cl.state_id = 3 , DATE_FORMAT(cl.end_date, '%H:%i:%s'), NULL), ' --- ') 'ENDED', " + 
				"IFNULL(IF(cl.state_id = 5 , DATE_FORMAT(cl.end_date, '%H:%i:%s'), NULL), ' --- ') 'LOST', " + 
				"IFNULL(IF(cl.state_id = 6 , DATE_FORMAT(cl.end_date, '%H:%i:%s'), NULL), ' --- ') 'ERROR', " + 
				"SEC_TO_TIME(TIMESTAMPDIFF(SECOND, cl.start_date, cl.end_date)) 'DURATION', IFNULL(cl.user, ' --- ') 'OPERATOR' ";
		
		return query;
		
	}

    // -----------------------------------------------------------------

    public String Statitictics(String equip_id) {
	
	String query = "IFNULL(ROUND(SUM(IF((cl.state_id = 3 OR cl.state_id = 5 OR cl.state_id = 6) AND cl.start_date <> '' , 1, NULL)),0),0) 'RECEIVED', " +
			"IFNULL(ROUND(SUM(IF((cl.state_id = 3 OR cl.state_id = 6) AND cl.answered_date <> '' , 1, NULL)),0),0) 'ANSWERED', " +
			"IFNULL(ROUND(SUM(IF(cl.state_id = 3 , 1, NULL)),0),0) 'ENDED', " +
			"IFNULL(ROUND(SUM(IF(cl.state_id = 5 , 1, NULL)),0),0) 'LOST', " +
			"IFNULL(ROUND(SUM(IF(cl.state_id = 6 , 1, NULL)),0),0) 'ERROR', " +
			"SEC_TO_TIME(ROUND(AVG(TIMESTAMPDIFF(SECOND, cl.start_date, cl.end_date)),0)) 'DURATION' ";
	
	return query;
	
   }
    
   // -----------------------------------------------------------------
    
   public String BatteryStatus(String equip_id) {
		
		String query = "IFNULL(ROUND(AVG(bt.volt), 2) ,0) 'VOLT', " +
				       "IFNULL(ROUND(AVG(bt.ampere), 2) ,0) 'AMPERE' ";
			
		   return query;
		
	}
   
   // -----------------------------------------------------------------

}
