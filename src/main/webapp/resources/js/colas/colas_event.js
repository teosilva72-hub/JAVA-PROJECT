var notify = $("#notifyPopUp")
var buffer_colas = {}

const connectCOLAS = async (request, debug) => {
	return await sendMsgStomp(request, 'ColasRequest', debug)
}

const callback_notify_default = response => {
    response = JSON.parse(response.body);
	let time

    switch (response.CMD) {
        case 3:
            switch (response.Lane_State) {
                case 1:
                    return
                
                case 2:
                    time = 2
                    break

                case 3:
                    time = 3
                    break
            }
            break
        
        case 4:
            switch (response.Local) {
                case 1:
                    time = 2
                    break
                
                case 2:
                    time = 1
                    break

                case 3:
                    time = 3
                    break
                
                default:
                    return
            }
            break
    }

    let msg = `CDF ${response.Device_Id} - Lane ${response.Lane} | Time: ${time} minute`
    alertToast(msg)
}

const consumeCOLAS = async ({ callback_notify = callback_notify_default, debug = false } = {}) => {
	var client = await getStomp();

	var on_connect = function() {
        if (typeof callback_notify == "function")
		    client.subscribe(`/exchange/colas_notify/colas_notify`, callback_notify)
	};

	var on_error = async function() {
	    console.log('error');
		await sleep(1000);

		consumeCOLAS({callback_notify, debug})
	};

	if (!debug)
		client.debug = null
	client.reconnect_delay = 1000;
	client.connect(rabbitmq.user, rabbitmq.pass, on_connect, on_error, '/');
}

const initCOLAS = async debug => {
    $(function () {
		consumeCOLAS(debug);
	});
}

initCOLAS();
