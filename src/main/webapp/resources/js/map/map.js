let widthMax = 1000
let heightMax = 1000
let updated = '';
let scale = 1;

// *********************************************************** //

setTimeout(() => {
	setInterval(() => {

		let data = new Date();
		let n = data.getSeconds();
		let minute = data.getMinutes();

		if (minute == 1 || minute == 16 || minute == 31 || minute == 46) {
			if (n < 4)
				init();

			// location.href = location.protocol + '//' + location.host + location.pathname
		}

	}, 3000)
}, 4000)


// *********************************************************** //
///////////////functions cftv/////////////////////////


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
						$('.cftv-modal-btn').removeClass('none')
						getInfo()
					}else{
						$('.cftv-modal-btn').addClass('none')
					}
					////////////////////////////////////////
				});

			$(window).resize(function () {
				posEquip(equip)
			})
		})

		borderEquip(updated);

		setInfoEquip();
		setEquipToolTip();
		showGenericName();
		DirectionEquip();
		
		if(window.initPMV)
		  initPMV();

		if(window.initSOS)
		  initSOS();

		if(window.initMonitor)		
			initMonitor();
	  
		if(window.initSPEED)		
			initSPEED();
	})
}

const onEventMapFunction = data => {
	var status = data.status;

	switch (status) {
		case "begin":
			break;

		case "complete":
			break;

		case "success":
			init();

			break;
	}
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

$(function () {
	var area = window.innerHeight - $('footer.page-footer').outerHeight(true) - $('#content').offset().top - 25

	let url = `${location.protocol}//${location.host}/map/mapView.xhtml`

	$("#frame1").attr("src", url)
	$("#frame2").attr("src", url)
	$("#frame3").attr("src", url)

	$('#fulldiv1').on('click', function () {
		$('#frame1')[0].contentWindow.setPosition(0, 0.43)
	})

	$('#fulldiv2').on('click', function () {
		$('#frame2')[0].contentWindow.setPosition(0.41, 0.40)
	})

	$('#fulldiv3').on('click', function () {
		$('#frame3')[0].contentWindow.setPosition(1, 0.19)
	})


	$('#mapDivide').css('height', area)
		.find('.grid-img').css('height', area / 3)

	init();

	$('#divide').on('click', () => {
		$('#frame1')[0].contentWindow.setPosition(0, 0.43)
		$('#frame2')[0].contentWindow.setPosition(0.41, 0.40)
		$('#frame3')[0].contentWindow.setPosition(1, 0.19)

	})

	$('[id$="btn-edit"]').click(function btnEdit() {
		setTimeout(() => {

			var equipsSEL = document.getElementById("equips-edit");
			var selectVAL = equipsSEL.options[equipsSEL.selectedIndex].value;
			if (selectVAL == 9) {
				$('.satInputs-edit').show(); // DIV FAIXAS 1	
				$('.dmsHidden-edit').hide();			
				$('.sosInputs-edit').hide();
				$('.speedHidden-edit').hide();
				$('.ipAddressShow-edit').show();
				$("#lanes-edit").change(
					function () {
						var satLanes = document.getElementById("lanes-edit");
						console.log(satLanes)
						var selectSAT = satLanes.value;
						if (selectSAT == 2) {
							$('#direction3-edit').hide();
							$('#direction4-edit').hide();
							$('#direction5-edit').hide();
							$('#direction6-edit').hide();
							$('#direction7-edit').hide();
							$('#direction8-edit').hide();
						} else if (selectSAT == 3) {
							$('#direction3-edit').show();
							$('#direction4-edit').hide();
							$('#direction5-edit').hide();
							$('#direction6-edit').hide();
							$('#direction7-edit').hide();
							$('#direction8-edit').hide();
						} else if (selectSAT == 4) {
							$('#direction3-edit').show();
							$('#direction4-edit').show();
							$('#direction5-edit').hide();
							$('#direction6-edit').hide();
							$('#direction7-edit').hide();
							$('#direction8-edit').hide();
						} else if (selectSAT == 5) {
							$('#direction3-edit').show();
							$('#direction4-edit').show();
							$('#direction5-edit').show();
							$('#direction6-edit').hide();
							$('#direction7-edit').hide();
							$('#direction8').hide();
						} else if (selectSAT == 6) {
							$('#direction3-edit').show();
							$('#direction4-edit').show();
							$('#direction5-edit').show();
							$('#direction6-edit').show();
							$('#direction7-edit').hide();
							$('#direction8-edit').hide();
						} else if (selectSAT == 7) {
							$('#direction3-edit').show();
							$('#direction4-edit').show();
							$('#direction5-edit').show();
							$('#direction6-edit').show();
							$('#direction7-edit').show();
							$('#direction8-edit').hide();
						} else if (selectSAT == 8) {
							$('#direction3-edit').show();
							$('#direction4-edit').show();
							$('#direction5-edit').show();
							$('#direction6-edit').show();
							$('#direction7-edit').show();
							$('#direction8-edit').show();
						}
					});
			} else if (selectVAL == 8) {

				$('.dmsHidden-edit').show(); // DIV DMS TYPE				
				$('.satInputs-edit').hide();
				$('.sosInputs-edit').hide();
				$('.speedHidden-edit').hide();
				$('.ipAddressShow-edit').show();
			
			}else if (selectVAL == 10) {
				
				$('.sosInputs-edit').show();			
				$('.dmsHidden-edit').hide();
				$('.satInputs-edit').hide();
				$('.speedHidden-edit').hide();
                $('.ipAddressShow-edit').show();

			} else if (selectVAL == 11) {
				
				$('.sosInputs-edit').hide();			
				$('.dmsHidden-edit').hide();
				$('.satInputs-edit').hide();
				$('.speedHidden-edit').show();
                $('.ipAddressShow-edit').hide();

			} else {

				$('.dmsHidden-edit').hide();			
				$('.satInputs-edit').hide();
				$('.sosInputs-edit').hide();
				$('.speedHidden-edit').hide();
                $('.ipAddressShow-edit').show();
			}

		}, 100)
	});


	//Scroll Zoom Map Full
	$('[scroll-zoom]').each(function () {
		let map = $(this)
		ScrollZoom(map)
		mapMove(map)
	})
	barResize()
	//Scroll Zoom Map Full END

	$(".overflow").css("height", $(this).height() - 125)
	$('#mapDivide').css('height', area)
		.find('.grid-img').css('height', area / 3)
	$(window).resize(function () {
		area = window.innerHeight - $('footer.page-footer').outerHeight(true) - $('#content').offset().top - 25

		$(".overflow").css("height", $(this).height() - 125)
		$('#mapDivide').css('height', area)
			.find('.grid-img').css('height', area / 3)
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



	//Equipments change sizes END

	$('#coefSize').change(function () {
		resizeEquipScale($('[scroll-zoom]'))
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

// SIZE BAR EQUIPMENTS

function barResize() {

	let size = $('.bar')
	let input = size.find('input')
	let min = Number(input.attr('min'))
	let max = Number(input.attr('max'))
	let value = Number(input.val())
	let width = size.width()

	size.on('touchstart mousedown', function (e) {
		let clientX = (e.touches || [e])[0].clientX
		let offSetLeft = size.offset().left
		let pos = clientX - offSetLeft
		width = size.width()
		input = size.find('input')
		let por = Math.min(1, Math.max(0, pos / width))
		let newVal = por * (max - min) + min

		size.children().first().css("left", `${por * 100}%`).find('input').attr('value', newVal).trigger("change")

		$(document).on('touchmove mousemove', function (e) {
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
	}).children().first().css("left", `${(value - min) / (max - min) * 100}%`)

	input.change(function () {
		resizeEquipScale($(`${input.attr("from")}`).parent())
	})
}

// SIZE BAR EQUIPMENTS END

// SCALE EQUIPS

//RESIZE EQUIPMENT
function resizeEquipScale(container) {
	let max = 0;
	let equips = container.find('.equip-box, .equip-info, .equip-box-sat');
	let plaque = $('.plaque');
	let allEquip = $('#equipAll .equip-box, #equipAll .equip-info, #equipAll .equip-box-sat');
	let toolbox = $('.square_tool');
	let barSize = Number($('#bar-size').val()) || 1
	let scaleA;
	let scaleB;

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

	scaleA = ((max / allEquip.length) / plaque.width());
	plaque.css('transform', `translateX(-50%) scale(${scaleA * scale})`).attr('scale', scaleA)

	scaleB = ((max / allEquip.length) / toolbox.width());
	toolbox.css('transform', `translateX(-50%) scale(${scaleB * scale})`).attr('scale', scaleB)
	
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
		let sat_name = equip.attr('id')
		let interval = Number(equip.attr('status-period'))
		//TESTE		

		//Green Color > indica que o equipamento está conectado
		if (sat_status > 0 && interval == 30) {
			equip.find("[id^=satName]").css({
				"background-color": '#00FF0D',
				color: 'black'
			});

			$(`#status${sat_name}`).css({ "color": '#00FF0D' });

		}
		//SeaGreen Color > indica que o equipamento está com perca de pacotes
		else if (sat_status > 0 && interval == 45) {
			equip.find("[id^=satName]").css({
				"background-color": '#00BFFF',
				color: 'black'
			});

			$(`#status${sat_name}`).css({ "color": '#00FF0D' });
		}
		//SeaGreen Color > indica que o equipamento está com perca de pacotes
		else if (sat_status > 0 && interval == 8) {
			equip.find("[id^=satName]").css({
				"background-color": '#FFFF00',
				color: 'black'
			});

			$(`#status${sat_name}`).css({ "color": '#00FF0D' });
		}
		//Red Color > indica que o equipamento está sem comunicação
		else {
			equip.find("[id^=satName]").css({
				"background-color": '#FF0000',
				color: 'white'
			});

			$(`#status${sat_name}`).css({ "color": '#FF0000' });
		}
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

let up = $.Event("DOMMouseScroll", { delta: 100 });
let down = $.Event("DOMMouseScroll", { delta: -100 });

function zoomIn(id) {

	id.trigger(up);

};

function zoomOut(id) {

	id.trigger(down);
};

function setPosition(posX, posY) {
	if (scale == 1) {
		const element = $('section.overflow')

		for (let idx = 0; idx < 2; idx++) {
			zoomIn(element)
		}

		console.log(posY, element[0].scrollHeight)

		element
			.scrollTop(posY * element[0].scrollHeight)
			.scrollLeft(posX * element[0].scrollWidth)
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

//Delete Modal Name
function DelName() {
}
//Delete Modal Name End

function closeModal(modalId, button) {
	$(button).click(function () {

		$(modalId).modal('hide');

	});

}

/**/
function showGenericName() {

	if (scale > 2)
		$('.equip-header').css('opacity', 1);

	else $('.equip-header').css('opacity', 0);

}

function borderEquip(id) {
	var equipBorder = document.getElementById(id);
	if (equipBorder != null) {
		equipBorder.style.border = "2px solid black"
		equipBorder.style.borderImage = "url(\"data:image/svg+xml;charset=utf-8,%3Csvg width='100' height='100' viewBox='0 0 100 100' fill='none' xmlns='http://www.w3.org/2000/svg'%3E %3Cstyle%3Epath%7Banimation:stroke 5s infinite linear%3B%7D%40keyframes stroke%7Bto%7Bstroke-dashoffset:776%3B%7D%7D%3C/style%3E%3ClinearGradient id='g' x1='0%25' y1='0%25' x2='0%25' y2='100%25'%3E%3Cstop offset='0%25' stop-color='%232d3561' /%3E%3Cstop offset='25%25' stop-color='%23c05c7e' /%3E%3Cstop offset='50%25' stop-color='%23f3826f' /%3E%3Cstop offset='100%25' stop-color='%23ffb961' /%3E%3C/linearGradient%3E %3Cpath d='M1.5 1.5 l97 0l0 97l-97 0 l0 -97' stroke-linecap='square' stroke='url(%23g)' stroke-width='3' stroke-dasharray='388'/%3E %3C/svg%3E\") 1"
		setTimeout(function () {
			equipBorder.style.border = ""
			equipBorder.style.borderImage = ""
		}, 2500);
	}
}

// *********************************************************** //


function hiddenBtns() {
	document.getElementById("topButtons").style.display = "none";
}

function showBtns() {
	document.getElementById("topButtons").style.display = "block";
}

function alertOptions(a) {
	$(a).css("display", "block");
	setTimeout(function () {
		$(a).fadeOut('fast');
	}, 2000);
	hiddenPosition();
}

function hiddenPosition() {
	document.getElementById("real:savePos").style.display = "none";
	document.getElementById("real:cancelPos").style.display = "none";
	posReset();
	showBtns();
}

//VARS
var id, type, toDrag;

function sendToBean() {

	document.getElementById('form-modal-submit:equipCod').value = id;
	document.getElementById('form-modal-submit:equipTable').value = type;

}

function sendToBeanDel() {

	document.getElementById('delete-equip-form:del-equip-name').innerText = type + id;

}

function sendType() {
	document.getElementById('edit-equip-form:equipId1').value = id;
	document.getElementById('edit-equip-form:equipTable1').value = type;
}

function deleteParameters() {
	document.getElementById('delete-equip-form:equipDel').value = id;
	document.getElementById('delete-equip-form:tableDel').value = type;
}

function deleteInfo() {
	document.getElementById('delete-equip-form:equipIdDel').value = id;
	document.getElementById('delete-equip-form:equipTableDel').value = type;
}

function hideEditDirections() {
	$('#direction3-edit').hide();
	$('#direction4-edit').hide();
	$('#direction5-edit').hide();
	$('#direction6-edit').hide();
	$('#direction7-edit').hide();
	$('#direction8-edit').hide();
}

///MAP/GRID HIDE AND SHOW

$(function () {

	$('[id$=divide]').click(function () {
		$(".overflow").hide();
		$(".map-options").hide();
		$("#dropdwnsizebtn").hide();
		$(".map-full-option").show();
		$("#mapDivide").show();

	});

	$('[id$=cancelPos]').click(function () {
		$('[id$=savePos]').hide();
		$('[id$=cancelPos]').hide();
	});

	$('[id$=full]').click(function () {
		$(".map-full-option").hide();
		$("#mapDivide").hide();
		$(".overflow").show();
		$(".map-options").show();
		$("#dropdwnsizebtn").show();
	});

	$('[id$=position]').click(function () {
		$('[id$=savePos]').show();
		$('[id$=cancelPos]').show();
	});

	$('[id$=addequip]').click(function () {
		$('[id$=savePos]').hide();
		$('[id$=cancelPos]').hide();
	});
});

///MAP/GRID HIDE AND SHOW END	

$(function () {

	//ZOOM EVENT
	$('[id$="zoomIn"]').click(function () {
		zoomIn($('section.overflow'))
	});
	$('[id$="zoomOut"]').click(
		function () {
			zoomOut($('section.overflow'))
		});
	$('[id$="zoomIn1"]').click(
		function () {
			$('#frame1')[0].contentWindow.zoomIn("section.overflow")
		});
	$('[id$="zoomOut1"]').click(
		function () {
			$('#frame1')[0].contentWindow.zoomOut("section.overflow")
		});
	$('[id$="zoomIn2"]').click(
		function () {
			$('#frame2')[0].contentWindow.zoomIn("section.overflow")
		});
	$('[id$="zoomOut2"]').click(
		function () {
			$('#frame2')[0].contentWindow.zoomOut("section.overflow")
		});
	$('[id$="zoomIn3"]').click(
		function () {
			$('#frame3')[0].contentWindow.zoomIn("section.overflow")
		});
	$('[id$="zoomOut3"]').click(
		function () {
			$('#frame3')[0].contentWindow.zoomOut("section.overflow")
		});
});

//Coefficient Script
$(document).on(
	'click',
	'.box-right-delivery button',
	function () {
		var btn = $(this), oldValue = btn.closest(
			'.box-right-delivery').find('input').val()
			.trim(), newVal = 0;
		if (btn.attr('data-dir') == 'up') {
			newVal = parseInt(oldValue) + 1;
		} else {
			if (oldValue > 1) {
				newVal = parseInt(oldValue) - 1;
			} else {
				newVal = 0;
			}
		}
		btn.closest('.box-right-delivery').find('input').val(
			newVal);
	});
//ZOOM EVENT

//SAT LANES INPUTS


$(function () {
	$('[id$="equips"]').click(function btnSave() {
		setTimeout(() => {
			var equipsSEL = document.getElementById("equips");
			var selectVAL = equipsSEL.options[equips.selectedIndex].value;
			if (selectVAL == 9) {
				$('.satInputs').show(); // DIV FAIXAS 1	
				$('.dmsHidden').hide();
			    $('.sosInputs').hide();
				$('.speedHidden').hide();
				$('.ipAddressShow').show();
				$('#id-type').addClass('col-md-12').removeClass('col-md-6').find('.valid-icon-visible').css('margin-left', '')
				$("#lanes").change(
					function () {
						var satLanes = document.getElementById("lanes");
						var selectSAT = satLanes.value;
						if (selectSAT == 2) {
							$('#direction3').hide();
							$('#direction4').hide();
							$('#direction5').hide();
							$('#direction6').hide();
							$('#direction7').hide();
							$('#direction8').hide();
						} else if (selectSAT == 3) {
							$('#direction3').show();
							$('#direction4').hide();
							$('#direction5').hide();
							$('#direction6').hide();
							$('#direction7').hide();
							$('#direction8').hide();
						} else if (selectSAT == 4) {
							$('#direction3').show();
							$('#direction4').show();
							$('#direction5').hide();
							$('#direction6').hide();
							$('#direction7').hide();
							$('#direction8').hide();
						} else if (selectSAT == 5) {
							$('#direction3').show();
							$('#direction4').show();
							$('#direction5').show();
							$('#direction6').hide();
							$('#direction7').hide();
							$('#direction8').hide();
						} else if (selectSAT == 6) {
							$('#direction3').show();
							$('#direction4').show();
							$('#direction5').show();
							$('#direction6').show();
							$('#direction7').hide();
							$('#direction8').hide();
						} else if (selectSAT == 7) {
							$('#direction3').show();
							$('#direction4').show();
							$('#direction5').show();
							$('#direction6').show();
							$('#direction7').show();
							$('#direction8').hide();
						} else if (selectSAT == 8) {
							$('#direction3').show();
							$('#direction4').show();
							$('#direction5').show();
							$('#direction6').show();
							$('#direction7').show();
							$('#direction8').show();
						}
					});

			} else if (selectVAL == 8) {
				
				$('.dmsHidden').show(); // DIV DMS TYPE				
				$('.satInputs').hide();
				$('.sosInputs').hide();
				$('.speedHidden').hide();
				$('.ipAddressShow').show();

			} else if (selectVAL == 10) {

				$('.sosInputs').show();				
				$('.satInputs').hide();
				$('.mtoHidden').hide();
				$('.speedHidden').hide();
				$('.ipAddressShow').show();

			} else if (selectVAL == 11) {

				$('.speedHidden').show();
				$('.sosInputs').hide();
				$('.dmsHidden').hide();
				$('.satInputs').hide();				
				$('.ipAddressShow').hide();

			} else {

				$('.dmsHidden').hide();			
				$('.satInputs').hide();
				$('.sosInputs').hide();
				$('.speedHidden').hide();
				$('.ipAddressShow').show();				
			}

		}, 100)
	});
});

//SAT LANES INPUTS END

// MODAL EQUIPMENT SIZE

$(function () {
	$('[class$="dprbtn"]').click(
		function () {
			let barSize = $('#bar-size')
			let min = barSize.attr('min')
			let max = barSize.attr('max')
			let value = $(`.${$(this).attr('from')}`).attr('barSize') || 1

			barSize.attr('value', value).parent().css('left', `${(value - min) / (max - min) * 100}%`)

			var url = $('.draggable').find(
				"[id^=" + $(this).attr('from')
				+ "] img").attr("src")
				|| "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=";
			let modal = $("#sizeeqps")
			modal.find("img").attr("src", url);
			modal.find("#bar-size").attr("from",
				"." + $(this).attr('from'))


		});
});

// MODAL EQUIPMENT SIZE END

$(function () {

	$('[id$="km"]').mask('000+000'); 	// KM MASK	
	$('[id$="kmEdit"]').mask('000+000'); 	// KM MASK

});

/* show hidden buttons */

$(function () {
	$('#btnLayers').removeClass('hidden').addClass('show');
	$('#btnEquips').removeClass('hidden').addClass('show');
});

/* show hidden buttons */

function cftvTop(){
	alert("^")
}
function cftvLeft(){
	alert("<")
}
function cftvRight(){
	alert(">")
}
function cftvBottom(){
	alert("v")
}

function DirectionEquip(){
/* top and bottom line */

$('.equip-info[direction], .equip-box[direction], .equip-box-sat[direction]').each(function (idx, item){
	item = $(item)
	if(item.attr('direction') == 'N') { 
	item.find('.equipLine').show();
	item.find('.equipLineTop').hide();
	} else {
		item.find('.equipLineTop').show();
		item.find('.equipLine').hide();
	}
	 })

/* top and bottom line [end] */
}