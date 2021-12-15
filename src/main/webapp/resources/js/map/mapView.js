var widthMax = 1000
var heightMax = 1000
var scale = 1

////////////////////////////////////////////////////////////////
const init = () => {
	$('#equipAll').load('/map/mapEquip.xhtml', () => {
		resizeEquipScale($('[scroll-zoom]'))
		resizeEquip($('[scroll-zoom]'))

		$('.equip-box, .equip-info, .equip-box-sat, .plaque').each(function () {
			let equip = $(this)

			posEquip(equip)

			if (!equip.attr('class').includes('plaque'))
				equip.dblclick(function () {
					posReset();

					id = equip.attr('id').match(/\d+/g)[0];
					type = equip.attr('id').match(/[a-zA-Z]+/g)[0];
					toDrag = `#${equip.attr('id')}`
					$('#OPmodal').modal('toggle');
					//add btn setting cftv and icon
					if(type =="cftv"){
						var cod = document.getElementById("cftvId")
						cod.value = id
						  
					} 
					////////////////////////////////////////
				});

			$(window).resize(function () {
				posEquip(equip)
			})
		})

		setInfoEquip();
		setEquipToolTip();
		showGenericName();
		
		if(window.initPMV)
		  initPMV();

		if(window.initSOS)
		  initSOS();

		if(window.initMonitor)		
			initMonitor();
	  
		if(window.initSPEED)		
			initSPEED();
			
		if (window.initGPS)
			initGPS();
	})
}

const setInfoEquip = () => {
	$('[data-toggle="popover"]').popover({
		html: true,
		trigger: 'hover',
		content: function () {
			var content = $(this).attr("data-popover-content");
			return $(content).children(".popover-body").html();
		},
		title: function () {
			var title = $(this).attr("data-popover-content");
			return $(title).children(".popover-header").html();
		}
	});
}

const setEquipToolTip = () => {
	$('[data-toggle="tooltip"]').tooltip();

}

$(function() {

	$('.plaque').each(function () {
		let plaque = $(this)

		plaque.attr('posX', plaque.css('left').replace("px", ""))
		plaque.attr('posY', plaque.css('top').replace("px", ""))
	})
	init();
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
})

