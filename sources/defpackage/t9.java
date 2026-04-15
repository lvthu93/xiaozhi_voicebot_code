package defpackage;

import android.util.Log;
import defpackage.x7;
import info.dourok.voicebot.java.data.model.DeviceInfo;
import j$.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: t9  reason: default package */
public final class t9 implements c0 {
    public c0 a;
    public a0 b;
    public final h1 c;
    public final ExecutorService d;
    public x7.n e;
    public final AtomicReference<i9> f = new AtomicReference<>();
    public final AtomicReference<d> g = new AtomicReference<>();
    public final int h = 3;
    public final long i = 1000;
    public int j = 0;
    public boolean k = false;
    public boolean l = false;

    /* renamed from: t9$a */
    public class a implements d0<Void> {
        public final /* synthetic */ d a;

        public a(d dVar) {
            this.a = dVar;
        }

        public final void onError(Exception exc) {
            t9.this.d("MQTT connection failed: " + exc.getMessage());
        }

        public final void onSuccess(Object obj) {
            Void voidR = (Void) obj;
            t9 t9Var = t9.this;
            d dVar = this.a;
            t9Var.getClass();
            Objects.toString(dVar);
            synchronized (t9Var) {
                t9Var.k = false;
                t9Var.j = 0;
            }
            a0 a0Var = t9Var.b;
            if (a0Var != null) {
                a0Var.onConnectionEstablished();
            }
        }
    }

    /* renamed from: t9$b */
    public class b implements d0<Void> {
        public final /* synthetic */ d a;

        public b(d dVar) {
            this.a = dVar;
        }

        public final void onError(Exception exc) {
            t9.this.d("WebSocket connection failed: " + exc.getMessage());
        }

        public final void onSuccess(Object obj) {
            Void voidR = (Void) obj;
            t9 t9Var = t9.this;
            d dVar = this.a;
            t9Var.getClass();
            Objects.toString(dVar);
            synchronized (t9Var) {
                t9Var.k = false;
                t9Var.j = 0;
            }
            a0 a0Var = t9Var.b;
            if (a0Var != null) {
                a0Var.onConnectionEstablished();
            }
        }
    }

    /* renamed from: t9$c */
    public class c implements Runnable {
        public final /* synthetic */ long c;
        public final /* synthetic */ String f;

        public c(long j, String str) {
            this.c = j;
            this.f = str;
        }

