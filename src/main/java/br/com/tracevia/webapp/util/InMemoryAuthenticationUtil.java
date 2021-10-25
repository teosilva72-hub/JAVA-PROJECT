package br.com.tracevia.webapp.util;

import br.com.tracevia.webapp.model.global.InMemoryAuthentication;

/**
 * Classe para autenticar usuário na memória
 * @author Wellington 29/09/2021
 * @since 1.0
 * @since 1.0 
 */

public class InMemoryAuthenticationUtil {
	
	// Tracevia USER
	
	public static final String TRACEVIA_USERNAME = "Tracevia";
	public static final String TRACEVIA_PASSWORD = "trcv1234";
	public static final int TRACEVIA_ROLE = 6;
	
	// CCR USER
	
	public static final String CCR_USERNAME = "CCR_TEST";
	public static final String CCR_PASSWORD = "ccr123";
	public static final int CCR_ROLE = 6;
		
	// --------------------------------------------------------------------------------------------

	/**
	 * Método para validar usuário na mémoria
	 * @author Wellington 29/09/2021
	 * @version 1.0
	 * @since 1.0
	 * @param username - nome de usuário a ser validado
	 * @return verdeiro caso for válido
	 */
	public static boolean validateUserInMemory(String username) {
		
		   boolean isUsername = false;
					 
			 if(username.equals(TRACEVIA_USERNAME) || username.equals(CCR_USERNAME)) 
				 isUsername = true;
			 						 		 
		 return isUsername;				
	}
	
	// --------------------------------------------------------------------------------------------

	/**
	 * Método para autenticar usuário na memória
	 * @author Wellington 29/09/2021
	 * @version 1.0
	 * @since 1.0
	 * @param username - nome de usuário
	 * @param password - senha de usuário
	 * @return Object do tipo InMemoryAuthentication
	 */
	public static InMemoryAuthentication authUserInMemory(String username, String password) {
		
		InMemoryAuthentication auth = new InMemoryAuthentication(); //Estanciar Objeto
		
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

   // --------------------------------------------------------------------------------------------

}
