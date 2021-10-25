package br.com.tracevia.webapp.controller.global;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import br.com.tracevia.webapp.dao.global.UserAccountDAO;
import br.com.tracevia.webapp.methods.EmailModel;
import br.com.tracevia.webapp.model.global.UserAccount;
import br.com.tracevia.webapp.util.EmailUtil;
import br.com.tracevia.webapp.util.EncryptPasswordUtil;
import br.com.tracevia.webapp.util.GeneratePasswordUtil;
import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.LogUtils;
import br.com.tracevia.webapp.util.SessionUtil;

/**
 * Classe modelo para conta de usuário
 * @author Wellington 10/06/2020
 * @version 1.0
 * @since 1.0
 */

@ManagedBean(name="userAccount")
@SessionScoped
public class UserAccountBean implements Serializable {
	
		private static final long serialVersionUID = 1L;		
		
		// --------------------------------------------------------------------------------------------
		
		// CLASS PATH
			
		private static String classLocation = UserAccountBean.class.getCanonicalName();
			
		// --------------------------------------------------------------------------------------------
			
		// CLASS LOG FOLDER
			
		private static String classErrorPath = LogUtils.ERROR.concat("users\\");
			
		// --------------------------------------------------------------------------------------------
			
		// EXCEPTION FILENAMES
			
		private static String registerExceptionLog = classErrorPath.concat("register_ex_");
		private static String updateInfoExceptionLog = classErrorPath.concat("update_info_ex_");
		private static String listExceptionLog = classErrorPath.concat("list_ex_");
		private static String updateExceptionLog = classErrorPath.concat("update_user_ex");
		private static String deleteExceptionLog = classErrorPath.concat("delete_ex_");
		private static String changePasswordExceptionLog = classErrorPath.concat("sql_user_validate_");
							
		// --------------------------------------------------------------------------------------------	
		
		private UserAccount user;
		
		LocaleUtil localeEmail, localeUsers;					
		
		String lastUsername, lastEmail; // Aux to user for check
		
		UserAccountDAO dao;
		
		DataModel<UserAccount> userDataModel;
	
		private ArrayList<UserAccount> usuarios;
		private ArrayList<UserAccount> usersList;	
									
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
			 					 		 			
