$(document).ready(function() {	
	 $('.info-link').click(function () {

	     var data_id = 0;

	    if (typeof $(this).data('id') !== 'undefined') 
	         data_id = $(this).data('id');	      
	      
	      //Iniciar demais faixas invisiveis
	   
	      document.getElementById("header3").style.visibility = "hidden";	
	      document.getElementById("lane3").style.visibility = "hidden";	
		  document.getElementById("header4").style.visibility = "hidden";
		  document.getElementById("lane4").style.visibility = "hidden";
		  document.getElementById("header5").style.visibility = "hidden";
		  document.getElementById("lane5").style.visibility = "hidden";	
		  document.getElementById("header6").style.visibility = "hidden";
		  document.getElementById("lane6").style.visibility = "hidden";	
		  document.getElementById("header7").style.visibility = "hidden";
		  document.getElementById("lane7").style.visibility = "hidden";	
		  document.getElementById("header8").style.visibility = "hidden";
		  document.getElementById("lane8").style.visibility = "hidden";	
		 				 	  
		//VIA PAULISTA BEGIN
		  			  		  
	   if(data_id == 20101){
		   
	      document.getElementById("nameSat").innerHTML = "SAT 01";
	      document.getElementById("siteID").innerHTML = "20101";   
	      document.getElementById("city").innerHTML = "Boa Esperan&#231;a"; 
	      document.getElementById("road").innerHTML = "SP255"; 
	      document.getElementById("km").innerHTML = "96,200";	
	      document.getElementById("lanes").innerHTML = "2"; 	     
	      document.getElementById("lane1").innerHTML = "S"; 
	      document.getElementById("lane2").innerHTML = "N";     
	    		  
	   }
	   
	     if(data_id == 20102){		   
		      document.getElementById("nameSat").innerHTML = "SAT 02";
		      document.getElementById("siteID").innerHTML = "20102";   
		      document.getElementById("city").innerHTML = "Boa Esperan&#231;a"; 
		      document.getElementById("road").innerHTML = "SP255"; 
		      document.getElementById("km").innerHTML = "124,200"; 
		      document.getElementById("lanes").innerHTML = "2"; 
		      document.getElementById("lane1").innerHTML = "S"; 
		      document.getElementById("lane2").innerHTML = "N"; 			 	         
	          
		   }
	   
	   if(data_id == 20103){		   
		      document.getElementById("nameSat").innerHTML = "SAT 03";
		      document.getElementById("siteID").innerHTML = "20103";   
		      document.getElementById("city").innerHTML = "Boa Esperan&#231;a"; 
		      document.getElementById("road").innerHTML = "SP255"; 
		      document.getElementById("km").innerHTML = "144,280"; 
		      document.getElementById("lanes").innerHTML = "2"; 
		      document.getElementById("lane1").innerHTML = "S"; 
		      document.getElementById("lane2").innerHTML = "N";               
               
		   }
	   
	   if(data_id == 20104){			    
		      document.getElementById("nameSat").innerHTML = "SAT 04";
		      document.getElementById("siteID").innerHTML = "20104";   
		      document.getElementById("city").innerHTML = "Ja&#250;"; 
		      document.getElementById("road").innerHTML = "SP255"; 
		      document.getElementById("km").innerHTML = "155,200"; 
		      document.getElementById("lanes").innerHTML = "4"; 		    
		      document.getElementById("lane1").innerHTML = "S"; 
		      document.getElementById("lane2").innerHTML = "S";   
		      document.getElementById("lane3").innerHTML = "N";		   
		      document.getElementById("lane4").innerHTML = "N";	
			  document.getElementById("header3").style.visibility = "visible";		
			  document.getElementById("header4").style.visibility = "visible";	
			  document.getElementById("lane3").style.visibility = "visible";	
			  document.getElementById("lane4").style.visibility = "visible";	
				    
		   }
	   
	    if(data_id == 20105){		   
		      document.getElementById("nameSat").innerHTML = "SAT 05";
		      document.getElementById("siteID").innerHTML = "20105";   
		      document.getElementById("city").innerHTML = "Ja&#250;"; 
		      document.getElementById("road").innerHTML = "SP255"; 
		      document.getElementById("km").innerHTML = "160,680"; 
		      document.getElementById("lanes").innerHTML = "2"; 
		      document.getElementById("lane1").innerHTML = "S"; 
		      document.getElementById("lane2").innerHTML = "N";        
            
		   }
	      
	      if(data_id == 20106){		   
		      document.getElementById("nameSat").innerHTML = "SAT 06";
		      document.getElementById("siteID").innerHTML = "20106";   
		      document.getElementById("city").innerHTML = "Ja&#250;"; 
		      document.getElementById("road").innerHTML = "SP255"; 
		      document.getElementById("km").innerHTML = "187,000"; 
		      document.getElementById("lanes").innerHTML = "2"; 
		      document.getElementById("lane1").innerHTML = "S"; 
		      document.getElementById("lane2").innerHTML = "N"; 		           
             
		    }
	      
	      if(data_id == 20107){		   
		      document.getElementById("nameSat").innerHTML = "SAT 08";
		      document.getElementById("siteID").innerHTML = "20107";   
		      document.getElementById("city").innerHTML = "Botucatu"; 
		      document.getElementById("road").innerHTML = "SP255"; 
		      document.getElementById("km").innerHTML = "232,000"; 
		      document.getElementById("lanes").innerHTML = "2"; 
		      document.getElementById("lane1").innerHTML = "S"; 
		      document.getElementById("lane2").innerHTML = "N"; 
		                              
		   }
	      
	      if(data_id == 20108){		   
		      document.getElementById("nameSat").innerHTML = "SAT 07";
		      document.getElementById("siteID").innerHTML = "20108";   
		      document.getElementById("city").innerHTML = "Botucatu"; 
		      document.getElementById("road").innerHTML = "SP255"; 
		      document.getElementById("km").innerHTML = "222,800"; 
		      document.getElementById("lanes").innerHTML = "2"; 
		      document.getElementById("lane1").innerHTML = "S"; 
		      document.getElementById("lane2").innerHTML = "N"; 
		                            
		   }
	   
	      if(data_id == 20109){		   
		      document.getElementById("nameSat").innerHTML = "SAT 09";
		      document.getElementById("siteID").innerHTML = "20109";   
		      document.getElementById("city").innerHTML = "Ita&#237;"; 
		      document.getElementById("road").innerHTML = "SP255"; 
		      document.getElementById("km").innerHTML = "290,250"; 
		      document.getElementById("lanes").innerHTML = "2"; 
		      document.getElementById("lane1").innerHTML = "S"; 
		      document.getElementById("lane2").innerHTML = "N"; 
		      
		   }
	      
	      if(data_id == 20110){		   
		      document.getElementById("nameSat").innerHTML = "SAT 10";
		      document.getElementById("siteID").innerHTML = "20110";   
		      document.getElementById("city").innerHTML = "Ita&#237;"; 
		      document.getElementById("road").innerHTML = "SP255"; 
		      document.getElementById("km").innerHTML = "299,300"; 
		      document.getElementById("lanes").innerHTML = "2"; 
		      document.getElementById("lane1").innerHTML = "S"; 
		      document.getElementById("lane2").innerHTML = "N"; 
		                        
		   }
	      
	      if(data_id == 20111){		   
		      document.getElementById("nameSat").innerHTML = "SAT 12";
		      document.getElementById("siteID").innerHTML = "20111";   
		      document.getElementById("city").innerHTML = "Coronel Macedo"; 
		      document.getElementById("road").innerHTML = "SP255"; 
		      document.getElementById("km").innerHTML = "321,500"; 
		      document.getElementById("lanes").innerHTML = "2"; 
		      document.getElementById("lane1").innerHTML = "S"; 
		      document.getElementById("lane2").innerHTML = "N"; 		  
            
		   }
	      
	      if(data_id == 20112){		   
		      document.getElementById("nameSat").innerHTML = "SAT 13";
		      document.getElementById("siteID").innerHTML = "20112";   
		      document.getElementById("city").innerHTML = "Coronel Macedo"; 
		      document.getElementById("road").innerHTML = "SP255"; 
		      document.getElementById("km").innerHTML = "326,500"; 
		      document.getElementById("lanes").innerHTML = "2"; 
		      document.getElementById("lane1").innerHTML = "S"; 
		      document.getElementById("lane2").innerHTML = "N";	
                           
		   }
	      
	      if(data_id == 20113){		   
		      document.getElementById("nameSat").innerHTML = "SAT 14";
		      document.getElementById("siteID").innerHTML = "20113";   
		      document.getElementById("city").innerHTML = "Coronel Macedo"; 
		      document.getElementById("road").innerHTML = "SP255"; 
		      document.getElementById("km").innerHTML = "340,800"; 
		      document.getElementById("lanes").innerHTML = "2"; 
		      document.getElementById("lane1").innerHTML = "S"; 
		      document.getElementById("lane2").innerHTML = "N"; 
              
		   }
	      
	      if(data_id == 20114){		   
		      document.getElementById("nameSat").innerHTML = "SAT 15";
		      document.getElementById("siteID").innerHTML = "20114";   
		      document.getElementById("city").innerHTML = "Coronel Macedo"; 
		      document.getElementById("road").innerHTML = "SP281"; 
		      document.getElementById("km").innerHTML = "44,600"; 
		      document.getElementById("lanes").innerHTML = "2"; 
		      document.getElementById("lane1").innerHTML = "S"; 
		      document.getElementById("lane2").innerHTML = "N";                           
		   }
	      
	      if(data_id == 20115){		   
		      document.getElementById("nameSat").innerHTML = "SAT 16";
		      document.getElementById("siteID").innerHTML = "20115";   
		      document.getElementById("city").innerHTML = "Coronel Macedo"; 
		      document.getElementById("road").innerHTML = "SP281"; 
		      document.getElementById("km").innerHTML = "61,700"; 
		      document.getElementById("lanes").innerHTML = "2"; 
		      document.getElementById("lane1").innerHTML = "S"; 
		      document.getElementById("lane2").innerHTML = "N"; 		    
                           
		   }
	      
	      if(data_id == 20116){		   
		      document.getElementById("nameSat").innerHTML = "SAT 17";
		      document.getElementById("siteID").innerHTML = "20116";   
		      document.getElementById("city").innerHTML = "Boa Esperan&#231;a"; 
		      document.getElementById("road").innerHTML = "SPA106/255"; 
		      document.getElementById("km").innerHTML = "2,800"; 
		      document.getElementById("lanes").innerHTML = "2"; 
		      document.getElementById("lane1").innerHTML = "O"; 
		      document.getElementById("lane2").innerHTML = "L"; 	                 
              
		   }
	      
	      if(data_id == 20117){		   
		      document.getElementById("nameSat").innerHTML = "SAT 18";
		      document.getElementById("siteID").innerHTML = "20117";   
		      document.getElementById("city").innerHTML = "Boa Esperan&#231;a"; 
		      document.getElementById("road").innerHTML = "SPA115/255"; 
		      document.getElementById("km").innerHTML = "0,600"; 
		      document.getElementById("lanes").innerHTML = "2"; 
		      document.getElementById("lane1").innerHTML = "L"; 
		      document.getElementById("lane2").innerHTML = "O";		                              
		   }
	      
	      if(data_id == 20118){		   
		      document.getElementById("nameSat").innerHTML = "SAT 19";
		      document.getElementById("siteID").innerHTML = "20118";   
		      document.getElementById("city").innerHTML = "Boa Esperan&#231;a"; 
		      document.getElementById("road").innerHTML = "SPA133/255"; 
		      document.getElementById("km").innerHTML = "2,800"; 
		      document.getElementById("lanes").innerHTML = "2"; 
		      document.getElementById("lane1").innerHTML = "O"; 
		      document.getElementById("lane2").innerHTML = "L"; 	      
                           
		   }
	      
	      if(data_id == 20119){		   
		      document.getElementById("nameSat").innerHTML = "SAT 20";
		      document.getElementById("siteID").innerHTML = "20119";   
		      document.getElementById("city").innerHTML = "Boa Esperan&#231;a"; 
		      document.getElementById("road").innerHTML = "SPA138/255"; 
		      document.getElementById("km").innerHTML = "0,500"; 
		      document.getElementById("lanes").innerHTML = "2"; 
		      document.getElementById("lane1").innerHTML = "O"; 
		      document.getElementById("lane2").innerHTML = "L"; 
		                                 
		   }
	      
	      if(data_id == 20120){		   
		      document.getElementById("nameSat").innerHTML = "SAT 21";
		      document.getElementById("siteID").innerHTML = "20120";   
		      document.getElementById("city").innerHTML = "Botucatu"; 
		      document.getElementById("road").innerHTML = "SPA198/255"; 
		      document.getElementById("km").innerHTML = "1,700"; 
		      document.getElementById("lanes").innerHTML = "2"; 
		      document.getElementById("lane1").innerHTML = "L"; 
		      document.getElementById("lane2").innerHTML = "O";  
                           
		   }
	      
	      if(data_id == 20121){		   
		      document.getElementById("nameSat").innerHTML = "SAT 22";
		      document.getElementById("siteID").innerHTML = "20121";   
		      document.getElementById("city").innerHTML = "Coronel Macedo"; 
		      document.getElementById("road").innerHTML = "SPA043/281"; 
		      document.getElementById("km").innerHTML = "2,500"; 
		      document.getElementById("lanes").innerHTML = "2"; 
		      document.getElementById("lane1").innerHTML = "O"; 
		      document.getElementById("lane2").innerHTML = "L"; 
		                            
		   }
	      
	      if(data_id == 20122){		   
		      document.getElementById("nameSat").innerHTML = "SAT 11";
		      document.getElementById("siteID").innerHTML = "20122";   
		      document.getElementById("city").innerHTML = "Ita&#237;"; 
		      document.getElementById("road").innerHTML = "SPA106/255"; 
		      document.getElementById("km").innerHTML = "309,500"; 
		      document.getElementById("lanes").innerHTML = "2"; 
		      document.getElementById("lane1").innerHTML = "S"; 
		      document.getElementById("lane2").innerHTML = "N"; 
		                                
		   }
	      
	      if(data_id == 20151){		   
		      document.getElementById("nameSat").innerHTML = "SAT 51";
		      document.getElementById("siteID").innerHTML = "20151";   
		      document.getElementById("city").innerHTML = "Boa Esperan&#231;a"; 
		      document.getElementById("road").innerHTML = "SP255"; 
		      document.getElementById("km").innerHTML = "117,000"; 
		      document.getElementById("lanes").innerHTML = "2"; 
		      document.getElementById("lane1").innerHTML = "S"; 
		      document.getElementById("lane2").innerHTML = "N";			     
                       
		   }
	      
	      if(data_id == 20152){		   
		      document.getElementById("nameSat").innerHTML = "SAT 52";
		      document.getElementById("siteID").innerHTML = "20152";   
		      document.getElementById("city").innerHTML = "Ja&#250;"; 
		      document.getElementById("road").innerHTML = "SP255"; 
		      document.getElementById("km").innerHTML = "166,000"; 
		      document.getElementById("lanes").innerHTML = "2"; 
		      document.getElementById("lane1").innerHTML = "S"; 
		      document.getElementById("lane2").innerHTML = "N"; 	
              
		   }
	      
	      if(data_id == 20153){		   
		      document.getElementById("nameSat").innerHTML = "SAT 53";
		      document.getElementById("siteID").innerHTML = "20153";   
		      document.getElementById("city").innerHTML = "Prat&#226;nia"; 
		      document.getElementById("road").innerHTML = "SP255"; 
		      document.getElementById("km").innerHTML = "228,000"; 
		      document.getElementById("lanes").innerHTML = "2"; 
		      document.getElementById("lane1").innerHTML = "S"; 
		      document.getElementById("lane2").innerHTML = "N"; 
		      document.getElementById("lane3").innerHTML = "";		   
              document.getElementById("lane4").innerHTML = "";        
            		      		   
		   }
	      
	      if(data_id == 20154){		   
		      document.getElementById("nameSat").innerHTML = "SAT 54";
		      document.getElementById("siteID").innerHTML = "20154";   
		      document.getElementById("city").innerHTML = "Coronel Macedo"; 
		      document.getElementById("road").innerHTML = "SP255"; 
		      document.getElementById("km").innerHTML = "334,000"; 
		      document.getElementById("lanes").innerHTML = "2"; 
		      document.getElementById("lane1").innerHTML = "S"; 
		      document.getElementById("lane2").innerHTML = "N";		                 
          
		   }
	      
	      if(data_id == 20155){		   
		      document.getElementById("nameSat").innerHTML = "SAT 55";
		      document.getElementById("siteID").innerHTML = "20155";   
		      document.getElementById("city").innerHTML = "Ita&#237;"; 
		      document.getElementById("road").innerHTML = "SP255"; 
		      document.getElementById("km").innerHTML = "305,000"; 
		      document.getElementById("lanes").innerHTML = "2"; 
		      document.getElementById("lane1").innerHTML = "S"; 
		      document.getElementById("lane2").innerHTML = "N"; 		     
                         		  		   
		   }	
	      
	      if(data_id == 20156){		   
		      document.getElementById("nameSat").innerHTML = "SAT 56";
		      document.getElementById("siteID").innerHTML = "20156";   
		      document.getElementById("city").innerHTML = "S&#227;o Carlos"; 
		      document.getElementById("road").innerHTML = "SP318"; 
		      document.getElementById("km").innerHTML = "254,620"; 
		      document.getElementById("lanes").innerHTML = "3"; 
		      document.getElementById("lane1").innerHTML = "N"; 
		      document.getElementById("lane2").innerHTML = "S"; 
		      document.getElementById("lane3").innerHTML = "S";		   
		      document.getElementById("header3").style.visibility = "visible";	
		      document.getElementById("lane3").style.visibility = "visible";	
                         		  		   
		   }	
	      
	      //VIA PAULISTA BEGIN
	      
	      //VIA RONDON BEGIN
	      
	  	if (data_id == 1000) {
			document.getElementById("nameSat").innerHTML = "SAT 01";
			document.getElementById("siteID").innerHTML = "1000";
			document.getElementById("city").innerHTML = "Promiss&atilde;o";
			document.getElementById("road").innerHTML = "SP300";
			document.getElementById("km").innerHTML = "464,400";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "L";
			document.getElementById("lane2").innerHTML = "L";
			document.getElementById("lane3").innerHTML = "O";
			document.getElementById("lane4").innerHTML = "O";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
	      
	      //VIA RONDON END
	  	
	  	
	  	//VIA SUL BEGIN 
	  	
	  	if (data_id == 30101) {
			document.getElementById("nameSat").innerHTML = "SAT01";
			document.getElementById("siteID").innerHTML = "30101";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR101";
			document.getElementById("km").innerHTML = "0,060";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
	  	
		if (data_id == 30102) {
			document.getElementById("nameSat").innerHTML = "SAT02";
			document.getElementById("siteID").innerHTML = "30102";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR101";
			document.getElementById("km").innerHTML = "8,700";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
		
		if (data_id == 30103) {
			document.getElementById("nameSat").innerHTML = "SAT03";
			document.getElementById("siteID").innerHTML = "30103";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR101";
			document.getElementById("km").innerHTML = "15,940";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
		
		if (data_id == 30104) {
			document.getElementById("nameSat").innerHTML = "SAT04";
			document.getElementById("siteID").innerHTML = "30104";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR101";
			document.getElementById("km").innerHTML = "19,140";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
		
		if (data_id == 30105) {
			document.getElementById("nameSat").innerHTML = "SAT05";
			document.getElementById("siteID").innerHTML = "30105";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR101";
			document.getElementById("km").innerHTML = "23,900";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
		
		if (data_id == 30106) {
			document.getElementById("nameSat").innerHTML = "SAT06";
			document.getElementById("siteID").innerHTML = "30106";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR101";
			document.getElementById("km").innerHTML = "30,200";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
		
		if (data_id == 30107) {
			document.getElementById("nameSat").innerHTML = "SAT07";
			document.getElementById("siteID").innerHTML = "30107";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR101";
			document.getElementById("km").innerHTML = "43,300";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
		
		
		if (data_id == 30108) {
			document.getElementById("nameSat").innerHTML = "SAT08";
			document.getElementById("siteID").innerHTML = "30108";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR101";
			document.getElementById("km").innerHTML = "61,800";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
		
		
		if (data_id == 30109) {
			document.getElementById("nameSat").innerHTML = "SAT09";
			document.getElementById("siteID").innerHTML = "30109";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR101";
			document.getElementById("km").innerHTML = "66,600";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
		
		if (data_id == 30110) {
			document.getElementById("nameSat").innerHTML = "SAT10";
			document.getElementById("siteID").innerHTML = "30110";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR101";
			document.getElementById("km").innerHTML = "67,400";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
		
		if (data_id == 30111) {
			document.getElementById("nameSat").innerHTML = "SAT11";
			document.getElementById("siteID").innerHTML = "30111";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR101";
			document.getElementById("km").innerHTML = "69,400";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
		
		if (data_id == 30112) {
			document.getElementById("nameSat").innerHTML = "SAT12";
			document.getElementById("siteID").innerHTML = "30112";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR101";
			document.getElementById("km").innerHTML = "71,190";
			document.getElementById("lanes").innerHTML = "6";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";
			document.getElementById("lane5").innerHTML = "";
			document.getElementById("lane6").innerHTML = "";
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("header5").style.visibility = "visible";		
			document.getElementById("header6").style.visibility = "visible";
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
			document.getElementById("lane5").style.visibility = "visible";	
			document.getElementById("lane6").style.visibility = "visible";	
		}
		
		if (data_id == 30113) {
			document.getElementById("nameSat").innerHTML = "SAT13";
			document.getElementById("siteID").innerHTML = "30113";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR101";
			document.getElementById("km").innerHTML = "83,520";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
		
		if (data_id == 30114) {
			document.getElementById("nameSat").innerHTML = "SAT14";
			document.getElementById("siteID").innerHTML = "30114";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR290";
			document.getElementById("km").innerHTML = "0,150";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
		
		if (data_id == 30115) {
			document.getElementById("nameSat").innerHTML = "SAT15";
			document.getElementById("siteID").innerHTML = "30115";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR290";
			document.getElementById("km").innerHTML = "1,113";
			document.getElementById("lanes").innerHTML = "8";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";	
			document.getElementById("lane5").innerHTML = "";
			document.getElementById("lane6").innerHTML = "";
			document.getElementById("lane7").innerHTML = "";
			document.getElementById("lane8").innerHTML = "";	
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("header5").style.visibility = "visible";		
			document.getElementById("header6").style.visibility = "visible";
			document.getElementById("header7").style.visibility = "visible";		
			document.getElementById("header8").style.visibility = "visible";
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
			document.getElementById("lane5").style.visibility = "visible";	
			document.getElementById("lane6").style.visibility = "visible";	
			document.getElementById("lane7").style.visibility = "visible";	
			document.getElementById("lane8").style.visibility = "visible";	
		}
		
		if (data_id == 30116) {
			document.getElementById("nameSat").innerHTML = "SAT16";
			document.getElementById("siteID").innerHTML = "30116";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR290";
			document.getElementById("km").innerHTML = "3,100";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
		
		if (data_id == 30117) {
			document.getElementById("nameSat").innerHTML = "SAT17";
			document.getElementById("siteID").innerHTML = "30117";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR290";
			document.getElementById("km").innerHTML = "22,800";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
		
		if (data_id == 30118) {
			document.getElementById("nameSat").innerHTML = "SAT18";
			document.getElementById("siteID").innerHTML = "30118";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR290";
			document.getElementById("km").innerHTML = "34,280";
			document.getElementById("lanes").innerHTML = "6";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("lane5").innerHTML = "";
			document.getElementById("lane6").innerHTML = "";
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("header5").style.visibility = "visible";		
			document.getElementById("header6").style.visibility = "visible";
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
			document.getElementById("lane5").style.visibility = "visible";	
			document.getElementById("lane6").style.visibility = "visible";		
		}
		
		if (data_id == 30119) {
			document.getElementById("nameSat").innerHTML = "SAT19";
			document.getElementById("siteID").innerHTML = "30119";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR290";
			document.getElementById("km").innerHTML = "65,120";
			document.getElementById("lanes").innerHTML = "8";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane5").innerHTML = "";
			document.getElementById("lane6").innerHTML = "";
			document.getElementById("lane7").innerHTML = "";
			document.getElementById("lane8").innerHTML = "";
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("header5").style.visibility = "visible";		
			document.getElementById("header6").style.visibility = "visible";
			document.getElementById("header7").style.visibility = "visible";
			document.getElementById("header8").style.visibility = "visible";
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
			document.getElementById("lane5").style.visibility = "visible";	
			document.getElementById("lane6").style.visibility = "visible";
			document.getElementById("lane7").style.visibility = "visible";
			document.getElementById("lane8").style.visibility = "visible";
		}
		
		if (data_id == 30120) {
			document.getElementById("nameSat").innerHTML = "SAT20";
			document.getElementById("siteID").innerHTML = "30120";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR290";
			document.getElementById("km").innerHTML = "72,300";
			document.getElementById("lanes").innerHTML = "6";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("lane5").innerHTML = "";
			document.getElementById("lane6").innerHTML = "";
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("header5").style.visibility = "visible";		
			document.getElementById("header6").style.visibility = "visible";
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
			document.getElementById("lane5").style.visibility = "visible";	
			document.getElementById("lane6").style.visibility = "visible";	
		}
		
		if (data_id == 30121) {
			document.getElementById("nameSat").innerHTML = "SAT21";
			document.getElementById("siteID").innerHTML = "30121";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR290";
			document.getElementById("km").innerHTML = "83,140";
			document.getElementById("lanes").innerHTML = "8";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("lane5").innerHTML = "";
			document.getElementById("lane6").innerHTML = "";
			document.getElementById("lane7").innerHTML = "";
			document.getElementById("lane8").innerHTML = "";	
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("header5").style.visibility = "visible";		
			document.getElementById("header6").style.visibility = "visible";
			document.getElementById("header7").style.visibility = "visible";		
			document.getElementById("header8").style.visibility = "visible";
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
			document.getElementById("lane5").style.visibility = "visible";	
			document.getElementById("lane6").style.visibility = "visible";	
			document.getElementById("lane7").style.visibility = "visible";	
			document.getElementById("lane8").style.visibility = "visible";	
		}
		
		if (data_id == 30122) {
			document.getElementById("nameSat").innerHTML = "SAT22";
			document.getElementById("siteID").innerHTML = "30122";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR290";
			document.getElementById("km").innerHTML = "89,720";
			document.getElementById("lanes").innerHTML = "8";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("lane5").innerHTML = "";
			document.getElementById("lane6").innerHTML = "";
			document.getElementById("lane7").innerHTML = "";
			document.getElementById("lane8").innerHTML = "";	
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("header5").style.visibility = "visible";		
			document.getElementById("header6").style.visibility = "visible";
			document.getElementById("header7").style.visibility = "visible";		
			document.getElementById("header8").style.visibility = "visible";
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
			document.getElementById("lane5").style.visibility = "visible";	
			document.getElementById("lane6").style.visibility = "visible";	
			document.getElementById("lane7").style.visibility = "visible";	
			document.getElementById("lane8").style.visibility = "visible";	
		}
		
		if (data_id == 30123) {
			document.getElementById("nameSat").innerHTML = "SAT23";
			document.getElementById("siteID").innerHTML = "30123";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR290";
			document.getElementById("km").innerHTML = "93,500";
			document.getElementById("lanes").innerHTML = "8";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("lane5").innerHTML = "";
			document.getElementById("lane6").innerHTML = "";
			document.getElementById("lane7").innerHTML = "";
			document.getElementById("lane8").innerHTML = "";	
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("header5").style.visibility = "visible";		
			document.getElementById("header6").style.visibility = "visible";
			document.getElementById("header7").style.visibility = "visible";		
			document.getElementById("header8").style.visibility = "visible";
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
			document.getElementById("lane5").style.visibility = "visible";	
			document.getElementById("lane6").style.visibility = "visible";	
			document.getElementById("lane7").style.visibility = "visible";	
			document.getElementById("lane8").style.visibility = "visible";	
		}
		
		if (data_id == 30124) {
			document.getElementById("nameSat").innerHTML = "SAT24";
			document.getElementById("siteID").innerHTML = "30124";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR290";
			document.getElementById("km").innerHTML = "95,000";
			document.getElementById("lanes").innerHTML = "";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
		
		if (data_id == 30125) {
			document.getElementById("nameSat").innerHTML = "SAT25";
			document.getElementById("siteID").innerHTML = "30125";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR290";
			document.getElementById("km").innerHTML = "96,000";
			document.getElementById("lanes").innerHTML = "";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
		
		if (data_id == 30126) {
			document.getElementById("nameSat").innerHTML = "SAT26";
			document.getElementById("siteID").innerHTML = "30126";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR290";
			document.getElementById("km").innerHTML = "97,800";
			document.getElementById("lanes").innerHTML = "6";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("lane5").innerHTML = "";
			document.getElementById("lane6").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("header5").style.visibility = "visible";		
			document.getElementById("header6").style.visibility = "visible";			
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
			document.getElementById("lane5").style.visibility = "visible";	
			document.getElementById("lane6").style.visibility = "visible";	
			
		}
		
		if (data_id == 30127) {
			document.getElementById("nameSat").innerHTML = "SAT27";
			document.getElementById("siteID").innerHTML = "30127";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR386";
			document.getElementById("km").innerHTML = "191,220";
			document.getElementById("lanes").innerHTML = "2";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";		
		}
		
		if (data_id == 30128) {
			document.getElementById("nameSat").innerHTML = "SAT28";
			document.getElementById("siteID").innerHTML = "30128";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR386";
			document.getElementById("km").innerHTML = "215,000";
			document.getElementById("lanes").innerHTML = "2";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
		
		}
		
		if (data_id == 30129) {
			document.getElementById("nameSat").innerHTML = "SAT29";
			document.getElementById("siteID").innerHTML = "30129";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR386";
			document.getElementById("km").innerHTML = "245,060";
			document.getElementById("lanes").innerHTML = "2";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";		
		}
		
		if (data_id == 30130) {
			document.getElementById("nameSat").innerHTML = "SAT30";
			document.getElementById("siteID").innerHTML = "30130";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR386";
			document.getElementById("km").innerHTML = "268,300";
			document.getElementById("lanes").innerHTML = "2";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
	
		}
		
		if (data_id == 30131) {
			document.getElementById("nameSat").innerHTML = "SAT31";
			document.getElementById("siteID").innerHTML = "30131";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR386";
			document.getElementById("km").innerHTML = "281,660";
			document.getElementById("lanes").innerHTML = "2";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
		}
		
		if (data_id == 30132) {
			document.getElementById("nameSat").innerHTML = "SAT32";
			document.getElementById("siteID").innerHTML = "30132";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR386";
			document.getElementById("km").innerHTML = "301,220";
			document.getElementById("lanes").innerHTML = "2";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";			
		}
		
		if (data_id == 30133) {
			document.getElementById("nameSat").innerHTML = "SAT33";
			document.getElementById("siteID").innerHTML = "30133";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR386";
			document.getElementById("km").innerHTML = "317,020";
			document.getElementById("lanes").innerHTML = "2";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
	
		}
		
		if (data_id == 30134) {
			document.getElementById("nameSat").innerHTML = "SAT34";
			document.getElementById("siteID").innerHTML = "30134";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR386";
			document.getElementById("km").innerHTML = "338,420";
			document.getElementById("lanes").innerHTML = "2";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
		
		}
		
		if (data_id == 30135) {
			document.getElementById("nameSat").innerHTML = "SAT35";
			document.getElementById("siteID").innerHTML = "30135";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR386";
			document.getElementById("km").innerHTML = "344,320";
			document.getElementById("lanes").innerHTML = "2";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			
		}
		
		if (data_id == 30136) {
			document.getElementById("nameSat").innerHTML = "SAT36";
			document.getElementById("siteID").innerHTML = "30136";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR386";
			document.getElementById("km").innerHTML = "346,550";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
		
		if (data_id == 30137) {
			document.getElementById("nameSat").innerHTML = "SAT37";
			document.getElementById("siteID").innerHTML = "30137";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR386";
			document.getElementById("km").innerHTML = "351,640";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
		
		if (data_id == 30138) {
			document.getElementById("nameSat").innerHTML = "SAT38";
			document.getElementById("siteID").innerHTML = "30138";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR386";
			document.getElementById("km").innerHTML = "359,220";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
		
		if (data_id == 30139) {
			document.getElementById("nameSat").innerHTML = "SAT39";
			document.getElementById("siteID").innerHTML = "30139";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR386";
			document.getElementById("km").innerHTML = "363,080";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
		
		if (data_id == 30140) {
			document.getElementById("nameSat").innerHTML = "SAT40";
			document.getElementById("siteID").innerHTML = "30140";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR386";
			document.getElementById("km").innerHTML = "378,500";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
		
		if (data_id == 30141) {
			document.getElementById("nameSat").innerHTML = "SAT41";
			document.getElementById("siteID").innerHTML = "30141";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR386";
			document.getElementById("km").innerHTML = "384,750";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
		
		if (data_id == 30142) {
			document.getElementById("nameSat").innerHTML = "SAT42";
			document.getElementById("siteID").innerHTML = "30142";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR386";
			document.getElementById("km").innerHTML = "387,850";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
		
		if (data_id == 30143) {
			document.getElementById("nameSat").innerHTML = "SAT43";
			document.getElementById("siteID").innerHTML = "30143";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR386";
			document.getElementById("km").innerHTML = "411,900";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
	  	
		if (data_id == 30144) {
			document.getElementById("nameSat").innerHTML = "SAT44";
			document.getElementById("siteID").innerHTML = "30144";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR386";
			document.getElementById("km").innerHTML = "420,800";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
		
		if (data_id == 30145) {
			document.getElementById("nameSat").innerHTML = "SAT45";
			document.getElementById("siteID").innerHTML = "30145";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR386";
			document.getElementById("km").innerHTML = "434,600";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
		
		if (data_id == 30146) {
			document.getElementById("nameSat").innerHTML = "SAT46";
			document.getElementById("siteID").innerHTML = "30146";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR386";
			document.getElementById("km").innerHTML = "442,500";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
		
		if (data_id == 30147) {
			document.getElementById("nameSat").innerHTML = "SAT47";
			document.getElementById("siteID").innerHTML = "30147";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR386";
			document.getElementById("km").innerHTML = "3,500";
			document.getElementById("lanes").innerHTML = "4";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
		}
		
		if (data_id == 30148) {
			document.getElementById("nameSat").innerHTML = "SAT48";
			document.getElementById("siteID").innerHTML = "30148";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR386";
			document.getElementById("km").innerHTML = "14,500";
			document.getElementById("lanes").innerHTML = "6";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("lane5").innerHTML = "";
			document.getElementById("lane6").innerHTML = "";	
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("header5").style.visibility = "visible";		
			document.getElementById("header6").style.visibility = "visible";
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
			document.getElementById("lane5").style.visibility = "visible";	
			document.getElementById("lane6").style.visibility = "visible";	
		}
		
		if (data_id == 30149) {
			document.getElementById("nameSat").innerHTML = "SAT49";
			document.getElementById("siteID").innerHTML = "30149";
			document.getElementById("city").innerHTML = "";
			document.getElementById("road").innerHTML = "BR386";
			document.getElementById("km").innerHTML = "20,700";
			document.getElementById("lanes").innerHTML = "6";
			document.getElementById("lane1").innerHTML = "";
			document.getElementById("lane2").innerHTML = "";
			document.getElementById("lane3").innerHTML = "";
			document.getElementById("lane4").innerHTML = "";		
			document.getElementById("lane5").innerHTML = "";
			document.getElementById("lane6").innerHTML = "";	
			document.getElementById("header3").style.visibility = "visible";		
			document.getElementById("header4").style.visibility = "visible";	
			document.getElementById("header5").style.visibility = "visible";		
			document.getElementById("header6").style.visibility = "visible";
			document.getElementById("lane3").style.visibility = "visible";	
			document.getElementById("lane4").style.visibility = "visible";	
			document.getElementById("lane5").style.visibility = "visible";	
			document.getElementById("lane6").style.visibility = "visible";
		}
	  	
	   //VIA SUL END*/
	  		      
	   });
   });

	