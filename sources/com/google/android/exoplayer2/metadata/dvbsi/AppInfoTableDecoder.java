package com.google.android.exoplayer2.metadata.dvbsi;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataInputBuffer;
import com.google.android.exoplayer2.metadata.SimpleMetadataDecoder;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.common.base.Charsets;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public final class AppInfoTableDecoder extends SimpleMetadataDecoder {
    @Nullable
    public final Metadata a(MetadataInputBuffer metadataInputBuffer, ByteBuffer byteBuffer) {
        String str;
        if (byteBuffer.get() != 116) {
            return null;
        }
        ParsableBitArray parsableBitArray = new ParsableBitArray(byteBuffer.array(), byteBuffer.limit());
        parsableBitArray.skipBits(12);
        int bytePosition = (parsableBitArray.getBytePosition() + parsableBitArray.readBits(12)) - 4;
        parsableBitArray.skipBits(44);
        parsableBitArray.skipBytes(parsableBitArray.readBits(12));
        parsableBitArray.skipBits(16);
        ArrayList arrayList = new ArrayList();
        while (parsableBitArray.getBytePosition() < bytePosition) {
            parsableBitArray.skipBits(48);
            int readBits = parsableBitArray.readBits(8);
            parsableBitArray.skipBits(4);
            int bytePosition2 = parsableBitArray.getBytePosition() + parsableBitArray.readBits(12);
            String str2 = null;
            String str3 = null;
            while (parsableBitArray.getBytePosition() < bytePosition2) {
                int readBits2 = parsableBitArray.readBits(8);
                int readBits3 = parsableBitArray.readBits(8);
                int bytePosition3 = parsableBitArray.getBytePosition() + readBits3;
                if (readBits2 == 2) {
                    int readBits4 = parsableBitArray.readBits(16);
                    parsableBitArray.skipBits(8);
                    if (readBits4 != 3) {
                    }
                    while (parsableBitArray.getBytePosition() < bytePosition3) {
                        str2 = parsableBitArray.readBytesAsString(parsableBitArray.readBits(8), Charsets.a);
                        int readBits5 = parsableBitArray.readBits(8);
                        for (int i = 0; i < readBits5; i++) {
                            parsableBitArray.skipBytes(parsableBitArray.readBits(8));
                        }
                    }
                } else if (readBits2 == 21) {
                    str3 = parsableBitArray.readBytesAsString(readBits3, Charsets.a);
                }
                parsableBitArray.setPosition(bytePosition3 * 8);
            }
            parsableBitArray.setPosition(bytePosition2 * 8);
            if (!(str2 == null || str3 == null)) {
                if (str3.length() != 0) {
                    str = str2.concat(str3);
                } else {
                    str = new String(str2);
                }
                arrayList.add(new AppInfoTable(readBits, str));
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new Metadata((List<? extends Metadata.Entry>) arrayList);
    }
}
