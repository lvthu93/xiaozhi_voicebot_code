/**
 * App - Main orchestrator class
 * Dependencies: All modules
 */
class App {
            constructor() {
                this.connection = new SpeakerConnection();
                this.aiboxPlus = new AiboxPlusConnection();
                this.control = new SpeakerControl(this.connection);
                this.ui = new AppUI();
                this.wifi = new WifiManager(this.aiboxPlus);
                this.chat = new ChatManager(this.aiboxPlus);
                this.mac = new MacManager(this.aiboxPlus);
                this.led = new LedManager(this.aiboxPlus);
                
                // Flag để tránh trigger event khi cập nhật từ server
                this.isUpdatingFromServer = false;
                
                // Flag để track YouTube đang phát
                this.isYouTubePlaying = false;
                // Source hiện tại: 'youtube', 'zing', hoặc null
                this.currentMusicSource = null;
                // Auto-next enabled (mặc định true)
                this.autoNextEnabled = true;
                
                // Loại tìm kiếm hiện tại: 'songs' hoặc 'playlist'
                this.searchType = 'songs';
                
                // Bindings (removed - auto connect only)
                
                // Bind event listeners cho các controls (sau khi DOM ready)
                setTimeout(() => this.initEventListeners(), 100);
                
                // Auto-scan and connect on load
                this.initAutoScan();
                
                // Load MAC address khi trang load
                setTimeout(() => {
                    if(this.mac) {
                        this.mac.load();
                    }
                }, 500);
            }

            initEventListeners() {
                // Lưu event handlers để có thể remove/add lại
                this.eventHandlers = {};
                
                // Volume slider
                const volSlider = document.getElementById('volSlider');
                if(volSlider) {
                    this.eventHandlers.volInput = (e) => {
                        if(!this.isUpdatingFromServer) {
                            app.ui.updateVol(e.target.value);
                        }
                    };
                    this.eventHandlers.volChange = (e) => {
                        if(!this.isUpdatingFromServer) {
                            app.control.setVolume(e.target.value);
                        }
                    };
                    volSlider.addEventListener('input', this.eventHandlers.volInput);
                    volSlider.addEventListener('change', this.eventHandlers.volChange);
                }

                // Main light checkbox
                const mainLightCheckbox = document.getElementById('sw-main-light');
                if(mainLightCheckbox) {
                    this.eventHandlers.mainLightChange = (e) => {
                        if(!this.isUpdatingFromServer && app.lightControl) {
                            app.lightControl.toggleMainLight(e.target.checked);
                        }
                    };
                    mainLightCheckbox.addEventListener('change', this.eventHandlers.mainLightChange);
                }

                // Brightness slider
                const brightnessSlider = document.getElementById('mainBrightness');
                if(brightnessSlider) {
                    this.eventHandlers.brightnessInput = (e) => {
                        if(!this.isUpdatingFromServer && app.lightControl) {
                            const val = e.target.value;
                            document.getElementById('brightnessDsp').innerText = val; // Hiển thị giá trị thực (1-200), không có %
                            app.lightControl.setLightBrightness(val);
                        }
                    };
                    brightnessSlider.addEventListener('input', this.eventHandlers.brightnessInput);
                }

                // Speed slider
                const speedSlider = document.getElementById('mainSpeed');
                if(speedSlider) {
                    this.eventHandlers.speedInput = (e) => {
                        if(!this.isUpdatingFromServer && app.lightControl) {
                            const val = e.target.value;
                            document.getElementById('speedDsp').innerText = val; // Hiển thị giá trị thực (1-100), không có %
                            app.lightControl.setLightSpeed(val);
                        }
                    };
                    speedSlider.addEventListener('input', this.eventHandlers.speedInput);
                }

                // DLNA checkbox
                const dlnaCheckbox = document.getElementById('sw-dlna');
                if(dlnaCheckbox) {
                    this.eventHandlers.dlnaChange = (e) => {
                        if(!this.isUpdatingFromServer) {
                            app.control.toggleDLNA(e.target.checked);
                        }
                    };
                    dlnaCheckbox.addEventListener('change', this.eventHandlers.dlnaChange);
                }

                // AirPlay checkbox
                const airplayCheckbox = document.getElementById('sw-airplay');
                if(airplayCheckbox) {
                    this.eventHandlers.airplayChange = (e) => {
                        if(!this.isUpdatingFromServer) {
                            app.control.toggleAirPlay(e.target.checked);
                        }
                    };
                    airplayCheckbox.addEventListener('change', this.eventHandlers.airplayChange);
                }

                // Bluetooth checkbox
                const bluetoothCheckbox = document.getElementById('sw-bluetooth');
                if(bluetoothCheckbox) {
                    this.eventHandlers.bluetoothChange = (e) => {
                        if(!this.isUpdatingFromServer) {
                            app.control.setBluetooth(e.target.checked);
                        }
                    };
                    bluetoothCheckbox.addEventListener('change', this.eventHandlers.bluetoothChange);
                }
                
                // LED checkbox
                const ledCheckbox = document.getElementById('sw-led');
                if(ledCheckbox) {
                    this.eventHandlers.ledChange = (e) => {
                        if(!this.isUpdatingFromServer && app.led) {
                            app.led.toggleLed();
                        }
                    };
                    ledCheckbox.addEventListener('change', this.eventHandlers.ledChange);
                }

                // Media Search (sử dụng thẻ có sẵn)
                const musicQuery = document.getElementById('musicQuery');
                if(musicQuery) {
                    this.eventHandlers.musicQueryEnter = (e) => {
                        if(e.key === 'Enter') {
                            const query = e.target.value.trim();
                            if(query) {
                                if(app.searchType === 'playlist') {
                                    app.aiboxPlus.searchPlaylist(query);
                                } else {
                                    app.aiboxPlus.search(query);
                                }
                            }
                        }
                    };
                    musicQuery.addEventListener('keypress', this.eventHandlers.musicQueryEnter);
                }
            }

