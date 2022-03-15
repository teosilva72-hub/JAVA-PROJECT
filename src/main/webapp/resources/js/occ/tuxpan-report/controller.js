$(init => {
    table()
    vehInvAppend()
})

function table() {
    $(document).ready(function() {
        $('#occurrence-table').DataTable({
            language: {
                "search": "",
                searchPlaceholder: "Search"
            },
            "select": false,
            "Width": true,
            // "scrollY": "55vh",
            "scrollCollapse": false,
            "paging": false,
            "bInfo": false,
            fixedColumns: true
        })
    });
}

function vehInvAppend() {
    let add = $('#btn_append')
    let vehInv = 'vehInv'
    let eje = 'eje'
    add.click(e => {
        let x = $('#vehInv_append')
            .append(`
			<div class="col-5">
				<div class="input-group has-validation">
					<div class="input-group-prepend">
						<span class="input-group-text">Tipo de Vehículo</span>
					</div>
					<input type="text" class="form-control ${vehInv}" />							
				</div>
			</div>
			<div class="col-5">
				<div class="input-group has-validation">
					<div class="input-group-prepend">
						<span class="input-group-text">Nº Ejes</span>
					</div>
					<input type="text" class="form-control ${eje}" />							
				</div>
			</div>					
		`)
    })
}
function addTipo(){
	let add = $('#btn_append_veh')
	add.click(e => {
		$('.addNum').append(`<input type="text" value="" id="num_veh" name="num_veh"/>`)
		$('.addMarca').append(`<input value="" type="text" id="marca_veh" name="marca_veh"/>`)
		$('.addTipo').append(`<input value="" type="text" id="tipo_veh" name="tipo_veh"/>`)
		$('.addModelo').append(`<input value="" type="text" id="modelo_veh" name="modelo_veh"/>`)
		$('.addCor').append(`<input value="" type="text" id="cor_veh" name="cor_veh"/>`)
		$('.addPlaca').append(`<input value="" type="text" id="placa_veh" name="placa_veh"/>`)
		$('.addTel').append(`<input value="" type="text" id="tel_veh" name="tel_veh"/>`)
	})
	
}