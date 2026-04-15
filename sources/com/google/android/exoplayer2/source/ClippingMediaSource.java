package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

public final class ClippingMediaSource extends CompositeMediaSource<Void> {
    public final MediaSource j;
    public final long k;
    public final long l;
    public final boolean m;
    public final boolean n;
    public final boolean o;
    public final ArrayList<ClippingMediaPeriod> p;
    public final Timeline.Window q;
    @Nullable
    public a r;
    @Nullable
    public IllegalClippingException s;
    public long t;
    public long u;

    public static final class IllegalClippingException extends IOException {

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        public @interface Reason {
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public IllegalClippingException(int r3) {
            /*
                r2 = this;
                if (r3 == 0) goto L_0x0011
                r0 = 1
                if (r3 == r0) goto L_0x000e
                r0 = 2
                if (r3 == r0) goto L_0x000b
                java.lang.String r3 = "unknown"
                goto L_0x0013
            L_0x000b:
                java.lang.String r3 = "start exceeds end"
                goto L_0x0013
            L_0x000e:
                java.lang.String r3 = "not seekable to start"
                goto L_0x0013
            L_0x0011:
                java.lang.String r3 = "invalid period count"
            L_0x0013:
                int r0 = r3.length()
                java.lang.String r1 = "Illegal clipping: "
                if (r0 == 0) goto L_0x0020
                java.lang.String r3 = r1.concat(r3)
                goto L_0x0025
            L_0x0020:
                java.lang.String r3 = new java.lang.String
                r3.<init>(r1)
            L_0x0025:
                r2.<init>(r3)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.ClippingMediaSource.IllegalClippingException.<init>(int):void");
        }
    }

    public static final class a extends ForwardingTimeline {
        public final long g;
        public final long h;
        public final long i;
        public final boolean j;

        public a(Timeline timeline, long j2, long j3) throws IllegalClippingException {
            super(timeline);
            long j4;
            long j5;
            boolean z = false;
            if (timeline.getPeriodCount() == 1) {
                Timeline.Window window = timeline.getWindow(0, new Timeline.Window());
                long max = Math.max(0, j2);
                if (window.p || max == 0 || window.l) {
                    if (j3 == Long.MIN_VALUE) {
                        j4 = window.r;
                    } else {
                        j4 = Math.max(0, j3);
                    }
                    long j6 = window.r;
                    if (j6 != -9223372036854775807L) {
                        j4 = j4 > j6 ? j6 : j4;
                        if (max > j4) {
                            throw new IllegalClippingException(2);
                        }
                    }
                    this.g = max;
                    this.h = j4;
                    int i2 = (j4 > -9223372036854775807L ? 1 : (j4 == -9223372036854775807L ? 0 : -1));
                    if (i2 == 0) {
                        j5 = -9223372036854775807L;
                    } else {
                        j5 = j4 - max;
                    }
                    this.i = j5;
                    if (window.m && (i2 == 0 || (j6 != -9223372036854775807L && j4 == j6))) {
                        z = true;
                    }
                    this.j = z;
                    return;
                }
                throw new IllegalClippingException(1);
            }
            throw new IllegalClippingException(0);
        }

        public Timeline.Period getPeriod(int i2, Timeline.Period period, boolean z) {
            long j2;
            this.f.getPeriod(0, period, z);
            long positionInWindowUs = period.getPositionInWindowUs() - this.g;
            long j3 = this.i;
            if (j3 == -9223372036854775807L) {
                j2 = -9223372036854775807L;
            } else {
                j2 = j3 - positionInWindowUs;
            }
            return period.set(period.c, period.f, 0, j2, positionInWindowUs);
        }

        public Timeline.Window getWindow(int i2, Timeline.Window window, long j2) {
            this.f.getWindow(0, window, 0);
            long j3 = window.u;
            long j4 = this.g;
            window.u = j3 + j4;
            window.r = this.i;
            window.m = this.j;
            long j5 = window.q;
            if (j5 != -9223372036854775807L) {
                long max = Math.max(j5, j4);
                window.q = max;
                long j6 = this.h;
                if (j6 != -9223372036854775807L) {
                    max = Math.min(max, j6);
                }
                window.q = max - j4;
            }
            long usToMs = C.usToMs(j4);
            long j7 = window.i;
            if (j7 != -9223372036854775807L) {
                window.i = j7 + usToMs;
            }
            long j8 = window.j;
            if (j8 != -9223372036854775807L) {
                window.j = j8 + usToMs;
            }
            return window;
        }
    }

    public ClippingMediaSource(MediaSource mediaSource, long j2, long j3) {
        this(mediaSource, j2, j3, true, false, false);
    }

    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j2) {
        ClippingMediaPeriod clippingMediaPeriod = new ClippingMediaPeriod(this.j.createPeriod(mediaPeriodId, allocator, j2), this.m, this.t, this.u);
        this.p.add(clippingMediaPeriod);
        return clippingMediaPeriod;
    }

    public final void f(Object obj, MediaSource mediaSource, Timeline timeline) {
        Void voidR = (Void) obj;
        if (this.s == null) {
            i(timeline);
        }
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

    public final void i(Timeline timeline) {
        long j2;
        long j3;
        long j4;
        Timeline.Window window = this.q;
        timeline.getWindow(0, window);
        long positionInFirstPeriodUs = window.getPositionInFirstPeriodUs();
        a aVar = this.r;
        ArrayList<ClippingMediaPeriod> arrayList = this.p;
        long j5 = this.l;
        long j6 = Long.MIN_VALUE;
        if (aVar == null || arrayList.isEmpty() || this.n) {
            boolean z = this.o;
            long j7 = this.k;
            if (z) {
                long defaultPositionUs = window.getDefaultPositionUs();
                j7 += defaultPositionUs;
                j4 = defaultPositionUs + j5;
            } else {
                j4 = j5;
            }
            this.t = positionInFirstPeriodUs + j7;
            if (j5 != Long.MIN_VALUE) {
                j6 = positionInFirstPeriodUs + j4;
            }
            this.u = j6;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                arrayList.get(i).updateClipping(this.t, this.u);
            }
            j2 = j4;
            j3 = j7;
        } else {
            long j8 = this.t - positionInFirstPeriodUs;
            if (j5 != Long.MIN_VALUE) {
                j6 = this.u - positionInFirstPeriodUs;
            }
            j3 = j8;
            j2 = j6;
        }
        try {
            a aVar2 = new a(timeline, j3, j2);
            this.r = aVar2;
            c(aVar2);
        } catch (IllegalClippingException e) {
            this.s = e;
        }
    }

    public /* bridge */ /* synthetic */ boolean isSingleWindow() {
        return e6.c(this);
    }

    public void maybeThrowSourceInfoRefreshError() throws IOException {
        IllegalClippingException illegalClippingException = this.s;
        if (illegalClippingException == null) {
            super.maybeThrowSourceInfoRefreshError();
            return;
        }
        throw illegalClippingException;
    }

    public final void prepareSourceInternal(@Nullable TransferListener transferListener) {
        super.prepareSourceInternal(transferListener);
        g(null, this.j);
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        ArrayList<ClippingMediaPeriod> arrayList = this.p;
        Assertions.checkState(arrayList.remove(mediaPeriod));
        this.j.releasePeriod(((ClippingMediaPeriod) mediaPeriod).c);
        if (arrayList.isEmpty() && !this.n) {
            i(((a) Assertions.checkNotNull(this.r)).f);
        }
    }

    public final void releaseSourceInternal() {
        super.releaseSourceInternal();
        this.s = null;
        this.r = null;
    }

    public ClippingMediaSource(MediaSource mediaSource, long j2) {
        this(mediaSource, 0, j2, true, false, true);
    }

    public ClippingMediaSource(MediaSource mediaSource, long j2, long j3, boolean z, boolean z2, boolean z3) {
        Assertions.checkArgument(j2 >= 0);
        this.j = (MediaSource) Assertions.checkNotNull(mediaSource);
        this.k = j2;
        this.l = j3;
        this.m = z;
        this.n = z2;
        this.o = z3;
        this.p = new ArrayList<>();
        this.q = new Timeline.Window();
    }
}
