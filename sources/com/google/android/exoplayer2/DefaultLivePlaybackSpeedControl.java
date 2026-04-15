package com.google.android.exoplayer2;

import android.os.SystemClock;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Preconditions;

public final class DefaultLivePlaybackSpeedControl implements LivePlaybackSpeedControl {
    public final float a;
    public final float b;
    public final long c;
    public final float d;
    public final long e;
    public final long f;
    public final float g;
    public long h = -9223372036854775807L;
    public long i = -9223372036854775807L;
    public long j;
    public long k = -9223372036854775807L;
    public long l = -9223372036854775807L;
    public long m;
    public float n;
    public float o;
    public float p;
    public long q;
    public long r;
    public long s;

    public static final class Builder {
        public float a = 0.97f;
        public float b = 1.03f;
        public long c = 1000;
        public float d = 1.0E-7f;
        public long e = C.msToUs(20);
        public long f = C.msToUs(500);
        public float g = 0.999f;

        public DefaultLivePlaybackSpeedControl build() {
            return new DefaultLivePlaybackSpeedControl(this.a, this.b, this.c, this.d, this.e, this.f, this.g);
        }

        public Builder setFallbackMaxPlaybackSpeed(float f2) {
            boolean z;
            if (f2 >= 1.0f) {
                z = true;
            } else {
                z = false;
            }
            Assertions.checkArgument(z);
            this.b = f2;
            return this;
        }

        public Builder setFallbackMinPlaybackSpeed(float f2) {
            boolean z;
            if (0.0f >= f2 || f2 > 1.0f) {
                z = false;
            } else {
                z = true;
            }
            Assertions.checkArgument(z);
            this.a = f2;
            return this;
        }

        public Builder setMaxLiveOffsetErrorMsForUnitSpeed(long j) {
            boolean z;
            if (j > 0) {
                z = true;
            } else {
                z = false;
            }
            Assertions.checkArgument(z);
            this.e = C.msToUs(j);
            return this;
        }

        public Builder setMinPossibleLiveOffsetSmoothingFactor(float f2) {
            boolean z;
            if (f2 < 0.0f || f2 >= 1.0f) {
                z = false;
            } else {
                z = true;
            }
            Assertions.checkArgument(z);
            this.g = f2;
            return this;
        }

        public Builder setMinUpdateIntervalMs(long j) {
            boolean z;
            if (j > 0) {
                z = true;
            } else {
                z = false;
            }
            Assertions.checkArgument(z);
            this.c = j;
            return this;
        }

        public Builder setProportionalControlFactor(float f2) {
            boolean z;
            if (f2 > 0.0f) {
                z = true;
            } else {
                z = false;
            }
            Assertions.checkArgument(z);
            this.d = f2 / 1000000.0f;
            return this;
        }

        public Builder setTargetLiveOffsetIncrementOnRebufferMs(long j) {
            boolean z;
            if (j >= 0) {
                z = true;
            } else {
                z = false;
            }
            Assertions.checkArgument(z);
            this.f = C.msToUs(j);
            return this;
        }
    }

    public DefaultLivePlaybackSpeedControl(float f2, float f3, long j2, float f4, long j3, long j4, float f5) {
        this.a = f2;
        this.b = f3;
        this.c = j2;
        this.d = f4;
        this.e = j3;
        this.f = j4;
        this.g = f5;
        this.o = f2;
        this.n = f3;
        this.p = 1.0f;
        this.q = -9223372036854775807L;
        this.j = -9223372036854775807L;
        this.m = -9223372036854775807L;
        this.r = -9223372036854775807L;
        this.s = -9223372036854775807L;
    }

    public final void a() {
        long j2 = this.h;
        if (j2 != -9223372036854775807L) {
            long j3 = this.i;
            if (j3 != -9223372036854775807L) {
                j2 = j3;
            }
            long j4 = this.k;
            if (j4 != -9223372036854775807L && j2 < j4) {
                j2 = j4;
            }
            long j5 = this.l;
            if (j5 != -9223372036854775807L && j2 > j5) {
                j2 = j5;
            }
        } else {
            j2 = -9223372036854775807L;
        }
        if (this.j != j2) {
            this.j = j2;
            this.m = j2;
            this.r = -9223372036854775807L;
            this.s = -9223372036854775807L;
            this.q = -9223372036854775807L;
        }
    }

    public float getAdjustedPlaybackSpeed(long j2, long j3) {
        if (this.h == -9223372036854775807L) {
            return 1.0f;
        }
        long j4 = j2 - j3;
        long j5 = this.r;
        if (j5 == -9223372036854775807L) {
            this.r = j4;
            this.s = 0;
        } else {
            float f2 = this.g;
            float f3 = 1.0f - f2;
            long max = Math.max(j4, (long) ((((float) j4) * f3) + (((float) j5) * f2)));
            this.r = max;
            this.s = (long) ((f3 * ((float) Math.abs(j4 - max))) + (((float) this.s) * f2));
        }
        long j6 = this.q;
        long j7 = this.c;
        if (j6 != -9223372036854775807L && SystemClock.elapsedRealtime() - this.q < j7) {
            return this.p;
        }
        this.q = SystemClock.elapsedRealtime();
        long j8 = (this.s * 3) + this.r;
        long j9 = this.m;
        float f4 = this.d;
        if (j9 > j8) {
            float msToUs = (float) C.msToUs(j7);
            long[] jArr = {j8, this.j, this.m - (((long) ((this.p - 1.0f) * msToUs)) + ((long) ((this.n - 1.0f) * msToUs)))};
            Preconditions.checkArgument(true);
            long j10 = jArr[0];
            for (int i2 = 1; i2 < 3; i2++) {
                long j11 = jArr[i2];
                if (j11 > j10) {
                    j10 = j11;
                }
            }
            this.m = j10;
        } else {
            long constrainValue = Util.constrainValue(j2 - ((long) (Math.max(0.0f, this.p - 1.0f) / f4)), this.m, j8);
            this.m = constrainValue;
            long j12 = this.l;
            if (j12 != -9223372036854775807L && constrainValue > j12) {
                this.m = j12;
            }
        }
        long j13 = j2 - this.m;
        if (Math.abs(j13) < this.e) {
            this.p = 1.0f;
        } else {
            this.p = Util.constrainValue((f4 * ((float) j13)) + 1.0f, this.o, this.n);
        }
        return this.p;
    }

    public long getTargetLiveOffsetUs() {
        return this.m;
    }

    public void notifyRebuffer() {
        long j2 = this.m;
        if (j2 != -9223372036854775807L) {
            long j3 = j2 + this.f;
            this.m = j3;
            long j4 = this.l;
            if (j4 != -9223372036854775807L && j3 > j4) {
                this.m = j4;
            }
            this.q = -9223372036854775807L;
        }
    }

    public void setLiveConfiguration(MediaItem.LiveConfiguration liveConfiguration) {
        this.h = C.msToUs(liveConfiguration.c);
        this.k = C.msToUs(liveConfiguration.f);
        this.l = C.msToUs(liveConfiguration.g);
        float f2 = liveConfiguration.h;
        if (f2 == -3.4028235E38f) {
            f2 = this.a;
        }
        this.o = f2;
        float f3 = liveConfiguration.i;
        if (f3 == -3.4028235E38f) {
            f3 = this.b;
        }
        this.n = f3;
        a();
    }

    public void setTargetLiveOffsetOverrideUs(long j2) {
        this.i = j2;
        a();
    }
}
