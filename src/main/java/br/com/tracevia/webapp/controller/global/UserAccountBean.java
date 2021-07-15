package br.com.tracevia.webapp.controller.global;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

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
		private int role_permission;
		private boolean status;
		private HtmlDataTable dataTable;
		private String rowkey;
		
		DataModel<UserAccount> userDataModel;
	
		private ArrayList<UserAccount> usersList;
		
		UserAccountDAO dao;

		private ArrayList<UserAccount> usuarios;
		
		LocaleUtil localeEmail, localeUsers;
		
		String lastUsername, lastEmail; // Aux to user for check		
				
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
			
			listarUsuarios(); // listar usuários ao inicializar	
			userPermision();
									
		}
		
		public void cadastroUsuario() {
								
			String generatedPass = "";		
			boolean response = false, email = false, usernameCheck = false, emailCheck = false;
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
				
				//Salvar usu�rio da sess�o que criou usu�rio
				user.setCreatedBy((String) facesContext.getExternalContext().getSessionMap().get("user"));
				
				//Gerar Assunto do Cadastro
				String assunto = cadMail.registerSubject();
				
				//Gerar a mensagem de cadastro
				String mensagem = cadMail.registerMessage(user.getName(), user.getUsername(), generatedPass);
												
                emailCheck = dao.checkEmail(user.getEmail());
                
                if(!emailCheck) {
                	                	                	
                	usernameCheck = dao.checkUsername(user.getUsername());
                	
                	if(!usernameCheck) {
                		                	                		                                		
                		response = dao.cadastroUsuario(user);                		              	
						
        				if(response) {        					
        					
        					email = mail.sendEmailHtml(user.getEmail(), assunto , mensagem); //Método para enviar email
        						
        					if(email) {	
        						        
        						  RequestContext.getCurrentInstance().execute("hideInfoMessage();");
        						  RequestContext.getCurrentInstance().execute("showSuccessMessage();");	
        						  RequestContext.getCurrentInstance().execute("hideSuccessMessage();");	
        						  RequestContext.getCurrentInstance().execute("$('#success #mail').html('"+user.getEmail()+"'); ");
        						  
        						  RequestContext.getCurrentInstance().execute("$('#form-register')[0].reset();"); // reset form
        						          		
        						  //Remove Validation icons
        						  RequestContext.getCurrentInstance().execute("$('span[for=fullname]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');");
        						  RequestContext.getCurrentInstance().execute("$('span[for=jobPosition]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');");
        						  RequestContext.getCurrentInstance().execute("$('span[for=email]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');");
        						  RequestContext.getCurrentInstance().execute("$('span[for=username]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');");
        						  RequestContext.getCurrentInstance().execute("$('span[for=permissions]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');");
        						        	        						        						  
        						  clearFields(); // limpar objeto user  
        						  
        						  assunto = ""; mensagem = "";
        						 
        					}	    					
        					      					
        					
        				} else {
        					
        					  RequestContext.getCurrentInstance().execute("hideInfoMessage();");
        					  RequestContext.getCurrentInstance().execute("showErrorMessage();");	
        					  RequestContext.getCurrentInstance().execute("hideErrorMessage();");	
        				}        				               		
                		
                	} else {
                		
                		  RequestContext.getCurrentInstance().execute("hideInfoMessage();");
                		  RequestContext.getCurrentInstance().execute("showUsernameErrorMessage();");	
    					  RequestContext.getCurrentInstance().execute("hideUsernameErrorMessage();");	
                   }
                }else {
                	
                	  RequestContext.getCurrentInstance().execute("hideInfoMessage();");
                	  RequestContext.getCurrentInstance().execute("showEmailErrorMessage();");	
					  RequestContext.getCurrentInstance().execute("hideEmailErrorMessage();");	
                }						     
            
				 listarUsuarios(); //listar após cadastrar


		    } // caso contrario não faz nada

			}catch(Exception ex) {
				
				ex.printStackTrace();

				  RequestContext.getCurrentInstance().execute("hideInfoMessage();");
				  RequestContext.getCurrentInstance().execute("showErrorMessage();");	
				  RequestContext.getCurrentInstance().execute("hideErrorMessage();");	
				
			}		
		}		

		//Criar metodos daqui
		public void listarUsuarios() {
		
			try {
				
				dao = new UserAccountDAO();
				user = new UserAccount();	
							
			    usersList = new ArrayList<UserAccount>();

				usersList = dao.BuscarUsuarios();
							
			    userDataModel = new ListDataModel<UserAccount>(usersList);
				
			
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
				lastUsername = user.getUsername();
				lastEmail = user.getEmail();
							
				return "/users/update_user.xhtml?faces-redirect=true";
			
				}
							
			return null;
		}
				
			
		public void excluirUsuario() {

			boolean response = false;
		
			try {
				
				dao = new UserAccountDAO();
				user = new UserAccount();
				
				response = dao.deletarRegistro(getSendParametro());
								
				if(response) {
										
					dao = new UserAccountDAO();
						              
					usersList = new ArrayList<UserAccount>();									
					userDataModel = new ListDataModel<UserAccount>();
					
					  RequestContext.getCurrentInstance().execute("showSuccessMessage();");	
					  RequestContext.getCurrentInstance().execute("hideSuccessMessage();");	
										
				} 
				
			}catch(Exception ex) {
				ex.printStackTrace();
				
				 RequestContext.getCurrentInstance().execute("showErrorMessage();");	
				 RequestContext.getCurrentInstance().execute("hideErrorMessage();");	
				
			}	
			
			listarUsuarios(); // listar usuários
		}
		
          public void atualizarCadastro() {
								
			boolean response = false, emailCheck = false, usernameCheck = false;
			
			//Get external application contents
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
		
			try {

				dao = new UserAccountDAO();						
				
				  // USER ID -> Needs to access this method				
				 user.setUserID(externalContext.getRequestParameterMap().get("myUserId"));
				 										
				   emailCheck = dao.checkEmailToUpdate(user.getEmail(), lastEmail);
				                
	                if(!emailCheck) {	                	
	            	                	
	                	usernameCheck = dao.checkUsernameToUpdate(user.getUsername(), lastUsername);
	                		                	
	                	if(!usernameCheck) {
	                		             		                		
	                		response = dao.atualizar(user);	
	            			
	        				if(response) {
	        					
	        					  RequestContext.getCurrentInstance().execute("showSuccessMessage();");	
	        					  RequestContext.getCurrentInstance().execute("hideSuccessMessage();");	
	        					 
	        					  RequestContext.getCurrentInstance().execute("redirecToSearch();");	  //redirect to table					  
	        					  				
	        				} 
	        			
	                		
	                	} else {
                		
              		  RequestContext.getCurrentInstance().execute("showUsernameErrorMessage();");	
  					  RequestContext.getCurrentInstance().execute("hideUsernameErrorMessage();");  					   
  					  
                 }
	                	
              }else {
              	
              	  RequestContext.getCurrentInstance().execute("showEmailErrorMessage();");	
				  RequestContext.getCurrentInstance().execute("hideEmailErrorMessage();");	
				  
			   }	           								
				
			}catch(Exception ex) {
				ex.printStackTrace();				
					
				  RequestContext.getCurrentInstance().execute("showErrorMessage();");	
				  RequestContext.getCurrentInstance().execute("hideErrorMessage();");	
			}	
			
			listarUsuarios(); // listar usuários
		}

				
		public String cancelUpdate() {
			
			user = new UserAccount();
			
			listarUsuarios(); // listar usuários
						 			  
			return "/users/panel_user.xhtml?faces-redirect=true";
			
		}
		
           public String backPanel() {
			
			user = new UserAccount();
			
			listarUsuarios(); // listar usuários
						 			  
			return "/users/panel_user.xhtml?faces-redirect=true";
			
		}
		
		public String newUser() {
			  
			return  "/users/register_user.xhtml?faces-redirect=true";	
			
		}
			
		public void clearFields(){
			
			user = new UserAccount();			
			
		}
		
						
		public void changePassword() {
						
			boolean response = false;
			
			MessagesUtil message = new MessagesUtil();

			FacesContext context = FacesContext.getCurrentInstance();			

			String usuario = (String) context.getExternalContext().getSessionMap().get("user");
					
			EncryptPasswordUtil encrypt = new EncryptPasswordUtil();

			String password = encrypt.encryptPassword(user.getPassword());
			String newPassword = encrypt.encryptPassword(user.getNewPassword());
			String confPassword = encrypt.encryptPassword(user.getConfPassword());

			try {

				UserAccountDAO dao = new UserAccountDAO();	
				
				if(!password.equals(newPassword)) {
				
				 if(newPassword.equals(confPassword)) {				

						response  = dao.changePassword(usuario, newPassword );

						if(response) {		
							
							 RequestContext.getCurrentInstance().execute("showSuccessMessage();");
							 RequestContext.getCurrentInstance().execute("hideSuccessMessage();");
							 
							  RequestContext.getCurrentInstance().execute("$('#change-password-form')[0].reset();"); // reset form
				          		
    						  //Remove Validation icons
    						  RequestContext.getCurrentInstance().execute("$('span[for=password]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');");
    						  RequestContext.getCurrentInstance().execute("$('span[for=newPassword]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');");
    						  RequestContext.getCurrentInstance().execute("$('span[for=confPassword]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');");
    						 						
							  resetChangePassword(); // Reset Password		

				      }						
				} // ELSE NOT NECESSARY BECAUSE JQUERY VALIDATOR DO IT
				 
			}else {
				
				 RequestContext.getCurrentInstance().execute("showChangeErrorMessage();");
				 RequestContext.getCurrentInstance().execute("hideChangeErrorMessage();");
			}
				

			}catch(Exception ex) {
				ex.printStackTrace();
				
				 RequestContext.getCurrentInstance().execute("showErrorMessage();");
				 RequestContext.getCurrentInstance().execute("hideErrorMessage();");
			}			
		}
				
		 public void getRowValue() {
			 
		     UserAccount user = new UserAccount();
						 
			  user = userDataModel.getRowData(); // There it is.
			 
			 rowkey = String.valueOf(user.getUser_id());
			 			 
			 RequestContext.getCurrentInstance().update("dialog-form:modal-text");   
			 			 
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

		public int getRole_permission() {
			return role_permission;
		}

		public void setRole_permission(int role_permission) {
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
        
        public int userPermision() {
        	FacesContext context = FacesContext.getCurrentInstance();
        	int role = (int) context.getExternalContext().getSessionMap().get("nivel");
        	
        	return role;
        }

}
