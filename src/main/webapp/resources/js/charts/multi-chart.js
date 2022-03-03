
google.charts.load('current', {packages: ['line', 'corechart']})

let md = '';

function drawTab(id, mod, name){
	
	if(id == 0)	
	  	return   '<li class="nav-item" id="chart'+mod+''+id+'" >'+
	                   '<a class="nav-link active" data-toggle="tab" href="#'+mod+''+id+'">'+name+'</a>' + 		      
			  	 '</li>';	
	
	   else  return   '<li class="nav-item" id="chart'+mod+''+id+'" >'+
	                   		'<a class="nav-link" data-toggle="tab" href="#'+mod+''+id+'">'+name+'</a>' + 		      
		  	 		  '</li>';	
 
} 

function drawTabContent(id, mod){
	
	if(id == 0)	
		return 	'<div id="'+mod+''+id+'" class="tab-pane fade in active show">' +		    
					'<div id="chart-div'+id+'"></div>' +  
					
				'<a class="text-decoration-none" id="download-link-'+mod+''+id+'">' +             
             		'<span class="btn-chart-download-multi">' +
             		'<i class="fas fa-download chart-icon"></i>' +  
             		'</span>' +              
            	'</a>' +
                             
		        '</div>';   


		else return 	'<div id="'+mod+''+id+'" class="tab-pane fade">' +					    					   												
							'<div id="chart-div'+id+'"></div>' + 
    
						'<a class="text-decoration-none" id="download-link-'+mod+''+id+'">' +             
		             		'<span class="btn-chart-download-multi">' +
		             		'<i class="fas fa-download chart-icon"></i>' +  
		             		'</span>' +              
            			'</a>' + 
    
		                '</div>'; 
		                     
	}

 function createTabs(mod, jsonArray, columns, dataRow, interval, title, vAxisTitle, dateFormat, imageName){
			
	let array = JSON.parse(jsonArray)
	let cols = JSON.parse(columns)
	let rows = JSON.parse(dataRow)	
	md = mod;
			
	for(let i = 0; i < rows.length; i++){
			
		let test = rows[i][0]
		
		rows[i][0] = new Date(test)
			
	    let obj = rows[i];	   

		for(var prop = 1; prop < obj.length; prop++){		  
	        if(obj.hasOwnProperty(prop) && obj[prop] !== null && !isNaN(obj[prop])){
	            obj[prop] = +obj[prop]; 
				  
	        }
	    }
	}
			  						
	for(let i = 0; i < array.length; i++){
							
		 $('#tabs').append(drawTab(i, mod, array[i])); // CRIAR A TAB

		 $('.tab-content').append(drawTabContent(i, mod)); // CRIA O CONTEUDO DINÂMICO		
		  	
        }

         // CRIA SOMENTE OS DADOS PARA O 1o GRAFICO
		 draw('chart-div0', mod, array, 0, cols, rows, interval, title, vAxisTitle, dateFormat, imageName);
	
	   // DESENHA O GRAFICO A CADA ABA SELECIONADA
	
		$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
  			let target = $(e.target).attr("href") // activated tab
  				
	        let num = Number(target.replace('#'+md, ''))
			
			draw('chart-div'+num, mod, array, num, cols, rows, interval, title, vAxisTitle, dateFormat, imageName);
		
		});
 	
	} 

// ------------------------------------------------------------------------------------------------

function draw(div, mod, array, equipIndex, columns, rows, interval, title, vAxisTitle, dateFormat, imageName){
  
 	let chart_div;
    let chart;
    let data; 
	let options;
 	let ticks = []; 	
  
	data = new google.visualization.DataTable();
  
  //Add columns

  let index = 0;
  
  data.addColumn('date', columns[0]);
   
  for(c = 1; c < columns.length; c++ )        
   	data.addColumn('number', columns[c]);
	  
	//Add rows
			
  	for(let i = 0; i < interval; i++){  
  	
 		index = i + (equipIndex * interval);
		
		data.addRow(rows[index]) 	
		
  }
	
  	// According to rows number     
     for (let i = 0; i < data.getNumberOfRows(); i++)
       		ticks.push(data.getValue(i, 0));	
     
    options = {
      title: title+' ('+array[equipIndex]+')',
      theme: 'material',
      lineWidth: 3,    
      width: 1150,
      height: 550, 
	  chartArea: {width: '63%'},
      titleTextStyle: {
	        color: 'black',    
	        fontName: 'Verdana', 
	        fontSize: 16, 
	        bold: true,   
	        italic: false  
   		 },
      legend: {
        position: 'right',
        maxLine: 3,
        textStyle: {
        fontName: 'Verdana', 
        color: 'black',
        bold: true       
        	}
     	},
         vAxis: {      
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
       	   }         
        }; 
         
       chart_div = document.getElementById(div);               
       chart = new google.visualization.LineChart(chart_div);
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
        var download = document.getElementById('download-link-'+mod+''+equipIndex);
        download.href = canvas.toDataURL('image/png');
        download.download = array[equipIndex]+'_'+imageName+'.png';
        
      }
      
      image.src = imageURI;              
                
  });        
    						
   	
}

// ------------------------------------------------------------------------------------------------

function toggleChart(){			 
	 $('#chart-area').removeClass('invisible');	
	 $('.table-container').addClass('d-none');
	 $('.logo-concessionaire').addClass('d-none');
}

$(function () {
    
	 $('#close-link-multi').click(function () {
	 	  $('#chart-area').addClass('invisible');	
	 	  $('.table-container').removeClass('d-none');
		  $('.logo-concessionaire').removeClass('d-none');
	 
		  // Column adjust
		  $($.fn.dataTable.tables(true)).DataTable()
		  .columns.adjust();
 
	})		
}); 
