package br.com.tracevia.webapp.dao.global;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
			  		
	   String select = "SELECT st.equip_id , st.equip_name, st.equip_type, st.battery_status, st.battery_last_status, st.battery_viewed, st.battery_datetime,  "
	   		+ " st.door_status, st.door_last_status, st.door_viewed, st.door_datetime, st.energy_status, st.energy_last_status, st.energy_viewed, st.energy_datetime,  "
	   		+ "st.online_status, st.online_last_status, st.online_viewed, st.online_datetime, "
			+ "st.presence_status, st.presence_last_status, st.presence_viewed, st.presence_datetime, "
			+ "st.temperature_status, st.temperature_last_status, st.temperature_viewed, st.temperature_datetime  "
			+ "FROM notifications_status st ";
									
				    try {
				    				    				    	
				    conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
					
					ps = conn.prepareStatement(select);		
					
					rs = ps.executeQuery();
																				
					if (rs.isBeforeFirst()) {
						while (rs.next()) {
							
							Notifications not;
														
							   if(rs.getBoolean("st.battery_status") == true && rs.getBoolean("st.battery_last_status") == false) {	
								   
								  not = new Notifications();	
								  								 										
								  not.setEquipId(rs.getInt("st.equip_id"));
								  not.setStatus(2); // BATTERY LOW
								  not.setType("battery"); //Type Notification
								  
								  if(rs.getInt("st.battery_viewed") == 0)								  
								  not.setViewedBgColor("dropdown-nofit-checked"); 
								  
								  else not.setViewedBgColor("dropdown-nofit-unchecked"); 
								  
								  if(!rs.getString("st.battery_datetime").equals(""))
								    not.setDateTime(dta.formatterDateTime(rs.getString("st.battery_datetime")));
								  
								  else  not.setDateTime("");
								  
								  not.setDescription(rs.getString("st.equip_name")+" - " +		
								  locale.getStringKey("stat_equipment_notification_battery_low_description"));
																														
							      lista.add(not);
							
							     } 
							   
							   if(rs.getBoolean("st.door_status") == true && rs.getBoolean("st.door_last_status") == false) {						
									
								   not = new Notifications();	
								   
								      not.setEquipId(rs.getInt("st.equip_id"));
									  not.setStatus(4); // DOOR OPENED
									  not.setType("door"); //Type Notification
									  
									  if(rs.getInt("st.door_viewed") == 0)
										  not.setViewedBgColor("dropdown-nofit-checked"); 
									  
									  else not.setViewedBgColor("dropdown-nofit-unchecked"); 									  
									  
									  if(!rs.getString("st.door_datetime").equals(""))
									     not.setDateTime(dta.formatterDateTime(rs.getString("st.door_datetime")));
									  
									  else  not.setDateTime("");
									 
									  not.setDescription(rs.getString("st.equip_name")+" - " +		
									  locale.getStringKey("stat_equipment_notification_door_open_description"));
																															
								      lista.add(not);
								
							    } 
							   
							   if(rs.getBoolean("st.energy_status") == true && rs.getBoolean("st.energy_last_status") == false) {						
									
								   not = new Notifications();	
								   
								      not.setEquipId(rs.getInt("st.equip_id"));
									  not.setStatus(6); // POWER OFF
									  not.setType("energy"); //Type Notification
									  
									  if(rs.getInt("st.energy_viewed") == 0)
										  not.setViewedBgColor("dropdown-nofit-checked"); 
									  
									  else not.setViewedBgColor("dropdown-nofit-unchecked"); 
									  
									  if(!rs.getString("st.energy_datetime").equals(""))
									  not.setDateTime(dta.formatterDateTime(rs.getString("st.energy_datetime")));
									  
									  else  not.setDateTime("");
									  
									  not.setDescription(rs.getString("st.equip_name")+" - " +		
									  locale.getStringKey("stat_equipment_notification_energy_off_description"));
																															
								      lista.add(not);								
							   } 
							   
							   if(rs.getBoolean("st.online_status") == true && rs.getBoolean("st.online_last_status") == false) {						
									
								   not = new Notifications();	
								   
								      not.setEquipId(rs.getInt("st.equip_id"));
									  not.setStatus(8); // OFF-LINE
									  not.setType("online"); //Type Notification
									  
									  if(rs.getInt("st.online_viewed") == 0)
										  not.setViewedBgColor("dropdown-nofit-checked"); 
									  
									  else not.setViewedBgColor("dropdown-nofit-unchecked"); 
									  
									  if(!rs.getString("st.online_datetime").equals(""))
									  not.setDateTime(dta.formatterDateTime(rs.getString("st.online_datetime")));
									  
									  else  not.setDateTime("");
									  
									  not.setDescription(rs.getString("st.equip_name")+" - " +		
									  locale.getStringKey("stat_equipment_notification_status_offline_description"));
																															
								      lista.add(not);								
							   } 
							   
							   if(rs.getBoolean("st.presence_status") == true && rs.getBoolean("st.presence_last_status") == false) {						
								      not = new Notifications();	
								   
								      not.setEquipId(rs.getInt("st.equip_id"));
									  not.setStatus(10); // PRESENCE CLOSE
									  not.setType("presence"); //Type Notification
									  
									  if(rs.getInt("st.presence_viewed") == 0)
										  not.setViewedBgColor("dropdown-nofit-checked"); 
									  
									  else not.setViewedBgColor("dropdown-nofit-unchecked");  
									  
									  if(!rs.getString("st.presence_datetime").equals(""))
									     not.setDateTime(dta.formatterDateTime(rs.getString("st.presence_datetime")));
									  
									  else  not.setDateTime("");
									  
									  not.setDescription(rs.getString("st.equip_name")+" - " +		
									  locale.getStringKey("stat_equipment_notification_presence_close_description"));
																															
								      lista.add(not);								
							   } 
							   
							   if(rs.getBoolean("st.temperature_status") == true && rs.getBoolean("st.temperature_status") == false) {						
								      
								      not = new Notifications();									   
								   
								      not.setEquipId(rs.getInt("st.equip_id"));
									  not.setStatus(12); // TEMPERATURE LOW
									  not.setType("temperature"); //Type Notification
									  
									  if(rs.getInt("st.temperature_viewed") == 0)
										  not.setViewedBgColor("dropdown-nofit-checked"); 
									  
									  else not.setViewedBgColor("dropdown-nofit-unchecked");     
									  
									  if(!rs.getString("st.temperature_datetime").equals(""))
									    not.setDateTime(dta.formatterDateTime(rs.getString("st.temperature_datetime")));
									  
									  else  not.setDateTime("");
									  
									  not.setDescription(rs.getString("st.equip_name")+" - " +		
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
          
     public Integer notificationsCount() throws Exception {
    	 
    	 int count = 0;
    	 
    	 String select = "SELECT IFNULL(SUM(IF(st.battery_status = 1 AND st.battery_viewed = 0, 1 , 0) + " +
    			 "IF(st.door_status = 1 AND st.door_viewed = 0, 1, 0) + " +
    			 "IF(st.energy_status = 1 AND st.energy_viewed = 0, 1, 0) + " +
    			 "IF(st.online_status = 1 AND st.online_viewed = 0, 1, 0) + " +
    			 "IF(st.presence_status = 1 AND st.presence_viewed = 0, 1, 0) + " +
    			 "IF(st.temperature_status = 1 AND st.temperature_viewed = 0, 1, 0)), 0) 'NOTIFICATIONS' " +
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
     
     
  public boolean updateNotificationsView(int stateId, int equipId) throws Exception {
    	 
    	  boolean response = false;
    	
    	  String update = " ";
    	  
    	  switch(stateId) {
    	  
    	     case 1: update = "UPDATE notifications_status SET battery_viewed = 0 WHERE equip_id = ? "; break;
    	     case 2: update = "UPDATE notifications_status SET battery_viewed = 1 WHERE equip_id = ? "; break;
    	     case 3: update = "UPDATE notifications_status SET door_viewed = 0 WHERE equip_id = ? "; break;
    	     case 4: update = "UPDATE notifications_status SET door_viewed = 1 WHERE equip_id = ? "; break;
    	     case 5: update = "UPDATE notifications_status SET energy_viewed = 0 WHERE equip_id = ? "; break;
    	     case 6: update = "UPDATE notifications_status SET energy_viewed = 1 WHERE equip_id = ? "; break;
    	     case 7: update = "UPDATE notifications_status SET online_viewed = 0 WHERE equip_id = ? "; break;
    	     case 8: update = "UPDATE notifications_status SET online_viewed = 1 WHERE equip_id = ? "; break;
    	     case 9: update = "UPDATE notifications_status SET presence_viewed = 0 WHERE equip_id = ? "; break;
    	     case 10: update = "UPDATE notifications_status SET presence_viewed = 1 WHERE equip_id = ? "; break;
    	     case 11: update = "UPDATE notifications_status SET temperature_viewed = 0 WHERE equip_id = ? "; break;
    	     case 12: update = "UPDATE notifications_status SET temperature_viewed = 1 WHERE equip_id = ? "; break;    	    
    	  }
    	    	
    	 try {
				
    	    	conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
				
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
  
  
  public  boolean updateNotificationStatus(int stateId, int equipId, String datetime, String type) throws Exception {
	  
	  boolean response = false;
	    	
	  String update = " ";
	  
	  switch(stateId) {
	  
	     case 1: update = "UPDATE notifications_status SET battery_status = 0 , battery_last_status = 1 , battery_viewed = 0, battery_datetime = ? WHERE equip_id = ? AND equip_type = ? AND online_last_status = 0 "; break;
	     case 2: update = "UPDATE notifications_status SET battery_status = 1 , battery_last_status = 0 , battery_datetime = ? WHERE equip_id = ? AND equip_type = ? AND online_last_status = 1 "; break;
	     case 3: update = "UPDATE notifications_status SET door_status = 0 , door_last_status = 1 , door_viewed = 0, door_datetime = ? WHERE equip_id = ? AND equip_type = ? AND online_last_status = 0 "; break;
	     case 4: update = "UPDATE notifications_status SET door_status = 1 , door_last_status = 0 , door_datetime = ? WHERE equip_id = ? AND equip_type = ? AND online_last_status = 1 "; break;
	     case 5: update = "UPDATE notifications_status SET energy_status = 0 , energy_last_status = 1 , energy_viewed = 0, energy_datetime = ? WHERE equip_id = ? AND equip_type = ? AND online_last_status = 0 "; break;
	     case 6: update = "UPDATE notifications_status SET energy_status = 1 , energy_last_status = 0 , energy_datetime = ? WHERE equip_id = ? AND equip_type = ? AND online_last_status = 1 "; break;
	     case 7: update = "UPDATE notifications_status SET online_status = 0 , online_last_status = 1 , online_viewed = 0,  online_datetime = ? WHERE equip_id = ? AND equip_type = ? AND online_last_status = 0"; break;
	     case 8: update = "UPDATE notifications_status SET online_status = 1 , online_last_status = 0 , online_datetime = ? WHERE equip_id = ? AND equip_type = ? AND online_last_status = 1"; break;
	     case 9: update = "UPDATE notifications_status SET presence_status = 0 , presence_last_status = 1 , presence_viewed = 0, presence_datetime = ? WHERE equip_id = ? AND equip_type = ? AND online_last_status = 0 "; break;
	     case 10: update = "UPDATE notifications_status SET presence_status = 1 , presence_last_status = 0 , presence_datetime = ? WHERE equip_id = ? AND equip_type = ? AND online_last_status = 1 "; break;
	     case 11: update = "UPDATE notifications_status SET temperature_status = 0 , temperature_last_status = 1 , temperature_viewed = 0, temperature_datetime = ? WHERE equip_id = ? AND equip_type = ? AND online_last_status = 0 "; break;
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
