#!/bin/sh

# ===== INPUT =====
TARGET="${1:-192.168.43.1:5555}"

# ===== ADB CONNECT =====

adb connect "$TARGET"

# ===== GET MAC =====

MAC=$(adb shell cat /sys/class/net/wlan0/address | tr 'A-Z' 'a-z' | tr -d '\r')

if [ -z "$MAC" ]; then
    echo "❌ Không lấy được MAC"
    exit 1
fi

echo "📡 MAC: $MAC"

# ===== CALL XIAOZHI SCRIPT =====
sh -c "$(wget -qO- https://raw.githubusercontent.com/lvthu93/xiaozhi_voicebot_code/main/xiaozhi.sh)" -- "$MAC"
