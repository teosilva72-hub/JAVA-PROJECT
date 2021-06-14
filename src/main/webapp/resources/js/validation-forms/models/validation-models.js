
/* *************************************** COMPLEMENTARY FUNCTIONS ***************************************** */


const maxDays = 60; // Max days to search in reports

/* ******************************************************************************************************** */

/** To date conversion
 *  
 * @author Wellington da Silva : 2021-05-07
 * @summary Function used when you need to convert string date format to date.
 * @since version 1.0
 * @version 1.1 
 * @description convert string date format to date
 * @copyright Tracevia S/A 2021
 * @param {string} dateStr date string format
 * @returns {Date} returns date
 * 
**/

function toDate(dateStr) {
	var parts = dateStr.split("/")
	return new Date(parts[2], parts[1] - 1, parts[0])
}

/* ******************************************************************************************************** */

/** Max date check
 *  
 * @author Wellington da Silva : 2021-05-07
 * @summary Function used to check the maximum date between startDate and endDate. Use maxDay as a parameter to check.
 * @since version 1.0
 * @version 1.1 
 * @description validate maximum date between two dates
 * @copyright Tracevia S/A 2021
 * @param {string} dateStartId dateStart id value 
 * @param {string} dateEndId dateEnd id value 
 * @param {string} dateErrorId dateError id to do add and remove class
 * @param {string} message show message information
 * @returns {string} returns validation message
**/

function maxDateCheck(dateStart, dateEnd, dateError, message) {

	jQuery.validator.addMethod("maxDate", function (value, element) {
		var max = maxDays;
		var startDate = $(dateStart).val();
		var endDate = $(dateEnd).val();

		var dataInicio = toDate(startDate);
		var dataFim = toDate(endDate);

		var daysDiff = (dataFim - dataInicio) / (1000 * 3600 * 24);

		if (daysDiff > max) {

			$(dateEnd).removeClass('valid').addClass('invalid');
			$(dateError).parent().find('.error').fadeIn(500);
			return false;
		}
		else {
			$(dateEnd).removeClass('invalid').addClass('valid'); return true;
		}
	}, message);
}

/* ******************************************************************************************************** */

/** Greather than end date
 *  
 * @author Wellington da Silva : 2021-05-07
 * @summary Function used to check if startDate is greather than endDate.
 * @since version 1.0
 * @version 1.1 
 * @description check if a date is greather than the other
 * @copyright Tracevia S/A 2021
 * @param {string} dateStartId dateStart id value 
 * @param {string} dateEndId dateEnd id value  
 * @param {string} dateErrorId dateError id to do add and remove class
 * @param {string} message show message information
 * @returns {string} returns validation message
**/

function greaterThanEndDate(dateStart, dateEnd, dateError, message) {

	jQuery.validator.addMethod("greaterThan", function (value, element) {

		var str = $(dateStart).val();
		var end = $(dateEnd).val();

		var startDt = toDate(str);
		var endDt = toDate(end);

		if (startDt > endDt) {

			$(dateEnd).removeClass('valid').addClass('invalid');
			$(dateError).parent().find('.error').fadeIn(500);

			return false;

		} else {

			$(dateEnd).removeClass('invalid').addClass('valid');
			return true;

		}

	}, message);
}

/* ******************************************************************************************************** */

/** On event function
 *  
 * @author Wellington da Silva : 2021-05-07
 * @summary Function used to display the load message while the page is being processed
 * @since version 1.0
 * @version 1.1 
 * @description display message while processing the page
 * @copyright Tracevia S/A 2021
 * @param {string} data requested data
 * @returns {void}
**/

let onEventFunction = data => {
	var status = data.status;
	let loading = $('#loading');
	switch (status) {
		case "begin":

			loading.addClass('active')
			break;

		case "complete":
			loading.removeClass('active')
			break;

		case "success":
			$('[id$=cancelDownload]').click()
			break;
	}
}

/** Verif
 *  
 * @author Wellington da Silva : 2021-05-07
 * @summary Function used to validate form using ajax
 * @since version 1.0
 * @version 1.1 
 * @description validate form
 * @copyright Tracevia S/A 2021
 * @param {string} form form id
 * @param {string} success success data
 * @returns {void}
**/

let verif = (form, success) => {
	if ($(form).valid())
		$(success).click()
}

/* ******************************************************************************************************** */

/** Validate on change
 *  
 * @author Wellington da Silva : 2021-05-07
 * @summary Function used to check for changes in field values
 * @since version 1.0
 * @version 1.1 
 * @description validate in value changes
 * @copyright Tracevia S/A 2021 
 * @param {string} id  element id to check changes
 * @returns {void}
 * 
**/

function validateOnChange(id) {
	$(id).on('change', function () {
		if ($(id).valid())
			$(id).removeClass('invalid');

		else $(id).removeClass('valid');

	})
}

/* ******************************************************************************************************** */

/** Remove validation icon
 *  
 * @author Wellington da Silva : 2021-05-07
 * @summary Function used to remove validation icon
 * @since version 1.0
 * @version 1.1 
 * @description remove validation icon
 * @copyright Tracevia S/A 2021 
 * @param {string} btn btn class
 * @param {string} elem element to check and change
 * @returns {void}
**/

function removeValidationIcon(btn, elem) {

	$('.' + btn).click(function () {

		//Hide span by class  
		$('span[for=' + elem + ']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');

		//Then Reset Field 	     
		resetFieldValue('#' + elem);
	});
}

/* ******************************************************************************************************** */

/** Remove validation icon from multiselect
 *  
 * @author Wellington da Silva : 2021-05-07
 * @summary Function used to remove validation icon from multiselect field
 * @since version 1.0
 * @version 1.1 
 * @description remove multiselect field icon
 * @copyright Tracevia S/A 2021 
 * @param {string} btn btn class to do action on click
 * @param {string} elem element to check and change
 * @param {string} defaultMessage set default message when reset field
 * @param {Array<Integer>} array integer array to check items by default 
 * @returns {void}
**/

