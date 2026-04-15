package com.google.android.exoplayer2.extractor.mp4;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Track {
    public final int a;
    public final int b;
    public final long c;
    public final long d;
    public final long e;
    public final Format f;
    public final int g;
    @Nullable
    public final long[] h;
    @Nullable
    public final long[] i;
    public final int j;
    @Nullable
    public final TrackEncryptionBox[] k;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Transformation {
    }

    public Track(int i2, int i3, long j2, long j3, long j4, Format format, int i4, @Nullable TrackEncryptionBox[] trackEncryptionBoxArr, int i5, @Nullable long[] jArr, @Nullable long[] jArr2) {
        this.a = i2;
        this.b = i3;
        this.c = j2;
        this.d = j3;
        this.e = j4;
        this.f = format;
        this.g = i4;
        this.k = trackEncryptionBoxArr;
        this.j = i5;
        this.h = jArr;
        this.i = jArr2;
    }

    public Track copyWithFormat(Format format) {
        return new Track(this.a, this.b, this.c, this.d, this.e, format, this.g, this.k, this.j, this.h, this.i);
    }

    @Nullable
    public TrackEncryptionBox getSampleDescriptionEncryptionBox(int i2) {
        TrackEncryptionBox[] trackEncryptionBoxArr = this.k;
        if (trackEncryptionBoxArr == null) {
            return null;
        }
        return trackEncryptionBoxArr[i2];
    }
}
