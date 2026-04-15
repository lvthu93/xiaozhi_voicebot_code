package com.google.android.exoplayer2;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.media.AudioTrack;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.Looper;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.AudioBecomingNoisyManager;
import com.google.android.exoplayer2.AudioFocusManager;
import com.google.android.exoplayer2.DefaultLivePlaybackSpeedControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.PlayerMessage;
import com.google.android.exoplayer2.StreamVolumeManager;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.audio.AudioListener;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.audio.AuxEffectInfo;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.device.DeviceInfo;
import com.google.android.exoplayer2.device.DeviceListener;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.ConditionVariable;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.PriorityTaskManager;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoDecoderOutputBufferRenderer;
import com.google.android.exoplayer2.video.VideoFrameMetadataListener;
import com.google.android.exoplayer2.video.VideoListener;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.android.exoplayer2.video.spherical.CameraMotionListener;
import com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeoutException;

public class SimpleExoPlayer extends BasePlayer implements ExoPlayer, ExoPlayer.AudioComponent, ExoPlayer.VideoComponent, ExoPlayer.TextComponent, ExoPlayer.MetadataComponent, ExoPlayer.DeviceComponent {
    public boolean aa;
    @Nullable
    public TextureView ab;
    public int ac;
    public int ad;
    public int ae;
    @Nullable
    public DecoderCounters af;
    @Nullable
    public DecoderCounters ag;
    public int ah;
    public AudioAttributes ai;
    public float aj;
    public boolean ak;
    public List<Cue> al;
    @Nullable
    public VideoFrameMetadataListener am;
    @Nullable
    public CameraMotionListener an;
    public boolean ao;
    public boolean ap;
    @Nullable
    public PriorityTaskManager aq;
    public boolean ar;
    public boolean as;
    public DeviceInfo at;
    public VideoSize au;
    public final Renderer[] b;
    public final ConditionVariable c;
    public final Context d;
    public final a e;
    public final a f;
    public final b g;
    public final CopyOnWriteArraySet<VideoListener> h;
    public final CopyOnWriteArraySet<AudioListener> i;
    public final CopyOnWriteArraySet<TextOutput> j;
    public final CopyOnWriteArraySet<MetadataOutput> k;
    public final CopyOnWriteArraySet<DeviceListener> l;
    public final AnalyticsCollector m;
    public final AudioBecomingNoisyManager n;
    public final AudioFocusManager o;
    public final StreamVolumeManager p;
    public final ee q;
    public final ff r;
    public final long s;
    @Nullable
    public Format t;
    @Nullable
    public Format u;
    @Nullable
    public AudioTrack v;
    @Nullable
    public Object w;
    @Nullable
    public Surface x;
    @Nullable
    public SurfaceHolder y;
    @Nullable
    public SphericalGLSurfaceView z;

    public static final class Builder {
        public final Context a;
        public final RenderersFactory b;
        public Clock c;
        public long d;
        public TrackSelector e;
        public MediaSourceFactory f;
        public LoadControl g;
        public BandwidthMeter h;
        public AnalyticsCollector i;
        public Looper j;
        @Nullable
        public PriorityTaskManager k;
        public AudioAttributes l;
        public boolean m;
        public int n;
        public boolean o;
        public boolean p;
        public int q;
        public boolean r;
        public SeekParameters s;
        public LivePlaybackSpeedControl t;
        public long u;
        public long v;
        public boolean w;
        public boolean x;

        public Builder(Context context) {
            this(context, new DefaultRenderersFactory(context), new DefaultExtractorsFactory());
        }

        public SimpleExoPlayer build() {
            Assertions.checkState(!this.x);
            this.x = true;
            return new SimpleExoPlayer(this);
        }

        public Builder experimentalSetForegroundModeTimeoutMs(long j2) {
            Assertions.checkState(!this.x);
            this.d = j2;
            return this;
        }

        public Builder setAnalyticsCollector(AnalyticsCollector analyticsCollector) {
            Assertions.checkState(!this.x);
            this.i = analyticsCollector;
            return this;
        }

        public Builder setAudioAttributes(AudioAttributes audioAttributes, boolean z) {
            Assertions.checkState(!this.x);
            this.l = audioAttributes;
            this.m = z;
            return this;
        }

        public Builder setBandwidthMeter(BandwidthMeter bandwidthMeter) {
            Assertions.checkState(!this.x);
            this.h = bandwidthMeter;
            return this;
        }

        @VisibleForTesting
        public Builder setClock(Clock clock) {
            Assertions.checkState(!this.x);
            this.c = clock;
            return this;
        }

        public Builder setDetachSurfaceTimeoutMs(long j2) {
            Assertions.checkState(!this.x);
            this.v = j2;
            return this;
        }

        public Builder setHandleAudioBecomingNoisy(boolean z) {
            Assertions.checkState(!this.x);
            this.o = z;
            return this;
        }

        public Builder setLivePlaybackSpeedControl(LivePlaybackSpeedControl livePlaybackSpeedControl) {
            Assertions.checkState(!this.x);
            this.t = livePlaybackSpeedControl;
            return this;
        }

        public Builder setLoadControl(LoadControl loadControl) {
            Assertions.checkState(!this.x);
            this.g = loadControl;
            return this;
        }

        public Builder setLooper(Looper looper) {
            Assertions.checkState(!this.x);
            this.j = looper;
            return this;
        }

        public Builder setMediaSourceFactory(MediaSourceFactory mediaSourceFactory) {
            Assertions.checkState(!this.x);
            this.f = mediaSourceFactory;
            return this;
        }

        public Builder setPauseAtEndOfMediaItems(boolean z) {
            Assertions.checkState(!this.x);
            this.w = z;
            return this;
        }

        public Builder setPriorityTaskManager(@Nullable PriorityTaskManager priorityTaskManager) {
            Assertions.checkState(!this.x);
            this.k = priorityTaskManager;
            return this;
        }

        public Builder setReleaseTimeoutMs(long j2) {
            Assertions.checkState(!this.x);
            this.u = j2;
            return this;
        }

        public Builder setSeekParameters(SeekParameters seekParameters) {
            Assertions.checkState(!this.x);
            this.s = seekParameters;
            return this;
        }

        public Builder setSkipSilenceEnabled(boolean z) {
            Assertions.checkState(!this.x);
            this.p = z;
            return this;
        }

        public Builder setTrackSelector(TrackSelector trackSelector) {
            Assertions.checkState(!this.x);
            this.e = trackSelector;
            return this;
        }

        public Builder setUseLazyPreparation(boolean z) {
            Assertions.checkState(!this.x);
            this.r = z;
            return this;
        }

        public Builder setVideoScalingMode(int i2) {
            Assertions.checkState(!this.x);
            this.q = i2;
            return this;
        }

        public Builder setWakeMode(int i2) {
            Assertions.checkState(!this.x);
            this.n = i2;
            return this;
        }

        public Builder(Context context, RenderersFactory renderersFactory) {
            this(context, renderersFactory, new DefaultExtractorsFactory());
        }

        public Builder(Context context, ExtractorsFactory extractorsFactory) {
            this(context, new DefaultRenderersFactory(context), extractorsFactory);
        }

