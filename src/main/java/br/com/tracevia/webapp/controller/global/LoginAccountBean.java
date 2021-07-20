package br.com.tracevia.webapp.controller.global;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import br.com.tracevia.webapp.dao.global.LoginAccountDAO;
import br.com.tracevia.webapp.dao.global.ModulesDAO;
import br.com.tracevia.webapp.dao.global.RoadConcessionaireDAO;
import br.com.tracevia.webapp.methods.EmailModels;
import br.com.tracevia.webapp.model.global.InMemoryAuthentication;
import br.com.tracevia.webapp.model.global.LoadStartupModules;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.global.UserAccount;
import br.com.tracevia.webapp.util.EmailUtil;
import br.com.tracevia.webapp.util.EncryptPasswordUtil;
import br.com.tracevia.webapp.util.GeneratePasswordUtil;
import br.com.tracevia.webapp.util.InMemoryAuthenticationUtil;
import br.com.tracevia.webapp.util.LocaleUtil;

@ManagedBean(name = "loginAccount")
@SessionScoped
public class LoginAccountBean {

	private UserAccount user;
	private UserAccount login;
	private String credentials;    

	private static final String EMAIL_PATTERN = "[\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]";

	private static final String LOGS_ERROR_PATH = "C:/Tracevia/software/logs/error";

	LocaleUtil locale, locale1, locale2;

	LoadStartupModules load;
	String mapUI, linearMapUI;
	String plaque;
	String logo;
	boolean mapEnabled, reportsLLEnabled;

	InetAddress addr;

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

