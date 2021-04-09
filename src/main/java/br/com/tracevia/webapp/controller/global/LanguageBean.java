package br.com.tracevia.webapp.controller.global;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import br.com.tracevia.webapp.cfg.RoadConcessionairesEnum;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;

@ManagedBean(name = "language")
@SessionScoped
public class LanguageBean implements Serializable {

	/**
	 * Autor: Wellington for Tracevia in 12/05/2020
	 * updated: 26/06/2020 - Wellington
	 */
	
	 private static final long serialVersionUID = 1L;
		
	 private final Locale ENGLISH = new Locale("en","US");
	 private final Locale SPANISH = new Locale("es", "ES");	 
	 // private final Locale ARGENTINE_SPANISH = new Locale("es", "AR");
	 // private final Locale MEXICAN_SPANISH = new Locale("es", "MX");
	 // private final Locale MEXICAN_SPANISH = new Locale("es", "CO");
	 private final Locale PORTUGUESE_BRAZILIAN = new Locale("pt", "BR");	
	 
	 private Locale locale = Locale.getDefault();
	 
	 InetAddress addr;
		 
	 public Locale getLocale() {
		return  (locale);
	 }
			 	  	
	 public void setLocale(Locale locale) {
		this.locale = locale;
	 }
	 	 
	 @PostConstruct
	 public void defaultLanguage() {
		 
	   Locale.setDefault(PORTUGUESE_BRAZILIAN);
	   locale = Locale.getDefault(); 
						 	 
	 }
			 
	 public void English(ActionEvent event) {
		 		 
	   Locale.setDefault(ENGLISH);	   
	   locale = Locale.getDefault();   
	   updateViewLocale(locale);
	   
	 }
	 
	 public void Spanish(ActionEvent event) {
		
		Locale.setDefault(SPANISH);
		locale = Locale.getDefault();
		updateViewLocale(locale);	
		
	}

	 public void Portuguese(ActionEvent event) {
	
		Locale.setDefault(PORTUGUESE_BRAZILIAN);
		locale = Locale.getDefault();
		updateViewLocale(locale);
	  
	 }	 
	 
	 private void updateViewLocale(Locale locale) {
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
	
     }

}
