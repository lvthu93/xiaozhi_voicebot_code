package com.google.android.exoplayer2.video;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.ArrayList;
import java.util.List;

public final class AvcConfig {
    public final List<byte[]> a;
    public final int b;
    public final int c;
    public final int d;
    public final float e;
    @Nullable
    public final String f;

    public AvcConfig(ArrayList arrayList, int i, int i2, int i3, float f2, @Nullable String str) {
        this.a = arrayList;
        this.b = i;
        this.c = i2;
        this.d = i3;
        this.e = f2;
        this.f = str;
    }

    public static AvcConfig parse(ParsableByteArray parsableByteArray) throws ParserException {
        String str;
        float f2;
        int i;
        int i2;
        try {
            parsableByteArray.skipBytes(4);
            int readUnsignedByte = (parsableByteArray.readUnsignedByte() & 3) + 1;
            if (readUnsignedByte != 3) {
                ArrayList arrayList = new ArrayList();
                int readUnsignedByte2 = parsableByteArray.readUnsignedByte() & 31;
                for (int i3 = 0; i3 < readUnsignedByte2; i3++) {
                    int readUnsignedShort = parsableByteArray.readUnsignedShort();
                    int position = parsableByteArray.getPosition();
                    parsableByteArray.skipBytes(readUnsignedShort);
                    arrayList.add(CodecSpecificDataUtil.buildNalUnit(parsableByteArray.getData(), position, readUnsignedShort));
                }
                int readUnsignedByte3 = parsableByteArray.readUnsignedByte();
                for (int i4 = 0; i4 < readUnsignedByte3; i4++) {
                    int readUnsignedShort2 = parsableByteArray.readUnsignedShort();
                    int position2 = parsableByteArray.getPosition();
                    parsableByteArray.skipBytes(readUnsignedShort2);
                    arrayList.add(CodecSpecificDataUtil.buildNalUnit(parsableByteArray.getData(), position2, readUnsignedShort2));
                }
                if (readUnsignedByte2 > 0) {
                    NalUnitUtil.SpsData parseSpsNalUnit = NalUnitUtil.parseSpsNalUnit((byte[]) arrayList.get(0), readUnsignedByte, ((byte[]) arrayList.get(0)).length);
                    int i5 = parseSpsNalUnit.e;
                    int i6 = parseSpsNalUnit.f;
                    float f3 = parseSpsNalUnit.g;
                    str = CodecSpecificDataUtil.buildAvcCodecString(parseSpsNalUnit.a, parseSpsNalUnit.b, parseSpsNalUnit.c);
                    i2 = i5;
                    i = i6;
                    f2 = f3;
                } else {
                    str = null;
                    i2 = -1;
                    i = -1;
                    f2 = 1.0f;
                }
                return new AvcConfig(arrayList, readUnsignedByte, i2, i, f2, str);
            }
            throw new IllegalStateException();
        } catch (ArrayIndexOutOfBoundsException e2) {
            throw new ParserException("Error parsing AVC config", e2);
        }
    }
}
