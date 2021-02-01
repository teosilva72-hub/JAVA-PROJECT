
 //CREATE / DEFINE MULTISELECT
function defineMultiselect(id, label){
	   	
    $(id).multiselect({
        columns: 1,       
        allSelectedText: 'All',
        maxHeight: 200,
        includeSelectAllOption: true,
        buttonWidth:'100%',
        nonSelectedText: label
        
    });
}

//CHECKED BY DEFAULT
function checkedByDefault(id, valArray){		
	    $(id).val(valArray);
}

//DESTROY MULTISELECT TO REBUILD
function destroyMultiselect(id){
	$(id).multiselect('destroy');	
}

//REBUILD MULTISELECT WITH VALUES PARAMETERS
function resetMultiselectValues(id, resetLabel, array){
	
	//Destroy multiselect
	destroyMultiselect(id);

    //Fill values
    checkedByDefault(id, array);  

    //Define multiselect values again   
    defineMultiselect(id, resetLabel)
	
}

