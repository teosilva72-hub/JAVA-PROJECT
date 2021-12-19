$(function () {

    //Call Datatables jQuery plugin
     $('#generic-report-table').DataTable({
        "scrollY": window.innerHeight - 230 - $('[id^=generic-report-table]').height(),
        "scrollX": true,
        "scrollCollapse": true,
        "paging": false, // false to disable pagination (or any other option)
        "bInfo": false,
        "deferRender": true,
        "ordering": false        
   });

    $('.dataTables_length').removeClass('bs-select');

	setTimeout(() => {
		if (!window.clear_table)
			$('[id$=clear_table]').click()
	}, 1)

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
"scrollY": window.innerHeight - 170 - $('thead').height() - $('.navbar.fixed-top').height() - $('footer.page-footer').height(),
"scrollX": true,
"scrollCollapse": true,
"paging": false, // false to disable pagination (or any other option)
"bInfo": false,
"deferRender": true,
"ordering": false
 });

  $('.dataTables_length').removeClass('bs-select');

 }

