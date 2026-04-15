package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;

public final class CeaUtil {
    public static void consume(long j, ParsableByteArray parsableByteArray, TrackOutput[] trackOutputArr) {
        int i;
        int i2;
        boolean z;
        while (true) {
            boolean z2 = true;
            if (parsableByteArray.bytesLeft() > 1) {
                int i3 = 0;
                while (true) {
                    if (parsableByteArray.bytesLeft() != 0) {
                        int readUnsignedByte = parsableByteArray.readUnsignedByte();
                        i3 += readUnsignedByte;
                        if (readUnsignedByte != 255) {
                            i = i3;
                            break;
                        }
                    } else {
                        i = -1;
                        break;
                    }
                }
                int i4 = 0;
                while (true) {
                    if (parsableByteArray.bytesLeft() != 0) {
                        int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
                        i4 += readUnsignedByte2;
                        if (readUnsignedByte2 != 255) {
                            break;
                        }
                    } else {
                        i4 = -1;
                        break;
                    }
                }
                int position = parsableByteArray.getPosition() + i4;
                if (i4 == -1 || i4 > parsableByteArray.bytesLeft()) {
                    Log.w("CeaUtil", "Skipping remainder of malformed SEI NAL unit.");
                    position = parsableByteArray.limit();
                } else if (i == 4 && i4 >= 8) {
                    int readUnsignedByte3 = parsableByteArray.readUnsignedByte();
                    int readUnsignedShort = parsableByteArray.readUnsignedShort();
                    if (readUnsignedShort == 49) {
                        i2 = parsableByteArray.readInt();
                    } else {
                        i2 = 0;
                    }
                    int readUnsignedByte4 = parsableByteArray.readUnsignedByte();
                    if (readUnsignedShort == 47) {
                        parsableByteArray.skipBytes(1);
                    }
                    if (readUnsignedByte3 == 181 && ((readUnsignedShort == 49 || readUnsignedShort == 47) && readUnsignedByte4 == 3)) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (readUnsignedShort == 49) {
                        if (i2 != 1195456820) {
                            z2 = false;
                        }
                        z &= z2;
                    }
                    if (z) {
                        consumeCcData(j, parsableByteArray, trackOutputArr);
                    }
                }
                parsableByteArray.setPosition(position);
            } else {
                return;
            }
        }
    }

    public static void consumeCcData(long j, ParsableByteArray parsableByteArray, TrackOutput[] trackOutputArr) {
        boolean z;
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        if ((readUnsignedByte & 64) != 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            parsableByteArray.skipBytes(1);
            int i = (readUnsignedByte & 31) * 3;
            int position = parsableByteArray.getPosition();
            for (TrackOutput trackOutput : trackOutputArr) {
                parsableByteArray.setPosition(position);
                trackOutput.sampleData(parsableByteArray, i);
                trackOutput.sampleMetadata(j, 1, i, 0, (TrackOutput.CryptoData) null);
            }
        }
    }
}
