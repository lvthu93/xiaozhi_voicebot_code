package com.google.android.exoplayer2.analytics;

import android.os.Looper;
import android.util.SparseArray;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.core.view.InputDeviceCompat;
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
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.device.DeviceInfo;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaPeriodId;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.ListenerSet;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.List;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.java_websocket.framing.CloseFrame;

public class AnalyticsCollector implements Player.Listener, AudioRendererEventListener, VideoRendererEventListener, MediaSourceEventListener, BandwidthMeter.EventListener, DrmSessionEventListener {
    public final Clock c;
    public final Timeline.Period f;
    public final Timeline.Window g = new Timeline.Window();
    public final a h;
    public final SparseArray<AnalyticsListener.EventTime> i;
    public ListenerSet<AnalyticsListener> j;
    public Player k;
    public boolean l;

    public static final class a {
        public final Timeline.Period a;
        public ImmutableList<MediaSource.MediaPeriodId> b = ImmutableList.of();
        public ImmutableMap<MediaSource.MediaPeriodId, Timeline> c = ImmutableMap.of();
        @Nullable
        public MediaSource.MediaPeriodId d;
        public MediaSource.MediaPeriodId e;
        public MediaSource.MediaPeriodId f;

        public a(Timeline.Period period) {
            this.a = period;
        }

        @Nullable
        public static MediaSource.MediaPeriodId b(Player player, ImmutableList<MediaSource.MediaPeriodId> immutableList, @Nullable MediaSource.MediaPeriodId mediaPeriodId, Timeline.Period period) {
            Object obj;
            int i;
            Timeline currentTimeline = player.getCurrentTimeline();
            int currentPeriodIndex = player.getCurrentPeriodIndex();
            if (currentTimeline.isEmpty()) {
                obj = null;
            } else {
                obj = currentTimeline.getUidOfPeriod(currentPeriodIndex);
            }
            if (player.isPlayingAd() || currentTimeline.isEmpty()) {
                i = -1;
            } else {
                i = currentTimeline.getPeriod(currentPeriodIndex, period).getAdGroupIndexAfterPositionUs(C.msToUs(player.getCurrentPosition()) - period.getPositionInWindowUs());
            }
            for (int i2 = 0; i2 < immutableList.size(); i2++) {
                MediaSource.MediaPeriodId mediaPeriodId2 = immutableList.get(i2);
                if (c(mediaPeriodId2, obj, player.isPlayingAd(), player.getCurrentAdGroupIndex(), player.getCurrentAdIndexInAdGroup(), i)) {
                    return mediaPeriodId2;
                }
            }
            if (immutableList.isEmpty() && mediaPeriodId != null) {
                if (c(mediaPeriodId, obj, player.isPlayingAd(), player.getCurrentAdGroupIndex(), player.getCurrentAdIndexInAdGroup(), i)) {
                    return mediaPeriodId;
                }
            }
            return null;
        }

        public static boolean c(MediaSource.MediaPeriodId mediaPeriodId, @Nullable Object obj, boolean z, int i, int i2, int i3) {
            if (!mediaPeriodId.a.equals(obj)) {
                return false;
            }
            int i4 = mediaPeriodId.b;
            if ((z && i4 == i && mediaPeriodId.c == i2) || (!z && i4 == -1 && mediaPeriodId.e == i3)) {
                return true;
            }
            return false;
        }

        public final void a(ImmutableMap.Builder<MediaSource.MediaPeriodId, Timeline> builder, @Nullable MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline) {
            if (mediaPeriodId != null) {
                if (timeline.getIndexOfPeriod(mediaPeriodId.a) != -1) {
                    builder.put(mediaPeriodId, timeline);
                    return;
                }
                Timeline timeline2 = this.c.get(mediaPeriodId);
                if (timeline2 != null) {
                    builder.put(mediaPeriodId, timeline2);
                }
            }
        }

