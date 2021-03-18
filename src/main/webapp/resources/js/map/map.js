var widthMax = 1000
var heightMax = 1000

$(function() {
		setTimeout(function () {
			$('#message-show').hide(); 		
		}, 5000); 
	});

$(function () {
	//Scroll Zoom Map Full
	$('[scroll-zoom]').each(function () {
		let map = $(this)
		ScrollZoom(map)
		mapMove(map)
	})
	barResize()
	//Scroll Zoom Map Full END

	$(".overflow").css("height", $(this).height() - 125)
	$(window).resize(function () {
		$(".overflow").css("height", $(this).height() - 125)
	})
																
	//FULLSCREEN
	// $('.zoomFull').addClass('img-enlargeable').click(function () {
	//	var src = this.getAttribute('target');
	//	var modal;
	//	let pos = { top: 0, left: 0, x: 0, y: 0 }
	//	let img = $('<img />').attr('src', src).addClass('box-img')
	//	let frame = $('<div></div>').css({
	//		width: '90%', height: '90%',
	//		transform: 'translate(-50%, -50%)',
	//		position: 'relative',
	//		top: '50%', left: '50%',
	//		overflow: 'hidden',
	//	}).append(img)

	//	function removeModal() { modal.remove(); $('body').off('keyup.modal-close'); }

	//	modal = $('<div></div>').css({
	//		background: 'RGBA(0,0,0,1.0)',
	//		width: '100%', height: '100%',
	//		position: 'fixed',
	//		zIndex: '10000',
	//		top: '0', left: '0',
	//	}).append(frame).click(function () { removeModal(); }).appendTo('body')
		//Zoom
	//	img.click(function (e) { e.stopPropagation() })
	//		.on("dragstart", function () { return false })
	//		.on("mousedown", function (down) {
	//			if (down.target.hasAttribute("zoom")) {
	//				pos = {
	//					left: frame.scrollLeft(),
	//					top: frame.scrollTop(),
	//					x: down.clientX,
	//					y: down.clientY,
	//				};
	//				img.on("mousemove", function (move) {
	//					img.css({ "cursor": "grabbing" })
	//						.off("mouseup").on("mouseup", function () {
	//							img.css({ "cursor": "zoom-out" }).off("mousemove")
	//						})
	//					const dx = move.clientX - pos.x;
	//					const dy = move.clientY - pos.y;
	//					frame
	//						.scrollTop(pos.top - dy)
	//						.scrollLeft(pos.left - dx)
	//				}).off("mouseup").on("mouseup", function () {
	//					img.css({ "cursor": "zoom-in" }).off("mousemove")
	//					this.toggleAttribute("zoom")
	//				})
	//			} else {
	//				img.off("mouseup").on("mouseup", function (e) {
	//					let click = {
	//						top: (e.pageY - img.offset().top) / img.height(),
	//						left: (e.pageX - img.offset().left) / img.width(),
	//					}
	//					this.toggleAttribute("zoom")
	//					img.css({ "cursor": "zoom-out" })
	//					frame
	//						.scrollLeft(
	//							click.left * e.target.scrollWidth - frame.width() / 2
	//						).scrollTop(
	//							click.top * e.target.scrollHeight - frame.height() / 2

	//						)

	//				})
	//			}
	//		});

		// handling ESC
	//	$('body').on('keyup.modal-close', function (e) {
	//		if (e.key === 'Escape') { removeModal(); }
	//	});
//	});
	// FULLSCREEN END

	// POS EQUIP

	$('.equip-box, .equip-info, .equip-box-sat').each(function () {
		let equip = $(this)

		posEquip(equip)
		resizeEquip(equip.closest('[scroll-zoom]'))

		equip.dblclick(function () {
			posReset();

			id = equip.attr('id').match(/\d+/g)[0];
			type = equip.attr('id').match(/[a-zA-Z]+/g)[0];
			toDrag = `#${equip.attr('id')}`

			$('#OPmodal').modal('toggle');
		});

		$(window).resize(function () {
			posEquip(equip)
		})

		$('zoomIn').click(function () { zoomInFactor(toDrag, 0.01) })
		$('zoomOut').click(function () { zoomInFactor(toDrag, 0.01) })
	})
	
	//Equipments change sizes END

	$('#coefSize').change(function () {
		resizeEquip($('[scroll-zoom]'))
	})

})

