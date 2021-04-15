package br.com.tracevia.webapp.dao.meteo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.meteo.mto.MtoPanel;
import br.com.tracevia.webapp.model.meteo.rs.RsPanel;
import br.com.tracevia.webapp.model.meteo.vs.VsPanel;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class MeteoDAO {
	
	private Connection conn;			
	private PreparedStatement ps;
	private ResultSet rs;
	
	public MtoPanel MtoPanelInformation(String station_id) throws Exception {
				
		MtoPanel panel = new MtoPanel();
						
		//QUERY
		String select = MeteoQueriesModels.MTO_PANEL_QUERY;
				
		    try {
			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(select);			
			ps.setString(1, station_id);		
						
			rs = ps.executeQuery();
			
			System.out.println(select);
			
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
										
					panel.setAtmPressure(rs.getInt(1)/10);
					panel.setRelative_humidity(rs.getInt(2));
					panel.setPreciptation_rate(rs.getInt(3));
					panel.setPreciptation_rate_hour(rs.getInt(4));
					panel.setWind_speed(rs.getInt(5));
					panel.setWind_direction(rs.getInt(6));  
					panel.setTemperature(rs.getDouble(7));
					panel.setVisibility(rs.getInt(8));
					panel.setStatus(rs.getInt(9));
					panel.setBattery(rs.getInt(10));
					panel.setLine_volts(rs.getInt(11));
				
				}				
			 } else panel = null;	//DEFINE A V�RI�VEL COMO NULA

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

				
		return panel;
		
	}
	
	public RsPanel RsPanelInformation(String station_id) throws Exception {
		
		RsPanel panel = new RsPanel();
						
		//QUERY
		String select = MeteoQueriesModels.RV_PANEL_QUERY;
				
		    try {
			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(select);			
			ps.setString(1, station_id);		
						
			rs = ps.executeQuery();
						
			if (rs.isBeforeFirst()) {
				while (rs.next()) {						
			
					panel.setRoad_temperature(rs.getDouble(1));					
					panel.setStatus(rs.getInt(2));
					panel.setBattery(rs.getInt(3));
					panel.setLine_volts(rs.getInt(4));
				
				}
				
			 } else panel = null;	//DEFINE A V�RI�VEL COMO NULA

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

				
		return panel;
		
	}
	
	public VsPanel VsPanelInformation(String station_id) throws Exception {
		
		VsPanel panel = new VsPanel();
						
		//QUERY
		String select = MeteoQueriesModels.SV_PANEL_QUERY;
				
		    try {
			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(select);			
			ps.setString(1, station_id);		
						
			rs = ps.executeQuery();
						
			if (rs.isBeforeFirst()) {
				while (rs.next()) {						
			
					panel.setAmbient_temperature(rs.getDouble(1));	
					panel.setVisibility(rs.getInt(2));
					panel.setStatus(rs.getInt(3));
					panel.setBattery(rs.getInt(4));
					panel.setLine_volts(rs.getInt(5));
				
				}
				
			 } else panel = null;	//DEFINE A V�RI�VEL COMO NULA

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

				
		return panel;
		
	}
	
	
}
