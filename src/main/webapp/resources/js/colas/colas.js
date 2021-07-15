function getTr(){
	var table = $('#dai-table').DataTable({
		language: {
			"search": "",
			searchPlaceholder: "Buscar"
		},		
		"select": true,
		"autoWidth": true,			  	   	
		"scrollY": `${screen.availHeight / 2}px`,
		"paging": false,
		"bInfo" : false
	});
}
function dataPicker(){
	var  date = $('#dateSearch')
    date.on('click', function() {
		date.mask('9999-99-99')
    });
	date.datepicker({ 
		dateFormat: "yy-mm-dd",  
		changeYear: true,
		changeMonth: true,
	})
}

function modalHide(){
	$('[id$=popup]').modal('hide')
	$('.modal-backdrop').addClass('hide')
}


function disPdfDetail(){
	$('[id$=btnPdf]').prop('disabled', true);
	$('[id$=btnDetalhes]').prop('disabled', true);
}
function disabledBtn(){
	$('[id$=btnPdf]').prop('disabled', true);
	$('[id$=btnDetalhes]').prop('disabled', true);
}
$(document).ready(function () {
	dataPicker()
	disabledBtn()
	
});