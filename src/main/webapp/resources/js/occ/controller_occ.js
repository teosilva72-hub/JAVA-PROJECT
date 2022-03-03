//validador rodovia e estado
function inputs() {
    /////////////////////////////////////////////////////////////////////////////////////////////
    //variaveis do input rodovia e estado
    var highway = document.getElementById("eventoRodovia");
    var state = document.getElementById("eventoEstado");
    var state1 = document.getElementById("eventoEstado1");
    var nav = document.querySelector(".sideMenuToggler");
    var home = document.getElementById("navbarHome");
    var logout = document.getElementById("navbarDropdown4");
    //passando valor para a classe rodovia
    var x = new rodovia(highway.value, state.value);

    //determina a cidade correspondente a rodovia selecionada
    highway.addEventListener("change", function(e) {
        e.preventDefault();
        var selectHighway = highway.value;
        if (selectHighway == 1) {
            state.value = "Veracruz";
            state1.value = "Veracruz";
        } else if (selectHighway == 2) {
            state.value = "Veracruz";
            state1.value = "Veracruz";
        } else if (selectHighway == 3) {
            state.value = "CDMX";
            state1.value = "CDMX";
        }

    }, false);
    ////////////////////////////////////////////////////////////////////////////////////////////
    //executando função para adicionar condição no input quantidade
    mask();
    ////////////////////////////////////////////////////////////////////////////////////////////
    nav.disabled = true;
    home.style.display = "none";
    logout.style.display = "none";
    ////////////////////////////////////////////////////////////////////////////////////////////
}
//INPUTS OBRIGATORIOS
function requiredList() {
    //entrada de dados
    var dataStart = document.getElementById("dataInicial");
    var horaStart = document.getElementById("horaInicial");
    var minutoStart = document.getElementById("minutoInicial");
    var amPmStart = document.getElementById("typeHour1");
    var dataEnd = document.getElementById("dataFinal");
    var horaEnd = document.getElementById("horaFinal");
    var minutoEnd = document.getElementById("minutoFinal");
    var amPmEnd = document.getElementById("typeHour2");
    var saveModal = document.getElementById("saveModal");
    var saveTextModal = document.getElementById("saveTextModal");
    var saveModalExit = document.getElementById("SaveModalExit");
    var saveModalVoltar = document.getElementById("saveModalVoltar");
    var saveBtn = document.getElementById("saveBtn");
    var editModal = document.getElementById("editModal");
    var editorTextModal = document.getElementById("editorTextModal");
    var editModalVoltar = document.getElementById("editModalVoltar");
    var editarBtn = document.getElementById("editarBtn");
    var errorEvent1 = document.getElementById("errorEvent1");
    var errorEvent2 = document.getElementById("errorEvent2");
    var errorEvent3 = document.getElementById("errorEvent3");
    var errorEvent4 = document.getElementById("errorEvent4");
    var errorEvent5 = document.getElementById("errorEvent5");
    var errorEvent6 = document.getElementById("errorEvent6");
    var errorEvent7 = document.getElementById("errorEvent7");
    var errorEvent8 = document.getElementById("errorEvent8");


    //processamento

    if (dataStart.value > dataEnd.value) {
        console.log("data inicial é maior que a data final");
        //mensagem de erro
        errorEvent1.style.display = "block";
        errorEvent4.style.display = "block";

        //save
        saveModal.style.display = "none";
        saveTextModal.style.display = "block";
        saveModalExit.style.display = "none";
        saveModalVoltar.style.display = "block";
        saveBtn.style.display = "none";
        //edit
        editModal.style.display = "none";
        editorTextModal.style.display = "block";
        editModalExit.style.display = "none";
        editModalVoltar.style.display = "block";
        editarBtn.style.display = "none";
    }
    //var horaInicial = (horaStart.value+":"+minutoStart.value);
    //var horaFinal = (horaFinal.value+":"+minutoEnd.value);


}

