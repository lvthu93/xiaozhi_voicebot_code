package defpackage;

import android.os.Handler;
import android.os.Looper;
import defpackage.a5;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: w4  reason: default package */
public final class w4 implements a5.h {
    public final /* synthetic */ a5 a;

    public w4(a5 a5Var) {
        this.a = a5Var;
    }

    public final Object a(JSONObject jSONObject) {
        a5 a5Var = this.a;
        a5Var.getClass();
        try {
            if (a5Var.h != null) {
                new Handler(Looper.getMainLooper()).post(new h5(a5Var));
            }
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("success", true);
            jSONObject2.put("message", "广播已停止");
            return jSONObject2;
        } catch (JSONException e) {
            throw new RuntimeException("Failed to stop radio", e);
        }
    }
}
