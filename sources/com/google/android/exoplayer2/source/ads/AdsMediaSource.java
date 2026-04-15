package com.google.android.exoplayer2.source.ads;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.CompositeMediaSource;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MaskingMediaPeriod;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.ads.AdPlaybackState;
import com.google.android.exoplayer2.source.ads.AdsLoader;
import com.google.android.exoplayer2.ui.AdViewProvider;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;

public final class AdsMediaSource extends CompositeMediaSource<MediaSource.MediaPeriodId> {
    public static final MediaSource.MediaPeriodId v = new MediaSource.MediaPeriodId(new Object());
    public final MediaSource j;
    public final MediaSourceFactory k;
    public final AdsLoader l;
    public final AdViewProvider m;
    public final DataSpec n;
    public final Object o;
    public final Handler p = new Handler(Looper.getMainLooper());
    public final Timeline.Period q = new Timeline.Period();
    @Nullable
    public c r;
    @Nullable
    public Timeline s;
    @Nullable
    public AdPlaybackState t;
    public a[][] u = new a[0][];

    public static final class AdLoadException extends IOException {
        public final int c;

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        public @interface Type {
        }

        public AdLoadException(int i, Exception exc) {
            super(exc);
            this.c = i;
        }

        public static AdLoadException createForAd(Exception exc) {
            return new AdLoadException(0, exc);
        }

        public static AdLoadException createForAdGroup(Exception exc, int i) {
            return new AdLoadException(1, new IOException(y2.d(35, "Failed to load ad group ", i), exc));
        }

        public static AdLoadException createForAllAds(Exception exc) {
            return new AdLoadException(2, exc);
        }

        public static AdLoadException createForUnexpected(RuntimeException runtimeException) {
            return new AdLoadException(3, runtimeException);
        }

        public RuntimeException getRuntimeExceptionForUnexpected() {
            boolean z;
            if (this.c == 3) {
                z = true;
            } else {
                z = false;
            }
            Assertions.checkState(z);
            return (RuntimeException) Assertions.checkNotNull(getCause());
        }
    }

    public final class a {
        public final MediaSource.MediaPeriodId a;
        public final ArrayList b = new ArrayList();
        public Uri c;
        public MediaSource d;
        public Timeline e;

        public a(MediaSource.MediaPeriodId mediaPeriodId) {
            this.a = mediaPeriodId;
        }

        public MediaPeriod createMediaPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
            MaskingMediaPeriod maskingMediaPeriod = new MaskingMediaPeriod(mediaPeriodId, allocator, j);
            this.b.add(maskingMediaPeriod);
            MediaSource mediaSource = this.d;
            if (mediaSource != null) {
                maskingMediaPeriod.setMediaSource(mediaSource);
                maskingMediaPeriod.setPrepareListener(new b((Uri) Assertions.checkNotNull(this.c)));
            }
            Timeline timeline = this.e;
            if (timeline != null) {
                maskingMediaPeriod.createPeriod(new MediaSource.MediaPeriodId(timeline.getUidOfPeriod(0), mediaPeriodId.d));
            }
            return maskingMediaPeriod;
        }

        public long getDurationUs() {
            Timeline timeline = this.e;
            if (timeline == null) {
                return -9223372036854775807L;
            }
            return timeline.getPeriod(0, AdsMediaSource.this.q).getDurationUs();
        }

        public void handleSourceInfoRefresh(Timeline timeline) {
            int i = 0;
            boolean z = true;
            if (timeline.getPeriodCount() != 1) {
                z = false;
            }
            Assertions.checkArgument(z);
            if (this.e == null) {
                Object uidOfPeriod = timeline.getUidOfPeriod(0);
                while (true) {
                    ArrayList arrayList = this.b;
                    if (i >= arrayList.size()) {
                        break;
                    }
                    MaskingMediaPeriod maskingMediaPeriod = (MaskingMediaPeriod) arrayList.get(i);
                    maskingMediaPeriod.createPeriod(new MediaSource.MediaPeriodId(uidOfPeriod, maskingMediaPeriod.c.d));
                    i++;
                }
            }
            this.e = timeline;
        }

        public boolean hasMediaSource() {
            return this.d != null;
        }

        public void initializeWithMediaSource(MediaSource mediaSource, Uri uri) {
            this.d = mediaSource;
            this.c = uri;
            int i = 0;
            while (true) {
                ArrayList arrayList = this.b;
                int size = arrayList.size();
                AdsMediaSource adsMediaSource = AdsMediaSource.this;
                if (i < size) {
                    MaskingMediaPeriod maskingMediaPeriod = (MaskingMediaPeriod) arrayList.get(i);
                    maskingMediaPeriod.setMediaSource(mediaSource);
                    maskingMediaPeriod.setPrepareListener(new b(uri));
                    i++;
                } else {
                    MediaSource.MediaPeriodId mediaPeriodId = AdsMediaSource.v;
                    adsMediaSource.g(this.a, mediaSource);
                    return;
                }
            }
        }

