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
		
	///////////////////////////////////////////////////////////////////////////////////////////////	
	
	////////////////////////////////////////
	  ///// VALIDATION FORM 1	
	////////////////////////////////////////
	
	/* USE IN COUNT VEHICLES REPORT */
	/* USE MULTISELECT ITEMS */	
	function satReportsValidationModel1(form, dateStart, equips_message, vehicles_message, periods_message, dateStart_message, dateEnd_message, validDate_message){
		
		 $(form).validate({		
		       ignore: [],               	                 
		         rules: {
		          equips: "required",        
		           vehicles: "required",    
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
						
		              equips: { required: equips_message },
		              vehicles: { required: vehicles_message },
		              periods: {required: periods_message },	
		              dateStart: { required: dateStart_message,
		              dateITA: validDate_message },		             
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

                  //Multiselect button configuration to set valid
                  if($(element.form).find("select[id="+element.id+"]").hasClass('valid')) {                         
                        $(element).next('.btn-group').find('button').removeClass('invalid').addClass('valid');
                    } 
                 }                  
		     });
	}
		  
	 ////////////////////////////////////////
	  ///// VALIDATION FORM 1	
	////////////////////////////////////////

	////////////////////////////////////////
	///// VALIDATION FORM 2	
	////////////////////////////////////////
	
	/* USE IN VEHICLE COUNT FLOW, SPEED, AXLES, CLASSES AND WEIGHING */	
	function satReportValidationModel2(form, dateStart, equip_message, periods_message, dateStart_message, dateEnd_message, validDate_message){
		
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
					 dateStart: { required: dateStart_message,
								  dateITA: validDate_message
					  },		             
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

                  //Multiselect button configuration to set valid
                  if($(element.form).find("select[id="+element.id+"]").hasClass('valid')) {                         
                        $(element).next('.btn-group').find('button').removeClass('invalid').addClass('valid');
                    } 
                 }                  
		     });
   }
				  							   
		 
	////////////////////////////////////////
	 ///// VALIDATION FORM 3	
   ////////////////////////////////////////

   	/* USE IN MONTH FLOW */	  

function satReportsValidationModel3(form, equip_message, month_message, year_message){
	
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
              //Multiselect button configuration to set valid
              if($(element.form).find("select[id="+element.id+"]").hasClass('valid')) {                         
                    $(element).next('.btn-group').find('button').removeClass('invalid').addClass('valid');
                } 
			}                					     		     	    	     	 	 
               
		});
}

////////////////////////////////////////
 ///// VALIDATION FORM 3	
////////////////////////////////////////
				
		 
	////////////////////////////////////////
	 ///// VALIDATION FORM 4	
   ////////////////////////////////////////

   	/* USE IN PERIOD FLOW */
	   function satReportsValidationModel4(form, equips_message, periods_message, month_message, year_message){
	
		$(form).validate({	
			ignore: [],   	    	                 
				rules: {		       
				  equips:  "required",				  	
				  periods: {
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
					 equips: { required: equips_message },
					 periods: { required: periods_message },
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

				 //Multiselect button configuration to set valid
				 if($(element.form).find("select[id="+element.id+"]").hasClass('valid')) {                         
					   $(element).next('.btn-group').find('button').removeClass('invalid').addClass('valid');
				   } 
				}                  
			});
  
 }
   
   ////////////////////////////////////////
	///// VALIDATION FORM 4
   ////////////////////////////////////////
				
   
 /////////////////////////////////////////
 //// CHANGE SELECTION
 ////////////////////////////////////////
  
//Change action by Selection
function changeActionBySelection(selectId, form, id1, id2){
 $(selectId).on('change', function() {
         
         document.getElementById(form+':'+id1).value = this.value;	
         document.getElementById(form+':'+id2).click();       
    });
} // END Change action by Selection

 /////////////////////////////////////////
 //// CHANGE SELECTION
 ////////////////////////////////////////
 
/////////////////////////////////////////
 //// DIRECTIONS
 ////////////////////////////////////////
//var  Directions 
//Specific for directions multiselect
 function updateDirections(nonSelectText){
        	
        dirLab1 = document.getElementById('report-form:dirLab1').value;
        dirValue1 = document.getElementById('report-form:dirVal1').value;
        dirLab2 = document.getElementById('report-form:dirLab2').value;
        dirValue2 = document.getElementById('report-form:dirVal2').value;
      
          $('#directions').multiselect('destroy');	
    	  
          $('#op1').text(dirLab1);
    	  $('#op1').val(dirValue1);
    	  
    	  $('#op2').text(dirLab2);
    	  $('#op2').val(dirValue2);
    	  
    	  $('#directions').multiselect({
    	        columns: 1,       
    	        allSelectedText: 'All',
    	        maxHeight: 200,
    	        includeSelectAllOption: true,
    	        buttonWidth:'100%',
    	        nSelectedText: nonSelectText    	        
    	    });    	    
   } //Update Directions	

/////////////////////////////////////////
 //// DIRECTIONS
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
 //// RESET FORM VALIDATION ICON MULTISELECT
 ////////////////////////////////////////////////
 function removeValidationMultiselectIcon(btn, elem, defaultMessage, array){ 
	
	  $('.'+btn).click(function() {
					
       //Hide span by class  
	    $('span[for='+elem+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    	     
	      //Reset Multiselect Field 	     
	      resetMultiselectValues('#'+elem, defaultMessage, array);   
    });
 } //End ResetValidationForm
	
 ////////////////////////////////////////////////
 ///// RESET FORM VALIDATION ICON MULTISELECT
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
 //// RESET MULTISELCT FIELD ON MODAL CLOSE
 ////////////////////////////////////////////////
function resetFieldMultiselectOnModalClose(modalId, elem, defaultMessage, array){
	
	$(modalId).on('hide.bs.modal', function (e) {
				
       //Hide span by class  
	    $('span[for='+elem+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
                 
          //Reset Multiselect Field 	     
	      resetMultiselectValues('#'+elem, defaultMessage, array);  
	  				  
  })
} //Reset on Modal Close


 ////////////////////////////////////////////////
 ////////RESET MULTISELCT FIELD ON MODAL CLOSE
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

	
