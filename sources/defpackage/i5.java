package defpackage;

import android.content.Context;
import android.media.AudioManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import defpackage.a5;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: i5  reason: default package */
public final class i5 implements a5.h {
    public final /* synthetic */ a5 a;

    public i5(a5 a5Var) {
        this.a = a5Var;
    }

    public final Object a(JSONObject jSONObject) {
        WifiInfo connectionInfo;
        int i;
        a5 a5Var = this.a;
        a5Var.getClass();
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("audio", "available");
            jSONObject2.put("network", "connected");
            jSONObject2.put("battery", "full");
            jSONObject2.put("device", a5Var.b);
            jSONObject2.put("version", "4.0.0");
            Context context = a5Var.a;
            if (context != null) {
                AudioManager audioManager = (AudioManager) context.getSystemService("audio");
                if (audioManager != null) {
                    int streamMaxVolume = audioManager.getStreamMaxVolume(3);
                    int streamVolume = audioManager.getStreamVolume(3);
                    if (streamMaxVolume > 0) {
                        i = (streamVolume * 100) / streamMaxVolume;
                    } else {
                        i = 0;
                    }
                    jSONObject2.put("volume", i);
                }
                try {
                    WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
                    if (!(wifiManager == null || (connectionInfo = wifiManager.getConnectionInfo()) == null)) {
                        int ipAddress = connectionInfo.getIpAddress();
                        if (ipAddress != 0) {
                            jSONObject2.put("ip", String.format("%d.%d.%d.%d", new Object[]{Integer.valueOf(ipAddress & 255), Integer.valueOf((ipAddress >> 8) & 255), Integer.valueOf((ipAddress >> 16) & 255), Integer.valueOf((ipAddress >> 24) & 255)}));
                        } else {
                            jSONObject2.put("ip", "no_ip");
                        }
                    }
                } catch (Exception unused) {
                    jSONObject2.put("ip", MqttServiceConstants.TRACE_ERROR);
                }
            }
            return jSONObject2;
        } catch (JSONException e) {
            throw new RuntimeException("Failed to create device status", e);
        }
    }
}
