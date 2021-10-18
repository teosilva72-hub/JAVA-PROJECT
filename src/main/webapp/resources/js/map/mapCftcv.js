const cftvEvent = data => {
	var status = data.status;
	switch (status) {
		case "begin":
			break;
		case "complete":
			break;
		case "success":
			cftvVideo(id, url);
			break;
	}
}
$(function(){
	$(".d-cftv-value").click(function() {
	    var value = $(this).val();
		var id = $(this).attr('id');
		$(`#${id}`).prop('disabled', true);
		var cod = document.getElementById("cftvId")
		cod.value = value
		getInfo()
		//var url = document.getElementById("url-img")
		setTimeout(() => {
			cftvVideo(value, url)
		}, 1000)
	})
	//remove option click btn right mouse
	$(document).contextmenu(function() {
    return false
	});
})
function getInfo(){
	$("#send-cftv-id").click();
}
function cftvVideo(id, url){
	id = document.getElementById("cftvId").value
	url = document.getElementById("url-img")
		$("#dinamic-div-cftv").append(`\
		<div class="img-cftv-div bg-dark ptz${id}">\				
				<i class="fas fa-times close-cftv ptz-close${id}"></i>\
				<img src="${url.value}" class="video-img video-img${id}"/>\
				<p class="text-cftv-barra">PTZ ${id}</p>\
		</div>`)
		$(".img-cftv-div").fadeIn(3000);
		document.querySelector(".video-img"+id).src = url.value
		close(`.ptz-close${id}`, `.ptz${id}`, id);
		dragdrop(`.img-cftv-div`);
		$(".img-cftv-div").resizable()
}
function disabledListCftv(id){
		$(`#ptz-window${id}`).prop('disabled', false);
}
function close(cam, close, id){
	setTimeout(() => {	
		$(cam).click(function() {
	  		$(close).remove();
			disabledListCftv(id)
		})
	}, 1000)
}
function dragdrop(cam){
	$(cam).draggable();
}
function cftvTop(){
	$("#cftvMoveUp" ).click();
}
function cftvBottom(){
	$("#cftvMoveDown" ).click();
}
function cftvLeft(){
	$("#cftvMoveLeft" ).click();
}
function cftvRight(){
	$("#cftvMoveRight" ).click();
}
function cftvZoomIn(){
	$("#cftvMoveIn" ).click();
}
function cftvZoomOut(){
	$("#cftvMoveOut" ).click();
}
function validatePresetCall(){
	let calls = document.getElementById("window-cftv")
	let calls1 = document.getElementById("window-cftv1")
	if(calls.value == "" && calls1.value == ""){
		calls.style.border ="Solid red 2px"
		calls1.style.border = "Solid red 2px"
		$('.msg-danger').addClass('show').fadeOut(2000)
	}else{
		$('.msg-success').addClass('show').fadeOut(2000)
		calls.style.border ="Solid green 2px"
		calls1.style.border = "Solid green 2px"
		comeBack()
	}
}
function presetCftv(){
	$(".btns-cftv-number").click(function(event){
	    var digito = $(this).html();
	    $("#window-cftv").val(function(){ return $(this).val()+digito; }).attr('maxlength','3');
		event.preventDefault()
	}).click(function(event){
	    var digito = $(this).html();
	    $("#window-cftv1").val(function(){ return $(this).val()+digito; }).attr('maxlength','3');
		event.preventDefault()
	});
	comeBack()
}
function removeNumber(){
	var texto = $("#window-cftv").val();
	var texto1 = $("#window-cftv1").val();
	$("#window-cftv").val(texto.substring(0, texto.length - 1));
	$("#window-cftv1").val(texto1.substring(0, texto1.length - 1));
}
function comeBack(){
	$('#window-cftv1').val('')
	//$('#presetSet').val('')
	$('#window-cftv').val('')
}
function patrol(){
	$('[id$=updateDetails]').click();
	preventDefault();
}
function msgError(){
	$('.msg-danger').addClass('show').fadeOut(2000)
	presetCftv()
	
}
function btnPreset(){
	var x = document.querySelector(".preset-call-row")
	var y = document.querySelector(".preset-patrol-row")
	x.style.display = "block"
	y.style.displayc = "none"	
}
function btnPatrol(){
	var x = document.querySelector(".preset-call-row")
	var y = document.querySelector(".preset-patrol-row")
	x.style.display = "none"
	y.style.displayc = "block"
}
function rightButtonCftv(type, id){
	console.log("chegamos aqui menino")
	$(`#${type}${id}`).append(`
		<div class="mouseDownCftv">
			<button type="button" class="btn btn-success">Configuração</button>
			<button type="button" class="btn btn-danger">Danger</button>
			<button type="button" class="btn btn-warning">Warning</button>
		</div>
	`)
}