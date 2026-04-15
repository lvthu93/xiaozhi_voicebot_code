package com.google.android.exoplayer2.source;

import android.content.Context;
import android.net.Uri;
import android.util.SparseArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.google.android.exoplayer2.source.ads.AdsLoader;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.ui.AdViewProvider;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.List;

public final class DefaultMediaSourceFactory implements MediaSourceFactory {
    public final DataSource.Factory a;
    public final SparseArray<MediaSourceFactory> b;
    public final int[] c;
    @Nullable
    public AdsLoaderProvider d;
    @Nullable
    public AdViewProvider e;
    @Nullable
    public LoadErrorHandlingPolicy f;
    public long g;
    public long h;
    public long i;
    public float j;
    public float k;

    public interface AdsLoaderProvider {
        @Nullable
        AdsLoader getAdsLoader(MediaItem.AdsConfiguration adsConfiguration);
    }

    public DefaultMediaSourceFactory(Context context) {
        this((DataSource.Factory) new DefaultDataSourceFactory(context));
    }

    @Deprecated
    public /* bridge */ /* synthetic */ MediaSource createMediaSource(Uri uri) {
        return j6.a(this, uri);
    }

    public MediaSource createMediaSource(MediaItem mediaItem) {
        ClippingMediaSource clippingMediaSource;
        MediaItem mediaItem2 = mediaItem;
        Assertions.checkNotNull(mediaItem2.f);
        MediaItem.PlaybackProperties playbackProperties = mediaItem2.f;
        int inferContentTypeForUriAndMimeType = Util.inferContentTypeForUriAndMimeType(playbackProperties.a, playbackProperties.b);
        MediaSourceFactory mediaSourceFactory = this.b.get(inferContentTypeForUriAndMimeType);
        StringBuilder sb = new StringBuilder(68);
        sb.append("No suitable media source factory found for content type: ");
        sb.append(inferContentTypeForUriAndMimeType);
        Assertions.checkNotNull(mediaSourceFactory, sb.toString());
        MediaItem.LiveConfiguration liveConfiguration = mediaItem2.g;
        if ((liveConfiguration.c == -9223372036854775807L && this.g != -9223372036854775807L) || ((liveConfiguration.h == -3.4028235E38f && this.j != -3.4028235E38f) || ((liveConfiguration.i == -3.4028235E38f && this.k != -3.4028235E38f) || ((liveConfiguration.f == -9223372036854775807L && this.h != -9223372036854775807L) || (liveConfiguration.g == -9223372036854775807L && this.i != -9223372036854775807L))))) {
            MediaItem.Builder buildUpon = mediaItem.buildUpon();
            long j2 = liveConfiguration.c;
            if (j2 == -9223372036854775807L) {
                j2 = this.g;
            }
            MediaItem.Builder liveTargetOffsetMs = buildUpon.setLiveTargetOffsetMs(j2);
            float f2 = liveConfiguration.h;
            if (f2 == -3.4028235E38f) {
                f2 = this.j;
            }
            MediaItem.Builder liveMinPlaybackSpeed = liveTargetOffsetMs.setLiveMinPlaybackSpeed(f2);
            float f3 = liveConfiguration.i;
            if (f3 == -3.4028235E38f) {
                f3 = this.k;
            }
            MediaItem.Builder liveMaxPlaybackSpeed = liveMinPlaybackSpeed.setLiveMaxPlaybackSpeed(f3);
            long j3 = liveConfiguration.f;
            if (j3 == -9223372036854775807L) {
                j3 = this.h;
            }
            MediaItem.Builder liveMinOffsetMs = liveMaxPlaybackSpeed.setLiveMinOffsetMs(j3);
            long j4 = liveConfiguration.g;
            if (j4 == -9223372036854775807L) {
                j4 = this.i;
            }
            mediaItem2 = liveMinOffsetMs.setLiveMaxOffsetMs(j4).build();
        }
        MediaSource createMediaSource = mediaSourceFactory.createMediaSource(mediaItem2);
        List<MediaItem.Subtitle> list = ((MediaItem.PlaybackProperties) Util.castNonNull(mediaItem2.f)).g;
        if (!list.isEmpty()) {
            MediaSource[] mediaSourceArr = new MediaSource[(list.size() + 1)];
            int i2 = 0;
            mediaSourceArr[0] = createMediaSource;
            SingleSampleMediaSource.Factory loadErrorHandlingPolicy = new SingleSampleMediaSource.Factory(this.a).setLoadErrorHandlingPolicy(this.f);
            while (i2 < list.size()) {
                int i3 = i2 + 1;
                mediaSourceArr[i3] = loadErrorHandlingPolicy.createMediaSource(list.get(i2), -9223372036854775807L);
                i2 = i3;
            }
            createMediaSource = new MergingMediaSource(mediaSourceArr);
        }
        MediaSource mediaSource = createMediaSource;
        MediaItem.ClippingProperties clippingProperties = mediaItem2.i;
        long j5 = clippingProperties.c;
        if (j5 == 0 && clippingProperties.f == Long.MIN_VALUE && !clippingProperties.h) {
            clippingMediaSource = mediaSource;
        } else {
            clippingMediaSource = new ClippingMediaSource(mediaSource, C.msToUs(j5), C.msToUs(clippingProperties.f), !clippingProperties.i, clippingProperties.g, clippingProperties.h);
        }
        MediaItem.PlaybackProperties playbackProperties2 = mediaItem2.f;
        Assertions.checkNotNull(playbackProperties2);
        MediaItem.AdsConfiguration adsConfiguration = playbackProperties2.d;
        if (adsConfiguration == null) {
            return clippingMediaSource;
        }
        AdsLoaderProvider adsLoaderProvider = this.d;
        AdViewProvider adViewProvider = this.e;
        if (adsLoaderProvider == null || adViewProvider == null) {
            Log.w("DefaultMediaSourceFactory", "Playing media without ads. Configure ad support by calling setAdsLoaderProvider and setAdViewProvider.");
            return clippingMediaSource;
        }
        AdsLoader adsLoader = adsLoaderProvider.getAdsLoader(adsConfiguration);
        if (adsLoader == null) {
            Log.w("DefaultMediaSourceFactory", "Playing media without ads, as no AdsLoader was provided.");
            return clippingMediaSource;
        }
        Uri uri = adsConfiguration.a;
        DataSpec dataSpec = new DataSpec(uri);
        Object obj = adsConfiguration.b;
        if (obj == null) {
            obj = ImmutableList.of(mediaItem2.c, playbackProperties2.a, uri);
        }
        return new AdsMediaSource(clippingMediaSource, dataSpec, obj, this, adsLoader, adViewProvider);
    }

