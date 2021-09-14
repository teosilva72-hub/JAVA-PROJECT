const connectSPEED = async (request, debug) => {
	return await sendMsgStomp(request, 'SpeedRequest', debug)
}

const callback_speed = response => {
    response = JSON.parse(response.body);
    
    let speed = $(`#speed${response.Id}`);
    speed.find(".speed-limit .speed-number").text(response.Limit);
    speed.find(".speed-speedy .speed-number").text(response.Registry);
}

const consumeSPEED = async debug => {
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

const initSPEED = async debug => {
    $(function () {
		consumeSPEED(debug);
	});
}

initSPEED();