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


@FacesValidator(value="alphaNumericValidator")
public class AlphaNumericValidator implements Validator {
	
	private static final String APLHA_PATTERN = "^[a-zA-Z0-9]+$";	
	 
	LocaleUtil locale;
	MessagesUtil message;
	
	private Pattern pattern;
	private Matcher matcher;	
	
	public AlphaNumericValidator() {
		    locale = new LocaleUtil();
		    pattern = Pattern.compile(APLHA_PATTERN);
			locale.getResourceBundle(LocaleUtil.MESSAGES_VALIDATOR);	
			message = new MessagesUtil();
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
						
		matcher = pattern.matcher(value.toString());
		if(!matcher.matches()){
						
			FacesMessage msg = message.ErrorMessageValidator(locale.getStringKey("validator_alpha_numeric_message_header"), locale.getStringKey("validator_alpha_numeric_message_body"));
			
			throw new ValidatorException(msg);
		}
	 }	
  }
