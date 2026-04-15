package com.google.android.exoplayer2;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MediaClock;
import java.io.IOException;

public abstract class NoSampleRenderer implements Renderer, RendererCapabilities {
    public int c;
    @Nullable
    public SampleStream f;
    public boolean g;

    public final void disable() {
        boolean z = true;
        if (this.c != 1) {
            z = false;
        }
        Assertions.checkState(z);
        this.c = 0;
        this.f = null;
        this.g = false;
    }

    public final void enable(RendererConfiguration rendererConfiguration, Format[] formatArr, SampleStream sampleStream, long j, boolean z, boolean z2, long j2, long j3) throws ExoPlaybackException {
        boolean z3;
        if (this.c == 0) {
            z3 = true;
        } else {
            z3 = false;
        }
        Assertions.checkState(z3);
        this.c = 1;
        replaceStream(formatArr, sampleStream, j2, j3);
    }

    public final RendererCapabilities getCapabilities() {
        return this;
    }

    @Nullable
    public MediaClock getMediaClock() {
        return null;
    }

    public abstract /* synthetic */ String getName();

    public long getReadingPositionUs() {
        return Long.MIN_VALUE;
    }

    public final int getState() {
        return this.c;
    }

    @Nullable
    public final SampleStream getStream() {
        return this.f;
    }

    public final int getTrackType() {
        return 7;
    }

    public void handleMessage(int i, @Nullable Object obj) throws ExoPlaybackException {
    }

    public final boolean hasReadStreamToEnd() {
        return true;
    }

    public final boolean isCurrentStreamFinal() {
        return this.g;
    }

    public boolean isEnded() {
        return true;
    }

    public boolean isReady() {
        return true;
    }

    public final void maybeThrowStreamError() throws IOException {
    }

    public abstract /* synthetic */ void render(long j, long j2) throws ExoPlaybackException;

    public final void replaceStream(Format[] formatArr, SampleStream sampleStream, long j, long j2) throws ExoPlaybackException {
        Assertions.checkState(!this.g);
        this.f = sampleStream;
    }

    public final void reset() {
        Assertions.checkState(this.c == 0);
    }

    public final void resetPosition(long j) throws ExoPlaybackException {
        this.g = false;
    }

    public final void setCurrentStreamFinal() {
        this.g = true;
    }

    public final void setIndex(int i) {
    }

    public /* bridge */ /* synthetic */ void setPlaybackSpeed(float f2, float f3) throws ExoPlaybackException {
        ga.a(this, f2, f3);
    }

    public final void start() throws ExoPlaybackException {
        boolean z = true;
        if (this.c != 1) {
            z = false;
        }
        Assertions.checkState(z);
        this.c = 2;
    }

    public final void stop() {
        boolean z;
        if (this.c == 2) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        this.c = 1;
    }

    public int supportsFormat(Format format) throws ExoPlaybackException {
        return ha.a(0);
    }

    public int supportsMixedMimeTypeAdaptation() throws ExoPlaybackException {
        return 0;
    }
}
