package com.google.android.exoplayer2.analytics;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import java.util.Collections;
import java.util.List;

public final class PlaybackStats {
    public static final PlaybackStats ao = merge(new PlaybackStats[0]);
    public final int a;
    public final int aa;
    public final long ab;
    public final int ac;
    public final long ad;
    public final long ae;
    public final long af;
    public final long ag;
    public final long ah;
    public final int ai;
    public final int aj;
    public final int ak;
    public final List<EventTimeAndException> al;
    public final List<EventTimeAndException> am;
    public final long[] an;
    public final List<EventTimeAndPlaybackState> b;
    public final List<long[]> c;
    public final long d;
    public final int e;
    public final int f;
    public final int g;
    public final int h;
    public final long i;
    public final int j;
    public final int k;
    public final int l;
    public final int m;
    public final int n;
    public final long o;
    public final int p;
    public final List<EventTimeAndFormat> q;
    public final List<EventTimeAndFormat> r;
    public final long s;
    public final long t;
    public final long u;
    public final long v;
    public final long w;
    public final long x;
    public final int y;
    public final int z;

    public static final class EventTimeAndException {
        public final AnalyticsListener.EventTime a;
        public final Exception b;

        public EventTimeAndException(AnalyticsListener.EventTime eventTime, Exception exc) {
            this.a = eventTime;
            this.b = exc;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || EventTimeAndException.class != obj.getClass()) {
                return false;
            }
            EventTimeAndException eventTimeAndException = (EventTimeAndException) obj;
            if (!this.a.equals(eventTimeAndException.a)) {
                return false;
            }
            return this.b.equals(eventTimeAndException.b);
        }

