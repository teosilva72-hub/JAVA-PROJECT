$(document).ready(function () {
    //Call Datatables jQuery plugin
     $('#vehicle-count-table').DataTable({
        "scrollY": "50.3vh",
        "scrollX": true,
        "scrollCollapse": true,
        "paging": false, // false to disable pagination (or any other option)
        "bInfo": false,
        "deferRender": true        
   });


    $('.dataTables_length').removeClass('bs-select');

    //Call Datatables jQuery plugin
    $('#vehicle-count-flow-table').DataTable({
        "scrollY": "34.3vh",
        "scrollX": true,
        "scrollCollapse": true,
        "paging": false, // false to disable pagination (or any other option)
        "bInfo": false,
        "deferRender": true
    });
    $('.dataTables_length').removeClass('bs-select');

    //Call Datatables jQuery plugin
    $('#vehicle-monthly-flow-table').DataTable({
        "scrollY": "35.3vh",
        "scrollX": true,
        "scrollCollapse": true,
        "paging": false, // false to disable pagination (or any other option)
        "bInfo": false,
        "deferRender": true
    });
    $('.dataTables_length').removeClass('bs-select');

    //Call Datatables jQuery plugin
    $('#vehicle-period-flow-table').DataTable({
        "scrollY": "35.3vh",
        "scrollX": true,
        "scrollCollapse": true,
        "paging": false, // false to disable pagination (or any other option)
        "bInfo": false,
        "deferRender": true
    });
    $('.dataTables_length').removeClass('bs-select');

    //Call Datatables jQuery plugin
    $('#vehicle-weighing-table').DataTable({
        "scrollY": "49.3vh",
        "scrollX": true,
        "scrollCollapse": true,
        "paging": false, // false to disable pagination (or any other option)
        "bInfo": false,
        "deferRender": true
    });
    $('.dataTables_length').removeClass('bs-select');

    //Call Datatables jQuery plugin
    $('#vehicle-class-table').DataTable({
        "scrollY": "49.3vh",
        "scrollX": true,
        "scrollCollapse": true,
        "paging": false, // false to disable pagination (or any other option)
        "bInfo": false,
        "deferRender": true
    });
    $('.dataTables_length').removeClass('bs-select');

    //Call Datatables jQuery plugin
    $('#vehicle-axle-table').DataTable({
        "scrollY": "50.3vh",
        "scrollX": true,
        "scrollCollapse": true,
        "paging": false, // false to disable pagination (or any other option)
        "bInfo": false,
        "deferRender": true
    });
    $('.dataTables_length').removeClass('bs-select');

    //Call Datatables jQuery plugin
    $('#vehicle-speed-table').DataTable({
        "scrollY": "50.3vh",
        "scrollX": true,
        "scrollCollapse": true,
        "paging": false, // false to disable pagination (or any other option)
        "bInfo": false,
        "deferRender": true
    });
    $('.dataTables_length').removeClass('bs-select');

    //CCR CLASSES

    //Call Datatables jQuery plugin
    $('#ccr-tipo-table').DataTable({
        "scrollY": "50.3vh",
        "scrollX": true,
        "scrollCollapse": true,
        "paging": false, // false to disable pagination (or any other option)
        "bInfo": false,
        "deferRender": true
    });
    $('.dataTables_length').removeClass('bs-select');

    //Call Datatables jQuery plugin
    $('#ccr-classes-table').DataTable({
        "scrollY": "36.3vh",
        "scrollX": true,
        "scrollCollapse": true,
        "paging": false, // false to disable pagination (or any other option)
        "bInfo": false,
        "deferRender": true
    });
    $('.dataTables_length').removeClass('bs-select');

    //Call Datatables jQuery plugin
    $('#ccr-speed-table').DataTable({
        "scrollY": "36.3vh",
        "scrollX": true,
        "scrollCollapse": true,
        "paging": false, // false to disable pagination (or any other option)
        "bInfo": false,
        "deferRender": true
    });
    $('.dataTables_length').removeClass('bs-select');

    //Call Datatables jQuery plugin
    $('#ccr-all-classes-table').DataTable({
        "scrollY": "36.3vh",
        "scrollX": true,
        "scrollCollapse": true,
        "paging": false, // false to disable pagination (or any other option)
        "bInfo": false,
        "deferRender": true
    });
    $('.dataTables_length').removeClass('bs-select');
});

//RESET MESSAGE DISPLAY	
function hideMessage() {
setTimeout(function () {
$('#info').hide(); 		
}, 5000); 
}

//SHOW MESSAGE DISPLAY	
function showMessage() {
$('#info').show(); 		
}

function drawTable(table, scrollHeight) {
$(table).DataTable({			  	   	
"scrollY": scrollHeight,
"scrollX": true,
"scrollCollapse": true,
"paging": false, // false to disable pagination (or any other option)
"bInfo": false,
"deferRender": true,
"ordering": false
 });
  $('.dataTables_length').removeClass('bs-select');
 }

