//Método para Criar a datatable JQuery 
$(function () {
  $('#user-table').DataTable({
    select: false,
    "searching": false,
    "autoWidth": true,
    "scrollY": "52.25vh",
    "scrollX": true,
    "scrollCollapse": true,
    "paging": false,
    "bInfo": false

  });
  $('.dataTables_length').removeClass('bs-select');
})