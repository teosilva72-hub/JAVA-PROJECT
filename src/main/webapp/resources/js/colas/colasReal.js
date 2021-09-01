function getTrReal(){
	var table = $('#colas-tableReal').DataTable({
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
}