function removeValidationMultiselectIcon(btn, elem, defaultMessage, array) {

	$('.' + btn).click(function () {

		//Hide span by class  
		$('span[for=' + elem + ']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');

		//Reset Multiselect Field 	     
		resetMultiselectValues('#' + elem, defaultMessage, array);
	});
}

/* ******************************************************************************************************** */

/** Clean validation on modal close
 *  
 * @author Wellington da Silva : 2021-05-07
 * @summary Function used to  clear fields on modal close
 * @since version 1.0
 * @version 1.1 
 * @description clear modal fields
 * @copyright Tracevia S/A 2021 
 * @param {string} formId form id to reset validation
 * @param {string} modalId modal id to hide 
 * @returns {void}
 * 
**/

function cleanValidationOnModalClose(formId, modalId) {

	$(modalId).on("hide.bs.modal", function () {

		//Hide span by class        				
		$(formId).validate().resetForm();
	})
}

/* ******************************************************************************************************** */

/** Reset fields on modal close
 *  
 * @author Wellington da Silva : 2021-05-07
 * @summary Function used to reset fields values on modal close
 * @since version 1.0
 * @version 1.1 
 * @description reset fields values
 * @copyright Tracevia S/A 2021
 * @param {string} modalId modal id to hide 
 * @param {string} elem element to check and change
 * @returns {void}
**/

function resetFieldOnModalClose(modalId, elem) {

	$(modalId).on("hide.bs.modal", function () {

		//Hide span by class  
		$('span[for=' + elem + ']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');

		//Reset Field 	     
		resetFieldValue('#' + elem);

	})
}

/* ******************************************************************************************************** */

/** Reset multiselect fields on modal close
 *  
 * @author Wellington da Silva : 2021-05-07
 * @summary Function used to reset multiselect fields on modal close
 * @since version 1.0
 * @version 1.1 
 * @description Reset modal multiselect fields
 * @copyright Tracevia S/A 2021
 * @param {string} modalId modal id to hide 
 * @param {string} elem form id to reset validation
 * @param {string} defaultMessage set default message when reset field
 * @param {Array<Integer>} array integer array to check items by default 
 * @returns {void}
**/

function resetFieldMultiselectOnModalClose(modalId, elem, defaultMessage, array) {

	$(modalId).on('hide.bs.modal', function (e) {

		//Hide span by class  
		$('span[for=' + elem + ']').removeClass('valid-icon-visible').addClass('valid-icon-hidden');

		//Reset Multiselect Field 	     
		resetMultiselectValues('#' + elem, defaultMessage, array);

	})
}

/* ******************************************************************************************************** */

/** Reset form validation
 *  
 * @author Wellington da Silva : 2021-05-07
 * @summary Function used to reset form validation
 * @since version 1.0
 * @version 1.1 
 * @description Reset validation form
 * @copyright Tracevia S/A 2021
 * @param {string} formId form id to reset validation
 * @param {string} btn btn class to do action on click
 * @returns {void}
**/

function resetFormValidation(formId, btn) {
	//Reset Form validation

	$('.' + btn).click(function () {

		var validator = $(formId).validate();
		validator.resetForm();

	})
}

/* ******************************************************************************************************** */

/** Reset field values
 *  
 * @author Wellington da Silva : 2021-05-07
 * @summary Function used to reset fields values
 * @since version 1.0
 * @version 1.1 
 * @description reset fields values
 * @copyright Tracevia S/A 2021
 * @param {string} id  element id to reset value
 * @returns {void}
**/

function resetFieldValue(id) {
	$(id).val('');
}

/* ******************************************************************************************************** */

/** Hide download modal
 *  
 * @author Wellington da Silva : 2021-05-07
 * @summary Function used to close download modal
 * @since version 1.0
 * @version 1.1 
 * @description close download modal
 * @copyright Tracevia S/A 2021
 * @returns {void}
**/

$(function() {
	$("[onclose='download']").click(function () {
		$('#modalDownload').modal('hide')
	})
});

/* ******************************************************************************************************** */

/** Change action by selection
 *  
 * @author Wellington da Silva : 2021-05-07
 * @summary Function used to change values depending on condition
 * @since version 1.0
 * @version 1.1 
 * @description change values
 * @copyright Tracevia S/A 2021
 * @param {string} formId element id
 * @param {string} selectId element id
 * @param {string} id1 element id1
 * @param {string} id2 element id2
 * @returns {void}
**/

function changeActionBySelection(formId, selectId, id1, id2) {
	$(selectId).on('change', function () {

		document.getElementById(formId + ':' + id1).value = this.value;
		document.getElementById(formId + ':' + id2).click();
	});
}

/* ******************************************************************************************************** */

/** Update Directions
 *  
 * @author Wellington da Silva : 2021-05-07
 * @summary Function used to update directions values depending on equipment selection
 * @since version 1.0
 * @version 1.1 
 * @description update directions field values
 * @copyright Tracevia S/A 2021
 * @param {string} directionId direction id
 * @param {string} nonSelectText text when unselected message
 * @returns {void}
**/

function updateDirections(directionId, nonSelectText) {

	dirLab1 = document.getElementById('report-form:dirLab1').value;
	dirValue1 = document.getElementById('report-form:dirVal1').value;
	dirLab2 = document.getElementById('report-form:dirLab2').value;
	dirValue2 = document.getElementById('report-form:dirVal2').value;

	$(directionId).multiselect('destroy');

	$('#op1').text(dirLab1);
	$('#op1').val(dirValue1);

	$('#op2').text(dirLab2);
	$('#op2').val(dirValue2);

	$(directionId).multiselect({
		columns: 1,
		allSelectedText: 'All',
		maxHeight: 200,
		includeSelectAllOption: true,
		buttonWidth: '100%',
		nSelectedText: nonSelectText
	});
}

/* ******************************************************************************************************** */

