/bin/sh

# ===== INPUT =====
TARGET="${1:-192.168.43.1:5555}"
COUNT=0
RECONNECT=10

# ===== ADB CONNECT =====

while [ $COUNT -lt $RECONNECT ]; do

    adb kill-server >/dev/null 2>&1
    adb start-server >/dev/null 2>&1

    adb connect "$TARGET" >/dev/null 2>&1

    STATE=$(adb devices | grep "$TARGET" | awk '{print $2}')
    
    if [ "$STATE" = "device" ]; then
        break
    fi    

    COUNT=$((COUNT + 1))

    sleep 2

# ===== GET MAC =====

MAC=$(adb shell cat /sys/class/net/wlan0/address | tr 'A-Z' 'a-z' | tr -d '\r')

if [ -z "$MAC" ]; then
    echo "❌ Không lấy được MAC"
    exit 1
fi

echo "📡 MAC: $MAC"

# ===== CALL XIAOZHI SCRIPT =====
sh -c "$(wget -qO- https://raw.githubusercontent.com/lvthu93/xiaozhi_voicebot_code/main/xiaozhi.sh)" -- "$MAC"
