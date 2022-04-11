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
       
    //Language
    $('.datepicker').datepickerLocale(language);
    
   //Validation Methods - needs to use
   //Date Validation
   maxDateCheck('#dateStart', '#dateEnd', '#dateEnd-error', maxDateMsg);
   greaterThanEndDate('#dateStart', '#dateEnd', '#dateEnd-error', greatherThanMsg);
        
    validationTemplate7('#report-form', '#dateStart', requiredEquipmentsMsg, 
        requiredPeriodsMsg, requiredDateStartMsg, requiredDateEndMsg, requiredValidDateMsg);

    //Validate on change value
    validateOnChange('#equips');
    validateOnChange('#periods');
    validateOnChange('#dateStart');
    validateOnChange('#dateEnd');

    //Reset Fields on close modal	   
    resetFieldMultiselectOnModalClose('#modalReportOptions', 'equips', equipmentSelectMsg, equipsArray);
    resetFieldOnModalClose('#modalReportOptions', 'periods');
    resetFieldOnModalClose('#modalReportOptions', 'dateStart');
    resetFieldOnModalClose('#modalReportOptions', 'dateEnd');

    //Remove validation icons
    //click reset button action	
    removeValidationMultiselectIcon("reset-btn", 'equips', equipmentSelectMsg, equipsArray); 
    removeValidationIcon("reset-btn", 'periods');
    removeValidationIcon("reset-btn", 'dateStart');
    removeValidationIcon("reset-btn", 'dateEnd');

    //Clean Form validation on close modal
    cleanValidationOnModalClose("#report-form", '#modalReportOptions');

    //Reset validation form
    //click reset button action
    resetFormValidation("#report-form", "reset-btn");

});