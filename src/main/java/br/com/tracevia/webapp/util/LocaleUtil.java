package br.com.tracevia.webapp.util;

import java.util.Locale;
import java.util.ResourceBundle;

import br.com.tracevia.webapp.controller.global.LanguageBean;

/**
 * Classe para viabilizar o uso de traduções no projeto
 * @author Wellington - 29/09/2021
 * @version 1.0
 * @since 1.0
 *
 */

public class LocaleUtil {
	
	// LABELS RESOURCE BUNDLE
	
	public static final String LABELS_DASHBOARD = "bundle.labels.dashboard.labels_dashboard_";
	public static final String LABELS_LOGIN = "bundle.labels.login.labels_login_";
	public static final String LABELS_PMV = "bundle.labels.pmv.labels_pmv_";
	public static final String LABELS_USERS = "bundle.labels.users.labels_users_";
	public static final String LABELS_SAT = "bundle.labels.sat.labels_sat_";
	public static final String LABELS_MTO = "bundle.labels.meteo.mto.labels_mto_";
	public static final String LABELS_SV = "bundle.labels.meteo.sv.labels_sv_";
	public static final String LABELS_CALENDAR = "bundle.labels.system.calendar.labels_calendar_";
	public static final String LABELS_DIRECTIONS = "bundle.labels.system.directions.labels_directions_";
	public static final String LABELS_EXCEL = "bundle.labels.system.excel.labels_excel_";
	public static final String LABELS_PERIODS = "bundle.labels.system.periods.labels_periods_";
	public static final String LABELS_OCC = "bundle.labels.occ.labels_occ_";
	public static final String LABELS_EMAIL = "bundle.labels.email.labels_email_"; //notification send e-mail
	public static final String LABELS_MAPS = "bundle.labels.system.maps.labels_maps_";
	public static final String LABELS_REALTIME = "bundle.labels.system.realtime.labels_realtime_";	
	public static final String LABELS_WIM = "bundle.labels.wim.labels_wim_";	
	public static final String LABELS_OCR = "bundle.labels.ocr.labels_ocr_";
	public static final String LABELS_DAI = "bundle.labels.dai.labels_dai_";
	public static final String LABELS_COLAS = "bundle.labels.colas.labels_colas_";
	public static final String LABELS_ABA = "bundle.labels.aba.labels_aba_";
	public static final String LABELS_SOS = "bundle.labels.sos.labels_sos_";
	public static final String LABELS_EXCELSHEET = "bundle.labels.system.excel-sheet.labels_excel_sheet_";
	public static final String LABELS_PDF = "bundle.labels.system.pdf.labels_pdf_";
	public static final String LABELS_CHARTS = "bundle.labels.system.charts.labels_charts_";
	public static final String LABELS_REPORTS = "bundle.labels.system.reports.labels_reports_";
	public static final String LABELS_SELECTION_ITEM = "bundle.labels.system.select-item.labels_select_item_";	
	public static final String LABELS_SPEED = "bundle.labels.speed.labels_speed_";	
	
	// MESSAGES RESOURCE BUNDLE
	
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
	public static final String MESSAGES_SV = "bundle.messages.meteo.sv.messages_sv_";	
	public static final String MESSAGES_SAT = "bundle.messages.sat.messages_sat_";	
	public static final String MESSAGES_REPORTS = "bundle.messages.system.reports.messages_reports_";	
	
	// --------------------------------------------------------------------------------------------
		
	 // RESOURCE BUNDLE OBJECT
	 ResourceBundle resourceBundle;	 
	 
	// --------------------------------------------------------------------------------------------
	 	 
	 /**
	  * Método para obter o objeto do tipo Locale que representa uma região geográfica, política ou cultural específica.
	  * @author Wellington - 29/09/2021
	  * @version 1.0
	  * @since 1.0
	  * @see https://docs.oracle.com/javase/7/docs/api/java/util/Locale.html
	  * @return Object do tipo Locale
	  */
	 public Locale currentLocale(){
		 
	      LanguageBean language = new LanguageBean();
		  Locale locale = language.getLocale();	

	     return locale;	   
	 }
	 
	// --------------------------------------------------------------------------------------------
	 	 		 
	 /**
	  * Método para carregar um pacote de recursos apropriado para a localidade do sistema (região geográfica, política ou cultural específica).
	  * @author Wellington - 29/09/2021
	  * @version 1.0
	  * @since 1.0
	  * @see https://docs.oracle.com/javase/7/docs/api/java/util/ResourceBundle.html
	  * @return Object do tipo ResourceBundle
	  */
	 public ResourceBundle getResourceBundle(String resource){
		 
		 resourceBundle = ResourceBundle.getBundle(resource + currentLocale().toString()); 
		 
	     return resourceBundle;	     
	 }
	 
	// --------------------------------------------------------------------------------------------
	 
	 /**
	  * Método para retornar um valor de acordo com a chave representada
	  * @author Wellington - 29/09/2021
	  * @version 1.0
	  * @since 1.0
	  * @param key - chave a ser encontrada pelo recurso
	  * @return uma String com o valor da chave
	  */
	 public String getStringKey(String key){
		 
	      return resourceBundle.getString(key);
	 }
	 
	 
   // --------------------------------------------------------------------------------------------

	 /**
	  * Método para instanciar um recurso de idioma
	  * @author Wellington - 08/10/2021
	  * @version 1.0
	  * @since 1.0
	  * @param locale - variável que representa uma região geográfica, política ou cultural específica.
	  * @param resource - resource bundle a ser executado	
	  */
	public static LocaleUtil setLocale(String resource) {
			
		    LocaleUtil locale = new LocaleUtil();		    
		    locale.getResourceBundle(resource);
		    
		    return locale;		    
									
		}
		
  // --------------------------------------------------------------------------------------------
}
