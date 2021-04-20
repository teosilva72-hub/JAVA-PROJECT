package br.com.tracevia.webapp.dao.meteo;

public class MeteoQueriesModels {
				
	public static String MTO_PANEL_QUERY = "SELECT "
			+ "atmospheric_pressure, "
			+ "relative_humidity, "
			+ "precipitation_rate, "
			+ "precipitation_rate_hour, "
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
			+ "ambient_temperature, "
			+ "visibility, "
			+ "status, "
			+ "battery_status, "
			+ "line_volts "
			+ "FROM sv_data "
			+ "WHERE station_id = ? AND DATE(datetime_) = DATE(NOW()) "
			+ "ORDER BY DATE(datetime_) DESC LIMIT 1";
	
	public String MtoMainQuery(String station_id) {
		
		String query = "IFNULL(ROUND(AVG(IF(eq.equip_id = '"+station_id+"', (st.atmospheric_pressure / 10) , NULL)),0),0) AS pressure, " +
			    "IFNULL(ROUND(AVG(IF(eq.equip_id = '"+station_id+"', st.relative_humidity, NULL)),0),0) AS humidity, " +	
			    "IFNULL(CAST(AVG(IF(eq.equip_id = '"+station_id+"', FORMAT((st.temperature), 2), NULL)) AS DECIMAL(3,1)),0) AS temperatura, " +
			    "IFNULL(ROUND(AVG(IF(eq.equip_id = '"+station_id+"', st.wind_speed, NULL)),0),0) AS wind_speed, " +
			    "IFNULL(ROUND(AVG(IF(eq.equip_id = '"+station_id+"', st.wind_direction, NULL)),0),0) AS wind_direction, " +			 
			    "IFNULL(ROUND(AVG(IF(eq.equip_id = '"+station_id+"', st.precipitation_rate, NULL)),0),0) AS precipitation_rate, " +
			    "IFNULL(ROUND(AVG(IF(eq.equip_id = '"+station_id+"', st.precipitation_rate_hour, NULL)),0),0) AS preciptation_rate_hour, " +
			    "IFNULL(ROUND(AVG(IF(eq.equip_id = '"+station_id+"', st.visibility, NULL)),0),0) AS visibility, " + 
			    "IFNULL(CAST(AVG(IF(eq.equip_id = '"+station_id+"', FORMAT((st.road_temperature), 2), NULL)) AS DECIMAL(3,1)),0) AS temperatura_area ";
		
		return query;
	
	}
	         
     public String SvMainQuery(String station_id) {
 		
 		String query = "IFNULL(CAST(AVG(IF(eq.equip_id = '"+station_id+"', FORMAT((st.ambient_temperature), 2), NULL)) AS DECIMAL(3,1)),0), "
 				     + "IFNULL(ROUND(AVG(IF(eq.equip_id = '"+station_id+"', st.visibility, NULL)),0),0) AS visibility ";
 			 		
 		return query;
 	
 	}


}
