package com.google.android.exoplayer2.extractor;

import androidx.annotation.Nullable;

public final class SeekPoint {
    public static final SeekPoint c = new SeekPoint(0, 0);
    public final long a;
    public final long b;

    public SeekPoint(long j, long j2) {
        this.a = j;
        this.b = j2;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || SeekPoint.class != obj.getClass()) {
            return false;
        }
        SeekPoint seekPoint = (SeekPoint) obj;
        if (this.a == seekPoint.a && this.b == seekPoint.b) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((int) this.a) * 31) + ((int) this.b);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(60);
        sb.append("[timeUs=");
        sb.append(this.a);
        sb.append(", position=");
        sb.append(this.b);
        sb.append("]");
        return sb.toString();
    }
}
