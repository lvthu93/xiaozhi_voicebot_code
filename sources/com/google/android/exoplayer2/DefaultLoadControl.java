package com.google.android.exoplayer2;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

public class DefaultLoadControl implements LoadControl {
    public final DefaultAllocator a;
    public final long b;
    public final long c;
    public final long d;
    public final long e;
    public final int f;
    public final boolean g;
    public final long h;
    public final boolean i;
    public int j;
    public boolean k;

    public static final class Builder {
        @Nullable
        public DefaultAllocator a;
        public int b = 50000;
        public int c = 50000;
        public int d = 2500;
        public int e = 5000;
        public int f = -1;
        public boolean g = false;
        public int h = 0;
        public boolean i = false;
        public boolean j;

        public DefaultLoadControl build() {
            Assertions.checkState(!this.j);
            this.j = true;
            if (this.a == null) {
                this.a = new DefaultAllocator(true, 65536);
            }
            return new DefaultLoadControl(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i);
        }

        @Deprecated
        public DefaultLoadControl createDefaultLoadControl() {
            return build();
        }

        public Builder setAllocator(DefaultAllocator defaultAllocator) {
            Assertions.checkState(!this.j);
            this.a = defaultAllocator;
            return this;
        }

        public Builder setBackBuffer(int i2, boolean z) {
            Assertions.checkState(!this.j);
            DefaultLoadControl.a("backBufferDurationMs", i2, 0, "0");
            this.h = i2;
            this.i = z;
            return this;
        }

        public Builder setBufferDurationsMs(int i2, int i3, int i4, int i5) {
            Assertions.checkState(!this.j);
            DefaultLoadControl.a("bufferForPlaybackMs", i4, 0, "0");
            DefaultLoadControl.a("bufferForPlaybackAfterRebufferMs", i5, 0, "0");
            DefaultLoadControl.a("minBufferMs", i2, i4, "bufferForPlaybackMs");
            DefaultLoadControl.a("minBufferMs", i2, i5, "bufferForPlaybackAfterRebufferMs");
            DefaultLoadControl.a("maxBufferMs", i3, i2, "minBufferMs");
            this.b = i2;
            this.c = i3;
            this.d = i4;
            this.e = i5;
            return this;
        }

        public Builder setPrioritizeTimeOverSizeThresholds(boolean z) {
            Assertions.checkState(!this.j);
            this.g = z;
            return this;
        }

        public Builder setTargetBufferBytes(int i2) {
            Assertions.checkState(!this.j);
            this.f = i2;
            return this;
        }
    }

    public DefaultLoadControl() {
        this(new DefaultAllocator(true, 65536), 50000, 50000, 2500, 5000, -1, false, 0, false);
    }

    public static void a(String str, int i2, int i3, String str2) {
        boolean z = i2 >= i3;
        StringBuilder sb = new StringBuilder(str2.length() + str.length() + 21);
        sb.append(str);
        sb.append(" cannot be less than ");
        sb.append(str2);
        Assertions.checkArgument(z, sb.toString());
    }

    public final void b(boolean z) {
        int i2 = this.f;
        if (i2 == -1) {
            i2 = 13107200;
        }
        this.j = i2;
        this.k = false;
        if (z) {
            this.a.reset();
        }
    }

    public Allocator getAllocator() {
        return this.a;
    }

    public long getBackBufferDurationUs() {
        return this.h;
    }

    public void onPrepared() {
        b(false);
    }

    public void onReleased() {
        b(true);
    }

    public void onStopped() {
        b(true);
    }

    public void onTracksSelected(Renderer[] rendererArr, TrackGroupArray trackGroupArray, ExoTrackSelection[] exoTrackSelectionArr) {
        int i2 = this.f;
        if (i2 == -1) {
            int i3 = 0;
            int i4 = 0;
            while (true) {
                int i5 = 13107200;
                if (i3 >= rendererArr.length) {
                    i2 = Math.max(13107200, i4);
                    break;
                }
                if (exoTrackSelectionArr[i3] != null) {
                    int trackType = rendererArr[i3].getTrackType();
                    if (trackType == 0) {
                        i5 = 144310272;
                    } else if (trackType != 1) {
                        if (trackType == 2) {
                            i5 = 131072000;
                        } else if (trackType == 3 || trackType == 5 || trackType == 6) {
                            i5 = 131072;
                        } else if (trackType == 7) {
                            i5 = 0;
                        } else {
                            throw new IllegalArgumentException();
                        }
                    }
                    i4 += i5;
                }
                i3++;
            }
        }
        this.j = i2;
        this.a.setTargetBufferSize(i2);
    }

    public boolean retainBackBufferFromKeyframe() {
        return this.i;
    }

    public boolean shouldContinueLoading(long j2, long j3, float f2) {
        boolean z;
        boolean z2 = true;
        if (this.a.getTotalBytesAllocated() >= this.j) {
            z = true;
        } else {
            z = false;
        }
        long j4 = this.c;
        long j5 = this.b;
        if (f2 > 1.0f) {
            j5 = Math.min(Util.getMediaDurationForPlayoutDuration(j5, f2), j4);
        }
        if (j3 < Math.max(j5, 500000)) {
            if (!this.g && z) {
                z2 = false;
            }
            this.k = z2;
            if (!z2 && j3 < 500000) {
                Log.w("DefaultLoadControl", "Target buffer size reached with less than 500ms of buffered media data.");
            }
        } else if (j3 >= j4 || z) {
            this.k = false;
        }
        return this.k;
    }

    public boolean shouldStartPlayback(long j2, float f2, boolean z, long j3) {
        long j4;
        long playoutDurationForMediaDuration = Util.getPlayoutDurationForMediaDuration(j2, f2);
        if (z) {
            j4 = this.e;
        } else {
            j4 = this.d;
        }
        if (j3 != -9223372036854775807L) {
            j4 = Math.min(j3 / 2, j4);
        }
        if (j4 <= 0 || playoutDurationForMediaDuration >= j4 || (!this.g && this.a.getTotalBytesAllocated() >= this.j)) {
            return true;
        }
        return false;
    }

    public DefaultLoadControl(DefaultAllocator defaultAllocator, int i2, int i3, int i4, int i5, int i6, boolean z, int i7, boolean z2) {
        a("bufferForPlaybackMs", i4, 0, "0");
        a("bufferForPlaybackAfterRebufferMs", i5, 0, "0");
        a("minBufferMs", i2, i4, "bufferForPlaybackMs");
        a("minBufferMs", i2, i5, "bufferForPlaybackAfterRebufferMs");
        a("maxBufferMs", i3, i2, "minBufferMs");
        a("backBufferDurationMs", i7, 0, "0");
        this.a = defaultAllocator;
        this.b = C.msToUs((long) i2);
        this.c = C.msToUs((long) i3);
        this.d = C.msToUs((long) i4);
        this.e = C.msToUs((long) i5);
        this.f = i6;
        this.j = i6 == -1 ? 13107200 : i6;
        this.g = z;
        this.h = C.msToUs((long) i7);
        this.i = z2;
    }
}
