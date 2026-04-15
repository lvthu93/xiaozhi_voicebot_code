package com.google.android.exoplayer2;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MediaClock;
import java.io.IOException;

public abstract class BaseRenderer implements Renderer, RendererCapabilities {
    public final int c;
    public final FormatHolder f = new FormatHolder();
    @Nullable
    public RendererConfiguration g;
    public int h;
    public int i;
    @Nullable
    public SampleStream j;
    @Nullable
    public Format[] k;
    public long l;
    public long m = Long.MIN_VALUE;
    public boolean n;
    public boolean o;

    public BaseRenderer(int i2) {
        this.c = i2;
    }

    public final ExoPlaybackException a(Throwable th, @Nullable Format format, boolean z) {
        int i2;
        if (format != null && !this.o) {
            this.o = true;
            try {
                int d = ha.d(supportsFormat(format));
                this.o = false;
                i2 = d;
            } catch (ExoPlaybackException unused) {
                this.o = false;
            } catch (Throwable th2) {
                this.o = false;
                throw th2;
            }
            return ExoPlaybackException.createForRenderer(th, getName(), this.h, format, i2, z);
        }
        i2 = 4;
        return ExoPlaybackException.createForRenderer(th, getName(), this.h, format, i2, z);
    }

    public final boolean b() {
        return hasReadStreamToEnd() ? this.n : ((SampleStream) Assertions.checkNotNull(this.j)).isReady();
    }

    public void c() {
    }

    public void d(boolean z, boolean z2) throws ExoPlaybackException {
    }

    public final void disable() {
        boolean z = true;
        if (this.i != 1) {
            z = false;
        }
        Assertions.checkState(z);
        this.f.clear();
        this.i = 0;
        this.j = null;
        this.k = null;
        this.n = false;
        c();
    }

    public void e(long j2, boolean z) throws ExoPlaybackException {
    }

    public final void enable(RendererConfiguration rendererConfiguration, Format[] formatArr, SampleStream sampleStream, long j2, boolean z, boolean z2, long j3, long j4) throws ExoPlaybackException {
        boolean z3;
        boolean z4 = z;
        if (this.i == 0) {
            z3 = true;
        } else {
            z3 = false;
        }
        Assertions.checkState(z3);
        this.g = rendererConfiguration;
        this.i = 1;
        d(z, z2);
        replaceStream(formatArr, sampleStream, j3, j4);
        long j5 = j2;
        e(j2, z);
    }

    public void f() {
    }

    public void g() throws ExoPlaybackException {
    }

    public final RendererCapabilities getCapabilities() {
        return this;
    }

    @Nullable
    public MediaClock getMediaClock() {
        return null;
    }

    public abstract /* synthetic */ String getName();

    public final long getReadingPositionUs() {
        return this.m;
    }

    public final int getState() {
        return this.i;
    }

    @Nullable
    public final SampleStream getStream() {
        return this.j;
    }

    public final int getTrackType() {
        return this.c;
    }

    public void h() {
    }

    public void handleMessage(int i2, @Nullable Object obj) throws ExoPlaybackException {
    }

    public final boolean hasReadStreamToEnd() {
        return this.m == Long.MIN_VALUE;
    }

    public void i(Format[] formatArr, long j2, long j3) throws ExoPlaybackException {
    }

    public final boolean isCurrentStreamFinal() {
        return this.n;
    }

    public abstract /* synthetic */ boolean isEnded();

    public abstract /* synthetic */ boolean isReady();

    public final int j(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i2) {
        int readData = ((SampleStream) Assertions.checkNotNull(this.j)).readData(formatHolder, decoderInputBuffer, i2);
        if (readData == -4) {
            if (decoderInputBuffer.isEndOfStream()) {
                this.m = Long.MIN_VALUE;
                if (this.n) {
                    return -4;
                }
                return -3;
            }
            long j2 = decoderInputBuffer.i + this.l;
            decoderInputBuffer.i = j2;
            this.m = Math.max(this.m, j2);
        } else if (readData == -5) {
            Format format = (Format) Assertions.checkNotNull(formatHolder.b);
            if (format.t != Long.MAX_VALUE) {
                formatHolder.b = format.buildUpon().setSubsampleOffsetUs(format.t + this.l).build();
            }
        }
        return readData;
    }

    public final void maybeThrowStreamError() throws IOException {
        ((SampleStream) Assertions.checkNotNull(this.j)).maybeThrowError();
    }

    public abstract /* synthetic */ void render(long j2, long j3) throws ExoPlaybackException;

    public final void replaceStream(Format[] formatArr, SampleStream sampleStream, long j2, long j3) throws ExoPlaybackException {
        Assertions.checkState(!this.n);
        this.j = sampleStream;
        this.m = j3;
        this.k = formatArr;
        this.l = j3;
        i(formatArr, j2, j3);
    }

    public final void reset() {
        boolean z;
        if (this.i == 0) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        this.f.clear();
        f();
    }

    public final void resetPosition(long j2) throws ExoPlaybackException {
        this.n = false;
        this.m = j2;
        e(j2, false);
    }

    public final void setCurrentStreamFinal() {
        this.n = true;
    }

    public final void setIndex(int i2) {
        this.h = i2;
    }

    public /* bridge */ /* synthetic */ void setPlaybackSpeed(float f2, float f3) throws ExoPlaybackException {
        ga.a(this, f2, f3);
    }

    public final void start() throws ExoPlaybackException {
        boolean z = true;
        if (this.i != 1) {
            z = false;
        }
        Assertions.checkState(z);
        this.i = 2;
        g();
    }

    public final void stop() {
        boolean z;
        if (this.i == 2) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        this.i = 1;
        h();
    }

    public abstract /* synthetic */ int supportsFormat(Format format) throws ExoPlaybackException;

    public int supportsMixedMimeTypeAdaptation() throws ExoPlaybackException {
        return 0;
    }
}
