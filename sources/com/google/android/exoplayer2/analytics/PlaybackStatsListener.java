package com.google.android.exoplayer2.analytics;

import android.os.SystemClock;
import android.util.Pair;
import androidx.annotation.Nullable;
import androidx.core.view.PointerIconCompat;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.analytics.PlaybackSessionManager;
import com.google.android.exoplayer2.analytics.PlaybackStats;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoSize;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public final class PlaybackStatsListener implements AnalyticsListener, PlaybackSessionManager.Listener {
    public final DefaultPlaybackSessionManager a;
    public final HashMap b = new HashMap();
    public final HashMap c = new HashMap();
    @Nullable
    public final Callback d;
    public final boolean e;
    public final Timeline.Period f = new Timeline.Period();
    public PlaybackStats g = PlaybackStats.ao;
    @Nullable
    public String h;
    public long i;
    public int j;
    public int k;
    @Nullable
    public Exception l;
    public long m;
    public long n;
    @Nullable
    public Format o;
    @Nullable
    public Format p;
    public VideoSize q = VideoSize.i;

    public interface Callback {
        void onPlaybackStatsReady(AnalyticsListener.EventTime eventTime, PlaybackStats playbackStats);
    }

    public static final class a {
        public final boolean a;
        public long aa;
        public long ab;
        public long ac;
        public long ad;
        public long ae;
        public int af;
        public int ag;
        public int ah;
        public long ai;
        public boolean aj;
        public boolean ak;
        public boolean al;
        public boolean am;
        public boolean an;
        public long ao;
        @Nullable
        public Format ap;
        @Nullable
        public Format aq;
        public long ar;
        public long as;
        public float at;
        public final long[] b = new long[16];
        public final List<PlaybackStats.EventTimeAndPlaybackState> c;
        public final List<long[]> d;
        public final List<PlaybackStats.EventTimeAndFormat> e;
        public final List<PlaybackStats.EventTimeAndFormat> f;
        public final List<PlaybackStats.EventTimeAndException> g;
        public final List<PlaybackStats.EventTimeAndException> h;
        public final boolean i;
        public long j;
        public boolean k;
        public boolean l;
        public boolean m;
        public int n;
        public int o;
        public int p;
        public int q;
        public long r;
        public int s;
        public long t;
        public long u;
        public long v;
        public long w;
        public long x;
        public long y;
        public long z;

        public a(boolean z2, AnalyticsListener.EventTime eventTime) {
            List<PlaybackStats.EventTimeAndPlaybackState> list;
            List<long[]> list2;
            List<PlaybackStats.EventTimeAndFormat> list3;
            List<PlaybackStats.EventTimeAndFormat> list4;
            List<PlaybackStats.EventTimeAndException> list5;
            List<PlaybackStats.EventTimeAndException> list6;
            this.a = z2;
            if (z2) {
                list = new ArrayList<>();
            } else {
                list = Collections.emptyList();
            }
            this.c = list;
            if (z2) {
                list2 = new ArrayList<>();
            } else {
                list2 = Collections.emptyList();
            }
            this.d = list2;
            if (z2) {
                list3 = new ArrayList<>();
            } else {
                list3 = Collections.emptyList();
            }
            this.e = list3;
            if (z2) {
                list4 = new ArrayList<>();
            } else {
                list4 = Collections.emptyList();
            }
            this.f = list4;
            if (z2) {
                list5 = new ArrayList<>();
            } else {
                list5 = Collections.emptyList();
            }
            this.g = list5;
            if (z2) {
                list6 = new ArrayList<>();
            } else {
                list6 = Collections.emptyList();
            }
            this.h = list6;
            boolean z3 = false;
            this.ah = 0;
            this.ai = eventTime.a;
            this.j = -9223372036854775807L;
            this.r = -9223372036854775807L;
            MediaSource.MediaPeriodId mediaPeriodId = eventTime.d;
            if (mediaPeriodId != null && mediaPeriodId.isAd()) {
                z3 = true;
            }
            this.i = z3;
            this.u = -1;
            this.t = -1;
            this.s = -1;
            this.at = 1.0f;
        }

        public static boolean b(int i2) {
            return i2 == 6 || i2 == 7 || i2 == 10;
        }

        public final long[] a(long j2) {
            List<long[]> list = this.d;
            long[] jArr = list.get(list.size() - 1);
            return new long[]{j2, jArr[1] + ((long) (((float) (j2 - jArr[0])) * this.at))};
        }

        public PlaybackStats build(boolean z2) {
            ArrayList arrayList;
            long[] jArr;
            int i2;
            long j2;
            int i3;
            ArrayList arrayList2;
            ArrayList arrayList3;
            ArrayList arrayList4;
            int i4;
            int i5;
            int i6;
            int i7;
            long[] jArr2 = this.b;
            List<long[]> list = this.d;
            if (!z2) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                long[] copyOf = Arrays.copyOf(jArr2, 16);
                long max = Math.max(0, elapsedRealtime - this.ai);
                int i8 = this.ah;
                copyOf[i8] = copyOf[i8] + max;
                f(elapsedRealtime);
                d(elapsedRealtime);
                c(elapsedRealtime);
                ArrayList arrayList5 = new ArrayList(list);
                if (this.a && this.ah == 3) {
                    arrayList5.add(a(elapsedRealtime));
                }
                jArr = copyOf;
                arrayList = arrayList5;
            } else {
                jArr = jArr2;
                arrayList = list;
            }
            if (this.m || !this.k) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            if (i2 != 0) {
                j2 = -9223372036854775807L;
            } else {
                j2 = jArr[2];
            }
            long j3 = j2;
            if (jArr[1] > 0) {
                i3 = 1;
            } else {
                i3 = 0;
            }
            List<PlaybackStats.EventTimeAndFormat> list2 = this.e;
            if (z2) {
                arrayList2 = list2;
            } else {
                arrayList2 = new ArrayList(list2);
            }
            List<PlaybackStats.EventTimeAndFormat> list3 = this.f;
            if (z2) {
                arrayList3 = list3;
            } else {
                arrayList3 = new ArrayList(list3);
            }
            List<PlaybackStats.EventTimeAndPlaybackState> list4 = this.c;
            if (z2) {
                arrayList4 = list4;
            } else {
                arrayList4 = new ArrayList(list4);
            }
            long j4 = this.j;
            boolean z3 = this.ak;
            boolean z4 = !this.k;
            boolean z5 = this.l;
            int i9 = i2 ^ 1;
            int i10 = this.n;
            int i11 = this.o;
            int i12 = this.p;
            int i13 = this.q;
            long j5 = this.r;
            boolean z6 = this.i;
            long[] jArr3 = jArr;
            long j6 = this.v;
            long j7 = this.w;
            long j8 = this.x;
            long j9 = this.y;
            long j10 = this.z;
            long j11 = this.aa;
            int i14 = this.s;
            int i15 = i14;
            if (i14 == -1) {
                i4 = 0;
            } else {
                i4 = 1;
            }
            long j12 = this.t;
            long j13 = j12;
            if (j12 == -1) {
                i5 = 0;
            } else {
                i5 = 1;
            }
            long j14 = this.u;
            long j15 = j14;
            if (j14 == -1) {
                i6 = 0;
            } else {
                i6 = 1;
            }
            long j16 = this.ab;
            long j17 = this.ac;
            long j18 = this.ad;
            long j19 = this.ae;
            int i16 = this.af;
            if (i16 > 0) {
                i7 = 1;
            } else {
                i7 = 0;
            }
            int i17 = this.ag;
            List<PlaybackStats.EventTimeAndException> list5 = this.g;
            List<PlaybackStats.EventTimeAndException> list6 = this.h;
            long j20 = j18;
            long j21 = j19;
            long j22 = j16;
            long j23 = j17;
            long j24 = j15;
            long j25 = j13;
            int i18 = i15;
            long j26 = j11;
            boolean z7 = z6;
            long j27 = j10;
            long j28 = j9;
            long j29 = j8;
            long j30 = j7;
            long j31 = j6;
            long[] jArr4 = jArr3;
            int i19 = i10;
            int i20 = i11;
            int i21 = i12;
            int i22 = i13;
            long j32 = j5;
            long j33 = j25;
            long j34 = j24;
            long j35 = j22;
            long j36 = j23;
            long j37 = j20;
            long j38 = j21;
            return new PlaybackStats(1, jArr4, arrayList4, arrayList, j4, z3 ? 1 : 0, z4 ? 1 : 0, z5 ? 1 : 0, i3, j3, i9, i19, i20, i21, i22, j32, z7 ? 1 : 0, arrayList2, arrayList3, j31, j30, j29, j28, j27, j26, i4, i5, i18, j33, i6, j34, j35, j36, j37, j38, i7, i16, i17, list5, list6);
        }

        public final void c(long j2) {
            Format format;
            int i2;
            if (!(this.ah != 3 || (format = this.aq) == null || (i2 = format.l) == -1)) {
                long j3 = (long) (((float) (j2 - this.as)) * this.at);
                this.z += j3;
                this.aa = (j3 * ((long) i2)) + this.aa;
            }
            this.as = j2;
        }

        public final void d(long j2) {
            Format format;
            if (this.ah == 3 && (format = this.ap) != null) {
                long j3 = (long) (((float) (j2 - this.ar)) * this.at);
                int i2 = format.v;
                if (i2 != -1) {
                    this.v += j3;
                    this.w = (((long) i2) * j3) + this.w;
                }
                int i3 = format.l;
                if (i3 != -1) {
                    this.x += j3;
                    this.y = (j3 * ((long) i3)) + this.y;
                }
            }
            this.ar = j2;
        }

        public final void e(AnalyticsListener.EventTime eventTime, @Nullable Format format) {
            int i2;
            if (!Util.areEqual(this.aq, format)) {
                c(eventTime.a);
                if (!(format == null || this.u != -1 || (i2 = format.l) == -1)) {
                    this.u = (long) i2;
                }
                this.aq = format;
                if (this.a) {
                    this.f.add(new PlaybackStats.EventTimeAndFormat(eventTime, format));
                }
            }
        }

        public final void f(long j2) {
            if (b(this.ah)) {
                long j3 = j2 - this.ao;
                long j4 = this.r;
                if (j4 == -9223372036854775807L || j3 > j4) {
                    this.r = j3;
                }
            }
        }

        public final void g(long j2, long j3) {
            long[] jArr;
            if (this.a) {
                int i2 = this.ah;
                List<long[]> list = this.d;
                if (i2 != 3) {
                    if (j3 != -9223372036854775807L) {
                        if (!list.isEmpty()) {
                            long j4 = list.get(list.size() - 1)[1];
                            if (j4 != j3) {
                                list.add(new long[]{j2, j4});
                            }
                        }
                    } else {
                        return;
                    }
                }
                if (j3 == -9223372036854775807L) {
                    jArr = a(j2);
                } else {
                    jArr = new long[]{j2, j3};
                }
                list.add(jArr);
            }
        }

        public final void h(AnalyticsListener.EventTime eventTime, @Nullable Format format) {
            int i2;
            int i3;
            if (!Util.areEqual(this.ap, format)) {
                d(eventTime.a);
                if (format != null) {
                    if (this.s == -1 && (i3 = format.v) != -1) {
                        this.s = i3;
                    }
                    if (this.t == -1 && (i2 = format.l) != -1) {
                        this.t = (long) i2;
                    }
                }
                this.ap = format;
                if (this.a) {
                    this.e.add(new PlaybackStats.EventTimeAndFormat(eventTime, format));
                }
            }
        }

        public final void i(AnalyticsListener.EventTime eventTime, int i2) {
            boolean z2;
            boolean z3;
            boolean z4;
            boolean z5;
            boolean z6;
            boolean z7 = false;
            if (eventTime.a >= this.ai) {
                z2 = true;
            } else {
                z2 = false;
            }
            Assertions.checkArgument(z2);
            long j2 = this.ai;
            long j3 = eventTime.a;
            int i3 = this.ah;
            long[] jArr = this.b;
            jArr[i3] = jArr[i3] + (j3 - j2);
            if (this.j == -9223372036854775807L) {
                this.j = j3;
            }
            boolean z8 = this.m;
            if ((i3 != 1 && i3 != 2 && i3 != 14) || i2 == 1 || i2 == 2 || i2 == 14 || i2 == 3 || i2 == 4 || i2 == 9 || i2 == 11) {
                z3 = false;
            } else {
                z3 = true;
            }
            this.m = z8 | z3;
            boolean z9 = this.k;
            if (i2 == 3 || i2 == 4 || i2 == 9) {
                z4 = true;
            } else {
                z4 = false;
            }
            this.k = z9 | z4;
            boolean z10 = this.l;
            if (i2 == 11) {
                z5 = true;
            } else {
                z5 = false;
            }
            this.l = z10 | z5;
            if (i3 == 4 || i3 == 7) {
                z6 = true;
            } else {
                z6 = false;
            }
            if (!z6) {
                if (i2 == 4 || i2 == 7) {
                    z7 = true;
                }
                if (z7) {
                    this.n++;
                }
            }
            if (i2 == 5) {
                this.p++;
            }
            if (!b(i3) && b(i2)) {
                this.q++;
                this.ao = j3;
            }
            if (b(this.ah) && this.ah != 7 && i2 == 7) {
                this.o++;
            }
            f(j3);
            this.ah = i2;
            this.ai = j3;
            if (this.a) {
                this.c.add(new PlaybackStats.EventTimeAndPlaybackState(eventTime, i2));
            }
        }

        public void onEvents(Player player, AnalyticsListener.EventTime eventTime, boolean z2, long j2, boolean z3, int i2, boolean z4, boolean z5, @Nullable ExoPlaybackException exoPlaybackException, @Nullable Exception exc, long j3, long j4, @Nullable Format format, @Nullable Format format2, @Nullable VideoSize videoSize) {
            AnalyticsListener.EventTime eventTime2 = eventTime;
            long j5 = j2;
            ExoPlaybackException exoPlaybackException2 = exoPlaybackException;
            Exception exc2 = exc;
            Format format3 = format;
            Format format4 = format2;
            VideoSize videoSize2 = videoSize;
            if (j5 != -9223372036854775807L) {
                g(eventTime2.a, j5);
                this.aj = true;
            }
            int i3 = 2;
            if (player.getPlaybackState() != 2) {
                this.aj = false;
            }
            int playbackState = player.getPlaybackState();
            if (playbackState == 1 || playbackState == 4 || z3) {
                this.al = false;
            }
            boolean z6 = this.a;
            if (exoPlaybackException2 != null) {
                this.am = true;
                this.af++;
                if (z6) {
                    this.g.add(new PlaybackStats.EventTimeAndException(eventTime2, exoPlaybackException2));
                }
            } else if (player.getPlayerError() == null) {
                this.am = false;
            }
            if (this.ak && !this.al) {
                boolean z7 = false;
                boolean z8 = false;
                for (TrackSelection trackSelection : player.getCurrentTrackSelections().getAll()) {
                    if (trackSelection != null && trackSelection.length() > 0) {
                        int trackType = MimeTypes.getTrackType(trackSelection.getFormat(0).p);
                        if (trackType == 2) {
                            z7 = true;
                        } else if (trackType == 1) {
                            z8 = true;
                        }
                    }
                }
                if (!z7) {
                    h(eventTime2, (Format) null);
                }
                if (!z8) {
                    e(eventTime2, (Format) null);
                }
            }
            if (format3 != null) {
                h(eventTime2, format3);
            }
            if (format4 != null) {
                e(eventTime2, format4);
            }
            Format format5 = this.ap;
            if (!(format5 == null || format5.v != -1 || videoSize2 == null)) {
                h(eventTime2, format5.buildUpon().setWidth(videoSize2.c).setHeight(videoSize2.f).build());
            }
            if (z5) {
                this.an = true;
            }
            if (z4) {
                this.ae++;
            }
            this.ad += (long) i2;
            this.ab += j3;
            this.ac += j4;
            if (exc2 != null) {
                this.ag++;
                if (z6) {
                    this.h.add(new PlaybackStats.EventTimeAndException(eventTime2, exc2));
                }
            }
            int playbackState2 = player.getPlaybackState();
            if (this.aj && this.ak) {
                i3 = 5;
            } else if (this.am) {
                i3 = 13;
            } else if (!this.ak) {
                i3 = this.an;
            } else if (this.al) {
                i3 = 14;
            } else if (playbackState2 == 4) {
                i3 = 11;
            } else if (playbackState2 == 2) {
                int i4 = this.ah;
                if (!(i4 == 0 || i4 == 1 || i4 == 2 || i4 == 14)) {
                    i3 = !player.getPlayWhenReady() ? 7 : player.getPlaybackSuppressionReason() != 0 ? 10 : 6;
                }
            } else {
                i3 = 3;
                if (playbackState2 != 3) {
                    i3 = (playbackState2 != 1 || this.ah == 0) ? this.ah : 12;
                } else if (!player.getPlayWhenReady()) {
                    i3 = 4;
                } else if (player.getPlaybackSuppressionReason() != 0) {
                    i3 = 9;
                }
            }
            float f2 = player.getPlaybackParameters().c;
            if (!(this.ah == i3 && this.at == f2)) {
                g(eventTime2.a, z2 ? eventTime2.e : -9223372036854775807L);
                long j6 = eventTime2.a;
                d(j6);
                c(j6);
            }
            this.at = f2;
            if (this.ah != i3) {
                i(eventTime2, i3);
            }
        }

        public void onFinished(AnalyticsListener.EventTime eventTime, boolean z2, long j2) {
            int i2 = 11;
            if (this.ah != 11 && !z2) {
                i2 = 15;
            }
            g(eventTime.a, j2);
            long j3 = eventTime.a;
            d(j3);
            c(j3);
            i(eventTime, i2);
        }

        public void onForeground() {
            this.ak = true;
        }

        public void onInterruptedByAd() {
            this.al = true;
            this.aj = false;
        }
    }

    public PlaybackStatsListener(boolean z, @Nullable Callback callback) {
        this.d = callback;
        this.e = z;
        DefaultPlaybackSessionManager defaultPlaybackSessionManager = new DefaultPlaybackSessionManager();
        this.a = defaultPlaybackSessionManager;
        defaultPlaybackSessionManager.setListener(this);
    }

    public final boolean a(AnalyticsListener.Events events, String str, int i2) {
        if (!events.contains(i2) || !this.a.belongsToSession(events.getEventTime(i2), str)) {
            return false;
        }
        return true;
    }

    public PlaybackStats getCombinedPlaybackStats() {
        HashMap hashMap = this.b;
        int i2 = 1;
        PlaybackStats[] playbackStatsArr = new PlaybackStats[(hashMap.size() + 1)];
        playbackStatsArr[0] = this.g;
        for (a build : hashMap.values()) {
            playbackStatsArr[i2] = build.build(false);
            i2++;
        }
        return PlaybackStats.merge(playbackStatsArr);
    }

    @Nullable
    public PlaybackStats getPlaybackStats() {
        a aVar;
        String activeSessionId = this.a.getActiveSessionId();
        if (activeSessionId == null) {
            aVar = null;
        } else {
            aVar = (a) this.b.get(activeSessionId);
        }
        if (aVar == null) {
            return null;
        }
        return aVar.build(false);
    }

    public void onAdPlaybackStarted(AnalyticsListener.EventTime eventTime, String str, String str2) {
        ((a) Assertions.checkNotNull((a) this.b.get(str))).onInterruptedByAd();
    }

    public /* bridge */ /* synthetic */ void onAudioAttributesChanged(AnalyticsListener.EventTime eventTime, AudioAttributes audioAttributes) {
        ba.a(this, eventTime, audioAttributes);
    }

    public /* bridge */ /* synthetic */ void onAudioCodecError(AnalyticsListener.EventTime eventTime, Exception exc) {
        ba.b(this, eventTime, exc);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onAudioDecoderInitialized(AnalyticsListener.EventTime eventTime, String str, long j2) {
        ba.c(this, eventTime, str, j2);
    }

    public /* bridge */ /* synthetic */ void onAudioDecoderInitialized(AnalyticsListener.EventTime eventTime, String str, long j2, long j3) {
        ba.d(this, eventTime, str, j2, j3);
    }

    public /* bridge */ /* synthetic */ void onAudioDecoderReleased(AnalyticsListener.EventTime eventTime, String str) {
        ba.e(this, eventTime, str);
    }

    public /* bridge */ /* synthetic */ void onAudioDisabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
        ba.f(this, eventTime, decoderCounters);
    }

    public /* bridge */ /* synthetic */ void onAudioEnabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
        ba.g(this, eventTime, decoderCounters);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onAudioInputFormatChanged(AnalyticsListener.EventTime eventTime, Format format) {
        ba.h(this, eventTime, format);
    }

    public /* bridge */ /* synthetic */ void onAudioInputFormatChanged(AnalyticsListener.EventTime eventTime, Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation) {
        ba.i(this, eventTime, format, decoderReuseEvaluation);
    }

    public /* bridge */ /* synthetic */ void onAudioPositionAdvancing(AnalyticsListener.EventTime eventTime, long j2) {
        ba.j(this, eventTime, j2);
    }

    public /* bridge */ /* synthetic */ void onAudioSessionIdChanged(AnalyticsListener.EventTime eventTime, int i2) {
        ba.k(this, eventTime, i2);
    }

    public /* bridge */ /* synthetic */ void onAudioSinkError(AnalyticsListener.EventTime eventTime, Exception exc) {
        ba.l(this, eventTime, exc);
    }

    public /* bridge */ /* synthetic */ void onAudioUnderrun(AnalyticsListener.EventTime eventTime, int i2, long j2, long j3) {
        ba.m(this, eventTime, i2, j2, j3);
    }

    public void onBandwidthEstimate(AnalyticsListener.EventTime eventTime, int i2, long j2, long j3) {
        this.m = (long) i2;
        this.n = j2;
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onDecoderDisabled(AnalyticsListener.EventTime eventTime, int i2, DecoderCounters decoderCounters) {
        ba.o(this, eventTime, i2, decoderCounters);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onDecoderEnabled(AnalyticsListener.EventTime eventTime, int i2, DecoderCounters decoderCounters) {
        ba.p(this, eventTime, i2, decoderCounters);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onDecoderInitialized(AnalyticsListener.EventTime eventTime, int i2, String str, long j2) {
        ba.q(this, eventTime, i2, str, j2);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onDecoderInputFormatChanged(AnalyticsListener.EventTime eventTime, int i2, Format format) {
        ba.r(this, eventTime, i2, format);
    }

    public void onDownstreamFormatChanged(AnalyticsListener.EventTime eventTime, MediaLoadData mediaLoadData) {
        int i2 = mediaLoadData.b;
        Format format = mediaLoadData.c;
        if (i2 == 2 || i2 == 0) {
            this.o = format;
        } else if (i2 == 1) {
            this.p = format;
        }
    }

    public /* bridge */ /* synthetic */ void onDrmKeysLoaded(AnalyticsListener.EventTime eventTime) {
        ba.t(this, eventTime);
    }

    public /* bridge */ /* synthetic */ void onDrmKeysRemoved(AnalyticsListener.EventTime eventTime) {
        ba.u(this, eventTime);
    }

    public /* bridge */ /* synthetic */ void onDrmKeysRestored(AnalyticsListener.EventTime eventTime) {
        ba.v(this, eventTime);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onDrmSessionAcquired(AnalyticsListener.EventTime eventTime) {
        ba.w(this, eventTime);
    }

    public /* bridge */ /* synthetic */ void onDrmSessionAcquired(AnalyticsListener.EventTime eventTime, int i2) {
        ba.x(this, eventTime, i2);
    }

    public void onDrmSessionManagerError(AnalyticsListener.EventTime eventTime, Exception exc) {
        this.l = exc;
    }

    public /* bridge */ /* synthetic */ void onDrmSessionReleased(AnalyticsListener.EventTime eventTime) {
        ba.z(this, eventTime);
    }

    public void onDroppedVideoFrames(AnalyticsListener.EventTime eventTime, int i2, long j2) {
        this.k = i2;
    }

    public void onEvents(Player player, AnalyticsListener.Events events) {
        DefaultPlaybackSessionManager defaultPlaybackSessionManager;
        Iterator it;
        boolean z;
        long j2;
        int i2;
        ExoPlaybackException exoPlaybackException;
        Exception exc;
        long j3;
        Format format;
        Format format2;
        VideoSize videoSize;
        MediaSource.MediaPeriodId mediaPeriodId;
        HashMap hashMap;
        PlaybackStatsListener playbackStatsListener = this;
        AnalyticsListener.Events events2 = events;
        if (events.size() != 0) {
            int i3 = 0;
            while (true) {
                int size = events.size();
                defaultPlaybackSessionManager = playbackStatsListener.a;
                if (i3 >= size) {
                    break;
                }
                int i4 = events2.get(i3);
                AnalyticsListener.EventTime eventTime = events2.getEventTime(i4);
                if (i4 == 0) {
                    defaultPlaybackSessionManager.updateSessionsWithTimelineChange(eventTime);
                } else if (i4 == 12) {
                    defaultPlaybackSessionManager.updateSessionsWithDiscontinuity(eventTime, playbackStatsListener.j);
                } else {
                    defaultPlaybackSessionManager.updateSessions(eventTime);
                }
                i3++;
            }
            HashMap hashMap2 = playbackStatsListener.b;
            Iterator it2 = hashMap2.keySet().iterator();
            while (it2.hasNext()) {
                String str = (String) it2.next();
                int i5 = 0;
                AnalyticsListener.EventTime eventTime2 = null;
                boolean z2 = false;
                while (i5 < events.size()) {
                    AnalyticsListener.EventTime eventTime3 = events2.getEventTime(events2.get(i5));
                    boolean belongsToSession = defaultPlaybackSessionManager.belongsToSession(eventTime3, str);
                    if (eventTime2 == null || (belongsToSession && !z2)) {
                        hashMap = hashMap2;
                    } else {
                        if (belongsToSession == z2) {
                            hashMap = hashMap2;
                            if (eventTime3.a <= eventTime2.a) {
                            }
                        } else {
                            hashMap = hashMap2;
                        }
                        i5++;
                        hashMap2 = hashMap;
                    }
                    eventTime2 = eventTime3;
                    z2 = belongsToSession;
                    i5++;
                    hashMap2 = hashMap;
                }
                HashMap hashMap3 = hashMap2;
                Assertions.checkNotNull(eventTime2);
                if (z2 || (mediaPeriodId = eventTime2.d) == null || !mediaPeriodId.isAd()) {
                    it = it2;
                } else {
                    MediaSource.MediaPeriodId mediaPeriodId2 = eventTime2.d;
                    Object obj = mediaPeriodId2.a;
                    Timeline timeline = eventTime2.b;
                    Timeline.Period period = playbackStatsListener.f;
                    Timeline.Period periodByUid = timeline.getPeriodByUid(obj, period);
                    int i6 = mediaPeriodId2.b;
                    long adGroupTimeUs = periodByUid.getAdGroupTimeUs(i6);
                    if (adGroupTimeUs == Long.MIN_VALUE) {
                        adGroupTimeUs = period.h;
                    }
                    it = it2;
                    AnalyticsListener.EventTime eventTime4 = new AnalyticsListener.EventTime(eventTime2.a, eventTime2.b, eventTime2.c, new MediaSource.MediaPeriodId(mediaPeriodId2.a, mediaPeriodId2.d, i6), C.usToMs(period.getPositionInWindowUs() + adGroupTimeUs), eventTime2.b, eventTime2.g, eventTime2.h, eventTime2.i, eventTime2.j);
                    z2 = defaultPlaybackSessionManager.belongsToSession(eventTime4, str);
                    eventTime2 = eventTime4;
                }
                Pair create = Pair.create(eventTime2, Boolean.valueOf(z2));
                HashMap hashMap4 = hashMap3;
                a aVar = (a) hashMap4.get(str);
                AnalyticsListener.Events events3 = events;
                boolean a2 = a(events3, str, 12);
                boolean a3 = a(events3, str, 1023);
                boolean a4 = a(events3, str, 1012);
                boolean a5 = a(events3, str, 1000);
                boolean a6 = a(events3, str, 11);
                if (a(events3, str, 1003) || a(events3, str, 1032)) {
                    z = true;
                } else {
                    z = false;
                }
                boolean a7 = a(events3, str, 1006);
                boolean a8 = a(events3, str, PointerIconCompat.TYPE_WAIT);
                boolean a9 = a(events3, str, 1028);
                AnalyticsListener.EventTime eventTime5 = (AnalyticsListener.EventTime) create.first;
                boolean booleanValue = ((Boolean) create.second).booleanValue();
                if (str.equals(this.h)) {
                    j2 = this.i;
                } else {
                    j2 = -9223372036854775807L;
                }
                long j4 = j2;
                if (a3) {
                    i2 = this.k;
                } else {
                    i2 = 0;
                }
                if (a6) {
                    exoPlaybackException = player.getPlayerError();
                } else {
                    exoPlaybackException = null;
                }
                if (z) {
                    exc = this.l;
                } else {
                    exc = null;
                }
                long j5 = 0;
                if (a7) {
                    j3 = this.m;
                } else {
                    j3 = 0;
                }
                if (a7) {
                    j5 = this.n;
                }
                long j6 = j5;
                if (a8) {
                    format = this.o;
                } else {
                    format = null;
                }
                if (a8) {
                    format2 = this.p;
                } else {
                    format2 = null;
                }
                if (a9) {
                    videoSize = this.q;
                } else {
                    videoSize = null;
                }
                aVar.onEvents(player, eventTime5, booleanValue, j4, a2, i2, a4, a5, exoPlaybackException, exc, j3, j6, format, format2, videoSize);
                playbackStatsListener = this;
                it2 = it;
                AnalyticsListener.Events events4 = events3;
                hashMap2 = hashMap4;
                events2 = events4;
            }
            PlaybackStatsListener playbackStatsListener2 = playbackStatsListener;
            AnalyticsListener.Events events5 = events2;
            playbackStatsListener2.o = null;
            playbackStatsListener2.p = null;
            playbackStatsListener2.h = null;
            if (events5.contains(1036)) {
                defaultPlaybackSessionManager.finishAllSessions(events5.getEventTime(1036));
            }
        }
    }

    public /* bridge */ /* synthetic */ void onIsLoadingChanged(AnalyticsListener.EventTime eventTime, boolean z) {
        ba.ac(this, eventTime, z);
    }

    public /* bridge */ /* synthetic */ void onIsPlayingChanged(AnalyticsListener.EventTime eventTime, boolean z) {
        ba.ad(this, eventTime, z);
    }

    public /* bridge */ /* synthetic */ void onLoadCanceled(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        ba.ae(this, eventTime, loadEventInfo, mediaLoadData);
    }

    public /* bridge */ /* synthetic */ void onLoadCompleted(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        ba.af(this, eventTime, loadEventInfo, mediaLoadData);
    }

    public void onLoadError(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z) {
        this.l = iOException;
    }

    public /* bridge */ /* synthetic */ void onLoadStarted(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        ba.ah(this, eventTime, loadEventInfo, mediaLoadData);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onLoadingChanged(AnalyticsListener.EventTime eventTime, boolean z) {
        ba.ai(this, eventTime, z);
    }

    public /* bridge */ /* synthetic */ void onMediaItemTransition(AnalyticsListener.EventTime eventTime, @Nullable MediaItem mediaItem, int i2) {
        ba.aj(this, eventTime, mediaItem, i2);
    }

    public /* bridge */ /* synthetic */ void onMediaMetadataChanged(AnalyticsListener.EventTime eventTime, MediaMetadata mediaMetadata) {
        ba.ak(this, eventTime, mediaMetadata);
    }

    public /* bridge */ /* synthetic */ void onMetadata(AnalyticsListener.EventTime eventTime, Metadata metadata) {
        ba.al(this, eventTime, metadata);
    }

    public /* bridge */ /* synthetic */ void onPlayWhenReadyChanged(AnalyticsListener.EventTime eventTime, boolean z, int i2) {
        ba.am(this, eventTime, z, i2);
    }

    public /* bridge */ /* synthetic */ void onPlaybackParametersChanged(AnalyticsListener.EventTime eventTime, PlaybackParameters playbackParameters) {
        ba.an(this, eventTime, playbackParameters);
    }

    public /* bridge */ /* synthetic */ void onPlaybackStateChanged(AnalyticsListener.EventTime eventTime, int i2) {
        ba.ao(this, eventTime, i2);
    }

    public /* bridge */ /* synthetic */ void onPlaybackSuppressionReasonChanged(AnalyticsListener.EventTime eventTime, int i2) {
        ba.ap(this, eventTime, i2);
    }

    public /* bridge */ /* synthetic */ void onPlayerError(AnalyticsListener.EventTime eventTime, ExoPlaybackException exoPlaybackException) {
        ba.aq(this, eventTime, exoPlaybackException);
    }

    public /* bridge */ /* synthetic */ void onPlayerReleased(AnalyticsListener.EventTime eventTime) {
        ba.ar(this, eventTime);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onPlayerStateChanged(AnalyticsListener.EventTime eventTime, boolean z, int i2) {
        ba.as(this, eventTime, z, i2);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onPositionDiscontinuity(AnalyticsListener.EventTime eventTime, int i2) {
        ba.at(this, eventTime, i2);
    }

    public void onPositionDiscontinuity(AnalyticsListener.EventTime eventTime, Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i2) {
        if (this.h == null) {
            this.h = this.a.getActiveSessionId();
            this.i = positionInfo.i;
        }
        this.j = i2;
    }

    public /* bridge */ /* synthetic */ void onRenderedFirstFrame(AnalyticsListener.EventTime eventTime, Object obj, long j2) {
        ba.av(this, eventTime, obj, j2);
    }

    public /* bridge */ /* synthetic */ void onRepeatModeChanged(AnalyticsListener.EventTime eventTime, int i2) {
        ba.aw(this, eventTime, i2);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onSeekProcessed(AnalyticsListener.EventTime eventTime) {
        ba.ax(this, eventTime);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onSeekStarted(AnalyticsListener.EventTime eventTime) {
        ba.ay(this, eventTime);
    }

    public void onSessionActive(AnalyticsListener.EventTime eventTime, String str) {
        ((a) Assertions.checkNotNull((a) this.b.get(str))).onForeground();
    }

    public void onSessionCreated(AnalyticsListener.EventTime eventTime, String str) {
        this.b.put(str, new a(this.e, eventTime));
        this.c.put(str, eventTime);
    }

    public void onSessionFinished(AnalyticsListener.EventTime eventTime, String str, boolean z) {
        long j2;
        a aVar = (a) Assertions.checkNotNull((a) this.b.remove(str));
        AnalyticsListener.EventTime eventTime2 = (AnalyticsListener.EventTime) Assertions.checkNotNull((AnalyticsListener.EventTime) this.c.remove(str));
        if (str.equals(this.h)) {
            j2 = this.i;
        } else {
            j2 = -9223372036854775807L;
        }
        aVar.onFinished(eventTime, z, j2);
        PlaybackStats build = aVar.build(true);
        this.g = PlaybackStats.merge(this.g, build);
        Callback callback = this.d;
        if (callback != null) {
            callback.onPlaybackStatsReady(eventTime2, build);
        }
    }

    public /* bridge */ /* synthetic */ void onShuffleModeChanged(AnalyticsListener.EventTime eventTime, boolean z) {
        ba.az(this, eventTime, z);
    }

    public /* bridge */ /* synthetic */ void onSkipSilenceEnabledChanged(AnalyticsListener.EventTime eventTime, boolean z) {
        ba.ba(this, eventTime, z);
    }

    public /* bridge */ /* synthetic */ void onStaticMetadataChanged(AnalyticsListener.EventTime eventTime, List list) {
        ba.bb(this, eventTime, list);
    }

    public /* bridge */ /* synthetic */ void onSurfaceSizeChanged(AnalyticsListener.EventTime eventTime, int i2, int i3) {
        ba.bc(this, eventTime, i2, i3);
    }

    public /* bridge */ /* synthetic */ void onTimelineChanged(AnalyticsListener.EventTime eventTime, int i2) {
        ba.bd(this, eventTime, i2);
    }

    public /* bridge */ /* synthetic */ void onTracksChanged(AnalyticsListener.EventTime eventTime, TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
        ba.be(this, eventTime, trackGroupArray, trackSelectionArray);
    }

    public /* bridge */ /* synthetic */ void onUpstreamDiscarded(AnalyticsListener.EventTime eventTime, MediaLoadData mediaLoadData) {
        ba.bf(this, eventTime, mediaLoadData);
    }

    public /* bridge */ /* synthetic */ void onVideoCodecError(AnalyticsListener.EventTime eventTime, Exception exc) {
        ba.bg(this, eventTime, exc);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onVideoDecoderInitialized(AnalyticsListener.EventTime eventTime, String str, long j2) {
        ba.bh(this, eventTime, str, j2);
    }

    public /* bridge */ /* synthetic */ void onVideoDecoderInitialized(AnalyticsListener.EventTime eventTime, String str, long j2, long j3) {
        ba.bi(this, eventTime, str, j2, j3);
    }

    public /* bridge */ /* synthetic */ void onVideoDecoderReleased(AnalyticsListener.EventTime eventTime, String str) {
        ba.bj(this, eventTime, str);
    }

    public /* bridge */ /* synthetic */ void onVideoDisabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
        ba.bk(this, eventTime, decoderCounters);
    }

    public /* bridge */ /* synthetic */ void onVideoEnabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
        ba.bl(this, eventTime, decoderCounters);
    }

    public /* bridge */ /* synthetic */ void onVideoFrameProcessingOffset(AnalyticsListener.EventTime eventTime, long j2, int i2) {
        ba.bm(this, eventTime, j2, i2);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onVideoInputFormatChanged(AnalyticsListener.EventTime eventTime, Format format) {
        ba.bn(this, eventTime, format);
    }

    public /* bridge */ /* synthetic */ void onVideoInputFormatChanged(AnalyticsListener.EventTime eventTime, Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation) {
        ba.bo(this, eventTime, format, decoderReuseEvaluation);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onVideoSizeChanged(AnalyticsListener.EventTime eventTime, int i2, int i3, int i4, float f2) {
        ba.bp(this, eventTime, i2, i3, i4, f2);
    }

    public void onVideoSizeChanged(AnalyticsListener.EventTime eventTime, VideoSize videoSize) {
        this.q = videoSize;
    }

    public /* bridge */ /* synthetic */ void onVolumeChanged(AnalyticsListener.EventTime eventTime, float f2) {
        ba.br(this, eventTime, f2);
    }
}
