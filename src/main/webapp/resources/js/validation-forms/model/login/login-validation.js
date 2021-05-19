
function validateLogin(usernameRequired, usernameMinLenght, usernameMaxLenght,
 passwordRequired, passwordMinLenght, passwordMaxLenght) {
	
	$('#form-login').validate({
		rules:{
			username:{
				required: true,	
				minlength: 4,
				maxlength: 80
			},	
			password:{
				required: true,	
				minlength: 3,
				maxlength: 15
			}	   
	    },
	    
	     messages: {
            username: {
                required: usernameRequired,
                minlength: usernameMinLenght,
                maxlength: usernameMaxLenght
            },

         password: {
                required: passwordRequired,
                minlength: passwordMinLenght,
                maxlength: passwordMaxLenght
            }
	      },
          
          errorClass : "error-login",
          // use highlight and unhighlight
          highlight: function(element, errorClass) {
           $(element).addClass(errorClass);
           $(element.form).find("label[for=" + element.id + "]").addClass(errorClass);
           $(element.form).find("select").addClass('invalid');
           $(element.form).find("input").addClass('invalid');
           },
          unhighlight: function(element, errorClass) {
           $(element).removeClass(errorClass);
           $(element.form).find("label[for=" + element.id + "]").removeClass(errorClass);
           $(element.form).find("select").removeClass('invalid');
           $(element.form).find("input").removeClass('invalid');           
           $(element.form).find("select").addClass('valid');
           $(element.form).find("input").addClass('valid');
           
          } 	
	});	
	
  }
