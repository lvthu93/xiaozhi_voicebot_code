package defpackage;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.HandlerThread;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.IntArrayQueue;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayDeque;

@RequiresApi(23)
/* renamed from: bh  reason: default package */
public final class bh extends MediaCodec.Callback {
    public final Object a = new Object();
    public final HandlerThread b;
    public Handler c;
    @GuardedBy("lock")
    public final IntArrayQueue d;
    @GuardedBy("lock")
    public final IntArrayQueue e;
    @GuardedBy("lock")
    public final ArrayDeque<MediaCodec.BufferInfo> f;
    @GuardedBy("lock")
    public final ArrayDeque<MediaFormat> g;
    @GuardedBy("lock")
    @Nullable
    public MediaFormat h;
    @GuardedBy("lock")
    @Nullable
    public MediaFormat i;
    @GuardedBy("lock")
    @Nullable
    public MediaCodec.CodecException j;
    @GuardedBy("lock")
    public long k;
    @GuardedBy("lock")
    public boolean l;
    @GuardedBy("lock")
    @Nullable
    public IllegalStateException m;

    public bh(HandlerThread handlerThread) {
        this.b = handlerThread;
        this.d = new IntArrayQueue();
        this.e = new IntArrayQueue();
        this.f = new ArrayDeque<>();
        this.g = new ArrayDeque<>();
    }

    @GuardedBy("lock")
    public final void a() {
        ArrayDeque<MediaFormat> arrayDeque = this.g;
        if (!arrayDeque.isEmpty()) {
            this.i = arrayDeque.getLast();
        }
        this.d.clear();
        this.e.clear();
        this.f.clear();
        arrayDeque.clear();
        this.j = null;
    }

