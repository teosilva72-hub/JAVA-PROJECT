function showImageInfo() {
	document.getElementById("image-div-info").style.display = "block";
}
function hideImageInfo() {
	document.getElementById("image-div-info").style.display = "none";
}
function hideLinkInfo() {
	document.getElementById("image-div-link").style.display = "none";
}
function refresh() {
	setTimeout("location.reload(true);", 3000);
}
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
function imageDisabled() {// Todo: Talvez elimine esse
	document.getElementById("image-place").style.opacity = "0.4";
}
function imageEnabled() { // Todo: Talvez elimine esse
	document.getElementById("image-place").style.opacity = "1";
}
function dialogHide() {
	var dialog = document.getElementById("deleteModal");
	dialog.modal("hide");
}
function showRefreshIcon() {
	document.getElementById("table-refresh").style.display = "block";
}
// ESCONDER TABELAS
function bloquerTable() {// Todo: Talvez elimine esse
	// ESCONDER A TABELA REAL
	document.getElementById("tabelaReal").style.display = "none";
	// TRAZER A TABELA OCULTA BLOQUEADA
	var element = document.getElementById("tabelaHidden");
	element.classList.remove("trazerTable");
}


// Id do inputHidden para passar valor para pmvMessage (Bean)		 
var idMessage, selected;

// Método para Criar a datatable JQuery (Quando for selection será definida na página)
function listTable() {
	$(document).ready(function () {

		var table = $('#message-table').DataTable({
			select: true,
			"autoWidth": true,
			"scrollY": "65vh",
			"scrollCollapse": true,
			"paging": false,
			"bInfo": false,
			"columnDefs": [{
				"width": "7%",
				"targets": 0
			}, {
				"width": "20%",
				"targets": 1
			}, {
				"width": "20%",
				"targets": 2
			}, {
				"width": "3%",
				"targets": 3
			}, {
				"width": "30%",
				"targets": 4
			}]
		});

		$('.dataTables_length').removeClass('bs-select');
	});
}
function listTableHidden() {
	$(document).ready(function () {

		$('#message-table-hidden').DataTable({
			select: false,
			"autoWidth": true,
			"scrollY": "65vh",
			"scrollCollapse": true,
			"paging": false,
			"bInfo": false,
			"columnDefs": [{
				"width": "7%",
				"targets": 0
			}, {
				"width": "20%",
				"targets": 1
			}, {
				"width": "20%",
				"targets": 2
			}, {
				"width": "3%",
				"targets": 3
			}, {
				"width": "30%",
				"targets": 4
			}]
		});
	});
}
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
		var tableData = $(this).children("td").map(function () {
			return $(this).text();
		}).get();

		idMessage = tableData[0]; // td posição 0 da row (Primeira posição)

		// alert(idMessage); //Mostrar id   

		// Chamar Action do Botão Hidden
		document.getElementById('formId:hdnBtn').click();

		// Passar o id para dialog
		$('#selectedId').text(idMessage);

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

// $(function () {
// 	setTimeout(function () {
// 		listTable();
// 		listTableHidden();
// 		selectList();
// 		getMessageId();
// 		hideMsg();
// 	}, 10)
// });