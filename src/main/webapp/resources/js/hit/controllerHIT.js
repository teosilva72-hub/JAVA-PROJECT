$((init) => {
  table();
  let hit = $("#iconHit");
  hit.append(dir);
  setTimeout((e) => {
    newText();
    resetInput();
  }, 100);
});
function resetInput() {
  $("#input_one").click((a) => {
    $("#input_one").val("");
  });
  $("#input_two").click((b) => {
    $("#input_two").val("");
  });
  $("#input_three").click((c) => {
    $("#input_three").val("");
  });
}
function newText() {
  $("#input_one").keyup((a) => {
    var msg1 = $("#input_one").val();
    let msg = [];
    for (var i = 0; i < msg1.length; i++) {
      msg[i] = msg1[i];
      $(`#box-${i}`).text(msg[i]);
    }
  });
  $("#input_two").keyup((a) => {
    var msg1 = $("#input_two").val();
    let msg = [];
    for (var i = 0; i < msg1.length; i++) {
      msg[i] = msg1[i];
      $(`#Msg_${i}`).text(msg[i]);
    }
  });
  $("#input_three").keyup((a) => {
    var msg1 = $("#input_three").val();
    let msg = [];
    for (var i = 0; i < msg1.length; i++) {
      msg[i] = msg1[i];
      $(`#msg_${i}`).text(msg[i]);
    }
  });
}

function dadosTable(msg1, msg2, msg3, name) {
  let msg = [];
  for (var i = 0; i < msg1.length; i++) {
    msg[i] = msg1[i];
    $(`#box-${i}`).text(msg[i]);
  }
  for (var i = 0; i < msg2.length; i++) {
    msg[i] = msg2[i];
    $(`#Msg_${i}`).text(msg[i]);
  }
  for (var i = 0; i < msg3.length; i++) {
    msg[i] = msg3[i];
    $(`#msg_${i}`).text(msg[i]);
  }
}
function iconHit(icon) {
  let hit = $("#iconHit");
  if (Number(icon) > 0) {
    //success
    switch (icon) {
      case "1":
        hit.append(sup);
        break;
      case "2":
        hit.append(inf);
        break;
      case "3":
        hit.append(dir);
        break;
      case "4":
        hit.append(esq);
        break;
    }
  } else {
    //error
  }
}

let esq, dir, sup, inf;
esq = $(
  `<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-down-left iconEsq" viewBox="0 0 16 16"><path fill-rule="evenodd" d="M2 13.5a.5.5 0 0 0 .5.5h6a.5.5 0 0 0 0-1H3.707L13.854 2.854a.5.5 0 0 0-.708-.708L3 12.293V7.5a.5.5 0 0 0-1 0v6z"/></svg>`
);
dir = $(
  `<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-down-right iconDir" viewBox="0 0 16 16"><path fill-rule="evenodd" d="M14 13.5a.5.5 0 0 1-.5.5h-6a.5.5 0 0 1 0-1h4.793L2.146 2.854a.5.5 0 1 1 .708-.708L13 12.293V7.5a.5.5 0 0 1 1 0v6z"/></svg>`
);
sup = $(
  `<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-up-left" viewBox="0 0 16 16"><path fill-rule="evenodd" d="M2 2.5a.5.5 0 0 1 .5-.5h6a.5.5 0 0 1 0 1H3.707l10.147 10.146a.5.5 0 0 1-.708.708L3 3.707V8.5a.5.5 0 0 1-1 0v-6z"/></svg>`
);
inf = $(
  `<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-up-right" viewBox="0 0 16 16"><path fill-rule="evenodd" d="M14 2.5a.5.5 0 0 0-.5-.5h-6a.5.5 0 0 0 0 1h4.793L2.146 13.146a.5.5 0 0 0 .708.708L13 3.707V8.5a.5.5 0 0 0 1 0v-6z"/></svg>`
);

function table() {
  let table = $("#table-hit").DataTable({
    language: {
      search: "",
      searchPlaceholder: "Search",
    },
    select: false,
    Width: true,
    scrollY: "55vh",
    scrollCollapse: false,
    paging: false,
    bInfo: false,
    fixedColumns: true,
  });
  $("#table-hit tbody").on("click", "tr", function () {
    var id = $(table.row(this).data()[0]).text();
    $("#tableId").val(id);
    setTimeout((g) => {
      $("#setId").click();
    }, 100);
  });
}

function addAnimation(id) {
  let hit = $(`#hit${id}`);
  hit.addClass("animationHit");
  setTimeout((e) => {
    hit.removeClass("animationHit");
  }, 60000);
  hit.click(a => {
    hit.removeClass("animationHit");
  });
}
