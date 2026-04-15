package com.google.android.exoplayer2.source;

import android.net.Uri;
import android.os.Handler;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.AbstractConcatenatedTimeline;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.CompositeMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Set;

public final class ConcatenatingMediaSource extends CompositeMediaSource<d> {
    public static final MediaItem v = new MediaItem.Builder().setUri(Uri.EMPTY).build();
    @GuardedBy("this")
    public final ArrayList j;
    @GuardedBy("this")
    public final HashSet k;
    @GuardedBy("this")
    @Nullable
    public Handler l;
    public final ArrayList m;
    public final IdentityHashMap<MediaPeriod, d> n;
    public final HashMap o;
    public final HashSet p;
    public final boolean q;
    public final boolean r;
    public boolean s;
    public HashSet t;
    public ShuffleOrder u;

    public static final class a extends AbstractConcatenatedTimeline {
        public final int i;
        public final int j;
        public final int[] k;
        public final int[] l;
        public final Timeline[] m;
        public final Object[] n;
        public final HashMap<Object, Integer> o = new HashMap<>();

        public a(Collection<d> collection, ShuffleOrder shuffleOrder, boolean z) {
            super(z, shuffleOrder);
            int size = collection.size();
            this.k = new int[size];
            this.l = new int[size];
            this.m = new Timeline[size];
            this.n = new Object[size];
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            for (d next : collection) {
                this.m[i4] = next.a.getTimeline();
                this.l[i4] = i2;
                this.k[i4] = i3;
                i2 += this.m[i4].getWindowCount();
                i3 += this.m[i4].getPeriodCount();
                Object[] objArr = this.n;
                Object obj = next.b;
                objArr[i4] = obj;
                this.o.put(obj, Integer.valueOf(i4));
                i4++;
            }
            this.i = i2;
            this.j = i3;
        }

        public final int b(Object obj) {
            Integer num = this.o.get(obj);
            if (num == null) {
                return -1;
            }
            return num.intValue();
        }

        public final int c(int i2) {
            return Util.binarySearchFloor(this.k, i2 + 1, false, false);
        }

        public final int d(int i2) {
            return Util.binarySearchFloor(this.l, i2 + 1, false, false);
        }

        public final Object e(int i2) {
            return this.n[i2];
        }

        public final int f(int i2) {
            return this.k[i2];
        }

        public final int g(int i2) {
            return this.l[i2];
        }

        public int getPeriodCount() {
            return this.j;
        }

        public int getWindowCount() {
            return this.i;
        }

        public final Timeline i(int i2) {
            return this.m[i2];
        }
    }

    public static final class b extends BaseMediaSource {
        public b(int i) {
        }

        public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
            throw new UnsupportedOperationException();
        }

        @Nullable
        public /* bridge */ /* synthetic */ Timeline getInitialTimeline() {
            return e6.a(this);
        }

        public MediaItem getMediaItem() {
            return ConcatenatingMediaSource.v;
        }

        @Deprecated
        @Nullable
        public /* bridge */ /* synthetic */ Object getTag() {
            return e6.b(this);
        }

        public /* bridge */ /* synthetic */ boolean isSingleWindow() {
            return e6.c(this);
        }

        public void maybeThrowSourceInfoRefreshError() {
        }

        public final void prepareSourceInternal(@Nullable TransferListener transferListener) {
        }

        public void releasePeriod(MediaPeriod mediaPeriod) {
        }

