
 // Validate VBV Report Form
	function formValidation(equipRequiredMessage, monthsRequiredMessage, yearsRequiredMessage){
		
		 $("#vbv-form").validate({		    	                   
		         rules: {
		         equips: {    	
		              required: true		            
		           },	          
                   months: {
	                 required: true
                   },   
	               years: {
		            required: true
	               }   	                         
		          },

		          messages: {
						
		              equips:{required: equipsRequiredMessage},                     
                      months:{required: monthsRequiredMessage},
                      years: {required: yearsRequiredMessage}           	              
		              
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

                    //Multiselect button configuration to set invalid
                    if($(element.form).find("select[id="+element.id+"]").hasClass('invalid')) {                         
                         $(element).next('.btn-group').find('button').removeClass('valid').addClass('invalid');

                      }
		            
                    },

		          unhighlight: function(element, errorClass, validClass) {		       
                  $(element.form).find("label[for=" + element.id + "]").removeClass(errorClass);  
               
                  $(element.form).find("input[id="+element.id+"]").removeClass('invalid').addClass('valid');
                  $(element.form).find("select[id="+element.id+"]").removeClass('invalid').addClass('valid');
                
                  //FontAwesome Icon Check for success
                  $(element.form).find("span[for="+element.id+"]").html("<i class='fa fa-check success'></i>");

                 
                 }                  
		     });
	}// End Validation Form