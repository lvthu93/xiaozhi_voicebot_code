package com.google.android.exoplayer2;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.MediaClock;
import com.google.android.exoplayer2.util.StandaloneMediaClock;

public final class DefaultMediaClock implements MediaClock {
    public final StandaloneMediaClock c;
    public final PlaybackParametersListener f;
    @Nullable
    public Renderer g;
    @Nullable
    public MediaClock h;
    public boolean i = true;
    public boolean j;

    public interface PlaybackParametersListener {
        void onPlaybackParametersChanged(PlaybackParameters playbackParameters);
    }

    public DefaultMediaClock(PlaybackParametersListener playbackParametersListener, Clock clock) {
        this.f = playbackParametersListener;
        this.c = new StandaloneMediaClock(clock);
    }

    public PlaybackParameters getPlaybackParameters() {
        MediaClock mediaClock = this.h;
        if (mediaClock != null) {
            return mediaClock.getPlaybackParameters();
        }
        return this.c.getPlaybackParameters();
    }

    public long getPositionUs() {
        if (this.i) {
            return this.c.getPositionUs();
        }
        return ((MediaClock) Assertions.checkNotNull(this.h)).getPositionUs();
    }

    public void onRendererDisabled(Renderer renderer) {
        if (renderer == this.g) {
            this.h = null;
            this.g = null;
            this.i = true;
        }
    }

    public void onRendererEnabled(Renderer renderer) throws ExoPlaybackException {
        MediaClock mediaClock;
        MediaClock mediaClock2 = renderer.getMediaClock();
        if (mediaClock2 != null && mediaClock2 != (mediaClock = this.h)) {
            if (mediaClock == null) {
                this.h = mediaClock2;
                this.g = renderer;
                mediaClock2.setPlaybackParameters(this.c.getPlaybackParameters());
                return;
            }
            throw ExoPlaybackException.createForUnexpected(new IllegalStateException("Multiple renderer media clocks enabled."));
        }
    }

    public void resetPosition(long j2) {
        this.c.resetPosition(j2);
    }

    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        MediaClock mediaClock = this.h;
        if (mediaClock != null) {
            mediaClock.setPlaybackParameters(playbackParameters);
            playbackParameters = this.h.getPlaybackParameters();
        }
        this.c.setPlaybackParameters(playbackParameters);
    }

    public void start() {
        this.j = true;
        this.c.start();
    }

    public void stop() {
        this.j = false;
        this.c.stop();
    }

    public long syncAndGetPositionUs(boolean z) {
        boolean z2;
        Renderer renderer = this.g;
        if (renderer == null || renderer.isEnded() || (!this.g.isReady() && (z || this.g.hasReadStreamToEnd()))) {
            z2 = true;
        } else {
            z2 = false;
        }
        StandaloneMediaClock standaloneMediaClock = this.c;
        if (z2) {
            this.i = true;
            if (this.j) {
                standaloneMediaClock.start();
            }
        } else {
            MediaClock mediaClock = (MediaClock) Assertions.checkNotNull(this.h);
            long positionUs = mediaClock.getPositionUs();
            if (this.i) {
                if (positionUs < standaloneMediaClock.getPositionUs()) {
                    standaloneMediaClock.stop();
                } else {
                    this.i = false;
                    if (this.j) {
                        standaloneMediaClock.start();
                    }
                }
            }
            standaloneMediaClock.resetPosition(positionUs);
            PlaybackParameters playbackParameters = mediaClock.getPlaybackParameters();
            if (!playbackParameters.equals(standaloneMediaClock.getPlaybackParameters())) {
                standaloneMediaClock.setPlaybackParameters(playbackParameters);
                this.f.onPlaybackParametersChanged(playbackParameters);
            }
        }
        return getPositionUs();
    }
}
