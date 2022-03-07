$(table => {
    $('#table_occ').DataTable({
        language: {
            "search": "",
            searchPlaceholder: "Search"
        },
        "select": false,
        "Width": true,
        "scrollY": "55vh",
        "scrollCollapse": false,
        "paging": false,
        "bInfo": false,
        fixedColumns: true
    })
})