    public final void b(IllegalStateException illegalStateException) {
        synchronized (this.a) {
            this.m = illegalStateException;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0031, code lost:
        return r2;
     */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0016 A[DONT_GENERATE] */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0018  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int dequeueInputBufferIndex() {
        /*
            r6 = this;
            java.lang.Object r0 = r6.a
            monitor-enter(r0)
            long r1 = r6.k     // Catch:{ all -> 0x003a }
            r3 = 0
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 > 0) goto L_0x0012
            boolean r1 = r6.l     // Catch:{ all -> 0x003a }
            if (r1 == 0) goto L_0x0010
            goto L_0x0012
        L_0x0010:
            r1 = 0
            goto L_0x0013
        L_0x0012:
            r1 = 1
        L_0x0013:
            r2 = -1
            if (r1 == 0) goto L_0x0018
            monitor-exit(r0)     // Catch:{ all -> 0x003a }
            return r2
        L_0x0018:
            java.lang.IllegalStateException r1 = r6.m     // Catch:{ all -> 0x003a }
            r3 = 0
            if (r1 != 0) goto L_0x0035
            android.media.MediaCodec$CodecException r1 = r6.j     // Catch:{ all -> 0x003a }
            if (r1 != 0) goto L_0x0032
            com.google.android.exoplayer2.util.IntArrayQueue r1 = r6.d     // Catch:{ all -> 0x003a }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x003a }
            if (r1 == 0) goto L_0x002a
            goto L_0x0030
        L_0x002a:
            com.google.android.exoplayer2.util.IntArrayQueue r1 = r6.d     // Catch:{ all -> 0x003a }
            int r2 = r1.remove()     // Catch:{ all -> 0x003a }
        L_0x0030:
            monitor-exit(r0)     // Catch:{ all -> 0x003a }
            return r2
        L_0x0032:
            r6.j = r3     // Catch:{ all -> 0x003a }
            throw r1     // Catch:{ all -> 0x003a }
        L_0x0035:
            r6.m = r3     // Catch:{ all -> 0x003a }
            throw r1     // Catch:{ all -> 0x003a }
        L_0x0038:
            monitor-exit(r0)     // Catch:{ all -> 0x003a }
            throw r1
        L_0x003a:
            r1 = move-exception
            goto L_0x0038
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bh.dequeueInputBufferIndex():int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x005b, code lost:
        return r1;
     */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0016 A[DONT_GENERATE] */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0018  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int dequeueOutputBufferIndex(android.media.MediaCodec.BufferInfo r10) {
        /*
            r9 = this;
            java.lang.Object r0 = r9.a
            monitor-enter(r0)
            long r1 = r9.k     // Catch:{ all -> 0x0064 }
            r3 = 0
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 > 0) goto L_0x0012
            boolean r1 = r9.l     // Catch:{ all -> 0x0064 }
            if (r1 == 0) goto L_0x0010
            goto L_0x0012
        L_0x0010:
            r1 = 0
            goto L_0x0013
        L_0x0012:
            r1 = 1
        L_0x0013:
            r2 = -1
            if (r1 == 0) goto L_0x0018
            monitor-exit(r0)     // Catch:{ all -> 0x0064 }
            return r2
        L_0x0018:
            java.lang.IllegalStateException r1 = r9.m     // Catch:{ all -> 0x0064 }
            r3 = 0
            if (r1 != 0) goto L_0x005f
            android.media.MediaCodec$CodecException r1 = r9.j     // Catch:{ all -> 0x0064 }
            if (r1 != 0) goto L_0x005c
            com.google.android.exoplayer2.util.IntArrayQueue r1 = r9.e     // Catch:{ all -> 0x0064 }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x0064 }
            if (r1 == 0) goto L_0x002b
            monitor-exit(r0)     // Catch:{ all -> 0x0064 }
            return r2
        L_0x002b:
            com.google.android.exoplayer2.util.IntArrayQueue r1 = r9.e     // Catch:{ all -> 0x0064 }
            int r1 = r1.remove()     // Catch:{ all -> 0x0064 }
            if (r1 < 0) goto L_0x004d
            android.media.MediaFormat r2 = r9.h     // Catch:{ all -> 0x0064 }
            com.google.android.exoplayer2.util.Assertions.checkStateNotNull(r2)     // Catch:{ all -> 0x0064 }
            java.util.ArrayDeque<android.media.MediaCodec$BufferInfo> r2 = r9.f     // Catch:{ all -> 0x0064 }
            java.lang.Object r2 = r2.remove()     // Catch:{ all -> 0x0064 }
            android.media.MediaCodec$BufferInfo r2 = (android.media.MediaCodec.BufferInfo) r2     // Catch:{ all -> 0x0064 }
            int r4 = r2.offset     // Catch:{ all -> 0x0064 }
            int r5 = r2.size     // Catch:{ all -> 0x0064 }
            long r6 = r2.presentationTimeUs     // Catch:{ all -> 0x0064 }
            int r8 = r2.flags     // Catch:{ all -> 0x0064 }
            r3 = r10
            r3.set(r4, r5, r6, r8)     // Catch:{ all -> 0x0064 }
            goto L_0x005a
        L_0x004d:
            r10 = -2
            if (r1 != r10) goto L_0x005a
            java.util.ArrayDeque<android.media.MediaFormat> r10 = r9.g     // Catch:{ all -> 0x0064 }
            java.lang.Object r10 = r10.remove()     // Catch:{ all -> 0x0064 }
            android.media.MediaFormat r10 = (android.media.MediaFormat) r10     // Catch:{ all -> 0x0064 }
            r9.h = r10     // Catch:{ all -> 0x0064 }
        L_0x005a:
            monitor-exit(r0)     // Catch:{ all -> 0x0064 }
            return r1
        L_0x005c:
            r9.j = r3     // Catch:{ all -> 0x0064 }
            throw r1     // Catch:{ all -> 0x0064 }
        L_0x005f:
            r9.m = r3     // Catch:{ all -> 0x0064 }
            throw r1     // Catch:{ all -> 0x0064 }
        L_0x0062:
            monitor-exit(r0)     // Catch:{ all -> 0x0064 }
            throw r10
        L_0x0064:
            r10 = move-exception
            goto L_0x0062
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bh.dequeueOutputBufferIndex(android.media.MediaCodec$BufferInfo):int");
    }

    public void flushAsync(Runnable runnable) {
        synchronized (this.a) {
            this.k++;
            ((Handler) Util.castNonNull(this.c)).post(new h2(4, this, runnable));
        }
    }

    public MediaFormat getOutputFormat() {
        MediaFormat mediaFormat;
        synchronized (this.a) {
            mediaFormat = this.h;
            if (mediaFormat == null) {
                throw new IllegalStateException();
            }
        }
        return mediaFormat;
    }

    public void initialize(MediaCodec mediaCodec) {
        boolean z;
        if (this.c == null) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        HandlerThread handlerThread = this.b;
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        mediaCodec.setCallback(this, handler);
        this.c = handler;
    }

    public void onError(@NonNull MediaCodec mediaCodec, @NonNull MediaCodec.CodecException codecException) {
        synchronized (this.a) {
            this.j = codecException;
        }
    }

    public void onInputBufferAvailable(@NonNull MediaCodec mediaCodec, int i2) {
        synchronized (this.a) {
            this.d.add(i2);
        }
    }

    public void onOutputBufferAvailable(@NonNull MediaCodec mediaCodec, int i2, @NonNull MediaCodec.BufferInfo bufferInfo) {
        synchronized (this.a) {
            MediaFormat mediaFormat = this.i;
            if (mediaFormat != null) {
                this.e.add(-2);
                this.g.add(mediaFormat);
                this.i = null;
            }
            this.e.add(i2);
            this.f.add(bufferInfo);
        }
    }

    public void onOutputFormatChanged(@NonNull MediaCodec mediaCodec, @NonNull MediaFormat mediaFormat) {
        synchronized (this.a) {
            this.e.add(-2);
            this.g.add(mediaFormat);
            this.i = null;
        }
    }

    public void shutdown() {
        synchronized (this.a) {
            this.l = true;
            this.b.quit();
            a();
        }
    }
}
