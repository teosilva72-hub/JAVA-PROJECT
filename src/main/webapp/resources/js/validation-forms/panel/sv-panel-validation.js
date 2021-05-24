
//VALIDATION DEFINITIONS

$(function () {

    var validation = document.forms.validation;
    
    for (messages of validation)
        window[messages.name] = messages.value

        validationTemplate6("#panel", requiredEquipmentMsg);

        //Validate on change value
        validateOnChange('#equip'); 
        
});