        public final void d(Timeline timeline) {
            ImmutableMap.Builder builder = ImmutableMap.builder();
            if (this.b.isEmpty()) {
                a(builder, this.e, timeline);
                if (!Objects.equal(this.f, this.e)) {
                    a(builder, this.f, timeline);
                }
                if (!Objects.equal(this.d, this.e) && !Objects.equal(this.d, this.f)) {
                    a(builder, this.d, timeline);
                }
            } else {
                for (int i = 0; i < this.b.size(); i++) {
                    a(builder, this.b.get(i), timeline);
                }
                if (!this.b.contains(this.d)) {
                    a(builder, this.d, timeline);
                }
            }
            this.c = builder.build();
        }

        @Nullable
        public MediaSource.MediaPeriodId getCurrentPlayerMediaPeriod() {
            return this.d;
        }

        @Nullable
        public MediaSource.MediaPeriodId getLoadingMediaPeriod() {
            if (this.b.isEmpty()) {
                return null;
            }
            return (MediaSource.MediaPeriodId) Iterables.getLast(this.b);
        }

        @Nullable
        public Timeline getMediaPeriodIdTimeline(MediaSource.MediaPeriodId mediaPeriodId) {
            return this.c.get(mediaPeriodId);
        }

        @Nullable
        public MediaSource.MediaPeriodId getPlayingMediaPeriod() {
            return this.e;
        }

        @Nullable
        public MediaSource.MediaPeriodId getReadingMediaPeriod() {
            return this.f;
        }

        public void onPositionDiscontinuity(Player player) {
            this.d = b(player, this.b, this.e, this.a);
        }

        public void onQueueUpdated(List<MediaSource.MediaPeriodId> list, @Nullable MediaSource.MediaPeriodId mediaPeriodId, Player player) {
            this.b = ImmutableList.copyOf(list);
            if (!list.isEmpty()) {
                this.e = list.get(0);
                this.f = (MediaSource.MediaPeriodId) Assertions.checkNotNull(mediaPeriodId);
            }
            if (this.d == null) {
                this.d = b(player, this.b, this.e, this.a);
            }
            d(player.getCurrentTimeline());
        }

        public void onTimelineChanged(Player player) {
            this.d = b(player, this.b, this.e, this.a);
            d(player.getCurrentTimeline());
        }
    }

    public AnalyticsCollector(Clock clock) {
        this.c = (Clock) Assertions.checkNotNull(clock);
        this.j = new ListenerSet<>(Util.getCurrentOrMainLooper(), clock, new f2(15));
        Timeline.Period period = new Timeline.Period();
        this.f = period;
        this.h = new a(period);
        this.i = new SparseArray<>();
    }

    public final AnalyticsListener.EventTime a() {
        return c(this.h.getCurrentPlayerMediaPeriod());
    }

    @CallSuper
    public void addListener(AnalyticsListener analyticsListener) {
        Assertions.checkNotNull(analyticsListener);
        this.j.add(analyticsListener);
    }

    @RequiresNonNull({"player"})
    public final AnalyticsListener.EventTime b(Timeline timeline, int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        MediaSource.MediaPeriodId mediaPeriodId2;
        boolean z;
        long j2;
        Timeline timeline2 = timeline;
        int i3 = i2;
        if (timeline.isEmpty()) {
            mediaPeriodId2 = null;
        } else {
            mediaPeriodId2 = mediaPeriodId;
        }
        long elapsedRealtime = this.c.elapsedRealtime();
        boolean z2 = true;
        if (!timeline2.equals(this.k.getCurrentTimeline()) || i3 != this.k.getCurrentWindowIndex()) {
            z = false;
        } else {
            z = true;
        }
        long j3 = 0;
        if (mediaPeriodId2 != null && mediaPeriodId2.isAd()) {
            if (!(z && this.k.getCurrentAdGroupIndex() == mediaPeriodId2.b && this.k.getCurrentAdIndexInAdGroup() == mediaPeriodId2.c)) {
                z2 = false;
            }
            if (z2) {
                j3 = this.k.getCurrentPosition();
            }
        } else if (z) {
            j2 = this.k.getContentPosition();
            return new AnalyticsListener.EventTime(elapsedRealtime, timeline, i2, mediaPeriodId2, j2, this.k.getCurrentTimeline(), this.k.getCurrentWindowIndex(), this.h.getCurrentPlayerMediaPeriod(), this.k.getCurrentPosition(), this.k.getTotalBufferedDuration());
        } else if (!timeline.isEmpty()) {
            j3 = timeline2.getWindow(i3, this.g).getDefaultPositionMs();
        }
        j2 = j3;
        return new AnalyticsListener.EventTime(elapsedRealtime, timeline, i2, mediaPeriodId2, j2, this.k.getCurrentTimeline(), this.k.getCurrentWindowIndex(), this.h.getCurrentPlayerMediaPeriod(), this.k.getCurrentPosition(), this.k.getTotalBufferedDuration());
    }

