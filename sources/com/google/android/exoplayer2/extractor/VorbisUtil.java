package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.Arrays;

public final class VorbisUtil {

    public static final class CommentHeader {
        public final String[] a;

        public CommentHeader(String str, String[] strArr, int i) {
            this.a = strArr;
        }
    }

    public static final class Mode {
        public final boolean a;

        public Mode(boolean z, int i, int i2, int i3) {
            this.a = z;
        }
    }

    public static final class VorbisIdHeader {
        public final int a;
        public final int b;
        public final int c;
        public final int d;
        public final int e;
        public final int f;
        public final byte[] g;

        public VorbisIdHeader(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z, byte[] bArr) {
            this.a = i2;
            this.b = i3;
            this.c = i4;
            this.d = i5;
            this.e = i7;
            this.f = i8;
            this.g = bArr;
        }
    }

    public static final class a {
        public a(int i, int i2, long[] jArr, int i3, boolean z) {
        }
    }

    public static int iLog(int i) {
        int i2 = 0;
        while (i > 0) {
            i2++;
            i >>>= 1;
        }
        return i2;
    }

    public static CommentHeader readVorbisCommentHeader(ParsableByteArray parsableByteArray) throws ParserException {
        return readVorbisCommentHeader(parsableByteArray, true, true);
    }

    public static VorbisIdHeader readVorbisIdentificationHeader(ParsableByteArray parsableByteArray) throws ParserException {
        boolean z = true;
        verifyVorbisHeaderCapturePattern(1, parsableByteArray, false);
        int readLittleEndianUnsignedIntToInt = parsableByteArray.readLittleEndianUnsignedIntToInt();
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int readLittleEndianUnsignedIntToInt2 = parsableByteArray.readLittleEndianUnsignedIntToInt();
        int readLittleEndianInt = parsableByteArray.readLittleEndianInt();
        if (readLittleEndianInt <= 0) {
            readLittleEndianInt = -1;
        }
        int readLittleEndianInt2 = parsableByteArray.readLittleEndianInt();
        if (readLittleEndianInt2 <= 0) {
            readLittleEndianInt2 = -1;
        }
        int readLittleEndianInt3 = parsableByteArray.readLittleEndianInt();
        if (readLittleEndianInt3 <= 0) {
            readLittleEndianInt3 = -1;
        }
        int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
        int pow = (int) Math.pow(2.0d, (double) (readUnsignedByte2 & 15));
        int pow2 = (int) Math.pow(2.0d, (double) ((readUnsignedByte2 & 240) >> 4));
        if ((parsableByteArray.readUnsignedByte() & 1) <= 0) {
            z = false;
        }
        return new VorbisIdHeader(readLittleEndianUnsignedIntToInt, readUnsignedByte, readLittleEndianUnsignedIntToInt2, readLittleEndianInt, readLittleEndianInt2, readLittleEndianInt3, pow, pow2, z, Arrays.copyOf(parsableByteArray.getData(), parsableByteArray.limit()));
    }

