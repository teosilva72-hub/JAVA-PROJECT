const changeGenericStates = response => {

	let name = `${response.EquipType}${response.Id}`
	let status = response.Status
	let equip = $(`#${name.toLowerCase()}`)
	let sidebar = $(`#status${name.toLowerCase()}`)
	
	switch (status) {
		case 1:

			status = 'active'
			sidebar.css("color", "#00FF00")
			break

		default:

			status = ''
			sidebar.css("color", "purple")
			break

			
	}
																	
	equip.find(`span.equip-status`).attr("class", `equip-status ${status}`.trim())

	console.log(status, equip, name)

}




const consumeMonitor = async ({ debug = false } = {}) => {
	var client = await getStomp();

	var on_connect = function() {	
			client.subscribe(`/exchange/monitor/monitor`, response => {
                let resp = JSON.parse(response.body)
                  console.log(resp)
            })
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

const connectMonitor = async function(request, debug) {	
	return await sendMsgStomp(request, 'MonitorStatus', debug)
	
}

const initMonitor = async debug => {
	let response = await connectMonitor('getMonitorStatus', true)
		
    console.log(response)

	for (const r of response)
		changeGenericStates(r)
				
	consumeMonitor();
}

// SAVE ON WINDOW
window.initMonitor = initMonitor;

//initMonitor();

