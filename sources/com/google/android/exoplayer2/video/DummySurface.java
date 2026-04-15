package com.google.android.exoplayer2.video;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.Surface;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.EGLSurfaceTexture;
import com.google.android.exoplayer2.util.GlUtil;
import com.google.android.exoplayer2.util.Log;

@RequiresApi(17)
public final class DummySurface extends Surface {
    public static int h;
    public static boolean i;
    public final boolean c;
    public final a f;
    public boolean g;

    public static class a extends HandlerThread implements Handler.Callback {
        public EGLSurfaceTexture c;
        public Handler f;
        @Nullable
        public Error g;
        @Nullable
        public RuntimeException h;
        @Nullable
        public DummySurface i;

        public a() {
            super("ExoPlayer:DummySurface");
        }

        public final void a(int i2) {
            boolean z;
            Assertions.checkNotNull(this.c);
            this.c.init(i2);
            SurfaceTexture surfaceTexture = this.c.getSurfaceTexture();
            if (i2 != 0) {
                z = true;
            } else {
                z = false;
            }
            this.i = new DummySurface(this, surfaceTexture, z);
        }

        public boolean handleMessage(Message message) {
            int i2 = message.what;
            if (i2 == 1) {
                try {
                    a(message.arg1);
                    synchronized (this) {
                        notify();
                    }
                } catch (RuntimeException e) {
                    Log.e("DummySurface", "Failed to initialize dummy surface", e);
                    this.h = e;
                    synchronized (this) {
                        notify();
                    }
                } catch (Error e2) {
                    try {
                        Log.e("DummySurface", "Failed to initialize dummy surface", e2);
                        this.g = e2;
                        synchronized (this) {
                            notify();
                        }
                    } catch (Throwable th) {
                        synchronized (this) {
                            notify();
                            throw th;
                        }
                    }
                }
                return true;
            } else if (i2 != 2) {
                return true;
            } else {
                try {
                    Assertions.checkNotNull(this.c);
                    this.c.release();
                } catch (Throwable th2) {
                    quit();
                    throw th2;
                }
                quit();
                return true;
            }
        }

        public DummySurface init(int i2) {
            boolean z;
            start();
            Handler handler = new Handler(getLooper(), this);
            this.f = handler;
            this.c = new EGLSurfaceTexture(handler);
            synchronized (this) {
                z = false;
                this.f.obtainMessage(1, i2, 0).sendToTarget();
                while (this.i == null && this.h == null && this.g == null) {
                    try {
                        wait();
                    } catch (InterruptedException unused) {
                        z = true;
                    }
                }
            }
            if (z) {
                Thread.currentThread().interrupt();
            }
            RuntimeException runtimeException = this.h;
            if (runtimeException == null) {
                Error error = this.g;
                if (error == null) {
                    return (DummySurface) Assertions.checkNotNull(this.i);
                }
                throw error;
            }
            throw runtimeException;
        }

        public void release() {
            Assertions.checkNotNull(this.f);
            this.f.sendEmptyMessage(2);
        }
    }

    public DummySurface(a aVar, SurfaceTexture surfaceTexture, boolean z) {
        super(surfaceTexture);
        this.f = aVar;
        this.c = z;
    }

    public static synchronized boolean isSecureSupported(Context context) {
        boolean z;
        int i2;
        synchronized (DummySurface.class) {
            z = false;
            if (!i) {
                if (!GlUtil.isProtectedContentExtensionSupported(context)) {
                    i2 = 0;
                } else if (GlUtil.isSurfacelessContextExtensionSupported()) {
                    i2 = 1;
                } else {
                    i2 = 2;
                }
                h = i2;
                i = true;
            }
            if (h != 0) {
                z = true;
            }
        }
        return z;
    }

    public static DummySurface newInstanceV17(Context context, boolean z) {
        boolean z2;
        int i2 = 0;
        if (!z || isSecureSupported(context)) {
            z2 = true;
        } else {
            z2 = false;
        }
        Assertions.checkState(z2);
        a aVar = new a();
        if (z) {
            i2 = h;
        }
        return aVar.init(i2);
    }

    public void release() {
        super.release();
        synchronized (this.f) {
            if (!this.g) {
                this.f.release();
                this.g = true;
            }
        }
    }
}
