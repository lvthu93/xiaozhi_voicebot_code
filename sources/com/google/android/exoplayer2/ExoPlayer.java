package com.google.android.exoplayer2;

import android.content.Context;
import android.os.Looper;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.DefaultLivePlaybackSpeedControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.PlayerMessage;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.audio.AudioListener;
import com.google.android.exoplayer2.audio.AuxEffectInfo;
import com.google.android.exoplayer2.device.DeviceInfo;
import com.google.android.exoplayer2.device.DeviceListener;
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
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoFrameMetadataListener;
import com.google.android.exoplayer2.video.VideoListener;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.android.exoplayer2.video.spherical.CameraMotionListener;
import java.util.List;

public interface ExoPlayer extends Player {

    public interface AudioComponent {
        @Deprecated
        void addAudioListener(AudioListener audioListener);

        void clearAuxEffectInfo();

        AudioAttributes getAudioAttributes();

        int getAudioSessionId();

        boolean getSkipSilenceEnabled();

        float getVolume();

        @Deprecated
        void removeAudioListener(AudioListener audioListener);

        void setAudioAttributes(AudioAttributes audioAttributes, boolean z);

        void setAudioSessionId(int i);

        void setAuxEffectInfo(AuxEffectInfo auxEffectInfo);

        void setSkipSilenceEnabled(boolean z);

        void setVolume(float f);
    }

    public interface AudioOffloadListener {
        void onExperimentalOffloadSchedulingEnabledChanged(boolean z);

        void onExperimentalSleepingForOffloadChanged(boolean z);
    }

    public interface DeviceComponent {
        @Deprecated
        void addDeviceListener(DeviceListener deviceListener);

        void decreaseDeviceVolume();

        DeviceInfo getDeviceInfo();

        int getDeviceVolume();

        void increaseDeviceVolume();

        boolean isDeviceMuted();

        @Deprecated
        void removeDeviceListener(DeviceListener deviceListener);

        void setDeviceMuted(boolean z);

        void setDeviceVolume(int i);
    }

    public interface MetadataComponent {
        @Deprecated
        void addMetadataOutput(MetadataOutput metadataOutput);

        @Deprecated
        void removeMetadataOutput(MetadataOutput metadataOutput);
    }

    public interface TextComponent {
        @Deprecated
        void addTextOutput(TextOutput textOutput);

        List<Cue> getCurrentCues();

        @Deprecated
        void removeTextOutput(TextOutput textOutput);
    }

    public interface VideoComponent {
        @Deprecated
        void addVideoListener(VideoListener videoListener);

        void clearCameraMotionListener(CameraMotionListener cameraMotionListener);

        void clearVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener);

        void clearVideoSurface();

        void clearVideoSurface(@Nullable Surface surface);

