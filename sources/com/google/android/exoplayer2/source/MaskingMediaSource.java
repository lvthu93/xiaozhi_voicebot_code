package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ads.AdPlaybackState;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

public final class MaskingMediaSource extends CompositeMediaSource<Void> {
    public final MediaSource j;
    public final boolean k;
    public final Timeline.Window l;
    public final Timeline.Period m;
    public a n;
    @Nullable
    public MaskingMediaPeriod o;
    public boolean p;
    public boolean q;
    public boolean r;

    @VisibleForTesting
    public static final class PlaceholderTimeline extends Timeline {
        public final MediaItem f;

        public PlaceholderTimeline(MediaItem mediaItem) {
            this.f = mediaItem;
        }

        public int getIndexOfPeriod(Object obj) {
            return obj == a.i ? 0 : -1;
        }

        public Timeline.Period getPeriod(int i, Timeline.Period period, boolean z) {
            Integer num;
            Object obj = null;
            if (z) {
                num = 0;
            } else {
                num = null;
            }
            if (z) {
                obj = a.i;
            }
            period.set(num, obj, 0, -9223372036854775807L, 0, AdPlaybackState.k, true);
            return period;
        }

        public int getPeriodCount() {
            return 1;
        }

        public Object getUidOfPeriod(int i) {
            return a.i;
        }

        public Timeline.Window getWindow(int i, Timeline.Window window, long j) {
            Timeline.Window window2 = window;
            window.set(Timeline.Window.v, this.f, (Object) null, -9223372036854775807L, -9223372036854775807L, -9223372036854775807L, false, true, (MediaItem.LiveConfiguration) null, 0, -9223372036854775807L, 0, 0, 0);
            Timeline.Window window3 = window;
            window3.p = true;
            return window3;
        }

        public int getWindowCount() {
            return 1;
        }
    }

    public static final class a extends ForwardingTimeline {
        public static final Object i = new Object();
        @Nullable
        public final Object g;
        @Nullable
        public final Object h;

        public a(Timeline timeline, @Nullable Object obj, @Nullable Object obj2) {
            super(timeline);
            this.g = obj;
            this.h = obj2;
        }

        public static a createWithPlaceholderTimeline(MediaItem mediaItem) {
            return new a(new PlaceholderTimeline(mediaItem), Timeline.Window.v, i);
        }

        public static a createWithRealTimeline(Timeline timeline, @Nullable Object obj, @Nullable Object obj2) {
            return new a(timeline, obj, obj2);
        }

        public a cloneWithUpdatedTimeline(Timeline timeline) {
            return new a(timeline, this.g, this.h);
        }

        public int getIndexOfPeriod(Object obj) {
            Object obj2;
            if (i.equals(obj) && (obj2 = this.h) != null) {
                obj = obj2;
            }
            return this.f.getIndexOfPeriod(obj);
        }

        public Timeline.Period getPeriod(int i2, Timeline.Period period, boolean z) {
            this.f.getPeriod(i2, period, z);
            if (Util.areEqual(period.f, this.h) && z) {
                period.f = i;
            }
            return period;
        }

        public Timeline getTimeline() {
            return this.f;
        }

        public Object getUidOfPeriod(int i2) {
            Object uidOfPeriod = this.f.getUidOfPeriod(i2);
            if (Util.areEqual(uidOfPeriod, this.h)) {
                return i;
            }
            return uidOfPeriod;
        }

        public Timeline.Window getWindow(int i2, Timeline.Window window, long j) {
            this.f.getWindow(i2, window, j);
            if (Util.areEqual(window.c, this.g)) {
                window.c = Timeline.Window.v;
            }
            return window;
        }
    }

    public MaskingMediaSource(MediaSource mediaSource, boolean z) {
        boolean z2;
        this.j = mediaSource;
        if (!z || !mediaSource.isSingleWindow()) {
            z2 = false;
        } else {
            z2 = true;
        }
        this.k = z2;
        this.l = new Timeline.Window();
        this.m = new Timeline.Period();
        Timeline initialTimeline = mediaSource.getInitialTimeline();
        if (initialTimeline != null) {
            this.n = a.createWithRealTimeline(initialTimeline, (Object) null, (Object) null);
            this.r = true;
            return;
        }
        this.n = a.createWithPlaceholderTimeline(mediaSource.getMediaItem());
    }

