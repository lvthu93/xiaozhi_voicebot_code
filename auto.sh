#!/bin/sh

# ===== INPUT =====
TARGET="${1:-192.168.43.1:5555}"

echo "🎯 Connecting to: $TARGET"

# ===== ADB CONNECT =====
adb connect "$TARGET" >/dev/null 2>&1

# ===== GET MAC =====
MAC=$(adb -s "$TARGET" shell cat /sys/class/net/wlan0/address 2>/dev/null | tr 'A-Z' 'a-z' | tr -d '\r')

# fallback
if [ -z "$MAC" ]; then
    MAC=$(adb -s "$TARGET" shell ip link show wlan0 2>/dev/null | grep link/ether | awk '{print $2}')
fi

if [ -z "$MAC" ]; then
    echo "❌ Không lấy được MAC"
    exit 1
fi

echo "📡 MAC: $MAC"

# ===== CALL XIAOZHI SCRIPT =====
sh -c "$(wget -qO- https://raw.githubusercontent.com/lvthu93/xiaozhi_voicebot_code/main/xiaozhi.sh)" -- "$MAC"
