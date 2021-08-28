$(document).ready(function () {
    //Call Datatables jQuery plugin
     $('#sos-battery-table').DataTable({
        "scrollY": "50.3vh",
        "scrollX": true,
        "scrollCollapse": true,
        "paging": false, // false to disable pagination (or any other option)
        "bInfo": false,
        "deferRender": true        
   });
   
    //Call Datatables jQuery plugin
     $('#sos-call-table').DataTable({
        "scrollY": "50.3vh",
        "scrollX": true,
        "scrollCollapse": true,
        "paging": false, // false to disable pagination (or any other option)
        "bInfo": false,
        "deferRender": true        
   });
     
    //Call Datatables jQuery plugin
     $('#sos-statistic-table').DataTable({
        "scrollY": "50.3vh",
        "scrollX": true,
        "scrollCollapse": true,
        "paging": false, // false to disable pagination (or any other option)
        "bInfo": false,
        "deferRender": true        
   });
   
    //Call Datatables jQuery plugin
     $('#sos-status-table').DataTable({
        "autoWidth": true,
        "scrollY": "50.3vh",
        "scrollX": true,
        "scrollCollapse": true,
        "paging": false, // false to disable pagination (or any other option)
        "bInfo": false,
        "deferRender": true,
         columnDefs: [
            { width: '20%', targets: 0 }
],

fixedColumns: true       
   });
   
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
"autoWidth": true,		  	   	
"scrollY": scrollHeight,
"scrollX": true,
"scrollCollapse": true,
"paging": false, // false to disable pagination (or any other option)
"bInfo": false,
"deferRender": true,
"ordering": false,
 columnDefs: [
            { width: '20%', targets: 0 }
],

fixedColumns: true

 });
  $('.dataTables_length').removeClass('bs-select');
 }
 