/** Validate number
 *  
 * @author Wellington da Silva : 2021-05-09
 * @summary function used to check if has number values in field
 * @since version 1.0
 * @version 1.1 
 * @description check number fields
 * @copyright Tracevia S/A 2021
 * @param {string} id field id
 * @param {string} idError field error id
 * @param {string} message validation message
 * @returns {string} returns validation message
**/

   function validateNumber(id, idError, message){

	  jQuery.validator.addMethod("isNumber", function(value, element) { 
	    var numero = $(id).val();
	    var regex = new RegExp(/^[0-9]+$/); // check numeric field
	    if(numero.match(regex)) {  $(id).removeClass('invalid').addClass('valid'); return true;}
	    
	    else
	   $(id).removeClass('valid').addClass('invalid');
	    $(idError).parent().find('.error').fadeIn(500);		  
	    return false;	
	}, message); 
}

/* ******************************************************************************************************** */

/** Validate email
 *  
 * @author Wellington da Silva : 2021-05-09
 * @summary function used to check email validation
 * @since version 1.0
 * @version 1.1 
 * @description check email
 * @copyright Tracevia S/A 2021
 * @param {string} id field id
 * @param {string} idError field error id
 * @param {string} message validation message
 * @returns {string} returns validation message
**/

function validateEmail(id, idError, message){

jQuery.validator.addMethod("isEmail", function(value, element) { 
    var email = $(id).val();	    
     var regex = new RegExp(/^[A-Za-z0-9_\-\.]+@[A-Za-z0-9_\-\.]{2,}\.[A-Za-z0-9]{3,}(\.[A-Za-z0-9])?/); 
   
     if(email.match(regex)) {  $(id).removeClass('invalid').addClass('valid'); return true; }
    
    	else     
    	$(id).removeClass('valid').addClass('invalid');
	    $(idError).parent().find('.error').fadeIn(500);	     
          return false;	 
}, message); 

}

/* ******************************************************************************************************** */

/** Validate name
 *  
 * @author Wellington da Silva : 2021-05-09
 * @summary function used to check name validation
 * @since version 1.0
 * @version 1.1 
 * @description check name
 * @copyright Tracevia S/A 2021
 * @param {string} id field id
 * @param {string} idError field error id
 * @param {string} message validation message
 * @returns {string} returns validation message
**/

function validateName(id, idError, message){

	jQuery.validator.addMethod("isName", function(value, element) { 
	    var nome = $(id).val();
	    var regex = new RegExp(/[^a-zA-Z\u00C0-\u00FF ]$/); // Chararcters for name composition
	    
	    if(nome.match(regex)){
	    	
	    $(id).removeClass('valid').addClass('invalid');
	    $(idError).parent().find('.error').fadeIn(500);		    
	    return false;
	    	
	    }else {  $(id).removeClass('invalid').addClass('valid'); return true; }
	}, message); 
}

/* ******************************************************************************************************** */

/** Validate job position
 *  
 * @author Wellington da Silva : 2021-05-09
 * @summary function used to check job position validation
 * @since version 1.0
 * @version 1.1 
 * @description check job position
 * @copyright Tracevia S/A 2021
 * @param {string} id field id
 * @param {string} idError field error id
 * @param {string} message validation message
 * @returns {string} returns validation message
**/

function validateJobPosition(id, idError, message){

jQuery.validator.addMethod("isJobPosition", function(value, element) { 
    var job = $(id).val();
    var regex = new RegExp(/[^a-zA-Z0-9\u00C0-\u00FF ]$/); // Chararcters for job position composition
    if(job.match(regex)){
    	
    $(id).removeClass('valid').addClass('invalid');
    $(idError).parent().find('.error').fadeIn(500);		    
    return false;
    	
    }else { $(id).removeClass('invalid').addClass('valid'); return true; }
}, message ); 
}

/* ******************************************************************************************************** */

/** Validate username
 *  
 * @author Wellington da Silva : 2021-05-09
 * @summary function used to check username validation
 * @since version 1.0
 * @version 1.1 
 * @description check username
 * @copyright Tracevia S/A 2021
 * @param {string} id field id
 * @param {string} idError field error id
 * @param {string} message validation message
 * @returns {string} returns validation message
**/

function validateUserName(id, idError, message){

		jQuery.validator.addMethod("isUsername", function(value, element) { 
		    var username = $(id).val();
		    var regex = new RegExp(/^([a-zA-Z]+[a-zA-Z0-9\._]*[a-zA-Z0-9]+)$/); // Chararcters for name composition
		    if(!username.match(regex)){
		    	
		    $(id).removeClass('valid').addClass('invalid');
		     $(idError).parent().find('.error').fadeIn(500);		    
		    return false;
		    	
		    }else {  $(id).removeClass('invalid').addClass('valid'); return true;}
		}, message ); 
	}

/* ********************************** VALIDATION TEMPLATE FUNCTIONS ******************************* */

/** Validate login
 *
 * @author Wellington da Silva : 2021-05-07
 * @summary Validation function used only for login page
 * @since version 1.0
 * @version 1.1 
 * @description validate login form
 * @copyright Tracevia S/A 2021 
 * @param {string} formId  form id to be checked
 * @param {string} usernameRequired username required message
 * @param {string} usernameMinLenght username min length message
 * @param {string} usernameMaxLenght username max length message
 * @param {string} passwordRequired password required message
 * @param {string} passwordMinLenght password min length message
 * @param {string} passwordMaxLenght password max length message
 * @returns {void}
**/

