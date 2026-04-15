package info.dourok.voicebot.java.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import j$.util.DesugarTimeZone;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

public class ZaloAuthService {
    private static final String KEY_BOT_TOKEN = "zalo_bot_token";
    private static final String KEY_MQTT_PASSWORD = "zalo_mqtt_password";
    private static final String KEY_MQTT_URL = "zalo_mqtt_url";
    private static final String KEY_TIKTOK_USER = "tiktok_user";
    private static final String PREFS_NAME = "zalo_config";
    private static final String SECRET_KEY = "F9FEECDCFCEA529828EF57968589B";
    private static final String SERVER_URL = "https://me.ai-box-plus.com/api/active";
    private static final String TAG = "ZaloAuthService";
    private final Context context;
    private final OkHttpClient httpClient;

    public ZaloAuthService(Context context2) {
        this.context = context2;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        TimeUnit timeUnit = TimeUnit.SECONDS;
        this.httpClient = builder.connectTimeout(10, timeUnit).readTimeout(10, timeUnit).writeTimeout(10, timeUnit).build();
    }

    private void saveConfig(String str, String str2, String str3, String str4, boolean z, boolean z2) {
        SharedPreferences.Editor edit = this.context.getSharedPreferences(PREFS_NAME, 0).edit();
        if (!str2.isEmpty()) {
            edit.putString(KEY_MQTT_URL, str2);
        }
        if (!str3.isEmpty()) {
            edit.putString(KEY_MQTT_PASSWORD, str3);
        }
        if (!z || str.isEmpty()) {
            edit.remove(KEY_BOT_TOKEN);
        } else {
            edit.putString(KEY_BOT_TOKEN, str);
        }
        if (!z2 || str4.isEmpty()) {
            edit.remove(KEY_TIKTOK_USER);
        } else {
            edit.putString(KEY_TIKTOK_USER, str4);
        }
        edit.apply();
    }

