package com.google.android.exoplayer2.source;

import android.os.Handler;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.UnknownNull;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.HashMap;

public abstract class CompositeMediaSource<T> extends BaseMediaSource {
    public final HashMap<T, b<T>> g = new HashMap<>();
    @Nullable
    public Handler h;
    @Nullable
    public TransferListener i;

    public final class a implements MediaSourceEventListener, DrmSessionEventListener {
        @UnknownNull
        public final T c;
        public MediaSourceEventListener.EventDispatcher f;
        public DrmSessionEventListener.EventDispatcher g;

        public a(@UnknownNull T t) {
            this.f = CompositeMediaSource.this.c.withParameters(0, (MediaSource.MediaPeriodId) null, 0);
            this.g = CompositeMediaSource.this.d.withParameters(0, (MediaSource.MediaPeriodId) null);
            this.c = t;
        }

        public final boolean a(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            MediaSource.MediaPeriodId mediaPeriodId2;
            T t = this.c;
            CompositeMediaSource compositeMediaSource = CompositeMediaSource.this;
            if (mediaPeriodId != null) {
                mediaPeriodId2 = compositeMediaSource.d(t, mediaPeriodId);
                if (mediaPeriodId2 == null) {
                    return false;
                }
            } else {
                mediaPeriodId2 = null;
            }
            int e = compositeMediaSource.e(i, t);
            MediaSourceEventListener.EventDispatcher eventDispatcher = this.f;
            if (eventDispatcher.a != e || !Util.areEqual(eventDispatcher.b, mediaPeriodId2)) {
                this.f = compositeMediaSource.c.withParameters(e, mediaPeriodId2, 0);
            }
            DrmSessionEventListener.EventDispatcher eventDispatcher2 = this.g;
            if (eventDispatcher2.a == e && Util.areEqual(eventDispatcher2.b, mediaPeriodId2)) {
                return true;
            }
            this.g = compositeMediaSource.d.withParameters(e, mediaPeriodId2);
            return true;
        }

        public final MediaLoadData b(MediaLoadData mediaLoadData) {
            long j = mediaLoadData.f;
            CompositeMediaSource compositeMediaSource = CompositeMediaSource.this;
            compositeMediaSource.getClass();
            long j2 = mediaLoadData.g;
            compositeMediaSource.getClass();
            if (j == mediaLoadData.f && j2 == mediaLoadData.g) {
                return mediaLoadData;
            }
            return new MediaLoadData(mediaLoadData.a, mediaLoadData.b, mediaLoadData.c, mediaLoadData.d, mediaLoadData.e, j, j2);
        }

        public void onDownstreamFormatChanged(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
            if (a(i, mediaPeriodId)) {
                this.f.downstreamFormatChanged(b(mediaLoadData));
            }
        }

        public void onDrmKeysLoaded(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            if (a(i, mediaPeriodId)) {
                this.g.drmKeysLoaded();
            }
        }

        public void onDrmKeysRemoved(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            if (a(i, mediaPeriodId)) {
                this.g.drmKeysRemoved();
            }
        }

        public void onDrmKeysRestored(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            if (a(i, mediaPeriodId)) {
                this.g.drmKeysRestored();
            }
        }

        @Deprecated
        public /* bridge */ /* synthetic */ void onDrmSessionAcquired(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            t1.d(this, i, mediaPeriodId);
        }

        public void onDrmSessionAcquired(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, int i2) {
            if (a(i, mediaPeriodId)) {
                this.g.drmSessionAcquired(i2);
            }
        }

        public void onDrmSessionManagerError(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, Exception exc) {
            if (a(i, mediaPeriodId)) {
                this.g.drmSessionManagerError(exc);
            }
        }

        public void onDrmSessionReleased(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            if (a(i, mediaPeriodId)) {
                this.g.drmSessionReleased();
            }
        }

        public void onLoadCanceled(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            if (a(i, mediaPeriodId)) {
                this.f.loadCanceled(loadEventInfo, b(mediaLoadData));
            }
        }

        public void onLoadCompleted(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            if (a(i, mediaPeriodId)) {
                this.f.loadCompleted(loadEventInfo, b(mediaLoadData));
            }
        }

