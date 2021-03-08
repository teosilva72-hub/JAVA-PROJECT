var msg, toast, modal;

// implementation 'loopNext' in jquery
$.fn.loopNext = function (selector) {
	var selector = selector || '';
	return this.next(selector).length ? this.next(selector) : this.siblings(selector).addBack(selector).first();
}

// Select message in pre-view
const selectMessage = () => {
	let pmv = $(`.equip-info.equip1`)
	msg = {
		id: pmv.find('.dmsTab span#dmsId').text(),
		type: pmv.find('.dmsTab span#dmsType').attr('type'),
		name: pmv.find('.dmsTab span#dmsName').text(),
		pages: []
	}

	for (let i = 1; i <= 5; i++) {
		let info = $(`.equip-info.equip${i}`)
		msg.pages.push({
			image: info.find('#child-img img').attr('src'),
			image_id: info.find('#child-img img').attr('id-img'),
			timer: String(($(`[id$=imerCheck${i}]`).prop('checked') || 0) && $(`#timerPage${i}`).val()),
			line1: info.find('#child-msg .message1').attr('msg'),
			line2: info.find('#child-msg .message2').attr('msg'),
			line3: info.find('#child-msg .message3').attr('msg'),
		})
	}

	$('#selectedId').text(msg.id);

	document.forms.dialogForm.deleteID.value = msg.id;
	document.forms.contentForm.requestParam.value = JSON.stringify({ id: msg.id, type: msg.type, name: msg.name });
	document.forms.contentForm.requestParamPAGE1.value = JSON.stringify(msg.pages[0]);
	document.forms.contentForm.requestParamPAGE2.value = JSON.stringify(msg.pages[1]);
	document.forms.contentForm.requestParamPAGE3.value = JSON.stringify(msg.pages[2]);
	document.forms.contentForm.requestParamPAGE4.value = JSON.stringify(msg.pages[3]);
	document.forms.contentForm.requestParamPAGE5.value = JSON.stringify(msg.pages[4]);
}

// Get update to menu
const updateMessage = () => {
	$('#id-input').val(msg.id)
	$('#type-input').val(msg.type)
	$('#name-input').val(msg.name)
	$('#message-box1').val(msg.pages[$('.equip-info.active').index()].line1)
	$('#message-box2').val(msg.pages[$('.equip-info.active').index()].line2)
	$('#message-box3').val(msg.pages[$('.equip-info.active').index()].line3)
}

// Create new message
const newMsg = () => {
	$('#btnCreate').prop('disabled', true);
	$('#btnEdit').prop('disabled', true);
	$('#btnDelete').prop('disabled', true);
	$('[id$=btnCr1]').prop('disabled', false);
	$('#btnCr2').prop('disabled', false);
	$('#disableTable').addClass('active');
	$('.edit-field').addClass('active');
	$('.edit-pmv-page').find(`[id=timerPage1]`).prop('disabled', false)
	$('.edit-pmv-page').addClass('active')
		.find(`[id^=timerPage]`).val(0)
		.eq(1).prev().find('[id^=timerCheck]')
		.prop('checked', false).trigger('change');
	let pre_vi = $(`#page-pmv .equip-info`);
	pre_vi.find('.picture-box').attr({ src: "/resources/images/pictures/000_6464.bmp", 'id-img': 0 });
	pre_vi.find('.dmsTab span').text('').each(function () {
		$(this).last().attr('type', '')
	})
	pre_vi.find('.dmsTab #dmsId').text('0')
	pre_vi.find(`[id^=msg]`).children().each(function () {
		$(this).attr('msg', '').children().each(function () {
			$(this).find('[id*=box]').text("");
		})
	})

	$('#btn-page1').prop('checked', true);
	$(`.equip-info.equip1`).addClass('active').siblings().removeClass('active');

	$('input[id^=timerCheck]').prop('disabled', false);

	selectMessage();
	updateMessage();
}

