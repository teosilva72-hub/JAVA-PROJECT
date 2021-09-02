
//VALIDATION DEFINITIONS

$(function () {

    let validation = document.forms.validation;
    
    for (messages of validation)
        window[messages.name] = messages.value

 validateCreationForm('#register-equip-form',  requiredEquipmentsMsg, requiredEquipIdMsg, validatorNumberMsg, validatorMaxLenght, requiredEquipmentNameMsg,
       requiredCitiesMsg, requiredRoadsMsg, requiredKmMsg);

    //Validate elements on change value
    //In this case check if the element is valid
    validateOnChange('#equips');
    validateOnChange('#equipId');	
    validateOnChange('#equipName');
	validateOnChange('#cities');
	validateOnChange('#roads');
	validateOnChange('#km');	
    
    //Reset Fields on close modal
	resetFieldOnModalClose('#modaladd', 'equips');
	resetFieldOnModalClose('#modaladd', 'equipId');
	resetFieldOnModalClose('#modaladd', 'dmsType ');
	resetFieldOnModalClose('#modaladd', 'equipIp');
	resetFieldOnModalClose('#modaladd', 'speed-equipIp');
	resetFieldOnModalClose('#modaladd', 'radar-equipIp');
	resetFieldOnModalClose('#modaladd', 'equipPort');
	resetFieldOnModalClose('#modaladd', 'model');
	resetFieldOnModalClose('#modaladd', 'sip');
	resetFieldOnModalClose('#modaladd', 'equipName ');
	resetFieldOnModalClose('#modaladd', 'roads');
	resetFieldOnModalClose('#modaladd', 'cities');
	resetFieldOnModalClose('#modaladd', 'km');
	resetFieldOnModalClose('#modaladd', 'lanes');
	resetFieldOnModalClose('#modaladd', 'direction1');
	resetFieldOnModalClose('#modaladd', 'direction2');
	resetFieldOnModalClose('#modaladd', 'direction3');
	resetFieldOnModalClose('#modaladd', 'direction4');
	resetFieldOnModalClose('#modaladd', 'direction5');
	resetFieldOnModalClose('#modaladd', 'direction6');
	resetFieldOnModalClose('#modaladd', 'direction7');
	resetFieldOnModalClose('#modaladd', 'direction8');
	 //Reset Fields on close modal
					
	//Remove validation icons
    //click reset button action		 
    removeValidationIcon("reset-btn", 'equips');
    removeValidationIcon("reset-btn", 'equipId');
    removeValidationIcon("reset-btn", 'equipName');
	removeValidationIcon("reset-btn", 'cities');
    removeValidationIcon("reset-btn", 'roads');
    removeValidationIcon("reset-btn", 'km');	
	    
    //Clean Form validation on close modal
    cleanValidationOnModalClose("#register-equip-form", '#modaladd');
    
    //Reset validation form
    //click reset button action
    resetFormValidation("#register-equip-form", "reset-btn");	
    resetFormValidation("#register-equip-form", "close-btn");
    
    //Dismiss modal function
    $("#modaladd").on("hidden.bs.modal", function() {
    	$('.satInputs').hide();	
		$('.dmsHidden').hide();		
		$('.sosInputs').hide();
		$('.speedHidden').hide();
		$('.ipAddressShow').show();
				  
      });
    
    //reset form function
    $(".reset-btn").on("click", function() {	    		    
    	$('.satInputs').hide();	
		$('.dmsHidden').hide();	
		$('.sosInputs').hide();
		$('.speedHidden').hide();
		$('.ipAddressShow').show();	  
	    $('#modaladd').modal('hide');
      }); 	 
    
    //EQUIP EDITION
    
       validateEditionForm('#edit-equip-form',  validatorMaxLenght, requiredEquipmentNameMsg,
    requiredCitiesMsg, requiredRoadsMsg, requiredKmMsg);
  
    //Validate elements on change value
	    //In this case check if the element is valid	   
	    validateOnChange('#equipNameEdit');
		validateOnChange('#citiesEdit');
        validateOnChange('#roadsEdit');
        validateOnChange('#kmEdit');		   
	    
	    //Reset Fields on close modal
		resetFieldOnModalClose('#editmodal', 'equips-edit');
		resetFieldOnModalClose('#editmodal', 'equipId-edit');
		resetFieldOnModalClose('#editmodal', 'dmsType-edit');		
        resetFieldOnModalClose('#editmodal', 'equipIp-edit');
        resetFieldOnModalClose('#editmodal', 'speed-equipIp-edit');
        resetFieldOnModalClose('#editmodal', 'radar-equipIp-edit');
        resetFieldOnModalClose('#editmodal', 'equipPort-edit');
        resetFieldOnModalClose('#editmodal', 'modelEdit');
        resetFieldOnModalClose('#editmodal', 'sipEdit');
		resetFieldOnModalClose('#editmodal', 'equipNameEdit');
		resetFieldOnModalClose('#editmodal', 'roadsEdit');
		resetFieldOnModalClose('#editmodal', 'citiesEdit');
		resetFieldOnModalClose('#editmodal', 'kmEdit');
		resetFieldOnModalClose('#editmodal', 'lanes-edit');
		resetFieldOnModalClose('#editmodal', 'direction1-edit');
		resetFieldOnModalClose('#editmodal', 'direction2-edit');
		resetFieldOnModalClose('#editmodal', 'direction3-edit');
		resetFieldOnModalClose('#editmodal', 'direction4-edit');
		resetFieldOnModalClose('#editmodal', 'direction5-edit');
		resetFieldOnModalClose('#editmodal', 'direction6-edit');
		resetFieldOnModalClose('#editmodal', 'direction7-edit');
		resetFieldOnModalClose('#editmodal', 'direction8-edit');
		 //Reset Fields on close modal
						
		//Remove validation icons
	    //click reset button action	
	    removeValidationIcon("reset-btn-edit", 'equipNameEdit');
		removeValidationIcon("reset-btn-edit", 'citiesEdit');
        removeValidationIcon("reset-btn-edit", 'roadsEdit');
        removeValidationIcon("reset-btn-edit", 'kmEdit');	
		    
	    //Clean Form validation on close modal
        cleanValidationOnModalClose("#edit-equip-form", '#editmodal');
	    
        //Reset validation form
        //click reset button action
        resetFormValidation("#edit-equip-form", "reset-btn-edit");	
        resetFormValidation("#edit-equip-form", "close-btn-edit");
          	    
	    //Dismiss modal function
	    $("#editmodal").on("hidden.bs.modal", function() {
			$('.satInputs-edit').hide();	
            $('.dmsHidden-edit').hide();       
	    	$('.satInputs-edit').hide();	
			$('.speedHidden-edit').hide();
			$('.ipAddressShow-edit').show();	 				  
	      });
	    
	    //reset form function
	    $(".reset-btn-edit").on("click", function() {	    		    
	    	$('.satInputs-edit').hide();
			$('.dmsHidden-edit').hide();    	
			$('.speedHidden-edit').hide();
			$('.ipAddressShow-edit').show();
			$('#editmodal').modal('hide');
	      }); 	     

});