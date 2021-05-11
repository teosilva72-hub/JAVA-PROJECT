var on_error = function() {
	console.log('error');
};

function sleep(time) {
	return new Promise(r => setTimeout(r, time))
}

const connectSOS = async function (request) {
	var ws = new WebSocket('ws://192.168.0.51:15674/ws');
	var client = Stomp.over(ws);
	var response = null;
	
	client.onreceive = function(m) {
		response = JSON.parse(m.body);
		client.disconnect()
	};
	
	var on_connect = function() {
		client.send("/amq/queue/ClientRequest", {"reply-to": "/temp-queue/ClientRequest_51", durable:false},`"${request}"`)
	};
	
	client.connect('tracevia', 'trcv1234', on_connect, on_error, '/');
	
	while (true) {
		if (response != null && response != undefined)
			return response
		await sleep(800);
		
	};
	
};

//GetAllEquipmentStates