            removeEventListeners() {
                const volSlider = document.getElementById('volSlider');
                if(volSlider && this.eventHandlers.volInput) {
                    volSlider.removeEventListener('input', this.eventHandlers.volInput);
                    volSlider.removeEventListener('change', this.eventHandlers.volChange);
                }

                const mainLightCheckbox = document.getElementById('sw-main-light');
                if(mainLightCheckbox && this.eventHandlers.mainLightChange) {
                    mainLightCheckbox.removeEventListener('change', this.eventHandlers.mainLightChange);
                }

                const brightnessSlider = document.getElementById('mainBrightness');
                if(brightnessSlider && this.eventHandlers.brightnessInput) {
                    brightnessSlider.removeEventListener('input', this.eventHandlers.brightnessInput);
                }

                const speedSlider = document.getElementById('mainSpeed');
                if(speedSlider && this.eventHandlers.speedInput) {
                    speedSlider.removeEventListener('input', this.eventHandlers.speedInput);
                }

                const dlnaCheckbox = document.getElementById('sw-dlna');
                if(dlnaCheckbox && this.eventHandlers.dlnaChange) {
                    dlnaCheckbox.removeEventListener('change', this.eventHandlers.dlnaChange);
                }

                const airplayCheckbox = document.getElementById('sw-airplay');
                if(airplayCheckbox && this.eventHandlers.airplayChange) {
                    airplayCheckbox.removeEventListener('change', this.eventHandlers.airplayChange);
                }

                const bluetoothCheckbox = document.getElementById('sw-bluetooth');
                if(bluetoothCheckbox && this.eventHandlers.bluetoothChange) {
                    bluetoothCheckbox.removeEventListener('change', this.eventHandlers.bluetoothChange);
                }
                
                const ledCheckbox = document.getElementById('sw-led');
                if(ledCheckbox && this.eventHandlers.ledChange) {
                    ledCheckbox.removeEventListener('change', this.eventHandlers.ledChange);
                }
            }

            initAutoScan() {
                // Get MAC address on load
                if(this.mac) {
                    this.mac.load();
                }
                // Get LED state on load
                if(this.led) {
                    this.led.getLedState();
                }
                
                // Lấy IP từ URL hiện tại (window.location.hostname)
                // Nếu user truy cập http://192.168.1.47:8081 thì IP đó chính là IP của thiết bị
                const currentIP = window.location.hostname;
                const savedIP = localStorage.getItem('r1_ip');
                const autoConnect = localStorage.getItem('r1_autoConnect') === 'true';
                
                // Ưu tiên IP từ URL hiện tại
                if(currentIP && currentIP !== 'localhost' && currentIP !== '127.0.0.1') {
                    Logger.log(`Using IP from URL: ${currentIP}`);
                    localStorage.setItem('r1_ip', currentIP);
                    
                    // Tự động kết nối WebSocket đến port 8080
                    this.connection.autoConnectEnabled = true;
                    this.connection.connect(currentIP);
                    // Kết nối AiboxPlus WebSocket trên port 8082
                    this.aiboxPlus.connect(currentIP);
                    this.updateAutoConnectButton();
                }
                // Nếu không có IP từ URL, dùng IP đã lưu và tự động kết nối
                else if(savedIP && autoConnect) {
                    setTimeout(() => {
                        this.connection.autoConnectEnabled = true;
                        this.connection.connect(savedIP);
                        // Kết nối YouTube WebSocket trên port 8082
                        this.aiboxPlus.connect(savedIP);
                        this.updateAutoConnectButton();
                    }, 500);
                }
            }

            connect() {
                // Ưu tiên lấy IP từ URL hiện tại
                let ip = window.location.hostname;
                
                // Nếu không có IP từ URL hoặc là localhost, lấy từ localStorage
                if(!ip || ip === 'localhost' || ip === '127.0.0.1') {
                    ip = localStorage.getItem('r1_ip') || '';
                }
                
                // Validate IP format (simple check)
                if(!ip || !/^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/.test(ip)) {
                    Logger.log('Invalid IP format. Use: 192.168.1.100', 'error');
                    return;
                }
                
                // Cập nhật localStorage
                localStorage.setItem('r1_ip', ip);
                this.connection.connect(ip);
                // Kết nối YouTube WebSocket trên port 8082
                this.aiboxPlus.connect(ip);
            }

            toggleAutoConnect() {
                const enabled = !this.connection.autoConnectEnabled;
                this.connection.autoConnectEnabled = enabled;
                localStorage.setItem('r1_autoConnect', enabled ? 'true' : 'false');
                this.updateAutoConnectButton();
                Logger.log(`Auto-connect ${enabled ? 'enabled' : 'disabled'}`);
                
                if(enabled && !this.connection.isConnected) {
                    const ip = window.location.hostname || localStorage.getItem('r1_ip') || '';
                    if(ip && ip !== 'localhost' && ip !== '127.0.0.1') {
                        this.connection.connect(ip);
                        // Kết nối YouTube WebSocket trên port 8082
                        this.aiboxPlus.connect(ip);
                    } else {
                        Logger.log('No IP configured for auto-connect', 'error');
                    }
                }
            }

            updateAutoConnectButton() {
                // UI removed - auto connect only
            }

            scanIP() {
                // Đơn giản: Lấy IP từ URL hiện tại
                const currentIP = window.location.hostname;
                if(currentIP && currentIP !== 'localhost' && currentIP !== '127.0.0.1') {
                    localStorage.setItem('r1_ip', currentIP);
                    Logger.log(`IP từ URL: ${currentIP}`);
                } else {
                    Logger.log('Không thể lấy IP từ URL. Vui lòng nhập IP thủ công.', 'error');
                }
            }

            formatTime(seconds) {
                if(!seconds || seconds < 0) return '0:00';
                const mins = Math.floor(seconds / 60);
                const secs = Math.floor(seconds % 60);
                return `${mins}:${secs.toString().padStart(2, '0')}`;
            }

