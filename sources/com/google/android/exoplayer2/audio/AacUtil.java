package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableBitArray;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class AacUtil {
    public static final int[] a = {96000, 88200, 64000, 48000, 44100, 32000, 24000, 22050, 16000, 12000, 11025, 8000, 7350};
    public static final int[] b = {0, 1, 2, 3, 4, 5, 6, 8, -1, -1, -1, 7, 8, -1, 8, -1};

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface AacAudioObjectType {
    }

    public static final class Config {
        public final int a;
        public final int b;
        public final String c;

        public Config(int i, int i2, String str) {
            this.a = i;
            this.b = i2;
            this.c = str;
        }
    }

    public static int a(ParsableBitArray parsableBitArray) throws ParserException {
        int readBits = parsableBitArray.readBits(4);
        if (readBits == 15) {
            return parsableBitArray.readBits(24);
        }
        if (readBits < 13) {
            return a[readBits];
        }
        throw new ParserException();
    }

    public static byte[] buildAacLcAudioSpecificConfig(int i, int i2) {
        int i3 = -1;
        for (int i4 = 0; i4 < 13; i4++) {
            if (i == a[i4]) {
                i3 = i4;
            }
        }
        int i5 = -1;
        for (int i6 = 0; i6 < 16; i6++) {
            if (i2 == b[i6]) {
                i5 = i6;
            }
        }
        if (i != -1 && i5 != -1) {
            return buildAudioSpecificConfig(2, i3, i5);
        }
        StringBuilder sb = new StringBuilder(67);
        sb.append("Invalid sample rate or number of channels: ");
        sb.append(i);
        sb.append(", ");
        sb.append(i2);
        throw new IllegalArgumentException(sb.toString());
    }

    public static byte[] buildAudioSpecificConfig(int i, int i2, int i3) {
        return new byte[]{(byte) (((i << 3) & 248) | ((i2 >> 1) & 7)), (byte) (((i2 << 7) & 128) | ((i3 << 3) & 120))};
    }

    public static int getEncodingForAudioObjectType(int i) {
        if (i == 2) {
            return 10;
        }
        if (i == 5) {
            return 11;
        }
        if (i == 29) {
            return 12;
        }
        if (i == 42) {
            return 16;
        }
        if (i != 22) {
            return i != 23 ? 0 : 15;
        }
        return 1073741824;
    }

    public static Config parseAudioSpecificConfig(byte[] bArr) throws ParserException {
        return parseAudioSpecificConfig(new ParsableBitArray(bArr), false);
    }

    public static Config parseAudioSpecificConfig(ParsableBitArray parsableBitArray, boolean z) throws ParserException {
        int readBits = parsableBitArray.readBits(5);
        if (readBits == 31) {
            readBits = parsableBitArray.readBits(6) + 32;
        }
        int a2 = a(parsableBitArray);
        int readBits2 = parsableBitArray.readBits(4);
        String d = y2.d(19, "mp4a.40.", readBits);
        if (readBits == 5 || readBits == 29) {
            a2 = a(parsableBitArray);
            int readBits3 = parsableBitArray.readBits(5);
            if (readBits3 == 31) {
                readBits3 = parsableBitArray.readBits(6) + 32;
            }
            readBits = readBits3;
            if (readBits == 22) {
                readBits2 = parsableBitArray.readBits(4);
            }
        }
        if (z) {
            if (!(readBits == 1 || readBits == 2 || readBits == 3 || readBits == 4 || readBits == 6 || readBits == 7 || readBits == 17)) {
                switch (readBits) {
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                        break;
                    default:
                        throw new ParserException(y2.d(42, "Unsupported audio object type: ", readBits));
                }
            }
            if (parsableBitArray.readBit()) {
                Log.w("AacUtil", "Unexpected frameLengthFlag = 1");
            }
            if (parsableBitArray.readBit()) {
                parsableBitArray.skipBits(14);
            }
            boolean readBit = parsableBitArray.readBit();
            if (readBits2 != 0) {
                if (readBits == 6 || readBits == 20) {
                    parsableBitArray.skipBits(3);
                }
                if (readBit) {
                    if (readBits == 22) {
                        parsableBitArray.skipBits(16);
                    }
                    if (readBits == 17 || readBits == 19 || readBits == 20 || readBits == 23) {
                        parsableBitArray.skipBits(3);
                    }
                    parsableBitArray.skipBits(1);
                }
                switch (readBits) {
                    case 17:
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                        int readBits4 = parsableBitArray.readBits(2);
                        if (readBits4 == 2 || readBits4 == 3) {
                            throw new ParserException(y2.d(33, "Unsupported epConfig: ", readBits4));
                        }
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }
        int i = b[readBits2];
        if (i != -1) {
            return new Config(a2, i, d);
        }
        throw new ParserException();
    }
}
