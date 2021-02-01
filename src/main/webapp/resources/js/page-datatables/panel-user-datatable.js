//Método para Criar a datatable JQuery 
$(document).ready(function() {
  $('#user-table').DataTable({		
  select: false,
"searching": false,
 "autoWidth": true,			  	   	
 "scrollY": "52.25vh",
 "scrollCollapse": true,
 "paging": false,
 "bInfo" : false

 });
  $('.dataTables_length').removeClass('bs-select');	  
})