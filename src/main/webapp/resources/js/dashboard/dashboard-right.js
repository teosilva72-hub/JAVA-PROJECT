$(async function () {
  $('.sideMenuToggler').on('click', function () {
    $('.wrapper').toggleClass('active');
    $('.overlay').addClass('active');
  });

  var adjustSidebar = function () {
    $(".menu-mode > form").slimScroll({ height: window.innerHeight - 20 })
    $(".equips-div").slimScroll({ height: window.innerHeight - 20 })
      .css('height', window.innerHeight - 200)

  };

  adjustSidebar();
  $(window).resize(function () {
    adjustSidebar();
  });

  toast = new bootstrap.Toast(document.getElementById('liveToast'), { delay: 7000 })

  while (typeof rabbitmq == "undefined" || typeof asterisk == "undefined") {
    await new Promise(r => setTimeout(r, 100))
  }

  let url_rabbitmq = `${rabbitmq.address}:${rabbitmq.port}/ws`
  let url_asterisk = `${asterisk.address}:${asterisk.port}/ws`

  TestCert(url_rabbitmq);
  TestCert(url_asterisk, "sip");

  $(".btnRunCommandSOS").click(btnSOSCommand);
});

var alertToast = msg => {
  $('#msgToastNotification').text(msg);
  toast.show();
}

$('#dismiss, .overlay').on('click', function () {
  // hide sidebar
  $('.wrapper').removeClass('active');
  // hide overlay
  $('.overlay').removeClass('active');
});
// OVERLAY SIDE BAR END

var url = '/phone/phone.html',
  features = 'menubar=no,location=no,resizable=no,scrollbars=no,status=no,addressbar=no,width=320,height=480';

$('#navCall').on('click', function (event) {
  event.preventDefault();
  // This is set when the phone is open and removed on close
  if (!localStorage.getItem('ctxPhone')) {
    window.open(url, 'ctxPhone', features);
    return false;
  } else {
    window.alert('Telefone já esta aberto.');
  }
});

const showStatesCallbox = action => {
  let client = $("#sipClient .sipStatus")
  let action2;

  if (action == 'open') {
    action2 = 'opening'
    client.removeClass('closed')
  } else if (client.hasClass('opening') && action == 'close')
    return
  else {
    action2 = 'closing'
    action = "closed"
    client.removeClass('open')
  }

  client.addClass(action2)
  setTimeout(() => {
    client.removeClass(action2).addClass(action)
  }, 2000)
}

const getCred = serviceName => {
  let credForm = document.forms.getCred;

  credForm.serviceName.value = serviceName;
  credForm.start.click();
}

const credEvent = data => {
  let status = data.status;

  switch (status) {
    case "begin":
    case "complete":
      break;

    case "success":
      let form = document.forms.getCred;
      let cred = JSON.parse(form.credentials.value);
      window[cred.name] = cred
      form.credentials.value = '';
      form.serviceName.value = '';

      localStorage.setItem(cred.name, JSON.stringify(cred))

      break;
  }
}

getCred('rabbitmq'); // Rabbit init
getCred('asterisk');

const TestCert = async (uri, init) => {
  try {
    let request = new WebSocket(`wss://${uri}`, init);
    while (request.readyState == 0) {
      await new Promise(r => setTimeout(r, 100))
    }
    if (request.readyState > 1) {
      window.open(`https://${uri}`);
    }
  }
  catch {
    window.open(`https://${uri}`);
  }
}

var url      = '/phone/phone.html',
    features = 'menubar=off,resizable=off,location=off,resizable=off,scrollbars=off,status=off,addressbar=off,width=320,height=480';

$('.sipStatus').on('click', function (event) {
  event.preventDefault();
  // This is set when the phone is open and removed on close
  // if (!localStorage.getItem('ctxPhone')) {
    window.open(url, 'ctxPhone', features);
    return false;
  // } else {
  //   window.alert('Phone already open.');
  // }
})

const btnSOSCommand = function (e) {
  let target = e.currentTarget;
  let sip = target.value;
  let command = target.getAttribute("command");
  
  connectSOS(`${command};${sip}`);
}

const developerMode = () => {
  const modeOn = () => {
    $("[mode=developer_mode]").show();
  }
  const modeOff = () => {
    $("[mode=developer_mode]").hide();
  }

  try {
    if (JSON.parse(localStorage.getItem("developer_mode")))
      modeOn()
    else
      modeOff()
  } catch {}
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

/*******************************************************************************/

function sidenavAction() {
  document.getElementById("sidenav2").style.display = "none";
  document.getElementById("sidenav3").style.display = "none";
  document.getElementById("sidenav").style.display = "block";
}

function sidenavAction2() {
  document.getElementById("sidenav3").style.display = "none";
  document.getElementById("sidenav").style.display = "none";
  document.getElementById("sidenav2").style.display = "block";
}

function sidenavAction3() {
  document.getElementById("sidenav").style.display = "none";
  document.getElementById("sidenav2").style.display = "none";
  document.getElementById("sidenav3").style.display = "block";
}

function clicked(id) {
  var element = document.getElementById(id);
  if (element.style.display === "none") {
    element.style.display = "block";
  } else {
    element.style.display = "none";
  }
}

function toggleBtnEquips() {
  $(this).find(".plusMinus").toggleClass('fa-plus fa-minus');
};


/*  function reloadNotifications(){
      var container = document.getElementById("testess");
       container.innerHTML = " ";
                  
     //this line is to watch the result in console , you can remove it later	
     alert("Refreshed"); 
  }*/

//Scroll stucked on Top  ==> FOR SCROLL
/* function setTopo(){
    $(window).scrollTop(0);
}
 //Call setTopo()
$(window).bind('scroll', setTopo);*/
// Voice Menu Open
$(function () {

  $("[id$=holdingcall]").click(function (e) {
    e.preventDefault();
    e.stopPropagation();
    $("[id$=callback]").show();
    $("[id$=holdingcall]").hide();
  });

  $("[id$=callback]").click(function (e) {
    e.preventDefault();
    e.stopPropagation();
    $("[id$=holdingcall]").show();
    $("[id$=callback]").hide();
  });

  $("#navCall").on('click', function (e) {
    e.preventDefault()
    $('#toastvoice').toast('show');
  });

  $("#navListCall").click(function (e) {
    e.preventDefault()
    $('#list-toast').toast('show');
  });
})
// Voice Menu Open End

// Visibility Equipments
$('[toggle]').click(function () {
  $('.equipments > [id^=' + $(this).attr('toggle').toLowerCase() + ']').toggle()
})
      // Visibility Equipments End

