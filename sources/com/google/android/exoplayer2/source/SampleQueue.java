package com.google.android.exoplayer2.source;

import android.os.Looper;
import androidx.annotation.CallSuper;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

public class SampleQueue implements TrackOutput {
    public final c a;
    public boolean aa;
    @Nullable
    public Format ab;
    @Nullable
    public Format ac;
    public int ad;
    public boolean ae;
    public boolean af;
    public long ag;
    public boolean ah;
    public final a b = new a();
    public final kb<b> c = new kb<>(new z6(13));
    @Nullable
    public final DrmSessionManager d;
    @Nullable
    public final DrmSessionEventListener.EventDispatcher e;
    @Nullable
    public final Looper f;
    @Nullable
    public UpstreamFormatChangedListener g;
    @Nullable
    public Format h;
    @Nullable
    public DrmSession i;
    public int j = 1000;
    public int[] k = new int[1000];
    public long[] l = new long[1000];
    public int[] m = new int[1000];
    public int[] n = new int[1000];
    public long[] o = new long[1000];
    public TrackOutput.CryptoData[] p = new TrackOutput.CryptoData[1000];
    public int q;
    public int r;
    public int s;
    public int t;
    public long u = Long.MIN_VALUE;
    public long v = Long.MIN_VALUE;
    public long w = Long.MIN_VALUE;
    public boolean x;
    public boolean y = true;
    public boolean z = true;

    public interface UpstreamFormatChangedListener {
        void onUpstreamFormatChanged(Format format);
    }

    public static final class a {
        public int a;
        public long b;
        @Nullable
        public TrackOutput.CryptoData c;
    }

    public static final class b {
        public final Format a;
        public final DrmSessionManager.DrmSessionReference b;

        public b(Format format, DrmSessionManager.DrmSessionReference drmSessionReference) {
            this.a = format;
            this.b = drmSessionReference;
        }
    }

    public SampleQueue(Allocator allocator, @Nullable Looper looper, @Nullable DrmSessionManager drmSessionManager, @Nullable DrmSessionEventListener.EventDispatcher eventDispatcher) {
        this.f = looper;
        this.d = drmSessionManager;
        this.e = eventDispatcher;
        this.a = new c(allocator);
    }

    public static SampleQueue createWithDrm(Allocator allocator, Looper looper, DrmSessionManager drmSessionManager, DrmSessionEventListener.EventDispatcher eventDispatcher) {
        return new SampleQueue(allocator, (Looper) Assertions.checkNotNull(looper), (DrmSessionManager) Assertions.checkNotNull(drmSessionManager), (DrmSessionEventListener.EventDispatcher) Assertions.checkNotNull(eventDispatcher));
    }

    public static SampleQueue createWithoutDrm(Allocator allocator) {
        return new SampleQueue(allocator, (Looper) null, (DrmSessionManager) null, (DrmSessionEventListener.EventDispatcher) null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0010, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized boolean a(long r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            int r0 = r5.q     // Catch:{ all -> 0x0027 }
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L_0x0011
            long r3 = r5.v     // Catch:{ all -> 0x0027 }
            int r0 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r0 <= 0) goto L_0x000e
            goto L_0x000f
        L_0x000e:
            r1 = 0
        L_0x000f:
            monitor-exit(r5)
            return r1
        L_0x0011:
            long r3 = r5.getLargestReadTimestampUs()     // Catch:{ all -> 0x0027 }
            int r0 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r0 < 0) goto L_0x001b
            monitor-exit(r5)
            return r2
        L_0x001b:
            int r6 = r5.b(r6)     // Catch:{ all -> 0x0027 }
            int r7 = r5.r     // Catch:{ all -> 0x0027 }
            int r7 = r7 + r6
            r5.d(r7)     // Catch:{ all -> 0x0027 }
            monitor-exit(r5)
            return r1
        L_0x0027:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.SampleQueue.a(long):boolean");
    }

    public final int b(long j2) {
        int i2 = this.q;
        int g2 = g(i2 - 1);
        while (i2 > this.t && this.o[g2] >= j2) {
            i2--;
            g2--;
            if (g2 == -1) {
                g2 = this.j - 1;
            }
        }
        return i2;
    }

