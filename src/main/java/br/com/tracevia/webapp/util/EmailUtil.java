package br.com.tracevia.webapp.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Classe para manipular envios de emails
 * @author Wellington 29/09/2021
 * @since 1.0
 * @since 1.0 
 * @see https://docs.oracle.com/javaee/7/api/javax/mail/package-summary.html
 */

public class EmailUtil {
	
	   // AUTH EMAIL CONFIG
	
	   private static String host ="smtp.gmail.com" ;
	   private static String username = "developers.tracevia@gmail.com";
	   private static String password = "ufxcqungtdvwjwde";
	   
		// --------------------------------------------------------------------------------------------
	
	   /**
	    * Método para criar uma sessão para o correio eletrônico
	    * @author Wellington 29/09/2021
	    * @version 1.0
	    * @since 1.0
	    * @see https://javaee.github.io/javamail/docs/api/javax/mail/Session.html
	    * @return Object do tipo Session
	    */
	   private static Session createSessionMail() {

			Properties props = new Properties();
			
			props.put("mail.transport.protocol", "smtp");	
			props.put("mail.smtp.auth", "true");
		    props.put("mail.smtp.starttls.enable", "true");
		    props.put("mail.smtp.ssl.trust", host);
		    props.put("mail.smtp.host", host);
		    props.put("mail.smtp.port", "587");
	        
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {

				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			session.setDebug(false); //Debug E-mail //False

			return session;
		}
	   
		// --------------------------------------------------------------------------------------------
			 
	   /**
	    * Método para envio de um correio eletrônico
	    * @author Wellington 29/09/2021
	    * @version 1.0
	    * @since 1.0	
	    * @param to - destinatário
	    * @param subject - assunto
	    * @param msg - mensagem a ser enviada
	    * @return verdadeiro caso seja enviado
	    */
		public static boolean sendEmail(String to, String subject, String msg) {

			boolean response = false;
			
			try {				
			
			String from = username;
			
			Session session = createSessionMail(); // Criando a Sessao

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));

			//InternetAddress[] address = {new InternetAddress(to)}; // Envio para um destinatario			
			//InternetAddress[] address = InternetAddress.parse(to); // Multiplos destinatarios
			
			InternetAddress[] EMAIL_TO = InternetAddress.parse(to); // TO EMAIL
					
            //TO
			message.setRecipients(Message.RecipientType.TO, EMAIL_TO);  			 		     
			message.setSentDate(new Date());
			message.setSubject(subject); 
			message.setText(msg);	
			
			/** Metodo para enviar a mensagem criada */         

			Transport transport = session.getTransport("smtp");
			transport.connect(host, username, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();  
			
			response = true;

			}catch(AddressException addressEx) {				 
				addressEx.printStackTrace();

			}catch(AuthenticationFailedException authEx) {
				authEx.printStackTrace();

			}catch( MessagingException mesEx) {

				mesEx.printStackTrace();
			} 

			return response;	

		}
		
		// --------------------------------------------------------------------------------------------
				
		/**
		 * Método para envio de um correio eletrônico
		 * @author Wellington 29/09/2021
		 * @version 1.0
		 * @since 1.0	
		 * @param to - destinatário
		 * @param cc - cópia para outros destinatário
		 * @param subject - assunto
		 * @param msg - mensagem a ser enviada
		 * @return verdadeiro caso seja enviado
		 */
		public static boolean sendEmail(String to, String cc, String subject, String msg) {

			boolean response = false;
			
			try {
			
			String from = username;
			
			Session session = createSessionMail(); // Criando a Sessao

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));

			//InternetAddress[] address = {new InternetAddress(to)}; // Envio para um destinatario			
			//InternetAddress[] address = InternetAddress.parse(to); // Multiplos destinatarios
			
			InternetAddress[] EMAIL_TO = InternetAddress.parse(to); // TO EMAIL
			InternetAddress[] EMAIL_CC = InternetAddress.parse(cc); // CONTACT
			
            //TO
			message.setRecipients(Message.RecipientType.TO, EMAIL_TO);  
			
