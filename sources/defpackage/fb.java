package defpackage;

import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;

/* renamed from: fb  reason: default package */
public final class fb {
    public final ParsableByteArray a = new ParsableByteArray(8);
    public int b;

    public final long a(ExtractorInput extractorInput) throws IOException {
        ParsableByteArray parsableByteArray = this.a;
        int i = 0;
        extractorInput.peekFully(parsableByteArray.getData(), 0, 1);
        byte b2 = parsableByteArray.getData()[0] & 255;
        if (b2 == 0) {
            return Long.MIN_VALUE;
        }
        int i2 = 128;
        int i3 = 0;
        while ((b2 & i2) == 0) {
            i2 >>= 1;
            i3++;
        }
        int i4 = b2 & (~i2);
        extractorInput.peekFully(parsableByteArray.getData(), 1, i3);
        while (i < i3) {
            i++;
            i4 = (parsableByteArray.getData()[i] & 255) + (i4 << 8);
        }
        this.b = i3 + 1 + this.b;
        return (long) i4;
    }

    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        long a2;
        int i;
        ExtractorInput extractorInput2 = extractorInput;
        long length = extractorInput.getLength();
        long j = 1024;
        int i2 = (length > -1 ? 1 : (length == -1 ? 0 : -1));
        if (i2 != 0 && length <= 1024) {
            j = length;
        }
        int i3 = (int) j;
        ParsableByteArray parsableByteArray = this.a;
        extractorInput2.peekFully(parsableByteArray.getData(), 0, 4);
        this.b = 4;
        for (long readUnsignedInt = parsableByteArray.readUnsignedInt(); readUnsignedInt != 440786851; readUnsignedInt = ((readUnsignedInt << 8) & -256) | ((long) (parsableByteArray.getData()[0] & 255))) {
            int i4 = this.b + 1;
            this.b = i4;
            if (i4 == i3) {
                return false;
            }
            extractorInput2.peekFully(parsableByteArray.getData(), 0, 1);
        }
        long a3 = a(extractorInput);
        long j2 = (long) this.b;
        if (a3 == Long.MIN_VALUE) {
            return false;
        }
        if (i2 != 0 && j2 + a3 >= length) {
            return false;
        }
        while (true) {
            int i5 = this.b;
            long j3 = j2 + a3;
            if (((long) i5) < j3) {
                if (a(extractorInput) != Long.MIN_VALUE && a2 >= 0 && a2 <= 2147483647L) {
                    if (i != 0) {
                        int a4 = (int) (a2 = a(extractorInput));
                        extractorInput2.advancePeekPosition(a4);
                        this.b += a4;
                    }
                }
            } else if (((long) i5) == j3) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
