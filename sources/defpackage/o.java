package defpackage;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import info.dourok.voicebot.java.audio.MusicPlayer;
import java.util.List;
import org.java_websocket.WebSocket;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: o  reason: default package */
public final /* synthetic */ class o implements Runnable {
    public final /* synthetic */ int c;
    public final /* synthetic */ x f;
    public final /* synthetic */ WebSocket g;

    public /* synthetic */ o(int i, x xVar, WebSocket webSocket) {
        this.c = i;
        this.f = xVar;
        this.g = webSocket;
    }

    public final void run() {
        a5 a5Var;
        boolean z;
        int i = this.c;
        MusicPlayer musicPlayer = null;
        WebSocket webSocket = this.g;
        x xVar = this.f;
        switch (i) {
            case 0:
                xVar.getClass();
                if (webSocket.isOpen()) {
                    xVar.u(webSocket);
                    return;
                }
                return;
            case 1:
                xVar.getClass();
                try {
                    a5 a5Var2 = xVar.f;
                    if (a5Var2 != null) {
                        musicPlayer = a5Var2.f;
                    }
                    if (musicPlayer != null) {
                        musicPlayer.pause();
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("type", "pause");
                        jSONObject.put("success", true);
                        x.bb(webSocket, jSONObject.toString());
                        return;
                    }
                    x.bd(webSocket, "MusicPlayer not available");
                    return;
                } catch (Exception e) {
                    y2.v(e, new StringBuilder("Failed to pause: "), webSocket);
                    return;
                }
            case 2:
                xVar.getClass();
                try {
                    a5 a5Var3 = xVar.f;
                    if (a5Var3 != null) {
                        musicPlayer = a5Var3.f;
                    }
                    if (musicPlayer != null) {
                        musicPlayer.resume();
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("type", "resume");
                        jSONObject2.put("success", true);
                        x.bb(webSocket, jSONObject2.toString());
                        return;
                    }
                    x.bd(webSocket, "MusicPlayer not available");
                    return;
                } catch (Exception e2) {
                    y2.v(e2, new StringBuilder("Failed to resume: "), webSocket);
                    return;
                }
            case 3:
                xVar.getClass();
                try {
                    WifiManager wifiManager = (WifiManager) xVar.c.getApplicationContext().getSystemService("wifi");
                    if (wifiManager == null) {
                        x.bd(webSocket, "WiFi manager not available");
                        return;
                    }
                    wifiManager.setWifiEnabled(true);
                    Thread.sleep(500);
                    wifiManager.startScan();
                    Thread.sleep(1500);
                    List<ScanResult> scanResults = wifiManager.getScanResults();
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("type", "wifi_scan_result");
                    jSONObject3.put("success", true);
                    JSONArray jSONArray = new JSONArray();
                    if (scanResults != null) {
                        for (ScanResult next : scanResults) {
                            JSONObject jSONObject4 = new JSONObject();
                            String str = next.SSID;
                            if (str == null) {
                                str = "";
                            }
                            jSONObject4.put("ssid", str);
                            String str2 = next.BSSID;
                            if (str2 == null) {
                                str2 = "";
                            }
                            jSONObject4.put("bssid", str2);
                            String str3 = next.capabilities;
                            if (str3 == null) {
                                str3 = "";
                            }
                            jSONObject4.put("capabilities", str3);
                            jSONObject4.put("frequency", next.frequency);
                            jSONObject4.put("level", next.level);
                            String str4 = next.capabilities;
                            if (str4 == null || (!str4.contains("WPA") && !next.capabilities.contains("WEP"))) {
                                z = false;
                            } else {
                                z = true;
                            }
                            jSONObject4.put("is_secure", z);
                            jSONArray.put(jSONObject4);
                        }
                    }
                    jSONObject3.put("networks", jSONArray);
                    jSONObject3.put("count", jSONArray.length());
                    x.bb(webSocket, jSONObject3.toString());
                    jSONArray.length();
                    return;
                } catch (Exception e3) {
                    y2.v(e3, new StringBuilder("Error scanning WiFi: "), webSocket);
                    return;
                }
            default:
                xVar.getClass();
                try {
                    if (xVar.g == null && (a5Var = xVar.f) != null) {
                        xVar.g = a5Var.f;
                    }
                    xVar.f();
                    MusicPlayer musicPlayer2 = xVar.g;
                    if (musicPlayer2 != null) {
                        musicPlayer2.stop();
                        xVar.k = null;
                        xVar.l = null;
                        xVar.m = null;
                        xVar.n = null;
                        xVar.o = null;
                        JSONObject jSONObject5 = new JSONObject();
                        jSONObject5.put("type", "playback_state");
                        jSONObject5.put("is_playing", false);
                        jSONObject5.put("position", 0);
                        jSONObject5.put("duration", 0);
                        jSONObject5.put("title", "");
                        jSONObject5.put("artist", "");
                        jSONObject5.put("thumbnail_url", "");
                        jSONObject5.put("source", "youtube");
                        x.bb(webSocket, jSONObject5.toString());
                        JSONObject jSONObject6 = new JSONObject();
                        jSONObject6.put("type", "stop");
                        jSONObject6.put("success", true);
                        x.bb(webSocket, jSONObject6.toString());
                        return;
                    }
                    x.bd(webSocket, "MusicPlayer not available");
                    return;
                } catch (Exception e4) {
                    y2.v(e4, new StringBuilder("Failed to stop: "), webSocket);
                    return;
                }
        }
    }
}
