var msg;

// implementation 'loopNext' in jquery
$.fn.loopNext = function (selector) {
	var selector = selector || '';
	return this.next(selector).length ? this.next(selector) : this.siblings(selector).addBack(selector).first();
}

const selectMessage = () => {
	msg = {
		id: $(`.equip-info.equip1`).find('.dmsTab span#dmsId').text(),
		pages: []
	}

	for (let i = 1; i <= 5; i++) {
		let info = $(`.equip-info.equip${i}`)
		msg.pages.push({
			type: info.find('.dmsTab span#dmsType').attr('type'),
			name: info.find('.dmsTab span#dmsName').text(),
			image: info.find('	#child-img img').attr('src'),
			text: [
				info.find('#child-msg .message1').attr('msg'),
				info.find('#child-msg .message2').attr('msg'),
				info.find('#child-msg .message3').attr('msg')
			]
		})
	}
}

const updateMessage = () => {
	$('#type-input').val(msg.pages[$('.equip-info.active').index()].type)
	$('#name-input').val(msg.pages[$('.equip-info.active').index()].name)
	$('#message-box1').val(msg.pages[$('.equip-info.active').index()].text[0])
	$('#message-box2').val(msg.pages[$('.equip-info.active').index()].text[1])
	$('#message-box3').val(msg.pages[$('.equip-info.active').index()].text[2])
}

const newMsg = () => {
	$('#btnCreate').prop('disabled', true);
	$('#btnEdit').prop('disabled', true);
	$('#btnDelete').prop('disabled', true);
	$('#btnCr1').prop('disabled', false);
	$('#btnCr2').prop('disabled', false);
	$('#disableTable').addClass('active');
	$('.edit-field').addClass('active');
	$('.edit-pmv-page').addClass('active')
		.find(`[id^=timerPage]`).val(0)
		.first().prev().find('[id^=timerCheck]')
		.prop('checked', false).trigger('change');
	let pre_vi = $(`#page-pmv .equip-info`);
	pre_vi.find('.picture-box').attr('src', "/resources/images/pictures/000_6464.bmp");
	pre_vi.find('.dmsTab span').text('').each(function () {
		$(this).last().attr('type', '')
	})
	pre_vi.find(`[id^=msg]`).children().each(function () {
		$(this).attr('msg', '').children().each(function () {
			$(this).find('[id*=box]').text("");
		})
	})

	$(`.equip-info.equip1`).addClass('active').siblings().removeClass('active');

	$('input[id^=timerCheck]').prop('disabled', false).trigger('change');

	selectMessage();
	updateMessage();
}

const cancel = () => {
	$('#btnCreate').prop('disabled', false);
	$('#btnCr1').prop('disabled', true);
	$('#btnCr2').prop('disabled', true);
	$('#disableTable').removeClass('active');
	$('.edit-pmv-page').removeClass('active');
	$('.edit-field').removeClass('active');
	let pre_vi = $(`#page-pmv .equip-info`);
	pre_vi.find('.picture-box').attr('src', "/resources/images/pictures/000_6464.bmp");
	pre_vi.find('.dmsTab span').text('').each(function () {
		$(this).last().attr('type', '')
	})
	pre_vi.find(`[id^=msg]`).children().each(function () {
		$(this).attr('msg', '').children().each(function () {
			$(this).find('[id*=box]').text("");
		})
	})

	$(`.equip-info.equip1`).addClass('active').siblings().removeClass('active');

	$('input[id^=timerCheck]').prop('disabled', true).trigger('change');

	selectMessage();
	updateMessage();
}

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

const init = () => {
	let table = $('.idColumn + td.pageTable1')
	table.each(function () {
		let tr = $(this).parent()
		let pagination = $('.edit-pmv-page')

		// Start rotation for tables
		changeMsg($(this));


		// Add on click pre-visualization
		$(`#page-pmv .equip1`).addClass('active');
		tr.click(function () {
			let morePage = true;
			// get all page with time > 0
			let pages = $(this).find('td:not(td[timer="0.0"])');

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
					pre_vi.find('.picture-box').attr('src', page.find('[id*=picture-table]').attr('src'))
					// Add ID and NAME
					pre_vi.find('.dmsTab span#dmsId').text(`${pages.filter('.idColumn').text()}`).next().text(`${page.find('.tablePageName').text()}`)
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
					pre_vi.find('.dmsTab span#dmsType').text(page.filter('[type]').text()).attr('type', page.filter('[type]').attr('type'))

					// disable button if morePage if false
					if (morePage && page.length) {
						// add check page
						pagination.find(`#timerCheck${originpager}`).prop('checked', true);
						pagination.find(`#btn-page${originpager}`).removeAttr('disabled');
					} else {
						pagination.find(`#timerCheck${originpager}`).prop('checked', false);
						pagination.find(`#btn-page${originpager}`).attr('disabled', 'disabled');
						morePage = false;
					}
					// add Timers
					pagination.find(`#timerPage${originpager}`).val(timer);

				} else {
					pre_vi.find('.picture-box').attr('src', "/resources/images/pictures/000_6464.bmp")
					pre_vi.find('.dmsTab span#dmsId').text(``).next().text(``).next().text('').attr('type', '')
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

			selectMessage();
		})
	})

	// load first table
	tableRender();
}

// func to start rotation tables. Only tables
const changeMsg = msg => {
	if (msg.index()) {
		let timer = Number(msg.attr("timer"));
		let name = msg.loopNext();
		let img = name.loopNext();
		let text1 = img.loopNext();
		let text2 = text1.loopNext();
		let text3 = text2.loopNext();
		let pg = text3.loopNext();
		if (timer) {
			msg.addClass('active');
			name.addClass('active');
			img.addClass('active');
			text1.addClass('active');
			text2.addClass('active');
			text3.addClass('active');
			pg.addClass('active');
			setTimeout(() => {
				msg.removeClass('active');
				name.removeClass('active');
				img.removeClass('active');
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
	$("#tabelaReal").load('/dms/messages/message-full.xhtml', () => {
		// Main loading
		init();
	})

	$('.edit-pmv-page').on('click', 'label', function () {
		selectMessage();

		$(`.equip-info.equip${$(this).text()}`).addClass('active').siblings().removeClass('active');

		updateMessage();

	}).find('[id^=timerCheck]').change(function () {
		let check = $(this);
		if (check.prop('checked'))
			check.parent().next().prop('disabled', false).next().prop('disabled', false)
				.parent().parent().next().find('input[id^=timerCheck]').prop('disabled', false);
		else
			check.parent().next().prop('disabled', true).next().prop('disabled', true)
				.parent().parent().next().find('input[id^=timerCheck]').prop({ disabled: true, checked: false }).trigger('change');
	})

	// change field type
	$('#type-input').change(function () {
		$('.equip-info.active').find('.dmsTab span#dmsType').attr('type', $(this).val()).text($(this).find('option:selected').text())
	})
	// change field name
	$('#name-input').keyup(function () {
		$('.equip-info.active').find('.dmsTab span#dmsName').text($(this).val())
	})
	// change field msg1
	$('#message-box1').keyup(function () {
		upTextToChar($(this), 1);
	})
	// change field msg2
	$('#message-box2').keyup(function () {
		upTextToChar($(this), 2);
	})
	// change field msg3
	$('#message-box3').keyup(function () {
		upTextToChar($(this), 3);
	})

	$('#btnCreate').click(newMsg);
	$('#btnCr2').click(cancel);
})