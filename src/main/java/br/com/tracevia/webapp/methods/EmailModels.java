package br.com.tracevia.webapp.methods;

import br.com.tracevia.webapp.util.LocaleUtil;

public class EmailModels {
	
	LocaleUtil locale;
	
	public EmailModels() {
		
		locale = new LocaleUtil();		
		locale.getResourceBundle(LocaleUtil.MESSAGES_EMAIL);		
		
	}
		
	
	//CADASTRO DE USUÁRIO
	 public String createRegisterSubject() {
		   return locale.getStringKey("email_user_registration_subject");
	    }
	     		
	public String createRegisterMessage(String name, String username, String generatedPass) {
		
		
		
		return locale.getStringKey("email_user_registration_message_hi")+name+", "
				+ "\n\n"+locale.getStringKey("email_user_registration_message")+"."		
				+ "\n\n"+locale.getStringKey("email_user_registration_message1")+": "
				+ "\n\n"+locale.getStringKey("email_user_registration_message2")+": "+username+" "
				+ "\n"+locale.getStringKey("email_user_registration_message3")+": "+generatedPass+" "
				+ "\n\n"+locale.getStringKey("email_user_registration_message4")
				+ locale.getStringKey("email_user_registration_message5")
				+ "\n\n"+locale.getStringKey("email_user_registration_message6")+""
				+ "\n\n"+locale.getStringKey("email_user_registration_message7")+", "
				+ "\n\n"+locale.getStringKey("email_user_registration_message8")+" ";
		
	      }
		     
	     //RECUPERAÇÃO DE SENHA
	     
	     public String createRecoverySubject() {
	 		   return locale.getStringKey("email_change_password_subject");
	 	    }
	     
	     public String createRecoveryMessage(String name, String username, String generatedPass) {
	 		
	    	 return locale.getStringKey("email_change_password_message_hi")+name+", "
		 				+ "\n\n"+locale.getStringKey("email_change_password_message")+": "
						+ "\n\n"+locale.getStringKey("email_change_password_message1")+": "+username+" "
						+ "\n"+locale.getStringKey("email_change_password_message2")+": "+generatedPass+" "
						+ "\n\n"+locale.getStringKey("email_change_password_message3")+" "
						+ locale.getStringKey("email_change_password_message4")+"."
						+ "\n\n"+locale.getStringKey("email_change_password_message5")+". "
						+ "\n\n"+locale.getStringKey("email_change_password_message6")+" "+", "
						+ "\n\n"+locale.getStringKey("email_change_password_message7")+" ";
	 		
	 	      }
	 	
	 	   

}
