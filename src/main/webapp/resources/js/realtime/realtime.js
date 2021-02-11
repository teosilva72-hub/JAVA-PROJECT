$(function () {
	$('#btnLayers').removeClass('hidden').addClass('show');
	$('#btnEquips').removeClass('hidden').addClass('show');

	$('.equip-box, .equip-info, .equip-box-sat').each(function () {
		let equip = $(this)

		posEquip(equip)

		$(window).resize(function () {
			posEquip(equip)
			resizeEquip($('#content'))
		})
	})
});

function posEquip(equip) {
	let width = Number(equip.attr('item-width'));
	let pos = {
		x: Number(equip.attr('posX')),
		y: Number(equip.attr('posY'))
	}
	
 	equip.css({
 		left: pos.x,
 		top: pos.y
	});
	
	if (equip.attr("class").includes('equip-box-sat')) {
		drawSat(equip.attr('id'), `satTab${equip.attr('id').replace('sat', '')}`, equip.attr('status'), Number(equip.find('#speed1').text()), Number(equip.find('#speed2').text()), 'fluxos', `img1FluxoTab${equip.attr('id').replace('sat', '')}`, `img2FluxoTab${equip.attr('id').replace('sat', '')}`, `satName${equip.attr('id').replace('sat', '')}`, pos.x, pos.y, Number(equip.attr('item-width')))
		console.log(equip.find('#speed1').text())
	} else if (equip.attr("class").includes('equip-box')) {	
		drawGenericEquipments(equip.attr('id'), `satTab${equip.attr('id').replace('sat', '')}`, pos.x, pos.y, Number(equip.attr('item-width')), 1)
	}
 }

 	//RESIZE EQUIPMENT
	 function resizeEquip(container) {
		container.find('.equip-box, .equip-info, .equip-box-sat').each(function () {
			let equip = $(this)
			let scale = Number(equip.attr('item-width')) / equip.width()
			
			equip.css('transform', `scale(${scale})`)
		})
	}
	
	//RESIZE EQUIPMENT END

	equip.css({
		left: pos.x,
		top: pos.y
	});

	if (equip.attr("class").includes('equip-box-sat')) {
		let sat_status = equip.attr('status')
		let fluxo1 = equip.find('[id^=img1FluxoTab]')
		let fluxo2 = fluxo1.next()
		let speed1 = Number(equip.find('speed1').text())
		let speed2 = Number(equip.find('speed2').text())
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
		if (sat_status > 7) {
			equip.find("[id^=satName]").css({
				"background-color": '#00FF0D',
				color: 'black'
			});
			document.getElementById(`status${equip.attr('id')}`).style.color = '#00FF0D';

		}
		//SeaGreen Color > indica que o equipamento está com perca de pacotes
		else if (sat_status > 0 && sat_status < 8) {
			equip.find("[id^=satName]").css({
				"background-color": '#00FF7F',
				color: 'black'
			});
			document.getElementById(`status${equip.attr('id')}`).style.color = '#00FF7F';
		}
		//Red Color > indica que o equipamento está sem comunicação
		else {
			equip.find("[id^=satName]").css({
				"background-color": '#FF0000',
				color: 'white'
			});
			document.getElementById(`status${equip.attr('id')}`).style.color = '#FF0000';
		}

		// VELOCIDADE SENTIDO 1
		if (speed1 != 0) {
			if (speed1 > 0 && speed1 < 31) {
				fluxo1.css({
					background: "url('/resources/images/realTimeInterface/serviceLevel_dir1_red.png')",
					animation: 'myMove 150s linear infinite',
				})
			}
			else if (speed1 > 30 && speed1 < 61) {
				fluxo1.css({
					background: "url('/resources/images/realTimeInterface/serviceLevel_dir1_orange.png')",
					animation: 'myMove 60s linear infinite',
				})
			}
			else if (speed1 > 60) {
				fluxo1.css({
					background: "url('/resources/images/realTimeInterface/serviceLevel_dir1_green.png')",
					animation: 'myMove 20s linear infinite',
				})
			}
		}
		else {
			fluxo1.css({
				background: "url('/resources/images/realTimeInterface/serviceLevel_dir1_gray.png')",
				animation: 'myMove 500s linear infinite',
			})
		}

		//VELOCIDADE SENTIDO 2
		if (speed2 != 0) {
			if (speed2 > 0 && speed2 < 31) {
				fluxo2.css({
					background: "url('/resources/images/realTimeInterface/serviceLevel_dir2_red.png')",
					animation: 'myMove 150s linear infinite',
					'animation-direction': "reverse",
				})
			}
			else if (speed2 > 30 && speed2 < 61) {
				fluxo2.css({
					background: "url('/resources/images/realTimeInterface/serviceLevel_dir2_orange.png')",
					animation: 'myMove 60s linear infinite',
					'animation-direction': "reverse",
				})
			}
			else if (speed2 > 60) {
				fluxo2.css({
					background: "url('/resources/images/realTimeInterface/serviceLevel_dir2_green.png')",
					animation: 'myMove 20s linear infinite',
					'animation-direction': "reverse",
				})
			}
		}
		else {
			fluxo2.css({
				background: "url('/resources/images/realTimeInterface/serviceLevel_dir2_gray.png')",
				animation: 'myMove 500s linear infinite',
				'animation-direction': "reverse",
			})
		}
	} else if (equip.attr("class").includes('equip-box')) {
		drawGenericEquipments(equip.attr('id'), `satTab${equip.attr('id').replace('sat', '')}`, pos.x, pos.y, Number(equip.attr('item-width')), 1)
	}
}

