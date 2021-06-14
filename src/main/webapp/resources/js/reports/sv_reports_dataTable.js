//Call Datatables jQuery plugin
$(document).ready(function () {
$('#sv-year-table').DataTable({			  	   	
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
$('#sv-month-table').DataTable({			  	   	
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
$('#sv-period-table').DataTable({			  	   	
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
$('#info').hide(); 		
}, 5000); 
}

//SHOW MESSAGE DISPLAY	
function showMessage() {
$('#info').show(); 		
}

function drawTable(table) {
$(table).DataTable({			  	   	
"scrollY": "50.3vh",
"scrollCollapse": true,
"paging": false, // false to disable pagination (or any other option)
"bInfo" : false,
"deferRender": true,
"ordering": false
});
$('.dataTables_length').removeClass('bs-select');
}
