/**
 * AudioControl - EQ, Surround, Bass, Loudness controls
 * Dependencies: SpeakerConnection, Logger, Utils
 */

class AudioControl {
    constructor(conn) {
        this.conn = conn;
    }
    
    toggleBass(en) {
        this.conn.send({type: 'set_bass_enable', enable: en});
    }
    
    toggleLoudness(en) {
        this.conn.send({type: 'set_loudness_enable', enable: en});
    }
    
    setEqBand(bandIdx, level) {
        this.conn.send({type: 'set_eq_bandlevel', band: parseInt(bandIdx), level: parseInt(level)});
    }
    
    applyEqPreset(name) {
        const presets = {
            flat: [0,0,0,0,0],
            bass: [1200, 700, 0, -200, -100],
            vocal: [-300, 400, 900, 500, 200],
            rock: [500, 300, 100, 400, 600],
            jazz: [200, 200, 200, 400, 600]
        };
        const levels = presets[name];
        if(levels) {
            this.conn.send({type: 'set_eq_enable', enable: true});
            levels.forEach((lvl, i) => {
                this.setEqBand(i, lvl);
                const slider = document.querySelector(`input[data-band="${i}"]`);
                if(slider) slider.value = lvl;
            });
            Logger.log(`Applied EQ: ${name}`);
        }
    }

    // Virtual Surround
    toggleSurround(en) {
        if(!en) {
            this.conn.send({type: 'set_loudness_enable', enable: false});
            this.applyEqPreset('flat');
            return;
        }
        this.updateSurround();
    }

    updateSurround() {
        const w = parseInt(document.getElementById('sur-w').value);
        const p = parseInt(document.getElementById('sur-p').value);
        const s = parseInt(document.getElementById('sur-s').value);
        
        document.getElementById('val-width').innerText = w;
        document.getElementById('val-presence').innerText = p;
        document.getElementById('val-space').innerText = s;

        const bands = [
            -Math.round(s*8),       // 60Hz
            -Math.round(w*7),       // 230Hz
            -Math.round(w*9),       // 910Hz
            Math.round(w*5 + p*6),  // 3.6k
            Math.round(w*6 + p*8)   // 14k
        ];
        
        bands.forEach((lvl, i) => {
            const safeLvl = Utils.clamp(lvl, -1500, 1500);
            this.setEqBand(i, safeLvl);
        });

        const gain = Utils.clamp(Math.round(s*6 + w*2), 0, 3000);
        this.conn.send({type: 'set_loudness_enable', enable: true});
        this.conn.send({type: 'set_loudness_gain', gain: gain});
        this.conn.send({type: 'set_eq_enable', enable: true});
    }

    applySurPreset(name) {
        const presets = {
            cinema: {w: 60, p: 40, s: 15},
            wide: {w: 80, p: 50, s: 10}
        };
        const p = presets[name];
        if(p) {
            document.getElementById('sur-w').value = p.w;
            document.getElementById('sur-p').value = p.p;
            document.getElementById('sur-s').value = p.s;
            this.updateSurround();
            Logger.log(`Applied Surround: ${name}`);
        }
    }
}

