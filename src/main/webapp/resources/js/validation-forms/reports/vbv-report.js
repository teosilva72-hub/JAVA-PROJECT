
 // Validate VBV Report Form
	function formValidation(form, equip_message, month_message, year_message){
		
		 $(form).validate({		    	                   
		         rules: {
		         equip: {    	
		              required: true		            
		           },	          
                   month: {
	                 required: true
                   },   
	               year: {
		            required: true
	               }   	                         
		          },

		          messages: {
						
		              equip :{ required: equip_message},                     
                      month :{ required: month_message},
                      year : { required: year_message}           	              
		              
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
                  
                     $(element.form).find("select[id="+element.id+"]").removeClass('valid').addClass('invalid');         
                                    
                     //Show span validation icon
                     $(element.form).find("span[for="+ element.id +"]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');

                     //FontAwesome Icon Times for error
                     $(element.form).find("span[for="+element.id+"]").html("<i class='fa fa-times error'></i>");

                    //Multiselect button configuration to set invalid
                    if($(element.form).find("select[id="+element.id+"]").hasClass('invalid')) {                         
                         $(element).next('.btn-group').find('button').removeClass('valid').addClass('invalid');

                      }
		            
                    },

		          unhighlight: function(element, errorClass, validClass) {		       
                  $(element.form).find("label[for=" + element.id + "]").removeClass(errorClass);  
                               
                  $(element.form).find("select[id="+element.id+"]").removeClass('invalid').addClass('valid');
                
                  //FontAwesome Icon Check for success
                  $(element.form).find("span[for="+element.id+"]").html("<i class='fa fa-check success'></i>");

                 
                 }                  
		     });
	}// End Validation Form

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


//////////////////////////////////////////////
//// ON CLICK SUBMIT BUTTON DOWNLOAD MODAL
////////////////////////////////////////////
  $(function () {
	    	$("[onclose='download']").click(function () {
	    		$('#modalDownload').modal('hide')
	    	})
	    })
	
/////////////////////////////////////////////
//// ON CLICK SUBMIT BUTTON DOWNLOAD MODAL
////////////////////////////////////////////