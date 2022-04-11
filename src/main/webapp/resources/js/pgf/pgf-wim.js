let arrowCollapse = 1

/* Set the width of the sidebar to 250px and the left margin of the page content to 250px */
function clickCollapse() {
		arrowCollapse *= -1 
		var btnCollapse = document.getElementById("btncollapse");
		btnCollapse.style.transform = `scale(${arrowCollapse})`;
}
		