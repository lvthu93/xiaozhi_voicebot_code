/**
 * LedManager - Quản lý đèn LED (bật/tắt, lấy trạng thái)
 * Dependencies: AiboxPlusConnection, Logger
 */

class LedManager {
    constructor(aiboxPlus) {
        this.aiboxPlus = aiboxPlus;
    }

    toggleLed() {
        if (!this.aiboxPlus || !this.aiboxPlus.isConnected) {
            Logger.log('Chưa kết nối WebSocket', 'error');
            return;
        }
        Logger.log('Đang bật/tắt LED...');
        this.aiboxPlus.send({ action: 'led_toggle' });
    }

    getLedState() {
        if (!this.aiboxPlus || !this.aiboxPlus.isConnected) {
            Logger.log('Chưa kết nối WebSocket', 'error');
            return;
        }
        Logger.log('Đang lấy trạng thái LED...');
        this.aiboxPlus.send({ action: 'led_get_state' });
    }

    handleLedState(data) {
        if (data.enabled !== undefined) {
            // Update switch state
            const ledCheckbox = document.getElementById('sw-led');
            if (ledCheckbox) {
                // Set flag để tránh trigger event khi update từ server
                if (window.app) {
                    window.app.isUpdatingFromServer = true;
                }
                ledCheckbox.checked = data.enabled;
                // Reset flag sau một chút
                setTimeout(() => {
                    if (window.app) {
                        window.app.isUpdatingFromServer = false;
                    }
                }, 100);
            }
            Logger.log(`LED: ${data.enabled ? 'Bật' : 'Tắt'}`, data.enabled ? 'success' : 'info');
        }
    }
    
    handleLedToggleResult(data) {
        if (data.success) {
            this.handleLedState(data);
            Logger.log(`LED đã ${data.enabled ? 'bật' : 'tắt'}`, 'success');
        } else {
            // Nếu toggle failed, revert switch state
            const ledCheckbox = document.getElementById('sw-led');
            if (ledCheckbox) {
                // Revert về trạng thái cũ (ngược lại với enabled trong response)
                ledCheckbox.checked = !data.enabled;
            }
            Logger.log(`Lỗi khi ${data.enabled ? 'bật' : 'tắt'} LED: ${data.message}`, 'error');
        }
    }
}

