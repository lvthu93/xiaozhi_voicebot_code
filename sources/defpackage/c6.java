package defpackage;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.util.Util;

/* renamed from: c6  reason: default package */
public final class c6 {
    public final MediaSource.MediaPeriodId a;
    public final long b;
    public final long c;
    public final long d;
    public final long e;
    public final boolean f;
    public final boolean g;
    public final boolean h;

    public c6(MediaSource.MediaPeriodId mediaPeriodId, long j, long j2, long j3, long j4, boolean z, boolean z2, boolean z3) {
        this.a = mediaPeriodId;
        this.b = j;
        this.c = j2;
        this.d = j3;
        this.e = j4;
        this.f = z;
        this.g = z2;
        this.h = z3;
    }

    public c6 copyWithRequestedContentPositionUs(long j) {
        if (j == this.c) {
            return this;
        }
        return new c6(this.a, this.b, j, this.d, this.e, this.f, this.g, this.h);
    }

    public c6 copyWithStartPositionUs(long j) {
        if (j == this.b) {
            return this;
        }
        return new c6(this.a, j, this.c, this.d, this.e, this.f, this.g, this.h);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || c6.class != obj.getClass()) {
            return false;
        }
        c6 c6Var = (c6) obj;
        if (this.b == c6Var.b && this.c == c6Var.c && this.d == c6Var.d && this.e == c6Var.e && this.f == c6Var.f && this.g == c6Var.g && this.h == c6Var.h && Util.areEqual(this.a, c6Var.a)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((((this.a.hashCode() + 527) * 31) + ((int) this.b)) * 31) + ((int) this.c)) * 31) + ((int) this.d)) * 31) + ((int) this.e)) * 31) + (this.f ? 1 : 0)) * 31) + (this.g ? 1 : 0)) * 31) + (this.h ? 1 : 0);
    }
}