    public ZaloAuthResult authenticate(boolean z) {
        boolean z2;
        boolean z3;
        int i = 0;
        while (i < 3) {
            if (i > 0) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return new ZaloAuthResult(false, "Bị gián đoạn: " + e.getMessage(), (String) null, (String) null, (String) null, (JSONArray) null, (String) null);
                } catch (Exception e2) {
                    i++;
                    if (i >= 3) {
                        return new ZaloAuthResult(false, "Lỗi sau 3 lần thử: " + e2.getMessage(), (String) null, (String) null, (String) null, (JSONArray) null, (String) null);
                    }
                }
            }
            String d = new c2(this.context).d();
            if (d != null) {
                if (!d.isEmpty()) {
                    String generateActiveKeyHex = generateActiveKeyHex(d);
                    if (!generateActiveKeyHex.isEmpty()) {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("k", generateActiveKeyHex);
                        jSONObject.put("t", "active");
                        Response execute = this.httpClient.newCall(new Request.Builder().url(SERVER_URL).post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jSONObject.toString())).addHeader("Content-Type", "application/json; charset=utf-8").build()).execute();
                        if (!execute.isSuccessful()) {
                            execute.code();
                        } else {
                            JSONObject jSONObject2 = new JSONObject(execute.body().string());
                            boolean optBoolean = jSONObject2.optBoolean("success", false);
                            boolean optBoolean2 = jSONObject2.optBoolean(NotificationCompat.CATEGORY_STATUS, false);
                            if (!optBoolean || !optBoolean2) {
                                jSONObject2.optString("message", "Xác thực thất bại");
                            } else {
                                if (jSONObject2.has("token")) {
                                    if (jSONObject2.has("mqtt_url")) {
                                        jSONObject2.optString("token", "");
                                        String optString = jSONObject2.optString("mqtt_url", "");
                                        String optString2 = jSONObject2.optString("password", "");
                                        String optString3 = jSONObject2.optString("ztoken", "");
                                        String optString4 = jSONObject2.optString("user_tiktok", "");
                                        JSONArray optJSONArray = jSONObject2.optJSONArray("zalo_list");
                                        if (optJSONArray == null) {
                                            optJSONArray = new JSONArray();
                                        }
                                        JSONArray jSONArray = optJSONArray;
                                        JSONArray optJSONArray2 = jSONObject2.optJSONArray("types");
                                        if (optJSONArray2 != null) {
                                            boolean z4 = false;
                                            boolean z5 = false;
                                            for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                                                String optString5 = optJSONArray2.optString(i2, "");
                                                if ("zalo".equals(optString5)) {
                                                    z4 = true;
                                                } else if ("tiktok".equals(optString5)) {
                                                    z5 = true;
                                                }
                                            }
                                            z3 = z4;
                                            z2 = z5;
                                        } else {
                                            z3 = false;
                                            z2 = false;
                                        }
                                        if (optJSONArray2 != null) {
                                            optJSONArray2.toString();
                                        }
                                        if (z) {
                                            saveConfig(optString3, optString, optString2, optString4, z3, z2);
                                        }
                                        return new ZaloAuthResult(true, "Xác thực thành công", optString3, optString, optString2, jSONArray, optString4);
                                    }
                                }
                                jSONObject2.optString("message", "Thiếu token hoặc mqtt_url trong response");
                            }
                        }
                    }
                    i++;
                }
            }
            return new ZaloAuthResult(false, "Không lấy được MAC address", (String) null, (String) null, (String) null, (JSONArray) null, (String) null);
        }
        return new ZaloAuthResult(false, "Xác thực thất bại sau 3 lần thử", (String) null, (String) null, (String) null, (JSONArray) null, (String) null);
    }

    public String generateActiveKeyHex(String str) {
        int i;
        try {
            String lowerCase = str.toLowerCase();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss", Locale.US);
            simpleDateFormat.setTimeZone(DesugarTimeZone.getTimeZone("GMT+7"));
            String str2 = lowerCase + "_@_@" + simpleDateFormat.format(new Date());
            String str3 = str2;
            while (true) {
                if (str3.length() >= 32) {
                    break;
                }
                str3 = str3 + "*";
            }
            if (str3.length() > 32) {
                str3 = str3.substring(0, 32);
            }
            Log.d(TAG, "activeKeyRaw: " + str2 + " (length: " + str2.length() + ")");
            Log.d(TAG, "activeKeyPad: " + str3 + " (length: " + str3.length() + ")");
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            byte[] bytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
            byte[] digest = instance.digest(bytes);
            Log.d(TAG, "SECRET_KEY length: " + bytes.length + " bytes");
            Log.d(TAG, "AES key length: " + digest.length + " bytes");
            byte[] bArr = new byte[48];
            System.arraycopy(str3.getBytes(StandardCharsets.UTF_8), 0, bArr, 0, 32);
            for (i = 32; i < 48; i++) {
                bArr[i] = 16;
            }
            Cipher instance2 = Cipher.getInstance("AES/CBC/NoPadding");
            instance2.init(1, new SecretKeySpec(digest, "AES"), new IvParameterSpec(new byte[16]));
            byte[] doFinal = instance2.doFinal(bArr);
            StringBuilder sb = new StringBuilder();
            int length = doFinal.length;
            for (int i2 = 0; i2 < length; i2++) {
                sb.append(String.format("%02X", new Object[]{Byte.valueOf(doFinal[i2])}));
            }
            return sb.toString();
        } catch (Exception unused) {
            return "";
        }
    }

    public String getBotToken() {
        return this.context.getSharedPreferences(PREFS_NAME, 0).getString(KEY_BOT_TOKEN, "");
    }

    public String getMqttPassword() {
        return this.context.getSharedPreferences(PREFS_NAME, 0).getString(KEY_MQTT_PASSWORD, "");
    }

    public String getMqttUrl() {
        return this.context.getSharedPreferences(PREFS_NAME, 0).getString(KEY_MQTT_URL, "");
    }

    public String getTiktokUser() {
        return this.context.getSharedPreferences(PREFS_NAME, 0).getString(KEY_TIKTOK_USER, "");
    }

    public static class ZaloAuthResult {
        public final String message;
        public final String mqttUrl;
        public final String password;
        public final boolean success;
        public final String userTiktok;
        public final JSONArray zaloList;
        public final String ztoken;

        public ZaloAuthResult(boolean z, String str, String str2, String str3, String str4, JSONArray jSONArray, String str5) {
            this.success = z;
            this.message = str;
            this.ztoken = str2;
            this.mqttUrl = str3;
            this.password = str4;
            this.zaloList = jSONArray;
            this.userTiktok = str5 == null ? "" : str5;
        }

        public ZaloAuthResult(boolean z, String str, String str2, String str3, String str4, JSONArray jSONArray) {
            this(z, str, str2, str3, str4, jSONArray, "");
        }

        public ZaloAuthResult(boolean z, String str, String str2, String str3, String str4) {
            this(z, str, str2, str3, str4, (JSONArray) null, "");
        }
    }
}
