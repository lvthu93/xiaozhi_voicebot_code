package com.google.android.exoplayer2.util;

import androidx.annotation.GuardedBy;

public final class TimestampAdjuster {
    @GuardedBy("this")
    public boolean a;
    @GuardedBy("this")
    public long b;
    @GuardedBy("this")
    public long c;
    @GuardedBy("this")
    public long d = -9223372036854775807L;

    public TimestampAdjuster(long j) {
        this.b = j;
    }

    public static long ptsToUs(long j) {
        return (j * 1000000) / 90000;
    }

    public static long usToNonWrappedPts(long j) {
        return (j * 90000) / 1000000;
    }

    public static long usToWrappedPts(long j) {
        return usToNonWrappedPts(j) % 8589934592L;
    }

    public synchronized long adjustSampleTimestamp(long j) {
        if (j == -9223372036854775807L) {
            return -9223372036854775807L;
        }
        if (this.d != -9223372036854775807L) {
            this.d = j;
        } else {
            long j2 = this.b;
            if (j2 != Long.MAX_VALUE) {
                this.c = j2 - j;
            }
            this.d = j;
            notifyAll();
        }
        return j + this.c;
    }

    public synchronized long adjustTsTimestamp(long j) {
        if (j == -9223372036854775807L) {
            return -9223372036854775807L;
        }
        long j2 = this.d;
        if (j2 != -9223372036854775807L) {
            long usToNonWrappedPts = usToNonWrappedPts(j2);
            long j3 = (4294967296L + usToNonWrappedPts) / 8589934592L;
            long j4 = ((j3 - 1) * 8589934592L) + j;
            long j5 = (j3 * 8589934592L) + j;
            if (Math.abs(j4 - usToNonWrappedPts) < Math.abs(j5 - usToNonWrappedPts)) {
                j = j4;
            } else {
                j = j5;
            }
        }
        return adjustSampleTimestamp(ptsToUs(j));
    }

    public synchronized long getFirstSampleTimestampUs() {
        return this.b;
    }

    public synchronized long getLastAdjustedTimestampUs() {
        long j;
        long j2 = this.d;
        j = -9223372036854775807L;
        if (j2 != -9223372036854775807L) {
            j = this.c + j2;
        } else {
            long j3 = this.b;
            if (j3 != Long.MAX_VALUE) {
                j = j3;
            }
        }
        return j;
    }

    public synchronized long getTimestampOffsetUs() {
        long j;
        if (this.b == Long.MAX_VALUE) {
            j = 0;
        } else if (this.d == -9223372036854775807L) {
            j = -9223372036854775807L;
        } else {
            j = this.c;
        }
        return j;
    }

    public synchronized void reset(long j) {
        this.b = j;
        this.d = -9223372036854775807L;
        this.a = false;
    }

    public synchronized void sharedInitializeOrWait(boolean z, long j) throws InterruptedException {
        if (z) {
            try {
                if (!this.a) {
                    this.b = j;
                    this.a = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (!z || j != this.b) {
            while (this.d == -9223372036854775807L) {
                wait();
            }
        }
    }
}
