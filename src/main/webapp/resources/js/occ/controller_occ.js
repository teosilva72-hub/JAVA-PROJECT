//validador rodovia e estado
function validatorRodovia(){ 
    //variaveis do input rodovia e estado
    var highway = document.getElementById("eventoRodovia");
    var state = document.getElementById("eventoEstado");
    var state1 = document.getElementById("eventoEstado1");
    //passando valor para a classe rodovia
    var x = new rodovia(highway.value, state.value);
    console.log(x);
     //determina a cidade correspondente a rodovia selecionada
    highway.addEventListener("change", function(e){
        e.preventDefault();
        var selectHighway = highway.value;
        if(selectHighway == 1 ){state.value = "Veracruz"; state1.value = "Veracruz";}
        else if(selectHighway == 2){state.value = "Veracruz"; state1.value = "Veracruz";}
        else if(selectHighway == 3){state.value = "CDMX"; state1.value = "CDMX";}
            
    }, false);

   //executando função para adicionar condição no input quantidade
    alterMeter();
	var nav = document.querySelector(".sideMenuToggler");
	var home = document.getElementById("navbarHome");
	var logout = document.getElementById("navbarDropdown4");
	nav.disabled = true;
	home.style.display = "none";
	logout.style.display = "none";
}
//alterar a maskara do input
function alterMeter(){
	//criando variaveis
	var unidade = document.getElementById("unityDamage");
    var quantidade = document.getElementById("damageAmount");
	//criando evento
    unidade.addEventListener("change", function(e){
		
        var value = unidade.value;

        if(value == 87){
	
            $(document).ready(function () { 
                var $km = $("#damageAmount");
                $km.mask('000+000', {reverse: false});
            });

        }else if(value == 88){
	
            $(document).ready(function () { 
                var $km = $("#damageAmount");
                $km.mask('000', {reverse: false});
            });
        }
    }, false);
}