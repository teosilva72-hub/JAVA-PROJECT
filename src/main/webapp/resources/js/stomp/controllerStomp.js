const sleep = time => {
    return new Promise(r => setTimeout(r, time))
}

const getStomp = async () => {
	while (!window.rabbitmq) {	
		await sleep(100);
	}

	var ws = new WebSocket(`wss://${rabbitmq.address}:${rabbitmq.port}/ws`);
	return Stomp.over(ws);
}

const sendMsgStomp = async function(request, to, debug) {	
	let client = await getStomp();
	let response = null;
	
	client.onreceive = function(m) {
		response = JSON.parse(m.body);
		client.disconnect()
	}
	
	var on_connect = function() {
		client.send(`/amq/queue/${to}`, {"reply-to": `/temp-queue/${to}`, durable: false}, `"${request}"`)
	};

	let on_error = async function() {
		console.log('error');
	};

	if (!debug)
		client.debug = null
	client.connect(rabbitmq.user, rabbitmq.pass, on_connect, on_error, '/');

	while (true) {	
		if (response != null && response != undefined)	
			return response
		await sleep(800);
	}
	
}

window.sleep = sleep;
window.getStomp = getStomp;
window.sendMsgStomp = sendMsgStomp;