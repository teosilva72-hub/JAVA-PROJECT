var user;
var loginAccount = {
    user: 'a',
    pass: 'a'
}

window.asterisk = JSON.parse(localStorage.getItem("asterisk"))
window.rabbitmq = JSON.parse(localStorage.getItem("rabbitmq"))
connectSOS(`LogIn;${loginAccount.user};${loginAccount.pass}`).then(async response => {
    while (!window.asterisk)
        await new Promise(r => setTimeout(r, 500))
    user = {
        //  User Name
        "User" : response.SIPAccount1,
        //  Password
        "Pass" : response.SIPPassword,
        //  Auth Realm
        "Realm"   : window.asterisk.address,
        // Display Name
        "Display" : loginAccount.user,
        // WebSocket URL
        "WSServer"  : `wss://${window.asterisk.address}:${window.asterisk.port}/ws`
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
