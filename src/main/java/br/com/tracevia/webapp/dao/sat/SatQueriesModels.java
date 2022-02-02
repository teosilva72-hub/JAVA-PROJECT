package br.com.tracevia.webapp.dao.sat;

import br.com.tracevia.webapp.model.global.RoadConcessionaire;

public class SatQueriesModels {
	
	private static int station_id;
	 	 	
	public static int getStation_id() {
		return station_id;
	}

	public static void setStation_id(int station_id) {
		SatQueriesModels.station_id = station_id;
	}
	
	
	// ------------------------------------------------------------------------------------------------------------------------------
		   
	   /**
	    * MÃ©todo que retorna query de velocidade de acordo com parametros selecionados
	    * @param station_id
	    * @param vehicles
	    * @return
	    */
	   public String SpeedMainQuery(String station_id) { 
		   
		   String query = null;
		   		  			   
		       query = "IFNULL(ROUND(SUM(CASEWHEN st.speed <= 50 THEN 1 ELSE NULL END)),0),0) '50km', " + 
		       		"IFNULL(ROUND(SUM(CASEWHEN st.speed > 50 AND st.speed <= 70 THEN 1 ELSE NULL END)),0),0) '50km/70km',  " + 
		       		"IFNULL(ROUND(SUM(CASEWHEN st.speed > 70 AND st.speed <= 90 THEN 1 ELSE NULL END)),0),0) '70km/90km', " + 
		       		"IFNULL(ROUND(SUM(CASEWHEN st.speed > 90 AND st.speed <=120 THEN 1 ELSE NULL END)),0),0) '90km/120km', " + 
		       		"IFNULL(ROUND(SUM(CASEWHEN st.speed > 120 AND st.speed <= 150 THEN 1 ELSE NULL END)),0),0) '120km/150km', " + 
		       		"IFNULL(ROUND(SUM(CASEWHEN st.speed > 150 THEN 1 ELSE NULL END)),0),0) 'above 150km', " + 
		       		"IFNULL(ROUND(SUM(CASEWHEN st.siteID = "+station_id+" THEN 1 ELSE 0 END)), 0),0) 'Total'";
		   		  		   
		   return query;
		   		   		   
	   }
	   
	   
	// ------------------------------------------------------------------------------------------------------------------------------
	   
	   /**
	    * MÃ©todo que retorna query de velocidade de acordo com parametros selecionados
	    * @param station_id
	    * @param vehicles
	    * @return
	    */
	   public String SpeedMultiMainQuery(String station_id) { 
		   
		   String query = null;
		   		   		   		  			   
		       query = " st.siteID 'equip', " +
		       		"IFNULL(ROUND(SUM(CASEWHEN st.speed <= 50 THEN 1 ELSE NULL END)),0),0) '50km', " + 
		       		"IFNULL(ROUND(SUM(CASEWHEN st.speed > 50 AND st.speed <= 70 THEN 1 ELSE NULL END)),0),0) '50km/70km',  " + 
		       		"IFNULL(ROUND(SUM(CASEWHEN st.speed > 70 AND st.speed <= 90 THEN 1 ELSE NULL END)),0),0) '70km/90km', " + 
		       		"IFNULL(ROUND(SUM(CASEWHEN st.speed > 90 AND st.speed <=120 THEN 1 ELSE NULL END)),0),0) '90km/120km', " + 
		       		"IFNULL(ROUND(SUM(CASEWHEN st.speed > 120 AND st.speed <= 150 THEN 1 ELSE NULL END)),0),0) '120km/150km', " + 
		       		"IFNULL(ROUND(SUM(CASEWHEN st.speed > 150 THEN 1 ELSE NULL END)),0),0) 'above 150km', " + 
		       		"IFNULL(ROUND(SUM(CASEWHEN st.siteID = "+station_id+" THEN 1 ELSE 0 END)), 0),0) 'Total'";
		   		  		   
		   return query;
		   		   		   
	   }
	  
	   
	// ------------------------------------------------------------------------------------------------------------------------------
	   	   
