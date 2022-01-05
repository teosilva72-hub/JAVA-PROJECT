// VALIDATION DEFINITIONS

const SPANISH = "es_ES";
const ARGENTINE_SPANISH = "es_AR";
const COLOMBIAN_SPANISH = "es_CO"; 
const MEXICAN_SPANISH = "es_MX";
const AMERICAN_ENGLISH = "en_US";
const BRAZILIAN_PORTUGUESE = "pt_BR";

$(function () {

var validation = document.forms.validation;

  for (messages of validation)
	 window[messages.name] = messages.value
	
	// ----------------------------------------------------------
	
	 if(language == SPANISH || language == ARGENTINE_SPANISH || language == COLOMBIAN_SPANISH || language == MEXICAN_SPANISH)
        spanishValidationMessages();

		else if(language == AMERICAN_ENGLISH)
        englishValidationMessages();

    	else portugueseValidationMessages();

  // --------------------------------------------------------------
  
    validationTemplate3("#vbv-form", requiredEquipmentMsg, requiredMonthMsg, requiredYearMsg);
		
    //Validate elements on change value
    //In this case check if the element is valid
    validateOnChange('#equip');
    validateOnChange('#month');
    validateOnChange('#year');
	    
    //Remove validation icons
    //click reset button action		 
    removeValidationIcon("reset-btn", 'equip');
    removeValidationIcon("reset-btn", 'month');
	removeValidationIcon("reset-btn", 'year');

    //Reset validation form
    //click reset button action
    resetFormValidation("#vbv-form", "reset-btn");

   // Preventing form resubmission
	window.history.replaceState('','',window.location.href)
		
	
});

