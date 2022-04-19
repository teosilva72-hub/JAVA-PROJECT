package br.com.tracevia.webapp.controller.global;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import br.com.tracevia.webapp.dao.global.NotificationsDAO;
import br.com.tracevia.webapp.model.global.NotificationsAlert;
import br.com.tracevia.webapp.model.global.NotificationsCount;
import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.SessionUtil;

@ManagedBean(name="notificationsView")
@RequestScoped
public class NotificationsBean {
	
	@ManagedProperty("#{loginAccount}")
	private LoginAccountBean login;
	
	LocaleUtil locale;	
			    
	private long timestamp;
	
	NotificationsDAO dao;						
	
	public long getTimestamp() {
		setTimestamp();

		return timestamp;
	}

	public void setTimestamp() {
		this.timestamp = System.currentTimeMillis();
	}
	
	public LoginAccountBean getLogin() {
		return login;
	}

	public void setLogin(LoginAccountBean login) {
		this.login = login;
	}
			
	       
	@PostConstruct
	public void initializer() {
		
		locale = new LocaleUtil();
		locale.getResourceBundle(LocaleUtil.LABELS_NOTIFICATIONS);
		
		try {
			
			// CASO ESSA OPÇÃO ESTEJA ATIVADA
			if(login.road.isHasNotification()) {
			
					count();
					notifications();
					
			
			}
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}
	
	// ---------------------------------------------------------------------------------
	
	public void count()
	{
		NotificationsCount not = new NotificationsCount();
		dao = new NotificationsDAO();
				
		not = dao.notificationsCount();
		
		// System.out.println("COUNT: "+not.getTotal());
						
		if(not.getTotal() > 0) {
			
			SessionUtil.executeScript("$('#badge-notif').css('display','block'); "+"$('#addequip').html("+not.getTotal()+");"+"$('#badge-notif').html("+not.getTotal()+")");			 			
						
			   if(not.getConnection() > 0) 			   
					SessionUtil.executeScript("$('#btn-act-connection').css('display','block'); $('#btn-act-connection').html("+not.getConnection()+")");
			 
		}
	 }
	
	// ---------------------------------------------------------------------------------
    
	  
    public void notifications() throws ParseException 
    {
        List<NotificationsAlert> listAux = new ArrayList<NotificationsAlert>();            
        NotificationsAlert not = new NotificationsAlert();
                           
        dao = new NotificationsDAO();
        
        listAux = dao.notifications();            

        if (!listAux.isEmpty())
        {
        	
        	 SessionUtil.executeScript("$('#notification-connection').empty()");
        	 SessionUtil.executeScript("$('#notification-list').empty()");
        	
            for(NotificationsAlert n : listAux) {           
                          	
            	 if(n.getAlarmType() == 8)                 
                     SessionUtil.executeScript("$('#notification-connection').append(backNotification('"+n.getViewedBgColor()+"', "+n.getAlarmType()+", '"+n.getEquipType()+"', "+n.getEquipId()+",'"+n.getDateTime()+"', '"+n.getEquipName()+"', '"+n.getDescription()+"')); $('#div-connection').css('display','block')");
            	        
               }  // END FOR 
            

        }else {
        	
            not.setEquipId(0);
            not.setAlarmType(0);
            not.setEquipType("none");               
            not.setDescription(locale.getStringKey("no_notifications"));
            not.setViewedBgColor("dropdown-nofit-alert");
            
            SessionUtil.executeScript("$('#notification-list').append(backWithoutNotification('"+not.getViewedBgColor()+"', "+not.getAlarmType()+", '"+not.getEquipType()+"', "+not.getEquipId()+", '"+not.getDescription()+"')); $('#div-single').css('display','block')");
   		 
 
            }
                   
    }
    
	// ---------------------------------------------------------------------------------
    
    public void updateStatus(int statusCode, int id, String equipType, String datetime, boolean status, boolean lastStatus) throws Exception
    {
    	boolean state = false;
    	
        dao = new NotificationsDAO();
        state = dao.updateStatus(statusCode, id, equipType, datetime, status, lastStatus);
       
        if(state)
        	dao.insertNotificationHistory(id, datetime, equipType, statusCode);
       
    }
    
	// ---------------------------------------------------------------------------------
    
    public List<NotificationsAlert> notificationStatus(String type) throws Exception {
		
		List<NotificationsAlert> list = new ArrayList<NotificationsAlert>();
		NotificationsDAO dao = new NotificationsDAO();
						
		 list = dao.notificationStatus(type);		
		 
		 return list;
		
		
	}

   // ---------------------------------------------------------------------------------
    
  }
