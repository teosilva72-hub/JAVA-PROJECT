let draw = $(".drawLines")

const connectGPS = async (request, debug) => {
	return await sendMsgStomp(request, 'GpsRequest', debug)
}

const drawPoint = item => {
	let divItem = draw.find(`#${item.i}`)
	let i = $("<div>")
	i.css({position: 'absolute', left: '100px', top: '100px', width: "50px", height: '50px', 'background-color': 'black'})
	if (divItem.length) {
		divItem.empty().append(i)
	} else {
		let n = $(`<div id="${item.i}">`).append(i)
		draw.append(n)
	}
	console.log(i)
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


		consumeGPS({ callback_gps, debug });
	});
}

window.initGPS = initGPS