        public final void releaseSourceInternal() {
        }
    }

    public static final class c {
        public final Handler a;
        public final Runnable b;

        public c(Handler handler, Runnable runnable) {
            this.a = handler;
            this.b = runnable;
        }

        public void dispatch() {
            this.a.post(this.b);
        }
    }

    public static final class d {
        public final MaskingMediaSource a;
        public final Object b = new Object();
        public final ArrayList c = new ArrayList();
        public int d;
        public int e;
        public boolean f;

        public d(MediaSource mediaSource, boolean z) {
            this.a = new MaskingMediaSource(mediaSource, z);
        }

        public void reset(int i, int i2) {
            this.d = i;
            this.e = i2;
            this.f = false;
            this.c.clear();
        }
    }

    public static final class e<T> {
        public final int a;
        public final T b;
        @Nullable
        public final c c;

        public e(int i, T t, @Nullable c cVar) {
            this.a = i;
            this.b = t;
            this.c = cVar;
        }
    }

    public ConcatenatingMediaSource(MediaSource... mediaSourceArr) {
        this(false, mediaSourceArr);
    }

    public final void a() {
        super.a();
        this.p.clear();
    }

    public synchronized void addMediaSource(MediaSource mediaSource) {
        addMediaSource(this.j.size(), mediaSource);
    }

    public synchronized void addMediaSources(Collection<MediaSource> collection) {
        j(this.j.size(), collection, (Handler) null, (Runnable) null);
    }

    public final void b() {
    }

    public synchronized void clear() {
        removeMediaSourceRange(0, getSize());
    }

    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j2) {
        Object childTimelineUidFromConcatenatedUid = AbstractConcatenatedTimeline.getChildTimelineUidFromConcatenatedUid(mediaPeriodId.a);
        MediaSource.MediaPeriodId copyWithPeriodUid = mediaPeriodId.copyWithPeriodUid(AbstractConcatenatedTimeline.getChildPeriodUidFromConcatenatedUid(mediaPeriodId.a));
        d dVar = (d) this.o.get(childTimelineUidFromConcatenatedUid);
        if (dVar == null) {
            dVar = new d(new b(0), this.r);
            dVar.f = true;
            g(dVar, dVar.a);
        }
        this.p.add(dVar);
        CompositeMediaSource.b bVar = (CompositeMediaSource.b) Assertions.checkNotNull(this.g.get(dVar));
        bVar.a.enable(bVar.b);
        dVar.c.add(copyWithPeriodUid);
        MaskingMediaPeriod createPeriod = dVar.a.createPeriod(copyWithPeriodUid, allocator, j2);
        this.n.put(createPeriod, dVar);
        m();
        return createPeriod;
    }

    @Nullable
    public final MediaSource.MediaPeriodId d(Object obj, MediaSource.MediaPeriodId mediaPeriodId) {
        d dVar = (d) obj;
        for (int i = 0; i < dVar.c.size(); i++) {
            if (((MediaSource.MediaPeriodId) dVar.c.get(i)).d == mediaPeriodId.d) {
                return mediaPeriodId.copyWithPeriodUid(AbstractConcatenatedTimeline.getConcatenatedUid(dVar.b, mediaPeriodId.a));
            }
        }
        return null;
    }

    public final int e(int i, Object obj) {
        return i + ((d) obj).e;
    }

    public final void f(Object obj, MediaSource mediaSource, Timeline timeline) {
        int windowCount;
        d dVar = (d) obj;
        int i = dVar.d + 1;
        ArrayList arrayList = this.m;
        if (i < arrayList.size() && (windowCount = timeline.getWindowCount() - (((d) arrayList.get(dVar.d + 1)).e - dVar.e)) != 0) {
            k(dVar.d + 1, 0, windowCount);
        }
        q((c) null);
    }

    public synchronized Timeline getInitialTimeline() {
        ShuffleOrder shuffleOrder;
        if (this.u.getLength() != this.j.size()) {
            shuffleOrder = this.u.cloneAndClear().cloneAndInsert(0, this.j.size());
        } else {
            shuffleOrder = this.u;
        }
        return new a(this.j, shuffleOrder, this.q);
    }

    public MediaItem getMediaItem() {
        return v;
    }

    public synchronized MediaSource getMediaSource(int i) {
        return ((d) this.j.get(i)).a;
    }

    public synchronized int getSize() {
        return this.j.size();
    }

    @Deprecated
    @Nullable
    public /* bridge */ /* synthetic */ Object getTag() {
        return e6.b(this);
    }

    public final void i(int i, Collection<d> collection) {
        for (d next : collection) {
            int i2 = i + 1;
            ArrayList arrayList = this.m;
            if (i > 0) {
                d dVar = (d) arrayList.get(i - 1);
                Timeline timeline = dVar.a.getTimeline();
                next.reset(i, timeline.getWindowCount() + dVar.e);
            } else {
                next.reset(i, 0);
            }
            k(i, 1, next.a.getTimeline().getWindowCount());
            arrayList.add(i, next);
            this.o.put(next.b, next);
            g(next, next.a);
            if (!(!this.b.isEmpty()) || !this.n.isEmpty()) {
                CompositeMediaSource.b bVar = (CompositeMediaSource.b) Assertions.checkNotNull(this.g.get(next));
                bVar.a.disable(bVar.b);
            } else {
                this.p.add(next);
            }
            i = i2;
        }
    }

    public boolean isSingleWindow() {
        return false;
    }

    @GuardedBy("this")
    public final void j(int i, Collection<MediaSource> collection, @Nullable Handler handler, @Nullable Runnable runnable) {
        boolean z;
        boolean z2;
        boolean z3 = true;
        if (handler == null) {
            z = true;
        } else {
            z = false;
        }
        if (runnable == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z != z2) {
            z3 = false;
        }
        Assertions.checkArgument(z3);
        Handler handler2 = this.l;
        for (MediaSource checkNotNull : collection) {
            Assertions.checkNotNull(checkNotNull);
        }
        ArrayList arrayList = new ArrayList(collection.size());
        for (MediaSource dVar : collection) {
            arrayList.add(new d(dVar, this.r));
        }
        this.j.addAll(i, arrayList);
        if (handler2 != null && !collection.isEmpty()) {
            handler2.obtainMessage(0, new e(i, arrayList, l(handler, runnable))).sendToTarget();
        } else if (runnable != null && handler != null) {
            handler.post(runnable);
        }
    }

    public final void k(int i, int i2, int i3) {
        while (true) {
            ArrayList arrayList = this.m;
            if (i < arrayList.size()) {
                d dVar = (d) arrayList.get(i);
                dVar.d += i2;
                dVar.e += i3;
                i++;
            } else {
                return;
            }
        }
    }

    @GuardedBy("this")
    @Nullable
    public final c l(@Nullable Handler handler, @Nullable Runnable runnable) {
        if (handler == null || runnable == null) {
            return null;
        }
        c cVar = new c(handler, runnable);
        this.k.add(cVar);
        return cVar;
    }

    public final void m() {
        Iterator it = this.p.iterator();
        while (it.hasNext()) {
            d dVar = (d) it.next();
            if (dVar.c.isEmpty()) {
                CompositeMediaSource.b bVar = (CompositeMediaSource.b) Assertions.checkNotNull(this.g.get(dVar));
                bVar.a.disable(bVar.b);
                it.remove();
            }
        }
    }

    public synchronized void moveMediaSource(int i, int i2) {
        o(i, i2, (Handler) null, (Runnable) null);
    }

    public final synchronized void n(Set<c> set) {
        for (c dispatch : set) {
            dispatch.dispatch();
        }
        this.k.removeAll(set);
    }

    @GuardedBy("this")
    public final void o(int i, int i2, @Nullable Handler handler, @Nullable Runnable runnable) {
        boolean z;
        boolean z2;
        boolean z3 = false;
        if (handler == null) {
            z = true;
        } else {
            z = false;
        }
        if (runnable == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z == z2) {
            z3 = true;
        }
        Assertions.checkArgument(z3);
        Handler handler2 = this.l;
        ArrayList arrayList = this.j;
        arrayList.add(i2, (d) arrayList.remove(i));
        if (handler2 != null) {
            handler2.obtainMessage(2, new e(i, Integer.valueOf(i2), l(handler, runnable))).sendToTarget();
        } else if (runnable != null && handler != null) {
            handler.post(runnable);
        }
    }

    @GuardedBy("this")
    public final void p(int i, int i2, @Nullable Handler handler, @Nullable Runnable runnable) {
        boolean z;
        boolean z2;
        boolean z3 = false;
        if (handler == null) {
            z = true;
        } else {
            z = false;
        }
        if (runnable == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z == z2) {
            z3 = true;
        }
        Assertions.checkArgument(z3);
        Handler handler2 = this.l;
        Util.removeRange(this.j, i, i2);
        if (handler2 != null) {
            handler2.obtainMessage(1, new e(i, Integer.valueOf(i2), l(handler, runnable))).sendToTarget();
        } else if (runnable != null && handler != null) {
            handler.post(runnable);
        }
    }

    public final synchronized void prepareSourceInternal(@Nullable TransferListener transferListener) {
        super.prepareSourceInternal(transferListener);
        this.l = new Handler(new q1(2, this));
        if (this.j.isEmpty()) {
            s();
        } else {
            this.u = this.u.cloneAndInsert(0, this.j.size());
            i(0, this.j);
            q((c) null);
        }
    }

    public final void q(@Nullable c cVar) {
        if (!this.s) {
            ((Handler) Assertions.checkNotNull(this.l)).obtainMessage(4).sendToTarget();
            this.s = true;
        }
        if (cVar != null) {
            this.t.add(cVar);
        }
    }

    @GuardedBy("this")
    public final void r(ShuffleOrder shuffleOrder, @Nullable Handler handler, @Nullable Runnable runnable) {
        boolean z;
        boolean z2;
        boolean z3 = true;
        if (handler == null) {
            z = true;
        } else {
            z = false;
        }
        if (runnable == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z != z2) {
            z3 = false;
        }
        Assertions.checkArgument(z3);
        Handler handler2 = this.l;
        if (handler2 != null) {
            int size = getSize();
            if (shuffleOrder.getLength() != size) {
                shuffleOrder = shuffleOrder.cloneAndClear().cloneAndInsert(0, size);
            }
            handler2.obtainMessage(3, new e(0, shuffleOrder, l(handler, runnable))).sendToTarget();
            return;
        }
        if (shuffleOrder.getLength() > 0) {
            shuffleOrder = shuffleOrder.cloneAndClear();
        }
        this.u = shuffleOrder;
        if (runnable != null && handler != null) {
            handler.post(runnable);
        }
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        IdentityHashMap<MediaPeriod, d> identityHashMap = this.n;
        d dVar = (d) Assertions.checkNotNull(identityHashMap.remove(mediaPeriod));
        dVar.a.releasePeriod(mediaPeriod);
        ArrayList arrayList = dVar.c;
        arrayList.remove(((MaskingMediaPeriod) mediaPeriod).c);
        if (!identityHashMap.isEmpty()) {
            m();
        }
        if (dVar.f && arrayList.isEmpty()) {
            this.p.remove(dVar);
            h(dVar);
        }
    }

    public final synchronized void releaseSourceInternal() {
        super.releaseSourceInternal();
        this.m.clear();
        this.p.clear();
        this.o.clear();
        this.u = this.u.cloneAndClear();
        Handler handler = this.l;
        if (handler != null) {
            handler.removeCallbacksAndMessages((Object) null);
            this.l = null;
        }
        this.s = false;
        this.t.clear();
        n(this.k);
    }

    public synchronized MediaSource removeMediaSource(int i) {
        MediaSource mediaSource;
        mediaSource = getMediaSource(i);
        p(i, i + 1, (Handler) null, (Runnable) null);
        return mediaSource;
    }

    public synchronized void removeMediaSourceRange(int i, int i2) {
        p(i, i2, (Handler) null, (Runnable) null);
    }

    public final void s() {
        this.s = false;
        HashSet hashSet = this.t;
        this.t = new HashSet();
        c(new a(this.m, this.u, this.q));
        ((Handler) Assertions.checkNotNull(this.l)).obtainMessage(5, hashSet).sendToTarget();
    }

    public synchronized void setShuffleOrder(ShuffleOrder shuffleOrder) {
        r(shuffleOrder, (Handler) null, (Runnable) null);
    }

    public ConcatenatingMediaSource(boolean z, MediaSource... mediaSourceArr) {
        this(z, new ShuffleOrder.DefaultShuffleOrder(0), mediaSourceArr);
    }

    public ConcatenatingMediaSource(boolean z, ShuffleOrder shuffleOrder, MediaSource... mediaSourceArr) {
        this(z, false, shuffleOrder, mediaSourceArr);
    }

    public synchronized void addMediaSource(MediaSource mediaSource, Handler handler, Runnable runnable) {
        addMediaSource(this.j.size(), mediaSource, handler, runnable);
    }

    public synchronized void clear(Handler handler, Runnable runnable) {
        removeMediaSourceRange(0, getSize(), handler, runnable);
    }

    public synchronized void moveMediaSource(int i, int i2, Handler handler, Runnable runnable) {
        o(i, i2, handler, runnable);
    }

    public synchronized void removeMediaSourceRange(int i, int i2, Handler handler, Runnable runnable) {
        p(i, i2, handler, runnable);
    }

    public synchronized void setShuffleOrder(ShuffleOrder shuffleOrder, Handler handler, Runnable runnable) {
        r(shuffleOrder, handler, runnable);
    }

    public ConcatenatingMediaSource(boolean z, boolean z2, ShuffleOrder shuffleOrder, MediaSource... mediaSourceArr) {
        for (MediaSource checkNotNull : mediaSourceArr) {
            Assertions.checkNotNull(checkNotNull);
        }
        this.u = shuffleOrder.getLength() > 0 ? shuffleOrder.cloneAndClear() : shuffleOrder;
        this.n = new IdentityHashMap<>();
        this.o = new HashMap();
        this.j = new ArrayList();
        this.m = new ArrayList();
        this.t = new HashSet();
        this.k = new HashSet();
        this.p = new HashSet();
        this.q = z;
        this.r = z2;
        addMediaSources(Arrays.asList(mediaSourceArr));
    }

    public synchronized MediaSource removeMediaSource(int i, Handler handler, Runnable runnable) {
        MediaSource mediaSource;
        mediaSource = getMediaSource(i);
        p(i, i + 1, handler, runnable);
        return mediaSource;
    }

    public synchronized void addMediaSource(int i, MediaSource mediaSource) {
        j(i, Collections.singletonList(mediaSource), (Handler) null, (Runnable) null);
    }

    public synchronized void addMediaSources(Collection<MediaSource> collection, Handler handler, Runnable runnable) {
        j(this.j.size(), collection, handler, runnable);
    }

    public synchronized void addMediaSources(int i, Collection<MediaSource> collection) {
        j(i, collection, (Handler) null, (Runnable) null);
    }

    public synchronized void addMediaSource(int i, MediaSource mediaSource, Handler handler, Runnable runnable) {
        j(i, Collections.singletonList(mediaSource), handler, runnable);
    }

    public synchronized void addMediaSources(int i, Collection<MediaSource> collection, Handler handler, Runnable runnable) {
        j(i, collection, handler, runnable);
    }
}
