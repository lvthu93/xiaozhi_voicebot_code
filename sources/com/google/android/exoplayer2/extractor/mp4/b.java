package com.google.android.exoplayer2.extractor.mp4;

import android.util.Pair;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.mp4.a;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.mp4.MdtaMetadataEntry;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.List;
import org.mozilla.javascript.Token;

public final class b {
    public static final byte[] a = Util.getUtf8Bytes("OpusHead");

    public static final class a {
        public final int a;
        public int b;
        public int c;
        public long d;
        public final boolean e;
        public final ParsableByteArray f;
        public final ParsableByteArray g;
        public int h;
        public int i;

        public a(ParsableByteArray parsableByteArray, ParsableByteArray parsableByteArray2, boolean z) {
            this.g = parsableByteArray;
            this.f = parsableByteArray2;
            this.e = z;
            parsableByteArray2.setPosition(12);
            this.a = parsableByteArray2.readUnsignedIntToInt();
            parsableByteArray.setPosition(12);
            this.i = parsableByteArray.readUnsignedIntToInt();
            Assertions.checkState(parsableByteArray.readInt() != 1 ? false : true, "first_chunk must be 1");
            this.b = -1;
        }

        public boolean moveNext() {
            long j;
            int i2;
            int i3 = this.b + 1;
            this.b = i3;
            if (i3 == this.a) {
                return false;
            }
            boolean z = this.e;
            ParsableByteArray parsableByteArray = this.f;
            if (z) {
                j = parsableByteArray.readUnsignedLongToLong();
            } else {
                j = parsableByteArray.readUnsignedInt();
            }
            this.d = j;
            if (this.b == this.h) {
                ParsableByteArray parsableByteArray2 = this.g;
                this.c = parsableByteArray2.readUnsignedIntToInt();
                parsableByteArray2.skipBytes(4);
                int i4 = this.i - 1;
                this.i = i4;
                if (i4 > 0) {
                    i2 = parsableByteArray2.readUnsignedIntToInt() - 1;
                } else {
                    i2 = -1;
                }
                this.h = i2;
            }
            return true;
        }
    }

    /* renamed from: com.google.android.exoplayer2.extractor.mp4.b$b  reason: collision with other inner class name */
    public interface C0015b {
        int getFixedSampleSize();

        int getSampleCount();

        int readNextSampleSize();
    }

    public static final class c {
        public final TrackEncryptionBox[] a;
        @Nullable
        public Format b;
        public int c;
        public int d = 0;

        public c(int i) {
            this.a = new TrackEncryptionBox[i];
        }
    }

    public static final class d implements C0015b {
        public final int a;
        public final int b;
        public final ParsableByteArray c;

        public d(a.b bVar, Format format) {
            ParsableByteArray parsableByteArray = bVar.b;
            this.c = parsableByteArray;
            parsableByteArray.setPosition(12);
            int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
            if ("audio/raw".equals(format.p)) {
                int pcmFrameSize = Util.getPcmFrameSize(format.ae, format.ac);
                if (readUnsignedIntToInt == 0 || readUnsignedIntToInt % pcmFrameSize != 0) {
                    StringBuilder sb = new StringBuilder(88);
                    sb.append("Audio sample size mismatch. stsd sample size: ");
                    sb.append(pcmFrameSize);
                    sb.append(", stsz sample size: ");
                    sb.append(readUnsignedIntToInt);
                    Log.w("AtomParsers", sb.toString());
                    readUnsignedIntToInt = pcmFrameSize;
                }
            }
            this.a = readUnsignedIntToInt == 0 ? -1 : readUnsignedIntToInt;
            this.b = parsableByteArray.readUnsignedIntToInt();
        }

        public int getFixedSampleSize() {
            return this.a;
        }

        public int getSampleCount() {
            return this.b;
        }

        public int readNextSampleSize() {
            int i = this.a;
            return i == -1 ? this.c.readUnsignedIntToInt() : i;
        }
    }

    public static final class e implements C0015b {
        public final ParsableByteArray a;
        public final int b;
        public final int c;
        public int d;
        public int e;

        public e(a.b bVar) {
            ParsableByteArray parsableByteArray = bVar.b;
            this.a = parsableByteArray;
            parsableByteArray.setPosition(12);
            this.c = parsableByteArray.readUnsignedIntToInt() & 255;
            this.b = parsableByteArray.readUnsignedIntToInt();
        }

        public int getFixedSampleSize() {
            return -1;
        }

        public int getSampleCount() {
            return this.b;
        }

        public int readNextSampleSize() {
            ParsableByteArray parsableByteArray = this.a;
            int i = this.c;
            if (i == 8) {
                return parsableByteArray.readUnsignedByte();
            }
            if (i == 16) {
                return parsableByteArray.readUnsignedShort();
            }
            int i2 = this.d;
            this.d = i2 + 1;
            if (i2 % 2 != 0) {
                return this.e & 15;
            }
            int readUnsignedByte = parsableByteArray.readUnsignedByte();
            this.e = readUnsignedByte;
            return (readUnsignedByte & 240) >> 4;
        }
    }

    public static final class f {
        public final int a;
        public final long b;
        public final int c;

        public f(int i, long j, int i2) {
            this.a = i;
            this.b = j;
            this.c = i2;
        }
    }

    public static Pair a(int i, ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(i + 8 + 4);
        parsableByteArray.skipBytes(1);
        b(parsableByteArray);
        parsableByteArray.skipBytes(2);
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        if ((readUnsignedByte & 128) != 0) {
            parsableByteArray.skipBytes(2);
        }
        if ((readUnsignedByte & 64) != 0) {
            parsableByteArray.skipBytes(parsableByteArray.readUnsignedShort());
        }
        if ((readUnsignedByte & 32) != 0) {
            parsableByteArray.skipBytes(2);
        }
        parsableByteArray.skipBytes(1);
        b(parsableByteArray);
        String mimeTypeFromMp4ObjectType = MimeTypes.getMimeTypeFromMp4ObjectType(parsableByteArray.readUnsignedByte());
        if ("audio/mpeg".equals(mimeTypeFromMp4ObjectType) || "audio/vnd.dts".equals(mimeTypeFromMp4ObjectType) || "audio/vnd.dts.hd".equals(mimeTypeFromMp4ObjectType)) {
            return Pair.create(mimeTypeFromMp4ObjectType, (Object) null);
        }
        parsableByteArray.skipBytes(12);
        parsableByteArray.skipBytes(1);
        int b = b(parsableByteArray);
        byte[] bArr = new byte[b];
        parsableByteArray.readBytes(bArr, 0, b);
        return Pair.create(mimeTypeFromMp4ObjectType, bArr);
    }

    public static int b(ParsableByteArray parsableByteArray) {
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int i = readUnsignedByte & Token.VOID;
        while ((readUnsignedByte & 128) == 128) {
            readUnsignedByte = parsableByteArray.readUnsignedByte();
            i = (i << 7) | (readUnsignedByte & Token.VOID);
        }
        return i;
    }

