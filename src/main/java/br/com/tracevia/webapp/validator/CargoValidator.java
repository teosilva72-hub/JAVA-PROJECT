package br.com.tracevia.webapp.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.MessagesUtil;

@FacesValidator(value="cargoValidator")
public class CargoValidator implements Validator {

		private static final String CARGO_PATTERN = "^[a-zA-Z\\s]+$";	
		
		LocaleUtil locale;
	    MessagesUtil message;
		
		private Pattern pattern;
		private Matcher matcher;
		
		public CargoValidator(){
			  locale = new LocaleUtil();
			  pattern = Pattern.compile(CARGO_PATTERN);
			  locale.getResourceBundle(LocaleUtil.MESSAGES_VALIDATOR);	
			  message = new MessagesUtil();
		}
		
		public void validate(FacesContext context, UIComponent component,
				Object value) throws ValidatorException {
								
			matcher = pattern.matcher(value.toString());
			if(!matcher.matches()){
				
				FacesMessage msg = message.ErrorMessageValidator(locale.getStringKey("validator_job_position_message_header"), locale.getStringKey("validator_job_position_message_body"));
			
				throw new ValidatorException(msg);
			}
		 }
		
}