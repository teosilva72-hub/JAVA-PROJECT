var highway = document.getElementById("eventoRodovia").value;
var state = document.getElementById("eventoEstado").value;




    
    if(highway == 1 && state == 4){
        console.log("Rodovia "+ highway)
        console.log("State: "+ state)
    }else{
	console.log("Erro")
	}

var x = new rodovia(highway, state);
console.log(x)