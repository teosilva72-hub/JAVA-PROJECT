var user;
var loginAccount = {
    user: 'a',
    pass: 'a'
}
connectSOS(`LogIn;${loginAccount.user};${loginAccount.pass}`).then(response => {
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

    return connectSOS("GetAllUsers")
}).then(response => {
    for(r of response)
        if(r.Name == loginAccount.user && r.Password == loginAccount.pass) {
            loginAccount.ID = r.ID
            break
        }
})
// {"Identity":"LogIn","SIPAccount1":"401","SIPPassword":"Sip401","ErrorID":0}