            /**
             * Cập nhật Media Card từ YouTube (WebSocket 8082)
             * Đơn giản: get_info như nào thì hiển thị như thế
             */
            updateMediaFromYouTube(data) {
                if(data.type === 'playback_state') {
                    // Kiểm tra source từ data
                    const source = data.source || 'youtube';
                    this.currentMusicSource = source;
                    this.isYouTubePlaying = data.is_playing && (source === 'youtube' || source === 'zing');
                    
                    // Cập nhật auto-next state
                    if(data.auto_next_enabled !== undefined) {
                        this.autoNextEnabled = data.auto_next_enabled;
                        this.updateAutoNextButton();
                    }
                    
                    // Cập nhật source badge
                    this.updateSourceBadge(source, data.is_playing);
                    
                    // Luôn update, nếu title rỗng thì clear UI
                    this.updateMediaCard(
                        data.title || "Không có nhạc",
                        data.artist || "---",
                        data.thumbnail_url || '',
                        data.position || 0,
                        data.duration || 0,
                        data.is_playing
                    );
                } else if(data.type === 'play_started') {
                    this.isYouTubePlaying = true;
                    this.currentMusicSource = 'youtube';
                    this.updateSourceBadge('youtube', true);
                    this.updateMediaCard(
                        data.title || "Đang phát...",
                        data.channel || "---",
                        data.thumbnail_url || '',
                        0,
                        0,
                        true
                    );
                } else if(data.type === 'stop') {
                    // Khi stop, clear UI
                    this.isYouTubePlaying = false;
                    this.currentMusicSource = null;
                    this.updateSourceBadge(null, false);
                    this.updateMediaCard("Không có nhạc", "---", "", 0, 0, false);
                }
            }
            
            /**
             * Cập nhật source badge
             */
            updateSourceBadge(source, isPlaying) {
                const sourceBadge = document.getElementById('sourceBadge');
                if(!sourceBadge) return;
                
                if(!isPlaying || !source) {
                    sourceBadge.innerText = "IDLE";
                } else if(source === 'zing') {
                    sourceBadge.innerText = "ZING MP3";
                } else if(source === 'youtube') {
                    sourceBadge.innerText = "YOUTUBE";
                } else {
                    sourceBadge.innerText = "IDLE";
                }
            }

            /**
             * Cập nhật Media Card với thông tin bài hát
             */
            updateMediaCard(title, artist, albumArt, position, duration, isPlaying) {
                // Update title và artist
                const trackTitleEl = document.getElementById('trackTitle');
                const trackArtistEl = document.getElementById('trackArtist');
                if(trackTitleEl) trackTitleEl.innerText = title || "Không có nhạc";
                if(trackArtistEl) trackArtistEl.innerText = artist || "---";
                
                // Show/hide waveform based on playing state
                const waveformContainer = document.getElementById('waveformContainer');
                if(waveformContainer) {
                    if(isPlaying) {
                        waveformContainer.classList.remove('hidden');
                    } else {
                        waveformContainer.classList.add('hidden');
                    }
                }
                
                // Update album art - sử dụng ảnh mặc định nếu không có URL
                const defaultAlbumArt = 'https://ai-box-plus.com/uploads/1762967762492_testz7217871844111_dbeb2a27c8246cdc136b376b28b8bee1.jpg';
                const finalAlbumArt = (albumArt && albumArt.trim()) ? albumArt : defaultAlbumArt;
                
                const albumArtEl = document.getElementById('albumArt');
                const albumArtThumb = document.getElementById('albumArtThumb');
                const albumArtIcon = document.getElementById('albumArtIcon');
                
                // Update background album art
                if(albumArtEl) {
                    albumArtEl.src = finalAlbumArt;
                    albumArtEl.style.display = 'block';
                    albumArtEl.classList.remove('opacity-0');
                    albumArtEl.classList.add('opacity-20');
                }
                
                // Update thumbnail
                if(albumArtThumb) {
                    albumArtThumb.src = finalAlbumArt;
                    albumArtThumb.classList.remove('hidden');
                }
                
                // Hide icon khi có ảnh
                if(albumArtIcon) {
                    albumArtIcon.style.display = 'none';
                }
                
                // Update progress bar
                const progressBar = document.getElementById('progressBar');
                const progressCurrent = document.getElementById('progressCurrent');
                const progressTotal = document.getElementById('progressTotal');
                
                if(duration > 0) {
                    const percent = Math.min(100, Math.max(0, (position / duration) * 100));
                    if(progressBar) progressBar.style.width = percent + '%';
                    if(progressCurrent) progressCurrent.innerText = this.formatTime(position);
                    if(progressTotal) progressTotal.innerText = this.formatTime(duration);
                } else {
                    if(progressBar) progressBar.style.width = '0%';
                    if(progressCurrent) progressCurrent.innerText = '0:00';
                    if(progressTotal) progressTotal.innerText = '0:00';
                }
                
                // Update play state
                this.control.state.isPlaying = isPlaying;
            }
            
            /**
             * Tua đến vị trí khi click vào progress bar
             */
            seekToPosition(event) {
                if(!this.isYouTubePlaying || !app.aiboxPlus || !app.aiboxPlus.isConnected) {
                    return;
                }
                
                const progressBarContainer = document.getElementById('progressBarContainer');
                if(!progressBarContainer) return;
                
                const rect = progressBarContainer.getBoundingClientRect();
                const clickX = event.clientX - rect.left;
                const percent = Math.max(0, Math.min(1, clickX / rect.width));
                
                // Lấy duration từ progressTotal
                const progressTotal = document.getElementById('progressTotal');
                if(!progressTotal) return;
                
                // Parse duration từ text (format: "M:SS" hoặc "MM:SS")
                const timeText = progressTotal.innerText;
                const timeParts = timeText.split(':');
                if(timeParts.length !== 2) return;
                
                const totalSeconds = parseInt(timeParts[0]) * 60 + parseInt(timeParts[1]);
                if(totalSeconds <= 0) return;
                
                const seekPosition = Math.floor(totalSeconds * percent);
                app.aiboxPlus.seek(seekPosition);
                Logger.log('Seek to: ' + seekPosition + 's (' + (percent * 100).toFixed(1) + '%)');
            }