    @GuardedBy("this")
    public final long c(int i2) {
        this.v = Math.max(this.v, f(i2));
        this.q -= i2;
        int i3 = this.r + i2;
        this.r = i3;
        int i4 = this.s + i2;
        this.s = i4;
        int i5 = this.j;
        if (i4 >= i5) {
            this.s = i4 - i5;
        }
        int i6 = this.t - i2;
        this.t = i6;
        if (i6 < 0) {
            this.t = 0;
        }
        this.c.discardTo(i3);
        if (this.q != 0) {
            return this.l[this.s];
        }
        int i7 = this.s;
        if (i7 == 0) {
            i7 = this.j;
        }
        int i8 = i7 - 1;
        return this.l[i8] + ((long) this.m[i8]);
    }

    public final long d(int i2) {
        boolean z2;
        int writeIndex = getWriteIndex() - i2;
        boolean z3 = false;
        if (writeIndex < 0 || writeIndex > this.q - this.t) {
            z2 = false;
        } else {
            z2 = true;
        }
        Assertions.checkArgument(z2);
        int i3 = this.q - writeIndex;
        this.q = i3;
        this.w = Math.max(this.v, f(i3));
        if (writeIndex == 0 && this.x) {
            z3 = true;
        }
        this.x = z3;
        this.c.discardFrom(i2);
        int i4 = this.q;
        if (i4 == 0) {
            return 0;
        }
        int g2 = g(i4 - 1);
        return this.l[g2] + ((long) this.m[g2]);
    }

    public synchronized long discardSampleMetadataToRead() {
        int i2 = this.t;
        if (i2 == 0) {
            return -1;
        }
        return c(i2);
    }

    public final void discardTo(long j2, boolean z2, boolean z3) {
        long j3;
        int i2;
        c cVar = this.a;
        synchronized (this) {
            int i3 = this.q;
            j3 = -1;
            if (i3 != 0) {
                long[] jArr = this.o;
                int i4 = this.s;
                if (j2 >= jArr[i4]) {
                    if (z3 && (i2 = this.t) != i3) {
                        i3 = i2 + 1;
                    }
                    int e2 = e(i4, i3, j2, z2);
                    if (e2 != -1) {
                        j3 = c(e2);
                    }
                }
            }
        }
        cVar.discardDownstreamTo(j3);
    }

    public final void discardToEnd() {
        long j2;
        c cVar = this.a;
        synchronized (this) {
            int i2 = this.q;
            if (i2 == 0) {
                j2 = -1;
            } else {
                j2 = c(i2);
            }
        }
        cVar.discardDownstreamTo(j2);
    }

    public final void discardToRead() {
        this.a.discardDownstreamTo(discardSampleMetadataToRead());
    }

    public final void discardUpstreamFrom(long j2) {
        boolean z2;
        if (this.q != 0) {
            if (j2 > getLargestReadTimestampUs()) {
                z2 = true;
            } else {
                z2 = false;
            }
            Assertions.checkArgument(z2);
            discardUpstreamSamples(this.r + b(j2));
        }
    }

    public final void discardUpstreamSamples(int i2) {
        this.a.discardUpstreamSampleBytes(d(i2));
    }

    public final int e(int i2, int i3, long j2, boolean z2) {
        int i4 = -1;
        for (int i5 = 0; i5 < i3; i5++) {
            long j3 = this.o[i2];
            if (j3 > j2) {
                return i4;
            }
            if (!z2 || (this.n[i2] & 1) != 0) {
                if (j3 == j2) {
                    return i5;
                }
                i4 = i5;
            }
            i2++;
            if (i2 == this.j) {
                i2 = 0;
            }
        }
        return i4;
    }

    public final long f(int i2) {
        long j2 = Long.MIN_VALUE;
        if (i2 == 0) {
            return Long.MIN_VALUE;
        }
        int g2 = g(i2 - 1);
        for (int i3 = 0; i3 < i2; i3++) {
            j2 = Math.max(j2, this.o[g2]);
            if ((this.n[g2] & 1) != 0) {
                break;
            }
            g2--;
            if (g2 == -1) {
                g2 = this.j - 1;
            }
        }
        return j2;
    }

