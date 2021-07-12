let notify = $("#notifyDAI")
let DAIpopup = $("#DAIpopup")

const bodyDai = " \
	<div class=\"position-fixed bottom-0 left-0 p-3\" style=\"z-index: 5; left: 0; bottom: 0;\"> \
		<div class=\"toast hide\" role=\"alert\" aria-live=\"assertive\" aria-atomic=\"true\" data-autohide=\"false\"> \
			<div class=\"toast-header\"> \
				<strong class=\"mr-auto\"></strong> \
				<small> \
				<h:form prependId=\"false\" id=\"sendDate\"> \
					<h:inputText styleClass=\"input-style-none\" name=\"filterDate\" id=\"filterDate\" p:disabled=\"disabled\" /> \
					<h:commandButton style=\"display: none;\" id=\"filterDateButton\"> \
					<f:ajax execute=\"@this\" render=\":modalPopUp\" listener=\"#{daiBean.getSpecificFile()}\" /> \
					</h:commandButton> \
				</h:form> \
				</small> \
				<button type=\"button\" class=\"ml-2 mb-1 close\" data-dismiss=\"toast\" aria-label=\"Close\"> \
				<span aria-hidden=\"true\">&times;</span> \
				</button> \
			</div> \
			<div class=\"toast-body\"> \
				<img style=\"cursor: pointer;\" src=\"data:image/jpg;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=\" class=\"rounded mr-2 w-100\" alt=\"\" /> \
			</div> \
		</div> \
	</div> \
"

const connectDAI = async (request, debug) => {
	return await sendMsgStomp(request, 'DAI_Request', debug)
}

const move_dai = function(e) {
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
				left: Math.round(elmnt.css("left").replace("px", "") - pos1),
				top: Math.round(elmnt.css("top").replace("px", "") - pos2)
			}

			// Set the element's new position:
			elmnt.css({
				top: pos.top,
				left: pos.left
			})
		})
	
	function closeDragElement() {
		// Stop moving when mouse button is released:
		$(document)
			.off("mouseup")
			.off("mousemove")
	}
}

const callback_alert = response => {
    response = JSON.parse(response.body);
	let date = response.dateTime.slice(0, 19)

	let elmt = $(bodyDai)
	
    elmt.find("strong").text(response.channelName);
    elmt.find("#filterDate").val(date);
	
	notify.append(elmt)
}

const callback_image = response => {
	let toast = notify.find(".toast:last")

    toast.find("img").attr("src", `data:image/jpg;base64, ${response.body}`)

	toast.find(".toast-header").on("mousedown", move_dai)
    toast.toast('show')
}

const consumeDAI = async debug => {
	var client = await getStomp();

	var on_connect = function() {
		client.subscribe(`/exchange/dai_alert/dai_alert`, callback_alert)
		client.subscribe(`/exchange/dai_image/dai_image`, callback_image)
	};

	var on_error =  function() {
	    console.log('error');
	};

	if (!debug)
		client.debug = null
	client.reconnect_delay = 1000;
	client.connect(rabbitmq.user, rabbitmq.pass, on_connect, on_error, '/');
}

const alert_click = () => {
	notify.find("#filterDateButton").click();
	DAIpopup.modal("show")
}

const initDAI = async debug => {
    $(function () {
		consumeDAI(debug);

		notify.find("img").click(alert_click)
	});
}

initDAI();