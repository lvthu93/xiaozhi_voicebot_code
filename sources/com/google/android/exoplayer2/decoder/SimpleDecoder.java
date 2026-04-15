package com.google.android.exoplayer2.decoder;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.decoder.DecoderException;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.decoder.OutputBuffer;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.text.SubtitleInputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import java.util.ArrayDeque;

public abstract class SimpleDecoder<I extends DecoderInputBuffer, O extends OutputBuffer, E extends DecoderException> implements Decoder<I, O, E> {
    public final a a;
    public final Object b = new Object();
    public final ArrayDeque<I> c = new ArrayDeque<>();
    public final ArrayDeque<O> d = new ArrayDeque<>();
    public final I[] e;
    public final O[] f;
    public int g;
    public int h;
    public I i;
    public E j;
    public boolean k;
    public boolean l;
    public int m;

    public class a extends Thread {
        public final /* synthetic */ SimpleDecoder c;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(SimpleSubtitleDecoder simpleSubtitleDecoder) {
            super("ExoPlayer:SimpleDecoder");
            this.c = simpleSubtitleDecoder;
        }

        public void run() {
            SimpleDecoder simpleDecoder = this.c;
            simpleDecoder.getClass();
            do {
                try {
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
            } while (simpleDecoder.d());
        }
    }

    public SimpleDecoder(I[] iArr, O[] oArr) {
        this.e = iArr;
        this.g = iArr.length;
        for (int i2 = 0; i2 < this.g; i2++) {
            this.e[i2] = new SubtitleInputBuffer();
        }
        this.f = oArr;
        this.h = oArr.length;
        for (int i3 = 0; i3 < this.h; i3++) {
            this.f[i3] = a();
        }
        a aVar = new a((SimpleSubtitleDecoder) this);
        this.a = aVar;
        aVar.start();
    }

    public abstract ya a();

    public abstract SubtitleDecoderException b(Throwable th);