    public final void format(Format format) {
        Format format2;
        if (this.ag == 0 || format.t == Long.MAX_VALUE) {
            format2 = format;
        } else {
            format2 = format.buildUpon().setSubsampleOffsetUs(format.t + this.ag).build();
        }
        boolean z2 = false;
        this.aa = false;
        this.ab = format;
        synchronized (this) {
            this.z = false;
            if (!Util.areEqual(format2, this.ac)) {
                if (this.c.isEmpty() || !this.c.getEndValue().a.equals(format2)) {
                    this.ac = format2;
                } else {
                    this.ac = this.c.getEndValue().a;
                }
                Format format3 = this.ac;
                this.ae = MimeTypes.allSamplesAreSyncSamples(format3.p, format3.m);
                this.af = false;
                z2 = true;
            }
        }
        UpstreamFormatChangedListener upstreamFormatChangedListener = this.g;
        if (upstreamFormatChangedListener != null && z2) {
            upstreamFormatChangedListener.onUpstreamFormatChanged(format2);
        }
    }

    public final int g(int i2) {
        int i3 = this.s + i2;
        int i4 = this.j;
        if (i3 < i4) {
            return i3;
        }
        return i3 - i4;
    }

    public final int getFirstIndex() {
        return this.r;
    }

    public final synchronized long getFirstTimestampUs() {
        long j2;
        if (this.q == 0) {
            j2 = Long.MIN_VALUE;
        } else {
            j2 = this.o[this.s];
        }
        return j2;
    }

    public final synchronized long getLargestQueuedTimestampUs() {
        return this.w;
    }

    public final synchronized long getLargestReadTimestampUs() {
        return Math.max(this.v, f(this.t));
    }

