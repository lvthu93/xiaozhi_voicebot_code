package com.google.android.exoplayer2;

import android.os.Handler;
import android.util.Pair;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.collect.ImmutableList;

public final class d {
    public final Timeline.Period a = new Timeline.Period();
    public final Timeline.Window b = new Timeline.Window();
    @Nullable
    public final AnalyticsCollector c;
    public final Handler d;
    public long e;
    public int f;
    public boolean g;
    @Nullable
    public b6 h;
    @Nullable
    public b6 i;
    @Nullable
    public b6 j;
    public int k;
    @Nullable
    public Object l;
    public long m;

    public d(@Nullable AnalyticsCollector analyticsCollector, Handler handler) {
        this.c = analyticsCollector;
        this.d = handler;
    }

    public static MediaSource.MediaPeriodId h(Timeline timeline, Object obj, long j2, long j3, Timeline.Period period) {
        timeline.getPeriodByUid(obj, period);
        int adGroupIndexForPositionUs = period.getAdGroupIndexForPositionUs(j2);
        if (adGroupIndexForPositionUs == -1) {
            return new MediaSource.MediaPeriodId(obj, j3, period.getAdGroupIndexAfterPositionUs(j2));
        }
        return new MediaSource.MediaPeriodId(obj, adGroupIndexForPositionUs, period.getFirstAdIndexToPlay(adGroupIndexForPositionUs), j3);
    }

    @Nullable
    public final c6 a(Timeline timeline, b6 b6Var, long j2) {
        long j3;
        long j4;
        long j5;
        Timeline timeline2 = timeline;
        c6 c6Var = b6Var.f;
        long rendererOffset = (b6Var.getRendererOffset() + c6Var.e) - j2;
        boolean z = c6Var.f;
        Timeline.Period period = this.a;
        MediaSource.MediaPeriodId mediaPeriodId = c6Var.a;
        if (z) {
            MediaSource.MediaPeriodId mediaPeriodId2 = mediaPeriodId;
            Timeline.Period period2 = period;
            int nextPeriodIndex = timeline.getNextPeriodIndex(timeline2.getIndexOfPeriod(mediaPeriodId.a), this.a, this.b, this.f, this.g);
            if (nextPeriodIndex == -1) {
                return null;
            }
            int i2 = timeline2.getPeriod(nextPeriodIndex, period2, true).g;
            Object obj = period2.f;
            long j6 = mediaPeriodId2.d;
            if (timeline2.getWindow(i2, this.b).s == nextPeriodIndex) {
                Pair<Object, Long> periodPosition = timeline.getPeriodPosition(this.b, this.a, i2, -9223372036854775807L, Math.max(0, rendererOffset));
                if (periodPosition == null) {
                    return null;
                }
                obj = periodPosition.first;
                long longValue = ((Long) periodPosition.second).longValue();
                b6 next = b6Var.getNext();
                if (next == null || !next.b.equals(obj)) {
                    j6 = this.e;
                    this.e = 1 + j6;
                } else {
                    j6 = next.f.a.d;
                }
                j4 = longValue;
                j5 = -9223372036854775807L;
            } else {
                j5 = 0;
                j4 = 0;
            }
            return b(timeline, h(timeline, obj, j4, j6, this.a), j5, j4);
        }
        MediaSource.MediaPeriodId mediaPeriodId3 = mediaPeriodId;
        Timeline.Period period3 = period;
        timeline2.getPeriodByUid(mediaPeriodId3.a, period3);
        if (mediaPeriodId3.isAd()) {
            int i3 = mediaPeriodId3.b;
            int adCountInAdGroup = period3.getAdCountInAdGroup(i3);
            if (adCountInAdGroup == -1) {
                return null;
            }
            int nextAdIndexToPlay = period3.getNextAdIndexToPlay(i3, mediaPeriodId3.c);
            if (nextAdIndexToPlay < adCountInAdGroup) {
                return c(timeline, mediaPeriodId3.a, i3, nextAdIndexToPlay, c6Var.c, mediaPeriodId3.d);
            }
            long j7 = c6Var.c;
            if (j7 == -9223372036854775807L) {
                Timeline.Window window = this.b;
                Timeline.Period period4 = this.a;
                Pair<Object, Long> periodPosition2 = timeline.getPeriodPosition(window, period4, period4.g, -9223372036854775807L, Math.max(0, rendererOffset));
                if (periodPosition2 == null) {
                    return null;
                }
                j3 = ((Long) periodPosition2.second).longValue();
            } else {
                j3 = j7;
            }
            return d(timeline, mediaPeriodId3.a, j3, c6Var.c, mediaPeriodId3.d);
        }
        int i4 = mediaPeriodId3.e;
        int firstAdIndexToPlay = period3.getFirstAdIndexToPlay(i4);
        if (firstAdIndexToPlay == period3.getAdCountInAdGroup(i4)) {
            Object obj2 = mediaPeriodId3.a;
            long j8 = c6Var.e;
            return d(timeline, obj2, j8, j8, mediaPeriodId3.d);
        }
        return c(timeline, mediaPeriodId3.a, mediaPeriodId3.e, firstAdIndexToPlay, c6Var.e, mediaPeriodId3.d);
    }