    public int[] getSupportedTypes() {
        int[] iArr = this.c;
        return Arrays.copyOf(iArr, iArr.length);
    }

    public DefaultMediaSourceFactory setAdViewProvider(@Nullable AdViewProvider adViewProvider) {
        this.e = adViewProvider;
        return this;
    }

    public DefaultMediaSourceFactory setAdsLoaderProvider(@Nullable AdsLoaderProvider adsLoaderProvider) {
        this.d = adsLoaderProvider;
        return this;
    }

    public DefaultMediaSourceFactory setLiveMaxOffsetMs(long j2) {
        this.i = j2;
        return this;
    }

    public DefaultMediaSourceFactory setLiveMaxSpeed(float f2) {
        this.k = f2;
        return this;
    }

    public DefaultMediaSourceFactory setLiveMinOffsetMs(long j2) {
        this.h = j2;
        return this;
    }

    public DefaultMediaSourceFactory setLiveMinSpeed(float f2) {
        this.j = f2;
        return this;
    }

    public DefaultMediaSourceFactory setLiveTargetOffsetMs(long j2) {
        this.g = j2;
        return this;
    }

    public DefaultMediaSourceFactory(Context context, ExtractorsFactory extractorsFactory) {
        this((DataSource.Factory) new DefaultDataSourceFactory(context), extractorsFactory);
    }

    public DefaultMediaSourceFactory setDrmHttpDataSourceFactory(@Nullable HttpDataSource.Factory factory) {
        int i2 = 0;
        while (true) {
            SparseArray<MediaSourceFactory> sparseArray = this.b;
            if (i2 >= sparseArray.size()) {
                return this;
            }
            sparseArray.valueAt(i2).setDrmHttpDataSourceFactory(factory);
            i2++;
        }
    }

