package com.google.android.exoplayer2.video;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.Choreographer;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

public final class VideoFrameReleaseHelper {
    public final b3 a = new b3();
    @Nullable
    public final DisplayHelper b;
    @Nullable
    public final c c;
    public boolean d;
    @Nullable
    public Surface e;
    public float f;
    public float g;
    public float h;
    public float i;
    public long j;
    public long k;
    public long l;
    public long m;
    public long n;
    public long o;
    public long p;

    public interface DisplayHelper {

        public interface Listener {
            void onDefaultDisplayChanged(@Nullable Display display);
        }

        void register(Listener listener);

        void unregister();
    }

    public static final class a implements DisplayHelper {
        public final WindowManager a;

        public a(WindowManager windowManager) {
            this.a = windowManager;
        }

        @Nullable
        public static DisplayHelper maybeBuildNewInstance(Context context) {
            WindowManager windowManager = (WindowManager) context.getSystemService("window");
            if (windowManager != null) {
                return new a(windowManager);
            }
            return null;
        }

        public void register(DisplayHelper.Listener listener) {
            listener.onDefaultDisplayChanged(this.a.getDefaultDisplay());
        }

        public void unregister() {
        }
    }

    @RequiresApi(17)
    public static final class b implements DisplayHelper, DisplayManager.DisplayListener {
        public final DisplayManager a;
        @Nullable
        public DisplayHelper.Listener b;

        public b(DisplayManager displayManager) {
            this.a = displayManager;
        }

        @Nullable
        public static DisplayHelper maybeBuildNewInstance(Context context) {
            DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
            if (displayManager != null) {
                return new b(displayManager);
            }
            return null;
        }

        public void onDisplayAdded(int i) {
        }

        public void onDisplayChanged(int i) {
            DisplayHelper.Listener listener = this.b;
            if (listener != null && i == 0) {
                listener.onDefaultDisplayChanged(this.a.getDisplay(0));
            }
        }

        public void onDisplayRemoved(int i) {
        }

        public void register(DisplayHelper.Listener listener) {
            this.b = listener;
            Handler createHandlerForCurrentLooper = Util.createHandlerForCurrentLooper();
            DisplayManager displayManager = this.a;
            displayManager.registerDisplayListener(this, createHandlerForCurrentLooper);
            listener.onDefaultDisplayChanged(displayManager.getDisplay(0));
        }

        public void unregister() {
            this.a.unregisterDisplayListener(this);
            this.b = null;
        }
    }

    public static final class c implements Choreographer.FrameCallback, Handler.Callback {
        public static final c i = new c();
        public volatile long c = -9223372036854775807L;
        public final Handler f;
        public Choreographer g;
        public int h;

        public c() {
            HandlerThread handlerThread = new HandlerThread("ExoPlayer:FrameReleaseChoreographer");
            handlerThread.start();
            Handler createHandler = Util.createHandler(handlerThread.getLooper(), this);
            this.f = createHandler;
            createHandler.sendEmptyMessage(0);
        }

        public static c getInstance() {
            return i;
        }

        public void addObserver() {
            this.f.sendEmptyMessage(1);
        }

        public void doFrame(long j) {
            this.c = j;
            ((Choreographer) Assertions.checkNotNull(this.g)).postFrameCallbackDelayed(this, 500);
        }

        public boolean handleMessage(Message message) {
            int i2 = message.what;
            if (i2 == 0) {
                this.g = Choreographer.getInstance();
                return true;
            } else if (i2 == 1) {
                int i3 = this.h + 1;
                this.h = i3;
                if (i3 == 1) {
                    ((Choreographer) Assertions.checkNotNull(this.g)).postFrameCallback(this);
                }
                return true;
            } else if (i2 != 2) {
                return false;
            } else {
                int i4 = this.h - 1;
                this.h = i4;
                if (i4 == 0) {
                    ((Choreographer) Assertions.checkNotNull(this.g)).removeFrameCallback(this);
                    this.c = -9223372036854775807L;
                }
                return true;
            }
        }

        public void removeObserver() {
            this.f.sendEmptyMessage(2);
        }
    }

