/**
 * MacManager - Quản lý MAC address (random, get, clear)
 * Dependencies: AiboxPlusConnection, Logger
 */

class MacManager {
    constructor(aiboxPlus) {
        this.aiboxPlus = aiboxPlus;
    }
    
    /**
     * Random và lưu MAC mới
     */
    random() {
        // Hiển thị popup xác nhận với cảnh báo
        const confirmed = confirm(
            '⚠️ CẢNH BÁO QUAN TRỌNG ⚠️\n\n' +
            'Chỉ dùng khi bạn KHÔNG CÓ QUYỀN TRUY CẬP thiết bị trên xiaozhi.me\n\n' +
            '❌ Nếu bạn tạo MAC mới, bạn sẽ MẤT QUYỀN TRUY CẬP các tính năng:\n' +
            '   • Zalo\n' +
            '   • TikTok\n' +
            '   • Bank Notification\n' +
            '   ...mà bạn đã mua\n\n' +
            '🔄 Giải pháp:\n' +
            '   • Liên hệ ai-box-plus.com để kích hoạt lại\n' +
            '   • Hoặc nếu chưa mua thì mua mới\n\n' +
            '⚠️ Chú ý: Chỉ hỗ trợ đổi MAC tối đa 3 lần\n\n' +
            'Bạn có chắc chắn muốn tiếp tục?'
        );
        
        if (!confirmed) {
            Logger.log('Đã hủy random MAC', 'info');
            return;
        }
        
        if(!this.aiboxPlus || !this.aiboxPlus.isConnected) {
            Logger.log('Chưa kết nối WebSocket', 'error');
            // Fallback: dùng HTTP API
            this.randomViaHttp();
            return;
        }
        Logger.log('Đang random MAC address...');
        this.aiboxPlus.ws.send(JSON.stringify({action: 'mac_random'}));
    }
    
    /**
     * Lấy MAC hiện tại
     */
    get() {
        if(!this.aiboxPlus || !this.aiboxPlus.isConnected) {
            Logger.log('Chưa kết nối WebSocket', 'error');
            // Fallback: dùng HTTP API
            this.getViaHttp();
            return;
        }
        Logger.log('Đang lấy MAC address...');
        this.aiboxPlus.ws.send(JSON.stringify({action: 'mac_get'}));
    }
    
    /**
     * Xóa MAC đã lưu (dùng MAC thực)
     */
    clear() {
        if(!this.aiboxPlus || !this.aiboxPlus.isConnected) {
            Logger.log('Chưa kết nối WebSocket', 'error');
            // Fallback: dùng HTTP API
            this.clearViaHttp();
            return;
        }
        Logger.log('Đang xóa MAC đã lưu...');
        this.aiboxPlus.ws.send(JSON.stringify({action: 'mac_clear'}));
    }
    
    /**
     * Random MAC via HTTP API (fallback)
     */
    async randomViaHttp() {
        try {
            const currentIP = this.aiboxPlus && this.aiboxPlus.currentIP ? this.aiboxPlus.currentIP : window.location.hostname;
            const response = await fetch(`http://${currentIP}:8081/api/mac/random`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            const data = await response.json();
            if(data.success) {
                Logger.log(`MAC mới: ${data.mac_address}`, 'success');
                this.updateDisplay(data.mac_address, true);
            } else {
                Logger.log('Lỗi random MAC: ' + (data.error || 'Unknown error'), 'error');
            }
        } catch(error) {
            Logger.log('Lỗi kết nối HTTP: ' + error.message, 'error');
        }
    }
    
    /**
     * Get MAC via HTTP API (fallback)
     */
    async getViaHttp() {
        try {
            const currentIP = this.aiboxPlus && this.aiboxPlus.currentIP ? this.aiboxPlus.currentIP : window.location.hostname;
            const response = await fetch(`http://${currentIP}:8081/api/mac/get`);
            const data = await response.json();
            if(data.success) {
                this.updateDisplay(data.mac_address, data.is_custom);
            } else {
                Logger.log('Lỗi lấy MAC: ' + (data.error || 'Unknown error'), 'error');
            }
        } catch(error) {
            Logger.log('Lỗi kết nối HTTP: ' + error.message, 'error');
        }
    }
    
    /**
     * Clear MAC via HTTP API (fallback)
     */
    async clearViaHttp() {
        try {
            const currentIP = this.aiboxPlus && this.aiboxPlus.currentIP ? this.aiboxPlus.currentIP : window.location.hostname;
            const response = await fetch(`http://${currentIP}:8081/api/mac/clear`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            const data = await response.json();
            if(data.success) {
                Logger.log('Đã xóa MAC đã lưu, đang dùng MAC thực', 'success');
                // Refresh display
                this.get();
            } else {
                Logger.log('Lỗi xóa MAC: ' + (data.error || 'Unknown error'), 'error');
            }
        } catch(error) {
            Logger.log('Lỗi kết nối HTTP: ' + error.message, 'error');
        }
    }
    
    /**
     * Xử lý response từ WebSocket
     */
    handleResponse(data) {
        if(data.type === 'mac_random') {
            if(data.success) {
                Logger.log(`MAC mới: ${data.mac_address}`, 'success');
                this.updateDisplay(data.mac_address, true);
            } else {
                Logger.log('Lỗi random MAC: ' + (data.error || 'Unknown error'), 'error');
            }
        } else if(data.type === 'mac_get') {
            if(data.success) {
                this.updateDisplay(data.mac_address, data.is_custom);
            } else {
                Logger.log('Lỗi lấy MAC: ' + (data.error || 'Unknown error'), 'error');
            }
        } else if(data.type === 'mac_clear') {
            if(data.success) {
                Logger.log('Đã xóa MAC đã lưu, đang dùng MAC thực', 'success');
                // Refresh display
                this.get();
            } else {
                Logger.log('Lỗi xóa MAC: ' + (data.error || 'Unknown error'), 'error');
            }
        }
    }
    
    /**
     * Cập nhật hiển thị MAC trên UI
     */
    updateDisplay(macAddress, isCustom) {
        const macDisplay = document.getElementById('macCurrentDisplay');
        const macTypeDisplay = document.getElementById('macTypeDisplay');
        
        if(macDisplay) {
            macDisplay.textContent = macAddress || 'Unknown';
        }
        
        if(macTypeDisplay) {
            if(isCustom) {
                macTypeDisplay.textContent = '🔀 Custom MAC (Random)';
                macTypeDisplay.className = 'text-[9px] text-yellow-400 mt-1';
            } else {
                macTypeDisplay.textContent = '📡 Real MAC (Hardware)';
                macTypeDisplay.className = 'text-[9px] text-green-400 mt-1';
            }
        }
    }
    
    /**
     * Load MAC khi trang load
     */
    load() {
        this.get();
    }
}

