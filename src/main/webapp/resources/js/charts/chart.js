                 
      google.charts.load('current', {packages: ['line', 'corechart'],  "callback": drawChart}); 
      google.charts.setOnLoadCallback(drawChart);
            
      var chart_div;
      var chart;
      var data;
      var options;

      function drawChart() {                              	  
    	     	    	    	      	
        data = new google.visualization.DataTable();

        //Add columns      
        data.addColumn('number', 'Dia');        
        data.addColumn('number', 'Motos');
        data.addColumn('number', 'Autos');
        data.addColumn('number', 'Pesados');
            
          //Add columns
                
         //Add rows
         data.addRows([
          [1,  1, 100, 20],
          [2,  6, 72, 30],
          [3,  3, 50, 40],
          [4,  2, 25, 50],
          [5,  1, 60, 60],
          [6,  4, 80, 80],
          [7,  5, 50, 60],
          [8,  8, 40, 40],
          [9,  12, 120, 35],
          [10, 9, 50, 30],
          [11, 5, 60, 25],
          [12, 6, 80, 20],
          [13, 2, 45, 25],
          [14, 3, 70, 35]
        ]);
        
              
      //  data.addRows([[new Date('06/02/2021'),5,15,11,4,0,0,35]]);

       var options = {
         
          title: 'Cotagem Veícular',   
          width: 900,
          height: 500     
        
      };
         
        chart_div = document.getElementById('chart-div');
        //chart = new google.charts.Line(chart_div);
        
        //DRAW CHART
       // chart.draw(data, google.charts.Line.convertOptions(options)); 
        
        chart = new google.visualization.LineChart(chart_div);
        chart.draw(data, options);                                               
                  
}  


 function reDrawChart(chartColumns, chartRows, title, vAxisTitle, dateFormat, imageName) {
  
  let columns = chartColumns; // Columns from backing bean JSF
                    
  data = new google.visualization.DataTable();
  
  //Add columns
  
  console.log(chartColumns);
  
  data.addColumn('date', columns[0]);
   
  for(c = 1; c < columns.length; c++ )        
   data.addColumn('number', columns[c]);
   
   console.log(chartRows);
      
   //Add rows
   data.addRows(chartRows); // ROWS from backing bean JSF
   
   //According to rows number
   
     var ticks = [];
     for (var i = 0; i < data.getNumberOfRows(); i++) {
       ticks.push(data.getValue(i, 0));
     }    

    var options = {
      title: title,
       titleTextStyle: {
        color: 'black',    
        fontName: 'Verdana', 
        fontSize: 16, 
        bold: true,   
        italic: false  
    },
      theme: 'material',
      lineWidth: 3,    
      width: 1150,
      height: 550,       
          legend: {
            position: 'right',
            maxLine: 3,
            textStyle: {
            fontName: 'Verdana', 
            color: 'black',
            bold: true       
            },
         },
        vAxis: {      
          minValue: 0,
          title: vAxisTitle,
            textStyle : {
            fontName: 'Verdana', 
            color: 'black',
            bold:true
        },
         titleTextStyle: {
           color: 'black',    
           fontName: 'Arial',      
           bold: false,
           italic: true     
       }                     
        },      
          hAxis: {                                   
            format: dateFormat, 
            slantedText: true,
            slantedTextAngle: 45, 
            ticks: ticks,      
            textStyle : {
            fontName: 'Verdana', 
            color: 'black',
            bold:true
        }                        
       },       
             
     };  
           
  chart_div = document.getElementById('chart-div');
  chart = new google.visualization.LineChart(chart_div);
  
    //DRAW CHART
     chart.draw(data, options); 
     
     google.visualization.events.addListener(chart, 'ready', function() {
      
       var canvas;
       var domURL;
       var imageNode;
       var imageURI;
       var svgParent;

      // add svg namespace to chart
      svgParent = chart.getContainer().getElementsByTagName('svg')[0];
      svgParent.setAttribute('xmlns', 'http://www.w3.org/2000/svg');

      // create image URI
      domURL = window.URL || window.webkitURL || window;
      imageNode = chart.getContainer().cloneNode(true);
      imageURI = domURL.createObjectURL(new Blob([svgParent.outerHTML], {type: 'image/svg+xml'}));
      image = new Image();
      image.onload = function() {
        canvas = document.createElement('canvas');
        canvas.setAttribute('width', parseFloat(svgParent.getAttribute('width')));
        canvas.setAttribute('height', parseFloat(svgParent.getAttribute('height')));
        canvas.getContext('2d').drawImage(image, 0, 0);
                   
        //Link to Download Image
        var download = document.getElementById('download-link');
        download.href = canvas.toDataURL('image/png');
        download.download = imageName+'.png';
        
      }
      
      image.src = imageURI;              
                
  });              

 }  
        
function toggleChart(){			 
 $('.chart-wrapper').removeClass('invisible');	
 $('.table-container').addClass('d-none');
}
            

$(function () {
    
 $('#close-link').click(function () {
 $('.chart-wrapper').addClass('invisible');	
 $('.table-container').removeClass('d-none');
 
 //Column adjust
  $($.fn.dataTable.tables(true)).DataTable()
  .columns.adjust();
 
})		
}); 

