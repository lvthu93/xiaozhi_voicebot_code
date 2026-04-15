package com.google.android.exoplayer2.util;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.PlaybackParameters;

public final class StandaloneMediaClock implements MediaClock {
    public final Clock c;
    public boolean f;
    public long g;
    public long h;
    public PlaybackParameters i = PlaybackParameters.h;

    public StandaloneMediaClock(Clock clock) {
        this.c = clock;
    }

    public PlaybackParameters getPlaybackParameters() {
        return this.i;
    }

    public long getPositionUs() {
        long j;
        long j2 = this.g;
        if (!this.f) {
            return j2;
        }
        long elapsedRealtime = this.c.elapsedRealtime() - this.h;
        PlaybackParameters playbackParameters = this.i;
        if (playbackParameters.c == 1.0f) {
            j = C.msToUs(elapsedRealtime);
        } else {
            j = playbackParameters.getMediaTimeUsForPlayoutTimeMs(elapsedRealtime);
        }
        return j2 + j;
    }

    public void resetPosition(long j) {
        this.g = j;
        if (this.f) {
            this.h = this.c.elapsedRealtime();
        }
    }

    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        if (this.f) {
            resetPosition(getPositionUs());
        }
        this.i = playbackParameters;
    }

    public void start() {
        if (!this.f) {
            this.h = this.c.elapsedRealtime();
            this.f = true;
        }
    }

    public void stop() {
        if (this.f) {
            resetPosition(getPositionUs());
            this.f = false;
        }
    }
}
