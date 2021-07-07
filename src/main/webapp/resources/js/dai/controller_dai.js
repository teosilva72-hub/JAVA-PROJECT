function getTr(){
	var table = $('#dai-table').DataTable({
		language: {
			"search": "",
			searchPlaceholder: "Buscar"
		},		
		"select": true,
		"autoWidth": true,			  	   	
		"scrollY": `${screen.availHeight / 2}px`,
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

const onFilterFunction = data => {
	var status = data.status;

	switch (status) {
		case "begin":
			break;

		case "complete":
			break;

		case "success":
			getTr();

			break;
	}
}

$(document).ready(function () {
	getTr()
	dataPicker()

	$("#btnDetalhes").click(function() {
		$("#DAIpopup").modal("show")
	})
});