$(() => {
    $(".speed-config").click(function() {
        $(this).closest(".col-speed").toggleClass("invert")
    })
})