    @Nullable
    public static Pair<Integer, TrackEncryptionBox> c(ParsableByteArray parsableByteArray, int i, int i2) {
        boolean z;
        Pair<Integer, TrackEncryptionBox> pair;
        boolean z2;
        Integer num;
        TrackEncryptionBox trackEncryptionBox;
        int i3;
        int i4;
        boolean z3;
        byte[] bArr;
        ParsableByteArray parsableByteArray2 = parsableByteArray;
        int position = parsableByteArray.getPosition();
        while (position - i < i2) {
            parsableByteArray2.setPosition(position);
            int readInt = parsableByteArray.readInt();
            if (readInt > 0) {
                z = true;
            } else {
                z = false;
            }
            Assertions.checkState(z, "childAtomSize should be positive");
            if (parsableByteArray.readInt() == 1936289382) {
                int i5 = position + 8;
                int i6 = -1;
                int i7 = 0;
                String str = null;
                Integer num2 = null;
                while (i5 - position < readInt) {
                    parsableByteArray2.setPosition(i5);
                    int readInt2 = parsableByteArray.readInt();
                    int readInt3 = parsableByteArray.readInt();
                    if (readInt3 == 1718775137) {
                        num2 = Integer.valueOf(parsableByteArray.readInt());
                    } else if (readInt3 == 1935894637) {
                        parsableByteArray2.skipBytes(4);
                        str = parsableByteArray2.readString(4);
                    } else if (readInt3 == 1935894633) {
                        i6 = i5;
                        i7 = readInt2;
                    }
                    i5 += readInt2;
                }
                if ("cenc".equals(str) || "cbc1".equals(str) || "cens".equals(str) || "cbcs".equals(str)) {
                    Assertions.checkStateNotNull(num2, "frma atom is mandatory");
                    if (i6 != -1) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    Assertions.checkState(z2, "schi atom is mandatory");
                    int i8 = i6 + 8;
                    while (true) {
                        if (i8 - i6 >= i7) {
                            num = num2;
                            trackEncryptionBox = null;
                            break;
                        }
                        parsableByteArray2.setPosition(i8);
                        int readInt4 = parsableByteArray.readInt();
                        if (parsableByteArray.readInt() == 1952804451) {
                            int parseFullAtomVersion = a.parseFullAtomVersion(parsableByteArray.readInt());
                            parsableByteArray2.skipBytes(1);
                            if (parseFullAtomVersion == 0) {
                                parsableByteArray2.skipBytes(1);
                                i4 = 0;
                                i3 = 0;
                            } else {
                                int readUnsignedByte = parsableByteArray.readUnsignedByte();
                                i4 = readUnsignedByte & 15;
                                i3 = (readUnsignedByte & 240) >> 4;
                            }
                            if (parsableByteArray.readUnsignedByte() == 1) {
                                z3 = true;
                            } else {
                                z3 = false;
                            }
                            int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
                            byte[] bArr2 = new byte[16];
                            parsableByteArray2.readBytes(bArr2, 0, 16);
                            if (!z3 || readUnsignedByte2 != 0) {
                                bArr = null;
                            } else {
                                int readUnsignedByte3 = parsableByteArray.readUnsignedByte();
                                byte[] bArr3 = new byte[readUnsignedByte3];
                                parsableByteArray2.readBytes(bArr3, 0, readUnsignedByte3);
                                bArr = bArr3;
                            }
                            num = num2;
                            trackEncryptionBox = new TrackEncryptionBox(z3, str, readUnsignedByte2, bArr2, i3, i4, bArr);
                        } else {
                            Integer num3 = num2;
                            i8 += readInt4;
                        }
                    }
                    pair = Pair.create(num, (TrackEncryptionBox) Assertions.checkStateNotNull(trackEncryptionBox, "tenc atom is mandatory"));
                } else {
                    pair = null;
                }
                if (pair != null) {
                    return pair;
                }
            }
            position += readInt;
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:106:0x0250  */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x0253  */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x02bd  */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x02cd  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0105  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0139  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static defpackage.pc d(com.google.android.exoplayer2.extractor.mp4.Track r43, com.google.android.exoplayer2.extractor.mp4.a.C0014a r44, com.google.android.exoplayer2.extractor.GaplessInfoHolder r45) throws com.google.android.exoplayer2.ParserException {
        /*
            r1 = r43
            r0 = r44
            r2 = r45
            r3 = 1937011578(0x7374737a, float:1.936741E31)
            com.google.android.exoplayer2.extractor.mp4.a$b r3 = r0.getLeafAtomOfType(r3)
            com.google.android.exoplayer2.Format r4 = r1.f
            if (r3 == 0) goto L_0x0017
            com.google.android.exoplayer2.extractor.mp4.b$d r5 = new com.google.android.exoplayer2.extractor.mp4.b$d
            r5.<init>(r3, r4)
            goto L_0x0025
        L_0x0017:
            r3 = 1937013298(0x73747a32, float:1.9369489E31)
            com.google.android.exoplayer2.extractor.mp4.a$b r3 = r0.getLeafAtomOfType(r3)
            if (r3 == 0) goto L_0x0536
            com.google.android.exoplayer2.extractor.mp4.b$e r5 = new com.google.android.exoplayer2.extractor.mp4.b$e
            r5.<init>(r3)
        L_0x0025:
            int r3 = r5.getSampleCount()
            r6 = 0
            if (r3 != 0) goto L_0x0040
            pc r9 = new pc
            long[] r2 = new long[r6]
            int[] r3 = new int[r6]
            r4 = 0
            long[] r5 = new long[r6]
            int[] r6 = new int[r6]
            r7 = 0
            r0 = r9
            r1 = r43
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            return r9
        L_0x0040:
            r7 = 1937007471(0x7374636f, float:1.9362445E31)
            com.google.android.exoplayer2.extractor.mp4.a$b r7 = r0.getLeafAtomOfType(r7)
            r8 = 1
            if (r7 != 0) goto L_0x0059
            r7 = 1668232756(0x636f3634, float:4.4126776E21)
            com.google.android.exoplayer2.extractor.mp4.a$b r7 = r0.getLeafAtomOfType(r7)
            java.lang.Object r7 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r7)
            com.google.android.exoplayer2.extractor.mp4.a$b r7 = (com.google.android.exoplayer2.extractor.mp4.a.b) r7
            r9 = 1
            goto L_0x005a
        L_0x0059:
            r9 = 0
        L_0x005a:
            com.google.android.exoplayer2.util.ParsableByteArray r7 = r7.b
            r10 = 1937011555(0x73747363, float:1.9367382E31)
            com.google.android.exoplayer2.extractor.mp4.a$b r10 = r0.getLeafAtomOfType(r10)
            java.lang.Object r10 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r10)
            com.google.android.exoplayer2.extractor.mp4.a$b r10 = (com.google.android.exoplayer2.extractor.mp4.a.b) r10
            com.google.android.exoplayer2.util.ParsableByteArray r10 = r10.b
            r11 = 1937011827(0x73747473, float:1.9367711E31)
            com.google.android.exoplayer2.extractor.mp4.a$b r11 = r0.getLeafAtomOfType(r11)
            java.lang.Object r11 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r11)
            com.google.android.exoplayer2.extractor.mp4.a$b r11 = (com.google.android.exoplayer2.extractor.mp4.a.b) r11
            com.google.android.exoplayer2.util.ParsableByteArray r11 = r11.b
            r12 = 1937011571(0x73747373, float:1.9367401E31)
            com.google.android.exoplayer2.extractor.mp4.a$b r12 = r0.getLeafAtomOfType(r12)
            if (r12 == 0) goto L_0x0086
            com.google.android.exoplayer2.util.ParsableByteArray r12 = r12.b
            goto L_0x0087
        L_0x0086:
            r12 = 0
        L_0x0087:
            r14 = 1668576371(0x63747473, float:4.5093966E21)
            com.google.android.exoplayer2.extractor.mp4.a$b r0 = r0.getLeafAtomOfType(r14)
            if (r0 == 0) goto L_0x0093
            com.google.android.exoplayer2.util.ParsableByteArray r0 = r0.b
            goto L_0x0094
        L_0x0093:
            r0 = 0
        L_0x0094:
            com.google.android.exoplayer2.extractor.mp4.b$a r14 = new com.google.android.exoplayer2.extractor.mp4.b$a
            r14.<init>(r10, r7, r9)
            r7 = 12
            r11.setPosition(r7)
            int r9 = r11.readUnsignedIntToInt()
            int r9 = r9 - r8
            int r10 = r11.readUnsignedIntToInt()
            int r15 = r11.readUnsignedIntToInt()
            if (r0 == 0) goto L_0x00b5
            r0.setPosition(r7)
            int r16 = r0.readUnsignedIntToInt()
            goto L_0x00b7
        L_0x00b5:
            r16 = 0
        L_0x00b7:
            r13 = -1
            if (r12 == 0) goto L_0x00cd
            r12.setPosition(r7)
            int r7 = r12.readUnsignedIntToInt()
            if (r7 <= 0) goto L_0x00ca
            int r17 = r12.readUnsignedIntToInt()
            int r17 = r17 + -1
            goto L_0x00d4
        L_0x00ca:
            r17 = 0
            goto L_0x00d0
        L_0x00cd:
            r17 = r12
            r7 = 0
        L_0x00d0:
            r12 = r17
            r17 = -1
        L_0x00d4:
            int r6 = r5.getFixedSampleSize()
            java.lang.String r8 = r4.p
            if (r6 == r13) goto L_0x00ff
            java.lang.String r13 = "audio/raw"
            boolean r13 = r13.equals(r8)
            if (r13 != 0) goto L_0x00f4
            java.lang.String r13 = "audio/g711-mlaw"
            boolean r13 = r13.equals(r8)
            if (r13 != 0) goto L_0x00f4
            java.lang.String r13 = "audio/g711-alaw"
            boolean r8 = r13.equals(r8)
            if (r8 == 0) goto L_0x00ff
        L_0x00f4:
            if (r9 != 0) goto L_0x00ff
            if (r16 != 0) goto L_0x00ff
            if (r7 != 0) goto L_0x00ff
            r44 = r9
            r13 = r10
            r8 = 1
            goto L_0x0103
        L_0x00ff:
            r44 = r9
            r13 = r10
            r8 = 0
        L_0x0103:
            if (r8 == 0) goto L_0x0139
            int r0 = r14.a
            long[] r5 = new long[r0]
            int[] r0 = new int[r0]
        L_0x010b:
            boolean r7 = r14.moveNext()
            if (r7 == 0) goto L_0x011c
            int r7 = r14.b
            long r11 = r14.d
            r5[r7] = r11
            int r8 = r14.c
            r0[r7] = r8
            goto L_0x010b
        L_0x011c:
            long r7 = (long) r15
            com.google.android.exoplayer2.extractor.mp4.FixedSampleSizeRechunker$Results r0 = com.google.android.exoplayer2.extractor.mp4.FixedSampleSizeRechunker.rechunk(r6, r5, r0, r7)
            long[] r5 = r0.a
            int[] r6 = r0.b
            int r7 = r0.c
            long[] r8 = r0.d
            int[] r11 = r0.e
            long r12 = r0.f
            r0 = r3
            r17 = r4
            r2 = r5
            r3 = r6
            r4 = r7
            r5 = r8
            r6 = r11
            r7 = r12
            r12 = r1
            goto L_0x02aa
        L_0x0139:
            long[] r6 = new long[r3]
            int[] r8 = new int[r3]
            long[] r9 = new long[r3]
            int[] r10 = new int[r3]
            r24 = r11
            r2 = r15
            r1 = 0
            r11 = 0
            r22 = 0
            r23 = 0
            r25 = 0
            r26 = 0
            r28 = 0
            r15 = r13
            r41 = r17
            r17 = r4
            r4 = r41
        L_0x0157:
            java.lang.String r13 = "AtomParsers"
            if (r1 >= r3) goto L_0x021a
            r30 = r26
            r26 = r23
            r23 = 1
        L_0x0161:
            if (r26 != 0) goto L_0x017a
            boolean r23 = r14.moveNext()
            if (r23 == 0) goto L_0x017a
            r32 = r2
            r27 = r3
            long r2 = r14.d
            r30 = r2
            int r2 = r14.c
            r26 = r2
            r3 = r27
            r2 = r32
            goto L_0x0161
        L_0x017a:
            r32 = r2
            r27 = r3
            if (r23 != 0) goto L_0x019c
            java.lang.String r2 = "Unexpected end of chunk data"
            com.google.android.exoplayer2.util.Log.w(r13, r2)
            long[] r6 = java.util.Arrays.copyOf(r6, r1)
            int[] r8 = java.util.Arrays.copyOf(r8, r1)
            long[] r9 = java.util.Arrays.copyOf(r9, r1)
            int[] r10 = java.util.Arrays.copyOf(r10, r1)
            r3 = r1
            r2 = r22
            r1 = r26
            goto L_0x0222
        L_0x019c:
            if (r0 == 0) goto L_0x01af
        L_0x019e:
            if (r25 != 0) goto L_0x01ad
            if (r16 <= 0) goto L_0x01ad
            int r25 = r0.readUnsignedIntToInt()
            int r22 = r0.readInt()
            int r16 = r16 + -1
            goto L_0x019e
        L_0x01ad:
            int r25 = r25 + -1
        L_0x01af:
            r2 = r22
            r6[r1] = r30
            int r3 = r5.readNextSampleSize()
            r8[r1] = r3
            if (r3 <= r11) goto L_0x01bc
            r11 = r3
        L_0x01bc:
            r3 = r5
            r33 = r6
            long r5 = (long) r2
            long r5 = r28 + r5
            r9[r1] = r5
            if (r12 != 0) goto L_0x01c8
            r5 = 1
            goto L_0x01c9
        L_0x01c8:
            r5 = 0
        L_0x01c9:
            r10[r1] = r5
            if (r1 != r4) goto L_0x01df
            r5 = 1
            r10[r1] = r5
            int r7 = r7 + -1
            if (r7 <= 0) goto L_0x01df
            java.lang.Object r4 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r12)
            com.google.android.exoplayer2.util.ParsableByteArray r4 = (com.google.android.exoplayer2.util.ParsableByteArray) r4
            int r4 = r4.readUnsignedIntToInt()
            int r4 = r4 - r5
        L_0x01df:
            r6 = r2
            r5 = r32
            r32 = r3
            long r2 = (long) r5
            long r28 = r28 + r2
            int r15 = r15 + -1
            if (r15 != 0) goto L_0x01fa
            if (r44 <= 0) goto L_0x01fa
            int r2 = r24.readUnsignedIntToInt()
            int r3 = r24.readInt()
            int r5 = r44 + -1
            r15 = r2
            r2 = r3
            goto L_0x01fd
        L_0x01fa:
            r2 = r5
            r5 = r44
        L_0x01fd:
            r3 = r8[r1]
            r44 = r2
            long r2 = (long) r3
            long r2 = r30 + r2
            int r23 = r26 + -1
            int r1 = r1 + 1
            r22 = r6
            r6 = r33
            r41 = r2
            r2 = r44
            r44 = r5
            r3 = r27
            r5 = r32
            r26 = r41
            goto L_0x0157
        L_0x021a:
            r27 = r3
            r33 = r6
            r2 = r22
            r1 = r23
        L_0x0222:
            long r4 = (long) r2
            long r4 = r28 + r4
            if (r0 == 0) goto L_0x0237
        L_0x0227:
            if (r16 <= 0) goto L_0x0237
            int r2 = r0.readUnsignedIntToInt()
            if (r2 == 0) goto L_0x0231
            r0 = 0
            goto L_0x0238
        L_0x0231:
            r0.readInt()
            int r16 = r16 + -1
            goto L_0x0227
        L_0x0237:
            r0 = 1
        L_0x0238:
            if (r7 != 0) goto L_0x024c
            if (r15 != 0) goto L_0x024c
            if (r1 != 0) goto L_0x024c
            if (r44 != 0) goto L_0x024c
            r2 = r25
            if (r2 != 0) goto L_0x024e
            if (r0 != 0) goto L_0x0247
            goto L_0x024e
        L_0x0247:
            r12 = r43
            r16 = r3
            goto L_0x02a2
        L_0x024c:
            r2 = r25
        L_0x024e:
            if (r0 != 0) goto L_0x0253
            java.lang.String r0 = ", ctts invalid"
            goto L_0x0255
        L_0x0253:
            java.lang.String r0 = ""
        L_0x0255:
            int r12 = r0.length()
            int r12 = r12 + 262
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>(r12)
            java.lang.String r12 = "Inconsistent stbl box for track "
            r14.append(r12)
            r12 = r43
            r16 = r3
            int r3 = r12.a
            r14.append(r3)
            java.lang.String r3 = ": remainingSynchronizationSamples "
            r14.append(r3)
            r14.append(r7)
            java.lang.String r3 = ", remainingSamplesAtTimestampDelta "
            r14.append(r3)
            r14.append(r15)
            java.lang.String r3 = ", remainingSamplesInChunk "
            r14.append(r3)
            r14.append(r1)
            java.lang.String r1 = ", remainingTimestampDeltaChanges "
            r14.append(r1)
            r1 = r44
            r14.append(r1)
            java.lang.String r1 = ", remainingSamplesAtTimestampOffset "
            r14.append(r1)
            r14.append(r2)
            r14.append(r0)
            java.lang.String r0 = r14.toString()
            com.google.android.exoplayer2.util.Log.w(r13, r0)
        L_0x02a2:
            r2 = r6
            r3 = r8
            r6 = r10
            r0 = r16
            r7 = r4
            r5 = r9
            r4 = r11
        L_0x02aa:
            long r9 = r12.c
            r24 = 1000000(0xf4240, double:4.940656E-318)
            r22 = r7
            r26 = r9
            long r9 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r22, r24, r26)
            long r13 = r12.c
            long[] r1 = r12.h
            if (r1 != 0) goto L_0x02cd
            r7 = 1000000(0xf4240, double:4.940656E-318)
            com.google.android.exoplayer2.util.Util.scaleLargeTimestampsInPlace(r5, r7, r13)
            pc r11 = new pc
            r0 = r11
            r1 = r43
            r7 = r9
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            return r11
        L_0x02cd:
            r22 = r7
            int r7 = r1.length
            int r8 = r12.b
            long[] r9 = r12.i
            r10 = 1
            if (r7 != r10) goto L_0x0388
            if (r8 != r10) goto L_0x0388
            int r7 = r5.length
            r10 = 2
            if (r7 < r10) goto L_0x0388
            java.lang.Object r7 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r9)
            long[] r7 = (long[]) r7
            r10 = 0
            r24 = r7[r10]
            r26 = r1[r10]
            long r10 = r12.c
            r32 = r8
            long r7 = r12.d
            r28 = r10
            r30 = r7
            long r7 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r26, r28, r30)
            long r7 = r24 + r7
            int r10 = r5.length
            r11 = 1
            int r10 = r10 - r11
            r11 = 4
            r15 = 0
            int r16 = com.google.android.exoplayer2.util.Util.constrainValue((int) r11, (int) r15, (int) r10)
            r28 = r0
            int r0 = r5.length
            int r0 = r0 - r11
            int r0 = com.google.android.exoplayer2.util.Util.constrainValue((int) r0, (int) r15, (int) r10)
            r10 = r5[r15]
            int r15 = (r10 > r24 ? 1 : (r10 == r24 ? 0 : -1))
            if (r15 > 0) goto L_0x0321
            r15 = r5[r16]
            int r29 = (r24 > r15 ? 1 : (r24 == r15 ? 0 : -1))
            if (r29 >= 0) goto L_0x0321
            r15 = r5[r0]
            int r0 = (r15 > r7 ? 1 : (r15 == r7 ? 0 : -1))
            if (r0 >= 0) goto L_0x0321
            int r0 = (r7 > r22 ? 1 : (r7 == r22 ? 0 : -1))
            if (r0 > 0) goto L_0x0321
            r0 = 1
            goto L_0x0322
        L_0x0321:
            r0 = 0
        L_0x0322:
            if (r0 == 0) goto L_0x0384
            long r33 = r22 - r7
            long r35 = r24 - r10
            r0 = r17
            int r7 = r0.ad
            long r7 = (long) r7
            long r10 = r12.c
            r37 = r7
            r39 = r10
            long r7 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r35, r37, r39)
            int r0 = r0.ad
            long r10 = (long) r0
            r44 = r3
            r15 = r4
            long r3 = r12.c
            r35 = r10
            r37 = r3
            long r3 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r33, r35, r37)
            r10 = 0
            int r0 = (r7 > r10 ? 1 : (r7 == r10 ? 0 : -1))
            if (r0 != 0) goto L_0x0351
            int r0 = (r3 > r10 ? 1 : (r3 == r10 ? 0 : -1))
            if (r0 == 0) goto L_0x038f
        L_0x0351:
            r10 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r0 = (r7 > r10 ? 1 : (r7 == r10 ? 0 : -1))
            if (r0 > 0) goto L_0x038f
            int r0 = (r3 > r10 ? 1 : (r3 == r10 ? 0 : -1))
            if (r0 > 0) goto L_0x038f
            int r0 = (int) r7
            r7 = r45
            r7.a = r0
            int r0 = (int) r3
            r7.b = r0
            r3 = 1000000(0xf4240, double:4.940656E-318)
            com.google.android.exoplayer2.util.Util.scaleLargeTimestampsInPlace(r5, r3, r13)
            r0 = 0
            r16 = r1[r0]
            r18 = 1000000(0xf4240, double:4.940656E-318)
            long r0 = r12.d
            r20 = r0
            long r7 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r16, r18, r20)
            pc r9 = new pc
            r0 = r9
            r1 = r43
            r3 = r44
            r4 = r15
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            return r9
        L_0x0384:
            r44 = r3
            r15 = r4
            goto L_0x038f
        L_0x0388:
            r28 = r0
            r44 = r3
            r15 = r4
            r32 = r8
        L_0x038f:
            int r0 = r1.length
            r3 = 1
            if (r0 != r3) goto L_0x03d5
            r0 = 0
            r3 = r1[r0]
            r7 = 0
            int r10 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r10 != 0) goto L_0x03d5
            java.lang.Object r1 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r9)
            long[] r1 = (long[]) r1
            r3 = r1[r0]
            r0 = 0
        L_0x03a5:
            int r1 = r5.length
            if (r0 >= r1) goto L_0x03bc
            r7 = r5[r0]
            long r16 = r7 - r3
            r18 = 1000000(0xf4240, double:4.940656E-318)
            long r7 = r12.c
            r20 = r7
            long r7 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r16, r18, r20)
            r5[r0] = r7
            int r0 = r0 + 1
            goto L_0x03a5
        L_0x03bc:
            long r16 = r22 - r3
            r18 = 1000000(0xf4240, double:4.940656E-318)
            long r0 = r12.c
            r20 = r0
            long r7 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r16, r18, r20)
            pc r9 = new pc
            r0 = r9
            r1 = r43
            r3 = r44
            r4 = r15
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            return r9
        L_0x03d5:
            r3 = r32
            r0 = 1
            if (r3 != r0) goto L_0x03dc
            r0 = 1
            goto L_0x03dd
        L_0x03dc:
            r0 = 0
        L_0x03dd:
            int r3 = r1.length
            int[] r3 = new int[r3]
            int r4 = r1.length
            int[] r4 = new int[r4]
            java.lang.Object r7 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r9)
            long[] r7 = (long[]) r7
            r8 = 0
            r10 = 0
            r11 = 0
            r13 = 0
        L_0x03ed:
            int r14 = r1.length
            if (r10 >= r14) goto L_0x0452
            r16 = r15
            r14 = r7[r10]
            r22 = -1
            int r17 = (r14 > r22 ? 1 : (r14 == r22 ? 0 : -1))
            if (r17 == 0) goto L_0x043d
            r22 = r1[r10]
            r29 = r1
            r17 = r2
            long r1 = r12.c
            r45 = r7
            r30 = r8
            long r7 = r12.d
            r24 = r1
            r26 = r7
            long r1 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r22, r24, r26)
            r7 = 1
            int r8 = com.google.android.exoplayer2.util.Util.binarySearchFloor((long[]) r5, (long) r14, (boolean) r7, (boolean) r7)
            r3[r10] = r8
            long r14 = r14 + r1
            r1 = 0
            int r2 = com.google.android.exoplayer2.util.Util.binarySearchCeil((long[]) r5, (long) r14, (boolean) r0, (boolean) r1)
            r4[r10] = r2
        L_0x041f:
            r2 = r3[r10]
            r8 = r4[r10]
            if (r2 >= r8) goto L_0x042f
            r14 = r6[r2]
            r14 = r14 & r7
            if (r14 != 0) goto L_0x042f
            int r2 = r2 + 1
            r3[r10] = r2
            goto L_0x041f
        L_0x042f:
            int r14 = r8 - r2
            int r14 = r14 + r11
            if (r13 == r2) goto L_0x0436
            r2 = 1
            goto L_0x0437
        L_0x0436:
            r2 = 0
        L_0x0437:
            r2 = r30 | r2
            r13 = r8
            r11 = r14
            r8 = r2
            goto L_0x0447
        L_0x043d:
            r29 = r1
            r17 = r2
            r45 = r7
            r30 = r8
            r1 = 0
            r7 = 1
        L_0x0447:
            int r10 = r10 + 1
            r7 = r45
            r15 = r16
            r2 = r17
            r1 = r29
            goto L_0x03ed
        L_0x0452:
            r29 = r1
            r17 = r2
            r30 = r8
            r16 = r15
            r2 = r28
            r1 = 0
            r7 = 1
            if (r11 == r2) goto L_0x0462
            r8 = 1
            goto L_0x0463
        L_0x0462:
            r8 = 0
        L_0x0463:
            r0 = r30 | r8
            if (r0 == 0) goto L_0x046a
            long[] r2 = new long[r11]
            goto L_0x046c
        L_0x046a:
            r2 = r17
        L_0x046c:
            if (r0 == 0) goto L_0x0471
            int[] r7 = new int[r11]
            goto L_0x0473
        L_0x0471:
            r7 = r44
        L_0x0473:
            if (r0 == 0) goto L_0x0477
            r16 = 0
        L_0x0477:
            if (r0 == 0) goto L_0x047c
            int[] r8 = new int[r11]
            goto L_0x047d
        L_0x047c:
            r8 = r6
        L_0x047d:
            long[] r10 = new long[r11]
            r18 = r10
            r15 = r29
            r11 = 0
            r13 = 0
        L_0x0486:
            int r10 = r15.length
            if (r1 >= r10) goto L_0x0517
            r28 = r9[r1]
            r10 = r3[r1]
            r19 = r3
            r3 = r4[r1]
            if (r0 == 0) goto L_0x04a7
            r30 = r4
            int r4 = r3 - r10
            r31 = r9
            r9 = r17
            java.lang.System.arraycopy(r9, r10, r2, r11, r4)
            r9 = r44
            java.lang.System.arraycopy(r9, r10, r7, r11, r4)
            java.lang.System.arraycopy(r6, r10, r8, r11, r4)
            goto L_0x04ad
        L_0x04a7:
            r30 = r4
            r31 = r9
            r9 = r44
        L_0x04ad:
            r4 = r16
        L_0x04af:
            if (r10 >= r3) goto L_0x04f8
            r24 = 1000000(0xf4240, double:4.940656E-318)
            r32 = r2
            r44 = r3
            long r2 = r12.d
            r22 = r13
            r26 = r2
            long r2 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r22, r24, r26)
            r22 = r5[r10]
            r24 = r5
            r25 = r6
            long r5 = r22 - r28
            r22 = r13
            r13 = 0
            long r33 = java.lang.Math.max(r13, r5)
            r35 = 1000000(0xf4240, double:4.940656E-318)
            long r5 = r12.c
            r37 = r5
            long r5 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r33, r35, r37)
            long r2 = r2 + r5
            r18[r11] = r2
            if (r0 == 0) goto L_0x04e9
            r2 = r7[r11]
            if (r2 <= r4) goto L_0x04e9
            r2 = r9[r10]
            r4 = r2
        L_0x04e9:
            int r11 = r11 + 1
            int r10 = r10 + 1
            r3 = r44
            r13 = r22
            r5 = r24
            r6 = r25
            r2 = r32
            goto L_0x04af
        L_0x04f8:
            r32 = r2
            r24 = r5
            r25 = r6
            r22 = r13
            r13 = 0
            r2 = r15[r1]
            long r2 = r22 + r2
            int r1 = r1 + 1
            r13 = r2
            r16 = r4
            r44 = r9
            r3 = r19
            r4 = r30
            r9 = r31
            r2 = r32
            goto L_0x0486
        L_0x0517:
            r32 = r2
            r22 = r13
            r24 = 1000000(0xf4240, double:4.940656E-318)
            long r0 = r12.d
            r26 = r0
            long r9 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r22, r24, r26)
            pc r11 = new pc
            r0 = r11
            r1 = r43
            r3 = r7
            r4 = r16
            r5 = r18
            r6 = r8
            r7 = r9
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            return r11
        L_0x0536:
            com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
            java.lang.String r1 = "Track has no sample table size information"
            r0.<init>((java.lang.String) r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mp4.b.d(com.google.android.exoplayer2.extractor.mp4.Track, com.google.android.exoplayer2.extractor.mp4.a$a, com.google.android.exoplayer2.extractor.GaplessInfoHolder):pc");
    }

    public static void maybeSkipRemainingMetaAtomHeaderBytes(ParsableByteArray parsableByteArray) {
        int position = parsableByteArray.getPosition();
        parsableByteArray.skipBytes(4);
        if (parsableByteArray.readInt() != 1751411826) {
            position += 4;
        }
        parsableByteArray.setPosition(position);
    }

    @Nullable
    public static Metadata parseMdtaFromMeta(a.C0014a aVar) {
        a.b leafAtomOfType = aVar.getLeafAtomOfType(1751411826);
        a.b leafAtomOfType2 = aVar.getLeafAtomOfType(1801812339);
        a.b leafAtomOfType3 = aVar.getLeafAtomOfType(1768715124);
        if (!(leafAtomOfType == null || leafAtomOfType2 == null || leafAtomOfType3 == null)) {
            ParsableByteArray parsableByteArray = leafAtomOfType.b;
            parsableByteArray.setPosition(16);
            if (parsableByteArray.readInt() == 1835299937) {
                ParsableByteArray parsableByteArray2 = leafAtomOfType2.b;
                parsableByteArray2.setPosition(12);
                int readInt = parsableByteArray2.readInt();
                String[] strArr = new String[readInt];
                for (int i = 0; i < readInt; i++) {
                    int readInt2 = parsableByteArray2.readInt();
                    parsableByteArray2.skipBytes(4);
                    strArr[i] = parsableByteArray2.readString(readInt2 - 8);
                }
                ParsableByteArray parsableByteArray3 = leafAtomOfType3.b;
                parsableByteArray3.setPosition(8);
                ArrayList arrayList = new ArrayList();
                while (parsableByteArray3.bytesLeft() > 8) {
                    int position = parsableByteArray3.getPosition();
                    int readInt3 = parsableByteArray3.readInt();
                    int readInt4 = parsableByteArray3.readInt() - 1;
                    if (readInt4 < 0 || readInt4 >= readInt) {
                        y2.t(52, "Skipped metadata with unknown key index: ", readInt4, "AtomParsers");
                    } else {
                        MdtaMetadataEntry parseMdtaMetadataEntryFromIlst = r6.parseMdtaMetadataEntryFromIlst(parsableByteArray3, position + readInt3, strArr[readInt4]);
                        if (parseMdtaMetadataEntryFromIlst != null) {
                            arrayList.add(parseMdtaMetadataEntryFromIlst);
                        }
                    }
                    parsableByteArray3.setPosition(position + readInt3);
                }
                if (!arrayList.isEmpty()) {
                    return new Metadata((List<? extends Metadata.Entry>) arrayList);
                }
            }
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:306:0x0562  */
    /* JADX WARNING: Removed duplicated region for block: B:361:0x06df  */
    /* JADX WARNING: Removed duplicated region for block: B:370:0x0720 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:499:0x09f0  */
    /* JADX WARNING: Removed duplicated region for block: B:522:0x0a65  */
    /* JADX WARNING: Removed duplicated region for block: B:526:0x0a70  */
    /* JADX WARNING: Removed duplicated region for block: B:550:0x0708 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0150  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0155  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x0168  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x016b  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0179  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x01bc  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x01bf  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x01ca  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x01cc  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x0227  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.List<defpackage.pc> parseTraks(com.google.android.exoplayer2.extractor.mp4.a.C0014a r50, com.google.android.exoplayer2.extractor.GaplessInfoHolder r51, long r52, @androidx.annotation.Nullable com.google.android.exoplayer2.drm.DrmInitData r54, boolean r55, boolean r56, com.google.common.base.Function<com.google.android.exoplayer2.extractor.mp4.Track, com.google.android.exoplayer2.extractor.mp4.Track> r57) throws com.google.android.exoplayer2.ParserException {
        /*
            r0 = r50
            r1 = r54
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r4 = 0
        L_0x000a:
            java.util.ArrayList r5 = r0.d
            int r5 = r5.size()
            if (r4 >= r5) goto L_0x0ae6
            java.util.ArrayList r5 = r0.d
            java.lang.Object r5 = r5.get(r4)
            com.google.android.exoplayer2.extractor.mp4.a$a r5 = (com.google.android.exoplayer2.extractor.mp4.a.C0014a) r5
            int r6 = r5.a
            r7 = 1953653099(0x7472616b, float:7.681346E31)
            if (r6 == r7) goto L_0x002b
            r3 = r51
            r1 = r57
            r32 = r4
            r31 = 0
            goto L_0x0ade
        L_0x002b:
            r6 = 1836476516(0x6d766864, float:4.7662196E27)
            com.google.android.exoplayer2.extractor.mp4.a$b r6 = r0.getLeafAtomOfType(r6)
            java.lang.Object r6 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r6)
            com.google.android.exoplayer2.extractor.mp4.a$b r6 = (com.google.android.exoplayer2.extractor.mp4.a.b) r6
            r7 = 1835297121(0x6d646961, float:4.4181236E27)
            com.google.android.exoplayer2.extractor.mp4.a$a r8 = r5.getContainerAtomOfType(r7)
            java.lang.Object r8 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r8)
            com.google.android.exoplayer2.extractor.mp4.a$a r8 = (com.google.android.exoplayer2.extractor.mp4.a.C0014a) r8
            r9 = 1751411826(0x68646c72, float:4.3148E24)
            com.google.android.exoplayer2.extractor.mp4.a$b r9 = r8.getLeafAtomOfType(r9)
            java.lang.Object r9 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r9)
            com.google.android.exoplayer2.extractor.mp4.a$b r9 = (com.google.android.exoplayer2.extractor.mp4.a.b) r9
            com.google.android.exoplayer2.util.ParsableByteArray r9 = r9.b
            r10 = 16
            r9.setPosition(r10)
            int r9 = r9.readInt()
            r11 = 1936684398(0x736f756e, float:1.8971874E31)
            r13 = -1
            if (r9 != r11) goto L_0x0065
            r9 = 1
            goto L_0x008b
        L_0x0065:
            r11 = 1986618469(0x76696465, float:1.1834389E33)
            if (r9 != r11) goto L_0x006c
            r9 = 2
            goto L_0x008b
        L_0x006c:
            r11 = 1952807028(0x74657874, float:7.272211E31)
            if (r9 == r11) goto L_0x008a
            r11 = 1935832172(0x7362746c, float:1.7941596E31)
            if (r9 == r11) goto L_0x008a
            r11 = 1937072756(0x73756274, float:1.944137E31)
            if (r9 == r11) goto L_0x008a
            r11 = 1668047728(0x636c6370, float:4.3605968E21)
            if (r9 != r11) goto L_0x0081
            goto L_0x008a
        L_0x0081:
            r11 = 1835365473(0x6d657461, float:4.4382975E27)
            if (r9 != r11) goto L_0x0088
            r9 = 5
            goto L_0x008b
        L_0x0088:
            r9 = -1
            goto L_0x008b
        L_0x008a:
            r9 = 3
        L_0x008b:
            r16 = 0
            if (r9 != r13) goto L_0x009b
            r1 = r57
            r33 = r2
            r32 = r4
            r0 = r16
            r31 = 0
            goto L_0x0a9f
        L_0x009b:
            r14 = 1953196132(0x746b6864, float:7.46037E31)
            com.google.android.exoplayer2.extractor.mp4.a$b r14 = r5.getLeafAtomOfType(r14)
            java.lang.Object r14 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r14)
            com.google.android.exoplayer2.extractor.mp4.a$b r14 = (com.google.android.exoplayer2.extractor.mp4.a.b) r14
            com.google.android.exoplayer2.util.ParsableByteArray r14 = r14.b
            r15 = 8
            r14.setPosition(r15)
            int r19 = r14.readInt()
            int r19 = com.google.android.exoplayer2.extractor.mp4.a.parseFullAtomVersion(r19)
            if (r19 != 0) goto L_0x00bc
            r3 = 8
            goto L_0x00be
        L_0x00bc:
            r3 = 16
        L_0x00be:
            r14.skipBytes(r3)
            int r3 = r14.readInt()
            r12 = 4
            r14.skipBytes(r12)
            int r21 = r14.getPosition()
            if (r19 != 0) goto L_0x00d1
            r11 = 4
            goto L_0x00d3
        L_0x00d1:
            r11 = 8
        L_0x00d3:
            r7 = 0
        L_0x00d4:
            if (r7 >= r11) goto L_0x00e7
            byte[] r22 = r14.getData()
            int r23 = r21 + r7
            byte r15 = r22[r23]
            if (r15 == r13) goto L_0x00e2
            r7 = 0
            goto L_0x00e8
        L_0x00e2:
            int r7 = r7 + 1
            r15 = 8
            goto L_0x00d4
        L_0x00e7:
            r7 = 1
        L_0x00e8:
            r21 = 0
            r25 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            if (r7 == 0) goto L_0x00f5
            r14.skipBytes(r11)
            goto L_0x0104
        L_0x00f5:
            if (r19 != 0) goto L_0x00fc
            long r27 = r14.readUnsignedInt()
            goto L_0x0100
        L_0x00fc:
            long r27 = r14.readUnsignedLongToLong()
        L_0x0100:
            int r7 = (r27 > r21 ? 1 : (r27 == r21 ? 0 : -1))
            if (r7 != 0) goto L_0x0107
        L_0x0104:
            r32 = r25
            goto L_0x0109
        L_0x0107:
            r32 = r27
        L_0x0109:
            r14.skipBytes(r10)
            int r7 = r14.readInt()
            int r11 = r14.readInt()
            r14.skipBytes(r12)
            int r15 = r14.readInt()
            int r14 = r14.readInt()
            r13 = 65536(0x10000, float:9.18355E-41)
            r12 = -65536(0xffffffffffff0000, float:NaN)
            if (r7 != 0) goto L_0x012e
            if (r11 != r13) goto L_0x012e
            if (r15 != r12) goto L_0x012e
            if (r14 != 0) goto L_0x012e
            r7 = 90
            goto L_0x0145
        L_0x012e:
            if (r7 != 0) goto L_0x0139
            if (r11 != r12) goto L_0x0139
            if (r15 != r13) goto L_0x0139
            if (r14 != 0) goto L_0x0139
            r7 = 270(0x10e, float:3.78E-43)
            goto L_0x0145
        L_0x0139:
            if (r7 != r12) goto L_0x0144
            if (r11 != 0) goto L_0x0144
            if (r15 != 0) goto L_0x0144
            if (r14 != r12) goto L_0x0144
            r7 = 180(0xb4, float:2.52E-43)
            goto L_0x0145
        L_0x0144:
            r7 = 0
        L_0x0145:
            com.google.android.exoplayer2.extractor.mp4.b$f r11 = new com.google.android.exoplayer2.extractor.mp4.b$f
            r12 = r32
            r11.<init>(r3, r12, r7)
            int r3 = (r52 > r25 ? 1 : (r52 == r25 ? 0 : -1))
            if (r3 != 0) goto L_0x0155
            long r12 = r11.b
            r32 = r12
            goto L_0x0157
        L_0x0155:
            r32 = r52
        L_0x0157:
            com.google.android.exoplayer2.util.ParsableByteArray r3 = r6.b
            r6 = 8
            r3.setPosition(r6)
            int r6 = r3.readInt()
            int r6 = com.google.android.exoplayer2.extractor.mp4.a.parseFullAtomVersion(r6)
            if (r6 != 0) goto L_0x016b
            r6 = 8
            goto L_0x016d
        L_0x016b:
            r6 = 16
        L_0x016d:
            r3.skipBytes(r6)
            long r6 = r3.readUnsignedInt()
            int r3 = (r32 > r25 ? 1 : (r32 == r25 ? 0 : -1))
            if (r3 != 0) goto L_0x0179
            goto L_0x0184
        L_0x0179:
            r34 = 1000000(0xf4240, double:4.940656E-318)
            r36 = r6
            long r12 = com.google.android.exoplayer2.util.Util.scaleLargeTimestamp(r32, r34, r36)
            r25 = r12
        L_0x0184:
            r3 = 1835626086(0x6d696e66, float:4.515217E27)
            com.google.android.exoplayer2.extractor.mp4.a$a r12 = r8.getContainerAtomOfType(r3)
            java.lang.Object r3 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r12)
            com.google.android.exoplayer2.extractor.mp4.a$a r3 = (com.google.android.exoplayer2.extractor.mp4.a.C0014a) r3
            r12 = 1937007212(0x7374626c, float:1.9362132E31)
            com.google.android.exoplayer2.extractor.mp4.a$a r3 = r3.getContainerAtomOfType(r12)
            java.lang.Object r3 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r3)
            com.google.android.exoplayer2.extractor.mp4.a$a r3 = (com.google.android.exoplayer2.extractor.mp4.a.C0014a) r3
            r12 = 1835296868(0x6d646864, float:4.418049E27)
            com.google.android.exoplayer2.extractor.mp4.a$b r8 = r8.getLeafAtomOfType(r12)
            java.lang.Object r8 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r8)
            com.google.android.exoplayer2.extractor.mp4.a$b r8 = (com.google.android.exoplayer2.extractor.mp4.a.b) r8
            com.google.android.exoplayer2.util.ParsableByteArray r8 = r8.b
            r12 = 8
            r8.setPosition(r12)
            int r12 = r8.readInt()
            int r12 = com.google.android.exoplayer2.extractor.mp4.a.parseFullAtomVersion(r12)
            if (r12 != 0) goto L_0x01bf
            r13 = 8
            goto L_0x01c1
        L_0x01bf:
            r13 = 16
        L_0x01c1:
            r8.skipBytes(r13)
            long r13 = r8.readUnsignedInt()
            if (r12 != 0) goto L_0x01cc
            r12 = 4
            goto L_0x01ce
        L_0x01cc:
            r12 = 8
        L_0x01ce:
            r8.skipBytes(r12)
            int r8 = r8.readUnsignedShort()
            int r12 = r8 >> 10
            r12 = r12 & 31
            int r12 = r12 + 96
            char r12 = (char) r12
            int r15 = r8 >> 5
            r15 = r15 & 31
            int r15 = r15 + 96
            char r15 = (char) r15
            r8 = r8 & 31
            int r8 = r8 + 96
            char r8 = (char) r8
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r0 = 3
            r10.<init>(r0)
            r10.append(r12)
            r10.append(r15)
            r10.append(r8)
            java.lang.String r0 = r10.toString()
            java.lang.Long r8 = java.lang.Long.valueOf(r13)
            android.util.Pair r0 = android.util.Pair.create(r8, r0)
            r8 = 1937011556(0x73747364, float:1.9367383E31)
            com.google.android.exoplayer2.extractor.mp4.a$b r3 = r3.getLeafAtomOfType(r8)
            java.lang.Object r3 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r3)
            com.google.android.exoplayer2.extractor.mp4.a$b r3 = (com.google.android.exoplayer2.extractor.mp4.a.b) r3
            com.google.android.exoplayer2.util.ParsableByteArray r3 = r3.b
            java.lang.Object r8 = r0.second
            java.lang.String r8 = (java.lang.String) r8
            r10 = 12
            r3.setPosition(r10)
            int r10 = r3.readInt()
            com.google.android.exoplayer2.extractor.mp4.b$c r12 = new com.google.android.exoplayer2.extractor.mp4.b$c
            r12.<init>(r10)
            r13 = 0
        L_0x0225:
            if (r13 >= r10) goto L_0x09df
            int r14 = r3.getPosition()
            int r15 = r3.readInt()
            r32 = r4
            r28 = r10
            if (r15 <= 0) goto L_0x0237
            r10 = 1
            goto L_0x0238
        L_0x0237:
            r10 = 0
        L_0x0238:
            java.lang.String r4 = "childAtomSize should be positive"
            com.google.android.exoplayer2.util.Assertions.checkState(r10, r4)
            int r10 = r3.readInt()
            r33 = r2
            int r2 = r11.a
            r35 = r6
            r6 = 1635148593(0x61766331, float:2.840654E20)
            com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox[] r7 = r12.a
            if (r10 == r6) goto L_0x0761
            r6 = 1635148595(0x61766333, float:2.8406544E20)
            if (r10 == r6) goto L_0x0761
            r6 = 1701733238(0x656e6376, float:7.035987E22)
            if (r10 == r6) goto L_0x0761
            r6 = 1831958048(0x6d317620, float:3.4326032E27)
            if (r10 == r6) goto L_0x0761
            r6 = 1836070006(0x6d703476, float:4.646239E27)
            if (r10 == r6) goto L_0x0761
            r6 = 1752589105(0x68766331, float:4.6541277E24)
            if (r10 == r6) goto L_0x0761
            r6 = 1751479857(0x68657631, float:4.3344087E24)
            if (r10 == r6) goto L_0x0761
            r6 = 1932670515(0x73323633, float:1.4119387E31)
            if (r10 == r6) goto L_0x0761
            r6 = 1211250227(0x48323633, float:182488.8)
            if (r10 == r6) goto L_0x0761
            r6 = 1987063864(0x76703038, float:1.21789965E33)
            if (r10 == r6) goto L_0x0761
            r6 = 1987063865(0x76703039, float:1.2178997E33)
            if (r10 == r6) goto L_0x0761
            r6 = 1635135537(0x61763031, float:2.8383572E20)
            if (r10 == r6) goto L_0x0761
            r6 = 1685479798(0x64766176, float:1.8179687E22)
            if (r10 == r6) goto L_0x0761
            r6 = 1685479729(0x64766131, float:1.817961E22)
            if (r10 == r6) goto L_0x0761
            r6 = 1685481573(0x64766865, float:1.8181686E22)
            if (r10 == r6) goto L_0x0761
            r6 = 1685481521(0x64766831, float:1.8181627E22)
            if (r10 != r6) goto L_0x029b
            goto L_0x0761
        L_0x029b:
            r6 = 1836069985(0x6d703461, float:4.6462328E27)
            r37 = r9
            r9 = 1634492771(0x616c6163, float:2.7252807E20)
            if (r10 == r6) goto L_0x03fa
            r6 = 1701733217(0x656e6361, float:7.0359778E22)
            if (r10 == r6) goto L_0x03fa
            r6 = 1633889587(0x61632d33, float:2.6191674E20)
            if (r10 == r6) goto L_0x03fa
            r6 = 1700998451(0x65632d33, float:6.7050686E22)
            if (r10 == r6) goto L_0x03fa
            r6 = 1633889588(0x61632d34, float:2.6191676E20)
            if (r10 == r6) goto L_0x03fa
            r6 = 1685353315(0x64747363, float:1.803728E22)
            if (r10 == r6) goto L_0x03fa
            r6 = 1685353317(0x64747365, float:1.8037282E22)
            if (r10 == r6) goto L_0x03fa
            r6 = 1685353320(0x64747368, float:1.8037286E22)
            if (r10 == r6) goto L_0x03fa
            r6 = 1685353324(0x6474736c, float:1.803729E22)
            if (r10 == r6) goto L_0x03fa
            r6 = 1935764850(0x73616d72, float:1.7860208E31)
            if (r10 == r6) goto L_0x03fa
            r6 = 1935767394(0x73617762, float:1.7863284E31)
            if (r10 == r6) goto L_0x03fa
            r6 = 1819304813(0x6c70636d, float:1.1624469E27)
            if (r10 == r6) goto L_0x03fa
            r6 = 1936684916(0x736f7774, float:1.89725E31)
            if (r10 == r6) goto L_0x03fa
            r6 = 1953984371(0x74776f73, float:7.841539E31)
            if (r10 == r6) goto L_0x03fa
            r6 = 778924082(0x2e6d7032, float:5.398721E-11)
            if (r10 == r6) goto L_0x03fa
            r6 = 778924083(0x2e6d7033, float:5.3987214E-11)
            if (r10 == r6) goto L_0x03fa
            r6 = 1835557169(0x6d686131, float:4.4948762E27)
            if (r10 == r6) goto L_0x03fa
            r6 = 1835560241(0x6d686d31, float:4.495783E27)
            if (r10 == r6) goto L_0x03fa
            if (r10 == r9) goto L_0x03fa
            r6 = 1634492791(0x616c6177, float:2.7252842E20)
            if (r10 == r6) goto L_0x03fa
            r6 = 1970037111(0x756c6177, float:2.9964816E32)
            if (r10 == r6) goto L_0x03fa
            r6 = 1332770163(0x4f707573, float:4.03422899E9)
            if (r10 == r6) goto L_0x03fa
            r6 = 1716281667(0x664c6143, float:2.4128923E23)
            if (r10 != r6) goto L_0x0312
            goto L_0x03fa
        L_0x0312:
            r4 = 1414810956(0x54544d4c, float:3.64731957E12)
            if (r10 == r4) goto L_0x0378
            r4 = 1954034535(0x74783367, float:7.865797E31)
            if (r10 == r4) goto L_0x0378
            r4 = 2004251764(0x77767474, float:4.998699E33)
            if (r10 == r4) goto L_0x0378
            r4 = 1937010800(0x73747070, float:1.9366469E31)
            if (r10 == r4) goto L_0x0378
            r4 = 1664495672(0x63363038, float:3.360782E21)
            if (r10 != r4) goto L_0x032c
            goto L_0x0378
        L_0x032c:
            r4 = 1835365492(0x6d657474, float:4.4383032E27)
            if (r10 != r4) goto L_0x035c
            int r4 = r14 + 8
            r6 = 8
            int r4 = r4 + r6
            r3.setPosition(r4)
            r4 = 1835365492(0x6d657474, float:4.4383032E27)
            if (r10 != r4) goto L_0x03e3
            r3.readNullTerminatedString()
            java.lang.String r4 = r3.readNullTerminatedString()
            if (r4 == 0) goto L_0x03e3
            com.google.android.exoplayer2.Format$Builder r6 = new com.google.android.exoplayer2.Format$Builder
            r6.<init>()
            com.google.android.exoplayer2.Format$Builder r2 = r6.setId((int) r2)
            com.google.android.exoplayer2.Format$Builder r2 = r2.setSampleMimeType(r4)
            com.google.android.exoplayer2.Format r2 = r2.build()
            r12.b = r2
            goto L_0x03e3
        L_0x035c:
            r4 = 1667329389(0x63616d6d, float:4.1584024E21)
            if (r10 != r4) goto L_0x03e3
            com.google.android.exoplayer2.Format$Builder r4 = new com.google.android.exoplayer2.Format$Builder
            r4.<init>()
            com.google.android.exoplayer2.Format$Builder r2 = r4.setId((int) r2)
            java.lang.String r4 = "application/x-camera-motion"
            com.google.android.exoplayer2.Format$Builder r2 = r2.setSampleMimeType(r4)
            com.google.android.exoplayer2.Format r2 = r2.build()
            r12.b = r2
            goto L_0x03e3
        L_0x0378:
            int r4 = r14 + 8
            r6 = 8
            int r4 = r4 + r6
            r3.setPosition(r4)
            r4 = 1414810956(0x54544d4c, float:3.64731957E12)
            java.lang.String r6 = "application/ttml+xml"
            r29 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            if (r10 != r4) goto L_0x038d
            goto L_0x03c1
        L_0x038d:
            r4 = 1954034535(0x74783367, float:7.865797E31)
            if (r10 != r4) goto L_0x03a5
            int r4 = r15 + -8
            int r4 = r4 + -8
            byte[] r6 = new byte[r4]
            r7 = 0
            r3.readBytes(r6, r7, r4)
            com.google.common.collect.ImmutableList r4 = com.google.common.collect.ImmutableList.of(r6)
            java.lang.String r6 = "application/x-quicktime-tx3g"
        L_0x03a2:
            r9 = r29
            goto L_0x03c4
        L_0x03a5:
            r4 = 2004251764(0x77767474, float:4.998699E33)
            if (r10 != r4) goto L_0x03ae
            java.lang.String r4 = "application/x-mp4-vtt"
        L_0x03ac:
            r6 = r4
            goto L_0x03c1
        L_0x03ae:
            r4 = 1937010800(0x73747070, float:1.9366469E31)
            if (r10 != r4) goto L_0x03b6
            r29 = r21
            goto L_0x03c1
        L_0x03b6:
            r4 = 1664495672(0x63363038, float:3.360782E21)
            if (r10 != r4) goto L_0x03f4
            r4 = 1
            r12.d = r4
            java.lang.String r4 = "application/x-mp4-cea-608"
            goto L_0x03ac
        L_0x03c1:
            r4 = r16
            goto L_0x03a2
        L_0x03c4:
            com.google.android.exoplayer2.Format$Builder r7 = new com.google.android.exoplayer2.Format$Builder
            r7.<init>()
            com.google.android.exoplayer2.Format$Builder r2 = r7.setId((int) r2)
            com.google.android.exoplayer2.Format$Builder r2 = r2.setSampleMimeType(r6)
            com.google.android.exoplayer2.Format$Builder r2 = r2.setLanguage(r8)
            com.google.android.exoplayer2.Format$Builder r2 = r2.setSubsampleOffsetUs(r9)
            com.google.android.exoplayer2.Format$Builder r2 = r2.setInitializationData(r4)
            com.google.android.exoplayer2.Format r2 = r2.build()
            r12.b = r2
        L_0x03e3:
            r38 = r0
            r40 = r5
            r44 = r8
            r1 = r11
            r41 = r13
            r45 = r14
            r11 = r15
            r9 = 3
            r31 = 0
            goto L_0x09c3
        L_0x03f4:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            r0.<init>()
            throw r0
        L_0x03fa:
            int r6 = r14 + 8
            r9 = 8
            int r6 = r6 + r9
            r3.setPosition(r6)
            r6 = 6
            if (r56 == 0) goto L_0x040f
            int r24 = r3.readUnsignedShort()
            r3.skipBytes(r6)
            r9 = r24
            goto L_0x0413
        L_0x040f:
            r3.skipBytes(r9)
            r9 = 0
        L_0x0413:
            if (r9 == 0) goto L_0x0447
            r6 = 1
            if (r9 != r6) goto L_0x0419
            goto L_0x0447
        L_0x0419:
            r6 = 2
            if (r9 != r6) goto L_0x0436
            r6 = 16
            r3.skipBytes(r6)
            double r38 = r3.readDouble()
            r40 = r5
            long r5 = java.lang.Math.round(r38)
            int r6 = (int) r5
            int r5 = r3.readUnsignedIntToInt()
            r9 = 20
            r3.skipBytes(r9)
            goto L_0x0461
        L_0x0436:
            r40 = r5
            r38 = r0
            r39 = r11
            r41 = r13
            r43 = r14
            r42 = r15
            r4 = -1
            r31 = 0
            goto L_0x0756
        L_0x0447:
            r40 = r5
            int r5 = r3.readUnsignedShort()
            r6 = 6
            r3.skipBytes(r6)
            int r6 = r3.readUnsignedFixedPoint1616()
            r30 = r5
            r5 = 1
            if (r9 != r5) goto L_0x045f
            r5 = 16
            r3.skipBytes(r5)
        L_0x045f:
            r5 = r30
        L_0x0461:
            int r9 = r3.getPosition()
            r30 = r5
            r5 = 1701733217(0x656e6361, float:7.0359778E22)
            if (r10 != r5) goto L_0x049b
            android.util.Pair r5 = c(r3, r14, r15)
            if (r5 == 0) goto L_0x0494
            java.lang.Object r10 = r5.first
            java.lang.Integer r10 = (java.lang.Integer) r10
            int r10 = r10.intValue()
            if (r1 != 0) goto L_0x0481
            r38 = r6
            r6 = r16
            goto L_0x048d
        L_0x0481:
            r38 = r6
            java.lang.Object r6 = r5.second
            com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox r6 = (com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox) r6
            java.lang.String r6 = r6.b
            com.google.android.exoplayer2.drm.DrmInitData r6 = r1.copyWithSchemeType(r6)
        L_0x048d:
            java.lang.Object r5 = r5.second
            com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox r5 = (com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox) r5
            r7[r13] = r5
            goto L_0x0497
        L_0x0494:
            r38 = r6
            r6 = r1
        L_0x0497:
            r3.setPosition(r9)
            goto L_0x049e
        L_0x049b:
            r38 = r6
            r6 = r1
        L_0x049e:
            r5 = 1633889587(0x61632d33, float:2.6191674E20)
            if (r10 != r5) goto L_0x04a7
            java.lang.String r5 = "audio/ac3"
            goto L_0x054b
        L_0x04a7:
            r5 = 1700998451(0x65632d33, float:6.7050686E22)
            if (r10 != r5) goto L_0x04b0
            java.lang.String r5 = "audio/eac3"
            goto L_0x054b
        L_0x04b0:
            r5 = 1633889588(0x61632d34, float:2.6191676E20)
            if (r10 != r5) goto L_0x04b9
            java.lang.String r5 = "audio/ac4"
            goto L_0x054b
        L_0x04b9:
            r5 = 1685353315(0x64747363, float:1.803728E22)
            if (r10 != r5) goto L_0x04c2
            java.lang.String r5 = "audio/vnd.dts"
            goto L_0x054b
        L_0x04c2:
            r5 = 1685353320(0x64747368, float:1.8037286E22)
            if (r10 == r5) goto L_0x0549
            r5 = 1685353324(0x6474736c, float:1.803729E22)
            if (r10 != r5) goto L_0x04ce
            goto L_0x0549
        L_0x04ce:
            r5 = 1685353317(0x64747365, float:1.8037282E22)
            if (r10 != r5) goto L_0x04d7
            java.lang.String r5 = "audio/vnd.dts.hd;profile=lbr"
            goto L_0x054b
        L_0x04d7:
            r5 = 1935764850(0x73616d72, float:1.7860208E31)
            if (r10 != r5) goto L_0x04e0
            java.lang.String r5 = "audio/3gpp"
            goto L_0x054b
        L_0x04e0:
            r5 = 1935767394(0x73617762, float:1.7863284E31)
            if (r10 != r5) goto L_0x04e9
            java.lang.String r5 = "audio/amr-wb"
            goto L_0x054b
        L_0x04e9:
            r5 = 1819304813(0x6c70636d, float:1.1624469E27)
            java.lang.String r7 = "audio/raw"
            if (r10 == r5) goto L_0x0547
            r5 = 1936684916(0x736f7774, float:1.89725E31)
            if (r10 != r5) goto L_0x04f6
            goto L_0x0547
        L_0x04f6:
            r5 = 1953984371(0x74776f73, float:7.841539E31)
            if (r10 != r5) goto L_0x04fe
            r5 = 268435456(0x10000000, float:2.5243549E-29)
            goto L_0x054d
        L_0x04fe:
            r5 = 778924082(0x2e6d7032, float:5.398721E-11)
            if (r10 == r5) goto L_0x0544
            r5 = 778924083(0x2e6d7033, float:5.3987214E-11)
            if (r10 != r5) goto L_0x0509
            goto L_0x0544
        L_0x0509:
            r5 = 1835557169(0x6d686131, float:4.4948762E27)
            if (r10 != r5) goto L_0x0511
            java.lang.String r5 = "audio/mha1"
            goto L_0x054b
        L_0x0511:
            r5 = 1835560241(0x6d686d31, float:4.495783E27)
            if (r10 != r5) goto L_0x0519
            java.lang.String r5 = "audio/mhm1"
            goto L_0x054b
        L_0x0519:
            r5 = 1634492771(0x616c6163, float:2.7252807E20)
            if (r10 != r5) goto L_0x0521
            java.lang.String r5 = "audio/alac"
            goto L_0x054b
        L_0x0521:
            r5 = 1634492791(0x616c6177, float:2.7252842E20)
            if (r10 != r5) goto L_0x0529
            java.lang.String r5 = "audio/g711-alaw"
            goto L_0x054b
        L_0x0529:
            r5 = 1970037111(0x756c6177, float:2.9964816E32)
            if (r10 != r5) goto L_0x0531
            java.lang.String r5 = "audio/g711-mlaw"
            goto L_0x054b
        L_0x0531:
            r5 = 1332770163(0x4f707573, float:4.03422899E9)
            if (r10 != r5) goto L_0x0539
            java.lang.String r5 = "audio/opus"
            goto L_0x054b
        L_0x0539:
            r5 = 1716281667(0x664c6143, float:2.4128923E23)
            if (r10 != r5) goto L_0x0541
            java.lang.String r5 = "audio/flac"
            goto L_0x054b
        L_0x0541:
            r7 = r16
            goto L_0x054c
        L_0x0544:
            java.lang.String r5 = "audio/mpeg"
            goto L_0x054b
        L_0x0547:
            r5 = 2
            goto L_0x054d
        L_0x0549:
            java.lang.String r5 = "audio/vnd.dts.hd"
        L_0x054b:
            r7 = r5
        L_0x054c:
            r5 = -1
        L_0x054d:
            r10 = r7
            r39 = r11
            r41 = r13
            r11 = r16
            r13 = r11
            r7 = r30
            r49 = r38
            r38 = r0
            r0 = r9
            r9 = r49
        L_0x055e:
            int r1 = r0 - r14
            if (r1 >= r15) goto L_0x0713
            r3.setPosition(r0)
            int r1 = r3.readInt()
            r42 = r15
            if (r1 <= 0) goto L_0x056f
            r15 = 1
            goto L_0x0570
        L_0x056f:
            r15 = 0
        L_0x0570:
            com.google.android.exoplayer2.util.Assertions.checkState(r15, r4)
            int r15 = r3.readInt()
            r43 = r14
            r14 = 1835557187(0x6d686143, float:4.4948815E27)
            if (r15 != r14) goto L_0x0593
            int r13 = r1 + -13
            byte[] r14 = new byte[r13]
            int r15 = r0 + 13
            r3.setPosition(r15)
            r15 = 0
            r3.readBytes(r14, r15, r13)
            com.google.common.collect.ImmutableList r13 = com.google.common.collect.ImmutableList.of(r14)
            r44 = r8
            goto L_0x0633
        L_0x0593:
            r14 = 1702061171(0x65736473, float:7.183675E22)
            if (r15 == r14) goto L_0x06a2
            if (r56 == 0) goto L_0x05a9
            r14 = 2002876005(0x77617665, float:4.5729223E33)
            if (r15 != r14) goto L_0x05a9
            r44 = r8
            r8 = 1702061171(0x65736473, float:7.183675E22)
            r14 = 4
            r31 = 0
            goto L_0x06aa
        L_0x05a9:
            r14 = 1684103987(0x64616333, float:1.6630662E22)
            if (r15 != r14) goto L_0x05be
            int r14 = r0 + 8
            r3.setPosition(r14)
            java.lang.String r14 = java.lang.Integer.toString(r2)
            com.google.android.exoplayer2.Format r14 = com.google.android.exoplayer2.audio.Ac3Util.parseAc3AnnexFFormat(r3, r14, r8, r6)
            r12.b = r14
            goto L_0x0610
        L_0x05be:
            r14 = 1684366131(0x64656333, float:1.692581E22)
            if (r15 != r14) goto L_0x05d3
            int r14 = r0 + 8
            r3.setPosition(r14)
            java.lang.String r14 = java.lang.Integer.toString(r2)
            com.google.android.exoplayer2.Format r14 = com.google.android.exoplayer2.audio.Ac3Util.parseEAc3AnnexFFormat(r3, r14, r8, r6)
            r12.b = r14
            goto L_0x0610
        L_0x05d3:
            r14 = 1684103988(0x64616334, float:1.6630663E22)
            if (r15 != r14) goto L_0x05e8
            int r14 = r0 + 8
            r3.setPosition(r14)
            java.lang.String r14 = java.lang.Integer.toString(r2)
            com.google.android.exoplayer2.Format r14 = com.google.android.exoplayer2.audio.Ac4Util.parseAc4AnnexEFormat(r3, r14, r8, r6)
            r12.b = r14
            goto L_0x0610
        L_0x05e8:
            r14 = 1684305011(0x64647473, float:1.6856995E22)
            if (r15 != r14) goto L_0x0614
            com.google.android.exoplayer2.Format$Builder r14 = new com.google.android.exoplayer2.Format$Builder
            r14.<init>()
            com.google.android.exoplayer2.Format$Builder r14 = r14.setId((int) r2)
            com.google.android.exoplayer2.Format$Builder r14 = r14.setSampleMimeType(r10)
            com.google.android.exoplayer2.Format$Builder r14 = r14.setChannelCount(r7)
            com.google.android.exoplayer2.Format$Builder r14 = r14.setSampleRate(r9)
            com.google.android.exoplayer2.Format$Builder r14 = r14.setDrmInitData(r6)
            com.google.android.exoplayer2.Format$Builder r14 = r14.setLanguage(r8)
            com.google.android.exoplayer2.Format r14 = r14.build()
            r12.b = r14
        L_0x0610:
            r44 = r8
            r14 = 4
            goto L_0x0667
        L_0x0614:
            r14 = 1682927731(0x644f7073, float:1.5306315E22)
            if (r15 != r14) goto L_0x0639
            int r13 = r1 + -8
            byte[] r14 = a
            int r15 = r14.length
            int r15 = r15 + r13
            byte[] r15 = java.util.Arrays.copyOf(r14, r15)
            r44 = r8
            int r8 = r0 + 8
            r3.setPosition(r8)
            int r8 = r14.length
            r3.readBytes(r15, r8, r13)
            java.util.List r8 = com.google.android.exoplayer2.audio.OpusUtil.buildInitializationData(r15)
            r13 = r8
        L_0x0633:
            r8 = 1634492771(0x616c6163, float:2.7252807E20)
            r14 = 4
        L_0x0637:
            r15 = 0
            goto L_0x069b
        L_0x0639:
            r44 = r8
            r8 = 1684425825(0x64664c61, float:1.6993019E22)
            if (r15 != r8) goto L_0x066b
            int r8 = r1 + -12
            int r13 = r8 + 4
            byte[] r13 = new byte[r13]
            r14 = 102(0x66, float:1.43E-43)
            r15 = 0
            r13[r15] = r14
            r14 = 76
            r15 = 1
            r13[r15] = r14
            r14 = 97
            r15 = 2
            r13[r15] = r14
            r14 = 67
            r15 = 3
            r13[r15] = r14
            int r14 = r0 + 12
            r3.setPosition(r14)
            r14 = 4
            r3.readBytes(r13, r14, r8)
            com.google.common.collect.ImmutableList r13 = com.google.common.collect.ImmutableList.of(r13)
        L_0x0667:
            r8 = 1634492771(0x616c6163, float:2.7252807E20)
            goto L_0x0637
        L_0x066b:
            r8 = 1634492771(0x616c6163, float:2.7252807E20)
            r14 = 4
            if (r15 != r8) goto L_0x0637
            int r7 = r1 + -12
            byte[] r9 = new byte[r7]
            int r13 = r0 + 12
            r3.setPosition(r13)
            r15 = 0
            r3.readBytes(r9, r15, r7)
            android.util.Pair r7 = com.google.android.exoplayer2.util.CodecSpecificDataUtil.parseAlacAudioSpecificConfig(r9)
            java.lang.Object r13 = r7.first
            java.lang.Integer r13 = (java.lang.Integer) r13
            int r13 = r13.intValue()
            java.lang.Object r7 = r7.second
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r7 = r7.intValue()
            com.google.common.collect.ImmutableList r9 = com.google.common.collect.ImmutableList.of(r9)
            r49 = r13
            r13 = r9
            r9 = r49
        L_0x069b:
            r45 = r4
            r4 = -1
            r31 = 0
            goto L_0x0708
        L_0x06a2:
            r44 = r8
            r14 = 4
            r31 = 0
            r8 = 1702061171(0x65736473, float:7.183675E22)
        L_0x06aa:
            if (r15 != r8) goto L_0x06b0
            r8 = r0
            r45 = r4
            goto L_0x06d2
        L_0x06b0:
            int r8 = r3.getPosition()
        L_0x06b4:
            int r15 = r8 - r0
            if (r15 >= r1) goto L_0x06d9
            r3.setPosition(r8)
            int r15 = r3.readInt()
            if (r15 <= 0) goto L_0x06c3
            r14 = 1
            goto L_0x06c4
        L_0x06c3:
            r14 = 0
        L_0x06c4:
            com.google.android.exoplayer2.util.Assertions.checkState(r14, r4)
            int r14 = r3.readInt()
            r45 = r4
            r4 = 1702061171(0x65736473, float:7.183675E22)
            if (r14 != r4) goto L_0x06d4
        L_0x06d2:
            r4 = -1
            goto L_0x06dd
        L_0x06d4:
            int r8 = r8 + r15
            r4 = r45
            r14 = 4
            goto L_0x06b4
        L_0x06d9:
            r45 = r4
            r4 = -1
            r8 = -1
        L_0x06dd:
            if (r8 == r4) goto L_0x0708
            android.util.Pair r8 = a(r8, r3)
            java.lang.Object r10 = r8.first
            java.lang.String r10 = (java.lang.String) r10
            java.lang.Object r8 = r8.second
            byte[] r8 = (byte[]) r8
            if (r8 == 0) goto L_0x0708
            java.lang.String r13 = "audio/mp4a-latm"
            boolean r13 = r13.equals(r10)
            if (r13 == 0) goto L_0x0704
            com.google.android.exoplayer2.audio.AacUtil$Config r7 = com.google.android.exoplayer2.audio.AacUtil.parseAudioSpecificConfig(r8)
            int r9 = r7.a
            int r11 = r7.b
            java.lang.String r7 = r7.c
            r49 = r11
            r11 = r7
            r7 = r49
        L_0x0704:
            com.google.common.collect.ImmutableList r13 = com.google.common.collect.ImmutableList.of(r8)
        L_0x0708:
            int r0 = r0 + r1
            r15 = r42
            r14 = r43
            r8 = r44
            r4 = r45
            goto L_0x055e
        L_0x0713:
            r44 = r8
            r43 = r14
            r42 = r15
            r4 = -1
            r31 = 0
            com.google.android.exoplayer2.Format r0 = r12.b
            if (r0 != 0) goto L_0x0754
            if (r10 == 0) goto L_0x0754
            com.google.android.exoplayer2.Format$Builder r0 = new com.google.android.exoplayer2.Format$Builder
            r0.<init>()
            com.google.android.exoplayer2.Format$Builder r0 = r0.setId((int) r2)
            com.google.android.exoplayer2.Format$Builder r0 = r0.setSampleMimeType(r10)
            com.google.android.exoplayer2.Format$Builder r0 = r0.setCodecs(r11)
            com.google.android.exoplayer2.Format$Builder r0 = r0.setChannelCount(r7)
            com.google.android.exoplayer2.Format$Builder r0 = r0.setSampleRate(r9)
            com.google.android.exoplayer2.Format$Builder r0 = r0.setPcmEncoding(r5)
            com.google.android.exoplayer2.Format$Builder r0 = r0.setInitializationData(r13)
            com.google.android.exoplayer2.Format$Builder r0 = r0.setDrmInitData(r6)
            r8 = r44
            com.google.android.exoplayer2.Format$Builder r0 = r0.setLanguage(r8)
            com.google.android.exoplayer2.Format r0 = r0.build()
            r12.b = r0
            goto L_0x0756
        L_0x0754:
            r8 = r44
        L_0x0756:
            r44 = r8
            r1 = r39
            r11 = r42
            r45 = r43
            r9 = 3
            goto L_0x09c3
        L_0x0761:
            r38 = r0
            r45 = r4
            r40 = r5
            r37 = r9
            r39 = r11
            r41 = r13
            r43 = r14
            r42 = r15
            r4 = -1
            r31 = 0
            int r14 = r43 + 8
            r0 = 8
            int r14 = r14 + r0
            r3.setPosition(r14)
            r0 = 16
            r3.skipBytes(r0)
            int r1 = r3.readUnsignedShort()
            int r5 = r3.readUnsignedShort()
            r6 = 50
            r3.skipBytes(r6)
            int r6 = r3.getPosition()
            r9 = 1701733238(0x656e6376, float:7.035987E22)
            if (r10 != r9) goto L_0x07c8
            r11 = r42
            r9 = r43
            android.util.Pair r13 = c(r3, r9, r11)
            if (r13 == 0) goto L_0x07c1
            java.lang.Object r10 = r13.first
            java.lang.Integer r10 = (java.lang.Integer) r10
            int r10 = r10.intValue()
            r14 = r54
            if (r14 != 0) goto L_0x07b0
            r15 = r16
            goto L_0x07ba
        L_0x07b0:
            java.lang.Object r15 = r13.second
            com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox r15 = (com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox) r15
            java.lang.String r15 = r15.b
            com.google.android.exoplayer2.drm.DrmInitData r15 = r14.copyWithSchemeType(r15)
        L_0x07ba:
            java.lang.Object r13 = r13.second
            com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox r13 = (com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox) r13
            r7[r41] = r13
            goto L_0x07c4
        L_0x07c1:
            r14 = r54
            r15 = r14
        L_0x07c4:
            r3.setPosition(r6)
            goto L_0x07cf
        L_0x07c8:
            r14 = r54
            r11 = r42
            r9 = r43
            r15 = r14
        L_0x07cf:
            java.lang.String r7 = "video/3gpp"
            r13 = 1831958048(0x6d317620, float:3.4326032E27)
            if (r10 != r13) goto L_0x07d9
            java.lang.String r13 = "video/mpeg"
            goto L_0x07e2
        L_0x07d9:
            r13 = 1211250227(0x48323633, float:182488.8)
            if (r10 != r13) goto L_0x07e0
            r13 = r7
            goto L_0x07e2
        L_0x07e0:
            r13 = r16
        L_0x07e2:
            r19 = 1065353216(0x3f800000, float:1.0)
            r30 = r7
            r44 = r8
            r0 = r13
            r42 = r15
            r7 = r16
            r8 = r7
            r14 = r8
            r4 = 1065353216(0x3f800000, float:1.0)
            r19 = 0
            r13 = r6
            r6 = -1
        L_0x07f5:
            int r15 = r13 - r9
            if (r15 >= r11) goto L_0x0976
            r3.setPosition(r13)
            int r15 = r3.getPosition()
            r43 = r8
            int r8 = r3.readInt()
            if (r8 != 0) goto L_0x0814
            int r46 = r3.getPosition()
            r47 = r6
            int r6 = r46 - r9
            if (r6 != r11) goto L_0x0816
            goto L_0x097a
        L_0x0814:
            r47 = r6
        L_0x0816:
            if (r8 <= 0) goto L_0x081a
            r6 = 1
            goto L_0x081b
        L_0x081a:
            r6 = 0
        L_0x081b:
            r49 = r45
            r45 = r9
            r9 = r49
            com.google.android.exoplayer2.util.Assertions.checkState(r6, r9)
            int r6 = r3.readInt()
            r46 = r9
            r9 = 1635148611(0x61766343, float:2.8406573E20)
            if (r6 != r9) goto L_0x084f
            if (r0 != 0) goto L_0x0833
            r0 = 1
            goto L_0x0834
        L_0x0833:
            r0 = 0
        L_0x0834:
            com.google.android.exoplayer2.util.Assertions.checkState(r0)
            int r15 = r15 + 8
            r3.setPosition(r15)
            com.google.android.exoplayer2.video.AvcConfig r0 = com.google.android.exoplayer2.video.AvcConfig.parse(r3)
            java.util.List<byte[]> r6 = r0.a
            int r9 = r0.b
            r12.c = r9
            if (r19 != 0) goto L_0x084a
            float r4 = r0.e
        L_0x084a:
            java.lang.String r9 = "video/avc"
            java.lang.String r0 = r0.f
            goto L_0x086f
        L_0x084f:
            r9 = 1752589123(0x68766343, float:4.6541328E24)
            if (r6 != r9) goto L_0x0873
            if (r0 != 0) goto L_0x0858
            r0 = 1
            goto L_0x0859
        L_0x0858:
            r0 = 0
        L_0x0859:
            com.google.android.exoplayer2.util.Assertions.checkState(r0)
            int r15 = r15 + 8
            r3.setPosition(r15)
            com.google.android.exoplayer2.video.HevcConfig r0 = com.google.android.exoplayer2.video.HevcConfig.parse(r3)
            java.util.List<byte[]> r6 = r0.a
            int r9 = r0.b
            r12.c = r9
            java.lang.String r9 = "video/hevc"
            java.lang.String r0 = r0.c
        L_0x086f:
            r14 = r0
            r43 = r6
            goto L_0x08a7
        L_0x0873:
            r9 = 1685480259(0x64766343, float:1.8180206E22)
            if (r6 == r9) goto L_0x095c
            r9 = 1685485123(0x64767643, float:1.8185683E22)
            if (r6 != r9) goto L_0x087f
            goto L_0x095c
        L_0x087f:
            r9 = 1987076931(0x76706343, float:1.21891066E33)
            if (r6 != r9) goto L_0x0897
            if (r0 != 0) goto L_0x0888
            r0 = 1
            goto L_0x0889
        L_0x0888:
            r0 = 0
        L_0x0889:
            com.google.android.exoplayer2.util.Assertions.checkState(r0)
            r0 = 1987063864(0x76703038, float:1.21789965E33)
            if (r10 != r0) goto L_0x0894
            java.lang.String r0 = "video/x-vnd.on2.vp8"
            goto L_0x08a6
        L_0x0894:
            java.lang.String r0 = "video/x-vnd.on2.vp9"
            goto L_0x08a6
        L_0x0897:
            r9 = 1635135811(0x61763143, float:2.8384055E20)
            if (r6 != r9) goto L_0x08ac
            if (r0 != 0) goto L_0x08a0
            r0 = 1
            goto L_0x08a1
        L_0x08a0:
            r0 = 0
        L_0x08a1:
            com.google.android.exoplayer2.util.Assertions.checkState(r0)
            java.lang.String r0 = "video/av01"
        L_0x08a6:
            r9 = r0
        L_0x08a7:
            r6 = r9
            r9 = 1702061171(0x65736473, float:7.183675E22)
            goto L_0x08e0
        L_0x08ac:
            r9 = 1681012275(0x64323633, float:1.3149704E22)
            if (r6 != r9) goto L_0x08bf
            if (r0 != 0) goto L_0x08b5
            r0 = 1
            goto L_0x08b6
        L_0x08b5:
            r0 = 0
        L_0x08b6:
            com.google.android.exoplayer2.util.Assertions.checkState(r0)
            r0 = r30
            r9 = 1702061171(0x65736473, float:7.183675E22)
            goto L_0x08e1
        L_0x08bf:
            r9 = 1702061171(0x65736473, float:7.183675E22)
            if (r6 != r9) goto L_0x08e4
            if (r0 != 0) goto L_0x08c8
            r0 = 1
            goto L_0x08c9
        L_0x08c8:
            r0 = 0
        L_0x08c9:
            com.google.android.exoplayer2.util.Assertions.checkState(r0)
            android.util.Pair r0 = a(r15, r3)
            java.lang.Object r6 = r0.first
            java.lang.String r6 = (java.lang.String) r6
            java.lang.Object r0 = r0.second
            byte[] r0 = (byte[]) r0
            if (r0 == 0) goto L_0x08e0
            com.google.common.collect.ImmutableList r0 = com.google.common.collect.ImmutableList.of(r0)
            r43 = r0
        L_0x08e0:
            r0 = r6
        L_0x08e1:
            r48 = r10
            goto L_0x0932
        L_0x08e4:
            r9 = 1885434736(0x70617370, float:2.7909473E29)
            if (r6 != r9) goto L_0x0902
            int r15 = r15 + 8
            r3.setPosition(r15)
            int r4 = r3.readUnsignedIntToInt()
            int r6 = r3.readUnsignedIntToInt()
            float r4 = (float) r4
            float r6 = (float) r6
            float r4 = r4 / r6
            r48 = r10
            r6 = r47
            r9 = 3
            r19 = 1
            goto L_0x096b
        L_0x0902:
            r9 = 1937126244(0x73763364, float:1.9506033E31)
            if (r6 != r9) goto L_0x0934
            int r6 = r15 + 8
        L_0x0909:
            int r7 = r6 - r15
            if (r7 >= r8) goto L_0x092e
            r3.setPosition(r6)
            int r7 = r3.readInt()
            int r9 = r3.readInt()
            r48 = r10
            r10 = 1886547818(0x70726f6a, float:3.0012025E29)
            if (r9 != r10) goto L_0x092a
            byte[] r9 = r3.getData()
            int r7 = r7 + r6
            byte[] r6 = java.util.Arrays.copyOfRange(r9, r6, r7)
            r7 = r6
            goto L_0x0932
        L_0x092a:
            int r6 = r6 + r7
            r10 = r48
            goto L_0x0909
        L_0x092e:
            r48 = r10
            r7 = r16
        L_0x0932:
            r9 = 3
            goto L_0x0969
        L_0x0934:
            r48 = r10
            r9 = 1936995172(0x73743364, float:1.9347576E31)
            if (r6 != r9) goto L_0x0932
            int r6 = r3.readUnsignedByte()
            r9 = 3
            r3.skipBytes(r9)
            if (r6 != 0) goto L_0x0969
            int r6 = r3.readUnsignedByte()
            if (r6 == 0) goto L_0x095a
            r10 = 1
            if (r6 == r10) goto L_0x0958
            r10 = 2
            if (r6 == r10) goto L_0x0956
            if (r6 == r9) goto L_0x0954
            goto L_0x0969
        L_0x0954:
            r6 = 3
            goto L_0x096b
        L_0x0956:
            r6 = 2
            goto L_0x096b
        L_0x0958:
            r6 = 1
            goto L_0x096b
        L_0x095a:
            r6 = 0
            goto L_0x096b
        L_0x095c:
            r48 = r10
            r9 = 3
            com.google.android.exoplayer2.video.DolbyVisionConfig r6 = com.google.android.exoplayer2.video.DolbyVisionConfig.parse(r3)
            if (r6 == 0) goto L_0x0969
            java.lang.String r14 = r6.a
            java.lang.String r0 = "video/dolby-vision"
        L_0x0969:
            r6 = r47
        L_0x096b:
            int r13 = r13 + r8
            r8 = r43
            r9 = r45
            r45 = r46
            r10 = r48
            goto L_0x07f5
        L_0x0976:
            r47 = r6
            r43 = r8
        L_0x097a:
            r45 = r9
            r9 = 3
            if (r0 != 0) goto L_0x0982
            r1 = r39
            goto L_0x09c3
        L_0x0982:
            com.google.android.exoplayer2.Format$Builder r6 = new com.google.android.exoplayer2.Format$Builder
            r6.<init>()
            com.google.android.exoplayer2.Format$Builder r2 = r6.setId((int) r2)
            com.google.android.exoplayer2.Format$Builder r0 = r2.setSampleMimeType(r0)
            com.google.android.exoplayer2.Format$Builder r0 = r0.setCodecs(r14)
            com.google.android.exoplayer2.Format$Builder r0 = r0.setWidth(r1)
            com.google.android.exoplayer2.Format$Builder r0 = r0.setHeight(r5)
            com.google.android.exoplayer2.Format$Builder r0 = r0.setPixelWidthHeightRatio(r4)
            r1 = r39
            int r2 = r1.c
            com.google.android.exoplayer2.Format$Builder r0 = r0.setRotationDegrees(r2)
            com.google.android.exoplayer2.Format$Builder r0 = r0.setProjectionData(r7)
            r4 = r47
            com.google.android.exoplayer2.Format$Builder r0 = r0.setStereoMode(r4)
            r2 = r43
            com.google.android.exoplayer2.Format$Builder r0 = r0.setInitializationData(r2)
            r14 = r42
            com.google.android.exoplayer2.Format$Builder r0 = r0.setDrmInitData(r14)
            com.google.android.exoplayer2.Format r0 = r0.build()
            r12.b = r0
        L_0x09c3:
            int r14 = r45 + r11
            r3.setPosition(r14)
            int r13 = r41 + 1
            r11 = r1
            r10 = r28
            r4 = r32
            r2 = r33
            r6 = r35
            r9 = r37
            r0 = r38
            r5 = r40
            r8 = r44
            r1 = r54
            goto L_0x0225
        L_0x09df:
            r38 = r0
            r33 = r2
            r32 = r4
            r40 = r5
            r35 = r6
            r37 = r9
            r1 = r11
            r31 = 0
            if (r55 != 0) goto L_0x0a65
            r0 = 1701082227(0x65647473, float:6.742798E22)
            r5 = r40
            com.google.android.exoplayer2.extractor.mp4.a$a r0 = r5.getContainerAtomOfType(r0)
            if (r0 == 0) goto L_0x0a67
            r2 = 1701606260(0x656c7374, float:6.9788014E22)
            com.google.android.exoplayer2.extractor.mp4.a$b r0 = r0.getLeafAtomOfType(r2)
            if (r0 != 0) goto L_0x0a07
            r0 = r16
            goto L_0x0a56
        L_0x0a07:
            com.google.android.exoplayer2.util.ParsableByteArray r0 = r0.b
            r2 = 8
            r0.setPosition(r2)
            int r2 = r0.readInt()
            int r2 = com.google.android.exoplayer2.extractor.mp4.a.parseFullAtomVersion(r2)
            int r3 = r0.readUnsignedIntToInt()
            long[] r4 = new long[r3]
            long[] r6 = new long[r3]
            r7 = 0
        L_0x0a1f:
            if (r7 >= r3) goto L_0x0a52
            r8 = 1
            if (r2 != r8) goto L_0x0a29
            long r9 = r0.readUnsignedLongToLong()
            goto L_0x0a2d
        L_0x0a29:
            long r9 = r0.readUnsignedInt()
        L_0x0a2d:
            r4[r7] = r9
            if (r2 != r8) goto L_0x0a36
            long r9 = r0.readLong()
            goto L_0x0a3b
        L_0x0a36:
            int r9 = r0.readInt()
            long r9 = (long) r9
        L_0x0a3b:
            r6[r7] = r9
            short r9 = r0.readShort()
            if (r9 != r8) goto L_0x0a4a
            r9 = 2
            r0.skipBytes(r9)
            int r7 = r7 + 1
            goto L_0x0a1f
        L_0x0a4a:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Unsupported media rate."
            r0.<init>(r1)
            throw r0
        L_0x0a52:
            android.util.Pair r0 = android.util.Pair.create(r4, r6)
        L_0x0a56:
            if (r0 == 0) goto L_0x0a67
            java.lang.Object r2 = r0.first
            long[] r2 = (long[]) r2
            java.lang.Object r0 = r0.second
            long[] r0 = (long[]) r0
            r30 = r0
            r29 = r2
            goto L_0x0a6b
        L_0x0a65:
            r5 = r40
        L_0x0a67:
            r29 = r16
            r30 = r29
        L_0x0a6b:
            com.google.android.exoplayer2.Format r0 = r12.b
            if (r0 != 0) goto L_0x0a70
            goto L_0x0a9b
        L_0x0a70:
            com.google.android.exoplayer2.extractor.mp4.Track r0 = new com.google.android.exoplayer2.extractor.mp4.Track
            int r1 = r1.a
            r2 = r38
            java.lang.Object r2 = r2.first
            java.lang.Long r2 = (java.lang.Long) r2
            long r19 = r2.longValue()
            com.google.android.exoplayer2.Format r2 = r12.b
            int r3 = r12.d
            com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox[] r4 = r12.a
            int r6 = r12.c
            r16 = r0
            r17 = r1
            r18 = r37
            r21 = r35
            r23 = r25
            r25 = r2
            r26 = r3
            r27 = r4
            r28 = r6
            r16.<init>(r17, r18, r19, r21, r23, r25, r26, r27, r28, r29, r30)
        L_0x0a9b:
            r1 = r57
            r0 = r16
        L_0x0a9f:
            java.lang.Object r0 = r1.apply(r0)
            com.google.android.exoplayer2.extractor.mp4.Track r0 = (com.google.android.exoplayer2.extractor.mp4.Track) r0
            if (r0 != 0) goto L_0x0aac
            r3 = r51
            r2 = r33
            goto L_0x0ade
        L_0x0aac:
            r2 = 1835297121(0x6d646961, float:4.4181236E27)
            com.google.android.exoplayer2.extractor.mp4.a$a r2 = r5.getContainerAtomOfType(r2)
            java.lang.Object r2 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r2)
            com.google.android.exoplayer2.extractor.mp4.a$a r2 = (com.google.android.exoplayer2.extractor.mp4.a.C0014a) r2
            r3 = 1835626086(0x6d696e66, float:4.515217E27)
            com.google.android.exoplayer2.extractor.mp4.a$a r2 = r2.getContainerAtomOfType(r3)
            java.lang.Object r2 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r2)
            com.google.android.exoplayer2.extractor.mp4.a$a r2 = (com.google.android.exoplayer2.extractor.mp4.a.C0014a) r2
            r3 = 1937007212(0x7374626c, float:1.9362132E31)
            com.google.android.exoplayer2.extractor.mp4.a$a r2 = r2.getContainerAtomOfType(r3)
            java.lang.Object r2 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r2)
            com.google.android.exoplayer2.extractor.mp4.a$a r2 = (com.google.android.exoplayer2.extractor.mp4.a.C0014a) r2
            r3 = r51
            pc r0 = d(r0, r2, r3)
            r2 = r33
            r2.add(r0)
        L_0x0ade:
            int r4 = r32 + 1
            r0 = r50
            r1 = r54
            goto L_0x000a
        L_0x0ae6:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mp4.b.parseTraks(com.google.android.exoplayer2.extractor.mp4.a$a, com.google.android.exoplayer2.extractor.GaplessInfoHolder, long, com.google.android.exoplayer2.drm.DrmInitData, boolean, boolean, com.google.common.base.Function):java.util.List");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00d5, code lost:
        r3 = null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.util.Pair<com.google.android.exoplayer2.metadata.Metadata, com.google.android.exoplayer2.metadata.Metadata> parseUdta(com.google.android.exoplayer2.extractor.mp4.a.b r11) {
        /*
            com.google.android.exoplayer2.util.ParsableByteArray r11 = r11.b
            r0 = 8
            r11.setPosition(r0)
            r1 = 0
            r2 = r1
            r3 = r2
        L_0x000a:
            int r4 = r11.bytesLeft()
            if (r4 < r0) goto L_0x00dc
            int r4 = r11.getPosition()
            int r5 = r11.readInt()
            int r6 = r11.readInt()
            r7 = 1835365473(0x6d657461, float:4.4382975E27)
            if (r6 != r7) goto L_0x0076
            r11.setPosition(r4)
            int r2 = r4 + r5
            r11.skipBytes(r0)
            maybeSkipRemainingMetaAtomHeaderBytes(r11)
        L_0x002c:
            int r6 = r11.getPosition()
            if (r6 >= r2) goto L_0x0074
            int r6 = r11.getPosition()
            int r7 = r11.readInt()
            int r8 = r11.readInt()
            r9 = 1768715124(0x696c7374, float:1.7865732E25)
            if (r8 != r9) goto L_0x006f
            r11.setPosition(r6)
            int r6 = r6 + r7
            r11.skipBytes(r0)
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
        L_0x004f:
            int r7 = r11.getPosition()
            if (r7 >= r6) goto L_0x005f
            com.google.android.exoplayer2.metadata.Metadata$Entry r7 = defpackage.r6.parseIlstElement(r11)
            if (r7 == 0) goto L_0x004f
            r2.add(r7)
            goto L_0x004f
        L_0x005f:
            boolean r6 = r2.isEmpty()
            if (r6 == 0) goto L_0x0067
            r6 = r1
            goto L_0x006c
        L_0x0067:
            com.google.android.exoplayer2.metadata.Metadata r6 = new com.google.android.exoplayer2.metadata.Metadata
            r6.<init>((java.util.List<? extends com.google.android.exoplayer2.metadata.Metadata.Entry>) r2)
        L_0x006c:
            r2 = r6
            goto L_0x00d6
        L_0x006f:
            int r6 = r6 + r7
            r11.setPosition(r6)
            goto L_0x002c
        L_0x0074:
            r2 = r1
            goto L_0x00d6
        L_0x0076:
            r7 = 1936553057(0x736d7461, float:1.8813092E31)
            if (r6 != r7) goto L_0x00d6
            r11.setPosition(r4)
            int r3 = r4 + r5
            r6 = 12
            r11.skipBytes(r6)
        L_0x0085:
            int r7 = r11.getPosition()
            if (r7 >= r3) goto L_0x00d5
            int r7 = r11.getPosition()
            int r8 = r11.readInt()
            int r9 = r11.readInt()
            r10 = 1935766900(0x73617574, float:1.7862687E31)
            if (r9 != r10) goto L_0x00d0
            r3 = 14
            if (r8 >= r3) goto L_0x00a1
            goto L_0x00d5
        L_0x00a1:
            r3 = 5
            r11.skipBytes(r3)
            int r3 = r11.readUnsignedByte()
            if (r3 == r6) goto L_0x00b0
            r7 = 13
            if (r3 == r7) goto L_0x00b0
            goto L_0x00d5
        L_0x00b0:
            if (r3 != r6) goto L_0x00b5
            r3 = 1131413504(0x43700000, float:240.0)
            goto L_0x00b7
        L_0x00b5:
            r3 = 1123024896(0x42f00000, float:120.0)
        L_0x00b7:
            r6 = 1
            r11.skipBytes(r6)
            int r7 = r11.readUnsignedByte()
            com.google.android.exoplayer2.metadata.Metadata r8 = new com.google.android.exoplayer2.metadata.Metadata
            com.google.android.exoplayer2.metadata.Metadata$Entry[] r6 = new com.google.android.exoplayer2.metadata.Metadata.Entry[r6]
            com.google.android.exoplayer2.metadata.mp4.SmtaMetadataEntry r9 = new com.google.android.exoplayer2.metadata.mp4.SmtaMetadataEntry
            r9.<init>(r3, r7)
            r3 = 0
            r6[r3] = r9
            r8.<init>((com.google.android.exoplayer2.metadata.Metadata.Entry[]) r6)
            r3 = r8
            goto L_0x00d6
        L_0x00d0:
            int r7 = r7 + r8
            r11.setPosition(r7)
            goto L_0x0085
        L_0x00d5:
            r3 = r1
        L_0x00d6:
            int r4 = r4 + r5
            r11.setPosition(r4)
            goto L_0x000a
        L_0x00dc:
            android.util.Pair r11 = android.util.Pair.create(r2, r3)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mp4.b.parseUdta(com.google.android.exoplayer2.extractor.mp4.a$b):android.util.Pair");
    }
}
