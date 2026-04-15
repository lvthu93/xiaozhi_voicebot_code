package com.google.android.exoplayer2.source;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public abstract class BaseMediaSource implements MediaSource {
    public final ArrayList<MediaSource.MediaSourceCaller> a = new ArrayList<>(1);
    public final HashSet<MediaSource.MediaSourceCaller> b = new HashSet<>(1);
    public final MediaSourceEventListener.EventDispatcher c = new MediaSourceEventListener.EventDispatcher();
    public final DrmSessionEventListener.EventDispatcher d = new DrmSessionEventListener.EventDispatcher();
    @Nullable
    public Looper e;
    @Nullable
    public Timeline f;

    public void a() {
    }

    public final void addDrmEventListener(Handler handler, DrmSessionEventListener drmSessionEventListener) {
        Assertions.checkNotNull(handler);
        Assertions.checkNotNull(drmSessionEventListener);
        this.d.addEventListener(handler, drmSessionEventListener);
    }

    public final void addEventListener(Handler handler, MediaSourceEventListener mediaSourceEventListener) {
        Assertions.checkNotNull(handler);
        Assertions.checkNotNull(mediaSourceEventListener);
        this.c.addEventListener(handler, mediaSourceEventListener);
    }

    public void b() {
    }

    public final void c(Timeline timeline) {
        this.f = timeline;
        Iterator<MediaSource.MediaSourceCaller> it = this.a.iterator();
        while (it.hasNext()) {
            it.next().onSourceInfoRefreshed(this, timeline);
        }
    }

    public abstract /* synthetic */ MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j);

    public final void disable(MediaSource.MediaSourceCaller mediaSourceCaller) {
        HashSet<MediaSource.MediaSourceCaller> hashSet = this.b;
        boolean z = !hashSet.isEmpty();
        hashSet.remove(mediaSourceCaller);
        if (z && hashSet.isEmpty()) {
            a();
        }
    }

    public final void enable(MediaSource.MediaSourceCaller mediaSourceCaller) {
        Assertions.checkNotNull(this.e);
        HashSet<MediaSource.MediaSourceCaller> hashSet = this.b;
        boolean isEmpty = hashSet.isEmpty();
        hashSet.add(mediaSourceCaller);
        if (isEmpty) {
            b();
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

    public /* bridge */ /* synthetic */ boolean isSingleWindow() {
        return e6.c(this);
    }

    public abstract /* synthetic */ void maybeThrowSourceInfoRefreshError() throws IOException;

    public final void prepareSource(MediaSource.MediaSourceCaller mediaSourceCaller, @Nullable TransferListener transferListener) {
        boolean z;
        Looper myLooper = Looper.myLooper();
        Looper looper = this.e;
        if (looper == null || looper == myLooper) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkArgument(z);
        Timeline timeline = this.f;
        this.a.add(mediaSourceCaller);
        if (this.e == null) {
            this.e = myLooper;
            this.b.add(mediaSourceCaller);
            prepareSourceInternal(transferListener);
        } else if (timeline != null) {
            enable(mediaSourceCaller);
            mediaSourceCaller.onSourceInfoRefreshed(this, timeline);
        }
    }

    public abstract void prepareSourceInternal(@Nullable TransferListener transferListener);

    public abstract /* synthetic */ void releasePeriod(MediaPeriod mediaPeriod);

    public final void releaseSource(MediaSource.MediaSourceCaller mediaSourceCaller) {
        ArrayList<MediaSource.MediaSourceCaller> arrayList = this.a;
        arrayList.remove(mediaSourceCaller);
        if (arrayList.isEmpty()) {
            this.e = null;
            this.f = null;
            this.b.clear();
            releaseSourceInternal();
            return;
        }
        disable(mediaSourceCaller);
    }

    public abstract void releaseSourceInternal();

    public final void removeDrmEventListener(DrmSessionEventListener drmSessionEventListener) {
        this.d.removeEventListener(drmSessionEventListener);
    }

    public final void removeEventListener(MediaSourceEventListener mediaSourceEventListener) {
        this.c.removeEventListener(mediaSourceEventListener);
    }
}
