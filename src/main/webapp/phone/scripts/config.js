var user;
var loginAccount = {
    user: 'a',
    pass: 'a'
}
connectSOS(`LogIn;${loginAccount.user};${loginAccount.pass}`).then(response => {
    user = {
        //  User Name
        "User" : response.SIPAccount1,
        //  Password
        "Pass" : response.SIPPassword,
        //  Auth Realm
        "Realm"   : "192.168.0.127",
        // Display Name
        "Display" : loginAccount.user,
        // WebSocket URL
        "WSServer"  : "wss://192.168.0.127:8089/ws"
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
