$((init) => {
    table();
    appendOcc();
    clickSave();
    getTypeReport();
    hiddenBts();
    clickUpdate();
    blockVirgula();
    formSin();
    divFile();
    saveSin();
	setFile()
    pdf();
	$('#type_occ, #type_sin').click(()=>{
	    $('.divFile').addClass('hidden')
	})
    setTimeout((a) => {
        vehOcupSin();
    }, 100);
    $("#modalOcc, #modalSin").on("hidden.bs.modal", () => {
        $("#resetFormSin, #resetFormOcc").click();
        scann();
        divFile();
    });

    $("#file").change(() => {
        $("#copyFile").val($("#file").val());
    });
});

function pdf() {
    $("[id$=downloadPdf]").click((e) => {
        setTimeout((x) => {
            $("[id$=pdfDownload]").click();
            $("#type_occ, #type_sin").click(() => {
                appendOcc();
                vehOcupSin();
            });
        }, 200);
    });
}

function setFile() {

    $(".getFile, .getFiles").change(() => {
	    let file = document.querySelector(".getFile").files.length;
	    let files = document.querySelector(".getFiles").files.length;
        if (file > 0 || files > 0) {
            $("#saveOcc").click(() => {
                setTimeout(() => {
                    $("#setFile").click();
                }, 150);
            });
            $("#sinSave").click(() => {
                setTimeout(() => {
                    $("#setFileS").click();
                }, 150);
            });
        }
    });
}

function divFile() {
    $(".file").click(() => {
        setTimeout(() => {
            $(".divForm").addClass("hidden");
            $(".divFile").removeClass("hidden");
        }, 100);
    });
    $(".tituloModal").click(() => {
        setTimeout(() => {
            $(".divForm").removeClass("hidden");
            $(".divFile").addClass("hidden");
        }, 50);
    });
}

function formSin() {
    $("#type_sin").click((a) => {
        getObsSin();
        blockVirgula();
        hiddenBts();
    });
}

function editInfos() {
    //sin
    $("#get_folio_secS").val($("#folio_rsa").val());
    $("#get_siniestroS").val($("#sinistro_sin").val());
    $("#get_folio_secs").val($("#folio_rsa").val());
    $("#get_siniestros").val($("#sinistro_sin").val());
    $("#sinReporte").val("-");
    //occ
    $("#get_folio_sec").val($("#folio_secuencial").val());
    $("#get_report").val($("#reporte").val());
    $("#get_siniestro").val($("#siniestro").val());
    //
    $("#folio_secFile").val($("#folio_secuencial").val());
    $("#reporteFile").val($("#reporte").val());
    $("#siniestroFile").val($("#siniestro").val());

    $("#sinistro_sin").prop("readonly", true);
    $("#folio_rsa").prop("readonly", true);
    $("#folio_secuencial").prop("readonly", true);
    $("#reporte").prop("readonly", true);
    $("#siniestro").prop("readonly", true);
}

function scann() {
    $("#saveSin").prop("disabled", true);
    $("#occSave").prop("disabled", true);
    const id = $(
        "#reporte, #siniestro, #folio_secuencial, #sinistro_sin, #folio_rsa"
    );
    $(id).change((e) => {
        $("#occSave", "#saveSin").prop("disabled", true);
        if ($("#type_report").val() == "1") {
            if (
                $("#folio_secuencial").val() == "".trim() ||
                $("#siniestro").val() == "".trim() ||
                $("#reporte").val() == "".trim()
            ) {
                $("#occSave").prop("disabled", true);
            } else {
                $("#occSave").prop("disabled", false);
                $("#get_folio_sec").val($("#folio_secuencial").val());
                $("#get_report").val($("#reporte").val());
                $("#get_siniestro").val($("#siniestro").val());
                //
                $("#folio_secFile").val($("#folio_secuencial").val());
                $("#reporteFile").val($("#reporte").val());
                $("#siniestroFile").val($("#siniestro").val());
            }
        } else if ($("#type_reports").val() == "2") {
            if (
                $("#sinistro_sin").val() == "".trim() ||
                $("#folio_rsa").val() == "".trim()
            ) {
                $("#saveSin").prop("disabled", true);
            } else {
                $("#saveSin").prop("disabled", false);
                $("#get_folio_secS").val($("#folio_rsa").val());
                $("#get_siniestroS").val($("#sinistro_sin").val());
                $("#get_folio_secs").val($("#folio_rsa").val());
                $("#get_siniestros").val($("#sinistro_sin").val());
                $("#sinReporte").val("-");
            }
        }
    });
}

