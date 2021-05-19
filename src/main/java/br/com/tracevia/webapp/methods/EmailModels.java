package br.com.tracevia.webapp.methods;

import java.time.LocalDateTime;

import br.com.tracevia.webapp.util.LocaleUtil;

public class EmailModels {
	
	LocaleUtil locale;
	
	public EmailModels() {
		
		locale = new LocaleUtil();		
		locale.getResourceBundle(LocaleUtil.MESSAGES_EMAIL);		
		
	}
	
	  // ------------------------------------------------------------------------------------------------------- // 
			
	  //CADASTRO DE USUÁRIO
	
	     public String registerSubject() {
		   return locale.getStringKey("email_subject_matter_registration");
	    }
	     		
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
		     
	    
	     //RECUPERAÇÃO DE SENHA
	     
	     public String recoverySubject() {
	 		   return locale.getStringKey("email_subject_matter_change_password");
	 	    }
	     
	     public String recoveryMessage(String name, String username, String generatedPass) {
	    	 
	    	 String salute = "", message = "";		    	
	    	
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
