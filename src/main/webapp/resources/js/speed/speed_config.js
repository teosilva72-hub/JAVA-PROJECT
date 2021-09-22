const saveSpeedBtn = e => {
    let mode,
        range,
        limit,
        tolerance,
        flash,
        strobe = 0;
    
    if (e.stealthMode.checked)
        mode = 1 | (4 << 4);
    else if (e.Strobe.checked && !e.Strobe.disabled)
        mode = 1 | (2 << 4);

    range = [e.displayRange.value  > 0 ? e.displayRange.value : 20, e.displayRange2.value > 0 ? e.displayRange2.value : 200]
    limit = e.displayLimit.value > 0 ? e.displayLimit.value : 100;
    tolerance = e.displayTolerated.value > limit ? e.displayTolerated.value : limit;

    if (e.displayFlashOption.checked && !e.stealthMode.checked)
        flash = e.displayFlash.value > 0 ? e.displayFlash.value : limit;
    if (e.displayStrobeOption.checked)
        strobe = e.displayStrobe.value > 0 ? e.displayStrobe.value : limit;

    connectSPEED(`SetConfig;${e.id.value};${mode};${range[0]};${range[1]};${limit};${tolerance};${flash};${strobe}`)

    $(e).closest(".col-speed").removeClass("invert")

    return false
}

const saveAllSpeed = e => {
    $("form.speed-form").each((idx, form) => {
        form.stealthMode.checked = e.stealthMode.checked
        form.Strobe.checked = e.Strobe.checked
        form.displayRange.value = e.displayRange.value
        form.displayRange2.value = e.displayRange2.value
        form.displayLimit.value = e.displayLimit.value
        form.displayTolerated.value = e.displayTolerated.value
        form.displayFlash.value = e.displayFlash.value
        form.displayStrobe.value = e.displayStrobe.value

        $([form.stealthMode, form.displayFlash, form.displayStrobe]).trigger("change")
    }).submit()

    return false
}

const getConfigSpeed = async () => {
    let config = await connectSPEED("GetAllConfig");
    let content = $(`.contentPage`);
    
    for (let c of config) {
        let form = content.find(`#col-speed${c.Id} .speed-form`)[0];
        let withOption = $([form.displayFlash, form.displayStrobe]);
        let options = $([form.displayFlashOption, form.displayStrobeOption]);
        let stealth = $(form.stealthMode);

        mode = c.mode >> 4;

        stealth.trigger("change", function() {
            let target = this.currentTarget;

            if (target.checked) {
                form.Strobe.disabled = true;
                form.Strobe.disabled = true;
                form.displayRange.disabled = true;
                form.displayRange2.disabled = true;
                form.displayFlash.disabled = true;
            } else {
                form.Strobe.disabled = false;
                form.Strobe.disabled = false;
                form.displayRange.disabled = false;
                form.displayRange2.disabled = false;
                form.displayFlash.disabled = false;
            }
        })

        options.trigger("change", function() {
            let target = this.currentTarget;
            let option = target.parentNode.nextSibling.nextSibling.firstChild;

            if (target.checked)
                option.disabled = false;
            else
                option.disabled = true;

            if (option.name == "displayStrobe") {
                let extra = target.parentNode.parentNode.nextSibling.firstChild

                extra.disabled = !target.checked;
            }
        });

        withOption.trigger("change", function() {
            let target = this.currentTarget;
            let option = target.parentNode.previousSibling.previousSibling.firstChild;

            if (target.value > 0)
                option.checked = true
            else
                option.checked = false

            $(option).trigger("change")
        })

        if (mode == 4) {
            form.stealthMode.checked = true;
        }
        else if (mode == 2)
            form.Strobe.checked = true;

        form.displayRange.value = c.Ranger[0];
        form.displayRange2.value = c.Ranger[1];
        form.displayLimit.value = c.Limit;
        form.displayTolerated.value = c.Tolerance;
        form.displayFlash.value = c.Flash;
        form.displayStrobe.value = c.Strobe;

        stealth.trigger("change");
        withOption.trigger("change");
    }
}

$(async () => {
    // await getConfigSpeed();

    $(".speed-config").click(function() {
        $(this).closest(".col-speed").toggleClass("invert")
    })
})