import init, { PMV } from "/resources/pkg/project.js";

var msg, toast, pmvActive, pageActive;
var listPMV = [];

async function main() {
	await init();

	let typeInput = $('#type-input');
	let nameInput = $('#name-input');
	let messageBox1 = $('#message-box1');
	let messageBox2 = $('#message-box2');
	let messageBox3 = $('#message-box3');
	let btnCreate = $('#btnCreate');
	let btnEdit = $('#btnEdit');
	let btnDelete = $('#btnDelete');
	let btnSave = $('#btnCr1');
	let btnCancel = $('#btnCr2');
	let btnSend = $("[id$=btnCr3]");
	let disableTable = $('#disableTable');
	let editField = $('.edit-field');
	let editPMV = $('.edit-pmv-page');
	let timerCheck = $('input[id^=timerCheck]');
	let pre_vi = $(`#page-pmv .equip-info`);
	let equipInfo = $('.equip-info');
	let table = $("#tabelaReal");
	let timerPage = $('[id^=timerPage]');
	let listImage = $('#list-images');

	// implementation 'loopNext' in jquery
	$.fn.loopNext = function (selector) {
		var selector = selector || '';
		return this.next(selector).length ? this.next(selector) : this.siblings(selector).addBack(selector).first();
	}

	// Select message in pre-view
	const selectMessage = () => {
		savePMV();

		msg = {
			id: String(pmvActive.id()),
			type: pmvActive.type_alert('&this'),
			name: pmvActive.name('&this'),
			pages: []
		}

		for (let i = 0; i < 5; i++) {
			msg.pages.push({
				image: pmvActive.page(i).image(),
				image_id: String(pmvActive.page(i).image_id()),
				timer: String(pmvActive.page(i).timer()),
				line1: pmvActive.page(i).line(1),
				line2: pmvActive.page(i).line(2),
				line3: pmvActive.page(i).line(3),
			})
		}

		document.forms.contentForm.requestParam.value = JSON.stringify({ id: msg.id, type: msg.type, name: msg.name });
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

		let img = $(`#page-pmv .equip-info .picture-box`);

		pmvActive.change_page(pageActive, messageBox1.val(), messageBox2.val(), messageBox3.val());
		pmvActive.change_image(pageActive, img.attr("id-img"), img.attr("src"));
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
		btnCreate.prop('disabled', true);
		btnEdit.prop('disabled', true);
		btnDelete.prop('disabled', true);
		btnSave.prop('disabled', false);
		btnCancel.prop('disabled', false);
		disableTable.addClass('active');
		editField.addClass('active');
		editPMV.find(`[id=timerPage1]`).prop('disabled', false)

		$('#btn-page1').prop('checked', true);

		pmvActive = PMV.new(0, "", "");
		pmvActive.add_page_default();
		pageActive = 0;

		updateMessage();

		timerCheck.prop('disabled', false);
	}

	// Create new message
	const editMsg = () => {
		btnCreate.prop('disabled', true);
		btnEdit.prop('disabled', true);
		btnDelete.prop('disabled', true);
		btnSave.prop('disabled', false);
		btnCancel.prop('disabled', false);
		disableTable.addClass('active');
		editField.addClass('active');
		editPMV.addClass('active').find(`[id=timerPage1]`).prop('disabled', false)

		timerCheck.prop('disabled', false);

		updateMessage();
	}

	const returnAlert = msg => {
		table.load('/dms/messages/message-full.xhtml', () => {
			// Main loading
			init_table();
		})

		btnEdit.prop('disabled', true);
		btnDelete.prop('disabled', true);

		cancel();

		$('#msgToastNotification').text(msg);
		toast.show();
	}

	// Cancel message in menu
	const cancel = () => {
		btnCreate.prop('disabled', false);
		btnSave.prop('disabled', true);
		btnCancel.prop('disabled', true);
		disableTable.removeClass('active');
		editPMV.removeClass('active');
		editField.removeClass('active');
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
		// Add ID and NAME
		pre_vi.find('.dmsTab span#dmsId').text(`${pmvActive.id()}`).next().text(`${pmvActive.name('&this')}`)
		// add Type
		pre_vi.find('.dmsTab span#dmsType').text($(`#type-input [value="${pmvActive.type_alert('&this')}"]`).text()).attr('type', pmvActive.type_alert('&this'))
		// Add image
		pre_vi.find('.picture-box').attr({ src: pmvActive.page(pageActive).image(), 'id-img': pmvActive.page(pageActive).image_id() })
		// add messages
		pre_vi.find(`#message`).children().each(function (line) {
			$(this).children().each(function (index) {
				$(this).find('[id*=box]').text(pmvActive.page(pageActive).line_char(line + 1, index))
			})
		})

		for (let i = 5; i >= 1; i--) {
			// add Timers
			if (pmvActive.len() >= i && pmvActive.page(i - 1).timer() > .0) {
				editPMV.find(`#timerPage${i}`).val(pmvActive.page(i - 1).timer());
				editPMV.find(`#timerCheck${i}`).prop('checked', true).trigger('change');
				editPMV.find(`#btn-page${i}`).prop('disabled', false);
			} else {
				editPMV.find(`#timerCheck${i}`).prop('checked', false).trigger('change');
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
	const init_table = () => {
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

			let pmv = PMV.new(id, type, name);

			for (let i = 0; i < 5; i++) {
				const page = pages.filter('.pageTable' + i);
				const verif = page.filter('td[active]').attr('active');
				if (verif) {
					const imgId = page.find('[id*=picture-table]').next().val()
					const img = page.find('[id*=picture-table]').attr('src');
					const timer = parseFloat(page.filter('td[timer]').attr('timer'));
					const line1 = page.filter('.msgPage1')
					const line2 = line1.next()
					const line3 = line2.next()

					pmv.add_page(imgId, img, timer, line1.text(), line2.text(), line3.text());
				}
			}

			listPMV.push(pmv);

			// Start rotation for tables
			if ($(this).siblings().addBack().filter('td[timer="0.0"]').length < 20)
				changeMsg($(this));
			else {
				$(this).addClass('active')
					.loopNext().addClass('active')
					.loopNext().addClass('active')
					.loopNext().addClass('active')
					.loopNext().addClass('active')
			}


			// Add on click pre-visualization
			pre_vi.addClass('active');
			tr.click(function () {
				pmvActive = pmv.clone();
				pageActive = 0;

				changeEquipInfo();

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
	const changeMsg = msg => {
		if (msg.index() > 2) {
			let timer = msg.attr("timer");
			let text1 = msg.loopNext();
			let text2 = text1.loopNext();
			let text3 = text2.loopNext();
			let pg = text3.loopNext();
			if (timer) {
				msg.addClass('active');
				text1.addClass('active');
				text2.addClass('active');
				text3.addClass('active');
				pg.addClass('active');
				setTimeout(() => {
					msg.removeClass('active');
					text1.removeClass('active');
					text2.removeClass('active');
					text3.removeClass('active');
					pg.removeClass('active');
					changeMsg(pg.loopNext());
				}, timer * 1000)
			} else
				changeMsg(pg.loopNext());
		} else
			changeMsg(msg.loopNext());
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

	$(function () {
		// request table
		table.load('/dms/messages/message-full.xhtml', () => {
			// Main loading
			init_table();
		})

		// change and pre-save page
		editPMV.on('click', 'label', function () {
			savePMV();

			pageActive = Number($(this).text()) - 1

			updateMessage();
		}).find('[id^=timerCheck]').change(function () {
			// disable or enable other page
			let check = $(this);
			let page = Number(check.parent().siblings().eq(2).text()) - 1
			if (check.prop('checked')) {
				check.parent().next().prop('disabled', false).next().prop('disabled', false)
					.parent().parent().next().find('input[id^=timerCheck]').prop('disabled', false);

				if (pmvActive.len() <= page)
					pmvActive.add_page_default();
			}
			else {
				check.parent().next().prop('disabled', true).next().prop('disabled', true)
					.parent().parent().next().find('input[id^=timerCheck]').prop({ disabled: true, checked: false }).trigger('change');


				if (pmvActive.len() > page) {
					pmvActive.remove_page(page)
					pageActive = page - 1

					updateMessage();
				}

			}
			timerPage.each(function () {
				if (!$(this).prop('disabled'))
					$(this).val(function () { return Number($(this).val()) || 1; });
			})
		})

		// hidden img and open list
		$('#image-div').click(function () {
			$(this).css('display', 'none').next().css('display', 'block').find('div > img[id-img]').on('dragstart', e => { e.preventDefault() })
		})

		// change field image
		listImage.on('click', 'div > img[id-img]', function (e) {
			e.currentTarget.ondragstart = function () { return false }
			equipInfo.find('#child-img img[id-img]').attr({ src: $(this).attr('src'), 'id-img': $(this).attr('id-img') })
			listImage.css('display', 'none').prev().css('display', 'block')
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
			pmvActive.change_timer(Number($(this).siblings().eq(2).text()) - 1, Number($(this).val()));
		})

		toast = new bootstrap.Toast(document.getElementById('liveToast'))

		window.returnAlert = returnAlert;

		btnCreate.click(newMsg);
		btnEdit.click(editMsg);
		btnCancel.click(cancel);
		btnSave.click(selectMessage);
	})
}

main();