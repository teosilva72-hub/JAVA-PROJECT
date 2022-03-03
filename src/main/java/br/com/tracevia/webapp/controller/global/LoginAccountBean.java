package br.com.tracevia.webapp.controller.global;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.tracevia.webapp.cfg.RoadConcessionairesEnum;
import br.com.tracevia.webapp.dao.global.LoginAccountDAO;
import br.com.tracevia.webapp.dao.global.ModulesDAO;
import br.com.tracevia.webapp.dao.global.RoadConcessionaireDAO;
import br.com.tracevia.webapp.methods.EmailModel;
import br.com.tracevia.webapp.model.global.InMemoryAuthentication;
import br.com.tracevia.webapp.model.global.LoadStartupModules;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.global.UserAccount;
import br.com.tracevia.webapp.util.EmailUtil;
import br.com.tracevia.webapp.util.EncryptPasswordUtil;
import br.com.tracevia.webapp.util.GeneratePasswordUtil;
import br.com.tracevia.webapp.util.InMemoryAuthenticationUtil;
import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.LogUtils;
import br.com.tracevia.webapp.util.SessionUtil;


/**
 * Classe para ger�ncia de logins
 * @author Wellington 05/06/2020
 * @version 1.0
 * @since 1.0
 */

@ManagedBean(name = "loginAccount")
@SessionScoped
public class LoginAccountBean {

	private UserAccount user;
	private UserAccount login;
	private String credentials;    
  
	public HashMap<String, double[][]> coord = new HashMap<>();    

	LocaleUtil locale, locale1, locale2;

	LoadStartupModules load;
	RoadConcessionaire road;
	String mapUI, darkMapUI, linearMapUI;
	String plaque;
	String logo;
	boolean mapEnabled, reportsLLEnabled;
	
	@ManagedProperty("#{language}")
	private LanguageBean language;

	public String getCredentials() {
		
		String cred = credentials;
		credentials = new String();
		
		return cred;
	}

	public LanguageBean getLanguage() {
		return language;
	}

	public void setLanguage(LanguageBean language) {
		this.language = language;
	}

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
	
	public String getDarkMapUI() {
		return darkMapUI;
	}

	public void setDarkMapUI(String darkMapUI) {
		this.darkMapUI = darkMapUI;
	}
	
	public String getPlaque() {
		return plaque;
	}

