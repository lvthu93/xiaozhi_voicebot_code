package com.google.android.exoplayer2.source;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.util.Assertions;

public final class SinglePeriodTimeline extends Timeline {
    public static final Object s = new Object();
    public static final MediaItem t = new MediaItem.Builder().setMediaId("SinglePeriodTimeline").setUri(Uri.EMPTY).build();
    public final long f;
    public final long g;
    public final long h;
    public final long i;
    public final long j;
    public final long k;
    public final long l;
    public final boolean m;
    public final boolean n;
    public final boolean o;
    @Nullable
    public final Object p;
    @Nullable
    public final MediaItem q;
    @Nullable
    public final MediaItem.LiveConfiguration r;

    @Deprecated
    public SinglePeriodTimeline(long j2, boolean z, boolean z2, boolean z3, @Nullable Object obj, @Nullable Object obj2) {
        this(j2, j2, 0, 0, z, z2, z3, obj, obj2);
    }

    public int getIndexOfPeriod(Object obj) {
        return s.equals(obj) ? 0 : -1;
    }

    public Timeline.Period getPeriod(int i2, Timeline.Period period, boolean z) {
        Object obj;
        Assertions.checkIndex(i2, 0, 1);
        if (z) {
            obj = s;
        } else {
            obj = null;
        }
        return period.set((Object) null, obj, 0, this.i, -this.k);
    }

    public int getPeriodCount() {
        return 1;
    }

    public Object getUidOfPeriod(int i2) {
        Assertions.checkIndex(i2, 0, 1);
        return s;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002b, code lost:
        if (r1 > r5) goto L_0x0024;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.exoplayer2.Timeline.Window getWindow(int r25, com.google.android.exoplayer2.Timeline.Window r26, long r27) {
        /*
            r24 = this;
            r0 = r24
            r1 = 0
            r2 = 1
            r3 = r25
            com.google.android.exoplayer2.util.Assertions.checkIndex(r3, r1, r2)
            boolean r14 = r0.n
            long r1 = r0.l
            if (r14 == 0) goto L_0x002e
            boolean r3 = r0.o
            if (r3 != 0) goto L_0x002e
            r3 = 0
            int r5 = (r27 > r3 ? 1 : (r27 == r3 ? 0 : -1))
            if (r5 == 0) goto L_0x002e
            r3 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            long r5 = r0.j
            int r7 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r7 != 0) goto L_0x0027
        L_0x0024:
            r16 = r3
            goto L_0x0030
        L_0x0027:
            long r1 = r1 + r27
            int r7 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r7 <= 0) goto L_0x002e
            goto L_0x0024
        L_0x002e:
            r16 = r1
        L_0x0030:
            java.lang.Object r4 = com.google.android.exoplayer2.Timeline.Window.v
            com.google.android.exoplayer2.MediaItem r5 = r0.q
            java.lang.Object r6 = r0.p
            long r7 = r0.f
            long r9 = r0.g
            long r11 = r0.h
            boolean r13 = r0.m
            com.google.android.exoplayer2.MediaItem$LiveConfiguration r15 = r0.r
            long r1 = r0.j
            r18 = r1
            r20 = 0
            r21 = 0
            long r1 = r0.k
            r22 = r1
            r3 = r26
            com.google.android.exoplayer2.Timeline$Window r1 = r3.set(r4, r5, r6, r7, r9, r11, r13, r14, r15, r16, r18, r20, r21, r22)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.SinglePeriodTimeline.getWindow(int, com.google.android.exoplayer2.Timeline$Window, long):com.google.android.exoplayer2.Timeline$Window");
    }

    public int getWindowCount() {
        return 1;
    }

    public SinglePeriodTimeline(long j2, boolean z, boolean z2, boolean z3, @Nullable Object obj, MediaItem mediaItem) {
        this(j2, j2, 0, 0, z, z2, z3, obj, mediaItem);
    }

    @Deprecated
    public SinglePeriodTimeline(long j2, long j3, long j4, long j5, boolean z, boolean z2, boolean z3, @Nullable Object obj, @Nullable Object obj2) {
        this(-9223372036854775807L, -9223372036854775807L, -9223372036854775807L, j2, j3, j4, j5, z, z2, z3, obj, obj2);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public SinglePeriodTimeline(long j2, long j3, long j4, long j5, boolean z, boolean z2, boolean z3, @Nullable Object obj, MediaItem mediaItem) {
        this(-9223372036854775807L, -9223372036854775807L, -9223372036854775807L, j2, j3, j4, j5, z, z2, false, obj, mediaItem, z3 ? mediaItem.g : null);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public SinglePeriodTimeline(long r22, long r24, long r26, long r28, long r30, long r32, long r34, boolean r36, boolean r37, boolean r38, @androidx.annotation.Nullable java.lang.Object r39, @androidx.annotation.Nullable java.lang.Object r40) {
        /*
            r21 = this;
            r17 = 0
            com.google.android.exoplayer2.MediaItem r0 = t
            com.google.android.exoplayer2.MediaItem$Builder r1 = r0.buildUpon()
            r2 = r40
            com.google.android.exoplayer2.MediaItem$Builder r1 = r1.setTag(r2)
            com.google.android.exoplayer2.MediaItem r19 = r1.build()
            if (r38 == 0) goto L_0x0017
            com.google.android.exoplayer2.MediaItem$LiveConfiguration r0 = r0.g
            goto L_0x0018
        L_0x0017:
            r0 = 0
        L_0x0018:
            r20 = r0
            r0 = r21
            r1 = r22
            r3 = r24
            r5 = r26
            r7 = r28
            r9 = r30
            r11 = r32
            r13 = r34
            r15 = r36
            r16 = r37
            r18 = r39
            r0.<init>(r1, r3, r5, r7, r9, r11, r13, r15, r16, r17, r18, r19, r20)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.SinglePeriodTimeline.<init>(long, long, long, long, long, long, long, boolean, boolean, boolean, java.lang.Object, java.lang.Object):void");
    }

    @Deprecated
    public SinglePeriodTimeline(long j2, long j3, long j4, long j5, long j6, long j7, long j8, boolean z, boolean z2, @Nullable Object obj, MediaItem mediaItem, @Nullable MediaItem.LiveConfiguration liveConfiguration) {
        this(j2, j3, j4, j5, j6, j7, j8, z, z2, false, obj, mediaItem, liveConfiguration);
    }

    public SinglePeriodTimeline(long j2, long j3, long j4, long j5, long j6, long j7, long j8, boolean z, boolean z2, boolean z3, @Nullable Object obj, MediaItem mediaItem, @Nullable MediaItem.LiveConfiguration liveConfiguration) {
        this.f = j2;
        this.g = j3;
        this.h = j4;
        this.i = j5;
        this.j = j6;
        this.k = j7;
        this.l = j8;
        this.m = z;
        this.n = z2;
        this.o = z3;
        this.p = obj;
        this.q = (MediaItem) Assertions.checkNotNull(mediaItem);
        this.r = liveConfiguration;
    }
}
