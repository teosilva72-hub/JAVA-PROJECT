 var adjustLists = function () {
    $(".pgf-card").slimScroll({ height: window.innerHeight - 328 })
    $("#pgf-tires").slimScroll({ height: window.innerHeight - 40 })
      .css('height', window.innerHeight - 150)

  };

 adjustLists();
  $(window).resize(function () {
    adjustLists();
  });