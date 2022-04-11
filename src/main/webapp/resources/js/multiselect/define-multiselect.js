
 //CREATE / DEFINE MULTISELECT
function defineMultiselect(id, label){
	   	
    $(`${id}, [multiple=multiple]`).each(function() {
        let self = $(this);
        let allSelected = self.attr("field") || "All";
        let nonSelected = self.attr("field") || label;

        self.multiselect({
            columns: 1,       
            allSelectedText: allSelected,
            maxHeight: 200,
            includeSelectAllOption: true,
            buttonWidth:'100%',
            nonSelectedText: nonSelected
            
        }).multiselect('selectAll', false)
        .multiselect('updateButtonText');
    })
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
function resetMultiselectValues(id, defaultLabel, array){
	
	//Destroy multiselect
	destroyMultiselect(id);

    //Fill values
    checkedByDefault(id, array);  

    //Define multiselect values again   
    defineMultiselect(id, defaultLabel)
	
}

