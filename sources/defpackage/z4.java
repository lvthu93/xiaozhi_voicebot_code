package defpackage;

import defpackage.a5;
import info.dourok.voicebot.java.services.ZaloMessageStorage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: z4  reason: default package */
public final class z4 implements a5.h {
    public final /* synthetic */ a5 a;

    public z4(a5 a5Var) {
        this.a = a5Var;
    }

    public final Object a(JSONObject jSONObject) throws Exception {
        a5 a5Var = this.a;
        a5Var.getClass();
        try {
            String optString = jSONObject.optString("action", "sendmess");
            if ("readmessage".equals(optString)) {
                ZaloMessageStorage zaloMessageStorage = new ZaloMessageStorage(a5Var.a);
                JSONArray messages = zaloMessageStorage.getMessages();
                JSONObject jSONObject2 = new JSONObject();
                if (messages.length() == 0) {
                    jSONObject2.put("success", false);
                    jSONObject2.put("message", "Không có tin nhắn Zalo nào");
                    jSONObject2.put("data", new JSONArray());
                    return jSONObject2;
                }
                jSONObject2.put("success", true);
                jSONObject2.put("message", "Đã đọc " + messages.length() + " tin nhắn Zalo");
                jSONObject2.put("count", messages.length());
                jSONObject2.put("data", messages);
                zaloMessageStorage.clearMessages();
                return jSONObject2;
            }
            CountDownLatch countDownLatch = new CountDownLatch(1);
            AtomicReference atomicReference = new AtomicReference((Object) null);
            s sVar = r2;
            s sVar2 = new s(a5Var, optString, jSONObject, atomicReference, countDownLatch, 1);
            a5Var.g.execute(sVar);
            if (!countDownLatch.await(30, TimeUnit.SECONDS)) {
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("success", false);
                jSONObject3.put("message", "Timeout khi thực hiện yêu cầu Zalo");
                return jSONObject3;
            }
            JSONObject jSONObject4 = (JSONObject) atomicReference.get();
            if (jSONObject4 == null) {
                jSONObject4 = new JSONObject();
                jSONObject4.put("success", false);
                jSONObject4.put("message", "Không nhận được kết quả");
            }
            return jSONObject4;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            try {
                JSONObject jSONObject5 = new JSONObject();
                jSONObject5.put("success", false);
                jSONObject5.put("message", "Bị gián đoạn: " + e.getMessage());
                return jSONObject5;
            } catch (JSONException e2) {
                throw new RuntimeException("Failed to execute Zalo message", e2);
            }
        } catch (JSONException e3) {
            try {
                JSONObject jSONObject6 = new JSONObject();
                jSONObject6.put("success", false);
                jSONObject6.put("message", "Lỗi: " + e3.getMessage());
                return jSONObject6;
            } catch (JSONException e4) {
                throw new RuntimeException("Failed to execute Zalo message", e4);
            }
        }
    }
}
