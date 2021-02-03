package br.com.tracevia.webapp.controller.global;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.tracevia.webapp.dao.global.LoginAccountDAO;
import br.com.tracevia.webapp.methods.EmailModels;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.global.InMemoryAuthentication;
import br.com.tracevia.webapp.model.global.LoadStartupModules;
import br.com.tracevia.webapp.model.global.UserAccount;
import br.com.tracevia.webapp.util.EmailUtil;
import br.com.tracevia.webapp.util.EncryptPasswordUtil;
import br.com.tracevia.webapp.util.GeneratePasswordUtil;
import br.com.tracevia.webapp.util.InMemoryAuthenticationUtil;
import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.MessagesUtil;

@ManagedBean(name="loginAccount")
@SessionScoped
public class LoginAccountBean {
	
	private UserAccount user;
	private UserAccount login;	
	private static final String EMAIL_PATTERN = 
			"[\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]";
	
	private static final String LOGS_ERROR_PATH =  "C:/Tracevia/software/logs/error";
	
	LocaleUtil locale, locale1, locale2;
	
	LoadStartupModules load;
	String mapUI, linearMapUI; 
		
	InetAddress addr;
					
	public UserAccount getUser() {
		return user;
	}

	public void setUser(UserAccount user) {
		this.user = user;
	}
	
	public UserAccount getLogin() {
		return login;
	}

	public void setLogin(UserAccount login) {
		this.login = login;
	}
	
	public LoadStartupModules getLoad() {
		return load;
	}

	public void setLoad(LoadStartupModules load) {
		this.load = load;
	}
	
	public String getMapUI() {
		return mapUI;
	}

	public void setMapUI(String mapUI) {
		this.mapUI = mapUI;
	}
	
	public String getLinearMapUI() {
		return linearMapUI;
	}

	public void setLinearMapUI(String linearMapUI) {
		this.linearMapUI = linearMapUI;
	}

	@PostConstruct
	public void initialize() {
		
		user = new UserAccount();	
		locale = new LocaleUtil();		
		locale.getResourceBundle(LocaleUtil.MESSAGES_LOGIN); 
		
		locale1 = new LocaleUtil();
		locale1.getResourceBundle(LocaleUtil.MESSAGES_EMAIL); 
		
		locale2 = new LocaleUtil();
		locale2.getResourceBundle(LocaleUtil.MESSAGES_REQUIRED); 
		
		//GET LOCAL HOST ADDRESS
		try {
			
			addr = InetAddress.getLocalHost();		
		
		} catch (UnknownHostException e) {			
			    e.printStackTrace();
		}
				
	}
		