        public boolean isInactive() {
            return this.b.isEmpty();
        }

        public void release() {
            if (hasMediaSource()) {
                MediaSource.MediaPeriodId mediaPeriodId = AdsMediaSource.v;
                AdsMediaSource.this.h(this.a);
            }
        }

        public void releaseMediaPeriod(MaskingMediaPeriod maskingMediaPeriod) {
            this.b.remove(maskingMediaPeriod);
            maskingMediaPeriod.releasePeriod();
        }
    }

    public final class b implements MaskingMediaPeriod.PrepareListener {
        public final Uri a;

        public b(Uri uri) {
            this.a = uri;
        }

        public void onPrepareComplete(MediaSource.MediaPeriodId mediaPeriodId) {
            AdsMediaSource.this.p.post(new h2(9, this, mediaPeriodId));
        }

        public void onPrepareError(MediaSource.MediaPeriodId mediaPeriodId, IOException iOException) {
            MediaSource.MediaPeriodId mediaPeriodId2 = AdsMediaSource.v;
            AdsMediaSource adsMediaSource = AdsMediaSource.this;
            adsMediaSource.c.withParameters(0, mediaPeriodId, 0).loadError(new LoadEventInfo(LoadEventInfo.getNewId(), new DataSpec(this.a), SystemClock.elapsedRealtime()), 6, (IOException) AdLoadException.createForAd(iOException), true);
            adsMediaSource.p.post(new d6(this, mediaPeriodId, iOException));
        }
    }

    public final class c implements AdsLoader.EventListener {
        public final Handler a = Util.createHandlerForCurrentLooper();
        public volatile boolean b;

        public c() {
        }

        public /* bridge */ /* synthetic */ void onAdClicked() {
            m.a(this);
        }

        public void onAdLoadError(AdLoadException adLoadException, DataSpec dataSpec) {
            if (!this.b) {
                AdsMediaSource adsMediaSource = AdsMediaSource.this;
                MediaSource.MediaPeriodId mediaPeriodId = AdsMediaSource.v;
                adsMediaSource.c.withParameters(0, (MediaSource.MediaPeriodId) null, 0).loadError(new LoadEventInfo(LoadEventInfo.getNewId(), dataSpec, SystemClock.elapsedRealtime()), 6, (IOException) adLoadException, true);
            }
        }

        public void onAdPlaybackState(AdPlaybackState adPlaybackState) {
            if (!this.b) {
                this.a.post(new h2(10, this, adPlaybackState));
            }
        }

        public /* bridge */ /* synthetic */ void onAdTapped() {
            m.d(this);
        }

        public void stop() {
            this.b = true;
            this.a.removeCallbacksAndMessages((Object) null);
        }
    }

    public AdsMediaSource(MediaSource mediaSource, DataSpec dataSpec, Object obj, MediaSourceFactory mediaSourceFactory, AdsLoader adsLoader, AdViewProvider adViewProvider) {
        this.j = mediaSource;
        this.k = mediaSourceFactory;
        this.l = adsLoader;
        this.m = adViewProvider;
        this.n = dataSpec;
        this.o = obj;
        adsLoader.setSupportedContentTypes(mediaSourceFactory.getSupportedTypes());
    }

    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j2) {
        if (((AdPlaybackState) Assertions.checkNotNull(this.t)).f <= 0 || !mediaPeriodId.isAd()) {
            MaskingMediaPeriod maskingMediaPeriod = new MaskingMediaPeriod(mediaPeriodId, allocator, j2);
            maskingMediaPeriod.setMediaSource(this.j);
            maskingMediaPeriod.createPeriod(mediaPeriodId);
            return maskingMediaPeriod;
        }
        a[][] aVarArr = this.u;
        int i = mediaPeriodId.b;
        a[] aVarArr2 = aVarArr[i];
        int length = aVarArr2.length;
        int i2 = mediaPeriodId.c;
        if (length <= i2) {
            aVarArr[i] = (a[]) Arrays.copyOf(aVarArr2, i2 + 1);
        }
        a aVar = this.u[i][i2];
        if (aVar == null) {
            aVar = new a(mediaPeriodId);
            this.u[i][i2] = aVar;
            i();
        }
        return aVar.createMediaPeriod(mediaPeriodId, allocator, j2);
    }

    public final MediaSource.MediaPeriodId d(Object obj, MediaSource.MediaPeriodId mediaPeriodId) {
        MediaSource.MediaPeriodId mediaPeriodId2 = (MediaSource.MediaPeriodId) obj;
        if (mediaPeriodId2.isAd()) {
            return mediaPeriodId2;
        }
        return mediaPeriodId;
    }

    public final void f(Object obj, MediaSource mediaSource, Timeline timeline) {
        MediaSource.MediaPeriodId mediaPeriodId = (MediaSource.MediaPeriodId) obj;
        if (mediaPeriodId.isAd()) {
            ((a) Assertions.checkNotNull(this.u[mediaPeriodId.b][mediaPeriodId.c])).handleSourceInfoRefresh(timeline);
        } else {
            boolean z = true;
            if (timeline.getPeriodCount() != 1) {
                z = false;
            }
            Assertions.checkArgument(z);
            this.s = timeline;
        }
        j();
    }

    @Nullable
    public /* bridge */ /* synthetic */ Timeline getInitialTimeline() {
        return e6.a(this);
    }

    public MediaItem getMediaItem() {
        return this.j.getMediaItem();
    }

    @Deprecated
    @Nullable
    public Object getTag() {
        return this.j.getTag();
    }

    public final void i() {
        AdPlaybackState.AdGroup adGroup;
        Uri uri;
        MediaItem.DrmConfiguration drmConfiguration;
        AdPlaybackState adPlaybackState = this.t;
        if (adPlaybackState != null) {
            for (int i = 0; i < this.u.length; i++) {
                int i2 = 0;
                while (true) {
                    a[] aVarArr = this.u[i];
                    if (i2 >= aVarArr.length) {
                        break;
                    }
                    a aVar = aVarArr[i2];
                    if (!(aVar == null || aVar.hasMediaSource() || (adGroup = adPlaybackState.h[i]) == null)) {
                        Uri[] uriArr = adGroup.f;
                        if (i2 < uriArr.length && (uri = uriArr[i2]) != null) {
                            MediaItem.Builder uri2 = new MediaItem.Builder().setUri(uri);
                            MediaItem.PlaybackProperties playbackProperties = this.j.getMediaItem().f;
                            if (!(playbackProperties == null || (drmConfiguration = playbackProperties.c) == null)) {
                                uri2.setDrmUuid(drmConfiguration.a);
                                uri2.setDrmKeySetId(drmConfiguration.getKeySetId());
                                uri2.setDrmLicenseUri(drmConfiguration.b);
                                uri2.setDrmForceDefaultLicenseUri(drmConfiguration.f);
                                uri2.setDrmLicenseRequestHeaders(drmConfiguration.c);
                                uri2.setDrmMultiSession(drmConfiguration.d);
                                uri2.setDrmPlayClearContentWithoutKey(drmConfiguration.e);
                                uri2.setDrmSessionForClearTypes(drmConfiguration.g);
                            }
                            aVar.initializeWithMediaSource(this.k.createMediaSource(uri2.build()), uri);
                        }
                    }
                    i2++;
                }
            }
        }
    }

    public /* bridge */ /* synthetic */ boolean isSingleWindow() {
        return e6.c(this);
    }

    public final void j() {
        long j2;
        Timeline timeline = this.s;
        AdPlaybackState adPlaybackState = this.t;
        if (adPlaybackState != null && timeline != null) {
            if (adPlaybackState.f == 0) {
                c(timeline);
                return;
            }
            long[][] jArr = new long[this.u.length][];
            int i = 0;
            while (true) {
                a[][] aVarArr = this.u;
                if (i < aVarArr.length) {
                    jArr[i] = new long[aVarArr[i].length];
                    int i2 = 0;
                    while (true) {
                        a[] aVarArr2 = this.u[i];
                        if (i2 >= aVarArr2.length) {
                            break;
                        }
                        a aVar = aVarArr2[i2];
                        long[] jArr2 = jArr[i];
                        if (aVar == null) {
                            j2 = -9223372036854775807L;
                        } else {
                            j2 = aVar.getDurationUs();
                        }
                        jArr2[i2] = j2;
                        i2++;
                    }
                    i++;
                } else {
                    this.t = adPlaybackState.withAdDurationsUs(jArr);
                    c(new SinglePeriodAdTimeline(timeline, this.t));
                    return;
                }
            }
        }
    }

    public final void prepareSourceInternal(@Nullable TransferListener transferListener) {
        super.prepareSourceInternal(transferListener);
        c cVar = new c();
        this.r = cVar;
        g(v, this.j);
        this.p.post(new n(this, cVar, 1));
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        MaskingMediaPeriod maskingMediaPeriod = (MaskingMediaPeriod) mediaPeriod;
        MediaSource.MediaPeriodId mediaPeriodId = maskingMediaPeriod.c;
        if (mediaPeriodId.isAd()) {
            a[][] aVarArr = this.u;
            int i = mediaPeriodId.b;
            a[] aVarArr2 = aVarArr[i];
            int i2 = mediaPeriodId.c;
            a aVar = (a) Assertions.checkNotNull(aVarArr2[i2]);
            aVar.releaseMediaPeriod(maskingMediaPeriod);
            if (aVar.isInactive()) {
                aVar.release();
                this.u[i][i2] = null;
                return;
            }
            return;
        }
        maskingMediaPeriod.releasePeriod();
    }

    public final void releaseSourceInternal() {
        super.releaseSourceInternal();
        c cVar = (c) Assertions.checkNotNull(this.r);
        this.r = null;
        cVar.stop();
        this.s = null;
        this.t = null;
        this.u = new a[0][];
        this.p.post(new n(this, cVar, 0));
    }
}
