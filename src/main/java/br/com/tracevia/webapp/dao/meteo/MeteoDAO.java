package br.com.tracevia.webapp.dao.meteo;

import java.sql.SQLException;

import br.com.tracevia.webapp.model.global.SQL_Tracevia;
import br.com.tracevia.webapp.model.global.ColumnsSql.RowResult;
import br.com.tracevia.webapp.model.global.ResultSql.MapResult;
import br.com.tracevia.webapp.model.meteo.MeteoPanel;

public class MeteoDAO {
	
	SQL_Tracevia conn = new SQL_Tracevia();
				
	public MeteoPanel MeteoPanel(String station_id, String type) throws Exception {
		
		MeteoPanel panel = new MeteoPanel();
						
		// QUERY
		String select = ""; 
		
		if(type.equals("MTO"))			
			select = MeteoQueriesModels.MTO_PANEL_QUERY;
			
		else select = MeteoQueriesModels.VS_PANEL_QUERY;
				
		    try {
			
			conn.start(1);
			
			conn.prepare(select);			
			conn.setString(1, station_id);		
						
			MapResult result = conn.executeQuery();
			
			if(type.equals("mto")) {
						
				if (result.hasNext()) {
					for (RowResult rs : result) {
											
						panel.setAtmosphericPressure(rs.getDouble(1));
						panel.setRelativeHumidity(rs.getInt(2));
						panel.setAbsolutePreciptation(rs.getDouble(3));				
						panel.setWindSpeed(rs.getDouble(4));
						panel.setWindDirection(rs.getDouble(5));  
						panel.setTemperature(rs.getDouble(6));
						panel.setVisibility(rs.getInt(7));
						panel.setStatus(rs.getInt(8));
						panel.setBattery(rs.getInt(9));
						panel.setLineVolts(rs.getInt(10));
						panel.setRoadTemperature(rs.getDouble(11));
					
					}
					
				 } else panel = null;	// PANEL NULL
				
			} else {
				
				if (result.hasNext()) {
					for (RowResult rs : result) {
											
						panel.setAtmosphericPressure(rs.getDouble(1));
						panel.setRelativeHumidity(rs.getInt(2));
						panel.setAbsolutePreciptation(rs.getDouble(3));				
						panel.setWindSpeed(rs.getDouble(4));
						panel.setWindDirection(rs.getDouble(5));  
						panel.setTemperature(rs.getDouble(6));					
						panel.setStatus(rs.getInt(7));
						panel.setBattery(rs.getInt(8));
						panel.setLineVolts(rs.getInt(9));
										
					}				
					
				 } else panel = null;	// PANEL NULL				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}

				
		return panel;
		
	}
		
}