        public void onLoadError(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z) {
            if (a(i, mediaPeriodId)) {
                this.f.loadError(loadEventInfo, b(mediaLoadData), iOException, z);
            }
        }

        public void onLoadStarted(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            if (a(i, mediaPeriodId)) {
                this.f.loadStarted(loadEventInfo, b(mediaLoadData));
            }
        }

        public void onUpstreamDiscarded(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
            if (a(i, mediaPeriodId)) {
                this.f.upstreamDiscarded(b(mediaLoadData));
            }
        }
    }

    public static final class b<T> {
        public final MediaSource a;
        public final MediaSource.MediaSourceCaller b;
        public final CompositeMediaSource<T>.defpackage.a c;

        public b(MediaSource mediaSource, MediaSource.MediaSourceCaller mediaSourceCaller, CompositeMediaSource<T>.defpackage.a aVar) {
            this.a = mediaSource;
            this.b = mediaSourceCaller;
            this.c = aVar;
        }
    }

    @CallSuper
    public void a() {
        for (b next : this.g.values()) {
            next.a.disable(next.b);
        }
    }

    @CallSuper
    public void b() {
        for (b next : this.g.values()) {
            next.a.enable(next.b);
        }
    }

    public abstract /* synthetic */ MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j);

    @Nullable
    public MediaSource.MediaPeriodId d(@UnknownNull T t, MediaSource.MediaPeriodId mediaPeriodId) {
        return mediaPeriodId;
    }

    public int e(int i2, @UnknownNull Object obj) {
        return i2;
    }

    public abstract void f(@UnknownNull T t, MediaSource mediaSource, Timeline timeline);

    public final void g(@UnknownNull T t, MediaSource mediaSource) {
        HashMap<T, b<T>> hashMap = this.g;
        Assertions.checkArgument(!hashMap.containsKey(t));
        q0 q0Var = new q0(this, t);
        a aVar = new a(t);
        hashMap.put(t, new b(mediaSource, q0Var, aVar));
        mediaSource.addEventListener((Handler) Assertions.checkNotNull(this.h), aVar);
        mediaSource.addDrmEventListener((Handler) Assertions.checkNotNull(this.h), aVar);
        mediaSource.prepareSource(q0Var, this.i);
        if (!(!this.b.isEmpty())) {
            mediaSource.disable(q0Var);
        }
    }

    @Nullable
    public /* bridge */ /* synthetic */ Timeline getInitialTimeline() {
        return e6.a(this);
    }

    public abstract /* synthetic */ MediaItem getMediaItem();

    @Deprecated
    @Nullable
    public /* bridge */ /* synthetic */ Object getTag() {
        return e6.b(this);
    }

    public final void h(@UnknownNull T t) {
        b bVar = (b) Assertions.checkNotNull(this.g.remove(t));
        bVar.a.releaseSource(bVar.b);
        MediaSource mediaSource = bVar.a;
        CompositeMediaSource<T>.defpackage.a aVar = bVar.c;
        mediaSource.removeEventListener(aVar);
        mediaSource.removeDrmEventListener(aVar);
    }

    public /* bridge */ /* synthetic */ boolean isSingleWindow() {
        return e6.c(this);
    }

    @CallSuper
    public void maybeThrowSourceInfoRefreshError() throws IOException {
        for (b<T> bVar : this.g.values()) {
            bVar.a.maybeThrowSourceInfoRefreshError();
        }
    }

    @CallSuper
    public void prepareSourceInternal(@Nullable TransferListener transferListener) {
        this.i = transferListener;
        this.h = Util.createHandlerForCurrentLooper();
    }

    public abstract /* synthetic */ void releasePeriod(MediaPeriod mediaPeriod);

    @CallSuper
    public void releaseSourceInternal() {
        HashMap<T, b<T>> hashMap = this.g;
        for (b next : hashMap.values()) {
            next.a.releaseSource(next.b);
            MediaSource mediaSource = next.a;
            CompositeMediaSource<T>.defpackage.a aVar = next.c;
            mediaSource.removeEventListener(aVar);
            mediaSource.removeDrmEventListener(aVar);
        }
        hashMap.clear();
    }
}
