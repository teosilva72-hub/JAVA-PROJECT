const speed_animation = response => {
    if (response.body)
        response = JSON.parse(response.body);

    let speed = $(`#speed${response.Id}`).next();
    let speed_number = speed.find("[class^=number-]");

    speed_number.addClass("start");

    let date = new Date(Number.parseInt(response.Date.replace("s", "")) * 1000)
    let date_format = date.toLocaleString()

    setTimeout(() => {
        let list = speed_number.get().reverse()
        let last = $(list.pop())
        for (let s of list)
            s.innerText = $(s).parent().prev().find("span").text()

        last.text(`${response.Registry} km/h - ${date_format}`).addClass("spawn")

        setTimeout(() => {
            last.removeClass("spawn");
        }, 500);
        
        speed_number.removeClass("start");
    }, 1000);

    refresh_speed(response)
}

initSPEED({ callback_speed: speed_animation })