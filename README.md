HƯỚNG DẪN SỬ DỤNG
----------------------------------------------------------------------------------------------------------------------------------
# Nếu có Mac Address thì có thể lấy CODE không cần Loa:

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
- Nếu không có các môi trường kia thì có thể tải file "xiaozhi.ps1" lưu vào ổ C:/ và mở powershell ở chế độ Admin để lấy CODE

    cd\ ; powershell -ExecutionPolicy Bypass -File .\xiaozhi.ps1 98:bb:99:3f:XX:XX
  
----------------------------------------------------------------------------------------------------------------------------------
# BONUS

#Get Mac Address

"adb shell cat /sys/class/net/wlan0/address"

#Get Android ID

adb shell settings get secure android_id
