package br.com.tracevia.webapp.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.Notifications;

@ManagedBean(name="notification")
@RequestScoped
public class Notification {

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

		//Send e-mail
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();	
		date();
		EmailUtil x = new EmailUtil();
		TranslationMethods trad = new TranslationMethods();
		System.out.println("e-mail enviado");
		//variables email
		String to = "mateus.silva@tracevia.com.br"; //email
		
		String Subject = trad.notificationEmail("Subject Matter"); //Subject Matter
		
		String msgStatus = "<style body{color: black;}></style>"+
				"<b>"+trad.notificationEmail("msg")+" "+
				"<br><br></b>"+
				dateHour+"   | "+
				trad.notificationEmail("Reason")+"  "+
				trad.notificationEmail("status")+"<b>  "+
				trad.notificationEmail("Off-line")+"</b> "+"<br><br>" + 
				"<b>"+trad.notificationEmail("msg1")+"<br>"+
				trad.notificationEmail("msg2")+"</b><br><br>"+
				trad.notificationEmail("msg3")+
				"<br><br>"+trad.notificationEmail("msg4"); //contents
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
		if(min < 10) minute = "0"+min;
		dateHour = x.notificationEmail("date")+" <b>"+dia+"/"+mes+"     "+" "+hora+":"+minute+"</b>";

		return dateHour;
	}
	
	
}