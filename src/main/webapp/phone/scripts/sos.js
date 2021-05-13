const PING = 10000
const ADDRESS = "192.168.0.51"
const USER = "tracevia"
const PASS = "trcv1234"
const PORT = 15674

let on_error =  function() {
    console.log('error');
};

const changeStates = response => {
	let name = response.EquipmentName
	let status = response.EquipmentStateID
	
	switch (status) {
		case 1:
			status = 'active'
			break

		case 3:
			status = 'using'
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

const getEquipFromID = async id => {
	let allEquip = await connectSOS("GetAllEquipments")
	for (const r of allEquip)
		if (id == r.ID) {
			return r
		}
}

const callsIncoming = async response => {
	let equip = await getEquipFromID(response.EquipmentID)
	let status = response.CallStateID


	let elmt = $(`#${equip.MasterName.toLowerCase()}`)
	
	switch (status) {
		case 1: // Atendido
		case 3: // Finalizado
		case 5: // Deligado
			elmt.removeClass(`call-box-action`)
			break
		
		case 4: // Chamando
			elmt.addClass(`call-box-action`)
			break
			
		default:
			status = ''
			break
	}
}

const callback_states_default = message => {
	let response = JSON.parse(message.body)
	changeStates(response)
}

const callback_alarms_default = message => {
	let response = JSON.parse(message.body)
}

const callback_calls_default = message => {
	let response = JSON.parse(message.body)
	callsIncoming(response)
}

function sleep(time) {
    return new Promise(r => setTimeout(r, time))
}

const getStomp = () => {
	var ws = new WebSocket(`ws://${ADDRESS}:${PORT}/ws`);
	return Stomp.over(ws);
}

const consumeStates = (callback, exchange) => {
	var client = getStomp();
	var count = 0
	
	var on_connect = function() {
		count = 0
		client.subscribe(`/exchange/sos_${exchange}/sos_${exchange}`, callback)
	};
	
	var on_error =  function() {
	    console.log('error');
		count++

		if (count > 3)
			setTimeout(() => {
				consumeStates((message) => {
					response = JSON.parse(message.body)
					changeStates(response)
				}, "states")
			}, 500)
		else
			consumeStates((message) => {
				response = JSON.parse(message.body)
				changeStates(response)
			}, "states")
	};
		
	client.heartbeat.outgoing = PING

	client.connect(USER, PASS, on_connect, on_error, '/');
}

const consume = ({ callback_calls = callback_calls_default, callback_alarms = callback_alarms_default, callback_states = callback_states_default } = {}) => {
	var client = getStomp();
	var count = 0

	var on_connect = function() {
		count = 0

		client.subscribe(`/exchange/sos_states/sos_states`, callback_states)
		client.subscribe(`/exchange/sos_alarms/sos_alarms`, callback_alarms)
		client.subscribe(`/exchange/sos_calls/sos_calls`, callback_calls)
	};

	var on_error =  function() {
	    console.log('error');
		count++

		if (count > 3)
			setTimeout(() => {
				consume()
			}, 1000)
		else
			consume()
	};

	client.heartbeat.outgoing = PING

	client.connect(USER, PASS, on_connect, on_error, '/');
}

const connectSOS = async function(request) {	
	let client = getStomp();
	let response = null;
	
	client.onreceive = function(m) {
		response = JSON.parse(m.body);
		client.disconnect()
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

	if (Array.isArray(response)) {
		for (const r of response) {
			changeStates(r)
		}
	} else {
		changeStates(response)
	}

	consume()
}

// GetAllEquipmentStates
// GetAllAlarmTypes