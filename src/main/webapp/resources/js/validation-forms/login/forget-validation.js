
//VALIDATION DEFINITIONS


$(function() {

    let validation = document.forms.validation;

    for (messages of validation)
        window[messages.name] = messages.value
        
    //Validate Email    
    validateEmail('#email', '#email-error', validatorEmail);
      
    //Validate Email form  
    validateEmailForm('#form-reset', emailMsg, emailMaxLengthMsg);
               
   //Validate elements on change value
   //In this case check if the element is valid
   validateOnChange('#email');                

});

/***********************************/

//cancela a são do botão enter
 document.addEventListener("keydown", function(e) {
    if(e.keyCode === 13) {
      e.preventDefault(); 
    }
  });

 /***********************************/
 
 //RESET MESSAGE DISPLAY	
function hideEmailInfoMessage() {
    setTimeout(function () {
        $('#emailInfo').hide();
    }, 5000);
}

//SHOW MESSAGE DISPLAY	
function showEmailInfoMessage() {
    $('#emailInfo').show();
}

/********************************/

//RESET MESSAGE DISPLAY	
function hideEmailRecoveryErrorMessage() {
    setTimeout(function () {
        $('#emailRecoveryError').hide();
    }, 5000);
}

//SHOW MESSAGE DISPLAY	
function showEmailRecoveryErrorMessage() {
    $('#emailRecoveryError').show();
}

/********************************/

//RESET MESSAGE DISPLAY	
function hideEmailRecoverySendErrorMessage() {
    setTimeout(function () {
        $('#emailRecoverySendError').hide();
    }, 5000);
}

//SHOW MESSAGE DISPLAY	
function showEmailRecoverySendErrorMessage() {
    $('#emailRecoverySendError').show();
}

/********************************/

//RESET MESSAGE DISPLAY	
function hideConnectionErrorMessage() {
    setTimeout(function () {
        $('#connectionError').hide();
    }, 5000);
}

//SHOW MESSAGE DISPLAY	
function showConnectionErrorMessage() {
    $('#connectionError').show();
}

/********************************/

