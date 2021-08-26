var notify = $("#notifyPopUp")
var buffer_colas = {}

const bodyColas = " \
	<div class=\"position-fixed p-3\" style=\"z-index: 5; left: 0; bottom: 0;\"> \
		<div class=\"toast hide\" role=\"alert\" aria-live=\"assertive\" aria-atomic=\"true\" data-bs-delay=\"15000\" data-delay=\"15000\"> \
			<div class=\"bg-primary text-dark toast-header\"> \
				<strong class=\"mr-auto me-auto\"></strong> \
				<span class=\"mr-auto me-auto lane\"></span> \
				<small></small> \
				<button type=\"button\" class=\"ml-2 mb-1 close btn-close\" data-dismiss=\"toast\" aria-label=\"Close\"></button> \
			</div> \
			<div class=\"toast-body\"> \
				<video class=\"rounded mr-2 w-100\" alt=\"\" /> \
			</div> \
		</div> \
	</div> \
"

const connectCOLAS = async (request, debug) => {
	return await sendMsgStomp(request, 'ColasRequest', debug)
}

const move_colas = function(e) {
	let elmnt = $(this).closest(".position-fixed")
	e.preventDefault();
	e.stopPropagation()
	// Get the mouse cursor position at startup:
	pos3 = e.clientX;
	pos4 = e.clientY;

	$(document)
		.on("mouseup", closeDragElement)

		.on("mousemove", function (e) {
			e.preventDefault();

			// Calculate the new cursor position:
			pos1 = pos3 - e.clientX;
			pos2 = pos4 - e.clientY;
			pos3 = e.clientX;
			pos4 = e.clientY;

			let pos = {
				left: Math.round(Number(elmnt.css("left").replace("px", "")) - pos1),
				bottom: Math.round(Number(elmnt.css("bottom").replace("px", "")) + pos2)
			}

			// Set the element's new position:
			elmnt.css({
				left: pos.left,
				bottom: pos.bottom
			})
		})
	
	function closeDragElement() {
		// Stop moving when mouse button is released:
		$(document)
			.off("mouseup")
			.off("mousemove")
	}
}

const callback_notify = response => {
    response = JSON.parse(response.body);
	let time,
        elmt
	
	elmt = $(`#colas-video-${response.Device_Id}`)
	if (!elmt.length) {
		elmt = $(bodyColas)
		notify.append(elmt)
		
    	elmt.attr("id", `colas-video-${response.Device_Id}`);
		
		
	}
		
    elmt.find("strong").text(`Colas ${response.Device_Id}`);
    elmt.find("span.lane").text(`lane ${response.Lane}`);

    switch (response.CMD) {
        case 3:
            switch (response.Lane_State) {
                case 1:
                    return
                
                case 2:
                    time = 2
                    break

                case 3:
                    time = 3
                    break
            }
            break
        
        case 4:
            switch (response.Local) {
                case 1:
                    time = 3
                    break
                
                case 2:
                    time = 1
                    break

                case 3:
                    time = 4
                    break
                
                default:
                    return
            }
            break
    }
    elmt.find("small").text(`${time} minute`);

    let toast = elmt.find(".toast")

	toast.find(".toast-header").on("mousedown", move_colas)
	toast.find(".close").click(() => { toast.remove() })
}

const callback_stream = response => {
	response = JSON.parse(response.body);
	let elmt = $(`#colas-video-${response.id}`)
	if (!elmt.length) { return }
	if (!buffer_colas[`colas${response.id}`]) {
		buffer_colas[`colas${response.id}`] = base64ToArrayBuffer(response.stream)
		setTimeout(() => {
			let buf = buffer_colas[`colas${response.id}`]
			buffer_colas[`colas${response.id}`] = undefined
			
			let blob = new Blob([buf], {type : 'video/MJPEG'})
			let video = elmt.find("video")[0]
			let toast = elmt.find(".toast")
			
			toast.toast('show')
			//video.crossOrigin = 'anonymous';
			video.src = URL.createObjectURL(blob)
			
			video.play()
		}, 15000)
	} else
		try {		
			buffer_colas[`colas${response.Device_Id}`].concat(base64ToArrayBuffer(response.stream))
		}
		catch {}
}

const base64ToArrayBuffer = base64 => {
    var binary_string = window.atob(base64);
    var len = binary_string.length;
    var bytes = new Uint8Array(len);
    for (var i = 0; i < len; i++) {
        bytes[i] = binary_string.charCodeAt(i);
    }
    return bytes.buffer;
}

const consumeCOLAS = async debug => {
	var client = await getStomp();

	var on_connect = function() {
		client.subscribe(`/exchange/colas_stream/colas_stream`, callback_stream)
	};

	var on_error =  function() {
	    console.log('error');
	};

	if (!debug)
		client.debug = null
	client.reconnect_delay = 1000;
	client.connect(rabbitmq.user, rabbitmq.pass, on_connect, on_error, '/');
}

const initCOLAS = async debug => {
    $(function () {
		consumeCOLAS(debug);
	});
}

initCOLAS();
