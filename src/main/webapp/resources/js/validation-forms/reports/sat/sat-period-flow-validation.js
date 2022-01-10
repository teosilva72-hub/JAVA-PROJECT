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
		if (messages.name == "")
			equipsArray.push(messages.value);
		else window[messages.name] = messages.value
	
	// ----------------------------------------------------------
	
	 if(language == SPANISH || language == ARGENTINE_SPANISH || language == COLOMBIAN_SPANISH || language == MEXICAN_SPANISH)
        spanishValidationMessages();

		else if(language == AMERICAN_ENGLISH)
        englishValidationMessages();

    	else portugueseValidationMessages();
 
   // --------------------------------------------------------------
			
	//Create / Define multiselect
	defineMultiselect('#equips', equipmentSelectMsg);
		
	//Validation Form			   
	validationTemplate4("#report-form", requiredEquipmentsMsg, requiredPeriodMsg, requiredMonthMsg, requiredYearMsg);
		
	validateInputFieldOnChange(); // Validate Input
    validateSelectFieldOnChange() // Validate Select
	validateCheckboxFieldOnChange(); // Validate Checkbox Field
	
	   //Remove validation icons
    //click reset button action		 

	removeValidationMultiselectIcon("reset-btn", 'equips')
    removeValidationIcon("reset-btn", 'periods');
    removeValidationIcon("reset-btn", 'month');
	removeValidationIcon("reset-btn", 'year');

	//Clean Form validation on close modal
    cleanModalOnClose("#report-form", '#modalReportOptions');

    //Reset validation form
    //click reset button action
    resetFormValidation("#report-form", "reset-btn");

});
