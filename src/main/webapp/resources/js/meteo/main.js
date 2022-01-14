const lastUpgrade = {}

const connectMeteo = async (request, debug) => {
	return await sendMsgStomp(request, 'MeteoRequest', debug, 'request')
}

const treat_values = response => {
    let type = response.type
    let id = response.id
    let status = response.status
    let meteo = $(`#meteo${id} .status`)
    let sidebar = $(`#status${type}${id}`)

    delete response.type
    delete response.id
    delete response.status
    lastUpgrade[type + id] = response

    if (status) {
        meteo.addClass('on')
        sidebar.css('color', '#00FF00')
    } else {
        meteo.removeClass('on')
        sidebar.css('color', '#ff000d')
    }
}

const set_values = () => {
    let panel = $('.panel-card').removeClass('on')
    for (const [k, v] of Object.entries(lastUpgrade)) {
        let equip = panel.filter(`[for=${k}]`);
        for (const [k2, v2] of Object.entries(v)) {
            let card = equip.filter(`.${k2}`).addClass('on')
            if (card.hasClass('deg'))
                card.find('.card-ponteiro').css('transform', `rotate(${v2}deg)`)
            else if (card.hasClass('wr'))  {
                let tip = card.find('[data-toggle=tooltipMETEO]')
                let d = tip.attr(`d${Math.round(v2 / 45) % 8}`).split('/');
                let v = tip.attr(`name`);
                card.find('.card-value').val(d[1])
                tip.attr('data-original-title', `${v}: ${d[0]}`)
            } else
                card.find('.card-value').val(v2)
        }
    }
}

const callback_meteo_default = response => {
	if (response.body)
    	response = JSON.parse(response.body);
    
    for (let r of response)
        treat_values(r)
    
    set_values()
}

const consumeMeteo = async ({ callback_meteo = callback_meteo_default, debug = false } = {}) => {
	var client = await getStomp();

	var on_connect = function() {
		client.subscribe(`/exchange/meteo_meteorology/meteo_meteorology`, callback_meteo)
	};

	var on_error = async function() {
	    console.log('error');
		await sleep(1000);

		consumeMeteo({callback_meteo, debug})
	};

	if (!debug)
		client.debug = null
	client.reconnect_delay = 1000;
	client.connect(rabbitmq.user, rabbitmq.pass, on_connect, on_error, '/');
}

const initMeteo = async ({ callback_meteo = callback_meteo_default, debug = false } = {}) => {
    $(async function () {
		let meteo = await connectMeteo('Sync')

		for (const item of meteo)
			treat_values(item)

        set_values()
		consumeMeteo({ callback_meteo, debug });
	});

    $('[data-toggle=tooltipMETEO]').each(function () {
        $(this).tooltip({
            offset: function (a, b) {
                let e = $(b)
                let p = e.closest('.panel-meteo')
                let s = e.closest('.panel-meteo-card').attr('scale') * scale
                let d
                if (a.arrow) {
                    d = -(e.offset().left - p.offset().left)
                } else {
                    d = (window.screen.availWidth - e.offset().left - e.width() * s)
                      - (window.screen.availWidth - p.offset().left - p.width() * s)
                }
    
                a.popper.left += d
    
                return a
            }
        })
    })
}

$(() => {
    $('.select-field.meteo select').change(function (e) {
        let target = e.target
        $('.panel-card.custom').attr('for', target.value)
        $('.stationName.meteo').text(target.options[target.selectedIndex].innerText)
        set_values()
    }).trigger('change')
})

window.initMeteo = initMeteo