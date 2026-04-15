package defpackage;

import androidx.core.app.NotificationCompat;
import defpackage.x7;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.json.JSONObject;

/* renamed from: a8  reason: default package */
public final class a8 implements Runnable {
    public final /* synthetic */ d0 c;
    public final /* synthetic */ x7 f;

    public a8(x7 x7Var, long j, x7.j jVar) {
        this.f = x7Var;
        this.c = jVar;
    }

    public final void run() {
        x7 x7Var = this.f;
        d0 d0Var = this.c;
        x7Var.getClass();
        System.currentTimeMillis();
        try {
            MqttAsyncClient mqttAsyncClient = x7Var.y;
            if (mqttAsyncClient == null || !mqttAsyncClient.isConnected()) {
                System.currentTimeMillis();
                if (d0Var != null) {
                    x7Var.a.execute(new c8(d0Var));
                    return;
                }
                return;
            }
            System.currentTimeMillis();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", "hello");
            jSONObject.put("version", 3);
            jSONObject.put(NotificationCompat.CATEGORY_TRANSPORT, "udp");
            JSONObject jSONObject2 = new JSONObject();
            boolean z = true;
            jSONObject2.put("mcp", true);
            jSONObject.put("features", jSONObject2);
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("format", "opus");
            jSONObject3.put("sample_rate", 16000);
            jSONObject3.put("channels", 1);
            jSONObject3.put("frame_duration", 60);
            jSONObject.put("audio_params", jSONObject3);
            System.currentTimeMillis();
            jSONObject.toString();
            x7Var.n = new CountDownLatch(1);
            x7Var.r(jSONObject);
            System.currentTimeMillis();
            boolean await = x7Var.n.await(10, TimeUnit.SECONDS);
            System.currentTimeMillis();
            if (d0Var != null) {
                if (!await || x7Var.ag.isEmpty()) {
                    z = false;
                }
                System.currentTimeMillis();
                x7Var.a.execute(new b8(d0Var, z));
            }
        } catch (Exception e) {
            System.currentTimeMillis();
            e.getMessage();
            if (d0Var != null) {
                x7Var.a.execute(new d8(d0Var));
            }
        }
    }
}
