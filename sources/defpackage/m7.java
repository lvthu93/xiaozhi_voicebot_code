package defpackage;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;

/* renamed from: m7  reason: default package */
public final class m7 {
    public int a;
    public long b;
    public int c;
    public int d;
    public int e;
    public final int[] f = new int[255];
    public final ParsableByteArray g = new ParsableByteArray(255);

    public boolean populate(ExtractorInput extractorInput, boolean z) throws IOException {
        reset();
        ParsableByteArray parsableByteArray = this.g;
        parsableByteArray.reset(27);
        if (!ExtractorUtil.peekFullyQuietly(extractorInput, parsableByteArray.getData(), 0, 27, z) || parsableByteArray.readUnsignedInt() != 1332176723) {
            return false;
        }
        if (parsableByteArray.readUnsignedByte() == 0) {
            this.a = parsableByteArray.readUnsignedByte();
            this.b = parsableByteArray.readLittleEndianLong();
            parsableByteArray.readLittleEndianUnsignedInt();
            parsableByteArray.readLittleEndianUnsignedInt();
            parsableByteArray.readLittleEndianUnsignedInt();
            int readUnsignedByte = parsableByteArray.readUnsignedByte();
            this.c = readUnsignedByte;
            this.d = readUnsignedByte + 27;
            parsableByteArray.reset(readUnsignedByte);
            if (!ExtractorUtil.peekFullyQuietly(extractorInput, parsableByteArray.getData(), 0, this.c, z)) {
                return false;
            }
            for (int i = 0; i < this.c; i++) {
                int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
                this.f[i] = readUnsignedByte2;
                this.e += readUnsignedByte2;
            }
            return true;
        } else if (z) {
            return false;
        } else {
            throw new ParserException("unsupported bit stream revision");
        }
    }

    public void reset() {
        this.a = 0;
        this.b = 0;
        this.c = 0;
        this.d = 0;
        this.e = 0;
    }

    public boolean skipToNextPage(ExtractorInput extractorInput) throws IOException {
        return skipToNextPage(extractorInput, -1);
    }

    public boolean skipToNextPage(ExtractorInput extractorInput, long j) throws IOException {
        int i;
        Assertions.checkArgument(extractorInput.getPosition() == extractorInput.getPeekPosition());
        ParsableByteArray parsableByteArray = this.g;
        parsableByteArray.reset(4);
        while (true) {
            i = (j > -1 ? 1 : (j == -1 ? 0 : -1));
            if ((i == 0 || extractorInput.getPosition() + 4 < j) && ExtractorUtil.peekFullyQuietly(extractorInput, parsableByteArray.getData(), 0, 4, true)) {
                parsableByteArray.setPosition(0);
                if (parsableByteArray.readUnsignedInt() == 1332176723) {
                    extractorInput.resetPeekPosition();
                    return true;
                }
                extractorInput.skipFully(1);
            }
        }
        do {
            if ((i != 0 && extractorInput.getPosition() >= j) || extractorInput.skip(1) == -1) {
                return false;
            }
            break;
        } while (extractorInput.skip(1) == -1);
        return false;
    }
}