        public Builder(Context context, RenderersFactory renderersFactory, ExtractorsFactory extractorsFactory) {
            this(context, renderersFactory, new DefaultTrackSelector(context), new DefaultMediaSourceFactory(context, extractorsFactory), new DefaultLoadControl(), DefaultBandwidthMeter.getSingletonInstance(context), new AnalyticsCollector(Clock.a));
        }

        public Builder(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector, MediaSourceFactory mediaSourceFactory, LoadControl loadControl, BandwidthMeter bandwidthMeter, AnalyticsCollector analyticsCollector) {
            this.a = context;
            this.b = renderersFactory;
            this.e = trackSelector;
            this.f = mediaSourceFactory;
            this.g = loadControl;
            this.h = bandwidthMeter;
            this.i = analyticsCollector;
            this.j = Util.getCurrentOrMainLooper();
            this.l = AudioAttributes.j;
            this.n = 0;
            this.q = 1;
            this.r = true;
            this.s = SeekParameters.c;
            this.t = new DefaultLivePlaybackSpeedControl.Builder().build();
            this.c = Clock.a;
            this.u = 500;
            this.v = 2000;
        }
    }

    public final class a implements VideoRendererEventListener, AudioRendererEventListener, TextOutput, MetadataOutput, SurfaceHolder.Callback, TextureView.SurfaceTextureListener, SphericalGLSurfaceView.VideoSurfaceListener, AudioFocusManager.PlayerControl, AudioBecomingNoisyManager.EventListener, StreamVolumeManager.Listener, Player.EventListener, ExoPlayer.AudioOffloadListener {
        public a() {
        }

        public void executePlayerCommand(int i) {
            SimpleExoPlayer simpleExoPlayer = SimpleExoPlayer.this;
            boolean playWhenReady = simpleExoPlayer.getPlayWhenReady();
            int i2 = 1;
            if (playWhenReady && i != 1) {
                i2 = 2;
            }
            simpleExoPlayer.i(playWhenReady, i, i2);
        }

        public void onAudioBecomingNoisy() {
            SimpleExoPlayer.this.i(false, -1, 3);
        }

        public void onAudioCodecError(Exception exc) {
            SimpleExoPlayer.this.m.onAudioCodecError(exc);
        }

        public void onAudioDecoderInitialized(String str, long j, long j2) {
            SimpleExoPlayer.this.m.onAudioDecoderInitialized(str, j, j2);
        }

        public void onAudioDecoderReleased(String str) {
            SimpleExoPlayer.this.m.onAudioDecoderReleased(str);
        }

        public void onAudioDisabled(DecoderCounters decoderCounters) {
            SimpleExoPlayer simpleExoPlayer = SimpleExoPlayer.this;
            simpleExoPlayer.m.onAudioDisabled(decoderCounters);
            simpleExoPlayer.u = null;
            simpleExoPlayer.ag = null;
        }

        public void onAudioEnabled(DecoderCounters decoderCounters) {
            SimpleExoPlayer simpleExoPlayer = SimpleExoPlayer.this;
            simpleExoPlayer.ag = decoderCounters;
            simpleExoPlayer.m.onAudioEnabled(decoderCounters);
        }

        @Deprecated
        public /* bridge */ /* synthetic */ void onAudioInputFormatChanged(Format format) {
            bp.f(this, format);
        }

        public void onAudioInputFormatChanged(Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation) {
            SimpleExoPlayer simpleExoPlayer = SimpleExoPlayer.this;
            simpleExoPlayer.u = format;
            simpleExoPlayer.m.onAudioInputFormatChanged(format, decoderReuseEvaluation);
        }

        public void onAudioPositionAdvancing(long j) {
            SimpleExoPlayer.this.m.onAudioPositionAdvancing(j);
        }

        public void onAudioSinkError(Exception exc) {
            SimpleExoPlayer.this.m.onAudioSinkError(exc);
        }

        public void onAudioUnderrun(int i, long j, long j2) {
            SimpleExoPlayer.this.m.onAudioUnderrun(i, j, j2);
        }

        public /* bridge */ /* synthetic */ void onAvailableCommandsChanged(Player.Commands commands) {
            t8.a(this, commands);
        }

        public void onCues(List<Cue> list) {
            SimpleExoPlayer simpleExoPlayer = SimpleExoPlayer.this;
            simpleExoPlayer.al = list;
            Iterator<TextOutput> it = simpleExoPlayer.j.iterator();
            while (it.hasNext()) {
                it.next().onCues(list);
            }
        }

        public void onDroppedFrames(int i, long j) {
            SimpleExoPlayer.this.m.onDroppedFrames(i, j);
        }

        public /* bridge */ /* synthetic */ void onEvents(Player player, Player.Events events) {
            t8.b(this, player, events);
        }

        public /* bridge */ /* synthetic */ void onExperimentalOffloadSchedulingEnabledChanged(boolean z) {
            g2.a(this, z);
        }

        public void onExperimentalSleepingForOffloadChanged(boolean z) {
            SimpleExoPlayer.a(SimpleExoPlayer.this);
        }

        public void onIsLoadingChanged(boolean z) {
            SimpleExoPlayer simpleExoPlayer = SimpleExoPlayer.this;
            PriorityTaskManager priorityTaskManager = simpleExoPlayer.aq;
            if (priorityTaskManager == null) {
                return;
            }
            if (z && !simpleExoPlayer.ar) {
                priorityTaskManager.add(0);
                simpleExoPlayer.ar = true;
            } else if (!z && simpleExoPlayer.ar) {
                priorityTaskManager.remove(0);
                simpleExoPlayer.ar = false;
            }
        }

        public /* bridge */ /* synthetic */ void onIsPlayingChanged(boolean z) {
            t8.d(this, z);
        }

        @Deprecated
        public /* bridge */ /* synthetic */ void onLoadingChanged(boolean z) {
            t8.e(this, z);
        }

        public /* bridge */ /* synthetic */ void onMediaItemTransition(@Nullable MediaItem mediaItem, int i) {
            t8.f(this, mediaItem, i);
        }

        public /* bridge */ /* synthetic */ void onMediaMetadataChanged(MediaMetadata mediaMetadata) {
            t8.g(this, mediaMetadata);
        }

        public void onMetadata(Metadata metadata) {
            SimpleExoPlayer simpleExoPlayer = SimpleExoPlayer.this;
            simpleExoPlayer.m.onMetadata(metadata);
            simpleExoPlayer.e.onMetadata(metadata);
            Iterator<MetadataOutput> it = simpleExoPlayer.k.iterator();
            while (it.hasNext()) {
                it.next().onMetadata(metadata);
            }
        }

        public void onPlayWhenReadyChanged(boolean z, int i) {
            SimpleExoPlayer.a(SimpleExoPlayer.this);
        }

        public /* bridge */ /* synthetic */ void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            t8.i(this, playbackParameters);
        }

        public void onPlaybackStateChanged(int i) {
            SimpleExoPlayer.a(SimpleExoPlayer.this);
        }

        public /* bridge */ /* synthetic */ void onPlaybackSuppressionReasonChanged(int i) {
            t8.k(this, i);
        }

        public /* bridge */ /* synthetic */ void onPlayerError(ExoPlaybackException exoPlaybackException) {
            t8.l(this, exoPlaybackException);
        }

