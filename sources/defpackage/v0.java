package defpackage;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;
import androidx.core.os.EnvironmentCompat;
import fi.iki.elonen.NanoHTTPD;
import info.dourok.voicebot.java.VoiceBotApplication;
import info.dourok.voicebot.java.services.ZaloMqttClient;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* renamed from: v0  reason: default package */
public final class v0 extends NanoHTTPD {
    public final Context a;

    public v0(Context context) {
        super(8081);
        this.a = context;
        Log.d("ControlWebServer", "ControlWebServer constructor called, port=8081");
    }

    public static String a(String str) {
        String lowerCase = str.toLowerCase();
        if (lowerCase.endsWith(".css")) {
            return "text/css";
        }
        if (lowerCase.endsWith(".js")) {
            return "application/javascript";
        }
        if (lowerCase.endsWith(".html")) {
            return NanoHTTPD.MIME_HTML;
        }
        if (lowerCase.endsWith(".png")) {
            return "image/png";
        }
        if (lowerCase.endsWith(".jpg") || lowerCase.endsWith(".jpeg")) {
            return "image/jpeg";
        }
        if (lowerCase.endsWith(".json")) {
            return "application/json";
        }
        return NanoHTTPD.MIME_PLAINTEXT;
    }

    public final boolean b() {
        int i;
        try {
            WifiManager wifiManager = (WifiManager) this.a.getSystemService("wifi");
            try {
                i = ((Integer) wifiManager.getClass().getMethod("getWifiApState", new Class[0]).invoke(wifiManager, new Object[0])).intValue();
            } catch (Exception unused) {
                i = 14;
            }
            if (i == 13 || i == 12) {
                return true;
            }
            return false;
        } catch (Exception unused2) {
            return false;
        }
    }

