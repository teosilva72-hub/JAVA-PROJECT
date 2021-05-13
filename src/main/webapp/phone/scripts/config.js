var ws = new WebSocket(`ws://192.168.0.51:15674/ws`);
var client = Stomp.over(ws);
var user;
var loginAccount = {
    user: 'a',
    pass: 'a'
}
var on_connect = function() {
    client.send("/amq/queue/ClientRequest", {"reply-to": "/temp-queue/ClientRequest", durable: false}, `"LogIn;${loginAccount.user};${loginAccount.pass}"`)
};

client.onreceive = function(m) {
    let response = JSON.parse(m.body);

    user = {
        //  User Name
        "User" : response.SIPAccount1 || "123",
        //  Password
        "Pass" : response.SIPPassword || "i68Oi68O",
        //  Auth Realm
        "Realm"   : "192.168.0.5",
        // Display Name
        "Display" : loginAccount.user,
        // WebSocket URL
        "WSServer"  : "ws://192.168.0.5:8088/asterisk/ws"
    };

    client.disconnect()
}

client.connect("tracevia", "trcv1234", on_connect);
// {"Identity":"LogIn","SIPAccount1":"401","SIPPassword":"Sip401","ErrorID":0}
