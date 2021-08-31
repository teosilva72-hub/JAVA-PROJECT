function getTr(){
	var table = $('#colas-table').DataTable({
		language: {
			"search": "",
			searchPlaceholder: "Buscar"
		},		
		"select": true,
		"autoWidth": true,			  	   	
		"scrollY": `${screen.availHeight / 2}px`,
		"scrollCollapse": true,
		"order": [[ 2, 'desc' ]],
		"paging": false,
		"bInfo" : false
	});
	 	$('#colas-table tbody').on( 'click', 'tr', function () {
		//get info table
	   	var toll = $(table.row( this ).data()[0]).text()
		var lane = $(table.row( this ).data()[1]).text()
		var date = $(table.row( this ).data()[2]).text()
		var waiting_time = $(table.row( this ).data()[3]).text()
		//set info table
		document.getElementById("toll").value = toll
		document.getElementById("lane").value = lane
		document.getElementById("date").value = date
		document.getElementById("waiting_time").value = waiting_time
	});
	table
		.on( 'user-select', function ( e, dt, type, cell, originalEvent ) {
			if ( type === 'row' ) {
				$(originalEvent.target).closest("tr").find("[setParameter=setParameter]").click()
				$('[id$=btnPdf]').prop('disabled', false);
				$('[id$=btnDetalhes]').prop('disabled', false);
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
function disPdfDetail(){
	$('[id$=btnPdf]').prop('disabled', true);
	$('[id$=btnDetalhes]').prop('disabled', true);
}
function disabledBtn(){
	$('[id$=btnPdf]').prop('disabled', true);
	$('[id$=btnDetalhes]').prop('disabled', true);
}
$(document).ready(function () {
	getTr()
	dataPicker()
	disabledBtn()
	$("#btnDetalhes").click(function() {
		$("#COLASpopup").modal("show")
	})
});