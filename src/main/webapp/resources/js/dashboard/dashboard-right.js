// OVERLAY SIDE BAR
$(function() {
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

  $(".loading").hide()
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
var simple = new SIP.Web.Simple(options);

var endButton = document.getElementById('misscall');
endButton.addEventListener("click", function () {
    simple.hangup();
    alert("Call Ended.");
	}, false);

simple.on('ringing', function() {
	simple.answer();
});
	
//makes the call
simple.call('');

//SIP VOICE/CALL END