        public int hashCode() {
            return this.b.hashCode() + (this.a.hashCode() * 31);
        }
    }

    public static final class EventTimeAndFormat {
        public final AnalyticsListener.EventTime a;
        @Nullable
        public final Format b;

        public EventTimeAndFormat(AnalyticsListener.EventTime eventTime, @Nullable Format format) {
            this.a = eventTime;
            this.b = format;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || EventTimeAndFormat.class != obj.getClass()) {
                return false;
            }
            EventTimeAndFormat eventTimeAndFormat = (EventTimeAndFormat) obj;
            if (!this.a.equals(eventTimeAndFormat.a)) {
                return false;
            }
            Format format = eventTimeAndFormat.b;
            Format format2 = this.b;
            if (format2 != null) {
                return format2.equals(format);
            }
            if (format == null) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            int i;
            int hashCode = this.a.hashCode() * 31;
            Format format = this.b;
            if (format != null) {
                i = format.hashCode();
            } else {
                i = 0;
            }
            return hashCode + i;
        }
    }

    public static final class EventTimeAndPlaybackState {
        public final AnalyticsListener.EventTime a;
        public final int b;

        public EventTimeAndPlaybackState(AnalyticsListener.EventTime eventTime, int i) {
            this.a = eventTime;
            this.b = i;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || EventTimeAndPlaybackState.class != obj.getClass()) {
                return false;
            }
            EventTimeAndPlaybackState eventTimeAndPlaybackState = (EventTimeAndPlaybackState) obj;
            if (this.b != eventTimeAndPlaybackState.b) {
                return false;
            }
            return this.a.equals(eventTimeAndPlaybackState.a);
        }

        public int hashCode() {
            return (this.a.hashCode() * 31) + this.b;
        }
    }

    public PlaybackStats(int i2, long[] jArr, List<EventTimeAndPlaybackState> list, List<long[]> list2, long j2, int i3, int i4, int i5, int i6, long j3, int i7, int i8, int i9, int i10, int i11, long j4, int i12, List<EventTimeAndFormat> list3, List<EventTimeAndFormat> list4, long j5, long j6, long j7, long j8, long j9, long j10, int i13, int i14, int i15, long j11, int i16, long j12, long j13, long j14, long j15, long j16, int i17, int i18, int i19, List<EventTimeAndException> list5, List<EventTimeAndException> list6) {
        this.a = i2;
        this.an = jArr;
        this.b = Collections.unmodifiableList(list);
        this.c = Collections.unmodifiableList(list2);
        this.d = j2;
        this.e = i3;
        this.f = i4;
        this.g = i5;
        this.h = i6;
        this.i = j3;
        this.j = i7;
        this.k = i8;
        this.l = i9;
        this.m = i10;
        this.n = i11;
        this.o = j4;
        this.p = i12;
        this.q = Collections.unmodifiableList(list3);
        this.r = Collections.unmodifiableList(list4);
        this.s = j5;
        this.t = j6;
        this.u = j7;
        this.v = j8;
        this.w = j9;
        this.x = j10;
        this.y = i13;
        this.z = i14;
        this.aa = i15;
        this.ab = j11;
        this.ac = i16;
        this.ad = j12;
        this.ae = j13;
        this.af = j14;
        this.ag = j15;
        this.ah = j16;
        this.ai = i17;
        this.aj = i18;
        this.ak = i19;
        this.al = Collections.unmodifiableList(list5);
        this.am = Collections.unmodifiableList(list6);
    }

    public static PlaybackStats merge(PlaybackStats... playbackStatsArr) {
        int i2;
        PlaybackStats[] playbackStatsArr2 = playbackStatsArr;
        int i3 = 16;
        long[] jArr = new long[16];
        int length = playbackStatsArr2.length;
        long j2 = 0;
        long j3 = 0;
        long j4 = 0;
        long j5 = 0;
        long j6 = 0;
        long j7 = 0;
        long j8 = 0;
        long j9 = 0;
        long j10 = 0;
        long j11 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = -1;
        long j12 = -9223372036854775807L;
        long j13 = -9223372036854775807L;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        long j14 = -9223372036854775807L;
        int i11 = 0;
        int i12 = 0;
        int i13 = 0;
        int i14 = 0;
        int i15 = 0;
        int i16 = 0;
        int i17 = 0;
        int i18 = 0;
        long j15 = -1;
        int i19 = 0;
        long j16 = -1;
        int i20 = 0;
        int i21 = 0;
        int i22 = 0;
        while (i4 < length) {
            PlaybackStats playbackStats = playbackStatsArr2[i4];
            i5 += playbackStats.a;
            for (int i23 = 0; i23 < i3; i23++) {
                jArr[i23] = jArr[i23] + playbackStats.an[i23];
            }
            long j17 = playbackStats.d;
            if (j13 == -9223372036854775807L) {
                j13 = j17;
            } else if (j17 != -9223372036854775807L) {
                j13 = Math.min(j13, j17);
            }
            i7 += playbackStats.e;
            i8 += playbackStats.f;
            i9 += playbackStats.g;
            i10 += playbackStats.h;
            long j18 = playbackStats.i;
            if (j14 == -9223372036854775807L) {
                j14 = j18;
            } else if (j18 != -9223372036854775807L) {
                j14 += j18;
            }
            i11 += playbackStats.j;
            i12 += playbackStats.k;
            i13 += playbackStats.l;
            i14 += playbackStats.m;
            i15 += playbackStats.n;
            long j19 = playbackStats.o;
            if (j12 == -9223372036854775807L) {
                j12 = j19;
            } else if (j19 != -9223372036854775807L) {
                j12 = Math.max(j12, j19);
            }
            i16 += playbackStats.p;
            j2 += playbackStats.s;
            j3 += playbackStats.t;
            j4 += playbackStats.u;
            j5 += playbackStats.v;
            j6 += playbackStats.w;
            j7 += playbackStats.x;
            i17 += playbackStats.y;
            i18 += playbackStats.z;
            int i24 = playbackStats.aa;
            if (i6 == -1) {
                i2 = length;
                i6 = i24;
            } else {
                if (i24 != -1) {
                    i6 += i24;
                }
                i2 = length;
            }
            long j20 = playbackStats.ab;
            if (j15 == -1) {
                j15 = j20;
            } else if (j20 != -1) {
                j15 += j20;
            }
            i19 += playbackStats.ac;
            long j21 = playbackStats.ad;
            if (j16 == -1) {
                j16 = j21;
            } else if (j21 != -1) {
                j16 += j21;
            }
            j8 += playbackStats.ae;
            j9 += playbackStats.af;
            j10 += playbackStats.ag;
            j11 += playbackStats.ah;
            i20 += playbackStats.ai;
            i21 += playbackStats.aj;
            i22 += playbackStats.ak;
            i4++;
            length = i2;
            i3 = 16;
        }
        return new PlaybackStats(i5, jArr, Collections.emptyList(), Collections.emptyList(), j13, i7, i8, i9, i10, j14, i11, i12, i13, i14, i15, j12, i16, Collections.emptyList(), Collections.emptyList(), j2, j3, j4, j5, j6, j7, i17, i18, i6, j15, i19, j16, j8, j9, j10, j11, i20, i21, i22, Collections.emptyList(), Collections.emptyList());
    }

    public float getAbandonedBeforeReadyRatio() {
        int i2 = this.a;
        int i3 = this.e;
        int i4 = this.f - (i2 - i3);
        if (i3 == 0) {
            return 0.0f;
        }
        return ((float) i4) / ((float) i3);
    }

    public float getAudioUnderrunRate() {
        long totalPlayTimeMs = getTotalPlayTimeMs();
        if (totalPlayTimeMs == 0) {
            return 0.0f;
        }
        return (((float) this.ah) * 1000.0f) / ((float) totalPlayTimeMs);
    }

    public float getDroppedFramesRate() {
        long totalPlayTimeMs = getTotalPlayTimeMs();
        if (totalPlayTimeMs == 0) {
            return 0.0f;
        }
        return (((float) this.ag) * 1000.0f) / ((float) totalPlayTimeMs);
    }

    public float getEndedRatio() {
        int i2 = this.e;
        if (i2 == 0) {
            return 0.0f;
        }
        return ((float) this.g) / ((float) i2);
    }

    public float getFatalErrorRate() {
        long totalPlayTimeMs = getTotalPlayTimeMs();
        if (totalPlayTimeMs == 0) {
            return 0.0f;
        }
        return (((float) this.aj) * 1000.0f) / ((float) totalPlayTimeMs);
    }

    public float getFatalErrorRatio() {
        int i2 = this.e;
        if (i2 == 0) {
            return 0.0f;
        }
        return ((float) this.ai) / ((float) i2);
    }

    public float getJoinTimeRatio() {
        long totalPlayAndWaitTimeMs = getTotalPlayAndWaitTimeMs();
        if (totalPlayAndWaitTimeMs == 0) {
            return 0.0f;
        }
        return ((float) getTotalJoinTimeMs()) / ((float) totalPlayAndWaitTimeMs);
    }

    public int getMeanAudioFormatBitrate() {
        long j2 = this.w;
        if (j2 == 0) {
            return -1;
        }
        return (int) (this.x / j2);
    }

    public int getMeanBandwidth() {
        long j2 = this.ae;
        if (j2 == 0) {
            return -1;
        }
        return (int) ((this.af * 8000) / j2);
    }

    public long getMeanElapsedTimeMs() {
        int i2 = this.a;
        if (i2 == 0) {
            return -9223372036854775807L;
        }
        return getTotalElapsedTimeMs() / ((long) i2);
    }

    public int getMeanInitialAudioFormatBitrate() {
        int i2 = this.ac;
        if (i2 == 0) {
            return -1;
        }
        return (int) (this.ad / ((long) i2));
    }

    public int getMeanInitialVideoFormatBitrate() {
        int i2 = this.z;
        if (i2 == 0) {
            return -1;
        }
        return (int) (this.ab / ((long) i2));
    }

    public int getMeanInitialVideoFormatHeight() {
        int i2 = this.y;
        if (i2 == 0) {
            return -1;
        }
        return this.aa / i2;
    }

    public long getMeanJoinTimeMs() {
        int i2 = this.j;
        if (i2 == 0) {
            return -9223372036854775807L;
        }
        return this.i / ((long) i2);
    }

    public float getMeanNonFatalErrorCount() {
        int i2 = this.e;
        if (i2 == 0) {
            return 0.0f;
        }
        return ((float) this.ak) / ((float) i2);
    }

    public float getMeanPauseBufferCount() {
        int i2 = this.e;
        if (i2 == 0) {
            return 0.0f;
        }
        return ((float) this.l) / ((float) i2);
    }

    public float getMeanPauseCount() {
        int i2 = this.e;
        if (i2 == 0) {
            return 0.0f;
        }
        return ((float) this.k) / ((float) i2);
    }

    public long getMeanPausedTimeMs() {
        int i2 = this.e;
        if (i2 == 0) {
            return -9223372036854775807L;
        }
        return getTotalPausedTimeMs() / ((long) i2);
    }

    public long getMeanPlayAndWaitTimeMs() {
        int i2 = this.e;
        if (i2 == 0) {
            return -9223372036854775807L;
        }
        return getTotalPlayAndWaitTimeMs() / ((long) i2);
    }

    public long getMeanPlayTimeMs() {
        int i2 = this.e;
        if (i2 == 0) {
            return -9223372036854775807L;
        }
        return getTotalPlayTimeMs() / ((long) i2);
    }

    public float getMeanRebufferCount() {
        int i2 = this.e;
        if (i2 == 0) {
            return 0.0f;
        }
        return ((float) this.n) / ((float) i2);
    }

    public long getMeanRebufferTimeMs() {
        int i2 = this.e;
        if (i2 == 0) {
            return -9223372036854775807L;
        }
        return getTotalRebufferTimeMs() / ((long) i2);
    }

    public float getMeanSeekCount() {
        int i2 = this.e;
        if (i2 == 0) {
            return 0.0f;
        }
        return ((float) this.m) / ((float) i2);
    }

    public long getMeanSeekTimeMs() {
        int i2 = this.e;
        if (i2 == 0) {
            return -9223372036854775807L;
        }
        return getTotalSeekTimeMs() / ((long) i2);
    }

    public long getMeanSingleRebufferTimeMs() {
        int i2 = this.n;
        if (i2 == 0) {
            return -9223372036854775807L;
        }
        return (getPlaybackStateDurationMs(7) + getPlaybackStateDurationMs(6)) / ((long) i2);
    }

    public long getMeanSingleSeekTimeMs() {
        int i2 = this.m;
        if (i2 == 0) {
            return -9223372036854775807L;
        }
        return getTotalSeekTimeMs() / ((long) i2);
    }

    public float getMeanTimeBetweenFatalErrors() {
        return 1.0f / getFatalErrorRate();
    }

    public float getMeanTimeBetweenNonFatalErrors() {
        return 1.0f / getNonFatalErrorRate();
    }

    public float getMeanTimeBetweenRebuffers() {
        return 1.0f / getRebufferRate();
    }

    public int getMeanVideoFormatBitrate() {
        long j2 = this.u;
        if (j2 == 0) {
            return -1;
        }
        return (int) (this.v / j2);
    }

    public int getMeanVideoFormatHeight() {
        long j2 = this.s;
        if (j2 == 0) {
            return -1;
        }
        return (int) (this.t / j2);
    }

    public long getMeanWaitTimeMs() {
        int i2 = this.e;
        if (i2 == 0) {
            return -9223372036854775807L;
        }
        return getTotalWaitTimeMs() / ((long) i2);
    }

    public long getMediaTimeMsAtRealtimeMs(long j2) {
        List<long[]> list = this.c;
        if (list.isEmpty()) {
            return -9223372036854775807L;
        }
        int i2 = 0;
        while (i2 < list.size() && list.get(i2)[0] <= j2) {
            i2++;
        }
        if (i2 == 0) {
            return list.get(0)[1];
        }
        if (i2 == list.size()) {
            return list.get(list.size() - 1)[1];
        }
        int i3 = i2 - 1;
        long j3 = list.get(i3)[0];
        long j4 = list.get(i3)[1];
        long j5 = list.get(i2)[0];
        long j6 = list.get(i2)[1];
        long j7 = j5 - j3;
        if (j7 == 0) {
            return j4;
        }
        return j4 + ((long) (((float) (j6 - j4)) * (((float) (j2 - j3)) / ((float) j7))));
    }

    public float getNonFatalErrorRate() {
        long totalPlayTimeMs = getTotalPlayTimeMs();
        if (totalPlayTimeMs == 0) {
            return 0.0f;
        }
        return (((float) this.ak) * 1000.0f) / ((float) totalPlayTimeMs);
    }

    public int getPlaybackStateAtTime(long j2) {
        int i2 = 0;
        for (EventTimeAndPlaybackState next : this.b) {
            if (next.a.a > j2) {
                break;
            }
            i2 = next.b;
        }
        return i2;
    }

    public long getPlaybackStateDurationMs(int i2) {
        return this.an[i2];
    }

    public float getRebufferRate() {
        long totalPlayTimeMs = getTotalPlayTimeMs();
        if (totalPlayTimeMs == 0) {
            return 0.0f;
        }
        return (((float) this.n) * 1000.0f) / ((float) totalPlayTimeMs);
    }

    public float getRebufferTimeRatio() {
        long totalPlayAndWaitTimeMs = getTotalPlayAndWaitTimeMs();
        if (totalPlayAndWaitTimeMs == 0) {
            return 0.0f;
        }
        return ((float) getTotalRebufferTimeMs()) / ((float) totalPlayAndWaitTimeMs);
    }

    public float getSeekTimeRatio() {
        long totalPlayAndWaitTimeMs = getTotalPlayAndWaitTimeMs();
        if (totalPlayAndWaitTimeMs == 0) {
            return 0.0f;
        }
        return ((float) getTotalSeekTimeMs()) / ((float) totalPlayAndWaitTimeMs);
    }

    public long getTotalElapsedTimeMs() {
        long j2 = 0;
        for (int i2 = 0; i2 < 16; i2++) {
            j2 += this.an[i2];
        }
        return j2;
    }

    public long getTotalJoinTimeMs() {
        return getPlaybackStateDurationMs(2);
    }

    public long getTotalPausedTimeMs() {
        return getPlaybackStateDurationMs(7) + getPlaybackStateDurationMs(4);
    }

    public long getTotalPlayAndWaitTimeMs() {
        return getTotalWaitTimeMs() + getTotalPlayTimeMs();
    }

    public long getTotalPlayTimeMs() {
        return getPlaybackStateDurationMs(3);
    }

    public long getTotalRebufferTimeMs() {
        return getPlaybackStateDurationMs(6);
    }

    public long getTotalSeekTimeMs() {
        return getPlaybackStateDurationMs(5);
    }

    public long getTotalWaitTimeMs() {
        return getPlaybackStateDurationMs(5) + getPlaybackStateDurationMs(6) + getPlaybackStateDurationMs(2);
    }

    public float getWaitTimeRatio() {
        long totalPlayAndWaitTimeMs = getTotalPlayAndWaitTimeMs();
        if (totalPlayAndWaitTimeMs == 0) {
            return 0.0f;
        }
        return ((float) getTotalWaitTimeMs()) / ((float) totalPlayAndWaitTimeMs);
    }
}
