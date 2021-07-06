let notify = $("#notifyDAI")

const connectDAI = async (request, debug) => {
	return await sendMsgStomp(request, 'DAI_Request', debug)
}

const callback_alert = response => {
    response = JSON.parse(response);

    notify.find("strong").text(response.channelName)
    notify.find("smal").text(response.dateTime)

    console.log(response);
}

const callback_image = response => {
    notify.find("img").attr("src", `data:image/jpg;base64, ${response}`)

    notify.toast('show')
}

const consumeDAI = async debug => {
	var client = await getStomp();

	var on_connect = function() {
		client.subscribe(`/exchange/dai_alert/dai_alert`, callback_alert)
		client.subscribe(`/exchange/dai_alert/dai_image`, callback_image)
	};

	var on_error =  function() {
	    console.log('error');
	};

	if (!debug)
		client.debug = null
	client.reconnect_delay = 1000;
	client.connect(rabbitmq.user, rabbitmq.pass, on_connect, on_error, '/');
}

const initDAI = async () => {
    $(function () {
		consumeDAI();
	});
}