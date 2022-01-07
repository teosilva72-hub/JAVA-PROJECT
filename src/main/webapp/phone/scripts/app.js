/* globals SIP,user,moment, Stopwatch */

var ctxSip;

async function initPhone() {

    if (typeof(user) === 'undefined') {
        user = JSON.parse(localStorage.getItem('SIPCreds'));
    }

    while (user === undefined || user === null)
        await sleep(500)

    ctxSip = {

        config  : {
            password        : user.Pass,
            displayName     : user.Display,
            uri             : 'sip:'+user.User+'@'+user.Realm,
            wsServers       : user.WSServer,
            registerExpires : 30,
            traceSip        : true,
            log : {
                level       : 0,
            }
        },
        ringtone            : document.getElementById('ringtone'),
        ringbacktone        : document.getElementById('ringbacktone'),
        dtmfTone            : document.getElementById('dtmfTone'),

        Sessions            : [],
        callTimers          : {},
        callActiveID        : null,
        callIncomingID      : null,
        process             : 0,
        callVolume          : 1,
        Stream              : null,
        hasSecondaryAudio   : null,

        /**
         * Parses a SIP uri and returns a formatted US phone number.
         *
         * @param  {string} phone number or uri to format
         * @return {string}       formatted number
         */
        formatPhone : function(phone) {

            var num;

            if (phone.indexOf('@')) {
                num =  phone.split('@')[0];
            } else {
                num = phone;
            }

            num = num.toString().replace(/[^0-9]/g, '');

            if (num.length === 10) {
                return '(' + num.substr(0, 3) + ') ' + num.substr(3, 3) + '-' + num.substr(6,4);
            } else if (num.length === 11) {
                return '(' + num.substr(1, 3) + ') ' + num.substr(4, 3) + '-' + num.substr(7,4);
            } else {
                return num;
            }
        },

        // Sound methods
        startRingTone : function() {
            localStorage.setItem("RingTone", "true")
        },
        
        stopRingTone : function() {
            localStorage.removeItem("RingTone")
        },
        
        startRingbackTone : function() {
            localStorage.setItem("RingBackTone", "true")
        },
        
        stopRingbackTone : function() {
            localStorage.removeItem("RingBackTone")
        },

        // Genereates a rendom string to ID a call
        getUniqueID : function() {
            return Math.random().toString(36).substring(2, 9);
        },

        newSession : function(newSess) {

            newSess.displayName = newSess.remoteIdentity.displayName || newSess.remoteIdentity.uri.user;
            newSess.ctxid       = newSess.ctxid || ctxSip.getUniqueID();

            var status;

            if (!ctxSip.callActiveID && !ctxSip.callIncomingID)
                if (newSess.direction === 'incoming') {
                    status = "Incoming: "+ newSess.displayName;
                    ctxSip.startRingTone();
                } else {
                    status = "Trying: "+ newSess.displayName;
                    ctxSip.startRingbackTone();
                }

            ctxSip.logCall(newSess, 'ringing');

            ctxSip.setCallSessionStatus(status);

            // EVENT CALLBACKS

            newSess.on('progress',function(e) {
                if (e.direction === 'outgoing') {
                    ctxSip.setCallSessionStatus('Calling...');
                }
            });

            newSess.on('connecting',function(e) {
                if (e.direction === 'outgoing') {
                    ctxSip.setCallSessionStatus('Connecting...');
                }
            });

            newSess.on('accepted',function(e) {
                // If there is another active call, hold it
                if (ctxSip.callActiveID && ctxSip.callActiveID !== newSess.ctxid) {
                    ctxSip.phoneHoldButtonPressed(ctxSip.callActiveID);
                }

                ctxSip.stopRingbackTone();
                ctxSip.stopRingTone();
                ctxSip.setCallSessionStatus('Answered');
                ctxSip.logCall(newSess, 'answered');
                ctxSip.callActiveID = newSess.ctxid;
            });

            newSess.on('hold', function(e) {
                ctxSip.callActiveID = null;
                ctxSip.logCall(newSess, 'holding');
            });

            newSess.on('unhold', function(e) {
                ctxSip.logCall(newSess, 'resumed');
                ctxSip.callActiveID = newSess.ctxid;
            });

            newSess.on('muted', function(e) {
                ctxSip.Sessions[newSess.ctxid].isMuted = true;
                ctxSip.setCallSessionStatus("Muted");
            });

            newSess.on('unmuted', function(e) {
                ctxSip.Sessions[newSess.ctxid].isMuted = false;
                ctxSip.setCallSessionStatus("Answered");
            });

            newSess.on('cancel', function(e) {
                ctxSip.stopRingTone();
                ctxSip.stopRingbackTone();
                ctxSip.setCallSessionStatus("Canceled");
                if (this.direction === 'outgoing') {
                    ctxSip.callActiveID = null;
                    newSess             = null;
                    ctxSip.logCall(this, 'ended');
                }
            });

            newSess.on('bye', function(e) {
                ctxSip.stopRingTone();
                ctxSip.stopRingbackTone();
                ctxSip.setCallSessionStatus("");
                ctxSip.logCall(newSess, 'ended');
                ctxSip.callActiveID = null;
                newSess             = null;
            });

            newSess.on('failed',function(e) {
                ctxSip.stopRingTone();
                ctxSip.stopRingbackTone();
                ctxSip.setCallSessionStatus('Terminated');
            });

            newSess.on('rejected',function(e) {
                ctxSip.stopRingTone();
                ctxSip.stopRingbackTone();
                ctxSip.setCallSessionStatus('Rejected');
                ctxSip.callActiveID = null;
                ctxSip.logCall(this, 'ended');
                newSess             = null;
            });

            ctxSip.Sessions[newSess.ctxid] = newSess;

        },

        // getUser media request refused or device was not present
        getUserMediaFailure : function(e) {
            window.console.error('getUserMedia failed:', e);
            // ctxSip.setError(true, 'Media Error.', 'You must allow access to your microphone.  Check the address bar.', true);
        },

        getUserMediaSuccess : async function(stream) {
            ctxSip.Stream = stream;
        },

        /**
         * sets the ui call status field
         *
         * @param {string} status
         */
        setCallSessionStatus : function(status) {
            localStorage.setItem("CallBoxReaction", status)
            $('#txtCallStatus').html(status);
        },

        /**
         * sets the ui connection status field
         *
         * @param {string} status
         */
        setStatus : function(status) {
            let status_icon = '<svg width="15" class="svg-inline--fa fa-signal fa-w-20" aria-hidden="true" focusable="false" data-prefix="fa" data-icon="signal" role="img" viewBox="0 0 640 512" data-fa-i2svg=""><path fill="currentColor" d="M216 288h-48c-8.84 0-16 7.16-16 16v192c0 8.84 7.16 16 16 16h48c8.84 0 16-7.16 16-16V304c0-8.84-7.16-16-16-16zM88 384H40c-8.84 0-16 7.16-16 16v96c0 8.84 7.16 16 16 16h48c8.84 0 16-7.16 16-16v-96c0-8.84-7.16-16-16-16zm256-192h-48c-8.84 0-16 7.16-16 16v288c0 8.84 7.16 16 16 16h48c8.84 0 16-7.16 16-16V208c0-8.84-7.16-16-16-16zm128-96h-48c-8.84 0-16 7.16-16 16v384c0 8.84 7.16 16 16 16h48c8.84 0 16-7.16 16-16V112c0-8.84-7.16-16-16-16zM600 0h-48c-8.84 0-16 7.16-16 16v480c0 8.84 7.16 16 16 16h48c8.84 0 16-7.16 16-16V16c0-8.84-7.16-16-16-16z"></path></svg> '+status
            localStorage.setItem("CallBoxStatus", status_icon)
            $("#txtRegStatus").html(status_icon);
        },

        /**
         * logs a call to localstorage
         *
         * @param  {object} session
         * @param  {string} status Enum 'ringing', 'answered', 'ended', 'holding', 'resumed'
         */
        logCall : function(session, status) {

            var log = {
                    clid : session.displayName || "Desconhecido",
                    uri  : (session.remoteIdentity.uri || "Desconhecido").toString(),
                    id   : session.ctxid,
                    time : new Date().getTime()
                },
                calllog = JSON.parse(localStorage.getItem('sipCalls'));

            if (!calllog) { calllog = {}; }

            if (!calllog.hasOwnProperty(session.ctxid) || status == "ringing") {
                calllog[log.id] = {
                    id    : log.id,
                    clid  : log.clid,
                    uri   : log.uri,
                    start : log.time,
                    flow  : session.direction,
                    bdid  : session.EquipmentID
                };
            }

            calllog[log.id].owner = session.owner ? session.owner : calllog[log.id].owner

            if (session.isMuted !== undefined)
                calllog[log.id].isMuted = session.isMuted

            if (status === 'ended') {
                calllog[log.id].stop = log.time;
            }

            if (status === 'ended' && calllog[log.id].status === 'ringing') {
                calllog[log.id].status = 'missed';
            } else {
                calllog[log.id].status = status ? status : calllog[log.id].status;
            }

            localStorage.setItem('sipCalls', JSON.stringify(calllog));
            ctxSip.logShow();
        },

        /**
         * adds a ui item to the call log
         *
         * @param  {object} item log item
         */
        logItem : function(item) {

            var callActive = (item.status !== 'ended' && item.status !== 'missed'),
                timeStart  = typeof item.start == "string" ? new Date(item.start).getTime() : item.start,
                timeStop    = typeof item.stop == "string" ? new Date(item.stop).getTime() : item.stop,
                callLength = (item.status !== 'ended')? '<span id="'+item.id+'"></span>': moment.duration(timeStop - timeStart).humanize(),
                callClass  = '',
                callIcon,
                i;

            switch (item.status) {
                case 'ringing'  :
                    callClass = 'list-group-item-success';
                    callIcon  = '<svg class="svg-inline--fa fa-bell fa-w-14 fa-fw" aria-hidden="true" focusable="false" data-prefix="fa" data-icon="bell" role="img" width="12" height="12" viewBox="0 0 448 512" data-fa-i2svg=""><path fill="currentColor" d="M224 512c35.32 0 63.97-28.65 63.97-64H160.03c0 35.35 28.65 64 63.97 64zm215.39-149.71c-19.32-20.76-55.47-51.99-55.47-154.29 0-77.7-54.48-139.9-127.94-155.16V32c0-17.67-14.32-32-31.98-32s-31.98 14.33-31.98 32v20.84C118.56 68.1 64.08 130.3 64.08 208c0 102.3-36.15 133.53-55.47 154.29-6 6.45-8.66 14.16-8.61 21.71.11 16.4 12.98 32 32.1 32h383.8c19.12 0 32-15.6 32.1-32 .05-7.55-2.61-15.27-8.61-21.71z"></path></svg>';
                    break;

                case 'missed'   :
                    callClass = 'list-group-item-danger';
                    if (item.flow === "incoming") { callIcon = '<svg class="svg-inline--fa fa-chevron-left fa-w-10 fa-fw" aria-hidden="true" focusable="false" data-prefix="fa" data-icon="chevron-left" role="img" width="12" height="12" viewBox="0 0 320 512" data-fa-i2svg=""><path fill="currentColor" d="M34.52 239.03L228.87 44.69c9.37-9.37 24.57-9.37 33.94 0l22.67 22.67c9.36 9.36 9.37 24.52.04 33.9L131.49 256l154.02 154.75c9.34 9.38 9.32 24.54-.04 33.9l-22.67 22.67c-9.37 9.37-24.57 9.37-33.94 0L34.52 272.97c-9.37-9.37-9.37-24.57 0-33.94z"></path></svg>'; }
                    if (item.flow === "outgoing") { callIcon = 'fa-chevron-right'; }
                    break;

                case 'holding'  :
                    callClass = 'list-group-item-warning';
                    callIcon  = '<svg class="svg-inline--fa fa-pause fa-w-14" aria-hidden="true" focusable="false" data-prefix="fa" data-icon="pause" role="img" width="12" height="12" viewBox="0 0 448 512" data-fa-i2svg=""><path fill="currentColor" d="M144 479H48c-26.5 0-48-21.5-48-48V79c0-26.5 21.5-48 48-48h96c26.5 0 48 21.5 48 48v352c0 26.5-21.5 48-48 48zm304-48V79c0-26.5-21.5-48-48-48h-96c-26.5 0-48 21.5-48 48v352c0 26.5 21.5 48 48 48h96c26.5 0 48-21.5 48-48z"></path></svg>';
                    break;

                case 'answered' :
                case 'resumed'  :
                    callClass = 'list-group-item-info';
                    callIcon  = '<svg class="svg-inline--fa fa-bell fa-w-14 fa-fw" aria-hidden="true" focusable="false" data-prefix="fa" data-icon="bell" role="img" width="12" height="12" viewBox="0 0 448 512" data-fa-i2svg=""><path fill="currentColor" d="M224 512c35.32 0 63.97-28.65 63.97-64H160.03c0 35.35 28.65 64 63.97 64zm215.39-149.71c-19.32-20.76-55.47-51.99-55.47-154.29 0-77.7-54.48-139.9-127.94-155.16V32c0-17.67-14.32-32-31.98-32s-31.98 14.33-31.98 32v20.84C118.56 68.1 64.08 130.3 64.08 208c0 102.3-36.15 133.53-55.47 154.29-6 6.45-8.66 14.16-8.61 21.71.11 16.4 12.98 32 32.1 32h383.8c19.12 0 32-15.6 32.1-32 .05-7.55-2.61-15.27-8.61-21.71z"></path></svg>';
                    break;

                case 'ended'  :
                    if (item.flow === "incoming") { callIcon = '<svg class="svg-inline--fa fa-chevron-left fa-w-10 fa-fw" aria-hidden="true" focusable="false" data-prefix="fa" data-icon="chevron-left" role="img" width="12" height="12" viewBox="0 0 320 512" data-fa-i2svg=""><path fill="currentColor" d="M34.52 239.03L228.87 44.69c9.37-9.37 24.57-9.37 33.94 0l22.67 22.67c9.36 9.36 9.37 24.52.04 33.9L131.49 256l154.02 154.75c9.34 9.38 9.32 24.54-.04 33.9l-22.67 22.67c-9.37 9.37-24.57 9.37-33.94 0L34.52 272.97c-9.37-9.37-9.37-24.57 0-33.94z"></path></svg>'; }
                    if (item.flow === "outgoing") { callIcon = 'fa-chevron-right'; }
                    break;
            }


            i  = '<div class="list-group-item sip-logitem clearfix '+callClass+'" data-uri="'+item.uri+'" data-sessionid="'+item.id+'">';
            i += '<div class="clearfix"><div class="float-left">';
            i += callIcon+' <strong>'+ctxSip.formatPhone(item.uri)+'</strong><br><small>'+moment(item.start).format('MM/DD hh:mm:ss a')+'</small>';
            i += '</div>';
            i += '<div class="float-right text-right"><em>'+item.clid+'</em><br>' + callLength+'</div></div>';

            if (callActive) {
                i += '<div class="btn-group btn-group-xs float-right">';
                if (item.status === 'ringing' && item.flow === 'incoming') {
                    i += '<button class="btn btn-xs btn-success btnCall" title="Call"><svg class="svg-inline--fa fa-phone fa-w-16" aria-hidden="true" focusable="false" data-prefix="fa" data-icon="phone" role="img" width="16" height="16" viewBox="0 0 512 512" data-fa-i2svg=""><path fill="currentColor" d="M493.4 24.6l-104-24c-11.3-2.6-22.9 3.3-27.5 13.9l-48 112c-4.2 9.8-1.4 21.3 6.9 28l60.6 49.6c-36 76.7-98.9 140.5-177.2 177.2l-49.6-60.6c-6.8-8.3-18.2-11.1-28-6.9l-112 48C3.9 366.5-2 378.1.6 389.4l24 104C27.1 504.2 36.7 512 48 512c256.1 0 464-207.5 464-464 0-11.2-7.7-20.9-18.6-23.4z"></path></svg></button>';
                } else if (item.owner) {
                    i += '<button class="btn btn-xs btn-primary btnHoldResume" title="Hold"><svg class="svg-inline--fa fa-pause fa-w-14" aria-hidden="true" focusable="false" data-prefix="fa" data-icon="pause" role="img" width="16" height="16" viewBox="0 0 448 512" data-fa-i2svg=""><path fill="currentColor" d="M144 479H48c-26.5 0-48-21.5-48-48V79c0-26.5 21.5-48 48-48h96c26.5 0 48 21.5 48 48v352c0 26.5-21.5 48-48 48zm304-48V79c0-26.5-21.5-48-48-48h-96c-26.5 0-48 21.5-48 48v352c0 26.5 21.5 48 48 48h96c26.5 0 48-21.5 48-48z"></path></svg></i></button>';
                    // i += '<button class="btn btn-xs btn-info btnTransfer" title="Transfer"><i class="fa fa-random"></i></button>';
                    i += '<button class="btn btn-xs btn-warning btnMute" title="Mute">';
                    if (!item.isMuted)
                        i += '<svg width="16" height="16" class="svg-inline--fa fa-microphone fa-w-11 fa-fw" aria-hidden="true" focusable="false" data-prefix="fa" data-icon="microphone" role="img" viewBox="0 0 352 512" data-fa-i2svg=""><path fill="currentColor" d="M176 352c53.02 0 96-42.98 96-96V96c0-53.02-42.98-96-96-96S80 42.98 80 96v160c0 53.02 42.98 96 96 96zm160-160h-16c-8.84 0-16 7.16-16 16v48c0 74.8-64.49 134.82-140.79 127.38C96.71 376.89 48 317.11 48 250.3V208c0-8.84-7.16-16-16-16H16c-8.84 0-16 7.16-16 16v40.16c0 89.64 63.97 169.55 152 181.69V464H96c-8.84 0-16 7.16-16 16v16c0 8.84 7.16 16 16 16h160c8.84 0 16-7.16 16-16v-16c0-8.84-7.16-16-16-16h-56v-33.77C285.71 418.47 352 344.9 352 256v-48c0-8.84-7.16-16-16-16z"></path></svg>';
                    else
                        i += '<svg width="16" height="16" fill="currentColor" class="bi bi-mic-mute-fill" viewBox="0 0 16 16"><path d="M13 8c0 .564-.094 1.107-.266 1.613l-.814-.814A4.02 4.02 0 0 0 12 8V7a.5.5 0 0 1 1 0v1zm-5 4c.818 0 1.578-.245 2.212-.667l.718.719a4.973 4.973 0 0 1-2.43.923V15h3a.5.5 0 0 1 0 1h-7a.5.5 0 0 1 0-1h3v-2.025A5 5 0 0 1 3 8V7a.5.5 0 0 1 1 0v1a4 4 0 0 0 4 4zm3-9v4.879L5.158 2.037A3.001 3.001 0 0 1 11 3z"/><path d="M9.486 10.607 5 6.12V8a3 3 0 0 0 4.486 2.607zm-7.84-9.253 12 12 .708-.708-12-12-.708.708z"/></svg>';
                    i += '</button>';
                    i += '<button class="btn btn-xs btn-danger btnHangUp" title="Hangup"><svg class="svg-inline--fa fa-stop fa-w-14" aria-hidden="true" focusable="false" data-prefix="fa" data-icon="stop" role="img" width="16" height="16" viewBox="0 0 448 512" data-fa-i2svg=""><path fill="currentColor" d="M400 32H48C21.5 32 0 53.5 0 80v352c0 26.5 21.5 48 48 48h352c26.5 0 48-21.5 48-48V80c0-26.5-21.5-48-48-48z"></path></svg></button>';
                }
                i += '</div>';
            }
            i += '</div>';

            $('#sip-logitems').append(i);


            try {
                // Start call timer on answer
                if (item.status === 'answered') {
                    var tEle = document.getElementById(item.id);
                    ctxSip.callTimers[item.id] = new Stopwatch(tEle);
                    ctxSip.callTimers[item.id].start();
                }
    
                if (callActive && item.status !== 'ringing') {
                    ctxSip.callTimers[item.id].start({startTime : item.start});
                }
            } catch {}

            // $('#sip-logitems').scrollTop(0);
        },

        /**
         * generate the call log ui
         */
        logGen : async function() {
            ctxSip.logClear();
            let history = connectSOS("GetHistoryCalls;0");
            let calls = connectSOS("GetAllActiveCalls");
            let equips = await connectSOS("GetAllEquipments");
            let ringtone = false;
            let gen = {}
            
            for (const call of (await history).concat(await calls)) {
                const equip = equips.find(e => e.EquipmentID == call.EquipmentID);
                let id = `${ call.SessionID }id${ call.equip ? call.equip.EquipmentID : equip.EquipmentID }`;
                let uri = `${ call.Sip ? call.Sip : equip.Sip }@${ call.equip ? call.equip.EquipmentIP : equip.EquipmentIP }`;
                let status;

                call.displayName    = call.equip ? call.equip.EquipmentName : equip.EquipmentName
                call.direction      = 'incoming'
                call.service        = true
                call.ctxid          = id
                call.remoteIdentity = {
                    uri         : uri,
                    displayName : call.equip ? call.equip.EquipmentName : equip.EquipmentName
                }

                ctxSip.Sessions[id] = call;

                switch (call.CallStateID) {
                    case 1:
                        status = "answered";
                        break

                    case 2:
                        status = "holding";
                        break

                    case 3:
                        status = "ended";
                        break
                        
                    case 4:
                        status = "ringing";
                        ringtone = true;
                        break

                    default:
                        status = "missed";
                        break
                }

                gen[id] = {
                    id      : id,
                    clid    : call.equip ? call.equip.EquipmentName : equip.EquipmentName,
                    uri     : uri,
                    start   : call.StartDate,
                    stop    : call.EndDate,
                    flow    : "incoming",
                    bdid    : call.equip ? call.equip.EquipmentID : equip.EquipmentID,
                    status  : status
                }
            }

            if (ringtone) {
                ctxSip.startRingTone();
            }
            
            let json = JSON.stringify(gen);
            localStorage.setItem("sipCalls", json);
        },

        /**
         * updates the call log ui
         */
        logShow : function() {

            var calllog = JSON.parse(localStorage.getItem('sipCalls')),
                x       = [];

            if (calllog !== null) {

                $('#sip-splash').addClass('hide'); // ! Remover essa linha futuramente
                $('#sip-log').removeClass('hide'); // ! Remover essa linha futuramente

                // empty existing logs
                $('#sip-logitems').empty();

                for (const log of Object.entries(calllog))
                    if (log[1].owner && (log[1].status !== 'ended' && log[1].status !== 'missed') && log[1].session == "finish") {
                        ctxSip.sipHangUp(log[0], log[1].bdid)

                        return
                    }

                // JS doesn't guarantee property order so
                // create an array with the start time as
                // the key and sort by that.

                // Add start time to array
                $.each(calllog, function(k,v) {
                    x.push(v);
                });

                // sort descending
                x.sort(function(a, b) {
                    let aId = Number(a.id.split("id")[0])
                    let bId = Number(b.id.split("id")[0])
                    return bId - aId;
                });

                $.each(x, function(k, v) {
                    ctxSip.logItem(v);
                });

            } else {
                $('#sip-logitems').html('<p class="text-muted text-center mt-4">Sem chamadas recentes.</p>')

                $('#sip-splash').removeClass('hide'); // ! Remover essa linha futuramente
                $('#sip-log').addClass('hide'); // ! Remover essa linha futuramente
            }
        },

        /**
         * removes log items from localstorage and updates the UI
         */
        logClear : function() {
            if (!ctxSip.callActiveID && !ctxSip.callIncomingID) {
                localStorage.removeItem('sipCalls');
                ctxSip.logShow();
            }
        },

        sipCall : function(target) {

            try {
                var s = ctxSip.phone.invite(target, {
                    media : {
                        stream      : ctxSip.Stream,
                        constraints : { audio : true, video : false },
                        render      : {
                            remote : $('#audioRemote').get()[0]
                        },
                        RTCConstraints : { "optional": [{ 'DtlsSrtpKeyAgreement': 'true'} ]}
                    }
                });
                s.direction = 'outgoing';
                ctxSip.newSession(s);

            } catch(e) {
                throw(e);
            }
        },

        sipTransfer : function(sessionid) {

            var s      = ctxSip.Sessions[sessionid],
                target = window.prompt('Enter destination number', '');

            ctxSip.setCallSessionStatus('<i>Transfering the call...</i>');
            s.refer(target);
        },

        sipHangUp : function(sessionid, id) {

            var s = ctxSip.Sessions[sessionid];
            // s.terminate();
            if (!s && id) {
                ctxSip.logCall({ctxid: sessionid, remoteIdentity: {}}, 'ended')
                connectSOS(`TerminateCall;${s.Sip}`)

                return;
            } else if (s.service) {
                s.call.bye();
            } else if (s.startTime) {
                // s.bye();
            } else if (s.reject) {
                // s.reject();
            } else if (s.cancel) {
                // s.cancel();
            }

        },

        sipSendDTMF : function(digit) {

            try { ctxSip.dtmfTone.play(); } catch(e) { }

            var a = ctxSip.callActiveID;
            if (a) {
                var s = ctxSip.Sessions[a];
                s.dtmf(digit);
            }
        },

        phoneCallButtonPressed : function(sessionid) {

            var s      = ctxSip.Sessions[sessionid],
                logged   = localStorage.getItem("user") || "Unknown";
                // target = $("#numDisplay").val();

            if (!ctxSip.callActiveID && !ctxSip.callIncomingID)
                if (!s) {

                    // $("#numDisplay").val("");
                    // ctxSip.sipCall(target);

                } else if (s.service) {
                    ctxSip.callIncomingID = sessionid;
                    connectSOS(`AnswerCall;${user.User};${s.Sip};${logged}`).then(response => {
                        if (response.UserID != loginAccount.ID && response.UserID)
                            ctxSip.callIncomingID = null;
                        else
                            ctxSip.proccess = setTimeout(() => {
                                ctxSip.callIncomingID = null;
                            }, 1000)
                    })
                } else if (s.accept && !s.startTime) {

                    // s.accept({
                    //     media : {
                    //         stream      : ctxSip.Stream,
                    //         constraints : { audio : true, video : false },
                    //         render      : {
                    //             remote : $('#audioRemote').get()[0]
                    //         },
                    //         RTCConstraints : { "optional": [{ 'DtlsSrtpKeyAgreement': 'true'} ]}
                    //     }
                    // });
                }
        },

        phoneMuteButtonPressed : function (sessionid) {

            var s = ctxSip.Sessions[sessionid];

            if (s.service)
                if (!s.isMuted) {
                    s.call.mute();
                    s.isMuted = true
                    ctxSip.setCallSessionStatus("Muted");
                    ctxSip.logCall(s)
                } else {
                    s.call.unmute();
                    s.isMuted = false
                    ctxSip.setCallSessionStatus("Answered");
                    ctxSip.logCall(s)
                }
        },

        phoneHoldButtonPressed : function(sessionid) {

            var s = ctxSip.Sessions[sessionid];
            let paused;

            if (s.service) {
                if (s.call.isOnHold().local === true && !ctxSip.callActiveID) {
                    s.call.unhold();
                    paused = false;
                } else {
                    s.call.hold();
                    paused = true;
                }

                connectSOS(`HoldCall;${s.Sip};${paused}`)
            }
        },


        setError : function(err, title, msg, closable) { // TODO: Corrigir a modal ou talvez remover completamente a mesma

            // Show modal if err = true
            if (err === true) {
                $("#mdlError p").html(msg);
                $("#mdlError").modal('show');

                if (closable) {
                    var b = '<button type="button" class="close" data-dismiss="modal">&times;</button>';
                    $("#mdlError .modal-header").find('button').remove();
                    $("#mdlError .modal-header").prepend(b);
                    $("#mdlError .modal-title").html(title);
                    $("#mdlError").modal({ keyboard : true });
                } else {
                    $("#mdlError .modal-header").find('button').remove();
                    $("#mdlError .modal-title").html(title);
                    $("#mdlError").modal({ keyboard : false });
                }
                $('#numDisplay').prop('disabled', 'disabled');
            } else {
                $('#numDisplay').removeProp('disabled');
                $("#mdlError").modal('hide');
            }
        },

        /**
         * Tests for a capable browser, return bool, and shows an
         * error modal on fail.
         */
        hasWebRTC : function() {

            if (navigator.webkitGetUserMedia) {
                return true;
            } else if (navigator.mozGetUserMedia) {
                return true;
            } else if (navigator.getUserMedia) {
                return true;
            } else {
                // ctxSip.setError(true, 'Unsupported Browser.', 'Your browser does not support the features required for this phone.');
                window.console.error("WebRTC support not found");
                return false;
            }
        },

        ping : async (sip, secs) => {
			await sleep(secs * 1000);
			let pong = await connectSOS(`Ping;${sip}`);
			
			if (pong == "Pong")
				ctxSip.ping(sip, secs)
		},

        setVolumeFrame : vol => {
            let svg = $("#btnVolRemote").children();

            if (vol == 0)
                svg.hide().eq(3).show()
            else if (vol < 60)
                svg.hide().eq(2).show()
            else
                svg.hide().first().show().css('opacity', (vol - 80) / 20).next().show()
        },

        setMicroFrame : vol => {
            let svg = $("#btnMicRemote").children();

            if (vol == 0)
                svg.hide().eq(2).show()
            else
                svg.hide().filter(':not(:last)').show().eq(0).css('opacity', vol / 100)
        }
    };

    ctxSip.logGen().then(() => {
        ctxSip.logShow();
    });

    // Throw an error if the browser can't hack it.
    if (!ctxSip.hasWebRTC()) {
        return true;
    }

    ctxSip.phone = new SIP.UA(ctxSip.config);

    ctxSip.phone.on('connected', function(e) {
        ctxSip.setStatus("Connected");
    });

    ctxSip.phone.on('disconnected', function(e) {
        ctxSip.setStatus("Disconnected");

        // disable phone
        // ctxSip.setError(true, 'Websocket Disconnected.', 'An Error occurred connecting to the websocket.');

        // remove existing sessions
        $("#sessions > .session").each(function(i, session) {
            ctxSip.removeSession(session, 500);
        });
    });

    ctxSip.phone.on('registered', function(e) {

        // var closeEditorWarning = function() {
        //     return 'If you close this window, you will not be able to make or receive calls from your browser.';
        // };

        var closePhone = function() {
            // stop the phone on unload
            localStorage.removeItem('ctxPhone');
            localStorage.removeItem('CallBoxStatus');
            localStorage.removeItem('CallBoxReaction');
            for ( s of Object.entries(ctxSip.Sessions) )
                if (s[1].CallStateID == 4)
                    s[1].call.bye();
            ctxSip.phone.stop();
        };

        // window.onbeforeunload = closeEditorWarning;
        window.onunload       = closePhone;

        // This key is set to prevent multiple windows.
        localStorage.setItem('ctxPhone', 'true');

        $("#mldError").modal('hide');
        ctxSip.setStatus("Ready");

        ctxSip.hasSecondaryAudio = localStorage.getItem("deviceOutput");

        // Get the userMedia and cache the stream
        if (SIP.WebRTC.isSupported()) {
            SIP.WebRTC.getUserMedia({ audio : true, video : false }, ctxSip.getUserMediaSuccess, ctxSip.getUserMediaFailure);
        }
    });

    ctxSip.phone.on('registrationFailed', function(e) {
        // ctxSip.setError(true, 'Registration Error.', 'An Error occurred registering your phone. Check your settings.');
        ctxSip.setStatus("Error: Registration Failed");
    });

    ctxSip.phone.on('unregistered', function(e) {
        localStorage.removeItem('CallBoxStatus');
    });

    ctxSip.phone.on('invite', function (incomingSession) {
        if (ctxSip.callIncomingID) {
            ctxSip.callActiveID = ctxSip.callIncomingID;
            ctxSip.callIncomingID = null;
            clearTimeout(ctxSip.proccess);
        } else return;

        let s   = incomingSession,
            r   = $('#rmtVol') 

        let closeEditorWarning = function() {
            // for ( s of Object.entries(ctxSip.Sessions) )
            //     if (s[1].CallStateID == 4)
            //         s[1].call.bye();

            return 'If you close this window, you will not be able to make or receive calls from your browser.';
        };

        // s.direction = 'incoming';
        // ctxSip.newSession(s);

        let session = ctxSip.Sessions[ctxSip.callActiveID]
        session.owner = true;

        r.showVol = async () => {
            let mic = await connectSOS(`GetMicroVolume;${session.EquipmentID}`),
                vol = await connectSOS(`GetSpeakerVolume;${session.EquipmentID}`),
                svr = $('#sldVolumeRemote'),
                smr = $('#sldMicrophoneRemote'),
                v1  = Number(vol.split(';')[1]),
                v2  = Number(mic.split(';')[1])

            svr.val(v1)
            smr.val(v2)
            ctxSip.setVolumeFrame(v1)
            ctxSip.setMicroFrame(v2)

            if (ctxSip.callActiveID)
                r.show();
        }

        // r.showVol();

        window.onbeforeunload = closeEditorWarning;

        s.accept({
            media : {
                stream      : ctxSip.Stream,
                constraints : { audio : true, video : false },
                render      : {
                    remote : $('#audioRemote').get()[0]
                },
                RTCConstraints : { "optional": [{ 'DtlsSrtpKeyAgreement': 'true'} ]}
            }
        });

        s.on('bye', function() {
            ctxSip.logCall(session, 'ended')
            connectSOS(`TerminateCall;${session.Sip}`)
            
            if (session.CallStateID != 2) {
                ctxSip.callActiveID = null;
            }
            
            r.hide();
            window.onbeforeunload = null;
            return;
        });

        s.on('hold', function(e) {
            ctxSip.callActiveID = null;
            ctxSip.logCall(session, 'holding');
            // r.hide();
        });

        s.on('unhold', function(e) {
            ctxSip.logCall(session, 'resumed');
            ctxSip.callActiveID = session.ctxid;
            // r.showVol();
        });

        ctxSip.ping(session.Sip, 3);
        ctxSip.logCall(session, "answered")
        ctxSip.Sessions[ctxSip.callActiveID].call = s
    });

    // // Auto-focus number input on backspace.
    // $('#sipClient').keydown(function(event) {
    //     if (event.which === 8) {
    //         $('#numDisplay').focus();
    //     }
    // });

    // $('#numDisplay').keypress(function(e) {
    //     // Enter pressed? so Dial.
    //     if (e.which === 13) {
    //         ctxSip.phoneCallButtonPressed();
    //     }
    // });

    // $('.digit').click(function(event) {
    //     event.preventDefault();
    //     var num = $('#numDisplay').val(),
    //         dig = $(this).data('digit');

    //     $('#numDisplay').val(num+dig);

    //     ctxSip.sipSendDTMF(dig);
    //     return false;
    // });

    $('#phoneUI').delegate('.btnCall', 'click', function(event) {
        // ctxSip.phoneCallButtonPressed();
        // // to close the dropdown
        // return true;
    });

    $('.sipLogClear').click(function(event) {
        event.preventDefault();
        ctxSip.logClear();
    });

    

    $('#sip-logitems').delegate('.sip-logitem .btnCall', 'click', function(event) {
        var sessionid = $(this).closest('.sip-logitem').data('sessionid');
        ctxSip.phoneCallButtonPressed(sessionid);
        return false;
    });

    $('#sip-logitems').delegate('.sip-logitem .btnHoldResume', 'click', function(event) {
        var sessionid = $(this).closest('.sip-logitem').data('sessionid');
        ctxSip.phoneHoldButtonPressed(sessionid);
        return false;
    });

    $('#sip-logitems').delegate('.sip-logitem .btnHangUp', 'click', function(event) {
        var sessionid = $(this).closest('.sip-logitem').data('sessionid');
        ctxSip.sipHangUp(sessionid);
        return false;
    });

    // $('#sip-logitems').delegate('.sip-logitem .btnTransfer', 'click', function(event) {
    //     var sessionid = $(this).closest('.sip-logitem').data('sessionid');
    //     ctxSip.sipTransfer(sessionid);
    //     return false;
    // });

    $('#sip-logitems').delegate('.sip-logitem .btnMute', 'click', function(event) {
        var sessionid = $(this).closest('.sip-logitem').data('sessionid');
        ctxSip.phoneMuteButtonPressed(sessionid);
        return false;
    });

    // $('#sip-logitems').delegate('.sip-logitem', 'dblclick', function(event) {
    //     event.preventDefault();

    //     var uri = $(this).data('uri');
    //     $('#numDisplay').val(uri);
    //     ctxSip.phoneCallButtonPressed();
    // });
    
    $('#sldVolume').on('input', function() {

        var v      = $(this).val() / 100,
            // player = $('audio').get()[0],
            btn    = $('#btnVol'),
            icon   = btn.find('svg'),
            active = ctxSip.callActiveID;

        // Set the object and media stream volumes
        if (ctxSip.Sessions[active]) {
            // ctxSip.Sessions[active].call.player.volume = v;
            ctxSip.callVolume                     = v;
        }

        // Set the others
        $('audio').each(function() {
            $(this).get()[0].volume = v;
        });

        if (v < 0.1) {
            btn.removeClass(function (index, css) {
                   return (css.match(/(^|\s)btn\S+/g) || []).join(' ');
                })
                .addClass('btn btn-sm btn-danger');
            icon.hide().eq(2).show()
        } else if (v < 0.8) {
            btn.removeClass(function (index, css) {
                   return (css.match(/(^|\s)btn\S+/g) || []).join(' ');
               }).addClass('btn btn-sm btn-info');
            icon.hide().eq(1).show()
        } else {
            btn.removeClass(function (index, css) {
                   return (css.match(/(^|\s)btn\S+/g) || []).join(' ');
               }).addClass('btn btn-sm btn-primary');
            icon.hide().eq(0).show()
        }
        return false;
    });
    
    $('#sldVolumeRemote').change(function () {
        let vol = $(this).val();
        
        connectSOS(`SetSpeakerVolume;${ctxSip.Sessions[ctxSip.callActiveID].EquipmentID};${vol}`)
    }).on('input', function() {
        let vol = $(this).val();

        ctxSip.setVolumeFrame(vol)
    })

    $('#sldMicrophoneRemote').change(function () {
        let vol = $(this).val();
        
        connectSOS(`SetMicroVolume;${ctxSip.Sessions[ctxSip.callActiveID].EquipmentID};${vol}`)
    }).on('input', function() {
        let vol = $(this).val();

        ctxSip.setMicroFrame(vol)
    })

    // receiver rabbitmq
    consumeSOS({
        callback_states : null,
        callback_alarms : null,
        callback_calls  : message => {
            let response = JSON.parse(message.body);

            getEquipFromID(response.EquipmentID).then(equip => {
                let direction = 'incoming';
                let status;

                switch(response.CallStateID) {
                    case 1:
                        status = "answered";
                        ctxSip.stopRingbackTone();
                        ctxSip.stopRingTone();
                        ctxSip.setCallSessionStatus('Answered');
                        break

                    case 2:
                        status = "holding";
                        break

                    case 3:
                        status = "ended";
                        ctxSip.stopRingTone();
                        ctxSip.stopRingbackTone();
                        ctxSip.setCallSessionStatus('');
                        break

                    case 4:
                        status = "ringing";
                        break

                    case 5:
                        status = "ended";
                        ctxSip.stopRingTone();
                        ctxSip.stopRingbackTone();
                        ctxSip.setCallSessionStatus('Rejected');
                        break
                }

                response.displayName = equip.EquipmentName;
                response.direction = direction;
                response.service = true;
                response.ctxid = `${response.SessionID}id${equip.EquipmentID}`;
                response.remoteIdentity = {
                    uri: `${equip.MasterSip}@${equip.EquipmentIP}`,
                    displayName: equip.EquipmentName
                }

                response.on = () => {}
    
                if (status == "ringing") {
                    ctxSip.newSession(response)
                } else {
                    let sess = ctxSip.Sessions[response.ctxid]
                    if (sess) {
                        sess.AnsweredBY = response.AnsweredBY
                        sess.CallState = response.CallState
                        sess.CallStateID = response.CallStateID
                    }
                    ctxSip.logCall(response, status)
                }
            });
        }
    })

    /**
     * Stopwatch object used for call timers
     *
     * @param {dom element} elem
     * @param {[object]} options
     */
    var Stopwatch = function(elem, options) {

        // private functions
        function createTimer() {
            return document.createElement("span");
        }

        var timer = createTimer(),
            offset,
            clock,
            interval;

        // default options
        options           = options || {};
        options.delay     = options.delay || 1000;
        options.startTime = options.startTime || Date.now();

        // append elements
        elem.appendChild(timer);

        function start() {
            if (!interval) {
                offset   = options.startTime;
                interval = setInterval(update, options.delay);
            }
        }

        function stop() {
            if (interval) {
                clearInterval(interval);
                interval = null;
            }
        }

        function reset() {
            clock = 0;
            render();
        }

        function update() {
            clock += delta();
            render();
        }

        function render() {
            timer.innerHTML = moment(clock).format('mm:ss');
        }

        function delta() {
            var now = Date.now(),
                d   = now - offset;

            offset = now;
            return d;
        }

        // initialize
        reset();

        // public API
        this.start = start; //function() { start; }
        this.stop  = stop; //function() { stop; }
    };

};

initPhone()
// initSOS()