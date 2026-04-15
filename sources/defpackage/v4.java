package defpackage;

import android.os.Handler;
import android.os.Looper;
import defpackage.a5;
import info.dourok.voicebot.java.audio.MusicPlayer;
import info.dourok.voicebot.java.audio.j;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: v4  reason: default package */
public final class v4 implements a5.h {
    public final /* synthetic */ a5 a;

    public v4(a5 a5Var) {
        this.a = a5Var;
    }

    public final Object a(JSONObject jSONObject) {
        JSONObject jSONObject2;
        a5 a5Var = this.a;
        a5Var.getClass();
        String str = "";
        try {
            String trim = jSONObject.optString("station_name", str).trim();
            if (!trim.isEmpty()) {
                if (a5Var.h == null && a5Var.a != null) {
                    CountDownLatch countDownLatch = new CountDownLatch(1);
                    new Handler(Looper.getMainLooper()).post(new e5(a5Var, countDownLatch));
                    countDownLatch.await(2, TimeUnit.SECONDS);
                }
                j jVar = a5Var.h;
                if (jVar == null) {
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("success", false);
                    jSONObject3.put("message", "广播播放器不可用");
                    return jSONObject3;
                }
                j.b a2 = jVar.a(trim);
                if (a2 == null) {
                    jSONObject2 = new JSONObject();
                    jSONObject2.put("success", false);
                    jSONObject2.put("message", "未找到电台: ".concat(trim));
                    jSONObject2.put("available_stations", "VOV1, VOV2, VOV3, VOV5, VOVGT (Giao thông Hà Nội), VOVGT_HCM, VOV_ENGLISH, VOV_MEKONG, VOV_MIENTRUNG, VOV_TAYBAC, VOV_DONGBAC, VOV_TAYNGUYEN");
                    return jSONObject2;
                }
                String str2 = a2.c;
                Iterator it = a5Var.h.d.entrySet().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Map.Entry entry = (Map.Entry) it.next();
                    if (entry.getValue() == a2) {
                        str = (String) entry.getKey();
                        break;
                    }
                }
                MusicPlayer musicPlayer = a5Var.f;
                if (musicPlayer != null && musicPlayer.isPlaying()) {
                    new Handler(Looper.getMainLooper()).post(new f5(a5Var));
                    Thread.sleep(100);
                }
                CountDownLatch countDownLatch2 = new CountDownLatch(1);
                AtomicBoolean atomicBoolean = new AtomicBoolean(false);
                new Handler(Looper.getMainLooper()).post(new g5(a5Var, atomicBoolean, trim, countDownLatch2));
                countDownLatch2.await(5, TimeUnit.SECONDS);
                JSONObject jSONObject4 = new JSONObject();
                if (atomicBoolean.get()) {
                    jSONObject4.put("success", true);
                    jSONObject4.put("message", "正在播放广播: " + str2);
                    jSONObject4.put("station_name", str2);
                    jSONObject4.put("station_key", str);
                    jSONObject4.put("category", a2.a);
                    jSONObject4.put("description", a2.b);
                    return jSONObject4;
                }
                jSONObject4.put("success", false);
                jSONObject4.put("message", "无法播放广播: " + str2);
                return jSONObject4;
            }
            throw new IllegalArgumentException("station_name is required");
        } catch (Exception e) {
            try {
                jSONObject2 = new JSONObject();
                jSONObject2.put("success", false);
                jSONObject2.put("message", "播放广播失败: " + e.getMessage());
            } catch (JSONException e2) {
                throw new RuntimeException("Failed to create error response", e2);
            }
        }
    }
}
