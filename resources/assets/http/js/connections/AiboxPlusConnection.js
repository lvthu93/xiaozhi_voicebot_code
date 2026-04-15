/**
 * AiboxPlusConnection - WebSocket connection to port 8082
 * Handles media playback, alarms, WiFi, chat, etc.
 */

class AiboxPlusConnection {
    constructor() {
        this.ws = null;
        this.isConnected = false;
        this.currentIP = null;
        this.reconnectTimer = null;
        this.reconnectAttempts = 0;
        this.maxReconnectAttempts = 5;
        this.pendingAlarmAction = null;
    }

    connect(ip) {
        if(this.ws && this.ws.readyState === WebSocket.OPEN) {
            return;
        }
        
        this.currentIP = ip;
        const wsUrl = `ws://${ip}:8082`;
        Logger.log(`Connecting to AiboxPlus WebSocket: ${wsUrl}`);
        
        try {
            this.ws = new WebSocket(wsUrl);
            this.ws.onopen = () => this.onOpen();
            this.ws.onclose = () => this.onClose();
            this.ws.onerror = (e) => this.onError(e);
            this.ws.onmessage = (e) => this.onMessage(e);
        } catch(e) {
            Logger.log(`AiboxPlus WebSocket error: ${e.message}`, 'error');
        }
    }

    onOpen() {
        this.isConnected = true;
        this.reconnectAttempts = 0;
        this.updateStatus(true);
        Logger.log('AiboxPlus WebSocket connected');
        
        if(window.app && window.app.chat) {
            const btn = document.getElementById('btnWakeUp');
            if(btn) btn.disabled = false;
        }
        
        setTimeout(() => {
            this.ws.send(JSON.stringify({action: 'chat_get_state'}));
            // TikTok user được lấy từ server response khi active, không cần request riêng
        }, 500);
        
        if(window.app && window.app.wifi) {
            setTimeout(() => {
                window.app.wifi.getStatus();
                window.app.wifi.getSaved();
            }, 500);
        }
        
        if(window.app && window.app.chat) {
            window.app.chat.loadHistory();
        }
    }

    onClose() {
        this.isConnected = false;
        this.updateStatus(false);
        Logger.log('AiboxPlus WebSocket disconnected');
        
        if(this.currentIP) {
            clearTimeout(this.reconnectTimer);
            this.reconnectAttempts++;
            if(this.reconnectAttempts < this.maxReconnectAttempts) {
                const delay = 3000 * this.reconnectAttempts;
                Logger.log(`Reconnecting AiboxPlus WebSocket in ${delay/1000}s...`);
                this.reconnectTimer = setTimeout(() => {
                    this.connect(this.currentIP);
                }, delay);
            }
        }
    }

    onError(e) {
        Logger.log(`AiboxPlus WebSocket error: ${e.message || 'Unknown error'}`, 'error');
    }

