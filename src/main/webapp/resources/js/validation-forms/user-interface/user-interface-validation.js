	////////////////////////////////////////////////////
	//// CHECK IF ID EXISTS
	////////////////////////////////////////////////////	

function userInterfaceformValidation(form, equips_message, id_message, number_message, max_length_message){
		
       $(form).validate({	                 	    	                   
		         rules: {
		          equips: {
	                 required: true  
	                                                
                   }, equipId : {                  
                     required: true,
                     number: true                                                                  
                   },
                    equipName: {
                     maxlength: 10
                    }                                 
		         },
		         
		          messages: {
						
		              equips: { required: equips_message },
		              equipId: { required: id_message,
		                         number: number_message
		               }, equipName: {
		                     maxlength: max_length_message
		               }	             
                    },		       
		            
		            errorClass : "error",
                    validClass: "success",                  
		            errorElement: "label", 

                    errorPlacement: function ( error, element ) {
	                  //Place elements for place errors	 
                
                    },
								
					success: function ( label, element ) {	
											 	
					  //If no have errors set check success status	
					 //Show span validation icon
                     $(element.form).find("span[for="+ element.id +"]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');
									
				     //FontAwesome Icon Check for success
                     $(element.form).find("span[for="+element.id+"]").html("<i class='fa fa-check success'></i>");
        		
				     }, 		
                         
		              // use highlight and unhighlight
		             highlight: function(element, errorClass, validClass) {	        
                     $(element.form).find("label[for=" + element.id + "]").addClass(errorClass);
                  
                     $(element.form).find("input[id="+element.id+"]").removeClass('valid').addClass('invalid');
                     $(element.form).find("select[id="+element.id+"]").removeClass('valid').addClass('invalid');         
                                    
                     //Show span validation icon
                     $(element.form).find("span[for="+ element.id +"]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');

                     //FontAwesome Icon Times for error
                     $(element.form).find("span[for="+element.id+"]").html("<i class='fa fa-times error'></i>");
                 		           
                    },

		          unhighlight: function(element, errorClass, validClass) {		       
                  $(element.form).find("label[for=" + element.id + "]").removeClass(errorClass);  
                         
                   $(element.form).find("input[id="+element.id+"]").removeClass('invalid').addClass('valid');             
                  $(element.form).find("select[id="+element.id+"]").removeClass('invalid').addClass('valid');
                
                  //FontAwesome Icon Check for success
                  $(element.form).find("span[for="+element.id+"]").html("<i class='fa fa-check success'></i>");

                 
                 }                  
		     });
		 	    			     
	}//   End Validation Form
	
	
	function userInterfaceEditformValidation(form, max_length_message){
		
       $(form).validate({	                 	    	                   
		         rules: {		         
                    equipNameEdit: {
                     maxlength: 10
                    }                                 
		         },
		         
		          messages: {
						
		              equipNameEdit: {
		                     maxlength: max_length_message
		               }	             
                    },		       
		            
		            errorClass : "error",
                    validClass: "success",                  
		            errorElement: "label", 

                    errorPlacement: function ( error, element ) {
	                  //Place elements for place errors	 
                
                    },
								
					success: function ( label, element ) {	
											 	
					  //If no have errors set check success status	
					 //Show span validation icon
                     $(element.form).find("span[for="+ element.id +"]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');
									
				     //FontAwesome Icon Check for success
                     $(element.form).find("span[for="+element.id+"]").html("<i class='fa fa-check success'></i>");
        		
				     }, 		
                         
		              // use highlight and unhighlight
		             highlight: function(element, errorClass, validClass) {	        
                     $(element.form).find("label[for=" + element.id + "]").addClass(errorClass);
                  
                     $(element.form).find("input[id="+element.id+"]").removeClass('valid').addClass('invalid');
                     $(element.form).find("select[id="+element.id+"]").removeClass('valid').addClass('invalid');         
                                    
                     //Show span validation icon
                     $(element.form).find("span[for="+ element.id +"]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');

                     //FontAwesome Icon Times for error
                     $(element.form).find("span[for="+element.id+"]").html("<i class='fa fa-times error'></i>");
                 		           
                    },

		          unhighlight: function(element, errorClass, validClass) {		       
                  $(element.form).find("label[for=" + element.id + "]").removeClass(errorClass);  
                         
                   $(element.form).find("input[id="+element.id+"]").removeClass('invalid').addClass('valid');             
                  $(element.form).find("select[id="+element.id+"]").removeClass('invalid').addClass('valid');
                
                  //FontAwesome Icon Check for success
                  $(element.form).find("span[for="+element.id+"]").html("<i class='fa fa-check success'></i>");

                 
                 }                  
		     });
		 	    			     
	}//   End Validation Form
		
	/////////////////////////////////////////////////
	///// VALIDATE FIELDS BY ID ON CHANGE
	////////////////////////////////////////////////
	function validateOnChange(id){
	 $(id).on('change', function(){
	        if($(id).valid()) 
	            $(id).removeClass('invalid');
	            	            
	            else  $(id).removeClass('valid');
	           	        
	    })
      } // End ValidateOnChange
      
   ////////////////////////////////////////////////
   //// VALIDATE FIELDS BY ID ON CHANGE
  ////////////////////////////////////////////////
  
  ////////////////////////////////////////////////
 //// RESET FIELD ON MODAL CLOSE
 ////////////////////////////////////////////////
function resetFieldOnModalClose(modalId, elem){
	
	   $(modalId).on("hide.bs.modal", function() {
	   
	    //Hide span by class  
	    $('span[for='+elem+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
                 
          //Reset Field 	     
	      resetFieldValue('#'+elem);	      
	      
})

} //Reset on Modal Close

 ////////////////////////////////////////////////
 ////////RESET FIELD ON MODAL CLOSE
 ////////////////////////////////////////////////
 
  ////////////////////////////////////////////////
 //// RESET FORM VALIDATION ICON
 ////////////////////////////////////////////////
 function removeValidationIcon(btn, elem){ 
	
	  $('.'+btn).click(function() {
					
       //Hide span by class  
	    $('span[for='+elem+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    	     
	      //Reset Field 	     
	     resetFieldValue('#'+elem);
    });
 } //End ResetValidationForm
	
 ////////////////////////////////////////////////
 ///// RESET FORM VALIDATION ICON
 ////////////////////////////////////////////////	
 
 ////////////////////////////////////////////////
 //// CLEAN MODAL ON CLOSE
 ////////////////////////////////////////////////
function cleanValidationOnModalClose(form, modalId){
	
	   $(modalId).on("hide.bs.modal", function() {
	   
	    //Hide span by class        				
		$(form).validate().resetForm();	
})

} //Reset on Modal Close


 ////////////////////////////////////////////////
 ////////CLEAN MODAL ON CLOSE
 ////////////////////////////////////////////////
 
 ///////////////////////////////////////
 ////////// RESET FORM VALIDATION
 //////////////////////////////////////
 
 function resetFormValidation(form, btn){
 //Reset Form validation
 
   $('.'+btn).click(function() {
   
	  var validator = $(form).validate();
	  validator.resetForm();
	  
	  })
   }
	       
 ///////////////////////////////////////
 ////////// RESET FORM VALIDATION
 //////////////////////////////////////
 
 /////////////////////////////////////
 //// RESET VALUES FORM FIELDS
 ////////////////////////////////////
   function resetFieldValue(id){ 
        $(id).val(''); 
 } // End ResetFieldsValues
		
  /////////////////////////////////////
 //// RESET VALUES FORM FIELDS
 ////////////////////////////////////
 
 