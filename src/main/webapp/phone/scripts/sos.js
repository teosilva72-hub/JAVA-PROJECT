const PING = 10000

let on_error =  function() {
    console.log('error');
};

const changeStates = response => {
	let name = response.EquipmentName
	let status = response.EquipmentStateID
	let equip = $(`#${name.toLowerCase()}`)
	let sidebar = $(`#status${name.toLowerCase()}`)
	
	switch (status) {
		case 1:
			status = 'active'
			sidebar.css("color", "#00FF00")
			break

		case 3:
			status = 'using'
			sidebar.css("color", "blue")
			break
		
		case 4:
			status = 'alarm'
			break
			
		case 5:
			status = 'warning'
			sidebar.css("color", "orange")
			break
			
		default:
			status = ''
			sidebar.css("color", "#FF0000")
			break
	}

	// if (status == 'alarm')
	//   equip.addClass('call-box-alarm')
	// else
	//	 equip.removeClass('call-box-alarm')

	equip.find(`span.equip-status`).attr("class", `equip-status ${status}`.trim())
}

const getEquipFromID = async id => {
	let allEquip = await connectSOS("GetAllEquipments")
	for (const r of allEquip)
		if (id == r.ID) {
			return r
		}
}

const callsIncoming = async response => {
	let equip = sosEquip[response.EquipmentID]
	let status = response.CallStateID


	let elmt = $(`#${equip.toLowerCase()}`)
	
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

const signaling = response => {
	if (response.AlarmTypeID > 3)
		return

	let equip = sosEquip[response.EquipmentID];
	let active = true;
	let alarm = $(`#${equip.toLowerCase()} #Alarm${equip}`)
	let html = alarm.find(`div`)
	let alarms = html.children()
	let door = alarms.filter(`span[door=${response.Value}]`)

	if (response.EndDate)
		active = false;
	
	if (door.length == 0 && active)
		html.append(`<span class="col-12" door="${response.Value}">Door ${response.Value}</span>`)
	else if (door.length == 1 && !active)
		door.remove();

	if (html.children().length > 0)
		alarm.addClass('d-flex').removeClass('d-none')
	else
		alarm.addClass('d-none').removeClass('d-flex')
}

const callback_states_default = message => {
	let response = JSON.parse(message.body)
	changeStates(response)
}

const callback_alarms_default = message => {
	let response = JSON.parse(message.body)
	signaling(response)
}

const callback_calls_default = message => {
	let response = JSON.parse(message.body)
	callsIncoming(response)
}

const consume = async ({ callback_calls = callback_calls_default, callback_alarms = callback_alarms_default, callback_states = callback_states_default, debug = false } = {}) => {
	var client = await getStomp();

	var on_connect = function() {
		if (typeof callback_states == "function")
			client.subscribe(`/exchange/sos_states/sos_states`, callback_states)
		if (typeof callback_alarms == "function")
			client.subscribe(`/exchange/sos_alarms/sos_alarms`, callback_alarms)
		if (typeof callback_calls == "function")
			client.subscribe(`/exchange/sos_calls/sos_calls`, callback_calls)
	};

	var on_error =  function() {
	    console.log('error');
	};

	client.heartbeat.outgoing = PING

	if (!debug)
		client.debug = null
	client.reconnect_delay = 1000;
	client.connect(rabbitmq.user, rabbitmq.pass, on_connect, on_error, '/');
}

const connectSOS = async function(request, debug) {	
	return await sendMsgStomp(request, 'ClientRequest', debug)
	
}

const initSOS = async debug => {
	let response = await connectSOS('GetAllEquipmentStates', debug)
	let alarms = await connectSOS('GetAllActiveAlarms', debug)
	window.sosEquip = {};

	for (const r of response) {
		changeStates(r)
		sosEquip[r.EquipmentID] = r.EquipmentName
	}

	for (const a of alarms)
		signaling(a)

	consume({debug: debug})
}

window.initSOS = initSOS;

// GetAllEquipmentStates
// GetAllAlarmTypes