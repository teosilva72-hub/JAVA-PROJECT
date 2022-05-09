package br.com.tracevia.webapp.controller.global;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import br.com.tracevia.webapp.cfg.RoadConcessionairesEnum;
import br.com.tracevia.webapp.dao.global.RoadConcessionaireDAO;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;

@ManagedBean(name = "language")
@SessionScoped
public class LanguageBean implements Serializable {

	/**	
	 * @author: Wellington 12/05/2020
	 * @version 1.2
	 * @since 1.0
	 */

	private static final long serialVersionUID = 1L;

	private final Locale ENGLISH = new Locale("en","US");
	private final Locale SPANISH = new Locale("es", "ES");	 
    /* private final Locale ARGENTINE_SPANISH = new Locale("es", "AR");
	 private final Locale MEXICAN_SPANISH = new Locale("es", "MX");
	 private final Locale COLOMBIAN_SPANISH = new Locale("es", "CO");*/
	private final Locale PORTUGUESE_BRAZILIAN = new Locale("pt", "BR");	

	private Locale locale = Locale.getDefault();

	RoadConcessionaire roadConcessionaire;
	RoadConcessionaireDAO dao;

	InetAddress addr;

	String concessionaire;	

	public Locale getLocale() {
		return  (locale);
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getConcessionaire() {
		return concessionaire;
	}

	public void setConcessionaire(String concessionaire) {
		this.concessionaire = concessionaire;
	}

	@PostConstruct
	public void defaultLanguage() {
				
		roadConcessionaire = new RoadConcessionaire();
		dao = new RoadConcessionaireDAO();

		// GET LOCAL HOST ADDRESS
		try {

			addr = InetAddress.getLocalHost();

			concessionaire = dao.IdentifyRoadConcessionarie(addr.getHostAddress());

			if(concessionaire.equals(RoadConcessionairesEnum.CardelPozaRica.getConcessionaire()) ||
					concessionaire.equals(RoadConcessionairesEnum.Tuxpan.getConcessionaire())) {

				Locale.setDefault(SPANISH);							
				locale = Locale.getDefault(); 	
			
			}

			else {

				Locale.setDefault(PORTUGUESE_BRAZILIAN);
				locale = Locale.getDefault();
			}					

		} catch (Exception ex) { /* DO NOTHING */ 
			
	       ex.printStackTrace();
		} 	

	}

	// --------------------------------------------------------------------------------------------

	/**
	 * M�todo para alterar o idioma do sistema (Ingl�s Americano)
	 * @author Wellington 12/05/2020
	 * version 1.1
	 * @since 1.0
	 * @see https://javaee.github.io/javaee-spec/javadocs/javax/faces/event/ActionEvent.html
	 * @param event - evento originado lan�ado na interface do sistema
	 */
	public void English(ActionEvent event) {

		Locale.setDefault(ENGLISH);	   
		locale = Locale.getDefault();   
		updateViewLocale(locale);

	}

	// --------------------------------------------------------------------------------------------

	/**
	 * M�todo para alterar o idioma do sistema (Espanhol)
	 * @author Wellington 12/05/2020
	 * version 1.1
	 * @since 1.0
	 * @see https://javaee.github.io/javaee-spec/javadocs/javax/faces/event/ActionEvent.html
	 * @param event - evento originado lan�ado na interface do sistema
	 */
	public void Spanish(ActionEvent event) {

		Locale.setDefault(SPANISH);
		locale = Locale.getDefault();
		updateViewLocale(locale);	

	}

	// --------------------------------------------------------------------------------------------

	/**
	 * M�todo para alterar o idioma do sistema (Portugu�s do Brasil)
	 * @author Wellington 12/05/2020
	 * version 1.1
	 * @since 1.0
	 * @see https://javaee.github.io/javaee-spec/javadocs/javax/faces/event/ActionEvent.html
	 * @param event - evento originado lan�ado na interface do sistema
	 */
	public void Portuguese(ActionEvent event) {

		Locale.setDefault(PORTUGUESE_BRAZILIAN);
		locale = Locale.getDefault();
		updateViewLocale(locale);

	}	

	// --------------------------------------------------------------------------------------------

	/**
	 * M�todo para alterar o idioma nas interfaces do sistema
	 * @author Wellington 12/05/2020
	 * version 1.1
	 * @since 1.0
	 * @see https://docs.oracle.com/javase/8/docs/api/java/util/Locale.html
	 * @param locale - representa uma cultura especif�ca, polit�ca ou geogr�fica
	 */
	void updateViewLocale(Locale locale) {
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);

	}

	// --------------------------------------------------------------------------------------------

}