	   /**
	    * MÃ©todo para criaÃ§Ã£o da mainQuery tipo por eixos
	    * @param station_id - Id do equipamento
	    * @param axles - array com axles selecionados
	    * @param lanes - numero de faixas do equipamento
	    * @return
	    */
	   public String AxleTypeMainQuery(String station_id) {
		   		   
		   String query = "";
		   
		   query += "IFNULL(ROUND(COUNT(CASEWHEN (st.axlNumber = 0 OR st.axlNumber = 1 OR st.axlNumber = 2) THEN 1 ELSE NULL END )), 0), 0) '2 AXLES', " +				
				   "IFNULL(ROUND(COUNT(CASEWHEN (st.axlNumber = 3) THEN st.axlNumber ELSE NULL END )), 0), 0) '3 AXLES', " +
				   "IFNULL(ROUND(COUNT(CASEWHEN (st.axlNumber = 4) THEN st.axlNumber ELSE NULL END )), 0), 0) '4 AXLES', " + 
				   "IFNULL(ROUND(COUNT(CASEWHEN (st.axlNumber = 5) THEN st.axlNumber ELSE NULL END )), 0), 0) '5 AXLES', " + 
				   "IFNULL(ROUND(COUNT(CASEWHEN (st.axlNumber = 6) THEN st.axlNumber ELSE NULL END )), 0), 0) '6 AXLES', " +
				   "IFNULL(ROUND(COUNT(CASEWHEN (st.axlNumber = 7) THEN st.axlNumber ELSE NULL END )), 0), 0) '7 AXLES', " +
				   "IFNULL(ROUND(COUNT(CASEWHEN (st.axlNumber = 8) THEN st.axlNumber ELSE NULL END )), 0), 0) '8 AXLES', " +					
				   "IFNULL(ROUND(COUNT(CASEWHEN (st.axlNumber = 9) THEN st.axlNumber ELSE NULL END )), 0), 0) '9 AXLES', " +
				   "IFNULL(ROUND(COUNT(CASEWHEN (st.axlNumber = 10) THEN st.axlNumber ELSE NULL END )), 0), 0) '10 AXLES', " +  
				   "IFNULL(ROUND(COUNT(st.axlNumber),0),0) 'TOTAL', " +

				   "CASE " +
				   "WHEN eq.number_lanes = 2  THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.axlNumber = 0 OR st.axlNumber = 1 OR st.axlNumber = 2) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.axlNumber = 0 OR st.axlNumber = 1 OR st.axlNumber = 2) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.axlNumber = 0 OR st.axlNumber = 1 OR st.axlNumber = 2) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.axlNumber = 0 OR st.axlNumber = 1 OR st.axlNumber = 2) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.axlNumber = 0 OR st.axlNumber = 1 OR st.axlNumber = 2) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.axlNumber = 0 OR st.axlNumber = 1 OR st.axlNumber = 2) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.axlNumber = 0 OR st.axlNumber = 1 OR st.axlNumber = 2) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "ELSE 0   " +
				   "END 'AXLE_2_S1', " +

				   "CASE " +
				   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.axlNumber = 3) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.axlNumber = 3) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.axlNumber = 3) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.axlNumber = 3) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.axlNumber = 3) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.axlNumber = 3) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.axlNumber = 3) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "ELSE 0   " +
				   "END 'AXLE_3_S1', " +

				   "CASE " +
				   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.axlNumber = 4) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.axlNumber = 4) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.axlNumber = 4) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.axlNumber = 4) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.axlNumber = 4) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.axlNumber = 4) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.axlNumber = 4) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "ELSE 0   " +
				   "END 'AXLE_4_S1', " +

				   "CASE " +
				   "WHEN eq.number_lanes = 2  THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.axlNumber = 5) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.axlNumber = 5) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.axlNumber = 5) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.axlNumber = 5) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.axlNumber = 5) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.axlNumber = 5) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.axlNumber = 5) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "ELSE 0   " +
				   "END 'AXLE_5_S1', " +

				   "CASE " +
				   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.axlNumber = 6) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.axlNumber = 6) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.axlNumber = 6) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.axlNumber = 6) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.axlNumber = 6) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.axlNumber = 6) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.axlNumber = 6) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "ELSE 0   " +
				   "END 'AXLE_6_S1', " +

				   "CASE " +
				   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.axlNumber = 7) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.axlNumber = 7) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.axlNumber = 7) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.axlNumber = 7) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.axlNumber = 7) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.axlNumber = 7) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.axlNumber = 7) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "ELSE 0   " +
				   "END 'AXLE_7_S1', " +

				   "CASE " +
				   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.axlNumber = 8) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.axlNumber = 8) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.axlNumber = 8) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.axlNumber = 8) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.axlNumber = 8) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.axlNumber = 8) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.axlNumber = 8) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "ELSE 0   " +
				   "END 'AXLE_8_S1', " +

				   "CASE " +
				   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.axlNumber = 9) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.axlNumber = 9) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.axlNumber = 9) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.axlNumber = 9) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.axlNumber = 9) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.axlNumber = 9) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.axlNumber = 9) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "ELSE 0   " +
				   "END 'AXLE_9_S1', " +

				   "CASE " +
				   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.axlNumber = 10) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.axlNumber = 10) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.axlNumber = 10) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.axlNumber = 10) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.axlNumber = 10) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.axlNumber = 10) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.axlNumber = 10) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "ELSE 0   " +
				   "END 'AXLE_10_S1', " +


				   "CASE    " +
				    "WHEN eq.number_lanes = 2 OR eq.number_lanes = 3  THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) THEN st.axlNumber ELSE NULL END)), 0), 0)  " +
				    "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) THEN st.axlNumber ELSE NULL END)), 0), 0)  " +
				    "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) THEN st.axlNumber ELSE NULL END)), 0), 0)  " +
				    "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) THEN st.axlNumber ELSE NULL END)), 0), 0)  " +
				    "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) THEN st.axlNumber ELSE NULL END)), 0), 0)  " +
				    "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) THEN st.axlNumber ELSE NULL END)), 0), 0)  " +

				    "ELSE 0   " +
				    "END 'TOTAL_S1' ,  " +
				                     
				   "CASE " +
				   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.axlNumber = 0 OR st.axlNumber = 1 OR st.axlNumber = 2) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.axlNumber = 0 OR st.axlNumber = 1 OR st.axlNumber = 2) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.axlNumber = 0 OR st.axlNumber = 1 OR st.axlNumber = 2) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.axlNumber = 0 OR st.axlNumber = 1 OR st.axlNumber = 2) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.axlNumber = 0 OR st.axlNumber = 1 OR st.axlNumber = 2) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.axlNumber = 0 OR st.axlNumber = 1 OR st.axlNumber = 2) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.axlNumber = 0 OR st.axlNumber = 1 OR st.axlNumber = 2) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "ELSE 0   " +
				   "END 'AXLE_2_S2', " +

				   "CASE " +
				   "WHEN eq.number_lanes = 2  THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.axlNumber = 3) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3 ) AND (st.axlNumber = 3) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.axlNumber = 3) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.axlNumber = 3) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.axlNumber = 3) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.axlNumber = 3) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.axlNumber = 3) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "ELSE 0   " +
				   "END 'AXLE_3_S2', " +

				   "CASE " +
				   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.axlNumber = 4) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.axlNumber = 4) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.axlNumber = 3) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.axlNumber = 4) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.axlNumber = 4) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.axlNumber = 4) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.axlNumber = 4) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "ELSE 0   " +
				   "END 'AXLE_4_S2', " +

				   "CASE " +
				   "WHEN eq.number_lanes = 2  THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.axlNumber = 5) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.axlNumber = 5) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.axlNumber = 5) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.axlNumber = 5) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.axlNumber = 5) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.axlNumber = 5) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.axlNumber = 5) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "ELSE 0   " +
				   "END 'AXLE_5_S2', " +

				   "CASE " +
				   "WHEN eq.number_lanes = 2  THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.axlNumber = 6) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.axlNumber = 6) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.axlNumber = 6) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.axlNumber = 6) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.axlNumber = 6) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.axlNumber = 6) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.axlNumber = 6) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "ELSE 0   " +
				   "END 'AXLE_6_S2', " +

				   "CASE " +
				   "WHEN eq.number_lanes = 2  THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.axlNumber = 7) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 3 AND st.axlNumber = 7  THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.axlNumber = 7) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 4 AND st.axlNumber = 7  THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.axlNumber = 7) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 5 AND st.axlNumber = 7  THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.axlNumber = 7) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 6 AND st.axlNumber = 7  THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.axlNumber = 7) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 7 AND st.axlNumber = 7  THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.axlNumber = 7) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 8 AND st.axlNumber = 7  THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.axlNumber = 7) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "ELSE 0   " +
				   "END 'AXLE_7_S2', " +

				   "CASE " +
				   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.axlNumber = 8) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.axlNumber = 8) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.axlNumber = 8) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.axlNumber = 8) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.axlNumber = 8) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.axlNumber = 8) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.axlNumber = 8) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "ELSE 0   " +
				   "END 'AXLE_8_S1', " +

				   "CASE " +
				   "WHEN eq.number_lanes = 2  THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.axlNumber = 9) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.axlNumber = 9) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.axlNumber = 9) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.axlNumber = 9) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.axlNumber = 9) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.axlNumber = 9) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.axlNumber = 9) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "ELSE 0   " +
				   "END 'AXLE_9_S2', " +

				   "CASE " +
				   "WHEN eq.number_lanes = 2  THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.axlNumber = 10) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.axlNumber = 10) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.axlNumber = 10) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.axlNumber = 10) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.axlNumber = 10) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.axlNumber = 10) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND ( 10) THEN st.axlNumber ELSE NULL END )), 0), 0) " +
				   "ELSE 0   " +
				   "END 'AXLE_10_S2', " +

				   "CASE    " +
				    "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) THEN st.axlNumber ELSE NULL END)), 0), 0)  " +
				    "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3)  THEN st.axlNumber ELSE NULL END)), 0), 0)  " +
				    "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) THEN st.axlNumber ELSE NULL END)), 0), 0)  " +
				    "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) THEN st.axlNumber ELSE NULL END)), 0), 0)  " +
				    "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) THEN st.axlNumber ELSE NULL END)), 0), 0)  " +
				    "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) THEN st.axlNumber ELSE NULL END)), 0), 0)  " +
				    "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) THEN st.axlNumber ELSE NULL END)), 0), 0)  " +

				    "ELSE 0   " +
				    "END 'TOTAL_S2'   " ;
	//END METHOD		               
    return query;		             
		     
}
	   	   
	   
	   /**
	    * MÃ©todo para criaÃ§Ã£o da mainQuery tipo por classes
	    * @param station_id - Id do equipamento
	    * @param classes - array com as classes selecionadas
	    * @param lanes - numero de faixas do equipamento
	    * @return
	    */
	   public String ClassTypeMainQuery(String station_id) {
   		   
		   String query = "";
		   
						
		   query += "IFNULL(ROUND(COUNT(CASEWHEN (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) 'AUTOS', " +
					 "IFNULL(ROUND(COUNT(CASEWHEN (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) 'MOTO', " +
					 "IFNULL(ROUND(COUNT(CASEWHEN (st.classe = '"+RoadConcessionaire.classTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) 'TRAILER', " + 
					 "IFNULL(ROUND(COUNT(CASEWHEN (st.classe = '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) 'SEMITRAILER', " +
					 "IFNULL(ROUND(COUNT(CASEWHEN (st.classe = '"+RoadConcessionaire.classTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) 'TRUCK 2 AXLES', " + 
					 "IFNULL(ROUND(COUNT(CASEWHEN (st.classe = '"+RoadConcessionaire.classTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) 'TRUCK 3 AXLES', " + 
		             "IFNULL(ROUND(COUNT(CASEWHEN (st.classe = '"+RoadConcessionaire.classTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) 'TRUCK 4 AXLES', " +
					 "IFNULL(ROUND(COUNT(CASEWHEN (st.classe = '"+RoadConcessionaire.classTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) 'TRUCK 5 AXLES', " +
					 "IFNULL(ROUND(COUNT(CASEWHEN (st.classe = '"+RoadConcessionaire.classTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) 'TRUCK 6 AXLES', " +
					 "IFNULL(ROUND(COUNT(CASEWHEN (st.classe = '"+RoadConcessionaire.classTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) 'TRUCK 7 AXLES', " +
					 "IFNULL(ROUND(COUNT(CASEWHEN (st.classe = '"+RoadConcessionaire.classTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) 'TRUCK 8 AXLES', " +			 
					 "IFNULL(ROUND(COUNT(CASEWHEN (st.classe = '"+RoadConcessionaire.classTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) 'TRUCK 9 AXLES', " +
			         "IFNULL(ROUND(COUNT(CASEWHEN (st.classe='"+RoadConcessionaire.classTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.axlNumber > 10)) THEN st.classe ELSE NULL END )), 0), 0) 'TRUCK 10 AXLES', " +
				     "IFNULL(ROUND(COUNT(st.axlNumber),0),0) 'TOTAL', " +				         	   		   


			   "CASE " +
			   "WHEN eq.number_lanes = 2  THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "ELSE 0   " +
			   "END 'AUTOS_S1', " +

			   "CASE " +
			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "ELSE 0   " +
			   "END 'MOTOS_S1', " +

			   "CASE " +
			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "ELSE 0   " +
			   "END 'TRAILER_S1', " +

			   "CASE " +
			   "WHEN eq.number_lanes = 2  THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "ELSE 0   " +
			   "END 'SEMI_TRAILER_S1', " +

			   "CASE " +
			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "ELSE 0   " +
			   "END 'AXLE_2_S1', " +

			   "CASE " +
			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "ELSE 0   " +
			   "END 'AXLE_3_S1', " +

			   "CASE " +
			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "ELSE 0   " +
			   "END 'AXLE_4_S1', " +

			   "CASE " +
			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "ELSE 0   " +
			   "END 'AXLE_5_S1', " +
			   
		       "CASE " +
		       "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
		       "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
		       "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
		       "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
		       "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
		       "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
		       "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
		       "ELSE 0   " +
		       "END 'AXLE_5_S1', " +
			  	   
			   "CASE " +
			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "ELSE 0   " +
			   "END 'AXLE_7_S1', " +
			   				   
			   "CASE " +
			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "ELSE 0   " +
			   "END 'AXLE_8_S1', " +
			   
			   "CASE " +
			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "ELSE 0   " +
			   "END 'AXLE_9_S1', " +
			   
			   "CASE " +
			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe='"+RoadConcessionaire.classTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe='"+RoadConcessionaire.classTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe='"+RoadConcessionaire.classTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe='"+RoadConcessionaire.classTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe='"+RoadConcessionaire.classTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe='"+RoadConcessionaire.classTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe='"+RoadConcessionaire.classTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
			   "ELSE 0   " +
			   "END 'AXLE_10_S1', " +

			   "CASE    " +
			    "WHEN eq.number_lanes = 2 OR eq.number_lanes = 3  THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) THEN st.classe ELSE NULL END)), 0), 0)  " +
			    "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) THEN st.classe ELSE NULL END)), 0), 0)  " +
			    "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) THEN st.classe ELSE NULL END)), 0), 0)  " +
			    "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) THEN st.classe ELSE NULL END)), 0), 0)  " +
			    "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) THEN st.classe ELSE NULL END)), 0), 0)  " +
			    "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) THEN st.classe ELSE NULL END)), 0), 0)  " +

			    "ELSE 0   " +
			    "END 'TOTAL_S1' ,  " +
			                     
			  "CASE " +
			   "WHEN eq.number_lanes = 2  THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "ELSE 0   " +
			   "END 'AUTOS_S2', " +

			   "CASE " +
			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2  OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "ELSE 0   " +
			   "END 'MOTOS_S2', " +

			   "CASE " +
			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "ELSE 0   " +
			   "END 'TRAILER_S2', " +

			   "CASE " +
			   "WHEN eq.number_lanes = 2  THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "ELSE 0   " +
			   "END 'SEMI_TRAILER_S2', " +

			   "CASE " +
			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "ELSE 0   " +
			   "END 'AXLE_2_S2', " +

			   "CASE " +
			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "ELSE 0   " +
			   "END 'AXLE_3_S2', " +

			   "CASE " +
			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "ELSE 0   " +
			   "END 'AXLE_4_S2', " +

			   "CASE " +
			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "ELSE 0   " +
			   "END 'AXLE_5_S2', " +

			   "CASE " +
			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "ELSE 0   " +
			   "END 'AXLE_6_S2', " + 
			   
			   "CASE " +
			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "ELSE 0   " +
			   "END 'AXLE_7_S2', " +
			   				   
			   "CASE " +
			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "ELSE 0   " +
			   "END 'AXLE_8_S2', " +
			   
			   "CASE " +
			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
			   "ELSE 0   " +
			   "END 'AXLE_9_S2', " +
			   
			   "CASE " +
			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2) AND (st.classe='"+RoadConcessionaire.classTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2 OR st.lane = 3) AND (st.classe='"+RoadConcessionaire.classTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 3 OR st.lane = 4) AND (st.classe='"+RoadConcessionaire.classTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe='"+RoadConcessionaire.classTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe='"+RoadConcessionaire.classTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe='"+RoadConcessionaire.classTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe='"+RoadConcessionaire.classTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
			   "ELSE 0   " +
			   "END 'AXLE_10_S2', " +


			   "CASE    " +
			    "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) THEN st.classe ELSE NULL END)), 0), 0)  " +
			    "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) THEN st.classe ELSE NULL END)), 0), 0)  " +
			    "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) THEN st.classe ELSE NULL END)), 0), 0)  " +
			    "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) THEN st.classe ELSE NULL END)), 0), 0)  " +
			    "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) THEN st.classe ELSE NULL END)), 0), 0)  " +
			    "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) THEN st.classe ELSE NULL END)), 0), 0)  " +
			    "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) THEN st.classe ELSE NULL END)), 0), 0)  " +

			    "ELSE 0   " +
			    "END 'TOTAL_S2'   " ;
		   
		                   //END METHOD		               
		                   return query;		             
		     
	                   } 
	   
	   
	   
	   /**
	    * MÃ©todo para criaÃ§Ã£o da mainQuery pesagem
	    * @param station_id - Id do equipamento
	    * @param classes - array com as classes selecionadas
	    * @param lanes - numero de faixas do equipamento
	    * @return
	    */
 public String WeighingMainQuery(String station_id) {
   		   
		   String query = "";
		    
	      query += "IFNULL(ROUND(AVG(CASEWHEN (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"')  THEN st.gross ELSE NULL END )), 0), 0) 'AUTOS', " +
	    "IFNULL(ROUND(AVG(CASEWHEN (st.classe = '"+RoadConcessionaire.classMotorcycle+"')  THEN st.gross ELSE NULL END )), 0), 0) 'MOTO', " +		
		"IFNULL(ROUND(AVG(CASEWHEN (st.classe = '"+RoadConcessionaire.classTrailer+"')  THEN st.gross ELSE NULL END )), 0), 0) 'TRAILER', " +	
		"IFNULL(ROUND(AVG(CASEWHEN (st.classe = '"+RoadConcessionaire.classSemiTrailer+"')  THEN st.gross ELSE NULL END )), 0), 0) 'SEMITRAILER', " +
		"IFNULL(ROUND(AVG(CASEWHEN (st.classe = '"+RoadConcessionaire.classTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"')  THEN st.gross ELSE NULL END )), 0), 0) 'TRUCK 2 AXLES', " +
		"IFNULL(ROUND(AVG(CASEWHEN (st.classe = '"+RoadConcessionaire.classTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"')  THEN st.gross ELSE NULL END )), 0), 0) 'TRUCK 3 AXLES', " +
		"IFNULL(ROUND(AVG(CASEWHEN (st.classe = '"+RoadConcessionaire.classTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"')  THEN st.gross ELSE NULL END )), 0), 0) 'TRUCK 4 AXLES', " +
	   	"IFNULL(ROUND(AVG(CASEWHEN (st.classe = '"+RoadConcessionaire.classTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"')  THEN st.gross ELSE NULL END )), 0), 0) 'TRUCK 5 AXLES', " +
		"IFNULL(ROUND(AVG(CASEWHEN (st.classe = '"+RoadConcessionaire.classTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"')  THEN st.gross ELSE NULL END )), 0), 0) 'TRUCK 6 AXLES', " +
		"IFNULL(ROUND(AVG(CASEWHEN (st.classe = '"+RoadConcessionaire.classTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"')  THEN st.gross ELSE NULL END )), 0), 0) 'TRUCK 7 AXLES', " +
		"IFNULL(ROUND(AVG(CASEWHEN (st.classe = '"+RoadConcessionaire.classTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"')  THEN st.gross ELSE NULL END )), 0), 0) 'TRUCK 8 AXLES', " +
		"IFNULL(ROUND(AVG(CASEWHEN (st.classe = '"+RoadConcessionaire.classTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"')  THEN st.gross ELSE NULL END )), 0), 0) 'TRUCK 9 AXLES', " +
		"IFNULL(ROUND(AVG(CASEWHEN (st.classe='"+RoadConcessionaire.classTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.axlNumber > 10))  THEN st.gross ELSE NULL END )), 0), 0) 'TRUCK 10 AXLES' " ;
						       
         //END METHOD		          
	      return query;    
	       
      } 	
 
	   
	   public String PeriodFlowMainQuery(String[] stations, String period, int[] lanes) {
     	  		   
     	 String query = "";
     	            	 
     	 query += PeriodFlowHeaderSelectQuery(period);
     	      	     	  
     	 for(int st = 0; st < stations.length; st++) {
       	  
       	  if(lanes[st] == 2) {  
       		  
       		/* NAME */
       		query += "(SELECT name FROM sat_equipment WHERE equip_id = '"+stations[st]+"' ) 'EQUIP', " +
       		         		           	  
       		/*CONTAGEM*/	
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'AUTOS1', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'COM1', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'MOTOS1', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END)),0),0) 'TOTALS1', " +
       		           	  			
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'AUTOS2', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'COM2', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'MOTOS2', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END)),0),0) 'TOTALS2', " +
       		"'' AS CONCAT1, " +  
       		
       		/*MÃ‰DIA*/	       		
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG AUTOS1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG COM1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG MOTOS1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'AVG TOTALS1', " + 

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG AUTOS2',  " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 2) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG COM2', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG MOTOS2', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 2) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'AVG TOTALS2', " +
       		"'' AS CONCAT2, " +   
       		
       		/*VELOCIDADE 50*/

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
       		"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - AUTO1', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
       		"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - COM1', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - MOTO1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50 ) / 100 ELSE NULL END)),0),0)'AVG 50th - TOTAL1', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
       		"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - AUTO2', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 2) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
       		"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - COM2', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - MOTO2', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 2) AND eq.equip_id = '"+stations[st]+"'  THEN (st.speed * 50 ) / 100 ELSE NULL END)),0),0)'AVG 50th - TOTAL2', " +
       		"'' AS CONCAT3, " +    
       		
       		/*VELOCIDADE 85*/

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
       		"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - AUTO1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
       		"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - COM1',  " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - MOTO1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85 ) / 100 ELSE NULL END)),0),0)'AVG 85th - TOTAL1', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
       		"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - AUTO2', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 2) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
       		"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - COM2', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - MOTO2', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 2) AND eq.equip_id = '"+stations[st]+"'  THEN (st.speed * 85 ) / 100 ELSE NULL END)),0),0)'AVG 85th - TOTAL2', " +
       		"'' AS CONCAT4, " +  
       		
       		/*MÃ�XIMA*/
       		           	  				 
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX AUTOS1', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX COM1', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.speed ELSE NULL END )) AND eq.equip_id = '"+stations[st]+"',0),0) 'MAX MOTOS1', " + 
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END)),0),0) 'MAX TOTALS1', " +

       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX AUTOS2', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 2) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX COM2', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX MOTOS2', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 2) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'MAX TOTALS2', " +
       		"'' AS CONCAT5, " +  
       		
       		/*MÃ�NIMA*/
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN AUTOS1', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN COM1', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN MOTOS1', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'MIN TOTALS1', " +

       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN AUTOS2', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 2) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN COM2', " + 
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN MOTOS2', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 2) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END)),0),0) 'MIN TOTALS2', " +
       		"'' AS CONCAT6, " +  
       		
       		/*DESVIO PADRÃƒO */				
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV AUTOS1', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV COM1', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV MOTOS1', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'DESV TOTALS1', " +

       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV AUTOS2', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 2) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV COM2', " + 
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV MOTOS2', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 2) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'DESV TOTALS2' ";
       		       		
       	        }
       	  
       	  if(lanes[st] == 3) {
       		  
       		/* NAME */
       		query += "(SELECT name FROM sat_equipment WHERE equip_id = '"+stations[st]+"' ) 'EQUIP', " +       	       	      
       		         	  
       		/*CONTAGEM*/	
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'AUTOS1', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'COM1', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'MOTOS1', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END)),0),0) 'TOTALS1', " +
       		           	  			
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'AUTOS2', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'COM2', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'MOTOS2', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END)),0),0) 'TOTALS2', " +
       		"'' AS CONCAT1, " +  
       		
       		/*MÃ‰DIA*/	
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG AUTOS1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG COM1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG MOTOS1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'AVG TOTALS1', " + 

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG AUTOS2',  " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG COM2', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG MOTOS2', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'AVG TOTALS2', " +
       		"'' AS CONCAT2, " +  
       		
       		/*VELOCIDADE 50*/

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
       		"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - AUTO1', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
       		"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - COM1', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - MOTO1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50 ) / 100 ELSE NULL END)),0),0)'AVG 50th - TOTAL1', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
       		"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - AUTO2', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
       		"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - COM2', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - MOTO2', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+stations[st]+"'  THEN (st.speed * 50 ) / 100 ELSE NULL END)),0),0)'AVG 50th - TOTAL2', " +
       		"'' AS CONCAT3, " +  
       		
       		/*VELOCIDADE 85*/

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
       		"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - AUTO1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
       		"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - COM1',  " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - MOTO1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85 ) / 100 ELSE NULL END)),0),0)'AVG 85th - TOTAL1', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
       		"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - AUTO2', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
       		"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - COM2', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - MOTO2', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+stations[st]+"'  THEN (st.speed * 85 ) / 100 ELSE NULL END)),0),0)'AVG 85th - TOTAL2', " +
       		"'' AS CONCAT4, " +  
       		
       		/*MÃ�XIMA*/
       		           	  				 
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX AUTOS1', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX COM1', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.speed ELSE NULL END )) AND eq.equip_id = '"+stations[st]+"',0),0) 'MAX MOTOS1', " + 
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END)),0),0) 'MAX TOTALS1', " +

       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX AUTOS2', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX COM2', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX MOTOS2', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'MAX TOTALS2', " +
       		"'' AS CONCAT5, " +  
       		
       		/*MÃ�NIMA*/
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN AUTOS1', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN COM1', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN MOTOS1', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'MIN TOTALS1', " +

       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN AUTOS2', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN COM2', " + 
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN MOTOS2', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END)),0),0) 'MIN TOTALS2', " +
       		"'' AS CONCAT6, " +  
       		
       		/*DESVIO PADRÃƒO */				
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV AUTOS1', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV COM1', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV MOTOS1', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'DESV TOTALS1', " +

       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV AUTOS2', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV COM2', " + 
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV MOTOS2', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'DESV TOTALS2' ";
       		
                }	
       	  
       	  if(lanes[st] == 4) {
       		  
       		/* NAME */
       		query += "(SELECT name FROM sat_equipment WHERE equip_id = '"+stations[st]+"' ) 'EQUIP', " +
       		  		    	       		
       		/*CONTAGEM*/	
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'AUTOS1', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'COM1', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'MOTOS1', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END)),0),0) 'TOTALS1', " +
       		           	  			
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'AUTOS2', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'COM2', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'MOTOS2', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END)),0),0) 'TOTALS2', " +
       		"'' AS CONCAT1, " +  
       		
       		/*MÃ‰DIA*/	
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG AUTOS1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG COM1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG MOTOS1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'AVG TOTALS1', " + 

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG AUTOS2',  " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG COM2', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG MOTOS2', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'AVG TOTALS2', " +
       		"'' AS CONCAT2, " +  
       		
       		/*VELOCIDADE 50*/

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
       		"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - AUTO1', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
       		"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - COM1', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - MOTO1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50 ) / 100 ELSE NULL END)),0),0)'AVG 50th - TOTAL1', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
       		"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - AUTO2', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
       		"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - COM2', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - MOTO2', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"'  THEN (st.speed * 50 ) / 100 ELSE NULL END)),0),0)'AVG 50th - TOTAL2', " +
       		"'' AS CONCAT3, " +  
       		
       		/*VELOCIDADE 85*/

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
       		"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - AUTO1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
       		"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - COM1',  " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - MOTO1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85 ) / 100 ELSE NULL END)),0),0)'AVG 85th - TOTAL1', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
       		"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - AUTO2', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
       		"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - COM2', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - MOTO2', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"'  THEN (st.speed * 85 ) / 100 ELSE NULL END)),0),0)'AVG 85th - TOTAL2', " +
       		"'' AS CONCAT4, " +  
       		
       		/*MÃ�XIMA*/
       		           	  				 
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX AUTOS1', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX COM1', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.speed ELSE NULL END )) AND eq.equip_id = '"+stations[st]+"',0),0) 'MAX MOTOS1', " + 
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1 OR st.lane = 2) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END)),0),0) 'MAX TOTALS1', " +

       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX AUTOS2', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX COM2', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX MOTOS2', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'MAX TOTALS2', " +
       		"'' AS CONCAT5, " +  
       		
       		/*MÃ�NIMA*/
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN AUTOS1', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN COM1', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN MOTOS1', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1 OR st.lane = 2) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'MIN TOTALS1', " +

       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN AUTOS2', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN COM2', " + 
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN MOTOS2', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END)),0),0) 'MIN TOTALS2', " +
       		"'' AS CONCAT6, " +  
       		
       		/*DESVIO PADRÃƒO */				
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV AUTOS1', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV COM1', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV MOTOS1', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1 OR st.lane = 2) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'DESV TOTALS1', " +

       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV AUTOS2', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV COM2', " + 
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV MOTOS2', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'DESV TOTALS2' " ;
       		           	           		
       		  }	
       	  
       	  if(lanes[st] == 5) {
       		  
       		/* NAME */
       		query += "(SELECT name FROM sat_equipment WHERE equip_id = '"+stations[st]+"' ) 'EQUIP', " +
       		  		               
       		/*CONTAGEM*/	
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'AUTOS1', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'COM1', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'MOTOS1', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END)),0),0) 'TOTALS1', " +
       		           	  			
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'AUTOS2', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'COM2', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'MOTOS2', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END)),0),0) 'TOTALS2', " +
       		"'' AS CONCAT1, " +  
       		
       		/*MÃ‰DIA*/	
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG AUTOS1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG COM1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG MOTOS1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'AVG TOTALS1', " + 

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG AUTOS2',  " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG COM2', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG MOTOS2', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'AVG TOTALS2', " +
       		"'' AS CONCAT2, " +  
       		
       		/*VELOCIDADE 50*/

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
       		"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - AUTO1', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
       		"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - COM1', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - MOTO1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50 ) / 100 ELSE NULL END)),0),0)'AVG 50th - TOTAL1', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
       		"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - AUTO2', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
       		"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - COM2', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - MOTO2', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"'  THEN (st.speed * 50 ) / 100 ELSE NULL END)),0),0)'AVG 50th - TOTAL2', " +
       		"'' AS CONCAT3, " +  
       		
       		/*VELOCIDADE 85*/

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
       		"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - AUTO1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
       		"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - COM1',  " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - MOTO1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85 ) / 100 ELSE NULL END)),0),0)'AVG 85th - TOTAL1', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
       		"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - AUTO2', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
       		"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - COM2', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - MOTO2', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"'  THEN (st.speed * 85 ) / 100 ELSE NULL END)),0),0)'AVG 85th - TOTAL2', " +
       		"'' AS CONCAT4, " +   
       		
       		/*MÃ�XIMA*/
       		           	  				 
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX AUTOS1', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX COM1', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.speed ELSE NULL END )) AND eq.equip_id = '"+stations[st]+"',0),0) 'MAX MOTOS1', " + 
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END)),0),0) 'MAX TOTALS1', " +

       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX AUTOS2', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX COM2', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX MOTOS2', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'MAX TOTALS2', " +
       		"'' AS CONCAT5, " +  
       		
       		/*MÃ�NIMA*/
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN AUTOS1', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN COM1', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN MOTOS1', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'MIN TOTALS1', " +

       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN AUTOS2', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN COM2', " + 
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN MOTOS2', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END)),0),0) 'MIN TOTALS2', " +
       		"'' AS CONCAT6, " + 
       		
       		/*DESVIO PADRÃƒO */				
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV AUTOS1', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV COM1', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV MOTOS1', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'DESV TOTALS1', " +

       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV AUTOS2', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV COM2', " + 
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV MOTOS2', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'DESV TOTALS2' " ;
       		           	           		
          		  }	
       	  
       	  if(lanes[st] == 6) {
       		  
       		/* NAME */
       		query += "(SELECT name FROM sat_equipment WHERE equip_id = '"+stations[st]+"' ) 'EQUIP', " +
       		  		            
       		/*CONTAGEM*/	
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'AUTOS1', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'COM1', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'MOTOS1', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END)),0),0) 'TOTALS1', " +
       		           	  			
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'AUTOS2', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'COM2', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'MOTOS2', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END)),0),0) 'TOTALS2', " +
       		"'' AS CONCAT1, " +  
       		
       		/*MÃ‰DIA*/	
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG AUTOS1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG COM1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG MOTOS1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'AVG TOTALS1', " + 

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG AUTOS2',  " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG COM2', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG MOTOS2', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'AVG TOTALS2', " +
       		"'' AS CONCAT2, " +  
       		
       		/*VELOCIDADE 50*/

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
       		"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - AUTO1', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
       		"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - COM1', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - MOTO1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50 ) / 100 ELSE NULL END)),0),0)'AVG 50th - TOTAL1', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
       		"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - AUTO2', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
       		"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - COM2', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - MOTO2', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND eq.equip_id = '"+stations[st]+"'  THEN (st.speed * 50 ) / 100 ELSE NULL END)),0),0)'AVG 50th - TOTAL2', " +
       		"'' AS CONCAT3, " +  
       		
       		/*VELOCIDADE 85*/

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
       		"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - AUTO1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
       		"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - COM1',  " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - MOTO1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85 ) / 100 ELSE NULL END)),0),0)'AVG 85th - TOTAL1', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
       		"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - AUTO2', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
       		"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - COM2', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - MOTO2', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND eq.equip_id = '"+stations[st]+"'  THEN (st.speed * 85 ) / 100 ELSE NULL END)),0),0)'AVG 85th - TOTAL2', " +
       		"'' AS CONCAT4, " +  
       		
       		/*MÃ�XIMA*/
       		           	  				 
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX AUTOS1', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX COM1', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.speed ELSE NULL END )) AND eq.equip_id = '"+stations[st]+"',0),0) 'MAX MOTOS1', " + 
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END)),0),0) 'MAX TOTALS1', " +

       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX AUTOS2', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX COM2', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX MOTOS2', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'MAX TOTALS2', " +
       		"'' AS CONCAT5, " +  
       		
       		/*MÃ�NIMA*/
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN AUTOS1', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN COM1', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN MOTOS1', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'MIN TOTALS1', " +

       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN AUTOS2', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN COM2', " + 
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN MOTOS2', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END)),0),0) 'MIN TOTALS2', " +
       		"'' AS CONCAT6, " +  
       		
       		/*DESVIO PADRÃƒO */				
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV AUTOS1', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV COM1', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV MOTOS1', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'DESV TOTALS1', " +

       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV AUTOS2', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV COM2', " + 
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV MOTOS2', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'DESV TOTALS2' " ;
       		           	           		
       		  }	
       	  
       	  if(lanes[st] == 7) {
       		  
       		/* NAME */
       		query += "(SELECT name FROM sat_equipment WHERE equip_id = '"+stations[st]+"' ) 'EQUIP', " +
       		  		            	  
       		/*CONTAGEM*/	
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'AUTOS1', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'COM1', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'MOTOS1', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END)),0),0) 'TOTALS1', " +
       		           	  			
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'AUTOS2', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'COM2', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'MOTOS2', " +
       		"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END)),0),0) 'TOTALS2', " +
       		"'' AS CONCAT1, " +  
       		
       		/*MÃ‰DIA*/	
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG AUTOS1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG COM1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG MOTOS1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'AVG TOTALS1', " + 

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG AUTOS2',  " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG COM2', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG MOTOS2', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'AVG TOTALS2', " +
       		"'' AS CONCAT2, " +  	
       		
       		/*VELOCIDADE 50*/

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
       		"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - AUTO1', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
       		"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - COM1', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - MOTO1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50 ) / 100 ELSE NULL END)),0),0)'AVG 50th - TOTAL1', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
       		"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - AUTO2', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
       		"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - COM2', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - MOTO2', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND eq.equip_id = '"+stations[st]+"'  THEN (st.speed * 50 ) / 100 ELSE NULL END)),0),0)'AVG 50th - TOTAL2', " +
       		"'' AS CONCAT3, " +  
       		
       		/*VELOCIDADE 85*/

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
       		"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - AUTO1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
       		"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - COM1',  " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - MOTO1', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85 ) / 100 ELSE NULL END)),0),0)'AVG 85th - TOTAL1', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
       		"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - AUTO2', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
       		"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - COM2', " +

       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - MOTO2', " +
       		"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND eq.equip_id = '"+stations[st]+"'  THEN (st.speed * 85 ) / 100 ELSE NULL END)),0),0)'AVG 85th - TOTAL2', " +
       		"'' AS CONCAT4, " +  
       		
       		/*MÃ�XIMA*/
       		           	  				 
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX AUTOS1', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX COM1', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.speed ELSE NULL END )) AND eq.equip_id = '"+stations[st]+"',0),0) 'MAX MOTOS1', " + 
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END)),0),0) 'MAX TOTALS1', " +

       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX AUTOS2', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX COM2', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX MOTOS2', " +
       		"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'MAX TOTALS2', " +
       		"'' AS CONCAT5, " +  
       		
       		/*MÃ�NIMA*/
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN AUTOS1', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN COM1', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN MOTOS1', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'MIN TOTALS1', " +

       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN AUTOS2', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN COM2', " + 
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN MOTOS2', " +
       		"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END)),0),0) 'MIN TOTALS2', " +
       		"'' AS CONCAT6, " +  
       		
       		/*DESVIO PADRÃƒO */				
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV AUTOS1', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV COM1', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV MOTOS1', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'DESV TOTALS1', " +

       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV AUTOS2', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV COM2', " + 
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV MOTOS2', " +
       		"IFNULL(ROUND(STD(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'DESV TOTALS2' " ;
       		           	           		
       	         }
  		   
  		      if(lanes[st] == 8) {
  		    	  
  		    	  /* NAME */
  		    	query += "(SELECT name FROM sat_equipment WHERE equip_id = '"+stations[st]+"' ) 'EQUIP', " +
  		    	               
  		    	/*CONTAGEM*/	
  		    	"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'AUTOS1', " +
  		    	"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'COM1', " +
  		    	"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'MOTOS1', " +
  		    	"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END)),0),0) 'TOTALS1', " +
  		    	           	  			
  		    	"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'AUTOS2', " +
  		    	"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'COM2', " +
  		    	"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END )),0),0) 'MOTOS2', " +
  		    	"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND eq.equip_id = '"+stations[st]+"' THEN 1 ELSE NULL END)),0),0) 'TOTALS2', " +
  		    	"'' AS CONCAT1, " +  
  		    	
  		    	/*MÃ‰DIA*/	
  		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG AUTOS1', " +
  		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG COM1', " +
  		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG MOTOS1', " +
  		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'AVG TOTALS1', " + 

  		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG AUTOS2',  " +
  		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG COM2', " +
  		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'AVG MOTOS2', " +
  		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'AVG TOTALS2', " +
  		    	"'' AS CONCAT2, " + 
  		    	
  		    	/*VELOCIDADE 50*/

  		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
  		    	"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - AUTO1', " +

  		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
  		    	"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - COM1', " +

  		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - MOTO1', " +
  		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50 ) / 100 ELSE NULL END)),0),0)'AVG 50th - TOTAL1', " +

  		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
  		    	"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - AUTO2', " +

  		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
  		    	"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - COM2', " +

  		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 50) / 100 ELSE NULL END )),0),0) 'AVG 50th - MOTO2', " +
  		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND eq.equip_id = '"+stations[st]+"'  THEN (st.speed * 50 ) / 100 ELSE NULL END)),0),0)'AVG 50th - TOTAL2', " +
  		    	"'' AS CONCAT3, " +  
  		    	
  		    	/*VELOCIDADE 85*/

  		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
  		    	"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - AUTO1', " +
  		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
  		    	"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - COM1',  " +

  		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - MOTO1', " +
  		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85 ) / 100 ELSE NULL END)),0),0)'AVG 85th - TOTAL1', " +

  		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR " +
  		    	"st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - AUTO2', " +

  		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe <> '"+RoadConcessionaire.classLight+"' " +
  		    	"AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - COM2', " +

  		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN (st.speed * 85) / 100 ELSE NULL END )),0),0) 'AVG 85th - MOTO2', " +
  		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND eq.equip_id = '"+stations[st]+"'  THEN (st.speed * 85 ) / 100 ELSE NULL END)),0),0)'AVG 85th - TOTAL2', " +
  		    	"'' AS CONCAT4, " +  
  		    	
  		    	/*MÃ�XIMA*/
  		    	           	  				 
  		    	"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX AUTOS1', " +
  		    	"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX COM1', " +
  		    	"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.speed ELSE NULL END )) AND eq.equip_id = '"+stations[st]+"',0),0) 'MAX MOTOS1', " + 
  		    	"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END)),0),0) 'MAX TOTALS1', " +

  		    	"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX AUTOS2', " +
  		    	"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX COM2', " +
  		    	"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MAX MOTOS2', " +
  		    	"IFNULL(ROUND(MAX(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'MAX TOTALS2', " +
  		    	"'' AS CONCAT5, " +  
  		    	
  		    	/*MÃ�NIMA*/
  		    	"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN AUTOS1', " +
  		    	"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN COM1', " +
  		    	"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN MOTOS1', " +
  		    	"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'MIN TOTALS1', " +

  		    	"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN AUTOS2', " +
  		    	"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN COM2', " + 
  		    	"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'MIN MOTOS2', " +
  		    	"IFNULL(ROUND(MIN(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END)),0),0) 'MIN TOTALS2', " +
  		    	"'' AS CONCAT6, " +  	
  		    	
  		    	/*DESVIO PADRÃƒO */				
  		    	"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV AUTOS1', " +
  		    	"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV COM1', " +
  		    	"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV MOTOS1', " +
  		    	"IFNULL(ROUND(STD(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'DESV TOTALS1', " +

  		    	"IFNULL(ROUND(STD(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV AUTOS2', " +
  		    	"IFNULL(ROUND(STD(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10)) AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV COM2', " + 
  		    	"IFNULL(ROUND(STD(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') AND eq.equip_id = '"+stations[st]+"' THEN st.speed ELSE NULL END )),0),0) 'DESV MOTOS2', " +
  		    	"IFNULL(ROUND(STD(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND eq.equip_id = '"+stations[st]+"'  THEN st.speed ELSE NULL END)),0),0) 'DESV TOTALS2' ";
  		    	           	           		   	           		  	  
  		      }
  		      
       	   } //for equipments
		      
		      return query;
     	  
       }
	                  
            public String MonthlyFlowMainQuery(String station_id, int lanes) {
 	                	  
	                	  String query = "";
	                	  	                	  
	                	  if(lanes == 2) {
	           		    	  
		           		    	query = "IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 1) AND eq.equip_id = '"+station_id+"' AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"')) THEN 1 ELSE NULL END )),0),0) 'MOTOS1', " +
		           		    	"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 1) AND eq.equip_id = '"+station_id+"' AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"')) THEN 1 ELSE NULL END )),0),0) 'AUTOS1', " +
		           		    	"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 1) AND eq.equip_id = '"+station_id+"' AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10))) THEN 1 ELSE NULL END )),0),0) 'COM1', " +
		           		    	           		    			           		    	
                                "IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 2) AND eq.equip_id = '"+station_id+"' AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"')) THEN 1 ELSE NULL END )),0),0) 'MOTOS2', " +
		           		    	"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 2) AND eq.equip_id = '"+station_id+"' AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.lane=2 AND st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"')) THEN 1 ELSE NULL END )),0),0) 'AUTOS2', " +
		           		    	"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 2) AND eq.equip_id = '"+station_id+"' AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10))) THEN 1 ELSE NULL END )),0),0) 'COM2', " +
		           		      		           		    	
                                "IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1) AND eq.equip_id = '"+station_id+"' THEN st.speed ELSE NULL END)),0),0) 'AVG SPEEDS1', " +
                                "IFNULL(ROUND(AVG(CASEWHEN (st.lane = 2) AND eq.equip_id = '"+station_id+"' THEN st.speed ELSE NULL END)),0),0) 'AVG SPEEDS2', " +
                                
                                "IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND eq.equip_id = '"+station_id+"' THEN 1 ELSE NULL END)),0),0) 'TOTALS1', " +
		           		    	"IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND eq.equip_id = '"+station_id+"' THEN 1 ELSE NULL END)),0),0) 'TOTALS2' " ;
		           		    	
		           		      }	  
	                	  
	                	  if(lanes == 3) {
	           		    	  
		           		    	query = "IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 1) AND eq.equip_id = '"+station_id+"' AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"')) THEN 1 ELSE NULL END )),0),0) 'MOTOS1', " +
				           		"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 1) AND eq.equip_id = '"+station_id+"' AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"')) THEN 1 ELSE NULL END )),0),0) 'AUTOS1', " +
		           		    	"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 1) AND eq.equip_id = '"+station_id+"' AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10))) THEN 1 ELSE NULL END )),0),0) 'COM1', " +
		           		              		    	
                                "IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+station_id+"' AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"')) THEN 1 ELSE NULL END )),0),0) 'MOTOS2', " +		           		    	
		           		    	"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+station_id+"' AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.lane=2 AND st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"')) THEN 1 ELSE NULL END )),0),0) 'AUTOS2', " +
		           		    	"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+station_id+"' AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10))) THEN 1 ELSE NULL END )),0),0) 'COM2', " +
		           		    	
   	                            "IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1) AND eq.equip_id = '"+station_id+"' THEN st.speed ELSE NULL END)),0),0) 'AVG SPEEDS1', " +
		           		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+station_id+"' THEN st.speed ELSE NULL END)),0),0) 'AVG SPEEDS2', " +  
		           		 		           		    	
                                "IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND eq.equip_id = '"+station_id+"' THEN 1 ELSE NULL END)),0),0) 'TOTALS1', " +
                                "IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+station_id+"' THEN 1 ELSE NULL END)),0),0) 'TOTALS2' " ;
		           		    			           		    	
		           		      }	  
	                	  
	                	  if(lanes == 4) {
	           		    	  
		           		    	query = "IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 1 OR st.lane = 2) AND eq.equip_id = '"+station_id+"' AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"')) THEN 1 ELSE NULL END )),0),0) 'MOTOS1', " +
		           		    	"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 1 OR st.lane = 2) AND eq.equip_id = '"+station_id+"' AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"')) THEN 1 ELSE NULL END )),0),0) 'AUTOS1', " +
		           		    	"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 1 OR st.lane = 2) AND eq.equip_id = '"+station_id+"' AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10))) THEN 1 ELSE NULL END )),0),0) 'COM1', " +
		         		    			         		    		           		    	
		           		        "IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+station_id+"' AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"')) THEN 1 ELSE NULL END )),0),0) 'MOTOS2', " +
				           		"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+station_id+"' AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.lane=2 AND st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"')) THEN 1 ELSE NULL END )),0),0) 'AUTOS2', " +
		           		    	"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+station_id+"' AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10))) THEN 1 ELSE NULL END )),0),0) 'COM2', " +
		           		    			           		    	  	    
                               "IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2) AND eq.equip_id = '"+station_id+"' THEN st.speed ELSE NULL END)),0),0) 'AVG SPEEDS1', " +
                               "IFNULL(ROUND(AVG(CASEWHEN (st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+station_id+"' THEN st.speed ELSE NULL END)),0),0) 'AVG SPEEDS2', " +
		           		    	
		           		 	   "IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND eq.equip_id = '"+station_id+"' THEN 1 ELSE NULL END)),0),0) 'TOTALS1', " +
		           		 	   "IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+station_id+"' THEN 1 ELSE NULL END)),0),0) 'TOTALS2' ";
    	                                                      		           		      
	                	    }	  
	                	  
	                	  if(lanes == 5) {
	           		    	  
		           		    	query = "IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+station_id+"' AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"')) THEN 1 ELSE NULL END )),0),0) 'MOTOS1', " +
		           		    	"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+station_id+"' AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"')) THEN 1 ELSE NULL END )),0),0) 'AUTOS1', " +
		           		    	"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+station_id+"' AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10))) THEN 1 ELSE NULL END )),0),0) 'COM1', " +
		           		    		           		    	
                                "IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 4 OR st.lane = 5) AND eq.equip_id = '"+station_id+"' AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"')) THEN 1 ELSE NULL END )),0),0) 'MOTOS2', " +
   		           		    	"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 4 OR st.lane = 5) AND eq.equip_id = '"+station_id+"' AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.lane=2 AND st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"')) THEN 1 ELSE NULL END )),0),0) 'AUTOS2', " +
		           		    	"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 4 OR st.lane = 5) AND eq.equip_id = '"+station_id+"' AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10))) THEN 1 ELSE NULL END )),0),0) 'COM2', " +
		           		     			           		    	
                                "IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+station_id+"' THEN st.speed ELSE NULL END)),0),0) 'AVG SPEEDS1', " +
		           		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 4 OR st.lane = 5) AND eq.equip_id = '"+station_id+"' THEN st.speed ELSE NULL END)),0),0) 'AVG SPEEDS2', " +	 
		           		    	
	                            "IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+station_id+"' THEN 1 ELSE NULL END)),0),0) 'TOTALS1', " +
			           		    "IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND eq.equip_id = '"+station_id+"' THEN 1 ELSE NULL END)),0),0) 'TOTALS2' " ;
		           		    	  	           		    	  
		           		      }	  
	                	  
	                   	  if(lanes == 6) {
	           		    	  
		           		    	query = "IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+station_id+"' AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"')) THEN 1 ELSE NULL END )),0),0) 'MOTOS1', " +
		           		    	"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+station_id+"' AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"')) THEN 1 ELSE NULL END )),0),0) 'AUTOS1', " +
		           		    	"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+station_id+"' AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10))) THEN 1 ELSE NULL END )),0),0) 'COM1', " +
		           		    			           		 	  		           		    	
                                "IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND eq.equip_id = '"+station_id+"' AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"')) THEN 1 ELSE NULL END )),0),0) 'MOTOS2', " +
		           		    	"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND eq.equip_id = '"+station_id+"' AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.lane=2 AND st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"')) THEN 1 ELSE NULL END )),0),0) 'AUTOS2', " +
		           		    	"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND eq.equip_id = '"+station_id+"' AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10))) THEN 1 ELSE NULL END )),0),0) 'COM2', " +
		           		                   
                                "IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+station_id+"' THEN st.speed ELSE NULL END)),0),0) 'AVG SPEEDS1', " +
		           		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND eq.equip_id = '"+station_id+"' THEN st.speed ELSE NULL END)),0),0) 'AVG SPEEDS2', " +		
                                
                                "IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND eq.equip_id = '"+station_id+"' THEN 1 ELSE NULL END)),0),0) 'TOTALS1', " +
                                "IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND eq.equip_id = '"+station_id+"' THEN 1 ELSE NULL END)),0),0) 'TOTALS2' " ;

		           		      }	    
	                	  
	                	  if(lanes == 7) {
	           		    	  
		           		    	query = "IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+station_id+"' AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"')) THEN 1 ELSE NULL END )),0),0) 'MOTOS1', " +
		           		    	"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+station_id+"' AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"')) THEN 1 ELSE NULL END )),0),0) 'AUTOS1', " +
		           		    	"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+station_id+"' AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10))) THEN 1 ELSE NULL END )),0),0) 'COM1', " +
		           		    			           		    			           		    	
                                "IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND eq.equip_id = '"+station_id+"' AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"')) THEN 1 ELSE NULL END )),0),0) 'MOTOS2', " +
		           		    	"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND eq.equip_id = '"+station_id+"' AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.lane=2 AND st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"')) THEN 1 ELSE NULL END )),0),0) 'AUTOS2', " +
		           		    	"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND eq.equip_id = '"+station_id+"' AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10))) THEN 1 ELSE NULL END )),0),0) 'COM2', " +
		           		    	
                               "IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+station_id+"' THEN st.speed ELSE NULL END)),0),0) 'AVG SPEEDS1', " +		           		  	 		           		    	
                               "IFNULL(ROUND(AVG(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND eq.equip_id = '"+station_id+"' THEN st.speed ELSE NULL END)),0),0) 'AVG SPEEDS2', " +
                               
                               "IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND eq.equip_id = '"+station_id+"' THEN 1 ELSE NULL END)),0),0) 'TOTALS1', " +		           		    	
		           		        "IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND eq.equip_id = '"+station_id+"' THEN 1 ELSE NULL END)),0),0) 'TOTALS2' " ;

		           		      }	    
	           		   
	           		      if(lanes == 8) {
	           		    	  
	           		    	query = "IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"')) THEN 1 ELSE NULL END )),0),0) 'MOTOS1', " +
	           		    	"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"')) THEN 1 ELSE NULL END )),0),0) 'AUTOS1', " +
	           		    	"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10))) THEN 1 ELSE NULL END )),0),0) 'COM1', " +
	           		 	  	           		       	           		    	   
	           		    	"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8)  AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"')) THEN 1 ELSE NULL END )),0),0) 'MOTOS2', " +
	           		    	"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8)  AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.lane=2 AND st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"')) THEN 1 ELSE NULL END )),0),0) 'AUTOS2', " +
	           		    	"IFNULL(ROUND(COUNT(CASEWHEN ((st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8)  AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10))) THEN 1 ELSE NULL END )),0),0) 'COM2', " +
	           		    	
                            "IFNULL(ROUND(AVG(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4)  THEN st.speed ELSE NULL END)),0),0) 'AVG SPEEDS1', " +
	           		    	"IFNULL(ROUND(AVG(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8)  THEN st.speed ELSE NULL END)),0),0) 'AVG SPEEDS2', " +
	           		           		    	           		    	
	           		        "IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) THEN 1 ELSE NULL END)),0),0) 'TOTALS1', " +
	           		        "IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) THEN 1 ELSE NULL END)),0),0) 'TOTALS2' " ;
	           		    	
	           		      }	    
	           		      
	           		      return query;
                     
	           		   }
                                                     
                       public String CountVehiclesDirectionMainQuery(String station_id) {
                    	   
                    	                     	                                         	    	  
                    	   String query ="CASE " +
                            	   "WHEN eq.number_lanes = 2  THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "ELSE 0 " + 
                            	   "END 'LEVES_S1', " +
                            	   
                                   "CASE " +
                            	   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "ELSE 0   " +
                            	   "END 'MOTOS_S1', " +
                            	   
                            	   "CASE " +
                            	   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' ) THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' ) THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' ) THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' ) THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' ) THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "ELSE 0   " +
                            	   "END 'COM_S1', " +
                            	                    		
                            	   "CASE " +
                            	   "WHEN eq.number_lanes = 2  THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "ELSE 0 " + 
                            	   "END 'LEVES_S2', " +
                            	   
                                   "CASE " +
                            	   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "ELSE 0   " +
                            	   "END 'MOTOS_S2', " +
                            	   
                            	   "CASE " +
                            	   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' ) THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' ) THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' ) THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' ) THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"'  ) THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' ) THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND (st.classe <> '"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"'  ) THEN st.classe ELSE NULL END )), 0), 0) " +
                            	   "ELSE 0   " +
                            	   "END 'COM_S2', " +
                            	   
                                   "IFNULL(ROUND(COUNT(st.classe), 0), 0) 'TOTAL'  ";
               	                      	 
                    	   
                    	   return query;
                    	   
                       }
                           /////////////////////////////////         
                          ///COUNT VEHICLES QUERY
                          /////////////////////////////////
                          public String CountVehiclesMainQuery(String[] station_id) {
                    	   
                    	   String query = "";
                    	   String total = " SUM(";
                    	   
                    	                     	   
                    	   for(int i = 0; i < station_id.length; i++) {				
                    			 
                               query += " SUM(st.siteID = "+station_id[i]+") 'equip "+(i+1)+"', " ;                       		   	             
                    	               	                    	   
                    	       //Adding total count
              			        total +="st.siteID = '"+station_id[i]+"'";
              			
              			       if(station_id[i] != station_id[station_id.length-1])                    				
              				    total += " OR ";
              			
                    	   }
                    	   
                    	        total += ") 'TOTAL' ";
                    	   
                    	        query += total;                    	                                              	                       	   
                    	   
                    	        return query;  	 
                    	                       	   
                          }
                                                                              
                          public String VBVs() { 
                        	  
                        	  String query = "SELECT siteID, seqG, seqN, data, classe, axlNumber, axl1W, axl2W, axl3W, axl4W, " +
                            		         "axl5W, axl6W, axl7W, axl8W, axl9W, axl2D, axl3D, axl4D, axl5D,  " +
                            		         "axl6D, axl7D, axl8D, axl9D, gross, temperature, speed, lane " +
                            		         "FROM sat_vbv " +
                            		         "WHERE DATE(data) BETWEEN ? AND ? AND siteID = ? " +
                            		         "ORDER BY data ASC";
                        	  
                        	  return query;
                        	  
                          }
                          
                           public String VBVCount() { 
                        	  
                        	  String query = "SELECT COUNT(*) FROM sat_vbv " +                            		        
                            		         "WHERE DATE(data) BETWEEN ? AND ? AND siteID = ? ";                            		        
                        	  
                        	  return query;
                        	  
                          }                           
                           
                   public String PeriodFlowHeaderSelectQuery(String period) {
                	   
                	   String periodQuery = "";
                	                   	   
                	   if(period.equals("05 minutes"))
                		   periodQuery = "DATE_FORMAT(datetime_05, '%d') AS day_, " +
                		         "DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(datetime_05) - TIME_TO_SEC(datetime_05)%(05*60))),'%H:%i') AS startTime, " +
                		         "' - ' AS trace, "  +
                		         "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(datetime_05) + 240) - TIME_TO_SEC(datetime_05)%(05*60))),'%H:%i') AS endTime, ";
                	   
                	   if(period.equals("06 minutes"))
                		   periodQuery = "DATE_FORMAT(datetime_06, '%d') AS day_, " +
                		         "DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(datetime_06) - TIME_TO_SEC(datetime_06)%(06*60))),'%H:%i') AS startTime, " +
                		         "' - ' AS trace, "  +
                		         "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(datetime_06) + 300) - TIME_TO_SEC(datetime_06)%(06*60))),'%H:%i') AS endTime, ";
                	   
                	   if(period.equals("10 minutes"))
                		   periodQuery = "DATE_FORMAT(datetime_10, '%d') AS day_, " +
                		         "DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(datetime_10) - TIME_TO_SEC(datetime_10)%(10*60))),'%H:%i') AS startTime, " +
                		         "' - ' AS trace, "  +
                		         "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(datetime_10) + 540) - TIME_TO_SEC(datetime_10)%(10*60))),'%H:%i') AS endTime, ";
                	   
                	   
                	   if(period.equals("15 minutes"))
                		   periodQuery = "DATE_FORMAT(datetime_15, '%d') AS day_, " +                		           
                		   		   "DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(datetime_15) - TIME_TO_SEC(datetime_15)%(15*60))),'%H:%i') AS startTime, " +
                				   "' - ' AS trace, " +   
                				   "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(datetime_15) + 840) - TIME_TO_SEC(datetime_15)%(15*60))),'%H:%i') AS endTime, ";
                	   
	                  if(period.equals("30 minutes"))
	                	  periodQuery = "DATE_FORMAT(datetime_30, '%d') AS day_, " +
		                           "DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(datetime_30) - TIME_TO_SEC(datetime_30)%(30*60))),'%H:%i') AS startTime, " +
				                   "' - ' AS trace, " +   
				                   "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(datetime_30) + 1740) - TIME_TO_SEC(datetime_30)%(30*60))),'%H:%i') AS endTime, ";
	   
	                  if(period.equals("01 hour"))
	                	  periodQuery = "DATE_FORMAT(datetime_hour, '%d') AS day_, " +
		                           "DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(datetime_hour) - TIME_TO_SEC(datetime_hour)%(60*60))),'%H:%i') AS startTime, " +
				                   "' - ' AS trace, " +   
				                   "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(datetime_hour) + 3599) - TIME_TO_SEC(datetime_hour)%(60*60))),'%H:%i') AS endTime, ";
	                 
	                  if(period.equals("06 hours"))
	                	  periodQuery = "DATE_FORMAT(datetime_six_hours, '%d') AS day_, " +
		                           "DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(datetime_six_hours) - TIME_TO_SEC(datetime_six_hours)%(360*60))),'%H:%i') AS startTime, " +
				                   "' - ' AS trace, " +   
				                   "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC(datetime_six_hours) + 21599) - TIME_TO_SEC(datetime_six_hours)%(360*60))),'%H:%i') AS endTime, ";
	   
	   
	                  if(period.equals("24 hours"))
	                	  periodQuery = "DATE_FORMAT(date_, '%d') AS day_, " +
               		                  "' ---- ' AS startTime, " +
               		                  "' - ' AS trace, " +   
               		                  "' ---- ' AS endTime, ";          	   
                	   
                	   return periodQuery;
                	   
                   }
                           
                     // -------------------------------------------------------------------- //      
                    // ---------------------------- CCR QUERIES --------------------------- //
                   // -------------------------------------------------------------------- //                   
                   
                     public String CCRTipos(String station_id) {
                	   
                	   String query = "";
                	   
                	   query += "IFNULL(ROUND(COUNT(CASEWHEN st.classe = '"+RoadConcessionaire.classLight+"' OR st.classe = '"+RoadConcessionaire.classMotorcycle+"' OR st.classe = '"+RoadConcessionaire.classSemiTrailer+"' THEN st.classe ELSE NULL END)), 0), 0) 'LEVES', " +
               		   		"IFNULL(ROUND(COUNT(CASEWHEN st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND st.classe <> '"+RoadConcessionaire.classSemiTrailer+"' THEN st.classe ELSE NULL END)), 0), 0) 'COMM', " +
               		   		"IFNULL(ROUND(COUNT(st.classe), 0), 0) 'TOTAL', " +

               		   		"CASE " + 
               		   		"WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR st.classe = '"+RoadConcessionaire.classMotorcycle+"' OR st.classe = '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0) " +
               		   		"WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR st.classe = '"+RoadConcessionaire.classMotorcycle+"' OR st.classe = '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0) " +
               		   		"WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR st.classe = '"+RoadConcessionaire.classMotorcycle+"' OR st.classe = '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0) " +
               		   		"WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR st.classe = '"+RoadConcessionaire.classMotorcycle+"' OR st.classe = '"+RoadConcessionaire.classSemiTrailer+"') AND eq.equip_id = '"+station_id+"' THEN st.classe ELSE NULL END)), 0), 0) " +

               		   		"ELSE 0  " +
               		   		"END 'LEVES_S1', " +
               		 
               		   		"CASE   " +
               		   		"WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND st.classe <> '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0) " +
               		   		"WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND st.classe <> '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0) " +
               		   		"WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND st.classe <> '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0) " +
               		   		"WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND st.classe <> '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0) " +

               		   		"ELSE 0 " +
               		   		"END 'COMM_S1', " +

               		   		"CASE   " +
               		   		"WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) THEN st.classe ELSE NULL END)) , 0), 0) " +
               		   		"WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) THEN st.classe ELSE NULL END)), 0), 0) " +
               		   		"WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) THEN st.classe ELSE NULL END)), 0), 0) " +
               		   		"WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) THEN st.classe ELSE NULL END)), 0), 0) " +

               		   		"ELSE 0  " +
               		   		"END 'TOTAL_S1', " +

               		   		"CASE   " +
               		   		"WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR st.classe = '"+RoadConcessionaire.classMotorcycle+"' OR st.classe = '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0) " +
               		   		"WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR st.classe = '"+RoadConcessionaire.classMotorcycle+"' OR st.classe = '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0) " +
               		   		"WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR st.classe = '"+RoadConcessionaire.classMotorcycle+"' OR st.classe = '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0) " +
               		   		"WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR st.classe = '"+RoadConcessionaire.classMotorcycle+"' OR st.classe = '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0) " +
               		   		"WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR st.classe = '"+RoadConcessionaire.classMotorcycle+"' OR st.classe = '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0) " +
               		   		"WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR st.classe = '"+RoadConcessionaire.classMotorcycle+"' OR st.classe = '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0) " + 
               		   		"WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR st.classe = '"+RoadConcessionaire.classMotorcycle+"' OR st.classe = '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0) " +

               		   		"ELSE 0 " +
               		   		"END 'LEVES_S2', " +
               		   		                
               		   		"CASE   " +
               		   		"WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND st.classe <> '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0) " +
               		   		"WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND st.classe <> '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0) " +
               		   		"WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND st.classe <> '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0) " + 
               		   		"WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND st.classe <> '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0) " +
               		   		"WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND st.classe <> '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0) " +
               		   		"WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> '"+RoadConcessionaire.classMotorcycle+"' AND st.classe <> '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0) " +
               		   		"WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe <> '"+RoadConcessionaire.classLight+"' AND st.classe <> 9 AND st.classe <> '"+RoadConcessionaire.classSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0) " +
               		 
               		   		"ELSE 0  " +
               		   		"END 'COMM_S2', " + 

               		   		"CASE  " +
               		   		"WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) THEN st.classe ELSE NULL END)), 0), 0) " +
               		   		"WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) THEN st.classe ELSE NULL END)), 0), 0) " +
               		   		"WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) THEN st.classe ELSE NULL END)), 0), 0) " +
               		   		"WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) THEN st.classe ELSE NULL END)), 0), 0) " +
               		   		"WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) THEN st.classe ELSE NULL END)), 0), 0) " +
               		   		"WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) THEN st.classe ELSE NULL END)), 0), 0) " +
               		   		"WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) THEN st.classe ELSE NULL END)), 0), 0) " +

               		   		"ELSE 0  " +
               		   		"END 'TOTAL_S2'  ";
               	   
                	   return query;
                   }
                   
                   public String CCRClasses(String station_id) {
                	   
                	   String query = "";
                	   
                	   query += "IFNULL(ROUND(COUNT(CASEWHEN st.classe = '"+RoadConcessionaire.classMotorcycle+"' THEN st.classe ELSE NULL END)), 0), 0) 'MOTOS', " +
                  			 "IFNULL(ROUND(COUNT(CASEWHEN st.classe = '"+RoadConcessionaire.classLight+"' OR st.classe = '"+RoadConcessionaire.classSemiTrailer+"' OR st.classe = '"+RoadConcessionaire.classUnknown+"' THEN st.classe ELSE NULL END)), 0), 0) 'LEVES', " +
                  		 	 "IFNULL(ROUND(COUNT(CASEWHEN st.classe = '"+ RoadConcessionaire.classTruck2Axles+"' THEN st.classe ELSE NULL END)), 0), 0) 'PEQUENO', " +
                  		 	 "IFNULL(ROUND(COUNT(CASEWHEN st.classe = '"+ RoadConcessionaire.classTruck6Axles+"' THEN st.classe ELSE NULL END)), 0), 0) 'LONGOS', " +
                  		 	 "IFNULL(ROUND(COUNT(CASEWHEN st.classe = '"+ RoadConcessionaire.classBus2Axles+"' THEN st.classe ELSE NULL END)), 0), 0) 'BUS', " +
                  		 	 "IFNULL(ROUND(COUNT(st.classe), 0), 0) 'TOTAL', " +
                  	
                  	 "CASE   " +
                  	 "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classMotorcycle+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

                  	 "ELSE 0  " +
                  	 "END 'MOTOS_S1',  " +

                  	 "CASE   " +
                  	 "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR st.classe = '"+RoadConcessionaire.classSemiTrailer+"' OR st.classe = '"+RoadConcessionaire.classUnknown+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR st.classe = '"+RoadConcessionaire.classSemiTrailer+"' OR st.classe = '"+RoadConcessionaire.classUnknown+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR st.classe = '"+RoadConcessionaire.classSemiTrailer+"' OR st.classe = '"+RoadConcessionaire.classUnknown+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR st.classe = '"+RoadConcessionaire.classSemiTrailer+"' OR st.classe = '"+RoadConcessionaire.classUnknown+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

                  	 "ELSE 0   " +
                  	 "END 'LEVES_S1',  " +
                  	   
                  	 "CASE   " +
                  	 "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+ RoadConcessionaire.classTruck2Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+ RoadConcessionaire.classTruck2Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+ RoadConcessionaire.classTruck2Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+ RoadConcessionaire.classTruck2Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

                  	 "ELSE 0   " +
                  	 "END 'PEQUENOS_S1', " +
                  	          
                  	 "CASE   " +
                  	 "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+ RoadConcessionaire.classTruck6Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+ RoadConcessionaire.classTruck6Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+ RoadConcessionaire.classTruck6Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+ RoadConcessionaire.classTruck6Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

                  	 "ELSE 0   " +
                  	 "END 'LONGOS_S1',  " +
                  	     
                  	 "CASE   " +
                  	 "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+ RoadConcessionaire.classBus2Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+ RoadConcessionaire.classBus2Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+ RoadConcessionaire.classBus2Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+ RoadConcessionaire.classBus2Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

                  	 "ELSE 0  " +
                  	 "END 'BUS_S1',  " +
                  	   
                  	 "CASE   " +
                  	 "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) THEN st.classe ELSE NULL END)), 0), 0)  " +

                  	 "ELSE 0   " +
                  	 "END 'TOTAL_S1', " +
               
                  	 "CASE " +
                  	 "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND st.classe = '"+RoadConcessionaire.classMotorcycle+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND st.classe = '"+RoadConcessionaire.classMotorcycle+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND st.classe = '"+RoadConcessionaire.classMotorcycle+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND st.classe = '"+RoadConcessionaire.classMotorcycle+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND st.classe = '"+RoadConcessionaire.classMotorcycle+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND st.classe = '"+RoadConcessionaire.classMotorcycle+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND st.classe = '"+RoadConcessionaire.classMotorcycle+"' THEN st.classe ELSE NULL END)), 0), 0)  " +

                  	 "ELSE 0 " +
                  	 "END 'MOTOS_S2', " +

                  	 "CASE  " +
                  	 "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR st.classe = '"+RoadConcessionaire.classSemiTrailer+"' OR st.classe = '"+RoadConcessionaire.classUnknown+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR st.classe = '"+RoadConcessionaire.classSemiTrailer+"' OR st.classe = '"+RoadConcessionaire.classUnknown+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR st.classe = '"+RoadConcessionaire.classSemiTrailer+"' OR st.classe = '"+RoadConcessionaire.classUnknown+"') THEN st.classe ELSE NULL END)), 0), 0)  " + 
                  	 "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR st.classe = '"+RoadConcessionaire.classSemiTrailer+"' OR st.classe = '"+RoadConcessionaire.classUnknown+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR st.classe = '"+RoadConcessionaire.classSemiTrailer+"' OR st.classe = '"+RoadConcessionaire.classUnknown+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR st.classe = '"+RoadConcessionaire.classSemiTrailer+"' OR st.classe = '"+RoadConcessionaire.classUnknown+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classLight+"' OR st.classe = '"+RoadConcessionaire.classSemiTrailer+"' OR st.classe = '"+RoadConcessionaire.classUnknown+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

                  	 "ELSE 0  " +
                  	 "END 'LEVES_S2',  " +

                  	 "CASE   " +
                  	 "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND st.classe = '"+RoadConcessionaire.classTruck2Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND st.classe = '"+ RoadConcessionaire.classTruck2Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND st.classe = '"+ RoadConcessionaire.classTruck2Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND st.classe = '"+ RoadConcessionaire.classTruck2Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND st.classe = '"+ RoadConcessionaire.classTruck2Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND st.classe = '"+ RoadConcessionaire.classTruck2Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND st.classe = '"+ RoadConcessionaire.classTruck2Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +

                  	 "ELSE 0  " +
                  	 "END 'PEQUENOS_S2',  " +

                  	 "CASE   " +
                  	 "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND st.classe = '"+ RoadConcessionaire.classTruck6Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND st.classe = '"+ RoadConcessionaire.classTruck6Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND st.classe = '"+ RoadConcessionaire.classTruck6Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND st.classe = '"+ RoadConcessionaire.classTruck6Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND st.classe = '"+ RoadConcessionaire.classTruck6Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND st.classe = '"+ RoadConcessionaire.classTruck6Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND st.classe = '"+ RoadConcessionaire.classTruck6Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +

                  	 "ELSE 0   " +
                  	 "END 'LONGOS_S2',  " +

                  	 "CASE    " +
                  	 "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND st.classe = '"+ RoadConcessionaire.classBus2Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND st.classe = '"+ RoadConcessionaire.classBus2Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND st.classe = '"+ RoadConcessionaire.classBus2Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND st.classe = '"+ RoadConcessionaire.classBus2Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND st.classe = '"+ RoadConcessionaire.classBus2Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND st.classe = '"+ RoadConcessionaire.classBus2Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND st.classe = '"+ RoadConcessionaire.classBus2Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +

                  	 "ELSE 0   " +
                  	 "END 'BUS_S2',  " +
                  	                 
                  	 "CASE    " +
                  	 "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) THEN st.classe ELSE NULL END)), 0), 0)  " +
                  	 "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) THEN st.classe ELSE NULL END)), 0), 0)  " +

                  	 "ELSE 0   " +
                  	 "END 'TOTAL_S2' ";                            	   
              	   
                	   return query;
                	   
                   }
                   
                   
                   public String  CCRVelocidade(String station_id) {
                	   
                	   String query = "";
                	   
                	   query += "IFNULL(ROUND(COUNT(CASEWHEN st.speed <= 50  THEN st.speed ELSE NULL END)), 0), 0) 'KM50',  " +
                			   "IFNULL(ROUND(COUNT(CASEWHEN st.speed > 50 and st.speed <= 70  THEN st.speed ELSE NULL END)), 0), 0) 'KM50_70', " +
                			   "IFNULL(ROUND(COUNT(CASEWHEN st.speed > 70 and st.speed <= 90  THEN st.speed ELSE NULL END)), 0), 0) 'KM70_90', " + 
                			   "IFNULL(ROUND(COUNT(CASEWHEN st.speed > 90 and st.speed <= 120  THEN st.speed ELSE NULL END)), 0), 0) 'KM90_120', " + 
                			   "IFNULL(ROUND(COUNT(CASEWHEN st.speed > 120 and st.speed <= 150  THEN st.speed ELSE NULL END)), 0), 0) 'KM120_150', " +
                			   "IFNULL(ROUND(COUNT(CASEWHEN st.speed > 150  THEN st.speed ELSE NULL END)), 0), 0) 'KM_150_PLUS', " +					
                			   "IFNULL(ROUND(COUNT(st.speed), 0), 0) 'TOTAL', " +

                			   "CASE " + 
                			   "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.speed < 50)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.speed <= 50)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.speed <= 50)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.speed <= 50)  THEN st.speed ELSE NULL END)), 0), 0) " +

                			   "ELSE 0 " +
                			   "END 'KM50_S1', " +
                			     
                			   "CASE " +
                			   "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.speed > 50 and st.speed <= 70) AND eq.equip_id = '"+station_id+"'  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.speed > 50 and st.speed <= 70)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.speed > 50 and st.speed <= 70)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.speed > 50 and st.speed <= 70)  THEN st.speed ELSE NULL END)), 0), 0) " +

                			   "ELSE 0 " + 
                			   "END 'KM50_70_S1', " +
                			    
                			   "CASE " +
                			   "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.speed > 70 and st.speed <= 90) AND eq.equip_id = '"+station_id+"'  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.speed > 70 and st.speed <= 90)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.speed > 70 and st.speed <= 90)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.speed > 70 and st.speed <= 90)  THEN st.speed ELSE NULL END)), 0), 0) " +

                			   "ELSE 0 " +
                			   "END 'KM70_90_S1', " +
                			     
                			   "CASE " + 
                			   "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.speed > 90 and st.speed  <= 120) AND eq.equip_id = '"+station_id+"'  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.speed > 90 and st.speed  <= 120)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.speed > 90 and st.speed <= 120)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.speed > 90 and st.speed  <= 120)  THEN st.speed ELSE NULL END)), 0), 0) " +

                			   "ELSE 0 " +
                			   "END 'KM90_120_S1', " +
                			      
                			   "CASE " +
                		       "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.speed > 120 and st.speed <= 150) AND eq.equip_id = '"+station_id+"'  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.speed > 120 and st.speed <= 150)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.speed > 120 and st.speed <= 150)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.speed > 120 and st.speed <= 150)  THEN st.speed ELSE NULL END)), 0), 0) " +

                			   "ELSE 0 " +
                			   "END 'KM120_150_S1', "+
                			                  
                			   "CASE  " +
                			   "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.speed > 150)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.speed > 150)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.speed > 150)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.speed > 150)  THEN st.speed ELSE NULL END)), 0), 0) " +

                			   "ELSE 0 " +
                			   "END 'KM150_PLUS_S1', " +
                			          
                		       "CASE  " +
                			   "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4)  THEN st.speed ELSE NULL END)), 0), 0) " +

                			   "ELSE 0 " +
                			   "END 'TOTAL_S1', " +

                			   "CASE  " +
                			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.speed <= 50)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.speed <= 50)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.speed <= 50)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.speed <= 50)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.speed <= 50)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.speed <= 50)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.speed <= 50)  THEN st.speed ELSE NULL END)), 0), 0) " +

                			   "ELSE 0 " +
                			   "END 'KM50_S2', " +

                			   "CASE " +  
                			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.speed > 50 and st.speed <= 70)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.speed > 50 and st.speed <= 70)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.speed > 50 and st.speed <= 70)   THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.speed > 50 and st.speed <= 70)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.speed > 50 and st.speed <= 70)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.speed > 50 and st.speed <= 70)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.speed > 50 and st.speed <= 70)  THEN st.speed ELSE NULL END)), 0), 0) " +

                			   "ELSE 0  " +
                			   "END 'KM50_70_S2', " +

                			   "CASE  " +
                			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.speed > 70 and st.speed <= 90)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.speed > 70 and st.speed <= 90)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.speed > 70 and st.speed <= 90)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.speed > 70 and st.speed <= 90)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.speed > 70 and st.speed <= 90)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.speed > 70 and st.speed <= 90)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.speed > 70 and st.speed <= 90)  THEN st.speed ELSE NULL END)), 0), 0) " +

                			   "ELSE 0 " +
                			   "END 'KM70_90_S2', " +

                			   "CASE  " +
                			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.speed > 90 and st.speed  <= 120)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.speed > 90 and st.speed  <= 120)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.speed > 90 and st.speed  <= 120)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.speed > 90 and st.speed  <= 120)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.speed > 90 and st.speed  <= 120)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.speed > 90 and st.speed  <= 120)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.speed > 90 and st.speed  <= 120)  THEN st.speed ELSE NULL END)), 0), 0) " +

                			   "ELSE 0 " +
                			   "END 'KM90_120_S2', " +

                			   "CASE  " +
                			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.speed > 120 and st.speed <= 150)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.speed > 120 and st.speed <= 150)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.speed > 120 and st.speed <= 150)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.speed > 120 and st.speed <= 150)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.speed > 120 and st.speed <= 150)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.speed > 120 and st.speed <= 150)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.speed > 120 and st.speed <= 150)  THEN st.speed ELSE NULL END)), 0), 0) " +

                			   "ELSE 0 " +
                			   "END 'KM120_150_S2', " +

                			   "CASE   " +
                			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.speed > 150)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.speed > 150)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.speed > 150)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.speed > 150)  THEN st.speed ELSE NULL END)), 0), 0)  " +
                			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.speed > 150)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.speed > 150)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.speed > 150)  THEN st.speed ELSE NULL END)), 0), 0) " +

                			   "ELSE 0 " +
                			   "END 'KM150_PLUS_S2', " +
                			                   
                			   "CASE  " +
                			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7)  THEN st.speed ELSE NULL END)), 0), 0) " +
                			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8)  THEN st.speed ELSE NULL END)), 0), 0) " +

                			   "ELSE 0  " +
                			   "END 'TOTAL_S2' ";
                	   
                	           return query;
                	   
                   }
                                      
                   
                   public String CCRAllClasses(String station_id) {
                	   
                	   String query="";
                	   
                	   query += "IFNULL(ROUND(COUNT(CASEWHEN st.classe = '"+RoadConcessionaire.classCCRMotorcycle+"' THEN st.classe ELSE NULL END)), 0), 0) 'MOTOS', " +
            		  			 "IFNULL(ROUND(COUNT(CASEWHEN st.classe = '"+RoadConcessionaire.classCCRLight+"' THEN st.classe ELSE NULL END)), 0), 0) 'AUTOS', " +
            		  			 "IFNULL(ROUND(COUNT(CASEWHEN st.classe = '"+RoadConcessionaire.classCCRSemiTrailer+"' THEN st.classe ELSE NULL END)), 0), 0) 'SEMI-TRAILER', " +
            		  			 "IFNULL(ROUND(COUNT(CASEWHEN st.classe = '"+RoadConcessionaire.classCCRTrailer+"' THEN st.classe ELSE NULL END)), 0), 0) 'TRAILER', " +
            		  			 "IFNULL(ROUND(COUNT(CASEWHEN st.classe = '"+RoadConcessionaire.classCCRTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRTruckSimple2Axles1+"' OR st.classe = '"+RoadConcessionaire.classCCRTruckSimple2Axles2+"' THEN st.classe ELSE NULL END)), 0), 0) 'TRUCK 2 EIXOS', " +
            		  			 "IFNULL(ROUND(COUNT(CASEWHEN st.classe = '"+RoadConcessionaire.classCCRTruck3Axles+"' THEN st.classe ELSE NULL END)), 0), 0) 'TRUCK 3 EIXOS', " +
            		  			 "IFNULL(ROUND(COUNT(CASEWHEN st.classe = '"+RoadConcessionaire.classCCRTruck4Axles+"' THEN st.classe ELSE NULL END)), 0), 0) 'TRUCK 4 EIXOS', " +
            		  			 "IFNULL(ROUND(COUNT(CASEWHEN st.classe = '"+RoadConcessionaire.classCCRTruck5Axles+"' THEN st.classe ELSE NULL END)), 0), 0) 'TRUCK 5 EIXOS', " +
            		  			 "IFNULL(ROUND(COUNT(CASEWHEN st.classe = '"+RoadConcessionaire.classCCRTruck6Axles+"' THEN st.classe ELSE NULL END)), 0), 0) 'TRUCK 6 EIXOS', " +
            		  			 "IFNULL(ROUND(COUNT(CASEWHEN st.classe = '"+RoadConcessionaire.classCCRTruck7Axles+"' THEN st.classe ELSE NULL END)), 0), 0) 'TRUCK 7 EIXOS', " +
            		  			 "IFNULL(ROUND(COUNT(CASEWHEN st.classe = '"+RoadConcessionaire.classCCRTruck8Axles+"' THEN st.classe ELSE NULL END)), 0), 0) 'TRUCK 8 EIXOS', " +
            		  			 "IFNULL(ROUND(COUNT(CASEWHEN st.classe = '"+RoadConcessionaire.classCCRTruck9Axles+"' THEN st.classe ELSE NULL END)), 0), 0) 'TRUCK 9 EIXOS', " +
            		  			 "IFNULL(ROUND(COUNT(CASEWHEN st.classe = '"+RoadConcessionaire.classCCRTruck10Axles+"' THEN st.classe ELSE NULL END)), 0), 0) 'TRUCK 10 EIXOS', " +
            		  		 	 "IFNULL(ROUND(COUNT(CASEWHEN st.classe = '"+RoadConcessionaire.classCCRBus2Axles+"' THEN st.classe ELSE NULL END)), 0), 0) 'BUS 2 AXLES', " +
            		  		 	"IFNULL(ROUND(COUNT(CASEWHEN st.classe = '"+RoadConcessionaire.classCCRBus3Axles+"' THEN st.classe ELSE NULL END)), 0), 0) 'BUS 3 AXLES', " +
            		  		 	"IFNULL(ROUND(COUNT(CASEWHEN st.classe = '"+RoadConcessionaire.classCCRBus4Axles+"' THEN st.classe ELSE NULL END)), 0), 0) 'BUS 4 AXLES', " +
            		  		 	"IFNULL(ROUND(COUNT(CASEWHEN st.classe = '"+RoadConcessionaire.classCCRBus5Axles+"' THEN st.classe ELSE NULL END)), 0), 0) 'BUS 5 AXLES', " +
            		  		 	"IFNULL(ROUND(COUNT(CASEWHEN st.classe = '"+RoadConcessionaire.classCCRBus6Axles+"' THEN st.classe ELSE NULL END)), 0), 0) 'BUS 6 AXLES', " +
            		  		 	"IFNULL(ROUND(COUNT(st.classe), 0), 0) 'TOTAL', " +
            		  	
            		  	 "CASE   " +
            		  	 "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRMotorcycle+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRMotorcycle+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRMotorcycle+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRMotorcycle+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

            		  	 "ELSE 0  " +
            		  	 "END 'MOTOS_S1',  " +

            		  	 "CASE   " +
            		  	 "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRLight+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRLight+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRLight+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRLight+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

            		  	 "ELSE 0   " +
            		  	 "END 'AUTOS_S1',  " +
            		  	 
                       "CASE   " +
          	         "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
          	         "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
          	         "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
          	         "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

          	         "ELSE 0   " +
          	         "END 'SEMI_TRAILER_S1',  " +
          	         
                       "CASE   " +
                       "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRTrailer+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
                       "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRTrailer+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
                       "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTrailer+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
                       "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTrailer+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

                       "ELSE 0   " +
                       "END 'TRAILER_S1',  " +
            		  	   
            		  	 "CASE   " +
            		  	 "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRTruckSimple2Axles1+"' OR st.classe = '"+RoadConcessionaire.classCCRTruckSimple2Axles2+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRTruckSimple2Axles1+"' OR st.classe = '"+RoadConcessionaire.classCCRTruckSimple2Axles2+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck2Axles+"'  OR st.classe = '"+RoadConcessionaire.classCCRTruckSimple2Axles1+"' OR st.classe = '"+RoadConcessionaire.classCCRTruckSimple2Axles2+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRTruckSimple2Axles1+"' OR st.classe = '"+RoadConcessionaire.classCCRTruckSimple2Axles2+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

            		  	 "ELSE 0   " +
            		  	 "END 'TRUCK_2AXLES_S1',  " +
            		  	 
            		     "CASE   " +
            			 "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck3Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck3Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck3Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck3Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

            			 "ELSE 0   " +
            			 "END 'TRUCK_3AXLES_S1',  " +
            			 
            		     "CASE   " +
            			 "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck4Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck4Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck4Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck4Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

            			 "ELSE 0   " +
            			 "END 'TRUCK_4AXLES_S1',  " +
            			 
            		      "CASE   " +
            			 "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck5Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck5Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck5Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck5Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

            			 "ELSE 0   " +
            			 "END 'TRUCK_5AXLES_S1',  " +	 
            		  	          
            		  	 "CASE   " +
            		  	 "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck6Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck6Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck6Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck6Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

            		  	 "ELSE 0   " +
            		  	 "END 'TRUCK_6AXLES_S1',  " +
            		  	 
            		     "CASE   " +
            			 "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck7Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck7Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck7Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck7Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

            			 "ELSE 0   " +
            			 "END 'TRUCK_7AXLES_S1',  " +
            		  	 
            		     "CASE   " +
            			 "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck8Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck8Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck8Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck8Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

            			 "ELSE 0   " +
            			 "END 'TRUCK_8AXLES_S1',  " +
            		  	 
            		     "CASE   " +
            			 "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck9Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck9Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck9Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck9Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

            			 "ELSE 0   " +
            			 "END 'TRUCK_9AXLES_S1',  " +
            			 
            		     "CASE   " +
            			 "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck10Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck10Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck10Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+ RoadConcessionaire.classCCRTruck10Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

            			 "ELSE 0   " +
            			 "END 'TRUCK_10AXLES_S1',  " +
            		  	   	     
            		  	 "CASE   " +
            		  	 "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+ RoadConcessionaire.classCCRBus2Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+ RoadConcessionaire.classCCRBus2Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+ RoadConcessionaire.classCCRBus2Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+ RoadConcessionaire.classCCRBus2Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

            		  	 "ELSE 0  " +
            		  	 "END 'BUS_2AXLES_S1',  " +
            		  	 
            		     "CASE   " +
            			 "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+ RoadConcessionaire.classCCRBus3Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+ RoadConcessionaire.classCCRBus3Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+ RoadConcessionaire.classCCRBus3Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+ RoadConcessionaire.classCCRBus3Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

            			 "ELSE 0  " +
            			 "END 'BUS_3AXLES_S1',  " +
            			 
            		     "CASE   " +
            			 "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+ RoadConcessionaire.classCCRBus4Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+ RoadConcessionaire.classCCRBus4Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+ RoadConcessionaire.classCCRBus4Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+ RoadConcessionaire.classCCRBus4Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

            			 "ELSE 0  " +
            			 "END 'BUS_4AXLES_S1',  " +
            			 
            		     "CASE   " +
            			 "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+ RoadConcessionaire.classCCRBus5Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+ RoadConcessionaire.classCCRBus5Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+ RoadConcessionaire.classCCRBus5Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+ RoadConcessionaire.classCCRBus5Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

            			 "ELSE 0  " +
            			 "END 'BUS_5AXLES_S1',  " +
            			 
            		     "CASE   " +
            			 "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+ RoadConcessionaire.classCCRBus6Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+ RoadConcessionaire.classCCRBus6Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+ RoadConcessionaire.classCCRBus6Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+ RoadConcessionaire.classCCRBus6Axles+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

            			 "ELSE 0  " +
            			 "END 'BUS_6AXLES_S1',  " +
            		  	   
            		  	 "CASE   " +
            		  	 "WHEN eq.number_lanes = 2  OR eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 5 OR eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 7 OR eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) THEN st.classe ELSE NULL END)), 0), 0)  " +

            		  	 "ELSE 0   " +
            		  	 "END 'TOTAL_S1',  " +

            		  	 "CASE " +
            		  	 "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND st.classe = '"+RoadConcessionaire.classCCRMotorcycle+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND st.classe = '"+RoadConcessionaire.classCCRMotorcycle+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND st.classe = '"+RoadConcessionaire.classCCRMotorcycle+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND st.classe = '"+RoadConcessionaire.classCCRMotorcycle+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND st.classe = '"+RoadConcessionaire.classCCRMotorcycle+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND st.classe = '"+RoadConcessionaire.classCCRMotorcycle+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND st.classe = '"+RoadConcessionaire.classCCRMotorcycle+"' THEN st.classe ELSE NULL END)), 0), 0)  " +

            		  	 "ELSE 0 " +
            		  	 "END 'MOTOS_S2', " +

                       "CASE  " +
            		  	 "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRLight+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRLight+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRLight+"') THEN st.classe ELSE NULL END)), 0), 0)  " + 
            		  	 "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classCCRLight+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classCCRLight+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classCCRLight+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classCCRLight+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

            		  	 "ELSE 0  " +
            		  	 "END 'AUTOS_S2', " + 		  	 
                     
            		  	 "CASE  " +
          	  	     "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
          	  	     "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
          	  	     "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0)  " + 
          	  	     "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classCCRSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
          	  	     "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classCCRSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
          	  	     "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classCCRSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
          	  	     "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classCCRSemiTrailer+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

          	  	     "ELSE 0  " +
          	  	     "END 'SEMI_TRAILER_S2',  " +
          	  	     	  	     
          	         "CASE  " +
          	  	     "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRTrailer+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
          	  	     "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTrailer+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
          	  	     "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTrailer+"') THEN st.classe ELSE NULL END)), 0), 0)  " + 
          	  	     "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classCCRTrailer+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
          	  	     "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classCCRTrailer+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
          	  	     "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classCCRTrailer+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
          	  	     "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classCCRTrailer+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

          	  	     "ELSE 0  " +
          	  	     "END 'TRAILER_S2',  " +
          	  	     
                       "CASE  " +
          	         "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRTruck2Axles+"' OR st.classe = '" +RoadConcessionaire.classCCRTruckSimple2Axles1+ "' OR st.classe = '"+RoadConcessionaire.classCCRTruckSimple2Axles2+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
          	         "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTruck2Axles+"' OR st.classe = '" +RoadConcessionaire.classCCRTruckSimple2Axles1+ "' OR st.classe = '"+RoadConcessionaire.classCCRTruckSimple2Axles2+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
          	         "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTruck2Axles+"' OR st.classe = '" +RoadConcessionaire.classCCRTruckSimple2Axles1+ "' OR st.classe = '"+RoadConcessionaire.classCCRTruckSimple2Axles2+"') THEN st.classe ELSE NULL END)), 0), 0)  " + 
          	         "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classCCRTruck2Axles+"' OR st.classe = '" +RoadConcessionaire.classCCRTruckSimple2Axles1+ "' OR st.classe = '"+RoadConcessionaire.classCCRTruckSimple2Axles2+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
          	         "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classCCRTruck2Axles+"' OR st.classe = '" +RoadConcessionaire.classCCRTruckSimple2Axles1+ "' OR st.classe = '"+RoadConcessionaire.classCCRTruckSimple2Axles2+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
          	         "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classCCRTruck2Axles+"' OR st.classe = '" +RoadConcessionaire.classCCRTruckSimple2Axles1+ "' OR st.classe = '"+RoadConcessionaire.classCCRTruckSimple2Axles2+"') THEN st.classe ELSE NULL END)), 0), 0)  " +
          	         "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classCCRTruck2Axles+"' OR st.classe = '" +RoadConcessionaire.classCCRTruckSimple2Axles1+ "' OR st.classe = '"+RoadConcessionaire.classCCRTruckSimple2Axles2+"') THEN st.classe ELSE NULL END)), 0), 0)  " +

          	         "ELSE 0  " +
          	         "END 'TRUCK_2AXLES_S2',  " +
            		  	 
            		     "CASE   " +
            			 "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND st.classe = '"+ RoadConcessionaire.classCCRTruck3Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND st.classe = '"+ RoadConcessionaire.classCCRTruck3Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND st.classe = '"+ RoadConcessionaire.classCCRTruck3Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND st.classe = '"+ RoadConcessionaire.classCCRTruck3Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND st.classe = '"+ RoadConcessionaire.classCCRTruck3Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND st.classe = '"+ RoadConcessionaire.classCCRTruck3Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND st.classe = '"+ RoadConcessionaire.classCCRTruck3Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +

            			 "ELSE 0   " +
            			 "END 'TRUCK_3AXLES_S2',  " +
            			 
            		     "CASE   " +
            			 "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND st.classe = '"+ RoadConcessionaire.classCCRTruck4Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND st.classe = '"+ RoadConcessionaire.classCCRTruck4Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND st.classe = '"+ RoadConcessionaire.classCCRTruck4Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND st.classe = '"+ RoadConcessionaire.classCCRTruck4Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND st.classe = '"+ RoadConcessionaire.classCCRTruck4Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND st.classe = '"+ RoadConcessionaire.classCCRTruck4Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND st.classe = '"+ RoadConcessionaire.classCCRTruck4Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +

            			 "ELSE 0   " +
            			 "END 'TRUCK_4AXLES_S2',  " +
            			 
            		     "CASE   " +
            			 "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND st.classe = '"+ RoadConcessionaire.classCCRTruck5Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND st.classe = '"+ RoadConcessionaire.classCCRTruck5Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND st.classe = '"+ RoadConcessionaire.classCCRTruck5Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND st.classe = '"+ RoadConcessionaire.classCCRTruck5Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND st.classe = '"+ RoadConcessionaire.classCCRTruck5Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND st.classe = '"+ RoadConcessionaire.classCCRTruck5Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND st.classe = '"+ RoadConcessionaire.classCCRTruck5Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +

            			 "ELSE 0   " +
            			 "END 'TRUCK_5AXLES_S2',  " +

            		  	 "CASE   " +
            		  	 "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND st.classe = '"+ RoadConcessionaire.classCCRTruck6Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND st.classe = '"+ RoadConcessionaire.classCCRTruck6Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND st.classe = '"+ RoadConcessionaire.classCCRTruck6Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND st.classe = '"+ RoadConcessionaire.classCCRTruck6Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND st.classe = '"+ RoadConcessionaire.classCCRTruck6Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND st.classe = '"+ RoadConcessionaire.classCCRTruck6Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND st.classe = '"+ RoadConcessionaire.classCCRTruck6Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +

            		  	 "ELSE 0   " +
            		  	 "END 'TRUCK_6AXLES_S2',  " +
            		  	 
            		     "CASE   " +
            			 "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND st.classe = '"+ RoadConcessionaire.classCCRTruck7Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND st.classe = '"+ RoadConcessionaire.classCCRTruck7Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND st.classe = '"+ RoadConcessionaire.classCCRTruck7Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND st.classe = '"+ RoadConcessionaire.classCCRTruck7Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND st.classe = '"+ RoadConcessionaire.classCCRTruck7Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND st.classe = '"+ RoadConcessionaire.classCCRTruck7Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND st.classe = '"+ RoadConcessionaire.classCCRTruck7Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +

            			 "ELSE 0   " +
            			 "END 'TRUCK_7AXLES_S2',  " +
            			 
            		     "CASE   " +
            			 "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND st.classe = '"+ RoadConcessionaire.classCCRTruck8Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND st.classe = '"+ RoadConcessionaire.classCCRTruck8Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND st.classe = '"+ RoadConcessionaire.classCCRTruck8Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND st.classe = '"+ RoadConcessionaire.classCCRTruck8Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND st.classe = '"+ RoadConcessionaire.classCCRTruck8Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND st.classe = '"+ RoadConcessionaire.classCCRTruck8Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND st.classe = '"+ RoadConcessionaire.classCCRTruck8Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +

            			 "ELSE 0   " +
            			 "END 'TRUCK_8AXLES_S2',  " +
            			 
            		     "CASE   " +
            			 "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND st.classe = '"+ RoadConcessionaire.classCCRTruck9Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND st.classe = '"+ RoadConcessionaire.classCCRTruck9Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND st.classe = '"+ RoadConcessionaire.classCCRTruck9Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND st.classe = '"+ RoadConcessionaire.classCCRTruck9Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND st.classe = '"+ RoadConcessionaire.classCCRTruck9Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND st.classe = '"+ RoadConcessionaire.classCCRTruck9Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND st.classe = '"+ RoadConcessionaire.classCCRTruck9Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +

            			 "ELSE 0   " +
            			 "END 'TRUCK_9AXLES_S2',  " +
            			 
            		     "CASE   " +
            			 "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND st.classe = '"+ RoadConcessionaire.classCCRTruck10Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND st.classe = '"+ RoadConcessionaire.classCCRTruck10Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND st.classe = '"+ RoadConcessionaire.classCCRTruck10Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND st.classe = '"+ RoadConcessionaire.classCCRTruck10Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND st.classe = '"+ RoadConcessionaire.classCCRTruck10Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND st.classe = '"+ RoadConcessionaire.classCCRTruck10Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND st.classe = '"+ RoadConcessionaire.classCCRTruck10Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +

            			 "ELSE 0   " +
            			 "END 'TRUCK_10AXLES_S2',  " +

            		  	"CASE    " +
            		  	 "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND st.classe = '"+ RoadConcessionaire.classCCRBus2Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND st.classe = '"+ RoadConcessionaire.classCCRBus2Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND st.classe = '"+ RoadConcessionaire.classCCRBus2Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND st.classe = '"+ RoadConcessionaire.classCCRBus2Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND st.classe = '"+ RoadConcessionaire.classCCRBus2Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND st.classe = '"+ RoadConcessionaire.classCCRBus2Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND st.classe = '"+ RoadConcessionaire.classCCRBus2Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +

            		  	 "ELSE 0   " +
            		  	 "END 'BUS_2AXLES_S2',  " +
            		  	 
            			 "CASE    " +
            		  	 "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND st.classe = '"+ RoadConcessionaire.classCCRBus3Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND st.classe = '"+ RoadConcessionaire.classCCRBus3Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND st.classe = '"+ RoadConcessionaire.classCCRBus3Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND st.classe = '"+ RoadConcessionaire.classCCRBus3Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND st.classe = '"+ RoadConcessionaire.classCCRBus3Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND st.classe = '"+ RoadConcessionaire.classCCRBus3Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND st.classe = '"+ RoadConcessionaire.classCCRBus3Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +

            		  	 "ELSE 0   " +
            		  	 "END 'BUS_3AXLES_S2',  " +
            		  	 
            		     "CASE    " +
            			 "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND st.classe = '"+ RoadConcessionaire.classCCRBus4Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND st.classe = '"+ RoadConcessionaire.classCCRBus4Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND st.classe = '"+ RoadConcessionaire.classCCRBus4Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND st.classe = '"+ RoadConcessionaire.classCCRBus4Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND st.classe = '"+ RoadConcessionaire.classCCRBus4Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND st.classe = '"+ RoadConcessionaire.classCCRBus4Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            			 "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND st.classe = '"+ RoadConcessionaire.classCCRBus4Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +

            			 "ELSE 0   " +
            			 "END 'BUS_4AXLES_S2',  " +
            			 
            			 "CASE    " +
            		  	 "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND st.classe = '"+ RoadConcessionaire.classCCRBus5Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND st.classe = '"+ RoadConcessionaire.classCCRBus5Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND st.classe = '"+ RoadConcessionaire.classCCRBus5Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND st.classe = '"+ RoadConcessionaire.classCCRBus5Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND st.classe = '"+ RoadConcessionaire.classCCRBus5Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND st.classe = '"+ RoadConcessionaire.classCCRBus5Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND st.classe = '"+ RoadConcessionaire.classCCRBus5Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +

            		  	 "ELSE 0   " +
            		  	 "END 'BUS_5AXLES_S2',  " +
            		  	 
            			 "CASE    " +
            		  	 "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND st.classe = '"+ RoadConcessionaire.classCCRBus6Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND st.classe = '"+ RoadConcessionaire.classCCRBus6Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND st.classe = '"+ RoadConcessionaire.classCCRBus6Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND st.classe = '"+ RoadConcessionaire.classCCRBus6Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND st.classe = '"+ RoadConcessionaire.classCCRBus6Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND st.classe = '"+ RoadConcessionaire.classCCRBus6Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND st.classe = '"+ RoadConcessionaire.classCCRBus6Axles+"' THEN st.classe ELSE NULL END)), 0), 0)  " +

            		  	 "ELSE 0   " +
            		  	 "END 'BUS_6AXLES_S2',  " +
            		  	                 
            		  	 "CASE    " +
            		  	 "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) THEN st.classe ELSE NULL END)), 0), 0)  " +
            		  	 "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) THEN st.classe ELSE NULL END)), 0), 0)  " +

            		  	 "ELSE 0   " +
            		  	 "END 'TOTAL_S2'   " ;
                  	   
                	   
                	   return query;
                   }
                   
                   
                   /**
            	    * MÃ©todo para criaÃ§Ã£o da mainQuery tipo por classes
            	    * @param station_id - Id do equipamento
            	    * @param classes - array com as classes selecionadas
            	    * @param lanes - numero de faixas do equipamento
            	    * @return
            	    */
            	   public String ClassTypeCCRMainQuery(String station_id) {
               		   
            		   String query = "";
            		   
            						
            		   query += "IFNULL(ROUND(COUNT(CASEWHEN (st.classe = '"+RoadConcessionaire.classCCRLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.axlNumber < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) 'AUTOS', " +
            					 "IFNULL(ROUND(COUNT(CASEWHEN (st.classe = '"+RoadConcessionaire.classCCRMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) 'MOTO', " +
            					 "IFNULL(ROUND(COUNT(CASEWHEN (st.classe = '"+RoadConcessionaire.classCCRTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) 'TRAILER', " + 
            					 "IFNULL(ROUND(COUNT(CASEWHEN (st.classe = '"+RoadConcessionaire.classCCRSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) 'SEMITRAILER', " +
            					 "IFNULL(ROUND(COUNT(CASEWHEN (st.classe = '"+RoadConcessionaire.classCCRTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRTruckSimple2Axles1+"' OR st.classe = '"+RoadConcessionaire.classCCRTruckSimple2Axles2+"'OR st.classe = '"+RoadConcessionaire.classCCRBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) 'TRUCK 2 AXLES', " + 
            					 "IFNULL(ROUND(COUNT(CASEWHEN (st.classe = '"+RoadConcessionaire.classCCRTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) 'TRUCK 3 AXLES', " + 
            		             "IFNULL(ROUND(COUNT(CASEWHEN (st.classe = '"+RoadConcessionaire.classCCRTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) 'TRUCK 4 AXLES', " +
            					 "IFNULL(ROUND(COUNT(CASEWHEN (st.classe = '"+RoadConcessionaire.classCCRTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) 'TRUCK 5 AXLES', " +
            					 "IFNULL(ROUND(COUNT(CASEWHEN (st.classe = '"+RoadConcessionaire.classCCRTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) 'TRUCK 6 AXLES', " +
            					 "IFNULL(ROUND(COUNT(CASEWHEN (st.classe = '"+RoadConcessionaire.classCCRTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) 'TRUCK 7 AXLES', " +
            					 "IFNULL(ROUND(COUNT(CASEWHEN (st.classe = '"+RoadConcessionaire.classCCRTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) 'TRUCK 8 AXLES', " +			 
            					 "IFNULL(ROUND(COUNT(CASEWHEN (st.classe = '"+RoadConcessionaire.classCCRTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) 'TRUCK 9 AXLES', " +
            			         "IFNULL(ROUND(COUNT(CASEWHEN (st.classe='"+RoadConcessionaire.classCCRTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.axlNumber > 10)) THEN st.classe ELSE NULL END )), 0), 0) 'TRUCK 10 AXLES', " +
            				     "IFNULL(ROUND(COUNT(st.axlNumber),0),0) 'TOTAL', " +				         	   		   


            			   "CASE " +
            			   "WHEN eq.number_lanes = 2  THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "ELSE 0   " +
            			   "END 'AUTOS_S1', " +

            			   "CASE " +
            			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "ELSE 0   " +
            			   "END 'MOTOS_S1', " +

            			   "CASE " +
            			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "ELSE 0   " +
            			   "END 'TRAILER_S1', " +

            			   "CASE " +
            			   "WHEN eq.number_lanes = 2  THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "ELSE 0   " +
            			   "END 'SEMI_TRAILER_S1', " +

            			   "CASE " +
            			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "ELSE 0   " +
            			   "END 'AXLE_2_S1', " +

            			   "CASE " +
            			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "ELSE 0   " +
            			   "END 'AXLE_3_S1', " +

            			   "CASE " +
            			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "ELSE 0   " +
            			   "END 'AXLE_4_S1', " +

            			   "CASE " +
            			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "ELSE 0   " +
            			   "END 'AXLE_5_S1', " +
            			   
            		       "CASE " +
            		       "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            		       "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            		       "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            		       "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            		       "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            		       "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            		       "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            		       "ELSE 0   " +
            		       "END 'AXLE_5_S1', " +
            			  	   
            			   "CASE " +
            			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "ELSE 0   " +
            			   "END 'AXLE_7_S1', " +
            			   				   
            			   "CASE " +
            			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "ELSE 0   " +
            			   "END 'AXLE_8_S1', " +
            			   
            			   "CASE " +
            			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe = '"+RoadConcessionaire.classCCRTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "ELSE 0   " +
            			   "END 'AXLE_9_S1', " +
            			   
            			   "CASE " +
            			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe='"+RoadConcessionaire.classCCRTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) AND (st.classe='"+RoadConcessionaire.classCCRTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) AND (st.classe='"+RoadConcessionaire.classCCRTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe='"+RoadConcessionaire.classCCRTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3) AND (st.classe='"+RoadConcessionaire.classCCRTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe='"+RoadConcessionaire.classCCRTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) AND (st.classe='"+RoadConcessionaire.classCCRTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "ELSE 0   " +
            			   "END 'AXLE_10_S1', " +

            			   "CASE    " +
            			    "WHEN eq.number_lanes = 2 OR eq.number_lanes = 3  THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1) THEN st.classe ELSE NULL END)), 0), 0)  " +
            			    "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2) THEN st.classe ELSE NULL END)), 0), 0)  " +
            			    "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) THEN st.classe ELSE NULL END)), 0), 0)  " +
            			    "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) THEN st.classe ELSE NULL END)), 0), 0)  " +
            			    "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) THEN st.classe ELSE NULL END)), 0), 0)  " +
            			    "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 1 OR st.lane = 2 OR st.lane = 3 OR st.lane = 4) THEN st.classe ELSE NULL END)), 0), 0)  " +

            			    "ELSE 0   " +
            			    "END 'TOTAL_S1' ,  " +
            			                     
            			  "CASE " +
            			   "WHEN eq.number_lanes = 2  THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classCCRLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classCCRLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classCCRLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classCCRLight+"' OR (st.classe='"+RoadConcessionaire.classUnknown+"' AND st.classe < 10) OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "ELSE 0   " +
            			   "END 'AUTOS_S2', " +

            			   "CASE " +
            			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2  OR st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classCCRMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classCCRMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classCCRMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classCCRMotorcycle+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "ELSE 0   " +
            			   "END 'MOTOS_S2', " +

            			   "CASE " +
            			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classCCRTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classCCRTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classCCRTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classCCRTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "ELSE 0   " +
            			   "END 'TRAILER_S2', " +

            			   "CASE " +
            			   "WHEN eq.number_lanes = 2  THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classCCRSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classCCRSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classCCRSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classCCRSemiTrailer+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "ELSE 0   " +
            			   "END 'SEMI_TRAILER_S2', " +

            			   "CASE " +
            			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classCCRTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classCCRTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classCCRTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classCCRTruck2Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus2Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "ELSE 0   " +
            			   "END 'AXLE_2_S2', " +

            			   "CASE " +
            			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classCCRTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classCCRTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classCCRTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classCCRTruck3Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus3Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl3+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "ELSE 0   " +
            			   "END 'AXLE_3_S2', " +

            			   "CASE " +
            			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classCCRTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classCCRTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classCCRTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classCCRTruck4Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus4Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl4+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "ELSE 0   " +
            			   "END 'AXLE_4_S2', " +

            			   "CASE " +
            			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classCCRTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classCCRTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classCCRTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classCCRTruck5Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus5Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl5+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "ELSE 0   " +
            			   "END 'AXLE_5_S2', " +

            			   "CASE " +
            			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classCCRTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classCCRTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classCCRTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classCCRTruck6Axles+"' OR st.classe = '"+RoadConcessionaire.classCCRBus6Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl6+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "ELSE 0   " +
            			   "END 'AXLE_6_S2', " + 
            			   
            			   "CASE " +
            			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classCCRTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classCCRTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classCCRTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classCCRTruck7Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl7+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "ELSE 0   " +
            			   "END 'AXLE_7_S2', " +
            			   				   
            			   "CASE " +
            			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classCCRTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classCCRTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classCCRTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classCCRTruck8Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl8+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "ELSE 0   " +
            			   "END 'AXLE_8_S2', " +
            			   
            			   "CASE " +
            			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2) AND (st.classe = '"+RoadConcessionaire.classCCRTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2 OR st.lane = 3) AND (st.classe = '"+RoadConcessionaire.classCCRTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 3 OR st.lane = 4) AND (st.classe = '"+RoadConcessionaire.classCCRTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe = '"+RoadConcessionaire.classCCRTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe = '"+RoadConcessionaire.classCCRTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe = '"+RoadConcessionaire.classCCRTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe = '"+RoadConcessionaire.classCCRTruck9Axles+"' OR st.classe = '"+RoadConcessionaire.classNotIdentifiedAxl9+"') THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "ELSE 0   " +
            			   "END 'AXLE_9_S2', " +
            			   
            			   "CASE " +
            			   "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2) AND (st.classe='"+RoadConcessionaire.classCCRTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 2 OR st.lane = 3) AND (st.classe='"+RoadConcessionaire.classCCRTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN ( st.lane = 3 OR st.lane = 4) AND (st.classe='"+RoadConcessionaire.classCCRTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) AND (st.classe='"+RoadConcessionaire.classCCRTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) AND (st.classe='"+RoadConcessionaire.classCCRTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) AND (st.classe='"+RoadConcessionaire.classCCRTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) AND (st.classe='"+RoadConcessionaire.classCCRTruck10Axles+"' or (st.classe='"+RoadConcessionaire.classUnknown+"' and st.classe > 9)) THEN st.classe ELSE NULL END )), 0), 0) " +
            			   "ELSE 0   " +
            			   "END 'AXLE_10_S2', " +


            			   "CASE    " +
            			    "WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2) THEN st.classe ELSE NULL END)), 0), 0)  " +
            			    "WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 2 OR st.lane = 3) THEN st.classe ELSE NULL END)), 0), 0)  " +
            			    "WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 3 OR st.lane = 4) THEN st.classe ELSE NULL END)), 0), 0)  " +
            			    "WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5) THEN st.classe ELSE NULL END)), 0), 0)  " +
            			    "WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 4 OR st.lane = 5 OR st.lane = 6) THEN st.classe ELSE NULL END)), 0), 0)  " +
            			    "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7) THEN st.classe ELSE NULL END)), 0), 0)  " +
            			    "WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(COUNT(CASEWHEN (st.lane = 5 OR st.lane = 6 OR st.lane = 7 OR st.lane = 8) THEN st.classe ELSE NULL END)), 0), 0)  " +

            			    "ELSE 0   " +
            			    "END 'TOTAL_S2'   " ;
            		   
            		                   //END METHOD		               
            		                   return query;		             
            		     
            	                   } 
            	   
                                      
                   
                    // -------------------------------------------------------------------- //      
                   // ---------------------------- CCR QUERIES --------------------------- //
                  // -------------------------------------------------------------------- //     
                   
                   
                  
                           
                    		   
        }