    @Nullable
    public abstract SubtitleDecoderException c(DecoderInputBuffer decoderInputBuffer, OutputBuffer outputBuffer, boolean z);

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0043, code lost:
        if (r1.isEndOfStream() == false) goto L_0x004a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0045, code lost:
        r4.addFlag(4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004e, code lost:
        if (r1.isDecodeOnly() == false) goto L_0x0055;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0050, code lost:
        r4.addFlag(Integer.MIN_VALUE);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r0 = c(r1, r4, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x005a, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x005b, code lost:
        r0 = b(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0060, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0061, code lost:
        r0 = b(r0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean d() throws java.lang.InterruptedException {
        /*
            r6 = this;
            java.lang.Object r0 = r6.b
            monitor-enter(r0)
        L_0x0003:
            boolean r1 = r6.l     // Catch:{ all -> 0x0017 }
            r2 = 0
            r3 = 1
            if (r1 != 0) goto L_0x0023
            java.util.ArrayDeque<I> r1 = r6.c     // Catch:{ all -> 0x0017 }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x0017 }
            if (r1 != 0) goto L_0x001a
            int r1 = r6.h     // Catch:{ all -> 0x0017 }
            if (r1 <= 0) goto L_0x001a
            r1 = 1
            goto L_0x001b
        L_0x0017:
            r1 = move-exception
            goto L_0x00a8
        L_0x001a:
            r1 = 0
        L_0x001b:
            if (r1 != 0) goto L_0x0023
            java.lang.Object r1 = r6.b     // Catch:{ all -> 0x0017 }
            r1.wait()     // Catch:{ all -> 0x0017 }
            goto L_0x0003
        L_0x0023:
            boolean r1 = r6.l     // Catch:{ all -> 0x0017 }
            if (r1 == 0) goto L_0x0029
            monitor-exit(r0)     // Catch:{ all -> 0x0017 }
            return r2
        L_0x0029:
            java.util.ArrayDeque<I> r1 = r6.c     // Catch:{ all -> 0x0017 }
            java.lang.Object r1 = r1.removeFirst()     // Catch:{ all -> 0x0017 }
            com.google.android.exoplayer2.decoder.DecoderInputBuffer r1 = (com.google.android.exoplayer2.decoder.DecoderInputBuffer) r1     // Catch:{ all -> 0x0017 }
            O[] r4 = r6.f     // Catch:{ all -> 0x0017 }
            int r5 = r6.h     // Catch:{ all -> 0x0017 }
            int r5 = r5 - r3
            r6.h = r5     // Catch:{ all -> 0x0017 }
            r4 = r4[r5]     // Catch:{ all -> 0x0017 }
            boolean r5 = r6.k     // Catch:{ all -> 0x0017 }
            r6.k = r2     // Catch:{ all -> 0x0017 }
            monitor-exit(r0)     // Catch:{ all -> 0x0017 }
            boolean r0 = r1.isEndOfStream()
            if (r0 == 0) goto L_0x004a
            r0 = 4
            r4.addFlag(r0)
            goto L_0x0071
        L_0x004a:
            boolean r0 = r1.isDecodeOnly()
            if (r0 == 0) goto L_0x0055
            r0 = -2147483648(0xffffffff80000000, float:-0.0)
            r4.addFlag(r0)
        L_0x0055:
            com.google.android.exoplayer2.text.SubtitleDecoderException r0 = r6.c(r1, r4, r5)     // Catch:{ RuntimeException -> 0x0060, OutOfMemoryError -> 0x005a }
            goto L_0x0065
        L_0x005a:
            r0 = move-exception
            com.google.android.exoplayer2.text.SubtitleDecoderException r0 = r6.b(r0)
            goto L_0x0065
        L_0x0060:
            r0 = move-exception
            com.google.android.exoplayer2.text.SubtitleDecoderException r0 = r6.b(r0)
        L_0x0065:
            if (r0 == 0) goto L_0x0071
            java.lang.Object r5 = r6.b
            monitor-enter(r5)
            r6.j = r0     // Catch:{ all -> 0x006e }
            monitor-exit(r5)     // Catch:{ all -> 0x006e }
            return r2
        L_0x006e:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x006e }
            throw r0
        L_0x0071:
            java.lang.Object r5 = r6.b
            monitor-enter(r5)
            boolean r0 = r6.k     // Catch:{ all -> 0x00a5 }
            if (r0 == 0) goto L_0x007c
            r4.release()     // Catch:{ all -> 0x00a5 }
            goto L_0x0096
        L_0x007c:
            boolean r0 = r4.isDecodeOnly()     // Catch:{ all -> 0x00a5 }
            if (r0 == 0) goto L_0x008b
            int r0 = r6.m     // Catch:{ all -> 0x00a5 }
            int r0 = r0 + r3
            r6.m = r0     // Catch:{ all -> 0x00a5 }
            r4.release()     // Catch:{ all -> 0x00a5 }
            goto L_0x0096
        L_0x008b:
            int r0 = r6.m     // Catch:{ all -> 0x00a5 }
            r4.g = r0     // Catch:{ all -> 0x00a5 }
            r6.m = r2     // Catch:{ all -> 0x00a5 }
            java.util.ArrayDeque<O> r0 = r6.d     // Catch:{ all -> 0x00a5 }
            r0.addLast(r4)     // Catch:{ all -> 0x00a5 }
        L_0x0096:
            r1.clear()     // Catch:{ all -> 0x00a5 }
            int r0 = r6.g     // Catch:{ all -> 0x00a5 }
            int r2 = r0 + 1
            r6.g = r2     // Catch:{ all -> 0x00a5 }
            I[] r2 = r6.e     // Catch:{ all -> 0x00a5 }
            r2[r0] = r1     // Catch:{ all -> 0x00a5 }
            monitor-exit(r5)     // Catch:{ all -> 0x00a5 }
            return r3
        L_0x00a5:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x00a5 }
            throw r0
        L_0x00a8:
            monitor-exit(r0)     // Catch:{ all -> 0x0017 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.decoder.SimpleDecoder.d():boolean");
    }

    public final void flush() {
        synchronized (this.b) {
            this.k = true;
            this.m = 0;
            I i2 = this.i;
            if (i2 != null) {
                i2.clear();
                int i3 = this.g;
                this.g = i3 + 1;
                this.e[i3] = i2;
                this.i = null;
            }
            while (!this.c.isEmpty()) {
                I i4 = (DecoderInputBuffer) this.c.removeFirst();
                i4.clear();
                int i5 = this.g;
                this.g = i5 + 1;
                this.e[i5] = i4;
            }
            while (!this.d.isEmpty()) {
                ((OutputBuffer) this.d.removeFirst()).release();
            }
        }
    }

    public abstract /* synthetic */ String getName();

    @CallSuper
    public void release() {
        synchronized (this.b) {
            this.l = true;
            this.b.notify();
        }
        try {
            this.a.join();
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
        }
    }

    @Nullable
    public final I dequeueInputBuffer() throws DecoderException {
        I i2;
        synchronized (this.b) {
            try {
                E e2 = this.j;
                if (e2 == null) {
                    Assertions.checkState(this.i == null);
                    int i3 = this.g;
                    if (i3 == 0) {
                        i2 = null;
                    } else {
                        I[] iArr = this.e;
                        int i4 = i3 - 1;
                        this.g = i4;
                        i2 = iArr[i4];
                    }
                    this.i = i2;
                } else {
                    throw e2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i2;
    }

    @Nullable
    public final O dequeueOutputBuffer() throws DecoderException {
        synchronized (this.b) {
            try {
                E e2 = this.j;
                if (e2 != null) {
                    throw e2;
                } else if (this.d.isEmpty()) {
                    return null;
                } else {
                    O o = (OutputBuffer) this.d.removeFirst();
                    return o;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void queueInputBuffer(I i2) throws DecoderException {
        synchronized (this.b) {
            try {
                E e2 = this.j;
                if (e2 == null) {
                    boolean z = true;
                    Assertions.checkArgument(i2 == this.i);
                    this.c.addLast(i2);
                    if (this.c.isEmpty() || this.h <= 0) {
                        z = false;
                    }
                    if (z) {
                        this.b.notify();
                    }
                    this.i = null;
                } else {
                    throw e2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
