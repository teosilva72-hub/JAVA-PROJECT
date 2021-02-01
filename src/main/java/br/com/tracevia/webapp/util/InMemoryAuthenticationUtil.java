package br.com.tracevia.webapp.util;

import br.com.tracevia.webapp.model.global.InMemoryAuthentication;

public class InMemoryAuthenticationUtil {
	
	public static final String TRACEVIA_USERNAME = "Tracevia";
	public static final String TRACEVIA_PASSWORD = "trcv1234";
	public static final int TRACEVIA_ROLE = 6;
	
	public static final String CCR_USERNAME = "CCR_TEST";
	public static final String CCR_PASSWORD = "ccr123";
	public static final int CCR_ROLE = 6;
	
	private InMemoryAuthentication auth;
	
	public boolean validateUserInMemory(String username) {
		
		   boolean isUsername = false;
					 
			 if(username.equals(TRACEVIA_USERNAME) || username.equals(CCR_USERNAME)) 
				 isUsername = true;
			 						 		 
		 return isUsername;				
	}

	public InMemoryAuthentication authUserInMemory(String username, String password) {
		
		   auth = new InMemoryAuthentication(); //Estanciar Objeto
		
		 if(username.equals(TRACEVIA_USERNAME) || username.equals(CCR_USERNAME)) {
			 
			 if(username.equals(TRACEVIA_USERNAME) && password.equals(TRACEVIA_PASSWORD)) {
				 
				  auth.setUsername(TRACEVIA_USERNAME);
	              auth.setPermission_id(TRACEVIA_ROLE);	              
			 }
				 
			 else if(username.equals(CCR_USERNAME) && password.equals(CCR_PASSWORD)) {
				 
				      auth.setUsername(CCR_USERNAME);
			          auth.setPermission_id(CCR_ROLE);			          
			 }			 
		 }
		 
		 return auth;				
	}

	public InMemoryAuthentication getAuth() {
		return auth;
	}

	public void setAuth(InMemoryAuthentication auth) {
		this.auth = auth;
	}

}
