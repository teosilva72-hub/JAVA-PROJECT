const PING = 10000

let on_error =  function() {
    console.log('error');
};

const changeStates = response => {
	let name = `sos${response.EquipmentID}`
	let status = response.EquipmentStateID
	let equip = $(`#${name.toLowerCase()}`)
	let alarm = equip.find(`#Alarm${name}`);
	let alarms = alarm.children().find("div");
	let sidebar = $(`#status${name.toLowerCase()}`)
	
	switch (status) {
		case 1:
			if (alarms.length > 0)
				status = "alarm"
			else
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
			if (alarms.length > 0)
				status = "alarm"
			else
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
	let equip = `sos${response.EquipmentID}`
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

const telemtry = response => {
	let info = $(`#popsos${response.EquipmentID}`);

	info.find("#Volt").text(response.Volt || 0.00);
	info.find("#Amp").text(response.Ampere || 0.00);
	info.find("#VoltPanel").text(response.VoltPanel || 0.00);
	info.find("#AmpPanel").text(response.AmpPanel || 0.00);
}

const signaling = response => {
	if (response.name == "TELEMETRY")
		telemtry(response)
	else if (response.AlarmTypeID <= 3 && response.name == "DOOR")
		door(response)
}

const door = response => {
	let equip = `SOS${response.EquipmentID}`;
	let active = response.Value;
	let alarm = $(`#${equip.toLowerCase()} #Alarm${equip}`)
	let html = alarm.find(`div`)
	let alarms = html.children()
	let door = alarms.filter(`span[door="${response.AlarmTypeID}"]`)
	var doors = document.getElementById("setting_user")
	
	if (door.length == 0 && active){
		
			if(doors.innerText == "Configuraciones"){
				if(response.AlarmTypeID == 3)
					response.port = "superior"
				else response.port = "inferior"
				html.append(`<span class="col-12" style="white-space: nowrap;" door="${response.AlarmTypeID}">Puerta ${response.port}</span>`)
			}else if(doors.innerText == "Configuração"){
				if(response.AlarmTypeID == 3)
					response.port = "superior"
				else response.port = "inferior"
				html.append(`<span class="col-12" style="white-space: nowrap;" door="${response.AlarmTypeID}">Porta ${response.port}</span>`)
			}else{
				if(response.AlarmTypeID == 3)
					response.port = "top"
				else response.port = "bottom"
				html.append(`<span class="col-12" style="white-space: nowrap;" door="${response.AlarmTypeID}">Door ${response.port}</span>`)
			}
	}
		
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
	let calls = await connectSOS("GetAllActiveCalls", debug);

	for (const r of response)
		changeStates(r)

	for (const a of alarms)
		signaling(a)

	for (const c of calls)
		callsIncoming(c)

	consume({debug: debug})
}

window.initSOS = initSOS;

// GetAllEquipmentStates
// GetAllAlarmTypes