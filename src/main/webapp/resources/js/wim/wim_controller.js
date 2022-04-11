function getTr(){
	var table = $('#wim-table').DataTable({
		language: {
			"search": "",
			searchPlaceholder: "Buscar"
		},		
		"select": true,
		"autoWidth": true,			  	   	
		"scrollY": "60vh",
		"paging": false,
		"bInfo" : false
	});
	$('.dataTables_length').removeClass('bs-select');
	$('.dataTables_filter').css('margin-left', '-100px')
	$('.dataTables_wrapper ').css('margin-left', '30px')
 
$('#wim-table tbody').on( 'click', 'tr', function () {
   var event = $(table.row( this ).data()[0]).text();
	document.getElementById("event").value = event
	if(event != ""){
		document.getElementById("checked").value = "true"
		$('[id$=statusPdf]').prop('disabled', false);
		btnTable();
	}else{
		document.getElementById("checked").value = "false"
	}
	
} );
}
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
$(window).on('load', function () {
    $('#preloader .inner').fadeOut();
    $('#preloader').delay(100).fadeOut('slow'); 
    $('body').delay(100).css({'overflow': 'visible'});
})
//medidor de peso
function sizeAcima(){
	$('.size').addClass('acima');
	$('.size').removeClass('normal');
	$('.size').removeClass('attention');
}
function sizeAtenttion(){
	$('.size').addClass('attention');
	$('.size').removeClass('acima');
	$('.size').removeClass('normal');
}
function sizeNormal(){
	$('.size').addClass('normal');
	$('.size').removeClass('attention');
	$('.size').removeClass('acima');
}

function dataPicker(){
	var  dtInitial = $('[id$=dateInitial]')
	var dtFinal = $('[id$=dateFinal]')
    dtInitial.on('click', function() {
      $('[id$=dateInitial]').mask('9999-99-99')
    });
	dtFinal.on('click', function() {
	$('[id$=dateFinal]').mask('9999-99-99')
    });
	$('[id$=dateInitial]').datepicker({ 
		dateFormat: "yy-mm-dd",  
		changeYear: true,
		changeMonth: true,
	})
    $('[id$=dateFinal]').datepicker({
	dateFormat: "yy-mm-dd",
	changeYear: true,
	changeMonth: true
	})
}

function loading(){
	var x = document.querySelector(".c-loader")
	x.style.display = "block"
	setTimeout(function() {
   $('.c-loader').fadeOut('fast');
}, 5000);
}
function validador(){
	var dtInitial = document.getElementById("dateInitial")
	var hourInitial = document.getElementById("hourInitial")
	var minuteInitial = document.getElementById("minuteInitial")
	var dateFinal = document.getElementById("dateFinal")
	var hourFinal = document.getElementById("hourFinal")
	var minuteFinal = document.getElementById("minuteFinal")
	var ckecked = document.getElementById("cheked")
	var title = document.getElementById("titleModal")
	if(dtInitial.value == "" || hourInitial.value==""||
	minuteInitial.value == ""|| dateFinal.value == ""||
	hourFinal.value == ""|| minuteFinal.value == ""){
		$('.ll').addClass('error')
		$('.ll').removeClass('ok')
		ckecked.style.display = "block"
		ckecked.style.color = "red"
		title.style.display = "none"
		return false
	}else{
		dataPicker()
		 loading()
		$('#dateInitial').removeClass('error')
		$('#dateInitial').addClass('ok')
		$('#modalForm').modal('hide')
		ckecked.style.display = "none"
		title.style.display = "block"
		return true
	}
}
let verif = (success) => {
	if (validador())
		$(success).click()
}

function resetForm(){
	var ckecked = document.getElementById("cheked")
	var title = document.getElementById("titleModal")
	$('[id$=formId]').trigger("reset");
	$('[id$=form-table]').trigger("reset");
	$('[id$=dados1]').trigger("reset");
	$('[id$=dados2]').trigger("reset");
	$('[id$=dados3]').trigger("reset");
	$('.ll').removeClass('error')
	ckecked.style.display = "none"
	title.style.display = "block"
}
function btnTable(){
	$('[id$=btnTable]').click();
	preventDefault();
}
function btnPdf(){
	dataPicker()
	$("[id$=PdfYes]").click(function() {
  		$( "[id$=idBtn]" ).click();
	$('[id$=modalPdf]').modal('hide')
	});
}
function disabledPdf(){
	$('[id$=statusPdf]').prop('disabled', true);
	$("[id$=procurar]").click(function() {
  		$('[id$=statusPdf]').prop('disabled', true);
	});
}
$(document).ready(function () {
	disabledPdf()
	dataPicker()
	btnPdf()
});

//$('#modalForm').modal('hide')