function color(){
	var body = document.querySelector("body");
	while(true){
		$( "#color-1" ).click(function() {
		$('body').addClass('color-1')
	});
	$( "#color-2" ).click(function() {
		$('body').addClass('color-2')
	});
	$( "#color-3" ).click(function() {
		$('body').addClass('color-3')
	});
	$( "#color-4" ).click(function() {
		$('body').addClass('color-4')
	});
	$( "#color-5" ).click(function() {
		$('body').addClass('color-5')
	});
	$( "#color-6" ).click(function() {
		$('body').addClass('color-6')
	});
	$( "#color-7" ).click(function() {
		$('body').addClass('color-7')
	});
	$( "#color-8" ).click(function() {
		$('body').addClass('color-8')
	});
	}
	
}
//methos type ccolor esta desativado
/*var colorWell;
	var defaultColor = "#ffffff";
$(document).ready(function () {
	
	startup()

});
function startup() {
  colorWell = document.querySelector("[id$=favcolor]");
  colorWell.value = defaultColor;
  colorWell.addEventListener("input", updateFirst, false);
  colorWell.addEventListener("change", updateAll, false);
  colorWell.select();
}
function updateFirst(event) {
  var p = document.querySelector("body");

  if (p) {
    p.style.background = event.target.value;
  }
}
function updateAll(event) {
  document.querySelector("body").forEach(function(p) {
    p.style.background = event.target.value;
  });
}*/