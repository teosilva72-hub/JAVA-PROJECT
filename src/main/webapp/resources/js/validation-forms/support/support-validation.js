
//VALIDATION DEFINITIONS

$(function () {

    let validation = document.forms.validation;
    
    for (messages of validation)
        window[messages.name] = messages.value

	 validateEmail('#email', '#email-error', emailValidationMsg);

 	 validateSupportForm('#submit', emailValidationMsg, emailMsg, categoriaMsg, assuntoMsg, messageMsg)

    //Validate elements on change value
    //In this case check if the element is valid
    validateOnChange('#email');
    validateOnChange('#category');	
    validateOnChange('#subject');
	validateOnChange('#message');

    //click reset button action		 
    removeValidationIcon("reset-btn", 'email');
    removeValidationIcon("reset-btn", 'category');
    removeValidationIcon("reset-btn", 'subject');
	removeValidationIcon("reset-btn", 'message');
  	       
    //Reset validation form
    //click reset button action
   // resetFormValidation("#register-equip-form", "reset-btn");	
    resetFormValidation("#submit", "reset-btn");

  // Preventing form resubmission
	window.history.replaceState('','',window.location.href)
    
});


$( opt =>{
	
	let option = $('#category')
	
	document.getElementById('others').style.display = 'none';
	

	
	option.on('click', f=>{
	
	if(option.val() == 'Others'){
	document.getElementById('email').style.display = 'block';
    document.getElementById('category').style.display = 'block';
    document.getElementById('others').style.display = 'block';
    document.getElementById('subject').style.display = 'block';
    document.getElementById('message').style.display = 'block';
	console.log('teste1')
	}
	else { 
	document.getElementById('email').style.display = 'block';
    document.getElementById('category').style.display = 'block';
    document.getElementById('others').style.display = 'none';
	document.getElementById('subject').style.display = 'block';
    document.getElementById('message').style.display = 'block';
	console.log('teste2')
	}
});
});


$(function getTr(){
	var table = $('#support-table').DataTable({
		language: {
			"search": "",
			searchPlaceholder: "Buscar"
		},		
		"select": true,
		"autoWidth": true,			  	   	
		"scrollY": "60vh",
		"paging": false,
		"bInfo" : false
		
		})
	});
	