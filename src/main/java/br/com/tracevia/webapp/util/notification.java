package br.com.tracevia.webapp.util;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.primefaces.context.RequestContext;

import br.com.tracevia.webapp.dao.global.NotificationsDAO;
import br.com.tracevia.webapp.dao.global.notificationDAO;
import br.com.tracevia.webapp.dao.occ.OccurrencesDAO;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.Notifications;

@ManagedBean(name="notification")
@RequestScoped
public class notification {
	
	//vars
	boolean stat_battery, door, status;
	int presence;
	String temp, batteryStatus, door1, open, status1;
	//vars
	
	//getters and setters
	
	
	public boolean isStat_battery() {
		return stat_battery;
	}

	public void setStat_battery(boolean stat_battery) {
		this.stat_battery = stat_battery;
	}

	public boolean isDoor() {
		return door;
	}

	public void setDoor(boolean door) {
		this.door = door;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getPresence() {
		return presence;
	}

	public void setPresence(int presence) {
		this.presence = presence;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}	//getters and setters
	private String dateHour;
	public String date() throws Exception {
		TranslationMethods x = new TranslationMethods();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		//passando os valores predeterminados para as variveis
		int day1 = LocalDateTime.now().getDayOfMonth();
		String dia = String.valueOf(day1);
		int year1 = LocalDateTime.now().getYear();
		String ano = String.valueOf(year1);
		int month1 = LocalDateTime.now().getMonthValue();
		String mes = String.valueOf(month1);
		int hora = LocalDateTime.now().getHour();
		String hour = String.valueOf(hora);
		int min = LocalDateTime.now().getMinute();
		String minute = String.valueOf(min);
		int sec = LocalDateTime.now().getSecond();
		String second = String.valueOf(sec);
		dateHour = x.notificationEmail("date")+" "+dia+"/"+mes+"     "+ x.notificationEmail("hour")+" "+hora+":"+minute;
		
		return dateHour;
	}
	public void sendEmailUser() throws Exception {
		Notifications not = new Notifications();
		
		//Send e-mail
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();	
		date();
		EmailUtil x = new EmailUtil();
		notificationDAO z = new notificationDAO();
		TranslationMethods trad = new TranslationMethods();
		status = false;
		
		//condition 
		
		if(!status) {status1 = trad.notificationEmail("Reason")+trad.notificationEmail("status")+" <b> "+trad.notificationEmail("Off-line")+"</b><br>";
		}else status1 = "";
		Notifications q = new Notifications();
		NotificationsDAO r = new NotificationsDAO();
		//variables email
		String to = "representante.com.sp@gmail.com, mateus.silva@tracevia.com.br"; //email
		String nameUser = "Mateus Silva";
		String Subject = trad.notificationEmail("Subject Matter"); //Subject Matter
		String msgStatus = "<style>body{color: black;}</style>"+nameUser+" "+trad.notificationEmail("msg")+"<br>"+"<b>"+ dateHour+"</b>"+
							"<br>" + status1 + 
							trad.notificationEmail("msg1")+"<br>"+trad.notificationEmail("msg2")+"<br><br>"+
							trad.notificationEmail("msg3")+"<br><br>"+trad.notificationEmail("msg4"); //contents
		//send email
		x.sendEmailHtml(to, Subject, msgStatus);

	}
}