$(init => {
    table()
    vehInvAppend()
    addTipo()
	datos_person()
})

let vehInv = 'vehInv'
let result = 0;
function getVehInv(){
	let dados = []
	let input = $('#vehInv_append input')
	for(var i = 0; i < input.length; i++){
		dados[i] = input.get(i).value
		
	}
	return dados
}
function getTipoVeh(){
	let dados = []
	let input = $('#tiposVeh input')
	for(var i = 0; i < input.length; i++){
		dados[i] = input.get(i).value
		
	}
	return dados
}
function getPerson(){
let dados = []
	let input = $('#datos_person input')
	for(var i = 0; i < input.length; i++){
		dados[i] = input.get(i).value
		
	}
	return dados

}
function datos_person(){
	let add = $('#btn_append_person')
	let person = "dt_person"
    add.click(e => {
        let input = $('#datos_person input').length - 2
        for (var i = input; i <= input; i++) {
            result = i
        }
        let cols = $(`
			<div class="col-1 ${person}${result}">
            	<h:inputText value="" name="id_person" class="form-control" />
            </div>
            <div class="col-4 ${person}${result}">
            	<h:inputText value="" name="nombre_person" class="form-control" />
            </div>
            <div class="col-2 ${person}${result}">
            	<h:inputText value="" name="edad_person" class="form-control" />
            </div>
            <div class="col-4 ${person}${result}">
            	<h:inputText value="" name="codiciones_person" class="form-control" />
            </div>
            <div class="col-1 removeDatos ${person}${result}"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-archive-fill" viewBox="0 0 16 16"><path d="M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z"/></svg></div>					
		`)
        $('#datos_person').append(cols)
        cols.filter(`.removeDatos.${person}${result}`).click(() => cols.remove())

    })
	
}

function vehInvAppend() {
    let add = $('#btn_append')
    add.click(e => {
        let input = $('#vehInv_append input').length - 2
        for (var i = input; i <= input; i++) {
            result = i
        }
        let invVeh = $(`
			<div class="col-5 ${vehInv}${result}">
				<div class="input-group has-validation">
					<div class="input-group-prepend">
						<span class="input-group-text">Tipo de Vehículo</span>
					</div>
					<input type="text" class="form-control " />							
				</div>
			</div>
			<div class="col-5 ${vehInv}${result}">
				<div class="input-group has-validation">
					<div class="input-group-prepend">
						<span class="input-group-text">Nº Ejes</span>
					</div>
					<input type="text" class="form-control" />							
				</div>
			</div>
            <div class="col-2 removeVEH ${vehInv}${result}"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-archive-fill" viewBox="0 0 16 16"><path d="M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z"/></svg></div>					
		`)
        $('#vehInv_append').append(invVeh)
        invVeh.filter(`.removeVEH.${vehInv}${result}`).click(() => invVeh.remove())

    })

}

function addTipo() {
    let add = $('#btn_append_veh')
    let tipo = "tipo"
    add.click(e => {
        for (var i = 0; i <= $('.addNum input').length; i++) {
            result = i
        }

        let tipoVeh = $(`
            <div class="col-1 addNum ${tipo}${result}">
            <input type="text" value=""  name="num_veh" class="form-control" />
            </div>
            <div class="col-1 addMarca ${tipo}${result}">
            <input type="text" value="" iname="marca_veh" class="form-control" />
            </div>
            <div class="col-1 addTipo ${tipo}${result}">
            <input type="text" value=""  name="tipo_veh" class="form-control" />
            </div>
            <div class="col-2 addModelo ${tipo}${result}">
            <input type="text" value=""  name="modelo_veh" class="form-control" />
            </div>
            <div class="col-2 addCor ${tipo}${result}">
            <input type="text" value=""  name="cor_veh" class="form-control" />
            </div>
            <div class="col-2 addPlaca ${tipo}${result}">
            <input type="text" value=""  name="placa_veh" class="form-control" />
            </div>
            <div class="col-2 addTel ${tipo}${result}">
            <input type="text" value=""  name="tel_veh" class="form-control" />
            </div>
            <div class="col-1 removeTVEH ${tipo}${result}"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-archive-fill" viewBox="0 0 16 16"><path d="M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z"/></svg></div>					

        `)
			
        $('#tiposVeh').append(tipoVeh)
        tipoVeh.filter(`.removeTVEH.${tipo}${result}`).click(() => tipoVeh.remove())
    })

}

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