        @Deprecated
        public /* bridge */ /* synthetic */ void onPlayerStateChanged(boolean z, int i) {
            t8.m(this, z, i);
        }

        @Deprecated
        public /* bridge */ /* synthetic */ void onPositionDiscontinuity(int i) {
            t8.n(this, i);
        }

        public /* bridge */ /* synthetic */ void onPositionDiscontinuity(Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i) {
            t8.o(this, positionInfo, positionInfo2, i);
        }

        public void onRenderedFirstFrame(Object obj, long j) {
            SimpleExoPlayer simpleExoPlayer = SimpleExoPlayer.this;
            simpleExoPlayer.m.onRenderedFirstFrame(obj, j);
            if (simpleExoPlayer.w == obj) {
                Iterator<VideoListener> it = simpleExoPlayer.h.iterator();
                while (it.hasNext()) {
                    it.next().onRenderedFirstFrame();
                }
            }
        }

        public /* bridge */ /* synthetic */ void onRepeatModeChanged(int i) {
            t8.p(this, i);
        }

        @Deprecated
        public /* bridge */ /* synthetic */ void onSeekProcessed() {
            t8.q(this);
        }

        public /* bridge */ /* synthetic */ void onShuffleModeEnabledChanged(boolean z) {
            t8.r(this, z);
        }

        public void onSkipSilenceEnabledChanged(boolean z) {
            SimpleExoPlayer simpleExoPlayer = SimpleExoPlayer.this;
            if (simpleExoPlayer.ak != z) {
                simpleExoPlayer.ak = z;
                simpleExoPlayer.d();
            }
        }

        public /* bridge */ /* synthetic */ void onStaticMetadataChanged(List list) {
            t8.s(this, list);
        }

        public void onStreamTypeChanged(int i) {
            SimpleExoPlayer simpleExoPlayer = SimpleExoPlayer.this;
            StreamVolumeManager streamVolumeManager = simpleExoPlayer.p;
            DeviceInfo deviceInfo = new DeviceInfo(0, streamVolumeManager.getMinVolume(), streamVolumeManager.getMaxVolume());
            if (!deviceInfo.equals(simpleExoPlayer.at)) {
                simpleExoPlayer.at = deviceInfo;
                Iterator<DeviceListener> it = simpleExoPlayer.l.iterator();
                while (it.hasNext()) {
                    it.next().onDeviceInfoChanged(deviceInfo);
                }
            }
        }

