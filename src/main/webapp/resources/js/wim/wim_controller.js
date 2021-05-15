var data = [
    [
        "409429",
        "15/05/2021 15:02:59"
    ],
    [
        "409441",
        "15/05/2021 18:30:30"
    ],
	[
        "409440",
        "15/05/2021 18:58:10"
    ],
[
        "409429",
        "15/05/2021 15:02:59"
    ],
    [
        "409441",
        "15/05/2021 18:30:30"
    ],
	[
        "409440",
        "15/05/2021 18:58:10"
    ],
[
        "409429",
        "15/05/2021 15:02:59"
    ],
    [
        "409441",
        "15/05/2021 18:30:30"
    ],
	[
        "409440",
        "15/05/2021 18:58:10"
    ],
[
        "409429",
        "15/05/2021 15:02:59"
    ],
    [
        "409441",
        "15/05/2021 18:30:30"
    ],
	[
        "409440",
        "15/05/2021 18:58:10"
    ],
[
        "409429",
        "15/05/2021 15:02:59"
    ],
    [
        "409441",
        "15/05/2021 18:30:30"
    ],
	[
        "409440",
        "15/05/2021 18:58:10"
    ],
[
        "409429",
        "15/05/2021 15:02:59"
    ],
    [
        "409441",
        "15/05/2021 18:30:30"
    ],
	[
        "409440",
        "15/05/2021 18:58:10"
    ],


]

$(document).ready(function() {
	var table = $('#wim-table').DataTable({
		language: {
			"search": "",
			searchPlaceholder: "Buscar"
		},		
		"select": true,
		data: data,
		"autoWidth": true,			  	   	
		"scrollY": "60vh",
		"paging": false,
		"bInfo" : false
	});
	$('.dataTables_length').removeClass('bs-select');
	$('.dataTables_filter').css('margin-left', '-100px')
	$('.dataTables_wrapper ').css('margin-left', '30px')
});

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
function sizeAcima(){
	$('.size').addClass('acima');
	('.size').removeClass('normal');
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
