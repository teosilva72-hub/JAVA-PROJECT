function msgSuccess(){
	var d = new Date();
	var second = d.getSeconds()
	var a = document.getElementById("success")
	var b = document.getElementById("error")
	var c = document.getElementById("attention")
	if(second <= 19){
		a.style.display = "block";
		setTimeout(function(){
			$('#success').fadeOut('fast');
		}, 2000);
	}else{
		if(second > 19 && second <= 29){
			b.style.display = "block";
			setTimeout(function(){
				$('#error').fadeOut('fast');
			}, 2000);
		}else if(second > 29 && second <= 59){
			c.style.display = "block";
			setTimeout(function(){
				$('#attention').fadeOut('fast');
			}, 2000);
		}
	}

}//msg de sucesso modelo para atenção e erro
function btnPopUp(e){
	$('[id$=btnPopUp]').click();
	e.preventDefault();
}///usuário online
$(document).ready(function teste() {
		   		var table = $('#occurrence-table').DataTable({
					language: {
						"search": "",
				        searchPlaceholder: "Buscar"
				    },		
		    		"select": true,
				    "autoWidth": true,			  	   	
				    "scrollY": "40vh",
					"paging": false,
					"bInfo" : false
							
		    	});
				$('.dataTables_length').removeClass('bs-select');			   
			});