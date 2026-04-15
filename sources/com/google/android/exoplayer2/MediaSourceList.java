package com.google.android.exoplayer2;

import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MaskingMediaPeriod;
import com.google.android.exoplayer2.source.MaskingMediaSource;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;

public final class MediaSourceList {
    public final ArrayList a = new ArrayList();
    public final IdentityHashMap<MediaPeriod, c> b = new IdentityHashMap<>();
    public final HashMap c = new HashMap();
    public final MediaSourceListInfoRefreshListener d;
    public final MediaSourceEventListener.EventDispatcher e;
    public final DrmSessionEventListener.EventDispatcher f;
    public final HashMap<c, b> g;
    public final HashSet h;
    public ShuffleOrder i = new ShuffleOrder.DefaultShuffleOrder(0);
    public boolean j;
    @Nullable
    public TransferListener k;

    public interface MediaSourceListInfoRefreshListener {
        void onPlaylistUpdateRequested();
    }

    public final class a implements MediaSourceEventListener, DrmSessionEventListener {
        public final c c;
        public MediaSourceEventListener.EventDispatcher f;
        public DrmSessionEventListener.EventDispatcher g;

        public a(c cVar) {
            this.f = MediaSourceList.this.e;
            this.g = MediaSourceList.this.f;
            this.c = cVar;
        }

        public final boolean a(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            c cVar = this.c;
            MediaSource.MediaPeriodId mediaPeriodId2 = null;
            if (mediaPeriodId != null) {
                int i2 = 0;
                while (true) {
                    if (i2 >= cVar.c.size()) {
                        break;
                    } else if (((MediaSource.MediaPeriodId) cVar.c.get(i2)).d == mediaPeriodId.d) {
                        mediaPeriodId2 = mediaPeriodId.copyWithPeriodUid(AbstractConcatenatedTimeline.getConcatenatedUid(cVar.b, mediaPeriodId.a));
                        break;
                    } else {
                        i2++;
                    }
                }
                if (mediaPeriodId2 == null) {
                    return false;
                }
            }
            int i3 = i + cVar.d;
            MediaSourceEventListener.EventDispatcher eventDispatcher = this.f;
            int i4 = eventDispatcher.a;
            MediaSourceList mediaSourceList = MediaSourceList.this;
            if (i4 != i3 || !Util.areEqual(eventDispatcher.b, mediaPeriodId2)) {
                this.f = mediaSourceList.e.withParameters(i3, mediaPeriodId2, 0);
            }
            DrmSessionEventListener.EventDispatcher eventDispatcher2 = this.g;
            if (eventDispatcher2.a == i3 && Util.areEqual(eventDispatcher2.b, mediaPeriodId2)) {
                return true;
            }
            this.g = mediaSourceList.f.withParameters(i3, mediaPeriodId2);
            return true;
        }

        public void onDownstreamFormatChanged(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
            if (a(i, mediaPeriodId)) {
                this.f.downstreamFormatChanged(mediaLoadData);
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
                this.f.loadCanceled(loadEventInfo, mediaLoadData);
            }
        }

        public void onLoadCompleted(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            if (a(i, mediaPeriodId)) {
                this.f.loadCompleted(loadEventInfo, mediaLoadData);
            }
        }

        public void onLoadError(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z) {
            if (a(i, mediaPeriodId)) {
                this.f.loadError(loadEventInfo, mediaLoadData, iOException, z);
            }
        }

        public void onLoadStarted(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            if (a(i, mediaPeriodId)) {
                this.f.loadStarted(loadEventInfo, mediaLoadData);
            }
        }

        public void onUpstreamDiscarded(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
            if (a(i, mediaPeriodId)) {
                this.f.upstreamDiscarded(mediaLoadData);
            }
        }
    }

    public static final class b {
        public final MediaSource a;
        public final MediaSource.MediaSourceCaller b;
        public final a c;

        public b(MediaSource mediaSource, MediaSource.MediaSourceCaller mediaSourceCaller, a aVar) {
            this.a = mediaSource;
            this.b = mediaSourceCaller;
            this.c = aVar;
        }
    }

    public static final class c implements k6 {
        public final MaskingMediaSource a;
        public final Object b = new Object();
        public final ArrayList c = new ArrayList();
        public int d;
        public boolean e;

        public c(MediaSource mediaSource, boolean z) {
            this.a = new MaskingMediaSource(mediaSource, z);
        }

        public Timeline getTimeline() {
            return this.a.getTimeline();
        }

        public Object getUid() {
            return this.b;
        }

