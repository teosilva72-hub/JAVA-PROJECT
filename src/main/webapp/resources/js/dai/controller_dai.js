function getTr(){
	var table = $('#dai-table').DataTable({
		language: {
			"search": "",
			searchPlaceholder: "Buscar"
		},		
		"select": true,
		"autoWidth": true,			  	   	
		"scrollY": "60vh",
		"scrollCollapse": true,
		"order": [[ 4, 'desc' ], [ 5, 'desc' ]],
		"paging": false,
		"bInfo" : false
	});
	 
	table
		.on( 'user-select', function ( e, dt, type, cell, originalEvent ) {
			if ( type === 'row' ) {
				$(originalEvent.target).closest("tr").find("[setParameter=setParameter]").click()
			}
		} );

	$("[setParameter=setParameter]").click(function(e) {
		e.stopPropagation()
	})
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

function modalHide(){
	$('[id$=popup]').modal('hide')
	$('.modal-backdrop').addClass('hide')
}

$(document).ready(function () {
	$("#btnDetalhes").click(function() {
		$("#popup").modal("show")
	})
});