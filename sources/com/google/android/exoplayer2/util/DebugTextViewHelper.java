package com.google.android.exoplayer2.util;

import android.annotation.SuppressLint;
import android.os.Looper;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.os.EnvironmentCompat;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.device.DeviceInfo;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.video.VideoSize;
import java.util.List;
import java.util.Locale;

public class DebugTextViewHelper implements Player.Listener, Runnable {
    public final SimpleExoPlayer c;
    public final TextView f;
    public boolean g;

    public DebugTextViewHelper(SimpleExoPlayer simpleExoPlayer, TextView textView) {
        boolean z;
        if (simpleExoPlayer.getApplicationLooper() == Looper.getMainLooper()) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkArgument(z);
        this.c = simpleExoPlayer;
        this.f = textView;
    }

    public static String a(DecoderCounters decoderCounters) {
        decoderCounters.ensureUpdated();
        int i = decoderCounters.a;
        int i2 = decoderCounters.c;
        int i3 = decoderCounters.b;
        int i4 = decoderCounters.d;
        int i5 = decoderCounters.e;
        int i6 = decoderCounters.f;
        StringBuilder sb = new StringBuilder(93);
        sb.append(" sib:");
        sb.append(i);
        sb.append(" sb:");
        sb.append(i2);
        sb.append(" rb:");
        sb.append(i3);
        sb.append(" db:");
        sb.append(i4);
        sb.append(" mcdb:");
        sb.append(i5);
        sb.append(" dk:");
        sb.append(i6);
        return sb.toString();
    }

    @SuppressLint({"SetTextI18n"})
    public final void b() {
        String str;
        String str2;
        String str3;
        String str4;
        SimpleExoPlayer simpleExoPlayer = this.c;
        int playbackState = simpleExoPlayer.getPlaybackState();
        if (playbackState == 1) {
            str = "idle";
        } else if (playbackState == 2) {
            str = "buffering";
        } else if (playbackState == 3) {
            str = "ready";
        } else if (playbackState != 4) {
            str = EnvironmentCompat.MEDIA_UNKNOWN;
        } else {
            str = "ended";
        }
        String format = String.format("playWhenReady:%s playbackState:%s window:%s", new Object[]{Boolean.valueOf(simpleExoPlayer.getPlayWhenReady()), str, Integer.valueOf(simpleExoPlayer.getCurrentWindowIndex())});
        Format videoFormat = simpleExoPlayer.getVideoFormat();
        DecoderCounters videoDecoderCounters = simpleExoPlayer.getVideoDecoderCounters();
        String str5 = "";
        if (videoFormat == null || videoDecoderCounters == null) {
            str2 = str5;
        } else {
            float f2 = videoFormat.y;
            if (f2 == -1.0f || f2 == 1.0f) {
                str3 = str5;
            } else {
                String valueOf = String.valueOf(String.format(Locale.US, "%.02f", new Object[]{Float.valueOf(f2)}));
                if (valueOf.length() != 0) {
                    str3 = " par:".concat(valueOf);
                } else {
                    str3 = new String(" par:");
                }
            }
            String a = a(videoDecoderCounters);
            long j = videoDecoderCounters.g;
            int i = videoDecoderCounters.h;
            if (i == 0) {
                str4 = "N/A";
            } else {
                str4 = String.valueOf((long) (((double) j) / ((double) i)));
            }
            String str6 = videoFormat.p;
            int c2 = y2.c(str6, 39);
            String str7 = videoFormat.c;
            StringBuilder l = y2.l(y2.c(str4, y2.c(a, y2.c(str3, y2.c(str7, c2)))), "\n", str6, "(id:", str7);
            l.append(" r:");
            l.append(videoFormat.u);
            l.append("x");
            l.append(videoFormat.v);
            l.append(str3);
            l.append(a);
            l.append(" vfpo: ");
            str2 = y2.k(l, str4, ")");
        }
        Format audioFormat = simpleExoPlayer.getAudioFormat();
        DecoderCounters audioDecoderCounters = simpleExoPlayer.getAudioDecoderCounters();
        if (!(audioFormat == null || audioDecoderCounters == null)) {
            String a2 = a(audioDecoderCounters);
            String str8 = audioFormat.p;
            int c3 = y2.c(str8, 36);
            String str9 = audioFormat.c;
            StringBuilder l2 = y2.l(y2.c(a2, y2.c(str9, c3)), "\n", str8, "(id:", str9);
            l2.append(" hz:");
            l2.append(audioFormat.ad);
            l2.append(" ch:");
            l2.append(audioFormat.ac);
            l2.append(a2);
            l2.append(")");
            str5 = l2.toString();
        }
        StringBuilder sb = new StringBuilder(y2.c(str5, y2.c(str2, String.valueOf(format).length())));
        sb.append(format);
        sb.append(str2);
        sb.append(str5);
        String sb2 = sb.toString();
        TextView textView = this.f;
        textView.setText(sb2);
        textView.removeCallbacks(this);
        textView.postDelayed(this, 1000);
    }