function blockVirgula() {
    $("#vehInv_append input").on("input", function(a) {
        $(this).val($(this).val().replace(/,/g, ""));
    });
    $("#tiposVeh input").on("input", function(b) {
        $(this).val($(this).val().replace(/,/g, ""));
    });
    $("#datos_person input").on("input", function(c) {
        $(this).val($(this).val().replace(/,/g, ""));
    });
    $("#add_append_sin input").on("input", function(d) {
        $(this).val($(this).val().replace(/,/g, ""));
    });
    $("#getObs input").on("input", function(d) {
        $(this).val($(this).val().replace(/,/g, ""));
    });
}

function appendOcc() {
    vehInvAppend();
    addTipo();
    datos_person();
}

function hiddenBtnSin() {
    $("#saveSin").addClass("hidden");
    $("#saveUpdate").removeClass("hidden");
    blockVirgula();
}

function hiddenBts() {
    $("[id$=editocc]").click((a) => {
        $("#updateOcc").removeClass("hidden");
        $("#occSave").addClass("hidden");
        setTimeout(() => {
            editInfos();
        }, 200);
    });
    $("[id$=type_occ]").click((b) => {
        //$('[id$=form_occ] input').val('')
        $("#updateOcc").addClass("hidden");
        $("#occSave").removeClass("hidden");
        scann();
    });
    $("[id$=type_sin]").click((c) => {
        $("#saveSin").removeClass("hidden");
        $("#saveUpdate").addClass("hidden");
        scann();
    });
}

function clickUpdate() {
    $("#updateOcc").click(() => {
        getVehInvChilds();
        let file = document.querySelector(".getFile").files.length;
        //setTimeout(() => {
            $("[id$=OccUpdate]").click();
            setTimeout(() => {
                if (file > 0) {
                    $("#setFile").click();
                }
            }, 150);
       // }, 100);
    });
    $("#saveUpdate").click(() => {
        let files = document.querySelector(".getFiles").files.length;
        getVehOcupSin();
        getObsSin();
        //setTimeout(d => {
        $("[id$=updateSin]").click();
        setTimeout(() => {
            if (files > 0) {
                $("#setFileS").click();
            }
        }, 150);
        //}, 100)
    });
}

function getTypeReport() {
    $("#type_occ").click((a) => {
        $("#type_report").val("1");
    });
    $("#type_sin").click((b) => {
        $("#type_reports").val("2");
    });
}

function clickSave() {
    $("#occSave").click((e) => {
        getTipoVeh();
        getVehInv();
        getPerson();
        setTimeout((f) => {
            $("[id$=saveOcc]").click();
            appendOcc();
            listOcc();
        }, 100);
    });
}

function saveSin() {
    console.log("*");
    $("#saveSin").click((a) => {
        getVehOcupSin();
        getObsSin();

        setTimeout((b) => {
            $("#sinSave").click();

            listSin();
        }, 50);
    });
}

function listSin() {
    $("[id$=list_sin]").click();
}

function listOcc() {
    $("[id$=list_occ]").click();
}

function listTable() {
    $("#list_occ").click();
}
let btn_sin = $(`
	<div class="col-2">
	<a id="btn_veh_ocup"><svg xmlns="http://www.w3.org/2000/svg"
	width="16" height="16" fill="currentColor"
	class="bi bi-plus-circle" viewBox="0 0 16 16"> <path
	d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z" />
	<path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z" /></svg> </a></div>
`);
let vehInv = "vehInv";
let result = 0;

function changeFilds() {}

