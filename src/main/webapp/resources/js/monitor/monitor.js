
function HasNotification(item){

 return '<a value="#" class="dropdown-item text-font dropdown-notif-style '+item.ViewedBgColor+'" id="n'+item.AlarmType+item.EquipType.toLowerCase()+item.EquipId+'" >'+
		'<div style="font-size: 10px; margin-left: 0px;">' +
        item.DateTime +
        '</div>' +
        '<div style="font-size: 12px;">' +
        item.EquipName+' - '+item.Description +
        '</div>' +
        '</a>';	
} 

function WithoutNotification(item){

	return '<a value="#" class="dropdown-item text-font dropdown-notif-style '+item.ViewedBgColor+'" id="n'+item.AlarmType+item.EquipType+item.EquipId+'" >'+
		   '<div style="font-size: 12px;">' +
		   item.Description +
		   '</div>' +
		   '</a>';	
   }  	
   

const changeNotificationStatus = response => {

	let total = response.Total;
    let battery = response.Battery;
	let connection = response.Connection;
	let door = response.Door;
	let energy = response.Energy;
	let events = response.Events;
	let presence = response.Presence;
	let temperature = response.Temperature;
 
	// FORMAT UNDEFINED VALUE
	const replacer = (key, value) =>
	  typeof value === 'undefined' ? 0 : value;
 
	 total = JSON.stringify(total, replacer);
	 battery = JSON.stringify(battery, replacer);
	 connection = JSON.stringify(connection, replacer);
	 door = JSON.stringify(door, replacer);
	 energy = JSON.stringify(energy, replacer);
	 events = JSON.stringify(events, replacer);
	 presence = JSON.stringify(presence, replacer);
	 temperature = JSON.stringify(temperature, replacer);
 
	 if (total > 0){
		
		$('#badge-notif').css('display','block')

         if(battery > 1)
		    $('#btn-act-battery').css('display','block')

		    else $('#btn-act-battery').css('display','none')

		 if(connection > 1)
			$('#btn-act-connection').css('display','block')

			else $('#btn-act-connection').css('display','none')

		 if(door > 1)
			$('#btn-act-door').css('display','block')

			else $('#btn-act-door').css('display','none')

		 if(energy > 1)
			$('#btn-act-energy').css('display','block')

			else $('#btn-act-energy').css('display','none')

		 if(events > 1)
			$('#btn-act-event').css('display','block')

			else $('#btn-act-event').css('display','none')

		 if(presence > 1)
			$('#btn-act-presence').css('display','block')

			else $('#btn-act-presence').css('display','none')

		 if(temperature > 1)
			$('#btn-act-temperature').css('display','block')

			else $('#btn-act-temperature').css('display','none')
	 
	 }else {
		 
		$('#badge-notif').css('display','none')
		$('#btn-act-battery').css('display','none')
		$('#btn-act-connection').css('display','none')
		$('#btn-act-door').css('display','none')
		$('#btn-act-energy').css('display','none')
		$('#btn-act-events').css('display','none')
		$('#btn-act-presence').css('display','none')
		$('#btn-act-temperature').css('display','none')

	}
 
	// BADGE VALUES	
	 $('#badge-notif').html(`${total}`)
	 $('#btn-act-battery').html(`${battery}`)
     $('#btn-act-connection').html(`${connection}`)
     $('#btn-act-door').html(`${door}`)
     $('#btn-act-energy').html(`${energy}`)
     $('#btn-act-events').html(`${events}`)
     $('#btn-act-presence').html(`${presence}`)
     $('#btn-act-temperature').html(`${temperature}`)
				 
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
				  
	 $('#notification-battery').empty()
	 $('#notification-connection').empty()
	 $('#notification-door').empty()
	 $('#notification-energy').empty()
	 $('#notification-event').empty()
	 $('#notification-presence').empty()
	 $('#notification-temperature').empty()
	 $('#notification-list').empty()	

	 // Counters
	 let battery = 0;
	 let connection = 0;
	 let door = 0;
	 let energy = 0;
	 let event = 0;
	 let presence = 0;
	 let temperature = 0;

	 let last_battery_id, last_connection_id, last_door_id, last_energy_id, last_presence_id, last_temperature_id;
 		
	for (const item of response){	
		
	   if(item.AlarmType == 2) {
			 	
		    battery++;
            
			if(battery == 1){

			   $('#notification-list').append(HasNotification(item));
			   $('#notification-battery').append(HasNotification(item)); 
					   
			   last_battery_id = "#n"+item.AlarmType+item.EquipType.toLowerCase()+item.EquipId;
		
		    } else if(battery == 2) {
				
			 removeItem("#notification-list "+last_battery_id);	
			
			 $('#notification-battery').append(HasNotification(item));
			
			 $('#div-battery').css('display','block');

		    }else {
					
				removeItem("#notification-list #n"+last_battery_id);	

			   $('#notification-battery').append(HasNotification(item));			   
			   $('#div-battery').css('display','block')

			}
	    }
	   
		else if(item.AlarmType == 4){		 
				     
		     door++;

			 if(door == 1){
				
				$('#notification-list').append(HasNotification(item));
				$('#notification-door').append(HasNotification(item));
			
				last_door_id = "#n"+item.AlarmType+item.EquipType.toLowerCase()+item.EquipId;
			 
			 } else if(door == 2) {
				
				removeItem("#notification-list "+last_door_id);	
							
				$('#notification-door').append(HasNotification(item));
				
				$('#div-door').css('display','block');	

			 }else {
 
				$('#notification-door').append(HasNotification(item));

				$('#div-door').css('display','block')	
			 }
		}

		else if(item.AlarmType == 6){		 
		
             energy++;

			 if(energy == 1){

				$('#notification-list').append(HasNotification(item));
				$('#notification-energy').append(HasNotification(item));
							
				last_energy_id = "#n"+item.AlarmType+item.EquipType.toLowerCase()+item.EquipId;
 		
			} else if(energy == 2) {

				removeItem("#notification-list "+last_door_id);	
					
				$('#notification-energy').append(HasNotification(item));
				
				$('#div-energy').css('display','block');	

			 }else {
 
				$('#notification-energy').append(HasNotification(item));

				$('#div-energy').css('display','block')	
			 }			 
		}

		else if(item.AlarmType == 8){		 
		 
             connection++;

			 if(connection == 1){

				$('#notification-list').append(HasNotification(item));
				$('#notification-connection').append(HasNotification(item));
											
				last_connection_id = "#n"+item.AlarmType+item.EquipType.toLowerCase()+item.EquipId;
 		
			} else if(connection == 2) {

				removeItem("#notification-list "+last_connection_id);
				
				$('#notification-connection').append(HasNotification(item));
				
				$('#div-connection').css('display','block');	

			 }else {
 
				$('#notification-connection').append(HasNotification(item));

				$('#div-connection').css('display','block')
			 }
		  }

		else if(item.AlarmType == 10){		 
		
			presence++;

			if(presence == 1){

				$('#notification-list').append(HasNotification(item));
				$('#notification-presence').append(HasNotification(item));		

				last_presence_id = "#n"+item.AlarmType+item.EquipType.toLowerCase()+item.EquipId;
				 		
			} else if(presence == 2) {

				removeItem("#notification-list "+last_presence_id);	

				$('#notification-presence').append(HasNotification(item));
				
				$('#div-presence').css('display','block');	

			 }else {
 
				$('#notification-presence').append(HasNotification(item));

				$('#div-presence').css('display','block')	
			 }
		}

		else if(item.AlarmType == 12){		 
		 
            temperature++;

			if(temperature == 1){

				$('#notification-list').append(HasNotification(item));
				$('#notification-temperature').append(HasNotification(item));	
					 		
				last_temperature_id = "#n"+item.AlarmType+item.EquipType.toLowerCase()+item.EquipId;

			} else if(temperature == 2) {
			
				removeItem("#notification-list "+last_temperature_id);

				$('#notification-temperature').append(HasNotification(item));
				
				$('#div-temperatura').css('display','block');	

			 }else {
 
				$('#notification-temperature').append(HasNotification(item));

				$('#div-temperature').css('display','block')	
			 }
		}

		//SE NÃO HOUVER NOTIFICAÇÔES ENTRAR AQUI
		if(battery == 0 && connection == 0 && door == 0 && energy == 0 && presence == 0 && temperature == 0 )		
		     $('#notification-list').append(WithoutNotification(item));	

        // CASO HAJA AOS MENOS UMA NOTIFICAÇÂO FAZ AQUI
	    if(battery == 1 || connection == 1 || door == 1 || energy == 1 || presence == 1 || temperature == 1)	
		     $('#div-single').css('display','block'); 

		else $('#div-single').css('display','none'); // CASO CONTRÁRIO ENTRA NESSA CONDIÇÃO
        
     } // END OF FOR	 

} // END METHOD

 // REMOVE ITEM
  function removeItem(id){
	 $(id).remove();
  }
	   
 // ITEM 
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
 
 