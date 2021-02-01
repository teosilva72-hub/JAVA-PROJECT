package br.com.tracevia.webapp.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {
	
	   private String host ="smtp.gmail.com" ;
	   private String username = "developers.tracevia@gmail.com";
	   private String password = "ufxcqungtdvwjwde";
	
	 public Session createSessionMail() {

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
		
		public void sendEmail(String to, String assunto, String conteudo) throws AddressException, MessagingException {

			String from = username;
			
			Session session = createSessionMail(); // Criando a Sessão

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));

			//InternetAddress[] address = {new InternetAddress(to)}; - Envio para um destinatário
			
			InternetAddress[] address = InternetAddress.parse(to); // Multiplus destinatários

			message.setRecipients(Message.RecipientType.TO, address);        
			message.setSentDate(new Date());
			message.setSubject(assunto); 
			message.setText(conteudo);	
			//message.setContent(message, "text/html");

			/** Método para enviar a mensagem criada */     

			Transport transport = session.getTransport("smtp");
			transport.connect(host, username, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();    

		}
		
		
		public void sendEmailHtml(String to, String assunto, String conteudo) throws AddressException, MessagingException {

			String from = username;
			
			Session session = createSessionMail(); // Criando a Sessão

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));

			//InternetAddress[] address = {new InternetAddress(to)}; - Envio para um destinatário
			
			InternetAddress[] address = InternetAddress.parse(to); // Multiplos destinatários

			message.setRecipients(Message.RecipientType.TO, address);        
			message.setSentDate(new Date());
			message.setSubject(assunto); 
			//message.setText(conteudo);	
			message.setContent(conteudo, "text/html");

			/** Método para enviar a mensagem criada */     

			Transport transport = session.getTransport("smtp");
			transport.connect(host, username, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();    

		}


}
