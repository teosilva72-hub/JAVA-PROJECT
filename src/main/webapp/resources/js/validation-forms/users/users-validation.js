/*Validation*/

/*$(function(){
	
	
	//M�todo verificar se Campo Nome esta em Branco
    jQuery.validator.addMethod("blank_nome", function(value, element) {	    	
    	var nome = $('#id_in').val();	    	
    	if( nome == "" ){
    		$('#id_in').removeClass('valid').addClass('invalid');
    	    $('#id_in').parent().find('#id_in-error').fadeOut(500).fadeIn(500);   		
    		return false;
    	}else return true;
    	
    }, " * Campo Obrigatorio");		
	
}); */

$(document).ready(function(){
	$('#form-register').validate({
		rules:{
			username:{
				required: true,	
				minlength: 3,
				maxlength: 10
			},	
	    password:{
		required: true,	
		minlength: 6,
		maxlength: 15
	   }
	    },
	     messages: {
            username: {
                required: " #{msg.validator_name_message_header}",
                minlength: "Username must be at least 8 characters",
                uniqueUserName: "This Username is taken already"
            }
	      }
	});	
	
  });

