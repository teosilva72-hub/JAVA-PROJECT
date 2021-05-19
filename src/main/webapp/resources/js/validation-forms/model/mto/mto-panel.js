 ////////////////////////////////////////
///// VALIDATION STATION METEO	
////////////////////////////////////////		
	
		/* USE IN STATION METEO */	
	function weatherStationValidation(form, equip_message){
				
		 $(form).validate({		    	                 
		         rules: {		       
		           equip: {
		            required: true
		           }   
		          },

		          messages: {						
		              equip: { required: equip_message }                                    
		            },
		            
		            errorClass : "error",
                    validClass: "success",                  
		            errorElement: "label", 

                    errorPlacement: function ( error, element ) {
	                  //Place elements for place errors	 
                
                    },
								
					success: function ( label, element ) {	
											 	
					  //If no have errors set check success status	
					 //Show span validation icon
                     $(element.form).find("span[for="+ element.id +"]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');
									
				     //FontAwesome Icon Check for success
                     $(element.form).find("span[for="+element.id+"]").html("<i class='fa fa-check success'></i>");
        		
				     }, 		
                         
		              // use highlight and unhighlight
		             highlight: function(element, errorClass, validClass) {	        
                     $(element.form).find("label[for=" + element.id + "]").addClass(errorClass);

                     $(element.form).find("input[id="+element.id+"]").removeClass('valid').addClass('invalid');
                     $(element.form).find("select[id="+element.id+"]").removeClass('valid').addClass('invalid');         
                                    
                     //Show span validation icon
                     $(element.form).find("span[for="+ element.id +"]").removeClass('valid-icon-hidden').addClass('valid-icon-visible');

                     //FontAwesome Icon Times for error
                     $(element.form).find("span[for="+element.id+"]").html("<i class='fa fa-times error'></i>");
		            
                    },

		          unhighlight: function(element, errorClass, validClass) {		       
                  $(element.form).find("label[for=" + element.id + "]").removeClass(errorClass);  
               
                  $(element.form).find("input[id="+element.id+"]").removeClass('invalid').addClass('valid');
                  $(element.form).find("select[id="+element.id+"]").removeClass('invalid').addClass('valid');
                
                  //FontAwesome Icon Check for success
                  $(element.form).find("span[for="+element.id+"]").html("<i class='fa fa-check success'></i>");
                
                 }                  
		     });				
	}
		  
	 ////////////////////////////////////////
	  ///// VALIDATION STATION METEO	
	////////////////////////////////////////	
	
	/////////////////////////////////////////////////
	///// VALIDATE FIELDS BY ID ON CHANGE
	////////////////////////////////////////////////
	function validateOnChange(id){
	 $(id).on('change', function(){
	        if($(id).valid()) 
	            $(id).removeClass('invalid');
	            	            
	            else  $(id).removeClass('valid');
	           	        
	    })
      } // End ValidateOnChange
      
   ////////////////////////////////////////////////
   //// VALIDATE FIELDS BY ID ON CHANGE
  ////////////////////////////////////////////////

	
	 ////////////////////////////////////////
	  ///// CHECK STATUS	
	////////////////////////////////////////		
	
	function checkMtoStatus() {
			
		var status = document.getElementById("panel:hidden").value;
		
		var info = document.getElementById("status"); 
						
		if(status == 0)
		{				
		  $("#status").css("color","red");
		  info.innerHTML = "  OFFLINE";
		}
		else
		{	
		  $("#status").css("color","green");
		  info.innerHTML = "  ONLINE";
		}
		}
		
      ////////////////////////////////////////
	  ///// CHECK STATUS	
	////////////////////////////////////////			