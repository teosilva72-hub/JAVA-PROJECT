const speed_animation = response => {
    if (response.body)
        response = JSON.parse(response.body);

    let timestamp = Number.parseInt(response.Date.replace("s", "")) * 1000;
    let speed = $(`#speed${response.Id}`).next();
    let speed_number = speed.find("[class^=speed-history-]");

    if (timestamp == 0) return;

    let color;
    if (response.Registry <= response.Limit)
        color = '#0f0'
    else if (response.Registry <= response.Tolerance)
        color = '#ff0'
    else
        color = '#f00'

    speed_number.addClass("start");

    let date = new Date(timestamp)
    let day = date.getDate();
    let month = date.getMonth() + 1;
    let year = date.getFullYear();
    let hour = date.getHours();
    let minute = date.getMinutes();
    let second = date.getSeconds();
    let date_format =  `${ month <= 9 ? '0' + month : month }/${ day <= 9 ? '0' + day : day }/${ year } ${ hour <= 9 ? '0' + hour : hour }:${ minute }:${ second }`

    setTimeout(() => {
        let list = speed_number.get().reverse()
        let last = $(list.pop())
        for (let s of list)
            $(s).html($(s).prev().html())

        last.addClass("spawn").find("svg").css("color", color).next().html(`${response.Registry} km/h<span>${date_format}</span>`)

        setTimeout(() => {
            last.removeClass("spawn");
        }, 500);
        
        speed_number.removeClass("start");
    }, 990);

    refresh_speed(response)
}

const resizeEquipSpeed = () => {
    let content = $('.contentPage')
    let size = Math.max(content.height() / 47.3, 10)

    content.css('font-size', size < 16 ? size : '')
}

$(() => {
    $(window).resize(resizeEquipSpeed).trigger('resize')
})

initSPEED({ callback_speed: speed_animation })