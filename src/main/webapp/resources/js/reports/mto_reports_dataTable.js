//Call Datatables jQuery plugin
$(document).ready(function () {
$('#weather-year-table').DataTable({			  	   	
"scrollY": "50.3vh",
"ordering": false,
"scrollCollapse": true,
"paging": false, // false to disable pagination (or any other option)
"bInfo" : false,
"deferRender": true
});
$('.dataTables_length').removeClass('bs-select');
}); 

//Call Datatables jQuery plugin
$(document).ready(function () {
$('#weather-month-table').DataTable({			  	   	
"scrollY": "50.3vh",
"scrollCollapse": true,
"paging": false, // false to disable pagination (or any other option)
"bInfo" : false,
"deferRender": true
});
$('.dataTables_length').removeClass('bs-select');
}); 


//Call Datatables jQuery plugin
$(document).ready(function () {
$('#weather-period-table').DataTable({			  	   	
"scrollY": "50.3vh",
"scrollCollapse": true,
"paging": false, // false to disable pagination (or any other option)
"bInfo" : false,
"deferRender": true
});
$('.dataTables_length').removeClass('bs-select');
}); 

//RESET MESSAGE DISPLAY	
function hideMessage() {
setTimeout(function () {
$('#message-div').hide(); 		
}, 5000); 
}
