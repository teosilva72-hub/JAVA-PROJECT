
const changeNotificationStatus = response => {

   let counter = response.Count;

   // FORMAT UNDEFINED VALUE
   const replacer = (key, value) =>
     typeof value === 'undefined' ? 0 : value;

	counter = JSON.stringify(counter, replacer);

    if (counter > 0)
	   $('#badge-notif').css('display','block')
    
	else $('#badge-notif').css('display','none')

	 // BADGE VALUE	
    $('#badge-notif').html(`${counter}`)
				
}

const changeGenericStates = response => {

	//Tipo equipamento
	let type = response.EquipType

	//Separação em caso do SPEED
	if(type == "SPEED I" || type == "SPEED R")
        type = "SPEED"

	let name = `${type}${response.Id}`
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
			sidebar.css("color", "#FF0000")
			break
			
	}

	if(response.EquipType == "SPEED I")	     
	     equip.find(`.equip-status-indicator`).attr("class", `card-body p-1 speed-limit equip-status-indicator ${status}`.trim())
	
    else if(response.EquipType == "SPEED R") equip.find(`.equip-status`).attr("class", `card-body p-1 speed-speedy equip-status ${status}`.trim())
	 	    
	else if(response.EquipType != "PMV" && response.EquipType != "SOS")   
		      equip.find(`.equip-status`).attr("class", `equip-status ${status}`.trim())

}

  const listNotifications = response => {		
	 			
	$('#notifications-list').empty()
       
    for (const item of response)
		
     if(item.EquipType == "none")

		$('#notifications-list').append(	
					
			'<a value="#" class="dropdown-item text-font dropdown-notif-style '+item.ViewedBgColor+'" id="'+item.EquipType.toLowerCase()+item.EquipId+'" >'+
			
			'<div style="font-size: 12px;">' +
			   item.Description +
			'</div>' +
			
		'</a>'	
	
		)// END APPEND
	
		else
			
		$('#notifications-list').append(	
					
			'<a value="#" class="dropdown-item text-font dropdown-notif-style '+item.ViewedBgColor+'" id="n'+item.EquipType.toLowerCase()+item.EquipId+'" >'+
			
			'<div style="font-size: 10px; margin-left: 0px;">' +
			   item.DateTime +
		    '</div>' +

		    '<div style="font-size: 12px;">' +
		      item.EquipName+' - '+item.Description +
		    '</div>' +
			
		'</a>'	 
		)  
     }
   
  

const callback_states = response => {
	let res = JSON.parse(response.body);

	for(const r of res)
	  changeGenericStates(r);
} 

const callback_count = response => {
	let res = JSON.parse(response.body);
	changeNotificationStatus(res);
} 

const callback_notifications = response => {	
	let res = JSON.parse(response.body); 	
	   listNotifications(res)		 	
} 

const consumeMonitor = async ({ callback1 = callback_states, callback2 = callback_count, callback3 = callback_notifications, debug } = {}) => {
	var client = await getStomp();

	var on_connect = function() {
		if (typeof callback_states == "function")	
			client.subscribe(`/exchange/monitor/monitor`, callback1)
			
		if (typeof callback_count == "function")
			client.subscribe(`/exchange/counter/counter`, callback2)

		if (typeof callback_notifications == "function")
	        client.subscribe(`/exchange/notification/notification`, callback3)
				
	};

	var on_error = async function() {
	    console.log('error');
		await sleep(1000);

		consumeMonitor({callback1, callback2, callback3, debug})
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

		connectMonitor('getMonitorStatus').then(response => {
			for (const r of response)
				changeGenericStates(r)		
		})

		connectMonitor('getNotificationsCount').then(count => {
			changeNotificationStatus(count);	
		})

		connectMonitor('getNotificationsAlert').then(notifications => {			
				 listNotifications(notifications)		
		})
		
		consumeMonitor();
}

// SAVE ON WINDOW

window.consumeMonitor = consumeMonitor;
window.connectMonitor = connectMonitor;
window.initMonitor = initMonitor;

initMonitor();