function posReset() {
	$(toDrag).off('mousedown');
}

function ScrollZoom(container) {
	let max_scale = Number(container.attr('max-scale')) || 4
	let factor = Number(container.attr('scroll-zoom')) || .5
	let target = container.children().first()
	let pos = zoom_point = { x: 0, y: 0 }
	let scale = scale_diff = 1

	target.css('transform-origin', '0 0')
	container.on("mousewheel DOMMouseScroll", scrolled)

	function scrolled(e) {
		let offset = target.offset()

		zoom_point.x = e.pageX - offset.left
		zoom_point.y = e.pageY - offset.top

		pos = {
			x: zoom_point.x / (target.width() * scale),
			y: zoom_point.y / (target.height() * scale)
		}

		e.preventDefault();
		let delta = e.delta || e.originalEvent.wheelDelta;
		if (delta === undefined) {
			//we are on firefox
			delta = e.originalEvent.detail;
		}
		delta = Math.max(-1, Math.min(1, delta)) // cap the delta to [-1,1] for cross browser consistency

		if (scale != max_scale || delta == -1) {
			scale_diff = scale

			// apply zoom
			scale += delta * factor * scale
			scale = Math.max(1, Math.min(max_scale, scale))
			scale_diff = scale / scale_diff
			target.attr('scale', scale)

			update()
			resizeEquip(container)
		}
	}

	function update() {
		target.css('transform', `scale(${scale})`)

		container
			.scrollTop(pos.y * container[0].scrollHeight - container.height() / 2)
			.scrollLeft(pos.x * container[0].scrollWidth - container.width() / 2)

		container.find('.equip-box, .equip-info, .equip-box-sat').each(function () {
			let equip = $(this)

			equip.css(
				{
					top: Number(equip.css('top').replace('px', '')) * scale_diff,
					left: Number(equip.css('left').replace('px', '')) * scale_diff
				}
			)
		})
	}
}

// SIZE BAR EQUIPMENTS

 function barResize(){

	let size = $('.bar')
	let input = size.find('input')
	let min = Number(input.attr('min'))
	let max = Number(input.attr('max'))
	let value = Number(input.val())
	let width = size.width()
	
	size.on('touchstart mousedown', function(e) {
	    let clientX = (e.touches || [e])[0].clientX
	    let offSetLeft = size.offset().left
	    let pos = clientX - offSetLeft
		width = size.width()
		input = size.find('input')
	    let por = Math.min(1, Math.max(0, pos / width))
	    let newVal = por * (max - min) + min
	    
	    size.children().first().css("left", `${por * 100}%`).find('input').attr('value', newVal).trigger("change")
	
	    $(document).on('touchmove mousemove', function(e) {
	        clientX = (e.touches || [e])[0].clientX
			offSetLeft = size.offset().left
	        pos = clientX - offSetLeft
			input = size.find('input')
	        por = Math.min(1, Math.max(0, pos / width))
	        newVal = por * (max - min) + min
	 		input.attr('value', newVal).trigger('change').parent().css('left', `${por * 100}%`)
	    }).on("mouseup touchend", () => {
	        $(document).off("touchmove mousemove")
	    })
	}).children().first().css("left", `${(value-min)/ (max-min) * 100}%`)
	
	input.change(function () {
		resizeEquip($(`${input.attr("from")}`).parent())
		console.log($(`${input.attr("from")}`).parent())
	})
}

// SIZE BAR EQUIPMENTS END

