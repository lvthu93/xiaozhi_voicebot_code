/**
 * LightControl - Main RGB, Edge lights controls
 * Dependencies: SpeakerConnection, Logger, CONFIG
 */

class LightControl {
    constructor(conn) {
        this.conn = conn;
        this.state = {
            lightMain: true,
            lightEdge: false
        };
    }

    // Lights - Main
    toggleMainLight(en) {
        this.sendCmd(4, 64, en ? 1 : 0); // 64 = Ambient Light Toggle
        if(en) this.setFwMode(3); // Default to static
    }
    
    setFwMode(modeId) {
        this.conn.send({type: 'send_message', what: 4, arg1: 68, arg2: modeId});
        const modeNames = {
            0: 'Mặc Định',
            1: 'Xoay vòng',
            2: 'Nháy 1',
            3: 'Đơn sắc',
            4: 'Nháy 2',
            7: 'Hơi thở'
        };
        Logger.log(`Chế độ ánh sáng: ${modeNames[modeId] || 'Chế độ ' + modeId}`);
    }

    setLightSpeed(speed) {
        // Slider: 1-100 (giá trị thực) -> Server: 1-100 (giá trị thực)
        // Dùng trực tiếp giá trị từ slider
        const speedVal = Math.max(1, Math.min(100, Math.round(speed)));
        this.conn.send({type: 'send_message', what: 4, arg1: 66, arg2: speedVal});
        Logger.log(`Tốc độ đèn: ${speedVal}`);
    }

    setLightBrightness(brightness) {
        // Slider: 1-200 (giá trị thực) -> Server: 1-200 (giá trị thực)
        // Dùng trực tiếp giá trị từ slider
        const brightVal = Math.max(1, Math.min(200, Math.round(brightness)));
        this.conn.send({type: 'send_message', what: 4, arg1: 65, arg2: brightVal});
        Logger.log(`Cường độ sáng: ${brightVal}`);
    }

    // Edge Light (White Only) - Theo R1_control.html dòng 1314-1317
    toggleEdgeLight(en) {
        this.state.lightEdge = en;
        // Giống hệt R1_control.html: 'lights_test set 7fffff8000 ffffff' hoặc 'lights_test set 7fffff8000 0'
        let shell = en ? 'lights_test set 7fffff8000 ffffff' : 'lights_test set 7fffff8000 0';
        // Format: { type_id: 'Turn on light', type: 'shell', shell: shell }
        this.conn.send({ type_id: 'Turn on light', type: 'shell', shell: shell });
        Logger.log(`Edge light ${en ? 'ON' : 'OFF'}`);
    }

    setEdgeLightIntensity(intensity) {
        // Tính độ sáng từ 0-100 thành hex (0-255)
        const val = Math.round((parseInt(intensity) / 100) * 255);
        const hexPair = val.toString(16).padStart(2, '0');
        const fullHex = `${hexPair}${hexPair}${hexPair}`; // R=G=B for white
        
        // Chỉ set độ sáng nếu đèn đang bật
        if(this.state.lightEdge) {
            // Giống R1_control.html: 'lights_test set 7fffff8000 <hex>'
            let shell = `lights_test set 7fffff8000 ${fullHex}`;
            this.conn.send({ type_id: 'Turn on light', type: 'shell', shell: shell });
        }
    }

    // Helpers
    sendCmd(what, arg1, arg2, obj = false) {
        this.conn.send({type: 'send_message', what, arg1, arg2, obj});
    }
    
    shell(cmd) {
        this.conn.send({type: 'shell', type_id: 'myshell', shell: cmd});
    }
}

