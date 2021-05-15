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
function btnUpdateView(e){
	$('[id$=updateView]').click();
	e.preventDefault();
}