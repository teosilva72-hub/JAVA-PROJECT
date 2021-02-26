package br.com.tracevia.webapp.controller.dms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.context.RequestContext;

import br.com.tracevia.webapp.controller.global.LanguageBean;
import br.com.tracevia.webapp.dao.dms.DMSDAO;
import br.com.tracevia.webapp.dao.dms.MessagesDAO;
import br.com.tracevia.webapp.model.dms.DMS;
import br.com.tracevia.webapp.model.dms.Messages;
import br.com.tracevia.webapp.model.global.Estrada;
import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.MessagesUtil;

@ManagedBean(name="interfaces")
@ViewScoped
public class InterfacesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int amountDMS;
	List<DMS> dmsList;
	
	int[] equips, equipUpd, msgEquip;
	String[] image, image1, imageAux;
	char[][][] letter, letter1, letterAux;
	boolean[] state, state_prev;
	private String imagem;
	private String texto1;
	private String texto2;
	private String texto3;
	private Estrada str, str1;
	private String typeSelection;
		
	boolean checkbox, send;
	boolean checkAllBoxes;
	
	private List<Messages> messages;
	private List<Messages> lista;
	
	/**/
	private List<Integer> equipID, msgID;	
	
	private Messages message;
	int messageID;
	
	LocaleUtil locale;
	
	String standard_image = "000_6464.bmp";
				
	@ManagedProperty(value = "#{language}")
    private LanguageBean lang;	
	
   @ManagedProperty("#{msgs}")
   private ResourceBundle msgs;	
			
	public int getAmountDMS() {
		return amountDMS;
	}

	public void setAmountDMS(int amountDMS) {
		this.amountDMS = amountDMS;
	}
	
	public List<DMS> getDmsList() {
		return dmsList;
	}

	public void setDmsList(List<DMS> dmsList) {
		this.dmsList = dmsList;
	}

	public int[] getEquips() {
		return equips;
	}

	public void setEquips(int[] equips) {
		this.equips = equips;
	}

	public String[] getImage() {
		return image;
	}

	public void setImage(String[] image) {
		this.image = image;
	}
		
	public String[] getImage1() {
		return image1;
	}

	public void setImage1(String[] image1) {
		this.image1 = image1;
	}

	public char[][][] getLetter() {
		return letter;
	}

	public void setLetter(char[][][] letter) {
		this.letter = letter;
	}
		
	public char[][][] getLetter1() {
		return letter1;
	}

	public void setLetter1(char[][][] letter1) {
		this.letter1 = letter1;
	}

	public LanguageBean getLang() {
		return lang;
	}

	public void setLang(LanguageBean lang) {
		this.lang = lang;
	}

	public ResourceBundle getMsgs() {
		return msgs;
	}

	public void setMsgs(ResourceBundle msgs) {
		this.msgs = msgs;
	}
	
	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getTexto1() {
		return texto1;
	}

	public void setTexto1(String texto1) {
		this.texto1 = texto1;
	}

	public String getTexto2() {
		return texto2;
	}

	public void setTexto2(String texto2) {
		this.texto2 = texto2;
	}

	public String getTexto3() {
		return texto3;
	}

	public void setTexto3(String texto3) {
		this.texto3 = texto3;
	}

	public List<Messages> getMessages() {
		return messages;
	}

	public void setMessages(List<Messages> messages) {
		this.messages = messages;
	}

	public Messages getMessage() {
		return message;
	}

	public void setMessage(Messages message) {
		this.message = message;
	}
	
	public boolean[] getState() {
		return state;
	}

	public void setState(boolean[] state) {
		this.state = state;
	}
		
	public boolean isCheckbox() {
		return checkbox;
	}

	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}
	
	public boolean isSend() {
		return send;
	}

	public void setSend(boolean send) {
		this.send = send;
	}
		
	public boolean isCheckAllBoxes() {
		return checkAllBoxes;
	}

	public void setCheckAllBoxes(boolean checkAllBoxes) {
		this.checkAllBoxes = checkAllBoxes;
	}
		
	public Estrada getStr() {
		return str;
	}

	public void setStr(Estrada str) {
		this.str = str;
	}

	public Estrada getStr1() {
		return str1;
	}

	public void setStr1(Estrada str1) {
		this.str1 = str1;
	}
	
	public String getTypeSelection() {
		return typeSelection;
	}

	public void setTypeSelection(String typeSelection) {
		this.typeSelection = typeSelection;
	}

	@PostConstruct
	public void initialize() {
						
		imagem = "000_6464.bmp";	
		
		checkbox = true;
		send = true;
		
		locale = new LocaleUtil();
		locale.getResourceBundle(LocaleUtil.MESSAGES_PMV);
			
		try {
			
			int i = 0;
			
			DMSDAO dmsDAO = new DMSDAO();
			MessagesDAO dao = new MessagesDAO();
			Messages msg = new Messages();
					
			lista = new ArrayList<Messages>();	
			equipID = new ArrayList<Integer>();
			msgID = new ArrayList<Integer>();
					
			message = new Messages(); //Instanciar objeto
						
			dmsList = dmsDAO.idsDMS();
			
			amountDMS = dmsList.size();
											
			 equips = new int[amountDMS];
			 equipUpd = new int[amountDMS];
			 msgEquip = new int[amountDMS];
			 					 
			     letter = new char[amountDMS][3][12];
			     letter1 = new char[amountDMS][3][12];
			     letterAux = new char[amountDMS][3][12];
				 image = new String[amountDMS];
				 image1 = new String[amountDMS];
				 imageAux = new String[amountDMS];
				 state = new boolean[amountDMS];
				 state_prev = new boolean[amountDMS];
				 
				 for(DMS dms : dmsList){						
						equips[i] = dms.getEquip_id();							 
						i++;						
					}
				 
				 for(int k=0; k < amountDMS; k++) {
					 state_prev[k] = false;
				     state[k] = false;
			     }
				 																											
				  lista = dao.selectActivesMessages();
								
                try {
								
				if(!lista.isEmpty()) {
					
					//System.out.println("is here");
				
				int e = 0;
														
				for(Messages m : lista){
					
				  
				  if(m.isActiveMessage()) {
																																		
				  image[e] = m.getImagem();	
				  image1[e] = m.getImagem();
								  					  					  						 																			
				  for(int n = 0; n < m.getTexto1().length(); n++) {
						  letter[e][0][n] = m.getTexto1().charAt(n);
				          letter1[e][0][n] = m.getTexto1().charAt(n);				          
				  }
					   
			      for(int n = 0; n < m.getTexto2().length(); n++) {
						   letter[e][1][n] = m.getTexto2().charAt(n);
						   letter1[e][1][n] = m.getTexto2().charAt(n);						  
			      }
					   
				  for(int n = 0; n < m.getTexto3().length(); n++) {
						    letter[e][2][n] = m.getTexto3().charAt(n);	
						    letter1[e][2][n] = m.getTexto3().charAt(n);					    
				  }
				  				 				  				  
					}else {
					  
										
					msg = dao.selectMessageToUpdate(m.getEquip());
			    				    	 								  
					RequestContext.getCurrentInstance().execute("myFunction('"+equips[e]+"');");
				    RequestContext.getCurrentInstance().execute("myFunction('1"+equips[e]+"');");
					
					RequestContext.getCurrentInstance().execute("uncheckedOpacityNormalShadow('"+equips[e]+"');");
					RequestContext.getCurrentInstance().execute("uncheckedOpacityNormalShadow('1"+equips[e]+"');");	
					
					  image[e] = m.getImagem(); //IF IS INACTIVE MUST REPEAT THE MESSAGE
					  image1[e] = msg.getImagem();
					  imageAux[e] = msg.getImagem();
					
					//IF IS INACTIVE MUST REPEAT THE ACTIVE MESSAGE ON THIS PART					
					   for(int n = 0; n < m.getTexto1().length(); n++) 
						  letter[e][0][n] = m.getTexto1().charAt(n);				    
					   
			           for(int n = 0; n < m.getTexto2().length(); n++) 
						   letter[e][1][n] = m.getTexto2().charAt(n);						   			    
					   
				       for(int n = 0; n < m.getTexto3().length(); n++) 
				    	   letter[e][2][n] = m.getTexto3().charAt(n);
				       
				     //IF IS INACTIVE MUST REPEAT THE ACTIVE MESSAGE ON THIS PART
						   				    	       
				         // HERE SET THE MESSAGE TO ACTIVATE ON THIS PART			    	
					      for(int n = 0; n < msg.getTexto1().length(); n++) {
								  letter1[e][0][n] = msg.getTexto1().charAt(n);
						          letterAux[e][0][n] = msg.getTexto1().charAt(n);				          
						  }
							   
					      for(int n = 0; n < msg.getTexto2().length(); n++) {
								   letter1[e][1][n] = msg.getTexto2().charAt(n);
								   letterAux[e][1][n] = msg.getTexto2().charAt(n);						  
					      }
							   
						  for(int n = 0; n < msg.getTexto3().length(); n++) {
								    letter1[e][2][n] = msg.getTexto3().charAt(n);	
								    letterAux[e][2][n] = msg.getTexto3().charAt(n);		  			    	
					    }
						  
					   // HERE SET THE MESSAGE TO ACTIVATE ON THIS PART																							
				     }
				  
				     e++; // incrementa equipamento				  					  						   						        
					       					
				  }	
								
			} else {
				
				//IF HAVEN´T REGISTERS								
				for(int eq = 0; eq < amountDMS; eq++) {
				     
					image[eq] = standard_image; 					
					image1[eq] = standard_image;
					imageAux[eq] = standard_image; 
					
				   for(int n = 0; n < 12; n++) {
					    letter[eq][0][n] = Character.MIN_VALUE;
				        letter1[eq][0][n] = Character.MIN_VALUE;
				        letterAux[eq][0][n] = Character.MIN_VALUE;	
				   }
				   
				   for(int n = 0; n < 12; n++) {
					    letter[eq][1][n] = Character.MIN_VALUE;
				        letter1[eq][1][n] = Character.MIN_VALUE;	
				        letterAux[eq][1][n] = Character.MIN_VALUE;	
				   }
				   
				   for(int n = 0; n < 12; n++) {
					    letter[eq][2][n] = Character.MIN_VALUE;	
				        letter1[eq][2][n] = Character.MIN_VALUE;
				        letterAux[eq][2][n] = Character.MIN_VALUE;	
				   }				   				          	
				}					
      		} 
								
            }catch(IndexOutOfBoundsException ex) {}
													
		} catch (Exception e) {
			
			e.printStackTrace();
		}					
	 }
				
	public void loadMessageInformation(AjaxBehaviorEvent event) throws AbortProcessingException {	
				
		this.messageID = message.getId_reg();
		this.imagem = String.valueOf(message.getImagem());	
		this.texto1 = message.getTexto1();
		this.texto2 = message.getTexto2();
		this.texto3 = message.getTexto3();
		
		//messages = null;
		typeSelection = null;
	}
		
	public void sendMessageToActivation() throws Exception { 
	
		   MessagesUtil message = new MessagesUtil();
			
           if(equipID.isEmpty() || msgID.isEmpty()) {
			
        	 waitSecs(2000);
        	  message.WarningMessage(locale.getStringKey("dms_activation_message_no_message_selected"), " ");
  			
			  RequestContext.getCurrentInstance().execute("hideMsg();");
			 
			
		}else {
				
		for(int i = 0; i < equipID.size(); i++) { 
			
			try {
		    
			equipUpd[i] = equipID.get(i);	
		    msgEquip[i] = msgID.get(i);
		 
			}catch(IndexOutOfBoundsException ex) {}
		 
		}
								
		boolean answer = false;
	
		MessagesDAO dao = new MessagesDAO();		
			
		answer = dao.updateActivesMessages(msgEquip, equipUpd);
		
		if(answer) { 	
						
		  waitSecs(3000);		  
		  RequestContext.getCurrentInstance().execute("refresh();");
				  
		}
					    		
		else message.ErrorMessage(locale.getStringKey("dms_activation_message_send_activation_error"), " ");
					
		send = true; //Reset Aplicar
	        
		}
	}
	
   public void checkBoxSelection(AjaxBehaviorEvent event) throws AbortProcessingException {		   
	   
	   int falseCT = 0;
	   
	   for(int i = 0; i < state.length; i++) {
			if(state[i] == true)  {
	            checkbox = false;
	            send = false;
				
			 RequestContext.getCurrentInstance().execute("changeOpacity('"+equips[i]+"');");
			 RequestContext.getCurrentInstance().execute("changeOpacity('1"+equips[i]+"');");
			 RequestContext.getCurrentInstance().execute("imageOpacity();");
			  	
			}
			
			if(state[i] == false)
				falseCT++;				   			   	   			   		   			   
								
			if(state[i] == false && state_prev[i] == false) {	
				
                try{ 
					
					equipID.remove(i);
					msgID.remove(i);
					
				}catch(IndexOutOfBoundsException ex) {}
				  								
				RequestContext.getCurrentInstance().execute("uncheckedOpacityNormalShadow('"+equips[i]+"');");
				RequestContext.getCurrentInstance().execute("uncheckedOpacityNormalShadow('1"+equips[i]+"');");			
			    RequestContext.getCurrentInstance().execute("myFunction2('"+equips[i]+"');");		
			    RequestContext.getCurrentInstance().execute("myFunction2('1"+equips[i]+"');");	
				
			}	
								
			//Deixar a apresentação conforme chefe solicitou
			if(state[i] == false && state_prev[i] == true) {
																							
				  RequestContext.getCurrentInstance().execute("myFunction('"+equips[i]+"');");
				  RequestContext.getCurrentInstance().execute("myFunction('1"+equips[i]+"');");
				   
				   RequestContext.getCurrentInstance().execute("checkedOpacity('"+equips[i]+"');");
				   RequestContext.getCurrentInstance().execute("checkedOpacity('1"+equips[i]+"');");
				   
				
			   }	
			
			/* Caso começe o a mensagem sem ativação - permancer nessa configuração */
			if(state[i] == false && state_prev[i] == false && !lista.get(i).isActiveMessage()) {
				
				RequestContext.getCurrentInstance().execute("myFunction('"+equips[i]+"');");
			    RequestContext.getCurrentInstance().execute("myFunction('1"+equips[i]+"');");
				
				RequestContext.getCurrentInstance().execute("uncheckedOpacityNormalShadow('"+equips[i]+"');");
				RequestContext.getCurrentInstance().execute("uncheckedOpacityNormalShadow('1"+equips[i]+"');");	
				
			}	
	    }	
	   
	   	   
	   if(falseCT == state.length) {
	    	checkbox = true; 
	    		      
	       imagem = "000_6464.bmp";
		   texto1 = "";
		   texto2 = "";
		   texto3 = "";	
		   
		   message = new Messages();
		   
		   RequestContext.getCurrentInstance().execute("imageOpacityLow();");	
	    }   
	   
     }
   
   public void aplicarMensagens() {
	   	    
	   MessagesUtil messageUtil = new MessagesUtil();
	   		   
	   if(message == null) {
		   
		   messageUtil.WarningMessage(locale.getStringKey("dms_activation_message_no_message_selected"), " ");
			   
		   for(int i = 0; i < state.length; i++) {
											
				if(state[i] == true && state_prev[i] == false) {				
				   				     			    
				    RequestContext.getCurrentInstance().execute("myFunction2('"+equips[i]+"');");
					RequestContext.getCurrentInstance().execute("myFunction2('1"+equips[i]+"');");
					   
					RequestContext.getCurrentInstance().execute("uncheckedOpacityNormalShadow('"+equips[i]+"');");
					RequestContext.getCurrentInstance().execute("uncheckedOpacityNormalShadow('1"+equips[i]+"');");
					
					RequestContext.getCurrentInstance().execute("changeOpacity('"+equips[i]+"');");
				    RequestContext.getCurrentInstance().execute("changeOpacity('1"+equips[i]+"');");	
				   	
			     }
							
				//Deixar a apresentação conforme chefe solicitou
				if(state[i] == false && state_prev[i] == true) {
																								
					 RequestContext.getCurrentInstance().execute("myFunction('"+equips[i]+"');");
					 RequestContext.getCurrentInstance().execute("myFunction('1"+equips[i]+"');");
					   
					 RequestContext.getCurrentInstance().execute("uncheckedOpacity('"+equips[i]+"');");
					 RequestContext.getCurrentInstance().execute("uncheckedOpacity('1"+equips[i]+"');");	
					 					   					
				   }	
				
				if(state[i] == true && state_prev[i] == true) {				
				     			     
				    // checkAllBoxes = false; // Caso esteja selecionado
				     			    
				    RequestContext.getCurrentInstance().execute("myFunction('"+equips[i]+"');");
					RequestContext.getCurrentInstance().execute("myFunction('1"+equips[i]+"');");
					   
					RequestContext.getCurrentInstance().execute("checkedOpacity('"+equips[i]+"');");
					RequestContext.getCurrentInstance().execute("checkedOpacity('1"+equips[i]+"');");	
					
					RequestContext.getCurrentInstance().execute("changeOpacity('"+equips[i]+"');");
				    RequestContext.getCurrentInstance().execute("changeOpacity('1"+equips[i]+"');");
				 
					
			      }	
				

				/* Caso a mensagem ainda n foi ativada - manter em estado de ativação */
				  
				  /* Caso começe com a mensagem sem ativação - permancer nessa configuração */
				  if(state[i] == false && state_prev[i] == false && !lista.get(i).isActiveMessage()) {
					
					RequestContext.getCurrentInstance().execute("myFunction('"+equips[i]+"');");
				    RequestContext.getCurrentInstance().execute("myFunction('1"+equips[i]+"');");
					
					RequestContext.getCurrentInstance().execute("uncheckedOpacityNormalShadow('"+equips[i]+"');");
					RequestContext.getCurrentInstance().execute("uncheckedOpacityNormalShadow('1"+equips[i]+"');");					
								
				   }
				  
			        /* Caso a mensagem ainda n foi ativada - manter em estado de ativação */			       
		       }
		   		   
		         RequestContext.getCurrentInstance().execute("imageOpacity();");
		         RequestContext.getCurrentInstance().execute("hideMsg();");
		             	
	      }
	   
	   else {
	   
	   for(int i = 0; i < state.length; i++) {
								
			if(state[i] == true && state_prev[i] == false) {				
			     state_prev[i] = true;
			     
			     checkAllBoxes = false; // Caso esteja selecionado
			     			    
			    RequestContext.getCurrentInstance().execute("myFunction('"+equips[i]+"');");
				RequestContext.getCurrentInstance().execute("myFunction('1"+equips[i]+"');");
				   
				RequestContext.getCurrentInstance().execute("checkedOpacity('"+equips[i]+"');");
				RequestContext.getCurrentInstance().execute("checkedOpacity('1"+equips[i]+"');");
			    		   
				image1[i] = imagem;
								
				equipID.add(equips[i]); //Equipamentos para atualizar
				msgID.add(messageID); //Equipamentos para atualizar
								
				for(int n = 0; n < texto1.length(); n++)					
				    letter1[i][0][n] = texto1.charAt(n);
					
				for(int n = 0; n < texto2.length(); n++)		
				    letter1[i][1][n] = texto2.charAt(n);
					
				for(int n = 0; n < texto3.length(); n++)					
				    letter1[i][2][n] = texto3.charAt(n);
									
		     }
						
			//Deixar a apresentação conforme chefe solicitou
			if(state[i] == false && state_prev[i] == true) {
																							
				  RequestContext.getCurrentInstance().execute("myFunction('"+equips[i]+"');");
				  RequestContext.getCurrentInstance().execute("myFunction('1"+equips[i]+"');");
				   
				   RequestContext.getCurrentInstance().execute("uncheckedOpacity('"+equips[i]+"');");
				   RequestContext.getCurrentInstance().execute("uncheckedOpacity('1"+equips[i]+"');");				   
				   				
			   }	
			
			if(state[i] == true && state_prev[i] == true) {				
			     			     
			     checkAllBoxes = false; // Caso esteja selecionado
			     			    
			    RequestContext.getCurrentInstance().execute("myFunction('"+equips[i]+"');");
				RequestContext.getCurrentInstance().execute("myFunction('1"+equips[i]+"');");
				   
				RequestContext.getCurrentInstance().execute("checkedOpacity('"+equips[i]+"');");
				RequestContext.getCurrentInstance().execute("checkedOpacity('1"+equips[i]+"');");
						 		   
				image1[i] = imagem;
				
				//Bug Quando n aplicar nenhuma mensagem
			//	equipID.add(equips[i]); //Equipamentos para atualizar
			//	msgID.add(messageID); //Equipamentos para atualizar
												
				for(int n = 0; n < texto1.length(); n++)					
				    letter1[i][0][n] = texto1.charAt(n);
					
				for(int n = 0; n < texto2.length(); n++)		
				    letter1[i][1][n] = texto2.charAt(n);
					
				for(int n = 0; n < texto3.length(); n++)					
				    letter1[i][2][n] = texto3.charAt(n);				
		     }
			
			/* Caso a mensagem ainda n foi ativada - manter em estado de ativação */
			  
			  /* Caso começe com a mensagem sem ativação - permancer nessa configuração */
			  if(state[i] == false && state_prev[i] == false && !lista.get(i).isActiveMessage()) {
				
				RequestContext.getCurrentInstance().execute("myFunction('"+equips[i]+"');");
			    RequestContext.getCurrentInstance().execute("myFunction('1"+equips[i]+"');");
				
				RequestContext.getCurrentInstance().execute("uncheckedOpacityNormalShadow('"+equips[i]+"');");
				RequestContext.getCurrentInstance().execute("uncheckedOpacityNormalShadow('1"+equips[i]+"');");					
							
			   }
			  
		   /* Caso a mensagem ainda n foi ativada - manter em estado de ativação */		
			
			state[i] = false;
				        
	       }
	           
	        resetButton();
	        checkbox = true;
	        send = false;
	        
	      }
       }
      
   public void limparMensagens() {
	   
	   for(int i = 0; i < state.length; i++) {
									
			if(state[i] == true && state_prev[i] == false) {				
			    state_prev[i] = true;
			    
			    checkAllBoxes = false; // Caso esteja selecionado
			    
			    RequestContext.getCurrentInstance().execute("myFunction('"+equips[i]+"');");
				RequestContext.getCurrentInstance().execute("myFunction('1"+equips[i]+"');");				   
				RequestContext.getCurrentInstance().execute("checkedOpacity('"+equips[i]+"');");
				RequestContext.getCurrentInstance().execute("checkedOpacity('1"+equips[i]+"');");
		
				image1[i] = "000_6464.bmp";
								
				equipID.add(equips[i]); //Equipamentos para atualizar
				msgID.add(1); //Equipamentos para atualizar
								
				for(int n = 0; n < 12; n++) {
				    letter1[i][0][n] = Character.MIN_VALUE;
				    letter1[i][1][n] = Character.MIN_VALUE;
				    letter1[i][2][n] = Character.MIN_VALUE;
				}				
		     }
			
			//Deixar a apresentação conforme chefe solicitou
			if(state[i] == false && state_prev[i] == true) {
																							
				  RequestContext.getCurrentInstance().execute("myFunction('"+equips[i]+"');");
				  RequestContext.getCurrentInstance().execute("myFunction('1"+equips[i]+"');");
				   
				   RequestContext.getCurrentInstance().execute("uncheckedOpacity('"+equips[i]+"');");
				   RequestContext.getCurrentInstance().execute("uncheckedOpacity('1"+equips[i]+"');");				   
				  
			  }			
		
			
			if(state[i] == true && state_prev[i] == true) {				
			     			     
			    checkAllBoxes = false; // Caso esteja selecionado
			     			    
			    RequestContext.getCurrentInstance().execute("myFunction('"+equips[i]+"');");
				RequestContext.getCurrentInstance().execute("myFunction('1"+equips[i]+"');");
				   
				RequestContext.getCurrentInstance().execute("checkedOpacity('"+equips[i]+"');");
				RequestContext.getCurrentInstance().execute("checkedOpacity('1"+equips[i]+"');");
			    		   
				image1[i] = "000_6464.bmp";
				
				//Bug Quando n aplicar nenhuma mensagem
				//equipID.add(equips[i]); //Equipamentos para atualizar
				//msgID.add(1); //Equipamentos para atualizar
												
				for(int n = 0; n < 12; n++) {
				    letter1[i][0][n] = Character.MIN_VALUE;
				    letter1[i][1][n] = Character.MIN_VALUE;
				    letter1[i][2][n] = Character.MIN_VALUE;
				}	     
		     }
			
			/* Caso a mensagem ainda n foi ativada - manter em estado de ativação */
			  
			  /* Caso começe com a mensagem sem ativação - permancer nessa configuração */
			  if(state[i] == false && state_prev[i] == false && !lista.get(i).isActiveMessage()) {
				
				RequestContext.getCurrentInstance().execute("myFunction('"+equips[i]+"');");
			    RequestContext.getCurrentInstance().execute("myFunction('1"+equips[i]+"');");
				
				RequestContext.getCurrentInstance().execute("uncheckedOpacityNormalShadow('"+equips[i]+"');");
				RequestContext.getCurrentInstance().execute("uncheckedOpacityNormalShadow('1"+equips[i]+"');");					
							
			   }
			  
		   /* Caso a mensagem ainda n foi ativada - manter em estado de ativação */		
			
			state[i] = false;		
		   }
	   
	     resetButton();
         checkbox = true;
         send = false;
      }   
   
   public void resetMessages() {
		   	   	  	   		  	   
	   for(int i = 0; i < state.length; i++) {
									
			if(state[i] == true && state_prev[i] == true) {				
			   			    
			      checkAllBoxes = false; // Caso esteja selecionado
			    		    
			      RequestContext.getCurrentInstance().execute("uncheckedOpacityNormalShadow('"+equips[i]+"');");
				  RequestContext.getCurrentInstance().execute("uncheckedOpacityNormalShadow('1"+equips[i]+"');");			
				  RequestContext.getCurrentInstance().execute("myFunction2('"+equips[i]+"');");		
				  RequestContext.getCurrentInstance().execute("myFunction2('1"+equips[i]+"');");
				  
				  if(!lista.get(i).isActiveMessage()) {
					  
					  try{ 
							
							equipID.remove(i);
							msgID.remove(i);
							
						}catch(IndexOutOfBoundsException ex) {}
		
				  image1[i] = imageAux[i];				  					
							
				for(int n = 0; n < 12; n++)					
				    letter1[i][0][n] = letterAux[i][0][n];
					
				for(int n = 0; n < 12; n++)		
				    letter1[i][1][n] = letterAux[i][1][n];
					
				for(int n = 0; n < 12; n++)					
				    letter1[i][2][n] = letterAux[i][2][n];
				
				  }else {
					  
					  image1[i] = image[i];
					  
					  try{ 							
							equipID.remove(i);
							msgID.remove(i);
							
						}catch(IndexOutOfBoundsException ex) {}
									
						for(int n = 0; n < 12; n++)					
						    letter1[i][0][n] = letter[i][0][n];
							
						for(int n = 0; n < 12; n++)		
						    letter1[i][1][n] = letter[i][1][n];
							
						for(int n = 0; n < 12; n++)					
						    letter1[i][2][n] = letter[i][2][n];				  
				  }							
		     }
											
			//Deixar a apresentação conforme chefe solicitou
			if(state[i] == false && state_prev[i] == true) {
																							
				  RequestContext.getCurrentInstance().execute("myFunction('"+equips[i]+"');");
				  RequestContext.getCurrentInstance().execute("myFunction('1"+equips[i]+"');");
				   
				   RequestContext.getCurrentInstance().execute("uncheckedOpacity('"+equips[i]+"');");
				   RequestContext.getCurrentInstance().execute("uncheckedOpacity('1"+equips[i]+"');");
				   
				 }	
			
			//Deixar a apresentação conforme chefe solicitou
			if(state[i] == true && state_prev[i] == false) {
																							
				   RequestContext.getCurrentInstance().execute("myFunction2('"+equips[i]+"');");
				   RequestContext.getCurrentInstance().execute("myFunction2('1"+equips[i]+"');");
				   
				   RequestContext.getCurrentInstance().execute("uncheckedOpacityNormalShadow('"+equips[i]+"');");
				   RequestContext.getCurrentInstance().execute("uncheckedOpacityNormalShadow('1"+equips[i]+"');");
				   
				   if(!lista.get(i).isActiveMessage()) {
					   					  			   
				   image1[i] = imageAux[i];
				   										
				   for(int n = 0; n < 12; n++)					
					    letter1[i][0][n] = letterAux[i][0][n];
						
					for(int n = 0; n < 12; n++)		
					    letter1[i][1][n] = letterAux[i][1][n];
						
					for(int n = 0; n < 12; n++)					
					    letter1[i][2][n] = letterAux[i][2][n];
					
				   }else {
					   
					   image1[i] = image[i];
							
					   for(int n = 0; n < 12; n++)					
						    letter1[i][0][n] = letter[i][0][n];
							
						for(int n = 0; n < 12; n++)		
						    letter1[i][1][n] = letter[i][1][n];
							
						for(int n = 0; n < 12; n++)					
						    letter1[i][2][n] = letter[i][2][n];
					   
				   }
					
				 }	
			
			/* Caso a mensagem ainda n foi ativada - manter em estado de ativação */
					  
			  /* Caso começe com a mensagem sem ativação - permancer nessa configuração */
			  if(state[i] == true && state_prev[i] == false && !lista.get(i).isActiveMessage() ||
				   state[i] == true && state_prev[i] == true && !lista.get(i).isActiveMessage() ||
					  state[i] == false && state_prev[i] == false && !lista.get(i).isActiveMessage()) {
				
				RequestContext.getCurrentInstance().execute("myFunction('"+equips[i]+"');");
			    RequestContext.getCurrentInstance().execute("myFunction('1"+equips[i]+"');");
				
				RequestContext.getCurrentInstance().execute("uncheckedOpacityNormalShadow('"+equips[i]+"');");
				RequestContext.getCurrentInstance().execute("uncheckedOpacityNormalShadow('1"+equips[i]+"');");	
				
				  if(!lista.get(i).isActiveMessage()) {
					  				
				        image1[i] = imageAux[i];
													
				        for(int n = 0; n < 12; n++)					
						    letter1[i][0][n] = letterAux[i][0][n];
							
						for(int n = 0; n < 12; n++)		
						    letter1[i][1][n] = letterAux[i][1][n];
							
						for(int n = 0; n < 12; n++)					
						    letter1[i][2][n] = letterAux[i][2][n];
						
			   } else {
				   
				   image1[i] = image[i];
					
				   for(int n = 0; n < 12; n++)					
					    letter1[i][0][n] = letter[i][0][n];
						
					for(int n = 0; n < 12; n++)		
					    letter1[i][1][n] = letter[i][1][n];
						
					for(int n = 0; n < 12; n++)					
					    letter1[i][2][n] = letter[i][2][n];
				   				   
			   }
				  
			  }
			  
		   /* Caso a mensagem ainda n foi ativada - manter em estado de ativação */		
							
		    state[i] = false;	    
			  
	   }
		  
	   resetButton();          
       checkbox = true;
      // send = true;
    }
   
        
   public void resetButton() {
	   
	   imagem = "000_6464.bmp";
	   texto1 = null;
	   texto2 = null;
	   texto3 = null;	
	   message = null;
	   messages = null;
	   typeSelection = null;
   }
   
   public void clearList() {
 	   
	   equipID.clear();
	   msgID.clear();	   
   }
   
   public void checkAllItems(AjaxBehaviorEvent event) throws AbortProcessingException {
	   
	   if(checkAllBoxes)  	{ 		 	     
	   	 	   	   
	   	//Marca todos checkboxes   
	   	for(int i = 0; i < state.length; i++) 	   		
	   		state[i] = true;
	   		   		   
	    for(int i = 0; i < state.length; i++) {
			
			if(state[i] == true && state_prev[i] == false) {				
			   				     			    
			    RequestContext.getCurrentInstance().execute("myFunction2('"+equips[i]+"');");
				RequestContext.getCurrentInstance().execute("myFunction2('1"+equips[i]+"');");
				   
				RequestContext.getCurrentInstance().execute("changeOpacity('"+equips[i]+"');");
				RequestContext.getCurrentInstance().execute("changeOpacity('1"+equips[i]+"');");		   	
		     }
										
			if(state[i] == true && state_prev[i] == true) {	     	    
					     			    
			    RequestContext.getCurrentInstance().execute("myFunction('"+equips[i]+"');");
				RequestContext.getCurrentInstance().execute("myFunction('1"+equips[i]+"');");
				   
				RequestContext.getCurrentInstance().execute("uncheckedOpacity('"+equips[i]+"');");
				RequestContext.getCurrentInstance().execute("uncheckedOpacity('1"+equips[i]+"');");
				 
				RequestContext.getCurrentInstance().execute("changeOpacity('"+equips[i]+"');");
				RequestContext.getCurrentInstance().execute("changeOpacity('1"+equips[i]+"');");	
				
		      }	
			
			 /* Caso começe o a mensagem sem ativação - permancer nessa configuração */
			  if(state[i] == true && state_prev[i] == false && !lista.get(i).isActiveMessage()) {
				
				RequestContext.getCurrentInstance().execute("myFunction('"+equips[i]+"');");
			    RequestContext.getCurrentInstance().execute("myFunction('1"+equips[i]+"');");
				
				RequestContext.getCurrentInstance().execute("uncheckedOpacityNormalShadow('"+equips[i]+"');");
				RequestContext.getCurrentInstance().execute("uncheckedOpacityNormalShadow('1"+equips[i]+"');");	
				
				RequestContext.getCurrentInstance().execute("changeOpacity('"+equips[i]+"');");
				RequestContext.getCurrentInstance().execute("changeOpacity('1"+equips[i]+"');");
				
			   }	
			  
	        } 
	    
	         RequestContext.getCurrentInstance().execute("imageOpacity();");
	    
	      checkbox = false;		      
	   }
	   
	   else {
		   	   
		   state = new boolean[amountDMS];	
		   
		 //Marca todos checkboxes   
		   	for(int i = 0; i < state.length; i++) 	   		
		   		state[i] = false;
		   	
		    for(int i = 0; i < state.length; i++) {
				
				if(state[i] == false && state_prev[i] == true) {				
				   				     			    
					RequestContext.getCurrentInstance().execute("myFunction('"+equips[i]+"');");
					RequestContext.getCurrentInstance().execute("myFunction('1"+equips[i]+"');");	
					   
					RequestContext.getCurrentInstance().execute("uncheckedOpacity('"+equips[i]+"');");
					RequestContext.getCurrentInstance().execute("uncheckedOpacity('1"+equips[i]+"');");	
							    
				}
											
				if(state[i] == false && state_prev[i] == false) {	     	    
						    					
					RequestContext.getCurrentInstance().execute("myFunction2('"+equips[i]+"');");
				    RequestContext.getCurrentInstance().execute("myFunction2('1"+equips[i]+"');");
					
					RequestContext.getCurrentInstance().execute("uncheckedOpacityNormalShadow('"+equips[i]+"');");
					RequestContext.getCurrentInstance().execute("uncheckedOpacityNormalShadow('1"+equips[i]+"');");	
					
			      }
				
				  /* Caso começe o a mensagem sem ativação - permancer nessa configuração */
				  if(state[i] == false && state_prev[i] == false && !lista.get(i).isActiveMessage()) {
					
					RequestContext.getCurrentInstance().execute("myFunction('"+equips[i]+"');");
				    RequestContext.getCurrentInstance().execute("myFunction('1"+equips[i]+"');");
					
					RequestContext.getCurrentInstance().execute("uncheckedOpacityNormalShadow('"+equips[i]+"');");
					RequestContext.getCurrentInstance().execute("uncheckedOpacityNormalShadow('1"+equips[i]+"');");	
					
				   }	
				
		        } 
		    
		      checkbox = true;		     
		   }
		   
		   
		   imagem = "000_6464.bmp";
		   texto1 = "";
		   texto2 = "";
		   texto3 = "";			   
	    
        }    
         
   /* Executar threads */   
 /*  public void executeThreads() throws Exception {
	   
				 	  		
	   t1.start();
      // new Thread(t2).start();
	  // t1.join();
	   
	   //t2.start();
	   t1.interrupt();
	   //t2.stop();
       
       
	   clearList();
	   send = true;
	   
   }   */
   
   
   public static void waitSecs(int time) {
	   
	    try {
	    	
	        Thread.sleep(time); //paraliza por x segundos a thread atual
	        
	       } catch (InterruptedException ex) {
	        
	     }
	  }   
   
   public void getMessageListType() throws Exception {
	   
	   MessagesDAO dao = new MessagesDAO();	  
	   messages = dao.availableMessagesByType(typeSelection);
	   
	   //finally
	   //RequestContext.getCurrentInstance().execute("changeDIV();");
   }
   
   
   
   
   /* Verificar Mensagens no DB */
   public void checkActiveMessage() throws Exception {
  	 
     List<Messages> list = new ArrayList<Messages>();
     MessagesUtil message = new MessagesUtil();
     
     String success = "", notUptate = "";
           	 		 
  	 MessagesDAO dao = new MessagesDAO();	
  	  	    		 	    				  
  	 list = dao.checkModifyMessageState(equipUpd);
  	 
  	 for(Messages m: list) {
  		   		 
  		 if(m.isActiveMessage())			
  			  success += String.valueOf(m.getNome())+" "; 			 
  		   		 
  		 else  notUptate += String.valueOf(m.getNome())+" "; 	 		 
  	 }

  	 if(!success.equals("")) 	
  		 message.InfoMessage(locale.getStringKey("dms_activation_message_update") , success);
    
  	 if(!notUptate.equals(""))
  		 message.WarningMessage(locale.getStringKey("dms_activation_message_update_error") , notUptate);
  				 	     
       }   
  
 }