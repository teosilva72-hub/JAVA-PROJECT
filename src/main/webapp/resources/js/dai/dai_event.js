let notify = $("#notifyDAI")
let DAIpopup = $("#DAIpopup")

const connectDAI = async (request, debug) => {
	return await sendMsgStomp(request, 'DAI_Request', debug)
}

const callback_alert = response => {
    response = JSON.parse(response.body);
	let date = response.dateTime.slice(0, 19)

    notify.find("strong").text(response.channelName);
    notify.find("#filterDate").val(date);
}

const callback_image = response => {
    notify.find("img").attr("src", `data:image/jpg;base64, ${response.body}`)

    notify.toast('show')
}

const consumeDAI = async debug => {
	var client = await getStomp();

	var on_connect = function() {
		client.subscribe(`/exchange/dai_alert/dai_alert`, callback_alert)
		client.subscribe(`/exchange/dai_image/dai_image`, callback_image)
	};

	var on_error =  function() {
	    console.log('error');
	};

	if (!debug)
		client.debug = null
	client.reconnect_delay = 1000;
	client.connect(rabbitmq.user, rabbitmq.pass, on_connect, on_error, '/');
}

const alert_click = () => {
	notify.find("#filterDateButton").click();
	DAIpopup.modal("show")
}

const initDAI = async debug => {
    $(function () {
		consumeDAI(debug);

		// notify.find("img").click(alert_click)
	});
}

initDAI();