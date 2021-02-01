package br.com.tracevia.webapp.dao.mto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import br.com.tracevia.webapp.model.mto.MtoPanel;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class MtoDAO {
	
	private Connection conn;			
	private PreparedStatement ps;
	private ResultSet rs;
	
	public MtoPanel WeatherPanelInformation(String station_id) throws Exception {
				
		MtoPanel panel = new MtoPanel();
						
		//QUERY
		String select = MtoQueriesModels.PANEL_MAIN_QUERY;
				
		    try {
			
			conn = ConnectionFactory.connectToTraceviaApp();
			
			ps = conn.prepareStatement(select);			
			ps.setString(1, station_id);		
						
			rs = ps.executeQuery();
			
			System.out.println(select);
			
			if (rs != null) {
				while (rs.next()) {
										
					panel.setAtmPressure(rs.getInt(1)/10);
					panel.setRelative_humidity(rs.getInt(2));
					panel.setPreciptation_rate(rs.getInt(3));
					panel.setPreciptation_rate_hour(rs.getInt(4));
					panel.setWind_speed(rs.getInt(5));
					panel.setWind_direction(rs.getInt(6));  
					panel.setTemperature(rs.getInt(7)/10);
					panel.setVisibility(rs.getInt(8));
					panel.setStatus(rs.getInt(9));
					panel.setBattery(rs.getInt(10));
					panel.setLine_volts(rs.getInt(11));
				
				}				
			 }			

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

				
		return panel;
		
	}

}
