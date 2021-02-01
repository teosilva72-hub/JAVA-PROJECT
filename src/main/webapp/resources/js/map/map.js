var widthMax = 1000
var heightMax = 1000

$(function () {
	//Scroll Zoom Map Full
	$('[scroll-zoom]').each(function () {
		let map = $(this)
		ScrollZoom(map)
		mapMove(map)
	})
	barResize()
	//Scroll Zoom Map Full END

	//Validate
	var form = document.querySelectorAll('form');
	for (const f of form) {
		f.addEventListener('submit', function (e) {
			let count = 0
			let requiredField = f.querySelectorAll('[requiredField]');
			e.preventDefault()
			if (requiredField.length) {
				for (const required of requiredField) {
					if (!required.value ||
						(required.getAttribute('requiredField')) === 'number' &&
						isNaN(required.value)) {

						count++

						required.style.border = "solid 2px red";
					} else {
						required.style.border = "solid 2px green";
					};
				};
			};
			if (!count) {
				let data = {}
				for (const inputForm of $(f).find('input, select')) {
					data[inputForm.getAttribute('name')] = inputForm.value
				}
				$.post(f.getAttribute('action'), data)
			}
		});
	};
	//Validate End

	//FULLSCREEN
	$('.zoomFull').addClass('img-enlargeable').click(function () {
		var src = this.getAttribute('target');
		var modal;
		let pos = { top: 0, left: 0, x: 0, y: 0 }
		let img = $('<img />').attr('src', src).addClass('box-img')
		let frame = $('<div></div>').css({
			width: '90%', height: '90%',
			transform: 'translate(-50%, -50%)',
			position: 'relative',
			top: '50%', left: '50%',
			overflow: 'hidden',
		}).append(img)

		function removeModal() { modal.remove(); $('body').off('keyup.modal-close'); }

		modal = $('<div></div>').css({
			background: 'RGBA(0,0,0,1.0)',
			width: '100%', height: '100%',
			position: 'fixed',
			zIndex: '10000',
			top: '0', left: '0',
		}).append(frame).click(function () { removeModal(); }).appendTo('body')
		//Zoom
		img.click(function (e) { e.stopPropagation() })
			.on("dragstart", function () { return false })
			.on("mousedown", function (down) {
				if (down.target.hasAttribute("zoom")) {
					pos = {
						left: frame.scrollLeft(),
						top: frame.scrollTop(),
						x: down.clientX,
						y: down.clientY,
					};
					img.on("mousemove", function (move) {
						img.css({ "cursor": "grabbing" })
							.off("mouseup").on("mouseup", function () {
								img.css({ "cursor": "zoom-out" }).off("mousemove")
							})
						const dx = move.clientX - pos.x;
						const dy = move.clientY - pos.y;
						frame
							.scrollTop(pos.top - dy)
							.scrollLeft(pos.left - dx)
					}).off("mouseup").on("mouseup", function () {
						img.css({ "cursor": "zoom-in" }).off("mousemove")
						this.toggleAttribute("zoom")
					})
				} else {
					img.off("mouseup").on("mouseup", function (e) {
						let click = {
							top: (e.pageY - img.offset().top) / img.height(),
							left: (e.pageX - img.offset().left) / img.width(),
						}
						this.toggleAttribute("zoom")
						img.css({ "cursor": "zoom-out" })
						frame
							.scrollLeft(
								click.left * e.target.scrollWidth - frame.width() / 2
							).scrollTop(
								click.top * e.target.scrollHeight - frame.height() / 2

							)

					})
				}
			});

		// handling ESC
		$('body').on('keyup.modal-close', function (e) {
			if (e.key === 'Escape') { removeModal(); }
		});
	});
	// FULLSCREEN END

	// POS EQUIP

	$('.equip-box, .equip-info, .equip-box-sat').each(function () {
		let equip = $(this)

		posEquip(equip)
		resizeEquip(equip.closest('[scroll-zoom]'))

		equip.dblclick(function () {
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

	//Equipments change sizes

	$('.input-group .button-plus, .input-group .button-minus').click(function () {
		let button = $(this)
		let input = $(this).parent().find('input');
		let step = Number(`${button.val()}${input.attr('step')}`)
		let currentVal = Number(input.val());
		let min = input.attr('min')
		let max = input.attr('max')

		console.log(step, currentVal)

		if (!isNaN(currentVal)) {
			input.val(Math.max(min, Math.min(max, currentVal + step))).trigger('change');
		} else {
			input.val(1);
		}
	});
	
	//Equipments change sizes END
	
	
	
	$('#coefSize').change(function () {
		resizeEquip($('[scroll-zoom]'))
	})

})

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

		$('.equip-box, .equip-info, .equip-box-sat').each(function () {
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
	    let por = Math.min(1, Math.max(0, pos / width))
	    let newVal = por * (max - min) + min
	    
	    size.children().first().css("left", `${por * 100}%`).find('input').val(newVal).trigger("change")
	
	    $(document).on('touchmove mousemove', function(e) {
	        clientX = (e.touches || [e])[0].clientX
			offSetLeft = size.offset().left
	        pos = clientX - offSetLeft
	        por = Math.min(1, Math.max(0, pos / width))
	        newVal = por * (max - min) + min
console.log(newVal)
	 		input.val(newVal).trigger('change').parent().css('left', `${por * 100}%`)
	    }).on("mouseup touchend", () => {
	        $(document).off("touchmove mousemove")
	    })
	}).children().first().css("left", `${(value-min)/ (max-min) * 100}%`)
}

// SIZE BAR EQUIPMENTS END

// Scale equips

function resizeEquip(container) {
	container.find('.equip-box, .equip-info, .equip-box-sat').each(function () {
		let equip = $(this)
		let scale = Number(equip.attr('item-width')) / equip.width()
		equip.css('transform', `scale(${scale * (Number($('#coefSize').val()) || 1)})`)
	})
}

function posEquip(equip) {
	let zoomTarget = equip.closest('[scroll-zoom]').children().first()
	let zoomTargetImg = zoomTarget.find('img')
	let pos = {
		x: Number(equip.attr('posX')),
		y: Number(equip.attr('posY'))
	}

	pos.centX = pos.x / widthMax
	pos.centY = pos.y / heightMax

	//Pos X and Pos Y
	equip.css({
		left: pos.centX * zoomTarget.width() + zoomTargetImg.offset().left - zoomTarget.offset().left,
		top: pos.centY * zoomTargetImg.height() + zoomTargetImg.offset().top - zoomTarget.offset().top
	});
}


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

// /* COPIED BY REALTIME JS */

// $(document).ready(function() {  
//     $('#btnLayers').removeClass('hidden').addClass('show');  
//     $('#btnEquips').removeClass('hidden').addClass('show');  
// });

function drawGenericEquipments(equip_id, table_id, posX, posY, width, factor) {
	// let equip = document.getElementById(equip_id)
	// let posXCent = posX / 1380

	// //Pos X and Pos Y
	// equip.style.left = posXCent * document.body.clientWidth + "px";
	// equip.style.top = posY + "px";



	// * Wequip_idth	  
	// equip.style.width = width + "px"; 

	// * Height 
	// equip.style.height = (width * 1.3) + "px"; 

	// * Table td congi
	// var generic_tabHeader = document.getElementById(table_id);			  
	// var generic_tr = generic_tabHeader.getElementsByTagName("tr");		    
	// var generic_td = null;

	// generic_td = generic_tr[0].getElementsByTagName("td");
	// generic_td[0].style.height = ((width * 1.3) * 0.2) +"px";
	// generic_td[0].style.width = (width * 0.8) + "px";	

	// generic_td[1].style.height = ((width * 1.3) * 0.2) +"px";
	// generic_td[1].style.width = (width * 0.2) + "px";	

	// * Table fit font on header
	// generic_tabHeader.style.fontSize = calculateFontSize(generic_tabHeader.offsetWidth, generic_tabHeader.offsetHeight, generic_tabHeader.innerHTML, factor) + "pt"; 

}

// * FONT SIZING

//FONT SIZE FUNCTION
function calculateFontSize(width, height, content, factor) {

	// var area = width * height ;
	// var contentLength = content.length;

	// return  Math.sqrt(area/contentLength) * factor; //this provides the font-size in pixels.
}

/* FONT SIZING */

//SAT DEFINITIONS

//DRAW DMS
function drawDMS(dms_id, dms_name_id, dms_msg_id, posX, posY, width, height) {
	// let dms = document.getElementById(dms_id)

	//  //solution for IDENTIFY MICROSOFT IE 
	// var isIE = /*@cc_on!@*/false || !!document.documentMode;	

	//  // Definição das posições
	// dms.style.left = posX + "px";
	// dms.style.top = posY + "px";

	//  //Comprimento		  
	// dms.style.width = width + "px";	

	//  //Altura
	// dms.style.height = height + 17  + "px";   

	//  //DEFINE FROM MSG_ID
	// var dmsTab = document.getElementById(dms_msg_id);      

	// var tr = dmsTab.getElementsByTagName("tr");		    
	// var td = null;

	// for (var i=0; i < tr.length; i++)
	// {
	// 	td = tr[i].getElementsByTagName("td");

	// 	for (var n=0; n < td.length; n++) {
	// 		td[n].style.height = ((height * 0.65) / 3) +"px";
	// 		td[n].style.width = ((width * 0.74) /12) + "px";

	// 		td[n].style.lineHeight = ((height * 0.65) / 3) +"px";		            
	// 		td[n].style.overflow = "hidden";
	// 	}
	// } 	

	//  // Border Right
	// dms.style.borderRight = ((height * 0.74) / 9) + "px solid #00FA9A";

	//  // SET MESSAGES SQUARE FONT 
	// dmsTab.style.fontSize = calculateFontSize(dmsTab.offsetWidth, dmsTab.offsetHeight, dmsTab.innerHTML, 7) + "pt";	

	//  // SET HEADER FONT
	//  //DEFINE FROM NAME_ID
	// var dmsHeader = document.getElementById(dms_name_id);		    
	// dmsHeader.style.fontSize = calculateFontSize(dmsHeader.offsetWidth, dmsHeader.offsetHeight, dmsHeader.innerHTML, 1.5) + "px";	

}

//DRAW SAT

function drawSat(sat_id, sat_tab, sat_status, sat_speed1, sat_speed2, fluxos, fluxo_img1, fluxo_img2, satName,
	posX, posY, width) {
	// let sat = document.getElementById(sat_id)
	// let fluxo = document.getElementById(fluxos)
	// let img1 = document.getElementById(fluxo_img1)
	// let img2 = document.getElementById(fluxo_img2)
	// let name = document.getElementById(satName)

	// //Position
	// sat.style.left = posX + "px";
	// sat.style.top = posY + "px";

	// //Comprimento		  
	// sat.style.width = width + "px"; 
	// fluxo.style.width = width + "px"; 

	// //altura	 
	// sat.style.height = (width * 1.25)  + "px"; 

	// //fluxos div
	// fluxo.style.width =  (width * 0.95)  + "px"; 	
	// fluxo.style.height =  ((width * 0.63) / 2)  + "px"; 	
	// fluxo.style.marginTop = 12 + "px"; 
	// fluxo.style.border=  "1px solid transparent"; 

	// //fluxos sub divs
	// img1.style.height = img2.style.height = (width * 0.10)  + "px";	
	// img1.style.border = img2.style.border = "1px solid transparent"; 

	// img1.style.marginTop =  7 +"px"; 

	// //Table Definitions
	// var tabSat = document.getElementById(sat_tab);      

	// 	   var trSat = tabSat.getElementsByTagName("tr");		    
	// 	   var tdSat = null;

	// 	    for (var i=0; i < trSat.length; i++)
	// 	    {
	// 	        tdSat = trSat[i].getElementsByTagName("td");

	// 	        for (var n=0; n < tdSat.length; n++)
	// 	        {		        	
	// 	        	tdSat[n].style.height = ((width * 0.6  ) / 2) + "px";  			        	
	// 	        	tdSat[n].style.width = (width / 4) + "px";

	// 	        }
	// 	    } 	

	// 	    * Header Color Status	

	// 	    * Green Color > indica que o equipamento está conectado
	// 		if(sat_status > 7) {
	// 			name.style.backgroundColor = '#00FF00';
	// 			name.style.color = 'black';

	// 		* SeaGreen Color > indica que o equipamento está com perca de pacotes
	// 		} else if(sat_status > 0 && sat_status < 8 ) {
	// 			name.style.backgroundColor = '#00FF7F';
	// 			name.style.color = 'black';
	// 		}	
	// 	    * Red Color > indica que o equipamento está sem comunicação
	// 		else {		 
	// 			name.style.backgroundColor = '#FF0000';
	// 		    name.style.color = 'white';	     
	// 		}


	// 		* VELOCIDADE SENTIDO 1
	// 		if( sat_speed1 != 0) {
	// 			if(sat_speed1 > 0 && sat_speed1 < 31){
	// 				img1.style.background = "url('/Tracevia/resources/images/realTimeInterface/serviceLevel_dir1_red.png')";
	// 				img1.style.animation = 'myMove 150s linear infinite';				
	// 			}
	// 			else if(sat_speed1 > 30 && sat_speed1 < 61){
	// 				img1.style.background = "url('/Tracevia/resources/images/realTimeInterface/serviceLevel_dir1_orange.png')";
	// 				img1.style.animation = 'myMove 60s linear infinite';		
	// 			}
	// 			else if(sat_speed1 > 60){
	// 				img1.style.background = "url('/Tracevia/resources/images/realTimeInterface/serviceLevel_dir1_green.png')";
	// 				img1.style.animation = 'myMove 20s linear infinite';			 
	// 			}		
	// 		}	
	// 		else {
	// 			img1.style.background = "url('/Tracevia/resources/images/realTimeInterface/serviceLevel_dir1_gray.png')";
	// 			img1.style.animation = 'myMove 500s linear infinite';	
	// 		}

	// 		* VELOCIDADE SENTIDO 2
	// 		if( sat_speed2 != 0){
	// 			if(sat_speed2 > 0 && sat_speed2 < 31){
	// 				img2.style.background = "url('/Tracevia/resources/images/realTimeInterface/serviceLevel_dir2_red.png')";
	// 				img2.style.animation = 'myMove 150s linear infinite';
	// 				img2.style.animationDirection = "reverse";
	// 			}
	// 			else if(sat_speed2 > 30 && sat_speed2 < 61){
	// 				img2.style.background = "url('/Tracevia/resources/images/realTimeInterface/serviceLevel_dir2_orange.png')";
	// 				img2.style.animation = 'myMove 60s linear infinite';	
	// 				img2.style.animationDirection = "reverse";
	// 			}
	// 			else if(sat_speed2 > 60){
	// 				img2.style.background = "url('/Tracevia/resources/images/realTimeInterface/serviceLevel_dir2_green.png')";
	// 				img2.style.animation = 'myMove 20s linear infinite';
	// 				img2.style.animationDirection = "reverse";
	// 			}		
	// 		}	  
	// 		    else {
	// 				img2.style.background = "url('/Tracevia/resources/images/realTimeInterface/serviceLevel_dir2_gray.png')";
	// 				img2.style.animation = 'myMove 500s linear infinite';	
	// 				img2.style.animationDirection = "reverse";
	// 		    }

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
}

function closeDragElement() {
	// Stop moving when mouse button is released:
	$(document)
		.off("mouseup")
		.off("mousemove")
}

//Drag/Drop Element END