    onMessage(event) {
        try {
            const data = JSON.parse(event.data);
            
            if(data.type === 'connected') {
                Logger.log('AiboxPlus WebSocket: ' + data.message);
            } else if(data.type === 'version') {
                this.handleVersion(data);
            } else if(data.type === 'search_result' || data.type === 'playlist_result' || data.type === 'zing_result') {
                this.handleSearchResult(data);
            } else if(data.type === 'play_started' || data.type === 'playback_state') {
                if(window.app) {
                    window.app.updateMediaFromYouTube(data);
                }
            } else if(data.type === 'alarm_triggered') {
                if(window.app) window.app.showAlarmBanner(data);
            } else if(data.type === 'alarm_stopped') {
                if(window.app) window.app.hideAlarmBanner();
            } else if(data.type === 'alarm_list') {
                if(data.success && data.alarms && window.app) {
                    window.app.renderAlarmList(data.alarms);
                }
            } else if(data.type === 'alarm_added' || data.type === 'alarm_edited' || data.type === 'alarm_deleted' || data.type === 'alarm_toggled') {
                if(data.success) {
                    Logger.log(data.message || 'Thao tác thành công');
                    if(window.app) window.app.loadAlarms();
                } else {
                    Logger.log(data.error || 'Thao tác thất bại', 'error');
                }
            } else if(data.type === 'alarm_sound_uploaded') {
                if(data.success && data.file_path && this.pendingAlarmAction) {
                    Logger.log('File âm thanh đã được upload: ' + data.file_path, 'info');
                    this.pendingAlarmAction.filePath = data.file_path;
                    this.pendingAlarmAction.execute();
                    this.pendingAlarmAction = null;
                }
            } else if(data.type === 'wifi_scan_result') {
                if(data.success && data.networks && window.app && window.app.wifi) {
                    window.app.wifi.handleScanResult(data.networks);
                }
            } else if(data.type === 'wifi_connect_result') {
                if(data.success) {
                    Logger.log('Đang kết nối WiFi...', 'info');
                    setTimeout(() => {
                        if(window.app && window.app.wifi) window.app.wifi.getStatus();
                    }, 2000);
                } else {
                    Logger.log('Lỗi kết nối WiFi: ' + (data.message || 'Unknown'), 'error');
                }
            } else if(data.type === 'wifi_status') {
                if(window.app && window.app.wifi) {
                    window.app.wifi.handleStatus(data);
                }
            } else if(data.type === 'wifi_ap_result') {
                if(data.success) {
                    Logger.log(data.message || 'AP mode updated', 'info');
                    if(window.app && window.app.wifi) window.app.wifi.getStatus();
                }
            } else if(data.type === 'wifi_ap_status') {
                if(window.app && window.app.wifi) {
                    window.app.wifi.handleApStatus(data);
                }
            } else if(data.type === 'wifi_saved_result') {
                if(window.app && window.app.wifi) {
                    window.app.wifi.handleSavedNetworks(data);
                }
            } else if(data.type === 'wifi_delete_result') {
                if(data.success) {
                    Logger.log('Đã xóa WiFi', 'info');
                    if(window.app && window.app.wifi) window.app.wifi.getSaved();
                }
            } else if(data.type === 'chat_message') {
                const messageType = data.message_type || 'server';
                const content = data.content || '';
                if(content && window.app && window.app.chat) {
                    window.app.chat.addMessage(content, messageType);
                }
            } else if(data.type === 'chat_state') {
                if(window.app && window.app.chat) {
                    window.app.chat.updateWakeUpButton(data.state, data.button_text, data.button_enabled);
                }
            } else if(data.type === 'test_mic_state') {
                if(window.app && window.app.chat) {
                    window.app.chat.updateTestMicButton(data.state, data.button_text);
                }
            } else if(data.type === 'tiktok_reply_state') {
                if(window.app && window.app.chat) {
                    window.app.chat.updateTiktokReplyButton(data.enabled);
                }
            } else if(data.type === 'tiktok_reply_toggle_result') {
                if(data.success && window.app && window.app.chat) {
                    window.app.chat.updateTiktokReplyButton(data.enabled);
                    Logger.log('TikTok reply ' + (data.enabled ? 'enabled' : 'disabled'), 'info');
                }
            } else if(data.type === 'wake_word_sensitivity_state') {
                if(window.app && window.app.wakeWordControl && data.sensitivity !== undefined) {
                    const slider = document.getElementById('wake-sensitivity');
                    const display = document.getElementById('val-wake-sensitivity');
                    if(slider) {
                        slider.value = data.sensitivity;
                    }
                    if(display) {
                        display.innerText = parseFloat(data.sensitivity).toFixed(2);
                    }
                }
            } else if(data.type === 'wake_word_set_sensitivity_result') {
                if(data.success) {
                    Logger.log('Wake word sensitivity set to ' + data.sensitivity, 'info');
                } else {
                    Logger.log('Failed to set wake word sensitivity', 'error');
                }
            } else if(data.type === 'wake_word_get_sensitivity_result') {
                if(data.success && data.sensitivity !== undefined && window.app && window.app.wakeWordControl) {
                    const slider = document.getElementById('wake-sensitivity');
                    const display = document.getElementById('val-wake-sensitivity');
                    if(slider) {
                        slider.value = data.sensitivity;
                    }
                    if(display) {
                        display.innerText = parseFloat(data.sensitivity).toFixed(2);
                    }
                }
            } else if(data.type === 'wake_word_enabled_state') {
                if(window.app && window.app.wakeWordControl && data.enabled !== undefined) {
                    const checkbox = document.getElementById('sw-wake-word');
                    if(checkbox) {
                        checkbox.checked = data.enabled;
                    }
                }
            } else if(data.type === 'wake_word_set_enabled_result') {
                if(data.success) {
                    Logger.log('Wake word detection ' + (data.enabled ? 'enabled' : 'disabled'), 'info');
                } else {
                    Logger.log('Failed to set wake word enabled', 'error');
                }
            } else if(data.type === 'wake_word_get_enabled_result') {
                if(data.success && data.enabled !== undefined && window.app && window.app.wakeWordControl) {
                    const checkbox = document.getElementById('sw-wake-word');
                    if(checkbox) {
                        checkbox.checked = data.enabled;
                    }
                }
            } else if(data.type === 'chat_send_result') {
                if(data.success) {
                    Logger.log('Chat message sent', 'info');
                }
            } else if(data.type === 'mac_random' || data.type === 'mac_get' || data.type === 'mac_clear') {
                if(window.app && window.app.mac) {
                    window.app.mac.handleResponse(data);
                }
            } else if(data.type === 'led_state') {
                if(window.app && window.app.led) {
                    window.app.led.handleLedState(data);
                }
            } else if(data.type === 'led_toggle_result') {
                if(window.app && window.app.led) {
                    window.app.led.handleLedToggleResult(data);
                }
            } else if(data.type === 'error') {
                Logger.log(`AiboxPlus Error: ${data.message}`, 'error');
            }
        } catch(e) {
            Logger.log(`Error parsing AiboxPlus message: ${e.message}`, 'error');
        }
    }

