package com.google.android.exoplayer2.text.dvb;

import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.List;

public final class DvbDecoder extends SimpleSubtitleDecoder {
    public final x1 o;

    public DvbDecoder(List<byte[]> list) {
        super("DvbDecoder");
        ParsableByteArray parsableByteArray = new ParsableByteArray(list.get(0));
        this.o = new x1(parsableByteArray.readUnsignedShort(), parsableByteArray.readUnsignedShort());
    }

    public final Subtitle e(byte[] bArr, int i, boolean z) {
        x1 x1Var = this.o;
        if (z) {
            x1Var.reset();
        }
        return new y1(x1Var.decode(bArr, i));
    }
}
