package br.com.tracevia.webapp.dao.global;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.tracevia.webapp.cfg.NotificationsAlarmsEnum;
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
		
     public List<Notifications> Notifications() throws Exception{
		
		List<Notifications> lista = new ArrayList<Notifications>();
			  		
	   String select = "SELECT st.equip_id , st.equip_name, st.equip_type, st.equip_km, st.battery_status, st.battery_last_status, st.battery_datetime,  "
	   		+ " st.door_status, st.door_last_status, st.door_datetime, st.energy_status, st.energy_last_status, st.energy_datetime,  "
	   		+ "st.online_status, st.online_last_status, st.online_datetime, "
			+ "st.presence_status, st.presence_last_status, st.presence_datetime, "
			+ "st.temperature_status, st.temperature_last_status, st.temperature_datetime  "
			+ "FROM notifications_status st ";
									
				    try {
				    				    				    	
				    conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
					
					ps = conn.prepareStatement(select);		
					
					rs = ps.executeQuery();
																												
					if (rs.isBeforeFirst()) {
						while (rs.next()) {
							
							Notifications not;
							
							  //----------------------------------
							  // BATTERY STATUS
							 //-------------------------------
														
							   if(rs.getBoolean("st.battery_status") == true && rs.getBoolean("st.battery_last_status") == false) {	
								   
								  not = new Notifications();	
								  								 										
								  not.setEquipId(rs.getInt("st.equip_id"));		
								  not.setStatus(1); // STATUS
								  not.setAlarmType(NotificationsAlarmsEnum.LOW_BATTERY.getAlarm()); // LOWW BATTERY
								  not.setEquipType(rs.getString("st.equip_type")); //Type Notification							 							  
								  not.setViewedBgColor("dropdown-nofit-alert"); 								  
																  
								  if(!rs.getString("st.battery_datetime").equals(" "))
								    not.setDateTime(dta.formatterDateTime(rs.getString("st.battery_datetime")));
								  
								  else  not.setDateTime(dta.currentViewDateTime());
								  
								  not.setDescription(rs.getString("st.equip_name") + " " + rs.getString("st.equip_km") + " - "	+			
								  locale.getStringKey("stat_equipment_notification_low_battery_description"));
																														
							      lista.add(not);
							
							     } 
							   
							       //----------------------------------
								  // DOOR STATUS
								 //-------------------------------
							   
							   if(rs.getBoolean("st.door_status") == true && rs.getBoolean("st.door_last_status") == false) {						
									
								   not = new Notifications();	
								   
								      not.setEquipId(rs.getInt("st.equip_id"));
								      not.setStatus(1); // STATUS
								      not.setAlarmType(NotificationsAlarmsEnum.DOOR_OPENED.getAlarm()); // DOOR OPENED
									  not.setEquipType(rs.getString("st.equip_type")); //Type Notification									
									  not.setViewedBgColor("dropdown-nofit-alert"); 									  
									  
									  if(!rs.getString("st.door_datetime").equals(" "))
									     not.setDateTime(dta.formatterDateTime(rs.getString("st.door_datetime")));
									  
									  else  not.setDateTime(dta.currentViewDateTime());
									 
									  not.setDescription(rs.getString("st.equip_name") + " " + rs.getString("st.equip_km") + " - "	+				
									  locale.getStringKey("stat_equipment_notification_door_open_description"));
																															
								      lista.add(not);
								
							    } 
							   
							       //----------------------------------
								  // ENERGY STATUS
								 //-------------------------------
							   
							   if(rs.getBoolean("st.energy_status") == true && rs.getBoolean("st.energy_last_status") == false) {						
									
								   not = new Notifications();	
								   
								      not.setEquipId(rs.getInt("st.equip_id"));
								      not.setStatus(1); // STATUS
								      not.setAlarmType(NotificationsAlarmsEnum.POWER_OFF.getAlarm()); // POWER OFF
									  not.setEquipType(rs.getString("st.equip_type")); //Type Notification									
									  not.setViewedBgColor("dropdown-nofit-alert"); 					
																		
									  if(!rs.getString("st.energy_datetime").equals(" "))
									  not.setDateTime(dta.formatterDateTime(rs.getString("st.energy_datetime")));
									  
									  else  not.setDateTime(dta.currentViewDateTime());
									  
									  not.setDescription(rs.getString("st.equip_name") + " " + rs.getString("st.equip_km") + " - "	+					
									  locale.getStringKey("stat_equipment_notification_power_off_description"));
																															
								      lista.add(not);								
							   } 
							   
							   
							       //----------------------------------
								  // ONLINE STATUS
								 //-------------------------------
							   
							   if(rs.getBoolean("st.online_status") == false && rs.getBoolean("st.online_last_status") == true) {						
									
								      not = new Notifications();	
								   
								      not.setEquipId(rs.getInt("st.equip_id"));
								      not.setStatus(1); // STATUS
									  not.setAlarmType(NotificationsAlarmsEnum.OFFLINE.getAlarm()); // OFF-LINE
									  not.setEquipType(rs.getString("st.equip_type")); //Type Notification									
									  not.setViewedBgColor("dropdown-nofit-alert");				
										 									  
									  if(!rs.getString("st.online_datetime").equals(" "))
									  not.setDateTime(dta.formatterDateTime(rs.getString("st.online_datetime")));
									  
									  else  not.setDateTime(dta.currentViewDateTime());
									  
									  not.setDescription(rs.getString("st.equip_name") + " " + rs.getString("st.equip_km") + " - "	+		
									  locale.getStringKey("stat_equipment_notification_offline_description"));
																															
								      lista.add(not);								
							   } 
							   
							      //----------------------------------
								  // PRESENCE STATUS
								 //-------------------------------
							   
							   if(rs.getBoolean("st.presence_status") == true && rs.getBoolean("st.presence_last_status") == false) {						
								      not = new Notifications();	
								   
								      not.setEquipId(rs.getInt("st.equip_id"));
								      not.setStatus(1); // STATUS
									  not.setAlarmType(NotificationsAlarmsEnum.PRESENCE.getAlarm()); // PRESENCE 
									  not.setEquipType(rs.getString("st.equip_type")); //Type Equip
									  not.setViewedBgColor("dropdown-nofit-alert"); 	
																	  
									  if(!rs.getString("st.presence_datetime").equals(" "))
									     not.setDateTime(dta.formatterDateTime(rs.getString("st.presence_datetime")));
									  
									  else  not.setDateTime(dta.currentViewDateTime());
									  
									  not.setDescription(rs.getString("st.equip_name") + " " + rs.getString("st.equip_km") + " - "	+			
									  locale.getStringKey("stat_equipment_notification_presence_description"));
																															
								      lista.add(not);								
							   } 
							   
							   
							       //----------------------------------
								  // TEMPERATURE STATUS
								 //-------------------------------
							   
							   if(rs.getBoolean("st.temperature_status") == true && rs.getBoolean("st.temperature_status") == false) {						
								      
								      not = new Notifications();									   
								   
								      not.setEquipId(rs.getInt("st.equip_id"));
								      not.setStatus(1); // STATUS
									  not.setAlarmType(NotificationsAlarmsEnum.HIGH_TEMPERATURE.getAlarm()); // TEMPERATURE LOW
									  not.setEquipType(rs.getString("st.equip_type")); //Type Equip
									  not.setViewedBgColor("dropdown-nofit-alert"); 	
									  									  									
									  if(!rs.getString("st.temperature_datetime").equals(" "))
									    not.setDateTime(dta.formatterDateTime(rs.getString("st.temperature_datetime")));
									  
									  else  not.setDateTime(dta.currentViewDateTime());
									  
									  not.setDescription(rs.getString("st.equip_name") + " " + rs.getString("st.equip_km") + " - "	+		
									  locale.getStringKey("stat_equipment_notification_high_temperature_description"));
																															
								      lista.add(not);								
							   } 							
					       }
					    }					    		

				} catch (SQLException e) {
					e.printStackTrace();
				}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

		  return lista;
	  } 	
     
     
    public List<Notifications> NotificationStatus(String type) throws Exception{
 		
 		List<Notifications> lista = new ArrayList<Notifications>();
 			  		
 	   String select = "SELECT equip_id, online_status FROM notifications_status WHERE equip_type = ? ";
 									
 				    try {
 				    				    				    	
 				    conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
 					
 					ps = conn.prepareStatement(select);		
 					ps.setString(1, type);
 					
 					rs = ps.executeQuery();
 																												
 					if (rs.isBeforeFirst()) {
 						while (rs.next()) {
 							
 							Notifications not = new Notifications();	
 								  								 										
 								  not.setEquipId(rs.getInt("equip_id"));		
 								  not.setStatus(rs.getInt("online_status")); // STATUS
 								  
 								  if(not.getStatus() == 0)
 								  not.setAlarmType(NotificationsAlarmsEnum.ONLINE.getAlarm()); // ONLINE
 								  
 								  else  not.setAlarmType(NotificationsAlarmsEnum.OFFLINE.getAlarm()); // OFFLINE								  
 								 								  																														
 							      lista.add(not); 							
 							   
 						    }
 					     }					    		

 				} catch (SQLException e) {
 					e.printStackTrace();
 				}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

 		  return lista;
 	  }
      
          
     public Integer notificationsCount() throws Exception {
    	 
    	 int count = 0;
    	 
    	 String select = "SELECT IFNULL(SUM(IF(st.battery_status = 1, 1 , 0) + " +
    			 "IF(st.door_status = 1, 1, 0) + " +
    			 "IF(st.energy_status = 1, 1, 0) + " +
    			 "IF(st.online_status = 1, 1, 0) + " +
    			 "IF(st.presence_status = 1, 1, 0) + " +
    			 "IF(st.temperature_status = 1, 1, 0)), 0) 'NOTIFICATIONS' " +
    			 "FROM notifications_status st "; 			
    	
    	 try {
				
    	    	conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
				
				ps = conn.prepareStatement(select);	
								
				//System.out.println(select);
				
				rs = ps.executeQuery();
									
				if (rs.isBeforeFirst()) {
					while (rs.next()) {
																	
						count = rs.getInt("NOTIFICATIONS");						
				    }				
			     }			

			} catch (SQLException e) {
				e.printStackTrace();
			}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

    	 
    	 return count;    	 
    	 
     }
        
  
  public  boolean updateNotificationStatus(int stateId, int equipId, String datetime, String type) throws Exception {
	  
	  boolean response = false;
	    	
	  String update = " ";
	  
	  switch(stateId) {
	  
	     case 1: update = "UPDATE notifications_status SET battery_status = 0 , battery_last_status = 1 , battery_datetime = ? WHERE equip_id = ? AND equip_type = ? AND online_last_status = 0 "; break;
	     case 2: update = "UPDATE notifications_status SET battery_status = 1 , battery_last_status = 0 , battery_datetime = ? WHERE equip_id = ? AND equip_type = ? AND online_last_status = 1 "; break;
	     case 3: update = "UPDATE notifications_status SET door_status = 0 , door_last_status = 1 , door_datetime = ? WHERE equip_id = ? AND equip_type = ? AND online_last_status = 0 "; break;
	     case 4: update = "UPDATE notifications_status SET door_status = 1 , door_last_status = 0 , door_datetime = ? WHERE equip_id = ? AND equip_type = ? AND online_last_status = 1 "; break;
	     case 5: update = "UPDATE notifications_status SET energy_status = 0 , energy_last_status = 1 , energy_datetime = ? WHERE equip_id = ? AND equip_type = ? AND online_last_status = 0 "; break;
	     case 6: update = "UPDATE notifications_status SET energy_status = 1 , energy_last_status = 0 , energy_datetime = ? WHERE equip_id = ? AND equip_type = ? AND online_last_status = 1 "; break;
	     case 7: update = "UPDATE notifications_status SET online_status = 0 , online_last_status = 1 , online_datetime = ? WHERE equip_id = ? AND equip_type = ?"; break;
	     case 8: update = "UPDATE notifications_status SET online_status = 1 , online_last_status = 0 , online_datetime = ? WHERE equip_id = ? AND equip_type = ?"; break;
	     case 9: update = "UPDATE notifications_status SET presence_status = 0 , presence_last_status = 1 , presence_datetime = ? WHERE equip_id = ? AND equip_type = ? AND online_last_status = 0 "; break;
	     case 10: update = "UPDATE notifications_status SET presence_status = 1 , presence_last_status = 0 , presence_datetime = ? WHERE equip_id = ? AND equip_type = ? AND online_last_status = 1 "; break;
	     case 11: update = "UPDATE notifications_status SET temperature_status = 0 , temperature_last_status = 1 , temperature_datetime = ? WHERE equip_id = ? AND equip_type = ? AND online_last_status = 0 "; break;
	     case 12: update = "UPDATE notifications_status SET temperature_status = 1 , temperature_last_status = 0 , temperature_datetime = ? WHERE equip_id = ? AND equip_type = ? AND online_last_status = 1 "; break;  
	     
	  }
		  
	  try {
			
	    	conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(update);	
			
			ps.setString(1 , datetime);			
			ps.setInt(2, equipId);
			ps.setString(3, type);
			
			int state = ps.executeUpdate();
			
			if(state > 0)
				response = true;	
						

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

	 
	 return response;  
	  
  }
    
  
  public boolean insertNotificationHistory( int state_id, int equip_id, String datetime, String type) throws Exception {
	 	  
	  boolean state = false;
		  
	  String insert = "INSERT INTO notifications_history (id, equip_id, state_id, datetime_, equip_type) VALUES(null, ?, ?, ?, ?)";
	  
	  try {
		  
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			ps = conn.prepareStatement(insert);
			
			//passando valores para os atributos BD
			ps.setInt(1, equip_id);
			ps.setInt(2, state_id);
			ps.setString(3, datetime);
			ps.setString(4, type);
			
			int res = ps.executeUpdate();
						
			if(res > 0) 
				state = true;
					
		}catch (SQLException inserirOcorrencia){
			throw new Exception("Erro ao inserir dados: " + inserirOcorrencia);
		}finally {
			ConnectionFactory.closeConnection(conn, ps);
		}
		
		return state;

	  
  }
     

}