            updateState(data) {
                console.log('[App] updateState called with data:', data);
                // Đánh dấu đang cập nhật từ server để tránh trigger event
                this.isUpdatingFromServer = true;
                
                // Tạm thời remove event listeners để tránh trigger khi set giá trị
                if(this.eventHandlers) {
                    this.removeEventListeners();
                }
                
                // Giống file tham khảo: this.deviceInfo = JSON.parse(jData.data)
                // Vue tự động cập nhật UI, nhưng tôi phải tự cập nhật từng element
                // LUÔN cập nhật TẤT CẢ các trường mỗi khi nhận get_info (từ heartbeat 950ms)
                
                // 1. Sync Volume - LUÔN cập nhật
                if(data.vol !== undefined) {
                    const volSlider = document.getElementById('volSlider');
                    const volText = document.getElementById('volText');
                    if(volSlider) volSlider.value = data.vol;
                    if(volText) volText.innerText = `Mức ${data.vol}`;
                    this.control.state.vol = data.vol;
                }
                
                // 2. Media Card chỉ được đồng bộ từ YouTube (8082), không sync từ 8080

                // 4. Source Badge Sync - LUÔN cập nhật
                // Ưu tiên music source từ WebSocket 8082, sau đó mới đến các source khác
                let source = "IDLE";
                if(this.currentMusicSource === 'zing' && this.isYouTubePlaying) {
                    source = "ZING MP3";
                } else if(this.currentMusicSource === 'youtube' && this.isYouTubePlaying) {
                    source = "YOUTUBE";
                }
                const sourceBadge = document.getElementById('sourceBadge');
                if(sourceBadge) sourceBadge.innerText = source;

                // 4.5. Sync DLNA/AirPlay States - LUÔN cập nhật
                if(data.dlna_open !== undefined) {
                    const dlnaCheckbox = document.getElementById('sw-dlna');
                    if(dlnaCheckbox) dlnaCheckbox.checked = data.dlna_open;
                }
                if(data.airplay_open !== undefined) {
                    const airplayCheckbox = document.getElementById('sw-airplay');
                    if(airplayCheckbox) airplayCheckbox.checked = data.airplay_open;
                }
                // Bluetooth checkbox: checked nếu device_state == 3
                if(data.device_state !== undefined) {
                    const bluetoothCheckbox = document.getElementById('sw-bluetooth');
                    if(bluetoothCheckbox) bluetoothCheckbox.checked = (data.device_state === 3);
                }
                
                // 5. Sync Light States - LUÔN cập nhật
                if(data.music_light_enable !== undefined) {
                    const mainLightCheckbox = document.getElementById('sw-main-light');
                    if(mainLightCheckbox) mainLightCheckbox.checked = data.music_light_enable;
                }
                
                // 6. Sync Brightness - LUÔN cập nhật
                // File tham khảo: music_light_luma là 1-200, slider :min="1" :max="200"
                // Dùng trực tiếp giá trị từ server (1-200)
                if(data.music_light_luma !== undefined) {
                    const brightnessSlider = document.getElementById('mainBrightness');
                    const brightnessDsp = document.getElementById('brightnessDsp');
                    const brightnessValue = Math.max(1, Math.min(200, Math.round(data.music_light_luma))); // Clamp 1-200
                    if(brightnessSlider) brightnessSlider.value = brightnessValue;
                    if(brightnessDsp) brightnessDsp.innerText = brightnessValue; // Hiển thị giá trị thực, không có %
                }
                
                // 7. Sync Speed - LUÔN cập nhật
                // File tham khảo: music_light_chroma là 1-100, slider :min="1" :max="100"
                // Dùng trực tiếp giá trị từ server (1-100)
                if(data.music_light_chroma !== undefined) {
                    const speedSlider = document.getElementById('mainSpeed');
                    const speedDsp = document.getElementById('speedDsp');
                    const speedValue = Math.max(1, Math.min(100, Math.round(data.music_light_chroma))); // Clamp 1-100
                    if(speedSlider) speedSlider.value = speedValue;
                    if(speedDsp) speedDsp.innerText = speedValue; // Hiển thị giá trị thực, không có %
                }
                
                // Add lại event listeners và reset flag
                setTimeout(() => {
                    // Add lại event listeners (chỉ add, không init lại toàn bộ)
                    const volSlider = document.getElementById('volSlider');
                    if(volSlider && this.eventHandlers) {
                        if(this.eventHandlers.volInput) volSlider.addEventListener('input', this.eventHandlers.volInput);
                        if(this.eventHandlers.volChange) volSlider.addEventListener('change', this.eventHandlers.volChange);
                    }

                    const mainLightCheckbox = document.getElementById('sw-main-light');
                    if(mainLightCheckbox && this.eventHandlers && this.eventHandlers.mainLightChange) {
                        mainLightCheckbox.addEventListener('change', this.eventHandlers.mainLightChange);
                    }

                    const brightnessSlider = document.getElementById('mainBrightness');
                    if(brightnessSlider && this.eventHandlers && this.eventHandlers.brightnessInput) {
                        brightnessSlider.addEventListener('input', this.eventHandlers.brightnessInput);
                    }

                const speedSlider = document.getElementById('mainSpeed');
                if(speedSlider && this.eventHandlers && this.eventHandlers.speedInput) {
                    speedSlider.addEventListener('input', this.eventHandlers.speedInput);
                }

                const dlnaCheckbox = document.getElementById('sw-dlna');
                if(dlnaCheckbox && this.eventHandlers && this.eventHandlers.dlnaChange) {
                    dlnaCheckbox.addEventListener('change', this.eventHandlers.dlnaChange);
                }

                const airplayCheckbox = document.getElementById('sw-airplay');
                if(airplayCheckbox && this.eventHandlers && this.eventHandlers.airplayChange) {
                    airplayCheckbox.addEventListener('change', this.eventHandlers.airplayChange);
                }

                const bluetoothCheckbox = document.getElementById('sw-bluetooth');
                if(bluetoothCheckbox && this.eventHandlers && this.eventHandlers.bluetoothChange) {
                    bluetoothCheckbox.addEventListener('change', this.eventHandlers.bluetoothChange);
                }
                
                const ledCheckbox = document.getElementById('sw-led');
                if(ledCheckbox && this.eventHandlers && this.eventHandlers.ledChange) {
                    ledCheckbox.addEventListener('change', this.eventHandlers.ledChange);
                }

                    this.isUpdatingFromServer = false;
                }, 10);
            }
            
