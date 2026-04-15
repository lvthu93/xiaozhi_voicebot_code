package defpackage;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import info.dourok.voicebot.java.audio.OpusDecoder;
import info.dourok.voicebot.java.audio.OpusEncoder;
import info.dourok.voicebot.java.audio.b;
import info.dourok.voicebot.java.audio.e;
import info.dourok.voicebot.java.services.OtaService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/* renamed from: h1  reason: default package */
public final class h1 {
    public static h1 o;
    public final Context a;
    public b b;
    public e c;
    public k1 d;
    public c2 e;
    public a5 f;
    public OpusDecoder g;
    public OpusEncoder h;
    public OtaService i;
    public i9 j;
    public t9 k;
    public va l;
    public final Handler m = new Handler(Looper.getMainLooper());
    public final ExecutorService n = Executors.newSingleThreadExecutor(new a());

    /* renamed from: h1$a */
    public class a implements ThreadFactory {
        public final Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "VoiceBot-Network");
            thread.setDaemon(true);
            return thread;
        }
    }

    public h1(Context context) {
        this.a = context.getApplicationContext();
        context.getSharedPreferences("voice_bot_prefs", 0);
    }

    public static h1 d(Context context) {
        if (o == null) {
            synchronized (h1.class) {
                if (o == null) {
                    o = new h1(context);
                }
            }
        }
        return o;
    }

    public static String f() {
        Runtime runtime = Runtime.getRuntime();
        long j2 = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        return String.format("Memory: Used=%dMB, Free=%dMB, Total=%dMB", new Object[]{Long.valueOf(((j2 - freeMemory) / 1024) / 1024), Long.valueOf((freeMemory / 1024) / 1024), Long.valueOf((j2 / 1024) / 1024)});
    }

    public final e a() {
        if (this.c == null) {
            synchronized (this) {
                if (this.c == null) {
                    this.c = new e(h());
                }
            }
        }
        return this.c;
    }

    public final k1 b() {
        if (this.d == null) {
            synchronized (this) {
                if (this.d == null) {
                    this.d = new k1(this.a);
                }
            }
        }
        return this.d;
    }

    public final c2 c() {
        if (this.e == null) {
            synchronized (this) {
                if (this.e == null) {
                    this.e = new c2(this.a);
                }
            }
        }
        return this.e;
    }

    public final a5 e() {
        if (this.f == null) {
            synchronized (this) {
                if (this.f == null) {
                    this.f = new a5(this.a);
                }
            }
        }
        return this.f;
    }

    public final OpusDecoder g() {
        if (this.g == null) {
            synchronized (this) {
                if (this.g == null) {
                    this.g = new OpusDecoder(24000, 60);
                }
            }
        }
        return this.g;
    }

    public final OpusEncoder h() {
        if (this.h == null) {
            synchronized (this) {
                if (this.h == null) {
                    this.h = new OpusEncoder();
                }
            }
        }
        return this.h;
    }

    public final t9 i() {
        if (this.k == null) {
            synchronized (this) {
                if (this.k == null) {
                    this.k = new t9(this);
                }
            }
        }
        return this.k;
    }

    public final va j() {
        if (this.l == null) {
            synchronized (this) {
                if (this.l == null) {
                    this.l = new va(this.a);
                }
            }
        }
        return this.l;
    }
}
