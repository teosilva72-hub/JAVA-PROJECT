function getInfo(){
	$("#send-cftv-id").click();
	var url = document.getElementById("url-img")
	var x = document.querySelector(".video-img").src = url.value

}
function cftvVideo(){
	
	
	var id = document.getElementById("cftvId").value
		$("#dinamic-div-cftv").append(`\
		<div class="img-cftv-div bg-dark ptz${id}">\
				<p class="text-cftv-barra">PTZ ${id}</p>\
				<i class="fas fa-times close-cftv ptz${id}"></i>\
				<img src="${url.value}" class="video-img"/>\
		</div>`)
		document.querySelector(".video-img").src = url.value
		$(".img-cftv-div").fadeIn(3000);
		close(".ptz"+id)
		dragdrop(".ptz"+id)
}

function close(cam){
	var click = $(cam)
		click.click(function() {
  			alert("Chegamos aqui")
		});
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