function color(){
	$( "#color" ).click(function() {
		$('body').addClass('color')
		$('body').removeClass('color-1')
		$('body').removeClass('color-2')
		$('body').removeClass('color-3')
		$('body').removeClass('color-4')
	});
}
function color1(){
	$( "#color-1" ).click(function() {
		$('body').addClass('color-1')
		$('body').removeClass('color-2')
		$('body').removeClass('color-3')
		$('body').removeClass('color-4')
	});
}
function color2(){
	$( "#color-2" ).click(function() {
		$('body').addClass('color-2')
		$('body').removeClass('color-1')
		$('body').removeClass('color-3')
		$('body').removeClass('color-4')
	});
}
function color3(){
	$( "#color-3" ).click(function() {
		$('body').addClass('color-3')
		$('body').removeClass('color-4')
		$('body').removeClass('color-2')
		$('body').removeClass('color-1')
	});
}
function color4(){
	$( "#color-4" ).click(function() {
		$('body').addClass('color-4')
		$('body').removeClass('color-3')
		$('body').removeClass('color-2')
		$('body').removeClass('color-1')
	});
}
$(document).ready(function () {

});
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