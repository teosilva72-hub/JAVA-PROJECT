
// VALIDATION DEFINITIONS

$(function () {

    var validation = document.forms.validation;
    
    for (messages of validation)
        window[messages.name] = messages.value

        validationTemplate6("#panel", requiredEquipmentMsg);

        //Validate on change value
        validateOnChange('#equip'); 
        
     // STATUS COLOR   
        
     if(panelStatus == 0){
        
          $('#status').html('OFFLINE');
          $('#status').css('color', '#FF0000');
          
      }else  { 
               $('#status').html('ONLINE');
               $('#status').css('color', '#00FF0D');
      }
      
      // STATUS COLOR
        
});

// --------------------------------------------------------

//HIDE MESSAGE DISPLAY	
function hideInfoMessage() {
    setTimeout(function () {
        $('#info').hide();
    }, 5000);
}

//SHOW MESSAGE DISPLAY	
function showInfoMessage() {
    $('#info').show();
}

// ----------------------------------------------------------

             