            updateEqState(data) {
                // Sync EQ Bands
                if(data.eq && data.eq.Bands && data.eq.Bands.list) {
                    data.eq.Bands.list.forEach((band, index) => {
                        const slider = document.querySelector(`input[data-band="${index}"]`);
                        if(slider) {
                            slider.value = band.BandLevel;
                        }
                    });
                }
                
                // Sync Bass/Loudness states
                if(data.bass && data.bass.sound_effects_bass_enable !== undefined) {
                    const bassCheckbox = document.querySelector('input[onchange*="toggleBass"]');
                    if(bassCheckbox) bassCheckbox.checked = data.bass.sound_effects_bass_enable;
                }
                
                if(data.loudness && data.loudness.sound_effects_loudness_enable !== undefined) {
                    const loudnessCheckbox = document.querySelector('input[onchange*="toggleLoudness"]');
                    if(loudnessCheckbox) loudnessCheckbox.checked = data.loudness.sound_effects_loudness_enable;
                }
            }

            updateSystemStats(rawText) {
                if (!rawText) return;
                
                // Robust Parsing Logic (No explicit split needed)
                // 1. Memory
                const matchVal = (key, text) => {
                    const re = new RegExp(`${key}:\\s+(\\d+)`);
                    const m = text.match(re);
                    return m ? parseInt(m[1]) : 0;
                };

                const memTotal = matchVal('MemTotal', rawText);
                const memFree = matchVal('MemFree', rawText);
                const memBuffers = matchVal('Buffers', rawText);
                const memCached = matchVal('Cached', rawText);

                if (memTotal > 0) {
                    const usedRam = memTotal - (memFree + memBuffers + memCached);
                    const ramPercent = Math.round((usedRam / memTotal) * 100) || 0;
                    
                    const ramBar = document.getElementById('ramBar');
                    const ramText = document.getElementById('ramText');
                    
                    if(ramBar) {
                        ramBar.style.width = `${ramPercent}%`;
                        ramBar.className = `h-full transition-all duration-500 ${ramPercent > 80 ? 'bg-red-500' : 'bg-blue-500'}`;
                    }
                    if(ramText) ramText.innerText = `${ramPercent}%`;
                }

                // 2. CPU
                // Find line starting with "cpu " (multi-line supported)
                const cpuMatch = rawText.match(/^cpu\s+(.+)$/m);
                if(cpuMatch) {
                    const parts = cpuMatch[1].trim().split(/\s+/).map(n => parseInt(n));
                    if(parts.length >= 7) {
                        // user + nice + system + idle + iowait + irq + softirq
                        const idle = parts[3];
                        const total = parts.reduce((a, b) => a + b, 0);
                        
                        if(this.ui.lastCpu) {
                            const dIdle = idle - this.ui.lastCpu.idle;
                            const dTotal = total - this.ui.lastCpu.total;
                            const usage = dTotal > 0 ? Math.round(100 - (dIdle / dTotal * 100)) : 0;
                            
                            const cpuBar = document.getElementById('cpuBar');
                            const cpuText = document.getElementById('cpuText');
                            
                            if(cpuBar) cpuBar.style.width = `${usage}%`;
                            if(cpuText) cpuText.innerText = `${usage}%`;
                        }
                        this.ui.lastCpu = { idle, total };
                    }
                }
            }

            playMusicInput() {
                const q = document.getElementById('musicQuery').value;
                if(q) {
                    // Gửi qua AiboxPlus WebSocket để tìm kiếm YouTube
                    if(this.aiboxPlus && this.aiboxPlus.isConnected) {
                        if(this.searchType === 'playlist') {
                            this.aiboxPlus.searchPlaylist(q);
                        } else if(this.searchType === 'zing') {
                            this.aiboxPlus.searchZing(q);
                        } else {
                            this.aiboxPlus.search(q);
                        }
                    } else {
                        // Fallback: gửi qua WebSocket cũ
                        this.control.conn.send({type:'send_message', what: 65536, arg1:0, arg2:1, obj: 'web_' + q});
                    }
                }
            }
            
            /**
             * Chuyển đổi tab tìm kiếm (Songs/Playlist/Zing/Báo thức)
             */
            switchSearchTab(type) {
                this.searchType = type;
                const tabSongs = document.getElementById('tab-songs');
                const tabPlaylist = document.getElementById('tab-playlist');
                const tabZing = document.getElementById('tab-zing');
                const tabAlarm = document.getElementById('tab-alarm');
                const musicQuery = document.getElementById('musicQuery');
                const searchSection = document.getElementById('searchSection');
                const alarmSection = document.getElementById('alarmSection');
                
                // Reset all tabs
                [tabSongs, tabPlaylist, tabZing, tabAlarm].forEach(tab => {
                    if(tab) {
                        tab.classList.remove('text-blue-400', 'border-b-2', 'border-blue-400');
                        tab.classList.add('text-slate-400');
                    }
                });
                
                // Ẩn/hiện sections
                if(searchSection) searchSection.classList.toggle('hidden', type === 'alarm');
                if(alarmSection) alarmSection.classList.toggle('hidden', type !== 'alarm');
                
                if(type === 'songs' && tabSongs) {
                    tabSongs.classList.add('text-blue-400', 'border-b-2', 'border-blue-400');
                    tabSongs.classList.remove('text-slate-400');
                    if(musicQuery) musicQuery.placeholder = 'Tìm bài hát...';
                } else if(type === 'playlist' && tabPlaylist) {
                    tabPlaylist.classList.add('text-blue-400', 'border-b-2', 'border-blue-400');
                    tabPlaylist.classList.remove('text-slate-400');
                    if(musicQuery) musicQuery.placeholder = 'Tìm playlist...';
                } else if(type === 'zing' && tabZing) {
                    tabZing.classList.add('text-blue-400', 'border-b-2', 'border-blue-400');
                    tabZing.classList.remove('text-slate-400');
                    if(musicQuery) musicQuery.placeholder = 'Tìm bài hát Zing MP3...';
                } else if(type === 'alarm' && tabAlarm) {
                    tabAlarm.classList.add('text-blue-400', 'border-b-2', 'border-blue-400');
                    tabAlarm.classList.remove('text-slate-400');
                    // Load danh sách báo thức khi chuyển sang tab
                    this.loadAlarms();
                }
            }
            
