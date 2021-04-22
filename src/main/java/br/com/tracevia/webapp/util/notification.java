package br.com.tracevia.webapp.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import br.com.tracevia.webapp.dao.occ.OccurrencesDAO;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.Notifications;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;

@ManagedBean(name="notification")
@RequestScoped
public class notification {

	//vars
	boolean stat_battery, door, status;
	int presence;
	String temp, nameEquip, open, status1;
	int idEquip;
	//vars

	//getters and setters
		private String dateHour;
	
	public String getNameEquip() {
		return nameEquip;
	}

	public void setNameEquip(String nameEquip) {
		this.nameEquip = nameEquip;
	}

	public int getIdEquip() {
		return idEquip;
	}

	public void setIdEquip(int idEquip) {
		this.idEquip = idEquip;
	}
	//getters and setters
	private Connection conn;		
	private PreparedStatement ps;
	private ResultSet rs;

	private List<Notifications> notifications;

	LocaleUtil locale;
	DateTimeApplication dta;


	public void sendEmailUser() throws Exception {
		

		Notifications not = new Notifications();
		System.out.println(not.getEquipName()+" name equip");
		//Send e-mail
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();	
		date();
		System.out.println(idEquip +" <<");
		EmailUtil x = new EmailUtil();
		TranslationMethods trad = new TranslationMethods();
		System.out.println("e-mail enviado");
		//variables email
		String to = "representante.com.sp@gmail.com, mateus.silva@tracevia.com.br"; //email
		String nameUser = "Mateus Silva";
		String Subject = trad.notificationEmail("Subject Matter"); //Subject Matter
		String msgStatus = "<style>body{color: purple;}</style>"+nameUser+" "+trad.notificationEmail("msg")+"<br>"+"<b>"+ dateHour+"</b>"+
				"<br>" + trad.notificationEmail("Reason")+
				trad.notificationEmail("status")+" <b> "+
				trad.notificationEmail("Off-line")+"</b><br>" + 
				trad.notificationEmail("msg1")+"<br>"+trad.notificationEmail("msg2")+"<br><br>"+
				trad.notificationEmail("msg3")+"<br><br>"+trad.notificationEmail("msg4"); //contents
		//send email
		x.sendEmailHtml(to, Subject, msgStatus);

	}
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
		dateHour = x.notificationEmail("date")+" "+dia+"/"+mes+"     "+" "+hora+":"+minute;

		return dateHour;
	}
}