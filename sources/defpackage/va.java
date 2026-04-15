package defpackage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import info.dourok.voicebot.java.data.model.ServerFormData;
import org.json.JSONObject;

/* renamed from: va  reason: default package */
public final class va {
    public final SharedPreferences a;
    public c7 b = null;

    public va(Context context) {
        this.a = context.getApplicationContext().getSharedPreferences("voicebot_settings", 0);
    }

    public final void a(d0<Object> d0Var) {
        String str;
        String str2;
        if (this.b != null) {
            Log.d("SettingsRepository", "✓ Using Xiaozhi MQTT config from RAM cache (endpoint: " + this.b.b + ")");
            d0Var.onSuccess(this.b);
            return;
        }
        SharedPreferences sharedPreferences = this.a;
        String string = sharedPreferences.getString("mqtt_host", (String) null);
        String string2 = sharedPreferences.getString("mqtt_client_id", (String) null);
        String string3 = sharedPreferences.getString("mqtt_username", (String) null);
        String string4 = sharedPreferences.getString("mqtt_password", (String) null);
        String string5 = sharedPreferences.getString("mqtt_publish_topic", (String) null);
        String string6 = sharedPreferences.getString("mqtt_subscribe_topic", (String) null);
        if (string == null || string2 == null || string3 == null || string4 == null) {
            try {
                String string7 = sharedPreferences.getString("server_form_data", (String) null);
                if (string7 == null || string7.trim().isEmpty()) {
                    d0Var.onSuccess(null);
                } else {
                    d0Var.onSuccess(ServerFormData.a(new JSONObject(string7)));
                }
            } catch (Exception e) {
                d0Var.onError(e);
            }
        } else {
            Log.d("SettingsRepository", "Found MQTT config in SharedPreferences (legacy)");
            if (string5 != null) {
                str = string5;
            } else {
                str = "device-server";
            }
            if (string6 != null) {
                str2 = string6;
            } else {
                str2 = "null";
            }
            d0Var.onSuccess(new c7(string, string2, string3, string4, str, str2));
        }
    }
}
