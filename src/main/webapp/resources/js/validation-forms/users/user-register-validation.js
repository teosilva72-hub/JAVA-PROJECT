
//VALIDATION DEFINITIONS

$(function () {

    var validation = document.forms.validation;

    for (messages of validation)
        window[messages.name] = messages.value


    validateName('#fullname', '#fullname-error', validatorName);
    validateJobPosition('#jobPosition', '#jobPosition-error', validatorJobPosition);
    validateEmail('#email', '#email-error', validatorEmail);
    validateUserName('#username', '#username-error', validatorUsername);

    validateUserForm('#form-register', validatorMinLengthMsg, validatorMaxLengthMsg, fullNameMsg, jobPositionMsg,
        emailMsg, usernameMsg, permissionMsg);            

    //Validate elements on change value
    //In this case check if the element is valid
    validateOnChange('#fullname');
    validateOnChange('#jobPosition');
    validateOnChange('#email');
    validateOnChange('#username');
    validateOnChange('#permissions');
      
    //Remove validation icons
    //click reset button action		 
    removeValidationIcon("reset-btn", 'fullname');
    removeValidationIcon("reset-btn", 'jobPosition');
    removeValidationIcon("reset-btn", 'email');
    removeValidationIcon("reset-btn", 'username');
    removeValidationIcon("reset-btn", 'permissions');
  
    //Reset validation form
    //click reset button action
    resetFormValidation("#form-register", "reset-btn");

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
function hideInfoMessage() {  
    $('#info').hide();   
}

//SHOW MESSAGE DISPLAY	
function showInfoMessage() {
    $('#info').show();
}

/*******************************/

//RESET MESSAGE DISPLAY	
function hideUsernameErrorMessage() {
    setTimeout(function () {
        $('#usernameError').hide();
    }, 5000);
}

//SHOW MESSAGE DISPLAY	
function showUsernameErrorMessage() {
    $('#usernameError').show();
}

/*******************************/

//RESET MESSAGE DISPLAY	
function hideEmailErrorMessage() {
    setTimeout(function () {
        $('#emailError').hide();
    }, 5000);
}

//SHOW MESSAGE DISPLAY	
function showEmailErrorMessage() {
    $('#emailError').show();
}

/*******************************/
