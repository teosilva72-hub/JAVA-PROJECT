localStorage.removeItem("RingTone");
localStorage.removeItem("RingBackTone");
let count = 0
let debug = false;

$(async () => {
	localStorage.setItem("user", $("body").attr("user"))
    
	let calls = await connectSOS("GetAllActiveCalls", debug);

	for (const c of calls)
        ringCall(c)

    consumeSOS({ callback_calls: ringCall })
    
    eventGetReaction()
    
    window.onstorage = eventGetReaction;
})

const ringCall = async response => {
    let phone = localStorage.getItem("ctxPhone");

    switch (response.CallStateID) {
        case 1: // Atendido
		case 3: // Finalizado
		case 5: // Deligado
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

const eventGetReaction = () => {
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
        try { RingTone.play() } catch (e) {}
    else
        try { RingTone.pause() } catch (e) {}
        
    if (ringbacktone)
        try { RingBackTone.play() } catch (e) {}
    else
        try { RingBackTone.pause() } catch (e) {}
}