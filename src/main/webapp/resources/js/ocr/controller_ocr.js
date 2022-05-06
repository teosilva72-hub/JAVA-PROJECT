//VALIDATION DEFINITIONS

const SPANISH = "es_ES";
const ARGENTINE_SPANISH = "es_AR";
const COLOMBIAN_SPANISH = "es_CO"; 
const MEXICAN_SPANISH = "es_MX";
const AMERICAN_ENGLISH = "en_US";
const BRAZILIAN_PORTUGUESE = "pt_BR";

const maxDayCheck = 60;

let table;

// --------------------------------------------------

$(function () { 

	let validation = document.forms.validation;

 	 for (messages of validation)
		 window[messages.name] = messages.value
		
	// ----------------------------------------------------------
	
	//Language
   	$('.datepicker').datepickerLocale(language);
	
	 if(language == SPANISH || language == ARGENTINE_SPANISH || language == COLOMBIAN_SPANISH || language == MEXICAN_SPANISH)
        spanishValidationMessages();

		else if(language == AMERICAN_ENGLISH)
        englishValidationMessages();

    	else portugueseValidationMessages();

  // --------------------------------------------------------------

	validateReportOCR('#pesquisar', requiredFieldMsg)
	
	//Validate elements on change value
    //In this case check if the element is valid 
	validateOnChange('#camera')
	validateOnChange('#dateInitial')
	validateOnChange('#hourInitial')
	validateOnChange('#minuteInitial')
	validateOnChange('#dateFinal')
	validateOnChange('#hourFinal')
	validateOnChange('#minuteFinal')
    
	//Reset Fields on close modal
	resetFieldOnModalClose('#modalPesquisa', 'camera')
	resetFieldOnModalClose('#modalPesquisa', 'dateInitial')
	resetFieldOnModalClose('#modalPesquisa', 'hourInitial')
	resetFieldOnModalClose('#modalPesquisa', 'minuteInitial')
	resetFieldOnModalClose('#modalPesquisa', 'dateFinal')
	resetFieldOnModalClose('#modalPesquisa', 'hourFinal')
	resetFieldOnModalClose('#modalPesquisa', 'minuteFinal')
	    
    //Remove validation icons
    //click reset button action		 

	//Clean Form validation on close modal
    cleanValidationOnModalClose("#pesquisar", '#modalPesquisa');

    //Reset validation form
    //click reset button action
    resetFormValidation("#pesquisar", "reset-btn");
	
	// METHODS

	disabledBtn()
	//filtro()
	updateDetails()
	img_all()
	
	drawTable()


})

// --------------------------------------------------

function getTr(){
		
	$('#ocr-table tbody').on( 'click', 'tr', function () {
  
    let event = $(table.row( this ).data()[0]).text();
	document.getElementById("event").value = event
	
	let cam = $(table.row( this ).data()[2]).text();
	document.getElementById("camSet").value = cam
	
	if(event > 0){
		$('[id$=btnPdf]').prop('disabled', false);
		btnTable()
	}else{
		//dataPicker()
		disabledBtn()
	}
	
} );
}

function loading(){
	var x = document.querySelector(".c-loader")
	x.style.display = "block"
	setTimeout(function() {
   $('.c-loader').fadeOut('fast');
}, 5000);
}

function btnTable(){
	$('[id$=btnTable]').click(function(e){
		e.preventDefault();		
	});
}

function dataPicker(){
	var  dtInitial = $('[id$=dateInitial]')
	var dtFinal = $('[id$=dateFinal]')
	$('[id$=btnPdf]').prop('disabled', true);
    dtInitial.on('click', function() {
      $('[id$=dateInitial]').mask('9999-99-99')
    });
	dtFinal.on('click', function() {
	$('[id$=dateFinal]').mask('9999-99-99')
    });
	$('[id$=dateInitial]').datepicker({ 
		dateFormat: "yy-mm-dd",  
		changeYear: true,
		changeMonth: true,
	})
    $('[id$=dateFinal]').datepicker({
	dateFormat: "yy-mm-dd",
	changeYear: true,
	changeMonth: true
	})
	img_all()
}

function updateView(){
	$( "[id$=updateView]" ).click(function(e) {		
		e.preventDefault();				
	});
}

function disabledBtn(){
	$('[id$=btnPdf]').prop('disabled', true);
}

function filtro(){
	var filtro1 = document.getElementById("filtro1")
	var filtro2 = document.getElementById("filtro2")
	filtro1.addEventListener("change", function(e){
		e.preventDefault()
		filtro2.value = filtro1.value
		$('[id$=setFilter]').click();
	})
}

function setPdf(){
	$('[id$=gerarPdf]').click();
	$('#modalPdf').modal('hide')
	disabledBtn()
}

function updateDetails(){
	$('[id$=updateDetails]').click(function(e){	   
		e.preventDefault();		
	});
}

function img_all(){
	let img = $('[id$=img_all]')
	img.mouseleave(er =>{
		$('[id$=img_all_get]').val(img.val())
	})
}

 function drawTable() {
	
  table = $('#ocr-table').DataTable({    
		language: {
			"search": "",
			searchPlaceholder: "Buscar"
		},		
		"select": true,
		"autoWidth": true,			  	   	
		"scrollY": "38vh",
		"paging": true,
		"bInfo" : false       
   });

	$('.dataTables_length').removeClass('bs-select');
	$('.dataTables_filter').css('margin-left', '-100px')
	$('.dataTables_wrapper ').css('margin-left', '20px')

}