            toggleAutoNext() {
                if(this.aiboxPlus && this.aiboxPlus.isConnected) {
                    this.aiboxPlus.toggleAutoNext();
                } else {
                    Logger.log('AiboxPlus WebSocket not connected', 'error');
                }
            }
            
            updateAutoNextButton() {
                const btnAutoNext = document.getElementById('btnAutoNext');
                if(btnAutoNext) {
                    if(this.autoNextEnabled) {
                        btnAutoNext.classList.add('bg-blue-600/50', 'text-blue-300');
                        btnAutoNext.classList.remove('bg-black/30', 'text-white/90');
                        btnAutoNext.title = 'Tự động phát tiếp (Bật)';
                    } else {
                        btnAutoNext.classList.remove('bg-blue-600/50', 'text-blue-300');
                        btnAutoNext.classList.add('bg-black/30', 'text-white/90');
                        btnAutoNext.title = 'Tự động phát tiếp (Tắt)';
                    }
                }
            }
            
            showAlarmBanner(data) {
                const banner = document.getElementById('alarmBanner');
                const title = document.getElementById('alarmBannerTitle');
                const message = document.getElementById('alarmBannerMessage');
                
                if(banner && title && message) {
                    title.textContent = data.message || 'Báo thức';
                    message.textContent = `Thời gian: ${data.time || ''}${data.alarm_label ? ' - ' + data.alarm_label : ''}`;
                    banner.classList.remove('-translate-y-full');
                    banner.classList.add('translate-y-0');
                }
            }
            
            hideAlarmBanner() {
                const banner = document.getElementById('alarmBanner');
                if(banner) {
                    banner.classList.add('-translate-y-full');
                    banner.classList.remove('translate-y-0');
                }
            }
            
            stopAlarm() {
                if(this.aiboxPlus && this.aiboxPlus.isConnected) {
                    this.aiboxPlus.stopAlarm();
                } else {
                    Logger.log('AiboxPlus WebSocket not connected', 'error');
                }
            }
            
            loadAlarms() {
                if(this.aiboxPlus && this.aiboxPlus.isConnected) {
                    this.aiboxPlus.listAlarms();
                } else {
                    Logger.log('AiboxPlus WebSocket not connected', 'error');
                }
            }
            
            showAddAlarmModal(alarm = null) {
                const isEdit = alarm !== null;
                const modal = document.getElementById('alarmModal');
                const modalTitle = document.getElementById('alarmModalTitle');
                const alarmIdInput = document.getElementById('alarmId');
                const alarmHourInput = document.getElementById('alarmHour');
                const alarmMinuteInput = document.getElementById('alarmMinute');
                const alarmRepeatSelect = document.getElementById('alarmRepeat');
                const alarmLabelInput = document.getElementById('alarmLabel');
                const alarmVolumeInput = document.getElementById('alarmVolume');
                const alarmVolumeDisplay = document.getElementById('alarmVolumeDisplay');
                const alarmSoundFileInput = document.getElementById('alarmSoundFile');
                const alarmSoundFileName = document.getElementById('alarmSoundFileName');
                const alarmSoundFileRemove = document.getElementById('alarmSoundFileRemove');
                const alarmSubmitBtn = document.getElementById('alarmSubmitBtn');
                
                if(modal && modalTitle && alarmHourInput && alarmMinuteInput && alarmRepeatSelect && alarmLabelInput && alarmVolumeInput && alarmVolumeDisplay && alarmSubmitBtn) {
                    if(isEdit) {
                        modalTitle.textContent = 'Chỉnh sửa báo thức';
                        if(alarmIdInput) alarmIdInput.value = alarm.id;
                        alarmHourInput.value = alarm.hour;
                        alarmMinuteInput.value = alarm.minute;
                        alarmRepeatSelect.value = alarm.repeat || 'none';
                        alarmLabelInput.value = alarm.label || '';
                        alarmVolumeInput.value = alarm.volume !== undefined ? alarm.volume : 100;
                        alarmVolumeDisplay.textContent = alarmVolumeInput.value;
                        
                        // Hiển thị file audio hiện tại
                        if(alarmSoundFileName) {
                            if(alarm.custom_sound_path) {
                                const pathParts = alarm.custom_sound_path.split('/');
                                const fileName = pathParts[pathParts.length - 1] || 'Custom';
                                alarmSoundFileName.textContent = 'File hiện tại: ' + fileName;
                                alarmSoundFileName.classList.remove('text-slate-500');
                                alarmSoundFileName.classList.add('text-blue-400');
                            } else if(alarm.youtube_song_name) {
                                alarmSoundFileName.textContent = 'Đang dùng: YouTube - ' + alarm.youtube_song_name;
                                alarmSoundFileName.classList.remove('text-slate-500');
                                alarmSoundFileName.classList.add('text-blue-400');
                            } else {
                                alarmSoundFileName.textContent = 'Đang dùng: Mặc định (alarm.mp3)';
                                alarmSoundFileName.classList.remove('text-blue-400');
                                alarmSoundFileName.classList.add('text-slate-500');
                            }
                        }
                        if(alarmSoundFileInput) alarmSoundFileInput.value = '';
                        
                        // Hiển thị YouTube song name
                        const alarmYoutubeSongName = document.getElementById('alarmYoutubeSongName');
                        if(alarmYoutubeSongName) {
                            alarmYoutubeSongName.value = alarm.youtube_song_name || '';
                        }
                        
                        alarmSubmitBtn.textContent = 'Cập nhật';
                    } else {
                        modalTitle.textContent = 'Thêm báo thức mới';
                        if(alarmIdInput) alarmIdInput.value = '';
                        alarmHourInput.value = '07';
                        alarmMinuteInput.value = '00';
                        alarmRepeatSelect.value = 'none';
                        alarmLabelInput.value = '';
                        alarmVolumeInput.value = '100';
                        alarmVolumeDisplay.textContent = '100';
                        if(alarmSoundFileInput) alarmSoundFileInput.value = '';
                        if(alarmSoundFileName) {
                            alarmSoundFileName.textContent = '';
                            alarmSoundFileName.classList.remove('text-blue-400');
                            alarmSoundFileName.classList.add('text-slate-500');
                        }
                        if(alarmSoundFileRemove) alarmSoundFileRemove.style.display = 'none';
                        
                        // Reset YouTube
                        const alarmYoutubeSongName = document.getElementById('alarmYoutubeSongName');
                        if(alarmYoutubeSongName) alarmYoutubeSongName.value = '';
                        
                        alarmSubmitBtn.textContent = 'Thêm';
                    }
                    
                    // Xử lý khi chọn file mới
                    if(alarmSoundFileInput) {
                        alarmSoundFileInput.onchange = function(e) {
                            const file = e.target.files[0];
                            if(file && alarmSoundFileName) {
                                alarmSoundFileName.textContent = 'Đã chọn: ' + file.name;
                                alarmSoundFileName.classList.remove('text-slate-500');
                                alarmSoundFileName.classList.add('text-blue-400');
                                if(alarmSoundFileRemove) {
                                    alarmSoundFileRemove.style.display = 'inline';
                                }
                            }
                        };
                    }
                    
                    modal.classList.remove('hidden');
                }
            }
            
