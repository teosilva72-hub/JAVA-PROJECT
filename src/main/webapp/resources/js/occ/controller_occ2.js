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
function pdfValidator(){
	var user = document.getElementById("userPdf")
	if(user.value == ""){
			$('.lll').addClass('error')
		$('.lll').removeClass('ok')
		document.getElementById("msgErrorPdf").style.display = "block"
		return false
	}else{
		$('.lll').removeClass('error')
		$('.lll').addClass('ok')
		//$('#modalDownload').modal('hide')
		//$('[id$=pdfpdf]').click();
		document.getElementById("msgErrorPdf").style.display = "none"
		return true
	}
}	
$(pdf=>{
	$('[id$=occpdf]').click(e=>{
		$('#pdfpdf').click()
	})
})

function saveImgDaiOcc(img_dai) {
    console.log(img_dai)

}