package info.dourok.voicebot.java.services;

import android.content.Context;
import android.util.Log;
import defpackage.c2;
import info.dourok.voicebot.java.data.model.DeviceInfo;
import j$.util.Objects;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OtaService {
    private static final String DEFAULT_OTA_URL = "https://api.tenclass.net/xiaozhi/ota/";
    private static final String TAG = "OtaService";
    private final OkHttpClient client;
    private final Context context;
    private final c2 esp32DeviceInfoProvider;
    private final ExecutorService executor;

    public OtaService(ExecutorService executorService, Context context2, c2 c2Var) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        TimeUnit timeUnit = TimeUnit.SECONDS;
        this.client = builder.connectTimeout(10, timeUnit).readTimeout(30, timeUnit).build();
        this.executor = executorService;
        this.context = context2;
        this.esp32DeviceInfoProvider = c2Var;
    }

    private u7 checkActivationStatusSync(DeviceInfo deviceInfo, String str) throws IOException, JSONException {
        Request request;
        if (str.length() >= 10) {
            String jSONObject = deviceInfoToJson(deviceInfo).toString();
            Request.Builder url = new Request.Builder().url(str);
            url.addHeader("Device-Id", getDeviceId(deviceInfo));
            url.addHeader("Client-Id", getClientId(deviceInfo));
            url.addHeader("X-Language", "Chinese");
            url.addHeader("Content-Type", "application/json");
            if (!jSONObject.isEmpty()) {
                request = url.post(RequestBody.create(MediaType.parse("application/json"), jSONObject)).build();
            } else {
                request = url.get().build();
            }
            request.method();
            Objects.toString(request.url());
            for (String str2 : request.headers().names()) {
                request.headers().get(str2);
            }
            request.body();
            Response execute = this.client.newCall(request).execute();
            try {
                execute.code();
                execute.message();
                for (String str3 : execute.headers().names()) {
                    execute.headers().get(str3);
                }
                String string = execute.body().string();
                if (!execute.isSuccessful()) {
                    execute.code();
                    throw new IOException("Server error: " + execute.code() + " " + execute.message() + " - " + string);
                } else if (string == null || string.isEmpty()) {
                    throw new IOException("Empty response body");
                } else {
                    u7 a = u7.a(new JSONObject(string));
                    execute.close();
                    return a;
                }
            } catch (Throwable th) {
                th.addSuppressed(th);
            }
        } else {
            throw new IOException("OTA URL is not properly set");
        }
        throw th;
    }

    private JSONObject deviceInfoToJson(DeviceInfo deviceInfo) throws JSONException {
        String str;
        DeviceInfo c = this.esp32DeviceInfoProvider.c();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("version", c.r);
            String str2 = c.k;
            if (str2 == null) {
                str2 = "vi-VN";
            }
            jSONObject.put("language", str2);
            jSONObject.put("flash_size", c.j);
            jSONObject.put("minimum_free_heap_size", c.m);
            jSONObject.put("mac_address", c.l);
            jSONObject.put("uuid", c.q);
            jSONObject.put("chip_model_name", c.h);
            if (c.g != null) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("model", c.g.g);
                jSONObject2.put("cores", c.g.c);
                jSONObject2.put("revision", c.g.h);
                jSONObject2.put("features", c.g.f);
                jSONObject.put("chip_info", jSONObject2);
            }
            if (c.c != null) {
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("name", c.c.i);
                jSONObject3.put("version", c.c.j);
                jSONObject3.put("compile_time", c.c.f);
                jSONObject3.put("idf_version", c.c.h);
                jSONObject3.put("elf_sha256", c.c.g);
                jSONObject.put("application", jSONObject3);
            }
            if (c.o != null) {
                JSONArray jSONArray = new JSONArray();
                for (DeviceInfo.Partition next : c.o) {
                    JSONObject jSONObject4 = new JSONObject();
                    jSONObject4.put("label", next.g);
                    jSONObject4.put("type", next.k);
                    jSONObject4.put("subtype", next.i);
                    jSONObject4.put("address", next.c);
                    jSONObject4.put("size", next.h);
                    jSONArray.put(jSONObject4);
                }
                jSONObject.put("partition_table", jSONArray);
            }
            if (c.n != null) {
                JSONObject jSONObject5 = new JSONObject();
                jSONObject5.put("label", c.n.f);
                jSONObject.put("ota", jSONObject5);
            }
            if (c.i != null) {
                JSONObject jSONObject6 = new JSONObject();
                jSONObject6.put("monochrome", c.i.g);
                jSONObject6.put("width", c.i.i);
                jSONObject6.put("height", c.i.f);
                jSONObject.put("display", jSONObject6);
            }
            if (c.f != null) {
                JSONObject jSONObject7 = new JSONObject();
                jSONObject7.put("type", c.f.q);
                jSONObject7.put("name", c.f.l);
                jSONObject7.put("ssid", c.f.p);
                jSONObject7.put("rssi", c.f.n);
                jSONObject7.put("channel", c.f.c);
                jSONObject7.put("ip", c.f.g);
                jSONObject7.put("mac", c.f.h);
                jSONObject.put("board", jSONObject7);
            }
            str = jSONObject.toString();
        } catch (Exception unused) {
            str = "{}";
        }
        JSONObject jSONObject8 = new JSONObject(str);
        this.esp32DeviceInfoProvider.getClass();
        this.esp32DeviceInfoProvider.d();
        String str3 = this.esp32DeviceInfoProvider.c;
        return jSONObject8;
    }

    private String generateRandomSha256() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 64; i++) {
            sb.append("0123456789abcdef".charAt((int) (Math.random() * ((double) 16))));
        }
        return sb.toString();
    }

    private String getClientId(DeviceInfo deviceInfo) {
        String str;
        if (deviceInfo == null || (str = deviceInfo.q) == null || str.isEmpty()) {
            return UUID.randomUUID().toString();
        }
        return deviceInfo.q;
    }

    private String getDeviceIP() {
        try {
            c2.a b = this.esp32DeviceInfoProvider.b();
            String str = b.b;
            if (str == null || str.isEmpty()) {
                return "192.168.1.100";
            }
            return b.b;
        } catch (Exception e) {
            e.getMessage();
            return "192.168.1.100";
        }
    }

    private String getDeviceId(DeviceInfo deviceInfo) {
        return getRealMacAddress();
    }

    private String getRealMacAddress() {
        try {
            String d = this.esp32DeviceInfoProvider.d();
            if (d != null && !d.isEmpty()) {
                return d;
            }
            return "3c:dc:75:63:35:48";
        } catch (Exception e) {
            e.getMessage();
            return "3c:dc:75:63:35:48";
        }
    }

    private String getWifiSSID() {
        try {
            c2.a b = this.esp32DeviceInfoProvider.b();
            String str = b.d;
            if (str == null || str.isEmpty()) {
                return "Unknown WiFi";
            }
            return b.d;
        } catch (Exception e) {
            e.getMessage();
            return "Unknown WiFi";
        }
    }

    private boolean sendActivationRequestSync(String str, DeviceInfo deviceInfo) throws IOException, JSONException {
        String jSONObject = deviceInfoToJson(deviceInfo).toString();
        Request.Builder url = new Request.Builder().url(str);
        url.addHeader("Device-Id", getDeviceId(deviceInfo));
        url.addHeader("Client-Id", getClientId(deviceInfo));
        url.addHeader("X-Language", "Chinese");
        url.addHeader("Content-Type", "application/json");
        Response execute = this.client.newCall(url.post(RequestBody.create(MediaType.parse("application/json"), jSONObject)).build()).execute();
        try {
            execute.code();
            execute.message();
            if (!execute.isSuccessful()) {
                if (execute.body() != null) {
                    execute.body().string();
                }
                execute.code();
                execute.close();
                return false;
            }
            String string = execute.body().string();
            if (string != null) {
                if (!string.isEmpty()) {
                    JSONObject jSONObject2 = new JSONObject(string);
                    boolean optBoolean = jSONObject2.optBoolean("success", false);
                    jSONObject2.toString();
                    execute.close();
                    return optBoolean;
                }
            }
            execute.close();
            return false;
        } catch (Throwable th) {
            if (execute != null) {
                execute.close();
            }
            throw th;
        }
    }

    public void checkActivationStatus(final DeviceInfo deviceInfo, final String str, final d0<u7> d0Var) {
        if (d0Var != null) {
            if (str == null || str.isEmpty()) {
                str = DEFAULT_OTA_URL;
            }
            this.executor.execute(new Runnable() {
                public final void run() {
                    OtaService.this.lambda$checkActivationStatus$0(deviceInfo, str, d0Var);
                }
            });
        }
    }

    public void dispose() {
        OkHttpClient okHttpClient = this.client;
        if (okHttpClient != null) {
            okHttpClient.dispatcher().cancelAll();
        }
    }

    public void lambda$checkActivationStatus$0(DeviceInfo deviceInfo, String str, d0 d0Var) {
        try {
            u7 checkActivationStatusSync = checkActivationStatusSync(deviceInfo, str);
            if (checkActivationStatusSync != null) {
                d0Var.onSuccess(checkActivationStatusSync);
            } else {
                d0Var.onError(new IOException("Failed to parse server response"));
            }
        } catch (Exception e) {
            d0Var.onError(e);
        }
    }

    public void lambda$sendActivationRequest$1(String str, DeviceInfo deviceInfo, d0 d0Var) {
        Log.d(TAG, "lambda$sendActivationRequest$1: URL=" + str);
        try {
            boolean sendActivationRequestSync = sendActivationRequestSync(str, deviceInfo);
            Log.d(TAG, "sendActivationRequestSync returned: " + sendActivationRequestSync);
            d0Var.onSuccess(Boolean.valueOf(sendActivationRequestSync));
        } catch (Exception e) {
            d0Var.onError(e);
        }
    }

    public void sendActivationRequest(String str, final DeviceInfo deviceInfo, final d0<Boolean> d0Var) {
        String str2;
        StringBuilder sb = new StringBuilder("sendActivationRequest called: otaUrl=");
        sb.append(str);
        sb.append(", callback=");
        if (d0Var != null) {
            str2 = "present";
        } else {
            str2 = "null";
        }
        sb.append(str2);
        Log.d(TAG, sb.toString());
        if (d0Var != null) {
            if (str == null || str.isEmpty()) {
                str = DEFAULT_OTA_URL;
            }
            if (!str.endsWith(MqttTopic.TOPIC_LEVEL_SEPARATOR)) {
                str = str.concat(MqttTopic.TOPIC_LEVEL_SEPARATOR);
            }
            final String x = y2.x(str, "activate");
            this.executor.execute(new Runnable() {
                public final void run() {
                    OtaService.this.lambda$sendActivationRequest$1(x, deviceInfo, d0Var);
                }
            });
        }
    }
}