        void clearVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder);

        void clearVideoSurfaceView(@Nullable SurfaceView surfaceView);

        void clearVideoTextureView(@Nullable TextureView textureView);

        int getVideoScalingMode();

        VideoSize getVideoSize();

        @Deprecated
        void removeVideoListener(VideoListener videoListener);

        void setCameraMotionListener(CameraMotionListener cameraMotionListener);

        void setVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener);

        void setVideoScalingMode(int i);

        void setVideoSurface(@Nullable Surface surface);

        void setVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder);

        void setVideoSurfaceView(@Nullable SurfaceView surfaceView);

        void setVideoTextureView(@Nullable TextureView textureView);
    }

    void addAudioOffloadListener(AudioOffloadListener audioOffloadListener);

    @Deprecated
    /* synthetic */ void addListener(Player.EventListener eventListener);

    /* synthetic */ void addListener(Player.Listener listener);

    /* synthetic */ void addMediaItem(int i, MediaItem mediaItem);

    /* synthetic */ void addMediaItem(MediaItem mediaItem);

    /* synthetic */ void addMediaItems(int i, List<MediaItem> list);

    /* synthetic */ void addMediaItems(List<MediaItem> list);

    void addMediaSource(int i, MediaSource mediaSource);

    void addMediaSource(MediaSource mediaSource);

    void addMediaSources(int i, List<MediaSource> list);

    void addMediaSources(List<MediaSource> list);

    /* synthetic */ void clearMediaItems();

    /* synthetic */ void clearVideoSurface();

    /* synthetic */ void clearVideoSurface(@Nullable Surface surface);

    /* synthetic */ void clearVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder);

    /* synthetic */ void clearVideoSurfaceView(@Nullable SurfaceView surfaceView);

    /* synthetic */ void clearVideoTextureView(@Nullable TextureView textureView);

    PlayerMessage createMessage(PlayerMessage.Target target);

    /* synthetic */ void decreaseDeviceVolume();

    boolean experimentalIsSleepingForOffload();

    void experimentalSetOffloadSchedulingEnabled(boolean z);

    /* synthetic */ Looper getApplicationLooper();

    /* synthetic */ AudioAttributes getAudioAttributes();

    @Nullable
    AudioComponent getAudioComponent();

    /* synthetic */ Player.Commands getAvailableCommands();

    /* synthetic */ int getBufferedPercentage();

    /* synthetic */ long getBufferedPosition();

    Clock getClock();

    /* synthetic */ long getContentBufferedPosition();

    /* synthetic */ long getContentDuration();

    /* synthetic */ long getContentPosition();

    /* synthetic */ int getCurrentAdGroupIndex();

    /* synthetic */ int getCurrentAdIndexInAdGroup();

    /* synthetic */ List<Cue> getCurrentCues();

    /* synthetic */ long getCurrentLiveOffset();

    @Nullable
    /* synthetic */ Object getCurrentManifest();

    @Nullable
    /* synthetic */ MediaItem getCurrentMediaItem();

    /* synthetic */ int getCurrentPeriodIndex();

    /* synthetic */ long getCurrentPosition();

    /* synthetic */ List<Metadata> getCurrentStaticMetadata();

    @Deprecated
    @Nullable
    /* synthetic */ Object getCurrentTag();

    /* synthetic */ Timeline getCurrentTimeline();

    /* synthetic */ TrackGroupArray getCurrentTrackGroups();

    /* synthetic */ TrackSelectionArray getCurrentTrackSelections();

    /* synthetic */ int getCurrentWindowIndex();

    @Nullable
    DeviceComponent getDeviceComponent();

    /* synthetic */ DeviceInfo getDeviceInfo();

    /* synthetic */ int getDeviceVolume();

    /* synthetic */ long getDuration();

    /* synthetic */ MediaItem getMediaItemAt(int i);

    /* synthetic */ int getMediaItemCount();

    /* synthetic */ MediaMetadata getMediaMetadata();

    @Nullable
    MetadataComponent getMetadataComponent();

    /* synthetic */ int getNextWindowIndex();

    boolean getPauseAtEndOfMediaItems();

    /* synthetic */ boolean getPlayWhenReady();

    @Deprecated
    @Nullable
    /* synthetic */ ExoPlaybackException getPlaybackError();

    Looper getPlaybackLooper();

    /* synthetic */ PlaybackParameters getPlaybackParameters();

    /* synthetic */ int getPlaybackState();

    /* synthetic */ int getPlaybackSuppressionReason();

    @Nullable
    /* synthetic */ ExoPlaybackException getPlayerError();

    /* synthetic */ int getPreviousWindowIndex();

    int getRendererCount();

    int getRendererType(int i);

    /* synthetic */ int getRepeatMode();

    SeekParameters getSeekParameters();

    /* synthetic */ boolean getShuffleModeEnabled();

    @Nullable
    TextComponent getTextComponent();

    /* synthetic */ long getTotalBufferedDuration();

    @Nullable
    TrackSelector getTrackSelector();

    @Nullable
    VideoComponent getVideoComponent();

    /* synthetic */ VideoSize getVideoSize();

    /* synthetic */ float getVolume();

    /* synthetic */ boolean hasNext();

    /* synthetic */ boolean hasPrevious();

    /* synthetic */ void increaseDeviceVolume();

    /* synthetic */ boolean isCommandAvailable(int i);

    /* synthetic */ boolean isCurrentWindowDynamic();

    /* synthetic */ boolean isCurrentWindowLive();

    /* synthetic */ boolean isCurrentWindowSeekable();

    /* synthetic */ boolean isDeviceMuted();

    /* synthetic */ boolean isLoading();

    /* synthetic */ boolean isPlaying();

    /* synthetic */ boolean isPlayingAd();

    /* synthetic */ void moveMediaItem(int i, int i2);

    /* synthetic */ void moveMediaItems(int i, int i2, int i3);

    /* synthetic */ void next();

    /* synthetic */ void pause();

    /* synthetic */ void play();

    /* synthetic */ void prepare();

    @Deprecated
    void prepare(MediaSource mediaSource);

    @Deprecated
    void prepare(MediaSource mediaSource, boolean z, boolean z2);

    /* synthetic */ void previous();

    /* synthetic */ void release();

    void removeAudioOffloadListener(AudioOffloadListener audioOffloadListener);

    @Deprecated
    /* synthetic */ void removeListener(Player.EventListener eventListener);

    /* synthetic */ void removeListener(Player.Listener listener);

    /* synthetic */ void removeMediaItem(int i);

    /* synthetic */ void removeMediaItems(int i, int i2);

    @Deprecated
    void retry();

    /* synthetic */ void seekTo(int i, long j);

    /* synthetic */ void seekTo(long j);

    /* synthetic */ void seekToDefaultPosition();

    /* synthetic */ void seekToDefaultPosition(int i);

    /* synthetic */ void setDeviceMuted(boolean z);

    /* synthetic */ void setDeviceVolume(int i);

    void setForegroundMode(boolean z);

    /* synthetic */ void setMediaItem(MediaItem mediaItem);

    /* synthetic */ void setMediaItem(MediaItem mediaItem, long j);

    /* synthetic */ void setMediaItem(MediaItem mediaItem, boolean z);

    /* synthetic */ void setMediaItems(List<MediaItem> list);

    /* synthetic */ void setMediaItems(List<MediaItem> list, int i, long j);

    /* synthetic */ void setMediaItems(List<MediaItem> list, boolean z);

    void setMediaSource(MediaSource mediaSource);

    void setMediaSource(MediaSource mediaSource, long j);

    void setMediaSource(MediaSource mediaSource, boolean z);

    void setMediaSources(List<MediaSource> list);

    void setMediaSources(List<MediaSource> list, int i, long j);

    void setMediaSources(List<MediaSource> list, boolean z);

    void setPauseAtEndOfMediaItems(boolean z);

    /* synthetic */ void setPlayWhenReady(boolean z);

    /* synthetic */ void setPlaybackParameters(PlaybackParameters playbackParameters);

    /* synthetic */ void setPlaybackSpeed(float f);

    /* synthetic */ void setRepeatMode(int i);

    void setSeekParameters(@Nullable SeekParameters seekParameters);

    /* synthetic */ void setShuffleModeEnabled(boolean z);

    void setShuffleOrder(ShuffleOrder shuffleOrder);

    /* synthetic */ void setVideoSurface(@Nullable Surface surface);

    /* synthetic */ void setVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder);

    /* synthetic */ void setVideoSurfaceView(@Nullable SurfaceView surfaceView);

    /* synthetic */ void setVideoTextureView(@Nullable TextureView textureView);

    /* synthetic */ void setVolume(float f);

    /* synthetic */ void stop();

    @Deprecated
    /* synthetic */ void stop(boolean z);

    @Deprecated
    public static final class Builder {
        public final Renderer[] a;
        public Clock b;
        public TrackSelector c;
        public MediaSourceFactory d;
        public LoadControl e;
        public BandwidthMeter f;
        public Looper g;
        @Nullable
        public AnalyticsCollector h;
        public boolean i;
        public SeekParameters j;
        public boolean k;
        public long l;
        public LivePlaybackSpeedControl m;
        public boolean n;
        public long o;

        public Builder(Context context, Renderer... rendererArr) {
            this(rendererArr, new DefaultTrackSelector(context), new DefaultMediaSourceFactory(context), new DefaultLoadControl(), DefaultBandwidthMeter.getSingletonInstance(context));
        }

        public ExoPlayer build() {
            Assertions.checkState(!this.n);
            this.n = true;
            a aVar = new a(this.a, this.c, this.d, this.e, this.f, this.h, this.i, this.j, this.m, this.l, this.k, this.b, this.g, (Player) null, Player.Commands.b);
            long j2 = this.o;
            if (j2 > 0) {
                aVar.experimentalSetForegroundModeTimeoutMs(j2);
            }
            return aVar;
        }

        public Builder experimentalSetForegroundModeTimeoutMs(long j2) {
            Assertions.checkState(!this.n);
            this.o = j2;
            return this;
        }

        public Builder setAnalyticsCollector(AnalyticsCollector analyticsCollector) {
            Assertions.checkState(!this.n);
            this.h = analyticsCollector;
            return this;
        }

        public Builder setBandwidthMeter(BandwidthMeter bandwidthMeter) {
            Assertions.checkState(!this.n);
            this.f = bandwidthMeter;
            return this;
        }

        @VisibleForTesting
        public Builder setClock(Clock clock) {
            Assertions.checkState(!this.n);
            this.b = clock;
            return this;
        }

        public Builder setLivePlaybackSpeedControl(LivePlaybackSpeedControl livePlaybackSpeedControl) {
            Assertions.checkState(!this.n);
            this.m = livePlaybackSpeedControl;
            return this;
        }

        public Builder setLoadControl(LoadControl loadControl) {
            Assertions.checkState(!this.n);
            this.e = loadControl;
            return this;
        }

        public Builder setLooper(Looper looper) {
            Assertions.checkState(!this.n);
            this.g = looper;
            return this;
        }

        public Builder setMediaSourceFactory(MediaSourceFactory mediaSourceFactory) {
            Assertions.checkState(!this.n);
            this.d = mediaSourceFactory;
            return this;
        }

        public Builder setPauseAtEndOfMediaItems(boolean z) {
            Assertions.checkState(!this.n);
            this.k = z;
            return this;
        }

        public Builder setReleaseTimeoutMs(long j2) {
            Assertions.checkState(!this.n);
            this.l = j2;
            return this;
        }

        public Builder setSeekParameters(SeekParameters seekParameters) {
            Assertions.checkState(!this.n);
            this.j = seekParameters;
            return this;
        }

        public Builder setTrackSelector(TrackSelector trackSelector) {
            Assertions.checkState(!this.n);
            this.c = trackSelector;
            return this;
        }

        public Builder setUseLazyPreparation(boolean z) {
            Assertions.checkState(!this.n);
            this.i = z;
            return this;
        }

        public Builder(Renderer[] rendererArr, TrackSelector trackSelector, MediaSourceFactory mediaSourceFactory, LoadControl loadControl, BandwidthMeter bandwidthMeter) {
            Assertions.checkArgument(rendererArr.length > 0);
            this.a = rendererArr;
            this.c = trackSelector;
            this.d = mediaSourceFactory;
            this.e = loadControl;
            this.f = bandwidthMeter;
            this.g = Util.getCurrentOrMainLooper();
            this.i = true;
            this.j = SeekParameters.c;
            this.m = new DefaultLivePlaybackSpeedControl.Builder().build();
            this.b = Clock.a;
            this.l = 500;
        }
    }
}