    public VideoFrameReleaseHelper(@Nullable Context context) {
        DisplayHelper displayHelper;
        c cVar = null;
        if (context != null) {
            Context applicationContext = context.getApplicationContext();
            if (Util.a >= 17) {
                displayHelper = b.maybeBuildNewInstance(applicationContext);
            } else {
                displayHelper = null;
            }
            if (displayHelper == null) {
                displayHelper = a.maybeBuildNewInstance(applicationContext);
            }
        } else {
            displayHelper = null;
        }
        this.b = displayHelper;
        this.c = displayHelper != null ? c.getInstance() : cVar;
        this.j = -9223372036854775807L;
        this.k = -9223372036854775807L;
        this.f = -1.0f;
        this.i = 1.0f;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0055, code lost:
        if (java.lang.Math.abs(r2 - r8.g) >= r0) goto L_0x0063;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0061, code lost:
        if (r0.getFramesWithoutSyncCount() >= 30) goto L_0x0063;
     */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:34:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a() {
        /*
            r8 = this;
            int r0 = com.google.android.exoplayer2.util.Util.a
            r1 = 30
            if (r0 < r1) goto L_0x006a
            android.view.Surface r0 = r8.e
            if (r0 != 0) goto L_0x000b
            goto L_0x006a
        L_0x000b:
            b3 r0 = r8.a
            boolean r2 = r0.isSynced()
            if (r2 == 0) goto L_0x0018
            float r2 = r0.getFrameRate()
            goto L_0x001a
        L_0x0018:
            float r2 = r8.f
        L_0x001a:
            float r3 = r8.g
            int r4 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r4 != 0) goto L_0x0021
            return
        L_0x0021:
            r4 = -1082130432(0xffffffffbf800000, float:-1.0)
            r5 = 0
            r6 = 1
            int r7 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r7 == 0) goto L_0x005a
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 == 0) goto L_0x005a
            boolean r1 = r0.isSynced()
            if (r1 == 0) goto L_0x0042
            long r0 = r0.getMatchingFrameDurationSumNs()
            r3 = 5000000000(0x12a05f200, double:2.470328229E-314)
            int r7 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r7 < 0) goto L_0x0042
            r0 = 1
            goto L_0x0043
        L_0x0042:
            r0 = 0
        L_0x0043:
            if (r0 == 0) goto L_0x0049
            r0 = 1017370378(0x3ca3d70a, float:0.02)
            goto L_0x004b
        L_0x0049:
            r0 = 1065353216(0x3f800000, float:1.0)
        L_0x004b:
            float r1 = r8.g
            float r1 = r2 - r1
            float r1 = java.lang.Math.abs(r1)
            int r0 = (r1 > r0 ? 1 : (r1 == r0 ? 0 : -1))
            if (r0 < 0) goto L_0x0058
            goto L_0x0063
        L_0x0058:
            r6 = 0
            goto L_0x0063
        L_0x005a:
            if (r7 == 0) goto L_0x005d
            goto L_0x0063
        L_0x005d:
            int r0 = r0.getFramesWithoutSyncCount()
            if (r0 < r1) goto L_0x0058
        L_0x0063:
            if (r6 == 0) goto L_0x006a
            r8.g = r2
            r8.b(r5)
        L_0x006a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.video.VideoFrameReleaseHelper.a():void");
    }

    public long adjustReleaseTime(long j2) {
        long j3;
        boolean z;
        if (this.o != -1 && this.a.isSynced()) {
            long frameDurationNs = this.p + ((long) (((float) ((this.l - this.o) * this.a.getFrameDurationNs())) / this.i));
            if (Math.abs(j2 - frameDurationNs) <= 20000000) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                j2 = frameDurationNs;
            } else {
                this.l = 0;
                this.o = -1;
                this.m = -1;
            }
        }
        this.m = this.l;
        this.n = j2;
        c cVar = this.c;
        if (cVar == null || this.j == -9223372036854775807L) {
            return j2;
        }
        long j4 = cVar.c;
        if (j4 == -9223372036854775807L) {
            return j2;
        }
        long j5 = this.j;
        long j6 = (((j2 - j4) / j5) * j5) + j4;
        if (j2 <= j6) {
            j3 = j6 - j5;
        } else {
            j3 = j6;
            j6 = j5 + j6;
        }
        if (j6 - j2 >= j2 - j3) {
            j6 = j3;
        }
        return j6 - this.k;
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0026 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0027  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void b(boolean r5) {
        /*
            r4 = this;
            int r0 = com.google.android.exoplayer2.util.Util.a
            r1 = 30
            if (r0 < r1) goto L_0x003c
            android.view.Surface r0 = r4.e
            if (r0 != 0) goto L_0x000b
            goto L_0x003c
        L_0x000b:
            boolean r1 = r4.d
            r2 = 0
            if (r1 == 0) goto L_0x001d
            float r1 = r4.g
            r3 = -1082130432(0xffffffffbf800000, float:-1.0)
            int r3 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r3 == 0) goto L_0x001d
            float r3 = r4.i
            float r1 = r1 * r3
            goto L_0x001e
        L_0x001d:
            r1 = 0
        L_0x001e:
            if (r5 != 0) goto L_0x0027
            float r5 = r4.h
            int r5 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r5 != 0) goto L_0x0027
            return
        L_0x0027:
            r4.h = r1
            int r5 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r5 != 0) goto L_0x002f
            r5 = 0
            goto L_0x0030
        L_0x002f:
            r5 = 1
        L_0x0030:
            r0.setFrameRate(r1, r5)     // Catch:{ IllegalStateException -> 0x0034 }
            goto L_0x003c
        L_0x0034:
            r5 = move-exception
            java.lang.String r0 = "VideoFrameReleaseHelper"
            java.lang.String r1 = "Failed to call Surface.setFrameRate"
            com.google.android.exoplayer2.util.Log.e(r0, r1, r5)
        L_0x003c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.video.VideoFrameReleaseHelper.b(boolean):void");
    }

    public void onDisabled() {
        DisplayHelper displayHelper = this.b;
        if (displayHelper != null) {
            displayHelper.unregister();
            ((c) Assertions.checkNotNull(this.c)).removeObserver();
        }
    }

    public void onEnabled() {
        DisplayHelper displayHelper = this.b;
        if (displayHelper != null) {
            ((c) Assertions.checkNotNull(this.c)).addObserver();
            displayHelper.register(new i2(11, this));
        }
    }

    public void onFormatChanged(float f2) {
        this.f = f2;
        this.a.reset();
        a();
    }

    public void onNextFrame(long j2) {
        long j3 = this.m;
        if (j3 != -1) {
            this.o = j3;
            this.p = this.n;
        }
        this.l++;
        this.a.onNextFrame(j2 * 1000);
        a();
    }

    public void onPlaybackSpeed(float f2) {
        this.i = f2;
        this.l = 0;
        this.o = -1;
        this.m = -1;
        b(false);
    }

    public void onPositionReset() {
        this.l = 0;
        this.o = -1;
        this.m = -1;
    }

    public void onStarted() {
        this.d = true;
        this.l = 0;
        this.o = -1;
        this.m = -1;
        b(false);
    }

    public void onStopped() {
        Surface surface;
        this.d = false;
        if (Util.a >= 30 && (surface = this.e) != null && this.h != 0.0f) {
            this.h = 0.0f;
            try {
                surface.setFrameRate(0.0f, 0);
            } catch (IllegalStateException e2) {
                Log.e("VideoFrameReleaseHelper", "Failed to call Surface.setFrameRate", e2);
            }
        }
    }

    public void onSurfaceChanged(@Nullable Surface surface) {
        if (surface instanceof DummySurface) {
            surface = null;
        }
        Surface surface2 = this.e;
        if (surface2 != surface) {
            if (!(Util.a < 30 || surface2 == null || this.h == 0.0f)) {
                this.h = 0.0f;
                try {
                    surface2.setFrameRate(0.0f, 0);
                } catch (IllegalStateException e2) {
                    Log.e("VideoFrameReleaseHelper", "Failed to call Surface.setFrameRate", e2);
                }
            }
            this.e = surface;
            b(true);
        }
    }
}
