import init, { PMV, PaginaType } from "/resources/pkg/project.js";

async function main() {
	await init();

	const equipInfo = $('.equip-info');

	const pmvResize = () => {
		equipInfo.css('transform', function() {
			let scale = Math.min(0.9 / (equipInfo.outerWidth() / $(this).closest('.message-block').outerWidth()), Number($(this).attr('scale')));
			return `translateX(-50%) scale(${scale})`;
		});
	}

	$(function () {
		$('#toogleMenu button[toggle]').click(function () {
			if ($(this).attr('toggle') === 'show') {
				$(this).text('Ocultar').attr('toggle', "hide")
				$('#showOption').css("display", "block")
				pmvResize();
			} else {
				$(this).text('Mostrar').attr('toggle', "show")
				$('#showOption').css("display", "none")
			}
		})
		
		pmvResize();
		$(window).resize(pmvResize);
	})
}

main();