			//CC
			message.setRecipients(Message.RecipientType.CC, EMAIL_CC);  
				     
			message.setSentDate(new Date());
			message.setSubject(subject); 
			message.setText(msg);	
			
			/** Metodo para enviar a mensagem criada */         

			Transport transport = session.getTransport("smtp");
			transport.connect(host, username, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close(); 
			
			response = true;

			}catch(AddressException addressEx) {				 
				addressEx.printStackTrace();

			}catch(AuthenticationFailedException authEx) {
				authEx.printStackTrace();

			}catch( MessagingException mesEx) {

				mesEx.printStackTrace();
			} 

			return response;

		}
		
		// --------------------------------------------------------------------------------------------
			
		/**
		 * Método para envio de um correio eletrônico (formatação HTML)
		 * @author Wellington 29/09/2021
		 * @version 1.0
		 * @since 1.0	
		 * @param to - destinatário		
		 * @param subject - assunto
		 * @param msg - mensagem a ser enviada
		 * @return verdadeiro caso seja enviado
		 */
		public static boolean sendEmailHtml(String to, String subject, String msg){
			
			boolean response = false;
			
			try {			
			
			String from = username;
			
			Session session = createSessionMail(); // Criando a Sessão

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));

			//InternetAddress[] address = {new InternetAddress(to)}; // Envio para um destinatario			
			//InternetAddress[] address = InternetAddress.parse(to); // Multiplos destinatarios
			
			InternetAddress[] EMAIL_TO = InternetAddress.parse(to); // TO EMAIL
						
            //TO
			message.setRecipients(Message.RecipientType.TO, EMAIL_TO); 			     
			message.setSentDate(new Date());
			message.setSubject(subject); 	
			message.setContent(msg, "text/html");

			/** Metodo para enviar a mensagem criada */     

			Transport transport = session.getTransport("smtp");
			transport.connect(host, username, password);
			transport.sendMessage(message, message.getAllRecipients());					
			transport.close(); 

			response = true;
			
			}catch(AddressException addressEx) {				 
				     addressEx.printStackTrace();
				
			}catch(AuthenticationFailedException authEx) {
				     authEx.printStackTrace();
			
			}catch( MessagingException mesEx) {
				
				mesEx.printStackTrace();
			} 
			
			return response;

		}
		
		// --------------------------------------------------------------------------------------------
		
		/**
		 * Método para envio de um correio eletrônico (formatação HTML)
		 * @author Wellington 29/09/2021
		 * @version 1.0
		 * @since 1.0	
		 * @param to - destinatário
		 * @param cc - cópia para outros destinatário
		 * @param subject - assunto
		 * @param msg - mensagem a ser enviada
		 * @return verdadeiro caso seja enviado
		 */
		public static boolean sendEmailHtml(String to, String cc, String subject, String msg) {
			
			boolean response = false;
			
			try {

			String from = username;
			
			Session session = createSessionMail(); // Criando a Sess�o

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));

			//InternetAddress[] address = {new InternetAddress(to)}; // Envio para um destinatario			
			//InternetAddress[] address = InternetAddress.parse(to); // Multiplos destinatarios
			
			InternetAddress[] EMAIL_TO = InternetAddress.parse(to); // TO EMAIL
			InternetAddress[] EMAIL_CC = InternetAddress.parse(cc); // CONTACT
			
            //TO
			message.setRecipients(Message.RecipientType.TO, EMAIL_TO);  
			
			//CC
			message.setRecipients(Message.RecipientType.CC, EMAIL_CC);  
			
			message.setSentDate(new Date());
			message.setSubject(subject); 		
			message.setContent(msg, "text/html");

			/** Metodo para enviar a mensagem criada */       

			Transport transport = session.getTransport("smtp");
			transport.connect(host, username, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();   
			
			response = true;
			
			}catch(AddressException addressEx) {				 
				addressEx.printStackTrace();

			}catch(AuthenticationFailedException authEx) {
				authEx.printStackTrace();

			}catch( MessagingException mesEx) {

				mesEx.printStackTrace();
			} 

			return response;

		}
		
	// --------------------------------------------------------------------------------------------
		
}