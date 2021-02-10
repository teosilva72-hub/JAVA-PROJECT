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
 	let zoomTarget = equip.closest('.overflow').children().first()
 	let zoomTargetImg = zoomTarget.find('img')
 	let pos = {
 		x: Number(equip.attr('posX')),
		y: Number(equip.attr('posY'))
	}
	
 	equip.css({
 		left: pos.x,
 		top: pos.y
	});
	
	if (equip.attr("class").includes('equip-box-sat')) {
		drawSat(equip.attr('id'), `satTab${equip.attr('id').replace('sat', '')}`, equip.attr('status'), equip.find('speed1').text(), equip.find('speed2').text(), 'fluxos', `img1FluxoTab${equip.attr('id').replace('sat', '')}`, `img2FluxoTab${equip.attr('id').replace('sat', '')}`, `satName${equip.attr('id').replace('sat', '')}`, pos.x, pos.y, Number(equip.attr('item-width')))
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

function drawGenericEquipments(equip_id, table_id, posX, posY, width, factor) {

	//Pos X and Pos Y
	document.getElementById(equip_id).style.left = posX + "px";
	document.getElementById(equip_id).style.top = posY + "px";

	//Wequip_idth	  
	document.getElementById(equip_id).style.width = width + "px";

	//Height 
	document.getElementById(equip_id).style.height = (width * 1.3) + "px";

	//Table td congi
	/*var generic_tabHeader = document.getElementById(table_id);
	var generic_tr = generic_tabHeader.getElementsByTagName("tr");
	var generic_td = null;

	generic_td = generic_tr[0].getElementsByTagName("td");
	generic_td[0].style.height = ((width * 1.3) * 0.2) + "px";
	generic_td[0].style.width = (width * 0.8) + "px";

	generic_td[1].style.height = ((width * 1.3) * 0.2) + "px";
	generic_td[1].style.width = (width * 0.2) + "px";

	//Table fit font on header
	generic_tabHeader.style.fontSize = calculateFontGenericSizeHeader(generic_tabHeader.offsetWidth, generic_tabHeader.offsetHeight, generic_tabHeader.innerHTML, factor) + "pt";
   */
}

/* FONT SIZING */

//Generic Title
function calculateFontGenericSizeHeader(width, height, content, factor) {

	var area = (width) * (height);
	var contentLength = content.length;

	return Math.sqrt((area) / (contentLength)) * factor;  //this provides the font-size in pixels.
}

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

//DRAW SAT

function drawSat(sat_id, sat_tab, sat_status, sat_speed1, sat_speed2, fluxos, fluxo_img1, fluxo_img2, satName,
	posX, posY, width) {

	//Position
	document.getElementById(sat_id).style.left = posX + "px";
	document.getElementById(sat_id).style.top = posY + "px";

	//Comprimento		  
	document.getElementById(sat_id).style.width = width + "px";
	document.getElementById(fluxos).style.width = width + "px";

	//altura	 
	document.getElementById(sat_id).style.height = (width * 1.5) + "px";

	//fluxos div
	document.getElementById(fluxos).style.width = (width * 0.95) + "px";
	document.getElementById(fluxos).style.height = ((width * 0.63) / 2) + "px";
	document.getElementById(fluxos).style.marginTop = 12 + "px";
	document.getElementById(fluxos).style.border = "1px solid transparent";

	//fluxos sub divs
	document.getElementById(fluxo_img1).style.height = (width * 0.10) + "px";
	document.getElementById(fluxo_img2).style.height = (width * 0.10) + "px";
	document.getElementById(fluxo_img1).style.border = "1px solid transparent";
	document.getElementById(fluxo_img2).style.border = "1px solid transparent";

	document.getElementById(fluxo_img1).style.marginTop = 7 + "px";

	//Table Definitions
	/* var tabSat = document.getElementById(sat_tab);      
		    
			var trSat = tabSat.getElementsByTagName("tr");		    
			var tdSat = null;
			 
			 for (var i=0; i < trSat.length; i++)
			 {
				 tdSat = trSat[i].getElementsByTagName("td");
							 
				 for (var n=0; n < tdSat.length; n++)
				 {		        	
					 tdSat[n].style.height = ((width * 0.6  ) / 2) + "px";  			        	
					 tdSat[n].style.width = (width / 6) + "px";
					 
				 }
			 } 	*/

	//Header Color Status	

	//Green Color > indica que o equipamento está conectado
	if (sat_status > 7) {
		document.getElementById(satName).style.backgroundColor = '#00FF0D';
		document.getElementById(satName).style.color = 'black';
		document.getElementById(`status${sat_id}`).style.color = '#00FF0D';	

		//SeaGreen Color > indica que o equipamento está com perca de pacotes
	} else if (sat_status > 0 && sat_status < 8) {
		document.getElementById(satName).style.backgroundColor = '#00FF7F';
		document.getElementById(satName).style.color = 'black';
		document.getElementById(`status${sat_id}`).style.color = '#00FF7F';	
	}
	//Red Color > indica que o equipamento está sem comunicação
	else {
		document.getElementById(satName).style.backgroundColor = '#FF0000';
		document.getElementById(satName).style.color = 'white';
		document.getElementById(`status${sat_id}`).style.color = '#FF0000';	
	}


	// VELOCIDADE SENTIDO 1
	if (sat_speed1 != 0) {
		if (sat_speed1 > 0 && sat_speed1 < 31) {
			document.getElementById(fluxo_img1).style.background = "url('/resources/images/realTimeInterface/serviceLevel_dir1_red.png')";
			document.getElementById(fluxo_img1).style.animation = 'myMove 150s linear infinite';
		}
		else if (sat_speed1 > 30 && sat_speed1 < 61) {
			document.getElementById(fluxo_img1).style.background = "url('/resources/images/realTimeInterface/serviceLevel_dir1_orange.png')";
			document.getElementById(fluxo_img1).style.animation = 'myMove 60s linear infinite';
		}
		else if (sat_speed1 > 60) {
			document.getElementById(fluxo_img1).style.background = "url('/resources/images/realTimeInterface/serviceLevel_dir1_green.png')";
			document.getElementById(fluxo_img1).style.animation = 'myMove 20s linear infinite';
		}
	}
	else {
		document.getElementById(fluxo_img1).style.background = "url('/resources/images/realTimeInterface/serviceLevel_dir1_gray.png')";
		document.getElementById(fluxo_img1).style.animation = 'myMove 500s linear infinite';
	}

	//VELOCIDADE SENTIDO 2
	if (sat_speed2 != 0) {
		if (sat_speed2 > 0 && sat_speed2 < 31) {
			document.getElementById(fluxo_img2).style.background = "url('/resources/images/realTimeInterface/serviceLevel_dir2_red.png')";
			document.getElementById(fluxo_img2).style.animation = 'myMove 150s linear infinite';
			document.getElementById(fluxo_img2).style.animationDirection = "reverse";
		}
		else if (sat_speed2 > 30 && sat_speed2 < 61) {
			document.getElementById(fluxo_img2).style.background = "url('/resources/images/realTimeInterface/serviceLevel_dir2_orange.png')";
			document.getElementById(fluxo_img2).style.animation = 'myMove 60s linear infinite';
			document.getElementById(fluxo_img2).style.animationDirection = "reverse";
		}
		else if (sat_speed2 > 60) {
			document.getElementById(fluxo_img2).style.background = "url('/resources/images/realTimeInterface/serviceLevel_dir2_green.png')";
			document.getElementById(fluxo_img2).style.animation = 'myMove 20s linear infinite';
			document.getElementById(fluxo_img2).style.animationDirection = "reverse";
		}
	}
	else {
		document.getElementById(fluxo_img2).style.background = "url('/resources/images/realTimeInterface/serviceLevel_dir2_gray.png')";
		document.getElementById(fluxo_img2).style.animation = 'myMove 500s linear infinite';
		document.getElementById(fluxo_img2).style.animationDirection = "reverse";
	}

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