
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

	//for (const r of response)
	//	changeStates(r)
				
	consumeMonitor();
}

initMonitor();

