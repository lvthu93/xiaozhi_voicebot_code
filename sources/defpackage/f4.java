package defpackage;

import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

/* renamed from: f4  reason: default package */
public final class f4 {
    public static Context c = null;
    public static f4 d = null;
    public static volatile boolean e = false;
    public Class<?> a;
    public Object b;

    public f4() {
        new Handler(Looper.getMainLooper());
    }

    public static synchronized f4 a() {
        f4 f4Var;
        synchronized (f4.class) {
            synchronized (f4.class) {
                if (d == null) {
                    d = new f4();
                }
                f4Var = d;
            }
        }
        return f4Var;
    }

    public static void e(boolean z) {
        e = z;
        f4 f4Var = d;
        if (f4Var == null || c == null) {
            Context context = c;
            if (context != null) {
                context.getSharedPreferences("led_controller_prefs", 0).edit().putBoolean("led_enabled", z).apply();
                return;
            }
            return;
        }
        f4Var.getClass();
        Context context2 = c;
        if (context2 != null) {
            try {
                context2.getSharedPreferences("led_controller_prefs", 0).edit().putBoolean("led_enabled", z).apply();
            } catch (Exception unused) {
            }
        }
    }

    public final void b(Context context) {
        if (context != null) {
            Context applicationContext = context.getApplicationContext();
            c = applicationContext;
            if (applicationContext != null) {
                try {
                    e = applicationContext.getSharedPreferences("led_controller_prefs", 0).getBoolean("led_enabled", false);
                } catch (Exception unused) {
                    e = false;
                }
            }
        }
        try {
            IBinder iBinder = (IBinder) Class.forName("android.os.ServiceManager").getMethod("getService", new Class[]{String.class}).invoke((Object) null, new Object[]{"msgcenter"});
            if (iBinder != null) {
                Object invoke = Class.forName("android.os.IMessageCenterService$Stub").getMethod("asInterface", new Class[]{IBinder.class}).invoke((Object) null, new Object[]{iBinder});
                this.b = invoke;
                if (invoke != null) {
                    this.a = invoke.getClass();
                }
            }
        } catch (Exception unused2) {
        }
    }

    public final void c(int i) {
        Class<?> cls;
        if (this.b != null && (cls = this.a) != null) {
            try {
                Class cls2 = Integer.TYPE;
                cls.getMethod("sendMsg", new Class[]{cls2, cls2, cls2}).invoke(this.b, new Object[]{4096, Integer.valueOf(i), 0});
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
    }

    public final void d() {
        if (e) {
            c(505);
            c(309);
            try {
                Thread.sleep((long) 100);
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
            c(503);
            return;
        }
        c(504);
    }
}
