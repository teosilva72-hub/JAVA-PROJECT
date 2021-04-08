import init, { PMV, PaginaType } from "/resources/pkg/project.js";

let listPMV;

window.reloadPMV = 0;

async function initPMV() {
	await init();
	const animationPMV = (img1, img2, message, pmv) => {
		let at = 300
		let driver = pmv.type_page()
		let reload = window.reloadPMV;

		const startAnimation = () => {
			if (window.reloadPMV != reload) {
				return;
			}

			let page = pmv.next();
			let image1 = [page.image(0), page.image_id(0)]
			let image2 = [page.image(1), page.image_id(1)]

			img1.fadeOut()
			setTimeout(() => {
				img1.attr({ src: image1[0], 'id-img': image1[1] }).fadeIn()
			}, at)
			if (driver == 3) {
				img2.fadeOut()
				setTimeout(() => {
					img2.attr({ src: image2[0], 'id-img': image2[1] }).fadeIn()
				}, at)
			}

			message.children().each(function (line) {
				$(this).children().each(function (index) {
					let char = $(this).find('span[id^=box]').fadeOut()
					let c = page.line_char(line + 1, index);
					setTimeout(() => {
						char.text(c).fadeIn()
					}, at)
				})
			})

			if (pmv.len() > 1)
				setTimeout(() => {
					startAnimation();
				}, page.timer() * 1000);
		}

		startAnimation();
	}

	const initAnimation = () => {
		listPMV.forEach(pmv => {
			const tablePMV = $(`#dms${pmv.id()}`);

			tablePMV.addClass(`driver${pmv.type_page()}`)

			const img1 = tablePMV.find('.picture-box.primary')
			const img2 = tablePMV.find('.picture-box.secondary')
			const message = tablePMV.find('#message')

			animationPMV(img1, img2, message, pmv)
		});
	}

	const collectPMV = () => {
		listPMV = [];

		$('[id^=listPMV]').each(function () {
			let data = $(this);
			let driver;

			switch (Number(data.attr('driver'))) {
				case 1:
					driver = PaginaType.Type1;

					break;

				case 2:
					driver = PaginaType.Type2;

					break;

				case 3:
					driver = PaginaType.Type3;

					break;

				default:
					driver = false;

					break;
			}

			if (driver) {
				let pmv = PMV.new(Number(data.attr('id').match(/\d+/g)[0]) || 0, data.attr('type') || "", data.attr('name') || "", driver);

				data.children().each(function () {
					let page = $(this);
					let timer = Number(page.attr('timer')) || 0
					if (timer)
						pmv.add_page(
							Number(page.attr('imageId')) || 0,
							Number(page.attr('imageId2')) || 0,
							`/resources/images/pictures/${page.attr('image')}` || "",
							`/resources/images/pictures/${page.attr('image2')}` || "",
							timer,
							page.attr('line1') || "",
							page.attr('line2') || "",
							page.attr('line3') || ""
						);
				})

				listPMV.push(pmv);

			}
			data.remove()
		})
	}

	$(function () {
		window.reloadPMV++;

		collectPMV();
		initAnimation();
	})
}

window.initPMV = initPMV;