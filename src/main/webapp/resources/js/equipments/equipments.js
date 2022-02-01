const speed_status = response => {
    if (response.body)
        response = JSON.parse(response.body);
        
    console.log(response.Id) 
    console.log(response.Status)   
        
}

const monitor_status = response => {
    if (response.body)
        response = JSON.parse(response.body);
      
    console.log(response)   
        
}

const sos_status = response => {
    if (response.body)
        response = JSON.parse(response.body);
      
    console.log(response)   
        
}

const dms_status = response => {
    if (response.body)
        response = JSON.parse(response.body);
      
    console.log(response.id)
    console.log(response.statusId)
    change_status(response.id, response.statusId, "DMS")    
        
}

const meteo_status = response => {
    if (response.body)
        response = JSON.parse(response.body);
      
    console.log(response.id)
    console.log(response.status)
    change_status(response.id, response.status, "METEO")    
        
}

const change_status = (id, status, type) => {
	 if (response.body)
        response = JSON.parse(response.body);
	
} 

consumeSPEED({ callback_status: speed_status })
consumeMonitor({callback1: monitor_status, callback2: null, callback3: null})
consumeSOS({callback_calls = null, callback_alarms = null, callback_states = sos_status})
consumeDMS({callback = dms_status})
consumeMeteo({callback_meteo = meteo_status})