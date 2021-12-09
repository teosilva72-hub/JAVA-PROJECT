let draw = $(".drawLines")
let idGps = {}
let roadPoint = []
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
		radiusDiff: () => (invertX ? -(distance.radiusOpposite() - diff.radiusOpposite() - (invertY ? Math.sign(diff.radius()) * 90 : 0)) : distance.radius() - diff.radius()) + distance.radiusPixel(),
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

const fillEquips = name => {
	let zoom = $(`[name=${name}]`)
	let radius = zoom.width() / 2
	zoom.children().filter(':not(img)').remove()
	$('.equip-box, .equip-info, .equip-box-sat').each((idx, item) => {
		item = $(item).clone()

		let pos = {
			longitude: item.attr('longitude'),
			latitude: item.attr('latitude'),
			x: item.attr('posx'),
			y: item.attr('posy'),
		}

		let point = coordToPixel(pos.longitude, pos.latitude, undefined, name)
		point.x += +pos.x;
		point.y += +pos.y;
		let distance = Math.sqrt(Math.pow(point.x - radius, 2) + Math.pow(point.y - radius, 2))
		let outRange = distance > radius

		if (!outRange) {
			item.css({
				left: point.x,
				top: point.y,
				transform: `translate(-50%, -70%) scale(${item.attr('scale')})`
			})
			zoom.append(item)
		}
	})
}

const insertZoomPoint = () => {
	let zooms = $(`[name$=-zoomPoint]`)
	zooms.each((i, zoom) => {
		zoom = $(zoom);
		let point = $(zoom).attr('for')
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
		zoom.find('.equip-box, .equip-info, .equip-box-sat, .plaque').remove()
		return
	}
	
	let pos = posZoom[name]

	zoom.css({
		position: "absolute",
		left: pos.start.x + (pos.end.x - pos.start.x) / 2,
		top: pos.start.y + (pos.end.y - pos.start.y) / 2,
		transform: "translate(-32.5%, -75.5%)",
		display: "block",
		overflow: "hidden",
		"z-index": 1
	})
	zoom.children().first().css("height", "100%")

	fillEquips(name)
}

const drawPointZoom = (id, name, title, coord) => {
	let pos = coordToPixel(coord.x, coord.y, coord.deg, name)
	let draw = $(`[name=${name}]`)
	let divItem = draw.find(`#carGPS${id}`)
	let radius = draw.width() / 2
	let distance = Math.sqrt(Math.pow(pos.x - radius, 2) + Math.pow(pos.y - radius, 2))
	let outRange = distance > radius
	let defaultCss = {display: 'block', position: 'absolute', transition: '1s', left: `${pos.x}px`, top: `${pos.y}px`, transform: `translate(-50%, -50%) rotate(${pos.rad}rad)${Math.cos(pos.rad) < 0 ? ' scaleY(-1)' : ''}`, "transform-origin": "50% 50%", width: "30px", "z-index": 1}
	if (coord.speed < 15)
		defaultCss.transform = 'translate(-50%, -50%)'

	if (divItem.length) {
		if (outRange)
			divItem.remove()
		else {
			divItem.css(defaultCss)
		}
	} else if (!outRange) {
		let n = $(`<img id="carGPS${id}" src="/resources/images/equips/car.png" target="carGPS" data-bs-toggle="tooltip" data-bs-placement="top" title="${title}">`).css(defaultCss)
		draw.append(n)
		n.tooltip()
	}
}

const verifRangeRoad = (point, maxRanger) => {
	maxRanger = maxRanger || 10
	return roadPoint.reduce((a, b, idx) => {
		if (a == true)
			return true
		let map = draw.prev()
		let pad = (draw.height() - map.height()) / 2
		let pointA = {
			x: a[1] / 1000 * draw.width(),
			y: a[2] / 1000 * map.height() + pad
		}
		let pointB = {
			x: b[1] / 1000 * draw.width(),
			y: b[2] / 1000 * map.height() + pad
		}
		let triangle = {
			base: Math.sqrt(Math.pow(pointA.x - pointB.x, 2) + Math.pow(pointA.y - pointB.y, 2)),
			A: Math.sqrt(Math.pow(pointA.x - point.x, 2) + Math.pow(pointA.y - point.y, 2)),
			B: Math.sqrt(Math.pow(pointB.x - point.x, 2) + Math.pow(pointB.y - point.y, 2)),
			p: function() {
				return this.base + this.A + this.B
			},
			area: function() {
				let p = this.p() / 2
				return Math.sqrt(p * (p - this.base) * (p - this.A) * (p - this.B))
			},
			h: function() {
				return this.area() * 2 / this.base
			}
		}
		let h = triangle.h()
		let limit = Math.sqrt(Math.pow(h, 2) + Math.pow(triangle.base, 2))
		return (triangle.h() < maxRanger && triangle.A < limit && triangle.B < limit) || (roadPoint.length - 1 > idx && b)
	})
}

const drawPoint = item => {
	let id = item.i || item.id;
	let name = item.nm || idGps[id]
	let divItem = draw.find(`#carGPS${id}`)
	let pos = {
		x: Number(item.d ? item.d.pos.x : item.pos.x),
		y: Number(item.d ? item.d.pos.y : item.pos.y),
		deg: Number(item.d ? item.d.pos.c : item.pos.c),
		speed: Number(item.d ? item.d.pos.s : item.pos.s),
	}
	let point = coordToPixel(pos.x, pos.y, pos.deg)
	let outRange = !verifRangeRoad(point);
	let defaultCss = {display: 'block', position: 'absolute', transition: '1s', left: `${point.x}px`, top: `${point.y}px`, transform: `translate(-50%, -50%) rotate(${point.rad}rad)${Math.cos(point.rad) < 0 ? ' scaleY(-1)' : ''}`, "transform-origin": "50% 50%", width: "42px", "z-index": 1}
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
		let n = $(`<img id="carGPS${id}" src="/resources/images/equips/car.png" target="carGPS" data-bs-toggle="tooltip" data-bs-placement="top" title="${name}">`).css(defaultCss)
		draw.append(n)
		n.tooltip()
		n.contextmenu(ev => {
			contextMenu(ev, 'carGPS', id, false)
		})
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

const replyPos = () => {
	$(`[name$=-zoomPoint]`).each((i, zoom) => {
		zoom = $(zoom);
		let point = $(zoom).attr('for')
		let z = $(`[name=${$(zoom).attr('for')}]`)
		let start = z.attr('gps_start').replaceAll(',', '.').split(';')
		let end = z.attr('gps_end').replaceAll(',', '.').split(';')
		posZoom[point] = {
			start: coordToPixel(start[0], start[1]),
			end: coordToPixel(end[0], end[1])
		}
		z.css({
			height: posZoom[point].start.y * 1.245,
			width: posZoom[point].start.y * 1.245 * 0.616608843537415,
			display: 'none'
		})
	})
}

const initGPS = async ({ callback_gps = callback_gps_default, debug = false } = {}) => {
    $(async function () {
		let units = await connectGPS('AllUnits')
		let road = document.forms.roadLine;
		replyPos()

		for (point of road)
			roadPoint.push(point.value.split(','));

		for (const item of units.items) {
			idGps[item.id] = item.nm
			drawPoint(item)
		}

		consumeGPS({ callback_gps, debug });
		insertZoomPoint()

		$(window).resize(() => {
			replyPos()
			insertZoomPoint()
			$('[target=carGPS]').css('display', 'none')
			connectGPS('AllUnits').then(response => {
				for (const item of response.items)
					drawPoint(item)
			})
		})
	});
}

window.initGPS = initGPS