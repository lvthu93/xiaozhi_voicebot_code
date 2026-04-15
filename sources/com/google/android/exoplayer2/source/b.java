package com.google.android.exoplayer2.source;

import android.net.Uri;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.exoplayer2.source.IcyDataSource;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.upstream.StatsDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ConditionVariable;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

public final class b implements MediaPeriod, ExtractorOutput, Loader.Callback<a>, Loader.ReleaseCallback, SampleQueue.UpstreamFormatChangedListener {
    public static final Map<String, String> aq;
    public static final Format ar = new Format.Builder().setId("icy").setSampleMimeType("application/x-icy").build();
    public boolean aa;
    public e ab;
    public SeekMap ac;
    public long ad;
    public boolean ae;
    public int af;
    public boolean ag;
    public boolean ah;
    public int ai;
    public long aj;
    public long ak;
    public long al;
    public boolean am;
    public int an;
    public boolean ao;
    public boolean ap;
    public final Uri c;
    public final DataSource f;
    public final DrmSessionManager g;
    public final LoadErrorHandlingPolicy h;
    public final MediaSourceEventListener.EventDispatcher i;
    public final DrmSessionEventListener.EventDispatcher j;
    public final C0018b k;
    public final Allocator l;
    @Nullable
    public final String m;
    public final long n;
    public final Loader o = new Loader("ProgressiveMediaPeriod");
    public final ProgressiveMediaExtractor p;
    public final ConditionVariable q;
    public final d9 r;
    public final d9 s;
    public final Handler t;
    @Nullable
    public MediaPeriod.Callback u;
    @Nullable
    public IcyHeaders v;
    public SampleQueue[] w;
    public d[] x;
    public boolean y;
    public boolean z;

    public final class a implements Loader.Loadable, IcyDataSource.Listener {
        public final long a = LoadEventInfo.getNewId();
        public final Uri b;
        public final StatsDataSource c;
        public final ProgressiveMediaExtractor d;
        public final ExtractorOutput e;
        public final ConditionVariable f;
        public final PositionHolder g = new PositionHolder();
        public volatile boolean h;
        public boolean i = true;
        public long j;
        public DataSpec k = a(0);
        public long l = -1;
        @Nullable
        public SampleQueue m;
        public boolean n;

        public a(Uri uri, DataSource dataSource, ProgressiveMediaExtractor progressiveMediaExtractor, ExtractorOutput extractorOutput, ConditionVariable conditionVariable) {
            this.b = uri;
            this.c = new StatsDataSource(dataSource);
            this.d = progressiveMediaExtractor;
            this.e = extractorOutput;
            this.f = conditionVariable;
        }

        public final DataSpec a(long j2) {
            return new DataSpec.Builder().setUri(this.b).setPosition(j2).setKey(b.this.m).setFlags(6).setHttpRequestHeaders(b.aq).build();
        }

        public void cancelLoad() {
            this.h = true;
        }