    public /* bridge */ /* synthetic */ void onAudioAttributesChanged(AudioAttributes audioAttributes) {
        u8.a(this, audioAttributes);
    }

    public /* bridge */ /* synthetic */ void onAudioSessionIdChanged(int i) {
        u8.b(this, i);
    }

    public /* bridge */ /* synthetic */ void onAvailableCommandsChanged(Player.Commands commands) {
        u8.c(this, commands);
    }

    public /* bridge */ /* synthetic */ void onCues(List list) {
        u8.d(this, list);
    }

    public /* bridge */ /* synthetic */ void onDeviceInfoChanged(DeviceInfo deviceInfo) {
        u8.e(this, deviceInfo);
    }

    public /* bridge */ /* synthetic */ void onDeviceVolumeChanged(int i, boolean z) {
        u8.f(this, i, z);
    }

    public /* bridge */ /* synthetic */ void onEvents(Player player, Player.Events events) {
        u8.g(this, player, events);
    }

    public /* bridge */ /* synthetic */ void onIsLoadingChanged(boolean z) {
        u8.h(this, z);
    }

    public /* bridge */ /* synthetic */ void onIsPlayingChanged(boolean z) {
        u8.i(this, z);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onLoadingChanged(boolean z) {
        t8.e(this, z);
    }

    public /* bridge */ /* synthetic */ void onMediaItemTransition(@Nullable MediaItem mediaItem, int i) {
        u8.k(this, mediaItem, i);
    }

    public /* bridge */ /* synthetic */ void onMediaMetadataChanged(MediaMetadata mediaMetadata) {
        u8.l(this, mediaMetadata);
    }

    public /* bridge */ /* synthetic */ void onMetadata(Metadata metadata) {
        u8.m(this, metadata);
    }

    public final void onPlayWhenReadyChanged(boolean z, int i) {
        b();
    }

    public /* bridge */ /* synthetic */ void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        u8.o(this, playbackParameters);
    }

    public final void onPlaybackStateChanged(int i) {
        b();
    }

    public /* bridge */ /* synthetic */ void onPlaybackSuppressionReasonChanged(int i) {
        u8.q(this, i);
    }

    public /* bridge */ /* synthetic */ void onPlayerError(ExoPlaybackException exoPlaybackException) {
        u8.r(this, exoPlaybackException);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onPlayerStateChanged(boolean z, int i) {
        t8.m(this, z, i);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onPositionDiscontinuity(int i) {
        t8.n(this, i);
    }

    public final void onPositionDiscontinuity(Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i) {
        b();
    }

    public /* bridge */ /* synthetic */ void onRenderedFirstFrame() {
        u8.v(this);
    }

    public /* bridge */ /* synthetic */ void onRepeatModeChanged(int i) {
        u8.w(this, i);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onSeekProcessed() {
        t8.q(this);
    }

    public /* bridge */ /* synthetic */ void onShuffleModeEnabledChanged(boolean z) {
        u8.y(this, z);
    }

    public /* bridge */ /* synthetic */ void onSkipSilenceEnabledChanged(boolean z) {
        u8.z(this, z);
    }

    public /* bridge */ /* synthetic */ void onStaticMetadataChanged(List list) {
        u8.aa(this, list);
    }

    public /* bridge */ /* synthetic */ void onSurfaceSizeChanged(int i, int i2) {
        u8.ab(this, i, i2);
    }

    public /* bridge */ /* synthetic */ void onTimelineChanged(Timeline timeline, int i) {
        u8.ac(this, timeline, i);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onTimelineChanged(Timeline timeline, @Nullable Object obj, int i) {
        t8.u(this, timeline, obj, i);
    }

    public /* bridge */ /* synthetic */ void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
        u8.ae(this, trackGroupArray, trackSelectionArray);
    }

    @Deprecated
    public /* bridge */ /* synthetic */ void onVideoSizeChanged(int i, int i2, int i3, float f2) {
        od.c(this, i, i2, i3, f2);
    }

    public /* bridge */ /* synthetic */ void onVideoSizeChanged(VideoSize videoSize) {
        u8.ag(this, videoSize);
    }

    public /* bridge */ /* synthetic */ void onVolumeChanged(float f2) {
        u8.ah(this, f2);
    }

    public final void run() {
        b();
    }

    public final void start() {
        if (!this.g) {
            this.g = true;
            this.c.addListener((Player.Listener) this);
            b();
        }
    }

    public final void stop() {
        if (this.g) {
            this.g = false;
            this.c.removeListener((Player.Listener) this);
            this.f.removeCallbacks(this);
        }
    }
}