            hideAlarmModal() {
                const modal = document.getElementById('alarmModal');
                if(modal) {
                    modal.classList.add('hidden');
                }
            }
            
            async submitAlarm() {
                const alarmIdInput = document.getElementById('alarmId');
                const alarmHourInput = document.getElementById('alarmHour');
                const alarmMinuteInput = document.getElementById('alarmMinute');
                const alarmRepeatSelect = document.getElementById('alarmRepeat');
                const alarmLabelInput = document.getElementById('alarmLabel');
                const alarmVolumeInput = document.getElementById('alarmVolume');
                const alarmSoundFileInput = document.getElementById('alarmSoundFile');
                
                if(!alarmHourInput || !alarmMinuteInput) return;
                
                const hour = parseInt(alarmHourInput.value);
                const minute = parseInt(alarmMinuteInput.value);
                const repeat = alarmRepeatSelect ? alarmRepeatSelect.value : 'none';
                const label = alarmLabelInput ? alarmLabelInput.value : '';
                const volume = alarmVolumeInput ? parseInt(alarmVolumeInput.value) : 100;
                const soundFile = alarmSoundFileInput && alarmSoundFileInput.files.length > 0 ? alarmSoundFileInput.files[0] : null;
                
                if(hour < 0 || hour > 23 || minute < 0 || minute > 59) {
                    Logger.log('Giờ hoặc phút không hợp lệ (hour: 0-23, minute: 0-59)', 'error');
                    return;
                }
                
                if(volume < 0 || volume > 100) {
                    Logger.log('Âm lượng không hợp lệ (0-100)', 'error');
                    return;
                }
                
                if(this.aiboxPlus && this.aiboxPlus.isConnected) {
                    const alarmId = alarmIdInput ? parseInt(alarmIdInput.value) : null;
                    
                    // Lấy YouTube song name nếu có
                    const alarmYoutubeSongName = document.getElementById('alarmYoutubeSongName');
                    const youtubeSongName = alarmYoutubeSongName ? alarmYoutubeSongName.value.trim() : null;
                    
                    // Tạo action để thực hiện sau khi upload file
                    const executeAlarmAction = (filePath) => {
                        if(alarmId && !isNaN(alarmId)) {
                            // Edit
                            this.aiboxPlus.editAlarm(alarmId, hour, minute, repeat, label, volume, filePath, youtubeSongName);
                        } else {
                            // Add
                            this.aiboxPlus.addAlarm(hour, minute, repeat, label, volume, filePath, youtubeSongName);
                        }
                        this.hideAlarmModal();
                    };
                    
                    // Upload file nếu có
                    if(soundFile) {
                        Logger.log('Đang upload file âm thanh...', 'info');
                        // Lưu action để thực hiện sau khi upload xong
                        this.aiboxPlus.pendingAlarmAction = {
                            filePath: null,
                            execute: () => executeAlarmAction(this.aiboxPlus.pendingAlarmAction.filePath)
                        };
                        // Upload file
                        this.aiboxPlus.uploadAlarmSound(soundFile, alarmId || -1);
                    } else {
                        // Không có file, thực hiện ngay
                        executeAlarmAction(null);
                    }
                } else {
                    Logger.log('AiboxPlus WebSocket not connected', 'error');
                }
            }
            
            deleteAlarm(alarmId) {
                if(confirm('Bạn có chắc chắn muốn xóa báo thức này?')) {
                    if(this.aiboxPlus && this.aiboxPlus.isConnected) {
                        this.aiboxPlus.deleteAlarm(alarmId);
                    } else {
                        Logger.log('AiboxPlus WebSocket not connected', 'error');
                    }
                }
            }
            
            toggleAlarm(alarmId) {
                if(this.aiboxPlus && this.aiboxPlus.isConnected) {
                    this.aiboxPlus.toggleAlarm(alarmId);
                } else {
                    Logger.log('AiboxPlus WebSocket not connected', 'error');
                }
            }
            
