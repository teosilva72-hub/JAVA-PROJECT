$(function () {

 /** To draw report datatable
  *  
  * @author Wellington da Silva : 2021-12-18
  * @summary Function used to draw datatable for reports.
  * @since version 1.0
  * @version 1.1 
  * @description draw datatable for reports.
  * @copyright Tracevia S/A 2021 
  * 
  **/
  $('#data-table').DataTable({
        "scrollY": window.innerHeight - 230 - $('[id^=data-table]').height(),
        "scrollX": true,
        "scrollCollapse": true,
        "paging": false, // false to disable pagination (or any other option)
        "bInfo": false,
        "deferRender": true,
        "ordering": false        
   });

    $('.dataTables_length').removeClass('bs-select');

});

// ----------------------------------------------------------------------------------------------

/** To hide message
 *  
 * @author Wellington da Silva : 2021-12-18
 * @summary Function used to hide alert messages.
 * @since version 1.0
 * @version 1.1 
 * @description hide alert messages after x time.
 * @copyright Tracevia S/A 2021 
 * 
 **/
 function hideMessage() {
	setTimeout(function () {
		$('#info').hide(); 		
	}, 5000); 
 }

// ----------------------------------------------------------------------------------------------

/** To show message
 *  
 * @author Wellington da Silva : 2021-12-18
 * @summary Function used to show alert messages.
 * @since version 1.0
 * @version 1.1 
 * @description show alert messages.
 * @copyright Tracevia S/A 2021 
 * 
 **/
 function showMessage() {
	$('#info').show(); 		
 }

// ----------------------------------------------------------------------------------------------

/** To Draw table
 *  
 * @author Wellington da Silva : 2021-12-18
 * @summary Function used to draw tables.
 * @since version 1.0
 * @version 1.1 
 * @description function to draw tables.
 * @copyright Tracevia S/A 2021 
 * 
 **/
 function drawTable() {
	
  $('#data-table').DataTable({			  	   	
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

// ----------------------------------------------------------------------------------------------
