
//VALIDATION DEFINITIONS

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
  

	//Form validation
	validationTemplate3('#report-form', requiredEquipmentsMsg,
		requiredMonthMsg, requiredYearMsg);

	//Validate elements on change value
	//In this case check if the element is valid
	validateOnChange('#equip');
	validateOnChange('#month');
	validateOnChange('#year');

	//Reset Fields on close modal
	resetFieldOnModalClose('#modalReportOptions', 'equip');
	resetFieldOnModalClose('#modalReportOptions', 'month');
	resetFieldOnModalClose('#modalReportOptions', 'year');

	//Remove validation icons
	//click reset button action		 
	removeValidationIcon("reset-btn", 'equip');
	removeValidationIcon("reset-btn", 'month');
	removeValidationIcon("reset-btn", 'year');

	//Clean Form validation on close modal
	cleanValidationOnModalClose("#report-form", '#modalReportOptions');

	//Reset validation form on reset click button       
	resetSpecialFormValidation("#report-form", "reset-btn");


});