var h = window.innerHeight |
 document.documentElement.clientHeight
 //document.body.clientHeight;

var z = Math.floor((h - 160) / 168);

var content = z * 2; /////quantidade de equipamento por página
function page(page){
	var x = $("#page").children().hide()
	
	for(var i = content * (page - 1); i < content * page; i++){
		x.eq(i).show()
	}
}
$(function(){
	let pag = Math.ceil($("#page").children().length / content)
	for(var i = 1; i <= pag; i++){
		$("#btnPage").append(`
		<label onclick="page(${i})" class="btn btn-dark">${i}
			<input class="btn-check" type="radio" id="btn-page${i}">
		</label>
		`)
		
	}//paginação auto-incremento
	$("#btnPage").children().first().click(); //initial btn
	////////////////////////////////////////////////////////////////
	//window.requestAnimationFrame(g)

})
var i = 1;
/*function g(){
	for(var a = 0; a <= 23; a++){
			for(var b = 0; b <= 8; b++){
				for(var c = 0; c <= 45; c += 15){
					var d = Math.random()
					var h = a < 10? '0' + a: a
					if(d < 0.9){
						document.querySelector(`#sat${i} [col="${h}"] [row="${b}"] [id="${c}"]`).classList.add("online")
					}else if(d < 0.98){
						//$(`#sat${i} [col=${a < 10? '0' + a: a}] [row=${b}] [id=${c}]`).addClass("warning")
						document.querySelector(`#sat${i} [col="${h}"] [row="${b}"] [id="${c}"]`).classList.add("warning")

					}
										
				}
					
			}
		}
		i++
		if(i <= 49){
			window.requestAnimationFrame(g)
		}
}*/
$(window).on('load', function () {
    $('#preloader .inner').fadeOut();
    $('#preloader').delay(400).fadeOut('slow'); 
    $('body').delay(400).css({'overflow': 'visible'});
})

$(function () {
  $('[data-toggle="popover"]').popover()
})
//$("#sat2 [col=13] [row=5] [id=30]").addClass("online") //add info sat
/*function btnEnable(){
	$('[id$=btn-enable-user]').click();
	eventValidator();
}//atualizar tela*/