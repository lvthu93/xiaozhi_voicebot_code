package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;

public final class MediaLoadData {
    public final int a;
    public final int b;
    @Nullable
    public final Format c;
    public final int d;
    @Nullable
    public final Object e;
    public final long f;
    public final long g;

    public MediaLoadData(int i) {
        this(i, -1, (Format) null, 0, (Object) null, -9223372036854775807L, -9223372036854775807L);
    }

    public MediaLoadData(int i, int i2, @Nullable Format format, int i3, @Nullable Object obj, long j, long j2) {
        this.a = i;
        this.b = i2;
        this.c = format;
        this.d = i3;
        this.e = obj;
        this.f = j;
        this.g = j2;
    }
}
