/*$(window).on('load', function () {
    $('#preloader .inner').fadeOut();
    $('#preloader').delay(100).fadeOut('slow'); 
    $('body').delay(100).css({'overflow': 'visible'});
})*/
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
function btnUpdateView(){
	$('[id$=updateView]').click();
	preventDefault();
}
function reloadPage(){
        location.reload(true);
    }

function color(){
	$('.troca').addClass('active');
	$('.titleColumn2-2').addClass('active');
	$('.btn-paleta').addClass('active');
	$('.paleta').addClass('active');
}
function colorReplacement(){
	$('.troca').removeClass('active');
	$('.titleColumn2-2').removeClass('active');
	$('.btn-paleta').removeClass('active');
	$('.paleta').removeClass('active');
}
function reload(){
	window.location.reload(false);
}