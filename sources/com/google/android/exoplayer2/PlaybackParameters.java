package com.google.android.exoplayer2;

import android.os.Bundle;
import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

public final class PlaybackParameters implements Bundleable {
    public static final PlaybackParameters h = new PlaybackParameters(1.0f);
    public final float c;
    public final float f;
    public final int g;

    public PlaybackParameters(float f2) {
        this(f2, 1.0f);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || PlaybackParameters.class != obj.getClass()) {
            return false;
        }
        PlaybackParameters playbackParameters = (PlaybackParameters) obj;
        if (this.c == playbackParameters.c && this.f == playbackParameters.f) {
            return true;
        }
        return false;
    }

    public long getMediaTimeUsForPlayoutTimeMs(long j) {
        return j * ((long) this.g);
    }

    public int hashCode() {
        return Float.floatToRawIntBits(this.f) + ((Float.floatToRawIntBits(this.c) + 527) * 31);
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putFloat(Integer.toString(0, 36), this.c);
        bundle.putFloat(Integer.toString(1, 36), this.f);
        return bundle;
    }

    public String toString() {
        return Util.formatInvariant("PlaybackParameters(speed=%.2f, pitch=%.2f)", Float.valueOf(this.c), Float.valueOf(this.f));
    }

    @CheckResult
    public PlaybackParameters withSpeed(float f2) {
        return new PlaybackParameters(f2, this.f);
    }

    public PlaybackParameters(float f2, float f3) {
        boolean z = true;
        Assertions.checkArgument(f2 > 0.0f);
        Assertions.checkArgument(f3 <= 0.0f ? false : z);
        this.c = f2;
        this.f = f3;
        this.g = Math.round(f2 * 1000.0f);
    }
}
