package com.google.android.exoplayer2;

import android.os.Looper;
import android.util.Pair;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerImplInternal;
import com.google.android.exoplayer2.MediaSourceList;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.PlayerMessage;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.device.DeviceInfo;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.HandlerWrapper;
import com.google.android.exoplayer2.util.ListenerSet;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

public final class a extends BasePlayer implements ExoPlayer {
    public ShuffleOrder aa;
    public boolean ab;
    public Player.Commands ac;
    public MediaMetadata ad;
    public s8 ae;
    public int af;
    public long ag;
    public final TrackSelectorResult b;
    public final Player.Commands c;
    public final Renderer[] d;
    public final TrackSelector e;
    public final HandlerWrapper f;
    public final i2 g;
    public final ExoPlayerImplInternal h;
    public final ListenerSet<Player.EventListener> i;
    public final CopyOnWriteArraySet<ExoPlayer.AudioOffloadListener> j;
    public final Timeline.Period k;
    public final ArrayList l;
    public final boolean m;
    public final MediaSourceFactory n;
    @Nullable
    public final AnalyticsCollector o;
    public final Looper p;
    public final BandwidthMeter q;
    public final Clock r;
    public int s;
    public boolean t;
    public int u;
    public int v;
    public boolean w;
    public int x;
    public boolean y;
    public SeekParameters z;

    /* renamed from: com.google.android.exoplayer2.a$a  reason: collision with other inner class name */
    public static final class C0013a implements k6 {
        public final Object a;
        public Timeline b;

        public C0013a(Object obj, Timeline timeline) {
            this.a = obj;
            this.b = timeline;
        }

        public Timeline getTimeline() {
            return this.b;
        }

        public Object getUid() {
            return this.a;
        }
    }

