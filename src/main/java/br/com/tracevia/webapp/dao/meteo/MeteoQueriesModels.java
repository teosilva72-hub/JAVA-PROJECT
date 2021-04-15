package br.com.tracevia.webapp.dao.mto;

public class MtoQueriesModels {
				
	public static String PANEL_MAIN_QUERY = "SELECT "
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
			+ "line_volts "
			+ "FROM weather_station "
			+ "WHERE station_id = ? AND DATE(datetime_) = DATE(NOW()) "
			+ "ORDER BY DATE(datetime_) DESC LIMIT 1";
	
	public String WeatherMainQuery(String station_id) {
		
		String query = "IFNULL(ROUND(AVG(IF(eq.equip_id = '"+station_id+"', (st.atmospheric_pressure / 10) , NULL)),0),0) AS pressure, " +
			    "IFNULL(ROUND(AVG(IF(eq.equip_id = '"+station_id+"', st.relative_humidity, NULL)),0),0) AS humidity, " +	
			    "IFNULL(ROUND(AVG(IF(eq.equip_id = '"+station_id+"', (st.temperature / 10), NULL)),0),0) AS temperatura, " +
			    "IFNULL(ROUND(AVG(IF(eq.equip_id = '"+station_id+"', st.wind_speed, NULL)),0),0) AS wind_speed, " +
			    "IFNULL(ROUND(AVG(IF(eq.equip_id = '"+station_id+"', st.wind_direction, NULL)),0),0) AS wind_direction, " +			 
			    "IFNULL(ROUND(AVG(IF(eq.equip_id = '"+station_id+"', st.precipitation_rate, NULL)),0),0) AS precipitation_rate, " +
			    "IFNULL(ROUND(AVG(IF(eq.equip_id = '"+station_id+"', st.precipitation_rate_hour, NULL)),0),0) AS preciptation_rate_hour, " +
			    "IFNULL(ROUND(AVG(IF(eq.equip_id = '"+station_id+"', st.visibility, NULL)),0),0) AS visibility ";	
		
		return query;
	
	}

}
