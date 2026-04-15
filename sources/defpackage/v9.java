package defpackage;

import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* renamed from: v9  reason: default package */
public final class v9 {
    public final TimestampAdjuster a = new TimestampAdjuster(0);
    public final ParsableByteArray b = new ParsableByteArray();
    public boolean c;
    public boolean d;
    public boolean e;
    public long f = -9223372036854775807L;
    public long g = -9223372036854775807L;
    public long h = -9223372036854775807L;

    public static int a(byte[] bArr, int i) {
        return (bArr[i + 3] & 255) | ((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
    }

    public static long readScrValueFromPack(ParsableByteArray parsableByteArray) {
        ParsableByteArray parsableByteArray2 = parsableByteArray;
        int position = parsableByteArray.getPosition();
        if (parsableByteArray.bytesLeft() < 9) {
            return -9223372036854775807L;
        }
        byte[] bArr = new byte[9];
        boolean z = false;
        parsableByteArray2.readBytes(bArr, 0, 9);
        parsableByteArray2.setPosition(position);
        byte b2 = bArr[0];
        if ((b2 & 196) == 68 && (bArr[2] & 4) == 4 && (bArr[4] & 4) == 4 && (bArr[5] & 1) == 1 && (bArr[8] & 3) == 3) {
            z = true;
        }
        if (!z) {
            return -9223372036854775807L;
        }
        long j = (long) b2;
        long j2 = ((j & 3) << 28) | (((56 & j) >> 3) << 30) | ((((long) bArr[1]) & 255) << 20);
        long j3 = (long) bArr[2];
        return j2 | (((j3 & 248) >> 3) << 15) | ((j3 & 3) << 13) | ((((long) bArr[3]) & 255) << 5) | ((((long) bArr[4]) & 248) >> 3);
    }

    public long getDurationUs() {
        return this.h;
    }

    public TimestampAdjuster getScrTimestampAdjuster() {
        return this.a;
    }

    public boolean isDurationReadFinished() {
        return this.c;
    }

    public int readDuration(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        boolean z = this.e;
        ParsableByteArray parsableByteArray = this.b;
        long j = -9223372036854775807L;
        if (!z) {
            long length = extractorInput.getLength();
            int min = (int) Math.min(20000, length);
            long j2 = length - ((long) min);
            if (extractorInput.getPosition() != j2) {
                positionHolder.a = j2;
                return 1;
            }
            parsableByteArray.reset(min);
            extractorInput.resetPeekPosition();
            extractorInput.peekFully(parsableByteArray.getData(), 0, min);
            int position = parsableByteArray.getPosition();
            int limit = parsableByteArray.limit() - 4;
            while (true) {
                if (limit < position) {
                    break;
                }
                if (a(parsableByteArray.getData(), limit) == 442) {
                    parsableByteArray.setPosition(limit + 4);
                    long readScrValueFromPack = readScrValueFromPack(parsableByteArray);
                    if (readScrValueFromPack != -9223372036854775807L) {
                        j = readScrValueFromPack;
                        break;
                    }
                }
                limit--;
            }
            this.g = j;
            this.e = true;
            return 0;
        } else if (this.g == -9223372036854775807L) {
            parsableByteArray.reset(Util.f);
            this.c = true;
            extractorInput.resetPeekPosition();
            return 0;
        } else if (!this.d) {
            int min2 = (int) Math.min(20000, extractorInput.getLength());
            long j3 = (long) 0;
            if (extractorInput.getPosition() != j3) {
                positionHolder.a = j3;
                return 1;
            }
            parsableByteArray.reset(min2);
            extractorInput.resetPeekPosition();
            extractorInput.peekFully(parsableByteArray.getData(), 0, min2);
            int position2 = parsableByteArray.getPosition();
            int limit2 = parsableByteArray.limit();
            while (true) {
                if (position2 >= limit2 - 3) {
                    break;
                }
                if (a(parsableByteArray.getData(), position2) == 442) {
                    parsableByteArray.setPosition(position2 + 4);
                    long readScrValueFromPack2 = readScrValueFromPack(parsableByteArray);
                    if (readScrValueFromPack2 != -9223372036854775807L) {
                        j = readScrValueFromPack2;
                        break;
                    }
                }
                position2++;
            }
            this.f = j;
            this.d = true;
            return 0;
        } else {
            long j4 = this.f;
            if (j4 == -9223372036854775807L) {
                parsableByteArray.reset(Util.f);
                this.c = true;
                extractorInput.resetPeekPosition();
                return 0;
            }
            TimestampAdjuster timestampAdjuster = this.a;
            this.h = timestampAdjuster.adjustTsTimestamp(this.g) - timestampAdjuster.adjustTsTimestamp(j4);
            parsableByteArray.reset(Util.f);
            this.c = true;
            extractorInput.resetPeekPosition();
            return 0;
        }
    }
}
