
//VALIDATION DEFINITIONS

$(function() {

    let validation = document.forms.validation;

    for (messages of validation)
        window[messages.name] = messages.value
      
    validateLogin('#form-login', usernameMsg, usernameMinLengthMsg, usernameMaxLengthMsg, passwordMsg, 
        passwordMinLengthMsg, passwordMaxLengthMsg);        
       
   //Validate elements on change value
   //In this case check if the element is valid
   validateOnChange('#username');
   validateOnChange('#password');                            
       
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
function hideNotFoundMessage() {
    setTimeout(function () {
        $('#notFound').hide();
    }, 5000);
}

//SHOW MESSAGE DISPLAY	
function showNotFoundMessage() {
    $('#notFound').show();
}

/********************************/

//RESET MESSAGE DISPLAY	
function hideLoginErrorMessage() {
    setTimeout(function () {
        $('#loginError').hide();
    }, 5000);
}

//SHOW MESSAGE DISPLAY	
function showLoginErrorMessage() {
    $('#loginError').show();
}

/********************************/

//RESET MESSAGE DISPLAY	
function hideInactiveErrorMessage() {
    setTimeout(function () {
        $('#inactiveError').hide();
    }, 5000);
}

//SHOW MESSAGE DISPLAY	
function showInactiveErrorMessage() {
    $('#inactiveError').show();
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

       //RESET MESSAGE DISPLAY	
        function hideLogoutMessage() {
          setTimeout(function () {
            $('#logout').hide();
         }, 5000);
       }

     //SHOW MESSAGE DISPLAY	
      function showLogoutMessage() {
      $('#logout').show();
      }

     /********************************/
