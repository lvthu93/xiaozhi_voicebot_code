package defpackage;

import android.util.Pair;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* renamed from: ge  reason: default package */
public final class ge {

    /* renamed from: ge$a */
    public static final class a {
        public final int a;
        public final long b;

        public a(int i, long j) {
            this.a = i;
            this.b = j;
        }

        public static a peek(ExtractorInput extractorInput, ParsableByteArray parsableByteArray) throws IOException {
            extractorInput.peekFully(parsableByteArray.getData(), 0, 8);
            parsableByteArray.setPosition(0);
            return new a(parsableByteArray.readInt(), parsableByteArray.readLittleEndianUnsignedInt());
        }
    }

    @Nullable
    public static fe peek(ExtractorInput extractorInput) throws IOException {
        long j;
        boolean z;
        byte[] bArr;
        ExtractorInput extractorInput2 = extractorInput;
        Assertions.checkNotNull(extractorInput);
        ParsableByteArray parsableByteArray = new ParsableByteArray(16);
        if (a.peek(extractorInput2, parsableByteArray).a != 1380533830) {
            return null;
        }
        extractorInput2.peekFully(parsableByteArray.getData(), 0, 4);
        parsableByteArray.setPosition(0);
        int readInt = parsableByteArray.readInt();
        if (readInt != 1463899717) {
            StringBuilder sb = new StringBuilder(36);
            sb.append("Unsupported RIFF format: ");
            sb.append(readInt);
            Log.e("WavHeaderReader", sb.toString());
            return null;
        }
        a peek = a.peek(extractorInput2, parsableByteArray);
        while (true) {
            int i = peek.a;
            j = peek.b;
            if (i == 1718449184) {
                break;
            }
            extractorInput2.advancePeekPosition((int) j);
            peek = a.peek(extractorInput2, parsableByteArray);
        }
        if (j >= 16) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        extractorInput2.peekFully(parsableByteArray.getData(), 0, 16);
        parsableByteArray.setPosition(0);
        int readLittleEndianUnsignedShort = parsableByteArray.readLittleEndianUnsignedShort();
        int readLittleEndianUnsignedShort2 = parsableByteArray.readLittleEndianUnsignedShort();
        int readLittleEndianUnsignedIntToInt = parsableByteArray.readLittleEndianUnsignedIntToInt();
        int readLittleEndianUnsignedIntToInt2 = parsableByteArray.readLittleEndianUnsignedIntToInt();
        int readLittleEndianUnsignedShort3 = parsableByteArray.readLittleEndianUnsignedShort();
        int readLittleEndianUnsignedShort4 = parsableByteArray.readLittleEndianUnsignedShort();
        int i2 = ((int) j) - 16;
        if (i2 > 0) {
            byte[] bArr2 = new byte[i2];
            extractorInput2.peekFully(bArr2, 0, i2);
            bArr = bArr2;
        } else {
            bArr = Util.f;
        }
        return new fe(readLittleEndianUnsignedShort, readLittleEndianUnsignedShort2, readLittleEndianUnsignedIntToInt, readLittleEndianUnsignedIntToInt2, readLittleEndianUnsignedShort3, readLittleEndianUnsignedShort4, bArr);
    }

    public static Pair<Long, Long> skipToData(ExtractorInput extractorInput) throws IOException {
        Assertions.checkNotNull(extractorInput);
        extractorInput.resetPeekPosition();
        ParsableByteArray parsableByteArray = new ParsableByteArray(8);
        a peek = a.peek(extractorInput, parsableByteArray);
        while (true) {
            int i = peek.a;
            long j = peek.b;
            if (i != 1684108385) {
                if (!(i == 1380533830 || i == 1718449184)) {
                    y2.t(39, "Ignoring unknown WAV chunk: ", i, "WavHeaderReader");
                }
                long j2 = j + 8;
                int i2 = peek.a;
                if (i2 == 1380533830) {
                    j2 = 12;
                }
                if (j2 <= 2147483647L) {
                    extractorInput.skipFully((int) j2);
                    peek = a.peek(extractorInput, parsableByteArray);
                } else {
                    throw new ParserException(y2.d(51, "Chunk is too large (~2GB+) to skip; id: ", i2));
                }
            } else {
                extractorInput.skipFully(8);
                long position = extractorInput.getPosition();
                long j3 = j + position;
                long length = extractorInput.getLength();
                if (length != -1 && j3 > length) {
                    StringBuilder sb = new StringBuilder(69);
                    sb.append("Data exceeds input length: ");
                    sb.append(j3);
                    sb.append(", ");
                    sb.append(length);
                    Log.w("WavHeaderReader", sb.toString());
                    j3 = length;
                }
                return Pair.create(Long.valueOf(position), Long.valueOf(j3));
            }
        }
    }
}
