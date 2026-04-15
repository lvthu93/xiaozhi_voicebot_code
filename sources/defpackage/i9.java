package defpackage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONObject;

/* renamed from: i9  reason: default package */
public abstract class i9 {
    public final ExecutorService a;
    public int b;
    public final AtomicBoolean c;
    public final ExecutorService d;
    public c0 e;
    public String f;

    /* renamed from: i9$a */
    public class a implements Runnable {
        public final /* synthetic */ JSONObject c;

        public a(JSONObject jSONObject) {
            this.c = jSONObject;
        }

        public final void run() {
            i9.this.e.onIncomingJson(this.c);
        }
    }

    /* renamed from: i9$b */
    public class b implements Runnable {
        public final /* synthetic */ String c;

        public b(String str) {
            this.c = str;
        }

        public final void run() {
            i9.this.e.onNetworkError(this.c);
        }
    }

    /* renamed from: i9$c */
    public class c implements Runnable {
        public c() {
        }

        public final void run() {
            i9.this.e.onSessionEnded();
        }
    }

    public i9() {
        this((ExecutorService) null);
    }

    public abstract void a();

    public abstract void b();

    public void c() {
        if (this.c.compareAndSet(false, true)) {
            a();
            b();
            ExecutorService executorService = this.d;
            if (executorService != null && !executorService.isShutdown()) {
                executorService.shutdown();
            }
            ExecutorService executorService2 = this.a;
            if (executorService2 != null && !executorService2.isShutdown()) {
                executorService2.shutdown();
            }
            this.e = null;
        }
    }

    public final void d(JSONObject jSONObject) {
        if (!this.c.get() && this.e != null) {
            this.a.execute(new a(jSONObject));
        }
    }

    public final void e(String str) {
        if (!this.c.get() && this.e != null) {
            this.a.execute(new b(str));
        }
    }

    public final void f() {
        if (!this.c.get() && this.e != null) {
            this.a.execute(new c());
        }
    }

    public abstract void g(byte[] bArr);

    public abstract void h(String str, d0<Void> d0Var);

    public i9(ExecutorService executorService) {
        this.f = "";
        this.c = new AtomicBoolean(false);
        this.b = 1;
        this.d = executorService == null ? Executors.newSingleThreadExecutor(new k9()) : executorService;
        this.a = Executors.newSingleThreadExecutor(new o9());
    }
}
