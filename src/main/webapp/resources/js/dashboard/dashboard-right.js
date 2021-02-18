// OVERLAY SIDE BAR
$(function () {
  $('.sideMenuToggler').on('click', function () {
    $('.wrapper').toggleClass('active');
    $('.overlay').addClass('active');
  });

  var adjustSidebar = function () {
    $(".menu-mode > form").slimScroll({ height: window.innerHeight - 150 })
    $(".content-module").slimScroll({ height: window.innerHeight - 150 })
  };

  adjustSidebar();
  $(window).resize(function () {
    adjustSidebar();
  });

  $(".loading").hide()
});

$('#dismiss, .overlay').on('click', function () {
  // hide sidebar
  $('.wrapper').removeClass('active');
  // hide overlay
  $('.overlay').removeClass('active');
});
// OVERLAY SIDE BAR END


//////////////////////SIP VOICE/CALL

//User Agent Configuration
var socket = new JsSIP.WebSocketInterface('wss://localhost:8081');
var configuration = {
  sockets: [socket],
  uri: 'sip:shesh@192.168.0.19',
  password: 'shesh'
};
//User Agent Configuration end

//User Agent Instance
var coolPhone = new JsSIP.UA(configuration);
//User Agent Instance end

//WebSocket connection events
coolPhone.on('connected', function (e) {/* Your code here */ });
coolPhone.on('disconnected', function (e) {/* Your code here */ });
//WebSocket connection events end

//New incoming or outgoing call event
coolPhone.on('newRTCSession', function (e) {/* Your code here */ });
//New incoming or outgoing call event end

//New incoming or outgoing IM message event
coolPhone.on('NewMessage', function (e) {/* Your code here */ });
//New incoming or outgoing IM message event end

//SIP registration events
coolPhone.on('registered', function (e) {/* Your code here */ });
coolPhone.on('unregistered', function (e) {/* Your code here */ });
coolPhone.on('registrationFailed', function (e) {/* Your code here */ });
//SIP registration events end

//Starting the User Agent
//See JsSIP.UA.start() method definition.
coolPhone.start();
//Starting the User Agent end



//Accept a Call Button
var startButton = document.getElementById('acptcall');
startButton.addEventListener("click", function () {
  coolPhone.start();
  alert("Call Started.")
}, false);
//Accept a Call Button End

//Ending a Call Button
var endButton = document.getElementById('misscall');
endButton.addEventListener("click", function () {
  coolPhone.hangup();
  alert("Call Ended.")
}, false);
//Ending a Call Button End

//Placing a call on hold
var holdButton = document.getElementById('holdcall');
holdButton.addEventListener("click", function () {
  coolPhone.hold();
  alert("Call on Hold.")
}, false);

var unholdButton = document.getElementById('holdcallstop');
unholdButton.addEventListener("click", function () {
  coolPhone.unhold();
  alert("Call Came Back.")
}, false);
//Placing a call on hold End

//Answering a Call
coolPhone.on('ringing', function () {
  coolPhone.answer()
});
//Answering a Call End

//////////////////////SIP VOICE/CALL END


//SIP BOOK-LIST
	//function color() {
		//var color1 = parseInt(document.form.cor1.value) || 0;
		//var color2 = parseInt(document.form.cor2.value) || 0;
		//var comp = parseInt(document.form.comparar.value) || 0;
		//var cor3 = cor1 + cor2;
		//document.form.cor3.value = cor3;
		//document.form.cor3.className = cor3 < comp ? 'red' : 'green';
	//}
//SIP BOOK-LIST END
