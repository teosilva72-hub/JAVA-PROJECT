const connectSPEED = async (request, debug) => {
	return await sendMsgStomp(request, 'SpeedRequest', debug)
}

const refresh_speed = response => {
    let speed = $(`#speed${response.Id}`);
	let plate = speed.find(".speed-speedy .speed-plate");
	let registry = response.Registry;
	let limit = response.Limit;

	if (registry > response.Tolerance)
		plate
			.addClass("speed-alert")
			.removeClass("speed-warn")
	else if (registry > limit)
		plate
			.addClass("speed-warn")
			.removeClass("speed-alert")
	else
		plate
			.removeClass("speed-warn")
			.removeClass("speed-alert")

    speed.find(".speed-limit .speed-number").text(limit);
    speed.find(".speed-speedy .speed-number").text(registry);
}

const callback_speed = response => {
	if (response.body)
    	response = JSON.parse(response.body);
    
    refresh_speed(response);
}

const consumeSPEED = async ({ callback_speed = callback_speed, debug = false } = {}) => {
	var client = await getStomp();

	var on_connect = function() {
		client.subscribe(`/exchange/speed_notify/speed_notify`, callback_speed)
	};

	var on_error =  function() {
	    console.log('error');
	};

	if (!debug)
		client.debug = null
	client.reconnect_delay = 1000;
	client.connect(rabbitmq.user, rabbitmq.pass, on_connect, on_error, '/');
}

const initSPEED = async (object = { callback_speed = callback_speed } = {}) => {
    $(async function () {
        // let last_status = await connectSPEED("LastStatus");

        // for (let status of last_status)
		// 	callback_speed(status)

		consumeSPEED(object);
	});
}

window.initSPEED = initSPEED