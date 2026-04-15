package defpackage;

import defpackage.a5;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: n5  reason: default package */
public final class n5 implements a5.h {
    public final /* synthetic */ a5 a;

    public n5(a5 a5Var) {
        this.a = a5Var;
    }

    public final Object a(JSONObject jSONObject) {
        a5 a5Var = this.a;
        a5Var.getClass();
        try {
            String trim = jSONObject.optString("city", "").trim();
            trim.isEmpty();
            CountDownLatch countDownLatch = new CountDownLatch(1);
            AtomicReference atomicReference = new AtomicReference((Object) null);
            a5Var.g.execute(new b5(a5Var, trim, countDownLatch, atomicReference));
            boolean await = countDownLatch.await(30, TimeUnit.SECONDS);
            Object obj = atomicReference.get();
            if (await && obj != null) {
                return obj;
            }
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("success", false);
            jSONObject2.put("message", "Hết thời gian chờ khi lấy thông tin thời tiết");
            return jSONObject2;
        } catch (Exception e) {
            try {
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("success", false);
                jSONObject3.put("message", "Lỗi lấy thông tin thời tiết: " + e.getMessage());
                return jSONObject3;
            } catch (JSONException e2) {
                throw new RuntimeException("Failed to create error response", e2);
            }
        }
    }
}
