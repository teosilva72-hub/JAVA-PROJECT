function getTr(){
	var table = $('#ocr-table').DataTable({
		language: {
			"search": "",
			searchPlaceholder: "Buscar"
		},		
		"select": true,
		"autoWidth": true,			  	   	
		"scrollY": "38vh",
		"paging": false,
		"bInfo" : false
	});
	$('.dataTables_length').removeClass('bs-select');
	$('.dataTables_filter').css('margin-left', '-100px')
	$('.dataTables_wrapper ').css('margin-left', '20px')
	
	$('#ocr-table tbody').on( 'click', 'tr', function () {
   var event = $(table.row( this ).data()[0]).text();
	document.getElementById("event").value = event
	
	if(event > 0){
		$('[id$=btnPdf]').prop('disabled', false);
		btnTable()
	}else{
		dataPicker()
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
	$('[id$=btnTable]').click();
	preventDefault();
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
}
function updateView(){
	$( "[id$=updateView]" ).click();
	preventDefault();
}
function modalHide(){
	$('[id$=popup]').modal('hide')
	$('.modal-backdrop').addClass('hide')
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
function validador(){
	var dtInitial = document.getElementById("dateInitial")
	var hourInitial = document.getElementById("hourInitial")
	var minuteInitial = document.getElementById("minuteInitial")
	var dateFinal = document.getElementById("dateFinal")
	var hourFinal = document.getElementById("hourFinal")
	var minuteFinal = document.getElementById("minuteFinal")

	if(dtInitial.value == "" || hourInitial.value==""||
	minuteInitial.value == ""|| dateFinal.value == ""||
	hourFinal.value == ""|| minuteFinal.value == ""){
		$('.ll').addClass('error')
		$('.ll').removeClass('ok')
		return false
	}else{
		dataPicker()
		loading()
		$('.ll').removeClass('error')
		$('.ll').addClass('ok')
		$('#modalPesquisa').modal('hide')
		$('[id$=setForm]').click();
		return true
	}
}
function setPdf(){
	$('[id$=gerarPdf]').click();
	$('#modalPdf').modal('hide')
	disabledBtn()
}
function updateDetails(){
	$('[id$=updateDetails]').click();
	preventDefault();
}
$(document).ready(function () {
	disabledBtn()
	filtro()
	updateDetails()
});