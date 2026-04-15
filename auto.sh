#!/bin/sh

# ===== INPUT =====
TARGET="${1:-192.168.43.1:5555}"

# ===== ADB CONNECT and GET MAC =====
adb connect "$TARGET" && MAC=$(adb -s shell cat /sys/class/net/wlan0/address | tr '[:upper:]' '[:lower:]' | tr -d '[:space:]') && echo "MAC: $MAC"

if [ -z "$MAC" ]; then
    echo "❌ Không lấy được MAC"
    exit 1
fi

echo "📡 MAC: $MAC"

# ===== CALL XIAOZHI SCRIPT =====
sh -c "$(wget -qO- https://raw.githubusercontent.com/lvthu93/xiaozhi_voicebot_code/main/xiaozhi.sh)" -- "$MAC"
