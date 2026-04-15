package defpackage;

import java.util.Arrays;

/* renamed from: b3  reason: default package */
public final class b3 {
    public a a = new a();
    public a b = new a();
    public boolean c;
    public long d = -9223372036854775807L;
    public int e;

    /* renamed from: b3$a */
    public static final class a {
        public long a;
        public long b;
        public long c;
        public long d;
        public long e;
        public long f;
        public final boolean[] g = new boolean[15];
        public int h;

        public long getFrameDurationNs() {
            long j = this.e;
            if (j == 0) {
                return 0;
            }
            return this.f / j;
        }

        public long getMatchingFrameDurationSumNs() {
            return this.f;
        }

        public boolean isLastFrameOutlier() {
            long j = this.d;
            if (j == 0) {
                return false;
            }
            return this.g[(int) ((j - 1) % 15)];
        }

        public boolean isSynced() {
            return this.d > 15 && this.h == 0;
        }

        public void onNextFrame(long j) {
            long j2 = this.d;
            if (j2 == 0) {
                this.a = j;
            } else if (j2 == 1) {
                long j3 = j - this.a;
                this.b = j3;
                this.f = j3;
                this.e = 1;
            } else {
                long j4 = j - this.c;
                int i = (int) (j2 % 15);
                long abs = Math.abs(j4 - this.b);
                boolean[] zArr = this.g;
                if (abs <= 1000000) {
                    this.e++;
                    this.f += j4;
                    if (zArr[i]) {
                        zArr[i] = false;
                        this.h--;
                    }
                } else if (!zArr[i]) {
                    zArr[i] = true;
                    this.h++;
                }
            }
            this.d++;
            this.c = j;
        }

        public void reset() {
            this.d = 0;
            this.e = 0;
            this.f = 0;
            this.h = 0;
            Arrays.fill(this.g, false);
        }
    }

    public long getFrameDurationNs() {
        if (isSynced()) {
            return this.a.getFrameDurationNs();
        }
        return -9223372036854775807L;
    }

    public float getFrameRate() {
        if (isSynced()) {
            return (float) (1.0E9d / ((double) this.a.getFrameDurationNs()));
        }
        return -1.0f;
    }

    public int getFramesWithoutSyncCount() {
        return this.e;
    }

    public long getMatchingFrameDurationSumNs() {
        if (isSynced()) {
            return this.a.getMatchingFrameDurationSumNs();
        }
        return -9223372036854775807L;
    }

    public boolean isSynced() {
        return this.a.isSynced();
    }

    public void onNextFrame(long j) {
        this.a.onNextFrame(j);
        int i = 0;
        if (this.a.isSynced()) {
            this.c = false;
        } else if (this.d != -9223372036854775807L) {
            if (!this.c || this.b.isLastFrameOutlier()) {
                this.b.reset();
                this.b.onNextFrame(this.d);
            }
            this.c = true;
            this.b.onNextFrame(j);
        }
        if (this.c && this.b.isSynced()) {
            a aVar = this.a;
            this.a = this.b;
            this.b = aVar;
            this.c = false;
        }
        this.d = j;
        if (!this.a.isSynced()) {
            i = this.e + 1;
        }
        this.e = i;
    }

    public void reset() {
        this.a.reset();
        this.b.reset();
        this.c = false;
        this.d = -9223372036854775807L;
        this.e = 0;
    }
}
