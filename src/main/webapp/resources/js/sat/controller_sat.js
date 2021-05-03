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
	for(var i = 1; i <= 10; i++){
		
		for(var a = 0; a <= 23; a++){
			for(var b = 0; b <= 9; b++){
				for(var c = 0; c <= 45; c += 15){
					var d = Math.random()
					if(d < 0.9){
						$(`#sat${i} [col=${a < 10? '0' + a: a}] [row=${b}] [id=${c}]`).addClass("online")
					}else if(d < 0.98){
						$(`#sat${i} [col=${a < 10? '0' + a: a}] [row=${b}] [id=${c}]`).addClass("warning")
					}
										
				}
					
			}
		}
	}
})
//$("#sat2 [col=13] [row=5] [id=30]").addClass("online") //add info sat
/*function btnEnable(){
	$('[id$=btn-enable-user]').click();
	eventValidator();
}//atualizar tela*/