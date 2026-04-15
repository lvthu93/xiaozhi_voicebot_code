/**
 * SpeakerConnection - WebSocket connection to port 8080
 * Handles speaker control communication
 */

class SpeakerConnection {
    constructor() {
        this.ws = null;
        this.isConnected = false;
        this.callbacks = {};
        this.pingTimer = null;
        this.reconnectTimer = null;
        this.reconnectAttempts = 0;
        this.maxReconnectAttempts = 10;
        this.reconnectDelay = 3000;
        this.autoConnectEnabled = false;
        this.currentIP = null;
    }

    connect(ip) {
        if(this.ws) this.ws.close();
        this.currentIP = ip;
        const url = `ws://${ip}:${CONFIG.WS_PORT}`;
        Logger.log(`Connecting WebSocket to ${url}...`);

        try {
            this.ws = new WebSocket(url);
            this.ws.onopen = () => this.onOpen();
            this.ws.onmessage = (e) => this.onMessage(e);
            this.ws.onclose = () => this.onClose();
            this.ws.onerror = (e) => Logger.log('WS Error', 'error');
        } catch(e) {
            Logger.log(`Connection failed: ${e.message}`, 'error');
            this.scheduleReconnect();
        }
    }

    onOpen() {
        this.isConnected = true;
        this.reconnectAttempts = 0;
        localStorage.setItem('r1_ip', this.currentIP);
        if(window.app && window.app.ui) {
            window.app.ui.setConnectionState(true);
        }
        Logger.log('Connected!', 'info');
        
        // Init sequences - send immediately
        this.send({type: 'get_info'});
        this.send({type: 'get_eq_config'});
        
        // Start Heartbeat (Keep-alive) - send get_info every 950ms
        clearInterval(this.pingTimer);
        const heartbeatData = JSON.stringify({type: 'get_info'});
        this.pingTimer = setInterval(() => {
            if(this.ws && this.ws.readyState === 1) {
                this.ws.send(heartbeatData);
            }
        }, 950);

        // Start System Monitor
        if(window.app && window.app.control) {
            window.app.control.startSystemMonitor();
        }
    }

    onClose() {
        this.isConnected = false;
        if(window.app && window.app.ui) {
            window.app.ui.setConnectionState(false);
        }
        if(window.app && window.app.control) {
            window.app.control.state.isPlaying = false;
        }
        clearInterval(this.pingTimer);
        Logger.log('Disconnected.', 'error');
        
        if(this.autoConnectEnabled) {
            this.scheduleReconnect();
        }
    }

    scheduleReconnect() {
        if(this.reconnectAttempts >= this.maxReconnectAttempts) {
            Logger.log('Max reconnect attempts reached', 'error');
            return;
        }

        clearTimeout(this.reconnectTimer);
        this.reconnectAttempts++;
        const delay = this.reconnectDelay * this.reconnectAttempts;
        Logger.log(`Reconnecting in ${Math.round(delay/1000)}s (attempt ${this.reconnectAttempts}/${this.maxReconnectAttempts})...`);
        
        this.reconnectTimer = setTimeout(() => {
            if(this.currentIP && this.autoConnectEnabled) {
                this.connect(this.currentIP);
            }
        }, delay);
    }

    onMessage(event) {
        try {
            const data = event.data;
            
            if (typeof data === 'string') {
                const json = JSON.parse(data);
                
                if (json.code != undefined && json.code != 200) {
                    Logger.log(`Response error: code ${json.code}`, 'error');
                    return;
                }
                
                if (json.type === 'get_info' && json.data) {
                    try {
                        const deviceData = JSON.parse(json.data);
                        console.log('[WebSocket] Received get_info, updating state...', deviceData);
                        if(window.app) {
                            window.app.updateState(deviceData);
                        }
                    } catch(e) {
                        Logger.log(`Error parsing get_info data: ${e.message}`, 'error');
                    }
                }
                else if (json.type === 'get_eq_config' && json.data) {
                    const eqData = JSON.parse(json.data);
                    if(window.app) {
                        window.app.updateEqState(eqData);
                    }
                }
                else if (json.type === 'shell') {
                    if (json.type_id === 'query') {
                        if(window.app) {
                            window.app.updateSystemStats(json.data);
                        }
                    }
                    else if (json.type_id === 'myshell') {
                        const time = new Date().toLocaleTimeString('vi-VN', { hour12: false, minute:'2-digit', second:'2-digit' });
                        Logger.log(`[${time}] Shell: ${json.data || 'No output'}`);
                    }
                }
                else if (json.type === 'playback_update' && json.data) {
                    try {
                        const playbackData = JSON.parse(json.data);
                        if(window.app) {
                            window.app.updateState(playbackData);
                        }
                    } catch(e) { /* ignore partial update */ }
                }
            }
            else if (data instanceof Blob) {
                // Handle binary data if needed
            }
        } catch(e) {
            if (e instanceof SyntaxError) {
                Logger.log(`Raw: ${event.data.substring(0, 100)}`);
            }
        }
    }

    send(payload) {
        if(this.ws && this.ws.readyState === 1) {
            this.ws.send(JSON.stringify(payload));
            if(payload.type !== 'ping') Logger.log(`TX: ${payload.type}`, 'sent');
        }
    }
}

