package defpackage;

import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;

/* renamed from: eb  reason: default package */
public final class eb {
    public static final int[] a = {1769172845, 1769172786, 1769172787, 1769172788, 1769172789, 1769172790, 1769172793, 1635148593, 1752589105, 1751479857, 1635135537, 1836069937, 1836069938, 862401121, 862401122, 862417462, 862417718, 862414134, 862414646, 1295275552, 1295270176, 1714714144, 1801741417, 1295275600, 1903435808, 1297305174, 1684175153, 1769172332, 1885955686};

    public static boolean a(ExtractorInput extractorInput, boolean z, boolean z2) throws IOException {
        boolean z3;
        boolean z4;
        int i;
        boolean z5;
        ExtractorInput extractorInput2 = extractorInput;
        long length = extractorInput.getLength();
        long j = 4096;
        long j2 = -1;
        int i2 = (length > -1 ? 1 : (length == -1 ? 0 : -1));
        if (i2 != 0 && length <= 4096) {
            j = length;
        }
        int i3 = (int) j;
        ParsableByteArray parsableByteArray = new ParsableByteArray(64);
        boolean z6 = false;
        int i4 = 0;
        boolean z7 = false;
        while (true) {
            if (i4 >= i3) {
                break;
            }
            parsableByteArray.reset(8);
            if (!extractorInput2.peekFully(parsableByteArray.getData(), z6 ? 1 : 0, 8, true)) {
                break;
            }
            long readUnsignedInt = parsableByteArray.readUnsignedInt();
            int readInt = parsableByteArray.readInt();
            if (readUnsignedInt == 1) {
                extractorInput2.peekFully(parsableByteArray.getData(), 8, 8);
                parsableByteArray.setLimit(16);
                readUnsignedInt = parsableByteArray.readLong();
                i = 16;
            } else {
                if (readUnsignedInt == 0) {
                    long length2 = extractorInput.getLength();
                    if (length2 != j2) {
                        readUnsignedInt = (length2 - extractorInput.getPeekPosition()) + ((long) 8);
                    }
                }
                i = 8;
            }
            long j3 = (long) i;
            if (readUnsignedInt < j3) {
                return z6;
            }
            i4 += i;
            if (readInt == 1836019574) {
                i3 += (int) readUnsignedInt;
                if (i2 != 0 && ((long) i3) > length) {
                    i3 = (int) length;
                }
                j2 = -1;
            } else if (readInt == 1836019558 || readInt == 1836475768) {
                z3 = true;
                z4 = true;
            } else {
                int i5 = i2;
                int i6 = i4;
                if ((((long) i4) + readUnsignedInt) - j3 >= ((long) i3)) {
                    break;
                }
                int i7 = (int) (readUnsignedInt - j3);
                i4 = i6 + i7;
                if (readInt == 1718909296) {
                    if (i7 < 8) {
                        return false;
                    }
                    parsableByteArray.reset(i7);
                    extractorInput2.peekFully(parsableByteArray.getData(), 0, i7);
                    int i8 = i7 / 4;
                    int i9 = 0;
                    while (true) {
                        if (i9 >= i8) {
                            break;
                        }
                        if (i9 == 1) {
                            parsableByteArray.skipBytes(4);
                        } else {
                            int readInt2 = parsableByteArray.readInt();
                            if ((readInt2 >>> 8) != 3368816 && (readInt2 != 1751476579 || !z2)) {
                                int[] iArr = a;
                                int i10 = 0;
                                while (true) {
                                    if (i10 >= 29) {
                                        z5 = false;
                                        break;
                                    } else if (iArr[i10] == readInt2) {
                                        break;
                                    } else {
                                        i10++;
                                    }
                                }
                            }
                            z5 = true;
                            if (z5) {
                                z7 = true;
                                break;
                            }
                        }
                        i9++;
                    }
                    if (!z7) {
                        return false;
                    }
                } else if (i7 != 0) {
                    extractorInput2.advancePeekPosition(i7);
                }
                i2 = i5;
                j2 = -1;
                z6 = false;
            }
        }
        z3 = true;
        z4 = false;
        if (!z7 || z != z4) {
            return false;
        }
        return z3;
    }

    public static boolean sniffFragmented(ExtractorInput extractorInput) throws IOException {
        return a(extractorInput, true, false);
    }

    public static boolean sniffUnfragmented(ExtractorInput extractorInput) throws IOException {
        return a(extractorInput, false, false);
    }

    public static boolean sniffUnfragmented(ExtractorInput extractorInput, boolean z) throws IOException {
        return a(extractorInput, false, z);
    }
}
