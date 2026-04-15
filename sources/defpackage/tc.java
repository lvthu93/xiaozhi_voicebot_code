package defpackage;

import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.ts.TsUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* renamed from: tc  reason: default package */
public final class tc {
    public final int a;
    public final TimestampAdjuster b = new TimestampAdjuster(0);
    public final ParsableByteArray c = new ParsableByteArray();
    public boolean d;
    public boolean e;
    public boolean f;
    public long g = -9223372036854775807L;
    public long h = -9223372036854775807L;
    public long i = -9223372036854775807L;

    public tc(int i2) {
        this.a = i2;
    }

    public final void a(ExtractorInput extractorInput) {
        this.c.reset(Util.f);
        this.d = true;
        extractorInput.resetPeekPosition();
    }

    public long getDurationUs() {
        return this.i;
    }

    public TimestampAdjuster getPcrTimestampAdjuster() {
        return this.b;
    }

    public boolean isDurationReadFinished() {
        return this.d;
    }

    public int readDuration(ExtractorInput extractorInput, PositionHolder positionHolder, int i2) throws IOException {
        if (i2 <= 0) {
            a(extractorInput);
            return 0;
        }
        boolean z = this.f;
        ParsableByteArray parsableByteArray = this.c;
        int i3 = this.a;
        long j = -9223372036854775807L;
        if (!z) {
            long length = extractorInput.getLength();
            int min = (int) Math.min((long) i3, length);
            long j2 = length - ((long) min);
            if (extractorInput.getPosition() != j2) {
                positionHolder.a = j2;
                return 1;
            }
            parsableByteArray.reset(min);
            extractorInput.resetPeekPosition();
            extractorInput.peekFully(parsableByteArray.getData(), 0, min);
            int position = parsableByteArray.getPosition();
            int limit = parsableByteArray.limit();
            int i4 = limit - 188;
            while (true) {
                if (i4 < position) {
                    break;
                }
                if (TsUtil.isStartOfTsPacket(parsableByteArray.getData(), position, limit, i4)) {
                    long readPcrFromPacket = TsUtil.readPcrFromPacket(parsableByteArray, i4, i2);
                    if (readPcrFromPacket != -9223372036854775807L) {
                        j = readPcrFromPacket;
                        break;
                    }
                }
                i4--;
            }
            this.h = j;
            this.f = true;
            return 0;
        } else if (this.h == -9223372036854775807L) {
            a(extractorInput);
            return 0;
        } else if (!this.e) {
            int min2 = (int) Math.min((long) i3, extractorInput.getLength());
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
                if (position2 >= limit2) {
                    break;
                }
                if (parsableByteArray.getData()[position2] == 71) {
                    long readPcrFromPacket2 = TsUtil.readPcrFromPacket(parsableByteArray, position2, i2);
                    if (readPcrFromPacket2 != -9223372036854775807L) {
                        j = readPcrFromPacket2;
                        break;
                    }
                }
                position2++;
            }
            this.g = j;
            this.e = true;
            return 0;
        } else {
            long j4 = this.g;
            if (j4 == -9223372036854775807L) {
                a(extractorInput);
                return 0;
            }
            TimestampAdjuster timestampAdjuster = this.b;
            this.i = timestampAdjuster.adjustTsTimestamp(this.h) - timestampAdjuster.adjustTsTimestamp(j4);
            a(extractorInput);
            return 0;
        }
    }
}
