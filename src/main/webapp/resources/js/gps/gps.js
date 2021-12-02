let draw = $(".drawLines")
let idGps = {}
let posZoom = {}

const connectGPS = async (request, debug) => {
	return await sendMsgStomp(request, 'GPSRequest', debug, 'request')
}

const coordToPixel = (x, y, deg, name) => {
	let d, map;
	let pad = 0;
	if (!name) {
		d = draw;
		map = draw.prev()
		pad = (draw.height() - map.height()) / 2
	} else
		map = d = $(`[name=${name}]`);
	let start = d.attr('gps_start').replaceAll(',', '.').split(';')
	let end = d.attr('gps_end').replaceAll(',', '.').split(';')
	let pos = {
		s1: Number(start[0]),
		s2: Number(start[1]),
		e1: Number(end[0]),
		e2: Number(end[1]),
		ps: Number(start[2] / 1000 * map.height() + pad),
		pe: Number(end[2] / 1000 * map.height() + pad),
		x: Number(x),
		y: Number(y),
		rad: deg * Math.PI / 180
	}
	let distance = {
		longitude: pos.e1 - pos.s1,
		latitude: pos.e2 - pos.s2,
		x: d.width(),
		y: pos.pe - pos.ps,
		hypotenuse: () => Math.sqrt(Math.pow(distance.longitude, 2) + Math.pow(distance.latitude, 2)),
		radius: () => Math.atan(distance.latitude / distance.longitude),
		radiusOpposite: () => Math.atan(distance.longitude / distance.latitude),
		pixel: () => Math.sqrt(Math.pow(d.width(), 2) + Math.pow(distance.y, 2)),
		radiusPixel: () => Math.atan(distance.y / distance.x),
	}
	let diff = {
		longitude: pos.x - pos.s1,
		latitude: pos.y - pos.s2,
		hypotenuse: () => Math.sqrt(Math.pow(diff.longitude, 2) + Math.pow(diff.latitude, 2)),
		radius: () => Math.atan(diff.latitude / diff.longitude),
		radiusOpposite: () => Math.atan(diff.longitude / diff.latitude),
		radiusDiff: () => (invertX ? -(distance.radiusOpposite() - diff.radiusOpposite() - (invertY ? Math.sign(diff.radius()) * -90 : 0)) : distance.radius() - diff.radius()) + distance.radiusPixel(),
		direction: () => {
			let d;
			if (Math.sign(distance.longitude) >= 0)
				if (Math.sign(distance.latitude) >= 0)
					d = distance.radiusOpposite() - pos.rad
				else
					d = distance.radius() - 0.5 * Math.PI + pos.rad
			else
				if (Math.sign(distance.latitude) <= 0)
					d = distance.radiusOpposite() + Math.PI - pos.rad
				else
					d = distance.radius() - 3 * Math.PI / 2 + pos.rad
			return d;
		}
	}
	let invertX = Math.sign(distance.longitude) != Math.sign(diff.longitude)
	let invertY = Math.sign(distance.latitude) != Math.sign(diff.latitude)
	let percent = {
		longitude: diff.longitude / distance.longitude,
		latitude: diff.latitude / distance.latitude,
		hypotenuse: diff.hypotenuse() / distance.hypotenuse()
	}
	return {
		x: percent.hypotenuse * distance.pixel() * Math.cos(diff.radiusDiff()),
		y: pos.ps + percent.hypotenuse * distance.pixel() * Math.sin(diff.radiusDiff()),
		rad: distance.radiusPixel() + diff.direction()
	}
}

const insertZoomPoint = () => {
	let zooms = $(`[name$=-zoomPoint]`)
	zooms.each((i, zoom) => {
		zoom = $(zoom);
		let point = $(zoom).attr('for')
		let z = $(`[name=${$(zoom).attr('for')}]`)
		let pos = posZoom[point]
	
		let width = Math.abs(pos.end.x - pos.start.x);
	
		zoom.css({
			position: "absolute",
			left: pos.start.x + (pos.end.x - pos.start.x) / 2,
			top: pos.start.y + (pos.end.y - pos.start.y) / 2,
			transform: "translate(-50%, -50%)",
			width: width,
			height: width,
			display: "block",
			"z-index": 2
		}).off('click').click(() => zoomRoadPoint(point))
	})
}

const zoomRoadPoint = name => {
	let zoom = $(`[name=${name}]`)
	if (zoom.css("display") == "block") {
		zoom.css("display", "none")
		return
	}
	
	let pos = posZoom[name]

	zoom.css({
		position: "relative",
		left: pos.start.x + (pos.end.x - pos.start.x) / 2,
		top: pos.start.y + (pos.end.y - pos.start.y) / 2,
		transform: "translate(-32.5%, -75.5%)",
		display: "block",
		overflow: "hidden",
		"z-index": 1
	})
	zoom.children().first().css("height", "100%")
}

