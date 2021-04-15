package br.com.tracevia.webapp.util;

import java.util.Locale;
import java.util.ResourceBundle;

import br.com.tracevia.webapp.controller.global.LanguageBean;

public class LocaleUtil {
	
	public static final String LABELS_DASHBOARD = "bundle.labels.dashboard.labels_dashboard_";
	public static final String LABELS_LOGIN = "bundle.labels.login.labels_login_";
	public static final String LABELS_PMV = "bundle.labels.pmv.labels_pmv_";
	public static final String LABELS_USERS = "bundle.labels.users.labels_users_";
	public static final String LABELS_SAT = "bundle.labels.sat.labels_sat_";
	public static final String LABELS_MTO = "bundle.labels.meteo.mto.labels_mto_";
	public static final String LABELS_RS = "bundle.labels.meteo.rs.labels_rs_";
	public static final String LABELS_VS = "bundle.labels.meteo.vs.labels_vs_";
	public static final String LABELS_CALENDAR = "bundle.labels.system.calendar.labels_calendar_";
	public static final String LABELS_DIRECTIONS = "bundle.labels.system.directions.labels_directions_";
	public static final String LABELS_EXCEL = "bundle.labels.system.excel.labels_excel_";
	public static final String LABELS_PERIODS = "bundle.labels.system.periods.labels_periods_";
	public static final String LABELS_OCC = "bundle.labels.occ.labels_occ_";
	public static final String LABELS_MAPS = "bundle.labels.system.maps.labels_maps_";
	public static final String LABELS_REALTIME = "bundle.labels.system.realtime.labels_realtime_";	
	
	public static final String MESSAGES_DASHBOARD = "bundle.messages.dashboard.messages_dashboard_";
	public static final String MESSAGES_EMAIL = "bundle.messages.system.email.messages_email_";
	public static final String MESSAGES_LOGIN = "bundle.messages.login.messages_login_";
	public static final String MESSAGES_PMV = "bundle.messages.pmv.messages_pmv_";
	public static final String MESSAGES_USERS = "bundle.messages.users.messages_users_";
	public static final String MESSAGES_VALIDATOR = "bundle.messages.system.validator.messages_validator_";
	public static final String MESSAGES_REQUIRED = "bundle.messages.system.required.messages_required_";
	public static final String MESSAGES_NOTIFICATIONS = "bundle.messages.system.notifications.messages_notifications_";
	public static final String MESSAGES_OCC = "bundle.messages.occ.messages_occ_";
	public static final String MESSAGES_MAPS = "bundle.messages.system.maps.labels_maps_";
	public static final String MESSAGES_REALTIME = "bundle.messages.system.realtime.labels_realtime_";	
	public static final String MESSAGES_MTO = "bundle.messages.meteo.mto.messages_mto_";	
	public static final String MESSAGES_RS = "bundle.messages.meteo.rs.messages_rs_";	
	public static final String MESSAGES_VS = "bundle.messages.meteo.vs.messages_vs_";	
	public static final String MESSAGES_SAT = "bundle.messages.sat.messages_sat_";	

			
	 LanguageBean language;
	 Locale locale;
	 ResourceBundle resourceBundle;	 
	 	 			 
	 public Locale currentLocale(){
		 
	       language = new LanguageBean();
		   locale = language.getLocale();	

	     return locale;	   
	 }
	 
	 		 
	 public ResourceBundle getResourceBundle(String resource){
		 
	       return resourceBundle = ResourceBundle.getBundle(resource + currentLocale().toString()); 
	 }
	 
	 // USE ONLY TO EXIT DEPEND ON LANGUAGE
	 public ResourceBundle getResourceBundleLogout(String resource, Locale locale){
		 
	       return resourceBundle = ResourceBundle.getBundle(resource + locale.toString()); 
	 }
	 
	 
	 public String getStringKey(String key){
		 
	      return resourceBundle.getString(key);
	 }

}
