function ready() {
    var collabseStatus = document.getElementsByClassName("multi-collapse");

    [...collabseStatus].forEach(collabse => {
        collabse.addEventListener('hidden.bs.collapse', function () {
            collabse.previousElementSibling.style = `background-color: ${getComputedStyle(collabse).backgroundColor}; color: white;`
            collabse.addEventListener('change', colorCollapse);
        });

        collabse.addEventListener('show.bs.collapse', function () {
            collabse.previousElementSibling.style = "";
            collabse.removeEventListener('change', colorCollapse);
        });
    });

    function colorCollapse(e) {
        e.currentTarget.previousElementSibling.style = `background-color: ${getComputedStyle(e.currentTarget).backgroundColor}; color: white;`
    }

    function update() {
        var xhttp = new XMLHttpRequest();
        xhttp.onload = function () {
            let json = JSON.parse(xhttp.responseText);
            for (const key in json) {
                const value = json[key];
                let change = new Event("change");

                if (key.startsWith('OUT')) {
                    let val;
                    let relay = document.getElementById(key);
                    let status = document.getElementById(`${key}status`);
                    let animate = document.getElementById(`${key}animate`);
                    if (relay) {
                        if (value == "1") {
                            val = "ON";
                            relay.classList.add('ok');
                            relay.dispatchEvent(change);
                            if (status) {
                                status.classList.add('ok');
                            }
                            if (animate) {
                                animate.classList.add('active');
                            }
                        } else {
                            val = "OFF";
                            relay.classList.remove('ok');
                            relay.dispatchEvent(change);
                            if (status) {
                                status.classList.remove('ok');
                            }
                            if (animate) {
                                animate.classList.remove('active');
                            }
                        };
                        relay.querySelector('span').innerText = val;
                        if (status) {
                            status.querySelector('span').innerText = val;
                        }
                    }
                } else if (key.startsWith("AIN")) {
                    let AIN = document.getElementById(key);
                    if (AIN) {
                        AIN.innerText = value;
                    }
                }
            }
        };
        xhttp.open('GET', location.href + 'response.json', true);
        xhttp.send();
    }

    let sliderTemp = document.querySelectorAll('[id^=temp]');
    sliderTemp.forEach(slide => {
        slide.addEventListener('change', function () {
            if (sliderTemp[0].value - 2 < sliderTemp[1].value) {
                sliderTemp[1].value = sliderTemp[0].value - 2
                sliderTemp[1].previousElementSibling.querySelector('span').innerHTML = sliderTemp[1].value;
            }
        })
        slide.previousElementSibling.querySelector('span').innerHTML = slide.value;
        slide.oninput = function () {
            slide.previousElementSibling.querySelector('span').innerHTML = this.value;
        }
    });

    let config = document.getElementById("saveConfig");
    config.addEventListener('click', function () {
        let form = document.forms.namedItem('config');
        let xhttp = new XMLHttpRequest();
        xhttp.open('GET', `${location.href}config.cfg?max=${form.tempMax.value}&min=${form.tempMin.value}`, true);
        xhttp.send();
    })

    update()
    setInterval(() => {
        update()
    }, 2000)
}

if (document.readyState != 'loading') {
    ready()
    document.removeEventListener('DOMContentLoaded', ready);
} else {
    document.addEventListener('DOMContentLoaded', ready);
}