/**
 * WifiManager - Quản lý WiFi setup và connection
 * Dependencies: AiboxPlusConnection, Logger
 */

class WifiManager {
    constructor(aiboxPlus) {
        this.aiboxPlus = aiboxPlus;
        this.scannedNetworks = [];
    }
    
    scan() {
        if(!this.aiboxPlus || !this.aiboxPlus.isConnected) {
            Logger.log('Chưa kết nối WebSocket', 'error');
            return;
        }
        Logger.log('Đang quét WiFi...');
        this.aiboxPlus.ws.send(JSON.stringify({action: 'wifi_scan'}));
    }
    
    connect() {
        const ssid = document.getElementById('wifiSsidInput').value.trim();
        if(!ssid) {
            Logger.log('Vui lòng nhập SSID', 'error');
            return;
        }
        const password = document.getElementById('wifiPasswordInput').value.trim();
        const securityType = document.getElementById('wifiSecurityType').value;
        Logger.log(`Đang kết nối WiFi: ${ssid}...`);
        this.aiboxPlus.ws.send(JSON.stringify({
            action: 'wifi_connect',
            ssid: ssid,
            password: password,
            security_type: securityType
        }));
    }
    
    startAp() {
        if(!this.aiboxPlus || !this.aiboxPlus.isConnected) {
            Logger.log('Chưa kết nối WebSocket', 'error');
            return;
        }
        Logger.log('Đang bật AP mode...');
        this.aiboxPlus.ws.send(JSON.stringify({action: 'wifi_start_ap'}));
    }
    
    stopAp() {
        if(!this.aiboxPlus || !this.aiboxPlus.isConnected) {
            Logger.log('Chưa kết nối WebSocket', 'error');
            return;
        }
        Logger.log('Đang tắt AP mode...');
        this.aiboxPlus.ws.send(JSON.stringify({action: 'wifi_stop_ap'}));
    }
    
    getStatus() {
        if(!this.aiboxPlus || !this.aiboxPlus.isConnected) {
            return;
        }
        this.aiboxPlus.ws.send(JSON.stringify({action: 'wifi_get_status'}));
    }
    
    getSaved() {
        if(!this.aiboxPlus || !this.aiboxPlus.isConnected) {
            return;
        }
        this.aiboxPlus.ws.send(JSON.stringify({action: 'wifi_get_saved'}));
    }
    
    handleScanResult(networks) {
        const listEl = document.getElementById('wifiNetworksList');
        if(!listEl) return;
        
        if(networks.length === 0) {
            listEl.innerHTML = '<div class="text-xs text-slate-500 text-center py-4">Không tìm thấy WiFi nào</div>';
            return;
        }
        
        this.scannedNetworks = networks;
        
        listEl.innerHTML = networks.map(network => {
            const secureIcon = network.is_secure ? '<i class="fa-solid fa-lock text-yellow-400"></i>' : '<i class="fa-solid fa-unlock text-green-400"></i>';
            const signalBars = Math.min(4, Math.max(1, Math.floor((network.level + 100) / 25)));
            const signalIcon = '<i class="fa-solid fa-signal text-blue-400"></i>'.repeat(signalBars);
            let securityHint = '';
            if(network.capabilities) {
                if(network.capabilities.includes('WPA2') || network.capabilities.includes('WPA')) {
                    securityHint = 'WPA';
                } else if(network.capabilities.includes('WEP')) {
                    securityHint = 'WEP';
                } else {
                    securityHint = 'Open';
                }
            }
            return `
                <div class="flex items-center justify-between bg-slate-800/50 p-2 rounded border border-slate-700/50 hover:bg-slate-800/70 cursor-pointer" onclick="app.wifi.selectNetwork('${network.ssid.replace(/'/g, "\\'")}', '${(network.capabilities || '').replace(/'/g, "\\'")}')">
                    <div class="flex items-center gap-2 flex-1">
                        ${secureIcon}
                        <div class="flex flex-col">
                            <span class="text-xs text-slate-300 font-medium">${network.ssid}</span>
                            ${securityHint ? '<span class="text-[10px] text-slate-500">' + securityHint + '</span>' : ''}
                        </div>
                    </div>
                    <div class="text-[10px] text-slate-500">${signalIcon}</div>
                </div>
            `;
        }).join('');
    }
    
