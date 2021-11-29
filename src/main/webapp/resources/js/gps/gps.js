let draw = $(".drawLines")
let idGps = {}

const connectGPS = async (request, debug) => {
	return await sendMsgStomp(request, 'GPSRequest', debug, 'request')
}

const coordToPixel = (x, y) => {
	let start = draw.attr('gps_start').replaceAll(',', '.').split(';')
	let end = draw.attr('gps_end').replaceAll(',', '.').split(';')
	let map = draw.prev()
	let pad = (draw.height() - map.height()) / 2
	let pos = {
		s1: Number(start[0]),
		s2: Number(start[1]),
		e1: Number(end[0]),
		e2: Number(end[1]),
		ps: Number(start[2] / 1000 * map.height() + pad),
		pe: Number(end[2] / 1000 * map.height() + pad),
		x: Number(x),
		y: Number(y)
	}
	let distance = {
		longitude: pos.e1 - pos.s1,
		latitude: pos.e2 - pos.s2,
		x: draw.width(),
		y: pos.pe - pos.ps,
		hypotenuse: () => Math.sqrt(Math.pow(distance.longitude, 2) + Math.pow(distance.latitude, 2)),
		radius: () => Math.atan(distance.latitude / distance.longitude),
		radiusOpposite: () => Math.atan(distance.longitude / distance.latitude),
		pixel: () => Math.sqrt(Math.pow(draw.width(), 2) + Math.pow(distance.y, 2)),
		radiusPixel: () => Math.atan(distance.y / distance.x),
	}
	let diff = {
		longitude: pos.x - pos.s1,
		latitude: pos.y - pos.s2,
		hypotenuse: () => Math.sqrt(Math.pow(diff.longitude, 2) + Math.pow(diff.latitude, 2)),
		radius: () => Math.atan(diff.latitude / diff.longitude),
		radiusOpposite: () => Math.atan(diff.longitude / diff.latitude),
		radiusDiff: () => (invertX ? -(distance.radiusOpposite() - diff.radiusOpposite() - (invertY ? Math.sign(diff.radius()) * -90 : 0)) : distance.radius() - diff.radius()) + distance.radiusPixel()
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
	}
}

const drawPoint = item => {
	let id = item.i || item.id;
	let name = item.nm || idGps[id]
	let divItem = draw.find(`#${id}`)
	let pos = {
		x: Number(item.d ? item.d.pos.x : item.pos.x),
		y: Number(item.d ? item.d.pos.y : item.pos.y)
	}
	let point = coordToPixel(pos.x, pos.y)
	let outRange = !(0 < point.x < draw.width()) || !(0 < point.y < draw.height()) //|| pos.x < Math.min(pos.s1, pos.e1) - 0.1 || pos.x > Math.max(pos.s1, pos.e1) + 0.1 || pos.y < Math.min(pos.s2, pos.e2) - 0.1 || pos.y > Math.max(pos.s2, pos.e2) + 0.1
	let defaultCss = {position: 'absolute', left: `${point.x}px`, top: `${point.y}px`, transform: "translate(-50%, -50%)", width: "42px", "z-index": 1}

	if (divItem.length) {
		if (outRange)
			divItem.remove()
		else {
			let beforeLeftPos = Number(divItem.css("left").replace("px", ""))
			if (beforeLeftPos > point.x)
				defaultCss.transform += " scaleX(-1)"
			else if (beforeLeftPos == point.x)
				defaultCss.transform = divItem.css("transform")
			
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

		for (const item of units.items) {
			idGps[item.id] = item.nm
			drawPoint(item)
		}

		consumeGPS({ callback_gps, debug });
	});
}

window.initGPS = initGPS