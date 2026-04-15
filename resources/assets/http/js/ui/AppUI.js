/**
 * AppUI - UI state management and tab switching
 */

class AppUI {
    constructor() {
        this.lastCpu = null;
    }

    setConnectionState(isConnected) {
        const dot = document.getElementById('connectionDot');
        if(dot) {
            if(isConnected) {
                dot.className = 'w-2.5 h-2.5 rounded-full bg-green-500 shadow-[0_0_8px_#22c55e]';
            } else {
                dot.className = 'w-2.5 h-2.5 rounded-full bg-red-500 animate-pulse';
            }
        }
    }

    updateVol(val) {
        const slider = document.getElementById('volSlider');
        const text = document.getElementById('volText');
        if(slider) slider.value = val;
        if(text) text.innerText = `Mức ${val}`;
    }

    showAudioTab(tab) {
        const isEq = tab === 'eq';
        document.getElementById('panel-eq').className = `space-y-4 ${isEq ? '' : 'hidden'}`;
        document.getElementById('panel-surround').className = `space-y-4 py-2 ${isEq ? 'hidden' : ''}`;
        const activeClass = 'tab-btn active px-3 py-1 rounded text-xs font-medium transition-all';
        const inactiveClass = 'tab-btn px-3 py-1 rounded text-xs font-medium text-slate-400 hover:text-white transition-all';
        document.getElementById('tab-eq').className = isEq ? activeClass : inactiveClass;
        document.getElementById('tab-surround').className = isEq ? inactiveClass : activeClass;
    }

    showLightTab(tab) {
        const isMain = tab === 'main';
        document.getElementById('lpanel-main').className = `space-y-4 ${isMain ? '' : 'hidden'}`;
        document.getElementById('lpanel-edge').className = `space-y-4 ${isMain ? 'hidden' : ''}`;
        const activeBtn = 'flex-1 py-1.5 rounded text-xs font-medium bg-blue-600 text-white transition-all shadow';
        const inactiveBtn = 'flex-1 py-1.5 rounded text-xs font-medium text-slate-400 hover:text-white transition-all';
        document.getElementById('ltab-main').className = isMain ? activeBtn : inactiveBtn;
        document.getElementById('ltab-edge').className = isMain ? inactiveBtn : activeBtn;
    }
}

