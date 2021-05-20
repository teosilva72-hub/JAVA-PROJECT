

//VALIDATION DEFINITIONS

$(function () {

//Get equipments from backing bean
//Create array
var equipsArray = new Array();

var validation = document.forms.validation;

for (messages of validation)
	if (messages.name == "")
		equipsArray.push(messages.value);
	else window[messages.name] = messages.value

//Call method to set checkbox all checked
checkedByDefault('#equips', equipsArray);

//Create / Define multiselect
defineMultiselect('#equips', equipmentSelectMsg);

//Validation Form			   
validationTemplatel4("#report-form", requiredEquipmentsMsg, requiredPeriodMsg, requiredMonthMsg, requiredYearMsg);

//Validate elements on change value
//In this case check if the element is valid
validateOnChange('#equips');
validateOnChange('#periods');
validateOnChange('#month');
validateOnChange('#year');

//Reset Fields on close modal
resetFieldMultiselectOnModalClose('#modalReportOptions', 'equips', '#{satLabels.sat_reports_select_equipments}', equipsArray);
resetFieldOnModalClose('#modalReportOptions', 'periods');
resetFieldOnModalClose('#modalReportOptions', 'month');
resetFieldOnModalClose('#modalReportOptions', 'year');

//Remove validation icons
//click reset button action	
removeValidationMultiselectIcon("reset-btn", 'equips', '#{satLabels.sat_reports_select_equipments}', equipsArray);
removeValidationIcon("reset-btn", 'periods');
removeValidationIcon("reset-btn", 'month');
removeValidationIcon("reset-btn", 'year');

//Clean Form validation on close modal
cleanValidationOnModalClose("#report-form", '#modalReportOptions');

//Reset validation form
//click reset button action
resetFormValidation("#report-form", "reset-btn");

});
