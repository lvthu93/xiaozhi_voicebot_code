package defpackage;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import androidx.core.app.NotificationCompat;
import defpackage.lf;
import info.dourok.voicebot.java.VoiceBotApplication;
import info.dourok.voicebot.java.activities.ChatActivity;
import info.dourok.voicebot.java.audio.MusicPlayer;
import info.dourok.voicebot.java.receivers.AlarmReceiver;
import info.dourok.voicebot.java.services.AlarmService;
import info.dourok.voicebot.java.services.WifiSetupService;
import info.dourok.voicebot.java.services.ZaloMqttClient;
import info.dourok.voicebot.java.services.ZingMp3Service;
import j$.util.Objects;
import j$.util.concurrent.ConcurrentHashMap;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.java_websocket.WebSocket;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mozilla.javascript.ES6Iterator;

/* renamed from: x  reason: default package */
public final class x extends WebSocketServer {
    public static final /* synthetic */ int aa = 0;
    public final Context c;
    public a5 f;
    public MusicPlayer g;
    public ArrayList h = new ArrayList();
    public List<ZingMp3Service.ZingMp3Song> i = new ArrayList();
    public final ArrayList j = new ArrayList();
    public lf.c k = null;
    public String l = null;
    public String m = null;
    public String n = null;
    public String o = null;
    public boolean p = true;
    public Integer q = null;
    public String r = null;
    public String s = null;
    public final Set<WebSocket> t = Collections.newSetFromMap(new ConcurrentHashMap());
    public final Handler u = new Handler(Looper.getMainLooper());
    public b v;
    public z w;
    public Runnable x;
    public String y;
    public final a z = new a();

    /* renamed from: x$a */
    public class a implements MusicPlayer.k {
        public a() {
        }
    }

    /* renamed from: x$b */
    public class b implements Runnable {
        public b() {
        }

        public final void run() {
            x xVar = x.this;
            if (!xVar.t.isEmpty()) {
                xVar.e();
            }
            xVar.u.postDelayed(this, 1000);
        }
    }

    /* renamed from: x$c */
    public static class c {
        public final String a;
        public final String b;
        public final long c = System.currentTimeMillis();

        public c(String str, String str2) {
            this.a = str;
            this.b = str2;
        }
    }

    public x(Context context) {
        super(new InetSocketAddress(8082));
        this.c = context;
    }

    public static void bb(WebSocket webSocket, String str) {
        if (webSocket != null && webSocket.isOpen()) {
            try {
                webSocket.send(str);
            } catch (Exception | WebsocketNotConnectedException unused) {
            }
        }
    }

