package com.google.android.exoplayer2;

import android.os.Looper;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ActivityChooserView;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.device.DeviceInfo;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoSize;
import java.util.Collections;
import java.util.List;

public abstract class BasePlayer implements Player {
    public final Timeline.Window a = new Timeline.Window();

    @Deprecated
    public abstract /* synthetic */ void addListener(Player.EventListener eventListener);

    public abstract /* synthetic */ void addListener(Player.Listener listener);

    public final void addMediaItem(int i, MediaItem mediaItem) {
        addMediaItems(i, Collections.singletonList(mediaItem));
    }

    public abstract /* synthetic */ void addMediaItems(int i, List<MediaItem> list);

    public final void addMediaItems(List<MediaItem> list) {
        addMediaItems(ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, list);
    }

    public final void clearMediaItems() {
        removeMediaItems(0, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
    }

    public abstract /* synthetic */ void clearVideoSurface();

    public abstract /* synthetic */ void clearVideoSurface(@Nullable Surface surface);

    public abstract /* synthetic */ void clearVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder);

    public abstract /* synthetic */ void clearVideoSurfaceView(@Nullable SurfaceView surfaceView);

    public abstract /* synthetic */ void clearVideoTextureView(@Nullable TextureView textureView);

    public abstract /* synthetic */ void decreaseDeviceVolume();

    public abstract /* synthetic */ Looper getApplicationLooper();

    public abstract /* synthetic */ AudioAttributes getAudioAttributes();

    public abstract /* synthetic */ Player.Commands getAvailableCommands();

    public final int getBufferedPercentage() {
        long bufferedPosition = getBufferedPosition();
        long duration = getDuration();
        if (bufferedPosition == -9223372036854775807L || duration == -9223372036854775807L) {
            return 0;
        }
        if (duration == 0) {
            return 100;
        }
        return Util.constrainValue((int) ((bufferedPosition * 100) / duration), 0, 100);
    }

    public abstract /* synthetic */ long getBufferedPosition();

    public abstract /* synthetic */ long getContentBufferedPosition();

