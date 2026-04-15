package com.google.android.exoplayer2.source.chunk;

import android.os.Looper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.SequenceableLoader;
import com.google.android.exoplayer2.source.chunk.ChunkSource;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChunkSampleStream<T extends ChunkSource> implements SampleStream, SequenceableLoader, Loader.Callback<Chunk>, Loader.ReleaseCallback {
    public final int c;
    public final int[] f;
    public final Format[] g;
    public final boolean[] h;
    public final T i;
    public final SequenceableLoader.Callback<ChunkSampleStream<T>> j;
    public final MediaSourceEventListener.EventDispatcher k;
    public final LoadErrorHandlingPolicy l;
    public final Loader m;
    public final ChunkHolder n;
    public final ArrayList<BaseMediaChunk> o;
    public final List<BaseMediaChunk> p;
    public final SampleQueue q;
    public final SampleQueue[] r;
    public Format s;
    @Nullable
    public ReleaseCallback<T> t;
    public long u;
    public long v;
    public int w;
    @Nullable
    public BaseMediaChunk x;

    public final class EmbeddedSampleStream implements SampleStream {
        public final SampleQueue c;
        public final int f;
        public boolean g;

        public EmbeddedSampleStream(ChunkSampleStream<T> chunkSampleStream, SampleQueue sampleQueue, int i) {
            this.c = sampleQueue;
            this.f = i;
        }

        public final void a() {
            if (!this.g) {
                ChunkSampleStream chunkSampleStream = ChunkSampleStream.this;
                MediaSourceEventListener.EventDispatcher eventDispatcher = chunkSampleStream.k;
                int[] iArr = chunkSampleStream.f;
                int i = this.f;
                eventDispatcher.downstreamFormatChanged(iArr[i], chunkSampleStream.g[i], 0, (Object) null, chunkSampleStream.v);
                this.g = true;
            }
        }

        public boolean isReady() {
            return !ChunkSampleStream.this.d() && this.c.isReady(false);
        }

        public void maybeThrowError() {
        }

        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i) {
            ChunkSampleStream chunkSampleStream = ChunkSampleStream.this;
            if (chunkSampleStream.d()) {
                return -3;
            }
            BaseMediaChunk baseMediaChunk = chunkSampleStream.x;
            SampleQueue sampleQueue = this.c;
            if (baseMediaChunk != null && baseMediaChunk.getFirstSampleIndex(this.f + 1) <= sampleQueue.getReadIndex()) {
                return -3;
            }
            a();
            chunkSampleStream.getClass();
            return sampleQueue.read(formatHolder, decoderInputBuffer, i, false);
        }

        public void release() {
            ChunkSampleStream chunkSampleStream = ChunkSampleStream.this;
            boolean[] zArr = chunkSampleStream.h;
            int i = this.f;
            Assertions.checkState(zArr[i]);
            chunkSampleStream.h[i] = false;
        }

        public int skipData(long j) {
            ChunkSampleStream chunkSampleStream = ChunkSampleStream.this;
            if (chunkSampleStream.d()) {
                return 0;
            }
            chunkSampleStream.getClass();
            SampleQueue sampleQueue = this.c;
            int skipCount = sampleQueue.getSkipCount(j, false);
            BaseMediaChunk baseMediaChunk = chunkSampleStream.x;
            if (baseMediaChunk != null) {
                skipCount = Math.min(skipCount, baseMediaChunk.getFirstSampleIndex(this.f + 1) - sampleQueue.getReadIndex());
            }
            sampleQueue.skip(skipCount);
            if (skipCount > 0) {
                a();
            }
            return skipCount;
        }
    }

    public interface ReleaseCallback<T extends ChunkSource> {
        void onSampleStreamReleased(ChunkSampleStream<T> chunkSampleStream);
    }

    public ChunkSampleStream(int i2, @Nullable int[] iArr, @Nullable Format[] formatArr, T t2, SequenceableLoader.Callback<ChunkSampleStream<T>> callback, Allocator allocator, long j2, DrmSessionManager drmSessionManager, DrmSessionEventListener.EventDispatcher eventDispatcher, LoadErrorHandlingPolicy loadErrorHandlingPolicy, MediaSourceEventListener.EventDispatcher eventDispatcher2) {
        this.c = i2;
        int i3 = 0;
        iArr = iArr == null ? new int[0] : iArr;
        this.f = iArr;
        this.g = formatArr == null ? new Format[0] : formatArr;
        this.i = t2;
        this.j = callback;
        this.k = eventDispatcher2;
        this.l = loadErrorHandlingPolicy;
        this.m = new Loader("ChunkSampleStream");
        this.n = new ChunkHolder();
        ArrayList<BaseMediaChunk> arrayList = new ArrayList<>();
        this.o = arrayList;
        this.p = Collections.unmodifiableList(arrayList);
        int length = iArr.length;
        this.r = new SampleQueue[length];
        this.h = new boolean[length];
        int i4 = length + 1;
        int[] iArr2 = new int[i4];
        SampleQueue[] sampleQueueArr = new SampleQueue[i4];
        SampleQueue createWithDrm = SampleQueue.createWithDrm(allocator, (Looper) Assertions.checkNotNull(Looper.myLooper()), drmSessionManager, eventDispatcher);
        this.q = createWithDrm;
        iArr2[0] = i2;
        sampleQueueArr[0] = createWithDrm;
        while (i3 < length) {
            SampleQueue createWithoutDrm = SampleQueue.createWithoutDrm(allocator);
            this.r[i3] = createWithoutDrm;
            int i5 = i3 + 1;
            sampleQueueArr[i5] = createWithoutDrm;
            iArr2[i5] = this.f[i3];
            i3 = i5;
        }
        new BaseMediaChunkOutput(iArr2, sampleQueueArr);
        this.u = j2;
        this.v = j2;
    }

    public final BaseMediaChunk a(int i2) {
        ArrayList<BaseMediaChunk> arrayList = this.o;
        BaseMediaChunk baseMediaChunk = arrayList.get(i2);
        Util.removeRange(arrayList, i2, arrayList.size());
        this.w = Math.max(this.w, arrayList.size());
        int i3 = 0;
        this.q.discardUpstreamSamples(baseMediaChunk.getFirstSampleIndex(0));
        while (true) {
            SampleQueue[] sampleQueueArr = this.r;
            if (i3 >= sampleQueueArr.length) {
                return baseMediaChunk;
            }
            SampleQueue sampleQueue = sampleQueueArr[i3];
            i3++;
            sampleQueue.discardUpstreamSamples(baseMediaChunk.getFirstSampleIndex(i3));
        }
    }

    public final BaseMediaChunk b() {
        ArrayList<BaseMediaChunk> arrayList = this.o;
        return arrayList.get(arrayList.size() - 1);
    }

    public final boolean c(int i2) {
        int readIndex;
        BaseMediaChunk baseMediaChunk = this.o.get(i2);
        if (this.q.getReadIndex() > baseMediaChunk.getFirstSampleIndex(0)) {
            return true;
        }
        int i3 = 0;
        do {
            SampleQueue[] sampleQueueArr = this.r;
            if (i3 >= sampleQueueArr.length) {
                return false;
            }
            readIndex = sampleQueueArr[i3].getReadIndex();
            i3++;
        } while (readIndex <= baseMediaChunk.getFirstSampleIndex(i3));
        return true;
    }

    public boolean continueLoading(long j2) {
        long j3;
        List<BaseMediaChunk> list;
        Loader loader = this.m;
        if (loader.isLoading() || loader.hasFatalError()) {
            return false;
        }
        if (d()) {
            list = Collections.emptyList();
            j3 = this.u;
        } else {
            j3 = b().h;
            list = this.p;
        }
        T t2 = this.i;
        long j4 = j2;
        t2.getNextChunk(j4, j3, list, this.n);
        ChunkHolder chunkHolder = this.n;
        chunkHolder.getClass();
        chunkHolder.clear();
        return false;
    }

    public final boolean d() {
        return this.u != -9223372036854775807L;
    }

    public void discardBuffer(long j2, boolean z) {
        if (!d()) {
            SampleQueue sampleQueue = this.q;
            int firstIndex = sampleQueue.getFirstIndex();
            sampleQueue.discardTo(j2, z, true);
            int firstIndex2 = sampleQueue.getFirstIndex();
            if (firstIndex2 > firstIndex) {
                long firstTimestampUs = sampleQueue.getFirstTimestampUs();
                int i2 = 0;
                while (true) {
                    SampleQueue[] sampleQueueArr = this.r;
                    if (i2 >= sampleQueueArr.length) {
                        break;
                    }
                    sampleQueueArr[i2].discardTo(firstTimestampUs, z, this.h[i2]);
                    i2++;
                }
            }
            int min = Math.min(f(firstIndex2, 0), this.w);
            if (min > 0) {
                Util.removeRange(this.o, 0, min);
                this.w -= min;
            }
        }
    }

    public final void e() {
        int f2 = f(this.q.getReadIndex(), this.w - 1);
        while (true) {
            int i2 = this.w;
            if (i2 <= f2) {
                this.w = i2 + 1;
                BaseMediaChunk baseMediaChunk = this.o.get(i2);
                Format format = baseMediaChunk.d;
                if (!format.equals(this.s)) {
                    this.k.downstreamFormatChanged(this.c, format, baseMediaChunk.e, baseMediaChunk.f, baseMediaChunk.g);
                }
                this.s = format;
            } else {
                return;
            }
        }
    }

    public final int f(int i2, int i3) {
        ArrayList<BaseMediaChunk> arrayList;
        do {
            i3++;
            arrayList = this.o;
            if (i3 >= arrayList.size()) {
                return arrayList.size() - 1;
            }
        } while (arrayList.get(i3).getFirstSampleIndex(0) <= i2);
        return i3 - 1;
    }

    public long getAdjustedSeekPositionUs(long j2, SeekParameters seekParameters) {
        return this.i.getAdjustedSeekPositionUs(j2, seekParameters);
    }

    public long getBufferedPositionUs() {
        if (d()) {
            return this.u;
        }
        long j2 = this.v;
        BaseMediaChunk b = b();
        if (!b.isLoadCompleted()) {
            ArrayList<BaseMediaChunk> arrayList = this.o;
            if (arrayList.size() > 1) {
                b = arrayList.get(arrayList.size() - 2);
            } else {
                b = null;
            }
        }
        if (b != null) {
            j2 = Math.max(j2, b.h);
        }
        return Math.max(j2, this.q.getLargestQueuedTimestampUs());
    }

    public T getChunkSource() {
        return this.i;
    }

    public long getNextLoadPositionUs() {
        if (d()) {
            return this.u;
        }
        return b().h;
    }

    public boolean isLoading() {
        return this.m.isLoading();
    }

    public boolean isReady() {
        return !d() && this.q.isReady(false);
    }

    public void maybeThrowError() throws IOException {
        Loader loader = this.m;
        loader.maybeThrowError();
        this.q.maybeThrowError();
        if (!loader.isLoading()) {
            this.i.maybeThrowError();
        }
    }

    public void onLoaderReleased() {
        this.q.release();
        for (SampleQueue release : this.r) {
            release.release();
        }
        this.i.release();
        ReleaseCallback<T> releaseCallback = this.t;
        if (releaseCallback != null) {
            releaseCallback.onSampleStreamReleased(this);
        }
    }

    public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i2) {
        if (d()) {
            return -3;
        }
        BaseMediaChunk baseMediaChunk = this.x;
        SampleQueue sampleQueue = this.q;
        if (baseMediaChunk != null && baseMediaChunk.getFirstSampleIndex(0) <= sampleQueue.getReadIndex()) {
            return -3;
        }
        e();
        return sampleQueue.read(formatHolder, decoderInputBuffer, i2, false);
    }

    public void reevaluateBuffer(long j2) {
        Loader loader = this.m;
        if (!loader.hasFatalError() && !d()) {
            boolean isLoading = loader.isLoading();
            ArrayList<BaseMediaChunk> arrayList = this.o;
            List<BaseMediaChunk> list = this.p;
            T t2 = this.i;
            if (isLoading) {
                Chunk chunk = (Chunk) Assertions.checkNotNull(null);
                boolean z = chunk instanceof BaseMediaChunk;
                if ((!z || !c(arrayList.size() - 1)) && t2.shouldCancelLoad(j2, chunk, list)) {
                    loader.cancelLoading();
                    if (z) {
                        this.x = (BaseMediaChunk) chunk;
                        return;
                    }
                    return;
                }
                return;
            }
            int preferredQueueSize = t2.getPreferredQueueSize(j2, list);
            if (preferredQueueSize < arrayList.size()) {
                Assertions.checkState(!loader.isLoading());
                int size = arrayList.size();
                while (true) {
                    if (preferredQueueSize >= size) {
                        preferredQueueSize = -1;
                        break;
                    } else if (!c(preferredQueueSize)) {
                        break;
                    } else {
                        preferredQueueSize++;
                    }
                }
                if (preferredQueueSize != -1) {
                    long j3 = b().h;
                    BaseMediaChunk a = a(preferredQueueSize);
                    if (arrayList.isEmpty()) {
                        this.u = this.v;
                    }
                    this.k.upstreamDiscarded(this.c, a.g, j3);
                }
            }
        }
    }

    public void release() {
        release((ReleaseCallback) null);
    }

    public void seekToUs(long j2) {
        ArrayList<BaseMediaChunk> arrayList;
        BaseMediaChunk baseMediaChunk;
        boolean z;
        boolean z2;
        this.v = j2;
        if (d()) {
            this.u = j2;
            return;
        }
        int i2 = 0;
        int i3 = 0;
        while (true) {
            arrayList = this.o;
            if (i3 >= arrayList.size()) {
                break;
            }
            baseMediaChunk = arrayList.get(i3);
            int i4 = (baseMediaChunk.g > j2 ? 1 : (baseMediaChunk.g == j2 ? 0 : -1));
            if (i4 == 0 && baseMediaChunk.k == -9223372036854775807L) {
                break;
            } else if (i4 > 0) {
                break;
            } else {
                i3++;
            }
        }
        baseMediaChunk = null;
        SampleQueue sampleQueue = this.q;
        if (baseMediaChunk != null) {
            z = sampleQueue.seekTo(baseMediaChunk.getFirstSampleIndex(0));
        } else {
            if (j2 < getNextLoadPositionUs()) {
                z2 = true;
            } else {
                z2 = false;
            }
            z = sampleQueue.seekTo(j2, z2);
        }
        SampleQueue[] sampleQueueArr = this.r;
        if (z) {
            this.w = f(sampleQueue.getReadIndex(), 0);
            int length = sampleQueueArr.length;
            while (i2 < length) {
                sampleQueueArr[i2].seekTo(j2, true);
                i2++;
            }
            return;
        }
        this.u = j2;
        arrayList.clear();
        this.w = 0;
        Loader loader = this.m;
        if (loader.isLoading()) {
            sampleQueue.discardToEnd();
            int length2 = sampleQueueArr.length;
            while (i2 < length2) {
                sampleQueueArr[i2].discardToEnd();
                i2++;
            }
            loader.cancelLoading();
            return;
        }
        loader.clearFatalError();
        sampleQueue.reset();
        int length3 = sampleQueueArr.length;
        while (i2 < length3) {
            sampleQueueArr[i2].reset();
            i2++;
        }
    }

    public ChunkSampleStream<T>.EmbeddedSampleStream selectEmbeddedTrack(long j2, int i2) {
        int i3 = 0;
        while (true) {
            SampleQueue[] sampleQueueArr = this.r;
            if (i3 >= sampleQueueArr.length) {
                throw new IllegalStateException();
            } else if (this.f[i3] == i2) {
                boolean[] zArr = this.h;
                Assertions.checkState(!zArr[i3]);
                zArr[i3] = true;
                sampleQueueArr[i3].seekTo(j2, true);
                return new EmbeddedSampleStream(this, sampleQueueArr[i3], i3);
            } else {
                i3++;
            }
        }
    }

    public int skipData(long j2) {
        if (d()) {
            return 0;
        }
        SampleQueue sampleQueue = this.q;
        int skipCount = sampleQueue.getSkipCount(j2, false);
        BaseMediaChunk baseMediaChunk = this.x;
        if (baseMediaChunk != null) {
            skipCount = Math.min(skipCount, baseMediaChunk.getFirstSampleIndex(0) - sampleQueue.getReadIndex());
        }
        sampleQueue.skip(skipCount);
        e();
        return skipCount;
    }

    public void onLoadCanceled(Chunk chunk, long j2, long j3, boolean z) {
        Chunk chunk2 = chunk;
        this.x = null;
        LoadEventInfo loadEventInfo = new LoadEventInfo(chunk2.a, chunk2.b, chunk.getUri(), chunk.getResponseHeaders(), j2, j3, chunk.bytesLoaded());
        this.l.onLoadTaskConcluded(chunk2.a);
        this.k.loadCanceled(loadEventInfo, chunk2.c, this.c, chunk2.d, chunk2.e, chunk2.f, chunk2.g, chunk2.h);
        if (!z) {
            if (d()) {
                this.q.reset();
                for (SampleQueue reset : this.r) {
                    reset.reset();
                }
            } else if (chunk2 instanceof BaseMediaChunk) {
                ArrayList<BaseMediaChunk> arrayList = this.o;
                a(arrayList.size() - 1);
                if (arrayList.isEmpty()) {
                    this.u = this.v;
                }
            }
            this.j.onContinueLoadingRequested(this);
        }
    }

    public void onLoadCompleted(Chunk chunk, long j2, long j3) {
        Chunk chunk2 = chunk;
        this.i.onChunkLoadCompleted(chunk2);
        LoadEventInfo loadEventInfo = new LoadEventInfo(chunk2.a, chunk2.b, chunk.getUri(), chunk.getResponseHeaders(), j2, j3, chunk.bytesLoaded());
        this.l.onLoadTaskConcluded(chunk2.a);
        this.k.loadCompleted(loadEventInfo, chunk2.c, this.c, chunk2.d, chunk2.e, chunk2.f, chunk2.g, chunk2.h);
        this.j.onContinueLoadingRequested(this);
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x00b8  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0100  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x010d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.exoplayer2.upstream.Loader.LoadErrorAction onLoadError(com.google.android.exoplayer2.source.chunk.Chunk r36, long r37, long r39, java.io.IOException r41, int r42) {
        /*
            r35 = this;
            r0 = r35
            r7 = r36
            long r18 = r36.bytesLoaded()
            boolean r5 = r7 instanceof com.google.android.exoplayer2.source.chunk.BaseMediaChunk
            java.util.ArrayList<com.google.android.exoplayer2.source.chunk.BaseMediaChunk> r6 = r0.o
            int r1 = r6.size()
            int r4 = r1 + -1
            r1 = 0
            r3 = 0
            r20 = 1
            int r8 = (r18 > r1 ? 1 : (r18 == r1 ? 0 : -1))
            if (r8 == 0) goto L_0x0027
            if (r5 == 0) goto L_0x0027
            boolean r1 = r0.c(r4)
            if (r1 != 0) goto L_0x0024
            goto L_0x0027
        L_0x0024:
            r21 = 0
            goto L_0x0029
        L_0x0027:
            r21 = 1
        L_0x0029:
            com.google.android.exoplayer2.source.LoadEventInfo r2 = new com.google.android.exoplayer2.source.LoadEventInfo
            long r9 = r7.a
            com.google.android.exoplayer2.upstream.DataSpec r11 = r7.b
            android.net.Uri r12 = r36.getUri()
            java.util.Map r13 = r36.getResponseHeaders()
            r8 = r2
            r14 = r37
            r16 = r39
            r8.<init>(r9, r11, r12, r13, r14, r16, r18)
            com.google.android.exoplayer2.source.MediaLoadData r1 = new com.google.android.exoplayer2.source.MediaLoadData
            int r8 = r7.c
            int r9 = r0.c
            com.google.android.exoplayer2.Format r10 = r7.d
            int r11 = r7.e
            java.lang.Object r12 = r7.f
            long r13 = r7.g
            long r28 = com.google.android.exoplayer2.C.usToMs(r13)
            long r13 = r7.h
            long r30 = com.google.android.exoplayer2.C.usToMs(r13)
            r22 = r1
            r23 = r8
            r24 = r9
            r25 = r10
            r26 = r11
            r27 = r12
            r22.<init>(r23, r24, r25, r26, r27, r28, r30)
            com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy$LoadErrorInfo r8 = new com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy$LoadErrorInfo
            r9 = r41
            r10 = r42
            r8.<init>(r2, r1, r9, r10)
            com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy r12 = r0.l
            if (r21 == 0) goto L_0x0078
            long r13 = r12.getBlacklistDurationMsFor(r8)
            goto L_0x007d
        L_0x0078:
            r13 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
        L_0x007d:
            T r1 = r0.i
            r15 = r2
            r2 = r36
            r3 = r21
            r10 = r4
            r4 = r41
            r11 = r5
            r16 = r6
            r5 = r13
            boolean r1 = r1.onChunkLoadError(r2, r3, r4, r5)
            if (r1 == 0) goto L_0x00b5
            if (r21 == 0) goto L_0x00ae
            com.google.android.exoplayer2.upstream.Loader$LoadErrorAction r1 = com.google.android.exoplayer2.upstream.Loader.d
            if (r11 == 0) goto L_0x00b6
            com.google.android.exoplayer2.source.chunk.BaseMediaChunk r2 = r0.a(r10)
            if (r2 != r7) goto L_0x009f
            r3 = 1
            goto L_0x00a0
        L_0x009f:
            r3 = 0
        L_0x00a0:
            com.google.android.exoplayer2.util.Assertions.checkState(r3)
            boolean r2 = r16.isEmpty()
            if (r2 == 0) goto L_0x00b6
            long r2 = r0.v
            r0.u = r2
            goto L_0x00b6
        L_0x00ae:
            java.lang.String r1 = "ChunkSampleStream"
            java.lang.String r2 = "Ignoring attempt to cancel non-cancelable load."
            com.google.android.exoplayer2.util.Log.w(r1, r2)
        L_0x00b5:
            r1 = 0
        L_0x00b6:
            if (r1 != 0) goto L_0x00cd
            long r1 = r12.getRetryDelayMsFor(r8)
            r3 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 == 0) goto L_0x00cb
            r3 = 0
            com.google.android.exoplayer2.upstream.Loader$LoadErrorAction r1 = com.google.android.exoplayer2.upstream.Loader.createRetryAction(r3, r1)
            goto L_0x00cd
        L_0x00cb:
            com.google.android.exoplayer2.upstream.Loader$LoadErrorAction r1 = com.google.android.exoplayer2.upstream.Loader.e
        L_0x00cd:
            boolean r2 = r1.isRetry()
            r2 = r2 ^ 1
            com.google.android.exoplayer2.source.MediaSourceEventListener$EventDispatcher r3 = r0.k
            int r4 = r7.c
            int r5 = r0.c
            com.google.android.exoplayer2.Format r6 = r7.d
            int r8 = r7.e
            java.lang.Object r10 = r7.f
            long r13 = r7.g
            r37 = r1
            long r0 = r7.h
            r22 = r3
            r23 = r15
            r24 = r4
            r25 = r5
            r26 = r6
            r27 = r8
            r28 = r10
            r29 = r13
            r31 = r0
            r33 = r41
            r34 = r2
            r22.loadError(r23, r24, r25, r26, r27, r28, r29, r31, r33, r34)
            if (r2 == 0) goto L_0x010d
            long r0 = r7.a
            r12.onLoadTaskConcluded(r0)
            r0 = r35
            com.google.android.exoplayer2.source.SequenceableLoader$Callback<com.google.android.exoplayer2.source.chunk.ChunkSampleStream<T>> r1 = r0.j
            r1.onContinueLoadingRequested(r0)
            goto L_0x010f
        L_0x010d:
            r0 = r35
        L_0x010f:
            return r37
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.chunk.ChunkSampleStream.onLoadError(com.google.android.exoplayer2.source.chunk.Chunk, long, long, java.io.IOException, int):com.google.android.exoplayer2.upstream.Loader$LoadErrorAction");
    }

    public void release(@Nullable ReleaseCallback<T> releaseCallback) {
        this.t = releaseCallback;
        this.q.preRelease();
        for (SampleQueue preRelease : this.r) {
            preRelease.preRelease();
        }
        this.m.release(this);
    }
}