    selectNetwork(ssid, capabilities) {
        document.getElementById('wifiSsidInput').value = ssid;
        if(capabilities) {
            const securitySelect = document.getElementById('wifiSecurityType');
            if(capabilities.includes('WPA2') || capabilities.includes('WPA')) {
                securitySelect.value = 'wpa';
            } else if(capabilities.includes('WEP')) {
                securitySelect.value = 'wep';
            } else {
                securitySelect.value = 'open';
            }
        }
    }
    
    handleStatus(data) {
        const statusEl = document.getElementById('wifiCurrentStatus');
        if(statusEl) {
            if(data.is_connected && data.current_ssid) {
                statusEl.innerHTML = `<span class="text-green-400">${data.current_ssid}</span>${data.ip_address ? ' <span class="text-slate-500">(' + data.ip_address + ')</span>' : ''}`;
            } else {
                statusEl.innerHTML = '<span class="text-red-400">Chưa kết nối</span>';
            }
        }
        this.handleApStatus(data);
    }
    
    handleApStatus(data) {
        const apStatusEl = document.getElementById('wifiApStatus');
        const apInfoEl = document.getElementById('wifiApInfo');
        
        if(data.ap_mode_active) {
            if(apStatusEl) apStatusEl.classList.remove('hidden');
            if(apInfoEl) {
                apInfoEl.innerHTML = `SSID: <span class="text-yellow-400">${data.ap_ssid || 'Phicomm-R1'}</span>${data.ap_ip ? ' | IP: ' + data.ap_ip : ''}`;
            }
        } else {
            if(apStatusEl) apStatusEl.classList.add('hidden');
        }
    }
    
    handleSavedNetworks(data) {
        const listEl = document.getElementById('savedWifiList');
        if(!listEl) return;
        
        if(!data.success || !data.networks || data.networks.length === 0) {
            listEl.innerHTML = '<div class="text-xs text-slate-500 text-center py-2">Chưa có WiFi nào được lưu</div>';
            return;
        }
        
        listEl.innerHTML = data.networks.map(network => {
            const statusText = network.status === 0 ? '<span class="text-green-400">Đã lưu</span>' : 
                              network.status === 1 ? '<span class="text-yellow-400">Đang kết nối</span>' : 
                              '<span class="text-slate-500">Không khả dụng</span>';
            return `
                <div class="flex items-center justify-between bg-slate-800/50 p-2 rounded border border-slate-700/50">
                    <div class="flex items-center gap-2 flex-1 min-w-0">
                        <i class="fa-solid fa-wifi text-blue-400"></i>
                        <div class="flex flex-col min-w-0 flex-1">
                            <span class="text-xs text-slate-300 font-medium truncate">${this.escapeHtml(network.ssid || '')}</span>
                            <span class="text-[10px] text-slate-500">${statusText}</span>
                        </div>
                    </div>
                    <div class="flex gap-1">
                        <button onclick="app.wifi.connectSaved('${this.escapeHtml(network.ssid || '')}', ${network.network_id || -1})" class="text-xs bg-green-600/20 hover:bg-green-600/30 text-green-400 px-2 py-1 rounded" title="Kết nối">
                            <i class="fa-solid fa-link"></i>
                        </button>
                        <button onclick="app.wifi.deleteSaved('${this.escapeHtml(network.ssid || '')}', ${network.network_id || -1})" class="text-xs bg-red-600/20 hover:bg-red-600/30 text-red-400 px-2 py-1 rounded" title="Xóa">
                            <i class="fa-solid fa-trash"></i>
                        </button>
                    </div>
                </div>
            `;
        }).join('');
    }
    
    connectSaved(ssid, networkId) {
        if(!this.aiboxPlus || !this.aiboxPlus.isConnected) {
            Logger.log('Chưa kết nối WebSocket', 'error');
            return;
        }
        Logger.log(`Đang kết nối lại WiFi: ${ssid}...`);
        this.aiboxPlus.ws.send(JSON.stringify({
            action: 'wifi_connect',
            ssid: ssid,
            network_id: networkId
        }));
    }
    
    deleteSaved(ssid, networkId) {
        if(!this.aiboxPlus || !this.aiboxPlus.isConnected) {
            Logger.log('Chưa kết nối WebSocket', 'error');
            return;
        }
        if(!confirm(`Xóa WiFi "${ssid}"?`)) {
            return;
        }
        Logger.log(`Đang xóa WiFi: ${ssid}...`);
        this.aiboxPlus.ws.send(JSON.stringify({
            action: 'wifi_delete_saved',
            ssid: ssid,
            network_id: networkId
        }));
    }
    
    escapeHtml(text) {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    }
}

