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

	if (response.Online) {
		speed.find('.speed-speedy, .speed-limit').addClass('active')
		$(`#statussos${response.Id}`).css('color', '#00FF00')
	}
}

const change_status = status => {
    let speed = $(`#speed${status.Id}`);
	let sidebar =$(`#statusspeed${status.Id}`);
	let plate;

	switch (status.Type) {
		case 3:
			plate = speed.find('.speed-speedy, .speed-limit')
			sidebar.css('color', status.Status ? '#00FF00' : '#FF0000'). attr({
				status1: status.Status,
				status2: status.Status,
			})
			break;
		case 1:
			plate = speed.find('.speed-limit')
			sidebar.css('color', sidebar.attr('status2') == `${status.Status}` ? status.Status ? '#00FF00' : '#FF0000' : '#FF7F00')
				.attr('status1', status.Status)
			break;
		case 2:
			plate = speed.find('.speed-speedy')
			sidebar.css('color', sidebar.attr('status1') == `${status.Status}` ? status.Status ? '#00FF00' : '#FF0000' : '#FF7F00')
				.attr('status2', status.Status)
			break;
		default:
			return;
	}

	if (status.Status)
		plate.addClass('active')
	else
		plate.removeClass('active')
}

const callback_speed_default = response => {
	if (response.body)
    	response = JSON.parse(response.body);
    
    refresh_speed(response);
}

const callback_status_default = response => {
	if (response.body)
    	response = JSON.parse(response.body);
    
		change_status(response);
}

const consumeSPEED = async ({ callback_speed = callback_speed_default, callback_status = callback_status_default, debug = false } = {}) => {
	var client = await getStomp();

	var on_connect = function() {
		client.subscribe(`/exchange/speed_notify/speed_notify`, callback_speed)
		client.subscribe(`/exchange/speed_status/speed_status`, callback_status)
	};

	var on_error = async function() {
	    console.log('error');
		await sleep(1000);

		consumeSPEED({callback_speed, debug})
	};

	if (!debug)
		client.debug = null
	client.reconnect_delay = 1000;
	client.connect(rabbitmq.user, rabbitmq.pass, on_connect, on_error, '/');
}

const initSPEED = async ({ callback_speed = callback_speed_default, debug = false } = {}) => {
    $(async function () {
		$('.speed-card [data-bs-toggle=tooltip]').tooltip()
        let last_status = await connectSPEED("LastStatus");

        for (let status of last_status)
			callback_speed(status)

		consumeSPEED({ callback_speed, debug });
	});
}

window.initSPEED = initSPEED