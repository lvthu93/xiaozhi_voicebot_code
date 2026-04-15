package defpackage;

import org.json.JSONObject;

/* renamed from: p9  reason: default package */
public final class p9 implements Runnable {
    public final /* synthetic */ String c;
    public final /* synthetic */ d0 f = null;
    public final /* synthetic */ i9 g;

    public p9(i9 i9Var, String str) {
        this.g = i9Var;
        this.c = str;
    }

    public final void run() {
        String str = this.c;
        d0 d0Var = this.f;
        i9 i9Var = this.g;
        i9Var.getClass();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("session_id", i9Var.f);
            jSONObject.put("type", "listen");
            jSONObject.put("state", "detect");
            jSONObject.put("text", str);
            i9Var.h(jSONObject.toString(), d0Var);
        } catch (Exception e) {
            if (d0Var != null) {
                i9Var.a.execute(new q9(d0Var, e));
            }
        }
    }
}