   public String loginValidation() throws Exception {
	   
	   load = new LoadStartupModules(); //Carregar os módulos
	  	   
	   boolean status = false, inMemory = false, isName = false;	
	   InMemoryAuthentication memoryAuth = new InMemoryAuthentication();
	   InMemoryAuthenticationUtil memoryUtil = new InMemoryAuthenticationUtil();
	   EncryptPasswordUtil encrypt = new EncryptPasswordUtil();
	   MessagesUtil message = new MessagesUtil();
	   
	   LoginAccountDAO dao = new LoginAccountDAO();	   
	   RoadConcessionaire roadConcessionaire = new RoadConcessionaire();
	   
	   FacesContext context = FacesContext.getCurrentInstance();
	   
	    //IF SUCCESS ON AUTH GET SERVER INFORMATION
	    isName = roadConcessionaire.defineConcessionarieValues(addr.getHostAddress());
	  	    
	    if(isName) {
	   	 	   
	   //First - Auth memory user
	   inMemory =  memoryUtil.validateUserInMemory(user.getUsername()); 
	   	 
	   if(inMemory) {
		
		   memoryAuth = memoryUtil.authUserInMemory(user.getUsername(), user.getPassword());
		   
		   //System.out.println("Memory: "+memoryAuth.getUsername());
		   		   
		   if(memoryAuth.getUsername() == null)
			   message.ErrorMessage(locale.getStringKey("login_message_incorrect_password_header"), locale.getStringKey("login_message_incorrect_password_body"));
			
		    else {		    	
		   							   
			context.getExternalContext().getSessionMap().put("user", memoryAuth.getUsername());
			context.getExternalContext().getSessionMap().put("nivel", memoryAuth.getPermission_id()); // Super User
			context.getExternalContext().getSessionMap().put("concessionaria", RoadConcessionaire.roadConcessionaire); 
					  
			load.startupComponents(); //Inicializar Componentes			
			mapUI = RoadConcessionaire.mapUI; // Load Map
			linearMapUI = RoadConcessionaire.linearMapUI;
									
		    return "/pages/main/dashboard/dashboard.xhtml?faces-redirect=true"; 		    
		    
		  } 
		 	   
	   } else {
	   
	   //Else Auth DataBase User
	  
	  status = dao.UserValidation(user.getUsername());
	  
	  //caso seja verdadeiro passar usuário e senha para validação
	  	  
	  if(status) {
	  
	  login = new UserAccount();
	  login = dao.loginValidation(user.getUsername(), encrypt.encryptPassword(user.getPassword()));
		 
	  if (login != null) {		  
		  if(login.isActiveStatus() == true) {			  	
			  			  
			  context.getExternalContext().getSessionMap().put("user", login.getUsername());
			  context.getExternalContext().getSessionMap().put("nivel", login.getPermission_id());
			  context.getExternalContext().getSessionMap().put("concessionaria", RoadConcessionaire.roadConcessionaire); 
			  	  
			  load.startupComponents(); //Inicializar Componentes	
			  mapUI = RoadConcessionaire.mapUI; // Load Map
			  linearMapUI = RoadConcessionaire.linearMapUI;
			  
			  return "/pages/main/dashboard/dashboard.xhtml?faces-redirect=true"; 				 
		  
		  }	message.ErrorMessage(locale.getStringKey("login_message_inactive_user"), "" );  
		  
	    } else message.ErrorMessage(locale.getStringKey("login_message_incorrect_password_header"), locale.getStringKey("login_message_incorrect_password_body"));
	  
	  } else message.ErrorMessage(locale.getStringKey("login_message_user_not_found"), "" );
	   
	  } //Fim do Else - Do Nothing
	   
	 } else message.ErrorMessage(locale.getStringKey("login_message_server_connection_error"), " ");   
	  	  	    
	    return null;
	    
     }        

public void LogOut() {
	FacesContext context = FacesContext.getCurrentInstance();
	ExternalContext externalContext = context.getExternalContext();		
	
	try {		
		
		context.addMessage(null,
		new FacesMessage(FacesMessage.SEVERITY_INFO, locale.getStringKey("login_logout_message"), ""));
		externalContext.getFlash().setKeepMessages(true);
		context.getExternalContext().invalidateSession();
		context.getExternalContext().redirect("${facesContext.externalContext.requestContextPath}/WebContent/RESOURCES/pages/login.xhtml");

	} catch (IOException e) {

		e.printStackTrace();
	}
	
}

public String forgetPasswordRedirect() {	
	
	return "/pages/main/login/forget.xhtml?faces-redirect=true";

}
public String forgetConfirmationRedirect() {		
	return "/pages/main/login/forget-confirmation.xhtml?faces-redirect=true";
}
public String loginRedirect() {		
	return "/pages/main/login/login.xhtml?faces-redirect=true";
}


public boolean permissionAdminOrSuper(int roleID) {
	 
	if(roleID == 1 || roleID == 6) 
		 return true;	 
	
    else return false;		
}


   public String passwordRecovery() {

	String generatedPass = "";

	boolean response = false;
		
	GeneratePasswordUtil generate = new GeneratePasswordUtil();
	EncryptPasswordUtil encrypt = new EncryptPasswordUtil();
	MessagesUtil message = new MessagesUtil();
	EmailUtil mail = new EmailUtil();
	EmailModels changeMail = new EmailModels();
	
	try {

		 LoginAccountDAO dao = new LoginAccountDAO();
		 UserAccount usr = new UserAccount();
		 
		 //Caso não seja e-mail válido
	 	  if(!user.getEmail().matches(EMAIL_PATTERN))  		  
	 		 message.ErrorMessage(locale1.getStringKey("email_recovery_invalid_header"), locale1.getStringKey("email_recovery_invalid_body"));
	 		  	 		  
		 else {	  
		  
			  //Senão faz isso ...
										
		 usr = dao.emailValidation(user.getEmail());
		 			
		if(usr.getUsername() == null)
			message.ErrorMessage(locale1.getStringKey("email_recovery_not_found"), " " );
				
		else {
			
			//Criar Senha
			generatedPass = generate.generatePassword();
						
			//Encriptar password para MD5
			usr.setPassword(encrypt.encryptPassword(generatedPass));

			//Gerar Assunto do Cadastro
			String assunto = changeMail.createRecoverySubject();
			
			//Gerar a mensagem de cadastro
			String mensagem = changeMail.createRecoveryMessage(usr.getName(), usr.getUsername(), generatedPass);
					
		    //Alterar nova senha na base de dados
		    response = dao.changePassword(usr.getUsername(), usr.getPassword(), usr.getUser_id());		
		
		if(response) {	
							
		mail.sendEmail(user.getEmail(), assunto, mensagem);	
		
		return "/pages/main/login/forget-confirmation.xhtml?faces-redirect=true";
					
	    }else message.ErrorMessage(locale1.getStringKey("email_recovery_unsuccess_send_header"), locale1.getStringKey("email_recovery_unsuccess_send_body") ); 
		
	   }			

    }     	 	  
	 	  
	}catch(Exception ex) { 
		ex.printStackTrace();
	} 
	
	return null;
}	

}