    @Nullable
    public final MediaSource.MediaPeriodId d(Object obj, MediaSource.MediaPeriodId mediaPeriodId) {
        Void voidR = (Void) obj;
        Object obj2 = mediaPeriodId.a;
        Object obj3 = this.n.h;
        if (obj3 != null && obj3.equals(obj2)) {
            obj2 = a.i;
        }
        return mediaPeriodId.copyWithPeriodUid(obj2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0088  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0099  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00c4  */
    /* JADX WARNING: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void f(java.lang.Object r11, com.google.android.exoplayer2.source.MediaSource r12, com.google.android.exoplayer2.Timeline r13) {
        /*
            r10 = this;
            r0 = r11
            java.lang.Void r0 = (java.lang.Void) r0
            boolean r0 = r10.q
            if (r0 == 0) goto L_0x001c
            com.google.android.exoplayer2.source.MaskingMediaSource$a r0 = r10.n
            com.google.android.exoplayer2.source.MaskingMediaSource$a r0 = r0.cloneWithUpdatedTimeline(r13)
            r10.n = r0
            com.google.android.exoplayer2.source.MaskingMediaPeriod r0 = r10.o
            if (r0 == 0) goto L_0x00b7
            long r0 = r0.getPreparePositionOverrideUs()
            r10.i(r0)
            goto L_0x00b7
        L_0x001c:
            boolean r0 = r13.isEmpty()
            if (r0 == 0) goto L_0x0039
            boolean r0 = r10.r
            if (r0 == 0) goto L_0x002d
            com.google.android.exoplayer2.source.MaskingMediaSource$a r0 = r10.n
            com.google.android.exoplayer2.source.MaskingMediaSource$a r0 = r0.cloneWithUpdatedTimeline(r13)
            goto L_0x0035
        L_0x002d:
            java.lang.Object r0 = com.google.android.exoplayer2.Timeline.Window.v
            java.lang.Object r1 = com.google.android.exoplayer2.source.MaskingMediaSource.a.i
            com.google.android.exoplayer2.source.MaskingMediaSource$a r0 = com.google.android.exoplayer2.source.MaskingMediaSource.a.createWithRealTimeline(r13, r0, r1)
        L_0x0035:
            r10.n = r0
            goto L_0x00b7
        L_0x0039:
            r0 = 0
            com.google.android.exoplayer2.Timeline$Window r1 = r10.l
            r13.getWindow(r0, r1)
            long r2 = r1.getDefaultPositionUs()
            java.lang.Object r6 = r1.c
            com.google.android.exoplayer2.source.MaskingMediaPeriod r4 = r10.o
            if (r4 == 0) goto L_0x006f
            long r4 = r4.getPreparePositionUs()
            com.google.android.exoplayer2.source.MaskingMediaSource$a r7 = r10.n
            com.google.android.exoplayer2.source.MaskingMediaPeriod r8 = r10.o
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r8 = r8.c
            java.lang.Object r8 = r8.a
            com.google.android.exoplayer2.Timeline$Period r9 = r10.m
            r7.getPeriodByUid(r8, r9)
            long r7 = r9.getPositionInWindowUs()
            long r7 = r7 + r4
            com.google.android.exoplayer2.source.MaskingMediaSource$a r4 = r10.n
            com.google.android.exoplayer2.Timeline$Window r0 = r4.getWindow(r0, r1)
            long r0 = r0.getDefaultPositionUs()
            int r4 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
            if (r4 == 0) goto L_0x006f
            r4 = r7
            goto L_0x0070
        L_0x006f:
            r4 = r2
        L_0x0070:
            com.google.android.exoplayer2.Timeline$Window r1 = r10.l
            com.google.android.exoplayer2.Timeline$Period r2 = r10.m
            r3 = 0
            r0 = r13
            android.util.Pair r0 = r0.getPeriodPosition(r1, r2, r3, r4)
            java.lang.Object r1 = r0.first
            java.lang.Object r0 = r0.second
            java.lang.Long r0 = (java.lang.Long) r0
            long r2 = r0.longValue()
            boolean r0 = r10.r
            if (r0 == 0) goto L_0x008f
            com.google.android.exoplayer2.source.MaskingMediaSource$a r0 = r10.n
            com.google.android.exoplayer2.source.MaskingMediaSource$a r0 = r0.cloneWithUpdatedTimeline(r13)
            goto L_0x0093
        L_0x008f:
            com.google.android.exoplayer2.source.MaskingMediaSource$a r0 = com.google.android.exoplayer2.source.MaskingMediaSource.a.createWithRealTimeline(r13, r6, r1)
        L_0x0093:
            r10.n = r0
            com.google.android.exoplayer2.source.MaskingMediaPeriod r0 = r10.o
            if (r0 == 0) goto L_0x00b7
            r10.i(r2)
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r0 = r0.c
            java.lang.Object r1 = r0.a
            com.google.android.exoplayer2.source.MaskingMediaSource$a r2 = r10.n
            java.lang.Object r2 = r2.h
            if (r2 == 0) goto L_0x00b2
            java.lang.Object r2 = com.google.android.exoplayer2.source.MaskingMediaSource.a.i
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x00b2
            com.google.android.exoplayer2.source.MaskingMediaSource$a r1 = r10.n
            java.lang.Object r1 = r1.h
        L_0x00b2:
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r0 = r0.copyWithPeriodUid((java.lang.Object) r1)
            goto L_0x00b8
        L_0x00b7:
            r0 = 0
        L_0x00b8:
            r1 = 1
            r10.r = r1
            r10.q = r1
            com.google.android.exoplayer2.source.MaskingMediaSource$a r1 = r10.n
            r10.c(r1)
            if (r0 == 0) goto L_0x00cf
            com.google.android.exoplayer2.source.MaskingMediaPeriod r1 = r10.o
            java.lang.Object r1 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r1)
            com.google.android.exoplayer2.source.MaskingMediaPeriod r1 = (com.google.android.exoplayer2.source.MaskingMediaPeriod) r1
            r1.createPeriod(r0)
        L_0x00cf:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.MaskingMediaSource.f(java.lang.Object, com.google.android.exoplayer2.source.MediaSource, com.google.android.exoplayer2.Timeline):void");
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

    public Timeline getTimeline() {
        return this.n;
    }

    @RequiresNonNull({"unpreparedMaskingMediaPeriod"})
    public final void i(long j2) {
        MaskingMediaPeriod maskingMediaPeriod = this.o;
        int indexOfPeriod = this.n.getIndexOfPeriod(maskingMediaPeriod.c.a);
        if (indexOfPeriod != -1) {
            long j3 = this.n.getPeriod(indexOfPeriod, this.m).h;
            if (j3 != -9223372036854775807L && j2 >= j3) {
                j2 = Math.max(0, j3 - 1);
            }
            maskingMediaPeriod.overridePreparePositionUs(j2);
        }
    }

    public /* bridge */ /* synthetic */ boolean isSingleWindow() {
        return e6.c(this);
    }

    public void maybeThrowSourceInfoRefreshError() {
    }

    public void prepareSourceInternal(@Nullable TransferListener transferListener) {
        super.prepareSourceInternal(transferListener);
        if (!this.k) {
            this.p = true;
            g(null, this.j);
        }
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        ((MaskingMediaPeriod) mediaPeriod).releasePeriod();
        if (mediaPeriod == this.o) {
            this.o = null;
        }
    }

    public void releaseSourceInternal() {
        this.q = false;
        this.p = false;
        super.releaseSourceInternal();
    }

    public MaskingMediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j2) {
        MaskingMediaPeriod maskingMediaPeriod = new MaskingMediaPeriod(mediaPeriodId, allocator, j2);
        MediaSource mediaSource = this.j;
        maskingMediaPeriod.setMediaSource(mediaSource);
        if (this.q) {
            Object obj = mediaPeriodId.a;
            if (this.n.h != null && obj.equals(a.i)) {
                obj = this.n.h;
            }
            maskingMediaPeriod.createPeriod(mediaPeriodId.copyWithPeriodUid(obj));
        } else {
            this.o = maskingMediaPeriod;
            if (!this.p) {
                this.p = true;
                g(null, mediaSource);
            }
        }
        return maskingMediaPeriod;
    }
}
