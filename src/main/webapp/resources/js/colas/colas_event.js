var notify = $("#notifyPopUp")
var buffer_colas = {}

const connectCOLAS = async (request, debug) => {
	return await sendMsgStomp(request, 'ColasRequest', debug)
}

const callback_notify = response => {
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
                    time = 3
                    break
                
                case 2:
                    time = 1
                    break

                case 3:
                    time = 4
                    break
                
                default:
                    return
            }
            break
    }

    let msg = `CDF ${response.Device_Id} - Lane ${response.Lane} | Time: ${time} minute`
    alertToast(msg)
}

const consumeCOLAS = async debug => {
	var client = await getStomp();

	var on_connect = function() {
		client.subscribe(`/exchange/colas_notify/colas_notify`, callback_notify)
	};

	var on_error =  function() {
	    console.log('error');
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