            renderAlarmList(alarms) {
                const alarmList = document.getElementById('alarmList');
                if(!alarmList) return;
                
                if(!alarms || alarms.length === 0) {
                    alarmList.innerHTML = `
                        <div class="text-center text-slate-500 text-xs py-4">
                            <i class="fa-solid fa-bell-slash text-xl mb-2 block"></i>
                            Chưa có báo thức nào
                        </div>
                    `;
                    return;
                }
                
                let html = '';
                alarms.forEach(alarm => {
                    const timeStr = `${String(alarm.hour).padStart(2, '0')}:${String(alarm.minute).padStart(2, '0')}`;
                    const repeatStr = alarm.repeat === 'daily' ? 'Hàng ngày' : (alarm.repeat === 'weekly' ? 'Hàng tuần' : 'Một lần');
                    const volume = alarm.volume !== undefined ? alarm.volume : 100;
                    const enabledClass = alarm.enabled ? 'bg-green-600/20 border-green-600/30' : 'bg-slate-800/50 border-slate-700/50 opacity-60';
                    const enabledIcon = alarm.enabled ? 'fa-bell' : 'fa-bell-slash';
                    
                    // Lấy tên file audio
                    let soundFileName = 'Mặc định (alarm.mp3)';
                    if(alarm.custom_sound_path) {
                        const pathParts = alarm.custom_sound_path.split('/');
                        soundFileName = pathParts[pathParts.length - 1] || 'Custom';
                    }
                    
                    html += `
                        <div class="bg-slate-800/50 border border-slate-700/50 rounded-lg p-3 ${enabledClass}">
                            <div class="flex items-center justify-between mb-2">
                                <div class="flex items-center gap-2">
                                    <i class="fa-solid ${enabledIcon} text-blue-400"></i>
                                    <span class="text-white font-bold text-sm">${timeStr}</span>
                                    ${alarm.label ? `<span class="text-slate-400 text-xs">- ${alarm.label}</span>` : ''}
                                </div>
                                <div class="flex items-center gap-1">
                                    <button onclick="app.toggleAlarm(${alarm.id})" class="text-xs px-2 py-1 rounded ${alarm.enabled ? 'bg-yellow-600/20 text-yellow-400 hover:bg-yellow-600/30' : 'bg-green-600/20 text-green-400 hover:bg-green-600/30'}" title="${alarm.enabled ? 'Tắt' : 'Bật'}">
                                        <i class="fa-solid ${alarm.enabled ? 'fa-toggle-on' : 'fa-toggle-off'}"></i>
                                    </button>
                                    <button onclick="app.showAddAlarmModal(${JSON.stringify(alarm).replace(/"/g, '&quot;')})" class="text-xs px-2 py-1 rounded bg-blue-600/20 text-blue-400 hover:bg-blue-600/30" title="Sửa">
                                        <i class="fa-solid fa-edit"></i>
                                    </button>
                                    <button onclick="app.deleteAlarm(${alarm.id})" class="text-xs px-2 py-1 rounded bg-red-600/20 text-red-400 hover:bg-red-600/30" title="Xóa">
                                        <i class="fa-solid fa-trash"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="text-xs text-slate-400 flex items-center gap-3 flex-wrap">
                                <span><i class="fa-solid fa-repeat mr-1"></i>${repeatStr}</span>
                                <span><i class="fa-solid fa-volume-high mr-1"></i>${volume}%</span>
                                <span><i class="fa-solid fa-music mr-1"></i>${soundFileName}</span>
                            </div>
                        </div>
                    `;
                });
                alarmList.innerHTML = html;
            }
            
            speakInput() {
                const q = document.getElementById('ttsInput').value;
                if(q) this.control.conn.send({type:'send_message', what: 65536, obj: q});
            }
            runShell() {
                const q = document.getElementById('shellInput').value;
                if(q) { 
                    this.control.shell(q); 
                    Logger.log(`$ ${q}`, 'sent');
                    document.getElementById('shellInput').value = '';
                }
            }
        }
// Initialize app instance
const app = new App();
window.app = app; // Expose globally for HTML onclick handlers

// Initialize audioControl and lightControl (lazy initialization)
app.audioControl = new AudioControl(app.connection);
app.lightControl = new LightControl(app.connection);

// Wake Word Control
app.wakeWordControl = {
    setSensitivity(value) {
        const sensitivity = parseFloat(value);
        if (isNaN(sensitivity) || sensitivity < 0 || sensitivity > 1) {
            Logger.log('Invalid sensitivity value: ' + value, 'error');
            return;
        }
        
        // Update UI
        const displayEl = document.getElementById('val-wake-sensitivity');
        if (displayEl) {
            displayEl.innerText = sensitivity.toFixed(2);
        }
        
        // Send to server
        if (app.aiboxPlus && app.aiboxPlus.isConnected) {
            app.aiboxPlus.send({
                action: 'wake_word_set_sensitivity',
                sensitivity: sensitivity
            });
            Logger.log(`Wake word sensitivity set to ${sensitivity.toFixed(2)}`);
        } else {
            Logger.log('AiboxPlus not connected', 'error');
        }
    },
    
    getSensitivity() {
        if (app.aiboxPlus && app.aiboxPlus.isConnected) {
            app.aiboxPlus.send({
                action: 'wake_word_get_sensitivity'
            });
        }
    },
    
    setEnabled(enabled) {
        // Update UI
        const checkbox = document.getElementById('sw-wake-word');
        if (checkbox) {
            checkbox.checked = enabled;
        }
        
        // Send to server
        if (app.aiboxPlus && app.aiboxPlus.isConnected) {
            app.aiboxPlus.send({
                action: 'wake_word_set_enabled',
                enabled: enabled
            });
            Logger.log(`Wake word detection ${enabled ? 'enabled' : 'disabled'}`);
        } else {
            Logger.log('AiboxPlus not connected', 'error');
        }
    },
    
    getEnabled() {
        if (app.aiboxPlus && app.aiboxPlus.isConnected) {
            app.aiboxPlus.send({
                action: 'wake_word_get_enabled'
            });
        }
    }
};

// Initialize UI
setTimeout(() => {
    if(app && app.ui && app.connection) {
        app.ui.setConnectionState(app.connection.isConnected);
    }
}, 100);
