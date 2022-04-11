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
	validationTemplate4("#form", requiredEquipmentsMsg, requiredPeriodMsg, requiredMonthMsg, requiredYearMsg);
		
	validateInputFieldOnChange(); // Validate Input
    validateSelectFieldOnChange() // Validate Select
	validateCheckboxFieldOnChange(); // Validate Checkbox Field
	
	   //Remove validation icons
    //click reset button action		 

	removeValidationMultiselectIcon("reset-btn", 'equips')
    removeValidationIcon("reset-btn", 'periods');
    removeValidationIcon("reset-btn", 'month');
	removeValidationIcon("reset-btn", 'year');
	
    //Reset validation form
    //click reset button action
    resetFormValidation("#form", "reset-btn");
   
});

// ---------------------------------------------------------------------

/** Show info modal after validation 
 *  
 * @author Wellington da Silva : 2022-01-10
 * @summary Method to Show info modal if form submit is valid
 * @since version 1.0
 * @version 1.1 
 * @description Show info modal if is a valid form submit
 * @copyright Tracevia S/A 2022 
 * @returns {void}
**/

function isValidSubmit(){
	
	valid = $("#form").validate();
	
	if(valid){
		
		$('#modalInfo').modal('show');	
		PF('poll').start();
		console.log("executou");
	}
			
}

// ---------------------------------------------------------------------

/** Set Scroll on bottom
 *  
 * @author Wellington da Silva : 2022-04-07
 * @summary Method to set vertical scroll bar on bottom
 * @since version 1.0
 * @version 1.1 
 * @description Set vertical scroll bar on bottom
 * @copyright Tracevia S/A 2022 
 * @returns {void}
 *
**/

function scrollOnBottom(){
	let $textArea = $('#display');
	$textArea.scrollTop($textArea[0].scrollHeight);	
}

// ---------------------------------------------------------------------

