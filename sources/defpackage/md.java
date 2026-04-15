package defpackage;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.audio.MpegAudioUtil;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.extractor.mp3.Seeker;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;

/* renamed from: md  reason: default package */
public final class md implements Seeker {
    public final long[] a;
    public final long[] b;
    public final long c;
    public final long d;

    public md(long[] jArr, long[] jArr2, long j, long j2) {
        this.a = jArr;
        this.b = jArr2;
        this.c = j;
        this.d = j2;
    }

    @Nullable
    public static md create(long j, long j2, MpegAudioUtil.Header header, ParsableByteArray parsableByteArray) {
        int i;
        int i2;
        long j3 = j;
        MpegAudioUtil.Header header2 = header;
        ParsableByteArray parsableByteArray2 = parsableByteArray;
        parsableByteArray2.skipBytes(10);
        int readInt = parsableByteArray.readInt();
        if (readInt <= 0) {
            return null;
        }
        int i3 = header2.d;
        long j4 = (long) readInt;
        if (i3 >= 32000) {
            i = 1152;
        } else {
            i = 576;
        }
        long scaleLargeTimestamp = Util.scaleLargeTimestamp(j4, ((long) i) * 1000000, (long) i3);
        int readUnsignedShort = parsableByteArray.readUnsignedShort();
        int readUnsignedShort2 = parsableByteArray.readUnsignedShort();
        int readUnsignedShort3 = parsableByteArray.readUnsignedShort();
        parsableByteArray2.skipBytes(2);
        long j5 = j2 + ((long) header2.c);
        long[] jArr = new long[readUnsignedShort];
        long[] jArr2 = new long[readUnsignedShort];
        int i4 = 0;
        long j6 = j2;
        while (i4 < readUnsignedShort) {
            int i5 = readUnsignedShort2;
            jArr[i4] = (((long) i4) * scaleLargeTimestamp) / ((long) readUnsignedShort);
            long j7 = j5;
            jArr2[i4] = Math.max(j6, j7);
            if (readUnsignedShort3 == 1) {
                i2 = parsableByteArray.readUnsignedByte();
            } else if (readUnsignedShort3 == 2) {
                i2 = parsableByteArray.readUnsignedShort();
            } else if (readUnsignedShort3 == 3) {
                i2 = parsableByteArray.readUnsignedInt24();
            } else if (readUnsignedShort3 != 4) {
                return null;
            } else {
                i2 = parsableByteArray.readUnsignedIntToInt();
            }
            j6 += (long) (i2 * i5);
            i4++;
            j5 = j7;
            readUnsignedShort2 = i5;
        }
        if (!(j3 == -1 || j3 == j6)) {
            StringBuilder sb = new StringBuilder(67);
            sb.append("VBRI data size mismatch: ");
            sb.append(j3);
            sb.append(", ");
            sb.append(j6);
            Log.w("VbriSeeker", sb.toString());
        }
        return new md(jArr, jArr2, scaleLargeTimestamp, j6);
    }

    public long getDataEndPosition() {
        return this.d;
    }

    public long getDurationUs() {
        return this.c;
    }

    public SeekMap.SeekPoints getSeekPoints(long j) {
        long[] jArr = this.a;
        int binarySearchFloor = Util.binarySearchFloor(jArr, j, true, true);
        long j2 = jArr[binarySearchFloor];
        long[] jArr2 = this.b;
        SeekPoint seekPoint = new SeekPoint(j2, jArr2[binarySearchFloor]);
        if (seekPoint.a >= j || binarySearchFloor == jArr.length - 1) {
            return new SeekMap.SeekPoints(seekPoint);
        }
        int i = binarySearchFloor + 1;
        return new SeekMap.SeekPoints(seekPoint, new SeekPoint(jArr[i], jArr2[i]));
    }

    public long getTimeUs(long j) {
        return this.a[Util.binarySearchFloor(this.b, j, true, true)];
    }

    public boolean isSeekable() {
        return true;
    }
}