    handleVersion(data) {
        if(data.success && data.version) {
            const versionBadge = document.getElementById('appVersionBadge');
            if(versionBadge) {
                versionBadge.textContent = 'V' + data.version;
            }
            Logger.log('App version: ' + data.version);
            
            const newVersionBadge = document.getElementById('newVersionBadge');
            if(data.has_new_version && data.new_version) {
                if(newVersionBadge) {
                    let currentIP = window.location.hostname;
                    if(!currentIP || currentIP === 'localhost' || currentIP === '127.0.0.1') {
                        currentIP = window.app && window.app.connection ? window.app.connection.currentIP : null;
                    }
                    if(currentIP && currentIP !== 'localhost' && currentIP !== '127.0.0.1') {
                        newVersionBadge.href = `https://r1.truongblack.me/?ip=${currentIP}`;
                    } else {
                        newVersionBadge.href = 'https://r1.truongblack.me/';
                    }
                    newVersionBadge.textContent = 'Có version mới: ' + data.new_version;
                    newVersionBadge.classList.remove('hidden');
                }
                Logger.log('Có version mới: ' + data.new_version);
            } else {
                if(newVersionBadge) {
                    newVersionBadge.classList.add('hidden');
                }
            }
        }
    }

    handleSearchResult(data) {
        const resultsDiv = document.getElementById('aiboxplus-results');
        if(!resultsDiv) return;

        if(!data.success || !data.songs || data.songs.length === 0) {
            resultsDiv.innerHTML = `
                <div class="text-center text-slate-500 text-xs py-4">
                    <i class="fa-solid fa-search text-xl mb-2 block"></i>
                    Không tìm thấy kết quả
                </div>
            `;
            return;
        }

        const isZingResult = data.type === 'zing_result';
        let html = '';
        data.songs.forEach((song) => {
            const duration = this.formatDuration(song.duration_seconds);
            const songId = isZingResult ? song.song_id : song.video_id;
            const artist = isZingResult ? (song.artist || 'Unknown') : (song.channel || 'Unknown');
            const playAction = isZingResult ? 
                `app.aiboxPlus.playZingSong('${songId}')` : 
                `app.aiboxPlus.playSong('${songId}')`;
            html += `
                <div class="bg-slate-800/50 border border-slate-700/50 rounded-lg p-2 hover:bg-slate-800/70 transition-colors">
                    <div class="flex items-center gap-2">
                        <div class="flex-shrink-0 w-10 h-10 bg-slate-700 rounded flex items-center justify-center">
                            ${song.thumbnail_url ? 
                                `<img src="${song.thumbnail_url}" alt="${song.title}" class="w-full h-full object-cover rounded">` :
                                `<i class="fa-solid fa-music text-slate-400 text-xs"></i>`
                            }
                        </div>
                        <div class="flex-1 min-w-0">
                            <div class="text-xs font-bold text-white truncate">${song.title}</div>
                            <div class="text-[10px] text-slate-400 truncate">${artist}</div>
                            <div class="text-[10px] text-slate-500 mt-0.5">${duration}</div>
                        </div>
                        <button onclick="${playAction}" 
                            class="bg-red-600 hover:bg-red-500 text-white px-3 py-1.5 rounded text-[10px] font-bold transition-colors flex-shrink-0">
                            <i class="fa-solid fa-play mr-1"></i> Phát
                        </button>
                    </div>
                </div>
            `;
        });
        resultsDiv.innerHTML = html;
        Logger.log(`Hiển thị ${data.songs.length} kết quả tìm kiếm`);
    }