function getVehInv() {
    let dados = [];
    let input = $("#vehInv_append .tipo_veh_inv");
    for (var i = 0; i < input.length; i++) {
        dados[i] = input.get(i).value;
        if (dados[i] == "") dados[i] = "-";
    }
    $("#tipo_veh_inv").val(dados);
    //
    input = $("#vehInv_append .ejes");
    for (var i = 0; i < input.length; i++) {
        dados[i] = input.get(i).value;
        if (dados[i] == "") dados[i] = "-";
    }
    $("#num_eje_veh_inv").val(dados);
    //
    return dados;
}

function getVehOcup() {
    let dados = [];
    let input = $("#add_append_sin input");
    for (var i = 0; i < input.length; i++) {
        dados[i] = input.get(i).value;
        if (dados[i] == "") dados[i] = "-";
    }
    return dados;
}

function getTipoVeh() {
    let dados = [];
    let input = $("#tiposVeh .num_");
    for (var i = 0; i < input.length; i++) {
        dados[i] = input.get(i).value;
        if (dados[i] == "") dados[i] = "-";
    }
    $("#num_veh").val(dados);
    //
    input = $("#tiposVeh .marca_");
    for (var i = 0; i < input.length; i++) {
        dados[i] = input.get(i).value;
        if (dados[i] == "") dados[i] = "-";
    }
    $("#marca_veh").val(dados);
    //
    input = $("#tiposVeh .tipo_");
    for (var i = 0; i < input.length; i++) {
        dados[i] = input.get(i).value;
        if (dados[i] == "") dados[i] = "-";
    }
    $("#tipo_veh").val(dados);
    //
    input = $("#tiposVeh .modelo_");
    for (var i = 0; i < input.length; i++) {
        dados[i] = input.get(i).value;
        if (dados[i] == "") dados[i] = "-";
    }
    $("#modelo_veh").val(dados);
    //
    input = $("#tiposVeh .color_");
    for (var i = 0; i < input.length; i++) {
        dados[i] = input.get(i).value;
        if (dados[i] == "") dados[i] = "-";
    }
    $("#cor_veh").val(dados);
    //
    input = $("#tiposVeh .placa_");
    for (var i = 0; i < input.length; i++) {
        dados[i] = input.get(i).value;
        if (dados[i] == "") dados[i] = "-";
    }
    $("#placa_veh").val(dados);

    //
    input = $("#tiposVeh .tel_");
    for (var i = 0; i < input.length; i++) {
        dados[i] = input.get(i).value;
        if (dados[i] == "") dados[i] = "-";
    }
    $("#tel_veh").val(dados);
    //
    return dados;
}

function getPerson() {
    let dados = [];
    let input = $("#datos_person .id_person");
    for (var i = 0; i < input.length; i++) {
        dados[i] = input.get(i).value;
        if (dados[i] == "") dados[i] = "-";
    }
    $("#id_person").val(dados);
    //
    input = $("#datos_person .nombre_person");
    for (var i = 0; i < input.length; i++) {
        dados[i] = input.get(i).value;
        if (dados[i] == "") dados[i] = "-";
    }
    $("#nombre_person").val(dados);
    //
    input = $("#datos_person .edad_person");
    for (var i = 0; i < input.length; i++) {
        dados[i] = input.get(i).value;
        if (dados[i] == "") dados[i] = "-";
    }
    $("#edad_person").val(dados);
    //
    input = $("#datos_person .cond_person");
    for (var i = 0; i < input.length; i++) {
        dados[i] = input.get(i).value;
        if (dados[i] == "") dados[i] = "-";
    }
    $("#condiciones_person").val(dados);
    //
    return dados;
}

function getVehOcupSin() {
    let dados = [];
    let input = $("#add_append_sin .ocupSin");
    for (var i = 0; i < input.length; i++) {
        dados[i] = input.get(i).value;
        if (dados[i] == "") dados[i] = "-";
    }
    $("#ocupantesSIN").val(dados);
    //
    input = $("#add_append_sin .vehSin");
    for (var i = 0; i < input.length; i++) {
        dados[i] = input.get(i).value;
        if (dados[i] == "") dados[i] = "-";
    }
    $("#vehOcupantesSIN").val(dados);
    //
    return dados;
}

function getObsSin() {
    let dados = [];
    let input = $("#getObs .obs_val");
    for (var i = 0; i < input.length; i++) {
        dados[i] = input.get(i).value;
        if (dados[i] == "") dados[i] = "-";
    }
    $("#obsGets").val(dados);

    //
    return dados;
}

