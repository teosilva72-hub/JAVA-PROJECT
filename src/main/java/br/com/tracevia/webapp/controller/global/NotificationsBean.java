package br.com.tracevia.webapp.controller.global;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.tracevia.webapp.cfg.NotificationsTypeEnum;
import br.com.tracevia.webapp.dao.global.NotificationsDAO;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.global.Notifications;
import br.com.tracevia.webapp.util.EmailUtil;
import br.com.tracevia.webapp.util.LocaleUtil;


@ManagedBean(name="notificationsView")
@RequestScoped
public class NotificationsBean {
	
	LocaleUtil locale;	
	NotificationsTypeEnum types;
			
    private List<Notifications> notifications;  
    private static List<Notifications> notificationStatus;  
    private int notifCount;
    
    private int equipId;
    private int stateId;
    private String type;
	private long timestamp;
	
	int delay;
	int interval;
	Timer timer;
	
	DateTimeApplication dta;
		
	public List<Notifications> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notifications> notifications) {
		this.notifications = notifications;
	}	
			
	public static List<Notifications> getNotificationStatus() {
		return notificationStatus;
	}

	public int getNotifCount() {
		return notifCount;
	}

	public void setNotifCount(int notifCount) {
		this.notifCount = notifCount;
	}
		
	public long getTimestamp() {
		setTimestamp();

		return timestamp;
	}

	public void setTimestamp() {
		this.timestamp = System.currentTimeMillis();
	}
			
	public int getEquipId() {
		return equipId;
	}

	public void setEquipId(int equipId) {
		this.equipId = equipId;
	}
	
	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
		
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	@PostConstruct
	public void initializer() {
		
		locale = new LocaleUtil();
		locale.getResourceBundle(LocaleUtil.MESSAGES_NOTIFICATIONS);
			
		try {
			
			findNotifications();
			countNotifications();
				
		} catch (Exception e) {			
			e.printStackTrace();
		}
				
	}
		
	public void findNotifications() throws Exception {
		
		notifications = new ArrayList<Notifications>();
		NotificationsDAO dao = new NotificationsDAO();
		
		timer = new Timer();
				
		notifications = dao.Notifications();
										
		if(notifications.isEmpty()) {
									
		   Notifications not = new Notifications();
			
		   not.setEquipId(0);
		   not.setEquipType("none");
		   not.setDescription(locale.getStringKey("stat_equipment_notification_none_notification"));
		   not.setViewedBgColor("dropdown-nofit-none"); 
			
		   notifications.add(not);
		   				   		   				 
		}
				
					
	}
	
	public List<Notifications> getNotificationStatus(String type) throws Exception {
		
		notificationStatus = new ArrayList<Notifications>();
		NotificationsDAO dao = new NotificationsDAO();
						
		return notificationStatus = dao.NotificationStatus(type);		
		
		
	}
	
	
	public void countNotifications() throws Exception {
		
		NotificationsDAO dao = new NotificationsDAO();
		
		notifCount = 0;
				
		notifCount = dao.notificationsCount();	
		
	}
				
	//UPDATE STATUS NOTIFICATION
	//ON READ EQUIPMENTS
	public void updateNotificationStatus(int stateId, int equipId, String type) throws Exception {
			
		DateTimeApplication dta = new DateTimeApplication();
		NotificationsDAO dao = new NotificationsDAO();
		
		boolean state = false;
		
		String datetime = dta.currentDateTime();
		
		state = dao.updateNotificationStatus(stateId, equipId, datetime, type);	   
		
		if(state)
			dao.insertNotificationHistory(stateId, equipId, datetime, type);
	  
	}
				
	
}
