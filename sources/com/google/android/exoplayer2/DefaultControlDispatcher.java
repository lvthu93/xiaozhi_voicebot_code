package com.google.android.exoplayer2;

import com.google.android.exoplayer2.Timeline;

public class DefaultControlDispatcher implements ControlDispatcher {
    public final Timeline.Window a;
    public long b;
    public long c;

    public DefaultControlDispatcher() {
        this(15000, 5000);
    }

    public static void a(Player player, long j) {
        long currentPosition = player.getCurrentPosition() + j;
        long duration = player.getDuration();
        if (duration != -9223372036854775807L) {
            currentPosition = Math.min(currentPosition, duration);
        }
        player.seekTo(player.getCurrentWindowIndex(), Math.max(currentPosition, 0));
    }

    public boolean dispatchFastForward(Player player) {
        if (!isFastForwardEnabled() || !player.isCurrentWindowSeekable()) {
            return true;
        }
        a(player, this.c);
        return true;
    }

    public boolean dispatchNext(Player player) {
        Timeline currentTimeline = player.getCurrentTimeline();
        if (!currentTimeline.isEmpty() && !player.isPlayingAd()) {
            int currentWindowIndex = player.getCurrentWindowIndex();
            Timeline.Window window = this.a;
            currentTimeline.getWindow(currentWindowIndex, window);
            int nextWindowIndex = player.getNextWindowIndex();
            if (nextWindowIndex != -1) {
                player.seekTo(nextWindowIndex, -9223372036854775807L);
            } else if (window.isLive() && window.m) {
                player.seekTo(currentWindowIndex, -9223372036854775807L);
            }
        }
        return true;
    }

    public boolean dispatchPrepare(Player player) {
        player.prepare();
        return true;
    }

    public boolean dispatchPrevious(Player player) {
        boolean z;
        Timeline currentTimeline = player.getCurrentTimeline();
        if (!currentTimeline.isEmpty() && !player.isPlayingAd()) {
            int currentWindowIndex = player.getCurrentWindowIndex();
            Timeline.Window window = this.a;
            currentTimeline.getWindow(currentWindowIndex, window);
            int previousWindowIndex = player.getPreviousWindowIndex();
            if (!window.isLive() || window.l) {
                z = false;
            } else {
                z = true;
            }
            if (previousWindowIndex != -1 && (player.getCurrentPosition() <= 3000 || z)) {
                player.seekTo(previousWindowIndex, -9223372036854775807L);
            } else if (!z) {
                player.seekTo(currentWindowIndex, 0);
            }
        }
        return true;
    }

    public boolean dispatchRewind(Player player) {
        if (!isRewindEnabled() || !player.isCurrentWindowSeekable()) {
            return true;
        }
        a(player, -this.b);
        return true;
    }

    public boolean dispatchSeekTo(Player player, int i, long j) {
        player.seekTo(i, j);
        return true;
    }

    public boolean dispatchSetPlayWhenReady(Player player, boolean z) {
        player.setPlayWhenReady(z);
        return true;
    }

    public boolean dispatchSetPlaybackParameters(Player player, PlaybackParameters playbackParameters) {
        player.setPlaybackParameters(playbackParameters);
        return true;
    }

    public boolean dispatchSetRepeatMode(Player player, int i) {
        player.setRepeatMode(i);
        return true;
    }

    public boolean dispatchSetShuffleModeEnabled(Player player, boolean z) {
        player.setShuffleModeEnabled(z);
        return true;
    }

    public boolean dispatchStop(Player player, boolean z) {
        player.stop(z);
        return true;
    }

    public long getFastForwardIncrementMs() {
        return this.c;
    }

    public long getRewindIncrementMs() {
        return this.b;
    }

    public boolean isFastForwardEnabled() {
        return this.c > 0;
    }

    public boolean isRewindEnabled() {
        return this.b > 0;
    }

    @Deprecated
    public void setFastForwardIncrementMs(long j) {
        this.c = j;
    }

    @Deprecated
    public void setRewindIncrementMs(long j) {
        this.b = j;
    }

    public DefaultControlDispatcher(long j, long j2) {
        this.c = j;
        this.b = j2;
        this.a = new Timeline.Window();
    }
}
