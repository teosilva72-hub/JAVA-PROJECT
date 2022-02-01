//Resolução
let h = window.innerHeight |
 document.documentElement.clientHeight
 //document.body.clientHeight;
let z = Math.floor((h - 160) / 180);
let content = z * 2; /////quantidade de equipamento por página

$(window).resize(
	() => {
		
		 h = window.innerHeight |
 			document.documentElement.clientHeight
		
		 z = Math.floor((h - 160) / 180);
         content = z * 2; /////quantidade de equipamento por página
         page(1)

         let pag = Math.ceil($("#page").children().filter("[id^=sat]").length / content)
	
			$("#btnPage").children().remove()
			
			if(pag != 1){	
			
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
		}		
	}
)

function page(page){
	var x = $("#page").children().filter("[id^=sat]").hide()
	for(var i = content * (page - 1); i < content * page; i++){				
				x.eq(i).show()
	}
}

$(function(){
	
	let pag = Math.ceil($("#page").children().filter("[id^=sat]").length / content)
	
	if(pag != 1){	
	
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
	}

	$('[data-toggle="popover"]').popover({
		html : true,
		trigger: 'hover',
		content: function() {
		  var content = $(this).attr("data-popover-content");
		  return $(content).children(".popover-body").html();
		},
		title: function() {
		  var title = $(this).attr("data-popover-content");
		  return $(title).children(".popover-header").html();
		}
  
	});  

  	let sat = $('.sat-def[id^=sat]')
	
	sat.each(function(i){
		let self = $(this) 
		
		quantFaixas(self, parseInt(self.attr('lanes'))); 
		
		if(self.attr('status') == 'true')		
			self.find('.btn-sat').addClass('on');
			
			else self.find('.btn-sat').removeClass('on');
	})
				
	//$('.btn-sat').addClass('on');  //status sat
	$('.btn-battery').addClass('on'); //status battery	
})


$(window).on('load', function () {
    $('#preloader .inner').fadeOut();
    $('#preloader').delay(250).fadeOut('slow'); 
    $('body').delay(250).css({'overflow': 'visible'});
})

function quantFaixas(sat, faixas) {
    faixas = Math.max(2, Math.min(8, faixas));
    let query = "";
    for (let i = 8; i > faixas; i--)
        query += `[row="${i}"]`;
    sat.find(query.replaceAll("][", "],[")).children().addClass("none");
}

//$("#sat2 [col=13] [row=5] [id=30]").addClass("online") //add info sat
/*function btnEnable(){
	$('[id$=btn-enable-user]').click();
	
}//atualizar tela*/