    public final int getReadIndex() {
        return this.r + this.t;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0038, code lost:
        return 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized int getSkipCount(long r9, boolean r11) {
        /*
            r8 = this;
            monitor-enter(r8)
            int r0 = r8.t     // Catch:{ all -> 0x0039 }
            int r2 = r8.g(r0)     // Catch:{ all -> 0x0039 }
            int r0 = r8.t     // Catch:{ all -> 0x0039 }
            int r1 = r8.q     // Catch:{ all -> 0x0039 }
            r7 = 0
            if (r0 == r1) goto L_0x0010
            r3 = 1
            goto L_0x0011
        L_0x0010:
            r3 = 0
        L_0x0011:
            if (r3 == 0) goto L_0x0037
            long[] r3 = r8.o     // Catch:{ all -> 0x0039 }
            r4 = r3[r2]     // Catch:{ all -> 0x0039 }
            int r3 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            if (r3 >= 0) goto L_0x001c
            goto L_0x0037
        L_0x001c:
            long r3 = r8.w     // Catch:{ all -> 0x0039 }
            int r5 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r5 <= 0) goto L_0x0027
            if (r11 == 0) goto L_0x0027
            int r1 = r1 - r0
            monitor-exit(r8)
            return r1
        L_0x0027:
            int r3 = r1 - r0
            r6 = 1
            r1 = r8
            r4 = r9
            int r9 = r1.e(r2, r3, r4, r6)     // Catch:{ all -> 0x0039 }
            r10 = -1
            if (r9 != r10) goto L_0x0035
            monitor-exit(r8)
            return r7
        L_0x0035:
            monitor-exit(r8)
            return r9
        L_0x0037:
            monitor-exit(r8)
            return r7
        L_0x0039:
            r9 = move-exception
            monitor-exit(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.SampleQueue.getSkipCount(long, boolean):int");
    }

    @Nullable
    public final synchronized Format getUpstreamFormat() {
        Format format;
        if (this.z) {
            format = null;
        } else {
            format = this.ac;
        }
        return format;
    }

    public final int getWriteIndex() {
        return this.r + this.q;
    }

    public final boolean h(int i2) {
        DrmSession drmSession = this.i;
        if (drmSession == null || drmSession.getState() == 4 || ((this.n[i2] & 1073741824) == 0 && this.i.playClearSamplesWithoutKeys())) {
            return true;
        }
        return false;
    }

    public final void i(Format format, FormatHolder formatHolder) {
        boolean z2;
        DrmInitData drmInitData;
        Format format2;
        Format format3 = this.h;
        if (format3 == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            drmInitData = null;
        } else {
            drmInitData = format3.s;
        }
        this.h = format;
        DrmInitData drmInitData2 = format.s;
        DrmSessionManager drmSessionManager = this.d;
        if (drmSessionManager != null) {
            format2 = format.copyWithExoMediaCryptoType(drmSessionManager.getExoMediaCryptoType(format));
        } else {
            format2 = format;
        }
        formatHolder.b = format2;
        formatHolder.a = this.i;
        if (drmSessionManager != null) {
            if (z2 || !Util.areEqual(drmInitData, drmInitData2)) {
                DrmSession drmSession = this.i;
                DrmSessionEventListener.EventDispatcher eventDispatcher = this.e;
                DrmSession acquireSession = drmSessionManager.acquireSession((Looper) Assertions.checkNotNull(this.f), eventDispatcher, format);
                this.i = acquireSession;
                formatHolder.a = acquireSession;
                if (drmSession != null) {
                    drmSession.release(eventDispatcher);
                }
            }
        }
    }

    public final synchronized boolean isLastSampleQueued() {
        return this.x;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x001f, code lost:
        return r2;
     */
    @androidx.annotation.CallSuper
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean isReady(boolean r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            int r0 = r4.t     // Catch:{ all -> 0x0040 }
            int r1 = r4.q     // Catch:{ all -> 0x0040 }
            r2 = 1
            r3 = 0
            if (r0 == r1) goto L_0x000b
            r0 = 1
            goto L_0x000c
        L_0x000b:
            r0 = 0
        L_0x000c:
            if (r0 != 0) goto L_0x0020
            if (r5 != 0) goto L_0x001e
            boolean r5 = r4.x     // Catch:{ all -> 0x0040 }
            if (r5 != 0) goto L_0x001e
            com.google.android.exoplayer2.Format r5 = r4.ac     // Catch:{ all -> 0x0040 }
            if (r5 == 0) goto L_0x001d
            com.google.android.exoplayer2.Format r0 = r4.h     // Catch:{ all -> 0x0040 }
            if (r5 == r0) goto L_0x001d
            goto L_0x001e
        L_0x001d:
            r2 = 0
        L_0x001e:
            monitor-exit(r4)
            return r2
        L_0x0020:
            kb<com.google.android.exoplayer2.source.SampleQueue$b> r5 = r4.c     // Catch:{ all -> 0x0040 }
            int r0 = r4.getReadIndex()     // Catch:{ all -> 0x0040 }
            java.lang.Object r5 = r5.get(r0)     // Catch:{ all -> 0x0040 }
            com.google.android.exoplayer2.source.SampleQueue$b r5 = (com.google.android.exoplayer2.source.SampleQueue.b) r5     // Catch:{ all -> 0x0040 }
            com.google.android.exoplayer2.Format r5 = r5.a     // Catch:{ all -> 0x0040 }
            com.google.android.exoplayer2.Format r0 = r4.h     // Catch:{ all -> 0x0040 }
            if (r5 == r0) goto L_0x0034
            monitor-exit(r4)
            return r2
        L_0x0034:
            int r5 = r4.t     // Catch:{ all -> 0x0040 }
            int r5 = r4.g(r5)     // Catch:{ all -> 0x0040 }
            boolean r5 = r4.h(r5)     // Catch:{ all -> 0x0040 }
            monitor-exit(r4)
            return r5
        L_0x0040:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.SampleQueue.isReady(boolean):boolean");
    }

    public final synchronized void j() {
        this.t = 0;
        this.a.rewind();
    }

    @CallSuper
    public void maybeThrowError() throws IOException {
        DrmSession drmSession = this.i;
        if (drmSession != null && drmSession.getState() == 1) {
            throw ((DrmSession.DrmSessionException) Assertions.checkNotNull(this.i.getError()));
        }
    }

    public final synchronized int peekSourceId() {
        boolean z2;
        int i2;
        int g2 = g(this.t);
        if (this.t != this.q) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            i2 = this.k[g2];
        } else {
            i2 = this.ad;
        }
        return i2;
    }

    @CallSuper
    public void preRelease() {
        discardToEnd();
        DrmSession drmSession = this.i;
        if (drmSession != null) {
            drmSession.release(this.e);
            this.i = null;
            this.h = null;
        }
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:46:0x0091=Splitter:B:46:0x0091, B:25:0x0039=Splitter:B:25:0x0039} */
    @androidx.annotation.CallSuper
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int read(com.google.android.exoplayer2.FormatHolder r12, com.google.android.exoplayer2.decoder.DecoderInputBuffer r13, int r14, boolean r15) {
        /*
            r11 = this;
            r0 = r14 & 2
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x0008
            r0 = 1
            goto L_0x0009
        L_0x0008:
            r0 = 0
        L_0x0009:
            com.google.android.exoplayer2.source.SampleQueue$a r3 = r11.b
            monitor-enter(r11)
            r13.h = r1     // Catch:{ all -> 0x00bf }
            int r4 = r11.t     // Catch:{ all -> 0x00bf }
            int r5 = r11.q     // Catch:{ all -> 0x00bf }
            if (r4 == r5) goto L_0x0016
            r4 = 1
            goto L_0x0017
        L_0x0016:
            r4 = 0
        L_0x0017:
            r5 = -4
            r6 = 4
            if (r4 != 0) goto L_0x003e
            if (r15 != 0) goto L_0x0039
            boolean r15 = r11.x     // Catch:{ all -> 0x00bf }
            if (r15 == 0) goto L_0x0022
            goto L_0x0039
        L_0x0022:
            com.google.android.exoplayer2.Format r15 = r11.ac     // Catch:{ all -> 0x00bf }
            if (r15 == 0) goto L_0x0037
            if (r0 != 0) goto L_0x002c
            com.google.android.exoplayer2.Format r0 = r11.h     // Catch:{ all -> 0x00bf }
            if (r15 == r0) goto L_0x0037
        L_0x002c:
            java.lang.Object r15 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r15)     // Catch:{ all -> 0x00bf }
            com.google.android.exoplayer2.Format r15 = (com.google.android.exoplayer2.Format) r15     // Catch:{ all -> 0x00bf }
            r11.i(r15, r12)     // Catch:{ all -> 0x00bf }
            monitor-exit(r11)
            goto L_0x0095
        L_0x0037:
            monitor-exit(r11)
            goto L_0x0062
        L_0x0039:
            r13.setFlags(r6)     // Catch:{ all -> 0x00bf }
            monitor-exit(r11)
            goto L_0x008f
        L_0x003e:
            kb<com.google.android.exoplayer2.source.SampleQueue$b> r15 = r11.c     // Catch:{ all -> 0x00bf }
            int r4 = r11.getReadIndex()     // Catch:{ all -> 0x00bf }
            java.lang.Object r15 = r15.get(r4)     // Catch:{ all -> 0x00bf }
            com.google.android.exoplayer2.source.SampleQueue$b r15 = (com.google.android.exoplayer2.source.SampleQueue.b) r15     // Catch:{ all -> 0x00bf }
            com.google.android.exoplayer2.Format r15 = r15.a     // Catch:{ all -> 0x00bf }
            if (r0 != 0) goto L_0x0091
            com.google.android.exoplayer2.Format r0 = r11.h     // Catch:{ all -> 0x00bf }
            if (r15 == r0) goto L_0x0053
            goto L_0x0091
        L_0x0053:
            int r12 = r11.t     // Catch:{ all -> 0x00bf }
            int r12 = r11.g(r12)     // Catch:{ all -> 0x00bf }
            boolean r15 = r11.h(r12)     // Catch:{ all -> 0x00bf }
            if (r15 != 0) goto L_0x0064
            r13.h = r2     // Catch:{ all -> 0x00bf }
            monitor-exit(r11)
        L_0x0062:
            r12 = -3
            goto L_0x0096
        L_0x0064:
            int[] r15 = r11.n     // Catch:{ all -> 0x00bf }
            r15 = r15[r12]     // Catch:{ all -> 0x00bf }
            r13.setFlags(r15)     // Catch:{ all -> 0x00bf }
            long[] r15 = r11.o     // Catch:{ all -> 0x00bf }
            r7 = r15[r12]     // Catch:{ all -> 0x00bf }
            r13.i = r7     // Catch:{ all -> 0x00bf }
            long r9 = r11.u     // Catch:{ all -> 0x00bf }
            int r15 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r15 >= 0) goto L_0x007c
            r15 = -2147483648(0xffffffff80000000, float:-0.0)
            r13.addFlag(r15)     // Catch:{ all -> 0x00bf }
        L_0x007c:
            int[] r15 = r11.m     // Catch:{ all -> 0x00bf }
            r15 = r15[r12]     // Catch:{ all -> 0x00bf }
            r3.a = r15     // Catch:{ all -> 0x00bf }
            long[] r15 = r11.l     // Catch:{ all -> 0x00bf }
            r7 = r15[r12]     // Catch:{ all -> 0x00bf }
            r3.b = r7     // Catch:{ all -> 0x00bf }
            com.google.android.exoplayer2.extractor.TrackOutput$CryptoData[] r15 = r11.p     // Catch:{ all -> 0x00bf }
            r12 = r15[r12]     // Catch:{ all -> 0x00bf }
            r3.c = r12     // Catch:{ all -> 0x00bf }
            monitor-exit(r11)
        L_0x008f:
            r12 = -4
            goto L_0x0096
        L_0x0091:
            r11.i(r15, r12)     // Catch:{ all -> 0x00bf }
            monitor-exit(r11)
        L_0x0095:
            r12 = -5
        L_0x0096:
            if (r12 != r5) goto L_0x00be
            boolean r15 = r13.isEndOfStream()
            if (r15 != 0) goto L_0x00be
            r15 = r14 & 1
            if (r15 == 0) goto L_0x00a3
            r1 = 1
        L_0x00a3:
            r14 = r14 & r6
            if (r14 != 0) goto L_0x00b7
            if (r1 == 0) goto L_0x00b0
            com.google.android.exoplayer2.source.c r14 = r11.a
            com.google.android.exoplayer2.source.SampleQueue$a r15 = r11.b
            r14.peekToBuffer(r13, r15)
            goto L_0x00b7
        L_0x00b0:
            com.google.android.exoplayer2.source.c r14 = r11.a
            com.google.android.exoplayer2.source.SampleQueue$a r15 = r11.b
            r14.readToBuffer(r13, r15)
        L_0x00b7:
            if (r1 != 0) goto L_0x00be
            int r13 = r11.t
            int r13 = r13 + r2
            r11.t = r13
        L_0x00be:
            return r12
        L_0x00bf:
            r12 = move-exception
            monitor-exit(r11)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.SampleQueue.read(com.google.android.exoplayer2.FormatHolder, com.google.android.exoplayer2.decoder.DecoderInputBuffer, int, boolean):int");
    }

    @CallSuper
    public void release() {
        reset(true);
        DrmSession drmSession = this.i;
        if (drmSession != null) {
            drmSession.release(this.e);
            this.i = null;
            this.h = null;
        }
    }

    public final void reset() {
        reset(false);
    }

    public /* bridge */ /* synthetic */ int sampleData(DataReader dataReader, int i2, boolean z2) throws IOException {
        return oc.a(this, dataReader, i2, z2);
    }

    public final int sampleData(DataReader dataReader, int i2, boolean z2, int i3) throws IOException {
        return this.a.sampleData(dataReader, i2, z2);
    }

    public /* bridge */ /* synthetic */ void sampleData(ParsableByteArray parsableByteArray, int i2) {
        oc.b(this, parsableByteArray, i2);
    }

    public void sampleMetadata(long j2, int i2, int i3, int i4, @Nullable TrackOutput.CryptoData cryptoData) {
        boolean z2;
        boolean z3;
        DrmSessionManager.DrmSessionReference drmSessionReference;
        boolean z4;
        if (this.aa) {
            format((Format) Assertions.checkStateNotNull(this.ab));
        }
        int i5 = i2 & 1;
        if (i5 != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (this.y) {
            if (z2) {
                this.y = false;
            } else {
                return;
            }
        }
        long j3 = j2 + this.ag;
        if (this.ae) {
            if (j3 >= this.u) {
                if (i5 == 0) {
                    if (!this.af) {
                        String valueOf = String.valueOf(this.ac);
                        StringBuilder sb = new StringBuilder(valueOf.length() + 50);
                        sb.append("Overriding unexpected non-sync sample for format: ");
                        sb.append(valueOf);
                        Log.w("SampleQueue", sb.toString());
                        this.af = true;
                    }
                    i2 |= 1;
                }
            } else {
                return;
            }
        }
        if (this.ah) {
            if (z2 && a(j3)) {
                this.ah = false;
            } else {
                return;
            }
        }
        long totalBytesWritten = (this.a.getTotalBytesWritten() - ((long) i3)) - ((long) i4);
        synchronized (this) {
            int i6 = this.q;
            if (i6 > 0) {
                int g2 = g(i6 - 1);
                if (this.l[g2] + ((long) this.m[g2]) <= totalBytesWritten) {
                    z4 = true;
                } else {
                    z4 = false;
                }
                Assertions.checkArgument(z4);
            }
            if ((536870912 & i2) != 0) {
                z3 = true;
            } else {
                z3 = false;
            }
            this.x = z3;
            this.w = Math.max(this.w, j3);
            int g3 = g(this.q);
            this.o[g3] = j3;
            this.l[g3] = totalBytesWritten;
            this.m[g3] = i3;
            this.n[g3] = i2;
            this.p[g3] = cryptoData;
            this.k[g3] = this.ad;
            if (this.c.isEmpty() || !this.c.getEndValue().a.equals(this.ac)) {
                DrmSessionManager drmSessionManager = this.d;
                if (drmSessionManager != null) {
                    drmSessionReference = drmSessionManager.preacquireSession((Looper) Assertions.checkNotNull(this.f), this.e, this.ac);
                } else {
                    drmSessionReference = DrmSessionManager.DrmSessionReference.a;
                }
                this.c.appendSpan(getWriteIndex(), new b((Format) Assertions.checkNotNull(this.ac), drmSessionReference));
            }
            int i7 = this.q + 1;
            this.q = i7;
            int i8 = this.j;
            if (i7 == i8) {
                int i9 = i8 + 1000;
                int[] iArr = new int[i9];
                long[] jArr = new long[i9];
                long[] jArr2 = new long[i9];
                int[] iArr2 = new int[i9];
                int[] iArr3 = new int[i9];
                TrackOutput.CryptoData[] cryptoDataArr = new TrackOutput.CryptoData[i9];
                int i10 = this.s;
                int i11 = i8 - i10;
                System.arraycopy(this.l, i10, jArr, 0, i11);
                System.arraycopy(this.o, this.s, jArr2, 0, i11);
                System.arraycopy(this.n, this.s, iArr2, 0, i11);
                System.arraycopy(this.m, this.s, iArr3, 0, i11);
                System.arraycopy(this.p, this.s, cryptoDataArr, 0, i11);
                System.arraycopy(this.k, this.s, iArr, 0, i11);
                int i12 = this.s;
                System.arraycopy(this.l, 0, jArr, i11, i12);
                System.arraycopy(this.o, 0, jArr2, i11, i12);
                System.arraycopy(this.n, 0, iArr2, i11, i12);
                System.arraycopy(this.m, 0, iArr3, i11, i12);
                System.arraycopy(this.p, 0, cryptoDataArr, i11, i12);
                System.arraycopy(this.k, 0, iArr, i11, i12);
                this.l = jArr;
                this.o = jArr2;
                this.n = iArr2;
                this.m = iArr3;
                this.p = cryptoDataArr;
                this.k = iArr;
                this.s = 0;
                this.j = i9;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0019, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized boolean seekTo(int r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            r3.j()     // Catch:{ all -> 0x001b }
            int r0 = r3.r     // Catch:{ all -> 0x001b }
            if (r4 < r0) goto L_0x0018
            int r1 = r3.q     // Catch:{ all -> 0x001b }
            int r1 = r1 + r0
            if (r4 <= r1) goto L_0x000e
            goto L_0x0018
        L_0x000e:
            r1 = -9223372036854775808
            r3.u = r1     // Catch:{ all -> 0x001b }
            int r4 = r4 - r0
            r3.t = r4     // Catch:{ all -> 0x001b }
            monitor-exit(r3)
            r4 = 1
            return r4
        L_0x0018:
            monitor-exit(r3)
            r4 = 0
            return r4
        L_0x001b:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.SampleQueue.seekTo(int):boolean");
    }

    public final void setSampleOffsetUs(long j2) {
        if (this.ag != j2) {
            this.ag = j2;
            this.aa = true;
        }
    }

    public final void setStartTimeUs(long j2) {
        this.u = j2;
    }

    public final void setUpstreamFormatChangeListener(@Nullable UpstreamFormatChangedListener upstreamFormatChangedListener) {
        this.g = upstreamFormatChangedListener;
    }

    public final synchronized void skip(int i2) {
        boolean z2;
        if (i2 >= 0) {
            try {
                if (this.t + i2 <= this.q) {
                    z2 = true;
                    Assertions.checkArgument(z2);
                    this.t += i2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        z2 = false;
        Assertions.checkArgument(z2);
        this.t += i2;
    }

    public final void sourceId(int i2) {
        this.ad = i2;
    }

    public final void splice() {
        this.ah = true;
    }

    @CallSuper
    public void reset(boolean z2) {
        this.a.reset();
        this.q = 0;
        this.r = 0;
        this.s = 0;
        this.t = 0;
        this.y = true;
        this.u = Long.MIN_VALUE;
        this.v = Long.MIN_VALUE;
        this.w = Long.MIN_VALUE;
        this.x = false;
        this.c.clear();
        if (z2) {
            this.ab = null;
            this.ac = null;
            this.z = true;
        }
    }

    public final void sampleData(ParsableByteArray parsableByteArray, int i2, int i3) {
        this.a.sampleData(parsableByteArray, i2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0040, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized boolean seekTo(long r10, boolean r12) {
        /*
            r9 = this;
            monitor-enter(r9)
            r9.j()     // Catch:{ all -> 0x0041 }
            int r0 = r9.t     // Catch:{ all -> 0x0041 }
            int r2 = r9.g(r0)     // Catch:{ all -> 0x0041 }
            int r0 = r9.t     // Catch:{ all -> 0x0041 }
            int r1 = r9.q     // Catch:{ all -> 0x0041 }
            r7 = 1
            r8 = 0
            if (r0 == r1) goto L_0x0014
            r3 = 1
            goto L_0x0015
        L_0x0014:
            r3 = 0
        L_0x0015:
            if (r3 == 0) goto L_0x003f
            long[] r3 = r9.o     // Catch:{ all -> 0x0041 }
            r4 = r3[r2]     // Catch:{ all -> 0x0041 }
            int r3 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r3 < 0) goto L_0x003f
            long r3 = r9.w     // Catch:{ all -> 0x0041 }
            int r5 = (r10 > r3 ? 1 : (r10 == r3 ? 0 : -1))
            if (r5 <= 0) goto L_0x0028
            if (r12 != 0) goto L_0x0028
            goto L_0x003f
        L_0x0028:
            int r3 = r1 - r0
            r6 = 1
            r1 = r9
            r4 = r10
            int r12 = r1.e(r2, r3, r4, r6)     // Catch:{ all -> 0x0041 }
            r0 = -1
            if (r12 != r0) goto L_0x0036
            monitor-exit(r9)
            return r8
        L_0x0036:
            r9.u = r10     // Catch:{ all -> 0x0041 }
            int r10 = r9.t     // Catch:{ all -> 0x0041 }
            int r10 = r10 + r12
            r9.t = r10     // Catch:{ all -> 0x0041 }
            monitor-exit(r9)
            return r7
        L_0x003f:
            monitor-exit(r9)
            return r8
        L_0x0041:
            r10 = move-exception
            monitor-exit(r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.SampleQueue.seekTo(long, boolean):boolean");
    }
}