//RESIZE EQUIPMENT
function resizeEquip(container) {
	container.find('.equip-box, .equip-info, .equip-box-sat').each(function () {
		let equip = $(this)
		let scale = Number(equip.attr('item-width')) / equip.width()

		equip.css('transform', `scale(${scale})`)
	})
}

//RESIZE EQUIPMENT END

/* FONT SIZING */

//FONT SIZE FUNCTION
function calculateFontSize(width, height, content, factor) {

	var area = width * height;
	var contentLength = content.length;

	return Math.sqrt(area / contentLength) * factor; //this provides the font-size in pixels.
}

//DMS TITLE FONT SIZE
function calculateFontSizeDMSTitle(width, height, content) {

	var area = (width) * (height);
	var contentLength = content.length;

	return Math.sqrt((area) / (contentLength)) * 1.5;  //this provides the font-size in pixels.
}

/* FONT SIZING */

//SAT DEFINITIONS

//MAP MOVE CURSOR
function mapMove() {

	const ele = document.getElementById('content');
	ele.style.cursor = 'grab';
	ele.ondragstart = function (
	) {
		return false
	}

	let pos = { top: 0, left: 0, x: 0, y: 0 };

	const mouseDownHandler = function (e) {

		ele.style.cursor = 'grabbing';
		ele.style.userSelect = 'none';

		pos = {
			left: ele.scrollLeft,
			top: ele.scrollTop,
			//Pega a posição atual do mouse
			x: e.clientX,
			y: e.clientY,
		};

		document.addEventListener('mousemove', mouseMoveHandler);
		document.addEventListener('mouseup', mouseUpHandler);

	};

	const mouseMoveHandler = function (e) {
		// O quao longe o mouse esta sendo movido
		const dx = e.clientX - pos.x;
		const dy = e.clientY - pos.y;

		ele.scrollTop = pos.top - dy;
		ele.scrollLeft = pos.left - dx;
	};

	const mouseUpHandler = function () {
		ele.style.cursor = 'grab';
		ele.style.removeProperty('user-select');

		document.removeEventListener('mousemove', mouseMoveHandler);
		document.removeEventListener('mouseup', mouseUpHandler);
	};

	ele.addEventListener('mousedown', mouseDownHandler);

}


//MAP DIVIDED MOVE CURSOR

function mapMoveDivs() {

	const ele = document.getElementById('divs');
	ele.style.cursor = 'grab';
	ele.ondragstart = function (
	) {
		return false
	}

	let pos = { top: 0, left: 0, x: 0, y: 0 };

	const mouseDownHandler = function (e) {

		ele.style.cursor = 'grabbing';
		ele.style.userSelect = 'none';

		pos = {
			left: ele.scrollLeft,
			top: ele.scrollTop,
			//Pega a posição atual do mouse
			x: e.clientX,
			y: e.clientY,
		};

		document.addEventListener('mousemove', mouseMoveHandler);
		document.addEventListener('mouseup', mouseUpHandler);

	};

	const mouseMoveHandler = function (e) {
		// O quao longe o mouse esta sendo movido
		const dx = e.clientX - pos.x;
		const dy = e.clientY - pos.y;

		ele.scrollTop = pos.top - dy;
		ele.scrollLeft = pos.left - dx;
	};

	const mouseUpHandler = function () {
		ele.style.cursor = 'grab';
		ele.style.removeProperty('user-select');

		document.removeEventListener('mousemove', mouseMoveHandler);
		document.removeEventListener('mouseup', mouseUpHandler);
	};

	ele.addEventListener('mousedown', mouseDownHandler);

}

