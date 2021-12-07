
$(function () {
    //Call Datatables jQuery plugin
     $('#speed-records-table').DataTable({
        "scrollY": window.innerHeight - 220,
        "scrollX": true,
        "scrollCollapse": true,
        "paging": false, // false to disable pagination (or any other option)
        "bInfo": false,
        "deferRender": true        
   });

    $('.dataTables_length').removeClass('bs-select');

	drawTable('#generic-report-table')
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
"scrollY": window.innerHeight - 220,
"scrollX": true,
"scrollCollapse": true,
"paging": false, // false to disable pagination (or any other option)
"bInfo": false,
"deferRender": true,
"ordering": false
 });

  $('.dataTables_length').removeClass('bs-select');

 }

