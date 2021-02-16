package br.com.tracevia.webapp.controller.global;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

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
	 private final Locale PORTUGUESE_BRAZILIAN = new Locale("pt", "BR");
			
	 //DEFAULT LANGUAGE
	private Locale locale = Locale.getDefault();
	  	
	public Locale getLocale() {
		return  (locale);
	}
			 	  	
	 public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public void English(ActionEvent event) {	  
	   locale = ENGLISH;
	   Locale.setDefault(locale);
	   updateViewLocale();
	 }
	 
	 public void Spanish(ActionEvent event) {
		locale = SPANISH;
		Locale.setDefault(locale);
		updateViewLocale();	
		
	}

	 public void Portuguese(ActionEvent event) {
		locale = PORTUGUESE_BRAZILIAN;
		Locale.setDefault(locale);
		updateViewLocale();
	  
	 }	 
	 
	 private void updateViewLocale() {
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
     }

}