    public static Mode[] readVorbisModes(ParsableByteArray parsableByteArray, int i) throws ParserException {
        int i2;
        int i3;
        long j;
        int i4 = i;
        int i5 = 5;
        int i6 = 0;
        verifyVorbisHeaderCapturePattern(5, parsableByteArray, false);
        int readUnsignedByte = parsableByteArray.readUnsignedByte() + 1;
        VorbisBitArray vorbisBitArray = new VorbisBitArray(parsableByteArray.getData());
        vorbisBitArray.skipBits(parsableByteArray.getPosition() * 8);
        int i7 = 0;
        while (i7 < readUnsignedByte) {
            if (vorbisBitArray.readBits(24) == 5653314) {
                int readBits = vorbisBitArray.readBits(16);
                int readBits2 = vorbisBitArray.readBits(24);
                long[] jArr = new long[readBits2];
                boolean readBit = vorbisBitArray.readBit();
                if (!readBit) {
                    boolean readBit2 = vorbisBitArray.readBit();
                    while (i6 < readBits2) {
                        if (!readBit2) {
                            jArr[i6] = (long) (vorbisBitArray.readBits(5) + 1);
                        } else if (vorbisBitArray.readBit()) {
                            jArr[i6] = (long) (vorbisBitArray.readBits(5) + 1);
                        } else {
                            jArr[i6] = 0;
                        }
                        i6++;
                    }
                } else {
                    int readBits3 = vorbisBitArray.readBits(5) + 1;
                    int i8 = 0;
                    while (i8 < readBits2) {
                        int readBits4 = vorbisBitArray.readBits(iLog(readBits2 - i8));
                        for (int i9 = 0; i9 < readBits4 && i8 < readBits2; i9++) {
                            jArr[i8] = (long) readBits3;
                            i8++;
                        }
                        readBits3++;
                    }
                }
                int readBits5 = vorbisBitArray.readBits(4);
                if (readBits5 <= 2) {
                    if (readBits5 == 1 || readBits5 == 2) {
                        vorbisBitArray.skipBits(32);
                        vorbisBitArray.skipBits(32);
                        int readBits6 = vorbisBitArray.readBits(4) + 1;
                        vorbisBitArray.skipBits(1);
                        if (readBits5 != 1) {
                            j = ((long) readBits2) * ((long) readBits);
                        } else if (readBits != 0) {
                            j = (long) Math.floor(Math.pow((double) ((long) readBits2), 1.0d / ((double) ((long) readBits))));
                        } else {
                            j = 0;
                        }
                        vorbisBitArray.skipBits((int) (j * ((long) readBits6)));
                    }
                    new a(readBits, readBits2, jArr, readBits5, readBit);
                    i7++;
                    i6 = 0;
                } else {
                    throw new ParserException(y2.d(53, "lookup type greater than 2 not decodable: ", readBits5));
                }
            } else {
                throw new ParserException(y2.d(66, "expected code book to start with [0x56, 0x43, 0x42] at ", vorbisBitArray.getPosition()));
            }
        }
        int readBits7 = vorbisBitArray.readBits(6) + 1;
        int i10 = 0;
        while (i10 < readBits7) {
            if (vorbisBitArray.readBits(16) == 0) {
                i10++;
            } else {
                throw new ParserException("placeholder of time domain transforms not zeroed out");
            }
        }
        int readBits8 = vorbisBitArray.readBits(6) + 1;
        int i11 = 0;
        while (true) {
            int i12 = 3;
            if (i11 < readBits8) {
                int readBits9 = vorbisBitArray.readBits(16);
                if (readBits9 == 0) {
                    int i13 = 8;
                    vorbisBitArray.skipBits(8);
                    vorbisBitArray.skipBits(16);
                    vorbisBitArray.skipBits(16);
                    vorbisBitArray.skipBits(6);
                    vorbisBitArray.skipBits(8);
                    int readBits10 = vorbisBitArray.readBits(4) + 1;
                    int i14 = 0;
                    while (i14 < readBits10) {
                        vorbisBitArray.skipBits(i13);
                        i14++;
                        i13 = 8;
                    }
                } else if (readBits9 == 1) {
                    int readBits11 = vorbisBitArray.readBits(i5);
                    int[] iArr = new int[readBits11];
                    int i15 = -1;
                    for (int i16 = 0; i16 < readBits11; i16++) {
                        int readBits12 = vorbisBitArray.readBits(4);
                        iArr[i16] = readBits12;
                        if (readBits12 > i15) {
                            i15 = readBits12;
                        }
                    }
                    int i17 = i15 + 1;
                    int[] iArr2 = new int[i17];
                    int i18 = 0;
                    while (i18 < i17) {
                        iArr2[i18] = vorbisBitArray.readBits(i12) + 1;
                        int readBits13 = vorbisBitArray.readBits(2);
                        int i19 = 8;
                        if (readBits13 > 0) {
                            vorbisBitArray.skipBits(8);
                        }
                        int i20 = 0;
                        while (i20 < (1 << readBits13)) {
                            vorbisBitArray.skipBits(i19);
                            i20++;
                            i19 = 8;
                        }
                        i18++;
                        i12 = 3;
                    }
                    vorbisBitArray.skipBits(2);
                    int readBits14 = vorbisBitArray.readBits(4);
                    int i21 = 0;
                    int i22 = 0;
                    for (int i23 = 0; i23 < readBits11; i23++) {
                        i21 += iArr2[iArr[i23]];
                        while (i22 < i21) {
                            vorbisBitArray.skipBits(readBits14);
                            i22++;
                        }
                    }
                } else {
                    throw new ParserException(y2.d(52, "floor type greater than 1 not decodable: ", readBits9));
                }
                i11++;
                i5 = 5;
            } else {
                int readBits15 = vorbisBitArray.readBits(6) + 1;
                int i24 = 0;
                while (i24 < readBits15) {
                    if (vorbisBitArray.readBits(16) <= 2) {
                        vorbisBitArray.skipBits(24);
                        vorbisBitArray.skipBits(24);
                        vorbisBitArray.skipBits(24);
                        int readBits16 = vorbisBitArray.readBits(6) + 1;
                        int i25 = 8;
                        vorbisBitArray.skipBits(8);
                        int[] iArr3 = new int[readBits16];
                        for (int i26 = 0; i26 < readBits16; i26++) {
                            int readBits17 = vorbisBitArray.readBits(3);
                            if (vorbisBitArray.readBit()) {
                                i3 = vorbisBitArray.readBits(5);
                            } else {
                                i3 = 0;
                            }
                            iArr3[i26] = (i3 * 8) + readBits17;
                        }
                        int i27 = 0;
                        while (i27 < readBits16) {
                            int i28 = 0;
                            while (i28 < i25) {
                                if ((iArr3[i27] & (1 << i28)) != 0) {
                                    vorbisBitArray.skipBits(i25);
                                }
                                i28++;
                                i25 = 8;
                            }
                            i27++;
                            i25 = 8;
                        }
                        i24++;
                    } else {
                        throw new ParserException("residueType greater than 2 is not decodable");
                    }
                }
                int readBits18 = vorbisBitArray.readBits(6) + 1;
                for (int i29 = 0; i29 < readBits18; i29++) {
                    int readBits19 = vorbisBitArray.readBits(16);
                    if (readBits19 != 0) {
                        StringBuilder sb = new StringBuilder(52);
                        sb.append("mapping type other than 0 not supported: ");
                        sb.append(readBits19);
                        Log.e("VorbisUtil", sb.toString());
                    } else {
                        if (vorbisBitArray.readBit()) {
                            i2 = vorbisBitArray.readBits(4) + 1;
                        } else {
                            i2 = 1;
                        }
                        if (vorbisBitArray.readBit()) {
                            int readBits20 = vorbisBitArray.readBits(8) + 1;
                            for (int i30 = 0; i30 < readBits20; i30++) {
                                int i31 = i4 - 1;
                                vorbisBitArray.skipBits(iLog(i31));
                                vorbisBitArray.skipBits(iLog(i31));
                            }
                        }
                        if (vorbisBitArray.readBits(2) == 0) {
                            if (i2 > 1) {
                                for (int i32 = 0; i32 < i4; i32++) {
                                    vorbisBitArray.skipBits(4);
                                }
                            }
                            for (int i33 = 0; i33 < i2; i33++) {
                                vorbisBitArray.skipBits(8);
                                vorbisBitArray.skipBits(8);
                                vorbisBitArray.skipBits(8);
                            }
                        } else {
                            throw new ParserException("to reserved bits must be zero after mapping coupling steps");
                        }
                    }
                }
                int readBits21 = vorbisBitArray.readBits(6) + 1;
                Mode[] modeArr = new Mode[readBits21];
                for (int i34 = 0; i34 < readBits21; i34++) {
                    modeArr[i34] = new Mode(vorbisBitArray.readBit(), vorbisBitArray.readBits(16), vorbisBitArray.readBits(16), vorbisBitArray.readBits(8));
                }
                if (vorbisBitArray.readBit()) {
                    return modeArr;
                }
                throw new ParserException("framing bit after modes not set as expected");
            }
        }
    }