        public void load() throws IOException {
            DataReader dataReader;
            int i2;
            int i3 = 0;
            while (i3 == 0 && !this.h) {
                try {
                    long j2 = this.g.a;
                    DataSpec a2 = a(j2);
                    this.k = a2;
                    long open = this.c.open(a2);
                    this.l = open;
                    if (open != -1) {
                        this.l = open + j2;
                    }
                    b.this.v = IcyHeaders.parse(this.c.getResponseHeaders());
                    StatsDataSource statsDataSource = this.c;
                    IcyHeaders icyHeaders = b.this.v;
                    if (icyHeaders == null || (i2 = icyHeaders.j) == -1) {
                        dataReader = statsDataSource;
                    } else {
                        dataReader = new IcyDataSource(statsDataSource, i2, this);
                        b bVar = b.this;
                        bVar.getClass();
                        SampleQueue h2 = bVar.h(new d(0, true));
                        this.m = h2;
                        h2.format(b.ar);
                    }
                    ProgressiveMediaExtractor progressiveMediaExtractor = this.d;
                    Uri uri = this.b;
                    Map<String, List<String>> responseHeaders = this.c.getResponseHeaders();
                    long j3 = this.l;
                    long j4 = j2;
                    progressiveMediaExtractor.init(dataReader, uri, responseHeaders, j2, j3, this.e);
                    if (b.this.v != null) {
                        this.d.disableSeekingOnMp3Streams();
                    }
                    if (this.i) {
                        this.d.seek(j4, this.j);
                        this.i = false;
                    }
                    while (true) {
                        long j5 = j4;
                        while (i3 == 0 && !this.h) {
                            this.f.block();
                            i3 = this.d.read(this.g);
                            j4 = this.d.getCurrentInputPosition();
                            if (j4 > b.this.n + j5) {
                                this.f.close();
                                b bVar2 = b.this;
                                bVar2.t.post(bVar2.s);
                            }
                        }
                    }
                    if (i3 == 1) {
                        i3 = 0;
                    } else if (this.d.getCurrentInputPosition() != -1) {
                        this.g.a = this.d.getCurrentInputPosition();
                    }
                    Util.closeQuietly((DataSource) this.c);
                } catch (InterruptedException unused) {
                    throw new InterruptedIOException();
                } catch (Throwable th) {
                    if (!(i3 == 1 || this.d.getCurrentInputPosition() == -1)) {
                        this.g.a = this.d.getCurrentInputPosition();
                    }
                    Util.closeQuietly((DataSource) this.c);
                    throw th;
                }
            }
        }

        public void onIcyMetadata(ParsableByteArray parsableByteArray) {
            long j2;
            if (!this.n) {
                j2 = this.j;
            } else {
                Map<String, String> map = b.aq;
                j2 = Math.max(b.this.c(), this.j);
            }
            int bytesLeft = parsableByteArray.bytesLeft();
            TrackOutput trackOutput = (TrackOutput) Assertions.checkNotNull(this.m);
            trackOutput.sampleData(parsableByteArray, bytesLeft);
            trackOutput.sampleMetadata(j2, 1, bytesLeft, 0, (TrackOutput.CryptoData) null);
            this.n = true;
        }
    }

    /* renamed from: com.google.android.exoplayer2.source.b$b  reason: collision with other inner class name */
    public interface C0018b {
        void onSourceInfoRefreshed(long j, boolean z, boolean z2);
    }

    public final class c implements SampleStream {
        public final int c;

        public c(int i) {
            this.c = i;
        }

        public boolean isReady() {
            b bVar = b.this;
            if (bVar.j() || !bVar.w[this.c].isReady(bVar.ao)) {
                return false;
            }
            return true;
        }

        public void maybeThrowError() throws IOException {
            b bVar = b.this;
            bVar.w[this.c].maybeThrowError();
            bVar.o.maybeThrowError(bVar.h.getMinimumLoadableRetryCount(bVar.af));
        }

        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i) {
            b bVar = b.this;
            if (bVar.j()) {
                return -3;
            }
            int i2 = this.c;
            bVar.f(i2);
            int read = bVar.w[i2].read(formatHolder, decoderInputBuffer, i, bVar.ao);
            if (read == -3) {
                bVar.g(i2);
            }
            return read;
        }

