//VALIDATION DEFINITIONS

$(function () {

    //Get equipments from backing bean
    //Create array
    var equipsArray = new Array();
    var veh = [1, 2, 3, 4, 5];

    var validation = document.forms.validation;

    for (messages of validation)
        if (messages.name == "")
            equipsArray.push(messages.value);
       else window[messages.name] = messages.value

    //Call method to set checkbox all checked
    checkedByDefault('#equips', equipsArray);
    checkedByDefault('#vehicles', veh);

    //Create / Define multiselect
    defineMultiselect('#equips', equipmentSelectMsg);
    defineMultiselect('#vehicles', vehicleSelectMsg);
    
    //Language
    $('.datepicker').datepickerLocale(language);
    
   //Validation Methods - needs to use
   //Date Validation
   maxDateCheck('#dateStart', '#dateEnd', '#dateEnd-error', maxDateMsg);
   greaterThanEndDate('#dateStart', '#dateEnd', '#dateEnd-error', greatherThanMsg);

    validationTemplate2('#report-form', '#dateStart', requiredEquipmentsMsg, requiredVehiclesMsg,
        requiredPeriodsMsg, requiredDateStartMsg, requiredDateEndMsg, requiredValidDateMsg);

    //Validate on change value
    validateOnChange('#equips');
    validateOnChange('#vehicles');
    validateOnChange('#periods');
    validateOnChange('#dateStart');
    validateOnChange('#dateEnd');

    //Reset Fields on close modal	   
    resetFieldMultiselectOnModalClose('#modalReportOptions', 'equips', equipmentSelectMsg, equipsArray);
    resetFieldMultiselectOnModalClose('#modalReportOptions', 'vehicles', vehicleSelectMsg, veh);
    resetFieldOnModalClose('#modalReportOptions', 'periods');
    resetFieldOnModalClose('#modalReportOptions', 'dateStart');
    resetFieldOnModalClose('#modalReportOptions', 'dateEnd');

    //Remove validation icons
    //click reset button action	
    removeValidationMultiselectIcon("reset-btn", 'equips', equipmentSelectMsg, equipsArray);
    removeValidationMultiselectIcon("reset-btn", 'vehicles', vehicleSelectMsg, veh);
    removeValidationIcon("reset-btn", 'periods');
    removeValidationIcon("reset-btn", 'dateStart');
    removeValidationIcon("reset-btn", 'dateEnd');

    //Clean Form validation on close modal
    cleanValidationOnModalClose("#report-form", '#modalReportOptions');

    //Reset validation form
    //click reset button action
    resetFormValidation("#report-form", "reset-btn");

});