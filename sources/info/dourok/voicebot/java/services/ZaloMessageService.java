package info.dourok.voicebot.java.services;

import android.content.Context;
import info.dourok.voicebot.java.services.ZaloAuthService;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ZaloMessageService {
    private static final String KEY_BOT_TOKEN = "zalo_bot_token";
    private static final String PREFS_NAME = "zalo_config";
    private static final String TAG = "ZaloMessageService";
    private static final String ZALO_API_BASE = "https://bot-api.zapps.me/bot";
    private ZaloAuthService authService;
    private Context context;
    private OkHttpClient httpClient = oa.c();

    public static class ZaloListResult {
        public String message;
        public boolean success;
        public JSONArray zaloList;

        public ZaloListResult(boolean z, JSONArray jSONArray, String str) {
            this.success = z;
            this.zaloList = jSONArray;
            this.message = str;
        }
    }

    public static class ZaloSendMessageResult {
        public String date;
        public String message;
        public String messageId;
        public String responseJson;
        public int statusCode;
        public boolean success;

        public ZaloSendMessageResult(boolean z, int i, String str, String str2, String str3, String str4) {
            this.success = z;
            this.statusCode = i;
            this.messageId = str;
            this.date = str2;
            this.responseJson = str3;
            this.message = str4;
        }
    }

    public ZaloMessageService(Context context2) {
        this.context = context2;
        this.authService = new ZaloAuthService(context2);
    }

    public ZaloListResult getZaloList() {
        try {
            ZaloAuthService.ZaloAuthResult authenticate = this.authService.authenticate(false);
            if (!authenticate.success) {
                return new ZaloListResult(false, new JSONArray(), authenticate.message);
            }
            JSONArray jSONArray = authenticate.zaloList;
            if (jSONArray == null) {
                jSONArray = new JSONArray();
            }
            return new ZaloListResult(true, jSONArray, "Thành công");
        } catch (Exception e) {
            JSONArray jSONArray2 = new JSONArray();
            return new ZaloListResult(false, jSONArray2, "Lỗi: " + e.getMessage());
        }
    }

    public ZaloSendMessageResult sendMessage(String str, String str2) {
        String str3;
        String str4 = str;
        String str5 = str2;
        if (str4 == null || str.isEmpty() || str5 == null || str2.isEmpty()) {
            return new ZaloSendMessageResult(false, 0, "", "", "", "Thiếu chat_id hoặc text");
        }
        String string = this.context.getSharedPreferences(PREFS_NAME, 0).getString(KEY_BOT_TOKEN, "");
        if (string.isEmpty()) {
            return new ZaloSendMessageResult(false, 0, "", "", "", "Bot token chưa được cấu hình");
        }
        String j = y2.j(ZALO_API_BASE, string, "/sendMessage");
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("chat_id", str4);
            jSONObject.put("text", str5);
            jSONObject.toString();
            Response execute = this.httpClient.newCall(new Request.Builder().url(j).post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jSONObject.toString())).addHeader("Content-Type", "application/json; charset=utf-8").build()).execute();
            int code = execute.code();
            if (execute.body() != null) {
                str3 = execute.body().string();
            } else {
                str3 = "";
            }
            if (!execute.isSuccessful()) {
                return new ZaloSendMessageResult(false, code, "", "", str3, "HTTP error: " + code);
            }
            JSONObject jSONObject2 = new JSONObject(str3);
            boolean optBoolean = jSONObject2.optBoolean("ok", false);
            JSONObject optJSONObject = jSONObject2.optJSONObject("result");
            if (optBoolean && optJSONObject != null) {
                return new ZaloSendMessageResult(true, code, optJSONObject.optString("message_id", ""), optJSONObject.optString("date", ""), str3, "Thành công");
            }
            return new ZaloSendMessageResult(false, jSONObject2.optInt("error_code", code), "", "", str3, jSONObject2.optString("description", "Thất bại"));
        } catch (JSONException e) {
            return new ZaloSendMessageResult(false, 0, "", "", "", "Lỗi parse JSON: " + e.getMessage());
        } catch (Exception e2) {
            return new ZaloSendMessageResult(false, 0, "", "", "", "Lỗi: " + e2.getMessage());
        }
    }
}
