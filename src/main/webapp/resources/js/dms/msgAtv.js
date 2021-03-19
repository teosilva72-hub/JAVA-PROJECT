import init, { PMV, PaginaType } from "/resources/pkg/project.js";

let listPMV = [];
let listChangePMV = [];
let listSelectPMV = [];
let animationPreview = 0;

async function main() {
	await init();

	const equipInfo = $('.equip-info');
	const equipInfoView = $('#one .equip-info, #two .equip-info');
	const allChecks = $('.option [id^=check]')
	const checksListAll = $('#checkListAll')
	const inputDriver = $('[name=typePMV]')
	const pagePMV = $('#page-pmv')
	const selectType = $("#selectionType")

	const pmvResize = () => {
		equipInfo.css('transform', function () {
			let scale = Math.min(0.9 / (equipInfo.outerWidth() / $(this).closest('.message-block').outerWidth()), Number($(this).attr('scale')));
			return `translateX(-50%) scale(${scale})`;
		});

		$('#allPMV').height($('.jumbotron').height() - 16)
	}

	const animationPMV = (img1, img2, message, pmv, preview) => {
		let at = 300
		let driver = pmv.type_page()

		const startAnimation = () => {
			let page = pmv.next();

			img1.fadeOut()
			setTimeout(() => {
				img1.attr({ src: page.image(0), 'id-img': page.image_id(0) }).fadeIn()
			}, at)
			if (driver == 3) {
				img2.fadeOut()
				setTimeout(() => {
					img2.attr({ src: page.image(1), 'id-img': page.image_id(1) }).fadeIn()
				}, at)
			}

			message.children().each(function (line) {
				$(this).children().each(function (index) {
					let char = $(this).find('span[id^=box]').fadeOut()
					setTimeout(() => {
						char.text(page.line_char(line + 1, index)).fadeIn()
					}, at)
				})
			})

			let id;

			if (pmv.len() > 1) {
				id = setTimeout(() => {
					startAnimation();
				}, page.timer() * 1000);

				if (preview)
					animationPreview = id;
			}
		}

		startAnimation();
	}

	const initAnimation = () => {
		listPMV.forEach(pmv => {
			const tablePMV = $(`#one #dms${pmv.id()}`);

			tablePMV.addClass(`driver${pmv.type_page()}`)

			const img1 = tablePMV.find('.picture-box.primary')
			const img2 = tablePMV.find('.picture-box.secondary')
			const message = tablePMV.find('#message')

			animationPMV(img1, img2, message, pmv, false)
		});

		listChangePMV.forEach(pmv => {
			const tablePMV = $(`#two #dms${pmv.id()}`);

			tablePMV.addClass(`driver${pmv.type_page()}`)

			const img1 = tablePMV.find('.picture-box.primary')
			const img2 = tablePMV.find('.picture-box.secondary')
			const message = tablePMV.find('#message')

			animationPMV(img1, img2, message, pmv, false)
		});
	}

	const collectPMV = () => {
		$('[id^=listPMV], [id^=listChangePMV], [id^=listSelectPMV]').each(function () {
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
				let pmv = PMV.new(Number((data.attr('id').match(/\d+/g) || [0])[0]), data.attr('type') || "", data.attr('name') || "", driver);

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

				if (data.attr('id').startsWith("listPMV"))
					listPMV.push(pmv);
				else if (data.attr('id').startsWith("listChangePMV")) {
					listChangePMV.push(pmv);
					data.next().find('.tableStyle').addClass(data.attr('status') == "true" ? "unchanged" : "change")
				} else {
					listSelectPMV.push(pmv);
					data.next().val(listSelectPMV.length - 1)
				}

			}
			data.remove()
		})
	}

	const previewPMV = idx => {
		clearTimeout(animationPreview)
		pagePMV.find('.dmsTab').text(listSelectPMV[idx].type_alert('&this'))

		const img1 = pagePMV.find('.picture-box.primary')
		const img2 = pagePMV.find('.picture-box.secondary')
		const message = pagePMV.find('#message')

		animationPMV(img1, img2, message, listSelectPMV[idx], true)
	}

	$(function () {
		collectPMV();
		initAnimation();

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

		$('#messages-list > option').appendTo($(`#availableMessage`));

		allChecks.filter(":enabled").change(function () {
			let check = $(this)
			$(`#${check.attr('id')}_Change`).prop('checked', check.prop('checked'))

			let verif = allChecks.filter(":enabled").map(function (a, b) { return $(b).prop('checked') }).toArray()

			if (verif.reduce(function (a, b) { return a && b }))
				checksListAll.prop('checked', true);
			else
				checksListAll.prop('checked', false);
		})

		checksListAll.change(() => {
			allChecks.filter(":enabled").prop('checked', checksListAll.prop('checked')).trigger('change')
		})

		$('#one .equip-info').change(function () {
			let equip = $(this);

			equip.prev().find('input').prop('disabled', equip.hasClass('unable'))
		})

		inputDriver.change(function () {
			equipInfoView.addClass('unable').filter(`.${$(this).val()}`).removeClass('unable');
			equipInfoView.trigger('change');
			allChecks.prop('checked', false);
			checksListAll.prop('checked', false);
			pagePMV.removeClass(['driver1', 'driver2', 'driver3']).addClass($(this).val())
			$(`#availableMessage option`).filter('option[driver]').hide().filter(`option[driver=${$(this).val()}]`).show();
			selectType.val('All');
		})

		selectType.change(function () {
			let type = $(this).val();
			let msg = $(`#availableMessage option`).filter(`option[driver=${inputDriver.filter(':checked').val()}]`);
			if (type === "All")
				msg.show();
			else
				msg.hide().filter(`[type=${type}]`).show();
		})

		$(`#availableMessage`).change(function() {
			let idx = $(this).val();
			previewPMV(idx);
		})

		pmvResize();
		$(window).resize(pmvResize);
		inputDriver.filter('#typePMV1').trigger('change');
	})
}

main();