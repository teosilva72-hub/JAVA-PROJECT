
function HasNotification(item){

 return '<a value="#" class="dropdown-item text-font dropdown-notif-style '+item.ViewedBgColor+'" id="n'+item.AlarmType+item.EquipType+item.EquipId+'" >'+
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


function backNotification(bgColor, alarmType, equipType, equipId, dateTime, equipName, description){

 return '<a value="#" class="dropdown-item text-font dropdown-notif-style '+bgColor+'" id="n'+alarmType+equipType.toLowerCase()+equipId+'" >'+
		'<div style="font-size: 10px; margin-left: 0px;">' +
        dateTime +
        '</div>' +
        '<div style="font-size: 12px;">' +
        equipName+' - '+description +
        '</div>' +
        '</a>';	
} 

function backWithoutNotification(bgColor, alarmType, equipType, equipId, description){

	return '<a value="#" class="dropdown-item text-font dropdown-notif-style '+bgColor+'" id="n'+alarmType+equipType+equipId+'" >'+
		   '<div style="font-size: 12px;">' +
		   description +
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

         if(battery > 0)
		    $('#btn-act-battery').css('display','block')

		    else $('#btn-act-battery').css('display','none')

		 if(connection > 0)
			$('#btn-act-connection').css('display','block')

			else $('#btn-act-connection').css('display','none')

		 if(door > 0)
			$('#btn-act-door').css('display','block')

			else $('#btn-act-door').css('display','none')

		 if(energy > 0)
			$('#btn-act-energy').css('display','block')

			else $('#btn-act-energy').css('display','none')

		 /*if(events > 0)
			$('#btn-act-event').css('display','block')

			else $('#btn-act-event').css('display','none')*/

		 if(presence > 0)
			$('#btn-act-presence').css('display','block')

			else $('#btn-act-presence').css('display','none')

		 if(temperature > 0)
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
	if(['SOS', 'PMV', 'SPEED I', 'SPEED R', 'MTO', 'SV'].includes(response.EquipType))
		return

	//Tipo equipamento
	let type = response.EquipType

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
 		
	for (const item of response){	
		
	   if(item.AlarmType == 2) {

		   $('#notification-battery').append(HasNotification(item));
		   $('#div-battery').css('display','block')
			 	
		      battery++;

        } else if(item.AlarmType == 4){		 
				     
		   $('#notification-door').append(HasNotification(item));
		   $('#div-door').css('display','block')
		  
		      door++;
		 
		} else if(item.AlarmType == 6){		 
		
			$('#notification-energy').append(HasNotification(item));
			$('#div-energy').css('display','block')	
			
              energy++;

		}

		else if(item.AlarmType == 8){	
			
			$('#notification-connection').append(HasNotification(item));
			$('#div-connection').css('display','block')
		 
             connection++;

		  }

		else if(item.AlarmType == 10){		 
		
			$('#notification-presence').append(HasNotification(item));
			$('#div-presence').css('display','block')	

			   presence++;
 
		 }	

		else if(item.AlarmType == 12){		
			
			$('#notification-temperature').append(HasNotification(item));
			$('#div-temperature').css('display','block')	
		 
            temperature++;
 			 
		}

		//SE NÃO HOUVER NOTIFICAÇÔES ENTRAR AQUI
		if(battery == 0 && connection == 0 && door == 0 && energy == 0 && presence == 0 && temperature == 0 ){		
		     $('#notification-list').append(WithoutNotification(item));	
			 $('#div-single').css('display','block')
		}
        
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
		 if (typeof callback1 == "function")	
			 client.subscribe(`/exchange/monitor/monitor`, callback1)
			 
		 if (typeof callback2 == "function")
			 client.subscribe(`/exchange/counter/counter`, callback2)
 
		 if (typeof callback3 == "function")
			 client.subscribe(`/exchange/notification/notification`, callback3)
				 
	 };
 
   	   // GUIGO
		  let on_error = async function() {
			console.log('error');
			await sleep(1000);
		
			consumeMonitor({callback1, callback2, callback3, debug})
		};
		 
 
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
 
 