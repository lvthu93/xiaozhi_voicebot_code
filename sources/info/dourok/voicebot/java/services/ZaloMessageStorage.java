package info.dourok.voicebot.java.services;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ZaloMessageStorage {
    private static final String KEY_MESSAGES = "messages";
    private static final int MAX_MESSAGES = 30;
    private static final String PREFS_NAME = "zalo_messages";
    private static final String TAG = "ZaloMessageStorage";
    private Context context;

    public ZaloMessageStorage(Context context2) {
        this.context = context2;
    }

    private List<JSONObject> getMessagesList() {
        String string = this.context.getSharedPreferences(PREFS_NAME, 0).getString(KEY_MESSAGES, "[]");
        ArrayList arrayList = new ArrayList();
        try {
            JSONArray jSONArray = new JSONArray(string);
            for (int i = 0; i < jSONArray.length(); i++) {
                arrayList.add(jSONArray.getJSONObject(i));
            }
        } catch (JSONException unused) {
        }
        return arrayList;
    }

    private void saveMessagesList(List<JSONObject> list) {
        JSONArray jSONArray = new JSONArray();
        for (JSONObject put : list) {
            jSONArray.put(put);
        }
        this.context.getSharedPreferences(PREFS_NAME, 0).edit().putString(KEY_MESSAGES, jSONArray.toString()).apply();
    }

    public void clearMessages() {
        this.context.getSharedPreferences(PREFS_NAME, 0).edit().remove(KEY_MESSAGES).apply();
    }

    public JSONArray getMessages() {
        try {
            List<JSONObject> messagesList = getMessagesList();
            JSONArray jSONArray = new JSONArray();
            for (JSONObject put : messagesList) {
                jSONArray.put(put);
            }
            return jSONArray;
        } catch (Exception unused) {
            return new JSONArray();
        }
    }

    public void saveMessage(String str, String str2, String str3, String str4, long j, String str5) {
        try {
            List<JSONObject> messagesList = getMessagesList();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("sender", str);
            jSONObject.put("text", str2);
            jSONObject.put("chat_id", str3);
            jSONObject.put("message_id", str4);
            jSONObject.put("time", j);
            if (str5 != null && !str5.isEmpty()) {
                jSONObject.put("audio_url", str5);
            }
            messagesList.add(0, jSONObject);
            if (messagesList.size() > 30) {
                messagesList = messagesList.subList(0, 30);
            }
            saveMessagesList(messagesList);
            messagesList.size();
        } catch (JSONException unused) {
        }
    }
}
