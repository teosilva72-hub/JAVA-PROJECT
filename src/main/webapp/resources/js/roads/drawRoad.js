	
	
function road(id, posX, posY, width, height) {
	
const canvas = document.getElementById(id);
const ctx = canvas.getContext('2d');

//largura
ctx.canvas.width  = window.innerWidth;

/* Retangulo */
ctx.fillStyle = '#333333';
ctx.fillRect(posX, posY, width, height); // fillRect  (x, y, width, height)

/*Linha continua acima*/
ctx.beginPath(); 
ctx.strokeStyle = "#FFFFFF";
ctx.lineWidth=6;
ctx.moveTo(posX, 59);
ctx.lineTo(width,59);
ctx.stroke();
ctx.closePath();

/*Linha continua acima*/

/*Linhas Centrais*/
ctx.beginPath();
ctx.strokeStyle = "#FFFF00";
ctx.lineWidth=6;

for (var i = 0; i < width; i+=50) {
		
	ctx.lineTo((i+15), 85);
    ctx.moveTo((i+35), 85);
    ctx.stroke();    
}	 
    
  ctx.closePath();

/*Linha Central*/

/*Linha continua abaixo*/
 
ctx.beginPath();
ctx.strokeStyle = "#FFFFFF";
ctx.lineWidth = 6;
ctx.moveTo(posX, 110);
ctx.lineTo(width, 110);
ctx.stroke();
ctx.closePath();
        
/*Linha continua abaixo*/

//Posicionamento
document.getElementById(id).style.marginTop = posX + "px";
document.getElementById(id).style.marginLeft = posY + "px";

//Altura
document.getElementById(id).style.height = height  + "px";

//Comprimento
document.getElementById(id).style.width = width + "px";

}

function road2(id, posX, posY, width, height) {


        
/*Linha continua abaixo*/


}

function draw() {
	
  var canvas = document.getElementById('myCanvas');
  if (canvas.getContext){
    var ctx = canvas.getContext('2d');

    // Exemplo de curvas de Bézier cúbicas
    ctx.beginPath();
    ctx.moveTo(75,40);
    ctx.fillStyle = '#FF0000';
    ctx.bezierCurveTo(75,37,70,25,50,25);
    ctx.bezierCurveTo(20,25,20,62.5,20,62.5);
    ctx.bezierCurveTo(20,80,40,102,75,120);
    ctx.bezierCurveTo(110,102,130,80,130,62.5);
    ctx.bezierCurveTo(130,62.5,130,25,100,25);
    ctx.bezierCurveTo(85,25,75,37,75,40);
    ctx.fill();
  
}
	
}

function getRadianAngle(degreeValue) {
    return degreeValue * Math.PI / 180;
} 
	