    /* JADX WARNING: type inference failed for: r34v0, types: [com.google.android.exoplayer2.Player] */
    /* JADX WARNING: Unknown variable types count: 1 */
    @android.annotation.SuppressLint({"HandlerLeak"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public a(com.google.android.exoplayer2.Renderer[] r20, com.google.android.exoplayer2.trackselection.TrackSelector r21, com.google.android.exoplayer2.source.MediaSourceFactory r22, com.google.android.exoplayer2.LoadControl r23, com.google.android.exoplayer2.upstream.BandwidthMeter r24, @androidx.annotation.Nullable com.google.android.exoplayer2.analytics.AnalyticsCollector r25, boolean r26, com.google.android.exoplayer2.SeekParameters r27, com.google.android.exoplayer2.LivePlaybackSpeedControl r28, long r29, boolean r31, com.google.android.exoplayer2.util.Clock r32, android.os.Looper r33, @androidx.annotation.Nullable com.google.android.exoplayer2.Player r34, com.google.android.exoplayer2.Player.Commands r35) {
        /*
            r19 = this;
            r0 = r19
            r2 = r20
            r6 = r24
            r9 = r25
            r15 = r32
            r14 = r33
            r19.<init>()
            int r1 = java.lang.System.identityHashCode(r19)
            java.lang.String r1 = java.lang.Integer.toHexString(r1)
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.e
            r4 = 30
            int r4 = defpackage.y2.c(r1, r4)
            int r4 = defpackage.y2.c(r3, r4)
            java.lang.String r5 = "Init "
            java.lang.String r7 = " [ExoPlayerLib/2.14.2] ["
            java.lang.StringBuilder r1 = defpackage.y2.l(r4, r5, r1, r7, r3)
            java.lang.String r3 = "]"
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            java.lang.String r3 = "ExoPlayerImpl"
            com.google.android.exoplayer2.util.Log.i(r3, r1)
            int r1 = r2.length
            r3 = 1
            r4 = 0
            if (r1 <= 0) goto L_0x0040
            r1 = 1
            goto L_0x0041
        L_0x0040:
            r1 = 0
        L_0x0041:
            com.google.android.exoplayer2.util.Assertions.checkState(r1)
            java.lang.Object r1 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r20)
            com.google.android.exoplayer2.Renderer[] r1 = (com.google.android.exoplayer2.Renderer[]) r1
            r0.d = r1
            java.lang.Object r1 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r21)
            com.google.android.exoplayer2.trackselection.TrackSelector r1 = (com.google.android.exoplayer2.trackselection.TrackSelector) r1
            r0.e = r1
            r1 = r22
            r0.n = r1
            r0.q = r6
            r0.o = r9
            r1 = r26
            r0.m = r1
            r10 = r27
            r0.z = r10
            r12 = r31
            r0.ab = r12
            r0.p = r14
            r0.r = r15
            r0.s = r4
            if (r34 == 0) goto L_0x0073
            r1 = r34
            goto L_0x0074
        L_0x0073:
            r1 = r0
        L_0x0074:
            com.google.android.exoplayer2.util.ListenerSet r5 = new com.google.android.exoplayer2.util.ListenerSet
            i2 r7 = new i2
            r7.<init>(r4, r1)
            r5.<init>(r14, r15, r7)
            r0.i = r5
            java.util.concurrent.CopyOnWriteArraySet r5 = new java.util.concurrent.CopyOnWriteArraySet
            r5.<init>()
            r0.j = r5
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            r0.l = r5
            com.google.android.exoplayer2.source.ShuffleOrder$DefaultShuffleOrder r5 = new com.google.android.exoplayer2.source.ShuffleOrder$DefaultShuffleOrder
            r5.<init>(r4)
            r0.aa = r5
            com.google.android.exoplayer2.trackselection.TrackSelectorResult r4 = new com.google.android.exoplayer2.trackselection.TrackSelectorResult
            int r5 = r2.length
            com.google.android.exoplayer2.RendererConfiguration[] r5 = new com.google.android.exoplayer2.RendererConfiguration[r5]
            int r7 = r2.length
            com.google.android.exoplayer2.trackselection.ExoTrackSelection[] r7 = new com.google.android.exoplayer2.trackselection.ExoTrackSelection[r7]
            r8 = 0
            r4.<init>(r5, r7, r8)
            r0.b = r4
            com.google.android.exoplayer2.Timeline$Period r5 = new com.google.android.exoplayer2.Timeline$Period
            r5.<init>()
            r0.k = r5
            com.google.android.exoplayer2.Player$Commands$Builder r5 = new com.google.android.exoplayer2.Player$Commands$Builder
            r5.<init>()
            r7 = 9
            int[] r7 = new int[r7]
            r7 = {1, 2, 8, 9, 10, 11, 12, 13, 14} // fill-array
            com.google.android.exoplayer2.Player$Commands$Builder r5 = r5.addAll((int[]) r7)
            r7 = r35
            com.google.android.exoplayer2.Player$Commands$Builder r5 = r5.addAll((com.google.android.exoplayer2.Player.Commands) r7)
            com.google.android.exoplayer2.Player$Commands r5 = r5.build()
            r0.c = r5
            com.google.android.exoplayer2.Player$Commands$Builder r7 = new com.google.android.exoplayer2.Player$Commands$Builder
            r7.<init>()
            com.google.android.exoplayer2.Player$Commands$Builder r5 = r7.addAll((com.google.android.exoplayer2.Player.Commands) r5)
            r7 = 3
            com.google.android.exoplayer2.Player$Commands$Builder r5 = r5.add(r7)
            r7 = 7
            com.google.android.exoplayer2.Player$Commands$Builder r5 = r5.add(r7)
            com.google.android.exoplayer2.Player$Commands r5 = r5.build()
            r0.ac = r5
            com.google.android.exoplayer2.MediaMetadata r5 = com.google.android.exoplayer2.MediaMetadata.w
            r0.ad = r5
            r5 = -1
            r0.af = r5
            com.google.android.exoplayer2.util.HandlerWrapper r5 = r15.createHandler(r14, r8)
            r0.f = r5
            i2 r13 = new i2
            r13.<init>(r3, r0)
            r0.g = r13
            s8 r3 = defpackage.s8.createDummy(r4)
            r0.ae = r3
            if (r9 == 0) goto L_0x0109
            r9.setPlayer(r1, r14)
            r0.addListener((com.google.android.exoplayer2.Player.Listener) r9)
            android.os.Handler r1 = new android.os.Handler
            r1.<init>(r14)
            r6.addEventListener(r1, r9)
        L_0x0109:
            com.google.android.exoplayer2.ExoPlayerImplInternal r11 = new com.google.android.exoplayer2.ExoPlayerImplInternal
            r1 = r11
            int r7 = r0.s
            boolean r8 = r0.t
            r2 = r20
            r3 = r21
            r5 = r23
            r6 = r24
            r9 = r25
            r10 = r27
            r18 = r11
            r11 = r28
            r17 = r13
            r12 = r29
            r14 = r31
            r15 = r33
            r16 = r32
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r14, r15, r16, r17)
            r1 = r18
            r0.h = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.a.<init>(com.google.android.exoplayer2.Renderer[], com.google.android.exoplayer2.trackselection.TrackSelector, com.google.android.exoplayer2.source.MediaSourceFactory, com.google.android.exoplayer2.LoadControl, com.google.android.exoplayer2.upstream.BandwidthMeter, com.google.android.exoplayer2.analytics.AnalyticsCollector, boolean, com.google.android.exoplayer2.SeekParameters, com.google.android.exoplayer2.LivePlaybackSpeedControl, long, boolean, com.google.android.exoplayer2.util.Clock, android.os.Looper, com.google.android.exoplayer2.Player, com.google.android.exoplayer2.Player$Commands):void");
    }

    public static long g(s8 s8Var) {
        Timeline.Window window = new Timeline.Window();
        Timeline.Period period = new Timeline.Period();
        s8Var.a.getPeriodByUid(s8Var.b.a, period);
        long j2 = s8Var.c;
        if (j2 == -9223372036854775807L) {
            return s8Var.a.getWindow(period.g, window).getDefaultPositionUs();
        }
        return period.getPositionInWindowUs() + j2;
    }

    public static boolean h(s8 s8Var) {
        return s8Var.e == 3 && s8Var.l && s8Var.m == 0;
    }

    public final ArrayList a(int i2, List list) {
        ArrayList arrayList = new ArrayList();
        for (int i3 = 0; i3 < list.size(); i3++) {
            MediaSourceList.c cVar = new MediaSourceList.c((MediaSource) list.get(i3), this.m);
            arrayList.add(cVar);
            this.l.add(i3 + i2, new C0013a(cVar.b, cVar.a.getTimeline()));
        }
        this.aa = this.aa.cloneAndInsert(i2, arrayList.size());
        return arrayList;
    }

    public void addAudioOffloadListener(ExoPlayer.AudioOffloadListener audioOffloadListener) {
        this.j.add(audioOffloadListener);
    }

    public void addListener(Player.Listener listener) {
        addListener((Player.EventListener) listener);
    }

    public void addMediaItems(int i2, List<MediaItem> list) {
        addMediaSources(Math.min(i2, this.l.size()), b(list));
    }

    public void addMediaSource(MediaSource mediaSource) {
        addMediaSources(Collections.singletonList(mediaSource));
    }

    public void addMediaSources(List<MediaSource> list) {
        addMediaSources(this.l.size(), list);
    }

    public final ArrayList b(List list) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            arrayList.add(this.n.createMediaSource((MediaItem) list.get(i2)));
        }
        return arrayList;
    }

    public final long c(s8 s8Var) {
        if (s8Var.a.isEmpty()) {
            return C.msToUs(this.ag);
        }
        if (s8Var.b.isAd()) {
            return s8Var.s;
        }
        Timeline timeline = s8Var.a;
        MediaSource.MediaPeriodId mediaPeriodId = s8Var.b;
        long j2 = s8Var.s;
        Object obj = mediaPeriodId.a;
        Timeline.Period period = this.k;
        timeline.getPeriodByUid(obj, period);
        return period.getPositionInWindowUs() + j2;
    }

    public void clearVideoSurface() {
    }

    public void clearVideoSurface(@Nullable Surface surface) {
    }

    public void clearVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder) {
    }

    public void clearVideoSurfaceView(@Nullable SurfaceView surfaceView) {
    }

    public void clearVideoTextureView(@Nullable TextureView textureView) {
    }

    public PlayerMessage createMessage(PlayerMessage.Target target) {
        return new PlayerMessage(this.h, target, this.ae.a, getCurrentWindowIndex(), this.r, this.h.getPlaybackLooper());
    }

    public final int d() {
        if (this.ae.a.isEmpty()) {
            return this.af;
        }
        s8 s8Var = this.ae;
        return s8Var.a.getPeriodByUid(s8Var.b.a, this.k).g;
    }

    public void decreaseDeviceVolume() {
    }

    @Nullable
    public final Pair e(Timeline timeline, z8 z8Var) {
        boolean z2;
        long contentPosition = getContentPosition();
        int i2 = -1;
        if (timeline.isEmpty() || z8Var.isEmpty()) {
            if (timeline.isEmpty() || !z8Var.isEmpty()) {
                z2 = false;
            } else {
                z2 = true;
            }
            if (!z2) {
                i2 = d();
            }
            if (z2) {
                contentPosition = -9223372036854775807L;
            }
            return f(z8Var, i2, contentPosition);
        }
        Pair<Object, Long> periodPosition = timeline.getPeriodPosition(this.a, this.k, getCurrentWindowIndex(), C.msToUs(contentPosition));
        Object obj = ((Pair) Util.castNonNull(periodPosition)).first;
        if (z8Var.getIndexOfPeriod(obj) != -1) {
            return periodPosition;
        }
        Object af2 = ExoPlayerImplInternal.af(this.a, this.k, this.s, this.t, obj, timeline, z8Var);
        if (af2 == null) {
            return f(z8Var, -1, -9223372036854775807L);
        }
        Timeline.Period period = this.k;
        z8Var.getPeriodByUid(af2, period);
        int i3 = period.g;
        return f(z8Var, i3, z8Var.getWindow(i3, this.a).getDefaultPositionMs());
    }

    public boolean experimentalIsSleepingForOffload() {
        return this.ae.p;
    }

    public void experimentalSetForegroundModeTimeoutMs(long j2) {
        this.h.experimentalSetForegroundModeTimeoutMs(j2);
    }

    public void experimentalSetOffloadSchedulingEnabled(boolean z2) {
        this.h.experimentalSetOffloadSchedulingEnabled(z2);
    }

    @Nullable
    public final Pair<Object, Long> f(Timeline timeline, int i2, long j2) {
        if (timeline.isEmpty()) {
            this.af = i2;
            if (j2 == -9223372036854775807L) {
                j2 = 0;
            }
            this.ag = j2;
            return null;
        }
        if (i2 == -1 || i2 >= timeline.getWindowCount()) {
            i2 = timeline.getFirstWindowIndex(this.t);
            j2 = timeline.getWindow(i2, this.a).getDefaultPositionMs();
        }
        return timeline.getPeriodPosition(this.a, this.k, i2, C.msToUs(j2));
    }

    public Looper getApplicationLooper() {
        return this.p;
    }

    public AudioAttributes getAudioAttributes() {
        return AudioAttributes.j;
    }

    @Nullable
    public ExoPlayer.AudioComponent getAudioComponent() {
        return null;
    }

    public Player.Commands getAvailableCommands() {
        return this.ac;
    }

    public long getBufferedPosition() {
        if (!isPlayingAd()) {
            return getContentBufferedPosition();
        }
        s8 s8Var = this.ae;
        if (s8Var.k.equals(s8Var.b)) {
            return C.usToMs(this.ae.q);
        }
        return getDuration();
    }

    public Clock getClock() {
        return this.r;
    }

    public long getContentBufferedPosition() {
        if (this.ae.a.isEmpty()) {
            return this.ag;
        }
        s8 s8Var = this.ae;
        if (s8Var.k.d != s8Var.b.d) {
            return s8Var.a.getWindow(getCurrentWindowIndex(), this.a).getDurationMs();
        }
        long j2 = s8Var.q;
        if (this.ae.k.isAd()) {
            s8 s8Var2 = this.ae;
            Timeline.Period periodByUid = s8Var2.a.getPeriodByUid(s8Var2.k.a, this.k);
            long adGroupTimeUs = periodByUid.getAdGroupTimeUs(this.ae.k.b);
            if (adGroupTimeUs == Long.MIN_VALUE) {
                j2 = periodByUid.h;
            } else {
                j2 = adGroupTimeUs;
            }
        }
        s8 s8Var3 = this.ae;
        Timeline timeline = s8Var3.a;
        Object obj = s8Var3.k.a;
        Timeline.Period period = this.k;
        timeline.getPeriodByUid(obj, period);
        return C.usToMs(period.getPositionInWindowUs() + j2);
    }

    public long getContentPosition() {
        if (!isPlayingAd()) {
            return getCurrentPosition();
        }
        s8 s8Var = this.ae;
        Timeline timeline = s8Var.a;
        Object obj = s8Var.b.a;
        Timeline.Period period = this.k;
        timeline.getPeriodByUid(obj, period);
        s8 s8Var2 = this.ae;
        if (s8Var2.c == -9223372036854775807L) {
            return s8Var2.a.getWindow(getCurrentWindowIndex(), this.a).getDefaultPositionMs();
        }
        return period.getPositionInWindowMs() + C.usToMs(this.ae.c);
    }

    public int getCurrentAdGroupIndex() {
        if (isPlayingAd()) {
            return this.ae.b.b;
        }
        return -1;
    }

    public int getCurrentAdIndexInAdGroup() {
        if (isPlayingAd()) {
            return this.ae.b.c;
        }
        return -1;
    }

    public int getCurrentPeriodIndex() {
        if (this.ae.a.isEmpty()) {
            return 0;
        }
        s8 s8Var = this.ae;
        return s8Var.a.getIndexOfPeriod(s8Var.b.a);
    }

    public long getCurrentPosition() {
        return C.usToMs(c(this.ae));
    }

    public List<Metadata> getCurrentStaticMetadata() {
        return this.ae.j;
    }

    public Timeline getCurrentTimeline() {
        return this.ae.a;
    }

    public TrackGroupArray getCurrentTrackGroups() {
        return this.ae.h;
    }

    public TrackSelectionArray getCurrentTrackSelections() {
        return new TrackSelectionArray(this.ae.i.c);
    }

    public int getCurrentWindowIndex() {
        int d2 = d();
        if (d2 == -1) {
            return 0;
        }
        return d2;
    }

    @Nullable
    public ExoPlayer.DeviceComponent getDeviceComponent() {
        return null;
    }

    public DeviceInfo getDeviceInfo() {
        return DeviceInfo.h;
    }

    public int getDeviceVolume() {
        return 0;
    }

    public long getDuration() {
        if (!isPlayingAd()) {
            return getContentDuration();
        }
        s8 s8Var = this.ae;
        MediaSource.MediaPeriodId mediaPeriodId = s8Var.b;
        Timeline timeline = s8Var.a;
        Object obj = mediaPeriodId.a;
        Timeline.Period period = this.k;
        timeline.getPeriodByUid(obj, period);
        return C.usToMs(period.getAdDurationUs(mediaPeriodId.b, mediaPeriodId.c));
    }

    public MediaMetadata getMediaMetadata() {
        return this.ad;
    }

    @Nullable
    public ExoPlayer.MetadataComponent getMetadataComponent() {
        return null;
    }

    public boolean getPauseAtEndOfMediaItems() {
        return this.ab;
    }

    public boolean getPlayWhenReady() {
        return this.ae.l;
    }

    public Looper getPlaybackLooper() {
        return this.h.getPlaybackLooper();
    }

    public PlaybackParameters getPlaybackParameters() {
        return this.ae.n;
    }

    public int getPlaybackState() {
        return this.ae.e;
    }

    public int getPlaybackSuppressionReason() {
        return this.ae.m;
    }

    @Nullable
    public ExoPlaybackException getPlayerError() {
        return this.ae.f;
    }

    public int getRendererCount() {
        return this.d.length;
    }

    public int getRendererType(int i2) {
        return this.d[i2].getTrackType();
    }

    public int getRepeatMode() {
        return this.s;
    }

    public SeekParameters getSeekParameters() {
        return this.z;
    }

    public boolean getShuffleModeEnabled() {
        return this.t;
    }

    @Nullable
    public ExoPlayer.TextComponent getTextComponent() {
        return null;
    }

    public long getTotalBufferedDuration() {
        return C.usToMs(this.ae.r);
    }

    @Nullable
    public TrackSelector getTrackSelector() {
        return this.e;
    }

    @Nullable
    public ExoPlayer.VideoComponent getVideoComponent() {
        return null;
    }

    public VideoSize getVideoSize() {
        return VideoSize.i;
    }

    public float getVolume() {
        return 1.0f;
    }

    public final s8 i(s8 s8Var, Timeline timeline, @Nullable Pair<Object, Long> pair) {
        boolean z2;
        MediaSource.MediaPeriodId mediaPeriodId;
        TrackGroupArray trackGroupArray;
        TrackSelectorResult trackSelectorResult;
        MediaSource.MediaPeriodId mediaPeriodId2;
        List list;
        int i2;
        long j2;
        Timeline timeline2 = timeline;
        Pair<Object, Long> pair2 = pair;
        if (timeline.isEmpty() || pair2 != null) {
            z2 = true;
        } else {
            z2 = false;
        }
        Assertions.checkArgument(z2);
        Timeline timeline3 = s8Var.a;
        s8 copyWithTimeline = s8Var.copyWithTimeline(timeline);
        if (timeline.isEmpty()) {
            MediaSource.MediaPeriodId dummyPeriodForEmptyTimeline = s8.getDummyPeriodForEmptyTimeline();
            long msToUs = C.msToUs(this.ag);
            s8 copyWithLoadingMediaPeriodId = copyWithTimeline.copyWithNewPosition(dummyPeriodForEmptyTimeline, msToUs, msToUs, msToUs, 0, TrackGroupArray.h, this.b, ImmutableList.of()).copyWithLoadingMediaPeriodId(dummyPeriodForEmptyTimeline);
            copyWithLoadingMediaPeriodId.q = copyWithLoadingMediaPeriodId.s;
            return copyWithLoadingMediaPeriodId;
        }
        Object obj = copyWithTimeline.b.a;
        boolean z3 = !obj.equals(((Pair) Util.castNonNull(pair)).first);
        if (z3) {
            mediaPeriodId = new MediaSource.MediaPeriodId(pair2.first);
        } else {
            mediaPeriodId = copyWithTimeline.b;
        }
        MediaSource.MediaPeriodId mediaPeriodId3 = mediaPeriodId;
        long longValue = ((Long) pair2.second).longValue();
        long msToUs2 = C.msToUs(getContentPosition());
        if (!timeline3.isEmpty()) {
            msToUs2 -= timeline3.getPeriodByUid(obj, this.k).getPositionInWindowUs();
        }
        if (z3 || longValue < msToUs2) {
            MediaSource.MediaPeriodId mediaPeriodId4 = mediaPeriodId3;
            Assertions.checkState(!mediaPeriodId4.isAd());
            if (z3) {
                trackGroupArray = TrackGroupArray.h;
            } else {
                trackGroupArray = copyWithTimeline.h;
            }
            TrackGroupArray trackGroupArray2 = trackGroupArray;
            if (z3) {
                mediaPeriodId2 = mediaPeriodId4;
                trackSelectorResult = this.b;
            } else {
                mediaPeriodId2 = mediaPeriodId4;
                trackSelectorResult = copyWithTimeline.i;
            }
            TrackSelectorResult trackSelectorResult2 = trackSelectorResult;
            if (z3) {
                list = ImmutableList.of();
            } else {
                list = copyWithTimeline.j;
            }
            s8 copyWithLoadingMediaPeriodId2 = copyWithTimeline.copyWithNewPosition(mediaPeriodId2, longValue, longValue, longValue, 0, trackGroupArray2, trackSelectorResult2, list).copyWithLoadingMediaPeriodId(mediaPeriodId2);
            copyWithLoadingMediaPeriodId2.q = longValue;
            return copyWithLoadingMediaPeriodId2;
        }
        if (i2 == 0) {
            int indexOfPeriod = timeline2.getIndexOfPeriod(copyWithTimeline.k.a);
            if (indexOfPeriod == -1 || timeline2.getPeriod(indexOfPeriod, this.k).g != timeline2.getPeriodByUid(mediaPeriodId3.a, this.k).g) {
                timeline2.getPeriodByUid(mediaPeriodId3.a, this.k);
                if (mediaPeriodId3.isAd()) {
                    j2 = this.k.getAdDurationUs(mediaPeriodId3.b, mediaPeriodId3.c);
                } else {
                    j2 = this.k.h;
                }
                copyWithTimeline = copyWithTimeline.copyWithNewPosition(mediaPeriodId3, copyWithTimeline.s, copyWithTimeline.s, copyWithTimeline.d, j2 - copyWithTimeline.s, copyWithTimeline.h, copyWithTimeline.i, copyWithTimeline.j).copyWithLoadingMediaPeriodId(mediaPeriodId3);
                copyWithTimeline.q = j2;
            }
        } else {
            MediaSource.MediaPeriodId mediaPeriodId5 = mediaPeriodId3;
            Assertions.checkState(!mediaPeriodId5.isAd());
            long max = Math.max(0, copyWithTimeline.r - (longValue - msToUs2));
            long j3 = copyWithTimeline.q;
            if (copyWithTimeline.k.equals(copyWithTimeline.b)) {
                j3 = longValue + max;
            }
            copyWithTimeline = copyWithTimeline.copyWithNewPosition(mediaPeriodId5, longValue, longValue, longValue, max, copyWithTimeline.h, copyWithTimeline.i, copyWithTimeline.j);
            copyWithTimeline.q = j3;
        }
        return copyWithTimeline;
    }

    public void increaseDeviceVolume() {
    }

    public boolean isDeviceMuted() {
        return false;
    }

    public boolean isLoading() {
        return this.ae.g;
    }

    public boolean isPlayingAd() {
        return this.ae.b.isAd();
    }

    public final s8 j(int i2, int i3) {
        boolean z2;
        ArrayList arrayList = this.l;
        boolean z3 = false;
        if (i2 < 0 || i3 < i2 || i3 > arrayList.size()) {
            z2 = false;
        } else {
            z2 = true;
        }
        Assertions.checkArgument(z2);
        int currentWindowIndex = getCurrentWindowIndex();
        Timeline currentTimeline = getCurrentTimeline();
        int size = arrayList.size();
        this.u++;
        for (int i4 = i3 - 1; i4 >= i2; i4--) {
            arrayList.remove(i4);
        }
        this.aa = this.aa.cloneAndRemove(i2, i3);
        z8 z8Var = new z8(arrayList, this.aa);
        s8 i5 = i(this.ae, z8Var, e(currentTimeline, z8Var));
        int i6 = i5.e;
        if (i6 != 1 && i6 != 4 && i2 < i3 && i3 == size && currentWindowIndex >= i5.a.getWindowCount()) {
            z3 = true;
        }
        if (z3) {
            i5 = i5.copyWithPlaybackState(4);
        }
        this.h.removeMediaSources(i2, i3, this.aa);
        return i5;
    }

    public final void k(List<MediaSource> list, int i2, long j2, boolean z2) {
        int i3;
        long j3;
        int i4 = i2;
        int d2 = d();
        long currentPosition = getCurrentPosition();
        boolean z3 = true;
        this.u++;
        ArrayList arrayList = this.l;
        if (!arrayList.isEmpty()) {
            int size = arrayList.size();
            for (int i5 = size - 1; i5 >= 0; i5--) {
                arrayList.remove(i5);
            }
            this.aa = this.aa.cloneAndRemove(0, size);
        }
        ArrayList a = a(0, list);
        z8 z8Var = new z8(arrayList, this.aa);
        if (z8Var.isEmpty() || i4 < z8Var.getWindowCount()) {
            long j4 = j2;
            if (z2) {
                j3 = -9223372036854775807L;
                i3 = z8Var.getFirstWindowIndex(this.t);
            } else if (i4 == -1) {
                i3 = d2;
                j3 = currentPosition;
            } else {
                i3 = i4;
                j3 = j4;
            }
            s8 i6 = i(this.ae, z8Var, f(z8Var, i3, j3));
            int i7 = i6.e;
            if (!(i3 == -1 || i7 == 1)) {
                i7 = (z8Var.isEmpty() || i3 >= z8Var.getWindowCount()) ? 4 : 2;
            }
            s8 copyWithPlaybackState = i6.copyWithPlaybackState(i7);
            this.h.setMediaSources(a, i3, C.msToUs(j3), this.aa);
            if (this.ae.b.a.equals(copyWithPlaybackState.b.a) || this.ae.a.isEmpty()) {
                z3 = false;
            }
            m(copyWithPlaybackState, 0, 1, false, z3, 4, c(copyWithPlaybackState), -1);
            return;
        }
        throw new IllegalSeekPositionException(z8Var, i4, j2);
    }

    public final void l() {
        boolean z2;
        boolean z3;
        Player.Commands commands = this.ac;
        Player.Commands.Builder addIf = new Player.Commands.Builder().addAll(this.c).addIf(3, !isPlayingAd());
        boolean z4 = false;
        if (!isCurrentWindowSeekable() || isPlayingAd()) {
            z2 = false;
        } else {
            z2 = true;
        }
        Player.Commands.Builder addIf2 = addIf.addIf(4, z2);
        if (!hasNext() || isPlayingAd()) {
            z3 = false;
        } else {
            z3 = true;
        }
        Player.Commands.Builder addIf3 = addIf2.addIf(5, z3);
        if (hasPrevious() && !isPlayingAd()) {
            z4 = true;
        }
        Player.Commands build = addIf3.addIf(6, z4).addIf(7, !isPlayingAd()).build();
        this.ac = build;
        if (!build.equals(commands)) {
            this.i.queueEvent(14, new q2(1, this));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:65:0x01da  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0202  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0218  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0225  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void m(defpackage.s8 r37, int r38, int r39, boolean r40, boolean r41, int r42, long r43, int r45) {
        /*
            r36 = this;
            r0 = r36
            r1 = r37
            r2 = r42
            s8 r3 = r0.ae
            r0.ae = r1
            com.google.android.exoplayer2.Timeline r4 = r3.a
            com.google.android.exoplayer2.Timeline r5 = r1.a
            boolean r4 = r4.equals(r5)
            r5 = 1
            r4 = r4 ^ r5
            com.google.android.exoplayer2.Timeline r6 = r3.a
            com.google.android.exoplayer2.Timeline r7 = r1.a
            boolean r8 = r7.isEmpty()
            r9 = -1
            java.lang.Integer r10 = java.lang.Integer.valueOf(r9)
            r11 = 3
            r13 = 0
            if (r8 == 0) goto L_0x0034
            boolean r8 = r6.isEmpty()
            if (r8 == 0) goto L_0x0034
            android.util.Pair r4 = new android.util.Pair
            java.lang.Boolean r6 = java.lang.Boolean.FALSE
            r4.<init>(r6, r10)
            goto L_0x00b6
        L_0x0034:
            boolean r8 = r7.isEmpty()
            boolean r14 = r6.isEmpty()
            if (r8 == r14) goto L_0x004b
            android.util.Pair r4 = new android.util.Pair
            java.lang.Boolean r6 = java.lang.Boolean.TRUE
            java.lang.Integer r7 = java.lang.Integer.valueOf(r11)
            r4.<init>(r6, r7)
            goto L_0x00b6
        L_0x004b:
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r8 = r3.b
            java.lang.Object r14 = r8.a
            com.google.android.exoplayer2.Timeline$Period r15 = r0.k
            com.google.android.exoplayer2.Timeline$Period r14 = r6.getPeriodByUid(r14, r15)
            int r14 = r14.g
            com.google.android.exoplayer2.Timeline$Window r11 = r0.a
            com.google.android.exoplayer2.Timeline$Window r6 = r6.getWindow(r14, r11)
            java.lang.Object r6 = r6.c
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r14 = r1.b
            java.lang.Object r12 = r14.a
            com.google.android.exoplayer2.Timeline$Period r12 = r7.getPeriodByUid(r12, r15)
            int r12 = r12.g
            com.google.android.exoplayer2.Timeline$Window r7 = r7.getWindow(r12, r11)
            java.lang.Object r7 = r7.c
            boolean r6 = r6.equals(r7)
            if (r6 != 0) goto L_0x0097
            if (r41 == 0) goto L_0x007b
            if (r2 != 0) goto L_0x007b
            r4 = 1
            goto L_0x0084
        L_0x007b:
            if (r41 == 0) goto L_0x0081
            if (r2 != r5) goto L_0x0081
            r4 = 2
            goto L_0x0084
        L_0x0081:
            if (r4 == 0) goto L_0x0091
            r4 = 3
        L_0x0084:
            android.util.Pair r6 = new android.util.Pair
            java.lang.Boolean r7 = java.lang.Boolean.TRUE
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r6.<init>(r7, r4)
            r4 = r6
            goto L_0x00b6
        L_0x0091:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            r1.<init>()
            throw r1
        L_0x0097:
            if (r41 == 0) goto L_0x00af
            if (r2 != 0) goto L_0x00af
            long r6 = r8.d
            long r11 = r14.d
            int r4 = (r6 > r11 ? 1 : (r6 == r11 ? 0 : -1))
            if (r4 >= 0) goto L_0x00af
            android.util.Pair r4 = new android.util.Pair
            java.lang.Boolean r6 = java.lang.Boolean.TRUE
            java.lang.Integer r7 = java.lang.Integer.valueOf(r13)
            r4.<init>(r6, r7)
            goto L_0x00b6
        L_0x00af:
            android.util.Pair r4 = new android.util.Pair
            java.lang.Boolean r6 = java.lang.Boolean.FALSE
            r4.<init>(r6, r10)
        L_0x00b6:
            java.lang.Object r6 = r4.first
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            java.lang.Object r4 = r4.second
            java.lang.Integer r4 = (java.lang.Integer) r4
            int r4 = r4.intValue()
            com.google.android.exoplayer2.MediaMetadata r7 = r0.ad
            if (r6 == 0) goto L_0x00f6
            com.google.android.exoplayer2.Timeline r10 = r1.a
            boolean r10 = r10.isEmpty()
            if (r10 != 0) goto L_0x00eb
            com.google.android.exoplayer2.Timeline r10 = r1.a
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r11 = r1.b
            java.lang.Object r11 = r11.a
            com.google.android.exoplayer2.Timeline$Period r12 = r0.k
            com.google.android.exoplayer2.Timeline$Period r10 = r10.getPeriodByUid(r11, r12)
            int r10 = r10.g
            com.google.android.exoplayer2.Timeline r11 = r1.a
            com.google.android.exoplayer2.Timeline$Window r12 = r0.a
            com.google.android.exoplayer2.Timeline$Window r10 = r11.getWindow(r10, r12)
            com.google.android.exoplayer2.MediaItem r10 = r10.g
            goto L_0x00ec
        L_0x00eb:
            r10 = 0
        L_0x00ec:
            if (r10 == 0) goto L_0x00f1
            com.google.android.exoplayer2.MediaMetadata r11 = r10.h
            goto L_0x00f3
        L_0x00f1:
            com.google.android.exoplayer2.MediaMetadata r11 = com.google.android.exoplayer2.MediaMetadata.w
        L_0x00f3:
            r0.ad = r11
            goto L_0x00f7
        L_0x00f6:
            r10 = 0
        L_0x00f7:
            java.util.List<com.google.android.exoplayer2.metadata.Metadata> r11 = r3.j
            java.util.List<com.google.android.exoplayer2.metadata.Metadata> r12 = r1.j
            boolean r11 = r11.equals(r12)
            if (r11 != 0) goto L_0x010f
            com.google.android.exoplayer2.MediaMetadata$Builder r7 = r7.buildUpon()
            java.util.List<com.google.android.exoplayer2.metadata.Metadata> r11 = r1.j
            com.google.android.exoplayer2.MediaMetadata$Builder r7 = r7.populateFromMetadata((java.util.List<com.google.android.exoplayer2.metadata.Metadata>) r11)
            com.google.android.exoplayer2.MediaMetadata r7 = r7.build()
        L_0x010f:
            com.google.android.exoplayer2.MediaMetadata r11 = r0.ad
            boolean r11 = r7.equals(r11)
            r11 = r11 ^ r5
            r0.ad = r7
            com.google.android.exoplayer2.Timeline r7 = r3.a
            com.google.android.exoplayer2.Timeline r12 = r1.a
            boolean r7 = r7.equals(r12)
            if (r7 != 0) goto L_0x012e
            com.google.android.exoplayer2.util.ListenerSet<com.google.android.exoplayer2.Player$EventListener> r7 = r0.i
            k2 r12 = new k2
            r14 = r38
            r12.<init>(r1, r14, r13)
            r7.queueEvent(r13, r12)
        L_0x012e:
            if (r41 == 0) goto L_0x0246
            com.google.android.exoplayer2.Timeline$Period r7 = new com.google.android.exoplayer2.Timeline$Period
            r7.<init>()
            com.google.android.exoplayer2.Timeline r12 = r3.a
            boolean r12 = r12.isEmpty()
            if (r12 != 0) goto L_0x0161
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r12 = r3.b
            java.lang.Object r12 = r12.a
            com.google.android.exoplayer2.Timeline r14 = r3.a
            r14.getPeriodByUid(r12, r7)
            int r14 = r7.g
            com.google.android.exoplayer2.Timeline r15 = r3.a
            int r15 = r15.getIndexOfPeriod(r12)
            com.google.android.exoplayer2.Timeline r8 = r3.a
            com.google.android.exoplayer2.Timeline$Window r13 = r0.a
            com.google.android.exoplayer2.Timeline$Window r8 = r8.getWindow(r14, r13)
            java.lang.Object r8 = r8.c
            r17 = r8
            r19 = r12
            r18 = r14
            r20 = r15
            goto L_0x0169
        L_0x0161:
            r18 = r45
            r17 = 0
            r19 = 0
            r20 = -1
        L_0x0169:
            if (r2 != 0) goto L_0x019e
            long r12 = r7.i
            long r14 = r7.h
            long r12 = r12 + r14
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r8 = r3.b
            boolean r8 = r8.isAd()
            if (r8 == 0) goto L_0x0187
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r8 = r3.b
            int r12 = r8.b
            int r8 = r8.c
            long r7 = r7.getAdDurationUs(r12, r8)
            long r12 = g(r3)
            goto L_0x01b3
        L_0x0187:
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r7 = r3.b
            int r7 = r7.e
            if (r7 == r9) goto L_0x01b2
            s8 r7 = r0.ae
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r7 = r7.b
            boolean r7 = r7.isAd()
            if (r7 == 0) goto L_0x01b2
            s8 r7 = r0.ae
            long r12 = g(r7)
            goto L_0x01b2
        L_0x019e:
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r8 = r3.b
            boolean r8 = r8.isAd()
            if (r8 == 0) goto L_0x01ad
            long r7 = r3.s
            long r12 = g(r3)
            goto L_0x01b3
        L_0x01ad:
            long r7 = r7.i
            long r12 = r3.s
            long r12 = r12 + r7
        L_0x01b2:
            r7 = r12
        L_0x01b3:
            com.google.android.exoplayer2.Player$PositionInfo r14 = new com.google.android.exoplayer2.Player$PositionInfo
            long r21 = com.google.android.exoplayer2.C.usToMs(r7)
            long r23 = com.google.android.exoplayer2.C.usToMs(r12)
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r7 = r3.b
            int r8 = r7.b
            int r7 = r7.c
            r16 = r14
            r25 = r8
            r26 = r7
            r16.<init>(r17, r18, r19, r20, r21, r23, r25, r26)
            int r7 = r36.getCurrentWindowIndex()
            s8 r8 = r0.ae
            com.google.android.exoplayer2.Timeline r8 = r8.a
            boolean r8 = r8.isEmpty()
            if (r8 != 0) goto L_0x0202
            s8 r8 = r0.ae
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r12 = r8.b
            java.lang.Object r12 = r12.a
            com.google.android.exoplayer2.Timeline r8 = r8.a
            com.google.android.exoplayer2.Timeline$Period r13 = r0.k
            r8.getPeriodByUid(r12, r13)
            s8 r8 = r0.ae
            com.google.android.exoplayer2.Timeline r8 = r8.a
            int r8 = r8.getIndexOfPeriod(r12)
            s8 r13 = r0.ae
            com.google.android.exoplayer2.Timeline r13 = r13.a
            com.google.android.exoplayer2.Timeline$Window r15 = r0.a
            com.google.android.exoplayer2.Timeline$Window r13 = r13.getWindow(r7, r15)
            java.lang.Object r13 = r13.c
            r29 = r8
            r28 = r12
            r26 = r13
            goto L_0x0208
        L_0x0202:
            r26 = 0
            r28 = 0
            r29 = -1
        L_0x0208:
            long r30 = com.google.android.exoplayer2.C.usToMs(r43)
            com.google.android.exoplayer2.Player$PositionInfo r8 = new com.google.android.exoplayer2.Player$PositionInfo
            s8 r12 = r0.ae
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r12 = r12.b
            boolean r12 = r12.isAd()
            if (r12 == 0) goto L_0x0225
            s8 r12 = r0.ae
            long r12 = g(r12)
            long r12 = com.google.android.exoplayer2.C.usToMs(r12)
            r32 = r12
            goto L_0x0227
        L_0x0225:
            r32 = r30
        L_0x0227:
            s8 r12 = r0.ae
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r12 = r12.b
            int r13 = r12.b
            int r12 = r12.c
            r25 = r8
            r27 = r7
            r34 = r13
            r35 = r12
            r25.<init>(r26, r27, r28, r29, r30, r32, r34, r35)
            com.google.android.exoplayer2.util.ListenerSet<com.google.android.exoplayer2.Player$EventListener> r7 = r0.i
            n2 r12 = new n2
            r12.<init>((com.google.android.exoplayer2.Player.PositionInfo) r14, (com.google.android.exoplayer2.Player.PositionInfo) r8, (int) r2)
            r2 = 12
            r7.queueEvent(r2, r12)
        L_0x0246:
            if (r6 == 0) goto L_0x0253
            com.google.android.exoplayer2.util.ListenerSet<com.google.android.exoplayer2.Player$EventListener> r2 = r0.i
            k2 r6 = new k2
            r7 = 2
            r6.<init>(r10, r4, r7)
            r2.queueEvent(r5, r6)
        L_0x0253:
            com.google.android.exoplayer2.ExoPlaybackException r2 = r3.f
            com.google.android.exoplayer2.ExoPlaybackException r4 = r1.f
            if (r2 == r4) goto L_0x0268
            if (r4 == 0) goto L_0x0268
            com.google.android.exoplayer2.util.ListenerSet<com.google.android.exoplayer2.Player$EventListener> r2 = r0.i
            l2 r4 = new l2
            r6 = 3
            r4.<init>(r1, r6)
            r6 = 11
            r2.queueEvent(r6, r4)
        L_0x0268:
            com.google.android.exoplayer2.trackselection.TrackSelectorResult r2 = r3.i
            com.google.android.exoplayer2.trackselection.TrackSelectorResult r4 = r1.i
            if (r2 == r4) goto L_0x028a
            com.google.android.exoplayer2.trackselection.TrackSelector r2 = r0.e
            java.lang.Object r4 = r4.d
            r2.onSelectionActivated(r4)
            com.google.android.exoplayer2.trackselection.TrackSelectionArray r2 = new com.google.android.exoplayer2.trackselection.TrackSelectionArray
            com.google.android.exoplayer2.trackselection.TrackSelectorResult r4 = r1.i
            com.google.android.exoplayer2.trackselection.ExoTrackSelection[] r4 = r4.c
            r2.<init>(r4)
            com.google.android.exoplayer2.util.ListenerSet<com.google.android.exoplayer2.Player$EventListener> r4 = r0.i
            o2 r6 = new o2
            r7 = 0
            r6.<init>(r7, r1, r2)
            r2 = 2
            r4.queueEvent(r2, r6)
        L_0x028a:
            java.util.List<com.google.android.exoplayer2.metadata.Metadata> r2 = r3.j
            java.util.List<com.google.android.exoplayer2.metadata.Metadata> r4 = r1.j
            boolean r2 = r2.equals(r4)
            r4 = 4
            if (r2 != 0) goto L_0x02a0
            com.google.android.exoplayer2.util.ListenerSet<com.google.android.exoplayer2.Player$EventListener> r2 = r0.i
            l2 r6 = new l2
            r6.<init>(r1, r4)
            r7 = 3
            r2.queueEvent(r7, r6)
        L_0x02a0:
            if (r11 == 0) goto L_0x02b1
            com.google.android.exoplayer2.MediaMetadata r2 = r0.ad
            com.google.android.exoplayer2.util.ListenerSet<com.google.android.exoplayer2.Player$EventListener> r6 = r0.i
            q2 r7 = new q2
            r8 = 2
            r7.<init>(r8, r2)
            r2 = 15
            r6.queueEvent(r2, r7)
        L_0x02b1:
            boolean r2 = r3.g
            boolean r6 = r1.g
            r7 = 5
            if (r2 == r6) goto L_0x02c2
            com.google.android.exoplayer2.util.ListenerSet<com.google.android.exoplayer2.Player$EventListener> r2 = r0.i
            l2 r6 = new l2
            r6.<init>(r1, r7)
            r2.queueEvent(r4, r6)
        L_0x02c2:
            int r2 = r3.e
            int r4 = r1.e
            r6 = 6
            if (r2 != r4) goto L_0x02cf
            boolean r2 = r3.l
            boolean r4 = r1.l
            if (r2 == r4) goto L_0x02d9
        L_0x02cf:
            com.google.android.exoplayer2.util.ListenerSet<com.google.android.exoplayer2.Player$EventListener> r2 = r0.i
            l2 r4 = new l2
            r4.<init>(r1, r6)
            r2.queueEvent(r9, r4)
        L_0x02d9:
            int r2 = r3.e
            int r4 = r1.e
            r8 = 7
            if (r2 == r4) goto L_0x02ea
            com.google.android.exoplayer2.util.ListenerSet<com.google.android.exoplayer2.Player$EventListener> r2 = r0.i
            l2 r4 = new l2
            r4.<init>(r1, r8)
            r2.queueEvent(r7, r4)
        L_0x02ea:
            boolean r2 = r3.l
            boolean r4 = r1.l
            if (r2 == r4) goto L_0x02fc
            com.google.android.exoplayer2.util.ListenerSet<com.google.android.exoplayer2.Player$EventListener> r2 = r0.i
            k2 r4 = new k2
            r7 = r39
            r4.<init>(r1, r7, r5)
            r2.queueEvent(r6, r4)
        L_0x02fc:
            int r2 = r3.m
            int r4 = r1.m
            if (r2 == r4) goto L_0x030d
            com.google.android.exoplayer2.util.ListenerSet<com.google.android.exoplayer2.Player$EventListener> r2 = r0.i
            l2 r4 = new l2
            r6 = 0
            r4.<init>(r1, r6)
            r2.queueEvent(r8, r4)
        L_0x030d:
            boolean r2 = h(r3)
            boolean r4 = h(r37)
            if (r2 == r4) goto L_0x0323
            com.google.android.exoplayer2.util.ListenerSet<com.google.android.exoplayer2.Player$EventListener> r2 = r0.i
            l2 r4 = new l2
            r4.<init>(r1, r5)
            r5 = 8
            r2.queueEvent(r5, r4)
        L_0x0323:
            com.google.android.exoplayer2.PlaybackParameters r2 = r3.n
            com.google.android.exoplayer2.PlaybackParameters r4 = r1.n
            boolean r2 = r2.equals(r4)
            if (r2 != 0) goto L_0x033a
            com.google.android.exoplayer2.util.ListenerSet<com.google.android.exoplayer2.Player$EventListener> r2 = r0.i
            l2 r4 = new l2
            r5 = 2
            r4.<init>(r1, r5)
            r5 = 13
            r2.queueEvent(r5, r4)
        L_0x033a:
            if (r40 == 0) goto L_0x0347
            com.google.android.exoplayer2.util.ListenerSet<com.google.android.exoplayer2.Player$EventListener> r2 = r0.i
            m2 r4 = new m2
            r5 = 0
            r4.<init>(r5)
            r2.queueEvent(r9, r4)
        L_0x0347:
            r36.l()
            com.google.android.exoplayer2.util.ListenerSet<com.google.android.exoplayer2.Player$EventListener> r2 = r0.i
            r2.flushEvents()
            boolean r2 = r3.o
            boolean r4 = r1.o
            if (r2 == r4) goto L_0x036d
            java.util.concurrent.CopyOnWriteArraySet<com.google.android.exoplayer2.ExoPlayer$AudioOffloadListener> r2 = r0.j
            java.util.Iterator r2 = r2.iterator()
        L_0x035b:
            boolean r4 = r2.hasNext()
            if (r4 == 0) goto L_0x036d
            java.lang.Object r4 = r2.next()
            com.google.android.exoplayer2.ExoPlayer$AudioOffloadListener r4 = (com.google.android.exoplayer2.ExoPlayer.AudioOffloadListener) r4
            boolean r5 = r1.o
            r4.onExperimentalOffloadSchedulingEnabledChanged(r5)
            goto L_0x035b
        L_0x036d:
            boolean r2 = r3.p
            boolean r3 = r1.p
            if (r2 == r3) goto L_0x038b
            java.util.concurrent.CopyOnWriteArraySet<com.google.android.exoplayer2.ExoPlayer$AudioOffloadListener> r2 = r0.j
            java.util.Iterator r2 = r2.iterator()
        L_0x0379:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x038b
            java.lang.Object r3 = r2.next()
            com.google.android.exoplayer2.ExoPlayer$AudioOffloadListener r3 = (com.google.android.exoplayer2.ExoPlayer.AudioOffloadListener) r3
            boolean r4 = r1.p
            r3.onExperimentalSleepingForOffloadChanged(r4)
            goto L_0x0379
        L_0x038b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.a.m(s8, int, int, boolean, boolean, int, long, int):void");
    }

    public void moveMediaItems(int i2, int i3, int i4) {
        boolean z2;
        ArrayList arrayList = this.l;
        if (i2 < 0 || i2 > i3 || i3 > arrayList.size() || i4 < 0) {
            z2 = false;
        } else {
            z2 = true;
        }
        Assertions.checkArgument(z2);
        Timeline currentTimeline = getCurrentTimeline();
        this.u++;
        int min = Math.min(i4, arrayList.size() - (i3 - i2));
        Util.moveItems(arrayList, i2, i3, min);
        z8 z8Var = new z8(arrayList, this.aa);
        s8 i5 = i(this.ae, z8Var, e(currentTimeline, z8Var));
        this.h.moveMediaSources(i2, i3, min, this.aa);
        m(i5, 0, 1, false, false, 5, -9223372036854775807L, -1);
    }

    public void onMetadata(Metadata metadata) {
        MediaMetadata build = this.ad.buildUpon().populateFromMetadata(metadata).build();
        if (!build.equals(this.ad)) {
            this.ad = build;
            this.i.sendEvent(15, new q2(0, this));
        }
    }

    public void prepare() {
        s8 s8Var = this.ae;
        if (s8Var.e == 1) {
            s8 copyWithPlaybackError = s8Var.copyWithPlaybackError((ExoPlaybackException) null);
            s8 copyWithPlaybackState = copyWithPlaybackError.copyWithPlaybackState(copyWithPlaybackError.a.isEmpty() ? 4 : 2);
            this.u++;
            this.h.prepare();
            m(copyWithPlaybackState, 1, 1, false, false, 5, -9223372036854775807L, -1);
        }
    }

    public void release() {
        String hexString = Integer.toHexString(System.identityHashCode(this));
        String str = Util.e;
        String registeredModules = ExoPlayerLibraryInfo.registeredModules();
        StringBuilder l2 = y2.l(y2.c(registeredModules, y2.c(str, y2.c(hexString, 36))), "Release ", hexString, " [ExoPlayerLib/2.14.2] [", str);
        l2.append("] [");
        l2.append(registeredModules);
        l2.append("]");
        Log.i("ExoPlayerImpl", l2.toString());
        if (!this.h.release()) {
            this.i.sendEvent(11, new m2(1));
        }
        this.i.release();
        this.f.removeCallbacksAndMessages((Object) null);
        AnalyticsCollector analyticsCollector = this.o;
        if (analyticsCollector != null) {
            this.q.removeEventListener(analyticsCollector);
        }
        s8 copyWithPlaybackState = this.ae.copyWithPlaybackState(1);
        this.ae = copyWithPlaybackState;
        s8 copyWithLoadingMediaPeriodId = copyWithPlaybackState.copyWithLoadingMediaPeriodId(copyWithPlaybackState.b);
        this.ae = copyWithLoadingMediaPeriodId;
        copyWithLoadingMediaPeriodId.q = copyWithLoadingMediaPeriodId.s;
        this.ae.r = 0;
    }

    public void removeAudioOffloadListener(ExoPlayer.AudioOffloadListener audioOffloadListener) {
        this.j.remove(audioOffloadListener);
    }

    public void removeListener(Player.Listener listener) {
        removeListener((Player.EventListener) listener);
    }

    public void removeMediaItems(int i2, int i3) {
        s8 j2 = j(i2, Math.min(i3, this.l.size()));
        m(j2, 0, 1, false, !j2.b.a.equals(this.ae.b.a), 4, c(j2), -1);
    }

    @Deprecated
    public void retry() {
        prepare();
    }

    public void seekTo(int i2, long j2) {
        int i3 = i2;
        long j3 = j2;
        Timeline timeline = this.ae.a;
        if (i3 < 0 || (!timeline.isEmpty() && i3 >= timeline.getWindowCount())) {
            throw new IllegalSeekPositionException(timeline, i2, j3);
        }
        int i4 = 1;
        this.u++;
        if (isPlayingAd()) {
            Log.w("ExoPlayerImpl", "seekTo ignored because an ad is playing");
            ExoPlayerImplInternal.PlaybackInfoUpdate playbackInfoUpdate = new ExoPlayerImplInternal.PlaybackInfoUpdate(this.ae);
            playbackInfoUpdate.incrementPendingOperationAcks(1);
            this.g.onPlaybackInfoUpdate(playbackInfoUpdate);
            return;
        }
        if (getPlaybackState() != 1) {
            i4 = 2;
        }
        int currentWindowIndex = getCurrentWindowIndex();
        s8 i5 = i(this.ae.copyWithPlaybackState(i4), timeline, f(timeline, i2, j3));
        this.h.seekTo(timeline, i2, C.msToUs(j2));
        m(i5, 0, 1, true, true, 1, c(i5), currentWindowIndex);
    }

    public void setDeviceMuted(boolean z2) {
    }

    public void setDeviceVolume(int i2) {
    }

    public void setForegroundMode(boolean z2) {
        if (this.y != z2) {
            this.y = z2;
            if (!this.h.setForegroundMode(z2)) {
                stop(false, ExoPlaybackException.createForRenderer(new ExoTimeoutException(2)));
            }
        }
    }

    public void setMediaItems(List<MediaItem> list, boolean z2) {
        setMediaSources(b(list), z2);
    }

    public void setMediaSource(MediaSource mediaSource) {
        setMediaSources(Collections.singletonList(mediaSource));
    }

    public void setMediaSources(List<MediaSource> list) {
        setMediaSources(list, true);
    }

    public void setPauseAtEndOfMediaItems(boolean z2) {
        if (this.ab != z2) {
            this.ab = z2;
            this.h.setPauseAtEndOfWindow(z2);
        }
    }

    public void setPlayWhenReady(boolean z2) {
        setPlayWhenReady(z2, 0, 1);
    }

    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        if (playbackParameters == null) {
            playbackParameters = PlaybackParameters.h;
        }
        if (!this.ae.n.equals(playbackParameters)) {
            s8 copyWithPlaybackParameters = this.ae.copyWithPlaybackParameters(playbackParameters);
            this.u++;
            this.h.setPlaybackParameters(playbackParameters);
            m(copyWithPlaybackParameters, 0, 1, false, false, 5, -9223372036854775807L, -1);
        }
    }

    public void setRepeatMode(int i2) {
        if (this.s != i2) {
            this.s = i2;
            this.h.setRepeatMode(i2);
            p2 p2Var = new p2(i2);
            ListenerSet<Player.EventListener> listenerSet = this.i;
            listenerSet.queueEvent(9, p2Var);
            l();
            listenerSet.flushEvents();
        }
    }

    public void setSeekParameters(@Nullable SeekParameters seekParameters) {
        if (seekParameters == null) {
            seekParameters = SeekParameters.c;
        }
        if (!this.z.equals(seekParameters)) {
            this.z = seekParameters;
            this.h.setSeekParameters(seekParameters);
        }
    }

    public void setShuffleModeEnabled(boolean z2) {
        if (this.t != z2) {
            this.t = z2;
            this.h.setShuffleModeEnabled(z2);
            j2 j2Var = new j2(z2);
            ListenerSet<Player.EventListener> listenerSet = this.i;
            listenerSet.queueEvent(10, j2Var);
            l();
            listenerSet.flushEvents();
        }
    }

    public void setShuffleOrder(ShuffleOrder shuffleOrder) {
        z8 z8Var = new z8(this.l, this.aa);
        s8 i2 = i(this.ae, z8Var, f(z8Var, getCurrentWindowIndex(), getCurrentPosition()));
        this.u++;
        this.aa = shuffleOrder;
        this.h.setShuffleOrder(shuffleOrder);
        m(i2, 0, 1, false, false, 5, -9223372036854775807L, -1);
    }

    public void setVideoSurface(@Nullable Surface surface) {
    }

    public void setVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder) {
    }

    public void setVideoSurfaceView(@Nullable SurfaceView surfaceView) {
    }

    public void setVideoTextureView(@Nullable TextureView textureView) {
    }

    public void setVolume(float f2) {
    }

    public void stop(boolean z2) {
        stop(z2, (ExoPlaybackException) null);
    }

    public void addListener(Player.EventListener eventListener) {
        this.i.add(eventListener);
    }

    public void addMediaSource(int i2, MediaSource mediaSource) {
        addMediaSources(i2, Collections.singletonList(mediaSource));
    }

    public void addMediaSources(int i2, List<MediaSource> list) {
        Assertions.checkArgument(i2 >= 0);
        Timeline currentTimeline = getCurrentTimeline();
        this.u++;
        ArrayList a = a(i2, list);
        z8 z8Var = new z8(this.l, this.aa);
        s8 i3 = i(this.ae, z8Var, e(currentTimeline, z8Var));
        this.h.addMediaSources(i2, a, this.aa);
        m(i3, 0, 1, false, false, 5, -9223372036854775807L, -1);
    }

    public ImmutableList<Cue> getCurrentCues() {
        return ImmutableList.of();
    }

    public void removeListener(Player.EventListener eventListener) {
        this.i.remove(eventListener);
    }

    public void setMediaItems(List<MediaItem> list, int i2, long j2) {
        setMediaSources(b(list), i2, j2);
    }

    public void setMediaSource(MediaSource mediaSource, long j2) {
        setMediaSources(Collections.singletonList(mediaSource), 0, j2);
    }

    public void setMediaSources(List<MediaSource> list, boolean z2) {
        k(list, -1, -9223372036854775807L, z2);
    }

    public void setPlayWhenReady(boolean z2, int i2, int i3) {
        s8 s8Var = this.ae;
        if (s8Var.l != z2 || s8Var.m != i2) {
            this.u++;
            s8 copyWithPlayWhenReady = s8Var.copyWithPlayWhenReady(z2, i2);
            this.h.setPlayWhenReady(z2, i2);
            m(copyWithPlayWhenReady, 0, i3, false, false, 5, -9223372036854775807L, -1);
        }
    }

    public void stop(boolean z2, @Nullable ExoPlaybackException exoPlaybackException) {
        s8 s8Var;
        if (z2) {
            s8Var = j(0, this.l.size()).copyWithPlaybackError((ExoPlaybackException) null);
        } else {
            s8 s8Var2 = this.ae;
            s8Var = s8Var2.copyWithLoadingMediaPeriodId(s8Var2.b);
            s8Var.q = s8Var.s;
            s8Var.r = 0;
        }
        s8 copyWithPlaybackState = s8Var.copyWithPlaybackState(1);
        if (exoPlaybackException != null) {
            copyWithPlaybackState = copyWithPlaybackState.copyWithPlaybackError(exoPlaybackException);
        }
        s8 s8Var3 = copyWithPlaybackState;
        this.u++;
        this.h.stop();
        m(s8Var3, 0, 1, false, s8Var3.a.isEmpty() && !this.ae.a.isEmpty(), 4, c(s8Var3), -1);
    }

    public void setMediaSources(List<MediaSource> list, int i2, long j2) {
        k(list, i2, j2, false);
    }

    public void setMediaSource(MediaSource mediaSource, boolean z2) {
        setMediaSources(Collections.singletonList(mediaSource), z2);
    }

    @Deprecated
    public void prepare(MediaSource mediaSource) {
        setMediaSource(mediaSource);
        prepare();
    }

    @Deprecated
    public void prepare(MediaSource mediaSource, boolean z2, boolean z3) {
        setMediaSource(mediaSource, z2);
        prepare();
    }
}
