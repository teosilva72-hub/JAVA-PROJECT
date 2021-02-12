// OVERLAY SIDE BAR
$(document).ready(function() {
  $('.sideMenuToggler').on('click', function() {
    $('.wrapper').toggleClass('active');
    $('.overlay').addClass('active');
  });

  var adjustSidebar = function() {
    $(".menu-mode > form").slimScroll({height: window.innerHeight - 150})
    $(".content-module").slimScroll({height: window.innerHeight - 150})
  };

  adjustSidebar();
  $(window).resize(function() {
    adjustSidebar();
  });
});

$('#dismiss, .overlay').on('click', function () {
    // hide sidebar
    $('.wrapper').removeClass('active');
    // hide overlay
    $('.overlay').removeClass('active');
});
// OVERLAY SIDE BAR END

//SIP VOICE/CALL

//here you determine whether the call has video and audio

var options = {
      media: {
        local: {
          video: document.getElementById('localVideo')
        },
        remote: {
          video: document.getElementById('remoteVideo'),
          // This is necessary to do an audio/video call as opposed to just a video call
          audio: document.getElementById('remoteVideo')
        }
      },
      ua: {uri: 'test@example.com',
 			  authorizationUser: 'test',
    		password: 'password',}
    };


//Accept a Call Button
	var startButton = document.getElementById('acptcall');
	startButton.addEventListener("click", function() {
		simple.call();
		alert("Call Started.");
		}, false);
//Accept a Call Button End

//Ending a Call Button
	var endButton = document.getElementById('misscall');
	endButton.addEventListener("click", function () {
	    simple.hangup();
	    alert("Call Ended.");
		}, false);
//Ending a Call Button End

//Placing a call on hold
	var holdButton = document.getElementById('holdcall');
	holdButton.addEventListener("click", function () {
		simpleUser.hold();
		alert("Call on Hold.")
	}, false);
	
	var unholdButton = document.getElementById('holdcallstop');
	unholdButton.addEventListener("click", function() {
		simpleUser.unhold();
		alert("Call Came Back.")
	}, false);
//Placing a call on hold End

//Answering a Call

//Answering a Call End


//SIP VOICE/CALL END

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