    @Nullable
    public b6 advancePlayingPeriod() {
        b6 b6Var = this.h;
        if (b6Var == null) {
            return null;
        }
        if (b6Var == this.i) {
            this.i = b6Var.getNext();
        }
        this.h.release();
        int i2 = this.k - 1;
        this.k = i2;
        if (i2 == 0) {
            this.j = null;
            b6 b6Var2 = this.h;
            this.l = b6Var2.b;
            this.m = b6Var2.f.a.d;
        }
        this.h = this.h.getNext();
        g();
        return this.h;
    }

    public b6 advanceReadingPeriod() {
        boolean z;
        b6 b6Var = this.i;
        if (b6Var == null || b6Var.getNext() == null) {
            z = false;
        } else {
            z = true;
        }
        Assertions.checkState(z);
        this.i = this.i.getNext();
        g();
        return this.i;
    }

    @Nullable
    public final c6 b(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId, long j2, long j3) {
        MediaSource.MediaPeriodId mediaPeriodId2 = mediaPeriodId;
        Timeline timeline2 = timeline;
        timeline.getPeriodByUid(mediaPeriodId2.a, this.a);
        if (mediaPeriodId.isAd()) {
            return c(timeline, mediaPeriodId2.a, mediaPeriodId2.b, mediaPeriodId2.c, j2, mediaPeriodId2.d);
        }
        return d(timeline, mediaPeriodId2.a, j3, j2, mediaPeriodId2.d);
    }

    public final c6 c(Timeline timeline, Object obj, int i2, int i3, long j2, long j3) {
        long j4;
        MediaSource.MediaPeriodId mediaPeriodId = new MediaSource.MediaPeriodId(obj, i2, i3, j3);
        Object obj2 = mediaPeriodId.a;
        Timeline.Period period = this.a;
        long adDurationUs = timeline.getPeriodByUid(obj2, period).getAdDurationUs(mediaPeriodId.b, mediaPeriodId.c);
        if (i3 == period.getFirstAdIndexToPlay(i2)) {
            j4 = period.getAdResumePositionUs();
        } else {
            j4 = 0;
        }
        if (adDurationUs != -9223372036854775807L && j4 >= adDurationUs) {
            j4 = Math.max(0, adDurationUs - 1);
        }
        return new c6(mediaPeriodId, j4, j2, -9223372036854775807L, adDurationUs, false, false, false);
    }

    public void clear() {
        if (this.k != 0) {
            b6 b6Var = (b6) Assertions.checkStateNotNull(this.h);
            this.l = b6Var.b;
            this.m = b6Var.f.a.d;
            while (b6Var != null) {
                b6Var.release();
                b6Var = b6Var.getNext();
            }
            this.h = null;
            this.j = null;
            this.i = null;
            this.k = 0;
            g();
        }
    }

