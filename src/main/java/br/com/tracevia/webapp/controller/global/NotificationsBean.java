package br.com.tracevia.webapp.controller.global;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.context.RequestContext;

import br.com.tracevia.webapp.cfg.NotificationsTypeEnum;
import br.com.tracevia.webapp.dao.global.NotificationsDAO;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.Notifications;
import br.com.tracevia.webapp.util.EmailUtil;
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
	private long timestamp;
	
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
				
		notifications = dao.Notifications();	
		
		sendNotificationEmail(notifications);
					
		if(notifications.isEmpty()) {
			
		   Notifications not = new Notifications();
			
		   not.setEquipId(0);
		   not.setType("void");
		   not.setDescription(locale.getStringKey("stat_equipment_notification_none_notification"));
			
		   notifications.add(not);
		   		   				 
		}
				
					
	}
	
	public void countNotifications() throws Exception {
		
		NotificationsDAO dao = new NotificationsDAO();
		
		notifCount = 0;
				
		notifCount = dao.notificationsCount();	
				
	}
		
	public void updateNotificationView() throws Exception {
						
		NotificationsDAO dao = new NotificationsDAO();
		
	   boolean isUpdated = dao.updateNotificationsView(stateId, equipId);
	   
	   if(isUpdated)
	      countNotifications();
	   
	   //Update badge number
	   RequestContext.getCurrentInstance().execute("$('#badge-notif').text("+notifCount+")");
	   
	   //Update badge notification color
	   RequestContext.getCurrentInstance().execute("$('[id$="+type+""+equipId+"]').removeClass('dropdown-nofit-checked').addClass('dropdown-nofit-unchecked');");
	   
	   //show/hide when 0 count
	   if(notifCount == 0)
		   RequestContext.getCurrentInstance().execute("$('#badge-notif').hide();");	  		       
	     	 			
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
	
	//MATEUS UPDATE
	public void sendNotificationEmail(List<Notifications> notif) throws Exception {
		
		String to, cc, salute, subject, message;
		
		EmailUtil mail = new EmailUtil();
		DateTimeApplication dta = new DateTimeApplication();
		
		to = "mateus.silva@tracevia.com.br"; // To
		
		cc = "wellington.silva@tracevia.com.br"; //Contact
		
		subject = locale.getStringKey("stat_equipment_notification_subjetct_matter"); //Subject Matter
		
		salute = saluteEmail();
		
		message = "<style body{color: black;}></style>"+
		        "<b>"+salute+" "+locale.getStringKey("stat_equipment_notification_dear")+", </b>" +			
				"<br/><br/>" +
		        "<b>"+locale.getStringKey("stat_equipment_notification_status")+"</b>: " +
			    "<br/><br/>" ;
				
		         for (Notifications not : notif) {
		        	 
		           message+="\n"+ not.getDateTime() + " | <b style='color: red;'>" +not.getDescription()+"</b>";
					
				}
		      
		        message += "<br/><br/><b>"+locale.getStringKey("stat_equipment_notification_action_message")+"</b><br/><br/> " +
		        locale.getStringKey("stat_equipment_notification_best_regards")+", <br/><br/>" +
		        locale.getStringKey("stat_equipment_notification_team");		
		    
		       //send email
		       mail.sendEmailHtml(to, cc, subject, message);
		       
	}
		
	  public String saluteEmail()
      {
          String mensagem = null;
          
          Calendar calendar = Calendar.getInstance();	
          int hour = calendar.get(Calendar.HOUR_OF_DAY);   
                
          if (hour > 4 && hour < 12)
              mensagem = locale.getStringKey("stat_equipment_notification_salute_good_morning");

          else if (hour > 11 && hour < 18)
        	  mensagem = locale.getStringKey("stat_equipment_notification_salute_good_afternoon");
          
          else if(hour == 18)
        	  mensagem = locale.getStringKey("stat_equipment_notification_salute_good_evening");
          
          else  mensagem = locale.getStringKey("stat_equipment_notification_salute_good_night");

          return mensagem;
      }

}
