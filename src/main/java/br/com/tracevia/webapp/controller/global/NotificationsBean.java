package br.com.tracevia.webapp.controller.global;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import br.com.tracevia.webapp.cfg.NotificationType;
import br.com.tracevia.webapp.cfg.NotificationsAlarmsEnum;
import br.com.tracevia.webapp.dao.global.NotificationsDAO;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.NotificationsAlert;
import br.com.tracevia.webapp.model.global.NotificationsCount;
import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.SessionUtil;

@ManagedBean(name="notificationsView")
@ViewScoped
public class NotificationsBean implements Serializable {
	
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 2452505798932888236L;

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
	
	@PostConstruct
	public void initializer() {}		
	
	// ---------------------------------------------------------------------------------
	
	public void updateOnlineStatus(List<? extends Equipments> values, List<Integer> available, List<Integer> unavailable, String typeEquip) throws Exception {
		
		DateTimeApplication dt = new DateTimeApplication();
	
		// SWITCH NOTIFICATION STATUS
		
		if(!available.isEmpty()) {
							
			for(Equipments eq : values) {															
								
				if(available.contains(eq.getEquip_id()) && eq.getStatus() == 0)
					  updateStatus(NotificationsAlarmsEnum.ONLINE.getAlarm(), eq.getEquip_id(), typeEquip,
							dt.currentDateTime(), true, false);
															
				else if(unavailable.contains(eq.getEquip_id()) && eq.getStatus() == 1)
					  updateStatus(NotificationsAlarmsEnum.OFFLINE.getAlarm(), eq.getEquip_id(),
							  typeEquip, dt.currentDateTime(), false, true);																				
				
			}
												
		} else {
			
				for(Equipments eq  : values) {	
				
					if(eq.getStatus() == 1) 
						  updateStatus(NotificationsAlarmsEnum.OFFLINE.getAlarm(), eq.getEquip_id(),
								NotificationType.SAT.getType(), dt.currentDateTime(), false, true);
				}
		}
	}
	
	// ---------------------------------------------------------------------------------
	
	public void count()
	{
		NotificationsCount not = new NotificationsCount();
		dao = new NotificationsDAO();
				
		not = dao.notificationsCount();
													
		if(not.getTotal() > 0) {
			
			SessionUtil.executeScript("$('#badge-notif').css('display','block'); $('#badge-notif').html("+not.getTotal()+")");			 			
						
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
        	
        	locale = new LocaleUtil();
            locale.getResourceBundle(LocaleUtil.LABELS_NOTIFICATIONS);
        	
            not.setEquipId(0);
            not.setAlarmType(0);
            not.setEquipType("none");               
            not.setDescription(locale.getStringKey("no_notifications"));
            not.setViewedBgColor("dropdown-nofit-alert");
            
         	SessionUtil.executeScript("$('#notification-list').empty()");
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