    public final void c() {
        c2 c;
        Context context = this.a;
        if (context != null) {
            try {
                if (context.getApplicationContext() instanceof VoiceBotApplication) {
                    VoiceBotApplication voiceBotApplication = (VoiceBotApplication) context.getApplicationContext();
                    h1 dependencies = voiceBotApplication.getDependencies();
                    if (!(dependencies == null || (c = dependencies.c()) == null)) {
                        c.b = c.a();
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

    public final NanoHTTPD.Response d() {
        String str;
        try {
            Object[] objArr = new Object[1];
            if (b()) {
                str = "true";
            } else {
                str = "false";
            }
            objArr[0] = str;
            NanoHTTPD.Response newFixedLengthResponse = NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, "application/json", String.format("{\"ap_mode\":%s}", objArr));
            newFixedLengthResponse.addHeader("Access-Control-Allow-Origin", "*");
            return newFixedLengthResponse;
        } catch (Exception unused) {
            return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.INTERNAL_ERROR, "application/json", "{\"error\":\"Failed to get AP mode status\"}");
        }
    }

    public final NanoHTTPD.Response e(NanoHTTPD.IHTTPSession iHTTPSession) {
        String str;
        try {
            String str2 = iHTTPSession.getHeaders().get("host");
            if (str2 != null) {
                str = str2.split(":")[0];
            } else {
                str = EnvironmentCompat.MEDIA_UNKNOWN;
            }
            NanoHTTPD.Response newFixedLengthResponse = NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, "application/json", String.format("{\"ip\":\"%s\",\"port\":%d}", new Object[]{str, Integer.valueOf(getListeningPort())}));
            newFixedLengthResponse.addHeader("Access-Control-Allow-Origin", "*");
            return newFixedLengthResponse;
        } catch (Exception unused) {
            return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.INTERNAL_ERROR, "application/json", "{\"error\":\"Failed to get IP\"}");
        }
    }

    public final NanoHTTPD.Response f() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.a.getAssets().open("http/index.html"), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    sb.append(readLine);
                    sb.append("\n");
                } else {
                    bufferedReader.close();
                    NanoHTTPD.Response newFixedLengthResponse = NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, NanoHTTPD.MIME_HTML, sb.toString());
                    newFixedLengthResponse.addHeader("Access-Control-Allow-Origin", "*");
                    return newFixedLengthResponse;
                }
            }
        } catch (IOException unused) {
            return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.INTERNAL_ERROR, NanoHTTPD.MIME_PLAINTEXT, "Could not load control page");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0033 A[SYNTHETIC, Splitter:B:14:0x0033] */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0042 A[Catch:{ Exception -> 0x004e }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final fi.iki.elonen.NanoHTTPD.Response g() {
        /*
            r7 = this;
            java.lang.String r0 = "*"
            java.lang.String r1 = "Access-Control-Allow-Origin"
            java.lang.String r2 = "application/json"
            r3 = 0
            r4 = 1
            android.content.Context r5 = r7.a     // Catch:{ Exception -> 0x004e }
            n4 r5 = defpackage.n4.a(r5)     // Catch:{ Exception -> 0x004e }
            if (r5 != 0) goto L_0x001c
            java.lang.String r5 = "{\"success\":false,\"error\":\"Failed to initialize MacAddressManager\"}"
            fi.iki.elonen.NanoHTTPD$Response$Status r6 = fi.iki.elonen.NanoHTTPD.Response.Status.INTERNAL_ERROR     // Catch:{ Exception -> 0x004e }
            fi.iki.elonen.NanoHTTPD$Response r5 = fi.iki.elonen.NanoHTTPD.newFixedLengthResponse(r6, r2, r5)     // Catch:{ Exception -> 0x004e }
            r5.addHeader(r1, r0)     // Catch:{ Exception -> 0x004e }
            return r5
        L_0x001c:
            android.content.SharedPreferences r5 = r5.a     // Catch:{ Exception -> 0x004e }
            if (r5 != 0) goto L_0x0021
            goto L_0x0030
        L_0x0021:
            android.content.SharedPreferences$Editor r5 = r5.edit()     // Catch:{ Exception -> 0x0030 }
            java.lang.String r6 = "custom_mac_address"
            android.content.SharedPreferences$Editor r5 = r5.remove(r6)     // Catch:{ Exception -> 0x0030 }
            r5.apply()     // Catch:{ Exception -> 0x0030 }
            r5 = 1
            goto L_0x0031
        L_0x0030:
            r5 = 0
        L_0x0031:
            if (r5 == 0) goto L_0x0042
            r7.c()     // Catch:{ Exception -> 0x004e }
            java.lang.String r5 = "{\"success\":true,\"message\":\"Custom MAC address cleared, using real MAC now\"}"
            fi.iki.elonen.NanoHTTPD$Response$Status r6 = fi.iki.elonen.NanoHTTPD.Response.Status.OK     // Catch:{ Exception -> 0x004e }
            fi.iki.elonen.NanoHTTPD$Response r5 = fi.iki.elonen.NanoHTTPD.newFixedLengthResponse(r6, r2, r5)     // Catch:{ Exception -> 0x004e }
            r5.addHeader(r1, r0)     // Catch:{ Exception -> 0x004e }
            return r5
        L_0x0042:
            java.lang.String r5 = "{\"success\":false,\"error\":\"Failed to clear MAC address\"}"
            fi.iki.elonen.NanoHTTPD$Response$Status r6 = fi.iki.elonen.NanoHTTPD.Response.Status.INTERNAL_ERROR     // Catch:{ Exception -> 0x004e }
            fi.iki.elonen.NanoHTTPD$Response r5 = fi.iki.elonen.NanoHTTPD.newFixedLengthResponse(r6, r2, r5)     // Catch:{ Exception -> 0x004e }
            r5.addHeader(r1, r0)     // Catch:{ Exception -> 0x004e }
            return r5
        L_0x004e:
            r5 = move-exception
            java.lang.Object[] r4 = new java.lang.Object[r4]
            java.lang.String r5 = r5.getMessage()
            r4[r3] = r5
            java.lang.String r3 = "{\"success\":false,\"error\":\"%s\"}"
            java.lang.String r3 = java.lang.String.format(r3, r4)
            fi.iki.elonen.NanoHTTPD$Response$Status r4 = fi.iki.elonen.NanoHTTPD.Response.Status.INTERNAL_ERROR
            fi.iki.elonen.NanoHTTPD$Response r2 = fi.iki.elonen.NanoHTTPD.newFixedLengthResponse(r4, r2, r3)
            r2.addHeader(r1, r0)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.v0.g():fi.iki.elonen.NanoHTTPD$Response");
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x003c A[Catch:{ Exception -> 0x004f }] */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x003d A[Catch:{ Exception -> 0x004f }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final fi.iki.elonen.NanoHTTPD.Response h() {
        /*
            r8 = this;
            java.lang.String r0 = "*"
            java.lang.String r1 = "Access-Control-Allow-Origin"
            java.lang.String r2 = "application/json"
            android.content.Context r3 = r8.a
            r4 = 1
            r5 = 0
            n4 r6 = defpackage.n4.a(r3)     // Catch:{ Exception -> 0x004f }
            if (r6 == 0) goto L_0x001c
            java.lang.String r7 = r6.b()     // Catch:{ Exception -> 0x004f }
            java.lang.String r6 = r6.b()     // Catch:{ Exception -> 0x004f }
            if (r6 == 0) goto L_0x001d
            r6 = 1
            goto L_0x001e
        L_0x001c:
            r7 = 0
        L_0x001d:
            r6 = 0
        L_0x001e:
            if (r6 == 0) goto L_0x002d
            if (r7 == 0) goto L_0x002d
            java.lang.String r3 = "{\"success\":true,\"mac_address\":\"%s\",\"is_custom\":true}"
            java.lang.Object[] r6 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x004f }
            r6[r5] = r7     // Catch:{ Exception -> 0x004f }
            java.lang.String r3 = java.lang.String.format(r3, r6)     // Catch:{ Exception -> 0x004f }
            goto L_0x0045
        L_0x002d:
            c2 r6 = new c2     // Catch:{ Exception -> 0x004f }
            r6.<init>(r3)     // Catch:{ Exception -> 0x004f }
            java.lang.String r3 = r6.d()     // Catch:{ Exception -> 0x004f }
            java.lang.String r6 = "{\"success\":true,\"mac_address\":\"%s\",\"is_custom\":false}"
            java.lang.Object[] r7 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x004f }
            if (r3 == 0) goto L_0x003d
            goto L_0x003f
        L_0x003d:
            java.lang.String r3 = "unknown"
        L_0x003f:
            r7[r5] = r3     // Catch:{ Exception -> 0x004f }
            java.lang.String r3 = java.lang.String.format(r6, r7)     // Catch:{ Exception -> 0x004f }
        L_0x0045:
            fi.iki.elonen.NanoHTTPD$Response$Status r6 = fi.iki.elonen.NanoHTTPD.Response.Status.OK     // Catch:{ Exception -> 0x004f }
            fi.iki.elonen.NanoHTTPD$Response r3 = fi.iki.elonen.NanoHTTPD.newFixedLengthResponse(r6, r2, r3)     // Catch:{ Exception -> 0x004f }
            r3.addHeader(r1, r0)     // Catch:{ Exception -> 0x004f }
            return r3
        L_0x004f:
            r3 = move-exception
            java.lang.Object[] r4 = new java.lang.Object[r4]
            java.lang.String r3 = r3.getMessage()
            r4[r5] = r3
            java.lang.String r3 = "{\"success\":false,\"error\":\"%s\"}"
            java.lang.String r3 = java.lang.String.format(r3, r4)
            fi.iki.elonen.NanoHTTPD$Response$Status r4 = fi.iki.elonen.NanoHTTPD.Response.Status.INTERNAL_ERROR
            fi.iki.elonen.NanoHTTPD$Response r2 = fi.iki.elonen.NanoHTTPD.newFixedLengthResponse(r4, r2, r3)
            r2.addHeader(r1, r0)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.v0.h():fi.iki.elonen.NanoHTTPD$Response");
    }

    public final NanoHTTPD.Response i(NanoHTTPD.IHTTPSession iHTTPSession) {
        try {
            NanoHTTPD.Method method = iHTTPSession.getMethod();
            if (method != NanoHTTPD.Method.POST && method != NanoHTTPD.Method.GET) {
                return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.METHOD_NOT_ALLOWED, "application/json", "{\"error\":\"Method not allowed\"}");
            }
            n4 a2 = n4.a(this.a);
            if (a2 == null) {
                NanoHTTPD.Response newFixedLengthResponse = NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.INTERNAL_ERROR, "application/json", "{\"success\":false,\"error\":\"Failed to initialize MacAddressManager\"}");
                newFixedLengthResponse.addHeader("Access-Control-Allow-Origin", "*");
                return newFixedLengthResponse;
            }
            String c = a2.c();
            if (c != null) {
                c();
                NanoHTTPD.Response newFixedLengthResponse2 = NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, "application/json", String.format("{\"success\":true,\"mac_address\":\"%s\",\"message\":\"MAC address randomized and saved\"}", new Object[]{c}));
                newFixedLengthResponse2.addHeader("Access-Control-Allow-Origin", "*");
                return newFixedLengthResponse2;
            }
            NanoHTTPD.Response newFixedLengthResponse3 = NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.INTERNAL_ERROR, "application/json", "{\"success\":false,\"error\":\"Failed to generate or save MAC address\"}");
            newFixedLengthResponse3.addHeader("Access-Control-Allow-Origin", "*");
            return newFixedLengthResponse3;
        } catch (Exception e) {
            NanoHTTPD.Response newFixedLengthResponse4 = NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.INTERNAL_ERROR, "application/json", String.format("{\"success\":false,\"error\":\"%s\"}", new Object[]{e.getMessage()}));
            newFixedLengthResponse4.addHeader("Access-Control-Allow-Origin", "*");
            return newFixedLengthResponse4;
        }
    }

    public final NanoHTTPD.Response j(String str) {
        String str2;
        try {
            if (str.startsWith(MqttTopic.TOPIC_LEVEL_SEPARATOR)) {
                str2 = "http".concat(str);
            } else {
                str2 = "http/".concat(str);
            }
            String a2 = a(str);
            InputStream open = this.a.getAssets().open(str2);
            if (!a2.startsWith("font/")) {
                if (!a2.startsWith("image/") && !str.endsWith(".woff2") && !str.endsWith(".woff") && !str.endsWith(".ttf") && !str.endsWith(".eot")) {
                    if (!str.endsWith(".otf")) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(open, "UTF-8"));
                        StringBuilder sb = new StringBuilder();
                        while (true) {
                            String readLine = bufferedReader.readLine();
                            if (readLine != null) {
                                sb.append(readLine);
                                sb.append("\n");
                            } else {
                                bufferedReader.close();
                                open.close();
                                NanoHTTPD.Response newFixedLengthResponse = NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, a2, sb.toString());
                                newFixedLengthResponse.addHeader("Access-Control-Allow-Origin", "*");
                                return newFixedLengthResponse;
                            }
                        }
                    }
                }
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int read = open.read(bArr);
                if (read != -1) {
                    byteArrayOutputStream.write(bArr, 0, read);
                } else {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    open.close();
                    NanoHTTPD.Response newFixedLengthResponse2 = NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, a2, new ByteArrayInputStream(byteArray), (long) byteArray.length);
                    newFixedLengthResponse2.addHeader("Access-Control-Allow-Origin", "*");
                    return newFixedLengthResponse2;
                }
            }
        } catch (IOException unused) {
            return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "File not found: ".concat(str));
        }
    }

    public final NanoHTTPD.Response k(NanoHTTPD.IHTTPSession iHTTPSession) {
        String str;
        try {
            String str2 = iHTTPSession.getHeaders().get("host");
            if (str2 != null) {
                str = str2.split(":")[0];
            } else {
                str = EnvironmentCompat.MEDIA_UNKNOWN;
            }
            NanoHTTPD.Response newFixedLengthResponse = NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, "application/json", String.format("{\"running\":true,\"port\":%d,\"ip\":\"%s\"}", new Object[]{Integer.valueOf(getListeningPort()), str}));
            newFixedLengthResponse.addHeader("Access-Control-Allow-Origin", "*");
            return newFixedLengthResponse;
        } catch (Exception unused) {
            return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.INTERNAL_ERROR, "application/json", "{\"error\":\"Failed to get status\"}");
        }
    }

    public final NanoHTTPD.Response l() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.a.getAssets().open("http/wifi.html"), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    sb.append(readLine);
                    sb.append("\n");
                } else {
                    bufferedReader.close();
                    NanoHTTPD.Response newFixedLengthResponse = NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, NanoHTTPD.MIME_HTML, sb.toString());
                    newFixedLengthResponse.addHeader("Access-Control-Allow-Origin", "*");
                    return newFixedLengthResponse;
                }
            }
        } catch (IOException unused) {
            return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.INTERNAL_ERROR, NanoHTTPD.MIME_PLAINTEXT, "Could not load WiFi setup page");
        }
    }

    public final void m() throws IOException {
        Log.d("ControlWebServer", "startServer() called, attempting to start on port 8081");
        try {
            start(5000, false);
            getListeningPort();
            getListeningPort();
        } catch (Exception e) {
            e.getMessage();
            throw e;
        }
    }

    public final NanoHTTPD.Response serve(NanoHTTPD.IHTTPSession iHTTPSession) {
        String uri = iHTTPSession.getUri();
        NanoHTTPD.Method method = iHTTPSession.getMethod();
        Log.d("ControlWebServer", "Request: " + method + " " + uri);
        try {
            if (!uri.equals(MqttTopic.TOPIC_LEVEL_SEPARATOR)) {
                if (!uri.equals("/index.html")) {
                    if (uri.equals("/api/ip")) {
                        return e(iHTTPSession);
                    }
                    if (uri.equals("/api/status")) {
                        return k(iHTTPSession);
                    }
                    if (uri.equals("/api/ap-mode")) {
                        return d();
                    }
                    if (uri.equals("/api/mac/random")) {
                        return i(iHTTPSession);
                    }
                    if (uri.equals("/api/mac/get")) {
                        return h();
                    }
                    if (uri.equals("/api/mac/clear")) {
                        return g();
                    }
                    return j(uri);
                }
            }
            if (b()) {
                return l();
            }
            return f();
        } catch (Exception unused) {
            return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.INTERNAL_ERROR, NanoHTTPD.MIME_PLAINTEXT, "Internal Server Error");
        }
    }
}
