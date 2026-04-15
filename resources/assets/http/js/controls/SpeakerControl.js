/**
 * SpeakerControl - Media playback, volume, system controls
 * Dependencies: SpeakerConnection, Logger, Utils, CONFIG
 */

class SpeakerControl {
    constructor(conn) {
        this.conn = conn;
        this.state = {
            vol: 0,
            lightMain: true,
            lightEdge: false,
            staticColor: '#3b82f6',
            isPlaying: false
        };
        this.animTimer = null;
        this.sysInterval = null;
    }

    // Media
    playPause() {
        const actionCode = this.state.isPlaying ? 2 : 3;
        this.conn.send({type: 'send_message', what: 4, arg1: actionCode, arg2: -1});
        this.shell('input keyevent 85');
        this.state.isPlaying = !this.state.isPlaying;
        Logger.log(`Toggle Play/Pause (State: ${this.state.isPlaying ? 'Play' : 'Pause'})`);
    }
    
    pause() {
        if(window.app && window.app.aiboxPlus && window.app.aiboxPlus.isConnected) {
            window.app.aiboxPlus.pause();
        }
    }
    
    resume() {
        if(window.app && window.app.aiboxPlus && window.app.aiboxPlus.isConnected) {
            window.app.aiboxPlus.resume();
        }
    }
    
    stop() {
        if(window.app && window.app.aiboxPlus && window.app.aiboxPlus.isConnected) {
            window.app.aiboxPlus.stop();
        }
    }

    next() {
        if(window.app && window.app.aiboxPlus && window.app.aiboxPlus.isConnected) {
            window.app.aiboxPlus.next();
        } else {
            this.shell('input keyevent 87');
            this.conn.send({type: 'send_message', what: 65536, arg1: 0, arg2: 1, obj: 'web_next'});
            this.conn.send({type: 'next'});
        }
    }
    
    prev() {
        if(window.app && window.app.aiboxPlus && window.app.aiboxPlus.isConnected) {
            window.app.aiboxPlus.previous();
        } else {
            this.shell('input keyevent 88');
            this.conn.send({type: 'send_message', what: 65536, arg1: 0, arg2: 1, obj: 'web_prev'});
            this.conn.send({type: 'prev'});
        }
    }
    
    setVolume(val) {
        const v = parseInt(val);
        this.conn.send({type: 'set_vol', vol: v});
        this.conn.send({type: 'send_message', what: 4, arg1: 5, arg2: v});
        
        if(window.app && window.app.ui) {
            window.app.ui.updateVol(v);
        }
        Logger.log(`Volume set to ${v}`);
    }

    // DLNA / AirPlay Control
    toggleDLNA(enabled) {
        this.conn.send({
            type: 'Set_DLNA_Open',
            open: enabled,
            type_id: enabled ? 'Allow DLNA service to start' : 'Prohibit DLNA service from starting'
        });
        Logger.log(`DLNA ${enabled ? 'enabled' : 'disabled'}`);
    }

    toggleAirPlay(enabled) {
        this.conn.send({
            type: 'Set_AirPlay_Open',
            open: enabled,
            type_id: enabled ? 'Allow AirPlay service to start' : 'Prohibit AirPlay service from starting'
        });
        Logger.log(`AirPlay ${enabled ? 'enabled' : 'disabled'}`);
    }

    // Bluetooth Toggle
    setBluetooth(enable) {
        const arg1 = enable ? 1 : 2;
        const type_id = enable ? 'Open Bluetooth' : 'Close Bluetooth';
        this.conn.send({
            type: 'send_message', 
            what: 64, 
            arg1: arg1, 
            arg2: -1,
            type_id: type_id
        });
        Logger.log(enable ? 'Đang BẬT Bluetooth (API)...' : 'Đang TẮT Bluetooth (API)...');
    }

    // System
    startSystemMonitor() {
        if(this.sysInterval) {
            clearInterval(this.sysInterval);
            this.sysInterval = null;
        }
        const cmd = 'cat /proc/meminfo&&cat /proc/stat';
        this.sysInterval = setInterval(() => {
            if(this.conn && this.conn.isConnected) {
                this.conn.send({type: 'shell', type_id: 'query', shell: cmd});
            }
        }, 3000);
        if(this.conn && this.conn.isConnected) {
            this.conn.send({type: 'shell', type_id: 'query', shell: cmd});
        }
    }

    reboot() {
        if(confirm('Khởi động lại loa?')) {
            this.shell('stop adbd&&start adbd&&adb reboot');
            Logger.log('Đã gửi lệnh Reboot (ADB)...');
        }
    }
    
    toggleMode(mode) {
        if(mode === 'repeat') this.conn.send({type: 'set_play_mode', mode: 2});
        if(mode === 'shuffle') this.conn.send({type: 'set_play_mode', mode: 1});
    }

    // Helpers
    sendCmd(what, arg1, arg2, obj = false) {
        this.conn.send({type: 'send_message', what, arg1, arg2, obj});
    }
    
    shell(cmd) {
        this.conn.send({type: 'shell', type_id: 'myshell', shell: cmd});
    }
}

