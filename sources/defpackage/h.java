package defpackage;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import j$.util.Objects;
import java.io.IOException;
import java.util.ArrayList;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* renamed from: h  reason: default package */
public final class h {
    public static h i;
    public Context a;
    public d b;
    public MediaPlayer c;
    public Handler d;
    public HandlerThread e;
    public boolean f = false;
    public int g = 0;
    public final ArrayList h = new ArrayList();

    /* renamed from: h$a */
    public class a implements Runnable {
        public a() {
        }

        public final void run() {
            d dVar = h.this.b;
            if (dVar != null) {
                dVar.onPlaybackComplete();
            }
        }
    }

    /* renamed from: h$b */
    public class b implements Runnable {
        public final /* synthetic */ String c;

        public b(String str, String str2) {
            this.c = str;
        }

        public final void run() {
            String str = this.c;
            h hVar = h.this;
            hVar.getClass();
            try {
                MediaPlayer mediaPlayer = hVar.c;
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                    hVar.c = null;
                }
                hVar.c = new MediaPlayer();
                AssetFileDescriptor openFd = hVar.a.getAssets().openFd(str);
                hVar.c.setDataSource(openFd.getFileDescriptor(), openFd.getStartOffset(), openFd.getLength());
                openFd.close();
                hVar.c.setOnCompletionListener(new i(hVar));
                hVar.c.setOnErrorListener(new j(hVar));
                hVar.c.prepare();
                hVar.c.start();
            } catch (IOException e) {
                e.getMessage();
                hVar.g++;
                hVar.c();
            } catch (Exception e2) {
                e2.getMessage();
                hVar.g++;
                hVar.c();
            }
        }
    }

    /* renamed from: h$c */
    public class c implements Runnable {
        public c() {
        }

        public final void run() {
            h hVar = h.this;
            MediaPlayer mediaPlayer = hVar.c;
            if (mediaPlayer != null) {
                try {
                    if (mediaPlayer.isPlaying()) {
                        hVar.c.stop();
                    }
                    hVar.c.release();
                } catch (Exception e) {
                    e.getMessage();
                }
                hVar.c = null;
            }
        }
    }

    /* renamed from: h$d */
    public interface d {
        void onPlaybackComplete();

        void onPlaybackStarted();
    }

    public static synchronized h b() {
        h hVar;
        synchronized (h.class) {
            synchronized (h.class) {
                if (i == null) {
                    i = new h();
                }
                hVar = i;
            }
        }
        return hVar;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001d, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a() {
        /*
            r2 = this;
            monitor-enter(r2)
            android.os.HandlerThread r0 = r2.e     // Catch:{ all -> 0x0039 }
            if (r0 == 0) goto L_0x001e
            boolean r0 = r0.isAlive()     // Catch:{ all -> 0x0039 }
            if (r0 == 0) goto L_0x001e
            android.os.Handler r0 = r2.d     // Catch:{ all -> 0x0039 }
            if (r0 != 0) goto L_0x001c
            android.os.Handler r0 = new android.os.Handler     // Catch:{ all -> 0x0039 }
            android.os.HandlerThread r1 = r2.e     // Catch:{ all -> 0x0039 }
            android.os.Looper r1 = r1.getLooper()     // Catch:{ all -> 0x0039 }
            r0.<init>(r1)     // Catch:{ all -> 0x0039 }
            r2.d = r0     // Catch:{ all -> 0x0039 }
        L_0x001c:
            monitor-exit(r2)
            return
        L_0x001e:
            android.os.HandlerThread r0 = new android.os.HandlerThread     // Catch:{ all -> 0x0039 }
            java.lang.String r1 = "ActivationSoundPlayer"
            r0.<init>(r1)     // Catch:{ all -> 0x0039 }
            r2.e = r0     // Catch:{ all -> 0x0039 }
            r0.start()     // Catch:{ all -> 0x0039 }
            android.os.Handler r0 = new android.os.Handler     // Catch:{ all -> 0x0039 }
            android.os.HandlerThread r1 = r2.e     // Catch:{ all -> 0x0039 }
            android.os.Looper r1 = r1.getLooper()     // Catch:{ all -> 0x0039 }
            r0.<init>(r1)     // Catch:{ all -> 0x0039 }
            r2.d = r0     // Catch:{ all -> 0x0039 }
            monitor-exit(r2)
            return
        L_0x0039:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.h.a():void");
    }

    public final void c() {
        StringBuilder sb = new StringBuilder("playNextSound: isPlaying=");
        sb.append(this.f);
        sb.append(", currentIndex=");
        sb.append(this.g);
        sb.append(MqttTopic.TOPIC_LEVEL_SEPARATOR);
        ArrayList arrayList = this.h;
        sb.append(arrayList.size());
        Log.d("ActivationSoundPlayer", sb.toString());
        if (!this.f || this.g >= arrayList.size()) {
            this.f = false;
            if (this.b != null) {
                new Handler(this.a.getMainLooper()).post(new a());
                return;
            }
            return;
        }
        a();
        String str = (String) arrayList.get(this.g);
        String i2 = y2.i("sounds/", str);
        Log.d("ActivationSoundPlayer", "Playing sound " + (this.g + 1) + MqttTopic.TOPIC_LEVEL_SEPARATOR + arrayList.size() + ": " + str);
        this.d.post(new b(i2, str));
    }

    public final void d(String str) {
        if (this.a != null && str != null && !str.isEmpty()) {
            ArrayList arrayList = this.h;
            arrayList.clear();
            String[] split = str.split("\\.");
            for (int i2 = 0; i2 < split.length; i2++) {
                for (char c2 : split[i2].trim().toCharArray()) {
                    if (Character.isDigit(c2)) {
                        arrayList.add(c2 + ".mp3");
                    }
                }
                if (i2 < split.length - 1) {
                    arrayList.add("cham.mp3");
                }
            }
            Objects.toString(arrayList);
            this.g = 0;
            this.f = true;
            d dVar = this.b;
            if (dVar != null) {
                dVar.onPlaybackStarted();
            }
            c();
        }
    }

    public final void e() {
        f("err_connect.mp3");
    }

    public final void f(String str) {
        if (this.a != null) {
            ArrayList arrayList = this.h;
            arrayList.clear();
            arrayList.add(str);
            this.g = 0;
            this.f = true;
            d dVar = this.b;
            if (dVar != null) {
                dVar.onPlaybackStarted();
            }
            c();
        }
    }

    public final void g() {
        HandlerThread handlerThread;
        this.f = false;
        if (this.d == null || (handlerThread = this.e) == null || !handlerThread.isAlive()) {
            MediaPlayer mediaPlayer = this.c;
            if (mediaPlayer != null) {
                try {
                    mediaPlayer.release();
                } catch (Exception e2) {
                    e2.getMessage();
                }
                this.c = null;
            }
        } else {
            this.d.post(new c());
        }
        this.h.clear();
        this.g = 0;
    }
}
