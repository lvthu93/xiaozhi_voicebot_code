package defpackage;

import android.os.Handler;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import info.dourok.voicebot.java.data.model.DeviceInfo;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ye  reason: default package */
public final class ye extends i9 {
    public String g;
    public final OkHttpClient h;
    public DeviceInfo i;
    public CountDownLatch j;
    public final AtomicBoolean k = new AtomicBoolean(false);
    public final Handler l;
    public String m;
    public WebSocket n;

    /* renamed from: ye$a */
    public class a implements Runnable {
        public final /* synthetic */ String c;
        public final /* synthetic */ d0 f;

        public a(String str, d0 d0Var) {
            this.c = str;
            this.f = d0Var;
        }

        public final void run() {
            String str = this.c;
            d0 d0Var = this.f;
            ye yeVar = ye.this;
            ExecutorService executorService = yeVar.a;
            try {
                if (yeVar.n != null) {
                    if (yeVar.k.get()) {
                        if (yeVar.n.send(str)) {
                            if (d0Var != null) {
                                executorService.execute(new ie(d0Var));
                                return;
                            }
                            return;
                        } else if (d0Var != null) {
                            executorService.execute(new je(d0Var));
                            return;
                        } else {
                            return;
                        }
                    }
                }
                if (d0Var != null) {
                    executorService.execute(new ze(d0Var));
                }
            } catch (Exception e) {
                if (d0Var != null) {
                    executorService.execute(new ke(d0Var, e));
                }
            }
        }
    }

    public ye(ExecutorService executorService, Handler handler) {
        super(executorService);
        this.l = handler;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        TimeUnit timeUnit = TimeUnit.SECONDS;
        this.h = builder.connectTimeout(10, timeUnit).readTimeout(10, timeUnit).build();
    }

    public final void a() {
        WebSocket webSocket = this.n;
        if (webSocket != null) {
            webSocket.close(1000, "Normal closure");
            this.n = null;
        }
        this.k.set(false);
    }

    public final void b() {
        a();
        this.b = 1;
    }

    public final void c() {
        super.c();
        a();
        OkHttpClient okHttpClient = this.h;
        if (okHttpClient != null) {
            okHttpClient.dispatcher().executorService().shutdown();
        }
    }

    public final void g(byte[] bArr) {
        this.d.execute(new te(this, bArr));
    }

    public final void h(String str, d0<Void> d0Var) {
        this.d.execute(new a(str, d0Var));
    }

    public final void i(JSONObject jSONObject) {
        try {
            jSONObject.toString();
            if ("websocket".equals(jSONObject.optString(NotificationCompat.CATEGORY_TRANSPORT))) {
                JSONObject optJSONObject = jSONObject.optJSONObject("audio_params");
                if (optJSONObject != null) {
                    optJSONObject.optInt("sample_rate", -1);
                }
                String optString = jSONObject.optString("session_id");
                if (!optString.isEmpty()) {
                    this.f = optString;
                    if (!this.c.get() && this.e != null) {
                        this.a.execute(new l9(this, optString));
                    }
                }
                CountDownLatch countDownLatch = this.j;
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
            }
        } catch (Exception unused) {
            CountDownLatch countDownLatch2 = this.j;
            if (countDownLatch2 != null) {
                countDownLatch2.countDown();
            }
        }
    }

    /* renamed from: ye$b */
    public class b extends WebSocketListener {

        /* renamed from: ye$b$a */
        public class a implements Runnable {
            public final /* synthetic */ String c;

            public a(String str) {
                this.c = str;
            }

            public final void run() {
                String str = this.c;
                b bVar = b.this;
                bVar.getClass();
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    boolean equals = "hello".equals(jSONObject.optString("type"));
                    ye yeVar = ye.this;
                    if (equals) {
                        yeVar.i(jSONObject);
                        if (!yeVar.c.get() && yeVar.e != null) {
                            yeVar.a.execute(new m9(yeVar));
                            return;
                        }
                        return;
                    }
                    yeVar.d(jSONObject);
                } catch (JSONException unused) {
                }
            }
        }

        public b() {
        }

        public final void onClosed(WebSocket webSocket, int i, String str) {
            ye yeVar = ye.this;
            yeVar.k.set(false);
            yeVar.b = 1;
            AtomicBoolean atomicBoolean = yeVar.c;
            boolean z = atomicBoolean.get();
            ExecutorService executorService = yeVar.a;
            if (!z && yeVar.e != null) {
                executorService.execute(new h9(yeVar));
            }
            if (!atomicBoolean.get() && yeVar.e != null) {
                executorService.execute(new n9(yeVar));
            }
            yeVar.n = null;
        }

        public final void onClosing(WebSocket webSocket, int i, String str) {
        }

        public final void onFailure(WebSocket webSocket, Throwable th, Response response) {
            ye yeVar = ye.this;
            yeVar.k.set(false);
            th.getMessage();
            yeVar.b = 5;
            yeVar.e("Server not found: " + th.getMessage());
            yeVar.n = null;
            CountDownLatch countDownLatch = yeVar.j;
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        }

        public final void onMessage(WebSocket webSocket, String str) {
            ye yeVar = ye.this;
            yeVar.getClass();
            yeVar.d.execute(new a(str));
        }

        public final void onOpen(WebSocket webSocket, Response response) {
            CountDownLatch countDownLatch;
            ye yeVar = ye.this;
            yeVar.k.set(true);
            yeVar.b = 3;
            if (!yeVar.c.get() && yeVar.e != null) {
                yeVar.a.execute(new g9(yeVar));
            }
            yeVar.n = webSocket;
            if (webSocket != null) {
                try {
                    if (yeVar.k.get()) {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("type", "hello");
                        jSONObject.put("version", 1);
                        jSONObject.put(NotificationCompat.CATEGORY_TRANSPORT, "websocket");
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("mcp", true);
                        jSONObject.put("features", jSONObject2);
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject3.put("format", "opus");
                        jSONObject3.put("sample_rate", 16000);
                        jSONObject3.put("channels", 1);
                        jSONObject3.put("frame_duration", 60);
                        jSONObject.put("audio_params", jSONObject3);
                        if (!webSocket.send(jSONObject.toString()) && (countDownLatch = yeVar.j) != null) {
                            countDownLatch.countDown();
                        }
                    }
                } catch (JSONException unused) {
                    CountDownLatch countDownLatch2 = yeVar.j;
                    if (countDownLatch2 != null) {
                        countDownLatch2.countDown();
                    }
                }
            }
        }

        public final void onMessage(WebSocket webSocket, cq cqVar) {
            Log.v("WebSocketProtocol", "WebSocket binary message: " + cqVar.t() + " bytes");
            ye yeVar = ye.this;
            yeVar.getClass();
            byte[] w = cqVar.w();
            if (!yeVar.c.get() && yeVar.e != null) {
                yeVar.a.execute(new j9(yeVar, w));
            }
        }
    }
}
