$(() => {
    window.onstorage = eventGetReaction;
    
    $(window).trigger("storage")
})

const eventGetReaction = () => {
    let last_status = $('#txtRegStatus').html()
    let last_reaction = $('#txtCallStatus').html()
    let status = localStorage.getItem("CallBoxStatus");
    let reaction = localStorage.getItem("CallBoxReaction");

    $("#txtRegStatus").html(status || ".");
    $('#txtCallStatus').html(reaction);

    if (reaction && !last_reaction)
        showStatesCallbox('open')
    else if (!reaction && last_reaction)
        showStatesCallbox('close')
}