package br.com.tracevia.webapp.util;

import java.io.IOException;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

/**
 * Classe para manipular attributos de uma sessão
 * @author Wellington - 29/09/2021
 * @version 1.0
 * @since 1.0
 *
 */

public class SessionUtil {
		
	/**
	 * Método que retorna uma instância Faces Context (Um pedido específico no início do processamento de uma requisição)
	 * Faces Context contém todas as informações por pedido relacionadas com o processamento de um único pedido JavaServer Faces.
	 * @author Wellington 08/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @see https://docs.oracle.com/javaee/7/api/javax/faces/context/FacesContext.html	
	 * @return External Context Object
	 */	
	public static FacesContext getFacesContext() {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		return facesContext;
			
	}
	
	// --------------------------------------------------------------------------------------------
	
	/**
	 * M�todo que retorna uma inst�ncia External Context (Permite que o Java Server Faces seja executado em um ambiente Servlet)
	 * Um servlet é um pequeno programa Java executado em um servidor web. Os servlets recebem e respondem às solicitações de clientes da Web, geralmente através de HTTP.
	 * @author Wellington 29/09/2021
	 * @version 1.0
	 * @since 1.0
	 * @see https://docs.oracle.com/javaee/7/api/javax/faces/context/ExternalContext.html
	 * @see https://docs.oracle.com/javaee/7/api/javax/servlet/Servlet.html
	 * @return External Context Object
	 */	
	public static ExternalContext getExternalContext() {
					
		ExternalContext externalContext = getFacesContext().getExternalContext();
						
		return externalContext;
		
	}	
	
	// --------------------------------------------------------------------------------------------
		
	/**
	 * Método para verificar o status de uma sessão
	 * @author Wellington - 29/09/2021
	 * @version 1.0
	 * @since version 1.0
	 * @see https://docs.oracle.com/javaee/7/api/javax/servlet/http/HttpSession.html
	 * @return Object session
	 */
	public static HttpSession getSession() {
		
		return (HttpSession) getExternalContext().getSession(false);
	}
	
	// --------------------------------------------------------------------------------------------
	
	/**
	 * Método para inserir valores na sessão
	 * @author Wellington - 29/09/2021
	 * @version 1.0
	 * @since 1.0
	 * @param key - nome da key a ser inserida na sessão
	 * @param value - valor a ser representada
	 */
	
	public static void setParam(String key, Object value) {
		getExternalContext().getSessionMap().put(key, value);
	}
	
	// --------------------------------------------------------------------------------------------
	
	/**
	 * Método para obter um valor da sessão
	 * @author Wellington - 29/09/2021
	 * @version 1.0
	 * @since 1.0
	 * @param key - nome da key que foi inserida na sessão	
	 * @return Object key value
	 */
	
	public static Object getParam(String key) {
		return getExternalContext().getSessionMap().get(key);
	}
	
	// --------------------------------------------------------------------------------------------
	
	/**
	 * Método para remover valor da sessão
	 * @author Wellington - 29/09/2021
	 * @version 1.0
	 * @since 1.0
	 * @param key - nome da key a ser removida da sessão	
	 */
	public static void remove(String key) {
		getExternalContext().getSessionMap().remove(key);
	}
	
	// --------------------------------------------------------------------------------------------
	
	/**
	 * Método para invalidar sessão (Encerrar no caso)
	 * @author Wellington - 29/09/2021
	 * @version 1.0
	 * @since 1.0
	 */
	public static void invalidate() {
		getExternalContext().invalidateSession();
	}
	
	// --------------------------------------------------------------------------------------------
		
	/**
	 * Método para redirecionar a uma URL
	 * @author Wellington - 29/09/2021
	 * @version 1.0
	 * @since 1.0
	 * @param url - url a ser redirecionada
	 */
	public static void redirectToUrl(String url) throws IOException {
		
		getExternalContext().redirect(url);					
		
	}
	
	// --------------------------------------------------------------------------------------------
	
	/**
	 * Método para executar scripts Javascript (Via Back-End)
	 * @author Wellington - 29/09/2021
	 * @version 1.0
	 * @since 1.0
	 * @param script - nome da função a ser executada
	 */
	public static void executeScript(String script) {
		
		RequestContext.getCurrentInstance().execute(script);
	}
	
	// --------------------------------------------------------------------------------------------
	
	/**
	 * Método atualizar elementos HTML (Via Back-End)
	 * @author Wellington - 29/09/2021
	 * @version 1.0
	 * @since 1.0
	 * @param element - id do elemento HTML
	 */
	public static void updateElement(String element) {
		
		RequestContext.getCurrentInstance().update(element);	
	
	}
	
	// --------------------------------------------------------------------------------------------		
	
	/**
	 * Método para receber paramentros da requisição HTTP (single parameters)
	 * @author Wellington - 29/09/2021
	 * @version 1.0
	 * @since 1.0
	 * @return Object do tipo Map
	 */
	 public static Map<String, String> getRequestParameterMap() throws IOException {
		
		Map<String, String> param = getExternalContext().getRequestParameterMap();
						
		return param;					
		
	}
	
	// --------------------------------------------------------------------------------------------

	/**
	* Método para receber paramentros da requisição HTTP (multi parameters)
	 * @author Wellington - 29/09/2021
	 * @version 1.0
	 * @since 1.0
	 * @return Object do tipo Map
	 */
	public static Map<String, String[]> getRequestParameterValuesMap() throws IOException {
		
		Map<String, String[]> params = getExternalContext().getRequestParameterValuesMap();
						
		return params;					
		
	}
	
	// --------------------------------------------------------------------------------------------
	
	/**
	 * Método para receber conjunto de par�metros atrav�s da key (String)
	 * @author Wellington - 29/09/2021
	 * @version 1.0
	 * @since 1.0
	 * @param key - nome da key para se obter o valor
	 * @see https://docs.oracle.com/javase/7/docs/api/java/io/IOException.html
	 * @throws IOException	 
	 * @return retorna um valor do tipo String
	 */
	public static String getParametersValue(String key) throws IOException {
										
		return getRequestParameterMap().get(key);					
		
	}	

	// --------------------------------------------------------------------------------------------
	
	/**
	 * Método para receber conjunto de nomes de par�metros atrav�s da key (String[])
	 * @author Wellington - 29/09/2021
	 * @version 1.0
	 * @since 1.0
	 * @param key - nome da key para se obter o valor
	 * @return retorna valores para um array de Strings
	 */
	public static String[] getParametersValues(String key) throws IOException {
										
		return getRequestParameterValuesMap().get(key);					
		
	}
	
	// --------------------------------------------------------------------------------------------
		
	
}
