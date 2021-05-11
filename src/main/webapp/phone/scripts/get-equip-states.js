const PING = 10000
const ADDRESS = "192.168.0.51"
const USER = "tracevia"
const PASS = "trcv1234"
const PORT = 15674

var on_error =  function() {
    console.log('error');
};

function sleep(time) {
    return new Promise(r => setTimeout(r, time))
}

const getStomp = () => {
	var ws = new WebSocket(`ws://${ADDRESS}:${PORT}/ws`);
	return Stomp.over(ws);
}

const consumeStates = (callback, exchange) => {
	var client = getStomp();
	
	var on_connect = function() {
		client.subscribe(`/exchange/sos_${exchange}/sos_${exchange}`, callback)
	};
	
	var on_error =  function() {
	    console.log('error');
		setTimeout(() => {
			consumeStates()
		}, 500)
	};
		
	client.heartbeat.outgoing = PING

	client.connect(USER, PASS, on_connect, on_error, '/');
}

const connectSOS = async function(request) {	
	var client = getStomp();
	var response = null;
	
	client.onreceive = function(m) {
		response = JSON.parse(m.body);
		client.disconnect()
<<<<<<< HEAD
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
=======
	}
	
	var on_connect = function() {
		client.send("/amq/queue/ClientRequest", {"reply-to": "/temp-queue/ClientRequest", durable: false}, `"${request}"`)
	};

	client.connect(USER, PASS, on_connect, on_error, '/');

	while (true) {	
		if (response != null && response != undefined)	
			return response
		await sleep(800);
	}
	
}

const initSOS = async () => {
	let response = await connectSOS('GetAllEquipmentStates')
	let name;
	let status;

	let changeStates = response => {
		name = response.EquipmentName
		status = response.EquipmentStateID
		
		switch (status) {
			case 1:
				status = 'active'
				break
			
			case 4:
				status = 'alert'
				break
				
			case 5:
				status = 'warning'
				break
				
			default:
				status = ''
				break
		}

		$(`#${name.toLowerCase()} span.equip-status`).attr("class", `equip-status ${status}`.trim())
	}

	if (Array.isArray(response)) {
		for (const r of response) {
			changeStates(r)
		}
	}

	consumeStates((message) => {
		response = JSON.parse(message.body)
		changeStates(response)
	}, "states")
}

$(function() {
	initSOS()
})

// GetAllEquipmentStates
// GetAllAlarmTypes
>>>>>>> 29be0bcd315cf04bc6b4558b140536e0617febb2
