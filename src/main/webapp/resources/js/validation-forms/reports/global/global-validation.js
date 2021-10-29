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

    //Create / Define multiselect
    defineMultiselect('#equips', equipmentSelectMsg);      

    $.validator.methods.date = function (value, element) {
        //This is not ideal but Chrome passes dates through in ISO1901 format regardless of locale 
        //and despite displaying in the specified format.

        let a = value.split("/");
        let date = new Date(a[2], a[1] - 1, a[0])
        let timestamp = Date.parse(date);
        let range = true;
        let id = element.id

        if (id.endsWith("-start")) {
            let end = $(`#${id.substr(0, id.length - 6)}-end`).val()
            let endSplit = end.split("/")
            if (end)
                range = timestamp <= Date.parse(`${endSplit[2]}/${endSplit[1]}/${endSplit[0]}`)
        } else {
            let start = $(`#${id.substr(0, id.length - 4)}-start`).val()
            let startSplit = start.split("/")
            if (start)
                range = timestamp >= Date.parse(`${startSplit[2]}/${startSplit[1]}/${startSplit[0]}`)
        }

        let check = date.toLocaleDateString() == value && timestamp < Date.now() && range

        return this.optional(element) || check;
    }

    //Language
   $('.datepicker').datepickerLocale(language);

   validationTemplate9("#report-form", '#dateStart', requiredEquipmentsMsg, requiredDateStartMsg, requiredDateEndMsg, requiredValidDateMsg);

	//Clean Form validation on close modal
    cleanValidationOnModalClose("#report-form", '#modalReportOptions');

    //Reset validation form
    //click reset button action
    resetFormValidation("#report-form", "reset-btn");

	
});