//alterar a maskara do input
function mask() {
    //criando variaveis
    var unidade = document.getElementById("unityDamage");
    var quantidade = document.getElementById("damageAmount");
    //criando evento
    unidade.addEventListener("change", function(e) {

        var value = unidade.value;

        if (value == 87) {

            $(document).ready(function() {
                var $km = $("#damageAmount");
                $km.mask('000+000', { reverse: false });
            });

        } else if (value == 88) {

            $(document).ready(function() {
                var $km = $("#damageAmount");
                $km.mask('000', { reverse: false });
            });
        }
    }, false);
}

function downloadPdf() {
    var id = document.getElementById("occNumber");
    if (id.value == "") return false;
}
//////////////////////////////////////////////////////////
function pdfValidator() {
    var user = document.getElementById("userPdf")
    if (user.value == "") {
        $('.lll').addClass('error')
        $('.lll').removeClass('ok')
        document.getElementById("msgErrorPdf").style.display = "block"
        return false
    } else {
        $('.lll').removeClass('error')
        $('.lll').addClass('ok')
        $('#modalDownload').modal('hide')
        $('[id$=pdfpdf]').click();
        document.getElementById("msgErrorPdf").style.display = "none"
        return true
    }
}

function setTypeDai(incident, date, hour, sentido, km, channel, nome, equipID, img) {
    //console.log(estrada + " " + date)
    //obs... st = start and en = end
    let st_date = new Date(date + ' ' + hour)
    let st_year = st_date.getFullYear()
    let st_day = st_date.getDate()
    let st_hour = st_date.getHours()
    let st_month = st_date.getMonth() + 1


    setTimeout(() => {
        //situacao
        $('#situacaoEvento').val('29')
            //type
        $('#origemEvento').val("23")
            //passando dia inicial
        if (st_day < 10) st_day = '0' + st_day
            //passando mês inicial
        if (st_month < 10) st_month = "0" + st_month
            //passando data para input
        $('#dataInicial').val(`${st_month}/${st_day}/${st_year}`)
            /////////////////////tr = transform ///////////////////////////
        $('#descricaoCausa').val(nome)
            ///////////////////

        let hr = hour.replaceAll(':', '')
        let hora = hr[0] + hr[1]
        let formatData = `${hora % 12}`.padStart(2, 0)
        let temp = hora > 11 ? 'pm' : 'am'
        $('#horaInicial').val(formatData)
        $('#minutoInicial').val(hr[2] + hr[3])
        $('#typeHour1').val(temp)
            //////////////////////////////////////////////////////////////
        $('#eventoKm').val(km)
            //pegar rodovia atual do equipamento
        setTimeout(() => {
            if (equipID != "") {
                $('#id-equip-get-rod').val(equipID)
                $('#btn-get-rod').click()
                setTimeout(() => {
                    if (getRodovias == '1')
                        $('#eventoRodovia').val(`${getRodovias}`), $('#eventoEstado1').val("Veracruz")
                    else if (getRodovias == '2')
                        $('#eventoRodovia').val(`${getRodovias}`), $('#eventoEstado1').val("Veracruz")
                    else if (getRodivia == '3')
                        $('#eventoRodovia').val(`${getRodovias}`), $('#eventoEstado1').val("CDMX")
                }, 300)
            }
        }, 250)

        $('#outrosEventos').val(incident)
            //
        if (nome != "") {
            $('#id-equip').val(nome)
            $('#btn-direction').click()
            setTimeout(() => {
                if (direction == 'N')
                    $('#eventoSentido').val("6")
                else if (direction == 'S')
                    $('#eventoSentido').val("7")
                else if (direction == 'L')
                    $('#eventoSentido').val("8")
                else
                    $('#eventoSentido').val("9")

                getImgDai(nome, img)
            }, 300)
        }

        alertToast('Apertura de ocurrencia activada. Edite los campos necesarios.')

    }, 700)

}