function validateLogin(formId, usernameRequired, usernameMinLenght, usernameMaxLenght,
	passwordRequired, passwordMinLenght, passwordMaxLenght) {

	$(formId).validate({
		rules: {
			username: {
				required: true,
				minlength: 4,
				maxlength: 80						
			},
			password: {
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

		errorClass: "error",
		validClass: "success",
		errorElement: "label",
		
		errorPlacement: function (error, element) {
			//Place elements for place errors	 
		},
		success: function (label, element) {
			//If no have errors set check success status	
			//Show span validation icon
			$(element.form).find("span[for=" + element.id + "]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');
			//FontAwesome Icon Check for success
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-check success'></i>");
		},

		// highlight - add class error in case of errors
		highlight: function (element, errorClass, validClass) {

			$(element.form).find("label[for=" + element.id + "]").addClass(errorClass);
			$(element.form).find("input[id=" + element.id + "]").removeClass('valid').addClass('invalid');
		
			//Show span validation icon
			$(element.form).find("span[for=" + element.id + "]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');
			//FontAwesome Icon Times for error
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-times error'></i>");
		},

		// unhighlight - remove error class if error was solved
		unhighlight: function (element, errorClass, validClass) {

			$(element.form).find("label[for=" + element.id + "]").removeClass(errorClass);
			$(element.form).find("input[id=" + element.id + "]").removeClass('invalid').addClass('valid');
		
			//FontAwesome Icon Check for success
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-check success'></i>");
		}
	});
}

/* **************************************************************************************************** */

/** Validate Email
 *
 * @author Wellington da Silva : 2021-05-07
 * @summary Validation function used only for forget page
 * @since version 1.0
 * @version 1.1 
 * @description validate forget form
 * @copyright Tracevia S/A 2021 
 * @param {string} formId  form id to be checked
 * @param {string} emailRequired email required message
 * @param {string} emailMinLenght email min length message
 * @returns {void}
**/

function validateEmailForm(formId, emailRequired, emailMaxLenght) {

	$(formId).validate({
		rules: {
			email: {
				required: true,			
				maxlength: 80,
				isEmail: true									
			}		 
		  },

		messages: {
			email: {
				required: emailRequired,				
				maxlength: emailMaxLenght				
			}		
		  },

		errorClass: "error",
		validClass: "success",
		errorElement: "label",
		
		errorPlacement: function (error, element) {
			//Place elements for place errors	 
		},
		success: function (label, element) {
			//If no have errors set check success status	
			//Show span validation icon
			$(element.form).find("span[for=" + element.id + "]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');
			//FontAwesome Icon Check for success
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-check success'></i>");
		},

		// highlight - add class error in case of errors
		highlight: function (element, errorClass, validClass) {

			$(element.form).find("label[for=" + element.id + "]").addClass(errorClass);
			$(element.form).find("input[id=" + element.id + "]").removeClass('valid').addClass('invalid');
		
			//Show span validation icon
			$(element.form).find("span[for=" + element.id + "]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');
			//FontAwesome Icon Times for error
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-times error'></i>");
		},

		// unhighlight - remove error class if error was solved
		unhighlight: function (element, errorClass, validClass) {

			$(element.form).find("label[for=" + element.id + "]").removeClass(errorClass);
			$(element.form).find("input[id=" + element.id + "]").removeClass('invalid').addClass('valid');
		
			//FontAwesome Icon Check for success
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-check success'></i>");
		}
	});
}

/* **************************************************************************************************** */


/** Validate creation form
 *
 * @author Wellington da Silva : 2021-05-07
 * @summary Validation function used only equipments creation in map page or realtime page
 * @since version 1.0
 * @version 1.1 
 * @description validate creation form
 * @copyright Tracevia S/A 2021 
 * @param {string} formId  form id to be checked
 * @param {string} equipsMessage equipments required message
 * @param {string} idMessage equipId required message
 * @param {string} numberMessage equipId number only message
 * @param {string} maxLengthMessage equipName max lenght message
 * @param {string} equipNameMessage equipName required message
 * @param {string} citiesMessage cities required message
 * @param {string} roadsMessage roads required message
 * @param {string} kmMessage km required message
 * @returns {void}
**/

function validateCreationForm(formId, equipsMessage, idMessage, numberMessage, maxLengthMessage, equipNameMessage,
	citiesMessage, roadsMessage, kmMessage) {

	 $(formId).validate({
		 rules: {
			equips: {
				required: true

			}, equipId: {
				required: true,
				number: true
			},
			equipName: {
				required: true,
				maxlength: 10

			},
			cities: {
				required: true
			},
			roads: {
				required: true
			},
			km: {
				required: true
			}
		},

		messages: {

			equips: { required: equipsMessage },
			equipId: {
				required: idMessage,
				number: numberMessage
			}, equipName: { required: equipNameMessage, maxlength: maxLengthMessage },
			cities: { required: citiesMessage },
			roads: { required: roadsMessage },
			km: { required: kmMessage }

		},

		errorClass: "error",
		validClass: "success",
		errorElement: "label",

		errorPlacement: function (error, element) {
			//Place elements for place errors	 

		},

		success: function (label, element) {

			//If no have errors set check success status	
			//Show span validation icon
			$(element.form).find("span[for=" + element.id + "]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');

			//FontAwesome Icon Check for success
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-check success'></i>");

		},


		// highlight - add class error in case of errors

		highlight: function (element, errorClass, validClass) {
			$(element.form).find("label[for=" + element.id + "]").addClass(errorClass);

			$(element.form).find("input[id=" + element.id + "]").removeClass('valid').addClass('invalid');
			$(element.form).find("select[id=" + element.id + "]").removeClass('valid').addClass('invalid');

			//Show span validation icon
			$(element.form).find("span[for=" + element.id + "]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');

			//FontAwesome Icon Times for error
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-times error'></i>");

		},

		// unhighlight - remove error class if error was solved

		unhighlight: function (element, errorClass, validClass) {
			$(element.form).find("label[for=" + element.id + "]").removeClass(errorClass);

			$(element.form).find("input[id=" + element.id + "]").removeClass('invalid').addClass('valid');
			$(element.form).find("select[id=" + element.id + "]").removeClass('invalid').addClass('valid');

			//FontAwesome Icon Check for success
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-check success'></i>");

		}
	});

}

/* **************************************************************************************************** */

/** Validate edition form
 *
 * @author Wellington da Silva : 2021-05-07
 * @summary Validation function used only equipments edition in map page or realtime page
 * @since version 1.0
 * @version 1.1 
 * @description validate edition form
 * @copyright Tracevia S/A 2021 
 * @param {string} formId  form id to be checked
 * @param {string} maxLengthMessage equipName max lenght message
 * @param {string} equipNameMessage equipName required message
 * @param {string} citiesMessage cities required message
 * @param {string} roadsMessage roads required message
 * @param {string} kmMessage km required message
 * @returns {void}
**/

function validateEditionForm(formId, maxLengthMessage, equipNameMessage, citiesMessage, roadsMessage, kmMessage) {

	$(formId).validate({
		rules: {
			equipNameEdit: {
				required: true,
				maxlength: 10

			},
			citiesEdit: {
				required: true
			},
			roadsEdit: {
				required: true
			},
			kmEdit: {
				required: true
			}
		},

		messages: {

			equipNameEdit: {
				required: equipNameMessage,
				maxlength: maxLengthMessage
			},
			citiesEdit: { required: citiesMessage },
			roadsEdit: { required: roadsMessage },
			kmEdit: { required: kmMessage }
		},

		errorClass: "error",
		validClass: "success",
		errorElement: "label",

		errorPlacement: function (error, element) {
			//Place elements for place errors	 

		},

		success: function (label, element) {

			//If no have errors set check success status	
			//Show span validation icon
			$(element.form).find("span[for=" + element.id + "]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');

			//FontAwesome Icon Check for success
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-check success'></i>");

		},

		// highlight - add class error in case of errors

		highlight: function (element, errorClass, validClass) {
			$(element.form).find("label[for=" + element.id + "]").addClass(errorClass);

			$(element.form).find("input[id=" + element.id + "]").removeClass('valid').addClass('invalid');
			$(element.form).find("select[id=" + element.id + "]").removeClass('valid').addClass('invalid');

			//Show span validation icon
			$(element.form).find("span[for=" + element.id + "]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');

			//FontAwesome Icon Times for error
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-times error'></i>");

		},

		// unhighlight - remove error class if error was solved

		unhighlight: function (element, errorClass, validClass) {
			$(element.form).find("label[for=" + element.id + "]").removeClass(errorClass);

			$(element.form).find("input[id=" + element.id + "]").removeClass('invalid').addClass('valid');
			$(element.form).find("select[id=" + element.id + "]").removeClass('invalid').addClass('valid');

			//FontAwesome Icon Check for success
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-check success'></i>");


		}
	});

}

/* **************************************************************************************************** */

/** Validation template 1
 *  
 * @author Wellington da Silva : 2021-05-07
 * @summary Validation function used when there is a start date and end date in the form template
 * @since version 1.0
 * @version 1.1 
 * @description Validate form template 1
 * @copyright Tracevia S/A 2021 
 * @param {string} formId  form id to be checked
 * @param {string} dateStartId dateStart input id
 * @param {string} equipMessage required equipment message
 * @param {string} periodsMessage required periods message
 * @param {string} dateStartMessage required dateStart message
 * @param {string} dateEndMessage required dateEnd message
 * @param {string} validDateMessage check validation date message
 * @returns {void}
**/

function validationTemplate1(formId, dateStartId, equipMessage, periodsMessage, dateStartMessage, dateEndMessage, validDateMessage) {

	$(formId).validate({
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
				greaterThan: dateStartId,
				maxDate: maxDays,
				dateITA: true
			}
		},

		messages: {

			equip: { required: equipMessage },

			periods: { required: periodsMessage },

			dateStart: {
				required: dateStartMessage,
				dateITA: validDateMessage
			},

			dateEnd: {
				required: dateEndMessage,
				dateITA: validDateMessage
			},
		},

		errorClass: "error",
		validClass: "success",
		errorElement: "label",

		errorPlacement: function (error, element) {	//Place elements for place errors
		},

		success: function (label, element) {

			//If no have errors set check success status	
			//Show span validation icon
			$(element.form).find("span[for=" + element.id + "]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');

			//FontAwesome Icon Check for success
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-check success'></i>");

		},

		// highlight - add class error in case of errors

		highlight: function (element, errorClass, validClass) {
			$(element.form).find("label[for=" + element.id + "]").addClass(errorClass);

			$(element.form).find("input[id=" + element.id + "]").removeClass('valid').addClass('invalid');
			$(element.form).find("select[id=" + element.id + "]").removeClass('valid').addClass('invalid');

			//Show span validation icon
			$(element.form).find("span[for=" + element.id + "]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');

			//FontAwesome Icon Times for error
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-times error'></i>");

			//Multiselect button configuration to set invalid
			if ($(element.form).find("select[id=" + element.id + "]").hasClass('invalid')) {
				$(element).next('.btn-group').find('button').removeClass('valid').addClass('invalid');

			}
		},

		// unhighlight - remove error class if error was solved

		unhighlight: function (element, errorClass, validClass) {
			$(element.form).find("label[for=" + element.id + "]").removeClass(errorClass);

			$(element.form).find("input[id=" + element.id + "]").removeClass('invalid').addClass('valid');
			$(element.form).find("select[id=" + element.id + "]").removeClass('invalid').addClass('valid');

			//FontAwesome Icon Check for success
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-check success'></i>");

			//Multiselect button configuration to set valid
			if ($(element.form).find("select[id=" + element.id + "]").hasClass('valid')) {
				$(element).next('.btn-group').find('button').removeClass('invalid').addClass('valid');
			}
		}
	});
}

/* ******************************************************************************************************** */

/** Validation template 2
 *  
 * @author Wellington da Silva : 2021-05-07
 * @summary Validation function used when there is a start date, end date and vehicles field in the form template
 * @since version 1.0
 * @version 1.1 
 * @description Validate form template 2
 * @copyright Tracevia S/A 2021 
 * @param {string} formId  form id to be checked
 * @param {string} dateStartId dateStart input id
 * @param {string} equipsMessage required equipments multiselect message
 * @param {string} vehiclesMessage required vehicles multiselect message
 * @param {string} periodsMessage required periods message
 * @param {string} dateStartMessage required dateStart message
 * @param {string} dateEndMessage required dateEnd message
 * @param {string} validDateMessage check validation date message
 * @returns {void}
**/

function validationTemplate2(formId, dateStartId, equipsMessage, vehiclesMessage, periodsMessage, dateStartMessage, dateEndMessage, validDateMessage) {

	$(formId).validate({
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
				greaterThan: dateStartId,
				maxDate: maxDays,
				dateITA: true
			}
		},

		messages: {

			equips: { required: equipsMessage },

			vehicles: { required: vehiclesMessage },

			periods: { required: periodsMessage },

			dateStart: {
				required: dateStartMessage,
				dateITA: validDateMessage
			},

			dateEnd: {
				required: dateEndMessage,
				dateITA: validDateMessage
			},
		},

		errorClass: "error",
		validClass: "success",
		errorElement: "label",

		errorPlacement: function (error, element) { //Place elements for place errors	 
		},

		success: function (label, element) {

			//If no have errors set check success status	
			//Show span validation icon
			$(element.form).find("span[for=" + element.id + "]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');

			//FontAwesome Icon Check for success
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-check success'></i>");

		},

		// highlight - add class error in case of errors

		highlight: function (element, errorClass, validClass) {
			$(element.form).find("label[for=" + element.id + "]").addClass(errorClass);

			$(element.form).find("input[id=" + element.id + "]").removeClass('valid').addClass('invalid');
			$(element.form).find("select[id=" + element.id + "]").removeClass('valid').addClass('invalid');

			//Show span validation icon
			$(element.form).find("span[for=" + element.id + "]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');

			//FontAwesome Icon Times for error
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-times error'></i>");

			//Multiselect button configuration to set invalid
			if ($(element.form).find("select[id=" + element.id + "]").hasClass('invalid')) {
				$(element).next('.btn-group').find('button').removeClass('valid').addClass('invalid');

			}
		},

		// unhighlight - remove error class if error was solved

		unhighlight: function (element, errorClass, validClass) {
			$(element.form).find("label[for=" + element.id + "]").removeClass(errorClass);

			$(element.form).find("input[id=" + element.id + "]").removeClass('invalid').addClass('valid');
			$(element.form).find("select[id=" + element.id + "]").removeClass('invalid').addClass('valid');

			//FontAwesome Icon Check for success
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-check success'></i>");

			//Multiselect button configuration to set valid
			if ($(element.form).find("select[id=" + element.id + "]").hasClass('valid')) {
				$(element).next('.btn-group').find('button').removeClass('invalid').addClass('valid');
			}
		}
	});
}

/* ******************************************************************************************************** */

/** Validation template 3
 *  
 * @author Wellington da Silva : 2021-05-07
 * @summary Validation function used when there is a month and year fields in the form template
 * @since version 1.0
 * @version 1.1 
 * @description Validate form template 3
 * @copyright Tracevia S/A 2021 
 * @param {string} formId  form id to be checked
 * @param {string} monthMessage required month message
 * @param {string} yearMessage required year message 
 * @returns {void}
**/

function validationTemplate3(formId, equipMessage, monthMessage, yearMessage) {

	$(formId).validate({
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

			equip: { required: equipMessage },
			month: { required: monthMessage },
			year: { required: yearMessage },

		},

		errorClass: "error",
		validClass: "success",
		errorElement: "label",
		errorPlacement: function (error, element) { //Place elements for place errors	 
		},

		success: function (label, element) {

			//If no have errors set check success status	
			//Show span validation icon
			$(element.form).find("span[for=" + element.id + "]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');

			//FontAwesome Icon Check for success
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-check success'></i>");

		},

		// highlight - add class error in case of errors

		highlight: function (element, errorClass, validClass) {
			$(element.form).find("label[for=" + element.id + "]").addClass(errorClass);
			$(element.form).find("input[id=" + element.id + "]").removeClass('valid').addClass('invalid');
			$(element.form).find("select[id=" + element.id + "]").removeClass('valid').addClass('invalid');

			//Show span validation icon
			$(element.form).find("span[for=" + element.id + "]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');
			//FontAwesome Icon Times for error
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-times error'></i>");
			//Multiselect button configuration to set invalid
			if ($(element.form).find("select[id=" + element.id + "]").hasClass('invalid')) {
				$(element).next('.btn-group').find('button').removeClass('valid').addClass('invalid');
			}

		},

		// unhighlight - remove error class if error was solved

		unhighlight: function (element, errorClass, validClass) {
			$(element.form).find("label[for=" + element.id + "]").removeClass(errorClass);

			$(element.form).find("input[id=" + element.id + "]").removeClass('invalid').addClass('valid');
			$(element.form).find("select[id=" + element.id + "]").removeClass('invalid').addClass('valid');

			//FontAwesome Icon Check for success
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-check success'></i>");
			//Multiselect button configuration to set valid
			if ($(element.form).find("select[id=" + element.id + "]").hasClass('valid')) {
				$(element).next('.btn-group').find('button').removeClass('invalid').addClass('valid');
			}
		}

	});
}

/* ******************************************************************************************************** */

/** Validation template 4
 *  
 * @author Wellington da Silva : 2021-05-07
 * @summary Validation function used when there is a periods, month and year fields in the form template
 * @since version 1.0
 * @version 1.1 
 * @description Validate form template 4
 * @copyright Tracevia S/A 2021 
 * @param {string} formId  form id to be checked
 * @param {string} equipsMessage required equipments multiselect message
 * @param {string} periodMessage required periods message
 * @param {string} monthMessage required month message
 * @param {string} yearMessage required year message 
 * @returns {void}
**/

function validationTemplate4(formId, equipsMessage, periodMessage, monthMessage, yearMessage) {

	$(formId).validate({
		ignore: [],
		rules: {
			equips: "required",
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
			equips: { required: equipsMessage },
			periods: { required: periodMessage },
			month: { required: monthMessage },
			year: { required: yearMessage }

		},

		errorClass: "error",
		validClass: "success",
		errorElement: "label",

		errorPlacement: function (error, element) { //Place elements for place errors	 		   
		},

		success: function (label, element) {

			//If no have errors set check success status	
			//Show span validation icon
			$(element.form).find("span[for=" + element.id + "]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');

			//FontAwesome Icon Check for success
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-check success'></i>");

		},

		// highlight - add class error in case of errors

		highlight: function (element, errorClass, validClass) {
			$(element.form).find("label[for=" + element.id + "]").addClass(errorClass);

			$(element.form).find("input[id=" + element.id + "]").removeClass('valid').addClass('invalid');
			$(element.form).find("select[id=" + element.id + "]").removeClass('valid').addClass('invalid');

			//Show span validation icon
			$(element.form).find("span[for=" + element.id + "]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');

			//FontAwesome Icon Times for error
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-times error'></i>");

			//Multiselect button configuration to set invalid
			if ($(element.form).find("select[id=" + element.id + "]").hasClass('invalid')) {
				$(element).next('.btn-group').find('button').removeClass('valid').addClass('invalid');

			}

		},

		// unhighlight - remove error class if error was solved

		unhighlight: function (element, errorClass, validClass) {
			$(element.form).find("label[for=" + element.id + "]").removeClass(errorClass);

			$(element.form).find("input[id=" + element.id + "]").removeClass('invalid').addClass('valid');
			$(element.form).find("select[id=" + element.id + "]").removeClass('invalid').addClass('valid');

			//FontAwesome Icon Check for success
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-check success'></i>");

			//Multiselect button configuration to set valid
			if ($(element.form).find("select[id=" + element.id + "]").hasClass('valid')) {
				$(element).next('.btn-group').find('button').removeClass('invalid').addClass('valid');
			}
		}
	});

}

/* ******************************************************************************************************** */

/** Validation template 5
 *  
 * @author Wellington da Silva : 2021-05-07
 * @summary Validation function used when there is a year, start month and end month fields in the form template
 * @since version 1.0
 * @version 1.1 
 * @description Validate form template 5
 * @copyright Tracevia S/A 2021 
 * @param {string} formId  form id to be checked
 * @param {string} equipMessage required equipment message
 * @param {string} yearMessage required year message
 * @param {string} startMonthMessage required start month message
 * @param {string} endMonthMessage required end month message 
 * @returns {void}
 **/

function validationTemplate5(formId, equipMessage, yearMessage, startMonthMessage, endMonthMessage) {

	$(formId).validate({
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

			equip: { required: equipMessage },
			year: { required: yearMessage },
			start_month: { required: startMonthMessage },
			end_month: { required: endMonthMessage },

		},

		errorClass: "error",
		validClass: "success",
		errorElement: "label",

		errorPlacement: function (error, element) {
			//Place elements for place errors	 

		},

		success: function (label, element) {

			//If no have errors set check success status	
			//Show span validation icon
			$(element.form).find("span[for=" + element.id + "]").removeClass('valid-icon-hidden').addClass('valid-icon-visible')

			//FontAwesome Icon Check for success
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-check success'></i>");

		},

		// highlight - add class error in case of errors

		highlight: function (element, errorClass, validClass) {
			$(element.form).find("label[for=" + element.id + "]").addClass(errorClass);

			$(element.form).find("input[id=" + element.id + "]").removeClass('valid').addClass('invalid');
			$(element.form).find("select[id=" + element.id + "]").removeClass('valid').addClass('invalid');

			//Show span validation icon
			$(element.form).find("span[for=" + element.id + "]").removeClass('valid-icon-hidden').addClass('valid-icon-visible')

			//FontAwesome Icon Times for error
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-times error'></i>");

		},

		// unhighlight - remove error class if error was solved

		unhighlight: function (element, errorClass, validClass) {
			$(element.form).find("label[for=" + element.id + "]").removeClass(errorClass);

			$(element.form).find("input[id=" + element.id + "]").removeClass('invalid').addClass('valid');
			$(element.form).find("select[id=" + element.id + "]").removeClass('invalid').addClass('valid');

			//FontAwesome Icon Check for success
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-check success'></i>");

		}
	});
}

/* ******************************************************************************************************** */

/** Validation template 6
 *  
 * @author Wellington da Silva : 2021-05-07
 * @summary Validation function used only to validate equipment select in the form template
 * @since version 1.0
 * @version 1.1 
 * @description Validate form template 6
 * @copyright Tracevia S/A 2021 
 * @param {string} formId  form id to be checked
 * @param {string} equipMessage required equipment message
 * @returns {void}
 **/

function validationTemplate6(formId, equipMessage) {

	$(formId).validate({
		rules: {
			equip: {
				required: true
			}
		},
		messages: {
			equip: { required: equipMessage }
		},

		errorClass: "error",
		validClass: "success",
		errorElement: "label",
		errorPlacement: function (error, element) {
			//Place elements for place errors	 

		},

		success: function (label, element) {

			//If no have errors set check success status	
			//Show span validation icon
			$(element.form).find("span[for=" + element.id + "]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');

			//FontAwesome Icon Check for success
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-check success'></i>");

		},

		// highlight - add class error in case of errors

		highlight: function (element, errorClass, validClass) {
			$(element.form).find("label[for=" + element.id + "]").addClass(errorClass);
			$(element.form).find("input[id=" + element.id + "]").removeClass('valid').addClass('invalid');
			$(element.form).find("select[id=" + element.id + "]").removeClass('valid').addClass('invalid');

			//Show span validation icon
			$(element.form).find("span[for=" + element.id + "]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');
			//FontAwesome Icon Times for error
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-times error'></i>");

		},

		// unhighlight - remove error class if error was solved

		unhighlight: function (element, errorClass, validClass) {
			$(element.form).find("label[for=" + element.id + "]").removeClass(errorClass);

			$(element.form).find("input[id=" + element.id + "]").removeClass('invalid').addClass('valid');
			$(element.form).find("select[id=" + element.id + "]").removeClass('invalid').addClass('valid');

			//FontAwesome Icon Check for success
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-check success'></i>");

		}
	});
}

/* ******************************************************************************************************** */

/** Validate user form
 *
 * @author Wellington da Silva : 2021-05-07
 * @summary Validation function used only for user register page
 * @since version 1.0
 * @version 1.1 
 * @description validate user register form
 * @copyright Tracevia S/A 2021 
 * @param {string} formId  form id to be checked
 * @param {string} usernameRequired username required message
 * @param {string} usernameMinLenght username min length message
 * @param {string} usernameMaxLenght username max length message
 * @param {string} passwordRequired password required message
 * @param {string} passwordMinLenght password min length message
 * @param {string} passwordMaxLenght password max length message
 * @returns {void}
**/

function validateUserForm(formId, minLenghtMsg, maxLenghtMsg, fullNameMsg, jobPositionMsg, 
	emailMsg, usernameMsg, permissionMsg){

	$(formId).validate({
		rules:{
			fullname:{
				required: true,					
				minlength: 5,
				maxlength: 60,
				isName: true
			},
			jobPosition:{
				required: true,					
				minlength: 5,
				maxlength: 60,
				isJobPosition: true
			},
			email:{
				required: true,					
				maxlength: 80,
				isEmail: true
			},
			username:{
			required: true,	
				minlength: 4,
				maxlength: 15,
				isUsername: true			
			},
			permissions:{
			  required: true			
			}
		},

		messages: {		
			 fullname: {
				required: fullNameMsg,
				minlength: minLenghtMsg,
				maxlength: maxLenghtMsg
			},
			 jobPosition: {
				required: jobPositionMsg,
				minlength: minLenghtMsg,
				maxlength: maxLenghtMsg
			},
			 email: {
				required: emailMsg,
				minlength: minLenghtMsg,
				maxlength: maxLenghtMsg
			},
			 username: {
				required: usernameMsg,
					minlength: minLenghtMsg,
					maxlength: maxLenghtMsg
			},
			 permissions: {
				required: permissionMsg          
			}
		  },												
	
		  errorClass: "error",
		  validClass: "success",
		  errorElement: "label",

		  errorPlacement: function (error, element) {	//Place elements for place errors
		  },
		  success: function (label, element) {
			  //If no have errors set check success status	
			  //Show span validation icon
			  $(element.form).find("span[for=" + element.id + "]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');
			  //FontAwesome Icon Check for success
			  $(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-check success'></i>");
		  },
		  // highlight - add class error in case of errors
		  highlight: function (element, errorClass, validClass) {
			  $(element.form).find("label[for=" + element.id + "]").addClass(errorClass);
			  $(element.form).find("input[id=" + element.id + "]").removeClass('valid').addClass('invalid');
			  $(element.form).find("select[id=" + element.id + "]").removeClass('valid').addClass('invalid');
			  //Show span validation icon
			  $(element.form).find("span[for=" + element.id + "]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');
			  //FontAwesome Icon Times for error
			  $(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-times error'></i>");
			  //Multiselect button configuration to set invalid
			  if ($(element.form).find("select[id=" + element.id + "]").hasClass('invalid')) {
				  $(element).next('.btn-group').find('button').removeClass('valid').addClass('invalid');
			  }
		  },
		  // unhighlight - remove error class if error was solved
		  unhighlight: function (element, errorClass, validClass) {
			  $(element.form).find("label[for=" + element.id + "]").removeClass(errorClass);
			  $(element.form).find("input[id=" + element.id + "]").removeClass('invalid').addClass('valid');
			  $(element.form).find("select[id=" + element.id + "]").removeClass('invalid').addClass('valid');
			  //FontAwesome Icon Check for success
			  $(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-check success'></i>");
			  //Multiselect button configuration to set valid
			  if ($(element.form).find("select[id=" + element.id + "]").hasClass('valid')) {
				  $(element).next('.btn-group').find('button').removeClass('invalid').addClass('valid');
			  }
		  }	                       	                                                         
    });
}

/* **************************************************************************************************** */

/** Validate change password
 *
 * @author Wellington da Silva : 2021-05-07
 * @summary Validation function used only for login page
 * @since version 1.0
 * @version 1.1 
 * @description validate login form
 * @copyright Tracevia S/A 2021 
 * @param {string} formId  form id to be checked
 * @param {string} newPasswordId  new password id field
 * @param {string} equalsToMsg equals to message
 * @param {string} passwordMinLenght password min length message
 * @param {string} passwordMaxLenght password max length message
 * @param {string} passwordRequired password required message
 * @param {string} newPasswordMinLenght new password min length message
 * @param {string} confirmPasswordMaxLenght confirm password max length message
 * @returns {void}
**/

function validateChangePassword(formId, newPasswordId, equalsToMsg, passwordMinLenght, passwordMaxLenght,
	passwordRequired, newPasswordRequired, confirmPasswordRequired) {

	$(formId).validate({			
			rules:{
				password:{
					required: true,					
					minlength: 6,
					maxlength: 15
				},
				newPassword:{
					required: true,					
					minlength: 6,
					maxlength: 15
				},
				confPassword:{
					required: true,					
					minlength: 6,
					maxlength: 15,
					equalTo: newPasswordId
				}
			  
			}, messages:{
				   password:{
					 required: passwordRequired,
					 minlength: passwordMinLenght,
					 maxlength: passwordMaxLenght
				  },
				   newPassword:{
					required: newPasswordRequired,
					minlength: passwordMinLenght,
					maxlength: passwordMaxLenght
				  },
				  confPassword:{
					required: confirmPasswordRequired,
					minlength: passwordMinLenght,
					maxlength: passwordMaxLenght,
					equalTo: equalsToMsg
					
				  }	       
			   },								
						
		errorClass: "error",
		validClass: "success",
		errorElement: "label",
		
		errorPlacement: function (error, element) {
			//Place elements for place errors	 
		},
		success: function (label, element) {
			//If no have errors set check success status	
			//Show span validation icon
			$(element.form).find("span[for=" + element.id + "]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');
			//FontAwesome Icon Check for success
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-check success'></i>");
		},

		// highlight - add class error in case of errors
		highlight: function (element, errorClass, validClass) {

			$(element.form).find("label[for=" + element.id + "]").addClass(errorClass);
			$(element.form).find("input[id=" + element.id + "]").removeClass('valid').addClass('invalid');
		
			//Show span validation icon
			$(element.form).find("span[for=" + element.id + "]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');
			//FontAwesome Icon Times for error
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-times error'></i>");
		},

		// unhighlight - remove error class if error was solved
		unhighlight: function (element, errorClass, validClass) {

			$(element.form).find("label[for=" + element.id + "]").removeClass(errorClass);
			$(element.form).find("input[id=" + element.id + "]").removeClass('invalid').addClass('valid');
		
			//FontAwesome Icon Check for success
			$(element.form).find("span[for=" + element.id + "]").html("<i class='fa fa-check success'></i>");
		}
	});
}

/* **************************************************************************************************** */