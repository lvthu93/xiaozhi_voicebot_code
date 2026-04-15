/**
 * ChatManager - Quản lý chat và TikTok reply
 * Dependencies: AiboxPlusConnection, Logger
 */

class ChatManager {
    constructor(aiboxPlus) {
        this.aiboxPlus = aiboxPlus;
    }
    
    wakeUp() {
        if(!this.aiboxPlus || !this.aiboxPlus.isConnected) {
            Logger.log('Chưa kết nối WebSocket', 'error');
            return;
        }
        
        // Add loading state to button
        const btn = document.getElementById('btnWakeUp');
        if(btn) {
            btn.classList.add('button-loading');
            btn.disabled = true;
            
            // Remove loading state after 2 seconds (or when response received)
            setTimeout(() => {
                btn.classList.remove('button-loading');
                btn.disabled = false;
            }, 2000);
        }
        
        this.aiboxPlus.ws.send(JSON.stringify({
            action: 'chat_wake_up'
        }));
    }
    
    testMic() {
        if(!this.aiboxPlus || !this.aiboxPlus.isConnected) {
            Logger.log('Chưa kết nối WebSocket', 'error');
            return;
        }
        this.aiboxPlus.ws.send(JSON.stringify({
            action: 'chat_test_mic'
        }));
    }
    
    updateWakeUpButton(state, buttonText, enabled) {
        const btn = document.getElementById('btnWakeUp');
        if(btn) {
            // Remove loading state when button is updated
            btn.classList.remove('button-loading');
            
            const icon = btn.querySelector('i');
            if(icon) {
                btn.innerHTML = icon.outerHTML + ' ' + buttonText;
            } else {
                btn.textContent = buttonText;
            }
            btn.disabled = !enabled;
            Logger.log('Wake Up button updated: state=' + state + ', enabled=' + enabled);
        }
    }
    
    updateTestMicButton(state, buttonText) {
        const btn = document.querySelector('button[onclick="app.chat.testMic()"]');
        if(!btn) return;
        
        let icon = 'fa-microphone-lines';
        let bgColor = 'bg-purple-600';
        let hoverColor = 'hover:bg-purple-500';
        
        if(state === 'RECORDING' || state === 'PLAYING') {
            icon = 'fa-stop';
            bgColor = 'bg-red-600';
            hoverColor = 'hover:bg-red-500';
        }
        
        btn.className = `${bgColor} ${hoverColor} text-white px-3 py-2 rounded text-xs font-bold transition-colors`;
        btn.innerHTML = `<i class="fa-solid ${icon} mr-1"></i>${buttonText}`;
    }
    
    toggleTiktokReply() {
        if(!this.aiboxPlus || !this.aiboxPlus.isConnected) {
            Logger.log('Chưa kết nối WebSocket', 'error');
            return;
        }
        
        const btn = document.getElementById('btnTiktokReply');
        const textSpan = document.getElementById('tiktokReplyText');
        if(!btn || !textSpan) return;
        
        const isEnabled = btn.classList.contains('bg-green-600');
        const newEnabled = !isEnabled;
        
        this.aiboxPlus.ws.send(JSON.stringify({
            action: 'tiktok_reply_toggle',
            enabled: newEnabled
        }));
        
        Logger.log(`TikTok reply ${newEnabled ? 'enabled' : 'disabled'}...`);
    }
    
    updateTiktokReplyButton(enabled) {
        const btn = document.getElementById('btnTiktokReply');
        const textSpan = document.getElementById('tiktokReplyText');
        if(!btn || !textSpan) return;
        
        if(enabled) {
            btn.className = 'flex-1 bg-green-600 hover:bg-green-500 text-white px-3 py-2 rounded text-xs font-bold transition-colors';
            textSpan.textContent = 'TikTok Reply: ON';
        } else {
            btn.className = 'flex-1 bg-slate-700 hover:bg-slate-600 text-white px-3 py-2 rounded text-xs font-bold transition-colors';
            textSpan.textContent = 'TikTok Reply: OFF';
        }
    }
    
    
    addMessage(content, type = 'user') {
        const messagesEl = document.getElementById('chatMessages');
        if(!messagesEl) return;
        
        if(messagesEl.querySelector('.text-slate-500')) {
            messagesEl.innerHTML = '';
        }
        
        const messageDiv = document.createElement('div');
        messageDiv.className = 'flex ' + (type === 'user' ? 'justify-end' : type === 'server' ? 'justify-start' : 'justify-center');
        
        const bubble = document.createElement('div');
        bubble.className = 'px-3 py-2 rounded-lg text-xs max-w-[80%]';
        
        if(type === 'user') {
            bubble.className += ' bg-blue-600 text-white';
        } else if(type === 'server') {
            bubble.className += ' bg-slate-800 text-slate-200';
        } else {
            bubble.className += ' bg-yellow-900/50 text-yellow-200 italic';
        }
        
        bubble.textContent = content;
        messageDiv.appendChild(bubble);
        messagesEl.appendChild(messageDiv);
        
        messagesEl.scrollTop = messagesEl.scrollHeight;
    }
    
    loadHistory() {
        if(!this.aiboxPlus || !this.aiboxPlus.isConnected) {
            Logger.log('Chưa kết nối WebSocket', 'error');
            return;
        }
        this.aiboxPlus.ws.send(JSON.stringify({action: 'chat_get_history'}));
    }
    
    clear() {
        const messagesEl = document.getElementById('chatMessages');
        if(!messagesEl) return;
        messagesEl.innerHTML = '<div class="text-xs text-slate-500 italic text-center py-4">Chưa có tin nhắn nào</div>';
    }
    
    sendText() {
        const inputEl = document.getElementById('chatInput');
        if(!inputEl) return;
        
        const text = inputEl.value.trim();
        if(!text) return;
        
        if(!this.aiboxPlus || !this.aiboxPlus.isConnected) {
            Logger.log('Chưa kết nối WebSocket', 'error');
            return;
        }
        
        this.aiboxPlus.ws.send(JSON.stringify({
            action: 'chat_send_text',
            text: text
        }));
        
        inputEl.value = '';
    }
}

