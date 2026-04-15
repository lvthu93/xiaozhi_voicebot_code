/**
 * Logger utility - Console logging with UI display
 */

let logConsoleEl = null;

const Logger = {
    init: () => {
        logConsoleEl = document.getElementById('logConsole');
    },
    
    log: (msg, type = 'info') => {
        if(!logConsoleEl) Logger.init();
        // Nếu không có logConsole element, chỉ log ra console
        if(!logConsoleEl) {
            console.log(`[${type.toUpperCase()}] ${msg}`);
            return;
        }
        const time = new Date().toLocaleTimeString('vi-VN', { 
            hour12: false, 
            minute: '2-digit', 
            second: '2-digit' 
        });
        const div = document.createElement('div');
        div.className = type === 'error' ? 'text-red-400' : (type === 'sent' ? 'text-blue-400' : 'text-slate-300');
        div.innerHTML = `<span class="text-slate-600">[${time}]</span> `;
        const msgSpan = document.createElement('span');
        msgSpan.textContent = msg;
        div.appendChild(msgSpan);
        
        // RESOURCE OPTIMIZATION: Limit log entries
        logConsoleEl.appendChild(div);
        if (logConsoleEl.childElementCount > 50) {
            logConsoleEl.removeChild(logConsoleEl.firstChild);
        }
        
        logConsoleEl.scrollTop = logConsoleEl.scrollHeight;
    }
};

