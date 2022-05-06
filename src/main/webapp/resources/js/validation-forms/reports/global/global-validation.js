//VALIDATION DEFINITIONS

const SPANISH = "es_ES";
const ARGENTINE_SPANISH = "es_AR";
const COLOMBIAN_SPANISH = "es_CO"; 
const MEXICAN_SPANISH = "es_MX";
const AMERICAN_ENGLISH = "en_US";
const BRAZILIAN_PORTUGUESE = "pt_BR";

const maxDayCheck = 60;

$(function () {
	
	//Get equipments from backing bean
    //Create array
    var equipsArray = new Array();
   
    var validation = document.forms.validation;

    for (messages of validation)
        if (messages.name == "")
            equipsArray.push(messages.value);
       else window[messages.name] = messages.value

    //Create / Define multiselect
    defineMultiselect('[multiple]', '');      


   $.validator.methods.date = function (value, element) {
        //This is not ideal but Chrome passes dates through in ISO1901 format regardless of locale 
        //and despite displaying in the specified format.

        let a = value.split("/");
        let date = new Date(a[2], a[1] - 1, a[0])
        let timestamp = Date.parse(date);
        let maxRange = true;
        let range = true;
        let id = element.id

        if (id.endsWith("-start")) {
            let end = $(`#${id.substr(0, id.length - 6)}-end`).val()
            let endSplit = end.split("/")
            if (end)
                range = timestamp <= Date.parse(`${endSplit[2]}/${endSplit[1]}/${endSplit[0]}`)

        } else {
	
            let start = $(`#${id.substr(0, id.length - 4)}-start`).val()
            let startSplit = start.split("/")
            if (start){
	
	            let start_date = new Date(`${startSplit[2]}/${startSplit[1]}/${startSplit[0]}`);
                range = timestamp >= Date.parse(start_date);

                start_date.setDate(start_date.getDate() + maxDayCheck);

				maxRange = Date.parse(start_date) > timestamp;
        
          }
        }
        let formatedDate = `${date.getDate().toString().padStart(2, 0)}/${(date.getMonth() + 1).toString().padStart(2, 0)}/${date.getFullYear().toString().padStart(4, 0)}`
        let check = formatedDate.startsWith(value) && timestamp < Date.now() && range && maxRange

        return this.optional(element) || check;
    };

    //Language
   $('.datepicker').datepickerLocale(language);

   validationTemplate9("#report-form");

  if(language == SPANISH || language == ARGENTINE_SPANISH || language == COLOMBIAN_SPANISH || language == MEXICAN_SPANISH)
        spanishValidationMessages();

   else if(language == AMERICAN_ENGLISH)
        englishValidationMessages();

    else portugueseValidationMessages();

    validateInputFieldOnChange(); // Validate Input
    validateSelectFieldOnChange() // Validate Select
    validateCheckboxFieldOnChange(); // Validate Checkbox Field

	//Clean Form validation on close modal
    cleanModalOnClose("#report-form", '#modalReportOptions');

    //Reset validation form
    //click reset button action
    resetFormValidation("#report-form", "reset-btn");

});