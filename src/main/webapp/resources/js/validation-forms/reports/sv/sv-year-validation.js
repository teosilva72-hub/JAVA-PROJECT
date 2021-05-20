
//VALIDATION DEFINITIONS

$(function () {

	var validation = document.forms.validation;

    for (messages of validation)
	window[messages.name] = messages.value

    //Form validation
	validationTemplate5('#report-form', requiredEquipmentsMsg, requiredYearMsg, requiredStartMonthMsg, requiredEndMonthMsg);

//Validate on change value
validateOnChange('#equip');
validateOnChange('#year');
validateOnChange('#start_month');
validateOnChange('#end_month');

//Reset Fields on close modal
resetFieldOnModalClose('#modalReportOptions', 'equip');
resetFieldOnModalClose('#modalReportOptions', 'year');
resetFieldOnModalClose('#modalReportOptions', 'start_month');
resetFieldOnModalClose('#modalReportOptions', 'end_month');

//Remove validation icons
//click reset button action
removeValidationIcon("reset-btn", 'equip');
removeValidationIcon("reset-btn", 'year');
removeValidationIcon("reset-btn", 'start_month');
removeValidationIcon("reset-btn", 'end_month');

//Clean Form validation on close modal
cleanValidationOnModalClose("#report-form", '#modalReportOptions');

//Reset validation form
//click reset button action
resetFormValidation("#report-form", "reset-btn");
	
});