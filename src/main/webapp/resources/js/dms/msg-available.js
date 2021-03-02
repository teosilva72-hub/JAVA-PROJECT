function hideID() {
	document.getElementById("id-div").style.display = "none";
	// document.getElementById("space").style.display = "block";	
	// document.getElementById("space1").style.display = "block";				 
}
function showID() {
	document.getElementById("id-div").style.display = "block";
	// document.getElementById("space").style.display = "none";	
	// document.getElementById("space1").style.display = "none";				
}
function changeDIV() {
	document.getElementById('image-div').style.display = "none";
	document.getElementById('list-images').style.display = "block";
}
function returnDIV() {
	document.getElementById('image-div').style.display = "block";
	document.getElementById('list-images').style.display = "none";

}
function dialogHide() {
	var dialog = document.getElementById("deleteModal");
	dialog.modal("hide");
}

// Id do inputHidden para passar valor para pmvMessage (Bean)		 
var idMessage, selected;

function selectList() {
	// Método para pegar id da mensagem na seleção de uma row
	$('#message-table tbody').on('click', 'tr', function () {
		// Get table row
		var row = $(this);

		// Check if table row is selected on not (Change state) (true or false)
		if (!row.hasClass('selected'))
			selected = true;
		else
			selected = false;

		// Get table id on select Row		  
		var tableData = $(this).children("td").first().text();

		// Chamar Action do Botão Hidden
		document.getElementById('formId:hdnBtn').click();

		// Passar o id para dialog
		$('#selectedId').text(tableData);

	});
}

// Método para passar o valor selecionado
function getMessageId() {
	document.getElementById("formId:idMessage").value = idMessage;
	document.getElementById("formId:checked").value = selected;
}

// Check if row is selected or not
function checkRowSelected() {
	if (!table.data().any())
		selected = false
	else
		selected = true;
}
function hideMsg() {
	$("#message-display").delay(1000).hide(1000);
}

// implementation 'loopNext' in jquery
$.fn.loopNext = function (selector) {
	var selector = selector || '';
	return this.next(selector).length ? this.next(selector) : this.siblings(selector).addBack(selector).first();
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

$(function () {
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
					pre_vi.find('.dmsTab p').text(`${pages.filter('.idColumn').text()} - ${page.find('.tablePageName').text()}`)
					// add messages
					pre_vi.find(`#msg${i}`).children().each(function () {
						$(this).children().each(function (index) {
							if ((msg.text().length - index) > 0)
								$(this).find('[id*=box]').text(msg.text()[index])
							else
								$(this).find('[id*=box]').text("")
						})
						msg = msg.next()
					})

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
					pre_vi.find('.dmsTab p').text('')
					pre_vi.find(`#msg${i}`).children().each(function () {
						$(this).children().each(function () {
							$(this).find('[id*=box]').text("")
						})
						msg = msg.next()
					})
					pagination.find(`#timerCheck${i}`).prop('checked', false);
					pagination.find(`#btn-page${i}`).attr('disabled', 'disabled');
					pagination.find(`#timerPage${i}`).val(0);
					morePage = false;
				}
			}
		})

		// Pagination with buttons
		pagination.on('click', 'label', function () {
			$(`.equip-info.equip${$(this).text()}`).addClass('active').siblings().removeClass('active')
		})
	})

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
})

selectList();
getMessageId();
hideMsg();