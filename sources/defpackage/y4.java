package defpackage;

import defpackage.a5;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: y4  reason: default package */
public final class y4 implements a5.h {
    public final /* synthetic */ a5 a;

    public y4(a5 a5Var) {
        this.a = a5Var;
    }

    public final Object a(JSONObject jSONObject) {
        this.a.getClass();
        try {
            f4.e(false);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("success", true);
            jSONObject2.put("message", "Đèn LED đã được tắt. Khi về chế độ idle, LED sẽ không bật lại / LED has been turned off. When entering idle mode, LED will not turn on again / LED已关闭。进入空闲模式时，LED将不会再次打开");
            jSONObject2.put("led_enabled", false);
            return jSONObject2;
        } catch (JSONException e) {
            throw new RuntimeException("Failed to control LED", e);
        }
    }
}
