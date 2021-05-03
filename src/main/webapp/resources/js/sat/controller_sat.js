var h = window.innerHeight |
 document.documentElement.clientHeight
 //document.body.clientHeight;

var z = h / 175;

var content = Math.ceil(z + z%2); /////quantidade de equipamento por página

function page(page){
	var x = $("#page").children().hide()
	
		console.log(content)
	for(var i = content * (page - 1); i < content * page; i++){
		x.eq(i).show()
	}
}
$(function(){
	page(1);
	
	let pag = Math.ceil($("#page").children().length / content)
	for(var i = 1; i <= pag; i++){
		$("#btnPage").append(`<button type="button" onclick="page(${i})" class="btn btn-dark">${i}</button>`)
	}
})

/*function btnEnable(){
	$('[id$=btn-enable-user]').click();
	eventValidator();
}//atualizar tela*/