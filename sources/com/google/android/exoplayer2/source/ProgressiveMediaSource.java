package com.google.android.exoplayer2.source;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManagerProvider;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaExtractor;
import com.google.android.exoplayer2.source.b;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.util.List;

public final class ProgressiveMediaSource extends BaseMediaSource implements b.C0018b {
    public final MediaItem g;
    public final MediaItem.PlaybackProperties h;
    public final DataSource.Factory i;
    public final ProgressiveMediaExtractor.Factory j;
    public final DrmSessionManager k;
    public final LoadErrorHandlingPolicy l;
    public final int m;
    public boolean n = true;
    public long o = -9223372036854775807L;
    public boolean p;
    public boolean q;
    @Nullable
    public TransferListener r;

    public static final class Factory implements MediaSourceFactory {
        public final DataSource.Factory a;
        public ProgressiveMediaExtractor.Factory b;
        public boolean c;
        public DrmSessionManagerProvider d;
        public LoadErrorHandlingPolicy e;
        public int f;
        @Nullable
        public String g;
        @Nullable
        public Object h;

        public Factory(DataSource.Factory factory) {
            this(factory, (ExtractorsFactory) new DefaultExtractorsFactory());
        }

        public int[] getSupportedTypes() {
            return new int[]{4};
        }

        public Factory setContinueLoadingCheckIntervalBytes(int i) {
            this.f = i;
            return this;
        }

        @Deprecated
        public Factory setCustomCacheKey(@Nullable String str) {
            this.g = str;
            return this;
        }

        @Deprecated
        public Factory setExtractorsFactory(@Nullable ExtractorsFactory extractorsFactory) {
            this.b = new e9(extractorsFactory, 1);
            return this;
        }

        @Deprecated
        public /* bridge */ /* synthetic */ MediaSourceFactory setStreamKeys(@Nullable List list) {
            return j6.b(this, list);
        }

        @Deprecated
        public Factory setTag(@Nullable Object obj) {
            this.h = obj;
            return this;
        }

        public Factory(DataSource.Factory factory, ExtractorsFactory extractorsFactory) {
            this(factory, (ProgressiveMediaExtractor.Factory) new e9(extractorsFactory, 0));
        }

        public Factory setDrmHttpDataSourceFactory(@Nullable HttpDataSource.Factory factory) {
            if (!this.c) {
                ((DefaultDrmSessionManagerProvider) this.d).setDrmHttpDataSourceFactory(factory);
            }
            return this;
        }

        public Factory setDrmSessionManager(@Nullable DrmSessionManager drmSessionManager) {
            if (drmSessionManager == null) {
                setDrmSessionManagerProvider((DrmSessionManagerProvider) null);
            } else {
                setDrmSessionManagerProvider((DrmSessionManagerProvider) new i2(7, drmSessionManager));
            }
            return this;
        }

        public Factory setDrmSessionManagerProvider(@Nullable DrmSessionManagerProvider drmSessionManagerProvider) {
            if (drmSessionManagerProvider != null) {
                this.d = drmSessionManagerProvider;
                this.c = true;
            } else {
                this.d = new DefaultDrmSessionManagerProvider();
                this.c = false;
            }
            return this;
        }

        public Factory setDrmUserAgent(@Nullable String str) {
            if (!this.c) {
                ((DefaultDrmSessionManagerProvider) this.d).setDrmUserAgent(str);
            }
            return this;
        }

        public Factory setLoadErrorHandlingPolicy(@Nullable LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
            if (loadErrorHandlingPolicy == null) {
                loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
            }
            this.e = loadErrorHandlingPolicy;
            return this;
        }

        public Factory(DataSource.Factory factory, ProgressiveMediaExtractor.Factory factory2) {
            this.a = factory;
            this.b = factory2;
            this.d = new DefaultDrmSessionManagerProvider();
            this.e = new DefaultLoadErrorHandlingPolicy();
            this.f = 1048576;
        }

        @Deprecated
        public ProgressiveMediaSource createMediaSource(Uri uri) {
            return createMediaSource(new MediaItem.Builder().setUri(uri).build());
        }

        public ProgressiveMediaSource createMediaSource(MediaItem mediaItem) {
            Assertions.checkNotNull(mediaItem.f);
            MediaItem.PlaybackProperties playbackProperties = mediaItem.f;
            boolean z = false;
            boolean z2 = playbackProperties.h == null && this.h != null;
            if (playbackProperties.f == null && this.g != null) {
                z = true;
            }
            if (z2 && z) {
                mediaItem = mediaItem.buildUpon().setTag(this.h).setCustomCacheKey(this.g).build();
            } else if (z2) {
                mediaItem = mediaItem.buildUpon().setTag(this.h).build();
            } else if (z) {
                mediaItem = mediaItem.buildUpon().setCustomCacheKey(this.g).build();
            }
            MediaItem mediaItem2 = mediaItem;
            return new ProgressiveMediaSource(mediaItem2, this.a, this.b, this.d.get(mediaItem2), this.e, this.f);
        }
    }