function ScrollZoom(container) {
	let max_scale = Number(container.attr('max-scale')) || 4
	let factor = Number(container.attr('scroll-zoom')) || .5
	let target = container.children().first()
	let pos = zoom_point = { x: 0, y: 0 }
	let scale_diff = scale_prev = 1

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
			scale_diff = scale_prev = scale

			// apply zoom
			scale += delta * factor * scale
			scale = Math.max(1, Math.min(max_scale, scale))
			scale_diff = scale / scale_diff
			target.attr('scale', scale)

			console.log(scale);

			update()
			resizeEquip(container)
		}
	}

	function update() {
		target.css('transform', `scale(${scale})`)

		container
			.scrollTop(pos.y * container[0].scrollHeight - zoom_point.y / scale_prev)
			.scrollLeft(pos.x * container[0].scrollWidth - zoom_point.x / scale_prev)

		showGenericName();

		container.find('.equip-box, .equip-info, .equip-box-sat, .plaque').each(function () {
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
		let max = 0;
		let equips = container.find('.equip-box, .equip-info, .equip-box-sat');
		let plaque = $('.plaque');
		let allEquip = $('#equipAll .equip-box, #equipAll .equip-info, #equipAll .equip-box-sat');
		let barSize = Number($('#bar-size').val()) || 1
		let scaleA;

		equips.each(function () {
			let equip = $(this)
			let width = Number(equip.attr('item-width'))
			scaleA = (width / equip.width()) * (barSize);

			equip.css('transform', `translate(-50%, -70%) scale(${scaleA * scale})`).attr('scale', scaleA).attr('barSize', barSize)
		})

		allEquip.each(function () {
			let equip = $(this)

			max += Number(equip.attr('item-width')) * Number(equip.attr('barSize')) || 1;
		})

		scaleA = ((max / allEquip.length) / (plaque.width() || 75));
		plaque.css('transform', `translateX(-50%) scale(${scaleA * scale})`).attr('scale', scaleA)
	}

	//RESIZE EQUIPMENT
	function resizeEquip(container) {
		let equips = container.find('.equip-box, .equip-info, .equip-box-sat');
		let plaque = $('.plaque');
		let scaleA;

		equips.each(function () {
			let equip = $(this)
			scaleA = equip.attr('scale')

			equip.css('transform', `translate(-50%, -70%) scale(${scaleA * scale}`)
		})

		scaleA = plaque.attr('scale');
		plaque.css('transform', `translateX(-50%) scale(${scaleA * scale})`)
	}
	
	//RESIZE EQUIPMENT END
	
	// EQUIPMENT POSITION
	function posEquip(equip) {
		let zoomTarget = equip.closest('[scroll-zoom]').children().first()
		let zoomTargetImg = zoomTarget.find('img')
		let scale = Number(zoomTarget.attr('scale'))
		let coord = {
			longitude: equip.attr("longitude"),
			latitude: equip.attr("latitude")
		}
		let pos = coordToPixel(coord.longitude, coord.latitude)
		pos.x += Number(equip.attr('posX'))
		pos.y += Number(equip.attr('posY'))
	
		//Pos X and Pos Y
		equip.css({
			left: pos.x * scale,
			top: pos.y * scale
		});
	
		if (!equip.hasClass("plaque"))
			updateLine(equip);
	
		if (equip.attr("class").includes('equip-box-sat')) {
			let sat_status = equip.attr('status')
			let sat_name = equip.attr('id')
			let interval = Number(equip.attr('status-period'))
			let fluxo1 = equip.find('[id^=img1FluxoTab]')
			let fluxo2 = fluxo1.next()
			let spd1 = Number(equip.find('#spd1').text())
			let spd2 = Number(equip.find('#spd2').text())

		//Green Color > indica que o equipamento está conectado
		if (interval == 15 || interval == 30) {
			equip.find("[id^=satName]").css({
				"background-color": '#00FF0D',
				color: 'black'
			});

			$(`#status${sat_name}`).css({"color": '#00FF0D'});	

		}

		//SeaGreen Color > indica que o equipamento está com perca de pacotes
		else if (interval == 3) {
			equip.find("[id^=satName]").css({
				"background-color": '#FFFF00',
				color: 'black'
			});

			$(`#status${sat_name}`).css({"color": '#FFFF00'});	
		}
		//SeaGreen Color > indica que o equipamento está com perca de pacotes
		else if (interval == 6) {
			equip.find("[id^=satName]").css({
				"background-color": '#FF7F00',
				color: 'black'
			});

			$(`#status${sat_name}`).css({"color": '#FF7F00'});		
			
		}
		//Red Color > indica que o equipamento está sem comunicação
		else {
			equip.find("[id^=satName]").css({
				"background-color": '#FF0000',
				color: 'white'
			});

			$(`#status${sat_name}`).css({"color": '#FF0000'});	
			
		}
		}
	}
		// EQUIPMENT POSITION END

		const clearLines = () => {
			let draw = $('.drawLines');
			let checkedLines = $("#visiblelines");
			draw.find(".equipLine ").remove();
			if (checkedLines.prop("checked"))
				$('.equip-box, .equip-info, .equip-box-sat').each(function () {
					let equip = $(this)
					updateLine(equip);
				});
		}
		
		const updateLine = equip => {
			let checkedLines = $("#visiblelines");
			if (!checkedLines.prop("checked"))
				return
				
			let draw = $('.drawLines');
			let id = equip.attr("id");
			let l = draw.find(`.equipLine.${id}`);
			let container = draw.closest("[scroll-zoom]");
		
			draw.css({
				"width": "100%",
				"height": "100%"
			})
		
			let equipScale = equip.attr("scale");
			let dimension = {
				"width": equip.width() * equipScale,
				"height": equip.height() * equipScale,
			}
			let pos = {
				longitude: Number(equip.attr("longitude")),
				latitude: Number(equip.attr("latitude")),
				x: Number(equip.css("left").replace("px", "")) / scale,
				y: Number(equip.css("top").replace("px", "")) / scale
			};
		
			let point = coordToPixel(pos.longitude, pos.latitude);
		
			let difference = {
				x: point.x - pos.x,
				y: point.y - pos.y,
			}
			difference.absX = Math.abs(difference.x);
			difference.absY = Math.abs(difference.y);
		
			if (difference.absX > difference.absY) {
				let x = difference.x / difference.absX;
				pos.x += dimension.width * .65 * x;
			} else {
				let y = difference.y / difference.absY;
				pos.y += dimension.height * .85 * y;
			}
			pos.x += dimension.width / (dimension.width - 1);
			// pos.y += dimension.height / (dimension.height - .1);
		
			if (!l.length) {
				let line = $(`<svg class="equipLine ${id}"><polyline style="stroke:black;stroke-width:.2"></polyline></svg>`);
				l = line;
		
				draw.append(l);
			}
		
			l.find("polyline").attr("points", `${point.x},${point.y} ${pos.x},${pos.y}`);
		}
		
	
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
	
		const element = $('section.overflow')
		zoomIn(element);
		setTimeout(() => {
			zoomOut(element);
			for (let idx = 0; idx < 1; idx++) {
			zoomIn(element)
			element
			.scrollLeft(posX * element[0].scrollWidth)
			.scrollTop(posY * element[0].scrollHeight)
		}
		}, 1) 

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
	