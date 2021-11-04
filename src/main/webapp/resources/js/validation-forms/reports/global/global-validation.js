//VALIDATION DEFINITIONS

$(function () {
	
	let maxDays = 60; // Máximo de dias para pesquisa
	
	// Get equipments from backing bean
    // Create array
    let equipsArray = new Array();
   
    let validation = document.forms.validation;

    for (messages of validation)
        if (messages.name == "")
            equipsArray.push(messages.value);

       else window[messages.name] = messages.value

     // Create / Define multiselect
     defineMultiselect('#equips', equipmentSelectMsg);

    /* ----------------------------------------------------------------------------------------------------- */     

    $.validator.addMethod("date", function (value, element) {
       
        // This is not ideal but Chrome passes dates through in ISO1901 format regardless of locale 
        // and despite displaying in the specified format.

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
    }, "foda-se");

    /* ----------------------------------------------------------------------------------------------------- */ 

     $.validator.addMethod("searchDays", function (value, element) {
	
	    let id = element.id

		let max = maxDays;
		
		
		  if (id.endsWith("-start"))
		      let startDate = $(id).val();

          else if(id.endsWith("-end"))
		      let endDate = $(id).val();
		
		let dataInicio = toDate(startDate);
		let dataFim = toDate(endDate);

		let daysDiff = (dataFim - dataInicio) / (1000 * 3600 * 24);

		if (daysDiff > max) {

			$(dateEnd).removeClass('valid').addClass('invalid');
			$(dateError).parent().find('.error').fadeIn(500);
			return false;
		}
		else {
			$(dateEnd).removeClass('invalid').addClass('valid'); return true;
		}
	}, "Fuck you");




    //Language
   $('.datepicker').datepickerLocale(language);

   validationTemplate9("#report-form", '#dateStart', requiredEquipmentsMsg, requiredDateStartMsg, requiredDateEndMsg, requiredValidDateMsg);

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