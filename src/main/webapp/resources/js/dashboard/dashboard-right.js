$(document).ready(function() {
  $('.sideMenuToggler').on('click', function() {
    $('.wrapper').toggleClass('active');
    $('.overlay').addClass('active');
  });

  var adjustSidebar = function() {
    $(".menu-mode > form").slimScroll({height: window.innerHeight - 150})
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


