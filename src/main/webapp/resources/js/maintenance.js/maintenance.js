
setInterval(() => {

		let data = new Date();
		let n = data.getSeconds();
		let minute = data.getMinutes();

		if (minute == 1 || minute == 16 || minute == 31 || minute == 46) {
            if (n > 0 && n < 4){
                init();
                location.href = location.protocol + '//' + location.host + location.pathname
                window.location.reload();
            }
	    }
			     
}, 3000)


const init = () => {

		// PRELOADER LOADING

		$('#preloader .inner').fadeOut();
  		$('#preloader').fadeOut('slow');
  		$('body').delay(350).css({'overflow' : 'visible'});

}

// -------------------------------------------------------------------


