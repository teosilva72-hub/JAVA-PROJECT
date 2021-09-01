function getTrReal(){
	var table = $('#colas-tableReal').DataTable({
		"select": false,
		"autoWidth": true,			  	   	
		"scrollY": `${screen.availHeight / 1.5}px`,
		"scrollCollapse": true,
		"order": [[ 0, 'asc' ]],
		"paging": false,
		"bInfo" : false
	});
}

const callback_realtime_colas = response => {
	response = JSON.parse(response.body);

	let time;
	let lane = response.Lane;
	let table_lane = $(`#mainTable [lane=${lane}]`)

	switch (response.CMD) {
        case 3:
            switch (response.Lane_State) {
                case 1:
                    return
                
                case 2:
                    time = 2
                    break

                case 3:
                    time = 3
                    break
            }
            break
        
        case 4:
            switch (response.Local) {
                case 1:
                    time = 2
                    break
                
                case 2:
                    time = 1
                    break

                case 3:
                    time = 3
                    break
                
                default:
                    return
            }
            break
    }

	table_lane.text(`${time} minute`)
}

$(() => {
	getTrReal();

	consumeCOLAS({ callback_notify: callback_realtime_colas });
})