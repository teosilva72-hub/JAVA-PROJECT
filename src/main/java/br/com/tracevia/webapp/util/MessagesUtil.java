package br.com.tracevia.webapp.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MessagesUtil {
		
	
	public void InfoMessage(String messageHeader, String messageBody) {
		  
		FacesMessage infoMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, messageHeader , messageBody);
		    FacesContext.getCurrentInstance().addMessage(null, infoMessage);	  
		  
	  }
	
	public void WarningMessage(String messageHeader, String messageBody) {
		  
		FacesMessage warnMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, messageHeader , messageBody);
		    FacesContext.getCurrentInstance().addMessage(null, warnMessage);	  
		  
	  }
	
	public void ErrorMessage(String messageHeader, String messageBody) {
		  
		FacesMessage errorMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageHeader , messageBody);
		    FacesContext.getCurrentInstance().addMessage(null, errorMessage); 
	  }
	
	 
	  public void FatalMessage(String messageHeader, String messageBody) {
		  
		  FacesMessage fatalMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, messageHeader , messageBody);
		    FacesContext.getCurrentInstance().addMessage(null, fatalMessage);	  
		  
	  }
	  	  
	  /*VALIDATOR MESSAGES*/
	  
	   public FacesMessage InfoMessageValidator(String messageHeader, String messageBody) {
		  
	    FacesMessage infoMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, messageHeader , messageBody);
			    
	    return infoMessage;
			  
	   }
		
		public FacesMessage WarningMessageValidator(String messageHeader, String messageBody) {
			  
		  FacesMessage warnMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, messageHeader , messageBody);
						    
		return warnMessage;
			  
	    }
	  
	    public FacesMessage ErrorMessageValidator(String messageHeader, String messageBody) {
	       FacesMessage errorMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageHeader , messageBody);
		  
		  return errorMessage;		  
	   }	  
	  
	  
        public FacesMessage FatalMessageValidator(String messageHeader, String messageBody) {
		  
		   FacesMessage fatalMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, messageHeader , messageBody);   
		  
		  return fatalMessage;
	    }
	    	  
}