function mapMoveDivs1() {

	const ele = document.getElementById('divs1');
	ele.style.cursor = 'grab';
	ele.ondragstart = function (
	) {
		return false
	}

	let pos = { top: 0, left: 0, x: 0, y: 0 };

	const mouseDownHandler = function (e) {

		ele.style.cursor = 'grabbing';
		ele.style.userSelect = 'none';

		pos = {
			left: ele.scrollLeft,
			top: ele.scrollTop,
			//Pega a posição atual do mouse
			x: e.clientX,
			y: e.clientY,
		};

		document.addEventListener('mousemove', mouseMoveHandler);
		document.addEventListener('mouseup', mouseUpHandler);

	};

	const mouseMoveHandler = function (e) {
		// O quao longe o mouse esta sendo movido
		const dx = e.clientX - pos.x;
		const dy = e.clientY - pos.y;

		ele.scrollTop = pos.top - dy;
		ele.scrollLeft = pos.left - dx;
	};

	const mouseUpHandler = function () {
		ele.style.cursor = 'grab';
		ele.style.removeProperty('user-select');

		document.removeEventListener('mousemove', mouseMoveHandler);
		document.removeEventListener('mouseup', mouseUpHandler);
	};

	ele.addEventListener('mousedown', mouseDownHandler);

}

function mapMoveDivs2() {

	const ele = document.getElementById('divs2');
	ele.style.cursor = 'grab';
	ele.ondragstart = function (
	) {
		return false
	}

	let pos = { top: 0, left: 0, x: 0, y: 0 };

	const mouseDownHandler = function (e) {

		ele.style.cursor = 'grabbing';
		ele.style.userSelect = 'none';

		pos = {
			left: ele.scrollLeft,
			top: ele.scrollTop,
			//Pega a posição atual do mouse
			x: e.clientX,
			y: e.clientY,
		};

		document.addEventListener('mousemove', mouseMoveHandler);
		document.addEventListener('mouseup', mouseUpHandler);

	};

	const mouseMoveHandler = function (e) {
		// O quao longe o mouse esta sendo movido
		const dx = e.clientX - pos.x;
		const dy = e.clientY - pos.y;

		ele.scrollTop = pos.top - dy;
		ele.scrollLeft = pos.left - dx;
	};

	const mouseUpHandler = function () {
		ele.style.cursor = 'grab';
		ele.style.removeProperty('user-select');

		document.removeEventListener('mousemove', mouseMoveHandler);
		document.removeEventListener('mouseup', mouseUpHandler);
	};

	ele.addEventListener('mousedown', mouseDownHandler);

}



//DRAW DMS
function drawDMS(dms_id, dms_name_id, dms_msg_id, posX, posY, width, height) {

	//solution for IDENTIFY MICROSOFT IE 
	var isIE = /*@cc_on!@*/false || !!document.documentMode;

	// Definição das posições
	document.getElementById(dms_id).style.left = posX + "px";
	document.getElementById(dms_id).style.top = posY + "px";

	//Comprimento		  
	document.getElementById(dms_id).style.width = width + "px";

	//Altura
	document.getElementById(dms_id).style.height = height + 17 + "px";

	//DEFINE FROM MSG_ID
	var dmsTab = document.getElementById(dms_msg_id);

	var tr = dmsTab.getElementsByTagName("tr");
	var td = null;

	for (var i = 0; i < tr.length; i++) {
		td = tr[i].getElementsByTagName("td");

		for (var n = 0; n < td.length; n++) {
			td[n].style.height = ((height * 0.65) / 3) + "px";
			td[n].style.width = ((width * 0.74) / 12) + "px";

			td[n].style.lineHeight = ((height * 0.65) / 3) + "px";
			td[n].style.overflow = "hidden";
		}
	}

	// Border Right
	document.getElementById(dms_id).style.borderRight = ((height * 0.74) / 9) + "px solid #00FA9A";

	// SET MESSAGES SQUARE FONT 
	dmsTab.style.fontSize = calculateFontSize(dmsTab.offsetWidth, dmsTab.offsetHeight, dmsTab.innerHTML, 7) + "pt";

	// SET HEADER FONT
	//DEFINE FROM NAME_ID
	var dmsHeader = document.getElementById(dms_name_id);
	dmsHeader.style.fontSize = calculateFontSizeDMSTitle(dmsHeader.offsetWidth, dmsHeader.offsetHeight, dmsHeader.innerHTML) + "px";

}

//////// ZOOM VARS
var zoom = 1;
var zoomStep = 0.1;
var zoomMin = 1;
var zoomMax = 5;
var left = 1;
var leftStep = 3;

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




   //else if(zoom == zoomMin){
    //       document.getElementById(id).style.marginRight =  40 + "%";
   // }


 //////// ZOOM VARS		