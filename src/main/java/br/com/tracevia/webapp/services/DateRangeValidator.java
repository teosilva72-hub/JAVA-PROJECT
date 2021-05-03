package br.com.tracevia.webapp.validator;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.MessagesUtil;

@FacesValidator(value = "dateRangeValidator")
public class DateRangeValidator implements Validator {
			
    LocaleUtil locale;
	MessagesUtil message;
	
	public DateRangeValidator(){
	  locale = new LocaleUtil();
	  locale.getResourceBundle(LocaleUtil.MESSAGES_VALIDATOR);	
	  message = new MessagesUtil();		
	}
	
  @Override
  public void validate(FacesContext context,  UIComponent uiComponent, Object value) throws ValidatorException {
	       
	  UIInput startDateInput = (UIInput) uiComponent.getAttributes().get("startDateAttr");
     
	  Date startDate = (Date) startDateInput.getValue();
     
	  Date endDate = (Date) value;

      if (endDate.before(startDate)) {    	  
         
           FacesMessage msg = message.ErrorMessageValidator(locale.getStringKey("validator_start_date_after_end_date_header"), locale.getStringKey("validator_start_date_after_end_date_body"));
		   
		  throw new ValidatorException(msg);
          
      }
  }

}