package br.com.tracevia.webapp.dao.global;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.tracevia.webapp.cfg.RoadConcessionairesEnum;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.global.Notifications;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.util.ConnectionFactory;
import br.com.tracevia.webapp.util.LocaleUtil;

public class NotificationsDAO {
	
	private Connection conn;		
	private PreparedStatement ps;
	private ResultSet rs;
	
	private List<Notifications> notifications;
	
	LocaleUtil locale;
	DateTimeApplication dta;

	public List<Notifications> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notifications> notifications) {
		this.notifications = notifications;
	}	
	
	public NotificationsDAO() {
		
		locale = new LocaleUtil();
		locale.getResourceBundle(LocaleUtil.MESSAGES_NOTIFICATIONS);
		dta = new DateTimeApplication();		
	}		
		
     public List<Notifications> Notifications(String type) throws Exception{
		
		List<Notifications> lista = new ArrayList<Notifications>();
		
	   String currentDate = dta.currentStringDate(DateTimeApplication.DATE_TIME_FORMAT_STANDARD_VIEW);
		
	   String select = "SELECT e.id,e.eq_name, st.notif_battery, st.notif_stored_battery, st.notif_door, st.notif_stored_door, " +
	   		    "st.notif_energy, st.notif_stored_energy, st.notif_online, st.notif_stored_online, " +
			    "st.notif_presence, st.notif_stored_presence, st.notif_temperature, st.notif_stored_temperature " +
	   		    "FROM tracevia_app.notifications_states st " +
		        "INNER JOIN notifications_equip e on (e.id = st.notif_equip_id)  " +
		        "WHERE e.eq_type = ? " +
		        "GROUP BY e.eq_name " +
		        "ORDER BY e.eq_type ASC";
						
				    try {
					
				    	 if(RoadConcessionaire.roadConcessionaire.equals(RoadConcessionairesEnum.ViaSul.getConcessionaire()))
					           conn = ConnectionFactory.connectToCCR();
					    
					    else if(RoadConcessionaire.roadConcessionaire.equals(RoadConcessionairesEnum.ViaPaulista.getConcessionaire()))
					           conn = ConnectionFactory.connectToViaPaulista();
					    
					    else conn = ConnectionFactory.connectToTraceviaApp();
					
					ps = conn.prepareStatement(select);	
					ps.setString(1 , type);
					
					rs = ps.executeQuery();
										
					if (rs != null) {
						while (rs.next()) {
							
							Notifications not;
														
							   if(rs.getBoolean("st.notif_battery") == true && rs.getBoolean("st.notif_stored_battery") == false) {	
								   
								  not = new Notifications();	
										
								  not.setNotifEquipId(rs.getInt("e.id"));
								  not.setStatus(2); // BATTERY LOW
								  not.setDateTime(currentDate);
								  not.setDescription(rs.getString("e.eq_name")+" - " +		
								  locale.getStringKey("stat_equipment_notification_battery_low_description"));
																														
							      lista.add(not);
							
							     } 
							   
							   if(rs.getBoolean("st.notif_door") == true && rs.getBoolean("st.notif_stored_door") == false) {						
									
								   not = new Notifications();	
								   
								      not.setNotifEquipId(rs.getInt("e.id"));
									  not.setStatus(4); // DOOR OPENED
									  not.setDateTime(currentDate);
									  not.setDescription(rs.getString("e.eq_name")+" - " +		
									  locale.getStringKey("stat_equipment_notification_door_open_description"));
																															
								      lista.add(not);
								
							    } 
							   
							   if(rs.getBoolean("st.notif_energy") == true && rs.getBoolean("st.notif_stored_energy") == false) {						
									
								   not = new Notifications();	
								   
								      not.setNotifEquipId(rs.getInt("e.id"));
									  not.setStatus(6); // POWER OFF
									  not.setDateTime(currentDate);
									  not.setDescription(rs.getString("e.eq_name")+" - " +		
									  locale.getStringKey("stat_equipment_notification_energy_off_description"));
																															
								      lista.add(not);								
							   } 
							   
							   if(rs.getBoolean("st.notif_online") == true && rs.getBoolean("st.notif_stored_online") == false) {						
									
								   not = new Notifications();	
								   
								      not.setNotifEquipId(rs.getInt("e.id"));
									  not.setStatus(8); // OFF-LINE
									  not.setDateTime(currentDate);
									  not.setDescription(rs.getString("e.eq_name")+" - " +		
									  locale.getStringKey("stat_equipment_notification_status_offline_description"));
																															
								      lista.add(not);								
							   } 
							   
							   if(rs.getBoolean("st.notif_presence") == true && rs.getBoolean("st.notif_stored_presence") == false) {						
								      not = new Notifications();	
								   
								      not.setNotifEquipId(rs.getInt("e.id"));
									  not.setStatus(10); // PRESENCE CLOSE
									  not.setDateTime(currentDate);
									  not.setDescription(rs.getString("e.eq_name")+" - " +		
									  locale.getStringKey("stat_equipment_notification_presence_close_description"));
																															
								      lista.add(not);								
							   } 
							   
							   if(rs.getBoolean("st.notif_temperature") == true && rs.getBoolean("st.notif_stored_temperature") == false) {						
								   not = new Notifications();	
								   
								   
								      not.setNotifEquipId(rs.getInt("e.id"));
									  not.setStatus(12); // TEMPERATURE LOW
									  not.setDateTime(currentDate);
									  not.setDescription(rs.getString("e.eq_name")+" - " +		
									  locale.getStringKey("stat_equipment_notification_temperature_low_description"));
																															
								      lista.add(not);								
							   } 							
					       }
					    }					    		

				} catch (SQLException e) {
					e.printStackTrace();
				}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

		  return lista;
	  } 	
     
     
     public Integer notificationsCount(String type) throws Exception {
    	 
    	 int count = 0;
    	 
    	 String select = "SELECT " +
    			 "SUM(IF(st.notif_battery = 1 AND st.notif_viewed_battery = 0 , 1, 0)) 'BATTERY', " +
    			 "SUM(IF(st.notif_door = 1 AND st.notif_viewed_door = 0 , 1, 0)) 'DOOR', " +
    			 "SUM(IF(st.notif_energy = 1 AND st.notif_viewed_energy = 0 , 1, 0)) 'ENERGY', " +
    			 "SUM(IF(st.notif_online = 1 AND st.notif_viewed_online = 0 , 1, 0)) 'ONLINE', " +
    			 "SUM(IF(st.notif_presence = 1 AND st.notif_viewed_presence = 0 , 1, 0)) 'PRESENCE', " +
    			 "SUM(IF(st.notif_temperature = 1 AND st.notif_viewed_temperature = 0 , 1, 0)) 'TEMPERATURE' " +
    			 "FROM tracevia_app.notifications_states st " +
    			 "INNER JOIN notifications_equip e on (e.id = st.notif_equip_id)  " + 		      
    			 "WHERE e.eq_type = ? ";
    	
    	 try {
				
				conn = ConnectionFactory.connectToTraceviaApp();
				
				ps = conn.prepareStatement(select);	
				ps.setString(1 , type);
				
				rs = ps.executeQuery();
									
				if (rs != null) {
					while (rs.next()) {
																	
						count = rs.getInt("BATTERY") + 	rs.getInt("DOOR") +	rs.getInt("ENERGY") +
						rs.getInt("ONLINE") + rs.getInt("PRESENCE") + rs.getInt("TEMPERATURE");
						
				    }				
			     }			

			} catch (SQLException e) {
				e.printStackTrace();
			}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

    	 
    	 return count;    	 
    	 
     }
     
     
  public boolean updateNotifications(int stateId, int equipId) throws Exception {
    	 
    	  boolean response = false;
    	
    	  String update = " ";
    	  
    	  switch(stateId) {
    	  
    	     case 1: update = "UPDATE tracevia_app.notifications_states SET notif_viewed_battery = 0 WHERE notif_equip_id = ? "; break;
    	     case 2: update = "UPDATE tracevia_app.notifications_states SET notif_viewed_battery = 1 WHERE notif_equip_id = ? "; break;
    	     case 3: update = "UPDATE tracevia_app.notifications_states SET notif_viewed_door = 0 WHERE notif_equip_id = ? "; break;
    	     case 4: update = "UPDATE tracevia_app.notifications_states SET notif_viewed_door = 1 WHERE notif_equip_id = ? "; break;
    	     case 5: update = "UPDATE tracevia_app.notifications_states SET notif_viewed_energy= 0 WHERE notif_equip_id = ? "; break;
    	     case 6: update = "UPDATE tracevia_app.notifications_states SET notif_viewed_energy = 1 WHERE notif_equip_id = ? "; break;
    	     case 7: update = "UPDATE tracevia_app.notifications_states SET notif_viewed_online = 0 WHERE notif_equip_id = ? "; break;
    	     case 8: update = "UPDATE tracevia_app.notifications_states SET notif_viewed_online = 1 WHERE notif_equip_id = ? "; break;
    	     case 9: update = "UPDATE tracevia_app.notifications_states SET notif_viewed_presence = 0 WHERE notif_equip_id = ? "; break;
    	     case 10: update = "UPDATE tracevia_app.notifications_states SET notif_viewed_presence = 1 WHERE notif_equip_id = ? "; break;
    	     case 11: update = "UPDATE tracevia_app.notifications_states SET notif_viewed_temperature = 0 WHERE notif_equip_id = ? "; break;
    	     case 12: update = "UPDATE tracevia_app.notifications_states SET notif_viewed_temperature = 1 WHERE notif_equip_id = ? "; break;    	    
    	  }
    	    	
    	 try {
				
				conn = ConnectionFactory.connectToTraceviaApp();
				
				ps = conn.prepareStatement(update);					
				ps.setInt(1 , equipId);
				
				int state = ps.executeUpdate();
				
				if(state > 0)
					response = true;	
				
				//System.out.println(update);
				//System.out.println("Equip: "+equipId);
				//System.out.println("State: "+stateId);
					

			} catch (SQLException e) {
				e.printStackTrace();
			}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

    	 
    	 return response;    	 
    	 
     }
     

}
