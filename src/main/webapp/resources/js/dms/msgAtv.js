import init, { PMV, PaginaType } from "/resources/pkg/project.js";

let listPMV = [];
let listChangePMV = [];
let timeChangePMV = [];
let saveChangePMV = [];
let listSelectPMV = [];
let animationPreview = 0;
let toast;

let changedPMV = {};

async function main() {
	await init();

	const allPMV = $('#allPMV')
	const checksListAll = $('#checkListAll');
	const inputDriver = $('[name=typePMV]');
	const pagePMV = $('#page-pmv');
	const selectType = $("#selectionType");
	const apply = $('#btn-apply');
	const reset = $('#btn-reset');
	const clear = $('#btn-clear');
	const dmsChanges = $('#dmsChanges');
	let equipInfo = $('.equip-info');
	let equipInfoView = $('#one .equip-info, #two .equip-info');
	let allChecks = $('.option [id^=check]');

	const pmvResize = () => {
		equipInfo.css('transform', function () {
			let scale = Math.min(0.9 / (equipInfo.outerWidth() / $(this).closest('.message-block').outerWidth()), Number($(this).attr('scale')));
			return `translateX(-50%) scale(${scale})`;
		});

		allPMV.height($('.jumbotron').height() - 16)
	}

	const returnAlert = msg => {
		load();
		
		dmsChanges.val('')

		$('#msgToastNotification').text(msg);
		toast.show();
	}

	const animationPMV = (img1, img2, message, arr, idx) => {
		let at = 400
		let pmv = arr[idx].clone()
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

				if (arr === listSelectPMV)
					animationPreview = id;
				else if (arr === listChangePMV)
					timeChangePMV[idx] = id;
			}
		}

		startAnimation();
	}

	const initAnimation = () => {
		listPMV.forEach((pmv, idx, arr) => {
			const tablePMV = $(`#one .equip-info:eq(${idx})`);

			tablePMV.addClass(`driver${pmv.type_page()}`)

			const img1 = tablePMV.find('.picture-box.primary')
			const img2 = tablePMV.find('.picture-box.secondary')
			const message = tablePMV.find('#message')

			animationPMV(img1, img2, message, arr, idx)
		});

		listChangePMV.forEach((pmv, idx, arr) => {
			const tablePMV = $(`#two .equip-info:eq(${idx})`);

			tablePMV.addClass(`driver${pmv.type_page()}`)

			const img1 = tablePMV.find('.picture-box.primary')
			const img2 = tablePMV.find('.picture-box.secondary')
			const message = tablePMV.find('#message')

			animationPMV(img1, img2, message, arr, idx)
		});
	}

	const collectPMV = () => {
		listPMV = [];
		listChangePMV = [];

		if (!listSelectPMV.length) {
			let pmv = PMV.new(0, "", "", PaginaType.Type3)
			pmv.add_page_default();
			listSelectPMV.push(pmv);
		}

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
				let pmv = PMV.new(Number(data.attr('idMessage') || 0), data.attr('type') || "", data.attr('name') || "", driver);

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
					data.next().find('input').val(listChangePMV.length)
					listChangePMV.push(pmv);
					timeChangePMV.push(0);
					data.next().find('.tableStyle').addClass(data.attr('status') == "true" ? "unchanged" : "change")
				} else {
					data.next().val(listSelectPMV.length)
					listSelectPMV.push(pmv);
				}
			}
			data.remove()
		})
		saveChangePMV = listChangePMV.slice(0);
	}

	const previewPMV = (idx) => {
		clearTimeout(animationPreview)
		pagePMV.find('.dmsTab').text(listSelectPMV[idx].type_alert('&this'))

		const img1 = pagePMV.find('.picture-box.primary')
		const img2 = pagePMV.find('.picture-box.secondary')
		const message = pagePMV.find('#message')

		animationPMV(img1, img2, message, listSelectPMV, idx)
	}

	const applyPMV = (selectBTN) => {
		let idx = $(`#availableMessage`).val();
		let selected = $('#two .option input:checked');

		selected.each(function () {
			const select = $(this);
			const idxChange = select.val();
			const tablePMV = select.parent().next();
			const id = tablePMV.attr('id').match(/\d+/g)[0];

			switch (selectBTN) {
				case 1:
					listChangePMV[idxChange] = listSelectPMV[idx];

					break;

				case 2:
					listChangePMV[idxChange] = listSelectPMV[0];

					break;

				case 3:
					listChangePMV[idxChange] = listPMV[idxChange];

					break;

				default:
					break;
			}

			if (saveChangePMV[idxChange].id() !== listChangePMV[idxChange].id()) {
				tablePMV.children().first().addClass('preview')
				changedPMV[id] = String(listChangePMV[idxChange].id());
			} else {
				tablePMV.children().first().removeClass('preview')
				delete changedPMV[id];
			}

			const img1 = tablePMV.find('.picture-box.primary')
			const img2 = tablePMV.find('.picture-box.secondary')
			const message = tablePMV.find('#message')

			clearTimeout(timeChangePMV[idxChange])
			animationPMV(img1, img2, message, listChangePMV, idxChange)
		});

		dmsChanges.val(JSON.stringify(changedPMV));
	}

	const load = () => {
		allPMV.load('/dms/messages/equipment-list.xhtml', () => {
			collectPMV();
			initAnimation();

			$('#messages-list > option').appendTo($(`#availableMessage`));

			equipInfo = $('.equip-info');
			equipInfoView = $('#one .equip-info, #two .equip-info');
			allChecks = $('.option [id^=check]');

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
				$(`#availableMessage`).val(0).trigger('change');
				selectType.val('All');
				previewPMV(0);
			})

			pmvResize();
			inputDriver.filter(':checked').trigger('change');
		})
	}

	$(function () {
		load();

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

		selectType.change(function () {
			let type = $(this).val();
			let select = $(`#availableMessage`);
			let msg = select.children().filter(`option[driver=${inputDriver.filter(':checked').val()}]`);
			if (type === "All")
				msg.show();
			else {
				msg.hide().filter(`[type=${type}]`).show();
				if (type != select.find(':selected').attr('type'))
					select.val(0).trigger('change');
			}
		})

		$(`#availableMessage`).change(function () {
			let idx = $(this).val();
			previewPMV(idx);

			if (idx == 0)
				apply.prop('disabled', true);
			else
				apply.prop('disabled', false);
		})

		toast = new bootstrap.Toast(document.getElementById('liveToast'))

		$(window).resize(pmvResize);
		window.returnAlert = returnAlert;
		apply.click(() => { applyPMV(1) });
		clear.click(() => { applyPMV(2) });
		reset.click(() => { applyPMV(3) });
	})
}

main();