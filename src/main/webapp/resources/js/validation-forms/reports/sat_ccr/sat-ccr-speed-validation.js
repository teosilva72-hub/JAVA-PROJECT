//VALIDATION DEFINITIONS

$(function () {

var validation = document.forms.validation;

for (messages of validation)
	window[messages.name] = messages.value

 // Language
$('.datepicker').datepickerLocale(language);

//Validation Methods - needs to use
//Date Validation
maxDateCheck('#dateStart', '#dateEnd', '#dateEnd-error', maxDateMsg);
greaterThanEndDate('#dateStart', '#dateEnd', '#dateEnd-error', greatherThanMsg);

validationTemplate1("#report-form", '#dateStart', requiredEquipmentsMsg,
	requiredPeriodsMsg, requiredDateStartMsg, requiredDateEndMsg, requiredValidDateMsg);
	
	 //Validate elements on change value
 //In this case check if the element is valid
 validateOnChange('#equip');
 validateOnChange('#periods');
 validateOnChange('#dateStart');
 validateOnChange('#dateEnd');
 
 //Reset Fields on close modal
 resetFieldOnModalClose('#modalReportOptions', 'equip');
 resetFieldOnModalClose('#modalReportOptions', 'periods');
 resetFieldOnModalClose('#modalReportOptions', 'dateStart');
 resetFieldOnModalClose('#modalReportOptions', 'dateEnd');
 
 //Remove validation icons
 //click reset button action		 
 removeValidationIcon("reset-btn", 'equip');
 removeValidationIcon("reset-btn", 'periods');
 removeValidationIcon("reset-btn", 'dateStart');
 removeValidationIcon("reset-btn", 'dateEnd');

 //Clean Form validation on close modal
 cleanValidationOnModalClose("#report-form", '#modalReportOptions');

 //Reset validation form
 //click reset button action
 resetFormValidation("#report-form", "reset-btn");

});