// Create new message
const editMsg = () => {
	$('#btnCreate').prop('disabled', true);
	$('#btnEdit').prop('disabled', true);
	$('#btnDelete').prop('disabled', true);
	$('[id$=btnCr1]').prop('disabled', false);
	$('#btnCr2').prop('disabled', false);
	$('#disableTable').addClass('active');
	$('.edit-field').addClass('active');
	$('.edit-pmv-page').addClass('active').find(`[id=timerPage1]`).prop('disabled', false)

	$('input[id^=timerCheck]').prop('disabled', false).trigger('change');

	selectMessage();
	updateMessage();
}

const returnAlert = msg => {
	$("#tabelaReal").load('/dms/messages/message-full.xhtml', () => {
		// Main loading
		init();
	})

	$('#btnEdit').prop('disabled', true);
	$('#btnDelete').prop('disabled', true);

	cancel();

	$('#msgToastNotification').text(msg);
	// modal.hide();
	toast.show();
}

// Cancel message in menu
const cancel = () => {
	$('#btnCreate').prop('disabled', false);
	$('[id$=btnCr1]').prop('disabled', true);
	$('#btnCr2').prop('disabled', true);
	$('#disableTable').removeClass('active');
	$('.edit-pmv-page').removeClass('active');
	$('.edit-field').removeClass('active');
	let pre_vi = $(`#page-pmv .equip-info`);
	pre_vi.find('.picture-box').attr({ src: "/resources/images/pictures/000_6464.bmp", 'id-img': 0 });
	pre_vi.find('.dmsTab span').text('').each(function () {
		$(this).last().attr('type', '')
	})
	pre_vi.find(`[id^=msg]`).children().each(function () {
		$(this).attr('msg', '').children().each(function () {
			$(this).find('[id*=box]').text("");
		})
	})

	$(`.equip-info.equip1`).addClass('active').siblings().removeClass('active');

	$('input[id^=timerCheck]').prop('disabled', true);
	$('input[id^=timerPage]').prop('disabled', true);

	selectMessage();
	updateMessage();
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

// init table
const init = () => {
	// get all message
	let table = $('.nameColumn + td.pageTable1')
	table.each(function () {
		let tr = $(this).parent()
		let pagination = $('.edit-pmv-page')

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
		$(`#page-pmv .equip1`).addClass('active');
		tr.click(function () {
			let morePage = true;
			// get all page with time > 0
			let pages = $(this).find('td:not(td[timer="0.0"])').add($(this).find('.pageTable1'));

			for (let i = 1; i <= 5; i++) {
				// page
				const page = pages.filter('.pageTable' + i);
				// Verification
				const verif = page.filter('td[active]').attr('active');
				// page originpager
				const originpager = page.filter('td[originpager]').attr('originpager');
				// page timer
				let timer = page.filter('td[timer]').attr('timer');
				// Pré visualização
				let pre_vi = $(`#page-pmv .equip${i}`);
				// get first message on page
				let msg = page.filter('.msgPage1')
				pagination.addClass('active').find('#btn-page1').prop('checked', true);
				if (i == 1) {
					morePage = true;
					pre_vi.addClass('active');
				}
				else
					pre_vi.removeClass('active');
				// add information on page
				if (verif) {
					// Add image
					pre_vi.find('.picture-box').attr({ src: page.find('[id*=picture-table]').attr('src'), 'id-img': page.find('[id*=picture-table]').next().val() })
					// Add ID and NAME
					pre_vi.find('.dmsTab span#dmsId').text(`${pages.filter('.idColumn').text()}`).next().text(`${pages.find('.tablePageName').text()}`)
					// add messages
					pre_vi.find(`#msg${i}`).children().each(function () {
						$(this).attr('msg', msg.text()).children().each(function (index) {
							if ((msg.text().length - index) > 0)
								$(this).find('[id*=box]').text(msg.text()[index])
							else
								$(this).find('[id*=box]').text("")
						})
						msg = msg.next()
					})
					// add Type
					pre_vi.find('.dmsTab span#dmsType').text(pages.filter('[type]').text()).attr('type', pages.filter('[type]').attr('type'))

					// disable button if morePage if false
					if (morePage && page.length) {
						// add check page
						pagination.find(`#timerCheck${originpager}`).prop('checked', true);
						pagination.find(`#btn-page${originpager}`).removeAttr('disabled');
					} else {
						// remove check page
						pagination.find(`#timerCheck${originpager}`).prop('checked', false);
						pagination.find(`#btn-page${originpager}`).attr('disabled', 'disabled');
						morePage = false;
					}
					// add Timers
					pagination.find(`#timerPage${originpager}`).val(timer);

				} else {
					// clean table void
					pre_vi.find('.picture-box').attr('src', "/resources/images/pictures/000_6464.bmp")
					pre_vi.find('.dmsTab span#dmsId').text(`${pages.filter('.idColumn').text()}`).next().text(``).next().text('').attr('type', '')
					pre_vi.find(`#msg${i}`).children().each(function () {
						$(this).attr('msg', '').children().each(function () {
							$(this).find('[id*=box]').text("")
						})
					})
					pagination.find(`#timerCheck${i}`).prop('checked', false);
					pagination.find(`#btn-page${i}`).attr('disabled', 'disabled');
					pagination.find(`#timerPage${i}`).val(0);
					morePage = false;
				}
			}

			$('#btnEdit').prop('disabled', false);
			$('#btnDelete').prop('disabled', false);

			// select message
			selectMessage();
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

			$('.equip-info.active').find(`.message${id}`).attr('msg', textFull).find(`div > span[id^=box]`)[idx].innerText = char;
		}
	}
	for (let index = 11; index >= textFull.length; index--) {
		$('.equip-info.active').find(`.message${id} div > span[id^=box]`)[index].innerText = "";
	}
}

$(function () {
	// request table
	$("#tabelaReal").load('/dms/messages/message-full.xhtml', () => {
		// Main loading
		init();
	})

	// change and pre-save page
	$('.edit-pmv-page').on('click', 'label', function () {
		$(`.equip-info.equip${$(this).text()}`).addClass('active').siblings().removeClass('active');

		updateMessage();

	}).find('[id^=timerCheck]').change(function () {
		// disable or enable other page
		let check = $(this);
		if (check.prop('checked'))
			check.parent().next().prop('disabled', false).next().prop('disabled', false)
				.parent().parent().next().find('input[id^=timerCheck]').prop('disabled', false);
		else
			check.parent().next().prop('disabled', true).next().prop('disabled', true)
				.parent().parent().next().find('input[id^=timerCheck]').prop({ disabled: true, checked: false }).trigger('change');
		$('[id^=timerPage]').each(function () {
			if (!$(this).prop('disabled'))
				$(this).val(function () { return Number($(this).val()) || 1; });
		})
		selectMessage();
	})

	// hidden img and open list
	$('#image-div').click(function () {
		$(this).css('display', 'none').next().css('display', 'block').find('div > img[id-img]').on('dragstart', e => { e.preventDefault() })
	})

	// change field image
	$('#list-images').on('click', 'div > img[id-img]', function (e) {
		e.currentTarget.ondragstart = function () { return false }
		$('.equip-info.active').find('#child-img img[id-img]').attr({ src: $(this).attr('src'), 'id-img': $(this).attr('id-img') })
		$('#list-images').css('display', 'none').prev().css('display', 'block')
		selectMessage();
	})

	// change field type
	$('#type-input').change(function () {
		$('.equip-info.active').find('.dmsTab span#dmsType').attr('type', $(this).val()).text($(this).find('option:selected').text())
		selectMessage();
	})
	// change field name
	$('#name-input').on('keyup change', function () {
		$('.equip-info.active').find('.dmsTab span#dmsName').text($(this).val())
		selectMessage();
	})
	for (let line = 1; line <= 3; line++) {
		// change field msg line
		$(`#message-box${line}`).on('keyup change', function () {
			upTextToChar($(this), line);
			selectMessage();
		})
	}
	// if change timer
	$('[id^=timerPage]').on('keyup change', function () {
		selectMessage();
	})

	toast = new bootstrap.Toast(document.getElementById('liveToast'))
	modal = new bootstrap.Modal(document.getElementById('deleteModal'))

	$('#btnCreate').click(newMsg);
	$('#btnEdit').click(editMsg);
	$('#btnCr2').click(cancel);
})