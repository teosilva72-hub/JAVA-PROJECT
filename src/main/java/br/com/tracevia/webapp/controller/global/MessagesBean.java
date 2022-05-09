package br.com.tracevia.webapp.controller.global;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="messages")
@ViewScoped

public class MessagesBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	 FacesMessage facesMsg;
	 Locale locale;
	 ResourceBundle resourceBundle;
	 
	@ManagedProperty(value = "#{language}")
	private LanguageBean lang;				
			         
	public LanguageBean getLang() {
		return lang;
	}

	public void setLang(LanguageBean lang) {
		this.lang = lang;
	}
	
	//Caso n�o encontre nenhum registro	
	public void nenhumRegistro() {		
		
		lang = new LanguageBean();
	    locale = lang.getLocale();
		resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
		
	    facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, resourceBundle.getString("no_vehicle_found") , "");
		    FacesContext.getCurrentInstance().addMessage(null, facesMsg);		    
    }	
	
	   //Mensagens de restri��es
	
	    //DataTable Vazia
		public void dataTableIsEmpty() {	
			
			lang = new LanguageBean();
		    locale = lang.getLocale();
			resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
			
		    facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("no_data_to_export") , " - "+resourceBundle.getString("no_data_to_export_cmp"));
			    FacesContext.getCurrentInstance().addMessage(null, facesMsg);		    
	    }	
		
		//Required Data
		
		 //DataTable Vazia
		public void inputDataInicioIsEmpty() {	
			
			lang = new LanguageBean();
		    locale = lang.getLocale();
			resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
			
		    facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("no_date_found") , " - "+resourceBundle.getString("no_init_date_found_cmp"));
			    FacesContext.getCurrentInstance().addMessage(null, facesMsg);		    
	    }		
		
		public void inputDataFimIsEmpty() {		
			
			lang = new LanguageBean();
		    locale = lang.getLocale();
			resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
			
		    facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("no_date_found") , " - "+resourceBundle.getString("no_final_date_found_cmp"));
			    FacesContext.getCurrentInstance().addMessage(null, facesMsg);		    
	    }
		
		//Data Converter
		public void inputDataConverter() {
			
			lang = new LanguageBean();
		    locale = lang.getLocale();
			resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
			
					facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("invalid_date")+": ", resourceBundle.getString("invalid_date_cmp"));
				    FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
		}
		
		//Data validator		
				public void inputDataValidator() {
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
										
						facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("inferior_date")+": ", resourceBundle.getString("inferior_date_cmp"));
					    FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
				}
			
		
		//Required Equipamento		
		public void equipamentoIsEmpty() {
			
			lang = new LanguageBean();
		    locale = lang.getLocale();
			resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
			
			facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("no_equipament_selected")+": ", resourceBundle.getString("no_equipament_selected_cmp"));
		    FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
         }
		
		        //Required Eixos	
				public void eixosIsEmpty() {
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
					facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("no_axle_selected")+": ", resourceBundle.getString("no_axle_selected_cmp"));
				    FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
		         }
				
				//Required Ve�culos	
				public void veiculosIsEmpty() {
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
					facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("no_vehicle_selected")+": ", resourceBundle.getString("no_vehicle_selected_cmp"));
				    FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
		         }
				
				//Required Ve�culos	
				public void sentidosIsEmpty() {
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
										
					facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("no_direction_selected")+": ", resourceBundle.getString("no_direction_selected_cmp"));
				    FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
		         }
				
				//Required Month	
				public void monthIsEmpty() {
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
					facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("no_month_selected")+":", resourceBundle.getString("no_month_selected_cmp"));
				    FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
		         }
				
				//Required year
				
				   public void yearIsEmpty() {
					   
						lang = new LanguageBean();
					    locale = lang.getLocale();
						resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
					   facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("no_year_selected")+": ", resourceBundle.getString("no_year_selected_cmp"));
					     FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
		         }				   
				   
				/*Usuarios*/
				   
				public void sucessSave() {
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
					facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, resourceBundle.getString("sucess_save"), "");
				    FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
					
				}
				
				public void sendEmailSucess(String email) {			
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
					facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, resourceBundle.getString("email_send_sucess"), resourceBundle.getString("email_send_sucess_confirmation")+": "+email);
				    FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
					
				}
				
				public void sendEmailUnsucess() {	
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
					facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("email_change_password_unsucess"), "");
				    FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
					
				}
				
				public void emailNotFound() {	
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
					facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("email_not_found"), resourceBundle.getString("system_error_message1"));
				    FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
					
				}
				
				public void unSucessSave() {	
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
					facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("unsucess_save"), resourceBundle.getString("system_error_message1"));
				    FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
					
				}
				
				public void registroDeletado() {	
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
					facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, resourceBundle.getString("deleted_sucess"), "");
				    FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
					
				}
				   
				//Caso n�o encontre nenhum registro	
				public void usuarioNaoEncontrado() {
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
												 
				    facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, resourceBundle.getString("user_not_found") , "");
					    FacesContext.getCurrentInstance().addMessage(null, facesMsg);		    
			    }
			
				public void usuarioAtualizado() {
										
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
							 
				    facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, resourceBundle.getString("updated_sucess") , "");
					    FacesContext.getCurrentInstance().addMessage(null, facesMsg);		    
			    }
								
				public void usuarioNaoAtualizado() {		
							 
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
				    facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("update_unsucess") , resourceBundle.getString("update_unsucess_cmp"));
					    FacesContext.getCurrentInstance().addMessage(null, facesMsg);		    
			    }
				
				public void senhaAlterada() {		
					 
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
				    facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, resourceBundle.getString("password_changed_sucess") , "");
					    FacesContext.getCurrentInstance().addMessage(null, facesMsg);		    
			    }
				
				public void senhasDiferentes() {		
					 
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
				    facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("password_different_on_change") , resourceBundle.getString("password_different_on_change_cmp"));
					    FacesContext.getCurrentInstance().addMessage(null, facesMsg);		    
			    }
				
				public void usuarioInativo() {	
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
										 
				    facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("inactive_user")+" -" , resourceBundle.getString("inactive_user_comp"));
					    FacesContext.getCurrentInstance().addMessage(null, facesMsg);		    
			    }
								
				public void senhaIncorreta() {	
					
					lang = new LanguageBean();
					locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					 
				    facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("incorrect_password_acess")+" -" , resourceBundle.getString("incorrect_password_acess_cmp"));
					    FacesContext.getCurrentInstance().addMessage(null, facesMsg);		    
			    }
				
				public void senhaNaoConfere() {		
					 
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
				    facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("current_password_incorrect") , "");
					    FacesContext.getCurrentInstance().addMessage(null, facesMsg);		    
			    }
								
				//Login				
				public void usuarioNaoExiste() {	
					
					lang = new LanguageBean();
					locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());					 
					
				    facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("user_not_found")+" -" , resourceBundle.getString("system_error_message1"));
					    FacesContext.getCurrentInstance().addMessage(null, facesMsg);
					    					    
			    }
				
				public void usuarioLogado() {		
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
				    facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("user_logged_in") , " ");
					    FacesContext.getCurrentInstance().addMessage(null, facesMsg);		    
			    }  
				
				/*** UsuarioBean ***/
				
                public void nivelIsNotSelected() {		
					
                	lang = new LanguageBean();
            	    locale = lang.getLocale();
            		resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
            		
				    facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("user_level_not_selected") , resourceBundle.getString("user_level_not_selected_cmp"));
					    FacesContext.getCurrentInstance().addMessage(null, facesMsg);		    
			    }  

                public void statusIsNotSelected() {		
	
                lang = new LanguageBean();
            	locale = lang.getLocale();
            	resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
            		
                facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("user_status_not_selected") , resourceBundle.getString("user_status_not_selected_cmp"));
	            FacesContext.getCurrentInstance().addMessage(null, facesMsg);		    
                }  
                
            public void idIsBlank() {		
					
            	lang = new LanguageBean();
        	    locale = lang.getLocale();
        		resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
        		
				    facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("blank_field_message") , resourceBundle.getString("blank_field_id_message"));
					    FacesContext.getCurrentInstance().addMessage(null, facesMsg);		    
			    }  

                public void nomeIsBlank() {		
	
                	lang = new LanguageBean();
            	    locale = lang.getLocale();
            		resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
            		
                	facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("blank_field_message") , resourceBundle.getString("blank_field_name_message"));
	            FacesContext.getCurrentInstance().addMessage(null, facesMsg);		    
                }  
                
                
           public void cargoIsBlank() {		
					
        		lang = new LanguageBean();
        	    locale = lang.getLocale();
        		resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
        		
        	   facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("blank_field_message") , resourceBundle.getString("blank_field_position_message"));
	                FacesContext.getCurrentInstance().addMessage(null, facesMsg);		    
			    }  

                public void userIsBlank() {		
	
                	lang = new LanguageBean();
            	    locale = lang.getLocale();
            		resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
            		
                facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("blank_field_message") , resourceBundle.getString("blank_field_user_message"));
    	            FacesContext.getCurrentInstance().addMessage(null, facesMsg);		    
                }  
                
                public void emailIsBlank() {		
                	
                	lang = new LanguageBean();
            	    locale = lang.getLocale();
            		resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
            		
                    facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("blank_field_message") , resourceBundle.getString("blank_field_email_message"));
        	            FacesContext.getCurrentInstance().addMessage(null, facesMsg);		    
                    }  
                
                   public void PeriodoIsBlank() {		
                	
                		lang = new LanguageBean();
                	    locale = lang.getLocale();
                		resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
                		
                    facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("blank_field_message") , resourceBundle.getString("blank_field_period_message"));
        	            FacesContext.getCurrentInstance().addMessage(null, facesMsg);		    
                    }  
                   
                   public void closeSession() {		                	   
                		
                		lang = new LanguageBean();
                	    locale = lang.getLocale();
                		resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
                		
                  	lang = new LanguageBean();
   					Locale locale = lang.getLocale();
   					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());	
                   	
                       facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("login_message_logout") , "");           	           
                       FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
								    
                   }   
                                      
            /* Messages Activation Block */       
	           
	           public void UpdateMessage() {			
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
					facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, resourceBundle.getString("pmv_messages_message_update"), "");
				    FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
					
				}
	           
	           public void UpdateMessageFail() {			
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
					facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("pmv_messages_message_update_error"), "");
				    FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
					
				}
	           
	           
	           public void RegisterMessage() {			
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
					facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, resourceBundle.getString("pmv_messages_message_register"), "");
				    FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
					
				}
	           
	           public void RegisterMessageFail() {			
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
					facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("pmv_messages_message_register_error"), "");
				    FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
					
				}
	           	           
	           
	           public void MessageActivationSendError() {			
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
					facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("pmv_messages_message_send_activation_error"), "");
				    FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
					
				}
	           
	           public void MessageActivationSend() {			
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
					facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, resourceBundle.getString("pmv_messages_message_send_activation"), "");
				    FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
					
				}
	           	           	           
	           public void MessageActivationSucess() {			
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
					facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, resourceBundle.getString("pmv_messages_message_activation_sucess"), "");
				    FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
					
				}
	           
	           public void MessageActivationFail() {			
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
					facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("pmv_messages_message_activation_error"), "");
				    FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
					
				}
	           
	           public void NoMessageActivationFound() {			
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
					facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, resourceBundle.getString("pmv_messages_message_activation_not_found"), "");
				    FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
					
				}
	           
	           public void MessageActivationSelected() {			
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
					facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, resourceBundle.getString("pmv_messages_message_activation_selected"), "");
				    FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
					
				}
	           
	           public void MessageDeleteError() {			
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
					facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("pmv_messages_message_delete_error"), "");
				    FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
					
				}
	           
	           public void MessageDeleteSucess() {			
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
					facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, resourceBundle.getString("pmv_messages_message_delete_sucess"), "");
				    FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
					
				}
	           
	           public void MessageSelectionWarning() {			
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
					//facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, resourceBundle.getString("pmv_messages_message_select"), "- "+resourceBundle.getString("pmv_messages_message_select_alert"));
					facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN,"No Message", "- "+"Select One");
					   					
					FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
					
				}
	           
		
	           /* Messages Activation Block */ 
	           
	           /* Messages Selection*/
	           
	           public void messageIsActive() {			
					
					lang = new LanguageBean();
				    locale = lang.getLocale();
					resourceBundle = ResourceBundle.getBundle("bundle.messages.messages_"+locale.toString());
					
					facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao apagar mensagem", "Esta mensagen est� ativa em PMV(s)");
				    FacesContext.getCurrentInstance().addMessage(null, facesMsg);	
					
				}
		
}
