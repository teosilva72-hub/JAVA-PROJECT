function getTr(){
	var table = $('#ocr-table').DataTable({
		language: {
			"search": "",
			searchPlaceholder: "Buscar"
		},		
		"select": true,
		"autoWidth": true,			  	   	
		"scrollY": "60vh",
		"paging": false,
		"bInfo" : false
	});
}
function dataPicker(){
	var  dtInitial = $('[id$=dateInitial]')
	var dtFinal = $('[id$=dateFinal]')
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
function btnPopUp(){
	$( "[id$=popUp]" ).click();
	preventDefault();
}
function modalHide(){
	$('[id$=popup]').modal('hide')
	$('.modal-backdrop').addClass('hide')
}
function disabledBtn(){
	var x = document.getElementById('btnPdf')
	var f = document.getElementById('btnDetalhes')
	x.disabled = true
	f.disabled = true
	preventDefault();
}
$(document).ready(function () {
	btnPopUp()
	disabledBtn()
});