function vehOcupSin() {
    //$('#add_append_sin').append(btn_sin)
    let add = $("#btn_veh_ocup");
    let veh_ocup = "veh_ocu_sin";
    add.click((e) => {
        let input = $("#add_append_sin input").length - 2;
        for (var i = input; i <= input; i++) {
            result = i;
        }
        let cols = $(`
			<div class="col-5 ${veh_ocup}${result}">
            	<input type="text" value="" class="form-control ocupSin" />
            </div>
            <div class="col-5 ${veh_ocup}${result}">
            	<input type="text" value=""  class="form-control vehSin" />
            </div>
            <div class="col-2 removeVehOcup ${veh_ocup}${result}"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-archive-fill" viewBox="0 0 16 16"><path d="M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z"/></svg></div>					
		`);
        $("#add_append_sin").append(cols);
        cols
            .filter(`.removeVehOcup.${veh_ocup}${result}`)
            .click(() => cols.remove());
    });
}

function datos_person() {
    let add = $("#btn_append_person");
    let person = "dt_person";
    add.click((e) => {
        let input = $("#datos_person input").length - 2;
        for (var i = input; i <= input; i++) {
            result = i;
        }
        let cols = $(`
			<div class="col-1 ${person}${result}">
            	<input type="text" value="" class="form-control id_person" />
            </div>
            <div class="col-4 ${person}${result}">
            	<input type="text" value="" class="form-control nombre_person" />
            </div>
            <div class="col-2 ${person}${result}">
            	<input type="text" value="" class="form-control edad_person" />
            </div>
            <div class="col-4 ${person}${result}">
            	<input type="text" value="" class="form-control cond_person" />
            </div>
            <div class="col-1 removeDatos ${person}${result}"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-archive-fill" viewBox="0 0 16 16"><path d="M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z"/></svg></div>					
		`);
        $("#datos_person").append(cols);
        cols.filter(`.removeDatos.${person}${result}`).click(() => cols.remove());
        blockVirgula();
    });
}

function vehInvAppend() {
    let add = $("#btn_append");
    add.click((e) => {
        let input = $("#vehInv_append input").length - 2;
        for (var i = input; i <= input; i++) {
            result = i;
        }
        let invVeh = $(`
			<div class="col-5 ${vehInv}${result}">
				<div class="input-group has-validation">
					<div class="input-group-prepend">
						<span class="input-group-text">Tipo de Vehículo</span>
					</div>
					<input type="text" value="0" class="form-control tipo_veh_inv" />							
				</div>
			</div>
			<div class="col-5 ${vehInv}${result}">
				<div class="input-group has-validation">
					<div class="input-group-prepend">
						<span class="input-group-text">Nº Ejes</span>
					</div>
					<input type="text" value="0" class="form-control ejes" />							
				</div>
			</div>
            <div class="col-2 removeVEH ${vehInv}${result}"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-archive-fill" viewBox="0 0 16 16"><path d="M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z"/></svg></div>					
		`);
        $("#vehInv_append").append(invVeh);
        invVeh.filter(`.removeVEH.${vehInv}${result}`).click(() => invVeh.remove());
        blockVirgula();
    });
}

function addTipo() {
    let add = $("#btn_append_veh");
    let tipo = "tipo";
    add.click((e) => {
        for (var i = 0; i <= $(".addNum input").length; i++) {
            result = i;
        }

        let tipoVeh = $(`
            <div class="col-1 addNum ${tipo}${result}">
            <input type="text" value="" class="form-control num_" />
            </div>
            <div class="col-1 addMarca ${tipo}${result}">
            <input type="text" value="" class="form-control marca_" />
            </div>
            <div class="col-1 addTipo ${tipo}${result}">
            <input type="text" value="" class="form-control tipo_" />
            </div>
            <div class="col-2 addModelo ${tipo}${result}">
            <input type="text" value="" class="form-control modelo_" />
            </div>
            <div class="col-2 addCor ${tipo}${result}">
            <input type="text" value="" class="form-control color_" />
            </div>
            <div class="col-2 addPlaca ${tipo}${result}">
            <input type="text" value=""  class="form-control placa_" />
            </div>
            <div class="col-2 addTel ${tipo}${result}">
            <input type="text" value="" class="form-control tel_" />
            </div>
            <div class="col-1 removeTVEH ${tipo}${result}"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-archive-fill" viewBox="0 0 16 16"><path d="M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z"/></svg></div>					

        `);

        $("#tiposVeh").append(tipoVeh);
        tipoVeh
            .filter(`.removeTVEH.${tipo}${result}`)
            .click(() => tipoVeh.remove());
        blockVirgula();
    });
}