    public final c6 d(Timeline timeline, Object obj, long j2, long j3, long j4) {
        boolean z;
        long j5;
        long j6;
        Timeline timeline2 = timeline;
        Object obj2 = obj;
        long j7 = j2;
        Timeline.Period period = this.a;
        timeline2.getPeriodByUid(obj2, period);
        int adGroupIndexAfterPositionUs = period.getAdGroupIndexAfterPositionUs(j7);
        MediaSource.MediaPeriodId mediaPeriodId = new MediaSource.MediaPeriodId(obj2, j4, adGroupIndexAfterPositionUs);
        if (mediaPeriodId.isAd() || mediaPeriodId.e != -1) {
            z = false;
        } else {
            z = true;
        }
        boolean f2 = f(timeline2, mediaPeriodId);
        boolean e2 = e(timeline2, mediaPeriodId, z);
        if (adGroupIndexAfterPositionUs != -1) {
            j5 = period.getAdGroupTimeUs(adGroupIndexAfterPositionUs);
        } else {
            j5 = -9223372036854775807L;
        }
        if (j5 == -9223372036854775807L || j5 == Long.MIN_VALUE) {
            j6 = period.h;
        } else {
            j6 = j5;
        }
        if (j6 != -9223372036854775807L && j7 >= j6) {
            j7 = Math.max(0, j6 - 1);
        }
        return new c6(mediaPeriodId, j7, j3, j5, j6, z, f2, e2);
    }