// SCALE EQUIPS

	//RESIZE EQUIPMENT
	function resizeEquip(container) {
		container.find('.equip-box, .equip-info, .equip-box-sat').each(function () {
			let equip = $(this)
			let scale = Number(equip.attr('item-width')) / equip.width()
			
			equip.css('transform', `scale(${scale * (Number($('#bar-size').val()) || 1)})`)
		})
	}
	
	//RESIZE EQUIPMENT END
	
	// EQUIPMENT POSITION
	function posEquip(equip) {
		let zoomTarget = equip.closest('[scroll-zoom]').children().first()
		let zoomTargetImg = zoomTarget.find('img')
		let scale = Number(zoomTarget.attr('scale'))
		let pos = {
			x: Number(equip.attr('posX')),
			y: Number(equip.attr('posY'))
		}
	
		pos.centX = pos.x / widthMax * scale
		pos.centY = pos.y / heightMax * scale
	
		//Pos X and Pos Y
		equip.css({
			left: pos.centX * zoomTarget.width() + zoomTargetImg.offset().left - zoomTarget.offset().left,
			top: pos.centY * zoomTargetImg.height() + zoomTargetImg.offset().top - zoomTarget.offset().top
		});

		if (equip.attr("class").includes('equip-box-sat')) {
			let sat_status = equip.attr('status')
			let interval =  Number(equip.attr('status-period'))
			let fluxo1 = equip.find('[id^=img1FluxoTab]')
			let fluxo = {
				height: (width * 0.10) + "px",
				border: "1px solid transparent",
			}
			fluxo1
				.css(fluxo)
				.css('margin-top', 7 + "px")
				.next()
				.css(fluxo)
	
			//Green Color > indica que o equipamento está conectado
			if (sat_status > 0 && interval == 30) {
				equip.find("[id^=satName]").css({
					"background-color": '#00FF0D',
					color: 'black'
				});
				document.getElementById(`status${equip.attr('id')}`).style.color = '#00FF0D';
	
			}
			//SeaGreen Color > indica que o equipamento está com perca de pacotes
			else if (sat_status > 0 && interval == 45) {
				equip.find("[id^=satName]").css({
					"background-color": '#00BFFF',
					color: 'black'
				});
				document.getElementById(`status${equip.attr('id')}`).style.color = '#00BFFF';
			}
			//SeaGreen Color > indica que o equipamento está com perca de pacotes
			else if (sat_status > 0 && interval == 8) {
				equip.find("[id^=satName]").css({
					"background-color": '#FFFF00',
					color: 'black'
				});
				document.getElementById(`status${equip.attr('id')}`).style.color = '#FFFF00';
			}
			//Red Color > indica que o equipamento está sem comunicação
			else {
				equip.find("[id^=satName]").css({
					"background-color": '#FF0000',
					color: 'white'
				});
				document.getElementById(`status${equip.attr('id')}`).style.color = '#FF0000';
			}
		}

		if (equip.attr("class").includes('equip-box')) {

		}
	}
	// EQUIPMENT POSITION END
	
// SCALE EQUIPS END

//MAP MOVE CURSOR
function mapMove(ele) {
	let pos = { top: 0, left: 0, x: 0, y: 0 };

	const mouseDownHandler = function (e) {
		e.preventDefault()

		ele.css({
			cursor: 'grabbing',
			userSelect: 'none'
		})

		pos = {
			left: ele.scrollLeft(),
			top: ele.scrollTop(),
			//Pega a posição atual do mouse
			x: e.clientX,
			y: e.clientY,
		};

		$(document)
			.mousemove(mouseMoveHandler)
			.mouseup(mouseUpHandler);

	};

	const mouseMoveHandler = function (e) {
		// O quao longe o mouse esta sendo movido
		const dx = e.clientX - pos.x;
		const dy = e.clientY - pos.y;

		ele
			.scrollTop(pos.top - dy)
			.scrollLeft(pos.left - dx)
	};

	const mouseUpHandler = function () {
		ele.css({
			cursor: 'grab',
			userSelect: ''
		})

		$(document)
			.off('mousemove')
			.off('mouseup')
	};

	ele
		.css('cursor', 'grab')
		.on('dragstart', function () { return false })
		.mousedown(mouseDownHandler)
}

//TODO: ZOOM VARS // compartilhar escala
var zoom = 1;

var zoomEquip = 1;

var zoomStep = 1;
var zoomMin = 1;
var zoomMax = 7;
var left = 1;
var top = 1;
var leftStep = 3;
var topStep = 3;
var ZoomEquipStep = 0.20;

function zoomIn(id) {

	if (zoom < zoomMax) {
		zoom += zoomStep;
		left += leftStep;
		document.getElementById(id).style.transform = "scale(" + zoom + ")";
	}
	//alert(zoom);
};

