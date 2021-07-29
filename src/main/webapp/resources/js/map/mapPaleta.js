var colorWell;
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
}