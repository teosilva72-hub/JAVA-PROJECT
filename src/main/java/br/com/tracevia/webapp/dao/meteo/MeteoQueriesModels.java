package br.com.tracevia.webapp.dao.meteo;

public class MeteoQueriesModels {
				
	public static String MTO_PANEL_QUERY = "SELECT "
			+ "atmospheric_pressure, "
			+ "relative_humidity, "
			+ "absolute_precipitation, "			
			+ "wind_speed, " 
			+ "wind_direction, "
			+ "temperature, "
			+ "visibility, "
			+ "status, "
			+ "battery_status, "
			+ "line_volts, "
			+ "road_temperature "
			+ "FROM mto_data "
			+ "WHERE station_id = ? AND DATE(datetime_) = DATE(NOW()) "
			+ "ORDER BY DATE(datetime_) DESC LIMIT 1";
			
	public static String VS_PANEL_QUERY = "SELECT "	
			+ "atmospheric_pressure, "
			+ "relative_humidity, "
			+ "absolute_precipitation, "			
			+ "wind_speed, " 
			+ "wind_direction, "
			+ "temperature, "	
			+ "status, "
			+ "battery_status, "
			+ "line_volts, "			
			+ "ambient_temperature "			
			+ "FROM sv_data "
			+ "WHERE station_id = ? AND DATE(datetime_) = DATE(NOW()) "
			+ "ORDER BY DATE(datetime_) DESC LIMIT 1";
	
	public String MtoMainQuery(String station_id) {
		
		String query = "IFNULL(CAST(AVG(IF(eq.equip_id = '"+station_id+"', st.atmospheric_pressure, NULL)) AS DECIMAL(5,1)),0)  AS pressure, " +
			    "IFNULL(ROUND(AVG(IF(eq.equip_id = '"+station_id+"', st.relative_humidity, NULL)),0),0) AS humidity, " +	
			    "IFNULL(CAST(AVG(IF(eq.equip_id = '"+station_id+"', st.temperature, NULL)) AS DECIMAL(3,1)),0) AS temperatura, " +
			    "IFNULL(CAST(AVG(IF(eq.equip_id = '"+station_id+"', st.wind_speed, NULL)) AS DECIMAL(3,2)),0) AS wind_speed, " +
			    "IFNULL(CAST(AVG(IF(eq.equip_id = '"+station_id+"', st.wind_direction, NULL)) AS DECIMAL(5,2)),0) AS wind_direction, " +			 
			    "IFNULL(CAST(AVG(IF(eq.equip_id = '"+station_id+"', st.absolute_precipitation, NULL)) AS DECIMAL(6,2)),0) AS absolute_precipitation, " +			
			    "IFNULL(ROUND(AVG(IF(eq.equip_id = '"+station_id+"', st.visibility, NULL)),0),0) AS visibility, " + 
			    "IFNULL(CAST(AVG(IF(eq.equip_id = '"+station_id+"', st.road_temperature, NULL)) AS DECIMAL(3,1)),0) AS temperatura_area ";
		
		return query;
	
	}
	         
     public String SvMainQuery(String station_id) {
 		 				 	
 		String query = "IFNULL(CAST(AVG(IF(eq.equip_id = '"+station_id+"', st.atmospheric_pressure, NULL)) AS DECIMAL(5,1)),0)  AS pressure, " +
			    "IFNULL(ROUND(AVG(IF(eq.equip_id = '"+station_id+"', st.relative_humidity, NULL)),0),0) AS humidity, " +	
			    "IFNULL(CAST(AVG(IF(eq.equip_id = '"+station_id+"', st.temperature, NULL)) AS DECIMAL(3,1)),0) AS temperatura, " +
			    "IFNULL(CAST(AVG(IF(eq.equip_id = '"+station_id+"', st.wind_speed, NULL)) AS DECIMAL(3,2)),0) AS wind_speed, " +
			    "IFNULL(CAST(AVG(IF(eq.equip_id = '"+station_id+"', st.wind_direction, NULL)) AS DECIMAL(5,2)),0) AS wind_direction, " +			 
			    "IFNULL(CAST(AVG(IF(eq.equip_id = '"+station_id+"', st.absolute_precipitation, NULL)) AS DECIMAL(6,2)),0) AS absolute_precipitation, " +			
				"IFNULL(CAST(AVG(IF(eq.equip_id = '"+station_id+"', st.ambient_temperature, NULL)) AS DECIMAL(3,1)),0) AS ambient_temperature ";
			 	 		
 		return query;
 	
 	}

}
