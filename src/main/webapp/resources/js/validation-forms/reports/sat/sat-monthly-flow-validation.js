
//VALIDATION DEFINITIONS

$(function () {

	var validation = document.forms.validation;

	for (messages of validation)
		window[messages.name] = messages.value

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
	resetFormValidation("#report-form", "reset-btn");


});