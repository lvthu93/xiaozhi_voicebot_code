package com.google.android.exoplayer2.util;

import android.os.SystemClock;
import android.text.TextUtils;
import androidx.annotation.Nullable;
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
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.video.VideoSize;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class EventLogger implements AnalyticsListener {
    public static final NumberFormat f;
    @Nullable
    public final MappingTrackSelector a;
    public final String b;
    public final Timeline.Window c;
    public final Timeline.Period d;
    public final long e;

    static {
        NumberFormat instance = NumberFormat.getInstance(Locale.US);
        f = instance;
        instance.setMinimumFractionDigits(2);
        instance.setMaximumFractionDigits(2);
        instance.setGroupingUsed(false);
    }

    public EventLogger(@Nullable MappingTrackSelector mappingTrackSelector) {
        this(mappingTrackSelector, "EventLogger");
    }

    public static String c(long j) {
        if (j == -9223372036854775807L) {
            return "?";
        }
        return f.format((double) (((float) j) / 1000.0f));
    }

    public final String a(AnalyticsListener.EventTime eventTime, String str, @Nullable String str2, @Nullable Throwable th) {
        String b2 = b(eventTime);
        StringBuilder sb = new StringBuilder(y2.c(b2, str.length() + 2));
        sb.append(str);
        sb.append(" [");
        sb.append(b2);
        String sb2 = sb.toString();
        if (str2 != null) {
            String valueOf = String.valueOf(sb2);
            StringBuilder sb3 = new StringBuilder(str2.length() + valueOf.length() + 2);
            sb3.append(valueOf);
            sb3.append(", ");
            sb3.append(str2);
            sb2 = sb3.toString();
        }
        String throwableString = Log.getThrowableString(th);
        if (!TextUtils.isEmpty(throwableString)) {
            String valueOf2 = String.valueOf(sb2);
            String replace = throwableString.replace("\n", "\n  ");
            StringBuilder sb4 = new StringBuilder(y2.c(replace, valueOf2.length() + 4));
            sb4.append(valueOf2);
            sb4.append("\n  ");
            sb4.append(replace);
            sb4.append(10);
            sb2 = sb4.toString();
        }
        return String.valueOf(sb2).concat("]");
    }

    public final String b(AnalyticsListener.EventTime eventTime) {
        String d2 = y2.d(18, "window=", eventTime.c);
        MediaSource.MediaPeriodId mediaPeriodId = eventTime.d;
        if (mediaPeriodId != null) {
            String valueOf = String.valueOf(d2);
            int indexOfPeriod = eventTime.b.getIndexOfPeriod(mediaPeriodId.a);
            StringBuilder sb = new StringBuilder(valueOf.length() + 20);
            sb.append(valueOf);
            sb.append(", period=");
            sb.append(indexOfPeriod);
            d2 = sb.toString();
            if (mediaPeriodId.isAd()) {
                String valueOf2 = String.valueOf(d2);
                int i = mediaPeriodId.b;
                StringBuilder sb2 = new StringBuilder(valueOf2.length() + 21);
                sb2.append(valueOf2);
                sb2.append(", adGroup=");
                sb2.append(i);
                String valueOf3 = String.valueOf(sb2.toString());
                int i2 = mediaPeriodId.c;
                StringBuilder sb3 = new StringBuilder(valueOf3.length() + 16);
                sb3.append(valueOf3);
                sb3.append(", ad=");
                sb3.append(i2);
                d2 = sb3.toString();
            }
        }
        String c2 = c(eventTime.a - this.e);
        String c3 = c(eventTime.e);
        return y2.k(y2.l(y2.c(d2, y2.c(c3, y2.c(c2, 23))), "eventTime=", c2, ", mediaPos=", c3), ", ", d2);
    }

    public final void d(String str) {
        Log.d(this.b, str);
    }

    public final void e(Metadata metadata, String str) {
        for (int i = 0; i < metadata.length(); i++) {
            String valueOf = String.valueOf(metadata.get(i));
            StringBuilder sb = new StringBuilder(valueOf.length() + str.length());
            sb.append(str);
            sb.append(valueOf);
            d(sb.toString());
        }
    }

    public void onAudioAttributesChanged(AnalyticsListener.EventTime eventTime, AudioAttributes audioAttributes) {
        int i = audioAttributes.c;
        StringBuilder sb = new StringBuilder(47);
        sb.append(i);
        sb.append(",");
        sb.append(audioAttributes.f);
        sb.append(",");
        sb.append(audioAttributes.g);
        sb.append(",");
        sb.append(audioAttributes.h);
        d(a(eventTime, "audioAttributes", sb.toString(), (Throwable) null));
    }

    public /* bridge */ /* synthetic */ void onAudioCodecError(AnalyticsListener.EventTime eventTime, Exception exc) {
        ba.b(this, eventTime, exc);
    }

    public void onAudioDecoderInitialized(AnalyticsListener.EventTime eventTime, String str, long j) {
        d(a(eventTime, "audioDecoderInitialized", str, (Throwable) null));
    }

    public /* bridge */ /* synthetic */ void onAudioDecoderInitialized(AnalyticsListener.EventTime eventTime, String str, long j, long j2) {
        ba.d(this, eventTime, str, j, j2);
    }

    public void onAudioDecoderReleased(AnalyticsListener.EventTime eventTime, String str) {
        d(a(eventTime, "audioDecoderReleased", str, (Throwable) null));
    }

    public void onAudioDisabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
        d(a(eventTime, "audioDisabled", (String) null, (Throwable) null));
    }

    public void onAudioEnabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
        d(a(eventTime, "audioEnabled", (String) null, (Throwable) null));
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onAudioInputFormatChanged(AnalyticsListener.EventTime eventTime, Format format) {
        ba.h(this, eventTime, format);
    }

    public void onAudioInputFormatChanged(AnalyticsListener.EventTime eventTime, Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation) {
        d(a(eventTime, "audioInputFormat", Format.toLogString(format), (Throwable) null));
    }

    public /* bridge */ /* synthetic */ void onAudioPositionAdvancing(AnalyticsListener.EventTime eventTime, long j) {
        ba.j(this, eventTime, j);
    }

    public void onAudioSessionIdChanged(AnalyticsListener.EventTime eventTime, int i) {
        d(a(eventTime, "audioSessionId", Integer.toString(i), (Throwable) null));
    }

    public /* bridge */ /* synthetic */ void onAudioSinkError(AnalyticsListener.EventTime eventTime, Exception exc) {
        ba.l(this, eventTime, exc);
    }

    public void onAudioUnderrun(AnalyticsListener.EventTime eventTime, int i, long j, long j2) {
        StringBuilder sb = new StringBuilder(55);
        sb.append(i);
        sb.append(", ");
        sb.append(j);
        sb.append(", ");
        sb.append(j2);
        Log.e(this.b, a(eventTime, "audioTrackUnderrun", sb.toString(), (Throwable) null));
    }

    public void onBandwidthEstimate(AnalyticsListener.EventTime eventTime, int i, long j, long j2) {
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onDecoderDisabled(AnalyticsListener.EventTime eventTime, int i, DecoderCounters decoderCounters) {
        ba.o(this, eventTime, i, decoderCounters);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onDecoderEnabled(AnalyticsListener.EventTime eventTime, int i, DecoderCounters decoderCounters) {
        ba.p(this, eventTime, i, decoderCounters);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onDecoderInitialized(AnalyticsListener.EventTime eventTime, int i, String str, long j) {
        ba.q(this, eventTime, i, str, j);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onDecoderInputFormatChanged(AnalyticsListener.EventTime eventTime, int i, Format format) {
        ba.r(this, eventTime, i, format);
    }

    public void onDownstreamFormatChanged(AnalyticsListener.EventTime eventTime, MediaLoadData mediaLoadData) {
        d(a(eventTime, "downstreamFormat", Format.toLogString(mediaLoadData.c), (Throwable) null));
    }

    public void onDrmKeysLoaded(AnalyticsListener.EventTime eventTime) {
        d(a(eventTime, "drmKeysLoaded", (String) null, (Throwable) null));
    }

    public void onDrmKeysRemoved(AnalyticsListener.EventTime eventTime) {
        d(a(eventTime, "drmKeysRemoved", (String) null, (Throwable) null));
    }

    public void onDrmKeysRestored(AnalyticsListener.EventTime eventTime) {
        d(a(eventTime, "drmKeysRestored", (String) null, (Throwable) null));
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onDrmSessionAcquired(AnalyticsListener.EventTime eventTime) {
        ba.w(this, eventTime);
    }

    public void onDrmSessionAcquired(AnalyticsListener.EventTime eventTime, int i) {
        d(a(eventTime, "drmSessionAcquired", y2.d(17, "state=", i), (Throwable) null));
    }

    public void onDrmSessionManagerError(AnalyticsListener.EventTime eventTime, Exception exc) {
        Log.e(this.b, a(eventTime, "internalError", "drmSessionManagerError", exc));
    }

    public void onDrmSessionReleased(AnalyticsListener.EventTime eventTime) {
        d(a(eventTime, "drmSessionReleased", (String) null, (Throwable) null));
    }

    public void onDroppedVideoFrames(AnalyticsListener.EventTime eventTime, int i, long j) {
        d(a(eventTime, "droppedFrames", Integer.toString(i), (Throwable) null));
    }

    public /* bridge */ /* synthetic */ void onEvents(Player player, AnalyticsListener.Events events) {
        ba.ab(this, player, events);
    }

    public void onIsLoadingChanged(AnalyticsListener.EventTime eventTime, boolean z) {
        d(a(eventTime, "loading", Boolean.toString(z), (Throwable) null));
    }

    public void onIsPlayingChanged(AnalyticsListener.EventTime eventTime, boolean z) {
        d(a(eventTime, "isPlaying", Boolean.toString(z), (Throwable) null));
    }

    public void onLoadCanceled(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
    }

    public void onLoadCompleted(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
    }

    public void onLoadError(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z) {
        Log.e(this.b, a(eventTime, "internalError", "loadError", iOException));
    }

    public void onLoadStarted(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onLoadingChanged(AnalyticsListener.EventTime eventTime, boolean z) {
        ba.ai(this, eventTime, z);
    }

    public void onMediaItemTransition(AnalyticsListener.EventTime eventTime, @Nullable MediaItem mediaItem, int i) {
        String str;
        String b2 = b(eventTime);
        if (i == 0) {
            str = "REPEAT";
        } else if (i == 1) {
            str = "AUTO";
        } else if (i == 2) {
            str = "SEEK";
        } else if (i != 3) {
            str = "?";
        } else {
            str = "PLAYLIST_CHANGED";
        }
        StringBuilder l = y2.l(str.length() + y2.c(b2, 21), "mediaItem [", b2, ", reason=", str);
        l.append("]");
        d(l.toString());
    }

    public /* bridge */ /* synthetic */ void onMediaMetadataChanged(AnalyticsListener.EventTime eventTime, MediaMetadata mediaMetadata) {
        ba.ak(this, eventTime, mediaMetadata);
    }

    public void onMetadata(AnalyticsListener.EventTime eventTime, Metadata metadata) {
        String str;
        String valueOf = String.valueOf(b(eventTime));
        if (valueOf.length() != 0) {
            str = "metadata [".concat(valueOf);
        } else {
            str = new String("metadata [");
        }
        d(str);
        e(metadata, "  ");
        d("]");
    }

    public void onPlayWhenReadyChanged(AnalyticsListener.EventTime eventTime, boolean z, int i) {
        String str;
        if (i == 1) {
            str = "USER_REQUEST";
        } else if (i == 2) {
            str = "AUDIO_FOCUS_LOSS";
        } else if (i == 3) {
            str = "AUDIO_BECOMING_NOISY";
        } else if (i == 4) {
            str = "REMOTE";
        } else if (i != 5) {
            str = "?";
        } else {
            str = "END_OF_MEDIA_ITEM";
        }
        StringBuilder sb = new StringBuilder(str.length() + 7);
        sb.append(z);
        sb.append(", ");
        sb.append(str);
        d(a(eventTime, "playWhenReady", sb.toString(), (Throwable) null));
    }

    public void onPlaybackParametersChanged(AnalyticsListener.EventTime eventTime, PlaybackParameters playbackParameters) {
        d(a(eventTime, "playbackParameters", playbackParameters.toString(), (Throwable) null));
    }

    public void onPlaybackStateChanged(AnalyticsListener.EventTime eventTime, int i) {
        String str;
        if (i == 1) {
            str = "IDLE";
        } else if (i == 2) {
            str = "BUFFERING";
        } else if (i == 3) {
            str = "READY";
        } else if (i != 4) {
            str = "?";
        } else {
            str = "ENDED";
        }
        d(a(eventTime, "state", str, (Throwable) null));
    }

    public void onPlaybackSuppressionReasonChanged(AnalyticsListener.EventTime eventTime, int i) {
        String str;
        if (i == 0) {
            str = "NONE";
        } else if (i != 1) {
            str = "?";
        } else {
            str = "TRANSIENT_AUDIO_FOCUS_LOSS";
        }
        d(a(eventTime, "playbackSuppressionReason", str, (Throwable) null));
    }

    public void onPlayerError(AnalyticsListener.EventTime eventTime, ExoPlaybackException exoPlaybackException) {
        Log.e(this.b, a(eventTime, "playerFailed", (String) null, exoPlaybackException));
    }

    public /* bridge */ /* synthetic */ void onPlayerReleased(AnalyticsListener.EventTime eventTime) {
        ba.ar(this, eventTime);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onPlayerStateChanged(AnalyticsListener.EventTime eventTime, boolean z, int i) {
        ba.as(this, eventTime, z, i);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onPositionDiscontinuity(AnalyticsListener.EventTime eventTime, int i) {
        ba.at(this, eventTime, i);
    }

    public void onPositionDiscontinuity(AnalyticsListener.EventTime eventTime, Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i) {
        StringBuilder sb = new StringBuilder("reason=");
        sb.append(i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? "?" : "INTERNAL" : "REMOVE" : "SKIP" : "SEEK_ADJUSTMENT" : "SEEK" : "AUTO_TRANSITION");
        sb.append(", PositionInfo:old [window=");
        sb.append(positionInfo.f);
        sb.append(", period=");
        sb.append(positionInfo.h);
        sb.append(", pos=");
        sb.append(positionInfo.i);
        int i2 = positionInfo.k;
        if (i2 != -1) {
            sb.append(", contentPos=");
            sb.append(positionInfo.j);
            sb.append(", adGroup=");
            sb.append(i2);
            sb.append(", ad=");
            sb.append(positionInfo.l);
        }
        sb.append("], PositionInfo:new [window=");
        sb.append(positionInfo2.f);
        sb.append(", period=");
        sb.append(positionInfo2.h);
        sb.append(", pos=");
        sb.append(positionInfo2.i);
        int i3 = positionInfo2.k;
        if (i3 != -1) {
            sb.append(", contentPos=");
            sb.append(positionInfo2.j);
            sb.append(", adGroup=");
            sb.append(i3);
            sb.append(", ad=");
            sb.append(positionInfo2.l);
        }
        sb.append("]");
        d(a(eventTime, "positionDiscontinuity", sb.toString(), (Throwable) null));
    }

    public void onRenderedFirstFrame(AnalyticsListener.EventTime eventTime, Object obj, long j) {
        d(a(eventTime, "renderedFirstFrame", String.valueOf(obj), (Throwable) null));
    }

    public void onRepeatModeChanged(AnalyticsListener.EventTime eventTime, int i) {
        String str;
        if (i == 0) {
            str = "OFF";
        } else if (i == 1) {
            str = "ONE";
        } else if (i != 2) {
            str = "?";
        } else {
            str = "ALL";
        }
        d(a(eventTime, "repeatMode", str, (Throwable) null));
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onSeekProcessed(AnalyticsListener.EventTime eventTime) {
        ba.ax(this, eventTime);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onSeekStarted(AnalyticsListener.EventTime eventTime) {
        ba.ay(this, eventTime);
    }

    public void onShuffleModeChanged(AnalyticsListener.EventTime eventTime, boolean z) {
        d(a(eventTime, "shuffleModeEnabled", Boolean.toString(z), (Throwable) null));
    }

    public void onSkipSilenceEnabledChanged(AnalyticsListener.EventTime eventTime, boolean z) {
        d(a(eventTime, "skipSilenceEnabled", Boolean.toString(z), (Throwable) null));
    }

    public void onStaticMetadataChanged(AnalyticsListener.EventTime eventTime, List<Metadata> list) {
        String str;
        String valueOf = String.valueOf(b(eventTime));
        if (valueOf.length() != 0) {
            str = "staticMetadata [".concat(valueOf);
        } else {
            str = new String("staticMetadata [");
        }
        d(str);
        for (int i = 0; i < list.size(); i++) {
            Metadata metadata = list.get(i);
            if (metadata.length() != 0) {
                StringBuilder sb = new StringBuilder(24);
                sb.append("  Metadata:");
                sb.append(i);
                sb.append(" [");
                d(sb.toString());
                e(metadata, "    ");
                d("  ]");
            }
        }
        d("]");
    }

    public void onSurfaceSizeChanged(AnalyticsListener.EventTime eventTime, int i, int i2) {
        StringBuilder sb = new StringBuilder(24);
        sb.append(i);
        sb.append(", ");
        sb.append(i2);
        d(a(eventTime, "surfaceSize", sb.toString(), (Throwable) null));
    }

    public void onTimelineChanged(AnalyticsListener.EventTime eventTime, int i) {
        String str;
        int periodCount = eventTime.b.getPeriodCount();
        Timeline timeline = eventTime.b;
        int windowCount = timeline.getWindowCount();
        String b2 = b(eventTime);
        if (i == 0) {
            str = "PLAYLIST_CHANGED";
        } else if (i != 1) {
            str = "?";
        } else {
            str = "SOURCE_UPDATE";
        }
        StringBuilder sb = new StringBuilder(str.length() + y2.c(b2, 69));
        sb.append("timeline [");
        sb.append(b2);
        sb.append(", periodCount=");
        sb.append(periodCount);
        sb.append(", windowCount=");
        sb.append(windowCount);
        sb.append(", reason=");
        sb.append(str);
        d(sb.toString());
        for (int i2 = 0; i2 < Math.min(periodCount, 3); i2++) {
            Timeline.Period period = this.d;
            timeline.getPeriod(i2, period);
            String c2 = c(period.getDurationMs());
            StringBuilder sb2 = new StringBuilder(y2.c(c2, 11));
            sb2.append("  period [");
            sb2.append(c2);
            sb2.append("]");
            d(sb2.toString());
        }
        if (periodCount > 3) {
            d("  ...");
        }
        for (int i3 = 0; i3 < Math.min(windowCount, 3); i3++) {
            Timeline.Window window = this.c;
            timeline.getWindow(i3, window);
            String c3 = c(window.getDurationMs());
            boolean z = window.l;
            boolean z2 = window.m;
            StringBuilder sb3 = new StringBuilder(y2.c(c3, 42));
            sb3.append("  window [");
            sb3.append(c3);
            sb3.append(", seekable=");
            sb3.append(z);
            sb3.append(", dynamic=");
            sb3.append(z2);
            sb3.append("]");
            d(sb3.toString());
        }
        if (windowCount > 3) {
            d("  ...");
        }
        d("]");
    }

    public void onTracksChanged(AnalyticsListener.EventTime eventTime, TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
        MappingTrackSelector.MappedTrackInfo mappedTrackInfo;
        String str;
        String str2;
        boolean z;
        String str3;
        MappingTrackSelector mappingTrackSelector = this.a;
        if (mappingTrackSelector != null) {
            mappedTrackInfo = mappingTrackSelector.getCurrentMappedTrackInfo();
        } else {
            mappedTrackInfo = null;
        }
        if (mappedTrackInfo == null) {
            d(a(eventTime, "tracks", "[]", (Throwable) null));
            return;
        }
        AnalyticsListener.EventTime eventTime2 = eventTime;
        String valueOf = String.valueOf(b(eventTime));
        if (valueOf.length() != 0) {
            str = "tracks [".concat(valueOf);
        } else {
            str = new String("tracks [");
        }
        d(str);
        int rendererCount = mappedTrackInfo.getRendererCount();
        int i = 0;
        while (true) {
            String str4 = "    Group:";
            String str5 = " [";
            if (i < rendererCount) {
                TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(i);
                TrackSelection trackSelection = trackSelectionArray.get(i);
                int i2 = rendererCount;
                if (trackGroups.c == 0) {
                    String rendererName = mappedTrackInfo.getRendererName(i);
                    StringBuilder sb = new StringBuilder(y2.c(rendererName, 5));
                    sb.append("  ");
                    sb.append(rendererName);
                    sb.append(" []");
                    d(sb.toString());
                } else {
                    String rendererName2 = mappedTrackInfo.getRendererName(i);
                    String str6 = "[ ]";
                    String str7 = "  ]";
                    StringBuilder sb2 = new StringBuilder(y2.c(rendererName2, 4));
                    sb2.append("  ");
                    sb2.append(rendererName2);
                    sb2.append(str5);
                    d(sb2.toString());
                    int i3 = 0;
                    while (i3 < trackGroups.c) {
                        TrackGroup trackGroup = trackGroups.get(i3);
                        int i4 = trackGroup.c;
                        int adaptiveSupport = mappedTrackInfo.getAdaptiveSupport(i, i3, false);
                        TrackGroupArray trackGroupArray2 = trackGroups;
                        if (i4 < 2) {
                            str2 = "N/A";
                        } else if (adaptiveSupport == 0) {
                            str2 = "NO";
                        } else if (adaptiveSupport == 8) {
                            str2 = "YES_NOT_SEAMLESS";
                        } else if (adaptiveSupport == 16) {
                            str2 = "YES";
                        } else {
                            throw new IllegalStateException();
                        }
                        StringBuilder sb3 = new StringBuilder(str2.length() + 44);
                        sb3.append(str4);
                        sb3.append(i3);
                        sb3.append(", adaptive_supported=");
                        sb3.append(str2);
                        sb3.append(str5);
                        d(sb3.toString());
                        int i5 = 0;
                        while (i5 < trackGroup.c) {
                            if (trackSelection == null || trackSelection.getTrackGroup() != trackGroup || trackSelection.indexOf(i5) == -1) {
                                z = false;
                            } else {
                                z = true;
                            }
                            if (z) {
                                str3 = "[X]";
                            } else {
                                str3 = str6;
                            }
                            String formatSupportString = C.getFormatSupportString(mappedTrackInfo.getTrackSupport(i, i3, i5));
                            TrackGroup trackGroup2 = trackGroup;
                            String logString = Format.toLogString(trackGroup.getFormat(i5));
                            String str8 = str4;
                            StringBuilder sb4 = new StringBuilder(y2.c(formatSupportString, y2.c(logString, str3.length() + 38)));
                            sb4.append("      ");
                            sb4.append(str3);
                            sb4.append(" Track:");
                            sb4.append(i5);
                            sb4.append(", ");
                            sb4.append(logString);
                            sb4.append(", supported=");
                            sb4.append(formatSupportString);
                            d(sb4.toString());
                            i5++;
                            str4 = str8;
                            trackGroup = trackGroup2;
                            str5 = str5;
                        }
                        String str9 = str4;
                        String str10 = str5;
                        d("    ]");
                        i3++;
                        trackGroups = trackGroupArray2;
                    }
                    if (trackSelection != null) {
                        int i6 = 0;
                        while (true) {
                            if (i6 >= trackSelection.length()) {
                                break;
                            }
                            Metadata metadata = trackSelection.getFormat(i6).n;
                            if (metadata != null) {
                                d("    Metadata [");
                                e(metadata, "      ");
                                d("    ]");
                                break;
                            }
                            i6++;
                        }
                    }
                    d(str7);
                }
                i++;
                rendererCount = i2;
            } else {
                String str11 = "[ ]";
                String str12 = str4;
                String str13 = "  ]";
                String str14 = str5;
                TrackGroupArray unmappedTrackGroups = mappedTrackInfo.getUnmappedTrackGroups();
                if (unmappedTrackGroups.c > 0) {
                    d("  Unmapped [");
                    int i7 = 0;
                    while (i7 < unmappedTrackGroups.c) {
                        StringBuilder sb5 = new StringBuilder(23);
                        String str15 = str12;
                        sb5.append(str15);
                        sb5.append(i7);
                        String str16 = str14;
                        sb5.append(str16);
                        d(sb5.toString());
                        TrackGroup trackGroup3 = unmappedTrackGroups.get(i7);
                        int i8 = 0;
                        while (i8 < trackGroup3.c) {
                            String formatSupportString2 = C.getFormatSupportString(0);
                            String logString2 = Format.toLogString(trackGroup3.getFormat(i8));
                            TrackGroupArray trackGroupArray3 = unmappedTrackGroups;
                            StringBuilder sb6 = new StringBuilder(y2.c(formatSupportString2, y2.c(logString2, str11.length() + 38)));
                            sb6.append("      ");
                            sb6.append(str11);
                            sb6.append(" Track:");
                            sb6.append(i8);
                            sb6.append(", ");
                            sb6.append(logString2);
                            sb6.append(", supported=");
                            sb6.append(formatSupportString2);
                            d(sb6.toString());
                            i8++;
                            unmappedTrackGroups = trackGroupArray3;
                        }
                        String str17 = str11;
                        TrackGroupArray trackGroupArray4 = unmappedTrackGroups;
                        d("    ]");
                        i7++;
                        str12 = str15;
                        str14 = str16;
                    }
                    d(str13);
                }
                d("]");
                return;
            }
        }
    }

    public void onUpstreamDiscarded(AnalyticsListener.EventTime eventTime, MediaLoadData mediaLoadData) {
        d(a(eventTime, "upstreamDiscarded", Format.toLogString(mediaLoadData.c), (Throwable) null));
    }

    public /* bridge */ /* synthetic */ void onVideoCodecError(AnalyticsListener.EventTime eventTime, Exception exc) {
        ba.bg(this, eventTime, exc);
    }

    public void onVideoDecoderInitialized(AnalyticsListener.EventTime eventTime, String str, long j) {
        d(a(eventTime, "videoDecoderInitialized", str, (Throwable) null));
    }

    public /* bridge */ /* synthetic */ void onVideoDecoderInitialized(AnalyticsListener.EventTime eventTime, String str, long j, long j2) {
        ba.bi(this, eventTime, str, j, j2);
    }

    public void onVideoDecoderReleased(AnalyticsListener.EventTime eventTime, String str) {
        d(a(eventTime, "videoDecoderReleased", str, (Throwable) null));
    }

    public void onVideoDisabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
        d(a(eventTime, "videoDisabled", (String) null, (Throwable) null));
    }

    public void onVideoEnabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
        d(a(eventTime, "videoEnabled", (String) null, (Throwable) null));
    }

    public /* bridge */ /* synthetic */ void onVideoFrameProcessingOffset(AnalyticsListener.EventTime eventTime, long j, int i) {
        ba.bm(this, eventTime, j, i);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onVideoInputFormatChanged(AnalyticsListener.EventTime eventTime, Format format) {
        ba.bn(this, eventTime, format);
    }

    public void onVideoInputFormatChanged(AnalyticsListener.EventTime eventTime, Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation) {
        d(a(eventTime, "videoInputFormat", Format.toLogString(format), (Throwable) null));
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onVideoSizeChanged(AnalyticsListener.EventTime eventTime, int i, int i2, int i3, float f2) {
        ba.bp(this, eventTime, i, i2, i3, f2);
    }

    public void onVideoSizeChanged(AnalyticsListener.EventTime eventTime, VideoSize videoSize) {
        int i = videoSize.c;
        StringBuilder sb = new StringBuilder(24);
        sb.append(i);
        sb.append(", ");
        sb.append(videoSize.f);
        d(a(eventTime, "videoSize", sb.toString(), (Throwable) null));
    }

    public void onVolumeChanged(AnalyticsListener.EventTime eventTime, float f2) {
        d(a(eventTime, "volume", Float.toString(f2), (Throwable) null));
    }

    public EventLogger(@Nullable MappingTrackSelector mappingTrackSelector, String str) {
        this.a = mappingTrackSelector;
        this.b = str;
        this.c = new Timeline.Window();
        this.d = new Timeline.Period();
        this.e = SystemClock.elapsedRealtime();
    }
}