function table() {
    let table = $("#occurrence-table").DataTable({
        language: {
            search: "",
            searchPlaceholder: "Search",
        },
        select: true,
        Width: true,
        scrollY: "55vh",
        scrollCollapse: false,
        paging: false,
        bInfo: false,
        fixedColumns: true,
    });
    $("#occurrence-table tbody").on("click", "tr", function() {
        var event = $(table.row(this).data()[0]).text();
        $("[id$=idTable]").val(event);
        //console.log(event)
    });
}
let re = /\s*,\s*/;

function plusInputSin(ocupantes, vehiculo, Obs) {
    let ocup = ocupantes.split(re);
    let veh = vehiculo.split(re);
    let obs = Obs.split(re);
    addChildSin(ocup, veh);
    setObsSin(obs);
}

function setObsSin(obs) {
    for (var i = 0; i < obs.length; i++) {
        $(`.obs${i}`).val(obs[i]);
    }
}

function addChildSin(ocup, veh) {
    result = [];
    $(".ocupSin0").val(ocup[0]);
    $(".vehSin0").val(veh[0]);
    for (var i = 1; i < ocup.length; i++) {
        let cols = $(`
			<div class="col-5 deleteChildSin${i}">
            	<input type="text" value="${ocup[i]}" class="form-control ocupSin" />
            </div>
            <div class="col-5 deleteChildSin${i}">
            	<input type="text" value="${veh[i]}"  class="form-control vehSin" />
            </div>
            <div class="col-2 removeVEH" id="deleteChildSin${i}"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-archive-fill" viewBox="0 0 16 16"><path d="M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z"/></svg></div>					
		`);
        $("#add_append_sin").append(cols);
    }
    deleteChild();
    blockVirgula();
}

function plusInputVehInv(
    tipo,
    eje,
    num_veh,
    marca,
    tipo_veh,
    modelo,
    cor,
    placa,
    tel,
    id_person,
    nombre,
    edad,
    cond
) {
    let tipo_ = tipo.split(re);
    let eje_ = eje.split(re);
    let num_veh_ = num_veh.split(re);
    let marca_ = marca.split(re);
    let tipo_veh_ = tipo_veh.split(re);
    let modelo_ = modelo.split(re);
    let cor_ = cor.split(re);
    let placa_ = placa.split(re);
    let tel_ = tel.split(re);
    let id_person_ = id_person.split(re);
    let nombre_ = nombre.split(re);
    let edad_ = edad.split(re);
    let cond_ = cond.split(re);
    appendVehInv(
        tipo_,
        eje_,
        num_veh_,
        marca_,
        tipo_veh_,
        modelo_,
        cor_,
        placa_,
        tel_,
        id_person_,
        nombre_,
        edad_,
        cond_
    );
}

function appendVehInv(
    tipo_,
    eje_,
    num_veh_,
    marca_,
    tipo_veh_,
    modelo_,
    cor_,
    placa_,
    tel_,
    id_person_,
    nombre_,
    edad_,
    cond_
) {
    addInputVehInv(
        tipo_,
        eje_,
        num_veh_,
        marca_,
        tipo_veh_,
        modelo_,
        cor_,
        placa_,
        tel_,
        id_person_,
        nombre_,
        edad_,
        cond_
    );
}

