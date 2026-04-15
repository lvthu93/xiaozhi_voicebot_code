package com.google.android.exoplayer2.upstream.cache;

import androidx.annotation.Nullable;
import java.io.File;

public class CacheSpan implements Comparable<CacheSpan> {
    public final String c;
    public final long f;
    public final long g;
    public final boolean h;
    @Nullable
    public final File i;
    public final long j;

    public CacheSpan(String str, long j2, long j3) {
        this(str, j2, j3, -9223372036854775807L, (File) null);
    }

    public boolean isHoleSpan() {
        return !this.h;
    }

    public boolean isOpenEnded() {
        return this.g == -1;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(44);
        sb.append("[");
        sb.append(this.f);
        sb.append(", ");
        sb.append(this.g);
        sb.append("]");
        return sb.toString();
    }

    public CacheSpan(String str, long j2, long j3, long j4, @Nullable File file) {
        this.c = str;
        this.f = j2;
        this.g = j3;
        this.h = file != null;
        this.i = file;
        this.j = j4;
    }

    public int compareTo(CacheSpan cacheSpan) {
        String str = cacheSpan.c;
        String str2 = this.c;
        if (!str2.equals(str)) {
            return str2.compareTo(cacheSpan.c);
        }
        int i2 = ((this.f - cacheSpan.f) > 0 ? 1 : ((this.f - cacheSpan.f) == 0 ? 0 : -1));
        if (i2 == 0) {
            return 0;
        }
        return i2 < 0 ? -1 : 1;
    }
}
