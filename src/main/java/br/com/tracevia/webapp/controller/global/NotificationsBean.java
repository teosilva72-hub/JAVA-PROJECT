package br.com.tracevia.webapp.controller.global;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.context.RequestContext;

import br.com.tracevia.webapp.cfg.NotificationsTypeEnum;
import br.com.tracevia.webapp.dao.global.NotificationsDAO;
import br.com.tracevia.webapp.model.global.Notifications;
import br.com.tracevia.webapp.util.LocaleUtil;

@ManagedBean(name="notificationsView")
@RequestScoped
public class NotificationsBean {
	
	LocaleUtil locale;	
	NotificationsTypeEnum types;
			
    private List<Notifications> notifications;
    private int notifCount;
    
    private int equipId;
    private int stateId;
    private String type;
	
	public List<Notifications> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notifications> notifications) {
		this.notifications = notifications;
	}	
		
	public int getNotifCount() {
		return notifCount;
	}

	public void setNotifCount(int notifCount) {
		this.notifCount = notifCount;
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
				
		notifications = dao.Notifications();	
					
		if(notifications.isEmpty()) {
			
		   Notifications not = new Notifications();
					
		   not.setDescription(locale.getStringKey("stat_equipment_notification_none_notification"));
			
		   notifications.add(not);	
		   		 			
		}
				
					
	}
	
	public void countNotifications() throws Exception {
		
		NotificationsDAO dao = new NotificationsDAO();
		
		notifCount = 0;
				
		notifCount = dao.notificationsCount();	
		
		RequestContext.getCurrentInstance().update("notif-badge;");
			       
							
	}
		
	public void updateNotificationView() throws Exception {
						
		NotificationsDAO dao = new NotificationsDAO();
		
	   boolean isUpdated = dao.updateNotificationsView(stateId, equipId);
	   
	   if(isUpdated)
	      countNotifications();
	   
	   if(notifCount == 0)
		   RequestContext.getCurrentInstance().execute("$('#badge-notif').hide();");
									
	       RequestContext.getCurrentInstance().execute("$('#badge-notif').text("+notifCount+")");	       
	 			
	}
	
	
	//UPDATE STATUS NOTIFICATION
	public void updateNotificationStatus(int stateId, int equipId) throws Exception {
		
		NotificationsDAO dao = new NotificationsDAO();
		
	   boolean resposta =  dao.updateNotificationStatus(stateId, equipId);
	   
	   System.out.println(resposta);
	   	   
	 			
	}
	
	
	

}