function getImgDai(nome, img) {
    //name equipament
    let name = nome.replaceAll(' ', '')
        //id equipament
    setTimeout(() => {
        $('.page-footer').append(`<div class="img-dai-occ" id="${name}">
        		<div class="content-img-dai-occ">
					<svg xmlns="http://www.w3.org/2000/svg" class="icon-closed-img-dai-occ ${name}" width="16" height="16" fill="currentColor" class="bi bi-x-square-fill" viewBox="0 0 16 16">
						<path d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm3.354 4.646L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 1 1 .708-.708z"/>
					</svg>
        			<img style="width: 100%;" src="data:image/jpg;base64, ${img}" />
        		</div>
        		
        	</div>
        `)
        let testeImg = `data:image/jpg;base64, ${img}`

        $(`.${name}`).click(ev => {
                $(`#${name}`).remove()
            })
            //move equipamement
        $(`#${name}`).draggable()
            //increase
        $(`#${name}`).resizable({
            //PARAMETERS
            aspectRatio: 16 / 9,
            maxHeight: 1020,
            maxWidth: 960,
            minHeight: 180,
            minWidth: 220,
            //grid: 30
            //animate: true,
            //helper: "ui-resizable-helper"
        })
        setTimeout(() => {
            $('#copy-img-dai').click()
        }, 2000)
    }, 300)
}
//
let direction = ""
let getRodovias = ""
    //
function getRodivia(rod) {
    getRodovias = rod
    return getRodovias
}

function getDirection(dir) {
    direction = dir
    return direction
}
//tratando dados sos
function setType(type, start_data, km, direction, estrada, nome) {
    //obs... st = start and en = end
    let st_date = new Date(start_data)
    let st_year = st_date.getFullYear()
    let st_day = st_date.getDate()
    let st_month = st_date.getMonth() + 1
    let st_hour = st_date.getHours()
    let st_minutes = st_date.getMinutes()

    setTimeout(() => {
        //passando o tipo para input
        $('#origemEvento').val(type)
            //passando dia inicial
        if (st_day < 10) st_day = '0' + st_day
            //passando mês inicial
        if (st_month < 10) st_month = "0" + st_month
            //passando data para input
        $('#dataInicial').val(`${st_month}/${st_day}/${st_year}`)
            /////////////////////tr = transform ///////////////////////////
        let temp = st_hour > 11 ? 'pm' : 'am'
        let hora = `${st_hour % 12}`.padStart(2, 0)
        $('#minutoInicial').val(st_minutes)
        $('#horaInicial').val(hora)
        $('#typeHour1').val(temp)
            //passando km
        $('#eventoKm').val(km)
        $('#eventoSentido').val(direction)

        if (estrada == 1 || estrada == 2) {
            $('#eventoRodovia').val(estrada)
            $('#eventoEstado1').val('Veracruz')
        } else {
            $('#eventoRodovia').val(estrada)
            $('#eventoEstado1').val('CDMX')
        }
        //////////////////////////////////////
        if (direction == 'N')
            $('#eventoSentido').val("6")
        else if (direction == 'S')
            $('#eventoSentido').val("7")
        else if (direction == 'L')
            $('#eventoSentido').val("8")
        else
            $('#eventoSentido').val("9")
            /////////////////////////////
        $('#outrosEventos').val(nome)

        alertToast('Apertura de ocurrencia activada. Edite los campos necesarios.')
    }, 700)
}
$(ext => {
    let param = new URLSearchParams(location.search)
    let date_dai = $('#date-dai')
    if (param.has('from') == true && param.get('from') != "") {
        if (param.has('id') == true && param.get('id') != "") {
            if (param.get('from') == 'dai') {
                if (param.get('date') != "") {
                    date_dai.val(param.get('date'))
                }
            }
            let id = $('#ext-get-id')
            let type = $('#ext-get-type')
            id.val(param.get('id'))
            type.val(param.get('from'))
            let btn = $('#btn-ext-id')
            btn.click()
            $('#new').click()
                //
        }
    }
})

function saveImgDaiOcc(img_dai) {
    console.log(img_dai)

}