    public static boolean verifyVorbisHeaderCapturePattern(int i, ParsableByteArray parsableByteArray, boolean z) throws ParserException {
        String str;
        if (parsableByteArray.bytesLeft() < 7) {
            if (z) {
                return false;
            }
            throw new ParserException(y2.d(29, "too short header: ", parsableByteArray.bytesLeft()));
        } else if (parsableByteArray.readUnsignedByte() != i) {
            if (z) {
                return false;
            }
            String valueOf = String.valueOf(Integer.toHexString(i));
            if (valueOf.length() != 0) {
                str = "expected header type ".concat(valueOf);
            } else {
                str = new String("expected header type ");
            }
            throw new ParserException(str);
        } else if (parsableByteArray.readUnsignedByte() == 118 && parsableByteArray.readUnsignedByte() == 111 && parsableByteArray.readUnsignedByte() == 114 && parsableByteArray.readUnsignedByte() == 98 && parsableByteArray.readUnsignedByte() == 105 && parsableByteArray.readUnsignedByte() == 115) {
            return true;
        } else {
            if (z) {
                return false;
            }
            throw new ParserException("expected characters 'vorbis'");
        }
    }

    public static CommentHeader readVorbisCommentHeader(ParsableByteArray parsableByteArray, boolean z, boolean z2) throws ParserException {
        if (z) {
            verifyVorbisHeaderCapturePattern(3, parsableByteArray, false);
        }
        String readString = parsableByteArray.readString((int) parsableByteArray.readLittleEndianUnsignedInt());
        long readLittleEndianUnsignedInt = parsableByteArray.readLittleEndianUnsignedInt();
        String[] strArr = new String[((int) readLittleEndianUnsignedInt)];
        int length = readString.length() + 11 + 4;
        for (int i = 0; ((long) i) < readLittleEndianUnsignedInt; i++) {
            String readString2 = parsableByteArray.readString((int) parsableByteArray.readLittleEndianUnsignedInt());
            strArr[i] = readString2;
            length = length + 4 + readString2.length();
        }
        if (!z2 || (parsableByteArray.readUnsignedByte() & 1) != 0) {
            return new CommentHeader(readString, strArr, length + 1);
        }
        throw new ParserException("framing bit expected to be set");
    }
}
