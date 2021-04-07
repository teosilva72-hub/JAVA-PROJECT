var widthMax = 1000
var heightMax = 1000
var scale = 1

$(function() {
	//Scroll Zoom Map Full
	$('[scroll-zoom]').each(function () {
		let map = $(this)
		ScrollZoom(map)
		mapMove(map)
	})

	$(".overflow").css("height", $(this).height())
	$(window).resize(function () {
		$(".overflow").css("height", $(this).height())
	})

	resizeEquipScale($('[scroll-zoom]'))

	$('.equip-box, .equip-info, .equip-box-sat').each(function () {
		let equip = $(this)

		posEquip(equip)

		$(window).resize(function () {
			posEquip(equip)
		})

	})
})

function ScrollZoom(container) {
	let max_scale = Number(container.attr('max-scale')) || 4
	let factor = Number(container.attr('scroll-zoom')) || .5
	let target = container.children().first()
	let pos = zoom_point = { x: 0, y: 0 }
	let scale_diff = 1

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

		showGenericName();

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

// SIZE BAR EQUIPMENTS END

// SCALE EQUIPS

	//RESIZE EQUIPMENT
	function resizeEquipScale(container) {
		container.find('.equip-box, .equip-info, .equip-box-sat').each(function () {
			let equip = $(this)
			let scale = (Number(equip.attr('item-width')) / equip.width()) * (Number($('#bar-size').val()) || 1);
			
			equip.css('transform', `translate(-50%, -70%) scale(${scale})`).attr('scale', scale)
		})
	}
	
	//RESIZE EQUIPMENT
	function resizeEquip(container) {
		container.find('.equip-box, .equip-info, .equip-box-sat').each(function () {
			let equip = $(this)
			let scaleA = equip.attr('scale')
			
			equip.css('transform', `translate(-50%, -70%) scale(${scaleA * scale}`)
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
					//TESTE		
																
			//Green Color > indica que o equipamento está conectado
			if (sat_status > 0 && interval == 30) {
				equip.find("[id^=satName]").css({
					"background-color": '#00FF0D',
					color: 'black'
				});

	
			}
			//SeaGreen Color > indica que o equipamento está com perca de pacotes
			else if (sat_status > 0 && interval == 45) {
				equip.find("[id^=satName]").css({
					"background-color": '#00BFFF',
					color: 'black'
				});

			}
			//SeaGreen Color > indica que o equipamento está com perca de pacotes
			else if (sat_status > 0 && interval == 8) {
				equip.find("[id^=satName]").css({
					"background-color": '#FFFF00',
					color: 'black'
				});

			}
			//Red Color > indica que o equipamento está sem comunicação
			else {
				equip.find("[id^=satName]").css({
					"background-color": '#FF0000',
					color: 'white'
				});

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
			.mouseup(mouseUpHandler)
			.mouseleave(mouseUpHandler)

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

var up = $.Event("DOMMouseScroll",{delta:100}); 
var down = $.Event("DOMMouseScroll",{delta:-100});
 
function zoomIn(id) {

	$(id).trigger(up);

};

function zoomOut(id) {

	$(id).trigger(down);
};

function setPosition(posX, posY) {
	if (scale == 1) {
		const element = $('section.overflow')
	
		for (let idx = 0; idx < 2; idx++) {
			zoomIn(element)
		}
	
		element
			.scrollLeft(posX * element[0].scrollWidth)
			.scrollTop(posY * element[0].scrollHeight)
	} 
}

//Reload on Cancel Position
function reloadAfterCancelPos(){
	setTimeout(function() {
	window.location.reload(1);
  }, 2000); // 2 sec						
}

/**/
function showGenericName(){

	if(scale > 1.3)
	  $('.equip-header').css('opacity', 1);
	  
	  else  $('.equip-header').css('opacity', 0);
	  
	}
	