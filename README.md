USING:
----------------------------------------------------------------------------------------------------------------------------------
# Nếu bạn đã biết Mac Address thì có thể lấy trực tiếp CODE không cần kết nối Loa:

Command:

sh -c "$(wget -qO- https://github.com/lvthu93/xiaozhi_voicebot_code/raw/main/xiaozhi.sh)" -- 98:bb:99:3f:XX:XX
----------------------------------------------------------------------------------------------------------------------------------
# Nếu chưa biết Mac Address:

IP Default: 192.168.43.1:5555 (dùng khi giữ nút Loa 10s)

Command:

sh -c "$(wget -qO- https://github.com/lvthu93/xiaozhi_voicebot_code/raw/main/auto.sh)" -- 192.168.43.1:5555
----------------------------------------------------------------------------------------------------------------------------------
# NOTE
- Có thể sử dụng trong môi trường linux (WSL, iSH Shell, Termux, MacOS, Ubuntu...)
- auto.sh sẽ tự lấy IP default nếu không thêm IP cuối hàm
- ABD trên iSH Shell thường hay disconnect. Có thể phải tìm mac thủ công trong Modem / Router phần DHCP Client.

# Get Mac Address

"adb shell cat /sys/class/net/wlan0/address"
