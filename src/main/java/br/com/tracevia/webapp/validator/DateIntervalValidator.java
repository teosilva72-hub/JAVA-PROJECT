package br.com.tracevia.webapp.validator;

import java.text.ParseException;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.MessagesUtil;

@FacesValidator(value = "dateIntervalValidator")
public class DateIntervalValidator implements Validator {

	DateTimeApplication dta;	
    LocaleUtil locale;
	MessagesUtil message;
	
	public DateIntervalValidator() {
	  locale = new LocaleUtil();
	  locale.getResourceBundle(LocaleUtil.MESSAGES_VALIDATOR);	
	  message = new MessagesUtil();
	  dta = new DateTimeApplication();
	}
	

	@Override
	public void validate(FacesContext context, UIComponent uiComponent, Object value) throws ValidatorException {
	
		boolean response = false;		
		
		 UIInput startDateInput = (UIInput) uiComponent.getAttributes().get("startDateAttr");
	     
		  Date startDate = (Date) startDateInput.getValue();	     
		  Date endDate = (Date) value;
		  
		  String begin = dta.DateTimeToString(startDate, DateTimeApplication.DATE_TIME_FORMAT_DATE_VIEW_START );
		  String end = dta.DateTimeToString(endDate, DateTimeApplication.DATE_TIME_FORMAT_DATE_VIEW_END );		  
			
		 String dataInicio;
		 String dataFim;
			
			try {
				
				dataInicio = dta.StringDateFormat(begin);
				dataFim = dta.StringDateFormat(end);
				  
				response = dta.limitSearch(dataInicio, dataFim, 45);				  
				  
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		 		
		if(response) {
					
			FacesMessage msg = message.ErrorMessageValidator(locale.getStringKey("validator_days_range_header"), locale.getStringKey("validator_days_range_body"));
		     
		 throw new ValidatorException(msg);
		
		}
		
	}

}