        public int skipData(long j) {
            b bVar = b.this;
            if (bVar.j()) {
                return 0;
            }
            int i = this.c;
            bVar.f(i);
            SampleQueue sampleQueue = bVar.w[i];
            int skipCount = sampleQueue.getSkipCount(j, bVar.ao);
            sampleQueue.skip(skipCount);
            if (skipCount != 0) {
                return skipCount;
            }
            bVar.g(i);
            return skipCount;
        }
    }

    public static final class d {
        public final int a;
        public final boolean b;

        public d(int i, boolean z) {
            this.a = i;
            this.b = z;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || d.class != obj.getClass()) {
                return false;
            }
            d dVar = (d) obj;
            if (this.a == dVar.a && this.b == dVar.b) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (this.a * 31) + (this.b ? 1 : 0);
        }
    }

    public static final class e {
        public final TrackGroupArray a;
        public final boolean[] b;
        public final boolean[] c;
        public final boolean[] d;

        public e(TrackGroupArray trackGroupArray, boolean[] zArr) {
            this.a = trackGroupArray;
            this.b = zArr;
            int i = trackGroupArray.c;
            this.c = new boolean[i];
            this.d = new boolean[i];
        }
    }

    static {
        HashMap hashMap = new HashMap();
        hashMap.put("Icy-MetaData", "1");
        aq = Collections.unmodifiableMap(hashMap);
    }

    public b(Uri uri, DataSource dataSource, ProgressiveMediaExtractor progressiveMediaExtractor, DrmSessionManager drmSessionManager, DrmSessionEventListener.EventDispatcher eventDispatcher, LoadErrorHandlingPolicy loadErrorHandlingPolicy, MediaSourceEventListener.EventDispatcher eventDispatcher2, C0018b bVar, Allocator allocator, @Nullable String str, int i2) {
        this.c = uri;
        this.f = dataSource;
        this.g = drmSessionManager;
        this.j = eventDispatcher;
        this.h = loadErrorHandlingPolicy;
        this.i = eventDispatcher2;
        this.k = bVar;
        this.l = allocator;
        this.m = str;
        this.n = (long) i2;
        this.p = progressiveMediaExtractor;
        this.q = new ConditionVariable();
        this.r = new d9(this, 0);
        this.s = new d9(this, 1);
        this.t = Util.createHandlerForCurrentLooper();
        this.x = new d[0];
        this.w = new SampleQueue[0];
        this.al = -9223372036854775807L;
        this.aj = -1;
        this.ad = -9223372036854775807L;
        this.af = 1;
    }

    @EnsuresNonNull({"trackState", "seekMap"})
    public final void a() {
        Assertions.checkState(this.z);
        Assertions.checkNotNull(this.ab);
        Assertions.checkNotNull(this.ac);
    }

    public final int b() {
        int i2 = 0;
        for (SampleQueue writeIndex : this.w) {
            i2 += writeIndex.getWriteIndex();
        }
        return i2;
    }

    public final long c() {
        long j2 = Long.MIN_VALUE;
        for (SampleQueue largestQueuedTimestampUs : this.w) {
            j2 = Math.max(j2, largestQueuedTimestampUs.getLargestQueuedTimestampUs());
        }
        return j2;
    }

    public boolean continueLoading(long j2) {
        if (this.ao) {
            return false;
        }
        Loader loader = this.o;
        if (loader.hasFatalError() || this.am) {
            return false;
        }
        if (this.z && this.ai == 0) {
            return false;
        }
        boolean open = this.q.open();
        if (loader.isLoading()) {
            return open;
        }
        i();
        return true;
    }

    public final boolean d() {
        return this.al != -9223372036854775807L;
    }

    public void discardBuffer(long j2, boolean z2) {
        a();
        if (!d()) {
            boolean[] zArr = this.ab.c;
            int length = this.w.length;
            for (int i2 = 0; i2 < length; i2++) {
                this.w[i2].discardTo(j2, z2, zArr[i2]);
            }
        }
    }

    public final void e() {
        boolean z2;
        int i2;
        Metadata metadata;
        if (!this.ap && !this.z && this.y && this.ac != null) {
            SampleQueue[] sampleQueueArr = this.w;
            int length = sampleQueueArr.length;
            int i3 = 0;
            while (i3 < length) {
                if (sampleQueueArr[i3].getUpstreamFormat() != null) {
                    i3++;
                } else {
                    return;
                }
            }
            this.q.close();
            int length2 = this.w.length;
            TrackGroup[] trackGroupArr = new TrackGroup[length2];
            boolean[] zArr = new boolean[length2];
            for (int i4 = 0; i4 < length2; i4++) {
                Format format = (Format) Assertions.checkNotNull(this.w[i4].getUpstreamFormat());
                String str = format.p;
                boolean isAudio = MimeTypes.isAudio(str);
                if (isAudio || MimeTypes.isVideo(str)) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                zArr[i4] = z2;
                this.aa = z2 | this.aa;
                IcyHeaders icyHeaders = this.v;
                if (icyHeaders != null) {
                    if (isAudio || this.x[i4].b) {
                        Metadata metadata2 = format.n;
                        if (metadata2 == null) {
                            metadata = new Metadata(icyHeaders);
                        } else {
                            metadata = metadata2.copyWithAppendedEntries(icyHeaders);
                        }
                        format = format.buildUpon().setMetadata(metadata).build();
                    }
                    if (isAudio && format.j == -1 && format.k == -1 && (i2 = icyHeaders.c) != -1) {
                        format = format.buildUpon().setAverageBitrate(i2).build();
                    }
                }
                trackGroupArr[i4] = new TrackGroup(format.copyWithExoMediaCryptoType(this.g.getExoMediaCryptoType(format)));
            }
            this.ab = new e(new TrackGroupArray(trackGroupArr), zArr);
            this.z = true;
            ((MediaPeriod.Callback) Assertions.checkNotNull(this.u)).onPrepared(this);
        }
    }

    public void endTracks() {
        this.y = true;
        this.t.post(this.r);
    }

    public final void f(int i2) {
        a();
        e eVar = this.ab;
        boolean[] zArr = eVar.d;
        if (!zArr[i2]) {
            Format format = eVar.a.get(i2).getFormat(0);
            this.i.downstreamFormatChanged(MimeTypes.getTrackType(format.p), format, 0, (Object) null, this.ak);
            zArr[i2] = true;
        }
    }

    public final void g(int i2) {
        a();
        boolean[] zArr = this.ab.b;
        if (this.am && zArr[i2]) {
            if (!this.w[i2].isReady(false)) {
                this.al = 0;
                this.am = false;
                this.ah = true;
                this.ak = 0;
                this.an = 0;
                for (SampleQueue reset : this.w) {
                    reset.reset();
                }
                ((MediaPeriod.Callback) Assertions.checkNotNull(this.u)).onContinueLoadingRequested(this);
            }
        }
    }

    public long getAdjustedSeekPositionUs(long j2, SeekParameters seekParameters) {
        a();
        if (!this.ac.isSeekable()) {
            return 0;
        }
        SeekMap.SeekPoints seekPoints = this.ac.getSeekPoints(j2);
        return seekParameters.resolveSeekPositionUs(j2, seekPoints.a.a, seekPoints.b.a);
    }

    public long getBufferedPositionUs() {
        long j2;
        a();
        boolean[] zArr = this.ab.b;
        if (this.ao) {
            return Long.MIN_VALUE;
        }
        if (d()) {
            return this.al;
        }
        if (this.aa) {
            int length = this.w.length;
            j2 = Long.MAX_VALUE;
            for (int i2 = 0; i2 < length; i2++) {
                if (zArr[i2] && !this.w[i2].isLastSampleQueued()) {
                    j2 = Math.min(j2, this.w[i2].getLargestQueuedTimestampUs());
                }
            }
        } else {
            j2 = Long.MAX_VALUE;
        }
        if (j2 == Long.MAX_VALUE) {
            j2 = c();
        }
        if (j2 == Long.MIN_VALUE) {
            return this.ak;
        }
        return j2;
    }

    public long getNextLoadPositionUs() {
        if (this.ai == 0) {
            return Long.MIN_VALUE;
        }
        return getBufferedPositionUs();
    }

    public /* bridge */ /* synthetic */ List getStreamKeys(List list) {
        return a6.a(this, list);
    }

    public TrackGroupArray getTrackGroups() {
        a();
        return this.ab.a;
    }

    public final SampleQueue h(d dVar) {
        int length = this.w.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (dVar.equals(this.x[i2])) {
                return this.w[i2];
            }
        }
        SampleQueue createWithDrm = SampleQueue.createWithDrm(this.l, this.t.getLooper(), this.g, this.j);
        createWithDrm.setUpstreamFormatChangeListener(this);
        int i3 = length + 1;
        d[] dVarArr = (d[]) Arrays.copyOf(this.x, i3);
        dVarArr[length] = dVar;
        this.x = (d[]) Util.castNonNullTypeArray(dVarArr);
        SampleQueue[] sampleQueueArr = (SampleQueue[]) Arrays.copyOf(this.w, i3);
        sampleQueueArr[length] = createWithDrm;
        this.w = (SampleQueue[]) Util.castNonNullTypeArray(sampleQueueArr);
        return createWithDrm;
    }

    public final void i() {
        a aVar = new a(this.c, this.f, this.p, this, this.q);
        if (this.z) {
            Assertions.checkState(d());
            long j2 = this.ad;
            if (j2 == -9223372036854775807L || this.al <= j2) {
                long j3 = ((SeekMap) Assertions.checkNotNull(this.ac)).getSeekPoints(this.al).a.b;
                long j4 = this.al;
                aVar.g.a = j3;
                aVar.j = j4;
                aVar.i = true;
                aVar.n = false;
                for (SampleQueue startTimeUs : this.w) {
                    startTimeUs.setStartTimeUs(this.al);
                }
                this.al = -9223372036854775807L;
            } else {
                this.ao = true;
                this.al = -9223372036854775807L;
                return;
            }
        }
        this.an = b();
        long startLoading = this.o.startLoading(aVar, this, this.h.getMinimumLoadableRetryCount(this.af));
        this.i.loadStarted(new LoadEventInfo(aVar.a, aVar.k, startLoading), 1, -1, (Format) null, 0, (Object) null, aVar.j, this.ad);
    }

    public boolean isLoading() {
        return this.o.isLoading() && this.q.isOpen();
    }

    public final boolean j() {
        return this.ah || d();
    }

    public void maybeThrowPrepareError() throws IOException {
        this.o.maybeThrowError(this.h.getMinimumLoadableRetryCount(this.af));
        if (this.ao && !this.z) {
            throw new ParserException("Loading finished before preparation is complete.");
        }
    }

    public void onLoaderReleased() {
        for (SampleQueue release : this.w) {
            release.release();
        }
        this.p.release();
    }

    public void onUpstreamFormatChanged(Format format) {
        this.t.post(this.r);
    }

    public void prepare(MediaPeriod.Callback callback, long j2) {
        this.u = callback;
        this.q.open();
        i();
    }

    public long readDiscontinuity() {
        if (!this.ah) {
            return -9223372036854775807L;
        }
        if (!this.ao && b() <= this.an) {
            return -9223372036854775807L;
        }
        this.ah = false;
        return this.ak;
    }

    public void reevaluateBuffer(long j2) {
    }

    public void release() {
        if (this.z) {
            for (SampleQueue preRelease : this.w) {
                preRelease.preRelease();
            }
        }
        this.o.release(this);
        this.t.removeCallbacksAndMessages((Object) null);
        this.u = null;
        this.ap = true;
    }

    public void seekMap(SeekMap seekMap) {
        this.t.post(new h2(8, this, seekMap));
    }

    public long seekToUs(long j2) {
        boolean z2;
        a();
        boolean[] zArr = this.ab.b;
        if (!this.ac.isSeekable()) {
            j2 = 0;
        }
        int i2 = 0;
        this.ah = false;
        this.ak = j2;
        if (d()) {
            this.al = j2;
            return j2;
        }
        if (this.af != 7) {
            int length = this.w.length;
            int i3 = 0;
            while (true) {
                if (i3 >= length) {
                    z2 = true;
                    break;
                } else if (this.w[i3].seekTo(j2, false) || (!zArr[i3] && this.aa)) {
                    i3++;
                }
            }
            z2 = false;
            if (z2) {
                return j2;
            }
        }
        this.am = false;
        this.al = j2;
        this.ao = false;
        Loader loader = this.o;
        if (loader.isLoading()) {
            SampleQueue[] sampleQueueArr = this.w;
            int length2 = sampleQueueArr.length;
            while (i2 < length2) {
                sampleQueueArr[i2].discardToEnd();
                i2++;
            }
            loader.cancelLoading();
        } else {
            loader.clearFatalError();
            SampleQueue[] sampleQueueArr2 = this.w;
            int length3 = sampleQueueArr2.length;
            while (i2 < length3) {
                sampleQueueArr2[i2].reset();
                i2++;
            }
        }
        return j2;
    }

    public long selectTracks(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j2) {
        boolean z2;
        ExoTrackSelection exoTrackSelection;
        boolean z3;
        boolean z4;
        a();
        e eVar = this.ab;
        TrackGroupArray trackGroupArray = eVar.a;
        boolean[] zArr3 = eVar.c;
        int i2 = this.ai;
        int i3 = 0;
        for (int i4 = 0; i4 < exoTrackSelectionArr.length; i4++) {
            c cVar = sampleStreamArr[i4];
            if (cVar != null && (exoTrackSelectionArr[i4] == null || !zArr[i4])) {
                int i5 = cVar.c;
                Assertions.checkState(zArr3[i5]);
                this.ai--;
                zArr3[i5] = false;
                sampleStreamArr[i4] = null;
            }
        }
        if (!this.ag ? j2 == 0 : i2 != 0) {
            z2 = false;
        } else {
            z2 = true;
        }
        for (int i6 = 0; i6 < exoTrackSelectionArr.length; i6++) {
            if (sampleStreamArr[i6] == null && (exoTrackSelection = exoTrackSelectionArr[i6]) != null) {
                if (exoTrackSelection.length() == 1) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                Assertions.checkState(z3);
                if (exoTrackSelection.getIndexInTrackGroup(0) == 0) {
                    z4 = true;
                } else {
                    z4 = false;
                }
                Assertions.checkState(z4);
                int indexOf = trackGroupArray.indexOf(exoTrackSelection.getTrackGroup());
                Assertions.checkState(!zArr3[indexOf]);
                this.ai++;
                zArr3[indexOf] = true;
                sampleStreamArr[i6] = new c(indexOf);
                zArr2[i6] = true;
                if (!z2) {
                    SampleQueue sampleQueue = this.w[indexOf];
                    if (sampleQueue.seekTo(j2, true) || sampleQueue.getReadIndex() == 0) {
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                }
            }
        }
        if (this.ai == 0) {
            this.am = false;
            this.ah = false;
            Loader loader = this.o;
            if (loader.isLoading()) {
                SampleQueue[] sampleQueueArr = this.w;
                int length = sampleQueueArr.length;
                while (i3 < length) {
                    sampleQueueArr[i3].discardToEnd();
                    i3++;
                }
                loader.cancelLoading();
            } else {
                SampleQueue[] sampleQueueArr2 = this.w;
                int length2 = sampleQueueArr2.length;
                while (i3 < length2) {
                    sampleQueueArr2[i3].reset();
                    i3++;
                }
            }
        } else if (z2) {
            j2 = seekToUs(j2);
            while (i3 < sampleStreamArr.length) {
                if (sampleStreamArr[i3] != null) {
                    zArr2[i3] = true;
                }
                i3++;
            }
        }
        this.ag = true;
        return j2;
    }

    public TrackOutput track(int i2, int i3) {
        return h(new d(i2, false));
    }

    public void onLoadCanceled(a aVar, long j2, long j3, boolean z2) {
        a aVar2 = aVar;
        StatsDataSource statsDataSource = aVar2.c;
        LoadEventInfo loadEventInfo = new LoadEventInfo(aVar2.a, aVar2.k, statsDataSource.getLastOpenedUri(), statsDataSource.getLastResponseHeaders(), j2, j3, statsDataSource.getBytesRead());
        this.h.onLoadTaskConcluded(aVar2.a);
        this.i.loadCanceled(loadEventInfo, 1, -1, (Format) null, 0, (Object) null, aVar2.j, this.ad);
        if (!z2) {
            if (this.aj == -1) {
                this.aj = aVar2.l;
            }
            for (SampleQueue reset : this.w) {
                reset.reset();
            }
            if (this.ai > 0) {
                ((MediaPeriod.Callback) Assertions.checkNotNull(this.u)).onContinueLoadingRequested(this);
            }
        }
    }

    public void onLoadCompleted(a aVar, long j2, long j3) {
        SeekMap seekMap;
        a aVar2 = aVar;
        if (this.ad == -9223372036854775807L && (seekMap = this.ac) != null) {
            boolean isSeekable = seekMap.isSeekable();
            long c2 = c();
            long j4 = c2 == Long.MIN_VALUE ? 0 : c2 + 10000;
            this.ad = j4;
            this.k.onSourceInfoRefreshed(j4, isSeekable, this.ae);
        }
        StatsDataSource statsDataSource = aVar2.c;
        LoadEventInfo loadEventInfo = new LoadEventInfo(aVar2.a, aVar2.k, statsDataSource.getLastOpenedUri(), statsDataSource.getLastResponseHeaders(), j2, j3, statsDataSource.getBytesRead());
        this.h.onLoadTaskConcluded(aVar2.a);
        this.i.loadCompleted(loadEventInfo, 1, -1, (Format) null, 0, (Object) null, aVar2.j, this.ad);
        if (this.aj == -1) {
            this.aj = aVar2.l;
        }
        this.ao = true;
        ((MediaPeriod.Callback) Assertions.checkNotNull(this.u)).onContinueLoadingRequested(this);
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x00bb  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00c0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.exoplayer2.upstream.Loader.LoadErrorAction onLoadError(com.google.android.exoplayer2.source.b.a r29, long r30, long r32, java.io.IOException r34, int r35) {
        /*
            r28 = this;
            r0 = r28
            r1 = r29
            long r2 = r0.aj
            r4 = -1
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 != 0) goto L_0x0010
            long r2 = r1.l
            r0.aj = r2
        L_0x0010:
            com.google.android.exoplayer2.upstream.StatsDataSource r2 = r1.c
            com.google.android.exoplayer2.source.LoadEventInfo r3 = new com.google.android.exoplayer2.source.LoadEventInfo
            long r7 = r1.a
            com.google.android.exoplayer2.upstream.DataSpec r9 = r1.k
            android.net.Uri r10 = r2.getLastOpenedUri()
            java.util.Map r11 = r2.getLastResponseHeaders()
            long r16 = r2.getBytesRead()
            r6 = r3
            r12 = r30
            r14 = r32
            r6.<init>(r7, r9, r10, r11, r12, r14, r16)
            com.google.android.exoplayer2.source.MediaLoadData r2 = new com.google.android.exoplayer2.source.MediaLoadData
            r19 = 1
            r20 = -1
            r21 = 0
            r22 = 0
            r23 = 0
            long r6 = r1.j
            long r24 = com.google.android.exoplayer2.C.usToMs(r6)
            long r6 = r0.ad
            long r26 = com.google.android.exoplayer2.C.usToMs(r6)
            r18 = r2
            r18.<init>(r19, r20, r21, r22, r23, r24, r26)
            com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy$LoadErrorInfo r6 = new com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy$LoadErrorInfo
            r15 = r34
            r7 = r35
            r6.<init>(r3, r2, r15, r7)
            com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy r2 = r0.h
            long r6 = r2.getRetryDelayMsFor(r6)
            r8 = 1
            r9 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r11 = (r6 > r9 ? 1 : (r6 == r9 ? 0 : -1))
            if (r11 != 0) goto L_0x0065
            com.google.android.exoplayer2.upstream.Loader$LoadErrorAction r4 = com.google.android.exoplayer2.upstream.Loader.e
            goto L_0x00c2
        L_0x0065:
            int r11 = r28.b()
            int r12 = r0.an
            if (r11 <= r12) goto L_0x006f
            r12 = 1
            goto L_0x0070
        L_0x006f:
            r12 = 0
        L_0x0070:
            long r13 = r0.aj
            int r16 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            if (r16 != 0) goto L_0x00b6
            com.google.android.exoplayer2.extractor.SeekMap r4 = r0.ac
            if (r4 == 0) goto L_0x0083
            long r4 = r4.getDurationUs()
            int r13 = (r4 > r9 ? 1 : (r4 == r9 ? 0 : -1))
            if (r13 == 0) goto L_0x0083
            goto L_0x00b6
        L_0x0083:
            boolean r4 = r0.z
            if (r4 == 0) goto L_0x0091
            boolean r4 = r28.j()
            if (r4 != 0) goto L_0x0091
            r0.am = r8
            r13 = 0
            goto L_0x00b9
        L_0x0091:
            boolean r4 = r0.z
            r0.ah = r4
            r4 = 0
            r0.ak = r4
            r9 = 0
            r0.an = r9
            com.google.android.exoplayer2.source.SampleQueue[] r9 = r0.w
            int r10 = r9.length
            r11 = 0
        L_0x00a0:
            if (r11 >= r10) goto L_0x00aa
            r13 = r9[r11]
            r13.reset()
            int r11 = r11 + 1
            goto L_0x00a0
        L_0x00aa:
            com.google.android.exoplayer2.extractor.PositionHolder r9 = r1.g
            r9.a = r4
            r1.j = r4
            r1.i = r8
            r4 = 0
            r1.n = r4
            goto L_0x00b8
        L_0x00b6:
            r0.an = r11
        L_0x00b8:
            r13 = 1
        L_0x00b9:
            if (r13 == 0) goto L_0x00c0
            com.google.android.exoplayer2.upstream.Loader$LoadErrorAction r4 = com.google.android.exoplayer2.upstream.Loader.createRetryAction(r12, r6)
            goto L_0x00c2
        L_0x00c0:
            com.google.android.exoplayer2.upstream.Loader$LoadErrorAction r4 = com.google.android.exoplayer2.upstream.Loader.d
        L_0x00c2:
            boolean r5 = r4.isRetry()
            r5 = r5 ^ r8
            com.google.android.exoplayer2.source.MediaSourceEventListener$EventDispatcher r6 = r0.i
            r8 = 1
            r9 = -1
            r10 = 0
            r11 = 0
            long r13 = r1.j
            r16 = r13
            long r12 = r0.ad
            r7 = r3
            r18 = r12
            r3 = 0
            r12 = r3
            r13 = r16
            r15 = r18
            r17 = r34
            r18 = r5
            r6.loadError(r7, r8, r9, r10, r11, r12, r13, r15, r17, r18)
            if (r5 == 0) goto L_0x00ea
            long r5 = r1.a
            r2.onLoadTaskConcluded(r5)
        L_0x00ea:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.b.onLoadError(com.google.android.exoplayer2.source.b$a, long, long, java.io.IOException, int):com.google.android.exoplayer2.upstream.Loader$LoadErrorAction");
    }
}
