/*
	-- Restrinção de pesquisa --
	> Pesquisa apenas pelo dia <
	autor: Mateus S Silva
*/
$((res)=>{
	let row = $('.modal-body .col-6')
	let x = []
	let icon = $('.fa-calendar-alt')[1]
	icon.style.display = 'none'
	
	$('#Fecha-end').addClass('d-none')
		$('#Fecha-start').change(()=>{
    	$('#Fecha-end').val($('#Fecha-start').val())
    })
    $('[type=search]').addClass('d-none')
    $('#btn-form-confirm').click(()=>{
		setTimeout(()=>{
			console.log('aqui')
			$('[type=search]').addClass('d-none')
			$('.dataTables_filter').addClass('d-none')
		},9000)
	})
})