    public final long getContentDuration() {
        Timeline currentTimeline = getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return -9223372036854775807L;
        }
        return currentTimeline.getWindow(getCurrentWindowIndex(), this.a).getDurationMs();
    }

    public abstract /* synthetic */ long getContentPosition();

    public abstract /* synthetic */ int getCurrentAdGroupIndex();

    public abstract /* synthetic */ int getCurrentAdIndexInAdGroup();

    public abstract /* synthetic */ List<Cue> getCurrentCues();

    public final long getCurrentLiveOffset() {
        Timeline currentTimeline = getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return -9223372036854775807L;
        }
        int currentWindowIndex = getCurrentWindowIndex();
        Timeline.Window window = this.a;
        if (currentTimeline.getWindow(currentWindowIndex, window).j == -9223372036854775807L) {
            return -9223372036854775807L;
        }
        return (window.getCurrentUnixTimeMs() - window.j) - getContentPosition();
    }

    @Nullable
    public final Object getCurrentManifest() {
        Timeline currentTimeline = getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return null;
        }
        return currentTimeline.getWindow(getCurrentWindowIndex(), this.a).h;
    }

    @Nullable
    public final MediaItem getCurrentMediaItem() {
        Timeline currentTimeline = getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return null;
        }
        return currentTimeline.getWindow(getCurrentWindowIndex(), this.a).g;
    }

    public abstract /* synthetic */ int getCurrentPeriodIndex();

    public abstract /* synthetic */ long getCurrentPosition();

    public abstract /* synthetic */ List<Metadata> getCurrentStaticMetadata();

    @Deprecated
    @Nullable
    public final Object getCurrentTag() {
        MediaItem.PlaybackProperties playbackProperties;
        Timeline currentTimeline = getCurrentTimeline();
        if (!currentTimeline.isEmpty() && (playbackProperties = currentTimeline.getWindow(getCurrentWindowIndex(), this.a).g.f) != null) {
            return playbackProperties.h;
        }
        return null;
    }

    public abstract /* synthetic */ Timeline getCurrentTimeline();

    public abstract /* synthetic */ TrackGroupArray getCurrentTrackGroups();

    public abstract /* synthetic */ TrackSelectionArray getCurrentTrackSelections();

    public abstract /* synthetic */ int getCurrentWindowIndex();

    public abstract /* synthetic */ DeviceInfo getDeviceInfo();

    public abstract /* synthetic */ int getDeviceVolume();

    public abstract /* synthetic */ long getDuration();

    public final MediaItem getMediaItemAt(int i) {
        return getCurrentTimeline().getWindow(i, this.a).g;
    }

    public final int getMediaItemCount() {
        return getCurrentTimeline().getWindowCount();
    }

    public abstract /* synthetic */ MediaMetadata getMediaMetadata();

    public final int getNextWindowIndex() {
        Timeline currentTimeline = getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return -1;
        }
        int currentWindowIndex = getCurrentWindowIndex();
        int repeatMode = getRepeatMode();
        if (repeatMode == 1) {
            repeatMode = 0;
        }
        return currentTimeline.getNextWindowIndex(currentWindowIndex, repeatMode, getShuffleModeEnabled());
    }

    public abstract /* synthetic */ boolean getPlayWhenReady();

    @Deprecated
    @Nullable
    public final ExoPlaybackException getPlaybackError() {
        return getPlayerError();
    }

    public abstract /* synthetic */ PlaybackParameters getPlaybackParameters();

    public abstract /* synthetic */ int getPlaybackState();

    public abstract /* synthetic */ int getPlaybackSuppressionReason();

    @Nullable
    public abstract /* synthetic */ ExoPlaybackException getPlayerError();

    public final int getPreviousWindowIndex() {
        Timeline currentTimeline = getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return -1;
        }
        int currentWindowIndex = getCurrentWindowIndex();
        int repeatMode = getRepeatMode();
        if (repeatMode == 1) {
            repeatMode = 0;
        }
        return currentTimeline.getPreviousWindowIndex(currentWindowIndex, repeatMode, getShuffleModeEnabled());
    }

    public abstract /* synthetic */ int getRepeatMode();

    public abstract /* synthetic */ boolean getShuffleModeEnabled();

    public abstract /* synthetic */ long getTotalBufferedDuration();

    public abstract /* synthetic */ VideoSize getVideoSize();

    public abstract /* synthetic */ float getVolume();

    public final boolean hasNext() {
        return getNextWindowIndex() != -1;
    }

    public final boolean hasPrevious() {
        return getPreviousWindowIndex() != -1;
    }

    public abstract /* synthetic */ void increaseDeviceVolume();

    public final boolean isCommandAvailable(int i) {
        return getAvailableCommands().contains(i);
    }

    public final boolean isCurrentWindowDynamic() {
        Timeline currentTimeline = getCurrentTimeline();
        if (currentTimeline.isEmpty() || !currentTimeline.getWindow(getCurrentWindowIndex(), this.a).m) {
            return false;
        }
        return true;
    }

    public final boolean isCurrentWindowLive() {
        Timeline currentTimeline = getCurrentTimeline();
        if (currentTimeline.isEmpty() || !currentTimeline.getWindow(getCurrentWindowIndex(), this.a).isLive()) {
            return false;
        }
        return true;
    }

    public final boolean isCurrentWindowSeekable() {
        Timeline currentTimeline = getCurrentTimeline();
        if (currentTimeline.isEmpty() || !currentTimeline.getWindow(getCurrentWindowIndex(), this.a).l) {
            return false;
        }
        return true;
    }

    public abstract /* synthetic */ boolean isDeviceMuted();

    public abstract /* synthetic */ boolean isLoading();

    public final boolean isPlaying() {
        if (getPlaybackState() == 3 && getPlayWhenReady() && getPlaybackSuppressionReason() == 0) {
            return true;
        }
        return false;
    }

    public abstract /* synthetic */ boolean isPlayingAd();

    public final void moveMediaItem(int i, int i2) {
        if (i != i2) {
            moveMediaItems(i, i + 1, i2);
        }
    }

    public abstract /* synthetic */ void moveMediaItems(int i, int i2, int i3);

    public final void next() {
        int nextWindowIndex = getNextWindowIndex();
        if (nextWindowIndex != -1) {
            seekToDefaultPosition(nextWindowIndex);
        }
    }

    public final void pause() {
        setPlayWhenReady(false);
    }

    public final void play() {
        setPlayWhenReady(true);
    }

    public abstract /* synthetic */ void prepare();

    public final void previous() {
        int previousWindowIndex = getPreviousWindowIndex();
        if (previousWindowIndex != -1) {
            seekToDefaultPosition(previousWindowIndex);
        }
    }

    public abstract /* synthetic */ void release();

    @Deprecated
    public abstract /* synthetic */ void removeListener(Player.EventListener eventListener);

    public abstract /* synthetic */ void removeListener(Player.Listener listener);

    public final void removeMediaItem(int i) {
        removeMediaItems(i, i + 1);
    }

    public abstract /* synthetic */ void removeMediaItems(int i, int i2);

    public abstract /* synthetic */ void seekTo(int i, long j);

    public final void seekTo(long j) {
        seekTo(getCurrentWindowIndex(), j);
    }

    public final void seekToDefaultPosition() {
        seekToDefaultPosition(getCurrentWindowIndex());
    }

    public abstract /* synthetic */ void setDeviceMuted(boolean z);

    public abstract /* synthetic */ void setDeviceVolume(int i);

    public final void setMediaItem(MediaItem mediaItem) {
        setMediaItems(Collections.singletonList(mediaItem));
    }

    public final void setMediaItems(List<MediaItem> list) {
        setMediaItems(list, true);
    }

    public abstract /* synthetic */ void setMediaItems(List<MediaItem> list, int i, long j);

    public abstract /* synthetic */ void setMediaItems(List<MediaItem> list, boolean z);

    public abstract /* synthetic */ void setPlayWhenReady(boolean z);

    public abstract /* synthetic */ void setPlaybackParameters(PlaybackParameters playbackParameters);

    public final void setPlaybackSpeed(float f) {
        setPlaybackParameters(getPlaybackParameters().withSpeed(f));
    }

    public abstract /* synthetic */ void setRepeatMode(int i);

    public abstract /* synthetic */ void setShuffleModeEnabled(boolean z);

    public abstract /* synthetic */ void setVideoSurface(@Nullable Surface surface);

    public abstract /* synthetic */ void setVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder);

    public abstract /* synthetic */ void setVideoSurfaceView(@Nullable SurfaceView surfaceView);

    public abstract /* synthetic */ void setVideoTextureView(@Nullable TextureView textureView);

    public abstract /* synthetic */ void setVolume(float f);

    public final void stop() {
        stop(false);
    }

    @Deprecated
    public abstract /* synthetic */ void stop(boolean z);

    public final void addMediaItem(MediaItem mediaItem) {
        addMediaItems(Collections.singletonList(mediaItem));
    }

    public final void seekToDefaultPosition(int i) {
        seekTo(i, -9223372036854775807L);
    }

    public final void setMediaItem(MediaItem mediaItem, long j) {
        setMediaItems(Collections.singletonList(mediaItem), 0, j);
    }

    public final void setMediaItem(MediaItem mediaItem, boolean z) {
        setMediaItems(Collections.singletonList(mediaItem), z);
    }
}