const drawPointZoom = (id, name, title, coord) => {
	let pos = coordToPixel(coord.x, coord.y, coord.deg, name)
	let draw = $(`[name=${name}]`)
	let divItem = draw.find(`#${id}`)
	let outRange = !(0 < pos.x && pos.x < draw.width()) || !(0 < pos.y && pos.y < draw.height()) //|| pos.x < Math.min(pos.s1, pos.e1) - 0.1 || pos.x > Math.max(pos.s1, pos.e1) + 0.1 || pos.y < Math.min(pos.s2, pos.e2) - 0.1 || pos.y > Math.max(pos.s2, pos.e2) + 0.1
	let defaultCss = {position: 'absolute', left: `${pos.x}px`, top: `${pos.y}px`, transform: `translate(-50%, -50%) rotate(${pos.rad}rad)${((-Math.PI < pos.rad + 0.5 * Math.PI && pos.rad + 0.5 * Math.PI < 0) || pos.rad + 0.5 * Math.PI > Math.PI) ? ' scaleY(-1)' : ''}`, "transform-origin": "50% 50%", width: "30px", "z-index": 1}
	if (coord.speed < 15)
		defaultCss.transform = 'translate(-50%, -50%)'

	if (divItem.length) {
		if (outRange)
			divItem.remove()
		else {
			divItem.css(defaultCss)
		}
	} else if (!outRange) {
		let n = $(`<img id="${id}" src="/resources/images/equips/car.png" data-bs-toggle="tooltip" data-bs-placement="top" title="${title}">`).css(defaultCss)
		draw.append(n)
		n.tooltip()
	}
}

const drawPoint = item => {
	let id = item.i || item.id;
	let name = item.nm || idGps[id]
	let divItem = draw.find(`#${id}`)
	let pos = {
		x: Number(item.d ? item.d.pos.x : item.pos.x),
		y: Number(item.d ? item.d.pos.y : item.pos.y),
		deg: Number(item.d ? item.d.pos.c : item.pos.c),
		speed: Number(item.d ? item.d.pos.s : item.pos.s),
	}
	let point = coordToPixel(pos.x, pos.y, pos.deg)
	let outRange = !(0 < point.x && point.x < draw.width()) || !(0 < point.y && point.y < draw.height()) //|| pos.x < Math.min(pos.s1, pos.e1) - 0.1 || pos.x > Math.max(pos.s1, pos.e1) + 0.1 || pos.y < Math.min(pos.s2, pos.e2) - 0.1 || pos.y > Math.max(pos.s2, pos.e2) + 0.1
	let defaultCss = {position: 'absolute', left: `${point.x}px`, top: `${point.y}px`, transform: `translate(-50%, -50%) rotate(${point.rad}rad)${((-Math.PI < point.rad + 0.5 * Math.PI && point.rad + 0.5 * Math.PI < 0) || point.rad + 0.5 * Math.PI > Math.PI) ? ' scaleY(-1)' : ''}`, "transform-origin": "50% 50%", width: "42px", "z-index": 1}
	if (pos.speed < 15)
		defaultCss.transform = 'translate(-50%, -50%)'

	$('[name$=-zoomPoint]').each((i, c) => {
		drawPointZoom(id, $(c).attr('for'), name, pos)
	})

	if (divItem.length) {
		if (outRange)
			divItem.remove()
		else {
			divItem.css(defaultCss)
		}
	} else if (!outRange) {
		let n = $(`<img id="${id}" src="/resources/images/equips/car.png" data-bs-toggle="tooltip" data-bs-placement="top" title="${name}">`).css(defaultCss)
		draw.append(n)
		n.tooltip()
	}
}

const callback_gps_default = response => {
	if (response.body)
    	response = JSON.parse(response.body).events.forEach(r => drawPoint(r));
}

const consumeGPS = async ({ callback_gps = callback_gps_default, debug = false } = {}) => {
	var client = await getStomp();

	var on_connect = function() {
		client.subscribe(`/exchange/gps_localization/gps_localization`, callback_gps)
	};

	var on_error = async function() {
	    console.log('error');
		await sleep(1000);

		consumeGPS({callback_gps, debug})
	};

	if (!debug)
		client.debug = null
	client.reconnect_delay = 1000;
	client.connect(rabbitmq.user, rabbitmq.pass, on_connect, on_error, '/');
}

const initGPS = async ({ callback_gps = callback_gps_default, debug = false } = {}) => {
    $(async function () {
		let units = await connectGPS('AllUnits')
		let zooms = $(`[name$=-zoomPoint]`)
		zooms.each((i, zoom) => {
			zoom = $(zoom);
			let point = $(zoom).attr('for')
			let z = $(`[name=${$(zoom).attr('for')}]`)
			let start = z.attr('gps_start').replaceAll(',', '.').split(';')
			let end = z.attr('gps_end').replaceAll(',', '.').split(';')
			z.css({
				height: 300,
				width: 300 * 0.616608843537415
			})
			posZoom[point] = {
				start: coordToPixel(start[0], start[1]),
				end: coordToPixel(end[0], end[1])
			}
		})

		for (const item of units.items) {
			idGps[item.id] = item.nm
			drawPoint(item)
		}

		consumeGPS({ callback_gps, debug });
		insertZoomPoint()
	});
}

window.initGPS = initGPS