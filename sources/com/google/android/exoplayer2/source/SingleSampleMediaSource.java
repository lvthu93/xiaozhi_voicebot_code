package com.google.android.exoplayer2.source;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Collections;

public final class SingleSampleMediaSource extends BaseMediaSource {
    public final DataSpec g;
    public final DataSource.Factory h;
    public final Format i;
    public final long j;
    public final LoadErrorHandlingPolicy k;
    public final boolean l;
    public final SinglePeriodTimeline m;
    public final MediaItem n;
    @Nullable
    public TransferListener o;

    public static final class Factory {
        public final DataSource.Factory a;
        public LoadErrorHandlingPolicy b = new DefaultLoadErrorHandlingPolicy();
        public boolean c = true;
        @Nullable
        public Object d;
        @Nullable
        public String e;

        public Factory(DataSource.Factory factory) {
            this.a = (DataSource.Factory) Assertions.checkNotNull(factory);
        }

        public SingleSampleMediaSource createMediaSource(MediaItem.Subtitle subtitle, long j) {
            return new SingleSampleMediaSource(this.e, subtitle, this.a, j, this.b, this.c, this.d);
        }

        public Factory setLoadErrorHandlingPolicy(@Nullable LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
            if (loadErrorHandlingPolicy == null) {
                loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
            }
            this.b = loadErrorHandlingPolicy;
            return this;
        }

        public Factory setTag(@Nullable Object obj) {
            this.d = obj;
            return this;
        }

        public Factory setTrackId(@Nullable String str) {
            this.e = str;
            return this;
        }

        public Factory setTreatLoadErrorsAsEndOfStream(boolean z) {
            this.c = z;
            return this;
        }

        @Deprecated
        public SingleSampleMediaSource createMediaSource(Uri uri, Format format, long j) {
            String str = format.c;
            if (str == null) {
                str = this.e;
            }
            return new SingleSampleMediaSource(str, new MediaItem.Subtitle(uri, (String) Assertions.checkNotNull(format.p), format.g, format.h), this.a, j, this.b, this.c, this.d);
        }
    }

    public SingleSampleMediaSource(String str, MediaItem.Subtitle subtitle, DataSource.Factory factory, long j2, LoadErrorHandlingPolicy loadErrorHandlingPolicy, boolean z, Object obj) {
        MediaItem.Subtitle subtitle2 = subtitle;
        this.h = factory;
        long j3 = j2;
        this.j = j3;
        this.k = loadErrorHandlingPolicy;
        this.l = z;
        MediaItem build = new MediaItem.Builder().setUri(Uri.EMPTY).setMediaId(subtitle2.a.toString()).setSubtitles(Collections.singletonList(subtitle)).setTag(obj).build();
        this.n = build;
        String str2 = str;
        this.i = new Format.Builder().setId(str).setSampleMimeType(subtitle2.b).setLanguage(subtitle2.c).setSelectionFlags(subtitle2.d).setRoleFlags(subtitle2.e).setLabel(subtitle2.f).build();
        this.g = new DataSpec.Builder().setUri(subtitle2.a).setFlags(1).build();
        this.m = new SinglePeriodTimeline(j3, true, false, false, (Object) null, build);
    }

    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j2) {
        return new d(this.g, this.h, this.o, this.i, this.j, this.k, this.c.withParameters(0, mediaPeriodId, 0), this.l);
    }

    @Nullable
    public /* bridge */ /* synthetic */ Timeline getInitialTimeline() {
        return e6.a(this);
    }

    public MediaItem getMediaItem() {
        return this.n;
    }

    @Deprecated
    @Nullable
    public Object getTag() {
        return ((MediaItem.PlaybackProperties) Util.castNonNull(this.n.f)).h;
    }

    public /* bridge */ /* synthetic */ boolean isSingleWindow() {
        return e6.c(this);
    }

    public void maybeThrowSourceInfoRefreshError() {
    }

    public final void prepareSourceInternal(@Nullable TransferListener transferListener) {
        this.o = transferListener;
        c(this.m);
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        ((d) mediaPeriod).release();
    }

    public final void releaseSourceInternal() {
    }
}
