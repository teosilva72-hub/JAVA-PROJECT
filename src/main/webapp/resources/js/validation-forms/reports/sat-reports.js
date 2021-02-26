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
				
    // Validate Count Vehicle Form
	function formValidation(startDate, elem1RequiredMessage, elem2RequiredMessage, elem3RequiredMessage,
	elem4RequiredMessage, elem5RequiredMessage, elem6RequiredMessage, elem7RequiredMessage, elem8RequiredMessage, startDateRequiredMessage, endDateRequiredMessage, validDateMessage){
		
		 $("#report-form").validate({
		    	  ignore: [],                    
		         rules: {
		         dateStart: {    	
		              required: true,
		              dateITA: true
		           },
		           equip: {
		            required: true
		           },
		           dateEnd: {
		             required: true,
		             greaterThan: startDate,	
                     dateITA: true,	           
		             maxDate: true
		           }, 
                   periods: {
		           required: true
		           }, 
                   month: {
	                 required: true
                   },   
	               year: {
		            required: true
	               },
      	           equips: "required",        
		           vehicles: "required",
                   axles: "required", 
                   classes: "required", 
                   directions: "required"               
		          },

		          messages: {
						
		              equips:{ required: elem1RequiredMessage },
		              equip:{ required: elem1RequiredMessage },
                      vehicles:{required: elem2RequiredMessage},                    
		              periods:{required: elem3RequiredMessage },
                      axles:{ required: elem4RequiredMessage },
                      classes: {required: elem5RequiredMessage},
                      month:{required: elem6RequiredMessage},
                      year: {required: elem7RequiredMessage},	
                      directions: {required: elem8RequiredMessage} ,
	            	              
		              dateStart:{
		              	required: startDateRequiredMessage              	
		              	
		              },
		              dateEnd:{ required: endDateRequiredMessage,            	      
		              	      dateITA: validDateMessage
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
	}// End Validation Form
	
	
//Reset modal validations on Close	
function resetOnModalClose(modalId, form, elem1, elem1NonText, elem1Array,
      elem2, elem2NonText, elem2Array, elem3, elem4, elem5){
	
	$(modalId).on('hidden.bs.modal', function (e) {
		
		//Reset Form validation
	       var validator = $(form).validate();
	       validator.resetForm();    

       //Hide span by class  
	    $('span[for='+elem1+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    $('span[for='+elem2+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    $('span[for='+elem3+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
        $('span[for='+elem4+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    $('span[for='+elem5+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');   

       //Redefine multiselect
        resetMultiselectValues('#'+elem1, elem1NonText, elem1Array);
        resetMultiselectValues('#'+elem2, elem2NonText, elem2Array);

       // Redefine values on click (Reset input and select)
        resetFieldValue('#'+elem3);
        resetFieldValue('#'+elem4);
        resetFieldValue('#'+elem5);
  })
} //Reset on Modal Close

//Reset modal validations on Close	
function resetOnModalClose2(modalId, form, elem1, elem1NonText, elem1Array,
      elem2, elem3){
	
	$(modalId).on('hidden.bs.modal', function (e) {
		
		//Reset Form validation
	       var validator = $(form).validate();
	       validator.resetForm();    

       //Hide span by class  
	    $('span[for='+elem1+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    $('span[for='+elem2+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    $('span[for='+elem3+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');      

       //Redefine multiselect
        resetMultiselectValues('#'+elem1, elem1NonText, elem1Array);      

       // Redefine values on click (Reset input and select)
        resetFieldValue('#'+elem2);
        resetFieldValue('#'+elem3);
    })
} //Reset on Modal Close

//Reset modal validations on Close	
function resetOnModalClose3(modalId, form, elem1, elem1NonText, elem1Array,
      elem2, elem3, elem4){
	
	$(modalId).on('hidden.bs.modal', function (e) {
		
		//Reset Form validation
	       var validator = $(form).validate();
	       validator.resetForm();    

       //Hide span by class  
	    $('span[for='+elem1+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    $('span[for='+elem2+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    $('span[for='+elem3+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');    
        $('span[for='+elem4+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');      

       //Redefine multiselect
        resetMultiselectValues('#'+elem1, elem1NonText, elem1Array);      

       // Redefine values on click (Reset input and select)
        resetFieldValue('#'+elem2);
        resetFieldValue('#'+elem3);
        resetFieldValue('#'+elem4);
    })
} //Reset on Modal Close

//Reset modal validations on Close	
function resetOnModalClose4(modalId, form, elem1, elem2, elem3, elem4){
	
	$(modalId).on('hidden.bs.modal', function (e) {
		
		//Reset Form validation
	       var validator = $(form).validate();
	       validator.resetForm();    

       //Hide span by class  
	    $('span[for='+elem1+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    $('span[for='+elem2+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    $('span[for='+elem3+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');    
 
       //Redefine multiselect
        resetMultiselectValues('#'+elem1, elem1NonText, elem1Array);      

       // Redefine values on click (Reset input and select)
        resetFieldValue('#'+elem1);
        resetFieldValue('#'+elem2);
        resetFieldValue('#'+elem3);
    })
} //Reset on Modal Close

//Reset modal validations on Close	
function resetOnModalClose5(modalId, form, elem1, elem1NonText, elem1Array,
      elem2, elem3, elem4, elem5){
	
	$(modalId).on('hidden.bs.modal', function (e) {
		
		//Reset Form validation
	       var validator = $(form).validate();
	       validator.resetForm();    

       //Hide span by class  
	    $('span[for='+elem1+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    $('span[for='+elem2+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    $('span[for='+elem3+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');    
        $('span[for='+elem4+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');  
        $('span[for='+elem5+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');      

       //Redefine multiselect
        resetMultiselectValues('#'+elem1, elem1NonText, elem1Array);      

       // Redefine values on click (Reset input and select)
        resetFieldValue('#'+elem2);
        resetFieldValue('#'+elem3);
        resetFieldValue('#'+elem4);
        resetFieldValue('#'+elem5);

    })
} //Reset on Modal Close
	
	//VALIDATE FIELDS BY ID ON CHANGE
	//CHECK IF ELEMENT IS VALID FIRST
	function validateOnChange(id){
	 $(id).on('change', function(){
	        if($(id).valid()) {
	            $(id).removeClass('invalid');
	        }
	    })
      } // End ValidateOnChange

//RESET VALUES FROM INPUTS AND SIMPLE SELECT
   function resetFieldValue(id){ 
        $(id).val(''); 
} // End ResetFieldsValue

  //Validate Reset on Reset button actions
   function resetValidationForm(class_, form, elem1, elem1NonText, elem1Array,
      elem2, elem2NonText, elem2Array, elem3, elem4, elem5){ 
	
	  $(class_).click(function() {
						
		//Reset Form validation
	       var validator = $(form).validate();
	       validator.resetForm();

       //Hide span by class  
	    $('span[for='+elem1+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    $('span[for='+elem2+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    $('span[for='+elem3+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
        $('span[for='+elem4+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    $('span[for='+elem5+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden'); 

     //Redefine multiselect
        resetMultiselectValues('#'+elem1, elem1NonText, elem1Array);
        resetMultiselectValues('#'+elem2, elem2NonText, elem2Array);

       // Redefine values on click (Reset input and select)
        resetFieldValue('#'+elem3);
        resetFieldValue('#'+elem4);
        resetFieldValue('#'+elem5);
	     
    });
 } //End ResetValidationForm

//Validate Reset on Reset button actions
   function resetValidationForm2(class_, form, elem1, elem1NonText, elem1Array,
      elem2, elem3){ 
	
	  $(class_).click(function() {
						
		//Reset Form validation
	       var validator = $(form).validate();
	       validator.resetForm();

       //Hide span by class  
	    $('span[for='+elem1+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    $('span[for='+elem2+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    $('span[for='+elem3+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
      
     //Redefine multiselect
        resetMultiselectValues('#'+elem1, elem1NonText, elem1Array);
    

       // Redefine values on click (Reset input and select)
        resetFieldValue('#'+elem2);
        resetFieldValue('#'+elem3);      
	     
    });
 } //End ResetValidationForm

//Validate Reset on Reset button actions
   function resetValidationForm3(class_, form, elem1, elem1NonText, elem1Array,
      elem2, elem3, elem4){ 
	
	  $(class_).click(function() {
						
		//Reset Form validation
	       var validator = $(form).validate();
	       validator.resetForm();

       //Hide span by class  
	    $('span[for='+elem1+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    $('span[for='+elem2+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    $('span[for='+elem3+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
        $('span[for='+elem4+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
      
     //Redefine multiselect
        resetMultiselectValues('#'+elem1, elem1NonText, elem1Array);
    

       // Redefine values on click (Reset input and select)
        resetFieldValue('#'+elem2);
        resetFieldValue('#'+elem3); 
        resetFieldValue('#'+elem4);      
	     
    });
 } //End ResetValidationForm

//Validate Reset on Reset button actions
   function resetValidationForm4(class_, form, elem1, elem2, elem3, elem4){ 
	
	  $(class_).click(function() {
						
		//Reset Form validation
	       var validator = $(form).validate();
	       validator.resetForm();

       //Hide span by class  
	    $('span[for='+elem1+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    $('span[for='+elem2+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    $('span[for='+elem3+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
        $('span[for='+elem4+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
           
       // Redefine values on click (Reset input and select)
        resetFieldValue('#'+elem1);
        resetFieldValue('#'+elem2); 
        resetFieldValue('#'+elem3); 
        resetFieldValue('#'+elem4);     
	     
    });
 } //End ResetValidationForm

//Validate Reset on Reset button actions
   function resetValidationForm5(class_, form, elem1, elem1NonText, elem1Array,
      elem2, elem3, elem4, elem5){ 
	
	  $(class_).click(function() {
						
		//Reset Form validation
	       var validator = $(form).validate();
	       validator.resetForm();

       //Hide span by class  
	    $('span[for='+elem1+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    $('span[for='+elem2+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    $('span[for='+elem3+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
        $('span[for='+elem4+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
        $('span[for='+elem5+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
      
     //Redefine multiselect
        resetMultiselectValues('#'+elem1, elem1NonText, elem1Array);
    
       // Redefine values on click (Reset input and select)
        resetFieldValue('#'+elem2);
        resetFieldValue('#'+elem3); 
        resetFieldValue('#'+elem4);    
        resetFieldValue('#'+elem5);   
	     
    });
 } //End ResetValidationForm

//Change action by Selection
function changeActionBySelection(selectId, form, id1, id2){
 $(selectId).on('change', function() {
         
         document.getElementById(form+':'+id1).value = this.value;	
         document.getElementById(form+':'+id2).click();       
    });
} // END Change action by Selection

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


//Validate Reset on Reset button actions
   function resetValidationForm6(class_, form, elem1, elem1NonText, elem1Array,
      elem2, elem2NonText, elem2Array, elem3, elem4, elem5, elem6){ 
	
	  $(class_).click(function() {
						
		//Reset Form validation
	       var validator = $(form).validate();
	       validator.resetForm();

       //Hide span by class  
	    $('span[for='+elem1+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    $('span[for='+elem2+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    $('span[for='+elem3+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
        $('span[for='+elem4+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
        $('span[for='+elem5+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
        $('span[for='+elem6+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
      
        //Redefine multiselect
        resetMultiselectValues('#'+elem1, elem1NonText, elem1Array);
        resetMultiselectValues('#'+elem2, elem2NonText, elem2Array);
    
       // Redefine values on click (Reset input and select)       
        resetFieldValue('#'+elem3); 
        resetFieldValue('#'+elem4);    
        resetFieldValue('#'+elem5); 
        resetFieldValue('#'+elem6);   
	     
    });
 } //End ResetValidationForm

//Reset modal validations on Close	
function resetOnModalClose6(modalId, form, elem1, elem1NonText, elem1Array,
      elem2,  elem2NonText, elem2Array, elem3, elem4, elem5, elem6){
	
	$(modalId).on('hidden.bs.modal', function (e) {
		
		  //Reset Form validation
	       var validator = $(form).validate();
	       validator.resetForm();    

       //Hide span by class  
	    $('span[for='+elem1+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    $('span[for='+elem2+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');
	    $('span[for='+elem3+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');    
        $('span[for='+elem4+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');  
        $('span[for='+elem5+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');      
        $('span[for='+elem6+']').removeClass('valid-icon-visible').addClass('valid-icon-hidden'); 

       //Redefine multiselect
        resetMultiselectValues('#'+elem1, elem1NonText, elem1Array);   
        resetMultiselectValues('#'+elem2, elem2NonText, elem2Array);     

       // Redefine values on click (Reset input and select)      
        resetFieldValue('#'+elem3);
        resetFieldValue('#'+elem4);
        resetFieldValue('#'+elem5);
        resetFieldValue('#'+elem6);
    })
} //Reset on Modal Close

//Close download modal

  $(function () {
	    	$("[onclose='download']").click(function () {
	    		$('#modalDownload').modal('hide')
	    	})
	    })
	