    public final AnalyticsListener.EventTime c(@Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        Timeline timeline;
        boolean z;
        Assertions.checkNotNull(this.k);
        if (mediaPeriodId == null) {
            timeline = null;
        } else {
            timeline = this.h.getMediaPeriodIdTimeline(mediaPeriodId);
        }
        if (mediaPeriodId != null && timeline != null) {
            return b(timeline, timeline.getPeriodByUid(mediaPeriodId.a, this.f).g, mediaPeriodId);
        }
        int currentWindowIndex = this.k.getCurrentWindowIndex();
        Timeline currentTimeline = this.k.getCurrentTimeline();
        if (currentWindowIndex < currentTimeline.getWindowCount()) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            currentTimeline = Timeline.c;
        }
        return b(currentTimeline, currentWindowIndex, (MediaSource.MediaPeriodId) null);
    }

    public final AnalyticsListener.EventTime d(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        Assertions.checkNotNull(this.k);
        boolean z = false;
        if (mediaPeriodId != null) {
            if (this.h.getMediaPeriodIdTimeline(mediaPeriodId) != null) {
                z = true;
            }
            if (z) {
                return c(mediaPeriodId);
            }
            return b(Timeline.c, i2, mediaPeriodId);
        }
        Timeline currentTimeline = this.k.getCurrentTimeline();
        if (i2 < currentTimeline.getWindowCount()) {
            z = true;
        }
        if (!z) {
            currentTimeline = Timeline.c;
        }
        return b(currentTimeline, i2, (MediaSource.MediaPeriodId) null);
    }

    public final AnalyticsListener.EventTime e() {
        return c(this.h.getReadingMediaPeriod());
    }

    public final void f(AnalyticsListener.EventTime eventTime, int i2, ListenerSet.Event<AnalyticsListener> event) {
        this.i.put(i2, eventTime);
        this.j.sendEvent(i2, event);
    }

    public final void notifySeekStarted() {
        if (!this.l) {
            AnalyticsListener.EventTime a2 = a();
            this.l = true;
            f(a2, -1, new ag(a2, 5));
        }
    }

    public final void onAudioAttributesChanged(AudioAttributes audioAttributes) {
        AnalyticsListener.EventTime e = e();
        f(e, PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW, new o2(7, e, audioAttributes));
    }

    public final void onAudioCodecError(Exception exc) {
        AnalyticsListener.EventTime e = e();
        f(e, 1037, new ak(e, exc, 3));
    }

    public final void onAudioDecoderInitialized(String str, long j2, long j3) {
        AnalyticsListener.EventTime e = e();
        f(e, 1009, new al(e, str, j3, j2, 0));
    }

    public final void onAudioDecoderReleased(String str) {
        AnalyticsListener.EventTime e = e();
        f(e, 1013, new ax(e, str, 1));
    }

    public final void onAudioDisabled(DecoderCounters decoderCounters) {
        AnalyticsListener.EventTime c2 = c(this.h.getPlayingMediaPeriod());
        f(c2, 1014, new au(c2, 0, decoderCounters));
    }

    public final void onAudioEnabled(DecoderCounters decoderCounters) {
        AnalyticsListener.EventTime e = e();
        f(e, 1008, new au(e, 1, decoderCounters));
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onAudioInputFormatChanged(Format format) {
        bp.f(this, format);
    }

    public final void onAudioInputFormatChanged(Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation) {
        AnalyticsListener.EventTime e = e();
        f(e, 1010, new ay(e, format, decoderReuseEvaluation, 1));
    }

    public final void onAudioPositionAdvancing(long j2) {
        AnalyticsListener.EventTime e = e();
        f(e, 1011, new aq(e, j2));
    }

    public final void onAudioSessionIdChanged(int i2) {
        AnalyticsListener.EventTime e = e();
        f(e, 1015, new as(e, i2, 5));
    }

    public final void onAudioSinkError(Exception exc) {
        AnalyticsListener.EventTime e = e();
        f(e, PointerIconCompat.TYPE_ZOOM_IN, new ak(e, exc, 2));
    }

    public final void onAudioUnderrun(int i2, long j2, long j3) {
        AnalyticsListener.EventTime e = e();
        f(e, 1012, new an(e, i2, j2, j3, 1));
    }

    public /* bridge */ /* synthetic */ void onAvailableCommandsChanged(Player.Commands commands) {
        u8.c(this, commands);
    }

    public final void onBandwidthSample(int i2, long j2, long j3) {
        AnalyticsListener.EventTime c2 = c(this.h.getLoadingMediaPeriod());
        f(c2, 1006, new an(c2, i2, j2, j3, 0));
    }

    public /* bridge */ /* synthetic */ void onCues(List list) {
        u8.d(this, list);
    }

    public /* bridge */ /* synthetic */ void onDeviceInfoChanged(DeviceInfo deviceInfo) {
        u8.e(this, deviceInfo);
    }

    public /* bridge */ /* synthetic */ void onDeviceVolumeChanged(int i2, boolean z) {
        u8.f(this, i2, z);
    }

    public final void onDownstreamFormatChanged(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
        AnalyticsListener.EventTime d = d(i2, mediaPeriodId);
        f(d, PointerIconCompat.TYPE_WAIT, new am(d, mediaLoadData, 1));
    }

    public final void onDrmKeysLoaded(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        AnalyticsListener.EventTime d = d(i2, mediaPeriodId);
        f(d, 1031, new ag(d, 2));
    }

    public final void onDrmKeysRemoved(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        AnalyticsListener.EventTime d = d(i2, mediaPeriodId);
        f(d, 1034, new ag(d, 1));
    }

    public final void onDrmKeysRestored(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        AnalyticsListener.EventTime d = d(i2, mediaPeriodId);
        f(d, 1033, new ag(d, 3));
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onDrmSessionAcquired(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        t1.d(this, i2, mediaPeriodId);
    }

    public final void onDrmSessionAcquired(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, int i3) {
        AnalyticsListener.EventTime d = d(i2, mediaPeriodId);
        f(d, 1030, new as(d, i3, 3));
    }

    public final void onDrmSessionManagerError(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, Exception exc) {
        AnalyticsListener.EventTime d = d(i2, mediaPeriodId);
        f(d, 1032, new ak(d, exc, 0));
    }

    public final void onDrmSessionReleased(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        AnalyticsListener.EventTime d = d(i2, mediaPeriodId);
        f(d, 1035, new ag(d, 6));
    }

    public final void onDroppedFrames(int i2, long j2) {
        AnalyticsListener.EventTime c2 = c(this.h.getPlayingMediaPeriod());
        f(c2, 1023, new av(c2, i2, j2));
    }

    public /* bridge */ /* synthetic */ void onEvents(Player player, Player.Events events) {
        u8.g(this, player, events);
    }

    public final void onIsLoadingChanged(boolean z) {
        AnalyticsListener.EventTime a2 = a();
        f(a2, 4, new ar(a2, z, 2));
    }

    public void onIsPlayingChanged(boolean z) {
        AnalyticsListener.EventTime a2 = a();
        f(a2, 8, new ar(a2, z, 3));
    }

    public final void onLoadCanceled(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        AnalyticsListener.EventTime d = d(i2, mediaPeriodId);
        f(d, 1002, new at(d, loadEventInfo, mediaLoadData, 0));
    }

    public final void onLoadCompleted(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        AnalyticsListener.EventTime d = d(i2, mediaPeriodId);
        f(d, 1001, new at(d, loadEventInfo, mediaLoadData, 2));
    }

    public final void onLoadError(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z) {
        AnalyticsListener.EventTime d = d(i2, mediaPeriodId);
        f(d, 1003, new aj(d, loadEventInfo, mediaLoadData, iOException, z));
    }

    public final void onLoadStarted(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        AnalyticsListener.EventTime d = d(i2, mediaPeriodId);
        f(d, 1000, new at(d, loadEventInfo, mediaLoadData, 1));
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onLoadingChanged(boolean z) {
        t8.e(this, z);
    }

    public final void onMediaItemTransition(@Nullable MediaItem mediaItem, int i2) {
        AnalyticsListener.EventTime a2 = a();
        f(a2, 1, new n2(a2, mediaItem, i2));
    }

    public void onMediaMetadataChanged(MediaMetadata mediaMetadata) {
        AnalyticsListener.EventTime a2 = a();
        f(a2, 15, new o2(5, a2, mediaMetadata));
    }

    public final void onMetadata(Metadata metadata) {
        AnalyticsListener.EventTime a2 = a();
        f(a2, 1007, new o2(2, a2, metadata));
    }

    public final void onPlayWhenReadyChanged(boolean z, int i2) {
        AnalyticsListener.EventTime a2 = a();
        f(a2, 6, new aw(a2, z, i2, 0));
    }

    public final void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        AnalyticsListener.EventTime a2 = a();
        f(a2, 13, new o2(6, a2, playbackParameters));
    }

    public final void onPlaybackStateChanged(int i2) {
        AnalyticsListener.EventTime a2 = a();
        f(a2, 5, new as(a2, i2, 4));
    }

    public final void onPlaybackSuppressionReasonChanged(int i2) {
        AnalyticsListener.EventTime a2 = a();
        f(a2, 7, new as(a2, i2, 0));
    }

    public final void onPlayerError(ExoPlaybackException exoPlaybackException) {
        AnalyticsListener.EventTime eventTime;
        MediaPeriodId mediaPeriodId = exoPlaybackException.k;
        if (mediaPeriodId != null) {
            eventTime = c(new MediaSource.MediaPeriodId(mediaPeriodId));
        } else {
            eventTime = a();
        }
        f(eventTime, 11, new o2(3, eventTime, exoPlaybackException));
    }

    public final void onPlayerStateChanged(boolean z, int i2) {
        AnalyticsListener.EventTime a2 = a();
        f(a2, -1, new aw(a2, z, i2, 1));
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onPositionDiscontinuity(int i2) {
        t8.n(this, i2);
    }

    public final void onPositionDiscontinuity(Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i2) {
        if (i2 == 1) {
            this.l = false;
        }
        this.h.onPositionDiscontinuity((Player) Assertions.checkNotNull(this.k));
        AnalyticsListener.EventTime a2 = a();
        f(a2, 12, new ap(a2, positionInfo, positionInfo2, i2));
    }

    public /* bridge */ /* synthetic */ void onRenderedFirstFrame() {
        u8.v(this);
    }

    public final void onRenderedFirstFrame(Object obj, long j2) {
        AnalyticsListener.EventTime e = e();
        f(e, 1027, new az(e, obj, j2));
    }

    public final void onRepeatModeChanged(int i2) {
        AnalyticsListener.EventTime a2 = a();
        f(a2, 9, new as(a2, i2, 1));
    }

    public final void onSeekProcessed() {
        AnalyticsListener.EventTime a2 = a();
        f(a2, -1, new ag(a2, 0));
    }

    public final void onShuffleModeEnabledChanged(boolean z) {
        AnalyticsListener.EventTime a2 = a();
        f(a2, 10, new ar(a2, z, 1));
    }

    public final void onSkipSilenceEnabledChanged(boolean z) {
        AnalyticsListener.EventTime e = e();
        f(e, PointerIconCompat.TYPE_TOP_LEFT_DIAGONAL_DOUBLE_ARROW, new ar(e, z, 0));
    }

    public final void onStaticMetadataChanged(List<Metadata> list) {
        AnalyticsListener.EventTime a2 = a();
        f(a2, 3, new o2(4, a2, list));
    }

    public void onSurfaceSizeChanged(int i2, int i3) {
        AnalyticsListener.EventTime e = e();
        f(e, 1029, new ai(e, i2, i3));
    }

    public final void onTimelineChanged(Timeline timeline, int i2) {
        this.h.onTimelineChanged((Player) Assertions.checkNotNull(this.k));
        AnalyticsListener.EventTime a2 = a();
        f(a2, 0, new as(a2, i2, 2));
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onTimelineChanged(Timeline timeline, @Nullable Object obj, int i2) {
        t8.u(this, timeline, obj, i2);
    }

    public final void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
        AnalyticsListener.EventTime a2 = a();
        f(a2, 2, new ay(a2, trackGroupArray, trackSelectionArray, 2));
    }

    public final void onUpstreamDiscarded(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
        AnalyticsListener.EventTime d = d(i2, mediaPeriodId);
        f(d, CloseFrame.NOCODE, new am(d, mediaLoadData, 0));
    }

    public final void onVideoCodecError(Exception exc) {
        AnalyticsListener.EventTime e = e();
        f(e, 1038, new ak(e, exc, 1));
    }

    public final void onVideoDecoderInitialized(String str, long j2, long j3) {
        AnalyticsListener.EventTime e = e();
        f(e, PointerIconCompat.TYPE_GRABBING, new al(e, str, j3, j2, 1));
    }

    public final void onVideoDecoderReleased(String str) {
        AnalyticsListener.EventTime e = e();
        f(e, 1024, new ax(e, str, 0));
    }

    public final void onVideoDisabled(DecoderCounters decoderCounters) {
        AnalyticsListener.EventTime c2 = c(this.h.getPlayingMediaPeriod());
        f(c2, InputDeviceCompat.SOURCE_GAMEPAD, new au(c2, 2, decoderCounters));
    }

    public final void onVideoEnabled(DecoderCounters decoderCounters) {
        AnalyticsListener.EventTime e = e();
        f(e, PointerIconCompat.TYPE_GRAB, new au(e, 3, decoderCounters));
    }

    public final void onVideoFrameProcessingOffset(long j2, int i2) {
        AnalyticsListener.EventTime c2 = c(this.h.getPlayingMediaPeriod());
        f(c2, 1026, new av(c2, j2, i2));
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onVideoInputFormatChanged(Format format) {
        pd.i(this, format);
    }

    public final void onVideoInputFormatChanged(Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation) {
        AnalyticsListener.EventTime e = e();
        f(e, 1022, new ay(e, format, decoderReuseEvaluation, 0));
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onVideoSizeChanged(int i2, int i3, int i4, float f2) {
        od.c(this, i2, i3, i4, f2);
    }

    public final void onVideoSizeChanged(VideoSize videoSize) {
        AnalyticsListener.EventTime e = e();
        f(e, 1028, new o2(1, e, videoSize));
    }

    public final void onVolumeChanged(float f2) {
        AnalyticsListener.EventTime e = e();
        f(e, PointerIconCompat.TYPE_ZOOM_OUT, new ao(e, f2));
    }

    @CallSuper
    public void release() {
        AnalyticsListener.EventTime a2 = a();
        this.i.put(1036, a2);
        this.j.lazyRelease(1036, new ag(a2, 4));
    }

    @CallSuper
    public void removeListener(AnalyticsListener analyticsListener) {
        this.j.remove(analyticsListener);
    }

    @CallSuper
    public void setPlayer(Player player, Looper looper) {
        boolean z;
        if (this.k == null || this.h.b.isEmpty()) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        this.k = (Player) Assertions.checkNotNull(player);
        this.j = this.j.copy(looper, new ah(0, this, player));
    }

    public final void updateMediaPeriodQueueInfo(List<MediaSource.MediaPeriodId> list, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        this.h.onQueueUpdated(list, mediaPeriodId, (Player) Assertions.checkNotNull(this.k));
    }
}