    public final boolean e(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId, boolean z) {
        int indexOfPeriod = timeline.getIndexOfPeriod(mediaPeriodId.a);
        if (!timeline.getWindow(timeline.getPeriod(indexOfPeriod, this.a).g, this.b).m) {
            if (!timeline.isLastPeriod(indexOfPeriod, this.a, this.b, this.f, this.g) || !z) {
                return false;
            }
            return true;
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0018, code lost:
        if (r3 != -9223372036854775807L) goto L_0x002d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public defpackage.b6 enqueueNextMediaPeriodHolder(com.google.android.exoplayer2.RendererCapabilities[] r12, com.google.android.exoplayer2.trackselection.TrackSelector r13, com.google.android.exoplayer2.upstream.Allocator r14, com.google.android.exoplayer2.MediaSourceList r15, defpackage.c6 r16, com.google.android.exoplayer2.trackselection.TrackSelectorResult r17) {
        /*
            r11 = this;
            r0 = r11
            r8 = r16
            b6 r1 = r0.j
            if (r1 != 0) goto L_0x001e
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r1 = r8.a
            boolean r1 = r1.isAd()
            if (r1 == 0) goto L_0x001b
            r1 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            long r3 = r8.c
            int r5 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r5 == 0) goto L_0x001b
            goto L_0x002d
        L_0x001b:
            r1 = 0
            goto L_0x002c
        L_0x001e:
            long r1 = r1.getRendererOffset()
            b6 r3 = r0.j
            c6 r3 = r3.f
            long r3 = r3.e
            long r1 = r1 + r3
            long r3 = r8.b
            long r1 = r1 - r3
        L_0x002c:
            r3 = r1
        L_0x002d:
            b6 r10 = new b6
            r1 = r10
            r2 = r12
            r5 = r13
            r6 = r14
            r7 = r15
            r8 = r16
            r9 = r17
            r1.<init>(r2, r3, r5, r6, r7, r8, r9)
            b6 r1 = r0.j
            if (r1 == 0) goto L_0x0043
            r1.setNext(r10)
            goto L_0x0047
        L_0x0043:
            r0.h = r10
            r0.i = r10
        L_0x0047:
            r1 = 0
            r0.l = r1
            r0.j = r10
            int r1 = r0.k
            int r1 = r1 + 1
            r0.k = r1
            r11.g()
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.d.enqueueNextMediaPeriodHolder(com.google.android.exoplayer2.RendererCapabilities[], com.google.android.exoplayer2.trackselection.TrackSelector, com.google.android.exoplayer2.upstream.Allocator, com.google.android.exoplayer2.MediaSourceList, c6, com.google.android.exoplayer2.trackselection.TrackSelectorResult):b6");
    }

    public final boolean f(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId) {
        boolean z;
        if (mediaPeriodId.isAd() || mediaPeriodId.e != -1) {
            z = false;
        } else {
            z = true;
        }
        if (!z) {
            return false;
        }
        Object obj = mediaPeriodId.a;
        int i2 = timeline.getPeriodByUid(obj, this.a).g;
        if (timeline.getWindow(i2, this.b).t == timeline.getIndexOfPeriod(obj)) {
            return true;
        }
        return false;
    }

    public final void g() {
        MediaSource.MediaPeriodId mediaPeriodId;
        if (this.c != null) {
            ImmutableList.Builder builder = ImmutableList.builder();
            for (b6 b6Var = this.h; b6Var != null; b6Var = b6Var.getNext()) {
                builder.add((Object) b6Var.f.a);
            }
            b6 b6Var2 = this.i;
            if (b6Var2 == null) {
                mediaPeriodId = null;
            } else {
                mediaPeriodId = b6Var2.f.a;
            }
            this.d.post(new d6(0, this, builder, mediaPeriodId));
        }
    }

    @Nullable
    public b6 getLoadingPeriod() {
        return this.j;
    }

    @Nullable
    public c6 getNextMediaPeriodInfo(long j2, s8 s8Var) {
        b6 b6Var = this.j;
        if (b6Var != null) {
            return a(s8Var.a, b6Var, j2);
        }
        return b(s8Var.a, s8Var.b, s8Var.c, s8Var.s);
    }

    @Nullable
    public b6 getPlayingPeriod() {
        return this.h;
    }

    @Nullable
    public b6 getReadingPeriod() {
        return this.i;
    }

    public c6 getUpdatedMediaPeriodInfo(Timeline timeline, c6 c6Var) {
        boolean z;
        long j2;
        long j3;
        MediaSource.MediaPeriodId mediaPeriodId = c6Var.a;
        if (mediaPeriodId.isAd() || mediaPeriodId.e != -1) {
            z = false;
        } else {
            z = true;
        }
        boolean f2 = f(timeline, mediaPeriodId);
        boolean e2 = e(timeline, mediaPeriodId, z);
        Object obj = c6Var.a.a;
        Timeline.Period period = this.a;
        timeline.getPeriodByUid(obj, period);
        if (mediaPeriodId.isAd()) {
            j3 = period.getAdDurationUs(mediaPeriodId.b, mediaPeriodId.c);
        } else {
            long j4 = c6Var.d;
            if (j4 == -9223372036854775807L || j4 == Long.MIN_VALUE) {
                j3 = period.getDurationUs();
            } else {
                j2 = j4;
                return new c6(mediaPeriodId, c6Var.b, c6Var.c, c6Var.d, j2, z, f2, e2);
            }
        }
        j2 = j3;
        return new c6(mediaPeriodId, c6Var.b, c6Var.c, c6Var.d, j2, z, f2, e2);
    }

    public final boolean i(Timeline timeline) {
        b6 b6Var = this.h;
        if (b6Var == null) {
            return true;
        }
        int indexOfPeriod = timeline.getIndexOfPeriod(b6Var.b);
        while (true) {
            indexOfPeriod = timeline.getNextPeriodIndex(indexOfPeriod, this.a, this.b, this.f, this.g);
            while (b6Var.getNext() != null && !b6Var.f.f) {
                b6Var = b6Var.getNext();
            }
            b6 next = b6Var.getNext();
            if (indexOfPeriod == -1 || next == null || timeline.getIndexOfPeriod(next.b) != indexOfPeriod) {
                boolean removeAfter = removeAfter(b6Var);
                b6Var.f = getUpdatedMediaPeriodInfo(timeline, b6Var.f);
            } else {
                b6Var = next;
            }
        }
        boolean removeAfter2 = removeAfter(b6Var);
        b6Var.f = getUpdatedMediaPeriodInfo(timeline, b6Var.f);
        return !removeAfter2;
    }

    public boolean isLoading(MediaPeriod mediaPeriod) {
        b6 b6Var = this.j;
        return b6Var != null && b6Var.a == mediaPeriod;
    }

    public void reevaluateBuffer(long j2) {
        b6 b6Var = this.j;
        if (b6Var != null) {
            b6Var.reevaluateBuffer(j2);
        }
    }

    public boolean removeAfter(b6 b6Var) {
        boolean z;
        boolean z2 = false;
        if (b6Var != null) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        if (b6Var.equals(this.j)) {
            return false;
        }
        this.j = b6Var;
        while (b6Var.getNext() != null) {
            b6Var = b6Var.getNext();
            if (b6Var == this.i) {
                this.i = this.h;
                z2 = true;
            }
            b6Var.release();
            this.k--;
        }
        this.j.setNext((b6) null);
        g();
        return z2;
    }

    public MediaSource.MediaPeriodId resolveMediaPeriodIdForAds(Timeline timeline, Object obj, long j2) {
        long j3;
        int indexOfPeriod;
        Timeline.Period period = this.a;
        int i2 = timeline.getPeriodByUid(obj, period).g;
        Object obj2 = this.l;
        if (obj2 == null || (indexOfPeriod = timeline.getIndexOfPeriod(obj2)) == -1 || timeline.getPeriod(indexOfPeriod, period).g != i2) {
            b6 b6Var = this.h;
            while (true) {
                if (b6Var == null) {
                    b6 b6Var2 = this.h;
                    while (true) {
                        if (b6Var2 != null) {
                            int indexOfPeriod2 = timeline.getIndexOfPeriod(b6Var2.b);
                            if (indexOfPeriod2 != -1 && timeline.getPeriod(indexOfPeriod2, period).g == i2) {
                                j3 = b6Var2.f.a.d;
                                break;
                            }
                            b6Var2 = b6Var2.getNext();
                        } else {
                            j3 = this.e;
                            this.e = 1 + j3;
                            if (this.h == null) {
                                this.l = obj;
                                this.m = j3;
                            }
                        }
                    }
                } else if (b6Var.b.equals(obj)) {
                    j3 = b6Var.f.a.d;
                    break;
                } else {
                    b6Var = b6Var.getNext();
                }
            }
        } else {
            j3 = this.m;
        }
        return h(timeline, obj, j2, j3, this.a);
    }

    public boolean shouldLoadNextMediaPeriod() {
        b6 b6Var = this.j;
        if (b6Var == null || (!b6Var.f.h && b6Var.isFullyBuffered() && this.j.f.e != -9223372036854775807L && this.k < 100)) {
            return true;
        }
        return false;
    }

    public boolean updateQueuedPeriods(Timeline timeline, long j2, long j3) {
        c6 c6Var;
        boolean z;
        long j4;
        boolean z2;
        boolean z3;
        Timeline timeline2 = timeline;
        b6 b6Var = null;
        for (b6 b6Var2 = this.h; b6Var2 != null; b6Var2 = b6Var2.getNext()) {
            c6 c6Var2 = b6Var2.f;
            if (b6Var == null) {
                c6Var = getUpdatedMediaPeriodInfo(timeline2, c6Var2);
                long j5 = j2;
            } else {
                c6 a2 = a(timeline2, b6Var, j2);
                if (a2 == null) {
                    return !removeAfter(b6Var);
                }
                if (c6Var2.b != a2.b || !c6Var2.a.equals(a2.a)) {
                    z3 = false;
                } else {
                    z3 = true;
                }
                if (!z3) {
                    return !removeAfter(b6Var);
                }
                c6Var = a2;
            }
            b6Var2.f = c6Var.copyWithRequestedContentPositionUs(c6Var2.c);
            long j6 = c6Var2.e;
            long j7 = c6Var.e;
            if (j6 == -9223372036854775807L || j6 == j7) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                if (j7 == -9223372036854775807L) {
                    j4 = Long.MAX_VALUE;
                } else {
                    j4 = b6Var2.toRendererTime(j7);
                }
                if (b6Var2 != this.i || (j3 != Long.MIN_VALUE && j3 < j4)) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                if (removeAfter(b6Var2) || z2) {
                    return false;
                }
                return true;
            }
            b6Var = b6Var2;
        }
        return true;
    }

    public boolean updateRepeatMode(Timeline timeline, int i2) {
        this.f = i2;
        return i(timeline);
    }

    public boolean updateShuffleModeEnabled(Timeline timeline, boolean z) {
        this.g = z;
        return i(timeline);
    }
}
