
$(function () {
  $('.sideMenuToggler').on('click', function () {
    $('.wrapper').toggleClass('active');
    $('.overlay').addClass('active');
  });

  var adjustSidebar = function () {
    $(".menu-mode > form").slimScroll({ height: window.innerHeight - 20 })
    $(".equips-div").slimScroll({ height: window.innerHeight - 20 })   
      .css('height' , window.innerHeight - 200)
       
  };

  adjustSidebar();
  $(window).resize(function () {
    adjustSidebar();
  });

  
  //UPDATE NOTIFICATIONS NUMBER ON LOAD
  $("#notification").load('/template/dashboard-rov-notifications.xhtml' , () => {
  
          notificationBadge();
  });

  toast = new bootstrap.Toast(document.getElementById('liveToast'), { delay: 3000 })

  $("#sipClient.calls-client .sipStatus").click(showCallbox)
  
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

  /*var badge = Number( document.getElementById('badge-notif').innerHTML);
    
  if(badge > 0)
  document.getElementById('badge-notif').style.display = 'block';

  else document.getElementById('badge-notif').style.display = 'none';*/
  
}

var callBoxStatus = false;

const showCallbox = () => {
  let client = $("#sipClient.calls-client")
  let callStatus = $('#txtCallStatus').html()
  let action;

  if(client.hasClass('showing') || client.hasClass('show')) {
    action = 'hiding'
    callBoxStatus = false
    if (!callStatus)
      showStatesCallbox('close')
    else if (callStatus === 'Rejected')
      ctxSip.setCallSessionStatus('');
  } else {
    action = 'showing'
    callBoxStatus = true
    showStatesCallbox('open')
  }

	client.addClass(action).css('bottom', action == 'showing' ? 40 : '').removeClass('hide').removeClass('show')
	setTimeout(() => {
	    client.removeClass(action).addClass(action == 'showing' ? "show" : 'hide')
	}, 2000)
}

const showStatesCallbox = action => {
  let client = $("#sipClient .sipStatus")
  let action2;

  if(action == 'open') {
    action2 = 'opening'
    client.removeClass('close')
  } else if ((client.hasClass('opening') && action == 'close') || callBoxStatus)
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
      

   