
function alertOptions(id, message) {
	$(id).css("display", "block");
	$(id +' .textAlert').html(message)
	setTimeout(function () {
		$(id).fadeOut('fast');
	}, 2000);
}