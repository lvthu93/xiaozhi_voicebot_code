package defpackage;

import android.content.Context;
import android.media.AudioManager;
import androidx.core.app.NotificationCompat;
import defpackage.a5;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: j5  reason: default package */
public final class j5 implements a5.h {
    public final /* synthetic */ a5 a;

    public j5(a5 a5Var) {
        this.a = a5Var;
    }

    public final Object a(JSONObject jSONObject) {
        AudioManager audioManager;
        a5 a5Var = this.a;
        a5Var.getClass();
        try {
            int optInt = jSONObject.optInt("volume", -1);
            if (optInt < 0 || optInt > 100) {
                throw new IllegalArgumentException("Volume must be between 0 and 100");
            }
            Context context = a5Var.a;
            if (!(context == null || (audioManager = (AudioManager) context.getSystemService("audio")) == null)) {
                audioManager.setStreamVolume(3, (audioManager.getStreamMaxVolume(3) * optInt) / 100, 0);
            }
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("volume", optInt);
            jSONObject2.put(NotificationCompat.CATEGORY_STATUS, "success");
            return jSONObject2;
        } catch (JSONException e) {
            throw new RuntimeException("Failed to set volume", e);
        }
    }
}
