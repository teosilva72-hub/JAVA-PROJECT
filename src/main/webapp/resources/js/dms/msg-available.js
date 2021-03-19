import init, { PMV, PaginaType } from "/resources/pkg/project.js";

let msg, toast, pmvActive, pageActive;
let listPMV = [];

async function main() {
	await init();

	const typeInput = $('#type-input');
	const nameInput = $('#name-input');
	const messageBox1 = $('#message-box1');
	const messageBox2 = $('#message-box2');
	const messageBox3 = $('#message-box3');
	const btnCreate = $('#btnCreate');
	const btnEdit = $('#btnEdit');
	const btnDelete = $('#btnDelete');
	const btnSave = $('#btnCr1');
	const btnCancel = $('#btnCr2');
	const btnSend = $("[id$=btnCr3]");
	const disableTable = $('#disableTable');
	const editField = $('.edit-field');
	const editPMV = $('.edit-pmv-page');
	const timerCheck = $('input[id^=timerCheck]:not(#timerCheck1)');
	const timerCheckAll = $('input[id^=timerCheck]');
	const pre_vi = $(`#page-pmv .equip-info`);
	const equipInfo = $('.equip-info');
	const picture = equipInfo.find(`.picture-box`);
	const table = $("#tabelaReal");
	const timerPage = $('[id^=timerPage]');
	const listImage = $('#list-images');
	const selectDriver = $('#selectDriver');
	const pmvBoard = $('.pmv-board');

	// implementation 'loopNext' in jquery
	$.fn.loopNext = function (selector) {
		var selector = selector || '';
		return this.next(selector).length ? this.next(selector) : this.siblings(selector).addBack(selector).first();
	}

	const pmvResize = () => {
		let scale = Math.min(0.9 / (equipInfo.outerWidth() / pmvBoard.outerWidth()), 1.5);
		equipInfo.css('transform', `translateX(-50%) scale(${scale})`);
	}

	// Select message in pre-view
	const selectMessage = () => {
		savePMV();

		msg = {
			id: String(pmvActive.id()),
			type: pmvActive.type_alert('&this'),
			name: pmvActive.name('&this'),
			type_page: String(pmvActive.type_page()),
			pages: []
		}

		for (let i = 0; i < 5; i++) {
			let pagePMV = pmvActive.page(i);
			msg.pages.push({
				image: pagePMV.image(0),
				image2: pagePMV.image(1),
				image_id: String(pagePMV.image_id(0)),
				image_id2: String(pagePMV.image_id(1)),
				timer: String(pagePMV.timer()),
				line1: pagePMV.line(1),
				line2: pagePMV.line(2),
				line3: pagePMV.line(3),
			})
		}

		document.forms.contentForm.requestParam.value = JSON.stringify({ id: msg.id, type: msg.type, name: msg.name, type_page: msg.type_page });
		document.forms.contentForm.requestParamPAGE1.value = JSON.stringify(msg.pages[0]);
		document.forms.contentForm.requestParamPAGE2.value = JSON.stringify(msg.pages[1]);
		document.forms.contentForm.requestParamPAGE3.value = JSON.stringify(msg.pages[2]);
		document.forms.contentForm.requestParamPAGE4.value = JSON.stringify(msg.pages[3]);
		document.forms.contentForm.requestParamPAGE5.value = JSON.stringify(msg.pages[4]);

		btnSend.click();
	}

	const savePMV = () => {
		pmvActive.type_alert(typeInput.val() || "");
		pmvActive.name(nameInput.val());

		let img = equipInfo.find(`.picture-box`);

		pmvActive.change_page(pageActive, messageBox1.val(), messageBox2.val(), messageBox3.val());
		pmvActive.change_image(pageActive, img.eq(0).attr("id-img"), img.eq(1).attr("id-img"), img.eq(0).attr("src"), img.eq(1).attr("src"));
	}

	// Get update to menu
	const updateMessage = () => {
		changeEquipInfo();

		$('#id-input').val(pmvActive.id())
		typeInput.val(pmvActive.type_alert('&this'))
		nameInput.val(pmvActive.name('&this'))
		messageBox1.val(pmvActive.page(pageActive).line(1))
		messageBox2.val(pmvActive.page(pageActive).line(2))
		messageBox3.val(pmvActive.page(pageActive).line(3))
	}

	// Create new message
	const newMsg = () => {
		btnSave.prop('disabled', false);
		btnCancel.prop('disabled', false);
		disableTable.addClass('active');
		editField.addClass('active');
		editPMV.find(`[id=timerPage1]`).prop('disabled', false)
		equipInfo.addClass('editing');

		$('#btn-page1').prop('checked', true);

		let type

		switch (selectDriver.val()) {
			case "1":
				type = PaginaType.Type1
				break;

			case "2":
				type = PaginaType.Type2
				break;

			case "3":
				type = PaginaType.Type3
				break;

			default:
				type = PaginaType.Type1
				break;
		}

		pmvActive = PMV.new(0, "", "", type);
		pmvActive.add_page_default();
		pageActive = 0;

		updateMessage();
		timerCheckAll.trigger('change')
	}

	// Create new message
	const editMsg = () => {
		btnSave.prop('disabled', false);
		btnCancel.prop('disabled', false);
		disableTable.addClass('active');
		editField.addClass('active');
		editPMV.addClass('active').find(`[id=timerPage1]`).prop('disabled', false)
		equipInfo.addClass('editing');
		
		updateMessage();
		timerCheckAll.trigger('change')
	}

	const returnAlert = msg => {
		changeDriver();

		btnEdit.prop('disabled', true);
		btnDelete.prop('disabled', true);

		cancel();

		$('#msgToastNotification').text(msg);
		toast.show();
	}

	// Cancel message in menu
	const cancel = () => {
		btnCreate.prop('disabled', false);
		btnEdit.prop('disabled', true);
		btnDelete.prop('disabled', true);
		btnSave.prop('disabled', true);
		btnCancel.prop('disabled', true);
		disableTable.removeClass('active');
		editPMV.removeClass('active');
		editField.removeClass('active');
		equipInfo.removeClass('editing');
		picture.removeClass('selected')
		listImage.css('display', 'none')
		pre_vi.find('.picture-box').attr({ src: "/resources/images/pictures/000_6464.bmp", 'id-img': 0 });
		pre_vi.find('.dmsTab span').text('').each(function () {
			$(this).last().attr('type', '')
		})
		pre_vi.find(`#message`).children().each(function () {
			$(this).children().each(function () {
				$(this).find('[id*=box]').text("");
			})
		})

		timerCheck.prop('disabled', true);
		$('input[id^=timerPage]').prop('disabled', true);
	}

	const changeEquipInfo = () => {
		let PagePMV = pmvActive.page(pageActive);
		let picture = pre_vi.find('.picture-box')

		// Add ID and NAME
		pre_vi.find('.dmsTab span#dmsId').text(`${pmvActive.id()}`).next().text(`${pmvActive.name('&this')}`)
		// add Type
		pre_vi.find('.dmsTab span#dmsType').text($(`#type-input [value="${pmvActive.type_alert('&this')}"]`).text()).attr('type', pmvActive.type_alert('&this'))
		// Add image
		picture
			.eq(0).attr({ src: PagePMV.image(0), 'id-img': PagePMV.image_id(0) })
		picture
			.eq(1).attr({ src: PagePMV.image(1), 'id-img': PagePMV.image_id(1) })
		// add messages
		pre_vi.find(`#message`).children().each(function (line) {
			$(this).children().each(function (index) {
				$(this).find('[id*=box]').text(PagePMV.line_char(line + 1, index))
			})
		})

		for (let i = 5; i >= 1; i--) {
			// add Timers
			if (pmvActive.len() >= i && pmvActive.page(i - 1).timer() > .0) {
				editPMV.find(`#timerPage${i}`).val(pmvActive.page(i - 1).timer());
				editPMV.find(`#timerCheck${i}`).prop('checked', true);
				editPMV.find(`#btn-page${i}`).prop('disabled', false);
			} else {
				editPMV.find(`#timerCheck${i}`).prop('checked', false);
				editPMV.find(`#btn-page${i}`).prop('disabled', true);
				editPMV.find(`#timerPage${i}`).val(0);
			}
		}
		editPMV.addClass('active').find(`#btn-page${pageActive + 1}`).prop('checked', true);
	}

	// render table with datatable
	const tableRender = () => {
		// Start more components for tables
		$('#message-table').DataTable({
			select: true,
			language: {
				search: "",
				searchPlaceholder: "Buscar",
			},
			"autoWidth": true,
			"scrollY": "65vh",
			"scrollCollapse": true,
			"paging": false,
			"bInfo": false,
			"columnDefs": [{
				"width": "5%",
				"targets": 0
			}, {
				"width": "10%",
				"targets": 1
			}, {
				"width": "10%",
				"targets": 2
			}, {
				"width": "5%",
				"targets": 3
			}, {
				"width": "15%",
				"targets": 4
			}, {
				"width": "15%",
				"targets": 5
			}, {
				"width": "15%",
				"targets": 6
			}, {
				"width": "5%",
				"targets": 7
			}]
		});
	}

	// init_table table
	const init_table = campos => {
		// get all message
		let table = $('.nameColumn + td.pageTable1')

		listPMV = [];
		table.each(function () {
			let tr = $(this).parent()

			// get all page with time > 0
			let pages = tr.find('td:not(td[timer="0.0"])').add(tr.find('.pageTable1'));
			let id = Number(pages.filter('.idColumn').text())
			let type = pages.filter('[type]').attr('type')
			let name = pages.find('.tablePageName').text()

			let pageType
			switch (selectDriver.val()) {
				case "1":
					pageType = PaginaType.Type1
					break;

				case "2":
					pageType = PaginaType.Type2
					break;

				case "3":
					pageType = PaginaType.Type3
					break;

				default:
					pageType = PaginaType.Type1
					break;
			}

			let pmv = PMV.new(id, type, name, pageType);

			for (let i = 0; i < 5; i++) {
				const page = pages.filter('.pageTable' + i);
				const verif = page.filter('td[active]').attr('active');
				if (verif) {
					const imgId = page.find('.picture-table').next().val();
					const imgId2 = page.find('.picture-table_2').next().val() || 0;
					const img = page.find('.picture-table').attr('src');
					const img2 = page.find('.picture-table_2').attr('src') || "/resources/images/pictures/000_6464.bmp";
					const timer = parseFloat(page.filter('td[timer]').attr('timer'));
					const line1 = page.filter('.msgPage1')
					const line2 = line1.next()
					const line3 = line2.next()

					pmv.add_page(imgId, imgId2, img, img2, timer, line1.text(), line2.text(), line3.text());
				}
			}

			listPMV.push(pmv);

			// Start rotation for tables
			if ($(this).siblings().addBack().filter('td[timer="0.0"]').length < campos * 4)
				changeMsg($(this), campos);
			else {
				let elmt = $(this).addClass('active');
				for (let idx = 1; idx < campos; idx++) {
					elmt = elmt.loopNext().addClass('active');
				}
			}


			// Add on click pre-visualization
			tr.click(function () {
				pmvActive = pmv.clone();
				pageActive = 0;

				changeEquipInfo();
				editPMV.find(`[id^=timerCheck]`).prop('disabled', true);

				btnEdit.prop('disabled', false);
				btnDelete.prop('disabled', false);

				$('#selectedId').text(id);
				document.forms.dialogForm.deleteID.value = id;
			})
		})

		// load first table
		tableRender();
	}

	// func to start rotation tables. Only tables
	const changeMsg = (msg, campos) => {
		if (msg.index() > 2) {
			let timer = msg.attr("timer");
			if (timer) {
				let elmt = [msg.addClass('active')];
				for (let idx = 0; idx < campos - 1; idx++) {
					elmt.push(elmt[idx].loopNext().addClass('active'));
				}
				setTimeout(() => {
					for (const e of elmt) {
						e.removeClass('active');
					}
					changeMsg(elmt.pop().loopNext(), campos);
				}, timer * 1000)
			} else {
				for (let idx = 0; idx < campos; idx++) {
					msg = msg.next();
				}
				changeMsg(msg, campos);
			}
		} else
			changeMsg(msg.loopNext(), campos);
	}

	// transform string to char in message pmv
	const upTextToChar = (elmt, id) => {
		let textFull = elmt.val();
		for (const idx in textFull) {
			if (Object.hasOwnProperty.call(textFull, idx) && idx < 12) {
				const char = textFull[idx];

				equipInfo.find(`.message${id}`).attr('msg', textFull).find(`div > span[id^=box]`)[idx].innerText = char;
			}
		}
		for (let index = 11; index >= textFull.length; index--) {
			equipInfo.find(`.message${id} div > span[id^=box]`)[index].innerText = "";
		}
	}

	const changeDriver = () => {

		cancel();

		switch (selectDriver.val()) {
			case "1":
				pmvBoard.removeClass(['driver2', 'driver3']);

				table.load('/dms/messages/message-driver1.xhtml', () => {
					// Main loading
					init_table(5);
				})

				break;

			case "2":
				pmvBoard.addClass('driver2')
					.removeClass('driver3');

				table.load('/dms/messages/message-driver2.xhtml', () => {
					// Main loading
					init_table(4);
				})

				break;

			case "3":
				pmvBoard.addClass('driver3')
					.removeClass('driver2');

				table.load('/dms/messages/message-driver3.xhtml', () => {
					// Main loading
					init_table(6);
				})

				break;

			default:
				break;
		}
	}

	$(function () {
		// request table
		changeDriver();

		// change and pre-save page
		editPMV.on('click', 'label', function () {
			if (equipInfo.hasClass('editing')) {
				savePMV();
			}

			pageActive = Number($(this).text()) - 1

			updateMessage();
		}).find('[id^=timerCheck]').change(function () {
			// disable or enable other page
			let check = $(this);
			let page = Number(check.parent().parent().siblings().eq(1).text()) - 1
			if (check.prop('checked')) {
				check.parent().next().prop('disabled', false).parent().next().prop('disabled', false)
					.parent().parent().next().find('input[id^=timerCheck]').prop('disabled', false);

				if (pmvActive.len() <= page)
					pmvActive.add_page_default();
			} else {
				check.parent().next().prop('disabled', true).parent().next().prop('disabled', true)
					.parent().parent().next().find('input[id^=timerCheck]').prop({ disabled: true, checked: false }).trigger('change');

				if (pmvActive.len() > page) {
					pmvActive.remove_page(page)
					pageActive = Number($('[id^=btn-page]:checked+label').text()) >= page ? page - 1 : Number($('[id^=btn-page]:checked+label').text()) - 1

					updateMessage();
				}

			}
			timerPage.each(function () {
				if (!$(this).prop('disabled'))
					$(this).val(function () { return Number($(this).val()) || 10; });
			})
		})

		// hidden img and open list
		picture.click(function () {
			if (equipInfo.hasClass('editing')) {
				picture.removeClass('selected')
				$(this).addClass('selected')
				$('#list-images').css('display', 'block').find('div > img[id-img]').on('dragstart', e => { e.preventDefault() })
			}
		})

		// change field image
		listImage.on('click', 'div > img[id-img]', function (e) {
			e.currentTarget.ondragstart = function () { return false }
			equipInfo.find('#child-img .picture-box.selected').attr({ src: $(this).attr('src'), 'id-img': $(this).attr('id-img') }).removeClass('selected')
			listImage.css('display', 'none')
		})

		// change field type
		typeInput.change(function () {
			equipInfo.find('.dmsTab span#dmsType').attr('type', $(this).val()).text($(this).find('option:selected').text())
		})
		// change field name
		nameInput.on('keyup change', function () {
			equipInfo.find('.dmsTab span#dmsName').text($(this).val())
		})
		for (let line = 1; line <= 3; line++) {
			// change field msg line
			$(`#message-box${line}`).on('keyup change', function () {
				upTextToChar($(this), line);
			})
		}
		// if change timer
		timerPage.on('keyup change', function () {
			pmvActive.change_timer(Number($(this).parent().next().next().text()) - 1, Number($(this).val()));
		})

		
		toast = new bootstrap.Toast(document.getElementById('liveToast'))
		
		pmvResize();
		$(window).resize(pmvResize);
		window.returnAlert = returnAlert;
		
		btnCreate.click(newMsg);
		btnEdit.click(editMsg);
		btnCancel.click(cancel);
		btnSave.click(selectMessage);
		selectDriver.change(changeDriver);
	})
}

main();