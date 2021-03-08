//INÍCIO DO SCRIPT//////////////////////////
 var delayIt = function(){
          setTimeout( rc, 10000) //Delay para execução         
        }
          			
         function refresh() {
           setTimeout("location.reload(true);", 4000);         
         }
         
          function refreshNoSelectedMessage() {
           setTimeout("location.reload(true);", 1000);         
         }
         
         function sumirMsg() {
				window.setTimeout(
						function() {
						   document.getElementById('msg').style.transition = 'opacity .25s ease-in-out';
						   document.getElementById('msg').style.display = 'none';
						}, 1500);
			};
			
			 function showMsg() {
					window.setTimeout(
					  function() {					
						  document.getElementById('msg').style.transition = 'opacity .25s ease-in-out';
						  document.getElementById('msg').style.display = 'block';
					}, 7000);
				};	
    
   
   function changeDIV(){
     	 
     	document.getElementById('message-block').style.height = "525px";	 
     	document.getElementById('messages-list').style.display = "block";
     	
      }
      
      function returnDIV(){
     	 
    	  document.getElementById('messages-list').style.display = "none";     	  
    	  document.getElementById('message-block').style.height = "240px";	             	          	 
      }
   
      function reset() {
          setTimeout("location.reload(true);", 3000);
        }
          
      function myFunction(equip) {
	
	  document.getElementById(equip).classList.remove('equip-info');	  
	  document.getElementById(equip).classList.add('equip-info-updating');  
      }

      function myFunction2(equip) {
	
	  document.getElementById(equip).classList.remove('equip-info-updating');
	  document.getElementById(equip).classList.add('equip-info');    
      } 
  
      function changeOpacity(equip){	 
	  document.getElementById(equip).style.opacity = '1';	 
	  document.getElementById("mostrarOption").style.display = "block";  
	  mostrarOption();
      }
      
      function imageOpacity(){	 
    	  document.getElementById('graphic').style.opacity = '1';	 
      }
      
      function imageOpacityLow(){	 
    	  document.getElementById('graphic').style.opacity = '0.7';	 
      }
      
      function checkedOpacity(equip){	 
    	  document.getElementById(equip).style.opacity = '0.7';	 
    	  document.getElementById(equip).style.background = '#222222';	
    	  document.getElementById(equip).style.boxShadow = " -1px 0px 6px 4px #3A5FCD"; 
		  document.getElementById("mostrarOption").style.display = "block";  

  	 
      }
           
      function uncheckedOpacity(equip){	 
    	  document.getElementById(equip).style.opacity = '0.7';	 
    	  document.getElementById(equip).style.background = '#222222';	
    	  document.getElementById(equip).style.boxShadow = "-1px 0px 6px 4px #3A5FCD";  
		  document.getElementById("mostrarOption").style.display = "block"; 
      }
           
      function uncheckedOpacityNormalShadow(equip){	 
    	  document.getElementById(equip).style.opacity = '0.7';	 
    	  document.getElementById(equip).style.background = '#222222';	
    	  document.getElementById(equip).style.boxShadow = "5px 5px 4px gray";  
   	 	  document.getElementById("mostrarOption").style.display = "block";  
      }
      
      function hideMsg(){
         $("#message-show").delay(1000).hide(1000);
      }
///////////////////////////////////////////////////////////////////////////////////////////////////////
 $('#one').on('scroll', function scroll() {
             $('#two').scrollTop($(this).scrollTop());
             });

$('#submit').submit(function(e) {
        	    e.preventDefault();
        	    // Coding        	  
        	});

 //Timeout to show Modal Status (loading) 	           
      $('#submit').click(function teste1(){
        setTimeout("$('#statusModal').modal('hide');", 2000);
    });

function seleccaoHidden(){
	document.getElementById("seleccaoHidden").style.display = "none";
}
/////ANIMAÇÃO ABERTURA DO SITE////////////////////////
	$(window).on('load', function () {
        $('#preloader .inner').fadeOut();
        $('#preloader').delay(350).fadeOut('slow'); 
        
    });
 ////////////////////////////////////////////////////////////


 $(async function() {
	$('#toogleMenu button[toggle]').click(function() {
		if ($(this).attr('toggle') === 'show') {
			$(this).text('Ocultar').attr('toggle', "hide")
			$('#showOption').css("display", "block")
		} else {
			$(this).text('Mostrar').attr('toggle', "show")
			$('#showOption').css("display", "none")
		}
	})
 })