        public final void run() {
            long j = this.c;
            String str = this.f;
            t9 t9Var = t9.this;
            t9Var.getClass();
            try {
                Thread.sleep(j);
                a0 a0Var = t9Var.b;
                if (a0Var != null) {
                    a0Var.onConnectionFailed(str);
                }
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /* renamed from: t9$d */
    public enum d {
        MQTT,
        WEBSOCKET
    }

    public t9(h1 h1Var) {
        this.c = h1Var;
        this.d = h1Var.n;
    }

    public final void a(d dVar, String str, Object obj, DeviceInfo deviceInfo) {
        i9 i9Var;
        h1 h1Var = this.c;
        String str2 = null;
        try {
            int ordinal = dVar.ordinal();
            if (ordinal == 0) {
                i9Var = new x7(h1Var.a, h1Var.e(), h1Var.n);
            } else if (ordinal != 1) {
                dVar.toString();
                i9Var = null;
            } else {
                i9Var = new ye(h1Var.n, h1Var.m);
            }
        } catch (Exception unused) {
            Objects.toString(dVar);
        }
        if (i9Var == null) {
            d("Failed to create protocol instance for: " + dVar);
            return;
        }
        d dVar2 = d.WEBSOCKET;
        if (dVar == dVar2 && (i9Var instanceof ye) && deviceInfo != null) {
            ye yeVar = (ye) i9Var;
            yeVar.i = deviceInfo;
            Log.d("WebSocketProtocol", "DeviceInfo set: ".concat("available"));
            if (obj instanceof String) {
                str2 = (String) obj;
            }
            yeVar.m = str;
            yeVar.g = str2;
            Log.d("WebSocketProtocol", "Connection params set - URL: " + str);
            Log.d("ProtocolManager", "WebSocket protocol configured with DeviceInfo");
        }
        i9Var.e = this;
        i9 andSet = this.f.getAndSet(i9Var);
        if (andSet != null) {
            try {
                andSet.b();
                andSet.c();
            } catch (Exception unused2) {
            }
        }
        x7.n nVar = this.e;
        if (nVar != null && (i9Var instanceof x7)) {
            ((x7) i9Var).l = nVar;
            Log.d("ProtocolManager", "Applied pending DeviceStateListener to protocol");
        }
        i9 i9Var2 = h1Var.j;
        if (i9Var2 != null) {
            i9Var2.b();
            h1Var.j.c();
        }
        h1Var.j = i9Var;
        Objects.toString(dVar);
        if (dVar == d.MQTT) {
            if (obj instanceof c7) {
                x7 x7Var = (x7) i9Var;
                c7 c7Var = (c7) obj;
                a aVar = new a(dVar);
                if (c7Var != null) {
                    x7Var.m = c7Var.b;
                    x7Var.j = c7Var.a;
                    x7Var.aj = c7Var.f;
                    x7Var.aa = c7Var.c;
                    x7Var.ab = c7Var.d;
                    x7Var.ah = c7Var.e;
                }
                x7Var.d.execute(new i8(x7Var, aVar));
                return;
            }
            d("Invalid MQTT configuration provided");
        } else if (dVar != dVar2) {
        } else {
            if (i9Var instanceof ye) {
                ye yeVar2 = (ye) i9Var;
                b bVar = new b(dVar);
                yeVar2.m = str;
                yeVar2.f = "client_session_" + System.currentTimeMillis();
                yeVar2.d.execute(new se(yeVar2, bVar));
                return;
            }
            d("Invalid WebSocket protocol instance");
        }
    }

    public final m1 b() {
        x7 c2 = c();
        if (c2 != null) {
            return c2.k;
        }
        return m1.IDLE;
    }

    public final x7 c() {
        i9 i9Var = this.f.get();
        if (i9Var instanceof x7) {
            return (x7) i9Var;
        }
        return null;
    }

    public final void d(String str) {
        synchronized (this) {
            this.k = false;
        }
        int i2 = this.j;
        if (i2 < this.h) {
            int i3 = i2 + 1;
            this.j = i3;
            this.d.execute(new c(this.i * ((long) (1 << (i3 - 1))), str));
            return;
        }
        a0 a0Var = this.b;
        if (a0Var != null) {
            a0Var.onConnectionFailed("Connection failed after " + this.h + " attempts: " + str);
        }
    }

    public final void e() {
        x7 c2 = c();
        if (c2 != null) {
            System.currentTimeMillis();
            int ordinal = c2.k.ordinal();
            if (ordinal == 0) {
                System.currentTimeMillis();
                c2.v();
                System.currentTimeMillis();
            } else if (ordinal == 1) {
                System.currentTimeMillis();
            } else if (ordinal == 2) {
                System.currentTimeMillis();
                System.currentTimeMillis();
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("session_id", c2.ag);
                    jSONObject.put("type", "goodbye");
                    c2.q(jSONObject);
                    c2.q = false;
                    c2.t(m1.IDLE);
                    zc zcVar = c2.ai;
                    c2.ai = null;
                    c2.ag = "";
                    if (zcVar != null) {
                        c2.d.execute(new h8(zcVar));
                    }
                    c2.f();
                    System.currentTimeMillis();
                } catch (JSONException e2) {
                    e2.getMessage();
                }
                System.currentTimeMillis();
            } else if (ordinal == 3) {
                System.currentTimeMillis();
                c2.p();
                System.currentTimeMillis();
            }
            System.currentTimeMillis();
        }
    }

    public final void onAudioChannelClosed() {
        c0 c0Var = this.a;
        if (c0Var != null) {
            c0Var.onAudioChannelClosed();
        }
    }

    public final void onAudioChannelOpened() {
        c0 c0Var = this.a;
        if (c0Var != null) {
            c0Var.onAudioChannelOpened();
        }
    }

    public final void onConnected() {
        c0 c0Var = this.a;
        if (c0Var != null) {
            c0Var.onConnected();
        }
    }

    public final void onDisconnected() {
        c0 c0Var = this.a;
        if (c0Var != null) {
            c0Var.onDisconnected();
        }
    }

    public final void onIncomingAudio(byte[] bArr) {
        c0 c0Var = this.a;
        if (c0Var != null) {
            c0Var.onIncomingAudio(bArr);
        }
    }

    public final void onIncomingJson(JSONObject jSONObject) {
        c0 c0Var = this.a;
        if (c0Var != null) {
            c0Var.onIncomingJson(jSONObject);
        }
    }

    public final void onNetworkError(String str) {
        c0 c0Var = this.a;
        if (c0Var != null) {
            c0Var.onNetworkError(str);
        }
    }

    public final void onSessionEnded() {
        c0 c0Var = this.a;
        if (c0Var != null) {
            c0Var.onSessionEnded();
        }
    }

    public final void onSessionStarted(String str) {
        c0 c0Var = this.a;
        if (c0Var != null) {
            c0Var.onSessionStarted(str);
        }
    }
}