function addInputVehInv(
    tipo,
    eje,
    num_veh,
    marca,
    tipo_veh,
    modelo,
    cor,
    placa,
    tel,
    id_person,
    nombre,
    edad,
    cond
) {
    let invVeh = "",
        result = [];
    $("#tp_veh0").val(tipo[0]);
    $("#eje_veh0").val(eje[0]);
    for (var i = 1; i < tipo.length; i++) {
        result[i] = i;
        invVeh = $(`
				<div class="col-5 childElete${i}">
					<div class="input-group has-validation">
						<div class="input-group-prepend">
							<span class="input-group-text">Tipo de Vehículo</span>
						</div>
						<input type="text" class="form-control tipo_veh_inv" value="${tipo[i]}"/>							
					</div>
				</div>
				<div class="col-5 childElete${i}">
					<div class="input-group has-validation">
						<div class="input-group-prepend">
							<span class="input-group-text">Nº Ejes</span>
						</div>
						<input type="text" class="form-control ejes childElete${i}" value="${eje[i]}"/>							
					</div>
				</div>
	            <div class="col-2 removeVEH ${vehInv}${result[i]}" id="childElete${i}"><svg id="" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-archive-fill" viewBox="0 0 16 16"><path d="M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z"/></svg></div>					
			`);

        $("#vehInv_append").append(invVeh);
    }

    $("#num0").val(num_veh[0]);
    $("#marca0").val(marca[0]);
    $("#tipo0").val(tipo_veh[0]);
    $("#modelo0").val(modelo[0]);
    $("#cor0").val(cor[0]);
    $("#placa0").val(placa[0]);
    $("#tel0").val(tel[0]);
    for (var i = 1; i < num_veh.length; i++) {
        result[i] = i;
        let tipoVeh = $(`
            <div class="col-1 addNum childEletes${i}">
            <input type="text" value="${num_veh[i]}" class="form-control num_" />
            </div>
            <div class="col-1 addMarca childEletes${i}">
            <input type="text" value="${marca[i]}" class="form-control marca_" />
            </div>
            <div class="col-1 addTipo childEletes${i}">
            <input type="text" value="${tipo_veh[i]}" class="form-control tipo_" />
            </div>
            <div class="col-2 addModelo childEletes${i}">
            <input type="text" value="${modelo[i]}" class="form-control modelo_" />
            </div>
            <div class="col-2 addCor childEletes${i}">
            <input type="text" value="${cor[i]}" class="form-control color_" />
            </div>
            <div class="col-2 addPlaca childEletes${i}">
            <input type="text" value="${placa[i]}"  class="form-control placa_" />
            </div>
            <div class="col-2 addTel childEletes${i}">
            <input type="text" value="${tel[i]}" class="form-control tel_" />
            </div>
            <div class="col-1 removeVEH ${vehInv}${result[i]}" id="childEletes${i}"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-archive-fill" viewBox="0 0 16 16"><path d="M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z"/></svg></div>					

        `);

        $("#tiposVeh").append(tipoVeh);
    }
    $("#id0").val(id_person[0]);
    $("#nombre0").val(nombre[0]);
    $("#edad0").val(edad[0]);
    $("#cond0").val(cond[0]);
    for (var i = 1; i < id_person.length; i++) {
        result[i] = i;
        let cols = $(`
			<div class="col-1 ChildEletes${i}">
            	<input type="text" value="${id_person[i]}" class="form-control id_person" />
            </div>
            <div class="col-4 ChildEletes${i}">
            	<input type="text" value="${nombre[i]}" class="form-control nombre_person" />
            </div>
            <div class="col-2 ChildEletes${i}">
            	<input type="text" value="${edad[i]}" class="form-control edad_person" />
            </div>
            <div class="col-4 ChildEletes${i}">
            	<input type="text" value="${cond[i]}" class="form-control cond_person" />
            </div>
            <div class="col-1 removeVEH ${vehInv}${result[i]}" id="ChildEletes${i}"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-archive-fill" viewBox="0 0 16 16"><path d="M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15h9.286zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1zM.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8H.8z"/></svg></div>					
		`);
        $("#datos_person").append(cols);
    }
    deleteChild();
    blockVirgula();
}

function getVehInvChilds() {
    getVehInv();
    getTipoVeh();
    getPerson();
}

function deleteChild() {
    $(".removeVEH").click(function() {
        var id = $(this).attr("id");
        $(`#${id}`).remove();
        $(`.${id}`).remove();
    });
}