    public DefaultMediaSourceFactory setDrmSessionManager(@Nullable DrmSessionManager drmSessionManager) {
        int i2 = 0;
        while (true) {
            SparseArray<MediaSourceFactory> sparseArray = this.b;
            if (i2 >= sparseArray.size()) {
                return this;
            }
            sparseArray.valueAt(i2).setDrmSessionManager(drmSessionManager);
            i2++;
        }
    }

    public DefaultMediaSourceFactory setDrmSessionManagerProvider(@Nullable DrmSessionManagerProvider drmSessionManagerProvider) {
        int i2 = 0;
        while (true) {
            SparseArray<MediaSourceFactory> sparseArray = this.b;
            if (i2 >= sparseArray.size()) {
                return this;
            }
            sparseArray.valueAt(i2).setDrmSessionManagerProvider(drmSessionManagerProvider);
            i2++;
        }
    }

    public DefaultMediaSourceFactory setDrmUserAgent(@Nullable String str) {
        int i2 = 0;
        while (true) {
            SparseArray<MediaSourceFactory> sparseArray = this.b;
            if (i2 >= sparseArray.size()) {
                return this;
            }
            sparseArray.valueAt(i2).setDrmUserAgent(str);
            i2++;
        }
    }

    public DefaultMediaSourceFactory setLoadErrorHandlingPolicy(@Nullable LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
        this.f = loadErrorHandlingPolicy;
        int i2 = 0;
        while (true) {
            SparseArray<MediaSourceFactory> sparseArray = this.b;
            if (i2 >= sparseArray.size()) {
                return this;
            }
            sparseArray.valueAt(i2).setLoadErrorHandlingPolicy(loadErrorHandlingPolicy);
            i2++;
        }
    }

    @Deprecated
    public DefaultMediaSourceFactory setStreamKeys(@Nullable List<StreamKey> list) {
        int i2 = 0;
        while (true) {
            SparseArray<MediaSourceFactory> sparseArray = this.b;
            if (i2 >= sparseArray.size()) {
                return this;
            }
            sparseArray.valueAt(i2).setStreamKeys(list);
            i2++;
        }
    }

    public DefaultMediaSourceFactory(DataSource.Factory factory) {
        this(factory, (ExtractorsFactory) new DefaultExtractorsFactory());
    }

    public DefaultMediaSourceFactory(DataSource.Factory factory, ExtractorsFactory extractorsFactory) {
        this.a = factory;
        Class<DataSource.Factory> cls = DataSource.Factory.class;
        Class<MediaSourceFactory> cls2 = MediaSourceFactory.class;
        SparseArray<MediaSourceFactory> sparseArray = new SparseArray<>();
        try {
            sparseArray.put(0, (MediaSourceFactory) Class.forName("com.google.android.exoplayer2.source.dash.DashMediaSource$Factory").asSubclass(cls2).getConstructor(new Class[]{cls}).newInstance(new Object[]{factory}));
        } catch (Exception unused) {
        }
        try {
            sparseArray.put(1, (MediaSourceFactory) Class.forName("com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource$Factory").asSubclass(cls2).getConstructor(new Class[]{cls}).newInstance(new Object[]{factory}));
        } catch (Exception unused2) {
        }
        try {
            sparseArray.put(2, (MediaSourceFactory) Class.forName("com.google.android.exoplayer2.source.hls.HlsMediaSource$Factory").asSubclass(cls2).getConstructor(new Class[]{cls}).newInstance(new Object[]{factory}));
        } catch (Exception unused3) {
        }
        try {
            sparseArray.put(3, (MediaSourceFactory) Class.forName("com.google.android.exoplayer2.source.rtsp.RtspMediaSource$Factory").asSubclass(cls2).getConstructor(new Class[0]).newInstance(new Object[0]));
        } catch (Exception unused4) {
        }
        sparseArray.put(4, new ProgressiveMediaSource.Factory(factory, extractorsFactory));
        this.b = sparseArray;
        this.c = new int[sparseArray.size()];
        for (int i2 = 0; i2 < this.b.size(); i2++) {
            this.c[i2] = this.b.keyAt(i2);
        }
        this.g = -9223372036854775807L;
        this.h = -9223372036854775807L;
        this.i = -9223372036854775807L;
        this.j = -3.4028235E38f;
        this.k = -3.4028235E38f;
    }
}
