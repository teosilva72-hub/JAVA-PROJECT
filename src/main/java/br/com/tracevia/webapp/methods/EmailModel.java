package br.com.tracevia.webapp.methods;

import java.time.LocalDateTime;

import br.com.tracevia.webapp.util.LocaleUtil;

public class EmailModel {

	LocaleUtil locale;
	
	// ------------------------------------------------------------------------------------------------------- // 

	// CONSTRUCTOR

	public EmailModel() {

		locale = new LocaleUtil();		
		locale.getResourceBundle(LocaleUtil.MESSAGES_EMAIL);		

	}

	// ------------------------------------------------------------------------------------------------------- // 
	// ------------------------------------------------------------------------------------------------------- // 

	// REGISTRO

	/**
	 * Método para obter o assunto da mensagem do tipo registro de usuário
	 * @author Wellington 06/10/2019
	 * @version 1.0 
	 * @since 1.0
	 * @return assunto para o registro de usuário
	 */
	public String registerSubject() {
		return locale.getStringKey("email_subject_matter_registration");
	}

	// ------------------------------------------------------------------------------------------------------- //

	/**
	 * Método para criar uma mensagem do tipo registro de usuário
	 * @author Wellington 06/10/2019
	 * @version 1.0
	 * @since 1.0
	 * @param name - nome do usuário
	 * @param username - nome de usuário do sistema
	 * @param generatedPass - senha gerada aleatóriamente 
	 * @return uma mensagem a ser enviada ao novo usuário
	 */

	public String registerMessage(String name, String username, String generatedPass) {	

		String salute = "", message = "";

		salute = SaluteEmail(); // Salute 

		message = "<style body{color: black;}></style>" +
				"<b>" + salute + "</b> " + locale.getStringKey("email_dear") + ", " +		// email salute		
				"<br/><br/>" +
				locale.getStringKey("email_welcome_registration") + "." +  // welcome message
				"<br/><br/><b><u>"+locale.getStringKey("email_data_info_registration") + "</u> : </b>" +  // registration password information
				"<br/><br/><b style='color: #009ACD;'>"+locale.getStringKey("email_user") +":</b> "+username+" " + //username
				"<br/><b style='color: #009ACD;'>" +locale.getStringKey("email_password") +":</b> "+generatedPass+" " + // new system generated password            
				"<br/><b style='color: #009ACD;'>"+ locale.getStringKey("email_change_password_message1") +" :</b> " + // change password note 
				locale.getStringKey("email_change_password_message2") + " " + // change password note 
				"<b>"+locale.getStringKey("email_change_password_message3") + ".</b><br/>" +   // change password note 
				"<b style='font-size: 12px; color: #3A5FCD;'>** "+locale.getStringKey("email_change_password_message4") + "</b>" +	// change password note                           
				"<br/><br/>"+ locale.getStringKey("email_report_message1") +"<br/> " +  // in case of problem note
				locale.getStringKey("email_report_message2") + "<br/><br/>" +  // in case of problem note
				locale.getStringKey("email_best_regards")+", <br/><br/>" + // Best Regards
				locale.getStringKey("email_team") + "<br/><br/> " + // Team	 
				"<b>"+locale.getStringKey("email_copyright")+"</b>"; // Copyright

		return message;

	}

	// ------------------------------------------------------------------------------------------------------- // 
	// ------------------------------------------------------------------------------------------------------- // 

	// RECUPERAÇÃO DE SENHA

	/**
	 * Método para obter o assunto da mensagem do tipo recuperação de senha
	 * @author Wellington 06/10/2019
	 * @version 1.0 
	 * @since 1.0
	 * @return assunto para recuperação de senha
	 */
	public String recoverySubject() {
		return locale.getStringKey("email_subject_matter_change_password");
	}

	// ------------------------------------------------------------------------------------------------------- // 

	/**
	 * Método para criar uma mensagem do tipo recuperação de senha
	 * @author Wellington 06/10/2019
	 * @version 1.0 
	 * @since 1.0
	 * @param name - nome do usuário
	 * @param username - nome de usuário do sistema
	 * @param generatedPass - senha gerada aleatóriamente 
	 * @return uma mensagem a ser enviada parar recuperação de senha
	 */

	public String recoveryMessage(String name, String username, String generatedPass) {

		String message = "";		    	

		message = "<style body{color: black;}></style>" +
				locale.getStringKey("email_hi")+" "+ name + ", " +	// email salute			     
				"<br/><br/><b><u>"+locale.getStringKey("email_data_info_change_password") + "</u> : </b>" +  // change password information
				"<br/><br/><b style='color: #009ACD;'>"+locale.getStringKey("email_user") +":</b> "+username+" " + //username
				"<br/><b style='color: #009ACD;'>" +locale.getStringKey("email_password") +":</b> "+generatedPass+" " +  // new system generated password         
				"<br/><b style='color: #009ACD;'>"+ locale.getStringKey("email_change_password_message1") +" :</b> " + // change password note 
				locale.getStringKey("email_change_password_message2") + " " + // change password note 
				"<b>"+locale.getStringKey("email_change_password_message3") + ".</b><br/>" +   // change password note 
				"<b style='font-size: 12px; color: #3A5FCD;'>** "+locale.getStringKey("email_change_password_message4") + "</b>" +	// change password note                                
				"<br/><br/>"+ locale.getStringKey("email_report_message1") +"<br/> " + // in case of problem note
				locale.getStringKey("email_report_message2") + "<br/><br/>" + // in case of problem message note
				locale.getStringKey("email_best_regards")+", <br/><br/>" + // Best Regards
				locale.getStringKey("email_team") + "<br/><br/> " +	 // Team
				"<b>"+locale.getStringKey("email_copyright")+"</b>"; // Copyright

		return message;

	}

	// ------------------------------------------------------------------------------------------------------- // 

	/**
	 * Método para criar uma saudação de email de acordo com o período do dia
	 * @author Wellington 06/10/2019
	 * @version 1.0
	 * @since 1.0
	 * @return uma saudação
	 */
	public String SaluteEmail() 
	{
		String mensagem = null;

		LocalDateTime now = LocalDateTime.now();

		int hour = now.getHour();

		if (hour > 4 && hour < 12)
			mensagem = locale.getStringKey("email_salute_good_morning"); // salute good morning

			else if (hour > 11 && hour < 18)
				mensagem = locale.getStringKey("email_salute_good_afternoon"); // salute good afternoon

			else if (hour == 18)
				mensagem = locale.getStringKey("email_salute_good_evening"); // salute good evening

			else mensagem = locale.getStringKey("email_salute_good_night"); // salute good night

		return mensagem;
	} 	


	// ------------------------------------------------------------------------------------------------------- // 

}
