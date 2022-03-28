$(init =>{
	table()
})
function dadosTable(msg1, msg2, msg3, img, name){
	
	
	let msg = []
	for(var i=0;i<msg1.length;i++){

    msg[i] = msg1[i]
	$(`#box-${i}`).text(msg[i])
	}
	for(var i=0;i<msg2.length;i++){

    msg[i] = msg2[i]
	$(`#Msg_${i}`).text(msg[i])
	}
	for(var i=0;i<msg3.length;i++){

    msg[i] = msg3[i]
	$(`#msg_${i}`).text(msg[i])
	}
}
function table() {
  let table = $("#table-hit").DataTable({
    language: {
      search: "",
      searchPlaceholder: "Search",
    },
    select: false,
    Width: true,
    scrollY: "55vh",
    scrollCollapse: false,
    paging: false,
    bInfo: false,
    fixedColumns: true,
  });
  $("#table-hit tbody").on("click", "tr", function () {
    var id = $(table.row(this).data()[0]).text();
    $('#tableId').val(id)
	setTimeout(g =>{
		$('#setId').click()
	},100)
  });
}