	public void setPlaque(String plaque) {
		this.plaque = plaque;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getLinearMapUI() {
		return linearMapUI;
	}

	public void setLinearMapUI(String linearMapUI) {
		this.linearMapUI = linearMapUI;
	}

	public boolean getMapEnabled() {
		return mapEnabled;
	}

	public void setMapEnabled(boolean mapEnabled) {
		this.mapEnabled = mapEnabled;
	}

	public boolean getReportsLLEnabled() {
		return reportsLLEnabled;
	}

	public void setReportsLLEnabled(boolean reportsLLEnabled) {
		this.reportsLLEnabled = reportsLLEnabled;
	}
		
	public RoadConcessionaire getRoad() {
		return road;
	}

	public void setRoad(RoadConcessionaire road) {
		this.road = road;
	}

	public double[][] getCoord() {
		if (!coord.containsKey("")) {
			coordMap();
		}
		return coord.get("");
	}
	    // --------------------------------------------------------------------------------------------
	
		// CLASS PATH
		
		private static String classLocation = LoginAccountBean.class.getCanonicalName();
		
		// --------------------------------------------------------------------------------------------
		
		// CLASS LOG FOLDER
		
		private static String classErrorPath = LogUtils.ERROR.concat("login\\");
		
		// --------------------------------------------------------------------------------------------
		
		// EXCEPTION FILENAMES
		
		private static String loginValidationExceptionLog = classErrorPath.concat("validation_exception_");
		private static String loginNullExceptionLog = classErrorPath.concat("null_pointer_exception_");
		private static String recoveryPasswordExceptionLog = classErrorPath.concat("recovery_password_exception_");
			
		// --------------------------------------------------------------------------------------------		
		
		// CONSTRUTOR 

	@PostConstruct
	public void initialize() {

		user = new UserAccount();
		
		locale = new LocaleUtil();
		locale.getResourceBundle(LocaleUtil.MESSAGES_LOGIN);

		locale1 = new LocaleUtil();
		locale1.getResourceBundle(LocaleUtil.MESSAGES_EMAIL);

		locale2 = new LocaleUtil();
		locale2.getResourceBundle(LocaleUtil.MESSAGES_REQUIRED);		
	}
	
	// --------------------------------------------------------------------------------------------

	/**
	 * M�todo para valida��o de um acesso
	 * @author Wellington 12/06/2018
     * @version 1.0
     * @since 1.0 
	 * @return uma url de acesso
	 */
	public String loginValidation() {
		
		load = new LoadStartupModules(); // Carregar os m�dulos		
		road = new RoadConcessionaire();
	
		boolean status = false, inMemory = false, isName = false;
		
		InMemoryAuthentication memoryAuth = new InMemoryAuthentication();		
	
		// IF SUCCESS ON AUTH GET SERVER INFORMATION	
		isName = road.defineConcessionarieValues(language.concessionaire);
		
		try {
		
		// CHANGES
		LoginAccountDAO dao = new LoginAccountDAO();

		if (isName) {

			// First - Auth memory user
			inMemory = InMemoryAuthenticationUtil.validateUserInMemory(user.getUsername());

			if (inMemory) {

				memoryAuth = InMemoryAuthenticationUtil.authUserInMemory(user.getUsername(), user.getPassword());

				// System.out.println("Memory: "+memoryAuth.getUsername());

				if (memoryAuth.getUsername() == null) {
										
					SessionUtil.executeScript("showLoginErrorMessage();");
					SessionUtil.executeScript("hideLoginErrorMessage();");

				}

				else {
					
					//FIX					
					login = new UserAccount();
					login.setPermission_id(memoryAuth.getPermission_id());					
				
					SessionUtil.setParam("user", memoryAuth.getUsername()); // User
					SessionUtil.setParam("nivel", memoryAuth.getPermission_id()); // Super
																												
					SessionUtil.setParam("concessionaria", RoadConcessionaire.roadConcessionaire); // Concessionaire
									
					load.startupComponents(); // Inicializar Componentes
								
					// NOT IN USE
					mapUI = RoadConcessionaire.mapUI; // Load Map
					darkMapUI = RoadConcessionaire.darkMapUI;
					linearMapUI = RoadConcessionaire.linearMapUI;
					mapEnabled = RoadConcessionaire.mapEnabled;
					reportsLLEnabled = RoadConcessionaire.reportsLLEnabled;

					plaque = RoadConcessionaire.plaque;
					logo = RoadConcessionaire.logo;
															
					// STARTS MAP
														
					if(RoadConcessionaire.roadConcessionaire.equals(RoadConcessionairesEnum.Tuxpan.getConcessionaire()) ||
						  RoadConcessionaire.roadConcessionaire.equals(RoadConcessionairesEnum.EcoviasAraguaia.getConcessionaire())||
						  RoadConcessionaire.roadConcessionaire.equals(RoadConcessionairesEnum.tester.getConcessionaire()))
					    
						return "/map/map.xhtml?faces-redirect=true";
					
					else return "/dashboard/dashboard.xhtml?faces-redirect=true";

				}

			} else {

				// Else Auth DataBase User

				status = dao.UserValidation(user.getUsername());

				// caso seja verdadeiro passar usu�rio e senha para valida��o

				if (status) {

					login = new UserAccount();
					login = dao.loginValidation(user.getUsername(), EncryptPasswordUtil.encryptPassword(user.getPassword()));

					if (login != null) {
						if (login.isActiveStatus() == true) {

							SessionUtil.setParam("user", login.getUsername());
							SessionUtil.setParam("nivel", login.getPermission_id());
							SessionUtil.setParam("concessionaria", RoadConcessionaire.roadConcessionaire);
																			
							load.startupComponents(); // Inicializar Componentes
						
							mapUI = RoadConcessionaire.mapUI; // Load Map
							linearMapUI = RoadConcessionaire.linearMapUI;
							mapEnabled = RoadConcessionaire.mapEnabled;
							reportsLLEnabled = RoadConcessionaire.reportsLLEnabled;

							plaque = RoadConcessionaire.plaque;
							logo = RoadConcessionaire.logo;
					
							// STARTS MAP
							
							if(RoadConcessionaire.roadConcessionaire.equals(RoadConcessionairesEnum.Tuxpan.getConcessionaire()) ||
									RoadConcessionaire.roadConcessionaire.equals(RoadConcessionairesEnum.EcoviasAraguaia.getConcessionaire())||
									RoadConcessionaire.roadConcessionaire.equals(RoadConcessionairesEnum.tester.getConcessionaire()))
							    
								return "/map/map.xhtml?faces-redirect=true";
							
							else return "/dashboard/dashboard.xhtml?faces-redirect=true";

						} else {

							SessionUtil.executeScript("showInactiveErrorMessage();");
							SessionUtil.executeScript("hideInactiveErrorMessage();");
						}

					} else {

						SessionUtil.executeScript("showLoginErrorMessage();");
						SessionUtil.executeScript("hideLoginErrorMessage();");
					}

				} else {

					SessionUtil.executeScript("showNotFoundMessage();");
					SessionUtil.executeScript("hideNotFoundMessage();");
				}

			} // Fim do Else - Do Nothing

		} else {

			SessionUtil.executeScript("showConnectionErrorMessage();");
			SessionUtil.executeScript("hideConnectionErrorMessage();");

		   }
		
	    }catch(NullPointerException nex) {
			
			StringWriter errors = new StringWriter(); 
			nex.printStackTrace(new PrintWriter(errors));	
			
			LogUtils.logError(LogUtils.fileDateTimeFormatter(loginNullExceptionLog), classLocation, nex.getMessage(), errors.toString());
						
		}catch(Exception ex) {
			
			StringWriter errors = new StringWriter(); 
			ex.printStackTrace(new PrintWriter(errors));	

			LogUtils.logError(LogUtils.fileDateTimeFormatter(loginValidationExceptionLog), classLocation, ex.getMessage(), errors.toString());
						
		}

		return null;

	}
	
	// --------------------------------------------------------------------------------------------

	/**
	 * M�todo para encerrar uma sess�o
	 * @author Wellington 12/06/2018
     * @version 1.0
     * @since 1.0 	
	 */
	public void LogOut() throws IOException {
			
		SessionUtil.invalidate();
		SessionUtil.redirectToUrl("login.xhtml");
			
	}
	
	// --------------------------------------------------------------------------------------------

	/**
	 * M�todo para redirecionar para p�gina de recupera��o de senha
	 * @author Wellington 12/06/2018
     * @version 1.0
     * @since 1.0 
     * @return retorna para p�gina de recupera��o de senha	
	 */
	public String forgetPasswordRedirect() {

		user = new UserAccount(); // RESET

		SessionUtil.executeScript("$('#form-reset')[0].reset();"); // reset form
		SessionUtil.executeScript("$('span[for=email]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');"); // Remove Validation Icons																											
																													
		return "/forget.xhtml?faces-redirect=true";

	}
	
	// --------------------------------------------------------------------------------------------

	/**
	 * M�todo para redirecionar para p�gina de confirma��o da recupera��o de senha
	 * @author Wellington 12/06/2018
     * @version 1.0
     * @since 1.0 
     * @return retorna para p�gina de confirma��o da recupera��o de senha	
	 */
	public String forgetConfirmationRedirect() {

		user = new UserAccount(); // RESET

		SessionUtil.executeScript("$('#form-reset')[0].reset();"); // reset form
		SessionUtil.executeScript("$('span[for=email]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');"); // Remove Validation Icons	
																													

		return "/forget-confirmation.xhtml?faces-redirect=true";
	}

	// --------------------------------------------------------------------------------------------
	
	/**
	 * M�todo para redirecionar para p�gina de login
	 * @author Wellington 12/06/2018
     * @version 1.0
     * @since 1.0 
     * @return retorna para p�gina de login
	 */	
	public String loginRedirect() {

		user = new UserAccount(); // RESET

		SessionUtil.executeScript("$('#form-reset')[0].reset();"); // reset form
		SessionUtil.executeScript("$('span[for=email]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');"); // Remove Validation Icons																									

		return "/login.xhtml?faces-redirect=true";
	}
	
	// --------------------------------------------------------------------------------------------

	/**
	 * M�todo para verificar o n�vel de permiss�o do usu�rio
	 * @author Guilherme 12/08/2021
     * @version 1.0
     * @since 1.0 
     * @return retorna verdadeiro caso se enquadre em uma das op��es desejadas
	 */
	public boolean permissionAdminOrSuper(int roleID) {

		if (roleID == 1 || roleID == 6)
			return true;

		else
			return false;
	}
	
	// --------------------------------------------------------------------------------------------

	/**
	 * M�todo para processar a recupera��o de senha
	 * @author Wellington 12/06/2018
     * @version 1.0
     * @since 1.0 
     * @return retorna para p�gina de confirma��o da recupera��o de senha em caso de sucesso	
	 */
	public String passwordRecovery() {

		String generatedPass = "";

		boolean response = false;
		
		EmailModel changeMail = new EmailModel();

		try {

			LoginAccountDAO dao = new LoginAccountDAO();
			RoadConcessionaireDAO road = new RoadConcessionaireDAO();
			UserAccount usr = new UserAccount();

			String roadConcessionaire = road.IdentifyRoadConcessionarie(language.addr.getHostAddress());				

			usr = dao.emailValidation(user.getEmail(), roadConcessionaire);

			if (usr.getUsername() == null) {

				SessionUtil.executeScript("showEmailInfoMessage();");
				SessionUtil.executeScript("hideEmailInfoMessage();");

			} else {
							
				// Criar Senha
				generatedPass = GeneratePasswordUtil.generatePassword();

				// Encriptar password para MD5
				usr.setPassword(EncryptPasswordUtil.encryptPassword(generatedPass));

				// Gerar Assunto do Cadastro
				String assunto = changeMail.recoverySubject();

				// Gerar a mensagem de cadastro
				String mensagem = changeMail.recoveryMessage(usr.getName(), usr.getUsername(), generatedPass);

				// Alterar nova senha na base de dados
				response = dao.changePassword(usr.getUsername(), usr.getPassword(), usr.getUser_id(),
						roadConcessionaire);

				if (response) {

					EmailUtil.sendEmailHtml(user.getEmail(), assunto, mensagem);

					return forgetConfirmationRedirect();

				} else {
									
					SessionUtil.executeScript("showEmailRecoverySendErrorMessage();");
					SessionUtil.executeScript("hideEmailRecoverySendErrorMessage();");

				}
			}

		}catch(NullPointerException n) {
								
			SessionUtil.executeScript("hideEmailRecoveryProcessMessage();");
			
			SessionUtil.executeScript("showConnectionErrorMessage();");
			SessionUtil.executeScript("hideConnectionErrorMessage();");
			
		} catch (Exception ex) {
			
			StringWriter errors = new StringWriter(); 
			ex.printStackTrace(new PrintWriter(errors));	

			LogUtils.logError(LogUtils.fileDateTimeFormatter(recoveryPasswordExceptionLog),  classLocation, ex.getMessage(), errors.toString());
						
		}		

		return null;
	}
	
	// --------------------------------------------------------------------------------------------

	/**
	 * M�todo para obter as credenciais de um aplicativo
	 * @author Guilherme 12/07/2021
     * @version 1.0
     * @since 1.0    
	 */
	public void getCred() throws Exception {
		
		ModulesDAO mod = new ModulesDAO();
		
		String name = SessionUtil.getParametersValue("serviceName");							
			
		String[] cred = mod.getCred(name);

		credentials = "{\"name\": \""
					+ cred[0]
					+ "\", \"user\": \""
					+ cred[1]
					+ "\", \"pass\": \""
					+ cred[2]
					+ "\", \"address\": \""
					+ cred[3]
					+ "\", \"port\": \""
					+ cred[4]
					+ "\", \"ws\": \""
					+ cred[5]
					+ "\"}";
	}

	/**
	 * M�todo para obter as credenciais de um aplicativo
	 * @author Guilherme 12/07/2021
     * @version 1.0
     * @since 1.0    
	 */
	public void coordMap() {
		coordMap("");
	}

	/**
	 * M�todo para obter as credenciais de um aplicativo
	 * @author Guilherme 12/07/2021
     * @version 1.0
     * @since 1.0    
	 */
	public double[][] coordMap(String name) {
		name = (name.isEmpty() ? name : "-" + name);
		if (!coord.containsKey(name)) {
			LoginAccountDAO dao = new LoginAccountDAO();
			double[][] c = new double[2][3];


			c[0] = dao.getCoord("start" + name);
			c[1] = dao.getCoord("end" + name);
			coord.put(name, c);
		}

		return coord.get(name);
	}
	
	// --------------------------------------------------------------------------------------------
}