    formatDuration(seconds) {
        if(!seconds) return '0:00';
        const mins = Math.floor(seconds / 60);
        const secs = Math.floor(seconds % 60);
        return `${mins}:${secs.toString().padStart(2, '0')}`;
    }

    updateStatus(connected) {
        // Status dot removed - no longer needed
    }

    send(data) {
        if(this.ws && this.ws.readyState === WebSocket.OPEN) {
            this.ws.send(JSON.stringify(data));
        } else {
            Logger.log('AiboxPlus WebSocket not connected', 'error');
        }
    }

    search(query) {
        if(!query || !query.trim()) {
            Logger.log('Query không được để trống', 'error');
            return;
        }
        this.send({
            action: 'search_songs',
            query: query.trim()
        });
        Logger.log(`Tìm kiếm: ${query}`);
    }
    
    searchPlaylist(query) {
        if(!query || !query.trim()) {
            Logger.log('Query không được để trống', 'error');
            return;
        }
        this.send({
            action: 'search_playlist',
            query: query.trim()
        });
        Logger.log(`Tìm playlist: ${query}`);
    }
    
    searchZing(query) {
        if(!query || !query.trim()) {
            Logger.log('Query không được để trống', 'error');
            return;
        }
        this.send({
            action: 'search_zing',
            query: query.trim()
        });
        Logger.log(`Tìm kiếm Zing MP3: ${query}`);
    }
    
    playZingSong(songId) {
        this.send({
            action: 'play_zing',
            song_id: songId
        });
        Logger.log('Playing Zing MP3 song: ' + songId);
    }

    playSong(videoId) {
        if(!videoId) {
            Logger.log('Video ID không hợp lệ', 'error');
            return;
        }
        this.send({
            action: 'play_song',
            video_id: videoId
        });
        Logger.log(`Phát bài hát: ${videoId}`);
    }
    
