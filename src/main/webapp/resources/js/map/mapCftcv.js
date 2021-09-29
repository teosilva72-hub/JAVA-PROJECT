function getInfo(){
	$("#send-cftv-id").click();
}
function cftvVideo(){
	var id = document.getElementById("cftvId").value
	var url = document.getElementById("url-img")
		$("#dinamic-div-cftv").append(`\
		<div class="img-cftv-div bg-dark ptz${id}">\				
				<i class="fas fa-times close-cftv ptz-close${id}"></i>\
				<img src="${url.value}" class="video-img video-img${id}"/>\
				<p class="text-cftv-barra">PTZ ${id}</p>\
		</div>`)
		$(".img-cftv-div").fadeIn(3000);
		document.querySelector(".video-img"+id).src = url.value
		close(`.ptz-close${id}`, `.ptz${id}`);
		dragdrop(`.img-cftv-div`);
}
const cftvEvent = data => {
	var status = data.status;
	switch (status) {
		case "begin":
			break;
		case "complete":
			break;
		case "success":
			cftvVideo();
			break;
	}
}
function close(cam, close){
	setTimeout(() => {		
		$(cam).click(function() {
	  		$(close).remove();
		});
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