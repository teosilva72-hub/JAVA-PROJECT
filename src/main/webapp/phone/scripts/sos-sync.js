$(() => {
    window.onstorage = eventGetReaction;
	localStorage.setItem("user", $("body").attr("user"))
    
    $(window).trigger("storage")
})

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