        public void reset(int i) {
            this.d = i;
            this.e = false;
            this.c.clear();
        }
    }

    public MediaSourceList(MediaSourceListInfoRefreshListener mediaSourceListInfoRefreshListener, @Nullable AnalyticsCollector analyticsCollector, Handler handler) {
        this.d = mediaSourceListInfoRefreshListener;
        MediaSourceEventListener.EventDispatcher eventDispatcher = new MediaSourceEventListener.EventDispatcher();
        this.e = eventDispatcher;
        DrmSessionEventListener.EventDispatcher eventDispatcher2 = new DrmSessionEventListener.EventDispatcher();
        this.f = eventDispatcher2;
        this.g = new HashMap<>();
        this.h = new HashSet();
        if (analyticsCollector != null) {
            eventDispatcher.addEventListener(handler, analyticsCollector);
            eventDispatcher2.addEventListener(handler, analyticsCollector);
        }
    }

    public final void a() {
        Iterator it = this.h.iterator();
        while (it.hasNext()) {
            c cVar = (c) it.next();
            if (cVar.c.isEmpty()) {
                b bVar = this.g.get(cVar);
                if (bVar != null) {
                    bVar.a.disable(bVar.b);
                }
                it.remove();
            }
        }
    }

    public Timeline addMediaSources(int i2, List<c> list, ShuffleOrder shuffleOrder) {
        if (!list.isEmpty()) {
            this.i = shuffleOrder;
            for (int i3 = i2; i3 < list.size() + i2; i3++) {
                c cVar = list.get(i3 - i2);
                ArrayList arrayList = this.a;
                if (i3 > 0) {
                    c cVar2 = (c) arrayList.get(i3 - 1);
                    cVar.reset(cVar2.a.getTimeline().getWindowCount() + cVar2.d);
                } else {
                    cVar.reset(0);
                }
                int windowCount = cVar.a.getTimeline().getWindowCount();
                for (int i4 = i3; i4 < arrayList.size(); i4++) {
                    ((c) arrayList.get(i4)).d += windowCount;
                }
                arrayList.add(i3, cVar);
                this.c.put(cVar.b, cVar);
                if (this.j) {
                    c(cVar);
                    if (this.b.isEmpty()) {
                        this.h.add(cVar);
                    } else {
                        b bVar = this.g.get(cVar);
                        if (bVar != null) {
                            bVar.a.disable(bVar.b);
                        }
                    }
                }
            }
        }
        return createTimeline();
    }

    public final void b(c cVar) {
        if (cVar.e && cVar.c.isEmpty()) {
            b bVar = (b) Assertions.checkNotNull(this.g.remove(cVar));
            bVar.a.releaseSource(bVar.b);
            MediaSource mediaSource = bVar.a;
            a aVar = bVar.c;
            mediaSource.removeEventListener(aVar);
            mediaSource.removeDrmEventListener(aVar);
            this.h.remove(cVar);
        }
    }

    public final void c(c cVar) {
        MaskingMediaSource maskingMediaSource = cVar.a;
        l6 l6Var = new l6(this);
        a aVar = new a(cVar);
        this.g.put(cVar, new b(maskingMediaSource, l6Var, aVar));
        maskingMediaSource.addEventListener(Util.createHandlerForCurrentOrMainLooper(), aVar);
        maskingMediaSource.addDrmEventListener(Util.createHandlerForCurrentOrMainLooper(), aVar);
        maskingMediaSource.prepareSource(l6Var, this.k);
    }

