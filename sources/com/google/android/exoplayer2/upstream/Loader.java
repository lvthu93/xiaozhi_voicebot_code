package com.google.android.exoplayer2.upstream;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

public final class Loader implements LoaderErrorThrower {
    public static final LoadErrorAction d = new LoadErrorAction(2, -9223372036854775807L);
    public static final LoadErrorAction e = new LoadErrorAction(3, -9223372036854775807L);
    public final ExecutorService a;
    @Nullable
    public a<? extends Loadable> b;
    @Nullable
    public IOException c;

    public interface Callback<T extends Loadable> {
        void onLoadCanceled(T t, long j, long j2, boolean z);

        void onLoadCompleted(T t, long j, long j2);

        LoadErrorAction onLoadError(T t, long j, long j2, IOException iOException, int i);
    }

    public static final class LoadErrorAction {
        public final int a;
        public final long b;

        public LoadErrorAction(int i, long j) {
            this.a = i;
            this.b = j;
        }

        public boolean isRetry() {
            int i = this.a;
            return i == 0 || i == 1;
        }
    }

    public interface Loadable {
        void cancelLoad();

        void load() throws IOException;
    }

    public interface ReleaseCallback {
        void onLoaderReleased();
    }

    public static final class UnexpectedLoaderException extends IOException {
        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public UnexpectedLoaderException(java.lang.Throwable r5) {
            /*
                r4 = this;
                java.lang.Class r0 = r5.getClass()
                java.lang.String r0 = r0.getSimpleName()
                java.lang.String r1 = r5.getMessage()
                int r2 = r0.length()
                int r2 = r2 + 13
                int r2 = defpackage.y2.c(r1, r2)
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>(r2)
                java.lang.String r2 = "Unexpected "
                r3.append(r2)
                r3.append(r0)
                java.lang.String r0 = ": "
                r3.append(r0)
                r3.append(r1)
                java.lang.String r0 = r3.toString()
                r4.<init>(r0, r5)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.Loader.UnexpectedLoaderException.<init>(java.lang.Throwable):void");
        }
    }

    @SuppressLint({"HandlerLeak"})
    public final class a<T extends Loadable> extends Handler implements Runnable {
        public final int c;
        public final T f;
        public final long g;
        @Nullable
        public Callback<T> h;
        @Nullable
        public IOException i;
        public int j;
        @Nullable
        public Thread k;
        public boolean l;
        public volatile boolean m;

        public a(Looper looper, T t, Callback<T> callback, int i2, long j2) {
            super(looper);
            this.f = t;
            this.h = callback;
            this.c = i2;
            this.g = j2;
        }

        public void cancel(boolean z) {
            this.m = z;
            this.i = null;
            if (hasMessages(0)) {
                this.l = true;
                removeMessages(0);
                if (!z) {
                    sendEmptyMessage(1);
                }
            } else {
                synchronized (this) {
                    this.l = true;
                    this.f.cancelLoad();
                    Thread thread = this.k;
                    if (thread != null) {
                        thread.interrupt();
                    }
                }
            }
            if (z) {
                Loader.this.b = null;
                long elapsedRealtime = SystemClock.elapsedRealtime();
                ((Callback) Assertions.checkNotNull(this.h)).onLoadCanceled(this.f, elapsedRealtime, elapsedRealtime - this.g, true);
                this.h = null;
            }
        }

        public void handleMessage(Message message) {
            if (!this.m) {
                int i2 = message.what;
                if (i2 == 0) {
                    this.i = null;
                    Loader loader = Loader.this;
                    loader.a.execute((Runnable) Assertions.checkNotNull(loader.b));
                } else if (i2 != 3) {
                    Loader.this.b = null;
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    long j2 = elapsedRealtime - this.g;
                    Callback callback = (Callback) Assertions.checkNotNull(this.h);
                    if (this.l) {
                        callback.onLoadCanceled(this.f, elapsedRealtime, j2, false);
                        return;
                    }
                    int i3 = message.what;
                    if (i3 == 1) {
                        try {
                            callback.onLoadCompleted(this.f, elapsedRealtime, j2);
                        } catch (RuntimeException e) {
                            Log.e("LoadTask", "Unexpected exception handling load completed", e);
                            Loader.this.c = new UnexpectedLoaderException(e);
                        }
                    } else if (i3 == 2) {
                        IOException iOException = (IOException) message.obj;
                        this.i = iOException;
                        int i4 = this.j + 1;
                        this.j = i4;
                        LoadErrorAction onLoadError = callback.onLoadError(this.f, elapsedRealtime, j2, iOException, i4);
                        int i5 = onLoadError.a;
                        if (i5 == 3) {
                            Loader.this.c = this.i;
                        } else if (i5 != 2) {
                            if (i5 == 1) {
                                this.j = 1;
                            }
                            long j3 = onLoadError.b;
                            if (j3 == -9223372036854775807L) {
                                j3 = (long) Math.min((this.j - 1) * 1000, 5000);
                            }
                            start(j3);
                        }
                    }
                } else {
                    throw ((Error) message.obj);
                }
            }
        }

        public void maybeThrowError(int i2) throws IOException {
            IOException iOException = this.i;
            if (iOException != null && this.j > i2) {
                throw iOException;
            }
        }

        public void run() {
            boolean z;
            String str;
            try {
                synchronized (this) {
                    if (!this.l) {
                        z = true;
                    } else {
                        z = false;
                    }
                    this.k = Thread.currentThread();
                }
                if (z) {
                    String simpleName = this.f.getClass().getSimpleName();
                    if (simpleName.length() != 0) {
                        str = "load:".concat(simpleName);
                    } else {
                        str = new String("load:");
                    }
                    TraceUtil.beginSection(str);
                    this.f.load();
                    TraceUtil.endSection();
                }
                synchronized (this) {
                    this.k = null;
                    Thread.interrupted();
                }
                if (!this.m) {
                    sendEmptyMessage(1);
                }
            } catch (IOException e) {
                if (!this.m) {
                    obtainMessage(2, e).sendToTarget();
                }
            } catch (Exception e2) {
                if (!this.m) {
                    Log.e("LoadTask", "Unexpected exception loading stream", e2);
                    obtainMessage(2, new UnexpectedLoaderException(e2)).sendToTarget();
                }
            } catch (OutOfMemoryError e3) {
                if (!this.m) {
                    Log.e("LoadTask", "OutOfMemory error loading stream", e3);
                    obtainMessage(2, new UnexpectedLoaderException(e3)).sendToTarget();
                }
            } catch (Error e4) {
                if (!this.m) {
                    Log.e("LoadTask", "Unexpected error loading stream", e4);
                    obtainMessage(3, e4).sendToTarget();
                }
                throw e4;
            } catch (Throwable th) {
                TraceUtil.endSection();
                throw th;
            }
        }

        public void start(long j2) {
            boolean z;
            Loader loader = Loader.this;
            if (loader.b == null) {
                z = true;
            } else {
                z = false;
            }
            Assertions.checkState(z);
            loader.b = this;
            if (j2 > 0) {
                sendEmptyMessageDelayed(0, j2);
                return;
            }
            this.i = null;
            loader.a.execute((Runnable) Assertions.checkNotNull(loader.b));
        }
    }

    public static final class b implements Runnable {
        public final ReleaseCallback c;

        public b(ReleaseCallback releaseCallback) {
            this.c = releaseCallback;
        }

        public void run() {
            this.c.onLoaderReleased();
        }
    }

    static {
        createRetryAction(false, -9223372036854775807L);
        createRetryAction(true, -9223372036854775807L);
    }

    public Loader(String str) {
        String str2;
        String valueOf = String.valueOf(str);
        if (valueOf.length() != 0) {
            str2 = "ExoPlayer:Loader:".concat(valueOf);
        } else {
            str2 = new String("ExoPlayer:Loader:");
        }
        this.a = Util.newSingleThreadExecutor(str2);
    }

    public static LoadErrorAction createRetryAction(boolean z, long j) {
        return new LoadErrorAction(z ? 1 : 0, j);
    }

    public void cancelLoading() {
        ((a) Assertions.checkStateNotNull(this.b)).cancel(false);
    }

    public void clearFatalError() {
        this.c = null;
    }

    public boolean hasFatalError() {
        return this.c != null;
    }

    public boolean isLoading() {
        return this.b != null;
    }

    public void maybeThrowError() throws IOException {
        maybeThrowError(Integer.MIN_VALUE);
    }

    public void release() {
        release((ReleaseCallback) null);
    }

    public <T extends Loadable> long startLoading(T t, Callback<T> callback, int i) {
        this.c = null;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        new a((Looper) Assertions.checkStateNotNull(Looper.myLooper()), t, callback, i, elapsedRealtime).start(0);
        return elapsedRealtime;
    }

    public void maybeThrowError(int i) throws IOException {
        IOException iOException = this.c;
        if (iOException == null) {
            a<? extends Loadable> aVar = this.b;
            if (aVar != null) {
                if (i == Integer.MIN_VALUE) {
                    i = aVar.c;
                }
                aVar.maybeThrowError(i);
                return;
            }
            return;
        }
        throw iOException;
    }

    public void release(@Nullable ReleaseCallback releaseCallback) {
        a<? extends Loadable> aVar = this.b;
        if (aVar != null) {
            aVar.cancel(true);
        }
        ExecutorService executorService = this.a;
        if (releaseCallback != null) {
            executorService.execute(new b(releaseCallback));
        }
        executorService.shutdown();
    }
}
