#!/bin/sh

MAC=$(echo "$1" | tr 'A-Z' 'a-z')
[ -z "$MAC" ] && echo "Usage: $0 <mac>" && exit 1

UUID=$(uuidgen)

RESPONSE=$(wget -qO- \
  --header="Device-Id: $MAC" \
  --header="Client-Id: $UUID" \
  --header="Content-Type: application/json" \
  --post-data="{\"mac_address\":\"$MAC\",\"uuid\":\"$UUID\"}" \
  https://api.tenclass.net/xiaozhi/ota/activate)

echo "📦 Raw: $RESPONSE"

DEVICE_ID=$(echo "$RESPONSE" | grep -o '"device_id":[0-9]*' | cut -d':' -f2)

if [ -n "$DEVICE_ID" ]; then
    echo "✅ DEVICE ID: $DEVICE_ID"
else
    echo "❌ Không có device_id (có thể API đã thay đổi)"
fi
