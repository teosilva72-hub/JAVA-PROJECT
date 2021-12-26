package br.com.tracevia.webapp.dao.meteo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.meteo.MtoPanel;
import br.com.tracevia.webapp.model.meteo.SvPanel;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class MeteoDAO {
	
	private Connection conn;			
	private PreparedStatement ps;
	private ResultSet rs;
	
	
	public String MtoPanelType(String station_id) throws Exception {
		
		String type = "";
						
		   //QUERY
		   String select = "SELECT type FROM mto_equipment WHERE equip_id = ? ";
				
		    try {
			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(select);				
			ps.setString(1, station_id);		
						
			rs = ps.executeQuery();
						
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
										
					type = rs.getString(1);
				
				}				
			 }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

				
		return type;
		
	}
	
	public MtoPanel MtoPanelInformation(String station_id) throws Exception {
				
		MtoPanel panel = new MtoPanel();
						
		//QUERY
		String select = MeteoQueriesModels.MTO_PANEL_QUERY;
				
		    try {
			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(select);			
			ps.setString(1, station_id);		
						
			rs = ps.executeQuery();
						
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
										
					panel.setAtmPressure(rs.getDouble(1));
					panel.setRelative_humidity(rs.getInt(2));
					panel.setAbsolute_preciptation(rs.getDouble(3));				
					panel.setWind_speed(rs.getDouble(4));
					panel.setWind_direction(rs.getDouble(5));  
					panel.setTemperature(rs.getDouble(6));
					panel.setVisibility(rs.getInt(7));
					panel.setStatus(rs.getInt(8));
					panel.setBattery(rs.getInt(9));
					panel.setLine_volts(rs.getInt(10));
					panel.setRoad_temperature(rs.getDouble(11));
				
				}				
			 } else panel = null;	//DEFINE A V�RI�VEL COMO NULA

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

				
		return panel;
		
	}
			
	public SvPanel SvPanelInformation(String station_id) throws Exception {
		
		SvPanel panel = new SvPanel();
						
		//QUERY
		String select = MeteoQueriesModels.VS_PANEL_QUERY;
				
		    try {
			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(select);			
			ps.setString(1, station_id);		
						
			rs = ps.executeQuery();
						
			if (rs.isBeforeFirst()) {
				while (rs.next()) {						
			
					panel.setAtmPressure(rs.getDouble(1));
					panel.setRelative_humidity(rs.getInt(2));
					panel.setAbsolute_preciptation(rs.getDouble(3));				
					panel.setWind_speed(rs.getDouble(4));
					panel.setWind_direction(rs.getDouble(5));  
					panel.setTemperature(rs.getDouble(6));
					panel.setAmbient_temperature(rs.getDouble(7));					
					panel.setStatus(rs.getInt(8));
					panel.setBattery(rs.getInt(9));
					panel.setLine_volts(rs.getInt(10));
				
				}
				
			 } else panel = null;	//DEFINE A V�RI�VEL COMO NULA

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

				
		return panel;
		
	}
		
}
