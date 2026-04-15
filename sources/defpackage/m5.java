package defpackage;

import defpackage.a5;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONObject;

/* renamed from: m5  reason: default package */
public final class m5 implements a5.h {
    public final /* synthetic */ a5 a;

    public m5(a5 a5Var) {
        this.a = a5Var;
    }

    public final Object a(JSONObject jSONObject) {
        String str;
        boolean z;
        a5 a5Var = this.a;
        a5Var.getClass();
        try {
            String lowerCase = jSONObject.optString("action", "").trim().toLowerCase();
            String trim = jSONObject.optString("query", "").trim();
            if (!"c".equals(lowerCase)) {
                z = false;
                if ("chuyensau".equals(lowerCase)) {
                    if (trim.isEmpty()) {
                        return cg.f("Thiếu query (cần từ khóa tìm kiếm khi action=chuyensau)");
                    }
                    str = cg.c("chuyensau", trim);
                } else if (!"q".equals(lowerCase)) {
                    return cg.f("Action không hợp lệ. Phải là 'q', 'c', hoặc 'chuyensau'");
                } else {
                    if (trim.isEmpty()) {
                        trim = "news";
                    }
                    str = cg.c("q", trim);
                }
            } else if (trim.isEmpty()) {
                return cg.f("Thiếu query (cần số bài viết 1-30 khi action=c)");
            } else {
                try {
                    int parseInt = Integer.parseInt(trim);
                    if (parseInt <= 0 || parseInt > 30) {
                        return cg.f("Số bài viết phải từ 1 đến 30");
                    }
                    str = cg.c("c", trim);
                    z = true;
                } catch (NumberFormatException unused) {
                    return cg.f("Query phải là số (1-30) khi action=c");
                }
            }
            CountDownLatch countDownLatch = new CountDownLatch(1);
            AtomicReference atomicReference = new AtomicReference((Object) null);
            a5Var.g.execute(new c5(a5Var, str, countDownLatch, atomicReference));
            boolean await = countDownLatch.await(20, TimeUnit.SECONDS);
            String str2 = (String) atomicReference.get();
            if (await && str2 != null) {
                if (!str2.isEmpty()) {
                    return cg.l(str2, z);
                }
            }
            return cg.f("Không thể kết nối đến máy chủ tin tức");
        } catch (Exception e) {
            return cg.f("Lỗi lấy tin tức: " + e.getMessage());
        }
    }
}