		public ArrayList<UserAccount> getUsuarios() {
				return usuarios;
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
		
		// --------------------------------------------------------------------------------------------	
		
		/**
		 * Método para cadastrar um usuário
		 * @author Wellington 10/06/2020
		 * @version 1.0
		 * @since 1.0
		 */
		public void cadastroUsuario() {
								
			String generatedPass = "";		
			boolean response = false, email = false, usernameCheck = false, emailCheck = false;
																			
			try {
									
				dao = new UserAccountDAO();
				
				EmailModel cadMail = new EmailModel();
				
				if(user.getName() != "" && user.getJob_position() != "" &&
						user.getEmail() != "" && user.getUsername() != "" && user.getPermission_id() != 0) {
				
				//Criar Senha
				generatedPass = GeneratePasswordUtil.generatePassword();
				
				//System.out.println(generatedPass);
				
				//Encriptar password para MD5
				user.setPassword(EncryptPasswordUtil.encryptPassword(generatedPass));
				
				//Salvar usu�rio da sess�o que criou usu�rio
				user.setCreatedBy((String) SessionUtil.getParam("user"));
				
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
        					
        					email = EmailUtil.sendEmailHtml(user.getEmail(), assunto , mensagem); //Método para enviar email
        						
        					if(email) {	
        						        
        						  SessionUtil.executeScript("hideInfoMessage();");
        						  SessionUtil.executeScript("showSuccessMessage();");	
        						  SessionUtil.executeScript("hideSuccessMessage();");	
        						  SessionUtil.executeScript("$('#success #mail').html('"+user.getEmail()+"'); ");
        						  
        						  SessionUtil.executeScript("$('#form-register')[0].reset();"); // reset form
        						          		
        						  //Remove Validation icons
        						  SessionUtil.executeScript("$('span[for=fullname]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');");
        						  SessionUtil.executeScript("$('span[for=jobPosition]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');");
        						  SessionUtil.executeScript("$('span[for=email]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');");
        						  SessionUtil.executeScript("$('span[for=username]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');");
        						  SessionUtil.executeScript("$('span[for=permissions]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');");
        						        	        						        						  
        						  clearFields(); // limpar objeto user  
        						  
        						  assunto = ""; mensagem = "";
        						 
        					}	    					
        					      					
        					
        				} else {
        					
        					  SessionUtil.executeScript("hideInfoMessage();");
        					  SessionUtil.executeScript("showErrorMessage();");	
        					  SessionUtil.executeScript("hideErrorMessage();");	
        				}        				               		
                		
                	} else {
                		
                		  SessionUtil.executeScript("hideInfoMessage();");
                		  SessionUtil.executeScript("showUsernameErrorMessage();");	
    					  SessionUtil.executeScript("hideUsernameErrorMessage();");	
                   }
                }else {
                	
                	  SessionUtil.executeScript("hideInfoMessage();");
                	  SessionUtil.executeScript("showEmailErrorMessage();");	
					  SessionUtil.executeScript("hideEmailErrorMessage();");	
                }						     
            
				 listarUsuarios(); //listar após cadastrar


		    } // caso contrario não faz nada

			}catch(Exception ex) {
				
				  StringWriter errors = new StringWriter(); 
				  ex.printStackTrace(new PrintWriter(errors));	

				  LogUtils.logError(LogUtils.fileDateTimeFormatter(registerExceptionLog),  classLocation, ex.getMessage(), errors.toString());
			
								
				  SessionUtil.executeScript("hideInfoMessage();");
				  SessionUtil.executeScript("showErrorMessage();");	
				  SessionUtil.executeScript("hideErrorMessage();");	
				
			}		
		}	
		
		// --------------------------------------------------------------------------------------------	

		/**
		 * Método para listar usuários
		 * @author Wellington 10/06/2020
		 * @version 1.0
		 * @since 1.0 
		 */
		public void listarUsuarios() {
		
			try {
				
				dao = new UserAccountDAO();
				user = new UserAccount();	
							
			    usersList = new ArrayList<UserAccount>();

				usersList = dao.BuscarUsuarios();
							
			    userDataModel = new ListDataModel<UserAccount>(usersList);
				
			
			}catch(Exception ex) {
				
				StringWriter errors = new StringWriter(); 
				ex.printStackTrace(new PrintWriter(errors));	

				LogUtils.logError(LogUtils.fileDateTimeFormatter(listExceptionLog),  classLocation, ex.getMessage(), errors.toString());
			
			}		
		}
		
		// --------------------------------------------------------------------------------------------	
		
         /**
          * Método para obter informações de um usuário para atualizar
          * @author Wellington 10/06/2020
		  * @version 1.0
		  * @since 1.0
          * @return para página de atualizaçaõ de um usuário
          * @throws Exception
          */
		public String usuarioInfoToUp() {

			String parametro = user.getSendParametro();			
											
			try {				
			
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
				
				}catch(Exception ex) {
					
					StringWriter errors = new StringWriter(); 
					ex.printStackTrace(new PrintWriter(errors));	

					LogUtils.logError(LogUtils.fileDateTimeFormatter(updateInfoExceptionLog),  classLocation, ex.getMessage(), errors.toString());
					
				}					
										
			return null;
		}
			
		// --------------------------------------------------------------------------------------------	
			
		/**
		 * Método para excluir um usuário
		 * @author Wellington 10/06/2020
		 * @version 1.0
		 * @since 1.0
		 */
		public void excluirUsuario() {

			boolean response = false;
		
			try {
				
				dao = new UserAccountDAO();
				user = new UserAccount();
				
				response = dao.deletarRegistro(user.getSendParametro());
								
				if(response) {
										
					dao = new UserAccountDAO();
						              
					usersList = new ArrayList<UserAccount>();									
					userDataModel = new ListDataModel<UserAccount>();
					
					  SessionUtil.executeScript("showSuccessMessage();");	
					  SessionUtil.executeScript("hideSuccessMessage();");	
										
				} 
				
			}catch(Exception ex) {
				
				 StringWriter errors = new StringWriter(); 
				 ex.printStackTrace(new PrintWriter(errors));	

				 LogUtils.logError(LogUtils.fileDateTimeFormatter(deleteExceptionLog),  classLocation, ex.getMessage(), errors.toString());
				
				 SessionUtil.executeScript("showErrorMessage();");	
				 SessionUtil.executeScript("hideErrorMessage();");	
				
			}	
			
			listarUsuarios(); // listar usuários
		}
		
		// --------------------------------------------------------------------------------------------	
		
          /**
           * Método para atualizar um usuário
           * @author Wellington 10/06/2020
		   * @version 1.0
		   * @since 1.0
           */
          public void atualizarCadastro() {
								
			boolean response = false, emailCheck = false, usernameCheck = false;
							
			try {

				dao = new UserAccountDAO();						
				
				 // USER ID -> Needs to access this method				
				 user.setUserID(SessionUtil.getParametersValue("myUserId"));
				 								 										
				   emailCheck = dao.checkEmail(user.getEmail());
				   								                
	                if(emailCheck && (user.getEmail().equals(lastEmail)) || !emailCheck) {	                	
	            	                	
	                	usernameCheck = dao.checkUsername(user.getUsername());
	                		                		                	
	                	if(usernameCheck && (user.getUsername().equals(lastUsername)) || !usernameCheck) {
	                		             		                		
	                		response = dao.atualizar(user);	
	            			
	        				if(response) {
	        					
	        					  SessionUtil.executeScript("showSuccessMessage();");	
	        					  SessionUtil.executeScript("hideSuccessMessage();");	
	        					 
	        					  SessionUtil.executeScript("redirecToSearch();");	  //redirect to table				  
	        					  				
	        				} 	        			
	                		
	                	} else {
                		
              		  SessionUtil.executeScript("showUsernameErrorMessage();");	
  					  SessionUtil.executeScript("hideUsernameErrorMessage();");  					   
  					  
                 }
	                	
              }else {
              	
              	  SessionUtil.executeScript("showEmailErrorMessage();");	
				  SessionUtil.executeScript("hideEmailErrorMessage();");	
				  
			   }	           								
				
			}catch(Exception ex) {
				
				  StringWriter errors = new StringWriter(); 
				  ex.printStackTrace(new PrintWriter(errors));	

				  LogUtils.logError(LogUtils.fileDateTimeFormatter(updateExceptionLog),  classLocation, ex.getMessage(), errors.toString());
					
				  SessionUtil.executeScript("showErrorMessage();");	
				  SessionUtil.executeScript("hideErrorMessage();");	
			}	
			
			listarUsuarios(); // listar usuários
		}
          
      	// --------------------------------------------------------------------------------------------	
          
      	/**
  		 * Método para redefinir a senha de usuário
  		 * @author Wellington 10/06/2020
  		 * @version 1.0
  		 * @since 1.0
  		 */
  		public void changePassword() {
  						
  			boolean response = false;		
  	
  			String usuario = (String) SessionUtil.getParam("user");				
  		
  			String password = EncryptPasswordUtil.encryptPassword(user.getPassword());
  			String newPassword = EncryptPasswordUtil.encryptPassword(user.getNewPassword());
  			String confPassword = EncryptPasswordUtil.encryptPassword(user.getConfPassword());

  			try {

  				UserAccountDAO dao = new UserAccountDAO();	
  				
  				if(!password.equals(newPassword)) {
  				
  				 if(newPassword.equals(confPassword)) {				

  						response  = dao.changePassword(usuario, newPassword );

  						if(response) {		
  							
  							  SessionUtil.executeScript("showSuccessMessage();");
  							  SessionUtil.executeScript("hideSuccessMessage();");
  							 
  							  SessionUtil.executeScript("$('#change-password-form')[0].reset();"); // reset form
  				          		
      						  //Remove Validation icons
      						  SessionUtil.executeScript("$('span[for=password]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');");
      						  SessionUtil.executeScript("$('span[for=newPassword]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');");
      						  SessionUtil.executeScript("$('span[for=confPassword]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');");
      						 						
  							  resetChangePassword(); // Reset Password		

  				      }						
  				} // ELSE NOT NECESSARY BECAUSE JQUERY VALIDATOR DO IT
  				 
  			}else {
  				
  				 SessionUtil.executeScript("showChangeErrorMessage();");
  				 SessionUtil.executeScript("hideChangeErrorMessage();");
  			}				

  			}catch(Exception ex) {
  				
  				StringWriter errors = new StringWriter(); 
  				ex.printStackTrace(new PrintWriter(errors));	

  				LogUtils.logError(LogUtils.fileDateTimeFormatter(changePasswordExceptionLog),  classLocation, ex.getMessage(), errors.toString());
  							
  				SessionUtil.executeScript("showErrorMessage();");
  				SessionUtil.executeScript("hideErrorMessage();");
  				
  			}			
  		}
  		
  		// --------------------------------------------------------------------------------------------	
  		
 				
		/**
		 * Método para redirecionar a página de painel de usuário
		 * @author Wellington 10/06/2020
		 * @version 1.0
		 * @since 1.0
	     * @return url da página do painel de usuários
		 */
           public String redirectToPanel() {
			
			user = new UserAccount();
			
			listarUsuarios(); // listar usuários
						 			  
			return "/users/panel_user.xhtml?faces-redirect=true";
			
		}
           
       	// --------------------------------------------------------------------------------------------	
           
        /**
   		 * Método para redirecionar a página de registro de usuário
   		 * @author Wellington 10/06/2020
   		 * @version 1.0
   		 * @since 1.0
   	     * @return url da página de registro de usuário
   		 */
		public String newUser() {
			  
			return  "/users/register_user.xhtml?faces-redirect=true";	
			
		}
		
		// --------------------------------------------------------------------------------------------	
			
		/**
		 * Método para redefinir o objeto user
		 * @author Wellington 10/06/2020
		 * @version 1.0
		 * @since 1.0	
		 */
		public void clearFields(){
			
			user = new UserAccount();			
			
		}
		
		// --------------------------------------------------------------------------------------------					
			
		/**
		 * Método para obter o valor de uma linha da tabela
		 * @author Wellington 10/06/2020
		 * @version 1.0
		 * @since 1.0 
		 */
		 public void getRowValue() {
			 
		     UserAccount user = new UserAccount();
						 
			 user = userDataModel.getRowData(); // There it is.
			 
			 user.setRowkey(String.valueOf(user.getUser_id()));
			 			 
			 SessionUtil.updateElement("dialog-form:modal-text");   
			 			 
		 }
		
		// ------------------------------------------------------------------------------------------------------- // 
		
		/**
		 * Método para resetar objeto do usuário
		 * @author Wellington 10/06/2020
		 * @version 1.0
		 * @since 1.0  
		 */
        public void resetChangePassword(){
			
		  user = new UserAccount();
			
		}
        
     // ------------------------------------------------------------------------------------------------------- // 
        
        /**
         * Método para obter a permissão de um usuário
         * @author Wellington 10/06/2020
		 * @version 1.0
		 * @since 1.0  
         * @return código do nível de acesso
         */
        public int userPermision() {        	        
        	
        	return (int) SessionUtil.getParam("nivel");
        }
        
     // ------------------------------------------------------------------------------------------------------- // 

}
