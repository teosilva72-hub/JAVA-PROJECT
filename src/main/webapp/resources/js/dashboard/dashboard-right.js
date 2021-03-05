// OVERLAY SIDE BAR
$(function () {
  $('.sideMenuToggler').on('click', function () {
    $('.wrapper').toggleClass('active');
    $('.overlay').addClass('active');
  });

  var adjustSidebar = function () {
    $(".menu-mode > form").slimScroll({ height: window.innerHeight - 150 })
    $(".equips-div").slimScroll({ height: window.innerHeight - 150 })
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

var url      = '/phone/phone.html',
    features = 'menubar=no,location=no,resizable=no,scrollbars=no,status=no,addressbar=no,width=320,height=480';

    $('#navCall').on('click', function(event) {
        event.preventDefault();
        // This is set when the phone is open and removed on close
        if (!localStorage.getItem('ctxPhone')) {
            window.open(url, 'ctxPhone', features);
            return false;
        } else {
            window.alert('Telefone já esta aberto.');
        }
    });

//NOTIFICATIONS BADGE
function notificationBadge(){

  var badge = Number( $('#badge-notif span').text() );

  alert(badge);

  if(badge > 0)
  document.getElementById('badge-notif').style.display = 'none';

  else document.getElementById('badge-notif').style.display = 'block';

}
//NOTIFICATIONS  BADGE

//Accept a Call Button
//var startButton = document.querySelector('.acptcall');
//startButton.addEventListener("click", function () {
//  coolPhone.start();
//  alert("Call Started.")
//}, false);
//Accept a Call Button End

//Ending a Call Button
//var endButton = document.querySelector('.dismisscall');
//endButton.addEventListener("click", function () {
//  coolPhone.hangup();
//  alert("Call Ended.")
//}, false);
//Ending a Call Button End

//Placing a call on hold
//var holdButton = document.querySelector('.holdcall');
//holdButton.addEventListener("click", function () {
//  coolPhone.hold();
//  alert("Call on Hold.")
//}, false);

//var unholdButton = document.querySelector('.holdcallstop');
//unholdButton.addEventListener("click", function () {
//  coolPhone.unhold();
//  alert("Call Came Back.")
//}, false);
//Placing a call on hold End

//Answering a Call
//coolPhone.on('ringing', function () {
//  coolPhone.answer()
//});

// //Accept a Call Button
// var startButton = document.querySelector('.acptcall');
// startButton.addEventListener("click", function () {
//   coolPhone.start();
//   alert("Call Started.")
// }, false);
// //Accept a Call Button End

// //Ending a Call Button
// var endButton = document.querySelector('.dismisscall');
// endButton.addEventListener("click", function () {
//   coolPhone.hangup();
//   alert("Call Ended.")
// }, false);
// //Ending a Call Button End

// //Placing a call on hold
// var holdButton = document.querySelector('.holdcall');
// holdButton.addEventListener("click", function () {
//   coolPhone.hold();
//   alert("Call on Hold.")
// }, false);
3
// var unholdButton = document.querySelector('.holdcallstop');
// unholdButton.addEventListener("click", function () {
//   coolPhone.unhold();
//   alert("Call Came Back.")
// }, false);
// //Placing a call on hold End

// //Answering a Call
// coolPhone.on('ringing', function () {
//   coolPhone.answer()
// });

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
