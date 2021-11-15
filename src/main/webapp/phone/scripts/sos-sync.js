localStorage.removeItem("RingTone");
localStorage.removeItem("RingBackTone");
let idInterval = 0
let count = 0
let debug = false;

$(async () => {
	localStorage.setItem("user", $("body").attr("user"))
    
	let calls = await connectSOS("GetAllActiveCalls", debug);

	for (const c of calls)
        ringCall(c)

    consumeSOS({ callback_calls: ringCall, callback_alarms : null, callback_states: null})

    const devices = await navigator.mediaDevices.enumerateDevices();
    const audioDevices = devices.filter(device => device.kind === 'audiooutput');

    for (media of audioDevices) {
        let audio = new Audio();
        audio.setSinkId(media.deviceId);
        audio.src = ringtone.src;
        audio.loop = true;

        secondaryAudio.push(audio);
    }
    
    window.onstorage = eventGetReaction;

    eventGetReaction()
})

const ringCall = async response => {
    let phone = localStorage.getItem("ctxPhone");

    switch (response.CallStateID) {
        case 1: // Atendido
		case 3: // Finalizado
		case 5: // Desligado
            count--
			break
		
		case 4: // Chamando
            count++
			break
			
		default:
			break
    }
    if (!phone)
		if (!count)
			try { localStorage.removeItem("RingTone") } catch (e) {}
		else
			try { localStorage.setItem("RingTone", "true") } catch (e) {}
}

const focusWindowPhone = () => {
    if (!idInterval)
        idInterval = setInterval(() => {
            if (phoneWindow)
                phoneWindow.focus()
        }, 1000);
}

const cancelFocusPhone = () => {
    clearInterval(idInterval);
    idInterval = 0;
}

const eventGetReaction = async () => {
    let last_status = $('#txtRegStatus').html()
    let last_reaction = $('#txtCallStatus').html()
    let status = localStorage.getItem("CallBoxStatus");
    let reaction = localStorage.getItem("CallBoxReaction");
    let ringtone = localStorage.getItem("RingTone");
    let ringbacktone = localStorage.getItem("RingBackTone");

    $("#txtRegStatus").html(status || ".");
    $('#txtCallStatus').html(reaction);

    if (reaction && !last_reaction)
        showStatesCallbox('open')
    else if (!reaction && last_reaction)
        showStatesCallbox('close')

    if (ringtone)
        try {
            focusWindowPhone();
            // RingTone.play()
            for (const ring of secondaryAudio)
                ring.play()
        } catch (e) {}
    else
        try {
            cancelFocusPhone();
            // RingTone.pause()
            for (const ring of secondaryAudio)
                ring.pause()
        } catch (e) {}
        
    if (ringbacktone)
        try { RingBackTone.play() } catch (e) {}
    else
        try { RingBackTone.pause() } catch (e) {}
}