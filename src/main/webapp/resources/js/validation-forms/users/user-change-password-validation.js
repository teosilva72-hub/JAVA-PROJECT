//VALIDATION DEFINITIONS

$(function () {

    var validation = document.forms.validation;

    for (messages of validation)
        window[messages.name] = messages.value

    validateChangePassword('#change-password-form', '#newPassword', equalsToMsg, validatorMinLengthMsg, validatorMaxLengthMsg,
     passwordMsg, newPasswordMsg, confirmationMsg);            

    //Validate elements on change value
    //In this case check if the element is valid
    validateOnChange('#password');
    validateOnChange('#newPassword');
    validateOnChange('#confPassword');
 
      
    //Remove validation icons
    //click reset button action		 
    removeValidationIcon("reset-btn", 'password');
    removeValidationIcon("reset-btn", 'newPassword');
    removeValidationIcon("reset-btn", 'confPassword');
     
    //Reset validation form
    //click reset button action
    resetFormValidation("#change-password-form", "reset-btn");

});

/***********************************/

//RESET MESSAGE DISPLAY	
function hideSuccessMessage() {
    setTimeout(function () {
        $('#success').hide();
    }, 5000);
}

//SHOW MESSAGE DISPLAY	
function showSuccessMessage() {
    $('#success').show();
}

/********************************/

//RESET MESSAGE DISPLAY	
function hideErrorMessage() {
    setTimeout(function () {
        $('#error').hide();
    }, 5000);
}

//SHOW MESSAGE DISPLAY	
function showErrorMessage() {
    $('#error').show();
}

/*******************************/

//RESET MESSAGE DISPLAY	
function hideChangeErrorMessage() {
    setTimeout(function () {
        $('#passwordError').hide();
    }, 5000);
}

//SHOW MESSAGE DISPLAY	
function showChangeErrorMessage() {
    $('#passwordError').show();
}


/*******************************/