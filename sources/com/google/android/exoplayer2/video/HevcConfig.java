package com.google.android.exoplayer2.video;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.ParsableNalUnitBitArray;
import java.util.Collections;
import java.util.List;
import org.mozilla.javascript.Token;

public final class HevcConfig {
    @Nullable
    public final List<byte[]> a;
    public final int b;
    @Nullable
    public final String c;

    public HevcConfig(int i, @Nullable String str, @Nullable List list) {
        this.a = list;
        this.b = i;
        this.c = str;
    }

    public static HevcConfig parse(ParsableByteArray parsableByteArray) throws ParserException {
        List list;
        ParsableByteArray parsableByteArray2 = parsableByteArray;
        try {
            parsableByteArray2.skipBytes(21);
            int readUnsignedByte = parsableByteArray.readUnsignedByte() & 3;
            int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
            int position = parsableByteArray.getPosition();
            int i = 0;
            for (int i2 = 0; i2 < readUnsignedByte2; i2++) {
                parsableByteArray2.skipBytes(1);
                int readUnsignedShort = parsableByteArray.readUnsignedShort();
                for (int i3 = 0; i3 < readUnsignedShort; i3++) {
                    int readUnsignedShort2 = parsableByteArray.readUnsignedShort();
                    i += readUnsignedShort2 + 4;
                    parsableByteArray2.skipBytes(readUnsignedShort2);
                }
            }
            parsableByteArray2.setPosition(position);
            byte[] bArr = new byte[i];
            String str = null;
            int i4 = 0;
            for (int i5 = 0; i5 < readUnsignedByte2; i5++) {
                int readUnsignedByte3 = parsableByteArray.readUnsignedByte() & Token.VOID;
                int readUnsignedShort3 = parsableByteArray.readUnsignedShort();
                for (int i6 = 0; i6 < readUnsignedShort3; i6++) {
                    int readUnsignedShort4 = parsableByteArray.readUnsignedShort();
                    System.arraycopy(NalUnitUtil.a, 0, bArr, i4, 4);
                    int i7 = i4 + 4;
                    System.arraycopy(parsableByteArray.getData(), parsableByteArray.getPosition(), bArr, i7, readUnsignedShort4);
                    if (readUnsignedByte3 == 33 && i6 == 0) {
                        str = CodecSpecificDataUtil.buildHevcCodecStringFromSps(new ParsableNalUnitBitArray(bArr, i7, i7 + readUnsignedShort4));
                    }
                    i4 = i7 + readUnsignedShort4;
                    parsableByteArray2.skipBytes(readUnsignedShort4);
                }
            }
            if (i == 0) {
                list = null;
            } else {
                list = Collections.singletonList(bArr);
            }
            return new HevcConfig(readUnsignedByte + 1, str, list);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParserException("Error parsing HEVC config", e);
        }
    }
}
