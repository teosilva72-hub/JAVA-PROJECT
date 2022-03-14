let phoneWindow = {};

/*(() => {
  if (location.protocol != "https:" && location.hostname != "localhost")
    location.href = location.href.replace(location.protocol, "https:")
})()*/

$(async function () {
  $('.sideMenuToggler').on('click', function () {
    $('.wrapper').toggleClass('active');
    $('.overlay').addClass('active');
  });

  $(document).on('contextmenu', function() {
    $(`.context-menu`).css('display', 'none')
    return false
	})
  $(document).on('click', function() {
    $(`.context-menu`).css('display', 'none')
	})

  var adjustSidebar = function () {
    $(".menu-mode > form").slimScroll({ height: window.innerHeight - $('.navbar.fixed-top').height() - $('.page-footer').height() - $('.company-info').height() - 20 })
    $(".equips-div").slimScroll({ height: window.innerHeight - 40 })
      .css('height', window.innerHeight - 200)

  };

  adjustSidebar();
  $(window).resize(function () {
    adjustSidebar();
  });

  toast = new bootstrap.Toast(document.getElementById('liveToast'), { delay: 7000 })

  $(".btnRunCommandSOS").click(btnSOSCommand);
  
  $(window).on("storage", function() {
    let report = localStorage.getItem('goReport');
    
	if(report){
	  localStorage.removeItem('goReport');
	  let r = report.split('/');
	  location.href = location.origin + `/occurrence/occurrences.xhtml?from=${r[0]}&id=${r[1]}`;
	}
  })
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

      TestCert(cred)

      break;
  }
}

getCred('rabbitmq'); // Rabbit init
getCred('asterisk');
getCred('digifort');

const TestCert = async (c) => {
  if (c.name == "null" || location.protocol == "http:")
    return
  let uri = ''
  try {
    if (c.ws != "http") {
      uri = `${c.address}:${c.port}/ws`
      let request = new WebSocket(`wss://${uri}`, c.ws != 'null' ? c.ws : undefined);
      while (request.readyState == 0) {
        await new Promise(r => setTimeout(r, 100))
      }
      if (request.readyState > 1) {
        window.open(`https://${uri}`);
      }
    } 
  } catch (e) {
    window.open(`https://${uri}`);
  }
}

var url      = '/phone/phone.html',
    features = 'menubar=off,resizable=off,location=off,resizable=off,scrollbars=off,status=off,addressbar=off,width=320,height=480';

$('.sipStatus').on('click', function (event) {
  event.preventDefault();
  // This is set when the phone is open and removed on close
  // if (!localStorage.getItem('ctxPhone')) {
    if (phoneWindow.opener)
      phoneWindow.focus()
    else
      phoneWindow = window.open(url, 'ctxPhone', features);
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

function clicked(id, action) {

  let element = document.getElementById(id); // div id element to show / hide

  let act = document.getElementById(action); // action button element
  
  if (element.style.display === "none") 
       element.style.display = "block";  

   else element.style.display = "none";
   
// ----------------------------------------

   // PLUS AND MINUS BUTTON SWITCH

  if(act.style.display !== "none")
      act.style.display = "block";
     
   else pls.style.display = "none";

  }

  // ----------------------------------------

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

// LOAD FUNCTION FOR NOTIFICATIONS

$(function () { 
  
    // GET VALUES FROM FORM NOTIFY
    let notify = document.forms.notifyForm;
       
       // READ VALUES
      for (messages of notify)
         window[messages.name] = messages.value

     // ARRAY WITH TITLE     
    let titles = new Array (batteryTitle, connectionTitle, doorTitle, energyTitle, eventTitle, presenceTitle, temperatureTitle);
     
     // ARRAY WITH LIST IDS
    let list = new Array ('notification-battery', 'notification-connection', 'notification-door', 'notification-energy', 'notification-event', 'notification-presence', 'notification-temperature');
    
    // ARRAY WITH BUTTON IDS
    let buttons = new Array ('btn-battery', 'btn-connection', 'btn-door', 'btn-energy', 'btn-event', 'btn-presence', 'btn-temperature');

    // DIV ITEMS TO SHOW / HIDE
    let divItems = new Array ('div-battery', 'div-connection', 'div-door', 'div-energy', 'div-event', 'div-presence', 'div-temperature');

    // ARRAY WITH PLUS IDS
    let actionButtons = new Array ('btn-act-battery', 'btn-act-connection', 'btn-act-door', 'btn-act-energy', 'btn-act-event', 'btn-act-presence', 'btn-act-temperature');
     
    // DINAMIC NOTIFICATION DIV
    for(let i = 0; i < titles.length; i++){    
       
      // GROUP
      let html = '<div id=\''+divItems[i]+'\' class="content-notification" style="display:none;"> ' +
   
      '<a id=\''+buttons[i]+'\' class="link-show-hide text-decoration-none" '+
        'onclick="clicked(\''+list[i]+'\', \''+actionButtons[i]+'\');"> ' +
            
        '<span id=\''+actionButtons[i]+'\' class="notification-badge">5</span> ' +
            
          '<label class="label-notification-title">'+titles[i]+'</label> ' +                 
          '</a>' +     

      '<div id='+list[i]+' style="display: none;"> </div> ' +                   
                   
      '</div>'
       
      // CREATE DIV COMPONENT
      let div = document.createElement('div');
      div.innerHTML = html; 
     
      // APPEND DIV
      document.getElementById('notif-dropdown').appendChild(div);
    
      $('.content-notification').on('click', function(e) {
        e.stopPropagation();

      });
    
      }

      // SINGLE
      let html2 = '<div id="div-single" class="content-notification" style="display:none;"> ' +                                
 
      '<div id="notification-list"></div> ' +                   
                   
      '</div>'

       // CREATE DIV COMPONENT
      let div = document.createElement('div');
        div.innerHTML = html2; 

       // APPEND DIV
       document.getElementById('notif-dropdown').appendChild(div);

       $('.content-notification').on('click', function(e) {
        e.stopPropagation();
      });   

  });

 
 // RIGHT SIDE PANEL

$(function(){
  $('.slider-arrow').click(function(){
    if($(this).hasClass('show')){
    $( ".slider-arrow, .panel" ).animate({
      left: "+=300"
      }, 700, function() {
        // Animation complete.
      });
      $(this).html('&laquo;').removeClass('show').addClass('hide');
    }
    else {      
    $( ".slider-arrow, .panel" ).animate({
      left: "-=300"
      }, 700, function() {
        // Animation complete.
      });
      $(this).html('&raquo;').removeClass('hide').addClass('show');    
    }
  });
});

 // RIGHT SIDE PANEL [END]
 