    public Timeline clear(@Nullable ShuffleOrder shuffleOrder) {
        if (shuffleOrder == null) {
            shuffleOrder = this.i.cloneAndClear();
        }
        this.i = shuffleOrder;
        d(0, getSize());
        return createTimeline();
    }

    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j2) {
        Object childTimelineUidFromConcatenatedUid = AbstractConcatenatedTimeline.getChildTimelineUidFromConcatenatedUid(mediaPeriodId.a);
        MediaSource.MediaPeriodId copyWithPeriodUid = mediaPeriodId.copyWithPeriodUid(AbstractConcatenatedTimeline.getChildPeriodUidFromConcatenatedUid(mediaPeriodId.a));
        c cVar = (c) Assertions.checkNotNull((c) this.c.get(childTimelineUidFromConcatenatedUid));
        this.h.add(cVar);
        b bVar = this.g.get(cVar);
        if (bVar != null) {
            bVar.a.enable(bVar.b);
        }
        cVar.c.add(copyWithPeriodUid);
        MaskingMediaPeriod createPeriod = cVar.a.createPeriod(copyWithPeriodUid, allocator, j2);
        this.b.put(createPeriod, cVar);
        a();
        return createPeriod;
    }

    public Timeline createTimeline() {
        ArrayList arrayList = this.a;
        if (arrayList.isEmpty()) {
            return Timeline.c;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            c cVar = (c) arrayList.get(i3);
            cVar.d = i2;
            i2 += cVar.a.getTimeline().getWindowCount();
        }
        return new z8(arrayList, this.i);
    }

    public final void d(int i2, int i3) {
        for (int i4 = i3 - 1; i4 >= i2; i4--) {
            ArrayList arrayList = this.a;
            c cVar = (c) arrayList.remove(i4);
            this.c.remove(cVar.b);
            int i5 = -cVar.a.getTimeline().getWindowCount();
            for (int i6 = i4; i6 < arrayList.size(); i6++) {
                ((c) arrayList.get(i6)).d += i5;
            }
            cVar.e = true;
            if (this.j) {
                b(cVar);
            }
        }
    }

    public int getSize() {
        return this.a.size();
    }

    public boolean isPrepared() {
        return this.j;
    }

    public Timeline moveMediaSource(int i2, int i3, ShuffleOrder shuffleOrder) {
        return moveMediaSourceRange(i2, i2 + 1, i3, shuffleOrder);
    }

    public Timeline moveMediaSourceRange(int i2, int i3, int i4, ShuffleOrder shuffleOrder) {
        boolean z;
        if (i2 < 0 || i2 > i3 || i3 > getSize() || i4 < 0) {
            z = false;
        } else {
            z = true;
        }
        Assertions.checkArgument(z);
        this.i = shuffleOrder;
        if (i2 == i3 || i2 == i4) {
            return createTimeline();
        }
        int min = Math.min(i2, i4);
        int max = Math.max(((i3 - i2) + i4) - 1, i3 - 1);
        ArrayList arrayList = this.a;
        int i5 = ((c) arrayList.get(min)).d;
        Util.moveItems(arrayList, i2, i3, i4);
        while (min <= max) {
            c cVar = (c) arrayList.get(min);
            cVar.d = i5;
            i5 += cVar.a.getTimeline().getWindowCount();
            min++;
        }
        return createTimeline();
    }

    public void prepare(@Nullable TransferListener transferListener) {
        Assertions.checkState(!this.j);
        this.k = transferListener;
        int i2 = 0;
        while (true) {
            ArrayList arrayList = this.a;
            if (i2 < arrayList.size()) {
                c cVar = (c) arrayList.get(i2);
                c(cVar);
                this.h.add(cVar);
                i2++;
            } else {
                this.j = true;
                return;
            }
        }
    }

    public void release() {
        HashMap<c, b> hashMap = this.g;
        for (b next : hashMap.values()) {
            try {
                next.a.releaseSource(next.b);
            } catch (RuntimeException e2) {
                Log.e("MediaSourceList", "Failed to release child source.", e2);
            }
            MediaSource mediaSource = next.a;
            a aVar = next.c;
            mediaSource.removeEventListener(aVar);
            next.a.removeDrmEventListener(aVar);
        }
        hashMap.clear();
        this.h.clear();
        this.j = false;
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        IdentityHashMap<MediaPeriod, c> identityHashMap = this.b;
        c cVar = (c) Assertions.checkNotNull(identityHashMap.remove(mediaPeriod));
        cVar.a.releasePeriod(mediaPeriod);
        cVar.c.remove(((MaskingMediaPeriod) mediaPeriod).c);
        if (!identityHashMap.isEmpty()) {
            a();
        }
        b(cVar);
    }

    public Timeline removeMediaSourceRange(int i2, int i3, ShuffleOrder shuffleOrder) {
        boolean z;
        if (i2 < 0 || i2 > i3 || i3 > getSize()) {
            z = false;
        } else {
            z = true;
        }
        Assertions.checkArgument(z);
        this.i = shuffleOrder;
        d(i2, i3);
        return createTimeline();
    }

    public Timeline setMediaSources(List<c> list, ShuffleOrder shuffleOrder) {
        ArrayList arrayList = this.a;
        d(0, arrayList.size());
        return addMediaSources(arrayList.size(), list, shuffleOrder);
    }

    public Timeline setShuffleOrder(ShuffleOrder shuffleOrder) {
        int size = getSize();
        if (shuffleOrder.getLength() != size) {
            shuffleOrder = shuffleOrder.cloneAndClear().cloneAndInsert(0, size);
        }
        this.i = shuffleOrder;
        return createTimeline();
    }
}
