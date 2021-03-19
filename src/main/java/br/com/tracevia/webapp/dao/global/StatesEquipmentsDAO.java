package br.com.tracevia.webapp.dao.global;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.global.StatesEquipments;
import br.com.tracevia.webapp.model.global.StatesEquipments.StateBattery;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class StatesEquipmentsDAO {
	
	private Connection conn;
	protected ConnectionFactory connection = new ConnectionFactory();
	private PreparedStatement ps;
	private ResultSet rs;
	
	public static String INTERVAL_30 = "INTERVAL 30 MINUTE";
	public static String INTERVAL_45 = "INTERVAL 45 MINUTE";
	public static String INTERVAL_08 = "INTERVAL 08 HOUR";
	
	private List<StateBattery> battery;
			
	public List<StateBattery> getBattery() {
		return battery;
	}

	DateTimeApplication dta;
		
	public StatesEquipmentsDAO() {
		
		dta = new DateTimeApplication();
	}
	
	
	  public List<StateBattery> batteryState(String type, String interval) throws Exception{
			
			List<StateBattery> lista = new ArrayList<StateBattery>();
			
			dta = new DateTimeApplication();
			
			Calendar calendar = Calendar.getInstance();	
			int minute = calendar.get(Calendar.MINUTE);
			
			//Obter datas formatadas para os dados
			String currentDate = dta.getCurrentDateDados15CCR(calendar, minute);
				  		
		   String select = "SELECT equip_id,   ROUND(CONVERT(((value_ / 1000) * 1), DECIMAL(3,1)), 1) AS value_ FROM battery_values "
		   		+ "WHERE datetime_ between DATE_SUB( ? , ? ) AND ? and equip_type = ? " 
		   		+ "ORDER BY equip_id ASC ";
					   
		         try {
					    				    				    	
					    conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
						
						ps = conn.prepareStatement(select);	
						ps.setString(1, currentDate);
						ps.setString(2, interval);
						ps.setString(3, currentDate);
						ps.setString(4, type);
						
						StatesEquipments sts = new StatesEquipments();						
						StateBattery battery = sts.new StateBattery();
						
						rs = ps.executeQuery();
																					
						if (rs.isBeforeFirst()) {
							while (rs.next()) {
																							
								battery.setEquipId(rs.getString("equip_id"));
								battery.setValue(rs.getDouble("value_"));
																	
						       }
						    }					    		

					} catch (SQLException e) {
						e.printStackTrace();
					}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

			  return lista;
		  } 
	  
	  
	  
	    // LISTAR UM EQUIPAMENTO POR STATUS NOS ULTIMAS 8 HORAS (DELAY DE 15 MINUTOS)
	    public StateBattery batteryState(String equip, String type, String interval) throws Exception {
			
			DateTimeApplication dta = new DateTimeApplication();
						
			Calendar calendar = Calendar.getInstance();	
			int minute = calendar.get(Calendar.MINUTE);
			
			StatesEquipments sts = new StatesEquipments();						
			StateBattery battery = sts.new StateBattery();
			
			//Obter datas formatadas para os dados
			String currentDate = dta.getCurrentDateDados15CCR(calendar, minute);
			
						
			  String select = "SELECT equip_id,   ROUND(CONVERT(((value_ / 1000) * 1), DECIMAL(3,1)), 1) AS value_ FROM battery_values "
				   		+ "WHERE datetime_ between DATE_SUB( ? , ? ) AND ? and equip_type = ? " 				   		
				        + "GROUP BY datetime_ " 			        
				        + "ORDER BY datetime_ DESC LIMIT 1";
				      	    	  					
		  try {
				
			  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
				
				ps = conn.prepareStatement(select);
				ps.setString(1, equip);	
				ps.setString(2, currentDate);	
				ps.setString(3, interval);	
				ps.setString(4, type);	
										
				rs = ps.executeQuery();
										
				if (rs.isBeforeFirst()) {
					while (rs.next()) {
					
						battery.setEquipId(rs.getString("equip_id"));
						battery.setValue(rs.getDouble("value_"));		
															
					}				
				 }			

			} catch (SQLException e) {
				e.printStackTrace();
			}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

					
			return battery;
			
		}	
	

}
