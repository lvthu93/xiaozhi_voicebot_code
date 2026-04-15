package defpackage;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.audio.MpegAudioUtil;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.extractor.mp3.Seeker;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;

/* renamed from: kf  reason: default package */
public final class kf implements Seeker {
    public final long a;
    public final int b;
    public final long c;
    public final long d;
    public final long e;
    @Nullable
    public final long[] f;

    public kf(long j, int i, long j2, long j3, @Nullable long[] jArr) {
        this.a = j;
        this.b = i;
        this.c = j2;
        this.f = jArr;
        this.d = j3;
        this.e = j3 != -1 ? j + j3 : -1;
    }

    @Nullable
    public static kf create(long j, long j2, MpegAudioUtil.Header header, ParsableByteArray parsableByteArray) {
        int readUnsignedIntToInt;
        long j3 = j;
        MpegAudioUtil.Header header2 = header;
        int i = header2.g;
        int i2 = header2.d;
        int readInt = parsableByteArray.readInt();
        if ((readInt & 1) != 1 || (readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt()) == 0) {
            return null;
        }
        long scaleLargeTimestamp = Util.scaleLargeTimestamp((long) readUnsignedIntToInt, ((long) i) * 1000000, (long) i2);
        if ((readInt & 6) != 6) {
            return new kf(j2, header2.c, scaleLargeTimestamp, -1, (long[]) null);
        }
        long readUnsignedInt = parsableByteArray.readUnsignedInt();
        long[] jArr = new long[100];
        for (int i3 = 0; i3 < 100; i3++) {
            jArr[i3] = (long) parsableByteArray.readUnsignedByte();
        }
        if (j3 != -1) {
            long j4 = j2 + readUnsignedInt;
            if (j3 != j4) {
                StringBuilder sb = new StringBuilder(67);
                sb.append("XING data size mismatch: ");
                sb.append(j3);
                sb.append(", ");
                sb.append(j4);
                Log.w("XingSeeker", sb.toString());
            }
        }
        return new kf(j2, header2.c, scaleLargeTimestamp, readUnsignedInt, jArr);
    }

    public long getDataEndPosition() {
        return this.e;
    }

    public long getDurationUs() {
        return this.c;
    }

    public SeekMap.SeekPoints getSeekPoints(long j) {
        double d2;
        double d3;
        boolean isSeekable = isSeekable();
        int i = this.b;
        long j2 = this.a;
        if (!isSeekable) {
            return new SeekMap.SeekPoints(new SeekPoint(0, j2 + ((long) i)));
        }
        long constrainValue = Util.constrainValue(j, 0, this.c);
        double d4 = (((double) constrainValue) * 100.0d) / ((double) this.c);
        double d5 = 0.0d;
        if (d4 > 0.0d) {
            if (d4 >= 100.0d) {
                d2 = 256.0d;
                d5 = 256.0d;
                double d6 = d5 / d2;
                long j3 = this.d;
                return new SeekMap.SeekPoints(new SeekPoint(constrainValue, j2 + Util.constrainValue(Math.round(d6 * ((double) j3)), (long) i, j3 - 1)));
            }
            int i2 = (int) d4;
            long[] jArr = (long[]) Assertions.checkStateNotNull(this.f);
            double d7 = (double) jArr[i2];
            if (i2 == 99) {
                d3 = 256.0d;
            } else {
                d3 = (double) jArr[i2 + 1];
            }
            d5 = ((d3 - d7) * (d4 - ((double) i2))) + d7;
        }
        d2 = 256.0d;
        double d62 = d5 / d2;
        long j32 = this.d;
        return new SeekMap.SeekPoints(new SeekPoint(constrainValue, j2 + Util.constrainValue(Math.round(d62 * ((double) j32)), (long) i, j32 - 1)));
    }

    public long getTimeUs(long j) {
        long j2;
        double d2;
        long j3 = j - this.a;
        if (!isSeekable() || j3 <= ((long) this.b)) {
            return 0;
        }
        long[] jArr = (long[]) Assertions.checkStateNotNull(this.f);
        double d3 = (((double) j3) * 256.0d) / ((double) this.d);
        int binarySearchFloor = Util.binarySearchFloor(jArr, (long) d3, true, true);
        long j4 = this.c;
        long j5 = (((long) binarySearchFloor) * j4) / 100;
        long j6 = jArr[binarySearchFloor];
        int i = binarySearchFloor + 1;
        long j7 = (j4 * ((long) i)) / 100;
        if (binarySearchFloor == 99) {
            j2 = 256;
        } else {
            j2 = jArr[i];
        }
        if (j6 == j2) {
            d2 = 0.0d;
        } else {
            d2 = (d3 - ((double) j6)) / ((double) (j2 - j6));
        }
        return Math.round(d2 * ((double) (j7 - j5))) + j5;
    }

    public boolean isSeekable() {
        return this.f != null;
    }
}
