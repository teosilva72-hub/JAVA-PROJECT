package br.com.tracevia.webapp.controller.global;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.primefaces.context.RequestContext;

import br.com.tracevia.webapp.dao.global.UserAccountDAO;
import br.com.tracevia.webapp.methods.EmailModels;
import br.com.tracevia.webapp.model.global.UserAccount;
import br.com.tracevia.webapp.util.EmailUtil;
import br.com.tracevia.webapp.util.EncryptPasswordUtil;
import br.com.tracevia.webapp.util.GeneratePasswordUtil;
import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.MessagesUtil;


@ManagedBean(name="userAccount")
@SessionScoped
public class UserAccountBean implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private boolean selectAll;
		private String parametro;
		private String sendParametro;		
		private UserAccount user;	
		private String user_id;
		private String date_register;
		private String name;
		private String job_position;
		private String email;
		private String username;
		private String role_permission;
		private boolean status;
		private HtmlDataTable dataTable;
		private String rowkey;
		
		DataModel<UserAccount> userDataModel;
	
		private ArrayList<UserAccount> usersList;
		
		UserAccountDAO dao;

		private ArrayList<UserAccount> usuarios;
		
		LocaleUtil localeEmail, localeUsers;
		
				
		public DataModel<UserAccount> getUserDataModel() {
			return userDataModel;
		}

		public void setUserDataModel(DataModel<UserAccount> userDataModel) {
			this.userDataModel = userDataModel;
		}

		public UserAccount getUser() {
			return user;
		}
		
		public void setUser(UserAccount user) {
			this.user = user;
		}
						
		public ArrayList<UserAccount> getUsersList() {
			return usersList;
		}
		
		public HtmlDataTable getDataTable() {
			return dataTable;
		}

		public void setDataTable(HtmlDataTable dataTable) {
			this.dataTable = dataTable;
		}
		
		public String getRowkey() {
			return rowkey;
		}

		public void setRowkey(String rowkey) {
			this.rowkey = rowkey;
		}

		@PostConstruct
		public void initialize() {
			
			user = new UserAccount();
			localeEmail = new LocaleUtil();
			localeEmail.getResourceBundle(LocaleUtil.MESSAGES_EMAIL);
			
			localeUsers = new LocaleUtil();
			localeUsers.getResourceBundle(LocaleUtil.MESSAGES_USERS);
									
		}
		
		public void cadastroUsuario() {

			String generatedPass = "";		
			boolean response = false;
			MessagesUtil message = new MessagesUtil();
			
			//Get external application contents
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
									
			try {
									
				dao = new UserAccountDAO();
				GeneratePasswordUtil generate = new GeneratePasswordUtil();
				EncryptPasswordUtil encrypt = new EncryptPasswordUtil();
				EmailUtil mail = new EmailUtil();
				EmailModels cadMail = new EmailModels();
				
				if(user.getName() != "" && user.getJob_position() != "" &&
						user.getEmail() != "" && user.getUsername() != "" && user.getPermission_id() != 0) {
				
				//Criar Senha
				generatedPass = generate.generatePassword();
				
				//System.out.println(generatedPass);
				
				//Encriptar password para MD5
				user.setPassword(encrypt.encryptPassword(generatedPass));
				
				//Salvar usuário da sessão que criou usuário
				user.setCreatedBy((String) facesContext.getExternalContext().getSessionMap().get("user"));
				
				//Gerar Assunto do Cadastro
				String assunto = cadMail.createRegisterSubject();
				
				//Gerar a mensagem de cadastro
				String mensagem = cadMail.createRegisterMessage(user.getName(), user.getUsername(), generatedPass);
								
				response = dao.cadastroUsuario(user);
								
				if(response) {
					
					mail.sendEmail(user.getEmail(), assunto , mensagem); //Método para enviar email
										
					message.InfoMessage(localeEmail.getStringKey("email_user_register_sucess"), localeEmail.getStringKey("email_user_register_sucess_confirmation")+": "+user.getEmail());
					clearFields(); // 
				} else message.ErrorMessage(localeEmail.getStringKey("users_register_failed_register_header"), localeEmail.getStringKey("users_register_failed_register_body"));


		    } // caso contrario não faz nada

			}catch(Exception ex) {

				ex.printStackTrace();
			}		
		}		

		//Criar metodos daqui
		public void listarUsuarios() {

			String parametro;

			if(isSelectAll()) //O checkbox selecionado
				parametro = "Todos";
			
			else parametro = getParametro();
												
			try {
				
				dao = new UserAccountDAO();
				user = new UserAccount();	
				MessagesUtil message = new MessagesUtil();
					
			    usersList = new ArrayList<UserAccount>();

				usersList = dao.BuscarUsuarios(parametro);
				
				if(usersList.isEmpty())
					message.InfoMessage(localeUsers.getStringKey("users_list_records_not_found"), " ");
				
				else userDataModel = new ListDataModel<UserAccount>(usersList);
				
				//Limpar campos
				cleanPanelSelection();

			}catch(Exception ex) {
				ex.printStackTrace();
			}		
		}
		
		public String usuarioInfoToUp() throws Exception {

			String parametro = getSendParametro();
								
				dao = new UserAccountDAO();
				user = new UserAccount();	
				
				user = dao.procurarUsuarioByID(parametro);
				
				if(user != null) {
					
				user.getUserID();				
				user.getName();
				user.getJob_position();
				user.getEmail();
				user.getUsername();
				user.getPermission_id();
				user.isActiveStatus();
								
				clearSearchDataTable();				
				
				return "update_user.xhtml";
			
				}
							
			return null;
		}
		
		
		public void clearSearchDataTable() throws Exception {
			
			try {
			
			if (userDataModel.isRowAvailable())		{			   
			     userDataModel = new ListDataModel<UserAccount>();	// Clean DataModel		
			     usersList = new ArrayList<UserAccount>();	
			     parametro = null;
			}
			else {  userDataModel = new ListDataModel<UserAccount>(); parametro = null; }	// Clean DataModel
			
			}catch(Exception ex){}
		}
		
		public void excluirUsuario() {

			boolean response = false;
			
			MessagesUtil message = new MessagesUtil();

			try {
				
				dao = new UserAccountDAO();
				user = new UserAccount();
				
				response = dao.deletarRegistro(getSendParametro());
								
				if(response) {
										
					dao = new UserAccountDAO();
						              
					usersList = new ArrayList<UserAccount>();									
					userDataModel = new ListDataModel<UserAccount>();
										
				} else message.ErrorMessage(localeUsers.getStringKey("users_delete_failed_delete_header"), localeUsers.getStringKey("users_delete_failed_delete_body"));
						

			}catch(Exception ex) {
				ex.printStackTrace();				
			}		
		}
		
		public void atualizarCadastro() {
			
			boolean response = false;
			
			MessagesUtil message = new MessagesUtil();

			try {

				dao = new UserAccountDAO();
								
				response = dao.atualizar(user);	
			
				if(response) {
					
					 message.InfoMessage(localeUsers.getStringKey("users_update_success_update"), " " );
					 user = new UserAccount(); //resetar objeto
					 clearFields();
				} 
			
			}catch(Exception ex) {
				ex.printStackTrace();
				
				message.ErrorMessage(localeUsers.getStringKey("users_update_failed_update_header"), localeUsers.getStringKey("users_update_failed_update_body") );	

			}		
		}
				
		public String cancelUpdate() {
			
			  clearFields();
			  user = new UserAccount(); //Resetar usuário
			  
			return "panel_user.xhtml?faces-redirect=true";
			
		}
			
		public void clearFields(){
			
			user = new UserAccount();
			
			setUser_id(null);
			setDate_register(null);
			setName(null);	  
			setJob_position(null);
			setEmail(null);
			setUsername(null);
			setRole_permission(null);
			setStatus(false);
		}
				
		public void changePassword() {
						
			boolean response = false;
			
			MessagesUtil message = new MessagesUtil();

			FacesContext context = FacesContext.getCurrentInstance();			

			String usuario = (String) context.getExternalContext().getSessionMap().get("user");
			
			EncryptPasswordUtil encrypt = new EncryptPasswordUtil();

			String newPassword = encrypt.encryptPassword(user.getNewPassword());
			String confPassword = encrypt.encryptPassword(user.getConfPassword());

			try {

				UserAccountDAO dao = new UserAccountDAO();				
				
				 if(newPassword.equals(confPassword)) {				

						response  = dao.changePassword(usuario, newPassword );

						if(response) {				
							message.InfoMessage(localeUsers.getStringKey("users_change_password_success"), " " );
							resetChangePassword();						

				 }else message.ErrorMessage(localeUsers.getStringKey("users_change_password_failed_header"), localeUsers.getStringKey("users_change_password_failed_body"));

				}else  message.ErrorMessage(localeUsers.getStringKey("users_change_password_confirm_header"), localeUsers.getStringKey("users_change_password_confirm_body"));

			}catch(Exception ex) {
				ex.printStackTrace();
			}			
		}
				
		 public void getRowValue() {
			 
		     UserAccount user = new UserAccount();
						 
			  user = userDataModel.getRowData(); // There it is.
			 
			 rowkey = String.valueOf(user.getUser_id());
			 			 
			 RequestContext.getCurrentInstance().update("dialog-form:modal-text");   
			 			 
		 }
		 
		 
		/*Limpar a Seleção do Panel Users*/
		public void cleanPanelSelection() {
			
			setParametro("");
			setSelectAll(false);
			
		}
		
		public boolean isSelectAll() {
			return selectAll;
		}

		public void setSelectAll(boolean selectAll) {
			this.selectAll = selectAll;
		}

		public String getParametro() {
			return parametro;
		}

		public void setParametro(String parametro) {
			this.parametro = parametro;
		}

		public ArrayList<UserAccount> getUsuarios() {
			return usuarios;
		}

		public String getSendParametro() {
			return sendParametro;
		}

		public void setSendParametro(String sendParametro) {
			this.sendParametro = sendParametro;
		}

		public boolean getStatus() {
			return status;
		}

		public void setStatus(boolean b) {
			this.status = b;
		}

		public String getRole_permission() {
			return role_permission;
		}

		public void setRole_permission(String role_permission) {
			this.role_permission = role_permission;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getJob_position() {
			return job_position;
		}

		public void setJob_position(String job_position) {
			this.job_position = job_position;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDate_register() {
			return date_register;
		}

		public void setDate_register(String date_register) {
			this.date_register = date_register;
		}

		public String getUser_id() {
			return user_id;
		}

		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}
		
		//RESET METHODS
        public void resetChangePassword(){
			
		  user = new UserAccount();
			
		}

}
