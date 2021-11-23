const connectGPS = async (request, debug) => {
	return await sendMsgStomp(request, 'GpsRequest', debug)
}

const set_gps_localization = gps => {
	console.log(gps)


}

const callback_gps_default = response => {
	if (response.body)
    	response = JSON.parse(response.body);
	
	set_gps_localization(response)
}

const consumeGPS = async ({ callback_gps = callback_gps_default, debug = false } = {}) => {
	var client = await getStomp();

	var on_connect = function() {
		client.subscribe(`/exchange/gps_notify/gps_notify`, callback_gps)
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