    public class a extends ForwardingTimeline {
        public a(SinglePeriodTimeline singlePeriodTimeline) {
            super(singlePeriodTimeline);
        }

        public Timeline.Period getPeriod(int i, Timeline.Period period, boolean z) {
            super.getPeriod(i, period, z);
            period.j = true;
            return period;
        }

        public Timeline.Window getWindow(int i, Timeline.Window window, long j) {
            super.getWindow(i, window, j);
            window.p = true;
            return window;
        }
    }

    public ProgressiveMediaSource(MediaItem mediaItem, DataSource.Factory factory, ProgressiveMediaExtractor.Factory factory2, DrmSessionManager drmSessionManager, LoadErrorHandlingPolicy loadErrorHandlingPolicy, int i2) {
        this.h = (MediaItem.PlaybackProperties) Assertions.checkNotNull(mediaItem.f);
        this.g = mediaItem;
        this.i = factory;
        this.j = factory2;
        this.k = drmSessionManager;
        this.l = loadErrorHandlingPolicy;
        this.m = i2;
    }

    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j2) {
        MediaSource.MediaPeriodId mediaPeriodId2 = mediaPeriodId;
        DataSource createDataSource = this.i.createDataSource();
        TransferListener transferListener = this.r;
        if (transferListener != null) {
            createDataSource.addTransferListener(transferListener);
        }
        MediaItem.PlaybackProperties playbackProperties = this.h;
        Uri uri = playbackProperties.a;
        ProgressiveMediaExtractor createProgressiveMediaExtractor = this.j.createProgressiveMediaExtractor();
        DrmSessionManager drmSessionManager = this.k;
        DrmSessionEventListener.EventDispatcher withParameters = this.d.withParameters(0, mediaPeriodId);
        return new b(uri, createDataSource, createProgressiveMediaExtractor, drmSessionManager, withParameters, this.l, this.c.withParameters(0, mediaPeriodId, 0), this, allocator, playbackProperties.f, this.m);
    }

    /* JADX WARNING: type inference failed for: r0v2, types: [com.google.android.exoplayer2.source.ProgressiveMediaSource$a] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void d() {
        /*
            r9 = this;
            com.google.android.exoplayer2.source.SinglePeriodTimeline r8 = new com.google.android.exoplayer2.source.SinglePeriodTimeline
            long r1 = r9.o
            boolean r3 = r9.p
            r4 = 0
            boolean r5 = r9.q
            r6 = 0
            com.google.android.exoplayer2.MediaItem r7 = r9.g
            r0 = r8
            r0.<init>((long) r1, (boolean) r3, (boolean) r4, (boolean) r5, (java.lang.Object) r6, (com.google.android.exoplayer2.MediaItem) r7)
            boolean r0 = r9.n
            if (r0 == 0) goto L_0x001a
            com.google.android.exoplayer2.source.ProgressiveMediaSource$a r0 = new com.google.android.exoplayer2.source.ProgressiveMediaSource$a
            r0.<init>(r8)
            r8 = r0
        L_0x001a:
            r9.c(r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.ProgressiveMediaSource.d():void");
    }

    @Nullable
    public /* bridge */ /* synthetic */ Timeline getInitialTimeline() {
        return e6.a(this);
    }

    public MediaItem getMediaItem() {
        return this.g;
    }

    @Deprecated
    @Nullable
    public Object getTag() {
        return this.h.h;
    }

    public /* bridge */ /* synthetic */ boolean isSingleWindow() {
        return e6.c(this);
    }

    public void maybeThrowSourceInfoRefreshError() {
    }

    public void onSourceInfoRefreshed(long j2, boolean z, boolean z2) {
        if (j2 == -9223372036854775807L) {
            j2 = this.o;
        }
        if (this.n || this.o != j2 || this.p != z || this.q != z2) {
            this.o = j2;
            this.p = z;
            this.q = z2;
            this.n = false;
            d();
        }
    }

    public final void prepareSourceInternal(@Nullable TransferListener transferListener) {
        this.r = transferListener;
        this.k.prepare();
        d();
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        ((b) mediaPeriod).release();
    }

    public final void releaseSourceInternal() {
        this.k.release();
    }
}