        public void onStreamVolumeChanged(int i, boolean z) {
            Iterator<DeviceListener> it = SimpleExoPlayer.this.l.iterator();
            while (it.hasNext()) {
                it.next().onDeviceVolumeChanged(i, z);
            }
        }

        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
            SimpleExoPlayer simpleExoPlayer = SimpleExoPlayer.this;
            simpleExoPlayer.getClass();
            Surface surface = new Surface(surfaceTexture);
            simpleExoPlayer.h(surface);
            simpleExoPlayer.x = surface;
            simpleExoPlayer.c(i, i2);
        }

        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            SimpleExoPlayer simpleExoPlayer = SimpleExoPlayer.this;
            simpleExoPlayer.h((Object) null);
            simpleExoPlayer.c(0, 0);
            return true;
        }

        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
            SimpleExoPlayer.this.c(i, i2);
        }

        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }

        public /* bridge */ /* synthetic */ void onTimelineChanged(Timeline timeline, int i) {
            t8.t(this, timeline, i);
        }

        @Deprecated
        public /* bridge */ /* synthetic */ void onTimelineChanged(Timeline timeline, @Nullable Object obj, int i) {
            t8.u(this, timeline, obj, i);
        }

        public /* bridge */ /* synthetic */ void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
            t8.v(this, trackGroupArray, trackSelectionArray);
        }

        public void onVideoCodecError(Exception exc) {
            SimpleExoPlayer.this.m.onVideoCodecError(exc);
        }

        public void onVideoDecoderInitialized(String str, long j, long j2) {
            SimpleExoPlayer.this.m.onVideoDecoderInitialized(str, j, j2);
        }

        public void onVideoDecoderReleased(String str) {
            SimpleExoPlayer.this.m.onVideoDecoderReleased(str);
        }

        public void onVideoDisabled(DecoderCounters decoderCounters) {
            SimpleExoPlayer simpleExoPlayer = SimpleExoPlayer.this;
            simpleExoPlayer.m.onVideoDisabled(decoderCounters);
            simpleExoPlayer.t = null;
            simpleExoPlayer.af = null;
        }

        public void onVideoEnabled(DecoderCounters decoderCounters) {
            SimpleExoPlayer simpleExoPlayer = SimpleExoPlayer.this;
            simpleExoPlayer.af = decoderCounters;
            simpleExoPlayer.m.onVideoEnabled(decoderCounters);
        }

        public void onVideoFrameProcessingOffset(long j, int i) {
            SimpleExoPlayer.this.m.onVideoFrameProcessingOffset(j, i);
        }

        @Deprecated
        public /* bridge */ /* synthetic */ void onVideoInputFormatChanged(Format format) {
            pd.i(this, format);
        }

        public void onVideoInputFormatChanged(Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation) {
            SimpleExoPlayer simpleExoPlayer = SimpleExoPlayer.this;
            simpleExoPlayer.t = format;
            simpleExoPlayer.m.onVideoInputFormatChanged(format, decoderReuseEvaluation);
        }

        public void onVideoSizeChanged(VideoSize videoSize) {
            SimpleExoPlayer simpleExoPlayer = SimpleExoPlayer.this;
            simpleExoPlayer.au = videoSize;
            simpleExoPlayer.m.onVideoSizeChanged(videoSize);
            Iterator<VideoListener> it = simpleExoPlayer.h.iterator();
            while (it.hasNext()) {
                VideoListener next = it.next();
                next.onVideoSizeChanged(videoSize);
                next.onVideoSizeChanged(videoSize.c, videoSize.f, videoSize.g, videoSize.h);
            }
        }

        public void onVideoSurfaceCreated(Surface surface) {
            SimpleExoPlayer.this.h(surface);
        }

        public void onVideoSurfaceDestroyed(Surface surface) {
            SimpleExoPlayer.this.h((Object) null);
        }

        public void setVolumeMultiplier(float f) {
            SimpleExoPlayer simpleExoPlayer = SimpleExoPlayer.this;
            simpleExoPlayer.f(1, 2, Float.valueOf(simpleExoPlayer.o.getVolumeMultiplier() * simpleExoPlayer.aj));
        }

        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            SimpleExoPlayer.this.c(i2, i3);
        }

        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            SimpleExoPlayer simpleExoPlayer = SimpleExoPlayer.this;
            if (simpleExoPlayer.aa) {
                simpleExoPlayer.h(surfaceHolder.getSurface());
            }
        }

        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            SimpleExoPlayer simpleExoPlayer = SimpleExoPlayer.this;
            if (simpleExoPlayer.aa) {
                simpleExoPlayer.h((Object) null);
            }
            simpleExoPlayer.c(0, 0);
        }
    }

    public static final class b implements VideoFrameMetadataListener, CameraMotionListener, PlayerMessage.Target {
        @Nullable
        public VideoFrameMetadataListener c;
        @Nullable
        public CameraMotionListener f;
        @Nullable
        public VideoFrameMetadataListener g;
        @Nullable
        public CameraMotionListener h;

        public void handleMessage(int i, @Nullable Object obj) {
            if (i == 6) {
                this.c = (VideoFrameMetadataListener) obj;
            } else if (i == 7) {
                this.f = (CameraMotionListener) obj;
            } else if (i == 10000) {
                SphericalGLSurfaceView sphericalGLSurfaceView = (SphericalGLSurfaceView) obj;
                if (sphericalGLSurfaceView == null) {
                    this.g = null;
                    this.h = null;
                    return;
                }
                this.g = sphericalGLSurfaceView.getVideoFrameMetadataListener();
                this.h = sphericalGLSurfaceView.getCameraMotionListener();
            }
        }

        public void onCameraMotion(long j, float[] fArr) {
            CameraMotionListener cameraMotionListener = this.h;
            if (cameraMotionListener != null) {
                cameraMotionListener.onCameraMotion(j, fArr);
            }
            CameraMotionListener cameraMotionListener2 = this.f;
            if (cameraMotionListener2 != null) {
                cameraMotionListener2.onCameraMotion(j, fArr);
            }
        }

        public void onCameraMotionReset() {
            CameraMotionListener cameraMotionListener = this.h;
            if (cameraMotionListener != null) {
                cameraMotionListener.onCameraMotionReset();
            }
            CameraMotionListener cameraMotionListener2 = this.f;
            if (cameraMotionListener2 != null) {
                cameraMotionListener2.onCameraMotionReset();
            }
        }

        public void onVideoFrameAboutToBeRendered(long j, long j2, Format format, @Nullable MediaFormat mediaFormat) {
            VideoFrameMetadataListener videoFrameMetadataListener = this.g;
            if (videoFrameMetadataListener != null) {
                videoFrameMetadataListener.onVideoFrameAboutToBeRendered(j, j2, format, mediaFormat);
            }
            VideoFrameMetadataListener videoFrameMetadataListener2 = this.c;
            if (videoFrameMetadataListener2 != null) {
                videoFrameMetadataListener2.onVideoFrameAboutToBeRendered(j, j2, format, mediaFormat);
            }
        }
    }

    public SimpleExoPlayer(Builder builder) {
        SimpleExoPlayer simpleExoPlayer;
        AudioAttributes audioAttributes;
        boolean z2;
        boolean z3;
        Builder builder2 = builder;
        ConditionVariable conditionVariable = new ConditionVariable();
        this.c = conditionVariable;
        try {
            Context context = builder2.a;
            Context applicationContext = context.getApplicationContext();
            this.d = applicationContext;
            AnalyticsCollector analyticsCollector = builder2.i;
            this.m = analyticsCollector;
            this.aq = builder2.k;
            this.ai = builder2.l;
            this.ac = builder2.q;
            this.ak = builder2.p;
            this.s = builder2.v;
            a aVar = new a();
            this.f = aVar;
            b bVar = new b();
            this.g = bVar;
            this.h = new CopyOnWriteArraySet<>();
            this.i = new CopyOnWriteArraySet<>();
            this.j = new CopyOnWriteArraySet<>();
            this.k = new CopyOnWriteArraySet<>();
            this.l = new CopyOnWriteArraySet<>();
            Handler handler = new Handler(builder2.j);
            Renderer[] createRenderers = builder2.b.createRenderers(handler, aVar, aVar, aVar, aVar);
            this.b = createRenderers;
            this.aj = 1.0f;
            if (Util.a < 21) {
                this.ah = b(0);
            } else {
                this.ah = C.generateAudioSessionIdV21(applicationContext);
            }
            this.al = Collections.emptyList();
            this.ao = true;
            Player.Commands.Builder builder3 = new Player.Commands.Builder();
            int[] iArr = new int[8];
            iArr[0] = 15;
            iArr[1] = 16;
            iArr[2] = 17;
            iArr[3] = 18;
            iArr[4] = 19;
            iArr[5] = 20;
            try {
                iArr[6] = 21;
                iArr[7] = 22;
                Player.Commands build = builder3.addAll(iArr).build();
                TrackSelector trackSelector = builder2.e;
                MediaSourceFactory mediaSourceFactory = builder2.f;
                LoadControl loadControl = builder2.g;
                BandwidthMeter bandwidthMeter = builder2.h;
                boolean z4 = builder2.r;
                SeekParameters seekParameters = builder2.s;
                LivePlaybackSpeedControl livePlaybackSpeedControl = builder2.t;
                a aVar2 = aVar;
                long j2 = builder2.u;
                Context context2 = context;
                boolean z5 = builder2.w;
                ConditionVariable conditionVariable2 = conditionVariable;
                Clock clock = builder2.c;
                Looper looper = builder2.j;
                a aVar3 = r1;
                Handler handler2 = handler;
                b bVar2 = bVar;
                Context context3 = context2;
                ConditionVariable conditionVariable3 = conditionVariable2;
                a aVar4 = aVar2;
                a aVar5 = new a(createRenderers, trackSelector, mediaSourceFactory, loadControl, bandwidthMeter, analyticsCollector, z4, seekParameters, livePlaybackSpeedControl, j2, z5, clock, looper, this, build);
                simpleExoPlayer = this;
                try {
                    simpleExoPlayer.e = aVar3;
                    a aVar6 = aVar4;
                    aVar3.addListener((Player.EventListener) aVar6);
                    aVar3.addAudioOffloadListener(aVar6);
                    a aVar7 = aVar3;
                    Builder builder4 = builder;
                    long j3 = builder4.d;
                    if (j3 > 0) {
                        aVar7.experimentalSetForegroundModeTimeoutMs(j3);
                    }
                    Handler handler3 = handler2;
                    Context context4 = context3;
                    AudioBecomingNoisyManager audioBecomingNoisyManager = new AudioBecomingNoisyManager(context4, handler3, aVar6);
                    simpleExoPlayer.n = audioBecomingNoisyManager;
                    audioBecomingNoisyManager.setEnabled(builder4.o);
                    AudioFocusManager audioFocusManager = new AudioFocusManager(context4, handler3, aVar6);
                    simpleExoPlayer.o = audioFocusManager;
                    if (builder4.m) {
                        audioAttributes = simpleExoPlayer.ai;
                    } else {
                        audioAttributes = null;
                    }
                    audioFocusManager.setAudioAttributes(audioAttributes);
                    StreamVolumeManager streamVolumeManager = new StreamVolumeManager(context4, handler3, aVar6);
                    simpleExoPlayer.p = streamVolumeManager;
                    streamVolumeManager.setStreamType(Util.getStreamTypeForAudioUsage(simpleExoPlayer.ai.g));
                    ee eeVar = new ee(context4);
                    simpleExoPlayer.q = eeVar;
                    if (builder4.n != 0) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    eeVar.setEnabled(z2);
                    ff ffVar = new ff(context4);
                    simpleExoPlayer.r = ffVar;
                    if (builder4.n == 2) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    ffVar.setEnabled(z3);
                    simpleExoPlayer.at = new DeviceInfo(0, streamVolumeManager.getMinVolume(), streamVolumeManager.getMaxVolume());
                    simpleExoPlayer.au = VideoSize.i;
                    simpleExoPlayer.f(1, 102, Integer.valueOf(simpleExoPlayer.ah));
                    simpleExoPlayer.f(2, 102, Integer.valueOf(simpleExoPlayer.ah));
                    simpleExoPlayer.f(1, 3, simpleExoPlayer.ai);
                    simpleExoPlayer.f(2, 4, Integer.valueOf(simpleExoPlayer.ac));
                    simpleExoPlayer.f(1, 101, Boolean.valueOf(simpleExoPlayer.ak));
                    b bVar3 = bVar2;
                    simpleExoPlayer.f(2, 6, bVar3);
                    simpleExoPlayer.f(6, 7, bVar3);
                    conditionVariable3.open();
                } catch (Throwable th) {
                    th = th;
                    simpleExoPlayer.c.open();
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                simpleExoPlayer = this;
                simpleExoPlayer.c.open();
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            simpleExoPlayer = this;
            simpleExoPlayer.c.open();
            throw th;
        }
    }

    public static void a(SimpleExoPlayer simpleExoPlayer) {
        int playbackState = simpleExoPlayer.getPlaybackState();
        boolean z2 = false;
        ff ffVar = simpleExoPlayer.r;
        ee eeVar = simpleExoPlayer.q;
        if (playbackState != 1) {
            if (playbackState == 2 || playbackState == 3) {
                boolean experimentalIsSleepingForOffload = simpleExoPlayer.experimentalIsSleepingForOffload();
                if (simpleExoPlayer.getPlayWhenReady() && !experimentalIsSleepingForOffload) {
                    z2 = true;
                }
                eeVar.setStayAwake(z2);
                ffVar.setStayAwake(simpleExoPlayer.getPlayWhenReady());
                return;
            } else if (playbackState != 4) {
                throw new IllegalStateException();
            }
        }
        eeVar.setStayAwake(false);
        ffVar.setStayAwake(false);
    }

    public void addAnalyticsListener(AnalyticsListener analyticsListener) {
        Assertions.checkNotNull(analyticsListener);
        this.m.addListener(analyticsListener);
    }

    @Deprecated
    public void addAudioListener(AudioListener audioListener) {
        Assertions.checkNotNull(audioListener);
        this.i.add(audioListener);
    }

    public void addAudioOffloadListener(ExoPlayer.AudioOffloadListener audioOffloadListener) {
        this.e.addAudioOffloadListener(audioOffloadListener);
    }

    @Deprecated
    public void addDeviceListener(DeviceListener deviceListener) {
        Assertions.checkNotNull(deviceListener);
        this.l.add(deviceListener);
    }

    public void addListener(Player.Listener listener) {
        Assertions.checkNotNull(listener);
        addAudioListener(listener);
        addVideoListener(listener);
        addTextOutput(listener);
        addMetadataOutput(listener);
        addDeviceListener(listener);
        addListener((Player.EventListener) listener);
    }

    public void addMediaItems(int i2, List<MediaItem> list) {
        j();
        this.e.addMediaItems(i2, list);
    }

    public void addMediaSource(MediaSource mediaSource) {
        j();
        this.e.addMediaSource(mediaSource);
    }

    public void addMediaSources(List<MediaSource> list) {
        j();
        this.e.addMediaSources(list);
    }

    @Deprecated
    public void addMetadataOutput(MetadataOutput metadataOutput) {
        Assertions.checkNotNull(metadataOutput);
        this.k.add(metadataOutput);
    }

    @Deprecated
    public void addTextOutput(TextOutput textOutput) {
        Assertions.checkNotNull(textOutput);
        this.j.add(textOutput);
    }

    @Deprecated
    public void addVideoListener(VideoListener videoListener) {
        Assertions.checkNotNull(videoListener);
        this.h.add(videoListener);
    }

    public final int b(int i2) {
        AudioTrack audioTrack = this.v;
        if (!(audioTrack == null || audioTrack.getAudioSessionId() == i2)) {
            this.v.release();
            this.v = null;
        }
        if (this.v == null) {
            this.v = new AudioTrack(3, 4000, 4, 2, 2, 0, i2);
        }
        return this.v.getAudioSessionId();
    }

    public final void c(int i2, int i3) {
        if (i2 != this.ad || i3 != this.ae) {
            this.ad = i2;
            this.ae = i3;
            this.m.onSurfaceSizeChanged(i2, i3);
            Iterator<VideoListener> it = this.h.iterator();
            while (it.hasNext()) {
                it.next().onSurfaceSizeChanged(i2, i3);
            }
        }
    }

    public void clearAuxEffectInfo() {
        setAuxEffectInfo(new AuxEffectInfo(0, 0.0f));
    }

    public void clearCameraMotionListener(CameraMotionListener cameraMotionListener) {
        j();
        if (this.an == cameraMotionListener) {
            this.e.createMessage(this.g).setType(7).setPayload((Object) null).send();
        }
    }

    public void clearVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener) {
        j();
        if (this.am == videoFrameMetadataListener) {
            this.e.createMessage(this.g).setType(6).setPayload((Object) null).send();
        }
    }

    public void clearVideoSurface() {
        j();
        e();
        h((Object) null);
        c(0, 0);
    }

    public void clearVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder) {
        j();
        if (surfaceHolder != null && surfaceHolder == this.y) {
            clearVideoSurface();
        }
    }

    public void clearVideoSurfaceView(@Nullable SurfaceView surfaceView) {
        SurfaceHolder surfaceHolder;
        j();
        if (surfaceView == null) {
            surfaceHolder = null;
        } else {
            surfaceHolder = surfaceView.getHolder();
        }
        clearVideoSurfaceHolder(surfaceHolder);
    }

    public void clearVideoTextureView(@Nullable TextureView textureView) {
        j();
        if (textureView != null && textureView == this.ab) {
            clearVideoSurface();
        }
    }

    public PlayerMessage createMessage(PlayerMessage.Target target) {
        j();
        return this.e.createMessage(target);
    }

    public final void d() {
        this.m.onSkipSilenceEnabledChanged(this.ak);
        Iterator<AudioListener> it = this.i.iterator();
        while (it.hasNext()) {
            it.next().onSkipSilenceEnabledChanged(this.ak);
        }
    }

    public void decreaseDeviceVolume() {
        j();
        this.p.decreaseVolume();
    }

    public final void e() {
        SphericalGLSurfaceView sphericalGLSurfaceView = this.z;
        a aVar = this.f;
        if (sphericalGLSurfaceView != null) {
            this.e.createMessage(this.g).setType(10000).setPayload((Object) null).send();
            this.z.removeVideoSurfaceListener(aVar);
            this.z = null;
        }
        TextureView textureView = this.ab;
        if (textureView != null) {
            if (textureView.getSurfaceTextureListener() != aVar) {
                Log.w("SimpleExoPlayer", "SurfaceTextureListener already unset or replaced.");
            } else {
                this.ab.setSurfaceTextureListener((TextureView.SurfaceTextureListener) null);
            }
            this.ab = null;
        }
        SurfaceHolder surfaceHolder = this.y;
        if (surfaceHolder != null) {
            surfaceHolder.removeCallback(aVar);
            this.y = null;
        }
    }

    public boolean experimentalIsSleepingForOffload() {
        j();
        return this.e.experimentalIsSleepingForOffload();
    }

    public void experimentalSetOffloadSchedulingEnabled(boolean z2) {
        j();
        this.e.experimentalSetOffloadSchedulingEnabled(z2);
    }

    public final void f(int i2, int i3, @Nullable Object obj) {
        for (Renderer renderer : this.b) {
            if (renderer.getTrackType() == i2) {
                this.e.createMessage(renderer).setType(i3).setPayload(obj).send();
            }
        }
    }

    public final void g(SurfaceHolder surfaceHolder) {
        this.aa = false;
        this.y = surfaceHolder;
        surfaceHolder.addCallback(this.f);
        Surface surface = this.y.getSurface();
        if (surface == null || !surface.isValid()) {
            c(0, 0);
            return;
        }
        Rect surfaceFrame = this.y.getSurfaceFrame();
        c(surfaceFrame.width(), surfaceFrame.height());
    }

    public AnalyticsCollector getAnalyticsCollector() {
        return this.m;
    }

    public Looper getApplicationLooper() {
        return this.e.getApplicationLooper();
    }

    public AudioAttributes getAudioAttributes() {
        return this.ai;
    }

    @Nullable
    public ExoPlayer.AudioComponent getAudioComponent() {
        return this;
    }

    @Nullable
    public DecoderCounters getAudioDecoderCounters() {
        return this.ag;
    }

    @Nullable
    public Format getAudioFormat() {
        return this.u;
    }

    public int getAudioSessionId() {
        return this.ah;
    }

    public Player.Commands getAvailableCommands() {
        j();
        return this.e.getAvailableCommands();
    }

    public long getBufferedPosition() {
        j();
        return this.e.getBufferedPosition();
    }

    public Clock getClock() {
        return this.e.getClock();
    }

    public long getContentBufferedPosition() {
        j();
        return this.e.getContentBufferedPosition();
    }

    public long getContentPosition() {
        j();
        return this.e.getContentPosition();
    }

    public int getCurrentAdGroupIndex() {
        j();
        return this.e.getCurrentAdGroupIndex();
    }

    public int getCurrentAdIndexInAdGroup() {
        j();
        return this.e.getCurrentAdIndexInAdGroup();
    }

    public List<Cue> getCurrentCues() {
        j();
        return this.al;
    }

    public int getCurrentPeriodIndex() {
        j();
        return this.e.getCurrentPeriodIndex();
    }

    public long getCurrentPosition() {
        j();
        return this.e.getCurrentPosition();
    }

    public List<Metadata> getCurrentStaticMetadata() {
        j();
        return this.e.getCurrentStaticMetadata();
    }

    public Timeline getCurrentTimeline() {
        j();
        return this.e.getCurrentTimeline();
    }

    public TrackGroupArray getCurrentTrackGroups() {
        j();
        return this.e.getCurrentTrackGroups();
    }

    public TrackSelectionArray getCurrentTrackSelections() {
        j();
        return this.e.getCurrentTrackSelections();
    }

    public int getCurrentWindowIndex() {
        j();
        return this.e.getCurrentWindowIndex();
    }

    @Nullable
    public ExoPlayer.DeviceComponent getDeviceComponent() {
        return this;
    }

    public DeviceInfo getDeviceInfo() {
        j();
        return this.at;
    }

    public int getDeviceVolume() {
        j();
        return this.p.getVolume();
    }

    public long getDuration() {
        j();
        return this.e.getDuration();
    }

    public MediaMetadata getMediaMetadata() {
        return this.e.getMediaMetadata();
    }

    @Nullable
    public ExoPlayer.MetadataComponent getMetadataComponent() {
        return this;
    }

    public boolean getPauseAtEndOfMediaItems() {
        j();
        return this.e.getPauseAtEndOfMediaItems();
    }

    public boolean getPlayWhenReady() {
        j();
        return this.e.getPlayWhenReady();
    }

    public Looper getPlaybackLooper() {
        return this.e.getPlaybackLooper();
    }

    public PlaybackParameters getPlaybackParameters() {
        j();
        return this.e.getPlaybackParameters();
    }

    public int getPlaybackState() {
        j();
        return this.e.getPlaybackState();
    }

    public int getPlaybackSuppressionReason() {
        j();
        return this.e.getPlaybackSuppressionReason();
    }

    @Nullable
    public ExoPlaybackException getPlayerError() {
        j();
        return this.e.getPlayerError();
    }

    public int getRendererCount() {
        j();
        return this.e.getRendererCount();
    }

    public int getRendererType(int i2) {
        j();
        return this.e.getRendererType(i2);
    }

    public int getRepeatMode() {
        j();
        return this.e.getRepeatMode();
    }

    public SeekParameters getSeekParameters() {
        j();
        return this.e.getSeekParameters();
    }

    public boolean getShuffleModeEnabled() {
        j();
        return this.e.getShuffleModeEnabled();
    }

    public boolean getSkipSilenceEnabled() {
        return this.ak;
    }

    @Nullable
    public ExoPlayer.TextComponent getTextComponent() {
        return this;
    }

    public long getTotalBufferedDuration() {
        j();
        return this.e.getTotalBufferedDuration();
    }

    @Nullable
    public TrackSelector getTrackSelector() {
        j();
        return this.e.getTrackSelector();
    }

    @Nullable
    public ExoPlayer.VideoComponent getVideoComponent() {
        return this;
    }

    @Nullable
    public DecoderCounters getVideoDecoderCounters() {
        return this.af;
    }

    @Nullable
    public Format getVideoFormat() {
        return this.t;
    }

    public int getVideoScalingMode() {
        return this.ac;
    }

    public VideoSize getVideoSize() {
        return this.au;
    }

    public float getVolume() {
        return this.aj;
    }

    public final void h(@Nullable Object obj) {
        a aVar;
        ArrayList arrayList = new ArrayList();
        Renderer[] rendererArr = this.b;
        int length = rendererArr.length;
        int i2 = 0;
        while (true) {
            aVar = this.e;
            if (i2 >= length) {
                break;
            }
            Renderer renderer = rendererArr[i2];
            if (renderer.getTrackType() == 2) {
                arrayList.add(aVar.createMessage(renderer).setType(1).setPayload(obj).send());
            }
            i2++;
        }
        Object obj2 = this.w;
        if (!(obj2 == null || obj2 == obj)) {
            try {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((PlayerMessage) it.next()).blockUntilDelivered(this.s);
                }
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            } catch (TimeoutException unused2) {
                aVar.stop(false, ExoPlaybackException.createForRenderer(new ExoTimeoutException(3)));
            }
            Object obj3 = this.w;
            Surface surface = this.x;
            if (obj3 == surface) {
                surface.release();
                this.x = null;
            }
        }
        this.w = obj;
    }

    public final void i(boolean z2, int i2, int i3) {
        int i4 = 0;
        boolean z3 = z2 && i2 != -1;
        if (z3 && i2 != 1) {
            i4 = 1;
        }
        this.e.setPlayWhenReady(z3, i4, i3);
    }

    public void increaseDeviceVolume() {
        j();
        this.p.increaseVolume();
    }

    public boolean isDeviceMuted() {
        j();
        return this.p.isMuted();
    }

    public boolean isLoading() {
        j();
        return this.e.isLoading();
    }

    public boolean isPlayingAd() {
        j();
        return this.e.isPlayingAd();
    }

    public final void j() {
        IllegalStateException illegalStateException;
        this.c.blockUninterruptible();
        if (Thread.currentThread() != getApplicationLooper().getThread()) {
            String formatInvariant = Util.formatInvariant("Player is accessed on the wrong thread.\nCurrent thread: '%s'\nExpected thread: '%s'\nSee https://exoplayer.dev/issues/player-accessed-on-wrong-thread", Thread.currentThread().getName(), getApplicationLooper().getThread().getName());
            if (!this.ao) {
                if (this.ap) {
                    illegalStateException = null;
                } else {
                    illegalStateException = new IllegalStateException();
                }
                Log.w("SimpleExoPlayer", formatInvariant, illegalStateException);
                this.ap = true;
                return;
            }
            throw new IllegalStateException(formatInvariant);
        }
    }

    public void moveMediaItems(int i2, int i3, int i4) {
        j();
        this.e.moveMediaItems(i2, i3, i4);
    }

    public void prepare() {
        j();
        boolean playWhenReady = getPlayWhenReady();
        int i2 = 2;
        int updateAudioFocus = this.o.updateAudioFocus(playWhenReady, 2);
        if (!playWhenReady || updateAudioFocus == 1) {
            i2 = 1;
        }
        i(playWhenReady, updateAudioFocus, i2);
        this.e.prepare();
    }

    public void release() {
        AudioTrack audioTrack;
        j();
        if (Util.a < 21 && (audioTrack = this.v) != null) {
            audioTrack.release();
            this.v = null;
        }
        this.n.setEnabled(false);
        this.p.release();
        this.q.setStayAwake(false);
        this.r.setStayAwake(false);
        this.o.release();
        this.e.release();
        this.m.release();
        e();
        Surface surface = this.x;
        if (surface != null) {
            surface.release();
            this.x = null;
        }
        if (this.ar) {
            ((PriorityTaskManager) Assertions.checkNotNull(this.aq)).remove(0);
            this.ar = false;
        }
        this.al = Collections.emptyList();
        this.as = true;
    }

    public void removeAnalyticsListener(AnalyticsListener analyticsListener) {
        this.m.removeListener(analyticsListener);
    }

    @Deprecated
    public void removeAudioListener(AudioListener audioListener) {
        this.i.remove(audioListener);
    }

    public void removeAudioOffloadListener(ExoPlayer.AudioOffloadListener audioOffloadListener) {
        this.e.removeAudioOffloadListener(audioOffloadListener);
    }

    @Deprecated
    public void removeDeviceListener(DeviceListener deviceListener) {
        this.l.remove(deviceListener);
    }

    public void removeListener(Player.Listener listener) {
        Assertions.checkNotNull(listener);
        removeAudioListener(listener);
        removeVideoListener(listener);
        removeTextOutput(listener);
        removeMetadataOutput(listener);
        removeDeviceListener(listener);
        removeListener((Player.EventListener) listener);
    }

    public void removeMediaItems(int i2, int i3) {
        j();
        this.e.removeMediaItems(i2, i3);
    }

    @Deprecated
    public void removeMetadataOutput(MetadataOutput metadataOutput) {
        this.k.remove(metadataOutput);
    }

    @Deprecated
    public void removeTextOutput(TextOutput textOutput) {
        this.j.remove(textOutput);
    }

    @Deprecated
    public void removeVideoListener(VideoListener videoListener) {
        this.h.remove(videoListener);
    }

    @Deprecated
    public void retry() {
        j();
        prepare();
    }

    public void seekTo(int i2, long j2) {
        j();
        this.m.notifySeekStarted();
        this.e.seekTo(i2, j2);
    }

    public void setAudioAttributes(AudioAttributes audioAttributes, boolean z2) {
        j();
        if (!this.as) {
            int i2 = 1;
            if (!Util.areEqual(this.ai, audioAttributes)) {
                this.ai = audioAttributes;
                f(1, 3, audioAttributes);
                this.p.setStreamType(Util.getStreamTypeForAudioUsage(audioAttributes.g));
                this.m.onAudioAttributesChanged(audioAttributes);
                Iterator<AudioListener> it = this.i.iterator();
                while (it.hasNext()) {
                    it.next().onAudioAttributesChanged(audioAttributes);
                }
            }
            if (!z2) {
                audioAttributes = null;
            }
            AudioFocusManager audioFocusManager = this.o;
            audioFocusManager.setAudioAttributes(audioAttributes);
            boolean playWhenReady = getPlayWhenReady();
            int updateAudioFocus = audioFocusManager.updateAudioFocus(playWhenReady, getPlaybackState());
            if (playWhenReady && updateAudioFocus != 1) {
                i2 = 2;
            }
            i(playWhenReady, updateAudioFocus, i2);
        }
    }

    public void setAudioSessionId(int i2) {
        j();
        if (this.ah != i2) {
            if (i2 == 0) {
                if (Util.a < 21) {
                    i2 = b(0);
                } else {
                    i2 = C.generateAudioSessionIdV21(this.d);
                }
            } else if (Util.a < 21) {
                b(i2);
            }
            this.ah = i2;
            f(1, 102, Integer.valueOf(i2));
            f(2, 102, Integer.valueOf(i2));
            this.m.onAudioSessionIdChanged(i2);
            Iterator<AudioListener> it = this.i.iterator();
            while (it.hasNext()) {
                it.next().onAudioSessionIdChanged(i2);
            }
        }
    }

    public void setAuxEffectInfo(AuxEffectInfo auxEffectInfo) {
        j();
        f(1, 5, auxEffectInfo);
    }

    public void setCameraMotionListener(CameraMotionListener cameraMotionListener) {
        j();
        this.an = cameraMotionListener;
        this.e.createMessage(this.g).setType(7).setPayload(cameraMotionListener).send();
    }

    public void setDeviceMuted(boolean z2) {
        j();
        this.p.setMuted(z2);
    }

    public void setDeviceVolume(int i2) {
        j();
        this.p.setVolume(i2);
    }

    public void setForegroundMode(boolean z2) {
        j();
        this.e.setForegroundMode(z2);
    }

    public void setHandleAudioBecomingNoisy(boolean z2) {
        j();
        if (!this.as) {
            this.n.setEnabled(z2);
        }
    }

    @Deprecated
    public void setHandleWakeLock(boolean z2) {
        setWakeMode(z2 ? 1 : 0);
    }

    public void setMediaItems(List<MediaItem> list, boolean z2) {
        j();
        this.e.setMediaItems(list, z2);
    }

    public void setMediaSource(MediaSource mediaSource) {
        j();
        this.e.setMediaSource(mediaSource);
    }

    public void setMediaSources(List<MediaSource> list) {
        j();
        this.e.setMediaSources(list);
    }

    public void setPauseAtEndOfMediaItems(boolean z2) {
        j();
        this.e.setPauseAtEndOfMediaItems(z2);
    }

    public void setPlayWhenReady(boolean z2) {
        j();
        int updateAudioFocus = this.o.updateAudioFocus(z2, getPlaybackState());
        int i2 = 1;
        if (z2 && updateAudioFocus != 1) {
            i2 = 2;
        }
        i(z2, updateAudioFocus, i2);
    }

    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        j();
        this.e.setPlaybackParameters(playbackParameters);
    }

    public void setPriorityTaskManager(@Nullable PriorityTaskManager priorityTaskManager) {
        j();
        if (!Util.areEqual(this.aq, priorityTaskManager)) {
            if (this.ar) {
                ((PriorityTaskManager) Assertions.checkNotNull(this.aq)).remove(0);
            }
            if (priorityTaskManager == null || !isLoading()) {
                this.ar = false;
            } else {
                priorityTaskManager.add(0);
                this.ar = true;
            }
            this.aq = priorityTaskManager;
        }
    }

    public void setRepeatMode(int i2) {
        j();
        this.e.setRepeatMode(i2);
    }

    public void setSeekParameters(@Nullable SeekParameters seekParameters) {
        j();
        this.e.setSeekParameters(seekParameters);
    }

    public void setShuffleModeEnabled(boolean z2) {
        j();
        this.e.setShuffleModeEnabled(z2);
    }

    public void setShuffleOrder(ShuffleOrder shuffleOrder) {
        j();
        this.e.setShuffleOrder(shuffleOrder);
    }

    public void setSkipSilenceEnabled(boolean z2) {
        j();
        if (this.ak != z2) {
            this.ak = z2;
            f(1, 101, Boolean.valueOf(z2));
            d();
        }
    }

    @Deprecated
    public void setThrowsWhenUsingWrongThread(boolean z2) {
        this.ao = z2;
    }

    public void setVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener) {
        j();
        this.am = videoFrameMetadataListener;
        this.e.createMessage(this.g).setType(6).setPayload(videoFrameMetadataListener).send();
    }

    public void setVideoScalingMode(int i2) {
        j();
        this.ac = i2;
        f(2, 4, Integer.valueOf(i2));
    }

    public void setVideoSurface(@Nullable Surface surface) {
        int i2;
        j();
        e();
        h(surface);
        if (surface == null) {
            i2 = 0;
        } else {
            i2 = -1;
        }
        c(i2, i2);
    }

    public void setVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder) {
        j();
        if (surfaceHolder == null) {
            clearVideoSurface();
            return;
        }
        e();
        this.aa = true;
        this.y = surfaceHolder;
        surfaceHolder.addCallback(this.f);
        Surface surface = surfaceHolder.getSurface();
        if (surface == null || !surface.isValid()) {
            h((Object) null);
            c(0, 0);
            return;
        }
        h(surface);
        Rect surfaceFrame = surfaceHolder.getSurfaceFrame();
        c(surfaceFrame.width(), surfaceFrame.height());
    }

    public void setVideoSurfaceView(@Nullable SurfaceView surfaceView) {
        SurfaceHolder surfaceHolder;
        j();
        if (surfaceView instanceof VideoDecoderOutputBufferRenderer) {
            e();
            h(surfaceView);
            g(surfaceView.getHolder());
        } else if (surfaceView instanceof SphericalGLSurfaceView) {
            e();
            this.z = (SphericalGLSurfaceView) surfaceView;
            this.e.createMessage(this.g).setType(10000).setPayload(this.z).send();
            this.z.addVideoSurfaceListener(this.f);
            h(this.z.getVideoSurface());
            g(surfaceView.getHolder());
        } else {
            if (surfaceView == null) {
                surfaceHolder = null;
            } else {
                surfaceHolder = surfaceView.getHolder();
            }
            setVideoSurfaceHolder(surfaceHolder);
        }
    }

    public void setVideoTextureView(@Nullable TextureView textureView) {
        SurfaceTexture surfaceTexture;
        j();
        if (textureView == null) {
            clearVideoSurface();
            return;
        }
        e();
        this.ab = textureView;
        if (textureView.getSurfaceTextureListener() != null) {
            Log.w("SimpleExoPlayer", "Replacing existing SurfaceTextureListener.");
        }
        textureView.setSurfaceTextureListener(this.f);
        if (textureView.isAvailable()) {
            surfaceTexture = textureView.getSurfaceTexture();
        } else {
            surfaceTexture = null;
        }
        if (surfaceTexture == null) {
            h((Object) null);
            c(0, 0);
            return;
        }
        Surface surface = new Surface(surfaceTexture);
        h(surface);
        this.x = surface;
        c(textureView.getWidth(), textureView.getHeight());
    }

    public void setVolume(float f2) {
        j();
        float constrainValue = Util.constrainValue(f2, 0.0f, 1.0f);
        if (this.aj != constrainValue) {
            this.aj = constrainValue;
            f(1, 2, Float.valueOf(this.o.getVolumeMultiplier() * constrainValue));
            this.m.onVolumeChanged(constrainValue);
            Iterator<AudioListener> it = this.i.iterator();
            while (it.hasNext()) {
                it.next().onVolumeChanged(constrainValue);
            }
        }
    }

    public void setWakeMode(int i2) {
        j();
        ff ffVar = this.r;
        ee eeVar = this.q;
        if (i2 == 0) {
            eeVar.setEnabled(false);
            ffVar.setEnabled(false);
        } else if (i2 == 1) {
            eeVar.setEnabled(true);
            ffVar.setEnabled(false);
        } else if (i2 == 2) {
            eeVar.setEnabled(true);
            ffVar.setEnabled(true);
        }
    }

    @Deprecated
    public void stop(boolean z2) {
        j();
        this.o.updateAudioFocus(getPlayWhenReady(), 1);
        this.e.stop(z2);
        this.al = Collections.emptyList();
    }

    public void addMediaSource(int i2, MediaSource mediaSource) {
        j();
        this.e.addMediaSource(i2, mediaSource);
    }

    public void addMediaSources(int i2, List<MediaSource> list) {
        j();
        this.e.addMediaSources(i2, list);
    }

    public void setMediaItems(List<MediaItem> list, int i2, long j2) {
        j();
        this.e.setMediaItems(list, i2, j2);
    }

    public void setMediaSource(MediaSource mediaSource, boolean z2) {
        j();
        this.e.setMediaSource(mediaSource, z2);
    }

    public void setMediaSources(List<MediaSource> list, boolean z2) {
        j();
        this.e.setMediaSources(list, z2);
    }

    public void clearVideoSurface(@Nullable Surface surface) {
        j();
        if (surface != null && surface == this.w) {
            clearVideoSurface();
        }
    }

    public void setMediaSource(MediaSource mediaSource, long j2) {
        j();
        this.e.setMediaSource(mediaSource, j2);
    }

    public void setMediaSources(List<MediaSource> list, int i2, long j2) {
        j();
        this.e.setMediaSources(list, i2, j2);
    }

    @Deprecated
    public void prepare(MediaSource mediaSource) {
        prepare(mediaSource, true, true);
    }

    @Deprecated
    public void prepare(MediaSource mediaSource, boolean z2, boolean z3) {
        j();
        setMediaSources(Collections.singletonList(mediaSource), z2);
        prepare();
    }

    @Deprecated
    public void addListener(Player.EventListener eventListener) {
        Assertions.checkNotNull(eventListener);
        this.e.addListener(eventListener);
    }

    @Deprecated
    public void removeListener(Player.EventListener eventListener) {
        this.e.removeListener(eventListener);
    }
}