	@PostConstruct
	public void initialize() {

		user = new UserAccount();
		locale = new LocaleUtil();
		locale.getResourceBundle(LocaleUtil.MESSAGES_LOGIN);

		locale1 = new LocaleUtil();
		locale1.getResourceBundle(LocaleUtil.MESSAGES_EMAIL);

		locale2 = new LocaleUtil();
		locale2.getResourceBundle(LocaleUtil.MESSAGES_REQUIRED);

		// GET LOCAL HOST ADDRESS
		try {

			addr = InetAddress.getLocalHost();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		//ip = addr.getHostAddress();

	}

	public String loginValidation() throws Exception {
		
		load = new LoadStartupModules(); // Carregar os m�dulos
	
		boolean status = false, inMemory = false, isName = false;
		InMemoryAuthentication memoryAuth = new InMemoryAuthentication();
		InMemoryAuthenticationUtil memoryUtil = new InMemoryAuthenticationUtil();
		EncryptPasswordUtil encrypt = new EncryptPasswordUtil();
			
		RoadConcessionaire roadConcessionaire = new RoadConcessionaire();
		FacesContext context = FacesContext.getCurrentInstance();

		// IF SUCCESS ON AUTH GET SERVER INFORMATION	
		isName = roadConcessionaire.defineConcessionarieValues(language.concessionaire);
		
		// CHANGES
		LoginAccountDAO dao = new LoginAccountDAO();

		if (isName) {

			// First - Auth memory user
			inMemory = memoryUtil.validateUserInMemory(user.getUsername());

			if (inMemory) {

				memoryAuth = memoryUtil.authUserInMemory(user.getUsername(), user.getPassword());

				// System.out.println("Memory: "+memoryAuth.getUsername());

				if (memoryAuth.getUsername() == null) {

					RequestContext.getCurrentInstance().execute("showLoginErrorMessage();");
					RequestContext.getCurrentInstance().execute("hideLoginErrorMessage();");

				}

				else {
					
					//FIX					
					login = new UserAccount();
					login.setPermission_id(memoryAuth.getPermission_id());

					context.getExternalContext().getSessionMap().put("user", memoryAuth.getUsername());
					context.getExternalContext().getSessionMap().put("nivel", memoryAuth.getPermission_id()); // Super
																												// User
					context.getExternalContext().getSessionMap().put("concessionaria",
							RoadConcessionaire.roadConcessionaire);
									
					load.startupComponents(); // Inicializar Componentes
								
					// NOT IN USE
					mapUI = RoadConcessionaire.mapUI; // Load Map
					linearMapUI = RoadConcessionaire.linearMapUI;
					mapEnabled = RoadConcessionaire.mapEnabled;
					reportsLLEnabled = RoadConcessionaire.reportsLLEnabled;

					plaque = RoadConcessionaire.plaque;
					logo = RoadConcessionaire.logo;

					return "/map/map.xhtml?faces-redirect=true";

				}

			} else {

				// Else Auth DataBase User

				status = dao.UserValidation(user.getUsername());

				// caso seja verdadeiro passar usu�rio e senha para valida��o

				if (status) {

					login = new UserAccount();
					login = dao.loginValidation(user.getUsername(), encrypt.encryptPassword(user.getPassword()));

					if (login != null) {
						if (login.isActiveStatus() == true) {

							context.getExternalContext().getSessionMap().put("user", login.getUsername());
							context.getExternalContext().getSessionMap().put("nivel", login.getPermission_id());
							context.getExternalContext().getSessionMap().put("concessionaria",
									RoadConcessionaire.roadConcessionaire);
																			
							load.startupComponents(); // Inicializar Componentes
						
							mapUI = RoadConcessionaire.mapUI; // Load Map
							linearMapUI = RoadConcessionaire.linearMapUI;
							mapEnabled = RoadConcessionaire.mapEnabled;
							reportsLLEnabled = RoadConcessionaire.reportsLLEnabled;

							plaque = RoadConcessionaire.plaque;
					
							return "/dashboard/dashboard.xhtml?faces-redirect=true";

						} else {

							RequestContext.getCurrentInstance().execute("showInactiveErrorMessage();");
							RequestContext.getCurrentInstance().execute("hideInactiveErrorMessage();");
						}

					} else {

						RequestContext.getCurrentInstance().execute("showLoginErrorMessage();");
						RequestContext.getCurrentInstance().execute("hideLoginErrorMessage();");
					}

				} else {

					RequestContext.getCurrentInstance().execute("showNotFoundMessage();");
					RequestContext.getCurrentInstance().execute("hideNotFoundMessage();");
				}

			} // Fim do Else - Do Nothing

		} else {

			RequestContext.getCurrentInstance().execute("showConnectionErrorMessage();");
			RequestContext.getCurrentInstance().execute("hideConnectionErrorMessage();");

		}

		return null;

	}

	public void LogOut() throws IOException {

		FacesContext context = FacesContext.getCurrentInstance();

		ExternalContext externalContext = context.getExternalContext();
		externalContext.getFlash().setKeepMessages(true);
		externalContext.invalidateSession();
		externalContext.redirect("login.xhtml");

	}

	public String forgetPasswordRedirect() {

		user = new UserAccount(); // RESET

		RequestContext.getCurrentInstance().execute("$('#form-reset')[0].reset();"); // reset form
		RequestContext.getCurrentInstance()
				.execute("$('span[for=email]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');"); // Remove
																													// Validation
																													// icons

		return "/forget.xhtml?faces-redirect=true";

	}

	public String forgetConfirmationRedirect() {

		user = new UserAccount(); // RESET

		RequestContext.getCurrentInstance().execute("$('#form-reset')[0].reset();"); // reset form
		RequestContext.getCurrentInstance()
				.execute("$('span[for=email]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');"); // Remove
																													// Validation
																													// icons

		return "/forget-confirmation.xhtml?faces-redirect=true";
	}

	public String loginRedirect() {

		user = new UserAccount(); // RESET

		RequestContext.getCurrentInstance().execute("$('#form-reset')[0].reset();"); // reset form
		RequestContext.getCurrentInstance()
				.execute("$('span[for=email]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');"); // Remove
																													// Validation
																													// icons

		return "/login.xhtml?faces-redirect=true";
	}

	public boolean permissionAdminOrSuper(int roleID) {

		if (roleID == 1 || roleID == 6)
			return true;

		else
			return false;
	}

	public String passwordRecovery() {

		String generatedPass = "";

		boolean response = false;

		GeneratePasswordUtil generate = new GeneratePasswordUtil();
		EncryptPasswordUtil encrypt = new EncryptPasswordUtil();	
		EmailUtil mail = new EmailUtil();
		EmailModels changeMail = new EmailModels();

		try {

			LoginAccountDAO dao = new LoginAccountDAO();
			RoadConcessionaireDAO road = new RoadConcessionaireDAO();
			UserAccount usr = new UserAccount();

			String roadConcessionaire = road.IdentifyRoadConcessionarie(addr.getHostAddress());

			usr = dao.emailValidation(user.getEmail(), roadConcessionaire);

			if (usr.getUsername() == null) {

				RequestContext.getCurrentInstance().execute("showEmailInfoMessage();");
				RequestContext.getCurrentInstance().execute("hideEmailInfoMessage();");

			} else {

				// Criar Senha
				generatedPass = generate.generatePassword();

				// Encriptar password para MD5
				usr.setPassword(encrypt.encryptPassword(generatedPass));

				// Gerar Assunto do Cadastro
				String assunto = changeMail.recoverySubject();

				// Gerar a mensagem de cadastro
				String mensagem = changeMail.recoveryMessage(usr.getName(), usr.getUsername(), generatedPass);

				// Alterar nova senha na base de dados
				response = dao.changePassword(usr.getUsername(), usr.getPassword(), usr.getUser_id(),
						roadConcessionaire);

				if (response) {

					mail.sendEmailHtml(user.getEmail(), assunto, mensagem);

					return forgetConfirmationRedirect();

				} else {

					RequestContext.getCurrentInstance().execute("showEmailRecoverySendErrorMessage();");
					RequestContext.getCurrentInstance().execute("hideEmailRecoverySendErrorMessage();");

				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	// Pull Credentials
	public void getCred() throws Exception {
		ModulesDAO mod = new ModulesDAO();
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		String name = context.getRequestParameterMap().get("serviceName");
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
					+ "\"}";
	}
}
