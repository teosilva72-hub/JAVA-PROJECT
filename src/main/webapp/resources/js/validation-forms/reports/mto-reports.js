	///////////////////////////////////
	//DAYS TO LIMIT MAX DATE SEARCH
	//////////////////////////////////
	var maxDays = 60;
	///////////////////////////////////
	//DAYS TO LIMIT MAX DATE SEARCH
	///////////////////////////////////
	
	//////////////////////////////
	//// CONVERT STRING TO DATE 
	/////////////////////////////
	 function toDate(dateStr) {
	   var parts = dateStr.split("/")
	   return new Date(parts[2], parts[1] - 1, parts[0])
	} // End toDate
	
	//////////////////////////////
	//// CONVERT STRING TO DATE 
	/////////////////////////////
	
	//////////////////////////
	//// VALIDATE MAX DATE 
	/////////////////////////
	function maxDateCheck(dateStart, dateEnd, dateError, message){
		
	jQuery.validator.addMethod("maxDate", function (value, element) {
		    var max = maxDays;
		    var startDate =  $(dateStart).val();
		    var endDate =  $(dateEnd).val();
		        
		    var dataInicio = toDate(startDate);
		    var dataFim = toDate(endDate);
		               
		    var daysDiff = (dataFim - dataInicio) / (1000*3600*24);
		    
		    if (daysDiff > max){
		    	
		    	 $(dateEnd).removeClass('valid').addClass('invalid');
		    	 $(dateError).parent().find('.error').fadeIn(500);
		    	 return false;
		    }
		    else{  $(dateEnd).removeClass('invalid').addClass('valid'); return true;      
		    }
		}, message);	
	}
		
	//////////////////////////////////////
	//// VALIDATE MAX DATE 
	/////////////////////////////////////

	/////////////////////////////////////////////////////
	//// VALIDATE START DATE IS GREATHER THEN END DATE 
	////////////////////////////////////////////////////
	function greaterThanEndDate(dateStart, dateEnd, dateError, message){
		
		jQuery.validator.addMethod("greaterThan", function(value, element) {			
			
			var str = $(dateStart).val();
		    var end = $(dateEnd).val();
		        
		    var startDt = toDate(str);
		    var endDt = toDate(end);

        if(startDt > endDt){
	
	        $(dateEnd).removeClass('valid').addClass('invalid');
		    $(dateError).parent().find('.error').fadeIn(500);

            return false;
                        
       }else {
	
	        $(dateEnd).removeClass('invalid').addClass('valid'); 
            return true;  
	
            }
				    
		}, message);
	} 
	
	/////////////////////////////////////////////////////
	//// VALIDATE START DATE IS GREATHER THEN END DATE 
	////////////////////////////////////////////////////
	
	////////////////////////////////////////
	///// VALIDATION FORM 1	
	////////////////////////////////////////
	
	/* USE IN YEAR REPORTS */	
		
	function weatherValidationModel1(form, equip_message, year_message, start_month_message, end_month_message){
		
		 $(form).validate({		    	                 
		         rules: {		       
		           equip: {
		            required: true
		           },
		            year: {
		            required: true
		           },		          
                   start_month: {
	                 required: true
                   },   
	               end_month: {
		            required: true
	               }               
		          },

		          messages: {
						
		              equip: { required: equip_message },
		              year: {required: year_message },	
		              start_month: { required: start_month_message },		             
                      end_month: {required: end_month_message },
                                         
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
	}
	
		
     ////////////////////////////////////////
	 ///// VALIDATION FORM 1	
	////////////////////////////////////////	
	
	////////////////////////////////////////
	///// VALIDATION FORM 2	
	////////////////////////////////////////
	
	/* USE IN MONTH REPORTS */			
	function weatherValidationModel2(form, equip_message, month_message, year_message){
		
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
						
		              equip: { required: equip_message },		             
                      month: {required: month_message},
                      year: {required: year_message},	
                    
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
	}
		  
	 ////////////////////////////////////////
	  ///// VALIDATION FORM 2	
	////////////////////////////////////////	
	
	////////////////////////////////////////
	///// VALIDATION FORM 3	
	////////////////////////////////////////
	
	/* USE IN PERIOD REPORTS */	
	function weatherValidationModel3(form, dateStart, equip_message, periods_message, dateStart_message, dateEnd_message, validDate_message){
		
		 $(form).validate({		    	                 
		         rules: {		       
		           equip: {
		            required: true
		           },
		            periods: {
		            required: true
		           },		          
                   dateStart: {
	                 required: true,
                     dateITA: true                   
                   },   
	               dateEnd: {
		            required: true,
                     greaterThan: dateStart,
                     maxDate: maxDays,	
                     dateITA: true,	           
		            
	               }               
		          },

		          messages: {
						
		              equip: { required: equip_message },
		              periods: {required: periods_message },	
		              dateStart: { required: dateStart_message },		             
                      dateEnd: {	                        
                            required: dateEnd_message,                                   
                            dateITA: validDate_message 
                     },
                                         
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
	}
		  
	 ////////////////////////////////////////
	  ///// VALIDATION FORM 3	
	////////////////////////////////////////
		
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
 //// RESET FORM VALIDATION
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
 ///// RESET FORM VALIDATION
 ////////////////////////////////////////////////	
 
 ////////////////////////////////////////////////
 //// RESET MODAL ON CLOSE
 ////////////////////////////////////////////////
function removeValidationOnModalClose(modalId, elem){
	
	$(modalId).on('hidden.bs.modal', function (e) {
				
       //Hide span by class  
	    $('span[for='+elem+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
                 
         //Reset Field 	
        resetFieldValue('#'+elem);
  })
} //Reset on Modal Close

 ////////////////////////////////////////////////
 ////////RESET MODAL ON CLOSE
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