    stopAlarm() {
        this.send({action: 'alarm_stop'});
        Logger.log('Dừng báo thức');
    }
    
    listAlarms() {
        this.send({action: 'alarm_list'});
        Logger.log('Lấy danh sách báo thức');
    }
    
    addAlarm(hour, minute, repeat, label, volume, customSoundPath, youtubeSongName) {
        const payload = {
            action: 'alarm_add',
            hour: hour,
            minute: minute,
            repeat: repeat || 'none',
            label: label || '',
            volume: volume !== undefined ? volume : 100
        };
        if(customSoundPath) payload.custom_sound_path = customSoundPath;
        if(youtubeSongName) payload.youtube_song_name = youtubeSongName;
        this.send(payload);
        Logger.log(`Thêm báo thức: ${hour}:${minute}, volume=${volume}, customSound=${customSoundPath || 'none'}, youtubeSong=${youtubeSongName || 'none'}`);
    }
    
    editAlarm(alarmId, hour, minute, repeat, label, volume, customSoundPath, youtubeSongName) {
        const payload = {
            action: 'alarm_edit',
            alarm_id: alarmId,
            hour: hour,
            minute: minute,
            repeat: repeat || 'none',
            label: label || '',
            volume: volume !== undefined ? volume : 100
        };
        if(customSoundPath) payload.custom_sound_path = customSoundPath;
        if(youtubeSongName) payload.youtube_song_name = youtubeSongName;
        this.send(payload);
        Logger.log(`Sửa báo thức ${alarmId}: ${hour}:${minute}, volume=${volume}, customSound=${customSoundPath || 'none'}, youtubeSong=${youtubeSongName || 'none'}`);
    }
    
    uploadAlarmSound(file, alarmId, callback) {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.onload = (e) => {
                const base64 = e.target.result.split(',')[1];
                this.send({
                    action: 'alarm_upload_sound',
                    alarm_id: alarmId || -1,
                    file_name: file.name,
                    file_data: base64
                });
                Logger.log(`Uploading alarm sound: ${file.name} (${Math.round(file.size / 1024)}KB)`);
                if(callback) callback();
                resolve();
            };
            reader.onerror = reject;
            reader.readAsDataURL(file);
        });
    }
    
    deleteAlarm(alarmId) {
        this.send({
            action: 'alarm_delete',
            alarm_id: alarmId
        });
        Logger.log(`Xóa báo thức: ${alarmId}`);
    }
    
    toggleAlarm(alarmId) {
        this.send({
            action: 'alarm_toggle',
            alarm_id: alarmId
        });
        Logger.log(`Bật/tắt báo thức: ${alarmId}`);
    }
    
    getVersion() {
        this.send({action: 'get_version'});
        Logger.log('Requesting app version');
    }
    
    pause() {
        this.send({action: 'pause'});
        Logger.log('Pause YouTube');
    }
    
    resume() {
        this.send({action: 'resume'});
        Logger.log('Resume YouTube');
    }
    
    stop() {
        this.send({action: 'stop'});
        Logger.log('Stop YouTube');
    }
    
    seek(positionSeconds) {
        this.send({action: 'seek', position: positionSeconds});
        Logger.log('Seek to: ' + positionSeconds + 's');
    }
    
    next() {
        if(!this.isConnected) {
            Logger.log('AiboxPlus WebSocket not connected', 'error');
            return;
        }
        this.send({action: 'next'});
        Logger.log('Next bài hát');
    }
    
    previous() {
        if(!this.isConnected) {
            Logger.log('AiboxPlus WebSocket not connected', 'error');
            return;
        }
        this.send({action: 'previous'});
        Logger.log('Previous bài hát');
    }
    
    toggleAutoNext() {
        if(!this.isConnected) {
            Logger.log('AiboxPlus WebSocket not connected', 'error');
            return;
        }
        this.send({action: 'toggle_auto_next'});
        Logger.log('Toggling auto-next');
    }
}