function zoomOut(id) {

	if (zoom > zoomMin) {
		zoom -= zoomStep;
		left += leftStep;

		document.getElementById(id).style.transform = "scale(" + zoom + ")";
		//document.getElementById(id).style.marginRight =  left + "%";
	}
}

function zoomInFactor(id, factor) {
	if (zoom < zoomMax) {
		if (zoomEquip < 1.2500000000000002) {
			zoomEquip += (factor);
			left += leftStep;
			top += topStep;
			document.getElementById(id).style.transform = "scale(" + zoomEquip + ")";
			//document.getElementById(id).style.marginTop = top +"%";
		}
		//alert('nothing');
	}
};

function zoomOutFactor(id, factor) {
	if (zoom > zoomMin) {
		zoomEquip -= (factor);
		left += leftStep;

		document.getElementById(id).style.transform = "scale(" + zoomEquip + ")";
		//document.getElementById(id).style.marginRight =  left + "%";
	}
}

// Drop Element	

function dragEquip() {

	var elmnt = $(toDrag);

	var pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;
	// Otherwise, move the DIV from anywhere inside the DIV:
	elmnt.on("mousedown", dragMouseDown);

	function dragMouseDown(e) {
		let elmnt = $(this)
		e.preventDefault();
		e.stopPropagation()
		// Get the mouse cursor position at startup:
		pos3 = e.clientX;
		pos4 = e.clientY;

		$(document)
			.on("mouseup", closeDragElement)

			.on("mousemove", function (e) {
				e.preventDefault();

				// Calculate the new cursor position:
				pos1 = pos3 - e.clientX;
				pos2 = pos4 - e.clientY;
				pos3 = e.clientX;
				pos4 = e.clientY;

				let targetZoom = elmnt.closest('[scroll-zoom]').children().first()
				let targetZoomImg = targetZoom.find('img')
				let scale = (targetZoom.attr('scale') || 1)

				let pos = {
					left: Math.round(elmnt.css("left").replace("px", "") - pos1),
					top: Math.round(elmnt.css("top").replace("px", "") - pos2)
				}
				pos.leftOrigin = Math.round(((pos.left - targetZoomImg.offset().left + targetZoom.offset().left) / targetZoomImg.width() * widthMax) / scale)
				pos.topOrigin = Math.round(((pos.top - targetZoomImg.offset().top + targetZoom.offset().top) / targetZoomImg.height() * heightMax) / scale)

				// Set the element's new position:
				elmnt.css({
					top: pos.top,
					left: pos.left
				})

				// Save element position on input 
				document.getElementById("real:equipIdPos").value = id;
				document.getElementById("real:equipTablePos").value = type;
				document.getElementById("real:equipPosX").value = pos.leftOrigin;
				document.getElementById("real:equipPosY").value = pos.topOrigin;
				$('#posX').text('x: ' + parseInt(pos.left / targetZoom.width() * widthMax / scale));
				$('#posY').text('y: ' + parseInt(pos.top / targetZoom.height() * heightMax / scale));
			})
	}

	//closeDragElement();
	function closeDragElement() {
	// Stop moving when mouse button is released:
	$(document)
		.off("mouseup")
		.off("mousemove")
}

}


//Drag/Drop Element END


// Map div Iframe Reload
$(document).ready(function(){
	$('#fulldiv1').click(function(){
		$('#frame1').attr('src',$('#frame1').attr('src'));
	return false;
	});
});

$(document).ready(function(){
	$('#fulldiv2').click(function(){
		$('#frame2').attr('src',$('#frame2').attr('src'));
	return false;
	});
});

$(document).ready(function(){
	$('#fulldiv3').click(function(){
		$('#frame3').attr('src',$('#frame3').attr('src'));
	return false;
	});
});



// Map div Iframe Reload END

//Delete Modal Name
 function DelName(){
}
//Delete Modal Name End


//prevent modal form submit
//use ajax to send data
$('#register-equip-form').submit(function(e) {
	e.preventDefault();
});

//Reload on Cancel Position
function reloadAfterCancelPos(){
	setTimeout(function() {
	window.location.reload(1);
  }, 2000); // 2 sec						
}

//Use validation on click button submit   
//Create button
function checkValidation(){	    	
   $("#register-equip-form").valid();	        
}

