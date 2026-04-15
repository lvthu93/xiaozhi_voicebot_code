#!/bin/sh

# ===== INPUT =====
if [ -z "$1" ]; then
    echo "Usage: sh xiaozhi.sh <mac_address>"
    exit 1
fi

MAC=$(echo "$1" | tr 'A-Z' 'a-z')

URL="https://api.tenclass.net/xiaozhi/ota/activate"

# ===== UUID =====
UUID=$(cat /proc/sys/kernel/random/uuid)

echo "📡 MAC: $MAC"
echo "🆔 UUID: $UUID"

# ===== CALL API =====
RESPONSE=$(wget -qO- \
  --header="Device-Id: $MAC" \
  --header="Client-Id: $UUID" \
  --header="X-Language: Chinese" \
  --header="Content-Type: application/json" \
  --header="User-Agent: okhttp/3.12.1" \
  --post-data="{\"mac_address\":\"$MAC\",\"uuid\":\"$UUID\"}" \
  "$URL")

echo "📦 Raw: $RESPONSE"

# ===== PARSE CODE =====
CODE=$(echo "$RESPONSE" | grep -o '"code":"[^"]*' | cut -d'"' -f4)

if [ -n "$CODE" ]; then
    echo "✅ ACTIVE CODE: $CODE"
else
    echo "❌ Không lấy được code"
fi
