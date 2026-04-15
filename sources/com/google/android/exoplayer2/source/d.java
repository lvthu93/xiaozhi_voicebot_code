package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.upstream.StatsDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class d implements MediaPeriod, Loader.Callback<b> {
    public final DataSpec c;
    public final DataSource.Factory f;
    @Nullable
    public final TransferListener g;
    public final LoadErrorHandlingPolicy h;
    public final MediaSourceEventListener.EventDispatcher i;
    public final TrackGroupArray j;
    public final ArrayList<a> k = new ArrayList<>();
    public final long l;
    public final Loader m = new Loader("SingleSampleMediaPeriod");
    public final Format n;
    public final boolean o;
    public boolean p;
    public byte[] q;
    public int r;

    public final class a implements SampleStream {
        public int c;
        public boolean f;

        public a() {
        }

        public final void a() {
            if (!this.f) {
                d dVar = d.this;
                dVar.i.downstreamFormatChanged(MimeTypes.getTrackType(dVar.n.p), dVar.n, 0, (Object) null, 0);
                this.f = true;
            }
        }

        public boolean isReady() {
            return d.this.p;
        }

        public void maybeThrowError() throws IOException {
            d dVar = d.this;
            if (!dVar.o) {
                dVar.m.maybeThrowError();
            }
        }

        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i) {
            a();
            int i2 = this.c;
            if (i2 == 2) {
                decoderInputBuffer.addFlag(4);
                return -4;
            }
            int i3 = i & 2;
            d dVar = d.this;
            if (i3 != 0 || i2 == 0) {
                formatHolder.b = dVar.n;
                this.c = 1;
                return -5;
            } else if (!dVar.p) {
                return -3;
            } else {
                if (dVar.q == null) {
                    decoderInputBuffer.addFlag(4);
                    this.c = 2;
                    return -4;
                }
                decoderInputBuffer.addFlag(1);
                decoderInputBuffer.i = 0;
                if ((i & 4) == 0) {
                    decoderInputBuffer.ensureSpaceForWrite(dVar.r);
                    decoderInputBuffer.g.put(dVar.q, 0, dVar.r);
                }
                if ((i & 1) == 0) {
                    this.c = 2;
                }
                return -4;
            }
        }

        public void reset() {
            if (this.c == 2) {
                this.c = 1;
            }
        }

        public int skipData(long j) {
            a();
            if (j <= 0 || this.c == 2) {
                return 0;
            }
            this.c = 2;
            return 1;
        }
    }

    public static final class b implements Loader.Loadable {
        public final long a = LoadEventInfo.getNewId();
        public final DataSpec b;
        public final StatsDataSource c;
        @Nullable
        public byte[] d;

        public b(DataSpec dataSpec, DataSource dataSource) {
            this.b = dataSpec;
            this.c = new StatsDataSource(dataSource);
        }

        public void cancelLoad() {
        }

        public void load() throws IOException {
            StatsDataSource statsDataSource = this.c;
            statsDataSource.resetBytesRead();
            try {
                statsDataSource.open(this.b);
                int i = 0;
                while (i != -1) {
                    int bytesRead = (int) statsDataSource.getBytesRead();
                    byte[] bArr = this.d;
                    if (bArr == null) {
                        this.d = new byte[1024];
                    } else if (bytesRead == bArr.length) {
                        this.d = Arrays.copyOf(bArr, bArr.length * 2);
                    }
                    byte[] bArr2 = this.d;
                    i = statsDataSource.read(bArr2, bytesRead, bArr2.length - bytesRead);
                }
            } finally {
                Util.closeQuietly((DataSource) statsDataSource);
            }
        }
    }

    public d(DataSpec dataSpec, DataSource.Factory factory, @Nullable TransferListener transferListener, Format format, long j2, LoadErrorHandlingPolicy loadErrorHandlingPolicy, MediaSourceEventListener.EventDispatcher eventDispatcher, boolean z) {
        this.c = dataSpec;
        this.f = factory;
        this.g = transferListener;
        this.n = format;
        this.l = j2;
        this.h = loadErrorHandlingPolicy;
        this.i = eventDispatcher;
        this.o = z;
        this.j = new TrackGroupArray(new TrackGroup(format));
    }

    public boolean continueLoading(long j2) {
        if (this.p) {
            return false;
        }
        Loader loader = this.m;
        if (loader.isLoading() || loader.hasFatalError()) {
            return false;
        }
        DataSource createDataSource = this.f.createDataSource();
        TransferListener transferListener = this.g;
        if (transferListener != null) {
            createDataSource.addTransferListener(transferListener);
        }
        b bVar = new b(this.c, createDataSource);
        this.i.loadStarted(new LoadEventInfo(bVar.a, this.c, loader.startLoading(bVar, this, this.h.getMinimumLoadableRetryCount(1))), 1, -1, this.n, 0, (Object) null, 0, this.l);
        return true;
    }

    public void discardBuffer(long j2, boolean z) {
    }

    public long getAdjustedSeekPositionUs(long j2, SeekParameters seekParameters) {
        return j2;
    }

    public long getBufferedPositionUs() {
        return this.p ? Long.MIN_VALUE : 0;
    }

    public long getNextLoadPositionUs() {
        return (this.p || this.m.isLoading()) ? Long.MIN_VALUE : 0;
    }

    public /* bridge */ /* synthetic */ List getStreamKeys(List list) {
        return a6.a(this, list);
    }

    public TrackGroupArray getTrackGroups() {
        return this.j;
    }

    public boolean isLoading() {
        return this.m.isLoading();
    }

    public void maybeThrowPrepareError() {
    }

    public void prepare(MediaPeriod.Callback callback, long j2) {
        callback.onPrepared(this);
    }

    public long readDiscontinuity() {
        return -9223372036854775807L;
    }

    public void reevaluateBuffer(long j2) {
    }

    public void release() {
        this.m.release();
    }

    public long seekToUs(long j2) {
        int i2 = 0;
        while (true) {
            ArrayList<a> arrayList = this.k;
            if (i2 >= arrayList.size()) {
                return j2;
            }
            arrayList.get(i2).reset();
            i2++;
        }
    }

    public long selectTracks(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j2) {
        for (int i2 = 0; i2 < exoTrackSelectionArr.length; i2++) {
            SampleStream sampleStream = sampleStreamArr[i2];
            ArrayList<a> arrayList = this.k;
            if (sampleStream != null && (exoTrackSelectionArr[i2] == null || !zArr[i2])) {
                arrayList.remove(sampleStream);
                sampleStreamArr[i2] = null;
            }
            if (sampleStreamArr[i2] == null && exoTrackSelectionArr[i2] != null) {
                a aVar = new a();
                arrayList.add(aVar);
                sampleStreamArr[i2] = aVar;
                zArr2[i2] = true;
            }
        }
        return j2;
    }

    public void onLoadCanceled(b bVar, long j2, long j3, boolean z) {
        b bVar2 = bVar;
        StatsDataSource statsDataSource = bVar2.c;
        LoadEventInfo loadEventInfo = new LoadEventInfo(bVar2.a, bVar2.b, statsDataSource.getLastOpenedUri(), statsDataSource.getLastResponseHeaders(), j2, j3, statsDataSource.getBytesRead());
        this.h.onLoadTaskConcluded(bVar2.a);
        this.i.loadCanceled(loadEventInfo, 1, -1, (Format) null, 0, (Object) null, 0, this.l);
    }

    public void onLoadCompleted(b bVar, long j2, long j3) {
        b bVar2 = bVar;
        this.r = (int) bVar2.c.getBytesRead();
        this.q = (byte[]) Assertions.checkNotNull(bVar2.d);
        this.p = true;
        long j4 = bVar2.a;
        DataSpec dataSpec = bVar2.b;
        StatsDataSource statsDataSource = bVar2.c;
        LoadEventInfo loadEventInfo = new LoadEventInfo(j4, dataSpec, statsDataSource.getLastOpenedUri(), statsDataSource.getLastResponseHeaders(), j2, j3, (long) this.r);
        this.h.onLoadTaskConcluded(bVar2.a);
        this.i.loadCompleted(loadEventInfo, 1, -1, this.n, 0, (Object) null, 0, this.l);
    }

    public Loader.LoadErrorAction onLoadError(b bVar, long j2, long j3, IOException iOException, int i2) {
        Loader.LoadErrorAction loadErrorAction;
        b bVar2 = bVar;
        IOException iOException2 = iOException;
        int i3 = i2;
        StatsDataSource statsDataSource = bVar2.c;
        LoadEventInfo loadEventInfo = new LoadEventInfo(bVar2.a, bVar2.b, statsDataSource.getLastOpenedUri(), statsDataSource.getLastResponseHeaders(), j2, j3, statsDataSource.getBytesRead());
        LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo = new LoadErrorHandlingPolicy.LoadErrorInfo(loadEventInfo, new MediaLoadData(1, -1, this.n, 0, (Object) null, 0, C.usToMs(this.l)), iOException2, i3);
        LoadErrorHandlingPolicy loadErrorHandlingPolicy = this.h;
        long retryDelayMsFor = loadErrorHandlingPolicy.getRetryDelayMsFor(loadErrorInfo);
        int i4 = (retryDelayMsFor > -9223372036854775807L ? 1 : (retryDelayMsFor == -9223372036854775807L ? 0 : -1));
        boolean z = i4 == 0 || i3 >= loadErrorHandlingPolicy.getMinimumLoadableRetryCount(1);
        if (this.o && z) {
            Log.w("SingleSampleMediaPeriod", "Loading failed, treating as end-of-stream.", iOException2);
            this.p = true;
            loadErrorAction = Loader.d;
        } else if (i4 != 0) {
            loadErrorAction = Loader.createRetryAction(false, retryDelayMsFor);
        } else {
            loadErrorAction = Loader.e;
        }
        Loader.LoadErrorAction loadErrorAction2 = loadErrorAction;
        boolean z2 = !loadErrorAction2.isRetry();
        this.i.loadError(loadEventInfo, 1, -1, this.n, 0, (Object) null, 0, this.l, iOException, z2);
        if (z2) {
            loadErrorHandlingPolicy.onLoadTaskConcluded(bVar2.a);
        }
        return loadErrorAction2;
    }
}
