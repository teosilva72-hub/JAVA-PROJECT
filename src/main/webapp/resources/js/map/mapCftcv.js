/*
	@Mateus Silva
	Tracevia 28/10/2021
 */
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
// DROPDOWN CFTV TOP MAP
$(function(){
	$(".d-cftv-value").click(function() {
	    var value = $(this).val();
		var id = $(this).attr('id');
		$(`#${id}`).prop('disabled', true);
		var cod = document.getElementById("cftvId")
		cod.value = value
		getInfo()
		setTimeout(() => {
			cftvVideo(value, url)
		}, 1000)
	})
})
//SEND INFO BACK END CFTV ID
function getInfo(){
	$("#send-cftv-id").click();
}
//OPEN WINDOW BOTTOM CFTV
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
		//CLOSE WINDOW
		close(`.ptz-close${id}`, `.ptz${id}`, id);
		//MOVE WINDOW
		dragdrop(`.img-cftv-div`);
		//RESIZABLE WINDOW
		$(".img-cftv-div").resizable({
			//PARAMETERS
      		aspectRatio: 16 / 9,
			maxHeight: 250,
      		maxWidth: 350,
      		minHeight: 90,
      		minWidth: 120,
			//grid: 30
			//animate: true,
			//helper: "ui-resizable-helper"
    	})
}
//DISABLED OPTION WINDOWN TOP
function disabledListCftv(id){
		$(`#ptz-window${id}`).prop('disabled', false);
		$(`.window-mouse-right${id}`).prop('disabled', false);
}
//CLOSED WINDOW CFTV
function close(cam, close, id){
	setTimeout(() => {	
		$(cam).click(function() {
	  		$(close).remove();
			disabledListCftv(id)
		})
	}, 1000)
}
//MOVE WINDOWN CFTV
function dragdrop(cam){
	$(cam).draggable();
}
///////BUTTONS CONTROLLER CFTV//////
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
////////////VALIDATE PRESET///////////////////////////////
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
/////ADD NUMBER PRESET//////////////////////////////
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
////////REMOVE NUMBER PRESET////////////////////////
function removeNumber(){
	var texto = $("#window-cftv").val();
	var texto1 = $("#window-cftv1").val();
	$("#window-cftv").val(texto.substring(0, texto.length - 1));
	$("#window-cftv1").val(texto1.substring(0, texto1.length - 1));
}
/////CLEAR INPUT NUMBER PRESET////////////////////
function comeBack(){
	$('#window-cftv1').val('')
	//$('#presetSet').val('')
	$('#window-cftv').val('')
}
////MENSEGER ERROR PRESET///////////////////
function msgError(){
	$('.msg-danger').addClass('show').fadeOut(2000)
	presetCftv()
	
}
////IS NOT BEING USED/////////////////////////
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

//DISABLED BTN WINDOWN BOTTOM
function windowCftvRight(){
	getInfo()
	var value = document.getElementById("cftvId").value
	var url = document.getElementById("url-img").value
	$(`#ptz-window${value}`).prop('disabled', true);
	$(`.window-mouse-right${value}`).prop('disabled', true);
	setTimeout(() =>{
		cftvVideo(value, url)
	},500)
}

//OPEN MODAL SETTING CONTROLLER
function settingCftv(){
	getInfo()
	setTimeout(() =>{
		$('#cftv-modal-setting').modal('toggle')
		presetCftv()
	},500)
}
//SUM ID CFTV AUTO INCREMENT SQL
function updateTotalId(){
	$('#btnCftvSumTotal').click()
}
//HIDDEN INPUT ID ADD EQUIPAMENT CFTV
/*function cftvInput(){
	var x = document.getElementById("equips")
	var currentId = document.getElementById("equipId")
		var icon = document.querySelector(".fa-ideal")
		currentId.style.display = "block"
		icon.style.display = "block"
	x.addEventListener("change", function(){
		if(x.value == "1"){
			currentId.style.display = "none"
			icon.style.display = "none"
			$('#btnCftvSumTotal').click()
			setTimeout(()=>{
				var lastId = document.getElementById("totalIdCftv")
				currentId.value = Number(lastId.value) + 1;
			},300)
		}else{
			currentId.style.display = "block"
			icon.style.display = "block"
			$('#equipId').val('')
		}
	})
}*/