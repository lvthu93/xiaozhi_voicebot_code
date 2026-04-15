package com.google.android.exoplayer2;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Pair;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.DefaultMediaClock;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaSourceList;
import com.google.android.exoplayer2.PlayerMessage;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.HandlerWrapper;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ExoPlayerImplInternal implements Handler.Callback, MediaPeriod.Callback, TrackSelector.InvalidationListener, MediaSourceList.MediaSourceListInfoRefreshListener, DefaultMediaClock.PlaybackParametersListener, PlayerMessage.Sender {
    public s8 aa;
    public PlaybackInfoUpdate ab;
    public boolean ac;
    public boolean ad;
    public boolean ae;
    public boolean af;
    public boolean ag;
    public int ah;
    public boolean ai;
    public boolean aj;
    public boolean ak;
    public boolean al;
    public int am;
    @Nullable
    public e an;
    public long ao;
    public int ap;
    public boolean aq;
    @Nullable
    public ExoPlaybackException ar;
    public long as;
    public final Renderer[] c;
    public final RendererCapabilities[] f;
    public final TrackSelector g;
    public final TrackSelectorResult h;
    public final LoadControl i;
    public final BandwidthMeter j;
    public final HandlerWrapper k;
    public final HandlerThread l;
    public final Looper m;
    public final Timeline.Window n;
    public final Timeline.Period o;
    public final long p;
    public final boolean q;
    public final DefaultMediaClock r;
    public final ArrayList<c> s;
    public final Clock t;
    public final PlaybackInfoUpdateListener u;
    public final d v;
    public final MediaSourceList w;
    public final LivePlaybackSpeedControl x;
    public final long y;
    public SeekParameters z;

    public static final class PlaybackInfoUpdate {
        public boolean a;
        public s8 b;
        public int c;
        public boolean d;
        public int e;
        public boolean f;
        public int g;

        public PlaybackInfoUpdate(s8 s8Var) {
            this.b = s8Var;
        }

        public void incrementPendingOperationAcks(int i) {
            boolean z;
            boolean z2 = this.a;
            if (i > 0) {
                z = true;
            } else {
                z = false;
            }
            this.a = z2 | z;
            this.c += i;
        }

        public void setPlayWhenReadyChangeReason(int i) {
            this.a = true;
            this.f = true;
            this.g = i;
        }

        public void setPlaybackInfo(s8 s8Var) {
            boolean z;
            boolean z2 = this.a;
            if (this.b != s8Var) {
                z = true;
            } else {
                z = false;
            }
            this.a = z2 | z;
            this.b = s8Var;
        }

        public void setPositionDiscontinuity(int i) {
            boolean z = true;
            if (!this.d || this.e == 5) {
                this.a = true;
                this.d = true;
                this.e = i;
                return;
            }
            if (i != 5) {
                z = false;
            }
            Assertions.checkArgument(z);
        }
    }

    public interface PlaybackInfoUpdateListener {
        void onPlaybackInfoUpdate(PlaybackInfoUpdate playbackInfoUpdate);
    }

    public static final class a {
        public final List<MediaSourceList.c> a;
        public final ShuffleOrder b;
        public final int c;
        public final long d;

        public a(List list, ShuffleOrder shuffleOrder, int i, long j) {
            this.a = list;
            this.b = shuffleOrder;
            this.c = i;
            this.d = j;
        }
    }

    public static class b {
        public final int a;
        public final int b;
        public final int c;
        public final ShuffleOrder d;

        public b(int i, int i2, int i3, ShuffleOrder shuffleOrder) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = shuffleOrder;
        }
    }

    public static final class c implements Comparable<c> {
        public final PlayerMessage c;
        public int f;
        public long g;
        @Nullable
        public Object h;

        public c(PlayerMessage playerMessage) {
            this.c = playerMessage;
        }

        public void setResolvedPosition(int i, long j, Object obj) {
            this.f = i;
            this.g = j;
            this.h = obj;
        }

        public int compareTo(c cVar) {
            Object obj = this.h;
            if ((obj == null) != (cVar.h == null)) {
                return obj != null ? -1 : 1;
            }
            if (obj == null) {
                return 0;
            }
            int i = this.f - cVar.f;
            if (i != 0) {
                return i;
            }
            return Util.compareLong(this.g, cVar.g);
        }
    }

    public static final class d {
        public final MediaSource.MediaPeriodId a;
        public final long b;
        public final long c;
        public final boolean d;
        public final boolean e;
        public final boolean f;

        public d(MediaSource.MediaPeriodId mediaPeriodId, long j, long j2, boolean z, boolean z2, boolean z3) {
            this.a = mediaPeriodId;
            this.b = j;
            this.c = j2;
            this.d = z;
            this.e = z2;
            this.f = z3;
        }
    }

    public static final class e {
        public final Timeline a;
        public final int b;
        public final long c;

        public e(Timeline timeline, int i, long j) {
            this.a = timeline;
            this.b = i;
            this.c = j;
        }
    }

    public ExoPlayerImplInternal(Renderer[] rendererArr, TrackSelector trackSelector, TrackSelectorResult trackSelectorResult, LoadControl loadControl, BandwidthMeter bandwidthMeter, int i2, boolean z2, @Nullable AnalyticsCollector analyticsCollector, SeekParameters seekParameters, LivePlaybackSpeedControl livePlaybackSpeedControl, long j2, boolean z3, Looper looper, Clock clock, PlaybackInfoUpdateListener playbackInfoUpdateListener) {
        Renderer[] rendererArr2 = rendererArr;
        BandwidthMeter bandwidthMeter2 = bandwidthMeter;
        AnalyticsCollector analyticsCollector2 = analyticsCollector;
        long j3 = j2;
        Clock clock2 = clock;
        this.u = playbackInfoUpdateListener;
        this.c = rendererArr2;
        this.g = trackSelector;
        this.h = trackSelectorResult;
        this.i = loadControl;
        this.j = bandwidthMeter2;
        this.ah = i2;
        this.ai = z2;
        this.z = seekParameters;
        this.x = livePlaybackSpeedControl;
        this.y = j3;
        this.as = j3;
        this.ad = z3;
        this.t = clock2;
        this.p = loadControl.getBackBufferDurationUs();
        this.q = loadControl.retainBackBufferFromKeyframe();
        s8 createDummy = s8.createDummy(trackSelectorResult);
        this.aa = createDummy;
        this.ab = new PlaybackInfoUpdate(createDummy);
        this.f = new RendererCapabilities[rendererArr2.length];
        for (int i3 = 0; i3 < rendererArr2.length; i3++) {
            rendererArr2[i3].setIndex(i3);
            this.f[i3] = rendererArr2[i3].getCapabilities();
        }
        this.r = new DefaultMediaClock(this, clock2);
        this.s = new ArrayList<>();
        this.n = new Timeline.Window();
        this.o = new Timeline.Period();
        trackSelector.init(this, bandwidthMeter2);
        this.aq = true;
        Handler handler = new Handler(looper);
        this.v = new d(analyticsCollector2, handler);
        this.w = new MediaSourceList(this, analyticsCollector2, handler);
        HandlerThread handlerThread = new HandlerThread("ExoPlayer:Playback", -16);
        this.l = handlerThread;
        handlerThread.start();
        Looper looper2 = handlerThread.getLooper();
        this.m = looper2;
        this.k = clock2.createHandler(looper2, this);
    }

    public static void ab(Timeline timeline, c cVar, Timeline.Window window, Timeline.Period period) {
        long j2;
        int i2 = timeline.getWindow(timeline.getPeriodByUid(cVar.h, period).g, window).t;
        Object obj = timeline.getPeriod(i2, period, true).f;
        long j3 = period.h;
        if (j3 != -9223372036854775807L) {
            j2 = j3 - 1;
        } else {
            j2 = Long.MAX_VALUE;
        }
        cVar.setResolvedPosition(i2, j2, obj);
    }

    public static boolean ac(c cVar, Timeline timeline, Timeline timeline2, int i2, boolean z2, Timeline.Window window, Timeline.Period period) {
        long j2;
        c cVar2 = cVar;
        Timeline timeline3 = timeline;
        Timeline timeline4 = timeline2;
        Timeline.Window window2 = window;
        Timeline.Period period2 = period;
        Object obj = cVar2.h;
        PlayerMessage playerMessage = cVar2.c;
        if (obj == null) {
            if (playerMessage.getPositionMs() == Long.MIN_VALUE) {
                j2 = -9223372036854775807L;
            } else {
                j2 = C.msToUs(playerMessage.getPositionMs());
            }
            Pair<Object, Long> ae2 = ae(timeline, new e(playerMessage.getTimeline(), playerMessage.getWindowIndex(), j2), false, i2, z2, window, period);
            if (ae2 == null) {
                return false;
            }
            cVar2.setResolvedPosition(timeline3.getIndexOfPeriod(ae2.first), ((Long) ae2.second).longValue(), ae2.first);
            if (playerMessage.getPositionMs() == Long.MIN_VALUE) {
                ab(timeline3, cVar2, window2, period2);
            }
            return true;
        }
        int indexOfPeriod = timeline3.getIndexOfPeriod(obj);
        if (indexOfPeriod == -1) {
            return false;
        }
        if (playerMessage.getPositionMs() == Long.MIN_VALUE) {
            ab(timeline3, cVar2, window2, period2);
            return true;
        }
        cVar2.f = indexOfPeriod;
        timeline4.getPeriodByUid(cVar2.h, period2);
        if (period2.j && timeline4.getWindow(period2.g, window2).s == timeline4.getIndexOfPeriod(cVar2.h)) {
            long positionInWindowUs = period.getPositionInWindowUs() + cVar2.g;
            Pair<Object, Long> periodPosition = timeline.getPeriodPosition(window, period, timeline3.getPeriodByUid(cVar2.h, period2).g, positionInWindowUs);
            cVar2.setResolvedPosition(timeline3.getIndexOfPeriod(periodPosition.first), ((Long) periodPosition.second).longValue(), periodPosition.first);
        }
        return true;
    }

    @Nullable
    public static Pair<Object, Long> ae(Timeline timeline, e eVar, boolean z2, int i2, boolean z3, Timeline.Window window, Timeline.Period period) {
        Timeline timeline2;
        Object af2;
        Timeline timeline3 = timeline;
        e eVar2 = eVar;
        Timeline.Period period2 = period;
        Timeline timeline4 = eVar2.a;
        if (timeline.isEmpty()) {
            return null;
        }
        if (timeline4.isEmpty()) {
            timeline2 = timeline3;
        } else {
            timeline2 = timeline4;
        }
        try {
            Pair<Object, Long> periodPosition = timeline2.getPeriodPosition(window, period, eVar2.b, eVar2.c);
            if (timeline.equals(timeline2)) {
                return periodPosition;
            }
            if (timeline.getIndexOfPeriod(periodPosition.first) == -1) {
                Timeline.Window window2 = window;
                if (z2 && (af2 = af(window, period, i2, z3, periodPosition.first, timeline2, timeline)) != null) {
                    return timeline.getPeriodPosition(window, period, timeline.getPeriodByUid(af2, period2).g, -9223372036854775807L);
                }
                return null;
            } else if (!timeline2.getPeriodByUid(periodPosition.first, period2).j || timeline2.getWindow(period2.g, window).s != timeline2.getIndexOfPeriod(periodPosition.first)) {
                return periodPosition;
            } else {
                return timeline.getPeriodPosition(window, period, timeline.getPeriodByUid(periodPosition.first, period2).g, eVar2.c);
            }
        } catch (IndexOutOfBoundsException unused) {
        }
    }

    @Nullable
    public static Object af(Timeline.Window window, Timeline.Period period, int i2, boolean z2, Object obj, Timeline timeline, Timeline timeline2) {
        int indexOfPeriod = timeline.getIndexOfPeriod(obj);
        int periodCount = timeline.getPeriodCount();
        int i3 = indexOfPeriod;
        int i4 = -1;
        for (int i5 = 0; i5 < periodCount && i4 == -1; i5++) {
            i3 = timeline.getNextPeriodIndex(i3, period, window, i2, z2);
            if (i3 == -1) {
                break;
            }
            i4 = timeline2.getIndexOfPeriod(timeline.getUidOfPeriod(i3));
        }
        if (i4 == -1) {
            return null;
        }
        return timeline2.getUidOfPeriod(i4);
    }

    public static boolean ax(s8 s8Var, Timeline.Period period) {
        MediaSource.MediaPeriodId mediaPeriodId = s8Var.b;
        if (!mediaPeriodId.isAd()) {
            Timeline timeline = s8Var.a;
            if (timeline.isEmpty() || timeline.getPeriodByUid(mediaPeriodId.a, period).j) {
                return true;
            }
            return false;
        }
        return true;
    }

    public static void b(PlayerMessage playerMessage) throws ExoPlaybackException {
        if (!playerMessage.isCanceled()) {
            try {
                playerMessage.getTarget().handleMessage(playerMessage.getType(), playerMessage.getPayload());
            } finally {
                playerMessage.markAsProcessed(true);
            }
        }
    }

    public static boolean p(Renderer renderer) {
        return renderer.getState() != 0;
    }

    public final void a(a aVar, int i2) throws ExoPlaybackException {
        this.ab.incrementPendingOperationAcks(1);
        MediaSourceList mediaSourceList = this.w;
        if (i2 == -1) {
            i2 = mediaSourceList.getSize();
        }
        k(mediaSourceList.addMediaSources(i2, aVar.a, aVar.b), false);
    }

    public final void aa(long j2) throws ExoPlaybackException {
        d dVar = this.v;
        b6 playingPeriod = dVar.getPlayingPeriod();
        if (playingPeriod != null) {
            j2 = playingPeriod.toRendererTime(j2);
        }
        this.ao = j2;
        this.r.resetPosition(j2);
        for (Renderer renderer : this.c) {
            if (p(renderer)) {
                renderer.resetPosition(this.ao);
            }
        }
        for (b6 playingPeriod2 = dVar.getPlayingPeriod(); playingPeriod2 != null; playingPeriod2 = playingPeriod2.getNext()) {
            for (ExoTrackSelection exoTrackSelection : playingPeriod2.getTrackSelectorResult().c) {
                if (exoTrackSelection != null) {
                    exoTrackSelection.onDiscontinuity();
                }
            }
        }
    }

    public final void ad(Timeline timeline, Timeline timeline2) {
        if (!timeline.isEmpty() || !timeline2.isEmpty()) {
            ArrayList<c> arrayList = this.s;
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                if (!ac(arrayList.get(size), timeline, timeline2, this.ah, this.ai, this.n, this.o)) {
                    arrayList.get(size).c.markAsProcessed(false);
                    arrayList.remove(size);
                }
            }
            Collections.sort(arrayList);
        }
    }

    public void addMediaSources(int i2, List<MediaSourceList.c> list, ShuffleOrder shuffleOrder) {
        this.k.obtainMessage(18, i2, 0, new a(list, shuffleOrder, -1, -9223372036854775807L)).sendToTarget();
    }

    public final void ag(boolean z2) throws ExoPlaybackException {
        MediaSource.MediaPeriodId mediaPeriodId = this.v.getPlayingPeriod().f.a;
        long ai2 = ai(mediaPeriodId, this.aa.s, true, false);
        if (ai2 != this.aa.s) {
            s8 s8Var = this.aa;
            this.aa = n(mediaPeriodId, ai2, s8Var.c, s8Var.d, z2, 5);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x00a7 A[Catch:{ all -> 0x014a }] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00aa A[Catch:{ all -> 0x014a }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void ah(com.google.android.exoplayer2.ExoPlayerImplInternal.e r20) throws com.google.android.exoplayer2.ExoPlaybackException {
        /*
            r19 = this;
            r11 = r19
            r0 = r20
            com.google.android.exoplayer2.ExoPlayerImplInternal$PlaybackInfoUpdate r1 = r11.ab
            r8 = 1
            r1.incrementPendingOperationAcks(r8)
            s8 r1 = r11.aa
            com.google.android.exoplayer2.Timeline r1 = r1.a
            r3 = 1
            int r4 = r11.ah
            boolean r5 = r11.ai
            com.google.android.exoplayer2.Timeline$Window r6 = r11.n
            com.google.android.exoplayer2.Timeline$Period r7 = r11.o
            r2 = r20
            android.util.Pair r1 = ae(r1, r2, r3, r4, r5, r6, r7)
            r2 = 0
            r4 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r7 = 0
            if (r1 != 0) goto L_0x0045
            s8 r6 = r11.aa
            com.google.android.exoplayer2.Timeline r6 = r6.a
            android.util.Pair r6 = r11.h(r6)
            java.lang.Object r9 = r6.first
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r9 = (com.google.android.exoplayer2.source.MediaSource.MediaPeriodId) r9
            java.lang.Object r6 = r6.second
            java.lang.Long r6 = (java.lang.Long) r6
            long r12 = r6.longValue()
            s8 r6 = r11.aa
            com.google.android.exoplayer2.Timeline r6 = r6.a
            boolean r6 = r6.isEmpty()
            r6 = r6 ^ r8
            goto L_0x009b
        L_0x0045:
            java.lang.Object r6 = r1.first
            java.lang.Object r9 = r1.second
            java.lang.Long r9 = (java.lang.Long) r9
            long r12 = r9.longValue()
            long r9 = r0.c
            int r14 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            if (r14 != 0) goto L_0x0057
            r9 = r4
            goto L_0x0058
        L_0x0057:
            r9 = r12
        L_0x0058:
            com.google.android.exoplayer2.d r14 = r11.v
            s8 r15 = r11.aa
            com.google.android.exoplayer2.Timeline r15 = r15.a
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r6 = r14.resolveMediaPeriodIdForAds(r15, r6, r12)
            boolean r14 = r6.isAd()
            if (r14 == 0) goto L_0x008c
            s8 r4 = r11.aa
            com.google.android.exoplayer2.Timeline r4 = r4.a
            java.lang.Object r5 = r6.a
            com.google.android.exoplayer2.Timeline$Period r12 = r11.o
            r4.getPeriodByUid(r5, r12)
            com.google.android.exoplayer2.Timeline$Period r4 = r11.o
            int r5 = r6.b
            int r4 = r4.getFirstAdIndexToPlay(r5)
            int r5 = r6.c
            if (r4 != r5) goto L_0x0086
            com.google.android.exoplayer2.Timeline$Period r4 = r11.o
            long r4 = r4.getAdResumePositionUs()
            goto L_0x0087
        L_0x0086:
            r4 = r2
        L_0x0087:
            r12 = r4
            r14 = r9
            r10 = 1
            r9 = r6
            goto L_0x009d
        L_0x008c:
            long r14 = r0.c
            int r16 = (r14 > r4 ? 1 : (r14 == r4 ? 0 : -1))
            if (r16 != 0) goto L_0x0094
            r4 = 1
            goto L_0x0095
        L_0x0094:
            r4 = 0
        L_0x0095:
            r18 = r6
            r6 = r4
            r4 = r9
            r9 = r18
        L_0x009b:
            r14 = r4
            r10 = r6
        L_0x009d:
            s8 r4 = r11.aa     // Catch:{ all -> 0x014a }
            com.google.android.exoplayer2.Timeline r4 = r4.a     // Catch:{ all -> 0x014a }
            boolean r4 = r4.isEmpty()     // Catch:{ all -> 0x014a }
            if (r4 == 0) goto L_0x00aa
            r11.an = r0     // Catch:{ all -> 0x014a }
            goto L_0x00b9
        L_0x00aa:
            r0 = 4
            if (r1 != 0) goto L_0x00bb
            s8 r1 = r11.aa     // Catch:{ all -> 0x014a }
            int r1 = r1.e     // Catch:{ all -> 0x014a }
            if (r1 == r8) goto L_0x00b6
            r11.au(r0)     // Catch:{ all -> 0x014a }
        L_0x00b6:
            r11.y(r7, r8, r7, r8)     // Catch:{ all -> 0x014a }
        L_0x00b9:
            r7 = r12
            goto L_0x00fb
        L_0x00bb:
            s8 r1 = r11.aa     // Catch:{ all -> 0x014a }
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r1 = r1.b     // Catch:{ all -> 0x014a }
            boolean r1 = r9.equals(r1)     // Catch:{ all -> 0x014a }
            if (r1 == 0) goto L_0x010c
            com.google.android.exoplayer2.d r1 = r11.v     // Catch:{ all -> 0x014a }
            b6 r1 = r1.getPlayingPeriod()     // Catch:{ all -> 0x014a }
            if (r1 == 0) goto L_0x00de
            boolean r4 = r1.d     // Catch:{ all -> 0x014a }
            if (r4 == 0) goto L_0x00de
            int r4 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1))
            if (r4 == 0) goto L_0x00de
            com.google.android.exoplayer2.source.MediaPeriod r1 = r1.a     // Catch:{ all -> 0x014a }
            com.google.android.exoplayer2.SeekParameters r2 = r11.z     // Catch:{ all -> 0x014a }
            long r1 = r1.getAdjustedSeekPositionUs(r12, r2)     // Catch:{ all -> 0x014a }
            goto L_0x00df
        L_0x00de:
            r1 = r12
        L_0x00df:
            long r3 = com.google.android.exoplayer2.C.usToMs(r1)     // Catch:{ all -> 0x014a }
            s8 r5 = r11.aa     // Catch:{ all -> 0x014a }
            long r5 = r5.s     // Catch:{ all -> 0x014a }
            long r5 = com.google.android.exoplayer2.C.usToMs(r5)     // Catch:{ all -> 0x014a }
            int r16 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r16 != 0) goto L_0x010a
            s8 r3 = r11.aa     // Catch:{ all -> 0x014a }
            int r4 = r3.e     // Catch:{ all -> 0x014a }
            r5 = 2
            if (r4 == r5) goto L_0x00f9
            r5 = 3
            if (r4 != r5) goto L_0x010a
        L_0x00f9:
            long r7 = r3.s     // Catch:{ all -> 0x014a }
        L_0x00fb:
            r0 = 2
            r1 = r19
            r2 = r9
            r3 = r7
            r5 = r14
            r9 = r10
            r10 = r0
            s8 r0 = r1.n(r2, r3, r5, r7, r9, r10)
            r11.aa = r0
            return
        L_0x010a:
            r3 = r1
            goto L_0x010d
        L_0x010c:
            r3 = r12
        L_0x010d:
            s8 r1 = r11.aa     // Catch:{ all -> 0x014a }
            int r1 = r1.e     // Catch:{ all -> 0x014a }
            if (r1 != r0) goto L_0x0115
            r6 = 1
            goto L_0x0116
        L_0x0115:
            r6 = 0
        L_0x0116:
            com.google.android.exoplayer2.d r0 = r11.v     // Catch:{ all -> 0x014a }
            b6 r1 = r0.getPlayingPeriod()     // Catch:{ all -> 0x014a }
            b6 r0 = r0.getReadingPeriod()     // Catch:{ all -> 0x014a }
            if (r1 == r0) goto L_0x0124
            r5 = 1
            goto L_0x0125
        L_0x0124:
            r5 = 0
        L_0x0125:
            r1 = r19
            r2 = r9
            long r16 = r1.ai(r2, r3, r5, r6)     // Catch:{ all -> 0x014a }
            int r0 = (r12 > r16 ? 1 : (r12 == r16 ? 0 : -1))
            if (r0 == 0) goto L_0x0131
            goto L_0x0132
        L_0x0131:
            r8 = 0
        L_0x0132:
            r8 = r8 | r10
            s8 r0 = r11.aa     // Catch:{ all -> 0x0145 }
            com.google.android.exoplayer2.Timeline r4 = r0.a     // Catch:{ all -> 0x0145 }
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r5 = r0.b     // Catch:{ all -> 0x0145 }
            r1 = r19
            r2 = r4
            r3 = r9
            r6 = r14
            r1.bb(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x0145 }
            r10 = r8
            r7 = r16
            goto L_0x00fb
        L_0x0145:
            r0 = move-exception
            r10 = r8
            r7 = r16
            goto L_0x014c
        L_0x014a:
            r0 = move-exception
            r7 = r12
        L_0x014c:
            r12 = 2
            r1 = r19
            r2 = r9
            r3 = r7
            r5 = r14
            r9 = r10
            r10 = r12
            s8 r1 = r1.n(r2, r3, r5, r7, r9, r10)
            r11.aa = r1
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ExoPlayerImplInternal.ah(com.google.android.exoplayer2.ExoPlayerImplInternal$e):void");
    }

    public final long ai(MediaSource.MediaPeriodId mediaPeriodId, long j2, boolean z2, boolean z3) throws ExoPlaybackException {
        az();
        this.af = false;
        if (z3 || this.aa.e == 3) {
            au(2);
        }
        d dVar = this.v;
        b6 playingPeriod = dVar.getPlayingPeriod();
        b6 b6Var = playingPeriod;
        while (b6Var != null && !mediaPeriodId.equals(b6Var.f.a)) {
            b6Var = b6Var.getNext();
        }
        if (z2 || playingPeriod != b6Var || (b6Var != null && b6Var.toRendererTime(j2) < 0)) {
            Renderer[] rendererArr = this.c;
            for (Renderer c2 : rendererArr) {
                c(c2);
            }
            if (b6Var != null) {
                while (dVar.getPlayingPeriod() != b6Var) {
                    dVar.advancePlayingPeriod();
                }
                dVar.removeAfter(b6Var);
                b6Var.setRendererOffset(0);
                e(new boolean[rendererArr.length]);
            }
        }
        if (b6Var != null) {
            dVar.removeAfter(b6Var);
            if (!b6Var.d) {
                b6Var.f = b6Var.f.copyWithStartPositionUs(j2);
            } else {
                long j3 = b6Var.f.e;
                if (j3 != -9223372036854775807L && j2 >= j3) {
                    j2 = Math.max(0, j3 - 1);
                }
                if (b6Var.e) {
                    MediaPeriod mediaPeriod = b6Var.a;
                    j2 = mediaPeriod.seekToUs(j2);
                    mediaPeriod.discardBuffer(j2 - this.p, this.q);
                }
            }
            aa(j2);
            r();
        } else {
            dVar.clear();
            aa(j2);
        }
        j(false);
        this.k.sendEmptyMessage(2);
        return j2;
    }

    public final void aj(PlayerMessage playerMessage) throws ExoPlaybackException {
        if (playerMessage.getPositionMs() == -9223372036854775807L) {
            ak(playerMessage);
            return;
        }
        boolean isEmpty = this.aa.a.isEmpty();
        ArrayList<c> arrayList = this.s;
        if (isEmpty) {
            arrayList.add(new c(playerMessage));
            return;
        }
        c cVar = new c(playerMessage);
        Timeline timeline = this.aa.a;
        if (ac(cVar, timeline, timeline, this.ah, this.ai, this.n, this.o)) {
            arrayList.add(cVar);
            Collections.sort(arrayList);
            return;
        }
        playerMessage.markAsProcessed(false);
    }

    public final void ak(PlayerMessage playerMessage) throws ExoPlaybackException {
        Looper looper = playerMessage.getLooper();
        Looper looper2 = this.m;
        HandlerWrapper handlerWrapper = this.k;
        if (looper == looper2) {
            b(playerMessage);
            int i2 = this.aa.e;
            if (i2 == 3 || i2 == 2) {
                handlerWrapper.sendEmptyMessage(2);
                return;
            }
            return;
        }
        handlerWrapper.obtainMessage(15, playerMessage).sendToTarget();
    }

    public final void al(PlayerMessage playerMessage) {
        Looper looper = playerMessage.getLooper();
        if (!looper.getThread().isAlive()) {
            Log.w("TAG", "Trying to send message on a dead thread.");
            playerMessage.markAsProcessed(false);
            return;
        }
        this.t.createHandler(looper, (Handler.Callback) null).post(new h2(1, this, playerMessage));
    }

    public final void am(boolean z2, @Nullable AtomicBoolean atomicBoolean) {
        if (this.aj != z2) {
            this.aj = z2;
            if (!z2) {
                for (Renderer renderer : this.c) {
                    if (!p(renderer)) {
                        renderer.reset();
                    }
                }
            }
        }
        if (atomicBoolean != null) {
            synchronized (this) {
                atomicBoolean.set(true);
                notifyAll();
            }
        }
    }

    public final void an(a aVar) throws ExoPlaybackException {
        this.ab.incrementPendingOperationAcks(1);
        int i2 = aVar.c;
        ShuffleOrder shuffleOrder = aVar.b;
        List<MediaSourceList.c> list = aVar.a;
        if (i2 != -1) {
            this.an = new e(new z8(list, shuffleOrder), aVar.c, aVar.d);
        }
        k(this.w.setMediaSources(list, shuffleOrder), false);
    }

    public final void ao(boolean z2) {
        if (z2 != this.al) {
            this.al = z2;
            s8 s8Var = this.aa;
            int i2 = s8Var.e;
            if (z2 || i2 == 4 || i2 == 1) {
                this.aa = s8Var.copyWithOffloadSchedulingEnabled(z2);
            } else {
                this.k.sendEmptyMessage(2);
            }
        }
    }

    public final void ap(boolean z2) throws ExoPlaybackException {
        this.ad = z2;
        z();
        if (this.ae) {
            d dVar = this.v;
            if (dVar.getReadingPeriod() != dVar.getPlayingPeriod()) {
                ag(true);
                j(false);
            }
        }
    }

    public final void aq(int i2, int i3, boolean z2, boolean z3) throws ExoPlaybackException {
        this.ab.incrementPendingOperationAcks(z3 ? 1 : 0);
        this.ab.setPlayWhenReadyChangeReason(i3);
        this.aa = this.aa.copyWithPlayWhenReady(z2, i2);
        this.af = false;
        for (b6 playingPeriod = this.v.getPlayingPeriod(); playingPeriod != null; playingPeriod = playingPeriod.getNext()) {
            for (ExoTrackSelection exoTrackSelection : playingPeriod.getTrackSelectorResult().c) {
                if (exoTrackSelection != null) {
                    exoTrackSelection.onPlayWhenReadyChanged(z2);
                }
            }
        }
        if (!av()) {
            az();
            bc();
            return;
        }
        int i4 = this.aa.e;
        HandlerWrapper handlerWrapper = this.k;
        if (i4 == 3) {
            this.af = false;
            this.r.start();
            for (Renderer renderer : this.c) {
                if (p(renderer)) {
                    renderer.start();
                }
            }
            handlerWrapper.sendEmptyMessage(2);
        } else if (i4 == 2) {
            handlerWrapper.sendEmptyMessage(2);
        }
    }

    public final void ar(int i2) throws ExoPlaybackException {
        this.ah = i2;
        if (!this.v.updateRepeatMode(this.aa.a, i2)) {
            ag(true);
        }
        j(false);
    }

    public final void as(boolean z2) throws ExoPlaybackException {
        this.ai = z2;
        if (!this.v.updateShuffleModeEnabled(this.aa.a, z2)) {
            ag(true);
        }
        j(false);
    }

    public final void at(ShuffleOrder shuffleOrder) throws ExoPlaybackException {
        this.ab.incrementPendingOperationAcks(1);
        k(this.w.setShuffleOrder(shuffleOrder), false);
    }

    public final void au(int i2) {
        s8 s8Var = this.aa;
        if (s8Var.e != i2) {
            this.aa = s8Var.copyWithPlaybackState(i2);
        }
    }

    public final boolean av() {
        s8 s8Var = this.aa;
        return s8Var.l && s8Var.m == 0;
    }

    public final boolean aw(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId) {
        if (mediaPeriodId.isAd() || timeline.isEmpty()) {
            return false;
        }
        int i2 = timeline.getPeriodByUid(mediaPeriodId.a, this.o).g;
        Timeline.Window window = this.n;
        timeline.getWindow(i2, window);
        if (!window.isLive() || !window.m || window.j == -9223372036854775807L) {
            return false;
        }
        return true;
    }

    public final void ay(boolean z2, boolean z3) {
        boolean z4;
        if (z2 || !this.aj) {
            z4 = true;
        } else {
            z4 = false;
        }
        y(z4, false, true, false);
        this.ab.incrementPendingOperationAcks(z3 ? 1 : 0);
        this.i.onStopped();
        au(1);
    }

    public final void az() throws ExoPlaybackException {
        this.r.stop();
        for (Renderer renderer : this.c) {
            if (p(renderer) && renderer.getState() == 2) {
                renderer.stop();
            }
        }
    }

    public final void ba() {
        boolean z2;
        b6 loadingPeriod = this.v.getLoadingPeriod();
        if (this.ag || (loadingPeriod != null && loadingPeriod.a.isLoading())) {
            z2 = true;
        } else {
            z2 = false;
        }
        s8 s8Var = this.aa;
        if (z2 != s8Var.g) {
            this.aa = s8Var.copyWithIsLoading(z2);
        }
    }

    public final void bb(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline2, MediaSource.MediaPeriodId mediaPeriodId2, long j2) {
        Object obj;
        if (timeline.isEmpty() || !aw(timeline, mediaPeriodId)) {
            DefaultMediaClock defaultMediaClock = this.r;
            float f2 = defaultMediaClock.getPlaybackParameters().c;
            PlaybackParameters playbackParameters = this.aa.n;
            if (f2 != playbackParameters.c) {
                defaultMediaClock.setPlaybackParameters(playbackParameters);
                return;
            }
            return;
        }
        Object obj2 = mediaPeriodId.a;
        Timeline.Period period = this.o;
        int i2 = timeline.getPeriodByUid(obj2, period).g;
        Timeline.Window window = this.n;
        timeline.getWindow(i2, window);
        LivePlaybackSpeedControl livePlaybackSpeedControl = this.x;
        livePlaybackSpeedControl.setLiveConfiguration((MediaItem.LiveConfiguration) Util.castNonNull(window.o));
        if (j2 != -9223372036854775807L) {
            livePlaybackSpeedControl.setTargetLiveOffsetOverrideUs(f(timeline, obj2, j2));
            return;
        }
        Object obj3 = window.c;
        if (!timeline2.isEmpty()) {
            obj = timeline2.getWindow(timeline2.getPeriodByUid(mediaPeriodId2.a, period).g, window).c;
        } else {
            obj = null;
        }
        if (!Util.areEqual(obj, obj3)) {
            livePlaybackSpeedControl.setTargetLiveOffsetOverrideUs(-9223372036854775807L);
        }
    }

    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x00cc A[EDGE_INSN: B:111:0x00cc->B:39:0x00cc ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00ae  */
    public final void bc() throws com.google.android.exoplayer2.ExoPlaybackException {
        /*
            r18 = this;
            r11 = r18
            com.google.android.exoplayer2.d r0 = r11.v
            b6 r0 = r0.getPlayingPeriod()
            if (r0 != 0) goto L_0x000b
            return
        L_0x000b:
            boolean r1 = r0.d
            r2 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            if (r1 == 0) goto L_0x001c
            com.google.android.exoplayer2.source.MediaPeriod r1 = r0.a
            long r4 = r1.readDiscontinuity()
            r7 = r4
            goto L_0x001d
        L_0x001c:
            r7 = r2
        L_0x001d:
            r12 = 0
            int r1 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r1 == 0) goto L_0x0041
            r11.aa(r7)
            s8 r0 = r11.aa
            long r0 = r0.s
            int r2 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
            if (r2 == 0) goto L_0x003e
            s8 r0 = r11.aa
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r2 = r0.b
            long r5 = r0.c
            r9 = 1
            r10 = 5
            r1 = r18
            r3 = r7
            s8 r0 = r1.n(r2, r3, r5, r7, r9, r10)
            r11.aa = r0
        L_0x003e:
            r14 = r11
            goto L_0x0173
        L_0x0041:
            com.google.android.exoplayer2.DefaultMediaClock r1 = r11.r
            com.google.android.exoplayer2.d r2 = r11.v
            b6 r2 = r2.getReadingPeriod()
            if (r0 == r2) goto L_0x004d
            r2 = 1
            goto L_0x004e
        L_0x004d:
            r2 = 0
        L_0x004e:
            long r1 = r1.syncAndGetPositionUs(r2)
            r11.ao = r1
            long r0 = r0.toPeriodTime(r1)
            s8 r2 = r11.aa
            long r2 = r2.s
            java.util.ArrayList<com.google.android.exoplayer2.ExoPlayerImplInternal$c> r4 = r11.s
            boolean r5 = r4.isEmpty()
            if (r5 != 0) goto L_0x016e
            s8 r5 = r11.aa
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r5 = r5.b
            boolean r5 = r5.isAd()
            if (r5 == 0) goto L_0x0070
            goto L_0x016e
        L_0x0070:
            boolean r5 = r11.aq
            if (r5 == 0) goto L_0x0079
            r5 = 1
            long r2 = r2 - r5
            r11.aq = r12
        L_0x0079:
            s8 r5 = r11.aa
            com.google.android.exoplayer2.Timeline r6 = r5.a
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r5 = r5.b
            java.lang.Object r5 = r5.a
            int r5 = r6.getIndexOfPeriod(r5)
            int r6 = r11.ap
            int r7 = r4.size()
            int r6 = java.lang.Math.min(r6, r7)
            if (r6 <= 0) goto L_0x00a1
            int r8 = r6 + -1
            java.lang.Object r8 = r4.get(r8)
            com.google.android.exoplayer2.ExoPlayerImplInternal$c r8 = (com.google.android.exoplayer2.ExoPlayerImplInternal.c) r8
            r10 = r5
            r9 = r6
            r13 = r11
            r14 = r13
            r6 = r4
            r4 = r2
            r2 = r0
            goto L_0x00ac
        L_0x00a1:
            r8 = r5
            r9 = r6
            r10 = r11
            r13 = r10
            r6 = r4
            r4 = r2
            r2 = r0
        L_0x00a8:
            r14 = r13
            r13 = r10
            r10 = r8
            r8 = 0
        L_0x00ac:
            if (r8 == 0) goto L_0x00cc
            int r15 = r8.f
            if (r15 > r10) goto L_0x00ba
            if (r15 != r10) goto L_0x00cc
            long r7 = r8.g
            int r16 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r16 <= 0) goto L_0x00cc
        L_0x00ba:
            int r9 = r9 + -1
            if (r9 <= 0) goto L_0x00c8
            int r7 = r9 + -1
            java.lang.Object r7 = r6.get(r7)
            r8 = r7
            com.google.android.exoplayer2.ExoPlayerImplInternal$c r8 = (com.google.android.exoplayer2.ExoPlayerImplInternal.c) r8
            goto L_0x00ac
        L_0x00c8:
            r8 = r10
            r10 = r13
            r13 = r14
            goto L_0x00a8
        L_0x00cc:
            int r7 = r6.size()
            if (r9 >= r7) goto L_0x00d9
            java.lang.Object r7 = r6.get(r9)
            com.google.android.exoplayer2.ExoPlayerImplInternal$c r7 = (com.google.android.exoplayer2.ExoPlayerImplInternal.c) r7
            goto L_0x00da
        L_0x00d9:
            r7 = 0
        L_0x00da:
            if (r7 == 0) goto L_0x0107
            java.lang.Object r8 = r7.h
            if (r8 == 0) goto L_0x0107
            int r8 = r7.f
            if (r8 < r10) goto L_0x00ef
            if (r8 != r10) goto L_0x0107
            r16 = r13
            long r12 = r7.g
            int r17 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
            if (r17 > 0) goto L_0x0109
            goto L_0x00f1
        L_0x00ef:
            r16 = r13
        L_0x00f1:
            int r9 = r9 + 1
            int r7 = r6.size()
            if (r9 >= r7) goto L_0x0103
            java.lang.Object r7 = r6.get(r9)
            com.google.android.exoplayer2.ExoPlayerImplInternal$c r7 = (com.google.android.exoplayer2.ExoPlayerImplInternal.c) r7
            r13 = r16
            r12 = 0
            goto L_0x00da
        L_0x0103:
            r13 = r16
            r12 = 0
            goto L_0x00d9
        L_0x0107:
            r16 = r13
        L_0x0109:
            if (r7 == 0) goto L_0x0168
            com.google.android.exoplayer2.PlayerMessage r12 = r7.c
            java.lang.Object r13 = r7.h
            if (r13 == 0) goto L_0x0168
            int r13 = r7.f
            if (r13 != r10) goto L_0x0168
            r17 = r9
            long r8 = r7.g
            int r7 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r7 <= 0) goto L_0x0163
            int r7 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
            if (r7 > 0) goto L_0x0163
            r7 = r16
            r7.ak(r12)     // Catch:{ all -> 0x014f }
            boolean r8 = r12.getDeleteAfterDelivery()
            if (r8 != 0) goto L_0x0136
            boolean r8 = r12.isCanceled()
            if (r8 == 0) goto L_0x0133
            goto L_0x0136
        L_0x0133:
            int r9 = r17 + 1
            goto L_0x013b
        L_0x0136:
            r9 = r17
            r6.remove(r9)
        L_0x013b:
            int r8 = r6.size()
            if (r9 >= r8) goto L_0x014b
            java.lang.Object r8 = r6.get(r9)
            com.google.android.exoplayer2.ExoPlayerImplInternal$c r8 = (com.google.android.exoplayer2.ExoPlayerImplInternal.c) r8
            r16 = r7
            r7 = r8
            goto L_0x0109
        L_0x014b:
            r16 = r7
            r7 = 0
            goto L_0x0109
        L_0x014f:
            r0 = move-exception
            r9 = r17
            r1 = r0
            boolean r0 = r12.getDeleteAfterDelivery()
            if (r0 != 0) goto L_0x015f
            boolean r0 = r12.isCanceled()
            if (r0 == 0) goto L_0x0162
        L_0x015f:
            r6.remove(r9)
        L_0x0162:
            throw r1
        L_0x0163:
            r7 = r16
            r9 = r17
            goto L_0x016a
        L_0x0168:
            r7 = r16
        L_0x016a:
            r7.ap = r9
            r0 = r2
            goto L_0x016f
        L_0x016e:
            r14 = r11
        L_0x016f:
            s8 r2 = r14.aa
            r2.s = r0
        L_0x0173:
            com.google.android.exoplayer2.d r0 = r14.v
            b6 r0 = r0.getLoadingPeriod()
            s8 r1 = r14.aa
            long r2 = r0.getBufferedPositionUs()
            r1.q = r2
            s8 r0 = r14.aa
            long r1 = r0.q
            com.google.android.exoplayer2.d r3 = r14.v
            b6 r3 = r3.getLoadingPeriod()
            r4 = 0
            if (r3 != 0) goto L_0x0191
            r1 = r4
            goto L_0x019c
        L_0x0191:
            long r6 = r14.ao
            long r6 = r3.toPeriodTime(r6)
            long r1 = r1 - r6
            long r1 = java.lang.Math.max(r4, r1)
        L_0x019c:
            r0.r = r1
            s8 r0 = r14.aa
            boolean r1 = r0.l
            if (r1 == 0) goto L_0x0212
            int r1 = r0.e
            r2 = 3
            if (r1 != r2) goto L_0x0212
            com.google.android.exoplayer2.Timeline r1 = r0.a
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r0 = r0.b
            boolean r0 = r14.aw(r1, r0)
            if (r0 == 0) goto L_0x0212
            s8 r0 = r14.aa
            com.google.android.exoplayer2.PlaybackParameters r1 = r0.n
            float r1 = r1.c
            r2 = 1065353216(0x3f800000, float:1.0)
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 != 0) goto L_0x0212
            com.google.android.exoplayer2.LivePlaybackSpeedControl r1 = r14.x
            com.google.android.exoplayer2.Timeline r2 = r0.a
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r3 = r0.b
            java.lang.Object r3 = r3.a
            long r6 = r0.s
            long r2 = r14.f(r2, r3, r6)
            s8 r0 = r14.aa
            long r6 = r0.q
            com.google.android.exoplayer2.d r0 = r14.v
            b6 r0 = r0.getLoadingPeriod()
            if (r0 != 0) goto L_0x01da
            goto L_0x01e5
        L_0x01da:
            long r8 = r14.ao
            long r8 = r0.toPeriodTime(r8)
            long r6 = r6 - r8
            long r4 = java.lang.Math.max(r4, r6)
        L_0x01e5:
            float r0 = r1.getAdjustedPlaybackSpeed(r2, r4)
            com.google.android.exoplayer2.DefaultMediaClock r1 = r14.r
            com.google.android.exoplayer2.PlaybackParameters r1 = r1.getPlaybackParameters()
            float r1 = r1.c
            int r1 = (r1 > r0 ? 1 : (r1 == r0 ? 0 : -1))
            if (r1 == 0) goto L_0x0212
            com.google.android.exoplayer2.DefaultMediaClock r1 = r14.r
            s8 r2 = r14.aa
            com.google.android.exoplayer2.PlaybackParameters r2 = r2.n
            com.google.android.exoplayer2.PlaybackParameters r0 = r2.withSpeed(r0)
            r1.setPlaybackParameters(r0)
            s8 r0 = r14.aa
            com.google.android.exoplayer2.PlaybackParameters r0 = r0.n
            com.google.android.exoplayer2.DefaultMediaClock r1 = r14.r
            com.google.android.exoplayer2.PlaybackParameters r1 = r1.getPlaybackParameters()
            float r1 = r1.c
            r2 = 0
            r14.m(r0, r1, r2, r2)
        L_0x0212:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ExoPlayerImplInternal.bc():void");
    }

    public final synchronized void bd(r2 r2Var, long j2) {
        long elapsedRealtime = this.t.elapsedRealtime() + j2;
        boolean z2 = false;
        while (!((Boolean) r2Var.get()).booleanValue() && j2 > 0) {
            try {
                this.t.onThreadBlocked();
                wait(j2);
            } catch (InterruptedException unused) {
                z2 = true;
            }
            j2 = elapsedRealtime - this.t.elapsedRealtime();
        }
        if (z2) {
            Thread.currentThread().interrupt();
        }
    }

    public final void c(Renderer renderer) throws ExoPlaybackException {
        boolean z2;
        if (renderer.getState() != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            this.r.onRendererDisabled(renderer);
            if (renderer.getState() == 2) {
                renderer.stop();
            }
            renderer.disable();
            this.am--;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:249:0x042f, code lost:
        if (r5.shouldStartPlayback(r21, r10.r.getPlaybackParameters().c, r10.af, r25) != false) goto L_0x0434;
     */
    /* JADX WARNING: Removed duplicated region for block: B:211:0x037e  */
    /* JADX WARNING: Removed duplicated region for block: B:212:0x038a  */
    /* JADX WARNING: Removed duplicated region for block: B:217:0x0394  */
    /* JADX WARNING: Removed duplicated region for block: B:218:0x039c  */
    /* JADX WARNING: Removed duplicated region for block: B:283:0x04af  */
    /* JADX WARNING: Removed duplicated region for block: B:302:0x04fb  */
    /* JADX WARNING: Removed duplicated region for block: B:307:0x050e  */
    /* JADX WARNING: Removed duplicated region for block: B:308:0x0510  */
    /* JADX WARNING: Removed duplicated region for block: B:314:0x051f  */
    /* JADX WARNING: Removed duplicated region for block: B:315:0x0521  */
    /* JADX WARNING: Removed duplicated region for block: B:326:0x054e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void d() throws com.google.android.exoplayer2.ExoPlaybackException, java.io.IOException {
        /*
            r27 = this;
            r10 = r27
            com.google.android.exoplayer2.util.Clock r0 = r10.t
            long r11 = r0.uptimeMillis()
            s8 r0 = r10.aa
            com.google.android.exoplayer2.Timeline r0 = r0.a
            boolean r0 = r0.isEmpty()
            r13 = 1
            r14 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r9 = 0
            if (r0 != 0) goto L_0x02ac
            com.google.android.exoplayer2.MediaSourceList r0 = r10.w
            boolean r0 = r0.isPrepared()
            if (r0 != 0) goto L_0x0023
            goto L_0x02ac
        L_0x0023:
            long r0 = r10.ao
            com.google.android.exoplayer2.d r8 = r10.v
            r8.reevaluateBuffer(r0)
            boolean r0 = r8.shouldLoadNextMediaPeriod()
            if (r0 == 0) goto L_0x0071
            long r0 = r10.ao
            s8 r2 = r10.aa
            c6 r0 = r8.getNextMediaPeriodInfo(r0, r2)
            if (r0 == 0) goto L_0x0071
            com.google.android.exoplayer2.d r1 = r10.v
            com.google.android.exoplayer2.RendererCapabilities[] r2 = r10.f
            com.google.android.exoplayer2.trackselection.TrackSelector r3 = r10.g
            com.google.android.exoplayer2.LoadControl r4 = r10.i
            com.google.android.exoplayer2.upstream.Allocator r19 = r4.getAllocator()
            com.google.android.exoplayer2.MediaSourceList r4 = r10.w
            com.google.android.exoplayer2.trackselection.TrackSelectorResult r5 = r10.h
            r16 = r1
            r17 = r2
            r18 = r3
            r20 = r4
            r21 = r0
            r22 = r5
            b6 r1 = r16.enqueueNextMediaPeriodHolder(r17, r18, r19, r20, r21, r22)
            com.google.android.exoplayer2.source.MediaPeriod r2 = r1.a
            long r3 = r0.b
            r2.prepare(r10, r3)
            b6 r0 = r8.getPlayingPeriod()
            if (r0 != r1) goto L_0x006e
            long r0 = r1.getStartPositionRendererTime()
            r10.aa(r0)
        L_0x006e:
            r10.j(r9)
        L_0x0071:
            boolean r0 = r10.ag
            if (r0 == 0) goto L_0x007f
            boolean r0 = r27.o()
            r10.ag = r0
            r27.ba()
            goto L_0x0082
        L_0x007f:
            r27.r()
        L_0x0082:
            b6 r0 = r8.getReadingPeriod()
            com.google.android.exoplayer2.Renderer[] r1 = r10.c
            if (r0 != 0) goto L_0x008c
            goto L_0x01ab
        L_0x008c:
            b6 r2 = r0.getNext()
            if (r2 == 0) goto L_0x0161
            boolean r2 = r10.ae
            if (r2 == 0) goto L_0x0098
            goto L_0x0161
        L_0x0098:
            b6 r2 = r8.getReadingPeriod()
            boolean r3 = r2.d
            if (r3 != 0) goto L_0x00a1
            goto L_0x00bd
        L_0x00a1:
            r3 = 0
        L_0x00a2:
            int r4 = r1.length
            if (r3 >= r4) goto L_0x00bf
            r4 = r1[r3]
            com.google.android.exoplayer2.source.SampleStream[] r5 = r2.c
            r5 = r5[r3]
            com.google.android.exoplayer2.source.SampleStream r6 = r4.getStream()
            if (r6 != r5) goto L_0x00bd
            if (r5 == 0) goto L_0x00ba
            boolean r4 = r4.hasReadStreamToEnd()
            if (r4 != 0) goto L_0x00ba
            goto L_0x00bd
        L_0x00ba:
            int r3 = r3 + 1
            goto L_0x00a2
        L_0x00bd:
            r2 = 0
            goto L_0x00c0
        L_0x00bf:
            r2 = 1
        L_0x00c0:
            if (r2 != 0) goto L_0x00c4
            goto L_0x01ab
        L_0x00c4:
            b6 r2 = r0.getNext()
            boolean r2 = r2.d
            if (r2 != 0) goto L_0x00dc
            long r2 = r10.ao
            b6 r4 = r0.getNext()
            long r4 = r4.getStartPositionRendererTime()
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 >= 0) goto L_0x00dc
            goto L_0x01ab
        L_0x00dc:
            com.google.android.exoplayer2.trackselection.TrackSelectorResult r0 = r0.getTrackSelectorResult()
            b6 r2 = r8.advanceReadingPeriod()
            com.google.android.exoplayer2.trackselection.TrackSelectorResult r3 = r2.getTrackSelectorResult()
            boolean r4 = r2.d
            if (r4 == 0) goto L_0x0115
            com.google.android.exoplayer2.source.MediaPeriod r4 = r2.a
            long r4 = r4.readDiscontinuity()
            int r6 = (r4 > r14 ? 1 : (r4 == r14 ? 0 : -1))
            if (r6 == 0) goto L_0x0115
            long r2 = r2.getStartPositionRendererTime()
            int r0 = r1.length
            r4 = 0
        L_0x00fc:
            if (r4 >= r0) goto L_0x01ab
            r5 = r1[r4]
            com.google.android.exoplayer2.source.SampleStream r6 = r5.getStream()
            if (r6 == 0) goto L_0x0112
            r5.setCurrentStreamFinal()
            boolean r6 = r5 instanceof com.google.android.exoplayer2.text.TextRenderer
            if (r6 == 0) goto L_0x0112
            com.google.android.exoplayer2.text.TextRenderer r5 = (com.google.android.exoplayer2.text.TextRenderer) r5
            r5.setFinalStreamEndPositionUs(r2)
        L_0x0112:
            int r4 = r4 + 1
            goto L_0x00fc
        L_0x0115:
            r4 = 0
        L_0x0116:
            int r5 = r1.length
            if (r4 >= r5) goto L_0x01ab
            boolean r5 = r0.isRendererEnabled(r4)
            boolean r6 = r3.isRendererEnabled(r4)
            if (r5 == 0) goto L_0x015d
            r5 = r1[r4]
            boolean r5 = r5.isCurrentStreamFinal()
            if (r5 != 0) goto L_0x015d
            com.google.android.exoplayer2.RendererCapabilities[] r5 = r10.f
            r5 = r5[r4]
            int r5 = r5.getTrackType()
            r7 = 7
            if (r5 != r7) goto L_0x0138
            r5 = 1
            goto L_0x0139
        L_0x0138:
            r5 = 0
        L_0x0139:
            com.google.android.exoplayer2.RendererConfiguration[] r7 = r0.b
            r7 = r7[r4]
            com.google.android.exoplayer2.RendererConfiguration[] r9 = r3.b
            r9 = r9[r4]
            if (r6 == 0) goto L_0x014b
            boolean r6 = r9.equals(r7)
            if (r6 == 0) goto L_0x014b
            if (r5 == 0) goto L_0x015d
        L_0x014b:
            r5 = r1[r4]
            long r6 = r2.getStartPositionRendererTime()
            r5.setCurrentStreamFinal()
            boolean r9 = r5 instanceof com.google.android.exoplayer2.text.TextRenderer
            if (r9 == 0) goto L_0x015d
            com.google.android.exoplayer2.text.TextRenderer r5 = (com.google.android.exoplayer2.text.TextRenderer) r5
            r5.setFinalStreamEndPositionUs(r6)
        L_0x015d:
            int r4 = r4 + 1
            r9 = 0
            goto L_0x0116
        L_0x0161:
            c6 r2 = r0.f
            boolean r2 = r2.h
            if (r2 != 0) goto L_0x016b
            boolean r2 = r10.ae
            if (r2 == 0) goto L_0x01ab
        L_0x016b:
            r2 = 0
        L_0x016c:
            int r3 = r1.length
            if (r2 >= r3) goto L_0x01ab
            r3 = r1[r2]
            com.google.android.exoplayer2.source.SampleStream[] r4 = r0.c
            r4 = r4[r2]
            if (r4 == 0) goto L_0x01a8
            com.google.android.exoplayer2.source.SampleStream r5 = r3.getStream()
            if (r5 != r4) goto L_0x01a8
            boolean r4 = r3.hasReadStreamToEnd()
            if (r4 == 0) goto L_0x01a8
            c6 r4 = r0.f
            long r4 = r4.e
            int r6 = (r4 > r14 ? 1 : (r4 == r14 ? 0 : -1))
            if (r6 == 0) goto L_0x019b
            r6 = -9223372036854775808
            int r9 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r9 == 0) goto L_0x019b
            long r4 = r0.getRendererOffset()
            c6 r6 = r0.f
            long r6 = r6.e
            long r4 = r4 + r6
            goto L_0x019c
        L_0x019b:
            r4 = r14
        L_0x019c:
            r3.setCurrentStreamFinal()
            boolean r6 = r3 instanceof com.google.android.exoplayer2.text.TextRenderer
            if (r6 == 0) goto L_0x01a8
            com.google.android.exoplayer2.text.TextRenderer r3 = (com.google.android.exoplayer2.text.TextRenderer) r3
            r3.setFinalStreamEndPositionUs(r4)
        L_0x01a8:
            int r2 = r2 + 1
            goto L_0x016c
        L_0x01ab:
            b6 r0 = r8.getReadingPeriod()
            if (r0 == 0) goto L_0x023a
            b6 r2 = r8.getPlayingPeriod()
            if (r2 == r0) goto L_0x023a
            boolean r0 = r0.g
            if (r0 == 0) goto L_0x01bd
            goto L_0x023a
        L_0x01bd:
            b6 r0 = r8.getReadingPeriod()
            com.google.android.exoplayer2.trackselection.TrackSelectorResult r2 = r0.getTrackSelectorResult()
            r3 = 0
            r4 = 0
        L_0x01c7:
            int r5 = r1.length
            if (r3 >= r5) goto L_0x022e
            r5 = r1[r3]
            boolean r6 = p(r5)
            if (r6 != 0) goto L_0x01d3
            goto L_0x0226
        L_0x01d3:
            com.google.android.exoplayer2.source.SampleStream r6 = r5.getStream()
            com.google.android.exoplayer2.source.SampleStream[] r7 = r0.c
            r9 = r7[r3]
            if (r6 == r9) goto L_0x01df
            r6 = 1
            goto L_0x01e0
        L_0x01df:
            r6 = 0
        L_0x01e0:
            boolean r9 = r2.isRendererEnabled(r3)
            if (r9 == 0) goto L_0x01e9
            if (r6 != 0) goto L_0x01e9
            goto L_0x0226
        L_0x01e9:
            boolean r6 = r5.isCurrentStreamFinal()
            if (r6 != 0) goto L_0x021b
            com.google.android.exoplayer2.trackselection.ExoTrackSelection[] r6 = r2.c
            r6 = r6[r3]
            if (r6 == 0) goto L_0x01fa
            int r9 = r6.length()
            goto L_0x01fb
        L_0x01fa:
            r9 = 0
        L_0x01fb:
            com.google.android.exoplayer2.Format[] r14 = new com.google.android.exoplayer2.Format[r9]
            r15 = 0
        L_0x01fe:
            if (r15 >= r9) goto L_0x0209
            com.google.android.exoplayer2.Format r17 = r6.getFormat(r15)
            r14[r15] = r17
            int r15 = r15 + 1
            goto L_0x01fe
        L_0x0209:
            r19 = r7[r3]
            long r20 = r0.getStartPositionRendererTime()
            long r22 = r0.getRendererOffset()
            r17 = r5
            r18 = r14
            r17.replaceStream(r18, r19, r20, r22)
            goto L_0x0226
        L_0x021b:
            boolean r6 = r5.isEnded()
            if (r6 == 0) goto L_0x0225
            r10.c(r5)
            goto L_0x0226
        L_0x0225:
            r4 = 1
        L_0x0226:
            int r3 = r3 + 1
            r14 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            goto L_0x01c7
        L_0x022e:
            r0 = r4 ^ 1
            if (r0 == 0) goto L_0x023a
            com.google.android.exoplayer2.Renderer[] r0 = r10.c
            int r0 = r0.length
            boolean[] r0 = new boolean[r0]
            r10.e(r0)
        L_0x023a:
            r0 = 0
        L_0x023b:
            boolean r1 = r27.av()
            if (r1 != 0) goto L_0x0242
            goto L_0x0264
        L_0x0242:
            boolean r1 = r10.ae
            if (r1 == 0) goto L_0x0247
            goto L_0x0264
        L_0x0247:
            b6 r1 = r8.getPlayingPeriod()
            if (r1 != 0) goto L_0x024e
            goto L_0x0264
        L_0x024e:
            b6 r1 = r1.getNext()
            if (r1 == 0) goto L_0x0264
            long r2 = r10.ao
            long r4 = r1.getStartPositionRendererTime()
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 < 0) goto L_0x0264
            boolean r1 = r1.g
            if (r1 == 0) goto L_0x0264
            r1 = 1
            goto L_0x0265
        L_0x0264:
            r1 = 0
        L_0x0265:
            if (r1 == 0) goto L_0x02ac
            if (r0 == 0) goto L_0x026c
            r27.s()
        L_0x026c:
            b6 r14 = r8.getPlayingPeriod()
            b6 r15 = r8.advancePlayingPeriod()
            c6 r0 = r15.f
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r1 = r0.a
            long r6 = r0.b
            long r4 = r0.c
            r9 = 1
            r17 = 0
            r0 = r27
            r2 = r6
            r18 = r8
            r8 = r9
            r9 = r17
            s8 r0 = r0.n(r1, r2, r4, r6, r8, r9)
            r10.aa = r0
            com.google.android.exoplayer2.Timeline r3 = r0.a
            c6 r0 = r15.f
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r2 = r0.a
            c6 r0 = r14.f
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r4 = r0.a
            r5 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r0 = r27
            r1 = r3
            r0.bb(r1, r2, r3, r4, r5)
            r27.z()
            r27.bc()
            r8 = r18
            r0 = 1
            goto L_0x023b
        L_0x02ac:
            s8 r0 = r10.aa
            int r0 = r0.e
            r1 = 2
            if (r0 == r13) goto L_0x055a
            r2 = 4
            if (r0 != r2) goto L_0x02b8
            goto L_0x055a
        L_0x02b8:
            com.google.android.exoplayer2.d r0 = r10.v
            b6 r0 = r0.getPlayingPeriod()
            r3 = 10
            if (r0 != 0) goto L_0x02cc
            com.google.android.exoplayer2.util.HandlerWrapper r0 = r10.k
            r0.removeMessages(r1)
            long r11 = r11 + r3
            r0.sendEmptyMessageAtTime(r1, r11)
            return
        L_0x02cc:
            java.lang.String r5 = "doSomeWork"
            com.google.android.exoplayer2.util.TraceUtil.beginSection(r5)
            r27.bc()
            boolean r5 = r0.d
            r6 = 1000(0x3e8, double:4.94E-321)
            if (r5 == 0) goto L_0x034e
            long r8 = android.os.SystemClock.elapsedRealtime()
            long r8 = r8 * r6
            com.google.android.exoplayer2.source.MediaPeriod r5 = r0.a
            s8 r14 = r10.aa
            long r14 = r14.s
            long r6 = r10.p
            long r14 = r14 - r6
            boolean r6 = r10.q
            r5.discardBuffer(r14, r6)
            r5 = 0
            r6 = 1
            r7 = 1
        L_0x02f1:
            com.google.android.exoplayer2.Renderer[] r14 = r10.c
            int r15 = r14.length
            if (r5 >= r15) goto L_0x0355
            r14 = r14[r5]
            boolean r15 = p(r14)
            if (r15 != 0) goto L_0x02ff
            goto L_0x0349
        L_0x02ff:
            long r3 = r10.ao
            r14.render(r3, r8)
            if (r6 == 0) goto L_0x030e
            boolean r3 = r14.isEnded()
            if (r3 == 0) goto L_0x030e
            r3 = 1
            goto L_0x030f
        L_0x030e:
            r3 = 0
        L_0x030f:
            com.google.android.exoplayer2.source.SampleStream[] r4 = r0.c
            r4 = r4[r5]
            com.google.android.exoplayer2.source.SampleStream r6 = r14.getStream()
            if (r4 == r6) goto L_0x031b
            r4 = 1
            goto L_0x031c
        L_0x031b:
            r4 = 0
        L_0x031c:
            if (r4 != 0) goto L_0x0326
            boolean r6 = r14.hasReadStreamToEnd()
            if (r6 == 0) goto L_0x0326
            r6 = 1
            goto L_0x0327
        L_0x0326:
            r6 = 0
        L_0x0327:
            if (r4 != 0) goto L_0x033a
            if (r6 != 0) goto L_0x033a
            boolean r4 = r14.isReady()
            if (r4 != 0) goto L_0x033a
            boolean r4 = r14.isEnded()
            if (r4 == 0) goto L_0x0338
            goto L_0x033a
        L_0x0338:
            r4 = 0
            goto L_0x033b
        L_0x033a:
            r4 = 1
        L_0x033b:
            if (r7 == 0) goto L_0x0341
            if (r4 == 0) goto L_0x0341
            r6 = 1
            goto L_0x0342
        L_0x0341:
            r6 = 0
        L_0x0342:
            if (r4 != 0) goto L_0x0347
            r14.maybeThrowStreamError()
        L_0x0347:
            r7 = r6
            r6 = r3
        L_0x0349:
            int r5 = r5 + 1
            r3 = 10
            goto L_0x02f1
        L_0x034e:
            com.google.android.exoplayer2.source.MediaPeriod r3 = r0.a
            r3.maybeThrowPrepareError()
            r6 = 1
            r7 = 1
        L_0x0355:
            c6 r3 = r0.f
            long r3 = r3.e
            if (r6 == 0) goto L_0x0372
            boolean r5 = r0.d
            if (r5 == 0) goto L_0x0372
            r5 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r8 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r8 == 0) goto L_0x0370
            s8 r8 = r10.aa
            long r8 = r8.s
            int r14 = (r3 > r8 ? 1 : (r3 == r8 ? 0 : -1))
            if (r14 > 0) goto L_0x0377
        L_0x0370:
            r9 = 1
            goto L_0x0378
        L_0x0372:
            r5 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
        L_0x0377:
            r9 = 0
        L_0x0378:
            if (r9 == 0) goto L_0x038a
            boolean r3 = r10.ae
            if (r3 == 0) goto L_0x038a
            r3 = 0
            r10.ae = r3
            s8 r4 = r10.aa
            int r4 = r4.m
            r8 = 5
            r10.aq(r4, r8, r3, r3)
            goto L_0x038b
        L_0x038a:
            r3 = 0
        L_0x038b:
            r4 = 3
            if (r9 == 0) goto L_0x039c
            c6 r8 = r0.f
            boolean r8 = r8.h
            if (r8 == 0) goto L_0x039c
            r10.au(r2)
            r27.az()
            goto L_0x04a8
        L_0x039c:
            s8 r8 = r10.aa
            int r9 = r8.e
            if (r9 != r1) goto L_0x045e
            int r9 = r10.am
            if (r9 != 0) goto L_0x03ac
            boolean r9 = r27.q()
            goto L_0x0435
        L_0x03ac:
            if (r7 != 0) goto L_0x03b0
            goto L_0x0432
        L_0x03b0:
            boolean r9 = r8.g
            if (r9 != 0) goto L_0x03b6
            goto L_0x0434
        L_0x03b6:
            com.google.android.exoplayer2.Timeline r8 = r8.a
            com.google.android.exoplayer2.d r9 = r10.v
            b6 r14 = r9.getPlayingPeriod()
            c6 r14 = r14.f
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r14 = r14.a
            boolean r8 = r10.aw(r8, r14)
            if (r8 == 0) goto L_0x03d1
            com.google.android.exoplayer2.LivePlaybackSpeedControl r5 = r10.x
            long r14 = r5.getTargetLiveOffsetUs()
            r25 = r14
            goto L_0x03d3
        L_0x03d1:
            r25 = r5
        L_0x03d3:
            b6 r5 = r9.getLoadingPeriod()
            boolean r6 = r5.isFullyBuffered()
            if (r6 == 0) goto L_0x03e5
            c6 r6 = r5.f
            boolean r6 = r6.h
            if (r6 == 0) goto L_0x03e5
            r9 = 1
            goto L_0x03e6
        L_0x03e5:
            r9 = 0
        L_0x03e6:
            c6 r6 = r5.f
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r6 = r6.a
            boolean r6 = r6.isAd()
            if (r6 == 0) goto L_0x03f6
            boolean r5 = r5.d
            if (r5 != 0) goto L_0x03f6
            r5 = 1
            goto L_0x03f7
        L_0x03f6:
            r5 = 0
        L_0x03f7:
            if (r9 != 0) goto L_0x0434
            if (r5 != 0) goto L_0x0434
            com.google.android.exoplayer2.LoadControl r5 = r10.i
            s8 r6 = r10.aa
            long r8 = r6.q
            com.google.android.exoplayer2.d r6 = r10.v
            b6 r6 = r6.getLoadingPeriod()
            r14 = 0
            if (r6 != 0) goto L_0x040e
            r21 = r14
            goto L_0x041b
        L_0x040e:
            long r1 = r10.ao
            long r1 = r6.toPeriodTime(r1)
            long r8 = r8 - r1
            long r1 = java.lang.Math.max(r14, r8)
            r21 = r1
        L_0x041b:
            com.google.android.exoplayer2.DefaultMediaClock r1 = r10.r
            com.google.android.exoplayer2.PlaybackParameters r1 = r1.getPlaybackParameters()
            float r1 = r1.c
            boolean r2 = r10.af
            r20 = r5
            r23 = r1
            r24 = r2
            boolean r1 = r20.shouldStartPlayback(r21, r23, r24, r25)
            if (r1 == 0) goto L_0x0432
            goto L_0x0434
        L_0x0432:
            r9 = 0
            goto L_0x0435
        L_0x0434:
            r9 = 1
        L_0x0435:
            if (r9 == 0) goto L_0x045e
            r10.au(r4)
            r1 = 0
            r10.ar = r1
            boolean r1 = r27.av()
            if (r1 == 0) goto L_0x04a8
            r10.af = r3
            com.google.android.exoplayer2.DefaultMediaClock r1 = r10.r
            r1.start()
            com.google.android.exoplayer2.Renderer[] r1 = r10.c
            int r2 = r1.length
            r9 = 0
        L_0x044e:
            if (r9 >= r2) goto L_0x04a8
            r5 = r1[r9]
            boolean r6 = p(r5)
            if (r6 == 0) goto L_0x045b
            r5.start()
        L_0x045b:
            int r9 = r9 + 1
            goto L_0x044e
        L_0x045e:
            s8 r1 = r10.aa
            int r1 = r1.e
            if (r1 != r4) goto L_0x04a8
            int r1 = r10.am
            if (r1 != 0) goto L_0x046f
            boolean r1 = r27.q()
            if (r1 == 0) goto L_0x0471
            goto L_0x04a8
        L_0x046f:
            if (r7 != 0) goto L_0x04a8
        L_0x0471:
            boolean r1 = r27.av()
            r10.af = r1
            r1 = 2
            r10.au(r1)
            boolean r1 = r10.af
            if (r1 == 0) goto L_0x04a5
            com.google.android.exoplayer2.d r1 = r10.v
            b6 r1 = r1.getPlayingPeriod()
        L_0x0485:
            if (r1 == 0) goto L_0x04a0
            com.google.android.exoplayer2.trackselection.TrackSelectorResult r2 = r1.getTrackSelectorResult()
            com.google.android.exoplayer2.trackselection.ExoTrackSelection[] r2 = r2.c
            int r5 = r2.length
            r9 = 0
        L_0x048f:
            if (r9 >= r5) goto L_0x049b
            r6 = r2[r9]
            if (r6 == 0) goto L_0x0498
            r6.onRebuffer()
        L_0x0498:
            int r9 = r9 + 1
            goto L_0x048f
        L_0x049b:
            b6 r1 = r1.getNext()
            goto L_0x0485
        L_0x04a0:
            com.google.android.exoplayer2.LivePlaybackSpeedControl r1 = r10.x
            r1.notifyRebuffer()
        L_0x04a5:
            r27.az()
        L_0x04a8:
            s8 r1 = r10.aa
            int r1 = r1.e
            r2 = 2
            if (r1 != r2) goto L_0x04f3
            r9 = 0
        L_0x04b0:
            com.google.android.exoplayer2.Renderer[] r1 = r10.c
            int r2 = r1.length
            if (r9 >= r2) goto L_0x04d5
            r1 = r1[r9]
            boolean r1 = p(r1)
            if (r1 == 0) goto L_0x04d2
            com.google.android.exoplayer2.Renderer[] r1 = r10.c
            r1 = r1[r9]
            com.google.android.exoplayer2.source.SampleStream r1 = r1.getStream()
            com.google.android.exoplayer2.source.SampleStream[] r2 = r0.c
            r2 = r2[r9]
            if (r1 != r2) goto L_0x04d2
            com.google.android.exoplayer2.Renderer[] r1 = r10.c
            r1 = r1[r9]
            r1.maybeThrowStreamError()
        L_0x04d2:
            int r9 = r9 + 1
            goto L_0x04b0
        L_0x04d5:
            s8 r0 = r10.aa
            boolean r1 = r0.g
            if (r1 != 0) goto L_0x04f3
            long r0 = r0.r
            r5 = 500000(0x7a120, double:2.47033E-318)
            int r2 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r2 >= 0) goto L_0x04f3
            boolean r0 = r27.o()
            if (r0 != 0) goto L_0x04eb
            goto L_0x04f3
        L_0x04eb:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Playback stuck buffering and not loading"
            r0.<init>(r1)
            throw r0
        L_0x04f3:
            boolean r0 = r10.al
            s8 r1 = r10.aa
            boolean r2 = r1.o
            if (r0 == r2) goto L_0x0501
            s8 r0 = r1.copyWithOffloadSchedulingEnabled(r0)
            r10.aa = r0
        L_0x0501:
            boolean r0 = r27.av()
            if (r0 == 0) goto L_0x0510
            s8 r0 = r10.aa
            int r0 = r0.e
            if (r0 == r4) goto L_0x050e
            goto L_0x0510
        L_0x050e:
            r1 = 2
            goto L_0x0517
        L_0x0510:
            s8 r0 = r10.aa
            int r0 = r0.e
            r1 = 2
            if (r0 != r1) goto L_0x052f
        L_0x0517:
            boolean r0 = r10.al
            if (r0 == 0) goto L_0x0521
            boolean r0 = r10.ak
            if (r0 == 0) goto L_0x0521
            r9 = 0
            goto L_0x052d
        L_0x0521:
            com.google.android.exoplayer2.util.HandlerWrapper r0 = r10.k
            r0.removeMessages(r1)
            r4 = 10
            long r11 = r11 + r4
            r0.sendEmptyMessageAtTime(r1, r11)
            r9 = 1
        L_0x052d:
            r9 = r9 ^ r13
            goto L_0x0548
        L_0x052f:
            int r2 = r10.am
            if (r2 == 0) goto L_0x0542
            r2 = 4
            if (r0 == r2) goto L_0x0542
            com.google.android.exoplayer2.util.HandlerWrapper r0 = r10.k
            r0.removeMessages(r1)
            r4 = 1000(0x3e8, double:4.94E-321)
            long r11 = r11 + r4
            r0.sendEmptyMessageAtTime(r1, r11)
            goto L_0x0547
        L_0x0542:
            com.google.android.exoplayer2.util.HandlerWrapper r0 = r10.k
            r0.removeMessages(r1)
        L_0x0547:
            r9 = 0
        L_0x0548:
            s8 r0 = r10.aa
            boolean r1 = r0.p
            if (r1 == r9) goto L_0x0554
            s8 r0 = r0.copyWithSleepingForOffload(r9)
            r10.aa = r0
        L_0x0554:
            r10.ak = r3
            com.google.android.exoplayer2.util.TraceUtil.endSection()
            return
        L_0x055a:
            com.google.android.exoplayer2.util.HandlerWrapper r0 = r10.k
            r1 = 2
            r0.removeMessages(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ExoPlayerImplInternal.d():void");
    }

    public final void e(boolean[] zArr) throws ExoPlaybackException {
        Renderer[] rendererArr;
        int i2;
        boolean z2;
        int i3;
        boolean z3;
        boolean z4;
        d dVar = this.v;
        b6 readingPeriod = dVar.getReadingPeriod();
        TrackSelectorResult trackSelectorResult = readingPeriod.getTrackSelectorResult();
        int i4 = 0;
        while (true) {
            rendererArr = this.c;
            if (i4 >= rendererArr.length) {
                break;
            }
            if (!trackSelectorResult.isRendererEnabled(i4)) {
                rendererArr[i4].reset();
            }
            i4++;
        }
        int i5 = 0;
        while (i5 < rendererArr.length) {
            if (trackSelectorResult.isRendererEnabled(i5)) {
                boolean z5 = zArr[i5];
                Renderer renderer = rendererArr[i5];
                if (!p(renderer)) {
                    b6 readingPeriod2 = dVar.getReadingPeriod();
                    if (readingPeriod2 == dVar.getPlayingPeriod()) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    TrackSelectorResult trackSelectorResult2 = readingPeriod2.getTrackSelectorResult();
                    RendererConfiguration rendererConfiguration = trackSelectorResult2.b[i5];
                    ExoTrackSelection exoTrackSelection = trackSelectorResult2.c[i5];
                    if (exoTrackSelection != null) {
                        i3 = exoTrackSelection.length();
                    } else {
                        i3 = 0;
                    }
                    Format[] formatArr = new Format[i3];
                    for (int i6 = 0; i6 < i3; i6++) {
                        formatArr[i6] = exoTrackSelection.getFormat(i6);
                    }
                    if (!av() || this.aa.e != 3) {
                        z3 = false;
                    } else {
                        z3 = true;
                    }
                    if (z5 || !z3) {
                        z4 = false;
                    } else {
                        z4 = true;
                    }
                    this.am++;
                    SampleStream sampleStream = readingPeriod2.c[i5];
                    i2 = i5;
                    RendererConfiguration rendererConfiguration2 = rendererConfiguration;
                    Format[] formatArr2 = formatArr;
                    long j2 = this.ao;
                    Renderer renderer2 = renderer;
                    renderer.enable(rendererConfiguration2, formatArr2, sampleStream, j2, z4, z2, readingPeriod2.getStartPositionRendererTime(), readingPeriod2.getRendererOffset());
                    renderer2.handleMessage(103, new b(this));
                    this.r.onRendererEnabled(renderer2);
                    if (z3) {
                        renderer2.start();
                    }
                    i5 = i2 + 1;
                }
            }
            i2 = i5;
            i5 = i2 + 1;
        }
        readingPeriod.g = true;
    }

    public void experimentalSetForegroundModeTimeoutMs(long j2) {
        this.as = j2;
    }

    public void experimentalSetOffloadSchedulingEnabled(boolean z2) {
        this.k.obtainMessage(24, z2 ? 1 : 0, 0).sendToTarget();
    }

    public final long f(Timeline timeline, Object obj, long j2) {
        Timeline.Period period = this.o;
        int i2 = timeline.getPeriodByUid(obj, period).g;
        Timeline.Window window = this.n;
        timeline.getWindow(i2, window);
        if (window.j == -9223372036854775807L || !window.isLive() || !window.m) {
            return -9223372036854775807L;
        }
        return C.msToUs(window.getCurrentUnixTimeMs() - window.j) - (period.getPositionInWindowUs() + j2);
    }

    public final long g() {
        b6 readingPeriod = this.v.getReadingPeriod();
        if (readingPeriod == null) {
            return 0;
        }
        long rendererOffset = readingPeriod.getRendererOffset();
        if (!readingPeriod.d) {
            return rendererOffset;
        }
        int i2 = 0;
        while (true) {
            Renderer[] rendererArr = this.c;
            if (i2 >= rendererArr.length) {
                return rendererOffset;
            }
            if (p(rendererArr[i2]) && rendererArr[i2].getStream() == readingPeriod.c[i2]) {
                long readingPositionUs = rendererArr[i2].getReadingPositionUs();
                if (readingPositionUs == Long.MIN_VALUE) {
                    return Long.MIN_VALUE;
                }
                rendererOffset = Math.max(readingPositionUs, rendererOffset);
            }
            i2++;
        }
    }

    public Looper getPlaybackLooper() {
        return this.m;
    }

    public final Pair<MediaSource.MediaPeriodId, Long> h(Timeline timeline) {
        long j2 = 0;
        if (timeline.isEmpty()) {
            return Pair.create(s8.getDummyPeriodForEmptyTimeline(), 0L);
        }
        Timeline timeline2 = timeline;
        Pair<Object, Long> periodPosition = timeline2.getPeriodPosition(this.n, this.o, timeline.getFirstWindowIndex(this.ai), -9223372036854775807L);
        MediaSource.MediaPeriodId resolveMediaPeriodIdForAds = this.v.resolveMediaPeriodIdForAds(timeline, periodPosition.first, 0);
        long longValue = ((Long) periodPosition.second).longValue();
        if (resolveMediaPeriodIdForAds.isAd()) {
            Object obj = resolveMediaPeriodIdForAds.a;
            Timeline.Period period = this.o;
            timeline.getPeriodByUid(obj, period);
            if (resolveMediaPeriodIdForAds.c == period.getFirstAdIndexToPlay(resolveMediaPeriodIdForAds.b)) {
                j2 = period.getAdResumePositionUs();
            }
            longValue = j2;
        }
        return Pair.create(resolveMediaPeriodIdForAds, Long.valueOf(longValue));
    }

    public boolean handleMessage(Message message) {
        b6 readingPeriod;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        d dVar = this.v;
        try {
            switch (message.what) {
                case 0:
                    u();
                    break;
                case 1:
                    if (message.arg1 != 0) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    aq(message.arg2, 1, z2, true);
                    break;
                case 2:
                    d();
                    break;
                case 3:
                    ah((e) message.obj);
                    break;
                case 4:
                    DefaultMediaClock defaultMediaClock = this.r;
                    defaultMediaClock.setPlaybackParameters((PlaybackParameters) message.obj);
                    PlaybackParameters playbackParameters = defaultMediaClock.getPlaybackParameters();
                    m(playbackParameters, playbackParameters.c, true, true);
                    break;
                case 5:
                    this.z = (SeekParameters) message.obj;
                    break;
                case 6:
                    ay(false, true);
                    break;
                case 7:
                    v();
                    return true;
                case 8:
                    l((MediaPeriod) message.obj);
                    break;
                case 9:
                    i((MediaPeriod) message.obj);
                    break;
                case 10:
                    x();
                    break;
                case 11:
                    ar(message.arg1);
                    break;
                case 12:
                    if (message.arg1 != 0) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    as(z3);
                    break;
                case 13:
                    if (message.arg1 != 0) {
                        z4 = true;
                    } else {
                        z4 = false;
                    }
                    am(z4, (AtomicBoolean) message.obj);
                    break;
                case 14:
                    aj((PlayerMessage) message.obj);
                    break;
                case 15:
                    al((PlayerMessage) message.obj);
                    break;
                case 16:
                    PlaybackParameters playbackParameters2 = (PlaybackParameters) message.obj;
                    m(playbackParameters2, playbackParameters2.c, true, false);
                    break;
                case 17:
                    an((a) message.obj);
                    break;
                case 18:
                    a((a) message.obj, message.arg1);
                    break;
                case 19:
                    t((b) message.obj);
                    break;
                case 20:
                    w(message.arg1, message.arg2, (ShuffleOrder) message.obj);
                    break;
                case 21:
                    at((ShuffleOrder) message.obj);
                    break;
                case 22:
                    k(this.w.createTimeline(), true);
                    break;
                case 23:
                    if (message.arg1 != 0) {
                        z5 = true;
                    } else {
                        z5 = false;
                    }
                    ap(z5);
                    break;
                case 24:
                    if (message.arg1 == 1) {
                        z6 = true;
                    } else {
                        z6 = false;
                    }
                    ao(z6);
                    break;
                case 25:
                    ag(true);
                    break;
                default:
                    return false;
            }
            s();
        } catch (ExoPlaybackException e2) {
            e = e2;
            if (e.c == 1 && (readingPeriod = dVar.getReadingPeriod()) != null) {
                e = e.a(readingPeriod.f.a);
            }
            if (!e.l || this.ar != null) {
                ExoPlaybackException exoPlaybackException = this.ar;
                if (exoPlaybackException != null) {
                    exoPlaybackException.addSuppressed(e);
                    e = this.ar;
                }
                Log.e("ExoPlayerImplInternal", "Playback error", e);
                ay(true, false);
                this.aa = this.aa.copyWithPlaybackError(e);
            } else {
                Log.w("ExoPlayerImplInternal", "Recoverable renderer error", e);
                this.ar = e;
                HandlerWrapper handlerWrapper = this.k;
                handlerWrapper.sendMessageAtFrontOfQueue(handlerWrapper.obtainMessage(25, e));
            }
            s();
        } catch (IOException e3) {
            ExoPlaybackException createForSource = ExoPlaybackException.createForSource(e3);
            b6 playingPeriod = dVar.getPlayingPeriod();
            if (playingPeriod != null) {
                createForSource = createForSource.a(playingPeriod.f.a);
            }
            Log.e("ExoPlayerImplInternal", "Playback error", createForSource);
            ay(false, false);
            this.aa = this.aa.copyWithPlaybackError(createForSource);
            s();
        } catch (RuntimeException e4) {
            ExoPlaybackException createForUnexpected = ExoPlaybackException.createForUnexpected(e4);
            Log.e("ExoPlayerImplInternal", "Playback error", createForUnexpected);
            ay(true, false);
            this.aa = this.aa.copyWithPlaybackError(createForUnexpected);
            s();
        }
        return true;
    }

    public final void i(MediaPeriod mediaPeriod) {
        d dVar = this.v;
        if (dVar.isLoading(mediaPeriod)) {
            dVar.reevaluateBuffer(this.ao);
            r();
        }
    }

    public final void j(boolean z2) {
        MediaSource.MediaPeriodId mediaPeriodId;
        long j2;
        b6 loadingPeriod = this.v.getLoadingPeriod();
        if (loadingPeriod == null) {
            mediaPeriodId = this.aa.b;
        } else {
            mediaPeriodId = loadingPeriod.f.a;
        }
        boolean z3 = !this.aa.k.equals(mediaPeriodId);
        if (z3) {
            this.aa = this.aa.copyWithLoadingMediaPeriodId(mediaPeriodId);
        }
        s8 s8Var = this.aa;
        if (loadingPeriod == null) {
            j2 = s8Var.s;
        } else {
            j2 = loadingPeriod.getBufferedPositionUs();
        }
        s8Var.q = j2;
        s8 s8Var2 = this.aa;
        long j3 = s8Var2.q;
        b6 loadingPeriod2 = this.v.getLoadingPeriod();
        long j4 = 0;
        if (loadingPeriod2 != null) {
            j4 = Math.max(0, j3 - loadingPeriod2.toPeriodTime(this.ao));
        }
        s8Var2.r = j4;
        if ((z3 || z2) && loadingPeriod != null && loadingPeriod.d) {
            this.i.onTracksSelected(this.c, loadingPeriod.getTrackGroups(), loadingPeriod.getTrackSelectorResult().c);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r23v5, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v5, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r23v6, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v6, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v7, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r23v7, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v8, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r23v8, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r23v9, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v9, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v12, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r23v12, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r23v13, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r23v14, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r23v15, resolved type: long} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r23v18, resolved type: boolean} */
    /* JADX WARNING: type inference failed for: r18v16 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x0303  */
    /* JADX WARNING: Removed duplicated region for block: B:166:0x0345  */
    /* JADX WARNING: Removed duplicated region for block: B:170:0x036f  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0148  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0164  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0179  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x017b  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0197  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x019e  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x01c0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void k(com.google.android.exoplayer2.Timeline r31, boolean r32) throws com.google.android.exoplayer2.ExoPlaybackException {
        /*
            r30 = this;
            r11 = r30
            r12 = r31
            s8 r0 = r11.aa
            com.google.android.exoplayer2.ExoPlayerImplInternal$e r8 = r11.an
            com.google.android.exoplayer2.d r9 = r11.v
            int r4 = r11.ah
            boolean r10 = r11.ai
            com.google.android.exoplayer2.Timeline$Window r13 = r11.n
            com.google.android.exoplayer2.Timeline$Period r14 = r11.o
            boolean r1 = r31.isEmpty()
            r15 = 4
            r7 = -1
            r16 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            if (r1 == 0) goto L_0x003b
            com.google.android.exoplayer2.ExoPlayerImplInternal$d r0 = new com.google.android.exoplayer2.ExoPlayerImplInternal$d
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r19 = defpackage.s8.getDummyPeriodForEmptyTimeline()
            r20 = 0
            r22 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r24 = 0
            r25 = 1
            r26 = 0
            r18 = r0
            r18.<init>(r19, r20, r22, r24, r25, r26)
            r7 = r0
            r15 = -1
            goto L_0x01cc
        L_0x003b:
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r3 = r0.b
            java.lang.Object r2 = r3.a
            boolean r1 = ax(r0, r14)
            if (r1 == 0) goto L_0x0048
            long r5 = r0.c
            goto L_0x004a
        L_0x0048:
            long r5 = r0.s
        L_0x004a:
            r20 = r5
            if (r8 == 0) goto L_0x009b
            r5 = 1
            r1 = r31
            r6 = r2
            r2 = r8
            r27 = r3
            r3 = r5
            r5 = r10
            r28 = r6
            r6 = r13
            r7 = r14
            android.util.Pair r1 = ae(r1, r2, r3, r4, r5, r6, r7)
            if (r1 != 0) goto L_0x006b
            int r1 = r12.getFirstWindowIndex(r10)
            r2 = r28
            r3 = 0
            r5 = 0
            r6 = 1
            goto L_0x0094
        L_0x006b:
            long r2 = r8.c
            int r4 = (r2 > r16 ? 1 : (r2 == r16 ? 0 : -1))
            if (r4 != 0) goto L_0x007d
            java.lang.Object r1 = r1.first
            com.google.android.exoplayer2.Timeline$Period r1 = r12.getPeriodByUid(r1, r14)
            int r7 = r1.g
            r2 = r28
            r6 = 0
            goto L_0x0089
        L_0x007d:
            java.lang.Object r2 = r1.first
            java.lang.Object r1 = r1.second
            java.lang.Long r1 = (java.lang.Long) r1
            long r20 = r1.longValue()
            r6 = 1
            r7 = -1
        L_0x0089:
            int r1 = r0.e
            if (r1 != r15) goto L_0x008f
            r1 = 1
            goto L_0x0090
        L_0x008f:
            r1 = 0
        L_0x0090:
            r3 = r1
            r5 = r6
            r1 = r7
            r6 = 0
        L_0x0094:
            r15 = -1
            r29 = r5
            r5 = r3
            r3 = r29
            goto L_0x00d9
        L_0x009b:
            r28 = r2
            r27 = r3
            com.google.android.exoplayer2.Timeline r2 = r0.a
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L_0x00b0
            int r1 = r12.getFirstWindowIndex(r10)
            r7 = r1
            r8 = r28
            r15 = -1
            goto L_0x00f2
        L_0x00b0:
            r8 = r28
            int r2 = r12.getIndexOfPeriod(r8)
            r7 = -1
            if (r2 != r7) goto L_0x00e4
            com.google.android.exoplayer2.Timeline r6 = r0.a
            r1 = r13
            r2 = r14
            r3 = r4
            r4 = r10
            r5 = r8
            r15 = -1
            r7 = r31
            java.lang.Object r1 = af(r1, r2, r3, r4, r5, r6, r7)
            if (r1 != 0) goto L_0x00cf
            int r1 = r12.getFirstWindowIndex(r10)
            r6 = 1
            goto L_0x00d6
        L_0x00cf:
            com.google.android.exoplayer2.Timeline$Period r1 = r12.getPeriodByUid(r1, r14)
            int r1 = r1.g
            r6 = 0
        L_0x00d6:
            r2 = r8
            r3 = 0
            r5 = 0
        L_0x00d9:
            r4 = r1
            r25 = r5
            r26 = r6
            r7 = r27
            r27 = r3
            goto L_0x0146
        L_0x00e4:
            r15 = -1
            if (r1 == 0) goto L_0x013b
            int r1 = (r20 > r16 ? 1 : (r20 == r16 ? 0 : -1))
            if (r1 != 0) goto L_0x00f6
            com.google.android.exoplayer2.Timeline$Period r1 = r12.getPeriodByUid(r8, r14)
            int r1 = r1.g
            r7 = r1
        L_0x00f2:
            r1 = r7
            r7 = r27
            goto L_0x013e
        L_0x00f6:
            com.google.android.exoplayer2.Timeline r1 = r0.a
            r7 = r27
            java.lang.Object r2 = r7.a
            r1.getPeriodByUid(r2, r14)
            com.google.android.exoplayer2.Timeline r1 = r0.a
            int r2 = r14.g
            com.google.android.exoplayer2.Timeline$Window r1 = r1.getWindow(r2, r13)
            int r1 = r1.s
            com.google.android.exoplayer2.Timeline r2 = r0.a
            java.lang.Object r3 = r7.a
            int r2 = r2.getIndexOfPeriod(r3)
            if (r1 != r2) goto L_0x0132
            long r1 = r14.getPositionInWindowUs()
            long r5 = r1 + r20
            com.google.android.exoplayer2.Timeline$Period r1 = r12.getPeriodByUid(r8, r14)
            int r4 = r1.g
            r1 = r31
            r2 = r13
            r3 = r14
            android.util.Pair r1 = r1.getPeriodPosition(r2, r3, r4, r5)
            java.lang.Object r2 = r1.first
            java.lang.Object r1 = r1.second
            java.lang.Long r1 = (java.lang.Long) r1
            long r20 = r1.longValue()
            goto L_0x0133
        L_0x0132:
            r2 = r8
        L_0x0133:
            r4 = -1
            r25 = 0
            r26 = 0
            r27 = 1
            goto L_0x0146
        L_0x013b:
            r7 = r27
            r1 = -1
        L_0x013e:
            r4 = r1
            r2 = r8
            r25 = 0
            r26 = 0
            r27 = 0
        L_0x0146:
            if (r4 == r15) goto L_0x0164
            r5 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r1 = r31
            r2 = r13
            r3 = r14
            android.util.Pair r1 = r1.getPeriodPosition(r2, r3, r4, r5)
            java.lang.Object r2 = r1.first
            java.lang.Object r1 = r1.second
            java.lang.Long r1 = (java.lang.Long) r1
            long r20 = r1.longValue()
            r23 = r16
            r3 = r20
            goto L_0x0168
        L_0x0164:
            r3 = r20
            r23 = r3
        L_0x0168:
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r1 = r9.resolveMediaPeriodIdForAds(r12, r2, r3)
            int r5 = r1.e
            if (r5 == r15) goto L_0x017b
            int r5 = r7.e
            if (r5 == r15) goto L_0x0179
            int r6 = r1.b
            if (r6 < r5) goto L_0x0179
            goto L_0x017b
        L_0x0179:
            r6 = 0
            goto L_0x017c
        L_0x017b:
            r6 = 1
        L_0x017c:
            java.lang.Object r5 = r7.a
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x0194
            boolean r2 = r7.isAd()
            if (r2 != 0) goto L_0x0194
            boolean r2 = r1.isAd()
            if (r2 != 0) goto L_0x0194
            if (r6 == 0) goto L_0x0194
            r6 = 1
            goto L_0x0195
        L_0x0194:
            r6 = 0
        L_0x0195:
            if (r6 == 0) goto L_0x0198
            r1 = r7
        L_0x0198:
            boolean r2 = r1.isAd()
            if (r2 == 0) goto L_0x01c0
            boolean r2 = r1.equals(r7)
            if (r2 == 0) goto L_0x01a7
            long r2 = r0.s
            goto L_0x01bd
        L_0x01a7:
            java.lang.Object r0 = r1.a
            r12.getPeriodByUid(r0, r14)
            int r0 = r1.c
            int r2 = r1.b
            int r2 = r14.getFirstAdIndexToPlay(r2)
            if (r0 != r2) goto L_0x01bb
            long r2 = r14.getAdResumePositionUs()
            goto L_0x01bd
        L_0x01bb:
            r2 = 0
        L_0x01bd:
            r21 = r2
            goto L_0x01c2
        L_0x01c0:
            r21 = r3
        L_0x01c2:
            com.google.android.exoplayer2.ExoPlayerImplInternal$d r0 = new com.google.android.exoplayer2.ExoPlayerImplInternal$d
            r19 = r0
            r20 = r1
            r19.<init>(r20, r21, r23, r25, r26, r27)
            r7 = r0
        L_0x01cc:
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r8 = r7.a
            long r9 = r7.c
            boolean r6 = r7.d
            long r13 = r7.b
            s8 r0 = r11.aa
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r0 = r0.b
            boolean r0 = r0.equals(r8)
            if (r0 == 0) goto L_0x01ea
            s8 r0 = r11.aa
            long r0 = r0.s
            int r2 = (r13 > r0 ? 1 : (r13 == r0 ? 0 : -1))
            if (r2 == 0) goto L_0x01e7
            goto L_0x01ea
        L_0x01e7:
            r19 = 0
            goto L_0x01ec
        L_0x01ea:
            r19 = 1
        L_0x01ec:
            r5 = 0
            r20 = 3
            boolean r0 = r7.e     // Catch:{ all -> 0x02f2 }
            if (r0 == 0) goto L_0x0205
            s8 r0 = r11.aa     // Catch:{ all -> 0x02f2 }
            int r0 = r0.e     // Catch:{ all -> 0x02f2 }
            r3 = 1
            if (r0 == r3) goto L_0x01ff
            r4 = 4
            r11.au(r4)     // Catch:{ all -> 0x02f2 }
            goto L_0x0200
        L_0x01ff:
            r4 = 4
        L_0x0200:
            r2 = 0
            r11.y(r2, r2, r2, r3)     // Catch:{ all -> 0x02f2 }
            goto L_0x0208
        L_0x0205:
            r2 = 0
            r3 = 1
            r4 = 4
        L_0x0208:
            if (r19 != 0) goto L_0x022f
            com.google.android.exoplayer2.d r1 = r11.v     // Catch:{ all -> 0x0226 }
            long r3 = r11.ao     // Catch:{ all -> 0x0226 }
            long r21 = r30.g()     // Catch:{ all -> 0x0226 }
            r6 = 0
            r2 = r31
            r18 = 4
            r23 = 1
            r15 = 0
            r5 = r21
            boolean r0 = r1.updateQueuedPeriods(r2, r3, r5)     // Catch:{ all -> 0x02ef }
            if (r0 != 0) goto L_0x0273
            r11.ag(r15)     // Catch:{ all -> 0x02ef }
            goto L_0x0273
        L_0x0226:
            r0 = move-exception
            r15 = 0
            r18 = 4
            r23 = 1
        L_0x022c:
            r6 = 0
            goto L_0x02f9
        L_0x022f:
            r15 = 0
            r18 = 4
            r23 = 1
            boolean r0 = r31.isEmpty()     // Catch:{ all -> 0x02ef }
            if (r0 != 0) goto L_0x0273
            com.google.android.exoplayer2.d r0 = r11.v     // Catch:{ all -> 0x02ef }
            b6 r0 = r0.getPlayingPeriod()     // Catch:{ all -> 0x02ef }
        L_0x0240:
            if (r0 == 0) goto L_0x025b
            c6 r1 = r0.f     // Catch:{ all -> 0x02ef }
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r1 = r1.a     // Catch:{ all -> 0x02ef }
            boolean r1 = r1.equals(r8)     // Catch:{ all -> 0x02ef }
            if (r1 == 0) goto L_0x0256
            com.google.android.exoplayer2.d r1 = r11.v     // Catch:{ all -> 0x02ef }
            c6 r2 = r0.f     // Catch:{ all -> 0x02ef }
            c6 r1 = r1.getUpdatedMediaPeriodInfo(r12, r2)     // Catch:{ all -> 0x02ef }
            r0.f = r1     // Catch:{ all -> 0x02ef }
        L_0x0256:
            b6 r0 = r0.getNext()     // Catch:{ all -> 0x02ef }
            goto L_0x0240
        L_0x025b:
            com.google.android.exoplayer2.d r0 = r11.v     // Catch:{ all -> 0x02ef }
            b6 r1 = r0.getPlayingPeriod()     // Catch:{ all -> 0x02ef }
            b6 r0 = r0.getReadingPeriod()     // Catch:{ all -> 0x02ef }
            if (r1 == r0) goto L_0x0269
            r5 = 1
            goto L_0x026a
        L_0x0269:
            r5 = 0
        L_0x026a:
            r1 = r30
            r2 = r8
            r3 = r13
            long r0 = r1.ai(r2, r3, r5, r6)     // Catch:{ all -> 0x02ef }
            r13 = r0
        L_0x0273:
            s8 r0 = r11.aa
            com.google.android.exoplayer2.Timeline r4 = r0.a
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r5 = r0.b
            boolean r0 = r7.f
            if (r0 == 0) goto L_0x027f
            r6 = r13
            goto L_0x0281
        L_0x027f:
            r6 = r16
        L_0x0281:
            r1 = r30
            r2 = r31
            r3 = r8
            r1.bb(r2, r3, r4, r5, r6)
            if (r19 != 0) goto L_0x0293
            s8 r0 = r11.aa
            long r0 = r0.c
            int r2 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
            if (r2 == 0) goto L_0x02d0
        L_0x0293:
            s8 r0 = r11.aa
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r1 = r0.b
            java.lang.Object r1 = r1.a
            com.google.android.exoplayer2.Timeline r0 = r0.a
            if (r19 == 0) goto L_0x02b0
            if (r32 == 0) goto L_0x02b0
            boolean r2 = r0.isEmpty()
            if (r2 != 0) goto L_0x02b0
            com.google.android.exoplayer2.Timeline$Period r2 = r11.o
            com.google.android.exoplayer2.Timeline$Period r0 = r0.getPeriodByUid(r1, r2)
            boolean r0 = r0.j
            if (r0 != 0) goto L_0x02b0
            goto L_0x02b2
        L_0x02b0:
            r23 = 0
        L_0x02b2:
            s8 r0 = r11.aa
            long r5 = r0.d
            int r0 = r12.getIndexOfPeriod(r1)
            r1 = -1
            if (r0 != r1) goto L_0x02be
            goto L_0x02c0
        L_0x02be:
            r18 = 3
        L_0x02c0:
            r1 = r30
            r2 = r8
            r3 = r13
            r7 = r5
            r5 = r9
            r9 = r23
            r10 = r18
            s8 r0 = r1.n(r2, r3, r5, r7, r9, r10)
            r11.aa = r0
        L_0x02d0:
            r30.z()
            s8 r0 = r11.aa
            com.google.android.exoplayer2.Timeline r0 = r0.a
            r11.ad(r12, r0)
            s8 r0 = r11.aa
            s8 r0 = r0.copyWithTimeline(r12)
            r11.aa = r0
            boolean r0 = r31.isEmpty()
            if (r0 != 0) goto L_0x02eb
            r6 = 0
            r11.an = r6
        L_0x02eb:
            r11.j(r15)
            return
        L_0x02ef:
            r0 = move-exception
            goto L_0x022c
        L_0x02f2:
            r0 = move-exception
            r6 = r5
            r15 = 0
            r18 = 4
            r23 = 1
        L_0x02f9:
            s8 r1 = r11.aa
            com.google.android.exoplayer2.Timeline r4 = r1.a
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r5 = r1.b
            boolean r1 = r7.f
            if (r1 == 0) goto L_0x0305
            r16 = r13
        L_0x0305:
            r1 = r30
            r2 = r31
            r3 = r8
            r15 = r6
            r6 = r16
            r1.bb(r2, r3, r4, r5, r6)
            if (r19 != 0) goto L_0x031a
            s8 r1 = r11.aa
            long r1 = r1.c
            int r3 = (r9 > r1 ? 1 : (r9 == r1 ? 0 : -1))
            if (r3 == 0) goto L_0x0357
        L_0x031a:
            s8 r1 = r11.aa
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r2 = r1.b
            java.lang.Object r2 = r2.a
            com.google.android.exoplayer2.Timeline r1 = r1.a
            if (r19 == 0) goto L_0x0337
            if (r32 == 0) goto L_0x0337
            boolean r3 = r1.isEmpty()
            if (r3 != 0) goto L_0x0337
            com.google.android.exoplayer2.Timeline$Period r3 = r11.o
            com.google.android.exoplayer2.Timeline$Period r1 = r1.getPeriodByUid(r2, r3)
            boolean r1 = r1.j
            if (r1 != 0) goto L_0x0337
            goto L_0x0339
        L_0x0337:
            r23 = 0
        L_0x0339:
            s8 r1 = r11.aa
            long r5 = r1.d
            int r1 = r12.getIndexOfPeriod(r2)
            r2 = -1
            if (r1 != r2) goto L_0x0345
            goto L_0x0347
        L_0x0345:
            r18 = 3
        L_0x0347:
            r1 = r30
            r2 = r8
            r3 = r13
            r7 = r5
            r5 = r9
            r9 = r23
            r10 = r18
            s8 r1 = r1.n(r2, r3, r5, r7, r9, r10)
            r11.aa = r1
        L_0x0357:
            r30.z()
            s8 r1 = r11.aa
            com.google.android.exoplayer2.Timeline r1 = r1.a
            r11.ad(r12, r1)
            s8 r1 = r11.aa
            s8 r1 = r1.copyWithTimeline(r12)
            r11.aa = r1
            boolean r1 = r31.isEmpty()
            if (r1 != 0) goto L_0x0371
            r11.an = r15
        L_0x0371:
            r1 = 0
            r11.j(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ExoPlayerImplInternal.k(com.google.android.exoplayer2.Timeline, boolean):void");
    }

    public final void l(MediaPeriod mediaPeriod) throws ExoPlaybackException {
        d dVar = this.v;
        if (dVar.isLoading(mediaPeriod)) {
            b6 loadingPeriod = dVar.getLoadingPeriod();
            loadingPeriod.handlePrepared(this.r.getPlaybackParameters().c, this.aa.a);
            TrackGroupArray trackGroups = loadingPeriod.getTrackGroups();
            ExoTrackSelection[] exoTrackSelectionArr = loadingPeriod.getTrackSelectorResult().c;
            LoadControl loadControl = this.i;
            Renderer[] rendererArr = this.c;
            loadControl.onTracksSelected(rendererArr, trackGroups, exoTrackSelectionArr);
            if (loadingPeriod == dVar.getPlayingPeriod()) {
                aa(loadingPeriod.f.b);
                e(new boolean[rendererArr.length]);
                s8 s8Var = this.aa;
                MediaSource.MediaPeriodId mediaPeriodId = s8Var.b;
                long j2 = loadingPeriod.f.b;
                this.aa = n(mediaPeriodId, j2, s8Var.c, j2, false, 5);
            }
            r();
        }
    }

    public final void m(PlaybackParameters playbackParameters, float f2, boolean z2, boolean z3) throws ExoPlaybackException {
        int i2;
        if (z2) {
            if (z3) {
                this.ab.incrementPendingOperationAcks(1);
            }
            this.aa = this.aa.copyWithPlaybackParameters(playbackParameters);
        }
        float f3 = playbackParameters.c;
        b6 playingPeriod = this.v.getPlayingPeriod();
        while (true) {
            i2 = 0;
            if (playingPeriod == null) {
                break;
            }
            ExoTrackSelection[] exoTrackSelectionArr = playingPeriod.getTrackSelectorResult().c;
            int length = exoTrackSelectionArr.length;
            while (i2 < length) {
                ExoTrackSelection exoTrackSelection = exoTrackSelectionArr[i2];
                if (exoTrackSelection != null) {
                    exoTrackSelection.onPlaybackSpeed(f3);
                }
                i2++;
            }
            playingPeriod = playingPeriod.getNext();
        }
        Renderer[] rendererArr = this.c;
        int length2 = rendererArr.length;
        while (i2 < length2) {
            Renderer renderer = rendererArr[i2];
            if (renderer != null) {
                renderer.setPlaybackSpeed(f2, playbackParameters.c);
            }
            i2++;
        }
    }

    public void moveMediaSources(int i2, int i3, int i4, ShuffleOrder shuffleOrder) {
        this.k.obtainMessage(19, new b(i2, i3, i4, shuffleOrder)).sendToTarget();
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x00b1  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00c6  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00c8  */
    @androidx.annotation.CheckResult
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final defpackage.s8 n(com.google.android.exoplayer2.source.MediaSource.MediaPeriodId r17, long r18, long r20, long r22, boolean r24, int r25) {
        /*
            r16 = this;
            r0 = r16
            r2 = r17
            r5 = r20
            boolean r1 = r0.aq
            r3 = 1
            r4 = 0
            if (r1 != 0) goto L_0x0021
            s8 r1 = r0.aa
            long r7 = r1.s
            int r1 = (r18 > r7 ? 1 : (r18 == r7 ? 0 : -1))
            if (r1 != 0) goto L_0x0021
            s8 r1 = r0.aa
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r1 = r1.b
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L_0x001f
            goto L_0x0021
        L_0x001f:
            r1 = 0
            goto L_0x0022
        L_0x0021:
            r1 = 1
        L_0x0022:
            r0.aq = r1
            r16.z()
            s8 r1 = r0.aa
            com.google.android.exoplayer2.source.TrackGroupArray r7 = r1.h
            com.google.android.exoplayer2.trackselection.TrackSelectorResult r8 = r1.i
            java.util.List<com.google.android.exoplayer2.metadata.Metadata> r1 = r1.j
            com.google.android.exoplayer2.MediaSourceList r9 = r0.w
            boolean r9 = r9.isPrepared()
            if (r9 == 0) goto L_0x0096
            com.google.android.exoplayer2.d r1 = r0.v
            b6 r1 = r1.getPlayingPeriod()
            if (r1 != 0) goto L_0x0042
            com.google.android.exoplayer2.source.TrackGroupArray r7 = com.google.android.exoplayer2.source.TrackGroupArray.h
            goto L_0x0046
        L_0x0042:
            com.google.android.exoplayer2.source.TrackGroupArray r7 = r1.getTrackGroups()
        L_0x0046:
            if (r1 != 0) goto L_0x004b
            com.google.android.exoplayer2.trackselection.TrackSelectorResult r8 = r0.h
            goto L_0x004f
        L_0x004b:
            com.google.android.exoplayer2.trackselection.TrackSelectorResult r8 = r1.getTrackSelectorResult()
        L_0x004f:
            com.google.android.exoplayer2.trackselection.ExoTrackSelection[] r9 = r8.c
            com.google.common.collect.ImmutableList$Builder r10 = new com.google.common.collect.ImmutableList$Builder
            r10.<init>()
            int r11 = r9.length
            r12 = 0
            r13 = 0
        L_0x0059:
            if (r12 >= r11) goto L_0x0079
            r14 = r9[r12]
            if (r14 == 0) goto L_0x0076
            com.google.android.exoplayer2.Format r14 = r14.getFormat(r4)
            com.google.android.exoplayer2.metadata.Metadata r14 = r14.n
            if (r14 != 0) goto L_0x0072
            com.google.android.exoplayer2.metadata.Metadata r14 = new com.google.android.exoplayer2.metadata.Metadata
            com.google.android.exoplayer2.metadata.Metadata$Entry[] r15 = new com.google.android.exoplayer2.metadata.Metadata.Entry[r4]
            r14.<init>((com.google.android.exoplayer2.metadata.Metadata.Entry[]) r15)
            r10.add((java.lang.Object) r14)
            goto L_0x0076
        L_0x0072:
            r10.add((java.lang.Object) r14)
            r13 = 1
        L_0x0076:
            int r12 = r12 + 1
            goto L_0x0059
        L_0x0079:
            if (r13 == 0) goto L_0x0080
            com.google.common.collect.ImmutableList r3 = r10.build()
            goto L_0x0084
        L_0x0080:
            com.google.common.collect.ImmutableList r3 = com.google.common.collect.ImmutableList.of()
        L_0x0084:
            if (r1 == 0) goto L_0x0094
            c6 r4 = r1.f
            long r9 = r4.c
            int r11 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1))
            if (r11 == 0) goto L_0x0094
            c6 r4 = r4.copyWithRequestedContentPositionUs(r5)
            r1.f = r4
        L_0x0094:
            r1 = r3
            goto L_0x00ac
        L_0x0096:
            s8 r3 = r0.aa
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r3 = r3.b
            boolean r3 = r2.equals(r3)
            if (r3 != 0) goto L_0x00ac
            com.google.android.exoplayer2.source.TrackGroupArray r1 = com.google.android.exoplayer2.source.TrackGroupArray.h
            com.google.android.exoplayer2.trackselection.TrackSelectorResult r3 = r0.h
            com.google.common.collect.ImmutableList r4 = com.google.common.collect.ImmutableList.of()
            r11 = r1
            r12 = r3
            r13 = r4
            goto L_0x00af
        L_0x00ac:
            r13 = r1
            r11 = r7
            r12 = r8
        L_0x00af:
            if (r24 == 0) goto L_0x00b8
            com.google.android.exoplayer2.ExoPlayerImplInternal$PlaybackInfoUpdate r1 = r0.ab
            r3 = r25
            r1.setPositionDiscontinuity(r3)
        L_0x00b8:
            s8 r1 = r0.aa
            long r3 = r1.q
            com.google.android.exoplayer2.d r7 = r0.v
            b6 r7 = r7.getLoadingPeriod()
            r8 = 0
            if (r7 != 0) goto L_0x00c8
            r9 = r8
            goto L_0x00d4
        L_0x00c8:
            long r14 = r0.ao
            long r14 = r7.toPeriodTime(r14)
            long r3 = r3 - r14
            long r3 = java.lang.Math.max(r8, r3)
            r9 = r3
        L_0x00d4:
            r2 = r17
            r3 = r18
            r5 = r20
            r7 = r22
            s8 r1 = r1.copyWithNewPosition(r2, r3, r5, r7, r9, r11, r12, r13)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ExoPlayerImplInternal.n(com.google.android.exoplayer2.source.MediaSource$MediaPeriodId, long, long, long, boolean, int):s8");
    }

    public final boolean o() {
        b6 loadingPeriod = this.v.getLoadingPeriod();
        if (loadingPeriod == null || loadingPeriod.getNextLoadPositionUs() == Long.MIN_VALUE) {
            return false;
        }
        return true;
    }

    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        this.k.obtainMessage(16, playbackParameters).sendToTarget();
    }

    public void onPlaylistUpdateRequested() {
        this.k.sendEmptyMessage(22);
    }

    public void onPrepared(MediaPeriod mediaPeriod) {
        this.k.obtainMessage(8, mediaPeriod).sendToTarget();
    }

    public void onTrackSelectionsInvalidated() {
        this.k.sendEmptyMessage(10);
    }

    public void prepare() {
        this.k.obtainMessage(0).sendToTarget();
    }

    public final boolean q() {
        b6 playingPeriod = this.v.getPlayingPeriod();
        long j2 = playingPeriod.f.e;
        if (!playingPeriod.d || (j2 != -9223372036854775807L && this.aa.s >= j2 && av())) {
            return false;
        }
        return true;
    }

    public final void r() {
        boolean z2;
        long j2;
        boolean o2 = o();
        d dVar = this.v;
        if (!o2) {
            z2 = false;
        } else {
            b6 loadingPeriod = dVar.getLoadingPeriod();
            long nextLoadPositionUs = loadingPeriod.getNextLoadPositionUs();
            b6 loadingPeriod2 = dVar.getLoadingPeriod();
            long j3 = 0;
            if (loadingPeriod2 != null) {
                j3 = Math.max(0, nextLoadPositionUs - loadingPeriod2.toPeriodTime(this.ao));
            }
            long j4 = j3;
            if (loadingPeriod == dVar.getPlayingPeriod()) {
                j2 = loadingPeriod.toPeriodTime(this.ao);
            } else {
                j2 = loadingPeriod.toPeriodTime(this.ao) - loadingPeriod.f.b;
            }
            z2 = this.i.shouldContinueLoading(j2, j4, this.r.getPlaybackParameters().c);
        }
        this.ag = z2;
        if (z2) {
            dVar.getLoadingPeriod().continueLoading(this.ao);
        }
        ba();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0024, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean release() {
        /*
            r3 = this;
            monitor-enter(r3)
            boolean r0 = r3.ac     // Catch:{ all -> 0x0026 }
            if (r0 != 0) goto L_0x0023
            android.os.HandlerThread r0 = r3.l     // Catch:{ all -> 0x0026 }
            boolean r0 = r0.isAlive()     // Catch:{ all -> 0x0026 }
            if (r0 != 0) goto L_0x000e
            goto L_0x0023
        L_0x000e:
            com.google.android.exoplayer2.util.HandlerWrapper r0 = r3.k     // Catch:{ all -> 0x0026 }
            r1 = 7
            r0.sendEmptyMessage(r1)     // Catch:{ all -> 0x0026 }
            r2 r0 = new r2     // Catch:{ all -> 0x0026 }
            r1 = 0
            r0.<init>(r1, r3)     // Catch:{ all -> 0x0026 }
            long r1 = r3.y     // Catch:{ all -> 0x0026 }
            r3.bd(r0, r1)     // Catch:{ all -> 0x0026 }
            boolean r0 = r3.ac     // Catch:{ all -> 0x0026 }
            monitor-exit(r3)
            return r0
        L_0x0023:
            monitor-exit(r3)
            r0 = 1
            return r0
        L_0x0026:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ExoPlayerImplInternal.release():boolean");
    }

    public void removeMediaSources(int i2, int i3, ShuffleOrder shuffleOrder) {
        this.k.obtainMessage(20, i2, i3, shuffleOrder).sendToTarget();
    }

    public final void s() {
        this.ab.setPlaybackInfo(this.aa);
        PlaybackInfoUpdate playbackInfoUpdate = this.ab;
        if (playbackInfoUpdate.a) {
            this.u.onPlaybackInfoUpdate(playbackInfoUpdate);
            this.ab = new PlaybackInfoUpdate(this.aa);
        }
    }

    public void seekTo(Timeline timeline, int i2, long j2) {
        this.k.obtainMessage(3, new e(timeline, i2, j2)).sendToTarget();
    }

    public synchronized void sendMessage(PlayerMessage playerMessage) {
        if (!this.ac) {
            if (this.l.isAlive()) {
                this.k.obtainMessage(14, playerMessage).sendToTarget();
                return;
            }
        }
        Log.w("ExoPlayerImplInternal", "Ignoring messages sent after release.");
        playerMessage.markAsProcessed(false);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x003e, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean setForegroundMode(boolean r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            boolean r0 = r4.ac     // Catch:{ all -> 0x003f }
            r1 = 1
            if (r0 != 0) goto L_0x003d
            android.os.HandlerThread r0 = r4.l     // Catch:{ all -> 0x003f }
            boolean r0 = r0.isAlive()     // Catch:{ all -> 0x003f }
            if (r0 != 0) goto L_0x000f
            goto L_0x003d
        L_0x000f:
            r0 = 13
            r2 = 0
            if (r5 == 0) goto L_0x001f
            com.google.android.exoplayer2.util.HandlerWrapper r5 = r4.k     // Catch:{ all -> 0x003f }
            com.google.android.exoplayer2.util.HandlerWrapper$Message r5 = r5.obtainMessage(r0, r1, r2)     // Catch:{ all -> 0x003f }
            r5.sendToTarget()     // Catch:{ all -> 0x003f }
            monitor-exit(r4)
            return r1
        L_0x001f:
            java.util.concurrent.atomic.AtomicBoolean r5 = new java.util.concurrent.atomic.AtomicBoolean     // Catch:{ all -> 0x003f }
            r5.<init>()     // Catch:{ all -> 0x003f }
            com.google.android.exoplayer2.util.HandlerWrapper r3 = r4.k     // Catch:{ all -> 0x003f }
            com.google.android.exoplayer2.util.HandlerWrapper$Message r0 = r3.obtainMessage(r0, r2, r2, r5)     // Catch:{ all -> 0x003f }
            r0.sendToTarget()     // Catch:{ all -> 0x003f }
            r2 r0 = new r2     // Catch:{ all -> 0x003f }
            r0.<init>(r1, r5)     // Catch:{ all -> 0x003f }
            long r1 = r4.as     // Catch:{ all -> 0x003f }
            r4.bd(r0, r1)     // Catch:{ all -> 0x003f }
            boolean r5 = r5.get()     // Catch:{ all -> 0x003f }
            monitor-exit(r4)
            return r5
        L_0x003d:
            monitor-exit(r4)
            return r1
        L_0x003f:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ExoPlayerImplInternal.setForegroundMode(boolean):boolean");
    }

    public void setMediaSources(List<MediaSourceList.c> list, int i2, long j2, ShuffleOrder shuffleOrder) {
        this.k.obtainMessage(17, new a(list, shuffleOrder, i2, j2)).sendToTarget();
    }

    public void setPauseAtEndOfWindow(boolean z2) {
        this.k.obtainMessage(23, z2 ? 1 : 0, 0).sendToTarget();
    }

    public void setPlayWhenReady(boolean z2, int i2) {
        this.k.obtainMessage(1, z2 ? 1 : 0, i2).sendToTarget();
    }

    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        this.k.obtainMessage(4, playbackParameters).sendToTarget();
    }

    public void setRepeatMode(int i2) {
        this.k.obtainMessage(11, i2, 0).sendToTarget();
    }

    public void setSeekParameters(SeekParameters seekParameters) {
        this.k.obtainMessage(5, seekParameters).sendToTarget();
    }

    public void setShuffleModeEnabled(boolean z2) {
        this.k.obtainMessage(12, z2 ? 1 : 0, 0).sendToTarget();
    }

    public void setShuffleOrder(ShuffleOrder shuffleOrder) {
        this.k.obtainMessage(21, shuffleOrder).sendToTarget();
    }

    public void stop() {
        this.k.obtainMessage(6).sendToTarget();
    }

    public final void t(b bVar) throws ExoPlaybackException {
        this.ab.incrementPendingOperationAcks(1);
        k(this.w.moveMediaSourceRange(bVar.a, bVar.b, bVar.c, bVar.d), false);
    }

    public final void u() {
        int i2;
        this.ab.incrementPendingOperationAcks(1);
        y(false, false, false, true);
        this.i.onPrepared();
        if (this.aa.a.isEmpty()) {
            i2 = 4;
        } else {
            i2 = 2;
        }
        au(i2);
        this.w.prepare(this.j.getTransferListener());
        this.k.sendEmptyMessage(2);
    }

    public final void v() {
        y(true, false, true, false);
        this.i.onReleased();
        au(1);
        this.l.quit();
        synchronized (this) {
            this.ac = true;
            notifyAll();
        }
    }

    public final void w(int i2, int i3, ShuffleOrder shuffleOrder) throws ExoPlaybackException {
        this.ab.incrementPendingOperationAcks(1);
        k(this.w.removeMediaSourceRange(i2, i3, shuffleOrder), false);
    }

    public final void x() throws ExoPlaybackException {
        boolean z2;
        float f2 = this.r.getPlaybackParameters().c;
        b6 playingPeriod = this.v.getPlayingPeriod();
        b6 readingPeriod = this.v.getReadingPeriod();
        boolean z3 = true;
        while (playingPeriod != null && playingPeriod.d) {
            TrackSelectorResult selectTracks = playingPeriod.selectTracks(f2, this.aa.a);
            if (!selectTracks.isEquivalent(playingPeriod.getTrackSelectorResult())) {
                if (z3) {
                    b6 playingPeriod2 = this.v.getPlayingPeriod();
                    boolean removeAfter = this.v.removeAfter(playingPeriod2);
                    boolean[] zArr = new boolean[this.c.length];
                    long applyTrackSelection = playingPeriod2.applyTrackSelection(selectTracks, this.aa.s, removeAfter, zArr);
                    s8 s8Var = this.aa;
                    if (s8Var.e == 4 || applyTrackSelection == s8Var.s) {
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                    s8 s8Var2 = this.aa;
                    b6 b6Var = playingPeriod2;
                    boolean[] zArr2 = zArr;
                    this.aa = n(s8Var2.b, applyTrackSelection, s8Var2.c, s8Var2.d, z2, 5);
                    if (z2) {
                        aa(applyTrackSelection);
                    }
                    boolean[] zArr3 = new boolean[this.c.length];
                    int i2 = 0;
                    while (true) {
                        Renderer[] rendererArr = this.c;
                        if (i2 >= rendererArr.length) {
                            break;
                        }
                        Renderer renderer = rendererArr[i2];
                        boolean p2 = p(renderer);
                        zArr3[i2] = p2;
                        SampleStream sampleStream = b6Var.c[i2];
                        if (p2) {
                            if (sampleStream != renderer.getStream()) {
                                c(renderer);
                            } else if (zArr2[i2]) {
                                renderer.resetPosition(this.ao);
                            }
                        }
                        i2++;
                    }
                    e(zArr3);
                } else {
                    this.v.removeAfter(playingPeriod);
                    if (playingPeriod.d) {
                        playingPeriod.applyTrackSelection(selectTracks, Math.max(playingPeriod.f.b, playingPeriod.toPeriodTime(this.ao)), false);
                    }
                }
                j(true);
                if (this.aa.e != 4) {
                    r();
                    bc();
                    this.k.sendEmptyMessage(2);
                    return;
                }
                return;
            }
            if (playingPeriod == readingPeriod) {
                z3 = false;
            }
            playingPeriod = playingPeriod.getNext();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00ad  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00b5  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00b8  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00bd  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00ea  */
    /* JADX WARNING: Removed duplicated region for block: B:52:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void y(boolean r30, boolean r31, boolean r32, boolean r33) {
        /*
            r29 = this;
            r1 = r29
            com.google.android.exoplayer2.util.HandlerWrapper r0 = r1.k
            r2 = 2
            r0.removeMessages(r2)
            r2 = 0
            r1.ar = r2
            r3 = 0
            r1.af = r3
            com.google.android.exoplayer2.DefaultMediaClock r0 = r1.r
            r0.stop()
            r4 = 0
            r1.ao = r4
            com.google.android.exoplayer2.Renderer[] r4 = r1.c
            int r5 = r4.length
            r6 = 0
        L_0x001b:
            java.lang.String r7 = "ExoPlayerImplInternal"
            if (r6 >= r5) goto L_0x0030
            r0 = r4[r6]
            r1.c(r0)     // Catch:{ ExoPlaybackException -> 0x0027, RuntimeException -> 0x0025 }
            goto L_0x002d
        L_0x0025:
            r0 = move-exception
            goto L_0x0028
        L_0x0027:
            r0 = move-exception
        L_0x0028:
            java.lang.String r8 = "Disable failed."
            com.google.android.exoplayer2.util.Log.e(r7, r8, r0)
        L_0x002d:
            int r6 = r6 + 1
            goto L_0x001b
        L_0x0030:
            if (r30 == 0) goto L_0x0048
            com.google.android.exoplayer2.Renderer[] r4 = r1.c
            int r5 = r4.length
            r6 = 0
        L_0x0036:
            if (r6 >= r5) goto L_0x0048
            r0 = r4[r6]
            r0.reset()     // Catch:{ RuntimeException -> 0x003e }
            goto L_0x0045
        L_0x003e:
            r0 = move-exception
            r8 = r0
            java.lang.String r0 = "Reset failed."
            com.google.android.exoplayer2.util.Log.e(r7, r0, r8)
        L_0x0045:
            int r6 = r6 + 1
            goto L_0x0036
        L_0x0048:
            r1.am = r3
            s8 r0 = r1.aa
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r4 = r0.b
            long r5 = r0.s
            s8 r0 = r1.aa
            com.google.android.exoplayer2.Timeline$Period r7 = r1.o
            boolean r0 = ax(r0, r7)
            if (r0 == 0) goto L_0x005f
            s8 r0 = r1.aa
            long r7 = r0.c
            goto L_0x0063
        L_0x005f:
            s8 r0 = r1.aa
            long r7 = r0.s
        L_0x0063:
            if (r31 == 0) goto L_0x0090
            r1.an = r2
            s8 r0 = r1.aa
            com.google.android.exoplayer2.Timeline r0 = r0.a
            android.util.Pair r0 = r1.h(r0)
            java.lang.Object r4 = r0.first
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r4 = (com.google.android.exoplayer2.source.MediaSource.MediaPeriodId) r4
            java.lang.Object r0 = r0.second
            java.lang.Long r0 = (java.lang.Long) r0
            long r5 = r0.longValue()
            s8 r0 = r1.aa
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r0 = r0.b
            boolean r0 = r4.equals(r0)
            r7 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            if (r0 != 0) goto L_0x0090
            r0 = 1
            r17 = r4
            r25 = r5
            goto L_0x0095
        L_0x0090:
            r17 = r4
            r25 = r5
            r0 = 0
        L_0x0095:
            com.google.android.exoplayer2.d r4 = r1.v
            r4.clear()
            r1.ag = r3
            s8 r3 = new s8
            s8 r4 = r1.aa
            com.google.android.exoplayer2.Timeline r5 = r4.a
            int r11 = r4.e
            if (r33 == 0) goto L_0x00a7
            goto L_0x00a9
        L_0x00a7:
            com.google.android.exoplayer2.ExoPlaybackException r2 = r4.f
        L_0x00a9:
            r12 = r2
            r13 = 0
            if (r0 == 0) goto L_0x00b0
            com.google.android.exoplayer2.source.TrackGroupArray r2 = com.google.android.exoplayer2.source.TrackGroupArray.h
            goto L_0x00b2
        L_0x00b0:
            com.google.android.exoplayer2.source.TrackGroupArray r2 = r4.h
        L_0x00b2:
            r14 = r2
            if (r0 == 0) goto L_0x00b8
            com.google.android.exoplayer2.trackselection.TrackSelectorResult r2 = r1.h
            goto L_0x00ba
        L_0x00b8:
            com.google.android.exoplayer2.trackselection.TrackSelectorResult r2 = r4.i
        L_0x00ba:
            r15 = r2
            if (r0 == 0) goto L_0x00c2
            com.google.common.collect.ImmutableList r0 = com.google.common.collect.ImmutableList.of()
            goto L_0x00c4
        L_0x00c2:
            java.util.List<com.google.android.exoplayer2.metadata.Metadata> r0 = r4.j
        L_0x00c4:
            r16 = r0
            s8 r0 = r1.aa
            boolean r2 = r0.l
            r18 = r2
            int r2 = r0.m
            r19 = r2
            com.google.android.exoplayer2.PlaybackParameters r0 = r0.n
            r20 = r0
            r23 = 0
            boolean r0 = r1.al
            r27 = r0
            r28 = 0
            r4 = r3
            r6 = r17
            r9 = r25
            r21 = r25
            r4.<init>(r5, r6, r7, r9, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r23, r25, r27, r28)
            r1.aa = r3
            if (r32 == 0) goto L_0x00ef
            com.google.android.exoplayer2.MediaSourceList r0 = r1.w
            r0.release()
        L_0x00ef:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ExoPlayerImplInternal.y(boolean, boolean, boolean, boolean):void");
    }

    public final void z() {
        boolean z2;
        b6 playingPeriod = this.v.getPlayingPeriod();
        if (playingPeriod == null || !playingPeriod.f.g || !this.ad) {
            z2 = false;
        } else {
            z2 = true;
        }
        this.ae = z2;
    }

    public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
        this.k.obtainMessage(9, mediaPeriod).sendToTarget();
    }
}
