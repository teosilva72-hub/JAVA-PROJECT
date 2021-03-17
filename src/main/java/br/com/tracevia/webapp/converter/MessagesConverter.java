package br.com.tracevia.webapp.converter;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.tracevia.webapp.dao.dms.MessagesDAO;
import br.com.tracevia.webapp.model.dms.Messages;

@FacesConverter(value = "messageConverter")
public class MessagesConverter implements Converter {
		
	 public static List<Messages> messagesDB;
	 public static MessagesDAO dao;
	 public static Messages msg;
	 
	
	 @Override
		public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
			
			
			 if(submittedValue != null && submittedValue.trim().length() > 0) {
		           
				 try {	            	
					    dao = new MessagesDAO();	
					    msg = new Messages();
		            			                
		                msg = dao.mensagensDisponivelById(Integer.parseInt(submittedValue));             

		                return msg;
		                
		            } catch(NumberFormatException exception) {
		                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Message"));
		            } catch (Exception e) {					
						e.printStackTrace();
					}
		        } 
			
			 return null;
			 
		    }

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
		
		 if (value == null || value.equals("")) {
	            return "";
	        } else {
	            return String.valueOf(((Messages) value).getId_message());
	        }
	}
	
 }