    public static void bd(WebSocket webSocket, String str) {
        if (webSocket != null && webSocket.isOpen()) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("type", MqttServiceConstants.TRACE_ERROR);
                jSONObject.put("success", false);
                jSONObject.put("message", str);
                bb(webSocket, jSONObject.toString());
            } catch (JSONException unused) {
            }
        }
    }

    public static void w(WebSocket webSocket) {
        try {
            boolean z2 = f4.e;
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", "led_state");
            jSONObject.put("success", true);
            jSONObject.put("enabled", z2);
            bb(webSocket, jSONObject.toString());
        } catch (Exception e) {
            y2.v(e, new StringBuilder("Error getting LED state: "), webSocket);
        }
    }

    public final void a(String str, String str2) {
        synchronized (this.j) {
            this.j.add(new c(str, str2));
            if (this.j.size() > 100) {
                this.j.remove(0);
            }
        }
    }

    public final void aa(WebSocket webSocket) {
        try {
            n4 a2 = n4.a(this.c);
            if (a2 == null) {
                bd(webSocket, "Failed to initialize MacAddressManager");
                return;
            }
            String c2 = a2.c();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", "mac_random");
            if (c2 != null) {
                ax();
                jSONObject.put("success", true);
                jSONObject.put("mac_address", c2);
                jSONObject.put("message", "MAC address randomized and saved");
            } else {
                jSONObject.put("success", false);
                jSONObject.put(MqttServiceConstants.TRACE_ERROR, "Failed to generate or save MAC address");
            }
            bb(webSocket, jSONObject.toString());
        } catch (Exception e) {
            y2.v(e, new StringBuilder("Error randomizing MAC: "), webSocket);
        }
    }

    public final void ab(WebSocket webSocket) {
        String str = this.o;
        int i2 = 0;
        if (str == null || str.isEmpty() || this.i.isEmpty()) {
            ArrayList arrayList = this.h;
            if (arrayList == null || arrayList.isEmpty()) {
                bd(webSocket, "Không có danh sách bài hát");
            } else if (this.k == null) {
                av(webSocket, (lf.c) this.h.get(0));
            } else {
                int i3 = 0;
                while (true) {
                    if (i3 >= this.h.size()) {
                        i3 = -1;
                        break;
                    } else if (((lf.c) this.h.get(i3)).a.equals(this.k.a)) {
                        break;
                    } else {
                        i3++;
                    }
                }
                if (i3 == -1) {
                    av(webSocket, (lf.c) this.h.get(0));
                    return;
                }
                int i4 = i3 + 1;
                if (i4 < this.h.size()) {
                    i2 = i4;
                }
                lf.c cVar = (lf.c) this.h.get(i2);
                av(webSocket, cVar);
                String str2 = cVar.b;
            }
        } else {
            int i5 = 0;
            while (true) {
                if (i5 >= this.i.size()) {
                    i5 = -1;
                    break;
                } else if (this.i.get(i5).id.equals(this.o)) {
                    break;
                } else {
                    i5++;
                }
            }
            if (i5 == -1) {
                i5 = 0;
            }
            int i6 = i5 + 1;
            if (i6 < this.i.size()) {
                i2 = i6;
            }
            aw(webSocket, this.i.get(i2).id);
        }
    }

    public final void ac(WebSocket webSocket, JSONObject jSONObject) {
        String trim = jSONObject.optString("video_id", "").trim();
        if (trim.isEmpty()) {
            bd(webSocket, "video_id is required");
            return;
        }
        a5 a5Var = this.f;
        if (a5Var != null) {
            this.g = a5Var.f;
        }
        f();
        if (this.g == null) {
            ay(webSocket, jSONObject, 0);
        } else {
            new Thread(new d6(7, this, trim, webSocket)).start();
        }
    }

    public final void ad(WebSocket webSocket, JSONObject jSONObject) {
        Context context;
        String trim = jSONObject.optString("song_id", "").trim();
        String trim2 = jSONObject.optString("song_name", "").trim();
        String trim3 = jSONObject.optString("artist_name", "").trim();
        if (!trim.isEmpty()) {
            aw(webSocket, trim);
        } else if (trim2.isEmpty()) {
            bd(webSocket, "song_name or song_id is required");
        } else {
            a5 a5Var = this.f;
            if (a5Var != null) {
                this.g = a5Var.f;
            }
            if (!(this.g != null || (context = this.c) == null || a5Var == null)) {
                MusicPlayer musicPlayer = new MusicPlayer(context);
                this.g = musicPlayer;
                MusicPlayer.m mVar = this.f.i;
                if (mVar != null) {
                    musicPlayer.setStateController(mVar);
                }
                this.f.h(this.g);
            }
            MusicPlayer musicPlayer2 = this.g;
            if (musicPlayer2 == null) {
                az(webSocket, jSONObject, 0);
            } else {
                new Thread(new s(this, trim2, trim3, webSocket, musicPlayer2, 0)).start();
            }
        }
    }

    public final void ae(WebSocket webSocket) {
        String str = this.o;
        int i2 = 0;
        if (str == null || str.isEmpty() || this.i.isEmpty()) {
            ArrayList arrayList = this.h;
            if (arrayList == null || arrayList.isEmpty()) {
                bd(webSocket, "Không có danh sách bài hát");
            } else if (this.k == null) {
                ArrayList arrayList2 = this.h;
                av(webSocket, (lf.c) arrayList2.get(arrayList2.size() - 1));
            } else {
                while (true) {
                    if (i2 >= this.h.size()) {
                        i2 = -1;
                        break;
                    } else if (((lf.c) this.h.get(i2)).a.equals(this.k.a)) {
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i2 == -1) {
                    ArrayList arrayList3 = this.h;
                    av(webSocket, (lf.c) arrayList3.get(arrayList3.size() - 1));
                    return;
                }
                int i3 = i2 - 1;
                if (i3 < 0) {
                    i3 = this.h.size() - 1;
                }
                lf.c cVar = (lf.c) this.h.get(i3);
                av(webSocket, cVar);
                String str2 = cVar.b;
            }
        } else {
            while (true) {
                if (i2 >= this.i.size()) {
                    i2 = -1;
                    break;
                } else if (this.i.get(i2).id.equals(this.o)) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i2 == -1) {
                i2 = this.i.size() - 1;
            }
            int i4 = i2 - 1;
            if (i4 < 0) {
                i4 = this.i.size() - 1;
            }
            aw(webSocket, this.i.get(i4).id);
        }
    }

    public final void af(WebSocket webSocket, JSONObject jSONObject) {
        String trim = jSONObject.optString("query", "").trim();
        boolean optBoolean = jSONObject.optBoolean("autoplay", false);
        if (trim.isEmpty()) {
            bd(webSocket, "Query is required");
            return;
        }
        this.i.clear();
        this.o = null;
        this.l = null;
        this.m = null;
        this.n = null;
        new Thread(new q(this, trim, webSocket, optBoolean, 0)).start();
    }

    public final void ag(WebSocket webSocket, JSONObject jSONObject) {
        String trim = jSONObject.optString("query", "").trim();
        boolean optBoolean = jSONObject.optBoolean("autoplay", false);
        if (trim.isEmpty()) {
            bd(webSocket, "Query is required");
            return;
        }
        this.i.clear();
        this.o = null;
        this.l = null;
        this.m = null;
        this.n = null;
        new Thread(new q(this, trim, webSocket, optBoolean, 1)).start();
    }

    public final void ah(WebSocket webSocket, JSONObject jSONObject) {
        String trim = jSONObject.optString("query", "").trim();
        boolean optBoolean = jSONObject.optBoolean("autoplay", false);
        if (trim.isEmpty()) {
            bd(webSocket, "Query is required");
            return;
        }
        this.h.clear();
        this.k = null;
        new Thread(new q(this, trim, webSocket, optBoolean, 2)).start();
    }

    public final void ai(WebSocket webSocket, JSONObject jSONObject) {
        int optInt = jSONObject.optInt("position", -1);
        if (optInt < 0) {
            bd(webSocket, "position is required and must be >= 0");
        } else {
            new Handler(Looper.getMainLooper()).post(new v1(optInt, this, webSocket, 2));
        }
    }

    public final void aj(WebSocket webSocket, JSONObject jSONObject) {
        String str;
        String str2;
        try {
            boolean optBoolean = jSONObject.optBoolean("enabled", false);
            Context context = this.c;
            if (context != null) {
                if (context.getApplicationContext() instanceof VoiceBotApplication) {
                    boolean tiktokReplyEnabled = ((VoiceBotApplication) context.getApplicationContext()).setTiktokReplyEnabled(optBoolean);
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("type", "tiktok_reply_toggle_result");
                    jSONObject2.put("success", tiktokReplyEnabled);
                    jSONObject2.put("enabled", optBoolean);
                    if (tiktokReplyEnabled) {
                        if (optBoolean) {
                            str2 = "enabled";
                        } else {
                            str2 = "disabled";
                        }
                        str = "TikTok reply ".concat(str2);
                    } else {
                        str = "Failed to toggle TikTok reply";
                    }
                    jSONObject2.put("message", str);
                    bb(webSocket, jSONObject2.toString());
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("type", "tiktok_reply_state");
                    jSONObject3.put("enabled", optBoolean);
                    d(jSONObject3.toString());
                    return;
                }
            }
            bd(webSocket, "VoiceBotApplication not available");
        } catch (Exception e) {
            y2.v(e, new StringBuilder("Error: "), webSocket);
        }
    }

    public final void ak(WebSocket webSocket, JSONObject jSONObject) {
        this.p = jSONObject.optBoolean("enabled", !this.p);
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("type", "auto_next_toggled");
            jSONObject2.put("enabled", this.p);
            bb(webSocket, jSONObject2.toString());
            e();
        } catch (JSONException unused) {
        }
    }

    public final void al(WebSocket webSocket) {
        boolean z2;
        try {
            Context context = this.c;
            if (context != null) {
                z2 = context.getSharedPreferences("voicebot_prefs", 0).getBoolean("wake_word_enabled", true);
            } else {
                z2 = true;
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", "wake_word_get_enabled_result");
            jSONObject.put("success", true);
            jSONObject.put("enabled", z2);
            bb(webSocket, jSONObject.toString());
        } catch (Exception e) {
            y2.v(e, new StringBuilder("Error: "), webSocket);
        }
    }

    public final void am(WebSocket webSocket) {
        try {
            Context context = this.c;
            float f2 = 0.9f;
            if (context != null) {
                f2 = context.getSharedPreferences("voicebot_prefs", 0).getFloat("wake_word_sensitivity", 0.9f);
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", "wake_word_get_sensitivity_result");
            jSONObject.put("success", true);
            jSONObject.put("sensitivity", (double) f2);
            bb(webSocket, jSONObject.toString());
        } catch (Exception e) {
            y2.v(e, new StringBuilder("Error: "), webSocket);
        }
    }

    public final void an(WebSocket webSocket, JSONObject jSONObject) {
        String str;
        try {
            boolean optBoolean = jSONObject.optBoolean("enabled", true);
            if (!ChatActivity.setWakeWordEnabled(optBoolean)) {
                bd(webSocket, "Wake word detector not available");
                return;
            }
            Context context = this.c;
            if (context != null) {
                context.getSharedPreferences("voicebot_prefs", 0).edit().putBoolean("wake_word_enabled", optBoolean).apply();
            }
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("type", "wake_word_set_enabled_result");
            jSONObject2.put("success", true);
            jSONObject2.put("enabled", optBoolean);
            if (optBoolean) {
                str = "enabled";
            } else {
                str = "disabled";
            }
            jSONObject2.put("message", "Wake word ".concat(str));
            bb(webSocket, jSONObject2.toString());
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("type", "wake_word_enabled_state");
            jSONObject3.put("enabled", optBoolean);
            d(jSONObject3.toString());
        } catch (Exception e) {
            y2.v(e, new StringBuilder("Error: "), webSocket);
        }
    }

    public final void ao(WebSocket webSocket, JSONObject jSONObject) {
        try {
            float optDouble = (float) jSONObject.optDouble("sensitivity", 0.9d);
            if (optDouble >= 0.0f) {
                if (optDouble <= 1.0f) {
                    info.dourok.voicebot.java.utils.a currentWakeWordDetector = ChatActivity.getCurrentWakeWordDetector();
                    if (currentWakeWordDetector == null) {
                        bd(webSocket, "Wake word detector not available");
                        return;
                    }
                    currentWakeWordDetector.setSensitivity(optDouble);
                    Context context = this.c;
                    if (context != null) {
                        context.getSharedPreferences("voicebot_prefs", 0).edit().putFloat("wake_word_sensitivity", optDouble).apply();
                    }
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("type", "wake_word_set_sensitivity_result");
                    jSONObject2.put("success", true);
                    double d = (double) optDouble;
                    jSONObject2.put("sensitivity", d);
                    jSONObject2.put("message", "Wake word sensitivity set to " + optDouble);
                    bb(webSocket, jSONObject2.toString());
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("type", "wake_word_sensitivity_state");
                    jSONObject3.put("sensitivity", d);
                    d(jSONObject3.toString());
                    return;
                }
            }
            bd(webSocket, "Sensitivity must be between 0.0 and 1.0");
        } catch (Exception e) {
            y2.v(e, new StringBuilder("Error: "), webSocket);
        }
    }

    public final void ap(WebSocket webSocket, JSONObject jSONObject) {
        String trim = jSONObject.optString("ssid", "").trim();
        String trim2 = jSONObject.optString("password", "").trim();
        int optInt = jSONObject.optInt("network_id", -1);
        if (!trim.isEmpty() || optInt >= 0) {
            try {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("type", "wifi_connect_result");
                jSONObject2.put("success", true);
                jSONObject2.put("message", "WiFi connection request sent");
                bb(webSocket, jSONObject2.toString());
                Thread.sleep(500);
            } catch (Exception unused) {
            }
            new Thread(new r(this, optInt, trim, trim2, webSocket)).start();
            return;
        }
        bd(webSocket, "SSID or network_id is required");
    }

    public final void aq(WebSocket webSocket, JSONObject jSONObject) {
        boolean z2;
        String str;
        String trim = jSONObject.optString("ssid", "").trim();
        int optInt = jSONObject.optInt("network_id", -1);
        if (!trim.isEmpty() || optInt >= 0) {
            try {
                WifiManager wifiManager = (WifiManager) this.c.getApplicationContext().getSystemService("wifi");
                if (wifiManager == null) {
                    bd(webSocket, "WiFi manager not available");
                    return;
                }
                if (optInt >= 0) {
                    z2 = wifiManager.removeNetwork(optInt);
                } else {
                    List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
                    if (configuredNetworks != null) {
                        Iterator<WifiConfiguration> it = configuredNetworks.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            WifiConfiguration next = it.next();
                            String str2 = next.SSID;
                            if (str2 != null) {
                                if (str2.startsWith("\"") && str2.endsWith("\"")) {
                                    str2 = str2.substring(1, str2.length() - 1);
                                }
                                if (str2.equals(trim)) {
                                    z2 = wifiManager.removeNetwork(next.networkId);
                                    break;
                                }
                            }
                        }
                    }
                    z2 = false;
                }
                if (z2) {
                    wifiManager.saveConfiguration();
                }
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("type", "wifi_delete_result");
                jSONObject2.put("success", z2);
                if (z2) {
                    str = "WiFi network removed";
                } else {
                    str = "Failed to remove WiFi network";
                }
                jSONObject2.put("message", str);
                bb(webSocket, jSONObject2.toString());
            } catch (Exception e) {
                y2.v(e, new StringBuilder("Error deleting saved WiFi: "), webSocket);
            }
        } else {
            bd(webSocket, "SSID or network_id is required");
        }
    }

    public final void ar(WebSocket webSocket) {
        try {
            WifiManager wifiManager = (WifiManager) this.c.getApplicationContext().getSystemService("wifi");
            if (wifiManager == null) {
                bd(webSocket, "WiFi manager not available");
                return;
            }
            List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", "wifi_saved_result");
            jSONObject.put("success", true);
            JSONArray jSONArray = new JSONArray();
            if (configuredNetworks != null) {
                for (WifiConfiguration next : configuredNetworks) {
                    JSONObject jSONObject2 = new JSONObject();
                    String str = next.SSID;
                    if (str != null && str.startsWith("\"") && str.endsWith("\"")) {
                        str = str.substring(1, str.length() - 1);
                    }
                    if (str == null) {
                        str = "";
                    }
                    jSONObject2.put("ssid", str);
                    jSONObject2.put("network_id", next.networkId);
                    jSONObject2.put(NotificationCompat.CATEGORY_STATUS, next.status);
                    jSONArray.put(jSONObject2);
                }
            }
            jSONObject.put("networks", jSONArray);
            jSONObject.put("count", jSONArray.length());
            bb(webSocket, jSONObject.toString());
        } catch (Exception e) {
            y2.v(e, new StringBuilder("Error getting saved WiFi: "), webSocket);
        }
    }

    public final void as(WebSocket webSocket) {
        boolean z2;
        Context context = this.c;
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", "wifi_status");
            jSONObject.put("success", true);
            if (wifiManager != null) {
                WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                if (connectionInfo != null) {
                    String ssid = connectionInfo.getSSID();
                    if (ssid != null && ssid.startsWith("\"") && ssid.endsWith("\"")) {
                        ssid = ssid.substring(1, ssid.length() - 1);
                    }
                    if (ssid == null) {
                        ssid = "";
                    }
                    jSONObject.put("current_ssid", ssid);
                    if (connectionInfo.getNetworkId() != -1) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    jSONObject.put("is_connected", z2);
                    int ipAddress = connectionInfo.getIpAddress();
                    if (ipAddress != 0) {
                        jSONObject.put("ip_address", String.format("%d.%d.%d.%d", new Object[]{Integer.valueOf(ipAddress & 255), Integer.valueOf((ipAddress >> 8) & 255), Integer.valueOf((ipAddress >> 16) & 255), Integer.valueOf((ipAddress >> 24) & 255)}));
                    } else {
                        jSONObject.put("ip_address", "");
                    }
                } else {
                    jSONObject.put("current_ssid", "");
                    jSONObject.put("is_connected", false);
                    jSONObject.put("ip_address", "");
                }
                jSONObject.put("wifi_enabled", wifiManager.isWifiEnabled());
            }
            ef efVar = new ef(context);
            boolean e = efVar.e();
            jSONObject.put("ap_mode_active", e);
            if (e) {
                jSONObject.put("ap_ssid", efVar.d());
                jSONObject.put("ap_ip", "192.168.43.1");
            }
            bb(webSocket, jSONObject.toString());
        } catch (Exception e2) {
            y2.v(e2, new StringBuilder("Error getting WiFi status: "), webSocket);
        }
    }

    public final void at(WebSocket webSocket) {
        Context context = this.c;
        try {
            ef efVar = new ef(context);
            if (efVar.e()) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("type", "wifi_ap_result");
                jSONObject.put("success", true);
                jSONObject.put("message", "AP mode already active");
                jSONObject.put("ap_ssid", efVar.d());
                jSONObject.put("ap_ip", "192.168.43.1");
                bb(webSocket, jSONObject.toString());
                return;
            }
            Intent intent = new Intent(context, WifiSetupService.class);
            intent.setAction(WifiSetupService.ACTION_START_AP);
            context.startService(intent);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("type", "wifi_ap_result");
            jSONObject2.put("success", true);
            jSONObject2.put("message", "Starting AP mode...");
            bb(webSocket, jSONObject2.toString());
        } catch (JSONException e) {
            bd(webSocket, "Error: " + e.getMessage());
        }
    }

    public final void au(WebSocket webSocket) {
        Context context = this.c;
        try {
            Intent intent = new Intent(context, WifiSetupService.class);
            intent.setAction(WifiSetupService.ACTION_STOP_AP);
            context.startService(intent);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", "wifi_ap_result");
            jSONObject.put("success", true);
            jSONObject.put("message", "Stopping AP mode...");
            bb(webSocket, jSONObject.toString());
        } catch (JSONException e) {
            bd(webSocket, "Error: " + e.getMessage());
        }
    }

    public final void av(WebSocket webSocket, lf.c cVar) {
        String str;
        if (cVar == null || (str = cVar.a) == null || str.isEmpty()) {
            bd(webSocket, "Invalid song info");
            return;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("video_id", str);
            ac(webSocket, jSONObject);
        } catch (JSONException e) {
            bd(webSocket, "Error creating play request: " + e.getMessage());
        }
    }

    public final void aw(WebSocket webSocket, String str) {
        ZingMp3Service.ZingMp3Song zingMp3Song;
        String str2;
        String str3;
        String str4;
        Context context;
        Iterator<ZingMp3Service.ZingMp3Song> it = this.i.iterator();
        while (true) {
            if (!it.hasNext()) {
                zingMp3Song = null;
                break;
            }
            zingMp3Song = it.next();
            if (zingMp3Song.id.equals(str)) {
                break;
            }
        }
        if (zingMp3Song == null) {
            bd(webSocket, "Song not found in search results");
            return;
        }
        a5 a5Var = this.f;
        if (a5Var != null) {
            this.g = a5Var.f;
        }
        if (!(this.g != null || (context = this.c) == null || a5Var == null)) {
            MusicPlayer musicPlayer = new MusicPlayer(context);
            this.g = musicPlayer;
            MusicPlayer.m mVar = this.f.i;
            if (mVar != null) {
                musicPlayer.setStateController(mVar);
            }
            this.f.h(this.g);
        }
        f();
        if (this.g == null) {
            ba(webSocket, 0, str);
            return;
        }
        String str5 = zingMp3Song.title;
        if (str5 == null || str5.isEmpty()) {
            str2 = "";
        } else {
            str2 = zingMp3Song.title;
        }
        String str6 = zingMp3Song.artistName;
        if (str6 == null || str6.isEmpty()) {
            str3 = "";
        } else {
            str3 = zingMp3Song.artistName;
        }
        String str7 = zingMp3Song.thumb;
        if (str7 != null) {
            str4 = str7;
        } else {
            str4 = "";
        }
        new Thread(new v(this, str, webSocket, str2, str3, str4)).start();
    }

    public final void ax() {
        c2 c2;
        Context context = this.c;
        if (context != null) {
            try {
                if (context.getApplicationContext() instanceof VoiceBotApplication) {
                    VoiceBotApplication voiceBotApplication = (VoiceBotApplication) context.getApplicationContext();
                    h1 dependencies = voiceBotApplication.getDependencies();
                    if (!(dependencies == null || (c2 = dependencies.c()) == null)) {
                        c2.b = c2.a();
                    }
                    ZaloMqttClient zaloMqttClient = voiceBotApplication.getZaloMqttClient();
                    if (zaloMqttClient != null && zaloMqttClient.isConnected()) {
                        zaloMqttClient.reconnectWithNewMac();
                    }
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    public final void ay(WebSocket webSocket, JSONObject jSONObject, int i2) {
        if (i2 < 3) {
            new Handler(Looper.getMainLooper()).postDelayed(new u(this, webSocket, jSONObject, i2, 0), ((long) Math.pow(2.0d, (double) i2)) * 500);
        } else if (this.c == null || this.f == null) {
            bd(webSocket, "MusicPlayer not available. MCP initialization may be incomplete.");
        } else {
            new Handler(Looper.getMainLooper()).post(new d6(8, this, webSocket, jSONObject));
        }
    }

    public final void az(WebSocket webSocket, JSONObject jSONObject, int i2) {
        if (i2 >= 5) {
            bd(webSocket, "MusicPlayer not available after retries");
            return;
        }
        new Handler(Looper.getMainLooper()).postDelayed(new u(this, webSocket, jSONObject, i2, 1), ((long) Math.pow(2.0d, (double) i2)) * 100);
    }

    public final void b() {
        Set<WebSocket> set = this.t;
        try {
            this.q = null;
            this.r = null;
            this.s = null;
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", "alarm_stopped");
            jSONObject.put("success", true);
            jSONObject.put("message", "Báo thức đã được dừng");
            String jSONObject2 = jSONObject.toString();
            for (WebSocket next : set) {
                if (next != null && next.isOpen()) {
                    bb(next, jSONObject2);
                }
            }
            set.size();
        } catch (JSONException unused) {
        }
    }

    public final void ba(WebSocket webSocket, int i2, String str) {
        if (i2 >= 5) {
            bd(webSocket, "MusicPlayer not available after retries");
            return;
        }
        new Handler(Looper.getMainLooper()).postDelayed(new u(this, webSocket, str, i2, 2), ((long) Math.pow(2.0d, (double) i2)) * 100);
    }

    public final void bc(WebSocket webSocket) {
        try {
            synchronized (this.j) {
                Iterator it = this.j.iterator();
                while (it.hasNext()) {
                    c cVar = (c) it.next();
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("type", "chat_message");
                    jSONObject.put("message_type", cVar.b);
                    jSONObject.put("content", cVar.a);
                    jSONObject.put("timestamp", cVar.c);
                    bb(webSocket, jSONObject.toString());
                }
            }
        } catch (Exception unused) {
        }
    }

    public final void be(WebSocket webSocket) {
        long j2;
        long j3;
        boolean z2;
        a5 a5Var;
        try {
            if (this.g == null && (a5Var = this.f) != null) {
                this.g = a5Var.f;
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", "playback_state");
            MusicPlayer musicPlayer = this.g;
            if (musicPlayer != null) {
                z2 = musicPlayer.isCurrentlyPlaying();
                j3 = this.g.getCurrentPosition() / 1000;
                j2 = this.g.getDuration() / 1000;
            } else {
                j3 = 0;
                z2 = false;
                j2 = 0;
            }
            jSONObject.put("is_playing", z2);
            jSONObject.put("position", j3);
            jSONObject.put("duration", j2);
            jSONObject.put("auto_next_enabled", this.p);
            lf.c cVar = this.k;
            String str = "";
            if (cVar != null) {
                String str2 = cVar.b;
                if (str2 == null) {
                    str2 = str;
                }
                jSONObject.put("title", str2);
                String str3 = this.k.c;
                if (str3 == null) {
                    str3 = str;
                }
                jSONObject.put("artist", str3);
                String str4 = this.k.e;
                if (str4 != null) {
                    str = str4;
                }
                jSONObject.put("thumbnail_url", str);
                jSONObject.put("source", "youtube");
            } else {
                String str5 = this.l;
                if (str5 != null) {
                    jSONObject.put("title", str5);
                    String str6 = this.m;
                    if (str6 == null) {
                        str6 = str;
                    }
                    jSONObject.put("artist", str6);
                    String str7 = this.n;
                    if (str7 != null) {
                        str = str7;
                    }
                    jSONObject.put("thumbnail_url", str);
                    jSONObject.put("source", "zing");
                } else {
                    jSONObject.put("title", str);
                    jSONObject.put("artist", str);
                    jSONObject.put("thumbnail_url", str);
                    jSONObject.put("source", "youtube");
                }
            }
            bb(webSocket, jSONObject.toString());
        } catch (Exception unused) {
        }
    }

    public final void bf() {
        z zVar = this.w;
        if (zVar != null) {
            try {
                this.c.unregisterReceiver(zVar);
            } catch (Exception unused) {
            }
            this.w = null;
        }
    }

    public final void c(boolean z2) {
        String str = "enabled";
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", "led_state");
            jSONObject.put(str, z2);
            if (!z2) {
                str = "disabled";
            }
            jSONObject.put("message", "LED ".concat(str));
            String jSONObject2 = jSONObject.toString();
            for (WebSocket next : this.t) {
                if (next != null && next.isOpen()) {
                    bb(next, jSONObject2);
                }
            }
        } catch (Exception unused) {
        }
    }

    public final void d(String str) {
        for (WebSocket next : this.t) {
            if (next != null && next.isOpen()) {
                bb(next, str);
            }
        }
    }

    public final void e() {
        for (WebSocket next : this.t) {
            if (next != null && next.isOpen()) {
                be(next);
            }
        }
    }

    public final void f() {
        a5 a5Var;
        if (this.g == null && (a5Var = this.f) != null) {
            this.g = a5Var.f;
        }
        MusicPlayer musicPlayer = this.g;
        if (musicPlayer != null) {
            musicPlayer.setCallback(this.z);
        }
    }

    public final void g(WebSocket webSocket, JSONObject jSONObject) {
        int i2;
        try {
            int i3 = jSONObject.getInt("hour");
            int i4 = jSONObject.getInt("minute");
            String optString = jSONObject.optString("repeat", "none");
            String optString2 = jSONObject.optString("label", "");
            int optInt = jSONObject.optInt("volume", 100);
            if (i3 >= 0 && i3 <= 23 && i4 >= 0) {
                if (i4 <= 59) {
                    if (optInt >= 0) {
                        if (optInt <= 100) {
                            i2 = optInt;
                            ab addAlarm = new AlarmService(this.c).addAlarm(i3, i4, optString, optString2, i2, jSONObject.optString("custom_sound_path", (String) null), jSONObject.optString("youtube_song_name", (String) null));
                            JSONObject jSONObject2 = new JSONObject();
                            jSONObject2.put("type", "alarm_added");
                            jSONObject2.put("success", true);
                            jSONObject2.put(NotificationCompat.CATEGORY_ALARM, addAlarm.d());
                            jSONObject2.put("message", "Báo thức đã được thêm: " + addAlarm.c() + " (" + addAlarm.b() + ")");
                            bb(webSocket, jSONObject2.toString());
                            return;
                        }
                    }
                    i2 = 100;
                    ab addAlarm2 = new AlarmService(this.c).addAlarm(i3, i4, optString, optString2, i2, jSONObject.optString("custom_sound_path", (String) null), jSONObject.optString("youtube_song_name", (String) null));
                    JSONObject jSONObject22 = new JSONObject();
                    jSONObject22.put("type", "alarm_added");
                    jSONObject22.put("success", true);
                    jSONObject22.put(NotificationCompat.CATEGORY_ALARM, addAlarm2.d());
                    jSONObject22.put("message", "Báo thức đã được thêm: " + addAlarm2.c() + " (" + addAlarm2.b() + ")");
                    bb(webSocket, jSONObject22.toString());
                    return;
                }
            }
            bd(webSocket, "Giờ hoặc phút không hợp lệ (hour: 0-23, minute: 0-59)");
        } catch (JSONException e) {
            bd(webSocket, "Lỗi thêm báo thức: " + e.getMessage());
        } catch (Exception e2) {
            y2.v(e2, new StringBuilder("Lỗi thêm báo thức: "), webSocket);
        }
    }

    public final void h(WebSocket webSocket, JSONObject jSONObject) {
        try {
            int i2 = jSONObject.getInt("alarm_id");
            boolean deleteAlarm = new AlarmService(this.c).deleteAlarm(i2);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("type", "alarm_deleted");
            jSONObject2.put("success", deleteAlarm);
            if (deleteAlarm) {
                jSONObject2.put("message", "Báo thức đã được xóa");
            } else {
                jSONObject2.put(MqttServiceConstants.TRACE_ERROR, "Không tìm thấy báo thức với ID: " + i2);
            }
            bb(webSocket, jSONObject2.toString());
        } catch (JSONException e) {
            bd(webSocket, "Lỗi xóa báo thức: " + e.getMessage());
        } catch (Exception e2) {
            y2.v(e2, new StringBuilder("Lỗi xóa báo thức: "), webSocket);
        }
    }

    public final void i(WebSocket webSocket, JSONObject jSONObject) {
        String str = "";
        try {
            int i2 = jSONObject.getInt("alarm_id");
            int i3 = jSONObject.getInt("hour");
            int i4 = jSONObject.getInt("minute");
            String optString = jSONObject.optString("repeat", "none");
            String optString2 = jSONObject.optString("label", str);
            if (i3 >= 0 && i3 <= 23 && i4 >= 0) {
                if (i4 <= 59) {
                    AlarmService alarmService = new AlarmService(this.c);
                    ab alarmById = alarmService.getAlarmById(i2);
                    if (alarmById == null) {
                        bd(webSocket, "Không tìm thấy báo thức với ID: " + i2);
                        return;
                    }
                    int optInt = jSONObject.optInt("volume", alarmById.g);
                    int i5 = 100;
                    if (optInt < 0 || optInt > 100) {
                        optInt = alarmById.g;
                    }
                    String optString3 = jSONObject.optString("custom_sound_path", (String) null);
                    alarmById.b = i3;
                    alarmById.c = i4;
                    alarmById.d = optString;
                    if (optString2 != null) {
                        str = optString2;
                    }
                    alarmById.f = str;
                    if (optInt >= 0 && optInt <= 100) {
                        i5 = optInt;
                    }
                    alarmById.g = i5;
                    if (optString3 != null) {
                        alarmById.h = optString3;
                    }
                    boolean updateAlarm = alarmService.updateAlarm(alarmById);
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("type", "alarm_edited");
                    jSONObject2.put("success", updateAlarm);
                    if (updateAlarm) {
                        jSONObject2.put(NotificationCompat.CATEGORY_ALARM, alarmById.d());
                        jSONObject2.put("message", "Báo thức đã được cập nhật: " + alarmById.c() + " (" + alarmById.b() + ")");
                    } else {
                        jSONObject2.put(MqttServiceConstants.TRACE_ERROR, "Không thể cập nhật báo thức");
                    }
                    bb(webSocket, jSONObject2.toString());
                    return;
                }
            }
            bd(webSocket, "Giờ hoặc phút không hợp lệ (hour: 0-23, minute: 0-59)");
        } catch (JSONException e) {
            bd(webSocket, "Lỗi chỉnh sửa báo thức: " + e.getMessage());
        } catch (Exception e2) {
            y2.v(e2, new StringBuilder("Lỗi chỉnh sửa báo thức: "), webSocket);
        }
    }

    public final void j(WebSocket webSocket) {
        try {
            List<ab> allAlarms = new AlarmService(this.c).getAllAlarms();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", "alarm_list");
            jSONObject.put("success", true);
            JSONArray jSONArray = new JSONArray();
            for (ab d : allAlarms) {
                jSONArray.put(d.d());
            }
            jSONObject.put("alarms", jSONArray);
            jSONObject.put("count", allAlarms.size());
            bb(webSocket, jSONObject.toString());
        } catch (Exception e) {
            y2.v(e, new StringBuilder("Lỗi lấy danh sách báo thức: "), webSocket);
        }
    }

    public final void k(WebSocket webSocket) {
        try {
            AlarmReceiver.c(this.c);
            b();
        } catch (Exception e) {
            y2.v(e, new StringBuilder("Lỗi dừng báo thức: "), webSocket);
        }
    }

    public final void l(WebSocket webSocket, JSONObject jSONObject) {
        String str;
        try {
            int i2 = jSONObject.getInt("alarm_id");
            AlarmService alarmService = new AlarmService(this.c);
            boolean z2 = alarmService.toggleAlarm(i2);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("type", "alarm_toggled");
            jSONObject2.put("success", z2);
            if (z2) {
                ab alarmById = alarmService.getAlarmById(i2);
                if (alarmById != null) {
                    jSONObject2.put(NotificationCompat.CATEGORY_ALARM, alarmById.d());
                    if (alarmById.e) {
                        str = "bật";
                    } else {
                        str = "tắt";
                    }
                    jSONObject2.put("message", "Báo thức đã được ".concat(str));
                }
            } else {
                jSONObject2.put(MqttServiceConstants.TRACE_ERROR, "Không tìm thấy báo thức với ID: " + i2);
            }
            bb(webSocket, jSONObject2.toString());
        } catch (JSONException e) {
            bd(webSocket, "Lỗi bật/tắt báo thức: " + e.getMessage());
        } catch (Exception e2) {
            y2.v(e2, new StringBuilder("Lỗi bật/tắt báo thức: "), webSocket);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0073, code lost:
        r9 = new info.dourok.voicebot.java.services.AlarmService(r0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void m(org.java_websocket.WebSocket r8, org.json.JSONObject r9) {
        /*
            r7 = this;
            android.content.Context r0 = r7.c
            java.lang.String r1 = "alarm_"
            java.lang.String r2 = "alarm_id"
            r3 = -1
            int r2 = r9.optInt(r2, r3)     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r3 = "file_name"
            java.lang.String r4 = "alarm_custom.mp3"
            java.lang.String r3 = r9.optString(r3, r4)     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r4 = "file_data"
            java.lang.String r5 = ""
            java.lang.String r9 = r9.optString(r4, r5)     // Catch:{ Exception -> 0x00a9 }
            boolean r4 = r9.isEmpty()     // Catch:{ Exception -> 0x00a9 }
            if (r4 == 0) goto L_0x0027
            java.lang.String r9 = "File data is required"
            bd(r8, r9)     // Catch:{ Exception -> 0x00a9 }
            return
        L_0x0027:
            r4 = 0
            byte[] r9 = android.util.Base64.decode(r9, r4)     // Catch:{ Exception -> 0x00a9 }
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x00a9 }
            java.io.File r5 = r0.getFilesDir()     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r6 = "alarm_sounds"
            r4.<init>(r5, r6)     // Catch:{ Exception -> 0x00a9 }
            boolean r5 = r4.exists()     // Catch:{ Exception -> 0x00a9 }
            if (r5 != 0) goto L_0x0040
            r4.mkdirs()     // Catch:{ Exception -> 0x00a9 }
        L_0x0040:
            if (r2 <= 0) goto L_0x005d
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x00a9 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a9 }
            r5.<init>(r1)     // Catch:{ Exception -> 0x00a9 }
            r5.append(r2)     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r1 = ".mp3"
            r5.append(r1)     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r1 = r5.toString()     // Catch:{ Exception -> 0x00a9 }
            r3.<init>(r4, r1)     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r1 = r3.getAbsolutePath()     // Catch:{ Exception -> 0x00a9 }
            goto L_0x0066
        L_0x005d:
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x00a9 }
            r1.<init>(r4, r3)     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r1 = r1.getAbsolutePath()     // Catch:{ Exception -> 0x00a9 }
        L_0x0066:
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00a9 }
            r3.<init>(r1)     // Catch:{ Exception -> 0x00a9 }
            r3.write(r9)     // Catch:{ Exception -> 0x00a9 }
            r3.close()     // Catch:{ Exception -> 0x00a9 }
            if (r2 <= 0) goto L_0x0083
            info.dourok.voicebot.java.services.AlarmService r9 = new info.dourok.voicebot.java.services.AlarmService     // Catch:{ Exception -> 0x00a9 }
            r9.<init>(r0)     // Catch:{ Exception -> 0x00a9 }
            ab r0 = r9.getAlarmById(r2)     // Catch:{ Exception -> 0x00a9 }
            if (r0 == 0) goto L_0x0083
            r0.h = r1     // Catch:{ Exception -> 0x00a9 }
            r9.updateAlarm(r0)     // Catch:{ Exception -> 0x00a9 }
        L_0x0083:
            org.json.JSONObject r9 = new org.json.JSONObject     // Catch:{ Exception -> 0x00a9 }
            r9.<init>()     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r0 = "type"
            java.lang.String r2 = "alarm_sound_uploaded"
            r9.put(r0, r2)     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r0 = "success"
            r2 = 1
            r9.put(r0, r2)     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r0 = "file_path"
            r9.put(r0, r1)     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r0 = "message"
            java.lang.String r1 = "File âm thanh đã được upload thành công"
            r9.put(r0, r1)     // Catch:{ Exception -> 0x00a9 }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x00a9 }
            bb(r8, r9)     // Catch:{ Exception -> 0x00a9 }
            goto L_0x00b4
        L_0x00a9:
            r9 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Lỗi upload file âm thanh: "
            r0.<init>(r1)
            defpackage.y2.v(r9, r0, r8)
        L_0x00b4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.x.m(org.java_websocket.WebSocket, org.json.JSONObject):void");
    }

    public final void n(WebSocket webSocket, JSONObject jSONObject) {
        try {
            new Thread(new p(this, jSONObject.optBoolean("enable", true), webSocket)).start();
        } catch (Exception e) {
            if (webSocket != null) {
                y2.v(e, new StringBuilder("Error: "), webSocket);
            }
        }
    }

    public final void o(WebSocket webSocket) {
        try {
            bc(webSocket);
        } catch (Exception e) {
            y2.v(e, new StringBuilder("Error: "), webSocket);
        }
    }

    public final void onClose(WebSocket webSocket, int i2, String str, boolean z2) {
        Objects.toString(webSocket.getRemoteSocketAddress());
        this.t.remove(webSocket);
    }

    public final void onError(WebSocket webSocket, Exception exc) {
        if (webSocket != null) {
            Objects.toString(webSocket.getRemoteSocketAddress());
        }
    }

    public final void onMessage(WebSocket webSocket, String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("action", "");
            if ("search_songs".equals(optString)) {
                ag(webSocket, jSONObject);
            } else if ("search_playlist".equals(optString)) {
                af(webSocket, jSONObject);
            } else if ("search_zing".equals(optString)) {
                ah(webSocket, jSONObject);
            } else if ("play_song".equals(optString)) {
                ac(webSocket, jSONObject);
            } else if ("pause".equals(optString)) {
                new Handler(Looper.getMainLooper()).post(new o(1, this, webSocket));
            } else if ("resume".equals(optString)) {
                new Handler(Looper.getMainLooper()).post(new o(2, this, webSocket));
            } else if ("stop".equals(optString)) {
                new Handler(Looper.getMainLooper()).post(new o(4, this, webSocket));
            } else if (ES6Iterator.NEXT_METHOD.equals(optString)) {
                ab(webSocket);
            } else if ("previous".equals(optString)) {
                ae(webSocket);
            } else if ("get_saved_results".equals(optString)) {
                u(webSocket);
            } else if ("seek".equals(optString)) {
                ai(webSocket, jSONObject);
            } else if ("play_zing".equals(optString)) {
                ad(webSocket, jSONObject);
            } else if ("toggle_auto_next".equals(optString)) {
                ak(webSocket, jSONObject);
            } else if ("alarm_add".equals(optString)) {
                g(webSocket, jSONObject);
            } else if ("alarm_list".equals(optString)) {
                j(webSocket);
            } else if ("alarm_delete".equals(optString)) {
                h(webSocket, jSONObject);
            } else if ("alarm_toggle".equals(optString)) {
                l(webSocket, jSONObject);
            } else if ("alarm_edit".equals(optString)) {
                i(webSocket, jSONObject);
            } else if ("alarm_stop".equals(optString)) {
                k(webSocket);
            } else if ("alarm_upload_sound".equals(optString)) {
                m(webSocket, jSONObject);
            } else if ("get_version".equals(optString)) {
                v(webSocket);
            } else if ("wifi_scan".equals(optString)) {
                new Thread(new o(3, this, webSocket)).start();
            } else if ("wifi_connect".equals(optString)) {
                ap(webSocket, jSONObject);
            } else if ("wifi_get_status".equals(optString)) {
                as(webSocket);
            } else if ("wifi_start_ap".equals(optString)) {
                at(webSocket);
            } else if ("wifi_stop_ap".equals(optString)) {
                au(webSocket);
            } else if ("wifi_get_saved".equals(optString)) {
                ar(webSocket);
            } else if ("wifi_delete_saved".equals(optString)) {
                aq(webSocket, jSONObject);
            } else if ("bluetooth_set_enabled".equals(optString)) {
                n(webSocket, jSONObject);
            } else if ("chat_send".equals(optString)) {
                q(webSocket, jSONObject);
            } else if ("chat_send_text".equals(optString)) {
                r(webSocket, jSONObject);
            } else if ("chat_wake_up".equals(optString)) {
                t(webSocket);
            } else if ("chat_test_mic".equals(optString)) {
                s(webSocket);
            } else if ("chat_get_state".equals(optString)) {
                p(webSocket);
            } else if ("chat_get_history".equals(optString)) {
                o(webSocket);
            } else if ("tiktok_reply_toggle".equals(optString)) {
                aj(webSocket, jSONObject);
            } else if ("wake_word_set_sensitivity".equals(optString)) {
                ao(webSocket, jSONObject);
            } else if ("wake_word_get_sensitivity".equals(optString)) {
                am(webSocket);
            } else if ("wake_word_set_enabled".equals(optString)) {
                an(webSocket, jSONObject);
            } else if ("wake_word_get_enabled".equals(optString)) {
                al(webSocket);
            } else if ("mac_random".equals(optString)) {
                aa(webSocket);
            } else if ("mac_get".equals(optString)) {
                z(webSocket);
            } else if ("mac_clear".equals(optString)) {
                y(webSocket);
            } else if ("led_toggle".equals(optString)) {
                x(webSocket);
            } else if ("led_get_state".equals(optString)) {
                w(webSocket);
            } else {
                bd(webSocket, "Unknown action: " + optString);
            }
        } catch (JSONException e) {
            bd(webSocket, "Invalid JSON: " + e.getMessage());
        } catch (Exception e2) {
            y2.v(e2, new StringBuilder("Error: "), webSocket);
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(15:0|1|2|3|(3:5|6|(1:8))|9|10|11|12|13|14|15|(1:19)|20|(12:22|23|(1:25)|26|27|(1:29)(1:30)|31|(1:33)(1:34)|35|(1:39)|40|42)(1:44)) */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x009b */
    /* JADX WARNING: Missing exception handler attribute for start block: B:14:0x00b6 */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00ed A[Catch:{ JSONException -> 0x014d }] */
    /* JADX WARNING: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onOpen(org.java_websocket.WebSocket r14, org.java_websocket.handshake.ClientHandshake r15) {
        /*
            r13 = this;
            java.lang.String r15 = "message"
            java.lang.String r0 = "type"
            java.lang.String r1 = " - "
            java.lang.String r2 = "Báo thức: "
            java.net.InetSocketAddress r3 = r14.getRemoteSocketAddress()
            j$.util.Objects.toString(r3)
            java.util.Set<org.java_websocket.WebSocket> r3 = r13.t
            r3.add(r14)
            r13.f()
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ JSONException -> 0x014d }
            r3.<init>()     // Catch:{ JSONException -> 0x014d }
            java.lang.String r4 = "connected"
            r3.put(r0, r4)     // Catch:{ JSONException -> 0x014d }
            java.lang.String r4 = "AiboxPlus WebSocket connected"
            r3.put(r15, r4)     // Catch:{ JSONException -> 0x014d }
            android.content.Context r4 = r13.c     // Catch:{ JSONException -> 0x014d }
            java.lang.String r5 = "enabled"
            if (r4 == 0) goto L_0x0057
            android.content.Context r4 = r4.getApplicationContext()     // Catch:{ JSONException -> 0x014d }
            boolean r4 = r4 instanceof info.dourok.voicebot.java.VoiceBotApplication     // Catch:{ JSONException -> 0x014d }
            if (r4 == 0) goto L_0x0057
            android.content.Context r4 = r13.c     // Catch:{ JSONException -> 0x014d }
            android.content.Context r4 = r4.getApplicationContext()     // Catch:{ JSONException -> 0x014d }
            info.dourok.voicebot.java.VoiceBotApplication r4 = (info.dourok.voicebot.java.VoiceBotApplication) r4     // Catch:{ JSONException -> 0x014d }
            boolean r6 = r4.isTiktokReplyEnabled()     // Catch:{ JSONException -> 0x014d }
            r4.getTiktokUser()     // Catch:{ JSONException -> 0x014d }
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ JSONException -> 0x014d }
            r4.<init>()     // Catch:{ JSONException -> 0x014d }
            java.lang.String r7 = "tiktok_reply_state"
            r4.put(r0, r7)     // Catch:{ JSONException -> 0x014d }
            r4.put(r5, r6)     // Catch:{ JSONException -> 0x014d }
            java.lang.String r4 = r4.toString()     // Catch:{ JSONException -> 0x014d }
            bb(r14, r4)     // Catch:{ JSONException -> 0x014d }
        L_0x0057:
            r4 = 1
            r6 = 0
            android.content.Context r7 = r13.c     // Catch:{ Exception -> 0x009b }
            java.lang.String r8 = "voicebot_prefs"
            android.content.SharedPreferences r7 = r7.getSharedPreferences(r8, r6)     // Catch:{ Exception -> 0x009b }
            java.lang.String r8 = "wake_word_sensitivity"
            r9 = 1063675494(0x3f666666, float:0.9)
            float r8 = r7.getFloat(r8, r9)     // Catch:{ Exception -> 0x009b }
            org.json.JSONObject r9 = new org.json.JSONObject     // Catch:{ Exception -> 0x009b }
            r9.<init>()     // Catch:{ Exception -> 0x009b }
            java.lang.String r10 = "wake_word_sensitivity_state"
            r9.put(r0, r10)     // Catch:{ Exception -> 0x009b }
            java.lang.String r10 = "sensitivity"
            double r11 = (double) r8     // Catch:{ Exception -> 0x009b }
            r9.put(r10, r11)     // Catch:{ Exception -> 0x009b }
            java.lang.String r8 = r9.toString()     // Catch:{ Exception -> 0x009b }
            bb(r14, r8)     // Catch:{ Exception -> 0x009b }
            java.lang.String r8 = "wake_word_enabled"
            boolean r7 = r7.getBoolean(r8, r4)     // Catch:{ Exception -> 0x009b }
            org.json.JSONObject r8 = new org.json.JSONObject     // Catch:{ Exception -> 0x009b }
            r8.<init>()     // Catch:{ Exception -> 0x009b }
            java.lang.String r9 = "wake_word_enabled_state"
            r8.put(r0, r9)     // Catch:{ Exception -> 0x009b }
            r8.put(r5, r7)     // Catch:{ Exception -> 0x009b }
            java.lang.String r7 = r8.toString()     // Catch:{ Exception -> 0x009b }
            bb(r14, r7)     // Catch:{ Exception -> 0x009b }
        L_0x009b:
            boolean r7 = defpackage.f4.e     // Catch:{ Exception -> 0x00b6 }
            org.json.JSONObject r8 = new org.json.JSONObject     // Catch:{ Exception -> 0x00b6 }
            r8.<init>()     // Catch:{ Exception -> 0x00b6 }
            java.lang.String r9 = "led_state"
            r8.put(r0, r9)     // Catch:{ Exception -> 0x00b6 }
            java.lang.String r9 = "success"
            r8.put(r9, r4)     // Catch:{ Exception -> 0x00b6 }
            r8.put(r5, r7)     // Catch:{ Exception -> 0x00b6 }
            java.lang.String r4 = r8.toString()     // Catch:{ Exception -> 0x00b6 }
            bb(r14, r4)     // Catch:{ Exception -> 0x00b6 }
        L_0x00b6:
            java.lang.String r3 = r3.toString()     // Catch:{ JSONException -> 0x014d }
            bb(r14, r3)     // Catch:{ JSONException -> 0x014d }
            java.util.ArrayList r3 = r13.h     // Catch:{ JSONException -> 0x014d }
            boolean r3 = r3.isEmpty()     // Catch:{ JSONException -> 0x014d }
            if (r3 == 0) goto L_0x00cd
            java.util.List<info.dourok.voicebot.java.services.ZingMp3Service$ZingMp3Song> r3 = r13.i     // Catch:{ JSONException -> 0x014d }
            boolean r3 = r3.isEmpty()     // Catch:{ JSONException -> 0x014d }
            if (r3 != 0) goto L_0x00e0
        L_0x00cd:
            android.os.Handler r3 = new android.os.Handler     // Catch:{ JSONException -> 0x014d }
            android.os.Looper r4 = android.os.Looper.getMainLooper()     // Catch:{ JSONException -> 0x014d }
            r3.<init>(r4)     // Catch:{ JSONException -> 0x014d }
            o r4 = new o     // Catch:{ JSONException -> 0x014d }
            r4.<init>(r6, r13, r14)     // Catch:{ JSONException -> 0x014d }
            r5 = 500(0x1f4, double:2.47E-321)
            r3.postDelayed(r4, r5)     // Catch:{ JSONException -> 0x014d }
        L_0x00e0:
            r13.v(r14)     // Catch:{ JSONException -> 0x014d }
            r13.be(r14)     // Catch:{ JSONException -> 0x014d }
            r13.bc(r14)     // Catch:{ JSONException -> 0x014d }
            java.lang.Integer r3 = r13.q     // Catch:{ JSONException -> 0x014d }
            if (r3 == 0) goto L_0x014d
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ JSONException -> 0x014d }
            r3.<init>()     // Catch:{ JSONException -> 0x014d }
            java.lang.String r4 = "alarm_triggered"
            r3.put(r0, r4)     // Catch:{ JSONException -> 0x014d }
            java.lang.String r0 = "alarm_id"
            java.lang.Integer r4 = r13.q     // Catch:{ JSONException -> 0x014d }
            r3.put(r0, r4)     // Catch:{ JSONException -> 0x014d }
            java.lang.String r0 = "alarm_label"
            java.lang.String r4 = r13.r     // Catch:{ JSONException -> 0x014d }
            java.lang.String r5 = ""
            if (r4 == 0) goto L_0x0107
            goto L_0x0108
        L_0x0107:
            r4 = r5
        L_0x0108:
            r3.put(r0, r4)     // Catch:{ JSONException -> 0x014d }
            java.lang.String r0 = "time"
            java.lang.String r4 = r13.s     // Catch:{ JSONException -> 0x014d }
            if (r4 == 0) goto L_0x0112
            goto L_0x0113
        L_0x0112:
            r4 = r5
        L_0x0113:
            r3.put(r0, r4)     // Catch:{ JSONException -> 0x014d }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x014d }
            r0.<init>(r2)     // Catch:{ JSONException -> 0x014d }
            java.lang.String r2 = r13.s     // Catch:{ JSONException -> 0x014d }
            if (r2 == 0) goto L_0x0120
            goto L_0x0121
        L_0x0120:
            r2 = r5
        L_0x0121:
            r0.append(r2)     // Catch:{ JSONException -> 0x014d }
            java.lang.String r2 = r13.r     // Catch:{ JSONException -> 0x014d }
            if (r2 == 0) goto L_0x013c
            boolean r2 = r2.isEmpty()     // Catch:{ JSONException -> 0x014d }
            if (r2 != 0) goto L_0x013c
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x014d }
            r2.<init>(r1)     // Catch:{ JSONException -> 0x014d }
            java.lang.String r1 = r13.r     // Catch:{ JSONException -> 0x014d }
            r2.append(r1)     // Catch:{ JSONException -> 0x014d }
            java.lang.String r5 = r2.toString()     // Catch:{ JSONException -> 0x014d }
        L_0x013c:
            r0.append(r5)     // Catch:{ JSONException -> 0x014d }
            java.lang.String r0 = r0.toString()     // Catch:{ JSONException -> 0x014d }
            r3.put(r15, r0)     // Catch:{ JSONException -> 0x014d }
            java.lang.String r15 = r3.toString()     // Catch:{ JSONException -> 0x014d }
            bb(r14, r15)     // Catch:{ JSONException -> 0x014d }
        L_0x014d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.x.onOpen(org.java_websocket.WebSocket, org.java_websocket.handshake.ClientHandshake):void");
    }

    public final void onStart() {
        b bVar = new b();
        this.v = bVar;
        this.u.post(bVar);
    }

    public final void p(WebSocket webSocket) {
        try {
            this.c.sendBroadcast(new Intent("info.dourok.voicebot.CHAT_GET_STATE"));
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", "chat_get_state_result");
            jSONObject.put("success", true);
            jSONObject.put("message", "State request sent");
            bb(webSocket, jSONObject.toString());
        } catch (Exception e) {
            y2.v(e, new StringBuilder("Error: "), webSocket);
        }
    }

    public final void q(WebSocket webSocket, JSONObject jSONObject) {
        try {
            String trim = jSONObject.optString("message", "").trim();
            String optString = jSONObject.optString("type", "user");
            if (trim.isEmpty()) {
                bd(webSocket, "Message is required");
                return;
            }
            a(trim, optString);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("type", "chat_message");
            jSONObject2.put("message_type", optString);
            jSONObject2.put("content", trim);
            jSONObject2.put("timestamp", System.currentTimeMillis());
            d(jSONObject2.toString());
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("type", "chat_send_result");
            jSONObject3.put("success", true);
            jSONObject3.put("message", "Message sent");
            bb(webSocket, jSONObject3.toString());
        } catch (JSONException e) {
            bd(webSocket, "Error: " + e.getMessage());
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:25|26) */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        java.lang.Thread.sleep(500);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        java.lang.Thread.currentThread().interrupt();
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x004d */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void r(org.java_websocket.WebSocket r5, org.json.JSONObject r6) {
        /*
            r4 = this;
            java.lang.String r0 = "text"
            java.lang.String r1 = ""
            java.lang.String r6 = r6.optString(r0, r1)     // Catch:{ Exception -> 0x0094 }
            java.lang.String r6 = r6.trim()     // Catch:{ Exception -> 0x0094 }
            boolean r0 = r6.isEmpty()     // Catch:{ Exception -> 0x0094 }
            if (r0 == 0) goto L_0x0018
            java.lang.String r6 = "Text is required"
            bd(r5, r6)     // Catch:{ Exception -> 0x0094 }
            return
        L_0x0018:
            android.content.Context r0 = r4.c     // Catch:{ Exception -> 0x0094 }
            h1 r0 = defpackage.h1.d(r0)     // Catch:{ Exception -> 0x0094 }
            t9 r0 = r0.i()     // Catch:{ Exception -> 0x0094 }
            if (r0 != 0) goto L_0x002a
            java.lang.String r6 = "ProtocolManager not available"
            bd(r5, r6)     // Catch:{ Exception -> 0x0094 }
            return
        L_0x002a:
            m1 r1 = r0.b()     // Catch:{ Exception -> 0x0094 }
            m1 r2 = defpackage.m1.IDLE     // Catch:{ Exception -> 0x0094 }
            if (r1 != r2) goto L_0x005f
            r0.e()     // Catch:{ Exception -> 0x0094 }
            r1 = 0
        L_0x0036:
            r2 = 50
            if (r1 >= r2) goto L_0x005f
            r2 = 100
            java.lang.Thread.sleep(r2)     // Catch:{ InterruptedException -> 0x0058 }
            m1 r2 = r0.b()     // Catch:{ Exception -> 0x0094 }
            m1 r3 = defpackage.m1.LISTENING     // Catch:{ Exception -> 0x0094 }
            if (r2 != r3) goto L_0x0055
            r1 = 500(0x1f4, double:2.47E-321)
            java.lang.Thread.sleep(r1)     // Catch:{ InterruptedException -> 0x004d }
            goto L_0x005f
        L_0x004d:
            java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch:{ Exception -> 0x0094 }
            r1.interrupt()     // Catch:{ Exception -> 0x0094 }
            goto L_0x005f
        L_0x0055:
            int r1 = r1 + 1
            goto L_0x0036
        L_0x0058:
            java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch:{ Exception -> 0x0094 }
            r1.interrupt()     // Catch:{ Exception -> 0x0094 }
        L_0x005f:
            java.util.concurrent.atomic.AtomicReference<i9> r0 = r0.f     // Catch:{ Exception -> 0x0094 }
            java.lang.Object r0 = r0.get()     // Catch:{ Exception -> 0x0094 }
            i9 r0 = (defpackage.i9) r0     // Catch:{ Exception -> 0x0094 }
            if (r0 == 0) goto L_0x0073
            p9 r1 = new p9     // Catch:{ Exception -> 0x0094 }
            r1.<init>(r0, r6)     // Catch:{ Exception -> 0x0094 }
            java.util.concurrent.ExecutorService r6 = r0.d     // Catch:{ Exception -> 0x0094 }
            r6.execute(r1)     // Catch:{ Exception -> 0x0094 }
        L_0x0073:
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ Exception -> 0x0094 }
            r6.<init>()     // Catch:{ Exception -> 0x0094 }
            java.lang.String r0 = "type"
            java.lang.String r1 = "chat_send_text_result"
            r6.put(r0, r1)     // Catch:{ Exception -> 0x0094 }
            java.lang.String r0 = "success"
            r1 = 1
            r6.put(r0, r1)     // Catch:{ Exception -> 0x0094 }
            java.lang.String r0 = "message"
            java.lang.String r1 = "Text sent to protocol"
            r6.put(r0, r1)     // Catch:{ Exception -> 0x0094 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0094 }
            bb(r5, r6)     // Catch:{ Exception -> 0x0094 }
            goto L_0x009f
        L_0x0094:
            r6 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Error: "
            r0.<init>(r1)
            defpackage.y2.v(r6, r0, r5)
        L_0x009f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.x.r(org.java_websocket.WebSocket, org.json.JSONObject):void");
    }

    public final void s(WebSocket webSocket) {
        try {
            this.c.sendBroadcast(new Intent("info.dourok.voicebot.CHAT_TEST_MIC"));
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", "chat_test_mic_result");
            jSONObject.put("success", true);
            jSONObject.put("message", "Test mic command sent");
            bb(webSocket, jSONObject.toString());
        } catch (Exception e) {
            y2.v(e, new StringBuilder("Error: "), webSocket);
        }
    }

    public final void stop() throws InterruptedException {
        b bVar = this.v;
        if (bVar != null) {
            this.u.removeCallbacks(bVar);
        }
        super.stop();
    }

    public final void t(WebSocket webSocket) {
        try {
            this.c.sendBroadcast(new Intent("info.dourok.voicebot.CHAT_WAKE_UP"));
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", "chat_wake_up_result");
            jSONObject.put("success", true);
            jSONObject.put("message", "Wake up command sent");
            bb(webSocket, jSONObject.toString());
        } catch (Exception e) {
            y2.v(e, new StringBuilder("Error: "), webSocket);
        }
    }

    public final void u(WebSocket webSocket) {
        if (webSocket != null && webSocket.isOpen()) {
            try {
                if (!this.i.isEmpty()) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("type", "zing_result");
                    jSONObject.put("success", true);
                    JSONArray jSONArray = new JSONArray();
                    for (ZingMp3Service.ZingMp3Song next : this.i) {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("song_id", next.id);
                        String str = next.title;
                        if (str == null) {
                            str = "";
                        }
                        jSONObject2.put("title", str);
                        String str2 = next.artistName;
                        if (str2 == null) {
                            str2 = "";
                        }
                        jSONObject2.put("artist", str2);
                        jSONObject2.put("duration_seconds", next.duration);
                        String str3 = next.thumb;
                        if (str3 == null) {
                            str3 = "";
                        }
                        jSONObject2.put("thumbnail_url", str3);
                        jSONArray.put(jSONObject2);
                    }
                    jSONObject.put("songs", jSONArray);
                    jSONObject.put("count", this.i.size());
                    bb(webSocket, jSONObject.toString());
                    this.i.size();
                } else if (!this.h.isEmpty()) {
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("type", "search_result");
                    jSONObject3.put("success", true);
                    JSONArray jSONArray2 = new JSONArray();
                    Iterator it = this.h.iterator();
                    while (it.hasNext()) {
                        lf.c cVar = (lf.c) it.next();
                        JSONObject jSONObject4 = new JSONObject();
                        jSONObject4.put("video_id", cVar.a);
                        jSONObject4.put("title", cVar.b);
                        jSONObject4.put("channel", cVar.c);
                        jSONObject4.put("duration_seconds", cVar.d);
                        String str4 = cVar.e;
                        if (str4 == null) {
                            str4 = "";
                        }
                        jSONObject4.put("thumbnail_url", str4);
                        jSONArray2.put(jSONObject4);
                    }
                    jSONObject3.put("songs", jSONArray2);
                    jSONObject3.put("count", this.h.size());
                    bb(webSocket, jSONObject3.toString());
                    this.h.size();
                }
            } catch (WebsocketNotConnectedException unused) {
            } catch (Exception e) {
                y2.v(e, new StringBuilder("Failed to get saved results: "), webSocket);
            }
        }
    }

    public final void v(WebSocket webSocket) {
        String latestServerVersion;
        char c2;
        int i2;
        int i3;
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", "version");
            jSONObject.put("success", true);
            jSONObject.put("version", "4.0.0");
            jSONObject.put("message", "App version: 4.0.0");
            Context context = this.c;
            if (context != null) {
                if ((context.getApplicationContext() instanceof VoiceBotApplication) && (latestServerVersion = ((VoiceBotApplication) context.getApplicationContext()).getLatestServerVersion()) != null && !latestServerVersion.isEmpty()) {
                    String[] split = "4.0.0".split("\\.");
                    String[] split2 = latestServerVersion.split("\\.");
                    int max = Math.max(split.length, split2.length);
                    int i4 = 0;
                    while (true) {
                        if (i4 >= max) {
                            c2 = 0;
                            break;
                        }
                        if (i4 < split.length) {
                            i2 = Integer.parseInt(split[i4]);
                        } else {
                            i2 = 0;
                        }
                        if (i4 < split2.length) {
                            i3 = Integer.parseInt(split2[i4]);
                        } else {
                            i3 = 0;
                        }
                        if (i2 < i3) {
                            c2 = 65535;
                            break;
                        } else if (i2 > i3) {
                            c2 = 1;
                            break;
                        } else {
                            i4++;
                        }
                    }
                    if (c2 < 0) {
                        jSONObject.put("has_new_version", true);
                        jSONObject.put("new_version", latestServerVersion);
                    } else {
                        jSONObject.put("has_new_version", false);
                    }
                }
            }
            bb(webSocket, jSONObject.toString());
        } catch (JSONException e) {
            bd(webSocket, "Lỗi lấy version: " + e.getMessage());
        }
    }

    public final void x(WebSocket webSocket) {
        boolean z2;
        h1 dependencies;
        t9 i2;
        String str = "enabled";
        try {
            if (!f4.e) {
                z2 = true;
            } else {
                z2 = false;
            }
            f4.e(z2);
            Context context = this.c;
            if (!(context == null || !(context.getApplicationContext() instanceof VoiceBotApplication) || (dependencies = ((VoiceBotApplication) this.c.getApplicationContext()).getDependencies()) == null || (i2 = dependencies.i()) == null || i2.b() != m1.IDLE)) {
                f4.a().d();
            }
            c(z2);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", "led_toggle_result");
            jSONObject.put("success", true);
            jSONObject.put(str, z2);
            if (!z2) {
                str = "disabled";
            }
            jSONObject.put("message", "LED ".concat(str));
            bb(webSocket, jSONObject.toString());
        } catch (Exception e) {
            y2.v(e, new StringBuilder("Error toggling LED: "), webSocket);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0035 A[SYNTHETIC, Splitter:B:17:0x0035] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0043 A[Catch:{ Exception -> 0x0055 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void y(org.java_websocket.WebSocket r7) {
        /*
            r6 = this;
            android.content.Context r0 = r6.c     // Catch:{ Exception -> 0x0055 }
            n4 r0 = defpackage.n4.a(r0)     // Catch:{ Exception -> 0x0055 }
            if (r0 != 0) goto L_0x000e
            java.lang.String r0 = "Failed to initialize MacAddressManager"
            bd(r7, r0)     // Catch:{ Exception -> 0x0055 }
            return
        L_0x000e:
            android.content.SharedPreferences r0 = r0.a     // Catch:{ Exception -> 0x0055 }
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L_0x0015
            goto L_0x0024
        L_0x0015:
            android.content.SharedPreferences$Editor r0 = r0.edit()     // Catch:{ Exception -> 0x0024 }
            java.lang.String r3 = "custom_mac_address"
            android.content.SharedPreferences$Editor r0 = r0.remove(r3)     // Catch:{ Exception -> 0x0024 }
            r0.apply()     // Catch:{ Exception -> 0x0024 }
            r0 = 1
            goto L_0x0025
        L_0x0024:
            r0 = 0
        L_0x0025:
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ Exception -> 0x0055 }
            r3.<init>()     // Catch:{ Exception -> 0x0055 }
            java.lang.String r4 = "type"
            java.lang.String r5 = "mac_clear"
            r3.put(r4, r5)     // Catch:{ Exception -> 0x0055 }
            java.lang.String r4 = "success"
            if (r0 == 0) goto L_0x0043
            r6.ax()     // Catch:{ Exception -> 0x0055 }
            r3.put(r4, r1)     // Catch:{ Exception -> 0x0055 }
            java.lang.String r0 = "message"
            java.lang.String r1 = "Custom MAC address cleared, using real MAC now"
            r3.put(r0, r1)     // Catch:{ Exception -> 0x0055 }
            goto L_0x004d
        L_0x0043:
            r3.put(r4, r2)     // Catch:{ Exception -> 0x0055 }
            java.lang.String r0 = "error"
            java.lang.String r1 = "Failed to clear MAC address"
            r3.put(r0, r1)     // Catch:{ Exception -> 0x0055 }
        L_0x004d:
            java.lang.String r0 = r3.toString()     // Catch:{ Exception -> 0x0055 }
            bb(r7, r0)     // Catch:{ Exception -> 0x0055 }
            goto L_0x0060
        L_0x0055:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Error clearing MAC: "
            r1.<init>(r2)
            defpackage.y2.v(r0, r1, r7)
        L_0x0060:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.x.y(org.java_websocket.WebSocket):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0043 A[Catch:{ Exception -> 0x0054 }] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0044 A[Catch:{ Exception -> 0x0054 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void z(org.java_websocket.WebSocket r9) {
        /*
            r8 = this;
            android.content.Context r0 = r8.c
            n4 r1 = defpackage.n4.a(r0)     // Catch:{ Exception -> 0x0054 }
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L_0x0016
            java.lang.String r4 = r1.b()     // Catch:{ Exception -> 0x0054 }
            java.lang.String r1 = r1.b()     // Catch:{ Exception -> 0x0054 }
            if (r1 == 0) goto L_0x0017
            r1 = 1
            goto L_0x0018
        L_0x0016:
            r4 = 0
        L_0x0017:
            r1 = 0
        L_0x0018:
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ Exception -> 0x0054 }
            r5.<init>()     // Catch:{ Exception -> 0x0054 }
            java.lang.String r6 = "type"
            java.lang.String r7 = "mac_get"
            r5.put(r6, r7)     // Catch:{ Exception -> 0x0054 }
            java.lang.String r6 = "success"
            r5.put(r6, r2)     // Catch:{ Exception -> 0x0054 }
            java.lang.String r6 = "is_custom"
            java.lang.String r7 = "mac_address"
            if (r1 == 0) goto L_0x0038
            if (r4 == 0) goto L_0x0038
            r5.put(r7, r4)     // Catch:{ Exception -> 0x0054 }
            r5.put(r6, r2)     // Catch:{ Exception -> 0x0054 }
            goto L_0x004c
        L_0x0038:
            c2 r1 = new c2     // Catch:{ Exception -> 0x0054 }
            r1.<init>(r0)     // Catch:{ Exception -> 0x0054 }
            java.lang.String r0 = r1.d()     // Catch:{ Exception -> 0x0054 }
            if (r0 == 0) goto L_0x0044
            goto L_0x0046
        L_0x0044:
            java.lang.String r0 = "unknown"
        L_0x0046:
            r5.put(r7, r0)     // Catch:{ Exception -> 0x0054 }
            r5.put(r6, r3)     // Catch:{ Exception -> 0x0054 }
        L_0x004c:
            java.lang.String r0 = r5.toString()     // Catch:{ Exception -> 0x0054 }
            bb(r9, r0)     // Catch:{ Exception -> 0x0054 }
            goto L_0x005f
        L_0x0054:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Error getting MAC: "
            r1.<init>(r2)
            defpackage.y2.v(r0, r1, r9)
        L_0x005f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.x.z(org.java_websocket.WebSocket):void");
    }
}
